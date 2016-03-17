/*    */ package org.apache.pdfbox.pdmodel.interactive.form;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ 
/*    */ public class PDUnknownField extends PDField
/*    */ {
/*    */   public PDUnknownField(PDAcroForm theAcroForm, COSDictionary field)
/*    */   {
/* 39 */     super(theAcroForm, field);
/*    */   }
/*    */ 
/*    */   public void setValue(String value)
/*    */     throws IOException
/*    */   {
/*    */   }
/*    */ 
/*    */   public String getValue()
/*    */     throws IOException
/*    */   {
/* 55 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.form.PDUnknownField
 * JD-Core Version:    0.6.2
 */