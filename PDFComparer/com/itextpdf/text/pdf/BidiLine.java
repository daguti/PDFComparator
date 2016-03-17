/*      */ package com.itextpdf.text.pdf;
/*      */ 
/*      */ import com.itextpdf.text.Chunk;
/*      */ import com.itextpdf.text.Image;
/*      */ import com.itextpdf.text.TabStop;
/*      */ import com.itextpdf.text.TabStop.Alignment;
/*      */ import com.itextpdf.text.Utilities;
/*      */ import com.itextpdf.text.pdf.draw.DrawInterface;
/*      */ import com.itextpdf.text.pdf.draw.LineSeparator;
/*      */ import com.itextpdf.text.pdf.languages.ArabicLigaturizer;
/*      */ import java.util.ArrayList;
/*      */ 
/*      */ public class BidiLine
/*      */ {
/*      */   protected int runDirection;
/*   64 */   protected int pieceSize = 256;
/*   65 */   protected char[] text = new char[this.pieceSize];
/*   66 */   protected PdfChunk[] detailChunks = new PdfChunk[this.pieceSize];
/*   67 */   protected int totalTextLength = 0;
/*      */ 
/*   69 */   protected byte[] orderLevels = new byte[this.pieceSize];
/*   70 */   protected int[] indexChars = new int[this.pieceSize];
/*      */ 
/*   72 */   protected ArrayList<PdfChunk> chunks = new ArrayList();
/*   73 */   protected int indexChunk = 0;
/*   74 */   protected int indexChunkChar = 0;
/*   75 */   protected int currentChar = 0;
/*      */   protected int storedRunDirection;
/*   78 */   protected char[] storedText = new char[0];
/*   79 */   protected PdfChunk[] storedDetailChunks = new PdfChunk[0];
/*   80 */   protected int storedTotalTextLength = 0;
/*      */ 
/*   82 */   protected byte[] storedOrderLevels = new byte[0];
/*   83 */   protected int[] storedIndexChars = new int[0];
/*      */ 
/*   85 */   protected int storedIndexChunk = 0;
/*   86 */   protected int storedIndexChunkChar = 0;
/*   87 */   protected int storedCurrentChar = 0;
/*      */   protected boolean shortStore;
/*   91 */   protected static final IntHashtable mirrorChars = new IntHashtable();
/*      */   protected int arabicOptions;
/*      */ 
/*      */   public BidiLine()
/*      */   {
/*      */   }
/*      */ 
/*      */   public BidiLine(BidiLine org)
/*      */   {
/*   99 */     this.runDirection = org.runDirection;
/*  100 */     this.pieceSize = org.pieceSize;
/*  101 */     this.text = ((char[])org.text.clone());
/*  102 */     this.detailChunks = ((PdfChunk[])org.detailChunks.clone());
/*  103 */     this.totalTextLength = org.totalTextLength;
/*      */ 
/*  105 */     this.orderLevels = ((byte[])org.orderLevels.clone());
/*  106 */     this.indexChars = ((int[])org.indexChars.clone());
/*      */ 
/*  108 */     this.chunks = new ArrayList(org.chunks);
/*  109 */     this.indexChunk = org.indexChunk;
/*  110 */     this.indexChunkChar = org.indexChunkChar;
/*  111 */     this.currentChar = org.currentChar;
/*      */ 
/*  113 */     this.storedRunDirection = org.storedRunDirection;
/*  114 */     this.storedText = ((char[])org.storedText.clone());
/*  115 */     this.storedDetailChunks = ((PdfChunk[])org.storedDetailChunks.clone());
/*  116 */     this.storedTotalTextLength = org.storedTotalTextLength;
/*      */ 
/*  118 */     this.storedOrderLevels = ((byte[])org.storedOrderLevels.clone());
/*  119 */     this.storedIndexChars = ((int[])org.storedIndexChars.clone());
/*      */ 
/*  121 */     this.storedIndexChunk = org.storedIndexChunk;
/*  122 */     this.storedIndexChunkChar = org.storedIndexChunkChar;
/*  123 */     this.storedCurrentChar = org.storedCurrentChar;
/*      */ 
/*  125 */     this.shortStore = org.shortStore;
/*  126 */     this.arabicOptions = org.arabicOptions;
/*      */   }
/*      */ 
/*      */   public boolean isEmpty() {
/*  130 */     return (this.currentChar >= this.totalTextLength) && (this.indexChunk >= this.chunks.size());
/*      */   }
/*      */ 
/*      */   public void clearChunks() {
/*  134 */     this.chunks.clear();
/*  135 */     this.totalTextLength = 0;
/*  136 */     this.currentChar = 0;
/*      */   }
/*      */ 
/*      */   public boolean getParagraph(int runDirection) {
/*  140 */     this.runDirection = runDirection;
/*  141 */     this.currentChar = 0;
/*  142 */     this.totalTextLength = 0;
/*      */ 
/*  144 */     boolean hasText = false;
/*      */ 
/*  148 */     for (; this.indexChunk < this.chunks.size(); this.indexChunk += 1) {
/*  149 */       PdfChunk ck = (PdfChunk)this.chunks.get(this.indexChunk);
/*  150 */       BaseFont bf = ck.font().getFont();
/*  151 */       String s = ck.toString();
/*  152 */       int len = s.length();
/*  153 */       for (; this.indexChunkChar < len; this.indexChunkChar += 1) {
/*  154 */         char c = s.charAt(this.indexChunkChar);
/*  155 */         char uniC = (char)bf.getUnicodeEquivalent(c);
/*  156 */         if ((uniC == '\r') || (uniC == '\n'))
/*      */         {
/*  158 */           if ((uniC == '\r') && (this.indexChunkChar + 1 < len) && (s.charAt(this.indexChunkChar + 1) == '\n'))
/*  159 */             this.indexChunkChar += 1;
/*  160 */           this.indexChunkChar += 1;
/*  161 */           if (this.indexChunkChar >= len) {
/*  162 */             this.indexChunkChar = 0;
/*  163 */             this.indexChunk += 1;
/*      */           }
/*  165 */           hasText = true;
/*  166 */           if (this.totalTextLength != 0) break;
/*  167 */           this.detailChunks[0] = ck; break;
/*      */         }
/*      */ 
/*  170 */         addPiece(c, ck);
/*      */       }
/*  172 */       if (hasText)
/*      */         break;
/*  174 */       this.indexChunkChar = 0;
/*      */     }
/*  176 */     if (this.totalTextLength == 0) {
/*  177 */       return hasText;
/*      */     }
/*      */ 
/*  180 */     this.totalTextLength = (trimRight(0, this.totalTextLength - 1) + 1);
/*  181 */     if (this.totalTextLength == 0) {
/*  182 */       return true;
/*      */     }
/*      */ 
/*  185 */     if ((runDirection == 2) || (runDirection == 3)) {
/*  186 */       if (this.orderLevels.length < this.totalTextLength) {
/*  187 */         this.orderLevels = new byte[this.pieceSize];
/*  188 */         this.indexChars = new int[this.pieceSize];
/*      */       }
/*  190 */       ArabicLigaturizer.processNumbers(this.text, 0, this.totalTextLength, this.arabicOptions);
/*  191 */       BidiOrder order = new BidiOrder(this.text, 0, this.totalTextLength, (byte)(runDirection == 3 ? 1 : 0));
/*  192 */       byte[] od = order.getLevels();
/*  193 */       for (int k = 0; k < this.totalTextLength; k++) {
/*  194 */         this.orderLevels[k] = od[k];
/*  195 */         this.indexChars[k] = k;
/*      */       }
/*  197 */       doArabicShapping();
/*  198 */       mirrorGlyphs();
/*      */     }
/*  200 */     this.totalTextLength = (trimRightEx(0, this.totalTextLength - 1) + 1);
/*  201 */     return true;
/*      */   }
/*      */ 
/*      */   public void addChunk(PdfChunk chunk) {
/*  205 */     this.chunks.add(chunk);
/*      */   }
/*      */ 
/*      */   public void addChunks(ArrayList<PdfChunk> chunks) {
/*  209 */     this.chunks.addAll(chunks);
/*      */   }
/*      */ 
/*      */   public void addPiece(char c, PdfChunk chunk) {
/*  213 */     if (this.totalTextLength >= this.pieceSize) {
/*  214 */       char[] tempText = this.text;
/*  215 */       PdfChunk[] tempDetailChunks = this.detailChunks;
/*  216 */       this.pieceSize *= 2;
/*  217 */       this.text = new char[this.pieceSize];
/*  218 */       this.detailChunks = new PdfChunk[this.pieceSize];
/*  219 */       System.arraycopy(tempText, 0, this.text, 0, this.totalTextLength);
/*  220 */       System.arraycopy(tempDetailChunks, 0, this.detailChunks, 0, this.totalTextLength);
/*      */     }
/*  222 */     this.text[this.totalTextLength] = c;
/*  223 */     this.detailChunks[(this.totalTextLength++)] = chunk;
/*      */   }
/*      */ 
/*      */   public void save() {
/*  227 */     if (this.indexChunk > 0) {
/*  228 */       if (this.indexChunk >= this.chunks.size())
/*  229 */         this.chunks.clear();
/*      */       else {
/*  231 */         for (this.indexChunk -= 1; this.indexChunk >= 0; this.indexChunk -= 1)
/*  232 */           this.chunks.remove(this.indexChunk);
/*      */       }
/*  234 */       this.indexChunk = 0;
/*      */     }
/*  236 */     this.storedRunDirection = this.runDirection;
/*  237 */     this.storedTotalTextLength = this.totalTextLength;
/*  238 */     this.storedIndexChunk = this.indexChunk;
/*  239 */     this.storedIndexChunkChar = this.indexChunkChar;
/*  240 */     this.storedCurrentChar = this.currentChar;
/*  241 */     this.shortStore = (this.currentChar < this.totalTextLength);
/*  242 */     if (!this.shortStore)
/*      */     {
/*  244 */       if (this.storedText.length < this.totalTextLength) {
/*  245 */         this.storedText = new char[this.totalTextLength];
/*  246 */         this.storedDetailChunks = new PdfChunk[this.totalTextLength];
/*      */       }
/*  248 */       System.arraycopy(this.text, 0, this.storedText, 0, this.totalTextLength);
/*  249 */       System.arraycopy(this.detailChunks, 0, this.storedDetailChunks, 0, this.totalTextLength);
/*      */     }
/*  251 */     if ((this.runDirection == 2) || (this.runDirection == 3)) {
/*  252 */       if (this.storedOrderLevels.length < this.totalTextLength) {
/*  253 */         this.storedOrderLevels = new byte[this.totalTextLength];
/*  254 */         this.storedIndexChars = new int[this.totalTextLength];
/*      */       }
/*  256 */       System.arraycopy(this.orderLevels, this.currentChar, this.storedOrderLevels, this.currentChar, this.totalTextLength - this.currentChar);
/*  257 */       System.arraycopy(this.indexChars, this.currentChar, this.storedIndexChars, this.currentChar, this.totalTextLength - this.currentChar);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void restore() {
/*  262 */     this.runDirection = this.storedRunDirection;
/*  263 */     this.totalTextLength = this.storedTotalTextLength;
/*  264 */     this.indexChunk = this.storedIndexChunk;
/*  265 */     this.indexChunkChar = this.storedIndexChunkChar;
/*  266 */     this.currentChar = this.storedCurrentChar;
/*  267 */     if (!this.shortStore)
/*      */     {
/*  269 */       System.arraycopy(this.storedText, 0, this.text, 0, this.totalTextLength);
/*  270 */       System.arraycopy(this.storedDetailChunks, 0, this.detailChunks, 0, this.totalTextLength);
/*      */     }
/*  272 */     if ((this.runDirection == 2) || (this.runDirection == 3)) {
/*  273 */       System.arraycopy(this.storedOrderLevels, this.currentChar, this.orderLevels, this.currentChar, this.totalTextLength - this.currentChar);
/*  274 */       System.arraycopy(this.storedIndexChars, this.currentChar, this.indexChars, this.currentChar, this.totalTextLength - this.currentChar);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void mirrorGlyphs() {
/*  279 */     for (int k = 0; k < this.totalTextLength; k++)
/*  280 */       if ((this.orderLevels[k] & 0x1) == 1) {
/*  281 */         int mirror = mirrorChars.get(this.text[k]);
/*  282 */         if (mirror != 0)
/*  283 */           this.text[k] = ((char)mirror);
/*      */       }
/*      */   }
/*      */ 
/*      */   public void doArabicShapping()
/*      */   {
/*  289 */     int src = 0;
/*  290 */     int dest = 0;
/*      */     while (true)
/*  292 */       if (src < this.totalTextLength) {
/*  293 */         char c = this.text[src];
/*  294 */         if ((c < '؀') || (c > 'ۿ'))
/*      */         {
/*  296 */           if (src != dest) {
/*  297 */             this.text[dest] = this.text[src];
/*  298 */             this.detailChunks[dest] = this.detailChunks[src];
/*  299 */             this.orderLevels[dest] = this.orderLevels[src];
/*      */           }
/*  301 */           src++;
/*  302 */           dest++;
/*      */         }
/*      */       } else { if (src >= this.totalTextLength) {
/*  305 */           this.totalTextLength = dest;
/*  306 */           return;
/*      */         }
/*  308 */         int startArabicIdx = src;
/*  309 */         src++;
/*  310 */         while (src < this.totalTextLength) {
/*  311 */           char c = this.text[src];
/*  312 */           if ((c < '؀') || (c > 'ۿ'))
/*      */             break;
/*  314 */           src++;
/*      */         }
/*  316 */         int arabicWordSize = src - startArabicIdx;
/*  317 */         int size = ArabicLigaturizer.arabic_shape(this.text, startArabicIdx, arabicWordSize, this.text, dest, arabicWordSize, this.arabicOptions);
/*  318 */         if (startArabicIdx != dest) {
/*  319 */           for (int k = 0; k < size; k++) {
/*  320 */             this.detailChunks[dest] = this.detailChunks[startArabicIdx];
/*  321 */             this.orderLevels[(dest++)] = this.orderLevels[(startArabicIdx++)];
/*      */           }
/*      */         }
/*      */         else
/*  325 */           dest += size; }
/*      */   }
/*      */ 
/*      */   public PdfLine processLine(float leftX, float width, int alignment, int runDirection, int arabicOptions, float minY, float yLine, float descender)
/*      */   {
/*  330 */     this.arabicOptions = arabicOptions;
/*  331 */     save();
/*  332 */     boolean isRTL = runDirection == 3;
/*  333 */     if (this.currentChar >= this.totalTextLength) {
/*  334 */       boolean hasText = getParagraph(runDirection);
/*  335 */       if (!hasText)
/*  336 */         return null;
/*  337 */       if (this.totalTextLength == 0) {
/*  338 */         ArrayList ar = new ArrayList();
/*  339 */         PdfChunk ck = new PdfChunk("", this.detailChunks[0]);
/*  340 */         ar.add(ck);
/*  341 */         return new PdfLine(0.0F, 0.0F, width, alignment, true, ar, isRTL);
/*      */       }
/*      */     }
/*  344 */     float originalWidth = width;
/*  345 */     int lastSplit = -1;
/*  346 */     if (this.currentChar != 0) {
/*  347 */       this.currentChar = trimLeftEx(this.currentChar, this.totalTextLength - 1);
/*      */     }
/*  349 */     int oldCurrentChar = this.currentChar;
/*  350 */     int uniC = 0;
/*  351 */     PdfChunk ck = null;
/*  352 */     float charWidth = 0.0F;
/*  353 */     PdfChunk lastValidChunk = null;
/*  354 */     TabStop tabStop = null;
/*  355 */     float tabStopAnchorPosition = (0.0F / 0.0F);
/*  356 */     float tabPosition = (0.0F / 0.0F);
/*  357 */     boolean surrogate = false;
/*  358 */     for (; this.currentChar < this.totalTextLength; this.currentChar += 1) {
/*  359 */       ck = this.detailChunks[this.currentChar];
/*  360 */       if ((ck.isImage()) && (minY < yLine)) {
/*  361 */         Image img = ck.getImage();
/*  362 */         if ((img.isScaleToFitHeight()) && (yLine + 2.0F * descender - img.getScaledHeight() - ck.getImageOffsetY() - img.getSpacingBefore() < minY)) {
/*  363 */           float scalePercent = (yLine + 2.0F * descender - ck.getImageOffsetY() - img.getSpacingBefore() - minY) / img.getScaledHeight();
/*  364 */           ck.setImageScalePercentage(scalePercent);
/*      */         }
/*      */       }
/*  367 */       surrogate = Utilities.isSurrogatePair(this.text, this.currentChar);
/*  368 */       if (surrogate)
/*  369 */         uniC = ck.getUnicodeEquivalent(Utilities.convertToUtf32(this.text, this.currentChar));
/*      */       else
/*  371 */         uniC = ck.getUnicodeEquivalent(this.text[this.currentChar]);
/*  372 */       if (!PdfChunk.noPrint(uniC))
/*      */       {
/*  374 */         if (surrogate) {
/*  375 */           charWidth = ck.getCharWidth(uniC);
/*      */         }
/*  377 */         else if (ck.isImage())
/*  378 */           charWidth = ck.getImageWidth();
/*      */         else {
/*  380 */           charWidth = ck.getCharWidth(this.text[this.currentChar]);
/*      */         }
/*      */ 
/*  383 */         if (width - charWidth < 0.0F)
/*      */         {
/*  386 */           if ((lastValidChunk == null) && (ck.isImage())) {
/*  387 */             Image img = ck.getImage();
/*  388 */             if (img.isScaleToFitLineWhenOverflow())
/*      */             {
/*  391 */               float scalePercent = width / img.getWidth();
/*  392 */               ck.setImageScalePercentage(scalePercent);
/*  393 */               charWidth = width;
/*      */             }
/*      */           }
/*      */         }
/*  397 */         if (ck.isTab()) {
/*  398 */           if (ck.isAttribute("TABSETTINGS")) {
/*  399 */             lastSplit = this.currentChar;
/*  400 */             if (tabStop != null) {
/*  401 */               float tabStopPosition = tabStop.getPosition(tabPosition, originalWidth - width, tabStopAnchorPosition);
/*  402 */               width = originalWidth - (tabStopPosition + (originalWidth - width - tabPosition));
/*  403 */               if (width < 0.0F) {
/*  404 */                 tabStopPosition += width;
/*  405 */                 width = 0.0F;
/*      */               }
/*  407 */               tabStop.setPosition(tabStopPosition);
/*      */             }
/*      */ 
/*  410 */             tabStop = PdfChunk.getTabStop(ck, originalWidth - width);
/*  411 */             if (tabStop.getPosition() > originalWidth) {
/*  412 */               tabStop = null;
/*  413 */               break;
/*      */             }
/*  415 */             ck.setTabStop(tabStop);
/*  416 */             if (tabStop.getAlignment() == TabStop.Alignment.LEFT) {
/*  417 */               width = originalWidth - tabStop.getPosition();
/*  418 */               tabStop = null;
/*  419 */               tabPosition = (0.0F / 0.0F);
/*  420 */               tabStopAnchorPosition = (0.0F / 0.0F);
/*      */             } else {
/*  422 */               tabPosition = originalWidth - width;
/*  423 */               tabStopAnchorPosition = (0.0F / 0.0F);
/*      */             }
/*      */           } else {
/*  426 */             Object[] tab = (Object[])ck.getAttribute("TAB");
/*      */ 
/*  428 */             float tabStopPosition = ((Float)tab[1]).floatValue();
/*  429 */             boolean newLine = ((Boolean)tab[2]).booleanValue();
/*  430 */             if ((newLine) && (tabStopPosition < originalWidth - width)) {
/*  431 */               return new PdfLine(0.0F, originalWidth, width, alignment, true, createArrayOfPdfChunks(oldCurrentChar, this.currentChar - 1), isRTL);
/*      */             }
/*  433 */             this.detailChunks[this.currentChar].adjustLeft(leftX);
/*  434 */             width = originalWidth - tabStopPosition;
/*      */           }
/*      */         }
/*  437 */         else if (ck.isSeparator()) {
/*  438 */           Object[] sep = (Object[])ck.getAttribute("SEPARATOR");
/*  439 */           DrawInterface di = (DrawInterface)sep[0];
/*  440 */           Boolean vertical = (Boolean)sep[1];
/*  441 */           if ((vertical.booleanValue()) && ((di instanceof LineSeparator))) {
/*  442 */             float separatorWidth = originalWidth * ((LineSeparator)di).getPercentage() / 100.0F;
/*  443 */             width -= separatorWidth;
/*  444 */             if (width < 0.0F)
/*  445 */               width = 0.0F;
/*      */           }
/*      */         }
/*      */         else {
/*  449 */           boolean splitChar = ck.isExtSplitCharacter(oldCurrentChar, this.currentChar, this.totalTextLength, this.text, this.detailChunks);
/*  450 */           if ((splitChar) && (Character.isWhitespace((char)uniC)))
/*  451 */             lastSplit = this.currentChar;
/*  452 */           if (width - charWidth < 0.0F)
/*      */             break;
/*  454 */           if ((tabStop != null) && (tabStop.getAlignment() == TabStop.Alignment.ANCHOR) && (Float.isNaN(tabStopAnchorPosition)) && (tabStop.getAnchorChar() == (char)uniC)) {
/*  455 */             tabStopAnchorPosition = originalWidth - width;
/*      */           }
/*  457 */           width -= charWidth;
/*  458 */           if (splitChar)
/*  459 */             lastSplit = this.currentChar;
/*      */         }
/*  461 */         lastValidChunk = ck;
/*  462 */         if (surrogate)
/*  463 */           this.currentChar += 1; 
/*      */       }
/*      */     }
/*  465 */     if (lastValidChunk == null)
/*      */     {
/*  467 */       this.currentChar += 1;
/*  468 */       if (surrogate)
/*  469 */         this.currentChar += 1;
/*  470 */       return new PdfLine(0.0F, originalWidth, 0.0F, alignment, false, createArrayOfPdfChunks(this.currentChar - 1, this.currentChar - 1), isRTL);
/*      */     }
/*      */ 
/*  473 */     if (tabStop != null) {
/*  474 */       float tabStopPosition = tabStop.getPosition(tabPosition, originalWidth - width, tabStopAnchorPosition);
/*  475 */       width = originalWidth - (tabStopPosition + (originalWidth - width - tabPosition));
/*  476 */       if (width < 0.0F) {
/*  477 */         tabStopPosition += width;
/*  478 */         width = 0.0F;
/*      */       }
/*  480 */       tabStop.setPosition(tabStopPosition);
/*      */     }
/*      */ 
/*  483 */     if (this.currentChar >= this.totalTextLength)
/*      */     {
/*  485 */       return new PdfLine(0.0F, originalWidth, width, alignment, true, createArrayOfPdfChunks(oldCurrentChar, this.totalTextLength - 1), isRTL);
/*      */     }
/*  487 */     int newCurrentChar = trimRightEx(oldCurrentChar, this.currentChar - 1);
/*  488 */     if (newCurrentChar < oldCurrentChar)
/*      */     {
/*  490 */       return new PdfLine(0.0F, originalWidth, width, alignment, false, createArrayOfPdfChunks(oldCurrentChar, this.currentChar - 1), isRTL);
/*      */     }
/*  492 */     if (newCurrentChar == this.currentChar - 1) {
/*  493 */       HyphenationEvent he = (HyphenationEvent)lastValidChunk.getAttribute("HYPHENATION");
/*  494 */       if (he != null) {
/*  495 */         int[] word = getWord(oldCurrentChar, newCurrentChar);
/*  496 */         if (word != null) {
/*  497 */           float testWidth = width + getWidth(word[0], this.currentChar - 1);
/*  498 */           String pre = he.getHyphenatedWordPre(new String(this.text, word[0], word[1] - word[0]), lastValidChunk.font().getFont(), lastValidChunk.font().size(), testWidth);
/*  499 */           String post = he.getHyphenatedWordPost();
/*  500 */           if (pre.length() > 0) {
/*  501 */             PdfChunk extra = new PdfChunk(pre, lastValidChunk);
/*  502 */             this.currentChar = (word[1] - post.length());
/*  503 */             return new PdfLine(0.0F, originalWidth, testWidth - lastValidChunk.width(pre), alignment, false, createArrayOfPdfChunks(oldCurrentChar, word[0] - 1, extra), isRTL);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  508 */     if ((lastSplit == -1) || (lastSplit >= newCurrentChar))
/*      */     {
/*  510 */       return new PdfLine(0.0F, originalWidth, width + getWidth(newCurrentChar + 1, this.currentChar - 1), alignment, false, createArrayOfPdfChunks(oldCurrentChar, newCurrentChar), isRTL);
/*      */     }
/*      */ 
/*  513 */     this.currentChar = (lastSplit + 1);
/*  514 */     newCurrentChar = trimRightEx(oldCurrentChar, lastSplit);
/*  515 */     if (newCurrentChar < oldCurrentChar)
/*      */     {
/*  517 */       newCurrentChar = this.currentChar - 1;
/*      */     }
/*  519 */     return new PdfLine(0.0F, originalWidth, originalWidth - getWidth(oldCurrentChar, newCurrentChar), alignment, false, createArrayOfPdfChunks(oldCurrentChar, newCurrentChar), isRTL);
/*      */   }
/*      */ 
/*      */   public float getWidth(int startIdx, int lastIdx)
/*      */   {
/*  528 */     char c = '\000';
/*  529 */     PdfChunk ck = null;
/*  530 */     float width = 0.0F;
/*  531 */     TabStop tabStop = null;
/*  532 */     float tabStopAnchorPosition = (0.0F / 0.0F);
/*  533 */     float tabPosition = (0.0F / 0.0F);
/*  534 */     for (; startIdx <= lastIdx; startIdx++) {
/*  535 */       boolean surrogate = Utilities.isSurrogatePair(this.text, startIdx);
/*  536 */       if ((this.detailChunks[startIdx].isTab()) && (this.detailChunks[startIdx].isAttribute("TABSETTINGS")))
/*      */       {
/*  539 */         if (tabStop != null) {
/*  540 */           float tabStopPosition = tabStop.getPosition(tabPosition, width, tabStopAnchorPosition);
/*  541 */           width = tabStopPosition + (width - tabPosition);
/*  542 */           tabStop.setPosition(tabStopPosition);
/*      */         }
/*  544 */         tabStop = this.detailChunks[startIdx].getTabStop();
/*  545 */         if (tabStop == null) {
/*  546 */           tabStop = PdfChunk.getTabStop(this.detailChunks[startIdx], width);
/*  547 */           tabPosition = width;
/*  548 */           tabStopAnchorPosition = (0.0F / 0.0F);
/*      */         } else {
/*  550 */           width = tabStop.getPosition();
/*  551 */           tabStop = null;
/*  552 */           tabPosition = (0.0F / 0.0F);
/*  553 */           tabStopAnchorPosition = (0.0F / 0.0F);
/*      */         }
/*  555 */       } else if (surrogate) {
/*  556 */         width += this.detailChunks[startIdx].getCharWidth(Utilities.convertToUtf32(this.text, startIdx));
/*  557 */         startIdx++;
/*      */       }
/*      */       else {
/*  560 */         c = this.text[startIdx];
/*  561 */         ck = this.detailChunks[startIdx];
/*  562 */         if (!PdfChunk.noPrint(ck.getUnicodeEquivalent(c)))
/*      */         {
/*  564 */           if ((tabStop != null) && (tabStop.getAlignment() != TabStop.Alignment.ANCHOR) && (Float.isNaN(tabStopAnchorPosition)) && (tabStop.getAnchorChar() == (char)ck.getUnicodeEquivalent(c))) {
/*  565 */             tabStopAnchorPosition = width;
/*      */           }
/*  567 */           width += this.detailChunks[startIdx].getCharWidth(c);
/*      */         }
/*      */       }
/*      */     }
/*  570 */     if (tabStop != null) {
/*  571 */       float tabStopPosition = tabStop.getPosition(tabPosition, width, tabStopAnchorPosition);
/*  572 */       width = tabStopPosition + (width - tabPosition);
/*  573 */       tabStop.setPosition(tabStopPosition);
/*      */     }
/*  575 */     return width;
/*      */   }
/*      */ 
/*      */   public ArrayList<PdfChunk> createArrayOfPdfChunks(int startIdx, int endIdx) {
/*  579 */     return createArrayOfPdfChunks(startIdx, endIdx, null);
/*      */   }
/*      */ 
/*      */   public ArrayList<PdfChunk> createArrayOfPdfChunks(int startIdx, int endIdx, PdfChunk extraPdfChunk) {
/*  583 */     boolean bidi = (this.runDirection == 2) || (this.runDirection == 3);
/*  584 */     if (bidi) {
/*  585 */       reorder(startIdx, endIdx);
/*      */     }
/*  587 */     ArrayList ar = new ArrayList();
/*  588 */     PdfChunk refCk = this.detailChunks[startIdx];
/*  589 */     PdfChunk ck = null;
/*  590 */     StringBuffer buf = new StringBuffer();
/*      */ 
/*  592 */     int idx = 0;
/*  593 */     for (; startIdx <= endIdx; startIdx++) {
/*  594 */       idx = bidi ? this.indexChars[startIdx] : startIdx;
/*  595 */       char c = this.text[idx];
/*  596 */       ck = this.detailChunks[idx];
/*  597 */       if (!PdfChunk.noPrint(ck.getUnicodeEquivalent(c)))
/*      */       {
/*  599 */         if ((ck.isImage()) || (ck.isSeparator()) || (ck.isTab())) {
/*  600 */           if (buf.length() > 0) {
/*  601 */             ar.add(new PdfChunk(buf.toString(), refCk));
/*  602 */             buf = new StringBuffer();
/*      */           }
/*  604 */           ar.add(ck);
/*      */         }
/*  606 */         else if (ck == refCk) {
/*  607 */           buf.append(c);
/*      */         }
/*      */         else {
/*  610 */           if (buf.length() > 0) {
/*  611 */             ar.add(new PdfChunk(buf.toString(), refCk));
/*  612 */             buf = new StringBuffer();
/*      */           }
/*  614 */           if ((!ck.isImage()) && (!ck.isSeparator()) && (!ck.isTab()))
/*  615 */             buf.append(c);
/*  616 */           refCk = ck;
/*      */         }
/*      */       }
/*      */     }
/*  619 */     if (buf.length() > 0) {
/*  620 */       ar.add(new PdfChunk(buf.toString(), refCk));
/*      */     }
/*  622 */     if (extraPdfChunk != null)
/*  623 */       ar.add(extraPdfChunk);
/*  624 */     return ar;
/*      */   }
/*      */ 
/*      */   public int[] getWord(int startIdx, int idx) {
/*  628 */     int last = idx;
/*  629 */     int first = idx;
/*      */ 
/*  631 */     while ((last < this.totalTextLength) && 
/*  632 */       (Character.isLetter(this.text[last]))) {
/*  631 */       last++;
/*      */     }
/*      */ 
/*  635 */     if (last == idx) {
/*  636 */       return null;
/*      */     }
/*  638 */     while ((first >= startIdx) && 
/*  639 */       (Character.isLetter(this.text[first]))) {
/*  638 */       first--;
/*      */     }
/*      */ 
/*  642 */     first++;
/*  643 */     return new int[] { first, last };
/*      */   }
/*      */ 
/*      */   public int trimRight(int startIdx, int endIdx) {
/*  647 */     for (int idx = endIdx; 
/*  649 */       idx >= startIdx; idx--) {
/*  650 */       char c = (char)this.detailChunks[idx].getUnicodeEquivalent(this.text[idx]);
/*  651 */       if (!isWS(c))
/*      */         break;
/*      */     }
/*  654 */     return idx;
/*      */   }
/*      */ 
/*      */   public int trimLeft(int startIdx, int endIdx) {
/*  658 */     for (int idx = startIdx; 
/*  660 */       idx <= endIdx; idx++) {
/*  661 */       char c = (char)this.detailChunks[idx].getUnicodeEquivalent(this.text[idx]);
/*  662 */       if (!isWS(c))
/*      */         break;
/*      */     }
/*  665 */     return idx;
/*      */   }
/*      */ 
/*      */   public int trimRightEx(int startIdx, int endIdx) {
/*  669 */     int idx = endIdx;
/*  670 */     char c = '\000';
/*  671 */     for (; idx >= startIdx; idx--) {
/*  672 */       c = (char)this.detailChunks[idx].getUnicodeEquivalent(this.text[idx]);
/*  673 */       if ((!isWS(c)) && (!PdfChunk.noPrint(c))) {
/*  674 */         if ((!this.detailChunks[idx].isTab()) || (!this.detailChunks[idx].isAttribute("TABSETTINGS"))) {
/*      */           break;
/*      */         }
/*  677 */         Object[] tab = (Object[])this.detailChunks[idx].getAttribute("TAB");
/*  678 */         boolean isWhitespace = ((Boolean)tab[1]).booleanValue();
/*  679 */         if (!isWhitespace)
/*      */         {
/*  681 */           break;
/*      */         }
/*      */       }
/*      */     }
/*  685 */     return idx;
/*      */   }
/*      */ 
/*      */   public int trimLeftEx(int startIdx, int endIdx) {
/*  689 */     int idx = startIdx;
/*  690 */     char c = '\000';
/*  691 */     for (; idx <= endIdx; idx++) {
/*  692 */       c = (char)this.detailChunks[idx].getUnicodeEquivalent(this.text[idx]);
/*  693 */       if ((!isWS(c)) && (!PdfChunk.noPrint(c))) {
/*  694 */         if ((!this.detailChunks[idx].isTab()) || (!this.detailChunks[idx].isAttribute("TABSETTINGS"))) {
/*      */           break;
/*      */         }
/*  697 */         Object[] tab = (Object[])this.detailChunks[idx].getAttribute("TAB");
/*  698 */         boolean isWhitespace = ((Boolean)tab[1]).booleanValue();
/*  699 */         if (!isWhitespace)
/*      */         {
/*  701 */           break;
/*      */         }
/*      */       }
/*      */     }
/*  705 */     return idx;
/*      */   }
/*      */ 
/*      */   public void reorder(int start, int end) {
/*  709 */     byte maxLevel = this.orderLevels[start];
/*  710 */     byte minLevel = maxLevel;
/*  711 */     byte onlyOddLevels = maxLevel;
/*  712 */     byte onlyEvenLevels = maxLevel;
/*  713 */     for (int k = start + 1; k <= end; k++) {
/*  714 */       byte b = this.orderLevels[k];
/*  715 */       if (b > maxLevel)
/*  716 */         maxLevel = b;
/*  717 */       else if (b < minLevel)
/*  718 */         minLevel = b;
/*  719 */       onlyOddLevels = (byte)(onlyOddLevels & b);
/*  720 */       onlyEvenLevels = (byte)(onlyEvenLevels | b);
/*      */     }
/*  722 */     if ((onlyEvenLevels & 0x1) == 0)
/*  723 */       return;
/*  724 */     if ((onlyOddLevels & 0x1) == 1) {
/*  725 */       flip(start, end + 1);
/*  726 */       return;
/*      */     }
/*  728 */     minLevel = (byte)(minLevel | 0x1);
/*  729 */     for (; maxLevel >= minLevel; maxLevel = (byte)(maxLevel - 1)) {
/*  730 */       int pstart = start;
/*      */       while (true)
/*  732 */         if ((pstart <= end) && 
/*  733 */           (this.orderLevels[pstart] < maxLevel))
/*      */         {
/*  732 */           pstart++;
/*      */         }
/*      */         else
/*      */         {
/*  736 */           if (pstart > end)
/*      */             break;
/*  738 */           int pend = pstart + 1;
/*  739 */           while ((pend <= end) && 
/*  740 */             (this.orderLevels[pend] >= maxLevel)) {
/*  739 */             pend++;
/*      */           }
/*      */ 
/*  743 */           flip(pstart, pend);
/*  744 */           pstart = pend + 1;
/*      */         }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void flip(int start, int end) {
/*  750 */     int mid = (start + end) / 2;
/*  751 */     end--;
/*  752 */     for (; start < mid; end--) {
/*  753 */       int temp = this.indexChars[start];
/*  754 */       this.indexChars[start] = this.indexChars[end];
/*  755 */       this.indexChars[end] = temp;
/*      */ 
/*  752 */       start++;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static boolean isWS(char c)
/*      */   {
/*  760 */     return c <= ' ';
/*      */   }
/*      */ 
/*      */   public static String processLTR(String s, int runDirection, int arabicOptions)
/*      */   {
/* 1092 */     BidiLine bidi = new BidiLine();
/* 1093 */     bidi.addChunk(new PdfChunk(new Chunk(s), null));
/* 1094 */     bidi.arabicOptions = arabicOptions;
/* 1095 */     bidi.getParagraph(runDirection);
/* 1096 */     ArrayList arr = bidi.createArrayOfPdfChunks(0, bidi.totalTextLength - 1);
/* 1097 */     StringBuilder sb = new StringBuilder();
/* 1098 */     for (PdfChunk ck : arr) {
/* 1099 */       sb.append(ck.toString());
/*      */     }
/* 1101 */     return sb.toString();
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/*  764 */     mirrorChars.put(40, 41);
/*  765 */     mirrorChars.put(41, 40);
/*  766 */     mirrorChars.put(60, 62);
/*  767 */     mirrorChars.put(62, 60);
/*  768 */     mirrorChars.put(91, 93);
/*  769 */     mirrorChars.put(93, 91);
/*  770 */     mirrorChars.put(123, 125);
/*  771 */     mirrorChars.put(125, 123);
/*  772 */     mirrorChars.put(171, 187);
/*  773 */     mirrorChars.put(187, 171);
/*  774 */     mirrorChars.put(8249, 8250);
/*  775 */     mirrorChars.put(8250, 8249);
/*  776 */     mirrorChars.put(8261, 8262);
/*  777 */     mirrorChars.put(8262, 8261);
/*  778 */     mirrorChars.put(8317, 8318);
/*  779 */     mirrorChars.put(8318, 8317);
/*  780 */     mirrorChars.put(8333, 8334);
/*  781 */     mirrorChars.put(8334, 8333);
/*  782 */     mirrorChars.put(8712, 8715);
/*  783 */     mirrorChars.put(8713, 8716);
/*  784 */     mirrorChars.put(8714, 8717);
/*  785 */     mirrorChars.put(8715, 8712);
/*  786 */     mirrorChars.put(8716, 8713);
/*  787 */     mirrorChars.put(8717, 8714);
/*  788 */     mirrorChars.put(8725, 10741);
/*  789 */     mirrorChars.put(8764, 8765);
/*  790 */     mirrorChars.put(8765, 8764);
/*  791 */     mirrorChars.put(8771, 8909);
/*  792 */     mirrorChars.put(8786, 8787);
/*  793 */     mirrorChars.put(8787, 8786);
/*  794 */     mirrorChars.put(8788, 8789);
/*  795 */     mirrorChars.put(8789, 8788);
/*  796 */     mirrorChars.put(8804, 8805);
/*  797 */     mirrorChars.put(8805, 8804);
/*  798 */     mirrorChars.put(8806, 8807);
/*  799 */     mirrorChars.put(8807, 8806);
/*  800 */     mirrorChars.put(8808, 8809);
/*  801 */     mirrorChars.put(8809, 8808);
/*  802 */     mirrorChars.put(8810, 8811);
/*  803 */     mirrorChars.put(8811, 8810);
/*  804 */     mirrorChars.put(8814, 8815);
/*  805 */     mirrorChars.put(8815, 8814);
/*  806 */     mirrorChars.put(8816, 8817);
/*  807 */     mirrorChars.put(8817, 8816);
/*  808 */     mirrorChars.put(8818, 8819);
/*  809 */     mirrorChars.put(8819, 8818);
/*  810 */     mirrorChars.put(8820, 8821);
/*  811 */     mirrorChars.put(8821, 8820);
/*  812 */     mirrorChars.put(8822, 8823);
/*  813 */     mirrorChars.put(8823, 8822);
/*  814 */     mirrorChars.put(8824, 8825);
/*  815 */     mirrorChars.put(8825, 8824);
/*  816 */     mirrorChars.put(8826, 8827);
/*  817 */     mirrorChars.put(8827, 8826);
/*  818 */     mirrorChars.put(8828, 8829);
/*  819 */     mirrorChars.put(8829, 8828);
/*  820 */     mirrorChars.put(8830, 8831);
/*  821 */     mirrorChars.put(8831, 8830);
/*  822 */     mirrorChars.put(8832, 8833);
/*  823 */     mirrorChars.put(8833, 8832);
/*  824 */     mirrorChars.put(8834, 8835);
/*  825 */     mirrorChars.put(8835, 8834);
/*  826 */     mirrorChars.put(8836, 8837);
/*  827 */     mirrorChars.put(8837, 8836);
/*  828 */     mirrorChars.put(8838, 8839);
/*  829 */     mirrorChars.put(8839, 8838);
/*  830 */     mirrorChars.put(8840, 8841);
/*  831 */     mirrorChars.put(8841, 8840);
/*  832 */     mirrorChars.put(8842, 8843);
/*  833 */     mirrorChars.put(8843, 8842);
/*  834 */     mirrorChars.put(8847, 8848);
/*  835 */     mirrorChars.put(8848, 8847);
/*  836 */     mirrorChars.put(8849, 8850);
/*  837 */     mirrorChars.put(8850, 8849);
/*  838 */     mirrorChars.put(8856, 10680);
/*  839 */     mirrorChars.put(8866, 8867);
/*  840 */     mirrorChars.put(8867, 8866);
/*  841 */     mirrorChars.put(8870, 10974);
/*  842 */     mirrorChars.put(8872, 10980);
/*  843 */     mirrorChars.put(8873, 10979);
/*  844 */     mirrorChars.put(8875, 10981);
/*  845 */     mirrorChars.put(8880, 8881);
/*  846 */     mirrorChars.put(8881, 8880);
/*  847 */     mirrorChars.put(8882, 8883);
/*  848 */     mirrorChars.put(8883, 8882);
/*  849 */     mirrorChars.put(8884, 8885);
/*  850 */     mirrorChars.put(8885, 8884);
/*  851 */     mirrorChars.put(8886, 8887);
/*  852 */     mirrorChars.put(8887, 8886);
/*  853 */     mirrorChars.put(8905, 8906);
/*  854 */     mirrorChars.put(8906, 8905);
/*  855 */     mirrorChars.put(8907, 8908);
/*  856 */     mirrorChars.put(8908, 8907);
/*  857 */     mirrorChars.put(8909, 8771);
/*  858 */     mirrorChars.put(8912, 8913);
/*  859 */     mirrorChars.put(8913, 8912);
/*  860 */     mirrorChars.put(8918, 8919);
/*  861 */     mirrorChars.put(8919, 8918);
/*  862 */     mirrorChars.put(8920, 8921);
/*  863 */     mirrorChars.put(8921, 8920);
/*  864 */     mirrorChars.put(8922, 8923);
/*  865 */     mirrorChars.put(8923, 8922);
/*  866 */     mirrorChars.put(8924, 8925);
/*  867 */     mirrorChars.put(8925, 8924);
/*  868 */     mirrorChars.put(8926, 8927);
/*  869 */     mirrorChars.put(8927, 8926);
/*  870 */     mirrorChars.put(8928, 8929);
/*  871 */     mirrorChars.put(8929, 8928);
/*  872 */     mirrorChars.put(8930, 8931);
/*  873 */     mirrorChars.put(8931, 8930);
/*  874 */     mirrorChars.put(8932, 8933);
/*  875 */     mirrorChars.put(8933, 8932);
/*  876 */     mirrorChars.put(8934, 8935);
/*  877 */     mirrorChars.put(8935, 8934);
/*  878 */     mirrorChars.put(8936, 8937);
/*  879 */     mirrorChars.put(8937, 8936);
/*  880 */     mirrorChars.put(8938, 8939);
/*  881 */     mirrorChars.put(8939, 8938);
/*  882 */     mirrorChars.put(8940, 8941);
/*  883 */     mirrorChars.put(8941, 8940);
/*  884 */     mirrorChars.put(8944, 8945);
/*  885 */     mirrorChars.put(8945, 8944);
/*  886 */     mirrorChars.put(8946, 8954);
/*  887 */     mirrorChars.put(8947, 8955);
/*  888 */     mirrorChars.put(8948, 8956);
/*  889 */     mirrorChars.put(8950, 8957);
/*  890 */     mirrorChars.put(8951, 8958);
/*  891 */     mirrorChars.put(8954, 8946);
/*  892 */     mirrorChars.put(8955, 8947);
/*  893 */     mirrorChars.put(8956, 8948);
/*  894 */     mirrorChars.put(8957, 8950);
/*  895 */     mirrorChars.put(8958, 8951);
/*  896 */     mirrorChars.put(8968, 8969);
/*  897 */     mirrorChars.put(8969, 8968);
/*  898 */     mirrorChars.put(8970, 8971);
/*  899 */     mirrorChars.put(8971, 8970);
/*  900 */     mirrorChars.put(9001, 9002);
/*  901 */     mirrorChars.put(9002, 9001);
/*  902 */     mirrorChars.put(10088, 10089);
/*  903 */     mirrorChars.put(10089, 10088);
/*  904 */     mirrorChars.put(10090, 10091);
/*  905 */     mirrorChars.put(10091, 10090);
/*  906 */     mirrorChars.put(10092, 10093);
/*  907 */     mirrorChars.put(10093, 10092);
/*  908 */     mirrorChars.put(10094, 10095);
/*  909 */     mirrorChars.put(10095, 10094);
/*  910 */     mirrorChars.put(10096, 10097);
/*  911 */     mirrorChars.put(10097, 10096);
/*  912 */     mirrorChars.put(10098, 10099);
/*  913 */     mirrorChars.put(10099, 10098);
/*  914 */     mirrorChars.put(10100, 10101);
/*  915 */     mirrorChars.put(10101, 10100);
/*  916 */     mirrorChars.put(10197, 10198);
/*  917 */     mirrorChars.put(10198, 10197);
/*  918 */     mirrorChars.put(10205, 10206);
/*  919 */     mirrorChars.put(10206, 10205);
/*  920 */     mirrorChars.put(10210, 10211);
/*  921 */     mirrorChars.put(10211, 10210);
/*  922 */     mirrorChars.put(10212, 10213);
/*  923 */     mirrorChars.put(10213, 10212);
/*  924 */     mirrorChars.put(10214, 10215);
/*  925 */     mirrorChars.put(10215, 10214);
/*  926 */     mirrorChars.put(10216, 10217);
/*  927 */     mirrorChars.put(10217, 10216);
/*  928 */     mirrorChars.put(10218, 10219);
/*  929 */     mirrorChars.put(10219, 10218);
/*  930 */     mirrorChars.put(10627, 10628);
/*  931 */     mirrorChars.put(10628, 10627);
/*  932 */     mirrorChars.put(10629, 10630);
/*  933 */     mirrorChars.put(10630, 10629);
/*  934 */     mirrorChars.put(10631, 10632);
/*  935 */     mirrorChars.put(10632, 10631);
/*  936 */     mirrorChars.put(10633, 10634);
/*  937 */     mirrorChars.put(10634, 10633);
/*  938 */     mirrorChars.put(10635, 10636);
/*  939 */     mirrorChars.put(10636, 10635);
/*  940 */     mirrorChars.put(10637, 10640);
/*  941 */     mirrorChars.put(10638, 10639);
/*  942 */     mirrorChars.put(10639, 10638);
/*  943 */     mirrorChars.put(10640, 10637);
/*  944 */     mirrorChars.put(10641, 10642);
/*  945 */     mirrorChars.put(10642, 10641);
/*  946 */     mirrorChars.put(10643, 10644);
/*  947 */     mirrorChars.put(10644, 10643);
/*  948 */     mirrorChars.put(10645, 10646);
/*  949 */     mirrorChars.put(10646, 10645);
/*  950 */     mirrorChars.put(10647, 10648);
/*  951 */     mirrorChars.put(10648, 10647);
/*  952 */     mirrorChars.put(10680, 8856);
/*  953 */     mirrorChars.put(10688, 10689);
/*  954 */     mirrorChars.put(10689, 10688);
/*  955 */     mirrorChars.put(10692, 10693);
/*  956 */     mirrorChars.put(10693, 10692);
/*  957 */     mirrorChars.put(10703, 10704);
/*  958 */     mirrorChars.put(10704, 10703);
/*  959 */     mirrorChars.put(10705, 10706);
/*  960 */     mirrorChars.put(10706, 10705);
/*  961 */     mirrorChars.put(10708, 10709);
/*  962 */     mirrorChars.put(10709, 10708);
/*  963 */     mirrorChars.put(10712, 10713);
/*  964 */     mirrorChars.put(10713, 10712);
/*  965 */     mirrorChars.put(10714, 10715);
/*  966 */     mirrorChars.put(10715, 10714);
/*  967 */     mirrorChars.put(10741, 8725);
/*  968 */     mirrorChars.put(10744, 10745);
/*  969 */     mirrorChars.put(10745, 10744);
/*  970 */     mirrorChars.put(10748, 10749);
/*  971 */     mirrorChars.put(10749, 10748);
/*  972 */     mirrorChars.put(10795, 10796);
/*  973 */     mirrorChars.put(10796, 10795);
/*  974 */     mirrorChars.put(10797, 10796);
/*  975 */     mirrorChars.put(10798, 10797);
/*  976 */     mirrorChars.put(10804, 10805);
/*  977 */     mirrorChars.put(10805, 10804);
/*  978 */     mirrorChars.put(10812, 10813);
/*  979 */     mirrorChars.put(10813, 10812);
/*  980 */     mirrorChars.put(10852, 10853);
/*  981 */     mirrorChars.put(10853, 10852);
/*  982 */     mirrorChars.put(10873, 10874);
/*  983 */     mirrorChars.put(10874, 10873);
/*  984 */     mirrorChars.put(10877, 10878);
/*  985 */     mirrorChars.put(10878, 10877);
/*  986 */     mirrorChars.put(10879, 10880);
/*  987 */     mirrorChars.put(10880, 10879);
/*  988 */     mirrorChars.put(10881, 10882);
/*  989 */     mirrorChars.put(10882, 10881);
/*  990 */     mirrorChars.put(10883, 10884);
/*  991 */     mirrorChars.put(10884, 10883);
/*  992 */     mirrorChars.put(10891, 10892);
/*  993 */     mirrorChars.put(10892, 10891);
/*  994 */     mirrorChars.put(10897, 10898);
/*  995 */     mirrorChars.put(10898, 10897);
/*  996 */     mirrorChars.put(10899, 10900);
/*  997 */     mirrorChars.put(10900, 10899);
/*  998 */     mirrorChars.put(10901, 10902);
/*  999 */     mirrorChars.put(10902, 10901);
/* 1000 */     mirrorChars.put(10903, 10904);
/* 1001 */     mirrorChars.put(10904, 10903);
/* 1002 */     mirrorChars.put(10905, 10906);
/* 1003 */     mirrorChars.put(10906, 10905);
/* 1004 */     mirrorChars.put(10907, 10908);
/* 1005 */     mirrorChars.put(10908, 10907);
/* 1006 */     mirrorChars.put(10913, 10914);
/* 1007 */     mirrorChars.put(10914, 10913);
/* 1008 */     mirrorChars.put(10918, 10919);
/* 1009 */     mirrorChars.put(10919, 10918);
/* 1010 */     mirrorChars.put(10920, 10921);
/* 1011 */     mirrorChars.put(10921, 10920);
/* 1012 */     mirrorChars.put(10922, 10923);
/* 1013 */     mirrorChars.put(10923, 10922);
/* 1014 */     mirrorChars.put(10924, 10925);
/* 1015 */     mirrorChars.put(10925, 10924);
/* 1016 */     mirrorChars.put(10927, 10928);
/* 1017 */     mirrorChars.put(10928, 10927);
/* 1018 */     mirrorChars.put(10931, 10932);
/* 1019 */     mirrorChars.put(10932, 10931);
/* 1020 */     mirrorChars.put(10939, 10940);
/* 1021 */     mirrorChars.put(10940, 10939);
/* 1022 */     mirrorChars.put(10941, 10942);
/* 1023 */     mirrorChars.put(10942, 10941);
/* 1024 */     mirrorChars.put(10943, 10944);
/* 1025 */     mirrorChars.put(10944, 10943);
/* 1026 */     mirrorChars.put(10945, 10946);
/* 1027 */     mirrorChars.put(10946, 10945);
/* 1028 */     mirrorChars.put(10947, 10948);
/* 1029 */     mirrorChars.put(10948, 10947);
/* 1030 */     mirrorChars.put(10949, 10950);
/* 1031 */     mirrorChars.put(10950, 10949);
/* 1032 */     mirrorChars.put(10957, 10958);
/* 1033 */     mirrorChars.put(10958, 10957);
/* 1034 */     mirrorChars.put(10959, 10960);
/* 1035 */     mirrorChars.put(10960, 10959);
/* 1036 */     mirrorChars.put(10961, 10962);
/* 1037 */     mirrorChars.put(10962, 10961);
/* 1038 */     mirrorChars.put(10963, 10964);
/* 1039 */     mirrorChars.put(10964, 10963);
/* 1040 */     mirrorChars.put(10965, 10966);
/* 1041 */     mirrorChars.put(10966, 10965);
/* 1042 */     mirrorChars.put(10974, 8870);
/* 1043 */     mirrorChars.put(10979, 8873);
/* 1044 */     mirrorChars.put(10980, 8872);
/* 1045 */     mirrorChars.put(10981, 8875);
/* 1046 */     mirrorChars.put(10988, 10989);
/* 1047 */     mirrorChars.put(10989, 10988);
/* 1048 */     mirrorChars.put(10999, 11000);
/* 1049 */     mirrorChars.put(11000, 10999);
/* 1050 */     mirrorChars.put(11001, 11002);
/* 1051 */     mirrorChars.put(11002, 11001);
/* 1052 */     mirrorChars.put(12296, 12297);
/* 1053 */     mirrorChars.put(12297, 12296);
/* 1054 */     mirrorChars.put(12298, 12299);
/* 1055 */     mirrorChars.put(12299, 12298);
/* 1056 */     mirrorChars.put(12300, 12301);
/* 1057 */     mirrorChars.put(12301, 12300);
/* 1058 */     mirrorChars.put(12302, 12303);
/* 1059 */     mirrorChars.put(12303, 12302);
/* 1060 */     mirrorChars.put(12304, 12305);
/* 1061 */     mirrorChars.put(12305, 12304);
/* 1062 */     mirrorChars.put(12308, 12309);
/* 1063 */     mirrorChars.put(12309, 12308);
/* 1064 */     mirrorChars.put(12310, 12311);
/* 1065 */     mirrorChars.put(12311, 12310);
/* 1066 */     mirrorChars.put(12312, 12313);
/* 1067 */     mirrorChars.put(12313, 12312);
/* 1068 */     mirrorChars.put(12314, 12315);
/* 1069 */     mirrorChars.put(12315, 12314);
/* 1070 */     mirrorChars.put(65288, 65289);
/* 1071 */     mirrorChars.put(65289, 65288);
/* 1072 */     mirrorChars.put(65308, 65310);
/* 1073 */     mirrorChars.put(65310, 65308);
/* 1074 */     mirrorChars.put(65339, 65341);
/* 1075 */     mirrorChars.put(65341, 65339);
/* 1076 */     mirrorChars.put(65371, 65373);
/* 1077 */     mirrorChars.put(65373, 65371);
/* 1078 */     mirrorChars.put(65375, 65376);
/* 1079 */     mirrorChars.put(65376, 65375);
/* 1080 */     mirrorChars.put(65378, 65379);
/* 1081 */     mirrorChars.put(65379, 65378);
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.BidiLine
 * JD-Core Version:    0.6.2
 */