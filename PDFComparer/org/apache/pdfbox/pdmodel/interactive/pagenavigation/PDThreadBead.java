/*     */ package org.apache.pdfbox.pdmodel.interactive.pagenavigation;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.PDPage;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*     */ 
/*     */ public class PDThreadBead
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSDictionary bead;
/*     */ 
/*     */   public PDThreadBead(COSDictionary b)
/*     */   {
/*  47 */     this.bead = b;
/*     */   }
/*     */ 
/*     */   public PDThreadBead()
/*     */   {
/*  56 */     this.bead = new COSDictionary();
/*  57 */     this.bead.setName("Type", "Bead");
/*  58 */     setNextBead(this);
/*  59 */     setPreviousBead(this);
/*     */   }
/*     */ 
/*     */   public COSDictionary getDictionary()
/*     */   {
/*  69 */     return this.bead;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  79 */     return this.bead;
/*     */   }
/*     */ 
/*     */   public PDThread getThread()
/*     */   {
/*  90 */     PDThread retval = null;
/*  91 */     COSDictionary dic = (COSDictionary)this.bead.getDictionaryObject("T");
/*  92 */     if (dic != null)
/*     */     {
/*  94 */       retval = new PDThread(dic);
/*     */     }
/*  96 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setThread(PDThread thread)
/*     */   {
/* 107 */     this.bead.setItem("T", thread);
/*     */   }
/*     */ 
/*     */   public PDThreadBead getNextBead()
/*     */   {
/* 118 */     return new PDThreadBead((COSDictionary)this.bead.getDictionaryObject("N"));
/*     */   }
/*     */ 
/*     */   protected void setNextBead(PDThreadBead next)
/*     */   {
/* 128 */     this.bead.setItem("N", next);
/*     */   }
/*     */ 
/*     */   public PDThreadBead getPreviousBead()
/*     */   {
/* 139 */     return new PDThreadBead((COSDictionary)this.bead.getDictionaryObject("V"));
/*     */   }
/*     */ 
/*     */   protected void setPreviousBead(PDThreadBead previous)
/*     */   {
/* 149 */     this.bead.setItem("V", previous);
/*     */   }
/*     */ 
/*     */   public void appendBead(PDThreadBead append)
/*     */   {
/* 160 */     PDThreadBead nextBead = getNextBead();
/* 161 */     nextBead.setPreviousBead(append);
/* 162 */     append.setNextBead(nextBead);
/* 163 */     setNextBead(append);
/* 164 */     append.setPreviousBead(this);
/*     */   }
/*     */ 
/*     */   public PDPage getPage()
/*     */   {
/* 174 */     PDPage page = null;
/* 175 */     COSDictionary dic = (COSDictionary)this.bead.getDictionaryObject("P");
/* 176 */     if (dic != null)
/*     */     {
/* 178 */       page = new PDPage(dic);
/*     */     }
/* 180 */     return page;
/*     */   }
/*     */ 
/*     */   public void setPage(PDPage page)
/*     */   {
/* 192 */     this.bead.setItem("P", page);
/*     */   }
/*     */ 
/*     */   public PDRectangle getRectangle()
/*     */   {
/* 202 */     PDRectangle rect = null;
/* 203 */     COSArray array = (COSArray)this.bead.getDictionaryObject(COSName.R);
/* 204 */     if (array != null)
/*     */     {
/* 206 */       rect = new PDRectangle(array);
/*     */     }
/* 208 */     return rect;
/*     */   }
/*     */ 
/*     */   public void setRectangle(PDRectangle rect)
/*     */   {
/* 218 */     this.bead.setItem(COSName.R, rect);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.pagenavigation.PDThreadBead
 * JD-Core Version:    0.6.2
 */