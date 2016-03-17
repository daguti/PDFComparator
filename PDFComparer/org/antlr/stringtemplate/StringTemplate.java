/*      */ package org.antlr.stringtemplate;
/*      */ 
/*      */ import antlr.CharScanner;
/*      */ import antlr.CommonAST;
/*      */ import antlr.CommonToken;
/*      */ import antlr.RecognitionException;
/*      */ import antlr.TokenStreamException;
/*      */ import antlr.collections.AST;
/*      */ import antlr.collections.ASTEnumeration;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.io.Reader;
/*      */ import java.io.StringReader;
/*      */ import java.io.StringWriter;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import org.antlr.stringtemplate.language.ASTExpr;
/*      */ import org.antlr.stringtemplate.language.ActionLexer;
/*      */ import org.antlr.stringtemplate.language.ActionParser;
/*      */ import org.antlr.stringtemplate.language.ConditionalExpr;
/*      */ import org.antlr.stringtemplate.language.Expr;
/*      */ import org.antlr.stringtemplate.language.FormalArgument;
/*      */ import org.antlr.stringtemplate.language.NewlineRef;
/*      */ import org.antlr.stringtemplate.language.StringTemplateAST;
/*      */ import org.antlr.stringtemplate.language.TemplateParser;
/*      */ 
/*      */ public class StringTemplate
/*      */ {
/*      */   public static final String VERSION = "3.2.1";
/*      */   public static final int REGION_IMPLICIT = 1;
/*      */   public static final int REGION_EMBEDDED = 2;
/*      */   public static final int REGION_EXPLICIT = 3;
/*      */   public static final String ANONYMOUS_ST_NAME = "anonymous";
/*  124 */   static boolean lintMode = false;
/*      */ 
/*  126 */   protected List referencedAttributes = null;
/*      */ 
/*  129 */   protected String name = "anonymous";
/*      */ 
/*  131 */   private static int templateCounter = 0;
/*      */ 
/*  143 */   protected int templateID = getNextTemplateCounter();
/*      */ 
/*  148 */   protected StringTemplate enclosingInstance = null;
/*      */ 
/*  167 */   protected Map argumentContext = null;
/*      */ 
/*  179 */   protected StringTemplateAST argumentsAST = null;
/*      */ 
/*  190 */   protected LinkedHashMap formalArguments = FormalArgument.UNKNOWN;
/*      */ 
/*  195 */   protected int numberOfDefaultArgumentValues = 0;
/*      */ 
/*  203 */   protected boolean passThroughAttributes = false;
/*      */   protected StringTemplateGroup nativeGroup;
/*      */   protected StringTemplateGroup group;
/*      */   protected int groupFileLine;
/*  231 */   StringTemplateErrorListener listener = null;
/*      */   protected String pattern;
/*      */   protected Map attributes;
/*      */   protected Map attributeRenderers;
/*      */   protected List chunks;
/*      */   protected int regionDefType;
/*      */   protected boolean isRegion;
/*      */   protected Set regions;
/*  281 */   public static StringTemplateGroup defaultGroup = new StringTemplateGroup("defaultGroup", ".");
/*      */ 
/*      */   private static synchronized int getNextTemplateCounter()
/*      */   {
/*  133 */     templateCounter += 1;
/*  134 */     return templateCounter;
/*      */   }
/*      */ 
/*      */   public static void resetTemplateCounter()
/*      */   {
/*  140 */     templateCounter = 0;
/*      */   }
/*      */ 
/*      */   public StringTemplate()
/*      */   {
/*  286 */     this.group = defaultGroup;
/*      */   }
/*      */ 
/*      */   public StringTemplate(String template)
/*      */   {
/*  293 */     this(null, template);
/*      */   }
/*      */ 
/*      */   public StringTemplate(String template, Class lexer) {
/*  297 */     this();
/*  298 */     setGroup(new StringTemplateGroup("defaultGroup", lexer));
/*  299 */     setTemplate(template);
/*      */   }
/*      */ 
/*      */   public StringTemplate(StringTemplateGroup group, String template)
/*      */   {
/*  304 */     this();
/*  305 */     if (group != null) {
/*  306 */       setGroup(group);
/*      */     }
/*  308 */     setTemplate(template);
/*      */   }
/*      */ 
/*      */   public StringTemplate(StringTemplateGroup group, String template, HashMap attributes)
/*      */   {
/*  315 */     this(group, template);
/*  316 */     this.attributes = attributes;
/*      */   }
/*      */ 
/*      */   protected void dup(StringTemplate from, StringTemplate to)
/*      */   {
/*  327 */     to.attributeRenderers = from.attributeRenderers;
/*  328 */     to.pattern = from.pattern;
/*  329 */     to.chunks = from.chunks;
/*  330 */     to.formalArguments = from.formalArguments;
/*  331 */     to.numberOfDefaultArgumentValues = from.numberOfDefaultArgumentValues;
/*  332 */     to.name = from.name;
/*  333 */     to.group = from.group;
/*  334 */     to.nativeGroup = from.nativeGroup;
/*  335 */     to.listener = from.listener;
/*  336 */     to.regions = from.regions;
/*  337 */     to.isRegion = from.isRegion;
/*  338 */     to.regionDefType = from.regionDefType;
/*      */   }
/*      */ 
/*      */   public StringTemplate getInstanceOf()
/*      */   {
/*  347 */     StringTemplate t = null;
/*  348 */     if (this.nativeGroup != null)
/*      */     {
/*  352 */       t = this.nativeGroup.createStringTemplate();
/*      */     }
/*      */     else {
/*  355 */       t = this.group.createStringTemplate();
/*      */     }
/*  357 */     dup(this, t);
/*  358 */     return t;
/*      */   }
/*      */ 
/*      */   public StringTemplate getEnclosingInstance() {
/*  362 */     return this.enclosingInstance;
/*      */   }
/*      */ 
/*      */   public StringTemplate getOutermostEnclosingInstance() {
/*  366 */     if (this.enclosingInstance != null) {
/*  367 */       return this.enclosingInstance.getOutermostEnclosingInstance();
/*      */     }
/*  369 */     return this;
/*      */   }
/*      */ 
/*      */   public void setEnclosingInstance(StringTemplate enclosingInstance) {
/*  373 */     if (this == enclosingInstance) {
/*  374 */       throw new IllegalArgumentException("cannot embed template " + getName() + " in itself");
/*      */     }
/*      */ 
/*  377 */     this.enclosingInstance = enclosingInstance;
/*      */   }
/*      */ 
/*      */   public Map getArgumentContext() {
/*  381 */     return this.argumentContext;
/*      */   }
/*      */ 
/*      */   public void setArgumentContext(Map ac) {
/*  385 */     this.argumentContext = ac;
/*      */   }
/*      */ 
/*      */   public StringTemplateAST getArgumentsAST() {
/*  389 */     return this.argumentsAST;
/*      */   }
/*      */ 
/*      */   public void setArgumentsAST(StringTemplateAST argumentsAST) {
/*  393 */     this.argumentsAST = argumentsAST;
/*      */   }
/*      */ 
/*      */   public String getName() {
/*  397 */     return this.name;
/*      */   }
/*      */ 
/*      */   public String getOutermostName() {
/*  401 */     if (this.enclosingInstance != null) {
/*  402 */       return this.enclosingInstance.getOutermostName();
/*      */     }
/*  404 */     return getName();
/*      */   }
/*      */ 
/*      */   public void setName(String name) {
/*  408 */     this.name = name;
/*      */   }
/*      */ 
/*      */   public StringTemplateGroup getGroup() {
/*  412 */     return this.group;
/*      */   }
/*      */ 
/*      */   public void setGroup(StringTemplateGroup group) {
/*  416 */     this.group = group;
/*      */   }
/*      */ 
/*      */   public StringTemplateGroup getNativeGroup() {
/*  420 */     return this.nativeGroup;
/*      */   }
/*      */ 
/*      */   public void setNativeGroup(StringTemplateGroup nativeGroup) {
/*  424 */     this.nativeGroup = nativeGroup;
/*      */   }
/*      */ 
/*      */   public int getGroupFileLine()
/*      */   {
/*  429 */     if (this.enclosingInstance != null) {
/*  430 */       return this.enclosingInstance.getGroupFileLine();
/*      */     }
/*  432 */     return this.groupFileLine;
/*      */   }
/*      */ 
/*      */   public void setGroupFileLine(int groupFileLine) {
/*  436 */     this.groupFileLine = groupFileLine;
/*      */   }
/*      */ 
/*      */   public void setTemplate(String template) {
/*  440 */     this.pattern = template;
/*  441 */     breakTemplateIntoChunks();
/*      */   }
/*      */ 
/*      */   public String getTemplate() {
/*  445 */     return this.pattern;
/*      */   }
/*      */ 
/*      */   public void setErrorListener(StringTemplateErrorListener listener) {
/*  449 */     this.listener = listener;
/*      */   }
/*      */ 
/*      */   public StringTemplateErrorListener getErrorListener() {
/*  453 */     if (this.listener == null) {
/*  454 */       return this.group.getErrorListener();
/*      */     }
/*  456 */     return this.listener;
/*      */   }
/*      */ 
/*      */   public void reset() {
/*  460 */     this.attributes = new HashMap();
/*      */   }
/*      */ 
/*      */   public void setPredefinedAttributes() {
/*  464 */     if (!inLintMode());
/*      */   }
/*      */ 
/*      */   public void removeAttribute(String name)
/*      */   {
/*  470 */     if (this.attributes != null) this.attributes.remove(name);
/*      */   }
/*      */ 
/*      */   public void setAttribute(String name, Object value)
/*      */   {
/*  486 */     if ((value == null) || (name == null)) {
/*  487 */       return;
/*      */     }
/*  489 */     if (name.indexOf('.') >= 0) {
/*  490 */       throw new IllegalArgumentException("cannot have '.' in attribute names");
/*      */     }
/*  492 */     if (this.attributes == null) {
/*  493 */       this.attributes = new HashMap();
/*      */     }
/*      */ 
/*  496 */     if ((value instanceof StringTemplate)) {
/*  497 */       ((StringTemplate)value).setEnclosingInstance(this);
/*      */     }
/*      */     else
/*      */     {
/*  501 */       value = ASTExpr.convertArrayToList(value);
/*      */     }
/*      */ 
/*  506 */     Object o = this.attributes.get(name);
/*  507 */     if (o == null) {
/*  508 */       rawSetAttribute(this.attributes, name, value);
/*  509 */       return;
/*      */     }
/*      */ 
/*  513 */     STAttributeList v = null;
/*  514 */     if (o.getClass() == STAttributeList.class) {
/*  515 */       v = (STAttributeList)o;
/*      */     }
/*  517 */     else if ((o instanceof List))
/*      */     {
/*  519 */       List listAttr = (List)o;
/*  520 */       v = new STAttributeList(listAttr.size());
/*  521 */       v.addAll(listAttr);
/*  522 */       rawSetAttribute(this.attributes, name, v);
/*      */     }
/*      */     else
/*      */     {
/*  526 */       v = new STAttributeList();
/*      */ 
/*  528 */       rawSetAttribute(this.attributes, name, v);
/*  529 */       v.add(o);
/*      */     }
/*  531 */     if ((value instanceof List))
/*      */     {
/*  533 */       if (v != value) {
/*  534 */         v.addAll((List)value);
/*      */       }
/*      */     }
/*      */     else
/*  538 */       v.add(value);
/*      */   }
/*      */ 
/*      */   public void setAttribute(String name, int value)
/*      */   {
/*  544 */     setAttribute(name, new Integer(value));
/*      */   }
/*      */ 
/*      */   public void setAttribute(String aggrSpec, Object v1, Object v2)
/*      */   {
/*  551 */     setAttribute(aggrSpec, new Object[] { v1, v2 });
/*      */   }
/*      */ 
/*      */   public void setAttribute(String aggrSpec, Object v1, Object v2, Object v3) {
/*  555 */     setAttribute(aggrSpec, new Object[] { v1, v2, v3 });
/*      */   }
/*      */ 
/*      */   public void setAttribute(String aggrSpec, Object v1, Object v2, Object v3, Object v4) {
/*  559 */     setAttribute(aggrSpec, new Object[] { v1, v2, v3, v4 });
/*      */   }
/*      */ 
/*      */   public void setAttribute(String aggrSpec, Object v1, Object v2, Object v3, Object v4, Object v5) {
/*  563 */     setAttribute(aggrSpec, new Object[] { v1, v2, v3, v4, v5 });
/*      */   }
/*      */ 
/*      */   protected void setAttribute(String aggrSpec, Object[] values)
/*      */   {
/*  571 */     List properties = new ArrayList();
/*  572 */     String aggrName = parseAggregateAttributeSpec(aggrSpec, properties);
/*  573 */     if ((values == null) || (properties.size() == 0)) {
/*  574 */       throw new IllegalArgumentException("missing properties or values for '" + aggrSpec + "'");
/*      */     }
/*  576 */     if (values.length != properties.size()) {
/*  577 */       throw new IllegalArgumentException("number of properties in '" + aggrSpec + "' != number of values");
/*      */     }
/*  579 */     Aggregate aggr = new Aggregate();
/*  580 */     for (int i = 0; i < values.length; i++) {
/*  581 */       Object value = values[i];
/*  582 */       if ((value instanceof StringTemplate)) {
/*  583 */         ((StringTemplate)value).setEnclosingInstance(this);
/*      */       }
/*      */       else {
/*  586 */         value = ASTExpr.convertArrayToList(value);
/*      */       }
/*  588 */       aggr.put((String)properties.get(i), value);
/*      */     }
/*  590 */     setAttribute(aggrName, aggr);
/*      */   }
/*      */ 
/*      */   protected String parseAggregateAttributeSpec(String aggrSpec, List properties)
/*      */   {
/*  597 */     int dot = aggrSpec.indexOf('.');
/*  598 */     if (dot <= 0) {
/*  599 */       throw new IllegalArgumentException("invalid aggregate attribute format: " + aggrSpec);
/*      */     }
/*      */ 
/*  602 */     String aggrName = aggrSpec.substring(0, dot);
/*  603 */     String propString = aggrSpec.substring(dot + 1, aggrSpec.length());
/*  604 */     boolean error = true;
/*  605 */     StringTokenizer tokenizer = new StringTokenizer(propString, "{,}", true);
/*      */ 
/*  607 */     if (tokenizer.hasMoreTokens()) {
/*  608 */       String token = tokenizer.nextToken();
/*  609 */       token = token.trim();
/*  610 */       if (token.equals("{")) {
/*  611 */         token = tokenizer.nextToken();
/*  612 */         token = token.trim();
/*  613 */         properties.add(token);
/*  614 */         token = tokenizer.nextToken();
/*  615 */         token = token.trim();
/*  616 */         while (token.equals(",")) {
/*  617 */           token = tokenizer.nextToken();
/*  618 */           token = token.trim();
/*  619 */           properties.add(token);
/*  620 */           token = tokenizer.nextToken();
/*  621 */           token = token.trim();
/*      */         }
/*  623 */         if (token.equals("}")) {
/*  624 */           error = false;
/*      */         }
/*      */       }
/*      */     }
/*  628 */     if (error) {
/*  629 */       throw new IllegalArgumentException("invalid aggregate attribute format: " + aggrSpec);
/*      */     }
/*      */ 
/*  632 */     return aggrName;
/*      */   }
/*      */ 
/*      */   protected void rawSetAttribute(Map attributes, String name, Object value)
/*      */   {
/*  643 */     if ((this.formalArguments != FormalArgument.UNKNOWN) && (getFormalArgument(name) == null))
/*      */     {
/*  647 */       throw new NoSuchElementException("no such attribute: " + name + " in template context " + getEnclosingInstanceStackString());
/*      */     }
/*      */ 
/*  651 */     if (value == null) {
/*  652 */       return;
/*      */     }
/*  654 */     attributes.put(name, value);
/*      */   }
/*      */ 
/*      */   public void rawSetArgumentAttribute(StringTemplate embedded, Map attributes, String name, Object value)
/*      */   {
/*  667 */     if ((embedded.formalArguments != FormalArgument.UNKNOWN) && (embedded.getFormalArgument(name) == null))
/*      */     {
/*  670 */       throw new NoSuchElementException("template " + embedded.getName() + " has no such attribute: " + name + " in template context " + getEnclosingInstanceStackString());
/*      */     }
/*      */ 
/*  675 */     if (value == null) {
/*  676 */       return;
/*      */     }
/*  678 */     attributes.put(name, value);
/*      */   }
/*      */ 
/*      */   public Object getAttribute(String name) {
/*  682 */     Object v = get(this, name);
/*  683 */     if (v == null) {
/*  684 */       checkNullAttributeAgainstFormalArguments(this, name);
/*      */     }
/*  686 */     return v;
/*      */   }
/*      */ 
/*      */   public int write(StringTemplateWriter out)
/*      */     throws IOException
/*      */   {
/*  696 */     if (this.group.debugTemplateOutput) {
/*  697 */       this.group.emitTemplateStartDebugString(this, out);
/*      */     }
/*  699 */     int n = 0;
/*  700 */     boolean missing = true;
/*  701 */     setPredefinedAttributes();
/*  702 */     setDefaultArgumentValues();
/*  703 */     for (int i = 0; (this.chunks != null) && (i < this.chunks.size()); i++) {
/*  704 */       Expr a = (Expr)this.chunks.get(i);
/*  705 */       int chunkN = a.write(this, out);
/*      */ 
/*  707 */       if ((chunkN <= 0) && (i == 0) && (i + 1 < this.chunks.size()) && ((this.chunks.get(i + 1) instanceof NewlineRef)))
/*      */       {
/*  711 */         i++;
/*      */       }
/*      */       else
/*      */       {
/*  717 */         if ((chunkN <= 0) && (i - 1 >= 0) && ((this.chunks.get(i - 1) instanceof NewlineRef)) && (i + 1 < this.chunks.size()) && ((this.chunks.get(i + 1) instanceof NewlineRef)))
/*      */         {
/*  722 */           i++;
/*      */         }
/*  724 */         if (chunkN != -1) {
/*  725 */           n += chunkN;
/*  726 */           missing = false;
/*      */         }
/*      */       }
/*      */     }
/*  729 */     if (this.group.debugTemplateOutput) {
/*  730 */       this.group.emitTemplateStopDebugString(this, out);
/*      */     }
/*  732 */     if (lintMode) checkForTrouble();
/*  733 */     if ((missing) && (this.chunks != null) && (this.chunks.size() > 0)) return -1;
/*  734 */     return n;
/*      */   }
/*      */ 
/*      */   public Object get(StringTemplate self, String attribute)
/*      */   {
/*  768 */     if (self == null) {
/*  769 */       return null;
/*      */     }
/*      */ 
/*  772 */     if (lintMode) {
/*  773 */       self.trackAttributeReference(attribute);
/*      */     }
/*      */ 
/*  777 */     Object o = null;
/*  778 */     if (self.attributes != null) {
/*  779 */       o = self.attributes.get(attribute);
/*      */     }
/*      */ 
/*  783 */     if (o == null) {
/*  784 */       Map argContext = self.getArgumentContext();
/*  785 */       if (argContext != null) {
/*  786 */         o = argContext.get(attribute);
/*      */       }
/*      */     }
/*      */ 
/*  790 */     if ((o == null) && (!self.passThroughAttributes) && (self.getFormalArgument(attribute) != null))
/*      */     {
/*  798 */       return null;
/*      */     }
/*      */ 
/*  802 */     if ((o == null) && (self.enclosingInstance != null))
/*      */     {
/*  807 */       Object valueFromEnclosing = get(self.enclosingInstance, attribute);
/*      */ 
/*  813 */       o = valueFromEnclosing;
/*      */     }
/*  817 */     else if ((o == null) && (self.enclosingInstance == null))
/*      */     {
/*  819 */       o = self.group.getMap(attribute);
/*      */     }
/*      */ 
/*  822 */     return o;
/*      */   }
/*      */ 
/*      */   protected void breakTemplateIntoChunks()
/*      */   {
/*  830 */     if (this.pattern == null) {
/*  831 */       return;
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  839 */       Class lexerClass = this.group.getTemplateLexerClass();
/*  840 */       Constructor ctor = lexerClass.getConstructor(new Class[] { StringTemplate.class, Reader.class });
/*      */ 
/*  844 */       CharScanner chunkStream = (CharScanner)ctor.newInstance(new Object[] { this, new StringReader(this.pattern) });
/*      */ 
/*  848 */       chunkStream.setTokenObjectClass("org.antlr.stringtemplate.language.ChunkToken");
/*  849 */       TemplateParser chunkifier = new TemplateParser(chunkStream);
/*  850 */       chunkifier.template(this);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  854 */       String name = "<unknown>";
/*  855 */       String outerName = getOutermostName();
/*  856 */       if (getName() != null) {
/*  857 */         name = getName();
/*      */       }
/*  859 */       if ((outerName != null) && (!name.equals(outerName))) {
/*  860 */         name = name + " nested in " + outerName;
/*      */       }
/*  862 */       error("problem parsing template '" + name + "'", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public ASTExpr parseAction(String action)
/*      */   {
/*  868 */     ActionLexer lexer = new ActionLexer(new StringReader(action.toString()));
/*      */ 
/*  870 */     ActionParser parser = new ActionParser(lexer, this);
/*      */ 
/*  872 */     parser.setASTNodeClass("org.antlr.stringtemplate.language.StringTemplateAST");
/*  873 */     lexer.setTokenObjectClass("org.antlr.stringtemplate.language.StringTemplateToken");
/*  874 */     ASTExpr a = null;
/*      */     try {
/*  876 */       Map options = parser.action();
/*  877 */       AST tree = parser.getAST();
/*  878 */       if (tree != null) {
/*  879 */         if (tree.getType() == 8) {
/*  880 */           a = new ConditionalExpr(this, tree);
/*      */         }
/*      */         else
/*  883 */           a = new ASTExpr(this, tree, options);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  888 */       error("Can't parse chunk: " + action.toString(), re);
/*      */     }
/*      */     catch (TokenStreamException tse) {
/*  891 */       error("Can't parse chunk: " + action.toString(), tse);
/*      */     }
/*  893 */     return a;
/*      */   }
/*      */ 
/*      */   public int getTemplateID() {
/*  897 */     return this.templateID;
/*      */   }
/*      */ 
/*      */   public Map getAttributes() {
/*  901 */     return this.attributes;
/*      */   }
/*      */ 
/*      */   public List getChunks()
/*      */   {
/*  908 */     return this.chunks;
/*      */   }
/*      */ 
/*      */   public void addChunk(Expr e) {
/*  912 */     if (this.chunks == null) {
/*  913 */       this.chunks = new ArrayList();
/*      */     }
/*  915 */     this.chunks.add(e);
/*      */   }
/*      */ 
/*      */   public void setAttributes(Map attributes) {
/*  919 */     this.attributes = attributes;
/*      */   }
/*      */ 
/*      */   public Map getFormalArguments()
/*      */   {
/*  925 */     return this.formalArguments;
/*      */   }
/*      */ 
/*      */   public void setFormalArguments(LinkedHashMap args) {
/*  929 */     this.formalArguments = args;
/*      */   }
/*      */ 
/*      */   public void setDefaultArgumentValues()
/*      */   {
/*  944 */     if (this.numberOfDefaultArgumentValues == 0) {
/*  945 */       return;
/*      */     }
/*  947 */     if (this.argumentContext == null)
/*  948 */       this.argumentContext = new HashMap();
/*      */     Iterator it;
/*  950 */     if (this.formalArguments != FormalArgument.UNKNOWN)
/*      */     {
/*  952 */       Set argNames = this.formalArguments.keySet();
/*  953 */       for (it = argNames.iterator(); it.hasNext(); ) {
/*  954 */         String argName = (String)it.next();
/*      */ 
/*  956 */         FormalArgument arg = (FormalArgument)this.formalArguments.get(argName);
/*      */ 
/*  958 */         if (arg.defaultValueST != null)
/*      */         {
/*  961 */           Object existingValue = getAttribute(argName);
/*      */ 
/*  963 */           if (existingValue == null) {
/*  964 */             Object defaultValue = arg.defaultValueST;
/*      */ 
/*  969 */             int nchunks = arg.defaultValueST.chunks.size();
/*  970 */             if (nchunks == 1)
/*      */             {
/*  975 */               Object a = arg.defaultValueST.chunks.get(0);
/*  976 */               if ((a instanceof ASTExpr)) {
/*  977 */                 ASTExpr e = (ASTExpr)a;
/*  978 */                 if (e.getAST().getType() == 9) {
/*  979 */                   defaultValue = e.evaluateExpression(this, e.getAST());
/*      */                 }
/*      */               }
/*      */             }
/*  983 */             this.argumentContext.put(argName, defaultValue);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public FormalArgument lookupFormalArgument(String name)
/*      */   {
/*  994 */     FormalArgument arg = getFormalArgument(name);
/*  995 */     if ((arg == null) && (this.enclosingInstance != null)) {
/*  996 */       arg = this.enclosingInstance.lookupFormalArgument(name);
/*      */     }
/*  998 */     return arg;
/*      */   }
/*      */ 
/*      */   public FormalArgument getFormalArgument(String name) {
/* 1002 */     return (FormalArgument)this.formalArguments.get(name);
/*      */   }
/*      */ 
/*      */   public void defineEmptyFormalArgumentList() {
/* 1006 */     setFormalArguments(new LinkedHashMap());
/*      */   }
/*      */ 
/*      */   public void defineFormalArgument(String name) {
/* 1010 */     defineFormalArgument(name, null);
/*      */   }
/*      */ 
/*      */   public void defineFormalArguments(List names) {
/* 1014 */     if (names == null) {
/* 1015 */       return;
/*      */     }
/* 1017 */     for (int i = 0; i < names.size(); i++) {
/* 1018 */       String name = (String)names.get(i);
/* 1019 */       defineFormalArgument(name);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void defineFormalArgument(String name, StringTemplate defaultValue)
/*      */   {
/* 1028 */     if (defaultValue != null) {
/* 1029 */       this.numberOfDefaultArgumentValues += 1;
/*      */     }
/* 1031 */     FormalArgument a = new FormalArgument(name, defaultValue);
/* 1032 */     if (this.formalArguments == FormalArgument.UNKNOWN) {
/* 1033 */       this.formalArguments = new LinkedHashMap();
/*      */     }
/* 1035 */     this.formalArguments.put(name, a);
/*      */   }
/*      */ 
/*      */   public void setPassThroughAttributes(boolean passThroughAttributes)
/*      */   {
/* 1044 */     this.passThroughAttributes = passThroughAttributes;
/*      */   }
/*      */ 
/*      */   public void setAttributeRenderers(Map renderers)
/*      */   {
/* 1051 */     this.attributeRenderers = renderers;
/*      */   }
/*      */ 
/*      */   public void registerRenderer(Class attributeClassType, AttributeRenderer renderer)
/*      */   {
/* 1058 */     if (this.attributeRenderers == null) {
/* 1059 */       this.attributeRenderers = new HashMap();
/*      */     }
/* 1061 */     this.attributeRenderers.put(attributeClassType, renderer);
/*      */   }
/*      */ 
/*      */   public AttributeRenderer getAttributeRenderer(Class attributeClassType)
/*      */   {
/* 1068 */     AttributeRenderer renderer = null;
/* 1069 */     if (this.attributeRenderers != null) {
/* 1070 */       renderer = (AttributeRenderer)this.attributeRenderers.get(attributeClassType);
/*      */     }
/* 1072 */     if (renderer != null)
/*      */     {
/* 1074 */       return renderer;
/*      */     }
/*      */ 
/* 1079 */     if (this.enclosingInstance != null) {
/* 1080 */       return this.enclosingInstance.getAttributeRenderer(attributeClassType);
/*      */     }
/*      */ 
/* 1083 */     return this.group.getAttributeRenderer(attributeClassType);
/*      */   }
/*      */ 
/*      */   public void error(String msg)
/*      */   {
/* 1089 */     error(msg, null);
/*      */   }
/*      */ 
/*      */   public void warning(String msg) {
/* 1093 */     if (getErrorListener() != null) {
/* 1094 */       getErrorListener().warning(msg);
/*      */     }
/*      */     else
/* 1097 */       System.err.println("StringTemplate: warning: " + msg);
/*      */   }
/*      */ 
/*      */   public void error(String msg, Throwable e)
/*      */   {
/* 1102 */     if (getErrorListener() != null) {
/* 1103 */       getErrorListener().error(msg, e);
/*      */     }
/* 1106 */     else if (e != null) {
/* 1107 */       System.err.println("StringTemplate: error: " + msg + ": " + e.toString());
/* 1108 */       if ((e instanceof InvocationTargetException)) {
/* 1109 */         e = ((InvocationTargetException)e).getTargetException();
/*      */       }
/* 1111 */       e.printStackTrace(System.err);
/*      */     }
/*      */     else {
/* 1114 */       System.err.println("StringTemplate: error: " + msg);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void setLintMode(boolean lint)
/*      */   {
/* 1124 */     lintMode = lint;
/*      */   }
/*      */ 
/*      */   public static boolean inLintMode() {
/* 1128 */     return lintMode;
/*      */   }
/*      */ 
/*      */   protected void trackAttributeReference(String name)
/*      */   {
/* 1133 */     if (this.referencedAttributes == null) {
/* 1134 */       this.referencedAttributes = new ArrayList();
/*      */     }
/* 1136 */     this.referencedAttributes.add(name);
/*      */   }
/*      */ 
/*      */   public static boolean isRecursiveEnclosingInstance(StringTemplate st)
/*      */   {
/* 1143 */     if (st == null) {
/* 1144 */       return false;
/*      */     }
/* 1146 */     StringTemplate p = st.enclosingInstance;
/* 1147 */     if (p == st) {
/* 1148 */       return true;
/*      */     }
/*      */ 
/* 1151 */     while (p != null) {
/* 1152 */       if (p == st) {
/* 1153 */         return true;
/*      */       }
/* 1155 */       p = p.enclosingInstance;
/*      */     }
/* 1157 */     return false;
/*      */   }
/*      */ 
/*      */   public String getEnclosingInstanceStackTrace() {
/* 1161 */     StringBuffer buf = new StringBuffer();
/* 1162 */     Set seen = new HashSet();
/* 1163 */     StringTemplate p = this;
/* 1164 */     while (p != null) {
/* 1165 */       if (seen.contains(p)) {
/* 1166 */         buf.append(p.getTemplateDeclaratorString());
/* 1167 */         buf.append(" (start of recursive cycle)");
/* 1168 */         buf.append("\n");
/* 1169 */         buf.append("...");
/* 1170 */         break;
/*      */       }
/* 1172 */       seen.add(p);
/* 1173 */       buf.append(p.getTemplateDeclaratorString());
/* 1174 */       if (p.attributes != null) {
/* 1175 */         buf.append(", attributes=[");
/* 1176 */         int i = 0;
/* 1177 */         for (Iterator iter = p.attributes.keySet().iterator(); iter.hasNext(); ) {
/* 1178 */           String attrName = (String)iter.next();
/* 1179 */           if (i > 0) {
/* 1180 */             buf.append(", ");
/*      */           }
/* 1182 */           i++;
/* 1183 */           buf.append(attrName);
/* 1184 */           Object o = p.attributes.get(attrName);
/* 1185 */           if ((o instanceof StringTemplate)) {
/* 1186 */             StringTemplate st = (StringTemplate)o;
/* 1187 */             buf.append("=");
/* 1188 */             buf.append("<");
/* 1189 */             buf.append(st.getName());
/* 1190 */             buf.append("()@");
/* 1191 */             buf.append(String.valueOf(st.getTemplateID()));
/* 1192 */             buf.append(">");
/*      */           }
/* 1194 */           else if ((o instanceof List)) {
/* 1195 */             buf.append("=List[..");
/* 1196 */             List list = (List)o;
/* 1197 */             int n = 0;
/* 1198 */             for (int j = 0; j < list.size(); j++) {
/* 1199 */               Object listValue = list.get(j);
/* 1200 */               if ((listValue instanceof StringTemplate)) {
/* 1201 */                 if (n > 0) {
/* 1202 */                   buf.append(", ");
/*      */                 }
/* 1204 */                 n++;
/* 1205 */                 StringTemplate st = (StringTemplate)listValue;
/* 1206 */                 buf.append("<");
/* 1207 */                 buf.append(st.getName());
/* 1208 */                 buf.append("()@");
/* 1209 */                 buf.append(String.valueOf(st.getTemplateID()));
/* 1210 */                 buf.append(">");
/*      */               }
/*      */             }
/* 1213 */             buf.append("..]");
/*      */           }
/*      */         }
/* 1216 */         buf.append("]");
/*      */       }
/* 1218 */       if (p.referencedAttributes != null) {
/* 1219 */         buf.append(", references=");
/* 1220 */         buf.append(p.referencedAttributes);
/*      */       }
/* 1222 */       buf.append(">\n");
/* 1223 */       p = p.enclosingInstance;
/*      */     }
/*      */ 
/* 1230 */     return buf.toString();
/*      */   }
/*      */ 
/*      */   public String getTemplateDeclaratorString() {
/* 1234 */     StringBuffer buf = new StringBuffer();
/* 1235 */     buf.append("<");
/* 1236 */     buf.append(getName());
/* 1237 */     buf.append("(");
/* 1238 */     buf.append(this.formalArguments.keySet());
/* 1239 */     buf.append(")@");
/* 1240 */     buf.append(String.valueOf(getTemplateID()));
/* 1241 */     buf.append(">");
/* 1242 */     return buf.toString();
/*      */   }
/*      */ 
/*      */   protected String getTemplateHeaderString(boolean showAttributes) {
/* 1246 */     if (showAttributes) {
/* 1247 */       StringBuffer buf = new StringBuffer();
/* 1248 */       buf.append(getName());
/* 1249 */       if (this.attributes != null) {
/* 1250 */         buf.append(this.attributes.keySet());
/*      */       }
/* 1252 */       return buf.toString();
/*      */     }
/* 1254 */     return getName();
/*      */   }
/*      */ 
/*      */   protected void checkNullAttributeAgainstFormalArguments(StringTemplate self, String attribute)
/*      */   {
/* 1300 */     if (self.getFormalArguments() == FormalArgument.UNKNOWN)
/*      */     {
/* 1302 */       if (self.enclosingInstance != null) {
/* 1303 */         checkNullAttributeAgainstFormalArguments(self.enclosingInstance, attribute);
/*      */       }
/*      */ 
/* 1307 */       return;
/*      */     }
/* 1309 */     FormalArgument formalArg = self.lookupFormalArgument(attribute);
/* 1310 */     if (formalArg == null)
/* 1311 */       throw new NoSuchElementException("no such attribute: " + attribute + " in template context " + getEnclosingInstanceStackString());
/*      */   }
/*      */ 
/*      */   protected void checkForTrouble()
/*      */   {
/* 1322 */     if (this.attributes == null) {
/* 1323 */       return;
/*      */     }
/* 1325 */     Set names = this.attributes.keySet();
/* 1326 */     Iterator iter = names.iterator();
/*      */ 
/* 1328 */     while (iter.hasNext()) {
/* 1329 */       String name = (String)iter.next();
/* 1330 */       if ((this.referencedAttributes != null) && (!this.referencedAttributes.contains(name)))
/*      */       {
/* 1333 */         warning(getName() + ": set but not used: " + name);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public String getEnclosingInstanceStackString()
/*      */   {
/* 1344 */     List names = new LinkedList();
/* 1345 */     StringTemplate p = this;
/* 1346 */     while (p != null) {
/* 1347 */       String name = p.getName();
/* 1348 */       names.add(0, name + (p.passThroughAttributes ? "(...)" : ""));
/* 1349 */       p = p.enclosingInstance;
/*      */     }
/* 1351 */     return names.toString().replaceAll(",", "");
/*      */   }
/*      */ 
/*      */   public boolean isRegion() {
/* 1355 */     return this.isRegion;
/*      */   }
/*      */ 
/*      */   public void setIsRegion(boolean isRegion) {
/* 1359 */     this.isRegion = isRegion;
/*      */   }
/*      */ 
/*      */   public void addRegionName(String name) {
/* 1363 */     if (this.regions == null) {
/* 1364 */       this.regions = new HashSet();
/*      */     }
/* 1366 */     this.regions.add(name);
/*      */   }
/*      */ 
/*      */   public boolean containsRegionName(String name)
/*      */   {
/* 1371 */     if (this.regions == null) {
/* 1372 */       return false;
/*      */     }
/* 1374 */     return this.regions.contains(name);
/*      */   }
/*      */ 
/*      */   public int getRegionDefType() {
/* 1378 */     return this.regionDefType;
/*      */   }
/*      */ 
/*      */   public void setRegionDefType(int regionDefType) {
/* 1382 */     this.regionDefType = regionDefType;
/*      */   }
/*      */ 
/*      */   public String toDebugString() {
/* 1386 */     StringBuffer buf = new StringBuffer();
/* 1387 */     buf.append("template-" + getTemplateDeclaratorString() + ":");
/* 1388 */     buf.append("chunks=");
/* 1389 */     if (this.chunks != null) {
/* 1390 */       buf.append(this.chunks.toString());
/*      */     }
/* 1392 */     buf.append("attributes=[");
/* 1393 */     if (this.attributes != null) {
/* 1394 */       Set attrNames = this.attributes.keySet();
/* 1395 */       int n = 0;
/* 1396 */       for (Iterator iter = attrNames.iterator(); iter.hasNext(); ) {
/* 1397 */         if (n > 0) {
/* 1398 */           buf.append(',');
/*      */         }
/* 1400 */         String name = (String)iter.next();
/* 1401 */         buf.append(name + "=");
/* 1402 */         Object value = this.attributes.get(name);
/* 1403 */         if ((value instanceof StringTemplate)) {
/* 1404 */           buf.append(((StringTemplate)value).toDebugString());
/*      */         }
/*      */         else {
/* 1407 */           buf.append(value);
/*      */         }
/* 1409 */         n++;
/*      */       }
/* 1411 */       buf.append("]");
/*      */     }
/* 1413 */     return buf.toString();
/*      */   }
/*      */ 
/*      */   public String toStructureString()
/*      */   {
/* 1420 */     return toStructureString(0);
/*      */   }
/*      */ 
/*      */   public String toStructureString(int indent) {
/* 1424 */     StringBuffer buf = new StringBuffer();
/* 1425 */     for (int i = 1; i <= indent; i++) {
/* 1426 */       buf.append("  ");
/*      */     }
/* 1428 */     buf.append(getName());
/* 1429 */     buf.append(this.attributes.keySet());
/* 1430 */     buf.append(":\n");
/*      */     Iterator iter;
/* 1431 */     if (this.attributes != null) {
/* 1432 */       Set attrNames = this.attributes.keySet();
/* 1433 */       for (iter = attrNames.iterator(); iter.hasNext(); ) {
/* 1434 */         String name = (String)iter.next();
/* 1435 */         Object value = this.attributes.get(name);
/* 1436 */         if ((value instanceof StringTemplate)) {
/* 1437 */           buf.append(((StringTemplate)value).toStructureString(indent + 1));
/*      */         }
/* 1440 */         else if ((value instanceof List)) {
/* 1441 */           List alist = (List)value;
/* 1442 */           for (int i = 0; i < alist.size(); i++) {
/* 1443 */             Object o = alist.get(i);
/* 1444 */             if ((o instanceof StringTemplate)) {
/* 1445 */               buf.append(((StringTemplate)o).toStructureString(indent + 1));
/*      */             }
/*      */           }
/*      */         }
/* 1449 */         else if ((value instanceof Map)) {
/* 1450 */           Map m = (Map)value;
/* 1451 */           Collection mvalues = m.values();
/* 1452 */           for (iterator = mvalues.iterator(); iterator.hasNext(); ) {
/* 1453 */             Object o = iterator.next();
/* 1454 */             if ((o instanceof StringTemplate))
/* 1455 */               buf.append(((StringTemplate)o).toStructureString(indent + 1));
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     Iterator iterator;
/* 1462 */     return buf.toString();
/*      */   }
/*      */ 
/*      */   public StringTemplate getDOTForDependencyGraph(boolean showAttributes)
/*      */   {
/* 1501 */     String structure = "digraph StringTemplateDependencyGraph {\nnode [shape=$shape$, $if(width)$width=$width$,$endif$      $if(height)$height=$height$,$endif$ fontsize=$fontsize$];\n$edges:{e|\"$e.src$\" -> \"$e.trg$\"\n}$}\n";
/*      */ 
/* 1507 */     StringTemplate graphST = new StringTemplate(structure);
/* 1508 */     HashMap edges = new HashMap();
/* 1509 */     getDependencyGraph(edges, showAttributes);
/* 1510 */     Set sourceNodes = edges.keySet();
/*      */ 
/* 1512 */     for (Iterator it = sourceNodes.iterator(); it.hasNext(); ) {
/* 1513 */       src = (String)it.next();
/* 1514 */       Set targetNodes = (Set)edges.get(src);
/*      */ 
/* 1516 */       for (it2 = targetNodes.iterator(); it2.hasNext(); ) {
/* 1517 */         String trg = (String)it2.next();
/* 1518 */         graphST.setAttribute("edges.{src,trg}", src, trg);
/*      */       }
/*      */     }
/*      */     String src;
/*      */     Iterator it2;
/* 1521 */     graphST.setAttribute("shape", "none");
/* 1522 */     graphST.setAttribute("fontsize", "11");
/* 1523 */     graphST.setAttribute("height", "0");
/* 1524 */     return graphST;
/*      */   }
/*      */ 
/*      */   public void getDependencyGraph(Map edges, boolean showAttributes)
/*      */   {
/* 1540 */     String srcNode = getTemplateHeaderString(showAttributes);
/*      */     Iterator iter;
/* 1541 */     if (this.attributes != null) {
/* 1542 */       Set attrNames = this.attributes.keySet();
/* 1543 */       for (iter = attrNames.iterator(); iter.hasNext(); ) {
/* 1544 */         String name = (String)iter.next();
/* 1545 */         Object value = this.attributes.get(name);
/* 1546 */         if ((value instanceof StringTemplate)) {
/* 1547 */           String targetNode = ((StringTemplate)value).getTemplateHeaderString(showAttributes);
/*      */ 
/* 1549 */           putToMultiValuedMap(edges, srcNode, targetNode);
/* 1550 */           ((StringTemplate)value).getDependencyGraph(edges, showAttributes);
/*      */         }
/* 1553 */         else if ((value instanceof List)) {
/* 1554 */           List alist = (List)value;
/* 1555 */           for (int i = 0; i < alist.size(); i++) {
/* 1556 */             Object o = alist.get(i);
/* 1557 */             if ((o instanceof StringTemplate)) {
/* 1558 */               String targetNode = ((StringTemplate)o).getTemplateHeaderString(showAttributes);
/*      */ 
/* 1560 */               putToMultiValuedMap(edges, srcNode, targetNode);
/* 1561 */               ((StringTemplate)o).getDependencyGraph(edges, showAttributes);
/*      */             }
/*      */           }
/*      */         }
/* 1565 */         else if ((value instanceof Map)) {
/* 1566 */           Map m = (Map)value;
/* 1567 */           Collection mvalues = m.values();
/* 1568 */           for (iterator = mvalues.iterator(); iterator.hasNext(); ) {
/* 1569 */             Object o = iterator.next();
/* 1570 */             if ((o instanceof StringTemplate)) {
/* 1571 */               String targetNode = ((StringTemplate)o).getTemplateHeaderString(showAttributes);
/*      */ 
/* 1573 */               putToMultiValuedMap(edges, srcNode, targetNode);
/* 1574 */               ((StringTemplate)o).getDependencyGraph(edges, showAttributes);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     Iterator iterator;
/* 1582 */     for (int i = 0; (this.chunks != null) && (i < this.chunks.size()); i++) {
/* 1583 */       Expr expr = (Expr)this.chunks.get(i);
/* 1584 */       if ((expr instanceof ASTExpr)) {
/* 1585 */         ASTExpr e = (ASTExpr)expr;
/* 1586 */         AST tree = e.getAST();
/* 1587 */         AST includeAST = new CommonAST(new CommonToken(7, "include"));
/*      */ 
/* 1589 */         ASTEnumeration it = tree.findAllPartial(includeAST);
/* 1590 */         while (it.hasMoreNodes()) {
/* 1591 */           AST t = it.nextNode();
/* 1592 */           String templateInclude = t.getFirstChild().getText();
/* 1593 */           System.out.println("found include " + templateInclude);
/* 1594 */           putToMultiValuedMap(edges, srcNode, templateInclude);
/* 1595 */           StringTemplateGroup group = getGroup();
/* 1596 */           if (group != null) {
/* 1597 */             StringTemplate st = group.getInstanceOf(templateInclude);
/*      */ 
/* 1599 */             st.getDependencyGraph(edges, showAttributes);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void putToMultiValuedMap(Map map, Object key, Object value)
/*      */   {
/* 1608 */     HashSet bag = (HashSet)map.get(key);
/* 1609 */     if (bag == null) {
/* 1610 */       bag = new HashSet();
/* 1611 */       map.put(key, bag);
/*      */     }
/* 1613 */     bag.add(value);
/*      */   }
/*      */ 
/*      */   public void printDebugString() {
/* 1617 */     System.out.println("template-" + getName() + ":");
/* 1618 */     System.out.print("chunks=");
/* 1619 */     System.out.println(this.chunks.toString());
/* 1620 */     if (this.attributes == null) {
/* 1621 */       return;
/*      */     }
/* 1623 */     System.out.print("attributes=[");
/* 1624 */     Set attrNames = this.attributes.keySet();
/* 1625 */     int n = 0;
/* 1626 */     for (Iterator iter = attrNames.iterator(); iter.hasNext(); ) {
/* 1627 */       if (n > 0) {
/* 1628 */         System.out.print(',');
/*      */       }
/* 1630 */       String name = (String)iter.next();
/* 1631 */       Object value = this.attributes.get(name);
/* 1632 */       if ((value instanceof StringTemplate)) {
/* 1633 */         System.out.print(name + "=");
/* 1634 */         ((StringTemplate)value).printDebugString();
/*      */       }
/* 1637 */       else if ((value instanceof List)) {
/* 1638 */         ArrayList alist = (ArrayList)value;
/* 1639 */         for (int i = 0; i < alist.size(); i++) {
/* 1640 */           Object o = alist.get(i);
/* 1641 */           System.out.print(name + "[" + i + "] is " + o.getClass().getName() + "=");
/* 1642 */           if ((o instanceof StringTemplate)) {
/* 1643 */             ((StringTemplate)o).printDebugString();
/*      */           }
/*      */           else
/* 1646 */             System.out.println(o);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1651 */         System.out.print(name + "=");
/* 1652 */         System.out.println(value);
/*      */       }
/*      */ 
/* 1655 */       n++;
/*      */     }
/* 1657 */     System.out.print("]\n");
/*      */   }
/*      */ 
/*      */   public String toString() {
/* 1661 */     return toString(-1);
/*      */   }
/*      */ 
/*      */   public String toString(int lineWidth) {
/* 1665 */     StringWriter out = new StringWriter();
/*      */ 
/* 1667 */     StringTemplateWriter wr = this.group.getStringTemplateWriter(out);
/* 1668 */     wr.setLineWidth(lineWidth);
/*      */     try {
/* 1670 */       write(wr);
/*      */     }
/*      */     catch (IOException io) {
/* 1673 */       error("Got IOException writing to writer " + wr.getClass().getName());
/*      */     }
/*      */ 
/* 1678 */     wr.setLineWidth(-1);
/* 1679 */     return out.toString();
/*      */   }
/*      */ 
/*      */   public static final class STAttributeList extends ArrayList
/*      */   {
/*      */     public STAttributeList(int size)
/*      */     {
/*  117 */       super();
/*      */     }
/*      */ 
/*      */     public STAttributeList()
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public static final class Aggregate
/*      */   {
/*   98 */     protected HashMap properties = new HashMap();
/*      */ 
/*      */     protected void put(String propName, Object propValue)
/*      */     {
/*  103 */       this.properties.put(propName, propValue);
/*      */     }
/*      */     public Object get(String propName) {
/*  106 */       return this.properties.get(propName);
/*      */     }
/*      */     public String toString() {
/*  109 */       return this.properties.toString();
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.stringtemplate.StringTemplate
 * JD-Core Version:    0.6.2
 */