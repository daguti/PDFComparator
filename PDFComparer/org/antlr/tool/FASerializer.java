/*     */ package org.antlr.tool;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.antlr.analysis.DFAState;
/*     */ import org.antlr.analysis.Label;
/*     */ import org.antlr.analysis.RuleClosureTransition;
/*     */ import org.antlr.analysis.SemanticContext;
/*     */ import org.antlr.analysis.State;
/*     */ import org.antlr.analysis.Transition;
/*     */ import org.antlr.codegen.CodeGenerator;
/*     */ import org.antlr.misc.Utils;
/*     */ import org.antlr.stringtemplate.StringTemplate;
/*     */ 
/*     */ public class FASerializer
/*     */ {
/*     */   protected Set markedStates;
/*  49 */   protected int stateCounter = 0;
/*     */   protected Map stateNumberTranslator;
/*     */   protected Grammar grammar;
/*     */ 
/*     */   public FASerializer(Grammar grammar)
/*     */   {
/*  61 */     this.grammar = grammar;
/*     */   }
/*     */ 
/*     */   public String serialize(State s) {
/*  65 */     if (s == null) {
/*  66 */       return "<no automaton>";
/*     */     }
/*  68 */     return serialize(s, true);
/*     */   }
/*     */ 
/*     */   public String serialize(State s, boolean renumber)
/*     */   {
/*  80 */     this.markedStates = new HashSet();
/*  81 */     this.stateCounter = 0;
/*  82 */     if (renumber) {
/*  83 */       this.stateNumberTranslator = new HashMap();
/*  84 */       walkFANormalizingStateNumbers(s);
/*     */     }
/*  86 */     List lines = new ArrayList();
/*  87 */     if (s.getNumberOfTransitions() > 0) {
/*  88 */       walkSerializingFA(lines, s);
/*     */     }
/*     */     else
/*     */     {
/*  92 */       String s0 = getStateString(0, s);
/*  93 */       lines.add(s0 + "\n");
/*     */     }
/*  95 */     StringBuffer buf = new StringBuffer(0);
/*     */ 
/*  98 */     Collections.sort(lines);
/*  99 */     for (int i = 0; i < lines.size(); i++) {
/* 100 */       String line = (String)lines.get(i);
/* 101 */       buf.append(line);
/*     */     }
/* 103 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   protected void walkFANormalizingStateNumbers(State s)
/*     */   {
/* 111 */     if (s == null) {
/* 112 */       ErrorManager.internalError("null state s");
/* 113 */       return;
/*     */     }
/* 115 */     if (this.stateNumberTranslator.get(s) != null) {
/* 116 */       return;
/*     */     }
/*     */ 
/* 119 */     this.stateNumberTranslator.put(s, Utils.integer(this.stateCounter));
/* 120 */     this.stateCounter += 1;
/*     */ 
/* 123 */     for (int i = 0; i < s.getNumberOfTransitions(); i++) {
/* 124 */       Transition edge = s.transition(i);
/* 125 */       walkFANormalizingStateNumbers(edge.target);
/*     */ 
/* 129 */       if ((edge instanceof RuleClosureTransition))
/* 130 */         walkFANormalizingStateNumbers(((RuleClosureTransition)edge).followState);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void walkSerializingFA(List lines, State s)
/*     */   {
/* 136 */     if (this.markedStates.contains(s)) {
/* 137 */       return;
/*     */     }
/*     */ 
/* 140 */     this.markedStates.add(s);
/*     */ 
/* 142 */     int normalizedStateNumber = s.stateNumber;
/* 143 */     if (this.stateNumberTranslator != null) {
/* 144 */       Integer normalizedStateNumberI = (Integer)this.stateNumberTranslator.get(s);
/* 145 */       normalizedStateNumber = normalizedStateNumberI.intValue();
/*     */     }
/*     */ 
/* 148 */     String stateStr = getStateString(normalizedStateNumber, s);
/*     */ 
/* 151 */     for (int i = 0; i < s.getNumberOfTransitions(); i++) {
/* 152 */       Transition edge = s.transition(i);
/* 153 */       StringBuffer buf = new StringBuffer();
/* 154 */       buf.append(stateStr);
/* 155 */       if (edge.isAction()) {
/* 156 */         buf.append("-{}->");
/*     */       }
/* 158 */       else if (edge.isEpsilon()) {
/* 159 */         buf.append("->");
/*     */       }
/* 161 */       else if (edge.isSemanticPredicate()) {
/* 162 */         buf.append("-{" + edge.label.getSemanticContext() + "}?->");
/*     */       }
/*     */       else {
/* 165 */         String predsStr = "";
/* 166 */         if ((edge.target instanceof DFAState))
/*     */         {
/* 168 */           SemanticContext preds = ((DFAState)edge.target).getGatedPredicatesInNFAConfigurations();
/*     */ 
/* 170 */           if (preds != null) {
/* 171 */             predsStr = "&&{" + preds.genExpr(this.grammar.generator, this.grammar.generator.getTemplates(), null).toString() + "}?";
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 177 */         buf.append("-" + edge.label.toString(this.grammar) + predsStr + "->");
/*     */       }
/*     */ 
/* 180 */       int normalizedTargetStateNumber = edge.target.stateNumber;
/* 181 */       if (this.stateNumberTranslator != null) {
/* 182 */         Integer normalizedTargetStateNumberI = (Integer)this.stateNumberTranslator.get(edge.target);
/*     */ 
/* 184 */         normalizedTargetStateNumber = normalizedTargetStateNumberI.intValue();
/*     */       }
/* 186 */       buf.append(getStateString(normalizedTargetStateNumber, edge.target));
/* 187 */       buf.append("\n");
/* 188 */       lines.add(buf.toString());
/*     */ 
/* 191 */       walkSerializingFA(lines, edge.target);
/*     */ 
/* 196 */       if ((edge instanceof RuleClosureTransition))
/* 197 */         walkSerializingFA(lines, ((RuleClosureTransition)edge).followState);
/*     */     }
/*     */   }
/*     */ 
/*     */   private String getStateString(int n, State s)
/*     */   {
/* 204 */     String stateStr = ".s" + n;
/* 205 */     if (s.isAcceptState()) {
/* 206 */       if ((s instanceof DFAState)) {
/* 207 */         stateStr = ":s" + n + "=>" + ((DFAState)s).getUniquelyPredictedAlt();
/*     */       }
/*     */       else {
/* 210 */         stateStr = ":s" + n;
/*     */       }
/*     */     }
/* 213 */     return stateStr;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.tool.FASerializer
 * JD-Core Version:    0.6.2
 */