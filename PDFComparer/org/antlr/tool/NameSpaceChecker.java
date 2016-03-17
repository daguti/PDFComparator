/*     */ package org.antlr.tool;
/*     */ 
/*     */ import antlr.Token;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class NameSpaceChecker
/*     */ {
/*     */   protected Grammar grammar;
/*     */ 
/*     */   public NameSpaceChecker(Grammar grammar)
/*     */   {
/*  41 */     this.grammar = grammar;
/*     */   }
/*     */ 
/*     */   public void checkConflicts() {
/*  45 */     for (int i = 1; i < this.grammar.composite.ruleIndexToRuleList.size(); i++) {
/*  46 */       Rule r = (Rule)this.grammar.composite.ruleIndexToRuleList.elementAt(i);
/*  47 */       if (r != null)
/*     */       {
/*  51 */         if (r.labelNameSpace != null) {
/*  52 */           Iterator it = r.labelNameSpace.values().iterator();
/*  53 */           while (it.hasNext()) {
/*  54 */             Grammar.LabelElementPair pair = (Grammar.LabelElementPair)it.next();
/*  55 */             checkForLabelConflict(r, pair.label);
/*     */           }
/*     */         }
/*     */ 
/*  59 */         if (r.ruleScope != null) {
/*  60 */           List attributes = r.ruleScope.getAttributes();
/*  61 */           for (int j = 0; j < attributes.size(); j++) {
/*  62 */             Attribute attribute = (Attribute)attributes.get(j);
/*  63 */             checkForRuleScopeAttributeConflict(r, attribute);
/*     */           }
/*     */         }
/*  66 */         checkForRuleDefinitionProblems(r);
/*  67 */         checkForRuleArgumentAndReturnValueConflicts(r);
/*     */       }
/*     */     }
/*  70 */     Iterator it = this.grammar.getGlobalScopes().values().iterator();
/*  71 */     while (it.hasNext()) {
/*  72 */       AttributeScope scope = (AttributeScope)it.next();
/*  73 */       checkForGlobalScopeTokenConflict(scope);
/*     */     }
/*     */ 
/*  76 */     lookForReferencesToUndefinedSymbols();
/*     */   }
/*     */ 
/*     */   protected void checkForRuleArgumentAndReturnValueConflicts(Rule r)
/*     */   {
/*     */     Iterator it;
/*  80 */     if (r.returnScope != null) {
/*  81 */       Set conflictingKeys = r.returnScope.intersection(r.parameterScope);
/*  82 */       if (conflictingKeys != null)
/*  83 */         for (it = conflictingKeys.iterator(); it.hasNext(); ) {
/*  84 */           String key = (String)it.next();
/*  85 */           ErrorManager.grammarError(126, this.grammar, r.tree.getToken(), key, r.name);
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void checkForRuleDefinitionProblems(Rule r)
/*     */   {
/*  97 */     String ruleName = r.name;
/*  98 */     Token ruleToken = r.tree.getToken();
/*  99 */     int msgID = 0;
/* 100 */     if (((this.grammar.type == 2) || (this.grammar.type == 3)) && (Character.isUpperCase(ruleName.charAt(0))))
/*     */     {
/* 103 */       msgID = 102;
/*     */     }
/* 105 */     else if ((this.grammar.type == 1) && (Character.isLowerCase(ruleName.charAt(0))) && (!r.isSynPred))
/*     */     {
/* 109 */       msgID = 103;
/*     */     }
/* 111 */     else if (this.grammar.getGlobalScope(ruleName) != null) {
/* 112 */       msgID = 118;
/*     */     }
/* 114 */     if (msgID != 0)
/* 115 */       ErrorManager.grammarError(msgID, this.grammar, ruleToken, ruleName);
/*     */   }
/*     */ 
/*     */   protected void lookForReferencesToUndefinedSymbols()
/*     */   {
/* 128 */     for (Iterator iter = this.grammar.ruleRefs.iterator(); iter.hasNext(); ) {
/* 129 */       GrammarAST refAST = (GrammarAST)iter.next();
/* 130 */       Token tok = refAST.token;
/* 131 */       String ruleName = tok.getText();
/* 132 */       Rule localRule = this.grammar.getLocallyDefinedRule(ruleName);
/* 133 */       Rule rule = this.grammar.getRule(ruleName);
/* 134 */       if ((localRule == null) && (rule != null)) {
/* 135 */         this.grammar.delegatedRuleReferences.add(rule);
/* 136 */         rule.imported = true;
/*     */       }
/* 138 */       if ((rule == null) && (this.grammar.getTokenType(ruleName) != -1))
/* 139 */         ErrorManager.grammarError(106, this.grammar, tok, ruleName);
/*     */     }
/*     */     Iterator iter;
/* 145 */     if (this.grammar.type == 4)
/*     */     {
/* 148 */       for (iter = this.grammar.tokenIDRefs.iterator(); iter.hasNext(); ) {
/* 149 */         Token tok = (Token)iter.next();
/* 150 */         String tokenID = tok.getText();
/* 151 */         if ((!this.grammar.composite.lexerRules.contains(tokenID)) && (this.grammar.getTokenType(tokenID) != -1))
/*     */         {
/* 154 */           ErrorManager.grammarWarning(105, this.grammar, tok, tokenID);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 162 */     for (Iterator it = this.grammar.scopedRuleRefs.iterator(); it.hasNext(); ) {
/* 163 */       GrammarAST scopeAST = (GrammarAST)it.next();
/* 164 */       Grammar scopeG = this.grammar.composite.getGrammar(scopeAST.getText());
/* 165 */       GrammarAST refAST = scopeAST.getChild(1);
/* 166 */       String ruleName = refAST.getText();
/* 167 */       if (scopeG == null) {
/* 168 */         ErrorManager.grammarError(156, this.grammar, scopeAST.getToken(), scopeAST.getText(), ruleName);
/*     */       }
/*     */       else
/*     */       {
/* 175 */         Rule rule = this.grammar.getRule(scopeG.name, ruleName);
/* 176 */         if (rule == null)
/* 177 */           ErrorManager.grammarError(157, this.grammar, scopeAST.getToken(), scopeAST.getText(), ruleName);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void checkForGlobalScopeTokenConflict(AttributeScope scope)
/*     */   {
/* 188 */     if (this.grammar.getTokenType(scope.getName()) != -7)
/* 189 */       ErrorManager.grammarError(118, this.grammar, null, scope.getName());
/*     */   }
/*     */ 
/*     */   public void checkForRuleScopeAttributeConflict(Rule r, Attribute attribute)
/*     */   {
/* 198 */     int msgID = 0;
/* 199 */     Object arg2 = null;
/* 200 */     String attrName = attribute.name;
/* 201 */     if (r.name.equals(attrName)) {
/* 202 */       msgID = 123;
/* 203 */       arg2 = r.name;
/*     */     }
/* 205 */     else if (((r.returnScope != null) && (r.returnScope.getAttribute(attrName) != null)) || ((r.parameterScope != null) && (r.parameterScope.getAttribute(attrName) != null)))
/*     */     {
/* 208 */       msgID = 124;
/* 209 */       arg2 = r.name;
/*     */     }
/* 211 */     if (msgID != 0)
/* 212 */       ErrorManager.grammarError(msgID, this.grammar, r.tree.getToken(), attrName, arg2);
/*     */   }
/*     */ 
/*     */   protected void checkForLabelConflict(Rule r, Token label)
/*     */   {
/* 222 */     int msgID = 0;
/* 223 */     Object arg2 = null;
/* 224 */     if (this.grammar.getGlobalScope(label.getText()) != null) {
/* 225 */       msgID = 118;
/*     */     }
/* 227 */     else if (this.grammar.getRule(label.getText()) != null) {
/* 228 */       msgID = 119;
/*     */     }
/* 230 */     else if (this.grammar.getTokenType(label.getText()) != -7) {
/* 231 */       msgID = 120;
/*     */     }
/* 233 */     else if ((r.ruleScope != null) && (r.ruleScope.getAttribute(label.getText()) != null)) {
/* 234 */       msgID = 121;
/* 235 */       arg2 = r.name;
/*     */     }
/* 237 */     else if (((r.returnScope != null) && (r.returnScope.getAttribute(label.getText()) != null)) || ((r.parameterScope != null) && (r.parameterScope.getAttribute(label.getText()) != null)))
/*     */     {
/* 240 */       msgID = 122;
/* 241 */       arg2 = r.name;
/*     */     }
/* 243 */     if (msgID != 0)
/* 244 */       ErrorManager.grammarError(msgID, this.grammar, label, label.getText(), arg2);
/*     */   }
/*     */ 
/*     */   public boolean checkForLabelTypeMismatch(Rule r, Token label, int type)
/*     */   {
/* 251 */     Grammar.LabelElementPair prevLabelPair = (Grammar.LabelElementPair)r.labelNameSpace.get(label.getText());
/*     */ 
/* 253 */     if (prevLabelPair != null)
/*     */     {
/* 255 */       if (prevLabelPair.type != type) {
/* 256 */         String typeMismatchExpr = Grammar.LabelTypeToString[type] + "!=" + Grammar.LabelTypeToString[prevLabelPair.type];
/*     */ 
/* 259 */         ErrorManager.grammarError(125, this.grammar, label, label.getText(), typeMismatchExpr);
/*     */ 
/* 265 */         return true;
/*     */       }
/*     */     }
/* 268 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.tool.NameSpaceChecker
 * JD-Core Version:    0.6.2
 */