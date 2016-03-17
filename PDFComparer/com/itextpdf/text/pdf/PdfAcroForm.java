/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.BaseColor;
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import com.itextpdf.text.Rectangle;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.HashSet;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public class PdfAcroForm extends PdfDictionary
/*     */ {
/*     */   private PdfWriter writer;
/*  66 */   private HashSet<PdfTemplate> fieldTemplates = new HashSet();
/*     */ 
/*  69 */   private PdfArray documentFields = new PdfArray();
/*     */ 
/*  72 */   private PdfArray calculationOrder = new PdfArray();
/*     */ 
/*  75 */   private int sigFlags = 0;
/*     */ 
/*     */   public PdfAcroForm(PdfWriter writer)
/*     */   {
/*  82 */     this.writer = writer;
/*     */   }
/*     */ 
/*     */   public void setNeedAppearances(boolean value) {
/*  86 */     put(PdfName.NEEDAPPEARANCES, new PdfBoolean(value));
/*     */   }
/*     */ 
/*     */   public void addFieldTemplates(HashSet<PdfTemplate> ft)
/*     */   {
/*  95 */     this.fieldTemplates.addAll(ft);
/*     */   }
/*     */ 
/*     */   public void addDocumentField(PdfIndirectReference ref)
/*     */   {
/* 104 */     this.documentFields.add(ref);
/*     */   }
/*     */ 
/*     */   public boolean isValid()
/*     */   {
/* 113 */     if (this.documentFields.size() == 0) return false;
/* 114 */     put(PdfName.FIELDS, this.documentFields);
/* 115 */     if (this.sigFlags != 0)
/* 116 */       put(PdfName.SIGFLAGS, new PdfNumber(this.sigFlags));
/* 117 */     if (this.calculationOrder.size() > 0)
/* 118 */       put(PdfName.CO, this.calculationOrder);
/* 119 */     if (this.fieldTemplates.isEmpty()) return true;
/* 120 */     PdfDictionary dic = new PdfDictionary();
/* 121 */     for (PdfTemplate template : this.fieldTemplates) {
/* 122 */       PdfFormField.mergeResources(dic, (PdfDictionary)template.getResources());
/*     */     }
/* 124 */     put(PdfName.DR, dic);
/* 125 */     put(PdfName.DA, new PdfString("/Helv 0 Tf 0 g "));
/* 126 */     PdfDictionary fonts = (PdfDictionary)dic.get(PdfName.FONT);
/* 127 */     if (fonts != null) {
/* 128 */       this.writer.eliminateFontSubset(fonts);
/*     */     }
/* 130 */     return true;
/*     */   }
/*     */ 
/*     */   public void addCalculationOrder(PdfFormField formField)
/*     */   {
/* 139 */     this.calculationOrder.add(formField.getIndirectReference());
/*     */   }
/*     */ 
/*     */   public void setSigFlags(int f)
/*     */   {
/* 148 */     this.sigFlags |= f;
/*     */   }
/*     */ 
/*     */   public void addFormField(PdfFormField formField)
/*     */   {
/* 157 */     this.writer.addAnnotation(formField);
/*     */   }
/*     */ 
/*     */   public PdfFormField addHtmlPostButton(String name, String caption, String value, String url, BaseFont font, float fontSize, float llx, float lly, float urx, float ury)
/*     */   {
/* 174 */     PdfAction action = PdfAction.createSubmitForm(url, null, 4);
/* 175 */     PdfFormField button = new PdfFormField(this.writer, llx, lly, urx, ury, action);
/* 176 */     setButtonParams(button, 65536, name, value);
/* 177 */     drawButton(button, caption, font, fontSize, llx, lly, urx, ury);
/* 178 */     addFormField(button);
/* 179 */     return button;
/*     */   }
/*     */ 
/*     */   public PdfFormField addResetButton(String name, String caption, String value, BaseFont font, float fontSize, float llx, float lly, float urx, float ury)
/*     */   {
/* 195 */     PdfAction action = PdfAction.createResetForm(null, 0);
/* 196 */     PdfFormField button = new PdfFormField(this.writer, llx, lly, urx, ury, action);
/* 197 */     setButtonParams(button, 65536, name, value);
/* 198 */     drawButton(button, caption, font, fontSize, llx, lly, urx, ury);
/* 199 */     addFormField(button);
/* 200 */     return button;
/*     */   }
/*     */ 
/*     */   public PdfFormField addMap(String name, String value, String url, PdfContentByte appearance, float llx, float lly, float urx, float ury)
/*     */   {
/* 215 */     PdfAction action = PdfAction.createSubmitForm(url, null, 20);
/* 216 */     PdfFormField button = new PdfFormField(this.writer, llx, lly, urx, ury, action);
/* 217 */     setButtonParams(button, 65536, name, null);
/* 218 */     PdfAppearance pa = PdfAppearance.createAppearance(this.writer, urx - llx, ury - lly);
/* 219 */     pa.add(appearance);
/* 220 */     button.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, pa);
/* 221 */     addFormField(button);
/* 222 */     return button;
/*     */   }
/*     */ 
/*     */   public void setButtonParams(PdfFormField button, int characteristics, String name, String value)
/*     */   {
/* 232 */     button.setButton(characteristics);
/* 233 */     button.setFlags(4);
/* 234 */     button.setPage();
/* 235 */     button.setFieldName(name);
/* 236 */     if (value != null) button.setValueAsString(value);
/*     */   }
/*     */ 
/*     */   public void drawButton(PdfFormField button, String caption, BaseFont font, float fontSize, float llx, float lly, float urx, float ury)
/*     */   {
/* 250 */     PdfAppearance pa = PdfAppearance.createAppearance(this.writer, urx - llx, ury - lly);
/* 251 */     pa.drawButton(0.0F, 0.0F, urx - llx, ury - lly, caption, font, fontSize);
/* 252 */     button.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, pa);
/*     */   }
/*     */ 
/*     */   public PdfFormField addHiddenField(String name, String value)
/*     */   {
/* 261 */     PdfFormField hidden = PdfFormField.createEmpty(this.writer);
/* 262 */     hidden.setFieldName(name);
/* 263 */     hidden.setValueAsName(value);
/* 264 */     addFormField(hidden);
/* 265 */     return hidden;
/*     */   }
/*     */ 
/*     */   public PdfFormField addSingleLineTextField(String name, String text, BaseFont font, float fontSize, float llx, float lly, float urx, float ury)
/*     */   {
/* 280 */     PdfFormField field = PdfFormField.createTextField(this.writer, false, false, 0);
/* 281 */     setTextFieldParams(field, text, name, llx, lly, urx, ury);
/* 282 */     drawSingleLineOfText(field, text, font, fontSize, llx, lly, urx, ury);
/* 283 */     addFormField(field);
/* 284 */     return field;
/*     */   }
/*     */ 
/*     */   public PdfFormField addMultiLineTextField(String name, String text, BaseFont font, float fontSize, float llx, float lly, float urx, float ury)
/*     */   {
/* 299 */     PdfFormField field = PdfFormField.createTextField(this.writer, true, false, 0);
/* 300 */     setTextFieldParams(field, text, name, llx, lly, urx, ury);
/* 301 */     drawMultiLineOfText(field, text, font, fontSize, llx, lly, urx, ury);
/* 302 */     addFormField(field);
/* 303 */     return field;
/*     */   }
/*     */ 
/*     */   public PdfFormField addSingleLinePasswordField(String name, String text, BaseFont font, float fontSize, float llx, float lly, float urx, float ury)
/*     */   {
/* 318 */     PdfFormField field = PdfFormField.createTextField(this.writer, false, true, 0);
/* 319 */     setTextFieldParams(field, text, name, llx, lly, urx, ury);
/* 320 */     drawSingleLineOfText(field, text, font, fontSize, llx, lly, urx, ury);
/* 321 */     addFormField(field);
/* 322 */     return field;
/*     */   }
/*     */ 
/*     */   public void setTextFieldParams(PdfFormField field, String text, String name, float llx, float lly, float urx, float ury)
/*     */   {
/* 335 */     field.setWidget(new Rectangle(llx, lly, urx, ury), PdfAnnotation.HIGHLIGHT_INVERT);
/* 336 */     field.setValueAsString(text);
/* 337 */     field.setDefaultValueAsString(text);
/* 338 */     field.setFieldName(name);
/* 339 */     field.setFlags(4);
/* 340 */     field.setPage();
/*     */   }
/*     */ 
/*     */   public void drawSingleLineOfText(PdfFormField field, String text, BaseFont font, float fontSize, float llx, float lly, float urx, float ury)
/*     */   {
/* 354 */     PdfAppearance tp = PdfAppearance.createAppearance(this.writer, urx - llx, ury - lly);
/* 355 */     PdfAppearance tp2 = (PdfAppearance)tp.getDuplicate();
/* 356 */     tp2.setFontAndSize(font, fontSize);
/* 357 */     tp2.resetRGBColorFill();
/* 358 */     field.setDefaultAppearanceString(tp2);
/* 359 */     tp.drawTextField(0.0F, 0.0F, urx - llx, ury - lly);
/* 360 */     tp.beginVariableText();
/* 361 */     tp.saveState();
/* 362 */     tp.rectangle(3.0F, 3.0F, urx - llx - 6.0F, ury - lly - 6.0F);
/* 363 */     tp.clip();
/* 364 */     tp.newPath();
/* 365 */     tp.beginText();
/* 366 */     tp.setFontAndSize(font, fontSize);
/* 367 */     tp.resetRGBColorFill();
/* 368 */     tp.setTextMatrix(4.0F, (ury - lly) / 2.0F - fontSize * 0.3F);
/* 369 */     tp.showText(text);
/* 370 */     tp.endText();
/* 371 */     tp.restoreState();
/* 372 */     tp.endVariableText();
/* 373 */     field.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, tp);
/*     */   }
/*     */ 
/*     */   public void drawMultiLineOfText(PdfFormField field, String text, BaseFont font, float fontSize, float llx, float lly, float urx, float ury)
/*     */   {
/* 387 */     PdfAppearance tp = PdfAppearance.createAppearance(this.writer, urx - llx, ury - lly);
/* 388 */     PdfAppearance tp2 = (PdfAppearance)tp.getDuplicate();
/* 389 */     tp2.setFontAndSize(font, fontSize);
/* 390 */     tp2.resetRGBColorFill();
/* 391 */     field.setDefaultAppearanceString(tp2);
/* 392 */     tp.drawTextField(0.0F, 0.0F, urx - llx, ury - lly);
/* 393 */     tp.beginVariableText();
/* 394 */     tp.saveState();
/* 395 */     tp.rectangle(3.0F, 3.0F, urx - llx - 6.0F, ury - lly - 6.0F);
/* 396 */     tp.clip();
/* 397 */     tp.newPath();
/* 398 */     tp.beginText();
/* 399 */     tp.setFontAndSize(font, fontSize);
/* 400 */     tp.resetRGBColorFill();
/* 401 */     tp.setTextMatrix(4.0F, 5.0F);
/* 402 */     StringTokenizer tokenizer = new StringTokenizer(text, "\n");
/* 403 */     float yPos = ury - lly;
/* 404 */     while (tokenizer.hasMoreTokens()) {
/* 405 */       yPos -= fontSize * 1.2F;
/* 406 */       tp.showTextAligned(0, tokenizer.nextToken(), 3.0F, yPos, 0.0F);
/*     */     }
/* 408 */     tp.endText();
/* 409 */     tp.restoreState();
/* 410 */     tp.endVariableText();
/* 411 */     field.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, tp);
/*     */   }
/*     */ 
/*     */   public PdfFormField addCheckBox(String name, String value, boolean status, float llx, float lly, float urx, float ury)
/*     */   {
/* 425 */     PdfFormField field = PdfFormField.createCheckBox(this.writer);
/* 426 */     setCheckBoxParams(field, name, value, status, llx, lly, urx, ury);
/* 427 */     drawCheckBoxAppearences(field, value, llx, lly, urx, ury);
/* 428 */     addFormField(field);
/* 429 */     return field;
/*     */   }
/*     */ 
/*     */   public void setCheckBoxParams(PdfFormField field, String name, String value, boolean status, float llx, float lly, float urx, float ury)
/*     */   {
/* 443 */     field.setWidget(new Rectangle(llx, lly, urx, ury), PdfAnnotation.HIGHLIGHT_TOGGLE);
/* 444 */     field.setFieldName(name);
/* 445 */     if (status) {
/* 446 */       field.setValueAsName(value);
/* 447 */       field.setAppearanceState(value);
/*     */     }
/*     */     else {
/* 450 */       field.setValueAsName("Off");
/* 451 */       field.setAppearanceState("Off");
/*     */     }
/* 453 */     field.setFlags(4);
/* 454 */     field.setPage();
/* 455 */     field.setBorderStyle(new PdfBorderDictionary(1.0F, 0));
/*     */   }
/*     */ 
/*     */   public void drawCheckBoxAppearences(PdfFormField field, String value, float llx, float lly, float urx, float ury)
/*     */   {
/* 467 */     BaseFont font = null;
/*     */     try {
/* 469 */       font = BaseFont.createFont("ZapfDingbats", "Cp1252", false);
/*     */     }
/*     */     catch (Exception e) {
/* 472 */       throw new ExceptionConverter(e);
/*     */     }
/* 474 */     float size = ury - lly;
/* 475 */     PdfAppearance tpOn = PdfAppearance.createAppearance(this.writer, urx - llx, ury - lly);
/* 476 */     PdfAppearance tp2 = (PdfAppearance)tpOn.getDuplicate();
/* 477 */     tp2.setFontAndSize(font, size);
/* 478 */     tp2.resetRGBColorFill();
/* 479 */     field.setDefaultAppearanceString(tp2);
/* 480 */     tpOn.drawTextField(0.0F, 0.0F, urx - llx, ury - lly);
/* 481 */     tpOn.saveState();
/* 482 */     tpOn.resetRGBColorFill();
/* 483 */     tpOn.beginText();
/* 484 */     tpOn.setFontAndSize(font, size);
/* 485 */     tpOn.showTextAligned(1, "4", (urx - llx) / 2.0F, (ury - lly) / 2.0F - size * 0.3F, 0.0F);
/* 486 */     tpOn.endText();
/* 487 */     tpOn.restoreState();
/* 488 */     field.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, value, tpOn);
/* 489 */     PdfAppearance tpOff = PdfAppearance.createAppearance(this.writer, urx - llx, ury - lly);
/* 490 */     tpOff.drawTextField(0.0F, 0.0F, urx - llx, ury - lly);
/* 491 */     field.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, "Off", tpOff);
/*     */   }
/*     */ 
/*     */   public PdfFormField getRadioGroup(String name, String defaultValue, boolean noToggleToOff)
/*     */   {
/* 501 */     PdfFormField radio = PdfFormField.createRadioButton(this.writer, noToggleToOff);
/* 502 */     radio.setFieldName(name);
/* 503 */     radio.setValueAsName(defaultValue);
/* 504 */     return radio;
/*     */   }
/*     */ 
/*     */   public void addRadioGroup(PdfFormField radiogroup)
/*     */   {
/* 511 */     addFormField(radiogroup);
/*     */   }
/*     */ 
/*     */   public PdfFormField addRadioButton(PdfFormField radiogroup, String value, float llx, float lly, float urx, float ury)
/*     */   {
/* 524 */     PdfFormField radio = PdfFormField.createEmpty(this.writer);
/* 525 */     radio.setWidget(new Rectangle(llx, lly, urx, ury), PdfAnnotation.HIGHLIGHT_TOGGLE);
/* 526 */     String name = ((PdfName)radiogroup.get(PdfName.V)).toString().substring(1);
/* 527 */     if (name.equals(value)) {
/* 528 */       radio.setAppearanceState(value);
/*     */     }
/*     */     else {
/* 531 */       radio.setAppearanceState("Off");
/*     */     }
/* 533 */     drawRadioAppearences(radio, value, llx, lly, urx, ury);
/* 534 */     radiogroup.addKid(radio);
/* 535 */     return radio;
/*     */   }
/*     */ 
/*     */   public void drawRadioAppearences(PdfFormField field, String value, float llx, float lly, float urx, float ury)
/*     */   {
/* 547 */     PdfAppearance tpOn = PdfAppearance.createAppearance(this.writer, urx - llx, ury - lly);
/* 548 */     tpOn.drawRadioField(0.0F, 0.0F, urx - llx, ury - lly, true);
/* 549 */     field.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, value, tpOn);
/* 550 */     PdfAppearance tpOff = PdfAppearance.createAppearance(this.writer, urx - llx, ury - lly);
/* 551 */     tpOff.drawRadioField(0.0F, 0.0F, urx - llx, ury - lly, false);
/* 552 */     field.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, "Off", tpOff);
/*     */   }
/*     */ 
/*     */   public PdfFormField addSelectList(String name, String[] options, String defaultValue, BaseFont font, float fontSize, float llx, float lly, float urx, float ury)
/*     */   {
/* 568 */     PdfFormField choice = PdfFormField.createList(this.writer, options, 0);
/* 569 */     setChoiceParams(choice, name, defaultValue, llx, lly, urx, ury);
/* 570 */     StringBuffer text = new StringBuffer();
/* 571 */     for (String option : options) {
/* 572 */       text.append(option).append('\n');
/*     */     }
/* 574 */     drawMultiLineOfText(choice, text.toString(), font, fontSize, llx, lly, urx, ury);
/* 575 */     addFormField(choice);
/* 576 */     return choice;
/*     */   }
/*     */ 
/*     */   public PdfFormField addSelectList(String name, String[][] options, String defaultValue, BaseFont font, float fontSize, float llx, float lly, float urx, float ury)
/*     */   {
/* 592 */     PdfFormField choice = PdfFormField.createList(this.writer, options, 0);
/* 593 */     setChoiceParams(choice, name, defaultValue, llx, lly, urx, ury);
/* 594 */     StringBuffer text = new StringBuffer();
/* 595 */     for (String[] option : options) {
/* 596 */       text.append(option[1]).append('\n');
/*     */     }
/* 598 */     drawMultiLineOfText(choice, text.toString(), font, fontSize, llx, lly, urx, ury);
/* 599 */     addFormField(choice);
/* 600 */     return choice;
/*     */   }
/*     */ 
/*     */   public PdfFormField addComboBox(String name, String[] options, String defaultValue, boolean editable, BaseFont font, float fontSize, float llx, float lly, float urx, float ury)
/*     */   {
/* 617 */     PdfFormField choice = PdfFormField.createCombo(this.writer, editable, options, 0);
/* 618 */     setChoiceParams(choice, name, defaultValue, llx, lly, urx, ury);
/* 619 */     if (defaultValue == null) {
/* 620 */       defaultValue = options[0];
/*     */     }
/* 622 */     drawSingleLineOfText(choice, defaultValue, font, fontSize, llx, lly, urx, ury);
/* 623 */     addFormField(choice);
/* 624 */     return choice;
/*     */   }
/*     */ 
/*     */   public PdfFormField addComboBox(String name, String[][] options, String defaultValue, boolean editable, BaseFont font, float fontSize, float llx, float lly, float urx, float ury)
/*     */   {
/* 641 */     PdfFormField choice = PdfFormField.createCombo(this.writer, editable, options, 0);
/* 642 */     setChoiceParams(choice, name, defaultValue, llx, lly, urx, ury);
/* 643 */     String value = null;
/* 644 */     for (String[] option : options) {
/* 645 */       if (option[0].equals(defaultValue)) {
/* 646 */         value = option[1];
/* 647 */         break;
/*     */       }
/*     */     }
/* 650 */     if (value == null) {
/* 651 */       value = options[0][1];
/*     */     }
/* 653 */     drawSingleLineOfText(choice, value, font, fontSize, llx, lly, urx, ury);
/* 654 */     addFormField(choice);
/* 655 */     return choice;
/*     */   }
/*     */ 
/*     */   public void setChoiceParams(PdfFormField field, String name, String defaultValue, float llx, float lly, float urx, float ury)
/*     */   {
/* 668 */     field.setWidget(new Rectangle(llx, lly, urx, ury), PdfAnnotation.HIGHLIGHT_INVERT);
/* 669 */     if (defaultValue != null) {
/* 670 */       field.setValueAsString(defaultValue);
/* 671 */       field.setDefaultValueAsString(defaultValue);
/*     */     }
/* 673 */     field.setFieldName(name);
/* 674 */     field.setFlags(4);
/* 675 */     field.setPage();
/* 676 */     field.setBorderStyle(new PdfBorderDictionary(2.0F, 0));
/*     */   }
/*     */ 
/*     */   public PdfFormField addSignature(String name, float llx, float lly, float urx, float ury)
/*     */   {
/* 689 */     PdfFormField signature = PdfFormField.createSignature(this.writer);
/* 690 */     setSignatureParams(signature, name, llx, lly, urx, ury);
/* 691 */     drawSignatureAppearences(signature, llx, lly, urx, ury);
/* 692 */     addFormField(signature);
/* 693 */     return signature;
/*     */   }
/*     */ 
/*     */   public void setSignatureParams(PdfFormField field, String name, float llx, float lly, float urx, float ury)
/*     */   {
/* 706 */     field.setWidget(new Rectangle(llx, lly, urx, ury), PdfAnnotation.HIGHLIGHT_INVERT);
/* 707 */     field.setFieldName(name);
/* 708 */     field.setFlags(4);
/* 709 */     field.setPage();
/* 710 */     field.setMKBorderColor(BaseColor.BLACK);
/* 711 */     field.setMKBackgroundColor(BaseColor.WHITE);
/*     */   }
/*     */ 
/*     */   public void drawSignatureAppearences(PdfFormField field, float llx, float lly, float urx, float ury)
/*     */   {
/* 723 */     PdfAppearance tp = PdfAppearance.createAppearance(this.writer, urx - llx, ury - lly);
/* 724 */     tp.setGrayFill(1.0F);
/* 725 */     tp.rectangle(0.0F, 0.0F, urx - llx, ury - lly);
/* 726 */     tp.fill();
/* 727 */     tp.setGrayStroke(0.0F);
/* 728 */     tp.setLineWidth(1.0F);
/* 729 */     tp.rectangle(0.5F, 0.5F, urx - llx - 0.5F, ury - lly - 0.5F);
/* 730 */     tp.closePathStroke();
/* 731 */     tp.saveState();
/* 732 */     tp.rectangle(1.0F, 1.0F, urx - llx - 2.0F, ury - lly - 2.0F);
/* 733 */     tp.clip();
/* 734 */     tp.newPath();
/* 735 */     tp.restoreState();
/* 736 */     field.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, tp);
/*     */   }
/*     */ 
/*     */   public void toPdf(PdfWriter writer, OutputStream os) throws IOException
/*     */   {
/* 741 */     PdfWriter.checkPdfIsoConformance(writer, 15, this);
/* 742 */     super.toPdf(writer, os);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfAcroForm
 * JD-Core Version:    0.6.2
 */