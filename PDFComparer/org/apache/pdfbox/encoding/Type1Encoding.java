/*    */ package org.apache.pdfbox.encoding;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ 
/*    */ public class Type1Encoding extends Encoding
/*    */ {
/*    */   public Type1Encoding(int size)
/*    */   {
/* 29 */     for (int i = 1; i < size; i++)
/*    */     {
/* 31 */       addCharacterEncoding(i, ".notdef");
/*    */     }
/*    */   }
/*    */ 
/*    */   public COSBase getCOSObject()
/*    */   {
/* 40 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.encoding.Type1Encoding
 * JD-Core Version:    0.6.2
 */