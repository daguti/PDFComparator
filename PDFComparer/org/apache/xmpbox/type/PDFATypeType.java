/*    */ package org.apache.xmpbox.type;
/*    */ 
/*    */ import org.apache.xmpbox.XMPMetadata;
/*    */ 
/*    */ @StructuredType(preferedPrefix="pdfaType", namespace="http://www.aiim.org/pdfa/ns/type#")
/*    */ public class PDFATypeType extends AbstractStructuredType
/*    */ {
/*    */ 
/*    */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*    */   public static final String TYPE = "type";
/*    */ 
/*    */   @PropertyType(type=Types.URI, card=Cardinality.Simple)
/*    */   public static final String NS_URI = "namespaceURI";
/*    */ 
/*    */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*    */   public static final String PREFIX = "prefix";
/*    */ 
/*    */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*    */   public static final String DESCRIPTION = "description";
/*    */ 
/*    */   @PropertyType(type=Types.PDFAField, card=Cardinality.Seq)
/*    */   public static final String FIELD = "field";
/*    */ 
/*    */   public PDFATypeType(XMPMetadata metadata)
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
/*    */   public String getType()
/*    */   {
/* 58 */     TextType tt = (TextType)getProperty("type");
/* 59 */     return tt == null ? null : tt.getStringValue();
/*    */   }
/*    */ 
/*    */   public String getPrefixValue()
/*    */   {
/* 64 */     TextType tt = (TextType)getProperty("prefix");
/* 65 */     return tt == null ? null : tt.getStringValue();
/*    */   }
/*    */ 
/*    */   public String getDescription()
/*    */   {
/* 70 */     TextType tt = (TextType)getProperty("description");
/* 71 */     return tt == null ? null : tt.getStringValue();
/*    */   }
/*    */ 
/*    */   public ArrayProperty getFields()
/*    */   {
/* 76 */     return getArrayProperty("field");
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.type.PDFATypeType
 * JD-Core Version:    0.6.2
 */