/*      */ package org.stringtemplate.v4.compiler;
/*      */ 
/*      */ import java.io.File;
/*      */ import org.antlr.runtime.BaseRecognizer;
/*      */ import org.antlr.runtime.CharStream;
/*      */ import org.antlr.runtime.CommonToken;
/*      */ import org.antlr.runtime.DFA;
/*      */ import org.antlr.runtime.Lexer;
/*      */ import org.antlr.runtime.MismatchedSetException;
/*      */ import org.antlr.runtime.MismatchedTokenException;
/*      */ import org.antlr.runtime.NoViableAltException;
/*      */ import org.antlr.runtime.RecognitionException;
/*      */ import org.antlr.runtime.RecognizerSharedState;
/*      */ import org.antlr.runtime.Token;
/*      */ import org.stringtemplate.v4.STGroup;
/*      */ import org.stringtemplate.v4.misc.ErrorManager;
/*      */ import org.stringtemplate.v4.misc.ErrorType;
/*      */ 
/*      */ public class GroupLexer extends Lexer
/*      */ {
/*      */   public static final int EOF = -1;
/*      */   public static final int T__14 = 14;
/*      */   public static final int T__15 = 15;
/*      */   public static final int T__16 = 16;
/*      */   public static final int T__17 = 17;
/*      */   public static final int T__18 = 18;
/*      */   public static final int T__19 = 19;
/*      */   public static final int T__20 = 20;
/*      */   public static final int T__21 = 21;
/*      */   public static final int T__22 = 22;
/*      */   public static final int T__23 = 23;
/*      */   public static final int T__24 = 24;
/*      */   public static final int T__25 = 25;
/*      */   public static final int T__26 = 26;
/*      */   public static final int T__27 = 27;
/*      */   public static final int T__28 = 28;
/*      */   public static final int T__29 = 29;
/*      */   public static final int ANONYMOUS_TEMPLATE = 4;
/*      */   public static final int BIGSTRING = 5;
/*      */   public static final int BIGSTRING_NO_NL = 6;
/*      */   public static final int COMMENT = 7;
/*      */   public static final int FALSE = 8;
/*      */   public static final int ID = 9;
/*      */   public static final int LINE_COMMENT = 10;
/*      */   public static final int STRING = 11;
/*      */   public static final int TRUE = 12;
/*      */   public static final int WS = 13;
/*      */   public STGroup group;
/* 1297 */   protected DFA8 dfa8 = new DFA8(this);
/*      */   static final String DFA8_eotS = "\001ğ¿¿\002\020\004ğ¿¿\001\031\005ğ¿¿\003\020\006ğ¿¿\002\020\002ğ¿¿\003\020\004ğ¿¿\007\020\001/\005\020\0015\001ğ¿¿\002\020\0018\002\020\001ğ¿¿\002\020\001ğ¿¿\001\020\001>\001?\002\020\002ğ¿¿\004\020\001F\001G\002ğ¿¿";
/*      */   static final String DFA8_eofS = "Hğ¿¿";
/*      */   static final String DFA8_minS = "\001\t\001a\001r\004ğ¿¿\001:\005ğ¿¿\001e\001r\001m\002ğ¿¿\001%\001ğ¿¿\001*\001ğ¿¿\001l\001u\002ğ¿¿\001f\001o\001p\004ğ¿¿\001s\001e\001a\001i\001u\001l\001e\001-\001u\001m\001p\001e\001r\001-\001ğ¿¿\001l\001i\001-\001m\001t\001ğ¿¿\002t\001ğ¿¿\001e\002-\001e\001n\002ğ¿¿\001r\001t\002s\002-\002ğ¿¿";
/*      */   static final String DFA8_maxS = "\001{\001a\001r\004ğ¿¿\001:\005ğ¿¿\001e\001r\001m\002ğ¿¿\001<\001ğ¿¿\001/\001ğ¿¿\001l\001u\002ğ¿¿\001l\001o\001p\004ğ¿¿\001s\001e\001a\001i\001u\001o\001e\001z\001u\001m\001p\001e\001r\001z\001ğ¿¿\001l\001i\001z\001m\001t\001ğ¿¿\002t\001ğ¿¿\001e\002z\001e\001n\002ğ¿¿\001r\001t\002s\002z\002ğ¿¿";
/*      */   static final String DFA8_acceptS = "\003ğ¿¿\001\003\001\004\001\005\001\006\001ğ¿¿\001\t\001\n\001\013\001\f\001\r\003ğ¿¿\001\023\001\024\001ğ¿¿\001\027\001ğ¿¿\001\032\002ğ¿¿\001\b\001\007\003ğ¿¿\001\025\001\026\001\030\001\031\016ğ¿¿\001\002\005ğ¿¿\001\001\002ğ¿¿\001\020\005ğ¿¿\001\022\001\016\006ğ¿¿\001\017\001\021";
/*      */   static final String DFA8_specialS = "Hğ¿¿}>";
/* 1325 */   static final String[] DFA8_transitionS = { "\002\025\002ğ¿¿\001\025\022ğ¿¿\001\025\001ğ¿¿\001\021\005ğ¿¿\001\003\001\004\002ğ¿¿\001\005\001ğ¿¿\001\006\001\024\nğ¿¿\001\007\001\b\001\022\001\t\002ğ¿¿\001\n\032\020\001\013\001ğ¿¿\001\f\001ğ¿¿\001\020\001ğ¿¿\003\020\001\r\001\020\001\001\001\016\001\020\001\017\n\020\001\002\006\020\001\023", "\001\026", "\001\027", "", "", "", "", "\001\030", "", "", "", "", "", "\001\032", "\001\033", "\001\034", "", "", "\001\035\026ğ¿¿\001\036", "", "\001\037\004ğ¿¿\001 ", "", "\001!", "\001\"", "", "", "\001#\005ğ¿¿\001$", "\001%", "\001&", "", "", "", "", "\001'", "\001(", "\001)", "\001*", "\001+", "\001,\002ğ¿¿\001-", "\001.", "\001\020\002ğ¿¿\n\020\007ğ¿¿\032\020\004ğ¿¿\001\020\001ğ¿¿\032\020", "\0010", "\0011", "\0012", "\0013", "\0014", "\001\020\002ğ¿¿\n\020\007ğ¿¿\032\020\004ğ¿¿\001\020\001ğ¿¿\032\020", "", "\0016", "\0017", "\001\020\002ğ¿¿\n\020\007ğ¿¿\032\020\004ğ¿¿\001\020\001ğ¿¿\032\020", "\0019", "\001:", "", "\001;", "\001<", "", "\001=", "\001\020\002ğ¿¿\n\020\007ğ¿¿\032\020\004ğ¿¿\001\020\001ğ¿¿\032\020", "\001\020\002ğ¿¿\n\020\007ğ¿¿\032\020\004ğ¿¿\001\020\001ğ¿¿\032\020", "\001@", "\001A", "", "", "\001B", "\001C", "\001D", "\001E", "\001\020\002ğ¿¿\n\020\007ğ¿¿\032\020\004ğ¿¿\001\020\001ğ¿¿\032\020", "\001\020\002ğ¿¿\n\020\007ğ¿¿\032\020\004ğ¿¿\001\020\001ğ¿¿\032\020", "", "" };
/*      */ 
/* 1403 */   static final short[] DFA8_eot = DFA.unpackEncodedString("\001ğ¿¿\002\020\004ğ¿¿\001\031\005ğ¿¿\003\020\006ğ¿¿\002\020\002ğ¿¿\003\020\004ğ¿¿\007\020\001/\005\020\0015\001ğ¿¿\002\020\0018\002\020\001ğ¿¿\002\020\001ğ¿¿\001\020\001>\001?\002\020\002ğ¿¿\004\020\001F\001G\002ğ¿¿");
/* 1404 */   static final short[] DFA8_eof = DFA.unpackEncodedString("Hğ¿¿");
/* 1405 */   static final char[] DFA8_min = DFA.unpackEncodedStringToUnsignedChars("\001\t\001a\001r\004ğ¿¿\001:\005ğ¿¿\001e\001r\001m\002ğ¿¿\001%\001ğ¿¿\001*\001ğ¿¿\001l\001u\002ğ¿¿\001f\001o\001p\004ğ¿¿\001s\001e\001a\001i\001u\001l\001e\001-\001u\001m\001p\001e\001r\001-\001ğ¿¿\001l\001i\001-\001m\001t\001ğ¿¿\002t\001ğ¿¿\001e\002-\001e\001n\002ğ¿¿\001r\001t\002s\002-\002ğ¿¿");
/* 1406 */   static final char[] DFA8_max = DFA.unpackEncodedStringToUnsignedChars("\001{\001a\001r\004ğ¿¿\001:\005ğ¿¿\001e\001r\001m\002ğ¿¿\001<\001ğ¿¿\001/\001ğ¿¿\001l\001u\002ğ¿¿\001l\001o\001p\004ğ¿¿\001s\001e\001a\001i\001u\001o\001e\001z\001u\001m\001p\001e\001r\001z\001ğ¿¿\001l\001i\001z\001m\001t\001ğ¿¿\002t\001ğ¿¿\001e\002z\001e\001n\002ğ¿¿\001r\001t\002s\002z\002ğ¿¿");
/* 1407 */   static final short[] DFA8_accept = DFA.unpackEncodedString("\003ğ¿¿\001\003\001\004\001\005\001\006\001ğ¿¿\001\t\001\n\001\013\001\f\001\r\003ğ¿¿\001\023\001\024\001ğ¿¿\001\027\001ğ¿¿\001\032\002ğ¿¿\001\b\001\007\003ğ¿¿\001\025\001\026\001\030\001\031\016ğ¿¿\001\002\005ğ¿¿\001\001\002ğ¿¿\001\020\005ğ¿¿\001\022\001\016\006ğ¿¿\001\017\001\021");
/* 1408 */   static final short[] DFA8_special = DFA.unpackEncodedString("Hğ¿¿}>");
/*      */   static final short[][] DFA8_transition;
/*      */ 
/*      */   public void reportError(RecognitionException e)
/*      */   {
/*   74 */     String msg = null;
/*   75 */     if ((e instanceof NoViableAltException)) {
/*   76 */       msg = "invalid character '" + (char)this.input.LA(1) + "'";
/*      */     }
/*   78 */     else if (((e instanceof MismatchedTokenException)) && (((MismatchedTokenException)e).expecting == 34)) {
/*   79 */       msg = "unterminated string";
/*      */     }
/*      */     else {
/*   82 */       msg = getErrorMessage(e, getTokenNames());
/*      */     }
/*   84 */     this.group.errMgr.groupSyntaxError(ErrorType.SYNTAX_ERROR, getSourceName(), e, msg);
/*      */   }
/*      */   public String getSourceName() {
/*   87 */     String fullFileName = super.getSourceName();
/*   88 */     File f = new File(fullFileName);
/*   89 */     return f.getName();
/*      */   }
/*      */ 
/*      */   public Lexer[] getDelegates()
/*      */   {
/*   96 */     return new Lexer[0];
/*      */   }
/*      */   public GroupLexer() {
/*      */   }
/*      */   public GroupLexer(CharStream input) {
/*  101 */     this(input, new RecognizerSharedState());
/*      */   }
/*      */   public GroupLexer(CharStream input, RecognizerSharedState state) {
/*  104 */     super(input, state);
/*      */   }
/*  106 */   public String getGrammarFileName() { return "/usr/local/website/st/depot/stringtemplate4/src/org/stringtemplate/v4/compiler/Group.g"; }
/*      */ 
/*      */   public final void mFALSE() throws RecognitionException
/*      */   {
/*      */     try {
/*  111 */       int _type = 8;
/*  112 */       int _channel = 0;
/*      */ 
/*  116 */       match("false");
/*      */ 
/*  122 */       this.state.type = _type;
/*  123 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mTRUE() throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  134 */       int _type = 12;
/*  135 */       int _channel = 0;
/*      */ 
/*  139 */       match("true");
/*      */ 
/*  145 */       this.state.type = _type;
/*  146 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__14() throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  157 */       int _type = 14;
/*  158 */       int _channel = 0;
/*      */ 
/*  162 */       match(40);
/*      */ 
/*  166 */       this.state.type = _type;
/*  167 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__15() throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  178 */       int _type = 15;
/*  179 */       int _channel = 0;
/*      */ 
/*  183 */       match(41);
/*      */ 
/*  187 */       this.state.type = _type;
/*  188 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__16() throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  199 */       int _type = 16;
/*  200 */       int _channel = 0;
/*      */ 
/*  204 */       match(44);
/*      */ 
/*  208 */       this.state.type = _type;
/*  209 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__17() throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  220 */       int _type = 17;
/*  221 */       int _channel = 0;
/*      */ 
/*  225 */       match(46);
/*      */ 
/*  229 */       this.state.type = _type;
/*  230 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__18() throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  241 */       int _type = 18;
/*  242 */       int _channel = 0;
/*      */ 
/*  246 */       match(58);
/*      */ 
/*  250 */       this.state.type = _type;
/*  251 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__19() throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  262 */       int _type = 19;
/*  263 */       int _channel = 0;
/*      */ 
/*  267 */       match("::=");
/*      */ 
/*  273 */       this.state.type = _type;
/*  274 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__20() throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  285 */       int _type = 20;
/*  286 */       int _channel = 0;
/*      */ 
/*  290 */       match(59);
/*      */ 
/*  294 */       this.state.type = _type;
/*  295 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__21() throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  306 */       int _type = 21;
/*  307 */       int _channel = 0;
/*      */ 
/*  311 */       match(61);
/*      */ 
/*  315 */       this.state.type = _type;
/*  316 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__22() throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  327 */       int _type = 22;
/*  328 */       int _channel = 0;
/*      */ 
/*  332 */       match(64);
/*      */ 
/*  336 */       this.state.type = _type;
/*  337 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__23() throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  348 */       int _type = 23;
/*  349 */       int _channel = 0;
/*      */ 
/*  353 */       match(91);
/*      */ 
/*  357 */       this.state.type = _type;
/*  358 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__24() throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  369 */       int _type = 24;
/*  370 */       int _channel = 0;
/*      */ 
/*  374 */       match(93);
/*      */ 
/*  378 */       this.state.type = _type;
/*  379 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__25() throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  390 */       int _type = 25;
/*  391 */       int _channel = 0;
/*      */ 
/*  395 */       match("default");
/*      */ 
/*  401 */       this.state.type = _type;
/*  402 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__26() throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  413 */       int _type = 26;
/*  414 */       int _channel = 0;
/*      */ 
/*  418 */       match("delimiters");
/*      */ 
/*  424 */       this.state.type = _type;
/*  425 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__27() throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  436 */       int _type = 27;
/*  437 */       int _channel = 0;
/*      */ 
/*  441 */       match("group");
/*      */ 
/*  447 */       this.state.type = _type;
/*  448 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__28() throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  459 */       int _type = 28;
/*  460 */       int _channel = 0;
/*      */ 
/*  464 */       match("implements");
/*      */ 
/*  470 */       this.state.type = _type;
/*  471 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__29() throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  482 */       int _type = 29;
/*  483 */       int _channel = 0;
/*      */ 
/*  487 */       match("import");
/*      */ 
/*  493 */       this.state.type = _type;
/*  494 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mID() throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  505 */       int _type = 9;
/*  506 */       int _channel = 0;
/*      */ 
/*  510 */       if (((this.input.LA(1) >= 65) && (this.input.LA(1) <= 90)) || (this.input.LA(1) == 95) || ((this.input.LA(1) >= 97) && (this.input.LA(1) <= 122))) {
/*  511 */         this.input.consume();
/*      */       }
/*      */       else {
/*  514 */         MismatchedSetException mse = new MismatchedSetException(null, this.input);
/*  515 */         recover(mse);
/*  516 */         throw mse;
/*      */       }
/*      */ 
/*      */       while (true)
/*      */       {
/*  523 */         int alt1 = 2;
/*  524 */         int LA1_0 = this.input.LA(1);
/*      */ 
/*  526 */         if ((LA1_0 == 45) || ((LA1_0 >= 48) && (LA1_0 <= 57)) || ((LA1_0 >= 65) && (LA1_0 <= 90)) || (LA1_0 == 95) || ((LA1_0 >= 97) && (LA1_0 <= 122))) {
/*  527 */           alt1 = 1;
/*      */         }
/*      */ 
/*  531 */         switch (alt1)
/*      */         {
/*      */         case 1:
/*  535 */           if ((this.input.LA(1) == 45) || ((this.input.LA(1) >= 48) && (this.input.LA(1) <= 57)) || ((this.input.LA(1) >= 65) && (this.input.LA(1) <= 90)) || (this.input.LA(1) == 95) || ((this.input.LA(1) >= 97) && (this.input.LA(1) <= 122))) {
/*  536 */             this.input.consume();
/*      */           }
/*      */           else {
/*  539 */             MismatchedSetException mse = new MismatchedSetException(null, this.input);
/*  540 */             recover(mse);
/*  541 */             throw mse;
/*      */           }
/*      */ 
/*      */           break;
/*      */         default:
/*  549 */           break label365;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  556 */       label365: this.state.type = _type;
/*  557 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mSTRING() throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  568 */       int _type = 11;
/*  569 */       int _channel = 0;
/*      */ 
/*  573 */       match(34);
/*      */       while (true)
/*      */       {
/*  578 */         int alt2 = 5;
/*  579 */         int LA2_0 = this.input.LA(1);
/*      */ 
/*  581 */         if (LA2_0 == 92) {
/*  582 */           int LA2_2 = this.input.LA(2);
/*      */ 
/*  584 */           if (LA2_2 == 34) {
/*  585 */             alt2 = 1;
/*      */           }
/*  587 */           else if (((LA2_2 >= 0) && (LA2_2 <= 33)) || ((LA2_2 >= 35) && (LA2_2 <= 65535))) {
/*  588 */             alt2 = 2;
/*      */           }
/*      */ 
/*      */         }
/*  593 */         else if (LA2_0 == 10) {
/*  594 */           alt2 = 3;
/*      */         }
/*  596 */         else if (((LA2_0 >= 0) && (LA2_0 <= 9)) || ((LA2_0 >= 11) && (LA2_0 <= 33)) || ((LA2_0 >= 35) && (LA2_0 <= 91)) || ((LA2_0 >= 93) && (LA2_0 <= 65535))) {
/*  597 */           alt2 = 4;
/*      */         }
/*      */ 
/*  601 */         switch (alt2)
/*      */         {
/*      */         case 1:
/*  605 */           match(92);
/*      */ 
/*  607 */           match(34);
/*      */ 
/*  610 */           break;
/*      */         case 2:
/*  614 */           match(92);
/*      */ 
/*  616 */           if (((this.input.LA(1) >= 0) && (this.input.LA(1) <= 33)) || ((this.input.LA(1) >= 35) && (this.input.LA(1) <= 65535))) {
/*  617 */             this.input.consume();
/*      */           }
/*      */           else {
/*  620 */             MismatchedSetException mse = new MismatchedSetException(null, this.input);
/*  621 */             recover(mse);
/*  622 */             throw mse;
/*      */           }
/*      */ 
/*      */           break;
/*      */         case 3:
/*  632 */           String msg = "\\n in string";
/*  633 */           NoViableAltException e = new NoViableAltException("", 0, 0, this.input);
/*  634 */           this.group.errMgr.groupLexerError(ErrorType.SYNTAX_ERROR, getSourceName(), e, msg);
/*      */ 
/*  637 */           match(10);
/*      */ 
/*  640 */           break;
/*      */         case 4:
/*  644 */           if (((this.input.LA(1) >= 0) && (this.input.LA(1) <= 9)) || ((this.input.LA(1) >= 11) && (this.input.LA(1) <= 33)) || ((this.input.LA(1) >= 35) && (this.input.LA(1) <= 91)) || ((this.input.LA(1) >= 93) && (this.input.LA(1) <= 65535))) {
/*  645 */             this.input.consume();
/*      */           }
/*      */           else {
/*  648 */             MismatchedSetException mse = new MismatchedSetException(null, this.input);
/*  649 */             recover(mse);
/*  650 */             throw mse;
/*      */           }
/*      */ 
/*      */           break;
/*      */         default:
/*  658 */           break label512;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  663 */       label512: match(34);
/*      */ 
/*  666 */       String txt = getText().replaceAll("\\\\\"", "\"");
/*  667 */       setText(txt);
/*      */ 
/*  672 */       this.state.type = _type;
/*  673 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mBIGSTRING_NO_NL() throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  684 */       int _type = 6;
/*  685 */       int _channel = 0;
/*      */ 
/*  689 */       match("<%");
/*      */       while (true)
/*      */       {
/*  696 */         int alt3 = 2;
/*  697 */         int LA3_0 = this.input.LA(1);
/*      */ 
/*  699 */         if (LA3_0 == 37) {
/*  700 */           int LA3_1 = this.input.LA(2);
/*      */ 
/*  702 */           if (LA3_1 == 62) {
/*  703 */             alt3 = 2;
/*      */           }
/*  705 */           else if (((LA3_1 >= 0) && (LA3_1 <= 61)) || ((LA3_1 >= 63) && (LA3_1 <= 65535))) {
/*  706 */             alt3 = 1;
/*      */           }
/*      */ 
/*      */         }
/*  711 */         else if (((LA3_0 >= 0) && (LA3_0 <= 36)) || ((LA3_0 >= 38) && (LA3_0 <= 65535))) {
/*  712 */           alt3 = 1;
/*      */         }
/*      */ 
/*  716 */         switch (alt3)
/*      */         {
/*      */         case 1:
/*  720 */           matchAny();
/*      */ 
/*  723 */           break;
/*      */         default:
/*  726 */           break label149;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  731 */       label149: match("%>");
/*      */ 
/*  737 */       this.state.type = _type;
/*  738 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mBIGSTRING() throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  749 */       int _type = 5;
/*  750 */       int _channel = 0;
/*      */ 
/*  754 */       match("<<");
/*      */       while (true)
/*      */       {
/*  761 */         int alt4 = 4;
/*  762 */         int LA4_0 = this.input.LA(1);
/*      */ 
/*  764 */         if (LA4_0 == 62) {
/*  765 */           int LA4_1 = this.input.LA(2);
/*      */ 
/*  767 */           if (LA4_1 == 62) {
/*  768 */             alt4 = 4;
/*      */           }
/*  770 */           else if (((LA4_1 >= 0) && (LA4_1 <= 61)) || ((LA4_1 >= 63) && (LA4_1 <= 65535))) {
/*  771 */             alt4 = 3;
/*      */           }
/*      */ 
/*      */         }
/*  776 */         else if (LA4_0 == 92) {
/*  777 */           int LA4_2 = this.input.LA(2);
/*      */ 
/*  779 */           if (LA4_2 == 62) {
/*  780 */             alt4 = 1;
/*      */           }
/*  782 */           else if (((LA4_2 >= 0) && (LA4_2 <= 61)) || ((LA4_2 >= 63) && (LA4_2 <= 65535))) {
/*  783 */             alt4 = 2;
/*      */           }
/*      */ 
/*      */         }
/*  788 */         else if (((LA4_0 >= 0) && (LA4_0 <= 61)) || ((LA4_0 >= 63) && (LA4_0 <= 91)) || ((LA4_0 >= 93) && (LA4_0 <= 65535))) {
/*  789 */           alt4 = 3;
/*      */         }
/*      */ 
/*  793 */         switch (alt4)
/*      */         {
/*      */         case 1:
/*  797 */           match(92);
/*      */ 
/*  799 */           match(62);
/*      */ 
/*  802 */           break;
/*      */         case 2:
/*  806 */           match(92);
/*      */ 
/*  808 */           if (((this.input.LA(1) >= 0) && (this.input.LA(1) <= 61)) || ((this.input.LA(1) >= 63) && (this.input.LA(1) <= 65535))) {
/*  809 */             this.input.consume();
/*      */           }
/*      */           else {
/*  812 */             MismatchedSetException mse = new MismatchedSetException(null, this.input);
/*  813 */             recover(mse);
/*  814 */             throw mse;
/*      */           }
/*      */ 
/*      */           break;
/*      */         case 3:
/*  823 */           if (((this.input.LA(1) >= 0) && (this.input.LA(1) <= 91)) || ((this.input.LA(1) >= 93) && (this.input.LA(1) <= 65535))) {
/*  824 */             this.input.consume();
/*      */           }
/*      */           else {
/*  827 */             MismatchedSetException mse = new MismatchedSetException(null, this.input);
/*  828 */             recover(mse);
/*  829 */             throw mse;
/*      */           }
/*      */ 
/*      */           break;
/*      */         default:
/*  837 */           break label429;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  842 */       label429: match(">>");
/*      */ 
/*  847 */       String txt = getText().replaceAll("\\\\>", ">");
/*  848 */       setText(txt);
/*      */ 
/*  853 */       this.state.type = _type;
/*  854 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mANONYMOUS_TEMPLATE() throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  865 */       int _type = 4;
/*  866 */       int _channel = 0;
/*      */ 
/*  870 */       match(123);
/*      */ 
/*  873 */       Token templateToken = new CommonToken(this.input, 4, 0, getCharIndex(), getCharIndex());
/*  874 */       STLexer lexer = new STLexer(this.group.errMgr, this.input, templateToken, this.group.delimiterStartChar, this.group.delimiterStopChar);
/*      */ 
/*  876 */       lexer.subtemplateDepth = 1;
/*  877 */       Token t = lexer.nextToken();
/*  878 */       while ((lexer.subtemplateDepth >= 1) || (t.getType() != 21)) {
/*  879 */         if (t.getType() == -1) {
/*  880 */           MismatchedTokenException e = new MismatchedTokenException(125, this.input);
/*  881 */           String msg = "missing final '}' in {...} anonymous template";
/*  882 */           this.group.errMgr.groupLexerError(ErrorType.SYNTAX_ERROR, getSourceName(), e, msg);
/*  883 */           break;
/*      */         }
/*  885 */         t = lexer.nextToken();
/*      */       }
/*      */ 
/*  891 */       this.state.type = _type;
/*  892 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mCOMMENT() throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  903 */       int _type = 7;
/*  904 */       int _channel = 0;
/*      */ 
/*  908 */       match("/*");
/*      */       while (true)
/*      */       {
/*  915 */         int alt5 = 2;
/*  916 */         int LA5_0 = this.input.LA(1);
/*      */ 
/*  918 */         if (LA5_0 == 42) {
/*  919 */           int LA5_1 = this.input.LA(2);
/*      */ 
/*  921 */           if (LA5_1 == 47) {
/*  922 */             alt5 = 2;
/*      */           }
/*  924 */           else if (((LA5_1 >= 0) && (LA5_1 <= 46)) || ((LA5_1 >= 48) && (LA5_1 <= 65535))) {
/*  925 */             alt5 = 1;
/*      */           }
/*      */ 
/*      */         }
/*  930 */         else if (((LA5_0 >= 0) && (LA5_0 <= 41)) || ((LA5_0 >= 43) && (LA5_0 <= 65535))) {
/*  931 */           alt5 = 1;
/*      */         }
/*      */ 
/*  935 */         switch (alt5)
/*      */         {
/*      */         case 1:
/*  939 */           matchAny();
/*      */ 
/*  942 */           break;
/*      */         default:
/*  945 */           break label149;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  950 */       label149: match("*/");
/*      */ 
/*  954 */       skip();
/*      */ 
/*  958 */       this.state.type = _type;
/*  959 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mLINE_COMMENT() throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  970 */       int _type = 10;
/*  971 */       int _channel = 0;
/*      */ 
/*  975 */       match("//");
/*      */       while (true)
/*      */       {
/*  982 */         int alt6 = 2;
/*  983 */         int LA6_0 = this.input.LA(1);
/*      */ 
/*  985 */         if (((LA6_0 >= 0) && (LA6_0 <= 9)) || ((LA6_0 >= 11) && (LA6_0 <= 12)) || ((LA6_0 >= 14) && (LA6_0 <= 65535))) {
/*  986 */           alt6 = 1;
/*      */         }
/*      */ 
/*  990 */         switch (alt6)
/*      */         {
/*      */         case 1:
/*  994 */           if (((this.input.LA(1) >= 0) && (this.input.LA(1) <= 9)) || ((this.input.LA(1) >= 11) && (this.input.LA(1) <= 12)) || ((this.input.LA(1) >= 14) && (this.input.LA(1) <= 65535))) {
/*  995 */             this.input.consume();
/*      */           }
/*      */           else {
/*  998 */             MismatchedSetException mse = new MismatchedSetException(null, this.input);
/*  999 */             recover(mse);
/* 1000 */             throw mse;
/*      */           }
/*      */ 
/*      */           break;
/*      */         default:
/* 1008 */           break label217;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1014 */       label217: int alt7 = 2;
/* 1015 */       int LA7_0 = this.input.LA(1);
/*      */ 
/* 1017 */       if (LA7_0 == 13) {
/* 1018 */         alt7 = 1;
/*      */       }
/* 1020 */       switch (alt7)
/*      */       {
/*      */       case 1:
/* 1024 */         match(13);
/*      */       }
/*      */ 
/* 1032 */       match(10);
/*      */ 
/* 1034 */       skip();
/*      */ 
/* 1038 */       this.state.type = _type;
/* 1039 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mWS() throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 1050 */       int _type = 13;
/* 1051 */       int _channel = 0;
/*      */ 
/* 1055 */       if (((this.input.LA(1) >= 9) && (this.input.LA(1) <= 10)) || (this.input.LA(1) == 13) || (this.input.LA(1) == 32)) {
/* 1056 */         this.input.consume();
/*      */       }
/*      */       else {
/* 1059 */         MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 1060 */         recover(mse);
/* 1061 */         throw mse;
/*      */       }
/*      */ 
/* 1065 */       skip();
/*      */ 
/* 1069 */       this.state.type = _type;
/* 1070 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public void mTokens()
/*      */     throws RecognitionException
/*      */   {
/* 1080 */     int alt8 = 26;
/* 1081 */     alt8 = this.dfa8.predict(this.input);
/* 1082 */     switch (alt8)
/*      */     {
/*      */     case 1:
/* 1086 */       mFALSE();
/*      */ 
/* 1090 */       break;
/*      */     case 2:
/* 1094 */       mTRUE();
/*      */ 
/* 1098 */       break;
/*      */     case 3:
/* 1102 */       mT__14();
/*      */ 
/* 1106 */       break;
/*      */     case 4:
/* 1110 */       mT__15();
/*      */ 
/* 1114 */       break;
/*      */     case 5:
/* 1118 */       mT__16();
/*      */ 
/* 1122 */       break;
/*      */     case 6:
/* 1126 */       mT__17();
/*      */ 
/* 1130 */       break;
/*      */     case 7:
/* 1134 */       mT__18();
/*      */ 
/* 1138 */       break;
/*      */     case 8:
/* 1142 */       mT__19();
/*      */ 
/* 1146 */       break;
/*      */     case 9:
/* 1150 */       mT__20();
/*      */ 
/* 1154 */       break;
/*      */     case 10:
/* 1158 */       mT__21();
/*      */ 
/* 1162 */       break;
/*      */     case 11:
/* 1166 */       mT__22();
/*      */ 
/* 1170 */       break;
/*      */     case 12:
/* 1174 */       mT__23();
/*      */ 
/* 1178 */       break;
/*      */     case 13:
/* 1182 */       mT__24();
/*      */ 
/* 1186 */       break;
/*      */     case 14:
/* 1190 */       mT__25();
/*      */ 
/* 1194 */       break;
/*      */     case 15:
/* 1198 */       mT__26();
/*      */ 
/* 1202 */       break;
/*      */     case 16:
/* 1206 */       mT__27();
/*      */ 
/* 1210 */       break;
/*      */     case 17:
/* 1214 */       mT__28();
/*      */ 
/* 1218 */       break;
/*      */     case 18:
/* 1222 */       mT__29();
/*      */ 
/* 1226 */       break;
/*      */     case 19:
/* 1230 */       mID();
/*      */ 
/* 1234 */       break;
/*      */     case 20:
/* 1238 */       mSTRING();
/*      */ 
/* 1242 */       break;
/*      */     case 21:
/* 1246 */       mBIGSTRING_NO_NL();
/*      */ 
/* 1250 */       break;
/*      */     case 22:
/* 1254 */       mBIGSTRING();
/*      */ 
/* 1258 */       break;
/*      */     case 23:
/* 1262 */       mANONYMOUS_TEMPLATE();
/*      */ 
/* 1266 */       break;
/*      */     case 24:
/* 1270 */       mCOMMENT();
/*      */ 
/* 1274 */       break;
/*      */     case 25:
/* 1278 */       mLINE_COMMENT();
/*      */ 
/* 1282 */       break;
/*      */     case 26:
/* 1286 */       mWS();
/*      */     }
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/* 1412 */     int numStates = DFA8_transitionS.length;
/* 1413 */     DFA8_transition = new short[numStates][];
/* 1414 */     for (int i = 0; i < numStates; i++)
/* 1415 */       DFA8_transition[i] = DFA.unpackEncodedString(DFA8_transitionS[i]);
/*      */   }
/*      */ 
/*      */   class DFA8 extends DFA
/*      */   {
/*      */     public DFA8(BaseRecognizer recognizer)
/*      */     {
/* 1422 */       this.recognizer = recognizer;
/* 1423 */       this.decisionNumber = 8;
/* 1424 */       this.eot = GroupLexer.DFA8_eot;
/* 1425 */       this.eof = GroupLexer.DFA8_eof;
/* 1426 */       this.min = GroupLexer.DFA8_min;
/* 1427 */       this.max = GroupLexer.DFA8_max;
/* 1428 */       this.accept = GroupLexer.DFA8_accept;
/* 1429 */       this.special = GroupLexer.DFA8_special;
/* 1430 */       this.transition = GroupLexer.DFA8_transition;
/*      */     }
/*      */     public String getDescription() {
/* 1433 */       return "1:1: Tokens : ( FALSE | TRUE | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | ID | STRING | BIGSTRING_NO_NL | BIGSTRING | ANONYMOUS_TEMPLATE | COMMENT | LINE_COMMENT | WS );";
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.compiler.GroupLexer
 * JD-Core Version:    0.6.2
 */