/*    */ package org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSArray;
/*    */ 
/*    */ public class PDPageFitDestination extends PDPageDestination
/*    */ {
/*    */   protected static final String TYPE = "Fit";
/*    */   protected static final String TYPE_BOUNDED = "FitB";
/*    */ 
/*    */   public PDPageFitDestination()
/*    */   {
/* 46 */     this.array.growToSize(2);
/* 47 */     this.array.setName(1, "Fit");
/*    */   }
/*    */ 
/*    */   public PDPageFitDestination(COSArray arr)
/*    */   {
/* 58 */     super(arr);
/*    */   }
/*    */ 
/*    */   public boolean fitBoundingBox()
/*    */   {
/* 68 */     return "FitB".equals(this.array.getName(1));
/*    */   }
/*    */ 
/*    */   public void setFitBoundingBox(boolean fitBoundingBox)
/*    */   {
/* 78 */     this.array.growToSize(2);
/* 79 */     if (fitBoundingBox)
/*    */     {
/* 81 */       this.array.setName(1, "FitB");
/*    */     }
/*    */     else
/*    */     {
/* 85 */       this.array.setName(1, "Fit");
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageFitDestination
 * JD-Core Version:    0.6.2
 */