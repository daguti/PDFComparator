/*     */ package org.antlr.analysis;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import org.antlr.codegen.CodeGenerator;
/*     */ import org.antlr.codegen.Target;
/*     */ import org.antlr.stringtemplate.StringTemplate;
/*     */ import org.antlr.stringtemplate.StringTemplateGroup;
/*     */ import org.antlr.tool.Grammar;
/*     */ import org.antlr.tool.GrammarAST;
/*     */ 
/*     */ public abstract class SemanticContext
/*     */ {
/*  67 */   public static final SemanticContext EMPTY_SEMANTIC_CONTEXT = new Predicate();
/*     */ 
/*     */   public abstract SemanticContext getGatedPredicateContext();
/*     */ 
/*     */   public abstract StringTemplate genExpr(CodeGenerator paramCodeGenerator, StringTemplateGroup paramStringTemplateGroup, DFA paramDFA);
/*     */ 
/*     */   public abstract boolean hasUserSemanticPredicate();
/*     */ 
/*     */   public abstract boolean isSyntacticPredicate();
/*     */ 
/*     */   public void trackUseOfSyntacticPredicates(Grammar g)
/*     */   {
/*     */   }
/*     */ 
/*     */   public static SemanticContext and(SemanticContext a, SemanticContext b)
/*     */   {
/* 470 */     if ((a == EMPTY_SEMANTIC_CONTEXT) || (a == null)) {
/* 471 */       return b;
/*     */     }
/* 473 */     if ((b == EMPTY_SEMANTIC_CONTEXT) || (b == null)) {
/* 474 */       return a;
/*     */     }
/* 476 */     if (a.equals(b)) {
/* 477 */       return a;
/*     */     }
/*     */ 
/* 480 */     return new AND(a, b);
/*     */   }
/*     */ 
/*     */   public static SemanticContext or(SemanticContext a, SemanticContext b)
/*     */   {
/* 485 */     if ((a == EMPTY_SEMANTIC_CONTEXT) || (a == null)) {
/* 486 */       return b;
/*     */     }
/* 488 */     if ((b == EMPTY_SEMANTIC_CONTEXT) || (b == null)) {
/* 489 */       return a;
/*     */     }
/* 491 */     if ((a instanceof TruePredicate)) {
/* 492 */       return a;
/*     */     }
/* 494 */     if ((b instanceof TruePredicate)) {
/* 495 */       return b;
/*     */     }
/* 497 */     if (((a instanceof NOT)) && ((b instanceof Predicate))) {
/* 498 */       NOT n = (NOT)a;
/*     */ 
/* 500 */       if (n.ctx.equals(b)) {
/* 501 */         return new TruePredicate();
/*     */       }
/*     */     }
/* 504 */     else if (((b instanceof NOT)) && ((a instanceof Predicate))) {
/* 505 */       NOT n = (NOT)b;
/*     */ 
/* 507 */       if (n.ctx.equals(a)) {
/* 508 */         return new TruePredicate();
/*     */       }
/*     */     }
/* 511 */     else if (a.equals(b)) {
/* 512 */       return a;
/*     */     }
/*     */ 
/* 515 */     return new OR(a, b);
/*     */   }
/*     */ 
/*     */   public static SemanticContext not(SemanticContext a) {
/* 519 */     return new NOT(a);
/*     */   }
/*     */ 
/*     */   public static class NOT extends SemanticContext
/*     */   {
/*     */     protected SemanticContext ctx;
/*     */ 
/*     */     public NOT(SemanticContext ctx)
/*     */     {
/* 420 */       this.ctx = ctx;
/*     */     }
/*     */ 
/*     */     public StringTemplate genExpr(CodeGenerator generator, StringTemplateGroup templates, DFA dfa)
/*     */     {
/* 426 */       StringTemplate eST = null;
/* 427 */       if (templates != null) {
/* 428 */         eST = templates.getInstanceOf("notPredicate");
/*     */       }
/*     */       else {
/* 431 */         eST = new StringTemplate("?!($pred$)");
/*     */       }
/* 433 */       eST.setAttribute("pred", this.ctx.genExpr(generator, templates, dfa));
/* 434 */       return eST;
/*     */     }
/*     */     public SemanticContext getGatedPredicateContext() {
/* 437 */       SemanticContext p = this.ctx.getGatedPredicateContext();
/* 438 */       if (p == null) {
/* 439 */         return null;
/*     */       }
/* 441 */       return new NOT(p);
/*     */     }
/*     */ 
/*     */     public boolean hasUserSemanticPredicate()
/*     */     {
/* 446 */       return this.ctx.hasUserSemanticPredicate();
/*     */     }
/*     */ 
/*     */     public boolean isSyntacticPredicate() {
/* 450 */       return this.ctx.isSyntacticPredicate();
/*     */     }
/*     */     public void trackUseOfSyntacticPredicates(Grammar g) {
/* 453 */       this.ctx.trackUseOfSyntacticPredicates(g);
/*     */     }
/*     */ 
/*     */     public boolean equals(Object object) {
/* 457 */       if (!(object instanceof NOT)) {
/* 458 */         return false;
/*     */       }
/* 460 */       return this.ctx.equals(((NOT)object).ctx);
/*     */     }
/*     */ 
/*     */     public String toString() {
/* 464 */       return "!(" + this.ctx + ")";
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class OR extends SemanticContext
/*     */   {
/*     */     protected Set operands;
/*     */ 
/*     */     public OR(SemanticContext a, SemanticContext b)
/*     */     {
/* 332 */       this.operands = new HashSet();
/* 333 */       if ((a instanceof OR)) {
/* 334 */         this.operands.addAll(((OR)a).operands);
/*     */       }
/* 336 */       else if (a != null) {
/* 337 */         this.operands.add(a);
/*     */       }
/* 339 */       if ((b instanceof OR)) {
/* 340 */         this.operands.addAll(((OR)b).operands);
/*     */       }
/* 342 */       else if (b != null)
/* 343 */         this.operands.add(b);
/*     */     }
/*     */ 
/*     */     public StringTemplate genExpr(CodeGenerator generator, StringTemplateGroup templates, DFA dfa)
/*     */     {
/* 350 */       StringTemplate eST = null;
/* 351 */       if (templates != null) {
/* 352 */         eST = templates.getInstanceOf("orPredicates");
/*     */       }
/*     */       else {
/* 355 */         eST = new StringTemplate("($first(operands)$$rest(operands):{o | ||$o$}$)");
/*     */       }
/* 357 */       for (Iterator it = this.operands.iterator(); it.hasNext(); ) {
/* 358 */         SemanticContext semctx = (SemanticContext)it.next();
/* 359 */         eST.setAttribute("operands", semctx.genExpr(generator, templates, dfa));
/*     */       }
/* 361 */       return eST;
/*     */     }
/*     */     public SemanticContext getGatedPredicateContext() {
/* 364 */       SemanticContext result = null;
/* 365 */       for (Iterator it = this.operands.iterator(); it.hasNext(); ) {
/* 366 */         SemanticContext semctx = (SemanticContext)it.next();
/* 367 */         SemanticContext gatedPred = semctx.getGatedPredicateContext();
/* 368 */         if (gatedPred != null) {
/* 369 */           result = or(result, gatedPred);
/*     */         }
/*     */       }
/*     */ 
/* 373 */       return result;
/*     */     }
/*     */ 
/*     */     public boolean hasUserSemanticPredicate() {
/* 377 */       for (Iterator it = this.operands.iterator(); it.hasNext(); ) {
/* 378 */         SemanticContext semctx = (SemanticContext)it.next();
/* 379 */         if (semctx.hasUserSemanticPredicate()) {
/* 380 */           return true;
/*     */         }
/*     */       }
/* 383 */       return false;
/*     */     }
/*     */     public boolean isSyntacticPredicate() {
/* 386 */       for (Iterator it = this.operands.iterator(); it.hasNext(); ) {
/* 387 */         SemanticContext semctx = (SemanticContext)it.next();
/* 388 */         if (semctx.isSyntacticPredicate()) {
/* 389 */           return true;
/*     */         }
/*     */       }
/* 392 */       return false;
/*     */     }
/*     */     public void trackUseOfSyntacticPredicates(Grammar g) {
/* 395 */       for (Iterator it = this.operands.iterator(); it.hasNext(); ) {
/* 396 */         SemanticContext semctx = (SemanticContext)it.next();
/* 397 */         semctx.trackUseOfSyntacticPredicates(g);
/*     */       }
/*     */     }
/*     */ 
/* 401 */     public String toString() { StringBuffer buf = new StringBuffer();
/* 402 */       buf.append("(");
/* 403 */       int i = 0;
/* 404 */       for (Iterator it = this.operands.iterator(); it.hasNext(); ) {
/* 405 */         SemanticContext semctx = (SemanticContext)it.next();
/* 406 */         if (i > 0) {
/* 407 */           buf.append("||");
/*     */         }
/* 409 */         buf.append(semctx.toString());
/* 410 */         i++;
/*     */       }
/* 412 */       buf.append(")");
/* 413 */       return buf.toString();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class AND extends SemanticContext
/*     */   {
/*     */     protected SemanticContext left;
/*     */     protected SemanticContext right;
/*     */ 
/*     */     public AND(SemanticContext a, SemanticContext b)
/*     */     {
/* 282 */       this.left = a;
/* 283 */       this.right = b;
/*     */     }
/*     */ 
/*     */     public StringTemplate genExpr(CodeGenerator generator, StringTemplateGroup templates, DFA dfa)
/*     */     {
/* 289 */       StringTemplate eST = null;
/* 290 */       if (templates != null) {
/* 291 */         eST = templates.getInstanceOf("andPredicates");
/*     */       }
/*     */       else {
/* 294 */         eST = new StringTemplate("($left$&&$right$)");
/*     */       }
/* 296 */       eST.setAttribute("left", this.left.genExpr(generator, templates, dfa));
/* 297 */       eST.setAttribute("right", this.right.genExpr(generator, templates, dfa));
/* 298 */       return eST;
/*     */     }
/*     */     public SemanticContext getGatedPredicateContext() {
/* 301 */       SemanticContext gatedLeft = this.left.getGatedPredicateContext();
/* 302 */       SemanticContext gatedRight = this.right.getGatedPredicateContext();
/* 303 */       if (gatedLeft == null) {
/* 304 */         return gatedRight;
/*     */       }
/* 306 */       if (gatedRight == null) {
/* 307 */         return gatedLeft;
/*     */       }
/* 309 */       return new AND(gatedLeft, gatedRight);
/*     */     }
/*     */ 
/*     */     public boolean hasUserSemanticPredicate()
/*     */     {
/* 314 */       return (this.left.hasUserSemanticPredicate()) || (this.right.hasUserSemanticPredicate());
/*     */     }
/*     */ 
/*     */     public boolean isSyntacticPredicate() {
/* 318 */       return (this.left.isSyntacticPredicate()) || (this.right.isSyntacticPredicate());
/*     */     }
/*     */     public void trackUseOfSyntacticPredicates(Grammar g) {
/* 321 */       this.left.trackUseOfSyntacticPredicates(g);
/* 322 */       this.right.trackUseOfSyntacticPredicates(g);
/*     */     }
/*     */     public String toString() {
/* 325 */       return "(" + this.left + "&&" + this.right + ")";
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class TruePredicate extends SemanticContext.Predicate
/*     */   {
/*     */     public TruePredicate()
/*     */     {
/* 235 */       this.constantValue = 1;
/*     */     }
/*     */ 
/*     */     public StringTemplate genExpr(CodeGenerator generator, StringTemplateGroup templates, DFA dfa)
/*     */     {
/* 242 */       if (templates != null) {
/* 243 */         return templates.getInstanceOf("true");
/*     */       }
/* 245 */       return new StringTemplate("true");
/*     */     }
/*     */ 
/*     */     public boolean hasUserSemanticPredicate()
/*     */     {
/* 250 */       return false;
/*     */     }
/*     */ 
/*     */     public String toString() {
/* 254 */       return "true";
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class Predicate extends SemanticContext
/*     */   {
/*     */     public GrammarAST predicateAST;
/* 100 */     protected boolean gated = false;
/*     */ 
/* 105 */     protected boolean synpred = false;
/*     */     public static final int INVALID_PRED_VALUE = -1;
/*     */     public static final int FALSE_PRED = 0;
/*     */     public static final int TRUE_PRED = 1;
/* 115 */     protected int constantValue = -1;
/*     */ 
/*     */     public Predicate() {
/* 118 */       this.predicateAST = new GrammarAST();
/* 119 */       this.gated = false;
/*     */     }
/*     */ 
/*     */     public Predicate(GrammarAST predicate) {
/* 123 */       this.predicateAST = predicate;
/* 124 */       this.gated = ((predicate.getType() == 35) || (predicate.getType() == 36));
/*     */ 
/* 127 */       this.synpred = ((predicate.getType() == 36) || (predicate.getType() == 37));
/*     */     }
/*     */ 
/*     */     public Predicate(Predicate p)
/*     */     {
/* 133 */       this.predicateAST = p.predicateAST;
/* 134 */       this.gated = p.gated;
/* 135 */       this.synpred = p.synpred;
/* 136 */       this.constantValue = p.constantValue;
/*     */     }
/*     */ 
/*     */     public boolean equals(Object o)
/*     */     {
/* 145 */       if (!(o instanceof Predicate)) {
/* 146 */         return false;
/*     */       }
/* 148 */       return this.predicateAST.getText().equals(((Predicate)o).predicateAST.getText());
/*     */     }
/*     */ 
/*     */     public int hashCode() {
/* 152 */       if (this.predicateAST == null) {
/* 153 */         return 0;
/*     */       }
/* 155 */       return this.predicateAST.getText().hashCode();
/*     */     }
/*     */ 
/*     */     public StringTemplate genExpr(CodeGenerator generator, StringTemplateGroup templates, DFA dfa)
/*     */     {
/* 162 */       StringTemplate eST = null;
/* 163 */       if (templates != null) {
/* 164 */         if (this.synpred) {
/* 165 */           eST = templates.getInstanceOf("evalSynPredicate");
/*     */         }
/*     */         else {
/* 168 */           eST = templates.getInstanceOf("evalPredicate");
/* 169 */           generator.grammar.decisionsWhoseDFAsUsesSemPreds.add(dfa);
/*     */         }
/* 171 */         String predEnclosingRuleName = this.predicateAST.enclosingRuleName;
/*     */ 
/* 180 */         if (generator != null) {
/* 181 */           eST.setAttribute("pred", generator.translateAction(predEnclosingRuleName, this.predicateAST));
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 186 */         eST = new StringTemplate("$pred$");
/* 187 */         eST.setAttribute("pred", toString());
/* 188 */         return eST;
/*     */       }
/* 190 */       if (generator != null) {
/* 191 */         String description = generator.target.getTargetStringLiteralFromString(toString());
/*     */ 
/* 193 */         eST.setAttribute("description", description);
/*     */       }
/* 195 */       return eST;
/*     */     }
/*     */ 
/*     */     public SemanticContext getGatedPredicateContext() {
/* 199 */       if (this.gated) {
/* 200 */         return this;
/*     */       }
/* 202 */       return null;
/*     */     }
/*     */ 
/*     */     public boolean hasUserSemanticPredicate()
/*     */     {
/* 207 */       return (this.predicateAST != null) && ((this.predicateAST.getType() == 35) || (this.predicateAST.getType() == 69));
/*     */     }
/*     */ 
/*     */     public boolean isSyntacticPredicate()
/*     */     {
/* 213 */       return (this.predicateAST != null) && ((this.predicateAST.getType() == 36) || (this.predicateAST.getType() == 37));
/*     */     }
/*     */ 
/*     */     public void trackUseOfSyntacticPredicates(Grammar g)
/*     */     {
/* 219 */       if (this.synpred)
/* 220 */         g.synPredNamesUsedInDFA.add(this.predicateAST.getText());
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 225 */       if (this.predicateAST == null) {
/* 226 */         return "<nopred>";
/*     */       }
/* 228 */       return this.predicateAST.getText();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.analysis.SemanticContext
 * JD-Core Version:    0.6.2
 */