/*    */ package org.antlr.runtime.tree;
/*    */ 
/*    */ public class TreeVisitor
/*    */ {
/*    */   protected TreeAdaptor adaptor;
/*    */ 
/*    */   public TreeVisitor(TreeAdaptor adaptor)
/*    */   {
/* 38 */     this.adaptor = adaptor;
/*    */   }
/* 40 */   public TreeVisitor() { this(new CommonTreeAdaptor()); }
/*    */ 
/*    */ 
/*    */   public Object visit(Object t, TreeVisitorAction action)
/*    */   {
/* 54 */     boolean isNil = this.adaptor.isNil(t);
/* 55 */     if ((action != null) && (!isNil)) {
/* 56 */       t = action.pre(t);
/*    */     }
/* 58 */     for (int i = 0; i < this.adaptor.getChildCount(t); i++) {
/* 59 */       Object child = this.adaptor.getChild(t, i);
/* 60 */       Object visitResult = visit(child, action);
/* 61 */       Object childAfterVisit = this.adaptor.getChild(t, i);
/* 62 */       if (visitResult != childAfterVisit) {
/* 63 */         this.adaptor.setChild(t, i, visitResult);
/*    */       }
/*    */     }
/* 66 */     if ((action != null) && (!isNil)) t = action.post(t);
/* 67 */     return t;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.tree.TreeVisitor
 * JD-Core Version:    0.6.2
 */