/*    */ package org.antlr.gunit.swingui;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.FlowLayout;
/*    */ import javax.swing.BorderFactory;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.JProgressBar;
/*    */ 
/*    */ public class StatusBarController
/*    */   implements IController
/*    */ {
/* 38 */   private final JPanel panel = new JPanel();
/*    */ 
/* 40 */   private final JLabel labelText = new JLabel("Ready");
/* 41 */   private final JLabel labelRuleName = new JLabel("");
/* 42 */   private final JProgressBar progress = new JProgressBar();
/*    */ 
/*    */   public StatusBarController() {
/* 45 */     initComponents();
/*    */   }
/*    */ 
/*    */   private void initComponents() {
/* 49 */     this.labelText.setPreferredSize(new Dimension(300, 20));
/* 50 */     this.labelText.setHorizontalTextPosition(2);
/* 51 */     this.progress.setPreferredSize(new Dimension(100, 15));
/*    */ 
/* 53 */     JLabel labRuleHint = new JLabel("Rule: ");
/*    */ 
/* 55 */     FlowLayout layout = new FlowLayout();
/* 56 */     layout.setAlignment(0);
/* 57 */     this.panel.setLayout(layout);
/* 58 */     this.panel.add(this.labelText);
/* 59 */     this.panel.add(this.progress);
/* 60 */     this.panel.add(labRuleHint);
/* 61 */     this.panel.add(this.labelRuleName);
/* 62 */     this.panel.setOpaque(false);
/* 63 */     this.panel.setBorder(BorderFactory.createEmptyBorder());
/*    */   }
/*    */ 
/*    */   public void setText(String text)
/*    */   {
/* 68 */     this.labelText.setText(text);
/*    */   }
/*    */ 
/*    */   public void setRule(String name) {
/* 72 */     this.labelRuleName.setText(name);
/*    */   }
/*    */ 
/*    */   public Object getModel() {
/* 76 */     throw new UnsupportedOperationException("Not supported yet.");
/*    */   }
/*    */ 
/*    */   public JPanel getView() {
/* 80 */     return this.panel;
/*    */   }
/*    */ 
/*    */   public void setProgressIndetermined(boolean value) {
/* 84 */     this.progress.setIndeterminate(value);
/*    */   }
/*    */ 
/*    */   public void setProgress(int value) {
/* 88 */     this.progress.setIndeterminate(false);
/* 89 */     this.progress.setValue(value);
/*    */   }
/*    */ 
/*    */   public Component getView()
/*    */   {
/* 36 */     return getView();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.swingui.StatusBarController
 * JD-Core Version:    0.6.2
 */