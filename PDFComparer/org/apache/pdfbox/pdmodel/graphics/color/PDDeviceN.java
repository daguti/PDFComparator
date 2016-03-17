/*     */ package org.apache.pdfbox.pdmodel.graphics.color;
/*     */ 
/*     */ import java.awt.color.ColorSpace;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSNull;
/*     */ import org.apache.pdfbox.pdmodel.common.COSArrayList;
/*     */ import org.apache.pdfbox.pdmodel.common.function.PDFunction;
/*     */ 
/*     */ public class PDDeviceN extends PDColorSpace
/*     */ {
/*  49 */   private static final Log LOG = LogFactory.getLog(PDDeviceN.class);
/*     */   private static final int COLORANT_NAMES = 1;
/*     */   private static final int ALTERNATE_CS = 2;
/*     */   private static final int TINT_TRANSFORM = 3;
/*     */   private static final int DEVICEN_ATTRIBUTES = 4;
/*  56 */   private PDFunction tintTransform = null;
/*  57 */   private PDColorSpace alternateCS = null;
/*  58 */   private PDDeviceNAttributes deviceNAttributes = null;
/*     */   public static final String NAME = "DeviceN";
/*     */   private COSArray array;
/*     */ 
/*     */   public PDDeviceN()
/*     */   {
/*  72 */     this.array = new COSArray();
/*  73 */     this.array.add(COSName.DEVICEN);
/*     */ 
/*  75 */     this.array.add(COSNull.NULL);
/*  76 */     this.array.add(COSNull.NULL);
/*  77 */     this.array.add(COSNull.NULL);
/*     */   }
/*     */ 
/*     */   public PDDeviceN(COSArray csAttributes)
/*     */   {
/*  87 */     this.array = csAttributes;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  98 */     return "DeviceN";
/*     */   }
/*     */ 
/*     */   public int getNumberOfComponents()
/*     */     throws IOException
/*     */   {
/* 110 */     return getColorantNames().size();
/*     */   }
/*     */ 
/*     */   protected ColorSpace createColorSpace()
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 124 */       return getAlternateColorSpace().getJavaColorSpace();
/*     */     }
/*     */     catch (IOException ioexception)
/*     */     {
/* 128 */       LOG.error(ioexception, ioexception);
/* 129 */       throw ioexception;
/*     */     }
/*     */     catch (Exception exception)
/*     */     {
/* 133 */       LOG.error(exception, exception);
/* 134 */     }throw new IOException("Failed to Create ColorSpace");
/*     */   }
/*     */ 
/*     */   public ColorModel createColorModel(int bpc)
/*     */     throws IOException
/*     */   {
/* 149 */     LOG.info("About to create ColorModel for " + getAlternateColorSpace().toString());
/* 150 */     return getAlternateColorSpace().createColorModel(bpc);
/*     */   }
/*     */ 
/*     */   public List<String> getColorantNames()
/*     */   {
/* 160 */     COSArray names = (COSArray)this.array.getObject(1);
/* 161 */     return COSArrayList.convertCOSNameCOSArrayToList(names);
/*     */   }
/*     */ 
/*     */   public void setColorantNames(List<String> names)
/*     */   {
/* 171 */     COSArray namesArray = COSArrayList.convertStringListToCOSNameCOSArray(names);
/* 172 */     this.array.set(1, namesArray);
/*     */   }
/*     */ 
/*     */   public PDColorSpace getAlternateColorSpace()
/*     */     throws IOException
/*     */   {
/* 184 */     if (this.alternateCS == null)
/*     */     {
/* 186 */       COSBase alternate = this.array.getObject(2);
/* 187 */       this.alternateCS = PDColorSpaceFactory.createColorSpace(alternate);
/*     */     }
/* 189 */     return this.alternateCS;
/*     */   }
/*     */ 
/*     */   public void setAlternateColorSpace(PDColorSpace cs)
/*     */   {
/* 199 */     this.alternateCS = cs;
/* 200 */     COSBase space = null;
/* 201 */     if (cs != null)
/*     */     {
/* 203 */       space = cs.getCOSObject();
/*     */     }
/* 205 */     this.array.set(2, space);
/*     */   }
/*     */ 
/*     */   public PDFunction getTintTransform()
/*     */     throws IOException
/*     */   {
/* 217 */     if (this.tintTransform == null)
/*     */     {
/* 219 */       this.tintTransform = PDFunction.create(this.array.getObject(3));
/*     */     }
/* 221 */     return this.tintTransform;
/*     */   }
/*     */ 
/*     */   public void setTintTransform(PDFunction tint)
/*     */   {
/* 231 */     this.tintTransform = tint;
/* 232 */     this.array.set(3, tint);
/*     */   }
/*     */ 
/*     */   public PDDeviceNAttributes getAttributes()
/*     */   {
/* 243 */     if ((this.deviceNAttributes == null) && (this.array.size() > 4)) {
/* 244 */       this.deviceNAttributes = new PDDeviceNAttributes((COSDictionary)this.array.getObject(4));
/*     */     }
/* 246 */     return this.deviceNAttributes;
/*     */   }
/*     */ 
/*     */   public void setAttributes(PDDeviceNAttributes attributes)
/*     */   {
/* 257 */     this.deviceNAttributes = attributes;
/* 258 */     if (attributes == null)
/*     */     {
/* 260 */       this.array.remove(4);
/*     */     }
/*     */     else
/*     */     {
/* 265 */       while (this.array.size() <= 5)
/*     */       {
/* 267 */         this.array.add(COSNull.NULL);
/*     */       }
/* 269 */       this.array.set(4, attributes.getCOSDictionary());
/*     */     }
/*     */   }
/*     */ 
/*     */   public COSArray calculateColorValues(List<COSBase> tintValues)
/*     */     throws IOException
/*     */   {
/* 281 */     PDFunction tintTransform = getTintTransform();
/* 282 */     COSArray tint = new COSArray();
/* 283 */     tint.addAll(tintValues);
/* 284 */     return tintTransform.eval(tint);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.color.PDDeviceN
 * JD-Core Version:    0.6.2
 */