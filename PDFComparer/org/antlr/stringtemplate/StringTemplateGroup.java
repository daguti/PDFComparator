/*      */ package org.antlr.stringtemplate;
/*      */ 
/*      */ import java.io.BufferedReader;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.PrintStream;
/*      */ import java.io.Reader;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.io.Writer;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.antlr.stringtemplate.language.AngleBracketTemplateLexer;
/*      */ import org.antlr.stringtemplate.language.DefaultTemplateLexer;
/*      */ import org.antlr.stringtemplate.language.GroupLexer;
/*      */ import org.antlr.stringtemplate.language.GroupParser;
/*      */ 
/*      */ public class StringTemplateGroup
/*      */ {
/*      */   protected String name;
/*   65 */   protected Map templates = new HashMap();
/*      */ 
/*   70 */   protected Map maps = new HashMap();
/*      */ 
/*   73 */   protected Class templateLexerClass = null;
/*      */ 
/*   79 */   protected static Class defaultTemplateLexerClass = DefaultTemplateLexer.class;
/*      */ 
/*   84 */   protected String rootDir = null;
/*      */ 
/*   87 */   protected static Map nameToGroupMap = Collections.synchronizedMap(new HashMap());
/*      */ 
/*   90 */   protected static Map nameToInterfaceMap = Collections.synchronizedMap(new HashMap());
/*      */ 
/*   95 */   protected StringTemplateGroup superGroup = null;
/*      */ 
/*   98 */   protected List interfaces = null;
/*      */ 
/*  111 */   protected boolean templatesDefinedInGroupFile = false;
/*      */   protected Class userSpecifiedWriter;
/*  118 */   protected boolean debugTemplateOutput = false;
/*      */   protected Set noDebugStartStopStrings;
/*      */   protected Map attributeRenderers;
/*  168 */   private static StringTemplateGroupLoader groupLoader = null;
/*      */ 
/*  173 */   protected StringTemplateErrorListener listener = DEFAULT_ERROR_LISTENER;
/*      */ 
/*  175 */   public static StringTemplateErrorListener DEFAULT_ERROR_LISTENER = new StringTemplateErrorListener()
/*      */   {
/*      */     public void error(String s, Throwable e) {
/*  178 */       System.err.println(s);
/*  179 */       if (e != null)
/*  180 */         e.printStackTrace(System.err);
/*      */     }
/*      */ 
/*      */     public void warning(String s) {
/*  184 */       System.out.println(s);
/*      */     }
/*  175 */   };
/*      */ 
/*  191 */   protected static final StringTemplate NOT_FOUND_ST = new StringTemplate();
/*      */ 
/*  195 */   protected int refreshIntervalInSeconds = 2147483;
/*  196 */   protected long lastCheckedDisk = 0L;
/*      */ 
/*  201 */   String fileCharEncoding = System.getProperty("file.encoding");
/*      */ 
/*      */   public StringTemplateGroup(String name, String rootDir)
/*      */   {
/*  207 */     this(name, rootDir, DefaultTemplateLexer.class);
/*      */   }
/*      */ 
/*      */   public StringTemplateGroup(String name, String rootDir, Class lexer) {
/*  211 */     this.name = name;
/*  212 */     this.rootDir = rootDir;
/*  213 */     this.lastCheckedDisk = System.currentTimeMillis();
/*  214 */     nameToGroupMap.put(name, this);
/*  215 */     this.templateLexerClass = lexer;
/*      */   }
/*      */ 
/*      */   public StringTemplateGroup(String name)
/*      */   {
/*  222 */     this(name, null, null);
/*      */   }
/*      */ 
/*      */   public StringTemplateGroup(String name, Class lexer) {
/*  226 */     this(name, null, lexer);
/*      */   }
/*      */ 
/*      */   public StringTemplateGroup(Reader r)
/*      */   {
/*  240 */     this(r, AngleBracketTemplateLexer.class, DEFAULT_ERROR_LISTENER, (StringTemplateGroup)null);
/*      */   }
/*      */ 
/*      */   public StringTemplateGroup(Reader r, StringTemplateErrorListener errors) {
/*  244 */     this(r, AngleBracketTemplateLexer.class, errors, (StringTemplateGroup)null);
/*      */   }
/*      */ 
/*      */   public StringTemplateGroup(Reader r, Class lexer) {
/*  248 */     this(r, lexer, null, (StringTemplateGroup)null);
/*      */   }
/*      */ 
/*      */   public StringTemplateGroup(Reader r, Class lexer, StringTemplateErrorListener errors) {
/*  252 */     this(r, lexer, errors, (StringTemplateGroup)null);
/*      */   }
/*      */ 
/*      */   public StringTemplateGroup(Reader r, Class lexer, StringTemplateErrorListener errors, StringTemplateGroup superGroup)
/*      */   {
/*  264 */     this.templatesDefinedInGroupFile = true;
/*      */ 
/*  266 */     if (lexer == null) {
/*  267 */       lexer = AngleBracketTemplateLexer.class;
/*      */     }
/*  269 */     this.templateLexerClass = lexer;
/*  270 */     if (errors != null) {
/*  271 */       this.listener = errors;
/*      */     }
/*  273 */     setSuperGroup(superGroup);
/*  274 */     parseGroup(r);
/*  275 */     nameToGroupMap.put(this.name, this);
/*  276 */     verifyInterfaceImplementations();
/*      */   }
/*      */ 
/*      */   public Class getTemplateLexerClass()
/*      */   {
/*  283 */     if (this.templateLexerClass != null) {
/*  284 */       return this.templateLexerClass;
/*      */     }
/*  286 */     return defaultTemplateLexerClass;
/*      */   }
/*      */ 
/*      */   public String getName() {
/*  290 */     return this.name;
/*      */   }
/*      */ 
/*      */   public void setName(String name) {
/*  294 */     this.name = name;
/*      */   }
/*      */ 
/*      */   public void setSuperGroup(StringTemplateGroup superGroup) {
/*  298 */     this.superGroup = superGroup;
/*      */   }
/*      */ 
/*      */   public void setSuperGroup(String superGroupName)
/*      */   {
/*  306 */     StringTemplateGroup superGroup = (StringTemplateGroup)nameToGroupMap.get(superGroupName);
/*      */ 
/*  308 */     if (superGroup != null) {
/*  309 */       setSuperGroup(superGroup);
/*  310 */       return;
/*      */     }
/*      */ 
/*  313 */     superGroup = loadGroup(superGroupName, this.templateLexerClass, null);
/*  314 */     if (superGroup != null) {
/*  315 */       nameToGroupMap.put(superGroupName, superGroup);
/*  316 */       setSuperGroup(superGroup);
/*      */     }
/*  319 */     else if (groupLoader == null) {
/*  320 */       this.listener.error("no group loader registered", null);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void implementInterface(StringTemplateGroupInterface I)
/*      */   {
/*  327 */     if (this.interfaces == null) {
/*  328 */       this.interfaces = new ArrayList();
/*      */     }
/*  330 */     this.interfaces.add(I);
/*      */   }
/*      */ 
/*      */   public void implementInterface(String interfaceName)
/*      */   {
/*  337 */     StringTemplateGroupInterface I = (StringTemplateGroupInterface)nameToInterfaceMap.get(interfaceName);
/*      */ 
/*  339 */     if (I != null) {
/*  340 */       implementInterface(I);
/*  341 */       return;
/*      */     }
/*  343 */     I = loadInterface(interfaceName);
/*  344 */     if (I != null) {
/*  345 */       nameToInterfaceMap.put(interfaceName, I);
/*  346 */       implementInterface(I);
/*      */     }
/*  349 */     else if (groupLoader == null) {
/*  350 */       this.listener.error("no group loader registered", null);
/*      */     }
/*      */   }
/*      */ 
/*      */   public StringTemplateGroup getSuperGroup()
/*      */   {
/*  356 */     return this.superGroup;
/*      */   }
/*      */ 
/*      */   public String getGroupHierarchyStackString()
/*      */   {
/*  361 */     List groupNames = new LinkedList();
/*  362 */     StringTemplateGroup p = this;
/*  363 */     while (p != null) {
/*  364 */       groupNames.add(0, p.name);
/*  365 */       p = p.superGroup;
/*      */     }
/*  367 */     return groupNames.toString().replaceAll(",", "");
/*      */   }
/*      */ 
/*      */   public String getRootDir() {
/*  371 */     return this.rootDir;
/*      */   }
/*      */ 
/*      */   public void setRootDir(String rootDir) {
/*  375 */     this.rootDir = rootDir;
/*      */   }
/*      */ 
/*      */   public StringTemplate createStringTemplate()
/*      */   {
/*  380 */     StringTemplate st = new StringTemplate();
/*  381 */     return st;
/*      */   }
/*      */ 
/*      */   protected StringTemplate getInstanceOf(StringTemplate enclosingInstance, String name)
/*      */     throws IllegalArgumentException
/*      */   {
/*  392 */     StringTemplate st = lookupTemplate(enclosingInstance, name);
/*  393 */     if (st != null) {
/*  394 */       StringTemplate instanceST = st.getInstanceOf();
/*  395 */       return instanceST;
/*      */     }
/*  397 */     return null;
/*      */   }
/*      */ 
/*      */   public StringTemplate getInstanceOf(String name)
/*      */   {
/*  404 */     return getInstanceOf(null, name);
/*      */   }
/*      */ 
/*      */   public StringTemplate getInstanceOf(String name, Map attributes)
/*      */   {
/*  412 */     StringTemplate st = getInstanceOf(name);
/*  413 */     st.attributes = attributes;
/*  414 */     return st;
/*      */   }
/*      */ 
/*      */   public StringTemplate getEmbeddedInstanceOf(StringTemplate enclosingInstance, String name)
/*      */     throws IllegalArgumentException
/*      */   {
/*  426 */     StringTemplate st = null;
/*      */ 
/*  428 */     if (name.startsWith("super."))
/*      */     {
/*  433 */       st = enclosingInstance.getNativeGroup().getInstanceOf(enclosingInstance, name);
/*      */     }
/*      */     else {
/*  436 */       st = getInstanceOf(enclosingInstance, name);
/*      */     }
/*      */ 
/*  440 */     st.setGroup(this);
/*  441 */     st.setEnclosingInstance(enclosingInstance);
/*  442 */     return st;
/*      */   }
/*      */ 
/*      */   public synchronized StringTemplate lookupTemplate(StringTemplate enclosingInstance, String name)
/*      */     throws IllegalArgumentException
/*      */   {
/*  458 */     if (name.startsWith("super.")) {
/*  459 */       if (this.superGroup != null) {
/*  460 */         int dot = name.indexOf('.');
/*  461 */         name = name.substring(dot + 1, name.length());
/*  462 */         StringTemplate superScopeST = this.superGroup.lookupTemplate(enclosingInstance, name);
/*      */ 
/*  469 */         return superScopeST;
/*      */       }
/*  471 */       throw new IllegalArgumentException(getName() + " has no super group; invalid template: " + name);
/*      */     }
/*      */ 
/*  474 */     checkRefreshInterval();
/*  475 */     StringTemplate st = (StringTemplate)this.templates.get(name);
/*  476 */     if (st == null)
/*      */     {
/*  478 */       if (!this.templatesDefinedInGroupFile)
/*      */       {
/*  480 */         st = loadTemplateFromBeneathRootDirOrCLASSPATH(getFileNameFromTemplateName(name));
/*      */       }
/*  482 */       if ((st == null) && (this.superGroup != null))
/*      */       {
/*  484 */         st = this.superGroup.getInstanceOf(name);
/*      */ 
/*  487 */         if (st != null) {
/*  488 */           st.setGroup(this);
/*      */         }
/*      */       }
/*  491 */       if (st != null)
/*      */       {
/*  495 */         this.templates.put(name, st);
/*      */       }
/*      */       else
/*      */       {
/*  499 */         this.templates.put(name, NOT_FOUND_ST);
/*  500 */         String context = "";
/*  501 */         if (enclosingInstance != null) {
/*  502 */           context = "; context is " + enclosingInstance.getEnclosingInstanceStackString();
/*      */         }
/*      */ 
/*  505 */         String hier = getGroupHierarchyStackString();
/*  506 */         context = context + "; group hierarchy is " + hier;
/*  507 */         throw new IllegalArgumentException("Can't find template " + getFileNameFromTemplateName(name) + context);
/*      */       }
/*      */ 
/*      */     }
/*  512 */     else if (st == NOT_FOUND_ST) {
/*  513 */       return null;
/*      */     }
/*      */ 
/*  516 */     return st;
/*      */   }
/*      */ 
/*      */   public StringTemplate lookupTemplate(String name) {
/*  520 */     return lookupTemplate(null, name);
/*      */   }
/*      */ 
/*      */   protected void checkRefreshInterval() {
/*  524 */     if (this.templatesDefinedInGroupFile) {
/*  525 */       return;
/*      */     }
/*  527 */     boolean timeToFlush = (this.refreshIntervalInSeconds == 0) || (System.currentTimeMillis() - this.lastCheckedDisk >= this.refreshIntervalInSeconds * 1000);
/*      */ 
/*  529 */     if (timeToFlush)
/*      */     {
/*  531 */       this.templates.clear();
/*  532 */       this.lastCheckedDisk = System.currentTimeMillis();
/*      */     }
/*      */   }
/*      */ 
/*      */   protected StringTemplate loadTemplate(String name, BufferedReader r)
/*      */     throws IOException
/*      */   {
/*  540 */     String nl = System.getProperty("line.separator");
/*  541 */     StringBuffer buf = new StringBuffer(300);
/*      */     String line;
/*  542 */     while ((line = r.readLine()) != null) {
/*  543 */       buf.append(line);
/*  544 */       buf.append(nl);
/*      */     }
/*      */ 
/*  548 */     String pattern = buf.toString().trim();
/*  549 */     if (pattern.length() == 0) {
/*  550 */       error("no text in template '" + name + "'");
/*  551 */       return null;
/*      */     }
/*  553 */     return defineTemplate(name, pattern);
/*      */   }
/*      */ 
/*      */   protected StringTemplate loadTemplateFromBeneathRootDirOrCLASSPATH(String fileName)
/*      */   {
/*  563 */     StringTemplate template = null;
/*  564 */     String name = getTemplateNameFromFileName(fileName);
/*      */ 
/*  566 */     if (this.rootDir == null) {
/*  567 */       ClassLoader cl = Thread.currentThread().getContextClassLoader();
/*  568 */       InputStream is = cl.getResourceAsStream(fileName);
/*  569 */       if (is == null) {
/*  570 */         cl = getClass().getClassLoader();
/*  571 */         is = cl.getResourceAsStream(fileName);
/*      */       }
/*  573 */       if (is == null) {
/*  574 */         return null;
/*      */       }
/*  576 */       BufferedReader br = null;
/*      */       try {
/*  578 */         br = new BufferedReader(getInputStreamReader(is));
/*  579 */         template = loadTemplate(name, br);
/*      */       }
/*      */       catch (IOException ioe) {
/*  582 */         error("Problem reading template file: " + fileName, ioe);
/*      */       }
/*      */       finally {
/*  585 */         if (br != null) {
/*      */           try {
/*  587 */             br.close();
/*      */           }
/*      */           catch (IOException ioe2) {
/*  590 */             error("Cannot close template file: " + fileName, ioe2);
/*      */           }
/*      */         }
/*      */       }
/*  594 */       return template;
/*      */     }
/*      */ 
/*  597 */     template = loadTemplate(name, this.rootDir + "/" + fileName);
/*  598 */     return template;
/*      */   }
/*      */ 
/*      */   public String getFileNameFromTemplateName(String templateName)
/*      */   {
/*  605 */     return templateName + ".st";
/*      */   }
/*      */ 
/*      */   public String getTemplateNameFromFileName(String fileName)
/*      */   {
/*  613 */     String name = fileName;
/*  614 */     int suffix = name.lastIndexOf(".st");
/*  615 */     if (suffix >= 0) {
/*  616 */       name = name.substring(0, suffix);
/*      */     }
/*  618 */     return name;
/*      */   }
/*      */ 
/*      */   protected StringTemplate loadTemplate(String name, String fileName)
/*      */   {
/*  623 */     BufferedReader br = null;
/*  624 */     StringTemplate template = null;
/*      */     try {
/*  626 */       InputStream fin = new FileInputStream(fileName);
/*  627 */       InputStreamReader isr = getInputStreamReader(fin);
/*  628 */       br = new BufferedReader(isr);
/*  629 */       template = loadTemplate(name, br);
/*  630 */       br.close();
/*  631 */       br = null;
/*      */     }
/*      */     catch (IOException ioe) {
/*  634 */       if (br != null) {
/*      */         try {
/*  636 */           br.close();
/*      */         }
/*      */         catch (IOException ioe2) {
/*  639 */           error("Cannot close template file: " + fileName);
/*      */         }
/*      */       }
/*      */     }
/*  643 */     return template;
/*      */   }
/*      */ 
/*      */   protected InputStreamReader getInputStreamReader(InputStream in) {
/*  647 */     InputStreamReader isr = null;
/*      */     try {
/*  649 */       isr = new InputStreamReader(in, this.fileCharEncoding);
/*      */     }
/*      */     catch (UnsupportedEncodingException uee) {
/*  652 */       error("Invalid file character encoding: " + this.fileCharEncoding);
/*      */     }
/*  654 */     return isr;
/*      */   }
/*      */ 
/*      */   public String getFileCharEncoding() {
/*  658 */     return this.fileCharEncoding;
/*      */   }
/*      */ 
/*      */   public void setFileCharEncoding(String fileCharEncoding) {
/*  662 */     this.fileCharEncoding = fileCharEncoding;
/*      */   }
/*      */ 
/*      */   public synchronized StringTemplate defineTemplate(String name, String template)
/*      */   {
/*  672 */     if ((name != null) && (name.indexOf('.') >= 0)) {
/*  673 */       throw new IllegalArgumentException("cannot have '.' in template names");
/*      */     }
/*  675 */     StringTemplate st = createStringTemplate();
/*  676 */     st.setName(name);
/*  677 */     st.setGroup(this);
/*  678 */     st.setNativeGroup(this);
/*  679 */     st.setTemplate(template);
/*  680 */     st.setErrorListener(this.listener);
/*  681 */     this.templates.put(name, st);
/*  682 */     return st;
/*      */   }
/*      */ 
/*      */   public StringTemplate defineRegionTemplate(String enclosingTemplateName, String regionName, String template, int type)
/*      */   {
/*  691 */     String mangledName = getMangledRegionName(enclosingTemplateName, regionName);
/*      */ 
/*  693 */     StringTemplate regionST = defineTemplate(mangledName, template);
/*  694 */     regionST.setIsRegion(true);
/*  695 */     regionST.setRegionDefType(type);
/*  696 */     return regionST;
/*      */   }
/*      */ 
/*      */   public StringTemplate defineRegionTemplate(StringTemplate enclosingTemplate, String regionName, String template, int type)
/*      */   {
/*  705 */     StringTemplate regionST = defineRegionTemplate(enclosingTemplate.getOutermostName(), regionName, template, type);
/*      */ 
/*  710 */     enclosingTemplate.getOutermostEnclosingInstance().addRegionName(regionName);
/*  711 */     return regionST;
/*      */   }
/*      */ 
/*      */   public StringTemplate defineImplicitRegionTemplate(StringTemplate enclosingTemplate, String name)
/*      */   {
/*  725 */     return defineRegionTemplate(enclosingTemplate, name, "", 1);
/*      */   }
/*      */ 
/*      */   public String getMangledRegionName(String enclosingTemplateName, String name)
/*      */   {
/*  736 */     return "region__" + enclosingTemplateName + "__" + name;
/*      */   }
/*      */ 
/*      */   public String getUnMangledTemplateName(String mangledName)
/*      */   {
/*  742 */     return mangledName.substring("region__".length(), mangledName.lastIndexOf("__"));
/*      */   }
/*      */ 
/*      */   public synchronized StringTemplate defineTemplateAlias(String name, String target)
/*      */   {
/*  748 */     StringTemplate targetST = getTemplateDefinition(target);
/*  749 */     if (targetST == null) {
/*  750 */       error("cannot alias " + name + " to undefined template: " + target);
/*  751 */       return null;
/*      */     }
/*  753 */     this.templates.put(name, targetST);
/*  754 */     return targetST;
/*      */   }
/*      */ 
/*      */   public synchronized boolean isDefinedInThisGroup(String name) {
/*  758 */     StringTemplate st = (StringTemplate)this.templates.get(name);
/*  759 */     if (st != null) {
/*  760 */       if (st.isRegion())
/*      */       {
/*  762 */         if (st.getRegionDefType() == 1) {
/*  763 */           return false;
/*      */         }
/*      */       }
/*  766 */       return true;
/*      */     }
/*  768 */     return false;
/*      */   }
/*      */ 
/*      */   public synchronized StringTemplate getTemplateDefinition(String name)
/*      */   {
/*  773 */     return (StringTemplate)this.templates.get(name);
/*      */   }
/*      */ 
/*      */   public boolean isDefined(String name)
/*      */   {
/*      */     try
/*      */     {
/*  781 */       return lookupTemplate(name) != null;
/*      */     } catch (IllegalArgumentException iae) {
/*      */     }
/*  784 */     return false;
/*      */   }
/*      */ 
/*      */   protected void parseGroup(Reader r)
/*      */   {
/*      */     try {
/*  790 */       GroupLexer lexer = new GroupLexer(r);
/*  791 */       GroupParser parser = new GroupParser(lexer);
/*  792 */       parser.group(this);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  796 */       String name = "<unknown>";
/*  797 */       if (getName() != null) {
/*  798 */         name = getName();
/*      */       }
/*  800 */       error("problem parsing group " + name + ": " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void verifyInterfaceImplementations()
/*      */   {
/*  806 */     for (int i = 0; (this.interfaces != null) && (i < this.interfaces.size()); i++) {
/*  807 */       StringTemplateGroupInterface I = (StringTemplateGroupInterface)this.interfaces.get(i);
/*      */ 
/*  809 */       List missing = I.getMissingTemplates(this);
/*  810 */       List mismatched = I.getMismatchedTemplates(this);
/*  811 */       if (missing != null) {
/*  812 */         error("group " + getName() + " does not satisfy interface " + I.getName() + ": missing templates " + missing);
/*      */       }
/*      */ 
/*  815 */       if (mismatched != null)
/*  816 */         error("group " + getName() + " does not satisfy interface " + I.getName() + ": mismatched arguments on these templates " + mismatched);
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getRefreshInterval()
/*      */   {
/*  823 */     return this.refreshIntervalInSeconds;
/*      */   }
/*      */ 
/*      */   public void setRefreshInterval(int refreshInterval)
/*      */   {
/*  833 */     this.refreshIntervalInSeconds = refreshInterval;
/*      */   }
/*      */ 
/*      */   public void setErrorListener(StringTemplateErrorListener listener) {
/*  837 */     this.listener = listener;
/*      */   }
/*      */ 
/*      */   public StringTemplateErrorListener getErrorListener() {
/*  841 */     return this.listener;
/*      */   }
/*      */ 
/*      */   public void setStringTemplateWriter(Class c)
/*      */   {
/*  848 */     this.userSpecifiedWriter = c;
/*      */   }
/*      */ 
/*      */   public StringTemplateWriter getStringTemplateWriter(Writer w)
/*      */   {
/*  855 */     StringTemplateWriter stw = null;
/*  856 */     if (this.userSpecifiedWriter != null) {
/*      */       try {
/*  858 */         Constructor ctor = this.userSpecifiedWriter.getConstructor(new Class[] { Writer.class });
/*      */ 
/*  860 */         stw = (StringTemplateWriter)ctor.newInstance(new Object[] { w });
/*      */       }
/*      */       catch (Exception e) {
/*  863 */         error("problems getting StringTemplateWriter", e);
/*      */       }
/*      */     }
/*  866 */     if (stw == null) {
/*  867 */       stw = new AutoIndentWriter(w);
/*      */     }
/*  869 */     return stw;
/*      */   }
/*      */ 
/*      */   public void setAttributeRenderers(Map renderers)
/*      */   {
/*  877 */     this.attributeRenderers = renderers;
/*      */   }
/*      */ 
/*      */   public void registerRenderer(Class attributeClassType, Object renderer)
/*      */   {
/*  884 */     if (this.attributeRenderers == null) {
/*  885 */       this.attributeRenderers = Collections.synchronizedMap(new HashMap());
/*      */     }
/*  887 */     this.attributeRenderers.put(attributeClassType, renderer);
/*      */   }
/*      */ 
/*      */   public AttributeRenderer getAttributeRenderer(Class attributeClassType)
/*      */   {
/*  894 */     if (this.attributeRenderers == null) {
/*  895 */       if (this.superGroup == null) {
/*  896 */         return null;
/*      */       }
/*      */ 
/*  899 */       return this.superGroup.getAttributeRenderer(attributeClassType);
/*      */     }
/*      */ 
/*  902 */     AttributeRenderer renderer = (AttributeRenderer)this.attributeRenderers.get(attributeClassType);
/*      */ 
/*  904 */     if ((renderer == null) && 
/*  905 */       (this.superGroup != null))
/*      */     {
/*  907 */       renderer = this.superGroup.getAttributeRenderer(attributeClassType);
/*      */     }
/*      */ 
/*  910 */     return renderer;
/*      */   }
/*      */ 
/*      */   public Map getMap(String name)
/*      */   {
/*  926 */     if (this.maps == null) {
/*  927 */       if (this.superGroup == null) {
/*  928 */         return null;
/*      */       }
/*  930 */       return this.superGroup.getMap(name);
/*      */     }
/*  932 */     Map m = (Map)this.maps.get(name);
/*  933 */     if ((m == null) && (this.superGroup != null)) {
/*  934 */       m = this.superGroup.getMap(name);
/*      */     }
/*  936 */     return m;
/*      */   }
/*      */ 
/*      */   public void defineMap(String name, Map mapping)
/*      */   {
/*  943 */     this.maps.put(name, mapping);
/*      */   }
/*      */ 
/*      */   public static void registerDefaultLexer(Class lexerClass) {
/*  947 */     defaultTemplateLexerClass = lexerClass;
/*      */   }
/*      */ 
/*      */   public static void registerGroupLoader(StringTemplateGroupLoader loader) {
/*  951 */     groupLoader = loader;
/*      */   }
/*      */ 
/*      */   public static StringTemplateGroup loadGroup(String name) {
/*  955 */     return loadGroup(name, null, null);
/*      */   }
/*      */ 
/*      */   public static StringTemplateGroup loadGroup(String name, StringTemplateGroup superGroup)
/*      */   {
/*  961 */     return loadGroup(name, null, superGroup);
/*      */   }
/*      */ 
/*      */   public static StringTemplateGroup loadGroup(String name, Class lexer, StringTemplateGroup superGroup)
/*      */   {
/*  968 */     if (groupLoader != null) {
/*  969 */       return groupLoader.loadGroup(name, lexer, superGroup);
/*      */     }
/*  971 */     return null;
/*      */   }
/*      */ 
/*      */   public static StringTemplateGroupInterface loadInterface(String name) {
/*  975 */     if (groupLoader != null) {
/*  976 */       return groupLoader.loadInterface(name);
/*      */     }
/*  978 */     return null;
/*      */   }
/*      */ 
/*      */   public void error(String msg) {
/*  982 */     error(msg, null);
/*      */   }
/*      */ 
/*      */   public void error(String msg, Exception e) {
/*  986 */     if (this.listener != null) {
/*  987 */       this.listener.error(msg, e);
/*      */     }
/*      */     else {
/*  990 */       System.err.println("StringTemplate: " + msg);
/*  991 */       if (e != null)
/*  992 */         e.printStackTrace();
/*      */     }
/*      */   }
/*      */ 
/*      */   public synchronized Set getTemplateNames()
/*      */   {
/*  998 */     return this.templates.keySet();
/*      */   }
/*      */ 
/*      */   public void emitDebugStartStopStrings(boolean emit)
/*      */   {
/* 1005 */     this.debugTemplateOutput = emit;
/*      */   }
/*      */ 
/*      */   public void doNotEmitDebugStringsForTemplate(String templateName) {
/* 1009 */     if (this.noDebugStartStopStrings == null) {
/* 1010 */       this.noDebugStartStopStrings = new HashSet();
/*      */     }
/* 1012 */     this.noDebugStartStopStrings.add(templateName);
/*      */   }
/*      */ 
/*      */   public void emitTemplateStartDebugString(StringTemplate st, StringTemplateWriter out)
/*      */     throws IOException
/*      */   {
/* 1019 */     if ((this.noDebugStartStopStrings == null) || (!this.noDebugStartStopStrings.contains(st.getName())))
/*      */     {
/* 1022 */       String groupPrefix = "";
/* 1023 */       if ((!st.getName().startsWith("if")) && (!st.getName().startsWith("else"))) {
/* 1024 */         if (st.getNativeGroup() != null) {
/* 1025 */           groupPrefix = st.getNativeGroup().getName() + ".";
/*      */         }
/*      */         else {
/* 1028 */           groupPrefix = st.getGroup().getName() + ".";
/*      */         }
/*      */       }
/* 1031 */       out.write("<" + groupPrefix + st.getName() + ">");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void emitTemplateStopDebugString(StringTemplate st, StringTemplateWriter out)
/*      */     throws IOException
/*      */   {
/* 1039 */     if ((this.noDebugStartStopStrings == null) || (!this.noDebugStartStopStrings.contains(st.getName())))
/*      */     {
/* 1042 */       String groupPrefix = "";
/* 1043 */       if ((!st.getName().startsWith("if")) && (!st.getName().startsWith("else"))) {
/* 1044 */         if (st.getNativeGroup() != null) {
/* 1045 */           groupPrefix = st.getNativeGroup().getName() + ".";
/*      */         }
/*      */         else {
/* 1048 */           groupPrefix = st.getGroup().getName() + ".";
/*      */         }
/*      */       }
/* 1051 */       out.write("</" + groupPrefix + st.getName() + ">");
/*      */     }
/*      */   }
/*      */ 
/*      */   public String toString() {
/* 1056 */     return toString(true);
/*      */   }
/*      */ 
/*      */   public String toString(boolean showTemplatePatterns) {
/* 1060 */     StringBuffer buf = new StringBuffer();
/* 1061 */     Set templateNameSet = this.templates.keySet();
/* 1062 */     List sortedNames = new ArrayList(templateNameSet);
/* 1063 */     Collections.sort(sortedNames);
/* 1064 */     Iterator iter = sortedNames.iterator();
/* 1065 */     buf.append("group " + getName() + ";\n");
/* 1066 */     StringTemplate formalArgs = new StringTemplate("$args;separator=\",\"$");
/* 1067 */     while (iter.hasNext()) {
/* 1068 */       String tname = (String)iter.next();
/* 1069 */       StringTemplate st = (StringTemplate)this.templates.get(tname);
/* 1070 */       if (st != NOT_FOUND_ST) {
/* 1071 */         formalArgs = formalArgs.getInstanceOf();
/* 1072 */         formalArgs.setAttribute("args", st.getFormalArguments());
/* 1073 */         buf.append(tname + "(" + formalArgs + ")");
/* 1074 */         if (showTemplatePatterns) {
/* 1075 */           buf.append(" ::= <<");
/* 1076 */           buf.append(st.getTemplate());
/* 1077 */           buf.append(">>\n");
/*      */         }
/*      */         else {
/* 1080 */           buf.append('\n');
/*      */         }
/*      */       }
/*      */     }
/* 1084 */     return buf.toString();
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.stringtemplate.StringTemplateGroup
 * JD-Core Version:    0.6.2
 */