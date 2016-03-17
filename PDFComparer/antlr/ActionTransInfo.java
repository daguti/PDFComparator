/*    */ package antlr;
/*    */ 
/*    */ public class ActionTransInfo
/*    */ {
/* 15 */   public boolean assignToRoot = false;
/* 16 */   public String refRuleRoot = null;
/* 17 */   public String followSetName = null;
/*    */ 
/*    */   public String toString() {
/* 20 */     return "assignToRoot:" + this.assignToRoot + ", refRuleRoot:" + this.refRuleRoot + ", FOLLOW Set:" + this.followSetName;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.ActionTransInfo
 * JD-Core Version:    0.6.2
 */