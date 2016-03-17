/*    */ package antlr;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.Reader;
/*    */ 
/*    */ public class LexerSharedInputState
/*    */ {
/* 19 */   protected int column = 1;
/* 20 */   protected int line = 1;
/* 21 */   protected int tokenStartColumn = 1;
/* 22 */   protected int tokenStartLine = 1;
/*    */   protected InputBuffer input;
/*    */   protected String filename;
/* 28 */   public int guessing = 0;
/*    */ 
/*    */   public LexerSharedInputState(InputBuffer paramInputBuffer) {
/* 31 */     this.input = paramInputBuffer;
/*    */   }
/*    */ 
/*    */   public LexerSharedInputState(InputStream paramInputStream) {
/* 35 */     this(new ByteBuffer(paramInputStream));
/*    */   }
/*    */ 
/*    */   public LexerSharedInputState(Reader paramReader) {
/* 39 */     this(new CharBuffer(paramReader));
/*    */   }
/*    */ 
/*    */   public String getFilename() {
/* 43 */     return this.filename;
/*    */   }
/*    */ 
/*    */   public InputBuffer getInput() {
/* 47 */     return this.input;
/*    */   }
/*    */ 
/*    */   public int getLine()
/*    */   {
/* 52 */     return this.line;
/*    */   }
/*    */ 
/*    */   public int getTokenStartColumn()
/*    */   {
/* 57 */     return this.tokenStartColumn;
/*    */   }
/*    */ 
/*    */   public int getTokenStartLine()
/*    */   {
/* 62 */     return this.tokenStartLine;
/*    */   }
/*    */ 
/*    */   public int getColumn()
/*    */   {
/* 67 */     return this.column;
/*    */   }
/*    */ 
/*    */   public void reset() {
/* 71 */     this.column = 1;
/* 72 */     this.line = 1;
/* 73 */     this.tokenStartColumn = 1;
/* 74 */     this.tokenStartLine = 1;
/* 75 */     this.guessing = 0;
/* 76 */     this.filename = null;
/* 77 */     this.input.reset();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.LexerSharedInputState
 * JD-Core Version:    0.6.2
 */