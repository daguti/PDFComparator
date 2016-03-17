/*     */ package antlr;
/*     */ 
/*     */ import antlr.ASdebug.ASDebugStream;
/*     */ import antlr.ASdebug.IASDebugStream;
/*     */ import antlr.ASdebug.TokenOffsetInfo;
/*     */ import antlr.collections.impl.BitSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class TokenStreamRewriteEngine
/*     */   implements TokenStream, IASDebugStream
/*     */ {
/*     */   public static final int MIN_TOKEN_INDEX = 0;
/*     */   public static final String DEFAULT_PROGRAM_NAME = "default";
/*     */   public static final int PROGRAM_INIT_SIZE = 100;
/*     */   protected List tokens;
/* 130 */   protected Map programs = null;
/*     */ 
/* 133 */   protected Map lastRewriteTokenIndexes = null;
/*     */ 
/* 136 */   protected int index = 0;
/*     */   protected TokenStream stream;
/* 142 */   protected BitSet discardMask = new BitSet();
/*     */ 
/*     */   public TokenStreamRewriteEngine(TokenStream paramTokenStream) {
/* 145 */     this(paramTokenStream, 1000);
/*     */   }
/*     */ 
/*     */   public TokenStreamRewriteEngine(TokenStream paramTokenStream, int paramInt) {
/* 149 */     this.stream = paramTokenStream;
/* 150 */     this.tokens = new ArrayList(paramInt);
/* 151 */     this.programs = new HashMap();
/* 152 */     this.programs.put("default", new ArrayList(100));
/*     */ 
/* 154 */     this.lastRewriteTokenIndexes = new HashMap();
/*     */   }
/*     */ 
/*     */   public Token nextToken() throws TokenStreamException
/*     */   {
/*     */     TokenWithIndex localTokenWithIndex;
/*     */     do {
/* 161 */       localTokenWithIndex = (TokenWithIndex)this.stream.nextToken();
/* 162 */       if (localTokenWithIndex != null) {
/* 163 */         localTokenWithIndex.setIndex(this.index);
/* 164 */         if (localTokenWithIndex.getType() != 1) {
/* 165 */           this.tokens.add(localTokenWithIndex);
/*     */         }
/* 167 */         this.index += 1;
/*     */       }
/*     */     }
/* 169 */     while ((localTokenWithIndex != null) && (this.discardMask.member(localTokenWithIndex.getType())));
/* 170 */     return localTokenWithIndex;
/*     */   }
/*     */ 
/*     */   public void rollback(int paramInt) {
/* 174 */     rollback("default", paramInt);
/*     */   }
/*     */ 
/*     */   public void rollback(String paramString, int paramInt)
/*     */   {
/* 182 */     List localList = (List)this.programs.get(paramString);
/* 183 */     if (localList != null)
/* 184 */       this.programs.put(paramString, localList.subList(0, paramInt));
/*     */   }
/*     */ 
/*     */   public void deleteProgram()
/*     */   {
/* 189 */     deleteProgram("default");
/*     */   }
/*     */ 
/*     */   public void deleteProgram(String paramString)
/*     */   {
/* 194 */     rollback(paramString, 0);
/*     */   }
/*     */ 
/*     */   protected void addToSortedRewriteList(RewriteOperation paramRewriteOperation)
/*     */   {
/* 200 */     addToSortedRewriteList("default", paramRewriteOperation);
/*     */   }
/*     */ 
/*     */   protected void addToSortedRewriteList(String paramString, RewriteOperation paramRewriteOperation)
/*     */   {
/* 247 */     List localList = getProgram(paramString);
/*     */ 
/* 249 */     Comparator local1 = new Comparator() {
/*     */       public int compare(Object paramAnonymousObject1, Object paramAnonymousObject2) {
/* 251 */         TokenStreamRewriteEngine.RewriteOperation localRewriteOperation1 = (TokenStreamRewriteEngine.RewriteOperation)paramAnonymousObject1;
/* 252 */         TokenStreamRewriteEngine.RewriteOperation localRewriteOperation2 = (TokenStreamRewriteEngine.RewriteOperation)paramAnonymousObject2;
/* 253 */         if (localRewriteOperation1.index < localRewriteOperation2.index) return -1;
/* 254 */         if (localRewriteOperation1.index > localRewriteOperation2.index) return 1;
/* 255 */         return 0;
/*     */       }
/*     */     };
/* 258 */     int i = Collections.binarySearch(localList, paramRewriteOperation, local1);
/*     */ 
/* 261 */     if (i >= 0)
/*     */     {
/* 264 */       for (; i >= 0; i--) {
/* 265 */         RewriteOperation localRewriteOperation1 = (RewriteOperation)localList.get(i);
/* 266 */         if (localRewriteOperation1.index < paramRewriteOperation.index) {
/*     */           break;
/*     */         }
/*     */       }
/* 270 */       i++;
/*     */ 
/* 277 */       if ((paramRewriteOperation instanceof ReplaceOp)) {
/* 278 */         int j = 0;
/*     */ 
/* 281 */         for (int k = i; k < localList.size(); k++) {
/* 282 */           RewriteOperation localRewriteOperation2 = (RewriteOperation)localList.get(i);
/* 283 */           if (localRewriteOperation2.index != paramRewriteOperation.index) {
/*     */             break;
/*     */           }
/* 286 */           if ((localRewriteOperation2 instanceof ReplaceOp)) {
/* 287 */             localList.set(i, paramRewriteOperation);
/* 288 */             j = 1;
/* 289 */             break;
/*     */           }
/*     */         }
/*     */ 
/* 293 */         if (j == 0)
/*     */         {
/* 295 */           localList.add(k, paramRewriteOperation);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 300 */         localList.add(i, paramRewriteOperation);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 305 */       localList.add(-i - 1, paramRewriteOperation);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void insertAfter(Token paramToken, String paramString)
/*     */   {
/* 311 */     insertAfter("default", paramToken, paramString);
/*     */   }
/*     */ 
/*     */   public void insertAfter(int paramInt, String paramString) {
/* 315 */     insertAfter("default", paramInt, paramString);
/*     */   }
/*     */ 
/*     */   public void insertAfter(String paramString1, Token paramToken, String paramString2) {
/* 319 */     insertAfter(paramString1, ((TokenWithIndex)paramToken).getIndex(), paramString2);
/*     */   }
/*     */ 
/*     */   public void insertAfter(String paramString1, int paramInt, String paramString2)
/*     */   {
/* 324 */     insertBefore(paramString1, paramInt + 1, paramString2);
/*     */   }
/*     */ 
/*     */   public void insertBefore(Token paramToken, String paramString) {
/* 328 */     insertBefore("default", paramToken, paramString);
/*     */   }
/*     */ 
/*     */   public void insertBefore(int paramInt, String paramString) {
/* 332 */     insertBefore("default", paramInt, paramString);
/*     */   }
/*     */ 
/*     */   public void insertBefore(String paramString1, Token paramToken, String paramString2) {
/* 336 */     insertBefore(paramString1, ((TokenWithIndex)paramToken).getIndex(), paramString2);
/*     */   }
/*     */ 
/*     */   public void insertBefore(String paramString1, int paramInt, String paramString2) {
/* 340 */     addToSortedRewriteList(paramString1, new InsertBeforeOp(paramInt, paramString2));
/*     */   }
/*     */ 
/*     */   public void replace(int paramInt, String paramString) {
/* 344 */     replace("default", paramInt, paramInt, paramString);
/*     */   }
/*     */ 
/*     */   public void replace(int paramInt1, int paramInt2, String paramString) {
/* 348 */     replace("default", paramInt1, paramInt2, paramString);
/*     */   }
/*     */ 
/*     */   public void replace(Token paramToken, String paramString) {
/* 352 */     replace("default", paramToken, paramToken, paramString);
/*     */   }
/*     */ 
/*     */   public void replace(Token paramToken1, Token paramToken2, String paramString) {
/* 356 */     replace("default", paramToken1, paramToken2, paramString);
/*     */   }
/*     */ 
/*     */   public void replace(String paramString1, int paramInt1, int paramInt2, String paramString2) {
/* 360 */     addToSortedRewriteList(new ReplaceOp(paramInt1, paramInt2, paramString2));
/*     */   }
/*     */ 
/*     */   public void replace(String paramString1, Token paramToken1, Token paramToken2, String paramString2) {
/* 364 */     replace(paramString1, ((TokenWithIndex)paramToken1).getIndex(), ((TokenWithIndex)paramToken2).getIndex(), paramString2);
/*     */   }
/*     */ 
/*     */   public void delete(int paramInt)
/*     */   {
/* 371 */     delete("default", paramInt, paramInt);
/*     */   }
/*     */ 
/*     */   public void delete(int paramInt1, int paramInt2) {
/* 375 */     delete("default", paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public void delete(Token paramToken) {
/* 379 */     delete("default", paramToken, paramToken);
/*     */   }
/*     */ 
/*     */   public void delete(Token paramToken1, Token paramToken2) {
/* 383 */     delete("default", paramToken1, paramToken2);
/*     */   }
/*     */ 
/*     */   public void delete(String paramString, int paramInt1, int paramInt2) {
/* 387 */     replace(paramString, paramInt1, paramInt2, null);
/*     */   }
/*     */ 
/*     */   public void delete(String paramString, Token paramToken1, Token paramToken2) {
/* 391 */     replace(paramString, paramToken1, paramToken2, null);
/*     */   }
/*     */ 
/*     */   public void discard(int paramInt) {
/* 395 */     this.discardMask.add(paramInt);
/*     */   }
/*     */ 
/*     */   public TokenWithIndex getToken(int paramInt) {
/* 399 */     return (TokenWithIndex)this.tokens.get(paramInt);
/*     */   }
/*     */ 
/*     */   public int getTokenStreamSize() {
/* 403 */     return this.tokens.size();
/*     */   }
/*     */ 
/*     */   public String toOriginalString() {
/* 407 */     return toOriginalString(0, getTokenStreamSize() - 1);
/*     */   }
/*     */ 
/*     */   public String toOriginalString(int paramInt1, int paramInt2) {
/* 411 */     StringBuffer localStringBuffer = new StringBuffer();
/* 412 */     for (int i = paramInt1; (i >= 0) && (i <= paramInt2) && (i < this.tokens.size()); i++) {
/* 413 */       localStringBuffer.append(getToken(i).getText());
/*     */     }
/* 415 */     return localStringBuffer.toString();
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 419 */     return toString(0, getTokenStreamSize() - 1);
/*     */   }
/*     */ 
/*     */   public String toString(String paramString) {
/* 423 */     return toString(paramString, 0, getTokenStreamSize() - 1);
/*     */   }
/*     */ 
/*     */   public String toString(int paramInt1, int paramInt2) {
/* 427 */     return toString("default", paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public String toString(String paramString, int paramInt1, int paramInt2) {
/* 431 */     List localList = (List)this.programs.get(paramString);
/* 432 */     if ((localList == null) || (localList.size() == 0)) {
/* 433 */       return toOriginalString(paramInt1, paramInt2);
/*     */     }
/* 435 */     StringBuffer localStringBuffer = new StringBuffer();
/*     */ 
/* 438 */     RewriteOperation localRewriteOperation1 = 0;
/*     */ 
/* 440 */     int i = paramInt1;
/*     */ 
/* 443 */     while ((i >= 0) && (i <= paramInt2) && (i < this.tokens.size()))
/*     */     {
/* 447 */       if (localRewriteOperation1 < localList.size()) {
/* 448 */         localRewriteOperation2 = (RewriteOperation)localList.get(localRewriteOperation1);
/*     */ 
/* 452 */         while ((localRewriteOperation2.index < i) && (localRewriteOperation1 < localList.size())) {
/* 453 */           localRewriteOperation1++;
/* 454 */           if (localRewriteOperation1 < localList.size()) {
/* 455 */             localRewriteOperation2 = (RewriteOperation)localList.get(localRewriteOperation1);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 460 */         while ((i == localRewriteOperation2.index) && (localRewriteOperation1 < localList.size()))
/*     */         {
/* 462 */           i = localRewriteOperation2.execute(localStringBuffer);
/*     */ 
/* 464 */           localRewriteOperation1++;
/* 465 */           if (localRewriteOperation1 < localList.size()) {
/* 466 */             localRewriteOperation2 = (RewriteOperation)localList.get(localRewriteOperation1);
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 471 */       if (i <= paramInt2) {
/* 472 */         localStringBuffer.append(getToken(i).getText());
/* 473 */         i++;
/*     */       }
/*     */     }
/*     */ 
/* 477 */     for (RewriteOperation localRewriteOperation2 = localRewriteOperation1; localRewriteOperation2 < localList.size(); localRewriteOperation2++) {
/* 478 */       RewriteOperation localRewriteOperation3 = (RewriteOperation)localList.get(localRewriteOperation2);
/*     */ 
/* 480 */       if (localRewriteOperation3.index >= size()) {
/* 481 */         localRewriteOperation3.execute(localStringBuffer);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 487 */     return localStringBuffer.toString();
/*     */   }
/*     */ 
/*     */   public String toDebugString() {
/* 491 */     return toDebugString(0, getTokenStreamSize() - 1);
/*     */   }
/*     */ 
/*     */   public String toDebugString(int paramInt1, int paramInt2) {
/* 495 */     StringBuffer localStringBuffer = new StringBuffer();
/* 496 */     for (int i = paramInt1; (i >= 0) && (i <= paramInt2) && (i < this.tokens.size()); i++) {
/* 497 */       localStringBuffer.append(getToken(i));
/*     */     }
/* 499 */     return localStringBuffer.toString();
/*     */   }
/*     */ 
/*     */   public int getLastRewriteTokenIndex() {
/* 503 */     return getLastRewriteTokenIndex("default");
/*     */   }
/*     */ 
/*     */   protected int getLastRewriteTokenIndex(String paramString) {
/* 507 */     Integer localInteger = (Integer)this.lastRewriteTokenIndexes.get(paramString);
/* 508 */     if (localInteger == null) {
/* 509 */       return -1;
/*     */     }
/* 511 */     return localInteger.intValue();
/*     */   }
/*     */ 
/*     */   protected void setLastRewriteTokenIndex(String paramString, int paramInt) {
/* 515 */     this.lastRewriteTokenIndexes.put(paramString, new Integer(paramInt));
/*     */   }
/*     */ 
/*     */   protected List getProgram(String paramString) {
/* 519 */     List localList = (List)this.programs.get(paramString);
/* 520 */     if (localList == null) {
/* 521 */       localList = initializeProgram(paramString);
/*     */     }
/* 523 */     return localList;
/*     */   }
/*     */ 
/*     */   private List initializeProgram(String paramString) {
/* 527 */     ArrayList localArrayList = new ArrayList(100);
/* 528 */     this.programs.put(paramString, localArrayList);
/* 529 */     return localArrayList;
/*     */   }
/*     */ 
/*     */   public int size() {
/* 533 */     return this.tokens.size();
/*     */   }
/*     */ 
/*     */   public int index() {
/* 537 */     return this.index;
/*     */   }
/*     */ 
/*     */   public String getEntireText()
/*     */   {
/* 542 */     return ASDebugStream.getEntireText(this.stream);
/*     */   }
/*     */ 
/*     */   public TokenOffsetInfo getOffsetInfo(Token paramToken)
/*     */   {
/* 547 */     return ASDebugStream.getOffsetInfo(this.stream, paramToken);
/*     */   }
/*     */ 
/*     */   static class DeleteOp extends TokenStreamRewriteEngine.ReplaceOp
/*     */   {
/*     */     public DeleteOp(int paramInt1, int paramInt2)
/*     */     {
/* 116 */       super(paramInt2, null);
/*     */     }
/*     */   }
/*     */ 
/*     */   static class ReplaceOp extends TokenStreamRewriteEngine.RewriteOperation
/*     */   {
/*     */     protected int lastIndex;
/*     */ 
/*     */     public ReplaceOp(int paramInt1, int paramInt2, String paramString)
/*     */     {
/* 103 */       super(paramString);
/* 104 */       this.lastIndex = paramInt2;
/*     */     }
/*     */     public int execute(StringBuffer paramStringBuffer) {
/* 107 */       if (this.text != null) {
/* 108 */         paramStringBuffer.append(this.text);
/*     */       }
/* 110 */       return this.lastIndex + 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   static class InsertBeforeOp extends TokenStreamRewriteEngine.RewriteOperation
/*     */   {
/*     */     public InsertBeforeOp(int paramInt, String paramString)
/*     */     {
/*  89 */       super(paramString);
/*     */     }
/*     */     public int execute(StringBuffer paramStringBuffer) {
/*  92 */       paramStringBuffer.append(this.text);
/*  93 */       return this.index;
/*     */     }
/*     */   }
/*     */ 
/*     */   static class RewriteOperation
/*     */   {
/*     */     protected int index;
/*     */     protected String text;
/*     */ 
/*     */     protected RewriteOperation(int paramInt, String paramString)
/*     */     {
/*  70 */       this.index = paramInt;
/*  71 */       this.text = paramString;
/*     */     }
/*     */ 
/*     */     public int execute(StringBuffer paramStringBuffer)
/*     */     {
/*  77 */       return this.index;
/*     */     }
/*     */     public String toString() {
/*  80 */       String str = getClass().getName();
/*  81 */       int i = str.indexOf('$');
/*  82 */       str = str.substring(i + 1, str.length());
/*  83 */       return str + "@" + this.index + '"' + this.text + '"';
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.TokenStreamRewriteEngine
 * JD-Core Version:    0.6.2
 */