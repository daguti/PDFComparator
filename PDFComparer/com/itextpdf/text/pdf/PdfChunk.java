/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.BaseColor;
/*     */ import com.itextpdf.text.Chunk;
/*     */ import com.itextpdf.text.Font;
/*     */ import com.itextpdf.text.Image;
/*     */ import com.itextpdf.text.SplitCharacter;
/*     */ import com.itextpdf.text.TabSettings;
/*     */ import com.itextpdf.text.TabStop;
/*     */ import com.itextpdf.text.Utilities;
/*     */ import com.itextpdf.text.pdf.interfaces.IAccessibleElement;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map.Entry;
/*     */ 
/*     */ public class PdfChunk
/*     */ {
/*  67 */   private static final char[] singleSpace = { ' ' };
/*  68 */   private static final PdfChunk[] thisChunk = new PdfChunk[1];
/*     */   private static final float ITALIC_ANGLE = 0.21256F;
/*  71 */   private static final HashSet<String> keysAttributes = new HashSet();
/*     */ 
/*  74 */   private static final HashSet<String> keysNoStroke = new HashSet();
/*     */   private static final String TABSTOP = "TABSTOP";
/* 105 */   protected String value = "";
/*     */ 
/* 108 */   protected String encoding = "Cp1252";
/*     */   protected PdfFont font;
/*     */   protected BaseFont baseFont;
/*     */   protected SplitCharacter splitCharacter;
/* 123 */   protected HashMap<String, Object> attributes = new HashMap();
/*     */ 
/* 131 */   protected HashMap<String, Object> noStroke = new HashMap();
/*     */   protected boolean newlineSplit;
/*     */   protected Image image;
/* 138 */   protected float imageScalePercentage = 1.0F;
/*     */   protected float offsetX;
/*     */   protected float offsetY;
/* 147 */   protected boolean changeLeading = false;
/*     */ 
/* 150 */   protected float leading = 0.0F;
/*     */ 
/* 152 */   protected IAccessibleElement accessibleElement = null;
/*     */   public static final float UNDERLINE_THICKNESS = 0.0666667F;
/*     */   public static final float UNDERLINE_OFFSET = -0.3333333F;
/*     */ 
/*     */   PdfChunk(String string, PdfChunk other)
/*     */   {
/* 166 */     thisChunk[0] = this;
/* 167 */     this.value = string;
/* 168 */     this.font = other.font;
/* 169 */     this.attributes = other.attributes;
/* 170 */     this.noStroke = other.noStroke;
/* 171 */     this.baseFont = other.baseFont;
/* 172 */     this.changeLeading = other.changeLeading;
/* 173 */     this.leading = other.leading;
/* 174 */     Object[] obj = (Object[])this.attributes.get("IMAGE");
/* 175 */     if (obj == null) {
/* 176 */       this.image = null;
/*     */     } else {
/* 178 */       this.image = ((Image)obj[0]);
/* 179 */       this.offsetX = ((Float)obj[1]).floatValue();
/* 180 */       this.offsetY = ((Float)obj[2]).floatValue();
/* 181 */       this.changeLeading = ((Boolean)obj[3]).booleanValue();
/*     */     }
/* 183 */     this.encoding = this.font.getFont().getEncoding();
/* 184 */     this.splitCharacter = ((SplitCharacter)this.noStroke.get("SPLITCHARACTER"));
/* 185 */     if (this.splitCharacter == null)
/* 186 */       this.splitCharacter = DefaultSplitCharacter.DEFAULT;
/* 187 */     this.accessibleElement = other.accessibleElement;
/*     */   }
/*     */ 
/*     */   PdfChunk(Chunk chunk, PdfAction action)
/*     */   {
/* 198 */     thisChunk[0] = this;
/* 199 */     this.value = chunk.getContent();
/*     */ 
/* 201 */     Font f = chunk.getFont();
/* 202 */     float size = f.getSize();
/* 203 */     if (size == -1.0F)
/* 204 */       size = 12.0F;
/* 205 */     this.baseFont = f.getBaseFont();
/* 206 */     int style = f.getStyle();
/* 207 */     if (style == -1) {
/* 208 */       style = 0;
/*     */     }
/* 210 */     if (this.baseFont == null)
/*     */     {
/* 212 */       this.baseFont = f.getCalculatedBaseFont(false);
/*     */     }
/*     */     else
/*     */     {
/* 216 */       if ((style & 0x1) != 0) {
/* 217 */         this.attributes.put("TEXTRENDERMODE", new Object[] { Integer.valueOf(2), new Float(size / 30.0F), null });
/*     */       }
/* 219 */       if ((style & 0x2) != 0)
/* 220 */         this.attributes.put("SKEW", new float[] { 0.0F, 0.21256F });
/*     */     }
/* 222 */     this.font = new PdfFont(this.baseFont, size);
/*     */ 
/* 224 */     HashMap attr = chunk.getAttributes();
/* 225 */     if (attr != null) {
/* 226 */       for (Map.Entry entry : attr.entrySet()) {
/* 227 */         String name = (String)entry.getKey();
/* 228 */         if (keysAttributes.contains(name)) {
/* 229 */           this.attributes.put(name, entry.getValue());
/*     */         }
/* 231 */         else if (keysNoStroke.contains(name)) {
/* 232 */           this.noStroke.put(name, entry.getValue());
/*     */         }
/*     */       }
/* 235 */       if ("".equals(attr.get("GENERICTAG"))) {
/* 236 */         this.attributes.put("GENERICTAG", chunk.getContent());
/*     */       }
/*     */     }
/* 239 */     if (f.isUnderlined()) {
/* 240 */       Object[] obj = { null, { 0.0F, 0.0666667F, 0.0F, -0.3333333F, 0.0F } };
/* 241 */       Object[][] unders = Utilities.addToArray((Object[][])this.attributes.get("UNDERLINE"), obj);
/* 242 */       this.attributes.put("UNDERLINE", unders);
/*     */     }
/* 244 */     if (f.isStrikethru()) {
/* 245 */       Object[] obj = { null, { 0.0F, 0.0666667F, 0.0F, 0.3333333F, 0.0F } };
/* 246 */       Object[][] unders = Utilities.addToArray((Object[][])this.attributes.get("UNDERLINE"), obj);
/* 247 */       this.attributes.put("UNDERLINE", unders);
/*     */     }
/* 249 */     if (action != null) {
/* 250 */       this.attributes.put("ACTION", action);
/*     */     }
/* 252 */     this.noStroke.put("COLOR", f.getColor());
/* 253 */     this.noStroke.put("ENCODING", this.font.getFont().getEncoding());
/*     */ 
/* 255 */     Float lh = (Float)this.attributes.get("LINEHEIGHT");
/* 256 */     if (lh != null) {
/* 257 */       this.changeLeading = true;
/* 258 */       this.leading = lh.floatValue();
/*     */     }
/*     */ 
/* 261 */     Object[] obj = (Object[])this.attributes.get("IMAGE");
/* 262 */     if (obj == null) {
/* 263 */       this.image = null;
/*     */     }
/*     */     else {
/* 266 */       this.attributes.remove("HSCALE");
/* 267 */       this.image = ((Image)obj[0]);
/* 268 */       this.offsetX = ((Float)obj[1]).floatValue();
/* 269 */       this.offsetY = ((Float)obj[2]).floatValue();
/* 270 */       this.changeLeading = ((Boolean)obj[3]).booleanValue();
/*     */     }
/* 272 */     Float hs = (Float)this.attributes.get("HSCALE");
/* 273 */     if (hs != null)
/* 274 */       this.font.setHorizontalScaling(hs.floatValue());
/* 275 */     this.encoding = this.font.getFont().getEncoding();
/* 276 */     this.splitCharacter = ((SplitCharacter)this.noStroke.get("SPLITCHARACTER"));
/* 277 */     if (this.splitCharacter == null)
/* 278 */       this.splitCharacter = DefaultSplitCharacter.DEFAULT;
/* 279 */     this.accessibleElement = chunk;
/*     */   }
/*     */ 
/*     */   PdfChunk(Chunk chunk, PdfAction action, TabSettings tabSettings)
/*     */   {
/* 290 */     this(chunk, action);
/* 291 */     if ((tabSettings != null) && (this.attributes.get("TABSETTINGS") == null))
/* 292 */       this.attributes.put("TABSETTINGS", tabSettings);
/*     */   }
/*     */ 
/*     */   public int getUnicodeEquivalent(int c)
/*     */   {
/* 304 */     return this.baseFont.getUnicodeEquivalent(c);
/*     */   }
/*     */ 
/*     */   protected int getWord(String text, int start) {
/* 308 */     int len = text.length();
/* 309 */     while ((start < len) && 
/* 310 */       (Character.isLetter(text.charAt(start))))
/*     */     {
/* 312 */       start++;
/*     */     }
/* 314 */     return start;
/*     */   }
/*     */ 
/*     */   PdfChunk split(float width)
/*     */   {
/* 327 */     this.newlineSplit = false;
/* 328 */     if (this.image != null) {
/* 329 */       if (this.image.getScaledWidth() > width) {
/* 330 */         PdfChunk pc = new PdfChunk("￼", this);
/* 331 */         this.value = "";
/* 332 */         this.attributes = new HashMap();
/* 333 */         this.image = null;
/* 334 */         this.font = PdfFont.getDefaultFont();
/* 335 */         return pc;
/*     */       }
/*     */ 
/* 338 */       return null;
/*     */     }
/* 340 */     HyphenationEvent hyphenationEvent = (HyphenationEvent)this.noStroke.get("HYPHENATION");
/* 341 */     int currentPosition = 0;
/* 342 */     int splitPosition = -1;
/* 343 */     float currentWidth = 0.0F;
/*     */ 
/* 347 */     int lastSpace = -1;
/* 348 */     float lastSpaceWidth = 0.0F;
/* 349 */     int length = this.value.length();
/* 350 */     char[] valueArray = this.value.toCharArray();
/* 351 */     char character = '\000';
/* 352 */     BaseFont ft = this.font.getFont();
/* 353 */     boolean surrogate = false;
/* 354 */     if ((ft.getFontType() == 2) && (ft.getUnicodeEquivalent(32) != 32));
/* 355 */     while (currentPosition < length)
/*     */     {
/* 357 */       char cidChar = valueArray[currentPosition];
/* 358 */       character = (char)ft.getUnicodeEquivalent(cidChar);
/*     */ 
/* 360 */       if (character == '\n') {
/* 361 */         this.newlineSplit = true;
/* 362 */         String returnValue = this.value.substring(currentPosition + 1);
/* 363 */         this.value = this.value.substring(0, currentPosition);
/* 364 */         if (this.value.length() < 1) {
/* 365 */           this.value = "\001";
/*     */         }
/* 367 */         PdfChunk pc = new PdfChunk(returnValue, this);
/* 368 */         return pc;
/*     */       }
/* 370 */       currentWidth += getCharWidth(cidChar);
/* 371 */       if (character == ' ') {
/* 372 */         lastSpace = currentPosition + 1;
/* 373 */         lastSpaceWidth = currentWidth;
/*     */       }
/* 375 */       if (currentWidth <= width)
/*     */       {
/* 378 */         if (this.splitCharacter.isSplitCharacter(0, currentPosition, length, valueArray, thisChunk))
/* 379 */           splitPosition = currentPosition + 1;
/* 380 */         currentPosition++;
/* 381 */         continue;
/*     */ 
/* 384 */         while (currentPosition < length)
/*     */         {
/* 386 */           character = valueArray[currentPosition];
/*     */ 
/* 388 */           if ((character == '\r') || (character == '\n')) {
/* 389 */             this.newlineSplit = true;
/* 390 */             int inc = 1;
/* 391 */             if ((character == '\r') && (currentPosition + 1 < length) && (valueArray[(currentPosition + 1)] == '\n'))
/* 392 */               inc = 2;
/* 393 */             String returnValue = this.value.substring(currentPosition + inc);
/* 394 */             this.value = this.value.substring(0, currentPosition);
/* 395 */             if (this.value.length() < 1) {
/* 396 */               this.value = " ";
/*     */             }
/* 398 */             PdfChunk pc = new PdfChunk(returnValue, this);
/* 399 */             return pc;
/*     */           }
/* 401 */           surrogate = Utilities.isSurrogatePair(valueArray, currentPosition);
/* 402 */           if (surrogate)
/* 403 */             currentWidth += getCharWidth(Utilities.convertToUtf32(valueArray[currentPosition], valueArray[(currentPosition + 1)]));
/*     */           else
/* 405 */             currentWidth += getCharWidth(character);
/* 406 */           if (character == ' ') {
/* 407 */             lastSpace = currentPosition + 1;
/* 408 */             lastSpaceWidth = currentWidth;
/*     */           }
/* 410 */           if (surrogate)
/* 411 */             currentPosition++;
/* 412 */           if (currentWidth > width) {
/*     */             break;
/*     */           }
/* 415 */           if (this.splitCharacter.isSplitCharacter(0, currentPosition, length, valueArray, null))
/* 416 */             splitPosition = currentPosition + 1;
/* 417 */           currentPosition++;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 422 */     if (currentPosition == length) {
/* 423 */       return null;
/*     */     }
/*     */ 
/* 426 */     if (splitPosition < 0) {
/* 427 */       String returnValue = this.value;
/* 428 */       this.value = "";
/* 429 */       PdfChunk pc = new PdfChunk(returnValue, this);
/* 430 */       return pc;
/*     */     }
/* 432 */     if ((lastSpace > splitPosition) && (this.splitCharacter.isSplitCharacter(0, 0, 1, singleSpace, null)))
/* 433 */       splitPosition = lastSpace;
/* 434 */     if ((hyphenationEvent != null) && (lastSpace >= 0) && (lastSpace < currentPosition)) {
/* 435 */       int wordIdx = getWord(this.value, lastSpace);
/* 436 */       if (wordIdx > lastSpace) {
/* 437 */         String pre = hyphenationEvent.getHyphenatedWordPre(this.value.substring(lastSpace, wordIdx), this.font.getFont(), this.font.size(), width - lastSpaceWidth);
/* 438 */         String post = hyphenationEvent.getHyphenatedWordPost();
/* 439 */         if (pre.length() > 0) {
/* 440 */           String returnValue = post + this.value.substring(wordIdx);
/* 441 */           this.value = trim(this.value.substring(0, lastSpace) + pre);
/* 442 */           PdfChunk pc = new PdfChunk(returnValue, this);
/* 443 */           return pc;
/*     */         }
/*     */       }
/*     */     }
/* 447 */     String returnValue = this.value.substring(splitPosition);
/* 448 */     this.value = trim(this.value.substring(0, splitPosition));
/* 449 */     PdfChunk pc = new PdfChunk(returnValue, this);
/* 450 */     return pc;
/*     */   }
/*     */ 
/*     */   PdfChunk truncate(float width)
/*     */   {
/* 462 */     if (this.image != null) {
/* 463 */       if (this.image.getScaledWidth() > width)
/*     */       {
/* 465 */         if (this.image.isScaleToFitLineWhenOverflow())
/*     */         {
/* 468 */           setImageScalePercentage(width / this.image.getWidth());
/* 469 */           return null;
/*     */         }
/* 471 */         PdfChunk pc = new PdfChunk("", this);
/* 472 */         this.value = "";
/* 473 */         this.attributes.remove("IMAGE");
/* 474 */         this.image = null;
/* 475 */         this.font = PdfFont.getDefaultFont();
/* 476 */         return pc;
/*     */       }
/*     */ 
/* 479 */       return null;
/*     */     }
/*     */ 
/* 482 */     int currentPosition = 0;
/* 483 */     float currentWidth = 0.0F;
/*     */ 
/* 486 */     if (width < this.font.width()) {
/* 487 */       String returnValue = this.value.substring(1);
/* 488 */       this.value = this.value.substring(0, 1);
/* 489 */       PdfChunk pc = new PdfChunk(returnValue, this);
/* 490 */       return pc;
/*     */     }
/*     */ 
/* 495 */     int length = this.value.length();
/* 496 */     boolean surrogate = false;
/* 497 */     while (currentPosition < length)
/*     */     {
/* 499 */       surrogate = Utilities.isSurrogatePair(this.value, currentPosition);
/* 500 */       if (surrogate)
/* 501 */         currentWidth += getCharWidth(Utilities.convertToUtf32(this.value, currentPosition));
/*     */       else
/* 503 */         currentWidth += getCharWidth(this.value.charAt(currentPosition));
/* 504 */       if (currentWidth > width)
/*     */         break;
/* 506 */       if (surrogate)
/* 507 */         currentPosition++;
/* 508 */       currentPosition++;
/*     */     }
/*     */ 
/* 512 */     if (currentPosition == length) {
/* 513 */       return null;
/*     */     }
/*     */ 
/* 519 */     if (currentPosition == 0) {
/* 520 */       currentPosition = 1;
/* 521 */       if (surrogate)
/* 522 */         currentPosition++;
/*     */     }
/* 524 */     String returnValue = this.value.substring(currentPosition);
/* 525 */     this.value = this.value.substring(0, currentPosition);
/* 526 */     PdfChunk pc = new PdfChunk(returnValue, this);
/* 527 */     return pc;
/*     */   }
/*     */ 
/*     */   PdfFont font()
/*     */   {
/* 539 */     return this.font;
/*     */   }
/*     */ 
/*     */   BaseColor color()
/*     */   {
/* 549 */     return (BaseColor)this.noStroke.get("COLOR");
/*     */   }
/*     */ 
/*     */   float width()
/*     */   {
/* 559 */     return width(this.value);
/*     */   }
/*     */ 
/*     */   float width(String str) {
/* 563 */     if (isAttribute("SEPARATOR")) {
/* 564 */       return 0.0F;
/*     */     }
/* 566 */     if (isImage()) {
/* 567 */       return getImageWidth();
/*     */     }
/*     */ 
/* 570 */     float width = this.font.width(str);
/*     */ 
/* 572 */     if (isAttribute("CHAR_SPACING")) {
/* 573 */       Float cs = (Float)getAttribute("CHAR_SPACING");
/* 574 */       width += str.length() * cs.floatValue();
/*     */     }
/* 576 */     if (isAttribute("WORD_SPACING")) {
/* 577 */       int numberOfSpaces = 0;
/* 578 */       int idx = -1;
/* 579 */       while ((idx = str.indexOf(' ', idx + 1)) >= 0)
/* 580 */         numberOfSpaces++;
/* 581 */       Float ws = (Float)getAttribute("WORD_SPACING");
/* 582 */       width += numberOfSpaces * ws.floatValue();
/*     */     }
/* 584 */     return width;
/*     */   }
/*     */ 
/*     */   float height() {
/* 588 */     if (isImage()) {
/* 589 */       return getImageHeight();
/*     */     }
/* 591 */     return this.font.size();
/*     */   }
/*     */ 
/*     */   public boolean isNewlineSplit()
/*     */   {
/* 602 */     return this.newlineSplit;
/*     */   }
/*     */ 
/*     */   public float getWidthCorrected(float charSpacing, float wordSpacing)
/*     */   {
/* 615 */     if (this.image != null) {
/* 616 */       return this.image.getScaledWidth() + charSpacing;
/*     */     }
/* 618 */     int numberOfSpaces = 0;
/* 619 */     int idx = -1;
/* 620 */     while ((idx = this.value.indexOf(' ', idx + 1)) >= 0)
/* 621 */       numberOfSpaces++;
/* 622 */     return this.font.width(this.value) + this.value.length() * charSpacing + numberOfSpaces * wordSpacing;
/*     */   }
/*     */ 
/*     */   public float getTextRise()
/*     */   {
/* 630 */     Float f = (Float)getAttribute("SUBSUPSCRIPT");
/* 631 */     if (f != null) {
/* 632 */       return f.floatValue();
/*     */     }
/* 634 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   public float trimLastSpace()
/*     */   {
/* 644 */     BaseFont ft = this.font.getFont();
/* 645 */     if ((ft.getFontType() == 2) && (ft.getUnicodeEquivalent(32) != 32)) {
/* 646 */       if ((this.value.length() > 1) && (this.value.endsWith("\001"))) {
/* 647 */         this.value = this.value.substring(0, this.value.length() - 1);
/* 648 */         return this.font.width(1);
/*     */       }
/*     */ 
/*     */     }
/* 652 */     else if ((this.value.length() > 1) && (this.value.endsWith(" "))) {
/* 653 */       this.value = this.value.substring(0, this.value.length() - 1);
/* 654 */       return this.font.width(32);
/*     */     }
/*     */ 
/* 657 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   public float trimFirstSpace() {
/* 661 */     BaseFont ft = this.font.getFont();
/* 662 */     if ((ft.getFontType() == 2) && (ft.getUnicodeEquivalent(32) != 32)) {
/* 663 */       if ((this.value.length() > 1) && (this.value.startsWith("\001"))) {
/* 664 */         this.value = this.value.substring(1);
/* 665 */         return this.font.width(1);
/*     */       }
/*     */ 
/*     */     }
/* 669 */     else if ((this.value.length() > 1) && (this.value.startsWith(" "))) {
/* 670 */       this.value = this.value.substring(1);
/* 671 */       return this.font.width(32);
/*     */     }
/*     */ 
/* 674 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   Object getAttribute(String name)
/*     */   {
/* 686 */     if (this.attributes.containsKey(name))
/* 687 */       return this.attributes.get(name);
/* 688 */     return this.noStroke.get(name);
/*     */   }
/*     */ 
/*     */   boolean isAttribute(String name)
/*     */   {
/* 699 */     if (this.attributes.containsKey(name))
/* 700 */       return true;
/* 701 */     return this.noStroke.containsKey(name);
/*     */   }
/*     */ 
/*     */   boolean isStroked()
/*     */   {
/* 711 */     return !this.attributes.isEmpty();
/*     */   }
/*     */ 
/*     */   boolean isSeparator()
/*     */   {
/* 720 */     return isAttribute("SEPARATOR");
/*     */   }
/*     */ 
/*     */   boolean isHorizontalSeparator()
/*     */   {
/* 729 */     if (isAttribute("SEPARATOR")) {
/* 730 */       Object[] o = (Object[])getAttribute("SEPARATOR");
/* 731 */       return !((Boolean)o[1]).booleanValue();
/*     */     }
/* 733 */     return false;
/*     */   }
/*     */ 
/*     */   boolean isTab()
/*     */   {
/* 742 */     return isAttribute("TAB");
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   void adjustLeft(float newValue)
/*     */   {
/* 752 */     Object[] o = (Object[])this.attributes.get("TAB");
/* 753 */     if (o != null)
/* 754 */       this.attributes.put("TAB", new Object[] { o[0], o[1], o[2], new Float(newValue) });
/*     */   }
/*     */ 
/*     */   static TabStop getTabStop(PdfChunk tab, float tabPosition)
/*     */   {
/* 759 */     TabStop tabStop = null;
/* 760 */     Object[] o = (Object[])tab.attributes.get("TAB");
/* 761 */     if (o != null) {
/* 762 */       Float tabInterval = (Float)o[0];
/* 763 */       if (Float.isNaN(tabInterval.floatValue()))
/* 764 */         tabStop = TabSettings.getTabStopNewInstance(tabPosition, (TabSettings)tab.attributes.get("TABSETTINGS"));
/*     */       else {
/* 766 */         tabStop = TabStop.newInstance(tabPosition, tabInterval.floatValue());
/*     */       }
/*     */     }
/* 769 */     return tabStop;
/*     */   }
/*     */ 
/*     */   TabStop getTabStop() {
/* 773 */     return (TabStop)this.attributes.get("TABSTOP");
/*     */   }
/*     */ 
/*     */   void setTabStop(TabStop tabStop) {
/* 777 */     this.attributes.put("TABSTOP", tabStop);
/*     */   }
/*     */ 
/*     */   boolean isImage()
/*     */   {
/* 787 */     return this.image != null;
/*     */   }
/*     */ 
/*     */   Image getImage()
/*     */   {
/* 797 */     return this.image;
/*     */   }
/*     */ 
/*     */   float getImageHeight() {
/* 801 */     return this.image.getScaledHeight() * this.imageScalePercentage;
/*     */   }
/*     */ 
/*     */   float getImageWidth() {
/* 805 */     return this.image.getScaledWidth() * this.imageScalePercentage;
/*     */   }
/*     */ 
/*     */   public float getImageScalePercentage()
/*     */   {
/* 813 */     return this.imageScalePercentage;
/*     */   }
/*     */ 
/*     */   public void setImageScalePercentage(float imageScalePercentage)
/*     */   {
/* 821 */     this.imageScalePercentage = imageScalePercentage;
/*     */   }
/*     */ 
/*     */   void setImageOffsetX(float offsetX)
/*     */   {
/* 831 */     this.offsetX = offsetX;
/*     */   }
/*     */ 
/*     */   float getImageOffsetX()
/*     */   {
/* 841 */     return this.offsetX;
/*     */   }
/*     */ 
/*     */   void setImageOffsetY(float offsetY)
/*     */   {
/* 851 */     this.offsetY = offsetY;
/*     */   }
/*     */ 
/*     */   float getImageOffsetY()
/*     */   {
/* 861 */     return this.offsetY;
/*     */   }
/*     */ 
/*     */   void setValue(String value)
/*     */   {
/* 871 */     this.value = value;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 879 */     return this.value;
/*     */   }
/*     */ 
/*     */   boolean isSpecialEncoding()
/*     */   {
/* 888 */     return (this.encoding.equals("UnicodeBigUnmarked")) || (this.encoding.equals("Identity-H"));
/*     */   }
/*     */ 
/*     */   String getEncoding()
/*     */   {
/* 898 */     return this.encoding;
/*     */   }
/*     */ 
/*     */   int length() {
/* 902 */     return this.value.length();
/*     */   }
/*     */ 
/*     */   int lengthUtf32() {
/* 906 */     if (!"Identity-H".equals(this.encoding))
/* 907 */       return this.value.length();
/* 908 */     int total = 0;
/* 909 */     int len = this.value.length();
/* 910 */     for (int k = 0; k < len; k++) {
/* 911 */       if (Utilities.isSurrogateHigh(this.value.charAt(k)))
/* 912 */         k++;
/* 913 */       total++;
/*     */     }
/* 915 */     return total;
/*     */   }
/*     */ 
/*     */   boolean isExtSplitCharacter(int start, int current, int end, char[] cc, PdfChunk[] ck) {
/* 919 */     return this.splitCharacter.isSplitCharacter(start, current, end, cc, ck);
/*     */   }
/*     */ 
/*     */   String trim(String string)
/*     */   {
/* 929 */     BaseFont ft = this.font.getFont();
/* 930 */     if ((ft.getFontType() == 2) && (ft.getUnicodeEquivalent(32) != 32));
/* 931 */     while (string.endsWith("\001")) {
/* 932 */       string = string.substring(0, string.length() - 1); continue;
/*     */ 
/* 936 */       while ((string.endsWith(" ")) || (string.endsWith("\t"))) {
/* 937 */         string = string.substring(0, string.length() - 1);
/*     */       }
/*     */     }
/* 940 */     return string;
/*     */   }
/*     */ 
/*     */   public boolean changeLeading() {
/* 944 */     return this.changeLeading;
/*     */   }
/*     */ 
/*     */   public float getLeading() {
/* 948 */     return this.leading;
/*     */   }
/*     */ 
/*     */   float getCharWidth(int c) {
/* 952 */     if (noPrint(c))
/* 953 */       return 0.0F;
/* 954 */     if (isAttribute("CHAR_SPACING")) {
/* 955 */       Float cs = (Float)getAttribute("CHAR_SPACING");
/* 956 */       return this.font.width(c) + cs.floatValue() * this.font.getHorizontalScaling();
/*     */     }
/* 958 */     if (isImage()) {
/* 959 */       return getImageWidth();
/*     */     }
/* 961 */     return this.font.width(c);
/*     */   }
/*     */ 
/*     */   public static boolean noPrint(int c) {
/* 965 */     return ((c >= 8203) && (c <= 8207)) || ((c >= 8234) && (c <= 8238));
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  78 */     keysAttributes.add("ACTION");
/*  79 */     keysAttributes.add("UNDERLINE");
/*  80 */     keysAttributes.add("REMOTEGOTO");
/*  81 */     keysAttributes.add("LOCALGOTO");
/*  82 */     keysAttributes.add("LOCALDESTINATION");
/*  83 */     keysAttributes.add("GENERICTAG");
/*  84 */     keysAttributes.add("NEWPAGE");
/*  85 */     keysAttributes.add("IMAGE");
/*  86 */     keysAttributes.add("BACKGROUND");
/*  87 */     keysAttributes.add("PDFANNOTATION");
/*  88 */     keysAttributes.add("SKEW");
/*  89 */     keysAttributes.add("HSCALE");
/*  90 */     keysAttributes.add("SEPARATOR");
/*  91 */     keysAttributes.add("TAB");
/*  92 */     keysAttributes.add("TABSETTINGS");
/*  93 */     keysAttributes.add("CHAR_SPACING");
/*  94 */     keysAttributes.add("WORD_SPACING");
/*  95 */     keysAttributes.add("LINEHEIGHT");
/*  96 */     keysNoStroke.add("SUBSUPSCRIPT");
/*  97 */     keysNoStroke.add("SPLITCHARACTER");
/*  98 */     keysNoStroke.add("HYPHENATION");
/*  99 */     keysNoStroke.add("TEXTRENDERMODE");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfChunk
 * JD-Core Version:    0.6.2
 */