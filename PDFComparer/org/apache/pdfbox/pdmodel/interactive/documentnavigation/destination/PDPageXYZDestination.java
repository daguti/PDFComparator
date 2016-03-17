/*     */ package org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ 
/*     */ public class PDPageXYZDestination extends PDPageDestination
/*     */ {
/*     */   protected static final String TYPE = "XYZ";
/*     */ 
/*     */   public PDPageXYZDestination()
/*     */   {
/*  44 */     this.array.growToSize(5);
/*  45 */     this.array.setName(1, "XYZ");
/*     */   }
/*     */ 
/*     */   public PDPageXYZDestination(COSArray arr)
/*     */   {
/*  56 */     super(arr);
/*     */   }
/*     */ 
/*     */   public int getLeft()
/*     */   {
/*  67 */     return this.array.getInt(2);
/*     */   }
/*     */ 
/*     */   public void setLeft(int x)
/*     */   {
/*  77 */     this.array.growToSize(3);
/*  78 */     if (x == -1)
/*     */     {
/*  80 */       this.array.set(2, (COSBase)null);
/*     */     }
/*     */     else
/*     */     {
/*  84 */       this.array.setInt(2, x);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getTop()
/*     */   {
/*  96 */     return this.array.getInt(3);
/*     */   }
/*     */ 
/*     */   public void setTop(int y)
/*     */   {
/* 106 */     this.array.growToSize(4);
/* 107 */     if (y == -1)
/*     */     {
/* 109 */       this.array.set(3, (COSBase)null);
/*     */     }
/*     */     else
/*     */     {
/* 113 */       this.array.setInt(3, y);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getZoom()
/*     */   {
/* 125 */     return this.array.getInt(4);
/*     */   }
/*     */ 
/*     */   public void setZoom(int zoom)
/*     */   {
/* 135 */     this.array.growToSize(5);
/* 136 */     if (zoom == -1)
/*     */     {
/* 138 */       this.array.set(4, (COSBase)null);
/*     */     }
/*     */     else
/*     */     {
/* 142 */       this.array.setInt(4, zoom);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageXYZDestination
 * JD-Core Version:    0.6.2
 */