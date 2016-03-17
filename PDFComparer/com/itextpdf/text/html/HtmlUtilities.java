/*     */ package com.itextpdf.text.html;
/*     */ 
/*     */ import com.itextpdf.text.BaseColor;
/*     */ import java.util.HashMap;
/*     */ import java.util.Properties;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ @Deprecated
/*     */ public class HtmlUtilities
/*     */ {
/*     */   public static final float DEFAULT_FONT_SIZE = 12.0F;
/*  67 */   private static HashMap<String, Float> sizes = new HashMap();
/*     */ 
/* 286 */   public static final int[] FONTSIZES = { 8, 10, 12, 14, 18, 24, 36 };
/*     */ 
/*     */   public static float parseLength(String string)
/*     */   {
/*  88 */     return parseLength(string, 12.0F);
/*     */   }
/*     */ 
/*     */   public static float parseLength(String string, float actualFontSize)
/*     */   {
/*  97 */     if (string == null)
/*  98 */       return 0.0F;
/*  99 */     Float fl = (Float)sizes.get(string.toLowerCase());
/* 100 */     if (fl != null)
/* 101 */       return fl.floatValue();
/* 102 */     int pos = 0;
/* 103 */     int length = string.length();
/* 104 */     boolean ok = true;
/* 105 */     while ((ok) && (pos < length)) {
/* 106 */       switch (string.charAt(pos)) {
/*     */       case '+':
/*     */       case '-':
/*     */       case '.':
/*     */       case '0':
/*     */       case '1':
/*     */       case '2':
/*     */       case '3':
/*     */       case '4':
/*     */       case '5':
/*     */       case '6':
/*     */       case '7':
/*     */       case '8':
/*     */       case '9':
/* 120 */         pos++;
/* 121 */         break;
/*     */       case ',':
/*     */       case '/':
/*     */       default:
/* 123 */         ok = false;
/*     */       }
/*     */     }
/* 126 */     if (pos == 0)
/* 127 */       return 0.0F;
/* 128 */     if (pos == length)
/* 129 */       return Float.parseFloat(string + "f");
/* 130 */     float f = Float.parseFloat(string.substring(0, pos) + "f");
/* 131 */     string = string.substring(pos);
/*     */ 
/* 133 */     if (string.startsWith("in")) {
/* 134 */       return f * 72.0F;
/*     */     }
/*     */ 
/* 137 */     if (string.startsWith("cm")) {
/* 138 */       return f / 2.54F * 72.0F;
/*     */     }
/*     */ 
/* 141 */     if (string.startsWith("mm")) {
/* 142 */       return f / 25.4F * 72.0F;
/*     */     }
/*     */ 
/* 145 */     if (string.startsWith("pc")) {
/* 146 */       return f * 12.0F;
/*     */     }
/*     */ 
/* 149 */     if (string.startsWith("em")) {
/* 150 */       return f * actualFontSize;
/*     */     }
/*     */ 
/* 154 */     if (string.startsWith("ex")) {
/* 155 */       return f * actualFontSize / 2.0F;
/*     */     }
/*     */ 
/* 158 */     return f;
/*     */   }
/*     */ 
/*     */   public static BaseColor decodeColor(String s)
/*     */   {
/* 171 */     if (s == null)
/* 172 */       return null;
/* 173 */     s = s.toLowerCase().trim();
/*     */     try {
/* 175 */       return WebColors.getRGBColor(s);
/*     */     } catch (IllegalArgumentException iae) {
/*     */     }
/* 178 */     return null;
/*     */   }
/*     */ 
/*     */   public static Properties parseAttributes(String string)
/*     */   {
/* 192 */     Properties result = new Properties();
/* 193 */     if (string == null)
/* 194 */       return result;
/* 195 */     StringTokenizer keyValuePairs = new StringTokenizer(string, ";");
/*     */ 
/* 199 */     while (keyValuePairs.hasMoreTokens()) {
/* 200 */       StringTokenizer keyValuePair = new StringTokenizer(keyValuePairs.nextToken(), ":");
/* 201 */       if (keyValuePair.hasMoreTokens()) {
/* 202 */         String key = keyValuePair.nextToken().trim();
/*     */ 
/* 205 */         if (keyValuePair.hasMoreTokens()) {
/* 206 */           String value = keyValuePair.nextToken().trim();
/*     */ 
/* 209 */           if (value.startsWith("\""))
/* 210 */             value = value.substring(1);
/* 211 */           if (value.endsWith("\""))
/* 212 */             value = value.substring(0, value.length() - 1);
/* 213 */           result.setProperty(key.toLowerCase(), value);
/*     */         }
/*     */       }
/*     */     }
/* 215 */     return result;
/*     */   }
/*     */ 
/*     */   public static String removeComment(String string, String startComment, String endComment)
/*     */   {
/* 231 */     StringBuffer result = new StringBuffer();
/* 232 */     int pos = 0;
/* 233 */     int end = endComment.length();
/* 234 */     int start = string.indexOf(startComment, pos);
/* 235 */     while (start > -1) {
/* 236 */       result.append(string.substring(pos, start));
/* 237 */       pos = string.indexOf(endComment, start) + end;
/* 238 */       start = string.indexOf(startComment, pos);
/*     */     }
/* 240 */     result.append(string.substring(pos));
/* 241 */     return result.toString();
/*     */   }
/*     */ 
/*     */   public static String eliminateWhiteSpace(String content)
/*     */   {
/* 253 */     StringBuffer buf = new StringBuffer();
/* 254 */     int len = content.length();
/*     */ 
/* 256 */     boolean newline = false;
/* 257 */     for (int i = 0; i < len; i++)
/*     */     {
/*     */       char character;
/* 258 */       switch (character = content.charAt(i)) {
/*     */       case ' ':
/* 260 */         if (!newline)
/* 261 */           buf.append(character); break;
/*     */       case '\n':
/* 265 */         if (i > 0) {
/* 266 */           newline = true;
/* 267 */           buf.append(' '); } break;
/*     */       case '\r':
/* 271 */         break;
/*     */       case '\t':
/* 273 */         break;
/*     */       default:
/* 275 */         newline = false;
/* 276 */         buf.append(character);
/*     */       }
/*     */     }
/* 279 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public static int getIndexedFontSize(String value, String previous)
/*     */   {
/* 296 */     int sIndex = 0;
/*     */ 
/* 298 */     if ((value.startsWith("+")) || (value.startsWith("-")))
/*     */     {
/* 300 */       if (previous == null)
/* 301 */         previous = "12";
/* 302 */       int c = (int)Float.parseFloat(previous);
/*     */ 
/* 304 */       for (int k = FONTSIZES.length - 1; k >= 0; k--) {
/* 305 */         if (c >= FONTSIZES[k]) {
/* 306 */           sIndex = k;
/* 307 */           break;
/*     */         }
/*     */       }
/*     */ 
/* 311 */       int diff = Integer.parseInt(value.startsWith("+") ? value.substring(1) : value);
/*     */ 
/* 315 */       sIndex += diff;
/*     */     }
/*     */     else
/*     */     {
/*     */       try {
/* 320 */         sIndex = Integer.parseInt(value) - 1;
/*     */       } catch (NumberFormatException nfe) {
/* 322 */         sIndex = 0;
/*     */       }
/*     */     }
/* 325 */     if (sIndex < 0)
/* 326 */       sIndex = 0;
/* 327 */     else if (sIndex >= FONTSIZES.length)
/* 328 */       sIndex = FONTSIZES.length - 1;
/* 329 */     return FONTSIZES[sIndex];
/*     */   }
/*     */ 
/*     */   public static int alignmentValue(String alignment)
/*     */   {
/* 339 */     if (alignment == null) return -1;
/* 340 */     if ("center".equalsIgnoreCase(alignment)) {
/* 341 */       return 1;
/*     */     }
/* 343 */     if ("left".equalsIgnoreCase(alignment)) {
/* 344 */       return 0;
/*     */     }
/* 346 */     if ("right".equalsIgnoreCase(alignment)) {
/* 347 */       return 2;
/*     */     }
/* 349 */     if ("justify".equalsIgnoreCase(alignment)) {
/* 350 */       return 3;
/*     */     }
/* 352 */     if ("JustifyAll".equalsIgnoreCase(alignment)) {
/* 353 */       return 8;
/*     */     }
/* 355 */     if ("top".equalsIgnoreCase(alignment)) {
/* 356 */       return 4;
/*     */     }
/* 358 */     if ("middle".equalsIgnoreCase(alignment)) {
/* 359 */       return 5;
/*     */     }
/* 361 */     if ("bottom".equalsIgnoreCase(alignment)) {
/* 362 */       return 6;
/*     */     }
/* 364 */     if ("baseline".equalsIgnoreCase(alignment)) {
/* 365 */       return 7;
/*     */     }
/* 367 */     return -1;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  69 */     sizes.put("xx-small", new Float(4.0F));
/*  70 */     sizes.put("x-small", new Float(6.0F));
/*  71 */     sizes.put("small", new Float(8.0F));
/*  72 */     sizes.put("medium", new Float(10.0F));
/*  73 */     sizes.put("large", new Float(13.0F));
/*  74 */     sizes.put("x-large", new Float(18.0F));
/*  75 */     sizes.put("xx-large", new Float(26.0F));
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.html.HtmlUtilities
 * JD-Core Version:    0.6.2
 */