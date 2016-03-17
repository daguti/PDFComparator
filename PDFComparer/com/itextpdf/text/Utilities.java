/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import com.itextpdf.text.pdf.ByteBuffer;
/*     */ import com.itextpdf.text.pdf.PRTokeniser;
/*     */ import com.itextpdf.text.pdf.PdfEncodings;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.util.Collections;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class Utilities
/*     */ {
/*     */   @Deprecated
/*     */   public static <K, V> Set<K> getKeySet(Hashtable<K, V> table)
/*     */   {
/*  80 */     return table == null ? Collections.emptySet() : table.keySet();
/*     */   }
/*     */ 
/*     */   public static Object[][] addToArray(Object[][] original, Object[] item)
/*     */   {
/*  93 */     if (original == null) {
/*  94 */       original = new Object[1][];
/*  95 */       original[0] = item;
/*  96 */       return original;
/*     */     }
/*  98 */     Object[][] original2 = new Object[original.length + 1][];
/*  99 */     System.arraycopy(original, 0, original2, 0, original.length);
/* 100 */     original2[original.length] = item;
/* 101 */     return original2;
/*     */   }
/*     */ 
/*     */   public static boolean checkTrueOrFalse(Properties attributes, String key)
/*     */   {
/* 112 */     return "true".equalsIgnoreCase(attributes.getProperty(key));
/*     */   }
/*     */ 
/*     */   public static String unEscapeURL(String src)
/*     */   {
/* 121 */     StringBuffer bf = new StringBuffer();
/* 122 */     char[] s = src.toCharArray();
/* 123 */     for (int k = 0; k < s.length; k++) {
/* 124 */       char c = s[k];
/* 125 */       if (c == '%') {
/* 126 */         if (k + 2 >= s.length) {
/* 127 */           bf.append(c);
/*     */         }
/*     */         else {
/* 130 */           int a0 = PRTokeniser.getHex(s[(k + 1)]);
/* 131 */           int a1 = PRTokeniser.getHex(s[(k + 2)]);
/* 132 */           if ((a0 < 0) || (a1 < 0)) {
/* 133 */             bf.append(c);
/*     */           }
/*     */           else {
/* 136 */             bf.append((char)(a0 * 16 + a1));
/* 137 */             k += 2;
/*     */           }
/*     */         }
/*     */       } else bf.append(c);
/*     */     }
/* 142 */     return bf.toString();
/*     */   }
/*     */ 
/*     */   public static URL toURL(String filename)
/*     */     throws MalformedURLException
/*     */   {
/*     */     try
/*     */     {
/* 158 */       return new URL(filename);
/*     */     } catch (Exception e) {
/*     */     }
/* 161 */     return new File(filename).toURI().toURL();
/*     */   }
/*     */ 
/*     */   public static void skip(InputStream is, int size)
/*     */     throws IOException
/*     */   {
/* 178 */     while (size > 0) {
/* 179 */       long n = is.skip(size);
/* 180 */       if (n <= 0L)
/*     */         break;
/* 182 */       size = (int)(size - n);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static final float millimetersToPoints(float value)
/*     */   {
/* 193 */     return inchesToPoints(millimetersToInches(value));
/*     */   }
/*     */ 
/*     */   public static final float millimetersToInches(float value)
/*     */   {
/* 203 */     return value / 25.4F;
/*     */   }
/*     */ 
/*     */   public static final float pointsToMillimeters(float value)
/*     */   {
/* 213 */     return inchesToMillimeters(pointsToInches(value));
/*     */   }
/*     */ 
/*     */   public static final float pointsToInches(float value)
/*     */   {
/* 223 */     return value / 72.0F;
/*     */   }
/*     */ 
/*     */   public static final float inchesToMillimeters(float value)
/*     */   {
/* 233 */     return value * 25.4F;
/*     */   }
/*     */ 
/*     */   public static final float inchesToPoints(float value)
/*     */   {
/* 243 */     return value * 72.0F;
/*     */   }
/*     */ 
/*     */   public static boolean isSurrogateHigh(char c)
/*     */   {
/* 254 */     return (c >= 55296) && (c <= 56319);
/*     */   }
/*     */ 
/*     */   public static boolean isSurrogateLow(char c)
/*     */   {
/* 265 */     return (c >= 56320) && (c <= 57343);
/*     */   }
/*     */ 
/*     */   public static boolean isSurrogatePair(String text, int idx)
/*     */   {
/* 278 */     if ((idx < 0) || (idx > text.length() - 2))
/* 279 */       return false;
/* 280 */     return (isSurrogateHigh(text.charAt(idx))) && (isSurrogateLow(text.charAt(idx + 1)));
/*     */   }
/*     */ 
/*     */   public static boolean isSurrogatePair(char[] text, int idx)
/*     */   {
/* 293 */     if ((idx < 0) || (idx > text.length - 2))
/* 294 */       return false;
/* 295 */     return (isSurrogateHigh(text[idx])) && (isSurrogateLow(text[(idx + 1)]));
/*     */   }
/*     */ 
/*     */   public static int convertToUtf32(char highSurrogate, char lowSurrogate)
/*     */   {
/* 307 */     return (highSurrogate - 55296) * 1024 + lowSurrogate - 56320 + 65536;
/*     */   }
/*     */ 
/*     */   public static int convertToUtf32(char[] text, int idx)
/*     */   {
/* 318 */     return (text[idx] - 55296) * 1024 + text[(idx + 1)] - 56320 + 65536;
/*     */   }
/*     */ 
/*     */   public static int convertToUtf32(String text, int idx)
/*     */   {
/* 329 */     return (text.charAt(idx) - 55296) * 1024 + text.charAt(idx + 1) - 56320 + 65536;
/*     */   }
/*     */ 
/*     */   public static String convertFromUtf32(int codePoint)
/*     */   {
/* 339 */     if (codePoint < 65536)
/* 340 */       return Character.toString((char)codePoint);
/* 341 */     codePoint -= 65536;
/* 342 */     return new String(new char[] { (char)(codePoint / 1024 + 55296), (char)(codePoint % 1024 + 56320) });
/*     */   }
/*     */ 
/*     */   public static String readFileToString(String path)
/*     */     throws IOException
/*     */   {
/* 353 */     return readFileToString(new File(path));
/*     */   }
/*     */ 
/*     */   public static String readFileToString(File file)
/*     */     throws IOException
/*     */   {
/* 364 */     byte[] jsBytes = new byte[(int)file.length()];
/* 365 */     FileInputStream f = new FileInputStream(file);
/* 366 */     f.read(jsBytes);
/* 367 */     return new String(jsBytes);
/*     */   }
/*     */ 
/*     */   public static String convertToHex(byte[] bytes)
/*     */   {
/* 376 */     ByteBuffer buf = new ByteBuffer();
/* 377 */     for (byte b : bytes) {
/* 378 */       buf.appendHex(b);
/*     */     }
/* 380 */     return PdfEncodings.convertToString(buf.toByteArray(), null).toUpperCase();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.Utilities
 * JD-Core Version:    0.6.2
 */