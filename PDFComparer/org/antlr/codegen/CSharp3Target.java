/*    */ package org.antlr.codegen;
/*    */ 
/*    */ public class CSharp3Target extends Target
/*    */ {
/*    */   public String encodeIntAsCharEscape(int v)
/*    */   {
/* 38 */     return "\\x" + Integer.toHexString(v).toUpperCase();
/*    */   }
/*    */ 
/*    */   public String getTarget64BitStringFromValue(long word)
/*    */   {
/* 43 */     return "0x" + Long.toHexString(word).toUpperCase();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.codegen.CSharp3Target
 * JD-Core Version:    0.6.2
 */