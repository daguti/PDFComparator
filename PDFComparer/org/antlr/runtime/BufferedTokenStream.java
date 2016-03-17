/*     */ package org.antlr.runtime;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ public class BufferedTokenStream
/*     */   implements TokenStream
/*     */ {
/*     */   protected TokenSource tokenSource;
/*  57 */   protected List<Token> tokens = new ArrayList(100);
/*     */   protected int lastMarker;
/*  67 */   protected int p = -1;
/*     */ 
/*  69 */   protected int range = -1;
/*     */ 
/*     */   public BufferedTokenStream() {
/*     */   }
/*     */   public BufferedTokenStream(TokenSource tokenSource) {
/*  74 */     this.tokenSource = tokenSource;
/*     */   }
/*     */   public TokenSource getTokenSource() {
/*  77 */     return this.tokenSource;
/*     */   }
/*  79 */   public int index() { return this.p; } 
/*     */   public int range() {
/*  81 */     return this.range;
/*     */   }
/*     */   public int mark() {
/*  84 */     if (this.p == -1) setup();
/*  85 */     this.lastMarker = index();
/*  86 */     return this.lastMarker;
/*     */   }
/*     */ 
/*     */   public void release(int marker)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void rewind(int marker) {
/*  94 */     seek(marker);
/*     */   }
/*     */ 
/*     */   public void rewind() {
/*  98 */     seek(this.lastMarker);
/*     */   }
/*     */ 
/*     */   public void reset() {
/* 102 */     this.p = 0;
/* 103 */     this.lastMarker = 0;
/*     */   }
/*     */   public void seek(int index) {
/* 106 */     this.p = index;
/*     */   }
/* 108 */   public int size() { return this.tokens.size(); }
/*     */ 
/*     */ 
/*     */   public void consume()
/*     */   {
/* 118 */     if (this.p == -1) setup();
/* 119 */     this.p += 1;
/* 120 */     sync(this.p);
/*     */   }
/*     */ 
/*     */   protected void sync(int i)
/*     */   {
/* 125 */     int n = i - this.tokens.size() + 1;
/*     */ 
/* 127 */     if (n > 0) fetch(n);
/*     */   }
/*     */ 
/*     */   protected void fetch(int n)
/*     */   {
/* 132 */     for (int i = 1; i <= n; i++) {
/* 133 */       Token t = this.tokenSource.nextToken();
/* 134 */       t.setTokenIndex(this.tokens.size());
/*     */ 
/* 136 */       this.tokens.add(t);
/* 137 */       if (t.getType() == -1) break;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Token get(int i) {
/* 142 */     if ((i < 0) || (i >= this.tokens.size())) {
/* 143 */       throw new NoSuchElementException("token index " + i + " out of range 0.." + (this.tokens.size() - 1));
/*     */     }
/* 145 */     return (Token)this.tokens.get(i);
/*     */   }
/*     */ 
/*     */   public List get(int start, int stop)
/*     */   {
/* 150 */     if ((start < 0) || (stop < 0)) return null;
/* 151 */     if (this.p == -1) setup();
/* 152 */     List subset = new ArrayList();
/* 153 */     if (stop >= this.tokens.size()) stop = this.tokens.size() - 1;
/* 154 */     for (int i = start; i <= stop; i++) {
/* 155 */       Token t = (Token)this.tokens.get(i);
/* 156 */       if (t.getType() == -1) break;
/* 157 */       subset.add(t);
/*     */     }
/* 159 */     return subset;
/*     */   }
/*     */   public int LA(int i) {
/* 162 */     return LT(i).getType();
/*     */   }
/*     */   protected Token LB(int k) {
/* 165 */     if (this.p - k < 0) return null;
/* 166 */     return (Token)this.tokens.get(this.p - k);
/*     */   }
/*     */ 
/*     */   public Token LT(int k) {
/* 170 */     if (this.p == -1) setup();
/* 171 */     if (k == 0) return null;
/* 172 */     if (k < 0) return LB(-k);
/*     */ 
/* 174 */     int i = this.p + k - 1;
/* 175 */     sync(i);
/* 176 */     if (i >= this.tokens.size())
/*     */     {
/* 178 */       return (Token)this.tokens.get(this.tokens.size() - 1);
/*     */     }
/* 180 */     if (i > this.range) this.range = i;
/* 181 */     return (Token)this.tokens.get(i);
/*     */   }
/*     */   protected void setup() {
/* 184 */     sync(0); this.p = 0;
/*     */   }
/*     */ 
/*     */   public void setTokenSource(TokenSource tokenSource) {
/* 188 */     this.tokenSource = tokenSource;
/* 189 */     this.tokens.clear();
/* 190 */     this.p = -1;
/*     */   }
/*     */   public List getTokens() {
/* 193 */     return this.tokens;
/*     */   }
/*     */   public List getTokens(int start, int stop) {
/* 196 */     return getTokens(start, stop, (BitSet)null);
/*     */   }
/*     */ 
/*     */   public List getTokens(int start, int stop, BitSet types)
/*     */   {
/* 204 */     if (this.p == -1) setup();
/* 205 */     if (stop >= this.tokens.size()) stop = this.tokens.size() - 1;
/* 206 */     if (start < 0) start = 0;
/* 207 */     if (start > stop) return null;
/*     */ 
/* 210 */     List filteredTokens = new ArrayList();
/* 211 */     for (int i = start; i <= stop; i++) {
/* 212 */       Token t = (Token)this.tokens.get(i);
/* 213 */       if ((types == null) || (types.member(t.getType()))) {
/* 214 */         filteredTokens.add(t);
/*     */       }
/*     */     }
/* 217 */     if (filteredTokens.size() == 0) {
/* 218 */       filteredTokens = null;
/*     */     }
/* 220 */     return filteredTokens;
/*     */   }
/*     */ 
/*     */   public List getTokens(int start, int stop, List types) {
/* 224 */     return getTokens(start, stop, new BitSet(types));
/*     */   }
/*     */ 
/*     */   public List getTokens(int start, int stop, int ttype) {
/* 228 */     return getTokens(start, stop, BitSet.of(ttype));
/*     */   }
/*     */   public String getSourceName() {
/* 231 */     return this.tokenSource.getSourceName();
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 235 */     if (this.p == -1) setup();
/* 236 */     fill();
/* 237 */     return toString(0, this.tokens.size() - 1);
/*     */   }
/*     */ 
/*     */   public String toString(int start, int stop) {
/* 241 */     if ((start < 0) || (stop < 0)) return null;
/* 242 */     if (this.p == -1) setup();
/* 243 */     if (stop >= this.tokens.size()) stop = this.tokens.size() - 1;
/* 244 */     StringBuffer buf = new StringBuffer();
/* 245 */     for (int i = start; i <= stop; i++) {
/* 246 */       Token t = (Token)this.tokens.get(i);
/* 247 */       if (t.getType() == -1) break;
/* 248 */       buf.append(t.getText());
/*     */     }
/* 250 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public String toString(Token start, Token stop) {
/* 254 */     if ((start != null) && (stop != null)) {
/* 255 */       return toString(start.getTokenIndex(), stop.getTokenIndex());
/*     */     }
/* 257 */     return null;
/*     */   }
/*     */ 
/*     */   public void fill()
/*     */   {
/* 262 */     if (this.p == -1) setup();
/* 263 */     if (((Token)this.tokens.get(this.p)).getType() == -1) return;
/*     */ 
/* 265 */     int i = this.p + 1;
/* 266 */     sync(i);
/* 267 */     while (((Token)this.tokens.get(i)).getType() != -1) {
/* 268 */       i++;
/* 269 */       sync(i);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.BufferedTokenStream
 * JD-Core Version:    0.6.2
 */