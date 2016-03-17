/*    */ package org.apache.jempbox.xmp.pdfa;
/*    */ 
/*    */ import org.apache.jempbox.xmp.XMPMetadata;
/*    */ import org.apache.jempbox.xmp.XMPSchema;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ public class XMPSchemaPDFAProperty extends XMPSchema
/*    */ {
/*    */   public static final String NAMESPACE = "http://www.aiim.org/pdfa/ns/property";
/*    */ 
/*    */   public XMPSchemaPDFAProperty(XMPMetadata parent)
/*    */   {
/* 44 */     super(parent, "pdfaProperty", "http://www.aiim.org/pdfa/ns/property");
/*    */   }
/*    */ 
/*    */   public XMPSchemaPDFAProperty(Element element, String prefix)
/*    */   {
/* 55 */     super(element, prefix);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.jempbox.xmp.pdfa.XMPSchemaPDFAProperty
 * JD-Core Version:    0.6.2
 */