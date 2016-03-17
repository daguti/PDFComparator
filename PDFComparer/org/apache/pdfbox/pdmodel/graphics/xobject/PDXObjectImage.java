/*     */ package org.apache.pdfbox.pdmodel.graphics.xobject;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpaceFactory;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorState;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceGray;
/*     */ 
/*     */ public abstract class PDXObjectImage extends PDXObject
/*     */ {
/*  62 */   private static final Log LOG = LogFactory.getLog(PDXObjectImage.class);
/*     */   public static final String SUB_TYPE = "Image";
/*     */   private String suffix;
/*     */   private PDColorState stencilColor;
/*     */ 
/*     */   public PDXObjectImage(PDStream imageStream, String fileSuffix)
/*     */   {
/*  84 */     super(imageStream);
/*  85 */     this.suffix = fileSuffix;
/*     */   }
/*     */ 
/*     */   public PDXObjectImage(PDDocument doc, String fileSuffix)
/*     */   {
/*  96 */     super(doc);
/*  97 */     getCOSStream().setName(COSName.SUBTYPE, "Image");
/*  98 */     this.suffix = fileSuffix;
/*     */   }
/*     */ 
/*     */   public static PDXObject createThumbnailXObject(COSBase xobject)
/*     */     throws IOException
/*     */   {
/* 111 */     PDXObject retval = commonXObjectCreation(xobject, true);
/* 112 */     return retval;
/*     */   }
/*     */ 
/*     */   public abstract BufferedImage getRGBImage()
/*     */     throws IOException;
/*     */ 
/*     */   public PDXObjectImage getSMaskImage()
/*     */     throws IOException
/*     */   {
/* 133 */     COSStream cosStream = getPDStream().getStream();
/* 134 */     COSBase smask = cosStream.getDictionaryObject(COSName.SMASK);
/*     */ 
/* 136 */     if (smask == null)
/*     */     {
/* 138 */       return null;
/*     */     }
/*     */ 
/* 142 */     return (PDXObjectImage)PDXObject.createXObject(smask);
/*     */   }
/*     */ 
/*     */   public BufferedImage applyMasks(BufferedImage baseImage)
/*     */     throws IOException
/*     */   {
/* 148 */     if (getImageMask())
/*     */     {
/* 150 */       return imageMask(baseImage);
/*     */     }
/* 152 */     if (getMask() != null)
/*     */     {
/* 154 */       return mask(baseImage);
/*     */     }
/* 156 */     PDXObjectImage smask = getSMaskImage();
/* 157 */     if (smask != null)
/*     */     {
/* 159 */       BufferedImage smaskBI = smask.getRGBImage();
/* 160 */       if (smaskBI != null)
/*     */       {
/* 162 */         COSArray decodeArray = smask.getDecode();
/* 163 */         CompositeImage compositeImage = new CompositeImage(baseImage, smaskBI);
/* 164 */         BufferedImage rgbImage = compositeImage.createMaskedImage(decodeArray);
/* 165 */         return rgbImage;
/*     */       }
/*     */ 
/* 170 */       LOG.warn("masking getRGBImage returned NULL");
/*     */     }
/*     */ 
/* 173 */     return baseImage;
/*     */   }
/*     */ 
/*     */   public boolean hasMask() throws IOException
/*     */   {
/* 178 */     return (getImageMask()) || (getMask() != null) || (getSMaskImage() != null);
/*     */   }
/*     */ 
/*     */   public BufferedImage imageMask(BufferedImage baseImage)
/*     */     throws IOException
/*     */   {
/* 184 */     BufferedImage stencilMask = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), 2);
/*     */ 
/* 186 */     Graphics2D graphics = (Graphics2D)stencilMask.getGraphics();
/* 187 */     if (getStencilColor() != null)
/*     */     {
/* 189 */       graphics.setColor(getStencilColor().getJavaColor());
/*     */     }
/*     */     else
/*     */     {
/* 194 */       LOG.debug("no stencil color for PixelMap found, using Color.BLACK instead.");
/* 195 */       graphics.setColor(Color.BLACK);
/*     */     }
/*     */ 
/* 198 */     graphics.fillRect(0, 0, baseImage.getWidth(), baseImage.getHeight());
/* 199 */     COSArray decode = getDecode();
/* 200 */     if ((decode != null) && (decode.getInt(0) == 1))
/*     */     {
/* 203 */       graphics.setComposite(AlphaComposite.DstOut);
/*     */     }
/*     */     else
/*     */     {
/* 207 */       graphics.setComposite(AlphaComposite.DstIn);
/*     */     }
/* 209 */     graphics.drawImage(baseImage, null, 0, 0);
/* 210 */     graphics.dispose();
/* 211 */     return stencilMask;
/*     */   }
/*     */ 
/*     */   public BufferedImage mask(BufferedImage baseImage)
/*     */     throws IOException
/*     */   {
/* 217 */     COSBase mask = getMask();
/* 218 */     if ((mask instanceof COSStream))
/*     */     {
/* 220 */       PDXObjectImage maskImageRef = (PDXObjectImage)PDXObject.createXObject((COSStream)mask);
/* 221 */       BufferedImage maskImage = maskImageRef.getRGBImage();
/* 222 */       if (maskImage == null)
/*     */       {
/* 224 */         LOG.warn("masking getRGBImage returned NULL");
/* 225 */         return baseImage;
/*     */       }
/*     */ 
/* 228 */       BufferedImage newImage = new BufferedImage(maskImage.getWidth(), maskImage.getHeight(), 2);
/*     */ 
/* 230 */       Graphics2D graphics = (Graphics2D)newImage.getGraphics();
/* 231 */       graphics.drawImage(baseImage, 0, 0, maskImage.getWidth(), maskImage.getHeight(), 0, 0, baseImage.getWidth(), baseImage.getHeight(), null);
/*     */ 
/* 233 */       graphics.setComposite(AlphaComposite.DstIn);
/* 234 */       graphics.drawImage(maskImage, null, 0, 0);
/* 235 */       graphics.dispose();
/* 236 */       return newImage;
/*     */     }
/*     */ 
/* 241 */     LOG.warn("Colour key masking isn't supported");
/* 242 */     return baseImage;
/*     */   }
/*     */ 
/*     */   public abstract void write2OutputStream(OutputStream paramOutputStream)
/*     */     throws IOException;
/*     */ 
/*     */   public void write2file(String filename)
/*     */     throws IOException
/*     */   {
/* 261 */     FileOutputStream out = null;
/*     */     try
/*     */     {
/* 264 */       out = new FileOutputStream(filename + "." + this.suffix);
/* 265 */       write2OutputStream(out);
/* 266 */       out.flush();
/*     */     }
/*     */     finally
/*     */     {
/* 270 */       if (out != null)
/*     */       {
/* 272 */         out.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write2file(File file)
/*     */     throws IOException
/*     */   {
/* 286 */     FileOutputStream out = null;
/*     */     try
/*     */     {
/* 289 */       out = new FileOutputStream(file);
/* 290 */       write2OutputStream(out);
/* 291 */       out.flush();
/*     */     }
/*     */     finally
/*     */     {
/* 295 */       if (out != null)
/*     */       {
/* 297 */         out.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getHeight()
/*     */   {
/* 309 */     return getCOSStream().getInt(COSName.HEIGHT, -1);
/*     */   }
/*     */ 
/*     */   public void setHeight(int height)
/*     */   {
/* 319 */     getCOSStream().setInt(COSName.HEIGHT, height);
/*     */   }
/*     */ 
/*     */   public int getWidth()
/*     */   {
/* 329 */     return getCOSStream().getInt(COSName.WIDTH, -1);
/*     */   }
/*     */ 
/*     */   public void setWidth(int width)
/*     */   {
/* 339 */     getCOSStream().setInt(COSName.WIDTH, width);
/*     */   }
/*     */ 
/*     */   public int getBitsPerComponent()
/*     */   {
/* 350 */     return getCOSStream().getInt(COSName.BITS_PER_COMPONENT, COSName.BPC, -1);
/*     */   }
/*     */ 
/*     */   public void setBitsPerComponent(int bpc)
/*     */   {
/* 360 */     getCOSStream().setInt(COSName.BITS_PER_COMPONENT, bpc);
/*     */   }
/*     */ 
/*     */   public PDColorSpace getColorSpace()
/*     */     throws IOException
/*     */   {
/* 373 */     COSBase cs = getCOSStream().getDictionaryObject(COSName.COLORSPACE, COSName.CS);
/* 374 */     PDColorSpace retval = null;
/* 375 */     if (cs != null)
/*     */     {
/* 377 */       retval = PDColorSpaceFactory.createColorSpace(cs);
/* 378 */       if (retval == null)
/*     */       {
/* 380 */         LOG.debug("About to return NULL from createColorSpace branch");
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 387 */       COSBase filter = getCOSStream().getDictionaryObject(COSName.FILTER);
/* 388 */       if ((COSName.CCITTFAX_DECODE.equals(filter)) || (COSName.CCITTFAX_DECODE_ABBREVIATION.equals(filter)))
/*     */       {
/* 391 */         retval = new PDDeviceGray();
/*     */       }
/* 393 */       else if (COSName.JBIG2_DECODE.equals(filter))
/*     */       {
/* 395 */         retval = new PDDeviceGray();
/*     */       }
/* 397 */       else if (getImageMask())
/*     */       {
/* 400 */         retval = new PDDeviceGray();
/*     */       }
/*     */       else
/*     */       {
/* 404 */         LOG.debug("Colorspace can't be determined at this time, about to return NULL from unhandled branch. filter = " + filter);
/*     */ 
/* 406 */         LOG.debug("Can happen e.g. when constructing PDJpeg from ImageStream");
/*     */       }
/*     */     }
/* 409 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setColorSpace(PDColorSpace cs)
/*     */   {
/* 419 */     COSBase base = null;
/* 420 */     if (cs != null)
/*     */     {
/* 422 */       base = cs.getCOSObject();
/*     */     }
/* 424 */     getCOSStream().setItem(COSName.COLORSPACE, base);
/*     */   }
/*     */ 
/*     */   public String getSuffix()
/*     */   {
/* 434 */     return this.suffix;
/*     */   }
/*     */ 
/*     */   public boolean getImageMask()
/*     */   {
/* 444 */     return getCOSStream().getBoolean(COSName.IMAGE_MASK, false);
/*     */   }
/*     */ 
/*     */   public void setStencilColor(PDColorState stencilColorValue)
/*     */   {
/* 454 */     this.stencilColor = stencilColorValue;
/*     */   }
/*     */ 
/*     */   public PDColorState getStencilColor()
/*     */   {
/* 464 */     return this.stencilColor;
/*     */   }
/*     */ 
/*     */   public COSArray getDecode()
/*     */   {
/* 473 */     COSBase decode = getCOSStream().getDictionaryObject(COSName.DECODE);
/* 474 */     if ((decode != null) && ((decode instanceof COSArray)))
/*     */     {
/* 476 */       return (COSArray)decode;
/*     */     }
/* 478 */     return null;
/*     */   }
/*     */ 
/*     */   public COSBase getMask()
/*     */   {
/* 488 */     COSBase mask = getCOSStream().getDictionaryObject(COSName.MASK);
/* 489 */     if (mask != null)
/*     */     {
/* 491 */       return mask;
/*     */     }
/* 493 */     return null;
/*     */   }
/*     */ 
/*     */   BufferedImage extractAlphaImage(BufferedImage bi)
/*     */   {
/* 498 */     WritableRaster alphaRaster = bi.getAlphaRaster();
/* 499 */     BufferedImage alphaImage = new BufferedImage(alphaRaster.getWidth(), alphaRaster.getHeight(), bi.getTransparency() == 2 ? 12 : 10);
/*     */ 
/* 504 */     alphaImage.setData(alphaRaster);
/* 505 */     return alphaImage;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage
 * JD-Core Version:    0.6.2
 */