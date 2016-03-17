/*    */ package org.antlr.stringtemplate;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
/*    */ 
/*    */ public class NoIndentWriter extends AutoIndentWriter
/*    */ {
/*    */   public NoIndentWriter(Writer out)
/*    */   {
/* 36 */     super(out);
/*    */   }
/*    */ 
/*    */   public int write(String str) throws IOException {
/* 40 */     this.out.write(str);
/* 41 */     return str.length();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.stringtemplate.NoIndentWriter
 * JD-Core Version:    0.6.2
 */