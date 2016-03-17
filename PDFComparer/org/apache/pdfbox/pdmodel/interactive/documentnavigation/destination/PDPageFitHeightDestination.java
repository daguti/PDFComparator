/*     */ package org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ 
/*     */ public class PDPageFitHeightDestination extends PDPageDestination
/*     */ {
/*     */   protected static final String TYPE = "FitV";
/*     */   protected static final String TYPE_BOUNDED = "FitBV";
/*     */ 
/*     */   public PDPageFitHeightDestination()
/*     */   {
/*  47 */     this.array.growToSize(3);
/*  48 */     this.array.setName(1, "FitV");
/*     */   }
/*     */ 
/*     */   public PDPageFitHeightDestination(COSArray arr)
/*     */   {
/*  59 */     super(arr);
/*     */   }
/*     */ 
/*     */   public int getLeft()
/*     */   {
/*  70 */     return this.array.getInt(2);
/*     */   }
/*     */ 
/*     */   public void setLeft(int x)
/*     */   {
/*  80 */     this.array.growToSize(3);
/*  81 */     if (x == -1)
/*     */     {
/*  83 */       this.array.set(2, (COSBase)null);
/*     */     }
/*     */     else
/*     */     {
/*  87 */       this.array.setInt(2, x);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean fitBoundingBox()
/*     */   {
/*  98 */     return "FitBV".equals(this.array.getName(1));
/*     */   }
/*     */ 
/*     */   public void setFitBoundingBox(boolean fitBoundingBox)
/*     */   {
/* 108 */     this.array.growToSize(2);
/* 109 */     if (fitBoundingBox)
/*     */     {
/* 111 */       this.array.setName(1, "FitBV");
/*     */     }
/*     */     else
/*     */     {
/* 115 */       this.array.setName(1, "FitV");
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageFitHeightDestination
 * JD-Core Version:    0.6.2
 */