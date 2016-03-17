/*    */ package org.stringtemplate.v4.misc;
/*    */ 
/*    */ import java.util.Map;
/*    */ import org.stringtemplate.v4.Interpreter;
/*    */ import org.stringtemplate.v4.ST;
/*    */ 
/*    */ public class AggregateModelAdaptor extends MapModelAdaptor
/*    */ {
/*    */   public Object getProperty(Interpreter interp, ST self, Object o, Object property, String propertyName)
/*    */     throws STNoSuchPropertyException
/*    */   {
/* 42 */     Map map = ((Aggregate)o).properties;
/* 43 */     return super.getProperty(interp, self, map, property, propertyName);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.misc.AggregateModelAdaptor
 * JD-Core Version:    0.6.2
 */