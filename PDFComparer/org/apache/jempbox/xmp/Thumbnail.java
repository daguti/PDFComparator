/*     */ package org.apache.jempbox.xmp;
/*     */ 
/*     */ import org.apache.jempbox.impl.XMLUtil;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ public class Thumbnail
/*     */ {
/*     */   public static final String FORMAT_JPEG = "JPEG";
/*  39 */   protected Element parent = null;
/*     */ 
/*     */   public Thumbnail(XMPMetadata metadata)
/*     */   {
/*  48 */     this(metadata.xmpDocument.createElement("rdf:li"));
/*     */   }
/*     */ 
/*     */   public Thumbnail(Element parentElement)
/*     */   {
/*  58 */     this.parent = parentElement;
/*  59 */     this.parent.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xapGImg", "http://ns.adobe.com/xap/1.0/g/img/");
/*     */   }
/*     */ 
/*     */   public Element getElement()
/*     */   {
/*  72 */     return this.parent;
/*     */   }
/*     */ 
/*     */   public Integer getHeight()
/*     */   {
/*  82 */     return XMLUtil.getIntValue(this.parent, "xapGImg:height");
/*     */   }
/*     */ 
/*     */   public void setHeight(Integer height)
/*     */   {
/*  92 */     XMLUtil.setIntValue(this.parent, "xapGImg:height", height);
/*     */   }
/*     */ 
/*     */   public Integer getWidth()
/*     */   {
/* 102 */     return XMLUtil.getIntValue(this.parent, "xapGImg:width");
/*     */   }
/*     */ 
/*     */   public void setWidth(Integer width)
/*     */   {
/* 112 */     XMLUtil.setIntValue(this.parent, "xapGImg:width", width);
/*     */   }
/*     */ 
/*     */   public void setFormat(String format)
/*     */   {
/* 122 */     XMLUtil.setStringValue(this.parent, "xapGImg:format", format);
/*     */   }
/*     */ 
/*     */   public String getFormat()
/*     */   {
/* 132 */     return XMLUtil.getStringValue(this.parent, "xapGImg:format");
/*     */   }
/*     */ 
/*     */   public void setImage(String image)
/*     */   {
/* 142 */     XMLUtil.setStringValue(this.parent, "xapGImg:image", image);
/*     */   }
/*     */ 
/*     */   public String getImage()
/*     */   {
/* 152 */     return XMLUtil.getStringValue(this.parent, "xapGImg:image");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.jempbox.xmp.Thumbnail
 * JD-Core Version:    0.6.2
 */