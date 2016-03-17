/*     */ package org.apache.pdfbox.pdmodel.interactive.digitalsignature.visible;
/*     */ 
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.exceptions.COSVisitorException;
/*     */ import org.apache.pdfbox.pdfwriter.COSWriter;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.PDPage;
/*     */ import org.apache.pdfbox.pdmodel.PDResources;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
/*     */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectForm;
/*     */ import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceDictionary;
/*     */ import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
/*     */ import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
/*     */ import org.apache.pdfbox.pdmodel.interactive.form.PDField;
/*     */ import org.apache.pdfbox.pdmodel.interactive.form.PDSignatureField;
/*     */ 
/*     */ public class PDFTemplateStructure
/*     */ {
/*     */   private PDPage page;
/*     */   private PDDocument template;
/*     */   private PDAcroForm acroForm;
/*     */   private PDSignatureField signatureField;
/*     */   private PDSignature pdSignature;
/*     */   private COSDictionary acroFormDictionary;
/*     */   private PDRectangle singatureRectangle;
/*     */   private AffineTransform affineTransform;
/*     */   private COSArray procSet;
/*     */   private PDJpeg jpedImage;
/*     */   private PDRectangle formaterRectangle;
/*     */   private PDStream holderFormStream;
/*     */   private PDResources holderFormResources;
/*     */   private PDXObjectForm holderForm;
/*     */   private PDAppearanceDictionary appearanceDictionary;
/*     */   private PDStream innterFormStream;
/*     */   private PDResources innerFormResources;
/*     */   private PDXObjectForm innerForm;
/*     */   private PDStream imageFormStream;
/*     */   private PDResources imageFormResources;
/*     */   private List<PDField> acroFormFields;
/*     */   private String innerFormName;
/*     */   private String imageFormName;
/*     */   private String imageName;
/*     */   private COSDocument visualSignature;
/*     */   private PDXObjectForm imageForm;
/*     */   private COSDictionary widgetDictionary;
/*     */ 
/*     */   public PDPage getPage()
/*     */   {
/*  86 */     return this.page;
/*     */   }
/*     */ 
/*     */   public void setPage(PDPage page)
/*     */   {
/*  95 */     this.page = page;
/*     */   }
/*     */ 
/*     */   public PDDocument getTemplate()
/*     */   {
/* 106 */     return this.template;
/*     */   }
/*     */ 
/*     */   public void setTemplate(PDDocument template)
/*     */   {
/* 117 */     this.template = template;
/*     */   }
/*     */ 
/*     */   public PDAcroForm getAcroForm()
/*     */   {
/* 126 */     return this.acroForm;
/*     */   }
/*     */ 
/*     */   public void setAcroForm(PDAcroForm acroForm)
/*     */   {
/* 135 */     this.acroForm = acroForm;
/*     */   }
/*     */ 
/*     */   public PDSignatureField getSignatureField()
/*     */   {
/* 144 */     return this.signatureField;
/*     */   }
/*     */ 
/*     */   public void setSignatureField(PDSignatureField signatureField)
/*     */   {
/* 153 */     this.signatureField = signatureField;
/*     */   }
/*     */ 
/*     */   public PDSignature getPdSignature()
/*     */   {
/* 162 */     return this.pdSignature;
/*     */   }
/*     */ 
/*     */   public void setPdSignature(PDSignature pdSignature)
/*     */   {
/* 171 */     this.pdSignature = pdSignature;
/*     */   }
/*     */ 
/*     */   public COSDictionary getAcroFormDictionary()
/*     */   {
/* 181 */     return this.acroFormDictionary;
/*     */   }
/*     */ 
/*     */   public void setAcroFormDictionary(COSDictionary acroFormDictionary)
/*     */   {
/* 192 */     this.acroFormDictionary = acroFormDictionary;
/*     */   }
/*     */ 
/*     */   public PDRectangle getSingatureRectangle()
/*     */   {
/* 201 */     return this.singatureRectangle;
/*     */   }
/*     */ 
/*     */   public void setSignatureRectangle(PDRectangle singatureRectangle)
/*     */   {
/* 210 */     this.singatureRectangle = singatureRectangle;
/*     */   }
/*     */ 
/*     */   public AffineTransform getAffineTransform()
/*     */   {
/* 219 */     return this.affineTransform;
/*     */   }
/*     */ 
/*     */   public void setAffineTransform(AffineTransform affineTransform)
/*     */   {
/* 228 */     this.affineTransform = affineTransform;
/*     */   }
/*     */ 
/*     */   public COSArray getProcSet()
/*     */   {
/* 237 */     return this.procSet;
/*     */   }
/*     */ 
/*     */   public void setProcSet(COSArray procSet)
/*     */   {
/* 246 */     this.procSet = procSet;
/*     */   }
/*     */ 
/*     */   public PDJpeg getJpedImage()
/*     */   {
/* 255 */     return this.jpedImage;
/*     */   }
/*     */ 
/*     */   public void setJpedImage(PDJpeg jpedImage)
/*     */   {
/* 264 */     this.jpedImage = jpedImage;
/*     */   }
/*     */ 
/*     */   public PDRectangle getFormaterRectangle()
/*     */   {
/* 273 */     return this.formaterRectangle;
/*     */   }
/*     */ 
/*     */   public void setFormaterRectangle(PDRectangle formaterRectangle)
/*     */   {
/* 282 */     this.formaterRectangle = formaterRectangle;
/*     */   }
/*     */ 
/*     */   public PDStream getHolderFormStream()
/*     */   {
/* 291 */     return this.holderFormStream;
/*     */   }
/*     */ 
/*     */   public void setHolderFormStream(PDStream holderFormStream)
/*     */   {
/* 300 */     this.holderFormStream = holderFormStream;
/*     */   }
/*     */ 
/*     */   public PDXObjectForm getHolderForm()
/*     */   {
/* 312 */     return this.holderForm;
/*     */   }
/*     */ 
/*     */   public void setHolderForm(PDXObjectForm holderForm)
/*     */   {
/* 321 */     this.holderForm = holderForm;
/*     */   }
/*     */ 
/*     */   public PDResources getHolderFormResources()
/*     */   {
/* 330 */     return this.holderFormResources;
/*     */   }
/*     */ 
/*     */   public void setHolderFormResources(PDResources holderFormResources)
/*     */   {
/* 339 */     this.holderFormResources = holderFormResources;
/*     */   }
/*     */ 
/*     */   public PDAppearanceDictionary getAppearanceDictionary()
/*     */   {
/* 349 */     return this.appearanceDictionary;
/*     */   }
/*     */ 
/*     */   public void setAppearanceDictionary(PDAppearanceDictionary appearanceDictionary)
/*     */   {
/* 359 */     this.appearanceDictionary = appearanceDictionary;
/*     */   }
/*     */ 
/*     */   public PDStream getInnterFormStream()
/*     */   {
/* 368 */     return this.innterFormStream;
/*     */   }
/*     */ 
/*     */   public void setInnterFormStream(PDStream innterFormStream)
/*     */   {
/* 377 */     this.innterFormStream = innterFormStream;
/*     */   }
/*     */ 
/*     */   public PDResources getInnerFormResources()
/*     */   {
/* 386 */     return this.innerFormResources;
/*     */   }
/*     */ 
/*     */   public void setInnerFormResources(PDResources innerFormResources)
/*     */   {
/* 395 */     this.innerFormResources = innerFormResources;
/*     */   }
/*     */ 
/*     */   public PDXObjectForm getInnerForm()
/*     */   {
/* 407 */     return this.innerForm;
/*     */   }
/*     */ 
/*     */   public void setInnerForm(PDXObjectForm innerForm)
/*     */   {
/* 417 */     this.innerForm = innerForm;
/*     */   }
/*     */ 
/*     */   public String getInnerFormName()
/*     */   {
/* 426 */     return this.innerFormName;
/*     */   }
/*     */ 
/*     */   public void setInnerFormName(String innerFormName)
/*     */   {
/* 435 */     this.innerFormName = innerFormName;
/*     */   }
/*     */ 
/*     */   public PDStream getImageFormStream()
/*     */   {
/* 444 */     return this.imageFormStream;
/*     */   }
/*     */ 
/*     */   public void setImageFormStream(PDStream imageFormStream)
/*     */   {
/* 453 */     this.imageFormStream = imageFormStream;
/*     */   }
/*     */ 
/*     */   public PDResources getImageFormResources()
/*     */   {
/* 462 */     return this.imageFormResources;
/*     */   }
/*     */ 
/*     */   public void setImageFormResources(PDResources imageFormResources)
/*     */   {
/* 471 */     this.imageFormResources = imageFormResources;
/*     */   }
/*     */ 
/*     */   public PDXObjectForm getImageForm()
/*     */   {
/* 482 */     return this.imageForm;
/*     */   }
/*     */ 
/*     */   public void setImageForm(PDXObjectForm imageForm)
/*     */   {
/* 496 */     this.imageForm = imageForm;
/*     */   }
/*     */ 
/*     */   public String getImageFormName()
/*     */   {
/* 505 */     return this.imageFormName;
/*     */   }
/*     */ 
/*     */   public void setImageFormName(String imageFormName)
/*     */   {
/* 514 */     this.imageFormName = imageFormName;
/*     */   }
/*     */ 
/*     */   public String getImageName()
/*     */   {
/* 523 */     return this.imageName;
/*     */   }
/*     */ 
/*     */   public void setImageName(String imageName)
/*     */   {
/* 532 */     this.imageName = imageName;
/*     */   }
/*     */ 
/*     */   public COSDocument getVisualSignature()
/*     */   {
/* 542 */     return this.visualSignature;
/*     */   }
/*     */ 
/*     */   public void setVisualSignature(COSDocument visualSignature)
/*     */   {
/* 553 */     this.visualSignature = visualSignature;
/*     */   }
/*     */ 
/*     */   public List<PDField> getAcroFormFields()
/*     */   {
/* 562 */     return this.acroFormFields;
/*     */   }
/*     */ 
/*     */   public void setAcroFormFields(List<PDField> acroFormFields)
/*     */   {
/* 571 */     this.acroFormFields = acroFormFields;
/*     */   }
/*     */ 
/*     */   public ByteArrayInputStream getTemplateAppearanceStream()
/*     */     throws IOException, COSVisitorException
/*     */   {
/* 582 */     COSDocument visualSignature = getVisualSignature();
/* 583 */     ByteArrayOutputStream memoryOut = new ByteArrayOutputStream();
/* 584 */     COSWriter memoryWriter = new COSWriter(memoryOut);
/* 585 */     memoryWriter.write(visualSignature);
/*     */ 
/* 587 */     ByteArrayInputStream input = new ByteArrayInputStream(memoryOut.toByteArray());
/*     */ 
/* 589 */     getTemplate().close();
/*     */ 
/* 591 */     return input;
/*     */   }
/*     */ 
/*     */   public COSDictionary getWidgetDictionary()
/*     */   {
/* 602 */     return this.widgetDictionary;
/*     */   }
/*     */ 
/*     */   public void setWidgetDictionary(COSDictionary widgetDictionary)
/*     */   {
/* 613 */     this.widgetDictionary = widgetDictionary;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.digitalsignature.visible.PDFTemplateStructure
 * JD-Core Version:    0.6.2
 */