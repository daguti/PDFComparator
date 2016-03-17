/*     */ package org.apache.pdfbox.filter;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.DataBuffer;
/*     */ import java.awt.image.DataBufferByte;
/*     */ import java.awt.image.Raster;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import javax.imageio.ImageIO;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpaceFactory;
/*     */ 
/*     */ public class JPXFilter
/*     */   implements Filter
/*     */ {
/*  45 */   private static final Log LOG = LogFactory.getLog(JPXFilter.class);
/*     */ 
/*     */   public void decode(InputStream compressedData, OutputStream result, COSDictionary options, int filterIndex)
/*     */     throws IOException
/*     */   {
/*  56 */     BufferedImage bi = ImageIO.read(compressedData);
/*  57 */     if (bi != null)
/*     */     {
/*  59 */       DataBuffer dBuf = bi.getData().getDataBuffer();
/*  60 */       if (dBuf.getDataType() == 0)
/*     */       {
/*  63 */         ColorModel colorModel = bi.getColorModel();
/*  64 */         if (options.getItem(COSName.COLORSPACE) == null)
/*     */         {
/*  66 */           options.setItem(COSName.COLORSPACE, PDColorSpaceFactory.createColorSpace(null, colorModel.getColorSpace()));
/*     */         }
/*     */ 
/*  69 */         options.setInt(COSName.BITS_PER_COMPONENT, colorModel.getPixelSize() / colorModel.getNumComponents());
/*  70 */         options.setInt(COSName.HEIGHT, bi.getHeight());
/*  71 */         options.setInt(COSName.WIDTH, bi.getWidth());
/*     */ 
/*  73 */         if (bi.getType() == 5)
/*     */         {
/*  76 */           byte[] byteBuffer = ((DataBufferByte)dBuf).getData();
/*  77 */           for (int i = 0; i < byteBuffer.length; i += 3)
/*     */           {
/*  82 */             byte tmp0 = byteBuffer[i];
/*  83 */             byteBuffer[i] = byteBuffer[(i + 2)];
/*  84 */             byteBuffer[(i + 2)] = tmp0;
/*     */           }
/*  86 */           result.write(byteBuffer);
/*     */         }
/*     */         else
/*     */         {
/*  90 */           result.write(((DataBufferByte)dBuf).getData());
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/*  95 */         LOG.error("Image data buffer not of type byte but type " + dBuf.getDataType());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void encode(InputStream rawData, OutputStream result, COSDictionary options, int filterIndex)
/*     */     throws IOException
/*     */   {
/* 106 */     LOG.error("Warning: JPXFilter.encode is not implemented yet, skipping this stream.");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.filter.JPXFilter
 * JD-Core Version:    0.6.2
 */