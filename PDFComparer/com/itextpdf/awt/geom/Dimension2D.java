/*    */ package com.itextpdf.awt.geom;
/*    */ 
/*    */ public abstract class Dimension2D
/*    */   implements Cloneable
/*    */ {
/*    */   public abstract double getWidth();
/*    */ 
/*    */   public abstract double getHeight();
/*    */ 
/*    */   public abstract void setSize(double paramDouble1, double paramDouble2);
/*    */ 
/*    */   public void setSize(Dimension2D d)
/*    */   {
/* 38 */     setSize(d.getWidth(), d.getHeight());
/*    */   }
/*    */ 
/*    */   public Object clone()
/*    */   {
/*    */     try {
/* 44 */       return super.clone(); } catch (CloneNotSupportedException e) {
/*    */     }
/* 46 */     throw new InternalError();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.awt.geom.Dimension2D
 * JD-Core Version:    0.6.2
 */