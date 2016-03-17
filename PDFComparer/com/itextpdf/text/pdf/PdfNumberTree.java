/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class PdfNumberTree
/*     */ {
/*     */   private static final int leafSize = 64;
/*     */ 
/*     */   public static <O extends PdfObject> PdfDictionary writeTree(HashMap<Integer, O> items, PdfWriter writer)
/*     */     throws IOException
/*     */   {
/*  68 */     if (items.isEmpty())
/*  69 */       return null;
/*  70 */     Integer[] numbers = new Integer[items.size()];
/*  71 */     numbers = (Integer[])items.keySet().toArray(numbers);
/*  72 */     Arrays.sort(numbers);
/*  73 */     if (numbers.length <= 64) {
/*  74 */       PdfDictionary dic = new PdfDictionary();
/*  75 */       PdfArray ar = new PdfArray();
/*  76 */       for (int k = 0; k < numbers.length; k++) {
/*  77 */         ar.add(new PdfNumber(numbers[k].intValue()));
/*  78 */         ar.add((PdfObject)items.get(numbers[k]));
/*     */       }
/*  80 */       dic.put(PdfName.NUMS, ar);
/*  81 */       return dic;
/*     */     }
/*  83 */     int skip = 64;
/*  84 */     PdfIndirectReference[] kids = new PdfIndirectReference[(numbers.length + 64 - 1) / 64];
/*  85 */     for (int k = 0; k < kids.length; k++) {
/*  86 */       int offset = k * 64;
/*  87 */       int end = Math.min(offset + 64, numbers.length);
/*  88 */       PdfDictionary dic = new PdfDictionary();
/*  89 */       PdfArray arr = new PdfArray();
/*  90 */       arr.add(new PdfNumber(numbers[offset].intValue()));
/*  91 */       arr.add(new PdfNumber(numbers[(end - 1)].intValue()));
/*  92 */       dic.put(PdfName.LIMITS, arr);
/*  93 */       arr = new PdfArray();
/*  94 */       for (; offset < end; offset++) {
/*  95 */         arr.add(new PdfNumber(numbers[offset].intValue()));
/*  96 */         arr.add((PdfObject)items.get(numbers[offset]));
/*     */       }
/*  98 */       dic.put(PdfName.NUMS, arr);
/*  99 */       kids[k] = writer.addToBody(dic).getIndirectReference();
/*     */     }
/* 101 */     int top = kids.length;
/*     */     while (true) {
/* 103 */       if (top <= 64) {
/* 104 */         PdfArray arr = new PdfArray();
/* 105 */         for (int k = 0; k < top; k++)
/* 106 */           arr.add(kids[k]);
/* 107 */         PdfDictionary dic = new PdfDictionary();
/* 108 */         dic.put(PdfName.KIDS, arr);
/* 109 */         return dic;
/*     */       }
/* 111 */       skip *= 64;
/* 112 */       int tt = (numbers.length + skip - 1) / skip;
/* 113 */       for (int k = 0; k < tt; k++) {
/* 114 */         int offset = k * 64;
/* 115 */         int end = Math.min(offset + 64, top);
/* 116 */         PdfDictionary dic = new PdfDictionary();
/* 117 */         PdfArray arr = new PdfArray();
/* 118 */         arr.add(new PdfNumber(numbers[(k * skip)].intValue()));
/* 119 */         arr.add(new PdfNumber(numbers[(Math.min((k + 1) * skip, numbers.length) - 1)].intValue()));
/* 120 */         dic.put(PdfName.LIMITS, arr);
/* 121 */         arr = new PdfArray();
/* 122 */         for (; offset < end; offset++) {
/* 123 */           arr.add(kids[offset]);
/*     */         }
/* 125 */         dic.put(PdfName.KIDS, arr);
/* 126 */         kids[k] = writer.addToBody(dic).getIndirectReference();
/*     */       }
/* 128 */       top = tt;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void iterateItems(PdfDictionary dic, HashMap<Integer, PdfObject> items) {
/* 133 */     PdfArray nn = (PdfArray)PdfReader.getPdfObjectRelease(dic.get(PdfName.NUMS));
/* 134 */     if (nn != null) {
/* 135 */       for (int k = 0; k < nn.size(); k++) {
/* 136 */         PdfNumber s = (PdfNumber)PdfReader.getPdfObjectRelease(nn.getPdfObject(k++));
/* 137 */         items.put(Integer.valueOf(s.intValue()), nn.getPdfObject(k));
/*     */       }
/*     */     }
/* 140 */     else if ((nn = (PdfArray)PdfReader.getPdfObjectRelease(dic.get(PdfName.KIDS))) != null)
/* 141 */       for (int k = 0; k < nn.size(); k++) {
/* 142 */         PdfDictionary kid = (PdfDictionary)PdfReader.getPdfObjectRelease(nn.getPdfObject(k));
/* 143 */         iterateItems(kid, items);
/*     */       }
/*     */   }
/*     */ 
/*     */   public static HashMap<Integer, PdfObject> readTree(PdfDictionary dic)
/*     */   {
/* 149 */     HashMap items = new HashMap();
/* 150 */     if (dic != null)
/* 151 */       iterateItems(dic, items);
/* 152 */     return items;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfNumberTree
 * JD-Core Version:    0.6.2
 */