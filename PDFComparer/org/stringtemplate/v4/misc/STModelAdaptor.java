/*    */ package org.stringtemplate.v4.misc;
/*    */ 
/*    */ import org.stringtemplate.v4.Interpreter;
/*    */ import org.stringtemplate.v4.ModelAdaptor;
/*    */ import org.stringtemplate.v4.ST;
/*    */ 
/*    */ public class STModelAdaptor
/*    */   implements ModelAdaptor
/*    */ {
/*    */   public Object getProperty(Interpreter interp, ST self, Object o, Object property, String propertyName)
/*    */     throws STNoSuchPropertyException
/*    */   {
/* 38 */     ST st = (ST)o;
/* 39 */     return st.getAttribute(propertyName);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.misc.STModelAdaptor
 * JD-Core Version:    0.6.2
 */