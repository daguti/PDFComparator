/*    */ package antlr.debug.misc;
/*    */ 
/*    */ import java.awt.BorderLayout;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.JScrollPane;
/*    */ import javax.swing.JTree;
/*    */ import javax.swing.JViewport;
/*    */ import javax.swing.event.TreeSelectionListener;
/*    */ import javax.swing.tree.TreeModel;
/*    */ 
/*    */ public class JTreeASTPanel extends JPanel
/*    */ {
/*    */   JTree tree;
/*    */ 
/*    */   public JTreeASTPanel(TreeModel paramTreeModel, TreeSelectionListener paramTreeSelectionListener)
/*    */   {
/* 20 */     setLayout(new BorderLayout());
/*    */ 
/* 23 */     this.tree = new JTree(paramTreeModel);
/*    */ 
/* 26 */     this.tree.putClientProperty("JTree.lineStyle", "Angled");
/*    */ 
/* 29 */     if (paramTreeSelectionListener != null) {
/* 30 */       this.tree.addTreeSelectionListener(paramTreeSelectionListener);
/*    */     }
/*    */ 
/* 33 */     JScrollPane localJScrollPane = new JScrollPane();
/* 34 */     localJScrollPane.getViewport().add(this.tree);
/*    */ 
/* 36 */     add(localJScrollPane, "Center");
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.debug.misc.JTreeASTPanel
 * JD-Core Version:    0.6.2
 */