/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class ZapfDingbatsList extends List
/*     */ {
/*     */   protected int zn;
/*     */ 
/*     */   public ZapfDingbatsList(int zn)
/*     */   {
/*  68 */     super(true);
/*  69 */     this.zn = zn;
/*  70 */     float fontsize = this.symbol.getFont().getSize();
/*  71 */     this.symbol.setFont(FontFactory.getFont("ZapfDingbats", fontsize, 0));
/*  72 */     this.postSymbol = " ";
/*     */   }
/*     */ 
/*     */   public ZapfDingbatsList(int zn, int symbolIndent)
/*     */   {
/*  82 */     super(true, symbolIndent);
/*  83 */     this.zn = zn;
/*  84 */     float fontsize = this.symbol.getFont().getSize();
/*  85 */     this.symbol.setFont(FontFactory.getFont("ZapfDingbats", fontsize, 0));
/*  86 */     this.postSymbol = " ";
/*     */   }
/*     */ 
/*     */   public ZapfDingbatsList(int zn, int symbolIndent, BaseColor zapfDingbatColor)
/*     */   {
/*  97 */     super(true, symbolIndent);
/*  98 */     this.zn = zn;
/*  99 */     float fontsize = this.symbol.getFont().getSize();
/* 100 */     this.symbol.setFont(FontFactory.getFont("ZapfDingbats", fontsize, 0, zapfDingbatColor));
/* 101 */     this.postSymbol = " ";
/*     */   }
/*     */ 
/*     */   public void setDingbatColor(BaseColor zapfDingbatColor)
/*     */   {
/* 110 */     float fontsize = this.symbol.getFont().getSize();
/* 111 */     this.symbol.setFont(FontFactory.getFont("ZapfDingbats", fontsize, 0, zapfDingbatColor));
/*     */   }
/*     */ 
/*     */   public void setCharNumber(int zn)
/*     */   {
/* 119 */     this.zn = zn;
/*     */   }
/*     */ 
/*     */   public int getCharNumber()
/*     */   {
/* 128 */     return this.zn;
/*     */   }
/*     */ 
/*     */   public boolean add(Element o)
/*     */   {
/* 139 */     if ((o instanceof ListItem)) {
/* 140 */       ListItem item = (ListItem)o;
/* 141 */       Chunk chunk = new Chunk(this.preSymbol, this.symbol.getFont());
/* 142 */       chunk.setAttributes(this.symbol.getAttributes());
/* 143 */       chunk.append(String.valueOf((char)this.zn));
/* 144 */       chunk.append(this.postSymbol);
/* 145 */       item.setListSymbol(chunk);
/* 146 */       item.setIndentationLeft(this.symbolIndent, this.autoindent);
/* 147 */       item.setIndentationRight(0.0F);
/* 148 */       this.list.add(item);
/* 149 */     } else if ((o instanceof List)) {
/* 150 */       List nested = (List)o;
/* 151 */       nested.setIndentationLeft(nested.getIndentationLeft() + this.symbolIndent);
/* 152 */       this.first -= 1;
/* 153 */       return this.list.add(nested);
/*     */     }
/* 155 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.ZapfDingbatsList
 * JD-Core Version:    0.6.2
 */