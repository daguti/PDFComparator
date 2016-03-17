/*    */ package com.itextpdf.text.xml.xmp;
/*    */ 
/*    */ import com.itextpdf.text.xml.XMLUtil;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ @Deprecated
/*    */ public class XmpArray extends ArrayList<String>
/*    */ {
/*    */   private static final long serialVersionUID = 5722854116328732742L;
/*    */   public static final String UNORDERED = "rdf:Bag";
/*    */   public static final String ORDERED = "rdf:Seq";
/*    */   public static final String ALTERNATIVE = "rdf:Alt";
/*    */   protected String type;
/*    */ 
/*    */   public XmpArray(String type)
/*    */   {
/* 73 */     this.type = type;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 82 */     StringBuffer buf = new StringBuffer("<");
/* 83 */     buf.append(this.type);
/* 84 */     buf.append('>');
/*    */ 
/* 86 */     for (String string : this) {
/* 87 */       String s = string;
/* 88 */       buf.append("<rdf:li>");
/* 89 */       buf.append(XMLUtil.escapeXML(s, false));
/* 90 */       buf.append("</rdf:li>");
/*    */     }
/* 92 */     buf.append("</");
/* 93 */     buf.append(this.type);
/* 94 */     buf.append('>');
/* 95 */     return buf.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.xml.xmp.XmpArray
 * JD-Core Version:    0.6.2
 */