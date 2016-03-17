/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import com.itextpdf.text.api.WriterOperation;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public abstract class WritableDirectElement
/*     */   implements Element, WriterOperation
/*     */ {
/*     */   public boolean process(ElementListener listener)
/*     */   {
/*  71 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   public int type()
/*     */   {
/*  78 */     return 666;
/*     */   }
/*     */ 
/*     */   public boolean isContent()
/*     */   {
/*  87 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isNestable()
/*     */   {
/*  96 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   public List<Chunk> getChunks()
/*     */   {
/* 105 */     return new ArrayList(0);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.WritableDirectElement
 * JD-Core Version:    0.6.2
 */