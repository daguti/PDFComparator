/*    */ package org.antlr.runtime.tree;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.antlr.runtime.Token;
/*    */ 
/*    */ public class RewriteRuleTokenStream extends RewriteRuleElementStream
/*    */ {
/*    */   public RewriteRuleTokenStream(TreeAdaptor adaptor, String elementDescription)
/*    */   {
/* 37 */     super(adaptor, elementDescription);
/*    */   }
/*    */ 
/*    */   public RewriteRuleTokenStream(TreeAdaptor adaptor, String elementDescription, Object oneElement)
/*    */   {
/* 45 */     super(adaptor, elementDescription, oneElement);
/*    */   }
/*    */ 
/*    */   public RewriteRuleTokenStream(TreeAdaptor adaptor, String elementDescription, List elements)
/*    */   {
/* 53 */     super(adaptor, elementDescription, elements);
/*    */   }
/*    */ 
/*    */   public Object nextNode()
/*    */   {
/* 58 */     Token t = (Token)_next();
/* 59 */     return this.adaptor.create(t);
/*    */   }
/*    */ 
/*    */   public Token nextToken() {
/* 63 */     return (Token)_next();
/*    */   }
/*    */ 
/*    */   protected Object toTree(Object el)
/*    */   {
/* 70 */     return el;
/*    */   }
/*    */ 
/*    */   protected Object dup(Object el) {
/* 74 */     throw new UnsupportedOperationException("dup can't be called for a token stream.");
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.tree.RewriteRuleTokenStream
 * JD-Core Version:    0.6.2
 */