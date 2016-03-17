/*     */ package org.apache.pdfbox.pdmodel.fdf;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.pdmodel.common.COSArrayList;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ 
/*     */ public class FDFPage
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSDictionary page;
/*     */ 
/*     */   public FDFPage()
/*     */   {
/*  44 */     this.page = new COSDictionary();
/*     */   }
/*     */ 
/*     */   public FDFPage(COSDictionary p)
/*     */   {
/*  54 */     this.page = p;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  64 */     return this.page;
/*     */   }
/*     */ 
/*     */   public COSDictionary getCOSDictionary()
/*     */   {
/*  74 */     return this.page;
/*     */   }
/*     */ 
/*     */   public List getTemplates()
/*     */   {
/*  85 */     List retval = null;
/*  86 */     COSArray array = (COSArray)this.page.getDictionaryObject("Templates");
/*  87 */     if (array != null)
/*     */     {
/*  89 */       List objects = new ArrayList();
/*  90 */       for (int i = 0; i < array.size(); i++)
/*     */       {
/*  92 */         objects.add(new FDFTemplate((COSDictionary)array.getObject(i)));
/*     */       }
/*  94 */       retval = new COSArrayList(objects, array);
/*     */     }
/*  96 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setTemplates(List templates)
/*     */   {
/* 106 */     this.page.setItem("Templates", COSArrayList.converterToCOSArray(templates));
/*     */   }
/*     */ 
/*     */   public FDFPageInfo getPageInfo()
/*     */   {
/* 116 */     FDFPageInfo retval = null;
/* 117 */     COSDictionary dict = (COSDictionary)this.page.getDictionaryObject("Info");
/* 118 */     if (dict != null)
/*     */     {
/* 120 */       retval = new FDFPageInfo(dict);
/*     */     }
/* 122 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setPageInfo(FDFPageInfo info)
/*     */   {
/* 132 */     this.page.setItem("Info", info);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.fdf.FDFPage
 * JD-Core Version:    0.6.2
 */