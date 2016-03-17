/*    */ package com.itextpdf.text.xml.xmp;
/*    */ 
/*    */ import com.itextpdf.xmp.XMPException;
/*    */ import com.itextpdf.xmp.XMPMeta;
/*    */ 
/*    */ public class PdfProperties
/*    */ {
/*    */   public static final String KEYWORDS = "Keywords";
/*    */   public static final String VERSION = "PDFVersion";
/*    */   public static final String PRODUCER = "Producer";
/*    */   public static final String PART = "part";
/*    */ 
/*    */   public static void setKeywords(XMPMeta xmpMeta, String keywords)
/*    */     throws XMPException
/*    */   {
/* 67 */     xmpMeta.setProperty("http://ns.adobe.com/pdf/1.3/", "Keywords", keywords);
/*    */   }
/*    */ 
/*    */   public static void setProducer(XMPMeta xmpMeta, String producer)
/*    */     throws XMPException
/*    */   {
/* 77 */     xmpMeta.setProperty("http://ns.adobe.com/pdf/1.3/", "Producer", producer);
/*    */   }
/*    */ 
/*    */   public static void setVersion(XMPMeta xmpMeta, String version)
/*    */     throws XMPException
/*    */   {
/* 87 */     xmpMeta.setProperty("http://ns.adobe.com/pdf/1.3/", "PDFVersion", version);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.xml.xmp.PdfProperties
 * JD-Core Version:    0.6.2
 */