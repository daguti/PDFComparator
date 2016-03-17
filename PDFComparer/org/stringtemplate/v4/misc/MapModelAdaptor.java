/*    */ package org.stringtemplate.v4.misc;
/*    */ 
/*    */ import java.util.Map;
/*    */ import org.stringtemplate.v4.Interpreter;
/*    */ import org.stringtemplate.v4.ModelAdaptor;
/*    */ import org.stringtemplate.v4.ST;
/*    */ 
/*    */ public class MapModelAdaptor
/*    */   implements ModelAdaptor
/*    */ {
/*    */   public Object getProperty(Interpreter interp, ST self, Object o, Object property, String propertyName)
/*    */     throws STNoSuchPropertyException
/*    */   {
/* 42 */     Map map = (Map)o;
/*    */     Object value;
/*    */     Object value;
/* 43 */     if (property == null) { value = map.get("default"); }
/*    */     else
/*    */     {
/* 44 */       Object value;
/* 44 */       if (property.equals("keys")) { value = map.keySet(); }
/*    */       else
/*    */       {
/* 45 */         Object value;
/* 45 */         if (property.equals("values")) { value = map.values(); }
/*    */         else
/*    */         {
/* 46 */           Object value;
/* 46 */           if (map.containsKey(property)) { value = map.get(property); }
/*    */           else
/*    */           {
/*    */             Object value;
/* 47 */             if (map.containsKey(propertyName))
/* 48 */               value = map.get(propertyName);
/*    */             else
/* 50 */               value = map.get("default"); 
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/* 51 */     if (value == "key") {
/* 52 */       value = property;
/*    */     }
/* 54 */     return value;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.misc.MapModelAdaptor
 * JD-Core Version:    0.6.2
 */