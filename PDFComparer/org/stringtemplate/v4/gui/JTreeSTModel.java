/*     */ package org.stringtemplate.v4.gui;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.swing.event.TreeModelListener;
/*     */ import javax.swing.tree.TreeModel;
/*     */ import javax.swing.tree.TreePath;
/*     */ import org.stringtemplate.v4.InstanceScope;
/*     */ import org.stringtemplate.v4.Interpreter;
/*     */ import org.stringtemplate.v4.ST;
/*     */ import org.stringtemplate.v4.ST.DebugState;
/*     */ import org.stringtemplate.v4.debug.ConstructionEvent;
/*     */ import org.stringtemplate.v4.debug.EvalTemplateEvent;
/*     */ 
/*     */ public class JTreeSTModel
/*     */   implements TreeModel
/*     */ {
/*     */   public Interpreter interp;
/*     */   public Wrapper root;
/*     */ 
/*     */   public JTreeSTModel(Interpreter interp, EvalTemplateEvent root)
/*     */   {
/*  71 */     this.interp = interp;
/*  72 */     this.root = new Wrapper(root);
/*     */   }
/*     */ 
/*     */   public Object getChild(Object parent, int index) {
/*  76 */     EvalTemplateEvent e = ((Wrapper)parent).event;
/*  77 */     return new Wrapper((EvalTemplateEvent)e.scope.childEvalTemplateEvents.get(index));
/*     */   }
/*     */ 
/*     */   public int getChildCount(Object parent) {
/*  81 */     EvalTemplateEvent e = ((Wrapper)parent).event;
/*  82 */     return e.scope.childEvalTemplateEvents.size();
/*     */   }
/*     */ 
/*     */   public int getIndexOfChild(Object parent, Object child) {
/*  86 */     EvalTemplateEvent p = ((Wrapper)parent).event;
/*  87 */     EvalTemplateEvent c = ((Wrapper)parent).event;
/*  88 */     int i = 0;
/*  89 */     for (EvalTemplateEvent e : p.scope.childEvalTemplateEvents) {
/*  90 */       if (e.scope.st == c.scope.st)
/*     */       {
/*  93 */         return i;
/*     */       }
/*  95 */       i++;
/*     */     }
/*  97 */     return -1;
/*     */   }
/*     */ 
/*     */   public boolean isLeaf(Object node) {
/* 101 */     return getChildCount(node) == 0;
/*     */   }
/*     */   public Object getRoot() {
/* 104 */     return this.root;
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
/*     */   public static class Wrapper
/*     */   {
/*     */     EvalTemplateEvent event;
/*     */ 
/*     */     public Wrapper(EvalTemplateEvent event)
/*     */     {
/*  44 */       this.event = event;
/*     */     }
/*     */ 
/*     */     public int hashCode() {
/*  48 */       return this.event.hashCode();
/*     */     }
/*     */ 
/*     */     public boolean equals(Object o)
/*     */     {
/*  54 */       return this.event == ((Wrapper)o).event;
/*     */     }
/*     */ 
/*     */     public String toString() {
/*  58 */       ST st = this.event.scope.st;
/*  59 */       if (st.isAnonSubtemplate()) return "{...}";
/*  60 */       if ((st.debugState != null) && (st.debugState.newSTEvent != null)) {
/*  61 */         return st.toString() + " @ " + st.debugState.newSTEvent.getFileName() + ":" + st.debugState.newSTEvent.getLine();
/*     */       }
/*     */ 
/*  65 */       return st.toString();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.gui.JTreeSTModel
 * JD-Core Version:    0.6.2
 */