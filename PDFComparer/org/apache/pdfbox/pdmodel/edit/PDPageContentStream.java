/*      */ package org.apache.pdfbox.pdmodel.edit;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.color.ColorSpace;
/*      */ import java.awt.geom.AffineTransform;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.Closeable;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.text.NumberFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import org.apache.commons.logging.Log;
/*      */ import org.apache.commons.logging.LogFactory;
/*      */ import org.apache.pdfbox.cos.COSArray;
/*      */ import org.apache.pdfbox.cos.COSDictionary;
/*      */ import org.apache.pdfbox.cos.COSName;
/*      */ import org.apache.pdfbox.cos.COSString;
/*      */ import org.apache.pdfbox.pdmodel.PDDocument;
/*      */ import org.apache.pdfbox.pdmodel.PDPage;
/*      */ import org.apache.pdfbox.pdmodel.PDResources;
/*      */ import org.apache.pdfbox.pdmodel.common.COSStreamArray;
/*      */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*      */ import org.apache.pdfbox.pdmodel.font.PDFont;
/*      */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
/*      */ import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceCMYK;
/*      */ import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceGray;
/*      */ import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceN;
/*      */ import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
/*      */ import org.apache.pdfbox.pdmodel.graphics.color.PDICCBased;
/*      */ import org.apache.pdfbox.pdmodel.graphics.color.PDPattern;
/*      */ import org.apache.pdfbox.pdmodel.graphics.color.PDSeparation;
/*      */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObject;
/*      */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
/*      */ 
/*      */ public class PDPageContentStream
/*      */   implements Closeable
/*      */ {
/*   68 */   private static final Log LOG = LogFactory.getLog(PDPageContentStream.class);
/*      */   private OutputStream output;
/*   71 */   private boolean inTextMode = false;
/*      */   private PDResources resources;
/*   74 */   private PDColorSpace currentStrokingColorSpace = new PDDeviceGray();
/*   75 */   private PDColorSpace currentNonStrokingColorSpace = new PDDeviceGray();
/*      */ 
/*   78 */   private float[] colorComponents = new float[4];
/*      */ 
/*   80 */   private NumberFormat formatDecimal = NumberFormat.getNumberInstance(Locale.US);
/*      */   private static final String ISO8859 = "ISO-8859-1";
/*   96 */   private static final byte[] BEGIN_TEXT = getISOBytes("BT\n");
/*   97 */   private static final byte[] END_TEXT = getISOBytes("ET\n");
/*   98 */   private static final byte[] SET_FONT = getISOBytes("Tf\n");
/*   99 */   private static final byte[] MOVE_TEXT_POSITION = getISOBytes("Td\n");
/*  100 */   private static final byte[] SET_TEXT_MATRIX = getISOBytes("Tm\n");
/*  101 */   private static final byte[] SHOW_TEXT = getISOBytes("Tj\n");
/*      */ 
/*  103 */   private static final byte[] SAVE_GRAPHICS_STATE = getISOBytes("q\n");
/*  104 */   private static final byte[] RESTORE_GRAPHICS_STATE = getISOBytes("Q\n");
/*  105 */   private static final byte[] CONCATENATE_MATRIX = getISOBytes("cm\n");
/*  106 */   private static final byte[] XOBJECT_DO = getISOBytes("Do\n");
/*  107 */   private static final byte[] RG_STROKING = getISOBytes("RG\n");
/*  108 */   private static final byte[] RG_NON_STROKING = getISOBytes("rg\n");
/*  109 */   private static final byte[] K_STROKING = getISOBytes("K\n");
/*  110 */   private static final byte[] K_NON_STROKING = getISOBytes("k\n");
/*  111 */   private static final byte[] G_STROKING = getISOBytes("G\n");
/*  112 */   private static final byte[] G_NON_STROKING = getISOBytes("g\n");
/*  113 */   private static final byte[] RECTANGLE = getISOBytes("re\n");
/*  114 */   private static final byte[] FILL_NON_ZERO = getISOBytes("f\n");
/*  115 */   private static final byte[] FILL_EVEN_ODD = getISOBytes("f*\n");
/*  116 */   private static final byte[] LINE_TO = getISOBytes("l\n");
/*  117 */   private static final byte[] MOVE_TO = getISOBytes("m\n");
/*  118 */   private static final byte[] CLOSE_STROKE = getISOBytes("s\n");
/*  119 */   private static final byte[] STROKE = getISOBytes("S\n");
/*  120 */   private static final byte[] LINE_WIDTH = getISOBytes("w\n");
/*  121 */   private static final byte[] LINE_JOIN_STYLE = getISOBytes("j\n");
/*  122 */   private static final byte[] LINE_CAP_STYLE = getISOBytes("J\n");
/*  123 */   private static final byte[] LINE_DASH_PATTERN = getISOBytes("d\n");
/*  124 */   private static final byte[] CLOSE_SUBPATH = getISOBytes("h\n");
/*  125 */   private static final byte[] CLIP_PATH_NON_ZERO = getISOBytes("W\n");
/*  126 */   private static final byte[] CLIP_PATH_EVEN_ODD = getISOBytes("W*\n");
/*  127 */   private static final byte[] NOP = getISOBytes("n\n");
/*  128 */   private static final byte[] BEZIER_312 = getISOBytes("c\n");
/*  129 */   private static final byte[] BEZIER_32 = getISOBytes("v\n");
/*  130 */   private static final byte[] BEZIER_313 = getISOBytes("y\n");
/*      */ 
/*  132 */   private static final byte[] BMC = getISOBytes("BMC\n");
/*  133 */   private static final byte[] BDC = getISOBytes("BDC\n");
/*  134 */   private static final byte[] EMC = getISOBytes("EMC\n");
/*      */ 
/*  136 */   private static final byte[] SET_STROKING_COLORSPACE = getISOBytes("CS\n");
/*  137 */   private static final byte[] SET_NON_STROKING_COLORSPACE = getISOBytes("cs\n");
/*      */ 
/*  139 */   private static final byte[] SET_STROKING_COLOR_SIMPLE = getISOBytes("SC\n");
/*  140 */   private static final byte[] SET_STROKING_COLOR_COMPLEX = getISOBytes("SCN\n");
/*  141 */   private static final byte[] SET_NON_STROKING_COLOR_SIMPLE = getISOBytes("sc\n");
/*  142 */   private static final byte[] SET_NON_STROKING_COLOR_COMPLEX = getISOBytes("scn\n");
/*      */ 
/*  144 */   private static final byte[] OPENING_BRACKET = getISOBytes("[");
/*  145 */   private static final byte[] CLOSING_BRACKET = getISOBytes("]");
/*      */   private static final int SPACE = 32;
/*      */ 
/*      */   private static byte[] getISOBytes(String s)
/*      */   {
/*      */     try
/*      */     {
/*   88 */       return s.getBytes("ISO-8859-1");
/*      */     }
/*      */     catch (UnsupportedEncodingException ex)
/*      */     {
/*   92 */       throw new IllegalStateException(ex);
/*      */     }
/*      */   }
/*      */ 
/*      */   public PDPageContentStream(PDDocument document, PDPage sourcePage)
/*      */     throws IOException
/*      */   {
/*  158 */     this(document, sourcePage, false, true);
/*      */   }
/*      */ 
/*      */   public PDPageContentStream(PDDocument document, PDPage sourcePage, boolean appendContent, boolean compress)
/*      */     throws IOException
/*      */   {
/*  173 */     this(document, sourcePage, appendContent, compress, false);
/*      */   }
/*      */ 
/*      */   public PDPageContentStream(PDDocument document, PDPage sourcePage, boolean appendContent, boolean compress, boolean resetContext)
/*      */     throws IOException
/*      */   {
/*  190 */     PDStream contents = sourcePage.getContents();
/*  191 */     boolean hasContent = contents != null;
/*      */ 
/*  194 */     if ((appendContent) && (hasContent))
/*      */     {
/*  198 */       PDStream contentsToAppend = new PDStream(document);
/*      */ 
/*  201 */       COSStreamArray compoundStream = null;
/*      */ 
/*  204 */       if ((contents.getStream() instanceof COSStreamArray))
/*      */       {
/*  206 */         compoundStream = (COSStreamArray)contents.getStream();
/*  207 */         compoundStream.appendStream(contentsToAppend.getStream());
/*      */       }
/*      */       else
/*      */       {
/*  212 */         COSArray newArray = new COSArray();
/*  213 */         newArray.add(contents.getCOSObject());
/*  214 */         newArray.add(contentsToAppend.getCOSObject());
/*  215 */         compoundStream = new COSStreamArray(newArray);
/*      */       }
/*      */ 
/*  218 */       if (compress)
/*      */       {
/*  220 */         List filters = new ArrayList();
/*  221 */         filters.add(COSName.FLATE_DECODE);
/*  222 */         contentsToAppend.setFilters(filters);
/*      */       }
/*      */ 
/*  225 */       if (resetContext)
/*      */       {
/*  228 */         PDStream saveGraphics = new PDStream(document);
/*  229 */         this.output = saveGraphics.createOutputStream();
/*      */ 
/*  231 */         saveGraphicsState();
/*  232 */         close();
/*  233 */         if (compress)
/*      */         {
/*  235 */           List filters = new ArrayList();
/*  236 */           filters.add(COSName.FLATE_DECODE);
/*  237 */           saveGraphics.setFilters(filters);
/*      */         }
/*      */ 
/*  240 */         compoundStream.insertCOSStream(saveGraphics);
/*      */       }
/*      */ 
/*  244 */       sourcePage.setContents(new PDStream(compoundStream));
/*  245 */       this.output = contentsToAppend.createOutputStream();
/*  246 */       if (resetContext)
/*      */       {
/*  249 */         restoreGraphicsState();
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  254 */       if (hasContent)
/*      */       {
/*  256 */         LOG.warn("You are overwriting an existing content, you should use the append mode");
/*      */       }
/*  258 */       contents = new PDStream(document);
/*  259 */       if (compress)
/*      */       {
/*  261 */         List filters = new ArrayList();
/*  262 */         filters.add(COSName.FLATE_DECODE);
/*  263 */         contents.setFilters(filters);
/*      */       }
/*  265 */       sourcePage.setContents(contents);
/*  266 */       this.output = contents.createOutputStream();
/*      */     }
/*  268 */     this.formatDecimal.setMaximumFractionDigits(10);
/*  269 */     this.formatDecimal.setGroupingUsed(false);
/*      */ 
/*  271 */     this.resources = sourcePage.getResources();
/*  272 */     if (this.resources == null)
/*      */     {
/*  274 */       this.resources = new PDResources();
/*  275 */       sourcePage.setResources(this.resources);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void beginText()
/*      */     throws IOException
/*      */   {
/*  288 */     if (this.inTextMode)
/*      */     {
/*  290 */       throw new IOException("Error: Nested beginText() calls are not allowed.");
/*      */     }
/*  292 */     appendRawCommands(BEGIN_TEXT);
/*  293 */     this.inTextMode = true;
/*      */   }
/*      */ 
/*      */   public void endText()
/*      */     throws IOException
/*      */   {
/*  304 */     if (!this.inTextMode)
/*      */     {
/*  306 */       throw new IOException("Error: You must call beginText() before calling endText.");
/*      */     }
/*  308 */     appendRawCommands(END_TEXT);
/*  309 */     this.inTextMode = false;
/*      */   }
/*      */ 
/*      */   public void setFont(PDFont font, float fontSize)
/*      */     throws IOException
/*      */   {
/*  321 */     String fontMapping = this.resources.addFont(font);
/*  322 */     appendRawCommands("/");
/*  323 */     appendRawCommands(fontMapping);
/*  324 */     appendRawCommands(32);
/*  325 */     appendRawCommands(fontSize);
/*  326 */     appendRawCommands(32);
/*  327 */     appendRawCommands(SET_FONT);
/*      */   }
/*      */ 
/*      */   public void drawImage(PDXObjectImage image, float x, float y)
/*      */     throws IOException
/*      */   {
/*  341 */     drawXObject(image, x, y, image.getWidth(), image.getHeight());
/*      */   }
/*      */ 
/*      */   public void drawXObject(PDXObject xobject, float x, float y, float width, float height)
/*      */     throws IOException
/*      */   {
/*  357 */     AffineTransform transform = new AffineTransform(width, 0.0F, 0.0F, height, x, y);
/*  358 */     drawXObject(xobject, transform);
/*      */   }
/*      */ 
/*      */   public void drawXObject(PDXObject xobject, AffineTransform transform)
/*      */     throws IOException
/*      */   {
/*  371 */     if (this.inTextMode)
/*      */     {
/*  373 */       throw new IOException("Error: drawXObject is not allowed within a text block.");
/*      */     }
/*  375 */     String xObjectPrefix = null;
/*  376 */     if ((xobject instanceof PDXObjectImage))
/*      */     {
/*  378 */       xObjectPrefix = "Im";
/*      */     }
/*      */     else
/*      */     {
/*  382 */       xObjectPrefix = "Form";
/*      */     }
/*  384 */     String objMapping = this.resources.addXObject(xobject, xObjectPrefix);
/*  385 */     saveGraphicsState();
/*  386 */     appendRawCommands(32);
/*  387 */     concatenate2CTM(transform);
/*  388 */     appendRawCommands(32);
/*  389 */     appendRawCommands("/");
/*  390 */     appendRawCommands(objMapping);
/*  391 */     appendRawCommands(32);
/*  392 */     appendRawCommands(XOBJECT_DO);
/*  393 */     restoreGraphicsState();
/*      */   }
/*      */ 
/*      */   public void moveTextPositionByAmount(float x, float y)
/*      */     throws IOException
/*      */   {
/*  405 */     if (!this.inTextMode)
/*      */     {
/*  407 */       throw new IOException("Error: must call beginText() before moveTextPositionByAmount");
/*      */     }
/*  409 */     appendRawCommands(x);
/*  410 */     appendRawCommands(32);
/*  411 */     appendRawCommands(y);
/*  412 */     appendRawCommands(32);
/*  413 */     appendRawCommands(MOVE_TEXT_POSITION);
/*      */   }
/*      */ 
/*      */   public void setTextMatrix(double a, double b, double c, double d, double e, double f)
/*      */     throws IOException
/*      */   {
/*  429 */     if (!this.inTextMode)
/*      */     {
/*  431 */       throw new IOException("Error: must call beginText() before setTextMatrix");
/*      */     }
/*  433 */     appendRawCommands(a);
/*  434 */     appendRawCommands(32);
/*  435 */     appendRawCommands(b);
/*  436 */     appendRawCommands(32);
/*  437 */     appendRawCommands(c);
/*  438 */     appendRawCommands(32);
/*  439 */     appendRawCommands(d);
/*  440 */     appendRawCommands(32);
/*  441 */     appendRawCommands(e);
/*  442 */     appendRawCommands(32);
/*  443 */     appendRawCommands(f);
/*  444 */     appendRawCommands(32);
/*  445 */     appendRawCommands(SET_TEXT_MATRIX);
/*      */   }
/*      */ 
/*      */   public void setTextMatrix(AffineTransform matrix)
/*      */     throws IOException
/*      */   {
/*  456 */     if (!this.inTextMode)
/*      */     {
/*  458 */       throw new IOException("Error: must call beginText() before setTextMatrix");
/*      */     }
/*  460 */     appendMatrix(matrix);
/*  461 */     appendRawCommands(SET_TEXT_MATRIX);
/*      */   }
/*      */ 
/*      */   public void setTextScaling(double sx, double sy, double tx, double ty)
/*      */     throws IOException
/*      */   {
/*  475 */     setTextMatrix(sx, 0.0D, 0.0D, sy, tx, ty);
/*      */   }
/*      */ 
/*      */   public void setTextTranslation(double tx, double ty)
/*      */     throws IOException
/*      */   {
/*  487 */     setTextMatrix(1.0D, 0.0D, 0.0D, 1.0D, tx, ty);
/*      */   }
/*      */ 
/*      */   public void setTextRotation(double angle, double tx, double ty)
/*      */     throws IOException
/*      */   {
/*  500 */     double angleCos = Math.cos(angle);
/*  501 */     double angleSin = Math.sin(angle);
/*  502 */     setTextMatrix(angleCos, angleSin, -angleSin, angleCos, tx, ty);
/*      */   }
/*      */ 
/*      */   public void concatenate2CTM(double a, double b, double c, double d, double e, double f)
/*      */     throws IOException
/*      */   {
/*  517 */     appendRawCommands(a);
/*  518 */     appendRawCommands(32);
/*  519 */     appendRawCommands(b);
/*  520 */     appendRawCommands(32);
/*  521 */     appendRawCommands(c);
/*  522 */     appendRawCommands(32);
/*  523 */     appendRawCommands(d);
/*  524 */     appendRawCommands(32);
/*  525 */     appendRawCommands(e);
/*  526 */     appendRawCommands(32);
/*  527 */     appendRawCommands(f);
/*  528 */     appendRawCommands(32);
/*  529 */     appendRawCommands(CONCATENATE_MATRIX);
/*      */   }
/*      */ 
/*      */   public void concatenate2CTM(AffineTransform at)
/*      */     throws IOException
/*      */   {
/*  540 */     appendMatrix(at);
/*  541 */     appendRawCommands(CONCATENATE_MATRIX);
/*      */   }
/*      */ 
/*      */   public void drawString(String text)
/*      */     throws IOException
/*      */   {
/*  552 */     if (!this.inTextMode)
/*      */     {
/*  554 */       throw new IOException("Error: must call beginText() before drawString");
/*      */     }
/*  556 */     COSString string = new COSString(text);
/*  557 */     ByteArrayOutputStream buffer = new ByteArrayOutputStream();
/*  558 */     string.writePDF(buffer);
/*  559 */     appendRawCommands(buffer.toByteArray());
/*  560 */     appendRawCommands(32);
/*  561 */     appendRawCommands(SHOW_TEXT);
/*      */   }
/*      */ 
/*      */   public void setStrokingColorSpace(PDColorSpace colorSpace)
/*      */     throws IOException
/*      */   {
/*  573 */     this.currentStrokingColorSpace = colorSpace;
/*  574 */     writeColorSpace(colorSpace);
/*  575 */     appendRawCommands(SET_STROKING_COLORSPACE);
/*      */   }
/*      */ 
/*      */   public void setNonStrokingColorSpace(PDColorSpace colorSpace)
/*      */     throws IOException
/*      */   {
/*  587 */     this.currentNonStrokingColorSpace = colorSpace;
/*  588 */     writeColorSpace(colorSpace);
/*  589 */     appendRawCommands(SET_NON_STROKING_COLORSPACE);
/*      */   }
/*      */ 
/*      */   private void writeColorSpace(PDColorSpace colorSpace) throws IOException
/*      */   {
/*  594 */     COSName key = null;
/*  595 */     if (((colorSpace instanceof PDDeviceGray)) || ((colorSpace instanceof PDDeviceRGB)) || ((colorSpace instanceof PDDeviceCMYK)))
/*      */     {
/*  598 */       key = COSName.getPDFName(colorSpace.getName());
/*      */     }
/*      */     else
/*      */     {
/*  602 */       COSDictionary colorSpaces = (COSDictionary)this.resources.getCOSDictionary().getDictionaryObject(COSName.COLORSPACE);
/*      */ 
/*  604 */       if (colorSpaces == null)
/*      */       {
/*  606 */         colorSpaces = new COSDictionary();
/*  607 */         this.resources.getCOSDictionary().setItem(COSName.COLORSPACE, colorSpaces);
/*      */       }
/*  609 */       key = colorSpaces.getKeyForValue(colorSpace.getCOSObject());
/*      */ 
/*  611 */       if (key == null)
/*      */       {
/*  613 */         int counter = 0;
/*  614 */         String csName = "CS";
/*  615 */         while (colorSpaces.containsValue(csName + counter))
/*      */         {
/*  617 */           counter++;
/*      */         }
/*  619 */         key = COSName.getPDFName(csName + counter);
/*  620 */         colorSpaces.setItem(key, colorSpace);
/*      */       }
/*      */     }
/*  623 */     key.writePDF(this.output);
/*  624 */     appendRawCommands(32);
/*      */   }
/*      */ 
/*      */   public void setStrokingColor(float[] components)
/*      */     throws IOException
/*      */   {
/*  635 */     for (int i = 0; i < components.length; i++)
/*      */     {
/*  637 */       appendRawCommands(components[i]);
/*  638 */       appendRawCommands(32);
/*      */     }
/*  640 */     if (((this.currentStrokingColorSpace instanceof PDSeparation)) || ((this.currentStrokingColorSpace instanceof PDPattern)) || ((this.currentStrokingColorSpace instanceof PDDeviceN)) || ((this.currentStrokingColorSpace instanceof PDICCBased)))
/*      */     {
/*  643 */       appendRawCommands(SET_STROKING_COLOR_COMPLEX);
/*      */     }
/*      */     else
/*      */     {
/*  647 */       appendRawCommands(SET_STROKING_COLOR_SIMPLE);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setStrokingColor(Color color)
/*      */     throws IOException
/*      */   {
/*  659 */     ColorSpace colorSpace = color.getColorSpace();
/*  660 */     if (colorSpace.getType() == 5)
/*      */     {
/*  662 */       setStrokingColor(color.getRed(), color.getGreen(), color.getBlue());
/*      */     }
/*  664 */     else if (colorSpace.getType() == 6)
/*      */     {
/*  666 */       color.getColorComponents(this.colorComponents);
/*  667 */       setStrokingColor(this.colorComponents[0]);
/*      */     }
/*  669 */     else if (colorSpace.getType() == 9)
/*      */     {
/*  671 */       color.getColorComponents(this.colorComponents);
/*  672 */       setStrokingColor(this.colorComponents[0], this.colorComponents[1], this.colorComponents[2], this.colorComponents[3]);
/*      */     }
/*      */     else
/*      */     {
/*  676 */       throw new IOException("Error: unknown colorspace:" + colorSpace);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setNonStrokingColor(Color color)
/*      */     throws IOException
/*      */   {
/*  688 */     ColorSpace colorSpace = color.getColorSpace();
/*  689 */     if (colorSpace.getType() == 5)
/*      */     {
/*  691 */       setNonStrokingColor(color.getRed(), color.getGreen(), color.getBlue());
/*      */     }
/*  693 */     else if (colorSpace.getType() == 6)
/*      */     {
/*  695 */       color.getColorComponents(this.colorComponents);
/*  696 */       setNonStrokingColor(this.colorComponents[0]);
/*      */     }
/*  698 */     else if (colorSpace.getType() == 9)
/*      */     {
/*  700 */       color.getColorComponents(this.colorComponents);
/*  701 */       setNonStrokingColor(this.colorComponents[0], this.colorComponents[1], this.colorComponents[2], this.colorComponents[3]);
/*      */     }
/*      */     else
/*      */     {
/*  705 */       throw new IOException("Error: unknown colorspace:" + colorSpace);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setStrokingColor(int r, int g, int b)
/*      */     throws IOException
/*      */   {
/*  719 */     appendRawCommands(r / 255.0D);
/*  720 */     appendRawCommands(32);
/*  721 */     appendRawCommands(g / 255.0D);
/*  722 */     appendRawCommands(32);
/*  723 */     appendRawCommands(b / 255.0D);
/*  724 */     appendRawCommands(32);
/*  725 */     appendRawCommands(RG_STROKING);
/*      */   }
/*      */ 
/*      */   public void setStrokingColor(int c, int m, int y, int k)
/*      */     throws IOException
/*      */   {
/*  739 */     appendRawCommands(c / 255.0D);
/*  740 */     appendRawCommands(32);
/*  741 */     appendRawCommands(m / 255.0D);
/*  742 */     appendRawCommands(32);
/*  743 */     appendRawCommands(y / 255.0D);
/*  744 */     appendRawCommands(32);
/*  745 */     appendRawCommands(k / 255.0D);
/*  746 */     appendRawCommands(32);
/*  747 */     appendRawCommands(K_STROKING);
/*      */   }
/*      */ 
/*      */   public void setStrokingColor(double c, double m, double y, double k)
/*      */     throws IOException
/*      */   {
/*  761 */     appendRawCommands(c);
/*  762 */     appendRawCommands(32);
/*  763 */     appendRawCommands(m);
/*  764 */     appendRawCommands(32);
/*  765 */     appendRawCommands(y);
/*  766 */     appendRawCommands(32);
/*  767 */     appendRawCommands(k);
/*  768 */     appendRawCommands(32);
/*  769 */     appendRawCommands(K_STROKING);
/*      */   }
/*      */ 
/*      */   public void setStrokingColor(int g)
/*      */     throws IOException
/*      */   {
/*  780 */     appendRawCommands(g / 255.0D);
/*  781 */     appendRawCommands(32);
/*  782 */     appendRawCommands(G_STROKING);
/*      */   }
/*      */ 
/*      */   public void setStrokingColor(double g)
/*      */     throws IOException
/*      */   {
/*  793 */     appendRawCommands(g);
/*  794 */     appendRawCommands(32);
/*  795 */     appendRawCommands(G_STROKING);
/*      */   }
/*      */ 
/*      */   public void setNonStrokingColor(float[] components)
/*      */     throws IOException
/*      */   {
/*  806 */     for (int i = 0; i < components.length; i++)
/*      */     {
/*  808 */       appendRawCommands(components[i]);
/*  809 */       appendRawCommands(32);
/*      */     }
/*  811 */     if (((this.currentNonStrokingColorSpace instanceof PDSeparation)) || ((this.currentNonStrokingColorSpace instanceof PDPattern)) || ((this.currentNonStrokingColorSpace instanceof PDDeviceN)) || ((this.currentNonStrokingColorSpace instanceof PDICCBased)))
/*      */     {
/*  815 */       appendRawCommands(SET_NON_STROKING_COLOR_COMPLEX);
/*      */     }
/*      */     else
/*      */     {
/*  819 */       appendRawCommands(SET_NON_STROKING_COLOR_SIMPLE);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setNonStrokingColor(int r, int g, int b)
/*      */     throws IOException
/*      */   {
/*  833 */     appendRawCommands(r / 255.0D);
/*  834 */     appendRawCommands(32);
/*  835 */     appendRawCommands(g / 255.0D);
/*  836 */     appendRawCommands(32);
/*  837 */     appendRawCommands(b / 255.0D);
/*  838 */     appendRawCommands(32);
/*  839 */     appendRawCommands(RG_NON_STROKING);
/*      */   }
/*      */ 
/*      */   public void setNonStrokingColor(int c, int m, int y, int k)
/*      */     throws IOException
/*      */   {
/*  853 */     appendRawCommands(c / 255.0D);
/*  854 */     appendRawCommands(32);
/*  855 */     appendRawCommands(m / 255.0D);
/*  856 */     appendRawCommands(32);
/*  857 */     appendRawCommands(y / 255.0D);
/*  858 */     appendRawCommands(32);
/*  859 */     appendRawCommands(k / 255.0D);
/*  860 */     appendRawCommands(32);
/*  861 */     appendRawCommands(K_NON_STROKING);
/*      */   }
/*      */ 
/*      */   public void setNonStrokingColor(double c, double m, double y, double k)
/*      */     throws IOException
/*      */   {
/*  875 */     appendRawCommands(c);
/*  876 */     appendRawCommands(32);
/*  877 */     appendRawCommands(m);
/*  878 */     appendRawCommands(32);
/*  879 */     appendRawCommands(y);
/*  880 */     appendRawCommands(32);
/*  881 */     appendRawCommands(k);
/*  882 */     appendRawCommands(32);
/*  883 */     appendRawCommands(K_NON_STROKING);
/*      */   }
/*      */ 
/*      */   public void setNonStrokingColor(int g)
/*      */     throws IOException
/*      */   {
/*  894 */     appendRawCommands(g / 255.0D);
/*  895 */     appendRawCommands(32);
/*  896 */     appendRawCommands(G_NON_STROKING);
/*      */   }
/*      */ 
/*      */   public void setNonStrokingColor(double g)
/*      */     throws IOException
/*      */   {
/*  907 */     appendRawCommands(g);
/*  908 */     appendRawCommands(32);
/*  909 */     appendRawCommands(G_NON_STROKING);
/*      */   }
/*      */ 
/*      */   public void addRect(float x, float y, float width, float height)
/*      */     throws IOException
/*      */   {
/*  923 */     if (this.inTextMode)
/*      */     {
/*  925 */       throw new IOException("Error: addRect is not allowed within a text block.");
/*      */     }
/*  927 */     appendRawCommands(x);
/*  928 */     appendRawCommands(32);
/*  929 */     appendRawCommands(y);
/*  930 */     appendRawCommands(32);
/*  931 */     appendRawCommands(width);
/*  932 */     appendRawCommands(32);
/*  933 */     appendRawCommands(height);
/*  934 */     appendRawCommands(32);
/*  935 */     appendRawCommands(RECTANGLE);
/*      */   }
/*      */ 
/*      */   public void fillRect(float x, float y, float width, float height)
/*      */     throws IOException
/*      */   {
/*  949 */     if (this.inTextMode)
/*      */     {
/*  951 */       throw new IOException("Error: fillRect is not allowed within a text block.");
/*      */     }
/*  953 */     addRect(x, y, width, height);
/*  954 */     fill(1);
/*      */   }
/*      */ 
/*      */   public void addBezier312(float x1, float y1, float x2, float y2, float x3, float y3)
/*      */     throws IOException
/*      */   {
/*  970 */     if (this.inTextMode)
/*      */     {
/*  972 */       throw new IOException("Error: addBezier312 is not allowed within a text block.");
/*      */     }
/*  974 */     appendRawCommands(x1);
/*  975 */     appendRawCommands(32);
/*  976 */     appendRawCommands(y1);
/*  977 */     appendRawCommands(32);
/*  978 */     appendRawCommands(x2);
/*  979 */     appendRawCommands(32);
/*  980 */     appendRawCommands(y2);
/*  981 */     appendRawCommands(32);
/*  982 */     appendRawCommands(x3);
/*  983 */     appendRawCommands(32);
/*  984 */     appendRawCommands(y3);
/*  985 */     appendRawCommands(32);
/*  986 */     appendRawCommands(BEZIER_312);
/*      */   }
/*      */ 
/*      */   public void addBezier32(float x2, float y2, float x3, float y3)
/*      */     throws IOException
/*      */   {
/* 1000 */     if (this.inTextMode)
/*      */     {
/* 1002 */       throw new IOException("Error: addBezier32 is not allowed within a text block.");
/*      */     }
/* 1004 */     appendRawCommands(x2);
/* 1005 */     appendRawCommands(32);
/* 1006 */     appendRawCommands(y2);
/* 1007 */     appendRawCommands(32);
/* 1008 */     appendRawCommands(x3);
/* 1009 */     appendRawCommands(32);
/* 1010 */     appendRawCommands(y3);
/* 1011 */     appendRawCommands(32);
/* 1012 */     appendRawCommands(BEZIER_32);
/*      */   }
/*      */ 
/*      */   public void addBezier31(float x1, float y1, float x3, float y3)
/*      */     throws IOException
/*      */   {
/* 1026 */     if (this.inTextMode)
/*      */     {
/* 1028 */       throw new IOException("Error: addBezier31 is not allowed within a text block.");
/*      */     }
/* 1030 */     appendRawCommands(x1);
/* 1031 */     appendRawCommands(32);
/* 1032 */     appendRawCommands(y1);
/* 1033 */     appendRawCommands(32);
/* 1034 */     appendRawCommands(x3);
/* 1035 */     appendRawCommands(32);
/* 1036 */     appendRawCommands(y3);
/* 1037 */     appendRawCommands(32);
/* 1038 */     appendRawCommands(BEZIER_313);
/*      */   }
/*      */ 
/*      */   public void moveTo(float x, float y)
/*      */     throws IOException
/*      */   {
/* 1050 */     if (this.inTextMode)
/*      */     {
/* 1052 */       throw new IOException("Error: moveTo is not allowed within a text block.");
/*      */     }
/* 1054 */     appendRawCommands(x);
/* 1055 */     appendRawCommands(32);
/* 1056 */     appendRawCommands(y);
/* 1057 */     appendRawCommands(32);
/* 1058 */     appendRawCommands(MOVE_TO);
/*      */   }
/*      */ 
/*      */   public void lineTo(float x, float y)
/*      */     throws IOException
/*      */   {
/* 1070 */     if (this.inTextMode)
/*      */     {
/* 1072 */       throw new IOException("Error: lineTo is not allowed within a text block.");
/*      */     }
/* 1074 */     appendRawCommands(x);
/* 1075 */     appendRawCommands(32);
/* 1076 */     appendRawCommands(y);
/* 1077 */     appendRawCommands(32);
/* 1078 */     appendRawCommands(LINE_TO);
/*      */   }
/*      */ 
/*      */   public void addLine(float xStart, float yStart, float xEnd, float yEnd)
/*      */     throws IOException
/*      */   {
/* 1092 */     if (this.inTextMode)
/*      */     {
/* 1094 */       throw new IOException("Error: addLine is not allowed within a text block.");
/*      */     }
/*      */ 
/* 1097 */     moveTo(xStart, yStart);
/*      */ 
/* 1099 */     lineTo(xEnd, yEnd);
/*      */   }
/*      */ 
/*      */   public void drawLine(float xStart, float yStart, float xEnd, float yEnd)
/*      */     throws IOException
/*      */   {
/* 1113 */     if (this.inTextMode)
/*      */     {
/* 1115 */       throw new IOException("Error: drawLine is not allowed within a text block.");
/*      */     }
/* 1117 */     addLine(xStart, yStart, xEnd, yEnd);
/*      */ 
/* 1119 */     stroke();
/*      */   }
/*      */ 
/*      */   public void addPolygon(float[] x, float[] y)
/*      */     throws IOException
/*      */   {
/* 1130 */     if (this.inTextMode)
/*      */     {
/* 1132 */       throw new IOException("Error: addPolygon is not allowed within a text block.");
/*      */     }
/* 1134 */     if (x.length != y.length)
/*      */     {
/* 1136 */       throw new IOException("Error: some points are missing coordinate");
/*      */     }
/* 1138 */     for (int i = 0; i < x.length; i++)
/*      */     {
/* 1140 */       if (i == 0)
/*      */       {
/* 1142 */         moveTo(x[i], y[i]);
/*      */       }
/*      */       else
/*      */       {
/* 1146 */         lineTo(x[i], y[i]);
/*      */       }
/*      */     }
/* 1149 */     closeSubPath();
/*      */   }
/*      */ 
/*      */   public void drawPolygon(float[] x, float[] y)
/*      */     throws IOException
/*      */   {
/* 1160 */     if (this.inTextMode)
/*      */     {
/* 1162 */       throw new IOException("Error: drawPolygon is not allowed within a text block.");
/*      */     }
/* 1164 */     addPolygon(x, y);
/* 1165 */     stroke();
/*      */   }
/*      */ 
/*      */   public void fillPolygon(float[] x, float[] y)
/*      */     throws IOException
/*      */   {
/* 1176 */     if (this.inTextMode)
/*      */     {
/* 1178 */       throw new IOException("Error: fillPolygon is not allowed within a text block.");
/*      */     }
/* 1180 */     addPolygon(x, y);
/* 1181 */     fill(1);
/*      */   }
/*      */ 
/*      */   public void stroke()
/*      */     throws IOException
/*      */   {
/* 1191 */     if (this.inTextMode)
/*      */     {
/* 1193 */       throw new IOException("Error: stroke is not allowed within a text block.");
/*      */     }
/* 1195 */     appendRawCommands(STROKE);
/*      */   }
/*      */ 
/*      */   public void closeAndStroke()
/*      */     throws IOException
/*      */   {
/* 1205 */     if (this.inTextMode)
/*      */     {
/* 1207 */       throw new IOException("Error: closeAndStroke is not allowed within a text block.");
/*      */     }
/* 1209 */     appendRawCommands(CLOSE_STROKE);
/*      */   }
/*      */ 
/*      */   public void fill(int windingRule)
/*      */     throws IOException
/*      */   {
/* 1221 */     if (this.inTextMode)
/*      */     {
/* 1223 */       throw new IOException("Error: fill is not allowed within a text block.");
/*      */     }
/* 1225 */     if (windingRule == 1)
/*      */     {
/* 1227 */       appendRawCommands(FILL_NON_ZERO);
/*      */     }
/* 1229 */     else if (windingRule == 0)
/*      */     {
/* 1231 */       appendRawCommands(FILL_EVEN_ODD);
/*      */     }
/*      */     else
/*      */     {
/* 1235 */       throw new IOException("Error: unknown value for winding rule");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void closeSubPath()
/*      */     throws IOException
/*      */   {
/* 1247 */     if (this.inTextMode)
/*      */     {
/* 1249 */       throw new IOException("Error: closeSubPath is not allowed within a text block.");
/*      */     }
/* 1251 */     appendRawCommands(CLOSE_SUBPATH);
/*      */   }
/*      */ 
/*      */   public void clipPath(int windingRule)
/*      */     throws IOException
/*      */   {
/* 1263 */     if (this.inTextMode)
/*      */     {
/* 1265 */       throw new IOException("Error: clipPath is not allowed within a text block.");
/*      */     }
/* 1267 */     if (windingRule == 1)
/*      */     {
/* 1269 */       appendRawCommands(CLIP_PATH_NON_ZERO);
/* 1270 */       appendRawCommands(NOP);
/*      */     }
/* 1272 */     else if (windingRule == 0)
/*      */     {
/* 1274 */       appendRawCommands(CLIP_PATH_EVEN_ODD);
/* 1275 */       appendRawCommands(NOP);
/*      */     }
/*      */     else
/*      */     {
/* 1279 */       throw new IOException("Error: unknown value for winding rule");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setLineWidth(float lineWidth)
/*      */     throws IOException
/*      */   {
/* 1291 */     if (this.inTextMode)
/*      */     {
/* 1293 */       throw new IOException("Error: setLineWidth is not allowed within a text block.");
/*      */     }
/* 1295 */     appendRawCommands(lineWidth);
/* 1296 */     appendRawCommands(32);
/* 1297 */     appendRawCommands(LINE_WIDTH);
/*      */   }
/*      */ 
/*      */   public void setLineJoinStyle(int lineJoinStyle)
/*      */     throws IOException
/*      */   {
/* 1307 */     if (this.inTextMode)
/*      */     {
/* 1309 */       throw new IOException("Error: setLineJoinStyle is not allowed within a text block.");
/*      */     }
/* 1311 */     if ((lineJoinStyle >= 0) && (lineJoinStyle <= 2))
/*      */     {
/* 1313 */       appendRawCommands(Integer.toString(lineJoinStyle));
/* 1314 */       appendRawCommands(32);
/* 1315 */       appendRawCommands(LINE_JOIN_STYLE);
/*      */     }
/*      */     else
/*      */     {
/* 1319 */       throw new IOException("Error: unknown value for line join style");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setLineCapStyle(int lineCapStyle)
/*      */     throws IOException
/*      */   {
/* 1330 */     if (this.inTextMode)
/*      */     {
/* 1332 */       throw new IOException("Error: setLineCapStyle is not allowed within a text block.");
/*      */     }
/* 1334 */     if ((lineCapStyle >= 0) && (lineCapStyle <= 2))
/*      */     {
/* 1336 */       appendRawCommands(Integer.toString(lineCapStyle));
/* 1337 */       appendRawCommands(32);
/* 1338 */       appendRawCommands(LINE_CAP_STYLE);
/*      */     }
/*      */     else
/*      */     {
/* 1342 */       throw new IOException("Error: unknown value for line cap style");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setLineDashPattern(float[] pattern, float phase)
/*      */     throws IOException
/*      */   {
/* 1354 */     if (this.inTextMode)
/*      */     {
/* 1356 */       throw new IOException("Error: setLineDashPattern is not allowed within a text block.");
/*      */     }
/* 1358 */     appendRawCommands(OPENING_BRACKET);
/* 1359 */     for (float value : pattern)
/*      */     {
/* 1361 */       appendRawCommands(value);
/* 1362 */       appendRawCommands(32);
/*      */     }
/* 1364 */     appendRawCommands(CLOSING_BRACKET);
/* 1365 */     appendRawCommands(32);
/* 1366 */     appendRawCommands(phase);
/* 1367 */     appendRawCommands(32);
/* 1368 */     appendRawCommands(LINE_DASH_PATTERN);
/*      */   }
/*      */ 
/*      */   public void beginMarkedContentSequence(COSName tag)
/*      */     throws IOException
/*      */   {
/* 1378 */     appendCOSName(tag);
/* 1379 */     appendRawCommands(32);
/* 1380 */     appendRawCommands(BMC);
/*      */   }
/*      */ 
/*      */   public void beginMarkedContentSequence(COSName tag, COSName propsName)
/*      */     throws IOException
/*      */   {
/* 1392 */     appendCOSName(tag);
/* 1393 */     appendRawCommands(32);
/* 1394 */     appendCOSName(propsName);
/* 1395 */     appendRawCommands(32);
/* 1396 */     appendRawCommands(BDC);
/*      */   }
/*      */ 
/*      */   public void endMarkedContentSequence()
/*      */     throws IOException
/*      */   {
/* 1405 */     appendRawCommands(EMC);
/*      */   }
/*      */ 
/*      */   public void saveGraphicsState()
/*      */     throws IOException
/*      */   {
/* 1414 */     appendRawCommands(SAVE_GRAPHICS_STATE);
/*      */   }
/*      */ 
/*      */   public void restoreGraphicsState()
/*      */     throws IOException
/*      */   {
/* 1423 */     appendRawCommands(RESTORE_GRAPHICS_STATE);
/*      */   }
/*      */ 
/*      */   public void appendRawCommands(String commands)
/*      */     throws IOException
/*      */   {
/* 1434 */     appendRawCommands(commands.getBytes("ISO-8859-1"));
/*      */   }
/*      */ 
/*      */   public void appendRawCommands(byte[] commands)
/*      */     throws IOException
/*      */   {
/* 1445 */     this.output.write(commands);
/*      */   }
/*      */ 
/*      */   public void appendRawCommands(int data)
/*      */     throws IOException
/*      */   {
/* 1457 */     this.output.write(data);
/*      */   }
/*      */ 
/*      */   public void appendRawCommands(double data)
/*      */     throws IOException
/*      */   {
/* 1469 */     appendRawCommands(this.formatDecimal.format(data));
/*      */   }
/*      */ 
/*      */   public void appendRawCommands(float data)
/*      */     throws IOException
/*      */   {
/* 1481 */     appendRawCommands(this.formatDecimal.format(data));
/*      */   }
/*      */ 
/*      */   public void appendCOSName(COSName name)
/*      */     throws IOException
/*      */   {
/* 1491 */     name.writePDF(this.output);
/*      */   }
/*      */ 
/*      */   private void appendMatrix(AffineTransform transform) throws IOException
/*      */   {
/* 1496 */     double[] values = new double[6];
/* 1497 */     transform.getMatrix(values);
/* 1498 */     for (double v : values)
/*      */     {
/* 1500 */       appendRawCommands(v);
/* 1501 */       appendRawCommands(32);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void close()
/*      */     throws IOException
/*      */   {
/* 1512 */     this.output.close();
/* 1513 */     this.currentNonStrokingColorSpace = null;
/* 1514 */     this.currentStrokingColorSpace = null;
/* 1515 */     this.resources = null;
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.edit.PDPageContentStream
 * JD-Core Version:    0.6.2
 */