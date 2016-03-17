/*     */ package org.apache.pdfbox.preflight.process;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSObject;
/*     */ import org.apache.pdfbox.io.IOUtils;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
/*     */ import org.apache.pdfbox.pdmodel.common.PDMetadata;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ import org.apache.pdfbox.preflight.PreflightContext;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*     */ import org.apache.pdfbox.preflight.metadata.PDFAIdentificationValidation;
/*     */ import org.apache.pdfbox.preflight.metadata.RDFAboutAttributeConcordanceValidation;
/*     */ import org.apache.pdfbox.preflight.metadata.RDFAboutAttributeConcordanceValidation.DifferentRDFAboutException;
/*     */ import org.apache.pdfbox.preflight.metadata.SynchronizedMetaDataValidation;
/*     */ import org.apache.pdfbox.preflight.metadata.XpacketParsingException;
/*     */ import org.apache.pdfbox.preflight.utils.COSUtils;
/*     */ import org.apache.xmpbox.XMPMetadata;
/*     */ import org.apache.xmpbox.xml.DomXmpParser;
/*     */ import org.apache.xmpbox.xml.XmpParsingException;
/*     */ import org.apache.xmpbox.xml.XmpParsingException.ErrorType;
/*     */ 
/*     */ public class MetadataValidationProcess extends AbstractProcess
/*     */ {
/*     */   public void validate(PreflightContext ctx)
/*     */     throws ValidationException
/*     */   {
/*     */     try
/*     */     {
/*  60 */       PDDocument document = ctx.getDocument();
/*     */ 
/*  62 */       byte[] tmp = getXpacket(document.getDocument());
/*     */ 
/*  64 */       DomXmpParser builder = new DomXmpParser();
/*     */ 
/*  66 */       XMPMetadata metadata = builder.parse(tmp);
/*  67 */       ctx.setMetadata(metadata);
/*     */ 
/*  70 */       if (metadata.getXpacketBytes() != null)
/*     */       {
/*  72 */         addValidationError(ctx, new ValidationResult.ValidationError("7.0.0", "bytes attribute is forbidden"));
/*     */       }
/*     */ 
/*  75 */       if (metadata.getXpacketEncoding() != null)
/*     */       {
/*  77 */         addValidationError(ctx, new ValidationResult.ValidationError("7.0.0", "encoding attribute is forbidden"));
/*     */       }
/*     */ 
/*  82 */       addValidationErrors(ctx, new SynchronizedMetaDataValidation().validateMetadataSynchronization(document, metadata));
/*     */ 
/*  86 */       addValidationErrors(ctx, new PDFAIdentificationValidation().validatePDFAIdentifer(metadata));
/*     */       try
/*     */       {
/*  91 */         new RDFAboutAttributeConcordanceValidation().validateRDFAboutAttributes(metadata);
/*     */       }
/*     */       catch (RDFAboutAttributeConcordanceValidation.DifferentRDFAboutException e)
/*     */       {
/*  95 */         addValidationError(ctx, new ValidationResult.ValidationError("7.0.1", e.getMessage()));
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (XpacketParsingException e)
/*     */     {
/* 102 */       if (e.getError() != null)
/*     */       {
/* 104 */         addValidationError(ctx, e.getError());
/*     */       }
/*     */       else
/*     */       {
/* 108 */         addValidationError(ctx, new ValidationResult.ValidationError("7", "Unexpected error"));
/*     */       }
/*     */     }
/*     */     catch (XmpParsingException e)
/*     */     {
/* 113 */       if (e.getErrorType() == XmpParsingException.ErrorType.NoValueType)
/*     */       {
/* 115 */         addValidationError(ctx, new ValidationResult.ValidationError("7.6", e.getMessage()));
/*     */       }
/* 118 */       else if (e.getErrorType() == XmpParsingException.ErrorType.RequiredProperty)
/*     */       {
/* 120 */         addValidationError(ctx, new ValidationResult.ValidationError("7.5", e.getMessage()));
/*     */       }
/* 123 */       else if (e.getErrorType() == XmpParsingException.ErrorType.InvalidPrefix)
/*     */       {
/* 125 */         addValidationError(ctx, new ValidationResult.ValidationError("7.3", e.getMessage()));
/*     */       }
/* 128 */       else if (e.getErrorType() == XmpParsingException.ErrorType.InvalidType)
/*     */       {
/* 130 */         addValidationError(ctx, new ValidationResult.ValidationError("7.1.1", e.getMessage()));
/*     */       }
/*     */       else {
/* 133 */         if (e.getErrorType() == XmpParsingException.ErrorType.XpacketBadEnd)
/*     */         {
/* 135 */           throw new ValidationException("Unable to parse font metadata due to : " + e.getMessage(), e);
/*     */         }
/* 137 */         if (e.getErrorType() == XmpParsingException.ErrorType.NoSchema)
/*     */         {
/* 139 */           addValidationError(ctx, new ValidationResult.ValidationError("7.3", e.getMessage()));
/*     */         }
/* 142 */         else if (e.getErrorType() == XmpParsingException.ErrorType.InvalidPdfaSchema)
/*     */         {
/* 144 */           addValidationError(ctx, new ValidationResult.ValidationError("7.4.1", e.getMessage()));
/*     */         }
/*     */         else
/*     */         {
/* 149 */           addValidationError(ctx, new ValidationResult.ValidationError("7.1", e.getMessage()));
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (IOException e) {
/* 154 */       throw new ValidationException("Failed while validating", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static byte[] getXpacket(COSDocument cdocument)
/*     */     throws IOException, XpacketParsingException
/*     */   {
/* 163 */     COSObject catalog = cdocument.getCatalog();
/* 164 */     COSBase cb = catalog.getDictionaryObject(COSName.METADATA);
/* 165 */     if (cb == null)
/*     */     {
/* 168 */       ValidationResult.ValidationError error = new ValidationResult.ValidationError("7.1", "Missing Metadata Key in catalog");
/*     */ 
/* 170 */       throw new XpacketParsingException("Failed while retrieving xpacket", error);
/*     */     }
/*     */ 
/* 173 */     COSDictionary metadataDictionnary = COSUtils.getAsDictionary(cb, cdocument);
/* 174 */     if (metadataDictionnary.getItem(COSName.FILTER) != null)
/*     */     {
/* 177 */       ValidationResult.ValidationError error = new ValidationResult.ValidationError("1.2.7", "Filter specified in metadata dictionnary");
/*     */ 
/* 179 */       throw new XpacketParsingException("Failed while retrieving xpacket", error);
/*     */     }
/*     */ 
/* 182 */     PDStream stream = PDStream.createFromCOS(metadataDictionnary);
/* 183 */     ByteArrayOutputStream bos = new ByteArrayOutputStream();
/* 184 */     InputStream is = stream.createInputStream();
/* 185 */     IOUtils.copy(is, bos);
/* 186 */     is.close();
/* 187 */     bos.close();
/* 188 */     return bos.toByteArray();
/*     */   }
/*     */ 
/*     */   protected List<ValidationResult.ValidationError> checkStreamFilterUsage(PDDocument doc)
/*     */   {
/* 199 */     List ve = new ArrayList();
/* 200 */     List filters = doc.getDocumentCatalog().getMetadata().getFilters();
/* 201 */     if ((filters != null) && (!filters.isEmpty()))
/*     */     {
/* 203 */       ve.add(new ValidationResult.ValidationError("7", "Using stream filter on metadata dictionary is forbidden"));
/*     */     }
/*     */ 
/* 206 */     return ve;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.process.MetadataValidationProcess
 * JD-Core Version:    0.6.2
 */