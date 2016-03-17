/*     */ package org.apache.pdfbox.encoding;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetDecoder;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import java.nio.charset.CoderResult;
/*     */ 
/*     */ public class SingleByteCharset extends Charset
/*     */ {
/*     */   protected static final char REPLACEMENT_CHARACTER = '�';
/*     */   private final char[] toUnicodeMap;
/*     */   private byte[][] toByteMap;
/*     */ 
/*     */   protected SingleByteCharset(String canonicalName, String[] aliases, char[] toUnicodeMap)
/*     */   {
/*  48 */     super(canonicalName, aliases);
/*  49 */     if (toUnicodeMap.length > 256)
/*     */     {
/*  51 */       throw new IllegalArgumentException("Single-byte encodings may have at most 256 characters.");
/*     */     }
/*     */ 
/*  54 */     this.toUnicodeMap = new char[256];
/*  55 */     System.arraycopy(toUnicodeMap, 0, this.toUnicodeMap, 0, toUnicodeMap.length);
/*     */ 
/*  57 */     initInverseMap();
/*     */   }
/*     */ 
/*     */   private void initInverseMap()
/*     */   {
/*  62 */     this.toByteMap = new byte[256][];
/*  63 */     if (this.toUnicodeMap[0] != 0)
/*     */     {
/*  65 */       throw new IllegalArgumentException("First character in map must be a NUL (0x0000) character.");
/*     */     }
/*     */ 
/*  70 */     int i = 1; for (int len = this.toUnicodeMap.length; i < len; i++)
/*     */     {
/*  72 */       char ch = this.toUnicodeMap[i];
/*  73 */       if (ch != 65533)
/*     */       {
/*  77 */         int upper = ch >> '\b';
/*  78 */         int lower = ch & 0xFF;
/*  79 */         if (upper > 255)
/*     */         {
/*  81 */           throw new IllegalArgumentException("Not a compatible character: " + ch + " (" + Integer.toHexString(ch) + ")");
/*     */         }
/*     */ 
/*  84 */         byte[] map = this.toByteMap[upper];
/*  85 */         if (map == null)
/*     */         {
/*  87 */           map = new byte[256];
/*  88 */           this.toByteMap[upper] = map;
/*     */         }
/*  90 */         map[lower] = ((byte)(i & 0xFF));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean contains(Charset cs)
/*     */   {
/*  98 */     return cs.getClass() == getClass();
/*     */   }
/*     */ 
/*     */   public CharsetDecoder newDecoder()
/*     */   {
/* 105 */     return new Decoder();
/*     */   }
/*     */ 
/*     */   public CharsetEncoder newEncoder()
/*     */   {
/* 112 */     return new Encoder();
/*     */   }
/*     */ 
/*     */   private class Encoder extends CharsetEncoder
/*     */   {
/*     */     protected Encoder()
/*     */     {
/* 156 */       super(1.0F, 1.0F);
/*     */     }
/*     */ 
/*     */     protected CoderResult encodeLoop(CharBuffer in, ByteBuffer out)
/*     */     {
/* 162 */       while (in.hasRemaining())
/*     */       {
/* 164 */         int ch = in.get();
/*     */ 
/* 166 */         if (!out.hasRemaining())
/*     */         {
/* 168 */           in.position(in.position() - 1);
/* 169 */           return CoderResult.OVERFLOW;
/*     */         }
/*     */ 
/* 172 */         int upper = ch >> 8;
/* 173 */         int lower = ch & 0xFF;
/* 174 */         if (upper > 255)
/*     */         {
/* 176 */           in.position(in.position() - 1);
/* 177 */           return CoderResult.unmappableForLength(1);
/*     */         }
/* 179 */         byte[] map = SingleByteCharset.this.toByteMap[upper];
/* 180 */         if (map == null)
/*     */         {
/* 182 */           in.position(in.position() - 1);
/* 183 */           return CoderResult.unmappableForLength(1);
/*     */         }
/* 185 */         byte b = map[lower];
/* 186 */         if (b == 0)
/*     */         {
/* 188 */           in.position(in.position() - 1);
/* 189 */           return CoderResult.unmappableForLength(1);
/*     */         }
/*     */ 
/* 192 */         out.put(b);
/*     */       }
/* 194 */       return CoderResult.UNDERFLOW;
/*     */     }
/*     */   }
/*     */ 
/*     */   private class Decoder extends CharsetDecoder
/*     */   {
/*     */     protected Decoder()
/*     */     {
/* 121 */       super(1.0F, 1.0F);
/*     */     }
/*     */ 
/*     */     protected CoderResult decodeLoop(ByteBuffer in, CharBuffer out)
/*     */     {
/* 127 */       while (in.hasRemaining())
/*     */       {
/* 129 */         byte b = in.get();
/*     */ 
/* 132 */         if (!out.hasRemaining())
/*     */         {
/* 134 */           in.position(in.position() - 1);
/* 135 */           return CoderResult.OVERFLOW;
/*     */         }
/* 137 */         char ch = SingleByteCharset.this.toUnicodeMap[(b & 0xFF)];
/* 138 */         if (ch == 65533)
/*     */         {
/* 140 */           in.position(in.position() - 1);
/* 141 */           return CoderResult.unmappableForLength(1);
/*     */         }
/* 143 */         out.put(ch);
/*     */       }
/* 145 */       return CoderResult.UNDERFLOW;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.encoding.SingleByteCharset
 * JD-Core Version:    0.6.2
 */