/*     */ package org.apache.pdfbox.pdmodel.common;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import org.apache.jempbox.xmp.XMPMetadata;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ 
/*     */ public class PDMetadata extends PDStream
/*     */ {
/*     */   public PDMetadata(PDDocument document)
/*     */   {
/*  46 */     super(document);
/*  47 */     getStream().setName("Type", "Metadata");
/*  48 */     getStream().setName("Subtype", "XML");
/*     */   }
/*     */ 
/*     */   public PDMetadata(PDDocument doc, InputStream str, boolean filtered)
/*     */     throws IOException
/*     */   {
/*  62 */     super(doc, str, filtered);
/*  63 */     getStream().setName("Type", "Metadata");
/*  64 */     getStream().setName("Subtype", "XML");
/*     */   }
/*     */ 
/*     */   public PDMetadata(COSStream str)
/*     */   {
/*  74 */     super(str);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public XMPMetadata exportXMPMetadata()
/*     */     throws IOException
/*     */   {
/*  90 */     return XMPMetadata.load(createInputStream());
/*     */   }
/*     */ 
/*     */   public void importXMPMetadata(byte[] xmp)
/*     */     throws IOException
/*     */   {
/* 103 */     OutputStream os = createOutputStream();
/* 104 */     os.write(xmp);
/* 105 */     os.close();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void importXMPMetadata(XMPMetadata xmp)
/*     */     throws IOException, TransformerException
/*     */   {
/* 121 */     xmp.save(createOutputStream());
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.PDMetadata
 * JD-Core Version:    0.6.2
 */