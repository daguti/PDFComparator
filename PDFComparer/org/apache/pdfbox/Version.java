/*    */ package org.apache.pdfbox;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ import java.util.Properties;
/*    */ import org.apache.pdfbox.util.ResourceLoader;
/*    */ 
/*    */ public class Version
/*    */ {
/*    */   private static final String PDFBOX_VERSION_PROPERTIES = "org/apache/pdfbox/resources/pdfbox.properties";
/*    */ 
/*    */   public static String getVersion()
/*    */   {
/* 49 */     String version = "unknown";
/*    */     try
/*    */     {
/* 52 */       Properties props = ResourceLoader.loadProperties("org/apache/pdfbox/resources/pdfbox.properties", false);
/* 53 */       version = props.getProperty("pdfbox.version", version);
/*    */     }
/*    */     catch (IOException io)
/*    */     {
/* 59 */       io.printStackTrace();
/*    */     }
/* 61 */     return version;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 71 */     if (args.length != 0)
/*    */     {
/* 73 */       usage();
/* 74 */       return;
/*    */     }
/* 76 */     System.out.println("Version:" + getVersion());
/*    */   }
/*    */ 
/*    */   private static void usage()
/*    */   {
/* 84 */     System.err.println("usage: " + Version.class.getName());
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.Version
 * JD-Core Version:    0.6.2
 */