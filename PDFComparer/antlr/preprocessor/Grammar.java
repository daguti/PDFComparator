/*     */ package antlr.preprocessor;
/*     */ 
/*     */ import antlr.CodeGenerator;
/*     */ import antlr.Tool;
/*     */ import antlr.collections.impl.IndexedVector;
/*     */ import java.io.IOException;
/*     */ import java.util.Enumeration;
/*     */ 
/*     */ class Grammar
/*     */ {
/*     */   protected String name;
/*     */   protected String fileName;
/*     */   protected String superGrammar;
/*     */   protected String type;
/*     */   protected IndexedVector rules;
/*     */   protected IndexedVector options;
/*     */   protected String tokenSection;
/*     */   protected String preambleAction;
/*     */   protected String memberAction;
/*     */   protected Hierarchy hier;
/*  27 */   protected boolean predefined = false;
/*  28 */   protected boolean alreadyExpanded = false;
/*  29 */   protected boolean specifiedVocabulary = false;
/*     */ 
/*  34 */   protected String superClass = null;
/*     */ 
/*  36 */   protected String importVocab = null;
/*  37 */   protected String exportVocab = null;
/*     */   protected Tool antlrTool;
/*     */ 
/*     */   public Grammar(Tool paramTool, String paramString1, String paramString2, IndexedVector paramIndexedVector)
/*     */   {
/*  41 */     this.name = paramString1;
/*  42 */     this.superGrammar = paramString2;
/*  43 */     this.rules = paramIndexedVector;
/*  44 */     this.antlrTool = paramTool;
/*     */   }
/*     */ 
/*     */   public void addOption(Option paramOption) {
/*  48 */     if (this.options == null) {
/*  49 */       this.options = new IndexedVector();
/*     */     }
/*  51 */     this.options.appendElement(paramOption.getName(), paramOption);
/*     */   }
/*     */ 
/*     */   public void addRule(Rule paramRule) {
/*  55 */     this.rules.appendElement(paramRule.getName(), paramRule);
/*     */   }
/*     */ 
/*     */   public void expandInPlace()
/*     */   {
/*  65 */     if (this.alreadyExpanded) {
/*  66 */       return;
/*     */     }
/*     */ 
/*  70 */     Grammar localGrammar = getSuperGrammar();
/*  71 */     if (localGrammar == null)
/*  72 */       return;
/*  73 */     if (this.exportVocab == null)
/*     */     {
/*  75 */       this.exportVocab = getName();
/*     */     }
/*  77 */     if (localGrammar.isPredefined())
/*  78 */       return;
/*  79 */     localGrammar.expandInPlace();
/*     */ 
/*  82 */     this.alreadyExpanded = true;
/*     */ 
/*  84 */     GrammarFile localGrammarFile = this.hier.getFile(getFileName());
/*  85 */     localGrammarFile.setExpanded(true);
/*     */ 
/*  88 */     IndexedVector localIndexedVector = localGrammar.getRules();
/*  89 */     for (Object localObject1 = localIndexedVector.elements(); ((Enumeration)localObject1).hasMoreElements(); ) {
/*  90 */       localObject2 = (Rule)((Enumeration)localObject1).nextElement();
/*  91 */       inherit((Rule)localObject2, localGrammar);
/*     */     }
/*     */     Object localObject2;
/*  96 */     localObject1 = localGrammar.getOptions();
/*  97 */     if (localObject1 != null)
/*  98 */       for (localObject2 = ((IndexedVector)localObject1).elements(); ((Enumeration)localObject2).hasMoreElements(); ) {
/*  99 */         localObject3 = (Option)((Enumeration)localObject2).nextElement();
/* 100 */         inherit((Option)localObject3, localGrammar);
/*     */       }
/*     */     Object localObject3;
/* 105 */     if (((this.options != null) && (this.options.getElement("importVocab") == null)) || (this.options == null))
/*     */     {
/* 107 */       localObject2 = new Option("importVocab", localGrammar.exportVocab + ";", this);
/* 108 */       addOption((Option)localObject2);
/*     */ 
/* 110 */       localObject3 = localGrammar.getFileName();
/* 111 */       String str1 = this.antlrTool.pathToFile((String)localObject3);
/* 112 */       String str2 = str1 + localGrammar.exportVocab + CodeGenerator.TokenTypesFileSuffix + CodeGenerator.TokenTypesFileExt;
/*     */ 
/* 115 */       String str3 = this.antlrTool.fileMinusPath(str2);
/* 116 */       if (!str1.equals("." + System.getProperty("file.separator")))
/*     */       {
/*     */         try
/*     */         {
/* 122 */           this.antlrTool.copyFile(str2, str3);
/*     */         }
/*     */         catch (IOException localIOException) {
/* 125 */           this.antlrTool.toolError("cannot find/copy importVocab file " + str2);
/* 126 */           return;
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 132 */     inherit(localGrammar.memberAction, localGrammar);
/*     */   }
/*     */ 
/*     */   public String getFileName() {
/* 136 */     return this.fileName;
/*     */   }
/*     */ 
/*     */   public String getName() {
/* 140 */     return this.name;
/*     */   }
/*     */ 
/*     */   public IndexedVector getOptions() {
/* 144 */     return this.options;
/*     */   }
/*     */ 
/*     */   public IndexedVector getRules() {
/* 148 */     return this.rules;
/*     */   }
/*     */ 
/*     */   public Grammar getSuperGrammar() {
/* 152 */     if (this.superGrammar == null) return null;
/* 153 */     Grammar localGrammar = this.hier.getGrammar(this.superGrammar);
/* 154 */     return localGrammar;
/*     */   }
/*     */ 
/*     */   public String getSuperGrammarName() {
/* 158 */     return this.superGrammar;
/*     */   }
/*     */ 
/*     */   public String getType() {
/* 162 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void inherit(Option paramOption, Grammar paramGrammar)
/*     */   {
/* 167 */     if ((paramOption.getName().equals("importVocab")) || (paramOption.getName().equals("exportVocab")))
/*     */     {
/* 169 */       return;
/*     */     }
/*     */ 
/* 172 */     Option localOption = null;
/* 173 */     if (this.options != null) {
/* 174 */       localOption = (Option)this.options.getElement(paramOption.getName());
/*     */     }
/*     */ 
/* 177 */     if (localOption == null)
/* 178 */       addOption(paramOption);
/*     */   }
/*     */ 
/*     */   public void inherit(Rule paramRule, Grammar paramGrammar)
/*     */   {
/* 184 */     Rule localRule = (Rule)this.rules.getElement(paramRule.getName());
/* 185 */     if (localRule != null)
/*     */     {
/* 187 */       if (!localRule.sameSignature(paramRule))
/*     */       {
/* 189 */         this.antlrTool.warning("rule " + getName() + "." + localRule.getName() + " has different signature than " + paramGrammar.getName() + "." + localRule.getName());
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 195 */       addRule(paramRule);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void inherit(String paramString, Grammar paramGrammar) {
/* 200 */     if (this.memberAction != null) return;
/* 201 */     if (paramString != null)
/* 202 */       this.memberAction = paramString;
/*     */   }
/*     */ 
/*     */   public boolean isPredefined()
/*     */   {
/* 207 */     return this.predefined;
/*     */   }
/*     */ 
/*     */   public void setFileName(String paramString) {
/* 211 */     this.fileName = paramString;
/*     */   }
/*     */ 
/*     */   public void setHierarchy(Hierarchy paramHierarchy) {
/* 215 */     this.hier = paramHierarchy;
/*     */   }
/*     */ 
/*     */   public void setMemberAction(String paramString) {
/* 219 */     this.memberAction = paramString;
/*     */   }
/*     */ 
/*     */   public void setOptions(IndexedVector paramIndexedVector) {
/* 223 */     this.options = paramIndexedVector;
/*     */   }
/*     */ 
/*     */   public void setPreambleAction(String paramString) {
/* 227 */     this.preambleAction = paramString;
/*     */   }
/*     */ 
/*     */   public void setPredefined(boolean paramBoolean) {
/* 231 */     this.predefined = paramBoolean;
/*     */   }
/*     */ 
/*     */   public void setTokenSection(String paramString) {
/* 235 */     this.tokenSection = paramString;
/*     */   }
/*     */ 
/*     */   public void setType(String paramString) {
/* 239 */     this.type = paramString;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 243 */     StringBuffer localStringBuffer = new StringBuffer(10000);
/* 244 */     if (this.preambleAction != null) {
/* 245 */       localStringBuffer.append(this.preambleAction);
/*     */     }
/* 247 */     if (this.superGrammar == null) {
/* 248 */       return "class " + this.name + ";";
/*     */     }
/* 250 */     if (this.superClass != null)
/*     */     {
/* 253 */       localStringBuffer.append("class " + this.name + " extends " + this.superClass + ";");
/*     */     }
/*     */     else {
/* 256 */       localStringBuffer.append("class " + this.name + " extends " + this.type + ";");
/*     */     }
/* 258 */     localStringBuffer.append(System.getProperty("line.separator") + System.getProperty("line.separator"));
/*     */ 
/* 261 */     if (this.options != null) {
/* 262 */       localStringBuffer.append(Hierarchy.optionsToString(this.options));
/*     */     }
/* 264 */     if (this.tokenSection != null) {
/* 265 */       localStringBuffer.append(this.tokenSection + "\n");
/*     */     }
/* 267 */     if (this.memberAction != null) {
/* 268 */       localStringBuffer.append(this.memberAction + System.getProperty("line.separator"));
/*     */     }
/* 270 */     for (int i = 0; i < this.rules.size(); i++) {
/* 271 */       Rule localRule = (Rule)this.rules.elementAt(i);
/* 272 */       if (!getName().equals(localRule.enclosingGrammar.getName())) {
/* 273 */         localStringBuffer.append("// inherited from grammar " + localRule.enclosingGrammar.getName() + System.getProperty("line.separator"));
/*     */       }
/* 275 */       localStringBuffer.append(localRule + System.getProperty("line.separator") + System.getProperty("line.separator"));
/*     */     }
/*     */ 
/* 279 */     return localStringBuffer.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.preprocessor.Grammar
 * JD-Core Version:    0.6.2
 */