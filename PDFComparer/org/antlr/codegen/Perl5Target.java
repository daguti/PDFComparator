/*    */ package org.antlr.codegen;
/*    */ 
/*    */ import org.antlr.tool.AttributeScope;
/*    */ import org.antlr.tool.Grammar;
/*    */ import org.antlr.tool.RuleLabelScope;
/*    */ 
/*    */ public class Perl5Target extends Target
/*    */ {
/*    */   public Perl5Target()
/*    */   {
/* 37 */     this.targetCharValueEscape[36] = "\\$";
/* 38 */     this.targetCharValueEscape[64] = "\\@";
/* 39 */     this.targetCharValueEscape[37] = "\\%";
/* 40 */     AttributeScope.tokenScope.addAttribute("self", null);
/* 41 */     RuleLabelScope.predefinedLexerRulePropertiesScope.addAttribute("self", null);
/*    */   }
/*    */ 
/*    */   public String getTargetCharLiteralFromANTLRCharLiteral(CodeGenerator generator, String literal)
/*    */   {
/* 46 */     StringBuffer buf = new StringBuffer(10);
/*    */ 
/* 48 */     int c = Grammar.getCharValueFromGrammarCharLiteral(literal);
/* 49 */     if (c < 0) {
/* 50 */       buf.append("\\x{0000}");
/* 51 */     } else if ((c < this.targetCharValueEscape.length) && (this.targetCharValueEscape[c] != null))
/*    */     {
/* 53 */       buf.append(this.targetCharValueEscape[c]);
/* 54 */     } else if ((Character.UnicodeBlock.of((char)c) == Character.UnicodeBlock.BASIC_LATIN) && (!Character.isISOControl((char)c)))
/*    */     {
/* 58 */       buf.append((char)c);
/*    */     }
/*    */     else
/*    */     {
/* 63 */       String hex = Integer.toHexString(c | 0x10000).toUpperCase().substring(1, 5);
/* 64 */       buf.append("\\x{");
/* 65 */       buf.append(hex);
/* 66 */       buf.append("}");
/*    */     }
/*    */ 
/* 69 */     if (buf.indexOf("\\") == -1)
/*    */     {
/* 71 */       buf.insert(0, '\'');
/* 72 */       buf.append('\'');
/*    */     }
/*    */     else {
/* 75 */       buf.insert(0, '"');
/* 76 */       buf.append('"');
/*    */     }
/*    */ 
/* 79 */     return buf.toString();
/*    */   }
/*    */ 
/*    */   public String encodeIntAsCharEscape(int v)
/*    */   {
/*    */     int intValue;
/*    */     int intValue;
/* 84 */     if ((v & 0x8000) == 0)
/* 85 */       intValue = v;
/*    */     else {
/* 87 */       intValue = -(65536 - v);
/*    */     }
/*    */ 
/* 90 */     return String.valueOf(intValue);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.codegen.Perl5Target
 * JD-Core Version:    0.6.2
 */