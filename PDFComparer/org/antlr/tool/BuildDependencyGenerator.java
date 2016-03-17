/*     */ package org.antlr.tool;
/*     */ 
/*     */ import antlr.RecognitionException;
/*     */ import antlr.TokenStreamException;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.antlr.Tool;
/*     */ import org.antlr.codegen.CodeGenerator;
/*     */ import org.antlr.misc.Utils;
/*     */ import org.antlr.stringtemplate.StringTemplate;
/*     */ import org.antlr.stringtemplate.StringTemplateGroup;
/*     */ import org.antlr.stringtemplate.language.AngleBracketTemplateLexer;
/*     */ 
/*     */ public class BuildDependencyGenerator
/*     */ {
/*     */   protected String grammarFileName;
/*     */   protected String tokenVocab;
/*     */   protected Tool tool;
/*     */   protected Grammar grammar;
/*     */   protected CodeGenerator generator;
/*     */   protected StringTemplateGroup templates;
/*     */ 
/*     */   public BuildDependencyGenerator(Tool tool, String grammarFileName)
/*     */     throws IOException, TokenStreamException, RecognitionException
/*     */   {
/*  87 */     this.tool = tool;
/*  88 */     this.grammarFileName = grammarFileName;
/*  89 */     this.grammar = tool.getRootGrammar(grammarFileName);
/*  90 */     String language = (String)this.grammar.getOption("language");
/*  91 */     this.generator = new CodeGenerator(tool, this.grammar, language);
/*  92 */     this.generator.loadTemplates(language);
/*     */   }
/*     */ 
/*     */   public List<File> getGeneratedFileList()
/*     */   {
/*  99 */     List files = new ArrayList();
/* 100 */     File outputDir = this.tool.getOutputDirectory(this.grammarFileName);
/* 101 */     if (outputDir.getName().equals(".")) {
/* 102 */       outputDir = null;
/* 103 */     } else if (outputDir.getName().indexOf(' ') >= 0) {
/* 104 */       String escSpaces = Utils.replace(outputDir.toString(), " ", "\\ ");
/*     */ 
/* 107 */       outputDir = new File(escSpaces);
/*     */     }
/*     */ 
/* 110 */     String recognizer = this.generator.getRecognizerFileName(this.grammar.name, this.grammar.type);
/*     */ 
/* 112 */     files.add(new File(outputDir, recognizer));
/*     */ 
/* 116 */     files.add(new File(this.tool.getOutputDirectory(), this.generator.getVocabFileName()));
/*     */ 
/* 118 */     StringTemplate headerExtST = null;
/* 119 */     StringTemplate extST = this.generator.getTemplates().getInstanceOf("codeFileExtension");
/* 120 */     if (this.generator.getTemplates().isDefined("headerFile")) {
/* 121 */       headerExtST = this.generator.getTemplates().getInstanceOf("headerFileExtension");
/* 122 */       String suffix = Grammar.grammarTypeToFileNameSuffix[this.grammar.type];
/* 123 */       String fileName = this.grammar.name + suffix + headerExtST.toString();
/* 124 */       files.add(new File(outputDir, fileName));
/*     */     }
/* 126 */     if (this.grammar.type == 4)
/*     */     {
/* 130 */       String suffix = Grammar.grammarTypeToFileNameSuffix[1];
/* 131 */       String lexer = this.grammar.name + suffix + extST.toString();
/* 132 */       files.add(new File(outputDir, lexer));
/*     */ 
/* 135 */       if (headerExtST != null) {
/* 136 */         String header = this.grammar.name + suffix + headerExtST.toString();
/* 137 */         files.add(new File(outputDir, header));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 143 */     List imports = this.grammar.composite.getDelegates(this.grammar.composite.getRootGrammar());
/*     */ 
/* 145 */     for (Iterator i$ = imports.iterator(); i$.hasNext(); ) { Grammar g = (Grammar)i$.next();
/* 146 */       outputDir = this.tool.getOutputDirectory(g.getFileName());
/* 147 */       String fname = groomQualifiedFileName(outputDir.toString(), g.getRecognizerName() + extST.toString());
/* 148 */       files.add(new File(fname));
/*     */     }
/*     */ 
/* 151 */     if (files.size() == 0) {
/* 152 */       return null;
/*     */     }
/* 154 */     return files;
/*     */   }
/*     */ 
/*     */   public List<File> getDependenciesFileList()
/*     */   {
/* 164 */     List files = getNonImportDependenciesFileList();
/*     */ 
/* 167 */     List imports = this.grammar.composite.getDelegates(this.grammar.composite.getRootGrammar());
/*     */ 
/* 169 */     for (Iterator i$ = imports.iterator(); i$.hasNext(); ) { Grammar g = (Grammar)i$.next();
/* 170 */       String libdir = this.tool.getLibraryDirectory();
/* 171 */       String fileName = groomQualifiedFileName(libdir, g.fileName);
/* 172 */       files.add(new File(fileName));
/*     */     }
/*     */ 
/* 175 */     if (files.size() == 0) {
/* 176 */       return null;
/*     */     }
/* 178 */     return files;
/*     */   }
/*     */ 
/*     */   public List<File> getNonImportDependenciesFileList()
/*     */   {
/* 189 */     List files = new ArrayList();
/*     */ 
/* 192 */     this.tokenVocab = ((String)this.grammar.getOption("tokenVocab"));
/* 193 */     if (this.tokenVocab != null)
/*     */     {
/* 195 */       File vocabFile = this.tool.getImportedVocabFile(this.tokenVocab);
/* 196 */       files.add(vocabFile);
/*     */     }
/*     */ 
/* 199 */     return files;
/*     */   }
/*     */ 
/*     */   public StringTemplate getDependencies() {
/* 203 */     loadDependencyTemplates();
/* 204 */     StringTemplate dependenciesST = this.templates.getInstanceOf("dependencies");
/* 205 */     dependenciesST.setAttribute("in", getDependenciesFileList());
/* 206 */     dependenciesST.setAttribute("out", getGeneratedFileList());
/* 207 */     dependenciesST.setAttribute("grammarFileName", this.grammar.fileName);
/* 208 */     return dependenciesST;
/*     */   }
/*     */ 
/*     */   public void loadDependencyTemplates() {
/* 212 */     if (this.templates != null) {
/* 213 */       return;
/*     */     }
/* 215 */     String fileName = "org/antlr/tool/templates/depend.stg";
/* 216 */     ClassLoader cl = Thread.currentThread().getContextClassLoader();
/* 217 */     InputStream is = cl.getResourceAsStream(fileName);
/* 218 */     if (is == null) {
/* 219 */       cl = ErrorManager.class.getClassLoader();
/* 220 */       is = cl.getResourceAsStream(fileName);
/*     */     }
/* 222 */     if (is == null) {
/* 223 */       ErrorManager.internalError("Can't load dependency templates: " + fileName);
/* 224 */       return;
/*     */     }
/* 226 */     BufferedReader br = null;
/*     */     try {
/* 228 */       br = new BufferedReader(new InputStreamReader(is));
/* 229 */       this.templates = new StringTemplateGroup(br, AngleBracketTemplateLexer.class);
/*     */ 
/* 231 */       br.close();
/*     */     } catch (IOException ioe) {
/* 233 */       ErrorManager.internalError("error reading dependency templates file " + fileName, ioe);
/*     */     } finally {
/* 235 */       if (br != null)
/*     */         try {
/* 237 */           br.close();
/*     */         } catch (IOException ioe) {
/* 239 */           ErrorManager.internalError("cannot close dependency templates file " + fileName, ioe);
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getTokenVocab()
/*     */   {
/* 246 */     return this.tokenVocab;
/*     */   }
/*     */ 
/*     */   public CodeGenerator getGenerator() {
/* 250 */     return this.generator;
/*     */   }
/*     */ 
/*     */   public String groomQualifiedFileName(String outputDir, String fileName) {
/* 254 */     if (outputDir.equals("."))
/* 255 */       return fileName;
/* 256 */     if (outputDir.indexOf(' ') >= 0) {
/* 257 */       String escSpaces = Utils.replace(outputDir.toString(), " ", "\\ ");
/*     */ 
/* 260 */       return escSpaces + File.separator + fileName;
/*     */     }
/* 262 */     return outputDir + File.separator + fileName;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.tool.BuildDependencyGenerator
 * JD-Core Version:    0.6.2
 */