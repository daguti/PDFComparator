/*     */ package org.apache.fontbox.util;
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
/*  56 */     ClassLoader loader = ResourceLoader.class.getClassLoader();
/*     */ 
/*  58 */     InputStream is = null;
/*     */ 
/*  60 */     if (loader != null)
/*     */     {
/*  62 */       is = loader.getResourceAsStream(resourceName);
/*     */     }
/*     */ 
/*  67 */     if (is == null)
/*     */     {
/*  69 */       loader = ClassLoader.getSystemClassLoader();
/*  70 */       if (loader != null)
/*     */       {
/*  72 */         is = loader.getResourceAsStream(resourceName);
/*     */       }
/*     */     }
/*     */ 
/*  76 */     if (is == null)
/*     */     {
/*  78 */       File f = new File(resourceName);
/*  79 */       if (f.exists())
/*     */       {
/*  81 */         is = new FileInputStream(f);
/*     */       }
/*     */     }
/*     */ 
/*  85 */     return is;
/*     */   }
/*     */ 
/*     */   public static Properties loadProperties(String resourceName)
/*     */     throws IOException
/*     */   {
/*  99 */     Properties properties = null;
/* 100 */     InputStream is = null;
/*     */     try
/*     */     {
/* 103 */       is = loadResource(resourceName);
/* 104 */       if (is != null)
/*     */       {
/* 106 */         properties = new Properties();
/* 107 */         properties.load(is);
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/* 112 */       if (is != null)
/*     */       {
/* 114 */         is.close();
/*     */       }
/*     */     }
/* 117 */     return properties;
/*     */   }
/*     */ 
/*     */   public static Properties loadProperties(String resourceName, Properties defaults)
/*     */     throws IOException
/*     */   {
/* 132 */     InputStream is = null;
/*     */     try
/*     */     {
/* 135 */       is = loadResource(resourceName);
/* 136 */       if (is != null)
/*     */       {
/* 138 */         defaults.load(is);
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/* 143 */       if (is != null)
/*     */       {
/* 145 */         is.close();
/*     */       }
/*     */     }
/* 148 */     return defaults;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.util.ResourceLoader
 * JD-Core Version:    0.6.2
 */