/*      */ package com.itextpdf.text;
/*      */ 
/*      */ import com.itextpdf.awt.PdfGraphics2D;
/*      */ import com.itextpdf.text.api.Indentable;
/*      */ import com.itextpdf.text.api.Spaceable;
/*      */ import com.itextpdf.text.error_messages.MessageLocalization;
/*      */ import com.itextpdf.text.io.RandomAccessSourceFactory;
/*      */ import com.itextpdf.text.pdf.ICC_Profile;
/*      */ import com.itextpdf.text.pdf.PRIndirectReference;
/*      */ import com.itextpdf.text.pdf.PdfArray;
/*      */ import com.itextpdf.text.pdf.PdfContentByte;
/*      */ import com.itextpdf.text.pdf.PdfDictionary;
/*      */ import com.itextpdf.text.pdf.PdfIndirectReference;
/*      */ import com.itextpdf.text.pdf.PdfName;
/*      */ import com.itextpdf.text.pdf.PdfNumber;
/*      */ import com.itextpdf.text.pdf.PdfOCG;
/*      */ import com.itextpdf.text.pdf.PdfObject;
/*      */ import com.itextpdf.text.pdf.PdfReader;
/*      */ import com.itextpdf.text.pdf.PdfString;
/*      */ import com.itextpdf.text.pdf.PdfTemplate;
/*      */ import com.itextpdf.text.pdf.PdfWriter;
/*      */ import com.itextpdf.text.pdf.RandomAccessFileOrArray;
/*      */ import com.itextpdf.text.pdf.codec.BmpImage;
/*      */ import com.itextpdf.text.pdf.codec.CCITTG4Encoder;
/*      */ import com.itextpdf.text.pdf.codec.GifImage;
/*      */ import com.itextpdf.text.pdf.codec.JBIG2Image;
/*      */ import com.itextpdf.text.pdf.codec.PngImage;
/*      */ import com.itextpdf.text.pdf.codec.TiffImage;
/*      */ import com.itextpdf.text.pdf.interfaces.IAccessibleElement;
/*      */ import com.itextpdf.text.pdf.interfaces.IAlternateDescription;
/*      */ import java.awt.Color;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.ColorModel;
/*      */ import java.awt.image.PixelGrabber;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.URL;
/*      */ import java.util.HashMap;
/*      */ 
/*      */ public abstract class Image extends Rectangle
/*      */   implements Indentable, Spaceable, IAccessibleElement, IAlternateDescription
/*      */ {
/*      */   public static final int DEFAULT = 0;
/*      */   public static final int RIGHT = 2;
/*      */   public static final int LEFT = 0;
/*      */   public static final int MIDDLE = 1;
/*      */   public static final int TEXTWRAP = 4;
/*      */   public static final int UNDERLYING = 8;
/*      */   public static final int AX = 0;
/*      */   public static final int AY = 1;
/*      */   public static final int BX = 2;
/*      */   public static final int BY = 3;
/*      */   public static final int CX = 4;
/*      */   public static final int CY = 5;
/*      */   public static final int DX = 6;
/*      */   public static final int DY = 7;
/*      */   public static final int ORIGINAL_NONE = 0;
/*      */   public static final int ORIGINAL_JPEG = 1;
/*      */   public static final int ORIGINAL_PNG = 2;
/*      */   public static final int ORIGINAL_GIF = 3;
/*      */   public static final int ORIGINAL_BMP = 4;
/*      */   public static final int ORIGINAL_TIFF = 5;
/*      */   public static final int ORIGINAL_WMF = 6;
/*      */   public static final int ORIGINAL_PS = 7;
/*      */   public static final int ORIGINAL_JPEG2000 = 8;
/*      */   public static final int ORIGINAL_JBIG2 = 9;
/*      */   protected int type;
/*      */   protected URL url;
/*      */   protected byte[] rawData;
/*  183 */   protected int bpc = 1;
/*      */ 
/*  186 */   protected PdfTemplate[] template = new PdfTemplate[1];
/*      */   protected int alignment;
/*      */   protected String alt;
/*  195 */   protected float absoluteX = (0.0F / 0.0F);
/*      */ 
/*  198 */   protected float absoluteY = (0.0F / 0.0F);
/*      */   protected float plainWidth;
/*      */   protected float plainHeight;
/*      */   protected float scaledWidth;
/*      */   protected float scaledHeight;
/*  216 */   protected int compressionLevel = -1;
/*      */ 
/*  219 */   protected Long mySerialId = getSerialId();
/*      */ 
/*  221 */   protected PdfName role = PdfName.FIGURE;
/*  222 */   protected HashMap<PdfName, PdfObject> accessibleAttributes = null;
/*  223 */   private AccessibleElementId id = null;
/*      */   private PdfIndirectReference directReference;
/* 1183 */   static long serialId = 0L;
/*      */   protected float rotationRadians;
/*      */   private float initialRotation;
/* 1272 */   protected float indentationLeft = 0.0F;
/*      */ 
/* 1275 */   protected float indentationRight = 0.0F;
/*      */   protected float spacingBefore;
/*      */   protected float spacingAfter;
/* 1364 */   private float widthPercentage = 100.0F;
/*      */   protected boolean scaleToFitLineWhenOverflow;
/* 1419 */   protected boolean scaleToFitHeight = true;
/*      */ 
/* 1442 */   protected Annotation annotation = null;
/*      */   protected PdfOCG layer;
/*      */   protected boolean interpolation;
/* 1516 */   protected int originalType = 0;
/*      */   protected byte[] originalData;
/* 1566 */   protected boolean deflated = false;
/*      */ 
/* 1591 */   protected int dpiX = 0;
/*      */ 
/* 1594 */   protected int dpiY = 0;
/*      */ 
/* 1630 */   private float XYRatio = 0.0F;
/*      */ 
/* 1654 */   protected int colorspace = -1;
/*      */ 
/* 1667 */   protected int colortransform = 1;
/*      */ 
/* 1678 */   protected boolean invert = false;
/*      */ 
/* 1700 */   protected ICC_Profile profile = null;
/*      */ 
/* 1731 */   private PdfDictionary additional = null;
/*      */ 
/* 1796 */   protected boolean mask = false;
/*      */   protected Image imageMask;
/*      */   private boolean smask;
/*      */   protected int[] transparency;
/*      */ 
/*      */   public Image(URL url)
/*      */   {
/*  235 */     super(0.0F, 0.0F);
/*  236 */     this.url = url;
/*  237 */     this.alignment = 0;
/*  238 */     this.rotationRadians = 0.0F;
/*      */   }
/*      */ 
/*      */   public static Image getInstance(URL url) throws BadElementException, MalformedURLException, IOException {
/*  242 */     return getInstance(url, false);
/*      */   }
/*      */ 
/*      */   public static Image getInstance(URL url, boolean recoverFromImageError)
/*      */     throws BadElementException, MalformedURLException, IOException
/*      */   {
/*  257 */     InputStream is = null;
/*  258 */     RandomAccessSourceFactory randomAccessSourceFactory = new RandomAccessSourceFactory();
/*      */     try
/*      */     {
/*  261 */       is = url.openStream();
/*  262 */       int c1 = is.read();
/*  263 */       int c2 = is.read();
/*  264 */       int c3 = is.read();
/*  265 */       int c4 = is.read();
/*      */ 
/*  267 */       int c5 = is.read();
/*  268 */       int c6 = is.read();
/*  269 */       int c7 = is.read();
/*  270 */       int c8 = is.read();
/*  271 */       is.close();
/*      */ 
/*  273 */       is = null;
/*      */       GifImage gif;
/*      */       Image localImage1;
/*  274 */       if ((c1 == 71) && (c2 == 73) && (c3 == 70)) {
/*  275 */         gif = new GifImage(url);
/*  276 */         Image img = gif.getImage(1);
/*  277 */         return img;
/*      */       }
/*  279 */       if ((c1 == 255) && (c2 == 216)) {
/*  280 */         return new Jpeg(url);
/*      */       }
/*  282 */       if ((c1 == 0) && (c2 == 0) && (c3 == 0) && (c4 == 12)) {
/*  283 */         return new Jpeg2000(url);
/*      */       }
/*  285 */       if ((c1 == 255) && (c2 == 79) && (c3 == 255) && (c4 == 81)) {
/*  286 */         return new Jpeg2000(url);
/*      */       }
/*  288 */       if ((c1 == PngImage.PNGID[0]) && (c2 == PngImage.PNGID[1]) && (c3 == PngImage.PNGID[2]) && (c4 == PngImage.PNGID[3]))
/*      */       {
/*  290 */         return PngImage.getImage(url);
/*      */       }
/*  292 */       if ((c1 == 215) && (c2 == 205)) {
/*  293 */         return new ImgWMF(url);
/*      */       }
/*  295 */       if ((c1 == 66) && (c2 == 77))
/*  296 */         return BmpImage.getImage(url);
/*      */       Image img;
/*  298 */       if (((c1 == 77) && (c2 == 77) && (c3 == 0) && (c4 == 42)) || ((c1 == 73) && (c2 == 73) && (c3 == 42) && (c4 == 0)))
/*      */       {
/*  300 */         RandomAccessFileOrArray ra = null;
/*      */         try {
/*  302 */           if (url.getProtocol().equals("file")) {
/*  303 */             String file = url.getFile();
/*  304 */             file = Utilities.unEscapeURL(file);
/*  305 */             ra = new RandomAccessFileOrArray(randomAccessSourceFactory.createBestSource(file));
/*      */           } else {
/*  307 */             ra = new RandomAccessFileOrArray(randomAccessSourceFactory.createSource(url));
/*  308 */           }Image img = TiffImage.getTiffImage(ra, 1);
/*  309 */           img.url = url;
/*  310 */           return img;
/*      */         } catch (RuntimeException e) {
/*  312 */           if (recoverFromImageError)
/*      */           {
/*  315 */             img = TiffImage.getTiffImage(ra, recoverFromImageError, 1);
/*  316 */             img.url = url;
/*  317 */             return img;
/*      */           }
/*  319 */           throw e;
/*      */         }
/*      */         finally
/*      */         {
/*      */         }
/*      */       }
/*      */ 
/*  326 */       if ((c1 == 151) && (c2 == 74) && (c3 == 66) && (c4 == 50) && (c5 == 13) && (c6 == 10) && (c7 == 26) && (c8 == 10))
/*      */       {
/*  328 */         RandomAccessFileOrArray ra = null;
/*      */         try {
/*  330 */           if (url.getProtocol().equals("file")) {
/*  331 */             String file = url.getFile();
/*  332 */             file = Utilities.unEscapeURL(file);
/*  333 */             ra = new RandomAccessFileOrArray(randomAccessSourceFactory.createBestSource(file));
/*      */           } else {
/*  335 */             ra = new RandomAccessFileOrArray(randomAccessSourceFactory.createSource(url));
/*  336 */           }Image img = JBIG2Image.getJbig2Image(ra, 1);
/*  337 */           img.url = url;
/*  338 */           return img;
/*      */         }
/*      */         finally
/*      */         {
/*      */         }
/*      */       }
/*  344 */       throw new IOException(MessageLocalization.getComposedMessage("unknown.image.format", new Object[] { url.toString() }));
/*      */     } finally {
/*  346 */       if (is != null)
/*  347 */         is.close();
/*      */     }
/*      */   }
/*      */ 
/*      */   public static Image getInstance(String filename)
/*      */     throws BadElementException, MalformedURLException, IOException
/*      */   {
/*  365 */     return getInstance(Utilities.toURL(filename));
/*      */   }
/*      */ 
/*      */   public static Image getInstance(String filename, boolean recoverFromImageError) throws IOException, BadElementException {
/*  369 */     return getInstance(Utilities.toURL(filename), recoverFromImageError);
/*      */   }
/*      */ 
/*      */   public static Image getInstance(byte[] imgb)
/*      */     throws BadElementException, MalformedURLException, IOException
/*      */   {
/*  375 */     return getInstance(imgb, false);
/*      */   }
/*      */ 
/*      */   public static Image getInstance(byte[] imgb, boolean recoverFromImageError)
/*      */     throws BadElementException, MalformedURLException, IOException
/*      */   {
/*  390 */     InputStream is = null;
/*  391 */     RandomAccessSourceFactory randomAccessSourceFactory = new RandomAccessSourceFactory();
/*      */     try {
/*  393 */       is = new ByteArrayInputStream(imgb);
/*  394 */       int c1 = is.read();
/*  395 */       int c2 = is.read();
/*  396 */       int c3 = is.read();
/*  397 */       int c4 = is.read();
/*  398 */       is.close();
/*      */ 
/*  400 */       is = null;
/*      */       GifImage gif;
/*  401 */       if ((c1 == 71) && (c2 == 73) && (c3 == 70)) {
/*  402 */         gif = new GifImage(imgb);
/*  403 */         return gif.getImage(1);
/*      */       }
/*  405 */       if ((c1 == 255) && (c2 == 216)) {
/*  406 */         return new Jpeg(imgb);
/*      */       }
/*  408 */       if ((c1 == 0) && (c2 == 0) && (c3 == 0) && (c4 == 12)) {
/*  409 */         return new Jpeg2000(imgb);
/*      */       }
/*  411 */       if ((c1 == 255) && (c2 == 79) && (c3 == 255) && (c4 == 81)) {
/*  412 */         return new Jpeg2000(imgb);
/*      */       }
/*  414 */       if ((c1 == PngImage.PNGID[0]) && (c2 == PngImage.PNGID[1]) && (c3 == PngImage.PNGID[2]) && (c4 == PngImage.PNGID[3]))
/*      */       {
/*  416 */         return PngImage.getImage(imgb);
/*      */       }
/*  418 */       if ((c1 == 215) && (c2 == 205)) {
/*  419 */         return new ImgWMF(imgb);
/*      */       }
/*  421 */       if ((c1 == 66) && (c2 == 77)) {
/*  422 */         return BmpImage.getImage(imgb);
/*      */       }
/*  424 */       if (((c1 == 77) && (c2 == 77) && (c3 == 0) && (c4 == 42)) || ((c1 == 73) && (c2 == 73) && (c3 == 42) && (c4 == 0)))
/*      */       {
/*  426 */         RandomAccessFileOrArray ra = null;
/*      */         try {
/*  428 */           ra = new RandomAccessFileOrArray(randomAccessSourceFactory.createSource(imgb));
/*  429 */           Image img = TiffImage.getTiffImage(ra, 1);
/*  430 */           if (img.getOriginalData() == null)
/*  431 */             img.setOriginalData(imgb);
/*  432 */           return img;
/*      */         } catch (RuntimeException e) {
/*  434 */           if (recoverFromImageError)
/*      */           {
/*  437 */             Image img = TiffImage.getTiffImage(ra, recoverFromImageError, 1);
/*  438 */             if (img.getOriginalData() == null)
/*  439 */               img.setOriginalData(imgb);
/*  440 */             return img;
/*      */           }
/*  442 */           throw e;
/*      */         }
/*      */         finally
/*      */         {
/*      */         }
/*      */       }
/*      */ 
/*  449 */       if ((c1 == 151) && (c2 == 74) && (c3 == 66) && (c4 == 50)) {
/*  450 */         is = new ByteArrayInputStream(imgb);
/*  451 */         is.skip(4L);
/*  452 */         int c5 = is.read();
/*  453 */         int c6 = is.read();
/*  454 */         int c7 = is.read();
/*  455 */         int c8 = is.read();
/*  456 */         is.close();
/*  457 */         if ((c5 == 13) && (c6 == 10) && (c7 == 26) && (c8 == 10))
/*      */         {
/*  461 */           RandomAccessFileOrArray ra = null;
/*      */           try {
/*  463 */             ra = new RandomAccessFileOrArray(randomAccessSourceFactory.createSource(imgb));
/*  464 */             Image img = JBIG2Image.getJbig2Image(ra, 1);
/*  465 */             if (img.getOriginalData() == null)
/*  466 */               img.setOriginalData(imgb);
/*  467 */             return img;
/*      */           }
/*      */           finally
/*      */           {
/*      */           }
/*      */         }
/*      */       }
/*  474 */       throw new IOException(MessageLocalization.getComposedMessage("the.byte.array.is.not.a.recognized.imageformat", new Object[0]));
/*      */     } finally {
/*  476 */       if (is != null)
/*  477 */         is.close();
/*      */     }
/*      */   }
/*      */ 
/*      */   public static Image getInstance(int width, int height, int components, int bpc, byte[] data)
/*      */     throws BadElementException
/*      */   {
/*  501 */     return getInstance(width, height, components, bpc, data, null);
/*      */   }
/*      */ 
/*      */   public static Image getInstance(int width, int height, byte[] data, byte[] globals)
/*      */   {
/*  514 */     return new ImgJBIG2(width, height, data, globals);
/*      */   }
/*      */ 
/*      */   public static Image getInstance(int width, int height, boolean reverseBits, int typeCCITT, int parameters, byte[] data)
/*      */     throws BadElementException
/*      */   {
/*  544 */     return getInstance(width, height, reverseBits, typeCCITT, parameters, data, null);
/*      */   }
/*      */ 
/*      */   public static Image getInstance(int width, int height, boolean reverseBits, int typeCCITT, int parameters, byte[] data, int[] transparency)
/*      */     throws BadElementException
/*      */   {
/*  578 */     if ((transparency != null) && (transparency.length != 2))
/*  579 */       throw new BadElementException(MessageLocalization.getComposedMessage("transparency.length.must.be.equal.to.2.with.ccitt.images", new Object[0]));
/*  580 */     Image img = new ImgCCITT(width, height, reverseBits, typeCCITT, parameters, data);
/*      */ 
/*  582 */     img.transparency = transparency;
/*  583 */     return img;
/*      */   }
/*      */ 
/*      */   public static Image getInstance(int width, int height, int components, int bpc, byte[] data, int[] transparency)
/*      */     throws BadElementException
/*      */   {
/*  609 */     if ((transparency != null) && (transparency.length != components * 2))
/*  610 */       throw new BadElementException(MessageLocalization.getComposedMessage("transparency.length.must.be.equal.to.componentes.2", new Object[0]));
/*  611 */     if ((components == 1) && (bpc == 1)) {
/*  612 */       byte[] g4 = CCITTG4Encoder.compress(data, width, height);
/*  613 */       return getInstance(width, height, false, 256, 1, g4, transparency);
/*      */     }
/*      */ 
/*  616 */     Image img = new ImgRaw(width, height, components, bpc, data);
/*  617 */     img.transparency = transparency;
/*  618 */     return img;
/*      */   }
/*      */ 
/*      */   public static Image getInstance(PdfTemplate template)
/*      */     throws BadElementException
/*      */   {
/*  633 */     return new ImgTemplate(template);
/*      */   }
/*      */ 
/*      */   public PdfIndirectReference getDirectReference()
/*      */   {
/*  650 */     return this.directReference;
/*      */   }
/*      */ 
/*      */   public void setDirectReference(PdfIndirectReference directReference)
/*      */   {
/*  658 */     this.directReference = directReference;
/*      */   }
/*      */ 
/*      */   public static Image getInstance(PRIndirectReference ref)
/*      */     throws BadElementException
/*      */   {
/*  668 */     PdfDictionary dic = (PdfDictionary)PdfReader.getPdfObjectRelease(ref);
/*  669 */     int width = ((PdfNumber)PdfReader.getPdfObjectRelease(dic.get(PdfName.WIDTH))).intValue();
/*  670 */     int height = ((PdfNumber)PdfReader.getPdfObjectRelease(dic.get(PdfName.HEIGHT))).intValue();
/*  671 */     Image imask = null;
/*  672 */     PdfObject obj = dic.get(PdfName.SMASK);
/*  673 */     if ((obj != null) && (obj.isIndirect())) {
/*  674 */       imask = getInstance((PRIndirectReference)obj);
/*      */     }
/*      */     else {
/*  677 */       obj = dic.get(PdfName.MASK);
/*  678 */       if ((obj != null) && (obj.isIndirect())) {
/*  679 */         PdfObject obj2 = PdfReader.getPdfObjectRelease(obj);
/*  680 */         if ((obj2 instanceof PdfDictionary))
/*  681 */           imask = getInstance((PRIndirectReference)obj);
/*      */       }
/*      */     }
/*  684 */     Image img = new ImgRaw(width, height, 1, 1, null);
/*  685 */     img.imageMask = imask;
/*  686 */     img.directReference = ref;
/*  687 */     return img;
/*      */   }
/*      */ 
/*      */   protected Image(Image image)
/*      */   {
/*  699 */     super(image);
/*  700 */     this.type = image.type;
/*  701 */     this.url = image.url;
/*  702 */     this.rawData = image.rawData;
/*  703 */     this.bpc = image.bpc;
/*  704 */     this.template = image.template;
/*  705 */     this.alignment = image.alignment;
/*  706 */     this.alt = image.alt;
/*  707 */     this.absoluteX = image.absoluteX;
/*  708 */     this.absoluteY = image.absoluteY;
/*  709 */     this.plainWidth = image.plainWidth;
/*  710 */     this.plainHeight = image.plainHeight;
/*  711 */     this.scaledWidth = image.scaledWidth;
/*  712 */     this.scaledHeight = image.scaledHeight;
/*  713 */     this.mySerialId = image.mySerialId;
/*      */ 
/*  715 */     this.directReference = image.directReference;
/*      */ 
/*  717 */     this.rotationRadians = image.rotationRadians;
/*  718 */     this.initialRotation = image.initialRotation;
/*  719 */     this.indentationLeft = image.indentationLeft;
/*  720 */     this.indentationRight = image.indentationRight;
/*  721 */     this.spacingBefore = image.spacingBefore;
/*  722 */     this.spacingAfter = image.spacingAfter;
/*      */ 
/*  724 */     this.widthPercentage = image.widthPercentage;
/*  725 */     this.scaleToFitLineWhenOverflow = image.scaleToFitLineWhenOverflow;
/*  726 */     this.scaleToFitHeight = image.scaleToFitHeight;
/*  727 */     this.annotation = image.annotation;
/*  728 */     this.layer = image.layer;
/*  729 */     this.interpolation = image.interpolation;
/*  730 */     this.originalType = image.originalType;
/*  731 */     this.originalData = image.originalData;
/*  732 */     this.deflated = image.deflated;
/*  733 */     this.dpiX = image.dpiX;
/*  734 */     this.dpiY = image.dpiY;
/*  735 */     this.XYRatio = image.XYRatio;
/*      */ 
/*  737 */     this.colorspace = image.colorspace;
/*  738 */     this.invert = image.invert;
/*  739 */     this.profile = image.profile;
/*  740 */     this.additional = image.additional;
/*  741 */     this.mask = image.mask;
/*  742 */     this.imageMask = image.imageMask;
/*  743 */     this.smask = image.smask;
/*  744 */     this.transparency = image.transparency;
/*  745 */     this.role = image.role;
/*  746 */     if (image.accessibleAttributes != null)
/*  747 */       this.accessibleAttributes = new HashMap(image.accessibleAttributes);
/*  748 */     setId(image.getId());
/*      */   }
/*      */ 
/*      */   public static Image getInstance(Image image)
/*      */   {
/*  759 */     if (image == null)
/*  760 */       return null;
/*      */     try {
/*  762 */       Class cs = image.getClass();
/*  763 */       Constructor constructor = cs.getDeclaredConstructor(new Class[] { Image.class });
/*      */ 
/*  765 */       return (Image)constructor.newInstance(new Object[] { image });
/*      */     } catch (Exception e) {
/*  767 */       throw new ExceptionConverter(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public int type()
/*      */   {
/*  781 */     return this.type;
/*      */   }
/*      */ 
/*      */   public boolean isNestable()
/*      */   {
/*  790 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean isJpeg()
/*      */   {
/*  803 */     return this.type == 32;
/*      */   }
/*      */ 
/*      */   public boolean isImgRaw()
/*      */   {
/*  814 */     return this.type == 34;
/*      */   }
/*      */ 
/*      */   public boolean isImgTemplate()
/*      */   {
/*  825 */     return this.type == 35;
/*      */   }
/*      */ 
/*      */   public URL getUrl()
/*      */   {
/*  838 */     return this.url;
/*      */   }
/*      */ 
/*      */   public void setUrl(URL url)
/*      */   {
/*  848 */     this.url = url;
/*      */   }
/*      */ 
/*      */   public byte[] getRawData()
/*      */   {
/*  860 */     return this.rawData;
/*      */   }
/*      */ 
/*      */   public int getBpc()
/*      */   {
/*  872 */     return this.bpc;
/*      */   }
/*      */ 
/*      */   public PdfTemplate getTemplateData()
/*      */   {
/*  884 */     return this.template[0];
/*      */   }
/*      */ 
/*      */   public void setTemplateData(PdfTemplate template)
/*      */   {
/*  894 */     this.template[0] = template;
/*      */   }
/*      */ 
/*      */   public int getAlignment()
/*      */   {
/*  903 */     return this.alignment;
/*      */   }
/*      */ 
/*      */   public void setAlignment(int alignment)
/*      */   {
/*  914 */     this.alignment = alignment;
/*      */   }
/*      */ 
/*      */   public String getAlt()
/*      */   {
/*  924 */     return this.alt;
/*      */   }
/*      */ 
/*      */   public void setAlt(String alt)
/*      */   {
/*  935 */     this.alt = alt;
/*  936 */     setAccessibleAttribute(PdfName.ALT, new PdfString(alt));
/*      */   }
/*      */ 
/*      */   public void setAbsolutePosition(float absoluteX, float absoluteY)
/*      */   {
/*  947 */     this.absoluteX = absoluteX;
/*  948 */     this.absoluteY = absoluteY;
/*      */   }
/*      */ 
/*      */   public boolean hasAbsoluteX()
/*      */   {
/*  958 */     return !Float.isNaN(this.absoluteX);
/*      */   }
/*      */ 
/*      */   public float getAbsoluteX()
/*      */   {
/*  967 */     return this.absoluteX;
/*      */   }
/*      */ 
/*      */   public boolean hasAbsoluteY()
/*      */   {
/*  977 */     return !Float.isNaN(this.absoluteY);
/*      */   }
/*      */ 
/*      */   public float getAbsoluteY()
/*      */   {
/*  986 */     return this.absoluteY;
/*      */   }
/*      */ 
/*      */   public float getScaledWidth()
/*      */   {
/*  997 */     return this.scaledWidth;
/*      */   }
/*      */ 
/*      */   public float getScaledHeight()
/*      */   {
/* 1006 */     return this.scaledHeight;
/*      */   }
/*      */ 
/*      */   public float getPlainWidth()
/*      */   {
/* 1015 */     return this.plainWidth;
/*      */   }
/*      */ 
/*      */   public float getPlainHeight()
/*      */   {
/* 1024 */     return this.plainHeight;
/*      */   }
/*      */ 
/*      */   public void scaleAbsolute(Rectangle rectangle)
/*      */   {
/* 1033 */     scaleAbsolute(rectangle.getWidth(), rectangle.getHeight());
/*      */   }
/*      */ 
/*      */   public void scaleAbsolute(float newWidth, float newHeight)
/*      */   {
/* 1045 */     this.plainWidth = newWidth;
/* 1046 */     this.plainHeight = newHeight;
/* 1047 */     float[] matrix = matrix();
/* 1048 */     this.scaledWidth = (matrix[6] - matrix[4]);
/* 1049 */     this.scaledHeight = (matrix[7] - matrix[5]);
/* 1050 */     setWidthPercentage(0.0F);
/*      */   }
/*      */ 
/*      */   public void scaleAbsoluteWidth(float newWidth)
/*      */   {
/* 1060 */     this.plainWidth = newWidth;
/* 1061 */     float[] matrix = matrix();
/* 1062 */     this.scaledWidth = (matrix[6] - matrix[4]);
/* 1063 */     this.scaledHeight = (matrix[7] - matrix[5]);
/* 1064 */     setWidthPercentage(0.0F);
/*      */   }
/*      */ 
/*      */   public void scaleAbsoluteHeight(float newHeight)
/*      */   {
/* 1074 */     this.plainHeight = newHeight;
/* 1075 */     float[] matrix = matrix();
/* 1076 */     this.scaledWidth = (matrix[6] - matrix[4]);
/* 1077 */     this.scaledHeight = (matrix[7] - matrix[5]);
/* 1078 */     setWidthPercentage(0.0F);
/*      */   }
/*      */ 
/*      */   public void scalePercent(float percent)
/*      */   {
/* 1088 */     scalePercent(percent, percent);
/*      */   }
/*      */ 
/*      */   public void scalePercent(float percentX, float percentY)
/*      */   {
/* 1100 */     this.plainWidth = (getWidth() * percentX / 100.0F);
/* 1101 */     this.plainHeight = (getHeight() * percentY / 100.0F);
/* 1102 */     float[] matrix = matrix();
/* 1103 */     this.scaledWidth = (matrix[6] - matrix[4]);
/* 1104 */     this.scaledHeight = (matrix[7] - matrix[5]);
/* 1105 */     setWidthPercentage(0.0F);
/*      */   }
/*      */ 
/*      */   public void scaleToFit(Rectangle rectangle)
/*      */   {
/* 1114 */     scaleToFit(rectangle.getWidth(), rectangle.getHeight());
/*      */   }
/*      */ 
/*      */   public void scaleToFit(float fitWidth, float fitHeight)
/*      */   {
/* 1126 */     scalePercent(100.0F);
/* 1127 */     float percentX = fitWidth * 100.0F / getScaledWidth();
/* 1128 */     float percentY = fitHeight * 100.0F / getScaledHeight();
/* 1129 */     scalePercent(percentX < percentY ? percentX : percentY);
/* 1130 */     setWidthPercentage(0.0F);
/*      */   }
/*      */ 
/*      */   public float[] matrix()
/*      */   {
/* 1140 */     return matrix(1.0F);
/*      */   }
/*      */ 
/*      */   public float[] matrix(float scalePercentage)
/*      */   {
/* 1149 */     float[] matrix = new float[8];
/* 1150 */     float cosX = (float)Math.cos(this.rotationRadians);
/* 1151 */     float sinX = (float)Math.sin(this.rotationRadians);
/* 1152 */     matrix[0] = (this.plainWidth * cosX * scalePercentage);
/* 1153 */     matrix[1] = (this.plainWidth * sinX * scalePercentage);
/* 1154 */     matrix[2] = (-this.plainHeight * sinX * scalePercentage);
/* 1155 */     matrix[3] = (this.plainHeight * cosX * scalePercentage);
/* 1156 */     if (this.rotationRadians < 1.570796326794897D) {
/* 1157 */       matrix[4] = matrix[2];
/* 1158 */       matrix[5] = 0.0F;
/* 1159 */       matrix[6] = matrix[0];
/* 1160 */       matrix[7] = (matrix[1] + matrix[3]);
/* 1161 */     } else if (this.rotationRadians < 3.141592653589793D) {
/* 1162 */       matrix[4] = (matrix[0] + matrix[2]);
/* 1163 */       matrix[5] = matrix[3];
/* 1164 */       matrix[6] = 0.0F;
/* 1165 */       matrix[7] = matrix[1];
/* 1166 */     } else if (this.rotationRadians < 4.71238898038469D) {
/* 1167 */       matrix[4] = matrix[0];
/* 1168 */       matrix[5] = (matrix[1] + matrix[3]);
/* 1169 */       matrix[6] = matrix[2];
/* 1170 */       matrix[7] = 0.0F;
/*      */     } else {
/* 1172 */       matrix[4] = 0.0F;
/* 1173 */       matrix[5] = matrix[1];
/* 1174 */       matrix[6] = (matrix[0] + matrix[2]);
/* 1175 */       matrix[7] = matrix[3];
/*      */     }
/* 1177 */     return matrix;
/*      */   }
/*      */ 
/*      */   protected static synchronized Long getSerialId()
/*      */   {
/* 1188 */     serialId += 1L;
/* 1189 */     return Long.valueOf(serialId);
/*      */   }
/*      */ 
/*      */   public Long getMySerialId()
/*      */   {
/* 1198 */     return this.mySerialId;
/*      */   }
/*      */ 
/*      */   public float getImageRotation()
/*      */   {
/* 1214 */     double d = 6.283185307179586D;
/* 1215 */     float rot = (float)((this.rotationRadians - this.initialRotation) % d);
/* 1216 */     if (rot < 0.0F) {
/* 1217 */       rot = (float)(rot + d);
/*      */     }
/* 1219 */     return rot;
/*      */   }
/*      */ 
/*      */   public void setRotation(float r)
/*      */   {
/* 1229 */     double d = 6.283185307179586D;
/* 1230 */     this.rotationRadians = ((float)((r + this.initialRotation) % d));
/* 1231 */     if (this.rotationRadians < 0.0F) {
/* 1232 */       this.rotationRadians = ((float)(this.rotationRadians + d));
/*      */     }
/* 1234 */     float[] matrix = matrix();
/* 1235 */     this.scaledWidth = (matrix[6] - matrix[4]);
/* 1236 */     this.scaledHeight = (matrix[7] - matrix[5]);
/*      */   }
/*      */ 
/*      */   public void setRotationDegrees(float deg)
/*      */   {
/* 1246 */     double d = 3.141592653589793D;
/* 1247 */     setRotation(deg / 180.0F * (float)d);
/*      */   }
/*      */ 
/*      */   public float getInitialRotation()
/*      */   {
/* 1255 */     return this.initialRotation;
/*      */   }
/*      */ 
/*      */   public void setInitialRotation(float initialRotation)
/*      */   {
/* 1264 */     float old_rot = this.rotationRadians - this.initialRotation;
/* 1265 */     this.initialRotation = initialRotation;
/* 1266 */     setRotation(old_rot);
/*      */   }
/*      */ 
/*      */   public float getIndentationLeft()
/*      */   {
/* 1289 */     return this.indentationLeft;
/*      */   }
/*      */ 
/*      */   public void setIndentationLeft(float f)
/*      */   {
/* 1298 */     this.indentationLeft = f;
/*      */   }
/*      */ 
/*      */   public float getIndentationRight()
/*      */   {
/* 1307 */     return this.indentationRight;
/*      */   }
/*      */ 
/*      */   public void setIndentationRight(float f)
/*      */   {
/* 1316 */     this.indentationRight = f;
/*      */   }
/*      */ 
/*      */   public float getSpacingBefore()
/*      */   {
/* 1325 */     return this.spacingBefore;
/*      */   }
/*      */ 
/*      */   public void setSpacingBefore(float spacing)
/*      */   {
/* 1336 */     this.spacingBefore = spacing;
/*      */   }
/*      */ 
/*      */   public float getSpacingAfter()
/*      */   {
/* 1345 */     return this.spacingAfter;
/*      */   }
/*      */ 
/*      */   public void setSpacingAfter(float spacing)
/*      */   {
/* 1356 */     this.spacingAfter = spacing;
/*      */   }
/*      */ 
/*      */   public float getWidthPercentage()
/*      */   {
/* 1372 */     return this.widthPercentage;
/*      */   }
/*      */ 
/*      */   public void setWidthPercentage(float widthPercentage)
/*      */   {
/* 1382 */     this.widthPercentage = widthPercentage;
/*      */   }
/*      */ 
/*      */   public boolean isScaleToFitLineWhenOverflow()
/*      */   {
/* 1400 */     return this.scaleToFitLineWhenOverflow;
/*      */   }
/*      */ 
/*      */   public void setScaleToFitLineWhenOverflow(boolean scaleToFitLineWhenOverflow)
/*      */   {
/* 1409 */     this.scaleToFitLineWhenOverflow = scaleToFitLineWhenOverflow;
/*      */   }
/*      */ 
/*      */   public boolean isScaleToFitHeight()
/*      */   {
/* 1427 */     return this.scaleToFitHeight;
/*      */   }
/*      */ 
/*      */   public void setScaleToFitHeight(boolean scaleToFitHeight)
/*      */   {
/* 1436 */     this.scaleToFitHeight = scaleToFitHeight;
/*      */   }
/*      */ 
/*      */   public void setAnnotation(Annotation annotation)
/*      */   {
/* 1451 */     this.annotation = annotation;
/*      */   }
/*      */ 
/*      */   public Annotation getAnnotation()
/*      */   {
/* 1460 */     return this.annotation;
/*      */   }
/*      */ 
/*      */   public PdfOCG getLayer()
/*      */   {
/* 1475 */     return this.layer;
/*      */   }
/*      */ 
/*      */   public void setLayer(PdfOCG layer)
/*      */   {
/* 1485 */     this.layer = layer;
/*      */   }
/*      */ 
/*      */   public boolean isInterpolation()
/*      */   {
/* 1499 */     return this.interpolation;
/*      */   }
/*      */ 
/*      */   public void setInterpolation(boolean interpolation)
/*      */   {
/* 1510 */     this.interpolation = interpolation;
/*      */   }
/*      */ 
/*      */   public int getOriginalType()
/*      */   {
/* 1528 */     return this.originalType;
/*      */   }
/*      */ 
/*      */   public void setOriginalType(int originalType)
/*      */   {
/* 1539 */     this.originalType = originalType;
/*      */   }
/*      */ 
/*      */   public byte[] getOriginalData()
/*      */   {
/* 1549 */     return this.originalData;
/*      */   }
/*      */ 
/*      */   public void setOriginalData(byte[] originalData)
/*      */   {
/* 1560 */     this.originalData = originalData;
/*      */   }
/*      */ 
/*      */   public boolean isDeflated()
/*      */   {
/* 1575 */     return this.deflated;
/*      */   }
/*      */ 
/*      */   public void setDeflated(boolean deflated)
/*      */   {
/* 1585 */     this.deflated = deflated;
/*      */   }
/*      */ 
/*      */   public int getDpiX()
/*      */   {
/* 1602 */     return this.dpiX;
/*      */   }
/*      */ 
/*      */   public int getDpiY()
/*      */   {
/* 1611 */     return this.dpiY;
/*      */   }
/*      */ 
/*      */   public void setDpi(int dpiX, int dpiY)
/*      */   {
/* 1623 */     this.dpiX = dpiX;
/* 1624 */     this.dpiY = dpiY;
/*      */   }
/*      */ 
/*      */   public float getXYRatio()
/*      */   {
/* 1638 */     return this.XYRatio;
/*      */   }
/*      */ 
/*      */   public void setXYRatio(float XYRatio)
/*      */   {
/* 1648 */     this.XYRatio = XYRatio;
/*      */   }
/*      */ 
/*      */   public int getColorspace()
/*      */   {
/* 1664 */     return this.colorspace;
/*      */   }
/*      */ 
/*      */   public void setColorTransform(int c)
/*      */   {
/* 1670 */     this.colortransform = c;
/*      */   }
/*      */ 
/*      */   public int getColorTransform() {
/* 1674 */     return this.colortransform;
/*      */   }
/*      */ 
/*      */   public boolean isInverted()
/*      */   {
/* 1686 */     return this.invert;
/*      */   }
/*      */ 
/*      */   public void setInverted(boolean invert)
/*      */   {
/* 1696 */     this.invert = invert;
/*      */   }
/*      */ 
/*      */   public void tagICC(ICC_Profile profile)
/*      */   {
/* 1709 */     this.profile = profile;
/*      */   }
/*      */ 
/*      */   public boolean hasICCProfile()
/*      */   {
/* 1718 */     return this.profile != null;
/*      */   }
/*      */ 
/*      */   public ICC_Profile getICCProfile()
/*      */   {
/* 1727 */     return this.profile;
/*      */   }
/*      */ 
/*      */   public PdfDictionary getAdditional()
/*      */   {
/* 1739 */     return this.additional;
/*      */   }
/*      */ 
/*      */   public void setAdditional(PdfDictionary additional)
/*      */   {
/* 1749 */     this.additional = additional;
/*      */   }
/*      */ 
/*      */   public void simplifyColorspace()
/*      */   {
/* 1756 */     if (this.additional == null)
/* 1757 */       return;
/* 1758 */     PdfArray value = this.additional.getAsArray(PdfName.COLORSPACE);
/* 1759 */     if (value == null)
/* 1760 */       return;
/* 1761 */     PdfObject cs = simplifyColorspace(value);
/*      */     PdfObject newValue;
/*      */     PdfObject newValue;
/* 1763 */     if (cs.isName()) {
/* 1764 */       newValue = cs;
/*      */     } else {
/* 1766 */       newValue = value;
/* 1767 */       PdfName first = value.getAsName(0);
/* 1768 */       if ((PdfName.INDEXED.equals(first)) && 
/* 1769 */         (value.size() >= 2)) {
/* 1770 */         PdfArray second = value.getAsArray(1);
/* 1771 */         if (second != null) {
/* 1772 */           value.set(1, simplifyColorspace(second));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1777 */     this.additional.put(PdfName.COLORSPACE, newValue);
/*      */   }
/*      */ 
/*      */   private PdfObject simplifyColorspace(PdfArray obj)
/*      */   {
/* 1784 */     if (obj == null)
/* 1785 */       return obj;
/* 1786 */     PdfName first = obj.getAsName(0);
/* 1787 */     if (PdfName.CALGRAY.equals(first))
/* 1788 */       return PdfName.DEVICEGRAY;
/* 1789 */     if (PdfName.CALRGB.equals(first)) {
/* 1790 */       return PdfName.DEVICERGB;
/*      */     }
/* 1792 */     return obj;
/*      */   }
/*      */ 
/*      */   public boolean isMask()
/*      */   {
/* 1810 */     return this.mask;
/*      */   }
/*      */ 
/*      */   public void makeMask()
/*      */     throws DocumentException
/*      */   {
/* 1820 */     if (!isMaskCandidate())
/* 1821 */       throw new DocumentException(MessageLocalization.getComposedMessage("this.image.can.not.be.an.image.mask", new Object[0]));
/* 1822 */     this.mask = true;
/*      */   }
/*      */ 
/*      */   public boolean isMaskCandidate()
/*      */   {
/* 1832 */     if ((this.type == 34) && 
/* 1833 */       (this.bpc > 255)) {
/* 1834 */       return true;
/*      */     }
/* 1836 */     return this.colorspace == 1;
/*      */   }
/*      */ 
/*      */   public Image getImageMask()
/*      */   {
/* 1845 */     return this.imageMask;
/*      */   }
/*      */ 
/*      */   public void setImageMask(Image mask)
/*      */     throws DocumentException
/*      */   {
/* 1857 */     if (this.mask)
/* 1858 */       throw new DocumentException(MessageLocalization.getComposedMessage("an.image.mask.cannot.contain.another.image.mask", new Object[0]));
/* 1859 */     if (!mask.mask)
/* 1860 */       throw new DocumentException(MessageLocalization.getComposedMessage("the.image.mask.is.not.a.mask.did.you.do.makemask", new Object[0]));
/* 1861 */     this.imageMask = mask;
/* 1862 */     this.smask = ((mask.bpc > 1) && (mask.bpc <= 8));
/*      */   }
/*      */ 
/*      */   public boolean isSmask()
/*      */   {
/* 1872 */     return this.smask;
/*      */   }
/*      */ 
/*      */   public void setSmask(boolean smask)
/*      */   {
/* 1882 */     this.smask = smask;
/*      */   }
/*      */ 
/*      */   public int[] getTransparency()
/*      */   {
/* 1895 */     return this.transparency;
/*      */   }
/*      */ 
/*      */   public void setTransparency(int[] transparency)
/*      */   {
/* 1905 */     this.transparency = transparency;
/*      */   }
/*      */ 
/*      */   public int getCompressionLevel()
/*      */   {
/* 1915 */     return this.compressionLevel;
/*      */   }
/*      */ 
/*      */   public void setCompressionLevel(int compressionLevel)
/*      */   {
/* 1924 */     if ((compressionLevel < 0) || (compressionLevel > 9))
/* 1925 */       this.compressionLevel = -1;
/*      */     else
/* 1927 */       this.compressionLevel = compressionLevel;
/*      */   }
/*      */ 
/*      */   public PdfObject getAccessibleAttribute(PdfName key) {
/* 1931 */     if (this.accessibleAttributes != null) {
/* 1932 */       return (PdfObject)this.accessibleAttributes.get(key);
/*      */     }
/* 1934 */     return null;
/*      */   }
/*      */ 
/*      */   public void setAccessibleAttribute(PdfName key, PdfObject value) {
/* 1938 */     if (this.accessibleAttributes == null)
/* 1939 */       this.accessibleAttributes = new HashMap();
/* 1940 */     this.accessibleAttributes.put(key, value);
/*      */   }
/*      */ 
/*      */   public HashMap<PdfName, PdfObject> getAccessibleAttributes() {
/* 1944 */     return this.accessibleAttributes;
/*      */   }
/*      */ 
/*      */   public PdfName getRole() {
/* 1948 */     return this.role;
/*      */   }
/*      */ 
/*      */   public void setRole(PdfName role) {
/* 1952 */     this.role = role;
/*      */   }
/*      */ 
/*      */   public AccessibleElementId getId() {
/* 1956 */     if (this.id == null)
/* 1957 */       this.id = new AccessibleElementId();
/* 1958 */     return this.id;
/*      */   }
/*      */ 
/*      */   public void setId(AccessibleElementId id) {
/* 1962 */     this.id = id;
/*      */   }
/*      */ 
/*      */   public boolean isInline() {
/* 1966 */     return true;
/*      */   }
/*      */ 
/*      */   public static Image getInstance(java.awt.Image image, Color color, boolean forceBW)
/*      */     throws BadElementException, IOException
/*      */   {
/* 1990 */     if ((image instanceof BufferedImage)) {
/* 1991 */       BufferedImage bi = (BufferedImage)image;
/* 1992 */       if ((bi.getType() == 12) && (bi.getColorModel().getPixelSize() == 1))
/*      */       {
/* 1994 */         forceBW = true;
/*      */       }
/*      */     }
/*      */ 
/* 1998 */     PixelGrabber pg = new PixelGrabber(image, 0, 0, -1, -1, true);
/*      */     try
/*      */     {
/* 2001 */       pg.grabPixels();
/*      */     } catch (InterruptedException e) {
/* 2003 */       throw new IOException(MessageLocalization.getComposedMessage("java.awt.image.interrupted.waiting.for.pixels", new Object[0]));
/*      */     }
/* 2005 */     if ((pg.getStatus() & 0x80) != 0) {
/* 2006 */       throw new IOException(MessageLocalization.getComposedMessage("java.awt.image.fetch.aborted.or.errored", new Object[0]));
/*      */     }
/* 2008 */     int w = pg.getWidth();
/* 2009 */     int h = pg.getHeight();
/* 2010 */     int[] pixels = (int[])pg.getPixels();
/* 2011 */     if (forceBW) {
/* 2012 */       int byteWidth = w / 8 + ((w & 0x7) != 0 ? 1 : 0);
/* 2013 */       byte[] pixelsByte = new byte[byteWidth * h];
/*      */ 
/* 2015 */       int index = 0;
/* 2016 */       int size = h * w;
/* 2017 */       int transColor = 1;
/* 2018 */       if (color != null) {
/* 2019 */         transColor = color.getRed() + color.getGreen() + color.getBlue() < 384 ? 0 : 1;
/*      */       }
/*      */ 
/* 2022 */       int[] transparency = null;
/* 2023 */       int cbyte = 128;
/* 2024 */       int wMarker = 0;
/* 2025 */       int currByte = 0;
/* 2026 */       if (color != null)
/* 2027 */         for (int j = 0; j < size; j++) {
/* 2028 */           int alpha = pixels[j] >> 24 & 0xFF;
/* 2029 */           if (alpha < 250) {
/* 2030 */             if (transColor == 1)
/* 2031 */               currByte |= cbyte;
/*      */           }
/* 2033 */           else if ((pixels[j] & 0x888) != 0) {
/* 2034 */             currByte |= cbyte;
/*      */           }
/* 2036 */           cbyte >>= 1;
/* 2037 */           if ((cbyte == 0) || (wMarker + 1 >= w)) {
/* 2038 */             pixelsByte[(index++)] = ((byte)currByte);
/* 2039 */             cbyte = 128;
/* 2040 */             currByte = 0;
/*      */           }
/* 2042 */           wMarker++;
/* 2043 */           if (wMarker >= w)
/* 2044 */             wMarker = 0;
/*      */         }
/*      */       else {
/* 2047 */         for (int j = 0; j < size; j++) {
/* 2048 */           if (transparency == null) {
/* 2049 */             int alpha = pixels[j] >> 24 & 0xFF;
/* 2050 */             if (alpha == 0) {
/* 2051 */               transparency = new int[2];
/*      */               int tmp415_414 = ((pixels[j] & 0x888) != 0 ? 255 : 0); transparency[1] = tmp415_414; transparency[0] = tmp415_414;
/*      */             }
/*      */           }
/* 2056 */           if ((pixels[j] & 0x888) != 0)
/* 2057 */             currByte |= cbyte;
/* 2058 */           cbyte >>= 1;
/* 2059 */           if ((cbyte == 0) || (wMarker + 1 >= w)) {
/* 2060 */             pixelsByte[(index++)] = ((byte)currByte);
/* 2061 */             cbyte = 128;
/* 2062 */             currByte = 0;
/*      */           }
/* 2064 */           wMarker++;
/* 2065 */           if (wMarker >= w)
/* 2066 */             wMarker = 0;
/*      */         }
/*      */       }
/* 2069 */       return getInstance(w, h, 1, 1, pixelsByte, transparency);
/*      */     }
/* 2071 */     byte[] pixelsByte = new byte[w * h * 3];
/* 2072 */     byte[] smask = null;
/*      */ 
/* 2074 */     int index = 0;
/* 2075 */     int size = h * w;
/* 2076 */     int red = 255;
/* 2077 */     int green = 255;
/* 2078 */     int blue = 255;
/* 2079 */     if (color != null) {
/* 2080 */       red = color.getRed();
/* 2081 */       green = color.getGreen();
/* 2082 */       blue = color.getBlue();
/*      */     }
/* 2084 */     int[] transparency = null;
/* 2085 */     if (color != null) {
/* 2086 */       for (int j = 0; j < size; j++) {
/* 2087 */         int alpha = pixels[j] >> 24 & 0xFF;
/* 2088 */         if (alpha < 250) {
/* 2089 */           pixelsByte[(index++)] = ((byte)red);
/* 2090 */           pixelsByte[(index++)] = ((byte)green);
/* 2091 */           pixelsByte[(index++)] = ((byte)blue);
/*      */         } else {
/* 2093 */           pixelsByte[(index++)] = ((byte)(pixels[j] >> 16 & 0xFF));
/* 2094 */           pixelsByte[(index++)] = ((byte)(pixels[j] >> 8 & 0xFF));
/* 2095 */           pixelsByte[(index++)] = ((byte)(pixels[j] & 0xFF));
/*      */         }
/*      */       }
/*      */     } else {
/* 2099 */       int transparentPixel = 0;
/* 2100 */       smask = new byte[w * h];
/* 2101 */       boolean shades = false;
/* 2102 */       for (int j = 0; j < size; j++) {
/* 2103 */         byte alpha = smask[j] = (byte)(pixels[j] >> 24 & 0xFF);
/*      */ 
/* 2105 */         if (!shades) {
/* 2106 */           if ((alpha != 0) && (alpha != -1))
/* 2107 */             shades = true;
/* 2108 */           else if (transparency == null) {
/* 2109 */             if (alpha == 0) {
/* 2110 */               transparentPixel = pixels[j] & 0xFFFFFF;
/* 2111 */               transparency = new int[6];
/*      */               int tmp823_822 = (transparentPixel >> 16 & 0xFF); transparency[1] = tmp823_822; transparency[0] = tmp823_822;
/*      */               int tmp841_840 = (transparentPixel >> 8 & 0xFF); transparency[3] = tmp841_840; transparency[2] = tmp841_840;
/*      */               int tmp856_855 = (transparentPixel & 0xFF); transparency[5] = tmp856_855; transparency[4] = tmp856_855;
/*      */             }
/* 2116 */           } else if ((pixels[j] & 0xFFFFFF) != transparentPixel) {
/* 2117 */             shades = true;
/*      */           }
/*      */         }
/* 2120 */         pixelsByte[(index++)] = ((byte)(pixels[j] >> 16 & 0xFF));
/* 2121 */         pixelsByte[(index++)] = ((byte)(pixels[j] >> 8 & 0xFF));
/* 2122 */         pixelsByte[(index++)] = ((byte)(pixels[j] & 0xFF));
/*      */       }
/* 2124 */       if (shades)
/* 2125 */         transparency = null;
/*      */       else
/* 2127 */         smask = null;
/*      */     }
/* 2129 */     Image img = getInstance(w, h, 3, 8, pixelsByte, transparency);
/* 2130 */     if (smask != null) {
/* 2131 */       Image sm = getInstance(w, h, 1, 8, smask);
/*      */       try {
/* 2133 */         sm.makeMask();
/* 2134 */         img.setImageMask(sm);
/*      */       } catch (DocumentException de) {
/* 2136 */         throw new ExceptionConverter(de);
/*      */       }
/*      */     }
/* 2139 */     return img;
/*      */   }
/*      */ 
/*      */   public static Image getInstance(java.awt.Image image, Color color)
/*      */     throws BadElementException, IOException
/*      */   {
/* 2159 */     return getInstance(image, color, false);
/*      */   }
/*      */ 
/*      */   public static Image getInstance(PdfWriter writer, java.awt.Image awtImage, float quality)
/*      */     throws BadElementException, IOException
/*      */   {
/* 2178 */     return getInstance(new PdfContentByte(writer), awtImage, quality);
/*      */   }
/*      */ 
/*      */   public static Image getInstance(PdfContentByte cb, java.awt.Image awtImage, float quality)
/*      */     throws BadElementException, IOException
/*      */   {
/* 2197 */     PixelGrabber pg = new PixelGrabber(awtImage, 0, 0, -1, -1, true);
/*      */     try
/*      */     {
/* 2200 */       pg.grabPixels();
/*      */     } catch (InterruptedException e) {
/* 2202 */       throw new IOException(MessageLocalization.getComposedMessage("java.awt.image.interrupted.waiting.for.pixels", new Object[0]));
/*      */     }
/* 2204 */     if ((pg.getStatus() & 0x80) != 0) {
/* 2205 */       throw new IOException(MessageLocalization.getComposedMessage("java.awt.image.fetch.aborted.or.errored", new Object[0]));
/*      */     }
/* 2207 */     int w = pg.getWidth();
/* 2208 */     int h = pg.getHeight();
/* 2209 */     PdfTemplate tp = cb.createTemplate(w, h);
/* 2210 */     PdfGraphics2D g2d = new PdfGraphics2D(tp, w, h, null, false, true, quality);
/* 2211 */     g2d.drawImage(awtImage, 0, 0, null);
/* 2212 */     g2d.dispose();
/* 2213 */     return getInstance(tp);
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.Image
 * JD-Core Version:    0.6.2
 */