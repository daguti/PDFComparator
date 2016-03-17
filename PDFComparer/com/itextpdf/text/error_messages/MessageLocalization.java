/*     */ package com.itextpdf.text.error_messages;
/*     */ 
/*     */ import com.itextpdf.text.io.StreamUtil;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public final class MessageLocalization
/*     */ {
/*  64 */   private static HashMap<String, String> defaultLanguage = new HashMap();
/*     */   private static HashMap<String, String> currentLanguage;
/*     */   private static final String BASE_PATH = "com/itextpdf/text/l10n/error/";
/*     */ 
/*     */   public static String getMessage(String key)
/*     */   {
/*  87 */     return getMessage(key, true);
/*     */   }
/*     */ 
/*     */   public static String getMessage(String key, boolean useDefaultLanguageIfMessageNotFound) {
/*  91 */     HashMap cl = currentLanguage;
/*     */ 
/*  93 */     if (cl != null) {
/*  94 */       String val = (String)cl.get(key);
/*  95 */       if (val != null) {
/*  96 */         return val;
/*     */       }
/*     */     }
/*  99 */     if (useDefaultLanguageIfMessageNotFound) {
/* 100 */       cl = defaultLanguage;
/* 101 */       String val = (String)cl.get(key);
/* 102 */       if (val != null) {
/* 103 */         return val;
/*     */       }
/*     */     }
/* 106 */     return "No message found for " + key;
/*     */   }
/*     */ 
/*     */   public static String getComposedMessage(String key, int p1)
/*     */   {
/* 117 */     return getComposedMessage(key, new Object[] { String.valueOf(p1), null, null, null });
/*     */   }
/*     */ 
/*     */   public static String getComposedMessage(String key, Object[] param)
/*     */   {
/* 132 */     String msg = getMessage(key);
/* 133 */     if (null != param) {
/* 134 */       int i = 1;
/* 135 */       for (Object o : param) {
/* 136 */         if (null != o) {
/* 137 */           msg = msg.replace("{" + i + "}", o.toString());
/*     */         }
/* 139 */         i++;
/*     */       }
/*     */     }
/* 142 */     return msg;
/*     */   }
/*     */ 
/*     */   public static boolean setLanguage(String language, String country)
/*     */     throws IOException
/*     */   {
/* 155 */     HashMap lang = getLanguageMessages(language, country);
/* 156 */     if (lang == null)
/* 157 */       return false;
/* 158 */     currentLanguage = lang;
/* 159 */     return true;
/*     */   }
/*     */ 
/*     */   public static void setMessages(Reader r)
/*     */     throws IOException
/*     */   {
/* 168 */     currentLanguage = readLanguageStream(r);
/*     */   }
/*     */ 
/*     */   private static HashMap<String, String> getLanguageMessages(String language, String country) throws IOException {
/* 172 */     if (language == null)
/* 173 */       throw new IllegalArgumentException("The language cannot be null.");
/* 174 */     InputStream is = null;
/*     */     try
/*     */     {
/*     */       String file;
/* 177 */       if (country != null)
/* 178 */         file = language + "_" + country + ".lng";
/*     */       else
/* 180 */         file = language + ".lng";
/* 181 */       is = StreamUtil.getResourceStream("com/itextpdf/text/l10n/error/" + file, new MessageLocalization().getClass().getClassLoader());
/*     */       HashMap localHashMap;
/* 182 */       if (is != null)
/* 183 */         return readLanguageStream(is);
/* 184 */       if (country == null)
/* 185 */         return null;
/* 186 */       String file = language + ".lng";
/* 187 */       is = StreamUtil.getResourceStream("com/itextpdf/text/l10n/error/" + file, new MessageLocalization().getClass().getClassLoader());
/* 188 */       if (is != null) {
/* 189 */         return readLanguageStream(is);
/*     */       }
/* 191 */       return null;
/*     */     }
/*     */     finally {
/*     */       try {
/* 195 */         if (null != is)
/* 196 */           is.close();
/*     */       }
/*     */       catch (Exception exx)
/*     */       {
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static HashMap<String, String> readLanguageStream(InputStream is) throws IOException {
/* 205 */     return readLanguageStream(new InputStreamReader(is, "UTF-8"));
/*     */   }
/*     */ 
/*     */   private static HashMap<String, String> readLanguageStream(Reader r) throws IOException {
/* 209 */     HashMap lang = new HashMap();
/* 210 */     BufferedReader br = new BufferedReader(r);
/*     */     String line;
/* 212 */     while ((line = br.readLine()) != null) {
/* 213 */       int idxeq = line.indexOf('=');
/* 214 */       if (idxeq >= 0)
/*     */       {
/* 216 */         String key = line.substring(0, idxeq).trim();
/* 217 */         if (!key.startsWith("#"))
/*     */         {
/* 219 */           lang.put(key, line.substring(idxeq + 1));
/*     */         }
/*     */       }
/*     */     }
/* 221 */     return lang;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*     */     try
/*     */     {
/*  73 */       defaultLanguage = getLanguageMessages("en", null);
/*     */     }
/*     */     catch (Exception ex) {
/*     */     }
/*  77 */     if (defaultLanguage == null)
/*  78 */       defaultLanguage = new HashMap();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.error_messages.MessageLocalization
 * JD-Core Version:    0.6.2
 */