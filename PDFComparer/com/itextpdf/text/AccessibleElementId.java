/*    */ package com.itextpdf.text;
/*    */ 
/*    */ public class AccessibleElementId
/*    */   implements Comparable<AccessibleElementId>
/*    */ {
/* 49 */   private static int id_counter = 0;
/* 50 */   private int id = 0;
/*    */ 
/*    */   public AccessibleElementId() {
/* 53 */     this.id = (++id_counter);
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 57 */     return Integer.toString(this.id);
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 61 */     return this.id;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object o) {
/* 65 */     return ((o instanceof AccessibleElementId)) && (this.id == ((AccessibleElementId)o).id);
/*    */   }
/*    */ 
/*    */   public int compareTo(AccessibleElementId elementId) {
/* 69 */     if (this.id < elementId.id)
/* 70 */       return -1;
/* 71 */     if (this.id > elementId.id) {
/* 72 */       return 1;
/*    */     }
/* 74 */     return 0;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.AccessibleElementId
 * JD-Core Version:    0.6.2
 */