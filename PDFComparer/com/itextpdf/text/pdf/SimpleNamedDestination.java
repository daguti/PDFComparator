/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.xml.XMLUtil;
/*     */ import com.itextpdf.text.xml.simpleparser.IanaEncodings;
/*     */ import com.itextpdf.text.xml.simpleparser.SimpleXMLDocHandler;
/*     */ import com.itextpdf.text.xml.simpleparser.SimpleXMLParser;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Reader;
/*     */ import java.io.Writer;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public final class SimpleNamedDestination
/*     */   implements SimpleXMLDocHandler
/*     */ {
/*     */   private HashMap<String, String> xmlNames;
/*     */   private HashMap<String, String> xmlLast;
/*     */ 
/*     */   public static HashMap<String, String> getNamedDestination(PdfReader reader, boolean fromNames)
/*     */   {
/*  77 */     IntHashtable pages = new IntHashtable();
/*  78 */     int numPages = reader.getNumberOfPages();
/*  79 */     for (int k = 1; k <= numPages; k++)
/*  80 */       pages.put(reader.getPageOrigRef(k).getNumber(), k);
/*  81 */     HashMap names = fromNames ? reader.getNamedDestinationFromNames() : reader.getNamedDestinationFromStrings();
/*  82 */     HashMap n2 = new HashMap(names.size());
/*  83 */     for (Map.Entry entry : names.entrySet()) {
/*  84 */       PdfArray arr = (PdfArray)entry.getValue();
/*  85 */       StringBuffer s = new StringBuffer();
/*     */       try {
/*  87 */         s.append(pages.get(arr.getAsIndirectObject(0).getNumber()));
/*  88 */         s.append(' ').append(arr.getPdfObject(1).toString().substring(1));
/*  89 */         for (int k = 2; k < arr.size(); k++)
/*  90 */           s.append(' ').append(arr.getPdfObject(k).toString());
/*  91 */         n2.put(entry.getKey(), s.toString());
/*     */       }
/*     */       catch (Exception e) {
/*     */       }
/*     */     }
/*  96 */     return n2;
/*     */   }
/*     */ 
/*     */   public static void exportToXML(HashMap<String, String> names, OutputStream out, String encoding, boolean onlyASCII)
/*     */     throws IOException
/*     */   {
/* 119 */     String jenc = IanaEncodings.getJavaEncoding(encoding);
/* 120 */     Writer wrt = new BufferedWriter(new OutputStreamWriter(out, jenc));
/* 121 */     exportToXML(names, wrt, encoding, onlyASCII);
/*     */   }
/*     */ 
/*     */   public static void exportToXML(HashMap<String, String> names, Writer wrt, String encoding, boolean onlyASCII)
/*     */     throws IOException
/*     */   {
/* 135 */     wrt.write("<?xml version=\"1.0\" encoding=\"");
/* 136 */     wrt.write(XMLUtil.escapeXML(encoding, onlyASCII));
/* 137 */     wrt.write("\"?>\n<Destination>\n");
/* 138 */     for (Map.Entry entry : names.entrySet()) {
/* 139 */       String key = (String)entry.getKey();
/* 140 */       String value = (String)entry.getValue();
/* 141 */       wrt.write("  <Name Page=\"");
/* 142 */       wrt.write(XMLUtil.escapeXML(value, onlyASCII));
/* 143 */       wrt.write("\">");
/* 144 */       wrt.write(XMLUtil.escapeXML(escapeBinaryString(key), onlyASCII));
/* 145 */       wrt.write("</Name>\n");
/*     */     }
/* 147 */     wrt.write("</Destination>\n");
/* 148 */     wrt.flush();
/*     */   }
/*     */ 
/*     */   public static HashMap<String, String> importFromXML(InputStream in)
/*     */     throws IOException
/*     */   {
/* 158 */     SimpleNamedDestination names = new SimpleNamedDestination();
/* 159 */     SimpleXMLParser.parse(names, in);
/* 160 */     return names.xmlNames;
/*     */   }
/*     */ 
/*     */   public static HashMap<String, String> importFromXML(Reader in)
/*     */     throws IOException
/*     */   {
/* 170 */     SimpleNamedDestination names = new SimpleNamedDestination();
/* 171 */     SimpleXMLParser.parse(names, in);
/* 172 */     return names.xmlNames;
/*     */   }
/*     */ 
/*     */   static PdfArray createDestinationArray(String value, PdfWriter writer) {
/* 176 */     PdfArray ar = new PdfArray();
/* 177 */     StringTokenizer tk = new StringTokenizer(value);
/* 178 */     int n = Integer.parseInt(tk.nextToken());
/* 179 */     ar.add(writer.getPageReference(n));
/* 180 */     if (!tk.hasMoreTokens()) {
/* 181 */       ar.add(PdfName.XYZ);
/* 182 */       ar.add(new float[] { 0.0F, 10000.0F, 0.0F });
/*     */     }
/*     */     else {
/* 185 */       String fn = tk.nextToken();
/* 186 */       if (fn.startsWith("/"))
/* 187 */         fn = fn.substring(1);
/* 188 */       ar.add(new PdfName(fn));
/* 189 */       for (int k = 0; (k < 4) && (tk.hasMoreTokens()); k++) {
/* 190 */         fn = tk.nextToken();
/* 191 */         if (fn.equals("null"))
/* 192 */           ar.add(PdfNull.PDFNULL);
/*     */         else
/* 194 */           ar.add(new PdfNumber(fn));
/*     */       }
/*     */     }
/* 197 */     return ar;
/*     */   }
/*     */ 
/*     */   public static PdfDictionary outputNamedDestinationAsNames(HashMap<String, String> names, PdfWriter writer) {
/* 201 */     PdfDictionary dic = new PdfDictionary();
/* 202 */     for (Map.Entry entry : names.entrySet()) {
/*     */       try {
/* 204 */         String key = (String)entry.getKey();
/* 205 */         String value = (String)entry.getValue();
/* 206 */         PdfArray ar = createDestinationArray(value, writer);
/* 207 */         PdfName kn = new PdfName(key);
/* 208 */         dic.put(kn, ar);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*     */       }
/*     */     }
/* 214 */     return dic;
/*     */   }
/*     */ 
/*     */   public static PdfDictionary outputNamedDestinationAsStrings(HashMap<String, String> names, PdfWriter writer) throws IOException {
/* 218 */     HashMap n2 = new HashMap(names.size());
/* 219 */     for (Map.Entry entry : names.entrySet())
/*     */       try {
/* 221 */         String value = (String)entry.getValue();
/* 222 */         PdfArray ar = createDestinationArray(value, writer);
/* 223 */         n2.put(entry.getKey(), writer.addToBody(ar).getIndirectReference());
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*     */       }
/* 228 */     return PdfNameTree.writeTree(n2, writer);
/*     */   }
/*     */ 
/*     */   public static String escapeBinaryString(String s) {
/* 232 */     StringBuffer buf = new StringBuffer();
/* 233 */     char[] cc = s.toCharArray();
/* 234 */     int len = cc.length;
/* 235 */     for (int k = 0; k < len; k++) {
/* 236 */       char c = cc[k];
/* 237 */       if (c < ' ') {
/* 238 */         buf.append('\\');
/* 239 */         String octal = "00" + Integer.toOctalString(c);
/* 240 */         buf.append(octal.substring(octal.length() - 3));
/*     */       }
/* 242 */       else if (c == '\\') {
/* 243 */         buf.append("\\\\");
/*     */       } else {
/* 245 */         buf.append(c);
/*     */       }
/*     */     }
/* 247 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public static String unEscapeBinaryString(String s) {
/* 251 */     StringBuffer buf = new StringBuffer();
/* 252 */     char[] cc = s.toCharArray();
/* 253 */     int len = cc.length;
/* 254 */     for (int k = 0; k < len; k++) {
/* 255 */       char c = cc[k];
/* 256 */       if (c == '\\') {
/* 257 */         k++; if (k >= len) {
/* 258 */           buf.append('\\');
/* 259 */           break;
/*     */         }
/* 261 */         c = cc[k];
/* 262 */         if ((c >= '0') && (c <= '7')) {
/* 263 */           int n = c - '0';
/* 264 */           k++;
/* 265 */           for (int j = 0; (j < 2) && (k < len); j++) {
/* 266 */             c = cc[k];
/* 267 */             if ((c < '0') || (c > '7')) break;
/* 268 */             k++;
/* 269 */             n = n * 8 + c - 48;
/*     */           }
/*     */ 
/* 275 */           k--;
/* 276 */           buf.append((char)n);
/*     */         }
/*     */         else {
/* 279 */           buf.append(c);
/*     */         }
/*     */       } else {
/* 282 */         buf.append(c);
/*     */       }
/*     */     }
/* 284 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public void endDocument() {
/*     */   }
/*     */ 
/*     */   public void endElement(String tag) {
/* 291 */     if (tag.equals("Destination")) {
/* 292 */       if ((this.xmlLast == null) && (this.xmlNames != null)) {
/* 293 */         return;
/*     */       }
/* 295 */       throw new RuntimeException(MessageLocalization.getComposedMessage("destination.end.tag.out.of.place", new Object[0]));
/*     */     }
/* 297 */     if (!tag.equals("Name"))
/* 298 */       throw new RuntimeException(MessageLocalization.getComposedMessage("invalid.end.tag.1", new Object[] { tag }));
/* 299 */     if ((this.xmlLast == null) || (this.xmlNames == null))
/* 300 */       throw new RuntimeException(MessageLocalization.getComposedMessage("name.end.tag.out.of.place", new Object[0]));
/* 301 */     if (!this.xmlLast.containsKey("Page"))
/* 302 */       throw new RuntimeException(MessageLocalization.getComposedMessage("page.attribute.missing", new Object[0]));
/* 303 */     this.xmlNames.put(unEscapeBinaryString((String)this.xmlLast.get("Name")), this.xmlLast.get("Page"));
/* 304 */     this.xmlLast = null;
/*     */   }
/*     */ 
/*     */   public void startDocument() {
/*     */   }
/*     */ 
/*     */   public void startElement(String tag, Map<String, String> h) {
/* 311 */     if (this.xmlNames == null) {
/* 312 */       if (tag.equals("Destination")) {
/* 313 */         this.xmlNames = new HashMap();
/* 314 */         return;
/*     */       }
/*     */ 
/* 317 */       throw new RuntimeException(MessageLocalization.getComposedMessage("root.element.is.not.destination", new Object[0]));
/*     */     }
/* 319 */     if (!tag.equals("Name"))
/* 320 */       throw new RuntimeException(MessageLocalization.getComposedMessage("tag.1.not.allowed", new Object[] { tag }));
/* 321 */     if (this.xmlLast != null)
/* 322 */       throw new RuntimeException(MessageLocalization.getComposedMessage("nested.tags.are.not.allowed", new Object[0]));
/* 323 */     this.xmlLast = new HashMap(h);
/* 324 */     this.xmlLast.put("Name", "");
/*     */   }
/*     */ 
/*     */   public void text(String str) {
/* 328 */     if (this.xmlLast == null)
/* 329 */       return;
/* 330 */     String name = (String)this.xmlLast.get("Name");
/* 331 */     name = name + str;
/* 332 */     this.xmlLast.put("Name", name);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.SimpleNamedDestination
 * JD-Core Version:    0.6.2
 */