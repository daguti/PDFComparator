/*    */ package org.apache.pdfbox.pdmodel.interactive.form;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.pdfbox.cos.COSArray;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.cos.COSString;
/*    */ import org.apache.pdfbox.pdmodel.common.COSArrayList;
/*    */ 
/*    */ public abstract class PDChoiceButton extends PDField
/*    */ {
/*    */   public PDChoiceButton(PDAcroForm theAcroForm, COSDictionary field)
/*    */   {
/* 46 */     super(theAcroForm, field);
/*    */   }
/*    */ 
/*    */   public List getOptions()
/*    */   {
/* 56 */     List retval = null;
/* 57 */     COSArray array = (COSArray)getDictionary().getDictionaryObject(COSName.getPDFName("Opt"));
/* 58 */     if (array != null)
/*    */     {
/* 60 */       List strings = new ArrayList();
/* 61 */       for (int i = 0; i < array.size(); i++)
/*    */       {
/* 63 */         strings.add(((COSString)array.getObject(i)).getString());
/*    */       }
/* 65 */       retval = new COSArrayList(strings, array);
/*    */     }
/* 67 */     return retval;
/*    */   }
/*    */ 
/*    */   public void setOptions(List options)
/*    */   {
/* 77 */     getDictionary().setItem(COSName.getPDFName("Opt"), COSArrayList.converterToCOSArray(options));
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.form.PDChoiceButton
 * JD-Core Version:    0.6.2
 */