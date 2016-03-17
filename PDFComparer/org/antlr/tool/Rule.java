/*     */ package org.antlr.tool;
/*     */ 
/*     */ import antlr.CommonToken;
/*     */ import antlr.Token;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.antlr.analysis.LookaheadSet;
/*     */ import org.antlr.analysis.NFAState;
/*     */ import org.antlr.codegen.CodeGenerator;
/*     */ import org.antlr.stringtemplate.StringTemplate;
/*     */ 
/*     */ public class Rule
/*     */ {
/*     */   public String name;
/*     */   public int index;
/*     */   public String modifier;
/*     */   public NFAState startState;
/*     */   public NFAState stopState;
/*     */   protected Map options;
/*  49 */   public static final Set legalOptions = new HashSet() { } ;
/*     */   public GrammarAST tree;
/*     */   public Grammar grammar;
/*     */   public GrammarAST argActionAST;
/*     */   public GrammarAST EORNode;
/*     */   public LookaheadSet FIRST;
/*     */   public AttributeScope returnScope;
/*     */   public AttributeScope parameterScope;
/*     */   public AttributeScope ruleScope;
/*     */   public List useScopes;
/*     */   public LinkedHashMap tokenLabels;
/*     */   public LinkedHashMap wildcardTreeLabels;
/*     */   public LinkedHashMap wildcardTreeListLabels;
/*     */   public LinkedHashMap charLabels;
/*     */   public LinkedHashMap ruleLabels;
/*     */   public LinkedHashMap tokenListLabels;
/*     */   public LinkedHashMap ruleListLabels;
/* 109 */   protected Map<String, Grammar.LabelElementPair> labelNameSpace = new HashMap();
/*     */ 
/* 119 */   protected Map<String, GrammarAST> actions = new HashMap();
/*     */ 
/* 127 */   protected List<GrammarAST> inlineActions = new ArrayList();
/*     */   public int numberOfAlts;
/*     */   protected Map<String, List<GrammarAST>>[] altToTokenRefMap;
/*     */   protected Map<String, List<GrammarAST>>[] altToRuleRefMap;
/*     */   protected boolean[] altsWithRewrites;
/* 160 */   public boolean referencedPredefinedRuleAttributes = false;
/*     */ 
/* 162 */   public boolean isSynPred = false;
/*     */ 
/* 164 */   public boolean imported = false;
/*     */ 
/*     */   public Rule(Grammar grammar, String ruleName, int ruleIndex, int numberOfAlts)
/*     */   {
/* 171 */     this.name = ruleName;
/* 172 */     this.index = ruleIndex;
/* 173 */     this.numberOfAlts = numberOfAlts;
/* 174 */     this.grammar = grammar;
/* 175 */     this.altToTokenRefMap = new Map[numberOfAlts + 1];
/* 176 */     this.altToRuleRefMap = new Map[numberOfAlts + 1];
/* 177 */     this.altsWithRewrites = new boolean[numberOfAlts + 1];
/* 178 */     for (int alt = 1; alt <= numberOfAlts; alt++) {
/* 179 */       this.altToTokenRefMap[alt] = new HashMap();
/* 180 */       this.altToRuleRefMap[alt] = new HashMap();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void defineLabel(Token label, GrammarAST elementRef, int type)
/*     */   {
/*     */     Grammar tmp8_5 = this.grammar; tmp8_5.getClass(); Grammar.LabelElementPair pair = new Grammar.LabelElementPair(tmp8_5, label, elementRef);
/* 186 */     pair.type = type;
/* 187 */     this.labelNameSpace.put(label.getText(), pair);
/* 188 */     switch (type) {
/*     */     case 2:
/* 190 */       if (this.tokenLabels == null) this.tokenLabels = new LinkedHashMap();
/* 191 */       this.tokenLabels.put(label.getText(), pair);
/* 192 */       break;
/*     */     case 6:
/* 194 */       if (this.wildcardTreeLabels == null) this.wildcardTreeLabels = new LinkedHashMap();
/* 195 */       this.wildcardTreeLabels.put(label.getText(), pair);
/* 196 */       break;
/*     */     case 7:
/* 198 */       if (this.wildcardTreeListLabels == null) this.wildcardTreeListLabels = new LinkedHashMap();
/* 199 */       this.wildcardTreeListLabels.put(label.getText(), pair);
/* 200 */       break;
/*     */     case 1:
/* 202 */       if (this.ruleLabels == null) this.ruleLabels = new LinkedHashMap();
/* 203 */       this.ruleLabels.put(label.getText(), pair);
/* 204 */       break;
/*     */     case 4:
/* 206 */       if (this.tokenListLabels == null) this.tokenListLabels = new LinkedHashMap();
/* 207 */       this.tokenListLabels.put(label.getText(), pair);
/* 208 */       break;
/*     */     case 3:
/* 210 */       if (this.ruleListLabels == null) this.ruleListLabels = new LinkedHashMap();
/* 211 */       this.ruleListLabels.put(label.getText(), pair);
/* 212 */       break;
/*     */     case 5:
/* 214 */       if (this.charLabels == null) this.charLabels = new LinkedHashMap();
/* 215 */       this.charLabels.put(label.getText(), pair);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Grammar.LabelElementPair getLabel(String name)
/*     */   {
/* 221 */     return (Grammar.LabelElementPair)this.labelNameSpace.get(name);
/*     */   }
/*     */ 
/*     */   public Grammar.LabelElementPair getTokenLabel(String name) {
/* 225 */     Grammar.LabelElementPair pair = null;
/* 226 */     if (this.tokenLabels != null) {
/* 227 */       return (Grammar.LabelElementPair)this.tokenLabels.get(name);
/*     */     }
/* 229 */     return pair;
/*     */   }
/*     */ 
/*     */   public Map getRuleLabels() {
/* 233 */     return this.ruleLabels;
/*     */   }
/*     */ 
/*     */   public Map getRuleListLabels() {
/* 237 */     return this.ruleListLabels;
/*     */   }
/*     */ 
/*     */   public Grammar.LabelElementPair getRuleLabel(String name) {
/* 241 */     Grammar.LabelElementPair pair = null;
/* 242 */     if (this.ruleLabels != null) {
/* 243 */       return (Grammar.LabelElementPair)this.ruleLabels.get(name);
/*     */     }
/* 245 */     return pair;
/*     */   }
/*     */ 
/*     */   public Grammar.LabelElementPair getTokenListLabel(String name) {
/* 249 */     Grammar.LabelElementPair pair = null;
/* 250 */     if (this.tokenListLabels != null) {
/* 251 */       return (Grammar.LabelElementPair)this.tokenListLabels.get(name);
/*     */     }
/* 253 */     return pair;
/*     */   }
/*     */ 
/*     */   public Grammar.LabelElementPair getRuleListLabel(String name) {
/* 257 */     Grammar.LabelElementPair pair = null;
/* 258 */     if (this.ruleListLabels != null) {
/* 259 */       return (Grammar.LabelElementPair)this.ruleListLabels.get(name);
/*     */     }
/* 261 */     return pair;
/*     */   }
/*     */ 
/*     */   public void trackTokenReferenceInAlt(GrammarAST refAST, int outerAltNum)
/*     */   {
/* 271 */     List refs = (List)this.altToTokenRefMap[outerAltNum].get(refAST.getText());
/* 272 */     if (refs == null) {
/* 273 */       refs = new ArrayList();
/* 274 */       this.altToTokenRefMap[outerAltNum].put(refAST.getText(), refs);
/*     */     }
/* 276 */     refs.add(refAST);
/*     */   }
/*     */ 
/*     */   public List getTokenRefsInAlt(String ref, int outerAltNum) {
/* 280 */     if (this.altToTokenRefMap[outerAltNum] != null) {
/* 281 */       List tokenRefASTs = (List)this.altToTokenRefMap[outerAltNum].get(ref);
/* 282 */       return tokenRefASTs;
/*     */     }
/* 284 */     return null;
/*     */   }
/*     */ 
/*     */   public void trackRuleReferenceInAlt(GrammarAST refAST, int outerAltNum) {
/* 288 */     List refs = (List)this.altToRuleRefMap[outerAltNum].get(refAST.getText());
/* 289 */     if (refs == null) {
/* 290 */       refs = new ArrayList();
/* 291 */       this.altToRuleRefMap[outerAltNum].put(refAST.getText(), refs);
/*     */     }
/* 293 */     refs.add(refAST);
/*     */   }
/*     */ 
/*     */   public List getRuleRefsInAlt(String ref, int outerAltNum) {
/* 297 */     if (this.altToRuleRefMap[outerAltNum] != null) {
/* 298 */       List ruleRefASTs = (List)this.altToRuleRefMap[outerAltNum].get(ref);
/* 299 */       return ruleRefASTs;
/*     */     }
/* 301 */     return null;
/*     */   }
/*     */ 
/*     */   public Set getTokenRefsInAlt(int altNum) {
/* 305 */     return this.altToTokenRefMap[altNum].keySet();
/*     */   }
/*     */ 
/*     */   public Set getAllTokenRefsInAltsWithRewrites()
/*     */   {
/* 314 */     String output = (String)this.grammar.getOption("output");
/* 315 */     Set tokens = new HashSet();
/* 316 */     if ((output == null) || (!output.equals("AST")))
/*     */     {
/* 318 */       return tokens;
/*     */     }
/*     */     Iterator it;
/* 320 */     for (int i = 1; i <= this.numberOfAlts; i++) {
/* 321 */       if (this.altsWithRewrites[i] != 0) {
/* 322 */         Map m = this.altToTokenRefMap[i];
/* 323 */         Set s = m.keySet();
/* 324 */         for (it = s.iterator(); it.hasNext(); )
/*     */         {
/* 326 */           String tokenName = (String)it.next();
/* 327 */           int ttype = this.grammar.getTokenType(tokenName);
/* 328 */           String label = this.grammar.generator.getTokenTypeAsTargetLabel(ttype);
/* 329 */           tokens.add(label);
/*     */         }
/*     */       }
/*     */     }
/* 333 */     return tokens;
/*     */   }
/*     */ 
/*     */   public Set getRuleRefsInAlt(int outerAltNum) {
/* 337 */     return this.altToRuleRefMap[outerAltNum].keySet();
/*     */   }
/*     */ 
/*     */   public Set getAllRuleRefsInAltsWithRewrites()
/*     */   {
/* 345 */     Set rules = new HashSet();
/* 346 */     for (int i = 1; i <= this.numberOfAlts; i++) {
/* 347 */       if (this.altsWithRewrites[i] != 0) {
/* 348 */         Map m = this.altToRuleRefMap[i];
/* 349 */         rules.addAll(m.keySet());
/*     */       }
/*     */     }
/* 352 */     return rules;
/*     */   }
/*     */ 
/*     */   public List<GrammarAST> getInlineActions() {
/* 356 */     return this.inlineActions;
/*     */   }
/*     */ 
/*     */   public boolean hasRewrite(int i) {
/* 360 */     if (i >= this.altsWithRewrites.length) {
/* 361 */       ErrorManager.internalError("alt " + i + " exceeds number of " + this.name + "'s alts (" + this.altsWithRewrites.length + ")");
/*     */ 
/* 363 */       return false;
/*     */     }
/* 365 */     return this.altsWithRewrites[i];
/*     */   }
/*     */ 
/*     */   public void trackAltsWithRewrites(GrammarAST altAST, int outerAltNum)
/*     */   {
/* 373 */     if ((this.grammar.type == 3) && (this.grammar.buildTemplate()) && (this.grammar.getOption("rewrite") != null) && (this.grammar.getOption("rewrite").equals("true")))
/*     */     {
/* 379 */       GrammarAST firstElementAST = (GrammarAST)altAST.getFirstChild();
/* 380 */       this.grammar.sanity.ensureAltIsSimpleNodeOrTree(altAST, firstElementAST, outerAltNum);
/*     */     }
/*     */ 
/* 384 */     this.altsWithRewrites[outerAltNum] = true;
/*     */   }
/*     */ 
/*     */   public AttributeScope getAttributeScope(String name)
/*     */   {
/* 389 */     AttributeScope scope = getLocalAttributeScope(name);
/* 390 */     if (scope != null) {
/* 391 */       return scope;
/*     */     }
/* 393 */     if ((this.ruleScope != null) && (this.ruleScope.getAttribute(name) != null)) {
/* 394 */       scope = this.ruleScope;
/*     */     }
/* 396 */     return scope;
/*     */   }
/*     */ 
/*     */   public AttributeScope getLocalAttributeScope(String name)
/*     */   {
/* 401 */     AttributeScope scope = null;
/* 402 */     if ((this.returnScope != null) && (this.returnScope.getAttribute(name) != null)) {
/* 403 */       scope = this.returnScope;
/*     */     }
/* 405 */     else if ((this.parameterScope != null) && (this.parameterScope.getAttribute(name) != null)) {
/* 406 */       scope = this.parameterScope;
/*     */     }
/*     */     else {
/* 409 */       AttributeScope rulePropertiesScope = RuleLabelScope.grammarTypeToRulePropertiesScope[this.grammar.type];
/*     */ 
/* 411 */       if (rulePropertiesScope.getAttribute(name) != null) {
/* 412 */         scope = rulePropertiesScope;
/*     */       }
/*     */     }
/* 415 */     return scope;
/*     */   }
/*     */ 
/*     */   public String getElementLabel(String refdSymbol, int outerAltNum, CodeGenerator generator)
/*     */   {
/*     */     GrammarAST uniqueRefAST;
/*     */     GrammarAST uniqueRefAST;
/* 427 */     if ((this.grammar.type != 1) && (Character.isUpperCase(refdSymbol.charAt(0))))
/*     */     {
/* 431 */       List tokenRefs = getTokenRefsInAlt(refdSymbol, outerAltNum);
/* 432 */       uniqueRefAST = (GrammarAST)tokenRefs.get(0);
/*     */     }
/*     */     else
/*     */     {
/* 436 */       List ruleRefs = getRuleRefsInAlt(refdSymbol, outerAltNum);
/* 437 */       uniqueRefAST = (GrammarAST)ruleRefs.get(0);
/*     */     }
/* 439 */     if (uniqueRefAST.code == null)
/*     */     {
/* 441 */       return null;
/*     */     }
/* 443 */     String labelName = null;
/* 444 */     String existingLabelName = (String)uniqueRefAST.code.getAttribute("label");
/*     */ 
/* 447 */     if (existingLabelName != null) {
/* 448 */       labelName = existingLabelName;
/*     */     }
/*     */     else
/*     */     {
/* 452 */       labelName = generator.createUniqueLabel(refdSymbol);
/* 453 */       CommonToken label = new CommonToken(21, labelName);
/* 454 */       if ((this.grammar.type != 1) && (Character.isUpperCase(refdSymbol.charAt(0))))
/*     */       {
/* 457 */         this.grammar.defineTokenRefLabel(this.name, label, uniqueRefAST);
/*     */       }
/*     */       else {
/* 460 */         this.grammar.defineRuleRefLabel(this.name, label, uniqueRefAST);
/*     */       }
/* 462 */       uniqueRefAST.code.setAttribute("label", labelName);
/*     */     }
/* 464 */     return labelName;
/*     */   }
/*     */ 
/*     */   public boolean getHasMultipleReturnValues()
/*     */   {
/* 473 */     return (this.referencedPredefinedRuleAttributes) || (this.grammar.buildAST()) || (this.grammar.buildTemplate()) || ((this.returnScope != null) && (this.returnScope.attributes.size() > 1));
/*     */   }
/*     */ 
/*     */   public boolean getHasSingleReturnValue()
/*     */   {
/* 480 */     return (!this.referencedPredefinedRuleAttributes) && (!this.grammar.buildAST()) && (!this.grammar.buildTemplate()) && (this.returnScope != null) && (this.returnScope.attributes.size() == 1);
/*     */   }
/*     */ 
/*     */   public boolean getHasReturnValue()
/*     */   {
/* 487 */     return (this.referencedPredefinedRuleAttributes) || (this.grammar.buildAST()) || (this.grammar.buildTemplate()) || ((this.returnScope != null) && (this.returnScope.attributes.size() > 0));
/*     */   }
/*     */ 
/*     */   public String getSingleValueReturnType()
/*     */   {
/* 494 */     if ((this.returnScope != null) && (this.returnScope.attributes.size() == 1)) {
/* 495 */       Collection retvalAttrs = this.returnScope.attributes.values();
/* 496 */       Object[] javaSucks = retvalAttrs.toArray();
/* 497 */       return ((Attribute)javaSucks[0]).type;
/*     */     }
/* 499 */     return null;
/*     */   }
/*     */ 
/*     */   public String getSingleValueReturnName() {
/* 503 */     if ((this.returnScope != null) && (this.returnScope.attributes.size() == 1)) {
/* 504 */       Collection retvalAttrs = this.returnScope.attributes.values();
/* 505 */       Object[] javaSucks = retvalAttrs.toArray();
/* 506 */       return ((Attribute)javaSucks[0]).name;
/*     */     }
/* 508 */     return null;
/*     */   }
/*     */ 
/*     */   public void defineNamedAction(GrammarAST ampersandAST, GrammarAST nameAST, GrammarAST actionAST)
/*     */   {
/* 519 */     String actionName = nameAST.getText();
/* 520 */     GrammarAST a = (GrammarAST)this.actions.get(actionName);
/* 521 */     if (a != null) {
/* 522 */       ErrorManager.grammarError(144, this.grammar, nameAST.getToken(), nameAST.getText());
/*     */     }
/*     */     else
/*     */     {
/* 527 */       this.actions.put(actionName, actionAST);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void trackInlineAction(GrammarAST actionAST) {
/* 532 */     this.inlineActions.add(actionAST);
/*     */   }
/*     */ 
/*     */   public Map<String, GrammarAST> getActions() {
/* 536 */     return this.actions;
/*     */   }
/*     */ 
/*     */   public void setActions(Map<String, GrammarAST> actions) {
/* 540 */     this.actions = actions;
/*     */   }
/*     */ 
/*     */   public String setOption(String key, Object value, Token optionsStartToken)
/*     */   {
/* 547 */     if (!legalOptions.contains(key)) {
/* 548 */       ErrorManager.grammarError(133, this.grammar, optionsStartToken, key);
/*     */ 
/* 552 */       return null;
/*     */     }
/* 554 */     if (this.options == null) {
/* 555 */       this.options = new HashMap();
/*     */     }
/* 557 */     if ((key.equals("memoize")) && (value.toString().equals("true"))) {
/* 558 */       this.grammar.atLeastOneRuleMemoizes = true;
/*     */     }
/* 560 */     if ((key.equals("backtrack")) && (value.toString().equals("true"))) {
/* 561 */       this.grammar.composite.getRootGrammar().atLeastOneBacktrackOption = true;
/*     */     }
/* 563 */     if (key.equals("k")) {
/* 564 */       this.grammar.numberOfManualLookaheadOptions += 1;
/*     */     }
/* 566 */     this.options.put(key, value);
/* 567 */     return key;
/*     */   }
/*     */ 
/*     */   public void setOptions(Map options, Token optionsStartToken) {
/* 571 */     if (options == null) {
/* 572 */       this.options = null;
/* 573 */       return;
/*     */     }
/* 575 */     Set keys = options.keySet();
/* 576 */     for (Iterator it = keys.iterator(); it.hasNext(); ) {
/* 577 */       String optionName = (String)it.next();
/* 578 */       Object optionValue = options.get(optionName);
/* 579 */       String stored = setOption(optionName, optionValue, optionsStartToken);
/* 580 */       if (stored == null)
/* 581 */         it.remove();
/*     */     }
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 600 */     return "[" + this.grammar.name + "." + this.name + ",index=" + this.index + ",line=" + this.tree.getToken().getLine() + "]";
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.tool.Rule
 * JD-Core Version:    0.6.2
 */