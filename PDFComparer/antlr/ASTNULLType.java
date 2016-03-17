/*     */ package antlr;
/*     */ 
/*     */ import antlr.collections.AST;
/*     */ import antlr.collections.ASTEnumeration;
/*     */ 
/*     */ public class ASTNULLType
/*     */   implements AST
/*     */ {
/*     */   public void addChild(AST paramAST)
/*     */   {
/*     */   }
/*     */ 
/*     */   public boolean equals(AST paramAST)
/*     */   {
/*  20 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean equalsList(AST paramAST) {
/*  24 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean equalsListPartial(AST paramAST) {
/*  28 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean equalsTree(AST paramAST) {
/*  32 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean equalsTreePartial(AST paramAST) {
/*  36 */     return false;
/*     */   }
/*     */ 
/*     */   public ASTEnumeration findAll(AST paramAST) {
/*  40 */     return null;
/*     */   }
/*     */ 
/*     */   public ASTEnumeration findAllPartial(AST paramAST) {
/*  44 */     return null;
/*     */   }
/*     */ 
/*     */   public AST getFirstChild() {
/*  48 */     return this;
/*     */   }
/*     */ 
/*     */   public AST getNextSibling() {
/*  52 */     return this;
/*     */   }
/*     */ 
/*     */   public String getText() {
/*  56 */     return "<ASTNULL>";
/*     */   }
/*     */ 
/*     */   public int getType() {
/*  60 */     return 3;
/*     */   }
/*     */ 
/*     */   public int getLine() {
/*  64 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getColumn() {
/*  68 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getNumberOfChildren() {
/*  72 */     return 0;
/*     */   }
/*     */ 
/*     */   public void initialize(int paramInt, String paramString) {
/*     */   }
/*     */ 
/*     */   public void initialize(AST paramAST) {
/*     */   }
/*     */ 
/*     */   public void initialize(Token paramToken) {
/*     */   }
/*     */ 
/*     */   public void setFirstChild(AST paramAST) {
/*     */   }
/*     */ 
/*     */   public void setNextSibling(AST paramAST) {
/*     */   }
/*     */ 
/*     */   public void setText(String paramString) {
/*     */   }
/*     */ 
/*     */   public void setType(int paramInt) {
/*     */   }
/*     */ 
/*     */   public String toString() {
/*  97 */     return getText();
/*     */   }
/*     */ 
/*     */   public String toStringList() {
/* 101 */     return getText();
/*     */   }
/*     */ 
/*     */   public String toStringTree() {
/* 105 */     return getText();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.ASTNULLType
 * JD-Core Version:    0.6.2
 */