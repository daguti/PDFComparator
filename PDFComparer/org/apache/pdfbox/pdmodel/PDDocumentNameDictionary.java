/*     */ package org.apache.pdfbox.pdmodel;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ 
/*     */ public class PDDocumentNameDictionary
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSDictionary nameDictionary;
/*     */   private PDDocumentCatalog catalog;
/*     */ 
/*     */   public PDDocumentNameDictionary(PDDocumentCatalog cat)
/*     */   {
/*  42 */     COSBase names = cat.getCOSDictionary().getDictionaryObject(COSName.NAMES);
/*  43 */     if (names != null)
/*     */     {
/*  45 */       this.nameDictionary = ((COSDictionary)names);
/*     */     }
/*     */     else
/*     */     {
/*  49 */       this.nameDictionary = new COSDictionary();
/*  50 */       cat.getCOSDictionary().setItem(COSName.NAMES, this.nameDictionary);
/*     */     }
/*  52 */     this.catalog = cat;
/*     */   }
/*     */ 
/*     */   public PDDocumentNameDictionary(PDDocumentCatalog cat, COSDictionary names)
/*     */   {
/*  63 */     this.catalog = cat;
/*  64 */     this.nameDictionary = names;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  74 */     return this.nameDictionary;
/*     */   }
/*     */ 
/*     */   public COSDictionary getCOSDictionary()
/*     */   {
/*  84 */     return this.nameDictionary;
/*     */   }
/*     */ 
/*     */   public PDDestinationNameTreeNode getDests()
/*     */   {
/*  95 */     PDDestinationNameTreeNode dests = null;
/*     */ 
/*  97 */     COSDictionary dic = (COSDictionary)this.nameDictionary.getDictionaryObject(COSName.DESTS);
/*     */ 
/* 101 */     if (dic == null)
/*     */     {
/* 103 */       dic = (COSDictionary)this.catalog.getCOSDictionary().getDictionaryObject(COSName.DESTS);
/*     */     }
/*     */ 
/* 106 */     if (dic != null)
/*     */     {
/* 108 */       dests = new PDDestinationNameTreeNode(dic);
/*     */     }
/*     */ 
/* 112 */     return dests;
/*     */   }
/*     */ 
/*     */   public void setDests(PDDestinationNameTreeNode dests)
/*     */   {
/* 122 */     this.nameDictionary.setItem(COSName.DESTS, dests);
/*     */ 
/* 128 */     this.catalog.getCOSDictionary().setItem(COSName.DESTS, (COSObjectable)null);
/*     */   }
/*     */ 
/*     */   public PDEmbeddedFilesNameTreeNode getEmbeddedFiles()
/*     */   {
/* 139 */     PDEmbeddedFilesNameTreeNode retval = null;
/*     */ 
/* 141 */     COSDictionary dic = (COSDictionary)this.nameDictionary.getDictionaryObject(COSName.EMBEDDED_FILES);
/*     */ 
/* 143 */     if (dic != null)
/*     */     {
/* 145 */       retval = new PDEmbeddedFilesNameTreeNode(dic);
/*     */     }
/*     */ 
/* 148 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setEmbeddedFiles(PDEmbeddedFilesNameTreeNode ef)
/*     */   {
/* 158 */     this.nameDictionary.setItem(COSName.EMBEDDED_FILES, ef);
/*     */   }
/*     */ 
/*     */   public PDJavascriptNameTreeNode getJavaScript()
/*     */   {
/* 168 */     PDJavascriptNameTreeNode retval = null;
/*     */ 
/* 170 */     COSDictionary dic = (COSDictionary)this.nameDictionary.getDictionaryObject(COSName.JAVA_SCRIPT);
/*     */ 
/* 172 */     if (dic != null)
/*     */     {
/* 174 */       retval = new PDJavascriptNameTreeNode(dic);
/*     */     }
/*     */ 
/* 177 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setJavascript(PDJavascriptNameTreeNode js)
/*     */   {
/* 187 */     this.nameDictionary.setItem(COSName.JAVA_SCRIPT, js);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.PDDocumentNameDictionary
 * JD-Core Version:    0.6.2
 */