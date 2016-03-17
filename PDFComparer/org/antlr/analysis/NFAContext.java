/*     */ package org.antlr.analysis;
/*     */ 
/*     */ public class NFAContext
/*     */ {
/*  99 */   public static int MAX_SAME_RULE_INVOCATIONS_PER_NFA_CONFIG_STACK = 4;
/*     */   public NFAContext parent;
/*     */   public NFAState invokingState;
/*     */   protected int cachedHashCode;
/*     */ 
/*     */   public NFAContext(NFAContext parent, NFAState invokingState)
/*     */   {
/* 120 */     this.parent = parent;
/* 121 */     this.invokingState = invokingState;
/* 122 */     if (invokingState != null) {
/* 123 */       this.cachedHashCode = invokingState.stateNumber;
/*     */     }
/* 125 */     if (parent != null)
/* 126 */       this.cachedHashCode += parent.cachedHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object o)
/*     */   {
/* 139 */     NFAContext other = (NFAContext)o;
/* 140 */     if (this.cachedHashCode != other.cachedHashCode) {
/* 141 */       return false;
/*     */     }
/* 143 */     if (this == other) {
/* 144 */       return true;
/*     */     }
/*     */ 
/* 147 */     NFAContext sp = this;
/* 148 */     while ((sp.parent != null) && (other.parent != null)) {
/* 149 */       if (sp.invokingState != other.invokingState) {
/* 150 */         return false;
/*     */       }
/* 152 */       sp = sp.parent;
/* 153 */       other = other.parent;
/*     */     }
/* 155 */     if ((sp.parent != null) || (other.parent != null)) {
/* 156 */       return false;
/*     */     }
/* 158 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean conflictsWith(NFAContext other)
/*     */   {
/* 184 */     return suffix(other);
/*     */   }
/*     */ 
/*     */   protected boolean suffix(NFAContext other)
/*     */   {
/* 208 */     NFAContext sp = this;
/*     */ 
/* 210 */     while ((sp.parent != null) && (other.parent != null)) {
/* 211 */       if (sp.invokingState != other.invokingState) {
/* 212 */         return false;
/*     */       }
/* 214 */       sp = sp.parent;
/* 215 */       other = other.parent;
/*     */     }
/*     */ 
/* 218 */     return true;
/*     */   }
/*     */ 
/*     */   public int recursionDepthEmanatingFromState(int state)
/*     */   {
/* 250 */     NFAContext sp = this;
/* 251 */     int n = 0;
/*     */ 
/* 253 */     while (sp.parent != null) {
/* 254 */       if (sp.invokingState.stateNumber == state) {
/* 255 */         n++;
/*     */       }
/* 257 */       sp = sp.parent;
/*     */     }
/* 259 */     return n;
/*     */   }
/*     */ 
/*     */   public int hashCode() {
/* 263 */     return this.cachedHashCode;
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 279 */     return this.parent == null;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 283 */     StringBuffer buf = new StringBuffer();
/* 284 */     NFAContext sp = this;
/* 285 */     buf.append("[");
/* 286 */     while (sp.parent != null) {
/* 287 */       buf.append(sp.invokingState.stateNumber);
/* 288 */       buf.append(" ");
/* 289 */       sp = sp.parent;
/*     */     }
/* 291 */     buf.append("$]");
/* 292 */     return buf.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.analysis.NFAContext
 * JD-Core Version:    0.6.2
 */