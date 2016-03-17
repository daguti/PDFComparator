/*    */ package org.apache.pdfbox.pdfviewer;
/*    */ 
/*    */ import java.awt.ComponentOrientation;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.FlowLayout;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JPanel;
/*    */ 
/*    */ public class ReaderBottomPanel extends JPanel
/*    */ {
/* 34 */   private JLabel statusLabel = null;
/*    */ 
/*    */   public ReaderBottomPanel()
/*    */   {
/* 42 */     initialize();
/*    */   }
/*    */ 
/*    */   private void initialize()
/*    */   {
/* 50 */     FlowLayout flowLayout1 = new FlowLayout();
/* 51 */     setLayout(flowLayout1);
/* 52 */     setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
/* 53 */     setPreferredSize(new Dimension(1000, 20));
/* 54 */     flowLayout1.setAlignment(0);
/* 55 */     add(getStatusLabel(), null);
/*    */   }
/*    */ 
/*    */   public JLabel getStatusLabel()
/*    */   {
/* 65 */     if (this.statusLabel == null)
/*    */     {
/* 67 */       this.statusLabel = new JLabel();
/* 68 */       this.statusLabel.setText("Ready");
/*    */     }
/* 70 */     return this.statusLabel;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdfviewer.ReaderBottomPanel
 * JD-Core Version:    0.6.2
 */