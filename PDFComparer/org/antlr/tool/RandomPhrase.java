/*     */ package org.antlr.tool;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileReader;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.Stack;
/*     */ import org.antlr.Tool;
/*     */ import org.antlr.analysis.Label;
/*     */ import org.antlr.analysis.NFAState;
/*     */ import org.antlr.analysis.RuleClosureTransition;
/*     */ import org.antlr.analysis.Transition;
/*     */ import org.antlr.misc.IntervalSet;
/*     */ import org.antlr.misc.Utils;
/*     */ 
/*     */ public class RandomPhrase
/*     */ {
/*     */   public static final boolean debug = false;
/*     */   protected static Random random;
/*     */ 
/*     */   protected static void randomPhrase(Grammar g, List<Integer> tokenTypes, String startRule)
/*     */   {
/*  70 */     NFAState state = g.getRuleStartState(startRule);
/*  71 */     NFAState stopState = g.getRuleStopState(startRule);
/*     */ 
/*  73 */     Stack ruleInvocationStack = new Stack();
/*     */ 
/*  75 */     while ((state != stopState) || (ruleInvocationStack.size() != 0))
/*     */     {
/*  79 */       if (state.getNumberOfTransitions() == 0)
/*     */       {
/*  81 */         return;
/*     */       }
/*     */ 
/*  84 */       if (state.isAcceptState()) {
/*  85 */         NFAState invokingState = (NFAState)ruleInvocationStack.pop();
/*     */ 
/*  88 */         RuleClosureTransition invokingTransition = (RuleClosureTransition)invokingState.transition[0];
/*     */ 
/*  91 */         state = invokingTransition.followState;
/*     */       }
/*  94 */       else if (state.getNumberOfTransitions() == 1)
/*     */       {
/*  96 */         Transition t0 = state.transition[0];
/*  97 */         if ((t0 instanceof RuleClosureTransition)) {
/*  98 */           ruleInvocationStack.push(state);
/*     */         }
/* 103 */         else if ((t0.label.isSet()) || (t0.label.isAtom())) {
/* 104 */           tokenTypes.add(getTokenType(t0.label));
/*     */         }
/* 106 */         state = (NFAState)t0.target;
/*     */       }
/*     */       else
/*     */       {
/* 110 */         int decisionNumber = state.getDecisionNumber();
/* 111 */         if (decisionNumber == 0) {
/* 112 */           System.out.println("weird: no decision number but a choice node");
/*     */         }
/*     */         else
/*     */         {
/* 116 */           int n = g.getNumberOfAltsForDecisionNFA(state);
/* 117 */           int randomAlt = random.nextInt(n) + 1;
/*     */ 
/* 119 */           NFAState altStartState = g.getNFAStateForAltOfDecision(state, randomAlt);
/*     */ 
/* 121 */           Transition t = altStartState.transition[0];
/* 122 */           state = (NFAState)t.target; } 
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/* 127 */   protected static Integer getTokenType(Label label) { if (label.isSet())
/*     */     {
/* 129 */       IntervalSet typeSet = (IntervalSet)label.getSet();
/* 130 */       int randomIndex = random.nextInt(typeSet.size());
/* 131 */       return new Integer(typeSet.get(randomIndex));
/*     */     }
/*     */ 
/* 134 */     return Utils.integer(label.getAtom());
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 141 */     if (args.length < 2) {
/* 142 */       System.err.println("usage: java org.antlr.tool.RandomPhrase grammarfile startrule");
/* 143 */       return;
/*     */     }
/* 145 */     String grammarFileName = args[0];
/* 146 */     String startRule = args[1];
/* 147 */     long seed = System.currentTimeMillis();
/* 148 */     if (args.length == 3) {
/* 149 */       String seedStr = args[2];
/* 150 */       seed = Long.parseLong(seedStr);
/*     */     }
/*     */     try {
/* 153 */       random = new Random(seed);
/*     */ 
/* 155 */       CompositeGrammar composite = new CompositeGrammar();
/* 156 */       Grammar parser = new Grammar(new Tool(), grammarFileName, composite);
/* 157 */       composite.setDelegationRoot(parser);
/*     */ 
/* 159 */       FileReader fr = new FileReader(grammarFileName);
/* 160 */       BufferedReader br = new BufferedReader(fr);
/* 161 */       parser.parseAndBuildAST(br);
/* 162 */       br.close();
/*     */ 
/* 164 */       parser.composite.assignTokenTypes();
/* 165 */       parser.composite.defineGrammarSymbols();
/* 166 */       parser.composite.createNFAs();
/*     */ 
/* 168 */       List leftRecursiveRules = parser.checkAllRulesForLeftRecursion();
/* 169 */       if (leftRecursiveRules.size() > 0) {
/* 170 */         return;
/*     */       }
/*     */ 
/* 173 */       if (parser.getRule(startRule) == null) {
/* 174 */         System.out.println("undefined start rule " + startRule);
/* 175 */         return;
/*     */       }
/*     */ 
/* 178 */       String lexerGrammarText = parser.getLexerGrammar();
/* 179 */       Grammar lexer = new Grammar();
/* 180 */       lexer.importTokenVocabulary(parser);
/* 181 */       lexer.fileName = grammarFileName;
/* 182 */       if (lexerGrammarText != null) {
/* 183 */         lexer.setGrammarContent(lexerGrammarText);
/*     */       }
/*     */       else {
/* 186 */         System.err.println("no lexer grammar found in " + grammarFileName);
/*     */       }
/* 188 */       lexer.buildNFA();
/* 189 */       leftRecursiveRules = lexer.checkAllRulesForLeftRecursion();
/* 190 */       if (leftRecursiveRules.size() > 0) {
/* 191 */         return;
/*     */       }
/*     */ 
/* 195 */       List tokenTypes = new ArrayList(100);
/* 196 */       randomPhrase(parser, tokenTypes, startRule);
/* 197 */       System.out.println("token types=" + tokenTypes);
/* 198 */       for (int i = 0; i < tokenTypes.size(); i++) {
/* 199 */         Integer ttypeI = (Integer)tokenTypes.get(i);
/* 200 */         int ttype = ttypeI.intValue();
/* 201 */         String ttypeDisplayName = parser.getTokenDisplayName(ttype);
/* 202 */         if (Character.isUpperCase(ttypeDisplayName.charAt(0))) {
/* 203 */           List charsInToken = new ArrayList(10);
/* 204 */           randomPhrase(lexer, charsInToken, ttypeDisplayName);
/* 205 */           System.out.print(" ");
/* 206 */           for (int j = 0; j < charsInToken.size(); j++) {
/* 207 */             Integer cI = (Integer)charsInToken.get(j);
/* 208 */             System.out.print((char)cI.intValue());
/*     */           }
/*     */         }
/*     */         else {
/* 212 */           String literal = ttypeDisplayName.substring(1, ttypeDisplayName.length() - 1);
/*     */ 
/* 214 */           System.out.print(" " + literal);
/*     */         }
/*     */       }
/* 217 */       System.out.println();
/*     */     }
/*     */     catch (Error er) {
/* 220 */       System.err.println("Error walking " + grammarFileName + " rule " + startRule + " seed " + seed);
/* 221 */       er.printStackTrace(System.err);
/*     */     }
/*     */     catch (Exception e) {
/* 224 */       System.err.println("Exception walking " + grammarFileName + " rule " + startRule + " seed " + seed);
/* 225 */       e.printStackTrace(System.err);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.tool.RandomPhrase
 * JD-Core Version:    0.6.2
 */