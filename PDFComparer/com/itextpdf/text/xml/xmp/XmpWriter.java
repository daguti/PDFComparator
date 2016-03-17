/*     */ package com.itextpdf.text.xml.xmp;
/*     */ 
/*     */ import com.itextpdf.text.Version;
/*     */ import com.itextpdf.text.pdf.PdfDate;
/*     */ import com.itextpdf.text.pdf.PdfDictionary;
/*     */ import com.itextpdf.text.pdf.PdfName;
/*     */ import com.itextpdf.text.pdf.PdfObject;
/*     */ import com.itextpdf.text.pdf.PdfString;
/*     */ import com.itextpdf.xmp.XMPException;
/*     */ import com.itextpdf.xmp.XMPMeta;
/*     */ import com.itextpdf.xmp.XMPMetaFactory;
/*     */ import com.itextpdf.xmp.XMPUtils;
/*     */ import com.itextpdf.xmp.options.PropertyOptions;
/*     */ import com.itextpdf.xmp.options.SerializeOptions;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ 
/*     */ public class XmpWriter
/*     */ {
/*     */   public static final String UTF8 = "UTF-8";
/*     */   public static final String UTF16 = "UTF-16";
/*     */   public static final String UTF16BE = "UTF-16BE";
/*     */   public static final String UTF16LE = "UTF-16LE";
/*     */   protected XMPMeta xmpMeta;
/*     */   protected OutputStream outputStream;
/*     */   protected SerializeOptions serializeOptions;
/*     */ 
/*     */   public XmpWriter(OutputStream os, String utfEncoding, int extraSpace)
/*     */     throws IOException
/*     */   {
/*  89 */     this.outputStream = os;
/*  90 */     this.serializeOptions = new SerializeOptions();
/*  91 */     if (("UTF-16BE".equals(utfEncoding)) || ("UTF-16".equals(utfEncoding)))
/*  92 */       this.serializeOptions.setEncodeUTF16BE(true);
/*  93 */     else if ("UTF-16LE".equals(utfEncoding))
/*  94 */       this.serializeOptions.setEncodeUTF16LE(true);
/*  95 */     this.serializeOptions.setPadding(extraSpace);
/*  96 */     this.xmpMeta = XMPMetaFactory.create();
/*  97 */     this.xmpMeta.setObjectName("xmpmeta");
/*  98 */     this.xmpMeta.setObjectName("");
/*     */     try {
/* 100 */       this.xmpMeta.setProperty("http://purl.org/dc/elements/1.1/", "format", "application/pdf");
/* 101 */       this.xmpMeta.setProperty("http://ns.adobe.com/pdf/1.3/", "Producer", Version.getInstance().getVersion());
/*     */     }
/*     */     catch (XMPException xmpExc)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public XmpWriter(OutputStream os)
/*     */     throws IOException
/*     */   {
/* 111 */     this(os, "UTF-8", 2000);
/*     */   }
/*     */ 
/*     */   public XmpWriter(OutputStream os, PdfDictionary info)
/*     */     throws IOException
/*     */   {
/* 120 */     this(os);
/* 121 */     if (info != null)
/*     */     {
/* 125 */       for (PdfName pdfName : info.getKeys()) {
/* 126 */         PdfName key = pdfName;
/* 127 */         PdfObject obj = info.get(key);
/* 128 */         if ((obj != null) && 
/* 130 */           (obj.isString()))
/*     */         {
/* 132 */           String value = ((PdfString)obj).toUnicodeString();
/*     */           try {
/* 134 */             addDocInfoProperty(key, value);
/*     */           } catch (XMPException xmpExc) {
/* 136 */             throw new IOException(xmpExc.getMessage());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public XmpWriter(OutputStream os, Map<String, String> info)
/*     */     throws IOException
/*     */   {
/* 149 */     this(os);
/* 150 */     if (info != null)
/*     */     {
/* 153 */       for (Map.Entry entry : info.entrySet()) {
/* 154 */         String key = (String)entry.getKey();
/* 155 */         String value = (String)entry.getValue();
/* 156 */         if (value != null)
/*     */           try
/*     */           {
/* 159 */             addDocInfoProperty(key, value);
/*     */           } catch (XMPException xmpExc) {
/* 161 */             throw new IOException(xmpExc.getMessage());
/*     */           }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public XMPMeta getXmpMeta() {
/* 168 */     return this.xmpMeta;
/*     */   }
/*     */ 
/*     */   public void setReadOnly()
/*     */   {
/* 173 */     this.serializeOptions.setReadOnlyPacket(true);
/*     */   }
/*     */ 
/*     */   public void setAbout(String about)
/*     */   {
/* 180 */     this.xmpMeta.setObjectName(about);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void addRdfDescription(String xmlns, String content)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 192 */       String str = "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"><rdf:Description rdf:about=\"" + this.xmpMeta.getObjectName() + "\" " + xmlns + ">" + content + "</rdf:Description></rdf:RDF>\n";
/*     */ 
/* 199 */       XMPMeta extMeta = XMPMetaFactory.parseFromString(str);
/* 200 */       XMPUtils.appendProperties(extMeta, this.xmpMeta, true, true);
/*     */     } catch (XMPException xmpExc) {
/* 202 */       throw new IOException(xmpExc.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void addRdfDescription(XmpSchema s)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 214 */       String str = "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"><rdf:Description rdf:about=\"" + this.xmpMeta.getObjectName() + "\" " + s.getXmlns() + ">" + s.toString() + "</rdf:Description></rdf:RDF>\n";
/*     */ 
/* 221 */       XMPMeta extMeta = XMPMetaFactory.parseFromString(str);
/* 222 */       XMPUtils.appendProperties(extMeta, this.xmpMeta, true, true);
/*     */     } catch (XMPException xmpExc) {
/* 224 */       throw new IOException(xmpExc.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setProperty(String schemaNS, String propName, Object value)
/*     */     throws XMPException
/*     */   {
/* 240 */     this.xmpMeta.setProperty(schemaNS, propName, value);
/*     */   }
/*     */ 
/*     */   public void appendArrayItem(String schemaNS, String arrayName, String value)
/*     */     throws XMPException
/*     */   {
/* 255 */     this.xmpMeta.appendArrayItem(schemaNS, arrayName, new PropertyOptions(512), value, null);
/*     */   }
/*     */ 
/*     */   public void appendOrderedArrayItem(String schemaNS, String arrayName, String value)
/*     */     throws XMPException
/*     */   {
/* 270 */     this.xmpMeta.appendArrayItem(schemaNS, arrayName, new PropertyOptions(1024), value, null);
/*     */   }
/*     */ 
/*     */   public void appendAlternateArrayItem(String schemaNS, String arrayName, String value)
/*     */     throws XMPException
/*     */   {
/* 285 */     this.xmpMeta.appendArrayItem(schemaNS, arrayName, new PropertyOptions(2048), value, null);
/*     */   }
/*     */ 
/*     */   public void serialize(OutputStream externalOutputStream)
/*     */     throws XMPException
/*     */   {
/* 293 */     XMPMetaFactory.serialize(this.xmpMeta, externalOutputStream, this.serializeOptions);
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 301 */     if (this.outputStream == null)
/* 302 */       return;
/*     */     try {
/* 304 */       XMPMetaFactory.serialize(this.xmpMeta, this.outputStream, this.serializeOptions);
/* 305 */       this.outputStream = null;
/*     */     } catch (XMPException xmpExc) {
/* 307 */       throw new IOException(xmpExc.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addDocInfoProperty(Object key, String value) throws XMPException {
/* 312 */     if ((key instanceof String))
/* 313 */       key = new PdfName((String)key);
/* 314 */     if (PdfName.TITLE.equals(key)) {
/* 315 */       this.xmpMeta.setLocalizedText("http://purl.org/dc/elements/1.1/", "title", "x-default", "x-default", value);
/* 316 */     } else if (PdfName.AUTHOR.equals(key)) {
/* 317 */       this.xmpMeta.appendArrayItem("http://purl.org/dc/elements/1.1/", "creator", new PropertyOptions(1024), value, null);
/* 318 */     } else if (PdfName.SUBJECT.equals(key)) {
/* 319 */       this.xmpMeta.setLocalizedText("http://purl.org/dc/elements/1.1/", "description", "x-default", "x-default", value);
/* 320 */     } else if (PdfName.KEYWORDS.equals(key)) {
/* 321 */       for (String v : value.split(",|;"))
/* 322 */         if (v.trim().length() > 0)
/* 323 */           this.xmpMeta.appendArrayItem("http://purl.org/dc/elements/1.1/", "subject", new PropertyOptions(512), v.trim(), null);
/* 324 */       this.xmpMeta.setProperty("http://ns.adobe.com/pdf/1.3/", "Keywords", value);
/* 325 */     } else if (PdfName.PRODUCER.equals(key)) {
/* 326 */       this.xmpMeta.setProperty("http://ns.adobe.com/pdf/1.3/", "Producer", value);
/* 327 */     } else if (PdfName.CREATOR.equals(key)) {
/* 328 */       this.xmpMeta.setProperty("http://ns.adobe.com/xap/1.0/", "CreatorTool", value);
/* 329 */     } else if (PdfName.CREATIONDATE.equals(key)) {
/* 330 */       this.xmpMeta.setProperty("http://ns.adobe.com/xap/1.0/", "CreateDate", PdfDate.getW3CDate(value));
/* 331 */     } else if (PdfName.MODDATE.equals(key)) {
/* 332 */       this.xmpMeta.setProperty("http://ns.adobe.com/xap/1.0/", "ModifyDate", PdfDate.getW3CDate(value));
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.xml.xmp.XmpWriter
 * JD-Core Version:    0.6.2
 */