/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.Rectangle;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class PdfAppearance extends PdfTemplate
/*     */ {
/*  56 */   public static final HashMap<String, PdfName> stdFieldFontNames = new HashMap();
/*     */ 
/*     */   PdfAppearance()
/*     */   {
/*  91 */     this.separator = 32;
/*     */   }
/*     */ 
/*     */   PdfAppearance(PdfIndirectReference iref) {
/*  95 */     this.thisReference = iref;
/*     */   }
/*     */ 
/*     */   PdfAppearance(PdfWriter wr)
/*     */   {
/* 105 */     super(wr);
/* 106 */     this.separator = 32;
/*     */   }
/*     */ 
/*     */   public static PdfAppearance createAppearance(PdfWriter writer, float width, float height)
/*     */   {
/* 118 */     return createAppearance(writer, width, height, null);
/*     */   }
/*     */ 
/*     */   static PdfAppearance createAppearance(PdfWriter writer, float width, float height, PdfName forcedName) {
/* 122 */     PdfAppearance template = new PdfAppearance(writer);
/* 123 */     template.setWidth(width);
/* 124 */     template.setHeight(height);
/* 125 */     writer.addDirectTemplateSimple(template, forcedName);
/* 126 */     return template;
/*     */   }
/*     */ 
/*     */   public void setFontAndSize(BaseFont bf, float size)
/*     */   {
/* 137 */     checkWriter();
/* 138 */     this.state.size = size;
/* 139 */     if (bf.getFontType() == 4) {
/* 140 */       this.state.fontDetails = new FontDetails(null, ((DocumentFont)bf).getIndirectReference(), bf);
/*     */     }
/*     */     else
/* 143 */       this.state.fontDetails = this.writer.addSimple(bf);
/* 144 */     PdfName psn = (PdfName)stdFieldFontNames.get(bf.getPostscriptFontName());
/* 145 */     if (psn == null) {
/* 146 */       if ((bf.isSubset()) && (bf.getFontType() == 3)) {
/* 147 */         psn = this.state.fontDetails.getFontName();
/*     */       } else {
/* 149 */         psn = new PdfName(bf.getPostscriptFontName());
/* 150 */         this.state.fontDetails.setSubset(false);
/*     */       }
/*     */     }
/* 153 */     PageResources prs = getPageResources();
/*     */ 
/* 155 */     prs.addFont(psn, this.state.fontDetails.getIndirectReference());
/* 156 */     this.content.append(psn.getBytes()).append(' ').append(size).append(" Tf").append_i(this.separator);
/*     */   }
/*     */ 
/*     */   public PdfContentByte getDuplicate()
/*     */   {
/* 161 */     PdfAppearance tpl = new PdfAppearance();
/* 162 */     tpl.writer = this.writer;
/* 163 */     tpl.pdf = this.pdf;
/* 164 */     tpl.thisReference = this.thisReference;
/* 165 */     tpl.pageResources = this.pageResources;
/* 166 */     tpl.bBox = new Rectangle(this.bBox);
/* 167 */     tpl.group = this.group;
/* 168 */     tpl.layer = this.layer;
/* 169 */     if (this.matrix != null) {
/* 170 */       tpl.matrix = new PdfArray(this.matrix);
/*     */     }
/* 172 */     tpl.separator = this.separator;
/* 173 */     return tpl;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  58 */     stdFieldFontNames.put("Courier-BoldOblique", new PdfName("CoBO"));
/*  59 */     stdFieldFontNames.put("Courier-Bold", new PdfName("CoBo"));
/*  60 */     stdFieldFontNames.put("Courier-Oblique", new PdfName("CoOb"));
/*  61 */     stdFieldFontNames.put("Courier", new PdfName("Cour"));
/*  62 */     stdFieldFontNames.put("Helvetica-BoldOblique", new PdfName("HeBO"));
/*  63 */     stdFieldFontNames.put("Helvetica-Bold", new PdfName("HeBo"));
/*  64 */     stdFieldFontNames.put("Helvetica-Oblique", new PdfName("HeOb"));
/*  65 */     stdFieldFontNames.put("Helvetica", PdfName.HELV);
/*  66 */     stdFieldFontNames.put("Symbol", new PdfName("Symb"));
/*  67 */     stdFieldFontNames.put("Times-BoldItalic", new PdfName("TiBI"));
/*  68 */     stdFieldFontNames.put("Times-Bold", new PdfName("TiBo"));
/*  69 */     stdFieldFontNames.put("Times-Italic", new PdfName("TiIt"));
/*  70 */     stdFieldFontNames.put("Times-Roman", new PdfName("TiRo"));
/*  71 */     stdFieldFontNames.put("ZapfDingbats", PdfName.ZADB);
/*  72 */     stdFieldFontNames.put("HYSMyeongJo-Medium", new PdfName("HySm"));
/*  73 */     stdFieldFontNames.put("HYGoThic-Medium", new PdfName("HyGo"));
/*  74 */     stdFieldFontNames.put("HeiseiKakuGo-W5", new PdfName("KaGo"));
/*  75 */     stdFieldFontNames.put("HeiseiMin-W3", new PdfName("KaMi"));
/*  76 */     stdFieldFontNames.put("MHei-Medium", new PdfName("MHei"));
/*  77 */     stdFieldFontNames.put("MSung-Light", new PdfName("MSun"));
/*  78 */     stdFieldFontNames.put("STSong-Light", new PdfName("STSo"));
/*  79 */     stdFieldFontNames.put("MSungStd-Light", new PdfName("MSun"));
/*  80 */     stdFieldFontNames.put("STSongStd-Light", new PdfName("STSo"));
/*  81 */     stdFieldFontNames.put("HYSMyeongJoStd-Medium", new PdfName("HySm"));
/*  82 */     stdFieldFontNames.put("KozMinPro-Regular", new PdfName("KaMi"));
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfAppearance
 * JD-Core Version:    0.6.2
 */