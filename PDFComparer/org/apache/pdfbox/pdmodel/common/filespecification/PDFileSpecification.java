/*    */ package org.apache.pdfbox.pdmodel.common.filespecification;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSString;
/*    */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*    */ 
/*    */ public abstract class PDFileSpecification
/*    */   implements COSObjectable
/*    */ {
/*    */   public static PDFileSpecification createFS(COSBase base)
/*    */     throws IOException
/*    */   {
/* 48 */     PDFileSpecification retval = null;
/* 49 */     if (base != null)
/*    */     {
/* 53 */       if ((base instanceof COSString))
/*    */       {
/* 55 */         retval = new PDSimpleFileSpecification((COSString)base);
/*    */       }
/* 57 */       else if ((base instanceof COSDictionary))
/*    */       {
/* 59 */         retval = new PDComplexFileSpecification((COSDictionary)base);
/*    */       }
/*    */       else
/*    */       {
/* 63 */         throw new IOException("Error: Unknown file specification " + base);
/*    */       }
/*    */     }
/* 65 */     return retval;
/*    */   }
/*    */ 
/*    */   public abstract String getFile();
/*    */ 
/*    */   public abstract void setFile(String paramString);
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.filespecification.PDFileSpecification
 * JD-Core Version:    0.6.2
 */