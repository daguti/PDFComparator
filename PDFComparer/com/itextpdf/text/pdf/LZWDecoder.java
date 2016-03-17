/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ public class LZWDecoder
/*     */ {
/*     */   byte[][] stringTable;
/*  60 */   byte[] data = null;
/*     */   OutputStream uncompData;
/*     */   int tableIndex;
/*  62 */   int bitsToGet = 9;
/*     */   int bytePointer;
/*     */   int bitPointer;
/*  64 */   int nextData = 0;
/*  65 */   int nextBits = 0;
/*     */ 
/*  67 */   int[] andTable = { 511, 1023, 2047, 4095 };
/*     */ 
/*     */   public void decode(byte[] data, OutputStream uncompData)
/*     */   {
/*  85 */     if ((data[0] == 0) && (data[1] == 1)) {
/*  86 */       throw new RuntimeException(MessageLocalization.getComposedMessage("lzw.flavour.not.supported", new Object[0]));
/*     */     }
/*     */ 
/*  89 */     initializeStringTable();
/*     */ 
/*  91 */     this.data = data;
/*  92 */     this.uncompData = uncompData;
/*     */ 
/*  95 */     this.bytePointer = 0;
/*  96 */     this.bitPointer = 0;
/*     */ 
/*  98 */     this.nextData = 0;
/*  99 */     this.nextBits = 0;
/*     */ 
/* 101 */     int oldCode = 0;
/*     */     int code;
/* 104 */     while ((code = getNextCode()) != 257)
/*     */     {
/* 106 */       if (code == 256)
/*     */       {
/* 108 */         initializeStringTable();
/* 109 */         code = getNextCode();
/*     */ 
/* 111 */         if (code == 257)
/*     */         {
/*     */           break;
/*     */         }
/* 115 */         writeString(this.stringTable[code]);
/* 116 */         oldCode = code;
/*     */       }
/* 120 */       else if (code < this.tableIndex)
/*     */       {
/* 122 */         byte[] string = this.stringTable[code];
/*     */ 
/* 124 */         writeString(string);
/* 125 */         addStringToTable(this.stringTable[oldCode], string[0]);
/* 126 */         oldCode = code;
/*     */       }
/*     */       else
/*     */       {
/* 130 */         byte[] string = this.stringTable[oldCode];
/* 131 */         string = composeString(string, string[0]);
/* 132 */         writeString(string);
/* 133 */         addStringToTable(string);
/* 134 */         oldCode = code;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void initializeStringTable()
/*     */   {
/* 146 */     this.stringTable = new byte[8192][];
/*     */ 
/* 148 */     for (int i = 0; i < 256; i++) {
/* 149 */       this.stringTable[i] = new byte[1];
/* 150 */       this.stringTable[i][0] = ((byte)i);
/*     */     }
/*     */ 
/* 153 */     this.tableIndex = 258;
/* 154 */     this.bitsToGet = 9;
/*     */   }
/*     */ 
/*     */   public void writeString(byte[] string)
/*     */   {
/*     */     try
/*     */     {
/* 162 */       this.uncompData.write(string);
/*     */     }
/*     */     catch (IOException e) {
/* 165 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addStringToTable(byte[] oldString, byte newString)
/*     */   {
/* 173 */     int length = oldString.length;
/* 174 */     byte[] string = new byte[length + 1];
/* 175 */     System.arraycopy(oldString, 0, string, 0, length);
/* 176 */     string[length] = newString;
/*     */ 
/* 179 */     this.stringTable[(this.tableIndex++)] = string;
/*     */ 
/* 181 */     if (this.tableIndex == 511)
/* 182 */       this.bitsToGet = 10;
/* 183 */     else if (this.tableIndex == 1023)
/* 184 */       this.bitsToGet = 11;
/* 185 */     else if (this.tableIndex == 2047)
/* 186 */       this.bitsToGet = 12;
/*     */   }
/*     */ 
/*     */   public void addStringToTable(byte[] string)
/*     */   {
/* 196 */     this.stringTable[(this.tableIndex++)] = string;
/*     */ 
/* 198 */     if (this.tableIndex == 511)
/* 199 */       this.bitsToGet = 10;
/* 200 */     else if (this.tableIndex == 1023)
/* 201 */       this.bitsToGet = 11;
/* 202 */     else if (this.tableIndex == 2047)
/* 203 */       this.bitsToGet = 12;
/*     */   }
/*     */ 
/*     */   public byte[] composeString(byte[] oldString, byte newString)
/*     */   {
/* 211 */     int length = oldString.length;
/* 212 */     byte[] string = new byte[length + 1];
/* 213 */     System.arraycopy(oldString, 0, string, 0, length);
/* 214 */     string[length] = newString;
/*     */ 
/* 216 */     return string;
/*     */   }
/*     */ 
/*     */   public int getNextCode()
/*     */   {
/*     */     try
/*     */     {
/* 226 */       this.nextData = (this.nextData << 8 | this.data[(this.bytePointer++)] & 0xFF);
/* 227 */       this.nextBits += 8;
/*     */ 
/* 229 */       if (this.nextBits < this.bitsToGet) {
/* 230 */         this.nextData = (this.nextData << 8 | this.data[(this.bytePointer++)] & 0xFF);
/* 231 */         this.nextBits += 8;
/*     */       }
/*     */ 
/* 234 */       int code = this.nextData >> this.nextBits - this.bitsToGet & this.andTable[(this.bitsToGet - 9)];
/*     */ 
/* 236 */       this.nextBits -= this.bitsToGet;
/*     */ 
/* 238 */       return code;
/*     */     } catch (ArrayIndexOutOfBoundsException e) {
/*     */     }
/* 241 */     return 257;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.LZWDecoder
 * JD-Core Version:    0.6.2
 */