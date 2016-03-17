/*     */ package org.antlr.analysis;
/*     */ 
/*     */ import org.antlr.misc.Utils;
/*     */ 
/*     */ public class NFAConfiguration
/*     */ {
/*     */   public int state;
/*     */   public int alt;
/*     */   public NFAContext context;
/*  57 */   public SemanticContext semanticContext = SemanticContext.EMPTY_SEMANTIC_CONTEXT;
/*     */   protected boolean resolved;
/*     */   protected boolean resolveWithPredicate;
/*     */   protected int numberEpsilonTransitionsEmanatingFromState;
/*     */   protected boolean singleAtomTransitionEmanating;
/*     */ 
/*     */   public NFAConfiguration(int state, int alt, NFAContext context, SemanticContext semanticContext)
/*     */   {
/*  94 */     this.state = state;
/*  95 */     this.alt = alt;
/*  96 */     this.context = context;
/*  97 */     this.semanticContext = semanticContext;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object o)
/*     */   {
/* 108 */     if (o == null) {
/* 109 */       return false;
/*     */     }
/* 111 */     NFAConfiguration other = (NFAConfiguration)o;
/* 112 */     return (this.state == other.state) && (this.alt == other.alt) && (this.context.equals(other.context)) && (this.semanticContext.equals(other.semanticContext));
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 119 */     int h = this.state + this.alt + this.context.hashCode();
/* 120 */     return h;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 124 */     return toString(true);
/*     */   }
/*     */ 
/*     */   public String toString(boolean showAlt) {
/* 128 */     StringBuffer buf = new StringBuffer();
/* 129 */     buf.append(this.state);
/* 130 */     if (showAlt) {
/* 131 */       buf.append("|");
/* 132 */       buf.append(this.alt);
/*     */     }
/* 134 */     if (this.context.parent != null) {
/* 135 */       buf.append("|");
/* 136 */       buf.append(this.context);
/*     */     }
/* 138 */     if ((this.semanticContext != null) && (this.semanticContext != SemanticContext.EMPTY_SEMANTIC_CONTEXT))
/*     */     {
/* 140 */       buf.append("|");
/* 141 */       String escQuote = Utils.replace(this.semanticContext.toString(), "\"", "\\\"");
/* 142 */       buf.append(escQuote);
/*     */     }
/* 144 */     if (this.resolved) {
/* 145 */       buf.append("|resolved");
/*     */     }
/* 147 */     if (this.resolveWithPredicate) {
/* 148 */       buf.append("|resolveWithPredicate");
/*     */     }
/* 150 */     return buf.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.analysis.NFAConfiguration
 * JD-Core Version:    0.6.2
 */