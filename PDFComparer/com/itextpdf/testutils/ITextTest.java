/*     */ package com.itextpdf.testutils;
/*     */ 
/*     */ import com.itextpdf.text.log.Logger;
/*     */ import com.itextpdf.text.log.LoggerFactory;
/*     */ import java.io.File;
/*     */ import javax.management.OperationsException;
/*     */ 
/*     */ public abstract class ITextTest
/*     */ {
/*  55 */   private static final Logger LOGGER = LoggerFactory.getLogger(ITextTest.class.getName());
/*     */ 
/*     */   public void runTest() throws Exception {
/*  58 */     LOGGER.info("Starting test.");
/*  59 */     String outPdf = getOutPdf();
/*  60 */     if ((outPdf == null) || (outPdf.length() == 0))
/*  61 */       throw new OperationsException("outPdf cannot be empty!");
/*  62 */     makePdf(outPdf);
/*  63 */     assertPdf(outPdf);
/*  64 */     comparePdf(outPdf, getCmpPdf());
/*  65 */     LOGGER.info("Test complete.");
/*     */   }
/*     */ 
/*     */   protected abstract void makePdf(String paramString)
/*     */     throws Exception;
/*     */ 
/*     */   protected abstract String getOutPdf();
/*     */ 
/*     */   protected void assertPdf(String outPdf)
/*     */     throws Exception
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void comparePdf(String outPdf, String cmpPdf)
/*     */     throws Exception
/*     */   {
/*     */   }
/*     */ 
/*     */   protected String getCmpPdf()
/*     */   {
/*  91 */     return "";
/*     */   }
/*     */ 
/*     */   protected void deleteDirectory(File path) {
/*  95 */     if (path == null)
/*  96 */       return;
/*  97 */     if (path.exists()) {
/*  98 */       for (File f : path.listFiles()) {
/*  99 */         if (f.isDirectory()) {
/* 100 */           deleteDirectory(f);
/* 101 */           f.delete();
/*     */         } else {
/* 103 */           f.delete();
/*     */         }
/*     */       }
/* 106 */       path.delete();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void deleteFiles(File path) {
/* 111 */     if ((path != null) && (path.exists()))
/* 112 */       for (File f : path.listFiles())
/* 113 */         f.delete();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.testutils.ITextTest
 * JD-Core Version:    0.6.2
 */