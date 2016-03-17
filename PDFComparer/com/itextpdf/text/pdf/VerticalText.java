/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.BaseColor;
/*     */ import com.itextpdf.text.Chunk;
/*     */ import com.itextpdf.text.Phrase;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public class VerticalText
/*     */ {
/*     */   public static final int NO_MORE_TEXT = 1;
/*     */   public static final int NO_MORE_COLUMN = 2;
/*  69 */   protected ArrayList<PdfChunk> chunks = new ArrayList();
/*     */   protected PdfContentByte text;
/*  75 */   protected int alignment = 0;
/*     */ 
/*  78 */   protected int currentChunkMarker = -1;
/*     */   protected PdfChunk currentStandbyChunk;
/*     */   protected String splittedChunkText;
/*     */   protected float leading;
/*     */   protected float startX;
/*     */   protected float startY;
/*     */   protected int maxLines;
/*     */   protected float height;
/* 258 */   private Float curCharSpace = Float.valueOf(0.0F);
/*     */ 
/*     */   public VerticalText(PdfContentByte text)
/*     */   {
/* 111 */     this.text = text;
/*     */   }
/*     */ 
/*     */   public void addText(Phrase phrase)
/*     */   {
/* 119 */     for (Chunk c : phrase.getChunks())
/* 120 */       this.chunks.add(new PdfChunk(c, null));
/*     */   }
/*     */ 
/*     */   public void addText(Chunk chunk)
/*     */   {
/* 129 */     this.chunks.add(new PdfChunk(chunk, null));
/*     */   }
/*     */ 
/*     */   public void setVerticalLayout(float startX, float startY, float height, int maxLines, float leading)
/*     */   {
/* 140 */     this.startX = startX;
/* 141 */     this.startY = startY;
/* 142 */     this.height = height;
/* 143 */     this.maxLines = maxLines;
/* 144 */     setLeading(leading);
/*     */   }
/*     */ 
/*     */   public void setLeading(float leading)
/*     */   {
/* 151 */     this.leading = leading;
/*     */   }
/*     */ 
/*     */   public float getLeading()
/*     */   {
/* 158 */     return this.leading;
/*     */   }
/*     */ 
/*     */   protected PdfLine createLine(float width)
/*     */   {
/* 167 */     if (this.chunks.isEmpty())
/* 168 */       return null;
/* 169 */     this.splittedChunkText = null;
/* 170 */     this.currentStandbyChunk = null;
/* 171 */     PdfLine line = new PdfLine(0.0F, width, this.alignment, 0.0F);
/*     */ 
/* 173 */     for (this.currentChunkMarker = 0; this.currentChunkMarker < this.chunks.size(); this.currentChunkMarker += 1) {
/* 174 */       PdfChunk original = (PdfChunk)this.chunks.get(this.currentChunkMarker);
/* 175 */       String total = original.toString();
/* 176 */       this.currentStandbyChunk = line.add(original);
/* 177 */       if (this.currentStandbyChunk != null) {
/* 178 */         this.splittedChunkText = original.toString();
/* 179 */         original.setValue(total);
/* 180 */         return line;
/*     */       }
/*     */     }
/* 183 */     return line;
/*     */   }
/*     */ 
/*     */   protected void shortenChunkArray()
/*     */   {
/* 190 */     if (this.currentChunkMarker < 0)
/* 191 */       return;
/* 192 */     if (this.currentChunkMarker >= this.chunks.size()) {
/* 193 */       this.chunks.clear();
/* 194 */       return;
/*     */     }
/* 196 */     PdfChunk split = (PdfChunk)this.chunks.get(this.currentChunkMarker);
/* 197 */     split.setValue(this.splittedChunkText);
/* 198 */     this.chunks.set(this.currentChunkMarker, this.currentStandbyChunk);
/* 199 */     for (int j = this.currentChunkMarker - 1; j >= 0; j--)
/* 200 */       this.chunks.remove(j);
/*     */   }
/*     */ 
/*     */   public int go()
/*     */   {
/* 209 */     return go(false);
/*     */   }
/*     */ 
/*     */   public int go(boolean simulate)
/*     */   {
/* 219 */     boolean dirty = false;
/* 220 */     PdfContentByte graphics = null;
/* 221 */     if (this.text != null) {
/* 222 */       graphics = this.text.getDuplicate();
/*     */     }
/* 224 */     else if (!simulate)
/* 225 */       throw new NullPointerException(MessageLocalization.getComposedMessage("verticaltext.go.with.simulate.eq.eq.false.and.text.eq.eq.null", new Object[0]));
/* 226 */     int status = 0;
/*     */     while (true) {
/* 228 */       if (this.maxLines <= 0) {
/* 229 */         status = 2;
/* 230 */         if (!this.chunks.isEmpty()) break;
/* 231 */         status |= 1; break;
/*     */       }
/*     */ 
/* 234 */       if (this.chunks.isEmpty()) {
/* 235 */         status = 1;
/* 236 */         break;
/*     */       }
/* 238 */       PdfLine line = createLine(this.height);
/* 239 */       if ((!simulate) && (!dirty)) {
/* 240 */         this.text.beginText();
/* 241 */         dirty = true;
/*     */       }
/* 243 */       shortenChunkArray();
/* 244 */       if (!simulate) {
/* 245 */         this.text.setTextMatrix(this.startX, this.startY - line.indentLeft());
/* 246 */         writeLine(line, this.text, graphics);
/*     */       }
/* 248 */       this.maxLines -= 1;
/* 249 */       this.startX -= this.leading;
/*     */     }
/* 251 */     if (dirty) {
/* 252 */       this.text.endText();
/* 253 */       this.text.add(graphics);
/*     */     }
/* 255 */     return status;
/*     */   }
/*     */ 
/*     */   void writeLine(PdfLine line, PdfContentByte text, PdfContentByte graphics)
/*     */   {
/* 261 */     PdfFont currentFont = null;
/*     */ 
/* 263 */     for (Iterator j = line.iterator(); j.hasNext(); ) {
/* 264 */       PdfChunk chunk = (PdfChunk)j.next();
/*     */ 
/* 266 */       if ((!chunk.isImage()) && (chunk.font().compareTo(currentFont) != 0)) {
/* 267 */         currentFont = chunk.font();
/* 268 */         text.setFontAndSize(currentFont.getFont(), currentFont.size());
/*     */       }
/* 270 */       Object[] textRender = (Object[])chunk.getAttribute("TEXTRENDERMODE");
/* 271 */       int tr = 0;
/* 272 */       float strokeWidth = 1.0F;
/* 273 */       BaseColor color = chunk.color();
/* 274 */       BaseColor strokeColor = null;
/* 275 */       if (textRender != null) {
/* 276 */         tr = ((Integer)textRender[0]).intValue() & 0x3;
/* 277 */         if (tr != 0)
/* 278 */           text.setTextRenderingMode(tr);
/* 279 */         if ((tr == 1) || (tr == 2)) {
/* 280 */           strokeWidth = ((Float)textRender[1]).floatValue();
/* 281 */           if (strokeWidth != 1.0F)
/* 282 */             text.setLineWidth(strokeWidth);
/* 283 */           strokeColor = (BaseColor)textRender[2];
/* 284 */           if (strokeColor == null)
/* 285 */             strokeColor = color;
/* 286 */           if (strokeColor != null) {
/* 287 */             text.setColorStroke(strokeColor);
/*     */           }
/*     */         }
/*     */       }
/* 291 */       Float charSpace = (Float)chunk.getAttribute("CHAR_SPACING");
/*     */ 
/* 293 */       if ((charSpace != null) && (!this.curCharSpace.equals(charSpace))) {
/* 294 */         this.curCharSpace = Float.valueOf(charSpace.floatValue());
/* 295 */         text.setCharacterSpacing(this.curCharSpace.floatValue());
/*     */       }
/* 297 */       if (color != null) {
/* 298 */         text.setColorFill(color);
/*     */       }
/* 300 */       text.showText(chunk.toString());
/*     */ 
/* 302 */       if (color != null)
/* 303 */         text.resetRGBColorFill();
/* 304 */       if (tr != 0)
/* 305 */         text.setTextRenderingMode(0);
/* 306 */       if (strokeColor != null)
/* 307 */         text.resetRGBColorStroke();
/* 308 */       if (strokeWidth != 1.0F)
/* 309 */         text.setLineWidth(1.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setOrigin(float startX, float startY)
/*     */   {
/* 318 */     this.startX = startX;
/* 319 */     this.startY = startY;
/*     */   }
/*     */ 
/*     */   public float getOriginX()
/*     */   {
/* 327 */     return this.startX;
/*     */   }
/*     */ 
/*     */   public float getOriginY()
/*     */   {
/* 334 */     return this.startY;
/*     */   }
/*     */ 
/*     */   public int getMaxLines()
/*     */   {
/* 342 */     return this.maxLines;
/*     */   }
/*     */ 
/*     */   public void setMaxLines(int maxLines)
/*     */   {
/* 349 */     this.maxLines = maxLines;
/*     */   }
/*     */ 
/*     */   public float getHeight()
/*     */   {
/* 356 */     return this.height;
/*     */   }
/*     */ 
/*     */   public void setHeight(float height)
/*     */   {
/* 363 */     this.height = height;
/*     */   }
/*     */ 
/*     */   public void setAlignment(int alignment)
/*     */   {
/* 371 */     this.alignment = alignment;
/*     */   }
/*     */ 
/*     */   public int getAlignment()
/*     */   {
/* 379 */     return this.alignment;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.VerticalText
 * JD-Core Version:    0.6.2
 */