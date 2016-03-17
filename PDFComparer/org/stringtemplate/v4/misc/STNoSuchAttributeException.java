/*    */ package org.stringtemplate.v4.misc;
/*    */ 
/*    */ import org.stringtemplate.v4.InstanceScope;
/*    */ import org.stringtemplate.v4.ST;
/*    */ import org.stringtemplate.v4.compiler.STException;
/*    */ 
/*    */ public class STNoSuchAttributeException extends STException
/*    */ {
/*    */   public InstanceScope scope;
/*    */   public String name;
/*    */ 
/*    */   public String getMessage()
/*    */   {
/* 41 */     return "from template " + this.scope.st.getName() + " no attribute " + this.name + " is visible";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.misc.STNoSuchAttributeException
 * JD-Core Version:    0.6.2
 */