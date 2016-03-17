/*    */ package org.apache.jempbox.xmp.pdfa;
/*    */ 
/*    */ import org.apache.jempbox.xmp.XMPMetadata;
/*    */ import org.apache.jempbox.xmp.XMPSchema;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ public class XMPSchemaPDFASchema extends XMPSchema
/*    */ {
/*    */   public static final String NAMESPACE = "http://www.aiim.org/pdfa/ns/schema";
/*    */ 
/*    */   public XMPSchemaPDFASchema(XMPMetadata parent)
/*    */   {
/* 43 */     super(parent, "pdfaSchema", "http://www.aiim.org/pdfa/ns/schema");
/*    */   }
/*    */ 
/*    */   public XMPSchemaPDFASchema(Element element, String prefix)
/*    */   {
/* 54 */     super(element, prefix);
/*    */   }
/*    */ 
/*    */   public void setSchema(String schema)
/*    */   {
/* 64 */     setTextProperty("pdfaSchema:schema", schema);
/*    */   }
/*    */ 
/*    */   public String getSchema()
/*    */   {
/* 74 */     return getTextProperty("pdfaSchema:schema");
/*    */   }
/*    */ 
/*    */   public void setPrefix(String prefix)
/*    */   {
/* 84 */     setTextProperty("pdfaSchema:prefix", prefix);
/*    */   }
/*    */ 
/*    */   public String getPrefix()
/*    */   {
/* 94 */     return getTextProperty("pdfaSchema:prefix");
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.jempbox.xmp.pdfa.XMPSchemaPDFASchema
 * JD-Core Version:    0.6.2
 */