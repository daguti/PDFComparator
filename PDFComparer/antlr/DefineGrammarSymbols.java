/*     */ package antlr;
/*     */ 
/*     */ import antlr.collections.impl.BitSet;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ public class DefineGrammarSymbols
/*     */   implements ANTLRGrammarParseBehavior
/*     */ {
/*  22 */   protected Hashtable grammars = new Hashtable();
/*     */ 
/*  24 */   protected Hashtable tokenManagers = new Hashtable();
/*     */   protected Grammar grammar;
/*     */   protected Tool tool;
/*     */   LLkAnalyzer analyzer;
/*     */   String[] args;
/*     */   static final String DEFAULT_TOKENMANAGER_NAME = "*default";
/*  38 */   protected Hashtable headerActions = new Hashtable();
/*     */ 
/*  40 */   Token thePreambleAction = new CommonToken(0, "");
/*     */ 
/*  42 */   String language = "Java";
/*     */ 
/*  44 */   protected int numLexers = 0;
/*  45 */   protected int numParsers = 0;
/*  46 */   protected int numTreeParsers = 0;
/*     */ 
/*     */   public DefineGrammarSymbols(Tool paramTool, String[] paramArrayOfString, LLkAnalyzer paramLLkAnalyzer) {
/*  49 */     this.tool = paramTool;
/*  50 */     this.args = paramArrayOfString;
/*  51 */     this.analyzer = paramLLkAnalyzer;
/*     */   }
/*     */ 
/*     */   public void _refStringLiteral(Token paramToken1, Token paramToken2, int paramInt, boolean paramBoolean) {
/*  55 */     if (!(this.grammar instanceof LexerGrammar))
/*     */     {
/*  57 */       String str = paramToken1.getText();
/*  58 */       if (this.grammar.tokenManager.getTokenSymbol(str) != null)
/*     */       {
/*  60 */         return;
/*     */       }
/*  62 */       StringLiteralSymbol localStringLiteralSymbol = new StringLiteralSymbol(str);
/*  63 */       int i = this.grammar.tokenManager.nextTokenType();
/*  64 */       localStringLiteralSymbol.setTokenType(i);
/*  65 */       this.grammar.tokenManager.define(localStringLiteralSymbol);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void _refToken(Token paramToken1, Token paramToken2, Token paramToken3, Token paramToken4, boolean paramBoolean1, int paramInt, boolean paramBoolean2)
/*     */   {
/*  77 */     String str = paramToken2.getText();
/*  78 */     if (!this.grammar.tokenManager.tokenDefined(str))
/*     */     {
/*  84 */       int i = this.grammar.tokenManager.nextTokenType();
/*  85 */       TokenSymbol localTokenSymbol = new TokenSymbol(str);
/*  86 */       localTokenSymbol.setTokenType(i);
/*  87 */       this.grammar.tokenManager.define(localTokenSymbol);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void abortGrammar()
/*     */   {
/*  93 */     if ((this.grammar != null) && (this.grammar.getClassName() != null)) {
/*  94 */       this.grammars.remove(this.grammar.getClassName());
/*     */     }
/*  96 */     this.grammar = null;
/*     */   }
/*     */ 
/*     */   public void beginAlt(boolean paramBoolean)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void beginChildList()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void beginExceptionGroup()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void beginExceptionSpec(Token paramToken)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void beginSubRule(Token paramToken1, Token paramToken2, boolean paramBoolean)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void beginTree(Token paramToken) throws SemanticException
/*     */   {
/*     */   }
/*     */ 
/*     */   public void defineRuleName(Token paramToken, String paramString1, boolean paramBoolean, String paramString2) throws SemanticException {
/* 124 */     String str = paramToken.getText();
/*     */ 
/* 127 */     if (paramToken.type == 24)
/*     */     {
/* 129 */       str = CodeGenerator.encodeLexerRuleName(str);
/*     */ 
/* 131 */       if (!this.grammar.tokenManager.tokenDefined(paramToken.getText())) {
/* 132 */         int i = this.grammar.tokenManager.nextTokenType();
/* 133 */         TokenSymbol localTokenSymbol = new TokenSymbol(paramToken.getText());
/* 134 */         localTokenSymbol.setTokenType(i);
/* 135 */         this.grammar.tokenManager.define(localTokenSymbol);
/*     */       }
/*     */     }
/*     */     RuleSymbol localRuleSymbol;
/* 140 */     if (this.grammar.isDefined(str))
/*     */     {
/* 142 */       localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(str);
/*     */ 
/* 144 */       if (localRuleSymbol.isDefined())
/* 145 */         this.tool.error("redefinition of rule " + str, this.grammar.getFilename(), paramToken.getLine(), paramToken.getColumn());
/*     */     }
/*     */     else
/*     */     {
/* 149 */       localRuleSymbol = new RuleSymbol(str);
/* 150 */       this.grammar.define(localRuleSymbol);
/*     */     }
/* 152 */     localRuleSymbol.setDefined();
/* 153 */     localRuleSymbol.access = paramString1;
/* 154 */     localRuleSymbol.comment = paramString2;
/*     */   }
/*     */ 
/*     */   public void defineToken(Token paramToken1, Token paramToken2)
/*     */   {
/* 161 */     String str1 = null;
/* 162 */     String str2 = null;
/* 163 */     if (paramToken1 != null) {
/* 164 */       str1 = paramToken1.getText();
/*     */     }
/* 166 */     if (paramToken2 != null) {
/* 167 */       str2 = paramToken2.getText();
/*     */     }
/*     */ 
/* 171 */     if (str2 != null) {
/* 172 */       StringLiteralSymbol localStringLiteralSymbol = (StringLiteralSymbol)this.grammar.tokenManager.getTokenSymbol(str2);
/* 173 */       if (localStringLiteralSymbol != null)
/*     */       {
/* 179 */         if ((str1 == null) || (localStringLiteralSymbol.getLabel() != null)) {
/* 180 */           this.tool.warning("Redefinition of literal in tokens {...}: " + str2, this.grammar.getFilename(), paramToken2.getLine(), paramToken2.getColumn());
/* 181 */           return;
/*     */         }
/* 183 */         if (str1 != null)
/*     */         {
/* 185 */           localStringLiteralSymbol.setLabel(str1);
/*     */ 
/* 187 */           this.grammar.tokenManager.mapToTokenSymbol(str1, localStringLiteralSymbol);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 192 */       if (str1 != null) {
/* 193 */         TokenSymbol localTokenSymbol1 = this.grammar.tokenManager.getTokenSymbol(str1);
/* 194 */         if (localTokenSymbol1 != null)
/*     */         {
/* 197 */           if ((localTokenSymbol1 instanceof StringLiteralSymbol)) {
/* 198 */             this.tool.warning("Redefinition of token in tokens {...}: " + str1, this.grammar.getFilename(), paramToken2.getLine(), paramToken2.getColumn());
/* 199 */             return;
/*     */           }
/*     */ 
/* 207 */           int k = localTokenSymbol1.getTokenType();
/*     */ 
/* 209 */           localStringLiteralSymbol = new StringLiteralSymbol(str2);
/* 210 */           localStringLiteralSymbol.setTokenType(k);
/* 211 */           localStringLiteralSymbol.setLabel(str1);
/*     */ 
/* 213 */           this.grammar.tokenManager.define(localStringLiteralSymbol);
/*     */ 
/* 215 */           this.grammar.tokenManager.mapToTokenSymbol(str1, localStringLiteralSymbol);
/* 216 */           return;
/*     */         }
/*     */       }
/*     */ 
/* 220 */       localStringLiteralSymbol = new StringLiteralSymbol(str2);
/* 221 */       int j = this.grammar.tokenManager.nextTokenType();
/* 222 */       localStringLiteralSymbol.setTokenType(j);
/* 223 */       localStringLiteralSymbol.setLabel(str1);
/* 224 */       this.grammar.tokenManager.define(localStringLiteralSymbol);
/* 225 */       if (str1 != null)
/*     */       {
/* 227 */         this.grammar.tokenManager.mapToTokenSymbol(str1, localStringLiteralSymbol);
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 233 */       if (this.grammar.tokenManager.tokenDefined(str1)) {
/* 234 */         this.tool.warning("Redefinition of token in tokens {...}: " + str1, this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/* 235 */         return;
/*     */       }
/* 237 */       int i = this.grammar.tokenManager.nextTokenType();
/* 238 */       TokenSymbol localTokenSymbol2 = new TokenSymbol(str1);
/* 239 */       localTokenSymbol2.setTokenType(i);
/* 240 */       this.grammar.tokenManager.define(localTokenSymbol2);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void endAlt()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void endChildList()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void endExceptionGroup()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void endExceptionSpec()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void endGrammar()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void endOptions()
/*     */   {
/*     */     Object localObject;
/* 266 */     if ((this.grammar.exportVocab == null) && (this.grammar.importVocab == null)) {
/* 267 */       this.grammar.exportVocab = this.grammar.getClassName();
/*     */ 
/* 269 */       if (this.tokenManagers.containsKey("*default"))
/*     */       {
/* 271 */         this.grammar.exportVocab = "*default";
/* 272 */         localObject = (TokenManager)this.tokenManagers.get("*default");
/*     */ 
/* 274 */         this.grammar.setTokenManager((TokenManager)localObject);
/* 275 */         return;
/*     */       }
/*     */ 
/* 279 */       localObject = new SimpleTokenManager(this.grammar.exportVocab, this.tool);
/* 280 */       this.grammar.setTokenManager((TokenManager)localObject);
/*     */ 
/* 282 */       this.tokenManagers.put(this.grammar.exportVocab, localObject);
/*     */ 
/* 284 */       this.tokenManagers.put("*default", localObject);
/*     */       return;
/*     */     }
/*     */     TokenManager localTokenManager;
/* 289 */     if ((this.grammar.exportVocab == null) && (this.grammar.importVocab != null)) {
/* 290 */       this.grammar.exportVocab = this.grammar.getClassName();
/*     */ 
/* 292 */       if (this.grammar.importVocab.equals(this.grammar.exportVocab)) {
/* 293 */         this.tool.warning("Grammar " + this.grammar.getClassName() + " cannot have importVocab same as default output vocab (grammar name); ignored.");
/*     */ 
/* 296 */         this.grammar.importVocab = null;
/* 297 */         endOptions();
/* 298 */         return;
/*     */       }
/*     */ 
/* 302 */       if (this.tokenManagers.containsKey(this.grammar.importVocab))
/*     */       {
/* 306 */         localObject = (TokenManager)this.tokenManagers.get(this.grammar.importVocab);
/*     */ 
/* 308 */         localTokenManager = (TokenManager)((TokenManager)localObject).clone();
/* 309 */         localTokenManager.setName(this.grammar.exportVocab);
/*     */ 
/* 311 */         localTokenManager.setReadOnly(false);
/* 312 */         this.grammar.setTokenManager(localTokenManager);
/* 313 */         this.tokenManagers.put(this.grammar.exportVocab, localTokenManager);
/* 314 */         return;
/*     */       }
/*     */ 
/* 318 */       localObject = new ImportVocabTokenManager(this.grammar, this.grammar.importVocab + CodeGenerator.TokenTypesFileSuffix + CodeGenerator.TokenTypesFileExt, this.grammar.exportVocab, this.tool);
/*     */ 
/* 323 */       ((ImportVocabTokenManager)localObject).setReadOnly(false);
/*     */ 
/* 325 */       this.tokenManagers.put(this.grammar.exportVocab, localObject);
/*     */ 
/* 328 */       this.grammar.setTokenManager((TokenManager)localObject);
/*     */ 
/* 331 */       if (!this.tokenManagers.containsKey("*default")) {
/* 332 */         this.tokenManagers.put("*default", localObject);
/*     */       }
/*     */ 
/* 335 */       return;
/*     */     }
/*     */ 
/* 339 */     if ((this.grammar.exportVocab != null) && (this.grammar.importVocab == null))
/*     */     {
/* 341 */       if (this.tokenManagers.containsKey(this.grammar.exportVocab))
/*     */       {
/* 343 */         localObject = (TokenManager)this.tokenManagers.get(this.grammar.exportVocab);
/*     */ 
/* 345 */         this.grammar.setTokenManager((TokenManager)localObject);
/* 346 */         return;
/*     */       }
/*     */ 
/* 350 */       localObject = new SimpleTokenManager(this.grammar.exportVocab, this.tool);
/* 351 */       this.grammar.setTokenManager((TokenManager)localObject);
/*     */ 
/* 353 */       this.tokenManagers.put(this.grammar.exportVocab, localObject);
/*     */ 
/* 355 */       if (!this.tokenManagers.containsKey("*default")) {
/* 356 */         this.tokenManagers.put("*default", localObject);
/*     */       }
/* 358 */       return;
/*     */     }
/*     */ 
/* 362 */     if ((this.grammar.exportVocab != null) && (this.grammar.importVocab != null))
/*     */     {
/* 364 */       if (this.grammar.importVocab.equals(this.grammar.exportVocab)) {
/* 365 */         this.tool.error("exportVocab of " + this.grammar.exportVocab + " same as importVocab; probably not what you want");
/*     */       }
/*     */ 
/* 368 */       if (this.tokenManagers.containsKey(this.grammar.importVocab))
/*     */       {
/* 371 */         localObject = (TokenManager)this.tokenManagers.get(this.grammar.importVocab);
/*     */ 
/* 373 */         localTokenManager = (TokenManager)((TokenManager)localObject).clone();
/* 374 */         localTokenManager.setName(this.grammar.exportVocab);
/*     */ 
/* 376 */         localTokenManager.setReadOnly(false);
/* 377 */         this.grammar.setTokenManager(localTokenManager);
/* 378 */         this.tokenManagers.put(this.grammar.exportVocab, localTokenManager);
/* 379 */         return;
/*     */       }
/*     */ 
/* 382 */       localObject = new ImportVocabTokenManager(this.grammar, this.grammar.importVocab + CodeGenerator.TokenTypesFileSuffix + CodeGenerator.TokenTypesFileExt, this.grammar.exportVocab, this.tool);
/*     */ 
/* 387 */       ((ImportVocabTokenManager)localObject).setReadOnly(false);
/*     */ 
/* 389 */       this.tokenManagers.put(this.grammar.exportVocab, localObject);
/*     */ 
/* 391 */       this.grammar.setTokenManager((TokenManager)localObject);
/*     */ 
/* 394 */       if (!this.tokenManagers.containsKey("*default")) {
/* 395 */         this.tokenManagers.put("*default", localObject);
/*     */       }
/*     */ 
/* 398 */       return;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void endRule(String paramString)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void endSubRule()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void endTree()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void hasError()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void noASTSubRule()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void oneOrMoreSubRule()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void optionalSubRule()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void setUserExceptions(String paramString)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void refAction(Token paramToken)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void refArgAction(Token paramToken)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void refCharLiteral(Token paramToken1, Token paramToken2, boolean paramBoolean1, int paramInt, boolean paramBoolean2)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void refCharRange(Token paramToken1, Token paramToken2, Token paramToken3, int paramInt, boolean paramBoolean)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void refElementOption(Token paramToken1, Token paramToken2)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void refTokensSpecElementOption(Token paramToken1, Token paramToken2, Token paramToken3)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void refExceptionHandler(Token paramToken1, Token paramToken2)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void refHeaderAction(Token paramToken1, Token paramToken2)
/*     */   {
/*     */     String str;
/* 451 */     if (paramToken1 == null)
/* 452 */       str = "";
/*     */     else {
/* 454 */       str = StringUtils.stripFrontBack(paramToken1.getText(), "\"", "\"");
/*     */     }
/*     */ 
/* 458 */     if (this.headerActions.containsKey(str)) {
/* 459 */       if (str.equals(""))
/* 460 */         this.tool.error(paramToken2.getLine() + ": header action already defined");
/*     */       else
/* 462 */         this.tool.error(paramToken2.getLine() + ": header action '" + str + "' already defined");
/*     */     }
/* 464 */     this.headerActions.put(str, paramToken2);
/*     */   }
/*     */ 
/*     */   public String getHeaderAction(String paramString) {
/* 468 */     Token localToken = (Token)this.headerActions.get(paramString);
/* 469 */     if (localToken == null) {
/* 470 */       return "";
/*     */     }
/* 472 */     return localToken.getText();
/*     */   }
/*     */ 
/*     */   public int getHeaderActionLine(String paramString) {
/* 476 */     Token localToken = (Token)this.headerActions.get(paramString);
/* 477 */     if (localToken == null) {
/* 478 */       return 0;
/*     */     }
/* 480 */     return localToken.getLine();
/*     */   }
/*     */ 
/*     */   public void refInitAction(Token paramToken) {
/*     */   }
/*     */ 
/*     */   public void refMemberAction(Token paramToken) {
/*     */   }
/*     */ 
/*     */   public void refPreambleAction(Token paramToken) {
/* 490 */     this.thePreambleAction = paramToken;
/*     */   }
/*     */ 
/*     */   public void refReturnAction(Token paramToken)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void refRule(Token paramToken1, Token paramToken2, Token paramToken3, Token paramToken4, int paramInt)
/*     */   {
/* 501 */     String str = paramToken2.getText();
/*     */ 
/* 503 */     if (paramToken2.type == 24)
/*     */     {
/* 505 */       str = CodeGenerator.encodeLexerRuleName(str);
/*     */     }
/* 507 */     if (!this.grammar.isDefined(str))
/* 508 */       this.grammar.define(new RuleSymbol(str));
/*     */   }
/*     */ 
/*     */   public void refSemPred(Token paramToken)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void refStringLiteral(Token paramToken1, Token paramToken2, int paramInt, boolean paramBoolean)
/*     */   {
/* 519 */     _refStringLiteral(paramToken1, paramToken2, paramInt, paramBoolean);
/*     */   }
/*     */ 
/*     */   public void refToken(Token paramToken1, Token paramToken2, Token paramToken3, Token paramToken4, boolean paramBoolean1, int paramInt, boolean paramBoolean2)
/*     */   {
/* 525 */     _refToken(paramToken1, paramToken2, paramToken3, paramToken4, paramBoolean1, paramInt, paramBoolean2);
/*     */   }
/*     */ 
/*     */   public void refTokenRange(Token paramToken1, Token paramToken2, Token paramToken3, int paramInt, boolean paramBoolean)
/*     */   {
/* 531 */     if (paramToken1.getText().charAt(0) == '"') {
/* 532 */       refStringLiteral(paramToken1, null, 1, paramBoolean);
/*     */     }
/*     */     else {
/* 535 */       _refToken(null, paramToken1, null, null, false, 1, paramBoolean);
/*     */     }
/* 537 */     if (paramToken2.getText().charAt(0) == '"') {
/* 538 */       _refStringLiteral(paramToken2, null, 1, paramBoolean);
/*     */     }
/*     */     else
/* 541 */       _refToken(null, paramToken2, null, null, false, 1, paramBoolean);
/*     */   }
/*     */ 
/*     */   public void refTreeSpecifier(Token paramToken)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void refWildcard(Token paramToken1, Token paramToken2, int paramInt)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void reset() {
/* 553 */     this.grammar = null;
/*     */   }
/*     */ 
/*     */   public void setArgOfRuleRef(Token paramToken)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void setCharVocabulary(BitSet paramBitSet)
/*     */   {
/* 562 */     ((LexerGrammar)this.grammar).setCharVocabulary(paramBitSet);
/*     */   }
/*     */ 
/*     */   public void setFileOption(Token paramToken1, Token paramToken2, String paramString)
/*     */   {
/* 571 */     if (paramToken1.getText().equals("language")) {
/* 572 */       if (paramToken2.getType() == 6) {
/* 573 */         this.language = StringUtils.stripBack(StringUtils.stripFront(paramToken2.getText(), '"'), '"');
/*     */       }
/* 575 */       else if ((paramToken2.getType() == 24) || (paramToken2.getType() == 41)) {
/* 576 */         this.language = paramToken2.getText();
/*     */       }
/*     */       else {
/* 579 */         this.tool.error("language option must be string or identifier", paramString, paramToken2.getLine(), paramToken2.getColumn());
/*     */       }
/*     */     }
/* 582 */     else if (paramToken1.getText().equals("mangleLiteralPrefix")) {
/* 583 */       if (paramToken2.getType() == 6) {
/* 584 */         this.tool.literalsPrefix = StringUtils.stripFrontBack(paramToken2.getText(), "\"", "\"");
/*     */       }
/*     */       else {
/* 587 */         this.tool.error("mangleLiteralPrefix option must be string", paramString, paramToken2.getLine(), paramToken2.getColumn());
/*     */       }
/*     */     }
/* 590 */     else if (paramToken1.getText().equals("upperCaseMangledLiterals")) {
/* 591 */       if (paramToken2.getText().equals("true")) {
/* 592 */         this.tool.upperCaseMangledLiterals = true;
/*     */       }
/* 594 */       else if (paramToken2.getText().equals("false")) {
/* 595 */         this.tool.upperCaseMangledLiterals = false;
/*     */       }
/*     */       else {
/* 598 */         this.grammar.antlrTool.error("Value for upperCaseMangledLiterals must be true or false", paramString, paramToken1.getLine(), paramToken1.getColumn());
/*     */       }
/*     */     }
/* 601 */     else if ((paramToken1.getText().equals("namespaceStd")) || (paramToken1.getText().equals("namespaceAntlr")) || (paramToken1.getText().equals("genHashLines")))
/*     */     {
/* 605 */       if (!this.language.equals("Cpp")) {
/* 606 */         this.tool.error(paramToken1.getText() + " option only valid for C++", paramString, paramToken1.getLine(), paramToken1.getColumn());
/*     */       }
/* 609 */       else if (paramToken1.getText().equals("noConstructors")) {
/* 610 */         if ((!paramToken2.getText().equals("true")) && (!paramToken2.getText().equals("false")))
/* 611 */           this.tool.error("noConstructors option must be true or false", paramString, paramToken2.getLine(), paramToken2.getColumn());
/* 612 */         this.tool.noConstructors = paramToken2.getText().equals("true");
/* 613 */       } else if (paramToken1.getText().equals("genHashLines")) {
/* 614 */         if ((!paramToken2.getText().equals("true")) && (!paramToken2.getText().equals("false")))
/* 615 */           this.tool.error("genHashLines option must be true or false", paramString, paramToken2.getLine(), paramToken2.getColumn());
/* 616 */         this.tool.genHashLines = paramToken2.getText().equals("true");
/*     */       }
/* 619 */       else if (paramToken2.getType() != 6) {
/* 620 */         this.tool.error(paramToken1.getText() + " option must be a string", paramString, paramToken2.getLine(), paramToken2.getColumn());
/*     */       }
/* 623 */       else if (paramToken1.getText().equals("namespaceStd")) {
/* 624 */         this.tool.namespaceStd = paramToken2.getText();
/* 625 */       } else if (paramToken1.getText().equals("namespaceAntlr")) {
/* 626 */         this.tool.namespaceAntlr = paramToken2.getText();
/*     */       }
/*     */ 
/*     */     }
/* 631 */     else if (paramToken1.getText().equals("namespace")) {
/* 632 */       if ((!this.language.equals("Cpp")) && (!this.language.equals("CSharp")))
/*     */       {
/* 634 */         this.tool.error(paramToken1.getText() + " option only valid for C++ and C# (a.k.a CSharp)", paramString, paramToken1.getLine(), paramToken1.getColumn());
/*     */       }
/* 638 */       else if (paramToken2.getType() != 6)
/*     */       {
/* 640 */         this.tool.error(paramToken1.getText() + " option must be a string", paramString, paramToken2.getLine(), paramToken2.getColumn());
/*     */       }
/* 643 */       else if (paramToken1.getText().equals("namespace")) {
/* 644 */         this.tool.setNameSpace(paramToken2.getText());
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 649 */       this.tool.error("Invalid file-level option: " + paramToken1.getText(), paramString, paramToken1.getLine(), paramToken2.getColumn());
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setGrammarOption(Token paramToken1, Token paramToken2)
/*     */   {
/* 659 */     if ((paramToken1.getText().equals("tokdef")) || (paramToken1.getText().equals("tokenVocabulary"))) {
/* 660 */       this.tool.error("tokdef/tokenVocabulary options are invalid >= ANTLR 2.6.0.\n  Use importVocab/exportVocab instead.  Please see the documentation.\n  The previous options were so heinous that Terence changed the whole\n  vocabulary mechanism; it was better to change the names rather than\n  subtly change the functionality of the known options.  Sorry!", this.grammar.getFilename(), paramToken2.getLine(), paramToken2.getColumn());
/*     */     }
/* 666 */     else if ((paramToken1.getText().equals("literal")) && ((this.grammar instanceof LexerGrammar)))
/*     */     {
/* 668 */       this.tool.error("the literal option is invalid >= ANTLR 2.6.0.\n  Use the \"tokens {...}\" mechanism instead.", this.grammar.getFilename(), paramToken2.getLine(), paramToken2.getColumn());
/*     */     }
/* 672 */     else if (paramToken1.getText().equals("exportVocab"))
/*     */     {
/* 674 */       if ((paramToken2.getType() == 41) || (paramToken2.getType() == 24)) {
/* 675 */         this.grammar.exportVocab = paramToken2.getText();
/*     */       }
/*     */       else {
/* 678 */         this.tool.error("exportVocab must be an identifier", this.grammar.getFilename(), paramToken2.getLine(), paramToken2.getColumn());
/*     */       }
/*     */     }
/* 681 */     else if (paramToken1.getText().equals("importVocab")) {
/* 682 */       if ((paramToken2.getType() == 41) || (paramToken2.getType() == 24)) {
/* 683 */         this.grammar.importVocab = paramToken2.getText();
/*     */       }
/*     */       else {
/* 686 */         this.tool.error("importVocab must be an identifier", this.grammar.getFilename(), paramToken2.getLine(), paramToken2.getColumn());
/*     */       }
/*     */     }
/* 689 */     else if (paramToken1.getText().equals("k")) {
/* 690 */       if (((this.grammar instanceof TreeWalkerGrammar)) && (!paramToken2.getText().equals("1")))
/*     */       {
/* 692 */         this.tool.error("Treewalkers only support k=1", this.grammar.getFilename(), paramToken2.getLine(), paramToken2.getColumn());
/*     */       }
/*     */       else {
/* 695 */         this.grammar.setOption(paramToken1.getText(), paramToken2);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 700 */       this.grammar.setOption(paramToken1.getText(), paramToken2);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setRuleOption(Token paramToken1, Token paramToken2)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void setSubruleOption(Token paramToken1, Token paramToken2) {
/*     */   }
/*     */ 
/*     */   public void startLexer(String paramString1, Token paramToken, String paramString2, String paramString3) {
/* 712 */     if (this.numLexers > 0) {
/* 713 */       this.tool.panic("You may only have one lexer per grammar file: class " + paramToken.getText());
/*     */     }
/* 715 */     this.numLexers += 1;
/* 716 */     reset();
/*     */ 
/* 719 */     Grammar localGrammar = (Grammar)this.grammars.get(paramToken);
/* 720 */     if (localGrammar != null) {
/* 721 */       if (!(localGrammar instanceof LexerGrammar)) {
/* 722 */         this.tool.panic("'" + paramToken.getText() + "' is already defined as a non-lexer");
/*     */       }
/*     */       else {
/* 725 */         this.tool.panic("Lexer '" + paramToken.getText() + "' is already defined");
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 730 */       LexerGrammar localLexerGrammar = new LexerGrammar(paramToken.getText(), this.tool, paramString2);
/* 731 */       localLexerGrammar.comment = paramString3;
/* 732 */       localLexerGrammar.processArguments(this.args);
/* 733 */       localLexerGrammar.setFilename(paramString1);
/* 734 */       this.grammars.put(localLexerGrammar.getClassName(), localLexerGrammar);
/*     */ 
/* 736 */       localLexerGrammar.preambleAction = this.thePreambleAction;
/* 737 */       this.thePreambleAction = new CommonToken(0, "");
/*     */ 
/* 739 */       this.grammar = localLexerGrammar;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void startParser(String paramString1, Token paramToken, String paramString2, String paramString3)
/*     */   {
/* 745 */     if (this.numParsers > 0) {
/* 746 */       this.tool.panic("You may only have one parser per grammar file: class " + paramToken.getText());
/*     */     }
/* 748 */     this.numParsers += 1;
/* 749 */     reset();
/*     */ 
/* 752 */     Grammar localGrammar = (Grammar)this.grammars.get(paramToken);
/* 753 */     if (localGrammar != null) {
/* 754 */       if (!(localGrammar instanceof ParserGrammar)) {
/* 755 */         this.tool.panic("'" + paramToken.getText() + "' is already defined as a non-parser");
/*     */       }
/*     */       else {
/* 758 */         this.tool.panic("Parser '" + paramToken.getText() + "' is already defined");
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 763 */       this.grammar = new ParserGrammar(paramToken.getText(), this.tool, paramString2);
/* 764 */       this.grammar.comment = paramString3;
/* 765 */       this.grammar.processArguments(this.args);
/* 766 */       this.grammar.setFilename(paramString1);
/* 767 */       this.grammars.put(this.grammar.getClassName(), this.grammar);
/*     */ 
/* 769 */       this.grammar.preambleAction = this.thePreambleAction;
/* 770 */       this.thePreambleAction = new CommonToken(0, "");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void startTreeWalker(String paramString1, Token paramToken, String paramString2, String paramString3)
/*     */   {
/* 776 */     if (this.numTreeParsers > 0) {
/* 777 */       this.tool.panic("You may only have one tree parser per grammar file: class " + paramToken.getText());
/*     */     }
/* 779 */     this.numTreeParsers += 1;
/* 780 */     reset();
/*     */ 
/* 783 */     Grammar localGrammar = (Grammar)this.grammars.get(paramToken);
/* 784 */     if (localGrammar != null) {
/* 785 */       if (!(localGrammar instanceof TreeWalkerGrammar)) {
/* 786 */         this.tool.panic("'" + paramToken.getText() + "' is already defined as a non-tree-walker");
/*     */       }
/*     */       else {
/* 789 */         this.tool.panic("Tree-walker '" + paramToken.getText() + "' is already defined");
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 794 */       this.grammar = new TreeWalkerGrammar(paramToken.getText(), this.tool, paramString2);
/* 795 */       this.grammar.comment = paramString3;
/* 796 */       this.grammar.processArguments(this.args);
/* 797 */       this.grammar.setFilename(paramString1);
/* 798 */       this.grammars.put(this.grammar.getClassName(), this.grammar);
/*     */ 
/* 800 */       this.grammar.preambleAction = this.thePreambleAction;
/* 801 */       this.thePreambleAction = new CommonToken(0, "");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void synPred()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void zeroOrMoreSubRule()
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.DefineGrammarSymbols
 * JD-Core Version:    0.6.2
 */