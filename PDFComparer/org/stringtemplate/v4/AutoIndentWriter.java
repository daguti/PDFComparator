/*     */ package org.stringtemplate.v4;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class AutoIndentWriter
/*     */   implements STWriter
/*     */ {
/*  57 */   public List<String> indents = new ArrayList();
/*     */ 
/*  62 */   public int[] anchors = new int[10];
/*  63 */   public int anchors_sp = -1;
/*     */   public String newline;
/*  68 */   public Writer out = null;
/*  69 */   public boolean atStartOfLine = true;
/*     */ 
/*  76 */   public int charPosition = 0;
/*     */ 
/*  79 */   public int charIndex = 0;
/*     */ 
/*  81 */   public int lineWidth = -1;
/*     */ 
/*     */   public AutoIndentWriter(Writer out, String newline) {
/*  84 */     this.out = out;
/*  85 */     this.indents.add(null);
/*  86 */     this.newline = newline;
/*     */   }
/*     */ 
/*     */   public AutoIndentWriter(Writer out) {
/*  90 */     this(out, System.getProperty("line.separator"));
/*     */   }
/*     */ 
/*     */   public void setLineWidth(int lineWidth) {
/*  94 */     this.lineWidth = lineWidth;
/*     */   }
/*     */ 
/*     */   public void pushIndentation(String indent) {
/*  98 */     this.indents.add(indent);
/*     */   }
/*     */ 
/*     */   public String popIndentation() {
/* 102 */     return (String)this.indents.remove(this.indents.size() - 1);
/*     */   }
/*     */ 
/*     */   public void pushAnchorPoint() {
/* 106 */     if (this.anchors_sp + 1 >= this.anchors.length) {
/* 107 */       int[] a = new int[this.anchors.length * 2];
/* 108 */       System.arraycopy(this.anchors, 0, a, 0, this.anchors.length - 1);
/* 109 */       this.anchors = a;
/*     */     }
/* 111 */     this.anchors_sp += 1;
/* 112 */     this.anchors[this.anchors_sp] = this.charPosition;
/*     */   }
/*     */ 
/*     */   public void popAnchorPoint() {
/* 116 */     this.anchors_sp -= 1;
/*     */   }
/*     */   public int index() {
/* 119 */     return this.charIndex;
/*     */   }
/*     */ 
/*     */   public int write(String str) throws IOException {
/* 123 */     int n = 0;
/* 124 */     int nll = this.newline.length();
/* 125 */     int sl = str.length();
/* 126 */     for (int i = 0; i < sl; i++) {
/* 127 */       char c = str.charAt(i);
/*     */ 
/* 129 */       if (c != '\r')
/* 130 */         if (c == '\n') {
/* 131 */           this.atStartOfLine = true;
/* 132 */           this.charPosition = (-nll);
/* 133 */           this.out.write(this.newline);
/* 134 */           n += nll;
/* 135 */           this.charIndex += nll;
/* 136 */           this.charPosition += n;
/*     */         }
/*     */         else
/*     */         {
/* 141 */           if (this.atStartOfLine) {
/* 142 */             n += indent();
/* 143 */             this.atStartOfLine = false;
/*     */           }
/* 145 */           n++;
/* 146 */           this.out.write(c);
/* 147 */           this.charPosition += 1;
/* 148 */           this.charIndex += 1;
/*     */         }
/*     */     }
/* 150 */     return n;
/*     */   }
/*     */ 
/*     */   public int writeSeparator(String str) throws IOException {
/* 154 */     return write(str);
/*     */   }
/*     */ 
/*     */   public int write(String str, String wrap)
/*     */     throws IOException
/*     */   {
/* 164 */     int n = writeWrap(wrap);
/* 165 */     return n + write(str);
/*     */   }
/*     */ 
/*     */   public int writeWrap(String wrap) throws IOException {
/* 169 */     int n = 0;
/*     */ 
/* 172 */     if ((this.lineWidth != -1) && (wrap != null) && (!this.atStartOfLine) && (this.charPosition >= this.lineWidth))
/*     */     {
/* 179 */       for (int i = 0; i < wrap.length(); i++) {
/* 180 */         char c = wrap.charAt(i);
/* 181 */         if (c != '\r')
/*     */         {
/* 183 */           if (c == '\n') {
/* 184 */             this.out.write(this.newline);
/* 185 */             n += this.newline.length();
/* 186 */             this.charPosition = 0;
/* 187 */             this.charIndex += this.newline.length();
/* 188 */             n += indent();
/*     */           }
/*     */           else
/*     */           {
/* 192 */             n++;
/* 193 */             this.out.write(c);
/* 194 */             this.charPosition += 1;
/* 195 */             this.charIndex += 1;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 199 */     return n;
/*     */   }
/*     */ 
/*     */   public int indent() throws IOException {
/* 203 */     int n = 0;
/* 204 */     for (String ind : this.indents) {
/* 205 */       if (ind != null) {
/* 206 */         n += ind.length();
/* 207 */         this.out.write(ind);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 213 */     int indentWidth = n;
/* 214 */     if ((this.anchors_sp >= 0) && (this.anchors[this.anchors_sp] > indentWidth)) {
/* 215 */       int remainder = this.anchors[this.anchors_sp] - indentWidth;
/* 216 */       for (int i = 1; i <= remainder; i++) this.out.write(32);
/* 217 */       n += remainder;
/*     */     }
/*     */ 
/* 220 */     this.charPosition += n;
/* 221 */     this.charIndex += n;
/* 222 */     return n;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.AutoIndentWriter
 * JD-Core Version:    0.6.2
 */