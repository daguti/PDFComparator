/*     */ package org.apache.pdfbox.util;
/*     */ 
/*     */ import java.awt.HeadlessException;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
/*     */ import org.apache.pdfbox.pdmodel.PDPage;
/*     */ 
/*     */ public class PDFImageWriter extends PDFStreamEngine
/*     */ {
/*  43 */   private static final Log LOG = LogFactory.getLog(PDFImageWriter.class);
/*     */ 
/*     */   public PDFImageWriter()
/*     */   {
/*     */   }
/*     */ 
/*     */   public PDFImageWriter(Properties props)
/*     */     throws IOException
/*     */   {
/*  64 */     super(props);
/*     */   }
/*     */ 
/*     */   public boolean writeImage(PDDocument document, String imageFormat, String password, int startPage, int endPage, String outputPrefix)
/*     */     throws IOException
/*     */   {
/*     */     int resolution;
/*     */     try
/*     */     {
/*  89 */       resolution = Toolkit.getDefaultToolkit().getScreenResolution();
/*     */     }
/*     */     catch (HeadlessException e)
/*     */     {
/*  93 */       resolution = 96;
/*     */     }
/*  95 */     return writeImage(document, imageFormat, password, startPage, endPage, outputPrefix, 1, resolution);
/*     */   }
/*     */ 
/*     */   public boolean writeImage(PDDocument document, String imageFormat, String password, int startPage, int endPage, String outputPrefix, int imageType, int resolution)
/*     */     throws IOException
/*     */   {
/* 124 */     boolean bSuccess = true;
/* 125 */     List pages = document.getDocumentCatalog().getAllPages();
/* 126 */     int pagesSize = pages.size();
/* 127 */     for (int i = startPage - 1; (i < endPage) && (i < pagesSize); i++)
/*     */     {
/* 129 */       PDPage page = (PDPage)pages.get(i);
/* 130 */       BufferedImage image = page.convertToImage(imageType, resolution);
/* 131 */       String fileName = outputPrefix + (i + 1) + "." + imageFormat;
/* 132 */       LOG.info("Writing: " + fileName);
/* 133 */       bSuccess &= ImageIOUtil.writeImage(image, fileName, resolution);
/*     */     }
/* 135 */     return bSuccess;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.PDFImageWriter
 * JD-Core Version:    0.6.2
 */