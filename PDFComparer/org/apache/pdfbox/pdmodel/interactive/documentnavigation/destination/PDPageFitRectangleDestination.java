/*     */ package org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ 
/*     */ public class PDPageFitRectangleDestination extends PDPageDestination
/*     */ {
/*     */   protected static final String TYPE = "FitR";
/*     */ 
/*     */   public PDPageFitRectangleDestination()
/*     */   {
/*  43 */     this.array.growToSize(6);
/*  44 */     this.array.setName(1, "FitR");
/*     */   }
/*     */ 
/*     */   public PDPageFitRectangleDestination(COSArray arr)
/*     */   {
/*  55 */     super(arr);
/*     */   }
/*     */ 
/*     */   public int getLeft()
/*     */   {
/*  66 */     return this.array.getInt(2);
/*     */   }
/*     */ 
/*     */   public void setLeft(int x)
/*     */   {
/*  76 */     this.array.growToSize(3);
/*  77 */     if (x == -1)
/*     */     {
/*  79 */       this.array.set(2, (COSBase)null);
/*     */     }
/*     */     else
/*     */     {
/*  83 */       this.array.setInt(2, x);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getBottom()
/*     */   {
/*  95 */     return this.array.getInt(3);
/*     */   }
/*     */ 
/*     */   public void setBottom(int y)
/*     */   {
/* 105 */     this.array.growToSize(6);
/* 106 */     if (y == -1)
/*     */     {
/* 108 */       this.array.set(3, (COSBase)null);
/*     */     }
/*     */     else
/*     */     {
/* 112 */       this.array.setInt(3, y);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getRight()
/*     */   {
/* 124 */     return this.array.getInt(4);
/*     */   }
/*     */ 
/*     */   public void setRight(int x)
/*     */   {
/* 134 */     this.array.growToSize(6);
/* 135 */     if (x == -1)
/*     */     {
/* 137 */       this.array.set(4, (COSBase)null);
/*     */     }
/*     */     else
/*     */     {
/* 141 */       this.array.setInt(4, x);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getTop()
/*     */   {
/* 154 */     return this.array.getInt(5);
/*     */   }
/*     */ 
/*     */   public void setTop(int y)
/*     */   {
/* 164 */     this.array.growToSize(6);
/* 165 */     if (y == -1)
/*     */     {
/* 167 */       this.array.set(5, (COSBase)null);
/*     */     }
/*     */     else
/*     */     {
/* 171 */       this.array.setInt(5, y);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageFitRectangleDestination
 * JD-Core Version:    0.6.2
 */