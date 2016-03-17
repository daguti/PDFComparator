/*    */ package org.apache.pdfbox.preflight.utils;
/*    */ 
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import javax.activation.DataSource;
/*    */ import org.apache.commons.io.IOUtils;
/*    */ 
/*    */ public class ByteArrayDataSource
/*    */   implements DataSource
/*    */ {
/*    */   private ByteArrayOutputStream data;
/* 37 */   private String type = null;
/* 38 */   private String name = null;
/*    */ 
/*    */   public ByteArrayDataSource(InputStream is) throws IOException
/*    */   {
/* 42 */     this.data = new ByteArrayOutputStream();
/* 43 */     IOUtils.copyLarge(is, this.data);
/* 44 */     IOUtils.closeQuietly(is);
/*    */   }
/*    */ 
/*    */   public String getContentType()
/*    */   {
/* 49 */     return this.type;
/*    */   }
/*    */ 
/*    */   public void setType(String type)
/*    */   {
/* 58 */     this.type = type;
/*    */   }
/*    */ 
/*    */   public void setName(String name)
/*    */   {
/* 67 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public InputStream getInputStream() throws IOException
/*    */   {
/* 72 */     return new ByteArrayInputStream(this.data.toByteArray());
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 77 */     return this.name;
/*    */   }
/*    */ 
/*    */   public OutputStream getOutputStream() throws IOException
/*    */   {
/* 82 */     this.data = new ByteArrayOutputStream();
/* 83 */     return this.data;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.utils.ByteArrayDataSource
 * JD-Core Version:    0.6.2
 */