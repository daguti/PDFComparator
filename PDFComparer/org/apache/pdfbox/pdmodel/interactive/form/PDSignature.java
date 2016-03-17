/*    */ package org.apache.pdfbox.pdmodel.interactive.form;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ 
/*    */ /** @deprecated */
/*    */ public class PDSignature extends PDField
/*    */ {
/*    */   public PDSignature(PDAcroForm theAcroForm, COSDictionary field)
/*    */   {
/* 42 */     super(theAcroForm, field);
/* 43 */     throw new RuntimeException("The usage of " + getClass().getName() + " is deprecated. Please use " + PDSignatureField.class.getName() + " instead (see PDFBOX-1513)");
/*    */   }
/*    */ 
/*    */   public void setValue(String value)
/*    */     throws IOException
/*    */   {
/* 56 */     throw new RuntimeException("Not yet implemented");
/*    */   }
/*    */ 
/*    */   public String getValue()
/*    */     throws IOException
/*    */   {
/* 68 */     throw new RuntimeException("Not yet implemented");
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 78 */     return "PDSignature";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.form.PDSignature
 * JD-Core Version:    0.6.2
 */