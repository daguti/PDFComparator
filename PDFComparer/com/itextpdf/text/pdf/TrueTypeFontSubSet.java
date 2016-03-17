/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ 
/*     */ class TrueTypeFontSubSet
/*     */ {
/*  63 */   static final String[] tableNamesSimple = { "cvt ", "fpgm", "glyf", "head", "hhea", "hmtx", "loca", "maxp", "prep" };
/*     */ 
/*  65 */   static final String[] tableNamesCmap = { "cmap", "cvt ", "fpgm", "glyf", "head", "hhea", "hmtx", "loca", "maxp", "prep" };
/*     */ 
/*  67 */   static final String[] tableNamesExtra = { "OS/2", "cmap", "cvt ", "fpgm", "glyf", "head", "hhea", "hmtx", "loca", "maxp", "name, prep" };
/*     */ 
/*  69 */   static final int[] entrySelectors = { 0, 0, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4 };
/*     */   static final int TABLE_CHECKSUM = 0;
/*     */   static final int TABLE_OFFSET = 1;
/*     */   static final int TABLE_LENGTH = 2;
/*     */   static final int HEAD_LOCA_FORMAT_OFFSET = 51;
/*     */   static final int ARG_1_AND_2_ARE_WORDS = 1;
/*     */   static final int WE_HAVE_A_SCALE = 8;
/*     */   static final int MORE_COMPONENTS = 32;
/*     */   static final int WE_HAVE_AN_X_AND_Y_SCALE = 64;
/*     */   static final int WE_HAVE_A_TWO_BY_TWO = 128;
/*     */   protected HashMap<String, int[]> tableDirectory;
/*     */   protected RandomAccessFileOrArray rf;
/*     */   protected String fileName;
/*     */   protected boolean includeCmap;
/*     */   protected boolean includeExtras;
/*     */   protected boolean locaShortTable;
/*     */   protected int[] locaTable;
/*     */   protected HashSet<Integer> glyphsUsed;
/*     */   protected ArrayList<Integer> glyphsInList;
/*     */   protected int tableGlyphOffset;
/*     */   protected int[] newLocaTable;
/*     */   protected byte[] newLocaTableOut;
/*     */   protected byte[] newGlyfTable;
/*     */   protected int glyfTableRealSize;
/*     */   protected int locaTableRealSize;
/*     */   protected byte[] outFont;
/*     */   protected int fontPtr;
/*     */   protected int directoryOffset;
/*     */ 
/*     */   TrueTypeFontSubSet(String fileName, RandomAccessFileOrArray rf, HashSet<Integer> glyphsUsed, int directoryOffset, boolean includeCmap, boolean includeExtras)
/*     */   {
/* 117 */     this.fileName = fileName;
/* 118 */     this.rf = rf;
/* 119 */     this.glyphsUsed = glyphsUsed;
/* 120 */     this.includeCmap = includeCmap;
/* 121 */     this.includeExtras = includeExtras;
/* 122 */     this.directoryOffset = directoryOffset;
/* 123 */     this.glyphsInList = new ArrayList(glyphsUsed);
/*     */   }
/*     */ 
/*     */   byte[] process()
/*     */     throws IOException, DocumentException
/*     */   {
/*     */     try
/*     */     {
/* 133 */       this.rf.reOpen();
/* 134 */       createTableDirectory();
/* 135 */       readLoca();
/* 136 */       flatGlyphs();
/* 137 */       createNewGlyphTables();
/* 138 */       locaTobytes();
/* 139 */       assembleFont();
/* 140 */       return this.outFont;
/*     */     }
/*     */     finally {
/*     */       try {
/* 144 */         this.rf.close();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void assembleFont() throws IOException
/*     */   {
/* 154 */     int fullFontSize = 0;
/*     */     String[] tableNames;
/*     */     String[] tableNames;
/* 156 */     if (this.includeExtras) {
/* 157 */       tableNames = tableNamesExtra;
/*     */     }
/*     */     else
/*     */     {
/*     */       String[] tableNames;
/* 159 */       if (this.includeCmap)
/* 160 */         tableNames = tableNamesCmap;
/*     */       else
/* 162 */         tableNames = tableNamesSimple;
/*     */     }
/* 164 */     int tablesUsed = 2;
/* 165 */     int len = 0;
/* 166 */     for (int k = 0; k < tableNames.length; k++) {
/* 167 */       String name = tableNames[k];
/* 168 */       if ((!name.equals("glyf")) && (!name.equals("loca")))
/*     */       {
/* 170 */         int[] tableLocation = (int[])this.tableDirectory.get(name);
/* 171 */         if (tableLocation != null)
/*     */         {
/* 173 */           tablesUsed++;
/* 174 */           fullFontSize += (tableLocation[2] + 3 & 0xFFFFFFFC);
/*     */         }
/*     */       }
/*     */     }
/* 176 */     fullFontSize += this.newLocaTableOut.length;
/* 177 */     fullFontSize += this.newGlyfTable.length;
/* 178 */     int ref = 16 * tablesUsed + 12;
/* 179 */     fullFontSize += ref;
/* 180 */     this.outFont = new byte[fullFontSize];
/* 181 */     this.fontPtr = 0;
/* 182 */     writeFontInt(65536);
/* 183 */     writeFontShort(tablesUsed);
/* 184 */     int selector = entrySelectors[tablesUsed];
/* 185 */     writeFontShort((1 << selector) * 16);
/* 186 */     writeFontShort(selector);
/* 187 */     writeFontShort((tablesUsed - (1 << selector)) * 16);
/* 188 */     for (int k = 0; k < tableNames.length; k++) {
/* 189 */       String name = tableNames[k];
/* 190 */       int[] tableLocation = (int[])this.tableDirectory.get(name);
/* 191 */       if (tableLocation != null)
/*     */       {
/* 193 */         writeFontString(name);
/* 194 */         if (name.equals("glyf")) {
/* 195 */           writeFontInt(calculateChecksum(this.newGlyfTable));
/* 196 */           len = this.glyfTableRealSize;
/*     */         }
/* 198 */         else if (name.equals("loca")) {
/* 199 */           writeFontInt(calculateChecksum(this.newLocaTableOut));
/* 200 */           len = this.locaTableRealSize;
/*     */         }
/*     */         else {
/* 203 */           writeFontInt(tableLocation[0]);
/* 204 */           len = tableLocation[2];
/*     */         }
/* 206 */         writeFontInt(ref);
/* 207 */         writeFontInt(len);
/* 208 */         ref += (len + 3 & 0xFFFFFFFC);
/*     */       }
/*     */     }
/* 210 */     for (int k = 0; k < tableNames.length; k++) {
/* 211 */       String name = tableNames[k];
/* 212 */       int[] tableLocation = (int[])this.tableDirectory.get(name);
/* 213 */       if (tableLocation != null)
/*     */       {
/* 215 */         if (name.equals("glyf")) {
/* 216 */           System.arraycopy(this.newGlyfTable, 0, this.outFont, this.fontPtr, this.newGlyfTable.length);
/* 217 */           this.fontPtr += this.newGlyfTable.length;
/* 218 */           this.newGlyfTable = null;
/*     */         }
/* 220 */         else if (name.equals("loca")) {
/* 221 */           System.arraycopy(this.newLocaTableOut, 0, this.outFont, this.fontPtr, this.newLocaTableOut.length);
/* 222 */           this.fontPtr += this.newLocaTableOut.length;
/* 223 */           this.newLocaTableOut = null;
/*     */         }
/*     */         else {
/* 226 */           this.rf.seek(tableLocation[1]);
/* 227 */           this.rf.readFully(this.outFont, this.fontPtr, tableLocation[2]);
/* 228 */           this.fontPtr += (tableLocation[2] + 3 & 0xFFFFFFFC);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/* 234 */   protected void createTableDirectory() throws IOException, DocumentException { this.tableDirectory = new HashMap();
/* 235 */     this.rf.seek(this.directoryOffset);
/* 236 */     int id = this.rf.readInt();
/* 237 */     if (id != 65536)
/* 238 */       throw new DocumentException(MessageLocalization.getComposedMessage("1.is.not.a.true.type.file", new Object[] { this.fileName }));
/* 239 */     int num_tables = this.rf.readUnsignedShort();
/* 240 */     this.rf.skipBytes(6);
/* 241 */     for (int k = 0; k < num_tables; k++) {
/* 242 */       String tag = readStandardString(4);
/* 243 */       int[] tableLocation = new int[3];
/* 244 */       tableLocation[0] = this.rf.readInt();
/* 245 */       tableLocation[1] = this.rf.readInt();
/* 246 */       tableLocation[2] = this.rf.readInt();
/* 247 */       this.tableDirectory.put(tag, tableLocation);
/*     */     } }
/*     */ 
/*     */   protected void readLoca()
/*     */     throws IOException, DocumentException
/*     */   {
/* 253 */     int[] tableLocation = (int[])this.tableDirectory.get("head");
/* 254 */     if (tableLocation == null)
/* 255 */       throw new DocumentException(MessageLocalization.getComposedMessage("table.1.does.not.exist.in.2", new Object[] { "head", this.fileName }));
/* 256 */     this.rf.seek(tableLocation[1] + 51);
/* 257 */     this.locaShortTable = (this.rf.readUnsignedShort() == 0);
/* 258 */     tableLocation = (int[])this.tableDirectory.get("loca");
/* 259 */     if (tableLocation == null)
/* 260 */       throw new DocumentException(MessageLocalization.getComposedMessage("table.1.does.not.exist.in.2", new Object[] { "loca", this.fileName }));
/* 261 */     this.rf.seek(tableLocation[1]);
/* 262 */     if (this.locaShortTable) {
/* 263 */       int entries = tableLocation[2] / 2;
/* 264 */       this.locaTable = new int[entries];
/* 265 */       for (int k = 0; k < entries; k++)
/* 266 */         this.locaTable[k] = (this.rf.readUnsignedShort() * 2);
/*     */     }
/*     */     else {
/* 269 */       int entries = tableLocation[2] / 4;
/* 270 */       this.locaTable = new int[entries];
/* 271 */       for (int k = 0; k < entries; k++)
/* 272 */         this.locaTable[k] = this.rf.readInt();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void createNewGlyphTables() throws IOException {
/* 277 */     this.newLocaTable = new int[this.locaTable.length];
/* 278 */     int[] activeGlyphs = new int[this.glyphsInList.size()];
/* 279 */     for (int k = 0; k < activeGlyphs.length; k++)
/* 280 */       activeGlyphs[k] = ((Integer)this.glyphsInList.get(k)).intValue();
/* 281 */     Arrays.sort(activeGlyphs);
/* 282 */     int glyfSize = 0;
/* 283 */     for (int k = 0; k < activeGlyphs.length; k++) {
/* 284 */       int glyph = activeGlyphs[k];
/* 285 */       glyfSize += this.locaTable[(glyph + 1)] - this.locaTable[glyph];
/*     */     }
/* 287 */     this.glyfTableRealSize = glyfSize;
/* 288 */     glyfSize = glyfSize + 3 & 0xFFFFFFFC;
/* 289 */     this.newGlyfTable = new byte[glyfSize];
/* 290 */     int glyfPtr = 0;
/* 291 */     int listGlyf = 0;
/* 292 */     for (int k = 0; k < this.newLocaTable.length; k++) {
/* 293 */       this.newLocaTable[k] = glyfPtr;
/* 294 */       if ((listGlyf < activeGlyphs.length) && (activeGlyphs[listGlyf] == k)) {
/* 295 */         listGlyf++;
/* 296 */         this.newLocaTable[k] = glyfPtr;
/* 297 */         int start = this.locaTable[k];
/* 298 */         int len = this.locaTable[(k + 1)] - start;
/* 299 */         if (len > 0) {
/* 300 */           this.rf.seek(this.tableGlyphOffset + start);
/* 301 */           this.rf.readFully(this.newGlyfTable, glyfPtr, len);
/* 302 */           glyfPtr += len;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void locaTobytes() {
/* 309 */     if (this.locaShortTable)
/* 310 */       this.locaTableRealSize = (this.newLocaTable.length * 2);
/*     */     else
/* 312 */       this.locaTableRealSize = (this.newLocaTable.length * 4);
/* 313 */     this.newLocaTableOut = new byte[this.locaTableRealSize + 3 & 0xFFFFFFFC];
/* 314 */     this.outFont = this.newLocaTableOut;
/* 315 */     this.fontPtr = 0;
/* 316 */     for (int k = 0; k < this.newLocaTable.length; k++)
/* 317 */       if (this.locaShortTable)
/* 318 */         writeFontShort(this.newLocaTable[k] / 2);
/*     */       else
/* 320 */         writeFontInt(this.newLocaTable[k]);
/*     */   }
/*     */ 
/*     */   protected void flatGlyphs()
/*     */     throws IOException, DocumentException
/*     */   {
/* 327 */     int[] tableLocation = (int[])this.tableDirectory.get("glyf");
/* 328 */     if (tableLocation == null)
/* 329 */       throw new DocumentException(MessageLocalization.getComposedMessage("table.1.does.not.exist.in.2", new Object[] { "glyf", this.fileName }));
/* 330 */     Integer glyph0 = Integer.valueOf(0);
/* 331 */     if (!this.glyphsUsed.contains(glyph0)) {
/* 332 */       this.glyphsUsed.add(glyph0);
/* 333 */       this.glyphsInList.add(glyph0);
/*     */     }
/* 335 */     this.tableGlyphOffset = tableLocation[1];
/* 336 */     for (int k = 0; k < this.glyphsInList.size(); k++) {
/* 337 */       int glyph = ((Integer)this.glyphsInList.get(k)).intValue();
/* 338 */       checkGlyphComposite(glyph);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void checkGlyphComposite(int glyph) throws IOException {
/* 343 */     int start = this.locaTable[glyph];
/* 344 */     if (start == this.locaTable[(glyph + 1)])
/* 345 */       return;
/* 346 */     this.rf.seek(this.tableGlyphOffset + start);
/* 347 */     int numContours = this.rf.readShort();
/* 348 */     if (numContours >= 0)
/* 349 */       return;
/* 350 */     this.rf.skipBytes(8);
/*     */     while (true) {
/* 352 */       int flags = this.rf.readUnsignedShort();
/* 353 */       Integer cGlyph = Integer.valueOf(this.rf.readUnsignedShort());
/* 354 */       if (!this.glyphsUsed.contains(cGlyph)) {
/* 355 */         this.glyphsUsed.add(cGlyph);
/* 356 */         this.glyphsInList.add(cGlyph);
/*     */       }
/* 358 */       if ((flags & 0x20) == 0)
/* 359 */         return;
/*     */       int skip;
/*     */       int skip;
/* 361 */       if ((flags & 0x1) != 0)
/* 362 */         skip = 4;
/*     */       else
/* 364 */         skip = 2;
/* 365 */       if ((flags & 0x8) != 0)
/* 366 */         skip += 2;
/* 367 */       else if ((flags & 0x40) != 0)
/* 368 */         skip += 4;
/* 369 */       if ((flags & 0x80) != 0)
/* 370 */         skip += 8;
/* 371 */       this.rf.skipBytes(skip);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected String readStandardString(int length)
/*     */     throws IOException
/*     */   {
/* 382 */     byte[] buf = new byte[length];
/* 383 */     this.rf.readFully(buf);
/*     */     try {
/* 385 */       return new String(buf, "Cp1252");
/*     */     }
/*     */     catch (Exception e) {
/* 388 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void writeFontShort(int n) {
/* 393 */     this.outFont[(this.fontPtr++)] = ((byte)(n >> 8));
/* 394 */     this.outFont[(this.fontPtr++)] = ((byte)n);
/*     */   }
/*     */ 
/*     */   protected void writeFontInt(int n) {
/* 398 */     this.outFont[(this.fontPtr++)] = ((byte)(n >> 24));
/* 399 */     this.outFont[(this.fontPtr++)] = ((byte)(n >> 16));
/* 400 */     this.outFont[(this.fontPtr++)] = ((byte)(n >> 8));
/* 401 */     this.outFont[(this.fontPtr++)] = ((byte)n);
/*     */   }
/*     */ 
/*     */   protected void writeFontString(String s) {
/* 405 */     byte[] b = PdfEncodings.convertToBytes(s, "Cp1252");
/* 406 */     System.arraycopy(b, 0, this.outFont, this.fontPtr, b.length);
/* 407 */     this.fontPtr += b.length;
/*     */   }
/*     */ 
/*     */   protected int calculateChecksum(byte[] b) {
/* 411 */     int len = b.length / 4;
/* 412 */     int v0 = 0;
/* 413 */     int v1 = 0;
/* 414 */     int v2 = 0;
/* 415 */     int v3 = 0;
/* 416 */     int ptr = 0;
/* 417 */     for (int k = 0; k < len; k++) {
/* 418 */       v3 += (b[(ptr++)] & 0xFF);
/* 419 */       v2 += (b[(ptr++)] & 0xFF);
/* 420 */       v1 += (b[(ptr++)] & 0xFF);
/* 421 */       v0 += (b[(ptr++)] & 0xFF);
/*     */     }
/* 423 */     return v0 + (v1 << 8) + (v2 << 16) + (v3 << 24);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.TrueTypeFontSubSet
 * JD-Core Version:    0.6.2
 */