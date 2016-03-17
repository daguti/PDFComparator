/*    */ package com.itextpdf.text.pdf.fonts.cmaps;
/*    */ 
/*    */ import com.itextpdf.text.pdf.PdfNumber;
/*    */ import com.itextpdf.text.pdf.PdfObject;
/*    */ import com.itextpdf.text.pdf.PdfString;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class CMapCidByte extends AbstractCMap
/*    */ {
/* 53 */   private HashMap<Integer, byte[]> map = new HashMap();
/* 54 */   private final byte[] EMPTY = new byte[0];
/*    */ 
/*    */   void addChar(PdfString mark, PdfObject code)
/*    */   {
/* 58 */     if (!(code instanceof PdfNumber))
/* 59 */       return;
/* 60 */     byte[] ser = decodeStringToByte(mark);
/* 61 */     this.map.put(Integer.valueOf(((PdfNumber)code).intValue()), ser);
/*    */   }
/*    */ 
/*    */   public byte[] lookup(int cid) {
/* 65 */     byte[] ser = (byte[])this.map.get(Integer.valueOf(cid));
/* 66 */     if (ser == null) {
/* 67 */       return this.EMPTY;
/*    */     }
/* 69 */     return ser;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.fonts.cmaps.CMapCidByte
 * JD-Core Version:    0.6.2
 */