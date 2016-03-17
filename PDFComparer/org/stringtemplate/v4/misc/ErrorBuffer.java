/*    */ package org.stringtemplate.v4.misc;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.stringtemplate.v4.STErrorListener;
/*    */ 
/*    */ public class ErrorBuffer
/*    */   implements STErrorListener
/*    */ {
/* 37 */   public List<STMessage> errors = new ArrayList();
/*    */ 
/*    */   public void compileTimeError(STMessage msg) {
/* 40 */     this.errors.add(msg);
/*    */   }
/*    */ 
/*    */   public void runTimeError(STMessage msg) {
/* 44 */     if (msg.error != ErrorType.NO_SUCH_PROPERTY)
/* 45 */       this.errors.add(msg);
/*    */   }
/*    */ 
/*    */   public void IOError(STMessage msg)
/*    */   {
/* 50 */     this.errors.add(msg);
/*    */   }
/*    */ 
/*    */   public void internalError(STMessage msg) {
/* 54 */     this.errors.add(msg);
/*    */   }
/*    */   public String toString() {
/* 57 */     StringBuilder buf = new StringBuilder();
/* 58 */     for (STMessage m : this.errors) {
/* 59 */       buf.append(m.toString() + Misc.newline);
/*    */     }
/* 61 */     return buf.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.misc.ErrorBuffer
 * JD-Core Version:    0.6.2
 */