/*     */ package com.itextpdf.text.pdf.draw;
/*     */ 
/*     */ import com.itextpdf.text.Chunk;
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import com.itextpdf.text.Element;
/*     */ import com.itextpdf.text.ElementListener;
/*     */ import com.itextpdf.text.pdf.PdfContentByte;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class VerticalPositionMark
/*     */   implements DrawInterface, Element
/*     */ {
/*  66 */   protected DrawInterface drawInterface = null;
/*     */ 
/*  69 */   protected float offset = 0.0F;
/*     */ 
/*     */   public VerticalPositionMark()
/*     */   {
/*     */   }
/*     */ 
/*     */   public VerticalPositionMark(DrawInterface drawInterface, float offset)
/*     */   {
/*  85 */     this.drawInterface = drawInterface;
/*  86 */     this.offset = offset;
/*     */   }
/*     */ 
/*     */   public void draw(PdfContentByte canvas, float llx, float lly, float urx, float ury, float y)
/*     */   {
/*  93 */     if (this.drawInterface != null)
/*  94 */       this.drawInterface.draw(canvas, llx, lly, urx, ury, y + this.offset);
/*     */   }
/*     */ 
/*     */   public boolean process(ElementListener listener)
/*     */   {
/*     */     try
/*     */     {
/* 103 */       return listener.add(this); } catch (DocumentException e) {
/*     */     }
/* 105 */     return false;
/*     */   }
/*     */ 
/*     */   public int type()
/*     */   {
/* 113 */     return 55;
/*     */   }
/*     */ 
/*     */   public boolean isContent()
/*     */   {
/* 120 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isNestable()
/*     */   {
/* 127 */     return false;
/*     */   }
/*     */ 
/*     */   public List<Chunk> getChunks()
/*     */   {
/* 134 */     List list = new ArrayList();
/* 135 */     list.add(new Chunk(this, true));
/* 136 */     return list;
/*     */   }
/*     */ 
/*     */   public DrawInterface getDrawInterface()
/*     */   {
/* 144 */     return this.drawInterface;
/*     */   }
/*     */ 
/*     */   public void setDrawInterface(DrawInterface drawInterface)
/*     */   {
/* 152 */     this.drawInterface = drawInterface;
/*     */   }
/*     */ 
/*     */   public float getOffset()
/*     */   {
/* 160 */     return this.offset;
/*     */   }
/*     */ 
/*     */   public void setOffset(float offset)
/*     */   {
/* 170 */     this.offset = offset;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.draw.VerticalPositionMark
 * JD-Core Version:    0.6.2
 */