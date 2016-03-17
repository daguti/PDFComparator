/*     */ package antlr;
/*     */ 
/*     */ import antlr.ASdebug.ASDebugStream;
/*     */ import antlr.ASdebug.IASDebugStream;
/*     */ import antlr.ASdebug.TokenOffsetInfo;
/*     */ import antlr.collections.Stack;
/*     */ import antlr.collections.impl.LList;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ public class TokenStreamSelector
/*     */   implements TokenStream, IASDebugStream
/*     */ {
/*     */   protected Hashtable inputStreamNames;
/*     */   protected TokenStream input;
/*  33 */   protected Stack streamStack = new LList();
/*     */ 
/*     */   public TokenStreamSelector()
/*     */   {
/*  37 */     this.inputStreamNames = new Hashtable();
/*     */   }
/*     */ 
/*     */   public void addInputStream(TokenStream paramTokenStream, String paramString) {
/*  41 */     this.inputStreamNames.put(paramString, paramTokenStream);
/*     */   }
/*     */ 
/*     */   public TokenStream getCurrentStream()
/*     */   {
/*  48 */     return this.input;
/*     */   }
/*     */ 
/*     */   public TokenStream getStream(String paramString) {
/*  52 */     TokenStream localTokenStream = (IASDebugStream)this.inputStreamNames.get(paramString);
/*  53 */     if (localTokenStream == null) {
/*  54 */       throw new IllegalArgumentException("TokenStream " + paramString + " not found");
/*     */     }
/*  56 */     return localTokenStream;
/*     */   }
/*     */ 
/*     */   public Token nextToken()
/*     */     throws TokenStreamException
/*     */   {
/*     */     while (true)
/*     */       try
/*     */       {
/*  65 */         return this.input.nextToken();
/*     */       }
/*     */       catch (TokenStreamRetryException localTokenStreamRetryException)
/*     */       {
/*     */       }
/*     */   }
/*     */ 
/*     */   public TokenStream pop()
/*     */   {
/*  74 */     TokenStream localTokenStream = (IASDebugStream)this.streamStack.pop();
/*  75 */     select(localTokenStream);
/*  76 */     return localTokenStream;
/*     */   }
/*     */ 
/*     */   public void push(TokenStream paramTokenStream) {
/*  80 */     this.streamStack.push(this.input);
/*  81 */     select(paramTokenStream);
/*     */   }
/*     */ 
/*     */   public void push(String paramString) {
/*  85 */     this.streamStack.push(this.input);
/*  86 */     select(paramString);
/*     */   }
/*     */ 
/*     */   public void retry()
/*     */     throws TokenStreamRetryException
/*     */   {
/*  97 */     throw new TokenStreamRetryException();
/*     */   }
/*     */ 
/*     */   public void select(TokenStream paramTokenStream)
/*     */   {
/* 102 */     this.input = paramTokenStream;
/*     */   }
/*     */ 
/*     */   public void select(String paramString) throws IllegalArgumentException {
/* 106 */     this.input = getStream(paramString);
/*     */   }
/*     */ 
/*     */   public String getEntireText()
/*     */   {
/* 111 */     return ASDebugStream.getEntireText(this.input);
/*     */   }
/*     */ 
/*     */   public TokenOffsetInfo getOffsetInfo(Token paramToken)
/*     */   {
/* 116 */     return ASDebugStream.getOffsetInfo(this.input, paramToken);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.TokenStreamSelector
 * JD-Core Version:    0.6.2
 */