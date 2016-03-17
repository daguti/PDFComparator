/*    */ package com.itextpdf.text.pdf.fonts.cmaps;
/*    */ 
/*    */ import com.itextpdf.text.io.RandomAccessSourceFactory;
/*    */ import com.itextpdf.text.pdf.PRTokeniser;
/*    */ import com.itextpdf.text.pdf.RandomAccessFileOrArray;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class CidLocationFromByte
/*    */   implements CidLocation
/*    */ {
/*    */   private byte[] data;
/*    */ 
/*    */   public CidLocationFromByte(byte[] data)
/*    */   {
/* 61 */     this.data = data;
/*    */   }
/*    */ 
/*    */   public PRTokeniser getLocation(String location) throws IOException {
/* 65 */     return new PRTokeniser(new RandomAccessFileOrArray(new RandomAccessSourceFactory().createSource(this.data)));
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.fonts.cmaps.CidLocationFromByte
 * JD-Core Version:    0.6.2
 */