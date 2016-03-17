/*      */ package org.antlr.gunit.swingui.parsers;
/*      */ 
/*      */ import org.antlr.runtime.BaseRecognizer;
/*      */ import org.antlr.runtime.CharStream;
/*      */ import org.antlr.runtime.CommonToken;
/*      */ import org.antlr.runtime.DFA;
/*      */ import org.antlr.runtime.EarlyExitException;
/*      */ import org.antlr.runtime.IntStream;
/*      */ import org.antlr.runtime.Lexer;
/*      */ import org.antlr.runtime.MismatchedSetException;
/*      */ import org.antlr.runtime.NoViableAltException;
/*      */ import org.antlr.runtime.RecognitionException;
/*      */ import org.antlr.runtime.RecognizerSharedState;
/*      */ 
/*      */ public class ANTLRv3Lexer extends Lexer
/*      */ {
/*      */   public static final int EOF = -1;
/*      */   public static final int T__65 = 65;
/*      */   public static final int T__66 = 66;
/*      */   public static final int T__67 = 67;
/*      */   public static final int T__68 = 68;
/*      */   public static final int T__69 = 69;
/*      */   public static final int T__70 = 70;
/*      */   public static final int T__71 = 71;
/*      */   public static final int T__72 = 72;
/*      */   public static final int T__73 = 73;
/*      */   public static final int T__74 = 74;
/*      */   public static final int T__75 = 75;
/*      */   public static final int T__76 = 76;
/*      */   public static final int T__77 = 77;
/*      */   public static final int T__78 = 78;
/*      */   public static final int T__79 = 79;
/*      */   public static final int T__80 = 80;
/*      */   public static final int T__81 = 81;
/*      */   public static final int T__82 = 82;
/*      */   public static final int T__83 = 83;
/*      */   public static final int T__84 = 84;
/*      */   public static final int T__85 = 85;
/*      */   public static final int T__86 = 86;
/*      */   public static final int T__87 = 87;
/*      */   public static final int T__88 = 88;
/*      */   public static final int T__89 = 89;
/*      */   public static final int T__90 = 90;
/*      */   public static final int T__91 = 91;
/*      */   public static final int T__92 = 92;
/*      */   public static final int T__93 = 93;
/*      */   public static final int DOC_COMMENT = 4;
/*      */   public static final int PARSER = 5;
/*      */   public static final int LEXER = 6;
/*      */   public static final int RULE = 7;
/*      */   public static final int BLOCK = 8;
/*      */   public static final int OPTIONAL = 9;
/*      */   public static final int CLOSURE = 10;
/*      */   public static final int POSITIVE_CLOSURE = 11;
/*      */   public static final int SYNPRED = 12;
/*      */   public static final int RANGE = 13;
/*      */   public static final int CHAR_RANGE = 14;
/*      */   public static final int EPSILON = 15;
/*      */   public static final int ALT = 16;
/*      */   public static final int EOR = 17;
/*      */   public static final int EOB = 18;
/*      */   public static final int EOA = 19;
/*      */   public static final int ID = 20;
/*      */   public static final int ARG = 21;
/*      */   public static final int ARGLIST = 22;
/*      */   public static final int RET = 23;
/*      */   public static final int LEXER_GRAMMAR = 24;
/*      */   public static final int PARSER_GRAMMAR = 25;
/*      */   public static final int TREE_GRAMMAR = 26;
/*      */   public static final int COMBINED_GRAMMAR = 27;
/*      */   public static final int INITACTION = 28;
/*      */   public static final int LABEL = 29;
/*      */   public static final int TEMPLATE = 30;
/*      */   public static final int SCOPE = 31;
/*      */   public static final int SEMPRED = 32;
/*      */   public static final int GATED_SEMPRED = 33;
/*      */   public static final int SYN_SEMPRED = 34;
/*      */   public static final int BACKTRACK_SEMPRED = 35;
/*      */   public static final int FRAGMENT = 36;
/*      */   public static final int TREE_BEGIN = 37;
/*      */   public static final int ROOT = 38;
/*      */   public static final int BANG = 39;
/*      */   public static final int REWRITE = 40;
/*      */   public static final int TOKENS = 41;
/*      */   public static final int TOKEN_REF = 42;
/*      */   public static final int STRING_LITERAL = 43;
/*      */   public static final int CHAR_LITERAL = 44;
/*      */   public static final int ACTION = 45;
/*      */   public static final int OPTIONS = 46;
/*      */   public static final int INT = 47;
/*      */   public static final int ARG_ACTION = 48;
/*      */   public static final int RULE_REF = 49;
/*      */   public static final int DOUBLE_QUOTE_STRING_LITERAL = 50;
/*      */   public static final int DOUBLE_ANGLE_STRING_LITERAL = 51;
/*      */   public static final int SRC = 52;
/*      */   public static final int SL_COMMENT = 53;
/*      */   public static final int ML_COMMENT = 54;
/*      */   public static final int LITERAL_CHAR = 55;
/*      */   public static final int ESC = 56;
/*      */   public static final int XDIGIT = 57;
/*      */   public static final int NESTED_ARG_ACTION = 58;
/*      */   public static final int ACTION_STRING_LITERAL = 59;
/*      */   public static final int ACTION_CHAR_LITERAL = 60;
/*      */   public static final int NESTED_ACTION = 61;
/*      */   public static final int ACTION_ESC = 62;
/*      */   public static final int WS_LOOP = 63;
/*      */   public static final int WS = 64;
/* 2702 */   protected DFA2 dfa2 = new DFA2(this);
/* 2703 */   protected DFA9 dfa9 = new DFA9(this);
/* 2704 */   protected DFA13 dfa13 = new DFA13(this);
/* 2705 */   protected DFA22 dfa22 = new DFA22(this);
/*      */   static final String DFA2_eotS = "\022ğ¿¿\001\002\004ğ¿¿\001\002\004ğ¿¿";
/*      */   static final String DFA2_eofS = "\034ğ¿¿";
/*      */   static final String DFA2_minS = "";
/*      */   static final String DFA2_maxS = "\002ğ¿¿\001ğ¿¿\026ğ¿¿\001ğ¿¿\001ğ¿¿\001ğ¿¿";
/*      */   static final String DFA2_acceptS = "\002ğ¿¿\001\002\026ğ¿¿\001\001\001ğ¿¿\001\001";
/*      */   static final String DFA2_specialS = "";
/* 2720 */   static final String[] DFA2_transitionS = { " \002\001\001ï¿Ÿ\002", "$\002\001\003ï¿›\002", "", "A\002\001\004ï¾¾\002", "N\002\001\005ï¾±\002", "T\002\001\006ï¾«\002", "L\002\001\007ï¾³\002", "R\002\001\bï¾­\002", " \002\001\tï¿Ÿ\002", "s\002\001\nï¾Œ\002", "r\002\001\013ï¾\002", "c\002\001\fï¾œ\002", " \002\001\rï¿Ÿ\002", "\"\002\001\016ï¿\002", "\n\023\001\022\002\023\001\020\024\023\001\0219\023\001\017ï¾£\023", "\n\030\001\027\002\030\001\026\024\030\001\025\004\030\001\024ï¿˜\030", "\n\031\001\022ï¿µ\031", " \002\001\032ï¿Ÿ\002", "", "\n\023\001\022\002\023\001\020\024\023\001\0219\023\001\017ï¾£\023", "\n\023\001\022\002\023\001\020\024\023\001\0219\023\001\017ï¾£\023", "\n\023\001\022\002\023\001\020\024\023\001\0219\023\001\017ï¾£\023", "\n\031\001\022ï¿µ\031", "", "\n\023\001\022\002\023\001\020\024\023\001\0219\023\001\017ï¾£\023", "", "0\002\n\033ï¿†\002", "" };
/*      */ 
/* 2751 */   static final short[] DFA2_eot = DFA.unpackEncodedString("\022ğ¿¿\001\002\004ğ¿¿\001\002\004ğ¿¿");
/* 2752 */   static final short[] DFA2_eof = DFA.unpackEncodedString("\034ğ¿¿");
/* 2753 */   static final char[] DFA2_min = DFA.unpackEncodedStringToUnsignedChars("");
/* 2754 */   static final char[] DFA2_max = DFA.unpackEncodedStringToUnsignedChars("\002ğ¿¿\001ğ¿¿\026ğ¿¿\001ğ¿¿\001ğ¿¿\001ğ¿¿");
/* 2755 */   static final short[] DFA2_accept = DFA.unpackEncodedString("\002ğ¿¿\001\002\026ğ¿¿\001\001\001ğ¿¿\001\001");
/* 2756 */   static final short[] DFA2_special = DFA.unpackEncodedString("");
/*      */   static final short[][] DFA2_transition;
/*      */   static final String DFA9_eotS = "\nğ¿¿\001\013\002ğ¿¿";
/*      */   static final String DFA9_eofS = "\rğ¿¿";
/*      */   static final String DFA9_minS = "";
/*      */   static final String DFA9_maxS = "\001ğ¿¿\tğ¿¿\001f\002ğ¿¿";
/*      */   static final String DFA9_acceptS = "\001ğ¿¿\001\001\001\002\001\003\001\004\001\005\001\006\001\007\001\b\001\t\001ğ¿¿\001\013\001\n";
/*      */   static final String DFA9_specialS = "";
/*      */   static final String[] DFA9_transitionS;
/*      */   static final short[] DFA9_eot;
/*      */   static final short[] DFA9_eof;
/*      */   static final char[] DFA9_min;
/*      */   static final char[] DFA9_max;
/*      */   static final short[] DFA9_accept;
/*      */   static final short[] DFA9_special;
/*      */   static final short[][] DFA9_transition;
/*      */   static final String DFA13_eotS = "\034ğ¿¿";
/*      */   static final String DFA13_eofS = "\034ğ¿¿";
/*      */   static final String DFA13_minS = "";
/*      */   static final String DFA13_maxS = "\001ğ¿¿\002ğ¿¿\003ğ¿¿\026ğ¿¿";
/*      */   static final String DFA13_acceptS = "\001ğ¿¿\001\007\001\001\003ğ¿¿\001\006\001\002\001\003\005ğ¿¿\007\004\006\005\001ğ¿¿";
/*      */   static final String DFA13_specialS = "";
/*      */   static final String[] DFA13_transitionS;
/*      */   static final short[] DFA13_eot;
/*      */   static final short[] DFA13_eof;
/*      */   static final char[] DFA13_min;
/*      */   static final char[] DFA13_max;
/*      */   static final short[] DFA13_accept;
/*      */   static final short[] DFA13_special;
/*      */   static final short[][] DFA13_transition;
/*      */   static final String DFA22_eotS = "\001ğ¿¿\002$\001*\001ğ¿¿\001,\001ğ¿¿\004$\002ğ¿¿\0016\001ğ¿¿\0018\001ğ¿¿\001$\004ğ¿¿\001$\001<\013ğ¿¿\001$\002ğ¿¿\003$\004ğ¿¿\b$\004ğ¿¿\002$\006ğ¿¿\017$\rğ¿¿\t$\001w\005$\002ğ¿¿\001$\001\002$\001Â‚\004$\001ğ¿¿\004$\001Â‹\001ğ¿¿\001$\001ğ¿¿\002$\001ğ¿¿\001Â\002$\001Â“\001Â”\003$\002ğ¿¿\002$\001Â›\001ğ¿¿\001$\001Â\003ğ¿¿\001Â\001ÂŸ\001ğ¿¿\001$\001Â¡\001ğ¿¿\001$\005ğ¿¿\001Â£\001ğ¿¿";
/*      */   static final String DFA22_eofS = "Â¤ğ¿¿";
/*      */   static final String DFA22_minS = "";
/*      */   static final String DFA22_maxS = "\001~\001c\001r\001(\001ğ¿¿\001.\001ğ¿¿\001e\001u\002r\002ğ¿¿\001>\001ğ¿¿\001:\001ğ¿¿\001e\004ğ¿¿\001a\001=\003ğ¿¿\001/\001ğ¿¿\006ğ¿¿\001p\002ğ¿¿\001o\001a\001n\004ğ¿¿\001x\001r\001o\001b\001e\001r\001k\001a\004ğ¿¿\002t\004ğ¿¿\002ğ¿¿\001t\001p\001g\001a\001e\001s\001t\001v\001l\001e\001o\001e\001m\001u\001c\013ğ¿¿\002ğ¿¿\001i\001e\001m\001l\001r\002e\001a\001i\001z\001w\001n\001m\001r\001h\001ğ¿¿\001ğ¿¿\001o\001z\001e\001l\001z\001r\001c\001t\001c\001ğ¿¿\002s\001a\001n\001z\001ğ¿¿\001n\001ğ¿¿\001n\001y\001ğ¿¿\001z\001t\001e\002z\001{\001r\001s\001ğ¿¿\001ğ¿¿\001s\001t\001z\001ğ¿¿\001e\001z\003ğ¿¿\002z\001ğ¿¿\001{\001z\001ğ¿¿\001d\005ğ¿¿\001z\001ğ¿¿";
/*      */   static final String DFA22_acceptS = "\004ğ¿¿\001\005\001ğ¿¿\001\007\004ğ¿¿\001\f\001\r\001ğ¿¿\001\017\001ğ¿¿\001\021\001ğ¿¿\001\030\001\031\001\032\001\033\002ğ¿¿\001 \001!\001$\002ğ¿¿\001)\001*\001+\001,\001-\001.\001ğ¿¿\001/\0012\003ğ¿¿\001\003\001\004\001\006\001#\bğ¿¿\001\037\001\016\001\020\001\026\002ğ¿¿\001\036\001\"\001%\001&\034ğ¿¿\001'\001(\020ğ¿¿\001'\tğ¿¿\001\n\007ğ¿¿\001\001\002ğ¿¿\001\b\bğ¿¿\001\034\004ğ¿¿\001\t\002ğ¿¿\001\023\001\027\0011\005ğ¿¿\001\035\001ğ¿¿\001\024\001\013\001\025\0010\001\002\001ğ¿¿\001\022";
/*      */   static final String DFA22_specialS = "";
/*      */   static final String[] DFA22_transitionS;
/*      */   static final short[] DFA22_eot;
/*      */   static final short[] DFA22_eof;
/*      */   static final char[] DFA22_min;
/*      */   static final char[] DFA22_max;
/*      */   static final short[] DFA22_accept;
/*      */   static final short[] DFA22_special;
/*      */   static final short[][] DFA22_transition;
/*      */ 
/*      */   public ANTLRv3Lexer()
/*      */   {
/*      */   }
/*      */ 
/*      */   public ANTLRv3Lexer(CharStream input)
/*      */   {
/*  109 */     this(input, new RecognizerSharedState());
/*      */   }
/*      */   public ANTLRv3Lexer(CharStream input, RecognizerSharedState state) {
/*  112 */     super(input, state);
/*      */   }
/*      */   public String getGrammarFileName() {
/*  115 */     return "org/antlr/gunit/swingui/parsers/ANTLRv3.g";
/*      */   }
/*      */ 
/*      */   public final void mSCOPE() throws RecognitionException {
/*      */     try {
/*  120 */       int _type = 31;
/*  121 */       int _channel = 0;
/*      */ 
/*  125 */       match("scope");
/*      */ 
/*  130 */       this.state.type = _type;
/*  131 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mFRAGMENT() throws RecognitionException
/*      */   {
/*      */     try {
/*  141 */       int _type = 36;
/*  142 */       int _channel = 0;
/*      */ 
/*  146 */       match("fragment");
/*      */ 
/*  151 */       this.state.type = _type;
/*  152 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mTREE_BEGIN() throws RecognitionException
/*      */   {
/*      */     try {
/*  162 */       int _type = 37;
/*  163 */       int _channel = 0;
/*      */ 
/*  167 */       match("^(");
/*      */ 
/*  172 */       this.state.type = _type;
/*  173 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mROOT() throws RecognitionException
/*      */   {
/*      */     try {
/*  183 */       int _type = 38;
/*  184 */       int _channel = 0;
/*      */ 
/*  188 */       match(94);
/*      */ 
/*  192 */       this.state.type = _type;
/*  193 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mBANG() throws RecognitionException
/*      */   {
/*      */     try {
/*  203 */       int _type = 39;
/*  204 */       int _channel = 0;
/*      */ 
/*  208 */       match(33);
/*      */ 
/*  212 */       this.state.type = _type;
/*  213 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mRANGE() throws RecognitionException
/*      */   {
/*      */     try {
/*  223 */       int _type = 13;
/*  224 */       int _channel = 0;
/*      */ 
/*  228 */       match("..");
/*      */ 
/*  233 */       this.state.type = _type;
/*  234 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mREWRITE() throws RecognitionException
/*      */   {
/*      */     try {
/*  244 */       int _type = 40;
/*  245 */       int _channel = 0;
/*      */ 
/*  249 */       match("->");
/*      */ 
/*  254 */       this.state.type = _type;
/*  255 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__65() throws RecognitionException
/*      */   {
/*      */     try {
/*  265 */       int _type = 65;
/*  266 */       int _channel = 0;
/*      */ 
/*  270 */       match("lexer");
/*      */ 
/*  275 */       this.state.type = _type;
/*  276 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__66() throws RecognitionException
/*      */   {
/*      */     try {
/*  286 */       int _type = 66;
/*  287 */       int _channel = 0;
/*      */ 
/*  291 */       match("parser");
/*      */ 
/*  296 */       this.state.type = _type;
/*  297 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__67() throws RecognitionException
/*      */   {
/*      */     try {
/*  307 */       int _type = 67;
/*  308 */       int _channel = 0;
/*      */ 
/*  312 */       match("tree");
/*      */ 
/*  317 */       this.state.type = _type;
/*  318 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__68() throws RecognitionException
/*      */   {
/*      */     try {
/*  328 */       int _type = 68;
/*  329 */       int _channel = 0;
/*      */ 
/*  333 */       match("grammar");
/*      */ 
/*  338 */       this.state.type = _type;
/*  339 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__69() throws RecognitionException
/*      */   {
/*      */     try {
/*  349 */       int _type = 69;
/*  350 */       int _channel = 0;
/*      */ 
/*  354 */       match(59);
/*      */ 
/*  358 */       this.state.type = _type;
/*  359 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__70() throws RecognitionException
/*      */   {
/*      */     try {
/*  369 */       int _type = 70;
/*  370 */       int _channel = 0;
/*      */ 
/*  374 */       match(125);
/*      */ 
/*  378 */       this.state.type = _type;
/*  379 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__71() throws RecognitionException
/*      */   {
/*      */     try {
/*  389 */       int _type = 71;
/*  390 */       int _channel = 0;
/*      */ 
/*  394 */       match(61);
/*      */ 
/*  398 */       this.state.type = _type;
/*  399 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__72() throws RecognitionException
/*      */   {
/*      */     try {
/*  409 */       int _type = 72;
/*  410 */       int _channel = 0;
/*      */ 
/*  414 */       match(64);
/*      */ 
/*  418 */       this.state.type = _type;
/*  419 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__73() throws RecognitionException
/*      */   {
/*      */     try {
/*  429 */       int _type = 73;
/*  430 */       int _channel = 0;
/*      */ 
/*  434 */       match("::");
/*      */ 
/*  439 */       this.state.type = _type;
/*  440 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__74() throws RecognitionException
/*      */   {
/*      */     try {
/*  450 */       int _type = 74;
/*  451 */       int _channel = 0;
/*      */ 
/*  455 */       match(42);
/*      */ 
/*  459 */       this.state.type = _type;
/*  460 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__75() throws RecognitionException
/*      */   {
/*      */     try {
/*  470 */       int _type = 75;
/*  471 */       int _channel = 0;
/*      */ 
/*  475 */       match("protected");
/*      */ 
/*  480 */       this.state.type = _type;
/*  481 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__76() throws RecognitionException
/*      */   {
/*      */     try {
/*  491 */       int _type = 76;
/*  492 */       int _channel = 0;
/*      */ 
/*  496 */       match("public");
/*      */ 
/*  501 */       this.state.type = _type;
/*  502 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__77() throws RecognitionException
/*      */   {
/*      */     try {
/*  512 */       int _type = 77;
/*  513 */       int _channel = 0;
/*      */ 
/*  517 */       match("private");
/*      */ 
/*  522 */       this.state.type = _type;
/*  523 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__78() throws RecognitionException
/*      */   {
/*      */     try {
/*  533 */       int _type = 78;
/*  534 */       int _channel = 0;
/*      */ 
/*  538 */       match("returns");
/*      */ 
/*  543 */       this.state.type = _type;
/*  544 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__79() throws RecognitionException
/*      */   {
/*      */     try {
/*  554 */       int _type = 79;
/*  555 */       int _channel = 0;
/*      */ 
/*  559 */       match(58);
/*      */ 
/*  563 */       this.state.type = _type;
/*  564 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__80() throws RecognitionException
/*      */   {
/*      */     try {
/*  574 */       int _type = 80;
/*  575 */       int _channel = 0;
/*      */ 
/*  579 */       match("throws");
/*      */ 
/*  584 */       this.state.type = _type;
/*  585 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__81() throws RecognitionException
/*      */   {
/*      */     try {
/*  595 */       int _type = 81;
/*  596 */       int _channel = 0;
/*      */ 
/*  600 */       match(44);
/*      */ 
/*  604 */       this.state.type = _type;
/*  605 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__82() throws RecognitionException
/*      */   {
/*      */     try {
/*  615 */       int _type = 82;
/*  616 */       int _channel = 0;
/*      */ 
/*  620 */       match(40);
/*      */ 
/*  624 */       this.state.type = _type;
/*  625 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__83() throws RecognitionException
/*      */   {
/*      */     try {
/*  635 */       int _type = 83;
/*  636 */       int _channel = 0;
/*      */ 
/*  640 */       match(124);
/*      */ 
/*  644 */       this.state.type = _type;
/*  645 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__84() throws RecognitionException
/*      */   {
/*      */     try {
/*  655 */       int _type = 84;
/*  656 */       int _channel = 0;
/*      */ 
/*  660 */       match(41);
/*      */ 
/*  664 */       this.state.type = _type;
/*  665 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__85() throws RecognitionException
/*      */   {
/*      */     try {
/*  675 */       int _type = 85;
/*  676 */       int _channel = 0;
/*      */ 
/*  680 */       match("catch");
/*      */ 
/*  685 */       this.state.type = _type;
/*  686 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__86() throws RecognitionException
/*      */   {
/*      */     try {
/*  696 */       int _type = 86;
/*  697 */       int _channel = 0;
/*      */ 
/*  701 */       match("finally");
/*      */ 
/*  706 */       this.state.type = _type;
/*  707 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__87() throws RecognitionException
/*      */   {
/*      */     try {
/*  717 */       int _type = 87;
/*  718 */       int _channel = 0;
/*      */ 
/*  722 */       match("+=");
/*      */ 
/*  727 */       this.state.type = _type;
/*  728 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__88() throws RecognitionException
/*      */   {
/*      */     try {
/*  738 */       int _type = 88;
/*  739 */       int _channel = 0;
/*      */ 
/*  743 */       match("=>");
/*      */ 
/*  748 */       this.state.type = _type;
/*  749 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__89() throws RecognitionException
/*      */   {
/*      */     try {
/*  759 */       int _type = 89;
/*  760 */       int _channel = 0;
/*      */ 
/*  764 */       match(126);
/*      */ 
/*  768 */       this.state.type = _type;
/*  769 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__90() throws RecognitionException
/*      */   {
/*      */     try {
/*  779 */       int _type = 90;
/*  780 */       int _channel = 0;
/*      */ 
/*  784 */       match(63);
/*      */ 
/*  788 */       this.state.type = _type;
/*  789 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__91() throws RecognitionException
/*      */   {
/*      */     try {
/*  799 */       int _type = 91;
/*  800 */       int _channel = 0;
/*      */ 
/*  804 */       match(43);
/*      */ 
/*  808 */       this.state.type = _type;
/*  809 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__92() throws RecognitionException
/*      */   {
/*      */     try {
/*  819 */       int _type = 92;
/*  820 */       int _channel = 0;
/*      */ 
/*  824 */       match(46);
/*      */ 
/*  828 */       this.state.type = _type;
/*  829 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__93() throws RecognitionException
/*      */   {
/*      */     try {
/*  839 */       int _type = 93;
/*  840 */       int _channel = 0;
/*      */ 
/*  844 */       match(36);
/*      */ 
/*  848 */       this.state.type = _type;
/*  849 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mSL_COMMENT() throws RecognitionException
/*      */   {
/*      */     try {
/*  859 */       int _type = 53;
/*  860 */       int _channel = 0;
/*      */ 
/*  864 */       match("//");
/*      */ 
/*  867 */       int alt2 = 2;
/*  868 */       alt2 = this.dfa2.predict(this.input);
/*  869 */       switch (alt2)
/*      */       {
/*      */       case 1:
/*  873 */         match(" $ANTLR ");
/*      */ 
/*  875 */         mSRC();
/*      */ 
/*  878 */         break;
/*      */       case 2:
/*      */         while (true)
/*      */         {
/*  885 */           int alt1 = 2;
/*  886 */           int LA1_0 = this.input.LA(1);
/*      */ 
/*  888 */           if (((LA1_0 >= 0) && (LA1_0 <= 9)) || ((LA1_0 >= 11) && (LA1_0 <= 12)) || ((LA1_0 >= 14) && (LA1_0 <= 65535))) {
/*  889 */             alt1 = 1;
/*      */           }
/*      */ 
/*  893 */           switch (alt1)
/*      */           {
/*      */           case 1:
/*  897 */             if (((this.input.LA(1) >= 0) && (this.input.LA(1) <= 9)) || ((this.input.LA(1) >= 11) && (this.input.LA(1) <= 12)) || ((this.input.LA(1) >= 14) && (this.input.LA(1) <= 65535))) {
/*  898 */               this.input.consume();
/*      */             }
/*      */             else
/*      */             {
/*  902 */               MismatchedSetException mse = new MismatchedSetException(null, this.input);
/*  903 */               recover(mse);
/*  904 */               throw mse;
/*      */             }
/*      */ 
/*      */             break;
/*      */           default:
/*  911 */             break label273;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  922 */       label273: int alt3 = 2;
/*  923 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 13:
/*  926 */         alt3 = 1;
/*      */       }
/*      */ 
/*  931 */       switch (alt3)
/*      */       {
/*      */       case 1:
/*  935 */         match(13);
/*      */       }
/*      */ 
/*  942 */       match(10);
/*  943 */       _channel = 99;
/*      */ 
/*  947 */       this.state.type = _type;
/*  948 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mML_COMMENT() throws RecognitionException
/*      */   {
/*      */     try {
/*  958 */       int _type = 54;
/*  959 */       int _channel = 0;
/*      */ 
/*  963 */       match("/*");
/*      */ 
/*  965 */       if (this.input.LA(1) == 42) _type = 4; else _channel = 99;
/*      */ 
/*      */       while (true)
/*      */       {
/*  969 */         int alt4 = 2;
/*  970 */         int LA4_0 = this.input.LA(1);
/*      */ 
/*  972 */         if (LA4_0 == 42) {
/*  973 */           int LA4_1 = this.input.LA(2);
/*      */ 
/*  975 */           if (LA4_1 == 47) {
/*  976 */             alt4 = 2;
/*      */           }
/*  978 */           else if (((LA4_1 >= 0) && (LA4_1 <= 46)) || ((LA4_1 >= 48) && (LA4_1 <= 65535))) {
/*  979 */             alt4 = 1;
/*      */           }
/*      */ 
/*      */         }
/*  984 */         else if (((LA4_0 >= 0) && (LA4_0 <= 41)) || ((LA4_0 >= 43) && (LA4_0 <= 65535))) {
/*  985 */           alt4 = 1;
/*      */         }
/*      */ 
/*  989 */         switch (alt4)
/*      */         {
/*      */         case 1:
/*  993 */           matchAny();
/*      */ 
/*  996 */           break;
/*      */         default:
/*  999 */           break label169;
/*      */         }
/*      */       }
/*      */ 
/* 1003 */       label169: match("*/");
/*      */ 
/* 1008 */       this.state.type = _type;
/* 1009 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mCHAR_LITERAL() throws RecognitionException
/*      */   {
/*      */     try {
/* 1019 */       int _type = 44;
/* 1020 */       int _channel = 0;
/*      */ 
/* 1024 */       match(39);
/* 1025 */       mLITERAL_CHAR();
/* 1026 */       match(39);
/*      */ 
/* 1030 */       this.state.type = _type;
/* 1031 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mSTRING_LITERAL() throws RecognitionException
/*      */   {
/*      */     try {
/* 1041 */       int _type = 43;
/* 1042 */       int _channel = 0;
/*      */ 
/* 1046 */       match(39);
/* 1047 */       mLITERAL_CHAR();
/*      */       while (true)
/*      */       {
/* 1051 */         int alt5 = 2;
/* 1052 */         int LA5_0 = this.input.LA(1);
/*      */ 
/* 1054 */         if (((LA5_0 >= 0) && (LA5_0 <= 38)) || ((LA5_0 >= 40) && (LA5_0 <= 65535))) {
/* 1055 */           alt5 = 1;
/*      */         }
/*      */ 
/* 1059 */         switch (alt5)
/*      */         {
/*      */         case 1:
/* 1063 */           mLITERAL_CHAR();
/*      */ 
/* 1066 */           break;
/*      */         default:
/* 1069 */           break label89;
/*      */         }
/*      */       }
/*      */ 
/* 1073 */       label89: match(39);
/*      */ 
/* 1077 */       this.state.type = _type;
/* 1078 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mLITERAL_CHAR() throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 1089 */       int alt6 = 2;
/* 1090 */       int LA6_0 = this.input.LA(1);
/*      */ 
/* 1092 */       if (LA6_0 == 92) {
/* 1093 */         alt6 = 1;
/*      */       }
/* 1095 */       else if (((LA6_0 >= 0) && (LA6_0 <= 38)) || ((LA6_0 >= 40) && (LA6_0 <= 91)) || ((LA6_0 >= 93) && (LA6_0 <= 65535))) {
/* 1096 */         alt6 = 2;
/*      */       }
/*      */       else {
/* 1099 */         NoViableAltException nvae = new NoViableAltException("", 6, 0, this.input);
/*      */ 
/* 1102 */         throw nvae;
/*      */       }
/* 1104 */       switch (alt6)
/*      */       {
/*      */       case 1:
/* 1108 */         mESC();
/*      */ 
/* 1111 */         break;
/*      */       case 2:
/* 1115 */         if (((this.input.LA(1) >= 0) && (this.input.LA(1) <= 38)) || ((this.input.LA(1) >= 40) && (this.input.LA(1) <= 91)) || ((this.input.LA(1) >= 93) && (this.input.LA(1) <= 65535))) {
/* 1116 */           this.input.consume();
/*      */         }
/*      */         else
/*      */         {
/* 1120 */           MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 1121 */           recover(mse);
/* 1122 */           throw mse;
/*      */         }
/*      */         break;
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mDOUBLE_QUOTE_STRING_LITERAL()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 1138 */       int _type = 50;
/* 1139 */       int _channel = 0;
/*      */ 
/* 1143 */       match(34);
/*      */       while (true)
/*      */       {
/* 1147 */         int alt7 = 3;
/* 1148 */         int LA7_0 = this.input.LA(1);
/*      */ 
/* 1150 */         if (LA7_0 == 92) {
/* 1151 */           alt7 = 1;
/*      */         }
/* 1153 */         else if (((LA7_0 >= 0) && (LA7_0 <= 33)) || ((LA7_0 >= 35) && (LA7_0 <= 91)) || ((LA7_0 >= 93) && (LA7_0 <= 65535))) {
/* 1154 */           alt7 = 2;
/*      */         }
/*      */ 
/* 1158 */         switch (alt7)
/*      */         {
/*      */         case 1:
/* 1162 */           mESC();
/*      */ 
/* 1165 */           break;
/*      */         case 2:
/* 1169 */           if (((this.input.LA(1) >= 0) && (this.input.LA(1) <= 33)) || ((this.input.LA(1) >= 35) && (this.input.LA(1) <= 91)) || ((this.input.LA(1) >= 93) && (this.input.LA(1) <= 65535))) {
/* 1170 */             this.input.consume();
/*      */           }
/*      */           else
/*      */           {
/* 1174 */             MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 1175 */             recover(mse);
/* 1176 */             throw mse;
/*      */           }
/*      */ 
/*      */           break;
/*      */         default:
/* 1183 */           break label244;
/*      */         }
/*      */       }
/*      */ 
/* 1187 */       label244: match(34);
/*      */ 
/* 1191 */       this.state.type = _type;
/* 1192 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mDOUBLE_ANGLE_STRING_LITERAL() throws RecognitionException
/*      */   {
/*      */     try {
/* 1202 */       int _type = 51;
/* 1203 */       int _channel = 0;
/*      */ 
/* 1207 */       match("<<");
/*      */       while (true)
/*      */       {
/* 1212 */         int alt8 = 2;
/* 1213 */         int LA8_0 = this.input.LA(1);
/*      */ 
/* 1215 */         if (LA8_0 == 62) {
/* 1216 */           int LA8_1 = this.input.LA(2);
/*      */ 
/* 1218 */           if (LA8_1 == 62) {
/* 1219 */             alt8 = 2;
/*      */           }
/* 1221 */           else if (((LA8_1 >= 0) && (LA8_1 <= 61)) || ((LA8_1 >= 63) && (LA8_1 <= 65535))) {
/* 1222 */             alt8 = 1;
/*      */           }
/*      */ 
/*      */         }
/* 1227 */         else if (((LA8_0 >= 0) && (LA8_0 <= 61)) || ((LA8_0 >= 63) && (LA8_0 <= 65535))) {
/* 1228 */           alt8 = 1;
/*      */         }
/*      */ 
/* 1232 */         switch (alt8)
/*      */         {
/*      */         case 1:
/* 1236 */           matchAny();
/*      */ 
/* 1239 */           break;
/*      */         default:
/* 1242 */           break label149;
/*      */         }
/*      */       }
/*      */ 
/* 1246 */       label149: match(">>");
/*      */ 
/* 1251 */       this.state.type = _type;
/* 1252 */       this.state.channel = _channel;
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
/* 1265 */       match(92);
/*      */ 
/* 1267 */       int alt9 = 11;
/* 1268 */       alt9 = this.dfa9.predict(this.input);
/* 1269 */       switch (alt9)
/*      */       {
/*      */       case 1:
/* 1273 */         match(110);
/*      */ 
/* 1276 */         break;
/*      */       case 2:
/* 1280 */         match(114);
/*      */ 
/* 1283 */         break;
/*      */       case 3:
/* 1287 */         match(116);
/*      */ 
/* 1290 */         break;
/*      */       case 4:
/* 1294 */         match(98);
/*      */ 
/* 1297 */         break;
/*      */       case 5:
/* 1301 */         match(102);
/*      */ 
/* 1304 */         break;
/*      */       case 6:
/* 1308 */         match(34);
/*      */ 
/* 1311 */         break;
/*      */       case 7:
/* 1315 */         match(39);
/*      */ 
/* 1318 */         break;
/*      */       case 8:
/* 1322 */         match(92);
/*      */ 
/* 1325 */         break;
/*      */       case 9:
/* 1329 */         match(62);
/*      */ 
/* 1332 */         break;
/*      */       case 10:
/* 1336 */         match(117);
/* 1337 */         mXDIGIT();
/* 1338 */         mXDIGIT();
/* 1339 */         mXDIGIT();
/* 1340 */         mXDIGIT();
/*      */ 
/* 1343 */         break;
/*      */       case 11:
/* 1347 */         matchAny();
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
/* 1369 */       if (((this.input.LA(1) >= 48) && (this.input.LA(1) <= 57)) || ((this.input.LA(1) >= 65) && (this.input.LA(1) <= 70)) || ((this.input.LA(1) >= 97) && (this.input.LA(1) <= 102))) {
/* 1370 */         this.input.consume();
/*      */       }
/*      */       else
/*      */       {
/* 1374 */         MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 1375 */         recover(mse);
/* 1376 */         throw mse;
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mINT()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 1390 */       int _type = 47;
/* 1391 */       int _channel = 0;
/*      */ 
/* 1396 */       int cnt10 = 0;
/*      */       while (true)
/*      */       {
/* 1399 */         int alt10 = 2;
/* 1400 */         switch (this.input.LA(1))
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
/* 1412 */           alt10 = 1;
/*      */         }
/*      */ 
/* 1418 */         switch (alt10)
/*      */         {
/*      */         case 1:
/* 1422 */           matchRange(48, 57);
/*      */ 
/* 1425 */           break;
/*      */         default:
/* 1428 */           if (cnt10 >= 1) break label143;
/* 1429 */           EarlyExitException eee = new EarlyExitException(10, this.input);
/*      */ 
/* 1431 */           throw eee;
/*      */         }
/* 1433 */         cnt10++;
/*      */       }
/*      */ 
/* 1439 */       label143: this.state.type = _type;
/* 1440 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mARG_ACTION() throws RecognitionException
/*      */   {
/*      */     try {
/* 1450 */       int _type = 48;
/* 1451 */       int _channel = 0;
/*      */ 
/* 1455 */       mNESTED_ARG_ACTION();
/*      */ 
/* 1459 */       this.state.type = _type;
/* 1460 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mNESTED_ARG_ACTION()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 1473 */       match(91);
/*      */       while (true)
/*      */       {
/* 1477 */         int alt11 = 5;
/* 1478 */         int LA11_0 = this.input.LA(1);
/*      */ 
/* 1480 */         if (LA11_0 == 93) {
/* 1481 */           alt11 = 5;
/*      */         }
/* 1483 */         else if (LA11_0 == 91) {
/* 1484 */           alt11 = 1;
/*      */         }
/* 1486 */         else if (LA11_0 == 34) {
/* 1487 */           alt11 = 2;
/*      */         }
/* 1489 */         else if (LA11_0 == 39) {
/* 1490 */           alt11 = 3;
/*      */         }
/* 1492 */         else if (((LA11_0 >= 0) && (LA11_0 <= 33)) || ((LA11_0 >= 35) && (LA11_0 <= 38)) || ((LA11_0 >= 40) && (LA11_0 <= 90)) || (LA11_0 == 92) || ((LA11_0 >= 94) && (LA11_0 <= 65535))) {
/* 1493 */           alt11 = 4;
/*      */         }
/*      */ 
/* 1497 */         switch (alt11)
/*      */         {
/*      */         case 1:
/* 1501 */           mNESTED_ARG_ACTION();
/*      */ 
/* 1504 */           break;
/*      */         case 2:
/* 1508 */           mACTION_STRING_LITERAL();
/*      */ 
/* 1511 */           break;
/*      */         case 3:
/* 1515 */           mACTION_CHAR_LITERAL();
/*      */ 
/* 1518 */           break;
/*      */         case 4:
/* 1522 */           matchAny();
/*      */ 
/* 1525 */           break;
/*      */         default:
/* 1528 */           break label182;
/*      */         }
/*      */       }
/*      */ 
/* 1532 */       label182: match(93);
/* 1533 */       setText(getText().substring(1, getText().length() - 1));
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
/* 1546 */       int _type = 45;
/* 1547 */       int _channel = 0;
/*      */ 
/* 1551 */       mNESTED_ACTION();
/*      */ 
/* 1553 */       int alt12 = 2;
/* 1554 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 63:
/* 1557 */         alt12 = 1;
/*      */       }
/*      */ 
/* 1562 */       switch (alt12)
/*      */       {
/*      */       case 1:
/* 1566 */         match(63);
/* 1567 */         _type = 32;
/*      */       }
/*      */ 
/* 1577 */       this.state.type = _type;
/* 1578 */       this.state.channel = _channel;
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
/* 1591 */       match(123);
/*      */       while (true)
/*      */       {
/* 1595 */         int alt13 = 7;
/* 1596 */         alt13 = this.dfa13.predict(this.input);
/* 1597 */         switch (alt13)
/*      */         {
/*      */         case 1:
/* 1601 */           mNESTED_ACTION();
/*      */ 
/* 1604 */           break;
/*      */         case 2:
/* 1608 */           mSL_COMMENT();
/*      */ 
/* 1611 */           break;
/*      */         case 3:
/* 1615 */           mML_COMMENT();
/*      */ 
/* 1618 */           break;
/*      */         case 4:
/* 1622 */           mACTION_STRING_LITERAL();
/*      */ 
/* 1625 */           break;
/*      */         case 5:
/* 1629 */           mACTION_CHAR_LITERAL();
/*      */ 
/* 1632 */           break;
/*      */         case 6:
/* 1636 */           matchAny();
/*      */ 
/* 1639 */           break;
/*      */         default:
/* 1642 */           break label108;
/*      */         }
/*      */       }
/*      */ 
/* 1646 */       label108: match(125);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mACTION_CHAR_LITERAL()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 1662 */       match(39);
/*      */ 
/* 1664 */       int alt14 = 2;
/* 1665 */       int LA14_0 = this.input.LA(1);
/*      */ 
/* 1667 */       if (LA14_0 == 92) {
/* 1668 */         alt14 = 1;
/*      */       }
/* 1670 */       else if (((LA14_0 >= 0) && (LA14_0 <= 38)) || ((LA14_0 >= 40) && (LA14_0 <= 91)) || ((LA14_0 >= 93) && (LA14_0 <= 65535))) {
/* 1671 */         alt14 = 2;
/*      */       }
/*      */       else {
/* 1674 */         NoViableAltException nvae = new NoViableAltException("", 14, 0, this.input);
/*      */ 
/* 1677 */         throw nvae;
/*      */       }
/* 1679 */       switch (alt14)
/*      */       {
/*      */       case 1:
/* 1683 */         mACTION_ESC();
/*      */ 
/* 1686 */         break;
/*      */       case 2:
/* 1690 */         if (((this.input.LA(1) >= 0) && (this.input.LA(1) <= 38)) || ((this.input.LA(1) >= 40) && (this.input.LA(1) <= 91)) || ((this.input.LA(1) >= 93) && (this.input.LA(1) <= 65535))) {
/* 1691 */           this.input.consume();
/*      */         }
/*      */         else
/*      */         {
/* 1695 */           MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 1696 */           recover(mse);
/* 1697 */           throw mse;
/*      */         }
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 1705 */       match(39);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mACTION_STRING_LITERAL()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 1721 */       match(34);
/*      */       while (true)
/*      */       {
/* 1725 */         int alt15 = 3;
/* 1726 */         int LA15_0 = this.input.LA(1);
/*      */ 
/* 1728 */         if (LA15_0 == 92) {
/* 1729 */           alt15 = 1;
/*      */         }
/* 1731 */         else if (((LA15_0 >= 0) && (LA15_0 <= 33)) || ((LA15_0 >= 35) && (LA15_0 <= 91)) || ((LA15_0 >= 93) && (LA15_0 <= 65535))) {
/* 1732 */           alt15 = 2;
/*      */         }
/*      */ 
/* 1736 */         switch (alt15)
/*      */         {
/*      */         case 1:
/* 1740 */           mACTION_ESC();
/*      */ 
/* 1743 */           break;
/*      */         case 2:
/* 1747 */           if (((this.input.LA(1) >= 0) && (this.input.LA(1) <= 33)) || ((this.input.LA(1) >= 35) && (this.input.LA(1) <= 91)) || ((this.input.LA(1) >= 93) && (this.input.LA(1) <= 65535))) {
/* 1748 */             this.input.consume();
/*      */           }
/*      */           else
/*      */           {
/* 1752 */             MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 1753 */             recover(mse);
/* 1754 */             throw mse;
/*      */           }
/*      */ 
/*      */           break;
/*      */         default:
/* 1761 */           break label225;
/*      */         }
/*      */       }
/*      */ 
/* 1765 */       label225: match(34);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mACTION_ESC()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 1779 */       int alt16 = 3;
/* 1780 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 92:
/* 1783 */         int LA16_1 = this.input.LA(2);
/*      */ 
/* 1785 */         if (LA16_1 == 39) {
/* 1786 */           alt16 = 1;
/*      */         }
/* 1788 */         else if (LA16_1 == 34) {
/* 1789 */           alt16 = 2;
/*      */         }
/* 1791 */         else if (((LA16_1 >= 0) && (LA16_1 <= 33)) || ((LA16_1 >= 35) && (LA16_1 <= 38)) || ((LA16_1 >= 40) && (LA16_1 <= 65535))) {
/* 1792 */           alt16 = 3;
/*      */         }
/*      */         else {
/* 1795 */           NoViableAltException nvae = new NoViableAltException("", 16, 1, this.input);
/*      */ 
/* 1798 */           throw nvae;
/*      */         }
/*      */ 
/* 1801 */         break;
/*      */       default:
/* 1803 */         NoViableAltException nvae = new NoViableAltException("", 16, 0, this.input);
/*      */ 
/* 1806 */         throw nvae;
/*      */       }
/*      */ 
/* 1809 */       switch (alt16)
/*      */       {
/*      */       case 1:
/* 1813 */         match("\\'");
/*      */ 
/* 1817 */         break;
/*      */       case 2:
/* 1821 */         match(92);
/* 1822 */         match(34);
/*      */ 
/* 1825 */         break;
/*      */       case 3:
/* 1829 */         match(92);
/* 1830 */         if (((this.input.LA(1) >= 0) && (this.input.LA(1) <= 33)) || ((this.input.LA(1) >= 35) && (this.input.LA(1) <= 38)) || ((this.input.LA(1) >= 40) && (this.input.LA(1) <= 65535))) {
/* 1831 */           this.input.consume();
/*      */         }
/*      */         else
/*      */         {
/* 1835 */           MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 1836 */           recover(mse);
/* 1837 */           throw mse;
/*      */         }
/*      */         break;
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mTOKEN_REF()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 1853 */       int _type = 42;
/* 1854 */       int _channel = 0;
/*      */ 
/* 1858 */       matchRange(65, 90);
/*      */       while (true)
/*      */       {
/* 1862 */         int alt17 = 2;
/* 1863 */         switch (this.input.LA(1))
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
/* 1928 */           alt17 = 1;
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
/* 1934 */         case 96: } switch (alt17)
/*      */         {
/*      */         case 1:
/* 1938 */           if (((this.input.LA(1) >= 48) && (this.input.LA(1) <= 57)) || ((this.input.LA(1) >= 65) && (this.input.LA(1) <= 90)) || (this.input.LA(1) == 95) || ((this.input.LA(1) >= 97) && (this.input.LA(1) <= 122))) {
/* 1939 */             this.input.consume();
/*      */           }
/*      */           else
/*      */           {
/* 1943 */             MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 1944 */             recover(mse);
/* 1945 */             throw mse;
/*      */           }
/*      */ 
/*      */           break;
/*      */         default:
/* 1952 */           break label506;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1959 */       label506: this.state.type = _type;
/* 1960 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mRULE_REF() throws RecognitionException
/*      */   {
/*      */     try {
/* 1970 */       int _type = 49;
/* 1971 */       int _channel = 0;
/*      */ 
/* 1975 */       matchRange(97, 122);
/*      */       while (true)
/*      */       {
/* 1979 */         int alt18 = 2;
/* 1980 */         switch (this.input.LA(1))
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
/* 2045 */           alt18 = 1;
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
/* 2051 */         case 96: } switch (alt18)
/*      */         {
/*      */         case 1:
/* 2055 */           if (((this.input.LA(1) >= 48) && (this.input.LA(1) <= 57)) || ((this.input.LA(1) >= 65) && (this.input.LA(1) <= 90)) || (this.input.LA(1) == 95) || ((this.input.LA(1) >= 97) && (this.input.LA(1) <= 122))) {
/* 2056 */             this.input.consume();
/*      */           }
/*      */           else
/*      */           {
/* 2060 */             MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 2061 */             recover(mse);
/* 2062 */             throw mse;
/*      */           }
/*      */ 
/*      */           break;
/*      */         default:
/* 2069 */           break label506;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2076 */       label506: this.state.type = _type;
/* 2077 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mOPTIONS() throws RecognitionException
/*      */   {
/*      */     try {
/* 2087 */       int _type = 46;
/* 2088 */       int _channel = 0;
/*      */ 
/* 2092 */       match("options");
/*      */ 
/* 2094 */       mWS_LOOP();
/* 2095 */       match(123);
/*      */ 
/* 2099 */       this.state.type = _type;
/* 2100 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mTOKENS() throws RecognitionException
/*      */   {
/*      */     try {
/* 2110 */       int _type = 41;
/* 2111 */       int _channel = 0;
/*      */ 
/* 2115 */       match("tokens");
/*      */ 
/* 2117 */       mWS_LOOP();
/* 2118 */       match(123);
/*      */ 
/* 2122 */       this.state.type = _type;
/* 2123 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mSRC() throws RecognitionException
/*      */   {
/*      */     try {
/* 2133 */       CommonToken file = null;
/* 2134 */       CommonToken line = null;
/*      */ 
/* 2139 */       match("src");
/*      */ 
/* 2141 */       match(32);
/* 2142 */       int fileStart982 = getCharIndex();
/* 2143 */       int fileStartLine982 = getLine();
/* 2144 */       int fileStartCharPos982 = getCharPositionInLine();
/* 2145 */       mACTION_STRING_LITERAL();
/* 2146 */       file = new CommonToken(this.input, 0, 0, fileStart982, getCharIndex() - 1);
/* 2147 */       file.setLine(fileStartLine982);
/* 2148 */       file.setCharPositionInLine(fileStartCharPos982);
/* 2149 */       match(32);
/* 2150 */       int lineStart988 = getCharIndex();
/* 2151 */       int lineStartLine988 = getLine();
/* 2152 */       int lineStartCharPos988 = getCharPositionInLine();
/* 2153 */       mINT();
/* 2154 */       line = new CommonToken(this.input, 0, 0, lineStart988, getCharIndex() - 1);
/* 2155 */       line.setLine(lineStartLine988);
/* 2156 */       line.setCharPositionInLine(lineStartCharPos988);
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
/* 2169 */       int _type = 64;
/* 2170 */       int _channel = 0;
/*      */ 
/* 2175 */       int cnt20 = 0;
/*      */       while (true)
/*      */       {
/* 2178 */         int alt20 = 4;
/* 2179 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 32:
/* 2182 */           alt20 = 1;
/*      */ 
/* 2184 */           break;
/*      */         case 9:
/* 2187 */           alt20 = 2;
/*      */ 
/* 2189 */           break;
/*      */         case 10:
/*      */         case 13:
/* 2193 */           alt20 = 3;
/*      */         }
/*      */ 
/* 2199 */         switch (alt20)
/*      */         {
/*      */         case 1:
/* 2203 */           match(32);
/*      */ 
/* 2206 */           break;
/*      */         case 2:
/* 2210 */           match(9);
/*      */ 
/* 2213 */           break;
/*      */         case 3:
/* 2218 */           int alt19 = 2;
/* 2219 */           switch (this.input.LA(1))
/*      */           {
/*      */           case 13:
/* 2222 */             alt19 = 1;
/*      */           }
/*      */ 
/* 2227 */           switch (alt19)
/*      */           {
/*      */           case 1:
/* 2231 */             match(13);
/*      */           }
/*      */ 
/* 2238 */           match(10);
/*      */ 
/* 2241 */           break;
/*      */         default:
/* 2244 */           if (cnt20 >= 1) break label227;
/* 2245 */           EarlyExitException eee = new EarlyExitException(20, this.input);
/*      */ 
/* 2247 */           throw eee;
/*      */         }
/* 2249 */         cnt20++;
/*      */       }
/*      */ 
/* 2252 */       label227: _channel = 99;
/*      */ 
/* 2256 */       this.state.type = _type;
/* 2257 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mWS_LOOP()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*      */       while (true)
/*      */       {
/* 2273 */         int alt21 = 4;
/* 2274 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 9:
/*      */         case 10:
/*      */         case 13:
/*      */         case 32:
/* 2280 */           alt21 = 1;
/*      */ 
/* 2282 */           break;
/*      */         case 47:
/* 2285 */           switch (this.input.LA(2))
/*      */           {
/*      */           case 47:
/* 2288 */             alt21 = 2;
/*      */ 
/* 2290 */             break;
/*      */           case 42:
/* 2293 */             alt21 = 3;
/*      */           }
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 2304 */         switch (alt21)
/*      */         {
/*      */         case 1:
/* 2308 */           mWS();
/*      */ 
/* 2311 */           break;
/*      */         case 2:
/* 2315 */           mSL_COMMENT();
/*      */ 
/* 2318 */           break;
/*      */         case 3:
/* 2322 */           mML_COMMENT();
/*      */ 
/* 2325 */           break;
/*      */         default:
/* 2328 */           return;
/*      */         }
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public void mTokens()
/*      */     throws RecognitionException
/*      */   {
/* 2343 */     int alt22 = 50;
/* 2344 */     alt22 = this.dfa22.predict(this.input);
/* 2345 */     switch (alt22)
/*      */     {
/*      */     case 1:
/* 2349 */       mSCOPE();
/*      */ 
/* 2352 */       break;
/*      */     case 2:
/* 2356 */       mFRAGMENT();
/*      */ 
/* 2359 */       break;
/*      */     case 3:
/* 2363 */       mTREE_BEGIN();
/*      */ 
/* 2366 */       break;
/*      */     case 4:
/* 2370 */       mROOT();
/*      */ 
/* 2373 */       break;
/*      */     case 5:
/* 2377 */       mBANG();
/*      */ 
/* 2380 */       break;
/*      */     case 6:
/* 2384 */       mRANGE();
/*      */ 
/* 2387 */       break;
/*      */     case 7:
/* 2391 */       mREWRITE();
/*      */ 
/* 2394 */       break;
/*      */     case 8:
/* 2398 */       mT__65();
/*      */ 
/* 2401 */       break;
/*      */     case 9:
/* 2405 */       mT__66();
/*      */ 
/* 2408 */       break;
/*      */     case 10:
/* 2412 */       mT__67();
/*      */ 
/* 2415 */       break;
/*      */     case 11:
/* 2419 */       mT__68();
/*      */ 
/* 2422 */       break;
/*      */     case 12:
/* 2426 */       mT__69();
/*      */ 
/* 2429 */       break;
/*      */     case 13:
/* 2433 */       mT__70();
/*      */ 
/* 2436 */       break;
/*      */     case 14:
/* 2440 */       mT__71();
/*      */ 
/* 2443 */       break;
/*      */     case 15:
/* 2447 */       mT__72();
/*      */ 
/* 2450 */       break;
/*      */     case 16:
/* 2454 */       mT__73();
/*      */ 
/* 2457 */       break;
/*      */     case 17:
/* 2461 */       mT__74();
/*      */ 
/* 2464 */       break;
/*      */     case 18:
/* 2468 */       mT__75();
/*      */ 
/* 2471 */       break;
/*      */     case 19:
/* 2475 */       mT__76();
/*      */ 
/* 2478 */       break;
/*      */     case 20:
/* 2482 */       mT__77();
/*      */ 
/* 2485 */       break;
/*      */     case 21:
/* 2489 */       mT__78();
/*      */ 
/* 2492 */       break;
/*      */     case 22:
/* 2496 */       mT__79();
/*      */ 
/* 2499 */       break;
/*      */     case 23:
/* 2503 */       mT__80();
/*      */ 
/* 2506 */       break;
/*      */     case 24:
/* 2510 */       mT__81();
/*      */ 
/* 2513 */       break;
/*      */     case 25:
/* 2517 */       mT__82();
/*      */ 
/* 2520 */       break;
/*      */     case 26:
/* 2524 */       mT__83();
/*      */ 
/* 2527 */       break;
/*      */     case 27:
/* 2531 */       mT__84();
/*      */ 
/* 2534 */       break;
/*      */     case 28:
/* 2538 */       mT__85();
/*      */ 
/* 2541 */       break;
/*      */     case 29:
/* 2545 */       mT__86();
/*      */ 
/* 2548 */       break;
/*      */     case 30:
/* 2552 */       mT__87();
/*      */ 
/* 2555 */       break;
/*      */     case 31:
/* 2559 */       mT__88();
/*      */ 
/* 2562 */       break;
/*      */     case 32:
/* 2566 */       mT__89();
/*      */ 
/* 2569 */       break;
/*      */     case 33:
/* 2573 */       mT__90();
/*      */ 
/* 2576 */       break;
/*      */     case 34:
/* 2580 */       mT__91();
/*      */ 
/* 2583 */       break;
/*      */     case 35:
/* 2587 */       mT__92();
/*      */ 
/* 2590 */       break;
/*      */     case 36:
/* 2594 */       mT__93();
/*      */ 
/* 2597 */       break;
/*      */     case 37:
/* 2601 */       mSL_COMMENT();
/*      */ 
/* 2604 */       break;
/*      */     case 38:
/* 2608 */       mML_COMMENT();
/*      */ 
/* 2611 */       break;
/*      */     case 39:
/* 2615 */       mCHAR_LITERAL();
/*      */ 
/* 2618 */       break;
/*      */     case 40:
/* 2622 */       mSTRING_LITERAL();
/*      */ 
/* 2625 */       break;
/*      */     case 41:
/* 2629 */       mDOUBLE_QUOTE_STRING_LITERAL();
/*      */ 
/* 2632 */       break;
/*      */     case 42:
/* 2636 */       mDOUBLE_ANGLE_STRING_LITERAL();
/*      */ 
/* 2639 */       break;
/*      */     case 43:
/* 2643 */       mINT();
/*      */ 
/* 2646 */       break;
/*      */     case 44:
/* 2650 */       mARG_ACTION();
/*      */ 
/* 2653 */       break;
/*      */     case 45:
/* 2657 */       mACTION();
/*      */ 
/* 2660 */       break;
/*      */     case 46:
/* 2664 */       mTOKEN_REF();
/*      */ 
/* 2667 */       break;
/*      */     case 47:
/* 2671 */       mRULE_REF();
/*      */ 
/* 2674 */       break;
/*      */     case 48:
/* 2678 */       mOPTIONS();
/*      */ 
/* 2681 */       break;
/*      */     case 49:
/* 2685 */       mTOKENS();
/*      */ 
/* 2688 */       break;
/*      */     case 50:
/* 2692 */       mWS();
/*      */     }
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/* 2760 */     int numStates = DFA2_transitionS.length;
/* 2761 */     DFA2_transition = new short[numStates][];
/* 2762 */     for (int i = 0; i < numStates; i++) {
/* 2763 */       DFA2_transition[i] = DFA.unpackEncodedString(DFA2_transitionS[i]);
/*      */     }
/*      */ 
/* 3092 */     DFA9_transitionS = new String[] { "\"\013\001\006\004\013\001\007\026\013\001\t\035\013\001\b\005\013\001\004\003\013\001\005\007\013\001\001\003\013\001\002\001\013\001\003\001\nï¾Š\013", "", "", "", "", "", "", "", "", "", "\n\f\007ğ¿¿\006\f\032ğ¿¿\006\f", "", "" };
/*      */ 
/* 3109 */     DFA9_eot = DFA.unpackEncodedString("\nğ¿¿\001\013\002ğ¿¿");
/* 3110 */     DFA9_eof = DFA.unpackEncodedString("\rğ¿¿");
/* 3111 */     DFA9_min = DFA.unpackEncodedStringToUnsignedChars("");
/* 3112 */     DFA9_max = DFA.unpackEncodedStringToUnsignedChars("\001ğ¿¿\tğ¿¿\001f\002ğ¿¿");
/* 3113 */     DFA9_accept = DFA.unpackEncodedString("\001ğ¿¿\001\001\001\002\001\003\001\004\001\005\001\006\001\007\001\b\001\t\001ğ¿¿\001\013\001\n");
/* 3114 */     DFA9_special = DFA.unpackEncodedString("");
/*      */ 
/* 3118 */     int numStates = DFA9_transitionS.length;
/* 3119 */     DFA9_transition = new short[numStates][];
/* 3120 */     for (int i = 0; i < numStates; i++) {
/* 3121 */       DFA9_transition[i] = DFA.unpackEncodedString(DFA9_transitionS[i]);
/*      */     }
/*      */ 
/* 3192 */     DFA13_transitionS = new String[] { "\"\006\001\004\004\006\001\005\007\006\001\003K\006\001\002\001\006\001\001ï¾‚\006", "", "", "*\006\001\b\004\006\001\007ï¿\006", "\"\024\001\020\004\024\001\023\007\024\001\022,\024\001\016\036\024\001\021\001\024\001\017ï¾‚\024", "\"\032\001\031\004\032\001\006\007\032\001\030,\032\001\025\036\032\001\027\001\032\001\026ï¾‚\032", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" };
/*      */ 
/* 3225 */     DFA13_eot = DFA.unpackEncodedString("\034ğ¿¿");
/* 3226 */     DFA13_eof = DFA.unpackEncodedString("\034ğ¿¿");
/* 3227 */     DFA13_min = DFA.unpackEncodedStringToUnsignedChars("");
/* 3228 */     DFA13_max = DFA.unpackEncodedStringToUnsignedChars("\001ğ¿¿\002ğ¿¿\003ğ¿¿\026ğ¿¿");
/* 3229 */     DFA13_accept = DFA.unpackEncodedString("\001ğ¿¿\001\007\001\001\003ğ¿¿\001\006\001\002\001\003\005ğ¿¿\007\004\006\005\001ğ¿¿");
/* 3230 */     DFA13_special = DFA.unpackEncodedString("");
/*      */ 
/* 3234 */     int numStates = DFA13_transitionS.length;
/* 3235 */     DFA13_transition = new short[numStates][];
/* 3236 */     for (int i = 0; i < numStates; i++) {
/* 3237 */       DFA13_transition[i] = DFA.unpackEncodedString(DFA13_transitionS[i]);
/*      */     }
/*      */ 
/* 3386 */     DFA22_transitionS = new String[] { "\002%\002ğ¿¿\001%\022ğ¿¿\001%\001\004\001\035\001ğ¿¿\001\032\002ğ¿¿\001\034\001\023\001\025\001\020\001\027\001\022\001\006\001\005\001\033\n\037\001\017\001\013\001\036\001\r\001ğ¿¿\001\031\001\016\032\"\001 \002ğ¿¿\001\003\002ğ¿¿\002$\001\026\002$\001\002\001\n\004$\001\007\002$\001#\001\b\001$\001\021\001\001\001\t\006$\001!\001\024\001\f\001\030", "\001&", "\001(\bğ¿¿\001'", "\001)", "", "\001+", "", "\001-", "\001.\020ğ¿¿\001/\002ğ¿¿\0010", "\0012\006ğ¿¿\0013\002ğ¿¿\0011", "\0014", "", "", "\0015", "", "\0017", "", "\0019", "", "", "", "", "\001:", "\001;", "", "", "", "\001>\004ğ¿¿\001=", "'@\001ğ¿¿4@\001?ï¾£@", "", "", "", "", "", "", "\001A", "", "", "\001B", "\001C", "\001D", "", "", "", "", "\001E", "\001F", "\001H\005ğ¿¿\001G", "\001I", "\001J", "\001K", "\001L", "\001M", "", "", "", "", "\001N", "\001O", "", "", "", "", "\"Z\001U\004Z\001V\026Z\001X\035Z\001W\005Z\001S\003Z\001T\007Z\001P\003Z\001Q\001Z\001R\001Yï¾ŠZ", "'\\\001[ï¿˜\\", "\001]", "\001^", "\001_", "\001`", "\001a", "\001b", "\001c", "\001d", "\001e", "\001f", "\001g", "\001h", "\001i", "\001j", "\001k", "'\\\001[ï¿˜\\", "'\\\001[ï¿˜\\", "'\\\001[ï¿˜\\", "'\\\001[ï¿˜\\", "'\\\001[ï¿˜\\", "'\\\001[ï¿˜\\", "'\\\001[ï¿˜\\", "'\\\001[ï¿˜\\", "'\\\001[ï¿˜\\", "'\\\001[\b\\\nl\007\\\006l\032\\\006lï¾™\\", "'\\\001[ï¿˜\\", "", "", "\001n", "\001o", "\001p", "\001q", "\001r", "\001s", "\001t", "\001u", "\001v", "\n$\007ğ¿¿\032$\004ğ¿¿\001$\001ğ¿¿\032$", "\001x", "\001y", "\001z", "\001{", "\001|", "0\\\n}\007\\\006}\032\\\006}ï¾™\\", "", "\001~", "\n$\007ğ¿¿\032$\004ğ¿¿\001$\001ğ¿¿\032$", "\001Â€", "\001Â", "\n$\007ğ¿¿\032$\004ğ¿¿\001$\001ğ¿¿\032$", "\001Âƒ", "\001Â„", "\001Â…", "\001Â†", "", "\001Â‡", "\001Âˆ", "\001Â‰", "\001ÂŠ", "\n$\007ğ¿¿\032$\004ğ¿¿\001$\001ğ¿¿\032$", "0\\\nÂŒ\007\\\006ÂŒ\032\\\006ÂŒï¾™\\", "\001Â", "", "\001Â", "\001Â", "", "\n$\007ğ¿¿\032$\004ğ¿¿\001$\001ğ¿¿\032$", "\001Â‘", "\001Â’", "\n$\007ğ¿¿\032$\004ğ¿¿\001$\001ğ¿¿\032$", "\n$\007ğ¿¿\032$\004ğ¿¿\001$\001ğ¿¿\032$", "\002Â•\002ğ¿¿\001Â•\022ğ¿¿\001Â•\016ğ¿¿\001Â•Kğ¿¿\001Â•", "\001Â–", "\001Â—", "", "0\\\nÂ˜\007\\\006Â˜\032\\\006Â˜ï¾™\\", "\001Â™", "\001Âš", "\n$\007ğ¿¿\032$\004ğ¿¿\001$\001ğ¿¿\032$", "", "\001Âœ", "\n$\007ğ¿¿\032$\004ğ¿¿\001$\001ğ¿¿\032$", "", "", "", "\n$\007ğ¿¿\032$\004ğ¿¿\001$\001ğ¿¿\032$", "\n$\007ğ¿¿\032$\004ğ¿¿\001$\001ğ¿¿\032$", "'\\\001[ï¿˜\\", "\002Â \002ğ¿¿\001Â \022ğ¿¿\001Â \016ğ¿¿\001Â Kğ¿¿\001Â ", "\n$\007ğ¿¿\032$\004ğ¿¿\001$\001ğ¿¿\032$", "", "\001Â¢", "", "", "", "", "", "\n$\007ğ¿¿\032$\004ğ¿¿\001$\001ğ¿¿\032$", "" };
/*      */ 
/* 3562 */     DFA22_eot = DFA.unpackEncodedString("\001ğ¿¿\002$\001*\001ğ¿¿\001,\001ğ¿¿\004$\002ğ¿¿\0016\001ğ¿¿\0018\001ğ¿¿\001$\004ğ¿¿\001$\001<\013ğ¿¿\001$\002ğ¿¿\003$\004ğ¿¿\b$\004ğ¿¿\002$\006ğ¿¿\017$\rğ¿¿\t$\001w\005$\002ğ¿¿\001$\001\002$\001Â‚\004$\001ğ¿¿\004$\001Â‹\001ğ¿¿\001$\001ğ¿¿\002$\001ğ¿¿\001Â\002$\001Â“\001Â”\003$\002ğ¿¿\002$\001Â›\001ğ¿¿\001$\001Â\003ğ¿¿\001Â\001ÂŸ\001ğ¿¿\001$\001Â¡\001ğ¿¿\001$\005ğ¿¿\001Â£\001ğ¿¿");
/* 3563 */     DFA22_eof = DFA.unpackEncodedString("Â¤ğ¿¿");
/* 3564 */     DFA22_min = DFA.unpackEncodedStringToUnsignedChars("");
/* 3565 */     DFA22_max = DFA.unpackEncodedStringToUnsignedChars("\001~\001c\001r\001(\001ğ¿¿\001.\001ğ¿¿\001e\001u\002r\002ğ¿¿\001>\001ğ¿¿\001:\001ğ¿¿\001e\004ğ¿¿\001a\001=\003ğ¿¿\001/\001ğ¿¿\006ğ¿¿\001p\002ğ¿¿\001o\001a\001n\004ğ¿¿\001x\001r\001o\001b\001e\001r\001k\001a\004ğ¿¿\002t\004ğ¿¿\002ğ¿¿\001t\001p\001g\001a\001e\001s\001t\001v\001l\001e\001o\001e\001m\001u\001c\013ğ¿¿\002ğ¿¿\001i\001e\001m\001l\001r\002e\001a\001i\001z\001w\001n\001m\001r\001h\001ğ¿¿\001ğ¿¿\001o\001z\001e\001l\001z\001r\001c\001t\001c\001ğ¿¿\002s\001a\001n\001z\001ğ¿¿\001n\001ğ¿¿\001n\001y\001ğ¿¿\001z\001t\001e\002z\001{\001r\001s\001ğ¿¿\001ğ¿¿\001s\001t\001z\001ğ¿¿\001e\001z\003ğ¿¿\002z\001ğ¿¿\001{\001z\001ğ¿¿\001d\005ğ¿¿\001z\001ğ¿¿");
/* 3566 */     DFA22_accept = DFA.unpackEncodedString("\004ğ¿¿\001\005\001ğ¿¿\001\007\004ğ¿¿\001\f\001\r\001ğ¿¿\001\017\001ğ¿¿\001\021\001ğ¿¿\001\030\001\031\001\032\001\033\002ğ¿¿\001 \001!\001$\002ğ¿¿\001)\001*\001+\001,\001-\001.\001ğ¿¿\001/\0012\003ğ¿¿\001\003\001\004\001\006\001#\bğ¿¿\001\037\001\016\001\020\001\026\002ğ¿¿\001\036\001\"\001%\001&\034ğ¿¿\001'\001(\020ğ¿¿\001'\tğ¿¿\001\n\007ğ¿¿\001\001\002ğ¿¿\001\b\bğ¿¿\001\034\004ğ¿¿\001\t\002ğ¿¿\001\023\001\027\0011\005ğ¿¿\001\035\001ğ¿¿\001\024\001\013\001\025\0010\001\002\001ğ¿¿\001\022");
/* 3567 */     DFA22_special = DFA.unpackEncodedString("");
/*      */ 
/* 3571 */     int numStates = DFA22_transitionS.length;
/* 3572 */     DFA22_transition = new short[numStates][];
/* 3573 */     for (int i = 0; i < numStates; i++)
/* 3574 */       DFA22_transition[i] = DFA.unpackEncodedString(DFA22_transitionS[i]);
/*      */   }
/*      */ 
/*      */   class DFA22 extends DFA
/*      */   {
/*      */     public DFA22(BaseRecognizer recognizer)
/*      */     {
/* 3581 */       this.recognizer = recognizer;
/* 3582 */       this.decisionNumber = 22;
/* 3583 */       this.eot = ANTLRv3Lexer.DFA22_eot;
/* 3584 */       this.eof = ANTLRv3Lexer.DFA22_eof;
/* 3585 */       this.min = ANTLRv3Lexer.DFA22_min;
/* 3586 */       this.max = ANTLRv3Lexer.DFA22_max;
/* 3587 */       this.accept = ANTLRv3Lexer.DFA22_accept;
/* 3588 */       this.special = ANTLRv3Lexer.DFA22_special;
/* 3589 */       this.transition = ANTLRv3Lexer.DFA22_transition;
/*      */     }
/*      */     public String getDescription() {
/* 3592 */       return "1:1: Tokens : ( SCOPE | FRAGMENT | TREE_BEGIN | ROOT | BANG | RANGE | REWRITE | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | T__83 | T__84 | T__85 | T__86 | T__87 | T__88 | T__89 | T__90 | T__91 | T__92 | T__93 | SL_COMMENT | ML_COMMENT | CHAR_LITERAL | STRING_LITERAL | DOUBLE_QUOTE_STRING_LITERAL | DOUBLE_ANGLE_STRING_LITERAL | INT | ARG_ACTION | ACTION | TOKEN_REF | RULE_REF | OPTIONS | TOKENS | WS );";
/*      */     }
/*      */     public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
/* 3595 */       IntStream input = _input;
/* 3596 */       int _s = s;
/* 3597 */       switch (s) {
/*      */       case 0:
/* 3599 */         int LA22_63 = input.LA(1);
/*      */ 
/* 3601 */         s = -1;
/* 3602 */         if (LA22_63 == 110) s = 80;
/* 3604 */         else if (LA22_63 == 114) s = 81;
/* 3606 */         else if (LA22_63 == 116) s = 82;
/* 3608 */         else if (LA22_63 == 98) s = 83;
/* 3610 */         else if (LA22_63 == 102) s = 84;
/* 3612 */         else if (LA22_63 == 34) s = 85;
/* 3614 */         else if (LA22_63 == 39) s = 86;
/* 3616 */         else if (LA22_63 == 92) s = 87;
/* 3618 */         else if (LA22_63 == 62) s = 88;
/* 3620 */         else if (LA22_63 == 117) s = 89;
/* 3622 */         else if (((LA22_63 >= 0) && (LA22_63 <= 33)) || ((LA22_63 >= 35) && (LA22_63 <= 38)) || ((LA22_63 >= 40) && (LA22_63 <= 61)) || ((LA22_63 >= 63) && (LA22_63 <= 91)) || ((LA22_63 >= 93) && (LA22_63 <= 97)) || ((LA22_63 >= 99) && (LA22_63 <= 101)) || ((LA22_63 >= 103) && (LA22_63 <= 109)) || ((LA22_63 >= 111) && (LA22_63 <= 113)) || (LA22_63 == 115) || ((LA22_63 >= 118) && (LA22_63 <= 65535))) s = 90;
/*      */ 
/* 3624 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 1:
/* 3627 */         int LA22_82 = input.LA(1);
/*      */ 
/* 3629 */         s = -1;
/* 3630 */         if (LA22_82 == 39) s = 91;
/* 3632 */         else if (((LA22_82 >= 0) && (LA22_82 <= 38)) || ((LA22_82 >= 40) && (LA22_82 <= 65535))) s = 92;
/*      */ 
/* 3634 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 2:
/* 3637 */         int LA22_81 = input.LA(1);
/*      */ 
/* 3639 */         s = -1;
/* 3640 */         if (LA22_81 == 39) s = 91;
/* 3642 */         else if (((LA22_81 >= 0) && (LA22_81 <= 38)) || ((LA22_81 >= 40) && (LA22_81 <= 65535))) s = 92;
/*      */ 
/* 3644 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 3:
/* 3647 */         int LA22_80 = input.LA(1);
/*      */ 
/* 3649 */         s = -1;
/* 3650 */         if (LA22_80 == 39) s = 91;
/* 3652 */         else if (((LA22_80 >= 0) && (LA22_80 <= 38)) || ((LA22_80 >= 40) && (LA22_80 <= 65535))) s = 92;
/*      */ 
/* 3654 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 4:
/* 3657 */         int LA22_28 = input.LA(1);
/*      */ 
/* 3659 */         s = -1;
/* 3660 */         if (LA22_28 == 92) s = 63;
/* 3662 */         else if (((LA22_28 >= 0) && (LA22_28 <= 38)) || ((LA22_28 >= 40) && (LA22_28 <= 91)) || ((LA22_28 >= 93) && (LA22_28 <= 65535))) s = 64;
/*      */ 
/* 3664 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 5:
/* 3667 */         int LA22_64 = input.LA(1);
/*      */ 
/* 3669 */         s = -1;
/* 3670 */         if (LA22_64 == 39) s = 91;
/* 3672 */         else if (((LA22_64 >= 0) && (LA22_64 <= 38)) || ((LA22_64 >= 40) && (LA22_64 <= 65535))) s = 92;
/*      */ 
/* 3674 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 6:
/* 3677 */         int LA22_90 = input.LA(1);
/*      */ 
/* 3679 */         s = -1;
/* 3680 */         if (LA22_90 == 39) s = 91;
/* 3682 */         else if (((LA22_90 >= 0) && (LA22_90 <= 38)) || ((LA22_90 >= 40) && (LA22_90 <= 65535))) s = 92;
/*      */ 
/* 3684 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 7:
/* 3687 */         int LA22_89 = input.LA(1);
/*      */ 
/* 3689 */         s = -1;
/* 3690 */         if (((LA22_89 >= 48) && (LA22_89 <= 57)) || ((LA22_89 >= 65) && (LA22_89 <= 70)) || ((LA22_89 >= 97) && (LA22_89 <= 102))) s = 108;
/* 3692 */         else if (LA22_89 == 39) s = 91;
/* 3694 */         else if (((LA22_89 >= 0) && (LA22_89 <= 38)) || ((LA22_89 >= 40) && (LA22_89 <= 47)) || ((LA22_89 >= 58) && (LA22_89 <= 64)) || ((LA22_89 >= 71) && (LA22_89 <= 96)) || ((LA22_89 >= 103) && (LA22_89 <= 65535))) s = 92;
/*      */ 
/* 3696 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 8:
/* 3699 */         int LA22_152 = input.LA(1);
/*      */ 
/* 3701 */         s = -1;
/* 3702 */         if (LA22_152 == 39) s = 91;
/* 3704 */         else if (((LA22_152 >= 0) && (LA22_152 <= 38)) || ((LA22_152 >= 40) && (LA22_152 <= 65535))) s = 92;
/*      */ 
/* 3706 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 9:
/* 3709 */         int LA22_88 = input.LA(1);
/*      */ 
/* 3711 */         s = -1;
/* 3712 */         if (LA22_88 == 39) s = 91;
/* 3714 */         else if (((LA22_88 >= 0) && (LA22_88 <= 38)) || ((LA22_88 >= 40) && (LA22_88 <= 65535))) s = 92;
/*      */ 
/* 3716 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 10:
/* 3719 */         int LA22_87 = input.LA(1);
/*      */ 
/* 3721 */         s = -1;
/* 3722 */         if (LA22_87 == 39) s = 91;
/* 3724 */         else if (((LA22_87 >= 0) && (LA22_87 <= 38)) || ((LA22_87 >= 40) && (LA22_87 <= 65535))) s = 92;
/*      */ 
/* 3726 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 11:
/* 3729 */         int LA22_108 = input.LA(1);
/*      */ 
/* 3731 */         s = -1;
/* 3732 */         if (((LA22_108 >= 48) && (LA22_108 <= 57)) || ((LA22_108 >= 65) && (LA22_108 <= 70)) || ((LA22_108 >= 97) && (LA22_108 <= 102))) s = 125;
/* 3734 */         else if (((LA22_108 >= 0) && (LA22_108 <= 47)) || ((LA22_108 >= 58) && (LA22_108 <= 64)) || ((LA22_108 >= 71) && (LA22_108 <= 96)) || ((LA22_108 >= 103) && (LA22_108 <= 65535))) s = 92;
/*      */ 
/* 3736 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 12:
/* 3739 */         int LA22_84 = input.LA(1);
/*      */ 
/* 3741 */         s = -1;
/* 3742 */         if (LA22_84 == 39) s = 91;
/* 3744 */         else if (((LA22_84 >= 0) && (LA22_84 <= 38)) || ((LA22_84 >= 40) && (LA22_84 <= 65535))) s = 92;
/*      */ 
/* 3746 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 13:
/* 3749 */         int LA22_125 = input.LA(1);
/*      */ 
/* 3751 */         s = -1;
/* 3752 */         if (((LA22_125 >= 48) && (LA22_125 <= 57)) || ((LA22_125 >= 65) && (LA22_125 <= 70)) || ((LA22_125 >= 97) && (LA22_125 <= 102))) s = 140;
/* 3754 */         else if (((LA22_125 >= 0) && (LA22_125 <= 47)) || ((LA22_125 >= 58) && (LA22_125 <= 64)) || ((LA22_125 >= 71) && (LA22_125 <= 96)) || ((LA22_125 >= 103) && (LA22_125 <= 65535))) s = 92;
/*      */ 
/* 3756 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 14:
/* 3759 */         int LA22_83 = input.LA(1);
/*      */ 
/* 3761 */         s = -1;
/* 3762 */         if (LA22_83 == 39) s = 91;
/* 3764 */         else if (((LA22_83 >= 0) && (LA22_83 <= 38)) || ((LA22_83 >= 40) && (LA22_83 <= 65535))) s = 92;
/*      */ 
/* 3766 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 15:
/* 3769 */         int LA22_140 = input.LA(1);
/*      */ 
/* 3771 */         s = -1;
/* 3772 */         if (((LA22_140 >= 48) && (LA22_140 <= 57)) || ((LA22_140 >= 65) && (LA22_140 <= 70)) || ((LA22_140 >= 97) && (LA22_140 <= 102))) s = 152;
/* 3774 */         else if (((LA22_140 >= 0) && (LA22_140 <= 47)) || ((LA22_140 >= 58) && (LA22_140 <= 64)) || ((LA22_140 >= 71) && (LA22_140 <= 96)) || ((LA22_140 >= 103) && (LA22_140 <= 65535))) s = 92;
/*      */ 
/* 3776 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 16:
/* 3779 */         int LA22_86 = input.LA(1);
/*      */ 
/* 3781 */         s = -1;
/* 3782 */         if (LA22_86 == 39) s = 91;
/* 3784 */         else if (((LA22_86 >= 0) && (LA22_86 <= 38)) || ((LA22_86 >= 40) && (LA22_86 <= 65535))) s = 92;
/*      */ 
/* 3786 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 17:
/* 3789 */         int LA22_85 = input.LA(1);
/*      */ 
/* 3791 */         s = -1;
/* 3792 */         if (LA22_85 == 39) s = 91;
/* 3794 */         else if (((LA22_85 >= 0) && (LA22_85 <= 38)) || ((LA22_85 >= 40) && (LA22_85 <= 65535))) s = 92;
/*      */ 
/* 3796 */         if (s >= 0) return s;
/*      */         break;
/*      */       }
/* 3799 */       NoViableAltException nvae = new NoViableAltException(getDescription(), 22, _s, input);
/*      */ 
/* 3801 */       error(nvae);
/* 3802 */       throw nvae;
/*      */     }
/*      */   }
/*      */ 
/*      */   class DFA13 extends DFA
/*      */   {
/*      */     public DFA13(BaseRecognizer recognizer)
/*      */     {
/* 3244 */       this.recognizer = recognizer;
/* 3245 */       this.decisionNumber = 13;
/* 3246 */       this.eot = ANTLRv3Lexer.DFA13_eot;
/* 3247 */       this.eof = ANTLRv3Lexer.DFA13_eof;
/* 3248 */       this.min = ANTLRv3Lexer.DFA13_min;
/* 3249 */       this.max = ANTLRv3Lexer.DFA13_max;
/* 3250 */       this.accept = ANTLRv3Lexer.DFA13_accept;
/* 3251 */       this.special = ANTLRv3Lexer.DFA13_special;
/* 3252 */       this.transition = ANTLRv3Lexer.DFA13_transition;
/*      */     }
/*      */     public String getDescription() {
/* 3255 */       return "()* loopback of 549:2: ( options {greedy=false; k=2; } : NESTED_ACTION | SL_COMMENT | ML_COMMENT | ACTION_STRING_LITERAL | ACTION_CHAR_LITERAL | . )*";
/*      */     }
/*      */     public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
/* 3258 */       IntStream input = _input;
/* 3259 */       int _s = s;
/* 3260 */       switch (s) {
/*      */       case 0:
/* 3262 */         int LA13_0 = input.LA(1);
/*      */ 
/* 3264 */         s = -1;
/* 3265 */         if (LA13_0 == 125) s = 1;
/* 3267 */         else if (LA13_0 == 123) s = 2;
/* 3269 */         else if (LA13_0 == 47) s = 3;
/* 3271 */         else if (LA13_0 == 34) s = 4;
/* 3273 */         else if (LA13_0 == 39) s = 5;
/* 3275 */         else if (((LA13_0 >= 0) && (LA13_0 <= 33)) || ((LA13_0 >= 35) && (LA13_0 <= 38)) || ((LA13_0 >= 40) && (LA13_0 <= 46)) || ((LA13_0 >= 48) && (LA13_0 <= 122)) || (LA13_0 == 124) || ((LA13_0 >= 126) && (LA13_0 <= 65535))) s = 6;
/*      */ 
/* 3277 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 1:
/* 3280 */         int LA13_3 = input.LA(1);
/*      */ 
/* 3282 */         s = -1;
/* 3283 */         if (LA13_3 == 47) s = 7;
/* 3285 */         else if (LA13_3 == 42) s = 8;
/* 3287 */         else if (((LA13_3 >= 0) && (LA13_3 <= 41)) || ((LA13_3 >= 43) && (LA13_3 <= 46)) || ((LA13_3 >= 48) && (LA13_3 <= 65535))) s = 6;
/*      */ 
/* 3289 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 2:
/* 3292 */         int LA13_4 = input.LA(1);
/*      */ 
/* 3294 */         s = -1;
/* 3295 */         if (LA13_4 == 92) s = 14;
/* 3297 */         else if (LA13_4 == 125) s = 15;
/* 3299 */         else if (LA13_4 == 34) s = 16;
/* 3301 */         else if (LA13_4 == 123) s = 17;
/* 3303 */         else if (LA13_4 == 47) s = 18;
/* 3305 */         else if (LA13_4 == 39) s = 19;
/* 3307 */         else if (((LA13_4 >= 0) && (LA13_4 <= 33)) || ((LA13_4 >= 35) && (LA13_4 <= 38)) || ((LA13_4 >= 40) && (LA13_4 <= 46)) || ((LA13_4 >= 48) && (LA13_4 <= 91)) || ((LA13_4 >= 93) && (LA13_4 <= 122)) || (LA13_4 == 124) || ((LA13_4 >= 126) && (LA13_4 <= 65535))) s = 20;
/*      */ 
/* 3309 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 3:
/* 3312 */         int LA13_5 = input.LA(1);
/*      */ 
/* 3314 */         s = -1;
/* 3315 */         if (LA13_5 == 92) s = 21;
/* 3317 */         else if (LA13_5 == 125) s = 22;
/* 3319 */         else if (LA13_5 == 123) s = 23;
/* 3321 */         else if (LA13_5 == 47) s = 24;
/* 3323 */         else if (LA13_5 == 34) s = 25;
/* 3325 */         else if (((LA13_5 >= 0) && (LA13_5 <= 33)) || ((LA13_5 >= 35) && (LA13_5 <= 38)) || ((LA13_5 >= 40) && (LA13_5 <= 46)) || ((LA13_5 >= 48) && (LA13_5 <= 91)) || ((LA13_5 >= 93) && (LA13_5 <= 122)) || (LA13_5 == 124) || ((LA13_5 >= 126) && (LA13_5 <= 65535))) s = 26;
/* 3327 */         else if (LA13_5 == 39) s = 6;
/*      */ 
/* 3329 */         if (s >= 0) return s;
/*      */         break;
/*      */       }
/* 3332 */       NoViableAltException nvae = new NoViableAltException(getDescription(), 13, _s, input);
/*      */ 
/* 3334 */       error(nvae);
/* 3335 */       throw nvae;
/*      */     }
/*      */   }
/*      */ 
/*      */   class DFA9 extends DFA
/*      */   {
/*      */     public DFA9(BaseRecognizer recognizer)
/*      */     {
/* 3128 */       this.recognizer = recognizer;
/* 3129 */       this.decisionNumber = 9;
/* 3130 */       this.eot = ANTLRv3Lexer.DFA9_eot;
/* 3131 */       this.eof = ANTLRv3Lexer.DFA9_eof;
/* 3132 */       this.min = ANTLRv3Lexer.DFA9_min;
/* 3133 */       this.max = ANTLRv3Lexer.DFA9_max;
/* 3134 */       this.accept = ANTLRv3Lexer.DFA9_accept;
/* 3135 */       this.special = ANTLRv3Lexer.DFA9_special;
/* 3136 */       this.transition = ANTLRv3Lexer.DFA9_transition;
/*      */     }
/*      */     public String getDescription() {
/* 3139 */       return "501:3: ( 'n' | 'r' | 't' | 'b' | 'f' | '\"' | '\\'' | '\\\\' | '>' | 'u' XDIGIT XDIGIT XDIGIT XDIGIT | . )";
/*      */     }
/*      */     public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
/* 3142 */       IntStream input = _input;
/* 3143 */       int _s = s;
/* 3144 */       switch (s) {
/*      */       case 0:
/* 3146 */         int LA9_0 = input.LA(1);
/*      */ 
/* 3148 */         s = -1;
/* 3149 */         if (LA9_0 == 110) s = 1;
/* 3151 */         else if (LA9_0 == 114) s = 2;
/* 3153 */         else if (LA9_0 == 116) s = 3;
/* 3155 */         else if (LA9_0 == 98) s = 4;
/* 3157 */         else if (LA9_0 == 102) s = 5;
/* 3159 */         else if (LA9_0 == 34) s = 6;
/* 3161 */         else if (LA9_0 == 39) s = 7;
/* 3163 */         else if (LA9_0 == 92) s = 8;
/* 3165 */         else if (LA9_0 == 62) s = 9;
/* 3167 */         else if (LA9_0 == 117) s = 10;
/* 3169 */         else if (((LA9_0 >= 0) && (LA9_0 <= 33)) || ((LA9_0 >= 35) && (LA9_0 <= 38)) || ((LA9_0 >= 40) && (LA9_0 <= 61)) || ((LA9_0 >= 63) && (LA9_0 <= 91)) || ((LA9_0 >= 93) && (LA9_0 <= 97)) || ((LA9_0 >= 99) && (LA9_0 <= 101)) || ((LA9_0 >= 103) && (LA9_0 <= 109)) || ((LA9_0 >= 111) && (LA9_0 <= 113)) || (LA9_0 == 115) || ((LA9_0 >= 118) && (LA9_0 <= 65535))) s = 11;
/*      */ 
/* 3171 */         if (s >= 0) return s;
/*      */         break;
/*      */       }
/* 3174 */       NoViableAltException nvae = new NoViableAltException(getDescription(), 9, _s, input);
/*      */ 
/* 3176 */       error(nvae);
/* 3177 */       throw nvae;
/*      */     }
/*      */   }
/*      */ 
/*      */   class DFA2 extends DFA
/*      */   {
/*      */     public DFA2(BaseRecognizer recognizer)
/*      */     {
/* 2770 */       this.recognizer = recognizer;
/* 2771 */       this.decisionNumber = 2;
/* 2772 */       this.eot = ANTLRv3Lexer.DFA2_eot;
/* 2773 */       this.eof = ANTLRv3Lexer.DFA2_eof;
/* 2774 */       this.min = ANTLRv3Lexer.DFA2_min;
/* 2775 */       this.max = ANTLRv3Lexer.DFA2_max;
/* 2776 */       this.accept = ANTLRv3Lexer.DFA2_accept;
/* 2777 */       this.special = ANTLRv3Lexer.DFA2_special;
/* 2778 */       this.transition = ANTLRv3Lexer.DFA2_transition;
/*      */     }
/*      */     public String getDescription() {
/* 2781 */       return "466:5: ( ' $ANTLR ' SRC | (~ ( '\\r' | '\\n' ) )* )";
/*      */     }
/*      */     public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
/* 2784 */       IntStream input = _input;
/* 2785 */       int _s = s;
/* 2786 */       switch (s) {
/*      */       case 0:
/* 2788 */         int LA2_6 = input.LA(1);
/*      */ 
/* 2790 */         s = -1;
/* 2791 */         if (LA2_6 == 76) s = 7;
/* 2793 */         else if (((LA2_6 >= 0) && (LA2_6 <= 75)) || ((LA2_6 >= 77) && (LA2_6 <= 65535))) s = 2;
/*      */ 
/* 2795 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 1:
/* 2798 */         int LA2_14 = input.LA(1);
/*      */ 
/* 2800 */         s = -1;
/* 2801 */         if (LA2_14 == 92) s = 15;
/* 2803 */         else if (LA2_14 == 13) s = 16;
/* 2805 */         else if (LA2_14 == 34) s = 17;
/* 2807 */         else if (LA2_14 == 10) s = 18;
/* 2809 */         else if (((LA2_14 >= 0) && (LA2_14 <= 9)) || ((LA2_14 >= 11) && (LA2_14 <= 12)) || ((LA2_14 >= 14) && (LA2_14 <= 33)) || ((LA2_14 >= 35) && (LA2_14 <= 91)) || ((LA2_14 >= 93) && (LA2_14 <= 65535))) s = 19;
/*      */ 
/* 2811 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 2:
/* 2814 */         int LA2_7 = input.LA(1);
/*      */ 
/* 2816 */         s = -1;
/* 2817 */         if (LA2_7 == 82) s = 8;
/* 2819 */         else if (((LA2_7 >= 0) && (LA2_7 <= 81)) || ((LA2_7 >= 83) && (LA2_7 <= 65535))) s = 2;
/*      */ 
/* 2821 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 3:
/* 2824 */         int LA2_8 = input.LA(1);
/*      */ 
/* 2826 */         s = -1;
/* 2827 */         if (LA2_8 == 32) s = 9;
/* 2829 */         else if (((LA2_8 >= 0) && (LA2_8 <= 31)) || ((LA2_8 >= 33) && (LA2_8 <= 65535))) s = 2;
/*      */ 
/* 2831 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 4:
/* 2834 */         int LA2_23 = input.LA(1);
/*      */ 
/* 2836 */         s = -1;
/* 2837 */         if ((LA2_23 >= 0) && (LA2_23 <= 65535)) s = 25;
/*      */         else {
/* 2839 */           s = 2;
/*      */         }
/* 2841 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 5:
/* 2844 */         int LA2_13 = input.LA(1);
/*      */ 
/* 2846 */         s = -1;
/* 2847 */         if (LA2_13 == 34) s = 14;
/* 2849 */         else if (((LA2_13 >= 0) && (LA2_13 <= 33)) || ((LA2_13 >= 35) && (LA2_13 <= 65535))) s = 2;
/*      */ 
/* 2851 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 6:
/* 2854 */         int LA2_17 = input.LA(1);
/*      */ 
/* 2856 */         s = -1;
/* 2857 */         if (LA2_17 == 32) s = 26;
/* 2859 */         else if (((LA2_17 >= 0) && (LA2_17 <= 31)) || ((LA2_17 >= 33) && (LA2_17 <= 65535))) s = 2;
/*      */ 
/* 2861 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 7:
/* 2864 */         int LA2_16 = input.LA(1);
/*      */ 
/* 2866 */         s = -1;
/* 2867 */         if (((LA2_16 >= 0) && (LA2_16 <= 9)) || ((LA2_16 >= 11) && (LA2_16 <= 65535))) s = 25;
/* 2869 */         else if (LA2_16 == 10) s = 18;
/*      */ 
/* 2871 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 8:
/* 2874 */         int LA2_19 = input.LA(1);
/*      */ 
/* 2876 */         s = -1;
/* 2877 */         if (LA2_19 == 34) s = 17;
/* 2879 */         else if (LA2_19 == 92) s = 15;
/* 2881 */         else if (LA2_19 == 13) s = 16;
/* 2883 */         else if (LA2_19 == 10) s = 18;
/* 2885 */         else if (((LA2_19 >= 0) && (LA2_19 <= 9)) || ((LA2_19 >= 11) && (LA2_19 <= 12)) || ((LA2_19 >= 14) && (LA2_19 <= 33)) || ((LA2_19 >= 35) && (LA2_19 <= 91)) || ((LA2_19 >= 93) && (LA2_19 <= 65535))) s = 19;
/*      */ 
/* 2887 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 9:
/* 2890 */         int LA2_3 = input.LA(1);
/*      */ 
/* 2892 */         s = -1;
/* 2893 */         if (LA2_3 == 65) s = 4;
/* 2895 */         else if (((LA2_3 >= 0) && (LA2_3 <= 64)) || ((LA2_3 >= 66) && (LA2_3 <= 65535))) s = 2;
/*      */ 
/* 2897 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 10:
/* 2900 */         int LA2_1 = input.LA(1);
/*      */ 
/* 2902 */         s = -1;
/* 2903 */         if (LA2_1 == 36) s = 3;
/* 2905 */         else if (((LA2_1 >= 0) && (LA2_1 <= 35)) || ((LA2_1 >= 37) && (LA2_1 <= 65535))) s = 2;
/*      */ 
/* 2907 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 11:
/* 2910 */         int LA2_5 = input.LA(1);
/*      */ 
/* 2912 */         s = -1;
/* 2913 */         if (LA2_5 == 84) s = 6;
/* 2915 */         else if (((LA2_5 >= 0) && (LA2_5 <= 83)) || ((LA2_5 >= 85) && (LA2_5 <= 65535))) s = 2;
/*      */ 
/* 2917 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 12:
/* 2920 */         int LA2_4 = input.LA(1);
/*      */ 
/* 2922 */         s = -1;
/* 2923 */         if (LA2_4 == 78) s = 5;
/* 2925 */         else if (((LA2_4 >= 0) && (LA2_4 <= 77)) || ((LA2_4 >= 79) && (LA2_4 <= 65535))) s = 2;
/*      */ 
/* 2927 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 13:
/* 2930 */         int LA2_26 = input.LA(1);
/*      */ 
/* 2932 */         s = -1;
/* 2933 */         if ((LA2_26 >= 48) && (LA2_26 <= 57)) s = 27;
/* 2935 */         else if (((LA2_26 >= 0) && (LA2_26 <= 47)) || ((LA2_26 >= 58) && (LA2_26 <= 65535))) s = 2;
/*      */ 
/* 2937 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 14:
/* 2940 */         int LA2_21 = input.LA(1);
/*      */ 
/* 2942 */         s = -1;
/* 2943 */         if (LA2_21 == 34) s = 17;
/* 2945 */         else if (LA2_21 == 92) s = 15;
/* 2947 */         else if (LA2_21 == 13) s = 16;
/* 2949 */         else if (LA2_21 == 10) s = 18;
/* 2951 */         else if (((LA2_21 >= 0) && (LA2_21 <= 9)) || ((LA2_21 >= 11) && (LA2_21 <= 12)) || ((LA2_21 >= 14) && (LA2_21 <= 33)) || ((LA2_21 >= 35) && (LA2_21 <= 91)) || ((LA2_21 >= 93) && (LA2_21 <= 65535))) s = 19;
/*      */ 
/* 2953 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 15:
/* 2956 */         int LA2_20 = input.LA(1);
/*      */ 
/* 2958 */         s = -1;
/* 2959 */         if (LA2_20 == 34) s = 17;
/* 2961 */         else if (LA2_20 == 92) s = 15;
/* 2963 */         else if (LA2_20 == 13) s = 16;
/* 2965 */         else if (LA2_20 == 10) s = 18;
/* 2967 */         else if (((LA2_20 >= 0) && (LA2_20 <= 9)) || ((LA2_20 >= 11) && (LA2_20 <= 12)) || ((LA2_20 >= 14) && (LA2_20 <= 33)) || ((LA2_20 >= 35) && (LA2_20 <= 91)) || ((LA2_20 >= 93) && (LA2_20 <= 65535))) s = 19;
/*      */ 
/* 2969 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 16:
/* 2972 */         int LA2_18 = input.LA(1);
/*      */ 
/* 2974 */         s = -1;
/* 2975 */         if ((LA2_18 >= 0) && (LA2_18 <= 65535)) s = 25;
/*      */         else {
/* 2977 */           s = 2;
/*      */         }
/* 2979 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 17:
/* 2982 */         int LA2_0 = input.LA(1);
/*      */ 
/* 2984 */         s = -1;
/* 2985 */         if (LA2_0 == 32) s = 1;
/* 2987 */         else if (((LA2_0 >= 0) && (LA2_0 <= 31)) || ((LA2_0 >= 33) && (LA2_0 <= 65535))) s = 2;
/*      */ 
/* 2989 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 18:
/* 2992 */         int LA2_12 = input.LA(1);
/*      */ 
/* 2994 */         s = -1;
/* 2995 */         if (LA2_12 == 32) s = 13;
/* 2997 */         else if (((LA2_12 >= 0) && (LA2_12 <= 31)) || ((LA2_12 >= 33) && (LA2_12 <= 65535))) s = 2;
/*      */ 
/* 2999 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 19:
/* 3002 */         int LA2_22 = input.LA(1);
/*      */ 
/* 3004 */         s = -1;
/* 3005 */         if (((LA2_22 >= 0) && (LA2_22 <= 9)) || ((LA2_22 >= 11) && (LA2_22 <= 65535))) s = 25;
/* 3007 */         else if (LA2_22 == 10) s = 18;
/*      */ 
/* 3009 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 20:
/* 3012 */         int LA2_9 = input.LA(1);
/*      */ 
/* 3014 */         s = -1;
/* 3015 */         if (LA2_9 == 115) s = 10;
/* 3017 */         else if (((LA2_9 >= 0) && (LA2_9 <= 114)) || ((LA2_9 >= 116) && (LA2_9 <= 65535))) s = 2;
/*      */ 
/* 3019 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 21:
/* 3022 */         int LA2_10 = input.LA(1);
/*      */ 
/* 3024 */         s = -1;
/* 3025 */         if (LA2_10 == 114) s = 11;
/* 3027 */         else if (((LA2_10 >= 0) && (LA2_10 <= 113)) || ((LA2_10 >= 115) && (LA2_10 <= 65535))) s = 2;
/*      */ 
/* 3029 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 22:
/* 3032 */         int LA2_15 = input.LA(1);
/*      */ 
/* 3034 */         s = -1;
/* 3035 */         if (LA2_15 == 39) s = 20;
/* 3037 */         else if (LA2_15 == 34) s = 21;
/* 3039 */         else if (LA2_15 == 13) s = 22;
/* 3041 */         else if (LA2_15 == 10) s = 23;
/* 3043 */         else if (((LA2_15 >= 0) && (LA2_15 <= 9)) || ((LA2_15 >= 11) && (LA2_15 <= 12)) || ((LA2_15 >= 14) && (LA2_15 <= 33)) || ((LA2_15 >= 35) && (LA2_15 <= 38)) || ((LA2_15 >= 40) && (LA2_15 <= 65535))) s = 24;
/*      */ 
/* 3045 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 23:
/* 3048 */         int LA2_11 = input.LA(1);
/*      */ 
/* 3050 */         s = -1;
/* 3051 */         if (LA2_11 == 99) s = 12;
/* 3053 */         else if (((LA2_11 >= 0) && (LA2_11 <= 98)) || ((LA2_11 >= 100) && (LA2_11 <= 65535))) s = 2;
/*      */ 
/* 3055 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 24:
/* 3058 */         int LA2_24 = input.LA(1);
/*      */ 
/* 3060 */         s = -1;
/* 3061 */         if (LA2_24 == 34) s = 17;
/* 3063 */         else if (LA2_24 == 92) s = 15;
/* 3065 */         else if (LA2_24 == 13) s = 16;
/* 3067 */         else if (LA2_24 == 10) s = 18;
/* 3069 */         else if (((LA2_24 >= 0) && (LA2_24 <= 9)) || ((LA2_24 >= 11) && (LA2_24 <= 12)) || ((LA2_24 >= 14) && (LA2_24 <= 33)) || ((LA2_24 >= 35) && (LA2_24 <= 91)) || ((LA2_24 >= 93) && (LA2_24 <= 65535))) s = 19;
/*      */ 
/* 3071 */         if (s >= 0) return s;
/*      */         break;
/*      */       }
/* 3074 */       NoViableAltException nvae = new NoViableAltException(getDescription(), 2, _s, input);
/*      */ 
/* 3076 */       error(nvae);
/* 3077 */       throw nvae;
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.swingui.parsers.ANTLRv3Lexer
 * JD-Core Version:    0.6.2
 */