/*    */ package org.antlr.tool;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import org.antlr.stringtemplate.StringTemplate;
/*    */ 
/*    */ public class LeftRecursionCyclesMessage extends Message
/*    */ {
/*    */   public Collection cycles;
/*    */ 
/*    */   public LeftRecursionCyclesMessage(Collection cycles)
/*    */   {
/* 42 */     super(210);
/* 43 */     this.cycles = cycles;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 47 */     StringTemplate st = getMessageTemplate();
/* 48 */     st.setAttribute("listOfCycles", this.cycles);
/* 49 */     return super.toString(st);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.tool.LeftRecursionCyclesMessage
 * JD-Core Version:    0.6.2
 */