/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class Type3Font extends BaseFont
/*     */ {
/*     */   private boolean[] usedSlot;
/*  58 */   private IntHashtable widths3 = new IntHashtable();
/*  59 */   private HashMap<Integer, Type3Glyph> char2glyph = new HashMap();
/*     */   private PdfWriter writer;
/*  61 */   private float llx = (0.0F / 0.0F);
/*     */   private float lly;
/*     */   private float urx;
/*     */   private float ury;
/*  62 */   private PageResources pageResources = new PageResources();
/*     */   private boolean colorized;
/*     */ 
/*     */   public Type3Font(PdfWriter writer, char[] chars, boolean colorized)
/*     */   {
/*  73 */     this(writer, colorized);
/*     */   }
/*     */ 
/*     */   public Type3Font(PdfWriter writer, boolean colorized)
/*     */   {
/* 104 */     this.writer = writer;
/* 105 */     this.colorized = colorized;
/* 106 */     this.fontType = 5;
/* 107 */     this.usedSlot = new boolean[256];
/*     */   }
/*     */ 
/*     */   public PdfContentByte defineGlyph(char c, float wx, float llx, float lly, float urx, float ury)
/*     */   {
/* 125 */     if ((c == 0) || (c > 'ÿ'))
/* 126 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.char.1.doesn.t.belong.in.this.type3.font", c));
/* 127 */     this.usedSlot[c] = true;
/* 128 */     Integer ck = Integer.valueOf(c);
/* 129 */     Type3Glyph glyph = (Type3Glyph)this.char2glyph.get(ck);
/* 130 */     if (glyph != null)
/* 131 */       return glyph;
/* 132 */     this.widths3.put(c, (int)wx);
/* 133 */     if (!this.colorized) {
/* 134 */       if (Float.isNaN(this.llx)) {
/* 135 */         this.llx = llx;
/* 136 */         this.lly = lly;
/* 137 */         this.urx = urx;
/* 138 */         this.ury = ury;
/*     */       }
/*     */       else {
/* 141 */         this.llx = Math.min(this.llx, llx);
/* 142 */         this.lly = Math.min(this.lly, lly);
/* 143 */         this.urx = Math.max(this.urx, urx);
/* 144 */         this.ury = Math.max(this.ury, ury);
/*     */       }
/*     */     }
/* 147 */     glyph = new Type3Glyph(this.writer, this.pageResources, wx, llx, lly, urx, ury, this.colorized);
/* 148 */     this.char2glyph.put(ck, glyph);
/* 149 */     return glyph;
/*     */   }
/*     */ 
/*     */   public String[][] getFamilyFontName()
/*     */   {
/* 154 */     return getFullFontName();
/*     */   }
/*     */ 
/*     */   public float getFontDescriptor(int key, float fontSize)
/*     */   {
/* 159 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   public String[][] getFullFontName()
/*     */   {
/* 164 */     return new String[][] { { "", "", "", "" } };
/*     */   }
/*     */ 
/*     */   public String[][] getAllNameEntries()
/*     */   {
/* 172 */     return new String[][] { { "4", "", "", "", "" } };
/*     */   }
/*     */ 
/*     */   public int getKerning(int char1, int char2)
/*     */   {
/* 177 */     return 0;
/*     */   }
/*     */ 
/*     */   public String getPostscriptFontName()
/*     */   {
/* 182 */     return "";
/*     */   }
/*     */ 
/*     */   protected int[] getRawCharBBox(int c, String name)
/*     */   {
/* 187 */     return null;
/*     */   }
/*     */ 
/*     */   int getRawWidth(int c, String name)
/*     */   {
/* 192 */     return 0;
/*     */   }
/*     */ 
/*     */   public boolean hasKernPairs()
/*     */   {
/* 197 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean setKerning(int char1, int char2, int kern)
/*     */   {
/* 202 */     return false;
/*     */   }
/*     */ 
/*     */   public void setPostscriptFontName(String name)
/*     */   {
/*     */   }
/*     */ 
/*     */   void writeFont(PdfWriter writer, PdfIndirectReference ref, Object[] params) throws DocumentException, IOException
/*     */   {
/* 211 */     if (this.writer != writer) {
/* 212 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("type3.font.used.with.the.wrong.pdfwriter", new Object[0]));
/*     */     }
/*     */ 
/* 215 */     int firstChar = 0;
/* 216 */     while ((firstChar < this.usedSlot.length) && (this.usedSlot[firstChar] == 0)) firstChar++;
/*     */ 
/* 218 */     if (firstChar == this.usedSlot.length) {
/* 219 */       throw new DocumentException(MessageLocalization.getComposedMessage("no.glyphs.defined.for.type3.font", new Object[0]));
/*     */     }
/* 221 */     int lastChar = this.usedSlot.length - 1;
/* 222 */     while ((lastChar >= firstChar) && (this.usedSlot[lastChar] == 0)) lastChar--;
/*     */ 
/* 224 */     int[] widths = new int[lastChar - firstChar + 1];
/* 225 */     int[] invOrd = new int[lastChar - firstChar + 1];
/*     */ 
/* 227 */     int invOrdIndx = 0; int w = 0;
/* 228 */     for (int u = firstChar; u <= lastChar; w++) {
/* 229 */       if (this.usedSlot[u] != 0) {
/* 230 */         invOrd[(invOrdIndx++)] = u;
/* 231 */         widths[w] = this.widths3.get(u);
/*     */       }
/* 228 */       u++;
/*     */     }
/*     */ 
/* 234 */     PdfArray diffs = new PdfArray();
/* 235 */     PdfDictionary charprocs = new PdfDictionary();
/* 236 */     int last = -1;
/* 237 */     for (int k = 0; k < invOrdIndx; k++) {
/* 238 */       int c = invOrd[k];
/* 239 */       if (c > last) {
/* 240 */         last = c;
/* 241 */         diffs.add(new PdfNumber(last));
/*     */       }
/* 243 */       last++;
/* 244 */       int c2 = invOrd[k];
/* 245 */       String s = GlyphList.unicodeToName(c2);
/* 246 */       if (s == null)
/* 247 */         s = "a" + c2;
/* 248 */       PdfName n = new PdfName(s);
/* 249 */       diffs.add(n);
/* 250 */       Type3Glyph glyph = (Type3Glyph)this.char2glyph.get(Integer.valueOf(c2));
/* 251 */       PdfStream stream = new PdfStream(glyph.toPdf(null));
/* 252 */       stream.flateCompress(this.compressionLevel);
/* 253 */       PdfIndirectReference refp = writer.addToBody(stream).getIndirectReference();
/* 254 */       charprocs.put(n, refp);
/*     */     }
/* 256 */     PdfDictionary font = new PdfDictionary(PdfName.FONT);
/* 257 */     font.put(PdfName.SUBTYPE, PdfName.TYPE3);
/* 258 */     if (this.colorized)
/* 259 */       font.put(PdfName.FONTBBOX, new PdfRectangle(0.0F, 0.0F, 0.0F, 0.0F));
/*     */     else
/* 261 */       font.put(PdfName.FONTBBOX, new PdfRectangle(this.llx, this.lly, this.urx, this.ury));
/* 262 */     font.put(PdfName.FONTMATRIX, new PdfArray(new float[] { 0.001F, 0.0F, 0.0F, 0.001F, 0.0F, 0.0F }));
/* 263 */     font.put(PdfName.CHARPROCS, writer.addToBody(charprocs).getIndirectReference());
/* 264 */     PdfDictionary encoding = new PdfDictionary();
/* 265 */     encoding.put(PdfName.DIFFERENCES, diffs);
/* 266 */     font.put(PdfName.ENCODING, writer.addToBody(encoding).getIndirectReference());
/* 267 */     font.put(PdfName.FIRSTCHAR, new PdfNumber(firstChar));
/* 268 */     font.put(PdfName.LASTCHAR, new PdfNumber(lastChar));
/* 269 */     font.put(PdfName.WIDTHS, writer.addToBody(new PdfArray(widths)).getIndirectReference());
/* 270 */     if (this.pageResources.hasResources())
/* 271 */       font.put(PdfName.RESOURCES, writer.addToBody(this.pageResources.getResources()).getIndirectReference());
/* 272 */     writer.addToBody(font, ref);
/*     */   }
/*     */ 
/*     */   public PdfStream getFullFontStream()
/*     */   {
/* 282 */     return null;
/*     */   }
/*     */ 
/*     */   public byte[] convertToBytes(String text)
/*     */   {
/* 288 */     char[] cc = text.toCharArray();
/* 289 */     byte[] b = new byte[cc.length];
/* 290 */     int p = 0;
/* 291 */     for (int k = 0; k < cc.length; k++) {
/* 292 */       char c = cc[k];
/* 293 */       if (charExists(c))
/* 294 */         b[(p++)] = ((byte)c);
/*     */     }
/* 296 */     if (b.length == p)
/* 297 */       return b;
/* 298 */     byte[] b2 = new byte[p];
/* 299 */     System.arraycopy(b, 0, b2, 0, p);
/* 300 */     return b2;
/*     */   }
/*     */ 
/*     */   byte[] convertToBytes(int char1)
/*     */   {
/* 305 */     if (charExists(char1))
/* 306 */       return new byte[] { (byte)char1 };
/* 307 */     return new byte[0];
/*     */   }
/*     */ 
/*     */   public int getWidth(int char1)
/*     */   {
/* 312 */     if (!this.widths3.containsKey(char1))
/* 313 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.char.1.is.not.defined.in.a.type3.font", char1));
/* 314 */     return this.widths3.get(char1);
/*     */   }
/*     */ 
/*     */   public int getWidth(String text)
/*     */   {
/* 319 */     char[] c = text.toCharArray();
/* 320 */     int total = 0;
/* 321 */     for (int k = 0; k < c.length; k++)
/* 322 */       total += getWidth(c[k]);
/* 323 */     return total;
/*     */   }
/*     */ 
/*     */   public int[] getCharBBox(int c)
/*     */   {
/* 328 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean charExists(int c)
/*     */   {
/* 333 */     if ((c > 0) && (c < 256)) {
/* 334 */       return this.usedSlot[c];
/*     */     }
/* 336 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean setCharAdvance(int c, int advance)
/*     */   {
/* 342 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.Type3Font
 * JD-Core Version:    0.6.2
 */