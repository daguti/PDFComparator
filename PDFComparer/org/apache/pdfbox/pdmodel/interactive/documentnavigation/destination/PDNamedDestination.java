/*     */ package org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSString;
/*     */ 
/*     */ public class PDNamedDestination extends PDDestination
/*     */ {
/*     */   private COSBase namedDestination;
/*     */ 
/*     */   public PDNamedDestination(COSString dest)
/*     */   {
/*  42 */     this.namedDestination = dest;
/*     */   }
/*     */ 
/*     */   public PDNamedDestination(COSName dest)
/*     */   {
/*  52 */     this.namedDestination = dest;
/*     */   }
/*     */ 
/*     */   public PDNamedDestination()
/*     */   {
/*     */   }
/*     */ 
/*     */   public PDNamedDestination(String dest)
/*     */   {
/*  70 */     this.namedDestination = new COSString(dest);
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  80 */     return this.namedDestination;
/*     */   }
/*     */ 
/*     */   public String getNamedDestination()
/*     */   {
/*  90 */     String retval = null;
/*  91 */     if ((this.namedDestination instanceof COSString))
/*     */     {
/*  93 */       retval = ((COSString)this.namedDestination).getString();
/*     */     }
/*  95 */     else if ((this.namedDestination instanceof COSName))
/*     */     {
/*  97 */       retval = ((COSName)this.namedDestination).getName();
/*     */     }
/*     */ 
/* 100 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setNamedDestination(String dest)
/*     */     throws IOException
/*     */   {
/* 112 */     if ((this.namedDestination instanceof COSString))
/*     */     {
/* 114 */       COSString string = (COSString)this.namedDestination;
/* 115 */       string.reset();
/* 116 */       string.append(dest.getBytes("ISO-8859-1"));
/*     */     }
/* 118 */     else if (dest == null)
/*     */     {
/* 120 */       this.namedDestination = null;
/*     */     }
/*     */     else
/*     */     {
/* 124 */       this.namedDestination = new COSString(dest);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDNamedDestination
 * JD-Core Version:    0.6.2
 */