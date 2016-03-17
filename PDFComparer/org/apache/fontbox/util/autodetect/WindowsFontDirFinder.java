/*     */ package org.apache.fontbox.util.autodetect;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class WindowsFontDirFinder
/*     */   implements FontDirFinder
/*     */ {
/*     */   private String getWinDir(String osName)
/*     */     throws IOException
/*     */   {
/*  42 */     Process process = null;
/*  43 */     Runtime runtime = Runtime.getRuntime();
/*  44 */     if (osName.startsWith("Windows 9"))
/*     */     {
/*  46 */       process = runtime.exec("command.com /c echo %windir%");
/*     */     }
/*     */     else
/*     */     {
/*  50 */       process = runtime.exec("cmd.exe /c echo %windir%");
/*     */     }
/*  52 */     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
/*     */ 
/*  54 */     return bufferedReader.readLine();
/*     */   }
/*     */ 
/*     */   public List<File> find()
/*     */   {
/*  64 */     List fontDirList = new ArrayList();
/*  65 */     String windir = null;
/*     */     try
/*     */     {
/*  68 */       windir = System.getProperty("env.windir");
/*     */     }
/*     */     catch (SecurityException e)
/*     */     {
/*     */     }
/*     */ 
/*  74 */     String osName = System.getProperty("os.name");
/*  75 */     if (windir == null)
/*     */     {
/*     */       try
/*     */       {
/*  79 */         windir = getWinDir(osName);
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/*     */       }
/*     */     }
/*     */ 
/*  86 */     File osFontsDir = null;
/*  87 */     File psFontsDir = null;
/*  88 */     if (windir != null)
/*     */     {
/*  91 */       if (windir.endsWith("/"))
/*     */       {
/*  93 */         windir = windir.substring(0, windir.length() - 1);
/*     */       }
/*  95 */       osFontsDir = new File(windir + File.separator + "FONTS");
/*  96 */       if ((osFontsDir.exists()) && (osFontsDir.canRead()))
/*     */       {
/*  98 */         fontDirList.add(osFontsDir);
/*     */       }
/* 100 */       psFontsDir = new File(windir.substring(0, 2) + File.separator + "PSFONTS");
/* 101 */       if ((psFontsDir.exists()) && (psFontsDir.canRead()))
/*     */       {
/* 103 */         fontDirList.add(psFontsDir);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 108 */       String windowsDirName = osName.endsWith("NT") ? "WINNT" : "WINDOWS";
/*     */ 
/* 110 */       for (char driveLetter = 'C'; driveLetter <= 'E'; driveLetter = (char)(driveLetter + '\001'))
/*     */       {
/* 112 */         osFontsDir = new File(driveLetter + ":" + File.separator + windowsDirName + File.separator + "FONTS");
/*     */ 
/* 114 */         if ((osFontsDir.exists()) && (osFontsDir.canRead()))
/*     */         {
/* 116 */           fontDirList.add(osFontsDir);
/* 117 */           break;
/*     */         }
/*     */       }
/*     */ 
/* 121 */       for (char driveLetter = 'C'; driveLetter <= 'E'; driveLetter = (char)(driveLetter + '\001'))
/*     */       {
/* 123 */         psFontsDir = new File(driveLetter + ":" + File.separator + "PSFONTS");
/* 124 */         if ((psFontsDir.exists()) && (psFontsDir.canRead()))
/*     */         {
/* 126 */           fontDirList.add(psFontsDir);
/* 127 */           break;
/*     */         }
/*     */       }
/*     */     }
/* 131 */     return fontDirList;
/*     */   }
/*     */ 
/*     */   public Map<String, String> getCommonTTFMapping()
/*     */   {
/* 139 */     HashMap map = new HashMap();
/* 140 */     map.put("Arial", "arialmt");
/* 141 */     map.put("Arial,Bold", "arialmtbold");
/* 142 */     map.put("Arial,Italic", "arialmtitalic");
/* 143 */     map.put("Arial,BoldItalic", "arialmtbolditalic");
/*     */ 
/* 145 */     map.put("TimesNewRoman", "timesnewromanpsmt");
/* 146 */     map.put("TimesNewRoman,Bold", "timesnewromanpsmtbold");
/* 147 */     map.put("TimesNewRoman,BoldItalic", "timesnewromanpsmtbolditalic");
/* 148 */     map.put("TimesNewRoman,Italic", "timesnewromanpsmtitalic");
/*     */ 
/* 150 */     map.put("Courier", "couriernewpsmt");
/* 151 */     map.put("Courier,Bold", "couriernewpsmtbold");
/* 152 */     map.put("Courier,Italic", "couriernewpsmtitalic");
/* 153 */     map.put("Courier,BoldItalic", "couriernewpsmtbolditalic");
/*     */ 
/* 155 */     map.put("Symbol", "symbolmt");
/* 156 */     map.put("ZapfDingbats", "Wingdings");
/*     */ 
/* 158 */     return Collections.unmodifiableMap(map);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.util.autodetect.WindowsFontDirFinder
 * JD-Core Version:    0.6.2
 */