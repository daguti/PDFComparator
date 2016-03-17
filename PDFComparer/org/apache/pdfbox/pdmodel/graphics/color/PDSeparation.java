/*     */ package org.apache.pdfbox.pdmodel.graphics.color;
/*     */ 
/*     */ import java.awt.color.ColorSpace;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.io.IOException;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.common.function.PDFunction;
/*     */ 
/*     */ public class PDSeparation extends PDColorSpace
/*     */ {
/*  42 */   private static final Log log = LogFactory.getLog(PDSeparation.class);
/*     */   public static final String NAME = "Separation";
/*     */ 
/*     */   public PDSeparation()
/*     */   {
/*  55 */     this.array = new COSArray();
/*  56 */     this.array.add(COSName.SEPARATION);
/*  57 */     this.array.add(COSName.getPDFName(""));
/*     */   }
/*     */ 
/*     */   public PDSeparation(COSArray separation)
/*     */   {
/*  67 */     this.array = separation;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  78 */     return "Separation";
/*     */   }
/*     */ 
/*     */   public int getNumberOfComponents()
/*     */     throws IOException
/*     */   {
/*  90 */     return getAlternateColorSpace().getNumberOfComponents();
/*     */   }
/*     */ 
/*     */   protected ColorSpace createColorSpace()
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 104 */       PDColorSpace alt = getAlternateColorSpace();
/* 105 */       return alt.getJavaColorSpace();
/*     */     }
/*     */     catch (IOException ioexception)
/*     */     {
/* 109 */       log.error(ioexception, ioexception);
/*     */ 
/* 111 */       throw ioexception;
/*     */     }
/*     */     catch (Exception exception)
/*     */     {
/* 115 */       log.error(exception, exception);
/* 116 */     }throw new IOException("Failed to Create ColorSpace");
/*     */   }
/*     */ 
/*     */   public ColorModel createColorModel(int bpc)
/*     */     throws IOException
/*     */   {
/* 131 */     log.info("About to create ColorModel for " + getAlternateColorSpace().toString());
/* 132 */     return getAlternateColorSpace().createColorModel(bpc);
/*     */   }
/*     */ 
/*     */   public String getColorantName()
/*     */   {
/* 142 */     COSName name = (COSName)this.array.getObject(1);
/* 143 */     return name.getName();
/*     */   }
/*     */ 
/*     */   public void setColorantName(String name)
/*     */   {
/* 153 */     this.array.set(1, COSName.getPDFName(name));
/*     */   }
/*     */ 
/*     */   public PDColorSpace getAlternateColorSpace()
/*     */     throws IOException
/*     */   {
/* 165 */     COSBase alternate = this.array.getObject(2);
/* 166 */     PDColorSpace cs = PDColorSpaceFactory.createColorSpace(alternate);
/* 167 */     return cs;
/*     */   }
/*     */ 
/*     */   public void setAlternateColorSpace(PDColorSpace cs)
/*     */   {
/* 177 */     COSBase space = null;
/* 178 */     if (cs != null)
/*     */     {
/* 180 */       space = cs.getCOSObject();
/*     */     }
/* 182 */     this.array.set(2, space);
/*     */   }
/*     */ 
/*     */   public PDFunction getTintTransform()
/*     */     throws IOException
/*     */   {
/* 194 */     return PDFunction.create(this.array.getObject(3));
/*     */   }
/*     */ 
/*     */   public void setTintTransform(PDFunction tint)
/*     */   {
/* 204 */     this.array.set(3, tint);
/*     */   }
/*     */ 
/*     */   public COSArray calculateColorValues(COSBase tintValue)
/*     */     throws IOException
/*     */   {
/* 215 */     PDFunction tintTransform = getTintTransform();
/* 216 */     COSArray tint = new COSArray();
/* 217 */     tint.add(tintValue);
/* 218 */     return tintTransform.eval(tint);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.color.PDSeparation
 * JD-Core Version:    0.6.2
 */