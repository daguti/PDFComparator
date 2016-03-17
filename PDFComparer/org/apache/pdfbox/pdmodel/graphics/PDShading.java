/*     */ package org.apache.pdfbox.pdmodel.graphics;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSBoolean;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSFloat;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.pdmodel.common.function.PDFunction;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpaceFactory;
/*     */ 
/*     */ public class PDShading
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSDictionary DictShading;
/*     */   private COSName shadingname;
/*  45 */   private COSArray domain = null;
/*  46 */   private COSArray extend = null;
/*  47 */   private PDFunction function = null;
/*  48 */   private PDColorSpace colorspace = null;
/*     */   public static final String NAME = "Shading";
/*     */ 
/*     */   public PDShading()
/*     */   {
/*  60 */     this.DictShading = new COSDictionary();
/*     */   }
/*     */ 
/*     */   public PDShading(COSName name, COSDictionary shading)
/*     */   {
/*  71 */     this.DictShading = shading;
/*  72 */     this.shadingname = name;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  82 */     return "Shading";
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  92 */     return COSName.SHADING;
/*     */   }
/*     */ 
/*     */   public COSName getShadingName()
/*     */   {
/* 102 */     return this.shadingname;
/*     */   }
/*     */ 
/*     */   public int getShadingType()
/*     */   {
/* 113 */     return this.DictShading.getInt(COSName.SHADING_TYPE);
/*     */   }
/*     */ 
/*     */   public PDColorSpace getColorSpace()
/*     */     throws IOException
/*     */   {
/* 124 */     if (this.colorspace == null)
/*     */     {
/* 126 */       this.colorspace = PDColorSpaceFactory.createColorSpace(this.DictShading.getDictionaryObject(COSName.COLORSPACE));
/*     */     }
/* 128 */     return this.colorspace;
/*     */   }
/*     */ 
/*     */   public boolean getAntiAlias()
/*     */   {
/* 138 */     return this.DictShading.getBoolean(COSName.ANTI_ALIAS, false);
/*     */   }
/*     */ 
/*     */   public COSArray getCoords()
/*     */   {
/* 148 */     return (COSArray)this.DictShading.getDictionaryObject(COSName.COORDS);
/*     */   }
/*     */ 
/*     */   public PDFunction getFunction()
/*     */     throws IOException
/*     */   {
/* 158 */     if (this.function == null)
/*     */     {
/* 160 */       this.function = PDFunction.create(this.DictShading.getDictionaryObject(COSName.FUNCTION));
/*     */     }
/* 162 */     return this.function;
/*     */   }
/*     */ 
/*     */   public COSArray getDomain()
/*     */   {
/* 172 */     if (this.domain == null)
/*     */     {
/* 174 */       this.domain = ((COSArray)this.DictShading.getDictionaryObject(COSName.DOMAIN));
/*     */ 
/* 176 */       if (this.domain == null)
/*     */       {
/* 178 */         this.domain = new COSArray();
/* 179 */         this.domain.add(new COSFloat(0.0F));
/* 180 */         this.domain.add(new COSFloat(1.0F));
/*     */       }
/*     */     }
/* 183 */     return this.domain;
/*     */   }
/*     */ 
/*     */   public COSArray getExtend()
/*     */   {
/* 194 */     if (this.extend == null)
/*     */     {
/* 196 */       this.extend = ((COSArray)this.DictShading.getDictionaryObject(COSName.EXTEND));
/*     */ 
/* 198 */       if (this.extend == null)
/*     */       {
/* 200 */         this.extend = new COSArray();
/* 201 */         this.extend.add(COSBoolean.FALSE);
/* 202 */         this.extend.add(COSBoolean.FALSE);
/*     */       }
/*     */     }
/* 205 */     return this.extend;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*     */     String sColorSpace;
/*     */     try
/*     */     {
/* 217 */       sColorSpace = getColorSpace().toString();
/*     */     }
/*     */     catch (IOException e) {
/* 220 */       sColorSpace = "Failure retrieving ColorSpace: " + e.toString();
/*     */     }
/*     */     String sFunction;
/*     */     try {
/* 224 */       sFunction = getFunction().toString();
/*     */     }
/*     */     catch (IOException e) {
/* 227 */       sFunction = "n/a";
/*     */     }
/*     */ 
/* 231 */     String s = "Shading " + this.shadingname + "\n" + "\tShadingType: " + getShadingType() + "\n" + "\tColorSpace: " + sColorSpace + "\n" + "\tAntiAlias: " + getAntiAlias() + "\n" + "\tCoords: " + (getCoords() != null ? getCoords().toString() : "") + "\n" + "\tDomain: " + getDomain().toString() + "\n" + "\tFunction: " + sFunction + "\n" + "\tExtend: " + getExtend().toString() + "\n" + "\tRaw Value:\n" + this.DictShading.toString();
/*     */ 
/* 242 */     return s;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.PDShading
 * JD-Core Version:    0.6.2
 */