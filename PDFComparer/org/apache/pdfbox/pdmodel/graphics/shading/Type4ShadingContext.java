/*     */ package org.apache.pdfbox.pdmodel.graphics.shading;
/*     */ 
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import javax.imageio.stream.ImageInputStream;
/*     */ import javax.imageio.stream.MemoryCacheImageInputStream;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRange;
/*     */ import org.apache.pdfbox.util.Matrix;
/*     */ 
/*     */ class Type4ShadingContext extends GouraudShadingContext
/*     */ {
/*  43 */   private static final Log LOG = LogFactory.getLog(Type4ShadingContext.class);
/*     */   private final int bitsPerFlag;
/*     */ 
/*     */   public Type4ShadingContext(PDShadingType4 shading, ColorModel cm, AffineTransform xform, Matrix ctm, int pageHeight, Rectangle dBounds)
/*     */     throws IOException
/*     */   {
/*  58 */     super(shading, cm, xform, ctm, pageHeight, dBounds);
/*  59 */     LOG.debug("Type4ShadingContext");
/*     */ 
/*  61 */     this.bitsPerFlag = shading.getBitsPerFlag();
/*  62 */     LOG.debug("bitsPerFlag: " + this.bitsPerFlag);
/*     */ 
/*  65 */     xform.scale(1.0D, -1.0D);
/*  66 */     xform.translate(0.0D, -pageHeight);
/*     */ 
/*  68 */     this.triangleList = getTriangleList(xform, ctm);
/*  69 */     this.pixelTable = calcPixelTable();
/*     */   }
/*     */ 
/*     */   private ArrayList<ShadedTriangle> getTriangleList(AffineTransform xform, Matrix ctm) throws IOException
/*     */   {
/*  74 */     PDShadingType4 freeTriangleShadingType = (PDShadingType4)this.shading;
/*  75 */     COSDictionary cosDictionary = freeTriangleShadingType.getCOSDictionary();
/*  76 */     PDRange rangeX = freeTriangleShadingType.getDecodeForParameter(0);
/*  77 */     PDRange rangeY = freeTriangleShadingType.getDecodeForParameter(1);
/*  78 */     PDRange[] colRange = new PDRange[this.numberOfColorComponents];
/*  79 */     for (int i = 0; i < this.numberOfColorComponents; i++)
/*     */     {
/*  81 */       colRange[i] = freeTriangleShadingType.getDecodeForParameter(2 + i);
/*     */     }
/*  83 */     ArrayList list = new ArrayList();
/*  84 */     long maxSrcCoord = ()Math.pow(2.0D, this.bitsPerCoordinate) - 1L;
/*  85 */     long maxSrcColor = ()Math.pow(2.0D, this.bitsPerColorComponent) - 1L;
/*  86 */     COSStream cosStream = (COSStream)cosDictionary;
/*     */ 
/*  88 */     ImageInputStream mciis = new MemoryCacheImageInputStream(cosStream.getUnfilteredStream());
/*  89 */     byte flag = 0;
/*     */     try
/*     */     {
/*  93 */       flag = (byte)(int)(mciis.readBits(this.bitsPerFlag) & 0x3);
/*     */     }
/*     */     catch (EOFException ex)
/*     */     {
/*  97 */       LOG.error(ex);
/*     */     }
/*     */     while (true) {
/*     */       try
/*     */       {
/*     */         Vertex p2;
/*     */         Point2D[] ps;
/*     */         float[][] cs;
/*     */         int lastIndex;
/* 108 */         switch (flag)
/*     */         {
/*     */         case 0:
/* 111 */           Vertex p0 = readVertex(mciis, maxSrcCoord, maxSrcColor, rangeX, rangeY, colRange, ctm, xform);
/* 112 */           flag = (byte)(int)(mciis.readBits(this.bitsPerFlag) & 0x3);
/* 113 */           if (flag != 0)
/*     */           {
/* 115 */             LOG.error("bad triangle: " + flag);
/*     */           }
/* 117 */           Vertex p1 = readVertex(mciis, maxSrcCoord, maxSrcColor, rangeX, rangeY, colRange, ctm, xform);
/* 118 */           mciis.readBits(this.bitsPerFlag);
/* 119 */           if (flag != 0)
/*     */           {
/* 121 */             LOG.error("bad triangle: " + flag);
/*     */           }
/* 123 */           p2 = readVertex(mciis, maxSrcCoord, maxSrcColor, rangeX, rangeY, colRange, ctm, xform);
/* 124 */           ps = new Point2D[] { p0.point, p1.point, p2.point };
/*     */ 
/* 128 */           cs = new float[][] { p0.color, p1.color, p2.color };
/*     */ 
/* 132 */           list.add(new ShadedTriangle(ps, cs));
/* 133 */           flag = (byte)(int)(mciis.readBits(this.bitsPerFlag) & 0x3);
/* 134 */           break;
/*     */         case 1:
/* 136 */           lastIndex = list.size() - 1;
/* 137 */           if (lastIndex < 0)
/*     */           {
/* 139 */             LOG.error("broken data stream: " + list.size());
/*     */           }
/*     */           else
/*     */           {
/* 143 */             ShadedTriangle preTri = (ShadedTriangle)list.get(lastIndex);
/* 144 */             p2 = readVertex(mciis, maxSrcCoord, maxSrcColor, rangeX, rangeY, colRange, ctm, xform);
/* 145 */             ps = new Point2D[] { preTri.corner[1], preTri.corner[2], p2.point };
/*     */ 
/* 149 */             cs = new float[][] { preTri.color[1], preTri.color[2], p2.color };
/*     */ 
/* 153 */             list.add(new ShadedTriangle(ps, cs));
/* 154 */             flag = (byte)(int)(mciis.readBits(this.bitsPerFlag) & 0x3);
/*     */           }
/* 156 */           break;
/*     */         case 2:
/* 158 */           lastIndex = list.size() - 1;
/* 159 */           if (lastIndex < 0)
/*     */           {
/* 161 */             LOG.error("broken data stream: " + list.size());
/*     */           }
/*     */           else
/*     */           {
/* 165 */             ShadedTriangle preTri = (ShadedTriangle)list.get(lastIndex);
/* 166 */             p2 = readVertex(mciis, maxSrcCoord, maxSrcColor, rangeX, rangeY, colRange, ctm, xform);
/* 167 */             ps = new Point2D[] { preTri.corner[0], preTri.corner[2], p2.point };
/*     */ 
/* 171 */             cs = new float[][] { preTri.color[0], preTri.color[2], p2.color };
/*     */ 
/* 175 */             list.add(new ShadedTriangle(ps, cs));
/* 176 */             flag = (byte)(int)(mciis.readBits(this.bitsPerFlag) & 0x3);
/*     */           }
/* 178 */           break;
/*     */         default:
/* 180 */           LOG.warn("bad flag: " + flag);
/*     */         }
/*     */ 
/*     */       }
/*     */       catch (EOFException ex)
/*     */       {
/* 186 */         break;
/*     */       }
/*     */     }
/* 189 */     mciis.close();
/* 190 */     return list;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.shading.Type4ShadingContext
 * JD-Core Version:    0.6.2
 */