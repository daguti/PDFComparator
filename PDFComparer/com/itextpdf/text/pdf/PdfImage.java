/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.Image;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.URL;
/*     */ 
/*     */ public class PdfImage extends PdfStream
/*     */ {
/*     */   static final int TRANSFERSIZE = 4096;
/*  65 */   protected PdfName name = null;
/*     */ 
/*  67 */   protected Image image = null;
/*     */ 
/*     */   public PdfImage(Image image, String name, PdfIndirectReference maskRef)
/*     */     throws BadPdfFormatException
/*     */   {
/*  81 */     this.image = image;
/*  82 */     if (name == null)
/*  83 */       generateImgResName(image);
/*     */     else
/*  85 */       this.name = new PdfName(name);
/*  86 */     put(PdfName.TYPE, PdfName.XOBJECT);
/*  87 */     put(PdfName.SUBTYPE, PdfName.IMAGE);
/*  88 */     put(PdfName.WIDTH, new PdfNumber(image.getWidth()));
/*  89 */     put(PdfName.HEIGHT, new PdfNumber(image.getHeight()));
/*  90 */     if (image.getLayer() != null)
/*  91 */       put(PdfName.OC, image.getLayer().getRef());
/*  92 */     if ((image.isMask()) && ((image.getBpc() == 1) || (image.getBpc() > 255)))
/*  93 */       put(PdfName.IMAGEMASK, PdfBoolean.PDFTRUE);
/*  94 */     if (maskRef != null) {
/*  95 */       if (image.isSmask())
/*  96 */         put(PdfName.SMASK, maskRef);
/*     */       else
/*  98 */         put(PdfName.MASK, maskRef);
/*     */     }
/* 100 */     if ((image.isMask()) && (image.isInverted()))
/* 101 */       put(PdfName.DECODE, new PdfLiteral("[1 0]"));
/* 102 */     if (image.isInterpolation())
/* 103 */       put(PdfName.INTERPOLATE, PdfBoolean.PDFTRUE);
/* 104 */     InputStream is = null;
/*     */     try
/*     */     {
/* 107 */       int[] transparency = image.getTransparency();
/* 108 */       if ((transparency != null) && (!image.isMask()) && (maskRef == null)) {
/* 109 */         StringBuilder s = new StringBuilder("[");
/* 110 */         for (int k = 0; k < transparency.length; k++)
/* 111 */           s.append(transparency[k]).append(" ");
/* 112 */         s.append("]");
/* 113 */         put(PdfName.MASK, new PdfLiteral(s.toString()));
/*     */       }
/*     */ 
/* 116 */       if (image.isImgRaw())
/*     */       {
/* 118 */         int colorspace = image.getColorspace();
/* 119 */         this.bytes = image.getRawData();
/* 120 */         put(PdfName.LENGTH, new PdfNumber(this.bytes.length));
/* 121 */         int bpc = image.getBpc();
/* 122 */         if (bpc > 255) {
/* 123 */           if (!image.isMask())
/* 124 */             put(PdfName.COLORSPACE, PdfName.DEVICEGRAY);
/* 125 */           put(PdfName.BITSPERCOMPONENT, new PdfNumber(1));
/* 126 */           put(PdfName.FILTER, PdfName.CCITTFAXDECODE);
/* 127 */           int k = bpc - 257;
/* 128 */           PdfDictionary decodeparms = new PdfDictionary();
/* 129 */           if (k != 0)
/* 130 */             decodeparms.put(PdfName.K, new PdfNumber(k));
/* 131 */           if ((colorspace & 0x1) != 0)
/* 132 */             decodeparms.put(PdfName.BLACKIS1, PdfBoolean.PDFTRUE);
/* 133 */           if ((colorspace & 0x2) != 0)
/* 134 */             decodeparms.put(PdfName.ENCODEDBYTEALIGN, PdfBoolean.PDFTRUE);
/* 135 */           if ((colorspace & 0x4) != 0)
/* 136 */             decodeparms.put(PdfName.ENDOFLINE, PdfBoolean.PDFTRUE);
/* 137 */           if ((colorspace & 0x8) != 0)
/* 138 */             decodeparms.put(PdfName.ENDOFBLOCK, PdfBoolean.PDFFALSE);
/* 139 */           decodeparms.put(PdfName.COLUMNS, new PdfNumber(image.getWidth()));
/* 140 */           decodeparms.put(PdfName.ROWS, new PdfNumber(image.getHeight()));
/* 141 */           put(PdfName.DECODEPARMS, decodeparms);
/*     */         }
/*     */         else {
/* 144 */           switch (colorspace) {
/*     */           case 1:
/* 146 */             put(PdfName.COLORSPACE, PdfName.DEVICEGRAY);
/* 147 */             if (image.isInverted())
/* 148 */               put(PdfName.DECODE, new PdfLiteral("[1 0]")); break;
/*     */           case 3:
/* 151 */             put(PdfName.COLORSPACE, PdfName.DEVICERGB);
/* 152 */             if (image.isInverted())
/* 153 */               put(PdfName.DECODE, new PdfLiteral("[1 0 1 0 1 0]")); break;
/*     */           case 2:
/*     */           case 4:
/*     */           default:
/* 157 */             put(PdfName.COLORSPACE, PdfName.DEVICECMYK);
/* 158 */             if (image.isInverted())
/* 159 */               put(PdfName.DECODE, new PdfLiteral("[1 0 1 0 1 0 1 0]")); break;
/*     */           }
/* 161 */           PdfDictionary additional = image.getAdditional();
/* 162 */           if (additional != null)
/* 163 */             putAll(additional);
/* 164 */           if ((image.isMask()) && ((image.getBpc() == 1) || (image.getBpc() > 8)))
/* 165 */             remove(PdfName.COLORSPACE);
/* 166 */           put(PdfName.BITSPERCOMPONENT, new PdfNumber(image.getBpc()));
/* 167 */           if (image.isDeflated())
/* 168 */             put(PdfName.FILTER, PdfName.FLATEDECODE);
/*     */           else
/* 170 */             flateCompress(image.getCompressionLevel());
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/*     */         String errorID;
/*     */         String errorID;
/* 177 */         if (image.getRawData() == null) {
/* 178 */           is = image.getUrl().openStream();
/* 179 */           errorID = image.getUrl().toString();
/*     */         }
/*     */         else {
/* 182 */           is = new ByteArrayInputStream(image.getRawData());
/* 183 */           errorID = "Byte array";
/*     */         }
/* 185 */         switch (image.type()) {
/*     */         case 32:
/* 187 */           put(PdfName.FILTER, PdfName.DCTDECODE);
/* 188 */           if (image.getColorTransform() == 0) {
/* 189 */             PdfDictionary decodeparms = new PdfDictionary();
/* 190 */             decodeparms.put(PdfName.COLORTRANSFORM, new PdfNumber(0));
/* 191 */             put(PdfName.DECODEPARMS, decodeparms);
/*     */           }
/* 193 */           switch (image.getColorspace()) {
/*     */           case 1:
/* 195 */             put(PdfName.COLORSPACE, PdfName.DEVICEGRAY);
/* 196 */             break;
/*     */           case 3:
/* 198 */             put(PdfName.COLORSPACE, PdfName.DEVICERGB);
/* 199 */             break;
/*     */           default:
/* 201 */             put(PdfName.COLORSPACE, PdfName.DEVICECMYK);
/* 202 */             if (image.isInverted())
/* 203 */               put(PdfName.DECODE, new PdfLiteral("[1 0 1 0 1 0 1 0]"));
/*     */             break;
/*     */           }
/* 206 */           put(PdfName.BITSPERCOMPONENT, new PdfNumber(8));
/* 207 */           if (image.getRawData() != null) { this.bytes = image.getRawData();
/* 209 */             put(PdfName.LENGTH, new PdfNumber(this.bytes.length));
/*     */             return; }
/* 212 */           this.streamBytes = new ByteArrayOutputStream();
/* 213 */           transferBytes(is, this.streamBytes, -1);
/* 214 */           break;
/*     */         case 33:
/* 216 */           put(PdfName.FILTER, PdfName.JPXDECODE);
/* 217 */           if (image.getColorspace() > 0) {
/* 218 */             switch (image.getColorspace()) {
/*     */             case 1:
/* 220 */               put(PdfName.COLORSPACE, PdfName.DEVICEGRAY);
/* 221 */               break;
/*     */             case 3:
/* 223 */               put(PdfName.COLORSPACE, PdfName.DEVICERGB);
/* 224 */               break;
/*     */             default:
/* 226 */               put(PdfName.COLORSPACE, PdfName.DEVICECMYK);
/*     */             }
/* 228 */             put(PdfName.BITSPERCOMPONENT, new PdfNumber(image.getBpc()));
/*     */           }
/* 230 */           if (image.getRawData() != null) { this.bytes = image.getRawData();
/* 232 */             put(PdfName.LENGTH, new PdfNumber(this.bytes.length));
/*     */             return; }
/* 235 */           this.streamBytes = new ByteArrayOutputStream();
/* 236 */           transferBytes(is, this.streamBytes, -1);
/* 237 */           break;
/*     */         case 36:
/* 239 */           put(PdfName.FILTER, PdfName.JBIG2DECODE);
/* 240 */           put(PdfName.COLORSPACE, PdfName.DEVICEGRAY);
/* 241 */           put(PdfName.BITSPERCOMPONENT, new PdfNumber(1));
/* 242 */           if (image.getRawData() != null) { this.bytes = image.getRawData();
/* 244 */             put(PdfName.LENGTH, new PdfNumber(this.bytes.length));
/*     */             return; }
/* 247 */           this.streamBytes = new ByteArrayOutputStream();
/* 248 */           transferBytes(is, this.streamBytes, -1);
/* 249 */           break;
/*     */         case 34:
/*     */         case 35:
/*     */         default:
/* 251 */           throw new BadPdfFormatException(MessageLocalization.getComposedMessage("1.is.an.unknown.image.format", new Object[] { errorID }));
/*     */         }
/* 253 */         if (image.getCompressionLevel() > 0)
/* 254 */           flateCompress(image.getCompressionLevel());
/* 255 */         put(PdfName.LENGTH, new PdfNumber(this.streamBytes.size()));
/*     */       }
/*     */     } catch (IOException ioe) {
/* 258 */       throw new BadPdfFormatException(ioe.getMessage());
/*     */     }
/*     */     finally {
/* 261 */       if (is != null)
/*     */         try {
/* 263 */           is.close();
/*     */         }
/*     */         catch (Exception ee)
/*     */         {
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   public PdfName name()
/*     */   {
/* 279 */     return this.name;
/*     */   }
/*     */ 
/*     */   public Image getImage() {
/* 283 */     return this.image;
/*     */   }
/*     */ 
/*     */   static void transferBytes(InputStream in, OutputStream out, int len) throws IOException {
/* 287 */     byte[] buffer = new byte[4096];
/* 288 */     if (len < 0) {
/* 289 */       len = 2147418112;
/*     */     }
/* 291 */     while (len != 0) {
/* 292 */       int size = in.read(buffer, 0, Math.min(len, 4096));
/* 293 */       if (size < 0)
/* 294 */         return;
/* 295 */       out.write(buffer, 0, size);
/* 296 */       len -= size;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void importAll(PdfImage dup) {
/* 301 */     this.name = dup.name;
/* 302 */     this.compressed = dup.compressed;
/* 303 */     this.compressionLevel = dup.compressionLevel;
/* 304 */     this.streamBytes = dup.streamBytes;
/* 305 */     this.bytes = dup.bytes;
/* 306 */     this.hashMap = dup.hashMap;
/*     */   }
/*     */ 
/*     */   private void generateImgResName(Image img)
/*     */   {
/* 315 */     this.name = new PdfName("img" + Long.toHexString(img.getMySerialId().longValue()));
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfImage
 * JD-Core Version:    0.6.2
 */