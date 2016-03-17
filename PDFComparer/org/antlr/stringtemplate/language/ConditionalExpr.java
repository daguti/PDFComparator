/*     */ package org.antlr.stringtemplate.language;
/*     */ 
/*     */ import antlr.RecognitionException;
/*     */ import antlr.collections.AST;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.antlr.stringtemplate.StringTemplate;
/*     */ import org.antlr.stringtemplate.StringTemplateWriter;
/*     */ 
/*     */ public class ConditionalExpr extends ASTExpr
/*     */ {
/*  41 */   StringTemplate subtemplate = null;
/*  42 */   List elseIfSubtemplates = null;
/*  43 */   StringTemplate elseSubtemplate = null;
/*     */ 
/*     */   public ConditionalExpr(StringTemplate enclosingTemplate, AST tree)
/*     */   {
/*  51 */     super(enclosingTemplate, tree, null);
/*     */   }
/*     */ 
/*     */   public void setSubtemplate(StringTemplate subtemplate) {
/*  55 */     this.subtemplate = subtemplate;
/*     */   }
/*     */ 
/*     */   public void addElseIfSubtemplate(final ASTExpr conditionalTree, final StringTemplate subtemplate)
/*     */   {
/*  61 */     if (this.elseIfSubtemplates == null) {
/*  62 */       this.elseIfSubtemplates = new ArrayList();
/*     */     }
/*  64 */     ElseIfClauseData d = new ElseIfClauseData()
/*     */     {
/*     */       private final ASTExpr val$conditionalTree;
/*     */       private final StringTemplate val$subtemplate;
/*     */     };
/*  68 */     this.elseIfSubtemplates.add(d);
/*     */   }
/*     */ 
/*     */   public StringTemplate getSubtemplate() {
/*  72 */     return this.subtemplate;
/*     */   }
/*     */ 
/*     */   public StringTemplate getElseSubtemplate() {
/*  76 */     return this.elseSubtemplate;
/*     */   }
/*     */ 
/*     */   public void setElseSubtemplate(StringTemplate elseSubtemplate) {
/*  80 */     this.elseSubtemplate = elseSubtemplate;
/*     */   }
/*     */ 
/*     */   public int write(StringTemplate self, StringTemplateWriter out)
/*     */     throws IOException
/*     */   {
/*  88 */     if ((this.exprTree == null) || (self == null) || (out == null)) {
/*  89 */       return 0;
/*     */     }
/*     */ 
/*  92 */     ActionEvaluator eval = new ActionEvaluator(self, this, out);
/*     */ 
/*  94 */     int n = 0;
/*     */     try {
/*  96 */       boolean testedTrue = false;
/*     */ 
/*  98 */       AST cond = this.exprTree.getFirstChild();
/*  99 */       boolean includeSubtemplate = eval.ifCondition(cond);
/*     */ 
/* 102 */       if (includeSubtemplate) {
/* 103 */         n = writeSubTemplate(self, out, this.subtemplate);
/* 104 */         testedTrue = true;
/*     */       }
/* 107 */       else if ((this.elseIfSubtemplates != null) && (this.elseIfSubtemplates.size() > 0)) {
/* 108 */         for (int i = 0; i < this.elseIfSubtemplates.size(); i++) {
/* 109 */           ElseIfClauseData elseIfClause = (ElseIfClauseData)this.elseIfSubtemplates.get(i);
/*     */ 
/* 111 */           includeSubtemplate = eval.ifCondition(elseIfClause.expr.exprTree);
/* 112 */           if (includeSubtemplate) {
/* 113 */             writeSubTemplate(self, out, elseIfClause.st);
/* 114 */             testedTrue = true;
/* 115 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 120 */       if ((!testedTrue) && (this.elseSubtemplate != null))
/*     */       {
/* 122 */         StringTemplate s = this.elseSubtemplate.getInstanceOf();
/* 123 */         s.setEnclosingInstance(self);
/* 124 */         s.setGroup(self.getGroup());
/* 125 */         s.setNativeGroup(self.getNativeGroup());
/* 126 */         n = s.write(out);
/*     */       }
/*     */ 
/* 129 */       if ((!testedTrue) && (this.elseSubtemplate == null)) n = -1; 
/*     */     }
/*     */     catch (RecognitionException re)
/*     */     {
/* 132 */       self.error("can't evaluate tree: " + this.exprTree.toStringList(), re);
/*     */     }
/* 134 */     return n;
/*     */   }
/*     */ 
/*     */   protected int writeSubTemplate(StringTemplate self, StringTemplateWriter out, StringTemplate subtemplate)
/*     */     throws IOException
/*     */   {
/* 148 */     StringTemplate s = subtemplate.getInstanceOf();
/* 149 */     s.setEnclosingInstance(self);
/*     */ 
/* 152 */     s.setGroup(self.getGroup());
/* 153 */     s.setNativeGroup(self.getNativeGroup());
/* 154 */     return s.write(out);
/*     */   }
/*     */ 
/*     */   protected static class ElseIfClauseData
/*     */   {
/*     */     ASTExpr expr;
/*     */     StringTemplate st;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.stringtemplate.language.ConditionalExpr
 * JD-Core Version:    0.6.2
 */