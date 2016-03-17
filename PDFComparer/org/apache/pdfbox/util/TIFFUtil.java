/*     */ package org.apache.pdfbox.util;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import javax.imageio.ImageWriteParam;
/*     */ import javax.imageio.metadata.IIOInvalidTreeException;
/*     */ import javax.imageio.metadata.IIOMetadata;
/*     */ import javax.imageio.metadata.IIOMetadataNode;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ class TIFFUtil
/*     */ {
/*  36 */   private static final Log LOG = LogFactory.getLog(TIFFUtil.class);
/*     */ 
/*     */   public static void setCompressionType(ImageWriteParam param, BufferedImage image)
/*     */   {
/*  47 */     if ((image.getType() == 12) && (image.getColorModel().getPixelSize() == 1))
/*     */     {
/*  50 */       param.setCompressionType("CCITT T.6");
/*     */     }
/*     */     else
/*     */     {
/*  54 */       param.setCompressionType("LZW");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void updateMetadata(IIOMetadata metadata, BufferedImage image, int dpi)
/*     */   {
/*  71 */     MetaUtil.debugLogMetadata(metadata, "com_sun_media_imageio_plugins_tiff_image_1.0");
/*     */ 
/*  73 */     if (!"com_sun_media_imageio_plugins_tiff_image_1.0".equals(metadata.getNativeMetadataFormatName()))
/*     */     {
/*  75 */       LOG.debug("Using unknown TIFF image writer: " + metadata.getNativeMetadataFormatName());
/*  76 */       return;
/*     */     }
/*     */ 
/*  79 */     IIOMetadataNode root = new IIOMetadataNode("com_sun_media_imageio_plugins_tiff_image_1.0");
/*     */     IIOMetadataNode ifd;
/*  81 */     if (root.getElementsByTagName("TIFFIFD").getLength() == 0)
/*     */     {
/*  83 */       IIOMetadataNode ifd = new IIOMetadataNode("TIFFIFD");
/*  84 */       ifd.setAttribute("tagSets", "com.sun.media.imageio.plugins.tiff.BaselineTIFFTagSet");
/*     */ 
/*  86 */       root.appendChild(ifd);
/*     */     }
/*     */     else
/*     */     {
/*  90 */       ifd = (IIOMetadataNode)root.getElementsByTagName("TIFFIFD").item(0);
/*     */     }
/*     */ 
/*  94 */     ifd.appendChild(createRationalField(282, "XResolution", dpi, 1));
/*  95 */     ifd.appendChild(createRationalField(283, "YResolution", dpi, 1));
/*  96 */     ifd.appendChild(createShortField(296, "ResolutionUnit", 2));
/*     */ 
/*  98 */     ifd.appendChild(createLongField(278, "RowsPerStrip", image.getHeight()));
/*  99 */     ifd.appendChild(createAsciiField(305, "Software", "PDFBOX"));
/*     */ 
/* 101 */     if ((image.getType() == 12) && (image.getColorModel().getPixelSize() == 1))
/*     */     {
/* 106 */       ifd.appendChild(createShortField(262, "PhotometricInterpretation", 0));
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 111 */       metadata.mergeTree("com_sun_media_imageio_plugins_tiff_image_1.0", root);
/*     */     }
/*     */     catch (IIOInvalidTreeException e)
/*     */     {
/* 116 */       throw new RuntimeException(e);
/*     */     }
/*     */ 
/* 119 */     MetaUtil.debugLogMetadata(metadata, "com_sun_media_imageio_plugins_tiff_image_1.0");
/*     */   }
/*     */ 
/*     */   private static IIOMetadataNode createShortField(int tiffTagNumber, String name, int val)
/*     */   {
/* 125 */     IIOMetadataNode field = new IIOMetadataNode("TIFFField");
/* 126 */     field.setAttribute("number", Integer.toString(tiffTagNumber));
/* 127 */     field.setAttribute("name", name);
/* 128 */     IIOMetadataNode arrayNode = new IIOMetadataNode("TIFFShorts");
/* 129 */     field.appendChild(arrayNode);
/* 130 */     IIOMetadataNode valueNode = new IIOMetadataNode("TIFFShort");
/* 131 */     arrayNode.appendChild(valueNode);
/* 132 */     valueNode.setAttribute("value", Integer.toString(val));
/* 133 */     return field;
/*     */   }
/*     */ 
/*     */   private static IIOMetadataNode createAsciiField(int number, String name, String val)
/*     */   {
/* 139 */     IIOMetadataNode field = new IIOMetadataNode("TIFFField");
/* 140 */     field.setAttribute("number", Integer.toString(number));
/* 141 */     field.setAttribute("name", name);
/* 142 */     IIOMetadataNode arrayNode = new IIOMetadataNode("TIFFAsciis");
/* 143 */     field.appendChild(arrayNode);
/* 144 */     IIOMetadataNode valueNode = new IIOMetadataNode("TIFFAscii");
/* 145 */     arrayNode.appendChild(valueNode);
/* 146 */     valueNode.setAttribute("value", val);
/* 147 */     return field;
/*     */   }
/*     */ 
/*     */   private static IIOMetadataNode createLongField(int number, String name, long val)
/*     */   {
/* 153 */     IIOMetadataNode field = new IIOMetadataNode("TIFFField");
/* 154 */     field.setAttribute("number", Integer.toString(number));
/* 155 */     field.setAttribute("name", name);
/* 156 */     IIOMetadataNode arrayNode = new IIOMetadataNode("TIFFLongs");
/* 157 */     field.appendChild(arrayNode);
/* 158 */     IIOMetadataNode valueNode = new IIOMetadataNode("TIFFLong");
/* 159 */     arrayNode.appendChild(valueNode);
/* 160 */     valueNode.setAttribute("value", Long.toString(val));
/* 161 */     return field;
/*     */   }
/*     */ 
/*     */   private static IIOMetadataNode createRationalField(int number, String name, int numerator, int denominator)
/*     */   {
/* 168 */     IIOMetadataNode field = new IIOMetadataNode("TIFFField");
/* 169 */     field.setAttribute("number", Integer.toString(number));
/* 170 */     field.setAttribute("name", name);
/* 171 */     IIOMetadataNode arrayNode = new IIOMetadataNode("TIFFRationals");
/* 172 */     field.appendChild(arrayNode);
/* 173 */     IIOMetadataNode valueNode = new IIOMetadataNode("TIFFRational");
/* 174 */     arrayNode.appendChild(valueNode);
/* 175 */     valueNode.setAttribute("value", numerator + "/" + denominator);
/* 176 */     return field;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.TIFFUtil
 * JD-Core Version:    0.6.2
 */