/*    */ package org.apache.jempbox.xmp.pdfa;
/*    */ 
/*    */ import org.apache.jempbox.xmp.XMPMetadata;
/*    */ import org.apache.jempbox.xmp.XMPSchema;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ public class XMPSchemaPDFAField extends XMPSchema
/*    */ {
/*    */   public static final String NAMESPACE = "http://www.aiim.org/pdfa/ns/field";
/*    */ 
/*    */   public XMPSchemaPDFAField(XMPMetadata parent)
/*    */   {
/* 45 */     super(parent, "pdfaField", "http://www.aiim.org/pdfa/ns/field");
/*    */   }
/*    */ 
/*    */   public XMPSchemaPDFAField(Element element, String prefix)
/*    */   {
/* 56 */     super(element, prefix);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.jempbox.xmp.pdfa.XMPSchemaPDFAField
 * JD-Core Version:    0.6.2
 */