/*     */ package com.itextpdf.text.pdf.fonts.cmaps;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.pdf.PdfNumber;
/*     */ import com.itextpdf.text.pdf.PdfObject;
/*     */ import com.itextpdf.text.pdf.PdfString;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class CMapByteCid extends AbstractCMap
/*     */ {
/*  54 */   private ArrayList<char[]> planes = new ArrayList();
/*     */ 
/*     */   public CMapByteCid() {
/*  57 */     this.planes.add(new char[256]);
/*     */   }
/*     */ 
/*     */   void addChar(PdfString mark, PdfObject code)
/*     */   {
/*  62 */     if (!(code instanceof PdfNumber))
/*  63 */       return;
/*  64 */     encodeSequence(decodeStringToByte(mark), (char)((PdfNumber)code).intValue());
/*     */   }
/*     */ 
/*     */   private void encodeSequence(byte[] seqs, char cid) {
/*  68 */     int size = seqs.length - 1;
/*  69 */     int nextPlane = 0;
/*  70 */     for (int idx = 0; idx < size; idx++) {
/*  71 */       char[] plane = (char[])this.planes.get(nextPlane);
/*  72 */       int one = seqs[idx] & 0xFF;
/*  73 */       char c = plane[one];
/*  74 */       if ((c != 0) && ((c & 0x8000) == 0))
/*  75 */         throw new RuntimeException(MessageLocalization.getComposedMessage("inconsistent.mapping", new Object[0]));
/*  76 */       if (c == 0) {
/*  77 */         this.planes.add(new char[256]);
/*  78 */         c = (char)(this.planes.size() - 1 | 0x8000);
/*  79 */         plane[one] = c;
/*     */       }
/*  81 */       nextPlane = c & 0x7FFF;
/*     */     }
/*  83 */     char[] plane = (char[])this.planes.get(nextPlane);
/*  84 */     int one = seqs[size] & 0xFF;
/*  85 */     char c = plane[one];
/*  86 */     if ((c & 0x8000) != 0)
/*  87 */       throw new RuntimeException(MessageLocalization.getComposedMessage("inconsistent.mapping", new Object[0]));
/*  88 */     plane[one] = cid;
/*     */   }
/*     */ 
/*     */   public int decodeSingle(CMapSequence seq)
/*     */   {
/*  97 */     int end = seq.off + seq.len;
/*  98 */     int currentPlane = 0;
/*  99 */     while (seq.off < end) {
/* 100 */       int one = seq.seq[(seq.off++)] & 0xFF;
/* 101 */       seq.len -= 1;
/* 102 */       char[] plane = (char[])this.planes.get(currentPlane);
/* 103 */       int cid = plane[one];
/* 104 */       if ((cid & 0x8000) == 0) {
/* 105 */         return cid;
/*     */       }
/*     */ 
/* 108 */       currentPlane = cid & 0x7FFF;
/*     */     }
/* 110 */     return -1;
/*     */   }
/*     */ 
/*     */   public String decodeSequence(CMapSequence seq) {
/* 114 */     StringBuilder sb = new StringBuilder();
/* 115 */     int cid = 0;
/* 116 */     while ((cid = decodeSingle(seq)) >= 0) {
/* 117 */       sb.append((char)cid);
/*     */     }
/* 119 */     return sb.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.fonts.cmaps.CMapByteCid
 * JD-Core Version:    0.6.2
 */