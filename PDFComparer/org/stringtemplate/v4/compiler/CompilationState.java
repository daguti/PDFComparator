/*     */ package org.stringtemplate.v4.compiler;
/*     */ 
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.antlr.runtime.CommonToken;
/*     */ import org.antlr.runtime.Token;
/*     */ import org.antlr.runtime.TokenStream;
/*     */ import org.antlr.runtime.tree.CommonTree;
/*     */ import org.stringtemplate.v4.Interpreter;
/*     */ import org.stringtemplate.v4.Interpreter.Option;
/*     */ import org.stringtemplate.v4.misc.ErrorManager;
/*     */ import org.stringtemplate.v4.misc.ErrorType;
/*     */ import org.stringtemplate.v4.misc.Interval;
/*     */ import org.stringtemplate.v4.misc.Misc;
/*     */ 
/*     */ public class CompilationState
/*     */ {
/*  41 */   CompiledST impl = new CompiledST();
/*     */ 
/*  44 */   StringTable stringtable = new StringTable();
/*     */ 
/*  49 */   int ip = 0;
/*     */   TokenStream tokens;
/*     */   ErrorManager errMgr;
/*     */ 
/*     */   public CompilationState(ErrorManager errMgr, String name, TokenStream tokens)
/*     */   {
/*  56 */     this.errMgr = errMgr;
/*  57 */     this.tokens = tokens;
/*  58 */     this.impl.name = name;
/*  59 */     this.impl.prefix = Misc.getPrefix(name);
/*     */   }
/*     */   public int defineString(String s) {
/*  62 */     return this.stringtable.add(s);
/*     */   }
/*     */   public void refAttr(Token templateToken, CommonTree id) {
/*  65 */     String name = id.getText();
/*  66 */     if ((this.impl.formalArguments != null) && (this.impl.formalArguments.get(name) != null)) {
/*  67 */       FormalArgument arg = (FormalArgument)this.impl.formalArguments.get(name);
/*  68 */       int index = arg.index;
/*  69 */       emit1(id, (short)3, index);
/*     */     }
/*  72 */     else if (Interpreter.predefinedAnonSubtemplateAttributes.contains(name)) {
/*  73 */       this.errMgr.compileTimeError(ErrorType.REF_TO_IMPLICIT_ATTRIBUTE_OUT_OF_SCOPE, templateToken, id.token);
/*     */ 
/*  75 */       emit(id, (short)44);
/*     */     }
/*     */     else {
/*  78 */       emit1(id, (short)2, name);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setOption(CommonTree id)
/*     */   {
/*  84 */     Interpreter.Option O = (Interpreter.Option)Compiler.supportedOptions.get(id.getText());
/*  85 */     emit1(id, (short)6, O.ordinal());
/*     */   }
/*     */ 
/*     */   public void func(Token templateToken, CommonTree id) {
/*  89 */     Short funcBytecode = (Short)Compiler.funcs.get(id.getText());
/*  90 */     if (funcBytecode == null) {
/*  91 */       this.errMgr.compileTimeError(ErrorType.NO_SUCH_FUNCTION, templateToken, id.token);
/*  92 */       emit(id, (short)43);
/*     */     }
/*     */     else {
/*  95 */       emit(id, funcBytecode.shortValue());
/*     */     }
/*     */   }
/*     */ 
/*  99 */   public void emit(short opcode) { emit(null, opcode); }
/*     */ 
/*     */   public void emit(CommonTree opAST, short opcode) {
/* 102 */     ensureCapacity(1);
/* 103 */     if (opAST != null) {
/* 104 */       int i = opAST.getTokenStartIndex();
/* 105 */       int j = opAST.getTokenStopIndex();
/* 106 */       int p = ((CommonToken)this.tokens.get(i)).getStartIndex();
/* 107 */       int q = ((CommonToken)this.tokens.get(j)).getStopIndex();
/* 108 */       if ((p >= 0) && (q >= 0)) this.impl.sourceMap[this.ip] = new Interval(p, q);
/*     */     }
/* 110 */     this.impl.instrs[(this.ip++)] = ((byte)opcode);
/*     */   }
/*     */ 
/*     */   public void emit1(CommonTree opAST, short opcode, int arg) {
/* 114 */     emit(opAST, opcode);
/* 115 */     ensureCapacity(2);
/* 116 */     writeShort(this.impl.instrs, this.ip, (short)arg);
/* 117 */     this.ip += 2;
/*     */   }
/*     */ 
/*     */   public void emit2(CommonTree opAST, short opcode, int arg, int arg2) {
/* 121 */     emit(opAST, opcode);
/* 122 */     ensureCapacity(4);
/* 123 */     writeShort(this.impl.instrs, this.ip, (short)arg);
/* 124 */     this.ip += 2;
/* 125 */     writeShort(this.impl.instrs, this.ip, (short)arg2);
/* 126 */     this.ip += 2;
/*     */   }
/*     */ 
/*     */   public void emit2(CommonTree opAST, short opcode, String s, int arg2) {
/* 130 */     int i = defineString(s);
/* 131 */     emit2(opAST, opcode, i, arg2);
/*     */   }
/*     */ 
/*     */   public void emit1(CommonTree opAST, short opcode, String s) {
/* 135 */     int i = defineString(s);
/* 136 */     emit1(opAST, opcode, i);
/*     */   }
/*     */ 
/*     */   public void insert(int addr, short opcode, String s)
/*     */   {
/* 141 */     ensureCapacity(3);
/* 142 */     int instrSize = 3;
/* 143 */     System.arraycopy(this.impl.instrs, addr, this.impl.instrs, addr + instrSize, this.ip - addr);
/*     */ 
/* 146 */     int save = this.ip;
/* 147 */     this.ip = addr;
/* 148 */     emit1(null, opcode, s);
/* 149 */     this.ip = (save + instrSize);
/*     */ 
/* 152 */     int a = addr + instrSize;
/* 153 */     while (a < this.ip) {
/* 154 */       byte op = this.impl.instrs[a];
/* 155 */       Bytecode.Instruction I = Bytecode.instructions[op];
/* 156 */       if ((op == 18) || (op == 19)) {
/* 157 */         int opnd = BytecodeDisassembler.getShort(this.impl.instrs, a + 1);
/* 158 */         writeShort(this.impl.instrs, a + 1, (short)(opnd + instrSize));
/*     */       }
/* 160 */       a += I.nopnds * 2 + 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(int addr, short value)
/*     */   {
/* 166 */     writeShort(this.impl.instrs, addr, value);
/*     */   }
/*     */ 
/*     */   protected void ensureCapacity(int n) {
/* 170 */     if (this.ip + n >= this.impl.instrs.length) {
/* 171 */       byte[] c = new byte[this.impl.instrs.length * 2];
/* 172 */       System.arraycopy(this.impl.instrs, 0, c, 0, this.impl.instrs.length);
/* 173 */       this.impl.instrs = c;
/* 174 */       Interval[] sm = new Interval[this.impl.sourceMap.length * 2];
/* 175 */       System.arraycopy(this.impl.sourceMap, 0, sm, 0, this.impl.sourceMap.length);
/* 176 */       this.impl.sourceMap = sm;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void indent(CommonTree indent) {
/* 181 */     emit1(indent, (short)39, indent.getText());
/*     */   }
/*     */ 
/*     */   public static void writeShort(byte[] memory, int index, short value)
/*     */   {
/* 188 */     memory[(index + 0)] = ((byte)(value >> 8 & 0xFF));
/* 189 */     memory[(index + 1)] = ((byte)(value & 0xFF));
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.compiler.CompilationState
 * JD-Core Version:    0.6.2
 */