/*     */ package org.apache.pdfbox.preflight.javacc.extractor;
/*     */ 
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class ExtractorTokenManager
/*     */   implements ExtractorConstants
/*     */ {
/*  10 */   private List<String> lTrailers = new ArrayList(1);
/*  11 */   private StringBuilder aTrailer = null;
/*  12 */   private boolean alreadyParsed = false;
/*     */ 
/*  41 */   public PrintStream debugStream = System.out;
/*     */ 
/* 517 */   static final int[] jjnextStates = { 19, 20, 9, 10, 4, 5 };
/*     */ 
/* 522 */   public static final String[] jjstrLiteralImages = { null, null, null, null, null, null, null, null, null, null, null };
/*     */ 
/* 526 */   public static final String[] lexStateNames = { "DEFAULT", "WithinTrailer" };
/*     */ 
/* 532 */   public static final int[] jjnewLexState = { -1, -1, -1, -1, -1, -1, -1, -1, 1, -1, 0 };
/*     */ 
/* 535 */   static final long[] jjtoToken = { 1383L };
/*     */ 
/* 538 */   static final long[] jjtoMore = { 640L };
/*     */   protected SimpleCharStream input_stream;
/* 542 */   private final int[] jjrounds = new int[21];
/* 543 */   private final int[] jjstateSet = new int[42];
/* 544 */   private final StringBuilder jjimage = new StringBuilder();
/* 545 */   private StringBuilder image = this.jjimage;
/*     */   private int jjimageLen;
/*     */   private int lengthOfMatch;
/*     */   protected char curChar;
/* 618 */   int curLexState = 0;
/* 619 */   int defaultLexState = 0;
/*     */   int jjnewStateCnt;
/*     */   int jjround;
/*     */   int jjmatchedPos;
/*     */   int jjmatchedKind;
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws FileNotFoundException
/*     */   {
/*  15 */     FileInputStream sr = new FileInputStream(args[0]);
/*  16 */     SimpleCharStream scs = new SimpleCharStream(sr);
/*  17 */     ExtractorTokenManager extractor = new ExtractorTokenManager(scs);
/*     */ 
/*  19 */     for (Token t = extractor.getNextToken(); t.kind != 0; t = extractor.getNextToken()) {
/*  20 */       System.out.println(t.image);
/*     */     }
/*     */ 
/*  23 */     for (String s : extractor.lTrailers)
/*  24 */       System.err.println(s);
/*     */   }
/*     */ 
/*     */   public void parse()
/*     */   {
/*  29 */     if (this.alreadyParsed) return;
/*  30 */     for (Token t = getNextToken(); t.kind != 0; t = getNextToken());
/*  33 */     this.alreadyParsed = true;
/*     */   }
/*     */ 
/*     */   public List<String> getAllTrailers() {
/*  37 */     return this.lTrailers;
/*     */   }
/*     */ 
/*     */   public void setDebugStream(PrintStream ds)
/*     */   {
/*  43 */     this.debugStream = ds;
/*     */   }
/*     */   private final int jjStopStringLiteralDfa_0(int pos, long active0) {
/*  46 */     switch (pos)
/*     */     {
/*     */     }
/*  49 */     return -1;
/*     */   }
/*     */ 
/*     */   private final int jjStartNfa_0(int pos, long active0)
/*     */   {
/*  54 */     return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
/*     */   }
/*     */ 
/*     */   private int jjStopAtPos(int pos, int kind) {
/*  58 */     this.jjmatchedKind = kind;
/*  59 */     this.jjmatchedPos = pos;
/*  60 */     return pos + 1;
/*     */   }
/*     */ 
/*     */   private int jjMoveStringLiteralDfa0_0() {
/*  64 */     switch (this.curChar)
/*     */     {
/*     */     case '%':
/*  67 */       this.jjmatchedKind = 5;
/*  68 */       return jjMoveStringLiteralDfa1_0(2L);
/*     */     }
/*  70 */     return jjMoveNfa_0(6, 0);
/*     */   }
/*     */ 
/*     */   private int jjMoveStringLiteralDfa1_0(long active0) {
/*     */     try {
/*  75 */       this.curChar = this.input_stream.readChar();
/*     */     } catch (IOException e) {
/*  77 */       jjStopStringLiteralDfa_0(0, active0);
/*  78 */       return 1;
/*     */     }
/*  80 */     switch (this.curChar)
/*     */     {
/*     */     case '%':
/*  83 */       return jjMoveStringLiteralDfa2_0(active0, 2L);
/*     */     }
/*     */ 
/*  87 */     return jjStartNfa_0(0, active0);
/*     */   }
/*     */ 
/*     */   private int jjMoveStringLiteralDfa2_0(long old0, long active0) {
/*  91 */     if ((active0 &= old0) == 0L)
/*  92 */       return jjStartNfa_0(0, old0); try {
/*  93 */       this.curChar = this.input_stream.readChar();
/*     */     } catch (IOException e) {
/*  95 */       jjStopStringLiteralDfa_0(1, active0);
/*  96 */       return 2;
/*     */     }
/*  98 */     switch (this.curChar)
/*     */     {
/*     */     case 'E':
/* 101 */       return jjMoveStringLiteralDfa3_0(active0, 2L);
/*     */     }
/*     */ 
/* 105 */     return jjStartNfa_0(1, active0);
/*     */   }
/*     */ 
/*     */   private int jjMoveStringLiteralDfa3_0(long old0, long active0) {
/* 109 */     if ((active0 &= old0) == 0L)
/* 110 */       return jjStartNfa_0(1, old0); try {
/* 111 */       this.curChar = this.input_stream.readChar();
/*     */     } catch (IOException e) {
/* 113 */       jjStopStringLiteralDfa_0(2, active0);
/* 114 */       return 3;
/*     */     }
/* 116 */     switch (this.curChar)
/*     */     {
/*     */     case 'O':
/* 119 */       return jjMoveStringLiteralDfa4_0(active0, 2L);
/*     */     }
/*     */ 
/* 123 */     return jjStartNfa_0(2, active0);
/*     */   }
/*     */ 
/*     */   private int jjMoveStringLiteralDfa4_0(long old0, long active0) {
/* 127 */     if ((active0 &= old0) == 0L)
/* 128 */       return jjStartNfa_0(2, old0); try {
/* 129 */       this.curChar = this.input_stream.readChar();
/*     */     } catch (IOException e) {
/* 131 */       jjStopStringLiteralDfa_0(3, active0);
/* 132 */       return 4;
/*     */     }
/* 134 */     switch (this.curChar)
/*     */     {
/*     */     case 'F':
/* 137 */       if ((active0 & 0x2) != 0L) {
/* 138 */         return jjStopAtPos(4, 1);
/*     */       }
/*     */       break;
/*     */     }
/*     */ 
/* 143 */     return jjStartNfa_0(3, active0);
/*     */   }
/*     */ 
/*     */   private int jjMoveNfa_0(int startState, int curPos) {
/* 147 */     int startsAt = 0;
/* 148 */     this.jjnewStateCnt = 21;
/* 149 */     int i = 1;
/* 150 */     this.jjstateSet[0] = startState;
/* 151 */     int kind = 2147483647;
/*     */     while (true)
/*     */     {
/* 154 */       if (++this.jjround == 2147483647)
/* 155 */         ReInitRounds();
/* 156 */       if (this.curChar < '@')
/*     */       {
/* 158 */         long l = 1L << this.curChar;
/*     */         do
/*     */         {
/* 161 */           switch (this.jjstateSet[(--i)])
/*     */           {
/*     */           case 6:
/* 164 */             if ((0x2400 & l) != 0L)
/*     */             {
/* 166 */               if (kind > 2)
/* 167 */                 kind = 2;
/* 168 */               jjCheckNAdd(17);
/*     */             }
/* 170 */             if (this.curChar == '\r')
/* 171 */               jjAddStates(0, 1); break;
/*     */           case 0:
/* 174 */             if (this.curChar == '.')
/* 175 */               this.jjstateSet[(this.jjnewStateCnt++)] = 1; break;
/*     */           case 1:
/* 178 */             if (((0x0 & l) != 0L) && (kind > 6))
/* 179 */               kind = 6; break;
/*     */           case 2:
/* 182 */             if (this.curChar == '1')
/* 183 */               this.jjstateSet[(this.jjnewStateCnt++)] = 0; break;
/*     */           case 3:
/* 186 */             if (this.curChar == '-')
/* 187 */               this.jjstateSet[(this.jjnewStateCnt++)] = 2; break;
/*     */           case 7:
/* 190 */             if ((0x2400 & l) != 0L)
/*     */             {
/* 192 */               if (kind > 2)
/* 193 */                 kind = 2;
/* 194 */               jjCheckNAdd(17);
/* 195 */             }break;
/*     */           case 9:
/* 197 */             if (((0x2400 & l) != 0L) && (kind > 8))
/* 198 */               kind = 8; break;
/*     */           case 10:
/* 201 */             if (this.curChar == '\r')
/* 202 */               this.jjstateSet[(this.jjnewStateCnt++)] = 11; break;
/*     */           case 11:
/* 205 */             if ((this.curChar == '\n') && (kind > 8))
/* 206 */               kind = 8; break;
/*     */           case 18:
/* 209 */             if (this.curChar == '\r')
/* 210 */               jjAddStates(0, 1); break;
/*     */           case 19:
/* 213 */             if ((this.curChar == '\n') && (kind > 2))
/* 214 */               kind = 2; break;
/*     */           case 20:
/* 217 */             if (this.curChar == '\n')
/* 218 */               jjCheckNAdd(17); break;
/*     */           case 4:
/*     */           case 5:
/*     */           case 8:
/*     */           case 12:
/*     */           case 13:
/*     */           case 14:
/*     */           case 15:
/*     */           case 16:
/* 222 */           case 17: }  } while (i != startsAt);
/*     */       }
/* 224 */       else if (this.curChar < '')
/*     */       {
/* 226 */         long l = 1L << (this.curChar & 0x3F);
/*     */         do
/*     */         {
/* 229 */           switch (this.jjstateSet[(--i)])
/*     */           {
/*     */           case 6:
/* 232 */             if (this.curChar == 'P')
/* 233 */               this.jjstateSet[(this.jjnewStateCnt++)] = 5; break;
/*     */           case 4:
/* 236 */             if (this.curChar == 'F')
/* 237 */               this.jjstateSet[(this.jjnewStateCnt++)] = 3; break;
/*     */           case 5:
/* 240 */             if (this.curChar == 'D')
/* 241 */               this.jjstateSet[(this.jjnewStateCnt++)] = 4; break;
/*     */           case 8:
/* 244 */             if (this.curChar == 'r')
/* 245 */               jjAddStates(2, 3); break;
/*     */           case 12:
/* 248 */             if (this.curChar == 'e')
/* 249 */               this.jjstateSet[(this.jjnewStateCnt++)] = 8; break;
/*     */           case 13:
/* 252 */             if (this.curChar == 'l')
/* 253 */               this.jjstateSet[(this.jjnewStateCnt++)] = 12; break;
/*     */           case 14:
/* 256 */             if (this.curChar == 'i')
/* 257 */               this.jjstateSet[(this.jjnewStateCnt++)] = 13; break;
/*     */           case 15:
/* 260 */             if (this.curChar == 'a')
/* 261 */               this.jjstateSet[(this.jjnewStateCnt++)] = 14; break;
/*     */           case 16:
/* 264 */             if (this.curChar == 'r')
/* 265 */               this.jjstateSet[(this.jjnewStateCnt++)] = 15; break;
/*     */           case 17:
/* 268 */             if (this.curChar == 't')
/* 269 */               this.jjstateSet[(this.jjnewStateCnt++)] = 16; break;
/*     */           case 7:
/*     */           case 9:
/*     */           case 10:
/* 273 */           case 11: }  } while (i != startsAt);
/*     */       }
/*     */       else
/*     */       {
/* 277 */         int i2 = (this.curChar & 0xFF) >> '\006';
/* 278 */         long l2 = 1L << (this.curChar & 0x3F);
/*     */         do
/*     */         {
/* 281 */           switch (this.jjstateSet[(--i)])
/*     */           {
/*     */           }
/*     */         }
/* 285 */         while (i != startsAt);
/*     */       }
/* 287 */       if (kind != 2147483647)
/*     */       {
/* 289 */         this.jjmatchedKind = kind;
/* 290 */         this.jjmatchedPos = curPos;
/* 291 */         kind = 2147483647;
/*     */       }
/* 293 */       curPos++;
/* 294 */       if ((i = this.jjnewStateCnt) == (startsAt = 21 - (this.jjnewStateCnt = startsAt)))
/* 295 */         return curPos; try {
/* 296 */         this.curChar = this.input_stream.readChar(); } catch (IOException e) {  }
/* 297 */     }return curPos;
/*     */   }
/*     */ 
/*     */   private final int jjStopStringLiteralDfa_1(int pos, long active0)
/*     */   {
/* 302 */     switch (pos)
/*     */     {
/*     */     }
/* 305 */     return -1;
/*     */   }
/*     */ 
/*     */   private final int jjStartNfa_1(int pos, long active0)
/*     */   {
/* 310 */     return jjMoveNfa_1(jjStopStringLiteralDfa_1(pos, active0), pos + 1);
/*     */   }
/*     */ 
/*     */   private int jjMoveStringLiteralDfa0_1() {
/* 314 */     switch (this.curChar)
/*     */     {
/*     */     case '%':
/* 317 */       return jjMoveStringLiteralDfa1_1(2L);
/*     */     }
/* 319 */     return jjMoveNfa_1(0, 0);
/*     */   }
/*     */ 
/*     */   private int jjMoveStringLiteralDfa1_1(long active0) {
/*     */     try {
/* 324 */       this.curChar = this.input_stream.readChar();
/*     */     } catch (IOException e) {
/* 326 */       jjStopStringLiteralDfa_1(0, active0);
/* 327 */       return 1;
/*     */     }
/* 329 */     switch (this.curChar)
/*     */     {
/*     */     case '%':
/* 332 */       return jjMoveStringLiteralDfa2_1(active0, 2L);
/*     */     }
/*     */ 
/* 336 */     return jjStartNfa_1(0, active0);
/*     */   }
/*     */ 
/*     */   private int jjMoveStringLiteralDfa2_1(long old0, long active0) {
/* 340 */     if ((active0 &= old0) == 0L)
/* 341 */       return jjStartNfa_1(0, old0); try {
/* 342 */       this.curChar = this.input_stream.readChar();
/*     */     } catch (IOException e) {
/* 344 */       jjStopStringLiteralDfa_1(1, active0);
/* 345 */       return 2;
/*     */     }
/* 347 */     switch (this.curChar)
/*     */     {
/*     */     case 'E':
/* 350 */       return jjMoveStringLiteralDfa3_1(active0, 2L);
/*     */     }
/*     */ 
/* 354 */     return jjStartNfa_1(1, active0);
/*     */   }
/*     */ 
/*     */   private int jjMoveStringLiteralDfa3_1(long old0, long active0) {
/* 358 */     if ((active0 &= old0) == 0L)
/* 359 */       return jjStartNfa_1(1, old0); try {
/* 360 */       this.curChar = this.input_stream.readChar();
/*     */     } catch (IOException e) {
/* 362 */       jjStopStringLiteralDfa_1(2, active0);
/* 363 */       return 3;
/*     */     }
/* 365 */     switch (this.curChar)
/*     */     {
/*     */     case 'O':
/* 368 */       return jjMoveStringLiteralDfa4_1(active0, 2L);
/*     */     }
/*     */ 
/* 372 */     return jjStartNfa_1(2, active0);
/*     */   }
/*     */ 
/*     */   private int jjMoveStringLiteralDfa4_1(long old0, long active0) {
/* 376 */     if ((active0 &= old0) == 0L)
/* 377 */       return jjStartNfa_1(2, old0); try {
/* 378 */       this.curChar = this.input_stream.readChar();
/*     */     } catch (IOException e) {
/* 380 */       jjStopStringLiteralDfa_1(3, active0);
/* 381 */       return 4;
/*     */     }
/* 383 */     switch (this.curChar)
/*     */     {
/*     */     case 'F':
/* 386 */       if ((active0 & 0x2) != 0L) {
/* 387 */         return jjStopAtPos(4, 1);
/*     */       }
/*     */       break;
/*     */     }
/*     */ 
/* 392 */     return jjStartNfa_1(3, active0);
/*     */   }
/*     */ 
/*     */   private int jjMoveNfa_1(int startState, int curPos) {
/* 396 */     int startsAt = 0;
/* 397 */     this.jjnewStateCnt = 15;
/* 398 */     int i = 1;
/* 399 */     this.jjstateSet[0] = startState;
/* 400 */     int kind = 2147483647;
/*     */     while (true)
/*     */     {
/* 403 */       if (++this.jjround == 2147483647)
/* 404 */         ReInitRounds();
/* 405 */       if (this.curChar < '@')
/*     */       {
/* 407 */         long l = 1L << this.curChar;
/*     */         do
/*     */         {
/* 410 */           switch (this.jjstateSet[(--i)])
/*     */           {
/*     */           case 0:
/* 413 */             if ((0x2400 & l) != 0L)
/*     */             {
/* 415 */               if (kind > 2)
/* 416 */                 kind = 2;
/*     */             }
/* 418 */             if (this.curChar == '\r')
/* 419 */               this.jjstateSet[(this.jjnewStateCnt++)] = 2; break;
/*     */           case 1:
/* 422 */             if (this.curChar == '\r')
/* 423 */               this.jjstateSet[(this.jjnewStateCnt++)] = 2; break;
/*     */           case 2:
/* 426 */             if ((this.curChar == '\n') && (kind > 2))
/* 427 */               kind = 2; break;
/*     */           case 4:
/* 430 */             if (((0x2400 & l) != 0L) && (kind > 10))
/* 431 */               kind = 10; break;
/*     */           case 5:
/* 434 */             if (this.curChar == '\r')
/* 435 */               this.jjstateSet[(this.jjnewStateCnt++)] = 6; break;
/*     */           case 6:
/* 438 */             if ((this.curChar == '\n') && (kind > 10))
/* 439 */               kind = 10; break;
/*     */           case 3:
/*     */           }
/*     */         }
/* 443 */         while (i != startsAt);
/*     */       }
/* 445 */       else if (this.curChar < '')
/*     */       {
/* 447 */         long l = 1L << (this.curChar & 0x3F);
/*     */         do
/*     */         {
/* 450 */           switch (this.jjstateSet[(--i)])
/*     */           {
/*     */           case 0:
/* 453 */             if (this.curChar == 's')
/* 454 */               this.jjstateSet[(this.jjnewStateCnt++)] = 13; break;
/*     */           case 3:
/* 457 */             if (this.curChar == 'f')
/* 458 */               jjAddStates(4, 5); break;
/*     */           case 7:
/* 461 */             if (this.curChar == 'e')
/* 462 */               this.jjstateSet[(this.jjnewStateCnt++)] = 3; break;
/*     */           case 8:
/* 465 */             if (this.curChar == 'r')
/* 466 */               this.jjstateSet[(this.jjnewStateCnt++)] = 7; break;
/*     */           case 9:
/* 469 */             if (this.curChar == 'x')
/* 470 */               this.jjstateSet[(this.jjnewStateCnt++)] = 8; break;
/*     */           case 10:
/* 473 */             if (this.curChar == 't')
/* 474 */               this.jjstateSet[(this.jjnewStateCnt++)] = 9; break;
/*     */           case 11:
/* 477 */             if (this.curChar == 'r')
/* 478 */               this.jjstateSet[(this.jjnewStateCnt++)] = 10; break;
/*     */           case 12:
/* 481 */             if (this.curChar == 'a')
/* 482 */               this.jjstateSet[(this.jjnewStateCnt++)] = 11; break;
/*     */           case 13:
/* 485 */             if (this.curChar == 't')
/* 486 */               this.jjstateSet[(this.jjnewStateCnt++)] = 12; break;
/*     */           case 1:
/*     */           case 2:
/*     */           case 4:
/*     */           case 5:
/* 490 */           case 6: }  } while (i != startsAt);
/*     */       }
/*     */       else
/*     */       {
/* 494 */         int i2 = (this.curChar & 0xFF) >> '\006';
/* 495 */         long l2 = 1L << (this.curChar & 0x3F);
/*     */         do
/*     */         {
/* 498 */           switch (this.jjstateSet[(--i)])
/*     */           {
/*     */           }
/*     */         }
/* 502 */         while (i != startsAt);
/*     */       }
/* 504 */       if (kind != 2147483647)
/*     */       {
/* 506 */         this.jjmatchedKind = kind;
/* 507 */         this.jjmatchedPos = curPos;
/* 508 */         kind = 2147483647;
/*     */       }
/* 510 */       curPos++;
/* 511 */       if ((i = this.jjnewStateCnt) == (startsAt = 15 - (this.jjnewStateCnt = startsAt)))
/* 512 */         return curPos; try {
/* 513 */         this.curChar = this.input_stream.readChar(); } catch (IOException e) {  }
/* 514 */     }return curPos;
/*     */   }
/*     */ 
/*     */   public ExtractorTokenManager(SimpleCharStream stream)
/*     */   {
/* 553 */     this.input_stream = stream;
/*     */   }
/*     */ 
/*     */   public ExtractorTokenManager(SimpleCharStream stream, int lexState)
/*     */   {
/* 558 */     this(stream);
/* 559 */     SwitchTo(lexState);
/*     */   }
/*     */ 
/*     */   public void ReInit(SimpleCharStream stream)
/*     */   {
/* 565 */     this.jjmatchedPos = (this.jjnewStateCnt = 0);
/* 566 */     this.curLexState = this.defaultLexState;
/* 567 */     this.input_stream = stream;
/* 568 */     ReInitRounds();
/*     */   }
/*     */ 
/*     */   private void ReInitRounds()
/*     */   {
/* 573 */     this.jjround = -2147483647;
/* 574 */     for (int i = 21; i-- > 0; )
/* 575 */       this.jjrounds[i] = -2147483648;
/*     */   }
/*     */ 
/*     */   public void ReInit(SimpleCharStream stream, int lexState)
/*     */   {
/* 581 */     ReInit(stream);
/* 582 */     SwitchTo(lexState);
/*     */   }
/*     */ 
/*     */   public void SwitchTo(int lexState)
/*     */   {
/* 588 */     if ((lexState >= 2) || (lexState < 0)) {
/* 589 */       throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", 2);
/*     */     }
/* 591 */     this.curLexState = lexState;
/*     */   }
/*     */ 
/*     */   protected Token jjFillToken()
/*     */   {
/* 602 */     String im = jjstrLiteralImages[this.jjmatchedKind];
/* 603 */     String curTokenImage = im == null ? this.input_stream.GetImage() : im;
/* 604 */     int beginLine = this.input_stream.getBeginLine();
/* 605 */     int beginColumn = this.input_stream.getBeginColumn();
/* 606 */     int endLine = this.input_stream.getEndLine();
/* 607 */     int endColumn = this.input_stream.getEndColumn();
/* 608 */     Token t = Token.newToken(this.jjmatchedKind, curTokenImage);
/*     */ 
/* 610 */     t.beginLine = beginLine;
/* 611 */     t.endLine = endLine;
/* 612 */     t.beginColumn = beginColumn;
/* 613 */     t.endColumn = endColumn;
/*     */ 
/* 615 */     return t;
/*     */   }
/*     */ 
/*     */   public Token getNextToken()
/*     */   {
/* 629 */     int curPos = 0;
/*     */     try
/*     */     {
/* 636 */       this.curChar = this.input_stream.BeginToken();
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 640 */       this.jjmatchedKind = 0;
/* 641 */       return jjFillToken();
/*     */     }
/*     */ 
/* 644 */     this.image = this.jjimage;
/* 645 */     this.image.setLength(0);
/* 646 */     this.jjimageLen = 0;
/*     */     while (true)
/*     */     {
/* 650 */       switch (this.curLexState)
/*     */       {
/*     */       case 0:
/* 653 */         this.jjmatchedKind = 2147483647;
/* 654 */         this.jjmatchedPos = 0;
/* 655 */         curPos = jjMoveStringLiteralDfa0_0();
/* 656 */         if ((this.jjmatchedPos == 0) && (this.jjmatchedKind > 7))
/*     */         {
/* 658 */           this.jjmatchedKind = 7; } break;
/*     */       case 1:
/* 662 */         this.jjmatchedKind = 2147483647;
/* 663 */         this.jjmatchedPos = 0;
/* 664 */         curPos = jjMoveStringLiteralDfa0_1();
/* 665 */         if ((this.jjmatchedPos == 0) && (this.jjmatchedKind > 9))
/*     */         {
/* 667 */           this.jjmatchedKind = 9;
/*     */         }
/*     */         break;
/*     */       }
/* 671 */       if (this.jjmatchedKind != 2147483647)
/*     */       {
/* 673 */         if (this.jjmatchedPos + 1 < curPos)
/* 674 */           this.input_stream.backup(curPos - this.jjmatchedPos - 1);
/* 675 */         if ((jjtoToken[(this.jjmatchedKind >> 6)] & 1L << (this.jjmatchedKind & 0x3F)) != 0L)
/*     */         {
/* 677 */           Token matchedToken = jjFillToken();
/* 678 */           TokenLexicalActions(matchedToken);
/* 679 */           if (jjnewLexState[this.jjmatchedKind] != -1)
/* 680 */             this.curLexState = jjnewLexState[this.jjmatchedKind];
/* 681 */           return matchedToken;
/*     */         }
/* 683 */         MoreLexicalActions();
/* 684 */         if (jjnewLexState[this.jjmatchedKind] != -1)
/* 685 */           this.curLexState = jjnewLexState[this.jjmatchedKind];
/* 686 */         curPos = 0;
/* 687 */         this.jjmatchedKind = 2147483647;
/*     */         try {
/* 689 */           this.curChar = this.input_stream.readChar();
/*     */         } catch (IOException e1) {
/*     */         }
/*     */       }
/*     */     }
/* 694 */     int error_line = this.input_stream.getEndLine();
/* 695 */     int error_column = this.input_stream.getEndColumn();
/* 696 */     String error_after = null;
/* 697 */     boolean EOFSeen = false;
/*     */     try { this.input_stream.readChar(); this.input_stream.backup(1);
/*     */     } catch (IOException e1) {
/* 700 */       EOFSeen = true;
/* 701 */       error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
/* 702 */       if ((this.curChar == '\n') || (this.curChar == '\r')) {
/* 703 */         error_line++;
/* 704 */         error_column = 0;
/*     */       }
/*     */       else {
/* 707 */         error_column++;
/*     */       }
/*     */     }
/* 709 */     if (!EOFSeen) {
/* 710 */       this.input_stream.backup(1);
/* 711 */       error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
/*     */     }
/* 713 */     throw new TokenMgrError(EOFSeen, this.curLexState, error_line, error_column, error_after, this.curChar, 0);
/*     */   }
/*     */ 
/*     */   void MoreLexicalActions()
/*     */   {
/* 720 */     this.jjimageLen += (this.lengthOfMatch = this.jjmatchedPos + 1);
/* 721 */     switch (this.jjmatchedKind)
/*     */     {
/*     */     case 9:
/* 724 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 725 */       this.jjimageLen = 0;
/* 726 */       this.aTrailer.append(this.image.charAt(this.image.length() - 1));
/* 727 */       break;
/*     */     }
/*     */   }
/*     */ 
/*     */   void TokenLexicalActions(Token matchedToken)
/*     */   {
/* 734 */     switch (this.jjmatchedKind)
/*     */     {
/*     */     case 8:
/* 737 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 738 */       this.aTrailer = new StringBuilder(50);
/* 739 */       break;
/*     */     case 10:
/* 741 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 742 */       this.lTrailers.add(this.aTrailer.toString()); this.aTrailer = null;
/* 743 */       break;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void jjCheckNAdd(int state)
/*     */   {
/* 750 */     if (this.jjrounds[state] != this.jjround)
/*     */     {
/* 752 */       this.jjstateSet[(this.jjnewStateCnt++)] = state;
/* 753 */       this.jjrounds[state] = this.jjround;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void jjAddStates(int start, int end) {
/*     */     do
/* 759 */       this.jjstateSet[(this.jjnewStateCnt++)] = jjnextStates[start];
/* 760 */     while (start++ != end);
/*     */   }
/*     */ 
/*     */   private void jjCheckNAddTwoStates(int state1, int state2) {
/* 764 */     jjCheckNAdd(state1);
/* 765 */     jjCheckNAdd(state2);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.javacc.extractor.ExtractorTokenManager
 * JD-Core Version:    0.6.2
 */