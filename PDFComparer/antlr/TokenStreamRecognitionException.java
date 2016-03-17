/*    */ package antlr;
/*    */ 
/*    */ public class TokenStreamRecognitionException extends TokenStreamException
/*    */ {
/*    */   public RecognitionException recog;
/*    */ 
/*    */   public TokenStreamRecognitionException(RecognitionException paramRecognitionException)
/*    */   {
/* 18 */     super(paramRecognitionException.getMessage());
/* 19 */     this.recog = paramRecognitionException;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 23 */     return this.recog.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.TokenStreamRecognitionException
 * JD-Core Version:    0.6.2
 */