/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class PdfNameTree
/*     */ {
/*     */   private static final int leafSize = 64;
/*     */ 
/*     */   public static PdfDictionary writeTree(HashMap<String, ? extends PdfObject> items, PdfWriter writer)
/*     */     throws IOException
/*     */   {
/*  72 */     if (items.isEmpty())
/*  73 */       return null;
/*  74 */     String[] names = new String[items.size()];
/*  75 */     names = (String[])items.keySet().toArray(names);
/*  76 */     Arrays.sort(names);
/*  77 */     if (names.length <= 64) {
/*  78 */       PdfDictionary dic = new PdfDictionary();
/*  79 */       PdfArray ar = new PdfArray();
/*  80 */       for (int k = 0; k < names.length; k++) {
/*  81 */         ar.add(new PdfString(names[k], null));
/*  82 */         ar.add((PdfObject)items.get(names[k]));
/*     */       }
/*  84 */       dic.put(PdfName.NAMES, ar);
/*  85 */       return dic;
/*     */     }
/*  87 */     int skip = 64;
/*  88 */     PdfIndirectReference[] kids = new PdfIndirectReference[(names.length + 64 - 1) / 64];
/*  89 */     for (int k = 0; k < kids.length; k++) {
/*  90 */       int offset = k * 64;
/*  91 */       int end = Math.min(offset + 64, names.length);
/*  92 */       PdfDictionary dic = new PdfDictionary();
/*  93 */       PdfArray arr = new PdfArray();
/*  94 */       arr.add(new PdfString(names[offset], null));
/*  95 */       arr.add(new PdfString(names[(end - 1)], null));
/*  96 */       dic.put(PdfName.LIMITS, arr);
/*  97 */       arr = new PdfArray();
/*  98 */       for (; offset < end; offset++) {
/*  99 */         arr.add(new PdfString(names[offset], null));
/* 100 */         arr.add((PdfObject)items.get(names[offset]));
/*     */       }
/* 102 */       dic.put(PdfName.NAMES, arr);
/* 103 */       kids[k] = writer.addToBody(dic).getIndirectReference();
/*     */     }
/* 105 */     int top = kids.length;
/*     */     while (true) {
/* 107 */       if (top <= 64) {
/* 108 */         PdfArray arr = new PdfArray();
/* 109 */         for (int k = 0; k < top; k++)
/* 110 */           arr.add(kids[k]);
/* 111 */         PdfDictionary dic = new PdfDictionary();
/* 112 */         dic.put(PdfName.KIDS, arr);
/* 113 */         return dic;
/*     */       }
/* 115 */       skip *= 64;
/* 116 */       int tt = (names.length + skip - 1) / skip;
/* 117 */       for (int k = 0; k < tt; k++) {
/* 118 */         int offset = k * 64;
/* 119 */         int end = Math.min(offset + 64, top);
/* 120 */         PdfDictionary dic = new PdfDictionary();
/* 121 */         PdfArray arr = new PdfArray();
/* 122 */         arr.add(new PdfString(names[(k * skip)], null));
/* 123 */         arr.add(new PdfString(names[(Math.min((k + 1) * skip, names.length) - 1)], null));
/* 124 */         dic.put(PdfName.LIMITS, arr);
/* 125 */         arr = new PdfArray();
/* 126 */         for (; offset < end; offset++) {
/* 127 */           arr.add(kids[offset]);
/*     */         }
/* 129 */         dic.put(PdfName.KIDS, arr);
/* 130 */         kids[k] = writer.addToBody(dic).getIndirectReference();
/*     */       }
/* 132 */       top = tt;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static PdfString iterateItems(PdfDictionary dic, HashMap<String, PdfObject> items, PdfString leftOverString) {
/* 137 */     PdfArray nn = (PdfArray)PdfReader.getPdfObjectRelease(dic.get(PdfName.NAMES));
/* 138 */     if (nn != null)
/* 139 */       for (int k = 0; k < nn.size(); k++)
/*     */       {
/*     */         PdfString s;
/*     */         PdfString s;
/* 141 */         if (leftOverString == null) {
/* 142 */           s = (PdfString)PdfReader.getPdfObjectRelease(nn.getPdfObject(k++));
/*     */         }
/*     */         else {
/* 145 */           s = leftOverString;
/* 146 */           leftOverString = null;
/*     */         }
/* 148 */         if (k < nn.size())
/* 149 */           items.put(PdfEncodings.convertToString(s.getBytes(), null), nn.getPdfObject(k));
/*     */         else
/* 151 */           return s;
/*     */       }
/* 153 */     else if ((nn = (PdfArray)PdfReader.getPdfObjectRelease(dic.get(PdfName.KIDS))) != null) {
/* 154 */       for (int k = 0; k < nn.size(); k++) {
/* 155 */         PdfDictionary kid = (PdfDictionary)PdfReader.getPdfObjectRelease(nn.getPdfObject(k));
/* 156 */         leftOverString = iterateItems(kid, items, leftOverString);
/*     */       }
/*     */     }
/* 159 */     return null;
/*     */   }
/*     */ 
/*     */   public static HashMap<String, PdfObject> readTree(PdfDictionary dic) {
/* 163 */     HashMap items = new HashMap();
/* 164 */     if (dic != null)
/* 165 */       iterateItems(dic, items, null);
/* 166 */     return items;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfNameTree
 * JD-Core Version:    0.6.2
 */