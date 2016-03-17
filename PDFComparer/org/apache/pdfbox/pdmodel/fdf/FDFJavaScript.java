/*     */ package org.apache.pdfbox.pdmodel.fdf;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.common.COSArrayList;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.pdmodel.common.PDNamedTextStream;
/*     */ import org.apache.pdfbox.pdmodel.common.PDTextStream;
/*     */ 
/*     */ public class FDFJavaScript
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSDictionary js;
/*     */ 
/*     */   public FDFJavaScript()
/*     */   {
/*  47 */     this.js = new COSDictionary();
/*     */   }
/*     */ 
/*     */   public FDFJavaScript(COSDictionary javaScript)
/*     */   {
/*  57 */     this.js = javaScript;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  67 */     return this.js;
/*     */   }
/*     */ 
/*     */   public COSDictionary getCOSDictionary()
/*     */   {
/*  77 */     return this.js;
/*     */   }
/*     */ 
/*     */   public PDTextStream getBefore()
/*     */   {
/*  87 */     return PDTextStream.createTextStream(this.js.getDictionaryObject("Before"));
/*     */   }
/*     */ 
/*     */   public void setBefore(PDTextStream before)
/*     */   {
/*  97 */     this.js.setItem("Before", before);
/*     */   }
/*     */ 
/*     */   public PDTextStream getAfter()
/*     */   {
/* 107 */     return PDTextStream.createTextStream(this.js.getDictionaryObject("After"));
/*     */   }
/*     */ 
/*     */   public void setAfter(PDTextStream after)
/*     */   {
/* 117 */     this.js.setItem("After", after);
/*     */   }
/*     */ 
/*     */   public List getNamedJavaScripts()
/*     */   {
/* 129 */     COSArray array = (COSArray)this.js.getDictionaryObject("Doc");
/* 130 */     List namedStreams = new ArrayList();
/* 131 */     if (array == null)
/*     */     {
/* 133 */       array = new COSArray();
/* 134 */       this.js.setItem("Doc", array);
/*     */     }
/* 136 */     for (int i = 0; i < array.size(); i++)
/*     */     {
/* 138 */       COSName name = (COSName)array.get(i);
/* 139 */       i++;
/* 140 */       COSBase stream = array.get(i);
/* 141 */       PDNamedTextStream namedStream = new PDNamedTextStream(name, stream);
/* 142 */       namedStreams.add(namedStream);
/*     */     }
/* 144 */     return new COSArrayList(namedStreams, array);
/*     */   }
/*     */ 
/*     */   public void setNamedJavaScripts(List namedStreams)
/*     */   {
/* 154 */     COSArray array = COSArrayList.converterToCOSArray(namedStreams);
/* 155 */     this.js.setItem("Doc", array);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.fdf.FDFJavaScript
 * JD-Core Version:    0.6.2
 */