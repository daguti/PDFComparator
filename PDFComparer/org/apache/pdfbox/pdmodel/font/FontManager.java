/*     */ package org.apache.pdfbox.pdmodel.font;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.io.IOException;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Properties;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.util.ResourceLoader;
/*     */ 
/*     */ public class FontManager
/*     */ {
/*  41 */   private static final Log LOG = LogFactory.getLog(FontManager.class);
/*     */ 
/*  44 */   private static HashMap<String, Font> envFonts = new HashMap();
/*     */   private static final String standardFont = "helvetica";
/*  47 */   private static Properties fontMapping = new Properties();
/*     */ 
/*     */   public static Font getStandardFont()
/*     */   {
/*  78 */     Font awtFont = getAwtFont("helvetica");
/*  79 */     if (awtFont == null)
/*     */     {
/*  82 */       LOG.error("Standard font 'helvetica' is not part of the environment");
/*  83 */       LOG.error("Available fonts:");
/*  84 */       for (Font font : GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts())
/*     */       {
/*  86 */         LOG.error("\t" + font.getFontName());
/*     */       }
/*     */     }
/*  89 */     return awtFont;
/*     */   }
/*     */ 
/*     */   public static Font getAwtFont(String font)
/*     */   {
/* 102 */     String fontname = normalizeFontname(font);
/* 103 */     if (envFonts.containsKey(fontname))
/*     */     {
/* 105 */       return (Font)envFonts.get(fontname);
/*     */     }
/* 107 */     return null;
/*     */   }
/*     */ 
/*     */   private static void loadFonts()
/*     */   {
/* 115 */     for (Font font : GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts())
/*     */     {
/* 117 */       String family = normalizeFontname(font.getFamily());
/* 118 */       String psname = normalizeFontname(font.getPSName());
/* 119 */       if (isBoldItalic(font))
/*     */       {
/* 121 */         envFonts.put(family + "bolditalic", font);
/*     */       }
/* 123 */       else if (isBold(font))
/*     */       {
/* 125 */         envFonts.put(family + "bold", font);
/*     */       }
/* 127 */       else if (isItalic(font))
/*     */       {
/* 129 */         envFonts.put(family + "italic", font);
/*     */       }
/*     */       else
/*     */       {
/* 133 */         envFonts.put(family, font);
/*     */       }
/* 135 */       if (!family.equals(psname))
/*     */       {
/* 137 */         envFonts.put(normalizeFontname(font.getPSName()), font);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static String normalizeFontname(String fontname)
/*     */   {
/* 153 */     String normalizedFontname = fontname.toLowerCase().replaceAll(" ", "").replaceAll(",", "").replaceAll("-", "");
/*     */ 
/* 157 */     if (normalizedFontname.indexOf("+") > -1)
/*     */     {
/* 159 */       normalizedFontname = normalizedFontname.substring(normalizedFontname.indexOf("+") + 1);
/*     */     }
/*     */ 
/* 163 */     boolean isBold = normalizedFontname.indexOf("bold") > -1;
/* 164 */     boolean isItalic = (normalizedFontname.indexOf("italic") > -1) || (normalizedFontname.indexOf("oblique") > -1);
/* 165 */     normalizedFontname = normalizedFontname.toLowerCase().replaceAll("bold", "").replaceAll("italic", "").replaceAll("oblique", "");
/*     */ 
/* 167 */     if (isBold)
/*     */     {
/* 169 */       normalizedFontname = normalizedFontname + "bold";
/*     */     }
/* 171 */     if (isItalic)
/*     */     {
/* 173 */       normalizedFontname = normalizedFontname + "italic";
/*     */     }
/* 175 */     return normalizedFontname;
/*     */   }
/*     */ 
/*     */   private static boolean addFontMapping(String font, String mappedName)
/*     */   {
/* 189 */     String fontname = normalizeFontname(font);
/*     */ 
/* 191 */     if (envFonts.containsKey(fontname))
/*     */     {
/* 193 */       return false;
/*     */     }
/* 195 */     String mappedFontname = normalizeFontname(mappedName);
/*     */ 
/* 197 */     if (!envFonts.containsKey(mappedFontname))
/*     */     {
/* 199 */       return false;
/*     */     }
/* 201 */     envFonts.put(fontname, envFonts.get(mappedFontname));
/* 202 */     return true;
/*     */   }
/*     */ 
/*     */   private static void loadFontMapping()
/*     */   {
/* 211 */     boolean addedMapping = true;
/*     */ 
/* 214 */     while (addedMapping)
/*     */     {
/* 216 */       int counter = 0;
/* 217 */       Enumeration keys = fontMapping.keys();
/* 218 */       while (keys.hasMoreElements())
/*     */       {
/* 220 */         String key = (String)keys.nextElement();
/* 221 */         if (addFontMapping(key, (String)fontMapping.get(key)))
/*     */         {
/* 223 */           counter++;
/*     */         }
/*     */       }
/* 226 */       if (counter == 0)
/*     */       {
/* 228 */         addedMapping = false;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void loadBasefontMapping()
/*     */   {
/* 240 */     if (!addFontMapping("Times-Roman", "TimesNewRoman"))
/*     */     {
/* 242 */       addFontMapping("Times-Roman", "Serif");
/*     */     }
/* 244 */     if (!addFontMapping("Times-Bold", "TimesNewRoman,Bold"))
/*     */     {
/* 246 */       addFontMapping("Times-Bold", "Serif.bold");
/*     */     }
/* 248 */     if (!addFontMapping("Times-Italic", "TimesNewRoman,Italic"))
/*     */     {
/* 250 */       addFontMapping("Times-Italic", "Serif.italic");
/*     */     }
/* 252 */     if (!addFontMapping("Times-BoldItalic", "TimesNewRoman,Bold,Italic"))
/*     */     {
/* 254 */       addFontMapping("Times-BoldItalic", "Serif.bolditalic");
/*     */     }
/*     */ 
/* 257 */     if (!addFontMapping("Helvetica", "Helvetica"))
/*     */     {
/* 259 */       addFontMapping("Helvetica", "SansSerif");
/*     */     }
/* 261 */     if (!addFontMapping("Helvetica-Bold", "Helvetica,Bold"))
/*     */     {
/* 263 */       addFontMapping("Helvetica-Bold", "SansSerif.bold");
/*     */     }
/* 265 */     if (!addFontMapping("Helvetica-Oblique", "Helvetica,Italic"))
/*     */     {
/* 267 */       addFontMapping("Helvetica-Oblique", "SansSerif.italic");
/*     */     }
/* 269 */     if (!addFontMapping("Helvetica-BoldOblique", "Helvetica,Bold,Italic"))
/*     */     {
/* 271 */       addFontMapping("Helvetica-BoldOblique", "SansSerif.bolditalic");
/*     */     }
/*     */ 
/* 274 */     if (!addFontMapping("Courier", "Courier"))
/*     */     {
/* 276 */       addFontMapping("Courier", "Monospaced");
/*     */     }
/* 278 */     if (!addFontMapping("Courier-Bold", "Courier,Bold"))
/*     */     {
/* 280 */       addFontMapping("Courier-Bold", "Monospaced.bold");
/*     */     }
/* 282 */     if (!addFontMapping("Courier-Oblique", "Courier,Italic"))
/*     */     {
/* 284 */       addFontMapping("Courier-Oblique", "Monospaced.italic");
/*     */     }
/* 286 */     if (!addFontMapping("Courier-BoldOblique", "Courier,Bold,Italic"))
/*     */     {
/* 288 */       addFontMapping("Courier-BoldOblique", "Monospaced.bolditalic");
/*     */     }
/*     */ 
/* 291 */     addFontMapping("Symbol", "StandardSymbolsL");
/* 292 */     addFontMapping("ZapfDingbats", "Dingbats");
/*     */   }
/*     */ 
/*     */   private static boolean isBoldItalic(Font font)
/*     */   {
/* 304 */     return (isBold(font)) && (isItalic(font));
/*     */   }
/*     */ 
/*     */   private static boolean isBold(Font font)
/*     */   {
/* 316 */     String name = font.getName().toLowerCase();
/* 317 */     if (name.indexOf("bold") > -1)
/*     */     {
/* 319 */       return true;
/*     */     }
/* 321 */     String psname = font.getPSName().toLowerCase();
/* 322 */     if (psname.indexOf("bold") > -1)
/*     */     {
/* 324 */       return true;
/*     */     }
/* 326 */     return false;
/*     */   }
/*     */ 
/*     */   private static boolean isItalic(Font font)
/*     */   {
/* 338 */     String name = font.getName().toLowerCase();
/*     */ 
/* 340 */     if ((name.indexOf("italic") > -1) || (name.indexOf("oblique") > -1))
/*     */     {
/* 342 */       return true;
/*     */     }
/* 344 */     String psname = font.getPSName().toLowerCase();
/* 345 */     if ((psname.indexOf("italic") > -1) || (psname.indexOf("oblique") > -1))
/*     */     {
/* 347 */       return true;
/*     */     }
/* 349 */     return false;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*     */     try
/*     */     {
/*  52 */       ResourceLoader.loadProperties("org/apache/pdfbox/resources/FontMapping.properties", fontMapping);
/*     */     }
/*     */     catch (IOException io)
/*     */     {
/*  58 */       LOG.error(io, io);
/*  59 */       throw new RuntimeException("Error loading font mapping");
/*     */     }
/*  61 */     loadFonts();
/*  62 */     loadBasefontMapping();
/*  63 */     loadFontMapping();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.font.FontManager
 * JD-Core Version:    0.6.2
 */