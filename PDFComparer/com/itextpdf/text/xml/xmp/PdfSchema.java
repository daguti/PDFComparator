/*    */ package com.itextpdf.text.xml.xmp;
/*    */ 
/*    */ import com.itextpdf.text.Version;
/*    */ 
/*    */ @Deprecated
/*    */ public class PdfSchema extends XmpSchema
/*    */ {
/*    */   private static final long serialVersionUID = -1541148669123992185L;
/*    */   public static final String DEFAULT_XPATH_ID = "pdf";
/*    */   public static final String DEFAULT_XPATH_URI = "http://ns.adobe.com/pdf/1.3/";
/*    */   public static final String KEYWORDS = "pdf:Keywords";
/*    */   public static final String VERSION = "pdf:PDFVersion";
/*    */   public static final String PRODUCER = "pdf:Producer";
/*    */ 
/*    */   public PdfSchema()
/*    */   {
/* 70 */     super("xmlns:pdf=\"http://ns.adobe.com/pdf/1.3/\"");
/* 71 */     addProducer(Version.getInstance().getVersion());
/*    */   }
/*    */ 
/*    */   public void addKeywords(String keywords)
/*    */   {
/* 79 */     setProperty("pdf:Keywords", keywords);
/*    */   }
/*    */ 
/*    */   public void addProducer(String producer)
/*    */   {
/* 87 */     setProperty("pdf:Producer", producer);
/*    */   }
/*    */ 
/*    */   public void addVersion(String version)
/*    */   {
/* 95 */     setProperty("pdf:PDFVersion", version);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.xml.xmp.PdfSchema
 * JD-Core Version:    0.6.2
 */