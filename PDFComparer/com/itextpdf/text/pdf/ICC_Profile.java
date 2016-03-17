/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class ICC_Profile
/*     */ {
/*     */   protected byte[] data;
/*     */   protected int numComponents;
/*  58 */   private static HashMap<String, Integer> cstags = new HashMap();
/*     */ 
/*     */   public static ICC_Profile getInstance(byte[] data, int numComponents)
/*     */   {
/*  64 */     if ((data.length < 128) || (data[36] != 97) || (data[37] != 99) || (data[38] != 115) || (data[39] != 112))
/*     */     {
/*  66 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("invalid.icc.profile", new Object[0]));
/*     */     }try {
/*  68 */       ICC_Profile icc = new ICC_Profile();
/*  69 */       icc.data = data;
/*     */ 
/*  71 */       Integer cs = (Integer)cstags.get(new String(data, 16, 4, "US-ASCII"));
/*  72 */       int nc = cs == null ? 0 : cs.intValue();
/*  73 */       icc.numComponents = nc;
/*     */ 
/*  75 */       if (nc != numComponents) {
/*  76 */         throw new IllegalArgumentException("ICC profile contains " + nc + " component(s), the image data contains " + numComponents + " component(s)");
/*     */       }
/*  78 */       return icc;
/*     */     } catch (UnsupportedEncodingException e) {
/*  80 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static ICC_Profile getInstance(byte[] data)
/*     */   {
/*     */     try {
/*  87 */       Integer cs = (Integer)cstags.get(new String(data, 16, 4, "US-ASCII"));
/*  88 */       int numComponents = cs == null ? 0 : cs.intValue();
/*  89 */       return getInstance(data, numComponents);
/*     */     } catch (UnsupportedEncodingException e) {
/*  91 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static ICC_Profile getInstance(InputStream file) {
/*     */     try {
/*  97 */       byte[] head = new byte[''];
/*  98 */       int remain = head.length;
/*  99 */       int ptr = 0;
/* 100 */       while (remain > 0) {
/* 101 */         int n = file.read(head, ptr, remain);
/* 102 */         if (n < 0)
/* 103 */           throw new IllegalArgumentException(MessageLocalization.getComposedMessage("invalid.icc.profile", new Object[0]));
/* 104 */         remain -= n;
/* 105 */         ptr += n;
/*     */       }
/* 107 */       if ((head[36] != 97) || (head[37] != 99) || (head[38] != 115) || (head[39] != 112))
/*     */       {
/* 109 */         throw new IllegalArgumentException(MessageLocalization.getComposedMessage("invalid.icc.profile", new Object[0]));
/* 110 */       }remain = (head[0] & 0xFF) << 24 | (head[1] & 0xFF) << 16 | (head[2] & 0xFF) << 8 | head[3] & 0xFF;
/*     */ 
/* 112 */       byte[] icc = new byte[remain];
/* 113 */       System.arraycopy(head, 0, icc, 0, head.length);
/* 114 */       remain -= head.length;
/* 115 */       ptr = head.length;
/* 116 */       while (remain > 0) {
/* 117 */         int n = file.read(icc, ptr, remain);
/* 118 */         if (n < 0)
/* 119 */           throw new IllegalArgumentException(MessageLocalization.getComposedMessage("invalid.icc.profile", new Object[0]));
/* 120 */         remain -= n;
/* 121 */         ptr += n;
/*     */       }
/* 123 */       return getInstance(icc);
/*     */     }
/*     */     catch (Exception ex) {
/* 126 */       throw new ExceptionConverter(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static ICC_Profile GetInstance(String fname) {
/* 131 */     FileInputStream fs = null;
/*     */     try {
/* 133 */       fs = new FileInputStream(fname);
/* 134 */       ICC_Profile icc = getInstance(fs);
/* 135 */       return icc;
/*     */     }
/*     */     catch (Exception ex) {
/* 138 */       throw new ExceptionConverter(ex);
/*     */     } finally {
/*     */       try {
/* 141 */         fs.close(); } catch (Exception x) {
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/* 146 */   public byte[] getData() { return this.data; }
/*     */ 
/*     */   public int getNumComponents()
/*     */   {
/* 150 */     return this.numComponents;
/*     */   }
/*     */ 
/*     */   static {
/* 154 */     cstags.put("XYZ ", Integer.valueOf(3));
/* 155 */     cstags.put("Lab ", Integer.valueOf(3));
/* 156 */     cstags.put("Luv ", Integer.valueOf(3));
/* 157 */     cstags.put("YCbr", Integer.valueOf(3));
/* 158 */     cstags.put("Yxy ", Integer.valueOf(3));
/* 159 */     cstags.put("RGB ", Integer.valueOf(3));
/* 160 */     cstags.put("GRAY", Integer.valueOf(1));
/* 161 */     cstags.put("HSV ", Integer.valueOf(3));
/* 162 */     cstags.put("HLS ", Integer.valueOf(3));
/* 163 */     cstags.put("CMYK", Integer.valueOf(4));
/* 164 */     cstags.put("CMY ", Integer.valueOf(3));
/* 165 */     cstags.put("2CLR", Integer.valueOf(2));
/* 166 */     cstags.put("3CLR", Integer.valueOf(3));
/* 167 */     cstags.put("4CLR", Integer.valueOf(4));
/* 168 */     cstags.put("5CLR", Integer.valueOf(5));
/* 169 */     cstags.put("6CLR", Integer.valueOf(6));
/* 170 */     cstags.put("7CLR", Integer.valueOf(7));
/* 171 */     cstags.put("8CLR", Integer.valueOf(8));
/* 172 */     cstags.put("9CLR", Integer.valueOf(9));
/* 173 */     cstags.put("ACLR", Integer.valueOf(10));
/* 174 */     cstags.put("BCLR", Integer.valueOf(11));
/* 175 */     cstags.put("CCLR", Integer.valueOf(12));
/* 176 */     cstags.put("DCLR", Integer.valueOf(13));
/* 177 */     cstags.put("ECLR", Integer.valueOf(14));
/* 178 */     cstags.put("FCLR", Integer.valueOf(15));
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.ICC_Profile
 * JD-Core Version:    0.6.2
 */