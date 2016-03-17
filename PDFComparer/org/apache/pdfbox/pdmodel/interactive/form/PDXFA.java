/*     */ package org.apache.pdfbox.pdmodel.interactive.form;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.w3c.dom.Document;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class PDXFA
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSBase xfa;
/*     */ 
/*     */   public PDXFA(COSBase xfaBase)
/*     */   {
/*  52 */     this.xfa = xfaBase;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  60 */     return this.xfa;
/*     */   }
/*     */ 
/*     */   public byte[] getBytes()
/*     */     throws IOException
/*     */   {
/*  83 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*  84 */     InputStream is = null;
/*  85 */     byte[] xfaBytes = null;
/*     */     try
/*     */     {
/*  90 */       if ((getCOSObject() instanceof COSArray))
/*     */       {
/*  92 */         xfaBytes = new byte[1024];
/*  93 */         COSArray cosArray = (COSArray)getCOSObject();
/*  94 */         for (int i = 1; i < cosArray.size(); i += 2)
/*     */         {
/*  96 */           COSBase cosObj = cosArray.getObject(i);
/*  97 */           if ((cosObj instanceof COSStream))
/*     */           {
/*  99 */             is = ((COSStream)cosObj).getUnfilteredStream();
/* 100 */             int nRead = 0;
/* 101 */             while ((nRead = is.read(xfaBytes, 0, xfaBytes.length)) != -1)
/*     */             {
/* 103 */               baos.write(xfaBytes, 0, nRead);
/*     */             }
/* 105 */             baos.flush();
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/* 110 */       else if ((this.xfa.getCOSObject() instanceof COSStream))
/*     */       {
/* 112 */         xfaBytes = new byte[1024];
/* 113 */         is = ((COSStream)this.xfa.getCOSObject()).getUnfilteredStream();
/* 114 */         int nRead = 0;
/* 115 */         while ((nRead = is.read(xfaBytes, 0, xfaBytes.length)) != -1)
/*     */         {
/* 117 */           baos.write(xfaBytes, 0, nRead);
/*     */         }
/* 119 */         baos.flush();
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/* 124 */       if (is != null)
/*     */       {
/* 126 */         is.close();
/*     */       }
/* 128 */       if (baos != null)
/*     */       {
/* 130 */         baos.close();
/*     */       }
/*     */     }
/* 133 */     return baos.toByteArray();
/*     */   }
/*     */ 
/*     */   public Document getDocument()
/*     */     throws ParserConfigurationException, SAXException, IOException
/*     */   {
/* 150 */     DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/* 151 */     factory.setNamespaceAware(true);
/* 152 */     DocumentBuilder builder = factory.newDocumentBuilder();
/* 153 */     Document xfaDocument = builder.parse(new ByteArrayInputStream(getBytes()));
/* 154 */     return xfaDocument;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.form.PDXFA
 * JD-Core Version:    0.6.2
 */