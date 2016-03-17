/*    */ package org.apache.xmpbox.type;
/*    */ 
/*    */ import org.apache.xmpbox.XMPMetadata;
/*    */ 
/*    */ public class TextType extends AbstractSimpleProperty
/*    */ {
/*    */   private String textValue;
/*    */ 
/*    */   public TextType(XMPMetadata metadata, String namespaceURI, String prefix, String propertyName, Object value)
/*    */   {
/* 53 */     super(metadata, namespaceURI, prefix, propertyName, value);
/*    */   }
/*    */ 
/*    */   public void setValue(Object value)
/*    */   {
/* 65 */     if (!(value instanceof String))
/*    */     {
/* 67 */       throw new IllegalArgumentException("Value given is not allowed for the Text type : '" + value + "'");
/*    */     }
/*    */ 
/* 71 */     this.textValue = ((String)value);
/*    */   }
/*    */ 
/*    */   public String getStringValue()
/*    */   {
/* 79 */     return this.textValue;
/*    */   }
/*    */ 
/*    */   public Object getValue()
/*    */   {
/* 85 */     return this.textValue;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.type.TextType
 * JD-Core Version:    0.6.2
 */