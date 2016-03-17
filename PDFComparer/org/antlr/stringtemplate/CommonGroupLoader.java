/*    */ package org.antlr.stringtemplate;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.List;
/*    */ 
/*    */ public class CommonGroupLoader extends PathGroupLoader
/*    */ {
/*    */   public CommonGroupLoader(StringTemplateErrorListener errors)
/*    */   {
/* 13 */     super(errors);
/*    */   }
/*    */ 
/*    */   public CommonGroupLoader(String dirStr, StringTemplateErrorListener errors)
/*    */   {
/* 24 */     super(dirStr, errors);
/*    */   }
/*    */ 
/*    */   protected BufferedReader locate(String name)
/*    */     throws IOException
/*    */   {
/* 31 */     for (int i = 0; i < this.dirs.size(); i++) {
/* 32 */       String dir = (String)this.dirs.get(i);
/* 33 */       String fileName = dir + "/" + name;
/*    */ 
/* 35 */       ClassLoader cl = Thread.currentThread().getContextClassLoader();
/* 36 */       InputStream is = cl.getResourceAsStream(fileName);
/* 37 */       if (is == null) {
/* 38 */         cl = getClass().getClassLoader();
/* 39 */         is = cl.getResourceAsStream(fileName);
/*    */       }
/* 41 */       if (is != null) {
/* 42 */         return new BufferedReader(getInputStreamReader(is));
/*    */       }
/*    */     }
/* 45 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.stringtemplate.CommonGroupLoader
 * JD-Core Version:    0.6.2
 */