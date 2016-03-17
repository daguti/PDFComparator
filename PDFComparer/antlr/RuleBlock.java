/*     */ package antlr;
/*     */ 
/*     */ import antlr.collections.impl.Vector;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ public class RuleBlock extends AlternativeBlock
/*     */ {
/*     */   protected String ruleName;
/*  19 */   protected String argAction = null;
/*  20 */   protected String throwsSpec = null;
/*  21 */   protected String returnAction = null;
/*     */   protected RuleEndElement endNode;
/*  25 */   protected boolean testLiterals = false;
/*     */   Vector labeledElements;
/*     */   protected boolean[] lock;
/*     */   protected Lookahead[] cache;
/*     */   Hashtable exceptionSpecs;
/*  43 */   protected boolean defaultErrorHandler = true;
/*  44 */   protected String ignoreRule = null;
/*     */ 
/*     */   public RuleBlock(Grammar paramGrammar, String paramString)
/*     */   {
/*  48 */     super(paramGrammar);
/*  49 */     this.ruleName = paramString;
/*  50 */     this.labeledElements = new Vector();
/*  51 */     this.cache = new Lookahead[paramGrammar.maxk + 1];
/*  52 */     this.exceptionSpecs = new Hashtable();
/*  53 */     setAutoGen(paramGrammar instanceof ParserGrammar);
/*     */   }
/*     */ 
/*     */   public RuleBlock(Grammar paramGrammar, String paramString, int paramInt, boolean paramBoolean)
/*     */   {
/*  58 */     this(paramGrammar, paramString);
/*  59 */     this.line = paramInt;
/*  60 */     setAutoGen(paramBoolean);
/*     */   }
/*     */ 
/*     */   public void addExceptionSpec(ExceptionSpec paramExceptionSpec) {
/*  64 */     if (findExceptionSpec(paramExceptionSpec.label) != null) {
/*  65 */       if (paramExceptionSpec.label != null) {
/*  66 */         this.grammar.antlrTool.error("Rule '" + this.ruleName + "' already has an exception handler for label: " + paramExceptionSpec.label);
/*     */       }
/*     */       else {
/*  69 */         this.grammar.antlrTool.error("Rule '" + this.ruleName + "' already has an exception handler");
/*     */       }
/*     */     }
/*     */     else
/*  73 */       this.exceptionSpecs.put(paramExceptionSpec.label == null ? "" : paramExceptionSpec.label.getText(), paramExceptionSpec);
/*     */   }
/*     */ 
/*     */   public ExceptionSpec findExceptionSpec(Token paramToken)
/*     */   {
/*  78 */     return (ExceptionSpec)this.exceptionSpecs.get(paramToken == null ? "" : paramToken.getText());
/*     */   }
/*     */ 
/*     */   public ExceptionSpec findExceptionSpec(String paramString) {
/*  82 */     return (ExceptionSpec)this.exceptionSpecs.get(paramString == null ? "" : paramString);
/*     */   }
/*     */ 
/*     */   public void generate() {
/*  86 */     this.grammar.generator.gen(this);
/*     */   }
/*     */ 
/*     */   public boolean getDefaultErrorHandler() {
/*  90 */     return this.defaultErrorHandler;
/*     */   }
/*     */ 
/*     */   public RuleEndElement getEndElement() {
/*  94 */     return this.endNode;
/*     */   }
/*     */ 
/*     */   public String getIgnoreRule() {
/*  98 */     return this.ignoreRule;
/*     */   }
/*     */ 
/*     */   public String getRuleName() {
/* 102 */     return this.ruleName;
/*     */   }
/*     */ 
/*     */   public boolean getTestLiterals() {
/* 106 */     return this.testLiterals;
/*     */   }
/*     */ 
/*     */   public boolean isLexerAutoGenRule() {
/* 110 */     return this.ruleName.equals("nextToken");
/*     */   }
/*     */ 
/*     */   public Lookahead look(int paramInt) {
/* 114 */     return this.grammar.theLLkAnalyzer.look(paramInt, this);
/*     */   }
/*     */ 
/*     */   public void prepareForAnalysis() {
/* 118 */     super.prepareForAnalysis();
/* 119 */     this.lock = new boolean[this.grammar.maxk + 1];
/*     */   }
/*     */ 
/*     */   public void setDefaultErrorHandler(boolean paramBoolean)
/*     */   {
/* 124 */     this.defaultErrorHandler = paramBoolean;
/*     */   }
/*     */ 
/*     */   public void setEndElement(RuleEndElement paramRuleEndElement) {
/* 128 */     this.endNode = paramRuleEndElement;
/*     */   }
/*     */ 
/*     */   public void setOption(Token paramToken1, Token paramToken2) {
/* 132 */     if (paramToken1.getText().equals("defaultErrorHandler")) {
/* 133 */       if (paramToken2.getText().equals("true")) {
/* 134 */         this.defaultErrorHandler = true;
/*     */       }
/* 136 */       else if (paramToken2.getText().equals("false")) {
/* 137 */         this.defaultErrorHandler = false;
/*     */       }
/*     */       else {
/* 140 */         this.grammar.antlrTool.error("Value for defaultErrorHandler must be true or false", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/*     */       }
/*     */     }
/* 143 */     else if (paramToken1.getText().equals("testLiterals")) {
/* 144 */       if (!(this.grammar instanceof LexerGrammar)) {
/* 145 */         this.grammar.antlrTool.error("testLiterals option only valid for lexer rules", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/*     */       }
/* 148 */       else if (paramToken2.getText().equals("true")) {
/* 149 */         this.testLiterals = true;
/*     */       }
/* 151 */       else if (paramToken2.getText().equals("false")) {
/* 152 */         this.testLiterals = false;
/*     */       }
/*     */       else {
/* 155 */         this.grammar.antlrTool.error("Value for testLiterals must be true or false", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/*     */       }
/*     */ 
/*     */     }
/* 159 */     else if (paramToken1.getText().equals("ignore")) {
/* 160 */       if (!(this.grammar instanceof LexerGrammar)) {
/* 161 */         this.grammar.antlrTool.error("ignore option only valid for lexer rules", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/*     */       }
/*     */       else {
/* 164 */         this.ignoreRule = paramToken2.getText();
/*     */       }
/*     */     }
/* 167 */     else if (paramToken1.getText().equals("paraphrase")) {
/* 168 */       if (!(this.grammar instanceof LexerGrammar)) {
/* 169 */         this.grammar.antlrTool.error("paraphrase option only valid for lexer rules", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/*     */       }
/*     */       else
/*     */       {
/* 173 */         TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbol(this.ruleName);
/* 174 */         if (localTokenSymbol == null) {
/* 175 */           this.grammar.antlrTool.panic("cannot find token associated with rule " + this.ruleName);
/*     */         }
/* 177 */         localTokenSymbol.setParaphrase(paramToken2.getText());
/*     */       }
/*     */     }
/* 180 */     else if (paramToken1.getText().equals("generateAmbigWarnings")) {
/* 181 */       if (paramToken2.getText().equals("true")) {
/* 182 */         this.generateAmbigWarnings = true;
/*     */       }
/* 184 */       else if (paramToken2.getText().equals("false")) {
/* 185 */         this.generateAmbigWarnings = false;
/*     */       }
/*     */       else {
/* 188 */         this.grammar.antlrTool.error("Value for generateAmbigWarnings must be true or false", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/*     */       }
/*     */     }
/*     */     else
/* 192 */       this.grammar.antlrTool.error("Invalid rule option: " + paramToken1.getText(), this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 197 */     String str = " FOLLOW={";
/* 198 */     Lookahead[] arrayOfLookahead = this.endNode.cache;
/* 199 */     int i = this.grammar.maxk;
/* 200 */     int j = 1;
/* 201 */     for (int k = 1; k <= i; k++)
/* 202 */       if (arrayOfLookahead[k] != null) {
/* 203 */         str = str + arrayOfLookahead[k].toString(",", this.grammar.tokenManager.getVocabulary());
/* 204 */         j = 0;
/* 205 */         if ((k < i) && (arrayOfLookahead[(k + 1)] != null)) str = str + ";";
/*     */       }
/* 207 */     str = str + "}";
/* 208 */     if (j != 0) str = "";
/* 209 */     return this.ruleName + ": " + super.toString() + " ;" + str;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.RuleBlock
 * JD-Core Version:    0.6.2
 */