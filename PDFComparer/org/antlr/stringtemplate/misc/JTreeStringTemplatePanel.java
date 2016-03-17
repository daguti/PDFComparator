/*    */ package org.antlr.stringtemplate.misc;
/*    */ 
/*    */ import java.awt.BorderLayout;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.JScrollPane;
/*    */ import javax.swing.JTree;
/*    */ import javax.swing.JViewport;
/*    */ import javax.swing.event.TreeSelectionListener;
/*    */ import javax.swing.tree.TreeModel;
/*    */ 
/*    */ public class JTreeStringTemplatePanel extends JPanel
/*    */ {
/*    */   JTree tree;
/*    */ 
/*    */   public JTreeStringTemplatePanel(TreeModel tm, TreeSelectionListener listener)
/*    */   {
/* 41 */     setLayout(new BorderLayout());
/*    */ 
/* 44 */     this.tree = new JTree(tm);
/*    */ 
/* 47 */     this.tree.putClientProperty("JTree.lineStyle", "Angled");
/*    */ 
/* 50 */     if (listener != null) {
/* 51 */       this.tree.addTreeSelectionListener(listener);
/*    */     }
/*    */ 
/* 54 */     JScrollPane sp = new JScrollPane();
/* 55 */     sp.getViewport().add(this.tree);
/*    */ 
/* 57 */     add(sp, "Center");
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.stringtemplate.misc.JTreeStringTemplatePanel
 * JD-Core Version:    0.6.2
 */