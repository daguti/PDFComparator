/*     */ package com.itextpdf.text.pdf.codec;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.TreeMap;
/*     */ 
/*     */ public class TiffWriter
/*     */ {
/*     */   private TreeMap<Integer, FieldBase> ifd;
/*     */ 
/*     */   public TiffWriter()
/*     */   {
/*  55 */     this.ifd = new TreeMap();
/*     */   }
/*     */   public void addField(FieldBase field) {
/*  58 */     this.ifd.put(Integer.valueOf(field.getTag()), field);
/*     */   }
/*     */ 
/*     */   public int getIfdSize() {
/*  62 */     return 6 + this.ifd.size() * 12;
/*     */   }
/*     */ 
/*     */   public void writeFile(OutputStream stream) throws IOException {
/*  66 */     stream.write(77);
/*  67 */     stream.write(77);
/*  68 */     stream.write(0);
/*  69 */     stream.write(42);
/*  70 */     writeLong(8, stream);
/*  71 */     writeShort(this.ifd.size(), stream);
/*  72 */     int offset = 8 + getIfdSize();
/*  73 */     for (FieldBase field : this.ifd.values()) {
/*  74 */       int size = field.getValueSize();
/*  75 */       if (size > 4) {
/*  76 */         field.setOffset(offset);
/*  77 */         offset += size;
/*     */       }
/*  79 */       field.writeField(stream);
/*     */     }
/*  81 */     writeLong(0, stream);
/*  82 */     for (FieldBase field : this.ifd.values())
/*  83 */       field.writeValue(stream);
/*     */   }
/*     */ 
/*     */   public static void writeShort(int v, OutputStream stream)
/*     */     throws IOException
/*     */   {
/* 263 */     stream.write(v >> 8 & 0xFF);
/* 264 */     stream.write(v & 0xFF);
/*     */   }
/*     */ 
/*     */   public static void writeLong(int v, OutputStream stream) throws IOException {
/* 268 */     stream.write(v >> 24 & 0xFF);
/* 269 */     stream.write(v >> 16 & 0xFF);
/* 270 */     stream.write(v >> 8 & 0xFF);
/* 271 */     stream.write(v & 0xFF);
/*     */   }
/*     */ 
/*     */   public static void compressLZW(OutputStream stream, int predictor, byte[] b, int height, int samplesPerPixel, int stride) throws IOException
/*     */   {
/* 276 */     LZWCompressor lzwCompressor = new LZWCompressor(stream, 8, true);
/* 277 */     boolean usePredictor = predictor == 2;
/*     */ 
/* 279 */     if (!usePredictor) {
/* 280 */       lzwCompressor.compress(b, 0, b.length);
/*     */     } else {
/* 282 */       int off = 0;
/* 283 */       byte[] rowBuf = usePredictor ? new byte[stride] : null;
/* 284 */       for (int i = 0; i < height; i++) {
/* 285 */         System.arraycopy(b, off, rowBuf, 0, stride);
/* 286 */         for (int j = stride - 1; j >= samplesPerPixel; j--)
/*     */         {
/*     */           int tmp97_95 = j;
/*     */           byte[] tmp97_93 = rowBuf; tmp97_93[tmp97_95] = ((byte)(tmp97_93[tmp97_95] - rowBuf[(j - samplesPerPixel)]));
/*     */         }
/* 289 */         lzwCompressor.compress(rowBuf, 0, stride);
/* 290 */         off += stride;
/*     */       }
/*     */     }
/*     */ 
/* 294 */     lzwCompressor.flush();
/*     */   }
/*     */ 
/*     */   public static class FieldAscii extends TiffWriter.FieldBase
/*     */   {
/*     */     public FieldAscii(int tag, String values)
/*     */     {
/* 255 */       super(2, values.getBytes().length + 1);
/* 256 */       byte[] b = values.getBytes();
/* 257 */       this.data = new byte[b.length + 1];
/* 258 */       System.arraycopy(b, 0, this.data, 0, b.length);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class FieldImage extends TiffWriter.FieldBase
/*     */   {
/*     */     public FieldImage(byte[] values)
/*     */     {
/* 244 */       super(4, 1);
/* 245 */       this.data = values;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class FieldUndefined extends TiffWriter.FieldBase
/*     */   {
/*     */     public FieldUndefined(int tag, byte[] values)
/*     */     {
/* 233 */       super(7, values.length);
/* 234 */       this.data = values;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class FieldByte extends TiffWriter.FieldBase
/*     */   {
/*     */     public FieldByte(int tag, byte[] values)
/*     */     {
/* 222 */       super(1, values.length);
/* 223 */       this.data = values;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class FieldRational extends TiffWriter.FieldBase
/*     */   {
/*     */     public FieldRational(int tag, int[] value)
/*     */     {
/* 196 */       this(tag, new int[][] { value });
/*     */     }
/*     */ 
/*     */     public FieldRational(int tag, int[][] values) {
/* 200 */       super(5, values.length);
/* 201 */       this.data = new byte[values.length * 8];
/* 202 */       int ptr = 0;
/* 203 */       for (int[] value : values) {
/* 204 */         this.data[(ptr++)] = ((byte)(value[0] >> 24));
/* 205 */         this.data[(ptr++)] = ((byte)(value[0] >> 16));
/* 206 */         this.data[(ptr++)] = ((byte)(value[0] >> 8));
/* 207 */         this.data[(ptr++)] = ((byte)value[0]);
/* 208 */         this.data[(ptr++)] = ((byte)(value[1] >> 24));
/* 209 */         this.data[(ptr++)] = ((byte)(value[1] >> 16));
/* 210 */         this.data[(ptr++)] = ((byte)(value[1] >> 8));
/* 211 */         this.data[(ptr++)] = ((byte)value[1]);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class FieldLong extends TiffWriter.FieldBase
/*     */   {
/*     */     public FieldLong(int tag, int value)
/*     */     {
/* 169 */       super(4, 1);
/* 170 */       this.data = new byte[4];
/* 171 */       this.data[0] = ((byte)(value >> 24));
/* 172 */       this.data[1] = ((byte)(value >> 16));
/* 173 */       this.data[2] = ((byte)(value >> 8));
/* 174 */       this.data[3] = ((byte)value);
/*     */     }
/*     */ 
/*     */     public FieldLong(int tag, int[] values) {
/* 178 */       super(4, values.length);
/* 179 */       this.data = new byte[values.length * 4];
/* 180 */       int ptr = 0;
/* 181 */       for (int value : values) {
/* 182 */         this.data[(ptr++)] = ((byte)(value >> 24));
/* 183 */         this.data[(ptr++)] = ((byte)(value >> 16));
/* 184 */         this.data[(ptr++)] = ((byte)(value >> 8));
/* 185 */         this.data[(ptr++)] = ((byte)value);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class FieldShort extends TiffWriter.FieldBase
/*     */   {
/*     */     public FieldShort(int tag, int value)
/*     */     {
/* 146 */       super(3, 1);
/* 147 */       this.data = new byte[2];
/* 148 */       this.data[0] = ((byte)(value >> 8));
/* 149 */       this.data[1] = ((byte)value);
/*     */     }
/*     */ 
/*     */     public FieldShort(int tag, int[] values) {
/* 153 */       super(3, values.length);
/* 154 */       this.data = new byte[values.length * 2];
/* 155 */       int ptr = 0;
/* 156 */       for (int value : values) {
/* 157 */         this.data[(ptr++)] = ((byte)(value >> 8));
/* 158 */         this.data[(ptr++)] = ((byte)value);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static abstract class FieldBase
/*     */   {
/*     */     private int tag;
/*     */     private int fieldType;
/*     */     private int count;
/*     */     protected byte[] data;
/*     */     private int offset;
/*     */ 
/*     */     protected FieldBase(int tag, int fieldType, int count)
/*     */     {
/*  99 */       this.tag = tag;
/* 100 */       this.fieldType = fieldType;
/* 101 */       this.count = count;
/*     */     }
/*     */ 
/*     */     public int getValueSize() {
/* 105 */       return this.data.length + 1 & 0xFFFFFFFE;
/*     */     }
/*     */ 
/*     */     public int getTag() {
/* 109 */       return this.tag;
/*     */     }
/*     */ 
/*     */     public void setOffset(int offset) {
/* 113 */       this.offset = offset;
/*     */     }
/*     */ 
/*     */     public void writeField(OutputStream stream) throws IOException {
/* 117 */       TiffWriter.writeShort(this.tag, stream);
/* 118 */       TiffWriter.writeShort(this.fieldType, stream);
/* 119 */       TiffWriter.writeLong(this.count, stream);
/* 120 */       if (this.data.length <= 4) {
/* 121 */         stream.write(this.data);
/* 122 */         for (int k = this.data.length; k < 4; k++)
/* 123 */           stream.write(0);
/*     */       }
/*     */       else
/*     */       {
/* 127 */         TiffWriter.writeLong(this.offset, stream);
/*     */       }
/*     */     }
/*     */ 
/*     */     public void writeValue(OutputStream stream) throws IOException {
/* 132 */       if (this.data.length <= 4)
/* 133 */         return;
/* 134 */       stream.write(this.data);
/* 135 */       if ((this.data.length & 0x1) == 1)
/* 136 */         stream.write(0);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.codec.TiffWriter
 * JD-Core Version:    0.6.2
 */