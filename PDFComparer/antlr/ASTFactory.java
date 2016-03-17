/*     */ package antlr;
/*     */ 
/*     */ import antlr.collections.AST;
/*     */ import antlr.collections.impl.ASTArray;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ public class ASTFactory
/*     */ {
/*  32 */   protected String theASTNodeType = null;
/*  33 */   protected Class theASTNodeTypeClass = null;
/*     */ 
/*  51 */   protected Hashtable tokenTypeToASTClassMap = null;
/*     */ 
/*     */   public ASTFactory()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ASTFactory(Hashtable paramHashtable)
/*     */   {
/*  61 */     setTokenTypeToASTClassMap(paramHashtable);
/*     */   }
/*     */ 
/*     */   public void setTokenTypeASTNodeType(int paramInt, String paramString)
/*     */     throws IllegalArgumentException
/*     */   {
/*  83 */     if (this.tokenTypeToASTClassMap == null) {
/*  84 */       this.tokenTypeToASTClassMap = new Hashtable();
/*     */     }
/*  86 */     if (paramString == null) {
/*  87 */       this.tokenTypeToASTClassMap.remove(new Integer(paramInt));
/*  88 */       return;
/*     */     }
/*  90 */     Class localClass = null;
/*     */     try {
/*  92 */       localClass = Utils.loadClass(paramString);
/*  93 */       this.tokenTypeToASTClassMap.put(new Integer(paramInt), localClass);
/*     */     }
/*     */     catch (Exception localException) {
/*  96 */       throw new IllegalArgumentException("Invalid class, " + paramString);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Class getASTNodeType(int paramInt)
/*     */   {
/* 106 */     if (this.tokenTypeToASTClassMap != null) {
/* 107 */       Class localClass = (Class)this.tokenTypeToASTClassMap.get(new Integer(paramInt));
/* 108 */       if (localClass != null) {
/* 109 */         return localClass;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 114 */     if (this.theASTNodeTypeClass != null) {
/* 115 */       return this.theASTNodeTypeClass;
/*     */     }
/*     */ 
/* 119 */     return CommonAST.class;
/*     */   }
/*     */ 
/*     */   public void addASTChild(ASTPair paramASTPair, AST paramAST)
/*     */   {
/* 124 */     if (paramAST != null) {
/* 125 */       if (paramASTPair.root == null)
/*     */       {
/* 127 */         paramASTPair.root = paramAST;
/*     */       }
/* 130 */       else if (paramASTPair.child == null)
/*     */       {
/* 132 */         paramASTPair.root.setFirstChild(paramAST);
/*     */       }
/*     */       else {
/* 135 */         paramASTPair.child.setNextSibling(paramAST);
/*     */       }
/*     */ 
/* 139 */       paramASTPair.child = paramAST;
/* 140 */       paramASTPair.advanceChildToEnd();
/*     */     }
/*     */   }
/*     */ 
/*     */   public AST create()
/*     */   {
/* 148 */     return create(0);
/*     */   }
/*     */ 
/*     */   public AST create(int paramInt) {
/* 152 */     Class localClass = getASTNodeType(paramInt);
/* 153 */     AST localAST = create(localClass);
/* 154 */     if (localAST != null) {
/* 155 */       localAST.initialize(paramInt, "");
/*     */     }
/* 157 */     return localAST;
/*     */   }
/*     */ 
/*     */   public AST create(int paramInt, String paramString) {
/* 161 */     AST localAST = create(paramInt);
/* 162 */     if (localAST != null) {
/* 163 */       localAST.initialize(paramInt, paramString);
/*     */     }
/* 165 */     return localAST;
/*     */   }
/*     */ 
/*     */   public AST create(int paramInt, String paramString1, String paramString2)
/*     */   {
/* 174 */     AST localAST = create(paramString2);
/* 175 */     if (localAST != null) {
/* 176 */       localAST.initialize(paramInt, paramString1);
/*     */     }
/* 178 */     return localAST;
/*     */   }
/*     */ 
/*     */   public AST create(AST paramAST)
/*     */   {
/* 185 */     if (paramAST == null) return null;
/* 186 */     AST localAST = create(paramAST.getType());
/* 187 */     if (localAST != null) {
/* 188 */       localAST.initialize(paramAST);
/*     */     }
/* 190 */     return localAST;
/*     */   }
/*     */ 
/*     */   public AST create(Token paramToken) {
/* 194 */     AST localAST = create(paramToken.getType());
/* 195 */     if (localAST != null) {
/* 196 */       localAST.initialize(paramToken);
/*     */     }
/* 198 */     return localAST;
/*     */   }
/*     */ 
/*     */   public AST create(Token paramToken, String paramString)
/*     */   {
/* 210 */     AST localAST = createUsingCtor(paramToken, paramString);
/* 211 */     return localAST;
/*     */   }
/*     */ 
/*     */   public AST create(String paramString)
/*     */   {
/* 218 */     Class localClass = null;
/*     */     try {
/* 220 */       localClass = Utils.loadClass(paramString);
/*     */     }
/*     */     catch (Exception localException) {
/* 223 */       throw new IllegalArgumentException("Invalid class, " + paramString);
/*     */     }
/* 225 */     return create(localClass);
/*     */   }
/*     */ 
/*     */   protected AST createUsingCtor(Token paramToken, String paramString)
/*     */   {
/* 232 */     Class localClass = null;
/* 233 */     AST localAST = null;
/*     */     try {
/* 235 */       localClass = Utils.loadClass(paramString);
/* 236 */       Class[] arrayOfClass = { Token.class };
/*     */       try {
/* 238 */         Constructor localConstructor = localClass.getConstructor(arrayOfClass);
/* 239 */         localAST = (AST)localConstructor.newInstance(new Object[] { paramToken });
/*     */       }
/*     */       catch (NoSuchMethodException localNoSuchMethodException)
/*     */       {
/* 244 */         localAST = create(localClass);
/* 245 */         if (localAST != null)
/* 246 */           localAST.initialize(paramToken);
/*     */       }
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/* 251 */       throw new IllegalArgumentException("Invalid class or can't make instance, " + paramString);
/*     */     }
/* 253 */     return localAST;
/*     */   }
/*     */ 
/*     */   protected AST create(Class paramClass)
/*     */   {
/* 260 */     AST localAST = null;
/*     */     try {
/* 262 */       localAST = (AST)paramClass.newInstance();
/*     */     }
/*     */     catch (Exception localException) {
/* 265 */       error("Can't create AST Node " + paramClass.getName());
/* 266 */       return null;
/*     */     }
/* 268 */     return localAST;
/*     */   }
/*     */ 
/*     */   public AST dup(AST paramAST)
/*     */   {
/* 280 */     if (paramAST == null) {
/* 281 */       return null;
/*     */     }
/* 283 */     AST localAST = create(paramAST.getClass());
/* 284 */     localAST.initialize(paramAST);
/* 285 */     return localAST;
/*     */   }
/*     */ 
/*     */   public AST dupList(AST paramAST)
/*     */   {
/* 290 */     AST localAST1 = dupTree(paramAST);
/* 291 */     AST localAST2 = localAST1;
/* 292 */     while (paramAST != null) {
/* 293 */       paramAST = paramAST.getNextSibling();
/* 294 */       localAST2.setNextSibling(dupTree(paramAST));
/* 295 */       localAST2 = localAST2.getNextSibling();
/*     */     }
/* 297 */     return localAST1;
/*     */   }
/*     */ 
/*     */   public AST dupTree(AST paramAST)
/*     */   {
/* 304 */     AST localAST = dup(paramAST);
/*     */ 
/* 306 */     if (paramAST != null) {
/* 307 */       localAST.setFirstChild(dupList(paramAST.getFirstChild()));
/*     */     }
/* 309 */     return localAST;
/*     */   }
/*     */ 
/*     */   public AST make(AST[] paramArrayOfAST)
/*     */   {
/* 319 */     if ((paramArrayOfAST == null) || (paramArrayOfAST.length == 0)) return null;
/* 320 */     AST localAST1 = paramArrayOfAST[0];
/* 321 */     AST localAST2 = null;
/* 322 */     if (localAST1 != null) {
/* 323 */       localAST1.setFirstChild(null);
/*     */     }
/*     */ 
/* 326 */     for (int i = 1; i < paramArrayOfAST.length; i++) {
/* 327 */       if (paramArrayOfAST[i] != null) {
/* 328 */         if (localAST1 == null)
/*     */         {
/* 330 */           localAST1 = localAST2 = paramArrayOfAST[i];
/*     */         }
/* 332 */         else if (localAST2 == null) {
/* 333 */           localAST1.setFirstChild(paramArrayOfAST[i]);
/* 334 */           localAST2 = localAST1.getFirstChild();
/*     */         }
/*     */         else {
/* 337 */           localAST2.setNextSibling(paramArrayOfAST[i]);
/* 338 */           localAST2 = localAST2.getNextSibling();
/*     */         }
/*     */ 
/* 341 */         while (localAST2.getNextSibling() != null)
/* 342 */           localAST2 = localAST2.getNextSibling();
/*     */       }
/*     */     }
/* 345 */     return localAST1;
/*     */   }
/*     */ 
/*     */   public AST make(ASTArray paramASTArray)
/*     */   {
/* 352 */     return make(paramASTArray.array);
/*     */   }
/*     */ 
/*     */   public void makeASTRoot(ASTPair paramASTPair, AST paramAST)
/*     */   {
/* 357 */     if (paramAST != null)
/*     */     {
/* 359 */       paramAST.addChild(paramASTPair.root);
/*     */ 
/* 361 */       paramASTPair.child = paramASTPair.root;
/* 362 */       paramASTPair.advanceChildToEnd();
/*     */ 
/* 364 */       paramASTPair.root = paramAST;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setASTNodeClass(Class paramClass) {
/* 369 */     if (paramClass != null) {
/* 370 */       this.theASTNodeTypeClass = paramClass;
/* 371 */       this.theASTNodeType = paramClass.getName();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setASTNodeClass(String paramString) {
/* 376 */     this.theASTNodeType = paramString;
/*     */     try {
/* 378 */       this.theASTNodeTypeClass = Utils.loadClass(paramString);
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/* 384 */       error("Can't find/access AST Node type" + paramString);
/*     */     }
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void setASTNodeType(String paramString)
/*     */   {
/* 392 */     setASTNodeClass(paramString);
/*     */   }
/*     */ 
/*     */   public Hashtable getTokenTypeToASTClassMap() {
/* 396 */     return this.tokenTypeToASTClassMap;
/*     */   }
/*     */ 
/*     */   public void setTokenTypeToASTClassMap(Hashtable paramHashtable) {
/* 400 */     this.tokenTypeToASTClassMap = paramHashtable;
/*     */   }
/*     */ 
/*     */   public void error(String paramString)
/*     */   {
/* 408 */     System.err.println(paramString);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.ASTFactory
 * JD-Core Version:    0.6.2
 */