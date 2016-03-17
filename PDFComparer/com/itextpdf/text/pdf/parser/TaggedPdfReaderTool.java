/*     */ package com.itextpdf.text.pdf.parser;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.pdf.PdfArray;
/*     */ import com.itextpdf.text.pdf.PdfDictionary;
/*     */ import com.itextpdf.text.pdf.PdfName;
/*     */ import com.itextpdf.text.pdf.PdfNumber;
/*     */ import com.itextpdf.text.pdf.PdfObject;
/*     */ import com.itextpdf.text.pdf.PdfReader;
/*     */ import com.itextpdf.text.xml.XMLUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class TaggedPdfReaderTool
/*     */ {
/*     */   protected PdfReader reader;
/*     */   protected PrintWriter out;
/*     */ 
/*     */   public void convertToXml(PdfReader reader, OutputStream os, String charset)
/*     */     throws IOException
/*     */   {
/*  82 */     this.reader = reader;
/*  83 */     OutputStreamWriter outs = new OutputStreamWriter(os, charset);
/*  84 */     this.out = new PrintWriter(outs);
/*     */ 
/*  86 */     PdfDictionary catalog = reader.getCatalog();
/*  87 */     PdfDictionary struct = catalog.getAsDict(PdfName.STRUCTTREEROOT);
/*  88 */     if (struct == null) {
/*  89 */       throw new IOException(MessageLocalization.getComposedMessage("no.structtreeroot.found", new Object[0]));
/*     */     }
/*  91 */     inspectChild(struct.getDirectObject(PdfName.K));
/*  92 */     this.out.flush();
/*  93 */     this.out.close();
/*     */   }
/*     */ 
/*     */   public void convertToXml(PdfReader reader, OutputStream os)
/*     */     throws IOException
/*     */   {
/* 107 */     convertToXml(reader, os, "UTF-8");
/*     */   }
/*     */ 
/*     */   public void inspectChild(PdfObject k)
/*     */     throws IOException
/*     */   {
/* 119 */     if (k == null)
/* 120 */       return;
/* 121 */     if ((k instanceof PdfArray))
/* 122 */       inspectChildArray((PdfArray)k);
/* 123 */     else if ((k instanceof PdfDictionary))
/* 124 */       inspectChildDictionary((PdfDictionary)k);
/*     */   }
/*     */ 
/*     */   public void inspectChildArray(PdfArray k)
/*     */     throws IOException
/*     */   {
/* 135 */     if (k == null)
/* 136 */       return;
/* 137 */     for (int i = 0; i < k.size(); i++)
/* 138 */       inspectChild(k.getDirectObject(i));
/*     */   }
/*     */ 
/*     */   public void inspectChildDictionary(PdfDictionary k)
/*     */     throws IOException
/*     */   {
/* 150 */     inspectChildDictionary(k, false);
/*     */   }
/*     */ 
/*     */   public void inspectChildDictionary(PdfDictionary k, boolean inspectAttributes)
/*     */     throws IOException
/*     */   {
/* 162 */     if (k == null)
/* 163 */       return;
/* 164 */     PdfName s = k.getAsName(PdfName.S);
/* 165 */     if (s != null) {
/* 166 */       String tagN = PdfName.decodeName(s.toString());
/* 167 */       String tag = fixTagName(tagN);
/* 168 */       this.out.print("<");
/* 169 */       this.out.print(tag);
/*     */       PdfDictionary a;
/* 170 */       if (inspectAttributes) {
/* 171 */         a = k.getAsDict(PdfName.A);
/* 172 */         if (a != null) {
/* 173 */           Set keys = a.getKeys();
/* 174 */           for (PdfName key : keys) {
/* 175 */             this.out.print(' ');
/* 176 */             PdfObject value = a.get(key);
/* 177 */             value = PdfReader.getPdfObject(value);
/* 178 */             this.out.print(xmlName(key));
/* 179 */             this.out.print("=\"");
/* 180 */             this.out.print(value.toString());
/* 181 */             this.out.print("\"");
/*     */           }
/*     */         }
/*     */       }
/* 185 */       this.out.print(">");
/* 186 */       PdfObject alt = k.get(PdfName.ALT);
/* 187 */       if ((alt != null) && (alt.toString() != null)) {
/* 188 */         this.out.print("<alt><![CDATA[");
/* 189 */         this.out.print(alt.toString().replaceAll("[\\000]*", ""));
/* 190 */         this.out.print("]]></alt>");
/*     */       }
/* 192 */       PdfDictionary dict = k.getAsDict(PdfName.PG);
/* 193 */       if (dict != null)
/* 194 */         parseTag(tagN, k.getDirectObject(PdfName.K), dict);
/* 195 */       inspectChild(k.getDirectObject(PdfName.K));
/* 196 */       this.out.print("</");
/* 197 */       this.out.print(tag);
/* 198 */       this.out.println(">");
/*     */     } else {
/* 200 */       inspectChild(k.getDirectObject(PdfName.K));
/*     */     }
/*     */   }
/*     */ 
/* 204 */   protected String xmlName(PdfName name) { String xmlName = name.toString().replaceFirst("/", "");
/* 205 */     xmlName = Character.toLowerCase(xmlName.charAt(0)) + xmlName.substring(1);
/*     */ 
/* 207 */     return xmlName; }
/*     */ 
/*     */   private static String fixTagName(String tag)
/*     */   {
/* 211 */     StringBuilder sb = new StringBuilder();
/* 212 */     for (int k = 0; k < tag.length(); k++) {
/* 213 */       char c = tag.charAt(k);
/* 214 */       boolean nameStart = (c == ':') || ((c >= 'A') && (c <= 'Z')) || (c == '_') || ((c >= 'a') && (c <= 'z')) || ((c >= 'À') && (c <= 'Ö')) || ((c >= 'Ø') && (c <= 'ö')) || ((c >= 'ø') && (c <= '˿')) || ((c >= 'Ͱ') && (c <= 'ͽ')) || ((c >= 'Ϳ') && (c <= '῿')) || ((c >= '‌') && (c <= '‍')) || ((c >= '⁰') && (c <= '↏')) || ((c >= 'Ⰰ') && (c <= '⿯')) || ((c >= '、') && (c <= 55295)) || ((c >= 63744) && (c <= 64975)) || ((c >= 65008) && (c <= 65533));
/*     */ 
/* 230 */       boolean nameMiddle = (c == '-') || (c == '.') || ((c >= '0') && (c <= '9')) || (c == '·') || ((c >= '̀') && (c <= 'ͯ')) || ((c >= '‿') && (c <= '⁀')) || (nameStart);
/*     */ 
/* 238 */       if (k == 0) {
/* 239 */         if (!nameStart) {
/* 240 */           c = '_';
/*     */         }
/*     */       }
/* 243 */       else if (!nameMiddle) {
/* 244 */         c = '-';
/*     */       }
/* 246 */       sb.append(c);
/*     */     }
/* 248 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public void parseTag(String tag, PdfObject object, PdfDictionary page)
/*     */     throws IOException
/*     */   {
/* 265 */     if ((object instanceof PdfNumber)) {
/* 266 */       PdfNumber mcid = (PdfNumber)object;
/* 267 */       RenderFilter filter = new MarkedContentRenderFilter(mcid.intValue());
/* 268 */       TextExtractionStrategy strategy = new SimpleTextExtractionStrategy();
/* 269 */       FilteredTextRenderListener listener = new FilteredTextRenderListener(strategy, new RenderFilter[] { filter });
/*     */ 
/* 271 */       PdfContentStreamProcessor processor = new PdfContentStreamProcessor(listener);
/*     */ 
/* 273 */       processor.processContent(PdfReader.getPageContent(page), page.getAsDict(PdfName.RESOURCES));
/*     */ 
/* 275 */       this.out.print(XMLUtil.escapeXML(listener.getResultantText(), true));
/*     */     }
/* 279 */     else if ((object instanceof PdfArray)) {
/* 280 */       PdfArray arr = (PdfArray)object;
/* 281 */       int n = arr.size();
/* 282 */       for (int i = 0; i < n; i++) {
/* 283 */         parseTag(tag, arr.getPdfObject(i), page);
/* 284 */         if (i < n - 1) {
/* 285 */           this.out.println();
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/* 290 */     else if ((object instanceof PdfDictionary)) {
/* 291 */       PdfDictionary mcr = (PdfDictionary)object;
/* 292 */       parseTag(tag, mcr.getDirectObject(PdfName.MCID), mcr.getAsDict(PdfName.PG));
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.parser.TaggedPdfReaderTool
 * JD-Core Version:    0.6.2
 */