/*     */ package org.stringtemplate.v4.misc;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import org.antlr.runtime.CharStream;
/*     */ import org.antlr.runtime.RecognitionException;
/*     */ import org.antlr.runtime.Token;
/*     */ import org.stringtemplate.v4.Interpreter;
/*     */ import org.stringtemplate.v4.ST;
/*     */ import org.stringtemplate.v4.STErrorListener;
/*     */ 
/*     */ public class ErrorManager
/*     */ {
/*  37 */   public static STErrorListener DEFAULT_ERROR_LISTENER = new STErrorListener()
/*     */   {
/*     */     public void compileTimeError(STMessage msg) {
/*  40 */       System.err.println(msg);
/*     */     }
/*     */ 
/*     */     public void runTimeError(STMessage msg) {
/*  44 */       if (msg.error != ErrorType.NO_SUCH_PROPERTY)
/*  45 */         System.err.println(msg);
/*     */     }
/*     */ 
/*     */     public void IOError(STMessage msg)
/*     */     {
/*  50 */       System.err.println(msg);
/*     */     }
/*     */ 
/*     */     public void internalError(STMessage msg) {
/*  54 */       System.err.println(msg);
/*     */     }
/*     */ 
/*     */     public void error(String s) {
/*  58 */       error(s, null);
/*     */     }
/*  60 */     public void error(String s, Throwable e) { System.err.println(s);
/*  61 */       if (e != null)
/*  62 */         e.printStackTrace(System.err); }
/*  37 */   };
/*     */   public final STErrorListener listener;
/*     */ 
/*     */   public ErrorManager()
/*     */   {
/*  69 */     this(DEFAULT_ERROR_LISTENER);
/*     */   }
/*  71 */   public ErrorManager(STErrorListener listener) { this.listener = listener; }
/*     */ 
/*     */   public void compileTimeError(ErrorType error, Token templateToken, Token t)
/*     */   {
/*  75 */     String srcName = t.getInputStream().getSourceName();
/*  76 */     if (srcName != null) srcName = Misc.getFileName(srcName);
/*  77 */     this.listener.compileTimeError(new STCompiletimeMessage(error, srcName, templateToken, t, null, t.getText()));
/*     */   }
/*     */ 
/*     */   public void lexerError(String srcName, String msg, Token templateToken, RecognitionException e)
/*     */   {
/*  83 */     if (srcName != null) srcName = Misc.getFileName(srcName);
/*  84 */     this.listener.compileTimeError(new STLexerMessage(srcName, msg, templateToken, e));
/*     */   }
/*     */ 
/*     */   public void compileTimeError(ErrorType error, Token templateToken, Token t, Object arg)
/*     */   {
/*  90 */     String srcName = t.getInputStream().getSourceName();
/*  91 */     if (srcName != null) srcName = Misc.getFileName(srcName);
/*  92 */     this.listener.compileTimeError(new STCompiletimeMessage(error, srcName, templateToken, t, null, arg));
/*     */   }
/*     */ 
/*     */   public void compileTimeError(ErrorType error, Token templateToken, Token t, Object arg, Object arg2)
/*     */   {
/*  98 */     String srcName = t.getInputStream().getSourceName();
/*  99 */     if (srcName != null) srcName = Misc.getFileName(srcName);
/* 100 */     this.listener.compileTimeError(new STCompiletimeMessage(error, srcName, templateToken, t, null, arg, arg2));
/*     */   }
/*     */ 
/*     */   public void groupSyntaxError(ErrorType error, String srcName, RecognitionException e, String msg)
/*     */   {
/* 106 */     Token t = e.token;
/* 107 */     this.listener.compileTimeError(new STGroupCompiletimeMessage(error, srcName, e.token, e, msg));
/*     */   }
/*     */ 
/*     */   public void groupLexerError(ErrorType error, String srcName, RecognitionException e, String msg)
/*     */   {
/* 113 */     this.listener.compileTimeError(new STGroupCompiletimeMessage(error, srcName, e.token, e, msg));
/*     */   }
/*     */ 
/*     */   public void runTimeError(Interpreter interp, ST self, int ip, ErrorType error)
/*     */   {
/* 119 */     this.listener.runTimeError(new STRuntimeMessage(interp, error, ip, self));
/*     */   }
/*     */ 
/*     */   public void runTimeError(Interpreter interp, ST self, int ip, ErrorType error, Object arg) {
/* 123 */     this.listener.runTimeError(new STRuntimeMessage(interp, error, ip, self, arg));
/*     */   }
/*     */ 
/*     */   public void runTimeError(Interpreter interp, ST self, int ip, ErrorType error, Throwable e, Object arg) {
/* 127 */     this.listener.runTimeError(new STRuntimeMessage(interp, error, ip, self, e, arg));
/*     */   }
/*     */ 
/*     */   public void runTimeError(Interpreter interp, ST self, int ip, ErrorType error, Object arg, Object arg2) {
/* 131 */     this.listener.runTimeError(new STRuntimeMessage(interp, error, ip, self, null, arg, arg2));
/*     */   }
/*     */ 
/*     */   public void runTimeError(Interpreter interp, ST self, int ip, ErrorType error, Object arg, Object arg2, Object arg3) {
/* 135 */     this.listener.runTimeError(new STRuntimeMessage(interp, error, ip, self, null, arg, arg2, arg3));
/*     */   }
/*     */ 
/*     */   public void IOError(ST self, ErrorType error, Throwable e) {
/* 139 */     this.listener.IOError(new STMessage(error, self, e));
/*     */   }
/*     */ 
/*     */   public void IOError(ST self, ErrorType error, Throwable e, Object arg) {
/* 143 */     this.listener.IOError(new STMessage(error, self, e, arg));
/*     */   }
/*     */ 
/*     */   public void internalError(ST self, String msg, Throwable e) {
/* 147 */     this.listener.internalError(new STMessage(ErrorType.INTERNAL_ERROR, self, e, msg));
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.misc.ErrorManager
 * JD-Core Version:    0.6.2
 */