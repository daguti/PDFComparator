/*     */ package antlr;
/*     */ 
/*     */ import antlr.collections.impl.BitSet;
/*     */ 
/*     */ public class ANTLRTokdefParser extends LLkParser
/*     */   implements ANTLRTokdefParserTokenTypes
/*     */ {
/*     */   private Tool antlrTool;
/* 222 */   public static final String[] _tokenNames = { "<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "ID", "STRING", "ASSIGN", "LPAREN", "RPAREN", "INT", "WS", "SL_COMMENT", "ML_COMMENT", "ESC", "DIGIT", "XDIGIT" };
/*     */ 
/* 245 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/*     */ 
/* 250 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/*     */ 
/*     */   public void setTool(Tool paramTool)
/*     */   {
/*  34 */     if (this.antlrTool == null) {
/*  35 */       this.antlrTool = paramTool;
/*     */     }
/*     */     else
/*  38 */       throw new IllegalStateException("antlr.Tool already registered");
/*     */   }
/*     */ 
/*     */   protected Tool getTool()
/*     */   {
/*  44 */     return this.antlrTool;
/*     */   }
/*     */ 
/*     */   public void reportError(String paramString)
/*     */   {
/*  52 */     if (getTool() != null) {
/*  53 */       getTool().error(paramString, getFilename(), -1, -1);
/*     */     }
/*     */     else
/*  56 */       super.reportError(paramString);
/*     */   }
/*     */ 
/*     */   public void reportError(RecognitionException paramRecognitionException)
/*     */   {
/*  65 */     if (getTool() != null) {
/*  66 */       getTool().error(paramRecognitionException.getErrorMessage(), paramRecognitionException.getFilename(), paramRecognitionException.getLine(), paramRecognitionException.getColumn());
/*     */     }
/*     */     else
/*  69 */       super.reportError(paramRecognitionException);
/*     */   }
/*     */ 
/*     */   public void reportWarning(String paramString)
/*     */   {
/*  78 */     if (getTool() != null) {
/*  79 */       getTool().warning(paramString, getFilename(), -1, -1);
/*     */     }
/*     */     else
/*  82 */       super.reportWarning(paramString);
/*     */   }
/*     */ 
/*     */   protected ANTLRTokdefParser(TokenBuffer paramTokenBuffer, int paramInt)
/*     */   {
/*  87 */     super(paramTokenBuffer, paramInt);
/*  88 */     this.tokenNames = _tokenNames;
/*     */   }
/*     */ 
/*     */   public ANTLRTokdefParser(TokenBuffer paramTokenBuffer) {
/*  92 */     this(paramTokenBuffer, 3);
/*     */   }
/*     */ 
/*     */   protected ANTLRTokdefParser(TokenStream paramTokenStream, int paramInt) {
/*  96 */     super(paramTokenStream, paramInt);
/*  97 */     this.tokenNames = _tokenNames;
/*     */   }
/*     */ 
/*     */   public ANTLRTokdefParser(TokenStream paramTokenStream) {
/* 101 */     this(paramTokenStream, 3);
/*     */   }
/*     */ 
/*     */   public ANTLRTokdefParser(ParserSharedInputState paramParserSharedInputState) {
/* 105 */     super(paramParserSharedInputState, 3);
/* 106 */     this.tokenNames = _tokenNames;
/*     */   }
/*     */ 
/*     */   public final void file(ImportVocabTokenManager paramImportVocabTokenManager)
/*     */     throws RecognitionException, TokenStreamException
/*     */   {
/* 113 */     Token localToken = null;
/*     */     try
/*     */     {
/* 116 */       localToken = LT(1);
/* 117 */       match(4);
/*     */ 
/* 121 */       while ((LA(1) == 4) || (LA(1) == 5)) {
/* 122 */         line(paramImportVocabTokenManager);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (RecognitionException localRecognitionException)
/*     */     {
/* 132 */       reportError(localRecognitionException);
/* 133 */       consume();
/* 134 */       consumeUntil(_tokenSet_0);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void line(ImportVocabTokenManager paramImportVocabTokenManager)
/*     */     throws RecognitionException, TokenStreamException
/*     */   {
/* 142 */     Token localToken1 = null;
/* 143 */     Token localToken2 = null;
/* 144 */     Token localToken3 = null;
/* 145 */     Token localToken4 = null;
/* 146 */     Token localToken5 = null;
/* 147 */     Token localToken6 = null;
/* 148 */     Token localToken7 = null;
/* 149 */     Token localToken8 = null; Token localToken9 = null;
/*     */     try
/*     */     {
/* 153 */       if (LA(1) == 5) {
/* 154 */         localToken1 = LT(1);
/* 155 */         match(5);
/* 156 */         localToken9 = localToken1;
/*     */       }
/* 158 */       else if ((LA(1) == 4) && (LA(2) == 6) && (LA(3) == 5)) {
/* 159 */         localToken2 = LT(1);
/* 160 */         match(4);
/* 161 */         localToken8 = localToken2;
/* 162 */         match(6);
/* 163 */         localToken3 = LT(1);
/* 164 */         match(5);
/* 165 */         localToken9 = localToken3;
/*     */       }
/* 167 */       else if ((LA(1) == 4) && (LA(2) == 7)) {
/* 168 */         localToken4 = LT(1);
/* 169 */         match(4);
/* 170 */         localToken8 = localToken4;
/* 171 */         match(7);
/* 172 */         localToken5 = LT(1);
/* 173 */         match(5);
/* 174 */         match(8);
/*     */       }
/* 176 */       else if ((LA(1) == 4) && (LA(2) == 6) && (LA(3) == 9)) {
/* 177 */         localToken6 = LT(1);
/* 178 */         match(4);
/* 179 */         localToken8 = localToken6;
/*     */       }
/*     */       else {
/* 182 */         throw new NoViableAltException(LT(1), getFilename());
/*     */       }
/*     */ 
/* 186 */       match(6);
/* 187 */       localToken7 = LT(1);
/* 188 */       match(9);
/*     */ 
/* 190 */       Integer localInteger = Integer.valueOf(localToken7.getText());
/*     */       Object localObject;
/* 192 */       if (localToken9 != null) {
/* 193 */         paramImportVocabTokenManager.define(localToken9.getText(), localInteger.intValue());
/*     */ 
/* 195 */         if (localToken8 != null) {
/* 196 */           localObject = (StringLiteralSymbol)paramImportVocabTokenManager.getTokenSymbol(localToken9.getText());
/*     */ 
/* 198 */           ((StringLiteralSymbol)localObject).setLabel(localToken8.getText());
/* 199 */           paramImportVocabTokenManager.mapToTokenSymbol(localToken8.getText(), (TokenSymbol)localObject);
/*     */         }
/*     */ 
/*     */       }
/* 203 */       else if (localToken8 != null) {
/* 204 */         paramImportVocabTokenManager.define(localToken8.getText(), localInteger.intValue());
/* 205 */         if (localToken5 != null) {
/* 206 */           localObject = paramImportVocabTokenManager.getTokenSymbol(localToken8.getText());
/* 207 */           ((TokenSymbol)localObject).setParaphrase(localToken5.getText());
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (RecognitionException localRecognitionException)
/*     */     {
/* 215 */       reportError(localRecognitionException);
/* 216 */       consume();
/* 217 */       consumeUntil(_tokenSet_1);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static final long[] mk_tokenSet_0()
/*     */   {
/* 242 */     long[] arrayOfLong = { 2L, 0L };
/* 243 */     return arrayOfLong;
/*     */   }
/*     */ 
/*     */   private static final long[] mk_tokenSet_1() {
/* 247 */     long[] arrayOfLong = { 50L, 0L };
/* 248 */     return arrayOfLong;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.ANTLRTokdefParser
 * JD-Core Version:    0.6.2
 */