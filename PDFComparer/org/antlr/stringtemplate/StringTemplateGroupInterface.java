/*     */ package org.antlr.stringtemplate;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.io.Reader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.antlr.stringtemplate.language.InterfaceLexer;
/*     */ import org.antlr.stringtemplate.language.InterfaceParser;
/*     */ 
/*     */ public class StringTemplateGroupInterface
/*     */ {
/*     */   protected String name;
/*  21 */   protected Map templates = new LinkedHashMap();
/*     */ 
/*  26 */   protected StringTemplateGroupInterface superInterface = null;
/*     */ 
/*  31 */   protected StringTemplateErrorListener listener = DEFAULT_ERROR_LISTENER;
/*     */ 
/*  33 */   public static StringTemplateErrorListener DEFAULT_ERROR_LISTENER = new StringTemplateErrorListener()
/*     */   {
/*     */     public void error(String s, Throwable e) {
/*  36 */       System.err.println(s);
/*  37 */       if (e != null)
/*  38 */         e.printStackTrace(System.err);
/*     */     }
/*     */ 
/*     */     public void warning(String s) {
/*  42 */       System.out.println(s);
/*     */     }
/*  33 */   };
/*     */ 
/*     */   public StringTemplateGroupInterface(Reader r)
/*     */   {
/*  59 */     this(r, DEFAULT_ERROR_LISTENER, (StringTemplateGroupInterface)null);
/*     */   }
/*     */ 
/*     */   public StringTemplateGroupInterface(Reader r, StringTemplateErrorListener errors) {
/*  63 */     this(r, errors, (StringTemplateGroupInterface)null);
/*     */   }
/*     */ 
/*     */   public StringTemplateGroupInterface(Reader r, StringTemplateErrorListener errors, StringTemplateGroupInterface superInterface)
/*     */   {
/*  71 */     this.listener = errors;
/*  72 */     setSuperInterface(superInterface);
/*  73 */     parseInterface(r);
/*     */   }
/*     */ 
/*     */   public StringTemplateGroupInterface getSuperInterface() {
/*  77 */     return this.superInterface;
/*     */   }
/*     */ 
/*     */   public void setSuperInterface(StringTemplateGroupInterface superInterface) {
/*  81 */     this.superInterface = superInterface;
/*     */   }
/*     */ 
/*     */   protected void parseInterface(Reader r) {
/*     */     try {
/*  86 */       InterfaceLexer lexer = new InterfaceLexer(r);
/*  87 */       InterfaceParser parser = new InterfaceParser(lexer);
/*  88 */       parser.groupInterface(this);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  92 */       String name = "<unknown>";
/*  93 */       if (getName() != null) {
/*  94 */         name = getName();
/*     */       }
/*  96 */       error("problem parsing group " + name + ": " + e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void defineTemplate(String name, LinkedHashMap formalArgs, boolean optional) {
/* 101 */     TemplateDefinition d = new TemplateDefinition(name, formalArgs, optional);
/* 102 */     this.templates.put(d.name, d);
/*     */   }
/*     */ 
/*     */   public List getMissingTemplates(StringTemplateGroup group)
/*     */   {
/* 109 */     List missing = new ArrayList();
/* 110 */     for (Iterator it = this.templates.keySet().iterator(); it.hasNext(); ) {
/* 111 */       String name = (String)it.next();
/* 112 */       TemplateDefinition d = (TemplateDefinition)this.templates.get(name);
/* 113 */       if ((!d.optional) && (!group.isDefined(d.name))) {
/* 114 */         missing.add(d.name);
/*     */       }
/*     */     }
/* 117 */     if (missing.size() == 0) {
/* 118 */       missing = null;
/*     */     }
/* 120 */     return missing;
/*     */   }
/*     */ 
/*     */   public List getMismatchedTemplates(StringTemplateGroup group)
/*     */   {
/* 127 */     List mismatched = new ArrayList();
/* 128 */     for (Iterator it = this.templates.keySet().iterator(); it.hasNext(); ) {
/* 129 */       String name = (String)it.next();
/* 130 */       TemplateDefinition d = (TemplateDefinition)this.templates.get(name);
/* 131 */       if (group.isDefined(d.name)) {
/* 132 */         StringTemplate defST = group.getTemplateDefinition(d.name);
/* 133 */         Map formalArgs = defST.getFormalArguments();
/* 134 */         boolean ack = false;
/* 135 */         if (((d.formalArgs != null) && (formalArgs == null)) || ((d.formalArgs == null) && (formalArgs != null)) || (d.formalArgs.size() != formalArgs.size()))
/*     */         {
/* 139 */           ack = true;
/*     */         }
/* 141 */         if (!ack) {
/* 142 */           Iterator it2 = formalArgs.keySet().iterator();
/* 143 */           while (it2.hasNext())
/*     */           {
/* 145 */             String argName = (String)it2.next();
/* 146 */             if (d.formalArgs.get(argName) == null) {
/* 147 */               ack = true;
/* 148 */               break;
/*     */             }
/*     */           }
/*     */         }
/* 152 */         if (ack)
/*     */         {
/* 154 */           mismatched.add(getTemplateSignature(d));
/*     */         }
/*     */       }
/*     */     }
/* 158 */     if (mismatched.size() == 0) {
/* 159 */       mismatched = null;
/*     */     }
/* 161 */     return mismatched;
/*     */   }
/*     */ 
/*     */   public String getName() {
/* 165 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/* 169 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public void error(String msg) {
/* 173 */     error(msg, null);
/*     */   }
/*     */ 
/*     */   public void error(String msg, Exception e) {
/* 177 */     if (this.listener != null) {
/* 178 */       this.listener.error(msg, e);
/*     */     }
/*     */     else {
/* 181 */       System.err.println("StringTemplate: " + msg);
/* 182 */       if (e != null)
/* 183 */         e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 189 */     StringBuffer buf = new StringBuffer();
/* 190 */     buf.append("interface ");
/* 191 */     buf.append(getName());
/* 192 */     buf.append(";\n");
/* 193 */     for (Iterator it = this.templates.keySet().iterator(); it.hasNext(); ) {
/* 194 */       String name = (String)it.next();
/* 195 */       TemplateDefinition d = (TemplateDefinition)this.templates.get(name);
/* 196 */       buf.append(getTemplateSignature(d));
/* 197 */       buf.append(";\n");
/*     */     }
/* 199 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   protected String getTemplateSignature(TemplateDefinition d) {
/* 203 */     StringBuffer buf = new StringBuffer();
/* 204 */     if (d.optional) {
/* 205 */       buf.append("optional ");
/*     */     }
/* 207 */     buf.append(d.name);
/* 208 */     if (d.formalArgs != null) {
/* 209 */       StringBuffer args = new StringBuffer();
/* 210 */       args.append('(');
/* 211 */       int i = 1;
/* 212 */       for (Iterator it = d.formalArgs.keySet().iterator(); it.hasNext(); ) {
/* 213 */         String name = (String)it.next();
/* 214 */         if (i > 1) {
/* 215 */           args.append(", ");
/*     */         }
/* 217 */         args.append(name);
/* 218 */         i++;
/*     */       }
/* 220 */       args.append(')');
/* 221 */       buf.append(args);
/*     */     }
/*     */     else {
/* 224 */       buf.append("()");
/*     */     }
/* 226 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   static class TemplateDefinition
/*     */   {
/*     */     public String name;
/*     */     public LinkedHashMap formalArgs;
/*  50 */     public boolean optional = false;
/*     */ 
/*  52 */     public TemplateDefinition(String name, LinkedHashMap formalArgs, boolean optional) { this.name = name;
/*  53 */       this.formalArgs = formalArgs;
/*  54 */       this.optional = optional;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.stringtemplate.StringTemplateGroupInterface
 * JD-Core Version:    0.6.2
 */