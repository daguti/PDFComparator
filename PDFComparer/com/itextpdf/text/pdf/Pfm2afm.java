/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ public final class Pfm2afm
/*     */ {
/*     */   private RandomAccessFileOrArray in;
/*     */   private PrintWriter out;
/*     */   private short vers;
/*     */   private int h_len;
/*     */   private String copyright;
/*     */   private short type;
/*     */   private short points;
/*     */   private short verres;
/*     */   private short horres;
/*     */   private short ascent;
/*     */   private short intleading;
/*     */   private short extleading;
/*     */   private byte italic;
/*     */   private byte uline;
/*     */   private byte overs;
/*     */   private short weight;
/*     */   private byte charset;
/*     */   private short pixwidth;
/*     */   private short pixheight;
/*     */   private byte kind;
/*     */   private short avgwidth;
/*     */   private short maxwidth;
/*     */   private int firstchar;
/*     */   private int lastchar;
/*     */   private byte defchar;
/*     */   private byte brkchar;
/*     */   private short widthby;
/*     */   private int device;
/*     */   private int face;
/*     */   private int bits;
/*     */   private int bitoff;
/*     */   private short extlen;
/*     */   private int psext;
/*     */   private int chartab;
/*     */   private int res1;
/*     */   private int kernpairs;
/*     */   private int res2;
/*     */   private int fontname;
/*     */   private short capheight;
/*     */   private short xheight;
/*     */   private short ascender;
/*     */   private short descender;
/*     */   private boolean isMono;
/* 503 */   private int[] Win2PSStd = { 0, 0, 0, 0, 197, 198, 199, 0, 202, 0, 205, 206, 207, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 32, 33, 34, 35, 36, 37, 38, 169, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 193, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 0, 184, 166, 185, 188, 178, 179, 195, 189, 0, 172, 234, 0, 0, 0, 0, 96, 0, 170, 186, 183, 177, 208, 196, 0, 0, 173, 250, 0, 0, 0, 0, 161, 162, 163, 168, 165, 0, 167, 200, 0, 227, 171, 0, 0, 0, 197, 0, 0, 0, 0, 194, 0, 182, 180, 203, 0, 235, 187, 0, 0, 0, 191, 0, 0, 0, 0, 0, 0, 225, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 233, 0, 0, 0, 0, 0, 0, 251, 0, 0, 0, 0, 0, 0, 241, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 249, 0, 0, 0, 0, 0, 0, 0 };
/*     */ 
/* 527 */   private int[] WinClass = { 0, 0, 0, 0, 2, 2, 2, 0, 2, 0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 0, 0, 2, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 3, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
/*     */ 
/* 550 */   private String[] WinChars = { "W00", "W01", "W02", "W03", "macron", "breve", "dotaccent", "W07", "ring", "W09", "W0a", "W0b", "W0c", "W0d", "W0e", "W0f", "hungarumlaut", "ogonek", "caron", "W13", "W14", "W15", "W16", "W17", "W18", "W19", "W1a", "W1b", "W1c", "W1d", "W1e", "W1f", "space", "exclam", "quotedbl", "numbersign", "dollar", "percent", "ampersand", "quotesingle", "parenleft", "parenright", "asterisk", "plus", "comma", "hyphen", "period", "slash", "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "colon", "semicolon", "less", "equal", "greater", "question", "at", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "bracketleft", "backslash", "bracketright", "asciicircum", "underscore", "grave", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "braceleft", "bar", "braceright", "asciitilde", "W7f", "euro", "W81", "quotesinglbase", "florin", "quotedblbase", "ellipsis", "dagger", "daggerdbl", "circumflex", "perthousand", "Scaron", "guilsinglleft", "OE", "W8d", "Zcaron", "W8f", "W90", "quoteleft", "quoteright", "quotedblleft", "quotedblright", "bullet", "endash", "emdash", "tilde", "trademark", "scaron", "guilsinglright", "oe", "W9d", "zcaron", "Ydieresis", "reqspace", "exclamdown", "cent", "sterling", "currency", "yen", "brokenbar", "section", "dieresis", "copyright", "ordfeminine", "guillemotleft", "logicalnot", "syllable", "registered", "macron", "degree", "plusminus", "twosuperior", "threesuperior", "acute", "mu", "paragraph", "periodcentered", "cedilla", "onesuperior", "ordmasculine", "guillemotright", "onequarter", "onehalf", "threequarters", "questiondown", "Agrave", "Aacute", "Acircumflex", "Atilde", "Adieresis", "Aring", "AE", "Ccedilla", "Egrave", "Eacute", "Ecircumflex", "Edieresis", "Igrave", "Iacute", "Icircumflex", "Idieresis", "Eth", "Ntilde", "Ograve", "Oacute", "Ocircumflex", "Otilde", "Odieresis", "multiply", "Oslash", "Ugrave", "Uacute", "Ucircumflex", "Udieresis", "Yacute", "Thorn", "germandbls", "agrave", "aacute", "acircumflex", "atilde", "adieresis", "aring", "ae", "ccedilla", "egrave", "eacute", "ecircumflex", "edieresis", "igrave", "iacute", "icircumflex", "idieresis", "eth", "ntilde", "ograve", "oacute", "ocircumflex", "otilde", "odieresis", "divide", "oslash", "ugrave", "uacute", "ucircumflex", "udieresis", "yacute", "thorn", "ydieresis" };
/*     */ 
/*     */   private Pfm2afm(RandomAccessFileOrArray in, OutputStream out)
/*     */     throws IOException
/*     */   {
/* 156 */     this.in = in;
/* 157 */     this.out = new PrintWriter(new OutputStreamWriter(out, "ISO-8859-1"));
/*     */   }
/*     */ 
/*     */   public static void convert(RandomAccessFileOrArray in, OutputStream out)
/*     */     throws IOException
/*     */   {
/* 167 */     Pfm2afm p = new Pfm2afm(in, out);
/* 168 */     p.openpfm();
/* 169 */     p.putheader();
/* 170 */     p.putchartab();
/* 171 */     p.putkerntab();
/* 172 */     p.puttrailer();
/* 173 */     p.out.flush();
/*     */   }
/*     */ 
/*     */   private String readString(int n)
/*     */     throws IOException
/*     */   {
/* 192 */     byte[] b = new byte[n];
/* 193 */     this.in.readFully(b);
/*     */ 
/* 195 */     for (int k = 0; (k < b.length) && 
/* 196 */       (b[k] != 0); k++);
/* 199 */     return new String(b, 0, k, "ISO-8859-1");
/*     */   }
/*     */ 
/*     */   private String readString() throws IOException {
/* 203 */     StringBuffer buf = new StringBuffer();
/*     */     while (true) {
/* 205 */       int c = this.in.read();
/* 206 */       if (c <= 0)
/*     */         break;
/* 208 */       buf.append((char)c);
/*     */     }
/* 210 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   private void outval(int n) {
/* 214 */     this.out.print(' ');
/* 215 */     this.out.print(n);
/*     */   }
/*     */ 
/*     */   private void outchar(int code, int width, String name)
/*     */   {
/* 222 */     this.out.print("C ");
/* 223 */     outval(code);
/* 224 */     this.out.print(" ; WX ");
/* 225 */     outval(width);
/* 226 */     if (name != null) {
/* 227 */       this.out.print(" ; N ");
/* 228 */       this.out.print(name);
/*     */     }
/* 230 */     this.out.print(" ;\n");
/*     */   }
/*     */ 
/*     */   private void openpfm() throws IOException {
/* 234 */     this.in.seek(0L);
/* 235 */     this.vers = this.in.readShortLE();
/* 236 */     this.h_len = this.in.readIntLE();
/* 237 */     this.copyright = readString(60);
/* 238 */     this.type = this.in.readShortLE();
/* 239 */     this.points = this.in.readShortLE();
/* 240 */     this.verres = this.in.readShortLE();
/* 241 */     this.horres = this.in.readShortLE();
/* 242 */     this.ascent = this.in.readShortLE();
/* 243 */     this.intleading = this.in.readShortLE();
/* 244 */     this.extleading = this.in.readShortLE();
/* 245 */     this.italic = ((byte)this.in.read());
/* 246 */     this.uline = ((byte)this.in.read());
/* 247 */     this.overs = ((byte)this.in.read());
/* 248 */     this.weight = this.in.readShortLE();
/* 249 */     this.charset = ((byte)this.in.read());
/* 250 */     this.pixwidth = this.in.readShortLE();
/* 251 */     this.pixheight = this.in.readShortLE();
/* 252 */     this.kind = ((byte)this.in.read());
/* 253 */     this.avgwidth = this.in.readShortLE();
/* 254 */     this.maxwidth = this.in.readShortLE();
/* 255 */     this.firstchar = this.in.read();
/* 256 */     this.lastchar = this.in.read();
/* 257 */     this.defchar = ((byte)this.in.read());
/* 258 */     this.brkchar = ((byte)this.in.read());
/* 259 */     this.widthby = this.in.readShortLE();
/* 260 */     this.device = this.in.readIntLE();
/* 261 */     this.face = this.in.readIntLE();
/* 262 */     this.bits = this.in.readIntLE();
/* 263 */     this.bitoff = this.in.readIntLE();
/* 264 */     this.extlen = this.in.readShortLE();
/* 265 */     this.psext = this.in.readIntLE();
/* 266 */     this.chartab = this.in.readIntLE();
/* 267 */     this.res1 = this.in.readIntLE();
/* 268 */     this.kernpairs = this.in.readIntLE();
/* 269 */     this.res2 = this.in.readIntLE();
/* 270 */     this.fontname = this.in.readIntLE();
/* 271 */     if ((this.h_len != this.in.length()) || (this.extlen != 30) || (this.fontname < 75) || (this.fontname > 512))
/* 272 */       throw new IOException(MessageLocalization.getComposedMessage("not.a.valid.pfm.file", new Object[0]));
/* 273 */     this.in.seek(this.psext + 14);
/* 274 */     this.capheight = this.in.readShortLE();
/* 275 */     this.xheight = this.in.readShortLE();
/* 276 */     this.ascender = this.in.readShortLE();
/* 277 */     this.descender = this.in.readShortLE();
/*     */   }
/*     */ 
/*     */   private void putheader() throws IOException {
/* 281 */     this.out.print("StartFontMetrics 2.0\n");
/* 282 */     if (this.copyright.length() > 0)
/* 283 */       this.out.print("Comment " + this.copyright + '\n');
/* 284 */     this.out.print("FontName ");
/* 285 */     this.in.seek(this.fontname);
/* 286 */     String fname = readString();
/* 287 */     this.out.print(fname);
/* 288 */     this.out.print("\nEncodingScheme ");
/* 289 */     if (this.charset != 0)
/* 290 */       this.out.print("FontSpecific\n");
/*     */     else {
/* 292 */       this.out.print("AdobeStandardEncoding\n");
/*     */     }
/*     */ 
/* 298 */     this.out.print("FullName " + fname.replace('-', ' '));
/* 299 */     if (this.face != 0) {
/* 300 */       this.in.seek(this.face);
/* 301 */       this.out.print("\nFamilyName " + readString());
/*     */     }
/*     */ 
/* 304 */     this.out.print("\nWeight ");
/* 305 */     if ((this.weight > 475) || (fname.toLowerCase().indexOf("bold") >= 0))
/* 306 */       this.out.print("Bold");
/* 307 */     else if (((this.weight < 325) && (this.weight != 0)) || (fname.toLowerCase().indexOf("light") >= 0))
/* 308 */       this.out.print("Light");
/* 309 */     else if (fname.toLowerCase().indexOf("black") >= 0)
/* 310 */       this.out.print("Black");
/*     */     else {
/* 312 */       this.out.print("Medium");
/*     */     }
/* 314 */     this.out.print("\nItalicAngle ");
/* 315 */     if ((this.italic != 0) || (fname.toLowerCase().indexOf("italic") >= 0)) {
/* 316 */       this.out.print("-12.00");
/*     */     }
/*     */     else
/*     */     {
/* 320 */       this.out.print("0");
/*     */     }
/*     */ 
/* 326 */     this.out.print("\nIsFixedPitch ");
/* 327 */     if (((this.kind & 0x1) == 0) || (this.avgwidth == this.maxwidth))
/*     */     {
/* 329 */       this.out.print("true");
/* 330 */       this.isMono = true;
/*     */     }
/*     */     else {
/* 333 */       this.out.print("false");
/* 334 */       this.isMono = false;
/*     */     }
/*     */ 
/* 342 */     this.out.print("\nFontBBox");
/* 343 */     if (this.isMono)
/* 344 */       outval(-20);
/*     */     else
/* 346 */       outval(-100);
/* 347 */     outval(-(this.descender + 5));
/* 348 */     outval(this.maxwidth + 10);
/* 349 */     outval(this.ascent + 5);
/*     */ 
/* 354 */     this.out.print("\nCapHeight");
/* 355 */     outval(this.capheight);
/* 356 */     this.out.print("\nXHeight");
/* 357 */     outval(this.xheight);
/* 358 */     this.out.print("\nDescender");
/* 359 */     outval(-this.descender);
/* 360 */     this.out.print("\nAscender");
/* 361 */     outval(this.ascender);
/* 362 */     this.out.print('\n');
/*     */   }
/*     */ 
/*     */   private void putchartab() throws IOException {
/* 366 */     int count = this.lastchar - this.firstchar + 1;
/* 367 */     int[] ctabs = new int[count];
/* 368 */     this.in.seek(this.chartab);
/* 369 */     for (int k = 0; k < count; k++)
/* 370 */       ctabs[k] = this.in.readUnsignedShortLE();
/* 371 */     int[] back = new int[256];
/* 372 */     if (this.charset == 0) {
/* 373 */       for (int i = this.firstchar; i <= this.lastchar; i++) {
/* 374 */         if (this.Win2PSStd[i] != 0) {
/* 375 */           back[this.Win2PSStd[i]] = i;
/*     */         }
/*     */       }
/*     */     }
/* 379 */     this.out.print("StartCharMetrics");
/* 380 */     outval(count);
/* 381 */     this.out.print('\n');
/*     */ 
/* 384 */     if (this.charset != 0)
/*     */     {
/* 389 */       for (int i = this.firstchar; i <= this.lastchar; i++) {
/* 390 */         if (ctabs[(i - this.firstchar)] != 0)
/* 391 */           outchar(i, ctabs[(i - this.firstchar)], null);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 396 */       for (int i = 0; i < 256; i++) {
/* 397 */         int j = back[i];
/* 398 */         if (j != 0) {
/* 399 */           outchar(i, ctabs[(j - this.firstchar)], this.WinChars[j]);
/* 400 */           ctabs[(j - this.firstchar)] = 0;
/*     */         }
/*     */       }
/*     */ 
/* 404 */       for (int i = this.firstchar; i <= this.lastchar; i++) {
/* 405 */         if (ctabs[(i - this.firstchar)] != 0) {
/* 406 */           outchar(-1, ctabs[(i - this.firstchar)], this.WinChars[i]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 411 */     this.out.print("EndCharMetrics\n");
/*     */   }
/*     */ 
/*     */   private void putkerntab() throws IOException
/*     */   {
/* 416 */     if (this.kernpairs == 0)
/* 417 */       return;
/* 418 */     this.in.seek(this.kernpairs);
/* 419 */     int count = this.in.readUnsignedShortLE();
/* 420 */     int nzero = 0;
/* 421 */     int[] kerns = new int[count * 3];
/* 422 */     for (int k = 0; k < kerns.length; ) {
/* 423 */       kerns[(k++)] = this.in.read();
/* 424 */       kerns[(k++)] = this.in.read();
/* 425 */       if ((kerns[(k++)] = this.in.readShortLE()) != 0)
/* 426 */         nzero++;
/*     */     }
/* 428 */     if (nzero == 0)
/* 429 */       return;
/* 430 */     this.out.print("StartKernData\nStartKernPairs");
/* 431 */     outval(nzero);
/* 432 */     this.out.print('\n');
/* 433 */     for (int k = 0; k < kerns.length; k += 3) {
/* 434 */       if (kerns[(k + 2)] != 0) {
/* 435 */         this.out.print("KPX ");
/* 436 */         this.out.print(this.WinChars[kerns[k]]);
/* 437 */         this.out.print(' ');
/* 438 */         this.out.print(this.WinChars[kerns[(k + 1)]]);
/* 439 */         outval(kerns[(k + 2)]);
/* 440 */         this.out.print('\n');
/*     */       }
/*     */     }
/*     */ 
/* 444 */     this.out.print("EndKernPairs\nEndKernData\n");
/*     */   }
/*     */ 
/*     */   private void puttrailer()
/*     */   {
/* 449 */     this.out.print("EndFontMetrics\n");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.Pfm2afm
 * JD-Core Version:    0.6.2
 */