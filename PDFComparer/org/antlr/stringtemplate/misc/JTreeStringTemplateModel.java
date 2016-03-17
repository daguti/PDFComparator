/*     */ package org.antlr.stringtemplate.misc;
/*     */ 
/*     */ import antlr.collections.AST;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.swing.event.TreeModelListener;
/*     */ import javax.swing.tree.TreeModel;
/*     */ import javax.swing.tree.TreePath;
/*     */ import org.antlr.stringtemplate.StringTemplate;
/*     */ import org.antlr.stringtemplate.StringTemplateGroup;
/*     */ import org.antlr.stringtemplate.language.ASTExpr;
/*     */ import org.antlr.stringtemplate.language.ConditionalExpr;
/*     */ import org.antlr.stringtemplate.language.Expr;
/*     */ import org.antlr.stringtemplate.language.StringRef;
/*     */ 
/*     */ public class JTreeStringTemplateModel
/*     */   implements TreeModel
/*     */ {
/*  46 */   static Map classNameToWrapperMap = new HashMap();
/*     */ 
/* 319 */   Wrapper root = null;
/*     */ 
/*     */   public static Object wrap(Object o)
/*     */   {
/* 325 */     Object wrappedObject = o;
/* 326 */     Class wrapperClass = null;
/*     */     try {
/* 328 */       wrapperClass = (Class)classNameToWrapperMap.get(o.getClass().getName());
/* 329 */       Constructor ctor = wrapperClass.getConstructor(new Class[] { Object.class });
/* 330 */       wrappedObject = ctor.newInstance(new Object[] { o });
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*     */ 
/* 336 */     return wrappedObject;
/*     */   }
/*     */ 
/*     */   public JTreeStringTemplateModel(StringTemplate st) {
/* 340 */     if (st == null) {
/* 341 */       throw new IllegalArgumentException("root is null");
/*     */     }
/* 343 */     this.root = new StringTemplateWrapper(st);
/*     */   }
/*     */ 
/*     */   public void addTreeModelListener(TreeModelListener l)
/*     */   {
/*     */   }
/*     */ 
/*     */   public Object getChild(Object parent, int index)
/*     */   {
/* 354 */     if (parent == null) {
/* 355 */       return null;
/*     */     }
/* 357 */     return ((Wrapper)parent).getChild(parent, index);
/*     */   }
/*     */ 
/*     */   public int getChildCount(Object parent) {
/* 361 */     if (parent == null) {
/* 362 */       throw new IllegalArgumentException("root is null");
/*     */     }
/* 364 */     return ((Wrapper)parent).getChildCount(parent);
/*     */   }
/*     */ 
/*     */   public int getIndexOfChild(Object parent, Object child) {
/* 368 */     if ((parent == null) || (child == null)) {
/* 369 */       throw new IllegalArgumentException("root or child is null");
/*     */     }
/* 371 */     return ((Wrapper)parent).getIndexOfChild(parent, child);
/*     */   }
/*     */ 
/*     */   public Object getRoot() {
/* 375 */     return this.root;
/*     */   }
/*     */ 
/*     */   public boolean isLeaf(Object node) {
/* 379 */     if (node == null) {
/* 380 */       throw new IllegalArgumentException("node is null");
/*     */     }
/* 382 */     if ((node instanceof Wrapper)) {
/* 383 */       return ((Wrapper)node).isLeaf(node);
/*     */     }
/* 385 */     return true;
/*     */   }
/*     */ 
/*     */   public void removeTreeModelListener(TreeModelListener l) {
/*     */   }
/*     */ 
/*     */   public void valueForPathChanged(TreePath path, Object newValue) {
/* 392 */     System.out.println("heh, who is calling this mystery method?");
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  49 */     classNameToWrapperMap.put("org.antlr.stringtemplate.StringTemplate", StringTemplateWrapper.class);
/*     */ 
/*  51 */     classNameToWrapperMap.put("org.antlr.stringtemplate.language.ASTExpr", ExprWrapper.class);
/*     */ 
/*  53 */     classNameToWrapperMap.put("java.util.Hashtable", HashMapWrapper.class);
/*     */ 
/*  55 */     classNameToWrapperMap.put("java.util.ArrayList", ListWrapper.class);
/*     */ 
/*  57 */     classNameToWrapperMap.put("java.util.Vector", ListWrapper.class);
/*     */   }
/*     */ 
/*     */   static class HashMapWrapper extends JTreeStringTemplateModel.Wrapper
/*     */   {
/*     */     HashMap table;
/*     */ 
/*     */     public HashMapWrapper(Object o)
/*     */     {
/* 278 */       this.table = ((HashMap)o);
/*     */     }
/*     */     public Object getWrappedObject() {
/* 281 */       return this.table;
/*     */     }
/*     */     public Object getChild(Object parent, int index) {
/* 284 */       List attributes = getTableAsListOfKeys();
/* 285 */       String key = (String)attributes.get(index);
/* 286 */       Object attr = this.table.get(key);
/* 287 */       Object wrappedAttr = JTreeStringTemplateModel.wrap(attr);
/* 288 */       return new JTreeStringTemplateModel.MapEntryWrapper(key, wrappedAttr);
/*     */     }
/*     */     public int getChildCount(Object parent) {
/* 291 */       List attributes = getTableAsListOfKeys();
/* 292 */       return attributes.size();
/*     */     }
/*     */     public int getIndexOfChild(Object parent, Object child) {
/* 295 */       List attributes = getTableAsListOfKeys();
/* 296 */       return attributes.indexOf(child);
/*     */     }
/*     */     public boolean isLeaf(Object node) {
/* 299 */       return false;
/*     */     }
/*     */     public String toString() {
/* 302 */       return "attributes";
/*     */     }
/*     */     private List getTableAsListOfKeys() {
/* 305 */       if (this.table == null) {
/* 306 */         return new LinkedList();
/*     */       }
/* 308 */       Set keys = this.table.keySet();
/* 309 */       List v = new LinkedList();
/* 310 */       for (Iterator itr = keys.iterator(); itr.hasNext(); ) {
/* 311 */         String attributeName = (String)itr.next();
/* 312 */         v.add(attributeName);
/*     */       }
/* 314 */       return v;
/*     */     }
/*     */   }
/*     */ 
/*     */   static class MapEntryWrapper extends JTreeStringTemplateModel.Wrapper
/*     */   {
/*     */     Object key;
/*     */     Object value;
/*     */ 
/*     */     public MapEntryWrapper(Object key, Object value)
/*     */     {
/* 239 */       this.key = key;
/* 240 */       this.value = value;
/*     */     }
/*     */     public Object getWrappedObject() {
/* 243 */       return JTreeStringTemplateModel.wrap(this.value);
/*     */     }
/*     */     public int getChildCount(Object parent) {
/* 246 */       if ((this.value instanceof JTreeStringTemplateModel.Wrapper)) {
/* 247 */         return ((JTreeStringTemplateModel.Wrapper)this.value).getChildCount(this.value);
/*     */       }
/* 249 */       return 1;
/*     */     }
/*     */ 
/*     */     public int getIndexOfChild(Object parent, Object child) {
/* 253 */       if ((this.value instanceof JTreeStringTemplateModel.Wrapper)) {
/* 254 */         return ((JTreeStringTemplateModel.Wrapper)this.value).getIndexOfChild(this.value, child);
/*     */       }
/* 256 */       return 0;
/*     */     }
/*     */ 
/*     */     public Object getChild(Object parent, int index) {
/* 260 */       if ((this.value instanceof JTreeStringTemplateModel.Wrapper)) {
/* 261 */         return ((JTreeStringTemplateModel.Wrapper)this.value).getChild(this.value, index);
/*     */       }
/* 263 */       return this.value;
/*     */     }
/*     */ 
/*     */     public boolean isLeaf(Object node) {
/* 267 */       return false;
/*     */     }
/*     */ 
/*     */     public String toString() {
/* 271 */       return this.key.toString();
/*     */     }
/*     */   }
/*     */ 
/*     */   static class ListWrapper extends JTreeStringTemplateModel.Wrapper
/*     */   {
/* 205 */     List v = null;
/*     */ 
/* 207 */     public ListWrapper(Object o) { this.v = ((List)o); }
/*     */ 
/*     */     public int getChildCount(Object parent) {
/* 210 */       return this.v.size();
/*     */     }
/*     */ 
/*     */     public int getIndexOfChild(Object parent, Object child) {
/* 214 */       if ((child instanceof JTreeStringTemplateModel.Wrapper)) {
/* 215 */         child = ((JTreeStringTemplateModel.Wrapper)child).getWrappedObject();
/*     */       }
/* 217 */       return this.v.indexOf(child);
/*     */     }
/*     */ 
/*     */     public Object getChild(Object parent, int index) {
/* 221 */       return this.v.get(index);
/*     */     }
/*     */ 
/*     */     public Object getWrappedObject() {
/* 225 */       return this.v;
/*     */     }
/*     */ 
/*     */     public boolean isLeaf(Object node) {
/* 229 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */   static class ExprWrapper extends JTreeStringTemplateModel.Wrapper
/*     */   {
/* 119 */     Expr expr = null;
/*     */ 
/* 121 */     public ExprWrapper(Object o) { this.expr = ((Expr)o); }
/*     */ 
/*     */     public Expr getExpr() {
/* 124 */       return this.expr;
/*     */     }
/*     */     public Object getWrappedObject() {
/* 127 */       return this.expr;
/*     */     }
/*     */     public Object getChild(Object parent, int index) {
/* 130 */       Expr expr = ((ExprWrapper)parent).getExpr();
/* 131 */       if ((expr instanceof ConditionalExpr))
/*     */       {
/* 133 */         return new JTreeStringTemplateModel.StringTemplateWrapper(((ConditionalExpr)expr).getSubtemplate());
/*     */       }
/*     */ 
/* 137 */       if ((expr instanceof ASTExpr)) {
/* 138 */         ASTExpr astExpr = (ASTExpr)expr;
/* 139 */         AST root = astExpr.getAST();
/* 140 */         if (root.getType() == 7)
/*     */         {
/* 143 */           switch (index) {
/*     */           case 0:
/* 145 */             return root.getFirstChild().getNextSibling().toStringList();
/*     */           case 1:
/* 147 */             String templateName = root.getFirstChild().getText();
/* 148 */             StringTemplate enclosingST = expr.getEnclosingTemplate();
/* 149 */             StringTemplateGroup group = enclosingST.getGroup();
/* 150 */             StringTemplate embedded = group.getEmbeddedInstanceOf(enclosingST, templateName);
/*     */ 
/* 153 */             return new JTreeStringTemplateModel.StringTemplateWrapper(embedded);
/*     */           }
/*     */         }
/*     */       }
/* 157 */       return "<invalid>";
/*     */     }
/*     */     public int getChildCount(Object parent) {
/* 160 */       if ((this.expr instanceof ConditionalExpr)) {
/* 161 */         return 1;
/*     */       }
/* 163 */       AST tree = ((ASTExpr)this.expr).getAST();
/* 164 */       if (tree.getType() == 7) {
/* 165 */         return 2;
/*     */       }
/* 167 */       return 0;
/*     */     }
/*     */ 
/*     */     public int getIndexOfChild(Object parent, Object child) {
/* 171 */       if ((this.expr instanceof ConditionalExpr)) {
/* 172 */         return 0;
/*     */       }
/* 174 */       return -1;
/*     */     }
/*     */     public boolean isLeaf(Object node) {
/* 177 */       if ((this.expr instanceof ConditionalExpr)) {
/* 178 */         return false;
/*     */       }
/* 180 */       if ((this.expr instanceof ASTExpr)) {
/* 181 */         AST tree = ((ASTExpr)this.expr).getAST();
/* 182 */         if (tree.getType() == 7) {
/* 183 */           return false;
/*     */         }
/*     */       }
/* 186 */       return true;
/*     */     }
/*     */ 
/*     */     public String toString() {
/* 190 */       if ((this.expr instanceof ASTExpr)) {
/* 191 */         AST tree = ((ASTExpr)this.expr).getAST();
/* 192 */         if (tree.getType() == 7) {
/* 193 */           return "$include$";
/*     */         }
/* 195 */         return "$" + ((ASTExpr)this.expr).getAST().toStringList() + "$";
/*     */       }
/* 197 */       if ((this.expr instanceof StringRef)) {
/* 198 */         return this.expr.toString();
/*     */       }
/* 200 */       return "<invalid node type>";
/*     */     }
/*     */   }
/*     */ 
/*     */   static class StringTemplateWrapper extends JTreeStringTemplateModel.Wrapper
/*     */   {
/*  71 */     StringTemplate st = null;
/*     */ 
/*  73 */     public StringTemplateWrapper(Object o) { this.st = ((StringTemplate)o); }
/*     */ 
/*     */     public Object getWrappedObject() {
/*  76 */       return getStringTemplate();
/*     */     }
/*     */     public StringTemplate getStringTemplate() {
/*  79 */       return this.st;
/*     */     }
/*     */     public Object getChild(Object parent, int index) {
/*  82 */       StringTemplate st = ((StringTemplateWrapper)parent).getStringTemplate();
/*  83 */       if (index == 0)
/*     */       {
/*  86 */         return new JTreeStringTemplateModel.HashMapWrapper(st.getAttributes());
/*     */       }
/*  88 */       Expr chunk = (Expr)st.getChunks().get(index - 1);
/*     */ 
/*  91 */       if ((chunk instanceof StringRef)) {
/*  92 */         return chunk;
/*     */       }
/*  94 */       return new JTreeStringTemplateModel.ExprWrapper(chunk);
/*     */     }
/*     */     public int getChildCount(Object parent) {
/*  97 */       return this.st.getChunks().size() + 1;
/*     */     }
/*     */     public int getIndexOfChild(Object parent, Object child) {
/* 100 */       if ((child instanceof JTreeStringTemplateModel.Wrapper)) {
/* 101 */         child = ((JTreeStringTemplateModel.Wrapper)child).getWrappedObject();
/*     */       }
/* 103 */       int index = this.st.getChunks().indexOf(child) + 1;
/*     */ 
/* 105 */       return index;
/*     */     }
/*     */     public boolean isLeaf(Object node) {
/* 108 */       return false;
/*     */     }
/*     */     public String toString() {
/* 111 */       if (this.st == null) {
/* 112 */         return "<invalid template>";
/*     */       }
/* 114 */       return this.st.getName();
/*     */     }
/*     */   }
/*     */ 
/*     */   static abstract class Wrapper
/*     */   {
/*     */     public abstract int getChildCount(Object paramObject);
/*     */ 
/*     */     public abstract int getIndexOfChild(Object paramObject1, Object paramObject2);
/*     */ 
/*     */     public abstract Object getChild(Object paramObject, int paramInt);
/*     */ 
/*     */     public abstract Object getWrappedObject();
/*     */ 
/*     */     public boolean isLeaf(Object node)
/*     */     {
/*  66 */       return true;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.stringtemplate.misc.JTreeStringTemplateModel
 * JD-Core Version:    0.6.2
 */