/*     */ package org.apache.pdfbox.cos;
/*     */ 
/*     */ import org.apache.pdfbox.exceptions.COSVisitorException;
/*     */ import org.apache.pdfbox.filter.FilterManager;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ 
/*     */ public abstract class COSBase
/*     */   implements COSObjectable
/*     */ {
/*     */   private boolean needToBeUpdate;
/*     */   private boolean direct;
/*     */ 
/*     */   public COSBase()
/*     */   {
/*  42 */     this.needToBeUpdate = false;
/*     */   }
/*     */ 
/*     */   public FilterManager getFilterManager()
/*     */   {
/*  55 */     return new FilterManager();
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  65 */     return this;
/*     */   }
/*     */ 
/*     */   public abstract Object accept(ICOSVisitor paramICOSVisitor)
/*     */     throws COSVisitorException;
/*     */ 
/*     */   public void setNeedToBeUpdate(boolean flag)
/*     */   {
/*  81 */     this.needToBeUpdate = flag;
/*     */   }
/*     */ 
/*     */   public boolean isDirect()
/*     */   {
/*  92 */     return this.direct;
/*     */   }
/*     */ 
/*     */   public void setDirect(boolean direct)
/*     */   {
/* 102 */     this.direct = direct;
/*     */   }
/*     */ 
/*     */   public boolean isNeedToBeUpdate()
/*     */   {
/* 107 */     return this.needToBeUpdate;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.cos.COSBase
 * JD-Core Version:    0.6.2
 */