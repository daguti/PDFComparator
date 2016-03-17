/*     */ package org.apache.pdfbox.pdmodel.interactive.digitalsignature;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.pdfparser.VisualSignatureParser;
/*     */ import org.apache.pdfbox.pdmodel.interactive.digitalsignature.visible.PDVisibleSigProperties;
/*     */ 
/*     */ public class SignatureOptions
/*     */ {
/*     */   private COSDocument visualSignature;
/*     */   private int preferedSignatureSize;
/*     */   private int pageNo;
/*     */ 
/*     */   public void setPage(int pageNo)
/*     */   {
/*  43 */     this.pageNo = pageNo;
/*     */   }
/*     */ 
/*     */   public int getPage()
/*     */   {
/*  53 */     return this.pageNo;
/*     */   }
/*     */ 
/*     */   public void setVisualSignature(InputStream is)
/*     */     throws IOException
/*     */   {
/*  65 */     VisualSignatureParser visParser = new VisualSignatureParser(is);
/*  66 */     visParser.parse();
/*  67 */     this.visualSignature = visParser.getDocument();
/*     */   }
/*     */ 
/*     */   public void setVisualSignature(PDVisibleSigProperties visSignatureProperties)
/*     */     throws IOException
/*     */   {
/*  81 */     setVisualSignature(visSignatureProperties.getVisibleSignature());
/*     */   }
/*     */ 
/*     */   public COSDocument getVisualSignature()
/*     */   {
/*  91 */     return this.visualSignature;
/*     */   }
/*     */ 
/*     */   public int getPreferedSignatureSize()
/*     */   {
/* 101 */     return this.preferedSignatureSize;
/*     */   }
/*     */ 
/*     */   public void setPreferedSignatureSize(int size)
/*     */   {
/* 111 */     if (size > 0)
/*     */     {
/* 113 */       this.preferedSignatureSize = size;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureOptions
 * JD-Core Version:    0.6.2
 */