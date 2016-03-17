/*     */ package org.antlr.tool;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.antlr.Tool;
/*     */ import org.antlr.analysis.DFAState;
/*     */ import org.antlr.analysis.Label;
/*     */ import org.antlr.analysis.NFAConfiguration;
/*     */ import org.antlr.analysis.NFAState;
/*     */ import org.antlr.analysis.RuleClosureTransition;
/*     */ import org.antlr.analysis.SemanticContext;
/*     */ import org.antlr.analysis.State;
/*     */ import org.antlr.analysis.Transition;
/*     */ import org.antlr.codegen.CodeGenerator;
/*     */ import org.antlr.misc.Utils;
/*     */ import org.antlr.stringtemplate.StringTemplate;
/*     */ import org.antlr.stringtemplate.StringTemplateGroup;
/*     */ import org.antlr.stringtemplate.language.AngleBracketTemplateLexer;
/*     */ 
/*     */ public class DOTGenerator
/*     */ {
/*     */   public static final boolean STRIP_NONREDUCED_STATES = false;
/*  44 */   protected String arrowhead = "normal";
/*  45 */   protected String rankdir = "LR";
/*     */ 
/*  48 */   public static StringTemplateGroup stlib = new StringTemplateGroup("toollib", AngleBracketTemplateLexer.class);
/*     */ 
/*  55 */   protected Set markedStates = null;
/*     */   protected Grammar grammar;
/*     */ 
/*     */   public DOTGenerator(Grammar grammar)
/*     */   {
/*  61 */     this.grammar = grammar;
/*     */   }
/*     */ 
/*     */   public String getDOT(State startState)
/*     */   {
/*  69 */     if (startState == null) {
/*  70 */       return null;
/*     */     }
/*     */ 
/*  73 */     StringTemplate dot = null;
/*  74 */     this.markedStates = new HashSet();
/*  75 */     if ((startState instanceof DFAState)) {
/*  76 */       dot = stlib.getInstanceOf("org/antlr/tool/templates/dot/dfa");
/*  77 */       dot.setAttribute("startState", Utils.integer(startState.stateNumber));
/*     */ 
/*  79 */       dot.setAttribute("useBox", Boolean.valueOf(Tool.internalOption_ShowNFAConfigsInDFA));
/*     */ 
/*  81 */       walkCreatingDFADOT(dot, (DFAState)startState);
/*     */     }
/*     */     else {
/*  84 */       dot = stlib.getInstanceOf("org/antlr/tool/templates/dot/nfa");
/*  85 */       dot.setAttribute("startState", Utils.integer(startState.stateNumber));
/*     */ 
/*  87 */       walkRuleNFACreatingDOT(dot, startState);
/*     */     }
/*  89 */     dot.setAttribute("rankdir", this.rankdir);
/*  90 */     return dot.toString();
/*     */   }
/*     */ 
/*     */   protected void walkCreatingDFADOT(StringTemplate dot, DFAState s)
/*     */   {
/* 115 */     if (this.markedStates.contains(Utils.integer(s.stateNumber))) {
/* 116 */       return;
/*     */     }
/*     */ 
/* 119 */     this.markedStates.add(Utils.integer(s.stateNumber));
/*     */     StringTemplate st;
/*     */     StringTemplate st;
/* 123 */     if (s.isAcceptState()) {
/* 124 */       st = stlib.getInstanceOf("org/antlr/tool/templates/dot/stopstate");
/*     */     }
/*     */     else {
/* 127 */       st = stlib.getInstanceOf("org/antlr/tool/templates/dot/state");
/*     */     }
/* 129 */     st.setAttribute("name", getStateLabel(s));
/* 130 */     dot.setAttribute("states", st);
/*     */ 
/* 133 */     for (int i = 0; i < s.getNumberOfTransitions(); i++) {
/* 134 */       Transition edge = s.transition(i);
/*     */ 
/* 146 */       st = stlib.getInstanceOf("org/antlr/tool/templates/dot/edge");
/* 147 */       st.setAttribute("label", getEdgeLabel(edge));
/* 148 */       st.setAttribute("src", getStateLabel(s));
/* 149 */       st.setAttribute("target", getStateLabel(edge.target));
/* 150 */       st.setAttribute("arrowhead", this.arrowhead);
/* 151 */       dot.setAttribute("edges", st);
/* 152 */       walkCreatingDFADOT(dot, (DFAState)edge.target);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void walkRuleNFACreatingDOT(StringTemplate dot, State s)
/*     */   {
/* 165 */     if (this.markedStates.contains(s)) {
/* 166 */       return;
/*     */     }
/*     */ 
/* 169 */     this.markedStates.add(s);
/*     */     StringTemplate stateST;
/*     */     StringTemplate stateST;
/* 173 */     if (s.isAcceptState()) {
/* 174 */       stateST = stlib.getInstanceOf("org/antlr/tool/templates/dot/stopstate");
/*     */     }
/*     */     else {
/* 177 */       stateST = stlib.getInstanceOf("org/antlr/tool/templates/dot/state");
/*     */     }
/* 179 */     stateST.setAttribute("name", getStateLabel(s));
/* 180 */     dot.setAttribute("states", stateST);
/*     */ 
/* 182 */     if (s.isAcceptState()) {
/* 183 */       return;
/*     */     }
/*     */ 
/* 188 */     if (((NFAState)s).isDecisionState()) {
/* 189 */       GrammarAST n = ((NFAState)s).associatedASTNode;
/* 190 */       if ((n != null) && (n.getType() != 19)) {
/* 191 */         StringTemplate rankST = stlib.getInstanceOf("org/antlr/tool/templates/dot/decision-rank");
/* 192 */         NFAState alt = (NFAState)s;
/* 193 */         while (alt != null) {
/* 194 */           rankST.setAttribute("states", getStateLabel(alt));
/* 195 */           if (alt.transition[1] != null) {
/* 196 */             alt = (NFAState)alt.transition[1].target;
/*     */           }
/*     */           else {
/* 199 */             alt = null;
/*     */           }
/*     */         }
/* 202 */         dot.setAttribute("decisionRanks", rankST);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 207 */     StringTemplate edgeST = null;
/* 208 */     for (int i = 0; i < s.getNumberOfTransitions(); i++) {
/* 209 */       Transition edge = s.transition(i);
/* 210 */       if ((edge instanceof RuleClosureTransition)) {
/* 211 */         RuleClosureTransition rr = (RuleClosureTransition)edge;
/*     */ 
/* 213 */         edgeST = stlib.getInstanceOf("org/antlr/tool/templates/dot/edge");
/* 214 */         if (rr.rule.grammar != this.grammar) {
/* 215 */           edgeST.setAttribute("label", "<" + rr.rule.grammar.name + "." + rr.rule.name + ">");
/*     */         }
/*     */         else {
/* 218 */           edgeST.setAttribute("label", "<" + rr.rule.name + ">");
/*     */         }
/* 220 */         edgeST.setAttribute("src", getStateLabel(s));
/* 221 */         edgeST.setAttribute("target", getStateLabel(rr.followState));
/* 222 */         edgeST.setAttribute("arrowhead", this.arrowhead);
/* 223 */         dot.setAttribute("edges", edgeST);
/* 224 */         walkRuleNFACreatingDOT(dot, rr.followState);
/*     */       }
/*     */       else {
/* 227 */         if (edge.isAction()) {
/* 228 */           edgeST = stlib.getInstanceOf("org/antlr/tool/templates/dot/action-edge");
/*     */         }
/* 230 */         else if (edge.isEpsilon()) {
/* 231 */           edgeST = stlib.getInstanceOf("org/antlr/tool/templates/dot/epsilon-edge");
/*     */         }
/*     */         else {
/* 234 */           edgeST = stlib.getInstanceOf("org/antlr/tool/templates/dot/edge");
/*     */         }
/* 236 */         edgeST.setAttribute("label", getEdgeLabel(edge));
/* 237 */         edgeST.setAttribute("src", getStateLabel(s));
/* 238 */         edgeST.setAttribute("target", getStateLabel(edge.target));
/* 239 */         edgeST.setAttribute("arrowhead", this.arrowhead);
/* 240 */         dot.setAttribute("edges", edgeST);
/* 241 */         walkRuleNFACreatingDOT(dot, edge.target);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected String getEdgeLabel(Transition edge)
/*     */   {
/* 286 */     String label = edge.label.toString(this.grammar);
/* 287 */     label = Utils.replace(label, "\\", "\\\\");
/* 288 */     label = Utils.replace(label, "\"", "\\\"");
/* 289 */     label = Utils.replace(label, "\n", "\\\\n");
/* 290 */     label = Utils.replace(label, "\r", "");
/* 291 */     if (label.equals("<EPSILON>")) {
/* 292 */       label = "e";
/*     */     }
/* 294 */     State target = edge.target;
/* 295 */     if ((!edge.isSemanticPredicate()) && ((target instanceof DFAState)))
/*     */     {
/* 297 */       SemanticContext preds = ((DFAState)target).getGatedPredicatesInNFAConfigurations();
/*     */ 
/* 299 */       if (preds != null) {
/* 300 */         String predsStr = "";
/* 301 */         predsStr = "&&{" + preds.genExpr(this.grammar.generator, this.grammar.generator.getTemplates(), null).toString() + "}?";
/*     */ 
/* 305 */         label = label + predsStr;
/*     */       }
/*     */     }
/* 308 */     return label;
/*     */   }
/*     */ 
/*     */   protected String getStateLabel(State s) {
/* 312 */     if (s == null) {
/* 313 */       return "null";
/*     */     }
/* 315 */     String stateLabel = String.valueOf(s.stateNumber);
/* 316 */     if ((s instanceof DFAState)) {
/* 317 */       StringBuffer buf = new StringBuffer(250);
/* 318 */       buf.append('s');
/* 319 */       buf.append(s.stateNumber);
/* 320 */       if (Tool.internalOption_ShowNFAConfigsInDFA) {
/* 321 */         if (((s instanceof DFAState)) && 
/* 322 */           (((DFAState)s).abortedDueToRecursionOverflow)) {
/* 323 */           buf.append("\\n");
/* 324 */           buf.append("abortedDueToRecursionOverflow");
/*     */         }
/*     */ 
/* 327 */         Set alts = ((DFAState)s).getAltSet();
/* 328 */         if (alts != null) {
/* 329 */           buf.append("\\n");
/*     */ 
/* 331 */           List altList = new ArrayList();
/* 332 */           altList.addAll(alts);
/* 333 */           Collections.sort(altList);
/* 334 */           Set configurations = ((DFAState)s).nfaConfigurations;
/* 335 */           for (int altIndex = 0; altIndex < altList.size(); altIndex++) {
/* 336 */             Integer altI = (Integer)altList.get(altIndex);
/* 337 */             int alt = altI.intValue();
/* 338 */             if (altIndex > 0) {
/* 339 */               buf.append("\\n");
/*     */             }
/* 341 */             buf.append("alt");
/* 342 */             buf.append(alt);
/* 343 */             buf.append(':');
/*     */ 
/* 346 */             List configsInAlt = new ArrayList();
/* 347 */             for (Iterator it = configurations.iterator(); it.hasNext(); ) {
/* 348 */               NFAConfiguration c = (NFAConfiguration)it.next();
/* 349 */               if (c.alt == alt)
/* 350 */                 configsInAlt.add(c);
/*     */             }
/* 352 */             int n = 0;
/* 353 */             for (int cIndex = 0; cIndex < configsInAlt.size(); cIndex++) {
/* 354 */               NFAConfiguration c = (NFAConfiguration)configsInAlt.get(cIndex);
/*     */ 
/* 356 */               n++;
/* 357 */               buf.append(c.toString(false));
/* 358 */               if (cIndex + 1 < configsInAlt.size()) {
/* 359 */                 buf.append(", ");
/*     */               }
/* 361 */               if ((n % 5 == 0) && (configsInAlt.size() - cIndex > 3)) {
/* 362 */                 buf.append("\\n");
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 368 */       stateLabel = buf.toString();
/*     */     }
/* 370 */     if (((s instanceof NFAState)) && (((NFAState)s).isDecisionState())) {
/* 371 */       stateLabel = stateLabel + ",d=" + ((NFAState)s).getDecisionNumber();
/*     */ 
/* 373 */       if (((NFAState)s).endOfBlockStateNumber != -1) {
/* 374 */         stateLabel = stateLabel + ",eob=" + ((NFAState)s).endOfBlockStateNumber;
/*     */       }
/*     */     }
/* 377 */     else if (((s instanceof NFAState)) && (((NFAState)s).endOfBlockStateNumber != -1))
/*     */     {
/* 380 */       NFAState n = (NFAState)s;
/* 381 */       stateLabel = stateLabel + ",eob=" + n.endOfBlockStateNumber;
/*     */     }
/* 383 */     else if (((s instanceof DFAState)) && (((DFAState)s).isAcceptState())) {
/* 384 */       stateLabel = stateLabel + "=>" + ((DFAState)s).getUniquelyPredictedAlt();
/*     */     }
/*     */ 
/* 387 */     return '"' + stateLabel + '"';
/*     */   }
/*     */ 
/*     */   public String getArrowheadType() {
/* 391 */     return this.arrowhead;
/*     */   }
/*     */ 
/*     */   public void setArrowheadType(String arrowhead) {
/* 395 */     this.arrowhead = arrowhead;
/*     */   }
/*     */ 
/*     */   public String getRankdir() {
/* 399 */     return this.rankdir;
/*     */   }
/*     */ 
/*     */   public void setRankdir(String rankdir) {
/* 403 */     this.rankdir = rankdir;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.tool.DOTGenerator
 * JD-Core Version:    0.6.2
 */