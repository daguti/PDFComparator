/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import com.itextpdf.text.Rectangle;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import java.io.IOException;
/*     */ 
/*     */ public class RadioCheckField extends BaseField
/*     */ {
/*     */   public static final int TYPE_CHECK = 1;
/*     */   public static final int TYPE_CIRCLE = 2;
/*     */   public static final int TYPE_CROSS = 3;
/*     */   public static final int TYPE_DIAMOND = 4;
/*     */   public static final int TYPE_SQUARE = 5;
/*     */   public static final int TYPE_STAR = 6;
/* 112 */   protected static String[] typeChars = { "4", "l", "8", "u", "n", "H" };
/*     */   protected int checkType;
/*     */   private String onValue;
/*     */   private boolean checked;
/*     */ 
/*     */   public RadioCheckField(PdfWriter writer, Rectangle box, String fieldName, String onValue)
/*     */   {
/* 137 */     super(writer, box, fieldName);
/* 138 */     setOnValue(onValue);
/* 139 */     setCheckType(2);
/*     */   }
/*     */ 
/*     */   public int getCheckType()
/*     */   {
/* 147 */     return this.checkType;
/*     */   }
/*     */ 
/*     */   public void setCheckType(int checkType)
/*     */   {
/* 161 */     if ((checkType < 1) || (checkType > 6))
/* 162 */       checkType = 2;
/* 163 */     this.checkType = checkType;
/* 164 */     setText(typeChars[(checkType - 1)]);
/*     */     try {
/* 166 */       setFont(BaseFont.createFont("ZapfDingbats", "Cp1252", false));
/*     */     }
/*     */     catch (Exception e) {
/* 169 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getOnValue()
/*     */   {
/* 178 */     return this.onValue;
/*     */   }
/*     */ 
/*     */   public void setOnValue(String onValue)
/*     */   {
/* 186 */     this.onValue = onValue;
/*     */   }
/*     */ 
/*     */   public boolean isChecked()
/*     */   {
/* 194 */     return this.checked;
/*     */   }
/*     */ 
/*     */   public void setChecked(boolean checked)
/*     */   {
/* 203 */     this.checked = checked;
/*     */   }
/*     */ 
/*     */   public PdfAppearance getAppearance(boolean isRadio, boolean on)
/*     */     throws IOException, DocumentException
/*     */   {
/* 217 */     if ((isRadio) && (this.checkType == 2))
/* 218 */       return getAppearanceRadioCircle(on);
/* 219 */     PdfAppearance app = getBorderAppearance();
/* 220 */     if (!on)
/* 221 */       return app;
/* 222 */     BaseFont ufont = getRealFont();
/* 223 */     boolean borderExtra = (this.borderStyle == 2) || (this.borderStyle == 3);
/* 224 */     float h = this.box.getHeight() - this.borderWidth * 2.0F;
/* 225 */     float bw2 = this.borderWidth;
/* 226 */     if (borderExtra) {
/* 227 */       h -= this.borderWidth * 2.0F;
/* 228 */       bw2 *= 2.0F;
/*     */     }
/* 230 */     float offsetX = borderExtra ? 2.0F * this.borderWidth : this.borderWidth;
/* 231 */     offsetX = Math.max(offsetX, 1.0F);
/* 232 */     float offX = Math.min(bw2, offsetX);
/* 233 */     float wt = this.box.getWidth() - 2.0F * offX;
/* 234 */     float ht = this.box.getHeight() - 2.0F * offX;
/* 235 */     float fsize = this.fontSize;
/* 236 */     if (fsize == 0.0F) {
/* 237 */       float bw = ufont.getWidthPoint(this.text, 1.0F);
/* 238 */       if (bw == 0.0F)
/* 239 */         fsize = 12.0F;
/*     */       else
/* 241 */         fsize = wt / bw;
/* 242 */       float nfsize = h / ufont.getFontDescriptor(1, 1.0F);
/* 243 */       fsize = Math.min(fsize, nfsize);
/*     */     }
/* 245 */     app.saveState();
/* 246 */     app.rectangle(offX, offX, wt, ht);
/* 247 */     app.clip();
/* 248 */     app.newPath();
/* 249 */     if (this.textColor == null)
/* 250 */       app.resetGrayFill();
/*     */     else
/* 252 */       app.setColorFill(this.textColor);
/* 253 */     app.beginText();
/* 254 */     app.setFontAndSize(ufont, fsize);
/* 255 */     app.setTextMatrix((this.box.getWidth() - ufont.getWidthPoint(this.text, fsize)) / 2.0F, (this.box.getHeight() - ufont.getAscentPoint(this.text, fsize)) / 2.0F);
/*     */ 
/* 257 */     app.showText(this.text);
/* 258 */     app.endText();
/* 259 */     app.restoreState();
/* 260 */     return app;
/*     */   }
/*     */ 
/*     */   public PdfAppearance getAppearanceRadioCircle(boolean on)
/*     */   {
/* 270 */     PdfAppearance app = PdfAppearance.createAppearance(this.writer, this.box.getWidth(), this.box.getHeight());
/* 271 */     switch (this.rotation) {
/*     */     case 90:
/* 273 */       app.setMatrix(0.0F, 1.0F, -1.0F, 0.0F, this.box.getHeight(), 0.0F);
/* 274 */       break;
/*     */     case 180:
/* 276 */       app.setMatrix(-1.0F, 0.0F, 0.0F, -1.0F, this.box.getWidth(), this.box.getHeight());
/* 277 */       break;
/*     */     case 270:
/* 279 */       app.setMatrix(0.0F, -1.0F, 1.0F, 0.0F, 0.0F, this.box.getWidth());
/*     */     }
/*     */ 
/* 282 */     Rectangle box = new Rectangle(app.getBoundingBox());
/* 283 */     float cx = box.getWidth() / 2.0F;
/* 284 */     float cy = box.getHeight() / 2.0F;
/* 285 */     float r = (Math.min(box.getWidth(), box.getHeight()) - this.borderWidth) / 2.0F;
/* 286 */     if (r <= 0.0F)
/* 287 */       return app;
/* 288 */     if (this.backgroundColor != null) {
/* 289 */       app.setColorFill(this.backgroundColor);
/* 290 */       app.circle(cx, cy, r + this.borderWidth / 2.0F);
/* 291 */       app.fill();
/*     */     }
/* 293 */     if ((this.borderWidth > 0.0F) && (this.borderColor != null)) {
/* 294 */       app.setLineWidth(this.borderWidth);
/* 295 */       app.setColorStroke(this.borderColor);
/* 296 */       app.circle(cx, cy, r);
/* 297 */       app.stroke();
/*     */     }
/* 299 */     if (on) {
/* 300 */       if (this.textColor == null)
/* 301 */         app.resetGrayFill();
/*     */       else
/* 303 */         app.setColorFill(this.textColor);
/* 304 */       app.circle(cx, cy, r / 2.0F);
/* 305 */       app.fill();
/*     */     }
/* 307 */     return app;
/*     */   }
/*     */ 
/*     */   public PdfFormField getRadioGroup(boolean noToggleToOff, boolean radiosInUnison)
/*     */   {
/* 324 */     PdfFormField field = PdfFormField.createRadioButton(this.writer, noToggleToOff);
/* 325 */     if (radiosInUnison)
/* 326 */       field.setFieldFlags(33554432);
/* 327 */     field.setFieldName(this.fieldName);
/* 328 */     if ((this.options & 0x1) != 0)
/* 329 */       field.setFieldFlags(1);
/* 330 */     if ((this.options & 0x2) != 0)
/* 331 */       field.setFieldFlags(2);
/* 332 */     field.setValueAsName(this.checked ? this.onValue : "Off");
/* 333 */     return field;
/*     */   }
/*     */ 
/*     */   public PdfFormField getRadioField()
/*     */     throws IOException, DocumentException
/*     */   {
/* 344 */     return getField(true);
/*     */   }
/*     */ 
/*     */   public PdfFormField getCheckField()
/*     */     throws IOException, DocumentException
/*     */   {
/* 354 */     return getField(false);
/*     */   }
/*     */ 
/*     */   protected PdfFormField getField(boolean isRadio)
/*     */     throws IOException, DocumentException
/*     */   {
/* 366 */     PdfFormField field = null;
/* 367 */     if (isRadio)
/* 368 */       field = PdfFormField.createEmpty(this.writer);
/*     */     else
/* 370 */       field = PdfFormField.createCheckBox(this.writer);
/* 371 */     field.setWidget(this.box, PdfAnnotation.HIGHLIGHT_INVERT);
/* 372 */     if (!isRadio) {
/* 373 */       if (!"Yes".equals(this.onValue)) {
/* 374 */         throw new DocumentException(MessageLocalization.getComposedMessage("1.is.not.a.valid.name.for.checkbox.appearance", new Object[] { this.onValue }));
/*     */       }
/* 376 */       field.setFieldName(this.fieldName);
/* 377 */       if ((this.options & 0x1) != 0)
/* 378 */         field.setFieldFlags(1);
/* 379 */       if ((this.options & 0x2) != 0)
/* 380 */         field.setFieldFlags(2);
/* 381 */       field.setValueAsName(this.checked ? this.onValue : "Off");
/* 382 */       setCheckType(this.checkType);
/*     */     }
/* 384 */     if (this.text != null)
/* 385 */       field.setMKNormalCaption(this.text);
/* 386 */     if (this.rotation != 0)
/* 387 */       field.setMKRotation(this.rotation);
/* 388 */     field.setBorderStyle(new PdfBorderDictionary(this.borderWidth, this.borderStyle, new PdfDashPattern(3.0F)));
/* 389 */     PdfAppearance tpon = getAppearance(isRadio, true);
/* 390 */     PdfAppearance tpoff = getAppearance(isRadio, false);
/* 391 */     field.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, this.onValue, tpon);
/* 392 */     field.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, "Off", tpoff);
/* 393 */     field.setAppearanceState(this.checked ? this.onValue : "Off");
/* 394 */     PdfAppearance da = (PdfAppearance)tpon.getDuplicate();
/* 395 */     BaseFont realFont = getRealFont();
/* 396 */     if (realFont != null)
/* 397 */       da.setFontAndSize(getRealFont(), this.fontSize);
/* 398 */     if (this.textColor == null)
/* 399 */       da.setGrayFill(0.0F);
/*     */     else
/* 401 */       da.setColorFill(this.textColor);
/* 402 */     field.setDefaultAppearanceString(da);
/* 403 */     if (this.borderColor != null)
/* 404 */       field.setMKBorderColor(this.borderColor);
/* 405 */     if (this.backgroundColor != null)
/* 406 */       field.setMKBackgroundColor(this.backgroundColor);
/* 407 */     switch (this.visibility) {
/*     */     case 1:
/* 409 */       field.setFlags(6);
/* 410 */       break;
/*     */     case 2:
/* 412 */       break;
/*     */     case 3:
/* 414 */       field.setFlags(36);
/* 415 */       break;
/*     */     default:
/* 417 */       field.setFlags(4);
/*     */     }
/*     */ 
/* 420 */     return field;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.RadioCheckField
 * JD-Core Version:    0.6.2
 */