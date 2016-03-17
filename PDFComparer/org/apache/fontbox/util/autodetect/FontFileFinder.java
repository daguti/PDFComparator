/*     */ package org.apache.fontbox.util.autodetect;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class FontFileFinder
/*     */ {
/*  33 */   private FontDirFinder fontDirFinder = null;
/*     */ 
/*     */   private FontDirFinder determineDirFinder()
/*     */   {
/*  44 */     String osName = System.getProperty("os.name");
/*  45 */     if (osName.startsWith("Windows"))
/*     */     {
/*  47 */       return new WindowsFontDirFinder();
/*     */     }
/*     */ 
/*  51 */     if (osName.startsWith("Mac"))
/*     */     {
/*  53 */       return new MacFontDirFinder();
/*     */     }
/*     */ 
/*  57 */     return new UnixFontDirFinder();
/*     */   }
/*     */ 
/*     */   public List<URI> find()
/*     */     throws IOException
/*     */   {
/*  70 */     if (this.fontDirFinder == null)
/*     */     {
/*  72 */       this.fontDirFinder = determineDirFinder();
/*     */     }
/*  74 */     List fontDirs = this.fontDirFinder.find();
/*  75 */     List results = new ArrayList();
/*  76 */     for (File dir : fontDirs)
/*     */     {
/*  78 */       walk(dir, results);
/*     */     }
/*  80 */     return results;
/*     */   }
/*     */ 
/*     */   public List<URI> find(String dir)
/*     */     throws IOException
/*     */   {
/*  92 */     List results = new ArrayList();
/*  93 */     File directory = new File(dir);
/*  94 */     if (directory.isDirectory())
/*     */     {
/*  96 */       walk(directory, results);
/*     */     }
/*  98 */     return results;
/*     */   }
/*     */ 
/*     */   public Map<String, String> getCommonTTFMapping()
/*     */   {
/* 108 */     if (this.fontDirFinder == null)
/*     */     {
/* 110 */       this.fontDirFinder = determineDirFinder();
/*     */     }
/* 112 */     return this.fontDirFinder.getCommonTTFMapping();
/*     */   }
/*     */ 
/*     */   private void walk(File directory, List<URI> results)
/*     */   {
/* 124 */     if (directory.isDirectory())
/*     */     {
/* 126 */       File[] filelist = directory.listFiles();
/* 127 */       if (filelist != null)
/*     */       {
/* 129 */         int numOfFiles = filelist.length;
/* 130 */         for (int i = 0; i < numOfFiles; i++)
/*     */         {
/* 132 */           File file = filelist[i];
/* 133 */           if (file.isDirectory())
/*     */           {
/* 136 */             if (!file.getName().startsWith("."))
/*     */             {
/* 140 */               walk(file, results);
/*     */             }
/*     */ 
/*     */           }
/* 144 */           else if (checkFontfile(file))
/*     */           {
/* 146 */             results.add(file.toURI());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean checkFontfile(File file)
/*     */   {
/* 162 */     String name = file.getName().toLowerCase();
/* 163 */     if ((name.endsWith(".ttf")) || (name.endsWith(".otf")) || (name.endsWith(".pfb")) || (name.endsWith(".ttc")))
/*     */     {
/* 165 */       return true;
/*     */     }
/* 167 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.util.autodetect.FontFileFinder
 * JD-Core Version:    0.6.2
 */