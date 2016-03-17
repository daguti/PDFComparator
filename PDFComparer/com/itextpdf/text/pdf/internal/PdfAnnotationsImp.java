/*     */ package com.itextpdf.text.pdf.internal;
/*     */ 
/*     */ import com.itextpdf.text.Annotation;
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import com.itextpdf.text.Rectangle;
/*     */ import com.itextpdf.text.pdf.PdfAcroForm;
/*     */ import com.itextpdf.text.pdf.PdfAction;
/*     */ import com.itextpdf.text.pdf.PdfAnnotation;
/*     */ import com.itextpdf.text.pdf.PdfArray;
/*     */ import com.itextpdf.text.pdf.PdfFileSpecification;
/*     */ import com.itextpdf.text.pdf.PdfFormField;
/*     */ import com.itextpdf.text.pdf.PdfName;
/*     */ import com.itextpdf.text.pdf.PdfNumber;
/*     */ import com.itextpdf.text.pdf.PdfRectangle;
/*     */ import com.itextpdf.text.pdf.PdfString;
/*     */ import com.itextpdf.text.pdf.PdfWriter;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ 
/*     */ public class PdfAnnotationsImp
/*     */ {
/*     */   protected PdfAcroForm acroForm;
/*     */   protected ArrayList<PdfAnnotation> annotations;
/*  85 */   protected ArrayList<PdfAnnotation> delayedAnnotations = new ArrayList();
/*     */ 
/*     */   public PdfAnnotationsImp(PdfWriter writer)
/*     */   {
/*  89 */     this.acroForm = new PdfAcroForm(writer);
/*     */   }
/*     */ 
/*     */   public boolean hasValidAcroForm()
/*     */   {
/*  96 */     return this.acroForm.isValid();
/*     */   }
/*     */ 
/*     */   public PdfAcroForm getAcroForm()
/*     */   {
/* 104 */     return this.acroForm;
/*     */   }
/*     */ 
/*     */   public void setSigFlags(int f) {
/* 108 */     this.acroForm.setSigFlags(f);
/*     */   }
/*     */ 
/*     */   public void addCalculationOrder(PdfFormField formField) {
/* 112 */     this.acroForm.addCalculationOrder(formField);
/*     */   }
/*     */ 
/*     */   public void addAnnotation(PdfAnnotation annot) {
/* 116 */     if (annot.isForm()) {
/* 117 */       PdfFormField field = (PdfFormField)annot;
/* 118 */       if (field.getParent() == null)
/* 119 */         addFormFieldRaw(field);
/*     */     }
/*     */     else {
/* 122 */       this.annotations.add(annot);
/*     */     }
/*     */   }
/*     */ 
/* 126 */   public void addPlainAnnotation(PdfAnnotation annot) { this.annotations.add(annot); }
/*     */ 
/*     */   void addFormFieldRaw(PdfFormField field)
/*     */   {
/* 130 */     this.annotations.add(field);
/* 131 */     ArrayList kids = field.getKids();
/* 132 */     if (kids != null)
/* 133 */       for (int k = 0; k < kids.size(); k++)
/* 134 */         addFormFieldRaw((PdfFormField)kids.get(k));
/*     */   }
/*     */ 
/*     */   public boolean hasUnusedAnnotations()
/*     */   {
/* 139 */     return !this.annotations.isEmpty();
/*     */   }
/*     */ 
/*     */   public void resetAnnotations() {
/* 143 */     this.annotations = this.delayedAnnotations;
/* 144 */     this.delayedAnnotations = new ArrayList();
/*     */   }
/*     */ 
/*     */   public PdfArray rotateAnnotations(PdfWriter writer, Rectangle pageSize) {
/* 148 */     PdfArray array = new PdfArray();
/* 149 */     int rotation = pageSize.getRotation() % 360;
/* 150 */     int currentPage = writer.getCurrentPageNumber();
/* 151 */     for (int k = 0; k < this.annotations.size(); k++) {
/* 152 */       PdfAnnotation dic = (PdfAnnotation)this.annotations.get(k);
/* 153 */       int page = dic.getPlaceInPage();
/* 154 */       if (page > currentPage) {
/* 155 */         this.delayedAnnotations.add(dic);
/*     */       }
/*     */       else {
/* 158 */         if (dic.isForm()) {
/* 159 */           if (!dic.isUsed()) {
/* 160 */             HashSet templates = dic.getTemplates();
/* 161 */             if (templates != null)
/* 162 */               this.acroForm.addFieldTemplates(templates);
/*     */           }
/* 164 */           PdfFormField field = (PdfFormField)dic;
/* 165 */           if (field.getParent() == null)
/* 166 */             this.acroForm.addDocumentField(field.getIndirectReference());
/*     */         }
/* 168 */         if (dic.isAnnotation()) {
/* 169 */           array.add(dic.getIndirectReference());
/* 170 */           if (!dic.isUsed()) {
/* 171 */             PdfArray tmp = dic.getAsArray(PdfName.RECT);
/*     */             PdfRectangle rect;
/*     */             PdfRectangle rect;
/* 173 */             if (tmp.size() == 4) {
/* 174 */               rect = new PdfRectangle(tmp.getAsNumber(0).floatValue(), tmp.getAsNumber(1).floatValue(), tmp.getAsNumber(2).floatValue(), tmp.getAsNumber(3).floatValue());
/*     */             }
/*     */             else {
/* 177 */               rect = new PdfRectangle(tmp.getAsNumber(0).floatValue(), tmp.getAsNumber(1).floatValue());
/*     */             }
/* 179 */             if (rect != null) {
/* 180 */               switch (rotation) {
/*     */               case 90:
/* 182 */                 dic.put(PdfName.RECT, new PdfRectangle(pageSize.getTop() - rect.bottom(), rect.left(), pageSize.getTop() - rect.top(), rect.right()));
/*     */ 
/* 187 */                 break;
/*     */               case 180:
/* 189 */                 dic.put(PdfName.RECT, new PdfRectangle(pageSize.getRight() - rect.left(), pageSize.getTop() - rect.bottom(), pageSize.getRight() - rect.right(), pageSize.getTop() - rect.top()));
/*     */ 
/* 194 */                 break;
/*     */               case 270:
/* 196 */                 dic.put(PdfName.RECT, new PdfRectangle(rect.bottom(), pageSize.getRight() - rect.left(), rect.top(), pageSize.getRight() - rect.right()));
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 206 */         if (!dic.isUsed()) {
/* 207 */           dic.setUsed();
/*     */           try {
/* 209 */             writer.addToBody(dic, dic.getIndirectReference());
/*     */           }
/*     */           catch (IOException e) {
/* 212 */             throw new ExceptionConverter(e);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 216 */     return array;
/*     */   }
/*     */ 
/*     */   public static PdfAnnotation convertAnnotation(PdfWriter writer, Annotation annot, Rectangle defaultRect) throws IOException {
/* 220 */     switch (annot.annotationType()) {
/*     */     case 1:
/* 222 */       return new PdfAnnotation(writer, annot.llx(), annot.lly(), annot.urx(), annot.ury(), new PdfAction((URL)annot.attributes().get("url")));
/*     */     case 2:
/* 224 */       return new PdfAnnotation(writer, annot.llx(), annot.lly(), annot.urx(), annot.ury(), new PdfAction((String)annot.attributes().get("file")));
/*     */     case 3:
/* 226 */       return new PdfAnnotation(writer, annot.llx(), annot.lly(), annot.urx(), annot.ury(), new PdfAction((String)annot.attributes().get("file"), (String)annot.attributes().get("destination")));
/*     */     case 7:
/* 228 */       boolean[] sparams = (boolean[])annot.attributes().get("parameters");
/* 229 */       String fname = (String)annot.attributes().get("file");
/* 230 */       String mimetype = (String)annot.attributes().get("mime");
/*     */       PdfFileSpecification fs;
/*     */       PdfFileSpecification fs;
/* 232 */       if (sparams[0] != 0)
/* 233 */         fs = PdfFileSpecification.fileEmbedded(writer, fname, fname, null);
/*     */       else
/* 235 */         fs = PdfFileSpecification.fileExtern(writer, fname);
/* 236 */       PdfAnnotation ann = PdfAnnotation.createScreen(writer, new Rectangle(annot.llx(), annot.lly(), annot.urx(), annot.ury()), fname, fs, mimetype, sparams[1]);
/*     */ 
/* 238 */       return ann;
/*     */     case 4:
/* 240 */       return new PdfAnnotation(writer, annot.llx(), annot.lly(), annot.urx(), annot.ury(), new PdfAction((String)annot.attributes().get("file"), ((Integer)annot.attributes().get("page")).intValue()));
/*     */     case 5:
/* 242 */       return new PdfAnnotation(writer, annot.llx(), annot.lly(), annot.urx(), annot.ury(), new PdfAction(((Integer)annot.attributes().get("named")).intValue()));
/*     */     case 6:
/* 244 */       return new PdfAnnotation(writer, annot.llx(), annot.lly(), annot.urx(), annot.ury(), new PdfAction((String)annot.attributes().get("application"), (String)annot.attributes().get("parameters"), (String)annot.attributes().get("operation"), (String)annot.attributes().get("defaultdir")));
/*     */     }
/* 246 */     return new PdfAnnotation(writer, defaultRect.getLeft(), defaultRect.getBottom(), defaultRect.getRight(), defaultRect.getTop(), new PdfString(annot.title(), "UnicodeBig"), new PdfString(annot.content(), "UnicodeBig"));
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.internal.PdfAnnotationsImp
 * JD-Core Version:    0.6.2
 */