/*     */ package org.antlr.tool;
/*     */ 
/*     */ import antlr.collections.AST;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.antlr.grammar.v2.AssignTokenTypesWalker;
/*     */ import org.antlr.misc.Utils;
/*     */ 
/*     */ public class AssignTokenTypesBehavior extends AssignTokenTypesWalker
/*     */ {
/*  38 */   protected static final Integer UNASSIGNED = Utils.integer(-1);
/*  39 */   protected static final Integer UNASSIGNED_IN_PARSER_RULE = Utils.integer(-2);
/*     */ 
/*  41 */   protected Map<String, Integer> stringLiterals = new LinkedHashMap();
/*  42 */   protected Map<String, Integer> tokens = new LinkedHashMap();
/*  43 */   protected Map<String, String> aliases = new LinkedHashMap();
/*  44 */   protected Map<String, String> aliasesReverseIndex = new HashMap();
/*     */ 
/*  49 */   protected Set<String> tokenRuleDefs = new HashSet();
/*     */ 
/*     */   protected void init(Grammar g)
/*     */   {
/*  53 */     this.grammar = g;
/*  54 */     this.currentRuleName = null;
/*  55 */     if (stringAlias == null)
/*     */     {
/*  57 */       initASTPatterns();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void trackString(GrammarAST t)
/*     */   {
/*  65 */     if ((this.currentRuleName == null) && (this.grammar.type == 1)) {
/*  66 */       ErrorManager.grammarError(108, this.grammar, t.token, t.getText());
/*     */ 
/*  70 */       return;
/*     */     }
/*     */ 
/*  75 */     if ((this.grammar.getGrammarIsRoot()) && (this.grammar.type == 2) && (this.grammar.getTokenType(t.getText()) == -7))
/*     */     {
/*  79 */       ErrorManager.grammarError(107, this.grammar, t.token, t.getText());
/*     */     }
/*     */ 
/*  85 */     if (this.grammar.type == 1) {
/*  86 */       return;
/*     */     }
/*     */ 
/*  90 */     if (((this.currentRuleName == null) || (Character.isLowerCase(this.currentRuleName.charAt(0)))) && (this.grammar.getTokenType(t.getText()) == -7))
/*     */     {
/*  94 */       this.stringLiterals.put(t.getText(), UNASSIGNED_IN_PARSER_RULE);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void trackToken(GrammarAST t)
/*     */   {
/* 103 */     if ((this.grammar.getTokenType(t.getText()) == -7) && (this.tokens.get(t.getText()) == null))
/*     */     {
/* 106 */       this.tokens.put(t.getText(), UNASSIGNED);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void trackTokenRule(GrammarAST t, GrammarAST modifier, GrammarAST block)
/*     */   {
/* 116 */     if ((this.grammar.type == 1) || (this.grammar.type == 4)) {
/* 117 */       if (!Character.isUpperCase(t.getText().charAt(0))) {
/* 118 */         return;
/*     */       }
/* 120 */       if (t.getText().equals("Tokens"))
/*     */       {
/* 122 */         return;
/*     */       }
/*     */ 
/* 127 */       this.grammar.composite.lexerRules.add(t.getText());
/*     */ 
/* 129 */       int existing = this.grammar.getTokenType(t.getText());
/* 130 */       if (existing == -7) {
/* 131 */         this.tokens.put(t.getText(), UNASSIGNED);
/*     */       }
/*     */ 
/* 135 */       if ((block.hasSameTreeStructure(charAlias)) || (block.hasSameTreeStructure(stringAlias)) || (block.hasSameTreeStructure(charAlias2)) || (block.hasSameTreeStructure(stringAlias2)))
/*     */       {
/* 140 */         this.tokenRuleDefs.add(t.getText());
/*     */ 
/* 147 */         if ((this.grammar.type == 4) || (this.grammar.type == 1))
/*     */         {
/* 149 */           alias(t, (GrammarAST)block.getFirstChild().getFirstChild());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void alias(GrammarAST t, GrammarAST s)
/*     */   {
/* 158 */     String tokenID = t.getText();
/* 159 */     String literal = s.getText();
/* 160 */     String prevAliasLiteralID = (String)this.aliasesReverseIndex.get(literal);
/* 161 */     if (prevAliasLiteralID != null) {
/* 162 */       if (tokenID.equals(prevAliasLiteralID))
/*     */       {
/* 165 */         return;
/*     */       }
/*     */ 
/* 169 */       if ((!this.tokenRuleDefs.contains(tokenID)) || (!this.tokenRuleDefs.contains(prevAliasLiteralID)))
/*     */       {
/* 173 */         ErrorManager.grammarError(158, this.grammar, t.token, tokenID + "=" + literal, prevAliasLiteralID);
/*     */       }
/*     */ 
/* 179 */       return;
/*     */     }
/* 181 */     int existingLiteralType = this.grammar.getTokenType(literal);
/* 182 */     if (existingLiteralType != -7)
/*     */     {
/* 185 */       this.tokens.put(tokenID, new Integer(existingLiteralType));
/*     */     }
/* 187 */     String prevAliasTokenID = (String)this.aliases.get(tokenID);
/* 188 */     if (prevAliasTokenID != null) {
/* 189 */       ErrorManager.grammarError(159, this.grammar, t.token, tokenID + "=" + literal, prevAliasTokenID);
/*     */ 
/* 194 */       return;
/*     */     }
/* 196 */     this.aliases.put(tokenID, literal);
/* 197 */     this.aliasesReverseIndex.put(literal, tokenID);
/*     */   }
/*     */ 
/*     */   public void defineTokens(Grammar root)
/*     */   {
/* 209 */     assignTokenIDTypes(root);
/*     */ 
/* 211 */     aliasTokenIDsAndLiterals(root);
/*     */ 
/* 213 */     assignStringTypes(root);
/*     */ 
/* 220 */     defineTokenNamesAndLiteralsInGrammar(root);
/*     */   }
/*     */ 
/*     */   protected void assignStringTypes(Grammar root)
/*     */   {
/* 245 */     Set s = this.stringLiterals.keySet();
/* 246 */     for (Iterator it = s.iterator(); it.hasNext(); ) {
/* 247 */       String lit = (String)it.next();
/* 248 */       Integer oldTypeI = (Integer)this.stringLiterals.get(lit);
/* 249 */       int oldType = oldTypeI.intValue();
/* 250 */       if (oldType < 4) {
/* 251 */         Integer typeI = Utils.integer(root.getNewTokenType());
/* 252 */         this.stringLiterals.put(lit, typeI);
/*     */ 
/* 255 */         root.defineLexerRuleForStringLiteral(lit, typeI.intValue());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void aliasTokenIDsAndLiterals(Grammar root)
/*     */   {
/* 262 */     if (root.type == 1) {
/* 263 */       return;
/*     */     }
/*     */ 
/* 267 */     Set s = this.aliases.keySet();
/* 268 */     for (Iterator it = s.iterator(); it.hasNext(); ) {
/* 269 */       String tokenID = (String)it.next();
/* 270 */       String literal = (String)this.aliases.get(tokenID);
/* 271 */       if ((literal.charAt(0) == '\'') && (this.stringLiterals.get(literal) != null)) {
/* 272 */         this.stringLiterals.put(literal, this.tokens.get(tokenID));
/*     */ 
/* 274 */         Integer typeI = (Integer)this.tokens.get(tokenID);
/* 275 */         if (!this.tokenRuleDefs.contains(tokenID))
/* 276 */           root.defineLexerRuleForAliasedStringLiteral(tokenID, literal, typeI.intValue());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void assignTokenIDTypes(Grammar root)
/*     */   {
/* 285 */     Set s = this.tokens.keySet();
/* 286 */     for (Iterator it = s.iterator(); it.hasNext(); ) {
/* 287 */       String tokenID = (String)it.next();
/* 288 */       if (this.tokens.get(tokenID) == UNASSIGNED)
/* 289 */         this.tokens.put(tokenID, Utils.integer(root.getNewTokenType()));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void defineTokenNamesAndLiteralsInGrammar(Grammar root)
/*     */   {
/* 296 */     Set s = this.tokens.keySet();
/* 297 */     for (Iterator it = s.iterator(); it.hasNext(); ) {
/* 298 */       String tokenID = (String)it.next();
/* 299 */       int ttype = ((Integer)this.tokens.get(tokenID)).intValue();
/* 300 */       root.defineToken(tokenID, ttype);
/*     */     }
/* 302 */     s = this.stringLiterals.keySet();
/* 303 */     for (Iterator it = s.iterator(); it.hasNext(); ) {
/* 304 */       String lit = (String)it.next();
/* 305 */       int ttype = ((Integer)this.stringLiterals.get(lit)).intValue();
/* 306 */       root.defineToken(lit, ttype);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.tool.AssignTokenTypesBehavior
 * JD-Core Version:    0.6.2
 */