/*     */ package org.antlr.stringtemplate;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class AutoIndentWriter
/*     */   implements StringTemplateWriter
/*     */ {
/*  57 */   protected List indents = new ArrayList();
/*     */ 
/*  62 */   protected int[] anchors = new int[10];
/*  63 */   protected int anchors_sp = -1;
/*     */   protected String newline;
/*  68 */   protected Writer out = null;
/*  69 */   protected boolean atStartOfLine = true;
/*     */ 
/*  76 */   protected int charPosition = 0;
/*  77 */   protected int lineWidth = -1;
/*     */ 
/*  79 */   protected int charPositionOfStartOfExpr = 0;
/*     */ 
/*     */   public AutoIndentWriter(Writer out, String newline) {
/*  82 */     this.out = out;
/*  83 */     this.indents.add(null);
/*  84 */     this.newline = newline;
/*     */   }
/*     */ 
/*     */   public AutoIndentWriter(Writer out) {
/*  88 */     this(out, System.getProperty("line.separator"));
/*     */   }
/*     */ 
/*     */   public void setLineWidth(int lineWidth) {
/*  92 */     this.lineWidth = lineWidth;
/*     */   }
/*     */ 
/*     */   public void pushIndentation(String indent)
/*     */   {
/* 105 */     int lastAnchor = 0;
/* 106 */     int indentWidth = getIndentationWidth();
/*     */ 
/* 108 */     if ((this.anchors_sp >= 0) && (this.anchors[this.anchors_sp] > indentWidth)) {
/* 109 */       lastAnchor = this.anchors[this.anchors_sp];
/* 110 */       StringBuffer buf = getIndentString(lastAnchor - indentWidth);
/* 111 */       if (indent != null) buf.append(indent);
/* 112 */       this.indents.add(buf.toString());
/* 113 */       return;
/*     */     }
/* 115 */     this.indents.add(indent);
/*     */   }
/*     */ 
/*     */   public String popIndentation() {
/* 119 */     return (String)this.indents.remove(this.indents.size() - 1);
/*     */   }
/*     */ 
/*     */   public void pushAnchorPoint() {
/* 123 */     if (this.anchors_sp + 1 >= this.anchors.length) {
/* 124 */       int[] a = new int[this.anchors.length * 2];
/* 125 */       System.arraycopy(this.anchors, 0, a, 0, this.anchors.length - 1);
/* 126 */       this.anchors = a;
/*     */     }
/* 128 */     this.anchors_sp += 1;
/* 129 */     this.anchors[this.anchors_sp] = this.charPosition;
/*     */   }
/*     */ 
/*     */   public void popAnchorPoint() {
/* 133 */     this.anchors_sp -= 1;
/*     */   }
/*     */ 
/*     */   public int getIndentationWidth() {
/* 137 */     int n = 0;
/* 138 */     for (int i = 0; i < this.indents.size(); i++) {
/* 139 */       String ind = (String)this.indents.get(i);
/* 140 */       if (ind != null) {
/* 141 */         n += ind.length();
/*     */       }
/*     */     }
/* 144 */     return n;
/*     */   }
/*     */ 
/*     */   public int write(String str) throws IOException
/*     */   {
/* 149 */     int n = 0;
/* 150 */     for (int i = 0; i < str.length(); i++) {
/* 151 */       char c = str.charAt(i);
/*     */ 
/* 153 */       if ((c == '\r') || (c == '\n')) {
/* 154 */         this.atStartOfLine = true;
/* 155 */         this.charPosition = -1;
/* 156 */         n += this.newline.length();
/* 157 */         this.out.write(this.newline);
/* 158 */         this.charPosition += n;
/*     */ 
/* 160 */         if ((c == '\r') && (i + 1 < str.length()) && (str.charAt(i + 1) == '\n')) {
/* 161 */           i++;
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 167 */         if (this.atStartOfLine) {
/* 168 */           n += indent();
/* 169 */           this.atStartOfLine = false;
/*     */         }
/* 171 */         n++;
/* 172 */         this.out.write(c);
/* 173 */         this.charPosition += 1;
/*     */       }
/*     */     }
/* 175 */     return n;
/*     */   }
/*     */ 
/*     */   public int writeSeparator(String str) throws IOException {
/* 179 */     return write(str);
/*     */   }
/*     */ 
/*     */   public int write(String str, String wrap)
/*     */     throws IOException
/*     */   {
/* 189 */     int n = writeWrapSeparator(wrap);
/* 190 */     return n + write(str);
/*     */   }
/*     */ 
/*     */   public int writeWrapSeparator(String wrap) throws IOException {
/* 194 */     int n = 0;
/*     */ 
/* 197 */     if ((this.lineWidth != -1) && (wrap != null) && (!this.atStartOfLine) && (this.charPosition >= this.lineWidth))
/*     */     {
/* 204 */       for (int i = 0; i < wrap.length(); i++) {
/* 205 */         char c = wrap.charAt(i);
/* 206 */         if (c == '\n') {
/* 207 */           n++;
/* 208 */           this.out.write(c);
/* 209 */           this.charPosition = 0;
/* 210 */           n += indent();
/*     */         }
/*     */         else
/*     */         {
/* 214 */           n++;
/* 215 */           this.out.write(c);
/* 216 */           this.charPosition += 1;
/*     */         }
/*     */       }
/*     */     }
/* 220 */     return n;
/*     */   }
/*     */ 
/*     */   public int indent() throws IOException {
/* 224 */     int n = 0;
/* 225 */     for (int i = 0; i < this.indents.size(); i++) {
/* 226 */       String ind = (String)this.indents.get(i);
/* 227 */       if (ind != null) {
/* 228 */         n += ind.length();
/* 229 */         this.out.write(ind);
/*     */       }
/*     */     }
/* 232 */     this.charPosition += n;
/* 233 */     return n;
/*     */   }
/*     */ 
/*     */   public int indent(int spaces) throws IOException {
/* 237 */     for (int i = 1; i <= spaces; i++) {
/* 238 */       this.out.write(32);
/*     */     }
/* 240 */     this.charPosition += spaces;
/* 241 */     return spaces;
/*     */   }
/*     */ 
/*     */   protected StringBuffer getIndentString(int spaces) {
/* 245 */     StringBuffer buf = new StringBuffer();
/* 246 */     for (int i = 1; i <= spaces; i++) {
/* 247 */       buf.append(' ');
/*     */     }
/* 249 */     return buf;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.stringtemplate.AutoIndentWriter
 * JD-Core Version:    0.6.2
 */