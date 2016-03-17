/*     */ package com.itextpdf.text.pdf.codec;
/*     */ 
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import com.itextpdf.text.Image;
/*     */ import com.itextpdf.text.ImgJBIG2;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.pdf.RandomAccessFileOrArray;
/*     */ 
/*     */ public class JBIG2Image
/*     */ {
/*     */   public static byte[] getGlobalSegment(RandomAccessFileOrArray ra)
/*     */   {
/*     */     try
/*     */     {
/*  69 */       JBIG2SegmentReader sr = new JBIG2SegmentReader(ra);
/*  70 */       sr.read();
/*  71 */       return sr.getGlobal(true); } catch (Exception e) {
/*     */     }
/*  73 */     return null;
/*     */   }
/*     */ 
/*     */   public static Image getJbig2Image(RandomAccessFileOrArray ra, int page)
/*     */   {
/*  84 */     if (page < 1)
/*  85 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.page.number.must.be.gt.eq.1", new Object[0]));
/*     */     try
/*     */     {
/*  88 */       JBIG2SegmentReader sr = new JBIG2SegmentReader(ra);
/*  89 */       sr.read();
/*  90 */       JBIG2SegmentReader.JBIG2Page p = sr.getPage(page);
/*  91 */       return new ImgJBIG2(p.pageBitmapWidth, p.pageBitmapHeight, p.getData(true), sr.getGlobal(true));
/*     */     }
/*     */     catch (Exception e) {
/*  94 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static int getNumberOfPages(RandomAccessFileOrArray ra)
/*     */   {
/*     */     try
/*     */     {
/* 105 */       JBIG2SegmentReader sr = new JBIG2SegmentReader(ra);
/* 106 */       sr.read();
/* 107 */       return sr.numberOfPages();
/*     */     } catch (Exception e) {
/* 109 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.codec.JBIG2Image
 * JD-Core Version:    0.6.2
 */