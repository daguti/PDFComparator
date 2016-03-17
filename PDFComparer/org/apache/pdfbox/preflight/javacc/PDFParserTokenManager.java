/*      */ package org.apache.pdfbox.preflight.javacc;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ 
/*      */ public class PDFParserTokenManager
/*      */   implements PDFParserConstants
/*      */ {
/*   17 */   public PrintStream debugStream = System.out;
/*      */ 
/*  242 */   static final long[] jjbitVec0 = { 0L, 0L, -1L, -1L };
/*      */ 
/*  986 */   static final long[] jjbitVec1 = { -2L, -1L, -1L, -1L };
/*      */ 
/* 1562 */   static final int[] jjnextStates = { 42, 44, 53, 63, 64, 70, 71, 65, 68, 72, 80, 11, 12, 15, 17, 46, 48, 59, 61, 75, 77, 5, 22, 23 };
/*      */ 
/* 1590 */   public static final String[] jjstrLiteralImages = { "", " ", null, null, "%", null, null, null, null, null, null, null, null, null, null, "[", "]", null, "null", null, null, null, null, null, null, null, null, null, null, null, null, null, "xref", null, null, null, null, "trailer", "<<", ">>", "startxref", null, "%%EOF" };
/*      */ 
/* 1598 */   public static final String[] lexStateNames = { "DEFAULT", "WithinTrailer", "CrossRefTable", "WithinLIT", "WithinStream" };
/*      */ 
/* 1607 */   public static final int[] jjnewLexState = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 4, -1, -1, -1, 3, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 2, -1, -1, -1, -1, 0, -1, -1, 1, -1, 0 };
/*      */ 
/* 1611 */   static final long[] jjtoToken = { 8691673726975L };
/*      */ 
/* 1614 */   static final long[] jjtoSkip = { 234881024L };
/*      */ 
/* 1617 */   static final long[] jjtoMore = { 1090519040L };
/*      */   protected SimpleCharStream input_stream;
/* 1621 */   private final int[] jjrounds = new int[82];
/* 1622 */   private final int[] jjstateSet = new int['¤'];
/*      */   protected char curChar;
/* 1693 */   int curLexState = 0;
/* 1694 */   int defaultLexState = 0;
/*      */   int jjnewStateCnt;
/*      */   int jjround;
/*      */   int jjmatchedPos;
/*      */   int jjmatchedKind;
/*      */ 
/*      */   public void setDebugStream(PrintStream ds)
/*      */   {
/*   19 */     this.debugStream = ds;
/*      */   }
/*      */   private final int jjStopStringLiteralDfa_0(int pos, long active0) {
/*   22 */     switch (pos)
/*      */     {
/*      */     case 0:
/*   25 */       if ((active0 & 0x0) != 0L)
/*   26 */         return 21;
/*   27 */       if ((active0 & 0x0) != 0L)
/*   28 */         return 10;
/*   29 */       return -1;
/*      */     case 1:
/*   31 */       if ((active0 & 0x0) != 0L)
/*   32 */         return 20;
/*   33 */       return -1;
/*      */     }
/*   35 */     return -1;
/*      */   }
/*      */ 
/*      */   private final int jjStartNfa_0(int pos, long active0)
/*      */   {
/*   40 */     return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
/*      */   }
/*      */ 
/*      */   private int jjStopAtPos(int pos, int kind) {
/*   44 */     this.jjmatchedKind = kind;
/*   45 */     this.jjmatchedPos = pos;
/*   46 */     return pos + 1;
/*      */   }
/*      */ 
/*      */   private int jjMoveStringLiteralDfa0_0() {
/*   50 */     switch (this.curChar)
/*      */     {
/*      */     case ' ':
/*   53 */       return jjStopAtPos(0, 1);
/*      */     case '%':
/*   55 */       return jjStopAtPos(0, 4);
/*      */     case '<':
/*   57 */       return jjMoveStringLiteralDfa1_0(274877906944L);
/*      */     case '>':
/*   59 */       return jjMoveStringLiteralDfa1_0(549755813888L);
/*      */     case '[':
/*   61 */       return jjStopAtPos(0, 15);
/*      */     case ']':
/*   63 */       return jjStopAtPos(0, 16);
/*      */     case 'n':
/*   65 */       return jjMoveStringLiteralDfa1_0(262144L);
/*      */     case 's':
/*   67 */       return jjMoveStringLiteralDfa1_0(1099511627776L);
/*      */     case 'x':
/*   69 */       return jjMoveStringLiteralDfa1_0(4294967296L);
/*      */     }
/*   71 */     return jjMoveNfa_0(0, 0);
/*      */   }
/*      */ 
/*      */   private int jjMoveStringLiteralDfa1_0(long active0) {
/*      */     try {
/*   76 */       this.curChar = this.input_stream.readChar();
/*      */     } catch (IOException e) {
/*   78 */       jjStopStringLiteralDfa_0(0, active0);
/*   79 */       return 1;
/*      */     }
/*   81 */     switch (this.curChar)
/*      */     {
/*      */     case '<':
/*   84 */       if ((active0 & 0x0) != 0L)
/*   85 */         return jjStopAtPos(1, 38);
/*      */       break;
/*      */     case '>':
/*   88 */       if ((active0 & 0x0) != 0L)
/*   89 */         return jjStopAtPos(1, 39);
/*      */       break;
/*      */     case 'r':
/*   92 */       return jjMoveStringLiteralDfa2_0(active0, 4294967296L);
/*      */     case 't':
/*   94 */       return jjMoveStringLiteralDfa2_0(active0, 1099511627776L);
/*      */     case 'u':
/*   96 */       return jjMoveStringLiteralDfa2_0(active0, 262144L);
/*      */     }
/*      */ 
/*  100 */     return jjStartNfa_0(0, active0);
/*      */   }
/*      */ 
/*      */   private int jjMoveStringLiteralDfa2_0(long old0, long active0) {
/*  104 */     if ((active0 &= old0) == 0L)
/*  105 */       return jjStartNfa_0(0, old0); try {
/*  106 */       this.curChar = this.input_stream.readChar();
/*      */     } catch (IOException e) {
/*  108 */       jjStopStringLiteralDfa_0(1, active0);
/*  109 */       return 2;
/*      */     }
/*  111 */     switch (this.curChar)
/*      */     {
/*      */     case 'a':
/*  114 */       return jjMoveStringLiteralDfa3_0(active0, 1099511627776L);
/*      */     case 'e':
/*  116 */       return jjMoveStringLiteralDfa3_0(active0, 4294967296L);
/*      */     case 'l':
/*  118 */       return jjMoveStringLiteralDfa3_0(active0, 262144L);
/*      */     }
/*      */ 
/*  122 */     return jjStartNfa_0(1, active0);
/*      */   }
/*      */ 
/*      */   private int jjMoveStringLiteralDfa3_0(long old0, long active0) {
/*  126 */     if ((active0 &= old0) == 0L)
/*  127 */       return jjStartNfa_0(1, old0); try {
/*  128 */       this.curChar = this.input_stream.readChar();
/*      */     } catch (IOException e) {
/*  130 */       jjStopStringLiteralDfa_0(2, active0);
/*  131 */       return 3;
/*      */     }
/*  133 */     switch (this.curChar)
/*      */     {
/*      */     case 'f':
/*  136 */       if ((active0 & 0x0) != 0L)
/*  137 */         return jjStopAtPos(3, 32);
/*      */       break;
/*      */     case 'l':
/*  140 */       if ((active0 & 0x40000) != 0L)
/*  141 */         return jjStopAtPos(3, 18);
/*      */       break;
/*      */     case 'r':
/*  144 */       return jjMoveStringLiteralDfa4_0(active0, 1099511627776L);
/*      */     }
/*      */ 
/*  148 */     return jjStartNfa_0(2, active0);
/*      */   }
/*      */ 
/*      */   private int jjMoveStringLiteralDfa4_0(long old0, long active0) {
/*  152 */     if ((active0 &= old0) == 0L)
/*  153 */       return jjStartNfa_0(2, old0); try {
/*  154 */       this.curChar = this.input_stream.readChar();
/*      */     } catch (IOException e) {
/*  156 */       jjStopStringLiteralDfa_0(3, active0);
/*  157 */       return 4;
/*      */     }
/*  159 */     switch (this.curChar)
/*      */     {
/*      */     case 't':
/*  162 */       return jjMoveStringLiteralDfa5_0(active0, 1099511627776L);
/*      */     }
/*      */ 
/*  166 */     return jjStartNfa_0(3, active0);
/*      */   }
/*      */ 
/*      */   private int jjMoveStringLiteralDfa5_0(long old0, long active0) {
/*  170 */     if ((active0 &= old0) == 0L)
/*  171 */       return jjStartNfa_0(3, old0); try {
/*  172 */       this.curChar = this.input_stream.readChar();
/*      */     } catch (IOException e) {
/*  174 */       jjStopStringLiteralDfa_0(4, active0);
/*  175 */       return 5;
/*      */     }
/*  177 */     switch (this.curChar)
/*      */     {
/*      */     case 'x':
/*  180 */       return jjMoveStringLiteralDfa6_0(active0, 1099511627776L);
/*      */     }
/*      */ 
/*  184 */     return jjStartNfa_0(4, active0);
/*      */   }
/*      */ 
/*      */   private int jjMoveStringLiteralDfa6_0(long old0, long active0) {
/*  188 */     if ((active0 &= old0) == 0L)
/*  189 */       return jjStartNfa_0(4, old0); try {
/*  190 */       this.curChar = this.input_stream.readChar();
/*      */     } catch (IOException e) {
/*  192 */       jjStopStringLiteralDfa_0(5, active0);
/*  193 */       return 6;
/*      */     }
/*  195 */     switch (this.curChar)
/*      */     {
/*      */     case 'r':
/*  198 */       return jjMoveStringLiteralDfa7_0(active0, 1099511627776L);
/*      */     }
/*      */ 
/*  202 */     return jjStartNfa_0(5, active0);
/*      */   }
/*      */ 
/*      */   private int jjMoveStringLiteralDfa7_0(long old0, long active0) {
/*  206 */     if ((active0 &= old0) == 0L)
/*  207 */       return jjStartNfa_0(5, old0); try {
/*  208 */       this.curChar = this.input_stream.readChar();
/*      */     } catch (IOException e) {
/*  210 */       jjStopStringLiteralDfa_0(6, active0);
/*  211 */       return 7;
/*      */     }
/*  213 */     switch (this.curChar)
/*      */     {
/*      */     case 'e':
/*  216 */       return jjMoveStringLiteralDfa8_0(active0, 1099511627776L);
/*      */     }
/*      */ 
/*  220 */     return jjStartNfa_0(6, active0);
/*      */   }
/*      */ 
/*      */   private int jjMoveStringLiteralDfa8_0(long old0, long active0) {
/*  224 */     if ((active0 &= old0) == 0L)
/*  225 */       return jjStartNfa_0(6, old0); try {
/*  226 */       this.curChar = this.input_stream.readChar();
/*      */     } catch (IOException e) {
/*  228 */       jjStopStringLiteralDfa_0(7, active0);
/*  229 */       return 8;
/*      */     }
/*  231 */     switch (this.curChar)
/*      */     {
/*      */     case 'f':
/*  234 */       if ((active0 & 0x0) != 0L) {
/*  235 */         return jjStopAtPos(8, 40);
/*      */       }
/*      */       break;
/*      */     }
/*      */ 
/*  240 */     return jjStartNfa_0(7, active0);
/*      */   }
/*      */ 
/*      */   private int jjMoveNfa_0(int startState, int curPos)
/*      */   {
/*  247 */     int startsAt = 0;
/*  248 */     this.jjnewStateCnt = 82;
/*  249 */     int i = 1;
/*  250 */     this.jjstateSet[0] = startState;
/*  251 */     int kind = 2147483647;
/*      */     while (true)
/*      */     {
/*  254 */       if (++this.jjround == 2147483647)
/*  255 */         ReInitRounds();
/*  256 */       if (this.curChar < '@')
/*      */       {
/*  258 */         long l = 1L << this.curChar;
/*      */         do
/*      */         {
/*  261 */           switch (this.jjstateSet[(--i)])
/*      */           {
/*      */           case 0:
/*  264 */             if ((0x0 & l) != 0L)
/*      */             {
/*  266 */               if (kind > 12)
/*  267 */                 kind = 12;
/*  268 */               jjCheckNAddTwoStates(32, 33);
/*      */             }
/*  270 */             else if ((0x1201 & l) != 0L)
/*      */             {
/*  272 */               if (kind > 2)
/*  273 */                 kind = 2;
/*      */             }
/*  275 */             else if ((0x2400 & l) != 0L)
/*      */             {
/*  277 */               if (kind > 3)
/*  278 */                 kind = 3;
/*  279 */               jjCheckNAddStates(0, 2);
/*      */             }
/*  281 */             else if ((0x0 & l) != 0L) {
/*  282 */               jjCheckNAddTwoStates(32, 35);
/*  283 */             } else if (this.curChar == '<') {
/*  284 */               jjCheckNAddTwoStates(57, 59);
/*  285 */             } else if (this.curChar == '/') {
/*  286 */               jjCheckNAdd(40);
/*  287 */             } else if (this.curChar == '(')
/*      */             {
/*  289 */               if (kind > 14)
/*  290 */                 kind = 14;
/*  291 */               jjCheckNAdd(38);
/*      */             }
/*  293 */             else if (this.curChar == '.') {
/*  294 */               jjCheckNAdd(36);
/*  295 */             }if ((0x0 & l) != 0L)
/*  296 */               jjCheckNAddStates(3, 6);
/*  297 */             else if (this.curChar == '\r')
/*  298 */               jjCheckNAddTwoStates(55, 43);
/*  299 */             else if (this.curChar == '<')
/*  300 */               this.jjstateSet[(this.jjnewStateCnt++)] = 10; break;
/*      */           case 10:
/*  303 */             if ((0x0 & l) != 0L)
/*  304 */               this.jjstateSet[(this.jjnewStateCnt++)] = 60;
/*  305 */             else if (this.curChar == '/')
/*  306 */               this.jjstateSet[(this.jjnewStateCnt++)] = 11; break;
/*      */           case 1:
/*  309 */             if (this.curChar == '.')
/*  310 */               this.jjstateSet[(this.jjnewStateCnt++)] = 2; break;
/*      */           case 2:
/*  313 */             if (((0x0 & l) != 0L) && (kind > 5))
/*  314 */               kind = 5; break;
/*      */           case 3:
/*  317 */             if (this.curChar == '1')
/*  318 */               this.jjstateSet[(this.jjnewStateCnt++)] = 1; break;
/*      */           case 4:
/*  321 */             if (this.curChar == '-')
/*  322 */               this.jjstateSet[(this.jjnewStateCnt++)] = 3; break;
/*      */           case 12:
/*  325 */             if ((this.curChar == '>') && (kind > 8))
/*  326 */               kind = 8; break;
/*      */           case 13:
/*  329 */             if (this.curChar == '<')
/*  330 */               this.jjstateSet[(this.jjnewStateCnt++)] = 10; break;
/*      */           case 15:
/*      */           case 16:
/*  334 */             if ((this.curChar == '\n') && (kind > 10))
/*  335 */               kind = 10; break;
/*      */           case 17:
/*  338 */             if (this.curChar == '\r')
/*  339 */               this.jjstateSet[(this.jjnewStateCnt++)] = 16; break;
/*      */           case 31:
/*  342 */             if ((0x0 & l) != 0L)
/*  343 */               jjCheckNAddTwoStates(32, 35); break;
/*      */           case 32:
/*  346 */             if ((0x0 & l) != 0L)
/*      */             {
/*  348 */               if (kind > 12)
/*  349 */                 kind = 12;
/*  350 */               jjCheckNAddTwoStates(32, 33);
/*  351 */             }break;
/*      */           case 33:
/*  353 */             if (this.curChar == '.')
/*      */             {
/*  355 */               if (kind > 12)
/*  356 */                 kind = 12;
/*  357 */               jjCheckNAdd(34);
/*  358 */             }break;
/*      */           case 34:
/*  360 */             if ((0x0 & l) != 0L)
/*      */             {
/*  362 */               if (kind > 12)
/*  363 */                 kind = 12;
/*  364 */               jjCheckNAdd(34);
/*  365 */             }break;
/*      */           case 35:
/*  367 */             if (this.curChar == '.')
/*  368 */               jjCheckNAdd(36); break;
/*      */           case 36:
/*  371 */             if ((0x0 & l) != 0L)
/*      */             {
/*  373 */               if (kind > 12)
/*  374 */                 kind = 12;
/*  375 */               jjCheckNAdd(36);
/*  376 */             }break;
/*      */           case 37:
/*  378 */             if (this.curChar == '(')
/*      */             {
/*  380 */               kind = 14;
/*  381 */               jjCheckNAdd(38);
/*  382 */             }break;
/*      */           case 38:
/*  384 */             if ((0xFFFFFFFF & l) != 0L)
/*      */             {
/*  386 */               if (kind > 14)
/*  387 */                 kind = 14;
/*  388 */               jjCheckNAdd(38);
/*  389 */             }break;
/*      */           case 39:
/*  391 */             if (this.curChar == '/')
/*  392 */               jjCheckNAdd(40); break;
/*      */           case 40:
/*  395 */             if ((0xFFFFD9FF & l) != 0L)
/*      */             {
/*  397 */               if (kind > 17)
/*  398 */                 kind = 17;
/*  399 */               jjCheckNAdd(40);
/*  400 */             }break;
/*      */           case 41:
/*  402 */             if ((0x2400 & l) != 0L)
/*      */             {
/*  404 */               if (kind > 3)
/*  405 */                 kind = 3;
/*  406 */               jjCheckNAddStates(0, 2);
/*  407 */             }break;
/*      */           case 42:
/*  409 */             if ((0x2400 & l) != 0L)
/*  410 */               jjCheckNAddStates(0, 2); break;
/*      */           case 43:
/*  413 */             if (this.curChar == '\n')
/*  414 */               jjCheckNAddStates(0, 2); break;
/*      */           case 44:
/*  417 */             if (this.curChar == '\r')
/*  418 */               jjCheckNAdd(43); break;
/*      */           case 46:
/*  421 */             if (((0x2400 & l) != 0L) && (kind > 9))
/*  422 */               kind = 9; break;
/*      */           case 47:
/*  425 */             if ((this.curChar == '\n') && (kind > 9))
/*  426 */               kind = 9; break;
/*      */           case 48:
/*  429 */             if (this.curChar == '\r')
/*  430 */               this.jjstateSet[(this.jjnewStateCnt++)] = 47; break;
/*      */           case 54:
/*  433 */             if (this.curChar == '\r')
/*  434 */               jjCheckNAddTwoStates(55, 43); break;
/*      */           case 55:
/*  437 */             if ((this.curChar == '\n') && (kind > 3))
/*  438 */               kind = 3; break;
/*      */           case 56:
/*  441 */             if (this.curChar == '<')
/*  442 */               jjCheckNAddTwoStates(57, 59); break;
/*      */           case 58:
/*  445 */             if ((this.curChar == '>') && (kind > 7))
/*  446 */               kind = 7; break;
/*      */           case 59:
/*  449 */             if ((0x0 & l) != 0L)
/*  450 */               this.jjstateSet[(this.jjnewStateCnt++)] = 60; break;
/*      */           case 60:
/*  453 */             if ((0x0 & l) != 0L)
/*  454 */               jjCheckNAddTwoStates(59, 61); break;
/*      */           case 61:
/*  457 */             if ((this.curChar == '>') && (kind > 13))
/*  458 */               kind = 13; break;
/*      */           case 62:
/*  461 */             if ((0x0 & l) != 0L)
/*  462 */               jjCheckNAddStates(3, 6); break;
/*      */           case 63:
/*  465 */             if ((0x0 & l) != 0L)
/*  466 */               jjCheckNAddTwoStates(63, 64); break;
/*      */           case 64:
/*  469 */             if ((0x1201 & l) != 0L)
/*  470 */               jjAddStates(7, 8); break;
/*      */           case 65:
/*  473 */             if (this.curChar == '0')
/*  474 */               jjCheckNAdd(66); break;
/*      */           case 66:
/*  477 */             if ((0x1201 & l) != 0L)
/*  478 */               this.jjstateSet[(this.jjnewStateCnt++)] = 67; break;
/*      */           case 68:
/*  481 */             if ((0x0 & l) != 0L)
/*  482 */               jjCheckNAdd(69); break;
/*      */           case 69:
/*  485 */             if ((0x0 & l) != 0L)
/*  486 */               jjCheckNAddTwoStates(69, 66); break;
/*      */           case 70:
/*  489 */             if ((0x0 & l) != 0L)
/*  490 */               jjCheckNAddTwoStates(70, 71); break;
/*      */           case 71:
/*  493 */             if ((0x1201 & l) != 0L)
/*  494 */               jjAddStates(9, 10); break;
/*      */           case 72:
/*  497 */             if (this.curChar == '0')
/*  498 */               jjCheckNAdd(73); break;
/*      */           case 73:
/*  501 */             if ((0x1201 & l) != 0L)
/*  502 */               this.jjstateSet[(this.jjnewStateCnt++)] = 79; break;
/*      */           case 75:
/*  505 */             if (((0x2400 & l) != 0L) && (kind > 20))
/*  506 */               kind = 20; break;
/*      */           case 76:
/*  509 */             if ((this.curChar == '\n') && (kind > 20))
/*  510 */               kind = 20; break;
/*      */           case 77:
/*  513 */             if (this.curChar == '\r')
/*  514 */               this.jjstateSet[(this.jjnewStateCnt++)] = 76; break;
/*      */           case 80:
/*  517 */             if ((0x0 & l) != 0L)
/*  518 */               jjCheckNAdd(81); break;
/*      */           case 81:
/*  521 */             if ((0x0 & l) != 0L)
/*  522 */               jjCheckNAddTwoStates(81, 73); break;
/*      */           case 5:
/*      */           case 6:
/*      */           case 7:
/*      */           case 8:
/*      */           case 9:
/*      */           case 11:
/*      */           case 14:
/*      */           case 18:
/*      */           case 19:
/*      */           case 20:
/*      */           case 21:
/*      */           case 22:
/*      */           case 23:
/*      */           case 24:
/*      */           case 25:
/*      */           case 26:
/*      */           case 27:
/*      */           case 28:
/*      */           case 29:
/*      */           case 30:
/*      */           case 45:
/*      */           case 49:
/*      */           case 50:
/*      */           case 51:
/*      */           case 52:
/*      */           case 53:
/*      */           case 57:
/*      */           case 67:
/*      */           case 74:
/*      */           case 78:
/*  526 */           case 79: }  } while (i != startsAt);
/*      */       }
/*  528 */       else if (this.curChar < '')
/*      */       {
/*  530 */         long l = 1L << (this.curChar & 0x3F);
/*      */         do
/*      */         {
/*  533 */           switch (this.jjstateSet[(--i)])
/*      */           {
/*      */           case 0:
/*  536 */             if (this.curChar == 'f')
/*  537 */               this.jjstateSet[(this.jjnewStateCnt++)] = 29;
/*  538 */             else if (this.curChar == 't')
/*  539 */               this.jjstateSet[(this.jjnewStateCnt++)] = 25;
/*  540 */             else if (this.curChar == 's')
/*  541 */               this.jjstateSet[(this.jjnewStateCnt++)] = 21;
/*  542 */             else if (this.curChar == 'P')
/*  543 */               this.jjstateSet[(this.jjnewStateCnt++)] = 6; break;
/*      */           case 10:
/*  546 */             if ((0x7FFFFFE & l) != 0L)
/*  547 */               jjCheckNAddTwoStates(57, 58);
/*  548 */             if ((0x7E & l) != 0L)
/*  549 */               this.jjstateSet[(this.jjnewStateCnt++)] = 60; break;
/*      */           case 5:
/*  552 */             if (this.curChar == 'F')
/*  553 */               this.jjstateSet[(this.jjnewStateCnt++)] = 4; break;
/*      */           case 6:
/*  556 */             if (this.curChar == 'D')
/*  557 */               this.jjstateSet[(this.jjnewStateCnt++)] = 5; break;
/*      */           case 7:
/*  560 */             if (this.curChar == 'P')
/*  561 */               this.jjstateSet[(this.jjnewStateCnt++)] = 6; break;
/*      */           case 11:
/*  564 */             if ((0x7FFFFFE & l) != 0L)
/*  565 */               jjAddStates(11, 12); break;
/*      */           case 14:
/*  568 */             if (this.curChar == 'm')
/*  569 */               jjAddStates(13, 14); break;
/*      */           case 18:
/*  572 */             if (this.curChar == 'a')
/*  573 */               this.jjstateSet[(this.jjnewStateCnt++)] = 14; break;
/*      */           case 19:
/*  576 */             if (this.curChar == 'e')
/*  577 */               this.jjstateSet[(this.jjnewStateCnt++)] = 18; break;
/*      */           case 20:
/*  580 */             if (this.curChar == 'r')
/*  581 */               this.jjstateSet[(this.jjnewStateCnt++)] = 19; break;
/*      */           case 21:
/*  584 */             if (this.curChar == 't')
/*  585 */               this.jjstateSet[(this.jjnewStateCnt++)] = 20; break;
/*      */           case 22:
/*  588 */             if (this.curChar == 's')
/*  589 */               this.jjstateSet[(this.jjnewStateCnt++)] = 21; break;
/*      */           case 23:
/*  592 */             if ((this.curChar == 'e') && (kind > 11))
/*  593 */               kind = 11; break;
/*      */           case 24:
/*  596 */             if (this.curChar == 'u')
/*  597 */               jjCheckNAdd(23); break;
/*      */           case 25:
/*  600 */             if (this.curChar == 'r')
/*  601 */               this.jjstateSet[(this.jjnewStateCnt++)] = 24; break;
/*      */           case 26:
/*  604 */             if (this.curChar == 't')
/*  605 */               this.jjstateSet[(this.jjnewStateCnt++)] = 25; break;
/*      */           case 27:
/*  608 */             if (this.curChar == 's')
/*  609 */               jjCheckNAdd(23); break;
/*      */           case 28:
/*  612 */             if (this.curChar == 'l')
/*  613 */               this.jjstateSet[(this.jjnewStateCnt++)] = 27; break;
/*      */           case 29:
/*  616 */             if (this.curChar == 'a')
/*  617 */               this.jjstateSet[(this.jjnewStateCnt++)] = 28; break;
/*      */           case 30:
/*  620 */             if (this.curChar == 'f')
/*  621 */               this.jjstateSet[(this.jjnewStateCnt++)] = 29; break;
/*      */           case 38:
/*  624 */             if (kind > 14)
/*  625 */               kind = 14;
/*  626 */             this.jjstateSet[(this.jjnewStateCnt++)] = 38;
/*  627 */             break;
/*      */           case 40:
/*  629 */             if ((0xD7FFFFFF & l) != 0L)
/*      */             {
/*  631 */               if (kind > 17)
/*  632 */                 kind = 17;
/*  633 */               this.jjstateSet[(this.jjnewStateCnt++)] = 40;
/*  634 */             }break;
/*      */           case 45:
/*  636 */             if (this.curChar == 'j')
/*  637 */               jjAddStates(15, 16); break;
/*      */           case 49:
/*  640 */             if (this.curChar == 'b')
/*  641 */               this.jjstateSet[(this.jjnewStateCnt++)] = 45; break;
/*      */           case 50:
/*  644 */             if (this.curChar == 'o')
/*  645 */               this.jjstateSet[(this.jjnewStateCnt++)] = 49; break;
/*      */           case 51:
/*  648 */             if (this.curChar == 'd')
/*  649 */               this.jjstateSet[(this.jjnewStateCnt++)] = 50; break;
/*      */           case 52:
/*  652 */             if (this.curChar == 'n')
/*  653 */               this.jjstateSet[(this.jjnewStateCnt++)] = 51; break;
/*      */           case 53:
/*  656 */             if (this.curChar == 'e')
/*  657 */               this.jjstateSet[(this.jjnewStateCnt++)] = 52; break;
/*      */           case 57:
/*  660 */             if ((0x7FFFFFE & l) != 0L)
/*  661 */               jjCheckNAddTwoStates(57, 58); break;
/*      */           case 59:
/*  664 */             if ((0x7E & l) != 0L)
/*  665 */               this.jjstateSet[(this.jjnewStateCnt++)] = 60; break;
/*      */           case 60:
/*  668 */             if ((0x7E & l) != 0L)
/*  669 */               jjAddStates(17, 18); break;
/*      */           case 67:
/*  672 */             if ((this.curChar == 'R') && (kind > 19))
/*  673 */               kind = 19; break;
/*      */           case 74:
/*  676 */             if (this.curChar == 'j')
/*  677 */               jjAddStates(19, 20); break;
/*      */           case 78:
/*  680 */             if (this.curChar == 'b')
/*  681 */               this.jjstateSet[(this.jjnewStateCnt++)] = 74; break;
/*      */           case 79:
/*  684 */             if (this.curChar == 'o')
/*  685 */               this.jjstateSet[(this.jjnewStateCnt++)] = 78; break;
/*      */           case 1:
/*      */           case 2:
/*      */           case 3:
/*      */           case 4:
/*      */           case 8:
/*      */           case 9:
/*      */           case 12:
/*      */           case 13:
/*      */           case 15:
/*      */           case 16:
/*      */           case 17:
/*      */           case 31:
/*      */           case 32:
/*      */           case 33:
/*      */           case 34:
/*      */           case 35:
/*      */           case 36:
/*      */           case 37:
/*      */           case 39:
/*      */           case 41:
/*      */           case 42:
/*      */           case 43:
/*      */           case 44:
/*      */           case 46:
/*      */           case 47:
/*      */           case 48:
/*      */           case 54:
/*      */           case 55:
/*      */           case 56:
/*      */           case 58:
/*      */           case 61:
/*      */           case 62:
/*      */           case 63:
/*      */           case 64:
/*      */           case 65:
/*      */           case 66:
/*      */           case 68:
/*      */           case 69:
/*      */           case 70:
/*      */           case 71:
/*      */           case 72:
/*      */           case 73:
/*      */           case 75:
/*      */           case 76:
/*  689 */           case 77: }  } while (i != startsAt);
/*      */       }
/*      */       else
/*      */       {
/*  693 */         int i2 = (this.curChar & 0xFF) >> '\006';
/*  694 */         long l2 = 1L << (this.curChar & 0x3F);
/*      */         do
/*      */         {
/*  697 */           switch (this.jjstateSet[(--i)])
/*      */           {
/*      */           case 0:
/*  700 */             if ((jjbitVec0[i2] & l2) != 0L)
/*  701 */               jjCheckNAdd(9); break;
/*      */           case 9:
/*  704 */             if ((jjbitVec0[i2] & l2) != 0L)
/*      */             {
/*  706 */               if (kind > 6)
/*  707 */                 kind = 6;
/*  708 */               jjCheckNAdd(9);
/*  709 */             }break;
/*      */           case 38:
/*  711 */             if ((jjbitVec0[i2] & l2) != 0L)
/*      */             {
/*  713 */               if (kind > 14)
/*  714 */                 kind = 14;
/*  715 */               this.jjstateSet[(this.jjnewStateCnt++)] = 38;
/*  716 */             }break;
/*      */           case 40:
/*  718 */             if ((jjbitVec0[i2] & l2) != 0L)
/*      */             {
/*  720 */               if (kind > 17)
/*  721 */                 kind = 17;
/*  722 */               this.jjstateSet[(this.jjnewStateCnt++)] = 40;
/*      */             }break;
/*      */           }
/*      */         }
/*  726 */         while (i != startsAt);
/*      */       }
/*  728 */       if (kind != 2147483647)
/*      */       {
/*  730 */         this.jjmatchedKind = kind;
/*  731 */         this.jjmatchedPos = curPos;
/*  732 */         kind = 2147483647;
/*      */       }
/*  734 */       curPos++;
/*  735 */       if ((i = this.jjnewStateCnt) == (startsAt = 82 - (this.jjnewStateCnt = startsAt)))
/*  736 */         return curPos; try {
/*  737 */         this.curChar = this.input_stream.readChar(); } catch (IOException e) {  }
/*  738 */     }return curPos;
/*      */   }
/*      */ 
/*      */   private final int jjStopStringLiteralDfa_1(int pos, long active0)
/*      */   {
/*  743 */     switch (pos)
/*      */     {
/*      */     }
/*  746 */     return -1;
/*      */   }
/*      */ 
/*      */   private final int jjStartNfa_1(int pos, long active0)
/*      */   {
/*  751 */     return jjMoveNfa_1(jjStopStringLiteralDfa_1(pos, active0), pos + 1);
/*      */   }
/*      */ 
/*      */   private int jjMoveStringLiteralDfa0_1() {
/*  755 */     switch (this.curChar)
/*      */     {
/*      */     case ' ':
/*  758 */       return jjStopAtPos(0, 1);
/*      */     case '%':
/*  760 */       return jjMoveStringLiteralDfa1_1(4398046511104L);
/*      */     }
/*  762 */     return jjMoveNfa_1(0, 0);
/*      */   }
/*      */ 
/*      */   private int jjMoveStringLiteralDfa1_1(long active0) {
/*      */     try {
/*  767 */       this.curChar = this.input_stream.readChar();
/*      */     } catch (IOException e) {
/*  769 */       jjStopStringLiteralDfa_1(0, active0);
/*  770 */       return 1;
/*      */     }
/*  772 */     switch (this.curChar)
/*      */     {
/*      */     case '%':
/*  775 */       return jjMoveStringLiteralDfa2_1(active0, 4398046511104L);
/*      */     }
/*      */ 
/*  779 */     return jjStartNfa_1(0, active0);
/*      */   }
/*      */ 
/*      */   private int jjMoveStringLiteralDfa2_1(long old0, long active0) {
/*  783 */     if ((active0 &= old0) == 0L)
/*  784 */       return jjStartNfa_1(0, old0); try {
/*  785 */       this.curChar = this.input_stream.readChar();
/*      */     } catch (IOException e) {
/*  787 */       jjStopStringLiteralDfa_1(1, active0);
/*  788 */       return 2;
/*      */     }
/*  790 */     switch (this.curChar)
/*      */     {
/*      */     case 'E':
/*  793 */       return jjMoveStringLiteralDfa3_1(active0, 4398046511104L);
/*      */     }
/*      */ 
/*  797 */     return jjStartNfa_1(1, active0);
/*      */   }
/*      */ 
/*      */   private int jjMoveStringLiteralDfa3_1(long old0, long active0) {
/*  801 */     if ((active0 &= old0) == 0L)
/*  802 */       return jjStartNfa_1(1, old0); try {
/*  803 */       this.curChar = this.input_stream.readChar();
/*      */     } catch (IOException e) {
/*  805 */       jjStopStringLiteralDfa_1(2, active0);
/*  806 */       return 3;
/*      */     }
/*  808 */     switch (this.curChar)
/*      */     {
/*      */     case 'O':
/*  811 */       return jjMoveStringLiteralDfa4_1(active0, 4398046511104L);
/*      */     }
/*      */ 
/*  815 */     return jjStartNfa_1(2, active0);
/*      */   }
/*      */ 
/*      */   private int jjMoveStringLiteralDfa4_1(long old0, long active0) {
/*  819 */     if ((active0 &= old0) == 0L)
/*  820 */       return jjStartNfa_1(2, old0); try {
/*  821 */       this.curChar = this.input_stream.readChar();
/*      */     } catch (IOException e) {
/*  823 */       jjStopStringLiteralDfa_1(3, active0);
/*  824 */       return 4;
/*      */     }
/*  826 */     switch (this.curChar)
/*      */     {
/*      */     case 'F':
/*  829 */       if ((active0 & 0x0) != 0L) {
/*  830 */         return jjStopAtPos(4, 42);
/*      */       }
/*      */       break;
/*      */     }
/*      */ 
/*  835 */     return jjStartNfa_1(3, active0);
/*      */   }
/*      */ 
/*      */   private int jjMoveNfa_1(int startState, int curPos) {
/*  839 */     int startsAt = 0;
/*  840 */     this.jjnewStateCnt = 5;
/*  841 */     int i = 1;
/*  842 */     this.jjstateSet[0] = startState;
/*  843 */     int kind = 2147483647;
/*      */     while (true)
/*      */     {
/*  846 */       if (++this.jjround == 2147483647)
/*  847 */         ReInitRounds();
/*  848 */       if (this.curChar < '@')
/*      */       {
/*  850 */         long l = 1L << this.curChar;
/*      */         do
/*      */         {
/*  853 */           switch (this.jjstateSet[(--i)])
/*      */           {
/*      */           case 0:
/*  856 */             if ((0x0 & l) != 0L)
/*      */             {
/*  858 */               if (kind > 41)
/*  859 */                 kind = 41;
/*  860 */               jjCheckNAdd(4);
/*      */             }
/*  862 */             else if ((0x1201 & l) != 0L)
/*      */             {
/*  864 */               if (kind > 2)
/*  865 */                 kind = 2;
/*      */             }
/*  867 */             else if ((0x2400 & l) != 0L)
/*      */             {
/*  869 */               if (kind > 3)
/*  870 */                 kind = 3;
/*      */             }
/*  872 */             if (this.curChar == '\r')
/*  873 */               this.jjstateSet[(this.jjnewStateCnt++)] = 2; break;
/*      */           case 1:
/*  876 */             if (((0x2400 & l) != 0L) && (kind > 3))
/*  877 */               kind = 3; break;
/*      */           case 2:
/*  880 */             if ((this.curChar == '\n') && (kind > 3))
/*  881 */               kind = 3; break;
/*      */           case 3:
/*  884 */             if (this.curChar == '\r')
/*  885 */               this.jjstateSet[(this.jjnewStateCnt++)] = 2; break;
/*      */           case 4:
/*  888 */             if ((0x0 & l) != 0L)
/*      */             {
/*  890 */               kind = 41;
/*  891 */               jjCheckNAdd(4);
/*      */             }break;
/*      */           }
/*      */         }
/*  895 */         while (i != startsAt);
/*      */       }
/*  897 */       else if (this.curChar < '')
/*      */       {
/*  899 */         long l = 1L << (this.curChar & 0x3F);
/*      */         do
/*      */         {
/*  902 */           switch (this.jjstateSet[(--i)])
/*      */           {
/*      */           }
/*      */         }
/*  906 */         while (i != startsAt);
/*      */       }
/*      */       else
/*      */       {
/*  910 */         int i2 = (this.curChar & 0xFF) >> '\006';
/*  911 */         long l2 = 1L << (this.curChar & 0x3F);
/*      */         do
/*      */         {
/*  914 */           switch (this.jjstateSet[(--i)])
/*      */           {
/*      */           }
/*      */         }
/*  918 */         while (i != startsAt);
/*      */       }
/*  920 */       if (kind != 2147483647)
/*      */       {
/*  922 */         this.jjmatchedKind = kind;
/*  923 */         this.jjmatchedPos = curPos;
/*  924 */         kind = 2147483647;
/*      */       }
/*  926 */       curPos++;
/*  927 */       if ((i = this.jjnewStateCnt) == (startsAt = 5 - (this.jjnewStateCnt = startsAt)))
/*  928 */         return curPos; try {
/*  929 */         this.curChar = this.input_stream.readChar(); } catch (IOException e) {  }
/*  930 */     }return curPos;
/*      */   }
/*      */ 
/*      */   private final int jjStopStringLiteralDfa_3(int pos, long active0)
/*      */   {
/*  935 */     switch (pos)
/*      */     {
/*      */     case 0:
/*  938 */       if ((active0 & 0xC000000) != 0L)
/*      */       {
/*  940 */         this.jjmatchedKind = 24;
/*  941 */         return -1;
/*      */       }
/*  943 */       return -1;
/*      */     }
/*  945 */     return -1;
/*      */   }
/*      */ 
/*      */   private final int jjStartNfa_3(int pos, long active0)
/*      */   {
/*  950 */     return jjMoveNfa_3(jjStopStringLiteralDfa_3(pos, active0), pos + 1);
/*      */   }
/*      */ 
/*      */   private int jjMoveStringLiteralDfa0_3() {
/*  954 */     switch (this.curChar)
/*      */     {
/*      */     case ')':
/*  957 */       return jjStopAtPos(0, 28);
/*      */     case '\\':
/*  959 */       return jjMoveStringLiteralDfa1_3(201326592L);
/*      */     }
/*  961 */     return jjMoveNfa_3(0, 0);
/*      */   }
/*      */ 
/*      */   private int jjMoveStringLiteralDfa1_3(long active0) {
/*      */     try {
/*  966 */       this.curChar = this.input_stream.readChar();
/*      */     } catch (IOException e) {
/*  968 */       jjStopStringLiteralDfa_3(0, active0);
/*  969 */       return 1;
/*      */     }
/*  971 */     switch (this.curChar)
/*      */     {
/*      */     case '(':
/*  974 */       if ((active0 & 0x4000000) != 0L)
/*  975 */         return jjStopAtPos(1, 26);
/*      */       break;
/*      */     case ')':
/*  978 */       if ((active0 & 0x8000000) != 0L) {
/*  979 */         return jjStopAtPos(1, 27);
/*      */       }
/*      */       break;
/*      */     }
/*      */ 
/*  984 */     return jjStartNfa_3(0, active0);
/*      */   }
/*      */ 
/*      */   private int jjMoveNfa_3(int startState, int curPos)
/*      */   {
/*  991 */     int startsAt = 0;
/*  992 */     this.jjnewStateCnt = 4;
/*  993 */     int i = 1;
/*  994 */     this.jjstateSet[0] = startState;
/*  995 */     int kind = 2147483647;
/*      */     while (true)
/*      */     {
/*  998 */       if (++this.jjround == 2147483647)
/*  999 */         ReInitRounds();
/* 1000 */       if (this.curChar < '@')
/*      */       {
/* 1002 */         long l = 1L << this.curChar;
/*      */         do
/*      */         {
/* 1005 */           switch (this.jjstateSet[(--i)])
/*      */           {
/*      */           case 0:
/* 1008 */             if ((0xFFFFFFFF & l) != 0L)
/*      */             {
/* 1010 */               if (kind > 24)
/* 1011 */                 kind = 24;
/*      */             }
/* 1013 */             else if (this.curChar == '(')
/*      */             {
/* 1015 */               if (kind > 29)
/* 1016 */                 kind = 29;
/* 1017 */               jjCheckNAdd(3); } break;
/*      */           case 2:
/* 1021 */             if (this.curChar == '(')
/*      */             {
/* 1023 */               kind = 29;
/* 1024 */               jjCheckNAdd(3);
/* 1025 */             }break;
/*      */           case 3:
/* 1027 */             if ((0xFFFFFFFF & l) != 0L)
/*      */             {
/* 1029 */               if (kind > 29)
/* 1030 */                 kind = 29;
/* 1031 */               jjCheckNAdd(3); } break;
/*      */           case 1:
/*      */           }
/*      */         }
/* 1035 */         while (i != startsAt);
/*      */       }
/* 1037 */       else if (this.curChar < '')
/*      */       {
/* 1039 */         long l = 1L << (this.curChar & 0x3F);
/*      */         do
/*      */         {
/* 1042 */           switch (this.jjstateSet[(--i)])
/*      */           {
/*      */           case 0:
/* 1045 */             if (kind > 24)
/* 1046 */               kind = 24; break;
/*      */           case 3:
/* 1049 */             if (kind > 29)
/* 1050 */               kind = 29;
/* 1051 */             this.jjstateSet[(this.jjnewStateCnt++)] = 3;
/*      */           }
/*      */         }
/*      */ 
/* 1055 */         while (i != startsAt);
/*      */       }
/*      */       else
/*      */       {
/* 1059 */         int hiByte = this.curChar >> '\b';
/* 1060 */         int i1 = hiByte >> 6;
/* 1061 */         long l1 = 1L << (hiByte & 0x3F);
/* 1062 */         int i2 = (this.curChar & 0xFF) >> '\006';
/* 1063 */         long l2 = 1L << (this.curChar & 0x3F);
/*      */         do
/*      */         {
/* 1066 */           switch (this.jjstateSet[(--i)])
/*      */           {
/*      */           case 0:
/* 1069 */             if (jjCanMove_0(hiByte, i1, i2, l1, l2))
/*      */             {
/* 1071 */               if (kind > 24)
/* 1072 */                 kind = 24;
/*      */             }
/* 1074 */             if (jjCanMove_1(hiByte, i1, i2, l1, l2))
/*      */             {
/* 1076 */               if (kind > 25)
/* 1077 */                 kind = 25;  } break;
/*      */           case 1:
/* 1081 */             if ((jjCanMove_1(hiByte, i1, i2, l1, l2)) && (kind > 25))
/* 1082 */               kind = 25; break;
/*      */           case 3:
/* 1085 */             if (jjCanMove_1(hiByte, i1, i2, l1, l2))
/*      */             {
/* 1087 */               if (kind > 29)
/* 1088 */                 kind = 29;
/* 1089 */               this.jjstateSet[(this.jjnewStateCnt++)] = 3; } break;
/*      */           case 2:
/*      */           }
/*      */         }
/* 1093 */         while (i != startsAt);
/*      */       }
/* 1095 */       if (kind != 2147483647)
/*      */       {
/* 1097 */         this.jjmatchedKind = kind;
/* 1098 */         this.jjmatchedPos = curPos;
/* 1099 */         kind = 2147483647;
/*      */       }
/* 1101 */       curPos++;
/* 1102 */       if ((i = this.jjnewStateCnt) == (startsAt = 4 - (this.jjnewStateCnt = startsAt)))
/* 1103 */         return curPos; try {
/* 1104 */         this.curChar = this.input_stream.readChar(); } catch (IOException e) {  }
/* 1105 */     }return curPos;
/*      */   }
/*      */ 
/*      */   private final int jjStopStringLiteralDfa_2(int pos, long active0)
/*      */   {
/* 1110 */     switch (pos)
/*      */     {
/*      */     }
/* 1113 */     return -1;
/*      */   }
/*      */ 
/*      */   private final int jjStartNfa_2(int pos, long active0)
/*      */   {
/* 1118 */     return jjMoveNfa_2(jjStopStringLiteralDfa_2(pos, active0), pos + 1);
/*      */   }
/*      */ 
/*      */   private int jjMoveStringLiteralDfa0_2() {
/* 1122 */     switch (this.curChar)
/*      */     {
/*      */     case ' ':
/* 1125 */       return jjStopAtPos(0, 1);
/*      */     case 't':
/* 1127 */       return jjMoveStringLiteralDfa1_2(137438953472L);
/*      */     }
/* 1129 */     return jjMoveNfa_2(0, 0);
/*      */   }
/*      */ 
/*      */   private int jjMoveStringLiteralDfa1_2(long active0) {
/*      */     try {
/* 1134 */       this.curChar = this.input_stream.readChar();
/*      */     } catch (IOException e) {
/* 1136 */       jjStopStringLiteralDfa_2(0, active0);
/* 1137 */       return 1;
/*      */     }
/* 1139 */     switch (this.curChar)
/*      */     {
/*      */     case 'r':
/* 1142 */       return jjMoveStringLiteralDfa2_2(active0, 137438953472L);
/*      */     }
/*      */ 
/* 1146 */     return jjStartNfa_2(0, active0);
/*      */   }
/*      */ 
/*      */   private int jjMoveStringLiteralDfa2_2(long old0, long active0) {
/* 1150 */     if ((active0 &= old0) == 0L)
/* 1151 */       return jjStartNfa_2(0, old0); try {
/* 1152 */       this.curChar = this.input_stream.readChar();
/*      */     } catch (IOException e) {
/* 1154 */       jjStopStringLiteralDfa_2(1, active0);
/* 1155 */       return 2;
/*      */     }
/* 1157 */     switch (this.curChar)
/*      */     {
/*      */     case 'a':
/* 1160 */       return jjMoveStringLiteralDfa3_2(active0, 137438953472L);
/*      */     }
/*      */ 
/* 1164 */     return jjStartNfa_2(1, active0);
/*      */   }
/*      */ 
/*      */   private int jjMoveStringLiteralDfa3_2(long old0, long active0) {
/* 1168 */     if ((active0 &= old0) == 0L)
/* 1169 */       return jjStartNfa_2(1, old0); try {
/* 1170 */       this.curChar = this.input_stream.readChar();
/*      */     } catch (IOException e) {
/* 1172 */       jjStopStringLiteralDfa_2(2, active0);
/* 1173 */       return 3;
/*      */     }
/* 1175 */     switch (this.curChar)
/*      */     {
/*      */     case 'i':
/* 1178 */       return jjMoveStringLiteralDfa4_2(active0, 137438953472L);
/*      */     }
/*      */ 
/* 1182 */     return jjStartNfa_2(2, active0);
/*      */   }
/*      */ 
/*      */   private int jjMoveStringLiteralDfa4_2(long old0, long active0) {
/* 1186 */     if ((active0 &= old0) == 0L)
/* 1187 */       return jjStartNfa_2(2, old0); try {
/* 1188 */       this.curChar = this.input_stream.readChar();
/*      */     } catch (IOException e) {
/* 1190 */       jjStopStringLiteralDfa_2(3, active0);
/* 1191 */       return 4;
/*      */     }
/* 1193 */     switch (this.curChar)
/*      */     {
/*      */     case 'l':
/* 1196 */       return jjMoveStringLiteralDfa5_2(active0, 137438953472L);
/*      */     }
/*      */ 
/* 1200 */     return jjStartNfa_2(3, active0);
/*      */   }
/*      */ 
/*      */   private int jjMoveStringLiteralDfa5_2(long old0, long active0) {
/* 1204 */     if ((active0 &= old0) == 0L)
/* 1205 */       return jjStartNfa_2(3, old0); try {
/* 1206 */       this.curChar = this.input_stream.readChar();
/*      */     } catch (IOException e) {
/* 1208 */       jjStopStringLiteralDfa_2(4, active0);
/* 1209 */       return 5;
/*      */     }
/* 1211 */     switch (this.curChar)
/*      */     {
/*      */     case 'e':
/* 1214 */       return jjMoveStringLiteralDfa6_2(active0, 137438953472L);
/*      */     }
/*      */ 
/* 1218 */     return jjStartNfa_2(4, active0);
/*      */   }
/*      */ 
/*      */   private int jjMoveStringLiteralDfa6_2(long old0, long active0) {
/* 1222 */     if ((active0 &= old0) == 0L)
/* 1223 */       return jjStartNfa_2(4, old0); try {
/* 1224 */       this.curChar = this.input_stream.readChar();
/*      */     } catch (IOException e) {
/* 1226 */       jjStopStringLiteralDfa_2(5, active0);
/* 1227 */       return 6;
/*      */     }
/* 1229 */     switch (this.curChar)
/*      */     {
/*      */     case 'r':
/* 1232 */       if ((active0 & 0x0) != 0L) {
/* 1233 */         return jjStopAtPos(6, 37);
/*      */       }
/*      */       break;
/*      */     }
/*      */ 
/* 1238 */     return jjStartNfa_2(5, active0);
/*      */   }
/*      */ 
/*      */   private int jjMoveNfa_2(int startState, int curPos) {
/* 1242 */     int startsAt = 0;
/* 1243 */     this.jjnewStateCnt = 26;
/* 1244 */     int i = 1;
/* 1245 */     this.jjstateSet[0] = startState;
/* 1246 */     int kind = 2147483647;
/*      */     while (true)
/*      */     {
/* 1249 */       if (++this.jjround == 2147483647)
/* 1250 */         ReInitRounds();
/* 1251 */       if (this.curChar < '@')
/*      */       {
/* 1253 */         long l = 1L << this.curChar;
/*      */         do
/*      */         {
/* 1256 */           switch (this.jjstateSet[(--i)])
/*      */           {
/*      */           case 0:
/* 1259 */             if ((0x0 & l) != 0L)
/* 1260 */               jjCheckNAddStates(21, 23);
/* 1261 */             else if ((0x1201 & l) != 0L)
/*      */             {
/* 1263 */               if (kind > 2)
/* 1264 */                 kind = 2;
/*      */             }
/* 1266 */             else if ((0x2400 & l) != 0L)
/*      */             {
/* 1268 */               if (kind > 3)
/* 1269 */                 kind = 3;
/*      */             }
/* 1271 */             if (this.curChar == '\r')
/* 1272 */               this.jjstateSet[(this.jjnewStateCnt++)] = 2; break;
/*      */           case 1:
/* 1275 */             if (((0x2400 & l) != 0L) && (kind > 3))
/* 1276 */               kind = 3; break;
/*      */           case 2:
/* 1279 */             if ((this.curChar == '\n') && (kind > 3))
/* 1280 */               kind = 3; break;
/*      */           case 3:
/* 1283 */             if (this.curChar == '\r')
/* 1284 */               this.jjstateSet[(this.jjnewStateCnt++)] = 2; break;
/*      */           case 4:
/* 1287 */             if ((0x0 & l) != 0L)
/* 1288 */               jjCheckNAddStates(21, 23); break;
/*      */           case 5:
/* 1291 */             if ((0x0 & l) != 0L)
/* 1292 */               this.jjstateSet[(this.jjnewStateCnt++)] = 6; break;
/*      */           case 6:
/* 1295 */             if ((0x0 & l) != 0L)
/* 1296 */               this.jjstateSet[(this.jjnewStateCnt++)] = 7; break;
/*      */           case 7:
/* 1299 */             if ((0x0 & l) != 0L)
/* 1300 */               this.jjstateSet[(this.jjnewStateCnt++)] = 8; break;
/*      */           case 8:
/* 1303 */             if ((0x0 & l) != 0L)
/* 1304 */               this.jjstateSet[(this.jjnewStateCnt++)] = 9; break;
/*      */           case 9:
/* 1307 */             if ((0x0 & l) != 0L)
/* 1308 */               this.jjstateSet[(this.jjnewStateCnt++)] = 10; break;
/*      */           case 10:
/* 1311 */             if ((0x0 & l) != 0L)
/* 1312 */               this.jjstateSet[(this.jjnewStateCnt++)] = 11; break;
/*      */           case 11:
/* 1315 */             if ((0x0 & l) != 0L)
/* 1316 */               this.jjstateSet[(this.jjnewStateCnt++)] = 12; break;
/*      */           case 12:
/* 1319 */             if ((0x0 & l) != 0L)
/* 1320 */               this.jjstateSet[(this.jjnewStateCnt++)] = 13; break;
/*      */           case 13:
/* 1323 */             if ((0x0 & l) != 0L)
/* 1324 */               this.jjstateSet[(this.jjnewStateCnt++)] = 14; break;
/*      */           case 14:
/* 1327 */             if (this.curChar == ' ')
/* 1328 */               this.jjstateSet[(this.jjnewStateCnt++)] = 15; break;
/*      */           case 15:
/* 1331 */             if ((0x0 & l) != 0L)
/* 1332 */               this.jjstateSet[(this.jjnewStateCnt++)] = 16; break;
/*      */           case 16:
/* 1335 */             if ((0x0 & l) != 0L)
/* 1336 */               this.jjstateSet[(this.jjnewStateCnt++)] = 17; break;
/*      */           case 17:
/* 1339 */             if ((0x0 & l) != 0L)
/* 1340 */               this.jjstateSet[(this.jjnewStateCnt++)] = 18; break;
/*      */           case 18:
/* 1343 */             if ((0x0 & l) != 0L)
/* 1344 */               this.jjstateSet[(this.jjnewStateCnt++)] = 19; break;
/*      */           case 19:
/* 1347 */             if ((0x0 & l) != 0L)
/* 1348 */               this.jjstateSet[(this.jjnewStateCnt++)] = 20; break;
/*      */           case 20:
/* 1351 */             if (this.curChar == ' ')
/* 1352 */               this.jjstateSet[(this.jjnewStateCnt++)] = 21; break;
/*      */           case 22:
/* 1355 */             if ((0x0 & l) != 0L)
/* 1356 */               jjCheckNAddTwoStates(22, 23); break;
/*      */           case 23:
/* 1359 */             if (this.curChar == ' ')
/* 1360 */               this.jjstateSet[(this.jjnewStateCnt++)] = 24; break;
/*      */           case 24:
/* 1363 */             if ((0x0 & l) != 0L)
/*      */             {
/* 1365 */               if (kind > 34)
/* 1366 */                 kind = 34;
/* 1367 */               jjCheckNAdd(25);
/* 1368 */             }break;
/*      */           case 25:
/* 1370 */             if ((0x0 & l) != 0L)
/*      */             {
/* 1372 */               if (kind > 34)
/* 1373 */                 kind = 34;
/* 1374 */               jjCheckNAdd(25); } break;
/*      */           case 21:
/*      */           }
/*      */         }
/* 1378 */         while (i != startsAt);
/*      */       }
/* 1380 */       else if (this.curChar < '')
/*      */       {
/* 1382 */         long l = 1L << (this.curChar & 0x3F);
/*      */         do
/*      */         {
/* 1385 */           switch (this.jjstateSet[(--i)])
/*      */           {
/*      */           case 21:
/* 1388 */             if ((0x0 & l) != 0L)
/* 1389 */               kind = 33;
/*      */             break;
/*      */           }
/*      */         }
/* 1393 */         while (i != startsAt);
/*      */       }
/*      */       else
/*      */       {
/* 1397 */         int hiByte = this.curChar >> '\b';
/* 1398 */         int i1 = hiByte >> 6;
/* 1399 */         long l1 = 1L << (hiByte & 0x3F);
/* 1400 */         int i2 = (this.curChar & 0xFF) >> '\006';
/* 1401 */         long l2 = 1L << (this.curChar & 0x3F);
/*      */         do
/*      */         {
/* 1404 */           switch (this.jjstateSet[(--i)])
/*      */           {
/*      */           }
/*      */         }
/* 1408 */         while (i != startsAt);
/*      */       }
/* 1410 */       if (kind != 2147483647)
/*      */       {
/* 1412 */         this.jjmatchedKind = kind;
/* 1413 */         this.jjmatchedPos = curPos;
/* 1414 */         kind = 2147483647;
/*      */       }
/* 1416 */       curPos++;
/* 1417 */       if ((i = this.jjnewStateCnt) == (startsAt = 26 - (this.jjnewStateCnt = startsAt)))
/* 1418 */         return curPos; try {
/* 1419 */         this.curChar = this.input_stream.readChar(); } catch (IOException e) {  }
/* 1420 */     }return curPos;
/*      */   }
/*      */ 
/*      */   private int jjMoveStringLiteralDfa0_4()
/*      */   {
/* 1425 */     switch (this.curChar)
/*      */     {
/*      */     case 'e':
/* 1428 */       return jjMoveStringLiteralDfa1_4(2147483648L);
/*      */     }
/* 1430 */     return 1;
/*      */   }
/*      */ 
/*      */   private int jjMoveStringLiteralDfa1_4(long active0) {
/*      */     try {
/* 1435 */       this.curChar = this.input_stream.readChar();
/*      */     } catch (IOException e) {
/* 1437 */       return 1;
/*      */     }
/* 1439 */     switch (this.curChar)
/*      */     {
/*      */     case 'n':
/* 1442 */       return jjMoveStringLiteralDfa2_4(active0, 2147483648L);
/*      */     }
/* 1444 */     return 2;
/*      */   }
/*      */ 
/*      */   private int jjMoveStringLiteralDfa2_4(long old0, long active0)
/*      */   {
/* 1449 */     if ((active0 &= old0) == 0L)
/* 1450 */       return 2; try {
/* 1451 */       this.curChar = this.input_stream.readChar();
/*      */     } catch (IOException e) {
/* 1453 */       return 2;
/*      */     }
/* 1455 */     switch (this.curChar)
/*      */     {
/*      */     case 'd':
/* 1458 */       return jjMoveStringLiteralDfa3_4(active0, 2147483648L);
/*      */     }
/* 1460 */     return 3;
/*      */   }
/*      */ 
/*      */   private int jjMoveStringLiteralDfa3_4(long old0, long active0)
/*      */   {
/* 1465 */     if ((active0 &= old0) == 0L)
/* 1466 */       return 3; try {
/* 1467 */       this.curChar = this.input_stream.readChar();
/*      */     } catch (IOException e) {
/* 1469 */       return 3;
/*      */     }
/* 1471 */     switch (this.curChar)
/*      */     {
/*      */     case 's':
/* 1474 */       return jjMoveStringLiteralDfa4_4(active0, 2147483648L);
/*      */     }
/* 1476 */     return 4;
/*      */   }
/*      */ 
/*      */   private int jjMoveStringLiteralDfa4_4(long old0, long active0)
/*      */   {
/* 1481 */     if ((active0 &= old0) == 0L)
/* 1482 */       return 4; try {
/* 1483 */       this.curChar = this.input_stream.readChar();
/*      */     } catch (IOException e) {
/* 1485 */       return 4;
/*      */     }
/* 1487 */     switch (this.curChar)
/*      */     {
/*      */     case 't':
/* 1490 */       return jjMoveStringLiteralDfa5_4(active0, 2147483648L);
/*      */     }
/* 1492 */     return 5;
/*      */   }
/*      */ 
/*      */   private int jjMoveStringLiteralDfa5_4(long old0, long active0)
/*      */   {
/* 1497 */     if ((active0 &= old0) == 0L)
/* 1498 */       return 5; try {
/* 1499 */       this.curChar = this.input_stream.readChar();
/*      */     } catch (IOException e) {
/* 1501 */       return 5;
/*      */     }
/* 1503 */     switch (this.curChar)
/*      */     {
/*      */     case 'r':
/* 1506 */       return jjMoveStringLiteralDfa6_4(active0, 2147483648L);
/*      */     }
/* 1508 */     return 6;
/*      */   }
/*      */ 
/*      */   private int jjMoveStringLiteralDfa6_4(long old0, long active0)
/*      */   {
/* 1513 */     if ((active0 &= old0) == 0L)
/* 1514 */       return 6; try {
/* 1515 */       this.curChar = this.input_stream.readChar();
/*      */     } catch (IOException e) {
/* 1517 */       return 6;
/*      */     }
/* 1519 */     switch (this.curChar)
/*      */     {
/*      */     case 'e':
/* 1522 */       return jjMoveStringLiteralDfa7_4(active0, 2147483648L);
/*      */     }
/* 1524 */     return 7;
/*      */   }
/*      */ 
/*      */   private int jjMoveStringLiteralDfa7_4(long old0, long active0)
/*      */   {
/* 1529 */     if ((active0 &= old0) == 0L)
/* 1530 */       return 7; try {
/* 1531 */       this.curChar = this.input_stream.readChar();
/*      */     } catch (IOException e) {
/* 1533 */       return 7;
/*      */     }
/* 1535 */     switch (this.curChar)
/*      */     {
/*      */     case 'a':
/* 1538 */       return jjMoveStringLiteralDfa8_4(active0, 2147483648L);
/*      */     }
/* 1540 */     return 8;
/*      */   }
/*      */ 
/*      */   private int jjMoveStringLiteralDfa8_4(long old0, long active0)
/*      */   {
/* 1545 */     if ((active0 &= old0) == 0L)
/* 1546 */       return 8; try {
/* 1547 */       this.curChar = this.input_stream.readChar();
/*      */     } catch (IOException e) {
/* 1549 */       return 8;
/*      */     }
/* 1551 */     switch (this.curChar)
/*      */     {
/*      */     case 'm':
/* 1554 */       if ((active0 & 0x80000000) != 0L)
/* 1555 */         return jjStopAtPos(8, 31);
/*      */       break;
/*      */     default:
/* 1558 */       return 9;
/*      */     }
/* 1560 */     return 9;
/*      */   }
/*      */ 
/*      */   private static final boolean jjCanMove_0(int hiByte, int i1, int i2, long l1, long l2)
/*      */   {
/* 1568 */     switch (hiByte)
/*      */     {
/*      */     case 0:
/* 1571 */       return (jjbitVec0[i2] & l2) != 0L;
/*      */     }
/* 1573 */     return false;
/*      */   }
/*      */ 
/*      */   private static final boolean jjCanMove_1(int hiByte, int i1, int i2, long l1, long l2)
/*      */   {
/* 1578 */     switch (hiByte)
/*      */     {
/*      */     case 0:
/* 1581 */       return (jjbitVec0[i2] & l2) != 0L;
/*      */     }
/* 1583 */     if ((jjbitVec1[i1] & l1) != 0L)
/* 1584 */       return true;
/* 1585 */     return false;
/*      */   }
/*      */ 
/*      */   public PDFParserTokenManager(SimpleCharStream stream)
/*      */   {
/* 1628 */     this.input_stream = stream;
/*      */   }
/*      */ 
/*      */   public PDFParserTokenManager(SimpleCharStream stream, int lexState)
/*      */   {
/* 1633 */     this(stream);
/* 1634 */     SwitchTo(lexState);
/*      */   }
/*      */ 
/*      */   public void ReInit(SimpleCharStream stream)
/*      */   {
/* 1640 */     this.jjmatchedPos = (this.jjnewStateCnt = 0);
/* 1641 */     this.curLexState = this.defaultLexState;
/* 1642 */     this.input_stream = stream;
/* 1643 */     ReInitRounds();
/*      */   }
/*      */ 
/*      */   private void ReInitRounds()
/*      */   {
/* 1648 */     this.jjround = -2147483647;
/* 1649 */     for (int i = 82; i-- > 0; )
/* 1650 */       this.jjrounds[i] = -2147483648;
/*      */   }
/*      */ 
/*      */   public void ReInit(SimpleCharStream stream, int lexState)
/*      */   {
/* 1656 */     ReInit(stream);
/* 1657 */     SwitchTo(lexState);
/*      */   }
/*      */ 
/*      */   public void SwitchTo(int lexState)
/*      */   {
/* 1663 */     if ((lexState >= 5) || (lexState < 0)) {
/* 1664 */       throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", 2);
/*      */     }
/* 1666 */     this.curLexState = lexState;
/*      */   }
/*      */ 
/*      */   protected Token jjFillToken()
/*      */   {
/* 1677 */     String im = jjstrLiteralImages[this.jjmatchedKind];
/* 1678 */     String curTokenImage = im == null ? this.input_stream.GetImage() : im;
/* 1679 */     int beginLine = this.input_stream.getBeginLine();
/* 1680 */     int beginColumn = this.input_stream.getBeginColumn();
/* 1681 */     int endLine = this.input_stream.getEndLine();
/* 1682 */     int endColumn = this.input_stream.getEndColumn();
/* 1683 */     Token t = Token.newToken(this.jjmatchedKind, curTokenImage);
/*      */ 
/* 1685 */     t.beginLine = beginLine;
/* 1686 */     t.endLine = endLine;
/* 1687 */     t.beginColumn = beginColumn;
/* 1688 */     t.endColumn = endColumn;
/*      */ 
/* 1690 */     return t;
/*      */   }
/*      */ 
/*      */   public Token getNextToken()
/*      */   {
/* 1704 */     int curPos = 0;
/*      */     try
/*      */     {
/* 1711 */       this.curChar = this.input_stream.BeginToken();
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/* 1715 */       this.jjmatchedKind = 0;
/* 1716 */       return jjFillToken();
/*      */     }
/*      */ 
/*      */     while (true)
/*      */     {
/* 1722 */       switch (this.curLexState)
/*      */       {
/*      */       case 0:
/* 1725 */         this.jjmatchedKind = 2147483647;
/* 1726 */         this.jjmatchedPos = 0;
/* 1727 */         curPos = jjMoveStringLiteralDfa0_0();
/* 1728 */         break;
/*      */       case 1:
/* 1730 */         this.jjmatchedKind = 2147483647;
/* 1731 */         this.jjmatchedPos = 0;
/* 1732 */         curPos = jjMoveStringLiteralDfa0_1();
/* 1733 */         break;
/*      */       case 2:
/* 1735 */         this.jjmatchedKind = 2147483647;
/* 1736 */         this.jjmatchedPos = 0;
/* 1737 */         curPos = jjMoveStringLiteralDfa0_2();
/* 1738 */         break;
/*      */       case 3:
/* 1740 */         this.jjmatchedKind = 2147483647;
/* 1741 */         this.jjmatchedPos = 0;
/* 1742 */         curPos = jjMoveStringLiteralDfa0_3();
/* 1743 */         break;
/*      */       case 4:
/* 1745 */         this.jjmatchedKind = 2147483647;
/* 1746 */         this.jjmatchedPos = 0;
/* 1747 */         curPos = jjMoveStringLiteralDfa0_4();
/* 1748 */         if ((this.jjmatchedPos == 0) && (this.jjmatchedKind > 30))
/*      */         {
/* 1750 */           this.jjmatchedKind = 30;
/*      */         }
/*      */         break;
/*      */       }
/* 1754 */       if (this.jjmatchedKind != 2147483647)
/*      */       {
/* 1756 */         if (this.jjmatchedPos + 1 < curPos)
/* 1757 */           this.input_stream.backup(curPos - this.jjmatchedPos - 1);
/* 1758 */         if ((jjtoToken[(this.jjmatchedKind >> 6)] & 1L << (this.jjmatchedKind & 0x3F)) != 0L)
/*      */         {
/* 1760 */           Token matchedToken = jjFillToken();
/* 1761 */           if (jjnewLexState[this.jjmatchedKind] != -1)
/* 1762 */             this.curLexState = jjnewLexState[this.jjmatchedKind];
/* 1763 */           return matchedToken;
/*      */         }
/* 1765 */         if ((jjtoSkip[(this.jjmatchedKind >> 6)] & 1L << (this.jjmatchedKind & 0x3F)) != 0L)
/*      */         {
/* 1767 */           if (jjnewLexState[this.jjmatchedKind] == -1) break;
/* 1768 */           this.curLexState = jjnewLexState[this.jjmatchedKind]; break;
/*      */         }
/*      */ 
/* 1771 */         if (jjnewLexState[this.jjmatchedKind] != -1)
/* 1772 */           this.curLexState = jjnewLexState[this.jjmatchedKind];
/* 1773 */         curPos = 0;
/* 1774 */         this.jjmatchedKind = 2147483647;
/*      */         try {
/* 1776 */           this.curChar = this.input_stream.readChar();
/*      */         } catch (IOException e1) {
/*      */         }
/*      */       }
/*      */     }
/* 1781 */     int error_line = this.input_stream.getEndLine();
/* 1782 */     int error_column = this.input_stream.getEndColumn();
/* 1783 */     String error_after = null;
/* 1784 */     boolean EOFSeen = false;
/*      */     try { this.input_stream.readChar(); this.input_stream.backup(1);
/*      */     } catch (IOException e1) {
/* 1787 */       EOFSeen = true;
/* 1788 */       error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
/* 1789 */       if ((this.curChar == '\n') || (this.curChar == '\r')) {
/* 1790 */         error_line++;
/* 1791 */         error_column = 0;
/*      */       }
/*      */       else {
/* 1794 */         error_column++;
/*      */       }
/*      */     }
/* 1796 */     if (!EOFSeen) {
/* 1797 */       this.input_stream.backup(1);
/* 1798 */       error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
/*      */     }
/* 1800 */     throw new TokenMgrError(EOFSeen, this.curLexState, error_line, error_column, error_after, this.curChar, 0);
/*      */   }
/*      */ 
/*      */   private void jjCheckNAdd(int state)
/*      */   {
/* 1807 */     if (this.jjrounds[state] != this.jjround)
/*      */     {
/* 1809 */       this.jjstateSet[(this.jjnewStateCnt++)] = state;
/* 1810 */       this.jjrounds[state] = this.jjround;
/*      */     }
/*      */   }
/*      */ 
/*      */   private void jjAddStates(int start, int end) {
/*      */     do
/* 1816 */       this.jjstateSet[(this.jjnewStateCnt++)] = jjnextStates[start];
/* 1817 */     while (start++ != end);
/*      */   }
/*      */ 
/*      */   private void jjCheckNAddTwoStates(int state1, int state2) {
/* 1821 */     jjCheckNAdd(state1);
/* 1822 */     jjCheckNAdd(state2);
/*      */   }
/*      */ 
/*      */   private void jjCheckNAddStates(int start, int end)
/*      */   {
/*      */     do
/* 1828 */       jjCheckNAdd(jjnextStates[start]);
/* 1829 */     while (start++ != end);
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.javacc.PDFParserTokenManager
 * JD-Core Version:    0.6.2
 */