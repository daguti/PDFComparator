/*     */ package org.antlr.runtime.tree;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.antlr.runtime.RecognitionException;
/*     */ import org.antlr.runtime.Token;
/*     */ import org.antlr.runtime.TokenStream;
/*     */ 
/*     */ public abstract class BaseTreeAdaptor
/*     */   implements TreeAdaptor
/*     */ {
/*     */   protected Map treeToUniqueIDMap;
/*  44 */   protected int uniqueNodeID = 1;
/*     */ 
/*     */   public Object nil() {
/*  47 */     return create(null);
/*     */   }
/*     */ 
/*     */   public Object errorNode(TokenStream input, Token start, Token stop, RecognitionException e)
/*     */   {
/*  64 */     CommonErrorNode t = new CommonErrorNode(input, start, stop, e);
/*     */ 
/*  66 */     return t;
/*     */   }
/*     */ 
/*     */   public boolean isNil(Object tree) {
/*  70 */     return ((Tree)tree).isNil();
/*     */   }
/*     */ 
/*     */   public Object dupTree(Object tree) {
/*  74 */     return dupTree(tree, null);
/*     */   }
/*     */ 
/*     */   public Object dupTree(Object t, Object parent)
/*     */   {
/*  82 */     if (t == null) {
/*  83 */       return null;
/*     */     }
/*  85 */     Object newTree = dupNode(t);
/*     */ 
/*  87 */     setChildIndex(newTree, getChildIndex(t));
/*  88 */     setParent(newTree, parent);
/*  89 */     int n = getChildCount(t);
/*  90 */     for (int i = 0; i < n; i++) {
/*  91 */       Object child = getChild(t, i);
/*  92 */       Object newSubTree = dupTree(child, t);
/*  93 */       addChild(newTree, newSubTree);
/*     */     }
/*  95 */     return newTree;
/*     */   }
/*     */ 
/*     */   public void addChild(Object t, Object child)
/*     */   {
/* 106 */     if ((t != null) && (child != null))
/* 107 */       ((Tree)t).addChild((Tree)child);
/*     */   }
/*     */ 
/*     */   public Object becomeRoot(Object newRoot, Object oldRoot)
/*     */   {
/* 139 */     Tree newRootTree = (Tree)newRoot;
/* 140 */     Tree oldRootTree = (Tree)oldRoot;
/* 141 */     if (oldRoot == null) {
/* 142 */       return newRoot;
/*     */     }
/*     */ 
/* 145 */     if (newRootTree.isNil()) {
/* 146 */       int nc = newRootTree.getChildCount();
/* 147 */       if (nc == 1) newRootTree = newRootTree.getChild(0);
/* 148 */       else if (nc > 1)
/*     */       {
/* 150 */         throw new RuntimeException("more than one node as root (TODO: make exception hierarchy)");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 156 */     newRootTree.addChild(oldRootTree);
/* 157 */     return newRootTree;
/*     */   }
/*     */ 
/*     */   public Object rulePostProcessing(Object root)
/*     */   {
/* 163 */     Tree r = (Tree)root;
/* 164 */     if ((r != null) && (r.isNil())) {
/* 165 */       if (r.getChildCount() == 0) {
/* 166 */         r = null;
/*     */       }
/* 168 */       else if (r.getChildCount() == 1) {
/* 169 */         r = r.getChild(0);
/*     */ 
/* 171 */         r.setParent(null);
/* 172 */         r.setChildIndex(-1);
/*     */       }
/*     */     }
/* 175 */     return r;
/*     */   }
/*     */ 
/*     */   public Object becomeRoot(Token newRoot, Object oldRoot) {
/* 179 */     return becomeRoot(create(newRoot), oldRoot);
/*     */   }
/*     */ 
/*     */   public Object create(int tokenType, Token fromToken) {
/* 183 */     fromToken = createToken(fromToken);
/*     */ 
/* 185 */     fromToken.setType(tokenType);
/* 186 */     Tree t = (Tree)create(fromToken);
/* 187 */     return t;
/*     */   }
/*     */ 
/*     */   public Object create(int tokenType, Token fromToken, String text) {
/* 191 */     if (fromToken == null) return create(tokenType, text);
/* 192 */     fromToken = createToken(fromToken);
/* 193 */     fromToken.setType(tokenType);
/* 194 */     fromToken.setText(text);
/* 195 */     Tree t = (Tree)create(fromToken);
/* 196 */     return t;
/*     */   }
/*     */ 
/*     */   public Object create(int tokenType, String text) {
/* 200 */     Token fromToken = createToken(tokenType, text);
/* 201 */     Tree t = (Tree)create(fromToken);
/* 202 */     return t;
/*     */   }
/*     */ 
/*     */   public int getType(Object t) {
/* 206 */     return ((Tree)t).getType();
/*     */   }
/*     */ 
/*     */   public void setType(Object t, int type) {
/* 210 */     throw new NoSuchMethodError("don't know enough about Tree node");
/*     */   }
/*     */ 
/*     */   public String getText(Object t) {
/* 214 */     return ((Tree)t).getText();
/*     */   }
/*     */ 
/*     */   public void setText(Object t, String text) {
/* 218 */     throw new NoSuchMethodError("don't know enough about Tree node");
/*     */   }
/*     */ 
/*     */   public Object getChild(Object t, int i) {
/* 222 */     return ((Tree)t).getChild(i);
/*     */   }
/*     */ 
/*     */   public void setChild(Object t, int i, Object child) {
/* 226 */     ((Tree)t).setChild(i, (Tree)child);
/*     */   }
/*     */ 
/*     */   public Object deleteChild(Object t, int i) {
/* 230 */     return ((Tree)t).deleteChild(i);
/*     */   }
/*     */ 
/*     */   public int getChildCount(Object t) {
/* 234 */     return ((Tree)t).getChildCount();
/*     */   }
/*     */ 
/*     */   public int getUniqueID(Object node) {
/* 238 */     if (this.treeToUniqueIDMap == null) {
/* 239 */       this.treeToUniqueIDMap = new HashMap();
/*     */     }
/* 241 */     Integer prevID = (Integer)this.treeToUniqueIDMap.get(node);
/* 242 */     if (prevID != null) {
/* 243 */       return prevID.intValue();
/*     */     }
/* 245 */     int ID = this.uniqueNodeID;
/* 246 */     this.treeToUniqueIDMap.put(node, new Integer(ID));
/* 247 */     this.uniqueNodeID += 1;
/* 248 */     return ID;
/*     */   }
/*     */ 
/*     */   public abstract Token createToken(int paramInt, String paramString);
/*     */ 
/*     */   public abstract Token createToken(Token paramToken);
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.tree.BaseTreeAdaptor
 * JD-Core Version:    0.6.2
 */