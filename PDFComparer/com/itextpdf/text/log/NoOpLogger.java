/*     */ package com.itextpdf.text.log;
/*     */ 
/*     */ public final class NoOpLogger
/*     */   implements Logger
/*     */ {
/*     */   public Logger getLogger(Class<?> name)
/*     */   {
/*  58 */     return this;
/*     */   }
/*     */ 
/*     */   public void warn(String message)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void trace(String message)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void debug(String message)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void info(String message)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void error(String message, Exception e)
/*     */   {
/*     */   }
/*     */ 
/*     */   public boolean isLogging(Level level)
/*     */   {
/*  95 */     return false;
/*     */   }
/*     */ 
/*     */   public void error(String message)
/*     */   {
/*     */   }
/*     */ 
/*     */   public Logger getLogger(String name)
/*     */   {
/* 108 */     return this;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.log.NoOpLogger
 * JD-Core Version:    0.6.2
 */