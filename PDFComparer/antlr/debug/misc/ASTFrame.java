/*    */ package antlr.debug.misc;
/*    */ 
/*    */ import antlr.ASTFactory;
/*    */ import antlr.CommonAST;
/*    */ import antlr.collections.AST;
/*    */ import java.awt.Container;
/*    */ import java.awt.Frame;
/*    */ import java.awt.event.WindowAdapter;
/*    */ import java.awt.event.WindowEvent;
/*    */ import java.io.PrintStream;
/*    */ import javax.swing.JFrame;
/*    */ import javax.swing.event.TreeSelectionEvent;
/*    */ import javax.swing.event.TreeSelectionListener;
/*    */ import javax.swing.tree.TreePath;
/*    */ 
/*    */ public class ASTFrame extends JFrame
/*    */ {
/*    */   static final int WIDTH = 200;
/*    */   static final int HEIGHT = 300;
/*    */ 
/*    */   public ASTFrame(String paramString, AST paramAST)
/*    */   {
/* 39 */     super(paramString);
/*    */ 
/* 42 */     MyTreeSelectionListener localMyTreeSelectionListener = new MyTreeSelectionListener();
/* 43 */     JTreeASTPanel localJTreeASTPanel = new JTreeASTPanel(new JTreeASTModel(paramAST), null);
/* 44 */     Container localContainer = getContentPane();
/* 45 */     localContainer.add(localJTreeASTPanel, "Center");
/* 46 */     addWindowListener(new WindowAdapter() {
/*    */       public void windowClosing(WindowEvent paramAnonymousWindowEvent) {
/* 48 */         Frame localFrame = (Frame)paramAnonymousWindowEvent.getSource();
/* 49 */         localFrame.setVisible(false);
/* 50 */         localFrame.dispose();
/*    */       }
/*    */     });
/* 54 */     setSize(200, 300);
/*    */   }
/*    */ 
/*    */   public static void main(String[] paramArrayOfString)
/*    */   {
/* 59 */     ASTFactory localASTFactory = new ASTFactory();
/* 60 */     CommonAST localCommonAST = (CommonAST)localASTFactory.create(0, "ROOT");
/* 61 */     localCommonAST.addChild((CommonAST)localASTFactory.create(0, "C1"));
/* 62 */     localCommonAST.addChild((CommonAST)localASTFactory.create(0, "C2"));
/* 63 */     localCommonAST.addChild((CommonAST)localASTFactory.create(0, "C3"));
/*    */ 
/* 65 */     ASTFrame localASTFrame = new ASTFrame("AST JTree Example", localCommonAST);
/* 66 */     localASTFrame.setVisible(true);
/*    */   }
/*    */ 
/*    */   class MyTreeSelectionListener
/*    */     implements TreeSelectionListener
/*    */   {
/*    */     MyTreeSelectionListener()
/*    */     {
/*    */     }
/*    */ 
/*    */     public void valueChanged(TreeSelectionEvent paramTreeSelectionEvent)
/*    */     {
/* 27 */       TreePath localTreePath = paramTreeSelectionEvent.getPath();
/* 28 */       System.out.println("Selected: " + localTreePath.getLastPathComponent());
/*    */ 
/* 30 */       Object[] arrayOfObject = localTreePath.getPath();
/* 31 */       for (int i = 0; i < arrayOfObject.length; i++) {
/* 32 */         System.out.print("->" + arrayOfObject[i]);
/*    */       }
/* 34 */       System.out.println();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.debug.misc.ASTFrame
 * JD-Core Version:    0.6.2
 */