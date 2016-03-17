/*    */ package org.apache.pdfbox.preflight.process;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSDocument;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.cos.COSObject;
/*    */ import org.apache.pdfbox.pdmodel.PDDocument;
/*    */ import org.apache.pdfbox.preflight.PreflightContext;
/*    */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*    */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*    */ 
/*    */ public class FileSpecificationValidationProcess extends AbstractProcess
/*    */ {
/*    */   public void validate(PreflightContext ctx)
/*    */     throws ValidationException
/*    */   {
/* 50 */     PDDocument pdfDoc = ctx.getDocument();
/* 51 */     COSDocument cDoc = pdfDoc.getDocument();
/*    */ 
/* 53 */     List lCOSObj = cDoc.getObjects();
/* 54 */     for (Iterator i$ = lCOSObj.iterator(); i$.hasNext(); ) { Object o = i$.next();
/*    */ 
/* 56 */       COSBase cBase = ((COSObject)o).getObject();
/* 57 */       if ((cBase instanceof COSDictionary))
/*    */       {
/* 59 */         COSDictionary dic = (COSDictionary)cBase;
/* 60 */         String type = dic.getNameAsString(COSName.TYPE);
/* 61 */         if (("Filespec".equals(type)) || (COSName.F.getName().equals(type)))
/*    */         {
/* 64 */           validateFileSpecification(ctx, dic);
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public List<ValidationResult.ValidationError> validateFileSpecification(PreflightContext ctx, COSDictionary fileSpec)
/*    */   {
/* 81 */     List result = new ArrayList(0);
/*    */ 
/* 85 */     if (fileSpec.getItem(COSName.getPDFName("EF")) != null)
/*    */     {
/* 87 */       addValidationError(ctx, new ValidationResult.ValidationError("1.2.9", "EmbeddedFile entry is present in a FileSpecification dictionary"));
/*    */     }
/*    */ 
/* 91 */     return result;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.process.FileSpecificationValidationProcess
 * JD-Core Version:    0.6.2
 */