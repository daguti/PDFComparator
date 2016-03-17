/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.Document;
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.io.StreamUtil;
/*     */ import com.itextpdf.text.pdf.fonts.FontsResourceAnchor;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ class Type1Font extends BaseFont
/*     */ {
/*     */   private static FontsResourceAnchor resourceAnchor;
/*     */   protected byte[] pfb;
/*     */   private String FontName;
/*     */   private String FullName;
/*     */   private String FamilyName;
/*  81 */   private String Weight = "";
/*     */ 
/*  84 */   private float ItalicAngle = 0.0F;
/*     */ 
/*  88 */   private boolean IsFixedPitch = false;
/*     */   private String CharacterSet;
/*  94 */   private int llx = -50;
/*     */ 
/*  97 */   private int lly = -200;
/*     */ 
/* 100 */   private int urx = 1000;
/*     */ 
/* 103 */   private int ury = 900;
/*     */ 
/* 106 */   private int UnderlinePosition = -100;
/*     */ 
/* 109 */   private int UnderlineThickness = 50;
/*     */ 
/* 115 */   private String EncodingScheme = "FontSpecific";
/*     */ 
/* 118 */   private int CapHeight = 700;
/*     */ 
/* 121 */   private int XHeight = 480;
/*     */ 
/* 124 */   private int Ascender = 800;
/*     */ 
/* 127 */   private int Descender = -200;
/*     */   private int StdHW;
/* 133 */   private int StdVW = 80;
/*     */ 
/* 140 */   private HashMap<Object, Object[]> CharMetrics = new HashMap();
/*     */ 
/* 147 */   private HashMap<String, Object[]> KernPairs = new HashMap();
/*     */   private String fileName;
/* 153 */   private boolean builtinFont = false;
/*     */ 
/* 157 */   private static final int[] PFB_TYPES = { 1, 2, 1 };
/*     */ 
/*     */   Type1Font(String afmFile, String enc, boolean emb, byte[] ttfAfm, byte[] pfb, boolean forceRead)
/*     */     throws DocumentException, IOException
/*     */   {
/* 171 */     if ((emb) && (ttfAfm != null) && (pfb == null))
/* 172 */       throw new DocumentException(MessageLocalization.getComposedMessage("two.byte.arrays.are.needed.if.the.type1.font.is.embedded", new Object[0]));
/* 173 */     if ((emb) && (ttfAfm != null))
/* 174 */       this.pfb = pfb;
/* 175 */     this.encoding = enc;
/* 176 */     this.embedded = emb;
/* 177 */     this.fileName = afmFile;
/* 178 */     this.fontType = 0;
/* 179 */     RandomAccessFileOrArray rf = null;
/* 180 */     InputStream is = null;
/* 181 */     if (BuiltinFonts14.containsKey(afmFile)) {
/* 182 */       this.embedded = false;
/* 183 */       this.builtinFont = true;
/* 184 */       byte[] buf = new byte[1024];
/*     */       try {
/* 186 */         if (resourceAnchor == null)
/* 187 */           resourceAnchor = new FontsResourceAnchor();
/* 188 */         is = StreamUtil.getResourceStream("com/itextpdf/text/pdf/fonts/" + afmFile + ".afm", resourceAnchor.getClass().getClassLoader());
/* 189 */         if (is == null) {
/* 190 */           String msg = MessageLocalization.getComposedMessage("1.not.found.as.resource", new Object[] { afmFile });
/* 191 */           System.err.println(msg);
/* 192 */           throw new DocumentException(msg);
/*     */         }
/* 194 */         ByteArrayOutputStream out = new ByteArrayOutputStream();
/*     */         while (true) {
/* 196 */           int size = is.read(buf);
/* 197 */           if (size < 0)
/*     */             break;
/* 199 */           out.write(buf, 0, size);
/*     */         }
/* 201 */         buf = out.toByteArray();
/*     */       }
/*     */       finally {
/* 204 */         if (is != null)
/*     */           try {
/* 206 */             is.close();
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/*     */           }
/*     */       }
/*     */       try
/*     */       {
/* 214 */         rf = new RandomAccessFileOrArray(buf);
/* 215 */         process(rf);
/*     */       }
/*     */       finally {
/* 218 */         if (rf != null) {
/*     */           try {
/* 220 */             rf.close();
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 228 */     else if (afmFile.toLowerCase().endsWith(".afm")) {
/*     */       try {
/* 230 */         if (ttfAfm == null)
/* 231 */           rf = new RandomAccessFileOrArray(afmFile, forceRead, Document.plainRandomAccess);
/*     */         else
/* 233 */           rf = new RandomAccessFileOrArray(ttfAfm);
/* 234 */         process(rf);
/*     */       }
/*     */       finally {
/* 237 */         if (rf != null) {
/*     */           try {
/* 239 */             rf.close();
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 247 */     else if (afmFile.toLowerCase().endsWith(".pfm")) {
/*     */       try {
/* 249 */         ByteArrayOutputStream ba = new ByteArrayOutputStream();
/* 250 */         if (ttfAfm == null)
/* 251 */           rf = new RandomAccessFileOrArray(afmFile, forceRead, Document.plainRandomAccess);
/*     */         else
/* 253 */           rf = new RandomAccessFileOrArray(ttfAfm);
/* 254 */         Pfm2afm.convert(rf, ba);
/* 255 */         rf.close();
/* 256 */         rf = new RandomAccessFileOrArray(ba.toByteArray());
/* 257 */         process(rf);
/*     */       }
/*     */       finally {
/* 260 */         if (rf != null)
/*     */           try {
/* 262 */             rf.close();
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/*     */           }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 271 */       throw new DocumentException(MessageLocalization.getComposedMessage("1.is.not.an.afm.or.pfm.font.file", new Object[] { afmFile }));
/*     */     }
/* 273 */     this.EncodingScheme = this.EncodingScheme.trim();
/* 274 */     if ((this.EncodingScheme.equals("AdobeStandardEncoding")) || (this.EncodingScheme.equals("StandardEncoding"))) {
/* 275 */       this.fontSpecific = false;
/*     */     }
/* 277 */     if (!this.encoding.startsWith("#"))
/* 278 */       PdfEncodings.convertToBytes(" ", enc);
/* 279 */     createEncoding();
/*     */   }
/*     */ 
/*     */   int getRawWidth(int c, String name)
/*     */   {
/*     */     Object[] metrics;
/*     */     Object[] metrics;
/* 292 */     if (name == null) {
/* 293 */       metrics = (Object[])this.CharMetrics.get(Integer.valueOf(c));
/*     */     }
/*     */     else {
/* 296 */       if (name.equals(".notdef"))
/* 297 */         return 0;
/* 298 */       metrics = (Object[])this.CharMetrics.get(name);
/*     */     }
/* 300 */     if (metrics != null)
/* 301 */       return ((Integer)metrics[1]).intValue();
/* 302 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getKerning(int char1, int char2)
/*     */   {
/* 315 */     String first = GlyphList.unicodeToName(char1);
/* 316 */     if (first == null)
/* 317 */       return 0;
/* 318 */     String second = GlyphList.unicodeToName(char2);
/* 319 */     if (second == null)
/* 320 */       return 0;
/* 321 */     Object[] obj = (Object[])this.KernPairs.get(first);
/* 322 */     if (obj == null)
/* 323 */       return 0;
/* 324 */     for (int k = 0; k < obj.length; k += 2) {
/* 325 */       if (second.equals(obj[k]))
/* 326 */         return ((Integer)obj[(k + 1)]).intValue();
/*     */     }
/* 328 */     return 0;
/*     */   }
/*     */ 
/*     */   public void process(RandomAccessFileOrArray rf)
/*     */     throws DocumentException, IOException
/*     */   {
/* 340 */     boolean isMetrics = false;
/*     */     String line;
/* 341 */     while ((line = rf.readLine()) != null)
/*     */     {
/* 343 */       StringTokenizer tok = new StringTokenizer(line, " ,\n\r\t\f");
/* 344 */       if (tok.hasMoreTokens())
/*     */       {
/* 346 */         String ident = tok.nextToken();
/* 347 */         if (ident.equals("FontName")) {
/* 348 */           this.FontName = tok.nextToken("ÿ").substring(1);
/* 349 */         } else if (ident.equals("FullName")) {
/* 350 */           this.FullName = tok.nextToken("ÿ").substring(1);
/* 351 */         } else if (ident.equals("FamilyName")) {
/* 352 */           this.FamilyName = tok.nextToken("ÿ").substring(1);
/* 353 */         } else if (ident.equals("Weight")) {
/* 354 */           this.Weight = tok.nextToken("ÿ").substring(1);
/* 355 */         } else if (ident.equals("ItalicAngle")) {
/* 356 */           this.ItalicAngle = Float.parseFloat(tok.nextToken());
/* 357 */         } else if (ident.equals("IsFixedPitch")) {
/* 358 */           this.IsFixedPitch = tok.nextToken().equals("true");
/* 359 */         } else if (ident.equals("CharacterSet")) {
/* 360 */           this.CharacterSet = tok.nextToken("ÿ").substring(1);
/* 361 */         } else if (ident.equals("FontBBox"))
/*     */         {
/* 363 */           this.llx = ((int)Float.parseFloat(tok.nextToken()));
/* 364 */           this.lly = ((int)Float.parseFloat(tok.nextToken()));
/* 365 */           this.urx = ((int)Float.parseFloat(tok.nextToken()));
/* 366 */           this.ury = ((int)Float.parseFloat(tok.nextToken()));
/*     */         }
/* 368 */         else if (ident.equals("UnderlinePosition")) {
/* 369 */           this.UnderlinePosition = ((int)Float.parseFloat(tok.nextToken()));
/* 370 */         } else if (ident.equals("UnderlineThickness")) {
/* 371 */           this.UnderlineThickness = ((int)Float.parseFloat(tok.nextToken()));
/* 372 */         } else if (ident.equals("EncodingScheme")) {
/* 373 */           this.EncodingScheme = tok.nextToken("ÿ").substring(1);
/* 374 */         } else if (ident.equals("CapHeight")) {
/* 375 */           this.CapHeight = ((int)Float.parseFloat(tok.nextToken()));
/* 376 */         } else if (ident.equals("XHeight")) {
/* 377 */           this.XHeight = ((int)Float.parseFloat(tok.nextToken()));
/* 378 */         } else if (ident.equals("Ascender")) {
/* 379 */           this.Ascender = ((int)Float.parseFloat(tok.nextToken()));
/* 380 */         } else if (ident.equals("Descender")) {
/* 381 */           this.Descender = ((int)Float.parseFloat(tok.nextToken()));
/* 382 */         } else if (ident.equals("StdHW")) {
/* 383 */           this.StdHW = ((int)Float.parseFloat(tok.nextToken()));
/* 384 */         } else if (ident.equals("StdVW")) {
/* 385 */           this.StdVW = ((int)Float.parseFloat(tok.nextToken()));
/* 386 */         } else if (ident.equals("StartCharMetrics"))
/*     */         {
/* 388 */           isMetrics = true;
/* 389 */           break;
/*     */         }
/*     */       }
/*     */     }
/* 392 */     if (!isMetrics)
/* 393 */       throw new DocumentException(MessageLocalization.getComposedMessage("missing.startcharmetrics.in.1", new Object[] { this.fileName }));
/* 394 */     while ((line = rf.readLine()) != null)
/*     */     {
/* 396 */       StringTokenizer tok = new StringTokenizer(line);
/* 397 */       if (tok.hasMoreTokens())
/*     */       {
/* 399 */         String ident = tok.nextToken();
/* 400 */         if (ident.equals("EndCharMetrics"))
/*     */         {
/* 402 */           isMetrics = false;
/* 403 */           break;
/*     */         }
/* 405 */         Integer C = Integer.valueOf(-1);
/* 406 */         Integer WX = Integer.valueOf(250);
/* 407 */         String N = "";
/* 408 */         int[] B = null;
/*     */ 
/* 410 */         tok = new StringTokenizer(line, ";");
/* 411 */         while (tok.hasMoreTokens())
/*     */         {
/* 413 */           StringTokenizer tokc = new StringTokenizer(tok.nextToken());
/* 414 */           if (tokc.hasMoreTokens())
/*     */           {
/* 416 */             ident = tokc.nextToken();
/* 417 */             if (ident.equals("C"))
/* 418 */               C = Integer.valueOf(tokc.nextToken());
/* 419 */             else if (ident.equals("WX"))
/* 420 */               WX = Integer.valueOf((int)Float.parseFloat(tokc.nextToken()));
/* 421 */             else if (ident.equals("N"))
/* 422 */               N = tokc.nextToken();
/* 423 */             else if (ident.equals("B")) {
/* 424 */               B = new int[] { Integer.parseInt(tokc.nextToken()), Integer.parseInt(tokc.nextToken()), Integer.parseInt(tokc.nextToken()), Integer.parseInt(tokc.nextToken()) };
/*     */             }
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 430 */         Object[] metrics = { C, WX, N, B };
/* 431 */         if (C.intValue() >= 0)
/* 432 */           this.CharMetrics.put(C, metrics);
/* 433 */         this.CharMetrics.put(N, metrics);
/*     */       }
/*     */     }
/* 435 */     if (isMetrics)
/* 436 */       throw new DocumentException(MessageLocalization.getComposedMessage("missing.endcharmetrics.in.1", new Object[] { this.fileName }));
/* 437 */     if (!this.CharMetrics.containsKey("nonbreakingspace")) {
/* 438 */       Object[] space = (Object[])this.CharMetrics.get("space");
/* 439 */       if (space != null)
/* 440 */         this.CharMetrics.put("nonbreakingspace", space);
/*     */     }
/* 442 */     while ((line = rf.readLine()) != null)
/*     */     {
/* 444 */       StringTokenizer tok = new StringTokenizer(line);
/* 445 */       if (tok.hasMoreTokens())
/*     */       {
/* 447 */         String ident = tok.nextToken();
/* 448 */         if (ident.equals("EndFontMetrics"))
/* 449 */           return;
/* 450 */         if (ident.equals("StartKernPairs"))
/*     */         {
/* 452 */           isMetrics = true;
/* 453 */           break;
/*     */         }
/*     */       }
/*     */     }
/* 456 */     if (!isMetrics)
/* 457 */       throw new DocumentException(MessageLocalization.getComposedMessage("missing.endfontmetrics.in.1", new Object[] { this.fileName }));
/* 458 */     while ((line = rf.readLine()) != null)
/*     */     {
/* 460 */       StringTokenizer tok = new StringTokenizer(line);
/* 461 */       if (tok.hasMoreTokens())
/*     */       {
/* 463 */         String ident = tok.nextToken();
/* 464 */         if (ident.equals("KPX"))
/*     */         {
/* 466 */           String first = tok.nextToken();
/* 467 */           String second = tok.nextToken();
/* 468 */           Integer width = Integer.valueOf((int)Float.parseFloat(tok.nextToken()));
/* 469 */           Object[] relates = (Object[])this.KernPairs.get(first);
/* 470 */           if (relates == null) {
/* 471 */             this.KernPairs.put(first, new Object[] { second, width });
/*     */           }
/*     */           else {
/* 474 */             int n = relates.length;
/* 475 */             Object[] relates2 = new Object[n + 2];
/* 476 */             System.arraycopy(relates, 0, relates2, 0, n);
/* 477 */             relates2[n] = second;
/* 478 */             relates2[(n + 1)] = width;
/* 479 */             this.KernPairs.put(first, relates2);
/*     */           }
/*     */         }
/* 482 */         else if (ident.equals("EndKernPairs"))
/*     */         {
/* 484 */           isMetrics = false;
/* 485 */           break;
/*     */         }
/*     */       }
/*     */     }
/* 488 */     if (isMetrics)
/* 489 */       throw new DocumentException(MessageLocalization.getComposedMessage("missing.endkernpairs.in.1", new Object[] { this.fileName }));
/* 490 */     rf.close();
/*     */   }
/*     */ 
/*     */   public PdfStream getFullFontStream()
/*     */     throws DocumentException
/*     */   {
/* 503 */     if ((this.builtinFont) || (!this.embedded))
/* 504 */       return null;
/* 505 */     RandomAccessFileOrArray rf = null;
/*     */     try {
/* 507 */       String filePfb = this.fileName.substring(0, this.fileName.length() - 3) + "pfb";
/* 508 */       if (this.pfb == null)
/* 509 */         rf = new RandomAccessFileOrArray(filePfb, true, Document.plainRandomAccess);
/*     */       else
/* 511 */         rf = new RandomAccessFileOrArray(this.pfb);
/* 512 */       int fileLength = (int)rf.length();
/* 513 */       byte[] st = new byte[fileLength - 18];
/* 514 */       int[] lengths = new int[3];
/* 515 */       int bytePtr = 0;
/* 516 */       for (int k = 0; k < 3; k++) {
/* 517 */         if (rf.read() != 128)
/* 518 */           throw new DocumentException(MessageLocalization.getComposedMessage("start.marker.missing.in.1", new Object[] { filePfb }));
/* 519 */         if (rf.read() != PFB_TYPES[k])
/* 520 */           throw new DocumentException(MessageLocalization.getComposedMessage("incorrect.segment.type.in.1", new Object[] { filePfb }));
/* 521 */         int size = rf.read();
/* 522 */         size += (rf.read() << 8);
/* 523 */         size += (rf.read() << 16);
/* 524 */         size += (rf.read() << 24);
/* 525 */         lengths[k] = size;
/* 526 */         while (size != 0) {
/* 527 */           int got = rf.read(st, bytePtr, size);
/* 528 */           if (got < 0)
/* 529 */             throw new DocumentException(MessageLocalization.getComposedMessage("premature.end.in.1", new Object[] { filePfb }));
/* 530 */           bytePtr += got;
/* 531 */           size -= got;
/*     */         }
/*     */       }
/* 534 */       return new BaseFont.StreamFont(st, lengths, this.compressionLevel);
/*     */     }
/*     */     catch (Exception e) {
/* 537 */       throw new DocumentException(e);
/*     */     }
/*     */     finally {
/* 540 */       if (rf != null)
/*     */         try {
/* 542 */           rf.close();
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   private PdfDictionary getFontDescriptor(PdfIndirectReference fontStream)
/*     */   {
/* 558 */     if (this.builtinFont)
/* 559 */       return null;
/* 560 */     PdfDictionary dic = new PdfDictionary(PdfName.FONTDESCRIPTOR);
/* 561 */     dic.put(PdfName.ASCENT, new PdfNumber(this.Ascender));
/* 562 */     dic.put(PdfName.CAPHEIGHT, new PdfNumber(this.CapHeight));
/* 563 */     dic.put(PdfName.DESCENT, new PdfNumber(this.Descender));
/* 564 */     dic.put(PdfName.FONTBBOX, new PdfRectangle(this.llx, this.lly, this.urx, this.ury));
/* 565 */     dic.put(PdfName.FONTNAME, new PdfName(this.FontName));
/* 566 */     dic.put(PdfName.ITALICANGLE, new PdfNumber(this.ItalicAngle));
/* 567 */     dic.put(PdfName.STEMV, new PdfNumber(this.StdVW));
/* 568 */     if (fontStream != null)
/* 569 */       dic.put(PdfName.FONTFILE, fontStream);
/* 570 */     int flags = 0;
/* 571 */     if (this.IsFixedPitch)
/* 572 */       flags |= 1;
/* 573 */     flags |= (this.fontSpecific ? 4 : 32);
/* 574 */     if (this.ItalicAngle < 0.0F)
/* 575 */       flags |= 64;
/* 576 */     if ((this.FontName.indexOf("Caps") >= 0) || (this.FontName.endsWith("SC")))
/* 577 */       flags |= 131072;
/* 578 */     if (this.Weight.equals("Bold"))
/* 579 */       flags |= 262144;
/* 580 */     dic.put(PdfName.FLAGS, new PdfNumber(flags));
/*     */ 
/* 582 */     return dic;
/*     */   }
/*     */ 
/*     */   private PdfDictionary getFontBaseType(PdfIndirectReference fontDescriptor, int firstChar, int lastChar, byte[] shortTag)
/*     */   {
/* 594 */     PdfDictionary dic = new PdfDictionary(PdfName.FONT);
/* 595 */     dic.put(PdfName.SUBTYPE, PdfName.TYPE1);
/* 596 */     dic.put(PdfName.BASEFONT, new PdfName(this.FontName));
/* 597 */     boolean stdEncoding = (this.encoding.equals("Cp1252")) || (this.encoding.equals("MacRoman"));
/* 598 */     if ((!this.fontSpecific) || (this.specialMap != null)) {
/* 599 */       for (int k = firstChar; k <= lastChar; k++) {
/* 600 */         if (!this.differences[k].equals(".notdef")) {
/* 601 */           firstChar = k;
/* 602 */           break;
/*     */         }
/*     */       }
/* 605 */       if (stdEncoding) {
/* 606 */         dic.put(PdfName.ENCODING, this.encoding.equals("Cp1252") ? PdfName.WIN_ANSI_ENCODING : PdfName.MAC_ROMAN_ENCODING);
/*     */       } else {
/* 608 */         PdfDictionary enc = new PdfDictionary(PdfName.ENCODING);
/* 609 */         PdfArray dif = new PdfArray();
/* 610 */         boolean gap = true;
/* 611 */         for (int k = firstChar; k <= lastChar; k++)
/* 612 */           if (shortTag[k] != 0) {
/* 613 */             if (gap) {
/* 614 */               dif.add(new PdfNumber(k));
/* 615 */               gap = false;
/*     */             }
/* 617 */             dif.add(new PdfName(this.differences[k]));
/*     */           }
/*     */           else {
/* 620 */             gap = true;
/*     */           }
/* 622 */         enc.put(PdfName.DIFFERENCES, dif);
/* 623 */         dic.put(PdfName.ENCODING, enc);
/*     */       }
/*     */     }
/* 626 */     if ((this.specialMap != null) || (this.forceWidthsOutput) || (!this.builtinFont) || ((!this.fontSpecific) && (!stdEncoding))) {
/* 627 */       dic.put(PdfName.FIRSTCHAR, new PdfNumber(firstChar));
/* 628 */       dic.put(PdfName.LASTCHAR, new PdfNumber(lastChar));
/* 629 */       PdfArray wd = new PdfArray();
/* 630 */       for (int k = firstChar; k <= lastChar; k++) {
/* 631 */         if (shortTag[k] == 0)
/* 632 */           wd.add(new PdfNumber(0));
/*     */         else
/* 634 */           wd.add(new PdfNumber(this.widths[k]));
/*     */       }
/* 636 */       dic.put(PdfName.WIDTHS, wd);
/*     */     }
/* 638 */     if ((!this.builtinFont) && (fontDescriptor != null))
/* 639 */       dic.put(PdfName.FONTDESCRIPTOR, fontDescriptor);
/* 640 */     return dic;
/*     */   }
/*     */ 
/*     */   void writeFont(PdfWriter writer, PdfIndirectReference ref, Object[] params)
/*     */     throws DocumentException, IOException
/*     */   {
/* 652 */     int firstChar = ((Integer)params[0]).intValue();
/* 653 */     int lastChar = ((Integer)params[1]).intValue();
/* 654 */     byte[] shortTag = (byte[])params[2];
/* 655 */     boolean subsetp = (((Boolean)params[3]).booleanValue()) && (this.subset);
/* 656 */     if (!subsetp) {
/* 657 */       firstChar = 0;
/* 658 */       lastChar = shortTag.length - 1;
/* 659 */       for (int k = 0; k < shortTag.length; k++)
/* 660 */         shortTag[k] = 1;
/*     */     }
/* 662 */     PdfIndirectReference ind_font = null;
/* 663 */     PdfObject pobj = null;
/* 664 */     PdfIndirectObject obj = null;
/* 665 */     pobj = getFullFontStream();
/* 666 */     if (pobj != null) {
/* 667 */       obj = writer.addToBody(pobj);
/* 668 */       ind_font = obj.getIndirectReference();
/*     */     }
/* 670 */     pobj = getFontDescriptor(ind_font);
/* 671 */     if (pobj != null) {
/* 672 */       obj = writer.addToBody(pobj);
/* 673 */       ind_font = obj.getIndirectReference();
/*     */     }
/* 675 */     pobj = getFontBaseType(ind_font, firstChar, lastChar, shortTag);
/* 676 */     writer.addToBody(pobj, ref);
/*     */   }
/*     */ 
/*     */   public float getFontDescriptor(int key, float fontSize)
/*     */   {
/* 689 */     switch (key) {
/*     */     case 1:
/*     */     case 9:
/* 692 */       return this.Ascender * fontSize / 1000.0F;
/*     */     case 2:
/* 694 */       return this.CapHeight * fontSize / 1000.0F;
/*     */     case 3:
/*     */     case 10:
/* 697 */       return this.Descender * fontSize / 1000.0F;
/*     */     case 4:
/* 699 */       return this.ItalicAngle;
/*     */     case 5:
/* 701 */       return this.llx * fontSize / 1000.0F;
/*     */     case 6:
/* 703 */       return this.lly * fontSize / 1000.0F;
/*     */     case 7:
/* 705 */       return this.urx * fontSize / 1000.0F;
/*     */     case 8:
/* 707 */       return this.ury * fontSize / 1000.0F;
/*     */     case 11:
/* 709 */       return 0.0F;
/*     */     case 12:
/* 711 */       return (this.urx - this.llx) * fontSize / 1000.0F;
/*     */     case 13:
/* 713 */       return this.UnderlinePosition * fontSize / 1000.0F;
/*     */     case 14:
/* 715 */       return this.UnderlineThickness * fontSize / 1000.0F;
/*     */     }
/* 717 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   public void setFontDescriptor(int key, float value)
/*     */   {
/* 730 */     switch (key) {
/*     */     case 1:
/*     */     case 9:
/* 733 */       this.Ascender = ((int)value);
/* 734 */       break;
/*     */     case 3:
/*     */     case 10:
/* 737 */       this.Descender = ((int)value);
/* 738 */       break;
/*     */     case 2:
/*     */     case 4:
/*     */     case 5:
/*     */     case 6:
/*     */     case 7:
/*     */     case 8:
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getPostscriptFontName() {
/* 749 */     return this.FontName;
/*     */   }
/*     */ 
/*     */   public String[][] getFullFontName()
/*     */   {
/* 762 */     return new String[][] { { "", "", "", this.FullName } };
/*     */   }
/*     */ 
/*     */   public String[][] getAllNameEntries()
/*     */   {
/* 775 */     return new String[][] { { "4", "", "", "", this.FullName } };
/*     */   }
/*     */ 
/*     */   public String[][] getFamilyFontName()
/*     */   {
/* 788 */     return new String[][] { { "", "", "", this.FamilyName } };
/*     */   }
/*     */ 
/*     */   public boolean hasKernPairs()
/*     */   {
/* 796 */     return !this.KernPairs.isEmpty();
/*     */   }
/*     */ 
/*     */   public void setPostscriptFontName(String name)
/*     */   {
/* 806 */     this.FontName = name;
/*     */   }
/*     */ 
/*     */   public boolean setKerning(int char1, int char2, int kern)
/*     */   {
/* 818 */     String first = GlyphList.unicodeToName(char1);
/* 819 */     if (first == null)
/* 820 */       return false;
/* 821 */     String second = GlyphList.unicodeToName(char2);
/* 822 */     if (second == null)
/* 823 */       return false;
/* 824 */     Object[] obj = (Object[])this.KernPairs.get(first);
/* 825 */     if (obj == null) {
/* 826 */       obj = new Object[] { second, Integer.valueOf(kern) };
/* 827 */       this.KernPairs.put(first, obj);
/* 828 */       return true;
/*     */     }
/* 830 */     for (int k = 0; k < obj.length; k += 2) {
/* 831 */       if (second.equals(obj[k])) {
/* 832 */         obj[(k + 1)] = Integer.valueOf(kern);
/* 833 */         return true;
/*     */       }
/*     */     }
/* 836 */     int size = obj.length;
/* 837 */     Object[] obj2 = new Object[size + 2];
/* 838 */     System.arraycopy(obj, 0, obj2, 0, size);
/* 839 */     obj2[size] = second;
/* 840 */     obj2[(size + 1)] = Integer.valueOf(kern);
/* 841 */     this.KernPairs.put(first, obj2);
/* 842 */     return true;
/*     */   }
/*     */ 
/*     */   protected int[] getRawCharBBox(int c, String name)
/*     */   {
/*     */     Object[] metrics;
/*     */     Object[] metrics;
/* 848 */     if (name == null) {
/* 849 */       metrics = (Object[])this.CharMetrics.get(Integer.valueOf(c));
/*     */     }
/*     */     else {
/* 852 */       if (name.equals(".notdef"))
/* 853 */         return null;
/* 854 */       metrics = (Object[])this.CharMetrics.get(name);
/*     */     }
/* 856 */     if (metrics != null)
/* 857 */       return (int[])metrics[3];
/* 858 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.Type1Font
 * JD-Core Version:    0.6.2
 */