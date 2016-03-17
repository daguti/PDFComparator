/*     */ package org.antlr.runtime;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class LegacyCommonTokenStream
/*     */   implements TokenStream
/*     */ {
/*     */   protected TokenSource tokenSource;
/*     */   protected List tokens;
/*     */   protected Map channelOverrideMap;
/*     */   protected Set discardSet;
/*  54 */   protected int channel = 0;
/*     */ 
/*  57 */   protected boolean discardOffChannelTokens = false;
/*     */   protected int lastMarker;
/*  62 */   protected int range = -1;
/*     */ 
/*  67 */   protected int p = -1;
/*     */ 
/*     */   public LegacyCommonTokenStream() {
/*  70 */     this.tokens = new ArrayList(500);
/*     */   }
/*     */ 
/*     */   public LegacyCommonTokenStream(TokenSource tokenSource) {
/*  74 */     this();
/*  75 */     this.tokenSource = tokenSource;
/*     */   }
/*     */ 
/*     */   public LegacyCommonTokenStream(TokenSource tokenSource, int channel) {
/*  79 */     this(tokenSource);
/*  80 */     this.channel = channel;
/*     */   }
/*     */ 
/*     */   public void setTokenSource(TokenSource tokenSource)
/*     */   {
/*  85 */     this.tokenSource = tokenSource;
/*  86 */     this.tokens.clear();
/*  87 */     this.p = -1;
/*  88 */     this.channel = 0;
/*     */   }
/*     */ 
/*     */   protected void fillBuffer()
/*     */   {
/*  96 */     int index = 0;
/*  97 */     Token t = this.tokenSource.nextToken();
/*  98 */     while ((t != null) && (t.getType() != -1)) {
/*  99 */       boolean discard = false;
/*     */ 
/* 101 */       if (this.channelOverrideMap != null) {
/* 102 */         Integer channelI = (Integer)this.channelOverrideMap.get(new Integer(t.getType()));
/*     */ 
/* 104 */         if (channelI != null) {
/* 105 */           t.setChannel(channelI.intValue());
/*     */         }
/*     */       }
/* 108 */       if ((this.discardSet != null) && (this.discardSet.contains(new Integer(t.getType()))))
/*     */       {
/* 111 */         discard = true;
/*     */       }
/* 113 */       else if ((this.discardOffChannelTokens) && (t.getChannel() != this.channel)) {
/* 114 */         discard = true;
/*     */       }
/* 116 */       if (!discard) {
/* 117 */         t.setTokenIndex(index);
/* 118 */         this.tokens.add(t);
/* 119 */         index++;
/*     */       }
/* 121 */       t = this.tokenSource.nextToken();
/*     */     }
/*     */ 
/* 124 */     this.p = 0;
/* 125 */     this.p = skipOffTokenChannels(this.p);
/*     */   }
/*     */ 
/*     */   public void consume()
/*     */   {
/* 136 */     if (this.p < this.tokens.size()) {
/* 137 */       this.p += 1;
/* 138 */       this.p = skipOffTokenChannels(this.p);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected int skipOffTokenChannels(int i)
/*     */   {
/* 146 */     int n = this.tokens.size();
/* 147 */     while ((i < n) && (((Token)this.tokens.get(i)).getChannel() != this.channel)) {
/* 148 */       i++;
/*     */     }
/* 150 */     return i;
/*     */   }
/*     */ 
/*     */   protected int skipOffTokenChannelsReverse(int i) {
/* 154 */     while ((i >= 0) && (((Token)this.tokens.get(i)).getChannel() != this.channel)) {
/* 155 */       i--;
/*     */     }
/* 157 */     return i;
/*     */   }
/*     */ 
/*     */   public void setTokenTypeChannel(int ttype, int channel)
/*     */   {
/* 167 */     if (this.channelOverrideMap == null) {
/* 168 */       this.channelOverrideMap = new HashMap();
/*     */     }
/* 170 */     this.channelOverrideMap.put(new Integer(ttype), new Integer(channel));
/*     */   }
/*     */ 
/*     */   public void discardTokenType(int ttype) {
/* 174 */     if (this.discardSet == null) {
/* 175 */       this.discardSet = new HashSet();
/*     */     }
/* 177 */     this.discardSet.add(new Integer(ttype));
/*     */   }
/*     */ 
/*     */   public void discardOffChannelTokens(boolean discardOffChannelTokens) {
/* 181 */     this.discardOffChannelTokens = discardOffChannelTokens;
/*     */   }
/*     */ 
/*     */   public List getTokens() {
/* 185 */     if (this.p == -1) {
/* 186 */       fillBuffer();
/*     */     }
/* 188 */     return this.tokens;
/*     */   }
/*     */ 
/*     */   public List getTokens(int start, int stop) {
/* 192 */     return getTokens(start, stop, (BitSet)null);
/*     */   }
/*     */ 
/*     */   public List getTokens(int start, int stop, BitSet types)
/*     */   {
/* 200 */     if (this.p == -1) {
/* 201 */       fillBuffer();
/*     */     }
/* 203 */     if (stop >= this.tokens.size()) {
/* 204 */       stop = this.tokens.size() - 1;
/*     */     }
/* 206 */     if (start < 0) {
/* 207 */       start = 0;
/*     */     }
/* 209 */     if (start > stop) {
/* 210 */       return null;
/*     */     }
/*     */ 
/* 214 */     List filteredTokens = new ArrayList();
/* 215 */     for (int i = start; i <= stop; i++) {
/* 216 */       Token t = (Token)this.tokens.get(i);
/* 217 */       if ((types == null) || (types.member(t.getType()))) {
/* 218 */         filteredTokens.add(t);
/*     */       }
/*     */     }
/* 221 */     if (filteredTokens.size() == 0) {
/* 222 */       filteredTokens = null;
/*     */     }
/* 224 */     return filteredTokens;
/*     */   }
/*     */ 
/*     */   public List getTokens(int start, int stop, List types) {
/* 228 */     return getTokens(start, stop, new BitSet(types));
/*     */   }
/*     */ 
/*     */   public List getTokens(int start, int stop, int ttype) {
/* 232 */     return getTokens(start, stop, BitSet.of(ttype));
/*     */   }
/*     */ 
/*     */   public Token LT(int k)
/*     */   {
/* 239 */     if (this.p == -1) {
/* 240 */       fillBuffer();
/*     */     }
/* 242 */     if (k == 0) {
/* 243 */       return null;
/*     */     }
/* 245 */     if (k < 0) {
/* 246 */       return LB(-k);
/*     */     }
/*     */ 
/* 249 */     if (this.p + k - 1 >= this.tokens.size()) {
/* 250 */       return (Token)this.tokens.get(this.tokens.size() - 1);
/*     */     }
/*     */ 
/* 253 */     int i = this.p;
/* 254 */     int n = 1;
/*     */ 
/* 256 */     while (n < k)
/*     */     {
/* 258 */       i = skipOffTokenChannels(i + 1);
/* 259 */       n++;
/*     */     }
/* 261 */     if (i >= this.tokens.size()) {
/* 262 */       return (Token)this.tokens.get(this.tokens.size() - 1);
/*     */     }
/*     */ 
/* 265 */     if (i > this.range) this.range = i;
/* 266 */     return (Token)this.tokens.get(i);
/*     */   }
/*     */ 
/*     */   protected Token LB(int k)
/*     */   {
/* 272 */     if (this.p == -1) {
/* 273 */       fillBuffer();
/*     */     }
/* 275 */     if (k == 0) {
/* 276 */       return null;
/*     */     }
/* 278 */     if (this.p - k < 0) {
/* 279 */       return null;
/*     */     }
/*     */ 
/* 282 */     int i = this.p;
/* 283 */     int n = 1;
/*     */ 
/* 285 */     while (n <= k)
/*     */     {
/* 287 */       i = skipOffTokenChannelsReverse(i - 1);
/* 288 */       n++;
/*     */     }
/* 290 */     if (i < 0) {
/* 291 */       return null;
/*     */     }
/* 293 */     return (Token)this.tokens.get(i);
/*     */   }
/*     */ 
/*     */   public Token get(int i)
/*     */   {
/* 300 */     return (Token)this.tokens.get(i);
/*     */   }
/*     */ 
/*     */   public List get(int start, int stop)
/*     */   {
/* 305 */     if (this.p == -1) fillBuffer();
/* 306 */     if ((start < 0) || (stop < 0)) return null;
/* 307 */     return this.tokens.subList(start, stop);
/*     */   }
/*     */ 
/*     */   public int LA(int i) {
/* 311 */     return LT(i).getType();
/*     */   }
/*     */ 
/*     */   public int mark() {
/* 315 */     if (this.p == -1) {
/* 316 */       fillBuffer();
/*     */     }
/* 318 */     this.lastMarker = index();
/* 319 */     return this.lastMarker;
/*     */   }
/*     */ 
/*     */   public void release(int marker)
/*     */   {
/*     */   }
/*     */ 
/*     */   public int size() {
/* 327 */     return this.tokens.size();
/*     */   }
/*     */ 
/*     */   public int index() {
/* 331 */     return this.p;
/*     */   }
/*     */ 
/*     */   public int range() {
/* 335 */     return this.range;
/*     */   }
/*     */ 
/*     */   public void rewind(int marker) {
/* 339 */     seek(marker);
/*     */   }
/*     */ 
/*     */   public void rewind() {
/* 343 */     seek(this.lastMarker);
/*     */   }
/*     */ 
/*     */   public void reset() {
/* 347 */     this.p = 0;
/* 348 */     this.lastMarker = 0;
/*     */   }
/*     */ 
/*     */   public void seek(int index) {
/* 352 */     this.p = index;
/*     */   }
/*     */ 
/*     */   public TokenSource getTokenSource() {
/* 356 */     return this.tokenSource;
/*     */   }
/*     */ 
/*     */   public String getSourceName() {
/* 360 */     return getTokenSource().getSourceName();
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 364 */     if (this.p == -1) {
/* 365 */       fillBuffer();
/*     */     }
/* 367 */     return toString(0, this.tokens.size() - 1);
/*     */   }
/*     */ 
/*     */   public String toString(int start, int stop) {
/* 371 */     if ((start < 0) || (stop < 0)) {
/* 372 */       return null;
/*     */     }
/* 374 */     if (this.p == -1) {
/* 375 */       fillBuffer();
/*     */     }
/* 377 */     if (stop >= this.tokens.size()) {
/* 378 */       stop = this.tokens.size() - 1;
/*     */     }
/* 380 */     StringBuffer buf = new StringBuffer();
/* 381 */     for (int i = start; i <= stop; i++) {
/* 382 */       Token t = (Token)this.tokens.get(i);
/* 383 */       buf.append(t.getText());
/*     */     }
/* 385 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public String toString(Token start, Token stop) {
/* 389 */     if ((start != null) && (stop != null)) {
/* 390 */       return toString(start.getTokenIndex(), stop.getTokenIndex());
/*     */     }
/* 392 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.LegacyCommonTokenStream
 * JD-Core Version:    0.6.2
 */