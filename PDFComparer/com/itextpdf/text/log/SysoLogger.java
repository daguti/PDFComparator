/*     */ package com.itextpdf.text.log;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ public class SysoLogger
/*     */   implements Logger
/*     */ {
/*     */   private String name;
/*     */   private final int shorten;
/*     */ 
/*     */   public SysoLogger()
/*     */   {
/*  60 */     this(1);
/*     */   }
/*     */ 
/*     */   public SysoLogger(int packageReduce)
/*     */   {
/*  68 */     this.shorten = packageReduce;
/*     */   }
/*     */ 
/*     */   protected SysoLogger(String klass, int shorten)
/*     */   {
/*  76 */     this.shorten = shorten;
/*  77 */     this.name = klass;
/*     */   }
/*     */ 
/*     */   public Logger getLogger(Class<?> klass) {
/*  81 */     return new SysoLogger(klass.getName(), this.shorten);
/*     */   }
/*     */ 
/*     */   public Logger getLogger(String name)
/*     */   {
/*  88 */     return new SysoLogger("[itext]", 0);
/*     */   }
/*     */ 
/*     */   public boolean isLogging(Level level) {
/*  92 */     return true;
/*     */   }
/*     */ 
/*     */   public void warn(String message) {
/*  96 */     System.out.println(String.format("%s WARN  %s", new Object[] { shorten(this.name), message }));
/*     */   }
/*     */ 
/*     */   private String shorten(String className)
/*     */   {
/* 104 */     if (this.shorten != 0) {
/* 105 */       StringBuilder target = new StringBuilder();
/* 106 */       String name = className;
/* 107 */       int fromIndex = className.indexOf('.');
/* 108 */       while (fromIndex != -1) {
/* 109 */         int parseTo = fromIndex < this.shorten ? fromIndex : this.shorten;
/* 110 */         target.append(name.substring(0, parseTo));
/* 111 */         target.append('.');
/* 112 */         name = name.substring(fromIndex + 1);
/* 113 */         fromIndex = name.indexOf('.');
/*     */       }
/* 115 */       target.append(className.substring(className.lastIndexOf('.') + 1));
/* 116 */       return target.toString();
/*     */     }
/* 118 */     return className;
/*     */   }
/*     */ 
/*     */   public void trace(String message) {
/* 122 */     System.out.println(String.format("%s TRACE %s", new Object[] { shorten(this.name), message }));
/*     */   }
/*     */ 
/*     */   public void debug(String message) {
/* 126 */     System.out.println(String.format("%s DEBUG %s", new Object[] { shorten(this.name), message }));
/*     */   }
/*     */ 
/*     */   public void info(String message) {
/* 130 */     System.out.println(String.format("%s INFO  %s", new Object[] { shorten(this.name), message }));
/*     */   }
/*     */ 
/*     */   public void error(String message) {
/* 134 */     System.out.println(String.format("%s ERROR %s", new Object[] { this.name, message }));
/*     */   }
/*     */ 
/*     */   public void error(String message, Exception e) {
/* 138 */     System.out.println(String.format("%s ERROR %s", new Object[] { this.name, message }));
/* 139 */     e.printStackTrace(System.out);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.log.SysoLogger
 * JD-Core Version:    0.6.2
 */