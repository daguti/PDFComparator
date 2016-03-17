/*     */ package org.apache.pdfbox.preflight;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.activation.DataSource;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.pdfparser.XrefTrailerResolver;
/*     */ import org.apache.pdfbox.preflight.font.container.FontContainer;
/*     */ import org.apache.pdfbox.preflight.graphic.ICCProfileWrapper;
/*     */ import org.apache.pdfbox.preflight.utils.COSUtils;
/*     */ import org.apache.xmpbox.XMPMetadata;
/*     */ 
/*     */ public class PreflightContext
/*     */   implements Closeable
/*     */ {
/*  44 */   protected Map<COSBase, FontContainer> fontContainers = new HashMap();
/*     */ 
/*  49 */   protected PreflightDocument document = null;
/*     */ 
/*  54 */   protected DataSource source = null;
/*     */   private XrefTrailerResolver xrefTableResolver;
/*  70 */   protected ICCProfileWrapper iccProfileWrapper = null;
/*     */ 
/*  75 */   protected boolean iccProfileAlreadySearched = false;
/*     */ 
/*  80 */   protected XMPMetadata metadata = null;
/*     */ 
/*  82 */   protected PreflightConfiguration config = null;
/*     */ 
/*  84 */   protected PreflightPath validationPath = new PreflightPath();
/*     */ 
/*     */   public PreflightContext(DataSource source)
/*     */   {
/*  93 */     this.source = source;
/*     */   }
/*     */ 
/*     */   public PreflightContext(DataSource source, PreflightConfiguration configuration)
/*     */   {
/*  98 */     this.source = source;
/*     */   }
/*     */ 
/*     */   public XMPMetadata getMetadata()
/*     */   {
/* 106 */     return this.metadata;
/*     */   }
/*     */ 
/*     */   public void setMetadata(XMPMetadata metadata)
/*     */   {
/* 115 */     this.metadata = metadata;
/*     */   }
/*     */ 
/*     */   public PreflightDocument getDocument()
/*     */   {
/* 139 */     return this.document;
/*     */   }
/*     */ 
/*     */   public XrefTrailerResolver getXrefTableResolver()
/*     */   {
/* 144 */     return this.xrefTableResolver;
/*     */   }
/*     */ 
/*     */   public void setXrefTableResolver(XrefTrailerResolver xrefTableResolver)
/*     */   {
/* 149 */     this.xrefTableResolver = xrefTableResolver;
/*     */   }
/*     */ 
/*     */   public void setDocument(PreflightDocument document)
/*     */   {
/* 159 */     this.document = document;
/*     */   }
/*     */ 
/*     */   public DataSource getSource()
/*     */   {
/* 168 */     return this.source;
/*     */   }
/*     */ 
/*     */   public boolean isComplete()
/*     */   {
/* 173 */     return (this.document != null) && (this.source != null);
/*     */   }
/*     */ 
/*     */   public void addFontContainer(COSBase cBase, FontContainer fc)
/*     */   {
/* 184 */     this.fontContainers.put(cBase, fc);
/*     */   }
/*     */ 
/*     */   public FontContainer getFontContainer(COSBase cBase)
/*     */   {
/* 196 */     return (FontContainer)this.fontContainers.get(cBase);
/*     */   }
/*     */ 
/*     */   public ICCProfileWrapper getIccProfileWrapper()
/*     */   {
/* 204 */     return this.iccProfileWrapper;
/*     */   }
/*     */ 
/*     */   public void setIccProfileWrapper(ICCProfileWrapper iccProfileWrapper)
/*     */   {
/* 213 */     this.iccProfileWrapper = iccProfileWrapper;
/*     */   }
/*     */ 
/*     */   public PreflightConfiguration getConfig()
/*     */   {
/* 218 */     return this.config;
/*     */   }
/*     */ 
/*     */   public void setConfig(PreflightConfiguration config)
/*     */   {
/* 223 */     this.config = config;
/*     */   }
/*     */ 
/*     */   public void close()
/*     */   {
/* 231 */     COSUtils.closeDocumentQuietly(this.document);
/*     */   }
/*     */ 
/*     */   public void addValidationError(ValidationResult.ValidationError error)
/*     */   {
/* 241 */     PreflightDocument document = this.document;
/* 242 */     document.addValidationError(error);
/*     */   }
/*     */ 
/*     */   public void addValidationErrors(List<ValidationResult.ValidationError> errors)
/*     */   {
/* 252 */     PreflightDocument document = this.document;
/* 253 */     for (ValidationResult.ValidationError error : errors)
/*     */     {
/* 255 */       document.addValidationError(error);
/*     */     }
/*     */   }
/*     */ 
/*     */   public PreflightPath getValidationPath()
/*     */   {
/* 261 */     return this.validationPath;
/*     */   }
/*     */ 
/*     */   public void setValidationPath(PreflightPath validationPath)
/*     */   {
/* 266 */     this.validationPath = validationPath;
/*     */   }
/*     */ 
/*     */   public boolean isIccProfileAlreadySearched()
/*     */   {
/* 271 */     return this.iccProfileAlreadySearched;
/*     */   }
/*     */ 
/*     */   public void setIccProfileAlreadySearched(boolean iccProfileAlreadySearched)
/*     */   {
/* 276 */     this.iccProfileAlreadySearched = iccProfileAlreadySearched;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.PreflightContext
 * JD-Core Version:    0.6.2
 */