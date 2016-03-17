/*     */ package antlr;
/*     */ 
/*     */ import antlr.collections.impl.BitSet;
/*     */ import antlr.collections.impl.Vector;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ public class HTMLCodeGenerator extends CodeGenerator
/*     */ {
/*  22 */   protected int syntacticPredLevel = 0;
/*     */ 
/*  25 */   protected boolean doingLexRules = false;
/*     */   protected boolean firstElementInAlt;
/*  29 */   protected AlternativeElement prevAltElem = null;
/*     */ 
/*     */   public HTMLCodeGenerator()
/*     */   {
/*  37 */     this.charFormatter = new JavaCharFormatter();
/*     */   }
/*     */ 
/*     */   static String HTMLEncode(String paramString)
/*     */   {
/*  45 */     StringBuffer localStringBuffer = new StringBuffer();
/*     */ 
/*  47 */     int i = 0; for (int j = paramString.length(); i < j; i++) {
/*  48 */       char c = paramString.charAt(i);
/*  49 */       if (c == '&')
/*  50 */         localStringBuffer.append("&amp;");
/*  51 */       else if (c == '"')
/*  52 */         localStringBuffer.append("&quot;");
/*  53 */       else if (c == '\'')
/*  54 */         localStringBuffer.append("&#039;");
/*  55 */       else if (c == '<')
/*  56 */         localStringBuffer.append("&lt;");
/*  57 */       else if (c == '>')
/*  58 */         localStringBuffer.append("&gt;");
/*     */       else
/*  60 */         localStringBuffer.append(c);
/*     */     }
/*  62 */     return localStringBuffer.toString();
/*     */   }
/*     */ 
/*     */   public void gen()
/*     */   {
/*     */     try
/*     */     {
/*  69 */       Enumeration localEnumeration = this.behavior.grammars.elements();
/*  70 */       while (localEnumeration.hasMoreElements()) {
/*  71 */         Grammar localGrammar = (Grammar)localEnumeration.nextElement();
/*     */ 
/*  78 */         localGrammar.setCodeGenerator(this);
/*     */ 
/*  81 */         localGrammar.generate();
/*     */ 
/*  83 */         if (this.antlrTool.hasError()) {
/*  84 */           this.antlrTool.fatalError("Exiting due to errors.");
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/*  91 */       this.antlrTool.reportException(localIOException, null);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void gen(ActionElement paramActionElement)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void gen(AlternativeBlock paramAlternativeBlock)
/*     */   {
/* 106 */     genGenericBlock(paramAlternativeBlock, "");
/*     */   }
/*     */ 
/*     */   public void gen(BlockEndElement paramBlockEndElement)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void gen(CharLiteralElement paramCharLiteralElement)
/*     */   {
/* 122 */     if (paramCharLiteralElement.not) {
/* 123 */       _print("~");
/*     */     }
/* 125 */     _print(HTMLEncode(paramCharLiteralElement.atomText) + " ");
/*     */   }
/*     */ 
/*     */   public void gen(CharRangeElement paramCharRangeElement)
/*     */   {
/* 132 */     print(paramCharRangeElement.beginText + ".." + paramCharRangeElement.endText + " ");
/*     */   }
/*     */ 
/*     */   public void gen(LexerGrammar paramLexerGrammar) throws IOException
/*     */   {
/* 137 */     setGrammar(paramLexerGrammar);
/* 138 */     this.antlrTool.reportProgress("Generating " + this.grammar.getClassName() + ".html");
/* 139 */     this.currentOutput = this.antlrTool.openOutputFile(this.grammar.getClassName() + ".html");
/*     */ 
/* 142 */     this.tabs = 0;
/* 143 */     this.doingLexRules = true;
/*     */ 
/* 146 */     genHeader();
/*     */ 
/* 153 */     println("");
/*     */ 
/* 156 */     if (this.grammar.comment != null) {
/* 157 */       _println(HTMLEncode(this.grammar.comment));
/*     */     }
/*     */ 
/* 160 */     println("Definition of lexer " + this.grammar.getClassName() + ", which is a subclass of " + this.grammar.getSuperClass() + ".");
/*     */ 
/* 193 */     genNextToken();
/*     */ 
/* 197 */     Enumeration localEnumeration = this.grammar.rules.elements();
/* 198 */     while (localEnumeration.hasMoreElements()) {
/* 199 */       RuleSymbol localRuleSymbol = (RuleSymbol)localEnumeration.nextElement();
/* 200 */       if (!localRuleSymbol.id.equals("mnextToken")) {
/* 201 */         genRule(localRuleSymbol);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 206 */     this.currentOutput.close();
/* 207 */     this.currentOutput = null;
/* 208 */     this.doingLexRules = false;
/*     */   }
/*     */ 
/*     */   public void gen(OneOrMoreBlock paramOneOrMoreBlock)
/*     */   {
/* 215 */     genGenericBlock(paramOneOrMoreBlock, "+");
/*     */   }
/*     */ 
/*     */   public void gen(ParserGrammar paramParserGrammar) throws IOException
/*     */   {
/* 220 */     setGrammar(paramParserGrammar);
/*     */ 
/* 222 */     this.antlrTool.reportProgress("Generating " + this.grammar.getClassName() + ".html");
/* 223 */     this.currentOutput = this.antlrTool.openOutputFile(this.grammar.getClassName() + ".html");
/*     */ 
/* 225 */     this.tabs = 0;
/*     */ 
/* 228 */     genHeader();
/*     */ 
/* 231 */     println("");
/*     */ 
/* 234 */     if (this.grammar.comment != null) {
/* 235 */       _println(HTMLEncode(this.grammar.comment));
/*     */     }
/*     */ 
/* 238 */     println("Definition of parser " + this.grammar.getClassName() + ", which is a subclass of " + this.grammar.getSuperClass() + ".");
/*     */ 
/* 241 */     Enumeration localEnumeration = this.grammar.rules.elements();
/* 242 */     while (localEnumeration.hasMoreElements()) {
/* 243 */       println("");
/*     */ 
/* 245 */       GrammarSymbol localGrammarSymbol = (GrammarSymbol)localEnumeration.nextElement();
/*     */ 
/* 247 */       if ((localGrammarSymbol instanceof RuleSymbol)) {
/* 248 */         genRule((RuleSymbol)localGrammarSymbol);
/*     */       }
/*     */     }
/* 251 */     this.tabs -= 1;
/* 252 */     println("");
/*     */ 
/* 254 */     genTail();
/*     */ 
/* 257 */     this.currentOutput.close();
/* 258 */     this.currentOutput = null;
/*     */   }
/*     */ 
/*     */   public void gen(RuleRefElement paramRuleRefElement)
/*     */   {
/* 265 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(paramRuleRefElement.targetRule);
/*     */ 
/* 268 */     _print("<a href=\"" + this.grammar.getClassName() + ".html#" + paramRuleRefElement.targetRule + "\">");
/* 269 */     _print(paramRuleRefElement.targetRule);
/* 270 */     _print("</a>");
/*     */ 
/* 275 */     _print(" ");
/*     */   }
/*     */ 
/*     */   public void gen(StringLiteralElement paramStringLiteralElement)
/*     */   {
/* 282 */     if (paramStringLiteralElement.not) {
/* 283 */       _print("~");
/*     */     }
/* 285 */     _print(HTMLEncode(paramStringLiteralElement.atomText));
/* 286 */     _print(" ");
/*     */   }
/*     */ 
/*     */   public void gen(TokenRangeElement paramTokenRangeElement)
/*     */   {
/* 293 */     print(paramTokenRangeElement.beginText + ".." + paramTokenRangeElement.endText + " ");
/*     */   }
/*     */ 
/*     */   public void gen(TokenRefElement paramTokenRefElement)
/*     */   {
/* 300 */     if (paramTokenRefElement.not) {
/* 301 */       _print("~");
/*     */     }
/* 303 */     _print(paramTokenRefElement.atomText);
/* 304 */     _print(" ");
/*     */   }
/*     */ 
/*     */   public void gen(TreeElement paramTreeElement) {
/* 308 */     print(paramTreeElement + " ");
/*     */   }
/*     */ 
/*     */   public void gen(TreeWalkerGrammar paramTreeWalkerGrammar) throws IOException
/*     */   {
/* 313 */     setGrammar(paramTreeWalkerGrammar);
/*     */ 
/* 315 */     this.antlrTool.reportProgress("Generating " + this.grammar.getClassName() + ".html");
/* 316 */     this.currentOutput = this.antlrTool.openOutputFile(this.grammar.getClassName() + ".html");
/*     */ 
/* 319 */     this.tabs = 0;
/*     */ 
/* 322 */     genHeader();
/*     */ 
/* 325 */     println("");
/*     */ 
/* 334 */     println("");
/*     */ 
/* 337 */     if (this.grammar.comment != null) {
/* 338 */       _println(HTMLEncode(this.grammar.comment));
/*     */     }
/*     */ 
/* 341 */     println("Definition of tree parser " + this.grammar.getClassName() + ", which is a subclass of " + this.grammar.getSuperClass() + ".");
/*     */ 
/* 353 */     println("");
/*     */ 
/* 355 */     this.tabs += 1;
/*     */ 
/* 358 */     Enumeration localEnumeration = this.grammar.rules.elements();
/* 359 */     while (localEnumeration.hasMoreElements()) {
/* 360 */       println("");
/*     */ 
/* 362 */       GrammarSymbol localGrammarSymbol = (GrammarSymbol)localEnumeration.nextElement();
/*     */ 
/* 364 */       if ((localGrammarSymbol instanceof RuleSymbol)) {
/* 365 */         genRule((RuleSymbol)localGrammarSymbol);
/*     */       }
/*     */     }
/* 368 */     this.tabs -= 1;
/* 369 */     println("");
/*     */ 
/* 376 */     this.currentOutput.close();
/* 377 */     this.currentOutput = null;
/*     */   }
/*     */ 
/*     */   public void gen(WildcardElement paramWildcardElement)
/*     */   {
/* 387 */     _print(". ");
/*     */   }
/*     */ 
/*     */   public void gen(ZeroOrMoreBlock paramZeroOrMoreBlock)
/*     */   {
/* 394 */     genGenericBlock(paramZeroOrMoreBlock, "*");
/*     */   }
/*     */ 
/*     */   protected void genAlt(Alternative paramAlternative) {
/* 398 */     if (paramAlternative.getTreeSpecifier() != null) {
/* 399 */       _print(paramAlternative.getTreeSpecifier().getText());
/*     */     }
/* 401 */     this.prevAltElem = null;
/* 402 */     for (AlternativeElement localAlternativeElement = paramAlternative.head; 
/* 403 */       !(localAlternativeElement instanceof BlockEndElement); 
/* 404 */       localAlternativeElement = localAlternativeElement.next) {
/* 405 */       localAlternativeElement.generate();
/* 406 */       this.firstElementInAlt = false;
/* 407 */       this.prevAltElem = localAlternativeElement;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void genCommonBlock(AlternativeBlock paramAlternativeBlock)
/*     */   {
/* 428 */     for (int i = 0; i < paramAlternativeBlock.alternatives.size(); i++) {
/* 429 */       Alternative localAlternative = paramAlternativeBlock.getAlternativeAt(i);
/* 430 */       AlternativeElement localAlternativeElement = localAlternative.head;
/*     */ 
/* 433 */       if ((i > 0) && (paramAlternativeBlock.alternatives.size() > 1)) {
/* 434 */         _println("");
/* 435 */         print("|\t");
/*     */       }
/*     */ 
/* 440 */       boolean bool = this.firstElementInAlt;
/* 441 */       this.firstElementInAlt = true;
/* 442 */       this.tabs += 1;
/*     */ 
/* 456 */       genAlt(localAlternative);
/* 457 */       this.tabs -= 1;
/* 458 */       this.firstElementInAlt = bool;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void genFollowSetForRuleBlock(RuleBlock paramRuleBlock)
/*     */   {
/* 467 */     Lookahead localLookahead = this.grammar.theLLkAnalyzer.FOLLOW(1, paramRuleBlock.endNode);
/* 468 */     printSet(this.grammar.maxk, 1, localLookahead);
/*     */   }
/*     */ 
/*     */   protected void genGenericBlock(AlternativeBlock paramAlternativeBlock, String paramString) {
/* 472 */     if (paramAlternativeBlock.alternatives.size() > 1)
/*     */     {
/* 474 */       if (!this.firstElementInAlt)
/*     */       {
/* 476 */         if ((this.prevAltElem == null) || (!(this.prevAltElem instanceof AlternativeBlock)) || (((AlternativeBlock)this.prevAltElem).alternatives.size() == 1))
/*     */         {
/* 479 */           _println("");
/* 480 */           print("(\t");
/*     */         }
/*     */         else {
/* 483 */           _print("(\t");
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 489 */         _print("(\t");
/*     */       }
/*     */     }
/*     */     else {
/* 493 */       _print("( ");
/*     */     }
/*     */ 
/* 497 */     genCommonBlock(paramAlternativeBlock);
/* 498 */     if (paramAlternativeBlock.alternatives.size() > 1) {
/* 499 */       _println("");
/* 500 */       print(")" + paramString + " ");
/*     */ 
/* 502 */       if (!(paramAlternativeBlock.next instanceof BlockEndElement)) {
/* 503 */         _println("");
/* 504 */         print("");
/*     */       }
/*     */     }
/*     */     else {
/* 508 */       _print(")" + paramString + " ");
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void genHeader()
/*     */   {
/* 514 */     println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
/* 515 */     println("<HTML>");
/* 516 */     println("<HEAD>");
/* 517 */     println("<TITLE>Grammar " + this.antlrTool.grammarFile + "</TITLE>");
/* 518 */     println("</HEAD>");
/* 519 */     println("<BODY>");
/* 520 */     println("<table summary=\"\" border=\"1\" cellpadding=\"5\">");
/* 521 */     println("<tr>");
/* 522 */     println("<td>");
/* 523 */     println("<font size=\"+2\">Grammar " + this.grammar.getClassName() + "</font><br>");
/* 524 */     println("<a href=\"http://www.ANTLR.org\">ANTLR</a>-generated HTML file from " + this.antlrTool.grammarFile);
/* 525 */     println("<p>");
/* 526 */     println("Terence Parr, <a href=\"http://www.magelang.com\">MageLang Institute</a>");
/* 527 */     println("<br>ANTLR Version " + Tool.version + "; 1989-2005");
/* 528 */     println("</td>");
/* 529 */     println("</tr>");
/* 530 */     println("</table>");
/* 531 */     println("<PRE>");
/*     */   }
/*     */ 
/*     */   protected void genLookaheadSetForAlt(Alternative paramAlternative)
/*     */   {
/* 540 */     if ((this.doingLexRules) && (paramAlternative.cache[1].containsEpsilon())) {
/* 541 */       println("MATCHES ALL");
/* 542 */       return;
/*     */     }
/* 544 */     int i = paramAlternative.lookaheadDepth;
/* 545 */     if (i == 2147483647)
/*     */     {
/* 548 */       i = this.grammar.maxk;
/*     */     }
/* 550 */     for (int j = 1; j <= i; j++) {
/* 551 */       Lookahead localLookahead = paramAlternative.cache[j];
/* 552 */       printSet(i, j, localLookahead);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void genLookaheadSetForBlock(AlternativeBlock paramAlternativeBlock)
/*     */   {
/* 562 */     int i = 0;
/*     */     Object localObject;
/* 563 */     for (int j = 0; j < paramAlternativeBlock.alternatives.size(); j++) {
/* 564 */       localObject = paramAlternativeBlock.getAlternativeAt(j);
/* 565 */       if (((Alternative)localObject).lookaheadDepth == 2147483647) {
/* 566 */         i = this.grammar.maxk;
/* 567 */         break;
/*     */       }
/* 569 */       if (i < ((Alternative)localObject).lookaheadDepth) {
/* 570 */         i = ((Alternative)localObject).lookaheadDepth;
/*     */       }
/*     */     }
/*     */ 
/* 574 */     for (j = 1; j <= i; j++) {
/* 575 */       localObject = this.grammar.theLLkAnalyzer.look(j, paramAlternativeBlock);
/* 576 */       printSet(i, j, (Lookahead)localObject);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void genNextToken()
/*     */   {
/* 585 */     println("");
/* 586 */     println("/** Lexer nextToken rule:");
/* 587 */     println(" *  The lexer nextToken rule is synthesized from all of the user-defined");
/* 588 */     println(" *  lexer rules.  It logically consists of one big alternative block with");
/* 589 */     println(" *  each user-defined rule being an alternative.");
/* 590 */     println(" */");
/*     */ 
/* 594 */     RuleBlock localRuleBlock = MakeGrammar.createNextTokenRule(this.grammar, this.grammar.rules, "nextToken");
/*     */ 
/* 597 */     RuleSymbol localRuleSymbol = new RuleSymbol("mnextToken");
/* 598 */     localRuleSymbol.setDefined();
/* 599 */     localRuleSymbol.setBlock(localRuleBlock);
/* 600 */     localRuleSymbol.access = "private";
/* 601 */     this.grammar.define(localRuleSymbol);
/*     */ 
/* 614 */     genCommonBlock(localRuleBlock);
/*     */   }
/*     */ 
/*     */   public void genRule(RuleSymbol paramRuleSymbol)
/*     */   {
/* 621 */     if ((paramRuleSymbol == null) || (!paramRuleSymbol.isDefined())) return;
/* 622 */     println("");
/* 623 */     if (paramRuleSymbol.comment != null) {
/* 624 */       _println(HTMLEncode(paramRuleSymbol.comment));
/*     */     }
/* 626 */     if ((paramRuleSymbol.access.length() != 0) && 
/* 627 */       (!paramRuleSymbol.access.equals("public"))) {
/* 628 */       _print(paramRuleSymbol.access + " ");
/*     */     }
/*     */ 
/* 631 */     _print("<a name=\"" + paramRuleSymbol.getId() + "\">");
/* 632 */     _print(paramRuleSymbol.getId());
/* 633 */     _print("</a>");
/*     */ 
/* 636 */     RuleBlock localRuleBlock = paramRuleSymbol.getBlock();
/*     */ 
/* 648 */     _println("");
/* 649 */     this.tabs += 1;
/* 650 */     print(":\t");
/*     */ 
/* 656 */     genCommonBlock(localRuleBlock);
/*     */ 
/* 658 */     _println("");
/* 659 */     println(";");
/* 660 */     this.tabs -= 1;
/*     */   }
/*     */ 
/*     */   protected void genSynPred(SynPredBlock paramSynPredBlock)
/*     */   {
/* 668 */     this.syntacticPredLevel += 1;
/* 669 */     genGenericBlock(paramSynPredBlock, " =>");
/* 670 */     this.syntacticPredLevel -= 1;
/*     */   }
/*     */ 
/*     */   public void genTail() {
/* 674 */     println("</PRE>");
/* 675 */     println("</BODY>");
/* 676 */     println("</HTML>");
/*     */   }
/*     */ 
/*     */   protected void genTokenTypes(TokenManager paramTokenManager)
/*     */     throws IOException
/*     */   {
/* 682 */     this.antlrTool.reportProgress("Generating " + paramTokenManager.getName() + TokenTypesFileSuffix + TokenTypesFileExt);
/* 683 */     this.currentOutput = this.antlrTool.openOutputFile(paramTokenManager.getName() + TokenTypesFileSuffix + TokenTypesFileExt);
/*     */ 
/* 685 */     this.tabs = 0;
/*     */ 
/* 688 */     genHeader();
/*     */ 
/* 692 */     println("");
/* 693 */     println("*** Tokens used by the parser");
/* 694 */     println("This is a list of the token numeric values and the corresponding");
/* 695 */     println("token identifiers.  Some tokens are literals, and because of that");
/* 696 */     println("they have no identifiers.  Literals are double-quoted.");
/* 697 */     this.tabs += 1;
/*     */ 
/* 700 */     Vector localVector = paramTokenManager.getVocabulary();
/* 701 */     for (int i = 4; i < localVector.size(); i++) {
/* 702 */       String str = (String)localVector.elementAt(i);
/* 703 */       if (str != null) {
/* 704 */         println(str + " = " + i);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 709 */     this.tabs -= 1;
/* 710 */     println("*** End of tokens used by the parser");
/*     */ 
/* 713 */     this.currentOutput.close();
/* 714 */     this.currentOutput = null;
/*     */   }
/*     */ 
/*     */   public String getASTCreateString(Vector paramVector)
/*     */   {
/* 721 */     return null;
/*     */   }
/*     */ 
/*     */   public String getASTCreateString(GrammarAtom paramGrammarAtom, String paramString)
/*     */   {
/* 728 */     return null;
/*     */   }
/*     */ 
/*     */   public String mapTreeId(String paramString, ActionTransInfo paramActionTransInfo)
/*     */   {
/* 738 */     return paramString;
/*     */   }
/*     */ 
/*     */   protected String processActionForSpecialSymbols(String paramString, int paramInt, RuleBlock paramRuleBlock, ActionTransInfo paramActionTransInfo)
/*     */   {
/* 746 */     return paramString;
/*     */   }
/*     */ 
/*     */   public void printSet(int paramInt1, int paramInt2, Lookahead paramLookahead)
/*     */   {
/* 755 */     int i = 5;
/*     */ 
/* 757 */     int[] arrayOfInt = paramLookahead.fset.toArray();
/*     */ 
/* 759 */     if (paramInt1 != 1) {
/* 760 */       print("k==" + paramInt2 + ": {");
/*     */     }
/*     */     else {
/* 763 */       print("{ ");
/*     */     }
/* 765 */     if (arrayOfInt.length > i) {
/* 766 */       _println("");
/* 767 */       this.tabs += 1;
/* 768 */       print("");
/*     */     }
/*     */ 
/* 771 */     int j = 0;
/* 772 */     for (int k = 0; k < arrayOfInt.length; k++) {
/* 773 */       j++;
/* 774 */       if (j > i) {
/* 775 */         _println("");
/* 776 */         print("");
/* 777 */         j = 0;
/*     */       }
/* 779 */       if (this.doingLexRules) {
/* 780 */         _print(this.charFormatter.literalChar(arrayOfInt[k]));
/*     */       }
/*     */       else {
/* 783 */         _print((String)this.grammar.tokenManager.getVocabulary().elementAt(arrayOfInt[k]));
/*     */       }
/* 785 */       if (k != arrayOfInt.length - 1) {
/* 786 */         _print(", ");
/*     */       }
/*     */     }
/*     */ 
/* 790 */     if (arrayOfInt.length > i) {
/* 791 */       _println("");
/* 792 */       this.tabs -= 1;
/* 793 */       print("");
/*     */     }
/* 795 */     _println(" }");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.HTMLCodeGenerator
 * JD-Core Version:    0.6.2
 */