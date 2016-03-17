/*     */ package org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ 
/*     */ public class PDPageFitWidthDestination extends PDPageDestination
/*     */ {
/*     */   protected static final String TYPE = "FitH";
/*     */   protected static final String TYPE_BOUNDED = "FitBH";
/*     */ 
/*     */   public PDPageFitWidthDestination()
/*     */   {
/*  48 */     this.array.growToSize(3);
/*  49 */     this.array.setName(1, "FitH");
/*     */   }
/*     */ 
/*     */   public PDPageFitWidthDestination(COSArray arr)
/*     */   {
/*  60 */     super(arr);
/*     */   }
/*     */ 
/*     */   public int getTop()
/*     */   {
/*  72 */     return this.array.getInt(2);
/*     */   }
/*     */ 
/*     */   public void setTop(int y)
/*     */   {
/*  82 */     this.array.growToSize(3);
/*  83 */     if (y == -1)
/*     */     {
/*  85 */       this.array.set(2, (COSBase)null);
/*     */     }
/*     */     else
/*     */     {
/*  89 */       this.array.setInt(2, y);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean fitBoundingBox()
/*     */   {
/* 100 */     return "FitBH".equals(this.array.getName(1));
/*     */   }
/*     */ 
/*     */   public void setFitBoundingBox(boolean fitBoundingBox)
/*     */   {
/* 110 */     this.array.growToSize(2);
/* 111 */     if (fitBoundingBox)
/*     */     {
/* 113 */       this.array.setName(1, "FitBH");
/*     */     }
/*     */     else
/*     */     {
/* 117 */       this.array.setName(1, "FitH");
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageFitWidthDestination
 * JD-Core Version:    0.6.2
 */