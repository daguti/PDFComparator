/*     */ package org.apache.pdfbox.filter;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.DataBuffer;
/*     */ import java.awt.image.DataBufferByte;
/*     */ import java.awt.image.Raster;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.io.SequenceInputStream;
/*     */ import java.util.Iterator;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.imageio.ImageReader;
/*     */ import javax.imageio.stream.ImageInputStream;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSInteger;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ 
/*     */ public class JBIG2Filter
/*     */   implements Filter
/*     */ {
/*  50 */   private static final Log LOG = LogFactory.getLog(JBIG2Filter.class);
/*     */ 
/*     */   public void decode(InputStream compressedData, OutputStream result, COSDictionary options, int filterIndex)
/*     */     throws IOException
/*     */   {
/*  66 */     Iterator readers = ImageIO.getImageReadersByFormatName("JBIG2");
/*  67 */     if (!readers.hasNext())
/*     */     {
/*  69 */       LOG.error("Can't find an ImageIO plugin to decode the JBIG2 encoded datastream.");
/*  70 */       return;
/*     */     }
/*  72 */     ImageReader reader = (ImageReader)readers.next();
/*  73 */     COSDictionary decodeP = (COSDictionary)options.getDictionaryObject(COSName.DECODE_PARMS);
/*  74 */     COSInteger bits = (COSInteger)options.getDictionaryObject(COSName.BITS_PER_COMPONENT);
/*  75 */     COSStream st = null;
/*  76 */     if (decodeP != null)
/*     */     {
/*  78 */       st = (COSStream)decodeP.getDictionaryObject(COSName.JBIG2_GLOBALS);
/*     */     }
/*  80 */     if (st != null)
/*     */     {
/*  82 */       compressedData = new SequenceInputStream(st.getUnfilteredStream(), compressedData);
/*     */     }
/*     */ 
/*  85 */     ImageInputStream iis = ImageIO.createImageInputStream(compressedData);
/*  86 */     reader.setInput(iis);
/*  87 */     BufferedImage bi = reader.read(0);
/*  88 */     iis.close();
/*  89 */     reader.dispose();
/*  90 */     if (bi != null)
/*     */     {
/*  94 */       if (bi.getColorModel().getPixelSize() != bits.intValue())
/*     */       {
/*  96 */         if (bits.intValue() != 1)
/*     */         {
/*  98 */           LOG.error("Do not know how to deal with JBIG2 with more than 1 bit");
/*  99 */           return;
/*     */         }
/* 101 */         BufferedImage packedImage = new BufferedImage(bi.getWidth(), bi.getHeight(), 12);
/*     */ 
/* 103 */         Graphics graphics = packedImage.getGraphics();
/* 104 */         graphics.drawImage(bi, 0, 0, null);
/* 105 */         graphics.dispose();
/* 106 */         bi = packedImage;
/*     */       }
/* 108 */       DataBuffer dBuf = bi.getData().getDataBuffer();
/* 109 */       if (dBuf.getDataType() == 0)
/*     */       {
/* 111 */         result.write(((DataBufferByte)dBuf).getData());
/*     */       }
/*     */       else
/*     */       {
/* 115 */         LOG.error("Image data buffer not of type byte but type " + dBuf.getDataType());
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 120 */       LOG.error("Something went wrong when decoding the JBIG2 encoded datastream.");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void encode(InputStream rawData, OutputStream result, COSDictionary options, int filterIndex)
/*     */     throws IOException
/*     */   {
/* 130 */     System.err.println("Warning: JBIG2.encode is not implemented yet, skipping this stream.");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.filter.JBIG2Filter
 * JD-Core Version:    0.6.2
 */