/*     */ package org.apache.pdfbox.pdmodel.graphics.color;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Paint;
/*     */ import java.awt.color.ColorSpace;
/*     */ import java.awt.color.ICC_ColorSpace;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.pdmodel.graphics.pattern.PDPatternResources;
/*     */ 
/*     */ public class PDColorState
/*     */   implements Cloneable
/*     */ {
/*  43 */   private static final Log LOG = LogFactory.getLog(PDColorState.class);
/*     */ 
/*  50 */   private static volatile Color iccOverrideColor = null;
/*     */ 
/*  79 */   private PDColorSpace colorSpace = new PDDeviceGray();
/*  80 */   private COSArray colorSpaceValue = new COSArray();
/*  81 */   private PDPatternResources pattern = null;
/*     */ 
/*  89 */   private Color color = null;
/*  90 */   private Paint paint = null;
/*     */ 
/*     */   public static void setIccOverrideColor(Color color)
/*     */   {
/*  76 */     iccOverrideColor = color;
/*     */   }
/*     */ 
/*     */   public PDColorState()
/*     */   {
/*  98 */     setColorSpaceValue(new float[] { 0.0F });
/*     */   }
/*     */ 
/*     */   public Object clone()
/*     */   {
/* 106 */     PDColorState retval = new PDColorState();
/* 107 */     retval.colorSpace = this.colorSpace;
/* 108 */     retval.colorSpaceValue.clear();
/* 109 */     retval.colorSpaceValue.addAll(this.colorSpaceValue);
/* 110 */     retval.setPattern(getPattern());
/* 111 */     return retval;
/*     */   }
/*     */ 
/*     */   public Color getJavaColor()
/*     */     throws IOException
/*     */   {
/* 122 */     if ((this.color == null) && (this.colorSpaceValue.size() > 0))
/*     */     {
/* 124 */       this.color = createColor();
/*     */     }
/* 126 */     return this.color;
/*     */   }
/*     */ 
/*     */   public Paint getPaint(int pageHeight)
/*     */     throws IOException
/*     */   {
/* 139 */     if ((this.paint == null) && (this.pattern != null))
/*     */     {
/* 141 */       this.paint = this.pattern.getPaint(pageHeight);
/*     */     }
/* 143 */     return this.paint;
/*     */   }
/*     */ 
/*     */   private Color createColor()
/*     */     throws IOException
/*     */   {
/* 154 */     float[] components = this.colorSpaceValue.toFloatArray();
/*     */     try
/*     */     {
/* 157 */       String csName = this.colorSpace.getName();
/* 158 */       if (("DeviceRGB".equals(csName)) && (components.length == 3))
/*     */       {
/* 164 */         return new Color(components[0], components[1], components[2]);
/*     */       }
/* 166 */       if ("Lab".equals(csName))
/*     */       {
/* 169 */         float[] csComponents = this.colorSpace.getJavaColorSpace().toRGB(components);
/* 170 */         return new Color(csComponents[0], csComponents[1], csComponents[2]);
/*     */       }
/*     */ 
/* 174 */       if (components.length == 1)
/*     */       {
/* 176 */         if ("Separation".equals(csName))
/*     */         {
/* 179 */           return new Color((int)components[0]);
/*     */         }
/* 181 */         if ("DeviceGray".equals(csName))
/*     */         {
/* 187 */           return new Color(components[0], components[0], components[0]);
/*     */         }
/*     */       }
/* 190 */       Color override = iccOverrideColor;
/* 191 */       ColorSpace cs = this.colorSpace.getJavaColorSpace();
/* 192 */       if (((cs instanceof ICC_ColorSpace)) && (override != null))
/*     */       {
/* 194 */         LOG.warn("Using an ICC override color to avoid a potential JVM crash (see PDFBOX-511)");
/* 195 */         return override;
/*     */       }
/*     */ 
/* 199 */       return new Color(cs, components, 1.0F);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 209 */       String sMsg = "Unable to create the color instance " + Arrays.toString(components) + " in color space " + this.colorSpace + "; guessing color ... ";
/*     */       Color cGuess;
/*     */       try
/*     */       {
/* 213 */         switch (components.length)
/*     */         {
/*     */         case 1:
/* 216 */           cGuess = new Color((int)components[0]);
/* 217 */           sMsg = sMsg + "\nInterpretating as single-integer RGB";
/* 218 */           break;
/*     */         case 3:
/* 220 */           cGuess = new Color(components[0], components[1], components[2]);
/* 221 */           sMsg = sMsg + "\nInterpretating as RGB";
/* 222 */           break;
/*     */         case 4:
/* 230 */           float k = components[3];
/*     */ 
/* 232 */           float r = components[0] * (1.0F - k) + k;
/* 233 */           float g = components[1] * (1.0F - k) + k;
/* 234 */           float b = components[2] * (1.0F - k) + k;
/*     */ 
/* 236 */           r = 1.0F - r;
/* 237 */           g = 1.0F - g;
/* 238 */           b = 1.0F - b;
/*     */ 
/* 240 */           cGuess = new Color(r, g, b);
/* 241 */           sMsg = sMsg + "\nInterpretating as CMYK";
/* 242 */           break;
/*     */         case 2:
/*     */         default:
/* 245 */           sMsg = sMsg + "\nUnable to guess using " + components.length + " components; using black instead";
/* 246 */           cGuess = Color.BLACK;
/*     */         }
/*     */       }
/*     */       catch (Exception e2)
/*     */       {
/* 251 */         sMsg = sMsg + "\nColor interpolation failed; using black instead\n";
/* 252 */         sMsg = sMsg + e2.toString();
/* 253 */         cGuess = Color.BLACK;
/*     */       }
/* 255 */       LOG.warn(sMsg, e);
/* 256 */       return cGuess;
/*     */     }
/*     */   }
/*     */ 
/*     */   public PDColorState(COSArray csValues)
/*     */   {
/* 267 */     this.colorSpaceValue = csValues;
/*     */   }
/*     */ 
/*     */   public PDColorSpace getColorSpace()
/*     */   {
/* 277 */     return this.colorSpace;
/*     */   }
/*     */ 
/*     */   public void setColorSpace(PDColorSpace value)
/*     */   {
/* 287 */     this.colorSpace = value;
/*     */ 
/* 289 */     this.color = null;
/* 290 */     this.paint = null;
/* 291 */     this.pattern = null;
/*     */   }
/*     */ 
/*     */   public float[] getColorSpaceValue()
/*     */   {
/* 301 */     return this.colorSpaceValue.toFloatArray();
/*     */   }
/*     */ 
/*     */   public COSArray getCOSColorSpaceValue()
/*     */   {
/* 311 */     return this.colorSpaceValue;
/*     */   }
/*     */ 
/*     */   public void setColorSpaceValue(float[] value)
/*     */   {
/* 321 */     this.colorSpaceValue.setFloatArray(value);
/*     */ 
/* 323 */     this.color = null;
/* 324 */     this.paint = null;
/* 325 */     this.pattern = null;
/*     */   }
/*     */ 
/*     */   public PDPatternResources getPattern()
/*     */   {
/* 335 */     return this.pattern;
/*     */   }
/*     */ 
/*     */   public void setPattern(PDPatternResources patternValue)
/*     */   {
/* 345 */     this.pattern = patternValue;
/*     */ 
/* 347 */     this.color = null;
/* 348 */     this.paint = null;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*     */     try
/*     */     {
/*  55 */       iccOverrideColor = Color.getColor("org.apache.pdfbox.ICC_override_color");
/*     */     }
/*     */     catch (SecurityException e)
/*     */     {
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.color.PDColorState
 * JD-Core Version:    0.6.2
 */