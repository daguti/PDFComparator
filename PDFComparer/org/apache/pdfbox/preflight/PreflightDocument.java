/*     */ package org.apache.pdfbox.preflight;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*     */ import org.apache.pdfbox.preflight.utils.ContextHelper;
/*     */ 
/*     */ public class PreflightDocument extends PDDocument
/*     */ {
/*  36 */   protected ValidationResult result = new ValidationResult(true);
/*     */   protected PreflightConfiguration config;
/*     */   protected PreflightContext context;
/*     */   protected final Format specification;
/*     */ 
/*     */   public PreflightDocument(Format format)
/*     */     throws IOException
/*     */   {
/*  52 */     this(format, (PreflightConfiguration)null);
/*     */   }
/*     */ 
/*     */   public PreflightDocument(COSDocument doc, Format format)
/*     */   {
/*  63 */     this(doc, format, null);
/*     */   }
/*     */ 
/*     */   public PreflightDocument(Format format, PreflightConfiguration cfg)
/*     */     throws IOException
/*     */   {
/*  76 */     this(new COSDocument(), format, cfg);
/*     */   }
/*     */ 
/*     */   public PreflightDocument(COSDocument doc, Format format, PreflightConfiguration cfg)
/*     */   {
/*  90 */     super(doc);
/*  91 */     this.specification = format;
/*  92 */     this.config = cfg;
/*  93 */     if (this.config == null)
/*     */     {
/*  95 */       initConfiguration(format);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void initConfiguration(Format format)
/*     */   {
/* 101 */     switch (1.$SwitchMap$org$apache$pdfbox$preflight$Format[format.ordinal()])
/*     */     {
/*     */     }
/*     */ 
/* 108 */     this.config = PreflightConfiguration.createPdfA1BConfiguration();
/*     */   }
/*     */ 
/*     */   public ValidationResult getResult()
/*     */   {
/* 116 */     return this.result;
/*     */   }
/*     */ 
/*     */   public void setResult(ValidationResult _result)
/*     */   {
/* 121 */     if (this.result != null)
/*     */     {
/* 123 */       this.result.mergeResult(_result);
/*     */     }
/* 125 */     else if (_result != null)
/*     */     {
/* 127 */       this.result = _result;
/*     */     }
/*     */     else
/*     */     {
/* 131 */       this.result = new ValidationResult(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addValidationError(ValidationResult.ValidationError error)
/*     */   {
/* 137 */     if (error != null)
/*     */     {
/* 139 */       if (this.result == null)
/*     */       {
/* 141 */         this.result = new ValidationResult(error.isWarning());
/*     */       }
/* 143 */       this.result.addError(error);
/*     */     }
/*     */   }
/*     */ 
/*     */   public PreflightContext getContext()
/*     */   {
/* 149 */     return this.context;
/*     */   }
/*     */ 
/*     */   public void setContext(PreflightContext context)
/*     */   {
/* 154 */     this.context = context;
/*     */   }
/*     */ 
/*     */   public void validate()
/*     */     throws ValidationException
/*     */   {
/* 164 */     this.context.setConfig(this.config);
/* 165 */     Collection processes = this.config.getProcessNames();
/* 166 */     for (String name : processes)
/*     */     {
/* 168 */       ContextHelper.validateElement(this.context, name);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Format getSpecification()
/*     */   {
/* 174 */     return this.specification;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.PreflightDocument
 * JD-Core Version:    0.6.2
 */