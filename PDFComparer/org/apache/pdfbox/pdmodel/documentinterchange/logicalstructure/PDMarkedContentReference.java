/*     */ package org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.PDPage;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ 
/*     */ public class PDMarkedContentReference
/*     */   implements COSObjectable
/*     */ {
/*     */   public static final String TYPE = "MCR";
/*     */   private COSDictionary dictionary;
/*     */ 
/*     */   protected COSDictionary getCOSDictionary()
/*     */   {
/*  40 */     return this.dictionary;
/*     */   }
/*     */ 
/*     */   public PDMarkedContentReference()
/*     */   {
/*  48 */     this.dictionary = new COSDictionary();
/*  49 */     this.dictionary.setName(COSName.TYPE, "MCR");
/*     */   }
/*     */ 
/*     */   public PDMarkedContentReference(COSDictionary dictionary)
/*     */   {
/*  59 */     this.dictionary = dictionary;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  67 */     return this.dictionary;
/*     */   }
/*     */ 
/*     */   public PDPage getPage()
/*     */   {
/*  77 */     COSDictionary pg = (COSDictionary)getCOSDictionary().getDictionaryObject(COSName.PG);
/*     */ 
/*  79 */     if (pg != null)
/*     */     {
/*  81 */       return new PDPage(pg);
/*     */     }
/*  83 */     return null;
/*     */   }
/*     */ 
/*     */   public void setPage(PDPage page)
/*     */   {
/*  93 */     getCOSDictionary().setItem(COSName.PG, page);
/*     */   }
/*     */ 
/*     */   public int getMCID()
/*     */   {
/* 103 */     return getCOSDictionary().getInt(COSName.MCID);
/*     */   }
/*     */ 
/*     */   public void setMCID(int mcid)
/*     */   {
/* 113 */     getCOSDictionary().setInt(COSName.MCID, mcid);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 120 */     return "mcid=" + getMCID();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDMarkedContentReference
 * JD-Core Version:    0.6.2
 */