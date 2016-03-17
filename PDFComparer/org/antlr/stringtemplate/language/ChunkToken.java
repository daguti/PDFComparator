/*    */ package org.antlr.stringtemplate.language;
/*    */ 
/*    */ import antlr.CommonToken;
/*    */ 
/*    */ public class ChunkToken extends CommonToken
/*    */ {
/*    */   protected String indentation;
/*    */ 
/*    */   public ChunkToken()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ChunkToken(int type, String text, String indentation)
/*    */   {
/* 45 */     super(type, text);
/* 46 */     setIndentation(indentation);
/*    */   }
/*    */ 
/*    */   public String getIndentation() {
/* 50 */     return this.indentation;
/*    */   }
/*    */ 
/*    */   public void setIndentation(String indentation) {
/* 54 */     this.indentation = indentation;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 58 */     return super.toString() + "<indent='" + this.indentation + "'>";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.stringtemplate.language.ChunkToken
 * JD-Core Version:    0.6.2
 */