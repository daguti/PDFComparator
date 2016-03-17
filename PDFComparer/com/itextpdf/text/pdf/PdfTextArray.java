/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class PdfTextArray
/*     */ {
/*  60 */   ArrayList<Object> arrayList = new ArrayList();
/*     */   private String lastStr;
/*     */   private Float lastNum;
/*     */ 
/*     */   public PdfTextArray(String str)
/*     */   {
/*  72 */     add(str);
/*     */   }
/*     */ 
/*     */   public PdfTextArray()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void add(PdfNumber number)
/*     */   {
/*  84 */     add((float)number.doubleValue());
/*     */   }
/*     */ 
/*     */   public void add(float number) {
/*  88 */     if (number != 0.0F) {
/*  89 */       if (this.lastNum != null) {
/*  90 */         this.lastNum = new Float(number + this.lastNum.floatValue());
/*  91 */         if (this.lastNum.floatValue() != 0.0F)
/*  92 */           replaceLast(this.lastNum);
/*     */         else
/*  94 */           this.arrayList.remove(this.arrayList.size() - 1);
/*     */       }
/*     */       else {
/*  97 */         this.lastNum = new Float(number);
/*  98 */         this.arrayList.add(this.lastNum);
/*     */       }
/*     */ 
/* 101 */       this.lastStr = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void add(String str)
/*     */   {
/* 107 */     if (str.length() > 0) {
/* 108 */       if (this.lastStr != null) {
/* 109 */         this.lastStr += str;
/* 110 */         replaceLast(this.lastStr);
/*     */       } else {
/* 112 */         this.lastStr = str;
/* 113 */         this.arrayList.add(this.lastStr);
/*     */       }
/* 115 */       this.lastNum = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   ArrayList<Object> getArrayList()
/*     */   {
/* 121 */     return this.arrayList;
/*     */   }
/*     */ 
/*     */   private void replaceLast(Object obj)
/*     */   {
/* 126 */     this.arrayList.set(this.arrayList.size() - 1, obj);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfTextArray
 * JD-Core Version:    0.6.2
 */