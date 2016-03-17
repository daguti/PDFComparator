/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ public class ExceptionConverter extends RuntimeException
/*     */ {
/*     */   private static final long serialVersionUID = 8657630363395849399L;
/*     */   private Exception ex;
/*     */   private String prefix;
/*     */ 
/*     */   public ExceptionConverter(Exception ex)
/*     */   {
/*  73 */     super(ex);
/*  74 */     this.ex = ex;
/*  75 */     this.prefix = ((ex instanceof RuntimeException) ? "" : "ExceptionConverter: ");
/*     */   }
/*     */ 
/*     */   public static final RuntimeException convertException(Exception ex)
/*     */   {
/*  87 */     if ((ex instanceof RuntimeException)) {
/*  88 */       return (RuntimeException)ex;
/*     */     }
/*  90 */     return new ExceptionConverter(ex);
/*     */   }
/*     */ 
/*     */   public Exception getException()
/*     */   {
/*  98 */     return this.ex;
/*     */   }
/*     */ 
/*     */   public String getMessage()
/*     */   {
/* 106 */     return this.ex.getMessage();
/*     */   }
/*     */ 
/*     */   public String getLocalizedMessage()
/*     */   {
/* 114 */     return this.ex.getLocalizedMessage();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 122 */     return this.prefix + this.ex;
/*     */   }
/*     */ 
/*     */   public void printStackTrace()
/*     */   {
/* 127 */     printStackTrace(System.err);
/*     */   }
/*     */ 
/*     */   public void printStackTrace(PrintStream s)
/*     */   {
/* 136 */     synchronized (s) {
/* 137 */       s.print(this.prefix);
/* 138 */       this.ex.printStackTrace(s);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void printStackTrace(PrintWriter s)
/*     */   {
/* 147 */     synchronized (s) {
/* 148 */       s.print(this.prefix);
/* 149 */       this.ex.printStackTrace(s);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Throwable fillInStackTrace()
/*     */   {
/* 160 */     return this;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.ExceptionConverter
 * JD-Core Version:    0.6.2
 */