/*     */ package org.antlr.runtime;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class ANTLRStringStream
/*     */   implements CharStream
/*     */ {
/*     */   protected char[] data;
/*     */   protected int n;
/*  45 */   protected int p = 0;
/*     */ 
/*  48 */   protected int line = 1;
/*     */ 
/*  51 */   protected int charPositionInLine = 0;
/*     */ 
/*  54 */   protected int markDepth = 0;
/*     */   protected List markers;
/*     */   protected int lastMarker;
/*     */   public String name;
/*     */ 
/*     */   public ANTLRStringStream()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ANTLRStringStream(String input)
/*     */   {
/*  74 */     this();
/*  75 */     this.data = input.toCharArray();
/*  76 */     this.n = input.length();
/*     */   }
/*     */ 
/*     */   public ANTLRStringStream(char[] data, int numberOfActualCharsInArray)
/*     */   {
/*  81 */     this();
/*  82 */     this.data = data;
/*  83 */     this.n = numberOfActualCharsInArray;
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/*  91 */     this.p = 0;
/*  92 */     this.line = 1;
/*  93 */     this.charPositionInLine = 0;
/*  94 */     this.markDepth = 0;
/*     */   }
/*     */ 
/*     */   public void consume()
/*     */   {
/*  99 */     if (this.p < this.n) {
/* 100 */       this.charPositionInLine += 1;
/* 101 */       if (this.data[this.p] == '\n')
/*     */       {
/* 106 */         this.line += 1;
/* 107 */         this.charPositionInLine = 0;
/*     */       }
/* 109 */       this.p += 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   public int LA(int i)
/*     */   {
/* 115 */     if (i == 0) {
/* 116 */       return 0;
/*     */     }
/* 118 */     if (i < 0) {
/* 119 */       i++;
/* 120 */       if (this.p + i - 1 < 0) {
/* 121 */         return -1;
/*     */       }
/*     */     }
/*     */ 
/* 125 */     if (this.p + i - 1 >= this.n)
/*     */     {
/* 127 */       return -1;
/*     */     }
/*     */ 
/* 131 */     return this.data[(this.p + i - 1)];
/*     */   }
/*     */ 
/*     */   public int LT(int i) {
/* 135 */     return LA(i);
/*     */   }
/*     */ 
/*     */   public int index()
/*     */   {
/* 143 */     return this.p;
/*     */   }
/*     */ 
/*     */   public int size() {
/* 147 */     return this.n;
/*     */   }
/*     */ 
/*     */   public int mark() {
/* 151 */     if (this.markers == null) {
/* 152 */       this.markers = new ArrayList();
/* 153 */       this.markers.add(null);
/*     */     }
/* 155 */     this.markDepth += 1;
/* 156 */     CharStreamState state = null;
/* 157 */     if (this.markDepth >= this.markers.size()) {
/* 158 */       state = new CharStreamState();
/* 159 */       this.markers.add(state);
/*     */     }
/*     */     else {
/* 162 */       state = (CharStreamState)this.markers.get(this.markDepth);
/*     */     }
/* 164 */     state.p = this.p;
/* 165 */     state.line = this.line;
/* 166 */     state.charPositionInLine = this.charPositionInLine;
/* 167 */     this.lastMarker = this.markDepth;
/* 168 */     return this.markDepth;
/*     */   }
/*     */ 
/*     */   public void rewind(int m) {
/* 172 */     CharStreamState state = (CharStreamState)this.markers.get(m);
/*     */ 
/* 174 */     seek(state.p);
/* 175 */     this.line = state.line;
/* 176 */     this.charPositionInLine = state.charPositionInLine;
/* 177 */     release(m);
/*     */   }
/*     */ 
/*     */   public void rewind() {
/* 181 */     rewind(this.lastMarker);
/*     */   }
/*     */ 
/*     */   public void release(int marker)
/*     */   {
/* 186 */     this.markDepth = marker;
/*     */ 
/* 188 */     this.markDepth -= 1;
/*     */   }
/*     */ 
/*     */   public void seek(int index)
/*     */   {
/* 195 */     if (index <= this.p) {
/* 196 */       this.p = index;
/* 197 */       return;
/*     */     }
/*     */ 
/* 200 */     while (this.p < index)
/* 201 */       consume();
/*     */   }
/*     */ 
/*     */   public String substring(int start, int stop)
/*     */   {
/* 206 */     return new String(this.data, start, stop - start + 1);
/*     */   }
/*     */ 
/*     */   public int getLine() {
/* 210 */     return this.line;
/*     */   }
/*     */ 
/*     */   public int getCharPositionInLine() {
/* 214 */     return this.charPositionInLine;
/*     */   }
/*     */ 
/*     */   public void setLine(int line) {
/* 218 */     this.line = line;
/*     */   }
/*     */ 
/*     */   public void setCharPositionInLine(int pos) {
/* 222 */     this.charPositionInLine = pos;
/*     */   }
/*     */ 
/*     */   public String getSourceName() {
/* 226 */     return this.name;
/*     */   }
/*     */   public String toString() {
/* 229 */     return new String(this.data);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.ANTLRStringStream
 * JD-Core Version:    0.6.2
 */