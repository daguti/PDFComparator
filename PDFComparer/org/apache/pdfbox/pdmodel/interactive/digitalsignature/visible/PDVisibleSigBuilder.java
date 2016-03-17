/*     */ package org.apache.pdfbox.pdmodel.interactive.digitalsignature.visible;
/*     */ 
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.List;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
/*     */ import org.apache.pdfbox.pdmodel.PDPage;
/*     */ import org.apache.pdfbox.pdmodel.PDResources;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
/*     */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectForm;
/*     */ import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
/*     */ import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceDictionary;
/*     */ import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceStream;
/*     */ import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
/*     */ import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
/*     */ import org.apache.pdfbox.pdmodel.interactive.form.PDSignatureField;
/*     */ 
/*     */ public class PDVisibleSigBuilder
/*     */   implements PDFTemplateBuilder
/*     */ {
/*     */   private PDFTemplateStructure pdfStructure;
/*  54 */   private static final Log logger = LogFactory.getLog(PDVisibleSigBuilder.class);
/*     */ 
/*     */   public void createPage(PDVisibleSignDesigner properties)
/*     */   {
/*  58 */     PDPage page = new PDPage();
/*  59 */     page.setMediaBox(new PDRectangle(properties.getPageWidth(), properties.getPageHeight()));
/*  60 */     this.pdfStructure.setPage(page);
/*  61 */     logger.info("PDF page has been created");
/*     */   }
/*     */ 
/*     */   public void createTemplate(PDPage page) throws IOException
/*     */   {
/*  66 */     PDDocument template = new PDDocument();
/*  67 */     template.addPage(page);
/*  68 */     this.pdfStructure.setTemplate(template);
/*     */   }
/*     */ 
/*     */   public PDVisibleSigBuilder()
/*     */   {
/*  73 */     this.pdfStructure = new PDFTemplateStructure();
/*  74 */     logger.info("PDF Strucure has been Created");
/*     */   }
/*     */ 
/*     */   public void createAcroForm(PDDocument template)
/*     */   {
/*  80 */     PDAcroForm theAcroForm = new PDAcroForm(template);
/*  81 */     template.getDocumentCatalog().setAcroForm(theAcroForm);
/*  82 */     this.pdfStructure.setAcroForm(theAcroForm);
/*  83 */     logger.info("Acro form page has been created");
/*     */   }
/*     */ 
/*     */   public PDFTemplateStructure getStructure()
/*     */   {
/*  89 */     return this.pdfStructure;
/*     */   }
/*     */ 
/*     */   public void createSignatureField(PDAcroForm acroForm) throws IOException
/*     */   {
/*  94 */     PDSignatureField sf = new PDSignatureField(acroForm);
/*  95 */     this.pdfStructure.setSignatureField(sf);
/*  96 */     logger.info("Signature field has been created");
/*     */   }
/*     */ 
/*     */   public void createSignature(PDSignatureField pdSignatureField, PDPage page, String signatureName)
/*     */     throws IOException
/*     */   {
/* 102 */     PDSignature pdSignature = new PDSignature();
/* 103 */     pdSignatureField.setSignature(pdSignature);
/* 104 */     pdSignatureField.getWidget().setPage(page);
/* 105 */     page.getAnnotations().add(pdSignatureField.getWidget());
/* 106 */     pdSignature.setName(signatureName);
/* 107 */     pdSignature.setByteRange(new int[] { 0, 0, 0, 0 });
/* 108 */     pdSignature.setContents(new byte[4096]);
/* 109 */     this.pdfStructure.setPdSignature(pdSignature);
/* 110 */     logger.info("PDSignatur has been created");
/*     */   }
/*     */ 
/*     */   public void createAcroFormDictionary(PDAcroForm acroForm, PDSignatureField signatureField)
/*     */     throws IOException
/*     */   {
/* 116 */     List acroFormFields = acroForm.getFields();
/* 117 */     COSDictionary acroFormDict = acroForm.getDictionary();
/* 118 */     acroFormDict.setDirect(true);
/* 119 */     acroFormDict.setInt(COSName.SIG_FLAGS, 3);
/* 120 */     acroFormFields.add(signatureField);
/* 121 */     acroFormDict.setString(COSName.DA, "/sylfaen 0 Tf 0 g");
/* 122 */     this.pdfStructure.setAcroFormFields(acroFormFields);
/* 123 */     this.pdfStructure.setAcroFormDictionary(acroFormDict);
/* 124 */     logger.info("AcroForm dictionary has been created");
/*     */   }
/*     */ 
/*     */   public void createSignatureRectangle(PDSignatureField signatureField, PDVisibleSignDesigner properties)
/*     */     throws IOException
/*     */   {
/* 131 */     PDRectangle rect = new PDRectangle();
/* 132 */     rect.setUpperRightX(properties.getxAxis() + properties.getWidth());
/* 133 */     rect.setUpperRightY(properties.getTemplateHeight() - properties.getyAxis());
/* 134 */     rect.setLowerLeftY(properties.getTemplateHeight() - properties.getyAxis() - properties.getHeight());
/* 135 */     rect.setLowerLeftX(properties.getxAxis());
/* 136 */     signatureField.getWidget().setRectangle(rect);
/* 137 */     this.pdfStructure.setSignatureRectangle(rect);
/* 138 */     logger.info("rectangle of signature has been created");
/*     */   }
/*     */ 
/*     */   public void createAffineTransform(byte[] params)
/*     */   {
/* 143 */     AffineTransform transform = new AffineTransform(params[0], params[1], params[2], params[3], params[4], params[5]);
/*     */ 
/* 145 */     this.pdfStructure.setAffineTransform(transform);
/* 146 */     logger.info("Matrix has been added");
/*     */   }
/*     */ 
/*     */   public void createProcSetArray()
/*     */   {
/* 151 */     COSArray procSetArr = new COSArray();
/* 152 */     procSetArr.add(COSName.getPDFName("PDF"));
/* 153 */     procSetArr.add(COSName.getPDFName("Text"));
/* 154 */     procSetArr.add(COSName.getPDFName("ImageB"));
/* 155 */     procSetArr.add(COSName.getPDFName("ImageC"));
/* 156 */     procSetArr.add(COSName.getPDFName("ImageI"));
/* 157 */     this.pdfStructure.setProcSet(procSetArr);
/* 158 */     logger.info("ProcSet array has been created");
/*     */   }
/*     */ 
/*     */   public void createSignatureImage(PDDocument template, InputStream inputStream) throws IOException
/*     */   {
/* 163 */     PDJpeg img = new PDJpeg(template, inputStream);
/* 164 */     this.pdfStructure.setJpedImage(img);
/* 165 */     logger.info("Visible Signature Image has been created");
/*     */ 
/* 167 */     inputStream.close();
/*     */   }
/*     */ 
/*     */   public void createFormaterRectangle(byte[] params)
/*     */   {
/* 174 */     PDRectangle formrect = new PDRectangle();
/* 175 */     formrect.setUpperRightX(params[0]);
/* 176 */     formrect.setUpperRightY(params[1]);
/* 177 */     formrect.setLowerLeftX(params[2]);
/* 178 */     formrect.setLowerLeftY(params[3]);
/*     */ 
/* 180 */     this.pdfStructure.setFormaterRectangle(formrect);
/* 181 */     logger.info("Formater rectangle has been created");
/*     */   }
/*     */ 
/*     */   public void createHolderFormStream(PDDocument template)
/*     */   {
/* 187 */     PDStream holderForm = new PDStream(template);
/* 188 */     this.pdfStructure.setHolderFormStream(holderForm);
/* 189 */     logger.info("Holder form Stream has been created");
/*     */   }
/*     */ 
/*     */   public void createHolderFormResources()
/*     */   {
/* 194 */     PDResources holderFormResources = new PDResources();
/* 195 */     this.pdfStructure.setHolderFormResources(holderFormResources);
/* 196 */     logger.info("Holder form resources have been created");
/*     */   }
/*     */ 
/*     */   public void createHolderForm(PDResources holderFormResources, PDStream holderFormStream, PDRectangle formrect)
/*     */   {
/* 203 */     PDXObjectForm holderForm = new PDXObjectForm(holderFormStream);
/* 204 */     holderForm.setResources(holderFormResources);
/* 205 */     holderForm.setBBox(formrect);
/* 206 */     holderForm.setFormType(1);
/* 207 */     this.pdfStructure.setHolderForm(holderForm);
/* 208 */     logger.info("Holder form has been created");
/*     */   }
/*     */ 
/*     */   public void createAppearanceDictionary(PDXObjectForm holderForml, PDSignatureField signatureField)
/*     */     throws IOException
/*     */   {
/* 216 */     PDAppearanceDictionary appearance = new PDAppearanceDictionary();
/* 217 */     appearance.getCOSObject().setDirect(true);
/*     */ 
/* 219 */     PDAppearanceStream appearanceStream = new PDAppearanceStream(holderForml.getCOSStream());
/*     */ 
/* 221 */     appearance.setNormalAppearance(appearanceStream);
/* 222 */     signatureField.getWidget().setAppearance(appearance);
/*     */ 
/* 224 */     this.pdfStructure.setAppearanceDictionary(appearance);
/* 225 */     logger.info("PDF appereance Dictionary has been created");
/*     */   }
/*     */ 
/*     */   public void createInnerFormStream(PDDocument template)
/*     */   {
/* 231 */     PDStream innterFormStream = new PDStream(template);
/* 232 */     this.pdfStructure.setInnterFormStream(innterFormStream);
/* 233 */     logger.info("Strean of another form (inner form - it would be inside holder form) has been created");
/*     */   }
/*     */ 
/*     */   public void createInnerFormResource()
/*     */   {
/* 238 */     PDResources innerFormResources = new PDResources();
/* 239 */     this.pdfStructure.setInnerFormResources(innerFormResources);
/* 240 */     logger.info("Resources of another form (inner form - it would be inside holder form) have been created");
/*     */   }
/*     */ 
/*     */   public void createInnerForm(PDResources innerFormResources, PDStream innerFormStream, PDRectangle formrect)
/*     */   {
/* 245 */     PDXObjectForm innerForm = new PDXObjectForm(innerFormStream);
/* 246 */     innerForm.setResources(innerFormResources);
/* 247 */     innerForm.setBBox(formrect);
/* 248 */     innerForm.setFormType(1);
/* 249 */     this.pdfStructure.setInnerForm(innerForm);
/* 250 */     logger.info("Another form (inner form - it would be inside holder form) have been created");
/*     */   }
/*     */ 
/*     */   public void insertInnerFormToHolerResources(PDXObjectForm innerForm, PDResources holderFormResources)
/*     */   {
/* 256 */     String name = holderFormResources.addXObject(innerForm, "FRM");
/* 257 */     this.pdfStructure.setInnerFormName(name);
/* 258 */     logger.info("Alerady inserted inner form  inside holder form");
/*     */   }
/*     */ 
/*     */   public void createImageFormStream(PDDocument template)
/*     */   {
/* 263 */     PDStream imageFormStream = new PDStream(template);
/* 264 */     this.pdfStructure.setImageFormStream(imageFormStream);
/* 265 */     logger.info("Created image form Stream");
/*     */   }
/*     */ 
/*     */   public void createImageFormResources()
/*     */   {
/* 271 */     PDResources imageFormResources = new PDResources();
/* 272 */     this.pdfStructure.setImageFormResources(imageFormResources);
/* 273 */     logger.info("Created image form Resources");
/*     */   }
/*     */ 
/*     */   public void createImageForm(PDResources imageFormResources, PDResources innerFormResource, PDStream imageFormStream, PDRectangle formrect, AffineTransform affineTransform, PDJpeg img)
/*     */     throws IOException
/*     */   {
/* 289 */     PDXObjectForm imageForm = new PDXObjectForm(imageFormStream);
/* 290 */     imageForm.setBBox(formrect);
/* 291 */     imageForm.setMatrix(affineTransform);
/* 292 */     imageForm.setResources(imageFormResources);
/* 293 */     imageForm.setFormType(1);
/*     */ 
/* 299 */     imageFormResources.getCOSObject().setDirect(true);
/* 300 */     String imageFormName = innerFormResource.addXObject(imageForm, "n");
/* 301 */     String imageName = imageFormResources.addXObject(img, "img");
/* 302 */     this.pdfStructure.setImageForm(imageForm);
/* 303 */     this.pdfStructure.setImageFormName(imageFormName);
/* 304 */     this.pdfStructure.setImageName(imageName);
/* 305 */     logger.info("Created image form");
/*     */   }
/*     */ 
/*     */   public void injectProcSetArray(PDXObjectForm innerForm, PDPage page, PDResources innerFormResources, PDResources imageFormResources, PDResources holderFormResources, COSArray procSet)
/*     */   {
/* 312 */     innerForm.getResources().getCOSDictionary().setItem(COSName.PROC_SET, procSet);
/* 313 */     page.getCOSDictionary().setItem(COSName.PROC_SET, procSet);
/* 314 */     innerFormResources.getCOSDictionary().setItem(COSName.PROC_SET, procSet);
/* 315 */     imageFormResources.getCOSDictionary().setItem(COSName.PROC_SET, procSet);
/* 316 */     holderFormResources.getCOSDictionary().setItem(COSName.PROC_SET, procSet);
/* 317 */     logger.info("inserted ProcSet to PDF");
/*     */   }
/*     */ 
/*     */   public void injectAppearanceStreams(PDStream holderFormStream, PDStream innterFormStream, PDStream imageFormStream, String imageObjectName, String imageName, String innerFormName, PDVisibleSignDesigner properties)
/*     */     throws IOException
/*     */   {
/* 329 */     String imgFormComment = "q 100 0 0 50 0 0 cm /" + imageName + " Do Q\n";
/* 330 */     String holderFormComment = "q 1 0 0 1 0 0 cm /" + innerFormName + " Do Q \n";
/* 331 */     String innerFormComment = "q 1 0 0 1 0 0 cm /" + imageObjectName + " Do Q\n";
/*     */ 
/* 333 */     appendRawCommands(this.pdfStructure.getHolderFormStream().createOutputStream(), holderFormComment);
/* 334 */     appendRawCommands(this.pdfStructure.getInnterFormStream().createOutputStream(), innerFormComment);
/* 335 */     appendRawCommands(this.pdfStructure.getImageFormStream().createOutputStream(), imgFormComment);
/* 336 */     logger.info("Injected apereance stream to pdf");
/*     */   }
/*     */ 
/*     */   public void appendRawCommands(OutputStream os, String commands)
/*     */     throws IOException
/*     */   {
/* 342 */     os.write(commands.getBytes("UTF-8"));
/* 343 */     os.close();
/*     */   }
/*     */ 
/*     */   public void createVisualSignature(PDDocument template)
/*     */   {
/* 348 */     this.pdfStructure.setVisualSignature(template.getDocument());
/* 349 */     logger.info("Visible signature has been created");
/*     */   }
/*     */ 
/*     */   public void createWidgetDictionary(PDSignatureField signatureField, PDResources holderFormResources)
/*     */     throws IOException
/*     */   {
/* 357 */     COSDictionary widgetDict = signatureField.getWidget().getDictionary();
/* 358 */     widgetDict.setNeedToBeUpdate(true);
/* 359 */     widgetDict.setItem(COSName.DR, holderFormResources.getCOSObject());
/*     */ 
/* 361 */     this.pdfStructure.setWidgetDictionary(widgetDict);
/* 362 */     logger.info("WidgetDictionary has been crated");
/*     */   }
/*     */ 
/*     */   public void closeTemplate(PDDocument template) throws IOException
/*     */   {
/* 367 */     template.close();
/* 368 */     this.pdfStructure.getTemplate().close();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.digitalsignature.visible.PDVisibleSigBuilder
 * JD-Core Version:    0.6.2
 */