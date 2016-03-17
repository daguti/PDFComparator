/*      */ package org.apache.fontbox.ttf;
/*      */ 
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.DataOutputStream;
/*      */ import java.io.EOFException;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Set;
/*      */ import java.util.SortedMap;
/*      */ import java.util.SortedSet;
/*      */ import java.util.TreeMap;
/*      */ import java.util.TreeSet;
/*      */ import org.apache.commons.logging.Log;
/*      */ import org.apache.commons.logging.LogFactory;
/*      */ import org.apache.fontbox.encoding.Encoding;
/*      */ import org.apache.fontbox.encoding.MacRomanEncoding;
/*      */ 
/*      */ public class TTFSubFont
/*      */ {
/*   54 */   private static final Log LOG = LogFactory.getLog(TTFSubFont.class);
/*   55 */   private static final byte[] PAD_BUF = { 0, 0, 0 };
/*      */   private final TrueTypeFont baseTTF;
/*      */   private final String nameSuffix;
/*      */   private final CMAPEncodingEntry baseCmap;
/*      */   private final SortedMap<Integer, Integer> characters;
/*      */   private final SortedSet<Integer> glyphIds;
/*      */ 
/*      */   public TTFSubFont(TrueTypeFont baseFont, String suffix)
/*      */   {
/*   76 */     this.baseTTF = baseFont;
/*   77 */     this.nameSuffix = suffix;
/*   78 */     this.characters = new TreeMap();
/*   79 */     this.glyphIds = new TreeSet();
/*      */ 
/*   81 */     CMAPEncodingEntry[] cmaps = this.baseTTF.getCMAP().getCmaps();
/*   82 */     CMAPEncodingEntry unicodeCmap = null;
/*      */ 
/*   84 */     for (CMAPEncodingEntry cmap : cmaps)
/*      */     {
/*   87 */       if ((cmap.getPlatformId() == 0) || ((cmap.getPlatformId() == 3) && (cmap.getPlatformEncodingId() == 1)))
/*      */       {
/*   89 */         unicodeCmap = cmap;
/*   90 */         break;
/*      */       }
/*      */     }
/*   93 */     this.baseCmap = unicodeCmap;
/*      */ 
/*   95 */     addCharCode(0);
/*      */   }
/*      */ 
/*      */   public void addCharCode(int charCode)
/*      */   {
/*  106 */     Integer gid = Integer.valueOf(this.baseCmap.getGlyphId(charCode));
/*  107 */     if ((charCode == 0) || (gid.intValue() != 0))
/*      */     {
/*  109 */       this.characters.put(Integer.valueOf(charCode), gid);
/*  110 */       this.glyphIds.add(gid);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static int log2i(int i)
/*      */   {
/*  116 */     int ret = -1;
/*  117 */     if ((i & 0xFFFF0000) != 0)
/*      */     {
/*  119 */       i >>>= 16;
/*  120 */       ret += 16;
/*      */     }
/*  122 */     if ((i & 0xFF00) != 0)
/*      */     {
/*  124 */       i >>>= 8;
/*  125 */       ret += 8;
/*      */     }
/*  127 */     if ((i & 0xF0) != 0)
/*      */     {
/*  129 */       i >>>= 4;
/*  130 */       ret += 4;
/*      */     }
/*  132 */     if ((i & 0xC) != 0)
/*      */     {
/*  134 */       i >>>= 2;
/*  135 */       ret += 2;
/*      */     }
/*  137 */     if ((i & 0x2) != 0)
/*      */     {
/*  139 */       i >>>= 1;
/*  140 */       ret++;
/*      */     }
/*  142 */     if (i != 0)
/*      */     {
/*  144 */       ret++;
/*      */     }
/*  146 */     return ret;
/*      */   }
/*      */ 
/*      */   private static long buildUint32(int high, int low)
/*      */   {
/*  151 */     return (high & 0xFFFF) << 16 | low & 0xFFFF;
/*      */   }
/*      */ 
/*      */   private static long buildUint32(byte[] bytes)
/*      */   {
/*  156 */     return (bytes[0] & 0xFF) << 24 | (bytes[1] & 0xFF) << 16 | (bytes[2] & 0xFF) << 8 | bytes[3] & 0xFF;
/*      */   }
/*      */ 
/*      */   private static long writeFileHeader(DataOutputStream dos, int nTables)
/*      */     throws IOException
/*      */   {
/*  170 */     dos.writeInt(65536);
/*  171 */     dos.writeShort(nTables);
/*      */ 
/*  173 */     int mask = Integer.highestOneBit(nTables);
/*  174 */     int searchRange = mask * 16;
/*  175 */     dos.writeShort(searchRange);
/*      */ 
/*  177 */     int entrySelector = log2i(mask);
/*      */ 
/*  179 */     dos.writeShort(entrySelector);
/*      */ 
/*  182 */     int last = 16 * nTables - searchRange;
/*  183 */     dos.writeShort(last);
/*      */ 
/*  185 */     return 65536L + buildUint32(nTables, searchRange) + buildUint32(entrySelector, last);
/*      */   }
/*      */ 
/*      */   private static long writeTableHeader(DataOutputStream dos, String tag, long offset, byte[] bytes)
/*      */     throws IOException
/*      */   {
/*  192 */     int n = bytes.length;
/*      */ 
/*  194 */     long checksum = 0L;
/*      */ 
/*  196 */     for (int nup = 0; nup < n; nup++)
/*      */     {
/*  198 */       checksum += ((bytes[nup] & 0xFF) << 24 - nup % 4 * 8);
/*      */     }
/*      */ 
/*  201 */     checksum &= 4294967295L;
/*      */ 
/*  203 */     LOG.debug(String.format("Writing table header [%s,%08x,%08x,%08x]", new Object[] { tag, Long.valueOf(checksum), Long.valueOf(offset), Integer.valueOf(bytes.length) }));
/*      */ 
/*  205 */     byte[] tagbytes = tag.getBytes("US-ASCII");
/*      */ 
/*  207 */     dos.write(tagbytes, 0, 4);
/*  208 */     dos.writeInt((int)checksum);
/*  209 */     dos.writeInt((int)offset);
/*  210 */     dos.writeInt(bytes.length);
/*      */ 
/*  213 */     return buildUint32(tagbytes) + checksum + checksum + offset + bytes.length;
/*      */   }
/*      */ 
/*      */   private static void writeTableBody(OutputStream os, byte[] bytes) throws IOException
/*      */   {
/*  218 */     int n = bytes.length;
/*  219 */     os.write(bytes);
/*  220 */     if (n % 4 != 0)
/*      */     {
/*  222 */       os.write(PAD_BUF, 0, 4 - n % 4);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static void writeFixed(DataOutputStream dos, double f) throws IOException
/*      */   {
/*  228 */     double ip = Math.floor(f);
/*  229 */     double fp = (f - ip) * 65536.0D;
/*  230 */     dos.writeShort((int)ip);
/*  231 */     dos.writeShort((int)fp);
/*      */   }
/*      */ 
/*      */   private static void writeUint32(DataOutputStream dos, long l) throws IOException
/*      */   {
/*  236 */     dos.writeInt((int)l);
/*      */   }
/*      */ 
/*      */   private static void writeUint16(DataOutputStream dos, int i) throws IOException
/*      */   {
/*  241 */     dos.writeShort(i);
/*      */   }
/*      */ 
/*      */   private static void writeSint16(DataOutputStream dos, short i) throws IOException
/*      */   {
/*  246 */     dos.writeShort(i);
/*      */   }
/*      */ 
/*      */   private static void writeUint8(DataOutputStream dos, int i) throws IOException
/*      */   {
/*  251 */     dos.writeByte(i);
/*      */   }
/*      */ 
/*      */   private static void writeLongDateTime(DataOutputStream dos, Calendar calendar)
/*      */     throws IOException
/*      */   {
/*  257 */     GregorianCalendar cal = new GregorianCalendar(1904, 0, 1);
/*  258 */     long millisFor1904 = cal.getTimeInMillis();
/*  259 */     long secondsSince1904 = (calendar.getTimeInMillis() - millisFor1904) / 1000L;
/*  260 */     dos.writeLong(secondsSince1904);
/*      */   }
/*      */ 
/*      */   private byte[] buildHeadTable() throws IOException
/*      */   {
/*  265 */     ByteArrayOutputStream bos = new ByteArrayOutputStream();
/*  266 */     DataOutputStream dos = new DataOutputStream(bos);
/*      */ 
/*  268 */     LOG.debug("Building table [head]...");
/*      */ 
/*  270 */     HeaderTable h = this.baseTTF.getHeader();
/*      */ 
/*  272 */     writeFixed(dos, h.getVersion());
/*  273 */     writeFixed(dos, h.getFontRevision());
/*  274 */     writeUint32(dos, 0L);
/*  275 */     writeUint32(dos, h.getMagicNumber());
/*  276 */     writeUint16(dos, h.getFlags());
/*  277 */     writeUint16(dos, h.getUnitsPerEm());
/*  278 */     writeLongDateTime(dos, h.getCreated());
/*  279 */     writeLongDateTime(dos, h.getModified());
/*  280 */     writeSint16(dos, h.getXMin());
/*  281 */     writeSint16(dos, h.getYMin());
/*  282 */     writeSint16(dos, h.getXMax());
/*  283 */     writeSint16(dos, h.getYMax());
/*  284 */     writeUint16(dos, h.getMacStyle());
/*  285 */     writeUint16(dos, h.getLowestRecPPEM());
/*  286 */     writeSint16(dos, h.getFontDirectionHint());
/*      */ 
/*  288 */     writeSint16(dos, (short)1);
/*  289 */     writeSint16(dos, h.getGlyphDataFormat());
/*  290 */     dos.flush();
/*      */ 
/*  292 */     LOG.debug("Finished table [head].");
/*      */ 
/*  294 */     return bos.toByteArray();
/*      */   }
/*      */ 
/*      */   private byte[] buildHheaTable() throws IOException
/*      */   {
/*  299 */     ByteArrayOutputStream bos = new ByteArrayOutputStream();
/*  300 */     DataOutputStream dos = new DataOutputStream(bos);
/*      */ 
/*  302 */     LOG.debug("Building table [hhea]...");
/*      */ 
/*  304 */     HorizontalHeaderTable h = this.baseTTF.getHorizontalHeader();
/*      */ 
/*  306 */     writeFixed(dos, h.getVersion());
/*  307 */     writeSint16(dos, h.getAscender());
/*  308 */     writeSint16(dos, h.getDescender());
/*  309 */     writeSint16(dos, h.getLineGap());
/*  310 */     writeUint16(dos, h.getAdvanceWidthMax());
/*  311 */     writeSint16(dos, h.getMinLeftSideBearing());
/*  312 */     writeSint16(dos, h.getMinRightSideBearing());
/*  313 */     writeSint16(dos, h.getXMaxExtent());
/*  314 */     writeSint16(dos, h.getCaretSlopeRise());
/*  315 */     writeSint16(dos, h.getCaretSlopeRun());
/*  316 */     writeSint16(dos, h.getReserved1());
/*  317 */     writeSint16(dos, h.getReserved2());
/*  318 */     writeSint16(dos, h.getReserved3());
/*  319 */     writeSint16(dos, h.getReserved4());
/*  320 */     writeSint16(dos, h.getReserved5());
/*  321 */     writeSint16(dos, h.getMetricDataFormat());
/*  322 */     writeUint16(dos, this.glyphIds.subSet(Integer.valueOf(0), Integer.valueOf(h.getNumberOfHMetrics())).size());
/*      */ 
/*  324 */     dos.flush();
/*  325 */     LOG.debug("Finished table [hhea].");
/*  326 */     return bos.toByteArray();
/*      */   }
/*      */ 
/*      */   private static boolean replicateNameRecord(NameRecord nr)
/*      */   {
/*  331 */     return (nr.getPlatformId() == 3) && (nr.getPlatformEncodingId() == 1) && (nr.getLanguageId() == 0) && (nr.getNameId() >= 0) && (nr.getNameId() < 7);
/*      */   }
/*      */ 
/*      */   private byte[] buildNameTable()
/*      */     throws IOException
/*      */   {
/*  339 */     ByteArrayOutputStream bos = new ByteArrayOutputStream();
/*  340 */     DataOutputStream dos = new DataOutputStream(bos);
/*      */ 
/*  342 */     LOG.debug("Building table [name]...");
/*      */ 
/*  344 */     NamingTable n = this.baseTTF.getNaming();
/*  345 */     List nameRecords = null;
/*  346 */     if (n != null)
/*      */     {
/*  348 */       nameRecords = n.getNameRecords();
/*      */     }
/*      */     else
/*      */     {
/*  354 */       nameRecords = new ArrayList();
/*  355 */       NameRecord nr = new NameRecord();
/*  356 */       nr.setPlatformId(3);
/*  357 */       nr.setPlatformEncodingId(1);
/*  358 */       nr.setLanguageId(0);
/*  359 */       nr.setNameId(1);
/*  360 */       nr.setString("PDFBox-Dummy-Familyname");
/*  361 */       nameRecords.add(nr);
/*  362 */       nr = new NameRecord();
/*  363 */       nr.setPlatformId(3);
/*  364 */       nr.setPlatformEncodingId(1);
/*  365 */       nr.setLanguageId(0);
/*  366 */       nr.setNameId(4);
/*  367 */       nr.setString("PDFBox-Dummy-Fullname");
/*  368 */       nameRecords.add(nr);
/*      */     }
/*  370 */     int numberOfRecords = nameRecords.size();
/*  371 */     int nrep = 0;
/*  372 */     for (int i = 0; i < numberOfRecords; i++)
/*      */     {
/*  374 */       NameRecord nr = (NameRecord)nameRecords.get(i);
/*  375 */       if (replicateNameRecord(nr))
/*      */       {
/*  377 */         LOG.debug("Writing name record [" + nr.getNameId() + "], [" + nr.getString() + "],");
/*  378 */         nrep++;
/*      */       }
/*      */     }
/*  381 */     writeUint16(dos, 0);
/*  382 */     writeUint16(dos, nrep);
/*  383 */     writeUint16(dos, 6 + 12 * nrep);
/*      */ 
/*  385 */     byte[][] names = new byte[nrep][];
/*  386 */     int j = 0;
/*  387 */     for (int i = 0; i < numberOfRecords; i++)
/*      */     {
/*  389 */       NameRecord nr = (NameRecord)nameRecords.get(i);
/*  390 */       if (replicateNameRecord(nr))
/*      */       {
/*  392 */         int platform = nr.getPlatformId();
/*  393 */         int encoding = nr.getPlatformEncodingId();
/*  394 */         String charset = "ISO-8859-1";
/*  395 */         if ((platform == 3) && (encoding == 1))
/*      */         {
/*  397 */           charset = "UTF-16BE";
/*      */         }
/*  399 */         else if (platform == 2)
/*      */         {
/*  401 */           if (encoding == 0)
/*      */           {
/*  403 */             charset = "US-ASCII";
/*      */           }
/*  405 */           else if (encoding == 1)
/*      */           {
/*  408 */             charset = "UTF16-BE";
/*      */           }
/*  410 */           else if (encoding == 2)
/*      */           {
/*  412 */             charset = "ISO-8859-1";
/*      */           }
/*      */         }
/*  415 */         String value = nr.getString();
/*  416 */         if ((nr.getNameId() == 6) && (this.nameSuffix != null))
/*      */         {
/*  418 */           value = value + this.nameSuffix;
/*      */         }
/*  420 */         names[j] = value.getBytes(charset);
/*  421 */         j++;
/*      */       }
/*      */     }
/*      */ 
/*  425 */     int offset = 0;
/*  426 */     j = 0;
/*  427 */     for (int i = 0; i < numberOfRecords; i++)
/*      */     {
/*  429 */       NameRecord nr = (NameRecord)nameRecords.get(i);
/*  430 */       if (replicateNameRecord(nr))
/*      */       {
/*  432 */         writeUint16(dos, nr.getPlatformId());
/*  433 */         writeUint16(dos, nr.getPlatformEncodingId());
/*  434 */         writeUint16(dos, nr.getLanguageId());
/*  435 */         writeUint16(dos, nr.getNameId());
/*  436 */         writeUint16(dos, names[j].length);
/*  437 */         writeUint16(dos, offset);
/*  438 */         offset += names[j].length;
/*  439 */         j++;
/*      */       }
/*      */     }
/*      */ 
/*  443 */     for (int i = 0; i < nrep; i++)
/*      */     {
/*  445 */       dos.write(names[i]);
/*      */     }
/*  447 */     dos.flush();
/*  448 */     LOG.debug("Finished table [name].");
/*  449 */     return bos.toByteArray();
/*      */   }
/*      */ 
/*      */   private byte[] buildMaxpTable() throws IOException
/*      */   {
/*  454 */     ByteArrayOutputStream bos = new ByteArrayOutputStream();
/*  455 */     DataOutputStream dos = new DataOutputStream(bos);
/*      */ 
/*  457 */     LOG.debug("Building table [maxp]...");
/*      */ 
/*  459 */     MaximumProfileTable p = this.baseTTF.getMaximumProfile();
/*      */ 
/*  461 */     writeFixed(dos, 1.0D);
/*  462 */     writeUint16(dos, this.glyphIds.size());
/*  463 */     writeUint16(dos, p.getMaxPoints());
/*  464 */     writeUint16(dos, p.getMaxContours());
/*  465 */     writeUint16(dos, p.getMaxCompositePoints());
/*  466 */     writeUint16(dos, p.getMaxCompositeContours());
/*  467 */     writeUint16(dos, p.getMaxZones());
/*  468 */     writeUint16(dos, p.getMaxTwilightPoints());
/*  469 */     writeUint16(dos, p.getMaxStorage());
/*  470 */     writeUint16(dos, p.getMaxFunctionDefs());
/*  471 */     writeUint16(dos, 0);
/*  472 */     writeUint16(dos, p.getMaxStackElements());
/*  473 */     writeUint16(dos, 0);
/*  474 */     writeUint16(dos, p.getMaxComponentElements());
/*  475 */     writeUint16(dos, p.getMaxComponentDepth());
/*      */ 
/*  477 */     dos.flush();
/*  478 */     LOG.debug("Finished table [maxp].");
/*  479 */     return bos.toByteArray();
/*      */   }
/*      */ 
/*      */   private byte[] buildOS2Table() throws IOException
/*      */   {
/*  484 */     ByteArrayOutputStream bos = new ByteArrayOutputStream();
/*  485 */     DataOutputStream dos = new DataOutputStream(bos);
/*  486 */     OS2WindowsMetricsTable os2 = this.baseTTF.getOS2Windows();
/*  487 */     if (os2 == null)
/*      */     {
/*  491 */       os2 = new OS2WindowsMetricsTable();
/*      */     }
/*      */ 
/*  494 */     LOG.debug("Building table [OS/2]...");
/*      */ 
/*  496 */     writeUint16(dos, 0);
/*  497 */     writeSint16(dos, os2.getAverageCharWidth());
/*  498 */     writeUint16(dos, os2.getWeightClass());
/*  499 */     writeUint16(dos, os2.getWidthClass());
/*      */ 
/*  501 */     writeSint16(dos, os2.getFsType());
/*      */ 
/*  503 */     writeSint16(dos, os2.getSubscriptXSize());
/*  504 */     writeSint16(dos, os2.getSubscriptYSize());
/*  505 */     writeSint16(dos, os2.getSubscriptXOffset());
/*  506 */     writeSint16(dos, os2.getSubscriptYOffset());
/*      */ 
/*  508 */     writeSint16(dos, os2.getSuperscriptXSize());
/*  509 */     writeSint16(dos, os2.getSuperscriptYSize());
/*  510 */     writeSint16(dos, os2.getSuperscriptXOffset());
/*  511 */     writeSint16(dos, os2.getSuperscriptYOffset());
/*      */ 
/*  513 */     writeSint16(dos, os2.getStrikeoutSize());
/*  514 */     writeSint16(dos, os2.getStrikeoutPosition());
/*  515 */     writeUint8(dos, os2.getFamilyClass());
/*  516 */     writeUint8(dos, os2.getFamilySubClass());
/*  517 */     dos.write(os2.getPanose());
/*      */ 
/*  519 */     writeUint32(dos, 0L);
/*  520 */     writeUint32(dos, 0L);
/*  521 */     writeUint32(dos, 0L);
/*  522 */     writeUint32(dos, 0L);
/*      */ 
/*  524 */     dos.write(os2.getAchVendId().getBytes("ISO-8859-1"));
/*      */ 
/*  526 */     Iterator it = this.characters.entrySet().iterator();
/*  527 */     it.next();
/*  528 */     Map.Entry first = (Map.Entry)it.next();
/*      */ 
/*  530 */     writeUint16(dos, os2.getFsSelection());
/*  531 */     writeUint16(dos, ((Integer)first.getKey()).intValue());
/*  532 */     writeUint16(dos, ((Integer)this.characters.lastKey()).intValue());
/*      */ 
/*  542 */     writeUint16(dos, os2.getTypoAscender());
/*  543 */     writeUint16(dos, os2.getTypoDescender());
/*  544 */     writeUint16(dos, os2.getTypeLineGap());
/*  545 */     writeUint16(dos, os2.getWinAscent());
/*  546 */     writeUint16(dos, os2.getWinDescent());
/*      */ 
/*  548 */     dos.flush();
/*  549 */     LOG.debug("Finished table [OS/2].");
/*  550 */     return bos.toByteArray();
/*      */   }
/*      */ 
/*      */   private byte[] buildLocaTable(long[] newOffsets) throws IOException
/*      */   {
/*  555 */     ByteArrayOutputStream bos = new ByteArrayOutputStream();
/*  556 */     DataOutputStream dos = new DataOutputStream(bos);
/*      */ 
/*  558 */     LOG.debug("Building table [loca]...");
/*      */ 
/*  560 */     for (long newOff : newOffsets)
/*      */     {
/*  562 */       writeUint32(dos, newOff);
/*      */     }
/*  564 */     dos.flush();
/*  565 */     LOG.debug("Finished table [loca].");
/*  566 */     return bos.toByteArray();
/*      */   }
/*      */ 
/*      */   private boolean addCompoundReferences() throws IOException
/*      */   {
/*  571 */     GlyphTable g = this.baseTTF.getGlyph();
/*  572 */     long[] offsets = this.baseTTF.getIndexToLocation().getOffsets();
/*  573 */     InputStream is = this.baseTTF.getOriginalData();
/*  574 */     Set glyphIdsToAdd = null;
/*      */     try
/*      */     {
/*  577 */       is.skip(g.getOffset());
/*  578 */       lastOff = 0L;
/*  579 */       for (Integer glyphId : this.glyphIds)
/*      */       {
/*  581 */         long offset = offsets[glyphId.intValue()];
/*  582 */         long len = offsets[(glyphId.intValue() + 1)] - offset;
/*  583 */         is.skip(offset - lastOff);
/*  584 */         byte[] buf = new byte[(int)len];
/*  585 */         is.read(buf);
/*      */ 
/*  587 */         if ((buf.length >= 2) && (buf[0] == -1) && (buf[1] == -1))
/*      */         {
/*  589 */           int off = 10;
/*  590 */           int flags = 0;
/*      */           do
/*      */           {
/*  593 */             flags = (buf[off] & 0xFF) << 8 | buf[(off + 1)] & 0xFF;
/*  594 */             off += 2;
/*  595 */             int ogid = (buf[off] & 0xFF) << 8 | buf[(off + 1)] & 0xFF;
/*  596 */             if (!this.glyphIds.contains(Integer.valueOf(ogid)))
/*      */             {
/*  598 */               LOG.debug("Adding referenced glyph " + ogid + " of compound glyph " + glyphId);
/*  599 */               if (glyphIdsToAdd == null)
/*      */               {
/*  601 */                 glyphIdsToAdd = new TreeSet();
/*      */               }
/*  603 */               glyphIdsToAdd.add(Integer.valueOf(ogid));
/*      */             }
/*  605 */             off += 2;
/*      */ 
/*  607 */             if ((flags & 0x1) != 0)
/*      */             {
/*  609 */               off += 4;
/*      */             }
/*      */             else
/*      */             {
/*  613 */               off += 2;
/*      */             }
/*      */ 
/*  616 */             if ((flags & 0x80) != 0)
/*      */             {
/*  618 */               off += 8;
/*      */             }
/*  621 */             else if ((flags & 0x40) != 0)
/*      */             {
/*  623 */               off += 4;
/*      */             }
/*  626 */             else if ((flags & 0x8) != 0)
/*      */             {
/*  628 */               off += 2;
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  633 */           while ((flags & 0x20) != 0);
/*      */         }
/*      */ 
/*  636 */         lastOff = offsets[(glyphId.intValue() + 1)];
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*      */       long lastOff;
/*  641 */       is.close();
/*      */     }
/*  643 */     if (glyphIdsToAdd != null)
/*      */     {
/*  645 */       this.glyphIds.addAll(glyphIdsToAdd);
/*      */     }
/*  647 */     return glyphIdsToAdd == null;
/*      */   }
/*      */ 
/*      */   private byte[] buildGlyfTable(long[] newOffsets) throws IOException
/*      */   {
/*  652 */     ByteArrayOutputStream bos = new ByteArrayOutputStream();
/*  653 */     LOG.debug("Building table [glyf]...");
/*  654 */     GlyphTable g = this.baseTTF.getGlyph();
/*  655 */     long[] offsets = this.baseTTF.getIndexToLocation().getOffsets();
/*  656 */     InputStream is = this.baseTTF.getOriginalData();
/*      */     try
/*      */     {
/*  659 */       is.skip(g.getOffset());
/*  660 */       long lastOff = 0L;
/*  661 */       long newOff = 0L;
/*  662 */       int ioff = 0;
/*  663 */       for (Integer glyphId : this.glyphIds)
/*      */       {
/*  665 */         long offset = offsets[glyphId.intValue()];
/*  666 */         long len = offsets[(glyphId.intValue() + 1)] - offset;
/*  667 */         newOffsets[(ioff++)] = newOff;
/*  668 */         is.skip(offset - lastOff);
/*  669 */         byte[] buf = new byte[(int)len];
/*  670 */         is.read(buf);
/*      */ 
/*  672 */         if ((buf.length >= 2) && (buf[0] == -1) && (buf[1] == -1))
/*      */         {
/*  674 */           LOG.debug("Compound glyph " + glyphId);
/*  675 */           int off = 10;
/*  676 */           int flags = 0;
/*      */           do
/*      */           {
/*      */             int tmp221_219 = off;
/*      */             byte[] tmp221_217 = buf; tmp221_217[tmp221_219] = ((byte)(tmp221_217[tmp221_219] & 0xFE));
/*  681 */             flags = (buf[off] & 0xFF) << 8 | buf[(off + 1)] & 0xFF;
/*  682 */             off += 2;
/*  683 */             int ogid = (buf[off] & 0xFF) << 8 | buf[(off + 1)] & 0xFF;
/*  684 */             if (!this.glyphIds.contains(Integer.valueOf(ogid)))
/*      */             {
/*  686 */               this.glyphIds.add(Integer.valueOf(ogid));
/*      */             }
/*  688 */             int ngid = getNewGlyphId(Integer.valueOf(ogid));
/*  689 */             if (LOG.isDebugEnabled())
/*      */             {
/*  691 */               LOG.debug(String.format("mapped glyph  %d to %d in compound reference (flags=%04x)", new Object[] { Integer.valueOf(ogid), Integer.valueOf(ngid), Integer.valueOf(flags) }));
/*      */             }
/*      */ 
/*  694 */             buf[off] = ((byte)(ngid >>> 8));
/*  695 */             buf[(off + 1)] = ((byte)ngid);
/*  696 */             off += 2;
/*      */ 
/*  698 */             if ((flags & 0x1) != 0)
/*      */             {
/*  700 */               off += 4;
/*      */             }
/*      */             else
/*      */             {
/*  704 */               off += 2;
/*      */             }
/*      */ 
/*  707 */             if ((flags & 0x80) != 0)
/*      */             {
/*  709 */               off += 8;
/*      */             }
/*  712 */             else if ((flags & 0x40) != 0)
/*      */             {
/*  714 */               off += 4;
/*      */             }
/*  717 */             else if ((flags & 0x8) != 0)
/*      */             {
/*  719 */               off += 2;
/*      */             }
/*      */           }
/*      */ 
/*  723 */           while ((flags & 0x20) != 0);
/*      */ 
/*  725 */           bos.write(buf, 0, off);
/*  726 */           newOff += off;
/*      */         }
/*  728 */         else if (buf.length > 0)
/*      */         {
/*  754 */           int numberOfContours = (buf[0] & 0xFF) << 8 | buf[1] & 0xFF;
/*      */ 
/*  757 */           int off = 10 + numberOfContours * 2;
/*      */ 
/*  760 */           bos.write(buf, 0, off);
/*  761 */           newOff += off;
/*      */ 
/*  763 */           int instructionLength = (buf[off] & 0xFF) << 8 | buf[(off + 1)] & 0xFF;
/*      */ 
/*  766 */           bos.write(0);
/*  767 */           bos.write(0);
/*  768 */           newOff += 2L;
/*      */ 
/*  770 */           off += 2 + instructionLength;
/*      */ 
/*  773 */           bos.write(buf, off, buf.length - off);
/*  774 */           newOff += buf.length - off;
/*      */         }
/*      */ 
/*  778 */         if (newOff % 4L != 0L)
/*      */         {
/*  780 */           int np = (int)(4L - newOff % 4L);
/*  781 */           bos.write(PAD_BUF, 0, np);
/*  782 */           newOff += np;
/*      */         }
/*      */ 
/*  785 */         lastOff = offsets[(glyphId.intValue() + 1)];
/*      */       }
/*  787 */       newOffsets[(ioff++)] = newOff;
/*      */     }
/*      */     finally
/*      */     {
/*  791 */       is.close();
/*      */     }
/*  793 */     LOG.debug("Finished table [glyf].");
/*  794 */     return bos.toByteArray();
/*      */   }
/*      */ 
/*      */   private int getNewGlyphId(Integer oldGid)
/*      */   {
/*  799 */     return this.glyphIds.headSet(oldGid).size();
/*      */   }
/*      */ 
/*      */   private byte[] buildCmapTable() throws IOException
/*      */   {
/*  804 */     ByteArrayOutputStream bos = new ByteArrayOutputStream();
/*  805 */     DataOutputStream dos = new DataOutputStream(bos);
/*  806 */     LOG.debug("Building table [cmap]...");
/*      */ 
/*  811 */     writeUint16(dos, 0);
/*  812 */     writeUint16(dos, 1);
/*      */ 
/*  818 */     writeUint16(dos, 3);
/*  819 */     writeUint16(dos, 1);
/*  820 */     writeUint32(dos, 12L);
/*      */ 
/*  822 */     Iterator it = this.characters.entrySet().iterator();
/*  823 */     it.next();
/*  824 */     Map.Entry lastChar = (Map.Entry)it.next();
/*  825 */     Map.Entry prevChar = lastChar;
/*  826 */     int lastGid = getNewGlyphId((Integer)lastChar.getValue());
/*      */ 
/*  828 */     int[] startCode = new int[this.characters.size()];
/*  829 */     int[] endCode = new int[this.characters.size()];
/*  830 */     int[] idDelta = new int[this.characters.size()];
/*  831 */     int nseg = 0;
/*  832 */     while (it.hasNext())
/*      */     {
/*  834 */       Map.Entry curChar = (Map.Entry)it.next();
/*  835 */       int curGid = getNewGlyphId((Integer)curChar.getValue());
/*      */ 
/*  837 */       if ((((Integer)curChar.getKey()).intValue() != ((Integer)prevChar.getKey()).intValue() + 1) || (curGid - lastGid != ((Integer)curChar.getKey()).intValue() - ((Integer)lastChar.getKey()).intValue()))
/*      */       {
/*  842 */         if (lastGid != 0)
/*      */         {
/*  844 */           startCode[nseg] = ((Integer)lastChar.getKey()).intValue();
/*  845 */           endCode[nseg] = ((Integer)prevChar.getKey()).intValue();
/*  846 */           idDelta[nseg] = (lastGid - ((Integer)lastChar.getKey()).intValue());
/*  847 */           nseg++;
/*      */         }
/*  850 */         else if (!((Integer)lastChar.getKey()).equals(prevChar.getKey()))
/*      */         {
/*  852 */           startCode[nseg] = (((Integer)lastChar.getKey()).intValue() + 1);
/*  853 */           endCode[nseg] = ((Integer)prevChar.getKey()).intValue();
/*  854 */           idDelta[nseg] = (lastGid - ((Integer)lastChar.getKey()).intValue());
/*  855 */           nseg++;
/*      */         }
/*  857 */         lastGid = curGid;
/*  858 */         lastChar = curChar;
/*      */       }
/*  860 */       prevChar = curChar;
/*      */     }
/*      */ 
/*  863 */     startCode[nseg] = ((Integer)lastChar.getKey()).intValue();
/*  864 */     endCode[nseg] = ((Integer)prevChar.getKey()).intValue();
/*  865 */     idDelta[nseg] = (lastGid - ((Integer)lastChar.getKey()).intValue());
/*  866 */     nseg++;
/*      */ 
/*  868 */     startCode[nseg] = 65535;
/*  869 */     endCode[nseg] = 65535;
/*  870 */     idDelta[nseg] = 1;
/*  871 */     nseg++;
/*      */ 
/*  889 */     writeUint16(dos, 4);
/*  890 */     writeUint16(dos, 16 + nseg * 8);
/*  891 */     writeUint16(dos, 0);
/*  892 */     writeUint16(dos, nseg * 2);
/*  893 */     int nsegHigh = Integer.highestOneBit(nseg);
/*  894 */     writeUint16(dos, nsegHigh * 2);
/*  895 */     writeUint16(dos, log2i(nsegHigh));
/*  896 */     writeUint16(dos, 2 * (nseg - nsegHigh));
/*      */ 
/*  898 */     for (int i = 0; i < nseg; i++)
/*      */     {
/*  900 */       writeUint16(dos, endCode[i]);
/*      */     }
/*  902 */     writeUint16(dos, 0);
/*  903 */     for (int i = 0; i < nseg; i++)
/*      */     {
/*  905 */       writeUint16(dos, startCode[i]);
/*      */     }
/*  907 */     for (int i = 0; i < nseg; i++)
/*      */     {
/*  909 */       writeUint16(dos, idDelta[i]);
/*      */     }
/*  911 */     for (int i = 0; i < nseg; i++)
/*      */     {
/*  913 */       writeUint16(dos, 0);
/*      */     }
/*  915 */     LOG.debug("Finished table [cmap].");
/*  916 */     return bos.toByteArray();
/*      */   }
/*      */ 
/*      */   private byte[] buildPostTable() throws IOException
/*      */   {
/*  921 */     ByteArrayOutputStream bos = new ByteArrayOutputStream();
/*  922 */     DataOutputStream dos = new DataOutputStream(bos);
/*  923 */     LOG.debug("Building table [post]...");
/*  924 */     PostScriptTable p = this.baseTTF.getPostScript();
/*  925 */     if (p == null)
/*      */     {
/*  929 */       p = new PostScriptTable();
/*      */     }
/*  931 */     String[] glyphNames = p.getGlyphNames();
/*      */ 
/*  949 */     writeFixed(dos, 2.0D);
/*  950 */     writeFixed(dos, p.getItalicAngle());
/*  951 */     writeSint16(dos, p.getUnderlinePosition());
/*  952 */     writeSint16(dos, p.getUnderlineThickness());
/*  953 */     writeUint32(dos, p.getIsFixedPitch());
/*  954 */     writeUint32(dos, p.getMinMemType42());
/*  955 */     writeUint32(dos, p.getMaxMemType42());
/*  956 */     writeUint32(dos, p.getMimMemType1());
/*  957 */     writeUint32(dos, p.getMaxMemType1());
/*  958 */     writeUint16(dos, this.baseTTF.getHorizontalHeader().getNumberOfHMetrics());
/*      */ 
/*  960 */     List additionalNames = new ArrayList();
/*  961 */     Map additionalNamesIndices = new HashMap();
/*      */     Encoding enc;
/*      */     int[] gidToUC;
/*  963 */     if (glyphNames == null)
/*      */     {
/*  965 */       enc = MacRomanEncoding.INSTANCE;
/*  966 */       gidToUC = this.baseCmap.getGlyphIdToCharacterCode();
/*  967 */       for (Integer glyphId : this.glyphIds)
/*      */       {
/*  969 */         int uc = gidToUC[glyphId.intValue()];
/*  970 */         String name = null;
/*  971 */         if (uc < 32768)
/*      */         {
/*      */           try
/*      */           {
/*  975 */             name = enc.getNameFromCharacter((char)uc);
/*      */           }
/*      */           catch (IOException e)
/*      */           {
/*      */           }
/*      */         }
/*      */ 
/*  982 */         if (name == null)
/*      */         {
/*  984 */           name = String.format(Locale.ENGLISH, "uni%04X", new Object[] { Integer.valueOf(uc) });
/*      */         }
/*  986 */         Integer macId = (Integer)Encoding.MAC_GLYPH_NAMES_INDICES.get(name);
/*  987 */         if (macId == null)
/*      */         {
/*  989 */           Integer idx = (Integer)additionalNamesIndices.get(name);
/*  990 */           if (idx == null)
/*      */           {
/*  992 */             idx = Integer.valueOf(additionalNames.size());
/*  993 */             additionalNames.add(name);
/*  994 */             additionalNamesIndices.put(name, idx);
/*      */           }
/*  996 */           writeUint16(dos, idx.intValue() + 258);
/*      */         }
/*      */         else
/*      */         {
/* 1000 */           writeUint16(dos, macId.intValue());
/*      */         }
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 1006 */       for (Integer glyphId : this.glyphIds)
/*      */       {
/* 1008 */         String name = glyphNames[glyphId.intValue()];
/* 1009 */         Integer macId = (Integer)Encoding.MAC_GLYPH_NAMES_INDICES.get(name);
/* 1010 */         if (macId == null)
/*      */         {
/* 1012 */           Integer idx = (Integer)additionalNamesIndices.get(name);
/* 1013 */           if (idx == null)
/*      */           {
/* 1015 */             idx = Integer.valueOf(additionalNames.size());
/* 1016 */             additionalNames.add(name);
/* 1017 */             additionalNamesIndices.put(name, idx);
/*      */           }
/* 1019 */           writeUint16(dos, idx.intValue() + 258);
/*      */         }
/*      */         else
/*      */         {
/* 1023 */           writeUint16(dos, macId.intValue());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1028 */     for (String name : additionalNames)
/*      */     {
/* 1030 */       LOG.debug("additionalName=[" + name + "].");
/* 1031 */       byte[] buf = name.getBytes("US-ASCII");
/* 1032 */       writeUint8(dos, buf.length);
/* 1033 */       dos.write(buf);
/*      */     }
/* 1035 */     dos.flush();
/* 1036 */     LOG.debug("Finished table [post].");
/* 1037 */     return bos.toByteArray();
/*      */   }
/*      */ 
/*      */   private byte[] buildHmtxTable() throws IOException
/*      */   {
/* 1042 */     ByteArrayOutputStream bos = new ByteArrayOutputStream();
/* 1043 */     LOG.debug("Building table [hmtx]...");
/* 1044 */     HorizontalHeaderTable h = this.baseTTF.getHorizontalHeader();
/* 1045 */     HorizontalMetricsTable hm = this.baseTTF.getHorizontalMetrics();
/* 1046 */     byte[] buf = new byte[4];
/* 1047 */     InputStream is = this.baseTTF.getOriginalData();
/*      */     try
/*      */     {
/* 1050 */       is.skip(hm.getOffset());
/* 1051 */       long lastOff = 0L;
/* 1052 */       for (Integer glyphId : this.glyphIds)
/*      */       {
/*      */         long off;
/*      */         long off;
/* 1056 */         if (glyphId.intValue() < h.getNumberOfHMetrics())
/*      */         {
/* 1058 */           off = glyphId.intValue() * 4;
/*      */         }
/*      */         else
/*      */         {
/* 1062 */           off = h.getNumberOfHMetrics() * 4 + (glyphId.intValue() - h.getNumberOfHMetrics()) * 2;
/*      */         }
/*      */ 
/* 1065 */         if (off != lastOff)
/*      */         {
/* 1067 */           long nskip = off - lastOff;
/* 1068 */           if (nskip != is.skip(nskip))
/*      */           {
/* 1070 */             throw new EOFException("Unexpected EOF exception parsing glyphId of hmtx table.");
/*      */           }
/*      */         }
/*      */ 
/* 1074 */         int n = glyphId.intValue() < h.getNumberOfHMetrics() ? 4 : 2;
/* 1075 */         if (n != is.read(buf, 0, n))
/*      */         {
/* 1077 */           throw new EOFException("Unexpected EOF exception parsing glyphId of hmtx table.");
/*      */         }
/* 1079 */         bos.write(buf, 0, n);
/* 1080 */         lastOff = off + n;
/*      */       }
/* 1082 */       LOG.debug("Finished table [hmtx].");
/* 1083 */       return bos.toByteArray();
/*      */     }
/*      */     finally
/*      */     {
/* 1087 */       is.close();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void writeToStream(OutputStream os)
/*      */     throws IOException
/*      */   {
/* 1099 */     LOG.debug("glyphIds=[" + this.glyphIds + "]");
/* 1100 */     LOG.debug("numGlyphs=[" + this.glyphIds.size() + "]");
/* 1101 */     while (!addCompoundReferences());
/* 1104 */     DataOutputStream dos = new DataOutputStream(os);
/*      */     try
/*      */     {
/* 1119 */       String[] tableNames = { "OS/2", "cmap", "glyf", "head", "hhea", "hmtx", "loca", "maxp", "name", "post" };
/* 1120 */       byte[][] tables = new byte[tableNames.length][];
/* 1121 */       long[] newOffsets = new long[this.glyphIds.size() + 1];
/* 1122 */       tables[3] = buildHeadTable();
/* 1123 */       tables[4] = buildHheaTable();
/* 1124 */       tables[7] = buildMaxpTable();
/* 1125 */       tables[8] = buildNameTable();
/* 1126 */       tables[0] = buildOS2Table();
/* 1127 */       tables[2] = buildGlyfTable(newOffsets);
/* 1128 */       tables[6] = buildLocaTable(newOffsets);
/* 1129 */       tables[1] = buildCmapTable();
/* 1130 */       tables[5] = buildHmtxTable();
/* 1131 */       tables[9] = buildPostTable();
/* 1132 */       long checksum = writeFileHeader(dos, tableNames.length);
/* 1133 */       long offset = 12L + 16L * tableNames.length;
/* 1134 */       for (int i = 0; i < tableNames.length; i++)
/*      */       {
/* 1136 */         checksum += writeTableHeader(dos, tableNames[i], offset, tables[i]);
/* 1137 */         offset += (tables[i].length + 3) / 4 * 4;
/*      */       }
/* 1139 */       checksum = 2981146554L - (checksum & 0xFFFFFFFF);
/*      */ 
/* 1141 */       tables[3][8] = ((byte)(int)(checksum >>> 24));
/* 1142 */       tables[3][9] = ((byte)(int)(checksum >>> 16));
/* 1143 */       tables[3][10] = ((byte)(int)(checksum >>> 8));
/* 1144 */       tables[3][11] = ((byte)(int)checksum);
/* 1145 */       for (int i = 0; i < tableNames.length; i++)
/*      */       {
/* 1147 */         writeTableBody(dos, tables[i]);
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/* 1152 */       dos.close();
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.ttf.TTFSubFont
 * JD-Core Version:    0.6.2
 */