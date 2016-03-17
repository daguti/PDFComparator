/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.pdf.PdfTemplate;
/*     */ import com.itextpdf.text.pdf.codec.wmf.InputMeta;
/*     */ import com.itextpdf.text.pdf.codec.wmf.MetaDo;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ 
/*     */ public class ImgWMF extends Image
/*     */ {
/*     */   ImgWMF(Image image)
/*     */   {
/*  70 */     super(image);
/*     */   }
/*     */ 
/*     */   public ImgWMF(URL url)
/*     */     throws BadElementException, IOException
/*     */   {
/*  82 */     super(url);
/*  83 */     processParameters();
/*     */   }
/*     */ 
/*     */   public ImgWMF(String filename)
/*     */     throws BadElementException, MalformedURLException, IOException
/*     */   {
/*  96 */     this(Utilities.toURL(filename));
/*     */   }
/*     */ 
/*     */   public ImgWMF(byte[] img)
/*     */     throws BadElementException, IOException
/*     */   {
/* 108 */     super((URL)null);
/* 109 */     this.rawData = img;
/* 110 */     this.originalData = img;
/* 111 */     processParameters();
/*     */   }
/*     */ 
/*     */   private void processParameters()
/*     */     throws BadElementException, IOException
/*     */   {
/* 121 */     this.type = 35;
/* 122 */     this.originalType = 6;
/* 123 */     InputStream is = null;
/*     */     try
/*     */     {
/*     */       String errorID;
/*     */       String errorID;
/* 126 */       if (this.rawData == null) {
/* 127 */         is = this.url.openStream();
/* 128 */         errorID = this.url.toString();
/*     */       }
/*     */       else {
/* 131 */         is = new ByteArrayInputStream(this.rawData);
/* 132 */         errorID = "Byte array";
/*     */       }
/* 134 */       InputMeta in = new InputMeta(is);
/* 135 */       if (in.readInt() != -1698247209) {
/* 136 */         throw new BadElementException(MessageLocalization.getComposedMessage("1.is.not.a.valid.placeable.windows.metafile", new Object[] { errorID }));
/*     */       }
/* 138 */       in.readWord();
/* 139 */       int left = in.readShort();
/* 140 */       int top = in.readShort();
/* 141 */       int right = in.readShort();
/* 142 */       int bottom = in.readShort();
/* 143 */       int inch = in.readWord();
/* 144 */       this.dpiX = 72;
/* 145 */       this.dpiY = 72;
/* 146 */       this.scaledHeight = ((bottom - top) / inch * 72.0F);
/* 147 */       setTop(this.scaledHeight);
/* 148 */       this.scaledWidth = ((right - left) / inch * 72.0F);
/* 149 */       setRight(this.scaledWidth);
/*     */     }
/*     */     finally {
/* 152 */       if (is != null) {
/* 153 */         is.close();
/*     */       }
/* 155 */       this.plainWidth = getWidth();
/* 156 */       this.plainHeight = getHeight();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void readWMF(PdfTemplate template)
/*     */     throws IOException, DocumentException
/*     */   {
/* 166 */     setTemplateData(template);
/* 167 */     template.setWidth(getWidth());
/* 168 */     template.setHeight(getHeight());
/* 169 */     InputStream is = null;
/*     */     try {
/* 171 */       if (this.rawData == null) {
/* 172 */         is = this.url.openStream();
/*     */       }
/*     */       else {
/* 175 */         is = new ByteArrayInputStream(this.rawData);
/*     */       }
/* 177 */       MetaDo meta = new MetaDo(is, template);
/* 178 */       meta.readAll();
/*     */     }
/*     */     finally {
/* 181 */       if (is != null)
/* 182 */         is.close();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.ImgWMF
 * JD-Core Version:    0.6.2
 */