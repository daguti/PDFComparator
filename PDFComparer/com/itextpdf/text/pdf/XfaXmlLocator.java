/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import com.itextpdf.text.pdf.security.XmlLocator;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerConfigurationException;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import org.w3c.dom.Document;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class XfaXmlLocator
/*     */   implements XmlLocator
/*     */ {
/*     */   private PdfStamper stamper;
/*     */   private XfaForm xfaForm;
/*     */   private String encoding;
/*     */ 
/*     */   public XfaXmlLocator(PdfStamper stamper)
/*     */     throws DocumentException, IOException
/*     */   {
/*  68 */     this.stamper = stamper;
/*     */     try {
/*  70 */       createXfaForm();
/*     */     } catch (ParserConfigurationException e) {
/*  72 */       throw new DocumentException(e);
/*     */     } catch (SAXException e) {
/*  74 */       throw new DocumentException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void createXfaForm()
/*     */     throws ParserConfigurationException, SAXException, IOException
/*     */   {
/*  83 */     this.xfaForm = new XfaForm(this.stamper.getReader());
/*     */   }
/*     */ 
/*     */   public Document getDocument()
/*     */   {
/*  90 */     return this.xfaForm.getDomDocument();
/*     */   }
/*     */ 
/*     */   public void setDocument(Document document)
/*     */     throws IOException, DocumentException
/*     */   {
/*     */     try
/*     */     {
/* 102 */       ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
/* 103 */       TransformerFactory tf = TransformerFactory.newInstance();
/*     */ 
/* 105 */       Transformer trans = tf.newTransformer();
/*     */ 
/* 108 */       trans.transform(new DOMSource(document), new StreamResult(outputStream));
/*     */ 
/* 110 */       PdfIndirectReference iref = this.stamper.getWriter().addToBody(new PdfStream(outputStream.toByteArray())).getIndirectReference();
/*     */ 
/* 112 */       this.stamper.getReader().getAcroForm().put(PdfName.XFA, iref);
/*     */     } catch (TransformerConfigurationException e) {
/* 114 */       throw new DocumentException(e);
/*     */     } catch (TransformerException e) {
/* 116 */       throw new DocumentException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getEncoding() {
/* 121 */     return this.encoding;
/*     */   }
/*     */ 
/*     */   public void setEncoding(String encoding) {
/* 125 */     this.encoding = encoding;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.XfaXmlLocator
 * JD-Core Version:    0.6.2
 */