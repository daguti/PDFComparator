/*     */ package com.itextpdf.text.pdf.hyphenation;
/*     */ 
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import com.itextpdf.text.xml.simpleparser.SimpleXMLDocHandler;
/*     */ import com.itextpdf.text.xml.simpleparser.SimpleXMLParser;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public class SimplePatternParser
/*     */   implements SimpleXMLDocHandler, PatternConsumer
/*     */ {
/*     */   int currElement;
/*     */   PatternConsumer consumer;
/*     */   StringBuffer token;
/*     */   ArrayList<Object> exception;
/*     */   char hyphenChar;
/*     */   SimpleXMLParser parser;
/*     */   static final int ELEM_CLASSES = 1;
/*     */   static final int ELEM_EXCEPTIONS = 2;
/*     */   static final int ELEM_PATTERNS = 3;
/*     */   static final int ELEM_HYPHEN = 4;
/*     */ 
/*     */   public SimplePatternParser()
/*     */   {
/*  85 */     this.token = new StringBuffer();
/*  86 */     this.hyphenChar = '-';
/*     */   }
/*     */ 
/*     */   public void parse(InputStream stream, PatternConsumer consumer) {
/*  90 */     this.consumer = consumer;
/*     */     try {
/*  92 */       SimpleXMLParser.parse(this, stream);
/*     */     } catch (IOException e) {
/*  94 */       throw new ExceptionConverter(e);
/*     */     } finally {
/*     */       try {
/*  97 */         stream.close();
/*     */       } catch (Exception e) {
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected static String getPattern(String word) {
/* 104 */     StringBuffer pat = new StringBuffer();
/* 105 */     int len = word.length();
/* 106 */     for (int i = 0; i < len; i++) {
/* 107 */       if (!Character.isDigit(word.charAt(i))) {
/* 108 */         pat.append(word.charAt(i));
/*     */       }
/*     */     }
/* 111 */     return pat.toString();
/*     */   }
/*     */ 
/*     */   protected ArrayList<Object> normalizeException(ArrayList<Object> ex) {
/* 115 */     ArrayList res = new ArrayList();
/* 116 */     for (int i = 0; i < ex.size(); i++) {
/* 117 */       Object item = ex.get(i);
/* 118 */       if ((item instanceof String)) {
/* 119 */         String str = (String)item;
/* 120 */         StringBuffer buf = new StringBuffer();
/* 121 */         for (int j = 0; j < str.length(); j++) {
/* 122 */           char c = str.charAt(j);
/* 123 */           if (c != this.hyphenChar) {
/* 124 */             buf.append(c);
/*     */           } else {
/* 126 */             res.add(buf.toString());
/* 127 */             buf.setLength(0);
/* 128 */             char[] h = new char[1];
/* 129 */             h[0] = this.hyphenChar;
/*     */ 
/* 132 */             res.add(new Hyphen(new String(h), null, null));
/*     */           }
/*     */         }
/* 135 */         if (buf.length() > 0)
/* 136 */           res.add(buf.toString());
/*     */       }
/*     */       else {
/* 139 */         res.add(item);
/*     */       }
/*     */     }
/* 142 */     return res;
/*     */   }
/*     */ 
/*     */   protected String getExceptionWord(ArrayList<Object> ex) {
/* 146 */     StringBuffer res = new StringBuffer();
/* 147 */     for (int i = 0; i < ex.size(); i++) {
/* 148 */       Object item = ex.get(i);
/* 149 */       if ((item instanceof String)) {
/* 150 */         res.append((String)item);
/*     */       }
/* 152 */       else if (((Hyphen)item).noBreak != null) {
/* 153 */         res.append(((Hyphen)item).noBreak);
/*     */       }
/*     */     }
/*     */ 
/* 157 */     return res.toString();
/*     */   }
/*     */ 
/*     */   protected static String getInterletterValues(String pat) {
/* 161 */     StringBuffer il = new StringBuffer();
/* 162 */     String word = pat + "a";
/* 163 */     int len = word.length();
/* 164 */     for (int i = 0; i < len; i++) {
/* 165 */       char c = word.charAt(i);
/* 166 */       if (Character.isDigit(c)) {
/* 167 */         il.append(c);
/* 168 */         i++;
/*     */       } else {
/* 170 */         il.append('0');
/*     */       }
/*     */     }
/* 173 */     return il.toString();
/*     */   }
/*     */ 
/*     */   public void endDocument()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void endElement(String tag) {
/* 181 */     if (this.token.length() > 0) {
/* 182 */       String word = this.token.toString();
/* 183 */       switch (this.currElement) {
/*     */       case 1:
/* 185 */         this.consumer.addClass(word);
/* 186 */         break;
/*     */       case 2:
/* 188 */         this.exception.add(word);
/* 189 */         this.exception = normalizeException(this.exception);
/* 190 */         this.consumer.addException(getExceptionWord(this.exception), (ArrayList)this.exception.clone());
/*     */ 
/* 192 */         break;
/*     */       case 3:
/* 194 */         this.consumer.addPattern(getPattern(word), getInterletterValues(word));
/*     */ 
/* 196 */         break;
/*     */       case 4:
/*     */       }
/*     */ 
/* 201 */       if (this.currElement != 4) {
/* 202 */         this.token.setLength(0);
/*     */       }
/*     */     }
/* 205 */     if (this.currElement == 4)
/* 206 */       this.currElement = 2;
/*     */     else
/* 208 */       this.currElement = 0;
/*     */   }
/*     */ 
/*     */   public void startDocument()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void startElement(String tag, Map<String, String> h) {
/* 216 */     if (tag.equals("hyphen-char")) {
/* 217 */       String hh = (String)h.get("value");
/* 218 */       if ((hh != null) && (hh.length() == 1))
/* 219 */         this.hyphenChar = hh.charAt(0);
/*     */     }
/* 221 */     else if (tag.equals("classes")) {
/* 222 */       this.currElement = 1;
/* 223 */     } else if (tag.equals("patterns")) {
/* 224 */       this.currElement = 3;
/* 225 */     } else if (tag.equals("exceptions")) {
/* 226 */       this.currElement = 2;
/* 227 */       this.exception = new ArrayList();
/* 228 */     } else if (tag.equals("hyphen")) {
/* 229 */       if (this.token.length() > 0) {
/* 230 */         this.exception.add(this.token.toString());
/*     */       }
/* 232 */       this.exception.add(new Hyphen((String)h.get("pre"), (String)h.get("no"), (String)h.get("post")));
/*     */ 
/* 234 */       this.currElement = 4;
/*     */     }
/* 236 */     this.token.setLength(0);
/*     */   }
/*     */ 
/*     */   public void text(String str)
/*     */   {
/* 241 */     StringTokenizer tk = new StringTokenizer(str);
/* 242 */     while (tk.hasMoreTokens()) {
/* 243 */       String word = tk.nextToken();
/*     */ 
/* 245 */       switch (this.currElement) {
/*     */       case 1:
/* 247 */         this.consumer.addClass(word);
/* 248 */         break;
/*     */       case 2:
/* 250 */         this.exception.add(word);
/* 251 */         this.exception = normalizeException(this.exception);
/* 252 */         this.consumer.addException(getExceptionWord(this.exception), (ArrayList)this.exception.clone());
/*     */ 
/* 254 */         this.exception.clear();
/* 255 */         break;
/*     */       case 3:
/* 257 */         this.consumer.addPattern(getPattern(word), getInterletterValues(word));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addClass(String c)
/*     */   {
/* 266 */     System.out.println("class: " + c);
/*     */   }
/*     */ 
/*     */   public void addException(String w, ArrayList<Object> e) {
/* 270 */     System.out.println("exception: " + w + " : " + e.toString());
/*     */   }
/*     */ 
/*     */   public void addPattern(String p, String v) {
/* 274 */     System.out.println("pattern: " + p + " : " + v);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.hyphenation.SimplePatternParser
 * JD-Core Version:    0.6.2
 */