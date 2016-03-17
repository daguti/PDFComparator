/*     */ package org.apache.pdfbox.preflight.javacc.extractor;
/*     */ 
/*     */ public class TokenMgrError extends Error
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   static final int LEXICAL_ERROR = 0;
/*     */   static final int STATIC_LEXER_ERROR = 1;
/*     */   static final int INVALID_LEXICAL_STATE = 2;
/*     */   static final int LOOP_DETECTED = 3;
/*     */   int errorCode;
/*     */ 
/*     */   protected static final String addEscapes(String str)
/*     */   {
/*  51 */     StringBuffer retval = new StringBuffer();
/*     */ 
/*  53 */     for (int i = 0; i < str.length(); i++) {
/*  54 */       switch (str.charAt(i))
/*     */       {
/*     */       case '\000':
/*  57 */         break;
/*     */       case '\b':
/*  59 */         retval.append("\\b");
/*  60 */         break;
/*     */       case '\t':
/*  62 */         retval.append("\\t");
/*  63 */         break;
/*     */       case '\n':
/*  65 */         retval.append("\\n");
/*  66 */         break;
/*     */       case '\f':
/*  68 */         retval.append("\\f");
/*  69 */         break;
/*     */       case '\r':
/*  71 */         retval.append("\\r");
/*  72 */         break;
/*     */       case '"':
/*  74 */         retval.append("\\\"");
/*  75 */         break;
/*     */       case '\'':
/*  77 */         retval.append("\\'");
/*  78 */         break;
/*     */       case '\\':
/*  80 */         retval.append("\\\\");
/*  81 */         break;
/*     */       default:
/*     */         char ch;
/*  83 */         if (((ch = str.charAt(i)) < ' ') || (ch > '~')) {
/*  84 */           String s = "0000" + Integer.toString(ch, 16);
/*  85 */           retval.append("\\u" + s.substring(s.length() - 4, s.length()));
/*     */         } else {
/*  87 */           retval.append(ch);
/*     */         }
/*     */         break;
/*     */       }
/*     */     }
/*  92 */     return retval.toString();
/*     */   }
/*     */ 
/*     */   protected static String LexicalError(boolean EOFSeen, int lexState, int errorLine, int errorColumn, String errorAfter, char curChar)
/*     */   {
/* 108 */     return "Lexical error at line " + errorLine + ", column " + errorColumn + ".  Encountered: " + (EOFSeen ? "<EOF> " : new StringBuilder().append("\"").append(addEscapes(String.valueOf(curChar))).append("\"").append(" (").append(curChar).append("), ").toString()) + "after : \"" + addEscapes(errorAfter) + "\"";
/*     */   }
/*     */ 
/*     */   public String getMessage()
/*     */   {
/* 125 */     return super.getMessage();
/*     */   }
/*     */ 
/*     */   public TokenMgrError()
/*     */   {
/*     */   }
/*     */ 
/*     */   public TokenMgrError(String message, int reason)
/*     */   {
/* 138 */     super(message);
/* 139 */     this.errorCode = reason;
/*     */   }
/*     */ 
/*     */   public TokenMgrError(boolean EOFSeen, int lexState, int errorLine, int errorColumn, String errorAfter, char curChar, int reason)
/*     */   {
/* 144 */     this(LexicalError(EOFSeen, lexState, errorLine, errorColumn, errorAfter, curChar), reason);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.javacc.extractor.TokenMgrError
 * JD-Core Version:    0.6.2
 */