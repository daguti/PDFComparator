/*    */ package org.apache.xmpbox.type;
/*    */ 
/*    */ import org.apache.xmpbox.XMPMetadata;
/*    */ 
/*    */ public class IntegerType extends AbstractSimpleProperty
/*    */ {
/*    */   private int integerValue;
/*    */ 
/*    */   public IntegerType(XMPMetadata metadata, String namespaceURI, String prefix, String propertyName, Object value)
/*    */   {
/* 53 */     super(metadata, namespaceURI, prefix, propertyName, value);
/*    */   }
/*    */ 
/*    */   public Integer getValue()
/*    */   {
/* 64 */     return Integer.valueOf(this.integerValue);
/*    */   }
/*    */ 
/*    */   public void setValue(Object value)
/*    */   {
/* 75 */     if ((value instanceof Integer))
/*    */     {
/* 77 */       this.integerValue = ((Integer)value).intValue();
/*    */     }
/* 79 */     else if ((value instanceof String))
/*    */     {
/* 81 */       this.integerValue = Integer.valueOf((String)value).intValue();
/*    */     }
/*    */     else
/*    */     {
/* 87 */       throw new IllegalArgumentException("Value given is not allowed for the Integer type.");
/*    */     }
/*    */   }
/*    */ 
/*    */   public String getStringValue()
/*    */   {
/* 94 */     return Integer.toString(this.integerValue);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.type.IntegerType
 * JD-Core Version:    0.6.2
 */