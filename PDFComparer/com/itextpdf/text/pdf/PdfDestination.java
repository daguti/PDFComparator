/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public class PdfDestination extends PdfArray
/*     */ {
/*     */   public static final int XYZ = 0;
/*     */   public static final int FIT = 1;
/*     */   public static final int FITH = 2;
/*     */   public static final int FITV = 3;
/*     */   public static final int FITR = 4;
/*     */   public static final int FITB = 5;
/*     */   public static final int FITBH = 6;
/*     */   public static final int FITBV = 7;
/*  84 */   private boolean status = false;
/*     */ 
/*     */   public PdfDestination(int type)
/*     */   {
/* 100 */     if (type == 5) {
/* 101 */       add(PdfName.FITB);
/*     */     }
/*     */     else
/* 104 */       add(PdfName.FIT);
/*     */   }
/*     */ 
/*     */   public PdfDestination(int type, float parameter)
/*     */   {
/* 124 */     super(new PdfNumber(parameter));
/* 125 */     switch (type) { case 4:
/*     */     case 5:
/*     */     default:
/* 127 */       addFirst(PdfName.FITH);
/* 128 */       break;
/*     */     case 3:
/* 130 */       addFirst(PdfName.FITV);
/* 131 */       break;
/*     */     case 6:
/* 133 */       addFirst(PdfName.FITBH);
/* 134 */       break;
/*     */     case 7:
/* 136 */       addFirst(PdfName.FITBV);
/*     */     }
/*     */   }
/*     */ 
/*     */   public PdfDestination(int type, float left, float top, float zoom)
/*     */   {
/* 153 */     super(PdfName.XYZ);
/* 154 */     if (left < 0.0F)
/* 155 */       add(PdfNull.PDFNULL);
/*     */     else
/* 157 */       add(new PdfNumber(left));
/* 158 */     if (top < 0.0F)
/* 159 */       add(PdfNull.PDFNULL);
/*     */     else
/* 161 */       add(new PdfNumber(top));
/* 162 */     add(new PdfNumber(zoom));
/*     */   }
/*     */ 
/*     */   public PdfDestination(int type, float left, float bottom, float right, float top)
/*     */   {
/* 182 */     super(PdfName.FITR);
/* 183 */     add(new PdfNumber(left));
/* 184 */     add(new PdfNumber(bottom));
/* 185 */     add(new PdfNumber(right));
/* 186 */     add(new PdfNumber(top));
/*     */   }
/*     */ 
/*     */   public PdfDestination(String dest)
/*     */   {
/* 198 */     StringTokenizer tokens = new StringTokenizer(dest);
/* 199 */     if (tokens.hasMoreTokens()) {
/* 200 */       add(new PdfName(tokens.nextToken()));
/*     */     }
/* 202 */     while (tokens.hasMoreTokens()) {
/* 203 */       String token = tokens.nextToken();
/* 204 */       if ("null".equals(token))
/* 205 */         add(new PdfNull());
/*     */       else
/*     */         try {
/* 208 */           add(new PdfNumber(token));
/*     */         } catch (RuntimeException e) {
/* 210 */           add(new PdfNull());
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean hasPage()
/*     */   {
/* 225 */     return this.status;
/*     */   }
/*     */ 
/*     */   public boolean addPage(PdfIndirectReference page)
/*     */   {
/* 235 */     if (!this.status) {
/* 236 */       addFirst(page);
/* 237 */       this.status = true;
/* 238 */       return true;
/*     */     }
/* 240 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfDestination
 * JD-Core Version:    0.6.2
 */