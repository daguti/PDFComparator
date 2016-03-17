/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ public class PdfSigLockDictionary extends PdfDictionary
/*     */ {
/*     */   public PdfSigLockDictionary()
/*     */   {
/*  94 */     super(PdfName.SIGFIELDLOCK);
/*  95 */     put(PdfName.ACTION, LockAction.ALL.getValue());
/*     */   }
/*     */ 
/*     */   public PdfSigLockDictionary(LockPermissions p)
/*     */   {
/* 103 */     this();
/* 104 */     put(PdfName.P, p.getValue());
/*     */   }
/*     */ 
/*     */   public PdfSigLockDictionary(LockAction action, String[] fields)
/*     */   {
/* 111 */     this(action, null, fields);
/*     */   }
/*     */ 
/*     */   public PdfSigLockDictionary(LockAction action, LockPermissions p, String[] fields)
/*     */   {
/* 118 */     super(PdfName.SIGFIELDLOCK);
/* 119 */     put(PdfName.ACTION, action.getValue());
/* 120 */     if (p != null)
/* 121 */       put(PdfName.P, p.getValue());
/* 122 */     PdfArray fieldsArray = new PdfArray();
/* 123 */     for (String field : fields) {
/* 124 */       fieldsArray.add(new PdfString(field));
/*     */     }
/* 126 */     put(PdfName.FIELDS, fieldsArray);
/*     */   }
/*     */ 
/*     */   public static enum LockPermissions
/*     */   {
/*  77 */     NO_CHANGES_ALLOWED(1), FORM_FILLING(2), FORM_FILLING_AND_ANNOTATION(3);
/*     */ 
/*     */     private PdfNumber number;
/*     */ 
/*     */     private LockPermissions(int p) {
/*  82 */       this.number = new PdfNumber(p);
/*     */     }
/*     */ 
/*     */     public PdfNumber getValue() {
/*  86 */       return this.number;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static enum LockAction
/*     */   {
/*  60 */     ALL(PdfName.ALL), INCLUDE(PdfName.INCLUDE), EXCLUDE(PdfName.EXCLUDE);
/*     */ 
/*     */     private PdfName name;
/*     */ 
/*     */     private LockAction(PdfName name) {
/*  65 */       this.name = name;
/*     */     }
/*     */ 
/*     */     public PdfName getValue() {
/*  69 */       return this.name;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfSigLockDictionary
 * JD-Core Version:    0.6.2
 */