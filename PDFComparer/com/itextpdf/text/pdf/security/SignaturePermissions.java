/*     */ package com.itextpdf.text.pdf.security;
/*     */ 
/*     */ import com.itextpdf.text.pdf.PdfArray;
/*     */ import com.itextpdf.text.pdf.PdfDictionary;
/*     */ import com.itextpdf.text.pdf.PdfName;
/*     */ import com.itextpdf.text.pdf.PdfNumber;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class SignaturePermissions
/*     */ {
/*  86 */   boolean certification = false;
/*     */ 
/*  88 */   boolean fillInAllowed = true;
/*     */ 
/*  90 */   boolean annotationsAllowed = true;
/*     */ 
/*  92 */   List<FieldLock> fieldLocks = new ArrayList();
/*     */ 
/*     */   public SignaturePermissions(PdfDictionary sigDict, SignaturePermissions previous)
/*     */   {
/* 100 */     if (previous != null) {
/* 101 */       this.annotationsAllowed &= previous.isAnnotationsAllowed();
/* 102 */       this.fillInAllowed &= previous.isFillInAllowed();
/* 103 */       this.fieldLocks.addAll(previous.getFieldLocks());
/*     */     }
/* 105 */     PdfArray ref = sigDict.getAsArray(PdfName.REFERENCE);
/* 106 */     if (ref != null)
/* 107 */       for (int i = 0; i < ref.size(); i++) {
/* 108 */         PdfDictionary dict = ref.getAsDict(i);
/* 109 */         PdfDictionary params = dict.getAsDict(PdfName.TRANSFORMPARAMS);
/* 110 */         if (PdfName.DOCMDP.equals(dict.getAsName(PdfName.TRANSFORMMETHOD))) {
/* 111 */           this.certification = true;
/*     */         }
/* 113 */         PdfName action = params.getAsName(PdfName.ACTION);
/* 114 */         if (action != null) {
/* 115 */           this.fieldLocks.add(new FieldLock(action, params.getAsArray(PdfName.FIELDS)));
/*     */         }
/* 117 */         PdfNumber p = params.getAsNumber(PdfName.P);
/* 118 */         if (p != null)
/*     */         {
/* 120 */           switch (p.intValue()) {
/*     */           default:
/* 122 */             break;
/*     */           case 1:
/* 124 */             this.fillInAllowed &= false;
/*     */           case 2:
/* 126 */             this.annotationsAllowed &= false;
/*     */           }
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   public boolean isCertification()
/*     */   {
/* 137 */     return this.certification;
/*     */   }
/*     */ 
/*     */   public boolean isFillInAllowed()
/*     */   {
/* 144 */     return this.fillInAllowed;
/*     */   }
/*     */ 
/*     */   public boolean isAnnotationsAllowed()
/*     */   {
/* 151 */     return this.annotationsAllowed;
/*     */   }
/*     */ 
/*     */   public List<FieldLock> getFieldLocks()
/*     */   {
/* 158 */     return this.fieldLocks;
/*     */   }
/*     */ 
/*     */   public class FieldLock
/*     */   {
/*     */     PdfName action;
/*     */     PdfArray fields;
/*     */ 
/*     */     public FieldLock(PdfName action, PdfArray fields)
/*     */     {
/*  72 */       this.action = action;
/*  73 */       this.fields = fields;
/*     */     }
/*     */     public PdfName getAction() {
/*  76 */       return this.action;
/*     */     }
/*  78 */     public PdfArray getFields() { return this.fields; }
/*     */ 
/*     */     public String toString() {
/*  81 */       return this.action.toString() + (this.fields == null ? "" : this.fields.toString());
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.security.SignaturePermissions
 * JD-Core Version:    0.6.2
 */