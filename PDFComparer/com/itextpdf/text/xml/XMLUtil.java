/*     */ package com.itextpdf.text.xml;
/*     */ 
/*     */ public class XMLUtil
/*     */ {
/*     */   public static String escapeXML(String s, boolean onlyASCII)
/*     */   {
/*  63 */     char[] cc = s.toCharArray();
/*  64 */     int len = cc.length;
/*  65 */     StringBuffer sb = new StringBuffer();
/*  66 */     for (int k = 0; k < len; k++) {
/*  67 */       int c = cc[k];
/*  68 */       switch (c) {
/*     */       case 60:
/*  70 */         sb.append("&lt;");
/*  71 */         break;
/*     */       case 62:
/*  73 */         sb.append("&gt;");
/*  74 */         break;
/*     */       case 38:
/*  76 */         sb.append("&amp;");
/*  77 */         break;
/*     */       case 34:
/*  79 */         sb.append("&quot;");
/*  80 */         break;
/*     */       case 39:
/*  82 */         sb.append("&apos;");
/*  83 */         break;
/*     */       default:
/*  85 */         if (isValidCharacterValue(c))
/*  86 */           if ((onlyASCII) && (c > 127))
/*  87 */             sb.append("&#").append(c).append(';');
/*     */           else
/*  89 */             sb.append((char)c);
/*     */         break;
/*     */       }
/*     */     }
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String unescapeXML(String s)
/*     */   {
/* 103 */     char[] cc = s.toCharArray();
/* 104 */     int len = cc.length;
/* 105 */     StringBuffer sb = new StringBuffer();
/*     */ 
/* 108 */     for (int i = 0; i < len; i++) {
/* 109 */       int c = cc[i];
/* 110 */       if (c == 38) {
/* 111 */         int pos = findInArray(';', cc, i + 3);
/* 112 */         if (pos > -1) {
/* 113 */           String esc = new String(cc, i + 1, pos - i - 1);
/* 114 */           if (esc.startsWith("#")) {
/* 115 */             esc = esc.substring(1);
/* 116 */             if (isValidCharacterValue(esc)) {
/* 117 */               c = (char)Integer.parseInt(esc);
/* 118 */               i = pos;
/*     */             } else {
/* 120 */               i = pos;
/* 121 */               continue;
/*     */             }
/*     */           }
/*     */           else {
/* 125 */             int tmp = unescape(esc);
/* 126 */             if (tmp > 0) {
/* 127 */               c = tmp;
/* 128 */               i = pos;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 133 */       sb.append((char)c);
/*     */     }
/* 135 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static int unescape(String s)
/*     */   {
/* 145 */     if ("apos".equals(s))
/* 146 */       return 39;
/* 147 */     if ("quot".equals(s))
/* 148 */       return 34;
/* 149 */     if ("lt".equals(s))
/* 150 */       return 60;
/* 151 */     if ("gt".equals(s))
/* 152 */       return 62;
/* 153 */     if ("amp".equals(s))
/* 154 */       return 38;
/* 155 */     return -1;
/*     */   }
/*     */ 
/*     */   public static boolean isValidCharacterValue(String s)
/*     */   {
/*     */     try
/*     */     {
/* 165 */       int i = Integer.parseInt(s);
/* 166 */       return isValidCharacterValue(i);
/*     */     } catch (NumberFormatException nfe) {
/*     */     }
/* 169 */     return false;
/*     */   }
/*     */ 
/*     */   public static boolean isValidCharacterValue(int c)
/*     */   {
/* 179 */     return (c == 9) || (c == 10) || (c == 13) || ((c >= 32) && (c <= 55295)) || ((c >= 57344) && (c <= 65533)) || ((c >= 65536) && (c <= 1114111));
/*     */   }
/*     */ 
/*     */   public static int findInArray(char needle, char[] haystack, int start)
/*     */   {
/* 193 */     for (int i = start; i < haystack.length; i++) {
/* 194 */       if (haystack[i] == ';')
/* 195 */         return i;
/*     */     }
/* 197 */     return -1;
/*     */   }
/*     */ 
/*     */   public static String getEncodingName(byte[] b4)
/*     */   {
/* 221 */     int b0 = b4[0] & 0xFF;
/* 222 */     int b1 = b4[1] & 0xFF;
/* 223 */     if ((b0 == 254) && (b1 == 255))
/*     */     {
/* 225 */       return "UTF-16BE";
/*     */     }
/* 227 */     if ((b0 == 255) && (b1 == 254))
/*     */     {
/* 229 */       return "UTF-16LE";
/*     */     }
/*     */ 
/* 233 */     int b2 = b4[2] & 0xFF;
/* 234 */     if ((b0 == 239) && (b1 == 187) && (b2 == 191)) {
/* 235 */       return "UTF-8";
/*     */     }
/*     */ 
/* 239 */     int b3 = b4[3] & 0xFF;
/* 240 */     if ((b0 == 0) && (b1 == 0) && (b2 == 0) && (b3 == 60))
/*     */     {
/* 242 */       return "ISO-10646-UCS-4";
/*     */     }
/* 244 */     if ((b0 == 60) && (b1 == 0) && (b2 == 0) && (b3 == 0))
/*     */     {
/* 246 */       return "ISO-10646-UCS-4";
/*     */     }
/* 248 */     if ((b0 == 0) && (b1 == 0) && (b2 == 60) && (b3 == 0))
/*     */     {
/* 251 */       return "ISO-10646-UCS-4";
/*     */     }
/* 253 */     if ((b0 == 0) && (b1 == 60) && (b2 == 0) && (b3 == 0))
/*     */     {
/* 256 */       return "ISO-10646-UCS-4";
/*     */     }
/* 258 */     if ((b0 == 0) && (b1 == 60) && (b2 == 0) && (b3 == 63))
/*     */     {
/* 262 */       return "UTF-16BE";
/*     */     }
/* 264 */     if ((b0 == 60) && (b1 == 0) && (b2 == 63) && (b3 == 0))
/*     */     {
/* 267 */       return "UTF-16LE";
/*     */     }
/* 269 */     if ((b0 == 76) && (b1 == 111) && (b2 == 167) && (b3 == 148))
/*     */     {
/* 272 */       return "CP037";
/*     */     }
/*     */ 
/* 276 */     return "UTF-8";
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.xml.XMLUtil
 * JD-Core Version:    0.6.2
 */