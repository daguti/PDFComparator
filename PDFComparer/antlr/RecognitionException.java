/*    */ package antlr;
/*    */ 
/*    */ public class RecognitionException extends ANTLRException
/*    */ {
/*    */   public String fileName;
/*    */   public int line;
/*    */   public int column;
/*    */ 
/*    */   public RecognitionException()
/*    */   {
/* 16 */     super("parsing error");
/* 17 */     this.fileName = null;
/* 18 */     this.line = -1;
/* 19 */     this.column = -1;
/*    */   }
/*    */ 
/*    */   public RecognitionException(String paramString)
/*    */   {
/* 27 */     super(paramString);
/* 28 */     this.fileName = null;
/* 29 */     this.line = -1;
/* 30 */     this.column = -1;
/*    */   }
/*    */ 
/*    */   /** @deprecated */
/*    */   public RecognitionException(String paramString1, String paramString2, int paramInt) {
/* 35 */     this(paramString1, paramString2, paramInt, -1);
/*    */   }
/*    */ 
/*    */   public RecognitionException(String paramString1, String paramString2, int paramInt1, int paramInt2)
/*    */   {
/* 43 */     super(paramString1);
/* 44 */     this.fileName = paramString2;
/* 45 */     this.line = paramInt1;
/* 46 */     this.column = paramInt2;
/*    */   }
/*    */ 
/*    */   public String getFilename() {
/* 50 */     return this.fileName;
/*    */   }
/*    */ 
/*    */   public int getLine() {
/* 54 */     return this.line;
/*    */   }
/*    */ 
/*    */   public int getColumn() {
/* 58 */     return this.column;
/*    */   }
/*    */ 
/*    */   /** @deprecated */
/*    */   public String getErrorMessage() {
/* 63 */     return getMessage();
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 67 */     return FileLineFormatter.getFormatter().getFormatString(this.fileName, this.line, this.column) + getMessage();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.RecognitionException
 * JD-Core Version:    0.6.2
 */