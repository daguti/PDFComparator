/*     */ package org.antlr.analysis;
/*     */ 
/*     */ import org.antlr.tool.ErrorManager;
/*     */ import org.antlr.tool.Grammar;
/*     */ import org.antlr.tool.GrammarAST;
/*     */ import org.antlr.tool.Rule;
/*     */ 
/*     */ public class NFAState extends State
/*     */ {
/*     */   public static final int LOOPBACK = 1;
/*     */   public static final int BLOCK_START = 2;
/*     */   public static final int OPTIONAL_BLOCK_START = 3;
/*     */   public static final int BYPASS = 4;
/*     */   public static final int RIGHT_EDGE_OF_BLOCK = 5;
/*     */   public static final int MAX_TRANSITIONS = 2;
/*  47 */   int numTransitions = 0;
/*  48 */   public Transition[] transition = new Transition[2];
/*     */   public Label incidentEdgeLabel;
/*  57 */   public NFA nfa = null;
/*     */ 
/*  60 */   protected int decisionNumber = 0;
/*     */   public int decisionStateType;
/*     */   public Rule enclosingRule;
/*     */   protected String description;
/*     */   public GrammarAST associatedASTNode;
/* 101 */   protected boolean EOTTargetState = false;
/*     */ 
/* 106 */   public int endOfBlockStateNumber = -1;
/*     */ 
/*     */   public NFAState(NFA nfa) {
/* 109 */     this.nfa = nfa;
/*     */   }
/*     */ 
/*     */   public int getNumberOfTransitions() {
/* 113 */     return this.numTransitions;
/*     */   }
/*     */ 
/*     */   public void addTransition(Transition e) {
/* 117 */     if (e == null) {
/* 118 */       throw new IllegalArgumentException("You can't add a null transition");
/*     */     }
/* 120 */     if (this.numTransitions > this.transition.length) {
/* 121 */       throw new IllegalArgumentException("You can only have " + this.transition.length + " transitions");
/*     */     }
/* 123 */     if (e != null) {
/* 124 */       this.transition[this.numTransitions] = e;
/* 125 */       this.numTransitions += 1;
/*     */ 
/* 128 */       Label label = e.label;
/* 129 */       if ((label.isAtom()) || (label.isSet())) {
/* 130 */         if (((NFAState)e.target).incidentEdgeLabel != null) {
/* 131 */           ErrorManager.internalError("Clobbered incident edge");
/*     */         }
/* 133 */         ((NFAState)e.target).incidentEdgeLabel = e.label;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setTransition0(Transition e)
/*     */   {
/* 142 */     if (e == null) {
/* 143 */       throw new IllegalArgumentException("You can't use a solitary null transition");
/*     */     }
/* 145 */     this.transition[0] = e;
/* 146 */     this.transition[1] = null;
/* 147 */     this.numTransitions = 1;
/*     */   }
/*     */ 
/*     */   public Transition transition(int i) {
/* 151 */     return this.transition[i];
/*     */   }
/*     */ 
/*     */   public int translateDisplayAltToWalkAlt(int displayAlt)
/*     */   {
/* 181 */     NFAState nfaStart = this;
/* 182 */     if ((this.decisionNumber == 0) || (this.decisionStateType == 0)) {
/* 183 */       return displayAlt;
/*     */     }
/* 185 */     int walkAlt = 0;
/*     */ 
/* 195 */     int nAlts = this.nfa.grammar.getNumberOfAltsForDecisionNFA(nfaStart);
/* 196 */     switch (nfaStart.decisionStateType) {
/*     */     case 1:
/* 198 */       walkAlt = displayAlt % nAlts + 1;
/* 199 */       break;
/*     */     case 2:
/*     */     case 3:
/* 202 */       walkAlt = displayAlt;
/* 203 */       break;
/*     */     case 4:
/* 205 */       if (displayAlt == nAlts) {
/* 206 */         walkAlt = 2;
/*     */       }
/*     */       else {
/* 209 */         walkAlt = 1;
/*     */       }
/*     */       break;
/*     */     }
/* 213 */     return walkAlt;
/*     */   }
/*     */ 
/*     */   public void setDecisionASTNode(GrammarAST decisionASTNode)
/*     */   {
/* 222 */     decisionASTNode.setNFAStartState(this);
/* 223 */     this.associatedASTNode = decisionASTNode;
/*     */   }
/*     */ 
/*     */   public String getDescription() {
/* 227 */     return this.description;
/*     */   }
/*     */ 
/*     */   public void setDescription(String description) {
/* 231 */     this.description = description;
/*     */   }
/*     */ 
/*     */   public int getDecisionNumber() {
/* 235 */     return this.decisionNumber;
/*     */   }
/*     */ 
/*     */   public void setDecisionNumber(int decisionNumber) {
/* 239 */     this.decisionNumber = decisionNumber;
/*     */   }
/*     */ 
/*     */   public boolean isEOTTargetState() {
/* 243 */     return this.EOTTargetState;
/*     */   }
/*     */ 
/*     */   public void setEOTTargetState(boolean eot) {
/* 247 */     this.EOTTargetState = eot;
/*     */   }
/*     */ 
/*     */   public boolean isDecisionState() {
/* 251 */     return this.decisionStateType > 0;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 255 */     return String.valueOf(this.stateNumber);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.analysis.NFAState
 * JD-Core Version:    0.6.2
 */