/*    */ package org.antlr.codegen;
/*    */ 
/*    */ public class JavaScriptTarget extends Target
/*    */ {
/*    */   public String encodeIntAsCharEscape(int v)
/*    */   {
/* 40 */     String hex = Integer.toHexString(v | 0x10000).substring(1, 5);
/* 41 */     return "\\u" + hex;
/*    */   }
/*    */ 
/*    */   public String getTarget64BitStringFromValue(long word)
/*    */   {
/* 56 */     StringBuffer buf = new StringBuffer(22);
/* 57 */     buf.append("0x");
/* 58 */     writeHexWithPadding(buf, Integer.toHexString((int)(word & 0xFFFFFFFF)));
/* 59 */     buf.append(", 0x");
/* 60 */     writeHexWithPadding(buf, Integer.toHexString((int)(word >> 32)));
/*    */ 
/* 62 */     return buf.toString();
/*    */   }
/*    */ 
/*    */   private void writeHexWithPadding(StringBuffer buf, String digits) {
/* 66 */     digits = digits.toUpperCase();
/* 67 */     int padding = 8 - digits.length();
/*    */ 
/* 69 */     for (int i = 1; i <= padding; i++) {
/* 70 */       buf.append('0');
/*    */     }
/* 72 */     buf.append(digits);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.codegen.JavaScriptTarget
 * JD-Core Version:    0.6.2
 */