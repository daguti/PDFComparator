/*      */ package org.antlr.gunit.swingui.parsers;
/*      */ 
/*      */ import org.antlr.runtime.BaseRecognizer;
/*      */ import org.antlr.runtime.CharStream;
/*      */ import org.antlr.runtime.DFA;
/*      */ import org.antlr.runtime.EarlyExitException;
/*      */ import org.antlr.runtime.IntStream;
/*      */ import org.antlr.runtime.Lexer;
/*      */ import org.antlr.runtime.MismatchedSetException;
/*      */ import org.antlr.runtime.NoViableAltException;
/*      */ import org.antlr.runtime.RecognitionException;
/*      */ import org.antlr.runtime.RecognizerSharedState;
/*      */ 
/*      */ public class StGUnitLexer extends Lexer
/*      */ {
/*      */   public static final int EOF = -1;
/*      */   public static final int T__25 = 25;
/*      */   public static final int T__26 = 26;
/*      */   public static final int T__27 = 27;
/*      */   public static final int T__28 = 28;
/*      */   public static final int T__29 = 29;
/*      */   public static final int T__30 = 30;
/*      */   public static final int T__31 = 31;
/*      */   public static final int OK = 4;
/*      */   public static final int FAIL = 5;
/*      */   public static final int DOC_COMMENT = 6;
/*      */   public static final int ACTION = 7;
/*      */   public static final int RULE_REF = 8;
/*      */   public static final int TOKEN_REF = 9;
/*      */   public static final int RETVAL = 10;
/*      */   public static final int AST = 11;
/*      */   public static final int STRING = 12;
/*      */   public static final int ML_STRING = 13;
/*      */   public static final int EXT = 14;
/*      */   public static final int SL_COMMENT = 15;
/*      */   public static final int ML_COMMENT = 16;
/*      */   public static final int ESC = 17;
/*      */   public static final int NESTED_RETVAL = 18;
/*      */   public static final int NESTED_AST = 19;
/*      */   public static final int NESTED_ACTION = 20;
/*      */   public static final int STRING_LITERAL = 21;
/*      */   public static final int CHAR_LITERAL = 22;
/*      */   public static final int XDIGIT = 23;
/*      */   public static final int WS = 24;
/* 1629 */   protected DFA13 dfa13 = new DFA13(this);
/* 1630 */   protected DFA16 dfa16 = new DFA16(this);
/* 1631 */   protected DFA19 dfa19 = new DFA19(this);
/*      */   static final String DFA13_eotS = "Nèøø";
/*      */   static final String DFA13_eofS = "Nèøø";
/*      */   static final String DFA13_minS = "";
/*      */   static final String DFA13_maxS = "\001èøø\002èøø\002èøø\001èøø\001èøø\002èøø\004èøø\001èøø\003èøø=èøø";
/*      */   static final String DFA13_acceptS = "\001èøø\001\005\001\001\002èøø\001\004\001èøø\001\004\001\002\004èøø\001\004\004èøø\037\002\016\003\004èøø\001\003\005èøø\001\003\004èøø";
/*      */   static final String DFA13_specialS = "";
/* 1647 */   static final String[] DFA13_transitionS = { "\"\005\001\003\004\005\001\004S\005\001\002\001\005\001\001ÔæÇ\005", "", "", "\"\013\001\b\004\013\001\n4\013\001\006\036\013\001\t\001\013\001\007ÔæÇ\013", "\"\020\001\017\004\020\001\0054\020\001\f\036\020\001\016\001\020\001\rÔæÇ\020", "", "\"\036\001\027\004\036\001\030\026\036\001\032\035\036\001\031\005\036\001\025\003\036\001\026\007\036\001\022\003\036\001\023\001\036\001\024\001\033\005\036\001\035\001\036\001\034ÔæÇ\036", "", "", "\"$\001\037\004$\001\"4$\001 \036$\001!\001$\001#ÔæÇ$", "\"*\001%\004*\001(4*\001&\036*\001)\001*\001'ÔæÇ*", "\"0\001+\0040\001/40\001,\0360\001.\0010\001-ÔæÇ0", "\"=\0016\004=\0017\026=\0019\035=\0018\005=\0014\003=\0015\007=\0011\003=\0012\001=\0013\001:\005=\001<\001=\001;ÔæÇ=", "", "'\005\001>Ôøò\005", "'\005\001CÔøò\005", "'\005\001IÔøò\005", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" };
/*      */ 
/* 1736 */   static final short[] DFA13_eot = DFA.unpackEncodedString("Nèøø");
/* 1737 */   static final short[] DFA13_eof = DFA.unpackEncodedString("Nèøø");
/* 1738 */   static final char[] DFA13_min = DFA.unpackEncodedStringToUnsignedChars("");
/* 1739 */   static final char[] DFA13_max = DFA.unpackEncodedStringToUnsignedChars("\001èøø\002èøø\002èøø\001èøø\001èøø\002èøø\004èøø\001èøø\003èøø=èøø");
/* 1740 */   static final short[] DFA13_accept = DFA.unpackEncodedString("\001èøø\001\005\001\001\002èøø\001\004\001èøø\001\004\001\002\004èøø\001\004\004èøø\037\002\016\003\004èøø\001\003\005èøø\001\003\004èøø");
/* 1741 */   static final short[] DFA13_special = DFA.unpackEncodedString("");
/*      */   static final short[][] DFA13_transition;
/*      */   static final String DFA16_eotS = "\nèøø\001\013\002èøø";
/*      */   static final String DFA16_eofS = "\rèøø";
/*      */   static final String DFA16_minS = "";
/*      */   static final String DFA16_maxS = "\001èøø\tèøø\001f\002èøø";
/*      */   static final String DFA16_acceptS = "\001èøø\001\001\001\002\001\003\001\004\001\005\001\006\001\007\001\b\001\t\001èøø\001\013\001\n";
/*      */   static final String DFA16_specialS = "";
/*      */   static final String[] DFA16_transitionS;
/*      */   static final short[] DFA16_eot;
/*      */   static final short[] DFA16_eof;
/*      */   static final char[] DFA16_min;
/*      */   static final char[] DFA16_max;
/*      */   static final short[] DFA16_accept;
/*      */   static final short[] DFA16_special;
/*      */   static final short[][] DFA16_transition;
/*      */   static final String DFA19_eotS = "\001èøø\002\r\002\016\003èøø\001\016\013èøø\001\033\001\r\003\016\003èøø\001\r\003\016\001$\003\016\001èøø\001(\001)\001\016\002èøø\001\016\001,\001èøø";
/*      */   static final String DFA19_eofS = "-èøø";
/*      */   static final String DFA19_minS = "\001\t\001K\001A\001u\001a\003èøø\001e\001èøø\001*\tèøø\0010\001I\001n\001l\001t\003èøø\001L\001i\001k\001u\0010\001t\001s\001r\001èøø\0020\001n\002èøø\001s\0010\001èøø";
/*      */   static final String DFA19_maxS = "\001{\001K\001A\001u\001a\003èøø\001e\001èøø\001/\tèøø\001z\001I\001n\001l\001t\003èøø\001L\001i\001k\001u\001z\001t\001s\001r\001èøø\002z\001n\002èøø\001s\001z\001èøø";
/*      */   static final String DFA19_acceptS = "\005èøø\001\005\001\006\001\007\001èøø\001\t\001èøø\001\f\001\r\001\016\001\017\001\020\001\021\001\022\001\023\001\024\005èøø\001\n\001\013\001\001\bèøø\001\002\003èøø\001\003\001\004\002èøø\001\b";
/*      */   static final String DFA19_specialS = "-èøø}>";
/*      */   static final String[] DFA19_transitionS;
/*      */   static final short[] DFA19_eot;
/*      */   static final short[] DFA19_eof;
/*      */   static final char[] DFA19_min;
/*      */   static final char[] DFA19_max;
/*      */   static final short[] DFA19_accept;
/*      */   static final short[] DFA19_special;
/*      */   static final short[][] DFA19_transition;
/*      */ 
/*      */   public StGUnitLexer()
/*      */   {
/*      */   }
/*      */ 
/*      */   public StGUnitLexer(CharStream input)
/*      */   {
/*   45 */     this(input, new RecognizerSharedState());
/*      */   }
/*      */   public StGUnitLexer(CharStream input, RecognizerSharedState state) {
/*   48 */     super(input, state);
/*      */   }
/*      */   public String getGrammarFileName() {
/*   51 */     return "org/antlr/gunit/swingui/parsers/StGUnit.g";
/*      */   }
/*      */ 
/*      */   public final void mOK() throws RecognitionException {
/*      */     try {
/*   56 */       int _type = 4;
/*   57 */       int _channel = 0;
/*      */ 
/*   61 */       match("OK");
/*      */ 
/*   66 */       this.state.type = _type;
/*   67 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mFAIL() throws RecognitionException
/*      */   {
/*      */     try {
/*   77 */       int _type = 5;
/*   78 */       int _channel = 0;
/*      */ 
/*   82 */       match("FAIL");
/*      */ 
/*   87 */       this.state.type = _type;
/*   88 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__25() throws RecognitionException
/*      */   {
/*      */     try {
/*   98 */       int _type = 25;
/*   99 */       int _channel = 0;
/*      */ 
/*  103 */       match("gunit");
/*      */ 
/*  108 */       this.state.type = _type;
/*  109 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__26() throws RecognitionException
/*      */   {
/*      */     try {
/*  119 */       int _type = 26;
/*  120 */       int _channel = 0;
/*      */ 
/*  124 */       match("walks");
/*      */ 
/*  129 */       this.state.type = _type;
/*  130 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__27() throws RecognitionException
/*      */   {
/*      */     try {
/*  140 */       int _type = 27;
/*  141 */       int _channel = 0;
/*      */ 
/*  145 */       match(59);
/*      */ 
/*  149 */       this.state.type = _type;
/*  150 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__28() throws RecognitionException
/*      */   {
/*      */     try {
/*  160 */       int _type = 28;
/*  161 */       int _channel = 0;
/*      */ 
/*  165 */       match("@header");
/*      */ 
/*  170 */       this.state.type = _type;
/*  171 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__29() throws RecognitionException
/*      */   {
/*      */     try {
/*  181 */       int _type = 29;
/*  182 */       int _channel = 0;
/*      */ 
/*  186 */       match(58);
/*      */ 
/*  190 */       this.state.type = _type;
/*  191 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__30() throws RecognitionException
/*      */   {
/*      */     try {
/*  201 */       int _type = 30;
/*  202 */       int _channel = 0;
/*      */ 
/*  206 */       match("returns");
/*      */ 
/*  211 */       this.state.type = _type;
/*  212 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__31() throws RecognitionException
/*      */   {
/*      */     try {
/*  222 */       int _type = 31;
/*  223 */       int _channel = 0;
/*      */ 
/*  227 */       match("->");
/*      */ 
/*  232 */       this.state.type = _type;
/*  233 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mSL_COMMENT() throws RecognitionException
/*      */   {
/*      */     try {
/*  243 */       int _type = 15;
/*  244 */       int _channel = 0;
/*      */ 
/*  248 */       match("//");
/*      */       while (true)
/*      */       {
/*  253 */         int alt1 = 2;
/*  254 */         int LA1_0 = this.input.LA(1);
/*      */ 
/*  256 */         if (((LA1_0 >= 0) && (LA1_0 <= 9)) || ((LA1_0 >= 11) && (LA1_0 <= 12)) || ((LA1_0 >= 14) && (LA1_0 <= 65535))) {
/*  257 */           alt1 = 1;
/*      */         }
/*      */ 
/*  261 */         switch (alt1)
/*      */         {
/*      */         case 1:
/*  265 */           if (((this.input.LA(1) >= 0) && (this.input.LA(1) <= 9)) || ((this.input.LA(1) >= 11) && (this.input.LA(1) <= 12)) || ((this.input.LA(1) >= 14) && (this.input.LA(1) <= 65535))) {
/*  266 */             this.input.consume();
/*      */           }
/*      */           else
/*      */           {
/*  270 */             MismatchedSetException mse = new MismatchedSetException(null, this.input);
/*  271 */             recover(mse);
/*  272 */             throw mse;
/*      */           }
/*      */ 
/*      */           break;
/*      */         default:
/*  279 */           break label217;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  284 */       label217: int alt2 = 2;
/*  285 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 13:
/*  288 */         alt2 = 1;
/*      */       }
/*      */ 
/*  293 */       switch (alt2)
/*      */       {
/*      */       case 1:
/*  297 */         match(13);
/*      */       }
/*      */ 
/*  304 */       match(10);
/*  305 */       _channel = 99;
/*      */ 
/*  309 */       this.state.type = _type;
/*  310 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mML_COMMENT() throws RecognitionException
/*      */   {
/*      */     try {
/*  320 */       int _type = 16;
/*  321 */       int _channel = 0;
/*      */ 
/*  325 */       match("/*");
/*      */ 
/*  327 */       _channel = 99;
/*      */       while (true)
/*      */       {
/*  331 */         int alt3 = 2;
/*  332 */         int LA3_0 = this.input.LA(1);
/*      */ 
/*  334 */         if (LA3_0 == 42) {
/*  335 */           int LA3_1 = this.input.LA(2);
/*      */ 
/*  337 */           if (LA3_1 == 47) {
/*  338 */             alt3 = 2;
/*      */           }
/*  340 */           else if (((LA3_1 >= 0) && (LA3_1 <= 46)) || ((LA3_1 >= 48) && (LA3_1 <= 65535))) {
/*  341 */             alt3 = 1;
/*      */           }
/*      */ 
/*      */         }
/*  346 */         else if (((LA3_0 >= 0) && (LA3_0 <= 41)) || ((LA3_0 >= 43) && (LA3_0 <= 65535))) {
/*  347 */           alt3 = 1;
/*      */         }
/*      */ 
/*  351 */         switch (alt3)
/*      */         {
/*      */         case 1:
/*  355 */           matchAny();
/*      */ 
/*  358 */           break;
/*      */         default:
/*  361 */           break label149;
/*      */         }
/*      */       }
/*      */ 
/*  365 */       label149: match("*/");
/*      */ 
/*  370 */       this.state.type = _type;
/*  371 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mSTRING() throws RecognitionException
/*      */   {
/*      */     try {
/*  381 */       int _type = 12;
/*  382 */       int _channel = 0;
/*      */ 
/*  386 */       match(34);
/*      */       while (true)
/*      */       {
/*  390 */         int alt4 = 3;
/*  391 */         int LA4_0 = this.input.LA(1);
/*      */ 
/*  393 */         if (LA4_0 == 92) {
/*  394 */           alt4 = 1;
/*      */         }
/*  396 */         else if (((LA4_0 >= 0) && (LA4_0 <= 33)) || ((LA4_0 >= 35) && (LA4_0 <= 91)) || ((LA4_0 >= 93) && (LA4_0 <= 65535))) {
/*  397 */           alt4 = 2;
/*      */         }
/*      */ 
/*  401 */         switch (alt4)
/*      */         {
/*      */         case 1:
/*  405 */           mESC();
/*      */ 
/*  408 */           break;
/*      */         case 2:
/*  412 */           if (((this.input.LA(1) >= 0) && (this.input.LA(1) <= 33)) || ((this.input.LA(1) >= 35) && (this.input.LA(1) <= 91)) || ((this.input.LA(1) >= 93) && (this.input.LA(1) <= 65535))) {
/*  413 */             this.input.consume();
/*      */           }
/*      */           else
/*      */           {
/*  417 */             MismatchedSetException mse = new MismatchedSetException(null, this.input);
/*  418 */             recover(mse);
/*  419 */             throw mse;
/*      */           }
/*      */ 
/*      */           break;
/*      */         default:
/*  426 */           break label244;
/*      */         }
/*      */       }
/*      */ 
/*  430 */       label244: match(34);
/*      */ 
/*  434 */       this.state.type = _type;
/*  435 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mML_STRING() throws RecognitionException
/*      */   {
/*      */     try {
/*  445 */       int _type = 13;
/*  446 */       int _channel = 0;
/*      */ 
/*  450 */       match("<<");
/*      */       while (true)
/*      */       {
/*  455 */         int alt5 = 2;
/*  456 */         int LA5_0 = this.input.LA(1);
/*      */ 
/*  458 */         if (LA5_0 == 62) {
/*  459 */           int LA5_1 = this.input.LA(2);
/*      */ 
/*  461 */           if (LA5_1 == 62) {
/*  462 */             alt5 = 2;
/*      */           }
/*  464 */           else if (((LA5_1 >= 0) && (LA5_1 <= 61)) || ((LA5_1 >= 63) && (LA5_1 <= 65535))) {
/*  465 */             alt5 = 1;
/*      */           }
/*      */ 
/*      */         }
/*  470 */         else if (((LA5_0 >= 0) && (LA5_0 <= 61)) || ((LA5_0 >= 63) && (LA5_0 <= 65535))) {
/*  471 */           alt5 = 1;
/*      */         }
/*      */ 
/*  475 */         switch (alt5)
/*      */         {
/*      */         case 1:
/*  479 */           matchAny();
/*      */ 
/*  482 */           break;
/*      */         default:
/*  485 */           break label149;
/*      */         }
/*      */       }
/*      */ 
/*  489 */       label149: match(">>");
/*      */ 
/*  494 */       this.state.type = _type;
/*  495 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mTOKEN_REF() throws RecognitionException
/*      */   {
/*      */     try {
/*  505 */       int _type = 9;
/*  506 */       int _channel = 0;
/*      */ 
/*  510 */       matchRange(65, 90);
/*      */       while (true)
/*      */       {
/*  514 */         int alt6 = 2;
/*  515 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 48:
/*      */         case 49:
/*      */         case 50:
/*      */         case 51:
/*      */         case 52:
/*      */         case 53:
/*      */         case 54:
/*      */         case 55:
/*      */         case 56:
/*      */         case 57:
/*      */         case 65:
/*      */         case 66:
/*      */         case 67:
/*      */         case 68:
/*      */         case 69:
/*      */         case 70:
/*      */         case 71:
/*      */         case 72:
/*      */         case 73:
/*      */         case 74:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*      */         case 78:
/*      */         case 79:
/*      */         case 80:
/*      */         case 81:
/*      */         case 82:
/*      */         case 83:
/*      */         case 84:
/*      */         case 85:
/*      */         case 86:
/*      */         case 87:
/*      */         case 88:
/*      */         case 89:
/*      */         case 90:
/*      */         case 95:
/*      */         case 97:
/*      */         case 98:
/*      */         case 99:
/*      */         case 100:
/*      */         case 101:
/*      */         case 102:
/*      */         case 103:
/*      */         case 104:
/*      */         case 105:
/*      */         case 106:
/*      */         case 107:
/*      */         case 108:
/*      */         case 109:
/*      */         case 110:
/*      */         case 111:
/*      */         case 112:
/*      */         case 113:
/*      */         case 114:
/*      */         case 115:
/*      */         case 116:
/*      */         case 117:
/*      */         case 118:
/*      */         case 119:
/*      */         case 120:
/*      */         case 121:
/*      */         case 122:
/*  580 */           alt6 = 1;
/*      */         case 58:
/*      */         case 59:
/*      */         case 60:
/*      */         case 61:
/*      */         case 62:
/*      */         case 63:
/*      */         case 64:
/*      */         case 91:
/*      */         case 92:
/*      */         case 93:
/*      */         case 94:
/*  586 */         case 96: } switch (alt6)
/*      */         {
/*      */         case 1:
/*  590 */           if (((this.input.LA(1) >= 48) && (this.input.LA(1) <= 57)) || ((this.input.LA(1) >= 65) && (this.input.LA(1) <= 90)) || (this.input.LA(1) == 95) || ((this.input.LA(1) >= 97) && (this.input.LA(1) <= 122))) {
/*  591 */             this.input.consume();
/*      */           }
/*      */           else
/*      */           {
/*  595 */             MismatchedSetException mse = new MismatchedSetException(null, this.input);
/*  596 */             recover(mse);
/*  597 */             throw mse;
/*      */           }
/*      */ 
/*      */           break;
/*      */         default:
/*  604 */           break label506;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  611 */       label506: this.state.type = _type;
/*  612 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mRULE_REF() throws RecognitionException
/*      */   {
/*      */     try {
/*  622 */       int _type = 8;
/*  623 */       int _channel = 0;
/*      */ 
/*  627 */       matchRange(97, 122);
/*      */       while (true)
/*      */       {
/*  631 */         int alt7 = 2;
/*  632 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 48:
/*      */         case 49:
/*      */         case 50:
/*      */         case 51:
/*      */         case 52:
/*      */         case 53:
/*      */         case 54:
/*      */         case 55:
/*      */         case 56:
/*      */         case 57:
/*      */         case 65:
/*      */         case 66:
/*      */         case 67:
/*      */         case 68:
/*      */         case 69:
/*      */         case 70:
/*      */         case 71:
/*      */         case 72:
/*      */         case 73:
/*      */         case 74:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*      */         case 78:
/*      */         case 79:
/*      */         case 80:
/*      */         case 81:
/*      */         case 82:
/*      */         case 83:
/*      */         case 84:
/*      */         case 85:
/*      */         case 86:
/*      */         case 87:
/*      */         case 88:
/*      */         case 89:
/*      */         case 90:
/*      */         case 95:
/*      */         case 97:
/*      */         case 98:
/*      */         case 99:
/*      */         case 100:
/*      */         case 101:
/*      */         case 102:
/*      */         case 103:
/*      */         case 104:
/*      */         case 105:
/*      */         case 106:
/*      */         case 107:
/*      */         case 108:
/*      */         case 109:
/*      */         case 110:
/*      */         case 111:
/*      */         case 112:
/*      */         case 113:
/*      */         case 114:
/*      */         case 115:
/*      */         case 116:
/*      */         case 117:
/*      */         case 118:
/*      */         case 119:
/*      */         case 120:
/*      */         case 121:
/*      */         case 122:
/*  697 */           alt7 = 1;
/*      */         case 58:
/*      */         case 59:
/*      */         case 60:
/*      */         case 61:
/*      */         case 62:
/*      */         case 63:
/*      */         case 64:
/*      */         case 91:
/*      */         case 92:
/*      */         case 93:
/*      */         case 94:
/*  703 */         case 96: } switch (alt7)
/*      */         {
/*      */         case 1:
/*  707 */           if (((this.input.LA(1) >= 48) && (this.input.LA(1) <= 57)) || ((this.input.LA(1) >= 65) && (this.input.LA(1) <= 90)) || (this.input.LA(1) == 95) || ((this.input.LA(1) >= 97) && (this.input.LA(1) <= 122))) {
/*  708 */             this.input.consume();
/*      */           }
/*      */           else
/*      */           {
/*  712 */             MismatchedSetException mse = new MismatchedSetException(null, this.input);
/*  713 */             recover(mse);
/*  714 */             throw mse;
/*      */           }
/*      */ 
/*      */           break;
/*      */         default:
/*  721 */           break label506;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  728 */       label506: this.state.type = _type;
/*  729 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mEXT() throws RecognitionException
/*      */   {
/*      */     try {
/*  739 */       int _type = 14;
/*  740 */       int _channel = 0;
/*      */ 
/*  744 */       match(46);
/*      */ 
/*  746 */       int cnt8 = 0;
/*      */       while (true)
/*      */       {
/*  749 */         int alt8 = 2;
/*  750 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 48:
/*      */         case 49:
/*      */         case 50:
/*      */         case 51:
/*      */         case 52:
/*      */         case 53:
/*      */         case 54:
/*      */         case 55:
/*      */         case 56:
/*      */         case 57:
/*      */         case 65:
/*      */         case 66:
/*      */         case 67:
/*      */         case 68:
/*      */         case 69:
/*      */         case 70:
/*      */         case 71:
/*      */         case 72:
/*      */         case 73:
/*      */         case 74:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*      */         case 78:
/*      */         case 79:
/*      */         case 80:
/*      */         case 81:
/*      */         case 82:
/*      */         case 83:
/*      */         case 84:
/*      */         case 85:
/*      */         case 86:
/*      */         case 87:
/*      */         case 88:
/*      */         case 89:
/*      */         case 90:
/*      */         case 97:
/*      */         case 98:
/*      */         case 99:
/*      */         case 100:
/*      */         case 101:
/*      */         case 102:
/*      */         case 103:
/*      */         case 104:
/*      */         case 105:
/*      */         case 106:
/*      */         case 107:
/*      */         case 108:
/*      */         case 109:
/*      */         case 110:
/*      */         case 111:
/*      */         case 112:
/*      */         case 113:
/*      */         case 114:
/*      */         case 115:
/*      */         case 116:
/*      */         case 117:
/*      */         case 118:
/*      */         case 119:
/*      */         case 120:
/*      */         case 121:
/*      */         case 122:
/*  814 */           alt8 = 1;
/*      */         case 58:
/*      */         case 59:
/*      */         case 60:
/*      */         case 61:
/*      */         case 62:
/*      */         case 63:
/*      */         case 64:
/*      */         case 91:
/*      */         case 92:
/*      */         case 93:
/*      */         case 94:
/*      */         case 95:
/*  820 */         case 96: } switch (alt8)
/*      */         {
/*      */         case 1:
/*  824 */           if (((this.input.LA(1) >= 48) && (this.input.LA(1) <= 57)) || ((this.input.LA(1) >= 65) && (this.input.LA(1) <= 90)) || ((this.input.LA(1) >= 97) && (this.input.LA(1) <= 122))) {
/*  825 */             this.input.consume();
/*      */           }
/*      */           else
/*      */           {
/*  829 */             MismatchedSetException mse = new MismatchedSetException(null, this.input);
/*  830 */             recover(mse);
/*  831 */             throw mse;
/*      */           }
/*      */ 
/*      */           break;
/*      */         default:
/*  838 */           if (cnt8 >= 1) break label521;
/*  839 */           EarlyExitException eee = new EarlyExitException(8, this.input);
/*      */ 
/*  841 */           throw eee;
/*      */         }
/*  843 */         cnt8++;
/*      */       }
/*      */ 
/*  849 */       label521: this.state.type = _type;
/*  850 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mRETVAL() throws RecognitionException
/*      */   {
/*      */     try {
/*  860 */       int _type = 10;
/*  861 */       int _channel = 0;
/*      */ 
/*  865 */       mNESTED_RETVAL();
/*      */ 
/*  869 */       this.state.type = _type;
/*  870 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mNESTED_RETVAL()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  883 */       match(91);
/*      */       while (true)
/*      */       {
/*  887 */         int alt9 = 3;
/*  888 */         int LA9_0 = this.input.LA(1);
/*      */ 
/*  890 */         if (LA9_0 == 93) {
/*  891 */           alt9 = 3;
/*      */         }
/*  893 */         else if (LA9_0 == 91) {
/*  894 */           alt9 = 1;
/*      */         }
/*  896 */         else if (((LA9_0 >= 0) && (LA9_0 <= 90)) || (LA9_0 == 92) || ((LA9_0 >= 94) && (LA9_0 <= 65535))) {
/*  897 */           alt9 = 2;
/*      */         }
/*      */ 
/*  901 */         switch (alt9)
/*      */         {
/*      */         case 1:
/*  905 */           mNESTED_RETVAL();
/*      */ 
/*  908 */           break;
/*      */         case 2:
/*  912 */           matchAny();
/*      */ 
/*  915 */           break;
/*      */         default:
/*  918 */           break label120;
/*      */         }
/*      */       }
/*      */ 
/*  922 */       label120: match(93);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mAST()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  935 */       int _type = 11;
/*  936 */       int _channel = 0;
/*      */ 
/*  940 */       mNESTED_AST();
/*      */       while (true)
/*      */       {
/*  944 */         int alt11 = 2;
/*  945 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 32:
/*      */         case 40:
/*  949 */           alt11 = 1;
/*      */         }
/*      */ 
/*  955 */         switch (alt11)
/*      */         {
/*      */         case 1:
/*  960 */           int alt10 = 2;
/*  961 */           switch (this.input.LA(1))
/*      */           {
/*      */           case 32:
/*  964 */             alt10 = 1;
/*      */           }
/*      */ 
/*  969 */           switch (alt10)
/*      */           {
/*      */           case 1:
/*  973 */             match(32);
/*      */           }
/*      */ 
/*  980 */           mNESTED_AST();
/*      */ 
/*  983 */           break;
/*      */         default:
/*  986 */           break label143;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  993 */       label143: this.state.type = _type;
/*  994 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mNESTED_AST()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 1007 */       match(40);
/*      */       while (true)
/*      */       {
/* 1011 */         int alt12 = 3;
/* 1012 */         int LA12_0 = this.input.LA(1);
/*      */ 
/* 1014 */         if (LA12_0 == 41) {
/* 1015 */           alt12 = 3;
/*      */         }
/* 1017 */         else if (LA12_0 == 40) {
/* 1018 */           alt12 = 1;
/*      */         }
/* 1020 */         else if (((LA12_0 >= 0) && (LA12_0 <= 39)) || ((LA12_0 >= 42) && (LA12_0 <= 65535))) {
/* 1021 */           alt12 = 2;
/*      */         }
/*      */ 
/* 1025 */         switch (alt12)
/*      */         {
/*      */         case 1:
/* 1029 */           mNESTED_AST();
/*      */ 
/* 1032 */           break;
/*      */         case 2:
/* 1036 */           matchAny();
/*      */ 
/* 1039 */           break;
/*      */         default:
/* 1042 */           break label112;
/*      */         }
/*      */       }
/*      */ 
/* 1046 */       label112: match(41);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mACTION()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 1059 */       int _type = 7;
/* 1060 */       int _channel = 0;
/*      */ 
/* 1064 */       mNESTED_ACTION();
/*      */ 
/* 1068 */       this.state.type = _type;
/* 1069 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mNESTED_ACTION()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 1082 */       match(123);
/*      */       while (true)
/*      */       {
/* 1086 */         int alt13 = 5;
/* 1087 */         alt13 = this.dfa13.predict(this.input);
/* 1088 */         switch (alt13)
/*      */         {
/*      */         case 1:
/* 1092 */           mNESTED_ACTION();
/*      */ 
/* 1095 */           break;
/*      */         case 2:
/* 1099 */           mSTRING_LITERAL();
/*      */ 
/* 1102 */           break;
/*      */         case 3:
/* 1106 */           mCHAR_LITERAL();
/*      */ 
/* 1109 */           break;
/*      */         case 4:
/* 1113 */           matchAny();
/*      */ 
/* 1116 */           break;
/*      */         default:
/* 1119 */           break label86;
/*      */         }
/*      */       }
/*      */ 
/* 1123 */       label86: match(125);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mCHAR_LITERAL()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 1139 */       match(39);
/*      */ 
/* 1141 */       int alt14 = 2;
/* 1142 */       int LA14_0 = this.input.LA(1);
/*      */ 
/* 1144 */       if (LA14_0 == 92) {
/* 1145 */         alt14 = 1;
/*      */       }
/* 1147 */       else if (((LA14_0 >= 0) && (LA14_0 <= 38)) || ((LA14_0 >= 40) && (LA14_0 <= 91)) || ((LA14_0 >= 93) && (LA14_0 <= 65535))) {
/* 1148 */         alt14 = 2;
/*      */       }
/*      */       else {
/* 1151 */         NoViableAltException nvae = new NoViableAltException("", 14, 0, this.input);
/*      */ 
/* 1154 */         throw nvae;
/*      */       }
/* 1156 */       switch (alt14)
/*      */       {
/*      */       case 1:
/* 1160 */         mESC();
/*      */ 
/* 1163 */         break;
/*      */       case 2:
/* 1167 */         if (((this.input.LA(1) >= 0) && (this.input.LA(1) <= 38)) || ((this.input.LA(1) >= 40) && (this.input.LA(1) <= 91)) || ((this.input.LA(1) >= 93) && (this.input.LA(1) <= 65535))) {
/* 1168 */           this.input.consume();
/*      */         }
/*      */         else
/*      */         {
/* 1172 */           MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 1173 */           recover(mse);
/* 1174 */           throw mse;
/*      */         }
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 1182 */       match(39);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mSTRING_LITERAL()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 1198 */       match(34);
/*      */       while (true)
/*      */       {
/* 1202 */         int alt15 = 3;
/* 1203 */         int LA15_0 = this.input.LA(1);
/*      */ 
/* 1205 */         if (LA15_0 == 92) {
/* 1206 */           alt15 = 1;
/*      */         }
/* 1208 */         else if (((LA15_0 >= 0) && (LA15_0 <= 33)) || ((LA15_0 >= 35) && (LA15_0 <= 91)) || ((LA15_0 >= 93) && (LA15_0 <= 65535))) {
/* 1209 */           alt15 = 2;
/*      */         }
/*      */ 
/* 1213 */         switch (alt15)
/*      */         {
/*      */         case 1:
/* 1217 */           mESC();
/*      */ 
/* 1220 */           break;
/*      */         case 2:
/* 1224 */           if (((this.input.LA(1) >= 0) && (this.input.LA(1) <= 33)) || ((this.input.LA(1) >= 35) && (this.input.LA(1) <= 91)) || ((this.input.LA(1) >= 93) && (this.input.LA(1) <= 65535))) {
/* 1225 */             this.input.consume();
/*      */           }
/*      */           else
/*      */           {
/* 1229 */             MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 1230 */             recover(mse);
/* 1231 */             throw mse;
/*      */           }
/*      */ 
/*      */           break;
/*      */         default:
/* 1238 */           break label225;
/*      */         }
/*      */       }
/*      */ 
/* 1242 */       label225: match(34);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mESC()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 1258 */       match(92);
/*      */ 
/* 1260 */       int alt16 = 11;
/* 1261 */       alt16 = this.dfa16.predict(this.input);
/* 1262 */       switch (alt16)
/*      */       {
/*      */       case 1:
/* 1266 */         match(110);
/*      */ 
/* 1269 */         break;
/*      */       case 2:
/* 1273 */         match(114);
/*      */ 
/* 1276 */         break;
/*      */       case 3:
/* 1280 */         match(116);
/*      */ 
/* 1283 */         break;
/*      */       case 4:
/* 1287 */         match(98);
/*      */ 
/* 1290 */         break;
/*      */       case 5:
/* 1294 */         match(102);
/*      */ 
/* 1297 */         break;
/*      */       case 6:
/* 1301 */         match(34);
/*      */ 
/* 1304 */         break;
/*      */       case 7:
/* 1308 */         match(39);
/*      */ 
/* 1311 */         break;
/*      */       case 8:
/* 1315 */         match(92);
/*      */ 
/* 1318 */         break;
/*      */       case 9:
/* 1322 */         match(62);
/*      */ 
/* 1325 */         break;
/*      */       case 10:
/* 1329 */         match(117);
/* 1330 */         mXDIGIT();
/* 1331 */         mXDIGIT();
/* 1332 */         mXDIGIT();
/* 1333 */         mXDIGIT();
/*      */ 
/* 1336 */         break;
/*      */       case 11:
/* 1340 */         matchAny();
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mXDIGIT()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 1362 */       if (((this.input.LA(1) >= 48) && (this.input.LA(1) <= 57)) || ((this.input.LA(1) >= 65) && (this.input.LA(1) <= 70)) || ((this.input.LA(1) >= 97) && (this.input.LA(1) <= 102))) {
/* 1363 */         this.input.consume();
/*      */       }
/*      */       else
/*      */       {
/* 1367 */         MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 1368 */         recover(mse);
/* 1369 */         throw mse;
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mWS()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 1383 */       int _type = 24;
/* 1384 */       int _channel = 0;
/*      */ 
/* 1389 */       int cnt18 = 0;
/*      */       while (true)
/*      */       {
/* 1392 */         int alt18 = 4;
/* 1393 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 32:
/* 1396 */           alt18 = 1;
/*      */ 
/* 1398 */           break;
/*      */         case 9:
/* 1401 */           alt18 = 2;
/*      */ 
/* 1403 */           break;
/*      */         case 10:
/*      */         case 13:
/* 1407 */           alt18 = 3;
/*      */         }
/*      */ 
/* 1413 */         switch (alt18)
/*      */         {
/*      */         case 1:
/* 1417 */           match(32);
/*      */ 
/* 1420 */           break;
/*      */         case 2:
/* 1424 */           match(9);
/*      */ 
/* 1427 */           break;
/*      */         case 3:
/* 1432 */           int alt17 = 2;
/* 1433 */           switch (this.input.LA(1))
/*      */           {
/*      */           case 13:
/* 1436 */             alt17 = 1;
/*      */           }
/*      */ 
/* 1441 */           switch (alt17)
/*      */           {
/*      */           case 1:
/* 1445 */             match(13);
/*      */           }
/*      */ 
/* 1452 */           match(10);
/*      */ 
/* 1455 */           break;
/*      */         default:
/* 1458 */           if (cnt18 >= 1) break label227;
/* 1459 */           EarlyExitException eee = new EarlyExitException(18, this.input);
/*      */ 
/* 1461 */           throw eee;
/*      */         }
/* 1463 */         cnt18++;
/*      */       }
/*      */ 
/* 1466 */       label227: _channel = 99;
/*      */ 
/* 1470 */       this.state.type = _type;
/* 1471 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public void mTokens() throws RecognitionException
/*      */   {
/* 1480 */     int alt19 = 20;
/* 1481 */     alt19 = this.dfa19.predict(this.input);
/* 1482 */     switch (alt19)
/*      */     {
/*      */     case 1:
/* 1486 */       mOK();
/*      */ 
/* 1489 */       break;
/*      */     case 2:
/* 1493 */       mFAIL();
/*      */ 
/* 1496 */       break;
/*      */     case 3:
/* 1500 */       mT__25();
/*      */ 
/* 1503 */       break;
/*      */     case 4:
/* 1507 */       mT__26();
/*      */ 
/* 1510 */       break;
/*      */     case 5:
/* 1514 */       mT__27();
/*      */ 
/* 1517 */       break;
/*      */     case 6:
/* 1521 */       mT__28();
/*      */ 
/* 1524 */       break;
/*      */     case 7:
/* 1528 */       mT__29();
/*      */ 
/* 1531 */       break;
/*      */     case 8:
/* 1535 */       mT__30();
/*      */ 
/* 1538 */       break;
/*      */     case 9:
/* 1542 */       mT__31();
/*      */ 
/* 1545 */       break;
/*      */     case 10:
/* 1549 */       mSL_COMMENT();
/*      */ 
/* 1552 */       break;
/*      */     case 11:
/* 1556 */       mML_COMMENT();
/*      */ 
/* 1559 */       break;
/*      */     case 12:
/* 1563 */       mSTRING();
/*      */ 
/* 1566 */       break;
/*      */     case 13:
/* 1570 */       mML_STRING();
/*      */ 
/* 1573 */       break;
/*      */     case 14:
/* 1577 */       mTOKEN_REF();
/*      */ 
/* 1580 */       break;
/*      */     case 15:
/* 1584 */       mRULE_REF();
/*      */ 
/* 1587 */       break;
/*      */     case 16:
/* 1591 */       mEXT();
/*      */ 
/* 1594 */       break;
/*      */     case 17:
/* 1598 */       mRETVAL();
/*      */ 
/* 1601 */       break;
/*      */     case 18:
/* 1605 */       mAST();
/*      */ 
/* 1608 */       break;
/*      */     case 19:
/* 1612 */       mACTION();
/*      */ 
/* 1615 */       break;
/*      */     case 20:
/* 1619 */       mWS();
/*      */     }
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/* 1745 */     int numStates = DFA13_transitionS.length;
/* 1746 */     DFA13_transition = new short[numStates][];
/* 1747 */     for (int i = 0; i < numStates; i++) {
/* 1748 */       DFA13_transition[i] = DFA.unpackEncodedString(DFA13_transitionS[i]);
/*      */     }
/*      */ 
/* 1991 */     DFA16_transitionS = new String[] { "\"\013\001\006\004\013\001\007\026\013\001\t\035\013\001\b\005\013\001\004\003\013\001\005\007\013\001\001\003\013\001\002\001\013\001\003\001\nÔæä\013", "", "", "", "", "", "", "", "", "", "\n\f\007èøø\006\f\032èøø\006\f", "", "" };
/*      */ 
/* 2008 */     DFA16_eot = DFA.unpackEncodedString("\nèøø\001\013\002èøø");
/* 2009 */     DFA16_eof = DFA.unpackEncodedString("\rèøø");
/* 2010 */     DFA16_min = DFA.unpackEncodedStringToUnsignedChars("");
/* 2011 */     DFA16_max = DFA.unpackEncodedStringToUnsignedChars("\001èøø\tèøø\001f\002èøø");
/* 2012 */     DFA16_accept = DFA.unpackEncodedString("\001èøø\001\001\001\002\001\003\001\004\001\005\001\006\001\007\001\b\001\t\001èøø\001\013\001\n");
/* 2013 */     DFA16_special = DFA.unpackEncodedString("");
/*      */ 
/* 2017 */     int numStates = DFA16_transitionS.length;
/* 2018 */     DFA16_transition = new short[numStates][];
/* 2019 */     for (int i = 0; i < numStates; i++) {
/* 2020 */       DFA16_transition[i] = DFA.unpackEncodedString(DFA16_transitionS[i]);
/*      */     }
/*      */ 
/* 2100 */     DFA19_transitionS = new String[] { "\002\023\002èøø\001\023\022èøø\001\023\001èøø\001\013\005èøø\001\021\004èøø\001\t\001\017\001\n\nèøø\001\007\001\005\001\f\003èøø\001\006\005\r\001\002\b\r\001\001\013\r\001\020\005èøø\006\016\001\003\n\016\001\b\004\016\001\004\003\016\001\022", "\001\024", "\001\025", "\001\026", "\001\027", "", "", "", "\001\030", "", "\001\032\004èøø\001\031", "", "", "", "", "", "", "", "", "", "\n\r\007èøø\032\r\004èøø\001\r\001èøø\032\r", "\001\034", "\001\035", "\001\036", "\001\037", "", "", "", "\001 ", "\001!", "\001\"", "\001#", "\n\r\007èøø\032\r\004èøø\001\r\001èøø\032\r", "\001%", "\001&", "\001'", "", "\n\016\007èøø\032\016\004èøø\001\016\001èøø\032\016", "\n\016\007èøø\032\016\004èøø\001\016\001èøø\032\016", "\001*", "", "", "\001+", "\n\016\007èøø\032\016\004èøø\001\016\001èøø\032\016", "" };
/*      */ 
/* 2151 */     DFA19_eot = DFA.unpackEncodedString("\001èøø\002\r\002\016\003èøø\001\016\013èøø\001\033\001\r\003\016\003èøø\001\r\003\016\001$\003\016\001èøø\001(\001)\001\016\002èøø\001\016\001,\001èøø");
/* 2152 */     DFA19_eof = DFA.unpackEncodedString("-èøø");
/* 2153 */     DFA19_min = DFA.unpackEncodedStringToUnsignedChars("\001\t\001K\001A\001u\001a\003èøø\001e\001èøø\001*\tèøø\0010\001I\001n\001l\001t\003èøø\001L\001i\001k\001u\0010\001t\001s\001r\001èøø\0020\001n\002èøø\001s\0010\001èøø");
/* 2154 */     DFA19_max = DFA.unpackEncodedStringToUnsignedChars("\001{\001K\001A\001u\001a\003èøø\001e\001èøø\001/\tèøø\001z\001I\001n\001l\001t\003èøø\001L\001i\001k\001u\001z\001t\001s\001r\001èøø\002z\001n\002èøø\001s\001z\001èøø");
/* 2155 */     DFA19_accept = DFA.unpackEncodedString("\005èøø\001\005\001\006\001\007\001èøø\001\t\001èøø\001\f\001\r\001\016\001\017\001\020\001\021\001\022\001\023\001\024\005èøø\001\n\001\013\001\001\bèøø\001\002\003èøø\001\003\001\004\002èøø\001\b");
/* 2156 */     DFA19_special = DFA.unpackEncodedString("-èøø}>");
/*      */ 
/* 2160 */     int numStates = DFA19_transitionS.length;
/* 2161 */     DFA19_transition = new short[numStates][];
/* 2162 */     for (int i = 0; i < numStates; i++)
/* 2163 */       DFA19_transition[i] = DFA.unpackEncodedString(DFA19_transitionS[i]);
/*      */   }
/*      */ 
/*      */   class DFA19 extends DFA
/*      */   {
/*      */     public DFA19(BaseRecognizer recognizer)
/*      */     {
/* 2170 */       this.recognizer = recognizer;
/* 2171 */       this.decisionNumber = 19;
/* 2172 */       this.eot = StGUnitLexer.DFA19_eot;
/* 2173 */       this.eof = StGUnitLexer.DFA19_eof;
/* 2174 */       this.min = StGUnitLexer.DFA19_min;
/* 2175 */       this.max = StGUnitLexer.DFA19_max;
/* 2176 */       this.accept = StGUnitLexer.DFA19_accept;
/* 2177 */       this.special = StGUnitLexer.DFA19_special;
/* 2178 */       this.transition = StGUnitLexer.DFA19_transition;
/*      */     }
/*      */     public String getDescription() {
/* 2181 */       return "1:1: Tokens : ( OK | FAIL | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | SL_COMMENT | ML_COMMENT | STRING | ML_STRING | TOKEN_REF | RULE_REF | EXT | RETVAL | AST | ACTION | WS );";
/*      */     }
/*      */   }
/*      */ 
/*      */   class DFA16 extends DFA
/*      */   {
/*      */     public DFA16(BaseRecognizer recognizer)
/*      */     {
/* 2027 */       this.recognizer = recognizer;
/* 2028 */       this.decisionNumber = 16;
/* 2029 */       this.eot = StGUnitLexer.DFA16_eot;
/* 2030 */       this.eof = StGUnitLexer.DFA16_eof;
/* 2031 */       this.min = StGUnitLexer.DFA16_min;
/* 2032 */       this.max = StGUnitLexer.DFA16_max;
/* 2033 */       this.accept = StGUnitLexer.DFA16_accept;
/* 2034 */       this.special = StGUnitLexer.DFA16_special;
/* 2035 */       this.transition = StGUnitLexer.DFA16_transition;
/*      */     }
/*      */     public String getDescription() {
/* 2038 */       return "187:3: ( 'n' | 'r' | 't' | 'b' | 'f' | '\"' | '\\'' | '\\\\' | '>' | 'u' XDIGIT XDIGIT XDIGIT XDIGIT | . )";
/*      */     }
/*      */     public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
/* 2041 */       IntStream input = _input;
/* 2042 */       int _s = s;
/* 2043 */       switch (s) {
/*      */       case 0:
/* 2045 */         int LA16_0 = input.LA(1);
/*      */ 
/* 2047 */         s = -1;
/* 2048 */         if (LA16_0 == 110) s = 1;
/* 2050 */         else if (LA16_0 == 114) s = 2;
/* 2052 */         else if (LA16_0 == 116) s = 3;
/* 2054 */         else if (LA16_0 == 98) s = 4;
/* 2056 */         else if (LA16_0 == 102) s = 5;
/* 2058 */         else if (LA16_0 == 34) s = 6;
/* 2060 */         else if (LA16_0 == 39) s = 7;
/* 2062 */         else if (LA16_0 == 92) s = 8;
/* 2064 */         else if (LA16_0 == 62) s = 9;
/* 2066 */         else if (LA16_0 == 117) s = 10;
/* 2068 */         else if (((LA16_0 >= 0) && (LA16_0 <= 33)) || ((LA16_0 >= 35) && (LA16_0 <= 38)) || ((LA16_0 >= 40) && (LA16_0 <= 61)) || ((LA16_0 >= 63) && (LA16_0 <= 91)) || ((LA16_0 >= 93) && (LA16_0 <= 97)) || ((LA16_0 >= 99) && (LA16_0 <= 101)) || ((LA16_0 >= 103) && (LA16_0 <= 109)) || ((LA16_0 >= 111) && (LA16_0 <= 113)) || (LA16_0 == 115) || ((LA16_0 >= 118) && (LA16_0 <= 65535))) s = 11;
/*      */ 
/* 2070 */         if (s >= 0) return s;
/*      */         break;
/*      */       }
/* 2073 */       NoViableAltException nvae = new NoViableAltException(getDescription(), 16, _s, input);
/*      */ 
/* 2075 */       error(nvae);
/* 2076 */       throw nvae;
/*      */     }
/*      */   }
/*      */ 
/*      */   class DFA13 extends DFA
/*      */   {
/*      */     public DFA13(BaseRecognizer recognizer)
/*      */     {
/* 1755 */       this.recognizer = recognizer;
/* 1756 */       this.decisionNumber = 13;
/* 1757 */       this.eot = StGUnitLexer.DFA13_eot;
/* 1758 */       this.eof = StGUnitLexer.DFA13_eof;
/* 1759 */       this.min = StGUnitLexer.DFA13_min;
/* 1760 */       this.max = StGUnitLexer.DFA13_max;
/* 1761 */       this.accept = StGUnitLexer.DFA13_accept;
/* 1762 */       this.special = StGUnitLexer.DFA13_special;
/* 1763 */       this.transition = StGUnitLexer.DFA13_transition;
/*      */     }
/*      */     public String getDescription() {
/* 1766 */       return "()* loopback of 166:2: ( options {greedy=false; k=3; } : NESTED_ACTION | STRING_LITERAL | CHAR_LITERAL | . )*";
/*      */     }
/*      */     public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
/* 1769 */       IntStream input = _input;
/* 1770 */       int _s = s;
/* 1771 */       switch (s) {
/*      */       case 0:
/* 1773 */         int LA13_0 = input.LA(1);
/*      */ 
/* 1775 */         s = -1;
/* 1776 */         if (LA13_0 == 125) s = 1;
/* 1778 */         else if (LA13_0 == 123) s = 2;
/* 1780 */         else if (LA13_0 == 34) s = 3;
/* 1782 */         else if (LA13_0 == 39) s = 4;
/* 1784 */         else if (((LA13_0 >= 0) && (LA13_0 <= 33)) || ((LA13_0 >= 35) && (LA13_0 <= 38)) || ((LA13_0 >= 40) && (LA13_0 <= 122)) || (LA13_0 == 124) || ((LA13_0 >= 126) && (LA13_0 <= 65535))) s = 5;
/*      */ 
/* 1786 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 1:
/* 1789 */         int LA13_3 = input.LA(1);
/*      */ 
/* 1791 */         s = -1;
/* 1792 */         if (LA13_3 == 92) s = 6;
/* 1794 */         else if (LA13_3 == 125) s = 7;
/* 1796 */         else if (LA13_3 == 34) s = 8;
/* 1798 */         else if (LA13_3 == 123) s = 9;
/* 1800 */         else if (LA13_3 == 39) s = 10;
/* 1802 */         else if (((LA13_3 >= 0) && (LA13_3 <= 33)) || ((LA13_3 >= 35) && (LA13_3 <= 38)) || ((LA13_3 >= 40) && (LA13_3 <= 91)) || ((LA13_3 >= 93) && (LA13_3 <= 122)) || (LA13_3 == 124) || ((LA13_3 >= 126) && (LA13_3 <= 65535))) s = 11;
/*      */ 
/* 1804 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 2:
/* 1807 */         int LA13_4 = input.LA(1);
/*      */ 
/* 1809 */         s = -1;
/* 1810 */         if (LA13_4 == 92) s = 12;
/* 1812 */         else if (LA13_4 == 125) s = 13;
/* 1814 */         else if (LA13_4 == 123) s = 14;
/* 1816 */         else if (LA13_4 == 34) s = 15;
/* 1818 */         else if (((LA13_4 >= 0) && (LA13_4 <= 33)) || ((LA13_4 >= 35) && (LA13_4 <= 38)) || ((LA13_4 >= 40) && (LA13_4 <= 91)) || ((LA13_4 >= 93) && (LA13_4 <= 122)) || (LA13_4 == 124) || ((LA13_4 >= 126) && (LA13_4 <= 65535))) s = 16;
/* 1820 */         else if (LA13_4 == 39) s = 5;
/*      */ 
/* 1822 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 3:
/* 1825 */         int LA13_6 = input.LA(1);
/*      */ 
/* 1827 */         s = -1;
/* 1828 */         if (LA13_6 == 110) s = 18;
/* 1830 */         else if (LA13_6 == 114) s = 19;
/* 1832 */         else if (LA13_6 == 116) s = 20;
/* 1834 */         else if (LA13_6 == 98) s = 21;
/* 1836 */         else if (LA13_6 == 102) s = 22;
/* 1838 */         else if (LA13_6 == 34) s = 23;
/* 1840 */         else if (LA13_6 == 39) s = 24;
/* 1842 */         else if (LA13_6 == 92) s = 25;
/* 1844 */         else if (LA13_6 == 62) s = 26;
/* 1846 */         else if (LA13_6 == 117) s = 27;
/* 1848 */         else if (LA13_6 == 125) s = 28;
/* 1850 */         else if (LA13_6 == 123) s = 29;
/* 1852 */         else if (((LA13_6 >= 0) && (LA13_6 <= 33)) || ((LA13_6 >= 35) && (LA13_6 <= 38)) || ((LA13_6 >= 40) && (LA13_6 <= 61)) || ((LA13_6 >= 63) && (LA13_6 <= 91)) || ((LA13_6 >= 93) && (LA13_6 <= 97)) || ((LA13_6 >= 99) && (LA13_6 <= 101)) || ((LA13_6 >= 103) && (LA13_6 <= 109)) || ((LA13_6 >= 111) && (LA13_6 <= 113)) || (LA13_6 == 115) || ((LA13_6 >= 118) && (LA13_6 <= 122)) || (LA13_6 == 124) || ((LA13_6 >= 126) && (LA13_6 <= 65535))) s = 30;
/*      */ 
/* 1854 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 4:
/* 1857 */         int LA13_9 = input.LA(1);
/*      */ 
/* 1859 */         s = -1;
/* 1860 */         if (LA13_9 == 34) s = 31;
/* 1862 */         else if (LA13_9 == 92) s = 32;
/* 1864 */         else if (LA13_9 == 123) s = 33;
/* 1866 */         else if (LA13_9 == 39) s = 34;
/* 1868 */         else if (LA13_9 == 125) s = 35;
/* 1870 */         else if (((LA13_9 >= 0) && (LA13_9 <= 33)) || ((LA13_9 >= 35) && (LA13_9 <= 38)) || ((LA13_9 >= 40) && (LA13_9 <= 91)) || ((LA13_9 >= 93) && (LA13_9 <= 122)) || (LA13_9 == 124) || ((LA13_9 >= 126) && (LA13_9 <= 65535))) s = 36;
/*      */ 
/* 1872 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 5:
/* 1875 */         int LA13_10 = input.LA(1);
/*      */ 
/* 1877 */         s = -1;
/* 1878 */         if (LA13_10 == 34) s = 37;
/* 1880 */         else if (LA13_10 == 92) s = 38;
/* 1882 */         else if (LA13_10 == 125) s = 39;
/* 1884 */         else if (LA13_10 == 39) s = 40;
/* 1886 */         else if (LA13_10 == 123) s = 41;
/* 1888 */         else if (((LA13_10 >= 0) && (LA13_10 <= 33)) || ((LA13_10 >= 35) && (LA13_10 <= 38)) || ((LA13_10 >= 40) && (LA13_10 <= 91)) || ((LA13_10 >= 93) && (LA13_10 <= 122)) || (LA13_10 == 124) || ((LA13_10 >= 126) && (LA13_10 <= 65535))) s = 42;
/*      */ 
/* 1890 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 6:
/* 1893 */         int LA13_11 = input.LA(1);
/*      */ 
/* 1895 */         s = -1;
/* 1896 */         if (LA13_11 == 34) s = 43;
/* 1898 */         else if (LA13_11 == 92) s = 44;
/* 1900 */         else if (LA13_11 == 125) s = 45;
/* 1902 */         else if (LA13_11 == 123) s = 46;
/* 1904 */         else if (LA13_11 == 39) s = 47;
/* 1906 */         else if (((LA13_11 >= 0) && (LA13_11 <= 33)) || ((LA13_11 >= 35) && (LA13_11 <= 38)) || ((LA13_11 >= 40) && (LA13_11 <= 91)) || ((LA13_11 >= 93) && (LA13_11 <= 122)) || (LA13_11 == 124) || ((LA13_11 >= 126) && (LA13_11 <= 65535))) s = 48;
/*      */ 
/* 1908 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 7:
/* 1911 */         int LA13_12 = input.LA(1);
/*      */ 
/* 1913 */         s = -1;
/* 1914 */         if (LA13_12 == 110) s = 49;
/* 1916 */         else if (LA13_12 == 114) s = 50;
/* 1918 */         else if (LA13_12 == 116) s = 51;
/* 1920 */         else if (LA13_12 == 98) s = 52;
/* 1922 */         else if (LA13_12 == 102) s = 53;
/* 1924 */         else if (LA13_12 == 34) s = 54;
/* 1926 */         else if (LA13_12 == 39) s = 55;
/* 1928 */         else if (LA13_12 == 92) s = 56;
/* 1930 */         else if (LA13_12 == 62) s = 57;
/* 1932 */         else if (LA13_12 == 117) s = 58;
/* 1934 */         else if (LA13_12 == 125) s = 59;
/* 1936 */         else if (LA13_12 == 123) s = 60;
/* 1938 */         else if (((LA13_12 >= 0) && (LA13_12 <= 33)) || ((LA13_12 >= 35) && (LA13_12 <= 38)) || ((LA13_12 >= 40) && (LA13_12 <= 61)) || ((LA13_12 >= 63) && (LA13_12 <= 91)) || ((LA13_12 >= 93) && (LA13_12 <= 97)) || ((LA13_12 >= 99) && (LA13_12 <= 101)) || ((LA13_12 >= 103) && (LA13_12 <= 109)) || ((LA13_12 >= 111) && (LA13_12 <= 113)) || (LA13_12 == 115) || ((LA13_12 >= 118) && (LA13_12 <= 122)) || (LA13_12 == 124) || ((LA13_12 >= 126) && (LA13_12 <= 65535))) s = 61;
/*      */ 
/* 1940 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 8:
/* 1943 */         int LA13_14 = input.LA(1);
/*      */ 
/* 1945 */         s = -1;
/* 1946 */         if (LA13_14 == 39) s = 62;
/* 1948 */         else if (((LA13_14 >= 0) && (LA13_14 <= 38)) || ((LA13_14 >= 40) && (LA13_14 <= 65535))) s = 5;
/*      */ 
/* 1950 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 9:
/* 1953 */         int LA13_15 = input.LA(1);
/*      */ 
/* 1955 */         s = -1;
/* 1956 */         if (LA13_15 == 39) s = 67;
/* 1958 */         else if (((LA13_15 >= 0) && (LA13_15 <= 38)) || ((LA13_15 >= 40) && (LA13_15 <= 65535))) s = 5;
/*      */ 
/* 1960 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 10:
/* 1963 */         int LA13_16 = input.LA(1);
/*      */ 
/* 1965 */         s = -1;
/* 1966 */         if (LA13_16 == 39) s = 73;
/* 1968 */         else if (((LA13_16 >= 0) && (LA13_16 <= 38)) || ((LA13_16 >= 40) && (LA13_16 <= 65535))) s = 5;
/*      */ 
/* 1970 */         if (s >= 0) return s;
/*      */         break;
/*      */       }
/* 1973 */       NoViableAltException nvae = new NoViableAltException(getDescription(), 13, _s, input);
/*      */ 
/* 1975 */       error(nvae);
/* 1976 */       throw nvae;
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.swingui.parsers.StGUnitLexer
 * JD-Core Version:    0.6.2
 */