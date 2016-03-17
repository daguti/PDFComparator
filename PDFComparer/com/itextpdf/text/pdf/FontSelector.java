/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.Chunk;
/*     */ import com.itextpdf.text.Font;
/*     */ import com.itextpdf.text.Phrase;
/*     */ import com.itextpdf.text.Utilities;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class FontSelector
/*     */ {
/*  65 */   protected ArrayList<Font> fonts = new ArrayList();
/*  66 */   protected Font currentFont = null;
/*     */ 
/*     */   public void addFont(Font font)
/*     */   {
/*  73 */     if (font.getBaseFont() != null) {
/*  74 */       this.fonts.add(font);
/*  75 */       return;
/*     */     }
/*  77 */     BaseFont bf = font.getCalculatedBaseFont(true);
/*  78 */     Font f2 = new Font(bf, font.getSize(), font.getCalculatedStyle(), font.getColor());
/*  79 */     this.fonts.add(f2);
/*     */   }
/*     */ 
/*     */   public Phrase process(String text)
/*     */   {
/*  89 */     if (this.fonts.size() == 0)
/*  90 */       throw new IndexOutOfBoundsException(MessageLocalization.getComposedMessage("no.font.is.defined", new Object[0]));
/*  91 */     char[] cc = text.toCharArray();
/*  92 */     int len = cc.length;
/*  93 */     StringBuffer sb = new StringBuffer();
/*  94 */     Phrase ret = new Phrase();
/*  95 */     this.currentFont = null;
/*  96 */     for (int k = 0; k < len; k++) {
/*  97 */       Chunk newChunk = processChar(cc, k, sb);
/*  98 */       if (newChunk != null) {
/*  99 */         ret.add(newChunk);
/*     */       }
/*     */     }
/* 102 */     if (sb.length() > 0) {
/* 103 */       Chunk ck = new Chunk(sb.toString(), this.currentFont != null ? this.currentFont : (Font)this.fonts.get(0));
/* 104 */       ret.add(ck);
/*     */     }
/* 106 */     return ret;
/*     */   }
/*     */ 
/*     */   protected Chunk processChar(char[] cc, int k, StringBuffer sb) {
/* 110 */     Chunk newChunk = null;
/* 111 */     char c = cc[k];
/* 112 */     if ((c == '\n') || (c == '\r')) {
/* 113 */       sb.append(c);
/*     */     } else {
/* 115 */       Font font = null;
/* 116 */       if (Utilities.isSurrogatePair(cc, k)) {
/* 117 */         int u = Utilities.convertToUtf32(cc, k);
/* 118 */         for (int f = 0; f < this.fonts.size(); f++) {
/* 119 */           font = (Font)this.fonts.get(f);
/* 120 */           if ((font.getBaseFont().charExists(u)) || (Character.getType(u) == 16)) {
/* 121 */             if (this.currentFont != font) {
/* 122 */               if ((sb.length() > 0) && (this.currentFont != null)) {
/* 123 */                 newChunk = new Chunk(sb.toString(), this.currentFont);
/* 124 */                 sb.setLength(0);
/*     */               }
/* 126 */               this.currentFont = font;
/*     */             }
/* 128 */             sb.append(c);
/* 129 */             sb.append(cc[(++k)]);
/* 130 */             break;
/*     */           }
/*     */         }
/*     */       } else {
/* 134 */         for (int f = 0; f < this.fonts.size(); f++) {
/* 135 */           font = (Font)this.fonts.get(f);
/* 136 */           if ((font.getBaseFont().charExists(c)) || (Character.getType(c) == 16)) {
/* 137 */             if (this.currentFont != font) {
/* 138 */               if ((sb.length() > 0) && (this.currentFont != null)) {
/* 139 */                 newChunk = new Chunk(sb.toString(), this.currentFont);
/* 140 */                 sb.setLength(0);
/*     */               }
/* 142 */               this.currentFont = font;
/*     */             }
/* 144 */             sb.append(c);
/* 145 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 150 */     return newChunk;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.FontSelector
 * JD-Core Version:    0.6.2
 */