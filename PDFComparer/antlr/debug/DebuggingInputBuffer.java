/*    */ package antlr.debug;
/*    */ 
/*    */ import antlr.CharStreamException;
/*    */ import antlr.InputBuffer;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class DebuggingInputBuffer extends InputBuffer
/*    */ {
/*    */   private InputBuffer buffer;
/*    */   private InputBufferEventSupport inputBufferEventSupport;
/* 11 */   private boolean debugMode = true;
/*    */ 
/*    */   public DebuggingInputBuffer(InputBuffer paramInputBuffer)
/*    */   {
/* 15 */     this.buffer = paramInputBuffer;
/* 16 */     this.inputBufferEventSupport = new InputBufferEventSupport(this);
/*    */   }
/*    */   public void addInputBufferListener(InputBufferListener paramInputBufferListener) {
/* 19 */     this.inputBufferEventSupport.addInputBufferListener(paramInputBufferListener);
/*    */   }
/*    */   public void consume() {
/* 22 */     char c = ' ';
/*    */     try { c = this.buffer.LA(1); } catch (CharStreamException localCharStreamException) {
/*    */     }
/* 25 */     this.buffer.consume();
/* 26 */     if (this.debugMode)
/* 27 */       this.inputBufferEventSupport.fireConsume(c); 
/*    */   }
/*    */ 
/* 30 */   public void fill(int paramInt) throws CharStreamException { this.buffer.fill(paramInt); }
/*    */ 
/*    */   public Vector getInputBufferListeners() {
/* 33 */     return this.inputBufferEventSupport.getInputBufferListeners();
/*    */   }
/*    */   public boolean isDebugMode() {
/* 36 */     return this.debugMode;
/*    */   }
/*    */   public boolean isMarked() {
/* 39 */     return this.buffer.isMarked();
/*    */   }
/*    */   public char LA(int paramInt) throws CharStreamException {
/* 42 */     char c = this.buffer.LA(paramInt);
/* 43 */     if (this.debugMode)
/* 44 */       this.inputBufferEventSupport.fireLA(c, paramInt);
/* 45 */     return c;
/*    */   }
/*    */   public int mark() {
/* 48 */     int i = this.buffer.mark();
/* 49 */     this.inputBufferEventSupport.fireMark(i);
/* 50 */     return i;
/*    */   }
/*    */   public void removeInputBufferListener(InputBufferListener paramInputBufferListener) {
/* 53 */     if (this.inputBufferEventSupport != null)
/* 54 */       this.inputBufferEventSupport.removeInputBufferListener(paramInputBufferListener); 
/*    */   }
/*    */ 
/* 57 */   public void rewind(int paramInt) { this.buffer.rewind(paramInt);
/* 58 */     this.inputBufferEventSupport.fireRewind(paramInt); }
/*    */ 
/*    */   public void setDebugMode(boolean paramBoolean) {
/* 61 */     this.debugMode = paramBoolean;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.debug.DebuggingInputBuffer
 * JD-Core Version:    0.6.2
 */