/*    */ package org.apache.xmpbox.type;
/*    */ 
/*    */ import org.apache.xmpbox.XMPMetadata;
/*    */ 
/*    */ @StructuredType(preferedPrefix="pdfaSchema", namespace="http://www.aiim.org/pdfa/ns/schema#")
/*    */ public class PDFASchemaType extends AbstractStructuredType
/*    */ {
/*    */ 
/*    */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*    */   public static final String SCHEMA = "schema";
/*    */ 
/*    */   @PropertyType(type=Types.URI, card=Cardinality.Simple)
/*    */   public static final String NAMESPACE_URI = "namespaceURI";
/*    */ 
/*    */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*    */   public static final String PREFIX = "prefix";
/*    */ 
/*    */   @PropertyType(type=Types.PDFAProperty, card=Cardinality.Seq)
/*    */   public static final String PROPERTY = "property";
/*    */ 
/*    */   @PropertyType(type=Types.PDFAType, card=Cardinality.Seq)
/*    */   public static final String VALUE_TYPE = "valueType";
/*    */ 
/*    */   public PDFASchemaType(XMPMetadata metadata)
/*    */   {
/* 47 */     super(metadata);
/*    */   }
/*    */ 
/*    */   public String getNamespaceURI()
/*    */   {
/* 52 */     URIType tt = (URIType)getProperty("namespaceURI");
/* 53 */     return tt == null ? null : tt.getStringValue();
/*    */   }
/*    */ 
/*    */   public String getPrefixValue()
/*    */   {
/* 58 */     TextType tt = (TextType)getProperty("prefix");
/* 59 */     return tt == null ? null : tt.getStringValue();
/*    */   }
/*    */ 
/*    */   public ArrayProperty getProperty()
/*    */   {
/* 64 */     return getArrayProperty("property");
/*    */   }
/*    */ 
/*    */   public ArrayProperty getValueType()
/*    */   {
/* 69 */     return getArrayProperty("valueType");
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.type.PDFASchemaType
 * JD-Core Version:    0.6.2
 */