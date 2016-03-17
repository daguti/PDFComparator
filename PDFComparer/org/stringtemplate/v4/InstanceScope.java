/*    */ package org.stringtemplate.v4;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.stringtemplate.v4.debug.EvalTemplateEvent;
/*    */ import org.stringtemplate.v4.debug.InterpEvent;
/*    */ 
/*    */ public class InstanceScope
/*    */ {
/*    */   public InstanceScope parent;
/*    */   public ST st;
/*    */   public int ret_ip;
/* 55 */   public List<InterpEvent> events = new ArrayList();
/*    */ 
/* 60 */   public List<EvalTemplateEvent> childEvalTemplateEvents = new ArrayList();
/*    */ 
/*    */   public InstanceScope(InstanceScope parent, ST st)
/*    */   {
/* 64 */     this.parent = parent;
/* 65 */     this.st = st;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.InstanceScope
 * JD-Core Version:    0.6.2
 */