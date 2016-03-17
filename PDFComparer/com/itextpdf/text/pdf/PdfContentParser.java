/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class PdfContentParser
/*     */ {
/*     */   public static final int COMMAND_TYPE = 200;
/*     */   private PRTokeniser tokeniser;
/*     */ 
/*     */   public PdfContentParser(PRTokeniser tokeniser)
/*     */   {
/*  72 */     this.tokeniser = tokeniser;
/*     */   }
/*     */ 
/*     */   public ArrayList<PdfObject> parse(ArrayList<PdfObject> ls)
/*     */     throws IOException
/*     */   {
/*  85 */     if (ls == null)
/*  86 */       ls = new ArrayList();
/*     */     else
/*  88 */       ls.clear();
/*  89 */     PdfObject ob = null;
/*  90 */     while ((ob = readPRObject()) != null) {
/*  91 */       ls.add(ob);
/*  92 */       if (ob.type() == 200)
/*  93 */         break;
/*     */     }
/*  95 */     return ls;
/*     */   }
/*     */ 
/*     */   public PRTokeniser getTokeniser()
/*     */   {
/* 103 */     return this.tokeniser;
/*     */   }
/*     */ 
/*     */   public void setTokeniser(PRTokeniser tokeniser)
/*     */   {
/* 111 */     this.tokeniser = tokeniser;
/*     */   }
/*     */ 
/*     */   public PdfDictionary readDictionary()
/*     */     throws IOException
/*     */   {
/* 120 */     PdfDictionary dic = new PdfDictionary();
/*     */     while (true) {
/* 122 */       if (!nextValidToken())
/* 123 */         throw new IOException(MessageLocalization.getComposedMessage("unexpected.end.of.file", new Object[0]));
/* 124 */       if (this.tokeniser.getTokenType() == PRTokeniser.TokenType.END_DIC)
/*     */         break;
/* 126 */       if ((this.tokeniser.getTokenType() != PRTokeniser.TokenType.OTHER) || (!"def".equals(this.tokeniser.getStringValue())))
/*     */       {
/* 128 */         if (this.tokeniser.getTokenType() != PRTokeniser.TokenType.NAME)
/* 129 */           throw new IOException(MessageLocalization.getComposedMessage("dictionary.key.1.is.not.a.name", new Object[] { this.tokeniser.getStringValue() }));
/* 130 */         PdfName name = new PdfName(this.tokeniser.getStringValue(), false);
/* 131 */         PdfObject obj = readPRObject();
/* 132 */         int type = obj.type();
/* 133 */         if (-type == PRTokeniser.TokenType.END_DIC.ordinal())
/* 134 */           throw new IOException(MessageLocalization.getComposedMessage("unexpected.gt.gt", new Object[0]));
/* 135 */         if (-type == PRTokeniser.TokenType.END_ARRAY.ordinal())
/* 136 */           throw new IOException(MessageLocalization.getComposedMessage("unexpected.close.bracket", new Object[0]));
/* 137 */         dic.put(name, obj);
/*     */       }
/*     */     }
/* 139 */     return dic;
/*     */   }
/*     */ 
/*     */   public PdfArray readArray()
/*     */     throws IOException
/*     */   {
/* 148 */     PdfArray array = new PdfArray();
/*     */     while (true) {
/* 150 */       PdfObject obj = readPRObject();
/* 151 */       int type = obj.type();
/* 152 */       if (-type == PRTokeniser.TokenType.END_ARRAY.ordinal())
/*     */         break;
/* 154 */       if (-type == PRTokeniser.TokenType.END_DIC.ordinal())
/* 155 */         throw new IOException(MessageLocalization.getComposedMessage("unexpected.gt.gt", new Object[0]));
/* 156 */       array.add(obj);
/*     */     }
/* 158 */     return array;
/*     */   }
/*     */ 
/*     */   public PdfObject readPRObject()
/*     */     throws IOException
/*     */   {
/* 167 */     if (!nextValidToken())
/* 168 */       return null;
/* 169 */     PRTokeniser.TokenType type = this.tokeniser.getTokenType();
/* 170 */     switch (1.$SwitchMap$com$itextpdf$text$pdf$PRTokeniser$TokenType[type.ordinal()]) {
/*     */     case 1:
/* 172 */       PdfDictionary dic = readDictionary();
/* 173 */       return dic;
/*     */     case 2:
/* 176 */       return readArray();
/*     */     case 3:
/* 178 */       PdfString str = new PdfString(this.tokeniser.getStringValue(), null).setHexWriting(this.tokeniser.isHexString());
/* 179 */       return str;
/*     */     case 4:
/* 181 */       return new PdfName(this.tokeniser.getStringValue(), false);
/*     */     case 5:
/* 183 */       return new PdfNumber(this.tokeniser.getStringValue());
/*     */     case 6:
/* 185 */       return new PdfLiteral(200, this.tokeniser.getStringValue());
/*     */     }
/* 187 */     return new PdfLiteral(-type.ordinal(), this.tokeniser.getStringValue());
/*     */   }
/*     */ 
/*     */   public boolean nextValidToken()
/*     */     throws IOException
/*     */   {
/* 197 */     while (this.tokeniser.nextToken())
/* 198 */       if (this.tokeniser.getTokenType() != PRTokeniser.TokenType.COMMENT)
/*     */       {
/* 200 */         return true;
/*     */       }
/* 202 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfContentParser
 * JD-Core Version:    0.6.2
 */