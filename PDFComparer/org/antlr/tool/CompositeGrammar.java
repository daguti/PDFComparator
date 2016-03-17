/*     */ package org.antlr.tool;
/*     */ 
/*     */ import antlr.RecognitionException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import org.antlr.analysis.NFAState;
/*     */ import org.antlr.grammar.v2.AssignTokenTypesWalker;
/*     */ import org.antlr.misc.Utils;
/*     */ 
/*     */ public class CompositeGrammar
/*     */ {
/*     */   public static final int MIN_RULE_INDEX = 1;
/*     */   public CompositeGrammarTree delegateGrammarTreeRoot;
/*  56 */   protected Set<NFAState> refClosureBusy = new HashSet();
/*     */ 
/*  61 */   public int stateCounter = 0;
/*     */ 
/*  69 */   protected Vector<NFAState> numberToStateList = new Vector(1000);
/*     */ 
/*  79 */   protected int maxTokenType = 3;
/*     */ 
/*  82 */   public Map tokenIDToTypeMap = new LinkedHashMap();
/*     */ 
/*  88 */   public Map<String, Integer> stringLiteralToTypeMap = new LinkedHashMap();
/*     */ 
/*  90 */   public Vector<String> typeToStringLiteralList = new Vector();
/*     */ 
/*  95 */   public Vector<String> typeToTokenList = new Vector();
/*     */ 
/* 102 */   protected Set<String> lexerRules = new HashSet();
/*     */ 
/* 105 */   protected int ruleIndex = 1;
/*     */ 
/* 112 */   protected Vector<Rule> ruleIndexToRuleList = new Vector();
/*     */ 
/* 114 */   public boolean watchNFAConversion = false;
/*     */ 
/*     */   protected void initTokenSymbolTables()
/*     */   {
/* 120 */     this.typeToTokenList.setSize(10);
/* 121 */     this.typeToTokenList.set(0, "<INVALID>");
/* 122 */     this.typeToTokenList.set(5, "<EOT>");
/* 123 */     this.typeToTokenList.set(3, "<SEMPRED>");
/* 124 */     this.typeToTokenList.set(4, "<SET>");
/* 125 */     this.typeToTokenList.set(2, "<EPSILON>");
/* 126 */     this.typeToTokenList.set(6, "EOF");
/* 127 */     this.typeToTokenList.set(7, "<EOR>");
/* 128 */     this.typeToTokenList.set(8, "DOWN");
/* 129 */     this.typeToTokenList.set(9, "UP");
/* 130 */     this.tokenIDToTypeMap.put("<INVALID>", Utils.integer(-7));
/* 131 */     this.tokenIDToTypeMap.put("<EOT>", Utils.integer(-2));
/* 132 */     this.tokenIDToTypeMap.put("<SEMPRED>", Utils.integer(-4));
/* 133 */     this.tokenIDToTypeMap.put("<SET>", Utils.integer(-3));
/* 134 */     this.tokenIDToTypeMap.put("<EPSILON>", Utils.integer(-5));
/* 135 */     this.tokenIDToTypeMap.put("EOF", Utils.integer(-1));
/* 136 */     this.tokenIDToTypeMap.put("<EOR>", Utils.integer(1));
/* 137 */     this.tokenIDToTypeMap.put("DOWN", Utils.integer(2));
/* 138 */     this.tokenIDToTypeMap.put("UP", Utils.integer(3));
/*     */   }
/*     */ 
/*     */   public CompositeGrammar() {
/* 142 */     initTokenSymbolTables();
/*     */   }
/*     */ 
/*     */   public CompositeGrammar(Grammar g) {
/* 146 */     this();
/* 147 */     setDelegationRoot(g);
/*     */   }
/*     */ 
/*     */   public void setDelegationRoot(Grammar root) {
/* 151 */     this.delegateGrammarTreeRoot = new CompositeGrammarTree(root);
/* 152 */     root.compositeTreeNode = this.delegateGrammarTreeRoot;
/*     */   }
/*     */ 
/*     */   public Rule getRule(String ruleName) {
/* 156 */     return this.delegateGrammarTreeRoot.getRule(ruleName);
/*     */   }
/*     */ 
/*     */   public Object getOption(String key) {
/* 160 */     return this.delegateGrammarTreeRoot.getOption(key);
/*     */   }
/*     */ 
/*     */   public void addGrammar(Grammar delegator, Grammar delegate)
/*     */   {
/* 165 */     if (delegator.compositeTreeNode == null) {
/* 166 */       delegator.compositeTreeNode = new CompositeGrammarTree(delegator);
/*     */     }
/* 168 */     delegator.compositeTreeNode.addChild(new CompositeGrammarTree(delegate));
/*     */ 
/* 175 */     delegate.composite = this;
/*     */   }
/*     */ 
/*     */   public Grammar getDelegator(Grammar g)
/*     */   {
/* 180 */     CompositeGrammarTree me = this.delegateGrammarTreeRoot.findNode(g);
/* 181 */     if (me == null) {
/* 182 */       return null;
/*     */     }
/* 184 */     if (me.parent != null) {
/* 185 */       return me.parent.grammar;
/*     */     }
/* 187 */     return null;
/*     */   }
/*     */ 
/*     */   public List<Grammar> getDelegates(Grammar g)
/*     */   {
/* 195 */     CompositeGrammarTree t = this.delegateGrammarTreeRoot.findNode(g);
/* 196 */     if (t == null) {
/* 197 */       return null;
/*     */     }
/* 199 */     List grammars = t.getPostOrderedGrammarList();
/* 200 */     grammars.remove(grammars.size() - 1);
/* 201 */     return grammars;
/*     */   }
/*     */ 
/*     */   public List<Grammar> getDirectDelegates(Grammar g) {
/* 205 */     CompositeGrammarTree t = this.delegateGrammarTreeRoot.findNode(g);
/* 206 */     List children = t.children;
/* 207 */     if (children == null) {
/* 208 */       return null;
/*     */     }
/* 210 */     List grammars = new ArrayList();
/* 211 */     for (int i = 0; (children != null) && (i < children.size()); i++) {
/* 212 */       CompositeGrammarTree child = (CompositeGrammarTree)children.get(i);
/* 213 */       grammars.add(child.grammar);
/*     */     }
/* 215 */     return grammars;
/*     */   }
/*     */ 
/*     */   public List<Grammar> getIndirectDelegates(Grammar g)
/*     */   {
/* 220 */     List direct = getDirectDelegates(g);
/* 221 */     List delegates = getDelegates(g);
/* 222 */     delegates.removeAll(direct);
/* 223 */     return delegates;
/*     */   }
/*     */ 
/*     */   public List<Grammar> getDelegators(Grammar g)
/*     */   {
/* 230 */     if (g == this.delegateGrammarTreeRoot.grammar) {
/* 231 */       return null;
/*     */     }
/* 233 */     List grammars = new ArrayList();
/* 234 */     CompositeGrammarTree t = this.delegateGrammarTreeRoot.findNode(g);
/*     */ 
/* 236 */     CompositeGrammarTree p = t.parent;
/* 237 */     while (p != null) {
/* 238 */       grammars.add(0, p.grammar);
/* 239 */       p = p.parent;
/*     */     }
/* 241 */     return grammars;
/*     */   }
/*     */ 
/*     */   public Set<Rule> getDelegatedRules(Grammar g)
/*     */   {
/* 254 */     if (g != this.delegateGrammarTreeRoot.grammar) {
/* 255 */       return null;
/*     */     }
/* 257 */     Set rules = getAllImportedRules(g);
/* 258 */     for (Iterator it = rules.iterator(); it.hasNext(); ) {
/* 259 */       Rule r = (Rule)it.next();
/* 260 */       Rule localRule = g.getLocallyDefinedRule(r.name);
/*     */ 
/* 263 */       if ((localRule != null) || (r.isSynPred)) {
/* 264 */         it.remove();
/*     */       }
/*     */     }
/* 267 */     return rules;
/*     */   }
/*     */ 
/*     */   public Set<Rule> getAllImportedRules(Grammar g)
/*     */   {
/* 274 */     Set ruleNames = new HashSet();
/* 275 */     Set rules = new HashSet();
/* 276 */     CompositeGrammarTree subtreeRoot = this.delegateGrammarTreeRoot.findNode(g);
/*     */ 
/* 278 */     List grammars = subtreeRoot.getPreOrderedGrammarList();
/*     */     Iterator it;
/* 280 */     for (int i = 0; i < grammars.size(); i++) {
/* 281 */       Grammar delegate = (Grammar)grammars.get(i);
/*     */ 
/* 284 */       for (it = delegate.getRules().iterator(); it.hasNext(); ) {
/* 285 */         Rule r = (Rule)it.next();
/* 286 */         if (!ruleNames.contains(r.name)) {
/* 287 */           ruleNames.add(r.name);
/* 288 */           rules.add(r);
/*     */         }
/*     */       }
/*     */     }
/* 292 */     return rules;
/*     */   }
/*     */ 
/*     */   public Grammar getRootGrammar() {
/* 296 */     if (this.delegateGrammarTreeRoot == null) {
/* 297 */       return null;
/*     */     }
/* 299 */     return this.delegateGrammarTreeRoot.grammar;
/*     */   }
/*     */ 
/*     */   public Grammar getGrammar(String grammarName) {
/* 303 */     CompositeGrammarTree t = this.delegateGrammarTreeRoot.findNode(grammarName);
/* 304 */     if (t != null) {
/* 305 */       return t.grammar;
/*     */     }
/* 307 */     return null;
/*     */   }
/*     */ 
/*     */   public int getNewNFAStateNumber()
/*     */   {
/* 313 */     return this.stateCounter++;
/*     */   }
/*     */ 
/*     */   public void addState(NFAState state) {
/* 317 */     this.numberToStateList.setSize(state.stateNumber + 1);
/* 318 */     this.numberToStateList.set(state.stateNumber, state);
/*     */   }
/*     */ 
/*     */   public NFAState getState(int s) {
/* 322 */     return (NFAState)this.numberToStateList.get(s);
/*     */   }
/*     */ 
/*     */   public void assignTokenTypes()
/*     */     throws RecognitionException
/*     */   {
/* 328 */     AssignTokenTypesWalker ttypesWalker = new AssignTokenTypesBehavior();
/* 329 */     ttypesWalker.setASTNodeClass("org.antlr.tool.GrammarAST");
/* 330 */     List grammars = this.delegateGrammarTreeRoot.getPostOrderedGrammarList();
/* 331 */     for (int i = 0; (grammars != null) && (i < grammars.size()); i++) {
/* 332 */       Grammar g = (Grammar)grammars.get(i);
/*     */       try
/*     */       {
/* 335 */         ttypesWalker.grammar(g.getGrammarTree(), g);
/*     */       }
/*     */       catch (RecognitionException re) {
/* 338 */         ErrorManager.error(15, re);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 344 */     ttypesWalker.defineTokens(this.delegateGrammarTreeRoot.grammar);
/*     */   }
/*     */ 
/*     */   public void defineGrammarSymbols() {
/* 348 */     this.delegateGrammarTreeRoot.trimLexerImportsIntoCombined();
/* 349 */     List grammars = this.delegateGrammarTreeRoot.getPostOrderedGrammarList();
/* 350 */     for (int i = 0; (grammars != null) && (i < grammars.size()); i++) {
/* 351 */       Grammar g = (Grammar)grammars.get(i);
/* 352 */       g.defineGrammarSymbols();
/*     */     }
/* 354 */     for (int i = 0; (grammars != null) && (i < grammars.size()); i++) {
/* 355 */       Grammar g = (Grammar)grammars.get(i);
/* 356 */       g.checkNameSpaceAndActions();
/*     */     }
/* 358 */     minimizeRuleSet();
/*     */   }
/*     */ 
/*     */   public void createNFAs() {
/* 362 */     if (ErrorManager.doNotAttemptAnalysis()) {
/* 363 */       return;
/*     */     }
/* 365 */     List grammars = this.delegateGrammarTreeRoot.getPostOrderedGrammarList();
/* 366 */     List names = new ArrayList();
/* 367 */     for (int i = 0; i < grammars.size(); i++) {
/* 368 */       Grammar g = (Grammar)grammars.get(i);
/* 369 */       names.add(g.name);
/*     */     }
/*     */ 
/* 372 */     for (int i = 0; (grammars != null) && (i < grammars.size()); i++) {
/* 373 */       Grammar g = (Grammar)grammars.get(i);
/* 374 */       g.createRuleStartAndStopNFAStates();
/*     */     }
/* 376 */     for (int i = 0; (grammars != null) && (i < grammars.size()); i++) {
/* 377 */       Grammar g = (Grammar)grammars.get(i);
/* 378 */       g.buildNFA();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void minimizeRuleSet() {
/* 383 */     Set ruleDefs = new HashSet();
/* 384 */     _minimizeRuleSet(ruleDefs, this.delegateGrammarTreeRoot);
/*     */   }
/*     */ 
/*     */   public void _minimizeRuleSet(Set<String> ruleDefs, CompositeGrammarTree p)
/*     */   {
/* 389 */     Set localRuleDefs = new HashSet();
/* 390 */     Set overrides = new HashSet();
/*     */ 
/* 392 */     for (Iterator i$ = p.grammar.getRules().iterator(); i$.hasNext(); ) { Rule r = (Rule)i$.next();
/* 393 */       if (!ruleDefs.contains(r.name)) {
/* 394 */         localRuleDefs.add(r.name);
/*     */       }
/* 396 */       else if (!r.name.equals("Tokens"))
/*     */       {
/* 398 */         overrides.add(r.name);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 403 */     p.grammar.overriddenRules = overrides;
/*     */ 
/* 408 */     ruleDefs.addAll(localRuleDefs);
/*     */     Iterator i$;
/* 411 */     if (p.children != null)
/* 412 */       for (i$ = p.children.iterator(); i$.hasNext(); ) { CompositeGrammarTree delegate = (CompositeGrammarTree)i$.next();
/* 413 */         _minimizeRuleSet(ruleDefs, delegate);
/*     */       }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.tool.CompositeGrammar
 * JD-Core Version:    0.6.2
 */