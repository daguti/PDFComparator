/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import com.itextpdf.text.factories.GreekAlphabetFactory;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class GreekList extends List
/*     */ {
/*     */   public GreekList()
/*     */   {
/*  64 */     super(true);
/*  65 */     setGreekFont();
/*     */   }
/*     */ 
/*     */   public GreekList(int symbolIndent)
/*     */   {
/*  73 */     super(true, symbolIndent);
/*  74 */     setGreekFont();
/*     */   }
/*     */ 
/*     */   public GreekList(boolean greeklower, int symbolIndent)
/*     */   {
/*  83 */     super(true, symbolIndent);
/*  84 */     this.lowercase = greeklower;
/*  85 */     setGreekFont();
/*     */   }
/*     */ 
/*     */   protected void setGreekFont()
/*     */   {
/*  94 */     float fontsize = this.symbol.getFont().getSize();
/*  95 */     this.symbol.setFont(FontFactory.getFont("Symbol", fontsize, 0));
/*     */   }
/*     */ 
/*     */   public boolean add(Element o)
/*     */   {
/* 108 */     if ((o instanceof ListItem)) {
/* 109 */       ListItem item = (ListItem)o;
/* 110 */       Chunk chunk = new Chunk(this.preSymbol, this.symbol.getFont());
/* 111 */       chunk.setAttributes(this.symbol.getAttributes());
/* 112 */       chunk.append(GreekAlphabetFactory.getString(this.first + this.list.size(), this.lowercase));
/* 113 */       chunk.append(this.postSymbol);
/* 114 */       item.setListSymbol(chunk);
/* 115 */       item.setIndentationLeft(this.symbolIndent, this.autoindent);
/* 116 */       item.setIndentationRight(0.0F);
/* 117 */       this.list.add(item);
/* 118 */     } else if ((o instanceof List)) {
/* 119 */       List nested = (List)o;
/* 120 */       nested.setIndentationLeft(nested.getIndentationLeft() + this.symbolIndent);
/* 121 */       this.first -= 1;
/* 122 */       return this.list.add(nested);
/*     */     }
/* 124 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.GreekList
 * JD-Core Version:    0.6.2
 */