/*     */ package org.apache.pdfbox.pdmodel.common;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import org.apache.fontbox.util.BoundingBox;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSFloat;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ 
/*     */ public class PDRectangle
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSArray rectArray;
/*     */ 
/*     */   public PDRectangle()
/*     */   {
/*  45 */     this.rectArray = new COSArray();
/*  46 */     this.rectArray.add(new COSFloat(0.0F));
/*  47 */     this.rectArray.add(new COSFloat(0.0F));
/*  48 */     this.rectArray.add(new COSFloat(0.0F));
/*  49 */     this.rectArray.add(new COSFloat(0.0F));
/*     */   }
/*     */ 
/*     */   public PDRectangle(float width, float height)
/*     */   {
/*  60 */     this.rectArray = new COSArray();
/*  61 */     this.rectArray.add(new COSFloat(0.0F));
/*  62 */     this.rectArray.add(new COSFloat(0.0F));
/*  63 */     this.rectArray.add(new COSFloat(width));
/*  64 */     this.rectArray.add(new COSFloat(height));
/*     */   }
/*     */ 
/*     */   public PDRectangle(BoundingBox box)
/*     */   {
/*  74 */     this.rectArray = new COSArray();
/*  75 */     this.rectArray.add(new COSFloat(box.getLowerLeftX()));
/*  76 */     this.rectArray.add(new COSFloat(box.getLowerLeftY()));
/*  77 */     this.rectArray.add(new COSFloat(box.getUpperRightX()));
/*  78 */     this.rectArray.add(new COSFloat(box.getUpperRightY()));
/*     */   }
/*     */ 
/*     */   public PDRectangle(COSArray array)
/*     */   {
/*  88 */     float[] values = array.toFloatArray();
/*  89 */     this.rectArray = new COSArray();
/*     */ 
/*  91 */     this.rectArray.add(new COSFloat(Math.min(values[0], values[2])));
/*  92 */     this.rectArray.add(new COSFloat(Math.min(values[1], values[3])));
/*  93 */     this.rectArray.add(new COSFloat(Math.max(values[0], values[2])));
/*  94 */     this.rectArray.add(new COSFloat(Math.max(values[1], values[3])));
/*     */   }
/*     */ 
/*     */   public boolean contains(float x, float y)
/*     */   {
/* 105 */     float llx = getLowerLeftX();
/* 106 */     float urx = getUpperRightX();
/* 107 */     float lly = getLowerLeftY();
/* 108 */     float ury = getUpperRightY();
/* 109 */     return (x >= llx) && (x <= urx) && (y >= lly) && (y <= ury);
/*     */   }
/*     */ 
/*     */   public PDRectangle createRetranslatedRectangle()
/*     */   {
/* 124 */     PDRectangle retval = new PDRectangle();
/* 125 */     retval.setUpperRightX(getWidth());
/* 126 */     retval.setUpperRightY(getHeight());
/* 127 */     return retval;
/*     */   }
/*     */ 
/*     */   public COSArray getCOSArray()
/*     */   {
/* 137 */     return this.rectArray;
/*     */   }
/*     */ 
/*     */   public float getLowerLeftX()
/*     */   {
/* 147 */     return ((COSNumber)this.rectArray.get(0)).floatValue();
/*     */   }
/*     */ 
/*     */   public void setLowerLeftX(float value)
/*     */   {
/* 157 */     this.rectArray.set(0, new COSFloat(value));
/*     */   }
/*     */ 
/*     */   public float getLowerLeftY()
/*     */   {
/* 167 */     return ((COSNumber)this.rectArray.get(1)).floatValue();
/*     */   }
/*     */ 
/*     */   public void setLowerLeftY(float value)
/*     */   {
/* 177 */     this.rectArray.set(1, new COSFloat(value));
/*     */   }
/*     */ 
/*     */   public float getUpperRightX()
/*     */   {
/* 187 */     return ((COSNumber)this.rectArray.get(2)).floatValue();
/*     */   }
/*     */ 
/*     */   public void setUpperRightX(float value)
/*     */   {
/* 197 */     this.rectArray.set(2, new COSFloat(value));
/*     */   }
/*     */ 
/*     */   public float getUpperRightY()
/*     */   {
/* 207 */     return ((COSNumber)this.rectArray.get(3)).floatValue();
/*     */   }
/*     */ 
/*     */   public void setUpperRightY(float value)
/*     */   {
/* 217 */     this.rectArray.set(3, new COSFloat(value));
/*     */   }
/*     */ 
/*     */   public float getWidth()
/*     */   {
/* 228 */     return getUpperRightX() - getLowerLeftX();
/*     */   }
/*     */ 
/*     */   public float getHeight()
/*     */   {
/* 239 */     return getUpperRightY() - getLowerLeftY();
/*     */   }
/*     */ 
/*     */   public Dimension createDimension()
/*     */   {
/* 249 */     return new Dimension((int)getWidth(), (int)getHeight());
/*     */   }
/*     */ 
/*     */   public void move(float horizontalAmount, float verticalAmount)
/*     */   {
/* 260 */     setUpperRightX(getUpperRightX() + horizontalAmount);
/* 261 */     setLowerLeftX(getLowerLeftX() + horizontalAmount);
/* 262 */     setUpperRightY(getUpperRightY() + verticalAmount);
/* 263 */     setLowerLeftY(getLowerLeftY() + verticalAmount);
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/* 273 */     return this.rectArray;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 284 */     return "[" + getLowerLeftX() + "," + getLowerLeftY() + "," + getUpperRightX() + "," + getUpperRightY() + "]";
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.PDRectangle
 * JD-Core Version:    0.6.2
 */