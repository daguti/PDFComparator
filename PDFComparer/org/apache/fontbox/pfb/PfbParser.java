/*     */ package org.apache.fontbox.pfb;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public class PfbParser
/*     */ {
/*     */   private static final int PFB_HEADER_LENGTH = 18;
/*     */   private static final int START_MARKER = 128;
/*     */   private static final int ASCII_MARKER = 1;
/*     */   private static final int BINARY_MARKER = 2;
/*  61 */   private static final int[] PFB_RECORDS = { 1, 2, 1 };
/*     */   private static final int BUFFER_SIZE = 65535;
/*     */   private byte[] pfbdata;
/*     */   private int[] lengths;
/*     */ 
/*     */   public PfbParser(String filename)
/*     */     throws IOException
/*     */   {
/*  91 */     this(new BufferedInputStream(new FileInputStream(filename), 65535));
/*     */   }
/*     */ 
/*     */   public PfbParser(InputStream in)
/*     */     throws IOException
/*     */   {
/* 101 */     byte[] pfb = readPfbInput(in);
/* 102 */     parsePfb(pfb);
/*     */   }
/*     */ 
/*     */   private void parsePfb(byte[] pfb)
/*     */     throws IOException
/*     */   {
/* 113 */     ByteArrayInputStream in = new ByteArrayInputStream(pfb);
/* 114 */     this.pfbdata = new byte[pfb.length - 18];
/* 115 */     this.lengths = new int[PFB_RECORDS.length];
/* 116 */     int pointer = 0;
/* 117 */     for (int records = 0; records < PFB_RECORDS.length; records++)
/*     */     {
/* 119 */       if (in.read() != 128)
/*     */       {
/* 121 */         throw new IOException("Start marker missing");
/*     */       }
/*     */ 
/* 124 */       if (in.read() != PFB_RECORDS[records])
/*     */       {
/* 126 */         throw new IOException("Incorrect record type");
/*     */       }
/*     */ 
/* 129 */       int size = in.read();
/* 130 */       size += (in.read() << 8);
/* 131 */       size += (in.read() << 16);
/* 132 */       size += (in.read() << 24);
/* 133 */       this.lengths[records] = size;
/* 134 */       int got = in.read(this.pfbdata, pointer, size);
/* 135 */       if (got < 0)
/*     */       {
/* 137 */         throw new EOFException();
/*     */       }
/* 139 */       pointer += got;
/*     */     }
/*     */   }
/*     */ 
/*     */   private byte[] readPfbInput(InputStream in)
/*     */     throws IOException
/*     */   {
/* 152 */     ByteArrayOutputStream out = new ByteArrayOutputStream();
/* 153 */     byte[] tmpbuf = new byte[65535];
/* 154 */     int amountRead = -1;
/* 155 */     while ((amountRead = in.read(tmpbuf)) != -1)
/*     */     {
/* 157 */       out.write(tmpbuf, 0, amountRead);
/*     */     }
/* 159 */     return out.toByteArray();
/*     */   }
/*     */ 
/*     */   public int[] getLengths()
/*     */   {
/* 168 */     return this.lengths;
/*     */   }
/*     */ 
/*     */   public byte[] getPfbdata()
/*     */   {
/* 177 */     return this.pfbdata;
/*     */   }
/*     */ 
/*     */   public InputStream getInputStream()
/*     */   {
/* 186 */     return new ByteArrayInputStream(this.pfbdata);
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/* 195 */     return this.pfbdata.length;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.pfb.PfbParser
 * JD-Core Version:    0.6.2
 */