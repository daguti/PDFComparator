/*    */ package org.apache.jempbox.xmp;
/*    */ 
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ public class XMPSchemaPagedText extends XMPSchema
/*    */ {
/*    */   public static final String NAMESPACE = "http://ns.adobe.com/xap/1.0/t/pg/";
/*    */ 
/*    */   public XMPSchemaPagedText(XMPMetadata parent)
/*    */   {
/* 40 */     super(parent, "xmpTPg", "http://ns.adobe.com/xap/1.0/t/pg/");
/*    */   }
/*    */ 
/*    */   public XMPSchemaPagedText(Element element, String prefix)
/*    */   {
/* 51 */     super(element, prefix);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.jempbox.xmp.XMPSchemaPagedText
 * JD-Core Version:    0.6.2
 */