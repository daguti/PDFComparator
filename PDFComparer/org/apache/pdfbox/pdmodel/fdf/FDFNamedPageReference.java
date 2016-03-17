/*     */ package org.apache.pdfbox.pdmodel.fdf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.pdmodel.common.filespecification.PDFileSpecification;
/*     */ 
/*     */ public class FDFNamedPageReference
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSDictionary ref;
/*     */ 
/*     */   public FDFNamedPageReference()
/*     */   {
/*  43 */     this.ref = new COSDictionary();
/*     */   }
/*     */ 
/*     */   public FDFNamedPageReference(COSDictionary r)
/*     */   {
/*  53 */     this.ref = r;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  63 */     return this.ref;
/*     */   }
/*     */ 
/*     */   public COSDictionary getCOSDictionary()
/*     */   {
/*  73 */     return this.ref;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  83 */     return this.ref.getString("Name");
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/*  93 */     this.ref.setString("Name", name);
/*     */   }
/*     */ 
/*     */   public PDFileSpecification getFileSpecification()
/*     */     throws IOException
/*     */   {
/* 105 */     return PDFileSpecification.createFS(this.ref.getDictionaryObject("F"));
/*     */   }
/*     */ 
/*     */   public void setFileSpecification(PDFileSpecification fs)
/*     */   {
/* 115 */     this.ref.setItem("F", fs);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.fdf.FDFNamedPageReference
 * JD-Core Version:    0.6.2
 */