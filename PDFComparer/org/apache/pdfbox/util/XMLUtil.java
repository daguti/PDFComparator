/*    */ package org.apache.pdfbox.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import javax.xml.parsers.DocumentBuilder;
/*    */ import javax.xml.parsers.DocumentBuilderFactory;
/*    */ import org.w3c.dom.Document;
/*    */ import org.w3c.dom.Element;
/*    */ import org.w3c.dom.Node;
/*    */ import org.w3c.dom.NodeList;
/*    */ import org.w3c.dom.Text;
/*    */ 
/*    */ public class XMLUtil
/*    */ {
/*    */   public static Document parse(InputStream is)
/*    */     throws IOException
/*    */   {
/*    */     try
/*    */     {
/* 58 */       DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
/* 59 */       DocumentBuilder builder = builderFactory.newDocumentBuilder();
/* 60 */       return builder.parse(is);
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 64 */       IOException thrown = new IOException(e.getMessage());
/* 65 */       throw thrown;
/*    */     }
/*    */   }
/*    */ 
/*    */   public static String getNodeValue(Element node)
/*    */   {
/* 77 */     String retval = "";
/* 78 */     NodeList children = node.getChildNodes();
/* 79 */     for (int i = 0; i < children.getLength(); i++)
/*    */     {
/* 81 */       Node next = children.item(i);
/* 82 */       if ((next instanceof Text))
/*    */       {
/* 84 */         retval = next.getNodeValue();
/*    */       }
/*    */     }
/* 87 */     return retval;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.XMLUtil
 * JD-Core Version:    0.6.2
 */