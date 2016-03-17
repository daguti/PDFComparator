/*     */ package org.apache.fontbox.ttf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ 
/*     */ public class CMAPEncodingEntry
/*     */ {
/*  39 */   private static final Log LOG = LogFactory.getLog(CMAPEncodingEntry.class);
/*     */   private int platformId;
/*     */   private int platformEncodingId;
/*     */   private long subTableOffset;
/*     */   private int[] glyphIdToCharacterCode;
/*     */   private Map<Integer, Integer> characterCodeToGlyphId;
/*     */ 
/*     */   public CMAPEncodingEntry()
/*     */   {
/*  45 */     this.characterCodeToGlyphId = new HashMap();
/*     */   }
/*     */ 
/*     */   public void initData(TTFDataStream data)
/*     */     throws IOException
/*     */   {
/*  55 */     this.platformId = data.readUnsignedShort();
/*  56 */     this.platformEncodingId = data.readUnsignedShort();
/*  57 */     this.subTableOffset = data.readUnsignedInt();
/*     */   }
/*     */ 
/*     */   public void initSubtable(CMAPTable cmap, int numGlyphs, TTFDataStream data)
/*     */     throws IOException
/*     */   {
/*  70 */     data.seek(cmap.getOffset() + this.subTableOffset);
/*  71 */     int subtableFormat = data.readUnsignedShort();
/*     */     long version;
/*     */     long version;
/*  74 */     if (subtableFormat < 8)
/*     */     {
/*  76 */       long length = data.readUnsignedShort();
/*  77 */       version = data.readUnsignedShort();
/*     */     }
/*     */     else
/*     */     {
/*  82 */       data.readUnsignedShort();
/*  83 */       long length = data.readUnsignedInt();
/*  84 */       version = data.readUnsignedInt();
/*     */     }
/*     */ 
/*  87 */     switch (subtableFormat)
/*     */     {
/*     */     case 0:
/*  90 */       processSubtype0(data);
/*  91 */       break;
/*     */     case 2:
/*  93 */       processSubtype2(data, numGlyphs);
/*  94 */       break;
/*     */     case 4:
/*  96 */       processSubtype4(data, numGlyphs);
/*  97 */       break;
/*     */     case 6:
/*  99 */       processSubtype6(data, numGlyphs);
/* 100 */       break;
/*     */     case 8:
/* 102 */       processSubtype8(data, numGlyphs);
/* 103 */       break;
/*     */     case 10:
/* 105 */       processSubtype10(data, numGlyphs);
/* 106 */       break;
/*     */     case 12:
/* 108 */       processSubtype12(data, numGlyphs);
/* 109 */       break;
/*     */     case 13:
/* 111 */       processSubtype13(data, numGlyphs);
/* 112 */       break;
/*     */     case 14:
/* 114 */       processSubtype14(data, numGlyphs);
/* 115 */       break;
/*     */     case 1:
/*     */     case 3:
/*     */     case 5:
/*     */     case 7:
/*     */     case 9:
/*     */     case 11:
/*     */     default:
/* 117 */       throw new IOException("Unknown cmap format:" + subtableFormat);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void processSubtype8(TTFDataStream data, int numGlyphs)
/*     */     throws IOException
/*     */   {
/* 131 */     int[] is32 = data.readUnsignedByteArray(8192);
/* 132 */     long nbGroups = data.readUnsignedInt();
/*     */ 
/* 135 */     if (nbGroups > 65536L) {
/* 136 */       throw new IOException("CMap ( Subtype8 ) is invalid");
/*     */     }
/*     */ 
/* 139 */     this.glyphIdToCharacterCode = new int[numGlyphs];
/*     */ 
/* 141 */     for (long i = 0L; i < nbGroups; i += 1L)
/*     */     {
/* 143 */       long firstCode = data.readUnsignedInt();
/* 144 */       long endCode = data.readUnsignedInt();
/* 145 */       long startGlyph = data.readUnsignedInt();
/*     */ 
/* 148 */       if ((firstCode > endCode) || (0L > firstCode)) {
/* 149 */         throw new IOException("Range invalid");
/*     */       }
/*     */ 
/* 152 */       for (long j = firstCode; j <= endCode; j += 1L)
/*     */       {
/* 154 */         if (j > 2147483647L)
/* 155 */           throw new IOException("[Sub Format 8] Invalid Character code");
/*     */         int currentCharCode;
/*     */         int currentCharCode;
/* 159 */         if ((is32[((int)j / 8)] & 1 << (int)j % 8) == 0) {
/* 160 */           currentCharCode = (int)j;
/*     */         }
/*     */         else
/*     */         {
/* 164 */           long LEAD_OFFSET = 55232L;
/* 165 */           long SURROGATE_OFFSET = -56613888L;
/* 166 */           long lead = LEAD_OFFSET + (j >> 10);
/* 167 */           long trail = 56320L + (j & 0x3FF);
/*     */ 
/* 169 */           long codepoint = (lead << 10) + trail + SURROGATE_OFFSET;
/* 170 */           if (codepoint > 2147483647L) {
/* 171 */             throw new IOException("[Sub Format 8] Invalid Character code");
/*     */           }
/* 173 */           currentCharCode = (int)codepoint;
/*     */         }
/*     */ 
/* 176 */         long glyphIndex = startGlyph + (j - firstCode);
/* 177 */         if ((glyphIndex > numGlyphs) || (glyphIndex > 2147483647L)) {
/* 178 */           throw new IOException("CMap contains an invalid glyph index");
/*     */         }
/*     */ 
/* 181 */         this.glyphIdToCharacterCode[((int)glyphIndex)] = currentCharCode;
/* 182 */         this.characterCodeToGlyphId.put(Integer.valueOf(currentCharCode), Integer.valueOf((int)glyphIndex));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void processSubtype10(TTFDataStream data, int numGlyphs)
/*     */     throws IOException
/*     */   {
/* 196 */     long startCode = data.readUnsignedInt();
/* 197 */     long numChars = data.readUnsignedInt();
/* 198 */     if (numChars > 2147483647L)
/*     */     {
/* 200 */       throw new IOException("Invalid number of Characters");
/*     */     }
/*     */ 
/* 203 */     if ((startCode < 0L) || (startCode > 1114111L) || (startCode + numChars > 1114111L) || ((startCode + numChars >= 55296L) && (startCode + numChars <= 57343L)))
/*     */     {
/* 206 */       throw new IOException("Invalid Characters codes");
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void processSubtype12(TTFDataStream data, int numGlyphs)
/*     */     throws IOException
/*     */   {
/* 220 */     long nbGroups = data.readUnsignedInt();
/* 221 */     this.glyphIdToCharacterCode = new int[numGlyphs];
/* 222 */     for (long i = 0L; i < nbGroups; i += 1L)
/*     */     {
/* 224 */       long firstCode = data.readUnsignedInt();
/* 225 */       long endCode = data.readUnsignedInt();
/* 226 */       long startGlyph = data.readUnsignedInt();
/*     */ 
/* 228 */       if ((firstCode < 0L) || (firstCode > 1114111L) || ((firstCode >= 55296L) && (firstCode <= 57343L)))
/*     */       {
/* 230 */         throw new IOException("Invalid Characters codes");
/*     */       }
/*     */ 
/* 233 */       if ((endCode > 0L) && ((endCode < firstCode) || (endCode > 1114111L) || ((endCode >= 55296L) && (endCode <= 57343L))))
/*     */       {
/* 235 */         throw new IOException("Invalid Characters codes");
/*     */       }
/*     */ 
/* 238 */       for (long j = 0L; j <= endCode - firstCode; j += 1L)
/*     */       {
/* 240 */         if (firstCode + j > 2147483647L) {
/* 241 */           throw new IOException("Character Code greater than Integer.MAX_VALUE");
/*     */         }
/*     */ 
/* 244 */         long glyphIndex = startGlyph + j;
/* 245 */         if ((glyphIndex > numGlyphs) || (glyphIndex > 2147483647L)) {
/* 246 */           throw new IOException("CMap contains an invalid glyph index");
/*     */         }
/* 248 */         this.glyphIdToCharacterCode[((int)glyphIndex)] = ((int)(firstCode + j));
/* 249 */         this.characterCodeToGlyphId.put(Integer.valueOf((int)(firstCode + j)), Integer.valueOf((int)glyphIndex));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void processSubtype13(TTFDataStream data, int numGlyphs)
/*     */     throws IOException
/*     */   {
/* 263 */     long nbGroups = data.readUnsignedInt();
/* 264 */     for (long i = 0L; i < nbGroups; i += 1L)
/*     */     {
/* 266 */       long firstCode = data.readUnsignedInt();
/* 267 */       long endCode = data.readUnsignedInt();
/* 268 */       long glyphId = data.readUnsignedInt();
/*     */ 
/* 270 */       if (glyphId > numGlyphs) {
/* 271 */         throw new IOException("CMap contains an invalid glyph index");
/*     */       }
/*     */ 
/* 274 */       if ((firstCode < 0L) || (firstCode > 1114111L) || ((firstCode >= 55296L) && (firstCode <= 57343L)))
/*     */       {
/* 276 */         throw new IOException("Invalid Characters codes");
/*     */       }
/*     */ 
/* 279 */       if ((endCode > 0L) && ((endCode < firstCode) || (endCode > 1114111L) || ((endCode >= 55296L) && (endCode <= 57343L))))
/*     */       {
/* 281 */         throw new IOException("Invalid Characters codes");
/*     */       }
/*     */ 
/* 284 */       for (long j = 0L; j <= endCode - firstCode; j += 1L)
/*     */       {
/* 286 */         if (firstCode + j > 2147483647L) {
/* 287 */           throw new IOException("Character Code greater than Integer.MAX_VALUE");
/*     */         }
/* 289 */         this.glyphIdToCharacterCode[((int)glyphId)] = ((int)(firstCode + j));
/* 290 */         this.characterCodeToGlyphId.put(Integer.valueOf((int)(firstCode + j)), Integer.valueOf((int)glyphId));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void processSubtype14(TTFDataStream data, int numGlyphs)
/*     */     throws IOException
/*     */   {
/* 304 */     throw new IOException("CMap subtype 14 not yet implemented");
/*     */   }
/*     */ 
/*     */   protected void processSubtype6(TTFDataStream data, int numGlyphs)
/*     */     throws IOException
/*     */   {
/* 316 */     int firstCode = data.readUnsignedShort();
/* 317 */     int entryCount = data.readUnsignedShort();
/* 318 */     this.glyphIdToCharacterCode = new int[numGlyphs];
/* 319 */     int[] glyphIdArray = data.readUnsignedShortArray(entryCount);
/* 320 */     for (int i = 0; i < entryCount; i++)
/*     */     {
/* 322 */       this.glyphIdToCharacterCode[glyphIdArray[i]] = (firstCode + i);
/* 323 */       this.characterCodeToGlyphId.put(Integer.valueOf(firstCode + i), Integer.valueOf(glyphIdArray[i]));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void processSubtype4(TTFDataStream data, int numGlyphs)
/*     */     throws IOException
/*     */   {
/* 336 */     int segCountX2 = data.readUnsignedShort();
/* 337 */     int segCount = segCountX2 / 2;
/* 338 */     int searchRange = data.readUnsignedShort();
/* 339 */     int entrySelector = data.readUnsignedShort();
/* 340 */     int rangeShift = data.readUnsignedShort();
/* 341 */     int[] endCount = data.readUnsignedShortArray(segCount);
/* 342 */     int reservedPad = data.readUnsignedShort();
/* 343 */     int[] startCount = data.readUnsignedShortArray(segCount);
/* 344 */     int[] idDelta = data.readUnsignedShortArray(segCount);
/* 345 */     int[] idRangeOffset = data.readUnsignedShortArray(segCount);
/*     */ 
/* 347 */     Map tmpGlyphToChar = new HashMap();
/*     */ 
/* 349 */     long currentPosition = data.getCurrentPosition();
/*     */ 
/* 351 */     for (int i = 0; i < segCount; i++)
/*     */     {
/* 353 */       int start = startCount[i];
/* 354 */       int end = endCount[i];
/* 355 */       int delta = idDelta[i];
/* 356 */       int rangeOffset = idRangeOffset[i];
/* 357 */       if ((start != 65535) && (end != 65535))
/*     */       {
/* 359 */         for (int j = start; j <= end; j++)
/*     */         {
/* 361 */           if (rangeOffset == 0)
/*     */           {
/* 363 */             int glyphid = (j + delta) % 65536;
/* 364 */             tmpGlyphToChar.put(Integer.valueOf(glyphid), Integer.valueOf(j));
/* 365 */             this.characterCodeToGlyphId.put(Integer.valueOf(j), Integer.valueOf(glyphid));
/*     */           }
/*     */           else
/*     */           {
/* 369 */             long glyphOffset = currentPosition + (rangeOffset / 2 + (j - start) + (i - segCount)) * 2;
/*     */ 
/* 373 */             data.seek(glyphOffset);
/* 374 */             int glyphIndex = data.readUnsignedShort();
/* 375 */             if (glyphIndex != 0)
/*     */             {
/* 377 */               glyphIndex += delta;
/* 378 */               glyphIndex %= 65536;
/* 379 */               if (!tmpGlyphToChar.containsKey(Integer.valueOf(glyphIndex)))
/*     */               {
/* 381 */                 tmpGlyphToChar.put(Integer.valueOf(glyphIndex), Integer.valueOf(j));
/* 382 */                 this.characterCodeToGlyphId.put(Integer.valueOf(j), Integer.valueOf(glyphIndex));
/*     */               }
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 394 */     if (tmpGlyphToChar.isEmpty())
/*     */     {
/* 396 */       LOG.warn("cmap format 4 subtable is empty");
/* 397 */       return;
/*     */     }
/* 399 */     this.glyphIdToCharacterCode = new int[((Integer)Collections.max(tmpGlyphToChar.keySet())).intValue() + 1];
/* 400 */     Arrays.fill(this.glyphIdToCharacterCode, 0);
/* 401 */     for (Map.Entry entry : tmpGlyphToChar.entrySet())
/*     */     {
/* 403 */       this.glyphIdToCharacterCode[((Integer)entry.getKey()).intValue()] = ((Integer)entry.getValue()).intValue();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void processSubtype2(TTFDataStream data, int numGlyphs)
/*     */     throws IOException
/*     */   {
/* 416 */     int[] subHeaderKeys = new int[256];
/*     */ 
/* 418 */     int maxSubHeaderIndex = 0;
/* 419 */     for (int i = 0; i < 256; i++)
/*     */     {
/* 421 */       subHeaderKeys[i] = data.readUnsignedShort();
/* 422 */       maxSubHeaderIndex = Math.max(maxSubHeaderIndex, subHeaderKeys[i] / 8);
/*     */     }
/*     */ 
/* 426 */     SubHeader[] subHeaders = new SubHeader[maxSubHeaderIndex + 1];
/* 427 */     for (int i = 0; i <= maxSubHeaderIndex; i++)
/*     */     {
/* 429 */       int firstCode = data.readUnsignedShort();
/* 430 */       int entryCount = data.readUnsignedShort();
/* 431 */       short idDelta = data.readSignedShort();
/* 432 */       int idRangeOffset = data.readUnsignedShort() - (maxSubHeaderIndex + 1 - i - 1) * 8 - 2;
/* 433 */       subHeaders[i] = new SubHeader(firstCode, entryCount, idDelta, idRangeOffset, null);
/*     */     }
/* 435 */     long startGlyphIndexOffset = data.getCurrentPosition();
/* 436 */     this.glyphIdToCharacterCode = new int[numGlyphs];
/* 437 */     for (int i = 0; i <= maxSubHeaderIndex; i++)
/*     */     {
/* 439 */       SubHeader sh = subHeaders[i];
/* 440 */       int firstCode = sh.getFirstCode();
/* 441 */       int idRangeOffset = sh.getIdRangeOffset();
/* 442 */       int idDelta = sh.getIdDelta();
/* 443 */       int entryCount = sh.getEntryCount();
/* 444 */       data.seek(startGlyphIndexOffset + idRangeOffset);
/* 445 */       for (int j = 0; j < entryCount; j++)
/*     */       {
/* 448 */         int charCode = i;
/* 449 */         charCode = (charCode << 8) + (firstCode + j);
/*     */ 
/* 455 */         int p = data.readUnsignedShort();
/*     */ 
/* 457 */         if (p > 0)
/*     */         {
/* 459 */           p = (p + idDelta) % 65536;
/*     */         }
/* 461 */         this.glyphIdToCharacterCode[p] = charCode;
/* 462 */         this.characterCodeToGlyphId.put(Integer.valueOf(charCode), Integer.valueOf(p));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void processSubtype0(TTFDataStream data)
/*     */     throws IOException
/*     */   {
/* 474 */     byte[] glyphMapping = data.read(256);
/* 475 */     this.glyphIdToCharacterCode = new int[256];
/* 476 */     for (int i = 0; i < glyphMapping.length; i++)
/*     */     {
/* 478 */       int glyphIndex = (glyphMapping[i] + 256) % 256;
/* 479 */       this.glyphIdToCharacterCode[glyphIndex] = i;
/* 480 */       this.characterCodeToGlyphId.put(Integer.valueOf(i), Integer.valueOf(glyphIndex));
/*     */     }
/*     */   }
/*     */ 
/*     */   public int[] getGlyphIdToCharacterCode()
/*     */   {
/* 489 */     return this.glyphIdToCharacterCode;
/*     */   }
/*     */ 
/*     */   public void setGlyphIdToCharacterCode(int[] glyphIdToCharacterCodeValue)
/*     */   {
/* 496 */     this.glyphIdToCharacterCode = glyphIdToCharacterCodeValue;
/*     */   }
/*     */ 
/*     */   public int getPlatformEncodingId()
/*     */   {
/* 504 */     return this.platformEncodingId;
/*     */   }
/*     */ 
/*     */   public void setPlatformEncodingId(int platformEncodingIdValue)
/*     */   {
/* 511 */     this.platformEncodingId = platformEncodingIdValue;
/*     */   }
/*     */ 
/*     */   public int getPlatformId()
/*     */   {
/* 518 */     return this.platformId;
/*     */   }
/*     */ 
/*     */   public void setPlatformId(int platformIdValue)
/*     */   {
/* 525 */     this.platformId = platformIdValue;
/*     */   }
/*     */ 
/*     */   public int getGlyphId(int characterCode)
/*     */   {
/* 535 */     Integer glyphId = (Integer)this.characterCodeToGlyphId.get(Integer.valueOf(characterCode));
/* 536 */     return glyphId == null ? 0 : glyphId.intValue();
/*     */   }
/*     */ 
/*     */   private class SubHeader
/*     */   {
/*     */     private final int firstCode;
/*     */     private final int entryCount;
/*     */     private final short idDelta;
/*     */     private final int idRangeOffset;
/*     */ 
/*     */     private SubHeader(int firstCode, int entryCount, short idDelta, int idRangeOffset)
/*     */     {
/* 560 */       this.firstCode = firstCode;
/* 561 */       this.entryCount = entryCount;
/* 562 */       this.idDelta = idDelta;
/* 563 */       this.idRangeOffset = idRangeOffset;
/*     */     }
/*     */ 
/*     */     private int getFirstCode()
/*     */     {
/* 571 */       return this.firstCode;
/*     */     }
/*     */ 
/*     */     private int getEntryCount()
/*     */     {
/* 579 */       return this.entryCount;
/*     */     }
/*     */ 
/*     */     private short getIdDelta()
/*     */     {
/* 587 */       return this.idDelta;
/*     */     }
/*     */ 
/*     */     private int getIdRangeOffset()
/*     */     {
/* 595 */       return this.idRangeOffset;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.ttf.CMAPEncodingEntry
 * JD-Core Version:    0.6.2
 */