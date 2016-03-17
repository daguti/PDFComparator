/*     */ package org.apache.pdfbox.util;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Iterator;
/*     */ import javax.imageio.IIOImage;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.imageio.ImageTypeSpecifier;
/*     */ import javax.imageio.ImageWriteParam;
/*     */ import javax.imageio.ImageWriter;
/*     */ import javax.imageio.metadata.IIOInvalidTreeException;
/*     */ import javax.imageio.metadata.IIOMetadata;
/*     */ import javax.imageio.metadata.IIOMetadataNode;
/*     */ import javax.imageio.stream.ImageOutputStream;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class ImageIOUtil
/*     */ {
/*  48 */   private static final Log LOG = LogFactory.getLog(ImageIOUtil.class);
/*     */   public static final int DEFAULT_SCREEN_RESOLUTION = 72;
/*     */   public static final float DEFAULT_COMPRESSION_QUALITY = 1.0F;
/*     */ 
/*     */   public static boolean writeImage(BufferedImage image, String filename, int dpi)
/*     */     throws IOException
/*     */   {
/*  78 */     File file = new File(filename);
/*  79 */     FileOutputStream output = new FileOutputStream(file);
/*     */     try
/*     */     {
/*  82 */       String formatName = filename.substring(filename.lastIndexOf('.') + 1);
/*  83 */       return writeImage(image, formatName, output, dpi);
/*     */     }
/*     */     finally
/*     */     {
/*  87 */       output.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public static boolean writeImage(BufferedImage image, String formatName, String filename, int dpi)
/*     */     throws IOException
/*     */   {
/* 112 */     File file = new File(filename + "." + formatName);
/* 113 */     FileOutputStream output = new FileOutputStream(file);
/*     */     try
/*     */     {
/* 116 */       return writeImage(image, formatName, output, dpi);
/*     */     }
/*     */     finally
/*     */     {
/* 120 */       output.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static boolean writeImage(BufferedImage image, String formatName, OutputStream output)
/*     */     throws IOException
/*     */   {
/* 138 */     return writeImage(image, formatName, output, 72);
/*     */   }
/*     */ 
/*     */   public static boolean writeImage(BufferedImage image, String formatName, OutputStream output, int dpi)
/*     */     throws IOException
/*     */   {
/* 156 */     return writeImage(image, formatName, output, dpi, 1.0F);
/*     */   }
/*     */ 
/*     */   public static boolean writeImage(BufferedImage image, String formatName, OutputStream output, int dpi, float quality)
/*     */     throws IOException
/*     */   {
/* 179 */     ImageOutputStream imageOutput = null;
/* 180 */     ImageWriter writer = null;
/*     */     try
/*     */     {
/* 184 */       Iterator writers = ImageIO.getImageWritersByFormatName(formatName);
/* 185 */       ImageWriteParam param = null;
/* 186 */       IIOMetadata metadata = null;
/*     */ 
/* 190 */       while (writers.hasNext())
/*     */       {
/* 192 */         if (writer != null)
/*     */         {
/* 194 */           writer.dispose();
/*     */         }
/* 196 */         writer = (ImageWriter)writers.next();
/* 197 */         param = writer.getDefaultWriteParam();
/* 198 */         metadata = writer.getDefaultImageMetadata(new ImageTypeSpecifier(image), param);
/* 199 */         if ((metadata != null) && (!metadata.isReadOnly()) && (metadata.isStandardMetadataFormatSupported()))
/*     */         {
/* 203 */           break;
/*     */         }
/*     */       }
/* 206 */       if (writer == null)
/*     */       {
/* 208 */         LOG.error("No ImageWriter found for '" + formatName + "' format");
/* 209 */         StringBuilder sb = new StringBuilder();
/* 210 */         String[] writerFormatNames = ImageIO.getWriterFormatNames();
/* 211 */         for (String fmt : writerFormatNames)
/*     */         {
/* 213 */           sb.append(fmt);
/* 214 */           sb.append(' ');
/*     */         }
/* 216 */         LOG.error("Supported formats: " + sb);
/* 217 */         return 0;
/*     */       }
/*     */ 
/* 221 */       if ((param != null) && (param.canWriteCompressed()))
/*     */       {
/* 223 */         param.setCompressionMode(2);
/* 224 */         if (formatName.toLowerCase().startsWith("tif"))
/*     */         {
/* 227 */           TIFFUtil.setCompressionType(param, image);
/*     */         }
/*     */         else
/*     */         {
/* 231 */           param.setCompressionType(param.getCompressionTypes()[0]);
/* 232 */           param.setCompressionQuality(quality);
/*     */         }
/*     */       }
/*     */ 
/* 236 */       if (formatName.toLowerCase().startsWith("tif"))
/*     */       {
/* 239 */         TIFFUtil.updateMetadata(metadata, image, dpi);
/*     */       }
/* 241 */       else if (("jpeg".equals(formatName.toLowerCase())) || ("jpg".equals(formatName.toLowerCase())))
/*     */       {
/* 248 */         JPEGUtil.updateMetadata(metadata, dpi);
/*     */       }
/* 253 */       else if ((metadata != null) && (!metadata.isReadOnly()) && (metadata.isStandardMetadataFormatSupported()))
/*     */       {
/* 257 */         setDPI(metadata, dpi, formatName);
/*     */       }
/*     */ 
/* 262 */       imageOutput = ImageIO.createImageOutputStream(output);
/* 263 */       writer.setOutput(imageOutput);
/* 264 */       writer.write(null, new IIOImage(image, null, metadata), param);
/*     */     }
/*     */     finally
/*     */     {
/* 268 */       if (writer != null)
/*     */       {
/* 270 */         writer.dispose();
/*     */       }
/* 272 */       if (imageOutput != null)
/*     */       {
/* 274 */         imageOutput.close();
/*     */       }
/*     */     }
/* 277 */     return true;
/*     */   }
/*     */ 
/*     */   private static IIOMetadataNode getOrCreateChildNode(IIOMetadataNode parentNode, String name)
/*     */   {
/* 290 */     NodeList nodeList = parentNode.getElementsByTagName(name);
/* 291 */     if ((nodeList != null) && (nodeList.getLength() > 0))
/*     */     {
/* 293 */       return (IIOMetadataNode)nodeList.item(0);
/*     */     }
/* 295 */     IIOMetadataNode childNode = new IIOMetadataNode(name);
/* 296 */     parentNode.appendChild(childNode);
/* 297 */     return childNode;
/*     */   }
/*     */ 
/*     */   private static void setDPI(IIOMetadata metadata, int dpi, String formatName)
/*     */   {
/* 303 */     IIOMetadataNode root = (IIOMetadataNode)metadata.getAsTree("javax_imageio_1.0");
/*     */ 
/* 305 */     IIOMetadataNode dimension = getOrCreateChildNode(root, "Dimension");
/*     */ 
/* 310 */     float res = "PNG".equals(formatName.toUpperCase()) ? dpi / 25.4F : 25.4F / dpi;
/*     */ 
/* 316 */     IIOMetadataNode child = getOrCreateChildNode(dimension, "HorizontalPixelSize");
/* 317 */     child.setAttribute("value", Double.toString(res));
/*     */ 
/* 319 */     child = getOrCreateChildNode(dimension, "VerticalPixelSize");
/* 320 */     child.setAttribute("value", Double.toString(res));
/*     */     try
/*     */     {
/* 324 */       metadata.mergeTree("javax_imageio_1.0", root);
/*     */     }
/*     */     catch (IIOInvalidTreeException e)
/*     */     {
/* 329 */       throw new RuntimeException(e);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.ImageIOUtil
 * JD-Core Version:    0.6.2
 */