/*     */ package org.antlr.codegen;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import org.antlr.Tool;
/*     */ import org.antlr.stringtemplate.StringTemplate;
/*     */ import org.antlr.tool.Grammar;
/*     */ 
/*     */ public class ActionScriptTarget extends Target
/*     */ {
/*     */   public String getTargetCharLiteralFromANTLRCharLiteral(CodeGenerator generator, String literal)
/*     */   {
/*  40 */     int c = Grammar.getCharValueFromGrammarCharLiteral(literal);
/*  41 */     return String.valueOf(c);
/*     */   }
/*     */ 
/*     */   public String getTokenTypeAsTargetLabel(CodeGenerator generator, int ttype)
/*     */   {
/*  48 */     if ((ttype >= 0) && (ttype <= 3)) {
/*  49 */       return String.valueOf(ttype);
/*     */     }
/*     */ 
/*  52 */     String name = generator.grammar.getTokenDisplayName(ttype);
/*     */ 
/*  55 */     if (name.charAt(0) == '\'') {
/*  56 */       return String.valueOf(ttype);
/*     */     }
/*     */ 
/*  59 */     return name;
/*     */   }
/*     */ 
/*     */   public String encodeIntAsCharEscape(int v)
/*     */   {
/*  79 */     if (v <= 255) {
/*  80 */       return "\\x" + Integer.toHexString(v | 0x100).substring(1, 3);
/*     */     }
/*  82 */     if (v <= 32767) {
/*  83 */       String hex = Integer.toHexString(v | 0x10000).substring(1, 5);
/*  84 */       return "\\u" + hex;
/*     */     }
/*  86 */     if (v > 65535) {
/*  87 */       System.err.println("Warning: character literal out of range for ActionScript target " + v);
/*  88 */       return "";
/*     */     }
/*  90 */     StringBuffer buf = new StringBuffer("\\u80");
/*  91 */     buf.append(Integer.toHexString(v >> 8 | 0x100).substring(1, 3));
/*  92 */     buf.append("\\x");
/*  93 */     buf.append(Integer.toHexString(v & 0xFF | 0x100).substring(1, 3));
/*  94 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public String getTarget64BitStringFromValue(long word)
/*     */   {
/* 107 */     StringBuffer buf = new StringBuffer(22);
/* 108 */     buf.append("0x");
/* 109 */     writeHexWithPadding(buf, Integer.toHexString((int)(word & 0xFFFFFFFF)));
/* 110 */     buf.append(", 0x");
/* 111 */     writeHexWithPadding(buf, Integer.toHexString((int)(word >> 32)));
/*     */ 
/* 113 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   private void writeHexWithPadding(StringBuffer buf, String digits) {
/* 117 */     digits = digits.toUpperCase();
/* 118 */     int padding = 8 - digits.length();
/*     */ 
/* 120 */     for (int i = 1; i <= padding; i++) {
/* 121 */       buf.append('0');
/*     */     }
/* 123 */     buf.append(digits);
/*     */   }
/*     */ 
/*     */   protected StringTemplate chooseWhereCyclicDFAsGo(Tool tool, CodeGenerator generator, Grammar grammar, StringTemplate recognizerST, StringTemplate cyclicDFAST)
/*     */   {
/* 131 */     return recognizerST;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.codegen.ActionScriptTarget
 * JD-Core Version:    0.6.2
 */