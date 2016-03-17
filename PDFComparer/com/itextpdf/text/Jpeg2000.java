/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class Jpeg2000 extends Image
/*     */ {
/*     */   public static final int JP2_JP = 1783636000;
/*     */   public static final int JP2_IHDR = 1768449138;
/*     */   public static final int JPIP_JPIP = 1785751920;
/*     */   public static final int JP2_FTYP = 1718909296;
/*     */   public static final int JP2_JP2H = 1785737832;
/*     */   public static final int JP2_COLR = 1668246642;
/*     */   public static final int JP2_JP2C = 1785737827;
/*     */   public static final int JP2_URL = 1970433056;
/*     */   public static final int JP2_DBTL = 1685348972;
/*     */   public static final int JP2_BPCC = 1651532643;
/*     */   public static final int JP2_JP2 = 1785737760;
/*     */   InputStream inp;
/*     */   int boxLength;
/*     */   int boxType;
/*     */   int numOfComps;
/*  83 */   ArrayList<ColorSpecBox> colorSpecBoxes = null;
/*  84 */   boolean isJp2 = false;
/*     */   byte[] bpcBoxData;
/*     */ 
/*     */   Jpeg2000(Image image)
/*     */   {
/*  90 */     super(image);
/*  91 */     if ((image instanceof Jpeg2000)) {
/*  92 */       Jpeg2000 jpeg2000 = (Jpeg2000)image;
/*  93 */       this.numOfComps = jpeg2000.numOfComps;
/*  94 */       if (this.colorSpecBoxes != null)
/*  95 */         this.colorSpecBoxes = ((ArrayList)jpeg2000.colorSpecBoxes.clone());
/*  96 */       this.isJp2 = jpeg2000.isJp2;
/*  97 */       if (this.bpcBoxData != null)
/*  98 */         this.bpcBoxData = ((byte[])jpeg2000.bpcBoxData.clone());
/*     */     }
/*     */   }
/*     */ 
/*     */   public Jpeg2000(URL url)
/*     */     throws BadElementException, IOException
/*     */   {
/* 111 */     super(url);
/* 112 */     processParameters();
/*     */   }
/*     */ 
/*     */   public Jpeg2000(byte[] img)
/*     */     throws BadElementException, IOException
/*     */   {
/* 124 */     super((URL)null);
/* 125 */     this.rawData = img;
/* 126 */     this.originalData = img;
/* 127 */     processParameters();
/*     */   }
/*     */ 
/*     */   public Jpeg2000(byte[] img, float width, float height)
/*     */     throws BadElementException, IOException
/*     */   {
/* 141 */     this(img);
/* 142 */     this.scaledWidth = width;
/* 143 */     this.scaledHeight = height;
/*     */   }
/*     */ 
/*     */   private int cio_read(int n) throws IOException {
/* 147 */     int v = 0;
/* 148 */     for (int i = n - 1; i >= 0; i--) {
/* 149 */       v += (this.inp.read() << (i << 3));
/*     */     }
/* 151 */     return v;
/*     */   }
/*     */ 
/*     */   public void jp2_read_boxhdr() throws IOException {
/* 155 */     this.boxLength = cio_read(4);
/* 156 */     this.boxType = cio_read(4);
/* 157 */     if (this.boxLength == 1) {
/* 158 */       if (cio_read(4) != 0) {
/* 159 */         throw new IOException(MessageLocalization.getComposedMessage("cannot.handle.box.sizes.higher.than.2.32", new Object[0]));
/*     */       }
/* 161 */       this.boxLength = cio_read(4);
/* 162 */       if (this.boxLength == 0)
/* 163 */         throw new IOException(MessageLocalization.getComposedMessage("unsupported.box.size.eq.eq.0", new Object[0]));
/*     */     }
/* 165 */     else if (this.boxLength == 0) {
/* 166 */       throw new ZeroBoxSizeException(MessageLocalization.getComposedMessage("unsupported.box.size.eq.eq.0", new Object[0]));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void processParameters()
/*     */     throws IOException
/*     */   {
/* 175 */     this.type = 33;
/* 176 */     this.originalType = 8;
/* 177 */     this.inp = null;
/*     */     try {
/* 179 */       if (this.rawData == null) {
/* 180 */         this.inp = this.url.openStream();
/*     */       }
/*     */       else {
/* 183 */         this.inp = new ByteArrayInputStream(this.rawData);
/*     */       }
/* 185 */       this.boxLength = cio_read(4);
/* 186 */       if (this.boxLength == 12) {
/* 187 */         this.isJp2 = true;
/* 188 */         this.boxType = cio_read(4);
/* 189 */         if (1783636000 != this.boxType) {
/* 190 */           throw new IOException(MessageLocalization.getComposedMessage("expected.jp.marker", new Object[0]));
/*     */         }
/* 192 */         if (218793738 != cio_read(4)) {
/* 193 */           throw new IOException(MessageLocalization.getComposedMessage("error.with.jp.marker", new Object[0]));
/*     */         }
/*     */ 
/* 196 */         jp2_read_boxhdr();
/* 197 */         if (1718909296 != this.boxType) {
/* 198 */           throw new IOException(MessageLocalization.getComposedMessage("expected.ftyp.marker", new Object[0]));
/*     */         }
/* 200 */         Utilities.skip(this.inp, this.boxLength - 8);
/* 201 */         jp2_read_boxhdr();
/*     */         do
/* 203 */           if (1785737832 != this.boxType) {
/* 204 */             if (this.boxType == 1785737827) {
/* 205 */               throw new IOException(MessageLocalization.getComposedMessage("expected.jp2h.marker", new Object[0]));
/*     */             }
/* 207 */             Utilities.skip(this.inp, this.boxLength - 8);
/* 208 */             jp2_read_boxhdr();
/*     */           }
/* 210 */         while (1785737832 != this.boxType);
/* 211 */         jp2_read_boxhdr();
/* 212 */         if (1768449138 != this.boxType) {
/* 213 */           throw new IOException(MessageLocalization.getComposedMessage("expected.ihdr.marker", new Object[0]));
/*     */         }
/* 215 */         this.scaledHeight = cio_read(4);
/* 216 */         setTop(this.scaledHeight);
/* 217 */         this.scaledWidth = cio_read(4);
/* 218 */         setRight(this.scaledWidth);
/* 219 */         this.numOfComps = cio_read(2);
/* 220 */         this.bpc = -1;
/* 221 */         this.bpc = cio_read(1);
/*     */ 
/* 223 */         Utilities.skip(this.inp, 3);
/*     */ 
/* 225 */         jp2_read_boxhdr();
/* 226 */         if (this.boxType == 1651532643) {
/* 227 */           this.bpcBoxData = new byte[this.boxLength - 8];
/* 228 */           this.inp.read(this.bpcBoxData, 0, this.boxLength - 8);
/* 229 */         } else if (this.boxType == 1668246642) {
/*     */           do {
/* 231 */             if (this.colorSpecBoxes == null)
/* 232 */               this.colorSpecBoxes = new ArrayList();
/* 233 */             this.colorSpecBoxes.add(jp2_read_colr());
/*     */             try {
/* 235 */               jp2_read_boxhdr();
/*     */             } catch (ZeroBoxSizeException ioe) {
/*     */             }
/*     */           }
/* 239 */           while (1668246642 == this.boxType);
/*     */         }
/*     */       }
/* 242 */       else if (this.boxLength == -11534511) {
/* 243 */         Utilities.skip(this.inp, 4);
/* 244 */         int x1 = cio_read(4);
/* 245 */         int y1 = cio_read(4);
/* 246 */         int x0 = cio_read(4);
/* 247 */         int y0 = cio_read(4);
/* 248 */         Utilities.skip(this.inp, 16);
/* 249 */         this.colorspace = cio_read(2);
/* 250 */         this.bpc = 8;
/* 251 */         this.scaledHeight = (y1 - y0);
/* 252 */         setTop(this.scaledHeight);
/* 253 */         this.scaledWidth = (x1 - x0);
/* 254 */         setRight(this.scaledWidth);
/*     */       }
/*     */       else {
/* 257 */         throw new IOException(MessageLocalization.getComposedMessage("not.a.valid.jpeg2000.file", new Object[0]));
/*     */       }
/*     */     }
/*     */     finally {
/* 261 */       if (this.inp != null) {
/*     */         try { this.inp.close(); } catch (Exception e) {
/* 263 */         }this.inp = null;
/*     */       }
/*     */     }
/* 266 */     this.plainWidth = getWidth();
/* 267 */     this.plainHeight = getHeight();
/*     */   }
/*     */ 
/*     */   private ColorSpecBox jp2_read_colr() throws IOException {
/* 271 */     int readBytes = 8;
/* 272 */     ColorSpecBox colr = new ColorSpecBox();
/* 273 */     for (int i = 0; i < 3; i++) {
/* 274 */       colr.add(Integer.valueOf(cio_read(1)));
/* 275 */       readBytes++;
/*     */     }
/* 277 */     if (colr.getMeth() == 1) {
/* 278 */       colr.add(Integer.valueOf(cio_read(4)));
/* 279 */       readBytes += 4;
/*     */     } else {
/* 281 */       colr.add(Integer.valueOf(0));
/*     */     }
/*     */ 
/* 284 */     if (this.boxLength - readBytes > 0) {
/* 285 */       byte[] colorProfile = new byte[this.boxLength - readBytes];
/* 286 */       this.inp.read(colorProfile, 0, this.boxLength - readBytes);
/* 287 */       colr.setColorProfile(colorProfile);
/*     */     }
/* 289 */     return colr;
/*     */   }
/*     */ 
/*     */   public int getNumOfComps() {
/* 293 */     return this.numOfComps;
/*     */   }
/*     */ 
/*     */   public byte[] getBpcBoxData() {
/* 297 */     return this.bpcBoxData;
/*     */   }
/*     */ 
/*     */   public ArrayList<ColorSpecBox> getColorSpecBoxes() {
/* 301 */     return this.colorSpecBoxes;
/*     */   }
/*     */ 
/*     */   public boolean isJp2()
/*     */   {
/* 308 */     return this.isJp2;
/*     */   }
/*     */ 
/*     */   private class ZeroBoxSizeException extends IOException
/*     */   {
/*     */     public ZeroBoxSizeException()
/*     */     {
/*     */     }
/*     */ 
/*     */     public ZeroBoxSizeException(String s)
/*     */     {
/* 345 */       super();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class ColorSpecBox extends ArrayList<Integer>
/*     */   {
/*     */     private byte[] colorProfile;
/*     */ 
/*     */     public int getMeth()
/*     */     {
/* 315 */       return ((Integer)get(0)).intValue();
/*     */     }
/*     */ 
/*     */     public int getPrec() {
/* 319 */       return ((Integer)get(1)).intValue();
/*     */     }
/*     */ 
/*     */     public int getApprox() {
/* 323 */       return ((Integer)get(2)).intValue();
/*     */     }
/*     */ 
/*     */     public int getEnumCs() {
/* 327 */       return ((Integer)get(3)).intValue();
/*     */     }
/*     */ 
/*     */     public byte[] getColorProfile() {
/* 331 */       return this.colorProfile;
/*     */     }
/*     */ 
/*     */     void setColorProfile(byte[] colorProfile) {
/* 335 */       this.colorProfile = colorProfile;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.Jpeg2000
 * JD-Core Version:    0.6.2
 */