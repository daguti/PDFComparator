/*     */ package com.itextpdf.text.html;
/*     */ 
/*     */ import com.itextpdf.text.BaseColor;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ @Deprecated
/*     */ public final class HtmlEncoder
/*     */ {
/*  94 */   private static final String[] HTML_CODE = new String[256];
/*     */   private static final Set<String> NEWLINETAGS;
/*     */ 
/*     */   public static String encode(String string)
/*     */   {
/* 131 */     int n = string.length();
/*     */ 
/* 133 */     StringBuffer buffer = new StringBuffer();
/*     */ 
/* 135 */     for (int i = 0; i < n; i++) {
/* 136 */       char character = string.charAt(i);
/*     */ 
/* 138 */       if (character < 'Ā') {
/* 139 */         buffer.append(HTML_CODE[character]);
/*     */       }
/*     */       else
/*     */       {
/* 143 */         buffer.append("&#").append(character).append(';');
/*     */       }
/*     */     }
/* 146 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */   public static String encode(BaseColor color)
/*     */   {
/* 156 */     StringBuffer buffer = new StringBuffer("#");
/* 157 */     if (color.getRed() < 16) {
/* 158 */       buffer.append('0');
/*     */     }
/* 160 */     buffer.append(Integer.toString(color.getRed(), 16));
/* 161 */     if (color.getGreen() < 16) {
/* 162 */       buffer.append('0');
/*     */     }
/* 164 */     buffer.append(Integer.toString(color.getGreen(), 16));
/* 165 */     if (color.getBlue() < 16) {
/* 166 */       buffer.append('0');
/*     */     }
/* 168 */     buffer.append(Integer.toString(color.getBlue(), 16));
/* 169 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */   public static String getAlignment(int alignment)
/*     */   {
/* 179 */     switch (alignment) {
/*     */     case 0:
/* 181 */       return "left";
/*     */     case 1:
/* 183 */       return "center";
/*     */     case 2:
/* 185 */       return "right";
/*     */     case 3:
/*     */     case 8:
/* 188 */       return "justify";
/*     */     case 4:
/* 190 */       return "top";
/*     */     case 5:
/* 192 */       return "middle";
/*     */     case 6:
/* 194 */       return "bottom";
/*     */     case 7:
/* 196 */       return "baseline";
/*     */     }
/* 198 */     return "";
/*     */   }
/*     */ 
/*     */   public static boolean isNewLineTag(String tag)
/*     */   {
/* 220 */     return NEWLINETAGS.contains(tag);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  97 */     for (int i = 0; i < 10; i++) {
/*  98 */       HTML_CODE[i] = ("&#00" + i + ";");
/*     */     }
/*     */ 
/* 101 */     for (int i = 10; i < 32; i++) {
/* 102 */       HTML_CODE[i] = ("&#0" + i + ";");
/*     */     }
/*     */ 
/* 105 */     for (int i = 32; i < 128; i++) {
/* 106 */       HTML_CODE[i] = String.valueOf((char)i);
/*     */     }
/*     */ 
/* 110 */     HTML_CODE[9] = "\t";
/* 111 */     HTML_CODE[10] = "<br />\n";
/* 112 */     HTML_CODE[34] = "&quot;";
/* 113 */     HTML_CODE[38] = "&amp;";
/* 114 */     HTML_CODE[60] = "&lt;";
/* 115 */     HTML_CODE[62] = "&gt;";
/*     */ 
/* 117 */     for (int i = 128; i < 256; i++) {
/* 118 */       HTML_CODE[i] = ("&#" + i + ";");
/*     */     }
/*     */ 
/* 206 */     NEWLINETAGS = new HashSet();
/*     */ 
/* 210 */     NEWLINETAGS.add("p");
/* 211 */     NEWLINETAGS.add("blockquote");
/* 212 */     NEWLINETAGS.add("br");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.html.HtmlEncoder
 * JD-Core Version:    0.6.2
 */