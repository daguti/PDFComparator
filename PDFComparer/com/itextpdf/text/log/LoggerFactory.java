/*     */ package com.itextpdf.text.log;
/*     */ 
/*     */ public class LoggerFactory
/*     */ {
/*  57 */   private static LoggerFactory myself = new LoggerFactory();
/*     */ 
/*  85 */   private Logger logger = new NoOpLogger();
/*     */ 
/*     */   public static Logger getLogger(Class<?> klass)
/*     */   {
/*  67 */     return myself.logger.getLogger(klass);
/*     */   }
/*     */ 
/*     */   public static Logger getLogger(String name)
/*     */   {
/*  75 */     return myself.logger.getLogger(name);
/*     */   }
/*     */ 
/*     */   public static LoggerFactory getInstance()
/*     */   {
/*  82 */     return myself;
/*     */   }
/*     */ 
/*     */   public void setLogger(Logger logger)
/*     */   {
/*  96 */     this.logger = logger;
/*     */   }
/*     */ 
/*     */   public Logger logger()
/*     */   {
/* 105 */     return this.logger;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.log.LoggerFactory
 * JD-Core Version:    0.6.2
 */