/*    */ package antlr.build;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ 
/*    */ class StreamScarfer extends Thread
/*    */ {
/*    */   InputStream is;
/*    */   String type;
/*    */   Tool tool;
/*    */ 
/*    */   StreamScarfer(InputStream paramInputStream, String paramString, Tool paramTool)
/*    */   {
/* 14 */     this.is = paramInputStream;
/* 15 */     this.type = paramString;
/* 16 */     this.tool = paramTool;
/*    */   }
/*    */ 
/*    */   public void run() {
/*    */     try {
/* 21 */       InputStreamReader localInputStreamReader = new InputStreamReader(this.is);
/* 22 */       BufferedReader localBufferedReader = new BufferedReader(localInputStreamReader);
/* 23 */       String str = null;
/* 24 */       while ((str = localBufferedReader.readLine()) != null) {
/* 25 */         if ((this.type == null) || (this.type.equals("stdout"))) {
/* 26 */           this.tool.stdout(str);
/*    */         }
/*    */         else
/* 29 */           this.tool.stderr(str);
/*    */       }
/*    */     }
/*    */     catch (IOException localIOException)
/*    */     {
/* 34 */       localIOException.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.build.StreamScarfer
 * JD-Core Version:    0.6.2
 */