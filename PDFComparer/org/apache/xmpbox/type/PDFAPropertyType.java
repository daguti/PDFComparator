/*    */ package org.apache.xmpbox.type;
/*    */ 
/*    */ import org.apache.xmpbox.XMPMetadata;
/*    */ 
/*    */ @StructuredType(preferedPrefix="pdfaProperty", namespace="http://www.aiim.org/pdfa/ns/property#")
/*    */ public class PDFAPropertyType extends AbstractStructuredType
/*    */ {
/*    */ 
/*    */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*    */   public static final String NAME = "name";
/*    */ 
/*    */   @PropertyType(type=Types.Choice, card=Cardinality.Simple)
/*    */   public static final String VALUETYPE = "valueType";
/*    */ 
/*    */   @PropertyType(type=Types.Choice, card=Cardinality.Simple)
/*    */   public static final String CATEGORY = "category";
/*    */ 
/*    */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*    */   public static final String DESCRIPTION = "description";
/*    */ 
/*    */   public PDFAPropertyType(XMPMetadata metadata)
/*    */   {
/* 44 */     super(metadata);
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 49 */     TextType tt = (TextType)getProperty("name");
/* 50 */     return tt == null ? null : tt.getStringValue();
/*    */   }
/*    */ 
/*    */   public String getValueType()
/*    */   {
/* 55 */     ChoiceType tt = (ChoiceType)getProperty("valueType");
/* 56 */     return tt == null ? null : tt.getStringValue();
/*    */   }
/*    */ 
/*    */   public String getDescription()
/*    */   {
/* 61 */     TextType tt = (TextType)getProperty("description");
/* 62 */     return tt == null ? null : tt.getStringValue();
/*    */   }
/*    */ 
/*    */   public String getCategory()
/*    */   {
/* 67 */     ChoiceType tt = (ChoiceType)getProperty("category");
/* 68 */     return tt == null ? null : tt.getStringValue();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.type.PDFAPropertyType
 * JD-Core Version:    0.6.2
 */