/*     */ package org.apache.pdfbox.pdmodel.font;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.awt.FontFormatException;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.fontbox.afm.FontMetric;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSFloat;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.encoding.AFMEncoding;
/*     */ import org.apache.pdfbox.encoding.Encoding;
/*     */ import org.apache.pdfbox.encoding.EncodingManager;
/*     */ import org.apache.pdfbox.encoding.Type1Encoding;
/*     */ import org.apache.pdfbox.encoding.WinAnsiEncoding;
/*     */ import org.apache.pdfbox.pdmodel.common.PDMatrix;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ 
/*     */ public class PDType1Font extends PDSimpleFont
/*     */ {
/*  55 */   private static final Log log = LogFactory.getLog(PDType1Font.class);
/*     */ 
/*  57 */   private PDType1CFont type1CFont = null;
/*     */ 
/*  61 */   public static final PDType1Font TIMES_ROMAN = new PDType1Font("Times-Roman");
/*     */ 
/*  65 */   public static final PDType1Font TIMES_BOLD = new PDType1Font("Times-Bold");
/*     */ 
/*  69 */   public static final PDType1Font TIMES_ITALIC = new PDType1Font("Times-Italic");
/*     */ 
/*  73 */   public static final PDType1Font TIMES_BOLD_ITALIC = new PDType1Font("Times-BoldItalic");
/*     */ 
/*  77 */   public static final PDType1Font HELVETICA = new PDType1Font("Helvetica");
/*     */ 
/*  81 */   public static final PDType1Font HELVETICA_BOLD = new PDType1Font("Helvetica-Bold");
/*     */ 
/*  85 */   public static final PDType1Font HELVETICA_OBLIQUE = new PDType1Font("Helvetica-Oblique");
/*     */ 
/*  89 */   public static final PDType1Font HELVETICA_BOLD_OBLIQUE = new PDType1Font("Helvetica-BoldOblique");
/*     */ 
/*  93 */   public static final PDType1Font COURIER = new PDType1Font("Courier");
/*     */ 
/*  97 */   public static final PDType1Font COURIER_BOLD = new PDType1Font("Courier-Bold");
/*     */ 
/* 101 */   public static final PDType1Font COURIER_OBLIQUE = new PDType1Font("Courier-Oblique");
/*     */ 
/* 105 */   public static final PDType1Font COURIER_BOLD_OBLIQUE = new PDType1Font("Courier-BoldOblique");
/*     */ 
/* 109 */   public static final PDType1Font SYMBOL = new PDType1Font("Symbol");
/*     */ 
/* 113 */   public static final PDType1Font ZAPF_DINGBATS = new PDType1Font("ZapfDingbats");
/*     */ 
/* 115 */   private static final Map<String, PDType1Font> STANDARD_14 = new HashMap();
/*     */ 
/* 134 */   private Font awtFont = null;
/*     */ 
/*     */   public PDType1Font()
/*     */   {
/* 142 */     this.font.setItem(COSName.SUBTYPE, COSName.TYPE1);
/*     */   }
/*     */ 
/*     */   public PDType1Font(COSDictionary fontDictionary)
/*     */   {
/* 152 */     super(fontDictionary);
/* 153 */     PDFontDescriptor fd = getFontDescriptor();
/* 154 */     if ((fd != null) && ((fd instanceof PDFontDescriptorDictionary)))
/*     */     {
/* 157 */       PDStream fontFile3 = ((PDFontDescriptorDictionary)fd).getFontFile3();
/* 158 */       if (fontFile3 != null)
/*     */       {
/*     */         try
/*     */         {
/* 162 */           this.type1CFont = new PDType1CFont(this.font);
/*     */         }
/*     */         catch (IOException exception)
/*     */         {
/* 166 */           log.info("Can't read the embedded type1C font " + fd.getFontName());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public PDType1Font(String baseFont)
/*     */   {
/* 179 */     this();
/* 180 */     setBaseFont(baseFont);
/* 181 */     setFontEncoding(new WinAnsiEncoding());
/* 182 */     setEncoding(COSName.WIN_ANSI_ENCODING);
/*     */   }
/*     */ 
/*     */   public static PDType1Font getStandardFont(String name)
/*     */   {
/* 194 */     return (PDType1Font)STANDARD_14.get(name);
/*     */   }
/*     */ 
/*     */   public static String[] getStandard14Names()
/*     */   {
/* 204 */     return (String[])STANDARD_14.keySet().toArray(new String[14]);
/*     */   }
/*     */ 
/*     */   public Font getawtFont()
/*     */     throws IOException
/*     */   {
/* 212 */     if (this.awtFont == null)
/*     */     {
/* 214 */       if (this.type1CFont != null)
/*     */       {
/* 216 */         this.awtFont = this.type1CFont.getawtFont();
/*     */       }
/*     */       else
/*     */       {
/* 220 */         String baseFont = getBaseFont();
/* 221 */         PDFontDescriptor fd = getFontDescriptor();
/* 222 */         if ((fd != null) && ((fd instanceof PDFontDescriptorDictionary)))
/*     */         {
/* 224 */           PDFontDescriptorDictionary fdDictionary = (PDFontDescriptorDictionary)fd;
/* 225 */           if (fdDictionary.getFontFile() != null)
/*     */           {
/*     */             try
/*     */             {
/* 230 */               this.awtFont = Font.createFont(1, fdDictionary.getFontFile().createInputStream());
/*     */             }
/*     */             catch (FontFormatException e)
/*     */             {
/* 234 */               log.info("Can't read the embedded type1 font " + fd.getFontName());
/*     */             }
/*     */           }
/* 237 */           if (this.awtFont == null)
/*     */           {
/* 240 */             if (fd.getFontName() != null)
/*     */             {
/* 242 */               this.awtFont = FontManager.getAwtFont(fd.getFontName());
/*     */             }
/* 244 */             if (this.awtFont == null)
/*     */             {
/* 246 */               log.info("Can't find the specified font " + fd.getFontName());
/*     */             }
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 253 */           this.awtFont = FontManager.getAwtFont(baseFont);
/* 254 */           if (this.awtFont == null)
/*     */           {
/* 256 */             log.info("Can't find the specified basefont " + baseFont);
/*     */           }
/*     */         }
/*     */       }
/* 260 */       if (this.awtFont == null)
/*     */       {
/* 263 */         this.awtFont = FontManager.getStandardFont();
/* 264 */         log.info("Using font " + this.awtFont.getName() + " instead");
/*     */       }
/*     */     }
/* 267 */     return this.awtFont;
/*     */   }
/*     */ 
/*     */   protected void determineEncoding()
/*     */   {
/* 272 */     super.determineEncoding();
/* 273 */     Encoding fontEncoding = getFontEncoding();
/* 274 */     if (fontEncoding == null)
/*     */     {
/* 276 */       FontMetric metric = getAFM();
/* 277 */       if (metric != null)
/*     */       {
/* 279 */         fontEncoding = new AFMEncoding(metric);
/*     */       }
/* 281 */       setFontEncoding(fontEncoding);
/*     */     }
/* 283 */     getEncodingFromFont(getFontEncoding() == null);
/*     */   }
/*     */ 
/*     */   private void getEncodingFromFont(boolean extractEncoding)
/*     */   {
/* 294 */     PDFontDescriptor fontDescriptor = getFontDescriptor();
/* 295 */     if ((fontDescriptor != null) && ((fontDescriptor instanceof PDFontDescriptorDictionary)))
/*     */     {
/* 297 */       PDStream fontFile = ((PDFontDescriptorDictionary)fontDescriptor).getFontFile();
/* 298 */       if (fontFile != null)
/*     */       {
/* 300 */         BufferedReader in = null;
/*     */         try
/*     */         {
/* 303 */           in = new BufferedReader(new InputStreamReader(fontFile.createInputStream()));
/*     */ 
/* 308 */           String line = "";
/* 309 */           Type1Encoding encoding = null;
/* 310 */           while ((line = in.readLine()) != null)
/*     */           {
/* 312 */             if (extractEncoding)
/*     */             {
/* 314 */               if (line.startsWith("currentdict end")) {
/* 315 */                 if (encoding == null) break;
/* 316 */                 setFontEncoding(encoding); break;
/*     */               }
/*     */ 
/* 319 */               if (line.startsWith("/Encoding"))
/*     */               {
/* 321 */                 if (line.contains("array"))
/*     */                 {
/* 323 */                   StringTokenizer st = new StringTokenizer(line);
/*     */ 
/* 325 */                   st.nextElement();
/* 326 */                   int arraySize = Integer.parseInt(st.nextToken());
/* 327 */                   encoding = new Type1Encoding(arraySize);
/*     */                 }
/* 331 */                 else if (getFontEncoding() == null)
/*     */                 {
/* 333 */                   StringTokenizer st = new StringTokenizer(line);
/*     */ 
/* 335 */                   st.nextElement();
/* 336 */                   String type1Encoding = st.nextToken();
/* 337 */                   setFontEncoding(EncodingManager.INSTANCE.getEncoding(COSName.getPDFName(type1Encoding)));
/*     */ 
/* 340 */                   break;
/*     */                 }
/*     */               }
/* 343 */               else if (line.startsWith("dup")) {
/* 344 */                 StringTokenizer st = new StringTokenizer(line.replaceAll("/", " /"));
/*     */ 
/* 346 */                 st.nextElement();
/*     */                 try
/*     */                 {
/* 349 */                   int index = Integer.parseInt(st.nextToken());
/* 350 */                   String name = st.nextToken();
/* 351 */                   if (encoding == null)
/*     */                   {
/* 353 */                     log.warn("Unable to get character encoding. Encoding definition found without /Encoding line.");
/*     */                   }
/*     */                   else
/*     */                   {
/* 358 */                     encoding.addCharacterEncoding(index, name.replace("/", ""));
/*     */                   }
/*     */ 
/*     */                 }
/*     */                 catch (NumberFormatException exception)
/*     */                 {
/* 366 */                   log.debug("Malformed encoding definition ignored (line=" + line + ")");
/*     */                 }
/*     */ 
/*     */               }
/*     */ 
/*     */             }
/* 374 */             else if (line.startsWith("/FontMatrix"))
/*     */             {
/* 377 */               if (line.indexOf("[") > -1)
/*     */               {
/* 379 */                 String matrixValues = line.substring(line.indexOf("[") + 1, line.lastIndexOf("]"));
/* 380 */                 StringTokenizer st = new StringTokenizer(matrixValues);
/* 381 */                 COSArray array = new COSArray();
/* 382 */                 if (st.countTokens() >= 6)
/*     */                 {
/*     */                   try
/*     */                   {
/* 386 */                     for (int i = 0; i < 6; i++)
/*     */                     {
/* 388 */                       COSFloat floatValue = new COSFloat(Float.parseFloat(st.nextToken()));
/* 389 */                       array.add(floatValue);
/*     */                     }
/*     */                   }
/*     */                   catch (NumberFormatException exception)
/*     */                   {
/* 394 */                     log.error("Can't read the fontmatrix from embedded font file!");
/*     */                   }
/* 396 */                   this.fontMatrix = new PDMatrix(array);
/*     */                 }
/*     */ 
/*     */               }
/*     */               else
/*     */               {
/* 402 */                 COSArray array = new COSArray();
/* 403 */                 while ((line = in.readLine()) != null)
/*     */                 {
/* 405 */                   if (!line.startsWith("["))
/*     */                   {
/* 409 */                     if (line.endsWith("]"))
/*     */                     {
/*     */                       break;
/*     */                     }
/*     */                     try
/*     */                     {
/* 415 */                       COSFloat floatValue = new COSFloat(Float.parseFloat(line));
/* 416 */                       array.add(floatValue);
/*     */                     }
/*     */                     catch (NumberFormatException exception)
/*     */                     {
/* 420 */                       log.error("Can't read the fontmatrix from embedded font file!");
/*     */                     }
/*     */                   }
/*     */                 }
/* 423 */                 if (array.size() == 6)
/*     */                 {
/* 425 */                   this.fontMatrix = new PDMatrix(array);
/*     */                 }
/*     */                 else
/*     */                 {
/* 429 */                   log.error("Can't read the fontmatrix from embedded font file, not enough values!");
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */         catch (IOException exception)
/*     */         {
/* 437 */           log.error("Error: Could not extract the encoding from the embedded type1 font.");
/*     */         }
/*     */         finally
/*     */         {
/* 441 */           if (in != null)
/*     */           {
/*     */             try
/*     */             {
/* 445 */               in.close();
/*     */             }
/*     */             catch (IOException exception)
/*     */             {
/* 449 */               log.error("An error occurs while closing the stream used to read the embedded type1 font.");
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public String encode(byte[] c, int offset, int length)
/*     */     throws IOException
/*     */   {
/* 464 */     if ((this.type1CFont != null) && (getFontEncoding() == null))
/*     */     {
/* 466 */       String character = this.type1CFont.encode(c, offset, length);
/* 467 */       if (character != null)
/*     */       {
/* 469 */         return character;
/*     */       }
/*     */     }
/* 472 */     return super.encode(c, offset, length);
/*     */   }
/*     */ 
/*     */   public int encodeToCID(byte[] c, int offset, int length)
/*     */     throws IOException
/*     */   {
/* 481 */     if ((this.type1CFont != null) && (getFontEncoding() == null))
/*     */     {
/* 483 */       return this.type1CFont.encodeToCID(c, offset, length);
/*     */     }
/*     */ 
/* 487 */     return super.encodeToCID(c, offset, length);
/*     */   }
/*     */ 
/*     */   public PDMatrix getFontMatrix()
/*     */   {
/* 497 */     if (this.type1CFont != null)
/*     */     {
/* 499 */       return this.type1CFont.getFontMatrix();
/*     */     }
/*     */ 
/* 503 */     return super.getFontMatrix();
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 510 */     super.clear();
/* 511 */     if (this.type1CFont != null)
/*     */     {
/* 513 */       this.type1CFont.clear();
/* 514 */       this.type1CFont = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 118 */     STANDARD_14.put(TIMES_ROMAN.getBaseFont(), TIMES_ROMAN);
/* 119 */     STANDARD_14.put(TIMES_BOLD.getBaseFont(), TIMES_BOLD);
/* 120 */     STANDARD_14.put(TIMES_ITALIC.getBaseFont(), TIMES_ITALIC);
/* 121 */     STANDARD_14.put(TIMES_BOLD_ITALIC.getBaseFont(), TIMES_BOLD_ITALIC);
/* 122 */     STANDARD_14.put(HELVETICA.getBaseFont(), HELVETICA);
/* 123 */     STANDARD_14.put(HELVETICA_BOLD.getBaseFont(), HELVETICA_BOLD);
/* 124 */     STANDARD_14.put(HELVETICA_OBLIQUE.getBaseFont(), HELVETICA_OBLIQUE);
/* 125 */     STANDARD_14.put(HELVETICA_BOLD_OBLIQUE.getBaseFont(), HELVETICA_BOLD_OBLIQUE);
/* 126 */     STANDARD_14.put(COURIER.getBaseFont(), COURIER);
/* 127 */     STANDARD_14.put(COURIER_BOLD.getBaseFont(), COURIER_BOLD);
/* 128 */     STANDARD_14.put(COURIER_OBLIQUE.getBaseFont(), COURIER_OBLIQUE);
/* 129 */     STANDARD_14.put(COURIER_BOLD_OBLIQUE.getBaseFont(), COURIER_BOLD_OBLIQUE);
/* 130 */     STANDARD_14.put(SYMBOL.getBaseFont(), SYMBOL);
/* 131 */     STANDARD_14.put(ZAPF_DINGBATS.getBaseFont(), ZAPF_DINGBATS);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.font.PDType1Font
 * JD-Core Version:    0.6.2
 */