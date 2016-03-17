/*    */ package antlr;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.File;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.FileReader;
/*    */ 
/*    */ class ImportVocabTokenManager extends SimpleTokenManager
/*    */   implements Cloneable
/*    */ {
/*    */   private String filename;
/*    */   protected Grammar grammar;
/*    */ 
/*    */   ImportVocabTokenManager(Grammar paramGrammar, String paramString1, String paramString2, Tool paramTool)
/*    */   {
/* 25 */     super(paramString2, paramTool);
/*    */ 
/* 27 */     this.grammar = paramGrammar;
/* 28 */     this.filename = paramString1;
/*    */ 
/* 33 */     File localFile = new File(this.filename);
/*    */ 
/* 35 */     if (!localFile.exists()) {
/* 36 */       localFile = new File(this.antlrTool.getOutputDirectory(), this.filename);
/*    */ 
/* 38 */       if (!localFile.exists()) {
/* 39 */         this.antlrTool.panic("Cannot find importVocab file '" + this.filename + "'");
/*    */       }
/*    */     }
/*    */ 
/* 43 */     setReadOnly(true);
/*    */     try
/*    */     {
/* 47 */       BufferedReader localBufferedReader = new BufferedReader(new FileReader(localFile));
/* 48 */       ANTLRTokdefLexer localANTLRTokdefLexer = new ANTLRTokdefLexer(localBufferedReader);
/* 49 */       ANTLRTokdefParser localANTLRTokdefParser = new ANTLRTokdefParser(localANTLRTokdefLexer);
/* 50 */       localANTLRTokdefParser.setTool(this.antlrTool);
/* 51 */       localANTLRTokdefParser.setFilename(this.filename);
/* 52 */       localANTLRTokdefParser.file(this);
/*    */     }
/*    */     catch (FileNotFoundException localFileNotFoundException) {
/* 55 */       this.antlrTool.panic("Cannot find importVocab file '" + this.filename + "'");
/*    */     }
/*    */     catch (RecognitionException localRecognitionException) {
/* 58 */       this.antlrTool.panic("Error parsing importVocab file '" + this.filename + "': " + localRecognitionException.toString());
/*    */     }
/*    */     catch (TokenStreamException localTokenStreamException) {
/* 61 */       this.antlrTool.panic("Error reading importVocab file '" + this.filename + "'");
/*    */     }
/*    */   }
/*    */ 
/*    */   public Object clone()
/*    */   {
/* 67 */     ImportVocabTokenManager localImportVocabTokenManager = (ImportVocabTokenManager)super.clone();
/* 68 */     localImportVocabTokenManager.filename = this.filename;
/* 69 */     localImportVocabTokenManager.grammar = this.grammar;
/* 70 */     return localImportVocabTokenManager;
/*    */   }
/*    */ 
/*    */   public void define(TokenSymbol paramTokenSymbol)
/*    */   {
/* 75 */     super.define(paramTokenSymbol);
/*    */   }
/*    */ 
/*    */   public void define(String paramString, int paramInt)
/*    */   {
/* 80 */     Object localObject = null;
/* 81 */     if (paramString.startsWith("\"")) {
/* 82 */       localObject = new StringLiteralSymbol(paramString);
/*    */     }
/*    */     else {
/* 85 */       localObject = new TokenSymbol(paramString);
/*    */     }
/* 87 */     ((TokenSymbol)localObject).setTokenType(paramInt);
/* 88 */     super.define((TokenSymbol)localObject);
/* 89 */     this.maxToken = (paramInt + 1 > this.maxToken ? paramInt + 1 : this.maxToken);
/*    */   }
/*    */ 
/*    */   public boolean isReadOnly()
/*    */   {
/* 94 */     return this.readOnly;
/*    */   }
/*    */ 
/*    */   public int nextTokenType()
/*    */   {
/* 99 */     return super.nextTokenType();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.ImportVocabTokenManager
 * JD-Core Version:    0.6.2
 */