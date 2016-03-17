/*    */ package org.apache.xmpbox.schema;
/*    */ 
/*    */ import org.apache.xmpbox.XMPMetadata;
/*    */ import org.apache.xmpbox.type.ArrayProperty;
/*    */ import org.apache.xmpbox.type.Cardinality;
/*    */ import org.apache.xmpbox.type.PropertyType;
/*    */ import org.apache.xmpbox.type.StructuredType;
/*    */ import org.apache.xmpbox.type.Types;
/*    */ 
/*    */ @StructuredType(preferedPrefix="pdfaExtension", namespace="http://www.aiim.org/pdfa/ns/extension/")
/*    */ public class PDFAExtensionSchema extends XMPSchema
/*    */ {
/*    */ 
/*    */   @PropertyType(type=Types.PDFASchema, card=Cardinality.Bag)
/*    */   public static final String SCHEMAS = "schemas";
/*    */ 
/*    */   public PDFAExtensionSchema(XMPMetadata metadata)
/*    */   {
/* 52 */     super(metadata);
/*    */   }
/*    */ 
/*    */   public PDFAExtensionSchema(XMPMetadata metadata, String prefix)
/*    */   {
/* 57 */     super(metadata, prefix);
/*    */   }
/*    */ 
/*    */   public ArrayProperty getSchemasProperty()
/*    */   {
/* 66 */     return (ArrayProperty)getProperty("schemas");
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.schema.PDFAExtensionSchema
 * JD-Core Version:    0.6.2
 */