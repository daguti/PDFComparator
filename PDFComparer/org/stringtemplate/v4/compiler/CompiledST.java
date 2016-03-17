/*     */ package org.stringtemplate.v4.compiler;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.antlr.runtime.Token;
/*     */ import org.antlr.runtime.TokenStream;
/*     */ import org.antlr.runtime.tree.CommonTree;
/*     */ import org.stringtemplate.v4.ST.RegionType;
/*     */ import org.stringtemplate.v4.STGroup;
/*     */ import org.stringtemplate.v4.misc.Interval;
/*     */ import org.stringtemplate.v4.misc.Misc;
/*     */ 
/*     */ public class CompiledST
/*     */ {
/*     */   public String name;
/*  62 */   public String prefix = "/";
/*     */   public String template;
/*     */   public Token templateDefStartToken;
/*     */   public TokenStream tokens;
/*     */   public CommonTree ast;
/*     */   public Map<String, FormalArgument> formalArguments;
/*     */   public boolean hasFormalArgs;
/*     */   public int numberOfArgsWithDefaultValues;
/*     */   public List<CompiledST> implicitlyDefinedTemplates;
/*  92 */   public STGroup nativeGroup = STGroup.defaultGroup;
/*     */   public boolean isRegion;
/*     */   public ST.RegionType regionDefType;
/*     */   public boolean isAnonSubtemplate;
/*     */   public String[] strings;
/*     */   public byte[] instrs;
/*     */   public int codeSize;
/*     */   public Interval[] sourceMap;
/*     */ 
/*     */   public CompiledST()
/*     */   {
/* 117 */     this.instrs = new byte[15];
/* 118 */     this.sourceMap = new Interval[15];
/* 119 */     this.template = "";
/*     */   }
/*     */ 
/*     */   public void addImplicitlyDefinedTemplate(CompiledST sub) {
/* 123 */     sub.prefix = this.prefix;
/* 124 */     if (sub.name.charAt(0) != '/') sub.name = (sub.prefix + sub.name);
/* 125 */     if (this.implicitlyDefinedTemplates == null) {
/* 126 */       this.implicitlyDefinedTemplates = new ArrayList();
/*     */     }
/* 128 */     this.implicitlyDefinedTemplates.add(sub);
/*     */   }
/*     */ 
/*     */   public void defineArgDefaultValueTemplates(STGroup group) {
/* 132 */     if (this.formalArguments == null) return;
/* 133 */     for (String a : this.formalArguments.keySet()) {
/* 134 */       FormalArgument fa = (FormalArgument)this.formalArguments.get(a);
/* 135 */       if (fa.defaultValueToken != null) {
/* 136 */         this.numberOfArgsWithDefaultValues += 1;
/* 137 */         if (fa.defaultValueToken.getType() == 4) {
/* 138 */           String argSTname = fa.name + "_default_value";
/* 139 */           Compiler c2 = new Compiler(group);
/* 140 */           String defArgTemplate = Misc.strip(fa.defaultValueToken.getText(), 1);
/*     */ 
/* 142 */           fa.compiledDefaultValue = c2.compile(group.getFileName(), argSTname, null, defArgTemplate, fa.defaultValueToken);
/*     */ 
/* 145 */           fa.compiledDefaultValue.name = argSTname;
/* 146 */           fa.compiledDefaultValue.defineImplicitlyDefinedTemplates(group);
/*     */         }
/* 148 */         else if (fa.defaultValueToken.getType() == 11) {
/* 149 */           fa.defaultValue = Misc.strip(fa.defaultValueToken.getText(), 1);
/*     */         }
/*     */         else {
/* 152 */           fa.defaultValue = Boolean.valueOf(fa.defaultValueToken.getType() == 12);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void defineFormalArgs(List<FormalArgument> args) {
/* 159 */     this.hasFormalArgs = true;
/* 160 */     if (args == null) { this.formalArguments = null; }
/*     */     else
/*     */     {
/* 161 */       FormalArgument a;
/* 161 */       for (Iterator i$ = args.iterator(); i$.hasNext(); addArg(a)) a = (FormalArgument)i$.next(); 
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addArg(FormalArgument a)
/*     */   {
/* 166 */     if (this.formalArguments == null) {
/* 167 */       this.formalArguments = Collections.synchronizedMap(new LinkedHashMap());
/*     */     }
/* 169 */     a.index = this.formalArguments.size();
/* 170 */     this.formalArguments.put(a.name, a);
/*     */   }
/*     */ 
/*     */   public void defineImplicitlyDefinedTemplates(STGroup group) {
/* 174 */     if (this.implicitlyDefinedTemplates != null)
/* 175 */       for (CompiledST sub : this.implicitlyDefinedTemplates) {
/* 176 */         group.rawDefineTemplate(sub.name, sub, sub.templateDefStartToken);
/* 177 */         sub.defineImplicitlyDefinedTemplates(group);
/*     */       }
/*     */   }
/*     */ 
/*     */   public String getTemplateSource()
/*     */   {
/* 183 */     Interval r = getTemplateRange();
/* 184 */     return this.template.substring(r.a, r.b + 1);
/*     */   }
/*     */ 
/*     */   public Interval getTemplateRange() {
/* 188 */     if (this.isAnonSubtemplate) {
/* 189 */       Interval start = this.sourceMap[0];
/* 190 */       Interval stop = null;
/* 191 */       for (int i = this.sourceMap.length - 1; i >= 0; i--) {
/* 192 */         Interval I = this.sourceMap[i];
/* 193 */         if (I != null) {
/* 194 */           stop = I;
/* 195 */           break;
/*     */         }
/*     */       }
/* 198 */       return new Interval(start.a, stop.b);
/*     */     }
/* 200 */     return new Interval(0, this.template.length() - 1);
/*     */   }
/*     */ 
/*     */   public String instrs() {
/* 204 */     BytecodeDisassembler dis = new BytecodeDisassembler(this);
/* 205 */     return dis.instrs();
/*     */   }
/*     */ 
/*     */   public void dump() {
/* 209 */     BytecodeDisassembler dis = new BytecodeDisassembler(this);
/* 210 */     System.out.println(this.name + ":");
/* 211 */     System.out.println(dis.disassemble());
/* 212 */     System.out.println("Strings:");
/* 213 */     System.out.println(dis.strings());
/* 214 */     System.out.println("Bytecode to template map:");
/* 215 */     System.out.println(dis.sourceMap());
/*     */   }
/*     */ 
/*     */   public String disasm() {
/* 219 */     BytecodeDisassembler dis = new BytecodeDisassembler(this);
/* 220 */     StringWriter sw = new StringWriter();
/* 221 */     PrintWriter pw = new PrintWriter(sw);
/* 222 */     pw.println(dis.disassemble());
/* 223 */     pw.println("Strings:");
/* 224 */     pw.println(dis.strings());
/* 225 */     pw.println("Bytecode to template map:");
/* 226 */     pw.println(dis.sourceMap());
/* 227 */     pw.close();
/* 228 */     return sw.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.compiler.CompiledST
 * JD-Core Version:    0.6.2
 */