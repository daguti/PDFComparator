/*     */ package org.antlr.codegen;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.antlr.analysis.DFA;
/*     */ import org.antlr.analysis.DFAState;
/*     */ import org.antlr.analysis.Label;
/*     */ import org.antlr.analysis.NFAState;
/*     */ import org.antlr.analysis.SemanticContext;
/*     */ import org.antlr.analysis.Transition;
/*     */ import org.antlr.misc.IntSet;
/*     */ import org.antlr.misc.Utils;
/*     */ import org.antlr.stringtemplate.StringTemplate;
/*     */ import org.antlr.stringtemplate.StringTemplateGroup;
/*     */ 
/*     */ public class ACyclicDFACodeGenerator
/*     */ {
/*     */   protected CodeGenerator parentGenerator;
/*     */ 
/*     */   public ACyclicDFACodeGenerator(CodeGenerator parent)
/*     */   {
/*  41 */     this.parentGenerator = parent;
/*     */   }
/*     */ 
/*     */   public StringTemplate genFixedLookaheadDecision(StringTemplateGroup templates, DFA dfa)
/*     */   {
/*  47 */     return walkFixedDFAGeneratingStateMachine(templates, dfa, dfa.startState, 1);
/*     */   }
/*     */ 
/*     */   protected StringTemplate walkFixedDFAGeneratingStateMachine(StringTemplateGroup templates, DFA dfa, DFAState s, int k)
/*     */   {
/*  57 */     if (s.isAcceptState()) {
/*  58 */       StringTemplate dfaST = templates.getInstanceOf("dfaAcceptState");
/*  59 */       dfaST.setAttribute("alt", Utils.integer(s.getUniquelyPredictedAlt()));
/*  60 */       return dfaST;
/*     */     }
/*     */ 
/*  65 */     String dfaStateName = "dfaState";
/*  66 */     String dfaLoopbackStateName = "dfaLoopbackState";
/*  67 */     String dfaOptionalBlockStateName = "dfaOptionalBlockState";
/*  68 */     String dfaEdgeName = "dfaEdge";
/*  69 */     if (this.parentGenerator.canGenerateSwitch(s)) {
/*  70 */       dfaStateName = "dfaStateSwitch";
/*  71 */       dfaLoopbackStateName = "dfaLoopbackStateSwitch";
/*  72 */       dfaOptionalBlockStateName = "dfaOptionalBlockStateSwitch";
/*  73 */       dfaEdgeName = "dfaEdgeSwitch";
/*     */     }
/*     */ 
/*  76 */     StringTemplate dfaST = templates.getInstanceOf(dfaStateName);
/*  77 */     if (dfa.getNFADecisionStartState().decisionStateType == 1) {
/*  78 */       dfaST = templates.getInstanceOf(dfaLoopbackStateName);
/*     */     }
/*  80 */     else if (dfa.getNFADecisionStartState().decisionStateType == 3) {
/*  81 */       dfaST = templates.getInstanceOf(dfaOptionalBlockStateName);
/*     */     }
/*  83 */     dfaST.setAttribute("k", Utils.integer(k));
/*  84 */     dfaST.setAttribute("stateNumber", Utils.integer(s.stateNumber));
/*  85 */     dfaST.setAttribute("semPredState", Boolean.valueOf(s.isResolvedWithPredicates()));
/*     */ 
/*  95 */     int EOTPredicts = -1;
/*  96 */     DFAState EOTTarget = null;
/*     */ 
/*  98 */     for (int i = 0; i < s.getNumberOfTransitions(); i++) {
/*  99 */       Transition edge = s.transition(i);
/*     */ 
/* 101 */       if (edge.label.getAtom() == -2)
/*     */       {
/* 104 */         EOTTarget = (DFAState)edge.target;
/* 105 */         EOTPredicts = EOTTarget.getUniquelyPredictedAlt();
/*     */       }
/*     */       else
/*     */       {
/* 113 */         StringTemplate edgeST = templates.getInstanceOf(dfaEdgeName);
/*     */ 
/* 115 */         if (edgeST.getFormalArgument("labels") != null) {
/* 116 */           List labels = edge.label.getSet().toList();
/* 117 */           for (int j = 0; j < labels.size(); j++) {
/* 118 */             Integer vI = (Integer)labels.get(j);
/* 119 */             String label = this.parentGenerator.getTokenTypeAsTargetLabel(vI.intValue());
/*     */ 
/* 121 */             labels.set(j, label);
/*     */           }
/* 123 */           edgeST.setAttribute("labels", labels);
/*     */         }
/*     */         else {
/* 126 */           edgeST.setAttribute("labelExpr", this.parentGenerator.genLabelExpr(templates, edge, k));
/*     */         }
/*     */ 
/* 131 */         if (!edge.label.isSemanticPredicate()) {
/* 132 */           DFAState target = (DFAState)edge.target;
/* 133 */           SemanticContext preds = target.getGatedPredicatesInNFAConfigurations();
/*     */ 
/* 135 */           if (preds != null)
/*     */           {
/* 137 */             StringTemplate predST = preds.genExpr(this.parentGenerator, this.parentGenerator.getTemplates(), dfa);
/*     */ 
/* 140 */             edgeST.setAttribute("predicates", predST);
/*     */           }
/*     */         }
/*     */ 
/* 144 */         StringTemplate targetST = walkFixedDFAGeneratingStateMachine(templates, dfa, (DFAState)edge.target, k + 1);
/*     */ 
/* 149 */         edgeST.setAttribute("targetState", targetST);
/* 150 */         dfaST.setAttribute("edges", edgeST);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 158 */     if (EOTPredicts != -1)
/*     */     {
/* 160 */       dfaST.setAttribute("eotPredictsAlt", Utils.integer(EOTPredicts));
/*     */     }
/* 162 */     else if ((EOTTarget != null) && (EOTTarget.getNumberOfTransitions() > 0))
/*     */     {
/* 171 */       for (int i = 0; i < EOTTarget.getNumberOfTransitions(); i++) {
/* 172 */         Transition predEdge = EOTTarget.transition(i);
/* 173 */         StringTemplate edgeST = templates.getInstanceOf(dfaEdgeName);
/* 174 */         edgeST.setAttribute("labelExpr", this.parentGenerator.genSemanticPredicateExpr(templates, predEdge));
/*     */ 
/* 178 */         StringTemplate targetST = walkFixedDFAGeneratingStateMachine(templates, dfa, (DFAState)predEdge.target, k + 1);
/*     */ 
/* 183 */         edgeST.setAttribute("targetState", targetST);
/* 184 */         dfaST.setAttribute("edges", edgeST);
/*     */       }
/*     */     }
/* 187 */     return dfaST;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.codegen.ACyclicDFACodeGenerator
 * JD-Core Version:    0.6.2
 */