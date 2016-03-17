/*     */ package org.antlr.grammar.v3;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import org.antlr.runtime.ANTLRStringStream;
/*     */ import org.antlr.runtime.CharStream;
/*     */ import org.antlr.runtime.CommonToken;
/*     */ import org.antlr.runtime.FailedPredicateException;
/*     */ import org.antlr.runtime.IntStream;
/*     */ import org.antlr.runtime.Lexer;
/*     */ import org.antlr.runtime.MismatchedSetException;
/*     */ import org.antlr.runtime.NoViableAltException;
/*     */ import org.antlr.runtime.RecognitionException;
/*     */ import org.antlr.runtime.RecognizerSharedState;
/*     */ import org.antlr.tool.AttributeScope;
/*     */ import org.antlr.tool.Grammar;
/*     */ import org.antlr.tool.Grammar.LabelElementPair;
/*     */ import org.antlr.tool.GrammarAST;
/*     */ import org.antlr.tool.Rule;
/*     */ 
/*     */ public class ActionAnalysis extends Lexer
/*     */ {
/*     */   public static final int X_Y = 5;
/*     */   public static final int ID = 4;
/*     */   public static final int EOF = -1;
/*     */   public static final int Y = 7;
/*     */   public static final int X = 6;
/*     */   Rule enclosingRule;
/*     */   Grammar grammar;
/*     */   antlr.Token actionToken;
/*  31 */   int outerAltNum = 0;
/*     */ 
/*     */   public ActionAnalysis(Grammar grammar, String ruleName, GrammarAST actionAST)
/*     */   {
/*  35 */     this(new ANTLRStringStream(actionAST.token.getText()));
/*  36 */     this.grammar = grammar;
/*  37 */     this.enclosingRule = grammar.getLocallyDefinedRule(ruleName);
/*  38 */     this.actionToken = actionAST.token;
/*  39 */     this.outerAltNum = actionAST.outerAltNum;
/*     */   }
/*     */ 
/*     */   public void analyze()
/*     */   {
/*     */     org.antlr.runtime.Token t;
/*     */     do
/*  46 */       t = nextToken();
/*  47 */     while (t.getType() != -1);
/*     */   }
/*     */ 
/*     */   public ActionAnalysis()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ActionAnalysis(CharStream input)
/*     */   {
/*  56 */     this(input, new RecognizerSharedState());
/*     */   }
/*     */   public ActionAnalysis(CharStream input, RecognizerSharedState state) {
/*  59 */     super(input, state);
/*     */   }
/*     */   public String getGrammarFileName() {
/*  62 */     return "org/antlr/grammar/v3/ActionAnalysis.g";
/*     */   }
/*     */   public org.antlr.runtime.Token nextToken() {
/*     */     while (true) {
/*  66 */       if (this.input.LA(1) == -1) {
/*  67 */         return org.antlr.runtime.Token.EOF_TOKEN;
/*     */       }
/*  69 */       this.state.token = null;
/*  70 */       this.state.channel = 0;
/*  71 */       this.state.tokenStartCharIndex = this.input.index();
/*  72 */       this.state.tokenStartCharPositionInLine = this.input.getCharPositionInLine();
/*  73 */       this.state.tokenStartLine = this.input.getLine();
/*  74 */       this.state.text = null;
/*     */       try {
/*  76 */         int m = this.input.mark();
/*  77 */         this.state.backtracking = 1;
/*  78 */         this.state.failed = false;
/*  79 */         mTokens();
/*  80 */         this.state.backtracking = 0;
/*     */ 
/*  82 */         if (this.state.failed) {
/*  83 */           this.input.rewind(m);
/*  84 */           this.input.consume();
/*     */         }
/*     */         else {
/*  87 */           emit();
/*  88 */           return this.state.token;
/*     */         }
/*     */       }
/*     */       catch (RecognitionException re)
/*     */       {
/*  93 */         reportError(re);
/*  94 */         recover(re);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void memoize(IntStream input, int ruleIndex, int ruleStartIndex)
/*     */   {
/* 103 */     if (this.state.backtracking > 1) super.memoize(input, ruleIndex, ruleStartIndex); 
/*     */   }
/*     */ 
/*     */   public boolean alreadyParsedRule(IntStream input, int ruleIndex)
/*     */   {
/* 107 */     if (this.state.backtracking > 1) return super.alreadyParsedRule(input, ruleIndex);
/* 108 */     return false;
/*     */   }
/*     */   public final void mX_Y() throws RecognitionException {
/*     */     try {
/* 112 */       int _type = 5;
/* 113 */       int _channel = 0;
/* 114 */       CommonToken x = null;
/* 115 */       CommonToken y = null;
/*     */ 
/* 120 */       match(36); if (this.state.failed) return;
/* 121 */       int xStart48 = getCharIndex();
/* 122 */       mID(); if (this.state.failed) return;
/* 123 */       x = new CommonToken(this.input, 0, 0, xStart48, getCharIndex() - 1);
/* 124 */       match(46); if (this.state.failed) return;
/* 125 */       int yStart54 = getCharIndex();
/* 126 */       mID(); if (this.state.failed) return;
/* 127 */       y = new CommonToken(this.input, 0, 0, yStart54, getCharIndex() - 1);
/* 128 */       if (this.enclosingRule == null) {
/* 129 */         if (this.state.backtracking > 0) { this.state.failed = true; return; }
/* 130 */         throw new FailedPredicateException(this.input, "X_Y", "enclosingRule!=null");
/*     */       }
/* 132 */       if (this.state.backtracking == 1)
/*     */       {
/* 134 */         AttributeScope scope = null;
/* 135 */         String refdRuleName = null;
/* 136 */         if ((x != null ? x.getText() : null).equals(this.enclosingRule.name))
/*     */         {
/* 138 */           refdRuleName = x != null ? x.getText() : null;
/* 139 */           scope = this.enclosingRule.getLocalAttributeScope(y != null ? y.getText() : null);
/*     */         }
/* 141 */         else if (this.enclosingRule.getRuleLabel(x != null ? x.getText() : null) != null)
/*     */         {
/* 143 */           Grammar.LabelElementPair pair = this.enclosingRule.getRuleLabel(x != null ? x.getText() : null);
/* 144 */           pair.actionReferencesLabel = true;
/* 145 */           refdRuleName = pair.referencedRuleName;
/* 146 */           Rule refdRule = this.grammar.getRule(refdRuleName);
/* 147 */           if (refdRule != null) {
/* 148 */             scope = refdRule.getLocalAttributeScope(y != null ? y.getText() : null);
/*     */           }
/*     */         }
/* 151 */         else if (this.enclosingRule.getRuleRefsInAlt(x.getText(), this.outerAltNum) != null)
/*     */         {
/* 153 */           refdRuleName = x != null ? x.getText() : null;
/* 154 */           Rule refdRule = this.grammar.getRule(refdRuleName);
/* 155 */           if (refdRule != null) {
/* 156 */             scope = refdRule.getLocalAttributeScope(y != null ? y.getText() : null);
/*     */           }
/*     */         }
/* 159 */         if ((scope != null) && ((scope.isPredefinedRuleScope) || (scope.isPredefinedLexerRuleScope)))
/*     */         {
/* 162 */           this.grammar.referenceRuleLabelPredefinedAttribute(refdRuleName);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 170 */       this.state.type = _type;
/* 171 */       this.state.channel = _channel;
/*     */     }
/*     */     finally
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void mX() throws RecognitionException
/*     */   {
/*     */     try {
/* 181 */       int _type = 6;
/* 182 */       int _channel = 0;
/* 183 */       CommonToken x = null;
/*     */ 
/* 188 */       match(36); if (this.state.failed) return;
/* 189 */       int xStart76 = getCharIndex();
/* 190 */       mID(); if (this.state.failed) return;
/* 191 */       x = new CommonToken(this.input, 0, 0, xStart76, getCharIndex() - 1);
/* 192 */       if (this.enclosingRule != null) { if (this.enclosingRule.getRuleLabel(x != null ? x.getText() : null) != null); } else {
/* 193 */         if (this.state.backtracking > 0) { this.state.failed = true; return; }
/* 194 */         throw new FailedPredicateException(this.input, "X", "enclosingRule!=null && enclosingRule.getRuleLabel($x.text)!=null");
/*     */       }
/* 196 */       if (this.state.backtracking == 1)
/*     */       {
/* 198 */         Grammar.LabelElementPair pair = this.enclosingRule.getRuleLabel(x != null ? x.getText() : null);
/* 199 */         pair.actionReferencesLabel = true;
/*     */       }
/*     */ 
/* 205 */       this.state.type = _type;
/* 206 */       this.state.channel = _channel;
/*     */     }
/*     */     finally
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void mY() throws RecognitionException
/*     */   {
/*     */     try {
/* 216 */       int _type = 7;
/* 217 */       int _channel = 0;
/* 218 */       CommonToken ID1 = null;
/*     */ 
/* 223 */       match(36); if (this.state.failed) return;
/* 224 */       int ID1Start97 = getCharIndex();
/* 225 */       mID(); if (this.state.failed) return;
/* 226 */       ID1 = new CommonToken(this.input, 0, 0, ID1Start97, getCharIndex() - 1);
/* 227 */       if (this.enclosingRule != null) { if (this.enclosingRule.getLocalAttributeScope(ID1 != null ? ID1.getText() : null) != null); } else {
/* 228 */         if (this.state.backtracking > 0) { this.state.failed = true; return; }
/* 229 */         throw new FailedPredicateException(this.input, "Y", "enclosingRule!=null && enclosingRule.getLocalAttributeScope($ID.text)!=null");
/*     */       }
/* 231 */       if (this.state.backtracking == 1)
/*     */       {
/* 233 */         AttributeScope scope = this.enclosingRule.getLocalAttributeScope(ID1 != null ? ID1.getText() : null);
/* 234 */         if ((scope != null) && ((scope.isPredefinedRuleScope) || (scope.isPredefinedLexerRuleScope)))
/*     */         {
/* 237 */           this.grammar.referenceRuleLabelPredefinedAttribute(this.enclosingRule.name);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 245 */       this.state.type = _type;
/* 246 */       this.state.channel = _channel;
/*     */     }
/*     */     finally
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void mID()
/*     */     throws RecognitionException
/*     */   {
/*     */     try
/*     */     {
/* 259 */       if (((this.input.LA(1) >= 65) && (this.input.LA(1) <= 90)) || (this.input.LA(1) == 95) || ((this.input.LA(1) >= 97) && (this.input.LA(1) <= 122))) {
/* 260 */         this.input.consume();
/* 261 */         this.state.failed = false;
/*     */       }
/*     */       else {
/* 264 */         if (this.state.backtracking > 0) { this.state.failed = true; return; }
/* 265 */         MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 266 */         recover(mse);
/* 267 */         throw mse;
/*     */       }
/*     */ 
/*     */       while (true)
/*     */       {
/* 272 */         int alt1 = 2;
/* 273 */         switch (this.input.LA(1))
/*     */         {
/*     */         case 48:
/*     */         case 49:
/*     */         case 50:
/*     */         case 51:
/*     */         case 52:
/*     */         case 53:
/*     */         case 54:
/*     */         case 55:
/*     */         case 56:
/*     */         case 57:
/*     */         case 65:
/*     */         case 66:
/*     */         case 67:
/*     */         case 68:
/*     */         case 69:
/*     */         case 70:
/*     */         case 71:
/*     */         case 72:
/*     */         case 73:
/*     */         case 74:
/*     */         case 75:
/*     */         case 76:
/*     */         case 77:
/*     */         case 78:
/*     */         case 79:
/*     */         case 80:
/*     */         case 81:
/*     */         case 82:
/*     */         case 83:
/*     */         case 84:
/*     */         case 85:
/*     */         case 86:
/*     */         case 87:
/*     */         case 88:
/*     */         case 89:
/*     */         case 90:
/*     */         case 95:
/*     */         case 97:
/*     */         case 98:
/*     */         case 99:
/*     */         case 100:
/*     */         case 101:
/*     */         case 102:
/*     */         case 103:
/*     */         case 104:
/*     */         case 105:
/*     */         case 106:
/*     */         case 107:
/*     */         case 108:
/*     */         case 109:
/*     */         case 110:
/*     */         case 111:
/*     */         case 112:
/*     */         case 113:
/*     */         case 114:
/*     */         case 115:
/*     */         case 116:
/*     */         case 117:
/*     */         case 118:
/*     */         case 119:
/*     */         case 120:
/*     */         case 121:
/*     */         case 122:
/* 338 */           alt1 = 1;
/*     */         case 58:
/*     */         case 59:
/*     */         case 60:
/*     */         case 61:
/*     */         case 62:
/*     */         case 63:
/*     */         case 64:
/*     */         case 91:
/*     */         case 92:
/*     */         case 93:
/*     */         case 94:
/* 344 */         case 96: } switch (alt1)
/*     */         {
/*     */         case 1:
/* 348 */           if (((this.input.LA(1) >= 48) && (this.input.LA(1) <= 57)) || ((this.input.LA(1) >= 65) && (this.input.LA(1) <= 90)) || (this.input.LA(1) == 95) || ((this.input.LA(1) >= 97) && (this.input.LA(1) <= 122))) {
/* 349 */             this.input.consume();
/* 350 */             this.state.failed = false;
/*     */           }
/*     */           else {
/* 353 */             if (this.state.backtracking > 0) { this.state.failed = true; return; }
/* 354 */             MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 355 */             recover(mse);
/* 356 */             throw mse;
/*     */           }
/*     */ 
/*     */           break;
/*     */         default:
/* 363 */           return;
/*     */         }
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public void mTokens()
/*     */     throws RecognitionException
/*     */   {
/* 378 */     int alt2 = 3;
/* 379 */     switch (this.input.LA(1))
/*     */     {
/*     */     case 36:
/* 382 */       int LA2_1 = this.input.LA(2);
/*     */ 
/* 384 */       if (synpred1_ActionAnalysis()) {
/* 385 */         alt2 = 1;
/*     */       }
/* 387 */       else if (synpred2_ActionAnalysis()) {
/* 388 */         alt2 = 2;
/*     */       }
/*     */       else {
/* 391 */         alt2 = 3;
/*     */       }
/*     */ 
/* 401 */       break;
/*     */     default:
/* 403 */       if (this.state.backtracking > 0) { this.state.failed = true; return; }
/* 404 */       NoViableAltException nvae = new NoViableAltException("", 2, 0, this.input);
/*     */ 
/* 407 */       throw nvae;
/*     */     }
/*     */ 
/* 410 */     switch (alt2)
/*     */     {
/*     */     case 1:
/* 414 */       mX_Y(); if (this.state.failed);
/*     */       break;
/*     */     case 2:
/* 421 */       mX(); if (this.state.failed);
/*     */       break;
/*     */     case 3:
/* 428 */       mY(); if (this.state.failed);
/*     */       break;
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void synpred1_ActionAnalysis_fragment()
/*     */     throws RecognitionException
/*     */   {
/* 442 */     mX_Y(); if (this.state.failed);
/*     */   }
/*     */ 
/*     */   public final void synpred2_ActionAnalysis_fragment()
/*     */     throws RecognitionException
/*     */   {
/* 453 */     mX(); if (this.state.failed);
/*     */   }
/*     */ 
/*     */   public final boolean synpred2_ActionAnalysis()
/*     */   {
/* 460 */     this.state.backtracking += 1;
/* 461 */     int start = this.input.mark();
/*     */     try {
/* 463 */       synpred2_ActionAnalysis_fragment();
/*     */     } catch (RecognitionException re) {
/* 465 */       System.err.println("impossible: " + re);
/*     */     }
/* 467 */     boolean success = !this.state.failed;
/* 468 */     this.input.rewind(start);
/* 469 */     this.state.backtracking -= 1;
/* 470 */     this.state.failed = false;
/* 471 */     return success;
/*     */   }
/*     */   public final boolean synpred1_ActionAnalysis() {
/* 474 */     this.state.backtracking += 1;
/* 475 */     int start = this.input.mark();
/*     */     try {
/* 477 */       synpred1_ActionAnalysis_fragment();
/*     */     } catch (RecognitionException re) {
/* 479 */       System.err.println("impossible: " + re);
/*     */     }
/* 481 */     boolean success = !this.state.failed;
/* 482 */     this.input.rewind(start);
/* 483 */     this.state.backtracking -= 1;
/* 484 */     this.state.failed = false;
/* 485 */     return success;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.grammar.v3.ActionAnalysis
 * JD-Core Version:    0.6.2
 */