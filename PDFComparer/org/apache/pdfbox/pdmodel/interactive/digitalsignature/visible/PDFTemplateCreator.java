/*     */ package org.apache.pdfbox.pdmodel.interactive.digitalsignature.visible;
/*     */ 
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.exceptions.COSVisitorException;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.PDPage;
/*     */ import org.apache.pdfbox.pdmodel.PDResources;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectForm;
/*     */ import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
/*     */ import org.apache.pdfbox.pdmodel.interactive.form.PDSignatureField;
/*     */ 
/*     */ public class PDFTemplateCreator
/*     */ {
/*     */   PDFTemplateBuilder pdfBuilder;
/*  44 */   private static final Log logger = LogFactory.getLog(PDFTemplateCreator.class);
/*     */ 
/*     */   public PDFTemplateCreator(PDFTemplateBuilder bookBuilder)
/*     */   {
/*  53 */     this.pdfBuilder = bookBuilder;
/*     */   }
/*     */ 
/*     */   public PDFTemplateStructure getPdfStructure()
/*     */   {
/*  63 */     return this.pdfBuilder.getStructure();
/*     */   }
/*     */ 
/*     */   public InputStream buildPDF(PDVisibleSignDesigner properties)
/*     */     throws IOException
/*     */   {
/*  76 */     logger.info("pdf building has been started");
/*  77 */     PDFTemplateStructure pdfStructure = this.pdfBuilder.getStructure();
/*     */ 
/*  80 */     this.pdfBuilder.createProcSetArray();
/*     */ 
/*  83 */     this.pdfBuilder.createPage(properties);
/*  84 */     PDPage page = pdfStructure.getPage();
/*     */ 
/*  87 */     this.pdfBuilder.createTemplate(page);
/*  88 */     PDDocument template = pdfStructure.getTemplate();
/*     */ 
/*  91 */     this.pdfBuilder.createAcroForm(template);
/*  92 */     PDAcroForm acroForm = pdfStructure.getAcroForm();
/*     */ 
/*  95 */     this.pdfBuilder.createSignatureField(acroForm);
/*  96 */     PDSignatureField pdSignatureField = pdfStructure.getSignatureField();
/*     */ 
/*  99 */     this.pdfBuilder.createSignature(pdSignatureField, page, properties.getSignatureFieldName());
/*     */ 
/* 102 */     this.pdfBuilder.createAcroFormDictionary(acroForm, pdSignatureField);
/*     */ 
/* 105 */     this.pdfBuilder.createAffineTransform(properties.getAffineTransformParams());
/* 106 */     AffineTransform transform = pdfStructure.getAffineTransform();
/*     */ 
/* 109 */     this.pdfBuilder.createSignatureRectangle(pdSignatureField, properties);
/* 110 */     this.pdfBuilder.createFormaterRectangle(properties.getFormaterRectangleParams());
/* 111 */     PDRectangle formater = pdfStructure.getFormaterRectangle();
/* 112 */     this.pdfBuilder.createSignatureImage(template, properties.getImageStream());
/*     */ 
/* 115 */     this.pdfBuilder.createHolderFormStream(template);
/* 116 */     PDStream holderFormStream = pdfStructure.getHolderFormStream();
/* 117 */     this.pdfBuilder.createHolderFormResources();
/* 118 */     PDResources holderFormResources = pdfStructure.getHolderFormResources();
/* 119 */     this.pdfBuilder.createHolderForm(holderFormResources, holderFormStream, formater);
/*     */ 
/* 122 */     this.pdfBuilder.createAppearanceDictionary(pdfStructure.getHolderForm(), pdSignatureField);
/*     */ 
/* 125 */     this.pdfBuilder.createInnerFormStream(template);
/* 126 */     this.pdfBuilder.createInnerFormResource();
/* 127 */     PDResources innerFormResource = pdfStructure.getInnerFormResources();
/* 128 */     this.pdfBuilder.createInnerForm(innerFormResource, pdfStructure.getInnterFormStream(), formater);
/* 129 */     PDXObjectForm innerForm = pdfStructure.getInnerForm();
/*     */ 
/* 132 */     this.pdfBuilder.insertInnerFormToHolerResources(innerForm, holderFormResources);
/*     */ 
/* 135 */     this.pdfBuilder.createImageFormStream(template);
/* 136 */     PDStream imageFormStream = pdfStructure.getImageFormStream();
/* 137 */     this.pdfBuilder.createImageFormResources();
/* 138 */     PDResources imageFormResources = pdfStructure.getImageFormResources();
/* 139 */     this.pdfBuilder.createImageForm(imageFormResources, innerFormResource, imageFormStream, formater, transform, pdfStructure.getJpedImage());
/*     */ 
/* 143 */     this.pdfBuilder.injectProcSetArray(innerForm, page, innerFormResource, imageFormResources, holderFormResources, pdfStructure.getProcSet());
/*     */ 
/* 146 */     String imgFormName = pdfStructure.getImageFormName();
/* 147 */     String imgName = pdfStructure.getImageName();
/* 148 */     String innerFormName = pdfStructure.getInnerFormName();
/*     */ 
/* 151 */     this.pdfBuilder.injectAppearanceStreams(holderFormStream, imageFormStream, imageFormStream, imgFormName, imgName, innerFormName, properties);
/*     */ 
/* 153 */     this.pdfBuilder.createVisualSignature(template);
/* 154 */     this.pdfBuilder.createWidgetDictionary(pdSignatureField, holderFormResources);
/*     */ 
/* 156 */     ByteArrayInputStream in = null;
/*     */     try
/*     */     {
/* 159 */       in = pdfStructure.getTemplateAppearanceStream();
/*     */     }
/*     */     catch (COSVisitorException e)
/*     */     {
/* 163 */       logger.error("COSVisitorException: can't get apereance stream ", e);
/*     */     }
/* 165 */     logger.info("stream returning started, size= " + in.available());
/*     */ 
/* 168 */     template.close();
/*     */ 
/* 171 */     return in;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.digitalsignature.visible.PDFTemplateCreator
 * JD-Core Version:    0.6.2
 */