/*     */ package org.apache.pdfbox.pdmodel.interactive.action;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.pdmodel.interactive.action.type.PDAction;
/*     */ 
/*     */ public class PDDocumentCatalogAdditionalActions
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSDictionary actions;
/*     */ 
/*     */   public PDDocumentCatalogAdditionalActions()
/*     */   {
/*  42 */     this.actions = new COSDictionary();
/*     */   }
/*     */ 
/*     */   public PDDocumentCatalogAdditionalActions(COSDictionary a)
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
/*     */   public PDAction getWC()
/*     */   {
/*  84 */     COSDictionary wc = (COSDictionary)this.actions.getDictionaryObject("WC");
/*  85 */     PDAction retval = null;
/*  86 */     if (wc != null)
/*     */     {
/*  88 */       retval = PDActionFactory.createAction(wc);
/*     */     }
/*  90 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setWC(PDAction wc)
/*     */   {
/* 102 */     this.actions.setItem("WC", wc);
/*     */   }
/*     */ 
/*     */   public PDAction getWS()
/*     */   {
/* 114 */     COSDictionary ws = (COSDictionary)this.actions.getDictionaryObject("WS");
/* 115 */     PDAction retval = null;
/* 116 */     if (ws != null)
/*     */     {
/* 118 */       retval = PDActionFactory.createAction(ws);
/*     */     }
/* 120 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setWS(PDAction ws)
/*     */   {
/* 132 */     this.actions.setItem("WS", ws);
/*     */   }
/*     */ 
/*     */   public PDAction getDS()
/*     */   {
/* 144 */     COSDictionary ds = (COSDictionary)this.actions.getDictionaryObject("DS");
/* 145 */     PDAction retval = null;
/* 146 */     if (ds != null)
/*     */     {
/* 148 */       retval = PDActionFactory.createAction(ds);
/*     */     }
/* 150 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setDS(PDAction ds)
/*     */   {
/* 162 */     this.actions.setItem("DS", ds);
/*     */   }
/*     */ 
/*     */   public PDAction getWP()
/*     */   {
/* 174 */     COSDictionary wp = (COSDictionary)this.actions.getDictionaryObject("WP");
/* 175 */     PDAction retval = null;
/* 176 */     if (wp != null)
/*     */     {
/* 178 */       retval = PDActionFactory.createAction(wp);
/*     */     }
/* 180 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setWP(PDAction wp)
/*     */   {
/* 192 */     this.actions.setItem("WP", wp);
/*     */   }
/*     */ 
/*     */   public PDAction getDP()
/*     */   {
/* 204 */     COSDictionary dp = (COSDictionary)this.actions.getDictionaryObject("DP");
/* 205 */     PDAction retval = null;
/* 206 */     if (dp != null)
/*     */     {
/* 208 */       retval = PDActionFactory.createAction(dp);
/*     */     }
/* 210 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setDP(PDAction dp)
/*     */   {
/* 222 */     this.actions.setItem("DP", dp);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.action.PDDocumentCatalogAdditionalActions
 * JD-Core Version:    0.6.2
 */