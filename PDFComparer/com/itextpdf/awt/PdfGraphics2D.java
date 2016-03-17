/*      */ package com.itextpdf.awt;
/*      */ 
/*      */ import com.itextpdf.awt.geom.PolylineShape;
/*      */ import com.itextpdf.text.BaseColor;
/*      */ import com.itextpdf.text.pdf.BaseFont;
/*      */ import com.itextpdf.text.pdf.ByteBuffer;
/*      */ import com.itextpdf.text.pdf.PdfAction;
/*      */ import com.itextpdf.text.pdf.PdfContentByte;
/*      */ import com.itextpdf.text.pdf.PdfGState;
/*      */ import com.itextpdf.text.pdf.PdfPatternPainter;
/*      */ import com.itextpdf.text.pdf.PdfShading;
/*      */ import com.itextpdf.text.pdf.PdfShadingPattern;
/*      */ import java.awt.AlphaComposite;
/*      */ import java.awt.BasicStroke;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Composite;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.GradientPaint;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.GraphicsConfiguration;
/*      */ import java.awt.MediaTracker;
/*      */ import java.awt.Paint;
/*      */ import java.awt.Polygon;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.RenderingHints;
/*      */ import java.awt.RenderingHints.Key;
/*      */ import java.awt.Shape;
/*      */ import java.awt.Stroke;
/*      */ import java.awt.TexturePaint;
/*      */ import java.awt.font.FontRenderContext;
/*      */ import java.awt.font.GlyphVector;
/*      */ import java.awt.font.TextAttribute;
/*      */ import java.awt.geom.AffineTransform;
/*      */ import java.awt.geom.Arc2D;
/*      */ import java.awt.geom.Arc2D.Double;
/*      */ import java.awt.geom.Area;
/*      */ import java.awt.geom.Ellipse2D;
/*      */ import java.awt.geom.Ellipse2D.Float;
/*      */ import java.awt.geom.Line2D;
/*      */ import java.awt.geom.Line2D.Double;
/*      */ import java.awt.geom.NoninvertibleTransformException;
/*      */ import java.awt.geom.PathIterator;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.awt.geom.Rectangle2D.Double;
/*      */ import java.awt.geom.Rectangle2D.Float;
/*      */ import java.awt.geom.RoundRectangle2D;
/*      */ import java.awt.geom.RoundRectangle2D.Double;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.BufferedImageOp;
/*      */ import java.awt.image.ColorModel;
/*      */ import java.awt.image.ImageObserver;
/*      */ import java.awt.image.RenderedImage;
/*      */ import java.awt.image.WritableRaster;
/*      */ import java.awt.image.renderable.RenderableImage;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.text.AttributedCharacterIterator;
/*      */ import java.text.AttributedCharacterIterator.Attribute;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import javax.imageio.IIOImage;
/*      */ import javax.imageio.ImageIO;
/*      */ import javax.imageio.ImageWriteParam;
/*      */ import javax.imageio.ImageWriter;
/*      */ import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
/*      */ import javax.imageio.stream.ImageOutputStream;
/*      */ 
/*      */ public class PdfGraphics2D extends Graphics2D
/*      */ {
/*      */   private static final int FILL = 1;
/*      */   private static final int STROKE = 2;
/*      */   private static final int CLIP = 3;
/*  120 */   private BasicStroke strokeOne = new BasicStroke(1.0F);
/*      */ 
/*  122 */   private static final AffineTransform IDENTITY = new AffineTransform();
/*      */   protected Font font;
/*      */   protected BaseFont baseFont;
/*      */   protected float fontSize;
/*      */   protected AffineTransform transform;
/*      */   protected Paint paint;
/*      */   protected Color background;
/*      */   protected float width;
/*      */   protected float height;
/*      */   protected Area clip;
/*  135 */   protected RenderingHints rhints = new RenderingHints(null);
/*      */   protected Stroke stroke;
/*      */   protected Stroke originalStroke;
/*      */   protected PdfContentByte cb;
/*      */   protected HashMap<String, BaseFont> baseFonts;
/*  145 */   protected boolean disposeCalled = false;
/*      */   protected FontMapper fontMapper;
/*      */   private ArrayList<Kid> kids;
/*  159 */   private boolean kid = false;
/*      */   private Graphics2D dg2;
/*  163 */   private boolean onlyShapes = false;
/*      */   private Stroke oldStroke;
/*      */   private Paint paintFill;
/*      */   private Paint paintStroke;
/*      */   private MediaTracker mediaTracker;
/*      */   protected boolean underline;
/*      */   protected boolean strikethrough;
/*      */   protected PdfGState[] fillGState;
/*      */   protected PdfGState[] strokeGState;
/*  179 */   protected int currentFillGState = 255;
/*  180 */   protected int currentStrokeGState = 255;
/*      */   public static final int AFM_DIVISOR = 1000;
/*  184 */   private boolean convertImagesToJPEG = false;
/*  185 */   private float jpegQuality = 0.95F;
/*      */   private float alpha;
/*      */   private Composite composite;
/*      */   private Paint realPaint;
/*      */ 
/*      */   private Graphics2D getDG2()
/*      */   {
/*  202 */     if (this.dg2 == null) {
/*  203 */       this.dg2 = new BufferedImage(2, 2, 1).createGraphics();
/*  204 */       this.dg2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
/*  205 */       setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
/*  206 */       setRenderingHint(HyperLinkKey.KEY_INSTANCE, HyperLinkKey.VALUE_HYPERLINKKEY_OFF);
/*      */     }
/*  208 */     return this.dg2;
/*      */   }
/*      */   private PdfGraphics2D() {
/*      */   }
/*      */ 
/*      */   public PdfGraphics2D(PdfContentByte cb, float width, float height) {
/*  214 */     this(cb, width, height, null, false, false, 0.0F);
/*      */   }
/*      */ 
/*      */   public PdfGraphics2D(PdfContentByte cb, float width, float height, FontMapper fontMapper) {
/*  218 */     this(cb, width, height, fontMapper, false, false, 0.0F);
/*      */   }
/*      */ 
/*      */   public PdfGraphics2D(PdfContentByte cb, float width, float height, boolean onlyShapes) {
/*  222 */     this(cb, width, height, null, onlyShapes, false, 0.0F);
/*      */   }
/*      */ 
/*      */   public PdfGraphics2D(PdfContentByte cb, float width, float height, FontMapper fontMapper, boolean onlyShapes, boolean convertImagesToJPEG, float quality)
/*      */   {
/*  230 */     this.fillGState = new PdfGState[256];
/*  231 */     this.strokeGState = new PdfGState[256];
/*  232 */     this.convertImagesToJPEG = convertImagesToJPEG;
/*  233 */     this.jpegQuality = quality;
/*  234 */     this.onlyShapes = onlyShapes;
/*  235 */     this.transform = new AffineTransform();
/*  236 */     this.baseFonts = new HashMap();
/*  237 */     if (!onlyShapes) {
/*  238 */       this.fontMapper = fontMapper;
/*  239 */       if (this.fontMapper == null)
/*  240 */         this.fontMapper = new DefaultFontMapper();
/*      */     }
/*  242 */     this.paint = Color.black;
/*  243 */     this.background = Color.white;
/*  244 */     setFont(new Font("sanserif", 0, 12));
/*  245 */     this.cb = cb;
/*  246 */     cb.saveState();
/*  247 */     this.width = width;
/*  248 */     this.height = height;
/*  249 */     this.clip = new Area(new Rectangle2D.Float(0.0F, 0.0F, width, height));
/*  250 */     clip(this.clip);
/*  251 */     this.originalStroke = (this.stroke = this.oldStroke = this.strokeOne);
/*  252 */     setStrokeDiff(this.stroke, null);
/*  253 */     cb.saveState();
/*      */   }
/*      */ 
/*      */   public void draw(Shape s)
/*      */   {
/*  261 */     followPath(s, 2);
/*      */   }
/*      */ 
/*      */   public boolean drawImage(java.awt.Image img, AffineTransform xform, ImageObserver obs)
/*      */   {
/*  269 */     return drawImage(img, null, xform, null, obs);
/*      */   }
/*      */ 
/*      */   public void drawImage(BufferedImage img, BufferedImageOp op, int x, int y)
/*      */   {
/*  277 */     BufferedImage result = img;
/*  278 */     if (op != null) {
/*  279 */       result = op.createCompatibleDestImage(img, img.getColorModel());
/*  280 */       result = op.filter(img, result);
/*      */     }
/*  282 */     drawImage(result, x, y, null);
/*      */   }
/*      */ 
/*      */   public void drawRenderedImage(RenderedImage img, AffineTransform xform)
/*      */   {
/*  290 */     BufferedImage image = null;
/*  291 */     if ((img instanceof BufferedImage)) {
/*  292 */       image = (BufferedImage)img;
/*      */     } else {
/*  294 */       ColorModel cm = img.getColorModel();
/*  295 */       int width = img.getWidth();
/*  296 */       int height = img.getHeight();
/*  297 */       WritableRaster raster = cm.createCompatibleWritableRaster(width, height);
/*  298 */       boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
/*  299 */       Hashtable properties = new Hashtable();
/*  300 */       String[] keys = img.getPropertyNames();
/*  301 */       if (keys != null) {
/*  302 */         for (String key : keys) {
/*  303 */           properties.put(key, img.getProperty(key));
/*      */         }
/*      */       }
/*  306 */       BufferedImage result = new BufferedImage(cm, raster, isAlphaPremultiplied, properties);
/*  307 */       img.copyData(raster);
/*  308 */       image = result;
/*      */     }
/*  310 */     drawImage(image, xform, null);
/*      */   }
/*      */ 
/*      */   public void drawRenderableImage(RenderableImage img, AffineTransform xform)
/*      */   {
/*  318 */     drawRenderedImage(img.createDefaultRendering(), xform);
/*      */   }
/*      */ 
/*      */   public void drawString(String s, int x, int y)
/*      */   {
/*  326 */     drawString(s, x, y);
/*      */   }
/*      */ 
/*      */   public static double asPoints(double d, int i)
/*      */   {
/*  336 */     return d * i / 1000.0D;
/*      */   }
/*      */ 
/*      */   protected void doAttributes(AttributedCharacterIterator iter)
/*      */   {
/*  345 */     this.underline = false;
/*  346 */     this.strikethrough = false;
/*  347 */     for (AttributedCharacterIterator.Attribute attribute : iter.getAttributes().keySet())
/*  348 */       if ((attribute instanceof TextAttribute))
/*      */       {
/*  350 */         TextAttribute textattribute = (TextAttribute)attribute;
/*  351 */         if (textattribute.equals(TextAttribute.FONT)) {
/*  352 */           Font font = (Font)iter.getAttributes().get(textattribute);
/*  353 */           setFont(font);
/*      */         }
/*  355 */         else if (textattribute.equals(TextAttribute.UNDERLINE)) {
/*  356 */           if (iter.getAttributes().get(textattribute) == TextAttribute.UNDERLINE_ON)
/*  357 */             this.underline = true;
/*      */         }
/*  359 */         else if (textattribute.equals(TextAttribute.STRIKETHROUGH)) {
/*  360 */           if (iter.getAttributes().get(textattribute) == TextAttribute.STRIKETHROUGH_ON)
/*  361 */             this.strikethrough = true;
/*      */         }
/*  363 */         else if (textattribute.equals(TextAttribute.SIZE)) {
/*  364 */           Object obj = iter.getAttributes().get(textattribute);
/*  365 */           if ((obj instanceof Integer)) {
/*  366 */             int i = ((Integer)obj).intValue();
/*  367 */             setFont(getFont().deriveFont(getFont().getStyle(), i));
/*      */           }
/*  369 */           else if ((obj instanceof Float)) {
/*  370 */             float f = ((Float)obj).floatValue();
/*  371 */             setFont(getFont().deriveFont(getFont().getStyle(), f));
/*      */           }
/*      */         }
/*  374 */         else if (textattribute.equals(TextAttribute.FOREGROUND)) {
/*  375 */           setColor((Color)iter.getAttributes().get(textattribute));
/*      */         }
/*  377 */         else if (textattribute.equals(TextAttribute.FAMILY)) {
/*  378 */           Font font = getFont();
/*  379 */           Map fontAttributes = font.getAttributes();
/*  380 */           fontAttributes.put(TextAttribute.FAMILY, iter.getAttributes().get(textattribute));
/*  381 */           setFont(font.deriveFont(fontAttributes));
/*      */         }
/*  383 */         else if (textattribute.equals(TextAttribute.POSTURE)) {
/*  384 */           Font font = getFont();
/*  385 */           Map fontAttributes = font.getAttributes();
/*  386 */           fontAttributes.put(TextAttribute.POSTURE, iter.getAttributes().get(textattribute));
/*  387 */           setFont(font.deriveFont(fontAttributes));
/*      */         }
/*  389 */         else if (textattribute.equals(TextAttribute.WEIGHT)) {
/*  390 */           Font font = getFont();
/*  391 */           Map fontAttributes = font.getAttributes();
/*  392 */           fontAttributes.put(TextAttribute.WEIGHT, iter.getAttributes().get(textattribute));
/*  393 */           setFont(font.deriveFont(fontAttributes));
/*      */         }
/*      */       }
/*      */   }
/*      */ 
/*      */   public void drawString(String s, float x, float y)
/*      */   {
/*  403 */     if (s.length() == 0)
/*  404 */       return;
/*  405 */     setFillPaint();
/*  406 */     if (this.onlyShapes) {
/*  407 */       drawGlyphVector(this.font.layoutGlyphVector(getFontRenderContext(), s.toCharArray(), 0, s.length(), 0), x, y);
/*      */     }
/*      */     else
/*      */     {
/*  412 */       boolean restoreTextRenderingMode = false;
/*  413 */       AffineTransform at = getTransform();
/*  414 */       AffineTransform at2 = getTransform();
/*  415 */       at2.translate(x, y);
/*  416 */       at2.concatenate(this.font.getTransform());
/*  417 */       setTransform(at2);
/*  418 */       AffineTransform inverse = normalizeMatrix();
/*  419 */       AffineTransform flipper = AffineTransform.getScaleInstance(1.0D, -1.0D);
/*  420 */       inverse.concatenate(flipper);
/*  421 */       double[] mx = new double[6];
/*  422 */       inverse.getMatrix(mx);
/*  423 */       this.cb.beginText();
/*  424 */       this.cb.setFontAndSize(this.baseFont, this.fontSize);
/*      */ 
/*  431 */       if ((this.font.isItalic()) && (this.font.getFontName().equals(this.font.getName()))) {
/*  432 */         float angle = this.baseFont.getFontDescriptor(4, 1000.0F);
/*  433 */         float angle2 = this.font.getItalicAngle();
/*      */ 
/*  436 */         if (angle2 == 0.0F)
/*      */         {
/*  440 */           angle2 = 15.0F;
/*      */         }
/*      */         else
/*      */         {
/*  444 */           angle2 = -angle2;
/*      */         }
/*  446 */         if (angle == 0.0F) {
/*  447 */           mx[2] = (angle2 / 100.0F);
/*      */         }
/*      */       }
/*  450 */       this.cb.setTextMatrix((float)mx[0], (float)mx[1], (float)mx[2], (float)mx[3], (float)mx[4], (float)mx[5]);
/*  451 */       Float fontTextAttributeWidth = (Float)this.font.getAttributes().get(TextAttribute.WIDTH);
/*  452 */       fontTextAttributeWidth = fontTextAttributeWidth == null ? TextAttribute.WIDTH_REGULAR : fontTextAttributeWidth;
/*      */ 
/*  455 */       if (!TextAttribute.WIDTH_REGULAR.equals(fontTextAttributeWidth)) {
/*  456 */         this.cb.setHorizontalScaling(100.0F / fontTextAttributeWidth.floatValue());
/*      */       }
/*      */ 
/*  460 */       if (this.baseFont.getPostscriptFontName().toLowerCase().indexOf("bold") < 0)
/*      */       {
/*  463 */         Float weight = (Float)this.font.getAttributes().get(TextAttribute.WEIGHT);
/*  464 */         if (weight == null) {
/*  465 */           weight = this.font.isBold() ? TextAttribute.WEIGHT_BOLD : TextAttribute.WEIGHT_REGULAR;
/*      */         }
/*      */ 
/*  468 */         if (((this.font.isBold()) || (weight.floatValue() >= TextAttribute.WEIGHT_SEMIBOLD.floatValue())) && (this.font.getFontName().equals(this.font.getName())))
/*      */         {
/*  471 */           float strokeWidth = this.font.getSize2D() * (weight.floatValue() - TextAttribute.WEIGHT_REGULAR.floatValue()) / 30.0F;
/*  472 */           if ((strokeWidth != 1.0F) && 
/*  473 */             ((this.realPaint instanceof Color))) {
/*  474 */             this.cb.setTextRenderingMode(2);
/*  475 */             this.cb.setLineWidth(strokeWidth);
/*  476 */             Color color = (Color)this.realPaint;
/*  477 */             int alpha = color.getAlpha();
/*  478 */             if (alpha != this.currentStrokeGState) {
/*  479 */               this.currentStrokeGState = alpha;
/*  480 */               PdfGState gs = this.strokeGState[alpha];
/*  481 */               if (gs == null) {
/*  482 */                 gs = new PdfGState();
/*  483 */                 gs.setStrokeOpacity(alpha / 255.0F);
/*  484 */                 this.strokeGState[alpha] = gs;
/*      */               }
/*  486 */               this.cb.setGState(gs);
/*      */             }
/*  488 */             this.cb.setColorStroke(new BaseColor(color.getRGB()));
/*  489 */             restoreTextRenderingMode = true;
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  495 */       double width = 0.0D;
/*  496 */       if (this.font.getSize2D() > 0.0F) {
/*  497 */         float scale = 1000.0F / this.font.getSize2D();
/*  498 */         Font derivedFont = this.font.deriveFont(AffineTransform.getScaleInstance(scale, scale));
/*  499 */         width = derivedFont.getStringBounds(s, getFontRenderContext()).getWidth();
/*  500 */         if (derivedFont.isTransformed()) {
/*  501 */           width /= scale;
/*      */         }
/*      */       }
/*  504 */       Object url = getRenderingHint(HyperLinkKey.KEY_INSTANCE);
/*  505 */       if ((url != null) && (!url.equals(HyperLinkKey.VALUE_HYPERLINKKEY_OFF)))
/*      */       {
/*  507 */         float scale = 1000.0F / this.font.getSize2D();
/*  508 */         Font derivedFont = this.font.deriveFont(AffineTransform.getScaleInstance(scale, scale));
/*  509 */         double height = derivedFont.getStringBounds(s, getFontRenderContext()).getHeight();
/*  510 */         if (derivedFont.isTransformed())
/*  511 */           height /= scale;
/*  512 */         double leftX = this.cb.getXTLM();
/*  513 */         double leftY = this.cb.getYTLM();
/*  514 */         PdfAction action = new PdfAction(url.toString());
/*  515 */         this.cb.setAction(action, (float)leftX, (float)leftY, (float)(leftX + width), (float)(leftY + height));
/*      */       }
/*  517 */       if (s.length() > 1) {
/*  518 */         float adv = ((float)width - this.baseFont.getWidthPoint(s, this.fontSize)) / (s.length() - 1);
/*  519 */         this.cb.setCharacterSpacing(adv);
/*      */       }
/*  521 */       this.cb.showText(s);
/*  522 */       if (s.length() > 1) {
/*  523 */         this.cb.setCharacterSpacing(0.0F);
/*      */       }
/*  525 */       if (!TextAttribute.WIDTH_REGULAR.equals(fontTextAttributeWidth)) {
/*  526 */         this.cb.setHorizontalScaling(100.0F);
/*      */       }
/*      */ 
/*  529 */       if (restoreTextRenderingMode) {
/*  530 */         this.cb.setTextRenderingMode(0);
/*      */       }
/*      */ 
/*  533 */       this.cb.endText();
/*  534 */       setTransform(at);
/*  535 */       if (this.underline)
/*      */       {
/*  538 */         int UnderlineThickness = 50;
/*      */ 
/*  540 */         double d = asPoints(UnderlineThickness, (int)this.fontSize);
/*  541 */         Stroke savedStroke = this.originalStroke;
/*  542 */         setStroke(new BasicStroke((float)d));
/*  543 */         y = (float)(y + asPoints(UnderlineThickness, (int)this.fontSize));
/*  544 */         Line2D line = new Line2D.Double(x, y, width + x, y);
/*  545 */         draw(line);
/*  546 */         setStroke(savedStroke);
/*      */       }
/*  548 */       if (this.strikethrough)
/*      */       {
/*  550 */         int StrikethroughThickness = 50;
/*  551 */         int StrikethroughPosition = 350;
/*      */ 
/*  553 */         double d = asPoints(StrikethroughThickness, (int)this.fontSize);
/*  554 */         double p = asPoints(StrikethroughPosition, (int)this.fontSize);
/*  555 */         Stroke savedStroke = this.originalStroke;
/*  556 */         setStroke(new BasicStroke((float)d));
/*  557 */         y = (float)(y + asPoints(StrikethroughThickness, (int)this.fontSize));
/*  558 */         Line2D line = new Line2D.Double(x, y - p, width + x, y - p);
/*  559 */         draw(line);
/*  560 */         setStroke(savedStroke);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void drawString(AttributedCharacterIterator iterator, int x, int y)
/*      */   {
/*  570 */     drawString(iterator, x, y);
/*      */   }
/*      */ 
/*      */   public void drawString(AttributedCharacterIterator iter, float x, float y)
/*      */   {
/*  585 */     StringBuffer stringbuffer = new StringBuffer(iter.getEndIndex());
/*  586 */     for (char c = iter.first(); c != 65535; c = iter.next())
/*      */     {
/*  588 */       if (iter.getIndex() == iter.getRunStart())
/*      */       {
/*  590 */         if (stringbuffer.length() > 0)
/*      */         {
/*  592 */           drawString(stringbuffer.toString(), x, y);
/*  593 */           FontMetrics fontmetrics = getFontMetrics();
/*  594 */           x = (float)(x + fontmetrics.getStringBounds(stringbuffer.toString(), this).getWidth());
/*  595 */           stringbuffer.delete(0, stringbuffer.length());
/*      */         }
/*  597 */         doAttributes(iter);
/*      */       }
/*  599 */       stringbuffer.append(c);
/*      */     }
/*      */ 
/*  602 */     drawString(stringbuffer.toString(), x, y);
/*  603 */     this.underline = false;
/*  604 */     this.strikethrough = false;
/*      */   }
/*      */ 
/*      */   public void drawGlyphVector(GlyphVector g, float x, float y)
/*      */   {
/*  612 */     Shape s = g.getOutline(x, y);
/*  613 */     fill(s);
/*      */   }
/*      */ 
/*      */   public void fill(Shape s)
/*      */   {
/*  621 */     followPath(s, 1);
/*      */   }
/*      */ 
/*      */   public boolean hit(Rectangle rect, Shape s, boolean onStroke)
/*      */   {
/*  629 */     if (onStroke) {
/*  630 */       s = this.stroke.createStrokedShape(s);
/*      */     }
/*  632 */     s = this.transform.createTransformedShape(s);
/*  633 */     Area area = new Area(s);
/*  634 */     if (this.clip != null)
/*  635 */       area.intersect(this.clip);
/*  636 */     return area.intersects(rect.x, rect.y, rect.width, rect.height);
/*      */   }
/*      */ 
/*      */   public GraphicsConfiguration getDeviceConfiguration()
/*      */   {
/*  644 */     return getDG2().getDeviceConfiguration();
/*      */   }
/*      */ 
/*      */   public void setComposite(Composite comp)
/*      */   {
/*  654 */     if ((comp instanceof AlphaComposite))
/*      */     {
/*  656 */       AlphaComposite composite = (AlphaComposite)comp;
/*      */ 
/*  658 */       if (composite.getRule() == 3)
/*      */       {
/*  660 */         this.alpha = composite.getAlpha();
/*  661 */         this.composite = composite;
/*      */ 
/*  663 */         if ((this.realPaint != null) && ((this.realPaint instanceof Color)))
/*      */         {
/*  665 */           Color c = (Color)this.realPaint;
/*  666 */           this.paint = new Color(c.getRed(), c.getGreen(), c.getBlue(), (int)(c.getAlpha() * this.alpha));
/*      */         }
/*      */ 
/*  669 */         return;
/*      */       }
/*      */     }
/*      */ 
/*  673 */     this.composite = comp;
/*  674 */     this.alpha = 1.0F;
/*      */   }
/*      */ 
/*      */   public void setPaint(Paint paint)
/*      */   {
/*  684 */     if (paint == null)
/*  685 */       return;
/*  686 */     this.paint = paint;
/*  687 */     this.realPaint = paint;
/*      */ 
/*  689 */     if (((this.composite instanceof AlphaComposite)) && ((paint instanceof Color)))
/*      */     {
/*  691 */       AlphaComposite co = (AlphaComposite)this.composite;
/*      */ 
/*  693 */       if (co.getRule() == 3) {
/*  694 */         Color c = (Color)paint;
/*  695 */         this.paint = new Color(c.getRed(), c.getGreen(), c.getBlue(), (int)(c.getAlpha() * this.alpha));
/*  696 */         this.realPaint = paint;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private Stroke transformStroke(Stroke stroke)
/*      */   {
/*  703 */     if (!(stroke instanceof BasicStroke))
/*  704 */       return stroke;
/*  705 */     BasicStroke st = (BasicStroke)stroke;
/*  706 */     float scale = (float)Math.sqrt(Math.abs(this.transform.getDeterminant()));
/*  707 */     float[] dash = st.getDashArray();
/*  708 */     if (dash != null) {
/*  709 */       for (int k = 0; k < dash.length; k++)
/*  710 */         dash[k] *= scale;
/*      */     }
/*  712 */     return new BasicStroke(st.getLineWidth() * scale, st.getEndCap(), st.getLineJoin(), st.getMiterLimit(), dash, st.getDashPhase() * scale);
/*      */   }
/*      */ 
/*      */   private void setStrokeDiff(Stroke newStroke, Stroke oldStroke) {
/*  716 */     if (newStroke == oldStroke)
/*  717 */       return;
/*  718 */     if (!(newStroke instanceof BasicStroke))
/*  719 */       return;
/*  720 */     BasicStroke nStroke = (BasicStroke)newStroke;
/*  721 */     boolean oldOk = oldStroke instanceof BasicStroke;
/*  722 */     BasicStroke oStroke = null;
/*  723 */     if (oldOk)
/*  724 */       oStroke = (BasicStroke)oldStroke;
/*  725 */     if ((!oldOk) || (nStroke.getLineWidth() != oStroke.getLineWidth()))
/*  726 */       this.cb.setLineWidth(nStroke.getLineWidth());
/*  727 */     if ((!oldOk) || (nStroke.getEndCap() != oStroke.getEndCap())) {
/*  728 */       switch (nStroke.getEndCap()) {
/*      */       case 0:
/*  730 */         this.cb.setLineCap(0);
/*  731 */         break;
/*      */       case 2:
/*  733 */         this.cb.setLineCap(2);
/*  734 */         break;
/*      */       default:
/*  736 */         this.cb.setLineCap(1);
/*      */       }
/*      */     }
/*  739 */     if ((!oldOk) || (nStroke.getLineJoin() != oStroke.getLineJoin())) {
/*  740 */       switch (nStroke.getLineJoin()) {
/*      */       case 0:
/*  742 */         this.cb.setLineJoin(0);
/*  743 */         break;
/*      */       case 2:
/*  745 */         this.cb.setLineJoin(2);
/*  746 */         break;
/*      */       default:
/*  748 */         this.cb.setLineJoin(1);
/*      */       }
/*      */     }
/*  751 */     if ((!oldOk) || (nStroke.getMiterLimit() != oStroke.getMiterLimit()))
/*  752 */       this.cb.setMiterLimit(nStroke.getMiterLimit());
/*      */     boolean makeDash;
/*      */     boolean makeDash;
/*  754 */     if (oldOk)
/*      */     {
/*      */       boolean makeDash;
/*  755 */       if (nStroke.getDashArray() != null)
/*      */       {
/*      */         boolean makeDash;
/*  756 */         if (nStroke.getDashPhase() != oStroke.getDashPhase()) {
/*  757 */           makeDash = true;
/*      */         }
/*      */         else
/*      */         {
/*      */           boolean makeDash;
/*  759 */           if (!Arrays.equals(nStroke.getDashArray(), oStroke.getDashArray())) {
/*  760 */             makeDash = true;
/*      */           }
/*      */           else
/*  763 */             makeDash = false;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*      */         boolean makeDash;
/*  765 */         if (oStroke.getDashArray() != null) {
/*  766 */           makeDash = true;
/*      */         }
/*      */         else
/*  769 */           makeDash = false;
/*      */       }
/*      */     } else {
/*  772 */       makeDash = true;
/*      */     }
/*  774 */     if (makeDash) {
/*  775 */       float[] dash = nStroke.getDashArray();
/*  776 */       if (dash == null) {
/*  777 */         this.cb.setLiteral("[]0 d\n");
/*      */       } else {
/*  779 */         this.cb.setLiteral('[');
/*  780 */         int lim = dash.length;
/*  781 */         for (int k = 0; k < lim; k++) {
/*  782 */           this.cb.setLiteral(dash[k]);
/*  783 */           this.cb.setLiteral(' ');
/*      */         }
/*  785 */         this.cb.setLiteral(']');
/*  786 */         this.cb.setLiteral(nStroke.getDashPhase());
/*  787 */         this.cb.setLiteral(" d\n");
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setStroke(Stroke s)
/*      */   {
/*  797 */     this.originalStroke = s;
/*  798 */     this.stroke = transformStroke(s);
/*      */   }
/*      */ 
/*      */   public void setRenderingHint(RenderingHints.Key arg0, Object arg1)
/*      */   {
/*  809 */     if (arg1 != null) {
/*  810 */       this.rhints.put(arg0, arg1);
/*      */     }
/*  812 */     else if ((arg0 instanceof HyperLinkKey))
/*      */     {
/*  814 */       this.rhints.put(arg0, HyperLinkKey.VALUE_HYPERLINKKEY_OFF);
/*      */     }
/*      */     else
/*      */     {
/*  818 */       this.rhints.remove(arg0);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Object getRenderingHint(RenderingHints.Key arg0)
/*      */   {
/*  829 */     return this.rhints.get(arg0);
/*      */   }
/*      */ 
/*      */   public void setRenderingHints(Map<?, ?> hints)
/*      */   {
/*  837 */     this.rhints.clear();
/*  838 */     this.rhints.putAll(hints);
/*      */   }
/*      */ 
/*      */   public void addRenderingHints(Map<?, ?> hints)
/*      */   {
/*  846 */     this.rhints.putAll(hints);
/*      */   }
/*      */ 
/*      */   public RenderingHints getRenderingHints()
/*      */   {
/*  854 */     return this.rhints;
/*      */   }
/*      */ 
/*      */   public void translate(int x, int y)
/*      */   {
/*  862 */     translate(x, y);
/*      */   }
/*      */ 
/*      */   public void translate(double tx, double ty)
/*      */   {
/*  870 */     this.transform.translate(tx, ty);
/*      */   }
/*      */ 
/*      */   public void rotate(double theta)
/*      */   {
/*  878 */     this.transform.rotate(theta);
/*      */   }
/*      */ 
/*      */   public void rotate(double theta, double x, double y)
/*      */   {
/*  886 */     this.transform.rotate(theta, x, y);
/*      */   }
/*      */ 
/*      */   public void scale(double sx, double sy)
/*      */   {
/*  894 */     this.transform.scale(sx, sy);
/*  895 */     this.stroke = transformStroke(this.originalStroke);
/*      */   }
/*      */ 
/*      */   public void shear(double shx, double shy)
/*      */   {
/*  903 */     this.transform.shear(shx, shy);
/*      */   }
/*      */ 
/*      */   public void transform(AffineTransform tx)
/*      */   {
/*  911 */     this.transform.concatenate(tx);
/*  912 */     this.stroke = transformStroke(this.originalStroke);
/*      */   }
/*      */ 
/*      */   public void setTransform(AffineTransform t)
/*      */   {
/*  920 */     this.transform = new AffineTransform(t);
/*  921 */     this.stroke = transformStroke(this.originalStroke);
/*      */   }
/*      */ 
/*      */   public AffineTransform getTransform()
/*      */   {
/*  929 */     return new AffineTransform(this.transform);
/*      */   }
/*      */ 
/*      */   public Paint getPaint()
/*      */   {
/*  938 */     if (this.realPaint != null) {
/*  939 */       return this.realPaint;
/*      */     }
/*  941 */     return this.paint;
/*      */   }
/*      */ 
/*      */   public Composite getComposite()
/*      */   {
/*  950 */     return this.composite;
/*      */   }
/*      */ 
/*      */   public void setBackground(Color color)
/*      */   {
/*  958 */     this.background = color;
/*      */   }
/*      */ 
/*      */   public Color getBackground()
/*      */   {
/*  966 */     return this.background;
/*      */   }
/*      */ 
/*      */   public Stroke getStroke()
/*      */   {
/*  974 */     return this.originalStroke;
/*      */   }
/*      */ 
/*      */   public FontRenderContext getFontRenderContext()
/*      */   {
/*  983 */     boolean antialias = RenderingHints.VALUE_TEXT_ANTIALIAS_ON.equals(getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING));
/*  984 */     boolean fractions = RenderingHints.VALUE_FRACTIONALMETRICS_ON.equals(getRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS));
/*  985 */     return new FontRenderContext(new AffineTransform(), antialias, fractions);
/*      */   }
/*      */ 
/*      */   public Graphics create()
/*      */   {
/*  993 */     PdfGraphics2D g2 = new PdfGraphics2D();
/*  994 */     g2.rhints.putAll(this.rhints);
/*  995 */     g2.onlyShapes = this.onlyShapes;
/*  996 */     g2.transform = new AffineTransform(this.transform);
/*  997 */     g2.baseFonts = this.baseFonts;
/*  998 */     g2.fontMapper = this.fontMapper;
/*  999 */     g2.paint = this.paint;
/* 1000 */     g2.fillGState = this.fillGState;
/* 1001 */     g2.currentFillGState = this.currentFillGState;
/* 1002 */     g2.strokeGState = this.strokeGState;
/* 1003 */     g2.background = this.background;
/* 1004 */     g2.mediaTracker = this.mediaTracker;
/* 1005 */     g2.convertImagesToJPEG = this.convertImagesToJPEG;
/* 1006 */     g2.jpegQuality = this.jpegQuality;
/* 1007 */     g2.setFont(this.font);
/* 1008 */     g2.cb = this.cb.getDuplicate();
/* 1009 */     g2.cb.saveState();
/* 1010 */     g2.width = this.width;
/* 1011 */     g2.height = this.height;
/* 1012 */     g2.followPath(new Area(new Rectangle2D.Float(0.0F, 0.0F, this.width, this.height)), 3);
/* 1013 */     if (this.clip != null)
/* 1014 */       g2.clip = new Area(this.clip);
/* 1015 */     g2.composite = this.composite;
/* 1016 */     g2.stroke = this.stroke;
/* 1017 */     g2.originalStroke = this.originalStroke;
/* 1018 */     g2.strokeOne = ((BasicStroke)g2.transformStroke(g2.strokeOne));
/* 1019 */     g2.oldStroke = g2.strokeOne;
/* 1020 */     g2.setStrokeDiff(g2.oldStroke, null);
/* 1021 */     g2.cb.saveState();
/* 1022 */     if (g2.clip != null)
/* 1023 */       g2.followPath(g2.clip, 3);
/* 1024 */     g2.kid = true;
/* 1025 */     if (this.kids == null)
/* 1026 */       this.kids = new ArrayList();
/* 1027 */     this.kids.add(new Kid(this.cb.getInternalBuffer().size(), g2));
/* 1028 */     return g2;
/*      */   }
/*      */ 
/*      */   public PdfContentByte getContent() {
/* 1032 */     return this.cb;
/*      */   }
/*      */ 
/*      */   public Color getColor()
/*      */   {
/* 1039 */     if ((this.paint instanceof Color)) {
/* 1040 */       return (Color)this.paint;
/*      */     }
/* 1042 */     return Color.black;
/*      */   }
/*      */ 
/*      */   public void setColor(Color color)
/*      */   {
/* 1051 */     setPaint(color);
/*      */   }
/*      */ 
/*      */   public void setPaintMode()
/*      */   {
/*      */   }
/*      */ 
/*      */   public void setXORMode(Color c1)
/*      */   {
/*      */   }
/*      */ 
/*      */   public Font getFont()
/*      */   {
/* 1073 */     return this.font;
/*      */   }
/*      */ 
/*      */   public void setFont(Font f)
/*      */   {
/* 1084 */     if (f == null)
/* 1085 */       return;
/* 1086 */     if (this.onlyShapes) {
/* 1087 */       this.font = f;
/* 1088 */       return;
/*      */     }
/* 1090 */     if (f == this.font)
/* 1091 */       return;
/* 1092 */     this.font = f;
/* 1093 */     this.fontSize = f.getSize2D();
/* 1094 */     this.baseFont = getCachedBaseFont(f);
/*      */   }
/*      */ 
/*      */   private BaseFont getCachedBaseFont(Font f) {
/* 1098 */     synchronized (this.baseFonts) {
/* 1099 */       BaseFont bf = (BaseFont)this.baseFonts.get(f.getFontName());
/* 1100 */       if (bf == null) {
/* 1101 */         bf = this.fontMapper.awtToPdf(f);
/* 1102 */         this.baseFonts.put(f.getFontName(), bf);
/*      */       }
/* 1104 */       return bf;
/*      */     }
/*      */   }
/*      */ 
/*      */   public FontMetrics getFontMetrics(Font f)
/*      */   {
/* 1113 */     return getDG2().getFontMetrics(f);
/*      */   }
/*      */ 
/*      */   public Rectangle getClipBounds()
/*      */   {
/* 1121 */     if (this.clip == null)
/* 1122 */       return null;
/* 1123 */     return getClip().getBounds();
/*      */   }
/*      */ 
/*      */   public void clipRect(int x, int y, int width, int height)
/*      */   {
/* 1131 */     Rectangle2D rect = new Rectangle2D.Double(x, y, width, height);
/* 1132 */     clip(rect);
/*      */   }
/*      */ 
/*      */   public void setClip(int x, int y, int width, int height)
/*      */   {
/* 1140 */     Rectangle2D rect = new Rectangle2D.Double(x, y, width, height);
/* 1141 */     setClip(rect);
/*      */   }
/*      */ 
/*      */   public void clip(Shape s)
/*      */   {
/* 1149 */     if (s == null) {
/* 1150 */       setClip(null);
/* 1151 */       return;
/*      */     }
/* 1153 */     s = this.transform.createTransformedShape(s);
/* 1154 */     if (this.clip == null)
/* 1155 */       this.clip = new Area(s);
/*      */     else
/* 1157 */       this.clip.intersect(new Area(s));
/* 1158 */     followPath(s, 3);
/*      */   }
/*      */ 
/*      */   public Shape getClip()
/*      */   {
/*      */     try
/*      */     {
/* 1167 */       return this.transform.createInverse().createTransformedShape(this.clip);
/*      */     } catch (NoninvertibleTransformException e) {
/*      */     }
/* 1170 */     return null;
/*      */   }
/*      */ 
/*      */   public void setClip(Shape s)
/*      */   {
/* 1179 */     this.cb.restoreState();
/* 1180 */     this.cb.saveState();
/* 1181 */     if (s != null)
/* 1182 */       s = this.transform.createTransformedShape(s);
/* 1183 */     if (s == null) {
/* 1184 */       this.clip = null;
/*      */     }
/*      */     else {
/* 1187 */       this.clip = new Area(s);
/* 1188 */       followPath(s, 3);
/*      */     }
/* 1190 */     this.paintFill = (this.paintStroke = null);
/* 1191 */     this.currentFillGState = (this.currentStrokeGState = -1);
/* 1192 */     this.oldStroke = this.strokeOne;
/*      */   }
/*      */ 
/*      */   public void copyArea(int x, int y, int width, int height, int dx, int dy)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void drawLine(int x1, int y1, int x2, int y2)
/*      */   {
/* 1208 */     Line2D line = new Line2D.Double(x1, y1, x2, y2);
/* 1209 */     draw(line);
/*      */   }
/*      */ 
/*      */   public void drawRect(int x, int y, int width, int height)
/*      */   {
/* 1217 */     draw(new Rectangle(x, y, width, height));
/*      */   }
/*      */ 
/*      */   public void fillRect(int x, int y, int width, int height)
/*      */   {
/* 1225 */     fill(new Rectangle(x, y, width, height));
/*      */   }
/*      */ 
/*      */   public void clearRect(int x, int y, int width, int height)
/*      */   {
/* 1233 */     Paint temp = this.paint;
/* 1234 */     setPaint(this.background);
/* 1235 */     fillRect(x, y, width, height);
/* 1236 */     setPaint(temp);
/*      */   }
/*      */ 
/*      */   public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight)
/*      */   {
/* 1244 */     RoundRectangle2D rect = new RoundRectangle2D.Double(x, y, width, height, arcWidth, arcHeight);
/* 1245 */     draw(rect);
/*      */   }
/*      */ 
/*      */   public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight)
/*      */   {
/* 1253 */     RoundRectangle2D rect = new RoundRectangle2D.Double(x, y, width, height, arcWidth, arcHeight);
/* 1254 */     fill(rect);
/*      */   }
/*      */ 
/*      */   public void drawOval(int x, int y, int width, int height)
/*      */   {
/* 1262 */     Ellipse2D oval = new Ellipse2D.Float(x, y, width, height);
/* 1263 */     draw(oval);
/*      */   }
/*      */ 
/*      */   public void fillOval(int x, int y, int width, int height)
/*      */   {
/* 1271 */     Ellipse2D oval = new Ellipse2D.Float(x, y, width, height);
/* 1272 */     fill(oval);
/*      */   }
/*      */ 
/*      */   public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle)
/*      */   {
/* 1280 */     Arc2D arc = new Arc2D.Double(x, y, width, height, startAngle, arcAngle, 0);
/* 1281 */     draw(arc);
/*      */   }
/*      */ 
/*      */   public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle)
/*      */   {
/* 1290 */     Arc2D arc = new Arc2D.Double(x, y, width, height, startAngle, arcAngle, 2);
/* 1291 */     fill(arc);
/*      */   }
/*      */ 
/*      */   public void drawPolyline(int[] x, int[] y, int nPoints)
/*      */   {
/* 1299 */     PolylineShape polyline = new PolylineShape(x, y, nPoints);
/* 1300 */     draw(polyline);
/*      */   }
/*      */ 
/*      */   public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints)
/*      */   {
/* 1308 */     Polygon poly = new Polygon(xPoints, yPoints, nPoints);
/* 1309 */     draw(poly);
/*      */   }
/*      */ 
/*      */   public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints)
/*      */   {
/* 1317 */     Polygon poly = new Polygon();
/* 1318 */     for (int i = 0; i < nPoints; i++) {
/* 1319 */       poly.addPoint(xPoints[i], yPoints[i]);
/*      */     }
/* 1321 */     fill(poly);
/*      */   }
/*      */ 
/*      */   public boolean drawImage(java.awt.Image img, int x, int y, ImageObserver observer)
/*      */   {
/* 1329 */     return drawImage(img, x, y, null, observer);
/*      */   }
/*      */ 
/*      */   public boolean drawImage(java.awt.Image img, int x, int y, int width, int height, ImageObserver observer)
/*      */   {
/* 1337 */     return drawImage(img, x, y, width, height, null, observer);
/*      */   }
/*      */ 
/*      */   public boolean drawImage(java.awt.Image img, int x, int y, Color bgcolor, ImageObserver observer)
/*      */   {
/* 1345 */     waitForImage(img);
/* 1346 */     return drawImage(img, x, y, img.getWidth(observer), img.getHeight(observer), bgcolor, observer);
/*      */   }
/*      */ 
/*      */   public boolean drawImage(java.awt.Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer)
/*      */   {
/* 1354 */     waitForImage(img);
/* 1355 */     double scalex = width / img.getWidth(observer);
/* 1356 */     double scaley = height / img.getHeight(observer);
/* 1357 */     AffineTransform tx = AffineTransform.getTranslateInstance(x, y);
/* 1358 */     tx.scale(scalex, scaley);
/* 1359 */     return drawImage(img, null, tx, bgcolor, observer);
/*      */   }
/*      */ 
/*      */   public boolean drawImage(java.awt.Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, ImageObserver observer)
/*      */   {
/* 1367 */     return drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null, observer);
/*      */   }
/*      */ 
/*      */   public boolean drawImage(java.awt.Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, Color bgcolor, ImageObserver observer)
/*      */   {
/* 1375 */     waitForImage(img);
/* 1376 */     double dwidth = dx2 - dx1;
/* 1377 */     double dheight = dy2 - dy1;
/* 1378 */     double swidth = sx2 - sx1;
/* 1379 */     double sheight = sy2 - sy1;
/*      */ 
/* 1382 */     if ((dwidth == 0.0D) || (dheight == 0.0D) || (swidth == 0.0D) || (sheight == 0.0D)) return true;
/*      */ 
/* 1384 */     double scalex = dwidth / swidth;
/* 1385 */     double scaley = dheight / sheight;
/*      */ 
/* 1387 */     double transx = sx1 * scalex;
/* 1388 */     double transy = sy1 * scaley;
/* 1389 */     AffineTransform tx = AffineTransform.getTranslateInstance(dx1 - transx, dy1 - transy);
/* 1390 */     tx.scale(scalex, scaley);
/*      */ 
/* 1392 */     BufferedImage mask = new BufferedImage(img.getWidth(observer), img.getHeight(observer), 12);
/* 1393 */     Graphics g = mask.getGraphics();
/* 1394 */     g.fillRect(sx1, sy1, (int)swidth, (int)sheight);
/* 1395 */     drawImage(img, mask, tx, null, observer);
/* 1396 */     g.dispose();
/* 1397 */     return true;
/*      */   }
/*      */ 
/*      */   public void dispose()
/*      */   {
/* 1405 */     if (this.kid)
/* 1406 */       return;
/* 1407 */     if (!this.disposeCalled) {
/* 1408 */       this.disposeCalled = true;
/* 1409 */       this.cb.restoreState();
/* 1410 */       this.cb.restoreState();
/* 1411 */       if (this.dg2 != null) {
/* 1412 */         this.dg2.dispose();
/* 1413 */         this.dg2 = null;
/*      */       }
/* 1415 */       if (this.kids != null) {
/* 1416 */         ByteBuffer buf = new ByteBuffer();
/* 1417 */         internalDispose(buf);
/* 1418 */         ByteBuffer buf2 = this.cb.getInternalBuffer();
/* 1419 */         buf2.reset();
/* 1420 */         buf2.append(buf);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void internalDispose(ByteBuffer buf) {
/* 1426 */     int last = 0;
/* 1427 */     int pos = 0;
/* 1428 */     ByteBuffer buf2 = this.cb.getInternalBuffer();
/* 1429 */     if (this.kids != null) {
/* 1430 */       for (Kid kid : this.kids) {
/* 1431 */         pos = kid.pos;
/* 1432 */         PdfGraphics2D g2 = kid.graphics;
/* 1433 */         g2.cb.restoreState();
/* 1434 */         g2.cb.restoreState();
/* 1435 */         buf.append(buf2.getBuffer(), last, pos - last);
/* 1436 */         if (g2.dg2 != null) {
/* 1437 */           g2.dg2.dispose();
/* 1438 */           g2.dg2 = null;
/*      */         }
/* 1440 */         g2.internalDispose(buf);
/* 1441 */         last = pos;
/*      */       }
/*      */     }
/* 1444 */     buf.append(buf2.getBuffer(), last, buf2.size() - last);
/*      */   }
/*      */ 
/*      */   private void followPath(Shape s, int drawType)
/*      */   {
/* 1456 */     if (s == null) return;
/* 1457 */     if ((drawType == 2) && 
/* 1458 */       (!(this.stroke instanceof BasicStroke))) {
/* 1459 */       s = this.stroke.createStrokedShape(s);
/* 1460 */       followPath(s, 1);
/* 1461 */       return;
/*      */     }
/*      */ 
/* 1464 */     if (drawType == 2) {
/* 1465 */       setStrokeDiff(this.stroke, this.oldStroke);
/* 1466 */       this.oldStroke = this.stroke;
/* 1467 */       setStrokePaint();
/*      */     }
/* 1469 */     else if (drawType == 1) {
/* 1470 */       setFillPaint();
/*      */     }
/* 1472 */     int traces = 0;
/*      */     PathIterator points;
/*      */     PathIterator points;
/* 1473 */     if (drawType == 3)
/* 1474 */       points = s.getPathIterator(IDENTITY);
/*      */     else
/* 1476 */       points = s.getPathIterator(this.transform);
/* 1477 */     float[] coords = new float[6];
/* 1478 */     double[] dcoords = new double[6];
/* 1479 */     while (!points.isDone()) {
/* 1480 */       traces++;
/*      */ 
/* 1482 */       int segtype = points.currentSegment(dcoords);
/* 1483 */       int numpoints = segtype == 3 ? 3 : segtype == 2 ? 2 : segtype == 4 ? 0 : 1;
/*      */ 
/* 1487 */       for (int i = 0; i < numpoints * 2; i++) {
/* 1488 */         coords[i] = ((float)dcoords[i]);
/*      */       }
/*      */ 
/* 1491 */       normalizeY(coords);
/* 1492 */       switch (segtype) {
/*      */       case 4:
/* 1494 */         this.cb.closePath();
/* 1495 */         break;
/*      */       case 3:
/* 1498 */         this.cb.curveTo(coords[0], coords[1], coords[2], coords[3], coords[4], coords[5]);
/* 1499 */         break;
/*      */       case 1:
/* 1502 */         this.cb.lineTo(coords[0], coords[1]);
/* 1503 */         break;
/*      */       case 0:
/* 1506 */         this.cb.moveTo(coords[0], coords[1]);
/* 1507 */         break;
/*      */       case 2:
/* 1510 */         this.cb.curveTo(coords[0], coords[1], coords[2], coords[3]);
/*      */       }
/*      */ 
/* 1513 */       points.next();
/*      */     }
/* 1515 */     switch (drawType) {
/*      */     case 1:
/* 1517 */       if (traces > 0)
/* 1518 */         if (points.getWindingRule() == 0)
/* 1519 */           this.cb.eoFill();
/*      */         else
/* 1521 */           this.cb.fill(); 
/* 1521 */       break;
/*      */     case 2:
/* 1525 */       if (traces > 0)
/* 1526 */         this.cb.stroke(); break;
/*      */     default:
/* 1529 */       if (traces == 0)
/* 1530 */         this.cb.rectangle(0.0F, 0.0F, 0.0F, 0.0F);
/* 1531 */       if (points.getWindingRule() == 0)
/* 1532 */         this.cb.eoClip();
/*      */       else
/* 1534 */         this.cb.clip();
/* 1535 */       this.cb.newPath();
/*      */     }
/*      */   }
/*      */ 
/*      */   private float normalizeY(float y) {
/* 1540 */     return this.height - y;
/*      */   }
/*      */ 
/*      */   private void normalizeY(float[] coords) {
/* 1544 */     coords[1] = normalizeY(coords[1]);
/* 1545 */     coords[3] = normalizeY(coords[3]);
/* 1546 */     coords[5] = normalizeY(coords[5]);
/*      */   }
/*      */ 
/*      */   protected AffineTransform normalizeMatrix() {
/* 1550 */     double[] mx = new double[6];
/* 1551 */     AffineTransform result = AffineTransform.getTranslateInstance(0.0D, 0.0D);
/* 1552 */     result.getMatrix(mx);
/* 1553 */     mx[3] = -1.0D;
/* 1554 */     mx[5] = this.height;
/* 1555 */     result = new AffineTransform(mx);
/* 1556 */     result.concatenate(this.transform);
/* 1557 */     return result;
/*      */   }
/*      */ 
/*      */   private boolean drawImage(java.awt.Image img, java.awt.Image mask, AffineTransform xform, Color bgColor, ImageObserver obs) {
/* 1561 */     if (xform == null)
/* 1562 */       xform = new AffineTransform();
/*      */     else
/* 1564 */       xform = new AffineTransform(xform);
/* 1565 */     xform.translate(0.0D, img.getHeight(obs));
/* 1566 */     xform.scale(img.getWidth(obs), img.getHeight(obs));
/*      */ 
/* 1568 */     AffineTransform inverse = normalizeMatrix();
/* 1569 */     AffineTransform flipper = AffineTransform.getScaleInstance(1.0D, -1.0D);
/* 1570 */     inverse.concatenate(xform);
/* 1571 */     inverse.concatenate(flipper);
/*      */ 
/* 1573 */     double[] mx = new double[6];
/* 1574 */     inverse.getMatrix(mx);
/* 1575 */     if (this.currentFillGState != 255) {
/* 1576 */       PdfGState gs = this.fillGState['ÿ'];
/* 1577 */       if (gs == null) {
/* 1578 */         gs = new PdfGState();
/* 1579 */         gs.setFillOpacity(1.0F);
/* 1580 */         this.fillGState['ÿ'] = gs;
/*      */       }
/* 1582 */       this.cb.setGState(gs);
/*      */     }
/*      */     try
/*      */     {
/* 1586 */       com.itextpdf.text.Image image = null;
/* 1587 */       if (!this.convertImagesToJPEG) {
/* 1588 */         image = com.itextpdf.text.Image.getInstance(img, bgColor);
/*      */       }
/*      */       else {
/* 1591 */         BufferedImage scaled = new BufferedImage(img.getWidth(null), img.getHeight(null), 1);
/* 1592 */         Graphics2D g3 = scaled.createGraphics();
/* 1593 */         g3.drawImage(img, 0, 0, img.getWidth(null), img.getHeight(null), null);
/* 1594 */         g3.dispose();
/*      */ 
/* 1596 */         ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 1597 */         ImageWriteParam iwparam = new JPEGImageWriteParam(Locale.getDefault());
/* 1598 */         iwparam.setCompressionMode(2);
/* 1599 */         iwparam.setCompressionQuality(this.jpegQuality);
/* 1600 */         ImageWriter iw = (ImageWriter)ImageIO.getImageWritersByFormatName("jpg").next();
/* 1601 */         ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
/* 1602 */         iw.setOutput(ios);
/* 1603 */         iw.write(null, new IIOImage(scaled, null, null), iwparam);
/* 1604 */         iw.dispose();
/* 1605 */         ios.close();
/*      */ 
/* 1607 */         scaled.flush();
/* 1608 */         scaled = null;
/* 1609 */         image = com.itextpdf.text.Image.getInstance(baos.toByteArray());
/*      */       }
/*      */ 
/* 1612 */       if (mask != null) {
/* 1613 */         com.itextpdf.text.Image msk = com.itextpdf.text.Image.getInstance(mask, null, true);
/* 1614 */         msk.makeMask();
/* 1615 */         msk.setInverted(true);
/* 1616 */         image.setImageMask(msk);
/*      */       }
/* 1618 */       this.cb.addImage(image, (float)mx[0], (float)mx[1], (float)mx[2], (float)mx[3], (float)mx[4], (float)mx[5]);
/* 1619 */       Object url = getRenderingHint(HyperLinkKey.KEY_INSTANCE);
/* 1620 */       if ((url != null) && (!url.equals(HyperLinkKey.VALUE_HYPERLINKKEY_OFF))) {
/* 1621 */         PdfAction action = new PdfAction(url.toString());
/* 1622 */         this.cb.setAction(action, (float)mx[4], (float)mx[5], (float)(mx[0] + mx[4]), (float)(mx[3] + mx[5]));
/*      */       }
/*      */     } catch (Exception ex) {
/* 1625 */       throw new IllegalArgumentException(ex);
/*      */     }
/* 1627 */     if ((this.currentFillGState >= 0) && (this.currentFillGState != 255)) {
/* 1628 */       PdfGState gs = this.fillGState[this.currentFillGState];
/* 1629 */       this.cb.setGState(gs);
/*      */     }
/* 1631 */     return true;
/*      */   }
/*      */ 
/*      */   private boolean checkNewPaint(Paint oldPaint) {
/* 1635 */     if (this.paint == oldPaint)
/* 1636 */       return false;
/* 1637 */     return (!(this.paint instanceof Color)) || (!this.paint.equals(oldPaint));
/*      */   }
/*      */ 
/*      */   private void setFillPaint() {
/* 1641 */     if (checkNewPaint(this.paintFill)) {
/* 1642 */       this.paintFill = this.paint;
/* 1643 */       setPaint(false, 0.0D, 0.0D, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void setStrokePaint() {
/* 1648 */     if (checkNewPaint(this.paintStroke)) {
/* 1649 */       this.paintStroke = this.paint;
/* 1650 */       setPaint(false, 0.0D, 0.0D, false);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void setPaint(boolean invert, double xoffset, double yoffset, boolean fill) {
/* 1655 */     if ((this.paint instanceof Color)) {
/* 1656 */       Color color = (Color)this.paint;
/* 1657 */       int alpha = color.getAlpha();
/* 1658 */       if (fill) {
/* 1659 */         if (alpha != this.currentFillGState) {
/* 1660 */           this.currentFillGState = alpha;
/* 1661 */           PdfGState gs = this.fillGState[alpha];
/* 1662 */           if (gs == null) {
/* 1663 */             gs = new PdfGState();
/* 1664 */             gs.setFillOpacity(alpha / 255.0F);
/* 1665 */             this.fillGState[alpha] = gs;
/*      */           }
/* 1667 */           this.cb.setGState(gs);
/*      */         }
/* 1669 */         this.cb.setColorFill(new BaseColor(color.getRGB()));
/*      */       }
/*      */       else {
/* 1672 */         if (alpha != this.currentStrokeGState) {
/* 1673 */           this.currentStrokeGState = alpha;
/* 1674 */           PdfGState gs = this.strokeGState[alpha];
/* 1675 */           if (gs == null) {
/* 1676 */             gs = new PdfGState();
/* 1677 */             gs.setStrokeOpacity(alpha / 255.0F);
/* 1678 */             this.strokeGState[alpha] = gs;
/*      */           }
/* 1680 */           this.cb.setGState(gs);
/*      */         }
/* 1682 */         this.cb.setColorStroke(new BaseColor(color.getRGB()));
/*      */       }
/*      */     }
/* 1685 */     else if ((this.paint instanceof GradientPaint)) {
/* 1686 */       GradientPaint gp = (GradientPaint)this.paint;
/* 1687 */       Point2D p1 = gp.getPoint1();
/* 1688 */       this.transform.transform(p1, p1);
/* 1689 */       Point2D p2 = gp.getPoint2();
/* 1690 */       this.transform.transform(p2, p2);
/* 1691 */       Color c1 = gp.getColor1();
/* 1692 */       Color c2 = gp.getColor2();
/* 1693 */       PdfShading shading = PdfShading.simpleAxial(this.cb.getPdfWriter(), (float)p1.getX(), normalizeY((float)p1.getY()), (float)p2.getX(), normalizeY((float)p2.getY()), new BaseColor(c1.getRGB()), new BaseColor(c2.getRGB()));
/* 1694 */       PdfShadingPattern pat = new PdfShadingPattern(shading);
/* 1695 */       if (fill)
/* 1696 */         this.cb.setShadingFill(pat);
/*      */       else
/* 1698 */         this.cb.setShadingStroke(pat);
/*      */     }
/* 1700 */     else if ((this.paint instanceof TexturePaint)) {
/*      */       try {
/* 1702 */         TexturePaint tp = (TexturePaint)this.paint;
/* 1703 */         BufferedImage img = tp.getImage();
/* 1704 */         Rectangle2D rect = tp.getAnchorRect();
/* 1705 */         com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(img, null);
/* 1706 */         PdfPatternPainter pattern = this.cb.createPattern(image.getWidth(), image.getHeight());
/* 1707 */         AffineTransform inverse = normalizeMatrix();
/* 1708 */         inverse.translate(rect.getX(), rect.getY());
/* 1709 */         inverse.scale(rect.getWidth() / image.getWidth(), -rect.getHeight() / image.getHeight());
/* 1710 */         double[] mx = new double[6];
/* 1711 */         inverse.getMatrix(mx);
/* 1712 */         pattern.setPatternMatrix((float)mx[0], (float)mx[1], (float)mx[2], (float)mx[3], (float)mx[4], (float)mx[5]);
/* 1713 */         image.setAbsolutePosition(0.0F, 0.0F);
/* 1714 */         pattern.addImage(image);
/* 1715 */         if (fill)
/* 1716 */           this.cb.setPatternFill(pattern);
/*      */         else
/* 1718 */           this.cb.setPatternStroke(pattern);
/*      */       } catch (Exception ex) {
/* 1720 */         if (fill)
/* 1721 */           this.cb.setColorFill(BaseColor.GRAY);
/*      */         else
/* 1723 */           this.cb.setColorStroke(BaseColor.GRAY);
/*      */       }
/*      */     }
/*      */     else {
/*      */       try {
/* 1728 */         BufferedImage img = null;
/* 1729 */         int type = 6;
/* 1730 */         if (this.paint.getTransparency() == 1) {
/* 1731 */           type = 5;
/*      */         }
/* 1733 */         img = new BufferedImage((int)this.width, (int)this.height, type);
/* 1734 */         Graphics2D g = (Graphics2D)img.getGraphics();
/* 1735 */         g.transform(this.transform);
/* 1736 */         AffineTransform inv = this.transform.createInverse();
/* 1737 */         Shape fillRect = new Rectangle2D.Double(0.0D, 0.0D, img.getWidth(), img.getHeight());
/* 1738 */         fillRect = inv.createTransformedShape(fillRect);
/* 1739 */         g.setPaint(this.paint);
/* 1740 */         g.fill(fillRect);
/* 1741 */         if (invert) {
/* 1742 */           AffineTransform tx = new AffineTransform();
/* 1743 */           tx.scale(1.0D, -1.0D);
/* 1744 */           tx.translate(-xoffset, -yoffset);
/* 1745 */           g.drawImage(img, tx, null);
/*      */         }
/* 1747 */         g.dispose();
/* 1748 */         g = null;
/* 1749 */         com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(img, null);
/* 1750 */         PdfPatternPainter pattern = this.cb.createPattern(this.width, this.height);
/* 1751 */         image.setAbsolutePosition(0.0F, 0.0F);
/* 1752 */         pattern.addImage(image);
/* 1753 */         if (fill)
/* 1754 */           this.cb.setPatternFill(pattern);
/*      */         else
/* 1756 */           this.cb.setPatternStroke(pattern);
/*      */       } catch (Exception ex) {
/* 1758 */         if (fill)
/* 1759 */           this.cb.setColorFill(BaseColor.GRAY);
/*      */         else
/* 1761 */           this.cb.setColorStroke(BaseColor.GRAY);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private synchronized void waitForImage(java.awt.Image image) {
/* 1767 */     if (this.mediaTracker == null)
/* 1768 */       this.mediaTracker = new MediaTracker(new FakeComponent(null));
/* 1769 */     this.mediaTracker.addImage(image, 0);
/*      */     try {
/* 1771 */       this.mediaTracker.waitForID(0);
/*      */     }
/*      */     catch (InterruptedException e)
/*      */     {
/*      */     }
/* 1776 */     this.mediaTracker.removeImage(image);
/*      */   }
/*      */ 
/*      */   public static class HyperLinkKey extends RenderingHints.Key
/*      */   {
/* 1789 */     public static final HyperLinkKey KEY_INSTANCE = new HyperLinkKey(9999);
/* 1790 */     public static final Object VALUE_HYPERLINKKEY_OFF = "0";
/*      */ 
/*      */     protected HyperLinkKey(int arg0) {
/* 1793 */       super();
/*      */     }
/*      */ 
/*      */     public boolean isCompatibleValue(Object val)
/*      */     {
/* 1799 */       return true;
/*      */     }
/*      */ 
/*      */     public String toString()
/*      */     {
/* 1804 */       return "HyperLinkKey";
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class FakeComponent extends Component
/*      */   {
/*      */     private static final long serialVersionUID = 6450197945596086638L;
/*      */   }
/*      */ 
/*      */   private static final class Kid
/*      */   {
/*      */     final int pos;
/*      */     final PdfGraphics2D graphics;
/*      */ 
/*      */     Kid(int pos, PdfGraphics2D graphics)
/*      */     {
/*  153 */       this.pos = pos;
/*  154 */       this.graphics = graphics;
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.awt.PdfGraphics2D
 * JD-Core Version:    0.6.2
 */