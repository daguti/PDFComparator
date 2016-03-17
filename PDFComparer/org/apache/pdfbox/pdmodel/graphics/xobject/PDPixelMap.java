/*     */ package org.apache.pdfbox.pdmodel.graphics.xobject;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.DataBuffer;
/*     */ import java.awt.image.DataBufferByte;
/*     */ import java.awt.image.IndexColorModel;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import javax.imageio.stream.MemoryCacheImageOutputStream;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ import org.apache.pdfbox.pdmodel.common.function.PDFunction;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceGray;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDIndexed;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDSeparation;
/*     */ import org.apache.pdfbox.util.ImageIOUtil;
/*     */ 
/*     */ public class PDPixelMap extends PDXObjectImage
/*     */ {
/*  60 */   private static final Log LOG = LogFactory.getLog(PDPixelMap.class);
/*     */ 
/*  62 */   private BufferedImage image = null;
/*     */   private static final String PNG = "png";
/*     */ 
/*     */   public PDPixelMap(PDStream pdStream)
/*     */   {
/*  72 */     super(pdStream, "png");
/*     */   }
/*     */ 
/*     */   public PDPixelMap(PDDocument doc, BufferedImage bi)
/*     */     throws IOException
/*     */   {
/*  85 */     super(doc, "png");
/*  86 */     createImageStream(doc, bi, false);
/*     */   }
/*     */ 
/*     */   private PDPixelMap(PDDocument doc, BufferedImage bi, boolean isMask)
/*     */     throws IOException
/*     */   {
/* 100 */     super(doc, "png");
/* 101 */     createImageStream(doc, bi, isMask);
/*     */   }
/*     */ 
/*     */   private void createImageStream(PDDocument doc, BufferedImage bi, boolean isMask)
/*     */     throws IOException
/*     */   {
/* 116 */     BufferedImage alphaImage = null;
/*     */ 
/* 118 */     int width = bi.getWidth();
/* 119 */     int height = bi.getHeight();
/*     */     BufferedImage rgbImage;
/* 120 */     if (bi.getColorModel().hasAlpha())
/*     */     {
/* 122 */       alphaImage = extractAlphaImage(bi);
/*     */ 
/* 129 */       BufferedImage rgbImage = new BufferedImage(width, height, 5);
/* 130 */       for (int x = 0; x < width; x++)
/*     */       {
/* 132 */         for (int y = 0; y < height; y++)
/*     */         {
/* 134 */           rgbImage.setRGB(x, y, bi.getRGB(x, y) & 0xFFFFFF);
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 140 */       rgbImage = bi;
/*     */     }
/* 142 */     OutputStream os = null;
/*     */     try
/*     */     {
/* 145 */       int numberOfComponents = rgbImage.getColorModel().getNumComponents();
/* 146 */       if ((numberOfComponents != 3) && (numberOfComponents != 1))
/*     */       {
/* 148 */         throw new IllegalStateException();
/*     */       }
/*     */ 
/* 154 */       getPDStream().addCompression();
/* 155 */       os = getCOSStream().createUnfilteredStream();
/*     */       int bpc;
/* 157 */       if (((isMask) || (bi.getType() == 10) || (bi.getType() == 12)) && (bi.getColorModel().getPixelSize() <= 8))
/*     */       {
/* 162 */         setColorSpace(new PDDeviceGray());
/* 163 */         int bpc = bi.getColorModel().getPixelSize();
/* 164 */         if (bpc < 8)
/*     */         {
/* 166 */           MemoryCacheImageOutputStream mcios = new MemoryCacheImageOutputStream(os);
/* 167 */           for (int y = 0; y < height; y++)
/*     */           {
/* 169 */             for (int x = 0; x < width; x++)
/*     */             {
/* 172 */               mcios.writeBits(bi.getRGB(x, y) & 0xFF, bpc);
/*     */             }
/*     */ 
/* 175 */             while (mcios.getBitOffset() != 0)
/*     */             {
/* 177 */               mcios.writeBit(0);
/*     */             }
/*     */           }
/* 180 */           mcios.flush();
/* 181 */           mcios.close();
/*     */         }
/*     */         else
/*     */         {
/* 187 */           DataBuffer dataBuffer = rgbImage.getData().getDataBuffer();
/* 188 */           for (int y = 0; y < height; y++)
/*     */           {
/* 190 */             for (int x = 0; x < width; x++)
/*     */             {
/* 193 */               os.write(dataBuffer.getElem(y * width + x));
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 201 */         setColorSpace(PDDeviceRGB.INSTANCE);
/* 202 */         bpc = 8;
/* 203 */         for (int y = 0; y < height; y++)
/*     */         {
/* 205 */           for (int x = 0; x < width; x++)
/*     */           {
/* 208 */             Color color = new Color(rgbImage.getRGB(x, y));
/* 209 */             os.write(color.getRed());
/* 210 */             os.write(color.getGreen());
/* 211 */             os.write(color.getBlue());
/*     */           }
/*     */         }
/*     */       }
/* 215 */       COSDictionary dic = getCOSStream();
/* 216 */       dic.setItem(COSName.FILTER, COSName.FLATE_DECODE);
/* 217 */       dic.setItem(COSName.SUBTYPE, COSName.IMAGE);
/* 218 */       dic.setItem(COSName.TYPE, COSName.XOBJECT);
/* 219 */       if (alphaImage != null)
/*     */       {
/* 221 */         PDPixelMap smask = new PDPixelMap(doc, alphaImage, true);
/* 222 */         dic.setItem(COSName.SMASK, smask);
/*     */       }
/* 224 */       setBitsPerComponent(bpc);
/* 225 */       setHeight(height);
/* 226 */       setWidth(width);
/*     */     }
/*     */     finally
/*     */     {
/* 230 */       if (os != null)
/*     */       {
/* 232 */         os.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public BufferedImage getRGBImage()
/*     */     throws IOException
/*     */   {
/* 247 */     if (this.image != null)
/*     */     {
/* 249 */       return this.image;
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 254 */       byte[] array = getPDStream().getByteArray();
/* 255 */       if (array.length == 0)
/*     */       {
/* 257 */         LOG.error("Something went wrong ... the pixelmap doesn't contain any data.");
/* 258 */         return null;
/*     */       }
/* 260 */       int width = getWidth();
/* 261 */       int height = getHeight();
/* 262 */       int bpc = getBitsPerComponent();
/*     */ 
/* 264 */       PDColorSpace colorspace = getColorSpace();
/* 265 */       if (colorspace == null)
/*     */       {
/* 267 */         LOG.error("getColorSpace() returned NULL.");
/* 268 */         return null;
/*     */       }
/*     */       ColorModel cm;
/*     */       ColorModel cm;
/* 272 */       if ((colorspace instanceof PDIndexed))
/*     */       {
/* 274 */         PDIndexed csIndexed = (PDIndexed)colorspace;
/* 275 */         COSBase maskArray = getMask();
/*     */         ColorModel cm;
/* 276 */         if ((maskArray != null) && ((maskArray instanceof COSArray)))
/*     */         {
/* 278 */           cm = csIndexed.createColorModel(bpc, ((COSArray)maskArray).getInt(0));
/*     */         }
/*     */         else
/*     */         {
/* 282 */           cm = csIndexed.createColorModel(bpc);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/*     */         ColorModel cm;
/* 285 */         if ((colorspace instanceof PDSeparation))
/*     */         {
/* 287 */           PDSeparation csSeparation = (PDSeparation)colorspace;
/* 288 */           int numberOfComponents = csSeparation.getAlternateColorSpace().getNumberOfComponents();
/* 289 */           PDFunction tintTransformFunc = csSeparation.getTintTransform();
/* 290 */           COSArray decode = getDecode();
/*     */ 
/* 293 */           boolean invert = (decode != null) && (decode.getInt(0) == 1);
/*     */ 
/* 295 */           int maxValue = (int)Math.pow(2.0D, bpc) - 1;
/*     */ 
/* 297 */           byte[] mappedData = new byte[width * height * numberOfComponents];
/* 298 */           int rowLength = width * numberOfComponents;
/* 299 */           float[] input = new float[1];
/* 300 */           for (int i = 0; i < height; i++)
/*     */           {
/* 302 */             int rowOffset = i * rowLength;
/* 303 */             for (int j = 0; j < width; j++)
/*     */             {
/* 306 */               int value = (array[(i * width + j)] + 256) % 256;
/* 307 */               if (invert)
/*     */               {
/* 309 */                 input[0] = (1 - value / maxValue);
/*     */               }
/*     */               else
/*     */               {
/* 313 */                 input[0] = (value / maxValue);
/*     */               }
/* 315 */               float[] mappedColor = tintTransformFunc.eval(input);
/* 316 */               int columnOffset = j * numberOfComponents;
/* 317 */               for (int k = 0; k < numberOfComponents; k++)
/*     */               {
/* 320 */                 float mappedValue = mappedColor[k];
/* 321 */                 mappedData[(rowOffset + columnOffset + k)] = ((byte)(int)(mappedValue * maxValue));
/*     */               }
/*     */             }
/*     */           }
/* 325 */           array = mappedData;
/* 326 */           cm = colorspace.createColorModel(bpc);
/*     */         }
/*     */         else
/*     */         {
/*     */           ColorModel cm;
/* 328 */           if (bpc == 1)
/*     */           {
/*     */             byte[] map;
/*     */             byte[] map;
/* 331 */             if ((colorspace instanceof PDDeviceGray))
/*     */             {
/* 333 */               COSArray decode = getDecode();
/*     */               byte[] map;
/* 336 */               if ((decode != null) && (decode.getInt(0) == 1))
/*     */               {
/* 338 */                 map = new byte[] { -1 };
/*     */               }
/*     */               else
/*     */               {
/* 342 */                 map = new byte[] { 0, -1 };
/*     */               }
/*     */             }
/*     */             else
/*     */             {
/* 347 */               map = new byte[] { 0, -1 };
/*     */             }
/* 349 */             cm = new IndexColorModel(bpc, map.length, map, map, map, 1);
/*     */           }
/*     */           else
/*     */           {
/* 353 */             cm = colorspace.createColorModel(bpc);
/*     */           }
/*     */         }
/*     */       }
/* 356 */       LOG.debug("ColorModel: " + cm.toString());
/* 357 */       WritableRaster raster = cm.createCompatibleWritableRaster(width, height);
/* 358 */       DataBufferByte buffer = (DataBufferByte)raster.getDataBuffer();
/* 359 */       byte[] bufferData = buffer.getData();
/*     */ 
/* 361 */       System.arraycopy(array, 0, bufferData, 0, array.length < bufferData.length ? array.length : bufferData.length);
/*     */ 
/* 363 */       this.image = new BufferedImage(cm, raster, false, null);
/*     */ 
/* 365 */       return applyMasks(this.image);
/*     */     }
/*     */     catch (Exception exception)
/*     */     {
/* 369 */       LOG.error(exception, exception);
/*     */     }
/*     */ 
/* 372 */     return null;
/*     */   }
/*     */ 
/*     */   public void write2OutputStream(OutputStream out)
/*     */     throws IOException
/*     */   {
/* 385 */     getRGBImage();
/* 386 */     if (this.image != null)
/*     */     {
/* 388 */       ImageIOUtil.writeImage(this.image, "png", out);
/*     */     }
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public COSDictionary getDecodeParams()
/*     */   {
/* 407 */     COSBase decodeParms = getCOSStream().getDictionaryObject(COSName.DECODE_PARMS);
/* 408 */     if (decodeParms != null)
/*     */     {
/* 410 */       if ((decodeParms instanceof COSDictionary))
/*     */       {
/* 412 */         return (COSDictionary)decodeParms;
/*     */       }
/* 414 */       if ((decodeParms instanceof COSArray))
/*     */       {
/* 417 */         return null;
/*     */       }
/*     */ 
/* 421 */       return null;
/*     */     }
/*     */ 
/* 424 */     return null;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public int getPredictor()
/*     */   {
/* 450 */     COSDictionary decodeParms = getDecodeParams();
/* 451 */     if (decodeParms != null)
/*     */     {
/* 453 */       int i = decodeParms.getInt(COSName.PREDICTOR);
/* 454 */       if (i != -1)
/*     */       {
/* 456 */         return i;
/*     */       }
/*     */     }
/* 459 */     return 1;
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 465 */     super.clear();
/* 466 */     this.image = null;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.xobject.PDPixelMap
 * JD-Core Version:    0.6.2
 */