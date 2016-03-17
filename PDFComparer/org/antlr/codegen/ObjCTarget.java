/*     */ package org.antlr.codegen;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.antlr.Tool;
/*     */ import org.antlr.misc.Utils;
/*     */ import org.antlr.stringtemplate.StringTemplate;
/*     */ import org.antlr.tool.Grammar;
/*     */ 
/*     */ public class ObjCTarget extends Target
/*     */ {
/*     */   protected void genRecognizerHeaderFile(Tool tool, CodeGenerator generator, Grammar grammar, StringTemplate headerFileST, String extName)
/*     */     throws IOException
/*     */   {
/*  46 */     generator.write(headerFileST, grammar.name + Grammar.grammarTypeToFileNameSuffix[grammar.type] + extName);
/*     */   }
/*     */ 
/*     */   public String getTargetCharLiteralFromANTLRCharLiteral(CodeGenerator generator, String literal)
/*     */   {
/*  52 */     if (literal.startsWith("'\\u")) {
/*  53 */       literal = "0x" + literal.substring(3, 7);
/*     */     } else {
/*  55 */       int c = literal.charAt(1);
/*  56 */       if ((c < 32) || (c > 127)) {
/*  57 */         literal = "0x" + Integer.toHexString(c);
/*     */       }
/*     */     }
/*     */ 
/*  61 */     return literal;
/*     */   }
/*     */ 
/*     */   public String getTargetStringLiteralFromANTLRStringLiteral(CodeGenerator generator, String literal)
/*     */   {
/*  73 */     literal = Utils.replace(literal, "\"", "\\\"");
/*  74 */     StringBuffer buf = new StringBuffer(literal);
/*  75 */     buf.setCharAt(0, '"');
/*  76 */     buf.setCharAt(literal.length() - 1, '"');
/*  77 */     buf.insert(0, '@');
/*  78 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public String getTokenTypeAsTargetLabel(CodeGenerator generator, int ttype)
/*     */   {
/*  83 */     String name = generator.grammar.getTokenDisplayName(ttype);
/*     */ 
/*  85 */     if (name.charAt(0) == '\'') {
/*  86 */       return String.valueOf(ttype);
/*     */     }
/*  88 */     return name;
/*     */   }
/*     */ 
/*     */   public String getTokenTextAndTypeAsTargetLabel(CodeGenerator generator, String text, int tokenType)
/*     */   {
/*  96 */     String name = generator.grammar.getTokenDisplayName(tokenType);
/*     */ 
/*  98 */     if (name.charAt(0) == '\'') {
/*  99 */       return String.valueOf(tokenType);
/*     */     }
/* 101 */     String textEquivalent = text == null ? name : text;
/* 102 */     if ((textEquivalent.charAt(0) >= '0') && (textEquivalent.charAt(0) <= '9')) {
/* 103 */       return textEquivalent;
/*     */     }
/* 105 */     return generator.grammar.name + Grammar.grammarTypeToFileNameSuffix[generator.grammar.type] + "_" + textEquivalent;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.codegen.ObjCTarget
 * JD-Core Version:    0.6.2
 */