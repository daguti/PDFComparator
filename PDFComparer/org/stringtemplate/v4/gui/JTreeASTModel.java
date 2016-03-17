/*    */ package org.stringtemplate.v4.gui;
/*    */ 
/*    */ import javax.swing.event.TreeModelListener;
/*    */ import javax.swing.tree.TreeModel;
/*    */ import javax.swing.tree.TreePath;
/*    */ import org.antlr.runtime.tree.CommonTreeAdaptor;
/*    */ import org.antlr.runtime.tree.TreeAdaptor;
/*    */ 
/*    */ public class JTreeASTModel
/*    */   implements TreeModel
/*    */ {
/*    */   TreeAdaptor adaptor;
/*    */   Object root;
/*    */ 
/*    */   public JTreeASTModel(TreeAdaptor adaptor, Object root)
/*    */   {
/* 44 */     this.adaptor = adaptor;
/* 45 */     this.root = root;
/*    */   }
/*    */ 
/*    */   public JTreeASTModel(Object root) {
/* 49 */     this.adaptor = new CommonTreeAdaptor();
/* 50 */     this.root = root;
/*    */   }
/*    */ 
/*    */   public int getChildCount(Object parent) {
/* 54 */     return this.adaptor.getChildCount(parent);
/*    */   }
/*    */ 
/*    */   public int getIndexOfChild(Object parent, Object child) {
/* 58 */     if (parent == null) return -1;
/* 59 */     return this.adaptor.getChildIndex(child);
/*    */   }
/*    */ 
/*    */   public Object getChild(Object parent, int index) {
/* 63 */     return this.adaptor.getChild(parent, index);
/*    */   }
/*    */ 
/*    */   public boolean isLeaf(Object node) {
/* 67 */     return getChildCount(node) == 0;
/*    */   }
/*    */   public Object getRoot() {
/* 70 */     return this.root;
/*    */   }
/*    */ 
/*    */   public void valueForPathChanged(TreePath treePath, Object o)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void addTreeModelListener(TreeModelListener treeModelListener)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void removeTreeModelListener(TreeModelListener treeModelListener)
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.gui.JTreeASTModel
 * JD-Core Version:    0.6.2
 */