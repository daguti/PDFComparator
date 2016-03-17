/*     */ package org.apache.pdfbox.pdmodel.graphics.color;
/*     */ 
/*     */ import java.awt.color.ColorSpace;
/*     */ import java.awt.color.ICC_ColorSpace;
/*     */ import java.awt.color.ICC_Profile;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Map;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSFloat;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSObject;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ import org.apache.pdfbox.pdmodel.graphics.pattern.PDPatternResources;
/*     */ 
/*     */ public final class PDColorSpaceFactory
/*     */ {
/*     */   public static PDColorSpace createColorSpace(COSBase colorSpace)
/*     */     throws IOException
/*     */   {
/*  62 */     return createColorSpace(colorSpace, null);
/*     */   }
/*     */ 
/*     */   public static PDColorSpace createColorSpace(COSBase colorSpace, Map<String, PDColorSpace> colorSpaces)
/*     */     throws IOException
/*     */   {
/*  78 */     return createColorSpace(colorSpace, colorSpaces, null);
/*     */   }
/*     */ 
/*     */   public static PDColorSpace createColorSpace(COSBase colorSpace, Map<String, PDColorSpace> colorSpaces, Map<String, PDPatternResources> patterns)
/*     */     throws IOException
/*     */   {
/*  95 */     PDColorSpace retval = null;
/*  96 */     if ((colorSpace instanceof COSObject))
/*     */     {
/*  98 */       retval = createColorSpace(((COSObject)colorSpace).getObject(), colorSpaces);
/*     */     }
/* 100 */     else if ((colorSpace instanceof COSName)) {
/* 101 */       retval = createColorSpace(((COSName)colorSpace).getName(), colorSpaces);
/*     */     }
/* 103 */     else if ((colorSpace instanceof COSArray))
/*     */     {
/* 105 */       COSArray array = (COSArray)colorSpace;
/* 106 */       String name = ((COSName)array.getObject(0)).getName();
/* 107 */       if (name.equals("CalGray"))
/*     */       {
/* 109 */         retval = new PDCalGray(array);
/*     */       }
/* 111 */       else if (name.equals("DeviceRGB"))
/*     */       {
/* 113 */         retval = PDDeviceRGB.INSTANCE;
/*     */       }
/* 115 */       else if (name.equals("DeviceGray"))
/*     */       {
/* 117 */         retval = new PDDeviceGray();
/*     */       }
/* 119 */       else if (name.equals("DeviceCMYK"))
/*     */       {
/* 121 */         retval = PDDeviceCMYK.INSTANCE;
/*     */       }
/* 123 */       else if (name.equals("CalRGB"))
/*     */       {
/* 125 */         retval = new PDCalRGB(array);
/*     */       }
/* 127 */       else if (name.equals("DeviceN"))
/*     */       {
/* 129 */         retval = new PDDeviceN(array);
/*     */       }
/* 131 */       else if ((name.equals("Indexed")) || (name.equals("I")))
/*     */       {
/* 134 */         retval = new PDIndexed(array);
/*     */       }
/* 136 */       else if (name.equals("Lab"))
/*     */       {
/* 138 */         retval = new PDLab(array);
/*     */       }
/* 140 */       else if (name.equals("Separation"))
/*     */       {
/* 142 */         retval = new PDSeparation(array);
/*     */       }
/* 144 */       else if (name.equals("ICCBased"))
/*     */       {
/* 146 */         retval = new PDICCBased(array);
/*     */       }
/* 148 */       else if (name.equals("Pattern"))
/*     */       {
/* 150 */         retval = new PDPattern(array);
/*     */       }
/*     */       else
/*     */       {
/* 154 */         throw new IOException("Unknown colorspace array type:" + name);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 159 */       throw new IOException("Unknown colorspace type:" + colorSpace);
/*     */     }
/* 161 */     return retval;
/*     */   }
/*     */ 
/*     */   public static PDColorSpace createColorSpace(String colorSpaceName)
/*     */     throws IOException
/*     */   {
/* 175 */     return createColorSpace(colorSpaceName, null);
/*     */   }
/*     */ 
/*     */   public static PDColorSpace createColorSpace(String colorSpaceName, Map<String, PDColorSpace> colorSpaces)
/*     */     throws IOException
/*     */   {
/* 191 */     PDColorSpace cs = null;
/* 192 */     if ((colorSpaceName.equals("DeviceCMYK")) || (colorSpaceName.equals("CMYK")))
/*     */     {
/* 195 */       cs = PDDeviceCMYK.INSTANCE;
/*     */     }
/* 197 */     else if ((colorSpaceName.equals("DeviceRGB")) || (colorSpaceName.equals("RGB")))
/*     */     {
/* 200 */       cs = PDDeviceRGB.INSTANCE;
/*     */     }
/* 202 */     else if ((colorSpaceName.equals("DeviceGray")) || (colorSpaceName.equals("G")))
/*     */     {
/* 205 */       cs = new PDDeviceGray();
/*     */     }
/* 207 */     else if ((colorSpaces != null) && (colorSpaces.get(colorSpaceName) != null))
/*     */     {
/* 209 */       cs = (PDColorSpace)colorSpaces.get(colorSpaceName);
/*     */     }
/* 211 */     else if (colorSpaceName.equals("Lab"))
/*     */     {
/* 213 */       cs = new PDLab();
/*     */     }
/* 215 */     else if (colorSpaceName.equals("Pattern"))
/*     */     {
/* 217 */       cs = new PDPattern();
/*     */     }
/*     */     else
/*     */     {
/* 221 */       throw new IOException("Error: Unknown colorspace '" + colorSpaceName + "'");
/*     */     }
/* 223 */     return cs;
/*     */   }
/*     */ 
/*     */   public static PDColorSpace createColorSpace(PDDocument doc, ColorSpace cs)
/*     */     throws IOException
/*     */   {
/* 238 */     PDColorSpace retval = null;
/* 239 */     if (cs.isCS_sRGB())
/*     */     {
/* 241 */       retval = PDDeviceRGB.INSTANCE;
/*     */     }
/* 243 */     else if ((cs instanceof ICC_ColorSpace))
/*     */     {
/* 245 */       ICC_ColorSpace ics = (ICC_ColorSpace)cs;
/* 246 */       PDICCBased pdCS = new PDICCBased(doc);
/* 247 */       retval = pdCS;
/* 248 */       COSArray ranges = new COSArray();
/* 249 */       for (int i = 0; i < cs.getNumComponents(); i++)
/*     */       {
/* 251 */         ranges.add(new COSFloat(ics.getMinValue(i)));
/* 252 */         ranges.add(new COSFloat(ics.getMaxValue(i)));
/*     */       }
/* 254 */       PDStream iccData = pdCS.getPDStream();
/* 255 */       OutputStream output = null;
/*     */       try
/*     */       {
/* 258 */         output = iccData.createOutputStream();
/* 259 */         output.write(ics.getProfile().getData());
/*     */       }
/*     */       finally
/*     */       {
/* 263 */         if (output != null)
/*     */         {
/* 265 */           output.close();
/*     */         }
/*     */       }
/* 268 */       pdCS.setNumberOfComponents(cs.getNumComponents());
/*     */     }
/*     */     else
/*     */     {
/* 272 */       throw new IOException("Not yet implemented:" + cs);
/*     */     }
/* 274 */     return retval;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.color.PDColorSpaceFactory
 * JD-Core Version:    0.6.2
 */