/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import com.itextpdf.text.Image;
/*     */ import com.itextpdf.text.Rectangle;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import java.io.IOException;
/*     */ 
/*     */ public class PushbuttonField extends BaseField
/*     */ {
/*     */   public static final int LAYOUT_LABEL_ONLY = 1;
/*     */   public static final int LAYOUT_ICON_ONLY = 2;
/*     */   public static final int LAYOUT_ICON_TOP_LABEL_BOTTOM = 3;
/*     */   public static final int LAYOUT_LABEL_TOP_ICON_BOTTOM = 4;
/*     */   public static final int LAYOUT_ICON_LEFT_LABEL_RIGHT = 5;
/*     */   public static final int LAYOUT_LABEL_LEFT_ICON_RIGHT = 6;
/*     */   public static final int LAYOUT_LABEL_OVER_ICON = 7;
/*     */   public static final int SCALE_ICON_ALWAYS = 1;
/*     */   public static final int SCALE_ICON_NEVER = 2;
/*     */   public static final int SCALE_ICON_IS_TOO_BIG = 3;
/*     */   public static final int SCALE_ICON_IS_TOO_SMALL = 4;
/* 110 */   private int layout = 1;
/*     */   private Image image;
/*     */   private PdfTemplate template;
/* 125 */   private int scaleIcon = 1;
/*     */ 
/* 130 */   private boolean proportionalIcon = true;
/*     */ 
/* 135 */   private float iconVerticalAdjustment = 0.5F;
/*     */ 
/* 140 */   private float iconHorizontalAdjustment = 0.5F;
/*     */   private boolean iconFitToBounds;
/*     */   private PdfTemplate tp;
/*     */   private PRIndirectReference iconReference;
/*     */ 
/*     */   public PushbuttonField(PdfWriter writer, Rectangle box, String fieldName)
/*     */   {
/* 157 */     super(writer, box, fieldName);
/*     */   }
/*     */ 
/*     */   public int getLayout()
/*     */   {
/* 165 */     return this.layout;
/*     */   }
/*     */ 
/*     */   public void setLayout(int layout)
/*     */   {
/* 177 */     if ((layout < 1) || (layout > 7))
/* 178 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("layout.out.of.bounds", new Object[0]));
/* 179 */     this.layout = layout;
/*     */   }
/*     */ 
/*     */   public Image getImage()
/*     */   {
/* 187 */     return this.image;
/*     */   }
/*     */ 
/*     */   public void setImage(Image image)
/*     */   {
/* 195 */     this.image = image;
/* 196 */     this.template = null;
/*     */   }
/*     */ 
/*     */   public PdfTemplate getTemplate()
/*     */   {
/* 204 */     return this.template;
/*     */   }
/*     */ 
/*     */   public void setTemplate(PdfTemplate template)
/*     */   {
/* 212 */     this.template = template;
/* 213 */     this.image = null;
/*     */   }
/*     */ 
/*     */   public int getScaleIcon()
/*     */   {
/* 221 */     return this.scaleIcon;
/*     */   }
/*     */ 
/*     */   public void setScaleIcon(int scaleIcon)
/*     */   {
/* 232 */     if ((scaleIcon < 1) || (scaleIcon > 4))
/* 233 */       scaleIcon = 1;
/* 234 */     this.scaleIcon = scaleIcon;
/*     */   }
/*     */ 
/*     */   public boolean isProportionalIcon()
/*     */   {
/* 242 */     return this.proportionalIcon;
/*     */   }
/*     */ 
/*     */   public void setProportionalIcon(boolean proportionalIcon)
/*     */   {
/* 251 */     this.proportionalIcon = proportionalIcon;
/*     */   }
/*     */ 
/*     */   public float getIconVerticalAdjustment()
/*     */   {
/* 259 */     return this.iconVerticalAdjustment;
/*     */   }
/*     */ 
/*     */   public void setIconVerticalAdjustment(float iconVerticalAdjustment)
/*     */   {
/* 269 */     if (iconVerticalAdjustment < 0.0F)
/* 270 */       iconVerticalAdjustment = 0.0F;
/* 271 */     else if (iconVerticalAdjustment > 1.0F)
/* 272 */       iconVerticalAdjustment = 1.0F;
/* 273 */     this.iconVerticalAdjustment = iconVerticalAdjustment;
/*     */   }
/*     */ 
/*     */   public float getIconHorizontalAdjustment()
/*     */   {
/* 281 */     return this.iconHorizontalAdjustment;
/*     */   }
/*     */ 
/*     */   public void setIconHorizontalAdjustment(float iconHorizontalAdjustment)
/*     */   {
/* 291 */     if (iconHorizontalAdjustment < 0.0F)
/* 292 */       iconHorizontalAdjustment = 0.0F;
/* 293 */     else if (iconHorizontalAdjustment > 1.0F)
/* 294 */       iconHorizontalAdjustment = 1.0F;
/* 295 */     this.iconHorizontalAdjustment = iconHorizontalAdjustment;
/*     */   }
/*     */ 
/*     */   private float calculateFontSize(float w, float h) throws IOException, DocumentException {
/* 299 */     BaseFont ufont = getRealFont();
/* 300 */     float fsize = this.fontSize;
/* 301 */     if (fsize == 0.0F) {
/* 302 */       float bw = ufont.getWidthPoint(this.text, 1.0F);
/* 303 */       if (bw == 0.0F)
/* 304 */         fsize = 12.0F;
/*     */       else
/* 306 */         fsize = w / bw;
/* 307 */       float nfsize = h / (1.0F - ufont.getFontDescriptor(3, 1.0F));
/* 308 */       fsize = Math.min(fsize, nfsize);
/* 309 */       if (fsize < 4.0F)
/* 310 */         fsize = 4.0F;
/*     */     }
/* 312 */     return fsize;
/*     */   }
/*     */ 
/*     */   public PdfAppearance getAppearance()
/*     */     throws IOException, DocumentException
/*     */   {
/* 322 */     PdfAppearance app = getBorderAppearance();
/* 323 */     Rectangle box = new Rectangle(app.getBoundingBox());
/* 324 */     if (((this.text == null) || (this.text.length() == 0)) && ((this.layout == 1) || ((this.image == null) && (this.template == null) && (this.iconReference == null)))) {
/* 325 */       return app;
/*     */     }
/* 327 */     if ((this.layout == 2) && (this.image == null) && (this.template == null) && (this.iconReference == null))
/* 328 */       return app;
/* 329 */     BaseFont ufont = getRealFont();
/* 330 */     boolean borderExtra = (this.borderStyle == 2) || (this.borderStyle == 3);
/* 331 */     float h = box.getHeight() - this.borderWidth * 2.0F;
/* 332 */     float bw2 = this.borderWidth;
/* 333 */     if (borderExtra) {
/* 334 */       h -= this.borderWidth * 2.0F;
/* 335 */       bw2 *= 2.0F;
/*     */     }
/* 337 */     float offsetX = borderExtra ? 2.0F * this.borderWidth : this.borderWidth;
/* 338 */     offsetX = Math.max(offsetX, 1.0F);
/* 339 */     float offX = Math.min(bw2, offsetX);
/* 340 */     this.tp = null;
/* 341 */     float textX = (0.0F / 0.0F);
/* 342 */     float textY = 0.0F;
/* 343 */     float fsize = this.fontSize;
/* 344 */     float wt = box.getWidth() - 2.0F * offX - 2.0F;
/* 345 */     float ht = box.getHeight() - 2.0F * offX;
/* 346 */     float adj = this.iconFitToBounds ? 0.0F : offX + 1.0F;
/* 347 */     int nlayout = this.layout;
/* 348 */     if ((this.image == null) && (this.template == null) && (this.iconReference == null))
/* 349 */       nlayout = 1;
/* 350 */     Rectangle iconBox = null;
/*     */     while (true)
/*     */     {
/*     */       float nht;
/*     */       float nw;
/* 352 */       switch (nlayout) {
/*     */       case 1:
/*     */       case 7:
/* 355 */         if ((this.text != null) && (this.text.length() > 0) && (wt > 0.0F) && (ht > 0.0F)) {
/* 356 */           fsize = calculateFontSize(wt, ht);
/* 357 */           textX = (box.getWidth() - ufont.getWidthPoint(this.text, fsize)) / 2.0F;
/* 358 */           textY = (box.getHeight() - ufont.getFontDescriptor(1, fsize)) / 2.0F;
/*     */         }
/*     */       case 2:
/* 361 */         if ((nlayout != 7) && (nlayout != 2)) break label1112;
/* 362 */         iconBox = new Rectangle(box.getLeft() + adj, box.getBottom() + adj, box.getRight() - adj, box.getTop() - adj); break;
/*     */       case 3:
/* 365 */         if ((this.text == null) || (this.text.length() == 0) || (wt <= 0.0F) || (ht <= 0.0F)) {
/* 366 */           nlayout = 2;
/*     */         }
/*     */         else {
/* 369 */           nht = box.getHeight() * 0.35F - offX;
/* 370 */           if (nht > 0.0F)
/* 371 */             fsize = calculateFontSize(wt, nht);
/*     */           else
/* 373 */             fsize = 4.0F;
/* 374 */           textX = (box.getWidth() - ufont.getWidthPoint(this.text, fsize)) / 2.0F;
/* 375 */           textY = offX - ufont.getFontDescriptor(3, fsize);
/* 376 */           iconBox = new Rectangle(box.getLeft() + adj, textY + fsize, box.getRight() - adj, box.getTop() - adj);
/* 377 */         }break;
/*     */       case 4:
/* 379 */         if ((this.text == null) || (this.text.length() == 0) || (wt <= 0.0F) || (ht <= 0.0F)) {
/* 380 */           nlayout = 2;
/*     */         }
/*     */         else {
/* 383 */           nht = box.getHeight() * 0.35F - offX;
/* 384 */           if (nht > 0.0F)
/* 385 */             fsize = calculateFontSize(wt, nht);
/*     */           else
/* 387 */             fsize = 4.0F;
/* 388 */           textX = (box.getWidth() - ufont.getWidthPoint(this.text, fsize)) / 2.0F;
/* 389 */           textY = box.getHeight() - offX - fsize;
/* 390 */           if (textY < offX)
/* 391 */             textY = offX;
/* 392 */           iconBox = new Rectangle(box.getLeft() + adj, box.getBottom() + adj, box.getRight() - adj, textY + ufont.getFontDescriptor(3, fsize));
/* 393 */         }break;
/*     */       case 6:
/* 395 */         if ((this.text == null) || (this.text.length() == 0) || (wt <= 0.0F) || (ht <= 0.0F)) {
/* 396 */           nlayout = 2;
/*     */         }
/*     */         else {
/* 399 */           nw = box.getWidth() * 0.35F - offX;
/* 400 */           if (nw > 0.0F)
/* 401 */             fsize = calculateFontSize(wt, nw);
/*     */           else
/* 403 */             fsize = 4.0F;
/* 404 */           if (ufont.getWidthPoint(this.text, fsize) >= wt) {
/* 405 */             nlayout = 1;
/* 406 */             fsize = this.fontSize;
/*     */           }
/*     */           else {
/* 409 */             textX = offX + 1.0F;
/* 410 */             textY = (box.getHeight() - ufont.getFontDescriptor(1, fsize)) / 2.0F;
/* 411 */             iconBox = new Rectangle(textX + ufont.getWidthPoint(this.text, fsize), box.getBottom() + adj, box.getRight() - adj, box.getTop() - adj); } 
/* 412 */         }break;
/*     */       case 5:
/* 414 */         if ((this.text == null) || (this.text.length() == 0) || (wt <= 0.0F) || (ht <= 0.0F)) {
/* 415 */           nlayout = 2;
/*     */         }
/*     */         else {
/* 418 */           nw = box.getWidth() * 0.35F - offX;
/* 419 */           if (nw > 0.0F)
/* 420 */             fsize = calculateFontSize(wt, nw);
/*     */           else
/* 422 */             fsize = 4.0F;
/* 423 */           if (ufont.getWidthPoint(this.text, fsize) < wt) break label1040;
/* 424 */           nlayout = 1;
/* 425 */           fsize = this.fontSize; } break;
/*     */       }
/*     */     }
/* 428 */     label1040: textX = box.getWidth() - ufont.getWidthPoint(this.text, fsize) - offX - 1.0F;
/* 429 */     textY = (box.getHeight() - ufont.getFontDescriptor(1, fsize)) / 2.0F;
/* 430 */     iconBox = new Rectangle(box.getLeft() + adj, box.getBottom() + adj, textX - 1.0F, box.getTop() - adj);
/*     */ 
/* 435 */     label1112: if (textY < box.getBottom() + offX)
/* 436 */       textY = box.getBottom() + offX;
/* 437 */     if ((iconBox != null) && ((iconBox.getWidth() <= 0.0F) || (iconBox.getHeight() <= 0.0F)))
/* 438 */       iconBox = null;
/* 439 */     boolean haveIcon = false;
/* 440 */     float boundingBoxWidth = 0.0F;
/* 441 */     float boundingBoxHeight = 0.0F;
/* 442 */     PdfArray matrix = null;
/* 443 */     if (iconBox != null) {
/* 444 */       if (this.image != null) {
/* 445 */         this.tp = new PdfTemplate(this.writer);
/* 446 */         this.tp.setBoundingBox(new Rectangle(this.image));
/* 447 */         this.writer.addDirectTemplateSimple(this.tp, PdfName.FRM);
/* 448 */         this.tp.addImage(this.image, this.image.getWidth(), 0.0F, 0.0F, this.image.getHeight(), 0.0F, 0.0F);
/* 449 */         haveIcon = true;
/* 450 */         boundingBoxWidth = this.tp.getBoundingBox().getWidth();
/* 451 */         boundingBoxHeight = this.tp.getBoundingBox().getHeight();
/*     */       }
/* 453 */       else if (this.template != null) {
/* 454 */         this.tp = new PdfTemplate(this.writer);
/* 455 */         this.tp.setBoundingBox(new Rectangle(this.template.getWidth(), this.template.getHeight()));
/* 456 */         this.writer.addDirectTemplateSimple(this.tp, PdfName.FRM);
/* 457 */         this.tp.addTemplate(this.template, this.template.getBoundingBox().getLeft(), this.template.getBoundingBox().getBottom());
/* 458 */         haveIcon = true;
/* 459 */         boundingBoxWidth = this.tp.getBoundingBox().getWidth();
/* 460 */         boundingBoxHeight = this.tp.getBoundingBox().getHeight();
/*     */       }
/* 462 */       else if (this.iconReference != null) {
/* 463 */         PdfDictionary dic = (PdfDictionary)PdfReader.getPdfObject(this.iconReference);
/* 464 */         if (dic != null) {
/* 465 */           Rectangle r2 = PdfReader.getNormalizedRectangle(dic.getAsArray(PdfName.BBOX));
/* 466 */           matrix = dic.getAsArray(PdfName.MATRIX);
/* 467 */           haveIcon = true;
/* 468 */           boundingBoxWidth = r2.getWidth();
/* 469 */           boundingBoxHeight = r2.getHeight();
/*     */         }
/*     */       }
/*     */     }
/* 473 */     if (haveIcon) {
/* 474 */       float icx = iconBox.getWidth() / boundingBoxWidth;
/* 475 */       float icy = iconBox.getHeight() / boundingBoxHeight;
/* 476 */       if (this.proportionalIcon) {
/* 477 */         switch (this.scaleIcon) {
/*     */         case 3:
/* 479 */           icx = Math.min(icx, icy);
/* 480 */           icx = Math.min(icx, 1.0F);
/* 481 */           break;
/*     */         case 4:
/* 483 */           icx = Math.min(icx, icy);
/* 484 */           icx = Math.max(icx, 1.0F);
/* 485 */           break;
/*     */         case 2:
/* 487 */           icx = 1.0F;
/* 488 */           break;
/*     */         default:
/* 490 */           icx = Math.min(icx, icy);
/*     */         }
/*     */ 
/* 493 */         icy = icx;
/*     */       }
/*     */       else {
/* 496 */         switch (this.scaleIcon) {
/*     */         case 3:
/* 498 */           icx = Math.min(icx, 1.0F);
/* 499 */           icy = Math.min(icy, 1.0F);
/* 500 */           break;
/*     */         case 4:
/* 502 */           icx = Math.max(icx, 1.0F);
/* 503 */           icy = Math.max(icy, 1.0F);
/* 504 */           break;
/*     */         case 2:
/* 506 */           icx = icy = 1.0F;
/* 507 */           break;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 512 */       float xpos = iconBox.getLeft() + (iconBox.getWidth() - boundingBoxWidth * icx) * this.iconHorizontalAdjustment;
/* 513 */       float ypos = iconBox.getBottom() + (iconBox.getHeight() - boundingBoxHeight * icy) * this.iconVerticalAdjustment;
/* 514 */       app.saveState();
/* 515 */       app.rectangle(iconBox.getLeft(), iconBox.getBottom(), iconBox.getWidth(), iconBox.getHeight());
/* 516 */       app.clip();
/* 517 */       app.newPath();
/* 518 */       if (this.tp != null) {
/* 519 */         app.addTemplate(this.tp, icx, 0.0F, 0.0F, icy, xpos, ypos);
/*     */       } else {
/* 521 */         float cox = 0.0F;
/* 522 */         float coy = 0.0F;
/* 523 */         if ((matrix != null) && (matrix.size() == 6)) {
/* 524 */           PdfNumber nm = matrix.getAsNumber(4);
/* 525 */           if (nm != null)
/* 526 */             cox = nm.floatValue();
/* 527 */           nm = matrix.getAsNumber(5);
/* 528 */           if (nm != null)
/* 529 */             coy = nm.floatValue();
/*     */         }
/* 531 */         app.addTemplateReference(this.iconReference, PdfName.FRM, icx, 0.0F, 0.0F, icy, xpos - cox * icx, ypos - coy * icy);
/*     */       }
/* 533 */       app.restoreState();
/*     */     }
/* 535 */     if (!Float.isNaN(textX)) {
/* 536 */       app.saveState();
/* 537 */       app.rectangle(offX, offX, box.getWidth() - 2.0F * offX, box.getHeight() - 2.0F * offX);
/* 538 */       app.clip();
/* 539 */       app.newPath();
/* 540 */       if (this.textColor == null)
/* 541 */         app.resetGrayFill();
/*     */       else
/* 543 */         app.setColorFill(this.textColor);
/* 544 */       app.beginText();
/* 545 */       app.setFontAndSize(ufont, fsize);
/* 546 */       app.setTextMatrix(textX, textY);
/* 547 */       app.showText(this.text);
/* 548 */       app.endText();
/* 549 */       app.restoreState();
/*     */     }
/* 551 */     return app;
/*     */   }
/*     */ 
/*     */   public PdfFormField getField()
/*     */     throws IOException, DocumentException
/*     */   {
/* 561 */     PdfFormField field = PdfFormField.createPushButton(this.writer);
/* 562 */     field.setWidget(this.box, PdfAnnotation.HIGHLIGHT_INVERT);
/* 563 */     if (this.fieldName != null) {
/* 564 */       field.setFieldName(this.fieldName);
/* 565 */       if ((this.options & 0x1) != 0)
/* 566 */         field.setFieldFlags(1);
/* 567 */       if ((this.options & 0x2) != 0)
/* 568 */         field.setFieldFlags(2);
/*     */     }
/* 570 */     if (this.text != null)
/* 571 */       field.setMKNormalCaption(this.text);
/* 572 */     if (this.rotation != 0)
/* 573 */       field.setMKRotation(this.rotation);
/* 574 */     field.setBorderStyle(new PdfBorderDictionary(this.borderWidth, this.borderStyle, new PdfDashPattern(3.0F)));
/* 575 */     PdfAppearance tpa = getAppearance();
/* 576 */     field.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, tpa);
/* 577 */     PdfAppearance da = (PdfAppearance)tpa.getDuplicate();
/* 578 */     da.setFontAndSize(getRealFont(), this.fontSize);
/* 579 */     if (this.textColor == null)
/* 580 */       da.setGrayFill(0.0F);
/*     */     else
/* 582 */       da.setColorFill(this.textColor);
/* 583 */     field.setDefaultAppearanceString(da);
/* 584 */     if (this.borderColor != null)
/* 585 */       field.setMKBorderColor(this.borderColor);
/* 586 */     if (this.backgroundColor != null)
/* 587 */       field.setMKBackgroundColor(this.backgroundColor);
/* 588 */     switch (this.visibility) {
/*     */     case 1:
/* 590 */       field.setFlags(6);
/* 591 */       break;
/*     */     case 2:
/* 593 */       break;
/*     */     case 3:
/* 595 */       field.setFlags(36);
/* 596 */       break;
/*     */     default:
/* 598 */       field.setFlags(4);
/*     */     }
/*     */ 
/* 601 */     if (this.tp != null)
/* 602 */       field.setMKNormalIcon(this.tp);
/* 603 */     field.setMKTextPosition(this.layout - 1);
/* 604 */     PdfName scale = PdfName.A;
/* 605 */     if (this.scaleIcon == 3)
/* 606 */       scale = PdfName.B;
/* 607 */     else if (this.scaleIcon == 4)
/* 608 */       scale = PdfName.S;
/* 609 */     else if (this.scaleIcon == 2)
/* 610 */       scale = PdfName.N;
/* 611 */     field.setMKIconFit(scale, this.proportionalIcon ? PdfName.P : PdfName.A, this.iconHorizontalAdjustment, this.iconVerticalAdjustment, this.iconFitToBounds);
/*     */ 
/* 613 */     return field;
/*     */   }
/*     */ 
/*     */   public boolean isIconFitToBounds()
/*     */   {
/* 621 */     return this.iconFitToBounds;
/*     */   }
/*     */ 
/*     */   public void setIconFitToBounds(boolean iconFitToBounds)
/*     */   {
/* 632 */     this.iconFitToBounds = iconFitToBounds;
/*     */   }
/*     */ 
/*     */   public PRIndirectReference getIconReference()
/*     */   {
/* 645 */     return this.iconReference;
/*     */   }
/*     */ 
/*     */   public void setIconReference(PRIndirectReference iconReference)
/*     */   {
/* 653 */     this.iconReference = iconReference;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PushbuttonField
 * JD-Core Version:    0.6.2
 */