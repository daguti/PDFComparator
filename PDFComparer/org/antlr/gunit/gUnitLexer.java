/*      */ package org.antlr.gunit;
/*      */ 
/*      */ import java.io.BufferedReader;
/*      */ import java.io.IOException;
/*      */ import java.io.StringReader;
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
/*      */ public class gUnitLexer extends Lexer
/*      */ {
/*      */   public static final int EOF = -1;
/*      */   public static final int T__26 = 26;
/*      */   public static final int T__27 = 27;
/*      */   public static final int T__28 = 28;
/*      */   public static final int T__29 = 29;
/*      */   public static final int T__30 = 30;
/*      */   public static final int T__31 = 31;
/*      */   public static final int T__32 = 32;
/*      */   public static final int T__33 = 33;
/*      */   public static final int T__34 = 34;
/*      */   public static final int OK = 4;
/*      */   public static final int FAIL = 5;
/*      */   public static final int DOC_COMMENT = 6;
/*      */   public static final int OPTIONS = 7;
/*      */   public static final int EXT = 8;
/*      */   public static final int ACTION = 9;
/*      */   public static final int RULE_REF = 10;
/*      */   public static final int TOKEN_REF = 11;
/*      */   public static final int STRING = 12;
/*      */   public static final int ML_STRING = 13;
/*      */   public static final int RETVAL = 14;
/*      */   public static final int AST = 15;
/*      */   public static final int SL_COMMENT = 16;
/*      */   public static final int ML_COMMENT = 17;
/*      */   public static final int ESC = 18;
/*      */   public static final int NESTED_RETVAL = 19;
/*      */   public static final int NESTED_AST = 20;
/*      */   public static final int STRING_LITERAL = 21;
/*      */   public static final int WS = 22;
/*      */   public static final int NESTED_ACTION = 23;
/*      */   public static final int CHAR_LITERAL = 24;
/*      */   public static final int XDIGIT = 25;
/* 1831 */   protected DFA14 dfa14 = new DFA14(this);
/* 1832 */   protected DFA17 dfa17 = new DFA17(this);
/* 1833 */   protected DFA20 dfa20 = new DFA20(this);
/*      */   static final String DFA14_eotS = "Nèøø";
/*      */   static final String DFA14_eofS = "Nèøø";
/*      */   static final String DFA14_minS = "";
/*      */   static final String DFA14_maxS = "\001èøø\002èøø\002èøø\001èøø\001èøø\002èøø\004èøø\001èøø\003èøø=èøø";
/*      */   static final String DFA14_acceptS = "\001èøø\001\005\001\001\002èøø\001\004\001èøø\001\004\001\002\004èøø\001\004\004èøø\037\002\016\003\004èøø\001\003\005èøø\001\003\004èøø";
/*      */   static final String DFA14_specialS = "";
/* 1849 */   static final String[] DFA14_transitionS = { "\"\005\001\003\004\005\001\004S\005\001\002\001\005\001\001ÔæÇ\005", "", "", "\"\013\001\b\004\013\001\n4\013\001\006\036\013\001\t\001\013\001\007ÔæÇ\013", "\"\020\001\017\004\020\001\0054\020\001\f\036\020\001\016\001\020\001\rÔæÇ\020", "", "\"\036\001\027\004\036\001\030\026\036\001\032\035\036\001\031\005\036\001\025\003\036\001\026\007\036\001\022\003\036\001\023\001\036\001\024\001\033\005\036\001\035\001\036\001\034ÔæÇ\036", "", "", "\"$\001\037\004$\001\"4$\001 \036$\001!\001$\001#ÔæÇ$", "\"*\001%\004*\001(4*\001&\036*\001)\001*\001'ÔæÇ*", "\"0\001+\0040\001/40\001,\0360\001.\0010\001-ÔæÇ0", "\"=\0016\004=\0017\026=\0019\035=\0018\005=\0014\003=\0015\007=\0011\003=\0012\001=\0013\001:\005=\001<\001=\001;ÔæÇ=", "", "'\005\001>Ôøò\005", "'\005\001CÔøò\005", "'\005\001IÔøò\005", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" };
/*      */ 
/* 1938 */   static final short[] DFA14_eot = DFA.unpackEncodedString("Nèøø");
/* 1939 */   static final short[] DFA14_eof = DFA.unpackEncodedString("Nèøø");
/* 1940 */   static final char[] DFA14_min = DFA.unpackEncodedStringToUnsignedChars("");
/* 1941 */   static final char[] DFA14_max = DFA.unpackEncodedStringToUnsignedChars("\001èøø\002èøø\002èøø\001èøø\001èøø\002èøø\004èøø\001èøø\003èøø=èøø");
/* 1942 */   static final short[] DFA14_accept = DFA.unpackEncodedString("\001èøø\001\005\001\001\002èøø\001\004\001èøø\001\004\001\002\004èøø\001\004\004èøø\037\002\016\003\004èøø\001\003\005èøø\001\003\004èøø");
/* 1943 */   static final short[] DFA14_special = DFA.unpackEncodedString("");
/*      */   static final short[][] DFA14_transition;
/*      */   static final String DFA17_eotS = "\nèøø\001\013\002èøø";
/*      */   static final String DFA17_eofS = "\rèøø";
/*      */   static final String DFA17_minS = "";
/*      */   static final String DFA17_maxS = "\001èøø\tèøø\001f\002èøø";
/*      */   static final String DFA17_acceptS = "\001èøø\001\001\001\002\001\003\001\004\001\005\001\006\001\007\001\b\001\t\001èøø\001\013\001\n";
/*      */   static final String DFA17_specialS = "";
/*      */   static final String[] DFA17_transitionS;
/*      */   static final short[] DFA17_eot;
/*      */   static final short[] DFA17_eof;
/*      */   static final char[] DFA17_min;
/*      */   static final char[] DFA17_max;
/*      */   static final short[] DFA17_accept;
/*      */   static final short[] DFA17_special;
/*      */   static final short[][] DFA17_transition;
/*      */   static final String DFA20_eotS = "\001èøø\002\017\002\024\005èøø\001\024\005èøø\001\024\006èøø\001\037\001\017\003\024\002èøø\001\024\001èøø\001\017\004\024\001*\004\024\001èøø\001/\0010\002\024\002èøø\002\024\0015\001\024\002èøø";
/*      */   static final String DFA20_eofS = "7èøø";
/*      */   static final String DFA20_minS = "\001\t\001K\001A\001u\001a\005èøø\001e\001èøø\001*\003èøø\001p\006èøø\0010\001I\001n\001l\001t\002èøø\001t\001èøø\001L\001i\001k\001u\001i\0010\001t\001s\001r\001o\001èøø\0020\002n\002èøø\002s\0010\001\t\002èøø";
/*      */   static final String DFA20_maxS = "\001}\001K\001A\001u\001a\005èøø\001e\001èøø\001/\003èøø\001p\006èøø\001z\001I\001n\001l\001t\002èøø\001t\001èøø\001L\001i\001k\001u\001i\001z\001t\001s\001r\001o\001èøø\002z\002n\002èøø\002s\001z\001{\002èøø";
/*      */   static final String DFA20_acceptS = "\005èøø\001\005\001\006\001\007\001\b\001\t\001èøø\001\013\001èøø\001\016\001\017\001\020\001èøø\001\022\001\023\001\024\001\021\001\026\001\027\005èøø\001\f\001\r\001èøø\001\001\nèøø\001\002\004èøø\001\003\001\004\004èøø\001\n\001\025";
/*      */   static final String DFA20_specialS = "7èøø}>";
/*      */   static final String[] DFA20_transitionS;
/*      */   static final short[] DFA20_eot;
/*      */   static final short[] DFA20_eof;
/*      */   static final char[] DFA20_min;
/*      */   static final char[] DFA20_max;
/*      */   static final short[] DFA20_accept;
/*      */   static final short[] DFA20_special;
/*      */   static final short[][] DFA20_transition;
/*      */ 
/*      */   public gUnitLexer()
/*      */   {
/*      */   }
/*      */ 
/*      */   public gUnitLexer(CharStream input)
/*      */   {
/*   53 */     this(input, new RecognizerSharedState());
/*      */   }
/*      */   public gUnitLexer(CharStream input, RecognizerSharedState state) {
/*   56 */     super(input, state);
/*      */   }
/*      */   public String getGrammarFileName() {
/*   59 */     return "org/antlr/gunit/gUnit.g";
/*      */   }
/*      */ 
/*      */   public final void mOK() throws RecognitionException {
/*      */     try {
/*   64 */       int _type = 4;
/*   65 */       int _channel = 0;
/*      */ 
/*   69 */       match("OK");
/*      */ 
/*   74 */       this.state.type = _type;
/*   75 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mFAIL() throws RecognitionException
/*      */   {
/*      */     try {
/*   85 */       int _type = 5;
/*   86 */       int _channel = 0;
/*      */ 
/*   90 */       match("FAIL");
/*      */ 
/*   95 */       this.state.type = _type;
/*   96 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__26() throws RecognitionException
/*      */   {
/*      */     try {
/*  106 */       int _type = 26;
/*  107 */       int _channel = 0;
/*      */ 
/*  111 */       match("gunit");
/*      */ 
/*  116 */       this.state.type = _type;
/*  117 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__27() throws RecognitionException
/*      */   {
/*      */     try {
/*  127 */       int _type = 27;
/*  128 */       int _channel = 0;
/*      */ 
/*  132 */       match("walks");
/*      */ 
/*  137 */       this.state.type = _type;
/*  138 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__28() throws RecognitionException
/*      */   {
/*      */     try {
/*  148 */       int _type = 28;
/*  149 */       int _channel = 0;
/*      */ 
/*  153 */       match(59);
/*      */ 
/*  157 */       this.state.type = _type;
/*  158 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__29() throws RecognitionException
/*      */   {
/*      */     try {
/*  168 */       int _type = 29;
/*  169 */       int _channel = 0;
/*      */ 
/*  173 */       match(125);
/*      */ 
/*  177 */       this.state.type = _type;
/*  178 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__30() throws RecognitionException
/*      */   {
/*      */     try {
/*  188 */       int _type = 30;
/*  189 */       int _channel = 0;
/*      */ 
/*  193 */       match(61);
/*      */ 
/*  197 */       this.state.type = _type;
/*  198 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__31() throws RecognitionException
/*      */   {
/*      */     try {
/*  208 */       int _type = 31;
/*  209 */       int _channel = 0;
/*      */ 
/*  213 */       match("@header");
/*      */ 
/*  218 */       this.state.type = _type;
/*  219 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__32() throws RecognitionException
/*      */   {
/*      */     try {
/*  229 */       int _type = 32;
/*  230 */       int _channel = 0;
/*      */ 
/*  234 */       match(58);
/*      */ 
/*  238 */       this.state.type = _type;
/*  239 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__33() throws RecognitionException
/*      */   {
/*      */     try {
/*  249 */       int _type = 33;
/*  250 */       int _channel = 0;
/*      */ 
/*  254 */       match("returns");
/*      */ 
/*  259 */       this.state.type = _type;
/*  260 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__34() throws RecognitionException
/*      */   {
/*      */     try {
/*  270 */       int _type = 34;
/*  271 */       int _channel = 0;
/*      */ 
/*  275 */       match("->");
/*      */ 
/*  280 */       this.state.type = _type;
/*  281 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mSL_COMMENT() throws RecognitionException
/*      */   {
/*      */     try {
/*  291 */       int _type = 16;
/*  292 */       int _channel = 0;
/*      */ 
/*  296 */       match("//");
/*      */       while (true)
/*      */       {
/*  301 */         int alt1 = 2;
/*  302 */         int LA1_0 = this.input.LA(1);
/*      */ 
/*  304 */         if (((LA1_0 >= 0) && (LA1_0 <= 9)) || ((LA1_0 >= 11) && (LA1_0 <= 12)) || ((LA1_0 >= 14) && (LA1_0 <= 65535))) {
/*  305 */           alt1 = 1;
/*      */         }
/*      */ 
/*  309 */         switch (alt1)
/*      */         {
/*      */         case 1:
/*  313 */           if (((this.input.LA(1) >= 0) && (this.input.LA(1) <= 9)) || ((this.input.LA(1) >= 11) && (this.input.LA(1) <= 12)) || ((this.input.LA(1) >= 14) && (this.input.LA(1) <= 65535))) {
/*  314 */             this.input.consume();
/*      */           }
/*      */           else
/*      */           {
/*  318 */             MismatchedSetException mse = new MismatchedSetException(null, this.input);
/*  319 */             recover(mse);
/*  320 */             throw mse;
/*      */           }
/*      */ 
/*      */           break;
/*      */         default:
/*  327 */           break label217;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  332 */       label217: int alt2 = 2;
/*  333 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 13:
/*  336 */         alt2 = 1;
/*      */       }
/*      */ 
/*  341 */       switch (alt2)
/*      */       {
/*      */       case 1:
/*  345 */         match(13);
/*      */       }
/*      */ 
/*  352 */       match(10);
/*  353 */       _channel = 99;
/*      */ 
/*  357 */       this.state.type = _type;
/*  358 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mML_COMMENT() throws RecognitionException
/*      */   {
/*      */     try {
/*  368 */       int _type = 17;
/*  369 */       int _channel = 0;
/*      */ 
/*  373 */       match("/*");
/*      */ 
/*  375 */       _channel = 99;
/*      */       while (true)
/*      */       {
/*  379 */         int alt3 = 2;
/*  380 */         int LA3_0 = this.input.LA(1);
/*      */ 
/*  382 */         if (LA3_0 == 42) {
/*  383 */           int LA3_1 = this.input.LA(2);
/*      */ 
/*  385 */           if (LA3_1 == 47) {
/*  386 */             alt3 = 2;
/*      */           }
/*  388 */           else if (((LA3_1 >= 0) && (LA3_1 <= 46)) || ((LA3_1 >= 48) && (LA3_1 <= 65535))) {
/*  389 */             alt3 = 1;
/*      */           }
/*      */ 
/*      */         }
/*  394 */         else if (((LA3_0 >= 0) && (LA3_0 <= 41)) || ((LA3_0 >= 43) && (LA3_0 <= 65535))) {
/*  395 */           alt3 = 1;
/*      */         }
/*      */ 
/*  399 */         switch (alt3)
/*      */         {
/*      */         case 1:
/*  403 */           matchAny();
/*      */ 
/*  406 */           break;
/*      */         default:
/*  409 */           break label149;
/*      */         }
/*      */       }
/*      */ 
/*  413 */       label149: match("*/");
/*      */ 
/*  418 */       this.state.type = _type;
/*  419 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mSTRING() throws RecognitionException
/*      */   {
/*      */     try {
/*  429 */       int _type = 12;
/*  430 */       int _channel = 0;
/*      */ 
/*  434 */       match(34);
/*      */       while (true)
/*      */       {
/*  438 */         int alt4 = 3;
/*  439 */         int LA4_0 = this.input.LA(1);
/*      */ 
/*  441 */         if (LA4_0 == 92) {
/*  442 */           alt4 = 1;
/*      */         }
/*  444 */         else if (((LA4_0 >= 0) && (LA4_0 <= 33)) || ((LA4_0 >= 35) && (LA4_0 <= 91)) || ((LA4_0 >= 93) && (LA4_0 <= 65535))) {
/*  445 */           alt4 = 2;
/*      */         }
/*      */ 
/*  449 */         switch (alt4)
/*      */         {
/*      */         case 1:
/*  453 */           mESC();
/*      */ 
/*  456 */           break;
/*      */         case 2:
/*  460 */           if (((this.input.LA(1) >= 0) && (this.input.LA(1) <= 33)) || ((this.input.LA(1) >= 35) && (this.input.LA(1) <= 91)) || ((this.input.LA(1) >= 93) && (this.input.LA(1) <= 65535))) {
/*  461 */             this.input.consume();
/*      */           }
/*      */           else
/*      */           {
/*  465 */             MismatchedSetException mse = new MismatchedSetException(null, this.input);
/*  466 */             recover(mse);
/*  467 */             throw mse;
/*      */           }
/*      */ 
/*      */           break;
/*      */         default:
/*  474 */           break label244;
/*      */         }
/*      */       }
/*      */ 
/*  478 */       label244: match(34);
/*  479 */       setText(getText().substring(1, getText().length() - 1));
/*      */ 
/*  483 */       this.state.type = _type;
/*  484 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mML_STRING() throws RecognitionException
/*      */   {
/*      */     try {
/*  494 */       int _type = 13;
/*  495 */       int _channel = 0;
/*      */ 
/*  500 */       StringBuffer buf = new StringBuffer();
/*  501 */       int i = -1;
/*  502 */       int c = this.input.LA(-1);
/*  503 */       while ((c == 32) || (c == 9)) {
/*  504 */         buf.append((char)c);
/*  505 */         c = this.input.LA(--i);
/*      */       }
/*  507 */       String indentation = buf.reverse().toString();
/*      */ 
/*  509 */       match("<<");
/*      */       while (true)
/*      */       {
/*  514 */         int alt5 = 2;
/*  515 */         int LA5_0 = this.input.LA(1);
/*      */ 
/*  517 */         if (LA5_0 == 62) {
/*  518 */           int LA5_1 = this.input.LA(2);
/*      */ 
/*  520 */           if (LA5_1 == 62) {
/*  521 */             alt5 = 2;
/*      */           }
/*  523 */           else if (((LA5_1 >= 0) && (LA5_1 <= 61)) || ((LA5_1 >= 63) && (LA5_1 <= 65535))) {
/*  524 */             alt5 = 1;
/*      */           }
/*      */ 
/*      */         }
/*  529 */         else if (((LA5_0 >= 0) && (LA5_0 <= 61)) || ((LA5_0 >= 63) && (LA5_0 <= 65535))) {
/*  530 */           alt5 = 1;
/*      */         }
/*      */ 
/*  534 */         switch (alt5)
/*      */         {
/*      */         case 1:
/*  538 */           matchAny();
/*      */ 
/*  541 */           break;
/*      */         default:
/*  544 */           break label225;
/*      */         }
/*      */       }
/*      */ 
/*  548 */       label225: match(">>");
/*      */ 
/*  551 */       String newline = System.getProperty("line.separator");
/*      */ 
/*  553 */       int oldFrontIndex = 2;
/*  554 */       int oldEndIndex = getText().length() - 2;
/*      */       int newEndIndex;
/*      */       String front;
/*      */       String end;
/*      */       int newFrontIndex;
/*      */       int newEndIndex;
/*  556 */       if (newline.length() == 1) {
/*  557 */         String front = getText().substring(2, 3);
/*  558 */         String end = getText().substring(getText().length() - 3, getText().length() - 2);
/*  559 */         int newFrontIndex = 3;
/*  560 */         newEndIndex = getText().length() - 3;
/*      */       }
/*      */       else {
/*  563 */         front = getText().substring(2, 4);
/*  564 */         end = getText().substring(getText().length() - 4, getText().length() - 2);
/*  565 */         newFrontIndex = 4;
/*  566 */         newEndIndex = getText().length() - 4;
/*      */       }
/*      */ 
/*  569 */       String temp = null;
/*  570 */       if ((front.equals(newline)) && (end.equals(newline)))
/*      */       {
/*  572 */         if ((newline.length() == 1) && (getText().length() == 5)) temp = "";
/*  573 */         else if ((newline.length() == 2) && (getText().length() == 6)) temp = ""; else
/*  574 */           temp = getText().substring(newFrontIndex, newEndIndex);
/*      */       }
/*  576 */       else if (front.equals(newline)) {
/*  577 */         temp = getText().substring(newFrontIndex, oldEndIndex);
/*      */       }
/*  579 */       else if (end.equals(newline)) {
/*  580 */         temp = getText().substring(oldFrontIndex, newEndIndex);
/*      */       }
/*      */       else {
/*  583 */         temp = getText().substring(oldFrontIndex, oldEndIndex);
/*      */       }
/*      */ 
/*  586 */       BufferedReader bufReader = new BufferedReader(new StringReader(temp));
/*  587 */       buf = new StringBuffer();
/*  588 */       String line = null;
/*  589 */       int count = 0;
/*      */       try {
/*  591 */         while ((line = bufReader.readLine()) != null) {
/*  592 */           if (line.startsWith(indentation)) line = line.substring(indentation.length());
/*  593 */           if (count > 0) buf.append(newline);
/*  594 */           buf.append(line);
/*  595 */           count++;
/*      */         }
/*  597 */         setText(buf.toString());
/*      */       }
/*      */       catch (IOException ioe) {
/*  600 */         setText(temp);
/*      */       }
/*      */ 
/*  606 */       this.state.type = _type;
/*  607 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mTOKEN_REF() throws RecognitionException
/*      */   {
/*      */     try {
/*  617 */       int _type = 11;
/*  618 */       int _channel = 0;
/*      */ 
/*  622 */       matchRange(65, 90);
/*      */       while (true)
/*      */       {
/*  626 */         int alt6 = 2;
/*  627 */         switch (this.input.LA(1))
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
/*  692 */           alt6 = 1;
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
/*  698 */         case 96: } switch (alt6)
/*      */         {
/*      */         case 1:
/*  702 */           if (((this.input.LA(1) >= 48) && (this.input.LA(1) <= 57)) || ((this.input.LA(1) >= 65) && (this.input.LA(1) <= 90)) || (this.input.LA(1) == 95) || ((this.input.LA(1) >= 97) && (this.input.LA(1) <= 122))) {
/*  703 */             this.input.consume();
/*      */           }
/*      */           else
/*      */           {
/*  707 */             MismatchedSetException mse = new MismatchedSetException(null, this.input);
/*  708 */             recover(mse);
/*  709 */             throw mse;
/*      */           }
/*      */ 
/*      */           break;
/*      */         default:
/*  716 */           break label506;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  723 */       label506: this.state.type = _type;
/*  724 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mRULE_REF() throws RecognitionException
/*      */   {
/*      */     try {
/*  734 */       int _type = 10;
/*  735 */       int _channel = 0;
/*      */ 
/*  739 */       matchRange(97, 122);
/*      */       while (true)
/*      */       {
/*  743 */         int alt7 = 2;
/*  744 */         switch (this.input.LA(1))
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
/*  809 */           alt7 = 1;
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
/*  815 */         case 96: } switch (alt7)
/*      */         {
/*      */         case 1:
/*  819 */           if (((this.input.LA(1) >= 48) && (this.input.LA(1) <= 57)) || ((this.input.LA(1) >= 65) && (this.input.LA(1) <= 90)) || (this.input.LA(1) == 95) || ((this.input.LA(1) >= 97) && (this.input.LA(1) <= 122))) {
/*  820 */             this.input.consume();
/*      */           }
/*      */           else
/*      */           {
/*  824 */             MismatchedSetException mse = new MismatchedSetException(null, this.input);
/*  825 */             recover(mse);
/*  826 */             throw mse;
/*      */           }
/*      */ 
/*      */           break;
/*      */         default:
/*  833 */           break label506;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  840 */       label506: this.state.type = _type;
/*  841 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mEXT() throws RecognitionException
/*      */   {
/*      */     try {
/*  851 */       int _type = 8;
/*  852 */       int _channel = 0;
/*      */ 
/*  856 */       match(46);
/*      */ 
/*  858 */       int cnt8 = 0;
/*      */       while (true)
/*      */       {
/*  861 */         int alt8 = 2;
/*  862 */         switch (this.input.LA(1))
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
/*  926 */           alt8 = 1;
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
/*  932 */         case 96: } switch (alt8)
/*      */         {
/*      */         case 1:
/*  936 */           if (((this.input.LA(1) >= 48) && (this.input.LA(1) <= 57)) || ((this.input.LA(1) >= 65) && (this.input.LA(1) <= 90)) || ((this.input.LA(1) >= 97) && (this.input.LA(1) <= 122))) {
/*  937 */             this.input.consume();
/*      */           }
/*      */           else
/*      */           {
/*  941 */             MismatchedSetException mse = new MismatchedSetException(null, this.input);
/*  942 */             recover(mse);
/*  943 */             throw mse;
/*      */           }
/*      */ 
/*      */           break;
/*      */         default:
/*  950 */           if (cnt8 >= 1) break label521;
/*  951 */           EarlyExitException eee = new EarlyExitException(8, this.input);
/*      */ 
/*  953 */           throw eee;
/*      */         }
/*  955 */         cnt8++;
/*      */       }
/*      */ 
/*  961 */       label521: this.state.type = _type;
/*  962 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mRETVAL() throws RecognitionException
/*      */   {
/*      */     try {
/*  972 */       int _type = 14;
/*  973 */       int _channel = 0;
/*      */ 
/*  977 */       mNESTED_RETVAL();
/*  978 */       setText(getText().substring(1, getText().length() - 1));
/*      */ 
/*  982 */       this.state.type = _type;
/*  983 */       this.state.channel = _channel;
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
/*  996 */       match(91);
/*      */       while (true)
/*      */       {
/* 1000 */         int alt9 = 3;
/* 1001 */         int LA9_0 = this.input.LA(1);
/*      */ 
/* 1003 */         if (LA9_0 == 93) {
/* 1004 */           alt9 = 3;
/*      */         }
/* 1006 */         else if (LA9_0 == 91) {
/* 1007 */           alt9 = 1;
/*      */         }
/* 1009 */         else if (((LA9_0 >= 0) && (LA9_0 <= 90)) || (LA9_0 == 92) || ((LA9_0 >= 94) && (LA9_0 <= 65535))) {
/* 1010 */           alt9 = 2;
/*      */         }
/*      */ 
/* 1014 */         switch (alt9)
/*      */         {
/*      */         case 1:
/* 1018 */           mNESTED_RETVAL();
/*      */ 
/* 1021 */           break;
/*      */         case 2:
/* 1025 */           matchAny();
/*      */ 
/* 1028 */           break;
/*      */         default:
/* 1031 */           break label120;
/*      */         }
/*      */       }
/*      */ 
/* 1035 */       label120: match(93);
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
/* 1048 */       int _type = 15;
/* 1049 */       int _channel = 0;
/*      */ 
/* 1053 */       mNESTED_AST();
/*      */       while (true)
/*      */       {
/* 1057 */         int alt11 = 2;
/* 1058 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 32:
/*      */         case 40:
/* 1062 */           alt11 = 1;
/*      */         }
/*      */ 
/* 1068 */         switch (alt11)
/*      */         {
/*      */         case 1:
/* 1073 */           int alt10 = 2;
/* 1074 */           switch (this.input.LA(1))
/*      */           {
/*      */           case 32:
/* 1077 */             alt10 = 1;
/*      */           }
/*      */ 
/* 1082 */           switch (alt10)
/*      */           {
/*      */           case 1:
/* 1086 */             match(32);
/*      */           }
/*      */ 
/* 1093 */           mNESTED_AST();
/*      */ 
/* 1096 */           break;
/*      */         default:
/* 1099 */           break label143;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1106 */       label143: this.state.type = _type;
/* 1107 */       this.state.channel = _channel;
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
/* 1120 */       match(40);
/*      */       while (true)
/*      */       {
/* 1124 */         int alt12 = 4;
/* 1125 */         int LA12_0 = this.input.LA(1);
/*      */ 
/* 1127 */         if (LA12_0 == 40) {
/* 1128 */           alt12 = 1;
/*      */         }
/* 1130 */         else if (LA12_0 == 34) {
/* 1131 */           alt12 = 2;
/*      */         }
/* 1133 */         else if (((LA12_0 >= 0) && (LA12_0 <= 33)) || ((LA12_0 >= 35) && (LA12_0 <= 39)) || ((LA12_0 >= 42) && (LA12_0 <= 65535))) {
/* 1134 */           alt12 = 3;
/*      */         }
/*      */ 
/* 1138 */         switch (alt12)
/*      */         {
/*      */         case 1:
/* 1142 */           mNESTED_AST();
/*      */ 
/* 1145 */           break;
/*      */         case 2:
/* 1149 */           mSTRING_LITERAL();
/*      */ 
/* 1152 */           break;
/*      */         case 3:
/* 1156 */           if (((this.input.LA(1) >= 0) && (this.input.LA(1) <= 33)) || ((this.input.LA(1) >= 35) && (this.input.LA(1) <= 39)) || ((this.input.LA(1) >= 42) && (this.input.LA(1) <= 65535))) {
/* 1157 */             this.input.consume();
/*      */           }
/*      */           else
/*      */           {
/* 1161 */             MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 1162 */             recover(mse);
/* 1163 */             throw mse;
/*      */           }
/*      */ 
/*      */           break;
/*      */         default:
/* 1170 */           break label244;
/*      */         }
/*      */       }
/*      */ 
/* 1174 */       label244: match(41);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mOPTIONS()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 1187 */       int _type = 7;
/* 1188 */       int _channel = 0;
/*      */ 
/* 1192 */       match("options");
/*      */       while (true)
/*      */       {
/* 1197 */         int alt13 = 2;
/* 1198 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 9:
/*      */         case 10:
/*      */         case 13:
/*      */         case 32:
/* 1204 */           alt13 = 1;
/*      */         }
/*      */ 
/* 1210 */         switch (alt13)
/*      */         {
/*      */         case 1:
/* 1214 */           mWS();
/*      */ 
/* 1217 */           break;
/*      */         default:
/* 1220 */           break label97;
/*      */         }
/*      */       }
/*      */ 
/* 1224 */       label97: match(123);
/*      */ 
/* 1228 */       this.state.type = _type;
/* 1229 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mACTION() throws RecognitionException
/*      */   {
/*      */     try {
/* 1239 */       int _type = 9;
/* 1240 */       int _channel = 0;
/*      */ 
/* 1244 */       mNESTED_ACTION();
/* 1245 */       setText(getText().substring(1, getText().length() - 1));
/*      */ 
/* 1249 */       this.state.type = _type;
/* 1250 */       this.state.channel = _channel;
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
/* 1263 */       match(123);
/*      */       while (true)
/*      */       {
/* 1267 */         int alt14 = 5;
/* 1268 */         alt14 = this.dfa14.predict(this.input);
/* 1269 */         switch (alt14)
/*      */         {
/*      */         case 1:
/* 1273 */           mNESTED_ACTION();
/*      */ 
/* 1276 */           break;
/*      */         case 2:
/* 1280 */           mSTRING_LITERAL();
/*      */ 
/* 1283 */           break;
/*      */         case 3:
/* 1287 */           mCHAR_LITERAL();
/*      */ 
/* 1290 */           break;
/*      */         case 4:
/* 1294 */           matchAny();
/*      */ 
/* 1297 */           break;
/*      */         default:
/* 1300 */           break label86;
/*      */         }
/*      */       }
/*      */ 
/* 1304 */       label86: match(125);
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
/* 1320 */       match(39);
/*      */ 
/* 1322 */       int alt15 = 2;
/* 1323 */       int LA15_0 = this.input.LA(1);
/*      */ 
/* 1325 */       if (LA15_0 == 92) {
/* 1326 */         alt15 = 1;
/*      */       }
/* 1328 */       else if (((LA15_0 >= 0) && (LA15_0 <= 38)) || ((LA15_0 >= 40) && (LA15_0 <= 91)) || ((LA15_0 >= 93) && (LA15_0 <= 65535))) {
/* 1329 */         alt15 = 2;
/*      */       }
/*      */       else {
/* 1332 */         NoViableAltException nvae = new NoViableAltException("", 15, 0, this.input);
/*      */ 
/* 1335 */         throw nvae;
/*      */       }
/* 1337 */       switch (alt15)
/*      */       {
/*      */       case 1:
/* 1341 */         mESC();
/*      */ 
/* 1344 */         break;
/*      */       case 2:
/* 1348 */         if (((this.input.LA(1) >= 0) && (this.input.LA(1) <= 38)) || ((this.input.LA(1) >= 40) && (this.input.LA(1) <= 91)) || ((this.input.LA(1) >= 93) && (this.input.LA(1) <= 65535))) {
/* 1349 */           this.input.consume();
/*      */         }
/*      */         else
/*      */         {
/* 1353 */           MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 1354 */           recover(mse);
/* 1355 */           throw mse;
/*      */         }
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 1363 */       match(39);
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
/* 1379 */       match(34);
/*      */       while (true)
/*      */       {
/* 1383 */         int alt16 = 3;
/* 1384 */         int LA16_0 = this.input.LA(1);
/*      */ 
/* 1386 */         if (LA16_0 == 92) {
/* 1387 */           alt16 = 1;
/*      */         }
/* 1389 */         else if (((LA16_0 >= 0) && (LA16_0 <= 33)) || ((LA16_0 >= 35) && (LA16_0 <= 91)) || ((LA16_0 >= 93) && (LA16_0 <= 65535))) {
/* 1390 */           alt16 = 2;
/*      */         }
/*      */ 
/* 1394 */         switch (alt16)
/*      */         {
/*      */         case 1:
/* 1398 */           mESC();
/*      */ 
/* 1401 */           break;
/*      */         case 2:
/* 1405 */           if (((this.input.LA(1) >= 0) && (this.input.LA(1) <= 33)) || ((this.input.LA(1) >= 35) && (this.input.LA(1) <= 91)) || ((this.input.LA(1) >= 93) && (this.input.LA(1) <= 65535))) {
/* 1406 */             this.input.consume();
/*      */           }
/*      */           else
/*      */           {
/* 1410 */             MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 1411 */             recover(mse);
/* 1412 */             throw mse;
/*      */           }
/*      */ 
/*      */           break;
/*      */         default:
/* 1419 */           break label225;
/*      */         }
/*      */       }
/*      */ 
/* 1423 */       label225: match(34);
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
/* 1439 */       match(92);
/*      */ 
/* 1441 */       int alt17 = 11;
/* 1442 */       alt17 = this.dfa17.predict(this.input);
/* 1443 */       switch (alt17)
/*      */       {
/*      */       case 1:
/* 1447 */         match(110);
/*      */ 
/* 1450 */         break;
/*      */       case 2:
/* 1454 */         match(114);
/*      */ 
/* 1457 */         break;
/*      */       case 3:
/* 1461 */         match(116);
/*      */ 
/* 1464 */         break;
/*      */       case 4:
/* 1468 */         match(98);
/*      */ 
/* 1471 */         break;
/*      */       case 5:
/* 1475 */         match(102);
/*      */ 
/* 1478 */         break;
/*      */       case 6:
/* 1482 */         match(34);
/*      */ 
/* 1485 */         break;
/*      */       case 7:
/* 1489 */         match(39);
/*      */ 
/* 1492 */         break;
/*      */       case 8:
/* 1496 */         match(92);
/*      */ 
/* 1499 */         break;
/*      */       case 9:
/* 1503 */         match(62);
/*      */ 
/* 1506 */         break;
/*      */       case 10:
/* 1510 */         match(117);
/* 1511 */         mXDIGIT();
/* 1512 */         mXDIGIT();
/* 1513 */         mXDIGIT();
/* 1514 */         mXDIGIT();
/*      */ 
/* 1517 */         break;
/*      */       case 11:
/* 1521 */         matchAny();
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
/* 1543 */       if (((this.input.LA(1) >= 48) && (this.input.LA(1) <= 57)) || ((this.input.LA(1) >= 65) && (this.input.LA(1) <= 70)) || ((this.input.LA(1) >= 97) && (this.input.LA(1) <= 102))) {
/* 1544 */         this.input.consume();
/*      */       }
/*      */       else
/*      */       {
/* 1548 */         MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 1549 */         recover(mse);
/* 1550 */         throw mse;
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
/* 1564 */       int _type = 22;
/* 1565 */       int _channel = 0;
/*      */ 
/* 1570 */       int cnt19 = 0;
/*      */       while (true)
/*      */       {
/* 1573 */         int alt19 = 4;
/* 1574 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 32:
/* 1577 */           alt19 = 1;
/*      */ 
/* 1579 */           break;
/*      */         case 9:
/* 1582 */           alt19 = 2;
/*      */ 
/* 1584 */           break;
/*      */         case 10:
/*      */         case 13:
/* 1588 */           alt19 = 3;
/*      */         }
/*      */ 
/* 1594 */         switch (alt19)
/*      */         {
/*      */         case 1:
/* 1598 */           match(32);
/*      */ 
/* 1601 */           break;
/*      */         case 2:
/* 1605 */           match(9);
/*      */ 
/* 1608 */           break;
/*      */         case 3:
/* 1613 */           int alt18 = 2;
/* 1614 */           switch (this.input.LA(1))
/*      */           {
/*      */           case 13:
/* 1617 */             alt18 = 1;
/*      */           }
/*      */ 
/* 1622 */           switch (alt18)
/*      */           {
/*      */           case 1:
/* 1626 */             match(13);
/*      */           }
/*      */ 
/* 1633 */           match(10);
/*      */ 
/* 1636 */           break;
/*      */         default:
/* 1639 */           if (cnt19 >= 1) break label227;
/* 1640 */           EarlyExitException eee = new EarlyExitException(19, this.input);
/*      */ 
/* 1642 */           throw eee;
/*      */         }
/* 1644 */         cnt19++;
/*      */       }
/*      */ 
/* 1647 */       label227: _channel = 99;
/*      */ 
/* 1651 */       this.state.type = _type;
/* 1652 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public void mTokens() throws RecognitionException
/*      */   {
/* 1661 */     int alt20 = 23;
/* 1662 */     alt20 = this.dfa20.predict(this.input);
/* 1663 */     switch (alt20)
/*      */     {
/*      */     case 1:
/* 1667 */       mOK();
/*      */ 
/* 1670 */       break;
/*      */     case 2:
/* 1674 */       mFAIL();
/*      */ 
/* 1677 */       break;
/*      */     case 3:
/* 1681 */       mT__26();
/*      */ 
/* 1684 */       break;
/*      */     case 4:
/* 1688 */       mT__27();
/*      */ 
/* 1691 */       break;
/*      */     case 5:
/* 1695 */       mT__28();
/*      */ 
/* 1698 */       break;
/*      */     case 6:
/* 1702 */       mT__29();
/*      */ 
/* 1705 */       break;
/*      */     case 7:
/* 1709 */       mT__30();
/*      */ 
/* 1712 */       break;
/*      */     case 8:
/* 1716 */       mT__31();
/*      */ 
/* 1719 */       break;
/*      */     case 9:
/* 1723 */       mT__32();
/*      */ 
/* 1726 */       break;
/*      */     case 10:
/* 1730 */       mT__33();
/*      */ 
/* 1733 */       break;
/*      */     case 11:
/* 1737 */       mT__34();
/*      */ 
/* 1740 */       break;
/*      */     case 12:
/* 1744 */       mSL_COMMENT();
/*      */ 
/* 1747 */       break;
/*      */     case 13:
/* 1751 */       mML_COMMENT();
/*      */ 
/* 1754 */       break;
/*      */     case 14:
/* 1758 */       mSTRING();
/*      */ 
/* 1761 */       break;
/*      */     case 15:
/* 1765 */       mML_STRING();
/*      */ 
/* 1768 */       break;
/*      */     case 16:
/* 1772 */       mTOKEN_REF();
/*      */ 
/* 1775 */       break;
/*      */     case 17:
/* 1779 */       mRULE_REF();
/*      */ 
/* 1782 */       break;
/*      */     case 18:
/* 1786 */       mEXT();
/*      */ 
/* 1789 */       break;
/*      */     case 19:
/* 1793 */       mRETVAL();
/*      */ 
/* 1796 */       break;
/*      */     case 20:
/* 1800 */       mAST();
/*      */ 
/* 1803 */       break;
/*      */     case 21:
/* 1807 */       mOPTIONS();
/*      */ 
/* 1810 */       break;
/*      */     case 22:
/* 1814 */       mACTION();
/*      */ 
/* 1817 */       break;
/*      */     case 23:
/* 1821 */       mWS();
/*      */     }
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/* 1947 */     int numStates = DFA14_transitionS.length;
/* 1948 */     DFA14_transition = new short[numStates][];
/* 1949 */     for (int i = 0; i < numStates; i++) {
/* 1950 */       DFA14_transition[i] = DFA.unpackEncodedString(DFA14_transitionS[i]);
/*      */     }
/*      */ 
/* 2193 */     DFA17_transitionS = new String[] { "\"\013\001\006\004\013\001\007\026\013\001\t\035\013\001\b\005\013\001\004\003\013\001\005\007\013\001\001\003\013\001\002\001\013\001\003\001\nÔæä\013", "", "", "", "", "", "", "", "", "", "\n\f\007èøø\006\f\032èøø\006\f", "", "" };
/*      */ 
/* 2210 */     DFA17_eot = DFA.unpackEncodedString("\nèøø\001\013\002èøø");
/* 2211 */     DFA17_eof = DFA.unpackEncodedString("\rèøø");
/* 2212 */     DFA17_min = DFA.unpackEncodedStringToUnsignedChars("");
/* 2213 */     DFA17_max = DFA.unpackEncodedStringToUnsignedChars("\001èøø\tèøø\001f\002èøø");
/* 2214 */     DFA17_accept = DFA.unpackEncodedString("\001èøø\001\001\001\002\001\003\001\004\001\005\001\006\001\007\001\b\001\t\001èøø\001\013\001\n");
/* 2215 */     DFA17_special = DFA.unpackEncodedString("");
/*      */ 
/* 2219 */     int numStates = DFA17_transitionS.length;
/* 2220 */     DFA17_transition = new short[numStates][];
/* 2221 */     for (int i = 0; i < numStates; i++) {
/* 2222 */       DFA17_transition[i] = DFA.unpackEncodedString(DFA17_transitionS[i]);
/*      */     }
/*      */ 
/* 2303 */     DFA20_transitionS = new String[] { "\002\026\002èøø\001\026\022èøø\001\026\001èøø\001\r\005èøø\001\023\004èøø\001\013\001\021\001\f\nèøø\001\t\001\005\001\016\001\007\002èøø\001\b\005\017\001\002\b\017\001\001\013\017\001\022\005èøø\006\024\001\003\007\024\001\020\002\024\001\n\004\024\001\004\003\024\001\025\001èøø\001\006", "\001\027", "\001\030", "\001\031", "\001\032", "", "", "", "", "", "\001\033", "", "\001\035\004èøø\001\034", "", "", "", "\001\036", "", "", "", "", "", "", "\n\017\007èøø\032\017\004èøø\001\017\001èøø\032\017", "\001 ", "\001!", "\001\"", "\001#", "", "", "\001$", "", "\001%", "\001&", "\001'", "\001(", "\001)", "\n\017\007èøø\032\017\004èøø\001\017\001èøø\032\017", "\001+", "\001,", "\001-", "\001.", "", "\n\024\007èøø\032\024\004èøø\001\024\001èøø\032\024", "\n\024\007èøø\032\024\004èøø\001\024\001èøø\032\024", "\0011", "\0012", "", "", "\0013", "\0014", "\n\024\007èøø\032\024\004èøø\001\024\001èøø\032\024", "\0026\002èøø\0016\022èøø\0016Zèøø\0016", "", "" };
/*      */ 
/* 2364 */     DFA20_eot = DFA.unpackEncodedString("\001èøø\002\017\002\024\005èøø\001\024\005èøø\001\024\006èøø\001\037\001\017\003\024\002èøø\001\024\001èøø\001\017\004\024\001*\004\024\001èøø\001/\0010\002\024\002èøø\002\024\0015\001\024\002èøø");
/* 2365 */     DFA20_eof = DFA.unpackEncodedString("7èøø");
/* 2366 */     DFA20_min = DFA.unpackEncodedStringToUnsignedChars("\001\t\001K\001A\001u\001a\005èøø\001e\001èøø\001*\003èøø\001p\006èøø\0010\001I\001n\001l\001t\002èøø\001t\001èøø\001L\001i\001k\001u\001i\0010\001t\001s\001r\001o\001èøø\0020\002n\002èøø\002s\0010\001\t\002èøø");
/* 2367 */     DFA20_max = DFA.unpackEncodedStringToUnsignedChars("\001}\001K\001A\001u\001a\005èøø\001e\001èøø\001/\003èøø\001p\006èøø\001z\001I\001n\001l\001t\002èøø\001t\001èøø\001L\001i\001k\001u\001i\001z\001t\001s\001r\001o\001èøø\002z\002n\002èøø\002s\001z\001{\002èøø");
/* 2368 */     DFA20_accept = DFA.unpackEncodedString("\005èøø\001\005\001\006\001\007\001\b\001\t\001èøø\001\013\001èøø\001\016\001\017\001\020\001èøø\001\022\001\023\001\024\001\021\001\026\001\027\005èøø\001\f\001\r\001èøø\001\001\nèøø\001\002\004èøø\001\003\001\004\004èøø\001\n\001\025");
/* 2369 */     DFA20_special = DFA.unpackEncodedString("7èøø}>");
/*      */ 
/* 2373 */     int numStates = DFA20_transitionS.length;
/* 2374 */     DFA20_transition = new short[numStates][];
/* 2375 */     for (int i = 0; i < numStates; i++)
/* 2376 */       DFA20_transition[i] = DFA.unpackEncodedString(DFA20_transitionS[i]);
/*      */   }
/*      */ 
/*      */   class DFA20 extends DFA
/*      */   {
/*      */     public DFA20(BaseRecognizer recognizer)
/*      */     {
/* 2383 */       this.recognizer = recognizer;
/* 2384 */       this.decisionNumber = 20;
/* 2385 */       this.eot = gUnitLexer.DFA20_eot;
/* 2386 */       this.eof = gUnitLexer.DFA20_eof;
/* 2387 */       this.min = gUnitLexer.DFA20_min;
/* 2388 */       this.max = gUnitLexer.DFA20_max;
/* 2389 */       this.accept = gUnitLexer.DFA20_accept;
/* 2390 */       this.special = gUnitLexer.DFA20_special;
/* 2391 */       this.transition = gUnitLexer.DFA20_transition;
/*      */     }
/*      */     public String getDescription() {
/* 2394 */       return "1:1: Tokens : ( OK | FAIL | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | SL_COMMENT | ML_COMMENT | STRING | ML_STRING | TOKEN_REF | RULE_REF | EXT | RETVAL | AST | OPTIONS | ACTION | WS );";
/*      */     }
/*      */   }
/*      */ 
/*      */   class DFA17 extends DFA
/*      */   {
/*      */     public DFA17(BaseRecognizer recognizer)
/*      */     {
/* 2229 */       this.recognizer = recognizer;
/* 2230 */       this.decisionNumber = 17;
/* 2231 */       this.eot = gUnitLexer.DFA17_eot;
/* 2232 */       this.eof = gUnitLexer.DFA17_eof;
/* 2233 */       this.min = gUnitLexer.DFA17_min;
/* 2234 */       this.max = gUnitLexer.DFA17_max;
/* 2235 */       this.accept = gUnitLexer.DFA17_accept;
/* 2236 */       this.special = gUnitLexer.DFA17_special;
/* 2237 */       this.transition = gUnitLexer.DFA17_transition;
/*      */     }
/*      */     public String getDescription() {
/* 2240 */       return "326:3: ( 'n' | 'r' | 't' | 'b' | 'f' | '\"' | '\\'' | '\\\\' | '>' | 'u' XDIGIT XDIGIT XDIGIT XDIGIT | . )";
/*      */     }
/*      */     public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
/* 2243 */       IntStream input = _input;
/* 2244 */       int _s = s;
/* 2245 */       switch (s) {
/*      */       case 0:
/* 2247 */         int LA17_0 = input.LA(1);
/*      */ 
/* 2249 */         s = -1;
/* 2250 */         if (LA17_0 == 110) s = 1;
/* 2252 */         else if (LA17_0 == 114) s = 2;
/* 2254 */         else if (LA17_0 == 116) s = 3;
/* 2256 */         else if (LA17_0 == 98) s = 4;
/* 2258 */         else if (LA17_0 == 102) s = 5;
/* 2260 */         else if (LA17_0 == 34) s = 6;
/* 2262 */         else if (LA17_0 == 39) s = 7;
/* 2264 */         else if (LA17_0 == 92) s = 8;
/* 2266 */         else if (LA17_0 == 62) s = 9;
/* 2268 */         else if (LA17_0 == 117) s = 10;
/* 2270 */         else if (((LA17_0 >= 0) && (LA17_0 <= 33)) || ((LA17_0 >= 35) && (LA17_0 <= 38)) || ((LA17_0 >= 40) && (LA17_0 <= 61)) || ((LA17_0 >= 63) && (LA17_0 <= 91)) || ((LA17_0 >= 93) && (LA17_0 <= 97)) || ((LA17_0 >= 99) && (LA17_0 <= 101)) || ((LA17_0 >= 103) && (LA17_0 <= 109)) || ((LA17_0 >= 111) && (LA17_0 <= 113)) || (LA17_0 == 115) || ((LA17_0 >= 118) && (LA17_0 <= 65535))) s = 11;
/*      */ 
/* 2272 */         if (s >= 0) return s;
/*      */         break;
/*      */       }
/* 2275 */       NoViableAltException nvae = new NoViableAltException(getDescription(), 17, _s, input);
/*      */ 
/* 2277 */       error(nvae);
/* 2278 */       throw nvae;
/*      */     }
/*      */   }
/*      */ 
/*      */   class DFA14 extends DFA
/*      */   {
/*      */     public DFA14(BaseRecognizer recognizer)
/*      */     {
/* 1957 */       this.recognizer = recognizer;
/* 1958 */       this.decisionNumber = 14;
/* 1959 */       this.eot = gUnitLexer.DFA14_eot;
/* 1960 */       this.eof = gUnitLexer.DFA14_eof;
/* 1961 */       this.min = gUnitLexer.DFA14_min;
/* 1962 */       this.max = gUnitLexer.DFA14_max;
/* 1963 */       this.accept = gUnitLexer.DFA14_accept;
/* 1964 */       this.special = gUnitLexer.DFA14_special;
/* 1965 */       this.transition = gUnitLexer.DFA14_transition;
/*      */     }
/*      */     public String getDescription() {
/* 1968 */       return "()* loopback of 305:2: ( options {greedy=false; k=3; } : NESTED_ACTION | STRING_LITERAL | CHAR_LITERAL | . )*";
/*      */     }
/*      */     public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
/* 1971 */       IntStream input = _input;
/* 1972 */       int _s = s;
/* 1973 */       switch (s) {
/*      */       case 0:
/* 1975 */         int LA14_0 = input.LA(1);
/*      */ 
/* 1977 */         s = -1;
/* 1978 */         if (LA14_0 == 125) s = 1;
/* 1980 */         else if (LA14_0 == 123) s = 2;
/* 1982 */         else if (LA14_0 == 34) s = 3;
/* 1984 */         else if (LA14_0 == 39) s = 4;
/* 1986 */         else if (((LA14_0 >= 0) && (LA14_0 <= 33)) || ((LA14_0 >= 35) && (LA14_0 <= 38)) || ((LA14_0 >= 40) && (LA14_0 <= 122)) || (LA14_0 == 124) || ((LA14_0 >= 126) && (LA14_0 <= 65535))) s = 5;
/*      */ 
/* 1988 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 1:
/* 1991 */         int LA14_3 = input.LA(1);
/*      */ 
/* 1993 */         s = -1;
/* 1994 */         if (LA14_3 == 92) s = 6;
/* 1996 */         else if (LA14_3 == 125) s = 7;
/* 1998 */         else if (LA14_3 == 34) s = 8;
/* 2000 */         else if (LA14_3 == 123) s = 9;
/* 2002 */         else if (LA14_3 == 39) s = 10;
/* 2004 */         else if (((LA14_3 >= 0) && (LA14_3 <= 33)) || ((LA14_3 >= 35) && (LA14_3 <= 38)) || ((LA14_3 >= 40) && (LA14_3 <= 91)) || ((LA14_3 >= 93) && (LA14_3 <= 122)) || (LA14_3 == 124) || ((LA14_3 >= 126) && (LA14_3 <= 65535))) s = 11;
/*      */ 
/* 2006 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 2:
/* 2009 */         int LA14_4 = input.LA(1);
/*      */ 
/* 2011 */         s = -1;
/* 2012 */         if (LA14_4 == 92) s = 12;
/* 2014 */         else if (LA14_4 == 125) s = 13;
/* 2016 */         else if (LA14_4 == 123) s = 14;
/* 2018 */         else if (LA14_4 == 34) s = 15;
/* 2020 */         else if (((LA14_4 >= 0) && (LA14_4 <= 33)) || ((LA14_4 >= 35) && (LA14_4 <= 38)) || ((LA14_4 >= 40) && (LA14_4 <= 91)) || ((LA14_4 >= 93) && (LA14_4 <= 122)) || (LA14_4 == 124) || ((LA14_4 >= 126) && (LA14_4 <= 65535))) s = 16;
/* 2022 */         else if (LA14_4 == 39) s = 5;
/*      */ 
/* 2024 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 3:
/* 2027 */         int LA14_6 = input.LA(1);
/*      */ 
/* 2029 */         s = -1;
/* 2030 */         if (LA14_6 == 110) s = 18;
/* 2032 */         else if (LA14_6 == 114) s = 19;
/* 2034 */         else if (LA14_6 == 116) s = 20;
/* 2036 */         else if (LA14_6 == 98) s = 21;
/* 2038 */         else if (LA14_6 == 102) s = 22;
/* 2040 */         else if (LA14_6 == 34) s = 23;
/* 2042 */         else if (LA14_6 == 39) s = 24;
/* 2044 */         else if (LA14_6 == 92) s = 25;
/* 2046 */         else if (LA14_6 == 62) s = 26;
/* 2048 */         else if (LA14_6 == 117) s = 27;
/* 2050 */         else if (LA14_6 == 125) s = 28;
/* 2052 */         else if (LA14_6 == 123) s = 29;
/* 2054 */         else if (((LA14_6 >= 0) && (LA14_6 <= 33)) || ((LA14_6 >= 35) && (LA14_6 <= 38)) || ((LA14_6 >= 40) && (LA14_6 <= 61)) || ((LA14_6 >= 63) && (LA14_6 <= 91)) || ((LA14_6 >= 93) && (LA14_6 <= 97)) || ((LA14_6 >= 99) && (LA14_6 <= 101)) || ((LA14_6 >= 103) && (LA14_6 <= 109)) || ((LA14_6 >= 111) && (LA14_6 <= 113)) || (LA14_6 == 115) || ((LA14_6 >= 118) && (LA14_6 <= 122)) || (LA14_6 == 124) || ((LA14_6 >= 126) && (LA14_6 <= 65535))) s = 30;
/*      */ 
/* 2056 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 4:
/* 2059 */         int LA14_9 = input.LA(1);
/*      */ 
/* 2061 */         s = -1;
/* 2062 */         if (LA14_9 == 34) s = 31;
/* 2064 */         else if (LA14_9 == 92) s = 32;
/* 2066 */         else if (LA14_9 == 123) s = 33;
/* 2068 */         else if (LA14_9 == 39) s = 34;
/* 2070 */         else if (LA14_9 == 125) s = 35;
/* 2072 */         else if (((LA14_9 >= 0) && (LA14_9 <= 33)) || ((LA14_9 >= 35) && (LA14_9 <= 38)) || ((LA14_9 >= 40) && (LA14_9 <= 91)) || ((LA14_9 >= 93) && (LA14_9 <= 122)) || (LA14_9 == 124) || ((LA14_9 >= 126) && (LA14_9 <= 65535))) s = 36;
/*      */ 
/* 2074 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 5:
/* 2077 */         int LA14_10 = input.LA(1);
/*      */ 
/* 2079 */         s = -1;
/* 2080 */         if (LA14_10 == 34) s = 37;
/* 2082 */         else if (LA14_10 == 92) s = 38;
/* 2084 */         else if (LA14_10 == 125) s = 39;
/* 2086 */         else if (LA14_10 == 39) s = 40;
/* 2088 */         else if (LA14_10 == 123) s = 41;
/* 2090 */         else if (((LA14_10 >= 0) && (LA14_10 <= 33)) || ((LA14_10 >= 35) && (LA14_10 <= 38)) || ((LA14_10 >= 40) && (LA14_10 <= 91)) || ((LA14_10 >= 93) && (LA14_10 <= 122)) || (LA14_10 == 124) || ((LA14_10 >= 126) && (LA14_10 <= 65535))) s = 42;
/*      */ 
/* 2092 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 6:
/* 2095 */         int LA14_11 = input.LA(1);
/*      */ 
/* 2097 */         s = -1;
/* 2098 */         if (LA14_11 == 34) s = 43;
/* 2100 */         else if (LA14_11 == 92) s = 44;
/* 2102 */         else if (LA14_11 == 125) s = 45;
/* 2104 */         else if (LA14_11 == 123) s = 46;
/* 2106 */         else if (LA14_11 == 39) s = 47;
/* 2108 */         else if (((LA14_11 >= 0) && (LA14_11 <= 33)) || ((LA14_11 >= 35) && (LA14_11 <= 38)) || ((LA14_11 >= 40) && (LA14_11 <= 91)) || ((LA14_11 >= 93) && (LA14_11 <= 122)) || (LA14_11 == 124) || ((LA14_11 >= 126) && (LA14_11 <= 65535))) s = 48;
/*      */ 
/* 2110 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 7:
/* 2113 */         int LA14_12 = input.LA(1);
/*      */ 
/* 2115 */         s = -1;
/* 2116 */         if (LA14_12 == 110) s = 49;
/* 2118 */         else if (LA14_12 == 114) s = 50;
/* 2120 */         else if (LA14_12 == 116) s = 51;
/* 2122 */         else if (LA14_12 == 98) s = 52;
/* 2124 */         else if (LA14_12 == 102) s = 53;
/* 2126 */         else if (LA14_12 == 34) s = 54;
/* 2128 */         else if (LA14_12 == 39) s = 55;
/* 2130 */         else if (LA14_12 == 92) s = 56;
/* 2132 */         else if (LA14_12 == 62) s = 57;
/* 2134 */         else if (LA14_12 == 117) s = 58;
/* 2136 */         else if (LA14_12 == 125) s = 59;
/* 2138 */         else if (LA14_12 == 123) s = 60;
/* 2140 */         else if (((LA14_12 >= 0) && (LA14_12 <= 33)) || ((LA14_12 >= 35) && (LA14_12 <= 38)) || ((LA14_12 >= 40) && (LA14_12 <= 61)) || ((LA14_12 >= 63) && (LA14_12 <= 91)) || ((LA14_12 >= 93) && (LA14_12 <= 97)) || ((LA14_12 >= 99) && (LA14_12 <= 101)) || ((LA14_12 >= 103) && (LA14_12 <= 109)) || ((LA14_12 >= 111) && (LA14_12 <= 113)) || (LA14_12 == 115) || ((LA14_12 >= 118) && (LA14_12 <= 122)) || (LA14_12 == 124) || ((LA14_12 >= 126) && (LA14_12 <= 65535))) s = 61;
/*      */ 
/* 2142 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 8:
/* 2145 */         int LA14_14 = input.LA(1);
/*      */ 
/* 2147 */         s = -1;
/* 2148 */         if (LA14_14 == 39) s = 62;
/* 2150 */         else if (((LA14_14 >= 0) && (LA14_14 <= 38)) || ((LA14_14 >= 40) && (LA14_14 <= 65535))) s = 5;
/*      */ 
/* 2152 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 9:
/* 2155 */         int LA14_15 = input.LA(1);
/*      */ 
/* 2157 */         s = -1;
/* 2158 */         if (LA14_15 == 39) s = 67;
/* 2160 */         else if (((LA14_15 >= 0) && (LA14_15 <= 38)) || ((LA14_15 >= 40) && (LA14_15 <= 65535))) s = 5;
/*      */ 
/* 2162 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 10:
/* 2165 */         int LA14_16 = input.LA(1);
/*      */ 
/* 2167 */         s = -1;
/* 2168 */         if (LA14_16 == 39) s = 73;
/* 2170 */         else if (((LA14_16 >= 0) && (LA14_16 <= 38)) || ((LA14_16 >= 40) && (LA14_16 <= 65535))) s = 5;
/*      */ 
/* 2172 */         if (s >= 0) return s;
/*      */         break;
/*      */       }
/* 2175 */       NoViableAltException nvae = new NoViableAltException(getDescription(), 14, _s, input);
/*      */ 
/* 2177 */       error(nvae);
/* 2178 */       throw nvae;
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.gUnitLexer
 * JD-Core Version:    0.6.2
 */