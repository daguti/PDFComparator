/*    */ package org.apache.xmpbox.type;
/*    */ 
/*    */ import org.apache.xmpbox.XMPMetadata;
/*    */ 
/*    */ public class RealType extends AbstractSimpleProperty
/*    */ {
/*    */   private float realValue;
/*    */ 
/*    */   public RealType(XMPMetadata metadata, String namespaceURI, String prefix, String propertyName, Object value)
/*    */   {
/* 53 */     super(metadata, namespaceURI, prefix, propertyName, value);
/*    */   }
/*    */ 
/*    */   public Float getValue()
/*    */   {
/* 64 */     return Float.valueOf(this.realValue);
/*    */   }
/*    */ 
/*    */   public void setValue(Object value)
/*    */   {
/* 75 */     if ((value instanceof Float))
/*    */     {
/* 77 */       this.realValue = ((Float)value).floatValue();
/*    */     }
/* 79 */     else if ((value instanceof String))
/*    */     {
/* 82 */       this.realValue = Float.valueOf((String)value).floatValue();
/*    */     }
/*    */     else
/*    */     {
/* 87 */       throw new IllegalArgumentException("Value given is not allowed for the Real type.");
/*    */     }
/*    */   }
/*    */ 
/*    */   public String getStringValue()
/*    */   {
/* 94 */     return Float.toString(this.realValue);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.type.RealType
 * JD-Core Version:    0.6.2
 */