/*     */ package antlr;
/*     */ 
/*     */ import antlr.collections.impl.Vector;
/*     */ 
/*     */ class AlternativeBlock extends AlternativeElement
/*     */ {
/*  14 */   protected String initAction = null;
/*     */   protected Vector alternatives;
/*     */   protected String label;
/*     */   protected int alti;
/*     */   protected int altj;
/*     */   protected int analysisAlt;
/*  23 */   protected boolean hasAnAction = false;
/*  24 */   protected boolean hasASynPred = false;
/*     */ 
/*  26 */   protected int ID = 0;
/*     */   protected static int nblks;
/*  28 */   boolean not = false;
/*     */ 
/*  30 */   boolean greedy = true;
/*  31 */   boolean greedySet = false;
/*     */ 
/*  33 */   protected boolean doAutoGen = true;
/*     */ 
/*  35 */   protected boolean warnWhenFollowAmbig = true;
/*     */ 
/*  37 */   protected boolean generateAmbigWarnings = true;
/*     */ 
/*     */   public AlternativeBlock(Grammar paramGrammar)
/*     */   {
/*  43 */     super(paramGrammar);
/*  44 */     this.alternatives = new Vector(5);
/*  45 */     this.not = false;
/*  46 */     nblks += 1;
/*  47 */     this.ID = nblks;
/*     */   }
/*     */ 
/*     */   public AlternativeBlock(Grammar paramGrammar, Token paramToken, boolean paramBoolean) {
/*  51 */     super(paramGrammar, paramToken);
/*  52 */     this.alternatives = new Vector(5);
/*     */ 
/*  55 */     this.not = paramBoolean;
/*  56 */     nblks += 1;
/*  57 */     this.ID = nblks;
/*     */   }
/*     */ 
/*     */   public void addAlternative(Alternative paramAlternative) {
/*  61 */     this.alternatives.appendElement(paramAlternative);
/*     */   }
/*     */ 
/*     */   public void generate() {
/*  65 */     this.grammar.generator.gen(this);
/*     */   }
/*     */ 
/*     */   public Alternative getAlternativeAt(int paramInt) {
/*  69 */     return (Alternative)this.alternatives.elementAt(paramInt);
/*     */   }
/*     */ 
/*     */   public Vector getAlternatives() {
/*  73 */     return this.alternatives;
/*     */   }
/*     */ 
/*     */   public boolean getAutoGen() {
/*  77 */     return this.doAutoGen;
/*     */   }
/*     */ 
/*     */   public String getInitAction() {
/*  81 */     return this.initAction;
/*     */   }
/*     */ 
/*     */   public String getLabel() {
/*  85 */     return this.label;
/*     */   }
/*     */ 
/*     */   public Lookahead look(int paramInt) {
/*  89 */     return this.grammar.theLLkAnalyzer.look(paramInt, this);
/*     */   }
/*     */ 
/*     */   public void prepareForAnalysis() {
/*  93 */     for (int i = 0; i < this.alternatives.size(); i++)
/*     */     {
/*  95 */       Alternative localAlternative = (Alternative)this.alternatives.elementAt(i);
/*  96 */       localAlternative.cache = new Lookahead[this.grammar.maxk + 1];
/*  97 */       localAlternative.lookaheadDepth = -1;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void removeTrackingOfRuleRefs(Grammar paramGrammar)
/*     */   {
/* 106 */     for (int i = 0; i < this.alternatives.size(); i++) {
/* 107 */       Alternative localAlternative = getAlternativeAt(i);
/* 108 */       AlternativeElement localAlternativeElement = localAlternative.head;
/* 109 */       while (localAlternativeElement != null) {
/* 110 */         if ((localAlternativeElement instanceof RuleRefElement)) {
/* 111 */           RuleRefElement localRuleRefElement = (RuleRefElement)localAlternativeElement;
/* 112 */           RuleSymbol localRuleSymbol = (RuleSymbol)paramGrammar.getSymbol(localRuleRefElement.targetRule);
/* 113 */           if (localRuleSymbol == null) {
/* 114 */             this.grammar.antlrTool.error("rule " + localRuleRefElement.targetRule + " referenced in (...)=>, but not defined");
/*     */           }
/*     */           else {
/* 117 */             localRuleSymbol.references.removeElement(localRuleRefElement);
/*     */           }
/*     */         }
/* 120 */         else if ((localAlternativeElement instanceof AlternativeBlock)) {
/* 121 */           ((AlternativeBlock)localAlternativeElement).removeTrackingOfRuleRefs(paramGrammar);
/*     */         }
/* 123 */         localAlternativeElement = localAlternativeElement.next;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setAlternatives(Vector paramVector) {
/* 129 */     this.alternatives = paramVector;
/*     */   }
/*     */ 
/*     */   public void setAutoGen(boolean paramBoolean) {
/* 133 */     this.doAutoGen = paramBoolean;
/*     */   }
/*     */ 
/*     */   public void setInitAction(String paramString) {
/* 137 */     this.initAction = paramString;
/*     */   }
/*     */ 
/*     */   public void setLabel(String paramString) {
/* 141 */     this.label = paramString;
/*     */   }
/*     */ 
/*     */   public void setOption(Token paramToken1, Token paramToken2) {
/* 145 */     if (paramToken1.getText().equals("warnWhenFollowAmbig")) {
/* 146 */       if (paramToken2.getText().equals("true")) {
/* 147 */         this.warnWhenFollowAmbig = true;
/*     */       }
/* 149 */       else if (paramToken2.getText().equals("false")) {
/* 150 */         this.warnWhenFollowAmbig = false;
/*     */       }
/*     */       else {
/* 153 */         this.grammar.antlrTool.error("Value for warnWhenFollowAmbig must be true or false", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/*     */       }
/*     */     }
/* 156 */     else if (paramToken1.getText().equals("generateAmbigWarnings")) {
/* 157 */       if (paramToken2.getText().equals("true")) {
/* 158 */         this.generateAmbigWarnings = true;
/*     */       }
/* 160 */       else if (paramToken2.getText().equals("false")) {
/* 161 */         this.generateAmbigWarnings = false;
/*     */       }
/*     */       else {
/* 164 */         this.grammar.antlrTool.error("Value for generateAmbigWarnings must be true or false", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/*     */       }
/*     */     }
/* 167 */     else if (paramToken1.getText().equals("greedy")) {
/* 168 */       if (paramToken2.getText().equals("true")) {
/* 169 */         this.greedy = true;
/* 170 */         this.greedySet = true;
/*     */       }
/* 172 */       else if (paramToken2.getText().equals("false")) {
/* 173 */         this.greedy = false;
/* 174 */         this.greedySet = true;
/*     */       }
/*     */       else {
/* 177 */         this.grammar.antlrTool.error("Value for greedy must be true or false", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/*     */       }
/*     */     }
/*     */     else
/* 181 */       this.grammar.antlrTool.error("Invalid subrule option: " + paramToken1.getText(), this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 186 */     String str1 = " (";
/* 187 */     if (this.initAction != null) {
/* 188 */       str1 = str1 + this.initAction;
/*     */     }
/* 190 */     for (int i = 0; i < this.alternatives.size(); i++) {
/* 191 */       Alternative localAlternative = getAlternativeAt(i);
/* 192 */       Lookahead[] arrayOfLookahead = localAlternative.cache;
/* 193 */       int j = localAlternative.lookaheadDepth;
/*     */ 
/* 195 */       if (j != -1)
/*     */       {
/* 197 */         if (j == 2147483647) {
/* 198 */           str1 = str1 + "{?}:";
/*     */         }
/*     */         else {
/* 201 */           str1 = str1 + " {";
/* 202 */           for (int k = 1; k <= j; k++) {
/* 203 */             str1 = str1 + arrayOfLookahead[k].toString(",", this.grammar.tokenManager.getVocabulary());
/* 204 */             if ((k < j) && (arrayOfLookahead[(k + 1)] != null)) str1 = str1 + ";";
/*     */           }
/* 206 */           str1 = str1 + "}:";
/*     */         }
/*     */       }
/* 209 */       AlternativeElement localAlternativeElement = localAlternative.head;
/* 210 */       String str2 = localAlternative.semPred;
/* 211 */       if (str2 != null) {
/* 212 */         str1 = str1 + str2;
/*     */       }
/* 214 */       while (localAlternativeElement != null) {
/* 215 */         str1 = str1 + localAlternativeElement;
/* 216 */         localAlternativeElement = localAlternativeElement.next;
/*     */       }
/* 218 */       if (i < this.alternatives.size() - 1) {
/* 219 */         str1 = str1 + " |";
/*     */       }
/*     */     }
/* 222 */     str1 = str1 + " )";
/* 223 */     return str1;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.AlternativeBlock
 * JD-Core Version:    0.6.2
 */