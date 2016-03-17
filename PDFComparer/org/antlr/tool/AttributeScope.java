/*     */ package org.antlr.tool;
/*     */ 
/*     */ import antlr.Token;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.antlr.codegen.CodeGenerator;
/*     */ 
/*     */ public class AttributeScope
/*     */ {
/*  48 */   public static AttributeScope tokenScope = new AttributeScope("Token", null);
/*     */   public Token derivedFromToken;
/*     */   public Grammar grammar;
/*     */   private String name;
/*     */   public boolean isDynamicGlobalScope;
/*     */   public boolean isDynamicRuleScope;
/*     */   public boolean isParameterScope;
/*     */   public boolean isReturnScope;
/*     */   public boolean isPredefinedRuleScope;
/*     */   public boolean isPredefinedLexerRuleScope;
/*  83 */   protected LinkedHashMap<String, Attribute> attributes = new LinkedHashMap();
/*     */ 
/*  86 */   public LinkedHashMap<String, GrammarAST> actions = new LinkedHashMap();
/*     */ 
/*     */   public AttributeScope(String name, Token derivedFromToken) {
/*  89 */     this(null, name, derivedFromToken);
/*     */   }
/*     */ 
/*     */   public AttributeScope(Grammar grammar, String name, Token derivedFromToken) {
/*  93 */     this.grammar = grammar;
/*  94 */     this.name = name;
/*  95 */     this.derivedFromToken = derivedFromToken;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  99 */     if (this.isParameterScope) {
/* 100 */       return this.name + "_parameter";
/*     */     }
/* 102 */     if (this.isReturnScope) {
/* 103 */       return this.name + "_return";
/*     */     }
/* 105 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void addAttributes(String definitions, int separator)
/*     */   {
/* 122 */     List attrs = new ArrayList();
/* 123 */     CodeGenerator.getListOfArgumentsFromAction(definitions, 0, -1, separator, attrs);
/* 124 */     for (Iterator i$ = attrs.iterator(); i$.hasNext(); ) { String a = (String)i$.next();
/* 125 */       Attribute attr = new Attribute(a);
/* 126 */       if ((!this.isReturnScope) && (attr.initValue != null)) {
/* 127 */         ErrorManager.grammarError(148, this.grammar, this.derivedFromToken, attr.name);
/*     */ 
/* 131 */         attr.initValue = null;
/*     */       }
/* 133 */       this.attributes.put(attr.name, attr); }
/*     */   }
/*     */ 
/*     */   public void addAttribute(String name, String decl)
/*     */   {
/* 138 */     this.attributes.put(name, new Attribute(name, decl));
/*     */   }
/*     */ 
/*     */   public Attribute getAttribute(String name) {
/* 142 */     return (Attribute)this.attributes.get(name);
/*     */   }
/*     */ 
/*     */   public List<Attribute> getAttributes()
/*     */   {
/* 147 */     List a = new ArrayList();
/* 148 */     a.addAll(this.attributes.values());
/* 149 */     return a;
/*     */   }
/*     */ 
/*     */   public Set intersection(AttributeScope other)
/*     */   {
/* 156 */     if ((other == null) || (other.size() == 0) || (size() == 0)) {
/* 157 */       return null;
/*     */     }
/* 159 */     Set inter = new HashSet();
/* 160 */     Set thisKeys = this.attributes.keySet();
/* 161 */     for (Iterator it = thisKeys.iterator(); it.hasNext(); ) {
/* 162 */       String key = (String)it.next();
/* 163 */       if (other.attributes.get(key) != null) {
/* 164 */         inter.add(key);
/*     */       }
/*     */     }
/* 167 */     if (inter.size() == 0) {
/* 168 */       return null;
/*     */     }
/* 170 */     return inter;
/*     */   }
/*     */ 
/*     */   public int size() {
/* 174 */     return this.attributes == null ? 0 : this.attributes.size();
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 178 */     return (this.isDynamicGlobalScope ? "global " : "") + getName() + ":" + this.attributes;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  50 */     tokenScope.addAttribute("text", null);
/*  51 */     tokenScope.addAttribute("type", null);
/*  52 */     tokenScope.addAttribute("line", null);
/*  53 */     tokenScope.addAttribute("index", null);
/*  54 */     tokenScope.addAttribute("pos", null);
/*  55 */     tokenScope.addAttribute("channel", null);
/*  56 */     tokenScope.addAttribute("tree", null);
/*  57 */     tokenScope.addAttribute("int", null);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.tool.AttributeScope
 * JD-Core Version:    0.6.2
 */