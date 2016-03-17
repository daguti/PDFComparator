/*     */ package org.apache.pdfbox.pdmodel.interactive.action;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.pdmodel.interactive.action.type.PDAction;
/*     */ 
/*     */ public class PDFormFieldAdditionalActions
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSDictionary actions;
/*     */ 
/*     */   public PDFormFieldAdditionalActions()
/*     */   {
/*  42 */     this.actions = new COSDictionary();
/*     */   }
/*     */ 
/*     */   public PDFormFieldAdditionalActions(COSDictionary a)
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
/*     */   public PDAction getK()
/*     */   {
/*  85 */     COSDictionary k = (COSDictionary)this.actions.getDictionaryObject("K");
/*  86 */     PDAction retval = null;
/*  87 */     if (k != null)
/*     */     {
/*  89 */       retval = PDActionFactory.createAction(k);
/*     */     }
/*  91 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setK(PDAction k)
/*     */   {
/* 104 */     this.actions.setItem("K", k);
/*     */   }
/*     */ 
/*     */   public PDAction getF()
/*     */   {
/* 116 */     COSDictionary f = (COSDictionary)this.actions.getDictionaryObject("F");
/* 117 */     PDAction retval = null;
/* 118 */     if (f != null)
/*     */     {
/* 120 */       retval = PDActionFactory.createAction(f);
/*     */     }
/* 122 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setF(PDAction f)
/*     */   {
/* 134 */     this.actions.setItem("F", f);
/*     */   }
/*     */ 
/*     */   public PDAction getV()
/*     */   {
/* 147 */     COSDictionary v = (COSDictionary)this.actions.getDictionaryObject("V");
/* 148 */     PDAction retval = null;
/* 149 */     if (v != null)
/*     */     {
/* 151 */       retval = PDActionFactory.createAction(v);
/*     */     }
/* 153 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setV(PDAction v)
/*     */   {
/* 166 */     this.actions.setItem("V", v);
/*     */   }
/*     */ 
/*     */   public PDAction getC()
/*     */   {
/* 180 */     COSDictionary c = (COSDictionary)this.actions.getDictionaryObject("C");
/* 181 */     PDAction retval = null;
/* 182 */     if (c != null)
/*     */     {
/* 184 */       retval = PDActionFactory.createAction(c);
/*     */     }
/* 186 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setC(PDAction c)
/*     */   {
/* 200 */     this.actions.setItem("C", c);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.action.PDFormFieldAdditionalActions
 * JD-Core Version:    0.6.2
 */