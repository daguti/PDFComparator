/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ 
/*     */ public final class Version
/*     */ {
/*  64 */   private String iText = "iText®";
/*     */ 
/*  70 */   private String release = "5.5.2";
/*     */ 
/*  75 */   private String key = null;
/*     */ 
/*  83 */   private String iTextVersion = this.iText + " " + this.release + " ©2000-2014 iText Group NV";
/*     */ 
/*  86 */   private static Version version = null;
/*     */ 
/*     */   public static Version getInstance()
/*     */   {
/*  94 */     if (version == null) {
/*  95 */       version = new Version();
/*     */       try {
/*  97 */         Class klass = Class.forName("com.itextpdf.license.LicenseKey");
/*  98 */         Method m = klass.getMethod("getLicenseeInfo", new Class[0]);
/*  99 */         String[] info = (String[])m.invoke(klass.newInstance(), new Object[0]);
/* 100 */         if ((info[3] != null) && (info[3].trim().length() > 0)) {
/* 101 */           version.key = info[3];
/*     */         }
/*     */         else {
/* 104 */           version.key = "Trial version ";
/* 105 */           if (info[5] == null) {
/* 106 */             version.key += "unauthorised";
/*     */           }
/*     */           else {
/* 109 */             version.key += info[5];
/*     */           }
/*     */         }
/* 112 */         if ((info[4] != null) && (info[4].trim().length() > 0)) {
/* 113 */           version.iTextVersion = info[4];
/*     */         }
/* 115 */         else if ((info[2] != null) && (info[2].trim().length() > 0))
/*     */         {
/*     */           Version tmp214_211 = version; tmp214_211.iTextVersion = (tmp214_211.iTextVersion + " (" + info[2]);
/* 117 */           if (!version.key.toLowerCase().startsWith("trial")) {
/* 118 */             version.iTextVersion += "; licensed version)";
/*     */           }
/*     */           else
/*     */           {
/*     */             Version tmp296_293 = version; tmp296_293.iTextVersion = (tmp296_293.iTextVersion + "; " + version.key + ")");
/*     */           }
/*     */ 
/*     */         }
/* 125 */         else if ((info[0] != null) && (info[0].trim().length() > 0))
/*     */         {
/*     */           Version tmp359_356 = version; tmp359_356.iTextVersion = (tmp359_356.iTextVersion + " (" + info[0]);
/* 127 */           if (!version.key.toLowerCase().startsWith("trial"))
/*     */           {
/* 130 */             version.iTextVersion += "; licensed version)";
/*     */           }
/*     */           else
/*     */           {
/*     */             Version tmp441_438 = version; tmp441_438.iTextVersion = (tmp441_438.iTextVersion + "; " + version.key + ")");
/*     */           }
/*     */         }
/*     */         else {
/* 137 */           throw new Exception();
/*     */         }
/*     */       } catch (Exception e) {
/* 140 */         version.iTextVersion += " (AGPL-version)";
/*     */       }
/*     */     }
/* 143 */     return version;
/*     */   }
/*     */ 
/*     */   public String getProduct()
/*     */   {
/* 153 */     return this.iText;
/*     */   }
/*     */ 
/*     */   public String getRelease()
/*     */   {
/* 163 */     return this.release;
/*     */   }
/*     */ 
/*     */   public String getVersion()
/*     */   {
/* 174 */     return this.iTextVersion;
/*     */   }
/*     */ 
/*     */   public String getKey()
/*     */   {
/* 182 */     return this.key;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.Version
 * JD-Core Version:    0.6.2
 */