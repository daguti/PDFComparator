/*      */ package com.itextpdf.text.pdf;
/*      */ 
/*      */ import com.itextpdf.text.Chunk;
/*      */ import com.itextpdf.text.DocumentException;
/*      */ import com.itextpdf.text.Font;
/*      */ import com.itextpdf.text.Image;
/*      */ import com.itextpdf.text.Paragraph;
/*      */ import com.itextpdf.text.Phrase;
/*      */ import com.itextpdf.text.Rectangle;
/*      */ import com.itextpdf.text.Version;
/*      */ import com.itextpdf.text.error_messages.MessageLocalization;
/*      */ import com.itextpdf.text.io.RASInputStream;
/*      */ import com.itextpdf.text.io.RandomAccessSource;
/*      */ import com.itextpdf.text.io.RandomAccessSourceFactory;
/*      */ import com.itextpdf.text.pdf.security.CertificateInfo;
/*      */ import com.itextpdf.text.pdf.security.CertificateInfo.X500Name;
/*      */ import java.io.EOFException;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.RandomAccessFile;
/*      */ import java.security.cert.Certificate;
/*      */ import java.security.cert.X509Certificate;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Arrays;
/*      */ import java.util.Calendar;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ 
/*      */ public class PdfSignatureAppearance
/*      */ {
/*      */   public static final int NOT_CERTIFIED = 0;
/*      */   public static final int CERTIFIED_NO_CHANGES_ALLOWED = 1;
/*      */   public static final int CERTIFIED_FORM_FILLING = 2;
/*      */   public static final int CERTIFIED_FORM_FILLING_AND_ANNOTATIONS = 3;
/*  115 */   private int certificationLevel = 0;
/*      */ 
/*  137 */   private String reasonCaption = "Reason: ";
/*      */ 
/*  140 */   private String locationCaption = "Location: ";
/*      */   private String reason;
/*      */   private String location;
/*      */   private Calendar signDate;
/*      */   private String signatureCreator;
/*      */   private String contact;
/*      */   private RandomAccessFile raf;
/*      */   private byte[] bout;
/*      */   private long[] range;
/*      */   private Certificate signCertificate;
/*      */   private PdfDictionary cryptoDictionary;
/*      */   private SignatureEvent signatureEvent;
/*      */   private String fieldName;
/*  410 */   private int page = 1;
/*      */   private Rectangle rect;
/*      */   private Rectangle pageRect;
/*  557 */   private RenderingMode renderingMode = RenderingMode.DESCRIPTION;
/*      */ 
/*  578 */   private Image signatureGraphic = null;
/*      */ 
/*  599 */   private boolean acro6Layers = true;
/*      */ 
/*  620 */   private PdfTemplate[] app = new PdfTemplate[5];
/*      */ 
/*  644 */   private boolean reuseAppearance = false;
/*      */   public static final String questionMark = "% DSUnknown\nq\n1 G\n1 g\n0.1 0 0 0.1 9 0 cm\n0 J 0 j 4 M []0 d\n1 i \n0 g\n313 292 m\n313 404 325 453 432 529 c\n478 561 504 597 504 645 c\n504 736 440 760 391 760 c\n286 760 271 681 265 626 c\n265 625 l\n100 625 l\n100 828 253 898 381 898 c\n451 898 679 878 679 650 c\n679 555 628 499 538 435 c\n488 399 467 376 467 292 c\n313 292 l\nh\n308 214 170 -164 re\nf\n0.44 G\n1.2 w\n1 1 0.4 rg\n287 318 m\n287 430 299 479 406 555 c\n451 587 478 623 478 671 c\n478 762 414 786 365 786 c\n260 786 245 707 239 652 c\n239 651 l\n74 651 l\n74 854 227 924 355 924 c\n425 924 653 904 653 676 c\n653 581 602 525 512 461 c\n462 425 441 402 441 318 c\n287 318 l\nh\n282 240 170 -164 re\nB\nQ\n";
/*      */   private Image image;
/*      */   private float imageScale;
/*      */   private String layer2Text;
/*      */   private Font layer2Font;
/*  783 */   private int runDirection = 1;
/*      */   private String layer4Text;
/*      */   private PdfTemplate frm;
/*      */   private static final float TOP_SECTION = 0.3F;
/*      */   private static final float MARGIN = 2.0F;
/*      */   private PdfStamper stamper;
/*      */   private PdfStamperImp writer;
/*      */   private ByteBuffer sigout;
/*      */   private OutputStream originalout;
/*      */   private File tempFile;
/*      */   private HashMap<PdfName, PdfLiteral> exclusionLocations;
/*      */   private int boutLen;
/* 1216 */   private boolean preClosed = false;
/*      */ 
/*      */   PdfSignatureAppearance(PdfStamperImp writer)
/*      */   {
/*   90 */     this.writer = writer;
/*   91 */     this.signDate = new GregorianCalendar();
/*   92 */     this.fieldName = getNewSigName();
/*   93 */     this.signatureCreator = Version.getInstance().getVersion();
/*      */   }
/*      */ 
/*      */   public void setCertificationLevel(int certificationLevel)
/*      */   {
/*  123 */     this.certificationLevel = certificationLevel;
/*      */   }
/*      */ 
/*      */   public int getCertificationLevel()
/*      */   {
/*  131 */     return this.certificationLevel;
/*      */   }
/*      */ 
/*      */   public String getReason()
/*      */   {
/*  156 */     return this.reason;
/*      */   }
/*      */ 
/*      */   public void setReason(String reason)
/*      */   {
/*  164 */     this.reason = reason;
/*      */   }
/*      */ 
/*      */   public void setReasonCaption(String reasonCaption)
/*      */   {
/*  172 */     this.reasonCaption = reasonCaption;
/*      */   }
/*      */ 
/*      */   public String getLocation()
/*      */   {
/*  180 */     return this.location;
/*      */   }
/*      */ 
/*      */   public void setLocation(String location)
/*      */   {
/*  188 */     this.location = location;
/*      */   }
/*      */ 
/*      */   public void setLocationCaption(String locationCaption)
/*      */   {
/*  196 */     this.locationCaption = locationCaption;
/*      */   }
/*      */ 
/*      */   public String getSignatureCreator()
/*      */   {
/*  207 */     return this.signatureCreator;
/*      */   }
/*      */ 
/*      */   public void setSignatureCreator(String signatureCreator)
/*      */   {
/*  215 */     this.signatureCreator = signatureCreator;
/*      */   }
/*      */ 
/*      */   public String getContact()
/*      */   {
/*  226 */     return this.contact;
/*      */   }
/*      */ 
/*      */   public void setContact(String contact)
/*      */   {
/*  234 */     this.contact = contact;
/*      */   }
/*      */ 
/*      */   public Calendar getSignDate()
/*      */   {
/*  242 */     return this.signDate;
/*      */   }
/*      */ 
/*      */   public void setSignDate(Calendar signDate)
/*      */   {
/*  250 */     this.signDate = signDate;
/*      */   }
/*      */ 
/*      */   public InputStream getRangeStream()
/*      */     throws IOException
/*      */   {
/*  269 */     RandomAccessSourceFactory fac = new RandomAccessSourceFactory();
/*  270 */     return new RASInputStream(fac.createRanged(getUnderlyingSource(), this.range));
/*      */   }
/*      */ 
/*      */   private RandomAccessSource getUnderlyingSource()
/*      */     throws IOException
/*      */   {
/*  279 */     RandomAccessSourceFactory fac = new RandomAccessSourceFactory();
/*  280 */     return this.raf == null ? fac.createSource(this.bout) : fac.createSource(this.raf);
/*      */   }
/*      */ 
/*      */   public void addDeveloperExtension(PdfDeveloperExtension de)
/*      */   {
/*  292 */     this.writer.addDeveloperExtension(de);
/*      */   }
/*      */ 
/*      */   public PdfDictionary getCryptoDictionary()
/*      */   {
/*  305 */     return this.cryptoDictionary;
/*      */   }
/*      */ 
/*      */   public void setCryptoDictionary(PdfDictionary cryptoDictionary)
/*      */   {
/*  313 */     this.cryptoDictionary = cryptoDictionary;
/*      */   }
/*      */ 
/*      */   public void setCertificate(Certificate signCertificate)
/*      */   {
/*  322 */     this.signCertificate = signCertificate;
/*      */   }
/*      */ 
/*      */   public Certificate getCertificate() {
/*  326 */     return this.signCertificate;
/*      */   }
/*      */ 
/*      */   public SignatureEvent getSignatureEvent()
/*      */   {
/*  352 */     return this.signatureEvent;
/*      */   }
/*      */ 
/*      */   public void setSignatureEvent(SignatureEvent signatureEvent)
/*      */   {
/*  360 */     this.signatureEvent = signatureEvent;
/*      */   }
/*      */ 
/*      */   public String getFieldName()
/*      */   {
/*  375 */     return this.fieldName;
/*      */   }
/*      */ 
/*      */   public String getNewSigName()
/*      */   {
/*  384 */     AcroFields af = this.writer.getAcroFields();
/*  385 */     String name = "Signature";
/*  386 */     int step = 0;
/*  387 */     boolean found = false;
/*      */     String n1;
/*  388 */     while (!found) {
/*  389 */       step++;
/*  390 */       n1 = name + step;
/*  391 */       if (af.getFieldItem(n1) == null)
/*      */       {
/*  393 */         n1 = n1 + ".";
/*  394 */         found = true;
/*  395 */         for (Object element : af.getFields().keySet()) {
/*  396 */           String fn = (String)element;
/*  397 */           if (fn.startsWith(n1)) {
/*  398 */             found = false;
/*  399 */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  403 */     name = name + step;
/*  404 */     return name;
/*      */   }
/*      */ 
/*      */   public int getPage()
/*      */   {
/*  417 */     return this.page;
/*      */   }
/*      */ 
/*      */   public Rectangle getRect()
/*      */   {
/*  432 */     return this.rect;
/*      */   }
/*      */ 
/*      */   public Rectangle getPageRect()
/*      */   {
/*  443 */     return this.pageRect;
/*      */   }
/*      */ 
/*      */   public boolean isInvisible()
/*      */   {
/*  451 */     return (this.rect == null) || (this.rect.getWidth() == 0.0F) || (this.rect.getHeight() == 0.0F);
/*      */   }
/*      */ 
/*      */   public void setVisibleSignature(Rectangle pageRect, int page, String fieldName)
/*      */   {
/*  461 */     if (fieldName != null) {
/*  462 */       if (fieldName.indexOf('.') >= 0)
/*  463 */         throw new IllegalArgumentException(MessageLocalization.getComposedMessage("field.names.cannot.contain.a.dot", new Object[0]));
/*  464 */       AcroFields af = this.writer.getAcroFields();
/*  465 */       AcroFields.Item item = af.getFieldItem(fieldName);
/*  466 */       if (item != null)
/*  467 */         throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.field.1.already.exists", new Object[] { fieldName }));
/*  468 */       this.fieldName = fieldName;
/*      */     }
/*  470 */     if ((page < 1) || (page > this.writer.reader.getNumberOfPages()))
/*  471 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("invalid.page.number.1", page));
/*  472 */     this.pageRect = new Rectangle(pageRect);
/*  473 */     this.pageRect.normalize();
/*  474 */     this.rect = new Rectangle(this.pageRect.getWidth(), this.pageRect.getHeight());
/*  475 */     this.page = page;
/*      */   }
/*      */ 
/*      */   public void setVisibleSignature(String fieldName)
/*      */   {
/*  483 */     AcroFields af = this.writer.getAcroFields();
/*  484 */     AcroFields.Item item = af.getFieldItem(fieldName);
/*  485 */     if (item == null)
/*  486 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.field.1.does.not.exist", new Object[] { fieldName }));
/*  487 */     PdfDictionary merged = item.getMerged(0);
/*  488 */     if (!PdfName.SIG.equals(PdfReader.getPdfObject(merged.get(PdfName.FT))))
/*  489 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.field.1.is.not.a.signature.field", new Object[] { fieldName }));
/*  490 */     this.fieldName = fieldName;
/*  491 */     PdfArray r = merged.getAsArray(PdfName.RECT);
/*  492 */     float llx = r.getAsNumber(0).floatValue();
/*  493 */     float lly = r.getAsNumber(1).floatValue();
/*  494 */     float urx = r.getAsNumber(2).floatValue();
/*  495 */     float ury = r.getAsNumber(3).floatValue();
/*  496 */     this.pageRect = new Rectangle(llx, lly, urx, ury);
/*  497 */     this.pageRect.normalize();
/*  498 */     this.page = item.getPage(0).intValue();
/*  499 */     int rotation = this.writer.reader.getPageRotation(this.page);
/*  500 */     Rectangle pageSize = this.writer.reader.getPageSizeWithRotation(this.page);
/*  501 */     switch (rotation) {
/*      */     case 90:
/*  503 */       this.pageRect = new Rectangle(this.pageRect.getBottom(), pageSize.getTop() - this.pageRect.getLeft(), this.pageRect.getTop(), pageSize.getTop() - this.pageRect.getRight());
/*      */ 
/*  508 */       break;
/*      */     case 180:
/*  510 */       this.pageRect = new Rectangle(pageSize.getRight() - this.pageRect.getLeft(), pageSize.getTop() - this.pageRect.getBottom(), pageSize.getRight() - this.pageRect.getRight(), pageSize.getTop() - this.pageRect.getTop());
/*      */ 
/*  515 */       break;
/*      */     case 270:
/*  517 */       this.pageRect = new Rectangle(pageSize.getRight() - this.pageRect.getBottom(), this.pageRect.getLeft(), pageSize.getRight() - this.pageRect.getTop(), this.pageRect.getRight());
/*      */     }
/*      */ 
/*  524 */     if (rotation != 0)
/*  525 */       this.pageRect.normalize();
/*  526 */     this.rect = new Rectangle(this.pageRect.getWidth(), this.pageRect.getHeight());
/*      */   }
/*      */ 
/*      */   public RenderingMode getRenderingMode()
/*      */   {
/*  565 */     return this.renderingMode;
/*      */   }
/*      */ 
/*      */   public void setRenderingMode(RenderingMode renderingMode)
/*      */   {
/*  574 */     this.renderingMode = renderingMode;
/*      */   }
/*      */ 
/*      */   public Image getSignatureGraphic()
/*      */   {
/*  585 */     return this.signatureGraphic;
/*      */   }
/*      */ 
/*      */   public void setSignatureGraphic(Image signatureGraphic)
/*      */   {
/*  595 */     this.signatureGraphic = signatureGraphic;
/*      */   }
/*      */ 
/*      */   public boolean isAcro6Layers()
/*      */   {
/*  606 */     return this.acro6Layers;
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public void setAcro6Layers(boolean acro6Layers)
/*      */   {
/*  616 */     this.acro6Layers = acro6Layers;
/*      */   }
/*      */ 
/*      */   public PdfTemplate getLayer(int layer)
/*      */   {
/*  632 */     if ((layer < 0) || (layer >= this.app.length))
/*  633 */       return null;
/*  634 */     PdfTemplate t = this.app[layer];
/*  635 */     if (t == null) {
/*  636 */       t = this.app[layer] =  = new PdfTemplate(this.writer);
/*  637 */       t.setBoundingBox(this.rect);
/*  638 */       this.writer.addDirectTemplateSimple(t, new PdfName("n" + layer));
/*      */     }
/*  640 */     return t;
/*      */   }
/*      */ 
/*      */   public void setReuseAppearance(boolean reuseAppearance)
/*      */   {
/*  650 */     this.reuseAppearance = reuseAppearance;
/*      */   }
/*      */ 
/*      */   public Image getImage()
/*      */   {
/*  710 */     return this.image;
/*      */   }
/*      */ 
/*      */   public void setImage(Image image)
/*      */   {
/*  718 */     this.image = image;
/*      */   }
/*      */ 
/*      */   public float getImageScale()
/*      */   {
/*  729 */     return this.imageScale;
/*      */   }
/*      */ 
/*      */   public void setImageScale(float imageScale)
/*      */   {
/*  740 */     this.imageScale = imageScale;
/*      */   }
/*      */ 
/*      */   public void setLayer2Text(String text)
/*      */   {
/*  752 */     this.layer2Text = text;
/*      */   }
/*      */ 
/*      */   public String getLayer2Text()
/*      */   {
/*  760 */     return this.layer2Text;
/*      */   }
/*      */ 
/*      */   public Font getLayer2Font()
/*      */   {
/*  771 */     return this.layer2Font;
/*      */   }
/*      */ 
/*      */   public void setLayer2Font(Font layer2Font)
/*      */   {
/*  779 */     this.layer2Font = layer2Font;
/*      */   }
/*      */ 
/*      */   public void setRunDirection(int runDirection)
/*      */   {
/*  789 */     if ((runDirection < 0) || (runDirection > 3))
/*  790 */       throw new RuntimeException(MessageLocalization.getComposedMessage("invalid.run.direction.1", runDirection));
/*  791 */     this.runDirection = runDirection;
/*      */   }
/*      */ 
/*      */   public int getRunDirection()
/*      */   {
/*  798 */     return this.runDirection;
/*      */   }
/*      */ 
/*      */   public void setLayer4Text(String text)
/*      */   {
/*  812 */     this.layer4Text = text;
/*      */   }
/*      */ 
/*      */   public String getLayer4Text()
/*      */   {
/*  820 */     return this.layer4Text;
/*      */   }
/*      */ 
/*      */   public PdfTemplate getTopLayer()
/*      */   {
/*  836 */     if (this.frm == null) {
/*  837 */       this.frm = new PdfTemplate(this.writer);
/*  838 */       this.frm.setBoundingBox(this.rect);
/*  839 */       this.writer.addDirectTemplateSimple(this.frm, new PdfName("FRM"));
/*      */     }
/*  841 */     return this.frm;
/*      */   }
/*      */ 
/*      */   public PdfTemplate getAppearance()
/*      */     throws DocumentException
/*      */   {
/*  861 */     if (isInvisible()) {
/*  862 */       PdfTemplate t = new PdfTemplate(this.writer);
/*  863 */       t.setBoundingBox(new Rectangle(0.0F, 0.0F));
/*  864 */       this.writer.addDirectTemplateSimple(t, null);
/*  865 */       return t;
/*      */     }
/*      */ 
/*  868 */     if ((this.app[0] == null) && (!this.reuseAppearance)) {
/*  869 */       createBlankN0();
/*      */     }
/*  871 */     if ((this.app[1] == null) && (!this.acro6Layers)) {
/*  872 */       PdfTemplate t = this.app[1] =  = new PdfTemplate(this.writer);
/*  873 */       t.setBoundingBox(new Rectangle(100.0F, 100.0F));
/*  874 */       this.writer.addDirectTemplateSimple(t, new PdfName("n1"));
/*  875 */       t.setLiteral("% DSUnknown\nq\n1 G\n1 g\n0.1 0 0 0.1 9 0 cm\n0 J 0 j 4 M []0 d\n1 i \n0 g\n313 292 m\n313 404 325 453 432 529 c\n478 561 504 597 504 645 c\n504 736 440 760 391 760 c\n286 760 271 681 265 626 c\n265 625 l\n100 625 l\n100 828 253 898 381 898 c\n451 898 679 878 679 650 c\n679 555 628 499 538 435 c\n488 399 467 376 467 292 c\n313 292 l\nh\n308 214 170 -164 re\nf\n0.44 G\n1.2 w\n1 1 0.4 rg\n287 318 m\n287 430 299 479 406 555 c\n451 587 478 623 478 671 c\n478 762 414 786 365 786 c\n260 786 245 707 239 652 c\n239 651 l\n74 651 l\n74 854 227 924 355 924 c\n425 924 653 904 653 676 c\n653 581 602 525 512 461 c\n462 425 441 402 441 318 c\n287 318 l\nh\n282 240 170 -164 re\nB\nQ\n");
/*      */     }
/*  877 */     if (this.app[2] == null)
/*      */     {
/*      */       String text;
/*      */       String text;
/*  879 */       if (this.layer2Text == null) {
/*  880 */         StringBuilder buf = new StringBuilder();
/*  881 */         buf.append("Digitally signed by ");
/*  882 */         String name = null;
/*  883 */         CertificateInfo.X500Name x500name = CertificateInfo.getSubjectFields((X509Certificate)this.signCertificate);
/*  884 */         if (x500name != null) {
/*  885 */           name = x500name.getField("CN");
/*  886 */           if (name == null)
/*  887 */             name = x500name.getField("E");
/*      */         }
/*  889 */         if (name == null)
/*  890 */           name = "";
/*  891 */         buf.append(name).append('\n');
/*  892 */         SimpleDateFormat sd = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss z");
/*  893 */         buf.append("Date: ").append(sd.format(this.signDate.getTime()));
/*  894 */         if (this.reason != null)
/*  895 */           buf.append('\n').append(this.reasonCaption).append(this.reason);
/*  896 */         if (this.location != null)
/*  897 */           buf.append('\n').append(this.locationCaption).append(this.location);
/*  898 */         text = buf.toString();
/*      */       }
/*      */       else {
/*  901 */         text = this.layer2Text;
/*  902 */       }PdfTemplate t = this.app[2] =  = new PdfTemplate(this.writer);
/*  903 */       t.setBoundingBox(this.rect);
/*  904 */       this.writer.addDirectTemplateSimple(t, new PdfName("n2"));
/*  905 */       if (this.image != null)
/*  906 */         if (this.imageScale == 0.0F) {
/*  907 */           t.addImage(this.image, this.rect.getWidth(), 0.0F, 0.0F, this.rect.getHeight(), 0.0F, 0.0F);
/*      */         }
/*      */         else {
/*  910 */           float usableScale = this.imageScale;
/*  911 */           if (this.imageScale < 0.0F)
/*  912 */             usableScale = Math.min(this.rect.getWidth() / this.image.getWidth(), this.rect.getHeight() / this.image.getHeight());
/*  913 */           float w = this.image.getWidth() * usableScale;
/*  914 */           float h = this.image.getHeight() * usableScale;
/*  915 */           float x = (this.rect.getWidth() - w) / 2.0F;
/*  916 */           float y = (this.rect.getHeight() - h) / 2.0F;
/*  917 */           t.addImage(this.image, w, 0.0F, 0.0F, h, x, y);
/*      */         }
/*      */       Font font;
/*      */       Font font;
/*  921 */       if (this.layer2Font == null)
/*  922 */         font = new Font();
/*      */       else
/*  924 */         font = new Font(this.layer2Font);
/*  925 */       float size = font.getSize();
/*      */ 
/*  927 */       Rectangle dataRect = null;
/*  928 */       Rectangle signatureRect = null;
/*      */ 
/*  930 */       if ((this.renderingMode == RenderingMode.NAME_AND_DESCRIPTION) || ((this.renderingMode == RenderingMode.GRAPHIC_AND_DESCRIPTION) && (this.signatureGraphic != null)))
/*      */       {
/*  933 */         signatureRect = new Rectangle(2.0F, 2.0F, this.rect.getWidth() / 2.0F - 2.0F, this.rect.getHeight() - 2.0F);
/*      */ 
/*  938 */         dataRect = new Rectangle(this.rect.getWidth() / 2.0F + 1.0F, 2.0F, this.rect.getWidth() - 1.0F, this.rect.getHeight() - 2.0F);
/*      */ 
/*  944 */         if (this.rect.getHeight() > this.rect.getWidth()) {
/*  945 */           signatureRect = new Rectangle(2.0F, this.rect.getHeight() / 2.0F, this.rect.getWidth() - 2.0F, this.rect.getHeight());
/*      */ 
/*  950 */           dataRect = new Rectangle(2.0F, 2.0F, this.rect.getWidth() - 2.0F, this.rect.getHeight() / 2.0F - 2.0F);
/*      */         }
/*      */ 
/*      */       }
/*  957 */       else if (this.renderingMode == RenderingMode.GRAPHIC) {
/*  958 */         if (this.signatureGraphic == null) {
/*  959 */           throw new IllegalStateException(MessageLocalization.getComposedMessage("a.signature.image.should.be.present.when.rendering.mode.is.graphic.only", new Object[0]));
/*      */         }
/*  961 */         signatureRect = new Rectangle(2.0F, 2.0F, this.rect.getWidth() - 2.0F, this.rect.getHeight() - 2.0F);
/*      */       }
/*      */       else
/*      */       {
/*  968 */         dataRect = new Rectangle(2.0F, 2.0F, this.rect.getWidth() - 2.0F, this.rect.getHeight() * 0.7F - 2.0F);
/*      */       }
/*      */       ColumnText ct2;
/*      */       Image im;
/*      */       Paragraph p;
/*      */       float x;
/*      */       float y;
/*  975 */       switch (1.$SwitchMap$com$itextpdf$text$pdf$PdfSignatureAppearance$RenderingMode[this.renderingMode.ordinal()]) {
/*      */       case 1:
/*  977 */         String signedBy = CertificateInfo.getSubjectFields((X509Certificate)this.signCertificate).getField("CN");
/*  978 */         if (signedBy == null)
/*  979 */           signedBy = CertificateInfo.getSubjectFields((X509Certificate)this.signCertificate).getField("E");
/*  980 */         if (signedBy == null)
/*  981 */           signedBy = "";
/*  982 */         Rectangle sr2 = new Rectangle(signatureRect.getWidth() - 2.0F, signatureRect.getHeight() - 2.0F);
/*  983 */         float signedSize = ColumnText.fitText(font, signedBy, sr2, -1.0F, this.runDirection);
/*      */ 
/*  985 */         ct2 = new ColumnText(t);
/*  986 */         ct2.setRunDirection(this.runDirection);
/*  987 */         ct2.setSimpleColumn(new Phrase(signedBy, font), signatureRect.getLeft(), signatureRect.getBottom(), signatureRect.getRight(), signatureRect.getTop(), signedSize, 0);
/*      */ 
/*  989 */         ct2.go();
/*  990 */         break;
/*      */       case 2:
/*  992 */         if (this.signatureGraphic == null) {
/*  993 */           throw new IllegalStateException(MessageLocalization.getComposedMessage("a.signature.image.should.be.present.when.rendering.mode.is.graphic.and.description", new Object[0]));
/*      */         }
/*  995 */         ct2 = new ColumnText(t);
/*  996 */         ct2.setRunDirection(this.runDirection);
/*  997 */         ct2.setSimpleColumn(signatureRect.getLeft(), signatureRect.getBottom(), signatureRect.getRight(), signatureRect.getTop(), 0.0F, 2);
/*      */ 
/*  999 */         im = Image.getInstance(this.signatureGraphic);
/* 1000 */         im.scaleToFit(signatureRect.getWidth(), signatureRect.getHeight());
/*      */ 
/* 1002 */         p = new Paragraph();
/*      */ 
/* 1004 */         x = 0.0F;
/*      */ 
/* 1007 */         y = -im.getScaledHeight() + 15.0F;
/*      */ 
/* 1009 */         x += (signatureRect.getWidth() - im.getScaledWidth()) / 2.0F;
/* 1010 */         y -= (signatureRect.getHeight() - im.getScaledHeight()) / 2.0F;
/* 1011 */         p.add(new Chunk(im, x + (signatureRect.getWidth() - im.getScaledWidth()) / 2.0F, y, false));
/* 1012 */         ct2.addElement(p);
/* 1013 */         ct2.go();
/* 1014 */         break;
/*      */       case 3:
/* 1016 */         ct2 = new ColumnText(t);
/* 1017 */         ct2.setRunDirection(this.runDirection);
/* 1018 */         ct2.setSimpleColumn(signatureRect.getLeft(), signatureRect.getBottom(), signatureRect.getRight(), signatureRect.getTop(), 0.0F, 2);
/*      */ 
/* 1020 */         im = Image.getInstance(this.signatureGraphic);
/* 1021 */         im.scaleToFit(signatureRect.getWidth(), signatureRect.getHeight());
/*      */ 
/* 1023 */         p = new Paragraph(signatureRect.getHeight());
/*      */ 
/* 1025 */         x = (signatureRect.getWidth() - im.getScaledWidth()) / 2.0F;
/* 1026 */         y = (signatureRect.getHeight() - im.getScaledHeight()) / 2.0F;
/* 1027 */         p.add(new Chunk(im, x, y, false));
/* 1028 */         ct2.addElement(p);
/* 1029 */         ct2.go();
/* 1030 */         break;
/*      */       }
/*      */ 
/* 1034 */       if (this.renderingMode != RenderingMode.GRAPHIC) {
/* 1035 */         if (size <= 0.0F) {
/* 1036 */           Rectangle sr = new Rectangle(dataRect.getWidth(), dataRect.getHeight());
/* 1037 */           size = ColumnText.fitText(font, text, sr, 12.0F, this.runDirection);
/*      */         }
/* 1039 */         ColumnText ct = new ColumnText(t);
/* 1040 */         ct.setRunDirection(this.runDirection);
/* 1041 */         ct.setSimpleColumn(new Phrase(text, font), dataRect.getLeft(), dataRect.getBottom(), dataRect.getRight(), dataRect.getTop(), size, 0);
/* 1042 */         ct.go();
/*      */       }
/*      */     }
/* 1045 */     if ((this.app[3] == null) && (!this.acro6Layers)) {
/* 1046 */       PdfTemplate t = this.app[3] =  = new PdfTemplate(this.writer);
/* 1047 */       t.setBoundingBox(new Rectangle(100.0F, 100.0F));
/* 1048 */       this.writer.addDirectTemplateSimple(t, new PdfName("n3"));
/* 1049 */       t.setLiteral("% DSBlank\n");
/*      */     }
/* 1051 */     if ((this.app[4] == null) && (!this.acro6Layers)) {
/* 1052 */       PdfTemplate t = this.app[4] =  = new PdfTemplate(this.writer);
/* 1053 */       t.setBoundingBox(new Rectangle(0.0F, this.rect.getHeight() * 0.7F, this.rect.getRight(), this.rect.getTop()));
/* 1054 */       this.writer.addDirectTemplateSimple(t, new PdfName("n4"));
/*      */       Font font;
/*      */       Font font;
/* 1056 */       if (this.layer2Font == null)
/* 1057 */         font = new Font();
/*      */       else {
/* 1059 */         font = new Font(this.layer2Font);
/*      */       }
/* 1061 */       String text = "Signature Not Verified";
/* 1062 */       if (this.layer4Text != null)
/* 1063 */         text = this.layer4Text;
/* 1064 */       Rectangle sr = new Rectangle(this.rect.getWidth() - 4.0F, this.rect.getHeight() * 0.3F - 4.0F);
/* 1065 */       float size = ColumnText.fitText(font, text, sr, 15.0F, this.runDirection);
/* 1066 */       ColumnText ct = new ColumnText(t);
/* 1067 */       ct.setRunDirection(this.runDirection);
/* 1068 */       ct.setSimpleColumn(new Phrase(text, font), 2.0F, 0.0F, this.rect.getWidth() - 2.0F, this.rect.getHeight() - 2.0F, size, 0);
/* 1069 */       ct.go();
/*      */     }
/* 1071 */     int rotation = this.writer.reader.getPageRotation(this.page);
/* 1072 */     Rectangle rotated = new Rectangle(this.rect);
/* 1073 */     int n = rotation;
/* 1074 */     while (n > 0) {
/* 1075 */       rotated = rotated.rotate();
/* 1076 */       n -= 90;
/*      */     }
/* 1078 */     if (this.frm == null) {
/* 1079 */       this.frm = new PdfTemplate(this.writer);
/* 1080 */       this.frm.setBoundingBox(rotated);
/* 1081 */       this.writer.addDirectTemplateSimple(this.frm, new PdfName("FRM"));
/* 1082 */       float scale = Math.min(this.rect.getWidth(), this.rect.getHeight()) * 0.9F;
/* 1083 */       float x = (this.rect.getWidth() - scale) / 2.0F;
/* 1084 */       float y = (this.rect.getHeight() - scale) / 2.0F;
/* 1085 */       scale /= 100.0F;
/* 1086 */       if (rotation == 90)
/* 1087 */         this.frm.concatCTM(0.0F, 1.0F, -1.0F, 0.0F, this.rect.getHeight(), 0.0F);
/* 1088 */       else if (rotation == 180)
/* 1089 */         this.frm.concatCTM(-1.0F, 0.0F, 0.0F, -1.0F, this.rect.getWidth(), this.rect.getHeight());
/* 1090 */       else if (rotation == 270)
/* 1091 */         this.frm.concatCTM(0.0F, -1.0F, 1.0F, 0.0F, 0.0F, this.rect.getWidth());
/* 1092 */       if (this.reuseAppearance) {
/* 1093 */         AcroFields af = this.writer.getAcroFields();
/* 1094 */         PdfIndirectReference ref = af.getNormalAppearance(getFieldName());
/* 1095 */         if (ref != null) {
/* 1096 */           this.frm.addTemplateReference(ref, new PdfName("n0"), 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F);
/*      */         }
/*      */         else {
/* 1099 */           this.reuseAppearance = false;
/* 1100 */           if (this.app[0] == null) {
/* 1101 */             createBlankN0();
/*      */           }
/*      */         }
/*      */       }
/* 1105 */       if (!this.reuseAppearance) {
/* 1106 */         this.frm.addTemplate(this.app[0], 0.0F, 0.0F);
/*      */       }
/* 1108 */       if (!this.acro6Layers)
/* 1109 */         this.frm.addTemplate(this.app[1], scale, 0.0F, 0.0F, scale, x, y);
/* 1110 */       this.frm.addTemplate(this.app[2], 0.0F, 0.0F);
/* 1111 */       if (!this.acro6Layers) {
/* 1112 */         this.frm.addTemplate(this.app[3], scale, 0.0F, 0.0F, scale, x, y);
/* 1113 */         this.frm.addTemplate(this.app[4], 0.0F, 0.0F);
/*      */       }
/*      */     }
/* 1116 */     PdfTemplate napp = new PdfTemplate(this.writer);
/* 1117 */     napp.setBoundingBox(rotated);
/* 1118 */     this.writer.addDirectTemplateSimple(napp, null);
/* 1119 */     napp.addTemplate(this.frm, 0.0F, 0.0F);
/* 1120 */     return napp;
/*      */   }
/*      */ 
/*      */   private void createBlankN0() {
/* 1124 */     PdfTemplate t = this.app[0] =  = new PdfTemplate(this.writer);
/* 1125 */     t.setBoundingBox(new Rectangle(100.0F, 100.0F));
/* 1126 */     this.writer.addDirectTemplateSimple(t, new PdfName("n0"));
/* 1127 */     t.setLiteral("% DSBlank\n");
/*      */   }
/*      */ 
/*      */   public PdfStamper getStamper()
/*      */   {
/* 1142 */     return this.stamper;
/*      */   }
/*      */ 
/*      */   void setStamper(PdfStamper stamper)
/*      */   {
/* 1150 */     this.stamper = stamper;
/*      */   }
/*      */ 
/*      */   ByteBuffer getSigout()
/*      */   {
/* 1163 */     return this.sigout;
/*      */   }
/*      */ 
/*      */   void setSigout(ByteBuffer sigout)
/*      */   {
/* 1170 */     this.sigout = sigout;
/*      */   }
/*      */ 
/*      */   OutputStream getOriginalout()
/*      */   {
/* 1180 */     return this.originalout;
/*      */   }
/*      */ 
/*      */   void setOriginalout(OutputStream originalout)
/*      */   {
/* 1187 */     this.originalout = originalout;
/*      */   }
/*      */ 
/*      */   public File getTempFile()
/*      */   {
/* 1198 */     return this.tempFile;
/*      */   }
/*      */ 
/*      */   void setTempFile(File tempFile)
/*      */   {
/* 1206 */     this.tempFile = tempFile;
/*      */   }
/*      */ 
/*      */   public boolean isPreClosed()
/*      */   {
/* 1224 */     return this.preClosed;
/*      */   }
/*      */ 
/*      */   public void preClose(HashMap<PdfName, Integer> exclusionSizes)
/*      */     throws IOException, DocumentException
/*      */   {
/* 1244 */     if (this.preClosed)
/* 1245 */       throw new DocumentException(MessageLocalization.getComposedMessage("document.already.pre.closed", new Object[0]));
/* 1246 */     this.stamper.mergeVerification();
/* 1247 */     this.preClosed = true;
/* 1248 */     AcroFields af = this.writer.getAcroFields();
/* 1249 */     String name = getFieldName();
/* 1250 */     boolean fieldExists = af.doesSignatureFieldExist(name);
/* 1251 */     PdfIndirectReference refSig = this.writer.getPdfIndirectReference();
/* 1252 */     this.writer.setSigFlags(3);
/* 1253 */     PdfDictionary fieldLock = null;
/* 1254 */     if (fieldExists) {
/* 1255 */       PdfDictionary widget = af.getFieldItem(name).getWidget(0);
/* 1256 */       this.writer.markUsed(widget);
/* 1257 */       fieldLock = widget.getAsDict(PdfName.LOCK);
/* 1258 */       widget.put(PdfName.P, this.writer.getPageReference(getPage()));
/* 1259 */       widget.put(PdfName.V, refSig);
/* 1260 */       PdfObject obj = PdfReader.getPdfObjectRelease(widget.get(PdfName.F));
/* 1261 */       int flags = 0;
/* 1262 */       if ((obj != null) && (obj.isNumber()))
/* 1263 */         flags = ((PdfNumber)obj).intValue();
/* 1264 */       flags |= 128;
/* 1265 */       widget.put(PdfName.F, new PdfNumber(flags));
/* 1266 */       PdfDictionary ap = new PdfDictionary();
/* 1267 */       ap.put(PdfName.N, getAppearance().getIndirectReference());
/* 1268 */       widget.put(PdfName.AP, ap);
/*      */     }
/*      */     else {
/* 1271 */       PdfFormField sigField = PdfFormField.createSignature(this.writer);
/* 1272 */       sigField.setFieldName(name);
/* 1273 */       sigField.put(PdfName.V, refSig);
/* 1274 */       sigField.setFlags(132);
/*      */ 
/* 1276 */       int pagen = getPage();
/* 1277 */       if (!isInvisible())
/* 1278 */         sigField.setWidget(getPageRect(), null);
/*      */       else
/* 1280 */         sigField.setWidget(new Rectangle(0.0F, 0.0F), null);
/* 1281 */       sigField.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, getAppearance());
/* 1282 */       sigField.setPage(pagen);
/* 1283 */       this.writer.addAnnotation(sigField, pagen);
/*      */     }
/*      */ 
/* 1286 */     this.exclusionLocations = new HashMap();
/* 1287 */     if (this.cryptoDictionary == null) {
/* 1288 */       throw new DocumentException("No crypto dictionary defined.");
/*      */     }
/*      */ 
/* 1291 */     PdfLiteral lit = new PdfLiteral(80);
/* 1292 */     this.exclusionLocations.put(PdfName.BYTERANGE, lit);
/* 1293 */     this.cryptoDictionary.put(PdfName.BYTERANGE, lit);
/* 1294 */     for (Map.Entry entry : exclusionSizes.entrySet()) {
/* 1295 */       PdfName key = (PdfName)entry.getKey();
/* 1296 */       Integer v = (Integer)entry.getValue();
/* 1297 */       lit = new PdfLiteral(v.intValue());
/* 1298 */       this.exclusionLocations.put(key, lit);
/* 1299 */       this.cryptoDictionary.put(key, lit);
/*      */     }
/* 1301 */     if (this.certificationLevel > 0)
/* 1302 */       addDocMDP(this.cryptoDictionary);
/* 1303 */     if (fieldLock != null)
/* 1304 */       addFieldMDP(this.cryptoDictionary, fieldLock);
/* 1305 */     if (this.signatureEvent != null)
/* 1306 */       this.signatureEvent.getSignatureDictionary(this.cryptoDictionary);
/* 1307 */     this.writer.addToBody(this.cryptoDictionary, refSig, false);
/*      */ 
/* 1309 */     if (this.certificationLevel > 0)
/*      */     {
/* 1311 */       PdfDictionary docmdp = new PdfDictionary();
/* 1312 */       docmdp.put(new PdfName("DocMDP"), refSig);
/* 1313 */       this.writer.reader.getCatalog().put(new PdfName("Perms"), docmdp);
/*      */     }
/* 1315 */     this.writer.close(this.stamper.getMoreInfo());
/*      */ 
/* 1317 */     this.range = new long[this.exclusionLocations.size() * 2];
/* 1318 */     long byteRangePosition = ((PdfLiteral)this.exclusionLocations.get(PdfName.BYTERANGE)).getPosition();
/* 1319 */     this.exclusionLocations.remove(PdfName.BYTERANGE);
/* 1320 */     int idx = 1;
/* 1321 */     for (PdfLiteral lit : this.exclusionLocations.values()) {
/* 1322 */       long n = lit.getPosition();
/* 1323 */       this.range[(idx++)] = n;
/* 1324 */       this.range[(idx++)] = (lit.getPosLength() + n);
/*      */     }
/* 1326 */     Arrays.sort(this.range, 1, this.range.length - 1);
/* 1327 */     for (int k = 3; k < this.range.length - 2; k += 2) {
/* 1328 */       this.range[k] -= this.range[(k - 1)];
/*      */     }
/* 1330 */     if (this.tempFile == null) {
/* 1331 */       this.bout = this.sigout.getBuffer();
/* 1332 */       this.boutLen = this.sigout.size();
/* 1333 */       this.range[(this.range.length - 1)] = (this.boutLen - this.range[(this.range.length - 2)]);
/* 1334 */       ByteBuffer bf = new ByteBuffer();
/* 1335 */       bf.append('[');
/* 1336 */       for (int k = 0; k < this.range.length; k++)
/* 1337 */         bf.append(this.range[k]).append(' ');
/* 1338 */       bf.append(']');
/* 1339 */       System.arraycopy(bf.getBuffer(), 0, this.bout, (int)byteRangePosition, bf.size());
/*      */     }
/*      */     else {
/*      */       try {
/* 1343 */         this.raf = new RandomAccessFile(this.tempFile, "rw");
/* 1344 */         long len = this.raf.length();
/* 1345 */         this.range[(this.range.length - 1)] = (len - this.range[(this.range.length - 2)]);
/* 1346 */         ByteBuffer bf = new ByteBuffer();
/* 1347 */         bf.append('[');
/* 1348 */         for (int k = 0; k < this.range.length; k++)
/* 1349 */           bf.append(this.range[k]).append(' ');
/* 1350 */         bf.append(']');
/* 1351 */         this.raf.seek(byteRangePosition);
/* 1352 */         this.raf.write(bf.getBuffer(), 0, bf.size());
/*      */       } catch (IOException e) {
/*      */         try {
/* 1355 */           this.raf.close(); } catch (Exception ee) {
/*      */         }try { this.tempFile.delete(); } catch (Exception ee) {
/* 1357 */         }throw e;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void addDocMDP(PdfDictionary crypto)
/*      */   {
/* 1369 */     PdfDictionary reference = new PdfDictionary();
/* 1370 */     PdfDictionary transformParams = new PdfDictionary();
/* 1371 */     transformParams.put(PdfName.P, new PdfNumber(this.certificationLevel));
/* 1372 */     transformParams.put(PdfName.V, new PdfName("1.2"));
/* 1373 */     transformParams.put(PdfName.TYPE, PdfName.TRANSFORMPARAMS);
/* 1374 */     reference.put(PdfName.TRANSFORMMETHOD, PdfName.DOCMDP);
/* 1375 */     reference.put(PdfName.TYPE, PdfName.SIGREF);
/* 1376 */     reference.put(PdfName.TRANSFORMPARAMS, transformParams);
/* 1377 */     reference.put(new PdfName("DigestValue"), new PdfString("aa"));
/* 1378 */     PdfArray loc = new PdfArray();
/* 1379 */     loc.add(new PdfNumber(0));
/* 1380 */     loc.add(new PdfNumber(0));
/* 1381 */     reference.put(new PdfName("DigestLocation"), loc);
/* 1382 */     reference.put(new PdfName("DigestMethod"), new PdfName("MD5"));
/* 1383 */     reference.put(PdfName.DATA, this.writer.reader.getTrailer().get(PdfName.ROOT));
/* 1384 */     PdfArray types = new PdfArray();
/* 1385 */     types.add(reference);
/* 1386 */     crypto.put(PdfName.REFERENCE, types);
/*      */   }
/*      */ 
/*      */   private void addFieldMDP(PdfDictionary crypto, PdfDictionary fieldLock)
/*      */   {
/* 1396 */     PdfDictionary reference = new PdfDictionary();
/* 1397 */     PdfDictionary transformParams = new PdfDictionary();
/* 1398 */     transformParams.putAll(fieldLock);
/* 1399 */     transformParams.put(PdfName.TYPE, PdfName.TRANSFORMPARAMS);
/* 1400 */     transformParams.put(PdfName.V, new PdfName("1.2"));
/* 1401 */     reference.put(PdfName.TRANSFORMMETHOD, PdfName.FIELDMDP);
/* 1402 */     reference.put(PdfName.TYPE, PdfName.SIGREF);
/* 1403 */     reference.put(PdfName.TRANSFORMPARAMS, transformParams);
/* 1404 */     reference.put(new PdfName("DigestValue"), new PdfString("aa"));
/* 1405 */     PdfArray loc = new PdfArray();
/* 1406 */     loc.add(new PdfNumber(0));
/* 1407 */     loc.add(new PdfNumber(0));
/* 1408 */     reference.put(new PdfName("DigestLocation"), loc);
/* 1409 */     reference.put(new PdfName("DigestMethod"), new PdfName("MD5"));
/* 1410 */     reference.put(PdfName.DATA, this.writer.reader.getTrailer().get(PdfName.ROOT));
/* 1411 */     PdfArray types = crypto.getAsArray(PdfName.REFERENCE);
/* 1412 */     if (types == null)
/* 1413 */       types = new PdfArray();
/* 1414 */     types.add(reference);
/* 1415 */     crypto.put(PdfName.REFERENCE, types);
/*      */   }
/*      */ 
/*      */   public void close(PdfDictionary update)
/*      */     throws IOException, DocumentException
/*      */   {
/*      */     try
/*      */     {
/* 1431 */       if (!this.preClosed)
/* 1432 */         throw new DocumentException(MessageLocalization.getComposedMessage("preclose.must.be.called.first", new Object[0]));
/* 1433 */       ByteBuffer bf = new ByteBuffer();
/* 1434 */       for (PdfName key : update.getKeys()) {
/* 1435 */         PdfObject obj = update.get(key);
/* 1436 */         PdfLiteral lit = (PdfLiteral)this.exclusionLocations.get(key);
/* 1437 */         if (lit == null)
/* 1438 */           throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.key.1.didn.t.reserve.space.in.preclose", new Object[] { key.toString() }));
/* 1439 */         bf.reset();
/* 1440 */         obj.toPdf(null, bf);
/* 1441 */         if (bf.size() > lit.getPosLength())
/* 1442 */           throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.key.1.is.too.big.is.2.reserved.3", new Object[] { key.toString(), String.valueOf(bf.size()), String.valueOf(lit.getPosLength()) }));
/* 1443 */         if (this.tempFile == null) {
/* 1444 */           System.arraycopy(bf.getBuffer(), 0, this.bout, (int)lit.getPosition(), bf.size());
/*      */         } else {
/* 1446 */           this.raf.seek(lit.getPosition());
/* 1447 */           this.raf.write(bf.getBuffer(), 0, bf.size());
/*      */         }
/*      */       }
/* 1450 */       if (update.size() != this.exclusionLocations.size())
/* 1451 */         throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.update.dictionary.has.less.keys.than.required", new Object[0]));
/* 1452 */       if (this.tempFile == null) {
/* 1453 */         this.originalout.write(this.bout, 0, this.boutLen);
/*      */       }
/* 1456 */       else if (this.originalout != null) {
/* 1457 */         this.raf.seek(0L);
/* 1458 */         long length = this.raf.length();
/* 1459 */         byte[] buf = new byte[8192];
/* 1460 */         while (length > 0L) {
/* 1461 */           int r = this.raf.read(buf, 0, (int)Math.min(buf.length, length));
/* 1462 */           if (r < 0)
/* 1463 */             throw new EOFException(MessageLocalization.getComposedMessage("unexpected.eof", new Object[0]));
/* 1464 */           this.originalout.write(buf, 0, r);
/* 1465 */           length -= r;
/*      */         }
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/* 1471 */       this.writer.reader.close();
/* 1472 */       if (this.tempFile != null) {
/*      */         try { this.raf.close(); } catch (Exception ee) {
/* 1474 */         }if (this.originalout != null) try {
/* 1475 */             this.tempFile.delete(); } catch (Exception ee) {  }
/*      */  
/*      */       }
/* 1477 */       if (this.originalout != null) try {
/* 1478 */           this.originalout.close();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*      */         }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static enum RenderingMode
/*      */   {
/*  541 */     DESCRIPTION, 
/*      */ 
/*  545 */     NAME_AND_DESCRIPTION, 
/*      */ 
/*  549 */     GRAPHIC_AND_DESCRIPTION, 
/*      */ 
/*  553 */     GRAPHIC;
/*      */   }
/*      */ 
/*      */   public static abstract interface SignatureEvent
/*      */   {
/*      */     public abstract void getSignatureDictionary(PdfDictionary paramPdfDictionary);
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfSignatureAppearance
 * JD-Core Version:    0.6.2
 */