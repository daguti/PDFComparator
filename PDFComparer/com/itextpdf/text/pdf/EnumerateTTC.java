/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ class EnumerateTTC extends TrueTypeFont
/*     */ {
/*     */   protected String[] names;
/*     */ 
/*     */   EnumerateTTC(String ttcFile)
/*     */     throws DocumentException, IOException
/*     */   {
/*  61 */     this.fileName = ttcFile;
/*  62 */     this.rf = new RandomAccessFileOrArray(ttcFile);
/*  63 */     findNames();
/*     */   }
/*     */ 
/*     */   EnumerateTTC(byte[] ttcArray) throws DocumentException, IOException {
/*  67 */     this.fileName = "Byte array TTC";
/*  68 */     this.rf = new RandomAccessFileOrArray(ttcArray);
/*  69 */     findNames();
/*     */   }
/*     */ 
/*     */   void findNames() throws DocumentException, IOException {
/*  73 */     this.tables = new HashMap();
/*     */     try
/*     */     {
/*  76 */       String mainTag = readStandardString(4);
/*  77 */       if (!mainTag.equals("ttcf"))
/*  78 */         throw new DocumentException(MessageLocalization.getComposedMessage("1.is.not.a.valid.ttc.file", new Object[] { this.fileName }));
/*  79 */       this.rf.skipBytes(4);
/*  80 */       int dirCount = this.rf.readInt();
/*  81 */       this.names = new String[dirCount];
/*  82 */       int dirPos = (int)this.rf.getFilePointer();
/*  83 */       for (int dirIdx = 0; dirIdx < dirCount; dirIdx++) {
/*  84 */         this.tables.clear();
/*  85 */         this.rf.seek(dirPos);
/*  86 */         this.rf.skipBytes(dirIdx * 4);
/*  87 */         this.directoryOffset = this.rf.readInt();
/*  88 */         this.rf.seek(this.directoryOffset);
/*  89 */         if (this.rf.readInt() != 65536)
/*  90 */           throw new DocumentException(MessageLocalization.getComposedMessage("1.is.not.a.valid.ttf.file", new Object[] { this.fileName }));
/*  91 */         int num_tables = this.rf.readUnsignedShort();
/*  92 */         this.rf.skipBytes(6);
/*  93 */         for (int k = 0; k < num_tables; k++) {
/*  94 */           String tag = readStandardString(4);
/*  95 */           this.rf.skipBytes(4);
/*  96 */           int[] table_location = new int[2];
/*  97 */           table_location[0] = this.rf.readInt();
/*  98 */           table_location[1] = this.rf.readInt();
/*  99 */           this.tables.put(tag, table_location);
/*     */         }
/* 101 */         this.names[dirIdx] = getBaseFont();
/*     */       }
/*     */     }
/*     */     finally {
/* 105 */       if (this.rf != null)
/* 106 */         this.rf.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   String[] getNames() {
/* 111 */     return this.names;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.EnumerateTTC
 * JD-Core Version:    0.6.2
 */