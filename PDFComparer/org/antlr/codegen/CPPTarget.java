/*     */ package org.antlr.codegen;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.antlr.Tool;
/*     */ import org.antlr.stringtemplate.StringTemplate;
/*     */ import org.antlr.stringtemplate.StringTemplateGroup;
/*     */ import org.antlr.tool.Grammar;
/*     */ 
/*     */ public class CPPTarget extends Target
/*     */ {
/*     */   public String escapeChar(int c)
/*     */   {
/*  41 */     switch (c) { case 10:
/*  42 */       return "\\n";
/*     */     case 9:
/*  43 */       return "\\t";
/*     */     case 13:
/*  44 */       return "\\r";
/*     */     case 92:
/*  45 */       return "\\\\";
/*     */     case 39:
/*  46 */       return "\\'";
/*     */     case 34:
/*  47 */       return "\\\"";
/*     */     }
/*  49 */     if ((c < 32) || (c > 126))
/*     */     {
/*  51 */       if (c > 255)
/*     */       {
/*  53 */         String s = Integer.toString(c, 16);
/*     */ 
/*  55 */         while (s.length() < 4)
/*  56 */           s = '0' + s;
/*  57 */         return "\\u" + s;
/*     */       }
/*     */ 
/*  60 */       return "\\" + Integer.toString(c, 8);
/*     */     }
/*     */ 
/*  64 */     return String.valueOf((char)c);
/*     */   }
/*     */ 
/*     */   public String escapeString(String s)
/*     */   {
/*  78 */     StringBuffer retval = new StringBuffer();
/*  79 */     for (int i = 0; i < s.length(); i++) {
/*  80 */       retval.append(escapeChar(s.charAt(i)));
/*     */     }
/*     */ 
/*  83 */     return retval.toString();
/*     */   }
/*     */ 
/*     */   protected void genRecognizerHeaderFile(Tool tool, CodeGenerator generator, Grammar grammar, StringTemplate headerFileST, String extName)
/*     */     throws IOException
/*     */   {
/*  93 */     StringTemplateGroup templates = generator.getTemplates();
/*  94 */     generator.write(headerFileST, grammar.name + extName);
/*     */   }
/*     */ 
/*     */   public String getTargetCharLiteralFromANTLRCharLiteral(CodeGenerator codegen, String literal)
/*     */   {
/* 105 */     int c = Grammar.getCharValueFromGrammarCharLiteral(literal);
/* 106 */     String prefix = "'";
/* 107 */     if (codegen.grammar.getMaxCharValue() > 255)
/* 108 */       prefix = "L'";
/* 109 */     else if ((c & 0x80) != 0)
/* 110 */       return "" + c;
/* 111 */     return prefix + escapeChar(c) + "'";
/*     */   }
/*     */ 
/*     */   public String getTargetStringLiteralFromANTLRStringLiteral(CodeGenerator codegen, String literal)
/*     */   {
/* 122 */     StringBuffer buf = Grammar.getUnescapedStringFromGrammarStringLiteral(literal);
/* 123 */     String prefix = "\"";
/* 124 */     if (codegen.grammar.getMaxCharValue() > 255)
/* 125 */       prefix = "L\"";
/* 126 */     return prefix + escapeString(buf.toString()) + "\"";
/*     */   }
/*     */ 
/*     */   public int getMaxCharValue(CodeGenerator codegen)
/*     */   {
/* 134 */     int maxval = 255;
/* 135 */     if (maxval <= 255) {
/* 136 */       return 255;
/*     */     }
/* 138 */     return maxval;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.codegen.CPPTarget
 * JD-Core Version:    0.6.2
 */