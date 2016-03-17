/*    */ package org.stringtemplate.v4.debug;
/*    */ 
/*    */ public class AddAttributeEvent extends ConstructionEvent
/*    */ {
/*    */   String name;
/*    */   Object value;
/*    */ 
/*    */   public AddAttributeEvent(String name, Object value)
/*    */   {
/* 34 */     this.name = name;
/* 35 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 39 */     return "addEvent{, name='" + this.name + '\'' + ", value=" + this.value + ", location=" + getFileName() + ":" + getLine() + '}';
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.debug.AddAttributeEvent
 * JD-Core Version:    0.6.2
 */