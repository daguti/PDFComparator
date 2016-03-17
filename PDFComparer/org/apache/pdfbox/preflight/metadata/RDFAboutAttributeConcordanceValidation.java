/*    */ package org.apache.pdfbox.preflight.metadata;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*    */ import org.apache.xmpbox.XMPMetadata;
/*    */ import org.apache.xmpbox.schema.XMPSchema;
/*    */ 
/*    */ public class RDFAboutAttributeConcordanceValidation
/*    */ {
/*    */   public void validateRDFAboutAttributes(XMPMetadata metadata)
/*    */     throws ValidationException, RDFAboutAttributeConcordanceValidation.DifferentRDFAboutException
/*    */   {
/* 49 */     List schemas = metadata.getAllSchemas();
/* 50 */     if (schemas.size() == 0)
/*    */     {
/* 52 */       throw new ValidationException("Schemas not found in the given metadata representation");
/*    */     }
/*    */ 
/* 55 */     String about = ((XMPSchema)schemas.get(0)).getAboutValue();
/*    */ 
/* 58 */     for (XMPSchema xmpSchema : schemas)
/*    */     {
/* 61 */       String schemaAboutValue = xmpSchema.getAboutValue();
/* 62 */       if ((!"".equals(schemaAboutValue)) && (!"".equals(about)) && (!about.equals(schemaAboutValue)))
/*    */       {
/* 64 */         throw new DifferentRDFAboutException();
/*    */       }
/*    */ 
/* 67 */       if ("".equals(about))
/* 68 */         about = schemaAboutValue;
/*    */     }
/*    */   }
/*    */ 
/*    */   public static class DifferentRDFAboutException extends Exception
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */ 
/*    */     public DifferentRDFAboutException()
/*    */     {
/* 81 */       super();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.metadata.RDFAboutAttributeConcordanceValidation
 * JD-Core Version:    0.6.2
 */