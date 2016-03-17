/*     */ package org.apache.pdfbox.preflight.metadata;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*     */ import org.apache.xmpbox.XMPMetadata;
/*     */ import org.apache.xmpbox.schema.PDFAIdentificationSchema;
/*     */ import org.apache.xmpbox.schema.XMPBasicSchema;
/*     */ import org.apache.xmpbox.type.StructuredType;
/*     */ 
/*     */ public class PDFAIdentificationValidation
/*     */ {
/*     */   public List<ValidationResult.ValidationError> validatePDFAIdentifer(XMPMetadata metadata)
/*     */     throws ValidationException
/*     */   {
/*  60 */     List ve = new ArrayList();
/*  61 */     PDFAIdentificationSchema id = metadata.getPDFIdentificationSchema();
/*  62 */     if (id == null)
/*     */     {
/*  64 */       ve.add(new ValidationResult.ValidationError("7.11", "PDF/A identification schema " + ((StructuredType)PDFAIdentificationSchema.class.getAnnotation(StructuredType.class)).namespace() + " is missing"));
/*     */ 
/*  68 */       return ve;
/*     */     }
/*     */ 
/*  72 */     StructuredType stBasic = (StructuredType)XMPBasicSchema.class.getAnnotation(StructuredType.class);
/*  73 */     StructuredType stPdfaIdent = (StructuredType)PDFAIdentificationSchema.class.getAnnotation(StructuredType.class);
/*  74 */     if (!id.getPrefix().equals(stPdfaIdent.preferedPrefix()))
/*     */     {
/*  76 */       if (metadata.getSchema(stPdfaIdent.preferedPrefix(), stBasic.namespace()) == null)
/*     */       {
/*  78 */         ve.add(unexpectedPrefixFoundError(id.getPrefix(), stPdfaIdent.preferedPrefix(), PDFAIdentificationSchema.class.getName()));
/*     */       }
/*     */       else
/*     */       {
/*  83 */         id = (PDFAIdentificationSchema)metadata.getSchema(stPdfaIdent.preferedPrefix(), stPdfaIdent.namespace());
/*     */       }
/*     */     }
/*     */ 
/*  87 */     checkConformanceLevel(ve, id.getConformance());
/*  88 */     checkPartNumber(ve, id.getPart().intValue());
/*  89 */     return ve;
/*     */   }
/*     */ 
/*     */   protected ValidationResult.ValidationError unexpectedPrefixFoundError(String prefFound, String prefExpected, String schema)
/*     */   {
/* 102 */     StringBuilder sb = new StringBuilder(80);
/* 103 */     sb.append(schema).append(" found but prefix used is '").append(prefFound).append("', prefix '").append(prefExpected).append("' is expected.");
/*     */ 
/* 106 */     return new ValidationResult.ValidationError("7.4.2", sb.toString());
/*     */   }
/*     */ 
/*     */   protected void checkConformanceLevel(List<ValidationResult.ValidationError> ve, String value)
/*     */   {
/* 111 */     if ((value == null) || ((!value.equals("A")) && (!value.equals("B"))))
/*     */     {
/* 113 */       ve.add(new ValidationResult.ValidationError("7.11.1"));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void checkPartNumber(List<ValidationResult.ValidationError> ve, int value)
/*     */   {
/* 119 */     if (value != 1)
/*     */     {
/* 121 */       ve.add(new ValidationResult.ValidationError("7.11.2"));
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.metadata.PDFAIdentificationValidation
 * JD-Core Version:    0.6.2
 */