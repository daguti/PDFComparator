/*    */ package org.antlr.stringtemplate.language;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.antlr.stringtemplate.StringTemplate;
/*    */ import org.antlr.stringtemplate.StringTemplateWriter;
/*    */ 
/*    */ public class StringRef extends Expr
/*    */ {
/*    */   String str;
/*    */ 
/*    */   public StringRef(StringTemplate enclosingTemplate, String str)
/*    */   {
/* 40 */     super(enclosingTemplate);
/* 41 */     this.str = str;
/*    */   }
/*    */ 
/*    */   public int write(StringTemplate self, StringTemplateWriter out)
/*    */     throws IOException
/*    */   {
/* 50 */     if (this.str != null) {
/* 51 */       int n = out.write(this.str);
/* 52 */       return n;
/*    */     }
/* 54 */     return 0;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 58 */     if (this.str != null) {
/* 59 */       return this.str;
/*    */     }
/* 61 */     return "";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.stringtemplate.language.StringRef
 * JD-Core Version:    0.6.2
 */