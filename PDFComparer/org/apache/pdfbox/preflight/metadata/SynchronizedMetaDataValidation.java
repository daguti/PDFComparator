/*     */ package org.apache.pdfbox.preflight.metadata;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.PDDocumentInformation;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*     */ import org.apache.xmpbox.DateConverter;
/*     */ import org.apache.xmpbox.XMPMetadata;
/*     */ import org.apache.xmpbox.schema.AdobePDFSchema;
/*     */ import org.apache.xmpbox.schema.DublinCoreSchema;
/*     */ import org.apache.xmpbox.schema.XMPBasicSchema;
/*     */ import org.apache.xmpbox.type.AbstractField;
/*     */ import org.apache.xmpbox.type.ArrayProperty;
/*     */ import org.apache.xmpbox.type.ComplexPropertyContainer;
/*     */ import org.apache.xmpbox.type.TextType;
/*     */ 
/*     */ public class SynchronizedMetaDataValidation
/*     */ {
/*     */   protected void analyzeTitleProperty(PDDocumentInformation dico, DublinCoreSchema dc, List<ValidationResult.ValidationError> ve)
/*     */   {
/*  64 */     String title = dico.getTitle();
/*  65 */     if (title != null)
/*     */     {
/*  68 */       title = removeTrailingNul(title);
/*  69 */       if (dc != null)
/*     */       {
/*  73 */         if (dc.getTitle() != null)
/*     */         {
/*  75 */           if (dc.getTitle("x-default") != null)
/*     */           {
/*  77 */             if (!dc.getTitle("x-default").equals(title))
/*     */             {
/*  79 */               ve.add(unsynchronizedMetaDataError("Title"));
/*     */             }
/*     */ 
/*     */           }
/*     */           else
/*     */           {
/*  88 */             Iterator it = dc.getTitleProperty().getContainer().getAllProperties().iterator();
/*  89 */             if (it.hasNext())
/*     */             {
/*  91 */               AbstractField tmp = (AbstractField)it.next();
/*  92 */               if ((tmp instanceof TextType))
/*     */               {
/*  94 */                 if (!((TextType)tmp).getStringValue().equals(title))
/*     */                 {
/*  96 */                   ve.add(unsynchronizedMetaDataError("Title"));
/*     */                 }
/*     */               }
/*     */               else
/*     */               {
/* 101 */                 ve.add(AbsentXMPPropertyError("Title", "Property is badly defined"));
/*     */               }
/*     */             }
/*     */             else
/*     */             {
/* 106 */               ve.add(AbsentXMPPropertyError("Title", "Property is not defined"));
/*     */             }
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 113 */           ve.add(AbsentXMPPropertyError("Title", "Property is not defined"));
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 118 */         ve.add(AbsentSchemaMetaDataError("Title", "Dublin Core"));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void analyzeAuthorProperty(PDDocumentInformation dico, DublinCoreSchema dc, List<ValidationResult.ValidationError> ve)
/*     */   {
/* 135 */     String author = dico.getAuthor();
/* 136 */     if (author != null)
/*     */     {
/* 139 */       author = removeTrailingNul(author);
/* 140 */       if (dc != null)
/*     */       {
/* 142 */         if (dc.getCreatorsProperty() != null)
/*     */         {
/* 144 */           if (dc.getCreators().size() != 1)
/*     */           {
/* 146 */             ve.add(AbsentXMPPropertyError("Author", "In XMP metadata, Author(s) must be represented by a single entry in a text array (dc:creator) "));
/*     */           }
/* 151 */           else if (dc.getCreators().get(0) == null)
/*     */           {
/* 153 */             ve.add(AbsentXMPPropertyError("Author", "Property is defined as null"));
/*     */           }
/* 157 */           else if (!((String)dc.getCreators().get(0)).equals(author))
/*     */           {
/* 159 */             ve.add(unsynchronizedMetaDataError("Author"));
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 166 */           ve.add(AbsentXMPPropertyError("Author", "Property is not defined in XMP Metadata"));
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 171 */         ve.add(AbsentSchemaMetaDataError("Author", "Dublin Core"));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void analyzeSubjectProperty(PDDocumentInformation dico, DublinCoreSchema dc, List<ValidationResult.ValidationError> ve)
/*     */   {
/* 188 */     String subject = dico.getSubject();
/* 189 */     if (subject != null)
/*     */     {
/* 192 */       subject = removeTrailingNul(subject);
/* 193 */       if (dc != null)
/*     */       {
/* 197 */         if (dc.getDescriptionProperty() != null)
/*     */         {
/* 199 */           if (dc.getDescription("x-default") == null)
/*     */           {
/* 201 */             ve.add(AbsentXMPPropertyError("Subject", "Subject not found in XMP (dc:description[\"x-default\"] not found)"));
/*     */           }
/* 206 */           else if (!dc.getDescription("x-default").equals(subject))
/*     */           {
/* 208 */             ve.add(unsynchronizedMetaDataError("Subject"));
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 215 */           ve.add(AbsentXMPPropertyError("Subject", "Property is defined as null"));
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 220 */         ve.add(AbsentSchemaMetaDataError("Subject", "Dublin Core"));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void analyzeKeywordsProperty(PDDocumentInformation dico, AdobePDFSchema pdf, List<ValidationResult.ValidationError> ve)
/*     */   {
/* 237 */     String keyword = dico.getKeywords();
/* 238 */     if (keyword != null)
/*     */     {
/* 241 */       keyword = removeTrailingNul(keyword);
/* 242 */       if (pdf != null)
/*     */       {
/* 244 */         if (pdf.getKeywordsProperty() == null)
/*     */         {
/* 246 */           ve.add(AbsentXMPPropertyError("Keywords", "Property is not defined"));
/*     */         }
/* 250 */         else if (!pdf.getKeywords().equals(keyword))
/*     */         {
/* 252 */           ve.add(unsynchronizedMetaDataError("Keywords"));
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 258 */         ve.add(AbsentSchemaMetaDataError("Keywords", "PDF"));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void analyzeProducerProperty(PDDocumentInformation dico, AdobePDFSchema pdf, List<ValidationResult.ValidationError> ve)
/*     */   {
/* 275 */     String producer = dico.getProducer();
/* 276 */     if (producer != null)
/*     */     {
/* 279 */       producer = removeTrailingNul(producer);
/* 280 */       if (pdf != null)
/*     */       {
/* 282 */         if (pdf.getProducerProperty() == null)
/*     */         {
/* 284 */           ve.add(AbsentXMPPropertyError("Producer", "Property is not defined"));
/*     */         }
/* 288 */         else if (!pdf.getProducer().equals(producer))
/*     */         {
/* 290 */           ve.add(unsynchronizedMetaDataError("Producer"));
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 296 */         ve.add(AbsentSchemaMetaDataError("Producer", "PDF"));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void analyzeCreatorToolProperty(PDDocumentInformation dico, XMPBasicSchema xmp, List<ValidationResult.ValidationError> ve)
/*     */   {
/* 315 */     String creatorTool = dico.getCreator();
/* 316 */     if (creatorTool != null)
/*     */     {
/* 319 */       creatorTool = removeTrailingNul(creatorTool);
/* 320 */       if (xmp != null)
/*     */       {
/* 322 */         if (xmp.getCreatorToolProperty() == null)
/*     */         {
/* 324 */           ve.add(AbsentXMPPropertyError("CreatorTool", "Property is not defined"));
/*     */         }
/* 328 */         else if (!xmp.getCreatorTool().equals(creatorTool))
/*     */         {
/* 330 */           ve.add(unsynchronizedMetaDataError("CreatorTool"));
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 336 */         ve.add(AbsentSchemaMetaDataError("CreatorTool", "PDF"));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void analyzeCreationDateProperty(PDDocumentInformation dico, XMPBasicSchema xmp, List<ValidationResult.ValidationError> ve)
/*     */     throws ValidationException
/*     */   {
/* 356 */     Calendar creationDate = null;
/*     */     try
/*     */     {
/* 359 */       creationDate = dico.getCreationDate();
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 364 */       ve.add(new ValidationResult.ValidationError("7.12", "Document Information 'CreationDate' can't be read : " + e.getMessage()));
/*     */     }
/* 366 */     if (creationDate != null)
/*     */     {
/* 368 */       if (xmp != null)
/*     */       {
/* 370 */         Calendar xmpCreationDate = xmp.getCreateDate();
/*     */ 
/* 372 */         if (xmpCreationDate == null)
/*     */         {
/* 374 */           ve.add(AbsentXMPPropertyError("CreationDate", "Property is not defined"));
/*     */         }
/* 378 */         else if (!DateConverter.toISO8601(xmpCreationDate).equals(DateConverter.toISO8601(creationDate)))
/*     */         {
/* 380 */           ve.add(unsynchronizedMetaDataError("CreationDate"));
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 387 */         ve.add(AbsentSchemaMetaDataError("CreationDate", "Basic XMP"));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void analyzeModifyDateProperty(PDDocumentInformation dico, XMPBasicSchema xmp, List<ValidationResult.ValidationError> ve)
/*     */     throws ValidationException
/*     */   {
/*     */     try
/*     */     {
/* 409 */       Calendar modifyDate = dico.getModificationDate();
/* 410 */       if (modifyDate != null)
/*     */       {
/* 412 */         if (xmp != null)
/*     */         {
/* 415 */           Calendar xmpModifyDate = xmp.getModifyDate();
/* 416 */           if (xmpModifyDate == null)
/*     */           {
/* 418 */             ve.add(AbsentXMPPropertyError("ModifyDate", "Property is not defined"));
/*     */           }
/* 422 */           else if (!DateConverter.toISO8601(xmpModifyDate).equals(DateConverter.toISO8601(modifyDate)))
/*     */           {
/* 425 */             ve.add(unsynchronizedMetaDataError("ModificationDate"));
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 432 */           ve.add(AbsentSchemaMetaDataError("ModifyDate", "Basic XMP"));
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 439 */       ve.add(new ValidationResult.ValidationError("7.12", "Document Information 'ModifyDate' can't be read : " + e.getMessage()));
/*     */     }
/*     */   }
/*     */ 
/*     */   public List<ValidationResult.ValidationError> validateMetadataSynchronization(PDDocument document, XMPMetadata metadata)
/*     */     throws ValidationException
/*     */   {
/* 457 */     List ve = new ArrayList();
/*     */ 
/* 459 */     if (document == null)
/*     */     {
/* 461 */       throw new ValidationException("Document provided is null");
/*     */     }
/*     */ 
/* 465 */     PDDocumentInformation dico = document.getDocumentInformation();
/* 466 */     if (metadata == null)
/*     */     {
/* 468 */       throw new ValidationException("Metadata provided are null");
/*     */     }
/*     */ 
/* 472 */     DublinCoreSchema dc = metadata.getDublinCoreSchema();
/*     */ 
/* 475 */     analyzeTitleProperty(dico, dc, ve);
/*     */ 
/* 477 */     analyzeAuthorProperty(dico, dc, ve);
/*     */ 
/* 479 */     analyzeSubjectProperty(dico, dc, ve);
/*     */ 
/* 481 */     AdobePDFSchema pdf = metadata.getAdobePDFSchema();
/*     */ 
/* 484 */     analyzeKeywordsProperty(dico, pdf, ve);
/*     */ 
/* 486 */     analyzeProducerProperty(dico, pdf, ve);
/*     */ 
/* 488 */     XMPBasicSchema xmp = metadata.getXMPBasicSchema();
/*     */ 
/* 491 */     analyzeCreatorToolProperty(dico, xmp, ve);
/*     */ 
/* 494 */     analyzeCreationDateProperty(dico, xmp, ve);
/*     */ 
/* 497 */     analyzeModifyDateProperty(dico, xmp, ve);
/*     */ 
/* 502 */     return ve;
/*     */   }
/*     */ 
/*     */   protected ValidationResult.ValidationError unexpectedPrefixFoundError(String prefFound, String prefExpected, String schema)
/*     */   {
/* 515 */     StringBuilder sb = new StringBuilder(80);
/* 516 */     sb.append(schema).append(" found but prefix used is '").append(prefFound).append("', prefix '").append(prefExpected).append("' is expected.");
/*     */ 
/* 519 */     return new ValidationResult.ValidationError("7.4.2", sb.toString());
/*     */   }
/*     */ 
/*     */   protected ValidationException SchemaAccessException(String target, Throwable cause)
/*     */   {
/* 533 */     StringBuilder sb = new StringBuilder(80);
/* 534 */     sb.append("Cannot access to the ").append(target).append(" schema");
/* 535 */     return new ValidationException(sb.toString(), cause);
/*     */   }
/*     */ 
/*     */   protected ValidationResult.ValidationError unsynchronizedMetaDataError(String target)
/*     */   {
/* 547 */     StringBuilder sb = new StringBuilder(80);
/* 548 */     sb.append(target).append(" present in the document catalog dictionary doesn't match with XMP information");
/* 549 */     return new ValidationResult.ValidationError("7.2", sb.toString());
/*     */   }
/*     */ 
/*     */   protected ValidationResult.ValidationError AbsentSchemaMetaDataError(String target, String schema)
/*     */   {
/* 563 */     StringBuilder sb = new StringBuilder(80);
/* 564 */     sb.append(target).append(" present in the document catalog dictionary can't be found in XMP information (").append(schema).append(" schema not declared)");
/*     */ 
/* 566 */     return new ValidationResult.ValidationError("7.2", sb.toString());
/*     */   }
/*     */ 
/*     */   protected ValidationResult.ValidationError AbsentXMPPropertyError(String target, String details)
/*     */   {
/* 580 */     StringBuilder sb = new StringBuilder(80);
/* 581 */     sb.append(target).append(" present in the document catalog dictionary can't be found in XMP information (").append(details).append(")");
/*     */ 
/* 583 */     return new ValidationResult.ValidationError("7.2", sb.toString());
/*     */   }
/*     */ 
/*     */   private String removeTrailingNul(String string)
/*     */   {
/* 596 */     int length = string.length();
/* 597 */     while ((length > 0) && (string.charAt(length - 1) == 0))
/*     */     {
/* 599 */       length--;
/*     */     }
/* 601 */     return string.substring(0, length);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.metadata.SynchronizedMetaDataValidation
 * JD-Core Version:    0.6.2
 */