/*     */ package org.apache.pdfbox.pdfviewer;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Composite;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Image;
/*     */ import java.awt.Paint;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Area;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Point2D.Double;
/*     */ import java.awt.geom.Point2D.Float;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.PDPage;
/*     */ import org.apache.pdfbox.pdmodel.PDResources;
/*     */ import org.apache.pdfbox.pdmodel.common.PDMatrix;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ import org.apache.pdfbox.pdmodel.font.PDFont;
/*     */ import org.apache.pdfbox.pdmodel.graphics.PDGraphicsState;
/*     */ import org.apache.pdfbox.pdmodel.graphics.PDShading;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorState;
/*     */ import org.apache.pdfbox.pdmodel.graphics.shading.AxialShadingPaint;
/*     */ import org.apache.pdfbox.pdmodel.graphics.shading.PDShadingResources;
/*     */ import org.apache.pdfbox.pdmodel.graphics.shading.PDShadingType1;
/*     */ import org.apache.pdfbox.pdmodel.graphics.shading.PDShadingType2;
/*     */ import org.apache.pdfbox.pdmodel.graphics.shading.PDShadingType3;
/*     */ import org.apache.pdfbox.pdmodel.graphics.shading.PDShadingType4;
/*     */ import org.apache.pdfbox.pdmodel.graphics.shading.PDShadingType5;
/*     */ import org.apache.pdfbox.pdmodel.graphics.shading.PDShadingType6;
/*     */ import org.apache.pdfbox.pdmodel.graphics.shading.PDShadingType7;
/*     */ import org.apache.pdfbox.pdmodel.graphics.shading.RadialShadingPaint;
/*     */ import org.apache.pdfbox.pdmodel.graphics.shading.Type1ShadingPaint;
/*     */ import org.apache.pdfbox.pdmodel.graphics.shading.Type4ShadingPaint;
/*     */ import org.apache.pdfbox.pdmodel.graphics.shading.Type5ShadingPaint;
/*     */ import org.apache.pdfbox.pdmodel.graphics.shading.Type6ShadingPaint;
/*     */ import org.apache.pdfbox.pdmodel.graphics.shading.Type7ShadingPaint;
/*     */ import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
/*     */ import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceDictionary;
/*     */ import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceStream;
/*     */ import org.apache.pdfbox.pdmodel.text.PDTextState;
/*     */ import org.apache.pdfbox.util.Matrix;
/*     */ import org.apache.pdfbox.util.PDFStreamEngine;
/*     */ import org.apache.pdfbox.util.ResourceLoader;
/*     */ import org.apache.pdfbox.util.TextPosition;
/*     */ 
/*     */ public class PageDrawer extends PDFStreamEngine
/*     */ {
/*  83 */   private static final Log LOG = LogFactory.getLog(PageDrawer.class);
/*     */   private Graphics2D graphics;
/*  90 */   private int clippingWindingRule = -1;
/*     */   protected Dimension pageSize;
/*     */   protected PDPage page;
/* 101 */   private GeneralPath linePath = new GeneralPath();
/*     */ 
/* 103 */   private BasicStroke stroke = null;
/*     */ 
/*     */   public PageDrawer()
/*     */     throws IOException
/*     */   {
/* 112 */     super(ResourceLoader.loadProperties("org/apache/pdfbox/resources/PageDrawer.properties", true));
/*     */   }
/*     */ 
/*     */   public void drawPage(Graphics g, PDPage p, Dimension pageDimension)
/*     */     throws IOException
/*     */   {
/* 127 */     this.graphics = ((Graphics2D)g);
/* 128 */     this.page = p;
/* 129 */     this.pageSize = pageDimension;
/* 130 */     this.graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/* 131 */     this.graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
/*     */ 
/* 133 */     this.graphics.setStroke(new BasicStroke(1.0F, 0, 0));
/*     */ 
/* 136 */     if (this.page.getContents() != null)
/*     */     {
/* 138 */       PDResources resources = this.page.findResources();
/* 139 */       processStream(this.page, resources, this.page.getContents().getStream());
/*     */     }
/* 141 */     List annotations = this.page.getAnnotations();
/* 142 */     for (int i = 0; i < annotations.size(); i++)
/*     */     {
/* 144 */       PDAnnotation annot = (PDAnnotation)annotations.get(i);
/* 145 */       PDRectangle rect = annot.getRectangle();
/* 146 */       String appearanceName = annot.getAppearanceStream();
/* 147 */       PDAppearanceDictionary appearDictionary = annot.getAppearance();
/* 148 */       if (appearDictionary != null)
/*     */       {
/* 150 */         if (appearanceName == null)
/*     */         {
/* 152 */           appearanceName = "default";
/*     */         }
/* 154 */         Map appearanceMap = appearDictionary.getNormalAppearance();
/* 155 */         if (appearanceMap != null)
/*     */         {
/* 157 */           PDAppearanceStream appearance = (PDAppearanceStream)appearanceMap.get(appearanceName);
/*     */ 
/* 159 */           if (appearance != null)
/*     */           {
/* 161 */             Point2D point = new Point2D.Float(rect.getLowerLeftX(), rect.getLowerLeftY());
/* 162 */             Matrix matrix = appearance.getMatrix();
/* 163 */             if (matrix != null)
/*     */             {
/* 166 */               AffineTransform at = matrix.createAffineTransform();
/* 167 */               at.transform(point, point);
/*     */             }
/* 169 */             g.translate((int)point.getX(), -(int)point.getY());
/* 170 */             processSubStream(this.page, appearance.getResources(), appearance.getStream());
/* 171 */             g.translate(-(int)point.getX(), (int)point.getY());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/* 184 */     this.graphics = null;
/* 185 */     this.linePath = null;
/* 186 */     this.page = null;
/* 187 */     this.pageSize = null;
/*     */   }
/*     */ 
/*     */   protected void processTextPosition(TextPosition text)
/*     */   {
/*     */     try
/*     */     {
/* 200 */       PDGraphicsState graphicsState = getGraphicsState();
/*     */       Composite composite;
/*     */       Paint paint;
/* 203 */       switch (graphicsState.getTextState().getRenderingMode())
/*     */       {
/*     */       case 0:
/* 206 */         composite = graphicsState.getNonStrokeJavaComposite();
/* 207 */         paint = graphicsState.getNonStrokingColor().getJavaColor();
/* 208 */         if (paint == null)
/*     */         {
/* 210 */           paint = graphicsState.getNonStrokingColor().getPaint(this.pageSize.height); } break;
/*     */       case 1:
/* 214 */         composite = graphicsState.getStrokeJavaComposite();
/* 215 */         paint = graphicsState.getStrokingColor().getJavaColor();
/* 216 */         if (paint == null)
/*     */         {
/* 218 */           paint = graphicsState.getStrokingColor().getPaint(this.pageSize.height); } break;
/*     */       case 3:
/* 223 */         Color nsc = graphicsState.getStrokingColor().getJavaColor();
/* 224 */         float[] components = { Color.black.getRed(), Color.black.getGreen(), Color.black.getBlue() };
/* 225 */         paint = new Color(nsc.getColorSpace(), components, 0.0F);
/* 226 */         composite = graphicsState.getStrokeJavaComposite();
/* 227 */         break;
/*     */       case 2:
/*     */       default:
/* 230 */         LOG.debug("Unsupported RenderingMode " + getGraphicsState().getTextState().getRenderingMode() + " in PageDrawer.processTextPosition()." + " Using RenderingMode " + 0 + " instead");
/*     */ 
/* 236 */         composite = graphicsState.getNonStrokeJavaComposite();
/* 237 */         paint = graphicsState.getNonStrokingColor().getJavaColor();
/*     */       }
/* 239 */       this.graphics.setComposite(composite);
/* 240 */       this.graphics.setPaint(paint);
/*     */ 
/* 242 */       PDFont font = text.getFont();
/* 243 */       Matrix textPos = text.getTextPos().copy();
/* 244 */       float x = textPos.getXPosition();
/*     */ 
/* 246 */       float y = this.pageSize.height - textPos.getYPosition();
/*     */ 
/* 248 */       textPos.setValue(2, 0, 0.0F);
/* 249 */       textPos.setValue(2, 1, 0.0F);
/*     */ 
/* 251 */       textPos.setValue(0, 1, -1.0F * textPos.getValue(0, 1));
/* 252 */       textPos.setValue(1, 0, -1.0F * textPos.getValue(1, 0));
/* 253 */       AffineTransform at = textPos.createAffineTransform();
/* 254 */       PDMatrix fontMatrix = font.getFontMatrix();
/* 255 */       at.scale(fontMatrix.getValue(0, 0) * 1000.0F, fontMatrix.getValue(1, 1) * 1000.0F);
/*     */ 
/* 257 */       this.graphics.setClip(graphicsState.getCurrentClippingPath());
/*     */ 
/* 260 */       font.drawString(text.getCharacter(), text.getCodePoints(), this.graphics, 1.0F, at, x, y);
/*     */     }
/*     */     catch (IOException io)
/*     */     {
/* 264 */       LOG.error(io, io);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Graphics2D getGraphics()
/*     */   {
/* 275 */     return this.graphics;
/*     */   }
/*     */ 
/*     */   public PDPage getPage()
/*     */   {
/* 285 */     return this.page;
/*     */   }
/*     */ 
/*     */   public Dimension getPageSize()
/*     */   {
/* 295 */     return this.pageSize;
/*     */   }
/*     */ 
/*     */   public double fixY(double y)
/*     */   {
/* 306 */     return this.pageSize.getHeight() - y;
/*     */   }
/*     */ 
/*     */   public GeneralPath getLinePath()
/*     */   {
/* 316 */     return this.linePath;
/*     */   }
/*     */ 
/*     */   public void setLinePath(GeneralPath newLinePath)
/*     */   {
/* 326 */     if ((this.linePath == null) || (this.linePath.getCurrentPoint() == null))
/*     */     {
/* 328 */       this.linePath = newLinePath;
/*     */     }
/*     */     else
/*     */     {
/* 332 */       this.linePath.append(newLinePath, false);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void fillPath(int windingRule)
/*     */     throws IOException
/*     */   {
/* 346 */     this.graphics.setComposite(getGraphicsState().getNonStrokeJavaComposite());
/* 347 */     Paint nonStrokingPaint = getGraphicsState().getNonStrokingColor().getJavaColor();
/* 348 */     if (nonStrokingPaint == null)
/*     */     {
/* 350 */       nonStrokingPaint = getGraphicsState().getNonStrokingColor().getPaint(this.pageSize.height);
/*     */     }
/* 352 */     if (nonStrokingPaint == null)
/*     */     {
/* 354 */       LOG.info("ColorSpace " + getGraphicsState().getNonStrokingColor().getColorSpace().getName() + " doesn't provide a non-stroking color, using white instead!");
/*     */ 
/* 356 */       nonStrokingPaint = Color.WHITE;
/*     */     }
/* 358 */     this.graphics.setPaint(nonStrokingPaint);
/* 359 */     getLinePath().setWindingRule(windingRule);
/* 360 */     this.graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
/* 361 */     this.graphics.setClip(getGraphicsState().getCurrentClippingPath());
/* 362 */     this.graphics.fill(getLinePath());
/* 363 */     getLinePath().reset();
/*     */   }
/*     */ 
/*     */   public void setStroke(BasicStroke newStroke)
/*     */   {
/* 375 */     this.stroke = newStroke;
/*     */   }
/*     */ 
/*     */   public BasicStroke getStroke()
/*     */   {
/* 386 */     return this.stroke;
/*     */   }
/*     */ 
/*     */   private BasicStroke calculateStroke()
/*     */   {
/* 396 */     float lineWidth = transformWidth((float)getGraphicsState().getLineWidth());
/* 397 */     if (lineWidth < 0.25D)
/*     */     {
/* 399 */       lineWidth = 0.25F;
/*     */     }
/* 401 */     BasicStroke currentStroke = null;
/* 402 */     if (this.stroke == null)
/*     */     {
/* 404 */       currentStroke = new BasicStroke(lineWidth);
/*     */     }
/*     */     else
/*     */     {
/* 408 */       float phaseStart = this.stroke.getDashPhase();
/* 409 */       float[] dashArray = this.stroke.getDashArray();
/* 410 */       if (dashArray != null)
/*     */       {
/* 413 */         for (int i = 0; i < dashArray.length; i++)
/*     */         {
/* 415 */           dashArray[i] = transformWidth(dashArray[i]);
/*     */         }
/* 417 */         phaseStart = (int)transformWidth(phaseStart);
/*     */ 
/* 420 */         if (dashArray.length == 0)
/*     */         {
/* 422 */           dashArray = null;
/*     */         }
/*     */       }
/* 425 */       currentStroke = new BasicStroke(lineWidth, this.stroke.getEndCap(), this.stroke.getLineJoin(), this.stroke.getMiterLimit(), dashArray, phaseStart);
/*     */     }
/*     */ 
/* 428 */     return currentStroke;
/*     */   }
/*     */ 
/*     */   public void strokePath()
/*     */     throws IOException
/*     */   {
/* 438 */     this.graphics.setComposite(getGraphicsState().getStrokeJavaComposite());
/* 439 */     Paint strokingPaint = getGraphicsState().getStrokingColor().getJavaColor();
/* 440 */     if (strokingPaint == null)
/*     */     {
/* 442 */       strokingPaint = getGraphicsState().getStrokingColor().getPaint(this.pageSize.height);
/*     */     }
/* 444 */     if (strokingPaint == null)
/*     */     {
/* 446 */       LOG.info("ColorSpace " + getGraphicsState().getStrokingColor().getColorSpace().getName() + " doesn't provide a stroking color, using white instead!");
/*     */ 
/* 448 */       strokingPaint = Color.WHITE;
/*     */     }
/* 450 */     this.graphics.setPaint(strokingPaint);
/* 451 */     this.graphics.setStroke(calculateStroke());
/* 452 */     this.graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
/* 453 */     this.graphics.setClip(getGraphicsState().getCurrentClippingPath());
/* 454 */     GeneralPath path = getLinePath();
/* 455 */     this.graphics.draw(path);
/* 456 */     path.reset();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void colorChanged(boolean bStroking)
/*     */     throws IOException
/*     */   {
/*     */   }
/*     */ 
/*     */   public Point2D.Double transformedPoint(double x, double y)
/*     */   {
/* 479 */     double[] position = { x, y };
/* 480 */     getGraphicsState().getCurrentTransformationMatrix().createAffineTransform().transform(position, 0, position, 0, 1);
/*     */ 
/* 482 */     position[1] = fixY(position[1]);
/* 483 */     return new Point2D.Double(position[0], position[1]);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void setClippingPath(int windingRule)
/*     */   {
/* 496 */     setClippingWindingRule(windingRule);
/*     */   }
/*     */ 
/*     */   public void setClippingWindingRule(int windingRule)
/*     */   {
/* 507 */     this.clippingWindingRule = windingRule;
/*     */   }
/*     */ 
/*     */   public void endPath()
/*     */   {
/* 516 */     if (this.clippingWindingRule > -1)
/*     */     {
/* 518 */       PDGraphicsState graphicsState = getGraphicsState();
/* 519 */       GeneralPath clippingPath = (GeneralPath)getLinePath().clone();
/* 520 */       clippingPath.setWindingRule(this.clippingWindingRule);
/*     */ 
/* 522 */       if (graphicsState.getCurrentClippingPath() != null)
/*     */       {
/* 524 */         Area currentArea = new Area(getGraphicsState().getCurrentClippingPath());
/* 525 */         Area newArea = new Area(clippingPath);
/* 526 */         currentArea.intersect(newArea);
/* 527 */         graphicsState.setCurrentClippingPath(currentArea);
/*     */       }
/*     */       else
/*     */       {
/* 531 */         graphicsState.setCurrentClippingPath(clippingPath);
/*     */       }
/* 533 */       this.clippingWindingRule = -1;
/*     */     }
/* 535 */     getLinePath().reset();
/*     */   }
/*     */ 
/*     */   public void drawImage(Image awtImage, AffineTransform at)
/*     */   {
/* 547 */     this.graphics.setComposite(getGraphicsState().getStrokeJavaComposite());
/* 548 */     this.graphics.setClip(getGraphicsState().getCurrentClippingPath());
/* 549 */     this.graphics.drawImage(awtImage, at, null);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void SHFill(COSName ShadingName)
/*     */     throws IOException
/*     */   {
/* 563 */     shFill(ShadingName);
/*     */   }
/*     */ 
/*     */   public void shFill(COSName shadingName)
/*     */     throws IOException
/*     */   {
/* 575 */     PDShadingResources shading = (PDShadingResources)getResources().getShadings().get(shadingName.getName());
/* 576 */     LOG.debug("Shading = " + shading.toString());
/* 577 */     int shadingType = shading.getShadingType();
/* 578 */     Matrix ctm = getGraphicsState().getCurrentTransformationMatrix();
/* 579 */     Paint paint = null;
/* 580 */     switch (shadingType)
/*     */     {
/*     */     case 1:
/* 583 */       paint = new Type1ShadingPaint((PDShadingType1)shading, ctm, this.pageSize.height);
/* 584 */       break;
/*     */     case 2:
/* 586 */       paint = new AxialShadingPaint((PDShadingType2)shading, ctm, this.pageSize.height);
/* 587 */       break;
/*     */     case 3:
/* 589 */       paint = new RadialShadingPaint((PDShadingType3)shading, ctm, this.pageSize.height);
/* 590 */       break;
/*     */     case 4:
/* 592 */       paint = new Type4ShadingPaint((PDShadingType4)shading, ctm, this.pageSize.height);
/* 593 */       break;
/*     */     case 5:
/* 595 */       paint = new Type5ShadingPaint((PDShadingType5)shading, ctm, this.pageSize.height);
/* 596 */       break;
/*     */     case 6:
/* 598 */       paint = new Type6ShadingPaint((PDShadingType6)shading, ctm, this.pageSize.height);
/* 599 */       break;
/*     */     case 7:
/* 601 */       paint = new Type7ShadingPaint((PDShadingType7)shading, ctm, this.pageSize.height);
/* 602 */       break;
/*     */     default:
/* 604 */       throw new IOException("Invalid ShadingType " + shadingType + " for Shading " + shadingName);
/*     */     }
/* 606 */     this.graphics.setComposite(getGraphicsState().getNonStrokeJavaComposite());
/* 607 */     this.graphics.setPaint(paint);
/* 608 */     this.graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
/* 609 */     this.graphics.setClip(null);
/* 610 */     this.graphics.fill(getGraphicsState().getCurrentClippingPath());
/*     */   }
/*     */ 
/*     */   protected void SHFill_Function(PDShading Shading)
/*     */     throws IOException
/*     */   {
/* 622 */     throw new IOException("Not Implemented");
/*     */   }
/*     */ 
/*     */   protected void SHFill_Axial(PDShading Shading)
/*     */     throws IOException
/*     */   {
/* 635 */     throw new IOException("Not Implemented");
/*     */   }
/*     */ 
/*     */   protected void SHFill_Radial(PDShading Shading)
/*     */     throws IOException
/*     */   {
/* 649 */     throw new IOException("Not Implemented");
/*     */   }
/*     */ 
/*     */   protected void SHFill_FreeGourad(PDShading Shading)
/*     */     throws IOException
/*     */   {
/* 662 */     throw new IOException("Not Implemented");
/*     */   }
/*     */ 
/*     */   protected void SHFill_LatticeGourad(PDShading Shading)
/*     */     throws IOException
/*     */   {
/* 675 */     throw new IOException("Not Implemented");
/*     */   }
/*     */ 
/*     */   protected void SHFill_CoonsPatch(PDShading Shading)
/*     */     throws IOException
/*     */   {
/* 688 */     throw new IOException("Not Implemented");
/*     */   }
/*     */ 
/*     */   protected void SHFill_TensorPatch(PDShading Shading)
/*     */     throws IOException
/*     */   {
/* 701 */     throw new IOException("Not Implemented");
/*     */   }
/*     */ 
/*     */   private float transformWidth(float width)
/*     */   {
/* 706 */     Matrix ctm = getGraphicsState().getCurrentTransformationMatrix();
/*     */ 
/* 708 */     if (ctm == null)
/*     */     {
/* 711 */       return width;
/*     */     }
/*     */ 
/* 714 */     float x = ctm.getValue(0, 0) + ctm.getValue(1, 0);
/* 715 */     float y = ctm.getValue(0, 1) + ctm.getValue(1, 1);
/* 716 */     return width * (float)Math.sqrt((x * x + y * y) * 0.5D);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdfviewer.PageDrawer
 * JD-Core Version:    0.6.2
 */