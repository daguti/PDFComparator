/*    */ package org.antlr.gunit.swingui.model;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.swing.DefaultListModel;
/*    */ 
/*    */ public class Rule extends DefaultListModel
/*    */ {
/*    */   private String name;
/*    */ 
/*    */   public Rule(String name)
/*    */   {
/* 45 */     this.name = name;
/*    */   }
/*    */   public String getName() {
/* 48 */     return this.name;
/*    */   }
/*    */   public boolean getNotEmpty() {
/* 51 */     return !isEmpty();
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 56 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void addTestCase(TestCase newItem) {
/* 60 */     addElement(newItem);
/*    */   }
/*    */ 
/*    */   public List<TestCase> getTestCases()
/*    */   {
/* 65 */     List result = new ArrayList();
/* 66 */     for (int i = 0; i < size(); i++) {
/* 67 */       result.add((TestCase)getElementAt(i));
/*    */     }
/* 69 */     return result;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.swingui.model.Rule
 * JD-Core Version:    0.6.2
 */