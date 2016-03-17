/*     */ package antlr;
/*     */ 
/*     */ import antlr.collections.impl.BitSet;
/*     */ import antlr.collections.impl.Vector;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ public class DocBookCodeGenerator extends CodeGenerator
/*     */ {
/*  25 */   protected int syntacticPredLevel = 0;
/*     */ 
/*  28 */   protected boolean doingLexRules = false;
/*     */   protected boolean firstElementInAlt;
/*  32 */   protected AlternativeElement prevAltElem = null;
/*     */ 
/*     */   public DocBookCodeGenerator()
/*     */   {
/*  40 */     this.charFormatter = new JavaCharFormatter();
/*     */   }
/*     */ 
/*     */   static String HTMLEncode(String paramString)
/*     */   {
/*  48 */     StringBuffer localStringBuffer = new StringBuffer();
/*     */ 
/*  50 */     int i = 0; for (int j = paramString.length(); i < j; i++) {
/*  51 */       char c = paramString.charAt(i);
/*  52 */       if (c == '&')
/*  53 */         localStringBuffer.append("&amp;");
/*  54 */       else if (c == '"')
/*  55 */         localStringBuffer.append("&quot;");
/*  56 */       else if (c == '\'')
/*  57 */         localStringBuffer.append("&#039;");
/*  58 */       else if (c == '<')
/*  59 */         localStringBuffer.append("&lt;");
/*  60 */       else if (c == '>')
/*  61 */         localStringBuffer.append("&gt;");
/*     */       else
/*  63 */         localStringBuffer.append(c);
/*     */     }
/*  65 */     return localStringBuffer.toString();
/*     */   }
/*     */ 
/*     */   static String QuoteForId(String paramString)
/*     */   {
/*  73 */     StringBuffer localStringBuffer = new StringBuffer();
/*     */ 
/*  75 */     int i = 0; for (int j = paramString.length(); i < j; i++) {
/*  76 */       char c = paramString.charAt(i);
/*  77 */       if (c == '_')
/*  78 */         localStringBuffer.append(".");
/*     */       else
/*  80 */         localStringBuffer.append(c);
/*     */     }
/*  82 */     return localStringBuffer.toString();
/*     */   }
/*     */ 
/*     */   public void gen()
/*     */   {
/*     */     try
/*     */     {
/*  89 */       Enumeration localEnumeration = this.behavior.grammars.elements();
/*  90 */       while (localEnumeration.hasMoreElements()) {
/*  91 */         Grammar localGrammar = (Grammar)localEnumeration.nextElement();
/*     */ 
/*  98 */         localGrammar.setCodeGenerator(this);
/*     */ 
/* 101 */         localGrammar.generate();
/*     */ 
/* 103 */         if (this.antlrTool.hasError()) {
/* 104 */           this.antlrTool.fatalError("Exiting due to errors.");
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/* 111 */       this.antlrTool.reportException(localIOException, null);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void gen(ActionElement paramActionElement)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void gen(AlternativeBlock paramAlternativeBlock)
/*     */   {
/* 126 */     genGenericBlock(paramAlternativeBlock, "");
/*     */   }
/*     */ 
/*     */   public void gen(BlockEndElement paramBlockEndElement)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void gen(CharLiteralElement paramCharLiteralElement)
/*     */   {
/* 142 */     if (paramCharLiteralElement.not) {
/* 143 */       _print("~");
/*     */     }
/* 145 */     _print(HTMLEncode(paramCharLiteralElement.atomText) + " ");
/*     */   }
/*     */ 
/*     */   public void gen(CharRangeElement paramCharRangeElement)
/*     */   {
/* 152 */     print(paramCharRangeElement.beginText + ".." + paramCharRangeElement.endText + " ");
/*     */   }
/*     */ 
/*     */   public void gen(LexerGrammar paramLexerGrammar) throws IOException
/*     */   {
/* 157 */     setGrammar(paramLexerGrammar);
/* 158 */     this.antlrTool.reportProgress("Generating " + this.grammar.getClassName() + ".sgml");
/* 159 */     this.currentOutput = this.antlrTool.openOutputFile(this.grammar.getClassName() + ".sgml");
/*     */ 
/* 161 */     this.tabs = 0;
/* 162 */     this.doingLexRules = true;
/*     */ 
/* 165 */     genHeader();
/*     */ 
/* 172 */     println("");
/*     */ 
/* 175 */     if (this.grammar.comment != null) {
/* 176 */       _println(HTMLEncode(this.grammar.comment));
/*     */     }
/*     */ 
/* 179 */     println("<para>Definition of lexer " + this.grammar.getClassName() + ", which is a subclass of " + this.grammar.getSuperClass() + ".</para>");
/*     */ 
/* 212 */     genNextToken();
/*     */ 
/* 216 */     Enumeration localEnumeration = this.grammar.rules.elements();
/* 217 */     while (localEnumeration.hasMoreElements()) {
/* 218 */       RuleSymbol localRuleSymbol = (RuleSymbol)localEnumeration.nextElement();
/* 219 */       if (!localRuleSymbol.id.equals("mnextToken")) {
/* 220 */         genRule(localRuleSymbol);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 225 */     this.currentOutput.close();
/* 226 */     this.currentOutput = null;
/* 227 */     this.doingLexRules = false;
/*     */   }
/*     */ 
/*     */   public void gen(OneOrMoreBlock paramOneOrMoreBlock)
/*     */   {
/* 234 */     genGenericBlock(paramOneOrMoreBlock, "+");
/*     */   }
/*     */ 
/*     */   public void gen(ParserGrammar paramParserGrammar) throws IOException
/*     */   {
/* 239 */     setGrammar(paramParserGrammar);
/*     */ 
/* 241 */     this.antlrTool.reportProgress("Generating " + this.grammar.getClassName() + ".sgml");
/* 242 */     this.currentOutput = this.antlrTool.openOutputFile(this.grammar.getClassName() + ".sgml");
/*     */ 
/* 244 */     this.tabs = 0;
/*     */ 
/* 247 */     genHeader();
/*     */ 
/* 250 */     println("");
/*     */ 
/* 253 */     if (this.grammar.comment != null) {
/* 254 */       _println(HTMLEncode(this.grammar.comment));
/*     */     }
/*     */ 
/* 257 */     println("<para>Definition of parser " + this.grammar.getClassName() + ", which is a subclass of " + this.grammar.getSuperClass() + ".</para>");
/*     */ 
/* 260 */     Enumeration localEnumeration = this.grammar.rules.elements();
/* 261 */     while (localEnumeration.hasMoreElements()) {
/* 262 */       println("");
/*     */ 
/* 264 */       GrammarSymbol localGrammarSymbol = (GrammarSymbol)localEnumeration.nextElement();
/*     */ 
/* 266 */       if ((localGrammarSymbol instanceof RuleSymbol)) {
/* 267 */         genRule((RuleSymbol)localGrammarSymbol);
/*     */       }
/*     */     }
/* 270 */     this.tabs -= 1;
/* 271 */     println("");
/*     */ 
/* 273 */     genTail();
/*     */ 
/* 276 */     this.currentOutput.close();
/* 277 */     this.currentOutput = null;
/*     */   }
/*     */ 
/*     */   public void gen(RuleRefElement paramRuleRefElement)
/*     */   {
/* 284 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(paramRuleRefElement.targetRule);
/*     */ 
/* 287 */     _print("<link linkend=\"" + QuoteForId(paramRuleRefElement.targetRule) + "\">");
/* 288 */     _print(paramRuleRefElement.targetRule);
/* 289 */     _print("</link>");
/*     */ 
/* 294 */     _print(" ");
/*     */   }
/*     */ 
/*     */   public void gen(StringLiteralElement paramStringLiteralElement)
/*     */   {
/* 301 */     if (paramStringLiteralElement.not) {
/* 302 */       _print("~");
/*     */     }
/* 304 */     _print(HTMLEncode(paramStringLiteralElement.atomText));
/* 305 */     _print(" ");
/*     */   }
/*     */ 
/*     */   public void gen(TokenRangeElement paramTokenRangeElement)
/*     */   {
/* 312 */     print(paramTokenRangeElement.beginText + ".." + paramTokenRangeElement.endText + " ");
/*     */   }
/*     */ 
/*     */   public void gen(TokenRefElement paramTokenRefElement)
/*     */   {
/* 319 */     if (paramTokenRefElement.not) {
/* 320 */       _print("~");
/*     */     }
/* 322 */     _print(paramTokenRefElement.atomText);
/* 323 */     _print(" ");
/*     */   }
/*     */ 
/*     */   public void gen(TreeElement paramTreeElement) {
/* 327 */     print(paramTreeElement + " ");
/*     */   }
/*     */ 
/*     */   public void gen(TreeWalkerGrammar paramTreeWalkerGrammar) throws IOException
/*     */   {
/* 332 */     setGrammar(paramTreeWalkerGrammar);
/*     */ 
/* 334 */     this.antlrTool.reportProgress("Generating " + this.grammar.getClassName() + ".sgml");
/* 335 */     this.currentOutput = this.antlrTool.openOutputFile(this.grammar.getClassName() + ".sgml");
/*     */ 
/* 338 */     this.tabs = 0;
/*     */ 
/* 341 */     genHeader();
/*     */ 
/* 344 */     println("");
/*     */ 
/* 353 */     println("");
/*     */ 
/* 356 */     if (this.grammar.comment != null) {
/* 357 */       _println(HTMLEncode(this.grammar.comment));
/*     */     }
/*     */ 
/* 360 */     println("<para>Definition of tree parser " + this.grammar.getClassName() + ", which is a subclass of " + this.grammar.getSuperClass() + ".</para>");
/*     */ 
/* 372 */     println("");
/*     */ 
/* 374 */     this.tabs += 1;
/*     */ 
/* 377 */     Enumeration localEnumeration = this.grammar.rules.elements();
/* 378 */     while (localEnumeration.hasMoreElements()) {
/* 379 */       println("");
/*     */ 
/* 381 */       GrammarSymbol localGrammarSymbol = (GrammarSymbol)localEnumeration.nextElement();
/*     */ 
/* 383 */       if ((localGrammarSymbol instanceof RuleSymbol)) {
/* 384 */         genRule((RuleSymbol)localGrammarSymbol);
/*     */       }
/*     */     }
/* 387 */     this.tabs -= 1;
/* 388 */     println("");
/*     */ 
/* 395 */     this.currentOutput.close();
/* 396 */     this.currentOutput = null;
/*     */   }
/*     */ 
/*     */   public void gen(WildcardElement paramWildcardElement)
/*     */   {
/* 406 */     _print(". ");
/*     */   }
/*     */ 
/*     */   public void gen(ZeroOrMoreBlock paramZeroOrMoreBlock)
/*     */   {
/* 413 */     genGenericBlock(paramZeroOrMoreBlock, "*");
/*     */   }
/*     */ 
/*     */   protected void genAlt(Alternative paramAlternative) {
/* 417 */     if (paramAlternative.getTreeSpecifier() != null) {
/* 418 */       _print(paramAlternative.getTreeSpecifier().getText());
/*     */     }
/* 420 */     this.prevAltElem = null;
/* 421 */     for (AlternativeElement localAlternativeElement = paramAlternative.head; 
/* 422 */       !(localAlternativeElement instanceof BlockEndElement); 
/* 423 */       localAlternativeElement = localAlternativeElement.next) {
/* 424 */       localAlternativeElement.generate();
/* 425 */       this.firstElementInAlt = false;
/* 426 */       this.prevAltElem = localAlternativeElement;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void genCommonBlock(AlternativeBlock paramAlternativeBlock)
/*     */   {
/* 447 */     if (paramAlternativeBlock.alternatives.size() > 1)
/* 448 */       println("<itemizedlist mark=\"none\">");
/* 449 */     for (int i = 0; i < paramAlternativeBlock.alternatives.size(); i++) {
/* 450 */       Alternative localAlternative = paramAlternativeBlock.getAlternativeAt(i);
/* 451 */       AlternativeElement localAlternativeElement = localAlternative.head;
/*     */ 
/* 453 */       if (paramAlternativeBlock.alternatives.size() > 1) {
/* 454 */         print("<listitem><para>");
/*     */       }
/*     */ 
/* 457 */       if ((i > 0) && (paramAlternativeBlock.alternatives.size() > 1)) {
/* 458 */         _print("| ");
/*     */       }
/*     */ 
/* 463 */       boolean bool = this.firstElementInAlt;
/* 464 */       this.firstElementInAlt = true;
/* 465 */       this.tabs += 1;
/*     */ 
/* 467 */       genAlt(localAlternative);
/* 468 */       this.tabs -= 1;
/* 469 */       this.firstElementInAlt = bool;
/* 470 */       if (paramAlternativeBlock.alternatives.size() > 1)
/* 471 */         _println("</para></listitem>");
/*     */     }
/* 473 */     if (paramAlternativeBlock.alternatives.size() > 1)
/* 474 */       println("</itemizedlist>");
/*     */   }
/*     */ 
/*     */   public void genFollowSetForRuleBlock(RuleBlock paramRuleBlock)
/*     */   {
/* 482 */     Lookahead localLookahead = this.grammar.theLLkAnalyzer.FOLLOW(1, paramRuleBlock.endNode);
/* 483 */     printSet(this.grammar.maxk, 1, localLookahead);
/*     */   }
/*     */ 
/*     */   protected void genGenericBlock(AlternativeBlock paramAlternativeBlock, String paramString) {
/* 487 */     if (paramAlternativeBlock.alternatives.size() > 1)
/*     */     {
/* 489 */       _println("");
/* 490 */       if (!this.firstElementInAlt)
/*     */       {
/* 496 */         _println("(");
/*     */       }
/*     */       else
/*     */       {
/* 506 */         _print("(");
/*     */       }
/*     */     }
/*     */     else {
/* 510 */       _print("( ");
/*     */     }
/*     */ 
/* 514 */     genCommonBlock(paramAlternativeBlock);
/* 515 */     if (paramAlternativeBlock.alternatives.size() > 1) {
/* 516 */       _println("");
/* 517 */       print(")" + paramString + " ");
/*     */ 
/* 519 */       if (!(paramAlternativeBlock.next instanceof BlockEndElement)) {
/* 520 */         _println("");
/* 521 */         print("");
/*     */       }
/*     */     }
/*     */     else {
/* 525 */       _print(")" + paramString + " ");
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void genHeader()
/*     */   {
/* 531 */     println("<?xml version=\"1.0\" standalone=\"no\"?>");
/* 532 */     println("<!DOCTYPE book PUBLIC \"-//OASIS//DTD DocBook V3.1//EN\">");
/* 533 */     println("<book lang=\"en\">");
/* 534 */     println("<bookinfo>");
/* 535 */     println("<title>Grammar " + this.grammar.getClassName() + "</title>");
/* 536 */     println("  <author>");
/* 537 */     println("    <firstname></firstname>");
/* 538 */     println("    <othername></othername>");
/* 539 */     println("    <surname></surname>");
/* 540 */     println("    <affiliation>");
/* 541 */     println("     <address>");
/* 542 */     println("     <email></email>");
/* 543 */     println("     </address>");
/* 544 */     println("    </affiliation>");
/* 545 */     println("  </author>");
/* 546 */     println("  <othercredit>");
/* 547 */     println("    <contrib>");
/* 548 */     println("    Generated by <ulink url=\"http://www.ANTLR.org/\">ANTLR</ulink>" + Tool.version);
/* 549 */     println("    from " + this.antlrTool.grammarFile);
/* 550 */     println("    </contrib>");
/* 551 */     println("  </othercredit>");
/* 552 */     println("  <pubdate></pubdate>");
/* 553 */     println("  <abstract>");
/* 554 */     println("  <para>");
/* 555 */     println("  </para>");
/* 556 */     println("  </abstract>");
/* 557 */     println("</bookinfo>");
/* 558 */     println("<chapter>");
/* 559 */     println("<title></title>");
/*     */   }
/*     */ 
/*     */   protected void genLookaheadSetForAlt(Alternative paramAlternative)
/*     */   {
/* 564 */     if ((this.doingLexRules) && (paramAlternative.cache[1].containsEpsilon())) {
/* 565 */       println("MATCHES ALL");
/* 566 */       return;
/*     */     }
/* 568 */     int i = paramAlternative.lookaheadDepth;
/* 569 */     if (i == 2147483647)
/*     */     {
/* 572 */       i = this.grammar.maxk;
/*     */     }
/* 574 */     for (int j = 1; j <= i; j++) {
/* 575 */       Lookahead localLookahead = paramAlternative.cache[j];
/* 576 */       printSet(i, j, localLookahead);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void genLookaheadSetForBlock(AlternativeBlock paramAlternativeBlock)
/*     */   {
/* 586 */     int i = 0;
/*     */     Object localObject;
/* 587 */     for (int j = 0; j < paramAlternativeBlock.alternatives.size(); j++) {
/* 588 */       localObject = paramAlternativeBlock.getAlternativeAt(j);
/* 589 */       if (((Alternative)localObject).lookaheadDepth == 2147483647) {
/* 590 */         i = this.grammar.maxk;
/* 591 */         break;
/*     */       }
/* 593 */       if (i < ((Alternative)localObject).lookaheadDepth) {
/* 594 */         i = ((Alternative)localObject).lookaheadDepth;
/*     */       }
/*     */     }
/*     */ 
/* 598 */     for (j = 1; j <= i; j++) {
/* 599 */       localObject = this.grammar.theLLkAnalyzer.look(j, paramAlternativeBlock);
/* 600 */       printSet(i, j, (Lookahead)localObject);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void genNextToken()
/*     */   {
/* 609 */     println("");
/* 610 */     println("/** Lexer nextToken rule:");
/* 611 */     println(" *  The lexer nextToken rule is synthesized from all of the user-defined");
/* 612 */     println(" *  lexer rules.  It logically consists of one big alternative block with");
/* 613 */     println(" *  each user-defined rule being an alternative.");
/* 614 */     println(" */");
/*     */ 
/* 618 */     RuleBlock localRuleBlock = MakeGrammar.createNextTokenRule(this.grammar, this.grammar.rules, "nextToken");
/*     */ 
/* 621 */     RuleSymbol localRuleSymbol = new RuleSymbol("mnextToken");
/* 622 */     localRuleSymbol.setDefined();
/* 623 */     localRuleSymbol.setBlock(localRuleBlock);
/* 624 */     localRuleSymbol.access = "private";
/* 625 */     this.grammar.define(localRuleSymbol);
/*     */ 
/* 638 */     genCommonBlock(localRuleBlock);
/*     */   }
/*     */ 
/*     */   public void genRule(RuleSymbol paramRuleSymbol)
/*     */   {
/* 645 */     if ((paramRuleSymbol == null) || (!paramRuleSymbol.isDefined())) return;
/* 646 */     println("");
/*     */ 
/* 648 */     if ((paramRuleSymbol.access.length() != 0) && 
/* 649 */       (!paramRuleSymbol.access.equals("public"))) {
/* 650 */       _print("<para>" + paramRuleSymbol.access + " </para>");
/*     */     }
/*     */ 
/* 654 */     println("<section id=\"" + QuoteForId(paramRuleSymbol.getId()) + "\">");
/* 655 */     println("<title>" + paramRuleSymbol.getId() + "</title>");
/* 656 */     if (paramRuleSymbol.comment != null) {
/* 657 */       _println("<para>" + HTMLEncode(paramRuleSymbol.comment) + "</para>");
/*     */     }
/* 659 */     println("<para>");
/*     */ 
/* 662 */     RuleBlock localRuleBlock = paramRuleSymbol.getBlock();
/*     */ 
/* 674 */     _println("");
/* 675 */     print(paramRuleSymbol.getId() + ":\t");
/* 676 */     this.tabs += 1;
/*     */ 
/* 682 */     genCommonBlock(localRuleBlock);
/*     */ 
/* 684 */     _println("");
/*     */ 
/* 686 */     this.tabs -= 1;
/* 687 */     _println("</para>");
/* 688 */     _println("</section><!-- section \"" + paramRuleSymbol.getId() + "\" -->");
/*     */   }
/*     */ 
/*     */   protected void genSynPred(SynPredBlock paramSynPredBlock)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void genTail()
/*     */   {
/* 700 */     println("</chapter>");
/* 701 */     println("</book>");
/*     */   }
/*     */ 
/*     */   protected void genTokenTypes(TokenManager paramTokenManager)
/*     */     throws IOException
/*     */   {
/* 707 */     this.antlrTool.reportProgress("Generating " + paramTokenManager.getName() + TokenTypesFileSuffix + TokenTypesFileExt);
/* 708 */     this.currentOutput = this.antlrTool.openOutputFile(paramTokenManager.getName() + TokenTypesFileSuffix + TokenTypesFileExt);
/*     */ 
/* 710 */     this.tabs = 0;
/*     */ 
/* 713 */     genHeader();
/*     */ 
/* 717 */     println("");
/* 718 */     println("*** Tokens used by the parser");
/* 719 */     println("This is a list of the token numeric values and the corresponding");
/* 720 */     println("token identifiers.  Some tokens are literals, and because of that");
/* 721 */     println("they have no identifiers.  Literals are double-quoted.");
/* 722 */     this.tabs += 1;
/*     */ 
/* 725 */     Vector localVector = paramTokenManager.getVocabulary();
/* 726 */     for (int i = 4; i < localVector.size(); i++) {
/* 727 */       String str = (String)localVector.elementAt(i);
/* 728 */       if (str != null) {
/* 729 */         println(str + " = " + i);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 734 */     this.tabs -= 1;
/* 735 */     println("*** End of tokens used by the parser");
/*     */ 
/* 738 */     this.currentOutput.close();
/* 739 */     this.currentOutput = null;
/*     */   }
/*     */ 
/*     */   protected String processActionForSpecialSymbols(String paramString, int paramInt, RuleBlock paramRuleBlock, ActionTransInfo paramActionTransInfo)
/*     */   {
/* 747 */     return paramString;
/*     */   }
/*     */ 
/*     */   public String getASTCreateString(Vector paramVector)
/*     */   {
/* 754 */     return null;
/*     */   }
/*     */ 
/*     */   public String getASTCreateString(GrammarAtom paramGrammarAtom, String paramString)
/*     */   {
/* 761 */     return null;
/*     */   }
/*     */ 
/*     */   public String mapTreeId(String paramString, ActionTransInfo paramActionTransInfo)
/*     */   {
/* 771 */     return paramString;
/*     */   }
/*     */ 
/*     */   public void printSet(int paramInt1, int paramInt2, Lookahead paramLookahead)
/*     */   {
/* 780 */     int i = 5;
/*     */ 
/* 782 */     int[] arrayOfInt = paramLookahead.fset.toArray();
/*     */ 
/* 784 */     if (paramInt1 != 1) {
/* 785 */       print("k==" + paramInt2 + ": {");
/*     */     }
/*     */     else {
/* 788 */       print("{ ");
/*     */     }
/* 790 */     if (arrayOfInt.length > i) {
/* 791 */       _println("");
/* 792 */       this.tabs += 1;
/* 793 */       print("");
/*     */     }
/*     */ 
/* 796 */     int j = 0;
/* 797 */     for (int k = 0; k < arrayOfInt.length; k++) {
/* 798 */       j++;
/* 799 */       if (j > i) {
/* 800 */         _println("");
/* 801 */         print("");
/* 802 */         j = 0;
/*     */       }
/* 804 */       if (this.doingLexRules) {
/* 805 */         _print(this.charFormatter.literalChar(arrayOfInt[k]));
/*     */       }
/*     */       else {
/* 808 */         _print((String)this.grammar.tokenManager.getVocabulary().elementAt(arrayOfInt[k]));
/*     */       }
/* 810 */       if (k != arrayOfInt.length - 1) {
/* 811 */         _print(", ");
/*     */       }
/*     */     }
/*     */ 
/* 815 */     if (arrayOfInt.length > i) {
/* 816 */       _println("");
/* 817 */       this.tabs -= 1;
/* 818 */       print("");
/*     */     }
/* 820 */     _println(" }");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.DocBookCodeGenerator
 * JD-Core Version:    0.6.2
 */