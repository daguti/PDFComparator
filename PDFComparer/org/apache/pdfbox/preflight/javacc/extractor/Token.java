/*     */ package org.apache.pdfbox.preflight.javacc.extractor;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class Token
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public int kind;
/*     */   public int beginLine;
/*     */   public int beginColumn;
/*     */   public int endLine;
/*     */   public int endColumn;
/*     */   public String image;
/*     */   public Token next;
/*     */   public Token specialToken;
/*     */ 
/*     */   public Object getValue()
/*     */   {
/*  72 */     return null;
/*     */   }
/*     */ 
/*     */   public Token()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Token(int kind)
/*     */   {
/*  85 */     this(kind, null);
/*     */   }
/*     */ 
/*     */   public Token(int kind, String image)
/*     */   {
/*  93 */     this.kind = kind;
/*  94 */     this.image = image;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 102 */     return this.image;
/*     */   }
/*     */ 
/*     */   public static Token newToken(int ofKind, String image)
/*     */   {
/* 119 */     switch (ofKind) {
/*     */     }
/* 121 */     return new Token(ofKind, image);
/*     */   }
/*     */ 
/*     */   public static Token newToken(int ofKind)
/*     */   {
/* 127 */     return newToken(ofKind, null);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.javacc.extractor.Token
 * JD-Core Version:    0.6.2
 */