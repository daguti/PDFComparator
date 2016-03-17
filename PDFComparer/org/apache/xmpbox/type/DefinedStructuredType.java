/*    */ package org.apache.xmpbox.type;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.apache.xmpbox.XMPMetadata;
/*    */ 
/*    */ public class DefinedStructuredType extends AbstractStructuredType
/*    */ {
/* 32 */   private Map<String, PropertyType> definedProperties = null;
/*    */ 
/*    */   public DefinedStructuredType(XMPMetadata metadata, String namespaceURI, String fieldPrefix, String propertyName)
/*    */   {
/* 36 */     super(metadata, namespaceURI, fieldPrefix, propertyName);
/* 37 */     this.definedProperties = new HashMap();
/*    */   }
/*    */ 
/*    */   public DefinedStructuredType(XMPMetadata metadata)
/*    */   {
/* 42 */     super(metadata);
/* 43 */     this.definedProperties = new HashMap();
/*    */   }
/*    */ 
/*    */   public void addProperty(String name, PropertyType type)
/*    */   {
/* 48 */     this.definedProperties.put(name, type);
/*    */   }
/*    */ 
/*    */   public Map<String, PropertyType> getDefinedProperties()
/*    */   {
/* 53 */     return this.definedProperties;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.type.DefinedStructuredType
 * JD-Core Version:    0.6.2
 */