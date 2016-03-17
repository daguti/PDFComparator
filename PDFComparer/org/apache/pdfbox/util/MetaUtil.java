/*    */ package org.apache.pdfbox.util;
/*    */ 
/*    */ import java.io.StringWriter;
/*    */ import javax.imageio.metadata.IIOMetadata;
/*    */ import javax.imageio.metadata.IIOMetadataNode;
/*    */ import javax.xml.transform.Transformer;
/*    */ import javax.xml.transform.TransformerException;
/*    */ import javax.xml.transform.TransformerFactory;
/*    */ import javax.xml.transform.dom.DOMSource;
/*    */ import javax.xml.transform.stream.StreamResult;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ 
/*    */ public class MetaUtil
/*    */ {
/* 37 */   private static final Log LOG = LogFactory.getLog(TIFFUtil.class);
/*    */   static final String SUN_TIFF_FORMAT = "com_sun_media_imageio_plugins_tiff_image_1.0";
/*    */   static final String JPEG_NATIVE_FORMAT = "javax_imageio_jpeg_image_1.0";
/*    */   static final String STANDARD_METADATA_FORMAT = "javax_imageio_1.0";
/*    */ 
/*    */   static void debugLogMetadata(IIOMetadata metadata, String format)
/*    */   {
/* 47 */     if (!LOG.isDebugEnabled())
/*    */     {
/* 49 */       return;
/*    */     }
/*    */ 
/* 54 */     IIOMetadataNode root = (IIOMetadataNode)metadata.getAsTree(format);
/*    */     try
/*    */     {
/* 57 */       StringWriter xmlStringWriter = new StringWriter();
/* 58 */       StreamResult streamResult = new StreamResult(xmlStringWriter);
/* 59 */       Transformer transformer = TransformerFactory.newInstance().newTransformer();
/*    */ 
/* 61 */       transformer.setOutputProperty("indent", "yes");
/* 62 */       transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
/* 63 */       DOMSource domSource = new DOMSource(root);
/* 64 */       transformer.transform(domSource, streamResult);
/* 65 */       LOG.debug("\n" + xmlStringWriter);
/*    */     }
/*    */     catch (IllegalArgumentException ex)
/*    */     {
/* 69 */       LOG.error(ex, ex);
/*    */     }
/*    */     catch (TransformerException ex)
/*    */     {
/* 73 */       LOG.error(ex, ex);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.MetaUtil
 * JD-Core Version:    0.6.2
 */