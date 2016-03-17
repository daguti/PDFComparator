/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class ZapfDingbatsNumberList extends List
/*     */ {
/*     */   protected int type;
/*     */ 
/*     */   public ZapfDingbatsNumberList(int type)
/*     */   {
/*  67 */     super(true);
/*  68 */     this.type = type;
/*  69 */     float fontsize = this.symbol.getFont().getSize();
/*  70 */     this.symbol.setFont(FontFactory.getFont("ZapfDingbats", fontsize, 0));
/*  71 */     this.postSymbol = " ";
/*     */   }
/*     */ 
/*     */   public ZapfDingbatsNumberList(int type, int symbolIndent)
/*     */   {
/*  80 */     super(true, symbolIndent);
/*  81 */     this.type = type;
/*  82 */     float fontsize = this.symbol.getFont().getSize();
/*  83 */     this.symbol.setFont(FontFactory.getFont("ZapfDingbats", fontsize, 0));
/*  84 */     this.postSymbol = " ";
/*     */   }
/*     */ 
/*     */   public void setType(int type)
/*     */   {
/*  93 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public int getType()
/*     */   {
/* 102 */     return this.type;
/*     */   }
/*     */ 
/*     */   public boolean add(Element o)
/*     */   {
/* 113 */     if ((o instanceof ListItem)) {
/* 114 */       ListItem item = (ListItem)o;
/* 115 */       Chunk chunk = new Chunk(this.preSymbol, this.symbol.getFont());
/* 116 */       chunk.setAttributes(this.symbol.getAttributes());
/* 117 */       switch (this.type) {
/*     */       case 0:
/* 119 */         chunk.append(String.valueOf((char)(this.first + this.list.size() + 171)));
/* 120 */         break;
/*     */       case 1:
/* 122 */         chunk.append(String.valueOf((char)(this.first + this.list.size() + 181)));
/* 123 */         break;
/*     */       case 2:
/* 125 */         chunk.append(String.valueOf((char)(this.first + this.list.size() + 191)));
/* 126 */         break;
/*     */       default:
/* 128 */         chunk.append(String.valueOf((char)(this.first + this.list.size() + 201)));
/*     */       }
/* 130 */       chunk.append(this.postSymbol);
/* 131 */       item.setListSymbol(chunk);
/* 132 */       item.setIndentationLeft(this.symbolIndent, this.autoindent);
/* 133 */       item.setIndentationRight(0.0F);
/* 134 */       this.list.add(item);
/* 135 */     } else if ((o instanceof List)) {
/* 136 */       List nested = (List)o;
/* 137 */       nested.setIndentationLeft(nested.getIndentationLeft() + this.symbolIndent);
/* 138 */       this.first -= 1;
/* 139 */       return this.list.add(nested);
/*     */     }
/* 141 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.ZapfDingbatsNumberList
 * JD-Core Version:    0.6.2
 */