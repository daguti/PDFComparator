/*     */ package org.apache.pdfbox.preflight.xobject;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.pdmodel.PDPage;
/*     */ import org.apache.pdfbox.pdmodel.PDResources;
/*     */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectForm;
/*     */ import org.apache.pdfbox.preflight.PreflightContext;
/*     */ import org.apache.pdfbox.preflight.PreflightPath;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.content.ContentStreamWrapper;
/*     */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*     */ import org.apache.pdfbox.preflight.utils.COSUtils;
/*     */ import org.apache.pdfbox.preflight.utils.ContextHelper;
/*     */ 
/*     */ public class XObjFormValidator extends AbstractXObjValidator
/*     */ {
/*  56 */   PDXObjectForm pdXObj = null;
/*     */ 
/*     */   public XObjFormValidator(PreflightContext context, PDXObjectForm xobj)
/*     */   {
/*  60 */     super(context, xobj.getCOSStream());
/*  61 */     this.pdXObj = xobj;
/*     */   }
/*     */ 
/*     */   public void validate()
/*     */     throws ValidationException
/*     */   {
/*  72 */     super.validate();
/*  73 */     checkGroup();
/*  74 */     checkSubtype2Value();
/*  75 */     validateXObjectResources();
/*  76 */     validateXObjectContent();
/*     */   }
/*     */ 
/*     */   protected void checkMandatoryFields()
/*     */   {
/*  87 */     boolean lastMod = this.xobject.getItem(COSName.LAST_MODIFIED) != null;
/*  88 */     boolean pieceInfo = this.xobject.getItem("PieceInfo") != null;
/*     */ 
/*  90 */     if ((lastMod ^ pieceInfo))
/*     */     {
/*  92 */       this.context.addValidationError(new ValidationResult.ValidationError("2.1.7"));
/*  93 */       return;
/*     */     }
/*     */ 
/*  96 */     COSBase bbBase = this.xobject.getItem(COSName.BBOX);
/*     */ 
/*  98 */     if ((bbBase == null) || (!COSUtils.isArray(bbBase, this.cosDocument)))
/*     */     {
/* 100 */       this.context.addValidationError(new ValidationResult.ValidationError("2.1.1"));
/* 101 */       return;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void validateXObjectContent()
/*     */     throws ValidationException
/*     */   {
/* 111 */     PreflightPath vPath = this.context.getValidationPath();
/* 112 */     ContentStreamWrapper csWrapper = new ContentStreamWrapper(this.context, (PDPage)vPath.getClosestPathElement(PDPage.class));
/* 113 */     csWrapper.validXObjContentStream(this.pdXObj);
/*     */   }
/*     */ 
/*     */   protected void checkGroup()
/*     */   {
/* 123 */     COSBase baseGroup = this.xobject.getItem("Group");
/* 124 */     COSDictionary groupDictionary = COSUtils.getAsDictionary(baseGroup, this.cosDocument);
/* 125 */     if (groupDictionary != null)
/*     */     {
/* 127 */       if (!"Group".equals(groupDictionary.getNameAsString(COSName.TYPE)))
/*     */       {
/* 129 */         this.context.addValidationError(new ValidationResult.ValidationError("2.1.7", "The Group dictionary hasn't Group as Type value"));
/*     */       }
/*     */       else
/*     */       {
/* 133 */         String sVal = groupDictionary.getNameAsString(COSName.S);
/* 134 */         if ((sVal == null) || ("Transparency".equals(sVal)))
/*     */         {
/* 136 */           this.context.addValidationError(new ValidationResult.ValidationError("2.2.1", "Group has a transparency S entry or the S entry is null."));
/* 137 */           return;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void checkPS()
/*     */   {
/* 149 */     if (this.xobject.getItem(COSName.getPDFName("PS")) != null)
/*     */     {
/* 151 */       this.context.addValidationError(new ValidationResult.ValidationError("2.3", "Unexpected 'PS' Key"));
/* 152 */       return;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void checkSubtype2Value()
/*     */   {
/* 162 */     if (this.xobject.getItem(COSName.getPDFName("Subtype2")) != null)
/*     */     {
/* 164 */       if ("PS".equals(this.xobject.getNameAsString("Subtype2")))
/*     */       {
/* 166 */         this.context.addValidationError(new ValidationResult.ValidationError("2.3.2", "Unexpected 'PS' value for 'Subtype2' Key"));
/*     */ 
/* 168 */         return;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void validateXObjectResources() throws ValidationException
/*     */   {
/* 175 */     PDResources resources = this.pdXObj.getResources();
/* 176 */     if (resources != null)
/*     */     {
/* 178 */       ContextHelper.validateElement(this.context, resources, "resources-process");
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.xobject.XObjFormValidator
 * JD-Core Version:    0.6.2
 */