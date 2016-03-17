/*     */ package org.stringtemplate.v4.compiler;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.stringtemplate.v4.misc.Interval;
/*     */ import org.stringtemplate.v4.misc.Misc;
/*     */ 
/*     */ public class BytecodeDisassembler
/*     */ {
/*     */   CompiledST code;
/*     */ 
/*     */   public BytecodeDisassembler(CompiledST code)
/*     */   {
/*  39 */     this.code = code;
/*     */   }
/*     */   public String instrs() {
/*  42 */     StringBuilder buf = new StringBuilder();
/*  43 */     int ip = 0;
/*  44 */     while (ip < this.code.codeSize) {
/*  45 */       if (ip > 0) buf.append(", ");
/*  46 */       int opcode = this.code.instrs[ip];
/*  47 */       Bytecode.Instruction I = Bytecode.instructions[opcode];
/*  48 */       buf.append(I.name);
/*  49 */       ip++;
/*  50 */       for (int opnd = 0; opnd < I.nopnds; opnd++) {
/*  51 */         buf.append(' ');
/*  52 */         buf.append(getShort(this.code.instrs, ip));
/*  53 */         ip += 2;
/*     */       }
/*     */     }
/*  56 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public String disassemble() {
/*  60 */     StringBuilder buf = new StringBuilder();
/*  61 */     int i = 0;
/*  62 */     while (i < this.code.codeSize) {
/*  63 */       i = disassembleInstruction(buf, i);
/*  64 */       buf.append('\n');
/*     */     }
/*  66 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public int disassembleInstruction(StringBuilder buf, int ip) {
/*  70 */     int opcode = this.code.instrs[ip];
/*  71 */     if (ip >= this.code.codeSize) {
/*  72 */       throw new IllegalArgumentException("ip out of range: " + ip);
/*     */     }
/*  74 */     Bytecode.Instruction I = Bytecode.instructions[opcode];
/*     */ 
/*  76 */     if (I == null) {
/*  77 */       throw new IllegalArgumentException("no such instruction " + opcode + " at address " + ip);
/*     */     }
/*     */ 
/*  80 */     String instrName = I.name;
/*  81 */     buf.append(String.format("%04d:\t%-14s", new Object[] { Integer.valueOf(ip), instrName }));
/*  82 */     ip++;
/*  83 */     if (I.nopnds == 0) {
/*  84 */       buf.append("  ");
/*  85 */       return ip;
/*     */     }
/*  87 */     List operands = new ArrayList();
/*  88 */     for (int i = 0; i < I.nopnds; i++) {
/*  89 */       int opnd = getShort(this.code.instrs, ip);
/*  90 */       ip += 2;
/*  91 */       switch (1.$SwitchMap$org$stringtemplate$v4$compiler$Bytecode$OperandType[I.type[i].ordinal()]) {
/*     */       case 1:
/*  93 */         operands.add(showConstPoolOperand(opnd));
/*  94 */         break;
/*     */       case 2:
/*     */       case 3:
/*  97 */         operands.add(String.valueOf(opnd));
/*  98 */         break;
/*     */       default:
/* 100 */         operands.add(String.valueOf(opnd));
/*     */       }
/*     */     }
/*     */ 
/* 104 */     for (int i = 0; i < operands.size(); i++) {
/* 105 */       String s = (String)operands.get(i);
/* 106 */       if (i > 0) buf.append(", ");
/* 107 */       buf.append(s);
/*     */     }
/* 109 */     return ip;
/*     */   }
/*     */ 
/*     */   private String showConstPoolOperand(int poolIndex) {
/* 113 */     StringBuffer buf = new StringBuffer();
/* 114 */     buf.append("#");
/* 115 */     buf.append(poolIndex);
/* 116 */     String s = "<bad string index>";
/* 117 */     if (poolIndex < this.code.strings.length) {
/* 118 */       if (this.code.strings[poolIndex] == null) { s = "null";
/*     */       } else {
/* 120 */         s = this.code.strings[poolIndex].toString();
/* 121 */         if ((this.code.strings[poolIndex] instanceof String)) {
/* 122 */           s = Misc.replaceEscapes(s);
/* 123 */           s = '"' + s + '"';
/*     */         }
/*     */       }
/*     */     }
/* 127 */     buf.append(":");
/* 128 */     buf.append(s);
/* 129 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public static int getShort(byte[] memory, int index) {
/* 133 */     int b1 = memory[index] & 0xFF;
/* 134 */     int b2 = memory[(index + 1)] & 0xFF;
/* 135 */     int word = b1 << 8 | b2;
/* 136 */     return word;
/*     */   }
/*     */ 
/*     */   public String strings() {
/* 140 */     StringBuffer buf = new StringBuffer();
/* 141 */     int addr = 0;
/* 142 */     if (this.code.strings != null) {
/* 143 */       for (Object o : this.code.strings) {
/* 144 */         if ((o instanceof String)) {
/* 145 */           String s = (String)o;
/* 146 */           s = Misc.replaceEscapes(s);
/* 147 */           buf.append(String.format("%04d: \"%s\"\n", new Object[] { Integer.valueOf(addr), s }));
/*     */         }
/*     */         else {
/* 150 */           buf.append(String.format("%04d: %s\n", new Object[] { Integer.valueOf(addr), o }));
/*     */         }
/* 152 */         addr++;
/*     */       }
/*     */     }
/* 155 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public String sourceMap() {
/* 159 */     StringBuffer buf = new StringBuffer();
/* 160 */     int addr = 0;
/* 161 */     for (Interval I : this.code.sourceMap) {
/* 162 */       if (I != null) {
/* 163 */         String chunk = this.code.template.substring(I.a, I.b + 1);
/* 164 */         buf.append(String.format("%04d: %s\t\"%s\"\n", new Object[] { Integer.valueOf(addr), I, chunk }));
/*     */       }
/* 166 */       addr++;
/*     */     }
/* 168 */     return buf.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.compiler.BytecodeDisassembler
 * JD-Core Version:    0.6.2
 */