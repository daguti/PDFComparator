/*     */ package org.apache.pdfbox.pdmodel.graphics.xobject;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.color.ColorSpace;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.ComponentColorModel;
/*     */ import java.awt.image.DataBufferByte;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.imageio.IIOException;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.imageio.ImageReader;
/*     */ import javax.imageio.metadata.IIOMetadata;
/*     */ import javax.imageio.stream.ImageInputStream;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.io.IOUtils;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ import org.apache.pdfbox.pdmodel.common.function.PDFunction;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceCMYK;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceGray;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceN;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDICCBased;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDSeparation;
/*     */ import org.apache.pdfbox.util.ImageIOUtil;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class PDJpeg extends PDXObjectImage
/*     */ {
/*  71 */   private BufferedImage image = null;
/*     */   private static final String JPG = "jpg";
/*  75 */   private static final List<String> DCT_FILTERS = new ArrayList();
/*     */   private static final float DEFAULT_COMPRESSION_LEVEL = 0.75F;
/*     */ 
/*     */   public PDJpeg(PDStream jpeg)
/*     */   {
/*  92 */     super(jpeg, "jpg");
/*     */   }
/*     */ 
/*     */   public PDJpeg(PDDocument doc, InputStream is)
/*     */     throws IOException
/*     */   {
/* 104 */     super(new PDStream(doc, is, true), "jpg");
/* 105 */     COSDictionary dic = getCOSStream();
/* 106 */     dic.setItem(COSName.FILTER, COSName.DCT_DECODE);
/* 107 */     dic.setItem(COSName.SUBTYPE, COSName.IMAGE);
/* 108 */     dic.setItem(COSName.TYPE, COSName.XOBJECT);
/*     */ 
/* 110 */     getRGBImage();
/* 111 */     if (this.image != null)
/*     */     {
/* 113 */       setPropertiesFromAWT(this.image);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void setPropertiesFromAWT(BufferedImage image) throws IllegalStateException
/*     */   {
/* 119 */     setBitsPerComponent(8);
/* 120 */     if (image.getColorModel().getNumComponents() == 3)
/*     */     {
/* 122 */       setColorSpace(PDDeviceRGB.INSTANCE);
/*     */     }
/* 126 */     else if (image.getColorModel().getNumComponents() == 1)
/*     */     {
/* 128 */       setColorSpace(new PDDeviceGray());
/*     */     }
/*     */     else
/*     */     {
/* 132 */       throw new IllegalStateException("");
/*     */     }
/*     */ 
/* 135 */     setHeight(image.getHeight());
/* 136 */     setWidth(image.getWidth());
/*     */   }
/*     */ 
/*     */   public PDJpeg(PDDocument doc, BufferedImage bi)
/*     */     throws IOException
/*     */   {
/* 149 */     super(new PDStream(doc), "jpg");
/* 150 */     createImageStream(doc, bi, 0.75F);
/*     */   }
/*     */ 
/*     */   public PDJpeg(PDDocument doc, BufferedImage bi, float compressionQuality)
/*     */     throws IOException
/*     */   {
/* 163 */     super(new PDStream(doc), "jpg");
/* 164 */     createImageStream(doc, bi, compressionQuality);
/*     */   }
/*     */ 
/*     */   private void createImageStream(PDDocument doc, BufferedImage bi, float compressionQuality) throws IOException
/*     */   {
/* 169 */     BufferedImage alphaImage = null;
/* 170 */     if (bi.getColorModel().hasAlpha())
/*     */     {
/* 172 */       if (bi.getTransparency() == 2)
/*     */       {
/* 174 */         throw new UnsupportedOperationException("BITMASK Transparency JPEG compression is not useful, use PDPixelMap instead");
/*     */       }
/* 176 */       alphaImage = extractAlphaImage(bi);
/*     */ 
/* 179 */       BufferedImage img = new BufferedImage(bi.getWidth(), bi.getHeight(), 1);
/* 180 */       Graphics2D g = img.createGraphics();
/* 181 */       g.setComposite(AlphaComposite.Src);
/* 182 */       g.drawImage(bi, 0, 0, null);
/* 183 */       g.dispose();
/* 184 */       bi = img;
/*     */     }
/*     */ 
/* 187 */     OutputStream os = getCOSStream().createFilteredStream();
/*     */     try
/*     */     {
/* 190 */       ImageIOUtil.writeImage(bi, "jpg", os, 72, compressionQuality);
/*     */ 
/* 192 */       COSDictionary dic = getCOSStream();
/* 193 */       dic.setItem(COSName.FILTER, COSName.DCT_DECODE);
/* 194 */       dic.setItem(COSName.SUBTYPE, COSName.IMAGE);
/* 195 */       dic.setItem(COSName.TYPE, COSName.XOBJECT);
/* 196 */       if (alphaImage != null)
/*     */       {
/* 198 */         PDXObjectImage alphaPdImage = new PDJpeg(doc, alphaImage, compressionQuality);
/* 199 */         dic.setItem(COSName.SMASK, alphaPdImage);
/*     */       }
/* 201 */       setPropertiesFromAWT(bi);
/*     */     }
/*     */     finally
/*     */     {
/* 205 */       os.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public BufferedImage getRGBImage()
/*     */     throws IOException
/*     */   {
/* 217 */     if (this.image != null)
/*     */     {
/* 220 */       return this.image;
/*     */     }
/*     */ 
/* 223 */     BufferedImage bi = null;
/* 224 */     boolean readError = false;
/* 225 */     ByteArrayOutputStream os = new ByteArrayOutputStream();
/* 226 */     removeAllFiltersButDCT(os);
/* 227 */     os.close();
/* 228 */     byte[] img = os.toByteArray();
/*     */ 
/* 230 */     PDColorSpace cs = getColorSpace();
/*     */     try
/*     */     {
/* 233 */       if (((cs instanceof PDDeviceCMYK)) || (((cs instanceof PDICCBased)) && (cs.getNumberOfComponents() == 4)))
/*     */       {
/* 237 */         int transform = getApp14AdobeTransform(img);
/*     */ 
/* 239 */         if (transform == 0)
/*     */         {
/* 241 */           bi = convertCMYK2RGB(readImage(img), cs);
/*     */         }
/* 243 */         else if (transform != 1)
/*     */         {
/* 247 */           if (transform == 2)
/*     */           {
/* 249 */             bi = convertYCCK2RGB(readImage(img));
/*     */           }
/*     */         }
/* 252 */       } else if ((cs instanceof PDSeparation))
/*     */       {
/* 255 */         bi = processTintTransformation(readImage(img), ((PDSeparation)cs).getTintTransform(), cs.getJavaColorSpace());
/*     */       }
/* 258 */       else if ((cs instanceof PDDeviceN))
/*     */       {
/* 261 */         bi = processTintTransformation(readImage(img), ((PDDeviceN)cs).getTintTransform(), cs.getJavaColorSpace());
/*     */       }
/*     */       else
/*     */       {
/* 266 */         ByteArrayInputStream bai = new ByteArrayInputStream(img);
/* 267 */         bi = ImageIO.read(bai);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (IIOException exception)
/*     */     {
/* 273 */       readError = true;
/*     */     }
/*     */ 
/* 278 */     if ((bi == null) && (readError))
/*     */     {
/* 280 */       byte[] newImage = replaceHeader(img);
/* 281 */       ByteArrayInputStream bai = new ByteArrayInputStream(newImage);
/* 282 */       bi = ImageIO.read(bai);
/*     */     }
/*     */ 
/* 285 */     this.image = applyMasks(bi);
/* 286 */     return this.image;
/*     */   }
/*     */ 
/*     */   public void write2OutputStream(OutputStream out)
/*     */     throws IOException
/*     */   {
/* 295 */     String colorSpaceName = getColorSpace().getName();
/* 296 */     if (("DeviceGray".equals(colorSpaceName)) || ("DeviceRGB".equals(colorSpaceName)))
/*     */     {
/* 301 */       removeAllFiltersButDCT(out);
/* 302 */       return;
/*     */     }
/*     */ 
/* 307 */     getRGBImage();
/* 308 */     if (this.image != null)
/*     */     {
/* 310 */       ImageIOUtil.writeImage(this.image, "jpg", out);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void removeAllFiltersButDCT(OutputStream out)
/*     */     throws IOException
/*     */   {
/* 317 */     InputStream data = getPDStream().getPartiallyFilteredStream(DCT_FILTERS);
/* 318 */     byte[] buf = new byte[1024];
/*     */     int amountRead;
/* 320 */     while ((amountRead = data.read(buf)) != -1)
/*     */     {
/* 322 */       out.write(buf, 0, amountRead);
/*     */     }
/* 324 */     IOUtils.closeQuietly(data);
/*     */   }
/*     */ 
/*     */   private int getHeaderEndPos(byte[] imageAsBytes)
/*     */   {
/* 329 */     for (int i = 0; i < imageAsBytes.length; i++)
/*     */     {
/* 331 */       byte b = imageAsBytes[i];
/* 332 */       if (b == -37)
/*     */       {
/* 335 */         return i - 2;
/*     */       }
/*     */     }
/* 338 */     return 0;
/*     */   }
/*     */ 
/*     */   private byte[] replaceHeader(byte[] imageAsBytes)
/*     */   {
/* 344 */     int pos = getHeaderEndPos(imageAsBytes);
/*     */ 
/* 347 */     byte[] header = { -1, -40, -1, -32, 0, 16, 74, 70, 73, 70, 0, 1, 1, 1, 0, 96, 0, 96, 0, 0 };
/*     */ 
/* 352 */     byte[] newImage = new byte[imageAsBytes.length - pos + header.length - 1];
/* 353 */     System.arraycopy(header, 0, newImage, 0, header.length);
/* 354 */     System.arraycopy(imageAsBytes, pos + 1, newImage, header.length, imageAsBytes.length - pos - 1);
/*     */ 
/* 356 */     return newImage;
/*     */   }
/*     */ 
/*     */   private int getApp14AdobeTransform(byte[] bytes)
/*     */   {
/* 361 */     int transformType = 0;
/* 362 */     ImageReader reader = null;
/* 363 */     ImageInputStream input = null;
/*     */     try
/*     */     {
/* 366 */       input = ImageIO.createImageInputStream(new ByteArrayInputStream(bytes));
/* 367 */       Iterator readers = ImageIO.getImageReaders(input);
/* 368 */       if ((readers == null) || (!readers.hasNext()))
/*     */       {
/* 370 */         throw new RuntimeException("No ImageReaders found");
/*     */       }
/* 372 */       reader = (ImageReader)readers.next();
/* 373 */       reader.setInput(input);
/* 374 */       IIOMetadata meta = reader.getImageMetadata(0);
/* 375 */       if (meta != null)
/*     */       {
/* 377 */         Node tree = meta.getAsTree("javax_imageio_jpeg_image_1.0");
/* 378 */         NodeList children = tree.getChildNodes();
/* 379 */         for (int i = 0; i < children.getLength(); i++)
/*     */         {
/* 381 */           Node markerSequence = children.item(i);
/* 382 */           if ("markerSequence".equals(markerSequence.getNodeName()))
/*     */           {
/* 384 */             NodeList markerSequenceChildren = markerSequence.getChildNodes();
/* 385 */             for (int j = 0; j < markerSequenceChildren.getLength(); j++)
/*     */             {
/* 387 */               Node child = markerSequenceChildren.item(j);
/* 388 */               if (("app14Adobe".equals(child.getNodeName())) && (child.hasAttributes()))
/*     */               {
/* 390 */                 NamedNodeMap attribs = child.getAttributes();
/* 391 */                 Node transformNode = attribs.getNamedItem("transform");
/* 392 */                 transformType = Integer.parseInt(transformNode.getNodeValue());
/* 393 */                 break;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (IOException exception)
/*     */     {
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 408 */         if (input != null)
/*     */         {
/* 410 */           input.close();
/*     */         }
/*     */       }
/*     */       catch (IOException exception)
/*     */       {
/*     */       }
/*     */ 
/* 417 */       if (reader != null)
/*     */       {
/* 419 */         reader.dispose();
/*     */       }
/*     */     }
/* 422 */     return transformType;
/*     */   }
/*     */ 
/*     */   private Raster readImage(byte[] bytes) throws IOException
/*     */   {
/* 427 */     ImageInputStream input = ImageIO.createImageInputStream(new ByteArrayInputStream(bytes));
/* 428 */     Iterator readers = ImageIO.getImageReaders(input);
/* 429 */     if ((readers == null) || (!readers.hasNext()))
/*     */     {
/* 431 */       input.close();
/* 432 */       throw new RuntimeException("No ImageReaders found");
/*     */     }
/*     */ 
/* 436 */     ImageReader reader = (ImageReader)readers.next();
/* 437 */     reader.setInput(input);
/* 438 */     Raster raster = reader.readRaster(0, reader.getDefaultReadParam());
/* 439 */     input.close();
/* 440 */     reader.dispose();
/* 441 */     return raster;
/*     */   }
/*     */ 
/*     */   private BufferedImage convertCMYK2RGB(Raster raster, PDColorSpace colorspace)
/*     */     throws IOException
/*     */   {
/* 448 */     ColorSpace cs = colorspace.getJavaColorSpace();
/* 449 */     int width = raster.getWidth();
/* 450 */     int height = raster.getHeight();
/* 451 */     byte[] rgb = new byte[width * height * 3];
/* 452 */     int rgbIndex = 0;
/* 453 */     for (int i = 0; i < height; i++)
/*     */     {
/* 455 */       for (int j = 0; j < width; j++)
/*     */       {
/* 458 */         float[] srcColorValues = raster.getPixel(j, i, (float[])null);
/*     */ 
/* 460 */         for (int k = 0; k < 4; k++)
/*     */         {
/* 462 */           srcColorValues[k] /= 255.0F;
/*     */         }
/*     */ 
/* 465 */         float[] rgbValues = cs.toRGB(srcColorValues);
/*     */ 
/* 467 */         for (int k = 0; k < 3; k++)
/*     */         {
/* 469 */           rgb[(rgbIndex + k)] = ((byte)(int)(rgbValues[k] * 255.0F));
/*     */         }
/* 471 */         rgbIndex += 3;
/*     */       }
/*     */     }
/* 474 */     return createRGBBufferedImage(ColorSpace.getInstance(1000), rgb, width, height);
/*     */   }
/*     */ 
/*     */   private BufferedImage convertYCCK2RGB(Raster raster)
/*     */     throws IOException
/*     */   {
/* 480 */     int width = raster.getWidth();
/* 481 */     int height = raster.getHeight();
/* 482 */     byte[] rgb = new byte[width * height * 3];
/* 483 */     int rgbIndex = 0;
/* 484 */     for (int i = 0; i < height; i++)
/*     */     {
/* 486 */       for (int j = 0; j < width; j++)
/*     */       {
/* 488 */         float[] srcColorValues = raster.getPixel(j, i, (float[])null);
/* 489 */         float k = srcColorValues[3];
/* 490 */         float y = srcColorValues[0];
/* 491 */         float c1 = srcColorValues[1];
/* 492 */         float c2 = srcColorValues[2];
/*     */ 
/* 494 */         double val = y + 1.402D * (c2 - 128.0F) - k;
/* 495 */         rgb[rgbIndex] = (val > 255.0D ? -1 : val < 0.0D ? 0 : (byte)(int)(val + 0.5D));
/*     */ 
/* 497 */         val = y - 0.34414D * (c1 - 128.0F) - 0.71414D * (c2 - 128.0F) - k;
/* 498 */         rgb[(rgbIndex + 1)] = (val > 255.0D ? -1 : val < 0.0D ? 0 : (byte)(int)(val + 0.5D));
/*     */ 
/* 500 */         val = y + 1.772D * (c1 - 128.0F) - k;
/* 501 */         rgb[(rgbIndex + 2)] = (val > 255.0D ? -1 : val < 0.0D ? 0 : (byte)(int)(val + 0.5D));
/*     */ 
/* 503 */         rgbIndex += 3;
/*     */       }
/*     */     }
/* 506 */     return createRGBBufferedImage(ColorSpace.getInstance(1000), rgb, width, height);
/*     */   }
/*     */ 
/*     */   private BufferedImage processTintTransformation(Raster raster, PDFunction function, ColorSpace colorspace)
/*     */     throws IOException
/*     */   {
/* 513 */     int numberOfInputValues = function.getNumberOfInputParameters();
/* 514 */     int numberOfOutputValues = function.getNumberOfOutputParameters();
/* 515 */     int width = raster.getWidth();
/* 516 */     int height = raster.getHeight();
/* 517 */     byte[] rgb = new byte[width * height * numberOfOutputValues];
/* 518 */     int bufferIndex = 0;
/* 519 */     for (int i = 0; i < height; i++)
/*     */     {
/* 521 */       for (int j = 0; j < width; j++)
/*     */       {
/* 524 */         float[] srcColorValues = raster.getPixel(j, i, (float[])null);
/*     */ 
/* 526 */         for (int k = 0; k < numberOfInputValues; k++)
/*     */         {
/* 528 */           srcColorValues[k] /= 255.0F;
/*     */         }
/*     */ 
/* 531 */         float[] convertedValues = function.eval(srcColorValues);
/*     */ 
/* 533 */         for (int k = 0; k < numberOfOutputValues; k++)
/*     */         {
/* 535 */           rgb[(bufferIndex + k)] = ((byte)(int)(convertedValues[k] * 255.0F));
/*     */         }
/* 537 */         bufferIndex += numberOfOutputValues;
/*     */       }
/*     */     }
/* 540 */     return createRGBBufferedImage(colorspace, rgb, width, height);
/*     */   }
/*     */ 
/*     */   private BufferedImage createRGBBufferedImage(ColorSpace cs, byte[] rgb, int width, int height)
/*     */   {
/* 546 */     ColorModel cm = new ComponentColorModel(cs, false, false, 1, 0);
/*     */ 
/* 548 */     WritableRaster writeableRaster = cm.createCompatibleWritableRaster(width, height);
/*     */ 
/* 550 */     DataBufferByte buffer = (DataBufferByte)writeableRaster.getDataBuffer();
/* 551 */     byte[] bufferData = buffer.getData();
/*     */ 
/* 553 */     System.arraycopy(rgb, 0, bufferData, 0, rgb.length);
/*     */ 
/* 555 */     return new BufferedImage(cm, writeableRaster, true, null);
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 561 */     super.clear();
/* 562 */     this.image = null;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  81 */     DCT_FILTERS.add(COSName.DCT_DECODE.getName());
/*  82 */     DCT_FILTERS.add(COSName.DCT_DECODE_ABBREVIATION.getName());
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg
 * JD-Core Version:    0.6.2
 */