/*     */ package antlr.preprocessor;
/*     */ 
/*     */ import antlr.collections.impl.IndexedVector;
/*     */ import java.util.Enumeration;
/*     */ 
/*     */ class Rule
/*     */ {
/*     */   protected String name;
/*     */   protected String block;
/*     */   protected String args;
/*     */   protected String returnValue;
/*     */   protected String throwsSpec;
/*     */   protected String initAction;
/*     */   protected IndexedVector options;
/*     */   protected String visibility;
/*     */   protected Grammar enclosingGrammar;
/*  25 */   protected boolean bang = false;
/*     */ 
/*     */   public Rule(String paramString1, String paramString2, IndexedVector paramIndexedVector, Grammar paramGrammar) {
/*  28 */     this.name = paramString1;
/*  29 */     this.block = paramString2;
/*  30 */     this.options = paramIndexedVector;
/*  31 */     setEnclosingGrammar(paramGrammar);
/*     */   }
/*     */ 
/*     */   public String getArgs() {
/*  35 */     return this.args;
/*     */   }
/*     */ 
/*     */   public boolean getBang() {
/*  39 */     return this.bang;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  43 */     return this.name;
/*     */   }
/*     */ 
/*     */   public String getReturnValue() {
/*  47 */     return this.returnValue;
/*     */   }
/*     */ 
/*     */   public String getVisibility() {
/*  51 */     return this.visibility;
/*     */   }
/*     */ 
/*     */   public boolean narrowerVisibility(Rule paramRule)
/*     */   {
/*  60 */     if (this.visibility.equals("public")) {
/*  61 */       if (!paramRule.equals("public")) {
/*  62 */         return true;
/*     */       }
/*  64 */       return false;
/*     */     }
/*  66 */     if (this.visibility.equals("protected")) {
/*  67 */       if (paramRule.equals("private")) {
/*  68 */         return true;
/*     */       }
/*  70 */       return false;
/*     */     }
/*  72 */     if (this.visibility.equals("private")) {
/*  73 */       return false;
/*     */     }
/*  75 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean sameSignature(Rule paramRule)
/*     */   {
/*  87 */     boolean bool1 = true;
/*  88 */     boolean bool2 = true;
/*  89 */     boolean bool3 = true;
/*     */ 
/*  91 */     bool1 = this.name.equals(paramRule.getName());
/*  92 */     if (this.args != null) {
/*  93 */       bool2 = this.args.equals(paramRule.getArgs());
/*     */     }
/*  95 */     if (this.returnValue != null) {
/*  96 */       bool3 = this.returnValue.equals(paramRule.getReturnValue());
/*     */     }
/*  98 */     return (bool1) && (bool2) && (bool3);
/*     */   }
/*     */ 
/*     */   public void setArgs(String paramString) {
/* 102 */     this.args = paramString;
/*     */   }
/*     */ 
/*     */   public void setBang() {
/* 106 */     this.bang = true;
/*     */   }
/*     */ 
/*     */   public void setEnclosingGrammar(Grammar paramGrammar) {
/* 110 */     this.enclosingGrammar = paramGrammar;
/*     */   }
/*     */ 
/*     */   public void setInitAction(String paramString) {
/* 114 */     this.initAction = paramString;
/*     */   }
/*     */ 
/*     */   public void setOptions(IndexedVector paramIndexedVector) {
/* 118 */     this.options = paramIndexedVector;
/*     */   }
/*     */ 
/*     */   public void setReturnValue(String paramString) {
/* 122 */     this.returnValue = paramString;
/*     */   }
/*     */ 
/*     */   public void setThrowsSpec(String paramString) {
/* 126 */     this.throwsSpec = paramString;
/*     */   }
/*     */ 
/*     */   public void setVisibility(String paramString) {
/* 130 */     this.visibility = paramString;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 134 */     String str1 = "";
/* 135 */     String str2 = "returns " + this.returnValue;
/* 136 */     String str3 = this.args == null ? "" : this.args;
/* 137 */     String str4 = getBang() ? "!" : "";
/*     */ 
/* 139 */     str1 = str1 + (this.visibility == null ? "" : new StringBuffer().append(this.visibility).append(" ").toString());
/* 140 */     str1 = str1 + this.name + str4 + str3 + " " + str2 + this.throwsSpec;
/* 141 */     if (this.options != null) {
/* 142 */       str1 = str1 + System.getProperty("line.separator") + "options {" + System.getProperty("line.separator");
/*     */ 
/* 145 */       for (Enumeration localEnumeration = this.options.elements(); localEnumeration.hasMoreElements(); ) {
/* 146 */         str1 = str1 + (Option)localEnumeration.nextElement() + System.getProperty("line.separator");
/*     */       }
/* 148 */       str1 = str1 + "}" + System.getProperty("line.separator");
/*     */     }
/* 150 */     if (this.initAction != null) {
/* 151 */       str1 = str1 + this.initAction + System.getProperty("line.separator");
/*     */     }
/* 153 */     str1 = str1 + this.block;
/* 154 */     return str1;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.preprocessor.Rule
 * JD-Core Version:    0.6.2
 */