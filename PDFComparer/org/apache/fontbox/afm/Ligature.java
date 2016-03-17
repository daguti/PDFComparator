/*    */ package org.apache.fontbox.afm;
/*    */ 
/*    */ public class Ligature
/*    */ {
/*    */   private String successor;
/*    */   private String ligature;
/*    */ 
/*    */   public String getLigature()
/*    */   {
/* 35 */     return this.ligature;
/*    */   }
/*    */ 
/*    */   public void setLigature(String lig)
/*    */   {
/* 43 */     this.ligature = lig;
/*    */   }
/*    */ 
/*    */   public String getSuccessor()
/*    */   {
/* 51 */     return this.successor;
/*    */   }
/*    */ 
/*    */   public void setSuccessor(String successorValue)
/*    */   {
/* 59 */     this.successor = successorValue;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.afm.Ligature
 * JD-Core Version:    0.6.2
 */