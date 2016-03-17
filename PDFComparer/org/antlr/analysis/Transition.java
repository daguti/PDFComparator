/*    */ package org.antlr.analysis;
/*    */ 
/*    */ public class Transition
/*    */   implements Comparable
/*    */ {
/*    */   public Label label;
/*    */   public State target;
/*    */ 
/*    */   public Transition(Label label, State target)
/*    */   {
/* 45 */     this.label = label;
/* 46 */     this.target = target;
/*    */   }
/*    */ 
/*    */   public Transition(int label, State target) {
/* 50 */     this.label = new Label(label);
/* 51 */     this.target = target;
/*    */   }
/*    */ 
/*    */   public boolean isEpsilon() {
/* 55 */     return this.label.isEpsilon();
/*    */   }
/*    */ 
/*    */   public boolean isAction() {
/* 59 */     return this.label.isAction();
/*    */   }
/*    */ 
/*    */   public boolean isSemanticPredicate() {
/* 63 */     return this.label.isSemanticPredicate();
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 67 */     return this.label.hashCode() + this.target.stateNumber;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object o) {
/* 71 */     Transition other = (Transition)o;
/* 72 */     return (this.label.equals(other.label)) && (this.target.equals(other.target));
/*    */   }
/*    */ 
/*    */   public int compareTo(Object o)
/*    */   {
/* 77 */     Transition other = (Transition)o;
/* 78 */     return this.label.compareTo(other.label);
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 82 */     return this.label + "->" + this.target.stateNumber;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.analysis.Transition
 * JD-Core Version:    0.6.2
 */