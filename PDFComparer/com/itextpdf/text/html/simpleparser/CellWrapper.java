/*     */ package com.itextpdf.text.html.simpleparser;
/*     */ 
/*     */ import com.itextpdf.text.Chunk;
/*     */ import com.itextpdf.text.Element;
/*     */ import com.itextpdf.text.ElementListener;
/*     */ import com.itextpdf.text.Phrase;
/*     */ import com.itextpdf.text.TextElementArray;
/*     */ import com.itextpdf.text.html.HtmlUtilities;
/*     */ import com.itextpdf.text.pdf.PdfPCell;
/*     */ import java.util.List;
/*     */ 
/*     */ @Deprecated
/*     */ public class CellWrapper
/*     */   implements TextElementArray
/*     */ {
/*     */   private final PdfPCell cell;
/*     */   private float width;
/*     */   private boolean percentage;
/*     */ 
/*     */   public CellWrapper(String tag, ChainedProperties chain)
/*     */   {
/*  90 */     this.cell = createPdfPCell(tag, chain);
/*  91 */     String value = chain.getProperty("width");
/*  92 */     if (value != null) {
/*  93 */       value = value.trim();
/*  94 */       if (value.endsWith("%")) {
/*  95 */         this.percentage = true;
/*  96 */         value = value.substring(0, value.length() - 1);
/*     */       }
/*  98 */       this.width = Float.parseFloat(value);
/*     */     }
/*     */   }
/*     */ 
/*     */   public PdfPCell createPdfPCell(String tag, ChainedProperties chain)
/*     */   {
/* 109 */     PdfPCell cell = new PdfPCell((Phrase)null);
/*     */ 
/* 111 */     String value = chain.getProperty("colspan");
/* 112 */     if (value != null) {
/* 113 */       cell.setColspan(Integer.parseInt(value));
/*     */     }
/* 115 */     value = chain.getProperty("rowspan");
/* 116 */     if (value != null) {
/* 117 */       cell.setRowspan(Integer.parseInt(value));
/*     */     }
/* 119 */     if (tag.equals("th"))
/* 120 */       cell.setHorizontalAlignment(1);
/* 121 */     value = chain.getProperty("align");
/* 122 */     if (value != null) {
/* 123 */       cell.setHorizontalAlignment(HtmlUtilities.alignmentValue(value));
/*     */     }
/*     */ 
/* 126 */     value = chain.getProperty("valign");
/* 127 */     cell.setVerticalAlignment(5);
/* 128 */     if (value != null) {
/* 129 */       cell.setVerticalAlignment(HtmlUtilities.alignmentValue(value));
/*     */     }
/*     */ 
/* 132 */     value = chain.getProperty("border");
/* 133 */     float border = 0.0F;
/* 134 */     if (value != null)
/* 135 */       border = Float.parseFloat(value);
/* 136 */     cell.setBorderWidth(border);
/*     */ 
/* 138 */     value = chain.getProperty("cellpadding");
/* 139 */     if (value != null)
/* 140 */       cell.setPadding(Float.parseFloat(value));
/* 141 */     cell.setUseDescender(true);
/*     */ 
/* 143 */     value = chain.getProperty("bgcolor");
/* 144 */     cell.setBackgroundColor(HtmlUtilities.decodeColor(value));
/* 145 */     return cell;
/*     */   }
/*     */ 
/*     */   public PdfPCell getCell()
/*     */   {
/* 153 */     return this.cell;
/*     */   }
/*     */ 
/*     */   public float getWidth()
/*     */   {
/* 162 */     return this.width;
/*     */   }
/*     */ 
/*     */   public boolean isPercentage()
/*     */   {
/* 171 */     return this.percentage;
/*     */   }
/*     */ 
/*     */   public boolean add(Element o)
/*     */   {
/* 179 */     this.cell.addElement(o);
/* 180 */     return true;
/*     */   }
/*     */ 
/*     */   public List<Chunk> getChunks()
/*     */   {
/* 189 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean isContent()
/*     */   {
/* 196 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isNestable()
/*     */   {
/* 203 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean process(ElementListener listener)
/*     */   {
/* 210 */     return false;
/*     */   }
/*     */ 
/*     */   public int type()
/*     */   {
/* 217 */     return 0;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.html.simpleparser.CellWrapper
 * JD-Core Version:    0.6.2
 */