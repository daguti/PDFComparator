/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import com.itextpdf.text.Utilities;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.pdf.fonts.cmaps.CMapByteCid;
/*     */ import com.itextpdf.text.pdf.fonts.cmaps.CMapCache;
/*     */ import com.itextpdf.text.pdf.fonts.cmaps.CMapCidUni;
/*     */ import com.itextpdf.text.pdf.fonts.cmaps.CMapParserEx;
/*     */ import com.itextpdf.text.pdf.fonts.cmaps.CMapSequence;
/*     */ import com.itextpdf.text.pdf.fonts.cmaps.CMapToUnicode;
/*     */ import com.itextpdf.text.pdf.fonts.cmaps.CidLocationFromByte;
/*     */ import com.itextpdf.text.pdf.fonts.cmaps.IdentityToUnicode;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ 
/*     */ public class CMapAwareDocumentFont extends DocumentFont
/*     */ {
/*     */   private PdfDictionary fontDic;
/*     */   private int spaceWidth;
/*     */   private CMapToUnicode toUnicodeCmap;
/*     */   private CMapByteCid byteCid;
/*     */   private CMapCidUni cidUni;
/*     */   private char[] cidbyte2uni;
/*     */   private Map<Integer, Integer> uni2cid;
/*     */ 
/*     */   public CMapAwareDocumentFont(PdfDictionary font)
/*     */   {
/*  90 */     super(font);
/*  91 */     this.fontDic = font;
/*  92 */     initFont();
/*     */   }
/*     */ 
/*     */   public CMapAwareDocumentFont(PRIndirectReference refFont)
/*     */   {
/* 100 */     super(refFont);
/* 101 */     this.fontDic = ((PdfDictionary)PdfReader.getPdfObjectRelease(refFont));
/* 102 */     initFont();
/*     */   }
/*     */ 
/*     */   private void initFont() {
/* 106 */     processToUnicode();
/*     */     try
/*     */     {
/* 109 */       processUni2Byte();
/*     */ 
/* 111 */       this.spaceWidth = super.getWidth(32);
/* 112 */       if (this.spaceWidth == 0) {
/* 113 */         this.spaceWidth = computeAverageWidth();
/*     */       }
/* 115 */       if (this.cjkEncoding != null) {
/* 116 */         this.byteCid = CMapCache.getCachedCMapByteCid(this.cjkEncoding);
/* 117 */         this.cidUni = CMapCache.getCachedCMapCidUni(this.uniMap);
/*     */       }
/*     */     }
/*     */     catch (Exception ex) {
/* 121 */       throw new ExceptionConverter(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void processToUnicode()
/*     */   {
/* 129 */     PdfObject toUni = PdfReader.getPdfObjectRelease(this.fontDic.get(PdfName.TOUNICODE));
/* 130 */     if ((toUni instanceof PRStream)) {
/*     */       try {
/* 132 */         byte[] touni = PdfReader.getStreamBytes((PRStream)toUni);
/* 133 */         CidLocationFromByte lb = new CidLocationFromByte(touni);
/* 134 */         this.toUnicodeCmap = new CMapToUnicode();
/* 135 */         CMapParserEx.parseCid("", this.toUnicodeCmap, lb);
/* 136 */         this.uni2cid = this.toUnicodeCmap.createReverseMapping();
/*     */       } catch (IOException e) {
/* 138 */         this.toUnicodeCmap = null;
/* 139 */         this.uni2cid = null;
/*     */       }
/*     */ 
/*     */     }
/* 144 */     else if (this.isType0)
/*     */       try
/*     */       {
/* 147 */         PdfName encodingName = this.fontDic.getAsName(PdfName.ENCODING);
/* 148 */         if (encodingName == null)
/* 149 */           return;
/* 150 */         String enc = PdfName.decodeName(encodingName.toString());
/* 151 */         if (!enc.equals("Identity-H"))
/* 152 */           return;
/* 153 */         PdfArray df = (PdfArray)PdfReader.getPdfObjectRelease(this.fontDic.get(PdfName.DESCENDANTFONTS));
/* 154 */         PdfDictionary cidft = (PdfDictionary)PdfReader.getPdfObjectRelease(df.getPdfObject(0));
/* 155 */         PdfDictionary cidinfo = cidft.getAsDict(PdfName.CIDSYSTEMINFO);
/* 156 */         if (cidinfo == null)
/* 157 */           return;
/* 158 */         PdfString ordering = cidinfo.getAsString(PdfName.ORDERING);
/* 159 */         if (ordering == null)
/* 160 */           return;
/* 161 */         CMapToUnicode touni = IdentityToUnicode.GetMapFromOrdering(ordering.toUnicodeString());
/* 162 */         if (touni == null)
/* 163 */           return;
/* 164 */         this.toUnicodeCmap = touni;
/* 165 */         this.uni2cid = this.toUnicodeCmap.createReverseMapping();
/*     */       } catch (IOException e) {
/* 167 */         this.toUnicodeCmap = null;
/* 168 */         this.uni2cid = null;
/*     */       }
/*     */   }
/*     */ 
/*     */   private void processUni2Byte()
/*     */     throws IOException
/*     */   {
/* 185 */     IntHashtable byte2uni = getByte2Uni();
/* 186 */     int[] e = byte2uni.toOrderedKeys();
/* 187 */     if (e.length == 0) {
/* 188 */       return;
/*     */     }
/* 190 */     this.cidbyte2uni = new char[256];
/* 191 */     for (int k = 0; k < e.length; k++) {
/* 192 */       int key = e[k];
/* 193 */       this.cidbyte2uni[key] = ((char)byte2uni.get(key));
/*     */     }
/* 195 */     if (this.toUnicodeCmap != null)
/*     */     {
/* 213 */       Map dm = this.toUnicodeCmap.createDirectMapping();
/* 214 */       for (Map.Entry kv : dm.entrySet()) {
/* 215 */         if (((Integer)kv.getKey()).intValue() < 256) {
/* 216 */           this.cidbyte2uni[((Integer)kv.getKey()).intValue()] = ((char)((Integer)kv.getValue()).intValue());
/*     */         }
/*     */       }
/*     */     }
/* 220 */     IntHashtable diffmap = getDiffmap();
/* 221 */     if (diffmap != null)
/*     */     {
/* 223 */       e = diffmap.toOrderedKeys();
/* 224 */       for (int k = 0; k < e.length; k++) {
/* 225 */         int n = diffmap.get(e[k]);
/* 226 */         if (n < 256)
/* 227 */           this.cidbyte2uni[n] = ((char)e[k]);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private int computeAverageWidth()
/*     */   {
/* 241 */     int count = 0;
/* 242 */     int total = 0;
/* 243 */     for (int i = 0; i < this.widths.length; i++) {
/* 244 */       if (this.widths[i] != 0) {
/* 245 */         total += this.widths[i];
/* 246 */         count++;
/*     */       }
/*     */     }
/* 249 */     return count != 0 ? total / count : 0;
/*     */   }
/*     */ 
/*     */   public int getWidth(int char1)
/*     */   {
/* 259 */     if (char1 == 32)
/* 260 */       return this.spaceWidth != 0 ? this.spaceWidth : this.defaultWidth;
/* 261 */     return super.getWidth(char1);
/*     */   }
/*     */ 
/*     */   private String decodeSingleCID(byte[] bytes, int offset, int len)
/*     */   {
/* 272 */     if (this.toUnicodeCmap != null) {
/* 273 */       if (offset + len > bytes.length)
/* 274 */         throw new ArrayIndexOutOfBoundsException(MessageLocalization.getComposedMessage("invalid.index.1", offset + len));
/* 275 */       String s = this.toUnicodeCmap.lookup(bytes, offset, len);
/* 276 */       if (s != null)
/* 277 */         return s;
/* 278 */       if ((len != 1) || (this.cidbyte2uni == null)) {
/* 279 */         return null;
/*     */       }
/*     */     }
/* 282 */     if (len == 1) {
/* 283 */       if (this.cidbyte2uni == null) {
/* 284 */         return "";
/*     */       }
/* 286 */       return new String(this.cidbyte2uni, 0xFF & bytes[offset], 1);
/*     */     }
/*     */ 
/* 289 */     throw new Error("Multi-byte glyphs not implemented yet");
/*     */   }
/*     */ 
/*     */   public String decode(byte[] cidbytes, int offset, int len)
/*     */   {
/* 301 */     StringBuilder sb = new StringBuilder();
/* 302 */     if ((this.toUnicodeCmap == null) && (this.byteCid != null)) {
/* 303 */       CMapSequence seq = new CMapSequence(cidbytes, offset, len);
/* 304 */       String cid = this.byteCid.decodeSequence(seq);
/* 305 */       for (int k = 0; k < cid.length(); k++) {
/* 306 */         int c = this.cidUni.lookup(cid.charAt(k));
/* 307 */         if (c > 0)
/* 308 */           sb.append(Utilities.convertFromUtf32(c));
/*     */       }
/*     */     }
/*     */     else {
/* 312 */       for (int i = offset; i < offset + len; i++) {
/* 313 */         String rslt = decodeSingleCID(cidbytes, i, 1);
/* 314 */         if ((rslt == null) && (i < offset + len - 1)) {
/* 315 */           rslt = decodeSingleCID(cidbytes, i, 2);
/* 316 */           i++;
/*     */         }
/* 318 */         if (rslt != null)
/* 319 */           sb.append(rslt);
/*     */       }
/*     */     }
/* 322 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public String encode(byte[] bytes, int offset, int len)
/*     */   {
/* 334 */     return decode(bytes, offset, len);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.CMapAwareDocumentFont
 * JD-Core Version:    0.6.2
 */