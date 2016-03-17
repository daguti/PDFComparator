/*     */ package org.apache.pdfbox.pdmodel.fdf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ public class FDFCatalog
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSDictionary catalog;
/*     */ 
/*     */   public FDFCatalog()
/*     */   {
/*  46 */     this.catalog = new COSDictionary();
/*     */   }
/*     */ 
/*     */   public FDFCatalog(COSDictionary cat)
/*     */   {
/*  56 */     this.catalog = cat;
/*     */   }
/*     */ 
/*     */   public FDFCatalog(Element element)
/*     */     throws IOException
/*     */   {
/*  67 */     this();
/*  68 */     FDFDictionary fdfDict = new FDFDictionary(element);
/*  69 */     setFDF(fdfDict);
/*     */   }
/*     */ 
/*     */   public void writeXML(Writer output)
/*     */     throws IOException
/*     */   {
/*  81 */     FDFDictionary fdf = getFDF();
/*  82 */     fdf.writeXML(output);
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  92 */     return this.catalog;
/*     */   }
/*     */ 
/*     */   public COSDictionary getCOSDictionary()
/*     */   {
/* 102 */     return this.catalog;
/*     */   }
/*     */ 
/*     */   public String getVersion()
/*     */   {
/* 112 */     return this.catalog.getNameAsString("Version");
/*     */   }
/*     */ 
/*     */   public void setVersion(String version)
/*     */   {
/* 122 */     this.catalog.setName("Version", version);
/*     */   }
/*     */ 
/*     */   public FDFDictionary getFDF()
/*     */   {
/* 132 */     COSDictionary fdf = (COSDictionary)this.catalog.getDictionaryObject("FDF");
/* 133 */     FDFDictionary retval = null;
/* 134 */     if (fdf != null)
/*     */     {
/* 136 */       retval = new FDFDictionary(fdf);
/*     */     }
/*     */     else
/*     */     {
/* 140 */       retval = new FDFDictionary();
/* 141 */       setFDF(retval);
/*     */     }
/* 143 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setFDF(FDFDictionary fdf)
/*     */   {
/* 153 */     this.catalog.setItem("FDF", fdf);
/*     */   }
/*     */ 
/*     */   public PDSignature getSignature()
/*     */   {
/* 163 */     PDSignature signature = null;
/* 164 */     COSDictionary sig = (COSDictionary)this.catalog.getDictionaryObject("Sig");
/* 165 */     if (sig != null)
/*     */     {
/* 167 */       signature = new PDSignature(sig);
/*     */     }
/* 169 */     return signature;
/*     */   }
/*     */ 
/*     */   public void setSignature(PDSignature sig)
/*     */   {
/* 179 */     this.catalog.setItem("Sig", sig);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.fdf.FDFCatalog
 * JD-Core Version:    0.6.2
 */