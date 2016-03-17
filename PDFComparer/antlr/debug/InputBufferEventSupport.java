/*    */ package antlr.debug;
/*    */ 
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class InputBufferEventSupport
/*    */ {
/*    */   private Object source;
/*    */   private Vector inputBufferListeners;
/*    */   private InputBufferEvent inputBufferEvent;
/*    */   protected static final int CONSUME = 0;
/*    */   protected static final int LA = 1;
/*    */   protected static final int MARK = 2;
/*    */   protected static final int REWIND = 3;
/*    */ 
/*    */   public InputBufferEventSupport(Object paramObject)
/*    */   {
/* 18 */     this.inputBufferEvent = new InputBufferEvent(paramObject);
/* 19 */     this.source = paramObject;
/*    */   }
/*    */   public void addInputBufferListener(InputBufferListener paramInputBufferListener) {
/* 22 */     if (this.inputBufferListeners == null) this.inputBufferListeners = new Vector();
/* 23 */     this.inputBufferListeners.addElement(paramInputBufferListener);
/*    */   }
/*    */   public void fireConsume(char paramChar) {
/* 26 */     this.inputBufferEvent.setValues(0, paramChar, 0);
/* 27 */     fireEvents(0, this.inputBufferListeners);
/*    */   }
/*    */   public void fireEvent(int paramInt, ListenerBase paramListenerBase) {
/* 30 */     switch (paramInt) { case 0:
/* 31 */       ((InputBufferListener)paramListenerBase).inputBufferConsume(this.inputBufferEvent); break;
/*    */     case 1:
/* 32 */       ((InputBufferListener)paramListenerBase).inputBufferLA(this.inputBufferEvent); break;
/*    */     case 2:
/* 33 */       ((InputBufferListener)paramListenerBase).inputBufferMark(this.inputBufferEvent); break;
/*    */     case 3:
/* 34 */       ((InputBufferListener)paramListenerBase).inputBufferRewind(this.inputBufferEvent); break;
/*    */     default:
/* 36 */       throw new IllegalArgumentException("bad type " + paramInt + " for fireEvent()"); }
/*    */   }
/*    */ 
/*    */   public void fireEvents(int paramInt, Vector paramVector) {
/* 40 */     Vector localVector = null;
/* 41 */     ListenerBase localListenerBase = null;
/*    */ 
/* 43 */     synchronized (this) {
/* 44 */       if (paramVector == null) return;
/* 45 */       localVector = (Vector)paramVector.clone();
/*    */     }
/*    */ 
/* 48 */     if (localVector != null)
/* 49 */       for (int i = 0; i < localVector.size(); i++) {
/* 50 */         localListenerBase = (ListenerBase)localVector.elementAt(i);
/* 51 */         fireEvent(paramInt, localListenerBase);
/*    */       }
/*    */   }
/*    */ 
/* 55 */   public void fireLA(char paramChar, int paramInt) { this.inputBufferEvent.setValues(1, paramChar, paramInt);
/* 56 */     fireEvents(1, this.inputBufferListeners); }
/*    */ 
/*    */   public void fireMark(int paramInt) {
/* 59 */     this.inputBufferEvent.setValues(2, ' ', paramInt);
/* 60 */     fireEvents(2, this.inputBufferListeners);
/*    */   }
/*    */   public void fireRewind(int paramInt) {
/* 63 */     this.inputBufferEvent.setValues(3, ' ', paramInt);
/* 64 */     fireEvents(3, this.inputBufferListeners);
/*    */   }
/*    */   public Vector getInputBufferListeners() {
/* 67 */     return this.inputBufferListeners;
/*    */   }
/*    */ 
/*    */   protected void refresh(Vector paramVector)
/*    */   {
/*    */     Vector localVector;
/* 71 */     synchronized (paramVector) {
/* 72 */       localVector = (Vector)paramVector.clone();
/*    */     }
/* 74 */     if (localVector != null)
/* 75 */       for (int i = 0; i < localVector.size(); i++)
/* 76 */         ((ListenerBase)localVector.elementAt(i)).refresh(); 
/*    */   }
/*    */ 
/* 79 */   public void refreshListeners() { refresh(this.inputBufferListeners); }
/*    */ 
/*    */   public void removeInputBufferListener(InputBufferListener paramInputBufferListener) {
/* 82 */     if (this.inputBufferListeners != null)
/* 83 */       this.inputBufferListeners.removeElement(paramInputBufferListener);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.debug.InputBufferEventSupport
 * JD-Core Version:    0.6.2
 */