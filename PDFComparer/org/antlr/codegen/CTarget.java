/*     */ package org.antlr.codegen;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import org.antlr.Tool;
/*     */ import org.antlr.stringtemplate.StringTemplate;
/*     */ import org.antlr.tool.Grammar;
/*     */ 
/*     */ public class CTarget extends Target
/*     */ {
/*  39 */   ArrayList strings = new ArrayList();
/*     */ 
/*     */   protected void genRecognizerFile(Tool tool, CodeGenerator generator, Grammar grammar, StringTemplate outputFileST)
/*     */     throws IOException
/*     */   {
/*  51 */     outputFileST.setAttribute("literals", this.strings);
/*  52 */     String fileName = generator.getRecognizerFileName(grammar.name, grammar.type);
/*  53 */     generator.write(outputFileST, fileName);
/*     */   }
/*     */ 
/*     */   protected void genRecognizerHeaderFile(Tool tool, CodeGenerator generator, Grammar grammar, StringTemplate headerFileST, String extName)
/*     */     throws IOException
/*     */   {
/*  67 */     String fileName = generator.getRecognizerFileName(grammar.name, grammar.type);
/*  68 */     fileName = fileName.substring(0, fileName.length() - 2) + extName;
/*     */ 
/*  70 */     generator.write(headerFileST, fileName);
/*     */   }
/*     */ 
/*     */   protected StringTemplate chooseWhereCyclicDFAsGo(Tool tool, CodeGenerator generator, Grammar grammar, StringTemplate recognizerST, StringTemplate cyclicDFAST)
/*     */   {
/*  78 */     return recognizerST;
/*     */   }
/*     */ 
/*     */   public boolean isValidActionScope(int grammarType, String scope)
/*     */   {
/*  89 */     switch (grammarType) {
/*     */     case 1:
/*  91 */       if (scope.equals("lexer")) {
/*  92 */         return true;
/*     */       }
/*  94 */       if (scope.equals("header")) {
/*  95 */         return true;
/*     */       }
/*  97 */       if (scope.equals("includes")) {
/*  98 */         return true;
/*     */       }
/* 100 */       if (scope.equals("preincludes")) {
/* 101 */         return true;
/*     */       }
/* 103 */       if (scope.equals("overrides")) {
/* 104 */         return true;
/*     */       }
/*     */       break;
/*     */     case 2:
/* 108 */       if (scope.equals("parser")) {
/* 109 */         return true;
/*     */       }
/* 111 */       if (scope.equals("header")) {
/* 112 */         return true;
/*     */       }
/* 114 */       if (scope.equals("includes")) {
/* 115 */         return true;
/*     */       }
/* 117 */       if (scope.equals("preincludes")) {
/* 118 */         return true;
/*     */       }
/* 120 */       if (scope.equals("overrides")) {
/* 121 */         return true;
/*     */       }
/*     */       break;
/*     */     case 4:
/* 125 */       if (scope.equals("parser")) {
/* 126 */         return true;
/*     */       }
/* 128 */       if (scope.equals("lexer")) {
/* 129 */         return true;
/*     */       }
/* 131 */       if (scope.equals("header")) {
/* 132 */         return true;
/*     */       }
/* 134 */       if (scope.equals("includes")) {
/* 135 */         return true;
/*     */       }
/* 137 */       if (scope.equals("preincludes")) {
/* 138 */         return true;
/*     */       }
/* 140 */       if (scope.equals("overrides")) {
/* 141 */         return true;
/*     */       }
/*     */       break;
/*     */     case 3:
/* 145 */       if (scope.equals("treeparser")) {
/* 146 */         return true;
/*     */       }
/* 148 */       if (scope.equals("header")) {
/* 149 */         return true;
/*     */       }
/* 151 */       if (scope.equals("includes")) {
/* 152 */         return true;
/*     */       }
/* 154 */       if (scope.equals("preincludes")) {
/* 155 */         return true;
/*     */       }
/* 157 */       if (scope.equals("overrides")) {
/* 158 */         return true;
/*     */       }
/*     */       break;
/*     */     }
/* 162 */     return false;
/*     */   }
/*     */ 
/*     */   public String getTargetCharLiteralFromANTLRCharLiteral(CodeGenerator generator, String literal)
/*     */   {
/* 170 */     if (literal.startsWith("'\\u")) {
/* 171 */       literal = "0x" + literal.substring(3, 7);
/*     */     } else {
/* 173 */       int c = literal.charAt(1);
/*     */ 
/* 175 */       if ((c < 32) || (c > 127)) {
/* 176 */         literal = "0x" + Integer.toHexString(c);
/*     */       }
/*     */     }
/*     */ 
/* 180 */     return literal;
/*     */   }
/*     */ 
/*     */   public String getTargetStringLiteralFromANTLRStringLiteral(CodeGenerator generator, String literal)
/*     */   {
/* 197 */     StringBuffer buf = new StringBuffer();
/*     */ 
/* 199 */     buf.append("{ ");
/*     */ 
/* 205 */     for (int i = 1; i < literal.length() - 1; i++) {
/* 206 */       buf.append("0x");
/*     */ 
/* 208 */       if (literal.charAt(i) == '\\') {
/* 209 */         i++;
/*     */ 
/* 211 */         switch (literal.charAt(i)) {
/*     */         case 'U':
/*     */         case 'u':
/* 214 */           buf.append(literal.substring(i + 1, i + 5));
/* 215 */           i += 5;
/* 216 */           break;
/*     */         case 'N':
/*     */         case 'n':
/* 221 */           buf.append("0A");
/* 222 */           break;
/*     */         case 'R':
/*     */         case 'r':
/* 227 */           buf.append("0D");
/* 228 */           break;
/*     */         case 'T':
/*     */         case 't':
/* 233 */           buf.append("09");
/* 234 */           break;
/*     */         case 'B':
/*     */         case 'b':
/* 239 */           buf.append("08");
/* 240 */           break;
/*     */         case 'F':
/*     */         case 'f':
/* 245 */           buf.append("0C");
/* 246 */           break;
/*     */         default:
/* 252 */           buf.append(Integer.toHexString(literal.charAt(i)).toUpperCase());
/* 253 */           break;
/*     */         }
/*     */       } else {
/* 256 */         buf.append(Integer.toHexString(literal.charAt(i)).toUpperCase());
/*     */       }
/* 258 */       buf.append(", ");
/*     */     }
/* 260 */     buf.append(" ANTLR3_STRING_TERMINATOR}");
/*     */ 
/* 262 */     String bytes = buf.toString();
/* 263 */     int index = this.strings.indexOf(bytes);
/*     */ 
/* 265 */     if (index == -1) {
/* 266 */       this.strings.add(bytes);
/* 267 */       index = this.strings.indexOf(bytes);
/*     */     }
/*     */ 
/* 270 */     String strref = "lit_" + String.valueOf(index + 1);
/*     */ 
/* 272 */     return strref;
/*     */   }
/*     */ 
/*     */   protected void performGrammarAnalysis(CodeGenerator generator, Grammar grammar)
/*     */   {
/* 297 */     if (CodeGenerator.MAX_ACYCLIC_DFA_STATES_INLINE == 10)
/*     */     {
/* 299 */       CodeGenerator.MAX_ACYCLIC_DFA_STATES_INLINE = 65535;
/*     */     }
/*     */ 
/* 308 */     if (CodeGenerator.MAX_SWITCH_CASE_LABELS == 300)
/*     */     {
/* 310 */       CodeGenerator.MAX_SWITCH_CASE_LABELS = 3000;
/*     */     }
/*     */ 
/* 318 */     if (CodeGenerator.MIN_SWITCH_ALTS == 3)
/*     */     {
/* 320 */       CodeGenerator.MIN_SWITCH_ALTS = 1;
/*     */     }
/*     */ 
/* 326 */     super.performGrammarAnalysis(generator, grammar);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.codegen.CTarget
 * JD-Core Version:    0.6.2
 */