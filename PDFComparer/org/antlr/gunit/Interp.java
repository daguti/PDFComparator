/*     */ package org.antlr.gunit;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.List;
/*     */ import org.antlr.runtime.ANTLRFileStream;
/*     */ import org.antlr.runtime.ANTLRInputStream;
/*     */ import org.antlr.runtime.CharStream;
/*     */ import org.antlr.runtime.CommonTokenStream;
/*     */ import org.antlr.runtime.RecognitionException;
/*     */ 
/*     */ public class Interp
/*     */ {
/*     */   static String testPackage;
/*     */   static boolean genJUnit;
/*     */   static String gunitFile;
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws IOException, ClassNotFoundException, RecognitionException
/*     */   {
/*  44 */     CharStream input = null;
/*     */ 
/*  46 */     String testsuiteDir = System.getProperty("user.dir");
/*     */ 
/*  48 */     processArgs(args);
/*     */ 
/*  50 */     if (genJUnit) {
/*  51 */       if (gunitFile != null) {
/*  52 */         input = new ANTLRFileStream(gunitFile);
/*  53 */         File f = new File(gunitFile);
/*  54 */         testsuiteDir = getTestsuiteDir(f.getCanonicalPath(), f.getName());
/*     */       }
/*     */       else {
/*  57 */         input = new ANTLRInputStream(System.in);
/*     */       }
/*  59 */       GrammarInfo grammarInfo = parse(input);
/*  60 */       grammarInfo.setTestPackage(testPackage);
/*  61 */       JUnitCodeGen generater = new JUnitCodeGen(grammarInfo, testsuiteDir);
/*  62 */       generater.compile();
/*  63 */       return;
/*     */     }
/*     */ 
/*  66 */     if (gunitFile != null) {
/*  67 */       input = new ANTLRFileStream(gunitFile);
/*  68 */       File f = new File(gunitFile);
/*  69 */       testsuiteDir = getTestsuiteDir(f.getCanonicalPath(), f.getName());
/*     */     }
/*     */     else {
/*  72 */       input = new ANTLRInputStream(System.in);
/*     */     }
/*  74 */     gUnitExecutor executer = new gUnitExecutor(parse(input), testsuiteDir);
/*     */ 
/*  76 */     System.out.print(executer.execTest());
/*     */ 
/*  79 */     System.exit(executer.failures.size() + executer.invalids.size());
/*     */   }
/*     */ 
/*     */   public static void processArgs(String[] args) {
/*  83 */     if ((args == null) || (args.length == 0)) return;
/*  84 */     for (int i = 0; i < args.length; i++)
/*  85 */       if (args[i].equals("-p")) {
/*  86 */         if (i + 1 >= args.length) {
/*  87 */           System.err.println("missing library directory with -lib option; ignoring");
/*     */         }
/*     */         else {
/*  90 */           i++;
/*  91 */           testPackage = args[i];
/*     */         }
/*     */       }
/*  94 */       else if (args[i].equals("-o")) genJUnit = true; else
/*  95 */         gunitFile = args[i];
/*     */   }
/*     */ 
/*     */   public static GrammarInfo parse(CharStream input) throws RecognitionException
/*     */   {
/* 100 */     gUnitLexer lexer = new gUnitLexer(input);
/* 101 */     CommonTokenStream tokens = new CommonTokenStream(lexer);
/*     */ 
/* 103 */     GrammarInfo grammarInfo = new GrammarInfo();
/* 104 */     gUnitParser parser = new gUnitParser(tokens, grammarInfo);
/* 105 */     parser.gUnitDef();
/* 106 */     return grammarInfo;
/*     */   }
/*     */ 
/*     */   public static String getTestsuiteDir(String fullPath, String fileName) {
/* 110 */     return fullPath.substring(0, fullPath.length() - fileName.length());
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.Interp
 * JD-Core Version:    0.6.2
 */