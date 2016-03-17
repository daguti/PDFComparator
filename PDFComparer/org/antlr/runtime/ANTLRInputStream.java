/*    */ package org.antlr.runtime;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ 
/*    */ public class ANTLRInputStream extends ANTLRReaderStream
/*    */ {
/*    */   public ANTLRInputStream()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ANTLRInputStream(InputStream input)
/*    */     throws IOException
/*    */   {
/* 40 */     this(input, null);
/*    */   }
/*    */ 
/*    */   public ANTLRInputStream(InputStream input, int size) throws IOException {
/* 44 */     this(input, size, null);
/*    */   }
/*    */ 
/*    */   public ANTLRInputStream(InputStream input, String encoding) throws IOException {
/* 48 */     this(input, 1024, encoding);
/*    */   }
/*    */ 
/*    */   public ANTLRInputStream(InputStream input, int size, String encoding) throws IOException {
/* 52 */     this(input, size, 1024, encoding);
/*    */   }
/*    */ 
/*    */   public ANTLRInputStream(InputStream input, int size, int readBufferSize, String encoding)
/*    */     throws IOException
/*    */   {
/*    */     InputStreamReader isr;
/*    */     InputStreamReader isr;
/* 62 */     if (encoding != null) {
/* 63 */       isr = new InputStreamReader(input, encoding);
/*    */     }
/*    */     else {
/* 66 */       isr = new InputStreamReader(input);
/*    */     }
/* 68 */     load(isr, size, readBufferSize);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.ANTLRInputStream
 * JD-Core Version:    0.6.2
 */