/*     */ package com.itextpdf.text.pdf.parser;
/*     */ 
/*     */ import com.itextpdf.text.Version;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.exceptions.UnsupportedPdfException;
/*     */ import com.itextpdf.text.pdf.FilterHandlers;
/*     */ import com.itextpdf.text.pdf.FilterHandlers.FilterHandler;
/*     */ import com.itextpdf.text.pdf.PRStream;
/*     */ import com.itextpdf.text.pdf.PdfArray;
/*     */ import com.itextpdf.text.pdf.PdfDictionary;
/*     */ import com.itextpdf.text.pdf.PdfName;
/*     */ import com.itextpdf.text.pdf.PdfNumber;
/*     */ import com.itextpdf.text.pdf.PdfObject;
/*     */ import com.itextpdf.text.pdf.PdfReader;
/*     */ import com.itextpdf.text.pdf.PdfString;
/*     */ import com.itextpdf.text.pdf.codec.PngWriter;
/*     */ import com.itextpdf.text.pdf.codec.TiffWriter;
/*     */ import com.itextpdf.text.pdf.codec.TiffWriter.FieldAscii;
/*     */ import com.itextpdf.text.pdf.codec.TiffWriter.FieldImage;
/*     */ import com.itextpdf.text.pdf.codec.TiffWriter.FieldLong;
/*     */ import com.itextpdf.text.pdf.codec.TiffWriter.FieldRational;
/*     */ import com.itextpdf.text.pdf.codec.TiffWriter.FieldShort;
/*     */ import com.itextpdf.text.pdf.codec.TiffWriter.FieldUndefined;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.imageio.ImageIO;
/*     */ 
/*     */ public class PdfImageObject
/*     */ {
/*     */   private PdfDictionary dictionary;
/*     */   private byte[] imageBytes;
/*     */   private PdfDictionary colorSpaceDic;
/* 128 */   private int pngColorType = -1;
/*     */   private int pngBitDepth;
/*     */   private int width;
/*     */   private int height;
/*     */   private int bpc;
/*     */   private byte[] palette;
/*     */   private byte[] icc;
/*     */   private int stride;
/* 140 */   private ImageBytesType streamContentType = null;
/*     */ 
/*     */   public String getFileType() {
/* 143 */     return this.streamContentType.getFileExtension();
/*     */   }
/*     */ 
/*     */   public ImageBytesType getImageBytesType()
/*     */   {
/* 150 */     return this.streamContentType;
/*     */   }
/*     */ 
/*     */   public PdfImageObject(PRStream stream)
/*     */     throws IOException
/*     */   {
/* 159 */     this(stream, PdfReader.getStreamBytesRaw(stream), null);
/*     */   }
/*     */ 
/*     */   public PdfImageObject(PRStream stream, PdfDictionary colorSpaceDic)
/*     */     throws IOException
/*     */   {
/* 169 */     this(stream, PdfReader.getStreamBytesRaw(stream), colorSpaceDic);
/*     */   }
/*     */ 
/*     */   protected PdfImageObject(PdfDictionary dictionary, byte[] samples, PdfDictionary colorSpaceDic)
/*     */     throws IOException
/*     */   {
/* 182 */     this.dictionary = dictionary;
/* 183 */     this.colorSpaceDic = colorSpaceDic;
/* 184 */     TrackingFilter trackingFilter = new TrackingFilter(null);
/* 185 */     Map handlers = new HashMap(FilterHandlers.getDefaultFilterHandlers());
/* 186 */     handlers.put(PdfName.JBIG2DECODE, trackingFilter);
/* 187 */     handlers.put(PdfName.DCTDECODE, trackingFilter);
/* 188 */     handlers.put(PdfName.JPXDECODE, trackingFilter);
/*     */ 
/* 190 */     this.imageBytes = PdfReader.decodeBytes(samples, dictionary, handlers);
/*     */ 
/* 192 */     if (trackingFilter.lastFilterName != null) {
/* 193 */       if (PdfName.JBIG2DECODE.equals(trackingFilter.lastFilterName))
/* 194 */         this.streamContentType = ImageBytesType.JBIG2;
/* 195 */       else if (PdfName.DCTDECODE.equals(trackingFilter.lastFilterName))
/* 196 */         this.streamContentType = ImageBytesType.JPG;
/* 197 */       else if (PdfName.JPXDECODE.equals(trackingFilter.lastFilterName))
/* 198 */         this.streamContentType = ImageBytesType.JP2;
/*     */     }
/* 200 */     else decodeImageBytes();
/*     */   }
/*     */ 
/*     */   public PdfObject get(PdfName key)
/*     */   {
/* 210 */     return this.dictionary.get(key);
/*     */   }
/*     */ 
/*     */   public PdfDictionary getDictionary()
/*     */   {
/* 218 */     return this.dictionary;
/*     */   }
/*     */ 
/*     */   private void findColorspace(PdfObject colorspace, boolean allowIndexed)
/*     */     throws IOException
/*     */   {
/* 228 */     if ((colorspace == null) && (this.bpc == 1)) {
/* 229 */       this.stride = ((this.width * this.bpc + 7) / 8);
/* 230 */       this.pngColorType = 0;
/*     */     }
/* 232 */     else if (PdfName.DEVICEGRAY.equals(colorspace)) {
/* 233 */       this.stride = ((this.width * this.bpc + 7) / 8);
/* 234 */       this.pngColorType = 0;
/*     */     }
/* 236 */     else if (PdfName.DEVICERGB.equals(colorspace)) {
/* 237 */       if ((this.bpc == 8) || (this.bpc == 16)) {
/* 238 */         this.stride = ((this.width * this.bpc * 3 + 7) / 8);
/* 239 */         this.pngColorType = 2;
/*     */       }
/*     */     }
/* 242 */     else if ((colorspace instanceof PdfArray)) {
/* 243 */       PdfArray ca = (PdfArray)colorspace;
/* 244 */       PdfObject tyca = ca.getDirectObject(0);
/* 245 */       if (PdfName.CALGRAY.equals(tyca)) {
/* 246 */         this.stride = ((this.width * this.bpc + 7) / 8);
/* 247 */         this.pngColorType = 0;
/*     */       }
/* 249 */       else if (PdfName.CALRGB.equals(tyca)) {
/* 250 */         if ((this.bpc == 8) || (this.bpc == 16)) {
/* 251 */           this.stride = ((this.width * this.bpc * 3 + 7) / 8);
/* 252 */           this.pngColorType = 2;
/*     */         }
/*     */       }
/* 255 */       else if (PdfName.ICCBASED.equals(tyca)) {
/* 256 */         PRStream pr = (PRStream)ca.getDirectObject(1);
/* 257 */         int n = pr.getAsNumber(PdfName.N).intValue();
/* 258 */         if (n == 1) {
/* 259 */           this.stride = ((this.width * this.bpc + 7) / 8);
/* 260 */           this.pngColorType = 0;
/* 261 */           this.icc = PdfReader.getStreamBytes(pr);
/*     */         }
/* 263 */         else if (n == 3) {
/* 264 */           this.stride = ((this.width * this.bpc * 3 + 7) / 8);
/* 265 */           this.pngColorType = 2;
/* 266 */           this.icc = PdfReader.getStreamBytes(pr);
/*     */         }
/*     */       }
/* 269 */       else if ((allowIndexed) && (PdfName.INDEXED.equals(tyca))) {
/* 270 */         findColorspace(ca.getDirectObject(1), false);
/* 271 */         if (this.pngColorType == 2) {
/* 272 */           PdfObject id2 = ca.getDirectObject(3);
/* 273 */           if ((id2 instanceof PdfString)) {
/* 274 */             this.palette = ((PdfString)id2).getBytes();
/*     */           }
/* 276 */           else if ((id2 instanceof PRStream)) {
/* 277 */             this.palette = PdfReader.getStreamBytes((PRStream)id2);
/*     */           }
/* 279 */           this.stride = ((this.width * this.bpc + 7) / 8);
/* 280 */           this.pngColorType = 3;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void decodeImageBytes()
/*     */     throws IOException
/*     */   {
/* 292 */     if (this.streamContentType != null) {
/* 293 */       throw new IllegalStateException(MessageLocalization.getComposedMessage("Decoding.can't.happen.on.this.type.of.stream.(.1.)", new Object[] { this.streamContentType }));
/*     */     }
/* 295 */     this.pngColorType = -1;
/* 296 */     PdfArray decode = this.dictionary.getAsArray(PdfName.DECODE);
/* 297 */     this.width = this.dictionary.getAsNumber(PdfName.WIDTH).intValue();
/* 298 */     this.height = this.dictionary.getAsNumber(PdfName.HEIGHT).intValue();
/* 299 */     this.bpc = this.dictionary.getAsNumber(PdfName.BITSPERCOMPONENT).intValue();
/* 300 */     this.pngBitDepth = this.bpc;
/* 301 */     PdfObject colorspace = this.dictionary.getDirectObject(PdfName.COLORSPACE);
/* 302 */     if (((colorspace instanceof PdfName)) && (this.colorSpaceDic != null)) {
/* 303 */       PdfObject csLookup = this.colorSpaceDic.getDirectObject((PdfName)colorspace);
/* 304 */       if (csLookup != null) {
/* 305 */         colorspace = csLookup;
/*     */       }
/*     */     }
/* 308 */     this.palette = null;
/* 309 */     this.icc = null;
/* 310 */     this.stride = 0;
/* 311 */     findColorspace(colorspace, true);
/* 312 */     ByteArrayOutputStream ms = new ByteArrayOutputStream();
/* 313 */     if (this.pngColorType < 0) {
/* 314 */       if (this.bpc != 8) {
/* 315 */         throw new UnsupportedPdfException(MessageLocalization.getComposedMessage("the.color.depth.1.is.not.supported", this.bpc));
/*     */       }
/* 317 */       if (!PdfName.DEVICECMYK.equals(colorspace))
/*     */       {
/* 319 */         if ((colorspace instanceof PdfArray)) {
/* 320 */           PdfArray ca = (PdfArray)colorspace;
/* 321 */           PdfObject tyca = ca.getDirectObject(0);
/* 322 */           if (!PdfName.ICCBASED.equals(tyca))
/* 323 */             throw new UnsupportedPdfException(MessageLocalization.getComposedMessage("the.color.space.1.is.not.supported", new Object[] { colorspace }));
/* 324 */           PRStream pr = (PRStream)ca.getDirectObject(1);
/* 325 */           int n = pr.getAsNumber(PdfName.N).intValue();
/* 326 */           if (n != 4) {
/* 327 */             throw new UnsupportedPdfException(MessageLocalization.getComposedMessage("N.value.1.is.not.supported", n));
/*     */           }
/* 329 */           this.icc = PdfReader.getStreamBytes(pr);
/*     */         }
/*     */         else {
/* 332 */           throw new UnsupportedPdfException(MessageLocalization.getComposedMessage("the.color.space.1.is.not.supported", new Object[] { colorspace })); } 
/* 333 */       }this.stride = (4 * this.width);
/* 334 */       TiffWriter wr = new TiffWriter();
/* 335 */       wr.addField(new TiffWriter.FieldShort(277, 4));
/* 336 */       wr.addField(new TiffWriter.FieldShort(258, new int[] { 8, 8, 8, 8 }));
/* 337 */       wr.addField(new TiffWriter.FieldShort(262, 5));
/* 338 */       wr.addField(new TiffWriter.FieldLong(256, this.width));
/* 339 */       wr.addField(new TiffWriter.FieldLong(257, this.height));
/* 340 */       wr.addField(new TiffWriter.FieldShort(259, 5));
/* 341 */       wr.addField(new TiffWriter.FieldShort(317, 2));
/* 342 */       wr.addField(new TiffWriter.FieldLong(278, this.height));
/* 343 */       wr.addField(new TiffWriter.FieldRational(282, new int[] { 300, 1 }));
/* 344 */       wr.addField(new TiffWriter.FieldRational(283, new int[] { 300, 1 }));
/* 345 */       wr.addField(new TiffWriter.FieldShort(296, 2));
/* 346 */       wr.addField(new TiffWriter.FieldAscii(305, Version.getInstance().getVersion()));
/* 347 */       ByteArrayOutputStream comp = new ByteArrayOutputStream();
/* 348 */       TiffWriter.compressLZW(comp, 2, this.imageBytes, this.height, 4, this.stride);
/* 349 */       byte[] buf = comp.toByteArray();
/* 350 */       wr.addField(new TiffWriter.FieldImage(buf));
/* 351 */       wr.addField(new TiffWriter.FieldLong(279, buf.length));
/* 352 */       if (this.icc != null)
/* 353 */         wr.addField(new TiffWriter.FieldUndefined(34675, this.icc));
/* 354 */       wr.writeFile(ms);
/* 355 */       this.streamContentType = ImageBytesType.CCITT;
/* 356 */       this.imageBytes = ms.toByteArray();
/* 357 */       return;
/*     */     }
/* 359 */     PngWriter png = new PngWriter(ms);
/* 360 */     if ((decode != null) && 
/* 361 */       (this.pngBitDepth == 1))
/*     */     {
/* 363 */       if ((decode.getAsNumber(0).intValue() == 1) && (decode.getAsNumber(1).intValue() == 0)) {
/* 364 */         int len = this.imageBytes.length;
/* 365 */         for (int t = 0; t < len; t++)
/*     */         {
/*     */           int tmp808_806 = t;
/*     */           byte[] tmp808_803 = this.imageBytes; tmp808_803[tmp808_806] = ((byte)(tmp808_803[tmp808_806] ^ 0xFF));
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 375 */     png.writeHeader(this.width, this.height, this.pngBitDepth, this.pngColorType);
/* 376 */     if (this.icc != null)
/* 377 */       png.writeIccProfile(this.icc);
/* 378 */     if (this.palette != null)
/* 379 */       png.writePalette(this.palette);
/* 380 */     png.writeData(this.imageBytes, this.stride);
/* 381 */     png.writeEnd();
/* 382 */     this.streamContentType = ImageBytesType.PNG;
/* 383 */     this.imageBytes = ms.toByteArray();
/*     */   }
/*     */ 
/*     */   public byte[] getImageAsBytes()
/*     */   {
/* 393 */     return this.imageBytes;
/*     */   }
/*     */ 
/*     */   public BufferedImage getBufferedImage()
/*     */     throws IOException
/*     */   {
/* 402 */     byte[] img = getImageAsBytes();
/* 403 */     if (img == null)
/* 404 */       return null;
/* 405 */     return ImageIO.read(new ByteArrayInputStream(img));
/*     */   }
/*     */ 
/*     */   private static class TrackingFilter
/*     */     implements FilterHandlers.FilterHandler
/*     */   {
/* 113 */     public PdfName lastFilterName = null;
/*     */ 
/*     */     public byte[] decode(byte[] b, PdfName filterName, PdfObject decodeParams, PdfDictionary streamDictionary) throws IOException {
/* 116 */       this.lastFilterName = filterName;
/* 117 */       return b;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static enum ImageBytesType
/*     */   {
/*  81 */     PNG("png"), 
/*  82 */     JPG("jpg"), 
/*  83 */     JP2("jp2"), 
/*  84 */     CCITT("tif"), 
/*  85 */     JBIG2("jbig2");
/*     */ 
/*     */     private final String fileExtension;
/*     */ 
/*     */     private ImageBytesType(String fileExtension)
/*     */     {
/*  97 */       this.fileExtension = fileExtension;
/*     */     }
/*     */ 
/*     */     public String getFileExtension()
/*     */     {
/* 104 */       return this.fileExtension;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.parser.PdfImageObject
 * JD-Core Version:    0.6.2
 */