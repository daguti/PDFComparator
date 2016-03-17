/*     */ package org.apache.pdfbox.pdmodel.documentinterchange.taggedpdf;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSNull;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDGamma;
/*     */ 
/*     */ public class PDFourColours
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSArray array;
/*     */ 
/*     */   public PDFourColours()
/*     */   {
/*  38 */     this.array = new COSArray();
/*  39 */     this.array.add(COSNull.NULL);
/*  40 */     this.array.add(COSNull.NULL);
/*  41 */     this.array.add(COSNull.NULL);
/*  42 */     this.array.add(COSNull.NULL);
/*     */   }
/*     */ 
/*     */   public PDFourColours(COSArray array)
/*     */   {
/*  47 */     this.array = array;
/*     */ 
/*  49 */     if (this.array.size() < 4)
/*     */     {
/*  51 */       for (int i = this.array.size() - 1; i < 4; i++)
/*     */       {
/*  53 */         this.array.add(COSNull.NULL);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public PDGamma getBeforeColour()
/*     */   {
/*  66 */     return getColourByIndex(0);
/*     */   }
/*     */ 
/*     */   public void setBeforeColour(PDGamma colour)
/*     */   {
/*  76 */     setColourByIndex(0, colour);
/*     */   }
/*     */ 
/*     */   public PDGamma getAfterColour()
/*     */   {
/*  86 */     return getColourByIndex(1);
/*     */   }
/*     */ 
/*     */   public void setAfterColour(PDGamma colour)
/*     */   {
/*  96 */     setColourByIndex(1, colour);
/*     */   }
/*     */ 
/*     */   public PDGamma getStartColour()
/*     */   {
/* 106 */     return getColourByIndex(2);
/*     */   }
/*     */ 
/*     */   public void setStartColour(PDGamma colour)
/*     */   {
/* 116 */     setColourByIndex(2, colour);
/*     */   }
/*     */ 
/*     */   public PDGamma getEndColour()
/*     */   {
/* 126 */     return getColourByIndex(3);
/*     */   }
/*     */ 
/*     */   public void setEndColour(PDGamma colour)
/*     */   {
/* 136 */     setColourByIndex(3, colour);
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/* 145 */     return this.array;
/*     */   }
/*     */ 
/*     */   private PDGamma getColourByIndex(int index)
/*     */   {
/* 157 */     PDGamma retval = null;
/* 158 */     COSBase item = this.array.getObject(index);
/* 159 */     if ((item instanceof COSArray))
/*     */     {
/* 161 */       retval = new PDGamma((COSArray)item);
/*     */     }
/* 163 */     return retval;
/*     */   }
/*     */ 
/*     */   private void setColourByIndex(int index, PDGamma colour)
/*     */   {
/*     */     COSBase base;
/*     */     COSBase base;
/* 175 */     if (colour == null)
/*     */     {
/* 177 */       base = COSNull.NULL;
/*     */     }
/*     */     else
/*     */     {
/* 181 */       base = colour.getCOSArray();
/*     */     }
/* 183 */     this.array.set(index, base);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.documentinterchange.taggedpdf.PDFourColours
 * JD-Core Version:    0.6.2
 */