/*    */ package antlr;
/*    */ 
/*    */ public class ANTLRStringBuffer
/*    */ {
/* 14 */   protected char[] buffer = null;
/* 15 */   protected int length = 0;
/*    */ 
/*    */   public ANTLRStringBuffer()
/*    */   {
/* 19 */     this.buffer = new char[50];
/*    */   }
/*    */ 
/*    */   public ANTLRStringBuffer(int paramInt) {
/* 23 */     this.buffer = new char[paramInt];
/*    */   }
/*    */ 
/*    */   public final void append(char paramChar)
/*    */   {
/* 29 */     if (this.length >= this.buffer.length)
/*    */     {
/* 31 */       int i = this.buffer.length;
/* 32 */       while (this.length >= i) {
/* 33 */         i *= 2;
/*    */       }
/*    */ 
/* 36 */       char[] arrayOfChar = new char[i];
/* 37 */       for (int j = 0; j < this.length; j++) {
/* 38 */         arrayOfChar[j] = this.buffer[j];
/*    */       }
/* 40 */       this.buffer = arrayOfChar;
/*    */     }
/* 42 */     this.buffer[this.length] = paramChar;
/* 43 */     this.length += 1;
/*    */   }
/*    */ 
/*    */   public final void append(String paramString) {
/* 47 */     for (int i = 0; i < paramString.length(); i++)
/* 48 */       append(paramString.charAt(i));
/*    */   }
/*    */ 
/*    */   public final char charAt(int paramInt)
/*    */   {
/* 53 */     return this.buffer[paramInt];
/*    */   }
/*    */ 
/*    */   public final char[] getBuffer() {
/* 57 */     return this.buffer;
/*    */   }
/*    */ 
/*    */   public final int length() {
/* 61 */     return this.length;
/*    */   }
/*    */ 
/*    */   public final void setCharAt(int paramInt, char paramChar) {
/* 65 */     this.buffer[paramInt] = paramChar;
/*    */   }
/*    */ 
/*    */   public final void setLength(int paramInt) {
/* 69 */     if (paramInt < this.length) {
/* 70 */       this.length = paramInt;
/*    */     }
/*    */     else
/* 73 */       while (paramInt > this.length)
/* 74 */         append('\000');
/*    */   }
/*    */ 
/*    */   public final String toString()
/*    */   {
/* 80 */     return new String(this.buffer, 0, this.length);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.ANTLRStringBuffer
 * JD-Core Version:    0.6.2
 */