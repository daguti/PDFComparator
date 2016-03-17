/*    */ package org.stringtemplate.v4.misc;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class Aggregate
/*    */ {
/* 74 */   public HashMap<String, Object> properties = new HashMap();
/*    */ 
/*    */   protected void put(String propName, Object propValue)
/*    */   {
/* 79 */     this.properties.put(propName, propValue);
/*    */   }
/*    */   public Object get(String propName) {
/* 82 */     return this.properties.get(propName);
/*    */   }
/*    */   public String toString() {
/* 85 */     return this.properties.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.misc.Aggregate
 * JD-Core Version:    0.6.2
 */