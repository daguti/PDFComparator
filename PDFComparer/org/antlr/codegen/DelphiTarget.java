/*     */ package org.antlr.codegen;
/*     */ 
/*     */ import org.antlr.Tool;
/*     */ import org.antlr.misc.Utils;
/*     */ import org.antlr.stringtemplate.StringTemplate;
/*     */ import org.antlr.tool.Grammar;
/*     */ 
/*     */ public class DelphiTarget extends Target
/*     */ {
/*     */   public DelphiTarget()
/*     */   {
/*  39 */     this.targetCharValueEscape[10] = "'#10'";
/*  40 */     this.targetCharValueEscape[13] = "'#13'";
/*  41 */     this.targetCharValueEscape[9] = "'#9'";
/*  42 */     this.targetCharValueEscape[8] = "\\b";
/*  43 */     this.targetCharValueEscape[12] = "\\f";
/*  44 */     this.targetCharValueEscape[92] = "\\";
/*  45 */     this.targetCharValueEscape[39] = "''";
/*  46 */     this.targetCharValueEscape[34] = "'";
/*     */   }
/*     */ 
/*     */   protected StringTemplate chooseWhereCyclicDFAsGo(Tool tool, CodeGenerator generator, Grammar grammar, StringTemplate recognizerST, StringTemplate cyclicDFAST)
/*     */   {
/*  55 */     return recognizerST;
/*     */   }
/*     */ 
/*     */   public String encodeIntAsCharEscape(int v)
/*     */   {
/*  60 */     if (v <= 127)
/*     */     {
/*  62 */       String hex1 = Integer.toHexString(v | 0x10000).substring(3, 5);
/*  63 */       return "'#$" + hex1 + "'";
/*     */     }
/*  65 */     String hex = Integer.toHexString(v | 0x10000).substring(1, 5);
/*  66 */     return "'#$" + hex + "'";
/*     */   }
/*     */ 
/*     */   public String getTargetCharLiteralFromANTLRCharLiteral(CodeGenerator generator, String literal)
/*     */   {
/*  73 */     StringBuffer buf = new StringBuffer();
/*  74 */     int c = Grammar.getCharValueFromGrammarCharLiteral(literal);
/*  75 */     if (c < 0) {
/*  76 */       return "0";
/*     */     }
/*     */ 
/*  79 */     buf.append(c);
/*     */ 
/*  81 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public String getTargetStringLiteralFromString(String s, boolean quoted) {
/*  85 */     if (s == null) {
/*  86 */       return null;
/*     */     }
/*  88 */     StringBuffer buf = new StringBuffer();
/*  89 */     if (quoted) {
/*  90 */       buf.append('\'');
/*     */     }
/*  92 */     for (int i = 0; i < s.length(); i++) {
/*  93 */       int c = s.charAt(i);
/*  94 */       if ((c != 34) && (c < this.targetCharValueEscape.length) && (this.targetCharValueEscape[c] != null))
/*     */       {
/*  98 */         buf.append(this.targetCharValueEscape[c]);
/*     */       }
/*     */       else {
/* 101 */         buf.append((char)c);
/*     */       }
/* 103 */       if ((i & 0x7F) == 127)
/*     */       {
/* 107 */         buf.append("' + \r\n  '");
/*     */       }
/*     */     }
/* 110 */     if (quoted) {
/* 111 */       buf.append('\'');
/*     */     }
/* 113 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public String getTargetStringLiteralFromANTLRStringLiteral(CodeGenerator generator, String literal)
/*     */   {
/* 120 */     literal = Utils.replace(literal, "\\'", "''");
/* 121 */     literal = Utils.replace(literal, "\\r\\n", "'#13#10'");
/* 122 */     literal = Utils.replace(literal, "\\r", "'#13'");
/* 123 */     literal = Utils.replace(literal, "\\n", "'#10'");
/* 124 */     StringBuffer buf = new StringBuffer(literal);
/* 125 */     buf.setCharAt(0, '\'');
/* 126 */     buf.setCharAt(literal.length() - 1, '\'');
/* 127 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public String getTarget64BitStringFromValue(long word) {
/* 131 */     int numHexDigits = 16;
/* 132 */     StringBuffer buf = new StringBuffer(numHexDigits + 2);
/* 133 */     buf.append("$");
/* 134 */     String digits = Long.toHexString(word);
/* 135 */     digits = digits.toUpperCase();
/* 136 */     int padding = numHexDigits - digits.length();
/*     */ 
/* 138 */     for (int i = 1; i <= padding; i++) {
/* 139 */       buf.append('0');
/*     */     }
/* 141 */     buf.append(digits);
/* 142 */     return buf.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.codegen.DelphiTarget
 * JD-Core Version:    0.6.2
 */