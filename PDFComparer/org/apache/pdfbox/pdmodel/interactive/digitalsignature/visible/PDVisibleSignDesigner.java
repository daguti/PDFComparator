/*     */ package org.apache.pdfbox.pdmodel.interactive.digitalsignature.visible;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.List;
/*     */ import javax.imageio.ImageIO;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
/*     */ import org.apache.pdfbox.pdmodel.PDPage;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*     */ 
/*     */ public class PDVisibleSignDesigner
/*     */ {
/*     */   private Float sigImgWidth;
/*     */   private Float sigImgHeight;
/*     */   private float xAxis;
/*     */   private float yAxis;
/*     */   private float pageHeight;
/*     */   private float pageWidth;
/*     */   private InputStream imgageStream;
/*  52 */   private String signatureFieldName = "sig";
/*  53 */   private byte[] formaterRectangleParams = { 0, 0, 100, 50 };
/*  54 */   private byte[] AffineTransformParams = { 1, 0, 0, 1, 0, 0 };
/*     */   private float imageSizeInPercents;
/*  56 */   private PDDocument document = null;
/*     */ 
/*     */   public PDVisibleSignDesigner(InputStream originalDocumenStream, InputStream imageStream, int page)
/*     */     throws IOException
/*     */   {
/*  67 */     signatureImageStream(imageStream);
/*  68 */     this.document = PDDocument.load(originalDocumenStream);
/*  69 */     calculatePageSize(this.document, page);
/*     */   }
/*     */ 
/*     */   public PDVisibleSignDesigner(String documentPath, InputStream imageStream, int page)
/*     */     throws IOException
/*     */   {
/*  82 */     signatureImageStream(imageStream);
/*     */ 
/*  85 */     this.document = PDDocument.load(documentPath);
/*     */ 
/*  88 */     calculatePageSize(this.document, page);
/*     */ 
/*  90 */     this.document.close();
/*     */   }
/*     */ 
/*     */   public PDVisibleSignDesigner(PDDocument doc, InputStream imageStream, int page)
/*     */     throws IOException
/*     */   {
/* 102 */     signatureImageStream(imageStream);
/* 103 */     calculatePageSize(doc, page);
/*     */   }
/*     */ 
/*     */   private void calculatePageSize(PDDocument document, int page)
/*     */   {
/* 114 */     if (page < 1)
/*     */     {
/* 116 */       throw new IllegalArgumentException("First page of pdf is 1, not " + page);
/*     */     }
/*     */ 
/* 119 */     List pages = document.getDocumentCatalog().getAllPages();
/* 120 */     PDPage firstPage = (PDPage)pages.get(page - 1);
/* 121 */     PDRectangle mediaBox = firstPage.findMediaBox();
/* 122 */     pageHeight(mediaBox.getHeight());
/* 123 */     this.pageWidth = mediaBox.getWidth();
/*     */ 
/* 125 */     float x = this.pageWidth;
/* 126 */     float y = 0.0F;
/* 127 */     this.pageWidth += y;
/* 128 */     float tPercent = 100.0F * y / (x + y);
/* 129 */     this.imageSizeInPercents = (100.0F - tPercent);
/*     */   }
/*     */ 
/*     */   public PDVisibleSignDesigner signatureImage(String path)
/*     */     throws IOException
/*     */   {
/* 141 */     InputStream fin = new FileInputStream(path);
/* 142 */     return signatureImageStream(fin);
/*     */   }
/*     */ 
/*     */   public PDVisibleSignDesigner zoom(float percent)
/*     */   {
/* 153 */     this.sigImgHeight = Float.valueOf(this.sigImgHeight.floatValue() + this.sigImgHeight.floatValue() * percent / 100.0F);
/* 154 */     this.sigImgWidth = Float.valueOf(this.sigImgWidth.floatValue() + this.sigImgWidth.floatValue() * percent / 100.0F);
/* 155 */     return this;
/*     */   }
/*     */ 
/*     */   public PDVisibleSignDesigner coordinates(float x, float y)
/*     */   {
/* 166 */     xAxis(x);
/* 167 */     yAxis(y);
/* 168 */     return this;
/*     */   }
/*     */ 
/*     */   public float getxAxis()
/*     */   {
/* 177 */     return this.xAxis;
/*     */   }
/*     */ 
/*     */   public PDVisibleSignDesigner xAxis(float xAxis)
/*     */   {
/* 187 */     this.xAxis = xAxis;
/* 188 */     return this;
/*     */   }
/*     */ 
/*     */   public float getyAxis()
/*     */   {
/* 197 */     return this.yAxis;
/*     */   }
/*     */ 
/*     */   public PDVisibleSignDesigner yAxis(float yAxis)
/*     */   {
/* 207 */     this.yAxis = yAxis;
/* 208 */     return this;
/*     */   }
/*     */ 
/*     */   public float getWidth()
/*     */   {
/* 217 */     return this.sigImgWidth.floatValue();
/*     */   }
/*     */ 
/*     */   public PDVisibleSignDesigner width(float signatureImgWidth)
/*     */   {
/* 227 */     this.sigImgWidth = Float.valueOf(signatureImgWidth);
/* 228 */     return this;
/*     */   }
/*     */ 
/*     */   public float getHeight()
/*     */   {
/* 237 */     return this.sigImgHeight.floatValue();
/*     */   }
/*     */ 
/*     */   public PDVisibleSignDesigner height(float signatureImgHeight)
/*     */   {
/* 247 */     this.sigImgHeight = Float.valueOf(signatureImgHeight);
/* 248 */     return this;
/*     */   }
/*     */ 
/*     */   protected float getTemplateHeight()
/*     */   {
/* 257 */     return getPageHeight();
/*     */   }
/*     */ 
/*     */   private PDVisibleSignDesigner pageHeight(float templateHeight)
/*     */   {
/* 267 */     this.pageHeight = templateHeight;
/* 268 */     return this;
/*     */   }
/*     */ 
/*     */   public String getSignatureFieldName()
/*     */   {
/* 277 */     return this.signatureFieldName;
/*     */   }
/*     */ 
/*     */   public PDVisibleSignDesigner signatureFieldName(String signatureFieldName)
/*     */   {
/* 287 */     this.signatureFieldName = signatureFieldName;
/* 288 */     return this;
/*     */   }
/*     */ 
/*     */   public InputStream getImageStream()
/*     */   {
/* 297 */     return this.imgageStream;
/*     */   }
/*     */ 
/*     */   private PDVisibleSignDesigner signatureImageStream(InputStream imageStream)
/*     */     throws IOException
/*     */   {
/* 308 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 309 */     byte[] buffer = new byte[1024];
/*     */     int len;
/* 311 */     while ((len = imageStream.read(buffer)) > -1)
/*     */     {
/* 313 */       baos.write(buffer, 0, len);
/*     */     }
/* 315 */     baos.flush();
/* 316 */     baos.close();
/*     */ 
/* 318 */     byte[] byteArray = baos.toByteArray();
/* 319 */     byte[] byteArraySecond = new byte[byteArray.length];
/* 320 */     System.arraycopy(byteArray, 0, byteArraySecond, 0, byteArray.length);
/*     */ 
/* 322 */     InputStream inputForBufferedImage = new ByteArrayInputStream(byteArray);
/* 323 */     InputStream revertInputStream = new ByteArrayInputStream(byteArraySecond);
/*     */ 
/* 325 */     if ((this.sigImgHeight == null) || (this.sigImgWidth == null))
/*     */     {
/* 327 */       calcualteImageSize(inputForBufferedImage);
/*     */     }
/*     */ 
/* 330 */     this.imgageStream = revertInputStream;
/*     */ 
/* 332 */     return this;
/*     */   }
/*     */ 
/*     */   private void calcualteImageSize(InputStream fis)
/*     */     throws IOException
/*     */   {
/* 344 */     BufferedImage bimg = ImageIO.read(fis);
/* 345 */     int width = bimg.getWidth();
/* 346 */     int height = bimg.getHeight();
/*     */ 
/* 348 */     this.sigImgHeight = Float.valueOf(height);
/* 349 */     this.sigImgWidth = Float.valueOf(width);
/*     */   }
/*     */ 
/*     */   public byte[] getAffineTransformParams()
/*     */   {
/* 359 */     return this.AffineTransformParams;
/*     */   }
/*     */ 
/*     */   public PDVisibleSignDesigner affineTransformParams(byte[] affineTransformParams)
/*     */   {
/* 369 */     this.AffineTransformParams = affineTransformParams;
/* 370 */     return this;
/*     */   }
/*     */ 
/*     */   public byte[] getFormaterRectangleParams()
/*     */   {
/* 379 */     return this.formaterRectangleParams;
/*     */   }
/*     */ 
/*     */   public PDVisibleSignDesigner formaterRectangleParams(byte[] formaterRectangleParams)
/*     */   {
/* 390 */     this.formaterRectangleParams = formaterRectangleParams;
/* 391 */     return this;
/*     */   }
/*     */ 
/*     */   public float getPageWidth()
/*     */   {
/* 400 */     return this.pageWidth;
/*     */   }
/*     */ 
/*     */   public PDVisibleSignDesigner pageWidth(float pageWidth)
/*     */   {
/* 410 */     this.pageWidth = pageWidth;
/* 411 */     return this;
/*     */   }
/*     */ 
/*     */   public float getPageHeight()
/*     */   {
/* 420 */     return this.pageHeight;
/*     */   }
/*     */ 
/*     */   public float getImageSizeInPercents()
/*     */   {
/* 429 */     return this.imageSizeInPercents;
/*     */   }
/*     */ 
/*     */   public void imageSizeInPercents(float imageSizeInPercents)
/*     */   {
/* 438 */     this.imageSizeInPercents = imageSizeInPercents;
/*     */   }
/*     */ 
/*     */   public String getSignatureText()
/*     */   {
/* 447 */     throw new UnsupportedOperationException("That method is not yet implemented");
/*     */   }
/*     */ 
/*     */   public PDVisibleSignDesigner signatureText(String signatureText)
/*     */   {
/* 457 */     throw new UnsupportedOperationException("That method is not yet implemented");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.digitalsignature.visible.PDVisibleSignDesigner
 * JD-Core Version:    0.6.2
 */