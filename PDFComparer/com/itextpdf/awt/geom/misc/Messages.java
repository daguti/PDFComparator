/*     */ package com.itextpdf.awt.geom.misc;
/*     */ 
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.ResourceBundle;
/*     */ 
/*     */ public class Messages
/*     */ {
/*  51 */   private static ResourceBundle bundle = null;
/*     */ 
/*     */   public static String getString(String msg)
/*     */   {
/*  61 */     if (bundle == null)
/*  62 */       return msg;
/*     */     try {
/*  64 */       return bundle.getString(msg); } catch (MissingResourceException e) {
/*     */     }
/*  66 */     return "Missing message: " + msg;
/*     */   }
/*     */ 
/*     */   public static String getString(String msg, Object arg)
/*     */   {
/*  80 */     return getString(msg, new Object[] { arg });
/*     */   }
/*     */ 
/*     */   public static String getString(String msg, int arg)
/*     */   {
/*  93 */     return getString(msg, new Object[] { Integer.toString(arg) });
/*     */   }
/*     */ 
/*     */   public static String getString(String msg, char arg)
/*     */   {
/* 106 */     return getString(msg, new Object[] { String.valueOf(arg) });
/*     */   }
/*     */ 
/*     */   public static String getString(String msg, Object arg1, Object arg2)
/*     */   {
/* 121 */     return getString(msg, new Object[] { arg1, arg2 });
/*     */   }
/*     */ 
/*     */   public static String getString(String msg, Object[] args)
/*     */   {
/* 134 */     String format = msg;
/*     */ 
/* 136 */     if (bundle != null)
/*     */       try {
/* 138 */         format = bundle.getString(msg);
/*     */       }
/*     */       catch (MissingResourceException e)
/*     */       {
/*     */       }
/* 143 */     return format(format, args);
/*     */   }
/*     */ 
/*     */   public static String format(String format, Object[] args)
/*     */   {
/* 163 */     StringBuilder answer = new StringBuilder(format.length() + args.length * 20);
/*     */ 
/* 165 */     String[] argStrings = new String[args.length];
/* 166 */     for (int i = 0; i < args.length; i++) {
/* 167 */       if (args[i] == null)
/* 168 */         argStrings[i] = "<null>";
/*     */       else
/* 170 */         argStrings[i] = args[i].toString();
/*     */     }
/* 172 */     int lastI = 0;
/* 173 */     for (int i = format.indexOf('{', 0); i >= 0; i = format.indexOf('{', lastI))
/*     */     {
/* 175 */       if ((i != 0) && (format.charAt(i - 1) == '\\'))
/*     */       {
/* 177 */         if (i != 1)
/* 178 */           answer.append(format.substring(lastI, i - 1));
/* 179 */         answer.append('{');
/* 180 */         lastI = i + 1;
/*     */       }
/* 183 */       else if (i > format.length() - 3)
/*     */       {
/* 185 */         answer.append(format.substring(lastI, format.length()));
/* 186 */         lastI = format.length();
/*     */       } else {
/* 188 */         int argnum = (byte)Character.digit(format.charAt(i + 1), 10);
/*     */ 
/* 190 */         if ((argnum < 0) || (format.charAt(i + 2) != '}'))
/*     */         {
/* 192 */           answer.append(format.substring(lastI, i + 1));
/* 193 */           lastI = i + 1;
/*     */         }
/*     */         else {
/* 196 */           answer.append(format.substring(lastI, i));
/* 197 */           if (argnum >= argStrings.length)
/* 198 */             answer.append("<missing argument>");
/*     */           else
/* 200 */             answer.append(argStrings[argnum]);
/* 201 */           lastI = i + 3;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 206 */     if (lastI < format.length())
/* 207 */       answer.append(format.substring(lastI, format.length()));
/* 208 */     return answer.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.awt.geom.misc.Messages
 * JD-Core Version:    0.6.2
 */