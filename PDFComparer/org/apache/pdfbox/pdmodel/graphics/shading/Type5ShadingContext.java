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
/*     */ class Type5ShadingContext extends GouraudShadingContext
/*     */ {
/*  43 */   private static final Log LOG = LogFactory.getLog(Type5ShadingContext.class);
/*     */ 
/*     */   public Type5ShadingContext(PDShadingType5 shading, ColorModel cm, AffineTransform xform, Matrix ctm, int pageHeight, Rectangle dBounds)
/*     */     throws IOException
/*     */   {
/*  58 */     super(shading, cm, xform, ctm, pageHeight, dBounds);
/*     */ 
/*  60 */     LOG.debug("Type5ShadingContext");
/*     */ 
/*  63 */     xform.scale(1.0D, -1.0D);
/*  64 */     xform.translate(0.0D, -pageHeight);
/*     */ 
/*  66 */     this.triangleList = getTriangleList(xform, ctm);
/*  67 */     this.pixelTable = calcPixelTable();
/*     */   }
/*     */ 
/*     */   private ArrayList<ShadedTriangle> getTriangleList(AffineTransform xform, Matrix ctm) throws IOException
/*     */   {
/*  72 */     ArrayList list = new ArrayList();
/*  73 */     PDShadingType5 latticeTriangleShadingType = (PDShadingType5)this.shading;
/*  74 */     COSDictionary cosDictionary = latticeTriangleShadingType.getCOSDictionary();
/*  75 */     PDRange rangeX = latticeTriangleShadingType.getDecodeForParameter(0);
/*  76 */     PDRange rangeY = latticeTriangleShadingType.getDecodeForParameter(1);
/*  77 */     int numPerRow = latticeTriangleShadingType.getVerticesPerRow();
/*  78 */     PDRange[] colRange = new PDRange[this.numberOfColorComponents];
/*  79 */     for (int i = 0; i < this.numberOfColorComponents; i++)
/*     */     {
/*  81 */       colRange[i] = latticeTriangleShadingType.getDecodeForParameter(2 + i);
/*     */     }
/*  83 */     ArrayList vlist = new ArrayList();
/*  84 */     long maxSrcCoord = ()Math.pow(2.0D, this.bitsPerCoordinate) - 1L;
/*  85 */     long maxSrcColor = ()Math.pow(2.0D, this.bitsPerColorComponent) - 1L;
/*  86 */     COSStream cosStream = (COSStream)cosDictionary;
/*     */ 
/*  88 */     ImageInputStream mciis = new MemoryCacheImageInputStream(cosStream.getUnfilteredStream());
/*     */     while (true)
/*     */     {
/*     */       try
/*     */       {
/*  94 */         Vertex p = readVertex(mciis, maxSrcCoord, maxSrcColor, rangeX, rangeY, colRange, ctm, xform);
/*  95 */         vlist.add(p);
/*     */       }
/*     */       catch (EOFException ex)
/*     */       {
/*  99 */         break;
/*     */       }
/*     */     }
/* 102 */     int sz = vlist.size(); int rowNum = sz / numPerRow;
/* 103 */     Vertex[][] latticeArray = new Vertex[rowNum][numPerRow];
/* 104 */     if (rowNum < 2)
/*     */     {
/* 106 */       return this.triangleList;
/*     */     }
/* 108 */     for (int i = 0; i < rowNum; i++)
/*     */     {
/* 110 */       for (int j = 0; j < numPerRow; j++)
/*     */       {
/* 112 */         latticeArray[i][j] = ((Vertex)vlist.get(i * numPerRow + j));
/*     */       }
/*     */     }
/*     */ 
/* 116 */     for (int i = 0; i < rowNum - 1; i++)
/*     */     {
/* 118 */       for (int j = 0; j < numPerRow - 1; j++)
/*     */       {
/* 120 */         Point2D[] ps = { latticeArray[i][j].point, latticeArray[i][(j + 1)].point, latticeArray[(i + 1)][j].point };
/*     */ 
/* 124 */         float[][] cs = { latticeArray[i][j].color, latticeArray[i][(j + 1)].color, latticeArray[(i + 1)][j].color };
/*     */ 
/* 128 */         list.add(new ShadedTriangle(ps, cs));
/* 129 */         ps = new Point2D[] { latticeArray[i][(j + 1)].point, latticeArray[(i + 1)][j].point, latticeArray[(i + 1)][(j + 1)].point };
/*     */ 
/* 133 */         cs = new float[][] { latticeArray[i][(j + 1)].color, latticeArray[(i + 1)][j].color, latticeArray[(i + 1)][(j + 1)].color };
/*     */ 
/* 137 */         list.add(new ShadedTriangle(ps, cs));
/*     */       }
/*     */     }
/* 140 */     return list;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.shading.Type5ShadingContext
 * JD-Core Version:    0.6.2
 */