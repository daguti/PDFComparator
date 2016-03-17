/*    */ package org.apache.jempbox.xmp.pdfa;
/*    */ 
/*    */ import org.apache.jempbox.xmp.XMPMetadata;
/*    */ import org.apache.jempbox.xmp.XMPSchema;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ public class XMPSchemaPDFAType extends XMPSchema
/*    */ {
/*    */   public static final String NAMESPACE = "http://www.aiim.org/pdfa/ns/type";
/*    */ 
/*    */   public XMPSchemaPDFAType(XMPMetadata parent)
/*    */   {
/* 44 */     super(parent, "pdfaType", "http://www.aiim.org/pdfa/ns/type");
/*    */   }
/*    */ 
/*    */   public XMPSchemaPDFAType(Element element, String prefix)
/*    */   {
/* 55 */     super(element, prefix);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.jempbox.xmp.pdfa.XMPSchemaPDFAType
 * JD-Core Version:    0.6.2
 */