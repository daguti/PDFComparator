/*     */ package antlr.preprocessor;
/*     */ 
/*     */ import antlr.collections.impl.Vector;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Enumeration;
/*     */ 
/*     */ public class Tool
/*     */ {
/*     */   protected Hierarchy theHierarchy;
/*     */   protected String grammarFileName;
/*     */   protected String[] args;
/*     */   protected int nargs;
/*     */   protected Vector grammars;
/*     */   protected antlr.Tool antlrTool;
/*     */ 
/*     */   public Tool(antlr.Tool paramTool, String[] paramArrayOfString)
/*     */   {
/*  24 */     this.antlrTool = paramTool;
/*  25 */     processArguments(paramArrayOfString);
/*     */   }
/*     */ 
/*     */   public static void main(String[] paramArrayOfString) {
/*  29 */     antlr.Tool localTool = new antlr.Tool();
/*  30 */     Tool localTool1 = new Tool(localTool, paramArrayOfString);
/*  31 */     localTool1.preprocess();
/*  32 */     String[] arrayOfString = localTool1.preprocessedArgList();
/*  33 */     for (int i = 0; i < arrayOfString.length; i++) {
/*  34 */       System.out.print(" " + arrayOfString[i]);
/*     */     }
/*  36 */     System.out.println();
/*     */   }
/*     */ 
/*     */   public boolean preprocess() {
/*  40 */     if (this.grammarFileName == null) {
/*  41 */       this.antlrTool.toolError("no grammar file specified");
/*  42 */       return false;
/*     */     }
/*     */     Enumeration localEnumeration;
/*  44 */     if (this.grammars != null) {
/*  45 */       this.theHierarchy = new Hierarchy(this.antlrTool);
/*  46 */       for (localEnumeration = this.grammars.elements(); localEnumeration.hasMoreElements(); ) {
/*  47 */         localObject = (String)localEnumeration.nextElement();
/*     */         try {
/*  49 */           this.theHierarchy.readGrammarFile((String)localObject);
/*     */         }
/*     */         catch (FileNotFoundException localFileNotFoundException) {
/*  52 */           this.antlrTool.toolError("file " + (String)localObject + " not found");
/*  53 */           return false;
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  59 */     boolean bool = this.theHierarchy.verifyThatHierarchyIsComplete();
/*  60 */     if (!bool)
/*  61 */       return false;
/*  62 */     this.theHierarchy.expandGrammarsInFile(this.grammarFileName);
/*  63 */     Object localObject = this.theHierarchy.getFile(this.grammarFileName);
/*  64 */     String str = ((GrammarFile)localObject).nameForExpandedGrammarFile(this.grammarFileName);
/*     */ 
/*  67 */     if (str.equals(this.grammarFileName))
/*  68 */       this.args[(this.nargs++)] = this.grammarFileName;
/*     */     else {
/*     */       try
/*     */       {
/*  72 */         ((GrammarFile)localObject).generateExpandedFile();
/*  73 */         this.args[(this.nargs++)] = (this.antlrTool.getOutputDirectory() + System.getProperty("file.separator") + str);
/*     */       }
/*     */       catch (IOException localIOException)
/*     */       {
/*  78 */         this.antlrTool.toolError("cannot write expanded grammar file " + str);
/*  79 */         return false;
/*     */       }
/*     */     }
/*  82 */     return true;
/*     */   }
/*     */ 
/*     */   public String[] preprocessedArgList()
/*     */   {
/*  87 */     String[] arrayOfString = new String[this.nargs];
/*  88 */     System.arraycopy(this.args, 0, arrayOfString, 0, this.nargs);
/*  89 */     this.args = arrayOfString;
/*  90 */     return this.args;
/*     */   }
/*     */ 
/*     */   private void processArguments(String[] paramArrayOfString)
/*     */   {
/*  98 */     this.nargs = 0;
/*  99 */     this.args = new String[paramArrayOfString.length];
/* 100 */     for (int i = 0; i < paramArrayOfString.length; i++)
/* 101 */       if (paramArrayOfString[i].length() == 0)
/*     */       {
/* 103 */         this.antlrTool.warning("Zero length argument ignoring...");
/*     */       }
/* 106 */       else if (paramArrayOfString[i].equals("-glib"))
/*     */       {
/* 108 */         if ((File.separator.equals("\\")) && (paramArrayOfString[i].indexOf('/') != -1))
/*     */         {
/* 110 */           this.antlrTool.warning("-glib cannot deal with '/' on a PC: use '\\'; ignoring...");
/*     */         }
/*     */         else {
/* 113 */           this.grammars = antlr.Tool.parseSeparatedList(paramArrayOfString[(i + 1)], ';');
/* 114 */           i++;
/*     */         }
/*     */       }
/* 117 */       else if (paramArrayOfString[i].equals("-o")) {
/* 118 */         this.args[(this.nargs++)] = paramArrayOfString[i];
/* 119 */         if (i + 1 >= paramArrayOfString.length) {
/* 120 */           this.antlrTool.error("missing output directory with -o option; ignoring");
/*     */         }
/*     */         else {
/* 123 */           i++;
/* 124 */           this.args[(this.nargs++)] = paramArrayOfString[i];
/* 125 */           this.antlrTool.setOutputDirectory(paramArrayOfString[i]);
/*     */         }
/*     */       }
/* 128 */       else if (paramArrayOfString[i].charAt(0) == '-') {
/* 129 */         this.args[(this.nargs++)] = paramArrayOfString[i];
/*     */       }
/*     */       else
/*     */       {
/* 133 */         this.grammarFileName = paramArrayOfString[i];
/* 134 */         if (this.grammars == null) {
/* 135 */           this.grammars = new Vector(10);
/*     */         }
/* 137 */         this.grammars.appendElement(this.grammarFileName);
/* 138 */         if (i + 1 < paramArrayOfString.length) {
/* 139 */           this.antlrTool.warning("grammar file must be last; ignoring other arguments...");
/* 140 */           break;
/*     */         }
/*     */       }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.preprocessor.Tool
 * JD-Core Version:    0.6.2
 */