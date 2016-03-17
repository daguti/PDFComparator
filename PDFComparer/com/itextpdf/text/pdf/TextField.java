/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.BaseColor;
/*     */ import com.itextpdf.text.Chunk;
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import com.itextpdf.text.Font;
/*     */ import com.itextpdf.text.Phrase;
/*     */ import com.itextpdf.text.Rectangle;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class TextField extends BaseField
/*     */ {
/*     */   private String defaultText;
/*     */   private String[] choices;
/*     */   private String[] choiceExports;
/*  75 */   private ArrayList<Integer> choiceSelections = new ArrayList();
/*     */   private int topFirst;
/*     */   private float extraMarginLeft;
/*     */   private float extraMarginTop;
/*     */   private ArrayList<BaseFont> substitutionFonts;
/*     */   private BaseFont extensionFont;
/*     */ 
/*     */   public TextField(PdfWriter writer, Rectangle box, String fieldName)
/*     */   {
/*  90 */     super(writer, box, fieldName);
/*     */   }
/*     */ 
/*     */   private static boolean checkRTL(String text) {
/*  94 */     if ((text == null) || (text.length() == 0))
/*  95 */       return false;
/*  96 */     char[] cc = text.toCharArray();
/*  97 */     for (int k = 0; k < cc.length; k++) {
/*  98 */       int c = cc[k];
/*  99 */       if ((c >= 1424) && (c < 1920))
/* 100 */         return true;
/*     */     }
/* 102 */     return false;
/*     */   }
/*     */ 
/*     */   private static void changeFontSize(Phrase p, float size) {
/* 106 */     for (int k = 0; k < p.size(); k++)
/* 107 */       ((Chunk)p.get(k)).getFont().setSize(size);
/*     */   }
/*     */ 
/*     */   private Phrase composePhrase(String text, BaseFont ufont, BaseColor color, float fontSize) {
/* 111 */     Phrase phrase = null;
/* 112 */     if ((this.extensionFont == null) && ((this.substitutionFonts == null) || (this.substitutionFonts.isEmpty()))) {
/* 113 */       phrase = new Phrase(new Chunk(text, new Font(ufont, fontSize, 0, color)));
/*     */     } else {
/* 115 */       FontSelector fs = new FontSelector();
/* 116 */       fs.addFont(new Font(ufont, fontSize, 0, color));
/* 117 */       if (this.extensionFont != null)
/* 118 */         fs.addFont(new Font(this.extensionFont, fontSize, 0, color));
/* 119 */       if (this.substitutionFonts != null) {
/* 120 */         for (int k = 0; k < this.substitutionFonts.size(); k++)
/* 121 */           fs.addFont(new Font((BaseFont)this.substitutionFonts.get(k), fontSize, 0, color));
/*     */       }
/* 123 */       phrase = fs.process(text);
/*     */     }
/* 125 */     return phrase;
/*     */   }
/*     */ 
/*     */   public static String removeCRLF(String text)
/*     */   {
/* 136 */     if ((text.indexOf('\n') >= 0) || (text.indexOf('\r') >= 0)) {
/* 137 */       char[] p = text.toCharArray();
/* 138 */       StringBuffer sb = new StringBuffer(p.length);
/* 139 */       for (int k = 0; k < p.length; k++) {
/* 140 */         char c = p[k];
/* 141 */         if (c == '\n') {
/* 142 */           sb.append(' ');
/* 143 */         } else if (c == '\r') {
/* 144 */           sb.append(' ');
/* 145 */           if ((k < p.length - 1) && (p[(k + 1)] == '\n'))
/* 146 */             k++;
/*     */         }
/*     */         else {
/* 149 */           sb.append(c);
/*     */         }
/*     */       }
/* 151 */       return sb.toString();
/*     */     }
/* 153 */     return text;
/*     */   }
/*     */ 
/*     */   public static String obfuscatePassword(String text)
/*     */   {
/* 165 */     char[] pchar = new char[text.length()];
/* 166 */     for (int i = 0; i < text.length(); i++)
/* 167 */       pchar[i] = '*';
/* 168 */     return new String(pchar);
/*     */   }
/*     */ 
/*     */   public PdfAppearance getAppearance()
/*     */     throws IOException, DocumentException
/*     */   {
/* 178 */     PdfAppearance app = getBorderAppearance();
/* 179 */     app.beginVariableText();
/* 180 */     if ((this.text == null) || (this.text.length() == 0)) {
/* 181 */       app.endVariableText();
/* 182 */       return app;
/*     */     }
/*     */ 
/* 185 */     boolean borderExtra = (this.borderStyle == 2) || (this.borderStyle == 3);
/* 186 */     float h = this.box.getHeight() - this.borderWidth * 2.0F - this.extraMarginTop;
/* 187 */     float bw2 = this.borderWidth;
/* 188 */     if (borderExtra) {
/* 189 */       h -= this.borderWidth * 2.0F;
/* 190 */       bw2 *= 2.0F;
/*     */     }
/* 192 */     float offsetX = Math.max(bw2, 1.0F);
/* 193 */     float offX = Math.min(bw2, offsetX);
/* 194 */     app.saveState();
/* 195 */     app.rectangle(offX, offX, this.box.getWidth() - 2.0F * offX, this.box.getHeight() - 2.0F * offX);
/* 196 */     app.clip();
/* 197 */     app.newPath();
/*     */     String ptext;
/*     */     String ptext;
/* 199 */     if ((this.options & 0x2000) != 0) {
/* 200 */       ptext = obfuscatePassword(this.text);
/*     */     }
/*     */     else
/*     */     {
/*     */       String ptext;
/* 201 */       if ((this.options & 0x1000) == 0)
/* 202 */         ptext = removeCRLF(this.text);
/*     */       else
/* 204 */         ptext = this.text; 
/*     */     }
/* 205 */     BaseFont ufont = getRealFont();
/* 206 */     BaseColor fcolor = this.textColor == null ? GrayColor.GRAYBLACK : this.textColor;
/* 207 */     int rtl = checkRTL(ptext) ? 2 : 1;
/* 208 */     float usize = this.fontSize;
/* 209 */     Phrase phrase = composePhrase(ptext, ufont, fcolor, usize);
/* 210 */     if ((this.options & 0x1000) != 0) {
/* 211 */       float width = this.box.getWidth() - 4.0F * offsetX - this.extraMarginLeft;
/* 212 */       float factor = ufont.getFontDescriptor(8, 1.0F) - ufont.getFontDescriptor(6, 1.0F);
/* 213 */       ColumnText ct = new ColumnText(null);
/* 214 */       if (usize == 0.0F) {
/* 215 */         usize = h / factor;
/* 216 */         if (usize > 4.0F) {
/* 217 */           if (usize > 12.0F)
/* 218 */             usize = 12.0F;
/* 219 */           float step = Math.max((usize - 4.0F) / 10.0F, 0.2F);
/* 220 */           ct.setSimpleColumn(0.0F, -h, width, 0.0F);
/* 221 */           ct.setAlignment(this.alignment);
/* 222 */           ct.setRunDirection(rtl);
/* 223 */           for (; usize > 4.0F; usize -= step) {
/* 224 */             ct.setYLine(0.0F);
/* 225 */             changeFontSize(phrase, usize);
/* 226 */             ct.setText(phrase);
/* 227 */             ct.setLeading(factor * usize);
/* 228 */             int status = ct.go(true);
/* 229 */             if ((status & 0x2) == 0)
/*     */               break;
/*     */           }
/*     */         }
/* 233 */         if (usize < 4.0F)
/* 234 */           usize = 4.0F;
/*     */       }
/* 236 */       changeFontSize(phrase, usize);
/* 237 */       ct.setCanvas(app);
/* 238 */       float leading = usize * factor;
/* 239 */       float offsetY = offsetX + h - ufont.getFontDescriptor(8, usize);
/* 240 */       ct.setSimpleColumn(this.extraMarginLeft + 2.0F * offsetX, -20000.0F, this.box.getWidth() - 2.0F * offsetX, offsetY + leading);
/* 241 */       ct.setLeading(leading);
/* 242 */       ct.setAlignment(this.alignment);
/* 243 */       ct.setRunDirection(rtl);
/* 244 */       ct.setText(phrase);
/* 245 */       ct.go();
/*     */     }
/*     */     else {
/* 248 */       if (usize == 0.0F) {
/* 249 */         float maxCalculatedSize = h / (ufont.getFontDescriptor(7, 1.0F) - ufont.getFontDescriptor(6, 1.0F));
/* 250 */         changeFontSize(phrase, 1.0F);
/* 251 */         float wd = ColumnText.getWidth(phrase, rtl, 0);
/* 252 */         if (wd == 0.0F)
/* 253 */           usize = maxCalculatedSize;
/*     */         else
/* 255 */           usize = Math.min(maxCalculatedSize, (this.box.getWidth() - this.extraMarginLeft - 4.0F * offsetX) / wd);
/* 256 */         if (usize < 4.0F)
/* 257 */           usize = 4.0F;
/*     */       }
/* 259 */       changeFontSize(phrase, usize);
/* 260 */       float offsetY = offX + (this.box.getHeight() - 2.0F * offX - ufont.getFontDescriptor(1, usize)) / 2.0F;
/* 261 */       if (offsetY < offX)
/* 262 */         offsetY = offX;
/* 263 */       if (offsetY - offX < -ufont.getFontDescriptor(3, usize)) {
/* 264 */         float ny = -ufont.getFontDescriptor(3, usize) + offX;
/* 265 */         float dy = this.box.getHeight() - offX - ufont.getFontDescriptor(1, usize);
/* 266 */         offsetY = Math.min(ny, Math.max(offsetY, dy));
/*     */       }
/* 268 */       if (((this.options & 0x1000000) != 0) && (this.maxCharacterLength > 0)) {
/* 269 */         int textLen = Math.min(this.maxCharacterLength, ptext.length());
/* 270 */         int position = 0;
/* 271 */         if (this.alignment == 2)
/* 272 */           position = this.maxCharacterLength - textLen;
/* 273 */         else if (this.alignment == 1)
/* 274 */           position = (this.maxCharacterLength - textLen) / 2;
/* 275 */         float step = (this.box.getWidth() - this.extraMarginLeft) / this.maxCharacterLength;
/* 276 */         float start = step / 2.0F + position * step;
/* 277 */         if (this.textColor == null)
/* 278 */           app.setGrayFill(0.0F);
/*     */         else
/* 280 */           app.setColorFill(this.textColor);
/* 281 */         app.beginText();
/* 282 */         for (int k = 0; k < phrase.size(); k++) {
/* 283 */           Chunk ck = (Chunk)phrase.get(k);
/* 284 */           BaseFont bf = ck.getFont().getBaseFont();
/* 285 */           app.setFontAndSize(bf, usize);
/* 286 */           StringBuffer sb = ck.append("");
/* 287 */           for (int j = 0; j < sb.length(); j++) {
/* 288 */             String c = sb.substring(j, j + 1);
/* 289 */             float wd = bf.getWidthPoint(c, usize);
/* 290 */             app.setTextMatrix(this.extraMarginLeft + start - wd / 2.0F, offsetY - this.extraMarginTop);
/* 291 */             app.showText(c);
/* 292 */             start += step;
/*     */           }
/*     */         }
/* 295 */         app.endText();
/*     */       }
/*     */       else
/*     */       {
/*     */         float x;
/* 299 */         switch (this.alignment) {
/*     */         case 2:
/* 301 */           x = this.extraMarginLeft + this.box.getWidth() - 2.0F * offsetX;
/* 302 */           break;
/*     */         case 1:
/* 304 */           x = this.extraMarginLeft + this.box.getWidth() / 2.0F;
/* 305 */           break;
/*     */         default:
/* 307 */           x = this.extraMarginLeft + 2.0F * offsetX;
/*     */         }
/* 309 */         ColumnText.showTextAligned(app, this.alignment, phrase, x, offsetY - this.extraMarginTop, 0.0F, rtl, 0);
/*     */       }
/*     */     }
/* 312 */     app.restoreState();
/* 313 */     app.endVariableText();
/* 314 */     return app;
/*     */   }
/*     */ 
/*     */   PdfAppearance getListAppearance()
/*     */     throws IOException, DocumentException
/*     */   {
/* 324 */     PdfAppearance app = getBorderAppearance();
/* 325 */     if ((this.choices == null) || (this.choices.length == 0)) {
/* 326 */       return app;
/*     */     }
/* 328 */     app.beginVariableText();
/*     */ 
/* 330 */     int topChoice = getTopChoice();
/*     */ 
/* 332 */     BaseFont ufont = getRealFont();
/* 333 */     float usize = this.fontSize;
/* 334 */     if (usize == 0.0F) {
/* 335 */       usize = 12.0F;
/*     */     }
/* 337 */     boolean borderExtra = (this.borderStyle == 2) || (this.borderStyle == 3);
/* 338 */     float h = this.box.getHeight() - this.borderWidth * 2.0F;
/* 339 */     float offsetX = this.borderWidth;
/* 340 */     if (borderExtra) {
/* 341 */       h -= this.borderWidth * 2.0F;
/* 342 */       offsetX *= 2.0F;
/*     */     }
/*     */ 
/* 345 */     float leading = ufont.getFontDescriptor(8, usize) - ufont.getFontDescriptor(6, usize);
/* 346 */     int maxFit = (int)(h / leading) + 1;
/* 347 */     int first = 0;
/* 348 */     int last = 0;
/* 349 */     first = topChoice;
/* 350 */     last = first + maxFit;
/* 351 */     if (last > this.choices.length)
/* 352 */       last = this.choices.length;
/* 353 */     this.topFirst = first;
/* 354 */     app.saveState();
/* 355 */     app.rectangle(offsetX, offsetX, this.box.getWidth() - 2.0F * offsetX, this.box.getHeight() - 2.0F * offsetX);
/* 356 */     app.clip();
/* 357 */     app.newPath();
/* 358 */     BaseColor fcolor = this.textColor == null ? GrayColor.GRAYBLACK : this.textColor;
/*     */ 
/* 362 */     app.setColorFill(new BaseColor(10, 36, 106));
/* 363 */     for (int curVal = 0; curVal < this.choiceSelections.size(); curVal++) {
/* 364 */       int curChoice = ((Integer)this.choiceSelections.get(curVal)).intValue();
/*     */ 
/* 367 */       if ((curChoice >= first) && (curChoice <= last)) {
/* 368 */         app.rectangle(offsetX, offsetX + h - (curChoice - first + 1) * leading, this.box.getWidth() - 2.0F * offsetX, leading);
/* 369 */         app.fill();
/*     */       }
/*     */     }
/* 372 */     float xp = offsetX * 2.0F;
/* 373 */     float yp = offsetX + h - ufont.getFontDescriptor(8, usize);
/* 374 */     for (int idx = first; idx < last; yp -= leading) {
/* 375 */       String ptext = this.choices[idx];
/* 376 */       int rtl = checkRTL(ptext) ? 2 : 1;
/* 377 */       ptext = removeCRLF(ptext);
/*     */ 
/* 379 */       BaseColor textCol = this.choiceSelections.contains(Integer.valueOf(idx)) ? GrayColor.GRAYWHITE : fcolor;
/* 380 */       Phrase phrase = composePhrase(ptext, ufont, textCol, usize);
/* 381 */       ColumnText.showTextAligned(app, 0, phrase, xp, yp, 0.0F, rtl, 0);
/*     */ 
/* 374 */       idx++;
/*     */     }
/*     */ 
/* 383 */     app.restoreState();
/* 384 */     app.endVariableText();
/* 385 */     return app;
/*     */   }
/*     */ 
/*     */   public PdfFormField getTextField()
/*     */     throws IOException, DocumentException
/*     */   {
/* 395 */     if (this.maxCharacterLength <= 0)
/* 396 */       this.options &= -16777217;
/* 397 */     if ((this.options & 0x1000000) != 0)
/* 398 */       this.options &= -4097;
/* 399 */     PdfFormField field = PdfFormField.createTextField(this.writer, false, false, this.maxCharacterLength);
/* 400 */     field.setWidget(this.box, PdfAnnotation.HIGHLIGHT_INVERT);
/* 401 */     switch (this.alignment) {
/*     */     case 1:
/* 403 */       field.setQuadding(1);
/* 404 */       break;
/*     */     case 2:
/* 406 */       field.setQuadding(2);
/*     */     }
/*     */ 
/* 409 */     if (this.rotation != 0)
/* 410 */       field.setMKRotation(this.rotation);
/* 411 */     if (this.fieldName != null) {
/* 412 */       field.setFieldName(this.fieldName);
/* 413 */       if (!"".equals(this.text))
/* 414 */         field.setValueAsString(this.text);
/* 415 */       if (this.defaultText != null)
/* 416 */         field.setDefaultValueAsString(this.defaultText);
/* 417 */       if ((this.options & 0x1) != 0)
/* 418 */         field.setFieldFlags(1);
/* 419 */       if ((this.options & 0x2) != 0)
/* 420 */         field.setFieldFlags(2);
/* 421 */       if ((this.options & 0x1000) != 0)
/* 422 */         field.setFieldFlags(4096);
/* 423 */       if ((this.options & 0x800000) != 0)
/* 424 */         field.setFieldFlags(8388608);
/* 425 */       if ((this.options & 0x2000) != 0)
/* 426 */         field.setFieldFlags(8192);
/* 427 */       if ((this.options & 0x100000) != 0)
/* 428 */         field.setFieldFlags(1048576);
/* 429 */       if ((this.options & 0x400000) != 0)
/* 430 */         field.setFieldFlags(4194304);
/* 431 */       if ((this.options & 0x1000000) != 0)
/* 432 */         field.setFieldFlags(16777216);
/*     */     }
/* 434 */     field.setBorderStyle(new PdfBorderDictionary(this.borderWidth, this.borderStyle, new PdfDashPattern(3.0F)));
/* 435 */     PdfAppearance tp = getAppearance();
/* 436 */     field.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, tp);
/* 437 */     PdfAppearance da = (PdfAppearance)tp.getDuplicate();
/* 438 */     da.setFontAndSize(getRealFont(), this.fontSize);
/* 439 */     if (this.textColor == null)
/* 440 */       da.setGrayFill(0.0F);
/*     */     else
/* 442 */       da.setColorFill(this.textColor);
/* 443 */     field.setDefaultAppearanceString(da);
/* 444 */     if (this.borderColor != null)
/* 445 */       field.setMKBorderColor(this.borderColor);
/* 446 */     if (this.backgroundColor != null)
/* 447 */       field.setMKBackgroundColor(this.backgroundColor);
/* 448 */     switch (this.visibility) {
/*     */     case 1:
/* 450 */       field.setFlags(6);
/* 451 */       break;
/*     */     case 2:
/* 453 */       break;
/*     */     case 3:
/* 455 */       field.setFlags(36);
/* 456 */       break;
/*     */     default:
/* 458 */       field.setFlags(4);
/*     */     }
/*     */ 
/* 461 */     return field;
/*     */   }
/*     */ 
/*     */   public PdfFormField getComboField()
/*     */     throws IOException, DocumentException
/*     */   {
/* 471 */     return getChoiceField(false);
/*     */   }
/*     */ 
/*     */   public PdfFormField getListField()
/*     */     throws IOException, DocumentException
/*     */   {
/* 481 */     return getChoiceField(true);
/*     */   }
/*     */ 
/*     */   private int getTopChoice() {
/* 485 */     if ((this.choiceSelections == null) || (this.choiceSelections.size() == 0)) {
/* 486 */       return 0;
/*     */     }
/*     */ 
/* 489 */     Integer firstValue = (Integer)this.choiceSelections.get(0);
/*     */ 
/* 491 */     if (firstValue == null) {
/* 492 */       return 0;
/*     */     }
/*     */ 
/* 495 */     int topChoice = 0;
/* 496 */     if (this.choices != null) {
/* 497 */       topChoice = firstValue.intValue();
/* 498 */       topChoice = Math.min(topChoice, this.choices.length);
/* 499 */       topChoice = Math.max(0, topChoice);
/*     */     }
/* 501 */     return topChoice;
/*     */   }
/*     */ 
/*     */   protected PdfFormField getChoiceField(boolean isList) throws IOException, DocumentException {
/* 505 */     this.options &= -16781313;
/* 506 */     String[] uchoices = this.choices;
/* 507 */     if (uchoices == null) {
/* 508 */       uchoices = new String[0];
/*     */     }
/* 510 */     int topChoice = getTopChoice();
/*     */ 
/* 512 */     if ((uchoices.length > 0) && (topChoice >= 0)) {
/* 513 */       this.text = uchoices[topChoice];
/*     */     }
/* 515 */     if (this.text == null) {
/* 516 */       this.text = "";
/*     */     }
/* 518 */     PdfFormField field = null;
/* 519 */     String[][] mix = (String[][])null;
/*     */ 
/* 521 */     if (this.choiceExports == null) {
/* 522 */       if (isList)
/* 523 */         field = PdfFormField.createList(this.writer, uchoices, topChoice);
/*     */       else
/* 525 */         field = PdfFormField.createCombo(this.writer, (this.options & 0x40000) != 0, uchoices, topChoice);
/*     */     }
/*     */     else {
/* 528 */       mix = new String[uchoices.length][2];
/* 529 */       for (int k = 0; k < mix.length; k++)
/*     */       {
/*     */         String tmp158_157 = uchoices[k]; mix[k][1] = tmp158_157; mix[k][0] = tmp158_157;
/* 531 */       }int top = Math.min(uchoices.length, this.choiceExports.length);
/* 532 */       for (int k = 0; k < top; k++) {
/* 533 */         if (this.choiceExports[k] != null)
/* 534 */           mix[k][0] = this.choiceExports[k];
/*     */       }
/* 536 */       if (isList)
/* 537 */         field = PdfFormField.createList(this.writer, mix, topChoice);
/*     */       else
/* 539 */         field = PdfFormField.createCombo(this.writer, (this.options & 0x40000) != 0, mix, topChoice);
/*     */     }
/* 541 */     field.setWidget(this.box, PdfAnnotation.HIGHLIGHT_INVERT);
/* 542 */     if (this.rotation != 0)
/* 543 */       field.setMKRotation(this.rotation);
/* 544 */     if (this.fieldName != null) {
/* 545 */       field.setFieldName(this.fieldName);
/* 546 */       if (uchoices.length > 0) {
/* 547 */         if (mix != null) {
/* 548 */           if (this.choiceSelections.size() < 2) {
/* 549 */             field.setValueAsString(mix[topChoice][0]);
/* 550 */             field.setDefaultValueAsString(mix[topChoice][0]);
/*     */           } else {
/* 552 */             writeMultipleValues(field, mix);
/*     */           }
/*     */         }
/* 555 */         else if (this.choiceSelections.size() < 2) {
/* 556 */           field.setValueAsString(this.text);
/* 557 */           field.setDefaultValueAsString(this.text);
/*     */         } else {
/* 559 */           writeMultipleValues(field, (String[][])null);
/*     */         }
/*     */       }
/*     */ 
/* 563 */       if ((this.options & 0x1) != 0)
/* 564 */         field.setFieldFlags(1);
/* 565 */       if ((this.options & 0x2) != 0)
/* 566 */         field.setFieldFlags(2);
/* 567 */       if ((this.options & 0x400000) != 0)
/* 568 */         field.setFieldFlags(4194304);
/* 569 */       if ((this.options & 0x200000) != 0) {
/* 570 */         field.setFieldFlags(2097152);
/*     */       }
/*     */     }
/* 573 */     field.setBorderStyle(new PdfBorderDictionary(this.borderWidth, this.borderStyle, new PdfDashPattern(3.0F)));
/*     */     PdfAppearance tp;
/* 575 */     if (isList) {
/* 576 */       PdfAppearance tp = getListAppearance();
/* 577 */       if (this.topFirst > 0)
/* 578 */         field.put(PdfName.TI, new PdfNumber(this.topFirst));
/*     */     }
/*     */     else {
/* 581 */       tp = getAppearance();
/* 582 */     }field.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, tp);
/* 583 */     PdfAppearance da = (PdfAppearance)tp.getDuplicate();
/* 584 */     da.setFontAndSize(getRealFont(), this.fontSize);
/* 585 */     if (this.textColor == null)
/* 586 */       da.setGrayFill(0.0F);
/*     */     else
/* 588 */       da.setColorFill(this.textColor);
/* 589 */     field.setDefaultAppearanceString(da);
/* 590 */     if (this.borderColor != null)
/* 591 */       field.setMKBorderColor(this.borderColor);
/* 592 */     if (this.backgroundColor != null)
/* 593 */       field.setMKBackgroundColor(this.backgroundColor);
/* 594 */     switch (this.visibility) {
/*     */     case 1:
/* 596 */       field.setFlags(6);
/* 597 */       break;
/*     */     case 2:
/* 599 */       break;
/*     */     case 3:
/* 601 */       field.setFlags(36);
/* 602 */       break;
/*     */     default:
/* 604 */       field.setFlags(4);
/*     */     }
/*     */ 
/* 607 */     return field;
/*     */   }
/*     */ 
/*     */   private void writeMultipleValues(PdfFormField field, String[][] mix) {
/* 611 */     PdfArray indexes = new PdfArray();
/* 612 */     PdfArray values = new PdfArray();
/* 613 */     for (int i = 0; i < this.choiceSelections.size(); i++) {
/* 614 */       int idx = ((Integer)this.choiceSelections.get(i)).intValue();
/* 615 */       indexes.add(new PdfNumber(idx));
/*     */ 
/* 617 */       if (mix != null)
/* 618 */         values.add(new PdfString(mix[idx][0]));
/* 619 */       else if (this.choices != null) {
/* 620 */         values.add(new PdfString(this.choices[idx]));
/*     */       }
/*     */     }
/* 623 */     field.put(PdfName.V, values);
/* 624 */     field.put(PdfName.I, indexes);
/*     */   }
/*     */ 
/*     */   public String getDefaultText()
/*     */   {
/* 633 */     return this.defaultText;
/*     */   }
/*     */ 
/*     */   public void setDefaultText(String defaultText)
/*     */   {
/* 641 */     this.defaultText = defaultText;
/*     */   }
/*     */ 
/*     */   public String[] getChoices()
/*     */   {
/* 649 */     return this.choices;
/*     */   }
/*     */ 
/*     */   public void setChoices(String[] choices)
/*     */   {
/* 657 */     this.choices = choices;
/*     */   }
/*     */ 
/*     */   public String[] getChoiceExports()
/*     */   {
/* 665 */     return this.choiceExports;
/*     */   }
/*     */ 
/*     */   public void setChoiceExports(String[] choiceExports)
/*     */   {
/* 675 */     this.choiceExports = choiceExports;
/*     */   }
/*     */ 
/*     */   public int getChoiceSelection()
/*     */   {
/* 683 */     return getTopChoice();
/*     */   }
/*     */ 
/*     */   public ArrayList<Integer> getChoiceSelections()
/*     */   {
/* 693 */     return this.choiceSelections;
/*     */   }
/*     */ 
/*     */   public void setChoiceSelection(int choiceSelection)
/*     */   {
/* 701 */     this.choiceSelections = new ArrayList();
/* 702 */     this.choiceSelections.add(Integer.valueOf(choiceSelection));
/*     */   }
/*     */ 
/*     */   public void addChoiceSelection(int selection)
/*     */   {
/* 711 */     if ((this.options & 0x200000) != 0)
/* 712 */       this.choiceSelections.add(Integer.valueOf(selection));
/*     */   }
/*     */ 
/*     */   public void setChoiceSelections(ArrayList<Integer> selections)
/*     */   {
/* 722 */     if (selections != null) {
/* 723 */       this.choiceSelections = new ArrayList(selections);
/* 724 */       if ((this.choiceSelections.size() > 1) && ((this.options & 0x200000) == 0))
/*     */       {
/* 726 */         while (this.choiceSelections.size() > 1)
/* 727 */           this.choiceSelections.remove(1);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 732 */       this.choiceSelections.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */   int getTopFirst() {
/* 737 */     return this.topFirst;
/*     */   }
/*     */ 
/*     */   public void setExtraMargin(float extraMarginLeft, float extraMarginTop)
/*     */   {
/* 746 */     this.extraMarginLeft = extraMarginLeft;
/* 747 */     this.extraMarginTop = extraMarginTop;
/*     */   }
/*     */ 
/*     */   public ArrayList<BaseFont> getSubstitutionFonts()
/*     */   {
/* 761 */     return this.substitutionFonts;
/*     */   }
/*     */ 
/*     */   public void setSubstitutionFonts(ArrayList<BaseFont> substitutionFonts)
/*     */   {
/* 770 */     this.substitutionFonts = substitutionFonts;
/*     */   }
/*     */ 
/*     */   public BaseFont getExtensionFont()
/*     */   {
/* 784 */     return this.extensionFont;
/*     */   }
/*     */ 
/*     */   public void setExtensionFont(BaseFont extensionFont)
/*     */   {
/* 793 */     this.extensionFont = extensionFont;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.TextField
 * JD-Core Version:    0.6.2
 */