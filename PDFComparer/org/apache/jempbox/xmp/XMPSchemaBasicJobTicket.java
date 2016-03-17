/*    */ package org.apache.jempbox.xmp;
/*    */ 
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ public class XMPSchemaBasicJobTicket extends XMPSchema
/*    */ {
/*    */   public static final String NAMESPACE = "http://ns.adobe.com/xap/1.0/bj/";
/*    */ 
/*    */   public XMPSchemaBasicJobTicket(XMPMetadata parent)
/*    */   {
/* 40 */     super(parent, "xmpBJ", "http://ns.adobe.com/xap/1.0/bj/");
/*    */   }
/*    */ 
/*    */   public XMPSchemaBasicJobTicket(Element element, String prefix)
/*    */   {
/* 51 */     super(element, prefix);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.jempbox.xmp.XMPSchemaBasicJobTicket
 * JD-Core Version:    0.6.2
 */