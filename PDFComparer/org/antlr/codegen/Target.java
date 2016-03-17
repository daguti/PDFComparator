/*     */ package org.antlr.codegen;
/*     */ 
/*     */ import antlr.Token;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import org.antlr.Tool;
/*     */ import org.antlr.stringtemplate.StringTemplate;
/*     */ import org.antlr.tool.Grammar;
/*     */ 
/*     */ public class Target
/*     */ {
/*  72 */   protected String[] targetCharValueEscape = new String['ÿ'];
/*     */ 
/*     */   public Target() {
/*  75 */     this.targetCharValueEscape[10] = "\\n";
/*  76 */     this.targetCharValueEscape[13] = "\\r";
/*  77 */     this.targetCharValueEscape[9] = "\\t";
/*  78 */     this.targetCharValueEscape[8] = "\\b";
/*  79 */     this.targetCharValueEscape[12] = "\\f";
/*  80 */     this.targetCharValueEscape[92] = "\\\\";
/*  81 */     this.targetCharValueEscape[39] = "\\'";
/*  82 */     this.targetCharValueEscape[34] = "\\\"";
/*     */   }
/*     */ 
/*     */   protected void genRecognizerFile(Tool tool, CodeGenerator generator, Grammar grammar, StringTemplate outputFileST)
/*     */     throws IOException
/*     */   {
/*  91 */     String fileName = generator.getRecognizerFileName(grammar.name, grammar.type);
/*     */ 
/*  93 */     generator.write(outputFileST, fileName);
/*     */   }
/*     */ 
/*     */   protected void genRecognizerHeaderFile(Tool tool, CodeGenerator generator, Grammar grammar, StringTemplate headerFileST, String extName)
/*     */     throws IOException
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void performGrammarAnalysis(CodeGenerator generator, Grammar grammar)
/*     */   {
/* 110 */     grammar.buildNFA();
/*     */ 
/* 113 */     grammar.createLookaheadDFAs();
/*     */   }
/*     */ 
/*     */   public boolean isValidActionScope(int grammarType, String scope)
/*     */   {
/* 123 */     switch (grammarType) {
/*     */     case 1:
/* 125 */       if (scope.equals("lexer")) return true;
/*     */       break;
/*     */     case 2:
/* 128 */       if (scope.equals("parser")) return true;
/*     */       break;
/*     */     case 4:
/* 131 */       if (scope.equals("parser")) return true;
/* 132 */       if (scope.equals("lexer")) return true;
/*     */       break;
/*     */     case 3:
/* 135 */       if (scope.equals("treeparser")) return true;
/*     */       break;
/*     */     }
/* 138 */     return false;
/*     */   }
/*     */ 
/*     */   public String getTokenTypeAsTargetLabel(CodeGenerator generator, int ttype)
/*     */   {
/* 143 */     String name = generator.grammar.getTokenDisplayName(ttype);
/*     */ 
/* 145 */     if (name.charAt(0) == '\'') {
/* 146 */       return String.valueOf(ttype);
/*     */     }
/* 148 */     return name;
/*     */   }
/*     */ 
/*     */   public String getTargetCharLiteralFromANTLRCharLiteral(CodeGenerator generator, String literal)
/*     */   {
/* 164 */     StringBuffer buf = new StringBuffer();
/* 165 */     buf.append('\'');
/* 166 */     int c = Grammar.getCharValueFromGrammarCharLiteral(literal);
/* 167 */     if (c < 0) {
/* 168 */       return "";
/*     */     }
/* 170 */     if ((c < this.targetCharValueEscape.length) && (this.targetCharValueEscape[c] != null))
/*     */     {
/* 173 */       buf.append(this.targetCharValueEscape[c]);
/*     */     }
/* 175 */     else if ((Character.UnicodeBlock.of((char)c) == Character.UnicodeBlock.BASIC_LATIN) && (!Character.isISOControl((char)c)))
/*     */     {
/* 180 */       buf.append((char)c);
/*     */     }
/*     */     else
/*     */     {
/* 186 */       String hex = Integer.toHexString(c | 0x10000).toUpperCase().substring(1, 5);
/* 187 */       buf.append("\\u");
/* 188 */       buf.append(hex);
/*     */     }
/*     */ 
/* 191 */     buf.append('\'');
/* 192 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public String getTargetStringLiteralFromANTLRStringLiteral(CodeGenerator generator, String literal)
/*     */   {
/* 210 */     StringBuilder sb = new StringBuilder();
/* 211 */     StringBuffer is = new StringBuffer(literal);
/*     */ 
/* 215 */     sb.append('"');
/*     */ 
/* 217 */     for (int i = 1; i < is.length() - 1; i++) {
/* 218 */       if (is.charAt(i) == '\\')
/*     */       {
/* 225 */         switch (is.charAt(i + 1))
/*     */         {
/*     */         case '"':
/*     */         case '\\':
/*     */         case 'b':
/*     */         case 'f':
/*     */         case 'n':
/*     */         case 'r':
/*     */         case 't':
/*     */         case 'u':
/* 236 */           sb.append('\\');
/* 237 */           break;
/*     */         }
/*     */ 
/* 247 */         i++;
/*     */       }
/* 251 */       else if (is.charAt(i) == '"')
/*     */       {
/* 254 */         sb.append('\\');
/*     */       }
/*     */ 
/* 259 */       sb.append(is.charAt(i));
/*     */     }
/*     */ 
/* 264 */     sb.append('"');
/*     */ 
/* 266 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String getTargetStringLiteralFromString(String s, boolean quoted)
/*     */   {
/* 289 */     if (s == null) {
/* 290 */       return null;
/*     */     }
/*     */ 
/* 293 */     StringBuffer buf = new StringBuffer();
/* 294 */     if (quoted) {
/* 295 */       buf.append('"');
/*     */     }
/* 297 */     for (int i = 0; i < s.length(); i++) {
/* 298 */       int c = s.charAt(i);
/* 299 */       if ((c != 39) && (c < this.targetCharValueEscape.length) && (this.targetCharValueEscape[c] != null))
/*     */       {
/* 303 */         buf.append(this.targetCharValueEscape[c]);
/*     */       }
/*     */       else {
/* 306 */         buf.append((char)c);
/*     */       }
/*     */     }
/* 309 */     if (quoted) {
/* 310 */       buf.append('"');
/*     */     }
/* 312 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public String getTargetStringLiteralFromString(String s) {
/* 316 */     return getTargetStringLiteralFromString(s, false);
/*     */   }
/*     */ 
/*     */   public String getTarget64BitStringFromValue(long word)
/*     */   {
/* 323 */     int numHexDigits = 16;
/* 324 */     StringBuffer buf = new StringBuffer(numHexDigits + 2);
/* 325 */     buf.append("0x");
/* 326 */     String digits = Long.toHexString(word);
/* 327 */     digits = digits.toUpperCase();
/* 328 */     int padding = numHexDigits - digits.length();
/*     */ 
/* 330 */     for (int i = 1; i <= padding; i++) {
/* 331 */       buf.append('0');
/*     */     }
/* 333 */     buf.append(digits);
/* 334 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public String encodeIntAsCharEscape(int v) {
/* 338 */     if (v <= 127) {
/* 339 */       return "\\" + Integer.toOctalString(v);
/*     */     }
/* 341 */     String hex = Integer.toHexString(v | 0x10000).substring(1, 5);
/* 342 */     return "\\u" + hex;
/*     */   }
/*     */ 
/*     */   public int getMaxCharValue(CodeGenerator generator)
/*     */   {
/* 349 */     return 65535;
/*     */   }
/*     */ 
/*     */   public List postProcessAction(List chunks, Token actionToken)
/*     */   {
/* 356 */     return chunks;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.codegen.Target
 * JD-Core Version:    0.6.2
 */