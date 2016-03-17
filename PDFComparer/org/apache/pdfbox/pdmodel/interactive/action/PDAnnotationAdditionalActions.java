/*     */ package org.apache.pdfbox.pdmodel.interactive.action;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.pdmodel.interactive.action.type.PDAction;
/*     */ 
/*     */ public class PDAnnotationAdditionalActions
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSDictionary actions;
/*     */ 
/*     */   public PDAnnotationAdditionalActions()
/*     */   {
/*  42 */     this.actions = new COSDictionary();
/*     */   }
/*     */ 
/*     */   public PDAnnotationAdditionalActions(COSDictionary a)
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
/*     */   public PDAction getE()
/*     */   {
/*  83 */     COSDictionary e = (COSDictionary)this.actions.getDictionaryObject("E");
/*  84 */     PDAction retval = null;
/*  85 */     if (e != null)
/*     */     {
/*  87 */       retval = PDActionFactory.createAction(e);
/*     */     }
/*  89 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setE(PDAction e)
/*     */   {
/* 100 */     this.actions.setItem("E", e);
/*     */   }
/*     */ 
/*     */   public PDAction getX()
/*     */   {
/* 111 */     COSDictionary x = (COSDictionary)this.actions.getDictionaryObject("X");
/* 112 */     PDAction retval = null;
/* 113 */     if (x != null)
/*     */     {
/* 115 */       retval = PDActionFactory.createAction(x);
/*     */     }
/* 117 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setX(PDAction x)
/*     */   {
/* 128 */     this.actions.setItem("X", x);
/*     */   }
/*     */ 
/*     */   public PDAction getD()
/*     */   {
/* 140 */     COSDictionary d = (COSDictionary)this.actions.getDictionaryObject("D");
/* 141 */     PDAction retval = null;
/* 142 */     if (d != null)
/*     */     {
/* 144 */       retval = PDActionFactory.createAction(d);
/*     */     }
/* 146 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setD(PDAction d)
/*     */   {
/* 158 */     this.actions.setItem("D", d);
/*     */   }
/*     */ 
/*     */   public PDAction getU()
/*     */   {
/* 170 */     COSDictionary u = (COSDictionary)this.actions.getDictionaryObject("U");
/* 171 */     PDAction retval = null;
/* 172 */     if (u != null)
/*     */     {
/* 174 */       retval = PDActionFactory.createAction(u);
/*     */     }
/* 176 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setU(PDAction u)
/*     */   {
/* 188 */     this.actions.setItem("U", u);
/*     */   }
/*     */ 
/*     */   public PDAction getFo()
/*     */   {
/* 199 */     COSDictionary fo = (COSDictionary)this.actions.getDictionaryObject("Fo");
/* 200 */     PDAction retval = null;
/* 201 */     if (fo != null)
/*     */     {
/* 203 */       retval = PDActionFactory.createAction(fo);
/*     */     }
/* 205 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setFo(PDAction fo)
/*     */   {
/* 216 */     this.actions.setItem("Fo", fo);
/*     */   }
/*     */ 
/*     */   public PDAction getBl()
/*     */   {
/* 228 */     COSDictionary bl = (COSDictionary)this.actions.getDictionaryObject("Bl");
/* 229 */     PDAction retval = null;
/* 230 */     if (bl != null)
/*     */     {
/* 232 */       retval = PDActionFactory.createAction(bl);
/*     */     }
/* 234 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setBl(PDAction bl)
/*     */   {
/* 246 */     this.actions.setItem("Bl", bl);
/*     */   }
/*     */ 
/*     */   public PDAction getPO()
/*     */   {
/* 259 */     COSDictionary po = (COSDictionary)this.actions.getDictionaryObject("PO");
/* 260 */     PDAction retval = null;
/* 261 */     if (po != null)
/*     */     {
/* 263 */       retval = PDActionFactory.createAction(po);
/*     */     }
/* 265 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setPO(PDAction po)
/*     */   {
/* 278 */     this.actions.setItem("PO", po);
/*     */   }
/*     */ 
/*     */   public PDAction getPC()
/*     */   {
/* 290 */     COSDictionary pc = (COSDictionary)this.actions.getDictionaryObject("PC");
/* 291 */     PDAction retval = null;
/* 292 */     if (pc != null)
/*     */     {
/* 294 */       retval = PDActionFactory.createAction(pc);
/*     */     }
/* 296 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setPC(PDAction pc)
/*     */   {
/* 308 */     this.actions.setItem("PC", pc);
/*     */   }
/*     */ 
/*     */   public PDAction getPV()
/*     */   {
/* 319 */     COSDictionary pv = (COSDictionary)this.actions.getDictionaryObject("PV");
/* 320 */     PDAction retval = null;
/* 321 */     if (pv != null)
/*     */     {
/* 323 */       retval = PDActionFactory.createAction(pv);
/*     */     }
/* 325 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setPV(PDAction pv)
/*     */   {
/* 336 */     this.actions.setItem("PV", pv);
/*     */   }
/*     */ 
/*     */   public PDAction getPI()
/*     */   {
/* 347 */     COSDictionary pi = (COSDictionary)this.actions.getDictionaryObject("PI");
/* 348 */     PDAction retval = null;
/* 349 */     if (pi != null)
/*     */     {
/* 351 */       retval = PDActionFactory.createAction(pi);
/*     */     }
/* 353 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setPI(PDAction pi)
/*     */   {
/* 364 */     this.actions.setItem("PI", pi);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.action.PDAnnotationAdditionalActions
 * JD-Core Version:    0.6.2
 */