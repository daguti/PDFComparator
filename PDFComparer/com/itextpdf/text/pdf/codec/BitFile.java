/*    */ package com.itextpdf.text.pdf.codec;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ 
/*    */ public class BitFile
/*    */ {
/*    */   OutputStream output_;
/*    */   byte[] buffer_;
/*    */   int index_;
/*    */   int bitsLeft_;
/* 69 */   boolean blocks_ = false;
/*    */ 
/*    */   public BitFile(OutputStream output, boolean blocks)
/*    */   {
/* 77 */     this.output_ = output;
/* 78 */     this.blocks_ = blocks;
/* 79 */     this.buffer_ = new byte[256];
/* 80 */     this.index_ = 0;
/* 81 */     this.bitsLeft_ = 8;
/*    */   }
/*    */ 
/*    */   public void flush() throws IOException
/*    */   {
/* 86 */     int numBytes = this.index_ + (this.bitsLeft_ == 8 ? 0 : 1);
/* 87 */     if (numBytes > 0)
/*    */     {
/* 89 */       if (this.blocks_)
/* 90 */         this.output_.write(numBytes);
/* 91 */       this.output_.write(this.buffer_, 0, numBytes);
/* 92 */       this.buffer_[0] = 0;
/* 93 */       this.index_ = 0;
/* 94 */       this.bitsLeft_ = 8;
/*    */     }
/*    */   }
/*    */ 
/*    */   public void writeBits(int bits, int numbits) throws IOException
/*    */   {
/* 100 */     int bitsWritten = 0;
/* 101 */     int numBytes = 255;
/*    */     do
/*    */     {
/* 105 */       if (((this.index_ == 254) && (this.bitsLeft_ == 0)) || (this.index_ > 254))
/*    */       {
/* 107 */         if (this.blocks_) {
/* 108 */           this.output_.write(numBytes);
/*    */         }
/* 110 */         this.output_.write(this.buffer_, 0, numBytes);
/*    */ 
/* 112 */         this.buffer_[0] = 0;
/* 113 */         this.index_ = 0;
/* 114 */         this.bitsLeft_ = 8;
/*    */       }
/*    */ 
/* 117 */       if (numbits <= this.bitsLeft_)
/*    */       {
/* 119 */         if (this.blocks_)
/*    */         {
/*    */           int tmp105_102 = this.index_;
/*    */           byte[] tmp105_98 = this.buffer_; tmp105_98[tmp105_102] = ((byte)(tmp105_98[tmp105_102] | (bits & (1 << numbits) - 1) << 8 - this.bitsLeft_));
/* 122 */           bitsWritten += numbits;
/* 123 */           this.bitsLeft_ -= numbits;
/* 124 */           numbits = 0;
/*    */         }
/*    */         else
/*    */         {
/*    */           int tmp152_149 = this.index_;
/*    */           byte[] tmp152_145 = this.buffer_; tmp152_145[tmp152_149] = ((byte)(tmp152_145[tmp152_149] | (bits & (1 << numbits) - 1) << this.bitsLeft_ - numbits));
/* 129 */           bitsWritten += numbits;
/* 130 */           this.bitsLeft_ -= numbits;
/* 131 */           numbits = 0;
/*    */         }
/*    */ 
/*    */       }
/* 137 */       else if (this.blocks_)
/*    */       {
/*    */         int tmp205_202 = this.index_;
/*    */         byte[] tmp205_198 = this.buffer_; tmp205_198[tmp205_202] = ((byte)(tmp205_198[tmp205_202] | (bits & (1 << this.bitsLeft_) - 1) << 8 - this.bitsLeft_));
/* 142 */         bitsWritten += this.bitsLeft_;
/* 143 */         bits >>= this.bitsLeft_;
/* 144 */         numbits -= this.bitsLeft_;
/* 145 */         this.buffer_[(++this.index_)] = 0;
/* 146 */         this.bitsLeft_ = 8;
/*    */       }
/*    */       else
/*    */       {
/* 153 */         int topbits = bits >>> numbits - this.bitsLeft_ & (1 << this.bitsLeft_) - 1;
/*    */         int tmp302_299 = this.index_;
/*    */         byte[] tmp302_295 = this.buffer_; tmp302_295[tmp302_299] = ((byte)(tmp302_295[tmp302_299] | topbits));
/* 155 */         numbits -= this.bitsLeft_;
/* 156 */         bitsWritten += this.bitsLeft_;
/* 157 */         this.buffer_[(++this.index_)] = 0;
/* 158 */         this.bitsLeft_ = 8;
/*    */       }
/*    */     }
/*    */ 
/* 162 */     while (numbits != 0);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.codec.BitFile
 * JD-Core Version:    0.6.2
 */