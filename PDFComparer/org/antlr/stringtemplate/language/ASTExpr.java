/*      */ package org.antlr.stringtemplate.language;
/*      */ 
/*      */ import antlr.RecognitionException;
/*      */ import antlr.collections.AST;
/*      */ import java.io.IOException;
/*      */ import java.io.StringWriter;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.antlr.stringtemplate.AttributeRenderer;
/*      */ import org.antlr.stringtemplate.StringTemplate;
/*      */ import org.antlr.stringtemplate.StringTemplate.Aggregate;
/*      */ import org.antlr.stringtemplate.StringTemplate.STAttributeList;
/*      */ import org.antlr.stringtemplate.StringTemplateGroup;
/*      */ import org.antlr.stringtemplate.StringTemplateWriter;
/*      */ 
/*      */ public class ASTExpr extends Expr
/*      */ {
/*      */   public static final int MISSING = -1;
/*      */   public static final String DEFAULT_ATTRIBUTE_NAME = "it";
/*      */   public static final String DEFAULT_ATTRIBUTE_NAME_DEPRECATED = "attr";
/*      */   public static final String DEFAULT_INDEX_VARIABLE_NAME = "i";
/*      */   public static final String DEFAULT_INDEX0_VARIABLE_NAME = "i0";
/*      */   public static final String DEFAULT_MAP_VALUE_NAME = "_default_";
/*      */   public static final String DEFAULT_MAP_KEY_NAME = "key";
/*   57 */   public static final StringTemplate MAP_KEY_VALUE = new StringTemplate();
/*      */   public static final String EMPTY_OPTION = "empty expr option";
/*   64 */   public static final Map defaultOptionValues = new HashMap() { } ;
/*      */ 
/*   72 */   public static final Set supportedOptions = new HashSet() { } ;
/*      */ 
/*   82 */   AST exprTree = null;
/*      */ 
/*   85 */   Map options = null;
/*      */ 
/*   91 */   String wrapString = null;
/*      */ 
/*  101 */   String nullValue = null;
/*      */ 
/*  107 */   String separatorString = null;
/*      */ 
/*  110 */   String formatString = null;
/*      */ 
/*      */   public ASTExpr(StringTemplate enclosingTemplate, AST exprTree, Map options) {
/*  113 */     super(enclosingTemplate);
/*  114 */     this.exprTree = exprTree;
/*  115 */     this.options = options;
/*      */   }
/*      */ 
/*      */   public AST getAST()
/*      */   {
/*  120 */     return this.exprTree;
/*      */   }
/*      */ 
/*      */   public int write(StringTemplate self, StringTemplateWriter out)
/*      */     throws IOException
/*      */   {
/*  134 */     if ((this.exprTree == null) || (self == null) || (out == null)) {
/*  135 */       return 0;
/*      */     }
/*      */ 
/*  138 */     StringTemplateAST anchorAST = (StringTemplateAST)getOption("anchor");
/*  139 */     if (anchorAST != null) {
/*  140 */       out.pushAnchorPoint();
/*      */     }
/*  142 */     out.pushIndentation(getIndentation());
/*  143 */     handleExprOptions(self);
/*      */ 
/*  145 */     ActionEvaluator eval = new ActionEvaluator(self, this, out);
/*      */ 
/*  147 */     int n = 0;
/*      */     try {
/*  149 */       n = eval.action(this.exprTree);
/*      */     }
/*      */     catch (RecognitionException re) {
/*  152 */       self.error("can't evaluate tree: " + this.exprTree.toStringList(), re);
/*      */     }
/*  154 */     out.popIndentation();
/*  155 */     if (anchorAST != null) {
/*  156 */       out.popAnchorPoint();
/*      */     }
/*  158 */     return n;
/*      */   }
/*      */ 
/*      */   protected void handleExprOptions(StringTemplate self)
/*      */   {
/*  165 */     this.formatString = null;
/*  166 */     StringTemplateAST wrapAST = (StringTemplateAST)getOption("wrap");
/*  167 */     if (wrapAST != null) {
/*  168 */       this.wrapString = evaluateExpression(self, wrapAST);
/*      */     }
/*  170 */     StringTemplateAST nullValueAST = (StringTemplateAST)getOption("null");
/*  171 */     if (nullValueAST != null) {
/*  172 */       this.nullValue = evaluateExpression(self, nullValueAST);
/*      */     }
/*  174 */     StringTemplateAST separatorAST = (StringTemplateAST)getOption("separator");
/*  175 */     if (separatorAST != null) {
/*  176 */       this.separatorString = evaluateExpression(self, separatorAST);
/*      */     }
/*      */ 
/*  179 */     StringTemplateAST formatAST = (StringTemplateAST)getOption("format");
/*      */ 
/*  181 */     if (formatAST != null) {
/*  182 */       this.formatString = evaluateExpression(self, formatAST);
/*      */     }
/*      */ 
/*  186 */     if (this.options != null) {
/*  187 */       Iterator it = this.options.keySet().iterator();
/*  188 */       while (it.hasNext()) {
/*  189 */         String option = (String)it.next();
/*  190 */         if (!supportedOptions.contains(option))
/*  191 */           self.warning("ignoring unsupported option: " + option);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public Object applyTemplateToListOfAttributes(StringTemplate self, List attributes, StringTemplate templateToApply)
/*      */   {
/*  206 */     if ((attributes == null) || (templateToApply == null) || (attributes.size() == 0)) {
/*  207 */       return null;
/*      */     }
/*  209 */     Map argumentContext = null;
/*      */ 
/*  211 */     List results = new StringTemplate.STAttributeList();
/*      */ 
/*  214 */     for (int a = 0; a < attributes.size(); a++) {
/*  215 */       Object o = attributes.get(a);
/*  216 */       if (o != null) {
/*  217 */         o = convertAnythingToIterator(o);
/*  218 */         attributes.set(a, o);
/*      */       }
/*      */     }
/*      */ 
/*  222 */     int numAttributes = attributes.size();
/*      */ 
/*  225 */     Map formalArguments = templateToApply.getFormalArguments();
/*  226 */     if ((formalArguments == null) || (formalArguments.size() == 0)) {
/*  227 */       self.error("missing arguments in anonymous template in context " + self.getEnclosingInstanceStackString());
/*      */ 
/*  229 */       return null;
/*      */     }
/*  231 */     Object[] formalArgumentNames = formalArguments.keySet().toArray();
/*  232 */     if (formalArgumentNames.length != numAttributes) {
/*  233 */       self.error("number of arguments " + formalArguments.keySet() + " mismatch between attribute list and anonymous" + " template in context " + self.getEnclosingInstanceStackString());
/*      */ 
/*  237 */       int shorterSize = Math.min(formalArgumentNames.length, numAttributes);
/*  238 */       numAttributes = shorterSize;
/*  239 */       Object[] newFormalArgumentNames = new Object[shorterSize];
/*  240 */       System.arraycopy(formalArgumentNames, 0, newFormalArgumentNames, 0, shorterSize);
/*      */ 
/*  243 */       formalArgumentNames = newFormalArgumentNames;
/*      */     }
/*      */ 
/*  247 */     int i = 0;
/*      */     while (true) {
/*  249 */       argumentContext = new HashMap();
/*      */ 
/*  252 */       int numEmpty = 0;
/*  253 */       for (int a = 0; a < numAttributes; a++) {
/*  254 */         Iterator it = (Iterator)attributes.get(a);
/*  255 */         if ((it != null) && (it.hasNext())) {
/*  256 */           String argName = (String)formalArgumentNames[a];
/*  257 */           Object iteratedValue = it.next();
/*  258 */           argumentContext.put(argName, iteratedValue);
/*      */         }
/*      */         else {
/*  261 */           numEmpty++;
/*      */         }
/*      */       }
/*  264 */       if (numEmpty == numAttributes) {
/*      */         break;
/*      */       }
/*  267 */       argumentContext.put("i", new Integer(i + 1));
/*  268 */       argumentContext.put("i0", new Integer(i));
/*  269 */       StringTemplate embedded = templateToApply.getInstanceOf();
/*  270 */       embedded.setEnclosingInstance(self);
/*  271 */       embedded.setArgumentContext(argumentContext);
/*  272 */       results.add(embedded);
/*  273 */       i++;
/*      */     }
/*      */ 
/*  276 */     return results;
/*      */   }
/*      */ 
/*      */   public Object applyListOfAlternatingTemplates(StringTemplate self, Object attributeValue, List templatesToApply)
/*      */   {
/*  283 */     if ((attributeValue == null) || (templatesToApply == null) || (templatesToApply.size() == 0)) {
/*  284 */       return null;
/*      */     }
/*  286 */     StringTemplate embedded = null;
/*  287 */     Map argumentContext = null;
/*      */ 
/*  291 */     attributeValue = convertArrayToList(attributeValue);
/*  292 */     attributeValue = convertAnythingIteratableToIterator(attributeValue);
/*      */ 
/*  294 */     if ((attributeValue instanceof Iterator))
/*      */     {
/*  296 */       List resultVector = new StringTemplate.STAttributeList();
/*  297 */       Iterator iter = (Iterator)attributeValue;
/*  298 */       int i = 0;
/*  299 */       while (iter.hasNext()) {
/*  300 */         Object ithValue = iter.next();
/*  301 */         if (ithValue == null) {
/*  302 */           if (this.nullValue != null)
/*      */           {
/*  305 */             ithValue = this.nullValue;
/*      */           }
/*      */         } else { int templateIndex = i % templatesToApply.size();
/*  308 */           embedded = (StringTemplate)templatesToApply.get(templateIndex);
/*      */ 
/*  313 */           StringTemplateAST args = embedded.getArgumentsAST();
/*  314 */           embedded = embedded.getInstanceOf();
/*  315 */           embedded.setEnclosingInstance(self);
/*  316 */           embedded.setArgumentsAST(args);
/*  317 */           argumentContext = new HashMap();
/*  318 */           Map formalArgs = embedded.getFormalArguments();
/*  319 */           boolean isAnonymous = embedded.getName() == "anonymous";
/*      */ 
/*  321 */           setSoleFormalArgumentToIthValue(embedded, argumentContext, ithValue);
/*      */ 
/*  323 */           if ((!isAnonymous) || (formalArgs == null) || (formalArgs.size() <= 0)) {
/*  324 */             argumentContext.put("it", ithValue);
/*  325 */             argumentContext.put("attr", ithValue);
/*      */           }
/*  327 */           argumentContext.put("i", new Integer(i + 1));
/*  328 */           argumentContext.put("i0", new Integer(i));
/*  329 */           embedded.setArgumentContext(argumentContext);
/*  330 */           evaluateArguments(embedded);
/*      */ 
/*  336 */           resultVector.add(embedded);
/*  337 */           i++; }
/*      */       }
/*  339 */       if (resultVector.size() == 0) {
/*  340 */         resultVector = null;
/*      */       }
/*  342 */       return resultVector;
/*      */     }
/*      */ 
/*  350 */     embedded = (StringTemplate)templatesToApply.get(0);
/*  351 */     argumentContext = new HashMap();
/*  352 */     Map formalArgs = embedded.getFormalArguments();
/*  353 */     StringTemplateAST args = embedded.getArgumentsAST();
/*  354 */     setSoleFormalArgumentToIthValue(embedded, argumentContext, attributeValue);
/*  355 */     boolean isAnonymous = embedded.getName() == "anonymous";
/*      */ 
/*  358 */     if ((!isAnonymous) || (formalArgs == null) || (formalArgs.size() <= 0)) {
/*  359 */       argumentContext.put("it", attributeValue);
/*  360 */       argumentContext.put("attr", attributeValue);
/*      */     }
/*  362 */     argumentContext.put("i", new Integer(1));
/*  363 */     argumentContext.put("i0", new Integer(0));
/*  364 */     embedded.setArgumentContext(argumentContext);
/*  365 */     evaluateArguments(embedded);
/*  366 */     return embedded;
/*      */   }
/*      */ 
/*      */   protected void setSoleFormalArgumentToIthValue(StringTemplate embedded, Map argumentContext, Object ithValue)
/*      */   {
/*  371 */     Map formalArgs = embedded.getFormalArguments();
/*  372 */     if (formalArgs != null) {
/*  373 */       String soleArgName = null;
/*  374 */       boolean isAnonymous = embedded.getName() == "anonymous";
/*      */ 
/*  376 */       if ((formalArgs.size() == 1) || ((isAnonymous) && (formalArgs.size() > 0))) {
/*  377 */         if ((isAnonymous) && (formalArgs.size() > 1)) {
/*  378 */           embedded.error("too many arguments on {...} template: " + formalArgs);
/*      */         }
/*      */ 
/*  383 */         Set argNames = formalArgs.keySet();
/*  384 */         soleArgName = (String)argNames.toArray()[0];
/*  385 */         argumentContext.put(soleArgName, ithValue);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public Object getObjectProperty(StringTemplate self, Object o, Object propertyName)
/*      */   {
/*  401 */     if ((o == null) || (propertyName == null)) {
/*  402 */       return null;
/*      */     }
/*      */ 
/*  417 */     Object value = rawGetObjectProperty(self, o, propertyName);
/*      */ 
/*  420 */     value = convertArrayToList(value);
/*  421 */     return value;
/*      */   }
/*      */ 
/*      */   protected Object rawGetObjectProperty(StringTemplate self, Object o, Object property) {
/*  425 */     Class c = o.getClass();
/*  426 */     Object value = null;
/*      */ 
/*  430 */     if (c == StringTemplate.Aggregate.class) {
/*  431 */       String propertyName = (String)property;
/*  432 */       value = ((StringTemplate.Aggregate)o).get(propertyName);
/*  433 */       return value;
/*      */     }
/*      */ 
/*  439 */     if (c == StringTemplate.class) {
/*  440 */       Map attributes = ((StringTemplate)o).getAttributes();
/*  441 */       if (attributes != null) {
/*  442 */         String propertyName = (String)property;
/*  443 */         value = attributes.get(propertyName);
/*  444 */         return value;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  450 */     if ((o instanceof Map)) {
/*  451 */       Map map = (Map)o;
/*  452 */       if (property.equals("keys")) {
/*  453 */         value = map.keySet();
/*      */       }
/*  455 */       else if (property.equals("values")) {
/*  456 */         value = map.values();
/*      */       }
/*  458 */       else if (map.containsKey(property)) {
/*  459 */         value = map.get(property);
/*      */       }
/*  461 */       else if (map.containsKey(property.toString()))
/*      */       {
/*  463 */         value = map.get(property.toString());
/*      */       }
/*  466 */       else if (map.containsKey("_default_")) {
/*  467 */         value = map.get("_default_");
/*      */       }
/*      */ 
/*  470 */       if (value == MAP_KEY_VALUE) {
/*  471 */         value = property;
/*      */       }
/*  473 */       return value;
/*      */     }
/*      */ 
/*  479 */     Method m = null;
/*      */ 
/*  504 */     String propertyName = (String)property;
/*  505 */     String methodSuffix = Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1, propertyName.length());
/*      */ 
/*  507 */     m = getMethod(c, "get" + methodSuffix);
/*  508 */     if (m == null) {
/*  509 */       m = getMethod(c, "is" + methodSuffix);
/*      */     }
/*  511 */     if (m != null)
/*      */     {
/*      */       try
/*      */       {
/*  515 */         value = invokeMethod(m, o, value);
/*      */       }
/*      */       catch (Exception e) {
/*  518 */         self.error("Can't get property " + propertyName + " using method get/is" + methodSuffix + " from " + c.getName() + " instance", e);
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*      */       try
/*      */       {
/*  525 */         Field f = c.getField(propertyName);
/*      */         try
/*      */         {
/*  528 */           value = accessField(f, o, value);
/*      */         }
/*      */         catch (IllegalAccessException iae) {
/*  531 */           self.error("Can't access property " + propertyName + " using method get/is" + methodSuffix + " or direct field access from " + c.getName() + " instance", iae);
/*      */         }
/*      */       }
/*      */       catch (NoSuchFieldException nsfe)
/*      */       {
/*  536 */         self.error("Class " + c.getName() + " has no such attribute: " + propertyName + " in template context " + self.getEnclosingInstanceStackString(), nsfe);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  541 */     return value;
/*      */   }
/*      */ 
/*      */   protected Object accessField(Field f, Object o, Object value) throws IllegalAccessException
/*      */   {
/*      */     try {
/*  547 */       f.setAccessible(true);
/*      */     }
/*      */     catch (SecurityException se)
/*      */     {
/*      */     }
/*  552 */     value = f.get(o);
/*  553 */     return value;
/*      */   }
/*      */ 
/*      */   protected Object invokeMethod(Method m, Object o, Object value) throws IllegalAccessException, InvocationTargetException
/*      */   {
/*      */     try {
/*  559 */       m.setAccessible(true);
/*      */     }
/*      */     catch (SecurityException se)
/*      */     {
/*      */     }
/*  564 */     value = m.invoke(o, (Object[])null);
/*  565 */     return value;
/*      */   }
/*      */ 
/*      */   protected Method getMethod(Class c, String methodName) {
/*      */     Method m;
/*      */     try {
/*  571 */       m = c.getMethod(methodName, (Class[])null);
/*      */     }
/*      */     catch (NoSuchMethodException nsme) {
/*  574 */       m = null;
/*      */     }
/*  576 */     return m;
/*      */   }
/*      */ 
/*      */   public boolean testAttributeTrue(Object a)
/*      */   {
/*  597 */     if (a == null) {
/*  598 */       return false;
/*      */     }
/*  600 */     if ((a instanceof Boolean)) {
/*  601 */       return ((Boolean)a).booleanValue();
/*      */     }
/*  603 */     if ((a instanceof Collection)) {
/*  604 */       return ((Collection)a).size() > 0;
/*      */     }
/*  606 */     if ((a instanceof Map)) {
/*  607 */       return ((Map)a).size() > 0;
/*      */     }
/*  609 */     if ((a instanceof Iterator)) {
/*  610 */       return ((Iterator)a).hasNext();
/*      */     }
/*  612 */     return true;
/*      */   }
/*      */ 
/*      */   public Object add(Object a, Object b)
/*      */   {
/*  619 */     if (a == null) {
/*  620 */       return b;
/*      */     }
/*  622 */     if (b == null) {
/*  623 */       return a;
/*      */     }
/*  625 */     return a.toString() + b.toString();
/*      */   }
/*      */ 
/*      */   public StringTemplate getTemplateInclude(StringTemplate enclosing, String templateName, StringTemplateAST argumentsAST)
/*      */   {
/*  637 */     StringTemplateGroup group = enclosing.getGroup();
/*  638 */     StringTemplate embedded = group.getEmbeddedInstanceOf(enclosing, templateName);
/*  639 */     if (embedded == null) {
/*  640 */       enclosing.error("cannot make embedded instance of " + templateName + " in template " + enclosing.getName());
/*      */ 
/*  642 */       return null;
/*      */     }
/*  644 */     embedded.setArgumentsAST(argumentsAST);
/*  645 */     evaluateArguments(embedded);
/*  646 */     return embedded;
/*      */   }
/*      */ 
/*      */   public int writeAttribute(StringTemplate self, Object o, StringTemplateWriter out)
/*      */   {
/*  660 */     return write(self, o, out);
/*      */   }
/*      */ 
/*      */   protected int write(StringTemplate self, Object o, StringTemplateWriter out)
/*      */   {
/*  672 */     if (o == null) {
/*  673 */       if (this.nullValue == null) {
/*  674 */         return -1;
/*      */       }
/*  676 */       o = this.nullValue;
/*      */     }
/*  678 */     int n = 0;
/*      */     try {
/*  680 */       if ((o instanceof StringTemplate)) return writeTemplate(self, o, out);
/*  681 */       o = convertAnythingIteratableToIterator(o);
/*  682 */       if ((o instanceof Iterator)) return writeIterableValue(self, o, out);
/*  683 */       return writePOJO(self, o, out);
/*      */     }
/*      */     catch (IOException io) {
/*  686 */       self.error("problem writing object: " + o, io);
/*      */     }
/*  688 */     return n;
/*      */   }
/*      */ 
/*      */   protected int writePOJO(StringTemplate self, Object o, StringTemplateWriter out) throws IOException {
/*  692 */     int n = 0;
/*  693 */     AttributeRenderer renderer = self.getAttributeRenderer(o.getClass());
/*      */ 
/*  695 */     String v = null;
/*  696 */     if (renderer != null) {
/*  697 */       if (this.formatString != null) v = renderer.toString(o, this.formatString); else
/*  698 */         v = renderer.toString(o);
/*      */     }
/*  700 */     else v = o.toString();
/*  701 */     if (this.wrapString != null) n = out.write(v, this.wrapString); else
/*  702 */       n = out.write(v);
/*  703 */     return n;
/*      */   }
/*      */ 
/*      */   protected int writeTemplate(StringTemplate self, Object o, StringTemplateWriter out) throws IOException {
/*  707 */     int n = 0;
/*  708 */     StringTemplate stToWrite = (StringTemplate)o;
/*      */ 
/*  715 */     stToWrite.setEnclosingInstance(self);
/*      */ 
/*  718 */     if ((StringTemplate.inLintMode()) && (StringTemplate.isRecursiveEnclosingInstance(stToWrite)))
/*      */     {
/*  723 */       throw new IllegalStateException("infinite recursion to " + stToWrite.getTemplateDeclaratorString() + " referenced in " + stToWrite.getEnclosingInstance().getTemplateDeclaratorString() + "; stack trace:\n" + stToWrite.getEnclosingInstanceStackTrace());
/*      */     }
/*      */ 
/*  731 */     if (this.wrapString != null) {
/*  732 */       n = out.writeWrapSeparator(this.wrapString);
/*      */     }
/*      */ 
/*  735 */     if (this.formatString != null) {
/*  736 */       AttributeRenderer renderer = self.getAttributeRenderer(String.class);
/*      */ 
/*  738 */       if (renderer != null)
/*      */       {
/*  742 */         StringWriter buf = new StringWriter();
/*  743 */         StringTemplateWriter sw = self.getGroup().getStringTemplateWriter(buf);
/*      */ 
/*  745 */         stToWrite.write(sw);
/*  746 */         n = out.write(renderer.toString(buf.toString(), this.formatString));
/*  747 */         return n;
/*      */       }
/*      */     }
/*  750 */     n = stToWrite.write(out);
/*      */ 
/*  752 */     return n;
/*      */   }
/*      */ 
/*      */   protected int writeIterableValue(StringTemplate self, Object o, StringTemplateWriter out)
/*      */     throws IOException
/*      */   {
/*  760 */     int n = 0;
/*  761 */     Iterator iter = (Iterator)o;
/*  762 */     boolean seenAValue = false;
/*  763 */     while (iter.hasNext()) {
/*  764 */       Object iterValue = iter.next();
/*  765 */       if (iterValue == null) iterValue = this.nullValue;
/*  766 */       if (iterValue != null)
/*      */       {
/*  769 */         if (this.separatorString == null)
/*      */         {
/*  772 */           int nw = write(self, iterValue, out);
/*  773 */           if (nw != -1) n += nw;
/*      */ 
/*      */         }
/*  779 */         else if ((iterValue instanceof StringTemplate)) {
/*  780 */           StringTemplate st = (StringTemplate)iterValue;
/*  781 */           int nchunks = st.getChunks() != null ? st.getChunks().size() : 0;
/*  782 */           boolean nullable = true;
/*  783 */           for (int i = 0; i < nchunks; i++) {
/*  784 */             Expr a = (Expr)st.getChunks().get(i);
/*  785 */             if (!(a instanceof ConditionalExpr)) nullable = false;
/*      */           }
/*      */ 
/*  788 */           if (!nullable) {
/*  789 */             if ((seenAValue) && (this.separatorString != null)) {
/*  790 */               n += out.writeSeparator(this.separatorString);
/*      */             }
/*  792 */             int nw = write(self, iterValue, out);
/*  793 */             n += nw;
/*  794 */             seenAValue = true;
/*      */           }
/*      */ 
/*      */         }
/*  799 */         else if ((!(iterValue instanceof StringTemplate)) && (!(iterValue instanceof Iterator)))
/*      */         {
/*  804 */           if ((seenAValue) && (this.separatorString != null)) {
/*  805 */             n += out.writeSeparator(this.separatorString);
/*      */           }
/*  807 */           int nw = write(self, iterValue, out);
/*  808 */           seenAValue = true;
/*  809 */           n += nw;
/*      */         }
/*      */         else
/*      */         {
/*  823 */           StringWriter buf = new StringWriter();
/*  824 */           StringTemplateWriter sw = self.getGroup().getStringTemplateWriter(buf);
/*      */ 
/*  826 */           int tmpsize = write(self, iterValue, sw);
/*      */ 
/*  828 */           if (tmpsize != -1) {
/*  829 */             if ((seenAValue) && (this.separatorString != null)) {
/*  830 */               n += out.writeSeparator(this.separatorString);
/*      */             }
/*      */ 
/*  833 */             int nw = write(self, iterValue, out);
/*  834 */             n += nw;
/*  835 */             seenAValue = true;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  839 */     return n;
/*      */   }
/*      */ 
/*      */   public String evaluateExpression(StringTemplate self, Object expr)
/*      */   {
/*  851 */     if (expr == null) {
/*  852 */       return null;
/*      */     }
/*  854 */     if ((expr instanceof StringTemplateAST)) {
/*  855 */       StringTemplateAST exprAST = (StringTemplateAST)expr;
/*      */ 
/*  857 */       StringWriter buf = new StringWriter();
/*  858 */       StringTemplateWriter sw = self.getGroup().getStringTemplateWriter(buf);
/*      */ 
/*  861 */       ActionEvaluator eval = new ActionEvaluator(self, this, sw);
/*      */       try
/*      */       {
/*  864 */         eval.action(exprAST);
/*      */       }
/*      */       catch (RecognitionException re) {
/*  867 */         self.error("can't evaluate tree: " + this.exprTree.toStringList(), re);
/*      */       }
/*      */ 
/*  870 */       return buf.toString();
/*      */     }
/*      */ 
/*  874 */     return expr.toString();
/*      */   }
/*      */ 
/*      */   protected void evaluateArguments(StringTemplate self)
/*      */   {
/*  884 */     StringTemplateAST argumentsAST = self.getArgumentsAST();
/*  885 */     if ((argumentsAST == null) || (argumentsAST.getFirstChild() == null))
/*      */     {
/*  887 */       return;
/*      */     }
/*      */ 
/*  895 */     StringTemplate enclosing = self.getEnclosingInstance();
/*  896 */     StringTemplate argContextST = new StringTemplate(self.getGroup(), "");
/*  897 */     argContextST.setName("<invoke " + self.getName() + " arg context>");
/*  898 */     argContextST.setEnclosingInstance(enclosing);
/*  899 */     argContextST.setArgumentContext(self.getArgumentContext());
/*      */ 
/*  901 */     ActionEvaluator eval = new ActionEvaluator(argContextST, this, null);
/*      */     try
/*      */     {
/*  913 */       Map ac = eval.argList(argumentsAST, self, self.getArgumentContext());
/*  914 */       self.setArgumentContext(ac);
/*      */     }
/*      */     catch (RecognitionException re) {
/*  917 */       self.error("can't evaluate tree: " + argumentsAST.toStringList(), re);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static Object convertArrayToList(Object value)
/*      */   {
/*  937 */     if (value == null) {
/*  938 */       return null;
/*      */     }
/*  940 */     if (value.getClass().isArray()) {
/*  941 */       if (value.getClass().getComponentType().isPrimitive()) {
/*  942 */         return new ArrayWrappedInList(value);
/*      */       }
/*  944 */       return Arrays.asList((Object[])value);
/*      */     }
/*  946 */     return value;
/*      */   }
/*      */ 
/*      */   protected static Object convertAnythingIteratableToIterator(Object o) {
/*  950 */     Iterator iter = null;
/*  951 */     if ((o instanceof Collection)) {
/*  952 */       iter = ((Collection)o).iterator();
/*      */     }
/*  954 */     else if ((o instanceof Map)) {
/*  955 */       iter = ((Map)o).values().iterator();
/*      */     }
/*  957 */     else if ((o instanceof Iterator)) {
/*  958 */       iter = (Iterator)o;
/*      */     }
/*  960 */     if (iter == null) {
/*  961 */       return o;
/*      */     }
/*  963 */     return iter;
/*      */   }
/*      */ 
/*      */   protected static Iterator convertAnythingToIterator(Object o) {
/*  967 */     Iterator iter = null;
/*  968 */     if ((o instanceof Collection)) {
/*  969 */       iter = ((Collection)o).iterator();
/*      */     }
/*  971 */     else if ((o instanceof Map)) {
/*  972 */       iter = ((Map)o).values().iterator();
/*      */     }
/*  974 */     else if ((o instanceof Iterator)) {
/*  975 */       iter = (Iterator)o;
/*      */     }
/*  977 */     if (iter == null) {
/*  978 */       List singleton = new StringTemplate.STAttributeList(1);
/*  979 */       singleton.add(o);
/*  980 */       return singleton.iterator();
/*      */     }
/*  982 */     return iter;
/*      */   }
/*      */ 
/*      */   public Object first(Object attribute)
/*      */   {
/*  989 */     if (attribute == null) {
/*  990 */       return null;
/*      */     }
/*  992 */     Object f = attribute;
/*  993 */     attribute = convertAnythingIteratableToIterator(attribute);
/*  994 */     if ((attribute instanceof Iterator)) {
/*  995 */       Iterator it = (Iterator)attribute;
/*  996 */       if (it.hasNext()) {
/*  997 */         f = it.next();
/*      */       }
/*      */     }
/*      */ 
/* 1001 */     return f;
/*      */   }
/*      */ 
/*      */   public Object rest(Object attribute)
/*      */   {
/* 1008 */     if (attribute == null) {
/* 1009 */       return null;
/*      */     }
/* 1011 */     Object theRest = attribute;
/* 1012 */     attribute = convertAnythingIteratableToIterator(attribute);
/* 1013 */     if ((attribute instanceof Iterator)) {
/* 1014 */       List a = new ArrayList();
/* 1015 */       Iterator it = (Iterator)attribute;
/* 1016 */       if (!it.hasNext()) {
/* 1017 */         return null;
/*      */       }
/* 1019 */       it.next();
/* 1020 */       while (it.hasNext()) {
/* 1021 */         Object o = it.next();
/* 1022 */         if (o != null) a.add(o);
/*      */       }
/* 1024 */       return a;
/*      */     }
/*      */ 
/* 1027 */     theRest = null;
/*      */ 
/* 1030 */     return theRest;
/*      */   }
/*      */ 
/*      */   public Object last(Object attribute)
/*      */   {
/* 1039 */     if (attribute == null) {
/* 1040 */       return null;
/*      */     }
/* 1042 */     Object last = attribute;
/* 1043 */     attribute = convertAnythingIteratableToIterator(attribute);
/* 1044 */     if ((attribute instanceof Iterator)) {
/* 1045 */       Iterator it = (Iterator)attribute;
/* 1046 */       while (it.hasNext()) {
/* 1047 */         last = it.next();
/*      */       }
/*      */     }
/*      */ 
/* 1051 */     return last;
/*      */   }
/*      */ 
/*      */   public Object strip(Object attribute)
/*      */   {
/* 1056 */     if (attribute == null) {
/* 1057 */       return null;
/*      */     }
/* 1059 */     attribute = convertAnythingIteratableToIterator(attribute);
/* 1060 */     if ((attribute instanceof Iterator)) {
/* 1061 */       List a = new ArrayList();
/* 1062 */       Iterator it = (Iterator)attribute;
/* 1063 */       while (it.hasNext()) {
/* 1064 */         Object o = it.next();
/* 1065 */         if (o != null) a.add(o);
/*      */       }
/* 1067 */       return a;
/*      */     }
/* 1069 */     return attribute;
/*      */   }
/*      */ 
/*      */   public Object trunc(Object attribute)
/*      */   {
/* 1074 */     if (attribute == null) {
/* 1075 */       return null;
/*      */     }
/* 1077 */     attribute = convertAnythingIteratableToIterator(attribute);
/* 1078 */     if ((attribute instanceof Iterator)) {
/* 1079 */       List a = new ArrayList();
/* 1080 */       Iterator it = (Iterator)attribute;
/* 1081 */       while (it.hasNext()) {
/* 1082 */         Object o = it.next();
/* 1083 */         if (it.hasNext()) a.add(o);
/*      */       }
/* 1085 */       return a;
/*      */     }
/* 1087 */     return null;
/*      */   }
/*      */ 
/*      */   public Object length(Object attribute)
/*      */   {
/* 1096 */     if (attribute == null) {
/* 1097 */       return new Integer(0);
/*      */     }
/* 1099 */     int i = 1;
/* 1100 */     if ((attribute instanceof Map)) {
/* 1101 */       i = ((Map)attribute).size();
/*      */     }
/* 1103 */     else if ((attribute instanceof Collection)) {
/* 1104 */       i = ((Collection)attribute).size();
/*      */     }
/* 1106 */     else if ((attribute instanceof Object[])) {
/* 1107 */       Object[] list = (Object[])attribute;
/* 1108 */       i = list.length;
/*      */     }
/* 1110 */     else if ((attribute instanceof int[])) {
/* 1111 */       int[] list = (int[])attribute;
/* 1112 */       i = list.length;
/*      */     }
/* 1114 */     else if ((attribute instanceof long[])) {
/* 1115 */       long[] list = (long[])attribute;
/* 1116 */       i = list.length;
/*      */     }
/* 1118 */     else if ((attribute instanceof float[])) {
/* 1119 */       float[] list = (float[])attribute;
/* 1120 */       i = list.length;
/*      */     }
/* 1122 */     else if ((attribute instanceof double[])) {
/* 1123 */       double[] list = (double[])attribute;
/* 1124 */       i = list.length;
/*      */     }
/* 1126 */     else if ((attribute instanceof Iterator)) {
/* 1127 */       Iterator it = (Iterator)attribute;
/* 1128 */       i = 0;
/* 1129 */       while (it.hasNext()) {
/* 1130 */         it.next();
/* 1131 */         i++;
/*      */       }
/*      */     }
/* 1134 */     return new Integer(i);
/*      */   }
/*      */ 
/*      */   public Object getOption(String name) {
/* 1138 */     Object value = null;
/* 1139 */     if (this.options != null) {
/* 1140 */       value = this.options.get(name);
/* 1141 */       if (value == "empty expr option") {
/* 1142 */         return defaultOptionValues.get(name);
/*      */       }
/*      */     }
/* 1145 */     return value;
/*      */   }
/*      */ 
/*      */   public String toString() {
/* 1149 */     return this.exprTree.toStringList();
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.stringtemplate.language.ASTExpr
 * JD-Core Version:    0.6.2
 */