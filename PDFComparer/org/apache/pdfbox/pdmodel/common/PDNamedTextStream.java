/*     */ package org.apache.pdfbox.pdmodel.common;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ 
/*     */ public class PDNamedTextStream
/*     */   implements DualCOSObjectable
/*     */ {
/*     */   private COSName streamName;
/*     */   private PDTextStream stream;
/*     */ 
/*     */   public PDNamedTextStream()
/*     */   {
/*     */   }
/*     */ 
/*     */   public PDNamedTextStream(COSName name, COSBase str)
/*     */   {
/*  50 */     this.streamName = name;
/*  51 */     this.stream = PDTextStream.createTextStream(str);
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  61 */     String name = null;
/*  62 */     if (this.streamName != null)
/*     */     {
/*  64 */       name = this.streamName.getName();
/*     */     }
/*  66 */     return name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/*  76 */     this.streamName = COSName.getPDFName(name);
/*     */   }
/*     */ 
/*     */   public PDTextStream getStream()
/*     */   {
/*  86 */     return this.stream;
/*     */   }
/*     */ 
/*     */   public void setStream(PDTextStream str)
/*     */   {
/*  96 */     this.stream = str;
/*     */   }
/*     */ 
/*     */   public COSBase getFirstCOSObject()
/*     */   {
/* 106 */     return this.streamName;
/*     */   }
/*     */ 
/*     */   public COSBase getSecondCOSObject()
/*     */   {
/* 116 */     COSBase retval = null;
/* 117 */     if (this.stream != null)
/*     */     {
/* 119 */       retval = this.stream.getCOSObject();
/*     */     }
/* 121 */     return retval;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.PDNamedTextStream
 * JD-Core Version:    0.6.2
 */