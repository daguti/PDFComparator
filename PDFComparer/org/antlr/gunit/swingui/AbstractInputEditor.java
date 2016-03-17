/*    */ package org.antlr.gunit.swingui;
/*    */ 
/*    */ import java.awt.event.ActionListener;
/*    */ import javax.swing.JComponent;
/*    */ import org.antlr.gunit.swingui.model.ITestCaseInput;
/*    */ 
/*    */ public abstract class AbstractInputEditor
/*    */ {
/*    */   protected ITestCaseInput input;
/*    */   protected JComponent comp;
/*    */ 
/*    */   public void setInput(ITestCaseInput input)
/*    */   {
/* 43 */     this.input = input;
/*    */   }
/*    */ 
/*    */   public JComponent getControl() {
/* 47 */     return this.comp;
/*    */   }
/*    */ 
/*    */   public abstract void addActionListener(ActionListener paramActionListener);
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.swingui.AbstractInputEditor
 * JD-Core Version:    0.6.2
 */