/*     */ package com.itextpdf.awt;
/*     */ 
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import com.itextpdf.text.pdf.BaseFont;
/*     */ import java.awt.Font;
/*     */ import java.io.File;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class DefaultFontMapper
/*     */   implements FontMapper
/*     */ {
/*  94 */   private HashMap<String, String> aliases = new HashMap();
/*     */ 
/*  97 */   private HashMap<String, BaseFontParameters> mapper = new HashMap();
/*     */ 
/*     */   public BaseFont awtToPdf(Font font)
/*     */   {
/*     */     try
/*     */     {
/* 107 */       BaseFontParameters p = getBaseFontParameters(font.getFontName());
/* 108 */       if (p != null)
/* 109 */         return BaseFont.createFont(p.fontName, p.encoding, p.embedded, p.cached, p.ttfAfm, p.pfb);
/* 110 */       String fontKey = null;
/* 111 */       String logicalName = font.getName();
/*     */ 
/* 113 */       if ((logicalName.equalsIgnoreCase("DialogInput")) || (logicalName.equalsIgnoreCase("Monospaced")) || (logicalName.equalsIgnoreCase("Courier")))
/*     */       {
/* 115 */         if (font.isItalic()) {
/* 116 */           if (font.isBold()) {
/* 117 */             fontKey = "Courier-BoldOblique";
/*     */           }
/*     */           else {
/* 120 */             fontKey = "Courier-Oblique";
/*     */           }
/*     */ 
/*     */         }
/* 124 */         else if (font.isBold()) {
/* 125 */           fontKey = "Courier-Bold";
/*     */         }
/*     */         else {
/* 128 */           fontKey = "Courier";
/*     */         }
/*     */ 
/*     */       }
/* 132 */       else if ((logicalName.equalsIgnoreCase("Serif")) || (logicalName.equalsIgnoreCase("TimesRoman")))
/*     */       {
/* 134 */         if (font.isItalic()) {
/* 135 */           if (font.isBold()) {
/* 136 */             fontKey = "Times-BoldItalic";
/*     */           }
/*     */           else {
/* 139 */             fontKey = "Times-Italic";
/*     */           }
/*     */ 
/*     */         }
/* 143 */         else if (font.isBold()) {
/* 144 */           fontKey = "Times-Bold";
/*     */         }
/*     */         else {
/* 147 */           fontKey = "Times-Roman";
/*     */         }
/*     */ 
/*     */       }
/* 153 */       else if (font.isItalic()) {
/* 154 */         if (font.isBold()) {
/* 155 */           fontKey = "Helvetica-BoldOblique";
/*     */         }
/*     */         else {
/* 158 */           fontKey = "Helvetica-Oblique";
/*     */         }
/*     */ 
/*     */       }
/* 162 */       else if (font.isBold())
/* 163 */         fontKey = "Helvetica-Bold";
/*     */       else {
/* 165 */         fontKey = "Helvetica";
/*     */       }
/*     */ 
/* 169 */       return BaseFont.createFont(fontKey, "Cp1252", false);
/*     */     }
/*     */     catch (Exception e) {
/* 172 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Font pdfToAwt(BaseFont font, int size)
/*     */   {
/* 185 */     String[][] names = font.getFullFontName();
/* 186 */     if (names.length == 1)
/* 187 */       return new Font(names[0][3], 0, size);
/* 188 */     String name10 = null;
/* 189 */     String name3x = null;
/* 190 */     for (int k = 0; k < names.length; k++) {
/* 191 */       String[] name = names[k];
/* 192 */       if ((name[0].equals("1")) && (name[1].equals("0"))) {
/* 193 */         name10 = name[3];
/* 194 */       } else if (name[2].equals("1033")) {
/* 195 */         name3x = name[3];
/* 196 */         break;
/*     */       }
/*     */     }
/* 199 */     String finalName = name3x;
/* 200 */     if (finalName == null)
/* 201 */       finalName = name10;
/* 202 */     if (finalName == null)
/* 203 */       finalName = names[0][3];
/* 204 */     return new Font(finalName, 0, size);
/*     */   }
/*     */ 
/*     */   public void putName(String awtName, BaseFontParameters parameters)
/*     */   {
/* 212 */     this.mapper.put(awtName, parameters);
/*     */   }
/*     */ 
/*     */   public void putAlias(String alias, String awtName)
/*     */   {
/* 220 */     this.aliases.put(alias, awtName);
/*     */   }
/*     */ 
/*     */   public BaseFontParameters getBaseFontParameters(String name)
/*     */   {
/* 228 */     String alias = (String)this.aliases.get(name);
/* 229 */     if (alias == null)
/* 230 */       return (BaseFontParameters)this.mapper.get(name);
/* 231 */     BaseFontParameters p = (BaseFontParameters)this.mapper.get(alias);
/* 232 */     if (p == null) {
/* 233 */       return (BaseFontParameters)this.mapper.get(name);
/*     */     }
/* 235 */     return p;
/*     */   }
/*     */ 
/*     */   public void insertNames(Object[] allNames, String path)
/*     */   {
/* 244 */     String[][] names = (String[][])allNames[2];
/* 245 */     String main = null;
/* 246 */     for (int k = 0; k < names.length; k++) {
/* 247 */       String[] name = names[k];
/* 248 */       if (name[2].equals("1033")) {
/* 249 */         main = name[3];
/* 250 */         break;
/*     */       }
/*     */     }
/* 253 */     if (main == null)
/* 254 */       main = names[0][3];
/* 255 */     BaseFontParameters p = new BaseFontParameters(path);
/* 256 */     this.mapper.put(main, p);
/* 257 */     for (int k = 0; k < names.length; k++) {
/* 258 */       this.aliases.put(names[k][3], main);
/*     */     }
/* 260 */     this.aliases.put((String)allNames[0], main);
/*     */   }
/*     */ 
/*     */   public int insertFile(File file)
/*     */   {
/* 271 */     String name = file.getPath().toLowerCase();
/*     */     try {
/* 273 */       if ((name.endsWith(".ttf")) || (name.endsWith(".otf")) || (name.endsWith(".afm"))) {
/* 274 */         Object[] allNames = BaseFont.getAllFontNames(file.getPath(), "Cp1252", null);
/* 275 */         insertNames(allNames, file.getPath());
/* 276 */         return 1;
/* 277 */       }if (name.endsWith(".ttc")) {
/* 278 */         String[] ttcs = BaseFont.enumerateTTCNames(file.getPath());
/* 279 */         for (int j = 0; j < ttcs.length; j++) {
/* 280 */           String nt = file.getPath() + "," + j;
/* 281 */           Object[] allNames = BaseFont.getAllFontNames(nt, "Cp1252", null);
/* 282 */           insertNames(allNames, nt);
/*     */         }
/* 284 */         return 1;
/*     */       }
/*     */     } catch (Exception e) {
/*     */     }
/* 288 */     return 0;
/*     */   }
/*     */ 
/*     */   public int insertDirectory(String dir)
/*     */   {
/* 299 */     File file = new File(dir);
/* 300 */     if ((!file.exists()) || (!file.isDirectory()))
/* 301 */       return 0;
/* 302 */     File[] files = file.listFiles();
/* 303 */     if (files == null)
/* 304 */       return 0;
/* 305 */     int count = 0;
/* 306 */     for (int k = 0; k < files.length; k++) {
/* 307 */       count += insertFile(files[k]);
/*     */     }
/* 309 */     return count;
/*     */   }
/*     */ 
/*     */   public HashMap<String, BaseFontParameters> getMapper() {
/* 313 */     return this.mapper;
/*     */   }
/*     */ 
/*     */   public HashMap<String, String> getAliases() {
/* 317 */     return this.aliases;
/*     */   }
/*     */ 
/*     */   public static class BaseFontParameters
/*     */   {
/*     */     public String fontName;
/*     */     public String encoding;
/*     */     public boolean embedded;
/*     */     public boolean cached;
/*     */     public byte[] ttfAfm;
/*     */     public byte[] pfb;
/*     */ 
/*     */     public BaseFontParameters(String fontName)
/*     */     {
/*  85 */       this.fontName = fontName;
/*  86 */       this.encoding = "Cp1252";
/*  87 */       this.embedded = true;
/*  88 */       this.cached = true;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.awt.DefaultFontMapper
 * JD-Core Version:    0.6.2
 */