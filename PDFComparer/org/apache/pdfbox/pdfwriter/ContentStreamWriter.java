/*     */ package org.apache.pdfbox.pdfwriter;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.List;
/*     */ import java.util.Map.Entry;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBoolean;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSFloat;
/*     */ import org.apache.pdfbox.cos.COSInteger;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSString;
/*     */ import org.apache.pdfbox.util.ImageParameters;
/*     */ import org.apache.pdfbox.util.PDFOperator;
/*     */ 
/*     */ public class ContentStreamWriter
/*     */ {
/*     */   private OutputStream output;
/*  50 */   public static final byte[] SPACE = { 32 };
/*     */ 
/*  55 */   public static final byte[] EOL = { 10 };
/*     */ 
/*     */   public ContentStreamWriter(OutputStream out)
/*     */   {
/*  64 */     this.output = out;
/*     */   }
/*     */ 
/*     */   public void writeTokens(List tokens, int start, int end)
/*     */     throws IOException
/*     */   {
/*  77 */     for (int i = start; i < end; i++)
/*     */     {
/*  79 */       Object o = tokens.get(i);
/*  80 */       writeObject(o);
/*     */ 
/*  82 */       this.output.write(32);
/*     */     }
/*  84 */     this.output.flush();
/*     */   }
/*     */ 
/*     */   private void writeObject(Object o) throws IOException
/*     */   {
/*  89 */     if ((o instanceof COSString))
/*     */     {
/*  91 */       ((COSString)o).writePDF(this.output);
/*     */     }
/*  93 */     else if ((o instanceof COSFloat))
/*     */     {
/*  95 */       ((COSFloat)o).writePDF(this.output);
/*     */     }
/*  97 */     else if ((o instanceof COSInteger))
/*     */     {
/*  99 */       ((COSInteger)o).writePDF(this.output);
/*     */     }
/* 101 */     else if ((o instanceof COSBoolean))
/*     */     {
/* 103 */       ((COSBoolean)o).writePDF(this.output);
/*     */     }
/* 105 */     else if ((o instanceof COSName))
/*     */     {
/* 107 */       ((COSName)o).writePDF(this.output);
/*     */     }
/* 109 */     else if ((o instanceof COSArray))
/*     */     {
/* 111 */       COSArray array = (COSArray)o;
/* 112 */       this.output.write(COSWriter.ARRAY_OPEN);
/* 113 */       for (int i = 0; i < array.size(); i++)
/*     */       {
/* 115 */         writeObject(array.get(i));
/* 116 */         this.output.write(SPACE);
/*     */       }
/*     */ 
/* 119 */       this.output.write(COSWriter.ARRAY_CLOSE);
/*     */     }
/* 121 */     else if ((o instanceof COSDictionary))
/*     */     {
/* 123 */       COSDictionary obj = (COSDictionary)o;
/* 124 */       this.output.write(COSWriter.DICT_OPEN);
/* 125 */       for (Map.Entry entry : obj.entrySet())
/*     */       {
/* 127 */         if (entry.getValue() != null)
/*     */         {
/* 129 */           writeObject(entry.getKey());
/* 130 */           this.output.write(SPACE);
/* 131 */           writeObject(entry.getValue());
/* 132 */           this.output.write(SPACE);
/*     */         }
/*     */       }
/* 135 */       this.output.write(COSWriter.DICT_CLOSE);
/* 136 */       this.output.write(SPACE);
/*     */     }
/* 138 */     else if ((o instanceof PDFOperator))
/*     */     {
/* 140 */       PDFOperator op = (PDFOperator)o;
/* 141 */       if (op.getOperation().equals("BI"))
/*     */       {
/* 143 */         this.output.write("BI".getBytes("ISO-8859-1"));
/* 144 */         ImageParameters params = op.getImageParameters();
/* 145 */         COSDictionary dic = params.getDictionary();
/* 146 */         for (COSName key : dic.keySet())
/*     */         {
/* 148 */           Object value = dic.getDictionaryObject(key);
/* 149 */           key.writePDF(this.output);
/* 150 */           this.output.write(SPACE);
/* 151 */           writeObject(value);
/* 152 */           this.output.write(EOL);
/*     */         }
/* 154 */         this.output.write("ID".getBytes("ISO-8859-1"));
/* 155 */         this.output.write(EOL);
/* 156 */         this.output.write(op.getImageData());
/*     */       }
/*     */       else
/*     */       {
/* 160 */         this.output.write(op.getOperation().getBytes("ISO-8859-1"));
/* 161 */         this.output.write(EOL);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 166 */       throw new IOException("Error:Unknown type in content stream:" + o);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void writeTokens(List tokens)
/*     */     throws IOException
/*     */   {
/* 178 */     writeTokens(tokens, 0, tokens.size());
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdfwriter.ContentStreamWriter
 * JD-Core Version:    0.6.2
 */