/*     */ package org.antlr.stringtemplate.language;
/*     */ 
/*     */ import antlr.LLkParser;
/*     */ import antlr.NoViableAltException;
/*     */ import antlr.ParserSharedInputState;
/*     */ import antlr.RecognitionException;
/*     */ import antlr.Token;
/*     */ import antlr.TokenBuffer;
/*     */ import antlr.TokenStream;
/*     */ import antlr.TokenStreamException;
/*     */ import antlr.collections.impl.BitSet;
/*     */ import java.io.PrintStream;
/*     */ import java.util.LinkedHashMap;
/*     */ import org.antlr.stringtemplate.StringTemplateGroupInterface;
/*     */ 
/*     */ public class InterfaceParser extends LLkParser
/*     */   implements InterfaceParserTokenTypes
/*     */ {
/*     */   protected StringTemplateGroupInterface groupI;
/* 224 */   public static final String[] _tokenNames = { "<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "\"interface\"", "ID", "SEMI", "\"optional\"", "LPAREN", "RPAREN", "COMMA", "COLON", "SL_COMMENT", "ML_COMMENT", "WS" };
/*     */ 
/* 246 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/*     */ 
/* 251 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/*     */ 
/* 256 */   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
/*     */ 
/*     */   public void reportError(RecognitionException e)
/*     */   {
/*  61 */     if (this.groupI != null) {
/*  62 */       this.groupI.error("template group interface parse error", e);
/*     */     }
/*     */     else {
/*  65 */       System.err.println("template group interface parse error: " + e);
/*  66 */       e.printStackTrace(System.err);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected InterfaceParser(TokenBuffer tokenBuf, int k) {
/*  71 */     super(tokenBuf, k);
/*  72 */     this.tokenNames = _tokenNames;
/*     */   }
/*     */ 
/*     */   public InterfaceParser(TokenBuffer tokenBuf) {
/*  76 */     this(tokenBuf, 3);
/*     */   }
/*     */ 
/*     */   protected InterfaceParser(TokenStream lexer, int k) {
/*  80 */     super(lexer, k);
/*  81 */     this.tokenNames = _tokenNames;
/*     */   }
/*     */ 
/*     */   public InterfaceParser(TokenStream lexer) {
/*  85 */     this(lexer, 3);
/*     */   }
/*     */ 
/*     */   public InterfaceParser(ParserSharedInputState state) {
/*  89 */     super(state, 3);
/*  90 */     this.tokenNames = _tokenNames;
/*     */   }
/*     */ 
/*     */   public final void groupInterface(StringTemplateGroupInterface groupI)
/*     */     throws RecognitionException, TokenStreamException
/*     */   {
/*  97 */     Token name = null;
/*  98 */     this.groupI = groupI;
/*     */     try
/*     */     {
/* 101 */       match(4);
/* 102 */       name = LT(1);
/* 103 */       match(5);
/* 104 */       groupI.setName(name.getText());
/* 105 */       match(6);
/*     */ 
/* 107 */       int _cnt3 = 0;
/*     */       while (true)
/*     */       {
/* 110 */         if ((LA(1) == 5) || (LA(1) == 7)) {
/* 111 */           template(groupI);
/*     */         }
/*     */         else {
/* 114 */           if (_cnt3 >= 1) break; throw new NoViableAltException(LT(1), getFilename());
/*     */         }
/*     */ 
/* 117 */         _cnt3++;
/*     */       }
/*     */     }
/*     */     catch (RecognitionException ex)
/*     */     {
/* 122 */       reportError(ex);
/* 123 */       recover(ex, _tokenSet_0);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void template(StringTemplateGroupInterface groupI)
/*     */     throws RecognitionException, TokenStreamException
/*     */   {
/* 131 */     Token opt = null;
/* 132 */     Token name = null;
/*     */ 
/* 134 */     LinkedHashMap formalArgs = new LinkedHashMap();
/* 135 */     String templateName = null;
/*     */     try
/*     */     {
/* 140 */       switch (LA(1))
/*     */       {
/*     */       case 7:
/* 143 */         opt = LT(1);
/* 144 */         match(7);
/* 145 */         break;
/*     */       case 5:
/* 149 */         break;
/*     */       default:
/* 153 */         throw new NoViableAltException(LT(1), getFilename());
/*     */       }
/*     */ 
/* 157 */       name = LT(1);
/* 158 */       match(5);
/* 159 */       match(8);
/*     */ 
/* 161 */       switch (LA(1))
/*     */       {
/*     */       case 5:
/* 164 */         formalArgs = args();
/* 165 */         break;
/*     */       case 9:
/* 169 */         break;
/*     */       default:
/* 173 */         throw new NoViableAltException(LT(1), getFilename());
/*     */       }
/*     */ 
/* 177 */       match(9);
/* 178 */       match(6);
/*     */ 
/* 180 */       templateName = name.getText();
/* 181 */       groupI.defineTemplate(templateName, formalArgs, opt != null);
/*     */     }
/*     */     catch (RecognitionException ex)
/*     */     {
/* 185 */       reportError(ex);
/* 186 */       recover(ex, _tokenSet_1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final LinkedHashMap args() throws RecognitionException, TokenStreamException {
/* 191 */     LinkedHashMap args = new LinkedHashMap();
/*     */ 
/* 193 */     Token a = null;
/* 194 */     Token b = null;
/*     */     try
/*     */     {
/* 197 */       a = LT(1);
/* 198 */       match(5);
/* 199 */       args.put(a.getText(), new FormalArgument(a.getText()));
/*     */ 
/* 203 */       while (LA(1) == 10) {
/* 204 */         match(10);
/* 205 */         b = LT(1);
/* 206 */         match(5);
/* 207 */         args.put(b.getText(), new FormalArgument(b.getText()));
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (RecognitionException ex)
/*     */     {
/* 217 */       reportError(ex);
/* 218 */       recover(ex, _tokenSet_2);
/*     */     }
/* 220 */     return args;
/*     */   }
/*     */ 
/*     */   private static final long[] mk_tokenSet_0()
/*     */   {
/* 243 */     long[] data = { 2L, 0L };
/* 244 */     return data;
/*     */   }
/*     */ 
/*     */   private static final long[] mk_tokenSet_1() {
/* 248 */     long[] data = { 162L, 0L };
/* 249 */     return data;
/*     */   }
/*     */ 
/*     */   private static final long[] mk_tokenSet_2() {
/* 253 */     long[] data = { 512L, 0L };
/* 254 */     return data;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.stringtemplate.language.InterfaceParser
 * JD-Core Version:    0.6.2
 */