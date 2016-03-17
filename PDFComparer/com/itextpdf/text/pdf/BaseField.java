/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.BaseColor;
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import com.itextpdf.text.Rectangle;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ 
/*     */ public abstract class BaseField
/*     */ {
/*     */   public static final float BORDER_WIDTH_THIN = 1.0F;
/*     */   public static final float BORDER_WIDTH_MEDIUM = 2.0F;
/*     */   public static final float BORDER_WIDTH_THICK = 3.0F;
/*     */   public static final int VISIBLE = 0;
/*     */   public static final int HIDDEN = 1;
/*     */   public static final int VISIBLE_BUT_DOES_NOT_PRINT = 2;
/*     */   public static final int HIDDEN_BUT_PRINTABLE = 3;
/*     */   public static final int READ_ONLY = 1;
/*     */   public static final int REQUIRED = 2;
/*     */   public static final int MULTILINE = 4096;
/*     */   public static final int DO_NOT_SCROLL = 8388608;
/*     */   public static final int PASSWORD = 8192;
/*     */   public static final int FILE_SELECTION = 1048576;
/*     */   public static final int DO_NOT_SPELL_CHECK = 4194304;
/*     */   public static final int EDIT = 262144;
/*     */   public static final int MULTISELECT = 2097152;
/*     */   public static final int COMB = 16777216;
/* 122 */   protected float borderWidth = 1.0F;
/* 123 */   protected int borderStyle = 0;
/*     */   protected BaseColor borderColor;
/*     */   protected BaseColor backgroundColor;
/*     */   protected BaseColor textColor;
/*     */   protected BaseFont font;
/* 128 */   protected float fontSize = 0.0F;
/* 129 */   protected int alignment = 0;
/*     */   protected PdfWriter writer;
/*     */   protected String text;
/*     */   protected Rectangle box;
/* 135 */   protected int rotation = 0;
/*     */   protected int visibility;
/*     */   protected String fieldName;
/*     */   protected int options;
/*     */   protected int maxCharacterLength;
/* 149 */   private static final HashMap<PdfName, Integer> fieldKeys = new HashMap();
/*     */ 
/*     */   public BaseField(PdfWriter writer, Rectangle box, String fieldName)
/*     */   {
/* 162 */     this.writer = writer;
/* 163 */     setBox(box);
/* 164 */     this.fieldName = fieldName;
/*     */   }
/*     */ 
/*     */   protected BaseFont getRealFont() throws IOException, DocumentException {
/* 168 */     if (this.font == null) {
/* 169 */       return BaseFont.createFont("Helvetica", "Cp1252", false);
/*     */     }
/* 171 */     return this.font;
/*     */   }
/*     */ 
/*     */   protected PdfAppearance getBorderAppearance() {
/* 175 */     PdfAppearance app = PdfAppearance.createAppearance(this.writer, this.box.getWidth(), this.box.getHeight());
/* 176 */     switch (this.rotation) {
/*     */     case 90:
/* 178 */       app.setMatrix(0.0F, 1.0F, -1.0F, 0.0F, this.box.getHeight(), 0.0F);
/* 179 */       break;
/*     */     case 180:
/* 181 */       app.setMatrix(-1.0F, 0.0F, 0.0F, -1.0F, this.box.getWidth(), this.box.getHeight());
/* 182 */       break;
/*     */     case 270:
/* 184 */       app.setMatrix(0.0F, -1.0F, 1.0F, 0.0F, 0.0F, this.box.getWidth());
/*     */     }
/*     */ 
/* 187 */     app.saveState();
/*     */ 
/* 189 */     if (this.backgroundColor != null) {
/* 190 */       app.setColorFill(this.backgroundColor);
/* 191 */       app.rectangle(0.0F, 0.0F, this.box.getWidth(), this.box.getHeight());
/* 192 */       app.fill();
/*     */     }
/*     */ 
/* 195 */     if (this.borderStyle == 4) {
/* 196 */       if ((this.borderWidth != 0.0F) && (this.borderColor != null)) {
/* 197 */         app.setColorStroke(this.borderColor);
/* 198 */         app.setLineWidth(this.borderWidth);
/* 199 */         app.moveTo(0.0F, this.borderWidth / 2.0F);
/* 200 */         app.lineTo(this.box.getWidth(), this.borderWidth / 2.0F);
/* 201 */         app.stroke();
/*     */       }
/*     */     }
/* 204 */     else if (this.borderStyle == 2) {
/* 205 */       if ((this.borderWidth != 0.0F) && (this.borderColor != null)) {
/* 206 */         app.setColorStroke(this.borderColor);
/* 207 */         app.setLineWidth(this.borderWidth);
/* 208 */         app.rectangle(this.borderWidth / 2.0F, this.borderWidth / 2.0F, this.box.getWidth() - this.borderWidth, this.box.getHeight() - this.borderWidth);
/* 209 */         app.stroke();
/*     */       }
/*     */ 
/* 212 */       BaseColor actual = this.backgroundColor;
/* 213 */       if (actual == null)
/* 214 */         actual = BaseColor.WHITE;
/* 215 */       app.setGrayFill(1.0F);
/* 216 */       drawTopFrame(app);
/* 217 */       app.setColorFill(actual.darker());
/* 218 */       drawBottomFrame(app);
/*     */     }
/* 220 */     else if (this.borderStyle == 3) {
/* 221 */       if ((this.borderWidth != 0.0F) && (this.borderColor != null)) {
/* 222 */         app.setColorStroke(this.borderColor);
/* 223 */         app.setLineWidth(this.borderWidth);
/* 224 */         app.rectangle(this.borderWidth / 2.0F, this.borderWidth / 2.0F, this.box.getWidth() - this.borderWidth, this.box.getHeight() - this.borderWidth);
/* 225 */         app.stroke();
/*     */       }
/*     */ 
/* 228 */       app.setGrayFill(0.5F);
/* 229 */       drawTopFrame(app);
/* 230 */       app.setGrayFill(0.75F);
/* 231 */       drawBottomFrame(app);
/*     */     }
/* 234 */     else if ((this.borderWidth != 0.0F) && (this.borderColor != null)) {
/* 235 */       if (this.borderStyle == 1)
/* 236 */         app.setLineDash(3.0F, 0.0F);
/* 237 */       app.setColorStroke(this.borderColor);
/* 238 */       app.setLineWidth(this.borderWidth);
/* 239 */       app.rectangle(this.borderWidth / 2.0F, this.borderWidth / 2.0F, this.box.getWidth() - this.borderWidth, this.box.getHeight() - this.borderWidth);
/* 240 */       app.stroke();
/* 241 */       if (((this.options & 0x1000000) != 0) && (this.maxCharacterLength > 1)) {
/* 242 */         float step = this.box.getWidth() / this.maxCharacterLength;
/* 243 */         float yb = this.borderWidth / 2.0F;
/* 244 */         float yt = this.box.getHeight() - this.borderWidth / 2.0F;
/* 245 */         for (int k = 1; k < this.maxCharacterLength; k++) {
/* 246 */           float x = step * k;
/* 247 */           app.moveTo(x, yb);
/* 248 */           app.lineTo(x, yt);
/*     */         }
/* 250 */         app.stroke();
/*     */       }
/*     */     }
/*     */ 
/* 254 */     app.restoreState();
/* 255 */     return app;
/*     */   }
/*     */ 
/*     */   protected static ArrayList<String> getHardBreaks(String text) {
/* 259 */     ArrayList arr = new ArrayList();
/* 260 */     char[] cs = text.toCharArray();
/* 261 */     int len = cs.length;
/* 262 */     StringBuffer buf = new StringBuffer();
/* 263 */     for (int k = 0; k < len; k++) {
/* 264 */       char c = cs[k];
/* 265 */       if (c == '\r') {
/* 266 */         if ((k + 1 < len) && (cs[(k + 1)] == '\n'))
/* 267 */           k++;
/* 268 */         arr.add(buf.toString());
/* 269 */         buf = new StringBuffer();
/*     */       }
/* 271 */       else if (c == '\n') {
/* 272 */         arr.add(buf.toString());
/* 273 */         buf = new StringBuffer();
/*     */       }
/*     */       else {
/* 276 */         buf.append(c);
/*     */       }
/*     */     }
/* 278 */     arr.add(buf.toString());
/* 279 */     return arr;
/*     */   }
/*     */ 
/*     */   protected static void trimRight(StringBuffer buf) {
/* 283 */     int len = buf.length();
/*     */     while (true) {
/* 285 */       if (len == 0)
/* 286 */         return;
/* 287 */       if (buf.charAt(--len) != ' ')
/* 288 */         return;
/* 289 */       buf.setLength(len);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected static ArrayList<String> breakLines(ArrayList<String> breaks, BaseFont font, float fontSize, float width) {
/* 294 */     ArrayList lines = new ArrayList();
/* 295 */     StringBuffer buf = new StringBuffer();
/* 296 */     for (int ck = 0; ck < breaks.size(); ck++) {
/* 297 */       buf.setLength(0);
/* 298 */       float w = 0.0F;
/* 299 */       char[] cs = ((String)breaks.get(ck)).toCharArray();
/* 300 */       int len = cs.length;
/*     */ 
/* 302 */       int state = 0;
/* 303 */       int lastspace = -1;
/* 304 */       char c = '\000';
/* 305 */       int refk = 0;
/* 306 */       for (int k = 0; k < len; k++) {
/* 307 */         c = cs[k];
/* 308 */         switch (state) {
/*     */         case 0:
/* 310 */           w += font.getWidthPoint(c, fontSize);
/* 311 */           buf.append(c);
/* 312 */           if (w > width) {
/* 313 */             w = 0.0F;
/* 314 */             if (buf.length() > 1) {
/* 315 */               k--;
/* 316 */               buf.setLength(buf.length() - 1);
/*     */             }
/* 318 */             lines.add(buf.toString());
/* 319 */             buf.setLength(0);
/* 320 */             refk = k;
/* 321 */             if (c == ' ')
/* 322 */               state = 2;
/*     */             else {
/* 324 */               state = 1;
/*     */             }
/*     */           }
/* 327 */           else if (c != ' ') {
/* 328 */             state = 1; } break;
/*     */         case 1:
/* 332 */           w += font.getWidthPoint(c, fontSize);
/* 333 */           buf.append(c);
/* 334 */           if (c == ' ')
/* 335 */             lastspace = k;
/* 336 */           if (w > width) {
/* 337 */             w = 0.0F;
/* 338 */             if (lastspace >= 0) {
/* 339 */               k = lastspace;
/* 340 */               buf.setLength(lastspace - refk);
/* 341 */               trimRight(buf);
/* 342 */               lines.add(buf.toString());
/* 343 */               buf.setLength(0);
/* 344 */               refk = k;
/* 345 */               lastspace = -1;
/* 346 */               state = 2;
/*     */             }
/*     */             else {
/* 349 */               if (buf.length() > 1) {
/* 350 */                 k--;
/* 351 */                 buf.setLength(buf.length() - 1);
/*     */               }
/* 353 */               lines.add(buf.toString());
/* 354 */               buf.setLength(0);
/* 355 */               refk = k;
/* 356 */               if (c == ' ')
/* 357 */                 state = 2;  }  } break;
/*     */         case 2:
/* 362 */           if (c != ' ') {
/* 363 */             w = 0.0F;
/* 364 */             k--;
/* 365 */             state = 1;
/*     */           }
/*     */           break;
/*     */         }
/*     */       }
/* 370 */       trimRight(buf);
/* 371 */       lines.add(buf.toString());
/*     */     }
/* 373 */     return lines;
/*     */   }
/*     */ 
/*     */   private void drawTopFrame(PdfAppearance app) {
/* 377 */     app.moveTo(this.borderWidth, this.borderWidth);
/* 378 */     app.lineTo(this.borderWidth, this.box.getHeight() - this.borderWidth);
/* 379 */     app.lineTo(this.box.getWidth() - this.borderWidth, this.box.getHeight() - this.borderWidth);
/* 380 */     app.lineTo(this.box.getWidth() - 2.0F * this.borderWidth, this.box.getHeight() - 2.0F * this.borderWidth);
/* 381 */     app.lineTo(2.0F * this.borderWidth, this.box.getHeight() - 2.0F * this.borderWidth);
/* 382 */     app.lineTo(2.0F * this.borderWidth, 2.0F * this.borderWidth);
/* 383 */     app.lineTo(this.borderWidth, this.borderWidth);
/* 384 */     app.fill();
/*     */   }
/*     */ 
/*     */   private void drawBottomFrame(PdfAppearance app) {
/* 388 */     app.moveTo(this.borderWidth, this.borderWidth);
/* 389 */     app.lineTo(this.box.getWidth() - this.borderWidth, this.borderWidth);
/* 390 */     app.lineTo(this.box.getWidth() - this.borderWidth, this.box.getHeight() - this.borderWidth);
/* 391 */     app.lineTo(this.box.getWidth() - 2.0F * this.borderWidth, this.box.getHeight() - 2.0F * this.borderWidth);
/* 392 */     app.lineTo(this.box.getWidth() - 2.0F * this.borderWidth, 2.0F * this.borderWidth);
/* 393 */     app.lineTo(2.0F * this.borderWidth, 2.0F * this.borderWidth);
/* 394 */     app.lineTo(this.borderWidth, this.borderWidth);
/* 395 */     app.fill();
/*     */   }
/*     */ 
/*     */   public float getBorderWidth()
/*     */   {
/* 401 */     return this.borderWidth;
/*     */   }
/*     */ 
/*     */   public void setBorderWidth(float borderWidth)
/*     */   {
/* 409 */     this.borderWidth = borderWidth;
/*     */   }
/*     */ 
/*     */   public int getBorderStyle()
/*     */   {
/* 416 */     return this.borderStyle;
/*     */   }
/*     */ 
/*     */   public void setBorderStyle(int borderStyle)
/*     */   {
/* 426 */     this.borderStyle = borderStyle;
/*     */   }
/*     */ 
/*     */   public BaseColor getBorderColor()
/*     */   {
/* 433 */     return this.borderColor;
/*     */   }
/*     */ 
/*     */   public void setBorderColor(BaseColor borderColor)
/*     */   {
/* 441 */     this.borderColor = borderColor;
/*     */   }
/*     */ 
/*     */   public BaseColor getBackgroundColor()
/*     */   {
/* 448 */     return this.backgroundColor;
/*     */   }
/*     */ 
/*     */   public void setBackgroundColor(BaseColor backgroundColor)
/*     */   {
/* 456 */     this.backgroundColor = backgroundColor;
/*     */   }
/*     */ 
/*     */   public BaseColor getTextColor()
/*     */   {
/* 463 */     return this.textColor;
/*     */   }
/*     */ 
/*     */   public void setTextColor(BaseColor textColor)
/*     */   {
/* 471 */     this.textColor = textColor;
/*     */   }
/*     */ 
/*     */   public BaseFont getFont()
/*     */   {
/* 478 */     return this.font;
/*     */   }
/*     */ 
/*     */   public void setFont(BaseFont font)
/*     */   {
/* 486 */     this.font = font;
/*     */   }
/*     */ 
/*     */   public float getFontSize()
/*     */   {
/* 493 */     return this.fontSize;
/*     */   }
/*     */ 
/*     */   public void setFontSize(float fontSize)
/*     */   {
/* 501 */     this.fontSize = fontSize;
/*     */   }
/*     */ 
/*     */   public int getAlignment()
/*     */   {
/* 508 */     return this.alignment;
/*     */   }
/*     */ 
/*     */   public void setAlignment(int alignment)
/*     */   {
/* 516 */     this.alignment = alignment;
/*     */   }
/*     */ 
/*     */   public String getText()
/*     */   {
/* 523 */     return this.text;
/*     */   }
/*     */ 
/*     */   public void setText(String text)
/*     */   {
/* 530 */     this.text = text;
/*     */   }
/*     */ 
/*     */   public Rectangle getBox()
/*     */   {
/* 537 */     return this.box;
/*     */   }
/*     */ 
/*     */   public void setBox(Rectangle box)
/*     */   {
/* 544 */     if (box == null) {
/* 545 */       this.box = null;
/*     */     }
/*     */     else {
/* 548 */       this.box = new Rectangle(box);
/* 549 */       this.box.normalize();
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getRotation()
/*     */   {
/* 557 */     return this.rotation;
/*     */   }
/*     */ 
/*     */   public void setRotation(int rotation)
/*     */   {
/* 565 */     if (rotation % 90 != 0)
/* 566 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("rotation.must.be.a.multiple.of.90", new Object[0]));
/* 567 */     rotation %= 360;
/* 568 */     if (rotation < 0)
/* 569 */       rotation += 360;
/* 570 */     this.rotation = rotation;
/*     */   }
/*     */ 
/*     */   public void setRotationFromPage(Rectangle page)
/*     */   {
/* 578 */     setRotation(page.getRotation());
/*     */   }
/*     */ 
/*     */   public int getVisibility()
/*     */   {
/* 585 */     return this.visibility;
/*     */   }
/*     */ 
/*     */   public void setVisibility(int visibility)
/*     */   {
/* 594 */     this.visibility = visibility;
/*     */   }
/*     */ 
/*     */   public String getFieldName()
/*     */   {
/* 601 */     return this.fieldName;
/*     */   }
/*     */ 
/*     */   public void setFieldName(String fieldName)
/*     */   {
/* 609 */     this.fieldName = fieldName;
/*     */   }
/*     */ 
/*     */   public int getOptions()
/*     */   {
/* 616 */     return this.options;
/*     */   }
/*     */ 
/*     */   public void setOptions(int options)
/*     */   {
/* 627 */     this.options = options;
/*     */   }
/*     */ 
/*     */   public int getMaxCharacterLength()
/*     */   {
/* 634 */     return this.maxCharacterLength;
/*     */   }
/*     */ 
/*     */   public void setMaxCharacterLength(int maxCharacterLength)
/*     */   {
/* 642 */     this.maxCharacterLength = maxCharacterLength;
/*     */   }
/*     */ 
/*     */   public PdfWriter getWriter()
/*     */   {
/* 650 */     return this.writer;
/*     */   }
/*     */ 
/*     */   public void setWriter(PdfWriter writer)
/*     */   {
/* 658 */     this.writer = writer;
/*     */   }
/*     */ 
/*     */   public static void moveFields(PdfDictionary from, PdfDictionary to)
/*     */   {
/* 668 */     for (Iterator i = from.getKeys().iterator(); i.hasNext(); ) {
/* 669 */       PdfName key = (PdfName)i.next();
/* 670 */       if (fieldKeys.containsKey(key)) {
/* 671 */         if (to != null)
/* 672 */           to.put(key, from.get(key));
/* 673 */         i.remove();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 152 */     fieldKeys.putAll(PdfCopyFieldsImp.fieldKeys);
/* 153 */     fieldKeys.put(PdfName.T, Integer.valueOf(1));
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.BaseField
 * JD-Core Version:    0.6.2
 */