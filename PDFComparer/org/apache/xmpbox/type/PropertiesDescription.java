/*    */ package org.apache.xmpbox.type;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class PropertiesDescription
/*    */ {
/*    */   private Map<String, PropertyType> types;
/*    */ 
/*    */   public PropertiesDescription()
/*    */   {
/* 48 */     this.types = new HashMap();
/*    */   }
/*    */ 
/*    */   public List<String> getPropertiesName()
/*    */   {
/* 58 */     return new ArrayList(this.types.keySet());
/*    */   }
/*    */ 
/*    */   public void addNewProperty(String name, PropertyType type)
/*    */   {
/* 71 */     this.types.put(name, type);
/*    */   }
/*    */ 
/*    */   public PropertyType getPropertyType(String name)
/*    */   {
/* 83 */     return (PropertyType)this.types.get(name);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.type.PropertiesDescription
 * JD-Core Version:    0.6.2
 */