/*     */ package org.apache.pdfbox.pdmodel.common;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.cos.COSString;
/*     */ 
/*     */ public class PDTextStream
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSString string;
/*     */   private COSStream stream;
/*     */ 
/*     */   public PDTextStream(COSString str)
/*     */   {
/*  50 */     this.string = str;
/*     */   }
/*     */ 
/*     */   public PDTextStream(String str)
/*     */   {
/*  60 */     this.string = new COSString(str);
/*     */   }
/*     */ 
/*     */   public PDTextStream(COSStream str)
/*     */   {
/*  70 */     this.stream = str;
/*     */   }
/*     */ 
/*     */   public static PDTextStream createTextStream(COSBase base)
/*     */   {
/*  83 */     PDTextStream retval = null;
/*  84 */     if ((base instanceof COSString))
/*     */     {
/*  86 */       retval = new PDTextStream((COSString)base);
/*     */     }
/*  88 */     else if ((base instanceof COSStream))
/*     */     {
/*  90 */       retval = new PDTextStream((COSStream)base);
/*     */     }
/*  92 */     return retval;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/* 102 */     COSBase retval = null;
/* 103 */     if (this.string == null)
/*     */     {
/* 105 */       retval = this.stream;
/*     */     }
/*     */     else
/*     */     {
/* 109 */       retval = this.string;
/*     */     }
/* 111 */     return retval;
/*     */   }
/*     */ 
/*     */   public String getAsString()
/*     */     throws IOException
/*     */   {
/* 125 */     String retval = null;
/* 126 */     if (this.string != null)
/*     */     {
/* 128 */       retval = this.string.getString();
/*     */     }
/*     */     else
/*     */     {
/* 132 */       ByteArrayOutputStream out = new ByteArrayOutputStream();
/* 133 */       byte[] buffer = new byte[1024];
/* 134 */       int amountRead = -1;
/* 135 */       InputStream is = this.stream.getUnfilteredStream();
/* 136 */       while ((amountRead = is.read(buffer)) != -1)
/*     */       {
/* 138 */         out.write(buffer, 0, amountRead);
/*     */       }
/* 140 */       retval = new String(out.toByteArray(), "ISO-8859-1");
/*     */     }
/* 142 */     return retval;
/*     */   }
/*     */ 
/*     */   public InputStream getAsStream()
/*     */     throws IOException
/*     */   {
/* 155 */     InputStream retval = null;
/* 156 */     if (this.string != null)
/*     */     {
/* 158 */       retval = new ByteArrayInputStream(this.string.getBytes());
/*     */     }
/*     */     else
/*     */     {
/* 162 */       retval = this.stream.getUnfilteredStream();
/*     */     }
/* 164 */     return retval;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.PDTextStream
 * JD-Core Version:    0.6.2
 */