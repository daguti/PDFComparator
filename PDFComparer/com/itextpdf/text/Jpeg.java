/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.pdf.ICC_Profile;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ 
/*     */ public class Jpeg extends Image
/*     */ {
/*     */   public static final int NOT_A_MARKER = -1;
/*     */   public static final int VALID_MARKER = 0;
/*  73 */   public static final int[] VALID_MARKERS = { 192, 193, 194 };
/*     */   public static final int UNSUPPORTED_MARKER = 1;
/*  79 */   public static final int[] UNSUPPORTED_MARKERS = { 195, 197, 198, 199, 200, 201, 202, 203, 205, 206, 207 };
/*     */   public static final int NOPARAM_MARKER = 2;
/*  85 */   public static final int[] NOPARAM_MARKERS = { 208, 209, 210, 211, 212, 213, 214, 215, 216, 1 };
/*     */   public static final int M_APP0 = 224;
/*     */   public static final int M_APP2 = 226;
/*     */   public static final int M_APPE = 238;
/*     */   public static final int M_APPD = 237;
/*  97 */   public static final byte[] JFIF_ID = { 74, 70, 73, 70, 0 };
/*     */ 
/* 100 */   public static final byte[] PS_8BIM_RESO = { 56, 66, 73, 77, 3, -19 };
/*     */   private byte[][] icc;
/*     */ 
/*     */   Jpeg(Image image)
/*     */   {
/* 106 */     super(image);
/*     */   }
/*     */ 
/*     */   public Jpeg(URL url)
/*     */     throws BadElementException, IOException
/*     */   {
/* 117 */     super(url);
/* 118 */     processParameters();
/*     */   }
/*     */ 
/*     */   public Jpeg(byte[] img)
/*     */     throws BadElementException, IOException
/*     */   {
/* 130 */     super((URL)null);
/* 131 */     this.rawData = img;
/* 132 */     this.originalData = img;
/* 133 */     processParameters();
/*     */   }
/*     */ 
/*     */   public Jpeg(byte[] img, float width, float height)
/*     */     throws BadElementException, IOException
/*     */   {
/* 147 */     this(img);
/* 148 */     this.scaledWidth = width;
/* 149 */     this.scaledHeight = height;
/*     */   }
/*     */ 
/*     */   private static final int getShort(InputStream is)
/*     */     throws IOException
/*     */   {
/* 162 */     return (is.read() << 8) + is.read();
/*     */   }
/*     */ 
/*     */   private static final int marker(int marker)
/*     */   {
/* 172 */     for (int i = 0; i < VALID_MARKERS.length; i++) {
/* 173 */       if (marker == VALID_MARKERS[i]) {
/* 174 */         return 0;
/*     */       }
/*     */     }
/* 177 */     for (int i = 0; i < NOPARAM_MARKERS.length; i++) {
/* 178 */       if (marker == NOPARAM_MARKERS[i]) {
/* 179 */         return 2;
/*     */       }
/*     */     }
/* 182 */     for (int i = 0; i < UNSUPPORTED_MARKERS.length; i++) {
/* 183 */       if (marker == UNSUPPORTED_MARKERS[i]) {
/* 184 */         return 1;
/*     */       }
/*     */     }
/* 187 */     return -1;
/*     */   }
/*     */ 
/*     */   private void processParameters()
/*     */     throws BadElementException, IOException
/*     */   {
/* 198 */     this.type = 32;
/* 199 */     this.originalType = 1;
/* 200 */     InputStream is = null;
/*     */     try
/*     */     {
/*     */       String errorID;
/*     */       String errorID;
/* 203 */       if (this.rawData == null) {
/* 204 */         is = this.url.openStream();
/* 205 */         errorID = this.url.toString();
/*     */       }
/*     */       else {
/* 208 */         is = new ByteArrayInputStream(this.rawData);
/* 209 */         errorID = "Byte array";
/*     */       }
/* 211 */       if ((is.read() != 255) || (is.read() != 216)) {
/* 212 */         throw new BadElementException(MessageLocalization.getComposedMessage("1.is.not.a.valid.jpeg.file", new Object[] { errorID }));
/*     */       }
/* 214 */       boolean firstPass = true;
/*     */       while (true)
/*     */       {
/* 217 */         int v = is.read();
/* 218 */         if (v < 0)
/* 219 */           throw new IOException(MessageLocalization.getComposedMessage("premature.eof.while.reading.jpg", new Object[0]));
/* 220 */         if (v == 255) {
/* 221 */           int marker = is.read();
/* 222 */           if ((firstPass) && (marker == 224)) {
/* 223 */             firstPass = false;
/* 224 */             int len = getShort(is);
/* 225 */             if (len < 16) {
/* 226 */               Utilities.skip(is, len - 2);
/*     */             }
/*     */             else {
/* 229 */               byte[] bcomp = new byte[JFIF_ID.length];
/* 230 */               int r = is.read(bcomp);
/* 231 */               if (r != bcomp.length)
/* 232 */                 throw new BadElementException(MessageLocalization.getComposedMessage("1.corrupted.jfif.marker", new Object[] { errorID }));
/* 233 */               boolean found = true;
/* 234 */               for (int k = 0; k < bcomp.length; k++) {
/* 235 */                 if (bcomp[k] != JFIF_ID[k]) {
/* 236 */                   found = false;
/* 237 */                   break;
/*     */                 }
/*     */               }
/* 240 */               if (!found) {
/* 241 */                 Utilities.skip(is, len - 2 - bcomp.length);
/*     */               }
/*     */               else {
/* 244 */                 Utilities.skip(is, 2);
/* 245 */                 int units = is.read();
/* 246 */                 int dx = getShort(is);
/* 247 */                 int dy = getShort(is);
/* 248 */                 if (units == 1) {
/* 249 */                   this.dpiX = dx;
/* 250 */                   this.dpiY = dy;
/*     */                 }
/* 252 */                 else if (units == 2) {
/* 253 */                   this.dpiX = ((int)(dx * 2.54F + 0.5F));
/* 254 */                   this.dpiY = ((int)(dy * 2.54F + 0.5F));
/*     */                 }
/* 256 */                 Utilities.skip(is, len - 2 - bcomp.length - 7);
/*     */               }
/*     */             }
/* 259 */           } else if (marker == 238) {
/* 260 */             int len = getShort(is) - 2;
/* 261 */             byte[] byteappe = new byte[len];
/* 262 */             for (int k = 0; k < len; k++) {
/* 263 */               byteappe[k] = ((byte)is.read());
/*     */             }
/* 265 */             if (byteappe.length >= 12) {
/* 266 */               String appe = new String(byteappe, 0, 5, "ISO-8859-1");
/* 267 */               if (appe.equals("Adobe")) {
/* 268 */                 this.invert = true;
/*     */               }
/*     */             }
/*     */ 
/*     */           }
/* 273 */           else if (marker == 226) {
/* 274 */             int len = getShort(is) - 2;
/* 275 */             byte[] byteapp2 = new byte[len];
/* 276 */             for (int k = 0; k < len; k++) {
/* 277 */               byteapp2[k] = ((byte)is.read());
/*     */             }
/* 279 */             if (byteapp2.length >= 14) {
/* 280 */               String app2 = new String(byteapp2, 0, 11, "ISO-8859-1");
/* 281 */               if (app2.equals("ICC_PROFILE")) {
/* 282 */                 int order = byteapp2[12] & 0xFF;
/* 283 */                 int count = byteapp2[13] & 0xFF;
/*     */ 
/* 285 */                 if (order < 1)
/* 286 */                   order = 1;
/* 287 */                 if (count < 1)
/* 288 */                   count = 1;
/* 289 */                 if (this.icc == null)
/* 290 */                   this.icc = new byte[count][];
/* 291 */                 this.icc[(order - 1)] = byteapp2;
/*     */               }
/*     */             }
/*     */ 
/*     */           }
/* 296 */           else if (marker == 237) {
/* 297 */             int len = getShort(is) - 2;
/* 298 */             byte[] byteappd = new byte[len];
/* 299 */             for (int k = 0; k < len; k++) {
/* 300 */               byteappd[k] = ((byte)is.read());
/*     */             }
/*     */ 
/* 303 */             int k = 0;
/* 304 */             for (k = 0; k < len - PS_8BIM_RESO.length; k++) {
/* 305 */               boolean found = true;
/* 306 */               for (int j = 0; j < PS_8BIM_RESO.length; j++) {
/* 307 */                 if (byteappd[(k + j)] != PS_8BIM_RESO[j]) {
/* 308 */                   found = false;
/* 309 */                   break;
/*     */                 }
/*     */               }
/* 312 */               if (found) {
/*     */                 break;
/*     */               }
/*     */             }
/* 316 */             k += PS_8BIM_RESO.length;
/* 317 */             if (k < len - PS_8BIM_RESO.length)
/*     */             {
/* 320 */               byte namelength = byteappd[k];
/*     */ 
/* 322 */               namelength = (byte)(namelength + 1);
/*     */ 
/* 324 */               if (namelength % 2 == 1) {
/* 325 */                 namelength = (byte)(namelength + 1);
/*     */               }
/* 327 */               k += namelength;
/*     */ 
/* 329 */               int resosize = (byteappd[k] << 24) + (byteappd[(k + 1)] << 16) + (byteappd[(k + 2)] << 8) + byteappd[(k + 3)];
/*     */ 
/* 331 */               if (resosize == 16)
/*     */               {
/* 336 */                 k += 4;
/* 337 */                 int dx = (byteappd[k] << 8) + (byteappd[(k + 1)] & 0xFF);
/* 338 */                 k += 2;
/*     */ 
/* 340 */                 k += 2;
/* 341 */                 int unitsx = (byteappd[k] << 8) + (byteappd[(k + 1)] & 0xFF);
/* 342 */                 k += 2;
/*     */ 
/* 344 */                 k += 2;
/* 345 */                 int dy = (byteappd[k] << 8) + (byteappd[(k + 1)] & 0xFF);
/* 346 */                 k += 2;
/*     */ 
/* 348 */                 k += 2;
/* 349 */                 int unitsy = (byteappd[k] << 8) + (byteappd[(k + 1)] & 0xFF);
/*     */ 
/* 351 */                 if ((unitsx == 1) || (unitsx == 2)) {
/* 352 */                   dx = unitsx == 2 ? (int)(dx * 2.54F + 0.5F) : dx;
/*     */ 
/* 354 */                   if ((this.dpiX == 0) || (this.dpiX == dx))
/*     */                   {
/* 358 */                     this.dpiX = dx;
/*     */                   }
/*     */                 }
/* 360 */                 if ((unitsy == 1) || (unitsy == 2)) {
/* 361 */                   dy = unitsy == 2 ? (int)(dy * 2.54F + 0.5F) : dy;
/*     */ 
/* 363 */                   if ((this.dpiY == 0) || (this.dpiY == dy))
/*     */                   {
/* 367 */                     this.dpiY = dy;
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */           } else { firstPass = false;
/* 373 */             int markertype = marker(marker);
/* 374 */             if (markertype == 0) {
/* 375 */               Utilities.skip(is, 2);
/* 376 */               if (is.read() != 8) {
/* 377 */                 throw new BadElementException(MessageLocalization.getComposedMessage("1.must.have.8.bits.per.component", new Object[] { errorID }));
/*     */               }
/* 379 */               this.scaledHeight = getShort(is);
/* 380 */               setTop(this.scaledHeight);
/* 381 */               this.scaledWidth = getShort(is);
/* 382 */               setRight(this.scaledWidth);
/* 383 */               this.colorspace = is.read();
/* 384 */               this.bpc = 8;
/* 385 */               break;
/*     */             }
/* 387 */             if (markertype == 1) {
/* 388 */               throw new BadElementException(MessageLocalization.getComposedMessage("1.unsupported.jpeg.marker.2", new Object[] { errorID, String.valueOf(marker) }));
/*     */             }
/* 390 */             if (markertype != 2)
/* 391 */               Utilities.skip(is, getShort(is) - 2); }
/*     */         }
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/* 397 */       if (is != null) {
/* 398 */         is.close();
/*     */       }
/*     */     }
/* 401 */     this.plainWidth = getWidth();
/* 402 */     this.plainHeight = getHeight();
/* 403 */     if (this.icc != null) {
/* 404 */       int total = 0;
/* 405 */       for (int k = 0; k < this.icc.length; k++) {
/* 406 */         if (this.icc[k] == null) {
/* 407 */           this.icc = ((byte[][])null);
/* 408 */           return;
/*     */         }
/* 410 */         total += this.icc[k].length - 14;
/*     */       }
/* 412 */       byte[] ficc = new byte[total];
/* 413 */       total = 0;
/* 414 */       for (int k = 0; k < this.icc.length; k++) {
/* 415 */         System.arraycopy(this.icc[k], 14, ficc, total, this.icc[k].length - 14);
/* 416 */         total += this.icc[k].length - 14;
/*     */       }
/*     */       try {
/* 419 */         ICC_Profile icc_prof = ICC_Profile.getInstance(ficc, this.colorspace);
/* 420 */         tagICC(icc_prof);
/*     */       }
/*     */       catch (IllegalArgumentException e)
/*     */       {
/*     */       }
/* 425 */       this.icc = ((byte[][])null);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.Jpeg
 * JD-Core Version:    0.6.2
 */