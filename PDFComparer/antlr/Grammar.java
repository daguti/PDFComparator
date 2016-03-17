/*     */ package antlr;
/*     */ 
/*     */ import antlr.collections.impl.Vector;
/*     */ import java.io.IOException;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ public abstract class Grammar
/*     */ {
/*     */   protected Tool antlrTool;
/*     */   protected CodeGenerator generator;
/*     */   protected LLkGrammarAnalyzer theLLkAnalyzer;
/*     */   protected Hashtable symbols;
/*  26 */   protected boolean buildAST = false;
/*  27 */   protected boolean analyzerDebug = false;
/*  28 */   protected boolean interactive = false;
/*  29 */   protected String superClass = null;
/*     */   protected TokenManager tokenManager;
/*  41 */   protected String exportVocab = null;
/*     */ 
/*  45 */   protected String importVocab = null;
/*     */   protected Hashtable options;
/*     */   protected Vector rules;
/*  52 */   protected Token preambleAction = new CommonToken(0, "");
/*  53 */   protected String className = null;
/*  54 */   protected String fileName = null;
/*  55 */   protected Token classMemberAction = new CommonToken(0, "");
/*  56 */   protected boolean hasSyntacticPredicate = false;
/*  57 */   protected boolean hasUserErrorHandling = false;
/*     */ 
/*  60 */   protected int maxk = 1;
/*     */ 
/*  63 */   protected boolean traceRules = false;
/*  64 */   protected boolean debuggingOutput = false;
/*  65 */   protected boolean defaultErrorHandler = true;
/*     */ 
/*  67 */   protected String comment = null;
/*     */ 
/*     */   public Grammar(String paramString1, Tool paramTool, String paramString2) {
/*  70 */     this.className = paramString1;
/*  71 */     this.antlrTool = paramTool;
/*  72 */     this.symbols = new Hashtable();
/*  73 */     this.options = new Hashtable();
/*  74 */     this.rules = new Vector(100);
/*  75 */     this.superClass = paramString2;
/*     */   }
/*     */ 
/*     */   public void define(RuleSymbol paramRuleSymbol)
/*     */   {
/*  80 */     this.rules.appendElement(paramRuleSymbol);
/*     */ 
/*  82 */     this.symbols.put(paramRuleSymbol.getId(), paramRuleSymbol);
/*     */   }
/*     */ 
/*     */   public abstract void generate() throws IOException;
/*     */ 
/*     */   protected String getClassName()
/*     */   {
/*  89 */     return this.className;
/*     */   }
/*     */ 
/*     */   public boolean getDefaultErrorHandler()
/*     */   {
/*  94 */     return this.defaultErrorHandler;
/*     */   }
/*     */ 
/*     */   public String getFilename() {
/*  98 */     return this.fileName;
/*     */   }
/*     */ 
/*     */   public int getIntegerOption(String paramString)
/*     */     throws NumberFormatException
/*     */   {
/* 108 */     Token localToken = (Token)this.options.get(paramString);
/* 109 */     if ((localToken == null) || (localToken.getType() != 20)) {
/* 110 */       throw new NumberFormatException();
/*     */     }
/*     */ 
/* 113 */     return Integer.parseInt(localToken.getText());
/*     */   }
/*     */ 
/*     */   public Token getOption(String paramString)
/*     */   {
/* 122 */     return (Token)this.options.get(paramString);
/*     */   }
/*     */ 
/*     */   protected abstract String getSuperClass();
/*     */ 
/*     */   public GrammarSymbol getSymbol(String paramString)
/*     */   {
/* 129 */     return (GrammarSymbol)this.symbols.get(paramString);
/*     */   }
/*     */ 
/*     */   public Enumeration getSymbols() {
/* 133 */     return this.symbols.elements();
/*     */   }
/*     */ 
/*     */   public boolean hasOption(String paramString)
/*     */   {
/* 141 */     return this.options.containsKey(paramString);
/*     */   }
/*     */ 
/*     */   public boolean isDefined(String paramString)
/*     */   {
/* 146 */     return this.symbols.containsKey(paramString);
/*     */   }
/*     */ 
/*     */   public abstract void processArguments(String[] paramArrayOfString);
/*     */ 
/*     */   public void setCodeGenerator(CodeGenerator paramCodeGenerator)
/*     */   {
/* 153 */     this.generator = paramCodeGenerator;
/*     */   }
/*     */ 
/*     */   public void setFilename(String paramString) {
/* 157 */     this.fileName = paramString;
/*     */   }
/*     */ 
/*     */   public void setGrammarAnalyzer(LLkGrammarAnalyzer paramLLkGrammarAnalyzer) {
/* 161 */     this.theLLkAnalyzer = paramLLkGrammarAnalyzer;
/*     */   }
/*     */ 
/*     */   public boolean setOption(String paramString, Token paramToken)
/*     */   {
/* 175 */     this.options.put(paramString, paramToken);
/* 176 */     String str = paramToken.getText();
/*     */ 
/* 178 */     if (paramString.equals("k")) {
/*     */       try {
/* 180 */         this.maxk = getIntegerOption("k");
/* 181 */         if (this.maxk <= 0) {
/* 182 */           this.antlrTool.error("option 'k' must be greater than 0 (was " + paramToken.getText() + ")", getFilename(), paramToken.getLine(), paramToken.getColumn());
/*     */ 
/* 187 */           this.maxk = 1;
/*     */         }
/*     */       }
/*     */       catch (NumberFormatException localNumberFormatException1) {
/* 191 */         this.antlrTool.error("option 'k' must be an integer (was " + paramToken.getText() + ")", getFilename(), paramToken.getLine(), paramToken.getColumn());
/*     */       }
/* 193 */       return true;
/*     */     }
/*     */     int i;
/* 195 */     if (paramString.equals("codeGenMakeSwitchThreshold")) {
/*     */       try {
/* 197 */         i = getIntegerOption("codeGenMakeSwitchThreshold");
/*     */       }
/*     */       catch (NumberFormatException localNumberFormatException2) {
/* 200 */         this.antlrTool.error("option 'codeGenMakeSwitchThreshold' must be an integer", getFilename(), paramToken.getLine(), paramToken.getColumn());
/*     */       }
/* 202 */       return true;
/*     */     }
/* 204 */     if (paramString.equals("codeGenBitsetTestThreshold")) {
/*     */       try {
/* 206 */         i = getIntegerOption("codeGenBitsetTestThreshold");
/*     */       }
/*     */       catch (NumberFormatException localNumberFormatException3) {
/* 209 */         this.antlrTool.error("option 'codeGenBitsetTestThreshold' must be an integer", getFilename(), paramToken.getLine(), paramToken.getColumn());
/*     */       }
/* 211 */       return true;
/*     */     }
/* 213 */     if (paramString.equals("defaultErrorHandler")) {
/* 214 */       if (str.equals("true")) {
/* 215 */         this.defaultErrorHandler = true;
/*     */       }
/* 217 */       else if (str.equals("false")) {
/* 218 */         this.defaultErrorHandler = false;
/*     */       }
/*     */       else {
/* 221 */         this.antlrTool.error("Value for defaultErrorHandler must be true or false", getFilename(), paramToken.getLine(), paramToken.getColumn());
/*     */       }
/* 223 */       return true;
/*     */     }
/* 225 */     if (paramString.equals("analyzerDebug")) {
/* 226 */       if (str.equals("true")) {
/* 227 */         this.analyzerDebug = true;
/*     */       }
/* 229 */       else if (str.equals("false")) {
/* 230 */         this.analyzerDebug = false;
/*     */       }
/*     */       else {
/* 233 */         this.antlrTool.error("option 'analyzerDebug' must be true or false", getFilename(), paramToken.getLine(), paramToken.getColumn());
/*     */       }
/* 235 */       return true;
/*     */     }
/* 237 */     if (paramString.equals("codeGenDebug")) {
/* 238 */       if (str.equals("true")) {
/* 239 */         this.analyzerDebug = true;
/*     */       }
/* 241 */       else if (str.equals("false")) {
/* 242 */         this.analyzerDebug = false;
/*     */       }
/*     */       else {
/* 245 */         this.antlrTool.error("option 'codeGenDebug' must be true or false", getFilename(), paramToken.getLine(), paramToken.getColumn());
/*     */       }
/* 247 */       return true;
/*     */     }
/* 249 */     if (paramString.equals("classHeaderSuffix")) {
/* 250 */       return true;
/*     */     }
/* 252 */     if (paramString.equals("classHeaderPrefix")) {
/* 253 */       return true;
/*     */     }
/* 255 */     if (paramString.equals("namespaceAntlr")) {
/* 256 */       return true;
/*     */     }
/* 258 */     if (paramString.equals("namespaceStd")) {
/* 259 */       return true;
/*     */     }
/* 261 */     if (paramString.equals("genHashLines")) {
/* 262 */       return true;
/*     */     }
/* 264 */     if (paramString.equals("noConstructors")) {
/* 265 */       return true;
/*     */     }
/* 267 */     return false;
/*     */   }
/*     */ 
/*     */   public void setTokenManager(TokenManager paramTokenManager) {
/* 271 */     this.tokenManager = paramTokenManager;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 276 */     StringBuffer localStringBuffer = new StringBuffer(20000);
/* 277 */     Enumeration localEnumeration = this.rules.elements();
/* 278 */     while (localEnumeration.hasMoreElements()) {
/* 279 */       RuleSymbol localRuleSymbol = (RuleSymbol)localEnumeration.nextElement();
/* 280 */       if (!localRuleSymbol.id.equals("mnextToken")) {
/* 281 */         localStringBuffer.append(localRuleSymbol.getBlock().toString());
/* 282 */         localStringBuffer.append("\n\n");
/*     */       }
/*     */     }
/* 285 */     return localStringBuffer.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.Grammar
 * JD-Core Version:    0.6.2
 */