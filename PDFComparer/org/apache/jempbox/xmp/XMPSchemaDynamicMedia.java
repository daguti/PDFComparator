/*    */ package org.apache.jempbox.xmp;
/*    */ 
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ public class XMPSchemaDynamicMedia extends XMPSchema
/*    */ {
/*    */   public static final String NAMESPACE = "http://ns.adobe.com/xmp/1.0/DynamicMedia/";
/*    */ 
/*    */   public XMPSchemaDynamicMedia(XMPMetadata parent)
/*    */   {
/* 40 */     super(parent, "xmpDM", "http://ns.adobe.com/xmp/1.0/DynamicMedia/");
/*    */   }
/*    */ 
/*    */   public XMPSchemaDynamicMedia(Element element, String prefix)
/*    */   {
/* 51 */     super(element, prefix);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.jempbox.xmp.XMPSchemaDynamicMedia
 * JD-Core Version:    0.6.2
 */