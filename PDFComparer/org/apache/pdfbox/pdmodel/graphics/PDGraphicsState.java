/*     */ package org.apache.pdfbox.pdmodel.graphics;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Composite;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorState;
/*     */ import org.apache.pdfbox.pdmodel.text.PDTextState;
/*     */ import org.apache.pdfbox.util.Matrix;
/*     */ 
/*     */ public class PDGraphicsState
/*     */   implements Cloneable
/*     */ {
/*  42 */   private static final Log LOG = LogFactory.getLog(PDGraphicsState.class);
/*     */ 
/*  44 */   private Matrix currentTransformationMatrix = new Matrix();
/*     */ 
/*  49 */   private PDColorState strokingColor = new PDColorState();
/*  50 */   private PDColorState nonStrokingColor = new PDColorState();
/*  51 */   private PDTextState textState = new PDTextState();
/*  52 */   private double lineWidth = 0.0D;
/*  53 */   private int lineCap = 0;
/*  54 */   private int lineJoin = 0;
/*  55 */   private double miterLimit = 0.0D;
/*     */   private PDLineDashPattern lineDashPattern;
/*     */   private String renderingIntent;
/*  58 */   private boolean strokeAdjustment = false;
/*     */ 
/*  61 */   private double alphaConstants = 1.0D;
/*  62 */   private double nonStrokingAlphaConstants = 1.0D;
/*  63 */   private boolean alphaSource = false;
/*     */ 
/*  66 */   private boolean overprint = false;
/*  67 */   private double overprintMode = 0.0D;
/*     */ 
/*  72 */   private double flatness = 1.0D;
/*  73 */   private double smoothness = 0.0D;
/*     */   private GeneralPath currentClippingPath;
/*     */ 
/*     */   public PDGraphicsState()
/*     */   {
/*     */   }
/*     */ 
/*     */   public PDGraphicsState(PDRectangle page)
/*     */   {
/*  90 */     this.currentClippingPath = new GeneralPath(new Rectangle(page.createDimension()));
/*  91 */     if ((page.getLowerLeftX() != 0.0F) || (page.getLowerLeftY() != 0.0F))
/*     */     {
/*  94 */       this.currentTransformationMatrix = this.currentTransformationMatrix.multiply(Matrix.getTranslatingInstance(-page.getLowerLeftX(), -page.getLowerLeftY()));
/*     */     }
/*     */   }
/*     */ 
/*     */   public Matrix getCurrentTransformationMatrix()
/*     */   {
/* 106 */     return this.currentTransformationMatrix;
/*     */   }
/*     */ 
/*     */   public void setCurrentTransformationMatrix(Matrix value)
/*     */   {
/* 116 */     this.currentTransformationMatrix = value;
/*     */   }
/*     */ 
/*     */   public double getLineWidth()
/*     */   {
/* 126 */     return this.lineWidth;
/*     */   }
/*     */ 
/*     */   public void setLineWidth(double value)
/*     */   {
/* 136 */     this.lineWidth = value;
/*     */   }
/*     */ 
/*     */   public int getLineCap()
/*     */   {
/* 146 */     return this.lineCap;
/*     */   }
/*     */ 
/*     */   public void setLineCap(int value)
/*     */   {
/* 156 */     this.lineCap = value;
/*     */   }
/*     */ 
/*     */   public int getLineJoin()
/*     */   {
/* 166 */     return this.lineJoin;
/*     */   }
/*     */ 
/*     */   public void setLineJoin(int value)
/*     */   {
/* 176 */     this.lineJoin = value;
/*     */   }
/*     */ 
/*     */   public double getMiterLimit()
/*     */   {
/* 186 */     return this.miterLimit;
/*     */   }
/*     */ 
/*     */   public void setMiterLimit(double value)
/*     */   {
/* 196 */     this.miterLimit = value;
/*     */   }
/*     */ 
/*     */   public boolean isStrokeAdjustment()
/*     */   {
/* 206 */     return this.strokeAdjustment;
/*     */   }
/*     */ 
/*     */   public void setStrokeAdjustment(boolean value)
/*     */   {
/* 216 */     this.strokeAdjustment = value;
/*     */   }
/*     */ 
/*     */   public double getAlphaConstants()
/*     */   {
/* 226 */     return this.alphaConstants;
/*     */   }
/*     */ 
/*     */   public void setAlphaConstants(double value)
/*     */   {
/* 236 */     this.alphaConstants = value;
/*     */   }
/*     */ 
/*     */   public double getNonStrokeAlphaConstants()
/*     */   {
/* 246 */     return this.nonStrokingAlphaConstants;
/*     */   }
/*     */ 
/*     */   public void setNonStrokeAlphaConstants(double value)
/*     */   {
/* 256 */     this.nonStrokingAlphaConstants = value;
/*     */   }
/*     */ 
/*     */   public boolean isAlphaSource()
/*     */   {
/* 266 */     return this.alphaSource;
/*     */   }
/*     */ 
/*     */   public void setAlphaSource(boolean value)
/*     */   {
/* 276 */     this.alphaSource = value;
/*     */   }
/*     */ 
/*     */   public boolean isOverprint()
/*     */   {
/* 286 */     return this.overprint;
/*     */   }
/*     */ 
/*     */   public void setOverprint(boolean value)
/*     */   {
/* 296 */     this.overprint = value;
/*     */   }
/*     */ 
/*     */   public double getOverprintMode()
/*     */   {
/* 306 */     return this.overprintMode;
/*     */   }
/*     */ 
/*     */   public void setOverprintMode(double value)
/*     */   {
/* 316 */     this.overprintMode = value;
/*     */   }
/*     */ 
/*     */   public double getFlatness()
/*     */   {
/* 326 */     return this.flatness;
/*     */   }
/*     */ 
/*     */   public void setFlatness(double value)
/*     */   {
/* 336 */     this.flatness = value;
/*     */   }
/*     */ 
/*     */   public double getSmoothness()
/*     */   {
/* 346 */     return this.smoothness;
/*     */   }
/*     */ 
/*     */   public void setSmoothness(double value)
/*     */   {
/* 356 */     this.smoothness = value;
/*     */   }
/*     */ 
/*     */   public PDTextState getTextState()
/*     */   {
/* 366 */     return this.textState;
/*     */   }
/*     */ 
/*     */   public void setTextState(PDTextState value)
/*     */   {
/* 376 */     this.textState = value;
/*     */   }
/*     */ 
/*     */   public PDLineDashPattern getLineDashPattern()
/*     */   {
/* 386 */     return this.lineDashPattern;
/*     */   }
/*     */ 
/*     */   public void setLineDashPattern(PDLineDashPattern value)
/*     */   {
/* 396 */     this.lineDashPattern = value;
/*     */   }
/*     */ 
/*     */   public String getRenderingIntent()
/*     */   {
/* 408 */     return this.renderingIntent;
/*     */   }
/*     */ 
/*     */   public void setRenderingIntent(String value)
/*     */   {
/* 418 */     this.renderingIntent = value;
/*     */   }
/*     */ 
/*     */   public Object clone()
/*     */   {
/* 426 */     PDGraphicsState clone = null;
/*     */     try
/*     */     {
/* 429 */       clone = (PDGraphicsState)super.clone();
/* 430 */       clone.setTextState((PDTextState)this.textState.clone());
/* 431 */       clone.setCurrentTransformationMatrix(this.currentTransformationMatrix.copy());
/* 432 */       clone.strokingColor = ((PDColorState)this.strokingColor.clone());
/* 433 */       clone.nonStrokingColor = ((PDColorState)this.nonStrokingColor.clone());
/* 434 */       if (this.lineDashPattern != null)
/*     */       {
/* 436 */         clone.setLineDashPattern((PDLineDashPattern)this.lineDashPattern.clone());
/*     */       }
/* 438 */       if (this.currentClippingPath != null)
/*     */       {
/* 440 */         clone.setCurrentClippingPath((GeneralPath)this.currentClippingPath.clone());
/*     */       }
/*     */     }
/*     */     catch (CloneNotSupportedException e)
/*     */     {
/* 445 */       LOG.error(e, e);
/*     */     }
/* 447 */     return clone;
/*     */   }
/*     */ 
/*     */   public PDColorState getStrokingColor()
/*     */   {
/* 457 */     return this.strokingColor;
/*     */   }
/*     */ 
/*     */   public PDColorState getNonStrokingColor()
/*     */   {
/* 467 */     return this.nonStrokingColor;
/*     */   }
/*     */ 
/*     */   public void setCurrentClippingPath(Shape pCurrentClippingPath)
/*     */   {
/* 478 */     if (pCurrentClippingPath != null)
/*     */     {
/* 480 */       if ((pCurrentClippingPath instanceof GeneralPath))
/*     */       {
/* 482 */         this.currentClippingPath = ((GeneralPath)pCurrentClippingPath);
/*     */       }
/*     */       else
/*     */       {
/* 486 */         this.currentClippingPath = new GeneralPath();
/* 487 */         this.currentClippingPath.append(pCurrentClippingPath, false);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 492 */       this.currentClippingPath = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Shape getCurrentClippingPath()
/*     */   {
/* 503 */     return this.currentClippingPath;
/*     */   }
/*     */ 
/*     */   public Composite getStrokeJavaComposite()
/*     */   {
/* 508 */     return AlphaComposite.getInstance(3, (float)this.alphaConstants);
/*     */   }
/*     */ 
/*     */   public Composite getNonStrokeJavaComposite()
/*     */   {
/* 513 */     return AlphaComposite.getInstance(3, (float)this.nonStrokingAlphaConstants);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.PDGraphicsState
 * JD-Core Version:    0.6.2
 */