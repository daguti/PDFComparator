/*     */ package org.apache.pdfbox.pdmodel.graphics.xobject;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.DataBuffer;
/*     */ import java.awt.image.DataBufferByte;
/*     */ import java.awt.image.DataBufferInt;
/*     */ import java.awt.image.IndexColorModel;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.filter.Filter;
/*     */ import org.apache.pdfbox.filter.FilterManager;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
/*     */ import org.apache.pdfbox.util.ImageParameters;
/*     */ 
/*     */ public class PDInlinedImage
/*     */ {
/*  52 */   private static final Log LOG = LogFactory.getLog(PDInlinedImage.class);
/*     */   private ImageParameters params;
/*     */   private byte[] imageData;
/*     */ 
/*     */   public ImageParameters getImageParameters()
/*     */   {
/*  64 */     return this.params;
/*     */   }
/*     */ 
/*     */   public void setImageParameters(ImageParameters imageParams)
/*     */   {
/*  74 */     this.params = imageParams;
/*     */   }
/*     */ 
/*     */   public byte[] getImageData()
/*     */   {
/*  84 */     return this.imageData;
/*     */   }
/*     */ 
/*     */   public void setImageData(byte[] value)
/*     */   {
/*  94 */     this.imageData = value;
/*     */   }
/*     */ 
/*     */   public BufferedImage createImage()
/*     */     throws IOException
/*     */   {
/* 107 */     return createImage(null);
/*     */   }
/*     */ 
/*     */   public BufferedImage createImage(Map colorSpaces)
/*     */     throws IOException
/*     */   {
/* 146 */     PDColorSpace pcs = this.params.getColorSpace(colorSpaces);
/*     */     ColorModel colorModel;
/*     */     ColorModel colorModel;
/* 148 */     if (pcs != null)
/*     */     {
/* 150 */       colorModel = pcs.createColorModel(this.params.getBitsPerComponent());
/*     */     }
/*     */     else
/*     */     {
/* 156 */       byte[] transparentColors = { -1, -1 };
/*     */ 
/* 158 */       byte[] colors = { 0, -1 };
/* 159 */       colorModel = new IndexColorModel(1, 2, colors, colors, colors, transparentColors);
/*     */     }
/*     */ 
/* 163 */     boolean invert = false;
/*     */ 
/* 165 */     COSBase dictObj = this.params.getDictionary().getDictionaryObject(COSName.DECODE, COSName.D);
/* 166 */     if ((dictObj != null) && ((dictObj instanceof COSArray)))
/*     */     {
/* 168 */       COSArray decode = (COSArray)dictObj;
/* 169 */       if (decode.getInt(0) == 1)
/*     */       {
/* 171 */         if (this.params.getBitsPerComponent() == 1)
/*     */         {
/* 174 */           invert = true;
/*     */         }
/*     */         else
/*     */         {
/* 179 */           LOG.warn("decode array is not implemented for BPC > 1");
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 184 */     List filters = this.params.getFilters();
/*     */     byte[] finalData;
/*     */     byte[] finalData;
/* 186 */     if ((filters == null) || (filters.isEmpty()))
/*     */     {
/* 188 */       finalData = getImageData();
/*     */     }
/*     */     else
/*     */     {
/* 192 */       ByteArrayInputStream in = new ByteArrayInputStream(getImageData());
/* 193 */       ByteArrayOutputStream out = new ByteArrayOutputStream(getImageData().length);
/* 194 */       FilterManager filterManager = new FilterManager();
/* 195 */       for (int i = 0; i < filters.size(); i++)
/*     */       {
/* 197 */         out.reset();
/* 198 */         Filter filter = filterManager.getFilter((String)filters.get(i));
/* 199 */         filter.decode(in, out, this.params.getDictionary(), i);
/* 200 */         in = new ByteArrayInputStream(out.toByteArray());
/*     */       }
/* 202 */       finalData = out.toByteArray();
/*     */     }
/*     */ 
/* 205 */     WritableRaster raster = colorModel.createCompatibleWritableRaster(this.params.getWidth(), this.params.getHeight());
/*     */ 
/* 213 */     DataBuffer rasterBuffer = raster.getDataBuffer();
/* 214 */     if ((rasterBuffer instanceof DataBufferByte))
/*     */     {
/* 216 */       DataBufferByte byteBuffer = (DataBufferByte)rasterBuffer;
/* 217 */       byte[] data = byteBuffer.getData();
/* 218 */       System.arraycopy(finalData, 0, data, 0, data.length);
/* 219 */       if (invert)
/*     */       {
/* 221 */         invertBitmap(data);
/*     */       }
/*     */     }
/* 224 */     else if ((rasterBuffer instanceof DataBufferInt))
/*     */     {
/* 226 */       DataBufferInt byteBuffer = (DataBufferInt)rasterBuffer;
/* 227 */       int[] data = byteBuffer.getData();
/* 228 */       for (int i = 0; i < finalData.length; i++)
/*     */       {
/* 230 */         data[i] = ((finalData[i] + 256) % 256);
/* 231 */         if (invert)
/*     */         {
/* 233 */           data[i] = ((data[i] ^ 0xFFFFFFFF) & 0xFF);
/*     */         }
/*     */       }
/*     */     }
/* 237 */     BufferedImage image = new BufferedImage(colorModel, raster, false, null);
/* 238 */     image.setData(raster);
/* 239 */     return image;
/*     */   }
/*     */ 
/*     */   private void invertBitmap(byte[] bufferData)
/*     */   {
/* 244 */     int i = 0; for (int c = bufferData.length; i < c; i++)
/*     */     {
/* 246 */       bufferData[i] = ((byte)((bufferData[i] ^ 0xFFFFFFFF) & 0xFF));
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.xobject.PDInlinedImage
 * JD-Core Version:    0.6.2
 */