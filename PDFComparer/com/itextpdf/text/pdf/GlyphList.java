/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.io.StreamUtil;
/*     */ import com.itextpdf.text.pdf.fonts.FontsResourceAnchor;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public class GlyphList
/*     */ {
/*  56 */   private static HashMap<Integer, String> unicode2names = new HashMap();
/*  57 */   private static HashMap<String, int[]> names2unicode = new HashMap();
/*     */ 
/*     */   public static int[] nameToUnicode(String name)
/*     */   {
/* 113 */     int[] v = (int[])names2unicode.get(name);
/* 114 */     if ((v == null) && (name.length() == 7) && (name.toLowerCase().startsWith("uni")))
/*     */       try {
/* 116 */         return new int[] { Integer.parseInt(name.substring(3), 16) };
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/*     */       }
/* 121 */     return v;
/*     */   }
/*     */ 
/*     */   public static String unicodeToName(int num) {
/* 125 */     return (String)unicode2names.get(Integer.valueOf(num));
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  60 */     InputStream is = null;
/*     */     try {
/*  62 */       is = StreamUtil.getResourceStream("com/itextpdf/text/pdf/fonts/glyphlist.txt", new FontsResourceAnchor().getClass().getClassLoader());
/*  63 */       if (is == null) {
/*  64 */         String msg = "glyphlist.txt not found as resource. (It must exist as resource in the package com.itextpdf.text.pdf.fonts)";
/*  65 */         throw new Exception(msg);
/*     */       }
/*  67 */       byte[] buf = new byte[1024];
/*  68 */       ByteArrayOutputStream out = new ByteArrayOutputStream();
/*     */       while (true) {
/*  70 */         int size = is.read(buf);
/*  71 */         if (size < 0)
/*     */           break;
/*  73 */         out.write(buf, 0, size);
/*     */       }
/*  75 */       is.close();
/*  76 */       is = null;
/*  77 */       String s = PdfEncodings.convertToString(out.toByteArray(), null);
/*  78 */       StringTokenizer tk = new StringTokenizer(s, "\r\n");
/*  79 */       while (tk.hasMoreTokens()) {
/*  80 */         String line = tk.nextToken();
/*  81 */         if (!line.startsWith("#"))
/*     */         {
/*  83 */           StringTokenizer t2 = new StringTokenizer(line, " ;\r\n\t\f");
/*  84 */           String name = null;
/*  85 */           String hex = null;
/*  86 */           if (t2.hasMoreTokens())
/*     */           {
/*  88 */             name = t2.nextToken();
/*  89 */             if (t2.hasMoreTokens())
/*     */             {
/*  91 */               hex = t2.nextToken();
/*  92 */               Integer num = Integer.valueOf(hex, 16);
/*  93 */               unicode2names.put(num, name);
/*  94 */               names2unicode.put(name, new int[] { num.intValue() });
/*     */             }
/*     */           }
/*     */         }
/*     */       } } catch (Exception e) { System.err.println("glyphlist.txt loading error: " + e.getMessage());
/*     */     } finally
/*     */     {
/* 101 */       if (is != null)
/*     */         try {
/* 103 */           is.close();
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*     */         }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.GlyphList
 * JD-Core Version:    0.6.2
 */