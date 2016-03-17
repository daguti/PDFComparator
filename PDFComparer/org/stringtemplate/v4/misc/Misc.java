/*     */ package org.stringtemplate.v4.misc;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public class Misc
/*     */ {
/*  36 */   public static final String newline = System.getProperty("line.separator");
/*     */ 
/*     */   public static String join(Iterator iter, String separator)
/*     */   {
/*  40 */     StringBuilder buf = new StringBuilder();
/*  41 */     while (iter.hasNext()) {
/*  42 */       buf.append(iter.next());
/*  43 */       if (iter.hasNext()) {
/*  44 */         buf.append(separator);
/*     */       }
/*     */     }
/*  47 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public static String strip(String s, int n)
/*     */   {
/*  60 */     return s.substring(n, s.length() - n);
/*     */   }
/*     */ 
/*     */   public static String trimOneStartingNewline(String s)
/*     */   {
/*  69 */     if (s.startsWith("\r\n")) s = s.substring(2);
/*  70 */     else if (s.startsWith("\n")) s = s.substring(1);
/*  71 */     return s;
/*     */   }
/*     */ 
/*     */   public static String trimOneTrailingNewline(String s)
/*     */   {
/*  76 */     if (s.endsWith("\r\n")) s = s.substring(0, s.length() - 2);
/*  77 */     else if (s.endsWith("\n")) s = s.substring(0, s.length() - 1);
/*  78 */     return s;
/*     */   }
/*     */ 
/*     */   public static String stripLastPathElement(String f)
/*     */   {
/*  85 */     int slash = f.lastIndexOf('/');
/*  86 */     if (slash < 0) return f;
/*  87 */     return f.substring(0, slash);
/*     */   }
/*     */ 
/*     */   public static String getFileNameNoSuffix(String f) {
/*  91 */     if (f == null) return null;
/*  92 */     f = getFileName(f);
/*  93 */     return f.substring(0, f.lastIndexOf('.'));
/*     */   }
/*     */ 
/*     */   public static String getFileName(String fullFileName) {
/*  97 */     if (fullFileName == null) return null;
/*  98 */     File f = new File(fullFileName);
/*  99 */     return f.getName();
/*     */   }
/*     */ 
/*     */   public static String getParent(String name)
/*     */   {
/* 104 */     if (name == null) return null;
/* 105 */     int lastSlash = name.lastIndexOf('/');
/* 106 */     if (lastSlash > 0) return name.substring(0, lastSlash);
/* 107 */     if (lastSlash == 0) return "/";
/*     */ 
/* 109 */     return "";
/*     */   }
/*     */ 
/*     */   public static String getPrefix(String name) {
/* 113 */     if (name == null) return "/";
/* 114 */     String parent = getParent(name);
/* 115 */     String prefix = parent;
/* 116 */     if (!parent.endsWith("/")) prefix = prefix + '/';
/* 117 */     return prefix;
/*     */   }
/*     */ 
/*     */   public static String replaceEscapes(String s) {
/* 121 */     s = s.replaceAll("\n", "\\\\n");
/* 122 */     s = s.replaceAll("\r", "\\\\r");
/* 123 */     s = s.replaceAll("\t", "\\\\t");
/* 124 */     return s;
/*     */   }
/*     */ 
/*     */   public static boolean urlExists(URL url) {
/*     */     try {
/* 129 */       URLConnection con = url.openConnection();
/* 130 */       InputStream is = con.getInputStream();
/* 131 */       is.close();
/* 132 */       return true;
/*     */     } catch (IOException ioe) {
/*     */     }
/* 135 */     return false;
/*     */   }
/*     */ 
/*     */   public static Coordinate getLineCharPosition(String s, int index)
/*     */   {
/* 141 */     int line = 1;
/* 142 */     int charPos = 0;
/* 143 */     int p = 0;
/* 144 */     while (p < index) {
/* 145 */       if (s.charAt(p) == '\n') { line++; charPos = 0; } else {
/* 146 */         charPos++;
/* 147 */       }p++;
/*     */     }
/*     */ 
/* 150 */     return new Coordinate(line, charPos);
/*     */   }
/*     */ 
/*     */   public static Object accessField(Field f, Object o, Object value) throws IllegalAccessException
/*     */   {
/*     */     try {
/* 156 */       f.setAccessible(true);
/*     */     }
/*     */     catch (SecurityException se)
/*     */     {
/*     */     }
/* 161 */     value = f.get(o);
/* 162 */     return value;
/*     */   }
/*     */ 
/*     */   public static Object invokeMethod(Method m, Object o, Object value) throws IllegalAccessException, InvocationTargetException
/*     */   {
/*     */     try {
/* 168 */       m.setAccessible(true);
/*     */     }
/*     */     catch (SecurityException se)
/*     */     {
/*     */     }
/* 173 */     value = m.invoke(o, (Object[])null);
/* 174 */     return value;
/*     */   }
/*     */ 
/*     */   public static Method getMethod(Class c, String methodName) {
/*     */     Method m;
/*     */     try {
/* 180 */       m = c.getMethod(methodName, (Class[])null);
/*     */     }
/*     */     catch (NoSuchMethodException nsme) {
/* 183 */       m = null;
/*     */     }
/* 185 */     return m;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.misc.Misc
 * JD-Core Version:    0.6.2
 */