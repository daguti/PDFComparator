/*     */ package org.antlr.tool;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileReader;
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
/*     */ import org.antlr.Tool;
/*     */ import org.antlr.runtime.ANTLRFileStream;
/*     */ import org.antlr.runtime.CharStream;
/*     */ import org.antlr.runtime.CommonTokenStream;
/*     */ import org.antlr.runtime.Token;
/*     */ import org.antlr.runtime.TokenSource;
/*     */ import org.antlr.runtime.tree.ParseTree;
/*     */ 
/*     */ public class Interp
/*     */ {
/*     */   public static void main(String[] args)
/*     */     throws Exception
/*     */   {
/*  65 */     if (args.length != 4) {
/*  66 */       System.err.println("java Interp file.g tokens-to-ignore start-rule input-file");
/*  67 */       return;
/*     */     }
/*  69 */     String grammarFileName = args[0];
/*  70 */     String ignoreTokens = args[1];
/*  71 */     String startRule = args[2];
/*  72 */     String inputFileName = args[3];
/*     */ 
/*  75 */     Tool tool = new Tool();
/*  76 */     CompositeGrammar composite = new CompositeGrammar();
/*  77 */     Grammar parser = new Grammar(tool, grammarFileName, composite);
/*  78 */     composite.setDelegationRoot(parser);
/*  79 */     FileReader fr = new FileReader(grammarFileName);
/*  80 */     BufferedReader br = new BufferedReader(fr);
/*  81 */     parser.parseAndBuildAST(br);
/*  82 */     br.close();
/*     */ 
/*  84 */     parser.composite.assignTokenTypes();
/*  85 */     parser.composite.defineGrammarSymbols();
/*  86 */     parser.composite.createNFAs();
/*     */ 
/*  88 */     List leftRecursiveRules = parser.checkAllRulesForLeftRecursion();
/*  89 */     if (leftRecursiveRules.size() > 0) {
/*  90 */       return;
/*     */     }
/*     */ 
/*  93 */     if (parser.getRule(startRule) == null) {
/*  94 */       System.out.println("undefined start rule " + startRule);
/*  95 */       return;
/*     */     }
/*     */ 
/*  98 */     String lexerGrammarText = parser.getLexerGrammar();
/*  99 */     Grammar lexer = new Grammar();
/* 100 */     lexer.importTokenVocabulary(parser);
/* 101 */     lexer.fileName = grammarFileName;
/* 102 */     lexer.setTool(tool);
/* 103 */     if (lexerGrammarText != null) {
/* 104 */       lexer.setGrammarContent(lexerGrammarText);
/*     */     }
/*     */     else {
/* 107 */       System.err.println("no lexer grammar found in " + grammarFileName);
/*     */     }
/* 109 */     lexer.composite.createNFAs();
/*     */ 
/* 111 */     CharStream input = new ANTLRFileStream(inputFileName);
/*     */ 
/* 113 */     Interpreter lexEngine = new Interpreter(lexer, input);
/* 114 */     FilteringTokenStream tokens = new FilteringTokenStream(lexEngine);
/* 115 */     StringTokenizer tk = new StringTokenizer(ignoreTokens, " ");
/* 116 */     while (tk.hasMoreTokens()) {
/* 117 */       String tokenName = tk.nextToken();
/* 118 */       tokens.setTokenTypeChannel(lexer.getTokenType(tokenName), 99);
/*     */     }
/*     */ 
/* 121 */     if (parser.getRule(startRule) == null) {
/* 122 */       System.err.println("Rule " + startRule + " does not exist in " + grammarFileName);
/* 123 */       return;
/*     */     }
/* 125 */     Interpreter parseEngine = new Interpreter(parser, tokens);
/* 126 */     ParseTree t = parseEngine.parse(startRule);
/* 127 */     System.out.println(t.toStringTree());
/*     */   }
/*     */ 
/*     */   public static class FilteringTokenStream extends CommonTokenStream
/*     */   {
/*  53 */     Set<Integer> hide = new HashSet();
/*     */ 
/*     */     public FilteringTokenStream(TokenSource src)
/*     */     {
/*  52 */       super();
/*     */     }
/*     */     protected void sync(int i) {
/*  55 */       super.sync(i);
/*  56 */       if (this.hide.contains(new Integer(get(i).getType()))) get(i).setChannel(99); 
/*     */     }
/*     */ 
/*  59 */     public void setTokenTypeChannel(int ttype, int channel) { this.hide.add(new Integer(ttype)); }
/*     */ 
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.tool.Interp
 * JD-Core Version:    0.6.2
 */