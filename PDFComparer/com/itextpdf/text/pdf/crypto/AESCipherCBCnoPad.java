/*    */ package com.itextpdf.text.pdf.crypto;
/*    */ 
/*    */ import org.bouncycastle.crypto.BlockCipher;
/*    */ import org.bouncycastle.crypto.engines.AESFastEngine;
/*    */ import org.bouncycastle.crypto.modes.CBCBlockCipher;
/*    */ import org.bouncycastle.crypto.params.KeyParameter;
/*    */ 
/*    */ public class AESCipherCBCnoPad
/*    */ {
/*    */   private BlockCipher cbc;
/*    */ 
/*    */   public AESCipherCBCnoPad(boolean forEncryption, byte[] key)
/*    */   {
/* 61 */     BlockCipher aes = new AESFastEngine();
/* 62 */     this.cbc = new CBCBlockCipher(aes);
/* 63 */     KeyParameter kp = new KeyParameter(key);
/* 64 */     this.cbc.init(forEncryption, kp);
/*    */   }
/*    */ 
/*    */   public byte[] processBlock(byte[] inp, int inpOff, int inpLen) {
/* 68 */     if (inpLen % this.cbc.getBlockSize() != 0)
/* 69 */       throw new IllegalArgumentException("Not multiple of block: " + inpLen);
/* 70 */     byte[] outp = new byte[inpLen];
/* 71 */     int baseOffset = 0;
/* 72 */     while (inpLen > 0) {
/* 73 */       this.cbc.processBlock(inp, inpOff, outp, baseOffset);
/* 74 */       inpLen -= this.cbc.getBlockSize();
/* 75 */       baseOffset += this.cbc.getBlockSize();
/* 76 */       inpOff += this.cbc.getBlockSize();
/*    */     }
/* 78 */     return outp;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.crypto.AESCipherCBCnoPad
 * JD-Core Version:    0.6.2
 */