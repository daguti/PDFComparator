/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import com.itextpdf.text.factories.RomanNumberFactory;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class RomanList extends List
/*     */ {
/*     */   public RomanList()
/*     */   {
/*  64 */     super(true);
/*     */   }
/*     */ 
/*     */   public RomanList(int symbolIndent)
/*     */   {
/*  73 */     super(true, symbolIndent);
/*     */   }
/*     */ 
/*     */   public RomanList(boolean lowercase, int symbolIndent)
/*     */   {
/*  82 */     super(true, symbolIndent);
/*  83 */     this.lowercase = lowercase;
/*     */   }
/*     */ 
/*     */   public boolean add(Element o)
/*     */   {
/*  96 */     if ((o instanceof ListItem)) {
/*  97 */       ListItem item = (ListItem)o;
/*     */ 
/*  99 */       Chunk chunk = new Chunk(this.preSymbol, this.symbol.getFont());
/* 100 */       chunk.setAttributes(this.symbol.getAttributes());
/* 101 */       chunk.append(RomanNumberFactory.getString(this.first + this.list.size(), this.lowercase));
/* 102 */       chunk.append(this.postSymbol);
/* 103 */       item.setListSymbol(chunk);
/* 104 */       item.setIndentationLeft(this.symbolIndent, this.autoindent);
/* 105 */       item.setIndentationRight(0.0F);
/* 106 */       this.list.add(item);
/* 107 */     } else if ((o instanceof List)) {
/* 108 */       List nested = (List)o;
/* 109 */       nested.setIndentationLeft(nested.getIndentationLeft() + this.symbolIndent);
/* 110 */       this.first -= 1;
/* 111 */       return this.list.add(nested);
/*     */     }
/* 113 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.RomanList
 * JD-Core Version:    0.6.2
 */