/*    */ package org.stringtemplate.v4.misc;
/*    */ 
/*    */ import org.stringtemplate.v4.compiler.STException;
/*    */ 
/*    */ public class STNoSuchPropertyException extends STException
/*    */ {
/*    */   public Object o;
/*    */   public String propertyName;
/*    */ 
/*    */   public STNoSuchPropertyException()
/*    */   {
/*    */   }
/*    */ 
/*    */   public STNoSuchPropertyException(Exception e, Object o, String propertyName)
/*    */   {
/* 39 */     super(null, e);
/* 40 */     this.o = o;
/* 41 */     this.propertyName = propertyName;
/*    */   }
/*    */ 
/*    */   public String getMessage()
/*    */   {
/* 46 */     if (this.o != null) return "object " + this.o.getClass() + " has no " + this.propertyName + " property";
/* 47 */     return "no such property: " + this.propertyName;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.misc.STNoSuchPropertyException
 * JD-Core Version:    0.6.2
 */