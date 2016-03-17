/*     */ package org.apache.fontbox.cmap;
/*     */ 
/*     */ public class CodespaceRange
/*     */ {
/*     */   private byte[] start;
/*     */   private byte[] end;
/*     */ 
/*     */   public byte[] getEnd()
/*     */   {
/*  44 */     return this.end;
/*     */   }
/*     */ 
/*     */   public void setEnd(byte[] endBytes)
/*     */   {
/*  53 */     this.end = endBytes;
/*     */   }
/*     */ 
/*     */   public byte[] getStart()
/*     */   {
/*  62 */     return this.start;
/*     */   }
/*     */ 
/*     */   public void setStart(byte[] startBytes)
/*     */   {
/*  71 */     this.start = startBytes;
/*     */   }
/*     */ 
/*     */   public boolean isInRange(byte[] code, int offset, int length)
/*     */   {
/*  84 */     if ((length < this.start.length) || (length > this.end.length))
/*     */     {
/*  86 */       return false;
/*     */     }
/*     */ 
/*  89 */     if (this.end.length == length)
/*     */     {
/*  91 */       for (int i = 0; i < this.end.length; i++)
/*     */       {
/*  93 */         int endInt = this.end[i] & 0xFF;
/*  94 */         int codeInt = code[(offset + i)] & 0xFF;
/*  95 */         if (endInt < codeInt)
/*     */         {
/*  97 */           return false;
/*     */         }
/*     */       }
/*     */     }
/* 101 */     if (this.start.length == length)
/*     */     {
/* 103 */       for (int i = 0; i < this.end.length; i++)
/*     */       {
/* 105 */         int startInt = this.start[i] & 0xFF;
/* 106 */         int codeInt = code[(offset + i)] & 0xFF;
/* 107 */         if (startInt > codeInt)
/*     */         {
/* 109 */           return false;
/*     */         }
/*     */       }
/*     */     }
/* 113 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.cmap.CodespaceRange
 * JD-Core Version:    0.6.2
 */