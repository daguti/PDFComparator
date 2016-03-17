/*    */ package org.apache.fontbox.cff;
/*    */ 
/*    */ public abstract class CIDKeyedFDSelect
/*    */ {
/* 22 */   protected CFFFontROS owner = null;
/*    */ 
/*    */   public CIDKeyedFDSelect(CFFFontROS _owner)
/*    */   {
/* 29 */     this.owner = _owner;
/*    */   }
/*    */ 
/*    */   public abstract int getFd(int paramInt);
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.cff.CIDKeyedFDSelect
 * JD-Core Version:    0.6.2
 */