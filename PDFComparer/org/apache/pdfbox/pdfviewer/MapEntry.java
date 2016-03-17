/*    */ package org.apache.pdfbox.pdfviewer;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ 
/*    */ public class MapEntry
/*    */ {
/*    */   private Object key;
/*    */   private Object value;
/*    */ 
/*    */   public Object getKey()
/*    */   {
/* 40 */     return this.key;
/*    */   }
/*    */ 
/*    */   public void setKey(Object k)
/*    */   {
/* 50 */     this.key = k;
/*    */   }
/*    */ 
/*    */   public Object getValue()
/*    */   {
/* 60 */     return this.value;
/*    */   }
/*    */ 
/*    */   public void setValue(Object val)
/*    */   {
/* 70 */     this.value = val;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 80 */     String retval = null;
/* 81 */     if ((this.key instanceof COSName))
/*    */     {
/* 83 */       retval = ((COSName)this.key).getName();
/*    */     }
/*    */     else
/*    */     {
/* 87 */       retval = "" + this.key;
/*    */     }
/* 89 */     return retval;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdfviewer.MapEntry
 * JD-Core Version:    0.6.2
 */