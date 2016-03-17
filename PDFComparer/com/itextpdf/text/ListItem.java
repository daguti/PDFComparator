/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import com.itextpdf.text.pdf.PdfName;
/*     */ import java.util.List;
/*     */ 
/*     */ public class ListItem extends Paragraph
/*     */ {
/*     */   private static final long serialVersionUID = 1970670787169329006L;
/*     */   protected Chunk symbol;
/* 114 */   private ListBody listBody = null;
/* 115 */   private ListLabel listLabel = null;
/*     */ 
/*     */   public ListItem()
/*     */   {
/* 124 */     setRole(PdfName.LI);
/*     */   }
/*     */ 
/*     */   public ListItem(float leading)
/*     */   {
/* 133 */     super(leading);
/* 134 */     setRole(PdfName.LI);
/*     */   }
/*     */ 
/*     */   public ListItem(Chunk chunk)
/*     */   {
/* 143 */     super(chunk);
/* 144 */     setRole(PdfName.LI);
/*     */   }
/*     */ 
/*     */   public ListItem(String string)
/*     */   {
/* 153 */     super(string);
/* 154 */     setRole(PdfName.LI);
/*     */   }
/*     */ 
/*     */   public ListItem(String string, Font font)
/*     */   {
/* 165 */     super(string, font);
/* 166 */     setRole(PdfName.LI);
/*     */   }
/*     */ 
/*     */   public ListItem(float leading, Chunk chunk)
/*     */   {
/* 177 */     super(leading, chunk);
/* 178 */     setRole(PdfName.LI);
/*     */   }
/*     */ 
/*     */   public ListItem(float leading, String string)
/*     */   {
/* 189 */     super(leading, string);
/* 190 */     setRole(PdfName.LI);
/*     */   }
/*     */ 
/*     */   public ListItem(float leading, String string, Font font)
/*     */   {
/* 202 */     super(leading, string, font);
/* 203 */     setRole(PdfName.LI);
/*     */   }
/*     */ 
/*     */   public ListItem(Phrase phrase)
/*     */   {
/* 212 */     super(phrase);
/* 213 */     setRole(PdfName.LI);
/*     */   }
/*     */ 
/*     */   public int type()
/*     */   {
/* 225 */     return 15;
/*     */   }
/*     */ 
/*     */   public void setListSymbol(Chunk symbol)
/*     */   {
/* 236 */     if (this.symbol == null) {
/* 237 */       this.symbol = symbol;
/* 238 */       if (this.symbol.getFont().isStandardFont())
/* 239 */         this.symbol.setFont(this.font);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setIndentationLeft(float indentation, boolean autoindent)
/*     */   {
/* 251 */     if (autoindent) {
/* 252 */       setIndentationLeft(getListSymbol().getWidthPoint());
/*     */     }
/*     */     else
/* 255 */       setIndentationLeft(indentation);
/*     */   }
/*     */ 
/*     */   public void adjustListSymbolFont()
/*     */   {
/* 265 */     List cks = getChunks();
/* 266 */     if ((!cks.isEmpty()) && (this.symbol != null))
/* 267 */       this.symbol.setFont(((Chunk)cks.get(0)).getFont());
/*     */   }
/*     */ 
/*     */   public Chunk getListSymbol()
/*     */   {
/* 278 */     return this.symbol;
/*     */   }
/*     */ 
/*     */   public ListBody getListBody() {
/* 282 */     if (this.listBody == null)
/* 283 */       this.listBody = new ListBody(this);
/* 284 */     return this.listBody;
/*     */   }
/*     */ 
/*     */   public ListLabel getListLabel() {
/* 288 */     if (this.listLabel == null)
/* 289 */       this.listLabel = new ListLabel(this);
/* 290 */     return this.listLabel;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.ListItem
 * JD-Core Version:    0.6.2
 */