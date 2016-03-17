/*    */ package org.apache.pdfbox.pdmodel.interactive.form;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.cos.COSString;
/*    */ 
/*    */ public class PDPushButton extends PDField
/*    */ {
/*    */   public PDPushButton(PDAcroForm theAcroForm, COSDictionary field)
/*    */   {
/* 42 */     super(theAcroForm, field);
/*    */   }
/*    */ 
/*    */   public void setValue(String value)
/*    */     throws IOException
/*    */   {
/* 54 */     COSString fieldValue = new COSString(value);
/* 55 */     getDictionary().setItem(COSName.getPDFName("V"), fieldValue);
/* 56 */     getDictionary().setItem(COSName.getPDFName("DV"), fieldValue);
/*    */   }
/*    */ 
/*    */   public String getValue()
/*    */     throws IOException
/*    */   {
/* 68 */     return getDictionary().getString("V");
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.form.PDPushButton
 * JD-Core Version:    0.6.2
 */