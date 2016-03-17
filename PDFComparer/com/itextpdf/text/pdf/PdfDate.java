/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import java.util.Calendar;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.SimpleTimeZone;
/*     */ 
/*     */ public class PdfDate extends PdfString
/*     */ {
/*  69 */   private static final int[] DATE_SPACE = { 1, 4, 0, 2, 2, -1, 5, 2, 0, 11, 2, 0, 12, 2, 0, 13, 2, 0 };
/*     */ 
/*     */   public PdfDate(Calendar d)
/*     */   {
/*  82 */     StringBuffer date = new StringBuffer("D:");
/*  83 */     date.append(setLength(d.get(1), 4));
/*  84 */     date.append(setLength(d.get(2) + 1, 2));
/*  85 */     date.append(setLength(d.get(5), 2));
/*  86 */     date.append(setLength(d.get(11), 2));
/*  87 */     date.append(setLength(d.get(12), 2));
/*  88 */     date.append(setLength(d.get(13), 2));
/*  89 */     int timezone = (d.get(15) + d.get(16)) / 3600000;
/*  90 */     if (timezone == 0) {
/*  91 */       date.append('Z');
/*     */     }
/*  93 */     else if (timezone < 0) {
/*  94 */       date.append('-');
/*  95 */       timezone = -timezone;
/*     */     }
/*     */     else {
/*  98 */       date.append('+');
/*     */     }
/* 100 */     if (timezone != 0) {
/* 101 */       date.append(setLength(timezone, 2)).append('\'');
/* 102 */       int zone = Math.abs((d.get(15) + d.get(16)) / 60000) - timezone * 60;
/* 103 */       date.append(setLength(zone, 2)).append('\'');
/*     */     }
/* 105 */     this.value = date.toString();
/*     */   }
/*     */ 
/*     */   public PdfDate()
/*     */   {
/* 113 */     this(new GregorianCalendar());
/*     */   }
/*     */ 
/*     */   private String setLength(int i, int length)
/*     */   {
/* 126 */     StringBuffer tmp = new StringBuffer();
/* 127 */     tmp.append(i);
/* 128 */     while (tmp.length() < length) {
/* 129 */       tmp.insert(0, "0");
/*     */     }
/* 131 */     tmp.setLength(length);
/* 132 */     return tmp.toString();
/*     */   }
/*     */ 
/*     */   public String getW3CDate()
/*     */   {
/* 140 */     return getW3CDate(this.value);
/*     */   }
/*     */ 
/*     */   public static String getW3CDate(String d)
/*     */   {
/* 149 */     if (d.startsWith("D:"))
/* 150 */       d = d.substring(2);
/* 151 */     StringBuffer sb = new StringBuffer();
/* 152 */     if (d.length() < 4)
/* 153 */       return "0000";
/* 154 */     sb.append(d.substring(0, 4));
/* 155 */     d = d.substring(4);
/* 156 */     if (d.length() < 2)
/* 157 */       return sb.toString();
/* 158 */     sb.append('-').append(d.substring(0, 2));
/* 159 */     d = d.substring(2);
/* 160 */     if (d.length() < 2)
/* 161 */       return sb.toString();
/* 162 */     sb.append('-').append(d.substring(0, 2));
/* 163 */     d = d.substring(2);
/* 164 */     if (d.length() < 2)
/* 165 */       return sb.toString();
/* 166 */     sb.append('T').append(d.substring(0, 2));
/* 167 */     d = d.substring(2);
/* 168 */     if (d.length() < 2) {
/* 169 */       sb.append(":00Z");
/* 170 */       return sb.toString();
/*     */     }
/* 172 */     sb.append(':').append(d.substring(0, 2));
/* 173 */     d = d.substring(2);
/* 174 */     if (d.length() < 2) {
/* 175 */       sb.append('Z');
/* 176 */       return sb.toString();
/*     */     }
/* 178 */     sb.append(':').append(d.substring(0, 2));
/* 179 */     d = d.substring(2);
/* 180 */     if ((d.startsWith("-")) || (d.startsWith("+"))) {
/* 181 */       String sign = d.substring(0, 1);
/* 182 */       d = d.substring(1);
/* 183 */       String h = "00";
/* 184 */       String m = "00";
/* 185 */       if (d.length() >= 2) {
/* 186 */         h = d.substring(0, 2);
/* 187 */         if (d.length() > 2) {
/* 188 */           d = d.substring(3);
/* 189 */           if (d.length() >= 2)
/* 190 */             m = d.substring(0, 2);
/*     */         }
/* 192 */         sb.append(sign).append(h).append(':').append(m);
/* 193 */         return sb.toString();
/*     */       }
/*     */     }
/* 196 */     sb.append('Z');
/* 197 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static Calendar decode(String s)
/*     */   {
/*     */     try
/*     */     {
/* 208 */       if (s.startsWith("D:")) {
/* 209 */         s = s.substring(2);
/*     */       }
/* 211 */       int slen = s.length();
/* 212 */       int idx = s.indexOf('Z');
/*     */       GregorianCalendar calendar;
/*     */       GregorianCalendar calendar;
/* 213 */       if (idx >= 0) {
/* 214 */         slen = idx;
/* 215 */         calendar = new GregorianCalendar(new SimpleTimeZone(0, "ZPDF"));
/*     */       }
/*     */       else {
/* 218 */         int sign = 1;
/* 219 */         idx = s.indexOf('+');
/* 220 */         if (idx < 0) {
/* 221 */           idx = s.indexOf('-');
/* 222 */           if (idx >= 0)
/* 223 */             sign = -1;
/*     */         }
/*     */         GregorianCalendar calendar;
/* 225 */         if (idx < 0) {
/* 226 */           calendar = new GregorianCalendar();
/*     */         } else {
/* 228 */           int offset = Integer.parseInt(s.substring(idx + 1, idx + 3)) * 60;
/* 229 */           if (idx + 5 < s.length())
/* 230 */             offset += Integer.parseInt(s.substring(idx + 4, idx + 6));
/* 231 */           calendar = new GregorianCalendar(new SimpleTimeZone(offset * sign * 60000, "ZPDF"));
/* 232 */           slen = idx;
/*     */         }
/*     */       }
/* 235 */       calendar.clear();
/* 236 */       idx = 0;
/* 237 */       for (int k = 0; (k < DATE_SPACE.length) && 
/* 238 */         (idx < slen); k += 3)
/*     */       {
/* 240 */         calendar.set(DATE_SPACE[k], Integer.parseInt(s.substring(idx, idx + DATE_SPACE[(k + 1)])) + DATE_SPACE[(k + 2)]);
/* 241 */         idx += DATE_SPACE[(k + 1)];
/*     */       }
/* 243 */       return calendar;
/*     */     } catch (Exception e) {
/*     */     }
/* 246 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfDate
 * JD-Core Version:    0.6.2
 */