/*     */ package org.apache.pdfbox.pdmodel.interactive.pagenavigation;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.pdmodel.PDDocumentInformation;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ 
/*     */ public class PDThread
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSDictionary thread;
/*     */ 
/*     */   public PDThread(COSDictionary t)
/*     */   {
/*  44 */     this.thread = t;
/*     */   }
/*     */ 
/*     */   public PDThread()
/*     */   {
/*  53 */     this.thread = new COSDictionary();
/*  54 */     this.thread.setName("Type", "Thread");
/*     */   }
/*     */ 
/*     */   public COSDictionary getDictionary()
/*     */   {
/*  64 */     return this.thread;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  74 */     return this.thread;
/*     */   }
/*     */ 
/*     */   public PDDocumentInformation getThreadInfo()
/*     */   {
/*  84 */     PDDocumentInformation retval = null;
/*  85 */     COSDictionary info = (COSDictionary)this.thread.getDictionaryObject("I");
/*  86 */     if (info != null)
/*     */     {
/*  88 */       retval = new PDDocumentInformation(info);
/*     */     }
/*     */ 
/*  91 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setThreadInfo(PDDocumentInformation info)
/*     */   {
/* 101 */     this.thread.setItem("I", info);
/*     */   }
/*     */ 
/*     */   public PDThreadBead getFirstBead()
/*     */   {
/* 112 */     PDThreadBead retval = null;
/* 113 */     COSDictionary bead = (COSDictionary)this.thread.getDictionaryObject("F");
/* 114 */     if (bead != null)
/*     */     {
/* 116 */       retval = new PDThreadBead(bead);
/*     */     }
/*     */ 
/* 119 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setFirstBead(PDThreadBead bead)
/*     */   {
/* 130 */     if (bead != null)
/*     */     {
/* 132 */       bead.setThread(this);
/*     */     }
/* 134 */     this.thread.setItem("F", bead);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.pagenavigation.PDThread
 * JD-Core Version:    0.6.2
 */