/*      */ package org.stringtemplate.v4;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.io.Writer;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.antlr.runtime.Token;
/*      */ import org.stringtemplate.v4.compiler.BytecodeDisassembler;
/*      */ import org.stringtemplate.v4.compiler.CompiledST;
/*      */ import org.stringtemplate.v4.compiler.Compiler;
/*      */ import org.stringtemplate.v4.compiler.FormalArgument;
/*      */ import org.stringtemplate.v4.debug.EvalExprEvent;
/*      */ import org.stringtemplate.v4.debug.EvalTemplateEvent;
/*      */ import org.stringtemplate.v4.debug.IndentEvent;
/*      */ import org.stringtemplate.v4.debug.InterpEvent;
/*      */ import org.stringtemplate.v4.misc.ArrayIterator;
/*      */ import org.stringtemplate.v4.misc.ErrorManager;
/*      */ import org.stringtemplate.v4.misc.ErrorType;
/*      */ import org.stringtemplate.v4.misc.Interval;
/*      */ import org.stringtemplate.v4.misc.Misc;
/*      */ import org.stringtemplate.v4.misc.STNoSuchAttributeException;
/*      */ import org.stringtemplate.v4.misc.STNoSuchPropertyException;
/*      */ 
/*      */ public class Interpreter
/*      */ {
/*      */   public static final int DEFAULT_OPERAND_STACK_SIZE = 100;
/*   60 */   public static final Set<String> predefinedAnonSubtemplateAttributes = new HashSet() { } ;
/*      */ 
/*   64 */   Object[] operands = new Object[100];
/*   65 */   int sp = -1;
/*   66 */   int current_ip = 0;
/*   67 */   int nwline = 0;
/*      */ 
/*   72 */   public InstanceScope currentScope = null;
/*      */   STGroup group;
/*      */   Locale locale;
/*      */   ErrorManager errMgr;
/*   85 */   public static boolean trace = false;
/*      */   protected List<String> executeTrace;
/*   92 */   public boolean debug = false;
/*      */   protected List<InterpEvent> events;
/*      */ 
/*      */   public Interpreter(STGroup group, boolean debug)
/*      */   {
/*  101 */     this(group, Locale.getDefault(), group.errMgr, debug);
/*      */   }
/*      */ 
/*      */   public Interpreter(STGroup group, Locale locale, boolean debug) {
/*  105 */     this(group, locale, group.errMgr, debug);
/*      */   }
/*      */ 
/*      */   public Interpreter(STGroup group, ErrorManager errMgr, boolean debug) {
/*  109 */     this(group, Locale.getDefault(), errMgr, debug);
/*      */   }
/*      */ 
/*      */   public Interpreter(STGroup group, Locale locale, ErrorManager errMgr, boolean debug) {
/*  113 */     this.group = group;
/*  114 */     this.locale = locale;
/*  115 */     this.errMgr = errMgr;
/*  116 */     this.debug = debug;
/*  117 */     if (debug) {
/*  118 */       this.events = new ArrayList();
/*  119 */       this.executeTrace = new ArrayList();
/*      */     }
/*      */   }
/*      */ 
/*      */   public int exec(STWriter out, ST self)
/*      */   {
/*  134 */     if (this.debug) System.out.println("exec(" + self.getName() + ")");
/*  135 */     pushScope(self);
/*      */     try {
/*  137 */       setDefaultArguments(out, self);
/*  138 */       return _exec(out, self);
/*      */     }
/*      */     catch (Exception e) {
/*  141 */       StringWriter sw = new StringWriter();
/*  142 */       PrintWriter pw = new PrintWriter(sw);
/*  143 */       e.printStackTrace(pw);
/*  144 */       pw.flush();
/*  145 */       this.errMgr.runTimeError(this, self, this.current_ip, ErrorType.INTERNAL_ERROR, "internal error: " + sw.toString());
/*      */ 
/*  147 */       return 0;
/*      */     } finally {
/*  149 */       popScope();
/*      */     }
/*      */   }
/*      */ 
/*  153 */   protected int _exec(STWriter out, ST self) { int start = out.index();
/*  154 */     int prevOpcode = 0;
/*  155 */     int n = 0;
/*      */ 
/*  163 */     byte[] code = self.impl.instrs;
/*  164 */     int ip = 0;
/*  165 */     while (ip < self.impl.codeSize) {
/*  166 */       if ((trace) || (this.debug)) trace(self, ip);
/*  167 */       short opcode = (short)code[ip];
/*      */ 
/*  169 */       this.current_ip = ip;
/*  170 */       ip++;
/*      */       int nameIndex;
/*      */       String name;
/*      */       Object o;
/*      */       int nargs;
/*      */       ST st;
/*      */       Map attrs;
/*      */       Object[] options;
/*      */       int n1;
/*      */       int nmaps;
/*      */       Object right;
/*      */       Object left;
/*      */       int strIndex;
/*  171 */       switch (opcode)
/*      */       {
/*      */       case 1:
/*  174 */         load_str(self, ip);
/*  175 */         ip += 2;
/*  176 */         break;
/*      */       case 2:
/*  178 */         nameIndex = getShort(code, ip);
/*  179 */         ip += 2;
/*  180 */         name = self.impl.strings[nameIndex];
/*      */         try {
/*  182 */           o = getAttribute(self, name);
/*  183 */           if (o == ST.EMPTY_ATTR) o = null; 
/*      */         }
/*      */         catch (STNoSuchAttributeException nsae)
/*      */         {
/*  186 */           this.errMgr.runTimeError(this, self, this.current_ip, ErrorType.NO_SUCH_ATTRIBUTE, name);
/*  187 */           o = null;
/*      */         }
/*  189 */         this.operands[(++this.sp)] = o;
/*  190 */         break;
/*      */       case 3:
/*  192 */         int valueIndex = getShort(code, ip);
/*  193 */         ip += 2;
/*  194 */         o = self.locals[valueIndex];
/*  195 */         if (o == ST.EMPTY_ATTR) o = null;
/*  196 */         this.operands[(++this.sp)] = o;
/*  197 */         break;
/*      */       case 4:
/*  199 */         nameIndex = getShort(code, ip);
/*  200 */         ip += 2;
/*  201 */         o = this.operands[(this.sp--)];
/*  202 */         name = self.impl.strings[nameIndex];
/*  203 */         this.operands[(++this.sp)] = getObjectProperty(out, self, o, name);
/*  204 */         break;
/*      */       case 5:
/*  206 */         Object propName = this.operands[(this.sp--)];
/*  207 */         o = this.operands[this.sp];
/*  208 */         this.operands[this.sp] = getObjectProperty(out, self, o, propName);
/*  209 */         break;
/*      */       case 8:
/*  211 */         nameIndex = getShort(code, ip);
/*  212 */         ip += 2;
/*  213 */         name = self.impl.strings[nameIndex];
/*  214 */         nargs = getShort(code, ip);
/*  215 */         ip += 2;
/*      */ 
/*  218 */         st = self.groupThatCreatedThisInstance.getEmbeddedInstanceOf(this, self, ip, name);
/*      */ 
/*  220 */         storeArgs(self, nargs, st);
/*  221 */         this.sp -= nargs;
/*  222 */         this.operands[(++this.sp)] = st;
/*  223 */         break;
/*      */       case 9:
/*  225 */         nargs = getShort(code, ip);
/*  226 */         ip += 2;
/*  227 */         name = (String)this.operands[(this.sp - nargs)];
/*  228 */         st = self.groupThatCreatedThisInstance.getEmbeddedInstanceOf(this, self, ip, name);
/*  229 */         storeArgs(self, nargs, st);
/*  230 */         this.sp -= nargs;
/*  231 */         this.sp -= 1;
/*  232 */         this.operands[(++this.sp)] = st;
/*  233 */         break;
/*      */       case 10:
/*  235 */         nameIndex = getShort(code, ip);
/*  236 */         ip += 2;
/*  237 */         name = self.impl.strings[nameIndex];
/*  238 */         attrs = (Map)this.operands[(this.sp--)];
/*      */ 
/*  241 */         st = self.groupThatCreatedThisInstance.getEmbeddedInstanceOf(this, self, ip, name);
/*      */ 
/*  243 */         storeArgs(self, attrs, st);
/*  244 */         this.operands[(++this.sp)] = st;
/*  245 */         break;
/*      */       case 11:
/*  247 */         nameIndex = getShort(code, ip);
/*  248 */         ip += 2;
/*  249 */         name = self.impl.strings[nameIndex];
/*  250 */         nargs = getShort(code, ip);
/*  251 */         ip += 2;
/*  252 */         super_new(self, name, nargs);
/*  253 */         break;
/*      */       case 12:
/*  255 */         nameIndex = getShort(code, ip);
/*  256 */         ip += 2;
/*  257 */         name = self.impl.strings[nameIndex];
/*  258 */         attrs = (Map)this.operands[(this.sp--)];
/*  259 */         super_new(self, name, attrs);
/*  260 */         break;
/*      */       case 6:
/*  262 */         int optionIndex = getShort(code, ip);
/*  263 */         ip += 2;
/*  264 */         o = this.operands[(this.sp--)];
/*  265 */         options = (Object[])this.operands[this.sp];
/*  266 */         options[optionIndex] = o;
/*  267 */         break;
/*      */       case 7:
/*  269 */         nameIndex = getShort(code, ip);
/*  270 */         name = self.impl.strings[nameIndex];
/*  271 */         ip += 2;
/*  272 */         o = this.operands[(this.sp--)];
/*  273 */         attrs = (Map)this.operands[this.sp];
/*  274 */         attrs.put(name, o);
/*  275 */         break;
/*      */       case 13:
/*  277 */         o = this.operands[(this.sp--)];
/*  278 */         n1 = writeObjectNoOptions(out, self, o);
/*  279 */         n += n1;
/*  280 */         this.nwline += n1;
/*  281 */         break;
/*      */       case 14:
/*  283 */         options = (Object[])this.operands[(this.sp--)];
/*  284 */         o = this.operands[(this.sp--)];
/*  285 */         int n2 = writeObjectWithOptions(out, self, o, options);
/*  286 */         n += n2;
/*  287 */         this.nwline += n2;
/*  288 */         break;
/*      */       case 15:
/*  290 */         st = (ST)this.operands[(this.sp--)];
/*  291 */         o = this.operands[(this.sp--)];
/*  292 */         map(self, o, st);
/*  293 */         break;
/*      */       case 16:
/*  295 */         nmaps = getShort(code, ip);
/*  296 */         ip += 2;
/*  297 */         List templates = new ArrayList();
/*  298 */         for (int i = nmaps - 1; i >= 0; i--) templates.add((ST)this.operands[(this.sp - i)]);
/*  299 */         this.sp -= nmaps;
/*  300 */         o = this.operands[(this.sp--)];
/*  301 */         if (o != null) rot_map(self, o, templates); break;
/*      */       case 17:
/*  304 */         st = (ST)this.operands[(this.sp--)];
/*  305 */         nmaps = getShort(code, ip);
/*  306 */         ip += 2;
/*  307 */         List exprs = new ArrayList();
/*  308 */         for (int i = nmaps - 1; i >= 0; i--) exprs.add(this.operands[(this.sp - i)]);
/*  309 */         this.sp -= nmaps;
/*  310 */         this.operands[(++this.sp)] = zip_map(self, exprs, st);
/*  311 */         break;
/*      */       case 18:
/*  313 */         ip = getShort(code, ip);
/*  314 */         break;
/*      */       case 19:
/*  316 */         int addr = getShort(code, ip);
/*  317 */         ip += 2;
/*  318 */         o = this.operands[(this.sp--)];
/*  319 */         if (!testAttributeTrue(o)) ip = addr; break;
/*      */       case 20:
/*  322 */         this.operands[(++this.sp)] = new Object[Compiler.NUM_OPTIONS];
/*  323 */         break;
/*      */       case 21:
/*  325 */         this.operands[(++this.sp)] = new HashMap();
/*  326 */         break;
/*      */       case 22:
/*  328 */         nameIndex = getShort(code, ip);
/*  329 */         ip += 2;
/*  330 */         name = self.impl.strings[nameIndex];
/*  331 */         attrs = (Map)this.operands[this.sp];
/*  332 */         passthru(self, name, attrs);
/*  333 */         break;
/*      */       case 24:
/*  335 */         this.operands[(++this.sp)] = new ArrayList();
/*  336 */         break;
/*      */       case 25:
/*  338 */         o = this.operands[(this.sp--)];
/*  339 */         List list = (List)this.operands[this.sp];
/*  340 */         addToList(list, o);
/*  341 */         break;
/*      */       case 26:
/*  344 */         this.operands[this.sp] = toString(out, self, this.operands[this.sp]);
/*  345 */         break;
/*      */       case 27:
/*  347 */         this.operands[this.sp] = first(this.operands[this.sp]);
/*  348 */         break;
/*      */       case 28:
/*  350 */         this.operands[this.sp] = last(this.operands[this.sp]);
/*  351 */         break;
/*      */       case 29:
/*  353 */         this.operands[this.sp] = rest(this.operands[this.sp]);
/*  354 */         break;
/*      */       case 30:
/*  356 */         this.operands[this.sp] = trunc(this.operands[this.sp]);
/*  357 */         break;
/*      */       case 31:
/*  359 */         this.operands[this.sp] = strip(this.operands[this.sp]);
/*  360 */         break;
/*      */       case 32:
/*  362 */         o = this.operands[(this.sp--)];
/*  363 */         if (o.getClass() == String.class) {
/*  364 */           this.operands[(++this.sp)] = ((String)o).trim();
/*      */         }
/*      */         else {
/*  367 */           this.errMgr.runTimeError(this, self, this.current_ip, ErrorType.EXPECTING_STRING, "trim", o.getClass().getName());
/*  368 */           this.operands[(++this.sp)] = o;
/*      */         }
/*  370 */         break;
/*      */       case 33:
/*  372 */         this.operands[this.sp] = length(this.operands[this.sp]);
/*  373 */         break;
/*      */       case 34:
/*  375 */         o = this.operands[(this.sp--)];
/*  376 */         if (o.getClass() == String.class) {
/*  377 */           this.operands[(++this.sp)] = Integer.valueOf(((String)o).length());
/*      */         }
/*      */         else {
/*  380 */           this.errMgr.runTimeError(this, self, this.current_ip, ErrorType.EXPECTING_STRING, "strlen", o.getClass().getName());
/*  381 */           this.operands[(++this.sp)] = Integer.valueOf(0);
/*      */         }
/*  383 */         break;
/*      */       case 35:
/*  385 */         this.operands[this.sp] = reverse(this.operands[this.sp]);
/*  386 */         break;
/*      */       case 36:
/*  388 */         this.operands[this.sp] = Boolean.valueOf(!testAttributeTrue(this.operands[this.sp]) ? 1 : false);
/*  389 */         break;
/*      */       case 37:
/*  391 */         right = this.operands[(this.sp--)];
/*  392 */         left = this.operands[(this.sp--)];
/*  393 */         this.operands[(++this.sp)] = Boolean.valueOf((testAttributeTrue(left)) || (testAttributeTrue(right)) ? 1 : false);
/*  394 */         break;
/*      */       case 38:
/*  396 */         right = this.operands[(this.sp--)];
/*  397 */         left = this.operands[(this.sp--)];
/*  398 */         this.operands[(++this.sp)] = Boolean.valueOf((testAttributeTrue(left)) && (testAttributeTrue(right)) ? 1 : false);
/*  399 */         break;
/*      */       case 39:
/*  401 */         strIndex = getShort(code, ip);
/*  402 */         ip += 2;
/*  403 */         indent(out, self, strIndex);
/*  404 */         break;
/*      */       case 40:
/*  406 */         out.popIndentation();
/*  407 */         break;
/*      */       case 41:
/*      */         try {
/*  410 */           if ((prevOpcode == 41) || (prevOpcode == 39) || (this.nwline > 0))
/*      */           {
/*  414 */             out.write(Misc.newline);
/*      */           }
/*  416 */           this.nwline = 0;
/*      */         }
/*      */         catch (IOException ioe) {
/*  419 */           this.errMgr.IOError(self, ErrorType.WRITE_IO_ERROR, ioe);
/*      */         }
/*      */ 
/*      */       case 42:
/*  423 */         break;
/*      */       case 43:
/*  425 */         this.sp -= 1;
/*  426 */         break;
/*      */       case 44:
/*  428 */         this.operands[(++this.sp)] = null;
/*  429 */         break;
/*      */       case 45:
/*  431 */         this.operands[(++this.sp)] = Boolean.valueOf(true);
/*  432 */         break;
/*      */       case 46:
/*  434 */         this.operands[(++this.sp)] = Boolean.valueOf(false);
/*  435 */         break;
/*      */       case 47:
/*  437 */         strIndex = getShort(code, ip);
/*  438 */         ip += 2;
/*  439 */         o = self.impl.strings[strIndex];
/*  440 */         n1 = writeObjectNoOptions(out, self, o);
/*  441 */         n += n1;
/*  442 */         this.nwline += n1;
/*  443 */         break;
/*      */       case 23:
/*      */       default:
/*  455 */         this.errMgr.internalError(self, "invalid bytecode @ " + (ip - 1) + ": " + opcode, null);
/*  456 */         self.impl.dump();
/*      */       }
/*  458 */       prevOpcode = opcode;
/*      */     }
/*  460 */     if (this.debug) {
/*  461 */       int stop = out.index() - 1;
/*  462 */       EvalTemplateEvent e = new EvalTemplateEvent(this.currentScope, start, stop);
/*  463 */       trackDebugEvent(self, e);
/*      */     }
/*  465 */     return n; }
/*      */ 
/*      */   void load_str(ST self, int ip)
/*      */   {
/*  469 */     int strIndex = getShort(self.impl.instrs, ip);
/*  470 */     ip += 2;
/*  471 */     this.operands[(++this.sp)] = self.impl.strings[strIndex];
/*      */   }
/*      */ 
/*      */   void super_new(ST self, String name, int nargs)
/*      */   {
/*  476 */     ST st = null;
/*  477 */     CompiledST imported = self.impl.nativeGroup.lookupImportedTemplate(name);
/*  478 */     if (imported == null) {
/*  479 */       this.errMgr.runTimeError(this, self, this.current_ip, ErrorType.NO_IMPORTED_TEMPLATE, name);
/*      */ 
/*  481 */       st = self.groupThatCreatedThisInstance.createStringTemplateInternally(new CompiledST());
/*      */     }
/*      */     else {
/*  484 */       st = imported.nativeGroup.getEmbeddedInstanceOf(this, self, this.current_ip, name);
/*  485 */       st.groupThatCreatedThisInstance = this.group;
/*      */     }
/*      */ 
/*  488 */     storeArgs(self, nargs, st);
/*  489 */     this.sp -= nargs;
/*  490 */     this.operands[(++this.sp)] = st;
/*      */   }
/*      */ 
/*      */   void super_new(ST self, String name, Map<String, Object> attrs) {
/*  494 */     ST st = null;
/*  495 */     CompiledST imported = self.impl.nativeGroup.lookupImportedTemplate(name);
/*  496 */     if (imported == null) {
/*  497 */       this.errMgr.runTimeError(this, self, this.current_ip, ErrorType.NO_IMPORTED_TEMPLATE, name);
/*      */ 
/*  499 */       st = self.groupThatCreatedThisInstance.createStringTemplateInternally(new CompiledST());
/*      */     }
/*      */     else {
/*  502 */       st = imported.nativeGroup.createStringTemplateInternally(imported);
/*  503 */       st.groupThatCreatedThisInstance = this.group;
/*      */     }
/*      */ 
/*  507 */     storeArgs(self, attrs, st);
/*  508 */     this.operands[(++this.sp)] = st;
/*      */   }
/*      */ 
/*      */   void passthru(ST self, String templateName, Map<String, Object> attrs) {
/*  512 */     CompiledST c = this.group.lookupTemplate(templateName);
/*  513 */     if (c == null) return;
/*  514 */     if (c.formalArguments == null) return;
/*  515 */     for (FormalArgument arg : c.formalArguments.values())
/*      */     {
/*  517 */       if (!attrs.containsKey(arg.name))
/*      */         try
/*      */         {
/*  520 */           Object o = getAttribute(self, arg.name);
/*      */ 
/*  523 */           if ((o == ST.EMPTY_ATTR) && (arg.defaultValueToken == null)) {
/*  524 */             attrs.put(arg.name, null);
/*      */           }
/*  527 */           else if (o != ST.EMPTY_ATTR) {
/*  528 */             attrs.put(arg.name, o);
/*      */           }
/*      */ 
/*      */         }
/*      */         catch (STNoSuchAttributeException nsae)
/*      */         {
/*  534 */           if (arg.defaultValueToken == null)
/*  535 */             attrs.put(arg.name, null);
/*      */         }
/*      */     }
/*      */   }
/*      */ 
/*      */   void storeArgs(ST self, Map<String, Object> attrs, ST st)
/*      */   {
/*  543 */     int nformalArgs = 0;
/*  544 */     if (st.impl.formalArguments != null) nformalArgs = st.impl.formalArguments.size();
/*  545 */     int nargs = 0;
/*  546 */     if (attrs != null) nargs = attrs.size();
/*      */ 
/*  548 */     if ((nargs < nformalArgs - st.impl.numberOfArgsWithDefaultValues) || (nargs > nformalArgs))
/*      */     {
/*  551 */       this.errMgr.runTimeError(this, self, this.current_ip, ErrorType.ARGUMENT_COUNT_MISMATCH, Integer.valueOf(nargs), st.impl.name, Integer.valueOf(nformalArgs));
/*      */     }
/*      */ 
/*  559 */     for (String argName : attrs.keySet())
/*      */     {
/*  561 */       if ((st.impl.formalArguments == null) || (!st.impl.formalArguments.containsKey(argName))) {
/*  562 */         this.errMgr.runTimeError(this, self, this.current_ip, ErrorType.NO_SUCH_ATTRIBUTE, argName);
/*      */       }
/*      */       else
/*      */       {
/*  568 */         Object o = attrs.get(argName);
/*  569 */         st.rawSetAttribute(argName, o);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*  574 */   void storeArgs(ST self, int nargs, ST st) { int nformalArgs = 0;
/*  575 */     if (st.impl.formalArguments != null) nformalArgs = st.impl.formalArguments.size();
/*  576 */     int firstArg = this.sp - (nargs - 1);
/*  577 */     int numToStore = Math.min(nargs, nformalArgs);
/*  578 */     if (st.impl.isAnonSubtemplate) nformalArgs -= predefinedAnonSubtemplateAttributes.size();
/*      */ 
/*  580 */     if ((nargs < nformalArgs - st.impl.numberOfArgsWithDefaultValues) || (nargs > nformalArgs))
/*      */     {
/*  583 */       this.errMgr.runTimeError(this, self, this.current_ip, ErrorType.ARGUMENT_COUNT_MISMATCH, Integer.valueOf(nargs), st.impl.name, Integer.valueOf(nformalArgs));
/*      */     }
/*      */ 
/*  591 */     if (st.impl.formalArguments == null) return;
/*      */ 
/*  593 */     Iterator argNames = st.impl.formalArguments.keySet().iterator();
/*  594 */     for (int i = 0; i < numToStore; i++) {
/*  595 */       Object o = this.operands[(firstArg + i)];
/*  596 */       String argName = (String)argNames.next();
/*  597 */       st.rawSetAttribute(argName, o);
/*      */     } }
/*      */ 
/*      */   protected void indent(STWriter out, ST self, int strIndex)
/*      */   {
/*  602 */     String indent = self.impl.strings[strIndex];
/*  603 */     if (this.debug) {
/*  604 */       int start = out.index();
/*  605 */       EvalExprEvent e = new IndentEvent(this.currentScope, start, start + indent.length() - 1, getExprStartChar(self), getExprStopChar(self));
/*      */ 
/*  609 */       trackDebugEvent(self, e);
/*      */     }
/*  611 */     out.pushIndentation(indent);
/*      */   }
/*      */ 
/*      */   protected int writeObjectNoOptions(STWriter out, ST self, Object o)
/*      */   {
/*  618 */     int start = out.index();
/*  619 */     int n = writeObject(out, self, o, null);
/*  620 */     if (this.debug) {
/*  621 */       EvalExprEvent e = new EvalExprEvent(this.currentScope, start, out.index() - 1, getExprStartChar(self), getExprStopChar(self));
/*      */ 
/*  625 */       trackDebugEvent(self, e);
/*      */     }
/*  627 */     return n;
/*      */   }
/*      */ 
/*      */   protected int writeObjectWithOptions(STWriter out, ST self, Object o, Object[] options)
/*      */   {
/*  636 */     int start = out.index();
/*      */ 
/*  638 */     String[] optionStrings = null;
/*  639 */     if (options != null) {
/*  640 */       optionStrings = new String[options.length];
/*  641 */       for (int i = 0; i < Compiler.NUM_OPTIONS; i++) {
/*  642 */         optionStrings[i] = toString(out, self, options[i]);
/*      */       }
/*      */     }
/*  645 */     if ((options != null) && (options[Option.ANCHOR.ordinal()] != null)) {
/*  646 */       out.pushAnchorPoint();
/*      */     }
/*      */ 
/*  649 */     int n = writeObject(out, self, o, optionStrings);
/*      */ 
/*  651 */     if ((options != null) && (options[Option.ANCHOR.ordinal()] != null)) {
/*  652 */       out.popAnchorPoint();
/*      */     }
/*  654 */     if (this.debug) {
/*  655 */       EvalExprEvent e = new EvalExprEvent(this.currentScope, start, out.index() - 1, getExprStartChar(self), getExprStopChar(self));
/*      */ 
/*  659 */       trackDebugEvent(self, e);
/*      */     }
/*  661 */     return n;
/*      */   }
/*      */ 
/*      */   protected int writeObject(STWriter out, ST self, Object o, String[] options)
/*      */   {
/*  668 */     int n = 0;
/*  669 */     if (o == null) {
/*  670 */       if ((options != null) && (options[Option.NULL.ordinal()] != null))
/*  671 */         o = options[Option.NULL.ordinal()];
/*      */       else
/*  673 */         return 0;
/*      */     }
/*  675 */     if ((o instanceof ST)) {
/*  676 */       ST st = (ST)o;
/*  677 */       if ((options != null) && (options[Option.WRAP.ordinal()] != null))
/*      */       {
/*      */         try
/*      */         {
/*  681 */           out.writeWrap(options[Option.WRAP.ordinal()]);
/*      */         }
/*      */         catch (IOException ioe) {
/*  684 */           this.errMgr.IOError(self, ErrorType.WRITE_IO_ERROR, ioe);
/*      */         }
/*      */       }
/*  687 */       n = exec(out, st);
/*      */     }
/*      */     else {
/*  690 */       o = convertAnythingIteratableToIterator(o);
/*      */       try {
/*  692 */         if ((o instanceof Iterator)) n = writeIterator(out, self, o, options); else
/*  693 */           n = writePOJO(out, o, options);
/*      */       }
/*      */       catch (IOException ioe) {
/*  696 */         this.errMgr.IOError(self, ErrorType.WRITE_IO_ERROR, ioe, o);
/*      */       }
/*      */     }
/*  699 */     return n;
/*      */   }
/*      */ 
/*      */   protected int writeIterator(STWriter out, ST self, Object o, String[] options) throws IOException {
/*  703 */     if (o == null) return 0;
/*  704 */     int n = 0;
/*  705 */     Iterator it = (Iterator)o;
/*  706 */     String separator = null;
/*  707 */     if (options != null) separator = options[Option.SEPARATOR.ordinal()];
/*  708 */     boolean seenAValue = false;
/*  709 */     while (it.hasNext()) {
/*  710 */       Object iterValue = it.next();
/*      */ 
/*  712 */       boolean needSeparator = (seenAValue) && (separator != null) && ((iterValue != null) || (options[Option.NULL.ordinal()] != null));
/*      */ 
/*  716 */       if (needSeparator) n += out.writeSeparator(separator);
/*  717 */       int nw = writeObject(out, self, iterValue, options);
/*  718 */       if (nw > 0) seenAValue = true;
/*  719 */       n += nw;
/*      */     }
/*  721 */     return n;
/*      */   }
/*      */ 
/*      */   protected int writePOJO(STWriter out, Object o, String[] options) throws IOException {
/*  725 */     String formatString = null;
/*  726 */     if (options != null) formatString = options[Option.FORMAT.ordinal()];
/*      */ 
/*  728 */     AttributeRenderer r = this.currentScope.st.impl.nativeGroup.getAttributeRenderer(o.getClass());
/*      */     String v;
/*      */     String v;
/*  730 */     if (r != null) v = r.toString(o, formatString, this.locale); else
/*  731 */       v = o.toString();
/*      */     int n;
/*      */     int n;
/*  733 */     if ((options != null) && (options[Option.WRAP.ordinal()] != null)) {
/*  734 */       n = out.write(v, options[Option.WRAP.ordinal()]);
/*      */     }
/*      */     else {
/*  737 */       n = out.write(v);
/*      */     }
/*  739 */     return n;
/*      */   }
/*      */ 
/*      */   protected int getExprStartChar(ST self) {
/*  743 */     Interval templateLocation = self.impl.sourceMap[this.current_ip];
/*  744 */     if (templateLocation != null) return templateLocation.a;
/*  745 */     return -1;
/*      */   }
/*      */ 
/*      */   protected int getExprStopChar(ST self) {
/*  749 */     Interval templateLocation = self.impl.sourceMap[this.current_ip];
/*  750 */     if (templateLocation != null) return templateLocation.b;
/*  751 */     return -1;
/*      */   }
/*      */ 
/*      */   protected void map(ST self, Object attr, final ST st) {
/*  755 */     rot_map(self, attr, new ArrayList() {
/*      */     });
/*      */   }
/*      */ 
/*      */   protected void rot_map(ST self, Object attr, List<ST> prototypes) {
/*  760 */     if (attr == null) {
/*  761 */       this.operands[(++this.sp)] = null;
/*  762 */       return;
/*      */     }
/*  764 */     attr = convertAnythingIteratableToIterator(attr);
/*  765 */     if ((attr instanceof Iterator)) {
/*  766 */       List mapped = rot_map_iterator(self, (Iterator)attr, prototypes);
/*  767 */       this.operands[(++this.sp)] = mapped;
/*      */     }
/*      */     else {
/*  770 */       ST proto = (ST)prototypes.get(0);
/*  771 */       ST st = this.group.createStringTemplateInternally(proto);
/*  772 */       if (st != null) {
/*  773 */         setFirstArgument(self, st, attr);
/*  774 */         if (st.impl.isAnonSubtemplate) {
/*  775 */           st.rawSetAttribute("i0", Integer.valueOf(0));
/*  776 */           st.rawSetAttribute("i", Integer.valueOf(1));
/*      */         }
/*  778 */         this.operands[(++this.sp)] = st;
/*      */       }
/*      */       else {
/*  781 */         this.operands[(++this.sp)] = null;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected List<ST> rot_map_iterator(ST self, Iterator attr, List<ST> prototypes) {
/*  787 */     List mapped = new ArrayList();
/*  788 */     Iterator iter = attr;
/*  789 */     int i0 = 0;
/*  790 */     int i = 1;
/*  791 */     int ti = 0;
/*  792 */     while (iter.hasNext()) {
/*  793 */       Object iterValue = iter.next();
/*  794 */       if (iterValue == null) { mapped.add(null); } else {
/*  795 */         int templateIndex = ti % prototypes.size();
/*  796 */         ti++;
/*  797 */         ST proto = (ST)prototypes.get(templateIndex);
/*  798 */         ST st = this.group.createStringTemplateInternally(proto);
/*  799 */         setFirstArgument(self, st, iterValue);
/*  800 */         if (st.impl.isAnonSubtemplate) {
/*  801 */           st.rawSetAttribute("i0", Integer.valueOf(i0));
/*  802 */           st.rawSetAttribute("i", Integer.valueOf(i));
/*      */         }
/*  804 */         mapped.add(st);
/*  805 */         i0++;
/*  806 */         i++;
/*      */       }
/*      */     }
/*  808 */     return mapped;
/*      */   }
/*      */ 
/*      */   protected ST.AttributeList zip_map(ST self, List<Object> exprs, ST prototype)
/*      */   {
/*  814 */     if ((exprs == null) || (prototype == null) || (exprs.size() == 0)) {
/*  815 */       return null;
/*      */     }
/*      */ 
/*  818 */     for (int i = 0; i < exprs.size(); i++) {
/*  819 */       Object attr = exprs.get(i);
/*  820 */       if (attr != null) exprs.set(i, convertAnythingToIterator(attr));
/*      */ 
/*      */     }
/*      */ 
/*  824 */     int numExprs = exprs.size();
/*  825 */     CompiledST code = prototype.impl;
/*  826 */     Map formalArguments = code.formalArguments;
/*  827 */     if ((!code.hasFormalArgs) || (formalArguments == null)) {
/*  828 */       this.errMgr.runTimeError(this, self, this.current_ip, ErrorType.MISSING_FORMAL_ARGUMENTS);
/*  829 */       return null;
/*      */     }
/*      */ 
/*  833 */     Object[] formalArgumentNames = formalArguments.keySet().toArray();
/*  834 */     int nformalArgs = formalArgumentNames.length;
/*  835 */     if (prototype.isAnonSubtemplate()) nformalArgs -= predefinedAnonSubtemplateAttributes.size();
/*  836 */     if (nformalArgs != numExprs) {
/*  837 */       this.errMgr.runTimeError(this, self, this.current_ip, ErrorType.MAP_ARGUMENT_COUNT_MISMATCH, Integer.valueOf(numExprs), Integer.valueOf(nformalArgs));
/*      */ 
/*  844 */       int shorterSize = Math.min(formalArgumentNames.length, numExprs);
/*  845 */       numExprs = shorterSize;
/*  846 */       Object[] newFormalArgumentNames = new Object[shorterSize];
/*  847 */       System.arraycopy(formalArgumentNames, 0, newFormalArgumentNames, 0, shorterSize);
/*      */ 
/*  850 */       formalArgumentNames = newFormalArgumentNames;
/*      */     }
/*      */ 
/*  855 */     ST.AttributeList results = new ST.AttributeList();
/*  856 */     int i = 0;
/*      */     while (true)
/*      */     {
/*  859 */       int numEmpty = 0;
/*  860 */       ST embedded = this.group.createStringTemplateInternally(prototype);
/*  861 */       embedded.rawSetAttribute("i0", Integer.valueOf(i));
/*  862 */       embedded.rawSetAttribute("i", Integer.valueOf(i + 1));
/*  863 */       for (int a = 0; a < numExprs; a++) {
/*  864 */         Iterator it = (Iterator)exprs.get(a);
/*  865 */         if ((it != null) && (it.hasNext())) {
/*  866 */           String argName = (String)formalArgumentNames[a];
/*  867 */           Object iteratedValue = it.next();
/*  868 */           embedded.rawSetAttribute(argName, iteratedValue);
/*      */         }
/*      */         else {
/*  871 */           numEmpty++;
/*      */         }
/*      */       }
/*  874 */       if (numEmpty == numExprs) break;
/*  875 */       results.add(embedded);
/*  876 */       i++;
/*      */     }
/*  878 */     return results;
/*      */   }
/*      */ 
/*      */   protected void setFirstArgument(ST self, ST st, Object attr) {
/*  882 */     if (st.impl.formalArguments == null) {
/*  883 */       this.errMgr.runTimeError(this, self, this.current_ip, ErrorType.ARGUMENT_COUNT_MISMATCH, Integer.valueOf(1), st.impl.name, Integer.valueOf(0));
/*      */ 
/*  889 */       return;
/*      */     }
/*  891 */     st.locals[0] = attr;
/*      */   }
/*      */ 
/*      */   protected void addToList(List<Object> list, Object o) {
/*  895 */     o = convertAnythingIteratableToIterator(o);
/*  896 */     if ((o instanceof Iterator))
/*      */     {
/*  898 */       Iterator it = (Iterator)o;
/*  899 */       while (it.hasNext()) list.add(it.next()); 
/*      */     }
/*      */     else
/*      */     {
/*  902 */       list.add(o);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Object first(Object v)
/*      */   {
/*  910 */     if (v == null) return null;
/*  911 */     Object r = v;
/*  912 */     v = convertAnythingIteratableToIterator(v);
/*  913 */     if ((v instanceof Iterator)) {
/*  914 */       Iterator it = (Iterator)v;
/*  915 */       if (it.hasNext()) {
/*  916 */         r = it.next();
/*      */       }
/*      */     }
/*  919 */     return r;
/*      */   }
/*      */ 
/*      */   public Object last(Object v)
/*      */   {
/*  927 */     if (v == null) return null;
/*  928 */     if ((v instanceof List)) return ((List)v).get(((List)v).size() - 1);
/*  929 */     if (v.getClass().isArray()) {
/*  930 */       Object[] elems = (Object[])v;
/*  931 */       return elems[(elems.length - 1)];
/*      */     }
/*  933 */     Object last = v;
/*  934 */     v = convertAnythingIteratableToIterator(v);
/*  935 */     if ((v instanceof Iterator)) {
/*  936 */       Iterator it = (Iterator)v;
/*  937 */       while (it.hasNext()) {
/*  938 */         last = it.next();
/*      */       }
/*      */     }
/*  941 */     return last;
/*      */   }
/*      */ 
/*      */   public Object rest(Object v)
/*      */   {
/*  948 */     if (v == null) return null;
/*  949 */     if ((v instanceof List)) {
/*  950 */       List elems = (List)v;
/*  951 */       if (elems.size() <= 1) return null;
/*  952 */       return elems.subList(1, elems.size());
/*      */     }
/*  954 */     v = convertAnythingIteratableToIterator(v);
/*  955 */     if ((v instanceof Iterator)) {
/*  956 */       List a = new ArrayList();
/*  957 */       Iterator it = (Iterator)v;
/*  958 */       if (!it.hasNext()) return null;
/*  959 */       it.next();
/*  960 */       while (it.hasNext()) {
/*  961 */         Object o = it.next();
/*  962 */         a.add(o);
/*      */       }
/*  964 */       return a;
/*      */     }
/*  966 */     return null;
/*      */   }
/*      */ 
/*      */   public Object trunc(Object v)
/*      */   {
/*  971 */     if (v == null) return null;
/*  972 */     if ((v instanceof List)) {
/*  973 */       List elems = (List)v;
/*  974 */       if (elems.size() <= 1) return null;
/*  975 */       return elems.subList(0, elems.size() - 1);
/*      */     }
/*  977 */     v = convertAnythingIteratableToIterator(v);
/*  978 */     if ((v instanceof Iterator)) {
/*  979 */       List a = new ArrayList();
/*  980 */       Iterator it = (Iterator)v;
/*  981 */       while (it.hasNext()) {
/*  982 */         Object o = it.next();
/*  983 */         if (it.hasNext()) a.add(o);
/*      */       }
/*  985 */       return a;
/*      */     }
/*  987 */     return null;
/*      */   }
/*      */ 
/*      */   public Object strip(Object v)
/*      */   {
/*  992 */     if (v == null) return null;
/*  993 */     v = convertAnythingIteratableToIterator(v);
/*  994 */     if ((v instanceof Iterator)) {
/*  995 */       List a = new ArrayList();
/*  996 */       Iterator it = (Iterator)v;
/*  997 */       while (it.hasNext()) {
/*  998 */         Object o = it.next();
/*  999 */         if (o != null) a.add(o);
/*      */       }
/* 1001 */       return a;
/*      */     }
/* 1003 */     return v;
/*      */   }
/*      */ 
/*      */   public Object reverse(Object v)
/*      */   {
/* 1010 */     if (v == null) return null;
/* 1011 */     v = convertAnythingIteratableToIterator(v);
/* 1012 */     if ((v instanceof Iterator)) {
/* 1013 */       List a = new LinkedList();
/* 1014 */       Iterator it = (Iterator)v;
/* 1015 */       while (it.hasNext()) a.add(0, it.next());
/* 1016 */       return a;
/*      */     }
/* 1018 */     return v;
/*      */   }
/*      */ 
/*      */   public Object length(Object v)
/*      */   {
/* 1027 */     if (v == null) return Integer.valueOf(0);
/* 1028 */     int i = 1;
/* 1029 */     if ((v instanceof Map)) { i = ((Map)v).size();
/* 1030 */     } else if ((v instanceof Collection)) { i = ((Collection)v).size();
/* 1031 */     } else if ((v instanceof Object[])) { i = ((Object[])v).length;
/* 1032 */     } else if ((v instanceof int[])) { i = ((int[])v).length;
/* 1033 */     } else if ((v instanceof long[])) { i = ((long[])v).length;
/* 1034 */     } else if ((v instanceof float[])) { i = ((float[])v).length;
/* 1035 */     } else if ((v instanceof double[])) { i = ((double[])v).length;
/* 1036 */     } else if ((v instanceof Iterator)) {
/* 1037 */       Iterator it = (Iterator)v;
/* 1038 */       i = 0;
/* 1039 */       while (it.hasNext()) {
/* 1040 */         it.next();
/* 1041 */         i++;
/*      */       }
/*      */     }
/* 1044 */     return Integer.valueOf(i);
/*      */   }
/*      */ 
/*      */   protected String toString(STWriter out, ST self, Object value) {
/* 1048 */     if (value != null) {
/* 1049 */       if (value.getClass() == String.class) return (String)value;
/*      */ 
/* 1051 */       StringWriter sw = new StringWriter();
/* 1052 */       STWriter stw = null;
/*      */       try {
/* 1054 */         Class writerClass = out.getClass();
/* 1055 */         Constructor ctor = writerClass.getConstructor(new Class[] { Writer.class });
/*      */ 
/* 1057 */         stw = (STWriter)ctor.newInstance(new Object[] { sw });
/*      */       }
/*      */       catch (Exception e) {
/* 1060 */         stw = new AutoIndentWriter(sw);
/* 1061 */         this.errMgr.runTimeError(this, self, this.current_ip, ErrorType.WRITER_CTOR_ISSUE, out.getClass().getSimpleName());
/*      */       }
/* 1063 */       writeObjectNoOptions(stw, self, value);
/*      */ 
/* 1065 */       return sw.toString();
/*      */     }
/* 1067 */     return null;
/*      */   }
/*      */ 
/*      */   public Object convertAnythingIteratableToIterator(Object o) {
/* 1071 */     Iterator iter = null;
/* 1072 */     if (o == null) return null;
/* 1073 */     if ((o instanceof Collection)) iter = ((Collection)o).iterator();
/* 1074 */     else if (o.getClass().isArray()) iter = new ArrayIterator(o);
/* 1075 */     else if ((this.currentScope.st.groupThatCreatedThisInstance.iterateAcrossValues) && ((o instanceof Map)))
/*      */     {
/* 1078 */       iter = ((Map)o).values().iterator();
/*      */     }
/* 1080 */     else if ((o instanceof Map)) iter = ((Map)o).keySet().iterator();
/* 1081 */     else if ((o instanceof Iterator)) iter = (Iterator)o;
/* 1082 */     if (iter == null) return o;
/* 1083 */     return iter;
/*      */   }
/*      */ 
/*      */   public Iterator convertAnythingToIterator(Object o) {
/* 1087 */     o = convertAnythingIteratableToIterator(o);
/* 1088 */     if ((o instanceof Iterator)) return (Iterator)o;
/* 1089 */     List singleton = new ST.AttributeList(1);
/* 1090 */     singleton.add(o);
/* 1091 */     return singleton.iterator();
/*      */   }
/*      */ 
/*      */   protected boolean testAttributeTrue(Object a) {
/* 1095 */     if (a == null) return false;
/* 1096 */     if ((a instanceof Boolean)) return ((Boolean)a).booleanValue();
/* 1097 */     if ((a instanceof Collection)) return ((Collection)a).size() > 0;
/* 1098 */     if ((a instanceof Map)) return ((Map)a).size() > 0;
/* 1099 */     if ((a instanceof Iterator)) return ((Iterator)a).hasNext();
/* 1100 */     return true;
/*      */   }
/*      */ 
/*      */   protected Object getObjectProperty(STWriter out, ST self, Object o, Object property) {
/* 1104 */     if (o == null) {
/* 1105 */       this.errMgr.runTimeError(this, self, this.current_ip, ErrorType.NO_SUCH_PROPERTY, "null attribute");
/*      */ 
/* 1107 */       return null;
/*      */     }
/*      */     try
/*      */     {
/* 1111 */       ModelAdaptor adap = self.groupThatCreatedThisInstance.getModelAdaptor(o.getClass());
/* 1112 */       return adap.getProperty(this, self, o, property, toString(out, self, property));
/*      */     }
/*      */     catch (STNoSuchPropertyException e) {
/* 1115 */       this.errMgr.runTimeError(this, self, this.current_ip, ErrorType.NO_SUCH_PROPERTY, e, o.getClass().getName() + "." + property);
/*      */     }
/*      */ 
/* 1118 */     return null;
/*      */   }
/*      */ 
/*      */   public Object getAttribute(ST self, String name)
/*      */   {
/* 1128 */     InstanceScope scope = this.currentScope;
/* 1129 */     while (scope != null) {
/* 1130 */       ST p = scope.st;
/* 1131 */       FormalArgument localArg = null;
/* 1132 */       if (p.impl.formalArguments != null) localArg = (FormalArgument)p.impl.formalArguments.get(name);
/* 1133 */       if (localArg != null) {
/* 1134 */         Object o = p.locals[localArg.index];
/* 1135 */         return o;
/*      */       }
/* 1137 */       scope = scope.parent;
/*      */     }
/*      */ 
/* 1140 */     STGroup g = self.impl.nativeGroup;
/* 1141 */     Object o = getDictionary(g, name);
/* 1142 */     if (o != null) return o;
/*      */ 
/* 1145 */     if (ST.cachedNoSuchAttrException == null) {
/* 1146 */       ST.cachedNoSuchAttrException = new STNoSuchAttributeException();
/*      */     }
/* 1148 */     ST.cachedNoSuchAttrException.name = name;
/* 1149 */     ST.cachedNoSuchAttrException.scope = this.currentScope;
/* 1150 */     throw ST.cachedNoSuchAttrException;
/*      */   }
/*      */ 
/*      */   public Object getDictionary(STGroup g, String name) {
/* 1154 */     if (g.isDictionary(name)) {
/* 1155 */       return g.rawGetDictionary(name);
/*      */     }
/* 1157 */     if (g.imports != null) {
/* 1158 */       for (STGroup sup : g.imports) {
/* 1159 */         Object o = getDictionary(sup, name);
/* 1160 */         if (o != null) return o;
/*      */       }
/*      */     }
/* 1163 */     return null;
/*      */   }
/*      */ 
/*      */   public void setDefaultArguments(STWriter out, ST invokedST)
/*      */   {
/* 1174 */     if ((invokedST.impl.formalArguments == null) || (invokedST.impl.numberOfArgsWithDefaultValues == 0))
/*      */     {
/* 1176 */       return;
/*      */     }
/* 1178 */     for (FormalArgument arg : invokedST.impl.formalArguments.values())
/*      */     {
/* 1180 */       if ((invokedST.locals[arg.index] == ST.EMPTY_ATTR) && (arg.defaultValueToken != null))
/*      */       {
/* 1184 */         if (arg.defaultValueToken.getType() == 4) {
/* 1185 */           CompiledST code = arg.compiledDefaultValue;
/* 1186 */           if (code == null) {
/* 1187 */             code = new CompiledST();
/*      */           }
/* 1189 */           ST defaultArgST = this.group.createStringTemplateInternally(code);
/* 1190 */           defaultArgST.groupThatCreatedThisInstance = this.group;
/*      */ 
/* 1195 */           String defArgTemplate = arg.defaultValueToken.getText();
/* 1196 */           if ((defArgTemplate.startsWith("{" + this.group.delimiterStartChar + "(")) && (defArgTemplate.endsWith(")" + this.group.delimiterStopChar + "}")))
/*      */           {
/* 1199 */             invokedST.rawSetAttribute(arg.name, toString(out, invokedST, defaultArgST));
/*      */           }
/*      */           else
/* 1202 */             invokedST.rawSetAttribute(arg.name, defaultArgST);
/*      */         }
/*      */         else
/*      */         {
/* 1206 */           invokedST.rawSetAttribute(arg.name, arg.defaultValue);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/* 1212 */   private void popScope() { this.current_ip = this.currentScope.ret_ip;
/* 1213 */     this.currentScope = this.currentScope.parent; }
/*      */ 
/*      */   private void pushScope(ST self)
/*      */   {
/* 1217 */     this.currentScope = new InstanceScope(this.currentScope, self);
/* 1218 */     if (this.debug) {
/* 1219 */       this.currentScope.events = new ArrayList();
/* 1220 */       this.currentScope.childEvalTemplateEvents = new ArrayList();
/*      */     }
/* 1222 */     this.currentScope.ret_ip = this.current_ip;
/*      */   }
/*      */ 
/*      */   public static String getEnclosingInstanceStackString(InstanceScope scope)
/*      */   {
/* 1230 */     List templates = getEnclosingInstanceStack(scope, true);
/* 1231 */     StringBuilder buf = new StringBuilder();
/* 1232 */     int i = 0;
/* 1233 */     for (ST st : templates) {
/* 1234 */       if (i > 0) buf.append(" ");
/* 1235 */       buf.append(st.getName());
/* 1236 */       i++;
/*      */     }
/* 1238 */     return buf.toString();
/*      */   }
/*      */ 
/*      */   public static List<ST> getEnclosingInstanceStack(InstanceScope scope, boolean topdown) {
/* 1242 */     List stack = new LinkedList();
/* 1243 */     InstanceScope p = scope;
/* 1244 */     while (p != null) {
/* 1245 */       if (topdown) stack.add(0, p.st); else
/* 1246 */         stack.add(p.st);
/* 1247 */       p = p.parent;
/*      */     }
/* 1249 */     return stack;
/*      */   }
/*      */ 
/*      */   public static List<InstanceScope> getScopeStack(InstanceScope scope, boolean topdown) {
/* 1253 */     List stack = new LinkedList();
/* 1254 */     InstanceScope p = scope;
/* 1255 */     while (p != null) {
/* 1256 */       if (topdown) stack.add(0, p); else
/* 1257 */         stack.add(p);
/* 1258 */       p = p.parent;
/*      */     }
/* 1260 */     return stack;
/*      */   }
/*      */ 
/*      */   public static List<EvalTemplateEvent> getEvalTemplateEventStack(InstanceScope scope, boolean topdown) {
/* 1264 */     List stack = new LinkedList();
/* 1265 */     InstanceScope p = scope;
/* 1266 */     while (p != null) {
/* 1267 */       EvalTemplateEvent eval = (EvalTemplateEvent)p.events.get(p.events.size() - 1);
/* 1268 */       if (topdown) stack.add(0, eval); else
/* 1269 */         stack.add(eval);
/* 1270 */       p = p.parent;
/*      */     }
/* 1272 */     return stack;
/*      */   }
/*      */ 
/*      */   protected void trace(ST self, int ip) {
/* 1276 */     StringBuilder tr = new StringBuilder();
/* 1277 */     BytecodeDisassembler dis = new BytecodeDisassembler(self.impl);
/* 1278 */     StringBuilder buf = new StringBuilder();
/* 1279 */     dis.disassembleInstruction(buf, ip);
/* 1280 */     String name = self.impl.name + ":";
/* 1281 */     if (self.impl.name == "anonymous") name = "";
/* 1282 */     tr.append(String.format("%-40s", new Object[] { name + buf }));
/* 1283 */     tr.append("\tstack=[");
/* 1284 */     for (int i = 0; i <= this.sp; i++) {
/* 1285 */       Object o = this.operands[i];
/* 1286 */       printForTrace(tr, o);
/*      */     }
/* 1288 */     tr.append(" ], calls=");
/* 1289 */     tr.append(getEnclosingInstanceStackString(this.currentScope));
/* 1290 */     tr.append(", sp=" + this.sp + ", nw=" + this.nwline);
/* 1291 */     String s = tr.toString();
/* 1292 */     if (this.debug) this.executeTrace.add(s);
/* 1293 */     if (trace) System.out.println(s); 
/*      */   }
/*      */ 
/*      */   protected void printForTrace(StringBuilder tr, Object o)
/*      */   {
/* 1297 */     if ((o instanceof ST)) {
/* 1298 */       if (((ST)o).impl == null) tr.append("bad-template()"); else
/* 1299 */         tr.append(" " + ((ST)o).impl.name + "()");
/* 1300 */       return;
/*      */     }
/* 1302 */     o = convertAnythingIteratableToIterator(o);
/* 1303 */     if ((o instanceof Iterator)) {
/* 1304 */       Iterator it = (Iterator)o;
/* 1305 */       tr.append(" [");
/* 1306 */       while (it.hasNext()) {
/* 1307 */         Object iterValue = it.next();
/* 1308 */         printForTrace(tr, iterValue);
/*      */       }
/* 1310 */       tr.append(" ]");
/*      */     }
/*      */     else {
/* 1313 */       tr.append(" " + o);
/*      */     }
/*      */   }
/*      */ 
/* 1317 */   public List<InterpEvent> getEvents() { return this.events; }
/*      */ 
/*      */ 
/*      */   protected void trackDebugEvent(ST self, InterpEvent e)
/*      */   {
/* 1326 */     this.events.add(e);
/* 1327 */     this.currentScope.events.add(e);
/* 1328 */     if ((e instanceof EvalTemplateEvent)) {
/* 1329 */       InstanceScope parent = this.currentScope.parent;
/* 1330 */       if (parent != null)
/*      */       {
/* 1332 */         this.currentScope.parent.childEvalTemplateEvents.add((EvalTemplateEvent)e);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/* 1337 */   public List<String> getExecutionTrace() { return this.executeTrace; }
/*      */ 
/*      */   public static int getShort(byte[] memory, int index) {
/* 1340 */     int b1 = memory[index] & 0xFF;
/* 1341 */     int b2 = memory[(index + 1)] & 0xFF;
/* 1342 */     return b1 << 8 | b2;
/*      */   }
/*      */ 
/*      */   public static enum Option
/*      */   {
/*   57 */     ANCHOR, FORMAT, NULL, SEPARATOR, WRAP;
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.Interpreter
 * JD-Core Version:    0.6.2
 */