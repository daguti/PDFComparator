/*     */ package org.apache.pdfbox.pdmodel.font;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.fontbox.cmap.CMap;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ import org.apache.pdfbox.io.IOUtils;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*     */ import org.apache.pdfbox.util.ResourceLoader;
/*     */ 
/*     */ public abstract class PDCIDFont extends PDSimpleFont
/*     */ {
/*  46 */   private static final Log log = LogFactory.getLog(PDCIDFont.class);
/*     */ 
/*  48 */   private Map<Integer, Float> widthCache = null;
/*  49 */   private long defaultWidth = 0L;
/*     */ 
/*     */   public PDCIDFont()
/*     */   {
/*     */   }
/*     */ 
/*     */   public PDCIDFont(COSDictionary fontDictionary)
/*     */   {
/*  66 */     super(fontDictionary);
/*  67 */     extractWidths();
/*     */   }
/*     */ 
/*     */   public PDRectangle getFontBoundingBox()
/*     */     throws IOException
/*     */   {
/*  79 */     throw new RuntimeException("getFontBoundingBox(): Not yet implemented");
/*     */   }
/*     */ 
/*     */   public long getDefaultWidth()
/*     */   {
/*  89 */     if (this.defaultWidth == 0L)
/*     */     {
/*  91 */       COSNumber number = (COSNumber)this.font.getDictionaryObject(COSName.DW);
/*  92 */       if (number != null)
/*     */       {
/*  94 */         this.defaultWidth = number.intValue();
/*     */       }
/*     */       else
/*     */       {
/*  98 */         this.defaultWidth = 1000L;
/*     */       }
/*     */     }
/* 101 */     return this.defaultWidth;
/*     */   }
/*     */ 
/*     */   public void setDefaultWidth(long dw)
/*     */   {
/* 111 */     this.defaultWidth = dw;
/* 112 */     this.font.setLong(COSName.DW, dw);
/*     */   }
/*     */ 
/*     */   public float getFontWidth(byte[] c, int offset, int length)
/*     */     throws IOException
/*     */   {
/* 129 */     float retval = (float)getDefaultWidth();
/* 130 */     int code = getCodeFromArray(c, offset, length);
/*     */ 
/* 132 */     Float widthFloat = (Float)this.widthCache.get(Integer.valueOf(code));
/* 133 */     if (widthFloat != null)
/*     */     {
/* 135 */       retval = widthFloat.floatValue();
/*     */     }
/* 137 */     return retval;
/*     */   }
/*     */ 
/*     */   private void extractWidths()
/*     */   {
/* 142 */     if (this.widthCache == null)
/*     */     {
/* 144 */       this.widthCache = new HashMap();
/* 145 */       COSArray widths = (COSArray)this.font.getDictionaryObject(COSName.W);
/* 146 */       if (widths != null)
/*     */       {
/* 148 */         int size = widths.size();
/* 149 */         int counter = 0;
/* 150 */         while (counter < size)
/*     */         {
/* 152 */           COSNumber firstCode = (COSNumber)widths.getObject(counter++);
/* 153 */           COSBase next = widths.getObject(counter++);
/* 154 */           if ((next instanceof COSArray))
/*     */           {
/* 156 */             COSArray array = (COSArray)next;
/* 157 */             int startRange = firstCode.intValue();
/* 158 */             int arraySize = array.size();
/* 159 */             for (int i = 0; i < arraySize; i++)
/*     */             {
/* 161 */               COSNumber width = (COSNumber)array.get(i);
/* 162 */               this.widthCache.put(Integer.valueOf(startRange + i), Float.valueOf(width.floatValue()));
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 167 */             COSNumber secondCode = (COSNumber)next;
/* 168 */             COSNumber rangeWidth = (COSNumber)widths.getObject(counter++);
/* 169 */             int startRange = firstCode.intValue();
/* 170 */             int endRange = secondCode.intValue();
/* 171 */             float width = rangeWidth.floatValue();
/* 172 */             for (int i = startRange; i <= endRange; i++)
/* 173 */               this.widthCache.put(Integer.valueOf(i), Float.valueOf(width));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public float getFontHeight(byte[] c, int offset, int length)
/*     */     throws IOException
/*     */   {
/* 193 */     float retval = 0.0F;
/* 194 */     PDFontDescriptor desc = getFontDescriptor();
/* 195 */     float xHeight = desc.getXHeight();
/* 196 */     float capHeight = desc.getCapHeight();
/* 197 */     if ((xHeight != 0.0F) && (capHeight != 0.0F))
/*     */     {
/* 200 */       retval = (xHeight + capHeight) / 2.0F;
/*     */     }
/* 202 */     else if (xHeight != 0.0F)
/*     */     {
/* 204 */       retval = xHeight;
/*     */     }
/* 206 */     else if (capHeight != 0.0F)
/*     */     {
/* 208 */       retval = capHeight;
/*     */     }
/*     */     else
/*     */     {
/* 212 */       retval = 0.0F;
/*     */     }
/* 214 */     if (retval == 0.0F)
/*     */     {
/* 216 */       retval = desc.getAscent();
/*     */     }
/* 218 */     return retval;
/*     */   }
/*     */ 
/*     */   public float getAverageFontWidth()
/*     */     throws IOException
/*     */   {
/* 230 */     float totalWidths = 0.0F;
/* 231 */     float characterCount = 0.0F;
/* 232 */     float defaultWidth = (float)getDefaultWidth();
/* 233 */     COSArray widths = (COSArray)this.font.getDictionaryObject(COSName.W);
/*     */ 
/* 235 */     if (widths != null)
/*     */     {
/* 237 */       for (int i = 0; i < widths.size(); i++)
/*     */       {
/* 239 */         COSNumber firstCode = (COSNumber)widths.getObject(i++);
/* 240 */         COSBase next = widths.getObject(i);
/* 241 */         if ((next instanceof COSArray))
/*     */         {
/* 243 */           COSArray array = (COSArray)next;
/* 244 */           for (int j = 0; j < array.size(); j++)
/*     */           {
/* 246 */             COSNumber width = (COSNumber)array.get(j);
/* 247 */             totalWidths += width.floatValue();
/* 248 */             characterCount += 1.0F;
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 253 */           i++;
/* 254 */           COSNumber rangeWidth = (COSNumber)widths.getObject(i);
/* 255 */           if (rangeWidth.floatValue() > 0.0F)
/*     */           {
/* 257 */             totalWidths += rangeWidth.floatValue();
/* 258 */             characterCount += 1.0F;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 263 */     float average = totalWidths / characterCount;
/* 264 */     if (average <= 0.0F)
/*     */     {
/* 266 */       average = defaultWidth;
/*     */     }
/* 268 */     return average;
/*     */   }
/*     */ 
/*     */   public float getFontWidth(int charCode)
/*     */   {
/* 276 */     float width = (float)getDefaultWidth();
/* 277 */     if (this.widthCache.containsKey(Integer.valueOf(charCode)))
/*     */     {
/* 279 */       width = ((Float)this.widthCache.get(Integer.valueOf(charCode))).floatValue();
/*     */     }
/* 281 */     return width;
/*     */   }
/*     */ 
/*     */   private String getCIDSystemInfo()
/*     */   {
/* 290 */     String cidSystemInfo = null;
/* 291 */     COSDictionary cidsysteminfo = (COSDictionary)this.font.getDictionaryObject(COSName.CIDSYSTEMINFO);
/* 292 */     if (cidsysteminfo != null)
/*     */     {
/* 294 */       String ordering = cidsysteminfo.getString(COSName.ORDERING);
/* 295 */       String registry = cidsysteminfo.getString(COSName.REGISTRY);
/* 296 */       int supplement = cidsysteminfo.getInt(COSName.SUPPLEMENT);
/* 297 */       cidSystemInfo = registry + "-" + ordering + "-" + supplement;
/*     */     }
/* 299 */     return cidSystemInfo;
/*     */   }
/*     */ 
/*     */   protected void determineEncoding()
/*     */   {
/* 305 */     String cidSystemInfo = getCIDSystemInfo();
/* 306 */     if (cidSystemInfo != null)
/*     */     {
/* 308 */       if (cidSystemInfo.contains("Identity"))
/*     */       {
/* 310 */         cidSystemInfo = "Identity-H";
/*     */       }
/* 312 */       else if (cidSystemInfo.startsWith("Adobe-UCS-"))
/*     */       {
/* 314 */         cidSystemInfo = "Adobe-Identity-UCS";
/*     */       }
/*     */       else
/*     */       {
/* 318 */         cidSystemInfo = cidSystemInfo.substring(0, cidSystemInfo.lastIndexOf("-")) + "-UCS2";
/*     */       }
/* 320 */       this.cmap = ((CMap)cmapObjects.get(cidSystemInfo));
/* 321 */       if (this.cmap == null)
/*     */       {
/* 323 */         InputStream cmapStream = null;
/*     */         try
/*     */         {
/* 327 */           cmapStream = ResourceLoader.loadResource("org/apache/pdfbox/resources/cmap/" + cidSystemInfo);
/* 328 */           if (cmapStream != null)
/*     */           {
/* 330 */             this.cmap = parseCmap("org/apache/pdfbox/resources/cmap/", cmapStream);
/* 331 */             if (this.cmap == null)
/*     */             {
/* 333 */               log.error("Error: Could not parse predefined CMAP file for '" + cidSystemInfo + "'");
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 338 */             log.debug("Debug: '" + cidSystemInfo + "' isn't a predefined CMap, most likely it's embedded in the pdf itself.");
/*     */           }
/*     */ 
/*     */         }
/*     */         catch (IOException exception)
/*     */         {
/* 344 */           log.error("Error: Could not find predefined CMAP file for '" + cidSystemInfo + "'");
/*     */         }
/*     */         finally
/*     */         {
/* 348 */           IOUtils.closeQuietly(cmapStream);
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 354 */       super.determineEncoding();
/*     */     }
/*     */   }
/*     */ 
/*     */   public String encode(byte[] c, int offset, int length)
/*     */     throws IOException
/*     */   {
/* 361 */     String result = null;
/* 362 */     if (this.cmap != null)
/*     */     {
/* 364 */       result = cmapEncoding(getCodeFromArray(c, offset, length), length, true, this.cmap);
/*     */     }
/*     */     else
/*     */     {
/* 368 */       result = super.encode(c, offset, length);
/*     */     }
/* 370 */     return result;
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 376 */     super.clear();
/* 377 */     if (this.widthCache != null)
/*     */     {
/* 379 */       this.widthCache.clear();
/* 380 */       this.widthCache = null;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.font.PDCIDFont
 * JD-Core Version:    0.6.2
 */