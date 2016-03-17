/*     */ package org.apache.fontbox.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URI;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.fontbox.ttf.NamingTable;
/*     */ import org.apache.fontbox.ttf.TTFParser;
/*     */ import org.apache.fontbox.ttf.TrueTypeFont;
/*     */ import org.apache.fontbox.util.autodetect.FontFileFinder;
/*     */ 
/*     */ public class FontManager
/*     */ {
/*  44 */   private static final Log LOG = LogFactory.getLog(FontManager.class);
/*     */ 
/*  46 */   private static HashMap<String, String> ttfFontfiles = new HashMap();
/*     */ 
/*  48 */   private static boolean fontsLoaded = false;
/*     */ 
/*  51 */   private static HashMap<String, String> fontMappingTTF = new HashMap();
/*     */ 
/*     */   private static void loadFonts()
/*     */   {
/*     */     try
/*     */     {
/*  64 */       FontFileFinder fontfinder = new FontFileFinder();
/*  65 */       List fonts = fontfinder.find();
/*  66 */       for (URI font : fonts)
/*     */       {
/*     */         try
/*     */         {
/*  72 */           String fontfilename = new File(font).getPath();
/*  73 */           if (fontfilename.toLowerCase().endsWith(".ttf"))
/*     */           {
/*  75 */             analyzeTTF(fontfilename);
/*     */           }
/*     */           else
/*     */           {
/*  79 */             LOG.debug("Unsupported font format for external font: " + fontfilename);
/*     */           }
/*     */         }
/*     */         catch (IOException exception)
/*     */         {
/*  84 */           LOG.debug("Can't read external font: " + font.getPath(), exception);
/*     */         }
/*     */       }
/*  87 */       addFontMapping(fontfinder.getCommonTTFMapping(), fontMappingTTF);
/*  88 */       createFontmapping();
/*     */     }
/*     */     catch (IOException exception)
/*     */     {
/*  92 */       LOG.error("An error occured when collecting external fonts.", exception);
/*     */     }
/*     */     finally
/*     */     {
/*  96 */       fontsLoaded = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void analyzeTTF(String ttfFilename)
/*     */     throws IOException
/*     */   {
/* 108 */     TTFParser ttfParser = new TTFParser(false, true);
/* 109 */     TrueTypeFont ttfFont = ttfParser.parseTTF(ttfFilename);
/* 110 */     if (ttfFont != null)
/*     */     {
/* 112 */       NamingTable namingTable = ttfFont.getNaming();
/* 113 */       if ((namingTable != null) && (namingTable.getPSName() != null))
/*     */       {
/* 115 */         String normalizedName = normalizeFontname(namingTable.getPSName());
/* 116 */         if (!ttfFontfiles.containsKey(normalizedName))
/*     */         {
/* 118 */           LOG.debug("Added font mapping " + normalizedName + " -=> " + ttfFilename);
/* 119 */           ttfFontfiles.put(normalizedName, ttfFilename);
/*     */         }
/*     */       }
/*     */ 
/* 123 */       if ((namingTable != null) && (namingTable.getFontFamily() != null))
/*     */       {
/* 125 */         String normalizedName = normalizeFontFamily(namingTable.getFontFamily(), namingTable.getPSName());
/* 126 */         if (!ttfFontfiles.containsKey(normalizedName))
/*     */         {
/* 128 */           LOG.debug("Added font mapping " + normalizedName + " -=> " + ttfFilename);
/* 129 */           ttfFontfiles.put(normalizedName, ttfFilename);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static String normalizeFontname(String fontname)
/*     */   {
/* 145 */     String normalizedFontname = fontname.toLowerCase().replaceAll(" ", "").replaceAll(",", "").replaceAll("-", "");
/*     */ 
/* 150 */     if (normalizedFontname.indexOf("+") > -1)
/*     */     {
/* 152 */       normalizedFontname = normalizedFontname.substring(normalizedFontname.indexOf("+") + 1);
/*     */     }
/*     */ 
/* 156 */     boolean isBold = normalizedFontname.indexOf("bold") > -1;
/* 157 */     boolean isItalic = (normalizedFontname.indexOf("italic") > -1) || (normalizedFontname.indexOf("oblique") > -1);
/* 158 */     normalizedFontname = normalizedFontname.replaceAll("bold", "").replaceAll("italic", "").replaceAll("oblique", "");
/*     */ 
/* 160 */     if (isBold)
/*     */     {
/* 162 */       normalizedFontname = normalizedFontname + "bold";
/*     */     }
/* 164 */     if (isItalic)
/*     */     {
/* 166 */       normalizedFontname = normalizedFontname + "italic";
/*     */     }
/* 168 */     return normalizedFontname;
/*     */   }
/*     */ 
/*     */   private static String normalizeFontFamily(String fontFamily, String psFontName)
/*     */   {
/* 173 */     String normalizedFontFamily = fontFamily.toLowerCase().replaceAll(" ", "").replaceAll(",", "").replaceAll("-", "");
/* 174 */     if (psFontName != null)
/*     */     {
/* 176 */       psFontName = psFontName.toLowerCase();
/*     */ 
/* 178 */       boolean isBold = psFontName.indexOf("bold") > -1;
/* 179 */       boolean isItalic = (psFontName.indexOf("italic") > -1) || (psFontName.indexOf("oblique") > -1);
/*     */ 
/* 181 */       if (isBold)
/*     */       {
/* 183 */         normalizedFontFamily = normalizedFontFamily + "bold";
/*     */       }
/* 185 */       if (isItalic)
/*     */       {
/* 187 */         normalizedFontFamily = normalizedFontFamily + "italic";
/*     */       }
/*     */     }
/* 190 */     return normalizedFontFamily;
/*     */   }
/*     */ 
/*     */   private static void addFontMapping(String font, String mappedName, Map<String, String> mapping)
/*     */   {
/* 202 */     String fontname = normalizeFontname(font);
/*     */ 
/* 204 */     if (mapping.containsKey(fontname))
/*     */     {
/* 206 */       return;
/*     */     }
/* 208 */     String mappedFontname = normalizeFontname(mappedName);
/*     */ 
/* 210 */     if (ttfFontfiles.containsKey(mappedFontname))
/*     */     {
/* 212 */       mapping.put(fontname, mappedFontname);
/*     */     }
/* 217 */     else if (mapping.containsKey(mappedFontname))
/*     */     {
/* 219 */       mapping.put(fontname, mapping.get(mappedFontname));
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void addFontMapping(Map<String, String> fontMappingSrc, Map<String, String> fontMappingDest)
/*     */   {
/* 232 */     for (String fontname : fontMappingSrc.keySet())
/*     */     {
/* 234 */       addFontMapping(fontname, (String)fontMappingSrc.get(fontname), fontMappingDest);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static String getMappedTTFName(String fontname)
/*     */   {
/* 246 */     String normalizedFontname = normalizeFontname(fontname);
/* 247 */     if (fontMappingTTF.containsKey(normalizedFontname))
/*     */     {
/* 249 */       return (String)fontMappingTTF.get(normalizedFontname);
/*     */     }
/* 251 */     return null;
/*     */   }
/*     */ 
/*     */   private static void createFontmapping()
/*     */   {
/* 259 */     addFontFamilyMapping("ArialNarrow", "Arial", fontMappingTTF);
/* 260 */     addFontFamilyMapping("ArialMT", "Arial", fontMappingTTF);
/* 261 */     addFontFamilyMapping("CourierNew", "Courier", fontMappingTTF);
/* 262 */     addFontFamilyMapping("TimesNewRomanPSMT", "TimesNewRoman", fontMappingTTF);
/*     */   }
/*     */ 
/*     */   private static void addFontFamilyMapping(String fontfamily, String mappedFontfamily, Map<String, String> mapping)
/*     */   {
/* 274 */     addFontMapping(fontfamily + ",BoldItalic", mappedFontfamily + ",BoldItalic", mapping);
/* 275 */     addFontMapping(fontfamily + ",Bold", mappedFontfamily + ",Bold", mapping);
/* 276 */     addFontMapping(fontfamily + ",Italic", mappedFontfamily + ",Italic", mapping);
/* 277 */     addFontMapping(fontfamily, mappedFontfamily, mapping);
/*     */   }
/*     */ 
/*     */   public static String findTTFontname(String fontname)
/*     */   {
/* 288 */     if (!fontsLoaded)
/*     */     {
/* 290 */       loadFonts();
/*     */     }
/* 292 */     String fontfile = null;
/* 293 */     String normalizedFontname = normalizeFontname(fontname);
/* 294 */     if (ttfFontfiles.containsKey(normalizedFontname))
/*     */     {
/* 296 */       fontfile = (String)ttfFontfiles.get(normalizedFontname);
/*     */     }
/* 298 */     if (fontfile == null)
/*     */     {
/* 300 */       String mappedFontname = getMappedTTFName(fontname);
/* 301 */       if ((mappedFontname != null) && (ttfFontfiles.containsKey(mappedFontname)))
/*     */       {
/* 303 */         fontfile = (String)ttfFontfiles.get(mappedFontname);
/*     */       }
/*     */     }
/* 306 */     if (fontfile != null)
/*     */     {
/* 308 */       LOG.debug("Using ttf mapping " + fontname + " -=> " + fontfile);
/*     */     }
/*     */     else
/*     */     {
/* 312 */       LOG.warn("Font not found: " + fontname);
/*     */     }
/* 314 */     return fontfile;
/*     */   }
/*     */ 
/*     */   public static TrueTypeFont findTTFont(String fontname)
/*     */     throws IOException
/*     */   {
/* 326 */     String ttffontname = findTTFontname(fontname);
/* 327 */     TrueTypeFont ttfFont = null;
/* 328 */     if (ttffontname != null)
/*     */     {
/* 330 */       TTFParser ttfParser = new TTFParser();
/* 331 */       InputStream fontStream = ResourceLoader.loadResource(ttffontname);
/* 332 */       if (fontStream == null)
/*     */       {
/* 334 */         throw new IOException("Can't load external font: " + ttffontname);
/*     */       }
/* 336 */       ttfFont = ttfParser.parseTTF(fontStream);
/*     */     }
/* 338 */     return ttfFont;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.util.FontManager
 * JD-Core Version:    0.6.2
 */