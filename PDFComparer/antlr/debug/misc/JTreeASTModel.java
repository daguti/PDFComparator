/*    */ package antlr.debug.misc;
/*    */ 
/*    */ import antlr.collections.AST;
/*    */ import java.io.PrintStream;
/*    */ import java.util.NoSuchElementException;
/*    */ import javax.swing.event.TreeModelListener;
/*    */ import javax.swing.tree.TreeModel;
/*    */ import javax.swing.tree.TreePath;
/*    */ 
/*    */ public class JTreeASTModel
/*    */   implements TreeModel
/*    */ {
/* 18 */   AST root = null;
/*    */ 
/*    */   public JTreeASTModel(AST paramAST) {
/* 21 */     if (paramAST == null) {
/* 22 */       throw new IllegalArgumentException("root is null");
/*    */     }
/* 24 */     this.root = paramAST;
/*    */   }
/*    */ 
/*    */   public void addTreeModelListener(TreeModelListener paramTreeModelListener) {
/*    */   }
/*    */ 
/*    */   public Object getChild(Object paramObject, int paramInt) {
/* 31 */     if (paramObject == null) {
/* 32 */       return null;
/*    */     }
/* 34 */     AST localAST1 = (AST)paramObject;
/* 35 */     AST localAST2 = localAST1.getFirstChild();
/* 36 */     if (localAST2 == null) {
/* 37 */       throw new ArrayIndexOutOfBoundsException("node has no children");
/*    */     }
/* 39 */     int i = 0;
/* 40 */     while ((localAST2 != null) && (i < paramInt)) {
/* 41 */       localAST2 = localAST2.getNextSibling();
/* 42 */       i++;
/*    */     }
/* 44 */     return localAST2;
/*    */   }
/*    */ 
/*    */   public int getChildCount(Object paramObject) {
/* 48 */     if (paramObject == null) {
/* 49 */       throw new IllegalArgumentException("root is null");
/*    */     }
/* 51 */     AST localAST1 = (AST)paramObject;
/* 52 */     AST localAST2 = localAST1.getFirstChild();
/* 53 */     int i = 0;
/* 54 */     while (localAST2 != null) {
/* 55 */       localAST2 = localAST2.getNextSibling();
/* 56 */       i++;
/*    */     }
/* 58 */     return i;
/*    */   }
/*    */ 
/*    */   public int getIndexOfChild(Object paramObject1, Object paramObject2) {
/* 62 */     if ((paramObject1 == null) || (paramObject2 == null)) {
/* 63 */       throw new IllegalArgumentException("root or child is null");
/*    */     }
/* 65 */     AST localAST1 = (AST)paramObject1;
/* 66 */     AST localAST2 = localAST1.getFirstChild();
/* 67 */     if (localAST2 == null) {
/* 68 */       throw new ArrayIndexOutOfBoundsException("node has no children");
/*    */     }
/* 70 */     int i = 0;
/* 71 */     while ((localAST2 != null) && (localAST2 != paramObject2)) {
/* 72 */       localAST2 = localAST2.getNextSibling();
/* 73 */       i++;
/*    */     }
/* 75 */     if (localAST2 == paramObject2) {
/* 76 */       return i;
/*    */     }
/* 78 */     throw new NoSuchElementException("node is not a child");
/*    */   }
/*    */ 
/*    */   public Object getRoot() {
/* 82 */     return this.root;
/*    */   }
/*    */ 
/*    */   public boolean isLeaf(Object paramObject) {
/* 86 */     if (paramObject == null) {
/* 87 */       throw new IllegalArgumentException("node is null");
/*    */     }
/* 89 */     AST localAST = (AST)paramObject;
/* 90 */     return localAST.getFirstChild() == null;
/*    */   }
/*    */ 
/*    */   public void removeTreeModelListener(TreeModelListener paramTreeModelListener) {
/*    */   }
/*    */ 
/*    */   public void valueForPathChanged(TreePath paramTreePath, Object paramObject) {
/* 97 */     System.out.println("heh, who is calling this mystery method?");
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.debug.misc.JTreeASTModel
 * JD-Core Version:    0.6.2
 */