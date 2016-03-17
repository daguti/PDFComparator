/*     */ package org.apache.pdfbox.cos;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.apache.pdfbox.exceptions.COSVisitorException;
/*     */ 
/*     */ public class COSObject extends COSBase
/*     */ {
/*     */   private COSBase baseObject;
/*     */   private COSInteger objectNumber;
/*     */   private COSInteger generationNumber;
/*     */ 
/*     */   public COSObject(COSBase object)
/*     */     throws IOException
/*     */   {
/*  44 */     setObject(object);
/*     */   }
/*     */ 
/*     */   public COSBase getDictionaryObject(COSName key)
/*     */   {
/*  57 */     COSBase retval = null;
/*  58 */     if ((this.baseObject instanceof COSDictionary))
/*     */     {
/*  60 */       retval = ((COSDictionary)this.baseObject).getDictionaryObject(key);
/*     */     }
/*  62 */     return retval;
/*     */   }
/*     */ 
/*     */   public COSBase getItem(COSName key)
/*     */   {
/*  74 */     COSBase retval = null;
/*  75 */     if ((this.baseObject instanceof COSDictionary))
/*     */     {
/*  77 */       retval = ((COSDictionary)this.baseObject).getItem(key);
/*     */     }
/*  79 */     return retval;
/*     */   }
/*     */ 
/*     */   public COSBase getObject()
/*     */   {
/*  89 */     return this.baseObject;
/*     */   }
/*     */ 
/*     */   public void setObject(COSBase object)
/*     */     throws IOException
/*     */   {
/* 101 */     this.baseObject = object;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 159 */     return "COSObject{" + (this.objectNumber == null ? "unknown" : new StringBuilder().append("").append(this.objectNumber.intValue()).toString()) + ", " + (this.generationNumber == null ? "unknown" : new StringBuilder().append("").append(this.generationNumber.intValue()).toString()) + "}";
/*     */   }
/*     */ 
/*     */   public COSInteger getObjectNumber()
/*     */   {
/* 170 */     return this.objectNumber;
/*     */   }
/*     */ 
/*     */   public void setObjectNumber(COSInteger objectNum)
/*     */   {
/* 178 */     this.objectNumber = objectNum;
/*     */   }
/*     */ 
/*     */   public COSInteger getGenerationNumber()
/*     */   {
/* 186 */     return this.generationNumber;
/*     */   }
/*     */ 
/*     */   public void setGenerationNumber(COSInteger generationNumberValue)
/*     */   {
/* 194 */     this.generationNumber = generationNumberValue;
/*     */   }
/*     */ 
/*     */   public Object accept(ICOSVisitor visitor)
/*     */     throws COSVisitorException
/*     */   {
/* 206 */     return getObject() != null ? getObject().accept(visitor) : COSNull.NULL.accept(visitor);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.cos.COSObject
 * JD-Core Version:    0.6.2
 */