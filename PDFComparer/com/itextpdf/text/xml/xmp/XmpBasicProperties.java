/*     */ package com.itextpdf.text.xml.xmp;
/*     */ 
/*     */ import com.itextpdf.xmp.XMPException;
/*     */ import com.itextpdf.xmp.XMPMeta;
/*     */ import com.itextpdf.xmp.XMPUtils;
/*     */ import com.itextpdf.xmp.options.PropertyOptions;
/*     */ 
/*     */ public class XmpBasicProperties
/*     */ {
/*     */   public static final String ADVISORY = "Advisory";
/*     */   public static final String BASEURL = "BaseURL";
/*     */   public static final String CREATEDATE = "CreateDate";
/*     */   public static final String CREATORTOOL = "CreatorTool";
/*     */   public static final String IDENTIFIER = "Identifier";
/*     */   public static final String METADATADATE = "MetadataDate";
/*     */   public static final String MODIFYDATE = "ModifyDate";
/*     */   public static final String NICKNAME = "Nickname";
/*     */   public static final String THUMBNAILS = "Thumbnails";
/*     */ 
/*     */   public static void setCreatorTool(XMPMeta xmpMeta, String creator)
/*     */     throws XMPException
/*     */   {
/*  80 */     xmpMeta.setProperty("http://ns.adobe.com/xap/1.0/", "CreatorTool", creator);
/*     */   }
/*     */ 
/*     */   public static void setCreateDate(XMPMeta xmpMeta, String date)
/*     */     throws XMPException
/*     */   {
/*  90 */     xmpMeta.setProperty("http://ns.adobe.com/xap/1.0/", "CreateDate", date);
/*     */   }
/*     */ 
/*     */   public static void setModDate(XMPMeta xmpMeta, String date)
/*     */     throws XMPException
/*     */   {
/* 100 */     xmpMeta.setProperty("http://ns.adobe.com/xap/1.0/", "ModifyDate", date);
/*     */   }
/*     */ 
/*     */   public static void setMetaDataDate(XMPMeta xmpMeta, String date)
/*     */     throws XMPException
/*     */   {
/* 110 */     xmpMeta.setProperty("http://ns.adobe.com/xap/1.0/", "MetadataDate", date);
/*     */   }
/*     */ 
/*     */   public static void setIdentifiers(XMPMeta xmpMeta, String[] id)
/*     */     throws XMPException
/*     */   {
/* 119 */     XMPUtils.removeProperties(xmpMeta, "http://purl.org/dc/elements/1.1/", "Identifier", true, true);
/* 120 */     for (int i = 0; i < id.length; i++)
/* 121 */       xmpMeta.appendArrayItem("http://purl.org/dc/elements/1.1/", "Identifier", new PropertyOptions(512), id[i], null);
/*     */   }
/*     */ 
/*     */   public static void setNickname(XMPMeta xmpMeta, String name)
/*     */     throws XMPException
/*     */   {
/* 131 */     xmpMeta.setProperty("http://ns.adobe.com/xap/1.0/", "Nickname", name);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.xml.xmp.XmpBasicProperties
 * JD-Core Version:    0.6.2
 */