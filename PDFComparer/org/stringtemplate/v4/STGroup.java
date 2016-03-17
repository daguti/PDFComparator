/*     */ package org.stringtemplate.v4;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import org.antlr.runtime.ANTLRFileStream;
/*     */ import org.antlr.runtime.ANTLRInputStream;
/*     */ import org.antlr.runtime.CharStream;
/*     */ import org.antlr.runtime.CommonToken;
/*     */ import org.antlr.runtime.CommonTokenStream;
/*     */ import org.antlr.runtime.RecognitionException;
/*     */ import org.antlr.runtime.Token;
/*     */ import org.stringtemplate.v4.compiler.CompiledST;
/*     */ import org.stringtemplate.v4.compiler.Compiler;
/*     */ import org.stringtemplate.v4.compiler.FormalArgument;
/*     */ import org.stringtemplate.v4.compiler.GroupLexer;
/*     */ import org.stringtemplate.v4.compiler.GroupParser;
/*     */ import org.stringtemplate.v4.compiler.STException;
/*     */ import org.stringtemplate.v4.misc.Aggregate;
/*     */ import org.stringtemplate.v4.misc.AggregateModelAdaptor;
/*     */ import org.stringtemplate.v4.misc.ErrorManager;
/*     */ import org.stringtemplate.v4.misc.ErrorType;
/*     */ import org.stringtemplate.v4.misc.MapModelAdaptor;
/*     */ import org.stringtemplate.v4.misc.Misc;
/*     */ import org.stringtemplate.v4.misc.ObjectModelAdaptor;
/*     */ import org.stringtemplate.v4.misc.STModelAdaptor;
/*     */ 
/*     */ public class STGroup
/*     */ {
/*     */   public static final String DICT_KEY = "key";
/*     */   public static final String DEFAULT_KEY = "default";
/*  51 */   public String encoding = "UTF-8";
/*     */ 
/*  56 */   protected final List<STGroup> imports = Collections.synchronizedList(new ArrayList());
/*     */ 
/*  58 */   protected final List<STGroup> importsToClearOnUnload = Collections.synchronizedList(new ArrayList());
/*     */ 
/*  60 */   public char delimiterStartChar = '<';
/*  61 */   public char delimiterStopChar = '>';
/*     */ 
/*  64 */   protected Map<String, CompiledST> templates = Collections.synchronizedMap(new LinkedHashMap());
/*     */ 
/*  70 */   protected Map<String, Map<String, Object>> dictionaries = Collections.synchronizedMap(new HashMap());
/*     */   protected Map<Class, AttributeRenderer> renderers;
/* 104 */   protected Map<Class, ModelAdaptor> adaptors = Collections.synchronizedMap(new LinkedHashMap()
/*     */   {
/*     */   });
/*     */ 
/* 115 */   protected Map<Class, ModelAdaptor> typeToAdaptorCache = Collections.synchronizedMap(new LinkedHashMap());
/*     */   protected Map<Class, AttributeRenderer> typeToRendererCache;
/* 124 */   protected static final CompiledST NOT_FOUND_ST = new CompiledST();
/*     */ 
/* 126 */   public static final ErrorManager DEFAULT_ERR_MGR = new ErrorManager();
/*     */ 
/* 129 */   public static boolean verbose = false;
/*     */ 
/* 134 */   public static boolean trackCreationEvents = false;
/*     */ 
/* 140 */   public boolean iterateAcrossValues = false;
/*     */ 
/* 142 */   public static STGroup defaultGroup = new STGroup();
/*     */ 
/* 147 */   public ErrorManager errMgr = DEFAULT_ERR_MGR;
/*     */ 
/*     */   public STGroup() {
/*     */   }
/*     */   public STGroup(char delimiterStartChar, char delimiterStopChar) {
/* 152 */     this.delimiterStartChar = delimiterStartChar;
/* 153 */     this.delimiterStopChar = delimiterStopChar;
/*     */   }
/*     */ 
/*     */   public ST getInstanceOf(String name)
/*     */   {
/* 160 */     if (name == null) return null;
/* 161 */     if (verbose) System.out.println(getName() + ".getInstanceOf(" + name + ")");
/* 162 */     if (name.charAt(0) != '/') name = "/" + name;
/* 163 */     CompiledST c = lookupTemplate(name);
/* 164 */     if (c != null) {
/* 165 */       return createStringTemplate(c);
/*     */     }
/* 167 */     return null;
/*     */   }
/*     */ 
/*     */   protected ST getEmbeddedInstanceOf(Interpreter interp, ST enclosingInstance, int ip, String name)
/*     */   {
/* 175 */     String fullyQualifiedName = name;
/* 176 */     if (name.charAt(0) != '/') {
/* 177 */       fullyQualifiedName = enclosingInstance.impl.prefix + name;
/*     */     }
/* 179 */     if (verbose) System.out.println("getEmbeddedInstanceOf(" + fullyQualifiedName + ")");
/* 180 */     ST st = getInstanceOf(fullyQualifiedName);
/* 181 */     if (st == null) {
/* 182 */       this.errMgr.runTimeError(interp, enclosingInstance, ip, ErrorType.NO_SUCH_TEMPLATE, fullyQualifiedName);
/*     */ 
/* 185 */       return createStringTemplateInternally(new CompiledST());
/*     */     }
/*     */ 
/* 188 */     if (trackCreationEvents) {
/* 189 */       st.debugState.newSTEvent = null;
/*     */     }
/* 191 */     return st;
/*     */   }
/*     */ 
/*     */   public ST createSingleton(Token templateToken)
/*     */   {
/*     */     String template;
/*     */     String template;
/* 197 */     if (templateToken.getType() == 5) {
/* 198 */       template = Misc.strip(templateToken.getText(), 2);
/*     */     }
/*     */     else {
/* 201 */       template = Misc.strip(templateToken.getText(), 1);
/*     */     }
/* 203 */     CompiledST impl = compile(getFileName(), null, null, template, templateToken);
/* 204 */     ST st = createStringTemplateInternally(impl);
/* 205 */     st.groupThatCreatedThisInstance = this;
/* 206 */     st.impl.hasFormalArgs = false;
/* 207 */     st.impl.name = "anonymous";
/* 208 */     st.impl.defineImplicitlyDefinedTemplates(this);
/* 209 */     return st;
/*     */   }
/*     */ 
/*     */   public boolean isDefined(String name)
/*     */   {
/* 216 */     return lookupTemplate(name) != null;
/*     */   }
/*     */ 
/*     */   public CompiledST lookupTemplate(String name)
/*     */   {
/* 221 */     if (name.charAt(0) != '/') name = "/" + name;
/* 222 */     if (verbose) System.out.println(getName() + ".lookupTemplate(" + name + ")");
/* 223 */     CompiledST code = rawGetTemplate(name);
/* 224 */     if (code == NOT_FOUND_ST) {
/* 225 */       if (verbose) System.out.println(name + " previously seen as not found");
/* 226 */       return null;
/*     */     }
/*     */ 
/* 229 */     if (code == null) code = load(name);
/* 230 */     if (code == null) code = lookupImportedTemplate(name);
/* 231 */     if (code == null) {
/* 232 */       if (verbose) System.out.println(name + " recorded not found");
/* 233 */       this.templates.put(name, NOT_FOUND_ST);
/*     */     }
/* 235 */     if ((verbose) && (code != null)) System.out.println(getName() + ".lookupTemplate(" + name + ") found");
/* 236 */     return code;
/*     */   }
/*     */ 
/*     */   public synchronized void unload()
/*     */   {
/* 246 */     this.templates.clear();
/* 247 */     this.dictionaries.clear();
/* 248 */     for (STGroup imp : this.imports) {
/* 249 */       imp.unload();
/*     */     }
/* 251 */     for (STGroup imp : this.importsToClearOnUnload) {
/* 252 */       this.imports.remove(imp);
/*     */     }
/* 254 */     this.importsToClearOnUnload.clear();
/*     */   }
/*     */ 
/*     */   protected CompiledST load(String name)
/*     */   {
/* 260 */     return null;
/*     */   }
/*     */   public void load() {
/*     */   }
/*     */ 
/*     */   protected CompiledST lookupImportedTemplate(String name) {
/* 266 */     if (this.imports.size() == 0) return null;
/* 267 */     for (STGroup g : this.imports) {
/* 268 */       if (verbose) System.out.println("checking " + g.getName() + " for imported " + name);
/* 269 */       CompiledST code = g.lookupTemplate(name);
/* 270 */       if (code != null) {
/* 271 */         if (verbose) System.out.println(g.getName() + ".lookupImportedTemplate(" + name + ") found");
/* 272 */         return code;
/*     */       }
/*     */     }
/* 275 */     if (verbose) System.out.println(name + " not found in " + getName() + " imports");
/* 276 */     return null;
/*     */   }
/*     */   public CompiledST rawGetTemplate(String name) {
/* 279 */     return (CompiledST)this.templates.get(name); } 
/* 280 */   public Map<String, Object> rawGetDictionary(String name) { return (Map)this.dictionaries.get(name); } 
/* 281 */   public boolean isDictionary(String name) { return this.dictionaries.get(name) != null; }
/*     */ 
/*     */   public CompiledST defineTemplate(String templateName, String template)
/*     */   {
/* 285 */     if (templateName.charAt(0) != '/') templateName = "/" + templateName; try
/*     */     {
/* 287 */       return defineTemplate(templateName, new CommonToken(9, templateName), null, template, null);
/*     */     }
/*     */     catch (STException se)
/*     */     {
/*     */     }
/*     */ 
/* 297 */     return null;
/*     */   }
/*     */ 
/*     */   public CompiledST defineTemplate(String name, String argsS, String template)
/*     */   {
/* 302 */     if (name.charAt(0) != '/') name = "/" + name;
/* 303 */     String[] args = argsS.split(",");
/* 304 */     List a = new ArrayList();
/* 305 */     for (String arg : args) {
/* 306 */       a.add(new FormalArgument(arg));
/*     */     }
/* 308 */     return defineTemplate(name, new CommonToken(9, name), a, template, null);
/*     */   }
/*     */ 
/*     */   public CompiledST defineTemplate(String fullyQualifiedTemplateName, Token nameT, List<FormalArgument> args, String template, Token templateToken)
/*     */   {
/* 318 */     if (verbose) System.out.println("defineTemplate(" + fullyQualifiedTemplateName + ")");
/* 319 */     if ((fullyQualifiedTemplateName == null) || (fullyQualifiedTemplateName.length() == 0)) {
/* 320 */       throw new IllegalArgumentException("empty template name");
/*     */     }
/* 322 */     if (fullyQualifiedTemplateName.indexOf('.') >= 0) {
/* 323 */       throw new IllegalArgumentException("cannot have '.' in template names");
/*     */     }
/* 325 */     template = Misc.trimOneStartingNewline(template);
/* 326 */     template = Misc.trimOneTrailingNewline(template);
/*     */ 
/* 328 */     CompiledST code = compile(getFileName(), fullyQualifiedTemplateName, args, template, templateToken);
/* 329 */     code.name = fullyQualifiedTemplateName;
/* 330 */     rawDefineTemplate(fullyQualifiedTemplateName, code, nameT);
/* 331 */     code.defineArgDefaultValueTemplates(this);
/* 332 */     code.defineImplicitlyDefinedTemplates(this);
/*     */ 
/* 334 */     return code;
/*     */   }
/*     */ 
/*     */   public CompiledST defineTemplateAlias(Token aliasT, Token targetT)
/*     */   {
/* 339 */     String alias = aliasT.getText();
/* 340 */     String target = targetT.getText();
/* 341 */     CompiledST targetCode = rawGetTemplate("/" + target);
/* 342 */     if (targetCode == null) {
/* 343 */       this.errMgr.compileTimeError(ErrorType.ALIAS_TARGET_UNDEFINED, null, aliasT, alias, target);
/* 344 */       return null;
/*     */     }
/* 346 */     rawDefineTemplate("/" + alias, targetCode, aliasT);
/* 347 */     return targetCode;
/*     */   }
/*     */ 
/*     */   public CompiledST defineRegion(String enclosingTemplateName, Token regionT, String template, Token templateToken)
/*     */   {
/* 355 */     String name = regionT.getText();
/* 356 */     template = Misc.trimOneStartingNewline(template);
/* 357 */     template = Misc.trimOneTrailingNewline(template);
/* 358 */     CompiledST code = compile(getFileName(), enclosingTemplateName, null, template, templateToken);
/* 359 */     String mangled = getMangledRegionName(enclosingTemplateName, name);
/*     */ 
/* 361 */     if (lookupTemplate(mangled) == null) {
/* 362 */       this.errMgr.compileTimeError(ErrorType.NO_SUCH_REGION, templateToken, regionT, enclosingTemplateName, name);
/*     */ 
/* 364 */       return new CompiledST();
/*     */     }
/* 366 */     code.name = mangled;
/* 367 */     code.isRegion = true;
/* 368 */     code.regionDefType = ST.RegionType.EXPLICIT;
/* 369 */     code.templateDefStartToken = regionT;
/*     */ 
/* 371 */     rawDefineTemplate(mangled, code, regionT);
/* 372 */     code.defineArgDefaultValueTemplates(this);
/* 373 */     code.defineImplicitlyDefinedTemplates(this);
/*     */ 
/* 375 */     return code;
/*     */   }
/*     */ 
/*     */   public void defineTemplateOrRegion(String fullyQualifiedTemplateName, String regionSurroundingTemplateName, Token templateToken, String template, Token nameToken, List<FormalArgument> args)
/*     */   {
/*     */     try
/*     */     {
/* 387 */       if (regionSurroundingTemplateName != null) {
/* 388 */         defineRegion(regionSurroundingTemplateName, nameToken, template, templateToken);
/*     */       }
/*     */       else
/* 391 */         defineTemplate(fullyQualifiedTemplateName, nameToken, args, template, templateToken);
/*     */     }
/*     */     catch (STException e)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public void rawDefineTemplate(String name, CompiledST code, Token defT)
/*     */   {
/* 401 */     CompiledST prev = rawGetTemplate(name);
/* 402 */     if (prev != null) {
/* 403 */       if (!prev.isRegion) {
/* 404 */         this.errMgr.compileTimeError(ErrorType.TEMPLATE_REDEFINITION, null, defT);
/* 405 */         return;
/*     */       }
/* 407 */       if (prev.isRegion) {
/* 408 */         if ((code.regionDefType != ST.RegionType.IMPLICIT) && (prev.regionDefType == ST.RegionType.EMBEDDED))
/*     */         {
/* 411 */           this.errMgr.compileTimeError(ErrorType.EMBEDDED_REGION_REDEFINITION, null, defT, getUnMangledTemplateName(name));
/*     */ 
/* 415 */           return;
/*     */         }
/* 417 */         if ((code.regionDefType == ST.RegionType.IMPLICIT) || (prev.regionDefType == ST.RegionType.EXPLICIT))
/*     */         {
/* 420 */           this.errMgr.compileTimeError(ErrorType.REGION_REDEFINITION, null, defT, getUnMangledTemplateName(name));
/*     */ 
/* 424 */           return;
/*     */         }
/*     */       }
/*     */     }
/* 428 */     code.nativeGroup = this;
/* 429 */     code.templateDefStartToken = defT;
/* 430 */     this.templates.put(name, code);
/*     */   }
/*     */ 
/*     */   public void undefineTemplate(String name) {
/* 434 */     this.templates.remove(name);
/*     */   }
/*     */ 
/*     */   public CompiledST compile(String srcName, String name, List<FormalArgument> args, String template, Token templateToken)
/*     */   {
/* 445 */     Compiler c = new Compiler(this);
/* 446 */     return c.compile(srcName, name, args, template, templateToken);
/*     */   }
/*     */ 
/*     */   public static String getMangledRegionName(String enclosingTemplateName, String name)
/*     */   {
/* 453 */     if (enclosingTemplateName.charAt(0) != '/') {
/* 454 */       enclosingTemplateName = '/' + enclosingTemplateName;
/*     */     }
/* 456 */     return "/region__" + enclosingTemplateName + "__" + name;
/*     */   }
/*     */ 
/*     */   public static String getUnMangledTemplateName(String mangledName)
/*     */   {
/* 461 */     String t = mangledName.substring("/region__".length(), mangledName.lastIndexOf("__"));
/*     */ 
/* 463 */     String r = mangledName.substring(mangledName.lastIndexOf("__") + 2, mangledName.length());
/*     */ 
/* 465 */     return t + '.' + r;
/*     */   }
/*     */ 
/*     */   public void defineDictionary(String name, Map<String, Object> mapping)
/*     */   {
/* 472 */     this.dictionaries.put(name, mapping);
/*     */   }
/*     */ 
/*     */   public void importTemplates(STGroup g)
/*     */   {
/* 481 */     importTemplates(g, false);
/*     */   }
/*     */ 
/*     */   public void importTemplates(Token fileNameToken)
/*     */   {
/* 504 */     if (verbose) System.out.println("importTemplates(" + fileNameToken.getText() + ")");
/* 505 */     String fileName = fileNameToken.getText();
/*     */ 
/* 507 */     if ((fileName == null) || (fileName.equals("<missing STRING>"))) return;
/* 508 */     fileName = Misc.strip(fileName, 1);
/*     */ 
/* 511 */     boolean isGroupFile = fileName.endsWith(".stg");
/* 512 */     boolean isTemplateFile = fileName.endsWith(".st");
/* 513 */     boolean isGroupDir = (!isGroupFile) && (!isTemplateFile);
/*     */ 
/* 515 */     STGroup g = null;
/*     */ 
/* 518 */     URL thisRoot = getRootDirURL();
/* 519 */     URL fileUnderRoot = null;
/*     */     try
/*     */     {
/* 522 */       fileUnderRoot = new URL(thisRoot + "/" + fileName);
/*     */     }
/*     */     catch (MalformedURLException mfe) {
/* 525 */       this.errMgr.internalError(null, "can't build URL for " + thisRoot + "/" + fileName, mfe);
/* 526 */       return;
/*     */     }
/* 528 */     if (isTemplateFile) {
/* 529 */       g = new STGroup();
/* 530 */       g.setListener(getListener());
/*     */       URL fileURL;
/*     */       URL fileURL;
/* 532 */       if (Misc.urlExists(fileUnderRoot)) fileURL = fileUnderRoot; else
/* 533 */         fileURL = getURL(fileName);
/* 534 */       if (fileURL != null) {
/*     */         try {
/* 536 */           InputStream s = fileURL.openStream();
/* 537 */           ANTLRInputStream templateStream = new ANTLRInputStream(s);
/* 538 */           templateStream.name = fileName;
/* 539 */           CompiledST code = g.loadTemplateFile("/", fileName, templateStream);
/* 540 */           if (code == null) g = null; 
/*     */         }
/*     */         catch (IOException ioe)
/*     */         {
/* 543 */           this.errMgr.internalError(null, "can't read from " + fileURL, ioe);
/* 544 */           g = null;
/*     */         }
/*     */       }
/*     */       else {
/* 548 */         g = null;
/*     */       }
/*     */     }
/* 551 */     else if (isGroupFile)
/*     */     {
/* 553 */       if (Misc.urlExists(fileUnderRoot)) {
/* 554 */         g = new STGroupFile(fileUnderRoot, this.encoding, this.delimiterStartChar, this.delimiterStopChar);
/* 555 */         g.setListener(getListener());
/*     */       }
/*     */       else {
/* 558 */         g = new STGroupFile(fileName, this.delimiterStartChar, this.delimiterStopChar);
/* 559 */         g.setListener(getListener());
/*     */       }
/*     */     }
/* 562 */     else if (isGroupDir)
/*     */     {
/* 564 */       if (Misc.urlExists(fileUnderRoot)) {
/* 565 */         g = new STGroupDir(fileUnderRoot, this.encoding, this.delimiterStartChar, this.delimiterStopChar);
/* 566 */         g.setListener(getListener());
/*     */       }
/*     */       else
/*     */       {
/* 571 */         g = new STGroupDir(fileName, this.delimiterStartChar, this.delimiterStopChar);
/* 572 */         g.setListener(getListener());
/*     */       }
/*     */     }
/*     */ 
/* 576 */     if (g == null) {
/* 577 */       this.errMgr.compileTimeError(ErrorType.CANT_IMPORT, null, fileNameToken, fileName);
/*     */     }
/*     */     else
/*     */     {
/* 581 */       importTemplates(g, true);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void importTemplates(STGroup g, boolean clearOnUnload) {
/* 586 */     if (g == null) return;
/* 587 */     this.imports.add(g);
/* 588 */     if (clearOnUnload)
/* 589 */       this.importsToClearOnUnload.add(g);
/*     */   }
/*     */ 
/*     */   public List<STGroup> getImportedGroups() {
/* 593 */     return this.imports;
/*     */   }
/*     */ 
/*     */   public void loadGroupFile(String prefix, String fileName) {
/* 597 */     if (verbose) System.out.println(getClass().getSimpleName() + ".loadGroupFile(group-file-prefix=" + prefix + ", fileName=" + fileName + ")");
/*     */ 
/* 599 */     GroupParser parser = null;
/*     */     try {
/* 601 */       URL f = new URL(fileName);
/* 602 */       ANTLRInputStream fs = new ANTLRInputStream(f.openStream(), this.encoding);
/* 603 */       GroupLexer lexer = new GroupLexer(fs);
/* 604 */       fs.name = fileName;
/* 605 */       CommonTokenStream tokens = new CommonTokenStream(lexer);
/* 606 */       parser = new GroupParser(tokens);
/* 607 */       parser.group(this, prefix);
/*     */     }
/*     */     catch (Exception e) {
/* 610 */       this.errMgr.IOError(null, ErrorType.CANT_LOAD_GROUP_FILE, e, fileName);
/*     */     }
/*     */   }
/*     */ 
/*     */   public CompiledST loadAbsoluteTemplateFile(String fileName)
/*     */   {
/*     */     ANTLRFileStream fs;
/*     */     try {
/* 618 */       fs = new ANTLRFileStream(fileName, this.encoding);
/* 619 */       fs.name = fileName;
/*     */     }
/*     */     catch (IOException ioe)
/*     */     {
/* 624 */       return null;
/*     */     }
/* 626 */     return loadTemplateFile("", fileName, fs);
/*     */   }
/*     */ 
/*     */   public CompiledST loadTemplateFile(String prefix, String unqualifiedFileName, CharStream templateStream)
/*     */   {
/* 634 */     GroupLexer lexer = new GroupLexer(templateStream);
/* 635 */     CommonTokenStream tokens = new CommonTokenStream(lexer);
/* 636 */     GroupParser parser = new GroupParser(tokens);
/* 637 */     parser.group = this;
/* 638 */     lexer.group = this;
/*     */     try {
/* 640 */       parser.templateDef(prefix);
/*     */     }
/*     */     catch (RecognitionException re) {
/* 643 */       this.errMgr.groupSyntaxError(ErrorType.SYNTAX_ERROR, unqualifiedFileName, re, re.getMessage());
/*     */     }
/*     */ 
/* 647 */     String templateName = Misc.getFileNameNoSuffix(unqualifiedFileName);
/* 648 */     if ((prefix != null) && (prefix.length() > 0)) templateName = prefix + templateName;
/* 649 */     CompiledST impl = rawGetTemplate(templateName);
/* 650 */     impl.prefix = prefix;
/* 651 */     return impl;
/*     */   }
/*     */ 
/*     */   public void registerModelAdaptor(Class attributeType, ModelAdaptor adaptor)
/*     */   {
/* 665 */     if (attributeType.isPrimitive()) {
/* 666 */       throw new IllegalArgumentException("can't register ModelAdaptor for primitive type " + attributeType.getSimpleName());
/*     */     }
/*     */ 
/* 669 */     this.adaptors.put(attributeType, adaptor);
/* 670 */     invalidateModelAdaptorCache(attributeType);
/*     */   }
/*     */ 
/*     */   public void invalidateModelAdaptorCache(Class attributeType)
/*     */   {
/* 675 */     this.typeToAdaptorCache.clear();
/*     */   }
/*     */ 
/*     */   public ModelAdaptor getModelAdaptor(Class attributeType) {
/* 679 */     ModelAdaptor a = (ModelAdaptor)this.typeToAdaptorCache.get(attributeType);
/* 680 */     if (a != null) return a;
/*     */ 
/* 685 */     for (Class t : this.adaptors.keySet())
/*     */     {
/* 688 */       if (t.isAssignableFrom(attributeType))
/*     */       {
/* 690 */         a = (ModelAdaptor)this.adaptors.get(t);
/*     */       }
/*     */     }
/*     */ 
/* 694 */     this.typeToAdaptorCache.put(attributeType, a);
/* 695 */     return a;
/*     */   }
/*     */ 
/*     */   public void registerRenderer(Class attributeType, AttributeRenderer r)
/*     */   {
/* 704 */     registerRenderer(attributeType, r, true);
/*     */   }
/*     */ 
/*     */   public void registerRenderer(Class attributeType, AttributeRenderer r, boolean recursive) {
/* 708 */     if (attributeType.isPrimitive()) {
/* 709 */       throw new IllegalArgumentException("can't register renderer for primitive type " + attributeType.getSimpleName());
/*     */     }
/*     */ 
/* 712 */     this.typeToAdaptorCache.clear();
/* 713 */     if (this.renderers == null) {
/* 714 */       this.renderers = Collections.synchronizedMap(new LinkedHashMap());
/*     */     }
/*     */ 
/* 717 */     this.renderers.put(attributeType, r);
/*     */ 
/* 719 */     if (recursive) {
/* 720 */       load();
/*     */       STGroup g;
/* 721 */       for (Iterator i$ = this.imports.iterator(); i$.hasNext(); g.registerRenderer(attributeType, r, true)) g = (STGroup)i$.next();
/*     */     }
/*     */   }
/*     */ 
/*     */   public AttributeRenderer getAttributeRenderer(Class attributeType)
/*     */   {
/* 737 */     if (this.renderers == null) return null;
/* 738 */     AttributeRenderer r = null;
/* 739 */     if (this.typeToRendererCache != null) {
/* 740 */       r = (AttributeRenderer)this.typeToRendererCache.get(attributeType);
/* 741 */       if (r != null) return r;
/*     */ 
/*     */     }
/*     */ 
/* 745 */     for (Class t : this.renderers.keySet())
/*     */     {
/* 747 */       if (t.isAssignableFrom(attributeType)) {
/* 748 */         r = (AttributeRenderer)this.renderers.get(t);
/* 749 */         if (this.typeToRendererCache == null) {
/* 750 */           this.typeToRendererCache = Collections.synchronizedMap(new LinkedHashMap());
/*     */         }
/*     */ 
/* 753 */         this.typeToRendererCache.put(attributeType, r);
/* 754 */         return r;
/*     */       }
/*     */     }
/* 757 */     return null;
/*     */   }
/*     */ 
/*     */   public ST createStringTemplate(CompiledST impl) {
/* 761 */     ST st = new ST();
/* 762 */     st.impl = impl;
/* 763 */     st.groupThatCreatedThisInstance = this;
/* 764 */     if (impl.formalArguments != null) {
/* 765 */       st.locals = new Object[impl.formalArguments.size()];
/* 766 */       Arrays.fill(st.locals, ST.EMPTY_ATTR);
/*     */     }
/* 768 */     return st;
/*     */   }
/*     */ 
/*     */   public ST createStringTemplateInternally(CompiledST impl)
/*     */   {
/* 775 */     ST st = createStringTemplate(impl);
/* 776 */     if ((trackCreationEvents) && (st.debugState != null)) {
/* 777 */       st.debugState.newSTEvent = null;
/*     */     }
/* 779 */     return st;
/*     */   }
/*     */ 
/*     */   public ST createStringTemplateInternally(ST proto) {
/* 783 */     return new ST(proto);
/*     */   }
/*     */   public String getName() {
/* 786 */     return "<no name>;"; } 
/* 787 */   public String getFileName() { return null; }
/*     */ 
/*     */ 
/*     */   public URL getRootDirURL()
/*     */   {
/* 795 */     return null;
/*     */   }
/*     */   public URL getURL(String fileName) {
/* 798 */     URL url = null;
/* 799 */     ClassLoader cl = Thread.currentThread().getContextClassLoader();
/* 800 */     url = cl.getResource(fileName);
/* 801 */     if (url == null) {
/* 802 */       cl = getClass().getClassLoader();
/* 803 */       url = cl.getResource(fileName);
/*     */     }
/* 805 */     return url;
/*     */   }
/*     */   public String toString() {
/* 808 */     return getName();
/*     */   }
/*     */   public String show() {
/* 811 */     StringBuilder buf = new StringBuilder();
/* 812 */     if (this.imports.size() != 0) buf.append(" : " + this.imports);
/* 813 */     for (String name : this.templates.keySet()) {
/* 814 */       CompiledST c = rawGetTemplate(name);
/* 815 */       if ((!c.isAnonSubtemplate) && (c != NOT_FOUND_ST)) {
/* 816 */         int slash = name.lastIndexOf('/');
/* 817 */         name = name.substring(slash + 1, name.length());
/* 818 */         buf.append(name);
/* 819 */         buf.append('(');
/* 820 */         if (c.formalArguments != null) buf.append(Misc.join(c.formalArguments.values().iterator(), ","));
/* 821 */         buf.append(')');
/* 822 */         buf.append(" ::= <<" + Misc.newline);
/* 823 */         buf.append(c.template + Misc.newline);
/* 824 */         buf.append(">>" + Misc.newline);
/*     */       }
/*     */     }
/* 826 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public STErrorListener getListener() {
/* 830 */     return this.errMgr.listener;
/*     */   }
/*     */ 
/*     */   public void setListener(STErrorListener listener) {
/* 834 */     this.errMgr = new ErrorManager(listener);
/*     */   }
/*     */ 
/*     */   public Set<String> getTemplateNames() {
/* 838 */     load();
/* 839 */     HashSet result = new HashSet();
/* 840 */     for (Map.Entry e : this.templates.entrySet()) {
/* 841 */       if (e.getValue() != NOT_FOUND_ST) {
/* 842 */         result.add(e.getKey());
/*     */       }
/*     */     }
/* 845 */     return result;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.STGroup
 * JD-Core Version:    0.6.2
 */