/*     */ package org.antlr.runtime;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class TokenRewriteStream extends CommonTokenStream
/*     */ {
/*     */   public static final String DEFAULT_PROGRAM_NAME = "default";
/*     */   public static final int PROGRAM_INIT_SIZE = 100;
/*     */   public static final int MIN_TOKEN_INDEX = 0;
/* 158 */   protected Map programs = null;
/*     */ 
/* 161 */   protected Map lastRewriteTokenIndexes = null;
/*     */ 
/*     */   public TokenRewriteStream() {
/* 164 */     init();
/*     */   }
/*     */ 
/*     */   protected void init() {
/* 168 */     this.programs = new HashMap();
/* 169 */     this.programs.put("default", new ArrayList(100));
/* 170 */     this.lastRewriteTokenIndexes = new HashMap();
/*     */   }
/*     */ 
/*     */   public TokenRewriteStream(TokenSource tokenSource) {
/* 174 */     super(tokenSource);
/* 175 */     init();
/*     */   }
/*     */ 
/*     */   public TokenRewriteStream(TokenSource tokenSource, int channel) {
/* 179 */     super(tokenSource, channel);
/* 180 */     init();
/*     */   }
/*     */ 
/*     */   public void rollback(int instructionIndex) {
/* 184 */     rollback("default", instructionIndex);
/*     */   }
/*     */ 
/*     */   public void rollback(String programName, int instructionIndex)
/*     */   {
/* 192 */     List is = (List)this.programs.get(programName);
/* 193 */     if (is != null)
/* 194 */       this.programs.put(programName, is.subList(0, instructionIndex));
/*     */   }
/*     */ 
/*     */   public void deleteProgram()
/*     */   {
/* 199 */     deleteProgram("default");
/*     */   }
/*     */ 
/*     */   public void deleteProgram(String programName)
/*     */   {
/* 204 */     rollback(programName, 0);
/*     */   }
/*     */ 
/*     */   public void insertAfter(Token t, Object text) {
/* 208 */     insertAfter("default", t, text);
/*     */   }
/*     */ 
/*     */   public void insertAfter(int index, Object text) {
/* 212 */     insertAfter("default", index, text);
/*     */   }
/*     */ 
/*     */   public void insertAfter(String programName, Token t, Object text) {
/* 216 */     insertAfter(programName, t.getTokenIndex(), text);
/*     */   }
/*     */ 
/*     */   public void insertAfter(String programName, int index, Object text)
/*     */   {
/* 221 */     insertBefore(programName, index + 1, text);
/*     */   }
/*     */ 
/*     */   public void insertBefore(Token t, Object text)
/*     */   {
/* 226 */     insertBefore("default", t, text);
/*     */   }
/*     */ 
/*     */   public void insertBefore(int index, Object text) {
/* 230 */     insertBefore("default", index, text);
/*     */   }
/*     */ 
/*     */   public void insertBefore(String programName, Token t, Object text) {
/* 234 */     insertBefore(programName, t.getTokenIndex(), text);
/*     */   }
/*     */ 
/*     */   public void insertBefore(String programName, int index, Object text)
/*     */   {
/* 239 */     RewriteOperation op = new InsertBeforeOp(index, text);
/* 240 */     List rewrites = getProgram(programName);
/* 241 */     op.instructionIndex = rewrites.size();
/* 242 */     rewrites.add(op);
/*     */   }
/*     */ 
/*     */   public void replace(int index, Object text) {
/* 246 */     replace("default", index, index, text);
/*     */   }
/*     */ 
/*     */   public void replace(int from, int to, Object text) {
/* 250 */     replace("default", from, to, text);
/*     */   }
/*     */ 
/*     */   public void replace(Token indexT, Object text) {
/* 254 */     replace("default", indexT, indexT, text);
/*     */   }
/*     */ 
/*     */   public void replace(Token from, Token to, Object text) {
/* 258 */     replace("default", from, to, text);
/*     */   }
/*     */ 
/*     */   public void replace(String programName, int from, int to, Object text) {
/* 262 */     if ((from > to) || (from < 0) || (to < 0) || (to >= this.tokens.size())) {
/* 263 */       throw new IllegalArgumentException("replace: range invalid: " + from + ".." + to + "(size=" + this.tokens.size() + ")");
/*     */     }
/* 265 */     RewriteOperation op = new ReplaceOp(from, to, text);
/* 266 */     List rewrites = getProgram(programName);
/* 267 */     op.instructionIndex = rewrites.size();
/* 268 */     rewrites.add(op);
/*     */   }
/*     */ 
/*     */   public void replace(String programName, Token from, Token to, Object text) {
/* 272 */     replace(programName, from.getTokenIndex(), to.getTokenIndex(), text);
/*     */   }
/*     */ 
/*     */   public void delete(int index)
/*     */   {
/* 279 */     delete("default", index, index);
/*     */   }
/*     */ 
/*     */   public void delete(int from, int to) {
/* 283 */     delete("default", from, to);
/*     */   }
/*     */ 
/*     */   public void delete(Token indexT) {
/* 287 */     delete("default", indexT, indexT);
/*     */   }
/*     */ 
/*     */   public void delete(Token from, Token to) {
/* 291 */     delete("default", from, to);
/*     */   }
/*     */ 
/*     */   public void delete(String programName, int from, int to) {
/* 295 */     replace(programName, from, to, null);
/*     */   }
/*     */ 
/*     */   public void delete(String programName, Token from, Token to) {
/* 299 */     replace(programName, from, to, null);
/*     */   }
/*     */ 
/*     */   public int getLastRewriteTokenIndex() {
/* 303 */     return getLastRewriteTokenIndex("default");
/*     */   }
/*     */ 
/*     */   protected int getLastRewriteTokenIndex(String programName) {
/* 307 */     Integer I = (Integer)this.lastRewriteTokenIndexes.get(programName);
/* 308 */     if (I == null) {
/* 309 */       return -1;
/*     */     }
/* 311 */     return I.intValue();
/*     */   }
/*     */ 
/*     */   protected void setLastRewriteTokenIndex(String programName, int i) {
/* 315 */     this.lastRewriteTokenIndexes.put(programName, new Integer(i));
/*     */   }
/*     */ 
/*     */   protected List getProgram(String name) {
/* 319 */     List is = (List)this.programs.get(name);
/* 320 */     if (is == null) {
/* 321 */       is = initializeProgram(name);
/*     */     }
/* 323 */     return is;
/*     */   }
/*     */ 
/*     */   private List initializeProgram(String name) {
/* 327 */     List is = new ArrayList(100);
/* 328 */     this.programs.put(name, is);
/* 329 */     return is;
/*     */   }
/*     */ 
/*     */   public String toOriginalString() {
/* 333 */     fill();
/* 334 */     return toOriginalString(0, size() - 1);
/*     */   }
/*     */ 
/*     */   public String toOriginalString(int start, int end) {
/* 338 */     StringBuffer buf = new StringBuffer();
/* 339 */     for (int i = start; (i >= 0) && (i <= end) && (i < this.tokens.size()); i++) {
/* 340 */       if (get(i).getType() != -1) buf.append(get(i).getText());
/*     */     }
/* 342 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 346 */     fill();
/* 347 */     return toString(0, size() - 1);
/*     */   }
/*     */ 
/*     */   public String toString(String programName) {
/* 351 */     fill();
/* 352 */     return toString(programName, 0, size() - 1);
/*     */   }
/*     */ 
/*     */   public String toString(int start, int end) {
/* 356 */     return toString("default", start, end);
/*     */   }
/*     */ 
/*     */   public String toString(String programName, int start, int end) {
/* 360 */     List rewrites = (List)this.programs.get(programName);
/*     */ 
/* 363 */     if (end > this.tokens.size() - 1) end = this.tokens.size() - 1;
/* 364 */     if (start < 0) start = 0;
/*     */ 
/* 366 */     if ((rewrites == null) || (rewrites.size() == 0)) {
/* 367 */       return toOriginalString(start, end);
/*     */     }
/* 369 */     StringBuffer buf = new StringBuffer();
/*     */ 
/* 372 */     Map indexToOp = reduceToSingleOperationPerIndex(rewrites);
/*     */ 
/* 375 */     int i = start;
/* 376 */     while ((i <= end) && (i < this.tokens.size())) {
/* 377 */       RewriteOperation op = (RewriteOperation)indexToOp.get(new Integer(i));
/* 378 */       indexToOp.remove(new Integer(i));
/* 379 */       Token t = (Token)this.tokens.get(i);
/* 380 */       if (op == null)
/*     */       {
/* 382 */         if (t.getType() != -1) buf.append(t.getText());
/* 383 */         i++;
/*     */       }
/*     */       else {
/* 386 */         i = op.execute(buf);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 393 */     if (end == this.tokens.size() - 1)
/*     */     {
/* 396 */       Iterator it = indexToOp.values().iterator();
/* 397 */       while (it.hasNext()) {
/* 398 */         RewriteOperation op = (RewriteOperation)it.next();
/* 399 */         if (op.index >= this.tokens.size() - 1) buf.append(op.text);
/*     */       }
/*     */     }
/* 402 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   protected Map reduceToSingleOperationPerIndex(List rewrites)
/*     */   {
/* 454 */     for (int i = 0; i < rewrites.size(); i++) {
/* 455 */       RewriteOperation op = (RewriteOperation)rewrites.get(i);
/* 456 */       if ((op != null) && 
/* 457 */         ((op instanceof ReplaceOp))) {
/* 458 */         ReplaceOp rop = (ReplaceOp)rewrites.get(i);
/*     */ 
/* 460 */         List inserts = getKindOfOps(rewrites, InsertBeforeOp.class, i);
/* 461 */         for (int j = 0; j < inserts.size(); j++) {
/* 462 */           InsertBeforeOp iop = (InsertBeforeOp)inserts.get(j);
/* 463 */           if ((iop.index >= rop.index) && (iop.index <= rop.lastIndex))
/*     */           {
/* 465 */             rewrites.set(iop.instructionIndex, null);
/*     */           }
/*     */         }
/*     */ 
/* 469 */         List prevReplaces = getKindOfOps(rewrites, ReplaceOp.class, i);
/* 470 */         for (int j = 0; j < prevReplaces.size(); j++) {
/* 471 */           ReplaceOp prevRop = (ReplaceOp)prevReplaces.get(j);
/* 472 */           if ((prevRop.index >= rop.index) && (prevRop.lastIndex <= rop.lastIndex))
/*     */           {
/* 474 */             rewrites.set(prevRop.instructionIndex, null);
/*     */           }
/*     */           else
/*     */           {
/* 478 */             boolean disjoint = (prevRop.lastIndex < rop.index) || (prevRop.index > rop.lastIndex);
/*     */ 
/* 480 */             boolean same = (prevRop.index == rop.index) && (prevRop.lastIndex == rop.lastIndex);
/*     */ 
/* 482 */             if ((!disjoint) && (!same)) {
/* 483 */               throw new IllegalArgumentException("replace op boundaries of " + rop + " overlap with previous " + prevRop);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 490 */     for (int i = 0; i < rewrites.size(); i++) {
/* 491 */       RewriteOperation op = (RewriteOperation)rewrites.get(i);
/* 492 */       if ((op != null) && 
/* 493 */         ((op instanceof InsertBeforeOp))) {
/* 494 */         InsertBeforeOp iop = (InsertBeforeOp)rewrites.get(i);
/*     */ 
/* 496 */         List prevInserts = getKindOfOps(rewrites, InsertBeforeOp.class, i);
/* 497 */         for (int j = 0; j < prevInserts.size(); j++) {
/* 498 */           InsertBeforeOp prevIop = (InsertBeforeOp)prevInserts.get(j);
/* 499 */           if (prevIop.index == iop.index)
/*     */           {
/* 502 */             iop.text = catOpText(iop.text, prevIop.text);
/*     */ 
/* 504 */             rewrites.set(prevIop.instructionIndex, null);
/*     */           }
/*     */         }
/*     */ 
/* 508 */         List prevReplaces = getKindOfOps(rewrites, ReplaceOp.class, i);
/* 509 */         for (int j = 0; j < prevReplaces.size(); j++) {
/* 510 */           ReplaceOp rop = (ReplaceOp)prevReplaces.get(j);
/* 511 */           if (iop.index == rop.index) {
/* 512 */             rop.text = catOpText(iop.text, rop.text);
/* 513 */             rewrites.set(i, null);
/*     */           }
/* 516 */           else if ((iop.index >= rop.index) && (iop.index <= rop.lastIndex)) {
/* 517 */             throw new IllegalArgumentException("insert op " + iop + " within boundaries of previous " + rop);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 523 */     Map m = new HashMap();
/* 524 */     for (int i = 0; i < rewrites.size(); i++) {
/* 525 */       RewriteOperation op = (RewriteOperation)rewrites.get(i);
/* 526 */       if (op != null) {
/* 527 */         if (m.get(new Integer(op.index)) != null) {
/* 528 */           throw new Error("should only be one op per index");
/*     */         }
/* 530 */         m.put(new Integer(op.index), op);
/*     */       }
/*     */     }
/* 533 */     return m;
/*     */   }
/*     */ 
/*     */   protected String catOpText(Object a, Object b) {
/* 537 */     String x = "";
/* 538 */     String y = "";
/* 539 */     if (a != null) x = a.toString();
/* 540 */     if (b != null) y = b.toString();
/* 541 */     return x + y;
/*     */   }
/*     */   protected List getKindOfOps(List rewrites, Class kind) {
/* 544 */     return getKindOfOps(rewrites, kind, rewrites.size());
/*     */   }
/*     */ 
/*     */   protected List getKindOfOps(List rewrites, Class kind, int before)
/*     */   {
/* 549 */     List ops = new ArrayList();
/* 550 */     for (int i = 0; (i < before) && (i < rewrites.size()); i++) {
/* 551 */       RewriteOperation op = (RewriteOperation)rewrites.get(i);
/* 552 */       if ((op != null) && 
/* 553 */         (op.getClass() == kind)) ops.add(op);
/*     */     }
/* 555 */     return ops;
/*     */   }
/*     */ 
/*     */   public String toDebugString() {
/* 559 */     return toDebugString(0, size() - 1);
/*     */   }
/*     */ 
/*     */   public String toDebugString(int start, int end) {
/* 563 */     StringBuffer buf = new StringBuffer();
/* 564 */     for (int i = start; (i >= 0) && (i <= end) && (i < this.tokens.size()); i++) {
/* 565 */       buf.append(get(i));
/*     */     }
/* 567 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   class DeleteOp extends TokenRewriteStream.ReplaceOp
/*     */   {
/*     */     public DeleteOp(int from, int to)
/*     */     {
/* 147 */       super(from, to, null);
/*     */     }
/*     */     public String toString() {
/* 150 */       return "<DeleteOp@" + this.index + ".." + this.lastIndex + ">";
/*     */     }
/*     */   }
/*     */ 
/*     */   class ReplaceOp extends TokenRewriteStream.RewriteOperation
/*     */   {
/*     */     protected int lastIndex;
/*     */ 
/*     */     public ReplaceOp(int from, int to, Object text)
/*     */     {
/* 131 */       super(from, text);
/* 132 */       this.lastIndex = to;
/*     */     }
/*     */     public int execute(StringBuffer buf) {
/* 135 */       if (this.text != null) {
/* 136 */         buf.append(this.text);
/*     */       }
/* 138 */       return this.lastIndex + 1;
/*     */     }
/*     */     public String toString() {
/* 141 */       return "<ReplaceOp@" + this.index + ".." + this.lastIndex + ":\"" + this.text + "\">";
/*     */     }
/*     */   }
/*     */ 
/*     */   class InsertBeforeOp extends TokenRewriteStream.RewriteOperation
/*     */   {
/*     */     public InsertBeforeOp(int index, Object text)
/*     */     {
/* 116 */       super(index, text);
/*     */     }
/*     */     public int execute(StringBuffer buf) {
/* 119 */       buf.append(this.text);
/* 120 */       if (((Token)TokenRewriteStream.this.tokens.get(this.index)).getType() != -1) buf.append(((Token)TokenRewriteStream.this.tokens.get(this.index)).getText());
/* 121 */       return this.index + 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   class RewriteOperation
/*     */   {
/*     */     protected int instructionIndex;
/*     */     protected int index;
/*     */     protected Object text;
/*     */ 
/*     */     protected RewriteOperation(int index, Object text)
/*     */     {
/*  97 */       this.index = index;
/*  98 */       this.text = text;
/*     */     }
/*     */ 
/*     */     public int execute(StringBuffer buf)
/*     */     {
/* 104 */       return this.index;
/*     */     }
/*     */     public String toString() {
/* 107 */       String opName = getClass().getName();
/* 108 */       int $index = opName.indexOf('$');
/* 109 */       opName = opName.substring($index + 1, opName.length());
/* 110 */       return "<" + opName + "@" + this.index + ":\"" + this.text + "\">";
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.TokenRewriteStream
 * JD-Core Version:    0.6.2
 */