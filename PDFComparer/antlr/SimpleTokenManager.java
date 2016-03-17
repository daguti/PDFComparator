/*     */ package antlr;
/*     */ 
/*     */ import antlr.collections.impl.Vector;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ class SimpleTokenManager
/*     */   implements TokenManager, Cloneable
/*     */ {
/*  17 */   protected int maxToken = 4;
/*     */   protected Vector vocabulary;
/*     */   private Hashtable table;
/*     */   protected Tool antlrTool;
/*     */   protected String name;
/*  27 */   protected boolean readOnly = false;
/*     */ 
/*     */   SimpleTokenManager(String paramString, Tool paramTool) {
/*  30 */     this.antlrTool = paramTool;
/*  31 */     this.name = paramString;
/*     */ 
/*  33 */     this.vocabulary = new Vector(1);
/*  34 */     this.table = new Hashtable();
/*     */ 
/*  37 */     TokenSymbol localTokenSymbol = new TokenSymbol("EOF");
/*  38 */     localTokenSymbol.setTokenType(1);
/*  39 */     define(localTokenSymbol);
/*     */ 
/*  42 */     this.vocabulary.ensureCapacity(3);
/*  43 */     this.vocabulary.setElementAt("NULL_TREE_LOOKAHEAD", 3);
/*     */   }
/*     */ 
/*     */   public Object clone() {
/*     */     SimpleTokenManager localSimpleTokenManager;
/*     */     try {
/*  49 */       localSimpleTokenManager = (SimpleTokenManager)super.clone();
/*  50 */       localSimpleTokenManager.vocabulary = ((Vector)this.vocabulary.clone());
/*  51 */       localSimpleTokenManager.table = ((Hashtable)this.table.clone());
/*  52 */       localSimpleTokenManager.maxToken = this.maxToken;
/*  53 */       localSimpleTokenManager.antlrTool = this.antlrTool;
/*  54 */       localSimpleTokenManager.name = this.name;
/*     */     }
/*     */     catch (CloneNotSupportedException localCloneNotSupportedException) {
/*  57 */       this.antlrTool.panic("cannot clone token manager");
/*  58 */       return null;
/*     */     }
/*  60 */     return localSimpleTokenManager;
/*     */   }
/*     */ 
/*     */   public void define(TokenSymbol paramTokenSymbol)
/*     */   {
/*  66 */     this.vocabulary.ensureCapacity(paramTokenSymbol.getTokenType());
/*  67 */     this.vocabulary.setElementAt(paramTokenSymbol.getId(), paramTokenSymbol.getTokenType());
/*     */ 
/*  69 */     mapToTokenSymbol(paramTokenSymbol.getId(), paramTokenSymbol);
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  74 */     return this.name;
/*     */   }
/*     */ 
/*     */   public String getTokenStringAt(int paramInt)
/*     */   {
/*  79 */     return (String)this.vocabulary.elementAt(paramInt);
/*     */   }
/*     */ 
/*     */   public TokenSymbol getTokenSymbol(String paramString)
/*     */   {
/*  84 */     return (TokenSymbol)this.table.get(paramString);
/*     */   }
/*     */ 
/*     */   public TokenSymbol getTokenSymbolAt(int paramInt)
/*     */   {
/*  89 */     return getTokenSymbol(getTokenStringAt(paramInt));
/*     */   }
/*     */ 
/*     */   public Enumeration getTokenSymbolElements()
/*     */   {
/*  94 */     return this.table.elements();
/*     */   }
/*     */ 
/*     */   public Enumeration getTokenSymbolKeys() {
/*  98 */     return this.table.keys();
/*     */   }
/*     */ 
/*     */   public Vector getVocabulary()
/*     */   {
/* 105 */     return this.vocabulary;
/*     */   }
/*     */ 
/*     */   public boolean isReadOnly()
/*     */   {
/* 110 */     return false;
/*     */   }
/*     */ 
/*     */   public void mapToTokenSymbol(String paramString, TokenSymbol paramTokenSymbol)
/*     */   {
/* 116 */     this.table.put(paramString, paramTokenSymbol);
/*     */   }
/*     */ 
/*     */   public int maxTokenType()
/*     */   {
/* 121 */     return this.maxToken - 1;
/*     */   }
/*     */ 
/*     */   public int nextTokenType()
/*     */   {
/* 126 */     return this.maxToken++;
/*     */   }
/*     */ 
/*     */   public void setName(String paramString)
/*     */   {
/* 131 */     this.name = paramString;
/*     */   }
/*     */ 
/*     */   public void setReadOnly(boolean paramBoolean) {
/* 135 */     this.readOnly = paramBoolean;
/*     */   }
/*     */ 
/*     */   public boolean tokenDefined(String paramString)
/*     */   {
/* 140 */     return this.table.containsKey(paramString);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.SimpleTokenManager
 * JD-Core Version:    0.6.2
 */