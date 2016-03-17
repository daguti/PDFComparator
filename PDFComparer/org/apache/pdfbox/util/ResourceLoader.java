/*     */ package org.apache.pdfbox.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Properties;
/*     */ 
/*     */ public class ResourceLoader
/*     */ {
/*     */   public static InputStream loadResource(String resourceName)
/*     */     throws IOException
/*     */   {
/*  54 */     ClassLoader loader = null;
/*     */     try
/*     */     {
/*  57 */       loader = ResourceLoader.class.getClassLoader();
/*     */     }
/*     */     catch (SecurityException ex)
/*     */     {
/*     */     }
/*     */ 
/*  64 */     InputStream is = null;
/*     */ 
/*  66 */     if (loader != null)
/*     */     {
/*  68 */       is = loader.getResourceAsStream(resourceName);
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  75 */       if (is == null)
/*     */       {
/*  77 */         loader = ClassLoader.getSystemClassLoader();
/*  78 */         if (loader != null)
/*     */         {
/*  80 */           is = loader.getResourceAsStream(resourceName);
/*     */         }
/*     */       }
/*     */ 
/*  84 */       if (is == null)
/*     */       {
/*  86 */         File f = new File(resourceName);
/*  87 */         if (f.exists())
/*     */         {
/*  89 */           is = new FileInputStream(f);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SecurityException ex)
/*     */     {
/*     */     }
/*     */ 
/*  98 */     return is;
/*     */   }
/*     */ 
/*     */   public static Properties loadProperties(String resourceName, boolean failIfNotFound)
/*     */     throws IOException
/*     */   {
/* 113 */     Properties properties = null;
/* 114 */     InputStream is = null;
/*     */     try
/*     */     {
/* 117 */       is = loadResource(resourceName);
/* 118 */       if (is != null)
/*     */       {
/* 120 */         properties = new Properties();
/* 121 */         properties.load(is);
/*     */       }
/* 125 */       else if (failIfNotFound)
/*     */       {
/* 127 */         throw new IOException("Error: could not find resource '" + resourceName + "' on classpath.");
/*     */       }
/*     */ 
/*     */     }
/*     */     finally
/*     */     {
/* 133 */       if (is != null)
/*     */       {
/* 135 */         is.close();
/*     */       }
/*     */     }
/* 138 */     return properties;
/*     */   }
/*     */ 
/*     */   public static Properties loadProperties(String resourceName, Properties defaults)
/*     */     throws IOException
/*     */   {
/* 153 */     InputStream is = null;
/*     */     try
/*     */     {
/* 156 */       is = loadResource(resourceName);
/* 157 */       if (is != null)
/*     */       {
/* 159 */         defaults.load(is);
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/* 164 */       if (is != null)
/*     */       {
/* 166 */         is.close();
/*     */       }
/*     */     }
/* 169 */     return defaults;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.ResourceLoader
 * JD-Core Version:    0.6.2
 */