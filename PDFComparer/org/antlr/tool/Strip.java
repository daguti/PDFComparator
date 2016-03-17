/*     */ package org.antlr.tool;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.List;
/*     */ import org.antlr.grammar.v3.ANTLRv3Lexer;
/*     */ import org.antlr.grammar.v3.ANTLRv3Parser;
/*     */ import org.antlr.grammar.v3.ANTLRv3Parser.grammarDef_return;
/*     */ import org.antlr.runtime.ANTLRFileStream;
/*     */ import org.antlr.runtime.ANTLRInputStream;
/*     */ import org.antlr.runtime.CharStream;
/*     */ import org.antlr.runtime.Token;
/*     */ import org.antlr.runtime.TokenRewriteStream;
/*     */ import org.antlr.runtime.tree.CommonTree;
/*     */ import org.antlr.runtime.tree.Tree;
/*     */ import org.antlr.runtime.tree.TreeAdaptor;
/*     */ import org.antlr.runtime.tree.TreeWizard;
/*     */ import org.antlr.runtime.tree.TreeWizard.Visitor;
/*     */ 
/*     */ public class Strip
/*     */ {
/*     */   protected String filename;
/*     */   protected TokenRewriteStream tokens;
/*  44 */   protected boolean tree_option = false;
/*     */   protected String[] args;
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws Exception
/*     */   {
/*  48 */     Strip s = new Strip(args);
/*  49 */     s.parseAndRewrite();
/*  50 */     System.out.println(s.tokens);
/*     */   }
/*     */   public Strip(String[] args) {
/*  53 */     this.args = args;
/*     */   }
/*  55 */   public TokenRewriteStream getTokenStream() { return this.tokens; }
/*     */ 
/*     */   public void parseAndRewrite() throws Exception {
/*  58 */     processArgs(this.args);
/*  59 */     CharStream input = null;
/*  60 */     if (this.filename != null) input = new ANTLRFileStream(this.filename); else {
/*  61 */       input = new ANTLRInputStream(System.in);
/*     */     }
/*  63 */     ANTLRv3Lexer lex = new ANTLRv3Lexer(input);
/*  64 */     this.tokens = new TokenRewriteStream(lex);
/*  65 */     ANTLRv3Parser g = new ANTLRv3Parser(this.tokens);
/*  66 */     ANTLRv3Parser.grammarDef_return r = g.grammarDef();
/*  67 */     CommonTree t = (CommonTree)r.getTree();
/*  68 */     if (this.tree_option) System.out.println(t.toStringTree());
/*  69 */     rewrite(g.getTreeAdaptor(), t, g.getTokenNames());
/*     */   }
/*     */ 
/*     */   public void rewrite(TreeAdaptor adaptor, CommonTree t, String[] tokenNames) throws Exception {
/*  73 */     TreeWizard wiz = new TreeWizard(adaptor, tokenNames);
/*     */ 
/*  76 */     wiz.visit(t, 47, new TreeWizard.Visitor() {
/*     */       public void visit(Object t) {
/*  78 */         Strip.ACTION(Strip.this.tokens, (CommonTree)t);
/*     */       }
/*     */     });
/*  81 */     wiz.visit(t, 40, new TreeWizard.Visitor()
/*     */     {
/*     */       public void visit(Object t) {
/*  84 */         CommonTree a = (CommonTree)t;
/*  85 */         CommonTree action = null;
/*  86 */         if (a.getChildCount() == 2) action = (CommonTree)a.getChild(1);
/*  87 */         else if (a.getChildCount() == 3) action = (CommonTree)a.getChild(2);
/*  88 */         if (action.getType() == 47) {
/*  89 */           Strip.this.tokens.delete(a.getTokenStartIndex(), a.getTokenStopIndex());
/*     */ 
/*  91 */           Strip.killTrailingNewline(Strip.this.tokens, action.getTokenStopIndex());
/*     */         }
/*     */       }
/*     */     });
/*  95 */     wiz.visit(t, 21, new TreeWizard.Visitor()
/*     */     {
/*     */       public void visit(Object t) {
/*  98 */         CommonTree a = (CommonTree)t;
/*  99 */         a = (CommonTree)a.getChild(0);
/* 100 */         Strip.this.tokens.delete(a.token.getTokenIndex());
/* 101 */         Strip.killTrailingNewline(Strip.this.tokens, a.token.getTokenIndex());
/*     */       }
/*     */     });
/* 104 */     wiz.visit(t, 23, new TreeWizard.Visitor()
/*     */     {
/*     */       public void visit(Object t) {
/* 107 */         CommonTree a = (CommonTree)t;
/* 108 */         CommonTree ret = (CommonTree)a.getChild(0);
/* 109 */         Strip.this.tokens.delete(a.token.getTokenIndex(), ret.token.getTokenIndex());
/*     */       }
/*     */     });
/* 113 */     wiz.visit(t, 31, new TreeWizard.Visitor()
/*     */     {
/*     */       public void visit(Object t) {
/* 116 */         CommonTree a = (CommonTree)t;
/* 117 */         Strip.this.tokens.replace(a.token.getTokenIndex(), "/*" + a.getText() + "*/");
/*     */       }
/*     */     });
/* 120 */     wiz.visit(t, 32, new TreeWizard.Visitor()
/*     */     {
/*     */       public void visit(Object t) {
/* 123 */         CommonTree a = (CommonTree)t;
/* 124 */         String text = Strip.this.tokens.toString(a.getTokenStartIndex(), a.getTokenStopIndex());
/*     */ 
/* 126 */         Strip.this.tokens.replace(a.getTokenStartIndex(), a.getTokenStopIndex(), "/*" + text + "*/");
/*     */       }
/*     */     });
/* 131 */     wiz.visit(t, 30, new TreeWizard.Visitor()
/*     */     {
/*     */       public void visit(Object t) {
/* 134 */         CommonTree a = (CommonTree)t;
/* 135 */         Strip.this.tokens.delete(a.getTokenStartIndex(), a.getTokenStopIndex());
/*     */ 
/* 137 */         Strip.killTrailingNewline(Strip.this.tokens, a.getTokenStopIndex());
/*     */       }
/*     */     });
/* 140 */     wiz.visit(t, 50, new TreeWizard.Visitor()
/*     */     {
/*     */       public void visit(Object t) {
/* 143 */         CommonTree a = (CommonTree)t;
/* 144 */         if (a.getParent().getType() == 51)
/* 145 */           Strip.this.tokens.delete(a.getTokenStartIndex(), a.getTokenStopIndex());
/*     */       }
/*     */     });
/* 150 */     wiz.visit(t, 41, new TreeWizard.Visitor()
/*     */     {
/*     */       public void visit(Object t) {
/* 153 */         CommonTree a = (CommonTree)t;
/* 154 */         if (!a.hasAncestor(48)) {
/* 155 */           CommonTree child = (CommonTree)a.getChild(0);
/* 156 */           Strip.this.tokens.delete(a.token.getTokenIndex());
/* 157 */           Strip.this.tokens.delete(child.token.getTokenIndex());
/*     */         }
/*     */       }
/*     */     });
/* 161 */     wiz.visit(t, 42, new TreeWizard.Visitor()
/*     */     {
/*     */       public void visit(Object t) {
/* 164 */         CommonTree a = (CommonTree)t;
/* 165 */         CommonTree child = (CommonTree)a.getChild(0);
/* 166 */         Strip.this.tokens.delete(a.token.getTokenIndex());
/* 167 */         Strip.this.tokens.delete(child.token.getTokenIndex());
/*     */       }
/*     */     });
/* 173 */     wiz.visit(t, 39, new TreeWizard.Visitor()
/*     */     {
/*     */       public void visit(Object t) {
/* 176 */         CommonTree a = (CommonTree)t;
/* 177 */         CommonTree child = (CommonTree)a.getChild(0);
/* 178 */         int stop = child.getTokenStopIndex();
/* 179 */         if (child.getType() == 31) {
/* 180 */           CommonTree rew = (CommonTree)a.getChild(1);
/* 181 */           stop = rew.getTokenStopIndex();
/*     */         }
/* 183 */         Strip.this.tokens.delete(a.token.getTokenIndex(), stop);
/* 184 */         Strip.killTrailingNewline(Strip.this.tokens, stop);
/*     */       }
/*     */     });
/* 187 */     wiz.visit(t, 37, new TreeWizard.Visitor()
/*     */     {
/*     */       public void visit(Object t) {
/* 190 */         Strip.this.tokens.delete(((CommonTree)t).token.getTokenIndex());
/*     */       }
/*     */     });
/* 193 */     wiz.visit(t, 38, new TreeWizard.Visitor()
/*     */     {
/*     */       public void visit(Object t) {
/* 196 */         Strip.this.tokens.delete(((CommonTree)t).token.getTokenIndex());
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public static void ACTION(TokenRewriteStream tokens, CommonTree t) {
/* 202 */     CommonTree parent = (CommonTree)t.getParent();
/* 203 */     int ptype = parent.getType();
/* 204 */     if ((ptype == 30) || (ptype == 40))
/*     */     {
/* 207 */       return;
/*     */     }
/*     */ 
/* 210 */     CommonTree root = (CommonTree)t.getAncestor(7);
/* 211 */     if (root != null) {
/* 212 */       CommonTree rule = (CommonTree)root.getChild(0);
/*     */ 
/* 214 */       if (!Character.isUpperCase(rule.getText().charAt(0))) {
/* 215 */         tokens.delete(t.getTokenStartIndex(), t.getTokenStopIndex());
/* 216 */         killTrailingNewline(tokens, t.token.getTokenIndex());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void killTrailingNewline(TokenRewriteStream tokens, int index) {
/* 222 */     List all = tokens.getTokens();
/* 223 */     Token tok = (Token)all.get(index);
/* 224 */     Token after = (Token)all.get(index + 1);
/* 225 */     String ws = after.getText();
/* 226 */     if (ws.startsWith("\n"))
/*     */     {
/* 228 */       if (ws.length() > 1) {
/* 229 */         int space = ws.indexOf(' ');
/* 230 */         int tab = ws.indexOf('\t');
/* 231 */         if (((ws.startsWith("\n")) && (space >= 0)) || (tab >= 0))
/*     */         {
/* 234 */           return;
/*     */         }
/*     */ 
/* 237 */         ws = ws.replaceAll("\n", "");
/* 238 */         tokens.replace(after.getTokenIndex(), ws);
/*     */       }
/*     */       else {
/* 241 */         tokens.delete(after.getTokenIndex());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void processArgs(String[] args) {
/* 247 */     if ((args == null) || (args.length == 0)) {
/* 248 */       help();
/* 249 */       return;
/*     */     }
/* 251 */     for (int i = 0; i < args.length; i++)
/* 252 */       if (args[i].equals("-tree")) this.tree_option = true;
/* 254 */       else if (args[i].charAt(0) != '-')
/*     */       {
/* 256 */         this.filename = args[i];
/*     */       }
/*     */   }
/*     */ 
/*     */   private static void help()
/*     */   {
/* 263 */     System.err.println("usage: java org.antlr.tool.Strip [args] file.g");
/* 264 */     System.err.println("  -tree      print out ANTLR grammar AST");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.tool.Strip
 * JD-Core Version:    0.6.2
 */