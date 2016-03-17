/*      */ package org.antlr.grammar.v3;
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
/*      */   public static final int BACKTRACK_SEMPRED = 34;
/*      */   public static final int DOUBLE_ANGLE_STRING_LITERAL = 53;
/*      */   public static final int LEXER_GRAMMAR = 24;
/*      */   public static final int EOA = 19;
/*      */   public static final int ARGLIST = 22;
/*      */   public static final int EOF = -1;
/*      */   public static final int SEMPRED = 31;
/*      */   public static final int ACTION = 47;
/*      */   public static final int EOB = 18;
/*      */   public static final int TOKEN_REF = 44;
/*      */   public static final int T__93 = 93;
/*      */   public static final int T__91 = 91;
/*      */   public static final int RET = 23;
/*      */   public static final int T__92 = 92;
/*      */   public static final int STRING_LITERAL = 45;
/*      */   public static final int T__90 = 90;
/*      */   public static final int ARG = 21;
/*      */   public static final int EOR = 17;
/*      */   public static final int ARG_ACTION = 50;
/*      */   public static final int DOUBLE_QUOTE_STRING_LITERAL = 52;
/*      */   public static final int NESTED_ARG_ACTION = 60;
/*      */   public static final int ACTION_CHAR_LITERAL = 62;
/*      */   public static final int T__80 = 80;
/*      */   public static final int T__81 = 81;
/*      */   public static final int T__82 = 82;
/*      */   public static final int RULE = 7;
/*      */   public static final int ACTION_ESC = 64;
/*      */   public static final int T__83 = 83;
/*      */   public static final int PARSER_GRAMMAR = 25;
/*      */   public static final int SRC = 54;
/*      */   public static final int INT = 49;
/*      */   public static final int CHAR_RANGE = 14;
/*      */   public static final int EPSILON = 15;
/*      */   public static final int T__85 = 85;
/*      */   public static final int T__84 = 84;
/*      */   public static final int T__87 = 87;
/*      */   public static final int T__86 = 86;
/*      */   public static final int REWRITE = 39;
/*      */   public static final int T__89 = 89;
/*      */   public static final int T__88 = 88;
/*      */   public static final int WS = 66;
/*      */   public static final int T__71 = 71;
/*      */   public static final int T__72 = 72;
/*      */   public static final int COMBINED_GRAMMAR = 27;
/*      */   public static final int T__70 = 70;
/*      */   public static final int LEXER = 6;
/*      */   public static final int SL_COMMENT = 55;
/*      */   public static final int TREE_GRAMMAR = 26;
/*      */   public static final int T__76 = 76;
/*      */   public static final int CLOSURE = 10;
/*      */   public static final int T__75 = 75;
/*      */   public static final int PARSER = 5;
/*      */   public static final int T__74 = 74;
/*      */   public static final int T__73 = 73;
/*      */   public static final int T__79 = 79;
/*      */   public static final int T__78 = 78;
/*      */   public static final int T__77 = 77;
/*      */   public static final int T__68 = 68;
/*      */   public static final int T__69 = 69;
/*      */   public static final int T__67 = 67;
/*      */   public static final int NESTED_ACTION = 63;
/*      */   public static final int ESC = 58;
/*      */   public static final int FRAGMENT = 35;
/*      */   public static final int ID = 20;
/*      */   public static final int TREE_BEGIN = 36;
/*      */   public static final int AT = 40;
/*      */   public static final int ML_COMMENT = 56;
/*      */   public static final int ALT = 16;
/*      */   public static final int SCOPE = 30;
/*      */   public static final int LABEL_ASSIGN = 41;
/*      */   public static final int DOC_COMMENT = 4;
/*      */   public static final int WS_LOOP = 65;
/*      */   public static final int RANGE = 13;
/*      */   public static final int TOKENS = 43;
/*      */   public static final int GATED_SEMPRED = 32;
/*      */   public static final int LITERAL_CHAR = 57;
/*      */   public static final int BANG = 38;
/*      */   public static final int LIST_LABEL_ASSIGN = 42;
/*      */   public static final int ACTION_STRING_LITERAL = 61;
/*      */   public static final int ROOT = 37;
/*      */   public static final int RULE_REF = 51;
/*      */   public static final int SYNPRED = 12;
/*      */   public static final int OPTIONAL = 9;
/*      */   public static final int CHAR_LITERAL = 46;
/*      */   public static final int LABEL = 28;
/*      */   public static final int TEMPLATE = 29;
/*      */   public static final int SYN_SEMPRED = 33;
/*      */   public static final int XDIGIT = 59;
/*      */   public static final int BLOCK = 8;
/*      */   public static final int POSITIVE_CLOSURE = 11;
/*      */   public static final int OPTIONS = 48;
/* 2747 */   protected DFA2 dfa2 = new DFA2(this);
/* 2748 */   protected DFA9 dfa9 = new DFA9(this);
/* 2749 */   protected DFA13 dfa13 = new DFA13(this);
/* 2750 */   protected DFA22 dfa22 = new DFA22(this);
/*      */   static final String DFA2_eotS = "\020ğ¿¿\001\002\007ğ¿¿\001\002\003ğ¿¿";
/*      */   static final String DFA2_eofS = "\034ğ¿¿";
/*      */   static final String DFA2_minS = "";
/*      */   static final String DFA2_maxS = "\002ğ¿¿\001ğ¿¿\021ğ¿¿\001ğ¿¿\006ğ¿¿\001ğ¿¿";
/*      */   static final String DFA2_acceptS = "\002ğ¿¿\001\002\021ğ¿¿\001\001\006ğ¿¿\001\001";
/*      */   static final String DFA2_specialS = "";
/* 2765 */   static final String[] DFA2_transitionS = { " \002\001\001ï¿Ÿ\002", "$\002\001\003ï¿›\002", "", "A\002\001\004ï¾¾\002", "N\002\001\005ï¾±\002", "T\002\001\006ï¾«\002", "L\002\001\007ï¾³\002", "R\002\001\bï¾­\002", " \002\001\tï¿Ÿ\002", "s\002\001\nï¾Œ\002", "r\002\001\013ï¾\002", "c\002\001\fï¾œ\002", " \002\001\rï¿Ÿ\002", "\"\002\001\016ï¿\002", "\n\022\001\020\002\022\001\017\024\022\001\0239\022\001\021ï¾£\022", "\n\024\001\020ï¿µ\024", "", "\n\031\001\030\002\031\001\027\024\031\001\026\004\031\001\025ï¿˜\031", "\n\022\001\020\002\022\001\017\024\022\001\0239\022\001\021ï¾£\022", " \002\001\032ï¿Ÿ\002", "", "\n\022\001\020\002\022\001\017\024\022\001\0239\022\001\021ï¾£\022", "\n\022\001\020\002\022\001\017\024\022\001\0239\022\001\021ï¾£\022", "\n\024\001\020ï¿µ\024", "", "\n\022\001\020\002\022\001\017\024\022\001\0239\022\001\021ï¾£\022", "0\002\n\033ï¿†\002", "" };
/*      */ 
/* 2796 */   static final short[] DFA2_eot = DFA.unpackEncodedString("\020ğ¿¿\001\002\007ğ¿¿\001\002\003ğ¿¿");
/* 2797 */   static final short[] DFA2_eof = DFA.unpackEncodedString("\034ğ¿¿");
/* 2798 */   static final char[] DFA2_min = DFA.unpackEncodedStringToUnsignedChars("");
/* 2799 */   static final char[] DFA2_max = DFA.unpackEncodedStringToUnsignedChars("\002ğ¿¿\001ğ¿¿\021ğ¿¿\001ğ¿¿\006ğ¿¿\001ğ¿¿");
/* 2800 */   static final short[] DFA2_accept = DFA.unpackEncodedString("\002ğ¿¿\001\002\021ğ¿¿\001\001\006ğ¿¿\001\001");
/* 2801 */   static final short[] DFA2_special = DFA.unpackEncodedString("");
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
/*      */   static final String DFA22_eotS = "\001ğ¿¿\003%\001,\001ğ¿¿\001.\002ğ¿¿\0010\0012\004%\002ğ¿¿\001<\005ğ¿¿\001%\001ğ¿¿\001?\nğ¿¿\001%\002ğ¿¿\004%\bğ¿¿\b%\002ğ¿¿\001%\006ğ¿¿\017%\rğ¿¿\n%\001{\004%\002ğ¿¿\002%\001Âƒ\002%\001Â†\004%\001ğ¿¿\003%\001Â\001ğ¿¿\002%\001ğ¿¿\002%\001ğ¿¿\001Â”\002%\001Â—\001Â˜\002%\002ğ¿¿\001%\001Â\001%\001ÂŸ\001ğ¿¿\001%\001Â¡\003ğ¿¿\001Â¢\001ğ¿¿\001%\001ğ¿¿\001Â¤\001ğ¿¿\001%\004ğ¿¿\001Â¦\001ğ¿¿";
/*      */   static final String DFA22_eofS = "Â§ğ¿¿";
/*      */   static final String DFA22_minS = "";
/*      */   static final String DFA22_maxS = "\001~\001e\001c\001r\001(\001ğ¿¿\001.\002ğ¿¿\001>\001=\001e\001u\002r\002ğ¿¿\001:\005ğ¿¿\001a\001ğ¿¿\001<\003ğ¿¿\001/\001ğ¿¿\005ğ¿¿\001p\002ğ¿¿\001t\001o\001a\001n\bğ¿¿\001x\001r\001o\001b\001e\001r\001k\001a\002ğ¿¿\001t\004ğ¿¿\002ğ¿¿\001t\001u\001p\001g\001a\001e\001s\001t\001v\001l\001e\001o\001e\001m\001c\013ğ¿¿\002ğ¿¿\001i\001r\001e\001m\001l\001r\002e\001a\001i\001z\001w\001n\001m\001h\001ğ¿¿\001ğ¿¿\001o\001n\001z\001e\001l\001z\001r\001c\001t\001c\001ğ¿¿\002s\001a\001z\001ğ¿¿\001n\001s\001ğ¿¿\001n\001y\001ğ¿¿\001z\001t\001e\002z\001{\001r\001ğ¿¿\001ğ¿¿\001s\001z\001t\001z\001ğ¿¿\001e\001z\003ğ¿¿\001z\001ğ¿¿\001{\001ğ¿¿\001z\001ğ¿¿\001d\004ğ¿¿\001z\001ğ¿¿";
/*      */   static final String DFA22_acceptS = "\005ğ¿¿\001\006\001ğ¿¿\001\b\001\t\006ğ¿¿\001\020\001\021\001ğ¿¿\001\023\001\031\001\032\001\033\001\034\001ğ¿¿\001 \001ğ¿¿\001\"\001$\001&\002ğ¿¿\001+\001-\001.\001/\0010\001ğ¿¿\0011\0014\004ğ¿¿\001\004\001\005\001\007\001#\001\037\001\n\001\013\001%\bğ¿¿\001\022\001\027\001ğ¿¿\001,\001!\001'\001(\034ğ¿¿\001*\001)\020ğ¿¿\001)\nğ¿¿\001\016\007ğ¿¿\001\002\002ğ¿¿\001\f\007ğ¿¿\001\035\005ğ¿¿\001\r\002ğ¿¿\001\025\001\030\0013\003ğ¿¿\001\001\001ğ¿¿\001\036\001ğ¿¿\001\026\001\017\0012\001\003\001ğ¿¿\001\024";
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
/*  115 */     return "org/antlr/grammar/v3/ANTLRv3.g";
/*      */   }
/*      */ 
/*      */   public final void mRET() throws RecognitionException {
/*      */     try {
/*  120 */       int _type = 23;
/*  121 */       int _channel = 0;
/*      */ 
/*  125 */       match("returns");
/*      */ 
/*  130 */       this.state.type = _type;
/*  131 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mSCOPE() throws RecognitionException
/*      */   {
/*      */     try {
/*  141 */       int _type = 30;
/*  142 */       int _channel = 0;
/*      */ 
/*  146 */       match("scope");
/*      */ 
/*  151 */       this.state.type = _type;
/*  152 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mFRAGMENT() throws RecognitionException
/*      */   {
/*      */     try {
/*  162 */       int _type = 35;
/*  163 */       int _channel = 0;
/*      */ 
/*  167 */       match("fragment");
/*      */ 
/*  172 */       this.state.type = _type;
/*  173 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mTREE_BEGIN() throws RecognitionException
/*      */   {
/*      */     try {
/*  183 */       int _type = 36;
/*  184 */       int _channel = 0;
/*      */ 
/*  188 */       match("^(");
/*      */ 
/*  193 */       this.state.type = _type;
/*  194 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mROOT() throws RecognitionException
/*      */   {
/*      */     try {
/*  204 */       int _type = 37;
/*  205 */       int _channel = 0;
/*      */ 
/*  209 */       match(94);
/*      */ 
/*  213 */       this.state.type = _type;
/*  214 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mBANG() throws RecognitionException
/*      */   {
/*      */     try {
/*  224 */       int _type = 38;
/*  225 */       int _channel = 0;
/*      */ 
/*  229 */       match(33);
/*      */ 
/*  233 */       this.state.type = _type;
/*  234 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mRANGE() throws RecognitionException
/*      */   {
/*      */     try {
/*  244 */       int _type = 13;
/*  245 */       int _channel = 0;
/*      */ 
/*  249 */       match("..");
/*      */ 
/*  254 */       this.state.type = _type;
/*  255 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mREWRITE() throws RecognitionException
/*      */   {
/*      */     try {
/*  265 */       int _type = 39;
/*  266 */       int _channel = 0;
/*      */ 
/*  270 */       match("->");
/*      */ 
/*  275 */       this.state.type = _type;
/*  276 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mAT() throws RecognitionException
/*      */   {
/*      */     try {
/*  286 */       int _type = 40;
/*  287 */       int _channel = 0;
/*      */ 
/*  291 */       match(64);
/*      */ 
/*  295 */       this.state.type = _type;
/*  296 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mLABEL_ASSIGN() throws RecognitionException
/*      */   {
/*      */     try {
/*  306 */       int _type = 41;
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
/*      */   public final void mLIST_LABEL_ASSIGN() throws RecognitionException
/*      */   {
/*      */     try {
/*  326 */       int _type = 42;
/*  327 */       int _channel = 0;
/*      */ 
/*  331 */       match("+=");
/*      */ 
/*  336 */       this.state.type = _type;
/*  337 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__67() throws RecognitionException
/*      */   {
/*      */     try {
/*  347 */       int _type = 67;
/*  348 */       int _channel = 0;
/*      */ 
/*  352 */       match("lexer");
/*      */ 
/*  357 */       this.state.type = _type;
/*  358 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__68() throws RecognitionException
/*      */   {
/*      */     try {
/*  368 */       int _type = 68;
/*  369 */       int _channel = 0;
/*      */ 
/*  373 */       match("parser");
/*      */ 
/*  378 */       this.state.type = _type;
/*  379 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__69() throws RecognitionException
/*      */   {
/*      */     try {
/*  389 */       int _type = 69;
/*  390 */       int _channel = 0;
/*      */ 
/*  394 */       match("tree");
/*      */ 
/*  399 */       this.state.type = _type;
/*  400 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__70() throws RecognitionException
/*      */   {
/*      */     try {
/*  410 */       int _type = 70;
/*  411 */       int _channel = 0;
/*      */ 
/*  415 */       match("grammar");
/*      */ 
/*  420 */       this.state.type = _type;
/*  421 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__71() throws RecognitionException
/*      */   {
/*      */     try {
/*  431 */       int _type = 71;
/*  432 */       int _channel = 0;
/*      */ 
/*  436 */       match(59);
/*      */ 
/*  440 */       this.state.type = _type;
/*  441 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__72() throws RecognitionException
/*      */   {
/*      */     try {
/*  451 */       int _type = 72;
/*  452 */       int _channel = 0;
/*      */ 
/*  456 */       match(125);
/*      */ 
/*  460 */       this.state.type = _type;
/*  461 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__73() throws RecognitionException
/*      */   {
/*      */     try {
/*  471 */       int _type = 73;
/*  472 */       int _channel = 0;
/*      */ 
/*  476 */       match("::");
/*      */ 
/*  481 */       this.state.type = _type;
/*  482 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__74() throws RecognitionException
/*      */   {
/*      */     try {
/*  492 */       int _type = 74;
/*  493 */       int _channel = 0;
/*      */ 
/*  497 */       match(42);
/*      */ 
/*  501 */       this.state.type = _type;
/*  502 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__75() throws RecognitionException
/*      */   {
/*      */     try {
/*  512 */       int _type = 75;
/*  513 */       int _channel = 0;
/*      */ 
/*  517 */       match("protected");
/*      */ 
/*  522 */       this.state.type = _type;
/*  523 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__76() throws RecognitionException
/*      */   {
/*      */     try {
/*  533 */       int _type = 76;
/*  534 */       int _channel = 0;
/*      */ 
/*  538 */       match("public");
/*      */ 
/*  543 */       this.state.type = _type;
/*  544 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__77() throws RecognitionException
/*      */   {
/*      */     try {
/*  554 */       int _type = 77;
/*  555 */       int _channel = 0;
/*      */ 
/*  559 */       match("private");
/*      */ 
/*  564 */       this.state.type = _type;
/*  565 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__78() throws RecognitionException
/*      */   {
/*      */     try {
/*  575 */       int _type = 78;
/*  576 */       int _channel = 0;
/*      */ 
/*  580 */       match(58);
/*      */ 
/*  584 */       this.state.type = _type;
/*  585 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__79() throws RecognitionException
/*      */   {
/*      */     try {
/*  595 */       int _type = 79;
/*  596 */       int _channel = 0;
/*      */ 
/*  600 */       match("throws");
/*      */ 
/*  605 */       this.state.type = _type;
/*  606 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__80() throws RecognitionException
/*      */   {
/*      */     try {
/*  616 */       int _type = 80;
/*  617 */       int _channel = 0;
/*      */ 
/*  621 */       match(44);
/*      */ 
/*  625 */       this.state.type = _type;
/*  626 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__81() throws RecognitionException
/*      */   {
/*      */     try {
/*  636 */       int _type = 81;
/*  637 */       int _channel = 0;
/*      */ 
/*  641 */       match(40);
/*      */ 
/*  645 */       this.state.type = _type;
/*  646 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__82() throws RecognitionException
/*      */   {
/*      */     try {
/*  656 */       int _type = 82;
/*  657 */       int _channel = 0;
/*      */ 
/*  661 */       match(124);
/*      */ 
/*  665 */       this.state.type = _type;
/*  666 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__83() throws RecognitionException
/*      */   {
/*      */     try {
/*  676 */       int _type = 83;
/*  677 */       int _channel = 0;
/*      */ 
/*  681 */       match(41);
/*      */ 
/*  685 */       this.state.type = _type;
/*  686 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__84() throws RecognitionException
/*      */   {
/*      */     try {
/*  696 */       int _type = 84;
/*  697 */       int _channel = 0;
/*      */ 
/*  701 */       match("catch");
/*      */ 
/*  706 */       this.state.type = _type;
/*  707 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__85() throws RecognitionException
/*      */   {
/*      */     try {
/*  717 */       int _type = 85;
/*  718 */       int _channel = 0;
/*      */ 
/*  722 */       match("finally");
/*      */ 
/*  727 */       this.state.type = _type;
/*  728 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__86() throws RecognitionException
/*      */   {
/*      */     try {
/*  738 */       int _type = 86;
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
/*      */   public final void mT__87() throws RecognitionException
/*      */   {
/*      */     try {
/*  759 */       int _type = 87;
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
/*      */   public final void mT__88() throws RecognitionException
/*      */   {
/*      */     try {
/*  779 */       int _type = 88;
/*  780 */       int _channel = 0;
/*      */ 
/*  784 */       match(60);
/*      */ 
/*  788 */       this.state.type = _type;
/*  789 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__89() throws RecognitionException
/*      */   {
/*      */     try {
/*  799 */       int _type = 89;
/*  800 */       int _channel = 0;
/*      */ 
/*  804 */       match(62);
/*      */ 
/*  808 */       this.state.type = _type;
/*  809 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__90() throws RecognitionException
/*      */   {
/*      */     try {
/*  819 */       int _type = 90;
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
/*      */   public final void mT__91() throws RecognitionException
/*      */   {
/*      */     try {
/*  839 */       int _type = 91;
/*  840 */       int _channel = 0;
/*      */ 
/*  844 */       match(63);
/*      */ 
/*  848 */       this.state.type = _type;
/*  849 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__92() throws RecognitionException
/*      */   {
/*      */     try {
/*  859 */       int _type = 92;
/*  860 */       int _channel = 0;
/*      */ 
/*  864 */       match(43);
/*      */ 
/*  868 */       this.state.type = _type;
/*  869 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mT__93() throws RecognitionException
/*      */   {
/*      */     try {
/*  879 */       int _type = 93;
/*  880 */       int _channel = 0;
/*      */ 
/*  884 */       match(36);
/*      */ 
/*  888 */       this.state.type = _type;
/*  889 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mSL_COMMENT() throws RecognitionException
/*      */   {
/*      */     try {
/*  899 */       int _type = 55;
/*  900 */       int _channel = 0;
/*      */ 
/*  904 */       match("//");
/*      */ 
/*  907 */       int alt2 = 2;
/*  908 */       alt2 = this.dfa2.predict(this.input);
/*  909 */       switch (alt2)
/*      */       {
/*      */       case 1:
/*  913 */         match(" $ANTLR ");
/*      */ 
/*  915 */         mSRC();
/*      */ 
/*  918 */         break;
/*      */       case 2:
/*      */         while (true)
/*      */         {
/*  925 */           int alt1 = 2;
/*  926 */           int LA1_0 = this.input.LA(1);
/*      */ 
/*  928 */           if (((LA1_0 >= 0) && (LA1_0 <= 9)) || ((LA1_0 >= 11) && (LA1_0 <= 12)) || ((LA1_0 >= 14) && (LA1_0 <= 65535))) {
/*  929 */             alt1 = 1;
/*      */           }
/*      */ 
/*  933 */           switch (alt1)
/*      */           {
/*      */           case 1:
/*  937 */             if (((this.input.LA(1) >= 0) && (this.input.LA(1) <= 9)) || ((this.input.LA(1) >= 11) && (this.input.LA(1) <= 12)) || ((this.input.LA(1) >= 14) && (this.input.LA(1) <= 65535))) {
/*  938 */               this.input.consume();
/*      */             }
/*      */             else
/*      */             {
/*  942 */               MismatchedSetException mse = new MismatchedSetException(null, this.input);
/*  943 */               recover(mse);
/*  944 */               throw mse;
/*      */             }
/*      */ 
/*      */             break;
/*      */           default:
/*  951 */             break label273;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  962 */       label273: int alt3 = 2;
/*  963 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 13:
/*  966 */         alt3 = 1;
/*      */       }
/*      */ 
/*  971 */       switch (alt3)
/*      */       {
/*      */       case 1:
/*  975 */         match(13);
/*      */       }
/*      */ 
/*  982 */       match(10);
/*  983 */       _channel = 99;
/*      */ 
/*  987 */       this.state.type = _type;
/*  988 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mML_COMMENT() throws RecognitionException
/*      */   {
/*      */     try {
/*  998 */       int _type = 56;
/*  999 */       int _channel = 0;
/*      */ 
/* 1003 */       match("/*");
/*      */ 
/* 1005 */       if (this.input.LA(1) == 42) _type = 4; else _channel = 99;
/*      */ 
/*      */       while (true)
/*      */       {
/* 1009 */         int alt4 = 2;
/* 1010 */         int LA4_0 = this.input.LA(1);
/*      */ 
/* 1012 */         if (LA4_0 == 42) {
/* 1013 */           int LA4_1 = this.input.LA(2);
/*      */ 
/* 1015 */           if (LA4_1 == 47) {
/* 1016 */             alt4 = 2;
/*      */           }
/* 1018 */           else if (((LA4_1 >= 0) && (LA4_1 <= 46)) || ((LA4_1 >= 48) && (LA4_1 <= 65535))) {
/* 1019 */             alt4 = 1;
/*      */           }
/*      */ 
/*      */         }
/* 1024 */         else if (((LA4_0 >= 0) && (LA4_0 <= 41)) || ((LA4_0 >= 43) && (LA4_0 <= 65535))) {
/* 1025 */           alt4 = 1;
/*      */         }
/*      */ 
/* 1029 */         switch (alt4)
/*      */         {
/*      */         case 1:
/* 1033 */           matchAny();
/*      */ 
/* 1036 */           break;
/*      */         default:
/* 1039 */           break label169;
/*      */         }
/*      */       }
/*      */ 
/* 1043 */       label169: match("*/");
/*      */ 
/* 1048 */       this.state.type = _type;
/* 1049 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mCHAR_LITERAL() throws RecognitionException
/*      */   {
/*      */     try {
/* 1059 */       int _type = 46;
/* 1060 */       int _channel = 0;
/*      */ 
/* 1064 */       match(39);
/* 1065 */       mLITERAL_CHAR();
/* 1066 */       match(39);
/*      */ 
/* 1070 */       this.state.type = _type;
/* 1071 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mSTRING_LITERAL() throws RecognitionException
/*      */   {
/*      */     try {
/* 1081 */       int _type = 45;
/* 1082 */       int _channel = 0;
/*      */ 
/* 1086 */       match(39);
/* 1087 */       mLITERAL_CHAR();
/*      */       while (true)
/*      */       {
/* 1091 */         int alt5 = 2;
/* 1092 */         int LA5_0 = this.input.LA(1);
/*      */ 
/* 1094 */         if (((LA5_0 >= 0) && (LA5_0 <= 38)) || ((LA5_0 >= 40) && (LA5_0 <= 65535))) {
/* 1095 */           alt5 = 1;
/*      */         }
/*      */ 
/* 1099 */         switch (alt5)
/*      */         {
/*      */         case 1:
/* 1103 */           mLITERAL_CHAR();
/*      */ 
/* 1106 */           break;
/*      */         default:
/* 1109 */           break label89;
/*      */         }
/*      */       }
/*      */ 
/* 1113 */       label89: match(39);
/*      */ 
/* 1117 */       this.state.type = _type;
/* 1118 */       this.state.channel = _channel;
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
/* 1129 */       int alt6 = 2;
/* 1130 */       int LA6_0 = this.input.LA(1);
/*      */ 
/* 1132 */       if (LA6_0 == 92) {
/* 1133 */         alt6 = 1;
/*      */       }
/* 1135 */       else if (((LA6_0 >= 0) && (LA6_0 <= 38)) || ((LA6_0 >= 40) && (LA6_0 <= 91)) || ((LA6_0 >= 93) && (LA6_0 <= 65535))) {
/* 1136 */         alt6 = 2;
/*      */       }
/*      */       else {
/* 1139 */         NoViableAltException nvae = new NoViableAltException("", 6, 0, this.input);
/*      */ 
/* 1142 */         throw nvae;
/*      */       }
/* 1144 */       switch (alt6)
/*      */       {
/*      */       case 1:
/* 1148 */         mESC();
/*      */ 
/* 1151 */         break;
/*      */       case 2:
/* 1155 */         if (((this.input.LA(1) >= 0) && (this.input.LA(1) <= 38)) || ((this.input.LA(1) >= 40) && (this.input.LA(1) <= 91)) || ((this.input.LA(1) >= 93) && (this.input.LA(1) <= 65535))) {
/* 1156 */           this.input.consume();
/*      */         }
/*      */         else
/*      */         {
/* 1160 */           MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 1161 */           recover(mse);
/* 1162 */           throw mse;
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
/* 1178 */       int _type = 52;
/* 1179 */       int _channel = 0;
/*      */ 
/* 1183 */       match(34);
/*      */       while (true)
/*      */       {
/* 1187 */         int alt7 = 3;
/* 1188 */         int LA7_0 = this.input.LA(1);
/*      */ 
/* 1190 */         if (LA7_0 == 92) {
/* 1191 */           alt7 = 1;
/*      */         }
/* 1193 */         else if (((LA7_0 >= 0) && (LA7_0 <= 33)) || ((LA7_0 >= 35) && (LA7_0 <= 91)) || ((LA7_0 >= 93) && (LA7_0 <= 65535))) {
/* 1194 */           alt7 = 2;
/*      */         }
/*      */ 
/* 1198 */         switch (alt7)
/*      */         {
/*      */         case 1:
/* 1202 */           mESC();
/*      */ 
/* 1205 */           break;
/*      */         case 2:
/* 1209 */           if (((this.input.LA(1) >= 0) && (this.input.LA(1) <= 33)) || ((this.input.LA(1) >= 35) && (this.input.LA(1) <= 91)) || ((this.input.LA(1) >= 93) && (this.input.LA(1) <= 65535))) {
/* 1210 */             this.input.consume();
/*      */           }
/*      */           else
/*      */           {
/* 1214 */             MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 1215 */             recover(mse);
/* 1216 */             throw mse;
/*      */           }
/*      */ 
/*      */           break;
/*      */         default:
/* 1223 */           break label244;
/*      */         }
/*      */       }
/*      */ 
/* 1227 */       label244: match(34);
/*      */ 
/* 1231 */       this.state.type = _type;
/* 1232 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mDOUBLE_ANGLE_STRING_LITERAL() throws RecognitionException
/*      */   {
/*      */     try {
/* 1242 */       int _type = 53;
/* 1243 */       int _channel = 0;
/*      */ 
/* 1247 */       match("<<");
/*      */       while (true)
/*      */       {
/* 1252 */         int alt8 = 2;
/* 1253 */         int LA8_0 = this.input.LA(1);
/*      */ 
/* 1255 */         if (LA8_0 == 62) {
/* 1256 */           int LA8_1 = this.input.LA(2);
/*      */ 
/* 1258 */           if (LA8_1 == 62) {
/* 1259 */             alt8 = 2;
/*      */           }
/* 1261 */           else if (((LA8_1 >= 0) && (LA8_1 <= 61)) || ((LA8_1 >= 63) && (LA8_1 <= 65535))) {
/* 1262 */             alt8 = 1;
/*      */           }
/*      */ 
/*      */         }
/* 1267 */         else if (((LA8_0 >= 0) && (LA8_0 <= 61)) || ((LA8_0 >= 63) && (LA8_0 <= 65535))) {
/* 1268 */           alt8 = 1;
/*      */         }
/*      */ 
/* 1272 */         switch (alt8)
/*      */         {
/*      */         case 1:
/* 1276 */           matchAny();
/*      */ 
/* 1279 */           break;
/*      */         default:
/* 1282 */           break label149;
/*      */         }
/*      */       }
/*      */ 
/* 1286 */       label149: match(">>");
/*      */ 
/* 1291 */       this.state.type = _type;
/* 1292 */       this.state.channel = _channel;
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
/* 1305 */       match(92);
/*      */ 
/* 1307 */       int alt9 = 11;
/* 1308 */       alt9 = this.dfa9.predict(this.input);
/* 1309 */       switch (alt9)
/*      */       {
/*      */       case 1:
/* 1313 */         match(110);
/*      */ 
/* 1316 */         break;
/*      */       case 2:
/* 1320 */         match(114);
/*      */ 
/* 1323 */         break;
/*      */       case 3:
/* 1327 */         match(116);
/*      */ 
/* 1330 */         break;
/*      */       case 4:
/* 1334 */         match(98);
/*      */ 
/* 1337 */         break;
/*      */       case 5:
/* 1341 */         match(102);
/*      */ 
/* 1344 */         break;
/*      */       case 6:
/* 1348 */         match(34);
/*      */ 
/* 1351 */         break;
/*      */       case 7:
/* 1355 */         match(39);
/*      */ 
/* 1358 */         break;
/*      */       case 8:
/* 1362 */         match(92);
/*      */ 
/* 1365 */         break;
/*      */       case 9:
/* 1369 */         match(62);
/*      */ 
/* 1372 */         break;
/*      */       case 10:
/* 1376 */         match(117);
/* 1377 */         mXDIGIT();
/* 1378 */         mXDIGIT();
/* 1379 */         mXDIGIT();
/* 1380 */         mXDIGIT();
/*      */ 
/* 1383 */         break;
/*      */       case 11:
/* 1387 */         matchAny();
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
/* 1409 */       if (((this.input.LA(1) >= 48) && (this.input.LA(1) <= 57)) || ((this.input.LA(1) >= 65) && (this.input.LA(1) <= 70)) || ((this.input.LA(1) >= 97) && (this.input.LA(1) <= 102))) {
/* 1410 */         this.input.consume();
/*      */       }
/*      */       else
/*      */       {
/* 1414 */         MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 1415 */         recover(mse);
/* 1416 */         throw mse;
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
/* 1430 */       int _type = 49;
/* 1431 */       int _channel = 0;
/*      */ 
/* 1436 */       int cnt10 = 0;
/*      */       while (true)
/*      */       {
/* 1439 */         int alt10 = 2;
/* 1440 */         switch (this.input.LA(1))
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
/* 1452 */           alt10 = 1;
/*      */         }
/*      */ 
/* 1458 */         switch (alt10)
/*      */         {
/*      */         case 1:
/* 1462 */           matchRange(48, 57);
/*      */ 
/* 1465 */           break;
/*      */         default:
/* 1468 */           if (cnt10 >= 1) break label143;
/* 1469 */           EarlyExitException eee = new EarlyExitException(10, this.input);
/*      */ 
/* 1471 */           throw eee;
/*      */         }
/* 1473 */         cnt10++;
/*      */       }
/*      */ 
/* 1479 */       label143: this.state.type = _type;
/* 1480 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mARG_ACTION() throws RecognitionException
/*      */   {
/*      */     try {
/* 1490 */       int _type = 50;
/* 1491 */       int _channel = 0;
/*      */ 
/* 1495 */       mNESTED_ARG_ACTION();
/*      */ 
/* 1499 */       this.state.type = _type;
/* 1500 */       this.state.channel = _channel;
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
/* 1513 */       match(91);
/*      */       while (true)
/*      */       {
/* 1517 */         int alt11 = 5;
/* 1518 */         int LA11_0 = this.input.LA(1);
/*      */ 
/* 1520 */         if (LA11_0 == 93) {
/* 1521 */           alt11 = 5;
/*      */         }
/* 1523 */         else if (LA11_0 == 91) {
/* 1524 */           alt11 = 1;
/*      */         }
/* 1526 */         else if (LA11_0 == 34) {
/* 1527 */           alt11 = 2;
/*      */         }
/* 1529 */         else if (LA11_0 == 39) {
/* 1530 */           alt11 = 3;
/*      */         }
/* 1532 */         else if (((LA11_0 >= 0) && (LA11_0 <= 33)) || ((LA11_0 >= 35) && (LA11_0 <= 38)) || ((LA11_0 >= 40) && (LA11_0 <= 90)) || (LA11_0 == 92) || ((LA11_0 >= 94) && (LA11_0 <= 65535))) {
/* 1533 */           alt11 = 4;
/*      */         }
/*      */ 
/* 1537 */         switch (alt11)
/*      */         {
/*      */         case 1:
/* 1541 */           mNESTED_ARG_ACTION();
/*      */ 
/* 1544 */           break;
/*      */         case 2:
/* 1548 */           mACTION_STRING_LITERAL();
/*      */ 
/* 1551 */           break;
/*      */         case 3:
/* 1555 */           mACTION_CHAR_LITERAL();
/*      */ 
/* 1558 */           break;
/*      */         case 4:
/* 1562 */           matchAny();
/*      */ 
/* 1565 */           break;
/*      */         default:
/* 1568 */           break label182;
/*      */         }
/*      */       }
/*      */ 
/* 1572 */       label182: match(93);
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
/* 1585 */       int _type = 47;
/* 1586 */       int _channel = 0;
/*      */ 
/* 1590 */       mNESTED_ACTION();
/*      */ 
/* 1592 */       int alt12 = 2;
/* 1593 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 63:
/* 1596 */         alt12 = 1;
/*      */       }
/*      */ 
/* 1601 */       switch (alt12)
/*      */       {
/*      */       case 1:
/* 1605 */         match(63);
/* 1606 */         _type = 31;
/*      */       }
/*      */ 
/* 1616 */       this.state.type = _type;
/* 1617 */       this.state.channel = _channel;
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
/* 1630 */       match(123);
/*      */       while (true)
/*      */       {
/* 1634 */         int alt13 = 7;
/* 1635 */         alt13 = this.dfa13.predict(this.input);
/* 1636 */         switch (alt13)
/*      */         {
/*      */         case 1:
/* 1640 */           mNESTED_ACTION();
/*      */ 
/* 1643 */           break;
/*      */         case 2:
/* 1647 */           mSL_COMMENT();
/*      */ 
/* 1650 */           break;
/*      */         case 3:
/* 1654 */           mML_COMMENT();
/*      */ 
/* 1657 */           break;
/*      */         case 4:
/* 1661 */           mACTION_STRING_LITERAL();
/*      */ 
/* 1664 */           break;
/*      */         case 5:
/* 1668 */           mACTION_CHAR_LITERAL();
/*      */ 
/* 1671 */           break;
/*      */         case 6:
/* 1675 */           matchAny();
/*      */ 
/* 1678 */           break;
/*      */         default:
/* 1681 */           break label108;
/*      */         }
/*      */       }
/*      */ 
/* 1685 */       label108: match(125);
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
/* 1701 */       match(39);
/*      */ 
/* 1703 */       int alt14 = 2;
/* 1704 */       int LA14_0 = this.input.LA(1);
/*      */ 
/* 1706 */       if (LA14_0 == 92) {
/* 1707 */         alt14 = 1;
/*      */       }
/* 1709 */       else if (((LA14_0 >= 0) && (LA14_0 <= 38)) || ((LA14_0 >= 40) && (LA14_0 <= 91)) || ((LA14_0 >= 93) && (LA14_0 <= 65535))) {
/* 1710 */         alt14 = 2;
/*      */       }
/*      */       else {
/* 1713 */         NoViableAltException nvae = new NoViableAltException("", 14, 0, this.input);
/*      */ 
/* 1716 */         throw nvae;
/*      */       }
/* 1718 */       switch (alt14)
/*      */       {
/*      */       case 1:
/* 1722 */         mACTION_ESC();
/*      */ 
/* 1725 */         break;
/*      */       case 2:
/* 1729 */         if (((this.input.LA(1) >= 0) && (this.input.LA(1) <= 38)) || ((this.input.LA(1) >= 40) && (this.input.LA(1) <= 91)) || ((this.input.LA(1) >= 93) && (this.input.LA(1) <= 65535))) {
/* 1730 */           this.input.consume();
/*      */         }
/*      */         else
/*      */         {
/* 1734 */           MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 1735 */           recover(mse);
/* 1736 */           throw mse;
/*      */         }
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 1744 */       match(39);
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
/* 1760 */       match(34);
/*      */       while (true)
/*      */       {
/* 1764 */         int alt15 = 3;
/* 1765 */         int LA15_0 = this.input.LA(1);
/*      */ 
/* 1767 */         if (LA15_0 == 92) {
/* 1768 */           alt15 = 1;
/*      */         }
/* 1770 */         else if (((LA15_0 >= 0) && (LA15_0 <= 33)) || ((LA15_0 >= 35) && (LA15_0 <= 91)) || ((LA15_0 >= 93) && (LA15_0 <= 65535))) {
/* 1771 */           alt15 = 2;
/*      */         }
/*      */ 
/* 1775 */         switch (alt15)
/*      */         {
/*      */         case 1:
/* 1779 */           mACTION_ESC();
/*      */ 
/* 1782 */           break;
/*      */         case 2:
/* 1786 */           if (((this.input.LA(1) >= 0) && (this.input.LA(1) <= 33)) || ((this.input.LA(1) >= 35) && (this.input.LA(1) <= 91)) || ((this.input.LA(1) >= 93) && (this.input.LA(1) <= 65535))) {
/* 1787 */             this.input.consume();
/*      */           }
/*      */           else
/*      */           {
/* 1791 */             MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 1792 */             recover(mse);
/* 1793 */             throw mse;
/*      */           }
/*      */ 
/*      */           break;
/*      */         default:
/* 1800 */           break label225;
/*      */         }
/*      */       }
/*      */ 
/* 1804 */       label225: match(34);
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
/* 1818 */       int alt16 = 3;
/* 1819 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 92:
/* 1822 */         int LA16_1 = this.input.LA(2);
/*      */ 
/* 1824 */         if (LA16_1 == 39) {
/* 1825 */           alt16 = 1;
/*      */         }
/* 1827 */         else if (LA16_1 == 34) {
/* 1828 */           alt16 = 2;
/*      */         }
/* 1830 */         else if (((LA16_1 >= 0) && (LA16_1 <= 33)) || ((LA16_1 >= 35) && (LA16_1 <= 38)) || ((LA16_1 >= 40) && (LA16_1 <= 65535))) {
/* 1831 */           alt16 = 3;
/*      */         }
/*      */         else {
/* 1834 */           NoViableAltException nvae = new NoViableAltException("", 16, 1, this.input);
/*      */ 
/* 1837 */           throw nvae;
/*      */         }
/*      */ 
/* 1840 */         break;
/*      */       default:
/* 1842 */         NoViableAltException nvae = new NoViableAltException("", 16, 0, this.input);
/*      */ 
/* 1845 */         throw nvae;
/*      */       }
/*      */ 
/* 1848 */       switch (alt16)
/*      */       {
/*      */       case 1:
/* 1852 */         match("\\'");
/*      */ 
/* 1856 */         break;
/*      */       case 2:
/* 1860 */         match(92);
/* 1861 */         match(34);
/*      */ 
/* 1864 */         break;
/*      */       case 3:
/* 1868 */         match(92);
/* 1869 */         if (((this.input.LA(1) >= 0) && (this.input.LA(1) <= 33)) || ((this.input.LA(1) >= 35) && (this.input.LA(1) <= 38)) || ((this.input.LA(1) >= 40) && (this.input.LA(1) <= 65535))) {
/* 1870 */           this.input.consume();
/*      */         }
/*      */         else
/*      */         {
/* 1874 */           MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 1875 */           recover(mse);
/* 1876 */           throw mse;
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
/* 1892 */       int _type = 44;
/* 1893 */       int _channel = 0;
/*      */ 
/* 1897 */       matchRange(65, 90);
/*      */       while (true)
/*      */       {
/* 1901 */         int alt17 = 2;
/* 1902 */         switch (this.input.LA(1))
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
/* 1967 */           alt17 = 1;
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
/* 1973 */         case 96: } switch (alt17)
/*      */         {
/*      */         case 1:
/* 1977 */           if (((this.input.LA(1) >= 48) && (this.input.LA(1) <= 57)) || ((this.input.LA(1) >= 65) && (this.input.LA(1) <= 90)) || (this.input.LA(1) == 95) || ((this.input.LA(1) >= 97) && (this.input.LA(1) <= 122))) {
/* 1978 */             this.input.consume();
/*      */           }
/*      */           else
/*      */           {
/* 1982 */             MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 1983 */             recover(mse);
/* 1984 */             throw mse;
/*      */           }
/*      */ 
/*      */           break;
/*      */         default:
/* 1991 */           break label506;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1998 */       label506: this.state.type = _type;
/* 1999 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mRULE_REF() throws RecognitionException
/*      */   {
/*      */     try {
/* 2009 */       int _type = 51;
/* 2010 */       int _channel = 0;
/*      */ 
/* 2014 */       matchRange(97, 122);
/*      */       while (true)
/*      */       {
/* 2018 */         int alt18 = 2;
/* 2019 */         switch (this.input.LA(1))
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
/* 2084 */           alt18 = 1;
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
/* 2090 */         case 96: } switch (alt18)
/*      */         {
/*      */         case 1:
/* 2094 */           if (((this.input.LA(1) >= 48) && (this.input.LA(1) <= 57)) || ((this.input.LA(1) >= 65) && (this.input.LA(1) <= 90)) || (this.input.LA(1) == 95) || ((this.input.LA(1) >= 97) && (this.input.LA(1) <= 122))) {
/* 2095 */             this.input.consume();
/*      */           }
/*      */           else
/*      */           {
/* 2099 */             MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 2100 */             recover(mse);
/* 2101 */             throw mse;
/*      */           }
/*      */ 
/*      */           break;
/*      */         default:
/* 2108 */           break label506;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2115 */       label506: this.state.type = _type;
/* 2116 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mOPTIONS() throws RecognitionException
/*      */   {
/*      */     try {
/* 2126 */       int _type = 48;
/* 2127 */       int _channel = 0;
/*      */ 
/* 2131 */       match("options");
/*      */ 
/* 2133 */       mWS_LOOP();
/* 2134 */       match(123);
/*      */ 
/* 2138 */       this.state.type = _type;
/* 2139 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mTOKENS() throws RecognitionException
/*      */   {
/*      */     try {
/* 2149 */       int _type = 43;
/* 2150 */       int _channel = 0;
/*      */ 
/* 2154 */       match("tokens");
/*      */ 
/* 2156 */       mWS_LOOP();
/* 2157 */       match(123);
/*      */ 
/* 2161 */       this.state.type = _type;
/* 2162 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mSRC() throws RecognitionException
/*      */   {
/*      */     try {
/* 2172 */       CommonToken file = null;
/* 2173 */       CommonToken line = null;
/*      */ 
/* 2178 */       match("src");
/*      */ 
/* 2180 */       match(32);
/* 2181 */       int fileStart997 = getCharIndex();
/* 2182 */       mACTION_STRING_LITERAL();
/* 2183 */       file = new CommonToken(this.input, 0, 0, fileStart997, getCharIndex() - 1);
/* 2184 */       match(32);
/* 2185 */       int lineStart1003 = getCharIndex();
/* 2186 */       mINT();
/* 2187 */       line = new CommonToken(this.input, 0, 0, lineStart1003, getCharIndex() - 1);
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
/* 2200 */       int _type = 66;
/* 2201 */       int _channel = 0;
/*      */ 
/* 2206 */       int cnt20 = 0;
/*      */       while (true)
/*      */       {
/* 2209 */         int alt20 = 4;
/* 2210 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 32:
/* 2213 */           alt20 = 1;
/*      */ 
/* 2215 */           break;
/*      */         case 9:
/* 2218 */           alt20 = 2;
/*      */ 
/* 2220 */           break;
/*      */         case 10:
/*      */         case 13:
/* 2224 */           alt20 = 3;
/*      */         }
/*      */ 
/* 2230 */         switch (alt20)
/*      */         {
/*      */         case 1:
/* 2234 */           match(32);
/*      */ 
/* 2237 */           break;
/*      */         case 2:
/* 2241 */           match(9);
/*      */ 
/* 2244 */           break;
/*      */         case 3:
/* 2249 */           int alt19 = 2;
/* 2250 */           switch (this.input.LA(1))
/*      */           {
/*      */           case 13:
/* 2253 */             alt19 = 1;
/*      */           }
/*      */ 
/* 2258 */           switch (alt19)
/*      */           {
/*      */           case 1:
/* 2262 */             match(13);
/*      */           }
/*      */ 
/* 2269 */           match(10);
/*      */ 
/* 2272 */           break;
/*      */         default:
/* 2275 */           if (cnt20 >= 1) break label227;
/* 2276 */           EarlyExitException eee = new EarlyExitException(20, this.input);
/*      */ 
/* 2278 */           throw eee;
/*      */         }
/* 2280 */         cnt20++;
/*      */       }
/*      */ 
/* 2283 */       label227: _channel = 99;
/*      */ 
/* 2287 */       this.state.type = _type;
/* 2288 */       this.state.channel = _channel;
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
/* 2304 */         int alt21 = 4;
/* 2305 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 9:
/*      */         case 10:
/*      */         case 13:
/*      */         case 32:
/* 2311 */           alt21 = 1;
/*      */ 
/* 2313 */           break;
/*      */         case 47:
/* 2316 */           switch (this.input.LA(2))
/*      */           {
/*      */           case 47:
/* 2319 */             alt21 = 2;
/*      */ 
/* 2321 */             break;
/*      */           case 42:
/* 2324 */             alt21 = 3;
/*      */           }
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 2335 */         switch (alt21)
/*      */         {
/*      */         case 1:
/* 2339 */           mWS();
/*      */ 
/* 2342 */           break;
/*      */         case 2:
/* 2346 */           mSL_COMMENT();
/*      */ 
/* 2349 */           break;
/*      */         case 3:
/* 2353 */           mML_COMMENT();
/*      */ 
/* 2356 */           break;
/*      */         default:
/* 2359 */           return;
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
/* 2374 */     int alt22 = 52;
/* 2375 */     alt22 = this.dfa22.predict(this.input);
/* 2376 */     switch (alt22)
/*      */     {
/*      */     case 1:
/* 2380 */       mRET();
/*      */ 
/* 2383 */       break;
/*      */     case 2:
/* 2387 */       mSCOPE();
/*      */ 
/* 2390 */       break;
/*      */     case 3:
/* 2394 */       mFRAGMENT();
/*      */ 
/* 2397 */       break;
/*      */     case 4:
/* 2401 */       mTREE_BEGIN();
/*      */ 
/* 2404 */       break;
/*      */     case 5:
/* 2408 */       mROOT();
/*      */ 
/* 2411 */       break;
/*      */     case 6:
/* 2415 */       mBANG();
/*      */ 
/* 2418 */       break;
/*      */     case 7:
/* 2422 */       mRANGE();
/*      */ 
/* 2425 */       break;
/*      */     case 8:
/* 2429 */       mREWRITE();
/*      */ 
/* 2432 */       break;
/*      */     case 9:
/* 2436 */       mAT();
/*      */ 
/* 2439 */       break;
/*      */     case 10:
/* 2443 */       mLABEL_ASSIGN();
/*      */ 
/* 2446 */       break;
/*      */     case 11:
/* 2450 */       mLIST_LABEL_ASSIGN();
/*      */ 
/* 2453 */       break;
/*      */     case 12:
/* 2457 */       mT__67();
/*      */ 
/* 2460 */       break;
/*      */     case 13:
/* 2464 */       mT__68();
/*      */ 
/* 2467 */       break;
/*      */     case 14:
/* 2471 */       mT__69();
/*      */ 
/* 2474 */       break;
/*      */     case 15:
/* 2478 */       mT__70();
/*      */ 
/* 2481 */       break;
/*      */     case 16:
/* 2485 */       mT__71();
/*      */ 
/* 2488 */       break;
/*      */     case 17:
/* 2492 */       mT__72();
/*      */ 
/* 2495 */       break;
/*      */     case 18:
/* 2499 */       mT__73();
/*      */ 
/* 2502 */       break;
/*      */     case 19:
/* 2506 */       mT__74();
/*      */ 
/* 2509 */       break;
/*      */     case 20:
/* 2513 */       mT__75();
/*      */ 
/* 2516 */       break;
/*      */     case 21:
/* 2520 */       mT__76();
/*      */ 
/* 2523 */       break;
/*      */     case 22:
/* 2527 */       mT__77();
/*      */ 
/* 2530 */       break;
/*      */     case 23:
/* 2534 */       mT__78();
/*      */ 
/* 2537 */       break;
/*      */     case 24:
/* 2541 */       mT__79();
/*      */ 
/* 2544 */       break;
/*      */     case 25:
/* 2548 */       mT__80();
/*      */ 
/* 2551 */       break;
/*      */     case 26:
/* 2555 */       mT__81();
/*      */ 
/* 2558 */       break;
/*      */     case 27:
/* 2562 */       mT__82();
/*      */ 
/* 2565 */       break;
/*      */     case 28:
/* 2569 */       mT__83();
/*      */ 
/* 2572 */       break;
/*      */     case 29:
/* 2576 */       mT__84();
/*      */ 
/* 2579 */       break;
/*      */     case 30:
/* 2583 */       mT__85();
/*      */ 
/* 2586 */       break;
/*      */     case 31:
/* 2590 */       mT__86();
/*      */ 
/* 2593 */       break;
/*      */     case 32:
/* 2597 */       mT__87();
/*      */ 
/* 2600 */       break;
/*      */     case 33:
/* 2604 */       mT__88();
/*      */ 
/* 2607 */       break;
/*      */     case 34:
/* 2611 */       mT__89();
/*      */ 
/* 2614 */       break;
/*      */     case 35:
/* 2618 */       mT__90();
/*      */ 
/* 2621 */       break;
/*      */     case 36:
/* 2625 */       mT__91();
/*      */ 
/* 2628 */       break;
/*      */     case 37:
/* 2632 */       mT__92();
/*      */ 
/* 2635 */       break;
/*      */     case 38:
/* 2639 */       mT__93();
/*      */ 
/* 2642 */       break;
/*      */     case 39:
/* 2646 */       mSL_COMMENT();
/*      */ 
/* 2649 */       break;
/*      */     case 40:
/* 2653 */       mML_COMMENT();
/*      */ 
/* 2656 */       break;
/*      */     case 41:
/* 2660 */       mCHAR_LITERAL();
/*      */ 
/* 2663 */       break;
/*      */     case 42:
/* 2667 */       mSTRING_LITERAL();
/*      */ 
/* 2670 */       break;
/*      */     case 43:
/* 2674 */       mDOUBLE_QUOTE_STRING_LITERAL();
/*      */ 
/* 2677 */       break;
/*      */     case 44:
/* 2681 */       mDOUBLE_ANGLE_STRING_LITERAL();
/*      */ 
/* 2684 */       break;
/*      */     case 45:
/* 2688 */       mINT();
/*      */ 
/* 2691 */       break;
/*      */     case 46:
/* 2695 */       mARG_ACTION();
/*      */ 
/* 2698 */       break;
/*      */     case 47:
/* 2702 */       mACTION();
/*      */ 
/* 2705 */       break;
/*      */     case 48:
/* 2709 */       mTOKEN_REF();
/*      */ 
/* 2712 */       break;
/*      */     case 49:
/* 2716 */       mRULE_REF();
/*      */ 
/* 2719 */       break;
/*      */     case 50:
/* 2723 */       mOPTIONS();
/*      */ 
/* 2726 */       break;
/*      */     case 51:
/* 2730 */       mTOKENS();
/*      */ 
/* 2733 */       break;
/*      */     case 52:
/* 2737 */       mWS();
/*      */     }
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/* 2805 */     int numStates = DFA2_transitionS.length;
/* 2806 */     DFA2_transition = new short[numStates][];
/* 2807 */     for (int i = 0; i < numStates; i++) {
/* 2808 */       DFA2_transition[i] = DFA.unpackEncodedString(DFA2_transitionS[i]);
/*      */     }
/*      */ 
/* 3137 */     DFA9_transitionS = new String[] { "\"\013\001\006\004\013\001\007\026\013\001\t\035\013\001\b\005\013\001\004\003\013\001\005\007\013\001\001\003\013\001\002\001\013\001\003\001\nï¾Š\013", "", "", "", "", "", "", "", "", "", "\n\f\007ğ¿¿\006\f\032ğ¿¿\006\f", "", "" };
/*      */ 
/* 3154 */     DFA9_eot = DFA.unpackEncodedString("\nğ¿¿\001\013\002ğ¿¿");
/* 3155 */     DFA9_eof = DFA.unpackEncodedString("\rğ¿¿");
/* 3156 */     DFA9_min = DFA.unpackEncodedStringToUnsignedChars("");
/* 3157 */     DFA9_max = DFA.unpackEncodedStringToUnsignedChars("\001ğ¿¿\tğ¿¿\001f\002ğ¿¿");
/* 3158 */     DFA9_accept = DFA.unpackEncodedString("\001ğ¿¿\001\001\001\002\001\003\001\004\001\005\001\006\001\007\001\b\001\t\001ğ¿¿\001\013\001\n");
/* 3159 */     DFA9_special = DFA.unpackEncodedString("");
/*      */ 
/* 3163 */     int numStates = DFA9_transitionS.length;
/* 3164 */     DFA9_transition = new short[numStates][];
/* 3165 */     for (int i = 0; i < numStates; i++) {
/* 3166 */       DFA9_transition[i] = DFA.unpackEncodedString(DFA9_transitionS[i]);
/*      */     }
/*      */ 
/* 3237 */     DFA13_transitionS = new String[] { "\"\006\001\004\004\006\001\005\007\006\001\003K\006\001\002\001\006\001\001ï¾‚\006", "", "", "*\006\001\b\004\006\001\007ï¿\006", "\"\024\001\021\004\024\001\022\007\024\001\020,\024\001\023\036\024\001\017\001\024\001\016ï¾‚\024", "\"\032\001\031\004\032\001\006\007\032\001\030,\032\001\025\036\032\001\027\001\032\001\026ï¾‚\032", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" };
/*      */ 
/* 3270 */     DFA13_eot = DFA.unpackEncodedString("\034ğ¿¿");
/* 3271 */     DFA13_eof = DFA.unpackEncodedString("\034ğ¿¿");
/* 3272 */     DFA13_min = DFA.unpackEncodedStringToUnsignedChars("");
/* 3273 */     DFA13_max = DFA.unpackEncodedStringToUnsignedChars("\001ğ¿¿\002ğ¿¿\003ğ¿¿\026ğ¿¿");
/* 3274 */     DFA13_accept = DFA.unpackEncodedString("\001ğ¿¿\001\007\001\001\003ğ¿¿\001\006\001\002\001\003\005ğ¿¿\007\004\006\005\001ğ¿¿");
/* 3275 */     DFA13_special = DFA.unpackEncodedString("");
/*      */ 
/* 3279 */     int numStates = DFA13_transitionS.length;
/* 3280 */     DFA13_transition = new short[numStates][];
/* 3281 */     for (int i = 0; i < numStates; i++) {
/* 3282 */       DFA13_transition[i] = DFA.unpackEncodedString(DFA13_transitionS[i]);
/*      */     }
/*      */ 
/* 3432 */     DFA22_transitionS = new String[] { "\002&\002ğ¿¿\001&\022ğ¿¿\001&\001\005\001\037\001ğ¿¿\001\034\002ğ¿¿\001\036\001\024\001\026\001\022\001\n\001\023\001\007\001\006\001\035\n \001\021\001\017\001\031\001\t\001\032\001\033\001\b\032#\001!\002ğ¿¿\001\004\002ğ¿¿\002%\001\027\002%\001\003\001\016\004%\001\013\002%\001$\001\f\001%\001\001\001\002\001\r\006%\001\"\001\025\001\020\001\030", "\001'", "\001(", "\001*\bğ¿¿\001)", "\001+", "", "\001-", "", "", "\001/", "\0011", "\0013", "\0014\020ğ¿¿\0015\002ğ¿¿\0016", "\0018\006ğ¿¿\0019\002ğ¿¿\0017", "\001:", "", "", "\001;", "", "", "", "", "", "\001=", "", "\001>", "", "", "", "\001A\004ğ¿¿\001@", "'C\001ğ¿¿4C\001Bï¾£C", "", "", "", "", "", "\001D", "", "", "\001E", "\001F", "\001G", "\001H", "", "", "", "", "", "", "", "", "\001I", "\001J", "\001L\005ğ¿¿\001K", "\001M", "\001N", "\001O", "\001P", "\001Q", "", "", "\001R", "", "", "", "", "\"]\001X\004]\001Y\026]\001[\035]\001Z\005]\001V\003]\001W\007]\001S\003]\001T\001]\001U\001\\ï¾Š]", "'^\001_ï¿˜^", "\001`", "\001a", "\001b", "\001c", "\001d", "\001e", "\001f", "\001g", "\001h", "\001i", "\001j", "\001k", "\001l", "\001m", "\001n", "'^\001_ï¿˜^", "'^\001_ï¿˜^", "'^\001_ï¿˜^", "'^\001_ï¿˜^", "'^\001_ï¿˜^", "'^\001_ï¿˜^", "'^\001_ï¿˜^", "'^\001_ï¿˜^", "'^\001_ï¿˜^", "'^\001_\b^\no\007^\006o\032^\006oï¾™^", "'^\001_ï¿˜^", "", "", "\001q", "\001r", "\001s", "\001t", "\001u", "\001v", "\001w", "\001x", "\001y", "\001z", "\n%\007ğ¿¿\032%\004ğ¿¿\001%\001ğ¿¿\032%", "\001|", "\001}", "\001~", "\001", "0^\nÂ€\007^\006Â€\032^\006Â€ï¾™^", "", "\001Â", "\001Â‚", "\n%\007ğ¿¿\032%\004ğ¿¿\001%\001ğ¿¿\032%", "\001Â„", "\001Â…", "\n%\007ğ¿¿\032%\004ğ¿¿\001%\001ğ¿¿\032%", "\001Â‡", "\001Âˆ", "\001Â‰", "\001ÂŠ", "", "\001Â‹", "\001ÂŒ", "\001Â", "\n%\007ğ¿¿\032%\004ğ¿¿\001%\001ğ¿¿\032%", "0^\nÂ\007^\006Â\032^\006Âï¾™^", "\001Â", "\001Â‘", "", "\001Â’", "\001Â“", "", "\n%\007ğ¿¿\032%\004ğ¿¿\001%\001ğ¿¿\032%", "\001Â•", "\001Â–", "\n%\007ğ¿¿\032%\004ğ¿¿\001%\001ğ¿¿\032%", "\n%\007ğ¿¿\032%\004ğ¿¿\001%\001ğ¿¿\032%", "\002Â™\002ğ¿¿\001Â™\022ğ¿¿\001Â™\016ğ¿¿\001Â™Kğ¿¿\001Â™", "\001Âš", "", "0^\nÂ›\007^\006Â›\032^\006Â›ï¾™^", "\001Âœ", "\n%\007ğ¿¿\032%\004ğ¿¿\001%\001ğ¿¿\032%", "\001Â", "\n%\007ğ¿¿\032%\004ğ¿¿\001%\001ğ¿¿\032%", "", "\001Â ", "\n%\007ğ¿¿\032%\004ğ¿¿\001%\001ğ¿¿\032%", "", "", "", "\n%\007ğ¿¿\032%\004ğ¿¿\001%\001ğ¿¿\032%", "'^\001_ï¿˜^", "\002Â£\002ğ¿¿\001Â£\022ğ¿¿\001Â£\016ğ¿¿\001Â£Kğ¿¿\001Â£", "", "\n%\007ğ¿¿\032%\004ğ¿¿\001%\001ğ¿¿\032%", "", "\001Â¥", "", "", "", "", "\n%\007ğ¿¿\032%\004ğ¿¿\001%\001ğ¿¿\032%", "" };
/*      */ 
/* 3611 */     DFA22_eot = DFA.unpackEncodedString("\001ğ¿¿\003%\001,\001ğ¿¿\001.\002ğ¿¿\0010\0012\004%\002ğ¿¿\001<\005ğ¿¿\001%\001ğ¿¿\001?\nğ¿¿\001%\002ğ¿¿\004%\bğ¿¿\b%\002ğ¿¿\001%\006ğ¿¿\017%\rğ¿¿\n%\001{\004%\002ğ¿¿\002%\001Âƒ\002%\001Â†\004%\001ğ¿¿\003%\001Â\001ğ¿¿\002%\001ğ¿¿\002%\001ğ¿¿\001Â”\002%\001Â—\001Â˜\002%\002ğ¿¿\001%\001Â\001%\001ÂŸ\001ğ¿¿\001%\001Â¡\003ğ¿¿\001Â¢\001ğ¿¿\001%\001ğ¿¿\001Â¤\001ğ¿¿\001%\004ğ¿¿\001Â¦\001ğ¿¿");
/* 3612 */     DFA22_eof = DFA.unpackEncodedString("Â§ğ¿¿");
/* 3613 */     DFA22_min = DFA.unpackEncodedStringToUnsignedChars("");
/* 3614 */     DFA22_max = DFA.unpackEncodedStringToUnsignedChars("\001~\001e\001c\001r\001(\001ğ¿¿\001.\002ğ¿¿\001>\001=\001e\001u\002r\002ğ¿¿\001:\005ğ¿¿\001a\001ğ¿¿\001<\003ğ¿¿\001/\001ğ¿¿\005ğ¿¿\001p\002ğ¿¿\001t\001o\001a\001n\bğ¿¿\001x\001r\001o\001b\001e\001r\001k\001a\002ğ¿¿\001t\004ğ¿¿\002ğ¿¿\001t\001u\001p\001g\001a\001e\001s\001t\001v\001l\001e\001o\001e\001m\001c\013ğ¿¿\002ğ¿¿\001i\001r\001e\001m\001l\001r\002e\001a\001i\001z\001w\001n\001m\001h\001ğ¿¿\001ğ¿¿\001o\001n\001z\001e\001l\001z\001r\001c\001t\001c\001ğ¿¿\002s\001a\001z\001ğ¿¿\001n\001s\001ğ¿¿\001n\001y\001ğ¿¿\001z\001t\001e\002z\001{\001r\001ğ¿¿\001ğ¿¿\001s\001z\001t\001z\001ğ¿¿\001e\001z\003ğ¿¿\001z\001ğ¿¿\001{\001ğ¿¿\001z\001ğ¿¿\001d\004ğ¿¿\001z\001ğ¿¿");
/* 3615 */     DFA22_accept = DFA.unpackEncodedString("\005ğ¿¿\001\006\001ğ¿¿\001\b\001\t\006ğ¿¿\001\020\001\021\001ğ¿¿\001\023\001\031\001\032\001\033\001\034\001ğ¿¿\001 \001ğ¿¿\001\"\001$\001&\002ğ¿¿\001+\001-\001.\001/\0010\001ğ¿¿\0011\0014\004ğ¿¿\001\004\001\005\001\007\001#\001\037\001\n\001\013\001%\bğ¿¿\001\022\001\027\001ğ¿¿\001,\001!\001'\001(\034ğ¿¿\001*\001)\020ğ¿¿\001)\nğ¿¿\001\016\007ğ¿¿\001\002\002ğ¿¿\001\f\007ğ¿¿\001\035\005ğ¿¿\001\r\002ğ¿¿\001\025\001\030\0013\003ğ¿¿\001\001\001ğ¿¿\001\036\001ğ¿¿\001\026\001\017\0012\001\003\001ğ¿¿\001\024");
/* 3616 */     DFA22_special = DFA.unpackEncodedString("");
/*      */ 
/* 3620 */     int numStates = DFA22_transitionS.length;
/* 3621 */     DFA22_transition = new short[numStates][];
/* 3622 */     for (int i = 0; i < numStates; i++)
/* 3623 */       DFA22_transition[i] = DFA.unpackEncodedString(DFA22_transitionS[i]);
/*      */   }
/*      */ 
/*      */   class DFA22 extends DFA
/*      */   {
/*      */     public DFA22(BaseRecognizer recognizer)
/*      */     {
/* 3630 */       this.recognizer = recognizer;
/* 3631 */       this.decisionNumber = 22;
/* 3632 */       this.eot = ANTLRv3Lexer.DFA22_eot;
/* 3633 */       this.eof = ANTLRv3Lexer.DFA22_eof;
/* 3634 */       this.min = ANTLRv3Lexer.DFA22_min;
/* 3635 */       this.max = ANTLRv3Lexer.DFA22_max;
/* 3636 */       this.accept = ANTLRv3Lexer.DFA22_accept;
/* 3637 */       this.special = ANTLRv3Lexer.DFA22_special;
/* 3638 */       this.transition = ANTLRv3Lexer.DFA22_transition;
/*      */     }
/*      */     public String getDescription() {
/* 3641 */       return "1:1: Tokens : ( RET | SCOPE | FRAGMENT | TREE_BEGIN | ROOT | BANG | RANGE | REWRITE | AT | LABEL_ASSIGN | LIST_LABEL_ASSIGN | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | T__83 | T__84 | T__85 | T__86 | T__87 | T__88 | T__89 | T__90 | T__91 | T__92 | T__93 | SL_COMMENT | ML_COMMENT | CHAR_LITERAL | STRING_LITERAL | DOUBLE_QUOTE_STRING_LITERAL | DOUBLE_ANGLE_STRING_LITERAL | INT | ARG_ACTION | ACTION | TOKEN_REF | RULE_REF | OPTIONS | TOKENS | WS );";
/*      */     }
/*      */     public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
/* 3644 */       IntStream input = _input;
/* 3645 */       int _s = s;
/* 3646 */       switch (s) {
/*      */       case 0:
/* 3648 */         int LA22_67 = input.LA(1);
/*      */ 
/* 3650 */         s = -1;
/* 3651 */         if (((LA22_67 >= 0) && (LA22_67 <= 38)) || ((LA22_67 >= 40) && (LA22_67 <= 65535))) s = 94;
/* 3653 */         else if (LA22_67 == 39) s = 95;
/*      */ 
/* 3655 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 1:
/* 3658 */         int LA22_66 = input.LA(1);
/*      */ 
/* 3660 */         s = -1;
/* 3661 */         if (LA22_66 == 110) s = 83;
/* 3663 */         else if (LA22_66 == 114) s = 84;
/* 3665 */         else if (LA22_66 == 116) s = 85;
/* 3667 */         else if (LA22_66 == 98) s = 86;
/* 3669 */         else if (LA22_66 == 102) s = 87;
/* 3671 */         else if (LA22_66 == 34) s = 88;
/* 3673 */         else if (LA22_66 == 39) s = 89;
/* 3675 */         else if (LA22_66 == 92) s = 90;
/* 3677 */         else if (LA22_66 == 62) s = 91;
/* 3679 */         else if (LA22_66 == 117) s = 92;
/* 3681 */         else if (((LA22_66 >= 0) && (LA22_66 <= 33)) || ((LA22_66 >= 35) && (LA22_66 <= 38)) || ((LA22_66 >= 40) && (LA22_66 <= 61)) || ((LA22_66 >= 63) && (LA22_66 <= 91)) || ((LA22_66 >= 93) && (LA22_66 <= 97)) || ((LA22_66 >= 99) && (LA22_66 <= 101)) || ((LA22_66 >= 103) && (LA22_66 <= 109)) || ((LA22_66 >= 111) && (LA22_66 <= 113)) || (LA22_66 == 115) || ((LA22_66 >= 118) && (LA22_66 <= 65535))) s = 93;
/*      */ 
/* 3683 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 2:
/* 3686 */         int LA22_155 = input.LA(1);
/*      */ 
/* 3688 */         s = -1;
/* 3689 */         if (LA22_155 == 39) s = 95;
/* 3691 */         else if (((LA22_155 >= 0) && (LA22_155 <= 38)) || ((LA22_155 >= 40) && (LA22_155 <= 65535))) s = 94;
/*      */ 
/* 3693 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 3:
/* 3696 */         int LA22_92 = input.LA(1);
/*      */ 
/* 3698 */         s = -1;
/* 3699 */         if (((LA22_92 >= 48) && (LA22_92 <= 57)) || ((LA22_92 >= 65) && (LA22_92 <= 70)) || ((LA22_92 >= 97) && (LA22_92 <= 102))) s = 111;
/* 3701 */         else if (LA22_92 == 39) s = 95;
/* 3703 */         else if (((LA22_92 >= 0) && (LA22_92 <= 38)) || ((LA22_92 >= 40) && (LA22_92 <= 47)) || ((LA22_92 >= 58) && (LA22_92 <= 64)) || ((LA22_92 >= 71) && (LA22_92 <= 96)) || ((LA22_92 >= 103) && (LA22_92 <= 65535))) s = 94;
/*      */ 
/* 3705 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 4:
/* 3708 */         int LA22_88 = input.LA(1);
/*      */ 
/* 3710 */         s = -1;
/* 3711 */         if (LA22_88 == 39) s = 95;
/* 3713 */         else if (((LA22_88 >= 0) && (LA22_88 <= 38)) || ((LA22_88 >= 40) && (LA22_88 <= 65535))) s = 94;
/*      */ 
/* 3715 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 5:
/* 3718 */         int LA22_89 = input.LA(1);
/*      */ 
/* 3720 */         s = -1;
/* 3721 */         if (LA22_89 == 39) s = 95;
/* 3723 */         else if (((LA22_89 >= 0) && (LA22_89 <= 38)) || ((LA22_89 >= 40) && (LA22_89 <= 65535))) s = 94;
/*      */ 
/* 3725 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 6:
/* 3728 */         int LA22_90 = input.LA(1);
/*      */ 
/* 3730 */         s = -1;
/* 3731 */         if (((LA22_90 >= 0) && (LA22_90 <= 38)) || ((LA22_90 >= 40) && (LA22_90 <= 65535))) s = 94;
/* 3733 */         else if (LA22_90 == 39) s = 95;
/*      */ 
/* 3735 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 7:
/* 3738 */         int LA22_91 = input.LA(1);
/*      */ 
/* 3740 */         s = -1;
/* 3741 */         if (((LA22_91 >= 0) && (LA22_91 <= 38)) || ((LA22_91 >= 40) && (LA22_91 <= 65535))) s = 94;
/* 3743 */         else if (LA22_91 == 39) s = 95;
/*      */ 
/* 3745 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 8:
/* 3748 */         int LA22_93 = input.LA(1);
/*      */ 
/* 3750 */         s = -1;
/* 3751 */         if (LA22_93 == 39) s = 95;
/* 3753 */         else if (((LA22_93 >= 0) && (LA22_93 <= 38)) || ((LA22_93 >= 40) && (LA22_93 <= 65535))) s = 94;
/*      */ 
/* 3755 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 9:
/* 3758 */         int LA22_30 = input.LA(1);
/*      */ 
/* 3760 */         s = -1;
/* 3761 */         if (LA22_30 == 92) s = 66;
/* 3763 */         else if (((LA22_30 >= 0) && (LA22_30 <= 38)) || ((LA22_30 >= 40) && (LA22_30 <= 91)) || ((LA22_30 >= 93) && (LA22_30 <= 65535))) s = 67;
/*      */ 
/* 3765 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 10:
/* 3768 */         int LA22_83 = input.LA(1);
/*      */ 
/* 3770 */         s = -1;
/* 3771 */         if (((LA22_83 >= 0) && (LA22_83 <= 38)) || ((LA22_83 >= 40) && (LA22_83 <= 65535))) s = 94;
/* 3773 */         else if (LA22_83 == 39) s = 95;
/*      */ 
/* 3775 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 11:
/* 3778 */         int LA22_85 = input.LA(1);
/*      */ 
/* 3780 */         s = -1;
/* 3781 */         if (((LA22_85 >= 0) && (LA22_85 <= 38)) || ((LA22_85 >= 40) && (LA22_85 <= 65535))) s = 94;
/* 3783 */         else if (LA22_85 == 39) s = 95;
/*      */ 
/* 3785 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 12:
/* 3788 */         int LA22_111 = input.LA(1);
/*      */ 
/* 3790 */         s = -1;
/* 3791 */         if (((LA22_111 >= 0) && (LA22_111 <= 47)) || ((LA22_111 >= 58) && (LA22_111 <= 64)) || ((LA22_111 >= 71) && (LA22_111 <= 96)) || ((LA22_111 >= 103) && (LA22_111 <= 65535))) s = 94;
/* 3793 */         else if (((LA22_111 >= 48) && (LA22_111 <= 57)) || ((LA22_111 >= 65) && (LA22_111 <= 70)) || ((LA22_111 >= 97) && (LA22_111 <= 102))) s = 128;
/*      */ 
/* 3795 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 13:
/* 3798 */         int LA22_84 = input.LA(1);
/*      */ 
/* 3800 */         s = -1;
/* 3801 */         if (LA22_84 == 39) s = 95;
/* 3803 */         else if (((LA22_84 >= 0) && (LA22_84 <= 38)) || ((LA22_84 >= 40) && (LA22_84 <= 65535))) s = 94;
/*      */ 
/* 3805 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 14:
/* 3808 */         int LA22_128 = input.LA(1);
/*      */ 
/* 3810 */         s = -1;
/* 3811 */         if (((LA22_128 >= 0) && (LA22_128 <= 47)) || ((LA22_128 >= 58) && (LA22_128 <= 64)) || ((LA22_128 >= 71) && (LA22_128 <= 96)) || ((LA22_128 >= 103) && (LA22_128 <= 65535))) s = 94;
/* 3813 */         else if (((LA22_128 >= 48) && (LA22_128 <= 57)) || ((LA22_128 >= 65) && (LA22_128 <= 70)) || ((LA22_128 >= 97) && (LA22_128 <= 102))) s = 143;
/*      */ 
/* 3815 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 15:
/* 3818 */         int LA22_87 = input.LA(1);
/*      */ 
/* 3820 */         s = -1;
/* 3821 */         if (LA22_87 == 39) s = 95;
/* 3823 */         else if (((LA22_87 >= 0) && (LA22_87 <= 38)) || ((LA22_87 >= 40) && (LA22_87 <= 65535))) s = 94;
/*      */ 
/* 3825 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 16:
/* 3828 */         int LA22_143 = input.LA(1);
/*      */ 
/* 3830 */         s = -1;
/* 3831 */         if (((LA22_143 >= 0) && (LA22_143 <= 47)) || ((LA22_143 >= 58) && (LA22_143 <= 64)) || ((LA22_143 >= 71) && (LA22_143 <= 96)) || ((LA22_143 >= 103) && (LA22_143 <= 65535))) s = 94;
/* 3833 */         else if (((LA22_143 >= 48) && (LA22_143 <= 57)) || ((LA22_143 >= 65) && (LA22_143 <= 70)) || ((LA22_143 >= 97) && (LA22_143 <= 102))) s = 155;
/*      */ 
/* 3835 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 17:
/* 3838 */         int LA22_86 = input.LA(1);
/*      */ 
/* 3840 */         s = -1;
/* 3841 */         if (LA22_86 == 39) s = 95;
/* 3843 */         else if (((LA22_86 >= 0) && (LA22_86 <= 38)) || ((LA22_86 >= 40) && (LA22_86 <= 65535))) s = 94;
/*      */ 
/* 3845 */         if (s >= 0) return s;
/*      */         break;
/*      */       }
/* 3848 */       NoViableAltException nvae = new NoViableAltException(getDescription(), 22, _s, input);
/*      */ 
/* 3850 */       error(nvae);
/* 3851 */       throw nvae;
/*      */     }
/*      */   }
/*      */ 
/*      */   class DFA13 extends DFA
/*      */   {
/*      */     public DFA13(BaseRecognizer recognizer)
/*      */     {
/* 3289 */       this.recognizer = recognizer;
/* 3290 */       this.decisionNumber = 13;
/* 3291 */       this.eot = ANTLRv3Lexer.DFA13_eot;
/* 3292 */       this.eof = ANTLRv3Lexer.DFA13_eof;
/* 3293 */       this.min = ANTLRv3Lexer.DFA13_min;
/* 3294 */       this.max = ANTLRv3Lexer.DFA13_max;
/* 3295 */       this.accept = ANTLRv3Lexer.DFA13_accept;
/* 3296 */       this.special = ANTLRv3Lexer.DFA13_special;
/* 3297 */       this.transition = ANTLRv3Lexer.DFA13_transition;
/*      */     }
/*      */     public String getDescription() {
/* 3300 */       return "()* loopback of 555:2: ( options {greedy=false; k=2; } : NESTED_ACTION | SL_COMMENT | ML_COMMENT | ACTION_STRING_LITERAL | ACTION_CHAR_LITERAL | . )*";
/*      */     }
/*      */     public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
/* 3303 */       IntStream input = _input;
/* 3304 */       int _s = s;
/* 3305 */       switch (s) {
/*      */       case 0:
/* 3307 */         int LA13_0 = input.LA(1);
/*      */ 
/* 3309 */         s = -1;
/* 3310 */         if (LA13_0 == 125) s = 1;
/* 3312 */         else if (LA13_0 == 123) s = 2;
/* 3314 */         else if (LA13_0 == 47) s = 3;
/* 3316 */         else if (LA13_0 == 34) s = 4;
/* 3318 */         else if (LA13_0 == 39) s = 5;
/* 3320 */         else if (((LA13_0 >= 0) && (LA13_0 <= 33)) || ((LA13_0 >= 35) && (LA13_0 <= 38)) || ((LA13_0 >= 40) && (LA13_0 <= 46)) || ((LA13_0 >= 48) && (LA13_0 <= 122)) || (LA13_0 == 124) || ((LA13_0 >= 126) && (LA13_0 <= 65535))) s = 6;
/*      */ 
/* 3322 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 1:
/* 3325 */         int LA13_3 = input.LA(1);
/*      */ 
/* 3327 */         s = -1;
/* 3328 */         if (LA13_3 == 47) s = 7;
/* 3330 */         else if (LA13_3 == 42) s = 8;
/* 3332 */         else if (((LA13_3 >= 0) && (LA13_3 <= 41)) || ((LA13_3 >= 43) && (LA13_3 <= 46)) || ((LA13_3 >= 48) && (LA13_3 <= 65535))) s = 6;
/*      */ 
/* 3334 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 2:
/* 3337 */         int LA13_4 = input.LA(1);
/*      */ 
/* 3339 */         s = -1;
/* 3340 */         if (LA13_4 == 125) s = 14;
/* 3342 */         else if (LA13_4 == 123) s = 15;
/* 3344 */         else if (LA13_4 == 47) s = 16;
/* 3346 */         else if (LA13_4 == 34) s = 17;
/* 3348 */         else if (LA13_4 == 39) s = 18;
/* 3350 */         else if (LA13_4 == 92) s = 19;
/* 3352 */         else if (((LA13_4 >= 0) && (LA13_4 <= 33)) || ((LA13_4 >= 35) && (LA13_4 <= 38)) || ((LA13_4 >= 40) && (LA13_4 <= 46)) || ((LA13_4 >= 48) && (LA13_4 <= 91)) || ((LA13_4 >= 93) && (LA13_4 <= 122)) || (LA13_4 == 124) || ((LA13_4 >= 126) && (LA13_4 <= 65535))) s = 20;
/*      */ 
/* 3354 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 3:
/* 3357 */         int LA13_5 = input.LA(1);
/*      */ 
/* 3359 */         s = -1;
/* 3360 */         if (LA13_5 == 92) s = 21;
/* 3362 */         else if (LA13_5 == 125) s = 22;
/* 3364 */         else if (LA13_5 == 123) s = 23;
/* 3366 */         else if (LA13_5 == 47) s = 24;
/* 3368 */         else if (LA13_5 == 34) s = 25;
/* 3370 */         else if (((LA13_5 >= 0) && (LA13_5 <= 33)) || ((LA13_5 >= 35) && (LA13_5 <= 38)) || ((LA13_5 >= 40) && (LA13_5 <= 46)) || ((LA13_5 >= 48) && (LA13_5 <= 91)) || ((LA13_5 >= 93) && (LA13_5 <= 122)) || (LA13_5 == 124) || ((LA13_5 >= 126) && (LA13_5 <= 65535))) s = 26;
/* 3372 */         else if (LA13_5 == 39) s = 6;
/*      */ 
/* 3374 */         if (s >= 0) return s;
/*      */         break;
/*      */       }
/* 3377 */       NoViableAltException nvae = new NoViableAltException(getDescription(), 13, _s, input);
/*      */ 
/* 3379 */       error(nvae);
/* 3380 */       throw nvae;
/*      */     }
/*      */   }
/*      */ 
/*      */   class DFA9 extends DFA
/*      */   {
/*      */     public DFA9(BaseRecognizer recognizer)
/*      */     {
/* 3173 */       this.recognizer = recognizer;
/* 3174 */       this.decisionNumber = 9;
/* 3175 */       this.eot = ANTLRv3Lexer.DFA9_eot;
/* 3176 */       this.eof = ANTLRv3Lexer.DFA9_eof;
/* 3177 */       this.min = ANTLRv3Lexer.DFA9_min;
/* 3178 */       this.max = ANTLRv3Lexer.DFA9_max;
/* 3179 */       this.accept = ANTLRv3Lexer.DFA9_accept;
/* 3180 */       this.special = ANTLRv3Lexer.DFA9_special;
/* 3181 */       this.transition = ANTLRv3Lexer.DFA9_transition;
/*      */     }
/*      */     public String getDescription() {
/* 3184 */       return "507:3: ( 'n' | 'r' | 't' | 'b' | 'f' | '\"' | '\\'' | '\\\\' | '>' | 'u' XDIGIT XDIGIT XDIGIT XDIGIT | . )";
/*      */     }
/*      */     public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
/* 3187 */       IntStream input = _input;
/* 3188 */       int _s = s;
/* 3189 */       switch (s) {
/*      */       case 0:
/* 3191 */         int LA9_0 = input.LA(1);
/*      */ 
/* 3193 */         s = -1;
/* 3194 */         if (LA9_0 == 110) s = 1;
/* 3196 */         else if (LA9_0 == 114) s = 2;
/* 3198 */         else if (LA9_0 == 116) s = 3;
/* 3200 */         else if (LA9_0 == 98) s = 4;
/* 3202 */         else if (LA9_0 == 102) s = 5;
/* 3204 */         else if (LA9_0 == 34) s = 6;
/* 3206 */         else if (LA9_0 == 39) s = 7;
/* 3208 */         else if (LA9_0 == 92) s = 8;
/* 3210 */         else if (LA9_0 == 62) s = 9;
/* 3212 */         else if (LA9_0 == 117) s = 10;
/* 3214 */         else if (((LA9_0 >= 0) && (LA9_0 <= 33)) || ((LA9_0 >= 35) && (LA9_0 <= 38)) || ((LA9_0 >= 40) && (LA9_0 <= 61)) || ((LA9_0 >= 63) && (LA9_0 <= 91)) || ((LA9_0 >= 93) && (LA9_0 <= 97)) || ((LA9_0 >= 99) && (LA9_0 <= 101)) || ((LA9_0 >= 103) && (LA9_0 <= 109)) || ((LA9_0 >= 111) && (LA9_0 <= 113)) || (LA9_0 == 115) || ((LA9_0 >= 118) && (LA9_0 <= 65535))) s = 11;
/*      */ 
/* 3216 */         if (s >= 0) return s;
/*      */         break;
/*      */       }
/* 3219 */       NoViableAltException nvae = new NoViableAltException(getDescription(), 9, _s, input);
/*      */ 
/* 3221 */       error(nvae);
/* 3222 */       throw nvae;
/*      */     }
/*      */   }
/*      */ 
/*      */   class DFA2 extends DFA
/*      */   {
/*      */     public DFA2(BaseRecognizer recognizer)
/*      */     {
/* 2815 */       this.recognizer = recognizer;
/* 2816 */       this.decisionNumber = 2;
/* 2817 */       this.eot = ANTLRv3Lexer.DFA2_eot;
/* 2818 */       this.eof = ANTLRv3Lexer.DFA2_eof;
/* 2819 */       this.min = ANTLRv3Lexer.DFA2_min;
/* 2820 */       this.max = ANTLRv3Lexer.DFA2_max;
/* 2821 */       this.accept = ANTLRv3Lexer.DFA2_accept;
/* 2822 */       this.special = ANTLRv3Lexer.DFA2_special;
/* 2823 */       this.transition = ANTLRv3Lexer.DFA2_transition;
/*      */     }
/*      */     public String getDescription() {
/* 2826 */       return "472:5: ( ' $ANTLR ' SRC | (~ ( '\\r' | '\\n' ) )* )";
/*      */     }
/*      */     public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
/* 2829 */       IntStream input = _input;
/* 2830 */       int _s = s;
/* 2831 */       switch (s) {
/*      */       case 0:
/* 2833 */         int LA2_12 = input.LA(1);
/*      */ 
/* 2835 */         s = -1;
/* 2836 */         if (LA2_12 == 32) s = 13;
/* 2838 */         else if (((LA2_12 >= 0) && (LA2_12 <= 31)) || ((LA2_12 >= 33) && (LA2_12 <= 65535))) s = 2;
/*      */ 
/* 2840 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 1:
/* 2843 */         int LA2_10 = input.LA(1);
/*      */ 
/* 2845 */         s = -1;
/* 2846 */         if (LA2_10 == 114) s = 11;
/* 2848 */         else if (((LA2_10 >= 0) && (LA2_10 <= 113)) || ((LA2_10 >= 115) && (LA2_10 <= 65535))) s = 2;
/*      */ 
/* 2850 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 2:
/* 2853 */         int LA2_11 = input.LA(1);
/*      */ 
/* 2855 */         s = -1;
/* 2856 */         if (LA2_11 == 99) s = 12;
/* 2858 */         else if (((LA2_11 >= 0) && (LA2_11 <= 98)) || ((LA2_11 >= 100) && (LA2_11 <= 65535))) s = 2;
/*      */ 
/* 2860 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 3:
/* 2863 */         int LA2_23 = input.LA(1);
/*      */ 
/* 2865 */         s = -1;
/* 2866 */         if (LA2_23 == 10) s = 16;
/* 2868 */         else if (((LA2_23 >= 0) && (LA2_23 <= 9)) || ((LA2_23 >= 11) && (LA2_23 <= 65535))) s = 20;
/*      */ 
/* 2870 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 4:
/* 2873 */         int LA2_18 = input.LA(1);
/*      */ 
/* 2875 */         s = -1;
/* 2876 */         if (LA2_18 == 34) s = 19;
/* 2878 */         else if (LA2_18 == 92) s = 17;
/* 2880 */         else if (LA2_18 == 13) s = 15;
/* 2882 */         else if (LA2_18 == 10) s = 16;
/* 2884 */         else if (((LA2_18 >= 0) && (LA2_18 <= 9)) || ((LA2_18 >= 11) && (LA2_18 <= 12)) || ((LA2_18 >= 14) && (LA2_18 <= 33)) || ((LA2_18 >= 35) && (LA2_18 <= 91)) || ((LA2_18 >= 93) && (LA2_18 <= 65535))) s = 18;
/*      */ 
/* 2886 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 5:
/* 2889 */         int LA2_15 = input.LA(1);
/*      */ 
/* 2891 */         s = -1;
/* 2892 */         if (LA2_15 == 10) s = 16;
/* 2894 */         else if (((LA2_15 >= 0) && (LA2_15 <= 9)) || ((LA2_15 >= 11) && (LA2_15 <= 65535))) s = 20;
/*      */ 
/* 2896 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 6:
/* 2899 */         int LA2_17 = input.LA(1);
/*      */ 
/* 2901 */         s = -1;
/* 2902 */         if (LA2_17 == 39) s = 21;
/* 2904 */         else if (LA2_17 == 34) s = 22;
/* 2906 */         else if (LA2_17 == 13) s = 23;
/* 2908 */         else if (LA2_17 == 10) s = 24;
/* 2910 */         else if (((LA2_17 >= 0) && (LA2_17 <= 9)) || ((LA2_17 >= 11) && (LA2_17 <= 12)) || ((LA2_17 >= 14) && (LA2_17 <= 33)) || ((LA2_17 >= 35) && (LA2_17 <= 38)) || ((LA2_17 >= 40) && (LA2_17 <= 65535))) s = 25;
/*      */ 
/* 2912 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 7:
/* 2915 */         int LA2_19 = input.LA(1);
/*      */ 
/* 2917 */         s = -1;
/* 2918 */         if (((LA2_19 >= 0) && (LA2_19 <= 31)) || ((LA2_19 >= 33) && (LA2_19 <= 65535))) s = 2;
/* 2920 */         else if (LA2_19 == 32) s = 26;
/*      */ 
/* 2922 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 8:
/* 2925 */         int LA2_9 = input.LA(1);
/*      */ 
/* 2927 */         s = -1;
/* 2928 */         if (LA2_9 == 115) s = 10;
/* 2930 */         else if (((LA2_9 >= 0) && (LA2_9 <= 114)) || ((LA2_9 >= 116) && (LA2_9 <= 65535))) s = 2;
/*      */ 
/* 2932 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 9:
/* 2935 */         int LA2_16 = input.LA(1);
/*      */ 
/* 2937 */         s = -1;
/* 2938 */         if ((LA2_16 >= 0) && (LA2_16 <= 65535)) s = 20;
/*      */         else {
/* 2940 */           s = 2;
/*      */         }
/* 2942 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 10:
/* 2945 */         int LA2_14 = input.LA(1);
/*      */ 
/* 2947 */         s = -1;
/* 2948 */         if (LA2_14 == 13) s = 15;
/* 2950 */         else if (LA2_14 == 10) s = 16;
/* 2952 */         else if (LA2_14 == 92) s = 17;
/* 2954 */         else if (((LA2_14 >= 0) && (LA2_14 <= 9)) || ((LA2_14 >= 11) && (LA2_14 <= 12)) || ((LA2_14 >= 14) && (LA2_14 <= 33)) || ((LA2_14 >= 35) && (LA2_14 <= 91)) || ((LA2_14 >= 93) && (LA2_14 <= 65535))) s = 18;
/* 2956 */         else if (LA2_14 == 34) s = 19;
/*      */ 
/* 2958 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 11:
/* 2961 */         int LA2_13 = input.LA(1);
/*      */ 
/* 2963 */         s = -1;
/* 2964 */         if (((LA2_13 >= 0) && (LA2_13 <= 33)) || ((LA2_13 >= 35) && (LA2_13 <= 65535))) s = 2;
/* 2966 */         else if (LA2_13 == 34) s = 14;
/*      */ 
/* 2968 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 12:
/* 2971 */         int LA2_26 = input.LA(1);
/*      */ 
/* 2973 */         s = -1;
/* 2974 */         if (((LA2_26 >= 0) && (LA2_26 <= 47)) || ((LA2_26 >= 58) && (LA2_26 <= 65535))) s = 2;
/* 2976 */         else if ((LA2_26 >= 48) && (LA2_26 <= 57)) s = 27;
/*      */ 
/* 2978 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 13:
/* 2981 */         int LA2_25 = input.LA(1);
/*      */ 
/* 2983 */         s = -1;
/* 2984 */         if (LA2_25 == 34) s = 19;
/* 2986 */         else if (LA2_25 == 92) s = 17;
/* 2988 */         else if (LA2_25 == 13) s = 15;
/* 2990 */         else if (LA2_25 == 10) s = 16;
/* 2992 */         else if (((LA2_25 >= 0) && (LA2_25 <= 9)) || ((LA2_25 >= 11) && (LA2_25 <= 12)) || ((LA2_25 >= 14) && (LA2_25 <= 33)) || ((LA2_25 >= 35) && (LA2_25 <= 91)) || ((LA2_25 >= 93) && (LA2_25 <= 65535))) s = 18;
/*      */ 
/* 2994 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 14:
/* 2997 */         int LA2_0 = input.LA(1);
/*      */ 
/* 2999 */         s = -1;
/* 3000 */         if (LA2_0 == 32) s = 1;
/* 3002 */         else if (((LA2_0 >= 0) && (LA2_0 <= 31)) || ((LA2_0 >= 33) && (LA2_0 <= 65535))) s = 2;
/*      */ 
/* 3004 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 15:
/* 3007 */         int LA2_3 = input.LA(1);
/*      */ 
/* 3009 */         s = -1;
/* 3010 */         if (LA2_3 == 65) s = 4;
/* 3012 */         else if (((LA2_3 >= 0) && (LA2_3 <= 64)) || ((LA2_3 >= 66) && (LA2_3 <= 65535))) s = 2;
/*      */ 
/* 3014 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 16:
/* 3017 */         int LA2_21 = input.LA(1);
/*      */ 
/* 3019 */         s = -1;
/* 3020 */         if (LA2_21 == 13) s = 15;
/* 3022 */         else if (LA2_21 == 10) s = 16;
/* 3024 */         else if (LA2_21 == 34) s = 19;
/* 3026 */         else if (LA2_21 == 92) s = 17;
/* 3028 */         else if (((LA2_21 >= 0) && (LA2_21 <= 9)) || ((LA2_21 >= 11) && (LA2_21 <= 12)) || ((LA2_21 >= 14) && (LA2_21 <= 33)) || ((LA2_21 >= 35) && (LA2_21 <= 91)) || ((LA2_21 >= 93) && (LA2_21 <= 65535))) s = 18;
/*      */ 
/* 3030 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 17:
/* 3033 */         int LA2_1 = input.LA(1);
/*      */ 
/* 3035 */         s = -1;
/* 3036 */         if (LA2_1 == 36) s = 3;
/* 3038 */         else if (((LA2_1 >= 0) && (LA2_1 <= 35)) || ((LA2_1 >= 37) && (LA2_1 <= 65535))) s = 2;
/*      */ 
/* 3040 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 18:
/* 3043 */         int LA2_5 = input.LA(1);
/*      */ 
/* 3045 */         s = -1;
/* 3046 */         if (LA2_5 == 84) s = 6;
/* 3048 */         else if (((LA2_5 >= 0) && (LA2_5 <= 83)) || ((LA2_5 >= 85) && (LA2_5 <= 65535))) s = 2;
/*      */ 
/* 3050 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 19:
/* 3053 */         int LA2_4 = input.LA(1);
/*      */ 
/* 3055 */         s = -1;
/* 3056 */         if (LA2_4 == 78) s = 5;
/* 3058 */         else if (((LA2_4 >= 0) && (LA2_4 <= 77)) || ((LA2_4 >= 79) && (LA2_4 <= 65535))) s = 2;
/*      */ 
/* 3060 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 20:
/* 3063 */         int LA2_24 = input.LA(1);
/*      */ 
/* 3065 */         s = -1;
/* 3066 */         if ((LA2_24 >= 0) && (LA2_24 <= 65535)) s = 20;
/*      */         else {
/* 3068 */           s = 2;
/*      */         }
/* 3070 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 21:
/* 3073 */         int LA2_7 = input.LA(1);
/*      */ 
/* 3075 */         s = -1;
/* 3076 */         if (LA2_7 == 82) s = 8;
/* 3078 */         else if (((LA2_7 >= 0) && (LA2_7 <= 81)) || ((LA2_7 >= 83) && (LA2_7 <= 65535))) s = 2;
/*      */ 
/* 3080 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 22:
/* 3083 */         int LA2_22 = input.LA(1);
/*      */ 
/* 3085 */         s = -1;
/* 3086 */         if (LA2_22 == 13) s = 15;
/* 3088 */         else if (LA2_22 == 10) s = 16;
/* 3090 */         else if (LA2_22 == 34) s = 19;
/* 3092 */         else if (LA2_22 == 92) s = 17;
/* 3094 */         else if (((LA2_22 >= 0) && (LA2_22 <= 9)) || ((LA2_22 >= 11) && (LA2_22 <= 12)) || ((LA2_22 >= 14) && (LA2_22 <= 33)) || ((LA2_22 >= 35) && (LA2_22 <= 91)) || ((LA2_22 >= 93) && (LA2_22 <= 65535))) s = 18;
/*      */ 
/* 3096 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 23:
/* 3099 */         int LA2_6 = input.LA(1);
/*      */ 
/* 3101 */         s = -1;
/* 3102 */         if (LA2_6 == 76) s = 7;
/* 3104 */         else if (((LA2_6 >= 0) && (LA2_6 <= 75)) || ((LA2_6 >= 77) && (LA2_6 <= 65535))) s = 2;
/*      */ 
/* 3106 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 24:
/* 3109 */         int LA2_8 = input.LA(1);
/*      */ 
/* 3111 */         s = -1;
/* 3112 */         if (LA2_8 == 32) s = 9;
/* 3114 */         else if (((LA2_8 >= 0) && (LA2_8 <= 31)) || ((LA2_8 >= 33) && (LA2_8 <= 65535))) s = 2;
/*      */ 
/* 3116 */         if (s >= 0) return s;
/*      */         break;
/*      */       }
/* 3119 */       NoViableAltException nvae = new NoViableAltException(getDescription(), 2, _s, input);
/*      */ 
/* 3121 */       error(nvae);
/* 3122 */       throw nvae;
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.grammar.v3.ANTLRv3Lexer
 * JD-Core Version:    0.6.2
 */