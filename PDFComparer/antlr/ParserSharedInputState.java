/*    */ package antlr;
/*    */ 
/*    */ public class ParserSharedInputState
/*    */ {
/*    */   protected TokenBuffer input;
/* 20 */   public int guessing = 0;
/*    */   protected String filename;
/*    */ 
/*    */   public void reset()
/*    */   {
/* 26 */     this.guessing = 0;
/* 27 */     this.filename = null;
/* 28 */     this.input.reset();
/*    */   }
/*    */ 
/*    */   public String getFilename() {
/* 32 */     return this.filename;
/*    */   }
/*    */ 
/*    */   public TokenBuffer getInput() {
/* 36 */     return this.input;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.ParserSharedInputState
 * JD-Core Version:    0.6.2
 */