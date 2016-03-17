/*    */ package com.itextpdf.text.pdf;
/*    */ 
/*    */ public class RefKey
/*    */ {
/*    */   int num;
/*    */   int gen;
/*    */ 
/*    */   RefKey(int num, int gen)
/*    */   {
/* 55 */     this.num = num;
/* 56 */     this.gen = gen;
/*    */   }
/*    */   public RefKey(PdfIndirectReference ref) {
/* 59 */     this.num = ref.getNumber();
/* 60 */     this.gen = ref.getGeneration();
/*    */   }
/*    */   RefKey(PRIndirectReference ref) {
/* 63 */     this.num = ref.getNumber();
/* 64 */     this.gen = ref.getGeneration();
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 68 */     return (this.gen << 16) + this.num;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object o) {
/* 72 */     if (!(o instanceof RefKey)) return false;
/* 73 */     RefKey other = (RefKey)o;
/* 74 */     return (this.gen == other.gen) && (this.num == other.num);
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 78 */     return Integer.toString(this.num) + ' ' + this.gen;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.RefKey
 * JD-Core Version:    0.6.2
 */