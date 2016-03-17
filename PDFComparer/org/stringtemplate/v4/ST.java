/*     */ package org.stringtemplate.v4;
/*     */ 
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.io.Writer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.stringtemplate.v4.compiler.CompiledST;
/*     */ import org.stringtemplate.v4.compiler.FormalArgument;
/*     */ import org.stringtemplate.v4.debug.AddAttributeEvent;
/*     */ import org.stringtemplate.v4.debug.ConstructionEvent;
/*     */ import org.stringtemplate.v4.debug.EvalTemplateEvent;
/*     */ import org.stringtemplate.v4.debug.InterpEvent;
/*     */ import org.stringtemplate.v4.gui.STViz;
/*     */ import org.stringtemplate.v4.misc.Aggregate;
/*     */ import org.stringtemplate.v4.misc.ErrorBuffer;
/*     */ import org.stringtemplate.v4.misc.ErrorManager;
/*     */ import org.stringtemplate.v4.misc.MultiMap;
/*     */ import org.stringtemplate.v4.misc.STNoSuchAttributeException;
/*     */ 
/*     */ public class ST
/*     */ {
/*     */   public static final String VERSION = "4.0.5";
/*     */   public static final String UNKNOWN_NAME = "anonymous";
/*  68 */   public static final Object EMPTY_ATTR = new Object();
/*     */   public static STNoSuchAttributeException cachedNoSuchAttrException;
/*     */   public CompiledST impl;
/*     */   protected Object[] locals;
/*     */   public STGroup groupThatCreatedThisInstance;
/*     */   public DebugState debugState;
/*     */ 
/*     */   protected ST()
/*     */   {
/* 118 */     if (STGroup.trackCreationEvents) {
/* 119 */       if (this.debugState == null) this.debugState = new DebugState();
/* 120 */       this.debugState.newSTEvent = new ConstructionEvent();
/*     */     }
/*     */   }
/*     */ 
/*     */   public ST(String template)
/*     */   {
/* 128 */     this(STGroup.defaultGroup, template);
/*     */   }
/*     */ 
/*     */   public ST(String template, char delimiterStartChar, char delimiterStopChar)
/*     */   {
/* 136 */     this(new STGroup(delimiterStartChar, delimiterStopChar), template);
/*     */   }
/*     */ 
/*     */   public ST(STGroup group, String template) {
/* 140 */     this();
/* 141 */     this.groupThatCreatedThisInstance = group;
/* 142 */     this.impl = this.groupThatCreatedThisInstance.compile(group.getFileName(), null, null, template, null);
/*     */ 
/* 144 */     this.impl.hasFormalArgs = false;
/* 145 */     this.impl.name = "anonymous";
/* 146 */     this.impl.defineImplicitlyDefinedTemplates(this.groupThatCreatedThisInstance);
/*     */   }
/*     */ 
/*     */   public ST(ST proto)
/*     */   {
/* 154 */     this.impl = proto.impl;
/* 155 */     if (proto.locals != null)
/*     */     {
/* 157 */       this.locals = new Object[proto.locals.length];
/* 158 */       System.arraycopy(proto.locals, 0, this.locals, 0, proto.locals.length);
/*     */     }
/* 160 */     this.groupThatCreatedThisInstance = proto.groupThatCreatedThisInstance;
/*     */   }
/*     */ 
/*     */   public synchronized ST add(String name, Object value)
/*     */   {
/* 173 */     if (name == null) return this;
/* 174 */     if (name.indexOf('.') >= 0) {
/* 175 */       throw new IllegalArgumentException("cannot have '.' in attribute names");
/*     */     }
/*     */ 
/* 178 */     if (STGroup.trackCreationEvents) {
/* 179 */       if (this.debugState == null) this.debugState = new DebugState();
/* 180 */       this.debugState.addAttrEvents.map(name, new AddAttributeEvent(name, value));
/*     */     }
/*     */ 
/* 183 */     FormalArgument arg = null;
/* 184 */     if (this.impl.hasFormalArgs) {
/* 185 */       if (this.impl.formalArguments != null) arg = (FormalArgument)this.impl.formalArguments.get(name);
/* 186 */       if (arg == null) {
/* 187 */         throw new IllegalArgumentException("no such attribute: " + name);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 192 */       if (this.impl.formalArguments != null) {
/* 193 */         arg = (FormalArgument)this.impl.formalArguments.get(name);
/*     */       }
/* 195 */       if (arg == null) {
/* 196 */         arg = new FormalArgument(name);
/* 197 */         this.impl.addArg(arg);
/* 198 */         if (this.locals == null) { this.locals = new Object[1];
/*     */         } else
/*     */         {
/* 201 */           Object[] copy = new Object[this.impl.formalArguments.size()];
/* 202 */           System.arraycopy(this.locals, 0, copy, 0, Math.min(this.locals.length, this.impl.formalArguments.size()));
/*     */ 
/* 204 */           this.locals = copy;
/*     */         }
/* 206 */         this.locals[arg.index] = EMPTY_ATTR;
/*     */       }
/*     */     }
/*     */ 
/* 210 */     Object curvalue = this.locals[arg.index];
/* 211 */     if (curvalue == EMPTY_ATTR) {
/* 212 */       this.locals[arg.index] = value;
/* 213 */       return this;
/*     */     }
/*     */ 
/* 219 */     AttributeList multi = convertToAttributeList(curvalue);
/* 220 */     this.locals[arg.index] = multi;
/*     */ 
/* 223 */     if ((value instanceof List))
/*     */     {
/* 225 */       multi.addAll((List)value);
/*     */     }
/* 227 */     else if ((value != null) && (value.getClass().isArray())) {
/* 228 */       multi.addAll(Arrays.asList(new Object[] { value }));
/*     */     }
/*     */     else {
/* 231 */       multi.add(value);
/*     */     }
/* 233 */     return this;
/*     */   }
/*     */ 
/*     */   public synchronized ST addAggr(String aggrSpec, Object[] values)
/*     */   {
/* 240 */     int dot = aggrSpec.indexOf(".{");
/* 241 */     if ((values == null) || (values.length == 0)) {
/* 242 */       throw new IllegalArgumentException("missing values for aggregate attribute format: " + aggrSpec);
/*     */     }
/*     */ 
/* 245 */     int finalCurly = aggrSpec.indexOf('}');
/* 246 */     if ((dot < 0) || (finalCurly < 0)) {
/* 247 */       throw new IllegalArgumentException("invalid aggregate attribute format: " + aggrSpec);
/*     */     }
/*     */ 
/* 250 */     String aggrName = aggrSpec.substring(0, dot);
/* 251 */     String propString = aggrSpec.substring(dot + 2, aggrSpec.length() - 1);
/* 252 */     propString = propString.trim();
/* 253 */     String[] propNames = propString.split("\\ *,\\ *");
/* 254 */     if ((propNames == null) || (propNames.length == 0)) {
/* 255 */       throw new IllegalArgumentException("invalid aggregate attribute format: " + aggrSpec);
/*     */     }
/*     */ 
/* 258 */     if (values.length != propNames.length) {
/* 259 */       throw new IllegalArgumentException("number of properties and values mismatch for aggregate attribute format: " + aggrSpec);
/*     */     }
/*     */ 
/* 263 */     int i = 0;
/* 264 */     Aggregate aggr = new Aggregate();
/* 265 */     for (String p : propNames) {
/* 266 */       Object v = values[(i++)];
/* 267 */       aggr.properties.put(p, v);
/*     */     }
/*     */ 
/* 270 */     add(aggrName, aggr);
/* 271 */     return this;
/*     */   }
/*     */ 
/*     */   public void remove(String name)
/*     */   {
/* 276 */     if (this.impl.formalArguments == null) {
/* 277 */       if (this.impl.hasFormalArgs) {
/* 278 */         throw new IllegalArgumentException("no such attribute: " + name);
/*     */       }
/* 280 */       return;
/*     */     }
/* 282 */     FormalArgument arg = (FormalArgument)this.impl.formalArguments.get(name);
/* 283 */     if (arg == null) {
/* 284 */       throw new IllegalArgumentException("no such attribute: " + name);
/*     */     }
/* 286 */     this.locals[arg.index] = EMPTY_ATTR;
/*     */   }
/*     */ 
/*     */   protected void rawSetAttribute(String name, Object value)
/*     */   {
/* 294 */     if (this.impl.formalArguments == null) {
/* 295 */       throw new IllegalArgumentException("no such attribute: " + name);
/*     */     }
/* 297 */     FormalArgument arg = (FormalArgument)this.impl.formalArguments.get(name);
/* 298 */     if (arg == null) {
/* 299 */       throw new IllegalArgumentException("no such attribute: " + name);
/*     */     }
/* 301 */     this.locals[arg.index] = value;
/*     */   }
/*     */ 
/*     */   public Object getAttribute(String name)
/*     */   {
/* 306 */     FormalArgument localArg = null;
/* 307 */     if (this.impl.formalArguments != null) localArg = (FormalArgument)this.impl.formalArguments.get(name);
/* 308 */     if (localArg != null) {
/* 309 */       Object o = this.locals[localArg.index];
/* 310 */       if (o == EMPTY_ATTR) o = null;
/* 311 */       return o;
/*     */     }
/* 313 */     return null;
/*     */   }
/*     */ 
/*     */   public Map<String, Object> getAttributes() {
/* 317 */     if (this.impl.formalArguments == null) return null;
/* 318 */     Map attributes = new HashMap();
/* 319 */     for (FormalArgument a : this.impl.formalArguments.values()) {
/* 320 */       Object o = this.locals[a.index];
/* 321 */       if (o == EMPTY_ATTR) o = null;
/* 322 */       attributes.put(a.name, o);
/*     */     }
/* 324 */     return attributes;
/*     */   }
/*     */ 
/*     */   protected static AttributeList<Object> convertToAttributeList(Object curvalue)
/*     */   {
/*     */     AttributeList multi;
/* 329 */     if (curvalue == null) {
/* 330 */       AttributeList multi = new AttributeList();
/* 331 */       multi.add(curvalue);
/*     */     }
/*     */     else
/*     */     {
/*     */       AttributeList multi;
/* 333 */       if (curvalue.getClass() == AttributeList.class) {
/* 334 */         multi = (AttributeList)curvalue;
/*     */       }
/* 336 */       else if ((curvalue instanceof List))
/*     */       {
/* 339 */         List listAttr = (List)curvalue;
/* 340 */         AttributeList multi = new AttributeList(listAttr.size());
/* 341 */         multi.addAll(listAttr);
/*     */       }
/* 343 */       else if (curvalue.getClass().isArray()) {
/* 344 */         Object[] a = (Object[])curvalue;
/* 345 */         AttributeList multi = new AttributeList(a.length);
/* 346 */         multi.addAll(Arrays.asList(a));
/*     */       }
/*     */       else
/*     */       {
/* 351 */         multi = new AttributeList();
/* 352 */         multi.add(curvalue);
/*     */       }
/*     */     }
/* 354 */     return multi;
/*     */   }
/*     */   public String getName() {
/* 357 */     return this.impl.name;
/*     */   }
/* 359 */   public boolean isAnonSubtemplate() { return this.impl.isAnonSubtemplate; }
/*     */ 
/*     */   public int write(STWriter out) throws IOException {
/* 362 */     Interpreter interp = new Interpreter(this.groupThatCreatedThisInstance, this.impl.nativeGroup.errMgr, false);
/*     */ 
/* 365 */     return interp.exec(out, this);
/*     */   }
/*     */ 
/*     */   public int write(STWriter out, Locale locale) {
/* 369 */     Interpreter interp = new Interpreter(this.groupThatCreatedThisInstance, locale, this.impl.nativeGroup.errMgr, false);
/*     */ 
/* 373 */     return interp.exec(out, this);
/*     */   }
/*     */ 
/*     */   public int write(STWriter out, STErrorListener listener) {
/* 377 */     Interpreter interp = new Interpreter(this.groupThatCreatedThisInstance, new ErrorManager(listener), false);
/*     */ 
/* 380 */     return interp.exec(out, this);
/*     */   }
/*     */ 
/*     */   public int write(STWriter out, Locale locale, STErrorListener listener) {
/* 384 */     Interpreter interp = new Interpreter(this.groupThatCreatedThisInstance, locale, new ErrorManager(listener), false);
/*     */ 
/* 388 */     return interp.exec(out, this);
/*     */   }
/*     */ 
/*     */   public int write(File outputFile, STErrorListener listener) throws IOException {
/* 392 */     return write(outputFile, listener, "UTF-8", Locale.getDefault(), -1);
/*     */   }
/*     */ 
/*     */   public int write(File outputFile, STErrorListener listener, String encoding)
/*     */     throws IOException
/*     */   {
/* 398 */     return write(outputFile, listener, encoding, Locale.getDefault(), -1);
/*     */   }
/*     */ 
/*     */   public int write(File outputFile, STErrorListener listener, String encoding, int lineWidth)
/*     */     throws IOException
/*     */   {
/* 404 */     return write(outputFile, listener, encoding, Locale.getDefault(), lineWidth);
/*     */   }
/*     */ 
/*     */   public int write(File outputFile, STErrorListener listener, String encoding, Locale locale, int lineWidth)
/*     */     throws IOException
/*     */   {
/* 414 */     Writer bw = null;
/*     */     try {
/* 416 */       FileOutputStream fos = new FileOutputStream(outputFile);
/* 417 */       OutputStreamWriter osw = new OutputStreamWriter(fos, encoding);
/* 418 */       bw = new BufferedWriter(osw);
/* 419 */       AutoIndentWriter w = new AutoIndentWriter(bw);
/* 420 */       w.setLineWidth(lineWidth);
/* 421 */       int n = write(w, locale, listener);
/* 422 */       bw.close();
/* 423 */       bw = null;
/* 424 */       return n;
/*     */     }
/*     */     finally {
/* 427 */       if (bw != null) bw.close(); 
/*     */     }
/*     */   }
/*     */ 
/* 431 */   public String render() { return render(Locale.getDefault()); } 
/*     */   public String render(int lineWidth) {
/* 433 */     return render(Locale.getDefault(), lineWidth);
/*     */   }
/* 435 */   public String render(Locale locale) { return render(locale, -1); }
/*     */ 
/*     */   public String render(Locale locale, int lineWidth) {
/* 438 */     StringWriter out = new StringWriter();
/* 439 */     STWriter wr = new AutoIndentWriter(out);
/* 440 */     wr.setLineWidth(lineWidth);
/* 441 */     write(wr, locale);
/* 442 */     return out.toString();
/*     */   }
/*     */ 
/*     */   public STViz inspect()
/*     */   {
/* 447 */     return inspect(Locale.getDefault());
/*     */   }
/*     */   public STViz inspect(int lineWidth) {
/* 450 */     return inspect(this.impl.nativeGroup.errMgr, Locale.getDefault(), lineWidth);
/*     */   }
/*     */ 
/*     */   public STViz inspect(Locale locale) {
/* 454 */     return inspect(this.impl.nativeGroup.errMgr, locale, -1);
/*     */   }
/*     */ 
/*     */   public STViz inspect(ErrorManager errMgr, Locale locale, int lineWidth) {
/* 458 */     ErrorBuffer errors = new ErrorBuffer();
/* 459 */     this.impl.nativeGroup.setListener(errors);
/* 460 */     StringWriter out = new StringWriter();
/* 461 */     STWriter wr = new AutoIndentWriter(out);
/* 462 */     wr.setLineWidth(lineWidth);
/* 463 */     Interpreter interp = new Interpreter(this.groupThatCreatedThisInstance, locale, true);
/*     */ 
/* 465 */     interp.exec(wr, this);
/* 466 */     List events = interp.getEvents();
/* 467 */     EvalTemplateEvent overallTemplateEval = (EvalTemplateEvent)events.get(events.size() - 1);
/*     */ 
/* 469 */     STViz viz = new STViz(errMgr, overallTemplateEval, out.toString(), interp, interp.getExecutionTrace(), errors.errors);
/*     */ 
/* 471 */     viz.open();
/* 472 */     return viz;
/*     */   }
/*     */ 
/*     */   public List<InterpEvent> getEvents()
/*     */   {
/* 477 */     return getEvents(Locale.getDefault());
/*     */   }
/* 479 */   public List<InterpEvent> getEvents(int lineWidth) { return getEvents(Locale.getDefault(), lineWidth); } 
/*     */   public List<InterpEvent> getEvents(Locale locale) {
/* 481 */     return getEvents(locale, -1);
/*     */   }
/*     */   public List<InterpEvent> getEvents(Locale locale, int lineWidth) {
/* 484 */     StringWriter out = new StringWriter();
/* 485 */     STWriter wr = new AutoIndentWriter(out);
/* 486 */     wr.setLineWidth(lineWidth);
/* 487 */     Interpreter interp = new Interpreter(this.groupThatCreatedThisInstance, locale, true);
/*     */ 
/* 489 */     interp.exec(wr, this);
/* 490 */     return interp.getEvents();
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 494 */     if (this.impl == null) return "bad-template()";
/* 495 */     String name = this.impl.name + "()";
/* 496 */     if (this.impl.isRegion) {
/* 497 */       name = "@" + STGroup.getUnMangledTemplateName(name);
/*     */     }
/*     */ 
/* 500 */     return name;
/*     */   }
/*     */ 
/*     */   public static String format(String template, Object[] attributes)
/*     */   {
/* 507 */     return format(-1, template, attributes);
/*     */   }
/*     */ 
/*     */   public static String format(int lineWidth, String template, Object[] attributes) {
/* 511 */     template = template.replaceAll("%([0-9]+)", "arg$1");
/* 512 */     ST st = new ST(template);
/* 513 */     int i = 1;
/* 514 */     for (Object a : attributes) {
/* 515 */       st.add("arg" + i, a);
/* 516 */       i++;
/*     */     }
/* 518 */     return st.render(lineWidth);
/*     */   }
/*     */ 
/*     */   public static final class AttributeList<T> extends ArrayList<T>
/*     */   {
/*     */     public AttributeList(int size)
/*     */     {
/* 112 */       super();
/*     */     }
/*     */ 
/*     */     public AttributeList()
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class DebugState
/*     */   {
/*     */     public ConstructionEvent newSTEvent;
/*  64 */     public MultiMap<String, AddAttributeEvent> addAttrEvents = new MultiMap();
/*     */   }
/*     */ 
/*     */   public static enum RegionType
/*     */   {
/*  56 */     IMPLICIT, EMBEDDED, EXPLICIT;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.ST
 * JD-Core Version:    0.6.2
 */