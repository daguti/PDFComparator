/*     */ package org.stringtemplate.v4.compiler;
/*     */ 
/*     */ import org.antlr.runtime.Token;
/*     */ 
/*     */ public class FormalArgument
/*     */ {
/*     */   public String name;
/*     */   public int index;
/*     */   public Token defaultValueToken;
/*     */   public Object defaultValue;
/*     */   public CompiledST compiledDefaultValue;
/*     */ 
/*     */   public FormalArgument(String name)
/*     */   {
/*  74 */     this.name = name;
/*     */   }
/*     */   public FormalArgument(String name, Token defaultValueToken) {
/*  77 */     this.name = name;
/*  78 */     this.defaultValueToken = defaultValueToken;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  94 */     return this.name.hashCode() + this.defaultValueToken.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object o) {
/*  98 */     if ((o == null) || (!(o instanceof FormalArgument))) {
/*  99 */       return false;
/*     */     }
/* 101 */     FormalArgument other = (FormalArgument)o;
/* 102 */     if (!this.name.equals(other.name)) {
/* 103 */       return false;
/*     */     }
/*     */ 
/* 106 */     return ((this.defaultValueToken == null) || (other.defaultValueToken != null)) && ((this.defaultValueToken != null) || (other.defaultValueToken == null));
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 111 */     if (this.defaultValueToken != null) return this.name + "=" + this.defaultValueToken.getText();
/* 112 */     return this.name;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.compiler.FormalArgument
 * JD-Core Version:    0.6.2
 */