/*     */ package com.itextpdf.text.html.simpleparser;
/*     */ 
/*     */ import com.itextpdf.text.Chunk;
/*     */ import com.itextpdf.text.Element;
/*     */ import com.itextpdf.text.ElementListener;
/*     */ import com.itextpdf.text.html.HtmlUtilities;
/*     */ import com.itextpdf.text.pdf.PdfPCell;
/*     */ import com.itextpdf.text.pdf.PdfPTable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ @Deprecated
/*     */ public class TableWrapper
/*     */   implements Element
/*     */ {
/*  75 */   private final Map<String, String> styles = new HashMap();
/*     */ 
/*  79 */   private final List<List<PdfPCell>> rows = new ArrayList();
/*     */   private float[] colWidths;
/*     */ 
/*     */   public TableWrapper(Map<String, String> attrs)
/*     */   {
/*  92 */     this.styles.putAll(attrs);
/*     */   }
/*     */ 
/*     */   public void addRow(List<PdfPCell> row)
/*     */   {
/* 100 */     if (row != null) {
/* 101 */       Collections.reverse(row);
/* 102 */       this.rows.add(row);
/* 103 */       row = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setColWidths(float[] colWidths)
/*     */   {
/* 112 */     this.colWidths = colWidths;
/*     */   }
/*     */ 
/*     */   public PdfPTable createTable()
/*     */   {
/* 122 */     if (this.rows.isEmpty()) {
/* 123 */       return new PdfPTable(1);
/*     */     }
/* 125 */     int ncol = 0;
/* 126 */     for (PdfPCell pc : (List)this.rows.get(0)) {
/* 127 */       ncol += pc.getColspan();
/*     */     }
/* 129 */     PdfPTable table = new PdfPTable(ncol);
/*     */ 
/* 131 */     String width = (String)this.styles.get("width");
/* 132 */     if (width == null) {
/* 133 */       table.setWidthPercentage(100.0F);
/*     */     }
/* 135 */     else if (width.endsWith("%")) {
/* 136 */       table.setWidthPercentage(Float.parseFloat(width.substring(0, width.length() - 1)));
/*     */     } else {
/* 138 */       table.setTotalWidth(Float.parseFloat(width));
/* 139 */       table.setLockedWidth(true);
/*     */     }
/*     */ 
/* 143 */     String alignment = (String)this.styles.get("align");
/* 144 */     int align = 0;
/* 145 */     if (alignment != null) {
/* 146 */       align = HtmlUtilities.alignmentValue(alignment);
/*     */     }
/* 148 */     table.setHorizontalAlignment(align);
/*     */     try
/*     */     {
/* 151 */       if (this.colWidths != null)
/* 152 */         table.setWidths(this.colWidths);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/* 157 */     for (List col : this.rows) {
/* 158 */       for (PdfPCell pc : col) {
/* 159 */         table.addCell(pc);
/*     */       }
/*     */     }
/* 162 */     return table;
/*     */   }
/*     */ 
/*     */   public List<Chunk> getChunks()
/*     */   {
/* 171 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean isContent()
/*     */   {
/* 178 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isNestable()
/*     */   {
/* 185 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean process(ElementListener listener)
/*     */   {
/* 192 */     return false;
/*     */   }
/*     */ 
/*     */   public int type()
/*     */   {
/* 199 */     return 0;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.html.simpleparser.TableWrapper
 * JD-Core Version:    0.6.2
 */