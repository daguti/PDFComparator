/*     */ package org.stringtemplate.v4.gui;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.swing.event.TreeModelListener;
/*     */ import javax.swing.tree.TreeModel;
/*     */ import javax.swing.tree.TreePath;
/*     */ import org.antlr.runtime.tree.CommonTree;
/*     */ import org.stringtemplate.v4.InstanceScope;
/*     */ import org.stringtemplate.v4.Interpreter;
/*     */ import org.stringtemplate.v4.ST;
/*     */ import org.stringtemplate.v4.ST.DebugState;
/*     */ import org.stringtemplate.v4.debug.AddAttributeEvent;
/*     */ import org.stringtemplate.v4.misc.MultiMap;
/*     */ 
/*     */ public class JTreeScopeStackModel
/*     */   implements TreeModel
/*     */ {
/*     */   CommonTree root;
/*     */ 
/*     */   public JTreeScopeStackModel(InstanceScope scope)
/*     */   {
/*  66 */     this.root = new StringTree("Scope stack:");
/*  67 */     List stack = Interpreter.getScopeStack(scope, true);
/*  68 */     for (InstanceScope s : stack) {
/*  69 */       StringTree templateNode = new StringTree(s.st.getName());
/*  70 */       this.root.addChild(templateNode);
/*  71 */       addAttributeDescriptions(s.st, templateNode);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addAttributeDescriptions(ST st, StringTree node)
/*     */   {
/*  77 */     Map attrs = st.getAttributes();
/*  78 */     if (attrs == null) return;
/*  79 */     for (String a : attrs.keySet()) {
/*  80 */       String descr = null;
/*  81 */       if ((st.debugState != null) && (st.debugState.addAttrEvents != null)) {
/*  82 */         List events = (List)st.debugState.addAttrEvents.get(a);
/*  83 */         StringBuilder locations = new StringBuilder();
/*  84 */         int i = 0;
/*  85 */         if (events != null) {
/*  86 */           for (AddAttributeEvent ae : events) {
/*  87 */             if (i > 0) locations.append(", ");
/*  88 */             locations.append(ae.getFileName() + ":" + ae.getLine());
/*  89 */             i++;
/*     */           }
/*     */         }
/*  92 */         if (locations.length() > 0) {
/*  93 */           descr = a + " = " + attrs.get(a) + " @ " + locations.toString();
/*     */         }
/*     */         else
/*  96 */           descr = a + " = " + attrs.get(a);
/*     */       }
/*     */       else
/*     */       {
/* 100 */         descr = a + " = " + attrs.get(a);
/*     */       }
/* 102 */       node.addChild(new StringTree(descr));
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object getRoot() {
/* 107 */     return this.root;
/*     */   }
/*     */ 
/*     */   public Object getChild(Object parent, int i) {
/* 111 */     StringTree t = (StringTree)parent;
/* 112 */     return t.getChild(i);
/*     */   }
/*     */ 
/*     */   public int getChildCount(Object parent) {
/* 116 */     StringTree t = (StringTree)parent;
/* 117 */     return t.getChildCount();
/*     */   }
/*     */ 
/*     */   public boolean isLeaf(Object node) {
/* 121 */     return getChildCount(node) == 0;
/*     */   }
/*     */ 
/*     */   public int getIndexOfChild(Object parent, Object child) {
/* 125 */     StringTree c = (StringTree)child;
/* 126 */     return c.getChildIndex();
/*     */   }
/*     */ 
/*     */   public void valueForPathChanged(TreePath treePath, Object o)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void addTreeModelListener(TreeModelListener treeModelListener)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void removeTreeModelListener(TreeModelListener treeModelListener)
/*     */   {
/*     */   }
/*     */ 
/*     */   public static class StringTree extends CommonTree
/*     */   {
/*     */     String text;
/*     */ 
/*     */     public StringTree(String text)
/*     */     {
/*  51 */       this.text = text;
/*     */     }
/*     */ 
/*     */     public boolean isNil() {
/*  55 */       return this.text == null;
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/*  60 */       if (!isNil()) return this.text.toString();
/*  61 */       return "nil";
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.gui.JTreeScopeStackModel
 * JD-Core Version:    0.6.2
 */