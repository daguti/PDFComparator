/*    */ package org.antlr.gunit.swingui;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ import javax.swing.SwingUtilities;
/*    */ import javax.swing.UIManager;
/*    */ 
/*    */ public class Tool
/*    */ {
/*    */   public static void main(String[] args)
/*    */     throws IOException
/*    */   {
/* 38 */     if ((args.length == 1) && ("-version".equals(args[0])))
/* 39 */       System.out.println("gUnitEditor Swing GUI\nby Shaoting Cai\n");
/*    */     else
/* 41 */       showUI();
/*    */   }
/*    */ 
/*    */   private static void showUI()
/*    */   {
/*    */     try {
/* 47 */       UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
/*    */     } catch (Exception e) {
/*    */     }
/* 50 */     SwingUtilities.invokeLater(new Runnable()
/*    */     {
/*    */       public void run() {
/* 53 */         WorkSpaceController control = new WorkSpaceController();
/* 54 */         control.show();
/*    */       }
/*    */     });
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.swingui.Tool
 * JD-Core Version:    0.6.2
 */