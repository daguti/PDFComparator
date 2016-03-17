/*    */ package org.antlr.tool;
/*    */ 
/*    */ import org.antlr.stringtemplate.StringTemplate;
/*    */ 
/*    */ public class ToolMessage extends Message
/*    */ {
/*    */   public ToolMessage(int msgID)
/*    */   {
/* 45 */     super(msgID, null, null);
/*    */   }
/*    */   public ToolMessage(int msgID, Object arg) {
/* 48 */     super(msgID, arg, null);
/*    */   }
/*    */   public ToolMessage(int msgID, Throwable e) {
/* 51 */     super(msgID);
/* 52 */     this.e = e;
/*    */   }
/*    */   public ToolMessage(int msgID, Object arg, Object arg2) {
/* 55 */     super(msgID, arg, arg2);
/*    */   }
/*    */   public ToolMessage(int msgID, Object arg, Throwable e) {
/* 58 */     super(msgID, arg, null);
/* 59 */     this.e = e;
/*    */   }
/*    */   public String toString() {
/* 62 */     StringTemplate st = getMessageTemplate();
/* 63 */     if (this.arg != null) {
/* 64 */       st.setAttribute("arg", this.arg);
/*    */     }
/* 66 */     if (this.arg2 != null) {
/* 67 */       st.setAttribute("arg2", this.arg2);
/*    */     }
/* 69 */     if (this.e != null) {
/* 70 */       st.setAttribute("exception", this.e);
/* 71 */       st.setAttribute("stackTrace", this.e.getStackTrace());
/*    */     }
/* 73 */     return super.toString(st);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.tool.ToolMessage
 * JD-Core Version:    0.6.2
 */