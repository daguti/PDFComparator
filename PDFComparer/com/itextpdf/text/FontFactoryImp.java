/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import com.itextpdf.text.log.Level;
/*     */ import com.itextpdf.text.log.Logger;
/*     */ import com.itextpdf.text.log.LoggerFactory;
/*     */ import com.itextpdf.text.pdf.BaseFont;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class FontFactoryImp
/*     */   implements FontProvider
/*     */ {
/*  69 */   private static final Logger LOGGER = LoggerFactory.getLogger(FontFactoryImp.class);
/*     */ 
/*  71 */   private final Hashtable<String, String> trueTypeFonts = new Hashtable();
/*     */ 
/*  73 */   private static String[] TTFamilyOrder = { "3", "1", "1033", "3", "0", "1033", "1", "0", "0", "0", "3", "0" };
/*     */ 
/*  81 */   private final Hashtable<String, ArrayList<String>> fontFamilies = new Hashtable();
/*     */ 
/*  84 */   public String defaultEncoding = "Cp1252";
/*     */ 
/*  87 */   public boolean defaultEmbedding = false;
/*     */ 
/*     */   public FontFactoryImp()
/*     */   {
/*  91 */     this.trueTypeFonts.put("Courier".toLowerCase(), "Courier");
/*  92 */     this.trueTypeFonts.put("Courier-Bold".toLowerCase(), "Courier-Bold");
/*  93 */     this.trueTypeFonts.put("Courier-Oblique".toLowerCase(), "Courier-Oblique");
/*  94 */     this.trueTypeFonts.put("Courier-BoldOblique".toLowerCase(), "Courier-BoldOblique");
/*  95 */     this.trueTypeFonts.put("Helvetica".toLowerCase(), "Helvetica");
/*  96 */     this.trueTypeFonts.put("Helvetica-Bold".toLowerCase(), "Helvetica-Bold");
/*  97 */     this.trueTypeFonts.put("Helvetica-Oblique".toLowerCase(), "Helvetica-Oblique");
/*  98 */     this.trueTypeFonts.put("Helvetica-BoldOblique".toLowerCase(), "Helvetica-BoldOblique");
/*  99 */     this.trueTypeFonts.put("Symbol".toLowerCase(), "Symbol");
/* 100 */     this.trueTypeFonts.put("Times-Roman".toLowerCase(), "Times-Roman");
/* 101 */     this.trueTypeFonts.put("Times-Bold".toLowerCase(), "Times-Bold");
/* 102 */     this.trueTypeFonts.put("Times-Italic".toLowerCase(), "Times-Italic");
/* 103 */     this.trueTypeFonts.put("Times-BoldItalic".toLowerCase(), "Times-BoldItalic");
/* 104 */     this.trueTypeFonts.put("ZapfDingbats".toLowerCase(), "ZapfDingbats");
/*     */ 
/* 107 */     ArrayList tmp = new ArrayList();
/* 108 */     tmp.add("Courier");
/* 109 */     tmp.add("Courier-Bold");
/* 110 */     tmp.add("Courier-Oblique");
/* 111 */     tmp.add("Courier-BoldOblique");
/* 112 */     this.fontFamilies.put("Courier".toLowerCase(), tmp);
/* 113 */     tmp = new ArrayList();
/* 114 */     tmp.add("Helvetica");
/* 115 */     tmp.add("Helvetica-Bold");
/* 116 */     tmp.add("Helvetica-Oblique");
/* 117 */     tmp.add("Helvetica-BoldOblique");
/* 118 */     this.fontFamilies.put("Helvetica".toLowerCase(), tmp);
/* 119 */     tmp = new ArrayList();
/* 120 */     tmp.add("Symbol");
/* 121 */     this.fontFamilies.put("Symbol".toLowerCase(), tmp);
/* 122 */     tmp = new ArrayList();
/* 123 */     tmp.add("Times-Roman");
/* 124 */     tmp.add("Times-Bold");
/* 125 */     tmp.add("Times-Italic");
/* 126 */     tmp.add("Times-BoldItalic");
/* 127 */     this.fontFamilies.put("Times".toLowerCase(), tmp);
/* 128 */     this.fontFamilies.put("Times-Roman".toLowerCase(), tmp);
/* 129 */     tmp = new ArrayList();
/* 130 */     tmp.add("ZapfDingbats");
/* 131 */     this.fontFamilies.put("ZapfDingbats".toLowerCase(), tmp);
/*     */   }
/*     */ 
/*     */   public Font getFont(String fontname, String encoding, boolean embedded, float size, int style, BaseColor color)
/*     */   {
/* 146 */     return getFont(fontname, encoding, embedded, size, style, color, true);
/*     */   }
/*     */ 
/*     */   public Font getFont(String fontname, String encoding, boolean embedded, float size, int style, BaseColor color, boolean cached)
/*     */   {
/* 165 */     if (fontname == null) return new Font(Font.FontFamily.UNDEFINED, size, style, color);
/* 166 */     String lowercasefontname = fontname.toLowerCase();
/* 167 */     ArrayList tmp = (ArrayList)this.fontFamilies.get(lowercasefontname);
/* 168 */     if (tmp != null) {
/* 169 */       synchronized (tmp)
/*     */       {
/* 171 */         int s = style == -1 ? 0 : style;
/* 172 */         int fs = 0;
/* 173 */         boolean found = false;
/* 174 */         for (String f : tmp) {
/* 175 */           String lcf = f.toLowerCase();
/* 176 */           fs = 0;
/* 177 */           if (lcf.indexOf("bold") != -1) fs |= 1;
/* 178 */           if ((lcf.indexOf("italic") != -1) || (lcf.indexOf("oblique") != -1)) fs |= 2;
/* 179 */           if ((s & 0x3) == fs) {
/* 180 */             fontname = f;
/* 181 */             found = true;
/* 182 */             break;
/*     */           }
/*     */         }
/* 185 */         if ((style != -1) && (found)) {
/* 186 */           style &= (fs ^ 0xFFFFFFFF);
/*     */         }
/*     */       }
/*     */     }
/* 190 */     BaseFont basefont = null;
/*     */     try
/*     */     {
/*     */       try {
/* 194 */         basefont = BaseFont.createFont(fontname, encoding, embedded, cached, null, null, true);
/*     */       }
/*     */       catch (DocumentException de) {
/*     */       }
/* 198 */       if (basefont == null)
/*     */       {
/* 200 */         fontname = (String)this.trueTypeFonts.get(fontname.toLowerCase());
/*     */ 
/* 202 */         if (fontname == null) return new Font(Font.FontFamily.UNDEFINED, size, style, color);
/*     */ 
/* 204 */         basefont = BaseFont.createFont(fontname, encoding, embedded, cached, null, null);
/*     */       }
/*     */     }
/*     */     catch (DocumentException de)
/*     */     {
/* 209 */       throw new ExceptionConverter(de);
/*     */     }
/*     */     catch (IOException ioe)
/*     */     {
/* 213 */       return new Font(Font.FontFamily.UNDEFINED, size, style, color);
/*     */     }
/*     */     catch (NullPointerException npe)
/*     */     {
/* 217 */       return new Font(Font.FontFamily.UNDEFINED, size, style, color);
/*     */     }
/* 219 */     return new Font(basefont, size, style, color);
/*     */   }
/*     */ 
/*     */   public Font getFont(String fontname, String encoding, boolean embedded, float size, int style)
/*     */   {
/* 234 */     return getFont(fontname, encoding, embedded, size, style, null);
/*     */   }
/*     */ 
/*     */   public Font getFont(String fontname, String encoding, boolean embedded, float size)
/*     */   {
/* 248 */     return getFont(fontname, encoding, embedded, size, -1, null);
/*     */   }
/*     */ 
/*     */   public Font getFont(String fontname, String encoding, boolean embedded)
/*     */   {
/* 261 */     return getFont(fontname, encoding, embedded, -1.0F, -1, null);
/*     */   }
/*     */ 
/*     */   public Font getFont(String fontname, String encoding, float size, int style, BaseColor color)
/*     */   {
/* 276 */     return getFont(fontname, encoding, this.defaultEmbedding, size, style, color);
/*     */   }
/*     */ 
/*     */   public Font getFont(String fontname, String encoding, float size, int style)
/*     */   {
/* 290 */     return getFont(fontname, encoding, this.defaultEmbedding, size, style, null);
/*     */   }
/*     */ 
/*     */   public Font getFont(String fontname, String encoding, float size)
/*     */   {
/* 303 */     return getFont(fontname, encoding, this.defaultEmbedding, size, -1, null);
/*     */   }
/*     */ 
/*     */   public Font getFont(String fontname, float size, BaseColor color)
/*     */   {
/* 318 */     return getFont(fontname, this.defaultEncoding, this.defaultEmbedding, size, -1, color);
/*     */   }
/*     */ 
/*     */   public Font getFont(String fontname, String encoding)
/*     */   {
/* 330 */     return getFont(fontname, encoding, this.defaultEmbedding, -1.0F, -1, null);
/*     */   }
/*     */ 
/*     */   public Font getFont(String fontname, float size, int style, BaseColor color)
/*     */   {
/* 344 */     return getFont(fontname, this.defaultEncoding, this.defaultEmbedding, size, style, color);
/*     */   }
/*     */ 
/*     */   public Font getFont(String fontname, float size, int style)
/*     */   {
/* 357 */     return getFont(fontname, this.defaultEncoding, this.defaultEmbedding, size, style, null);
/*     */   }
/*     */ 
/*     */   public Font getFont(String fontname, float size)
/*     */   {
/* 369 */     return getFont(fontname, this.defaultEncoding, this.defaultEmbedding, size, -1, null);
/*     */   }
/*     */ 
/*     */   public Font getFont(String fontname)
/*     */   {
/* 380 */     return getFont(fontname, this.defaultEncoding, this.defaultEmbedding, -1.0F, -1, null);
/*     */   }
/*     */ 
/*     */   public void registerFamily(String familyName, String fullName, String path)
/*     */   {
/* 390 */     if (path != null)
/* 391 */       this.trueTypeFonts.put(fullName, path);
/*     */     ArrayList tmp;
/* 393 */     synchronized (this.fontFamilies) {
/* 394 */       tmp = (ArrayList)this.fontFamilies.get(familyName);
/* 395 */       if (tmp == null) {
/* 396 */         tmp = new ArrayList();
/* 397 */         this.fontFamilies.put(familyName, tmp);
/*     */       }
/*     */     }
/* 400 */     synchronized (tmp) {
/* 401 */       if (!tmp.contains(fullName)) {
/* 402 */         int fullNameLength = fullName.length();
/* 403 */         boolean inserted = false;
/* 404 */         for (int j = 0; j < tmp.size(); j++) {
/* 405 */           if (((String)tmp.get(j)).length() >= fullNameLength) {
/* 406 */             tmp.add(j, fullName);
/* 407 */             inserted = true;
/* 408 */             break;
/*     */           }
/*     */         }
/* 411 */         if (!inserted)
/* 412 */           tmp.add(fullName);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void register(String path)
/*     */   {
/* 424 */     register(path, null);
/*     */   }
/*     */ 
/*     */   public void register(String path, String alias)
/*     */   {
/*     */     try
/*     */     {
/* 436 */       if ((path.toLowerCase().endsWith(".ttf")) || (path.toLowerCase().endsWith(".otf")) || (path.toLowerCase().indexOf(".ttc,") > 0)) {
/* 437 */         Object[] allNames = BaseFont.getAllFontNames(path, "Cp1252", null);
/* 438 */         this.trueTypeFonts.put(((String)allNames[0]).toLowerCase(), path);
/* 439 */         if (alias != null) {
/* 440 */           this.trueTypeFonts.put(alias.toLowerCase(), path);
/*     */         }
/*     */ 
/* 443 */         String[][] names = (String[][])allNames[2];
/* 444 */         for (String[] name : names) {
/* 445 */           this.trueTypeFonts.put(name[3].toLowerCase(), path);
/*     */         }
/* 447 */         String fullName = null;
/* 448 */         String familyName = null;
/* 449 */         names = (String[][])allNames[1];
/* 450 */         for (int k = 0; k < TTFamilyOrder.length; k += 3) {
/* 451 */           for (String[] name : names) {
/* 452 */             if ((TTFamilyOrder[k].equals(name[0])) && (TTFamilyOrder[(k + 1)].equals(name[1])) && (TTFamilyOrder[(k + 2)].equals(name[2]))) {
/* 453 */               familyName = name[3].toLowerCase();
/* 454 */               k = TTFamilyOrder.length;
/* 455 */               break;
/*     */             }
/*     */           }
/*     */         }
/* 459 */         if (familyName != null) {
/* 460 */           String lastName = "";
/* 461 */           names = (String[][])allNames[2];
/* 462 */           for (String[] name : names)
/* 463 */             for (int k = 0; k < TTFamilyOrder.length; k += 3)
/*     */             {
/*     */               String[] names;
/*     */               int i;
/*     */               BaseFont bf;
/*     */               String fullName;
/*     */               String familyName;
/*     */               String psName;
/* 464 */               if ((TTFamilyOrder[k].equals(name[0])) && (TTFamilyOrder[(k + 1)].equals(name[1])) && (TTFamilyOrder[(k + 2)].equals(name[2]))) {
/* 465 */                 fullName = name[3];
/* 466 */                 if (!fullName.equals(lastName))
/*     */                 {
/* 468 */                   lastName = fullName;
/* 469 */                   registerFamily(familyName, fullName, null);
/* 470 */                   break;
/*     */                 }
/*     */               }
/*     */             }
/*     */         }
/*     */       }
/* 476 */       else if (path.toLowerCase().endsWith(".ttc")) {
/* 477 */         if (alias != null)
/* 478 */           LOGGER.error("You can't define an alias for a true type collection.");
/* 479 */         names = BaseFont.enumerateTTCNames(path);
/* 480 */         for (i = 0; i < names.length; i++) {
/* 481 */           register(path + "," + i);
/*     */         }
/*     */       }
/* 484 */       else if ((path.toLowerCase().endsWith(".afm")) || (path.toLowerCase().endsWith(".pfm"))) {
/* 485 */         bf = BaseFont.createFont(path, "Cp1252", false);
/* 486 */         fullName = bf.getFullFontName()[0][3].toLowerCase();
/* 487 */         familyName = bf.getFamilyFontName()[0][3].toLowerCase();
/* 488 */         psName = bf.getPostscriptFontName().toLowerCase();
/* 489 */         registerFamily(familyName, fullName, null);
/* 490 */         this.trueTypeFonts.put(psName, path);
/* 491 */         this.trueTypeFonts.put(fullName, path);
/*     */       }
/* 493 */       if (LOGGER.isLogging(Level.TRACE)) {
/* 494 */         LOGGER.trace(String.format("Registered %s", new Object[] { path }));
/*     */       }
/*     */     }
/*     */     catch (DocumentException de)
/*     */     {
/* 499 */       throw new ExceptionConverter(de);
/*     */     }
/*     */     catch (IOException ioe) {
/* 502 */       throw new ExceptionConverter(ioe);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int registerDirectory(String dir)
/*     */   {
/* 511 */     return registerDirectory(dir, false);
/*     */   }
/*     */ 
/*     */   public int registerDirectory(String dir, boolean scanSubdirectories)
/*     */   {
/* 522 */     if (LOGGER.isLogging(Level.DEBUG)) {
/* 523 */       LOGGER.debug(String.format("Registering directory %s, looking for fonts", new Object[] { dir }));
/*     */     }
/* 525 */     int count = 0;
/*     */     try {
/* 527 */       File file = new File(dir);
/* 528 */       if ((!file.exists()) || (!file.isDirectory()))
/* 529 */         return 0;
/* 530 */       String[] files = file.list();
/* 531 */       if (files == null)
/* 532 */         return 0;
/* 533 */       for (int k = 0; k < files.length; k++) {
/*     */         try {
/* 535 */           file = new File(dir, files[k]);
/* 536 */           if (file.isDirectory()) {
/* 537 */             if (scanSubdirectories)
/* 538 */               count += registerDirectory(file.getAbsolutePath(), true);
/*     */           }
/*     */           else {
/* 541 */             String name = file.getPath();
/* 542 */             String suffix = name.length() < 4 ? null : name.substring(name.length() - 4).toLowerCase();
/* 543 */             if ((".afm".equals(suffix)) || (".pfm".equals(suffix)))
/*     */             {
/* 545 */               File pfb = new File(name.substring(0, name.length() - 4) + ".pfb");
/* 546 */               if (pfb.exists()) {
/* 547 */                 register(name, null);
/* 548 */                 count++;
/*     */               }
/* 550 */             } else if ((".ttf".equals(suffix)) || (".otf".equals(suffix)) || (".ttc".equals(suffix))) {
/* 551 */               register(name, null);
/* 552 */               count++;
/*     */             }
/*     */           }
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/* 564 */     return count;
/*     */   }
/*     */ 
/*     */   public int registerDirectories()
/*     */   {
/* 572 */     int count = 0;
/* 573 */     String windir = System.getenv("windir");
/* 574 */     String fileseparator = System.getProperty("file.separator");
/* 575 */     if ((windir != null) && (fileseparator != null)) {
/* 576 */       count += registerDirectory(windir + fileseparator + "fonts");
/*     */     }
/* 578 */     count += registerDirectory("/usr/share/X11/fonts", true);
/* 579 */     count += registerDirectory("/usr/X/lib/X11/fonts", true);
/* 580 */     count += registerDirectory("/usr/openwin/lib/X11/fonts", true);
/* 581 */     count += registerDirectory("/usr/share/fonts", true);
/* 582 */     count += registerDirectory("/usr/X11R6/lib/X11/fonts", true);
/* 583 */     count += registerDirectory("/Library/Fonts");
/* 584 */     count += registerDirectory("/System/Library/Fonts");
/* 585 */     return count;
/*     */   }
/*     */ 
/*     */   public Set<String> getRegisteredFonts()
/*     */   {
/* 594 */     return this.trueTypeFonts.keySet();
/*     */   }
/*     */ 
/*     */   public Set<String> getRegisteredFamilies()
/*     */   {
/* 603 */     return this.fontFamilies.keySet();
/*     */   }
/*     */ 
/*     */   public boolean isRegistered(String fontname)
/*     */   {
/* 613 */     return this.trueTypeFonts.containsKey(fontname.toLowerCase());
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.FontFactoryImp
 * JD-Core Version:    0.6.2
 */