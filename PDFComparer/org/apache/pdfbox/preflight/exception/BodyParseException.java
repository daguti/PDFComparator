/*     */ package org.apache.pdfbox.preflight.exception;
/*     */ 
/*     */ import org.apache.pdfbox.preflight.javacc.ParseException;
/*     */ 
/*     */ public class BodyParseException extends PdfParseException
/*     */ {
/*     */   public BodyParseException(ParseException e)
/*     */   {
/*  44 */     super(e);
/*     */   }
/*     */ 
/*     */   public BodyParseException(String message, String code)
/*     */   {
/*  54 */     super(message, code);
/*     */   }
/*     */ 
/*     */   public BodyParseException(String message)
/*     */   {
/*  64 */     super(message);
/*     */   }
/*     */ 
/*     */   public String getErrorCode()
/*     */   {
/*  75 */     if (this.errorCode != null)
/*     */     {
/*  77 */       return this.errorCode;
/*     */     }
/*     */ 
/*  81 */     this.errorCode = "1.2";
/*     */ 
/*  83 */     if (!this.isTokenMgrError)
/*     */     {
/*  87 */       if (this.expectedTokenSequences != null)
/*     */       {
/*  90 */         for (int i = 0; i < this.expectedTokenSequences.length; i++)
/*     */         {
/*  95 */           switch (this.expectedTokenSequences[i][0])
/*     */           {
/*     */           case 9:
/*     */           case 20:
/*  99 */             this.errorCode = "1.2.1";
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 107 */     return this.errorCode;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.exception.BodyParseException
 * JD-Core Version:    0.6.2
 */