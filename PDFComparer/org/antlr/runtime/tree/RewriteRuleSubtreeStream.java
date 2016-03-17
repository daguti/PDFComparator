/*    */ package org.antlr.runtime.tree;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class RewriteRuleSubtreeStream extends RewriteRuleElementStream
/*    */ {
/*    */   public RewriteRuleSubtreeStream(TreeAdaptor adaptor, String elementDescription)
/*    */   {
/* 35 */     super(adaptor, elementDescription);
/*    */   }
/*    */ 
/*    */   public RewriteRuleSubtreeStream(TreeAdaptor adaptor, String elementDescription, Object oneElement)
/*    */   {
/* 43 */     super(adaptor, elementDescription, oneElement);
/*    */   }
/*    */ 
/*    */   public RewriteRuleSubtreeStream(TreeAdaptor adaptor, String elementDescription, List elements)
/*    */   {
/* 51 */     super(adaptor, elementDescription, elements);
/*    */   }
/*    */ 
/*    */   public Object nextNode()
/*    */   {
/* 69 */     int n = size();
/* 70 */     if ((this.dirty) || ((this.cursor >= n) && (n == 1)))
/*    */     {
/* 73 */       Object el = _next();
/* 74 */       return this.adaptor.dupNode(el);
/*    */     }
/*    */ 
/* 77 */     Object tree = _next();
/*    */ 
/* 79 */     Object el = this.adaptor.dupNode(tree);
/* 80 */     return el;
/*    */   }
/*    */ 
/*    */   protected Object dup(Object el) {
/* 84 */     return this.adaptor.dupTree(el);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.tree.RewriteRuleSubtreeStream
 * JD-Core Version:    0.6.2
 */