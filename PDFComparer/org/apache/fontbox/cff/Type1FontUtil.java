/*     */ package org.apache.fontbox.cff;
/*     */ 
/*     */ public class Type1FontUtil
/*     */ {
/*     */   public static String hexEncode(byte[] bytes)
/*     */   {
/*  39 */     StringBuilder sb = new StringBuilder();
/*  40 */     for (int i = 0; i < bytes.length; i++)
/*     */     {
/*  42 */       String string = Integer.toHexString(bytes[i] & 0xFF);
/*  43 */       if (string.length() == 1)
/*     */       {
/*  45 */         sb.append("0");
/*     */       }
/*  47 */       sb.append(string.toUpperCase());
/*     */     }
/*  49 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static byte[] hexDecode(String string)
/*     */   {
/*  59 */     if (string.length() % 2 != 0)
/*     */     {
/*  61 */       throw new IllegalArgumentException();
/*     */     }
/*  63 */     byte[] bytes = new byte[string.length() / 2];
/*  64 */     for (int i = 0; i < string.length(); i += 2)
/*     */     {
/*  66 */       bytes[(i / 2)] = ((byte)Integer.parseInt(string.substring(i, i + 2), 16));
/*     */     }
/*  68 */     return bytes;
/*     */   }
/*     */ 
/*     */   public static byte[] eexecEncrypt(byte[] buffer)
/*     */   {
/*  78 */     return encrypt(buffer, 55665, 4);
/*     */   }
/*     */ 
/*     */   public static byte[] charstringEncrypt(byte[] buffer, int n)
/*     */   {
/*  89 */     return encrypt(buffer, 4330, n);
/*     */   }
/*     */ 
/*     */   private static byte[] encrypt(byte[] plaintextBytes, int r, int n)
/*     */   {
/*  94 */     byte[] buffer = new byte[plaintextBytes.length + n];
/*     */ 
/*  96 */     for (int i = 0; i < n; i++)
/*     */     {
/*  98 */       buffer[i] = 0;
/*     */     }
/*     */ 
/* 101 */     System.arraycopy(plaintextBytes, 0, buffer, n, buffer.length - n);
/*     */ 
/* 103 */     int c1 = 52845;
/* 104 */     int c2 = 22719;
/*     */ 
/* 106 */     byte[] ciphertextBytes = new byte[buffer.length];
/*     */ 
/* 108 */     for (int i = 0; i < buffer.length; i++)
/*     */     {
/* 110 */       int plain = buffer[i] & 0xFF;
/* 111 */       int cipher = plain ^ r >> 8;
/*     */ 
/* 113 */       ciphertextBytes[i] = ((byte)cipher);
/*     */ 
/* 115 */       r = (cipher + r) * c1 + c2 & 0xFFFF;
/*     */     }
/*     */ 
/* 118 */     return ciphertextBytes;
/*     */   }
/*     */ 
/*     */   public static byte[] eexecDecrypt(byte[] buffer)
/*     */   {
/* 128 */     return decrypt(buffer, 55665, 4);
/*     */   }
/*     */ 
/*     */   public static byte[] charstringDecrypt(byte[] buffer, int n)
/*     */   {
/* 139 */     return decrypt(buffer, 4330, n);
/*     */   }
/*     */ 
/*     */   private static byte[] decrypt(byte[] ciphertextBytes, int r, int n)
/*     */   {
/* 144 */     byte[] buffer = new byte[ciphertextBytes.length];
/*     */ 
/* 146 */     int c1 = 52845;
/* 147 */     int c2 = 22719;
/*     */ 
/* 149 */     for (int i = 0; i < ciphertextBytes.length; i++)
/*     */     {
/* 151 */       int cipher = ciphertextBytes[i] & 0xFF;
/* 152 */       int plain = cipher ^ r >> 8;
/*     */ 
/* 154 */       buffer[i] = ((byte)plain);
/*     */ 
/* 156 */       r = (cipher + r) * c1 + c2 & 0xFFFF;
/*     */     }
/*     */ 
/* 159 */     byte[] plaintextBytes = new byte[ciphertextBytes.length - n];
/* 160 */     System.arraycopy(buffer, n, plaintextBytes, 0, plaintextBytes.length);
/*     */ 
/* 162 */     return plaintextBytes;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.cff.Type1FontUtil
 * JD-Core Version:    0.6.2
 */