/*    */ package org.apache.xmpbox.type;
/*    */ 
/*    */ import org.apache.xmpbox.XMPMetadata;
/*    */ 
/*    */ @StructuredType(preferedPrefix="pdfaField", namespace="http://www.aiim.org/pdfa/ns/field#")
/*    */ public class PDFAFieldType extends AbstractStructuredType
/*    */ {
/*    */ 
/*    */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*    */   public static final String NAME = "name";
/*    */ 
/*    */   @PropertyType(type=Types.Choice, card=Cardinality.Simple)
/*    */   public static final String VALUETYPE = "valueType";
/*    */ 
/*    */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*    */   public static final String DESCRIPTION = "description";
/*    */ 
/*    */   public PDFAFieldType(XMPMetadata metadata)
/*    */   {
/* 41 */     super(metadata);
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 46 */     TextType tt = (TextType)getProperty("name");
/* 47 */     return tt == null ? null : tt.getStringValue();
/*    */   }
/*    */ 
/*    */   public String getValueType()
/*    */   {
/* 52 */     TextType tt = (TextType)getProperty("valueType");
/* 53 */     return tt == null ? null : tt.getStringValue();
/*    */   }
/*    */ 
/*    */   public String getDescription()
/*    */   {
/* 58 */     TextType tt = (TextType)getProperty("description");
/* 59 */     return tt == null ? null : tt.getStringValue();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.type.PDFAFieldType
 * JD-Core Version:    0.6.2
 */