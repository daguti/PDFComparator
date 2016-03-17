/*     */ package org.stringtemplate.v4.compiler;
/*     */ 
/*     */ public class Bytecode
/*     */ {
/*     */   public static final int MAX_OPNDS = 2;
/*     */   public static final int OPND_SIZE_IN_BYTES = 2;
/*     */   public static final short INSTR_LOAD_STR = 1;
/*     */   public static final short INSTR_LOAD_ATTR = 2;
/*     */   public static final short INSTR_LOAD_LOCAL = 3;
/*     */   public static final short INSTR_LOAD_PROP = 4;
/*     */   public static final short INSTR_LOAD_PROP_IND = 5;
/*     */   public static final short INSTR_STORE_OPTION = 6;
/*     */   public static final short INSTR_STORE_ARG = 7;
/*     */   public static final short INSTR_NEW = 8;
/*     */   public static final short INSTR_NEW_IND = 9;
/*     */   public static final short INSTR_NEW_BOX_ARGS = 10;
/*     */   public static final short INSTR_SUPER_NEW = 11;
/*     */   public static final short INSTR_SUPER_NEW_BOX_ARGS = 12;
/*     */   public static final short INSTR_WRITE = 13;
/*     */   public static final short INSTR_WRITE_OPT = 14;
/*     */   public static final short INSTR_MAP = 15;
/*     */   public static final short INSTR_ROT_MAP = 16;
/*     */   public static final short INSTR_ZIP_MAP = 17;
/*     */   public static final short INSTR_BR = 18;
/*     */   public static final short INSTR_BRF = 19;
/*     */   public static final short INSTR_OPTIONS = 20;
/*     */   public static final short INSTR_ARGS = 21;
/*     */   public static final short INSTR_PASSTHRU = 22;
/*     */   public static final short INSTR_LIST = 24;
/*     */   public static final short INSTR_ADD = 25;
/*     */   public static final short INSTR_TOSTR = 26;
/*     */   public static final short INSTR_FIRST = 27;
/*     */   public static final short INSTR_LAST = 28;
/*     */   public static final short INSTR_REST = 29;
/*     */   public static final short INSTR_TRUNC = 30;
/*     */   public static final short INSTR_STRIP = 31;
/*     */   public static final short INSTR_TRIM = 32;
/*     */   public static final short INSTR_LENGTH = 33;
/*     */   public static final short INSTR_STRLEN = 34;
/*     */   public static final short INSTR_REVERSE = 35;
/*     */   public static final short INSTR_NOT = 36;
/*     */   public static final short INSTR_OR = 37;
/*     */   public static final short INSTR_AND = 38;
/*     */   public static final short INSTR_INDENT = 39;
/*     */   public static final short INSTR_DEDENT = 40;
/*     */   public static final short INSTR_NEWLINE = 41;
/*     */   public static final short INSTR_NOOP = 42;
/*     */   public static final short INSTR_POP = 43;
/*     */   public static final short INSTR_NULL = 44;
/*     */   public static final short INSTR_TRUE = 45;
/*     */   public static final short INSTR_FALSE = 46;
/*     */   public static final short INSTR_WRITE_STR = 47;
/*     */   public static final short INSTR_WRITE_LOCAL = 48;
/*     */   public static final short MAX_BYTECODE = 48;
/* 118 */   public static Instruction[] instructions = { null, new Instruction("load_str", OperandType.STRING), new Instruction("load_attr", OperandType.STRING), new Instruction("load_local", OperandType.INT), new Instruction("load_prop", OperandType.STRING), new Instruction("load_prop_ind"), new Instruction("store_option", OperandType.INT), new Instruction("store_arg", OperandType.STRING), new Instruction("new", OperandType.STRING, OperandType.INT), new Instruction("new_ind", OperandType.INT), new Instruction("new_box_args", OperandType.STRING), new Instruction("super_new", OperandType.STRING, OperandType.INT), new Instruction("super_new_box_args", OperandType.STRING), new Instruction("write"), new Instruction("write_opt"), new Instruction("map"), new Instruction("rot_map", OperandType.INT), new Instruction("zip_map", OperandType.INT), new Instruction("br", OperandType.ADDR), new Instruction("brf", OperandType.ADDR), new Instruction("options"), new Instruction("args"), new Instruction("passthru", OperandType.STRING), null, new Instruction("list"), new Instruction("add"), new Instruction("tostr"), new Instruction("first"), new Instruction("last"), new Instruction("rest"), new Instruction("trunc"), new Instruction("strip"), new Instruction("trim"), new Instruction("length"), new Instruction("strlen"), new Instruction("reverse"), new Instruction("not"), new Instruction("or"), new Instruction("and"), new Instruction("indent", OperandType.STRING), new Instruction("dedent"), new Instruction("newline"), new Instruction("noop"), new Instruction("pop"), new Instruction("null"), new Instruction("true"), new Instruction("false"), new Instruction("write_str", OperandType.STRING), new Instruction("write_local", OperandType.INT) };
/*     */ 
/*     */   public static class Instruction
/*     */   {
/*     */     public String name;
/*  38 */     public Bytecode.OperandType[] type = new Bytecode.OperandType[2];
/*  39 */     public int nopnds = 0;
/*     */ 
/*  41 */     public Instruction(String name) { this(name, Bytecode.OperandType.NONE, Bytecode.OperandType.NONE); this.nopnds = 0; }
/*     */ 
/*     */     public Instruction(String name, Bytecode.OperandType a) {
/*  44 */       this(name, a, Bytecode.OperandType.NONE); this.nopnds = 1;
/*     */     }
/*     */     public Instruction(String name, Bytecode.OperandType a, Bytecode.OperandType b) {
/*  47 */       this.name = name;
/*  48 */       this.type[0] = a;
/*  49 */       this.type[1] = b;
/*  50 */       this.nopnds = 2;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static enum OperandType
/*     */   {
/*  34 */     NONE, STRING, ADDR, INT;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.compiler.Bytecode
 * JD-Core Version:    0.6.2
 */