/*     */ package org.antlr.tool;
/*     */ 
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.Stack;
/*     */ 
/*     */ public class GrammarSerializerFoo
/*     */ {
/*     */   protected DataOutputStream out;
/*     */   protected String filename;
/*     */   protected Grammar g;
/*  50 */   protected Stack streams = new Stack();
/*     */   protected ByteArrayOutputStream altBuf;
/*  52 */   protected int numElementsInAlt = 0;
/*     */ 
/*     */   public GrammarSerializerFoo(Grammar g) {
/*  55 */     this.g = g;
/*     */   }
/*     */ 
/*     */   public void open(String filename) throws IOException {
/*  59 */     this.filename = filename;
/*  60 */     FileOutputStream fos = new FileOutputStream(filename);
/*  61 */     BufferedOutputStream bos = new BufferedOutputStream(fos);
/*  62 */     this.out = new DataOutputStream(bos);
/*  63 */     writeString(this.out, "$ANTLR");
/*  64 */     this.out.writeByte(1);
/*     */   }
/*     */ 
/*     */   public void close() throws IOException {
/*  68 */     if (this.out != null) this.out.close();
/*  69 */     this.out = null;
/*     */   }
/*     */ 
/*     */   public void grammar(int grammarTokenType, String name)
/*     */   {
/*     */     try
/*     */     {
/*  86 */       this.out.writeShort(this.g.getRules().size());
/*     */     }
/*     */     catch (IOException ioe) {
/*  89 */       ErrorManager.error(1, this.filename);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void rule(String name) {
/*     */     try {
/*  95 */       this.out.writeByte(82);
/*  96 */       writeString(this.out, name);
/*     */     }
/*     */     catch (IOException ioe) {
/*  99 */       ErrorManager.error(1, this.filename);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void endRule() {
/*     */     try {
/* 105 */       this.out.writeByte(46);
/*     */     }
/*     */     catch (IOException ioe) {
/* 108 */       ErrorManager.error(1, this.filename);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void block(int nalts) {
/*     */     try {
/* 114 */       this.out.writeByte(66);
/* 115 */       this.out.writeShort(nalts);
/*     */     }
/*     */     catch (IOException ioe) {
/* 118 */       ErrorManager.error(1, this.filename);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void alt(GrammarAST alt) {
/* 123 */     this.numElementsInAlt = 0;
/*     */     try {
/* 125 */       this.out.writeByte(65);
/*     */     }
/*     */     catch (IOException ioe) {
/* 128 */       ErrorManager.error(1, this.filename);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void endAlt()
/*     */   {
/*     */     try
/*     */     {
/* 139 */       this.out.writeByte(59);
/*     */     }
/*     */     catch (IOException ioe)
/*     */     {
/* 144 */       ErrorManager.error(1, this.filename);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void ruleRef(GrammarAST t) {
/* 149 */     this.numElementsInAlt += 1;
/*     */     try {
/* 151 */       this.out.writeByte(114);
/* 152 */       this.out.writeShort(this.g.getRuleIndex(t.getText()));
/*     */     }
/*     */     catch (IOException ioe) {
/* 155 */       ErrorManager.error(1, this.filename);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void token(GrammarAST t) {
/* 160 */     this.numElementsInAlt += 1;
/*     */     try {
/* 162 */       this.out.writeByte(116);
/* 163 */       int ttype = this.g.getTokenType(t.getText());
/* 164 */       this.out.writeShort(ttype);
/*     */     }
/*     */     catch (IOException ioe) {
/* 167 */       ErrorManager.error(1, this.filename);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void charLiteral(GrammarAST t) {
/* 172 */     this.numElementsInAlt += 1;
/*     */     try {
/* 174 */       if (this.g.type != 1) {
/* 175 */         this.out.writeByte(116);
/* 176 */         int ttype = this.g.getTokenType(t.getText());
/* 177 */         this.out.writeShort(ttype);
/*     */       }
/*     */     }
/*     */     catch (IOException ioe)
/*     */     {
/* 182 */       ErrorManager.error(1, this.filename);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void wildcard(GrammarAST t) {
/* 187 */     this.numElementsInAlt += 1;
/*     */     try {
/* 189 */       this.out.writeByte(119);
/*     */     }
/*     */     catch (IOException ioe) {
/* 192 */       ErrorManager.error(1, this.filename);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void range() {
/* 197 */     this.numElementsInAlt += 1;
/*     */     try {
/* 199 */       this.out.writeByte(45);
/*     */     }
/*     */     catch (IOException ioe) {
/* 202 */       ErrorManager.error(1, this.filename);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void not() {
/*     */     try {
/* 208 */       this.out.writeByte(126);
/*     */     }
/*     */     catch (IOException ioe) {
/* 211 */       ErrorManager.error(1, this.filename);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void writeString(DataOutputStream out, String s) throws IOException {
/* 216 */     out.writeBytes(s);
/* 217 */     out.writeByte(59);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.tool.GrammarSerializerFoo
 * JD-Core Version:    0.6.2
 */