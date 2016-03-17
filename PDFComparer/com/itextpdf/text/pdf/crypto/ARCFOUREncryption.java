/*    */ package com.itextpdf.text.pdf.crypto;
/*    */ 
/*    */ public class ARCFOUREncryption
/*    */ {
/* 48 */   private byte[] state = new byte[256];
/*    */   private int x;
/*    */   private int y;
/*    */ 
/*    */   public void prepareARCFOURKey(byte[] key)
/*    */   {
/* 57 */     prepareARCFOURKey(key, 0, key.length);
/*    */   }
/*    */ 
/*    */   public void prepareARCFOURKey(byte[] key, int off, int len) {
/* 61 */     int index1 = 0;
/* 62 */     int index2 = 0;
/* 63 */     for (int k = 0; k < 256; k++)
/* 64 */       this.state[k] = ((byte)k);
/* 65 */     this.x = 0;
/* 66 */     this.y = 0;
/*    */ 
/* 68 */     for (int k = 0; k < 256; k++) {
/* 69 */       index2 = key[(index1 + off)] + this.state[k] + index2 & 0xFF;
/* 70 */       byte tmp = this.state[k];
/* 71 */       this.state[k] = this.state[index2];
/* 72 */       this.state[index2] = tmp;
/* 73 */       index1 = (index1 + 1) % len;
/*    */     }
/*    */   }
/*    */ 
/*    */   public void encryptARCFOUR(byte[] dataIn, int off, int len, byte[] dataOut, int offOut) {
/* 78 */     int length = len + off;
/*    */ 
/* 80 */     for (int k = off; k < length; k++) {
/* 81 */       this.x = (this.x + 1 & 0xFF);
/* 82 */       this.y = (this.state[this.x] + this.y & 0xFF);
/* 83 */       byte tmp = this.state[this.x];
/* 84 */       this.state[this.x] = this.state[this.y];
/* 85 */       this.state[this.y] = tmp;
/* 86 */       dataOut[(k - off + offOut)] = ((byte)(dataIn[k] ^ this.state[(this.state[this.x] + this.state[this.y] & 0xFF)]));
/*    */     }
/*    */   }
/*    */ 
/*    */   public void encryptARCFOUR(byte[] data, int off, int len) {
/* 91 */     encryptARCFOUR(data, off, len, data, off);
/*    */   }
/*    */ 
/*    */   public void encryptARCFOUR(byte[] dataIn, byte[] dataOut) {
/* 95 */     encryptARCFOUR(dataIn, 0, dataIn.length, dataOut, 0);
/*    */   }
/*    */ 
/*    */   public void encryptARCFOUR(byte[] data) {
/* 99 */     encryptARCFOUR(data, 0, data.length, data, 0);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.crypto.ARCFOUREncryption
 * JD-Core Version:    0.6.2
 */