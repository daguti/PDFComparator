/*     */ package com.itextpdf.text.pdf.fonts.cmaps;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.pdf.PRTokeniser;
/*     */ import com.itextpdf.text.pdf.PdfContentParser;
/*     */ import com.itextpdf.text.pdf.PdfName;
/*     */ import com.itextpdf.text.pdf.PdfNumber;
/*     */ import com.itextpdf.text.pdf.PdfObject;
/*     */ import com.itextpdf.text.pdf.PdfString;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class CMapParserEx
/*     */ {
/*  67 */   private static final PdfName CMAPNAME = new PdfName("CMapName");
/*     */   private static final String DEF = "def";
/*     */   private static final String ENDCIDRANGE = "endcidrange";
/*     */   private static final String ENDCIDCHAR = "endcidchar";
/*     */   private static final String ENDBFRANGE = "endbfrange";
/*     */   private static final String ENDBFCHAR = "endbfchar";
/*     */   private static final String USECMAP = "usecmap";
/*     */   private static final int MAXLEVEL = 10;
/*     */ 
/*     */   public static void parseCid(String cmapName, AbstractCMap cmap, CidLocation location)
/*     */     throws IOException
/*     */   {
/*  77 */     parseCid(cmapName, cmap, location, 0);
/*     */   }
/*     */ 
/*     */   private static void parseCid(String cmapName, AbstractCMap cmap, CidLocation location, int level) throws IOException {
/*  81 */     if (level >= 10)
/*  82 */       return;
/*  83 */     PRTokeniser inp = location.getLocation(cmapName);
/*     */     try {
/*  85 */       ArrayList list = new ArrayList();
/*  86 */       PdfContentParser cp = new PdfContentParser(inp);
/*  87 */       int maxExc = 50;
/*     */       while (true) {
/*     */         try {
/*  90 */           cp.parse(list);
/*     */         }
/*     */         catch (Exception ex) {
/*  93 */           maxExc--; if (maxExc >= 0) break label64; 
/*  94 */         }break;
/*  95 */         label64: continue;
/*     */ 
/*  97 */         if (list.isEmpty())
/*     */           break;
/*  99 */         String last = ((PdfObject)list.get(list.size() - 1)).toString();
/* 100 */         if ((level == 0) && (list.size() == 3) && (last.equals("def"))) {
/* 101 */           PdfObject key = (PdfObject)list.get(0);
/* 102 */           if (PdfName.REGISTRY.equals(key))
/* 103 */             cmap.setRegistry(((PdfObject)list.get(1)).toString());
/* 104 */           else if (PdfName.ORDERING.equals(key))
/* 105 */             cmap.setOrdering(((PdfObject)list.get(1)).toString());
/* 106 */           else if (CMAPNAME.equals(key))
/* 107 */             cmap.setName(((PdfObject)list.get(1)).toString());
/* 108 */           else if (PdfName.SUPPLEMENT.equals(key))
/*     */             try {
/* 110 */               cmap.setSupplement(((PdfNumber)list.get(1)).intValue());
/*     */             }
/*     */             catch (Exception ex) {
/*     */             }
/*     */         }
/* 115 */         else if (((last.equals("endcidchar")) || (last.equals("endbfchar"))) && (list.size() >= 3)) {
/* 116 */           int lmax = list.size() - 2;
/* 117 */           for (int k = 0; k < lmax; k += 2) {
/* 118 */             if ((list.get(k) instanceof PdfString)) {
/* 119 */               cmap.addChar((PdfString)list.get(k), (PdfObject)list.get(k + 1));
/*     */             }
/*     */           }
/*     */         }
/* 123 */         else if (((last.equals("endcidrange")) || (last.equals("endbfrange"))) && (list.size() >= 4)) {
/* 124 */           int lmax = list.size() - 3;
/* 125 */           for (int k = 0; k < lmax; k += 3) {
/* 126 */             if (((list.get(k) instanceof PdfString)) && ((list.get(k + 1) instanceof PdfString))) {
/* 127 */               cmap.addRange((PdfString)list.get(k), (PdfString)list.get(k + 1), (PdfObject)list.get(k + 2));
/*     */             }
/*     */           }
/*     */         }
/* 131 */         else if ((last.equals("usecmap")) && (list.size() == 2) && ((list.get(0) instanceof PdfName))) {
/* 132 */           parseCid(PdfName.decodeName(((PdfObject)list.get(0)).toString()), cmap, location, level + 1);
/*     */         }
/*     */       }
/*     */     }
/*     */     finally {
/* 137 */       inp.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void encodeSequence(int size, byte[] seqs, char cid, ArrayList<char[]> planes) {
/* 142 */     size--;
/* 143 */     int nextPlane = 0;
/* 144 */     for (int idx = 0; idx < size; idx++) {
/* 145 */       char[] plane = (char[])planes.get(nextPlane);
/* 146 */       int one = seqs[idx] & 0xFF;
/* 147 */       char c = plane[one];
/* 148 */       if ((c != 0) && ((c & 0x8000) == 0))
/* 149 */         throw new RuntimeException(MessageLocalization.getComposedMessage("inconsistent.mapping", new Object[0]));
/* 150 */       if (c == 0) {
/* 151 */         planes.add(new char[256]);
/* 152 */         c = (char)(planes.size() - 1 | 0x8000);
/* 153 */         plane[one] = c;
/*     */       }
/* 155 */       nextPlane = c & 0x7FFF;
/*     */     }
/* 157 */     char[] plane = (char[])planes.get(nextPlane);
/* 158 */     int one = seqs[size] & 0xFF;
/* 159 */     char c = plane[one];
/* 160 */     if ((c & 0x8000) != 0)
/* 161 */       throw new RuntimeException(MessageLocalization.getComposedMessage("inconsistent.mapping", new Object[0]));
/* 162 */     plane[one] = cid;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.fonts.cmaps.CMapParserEx
 * JD-Core Version:    0.6.2
 */