/*    */ package org.apache.pdfbox.pdmodel.interactive.form;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ 
/*    */ public class PDTextbox extends PDVariableText
/*    */ {
/*    */   public PDTextbox(PDAcroForm theAcroForm)
/*    */   {
/* 38 */     super(theAcroForm);
/*    */   }
/*    */ 
/*    */   public PDTextbox(PDAcroForm theAcroForm, COSDictionary field)
/*    */   {
/* 49 */     super(theAcroForm, field);
/*    */   }
/*    */ 
/*    */   public int getMaxLen()
/*    */   {
/* 59 */     return getDictionary().getInt(COSName.MAX_LEN);
/*    */   }
/*    */ 
/*    */   public void setMaxLen(int maxLen)
/*    */   {
/* 69 */     getDictionary().setInt(COSName.MAX_LEN, maxLen);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.form.PDTextbox
 * JD-Core Version:    0.6.2
 */