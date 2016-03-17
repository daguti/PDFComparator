/*     */ package org.apache.pdfbox.pdmodel.interactive.action;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.pdmodel.interactive.action.type.PDAction;
/*     */ 
/*     */ public class PDPageAdditionalActions
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSDictionary actions;
/*     */ 
/*     */   public PDPageAdditionalActions()
/*     */   {
/*  42 */     this.actions = new COSDictionary();
/*     */   }
/*     */ 
/*     */   public PDPageAdditionalActions(COSDictionary a)
/*     */   {
/*  52 */     this.actions = a;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  62 */     return this.actions;
/*     */   }
/*     */ 
/*     */   public COSDictionary getCOSDictionary()
/*     */   {
/*  72 */     return this.actions;
/*     */   }
/*     */ 
/*     */   public PDAction getO()
/*     */   {
/*  85 */     COSDictionary o = (COSDictionary)this.actions.getDictionaryObject("O");
/*  86 */     PDAction retval = null;
/*  87 */     if (o != null)
/*     */     {
/*  89 */       retval = PDActionFactory.createAction(o);
/*     */     }
/*  91 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setO(PDAction o)
/*     */   {
/* 104 */     this.actions.setItem("O", o);
/*     */   }
/*     */ 
/*     */   public PDAction getC()
/*     */   {
/* 116 */     COSDictionary c = (COSDictionary)this.actions.getDictionaryObject("C");
/* 117 */     PDAction retval = null;
/* 118 */     if (c != null)
/*     */     {
/* 120 */       retval = PDActionFactory.createAction(c);
/*     */     }
/* 122 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setC(PDAction c)
/*     */   {
/* 134 */     this.actions.setItem("C", c);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.action.PDPageAdditionalActions
 * JD-Core Version:    0.6.2
 */