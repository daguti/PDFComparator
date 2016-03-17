/*    */ package org.stringtemplate.v4.misc;
/*    */ 
/*    */ import org.stringtemplate.v4.InstanceScope;
/*    */ import org.stringtemplate.v4.Interpreter;
/*    */ import org.stringtemplate.v4.ST;
/*    */ import org.stringtemplate.v4.compiler.CompiledST;
/*    */ 
/*    */ public class STRuntimeMessage extends STMessage
/*    */ {
/*    */   Interpreter interp;
/* 39 */   public int ip = -1;
/*    */   public InstanceScope scope;
/*    */ 
/*    */   public STRuntimeMessage(Interpreter interp, ErrorType error, int ip)
/*    */   {
/* 44 */     this(interp, error, ip, null);
/*    */   }
/*    */   public STRuntimeMessage(Interpreter interp, ErrorType error, int ip, ST self) {
/* 47 */     this(interp, error, ip, self, null);
/*    */   }
/*    */   public STRuntimeMessage(Interpreter interp, ErrorType error, int ip, ST self, Object arg) {
/* 50 */     this(interp, error, ip, self, null, arg, null);
/*    */   }
/*    */   public STRuntimeMessage(Interpreter interp, ErrorType error, int ip, ST self, Throwable e, Object arg) {
/* 53 */     this(interp, error, ip, self, e, arg, null);
/*    */   }
/*    */   public STRuntimeMessage(Interpreter interp, ErrorType error, int ip, ST self, Throwable e, Object arg, Object arg2) {
/* 56 */     this(interp, error, ip, self, e, arg, arg2, null);
/*    */   }
/*    */   public STRuntimeMessage(Interpreter interp, ErrorType error, int ip, ST self, Throwable e, Object arg, Object arg2, Object arg3) {
/* 59 */     super(error, self, e, arg, arg2, arg3);
/* 60 */     this.interp = interp;
/* 61 */     this.ip = ip;
/* 62 */     if (interp != null) this.scope = interp.currentScope;
/*    */   }
/*    */ 
/*    */   public String getSourceLocation()
/*    */   {
/* 69 */     if ((this.ip < 0) || (this.self.impl == null)) return null;
/* 70 */     Interval I = this.self.impl.sourceMap[this.ip];
/* 71 */     if (I == null) return null;
/*    */ 
/* 73 */     int i = I.a;
/* 74 */     Coordinate loc = Misc.getLineCharPosition(this.self.impl.template, i);
/* 75 */     return loc.toString();
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 80 */     StringBuilder buf = new StringBuilder();
/* 81 */     String loc = getSourceLocation();
/* 82 */     if (this.self != null) {
/* 83 */       buf.append("context [");
/* 84 */       if (this.interp != null) {
/* 85 */         buf.append(Interpreter.getEnclosingInstanceStackString(this.scope));
/*    */       }
/* 87 */       buf.append("]");
/*    */     }
/* 89 */     if (loc != null) buf.append(" " + loc);
/* 90 */     buf.append(" " + super.toString());
/* 91 */     return buf.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.misc.STRuntimeMessage
 * JD-Core Version:    0.6.2
 */