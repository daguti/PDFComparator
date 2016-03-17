/*    */ package org.apache.pdfbox.util;
/*    */ 
/*    */ import javax.imageio.metadata.IIOInvalidTreeException;
/*    */ import javax.imageio.metadata.IIOMetadata;
/*    */ import javax.imageio.metadata.IIOMetadataNode;
/*    */ import org.w3c.dom.Element;
/*    */ import org.w3c.dom.NodeList;
/*    */ 
/*    */ class JPEGUtil
/*    */ {
/*    */   static void updateMetadata(IIOMetadata metadata, int dpi)
/*    */     throws IIOInvalidTreeException
/*    */   {
/* 42 */     MetaUtil.debugLogMetadata(metadata, "javax_imageio_jpeg_image_1.0");
/*    */ 
/* 46 */     Element root = (Element)metadata.getAsTree("javax_imageio_jpeg_image_1.0");
/* 47 */     NodeList jvarNodeList = root.getElementsByTagName("JPEGvariety");
/*    */     Element jvarChild;
/* 49 */     if (jvarNodeList.getLength() == 0)
/*    */     {
/* 51 */       Element jvarChild = new IIOMetadataNode("JPEGvariety");
/* 52 */       root.appendChild(jvarChild);
/*    */     }
/*    */     else
/*    */     {
/* 56 */       jvarChild = (Element)jvarNodeList.item(0);
/*    */     }
/*    */ 
/* 59 */     NodeList jfifNodeList = jvarChild.getElementsByTagName("app0JFIF");
/*    */     Element jfifChild;
/* 61 */     if (jfifNodeList.getLength() == 0)
/*    */     {
/* 63 */       Element jfifChild = new IIOMetadataNode("app0JFIF");
/* 64 */       jvarChild.appendChild(jfifChild);
/*    */     }
/*    */     else
/*    */     {
/* 68 */       jfifChild = (Element)jfifNodeList.item(0);
/*    */     }
/* 70 */     if (jfifChild.getAttribute("majorVersion").length() == 0)
/*    */     {
/* 72 */       jfifChild.setAttribute("majorVersion", "1");
/*    */     }
/* 74 */     if (jfifChild.getAttribute("minorVersion").length() == 0)
/*    */     {
/* 76 */       jfifChild.setAttribute("minorVersion", "2");
/*    */     }
/* 78 */     jfifChild.setAttribute("resUnits", "1");
/* 79 */     jfifChild.setAttribute("Xdensity", Integer.toString(dpi));
/* 80 */     jfifChild.setAttribute("Ydensity", Integer.toString(dpi));
/* 81 */     if (jfifChild.getAttribute("thumbWidth").length() == 0)
/*    */     {
/* 83 */       jfifChild.setAttribute("thumbWidth", "0");
/*    */     }
/* 85 */     if (jfifChild.getAttribute("thumbHeight").length() == 0)
/*    */     {
/* 87 */       jfifChild.setAttribute("thumbHeight", "0");
/*    */     }
/* 89 */     metadata.setFromTree("javax_imageio_jpeg_image_1.0", root);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.JPEGUtil
 * JD-Core Version:    0.6.2
 */