/*     */ package org.antlr.runtime;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class SerializedGrammar
/*     */ {
/*     */   public static final String COOKIE = "$ANTLR";
/*     */   public static final int FORMAT_VERSION = 1;
/*     */   public String name;
/*     */   public char type;
/*     */   public List rules;
/*     */ 
/*     */   public SerializedGrammar(String filename)
/*     */     throws IOException
/*     */   {
/*  90 */     System.out.println("loading " + filename);
/*  91 */     FileInputStream fis = new FileInputStream(filename);
/*  92 */     BufferedInputStream bos = new BufferedInputStream(fis);
/*  93 */     DataInputStream in = new DataInputStream(bos);
/*  94 */     readFile(in);
/*  95 */     in.close();
/*     */   }
/*     */ 
/*     */   protected void readFile(DataInputStream in) throws IOException {
/*  99 */     String cookie = readString(in);
/* 100 */     if (!cookie.equals("$ANTLR")) throw new IOException("not a serialized grammar file");
/* 101 */     int version = in.readByte();
/* 102 */     char grammarType = (char)in.readByte();
/* 103 */     this.type = grammarType;
/* 104 */     String grammarName = readString(in);
/* 105 */     this.name = grammarName;
/* 106 */     System.out.println(grammarType + " grammar " + grammarName);
/* 107 */     int numRules = in.readShort();
/* 108 */     System.out.println("num rules = " + numRules);
/* 109 */     this.rules = readRules(in, numRules);
/*     */   }
/*     */ 
/*     */   protected List readRules(DataInputStream in, int numRules) throws IOException {
/* 113 */     List rules = new ArrayList();
/* 114 */     for (int i = 0; i < numRules; i++) {
/* 115 */       Rule r = readRule(in);
/* 116 */       rules.add(r);
/*     */     }
/* 118 */     return rules;
/*     */   }
/*     */ 
/*     */   protected Rule readRule(DataInputStream in) throws IOException {
/* 122 */     byte R = in.readByte();
/* 123 */     if (R != 82) throw new IOException("missing R on start of rule");
/* 124 */     String name = readString(in);
/* 125 */     System.out.println("rule: " + name);
/* 126 */     byte B = in.readByte();
/* 127 */     Block b = readBlock(in);
/* 128 */     byte period = in.readByte();
/* 129 */     if (period != 46) throw new IOException("missing . on end of rule");
/* 130 */     return new Rule(name, b);
/*     */   }
/*     */ 
/*     */   protected Block readBlock(DataInputStream in) throws IOException {
/* 134 */     int nalts = in.readShort();
/* 135 */     List[] alts = new List[nalts];
/*     */ 
/* 137 */     for (int i = 0; i < nalts; i++) {
/* 138 */       List alt = readAlt(in);
/* 139 */       alts[i] = alt;
/*     */     }
/*     */ 
/* 142 */     return new Block(alts);
/*     */   }
/*     */ 
/*     */   protected List readAlt(DataInputStream in) throws IOException {
/* 146 */     List alt = new ArrayList();
/* 147 */     byte A = in.readByte();
/* 148 */     if (A != 65) throw new IOException("missing A on start of alt");
/* 149 */     byte cmd = in.readByte();
/* 150 */     while (cmd != 59)
/*     */     {
/*     */       int to;
/*     */       int notThisTokenType;
/* 151 */       switch (cmd) {
/*     */       case 116:
/* 153 */         int ttype = in.readShort();
/* 154 */         alt.add(new TokenRef(ttype));
/*     */ 
/* 156 */         break;
/*     */       case 114:
/* 158 */         int ruleIndex = in.readShort();
/* 159 */         alt.add(new RuleRef(ruleIndex));
/*     */ 
/* 161 */         break;
/*     */       case 46:
/* 163 */         break;
/*     */       case 45:
/* 165 */         int from = in.readChar();
/* 166 */         to = in.readChar();
/* 167 */         break;
/*     */       case 126:
/* 169 */         notThisTokenType = in.readShort();
/* 170 */         break;
/*     */       case 66:
/* 172 */         Block b = readBlock(in);
/* 173 */         alt.add(b);
/*     */       }
/*     */ 
/* 176 */       cmd = in.readByte();
/*     */     }
/*     */ 
/* 179 */     return alt;
/*     */   }
/*     */ 
/*     */   protected String readString(DataInputStream in) throws IOException {
/* 183 */     byte c = in.readByte();
/* 184 */     StringBuffer buf = new StringBuffer();
/* 185 */     while (c != 59) {
/* 186 */       buf.append((char)c);
/* 187 */       c = in.readByte();
/*     */     }
/* 189 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 193 */     StringBuffer buf = new StringBuffer();
/* 194 */     buf.append(this.type + " grammar " + this.name);
/* 195 */     buf.append(this.rules);
/* 196 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   class RuleRef
/*     */   {
/*     */     int ruleIndex;
/*     */ 
/*     */     public RuleRef(int ruleIndex)
/*     */     {
/*  85 */       this.ruleIndex = ruleIndex; } 
/*  86 */     public String toString() { return String.valueOf(this.ruleIndex); }
/*     */ 
/*     */   }
/*     */ 
/*     */   class TokenRef
/*     */   {
/*     */     int ttype;
/*     */ 
/*     */     public TokenRef(int ttype)
/*     */     {
/*  79 */       this.ttype = ttype; } 
/*  80 */     public String toString() { return String.valueOf(this.ttype); }
/*     */ 
/*     */   }
/*     */ 
/*     */   class Block
/*     */   {
/*     */     List[] alts;
/*     */ 
/*     */     public Block(List[] alts)
/*     */     {
/*  62 */       this.alts = alts;
/*     */     }
/*     */     public String toString() {
/*  65 */       StringBuffer buf = new StringBuffer();
/*  66 */       buf.append("(");
/*  67 */       for (int i = 0; i < this.alts.length; i++) {
/*  68 */         List alt = this.alts[i];
/*  69 */         if (i > 0) buf.append("|");
/*  70 */         buf.append(alt.toString());
/*     */       }
/*  72 */       buf.append(")");
/*  73 */       return buf.toString();
/*     */     }
/*     */   }
/*     */ 
/*     */   class Rule
/*     */   {
/*     */     String name;
/*     */     SerializedGrammar.Block block;
/*     */ 
/*     */     public Rule(String name, SerializedGrammar.Block block)
/*     */     {
/*  51 */       this.name = name;
/*  52 */       this.block = block;
/*     */     }
/*     */     public String toString() {
/*  55 */       return this.name + ":" + this.block;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.SerializedGrammar
 * JD-Core Version:    0.6.2
 */