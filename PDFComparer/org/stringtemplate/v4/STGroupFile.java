/*     */ package org.stringtemplate.v4;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.util.Map;
/*     */ import org.stringtemplate.v4.compiler.CompiledST;
/*     */ import org.stringtemplate.v4.compiler.STException;
/*     */ import org.stringtemplate.v4.misc.ErrorManager;
/*     */ import org.stringtemplate.v4.misc.ErrorType;
/*     */ import org.stringtemplate.v4.misc.Misc;
/*     */ 
/*     */ public class STGroupFile extends STGroup
/*     */ {
/*     */   public String fileName;
/*     */   public URL url;
/*  45 */   protected boolean alreadyLoaded = false;
/*     */ 
/*     */   public STGroupFile(String fileName) {
/*  48 */     this(fileName, '<', '>');
/*     */   }
/*     */   public STGroupFile(String fileName, char delimiterStartChar, char delimiterStopChar) {
/*  51 */     super(delimiterStartChar, delimiterStopChar);
/*  52 */     if (!fileName.endsWith(".stg")) {
/*  53 */       throw new IllegalArgumentException("Group file names must end in .stg: " + fileName);
/*     */     }
/*     */ 
/*  56 */     File f = new File(fileName);
/*  57 */     if (f.exists()) {
/*     */       try {
/*  59 */         this.url = f.toURI().toURL();
/*     */       }
/*     */       catch (MalformedURLException e) {
/*  62 */         throw new STException("can't load group file " + fileName, e);
/*     */       }
/*  64 */       if (verbose) System.out.println("STGroupFile(" + fileName + ") == file " + f.getAbsolutePath()); 
/*     */     }
/*     */     else
/*     */     {
/*  67 */       this.url = getURL(fileName);
/*  68 */       if (this.url == null) {
/*  69 */         throw new IllegalArgumentException("No such group file: " + fileName);
/*     */       }
/*     */ 
/*  72 */       if (verbose) System.out.println("STGroupFile(" + fileName + ") == url " + this.url);
/*     */     }
/*  74 */     this.fileName = fileName;
/*     */   }
/*     */ 
/*     */   public STGroupFile(String fullyQualifiedFileName, String encoding) {
/*  78 */     this(fullyQualifiedFileName, encoding, '<', '>');
/*     */   }
/*     */ 
/*     */   public STGroupFile(String fullyQualifiedFileName, String encoding, char delimiterStartChar, char delimiterStopChar)
/*     */   {
/*  84 */     this(fullyQualifiedFileName, delimiterStartChar, delimiterStopChar);
/*  85 */     this.encoding = encoding;
/*     */   }
/*     */ 
/*     */   public STGroupFile(URL url, String encoding, char delimiterStartChar, char delimiterStopChar)
/*     */   {
/*  91 */     super(delimiterStartChar, delimiterStopChar);
/*  92 */     this.url = url;
/*  93 */     this.encoding = encoding;
/*     */     try
/*     */     {
/*  98 */       String urlString = url.toString();
/*  99 */       if (urlString.startsWith("jar:file:")) {
/* 100 */         urlString = urlString.substring(4);
/*     */       }
/* 102 */       this.fileName = new File(new URI(urlString)).getAbsolutePath();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isDictionary(String name) {
/* 110 */     if (!this.alreadyLoaded) load();
/* 111 */     return super.isDictionary(name);
/*     */   }
/*     */ 
/*     */   public boolean isDefined(String name) {
/* 115 */     if (!this.alreadyLoaded) load();
/* 116 */     return super.isDefined(name);
/*     */   }
/*     */ 
/*     */   public synchronized void unload()
/*     */   {
/* 121 */     super.unload();
/* 122 */     this.alreadyLoaded = false;
/*     */   }
/*     */ 
/*     */   protected CompiledST load(String name) {
/* 126 */     if (!this.alreadyLoaded) load();
/* 127 */     return rawGetTemplate(name);
/*     */   }
/*     */ 
/*     */   public void load() {
/* 131 */     if (this.alreadyLoaded) return;
/* 132 */     this.alreadyLoaded = true;
/*     */ 
/* 135 */     if (verbose) System.out.println("loading group file " + this.url.toString());
/* 136 */     loadGroupFile("/", this.url.toString());
/* 137 */     if (verbose) System.out.println("found " + this.templates.size() + " templates in " + this.url.toString() + " = " + this.templates.keySet()); 
/*     */   }
/*     */ 
/*     */   public String show()
/*     */   {
/* 141 */     if (!this.alreadyLoaded) load();
/* 142 */     return super.show();
/*     */   }
/*     */   public String getName() {
/* 145 */     return Misc.getFileNameNoSuffix(this.fileName); } 
/* 146 */   public String getFileName() { return this.fileName; }
/*     */ 
/*     */ 
/*     */   public URL getRootDirURL()
/*     */   {
/* 151 */     String parent = Misc.stripLastPathElement(this.url.toString());
/*     */     try {
/* 153 */       return new URL(parent);
/*     */     }
/*     */     catch (MalformedURLException mue) {
/* 156 */       this.errMgr.runTimeError(null, null, 0, ErrorType.INVALID_TEMPLATE_NAME, mue, parent);
/*     */     }
/*     */ 
/* 159 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.STGroupFile
 * JD-Core Version:    0.6.2
 */