/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.Rectangle;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class PdfFormField extends PdfAnnotation
/*     */ {
/*     */   public static final int FF_READ_ONLY = 1;
/*     */   public static final int FF_REQUIRED = 2;
/*     */   public static final int FF_NO_EXPORT = 4;
/*     */   public static final int FF_NO_TOGGLE_TO_OFF = 16384;
/*     */   public static final int FF_RADIO = 32768;
/*     */   public static final int FF_PUSHBUTTON = 65536;
/*     */   public static final int FF_MULTILINE = 4096;
/*     */   public static final int FF_PASSWORD = 8192;
/*     */   public static final int FF_COMBO = 131072;
/*     */   public static final int FF_EDIT = 262144;
/*     */   public static final int FF_FILESELECT = 1048576;
/*     */   public static final int FF_MULTISELECT = 2097152;
/*     */   public static final int FF_DONOTSPELLCHECK = 4194304;
/*     */   public static final int FF_DONOTSCROLL = 8388608;
/*     */   public static final int FF_COMB = 16777216;
/*     */   public static final int FF_RADIOSINUNISON = 33554432;
/*     */   public static final int FF_RICHTEXT = 33554432;
/*     */   public static final int Q_LEFT = 0;
/*     */   public static final int Q_CENTER = 1;
/*     */   public static final int Q_RIGHT = 2;
/*     */   public static final int MK_NO_ICON = 0;
/*     */   public static final int MK_NO_CAPTION = 1;
/*     */   public static final int MK_CAPTION_BELOW = 2;
/*     */   public static final int MK_CAPTION_ABOVE = 3;
/*     */   public static final int MK_CAPTION_RIGHT = 4;
/*     */   public static final int MK_CAPTION_LEFT = 5;
/*     */   public static final int MK_CAPTION_OVERLAID = 6;
/*  87 */   public static final PdfName IF_SCALE_ALWAYS = PdfName.A;
/*  88 */   public static final PdfName IF_SCALE_BIGGER = PdfName.B;
/*  89 */   public static final PdfName IF_SCALE_SMALLER = PdfName.S;
/*  90 */   public static final PdfName IF_SCALE_NEVER = PdfName.N;
/*  91 */   public static final PdfName IF_SCALE_ANAMORPHIC = PdfName.A;
/*  92 */   public static final PdfName IF_SCALE_PROPORTIONAL = PdfName.P;
/*     */   public static final boolean MULTILINE = true;
/*     */   public static final boolean SINGLELINE = false;
/*     */   public static final boolean PLAINTEXT = false;
/*     */   public static final boolean PASSWORD = true;
/*  97 */   static PdfName[] mergeTarget = { PdfName.FONT, PdfName.XOBJECT, PdfName.COLORSPACE, PdfName.PATTERN };
/*     */   protected PdfFormField parent;
/*     */   protected ArrayList<PdfFormField> kids;
/*     */ 
/*     */   public PdfFormField(PdfWriter writer, float llx, float lly, float urx, float ury, PdfAction action)
/*     */   {
/* 109 */     super(writer, llx, lly, urx, ury, action);
/* 110 */     put(PdfName.TYPE, PdfName.ANNOT);
/* 111 */     put(PdfName.SUBTYPE, PdfName.WIDGET);
/* 112 */     this.annotation = true;
/*     */   }
/*     */ 
/*     */   protected PdfFormField(PdfWriter writer)
/*     */   {
/* 117 */     super(writer, null);
/* 118 */     this.form = true;
/* 119 */     this.annotation = false;
/* 120 */     this.role = PdfName.FORM;
/*     */   }
/*     */ 
/*     */   public void setWidget(Rectangle rect, PdfName highlight) {
/* 124 */     put(PdfName.TYPE, PdfName.ANNOT);
/* 125 */     put(PdfName.SUBTYPE, PdfName.WIDGET);
/* 126 */     put(PdfName.RECT, new PdfRectangle(rect));
/* 127 */     this.annotation = true;
/* 128 */     if ((highlight != null) && (!highlight.equals(HIGHLIGHT_INVERT)))
/* 129 */       put(PdfName.H, highlight);
/*     */   }
/*     */ 
/*     */   public static PdfFormField createEmpty(PdfWriter writer) {
/* 133 */     PdfFormField field = new PdfFormField(writer);
/* 134 */     return field;
/*     */   }
/*     */ 
/*     */   public void setButton(int flags) {
/* 138 */     put(PdfName.FT, PdfName.BTN);
/* 139 */     if (flags != 0)
/* 140 */       put(PdfName.FF, new PdfNumber(flags));
/*     */   }
/*     */ 
/*     */   protected static PdfFormField createButton(PdfWriter writer, int flags) {
/* 144 */     PdfFormField field = new PdfFormField(writer);
/* 145 */     field.setButton(flags);
/* 146 */     return field;
/*     */   }
/*     */ 
/*     */   public static PdfFormField createPushButton(PdfWriter writer) {
/* 150 */     return createButton(writer, 65536);
/*     */   }
/*     */ 
/*     */   public static PdfFormField createCheckBox(PdfWriter writer) {
/* 154 */     return createButton(writer, 0);
/*     */   }
/*     */ 
/*     */   public static PdfFormField createRadioButton(PdfWriter writer, boolean noToggleToOff) {
/* 158 */     return createButton(writer, 32768 + (noToggleToOff ? 16384 : 0));
/*     */   }
/*     */ 
/*     */   public static PdfFormField createTextField(PdfWriter writer, boolean multiline, boolean password, int maxLen) {
/* 162 */     PdfFormField field = new PdfFormField(writer);
/* 163 */     field.put(PdfName.FT, PdfName.TX);
/* 164 */     int flags = multiline ? 4096 : 0;
/* 165 */     flags += (password ? 8192 : 0);
/* 166 */     field.put(PdfName.FF, new PdfNumber(flags));
/* 167 */     if (maxLen > 0)
/* 168 */       field.put(PdfName.MAXLEN, new PdfNumber(maxLen));
/* 169 */     return field;
/*     */   }
/*     */ 
/*     */   protected static PdfFormField createChoice(PdfWriter writer, int flags, PdfArray options, int topIndex) {
/* 173 */     PdfFormField field = new PdfFormField(writer);
/* 174 */     field.put(PdfName.FT, PdfName.CH);
/* 175 */     field.put(PdfName.FF, new PdfNumber(flags));
/* 176 */     field.put(PdfName.OPT, options);
/* 177 */     if (topIndex > 0)
/* 178 */       field.put(PdfName.TI, new PdfNumber(topIndex));
/* 179 */     return field;
/*     */   }
/*     */ 
/*     */   public static PdfFormField createList(PdfWriter writer, String[] options, int topIndex) {
/* 183 */     return createChoice(writer, 0, processOptions(options), topIndex);
/*     */   }
/*     */ 
/*     */   public static PdfFormField createList(PdfWriter writer, String[][] options, int topIndex) {
/* 187 */     return createChoice(writer, 0, processOptions(options), topIndex);
/*     */   }
/*     */ 
/*     */   public static PdfFormField createCombo(PdfWriter writer, boolean edit, String[] options, int topIndex) {
/* 191 */     return createChoice(writer, 131072 + (edit ? 262144 : 0), processOptions(options), topIndex);
/*     */   }
/*     */ 
/*     */   public static PdfFormField createCombo(PdfWriter writer, boolean edit, String[][] options, int topIndex) {
/* 195 */     return createChoice(writer, 131072 + (edit ? 262144 : 0), processOptions(options), topIndex);
/*     */   }
/*     */ 
/*     */   protected static PdfArray processOptions(String[] options) {
/* 199 */     PdfArray array = new PdfArray();
/* 200 */     for (int k = 0; k < options.length; k++) {
/* 201 */       array.add(new PdfString(options[k], "UnicodeBig"));
/*     */     }
/* 203 */     return array;
/*     */   }
/*     */ 
/*     */   protected static PdfArray processOptions(String[][] options) {
/* 207 */     PdfArray array = new PdfArray();
/* 208 */     for (int k = 0; k < options.length; k++) {
/* 209 */       String[] subOption = options[k];
/* 210 */       PdfArray ar2 = new PdfArray(new PdfString(subOption[0], "UnicodeBig"));
/* 211 */       ar2.add(new PdfString(subOption[1], "UnicodeBig"));
/* 212 */       array.add(ar2);
/*     */     }
/* 214 */     return array;
/*     */   }
/*     */ 
/*     */   public static PdfFormField createSignature(PdfWriter writer) {
/* 218 */     PdfFormField field = new PdfFormField(writer);
/* 219 */     field.put(PdfName.FT, PdfName.SIG);
/* 220 */     return field;
/*     */   }
/*     */ 
/*     */   public PdfFormField getParent()
/*     */   {
/* 227 */     return this.parent;
/*     */   }
/*     */ 
/*     */   public void addKid(PdfFormField field) {
/* 231 */     field.parent = this;
/* 232 */     if (this.kids == null)
/* 233 */       this.kids = new ArrayList();
/* 234 */     this.kids.add(field);
/*     */   }
/*     */ 
/*     */   public ArrayList<PdfFormField> getKids() {
/* 238 */     return this.kids;
/*     */   }
/*     */ 
/*     */   public int setFieldFlags(int flags)
/*     */   {
/* 247 */     PdfNumber obj = (PdfNumber)get(PdfName.FF);
/*     */     int old;
/*     */     int old;
/* 249 */     if (obj == null)
/* 250 */       old = 0;
/*     */     else
/* 252 */       old = obj.intValue();
/* 253 */     int v = old | flags;
/* 254 */     put(PdfName.FF, new PdfNumber(v));
/* 255 */     return old;
/*     */   }
/*     */ 
/*     */   public void setValueAsString(String s) {
/* 259 */     put(PdfName.V, new PdfString(s, "UnicodeBig"));
/*     */   }
/*     */ 
/*     */   public void setValueAsName(String s) {
/* 263 */     put(PdfName.V, new PdfName(s));
/*     */   }
/*     */ 
/*     */   public void setValue(PdfSignature sig) {
/* 267 */     put(PdfName.V, sig);
/*     */   }
/*     */ 
/*     */   public void setRichValue(String rv)
/*     */   {
/* 280 */     put(PdfName.RV, new PdfString(rv));
/*     */   }
/*     */ 
/*     */   public void setDefaultValueAsString(String s) {
/* 284 */     put(PdfName.DV, new PdfString(s, "UnicodeBig"));
/*     */   }
/*     */ 
/*     */   public void setDefaultValueAsName(String s) {
/* 288 */     put(PdfName.DV, new PdfName(s));
/*     */   }
/*     */ 
/*     */   public void setFieldName(String s) {
/* 292 */     if (s != null)
/* 293 */       put(PdfName.T, new PdfString(s, "UnicodeBig"));
/*     */   }
/*     */ 
/*     */   public void setUserName(String s)
/*     */   {
/* 301 */     put(PdfName.TU, new PdfString(s, "UnicodeBig"));
/*     */   }
/*     */ 
/*     */   public void setMappingName(String s)
/*     */   {
/* 309 */     put(PdfName.TM, new PdfString(s, "UnicodeBig"));
/*     */   }
/*     */ 
/*     */   public void setQuadding(int v)
/*     */   {
/* 317 */     put(PdfName.Q, new PdfNumber(v));
/*     */   }
/*     */ 
/*     */   static void mergeResources(PdfDictionary result, PdfDictionary source, PdfStamperImp writer) {
/* 321 */     PdfDictionary dic = null;
/* 322 */     PdfDictionary res = null;
/* 323 */     PdfName target = null;
/* 324 */     for (int k = 0; k < mergeTarget.length; k++) {
/* 325 */       target = mergeTarget[k];
/* 326 */       PdfDictionary pdfDict = source.getAsDict(target);
/* 327 */       if ((dic = pdfDict) != null) {
/* 328 */         if ((res = (PdfDictionary)PdfReader.getPdfObject(result.get(target), result)) == null) {
/* 329 */           res = new PdfDictionary();
/*     */         }
/* 331 */         res.mergeDifferent(dic);
/* 332 */         result.put(target, res);
/* 333 */         if (writer != null)
/* 334 */           writer.markUsed(res);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static void mergeResources(PdfDictionary result, PdfDictionary source) {
/* 340 */     mergeResources(result, source, null);
/*     */   }
/*     */ 
/*     */   public void setUsed()
/*     */   {
/* 345 */     this.used = true;
/* 346 */     if (this.parent != null)
/* 347 */       put(PdfName.PARENT, this.parent.getIndirectReference());
/* 348 */     if (this.kids != null) {
/* 349 */       PdfArray array = new PdfArray();
/* 350 */       for (int k = 0; k < this.kids.size(); k++)
/* 351 */         array.add(((PdfFormField)this.kids.get(k)).getIndirectReference());
/* 352 */       put(PdfName.KIDS, array);
/*     */     }
/* 354 */     if (this.templates == null)
/* 355 */       return;
/* 356 */     PdfDictionary dic = new PdfDictionary();
/* 357 */     for (PdfTemplate template : this.templates) {
/* 358 */       mergeResources(dic, (PdfDictionary)template.getResources());
/*     */     }
/* 360 */     put(PdfName.DR, dic);
/*     */   }
/*     */ 
/*     */   public static PdfAnnotation shallowDuplicate(PdfAnnotation annot)
/*     */   {
/*     */     PdfAnnotation dup;
/* 365 */     if (annot.isForm()) {
/* 366 */       PdfAnnotation dup = new PdfFormField(annot.writer);
/* 367 */       PdfFormField dupField = (PdfFormField)dup;
/* 368 */       PdfFormField srcField = (PdfFormField)annot;
/* 369 */       dupField.parent = srcField.parent;
/* 370 */       dupField.kids = srcField.kids;
/*     */     }
/*     */     else {
/* 373 */       dup = new PdfAnnotation(annot.writer, null);
/* 374 */     }dup.merge(annot);
/* 375 */     dup.form = annot.form;
/* 376 */     dup.annotation = annot.annotation;
/* 377 */     dup.templates = annot.templates;
/* 378 */     return dup;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfFormField
 * JD-Core Version:    0.6.2
 */