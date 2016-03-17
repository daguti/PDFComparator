/*     */ package com.itextpdf.text.html.simpleparser;
/*     */ 
/*     */ import com.itextpdf.text.BaseColor;
/*     */ import com.itextpdf.text.Chunk;
/*     */ import com.itextpdf.text.DocListener;
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import com.itextpdf.text.Font;
/*     */ import com.itextpdf.text.FontFactory;
/*     */ import com.itextpdf.text.FontProvider;
/*     */ import com.itextpdf.text.Image;
/*     */ import com.itextpdf.text.List;
/*     */ import com.itextpdf.text.ListItem;
/*     */ import com.itextpdf.text.Paragraph;
/*     */ import com.itextpdf.text.html.HtmlUtilities;
/*     */ import com.itextpdf.text.pdf.HyphenationAuto;
/*     */ import com.itextpdf.text.pdf.HyphenationEvent;
/*     */ import com.itextpdf.text.pdf.draw.LineSeparator;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ @Deprecated
/*     */ public class ElementFactory
/*     */ {
/*  87 */   private FontProvider provider = FontFactory.getFontImp();
/*     */ 
/*     */   public void setFontProvider(FontProvider provider)
/*     */   {
/* 101 */     this.provider = provider;
/*     */   }
/*     */ 
/*     */   public FontProvider getFontProvider()
/*     */   {
/* 110 */     return this.provider;
/*     */   }
/*     */ 
/*     */   public Font getFont(ChainedProperties chain)
/*     */   {
/* 122 */     String face = chain.getProperty("face");
/*     */ 
/* 128 */     if ((face == null) || (face.trim().length() == 0)) {
/* 129 */       face = chain.getProperty("font-family");
/*     */     }
/*     */ 
/* 133 */     if (face != null) {
/* 134 */       StringTokenizer tok = new StringTokenizer(face, ",");
/* 135 */       while (tok.hasMoreTokens()) {
/* 136 */         face = tok.nextToken().trim();
/* 137 */         if (face.startsWith("\""))
/* 138 */           face = face.substring(1);
/* 139 */         if (face.endsWith("\""))
/* 140 */           face = face.substring(0, face.length() - 1);
/* 141 */         if (this.provider.isRegistered(face)) {
/* 142 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 147 */     String encoding = chain.getProperty("encoding");
/* 148 */     if (encoding == null) {
/* 149 */       encoding = "Cp1252";
/*     */     }
/*     */ 
/* 154 */     String value = chain.getProperty("size");
/* 155 */     float size = 12.0F;
/* 156 */     if (value != null) {
/* 157 */       size = Float.parseFloat(value);
/*     */     }
/*     */ 
/* 160 */     int style = 0;
/*     */ 
/* 163 */     String decoration = chain.getProperty("text-decoration");
/* 164 */     if ((decoration != null) && (decoration.trim().length() != 0)) {
/* 165 */       if ("underline".equals(decoration))
/* 166 */         style |= 4;
/* 167 */       else if ("line-through".equals(decoration)) {
/* 168 */         style |= 8;
/*     */       }
/*     */     }
/*     */ 
/* 172 */     if (chain.hasProperty("i")) {
/* 173 */       style |= 2;
/*     */     }
/* 175 */     if (chain.hasProperty("b")) {
/* 176 */       style |= 1;
/*     */     }
/* 178 */     if (chain.hasProperty("u")) {
/* 179 */       style |= 4;
/*     */     }
/* 181 */     if (chain.hasProperty("s")) {
/* 182 */       style |= 8;
/*     */     }
/*     */ 
/* 185 */     BaseColor color = HtmlUtilities.decodeColor(chain.getProperty("color"));
/*     */ 
/* 188 */     return this.provider.getFont(face, encoding, true, size, style, color);
/*     */   }
/*     */ 
/*     */   public Chunk createChunk(String content, ChainedProperties chain)
/*     */   {
/* 199 */     Font font = getFont(chain);
/* 200 */     Chunk ck = new Chunk(content, font);
/* 201 */     if (chain.hasProperty("sub"))
/* 202 */       ck.setTextRise(-font.getSize() / 2.0F);
/* 203 */     else if (chain.hasProperty("sup"))
/* 204 */       ck.setTextRise(font.getSize() / 2.0F);
/* 205 */     ck.setHyphenation(getHyphenation(chain));
/* 206 */     return ck;
/*     */   }
/*     */ 
/*     */   public Paragraph createParagraph(ChainedProperties chain)
/*     */   {
/* 216 */     Paragraph paragraph = new Paragraph();
/* 217 */     updateElement(paragraph, chain);
/* 218 */     return paragraph;
/*     */   }
/*     */ 
/*     */   public ListItem createListItem(ChainedProperties chain)
/*     */   {
/* 228 */     ListItem item = new ListItem();
/* 229 */     updateElement(item, chain);
/* 230 */     return item;
/*     */   }
/*     */ 
/*     */   protected void updateElement(Paragraph paragraph, ChainedProperties chain)
/*     */   {
/* 241 */     String value = chain.getProperty("align");
/* 242 */     paragraph.setAlignment(HtmlUtilities.alignmentValue(value));
/*     */ 
/* 244 */     paragraph.setHyphenation(getHyphenation(chain));
/*     */ 
/* 246 */     setParagraphLeading(paragraph, chain.getProperty("leading"));
/*     */ 
/* 248 */     value = chain.getProperty("after");
/* 249 */     if (value != null)
/*     */       try {
/* 251 */         paragraph.setSpacingBefore(Float.parseFloat(value));
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*     */       }
/* 256 */     value = chain.getProperty("after");
/* 257 */     if (value != null)
/*     */       try {
/* 259 */         paragraph.setSpacingAfter(Float.parseFloat(value));
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*     */       }
/* 264 */     value = chain.getProperty("extraparaspace");
/* 265 */     if (value != null)
/*     */       try {
/* 267 */         paragraph.setExtraParagraphSpace(Float.parseFloat(value));
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*     */       }
/* 272 */     value = chain.getProperty("indent");
/* 273 */     if (value != null)
/*     */       try {
/* 275 */         paragraph.setIndentationLeft(Float.parseFloat(value));
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*     */       }
/*     */   }
/*     */ 
/*     */   protected static void setParagraphLeading(Paragraph paragraph, String leading)
/*     */   {
/* 288 */     if (leading == null) {
/* 289 */       paragraph.setLeading(0.0F, 1.5F);
/* 290 */       return;
/*     */     }
/*     */     try {
/* 293 */       StringTokenizer tk = new StringTokenizer(leading, " ,");
/*     */ 
/* 295 */       String v = tk.nextToken();
/* 296 */       float v1 = Float.parseFloat(v);
/* 297 */       if (!tk.hasMoreTokens()) {
/* 298 */         paragraph.setLeading(v1, 0.0F);
/* 299 */         return;
/*     */       }
/*     */ 
/* 302 */       v = tk.nextToken();
/* 303 */       float v2 = Float.parseFloat(v);
/* 304 */       paragraph.setLeading(v1, v2);
/*     */     }
/*     */     catch (Exception e) {
/* 307 */       paragraph.setLeading(0.0F, 1.5F);
/*     */     }
/*     */   }
/*     */ 
/*     */   public HyphenationEvent getHyphenation(ChainedProperties chain)
/*     */   {
/* 320 */     String value = chain.getProperty("hyphenation");
/*     */ 
/* 322 */     if ((value == null) || (value.length() == 0)) {
/* 323 */       return null;
/*     */     }
/*     */ 
/* 326 */     int pos = value.indexOf('_');
/* 327 */     if (pos == -1) {
/* 328 */       return new HyphenationAuto(value, null, 2, 2);
/*     */     }
/*     */ 
/* 331 */     String lang = value.substring(0, pos);
/* 332 */     String country = value.substring(pos + 1);
/*     */ 
/* 334 */     pos = country.indexOf(',');
/* 335 */     if (pos == -1) {
/* 336 */       return new HyphenationAuto(lang, country, 2, 2);
/*     */     }
/*     */ 
/* 340 */     int rightMin = 2;
/* 341 */     value = country.substring(pos + 1);
/* 342 */     country = country.substring(0, pos);
/* 343 */     pos = value.indexOf(',');
/*     */     int leftMin;
/*     */     int leftMin;
/* 344 */     if (pos == -1) {
/* 345 */       leftMin = Integer.parseInt(value);
/*     */     } else {
/* 347 */       leftMin = Integer.parseInt(value.substring(0, pos));
/* 348 */       rightMin = Integer.parseInt(value.substring(pos + 1));
/*     */     }
/* 350 */     return new HyphenationAuto(lang, country, leftMin, rightMin);
/*     */   }
/*     */ 
/*     */   public LineSeparator createLineSeparator(Map<String, String> attrs, float offset)
/*     */   {
/* 362 */     float lineWidth = 1.0F;
/* 363 */     String size = (String)attrs.get("size");
/* 364 */     if (size != null) {
/* 365 */       float tmpSize = HtmlUtilities.parseLength(size, 12.0F);
/* 366 */       if (tmpSize > 0.0F) {
/* 367 */         lineWidth = tmpSize;
/*     */       }
/*     */     }
/* 370 */     String width = (String)attrs.get("width");
/* 371 */     float percentage = 100.0F;
/* 372 */     if (width != null) {
/* 373 */       float tmpWidth = HtmlUtilities.parseLength(width, 12.0F);
/* 374 */       if (tmpWidth > 0.0F) percentage = tmpWidth;
/* 375 */       if (!width.endsWith("%")) {
/* 376 */         percentage = 100.0F;
/*     */       }
/*     */     }
/* 379 */     BaseColor lineColor = null;
/*     */ 
/* 381 */     int align = HtmlUtilities.alignmentValue((String)attrs.get("align"));
/* 382 */     return new LineSeparator(lineWidth, percentage, lineColor, align, offset);
/*     */   }
/*     */ 
/*     */   public Image createImage(String src, Map<String, String> attrs, ChainedProperties chain, DocListener document, ImageProvider img_provider, HashMap<String, Image> img_store, String img_baseurl)
/*     */     throws DocumentException, IOException
/*     */   {
/* 405 */     Image img = null;
/*     */ 
/* 407 */     if (img_provider != null) {
/* 408 */       img = img_provider.getImage(src, attrs, chain, document);
/*     */     }
/* 410 */     if ((img == null) && (img_store != null)) {
/* 411 */       Image tim = (Image)img_store.get(src);
/* 412 */       if (tim != null)
/* 413 */         img = Image.getInstance(tim);
/*     */     }
/* 415 */     if (img != null) {
/* 416 */       return img;
/*     */     }
/*     */ 
/* 419 */     if ((!src.startsWith("http")) && (img_baseurl != null)) {
/* 420 */       src = img_baseurl + src;
/*     */     }
/* 422 */     else if ((img == null) && (!src.startsWith("http"))) {
/* 423 */       String path = chain.getProperty("image_path");
/* 424 */       if (path == null)
/* 425 */         path = "";
/* 426 */       src = new File(path, src).getPath();
/*     */     }
/* 428 */     img = Image.getInstance(src);
/* 429 */     if (img == null) {
/* 430 */       return null;
/*     */     }
/* 432 */     float actualFontSize = HtmlUtilities.parseLength(chain.getProperty("size"), 12.0F);
/*     */ 
/* 435 */     if (actualFontSize <= 0.0F)
/* 436 */       actualFontSize = 12.0F;
/* 437 */     String width = (String)attrs.get("width");
/* 438 */     float widthInPoints = HtmlUtilities.parseLength(width, actualFontSize);
/* 439 */     String height = (String)attrs.get("height");
/* 440 */     float heightInPoints = HtmlUtilities.parseLength(height, actualFontSize);
/* 441 */     if ((widthInPoints > 0.0F) && (heightInPoints > 0.0F)) {
/* 442 */       img.scaleAbsolute(widthInPoints, heightInPoints);
/* 443 */     } else if (widthInPoints > 0.0F) {
/* 444 */       heightInPoints = img.getHeight() * widthInPoints / img.getWidth();
/*     */ 
/* 446 */       img.scaleAbsolute(widthInPoints, heightInPoints);
/* 447 */     } else if (heightInPoints > 0.0F) {
/* 448 */       widthInPoints = img.getWidth() * heightInPoints / img.getHeight();
/*     */ 
/* 450 */       img.scaleAbsolute(widthInPoints, heightInPoints);
/*     */     }
/*     */ 
/* 453 */     String before = chain.getProperty("before");
/* 454 */     if (before != null)
/* 455 */       img.setSpacingBefore(Float.parseFloat(before));
/* 456 */     String after = chain.getProperty("after");
/* 457 */     if (after != null)
/* 458 */       img.setSpacingAfter(Float.parseFloat(after));
/* 459 */     img.setWidthPercentage(0.0F);
/* 460 */     return img;
/*     */   }
/*     */ 
/*     */   public List createList(String tag, ChainedProperties chain)
/*     */   {
/*     */     List list;
/* 470 */     if ("ul".equalsIgnoreCase(tag)) {
/* 471 */       List list = new List(false);
/* 472 */       list.setListSymbol("• ");
/*     */     }
/*     */     else {
/* 475 */       list = new List(true);
/*     */     }
/*     */     try {
/* 478 */       list.setIndentationLeft(new Float(chain.getProperty("indent")).floatValue());
/*     */     } catch (Exception e) {
/* 480 */       list.setAutoindent(true);
/*     */     }
/* 482 */     return list;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.html.simpleparser.ElementFactory
 * JD-Core Version:    0.6.2
 */