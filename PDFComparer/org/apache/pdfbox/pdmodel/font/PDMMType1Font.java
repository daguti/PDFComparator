/*    */ package org.apache.pdfbox.pdmodel.font;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ 
/*    */ public class PDMMType1Font extends PDType1Font
/*    */ {
/*    */   public PDMMType1Font()
/*    */   {
/* 36 */     this.font.setItem(COSName.SUBTYPE, COSName.MM_TYPE1);
/*    */   }
/*    */ 
/*    */   public PDMMType1Font(COSDictionary fontDictionary)
/*    */   {
/* 46 */     super(fontDictionary);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.font.PDMMType1Font
 * JD-Core Version:    0.6.2
 */