/*     */ package com.itextpdf.text.xml.xmp;
/*     */ 
/*     */ @Deprecated
/*     */ public class XmpBasicSchema extends XmpSchema
/*     */ {
/*     */   private static final long serialVersionUID = -2416613941622479298L;
/*     */   public static final String DEFAULT_XPATH_ID = "xmp";
/*     */   public static final String DEFAULT_XPATH_URI = "http://ns.adobe.com/xap/1.0/";
/*     */   public static final String ADVISORY = "xmp:Advisory";
/*     */   public static final String BASEURL = "xmp:BaseURL";
/*     */   public static final String CREATEDATE = "xmp:CreateDate";
/*     */   public static final String CREATORTOOL = "xmp:CreatorTool";
/*     */   public static final String IDENTIFIER = "xmp:Identifier";
/*     */   public static final String METADATADATE = "xmp:MetadataDate";
/*     */   public static final String MODIFYDATE = "xmp:ModifyDate";
/*     */   public static final String NICKNAME = "xmp:Nickname";
/*     */   public static final String THUMBNAILS = "xmp:Thumbnails";
/*     */ 
/*     */   public XmpBasicSchema()
/*     */   {
/*  81 */     super("xmlns:xmp=\"http://ns.adobe.com/xap/1.0/\"");
/*     */   }
/*     */ 
/*     */   public void addCreatorTool(String creator)
/*     */   {
/*  89 */     setProperty("xmp:CreatorTool", creator);
/*     */   }
/*     */ 
/*     */   public void addCreateDate(String date)
/*     */   {
/*  97 */     setProperty("xmp:CreateDate", date);
/*     */   }
/*     */ 
/*     */   public void addModDate(String date)
/*     */   {
/* 105 */     setProperty("xmp:ModifyDate", date);
/*     */   }
/*     */ 
/*     */   public void addMetaDataDate(String date)
/*     */   {
/* 113 */     setProperty("xmp:MetadataDate", date);
/*     */   }
/*     */ 
/*     */   public void addIdentifiers(String[] id)
/*     */   {
/* 120 */     XmpArray array = new XmpArray("rdf:Bag");
/* 121 */     for (int i = 0; i < id.length; i++) {
/* 122 */       array.add(id[i]);
/*     */     }
/* 124 */     setProperty("xmp:Identifier", array);
/*     */   }
/*     */ 
/*     */   public void addNickname(String name)
/*     */   {
/* 131 */     setProperty("xmp:Nickname", name);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.xml.xmp.XmpBasicSchema
 * JD-Core Version:    0.6.2
 */