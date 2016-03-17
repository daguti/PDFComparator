/*    */ package com.itextpdf.text.pdf.collection;
/*    */ 
/*    */ import com.itextpdf.text.error_messages.MessageLocalization;
/*    */ import com.itextpdf.text.pdf.PdfArray;
/*    */ import com.itextpdf.text.pdf.PdfBoolean;
/*    */ import com.itextpdf.text.pdf.PdfDictionary;
/*    */ import com.itextpdf.text.pdf.PdfName;
/*    */ import com.itextpdf.text.pdf.PdfObject;
/*    */ 
/*    */ public class PdfCollectionSort extends PdfDictionary
/*    */ {
/*    */   public PdfCollectionSort(String key)
/*    */   {
/* 61 */     super(PdfName.COLLECTIONSORT);
/* 62 */     put(PdfName.S, new PdfName(key));
/*    */   }
/*    */ 
/*    */   public PdfCollectionSort(String[] keys)
/*    */   {
/* 70 */     super(PdfName.COLLECTIONSORT);
/* 71 */     PdfArray array = new PdfArray();
/* 72 */     for (int i = 0; i < keys.length; i++) {
/* 73 */       array.add(new PdfName(keys[i]));
/*    */     }
/* 75 */     put(PdfName.S, array);
/*    */   }
/*    */ 
/*    */   public void setSortOrder(boolean ascending)
/*    */   {
/* 83 */     PdfObject o = get(PdfName.S);
/* 84 */     if ((o instanceof PdfName)) {
/* 85 */       put(PdfName.A, new PdfBoolean(ascending));
/*    */     }
/*    */     else
/* 88 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("you.have.to.define.a.boolean.array.for.this.collection.sort.dictionary", new Object[0]));
/*    */   }
/*    */ 
/*    */   public void setSortOrder(boolean[] ascending)
/*    */   {
/* 97 */     PdfObject o = get(PdfName.S);
/* 98 */     if ((o instanceof PdfArray)) {
/* 99 */       if (((PdfArray)o).size() != ascending.length) {
/* 100 */         throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.number.of.booleans.in.this.array.doesn.t.correspond.with.the.number.of.fields", new Object[0]));
/*    */       }
/* 102 */       PdfArray array = new PdfArray();
/* 103 */       for (int i = 0; i < ascending.length; i++) {
/* 104 */         array.add(new PdfBoolean(ascending[i]));
/*    */       }
/* 106 */       put(PdfName.A, array);
/*    */     }
/*    */     else {
/* 109 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("you.need.a.single.boolean.for.this.collection.sort.dictionary", new Object[0]));
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.collection.PdfCollectionSort
 * JD-Core Version:    0.6.2
 */