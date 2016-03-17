/*     */ package org.stringtemplate.v4.compiler;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.antlr.runtime.ANTLRStringStream;
/*     */ import org.antlr.runtime.CharStream;
/*     */ import org.antlr.runtime.CommonTokenStream;
/*     */ import org.antlr.runtime.NoViableAltException;
/*     */ import org.antlr.runtime.Parser;
/*     */ import org.antlr.runtime.RecognitionException;
/*     */ import org.antlr.runtime.Token;
/*     */ import org.antlr.runtime.TokenStream;
/*     */ import org.antlr.runtime.tree.CommonTree;
/*     */ import org.antlr.runtime.tree.CommonTreeNodeStream;
/*     */ import org.stringtemplate.v4.Interpreter.Option;
/*     */ import org.stringtemplate.v4.ST.RegionType;
/*     */ import org.stringtemplate.v4.STGroup;
/*     */ import org.stringtemplate.v4.misc.ErrorManager;
/*     */ import org.stringtemplate.v4.misc.ErrorType;
/*     */ 
/*     */ public class Compiler
/*     */ {
/*     */   public static final String SUBTEMPLATE_PREFIX = "_sub";
/*     */   public static final int TEMPLATE_INITIAL_CODE_SIZE = 15;
/*  48 */   public static final Map<String, Interpreter.Option> supportedOptions = new HashMap() { } ;
/*     */ 
/*  59 */   public static final int NUM_OPTIONS = supportedOptions.size();
/*     */ 
/*  61 */   public static final Map<String, String> defaultOptionValues = new HashMap() { } ;
/*     */ 
/*  69 */   public static Map<String, Short> funcs = new HashMap() { } ;
/*     */ 
/*  84 */   public static int subtemplateCount = 0;
/*     */   public STGroup group;
/*     */ 
/*     */   public Compiler()
/*     */   {
/*  88 */     this(STGroup.defaultGroup); } 
/*  89 */   public Compiler(STGroup group) { this.group = group; }
/*     */ 
/*     */   public CompiledST compile(String template) {
/*  92 */     CompiledST code = compile(null, null, null, template, null);
/*  93 */     code.hasFormalArgs = false;
/*  94 */     return code;
/*     */   }
/*     */ 
/*     */   public CompiledST compile(String name, String template)
/*     */   {
/*  99 */     CompiledST code = compile(null, name, null, template, null);
/* 100 */     code.hasFormalArgs = false;
/* 101 */     return code;
/*     */   }
/*     */ 
/*     */   public CompiledST compile(String srcName, String name, List<FormalArgument> args, String template, Token templateToken)
/*     */   {
/* 111 */     ANTLRStringStream is = new ANTLRStringStream(template);
/* 112 */     is.name = (srcName != null ? srcName : name);
/* 113 */     STLexer lexer = null;
/* 114 */     if ((templateToken != null) && (templateToken.getType() == 6))
/*     */     {
/* 117 */       lexer = new STLexer(this.group.errMgr, is, templateToken, this.group.delimiterStartChar, this.group.delimiterStopChar)
/*     */       {
/*     */         public Token nextToken()
/*     */         {
/* 122 */           Token t = super.nextToken();
/*     */ 
/* 124 */           while ((t.getType() == 32) || (t.getType() == 31))
/*     */           {
/* 126 */             t = super.nextToken();
/*     */           }
/* 128 */           return t;
/*     */         }
/*     */       };
/*     */     }
/*     */     else {
/* 133 */       lexer = new STLexer(this.group.errMgr, is, templateToken, this.group.delimiterStartChar, this.group.delimiterStopChar);
/*     */     }
/*     */ 
/* 136 */     CommonTokenStream tokens = new CommonTokenStream(lexer);
/* 137 */     STParser p = new STParser(tokens, this.group.errMgr, templateToken);
/* 138 */     STParser.templateAndEOF_return r = null;
/*     */     try {
/* 140 */       r = p.templateAndEOF();
/*     */     }
/*     */     catch (RecognitionException re) {
/* 143 */       reportMessageAndThrowSTException(tokens, templateToken, p, re);
/* 144 */       return null;
/*     */     }
/* 146 */     if ((p.getNumberOfSyntaxErrors() > 0) || (r.getTree() == null)) {
/* 147 */       CompiledST impl = new CompiledST();
/* 148 */       impl.defineFormalArgs(args);
/* 149 */       return impl;
/*     */     }
/*     */ 
/* 153 */     CommonTreeNodeStream nodes = new CommonTreeNodeStream(r.getTree());
/* 154 */     nodes.setTokenStream(tokens);
/* 155 */     CodeGenerator gen = new CodeGenerator(nodes, this.group.errMgr, name, template, templateToken);
/*     */ 
/* 157 */     CompiledST impl = null;
/*     */     try {
/* 159 */       impl = gen.template(name, args);
/* 160 */       impl.nativeGroup = this.group;
/* 161 */       impl.template = template;
/* 162 */       impl.ast = ((CommonTree)r.getTree());
/* 163 */       impl.ast.setUnknownTokenBoundaries();
/* 164 */       impl.tokens = tokens;
/*     */     }
/*     */     catch (RecognitionException re) {
/* 167 */       this.group.errMgr.internalError(null, "bad tree structure", re);
/*     */     }
/*     */ 
/* 170 */     return impl;
/*     */   }
/*     */ 
/*     */   public static CompiledST defineBlankRegion(CompiledST outermostImpl, Token nameToken) {
/* 174 */     String outermostTemplateName = outermostImpl.name;
/* 175 */     String mangled = STGroup.getMangledRegionName(outermostTemplateName, nameToken.getText());
/* 176 */     CompiledST blank = new CompiledST();
/* 177 */     blank.isRegion = true;
/* 178 */     blank.templateDefStartToken = nameToken;
/* 179 */     blank.regionDefType = ST.RegionType.IMPLICIT;
/* 180 */     blank.name = mangled;
/* 181 */     outermostImpl.addImplicitlyDefinedTemplate(blank);
/* 182 */     return blank;
/*     */   }
/*     */ 
/*     */   public static String getNewSubtemplateName() {
/* 186 */     subtemplateCount += 1;
/* 187 */     return "_sub" + subtemplateCount;
/*     */   }
/*     */ 
/*     */   protected void reportMessageAndThrowSTException(TokenStream tokens, Token templateToken, Parser parser, RecognitionException re)
/*     */   {
/* 193 */     if (re.token.getType() == -1) {
/* 194 */       String msg = "premature EOF";
/* 195 */       this.group.errMgr.compileTimeError(ErrorType.SYNTAX_ERROR, templateToken, re.token, msg);
/*     */     }
/* 197 */     else if ((re instanceof NoViableAltException)) {
/* 198 */       String msg = "'" + re.token.getText() + "' came as a complete surprise to me";
/* 199 */       this.group.errMgr.compileTimeError(ErrorType.SYNTAX_ERROR, templateToken, re.token, msg);
/*     */     }
/* 201 */     else if (tokens.index() == 0) {
/* 202 */       String msg = "this doesn't look like a template: \"" + tokens + "\"";
/* 203 */       this.group.errMgr.compileTimeError(ErrorType.SYNTAX_ERROR, templateToken, re.token, msg);
/*     */     }
/* 205 */     else if (tokens.LA(1) == 23) {
/* 206 */       String msg = "doesn't look like an expression";
/* 207 */       this.group.errMgr.compileTimeError(ErrorType.SYNTAX_ERROR, templateToken, re.token, msg);
/*     */     }
/*     */     else {
/* 210 */       String msg = parser.getErrorMessage(re, parser.getTokenNames());
/* 211 */       this.group.errMgr.compileTimeError(ErrorType.SYNTAX_ERROR, templateToken, re.token, msg);
/*     */     }
/* 213 */     throw new STException();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.compiler.Compiler
 * JD-Core Version:    0.6.2
 */