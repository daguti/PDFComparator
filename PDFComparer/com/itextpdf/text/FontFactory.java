/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import java.util.Set;
/*     */ 
/*     */ public final class FontFactory
/*     */ {
/*     */   public static final String COURIER = "Courier";
/*     */   public static final String COURIER_BOLD = "Courier-Bold";
/*     */   public static final String COURIER_OBLIQUE = "Courier-Oblique";
/*     */   public static final String COURIER_BOLDOBLIQUE = "Courier-BoldOblique";
/*     */   public static final String HELVETICA = "Helvetica";
/*     */   public static final String HELVETICA_BOLD = "Helvetica-Bold";
/*     */   public static final String HELVETICA_OBLIQUE = "Helvetica-Oblique";
/*     */   public static final String HELVETICA_BOLDOBLIQUE = "Helvetica-BoldOblique";
/*     */   public static final String SYMBOL = "Symbol";
/*     */   public static final String TIMES = "Times";
/*     */   public static final String TIMES_ROMAN = "Times-Roman";
/*     */   public static final String TIMES_BOLD = "Times-Bold";
/*     */   public static final String TIMES_ITALIC = "Times-Italic";
/*     */   public static final String TIMES_BOLDITALIC = "Times-BoldItalic";
/*     */   public static final String ZAPFDINGBATS = "ZapfDingbats";
/* 107 */   private static FontFactoryImp fontImp = new FontFactoryImp();
/*     */ 
/* 110 */   public static String defaultEncoding = "Cp1252";
/*     */ 
/* 113 */   public static boolean defaultEmbedding = false;
/*     */ 
/*     */   public static Font getFont(String fontname, String encoding, boolean embedded, float size, int style, BaseColor color)
/*     */   {
/* 132 */     return fontImp.getFont(fontname, encoding, embedded, size, style, color);
/*     */   }
/*     */ 
/*     */   public static Font getFont(String fontname, String encoding, boolean embedded, float size, int style, BaseColor color, boolean cached)
/*     */   {
/* 150 */     return fontImp.getFont(fontname, encoding, embedded, size, style, color, cached);
/*     */   }
/*     */ 
/*     */   public static Font getFont(String fontname, String encoding, boolean embedded, float size, int style)
/*     */   {
/* 165 */     return getFont(fontname, encoding, embedded, size, style, null);
/*     */   }
/*     */ 
/*     */   public static Font getFont(String fontname, String encoding, boolean embedded, float size)
/*     */   {
/* 179 */     return getFont(fontname, encoding, embedded, size, -1, null);
/*     */   }
/*     */ 
/*     */   public static Font getFont(String fontname, String encoding, boolean embedded)
/*     */   {
/* 192 */     return getFont(fontname, encoding, embedded, -1.0F, -1, null);
/*     */   }
/*     */ 
/*     */   public static Font getFont(String fontname, String encoding, float size, int style, BaseColor color)
/*     */   {
/* 207 */     return getFont(fontname, encoding, defaultEmbedding, size, style, color);
/*     */   }
/*     */ 
/*     */   public static Font getFont(String fontname, String encoding, float size, int style)
/*     */   {
/* 221 */     return getFont(fontname, encoding, defaultEmbedding, size, style, null);
/*     */   }
/*     */ 
/*     */   public static Font getFont(String fontname, String encoding, float size)
/*     */   {
/* 234 */     return getFont(fontname, encoding, defaultEmbedding, size, -1, null);
/*     */   }
/*     */ 
/*     */   public static Font getFont(String fontname, String encoding)
/*     */   {
/* 246 */     return getFont(fontname, encoding, defaultEmbedding, -1.0F, -1, null);
/*     */   }
/*     */ 
/*     */   public static Font getFont(String fontname, float size, int style, BaseColor color)
/*     */   {
/* 260 */     return getFont(fontname, defaultEncoding, defaultEmbedding, size, style, color);
/*     */   }
/*     */ 
/*     */   public static Font getFont(String fontname, float size, BaseColor color)
/*     */   {
/* 274 */     return getFont(fontname, defaultEncoding, defaultEmbedding, size, -1, color);
/*     */   }
/*     */ 
/*     */   public static Font getFont(String fontname, float size, int style)
/*     */   {
/* 287 */     return getFont(fontname, defaultEncoding, defaultEmbedding, size, style, null);
/*     */   }
/*     */ 
/*     */   public static Font getFont(String fontname, float size)
/*     */   {
/* 299 */     return getFont(fontname, defaultEncoding, defaultEmbedding, size, -1, null);
/*     */   }
/*     */ 
/*     */   public static Font getFont(String fontname)
/*     */   {
/* 310 */     return getFont(fontname, defaultEncoding, defaultEmbedding, -1.0F, -1, null);
/*     */   }
/*     */ 
/*     */   public static void registerFamily(String familyName, String fullName, String path)
/*     */   {
/* 320 */     fontImp.registerFamily(familyName, fullName, path);
/*     */   }
/*     */ 
/*     */   public static void register(String path)
/*     */   {
/* 330 */     register(path, null);
/*     */   }
/*     */ 
/*     */   public static void register(String path, String alias)
/*     */   {
/* 341 */     fontImp.register(path, alias);
/*     */   }
/*     */ 
/*     */   public static int registerDirectory(String dir)
/*     */   {
/* 349 */     return fontImp.registerDirectory(dir);
/*     */   }
/*     */ 
/*     */   public static int registerDirectory(String dir, boolean scanSubdirectories)
/*     */   {
/* 360 */     return fontImp.registerDirectory(dir, scanSubdirectories);
/*     */   }
/*     */ 
/*     */   public static int registerDirectories()
/*     */   {
/* 368 */     return fontImp.registerDirectories();
/*     */   }
/*     */ 
/*     */   public static Set<String> getRegisteredFonts()
/*     */   {
/* 377 */     return fontImp.getRegisteredFonts();
/*     */   }
/*     */ 
/*     */   public static Set<String> getRegisteredFamilies()
/*     */   {
/* 386 */     return fontImp.getRegisteredFamilies();
/*     */   }
/*     */ 
/*     */   public static boolean contains(String fontname)
/*     */   {
/* 396 */     return fontImp.isRegistered(fontname);
/*     */   }
/*     */ 
/*     */   public static boolean isRegistered(String fontname)
/*     */   {
/* 407 */     return fontImp.isRegistered(fontname);
/*     */   }
/*     */ 
/*     */   public static FontFactoryImp getFontImp()
/*     */   {
/* 415 */     return fontImp;
/*     */   }
/*     */ 
/*     */   public static void setFontImp(FontFactoryImp fontImp)
/*     */   {
/* 423 */     if (fontImp == null)
/* 424 */       throw new NullPointerException(MessageLocalization.getComposedMessage("fontfactoryimp.cannot.be.null", new Object[0]));
/* 425 */     fontImp = fontImp;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.FontFactory
 * JD-Core Version:    0.6.2
 */