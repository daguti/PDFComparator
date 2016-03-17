/*     */ package antlr;
/*     */ 
/*     */ import antlr.collections.impl.BitSet;
/*     */ 
/*     */ class DefaultToolErrorHandler
/*     */   implements ToolErrorHandler
/*     */ {
/*     */   private final Tool antlrTool;
/*  18 */   CharFormatter javaCharFormatter = new JavaCharFormatter();
/*     */ 
/*     */   DefaultToolErrorHandler(Tool paramTool)
/*     */   {
/*  14 */     this.antlrTool = paramTool;
/*     */   }
/*     */ 
/*     */   private void dumpSets(String[] paramArrayOfString, int paramInt1, Grammar paramGrammar, boolean paramBoolean, int paramInt2, Lookahead[] paramArrayOfLookahead)
/*     */   {
/*  36 */     StringBuffer localStringBuffer = new StringBuffer(100);
/*  37 */     for (int i = 1; i <= paramInt2; i++) {
/*  38 */       localStringBuffer.append("k==").append(i).append(':');
/*  39 */       if (paramBoolean) {
/*  40 */         String str = paramArrayOfLookahead[i].fset.toStringWithRanges(",", this.javaCharFormatter);
/*  41 */         if (paramArrayOfLookahead[i].containsEpsilon()) {
/*  42 */           localStringBuffer.append("<end-of-token>");
/*  43 */           if (str.length() > 0) {
/*  44 */             localStringBuffer.append(',');
/*     */           }
/*     */         }
/*  47 */         localStringBuffer.append(str);
/*     */       } else {
/*  49 */         localStringBuffer.append(paramArrayOfLookahead[i].fset.toString(",", paramGrammar.tokenManager.getVocabulary()));
/*     */       }
/*  51 */       paramArrayOfString[(paramInt1++)] = localStringBuffer.toString();
/*  52 */       localStringBuffer.setLength(0);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void warnAltAmbiguity(Grammar paramGrammar, AlternativeBlock paramAlternativeBlock, boolean paramBoolean, int paramInt1, Lookahead[] paramArrayOfLookahead, int paramInt2, int paramInt3)
/*     */   {
/*  72 */     StringBuffer localStringBuffer = new StringBuffer(100);
/*  73 */     if (((paramAlternativeBlock instanceof RuleBlock)) && (((RuleBlock)paramAlternativeBlock).isLexerAutoGenRule())) {
/*  74 */       localObject = paramAlternativeBlock.getAlternativeAt(paramInt2);
/*  75 */       Alternative localAlternative = paramAlternativeBlock.getAlternativeAt(paramInt3);
/*  76 */       RuleRefElement localRuleRefElement1 = (RuleRefElement)((Alternative)localObject).head;
/*  77 */       RuleRefElement localRuleRefElement2 = (RuleRefElement)localAlternative.head;
/*  78 */       String str1 = CodeGenerator.reverseLexerRuleName(localRuleRefElement1.targetRule);
/*  79 */       String str2 = CodeGenerator.reverseLexerRuleName(localRuleRefElement2.targetRule);
/*  80 */       localStringBuffer.append("lexical nondeterminism between rules ");
/*  81 */       localStringBuffer.append(str1).append(" and ").append(str2).append(" upon");
/*     */     }
/*     */     else {
/*  84 */       if (paramBoolean) {
/*  85 */         localStringBuffer.append("lexical ");
/*     */       }
/*  87 */       localStringBuffer.append("nondeterminism between alts ");
/*  88 */       localStringBuffer.append(paramInt2 + 1).append(" and ");
/*  89 */       localStringBuffer.append(paramInt3 + 1).append(" of block upon");
/*     */     }
/*  91 */     Object localObject = new String[paramInt1 + 1];
/*  92 */     localObject[0] = localStringBuffer.toString();
/*  93 */     dumpSets((String[])localObject, 1, paramGrammar, paramBoolean, paramInt1, paramArrayOfLookahead);
/*  94 */     this.antlrTool.warning((String[])localObject, paramGrammar.getFilename(), paramAlternativeBlock.getLine(), paramAlternativeBlock.getColumn());
/*     */   }
/*     */ 
/*     */   public void warnAltExitAmbiguity(Grammar paramGrammar, BlockWithImpliedExitPath paramBlockWithImpliedExitPath, boolean paramBoolean, int paramInt1, Lookahead[] paramArrayOfLookahead, int paramInt2)
/*     */   {
/* 113 */     String[] arrayOfString = new String[paramInt1 + 2];
/* 114 */     arrayOfString[0] = ((paramBoolean ? "lexical " : "") + "nondeterminism upon");
/* 115 */     dumpSets(arrayOfString, 1, paramGrammar, paramBoolean, paramInt1, paramArrayOfLookahead);
/* 116 */     arrayOfString[(paramInt1 + 1)] = ("between alt " + (paramInt2 + 1) + " and exit branch of block");
/* 117 */     this.antlrTool.warning(arrayOfString, paramGrammar.getFilename(), paramBlockWithImpliedExitPath.getLine(), paramBlockWithImpliedExitPath.getColumn());
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.DefaultToolErrorHandler
 * JD-Core Version:    0.6.2
 */