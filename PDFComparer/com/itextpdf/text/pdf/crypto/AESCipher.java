/*    */ package com.itextpdf.text.pdf.crypto;
/*    */ 
/*    */ import org.bouncycastle.crypto.BlockCipher;
/*    */ import org.bouncycastle.crypto.engines.AESFastEngine;
/*    */ import org.bouncycastle.crypto.modes.CBCBlockCipher;
/*    */ import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
/*    */ import org.bouncycastle.crypto.params.KeyParameter;
/*    */ import org.bouncycastle.crypto.params.ParametersWithIV;
/*    */ 
/*    */ public class AESCipher
/*    */ {
/*    */   private PaddedBufferedBlockCipher bp;
/*    */ 
/*    */   public AESCipher(boolean forEncryption, byte[] key, byte[] iv)
/*    */   {
/* 63 */     BlockCipher aes = new AESFastEngine();
/* 64 */     BlockCipher cbc = new CBCBlockCipher(aes);
/* 65 */     this.bp = new PaddedBufferedBlockCipher(cbc);
/* 66 */     KeyParameter kp = new KeyParameter(key);
/* 67 */     ParametersWithIV piv = new ParametersWithIV(kp, iv);
/* 68 */     this.bp.init(forEncryption, piv);
/*    */   }
/*    */ 
/*    */   public byte[] update(byte[] inp, int inpOff, int inpLen) {
/* 72 */     int neededLen = this.bp.getUpdateOutputSize(inpLen);
/* 73 */     byte[] outp = null;
/* 74 */     if (neededLen > 0)
/* 75 */       outp = new byte[neededLen];
/*    */     else
/* 77 */       neededLen = 0;
/* 78 */     this.bp.processBytes(inp, inpOff, inpLen, outp, 0);
/* 79 */     return outp;
/*    */   }
/*    */ 
/*    */   public byte[] doFinal() {
/* 83 */     int neededLen = this.bp.getOutputSize(0);
/* 84 */     byte[] outp = new byte[neededLen];
/* 85 */     int n = 0;
/*    */     try {
/* 87 */       n = this.bp.doFinal(outp, 0);
/*    */     } catch (Exception ex) {
/* 89 */       return outp;
/*    */     }
/* 91 */     if (n != outp.length) {
/* 92 */       byte[] outp2 = new byte[n];
/* 93 */       System.arraycopy(outp, 0, outp2, 0, n);
/* 94 */       return outp2;
/*    */     }
/*    */ 
/* 97 */     return outp;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.crypto.AESCipher
 * JD-Core Version:    0.6.2
 */