/*     */ package org.stringtemplate.v4;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import org.antlr.runtime.ANTLRInputStream;
/*     */ import org.antlr.runtime.Token;
/*     */ import org.stringtemplate.v4.compiler.CompiledST;
/*     */ import org.stringtemplate.v4.compiler.STException;
/*     */ import org.stringtemplate.v4.misc.ErrorManager;
/*     */ import org.stringtemplate.v4.misc.ErrorType;
/*     */ import org.stringtemplate.v4.misc.Misc;
/*     */ 
/*     */ public class STGroupDir extends STGroup
/*     */ {
/*     */   public String groupDirName;
/*     */   public URL root;
/*     */ 
/*     */   public STGroupDir(String dirName)
/*     */   {
/*  48 */     this(dirName, '<', '>');
/*     */   }
/*     */   public STGroupDir(String dirName, char delimiterStartChar, char delimiterStopChar) {
/*  51 */     super(delimiterStartChar, delimiterStopChar);
/*  52 */     this.groupDirName = dirName;
/*  53 */     File dir = new File(dirName);
/*  54 */     if ((dir.exists()) && (dir.isDirectory()))
/*     */     {
/*     */       try {
/*  57 */         this.root = dir.toURI().toURL();
/*     */       }
/*     */       catch (MalformedURLException e) {
/*  60 */         throw new STException("can't load dir " + dirName, e);
/*     */       }
/*  62 */       if (verbose) System.out.println("STGroupDir(" + dirName + ") found at " + this.root); 
/*     */     }
/*     */     else
/*     */     {
/*  65 */       ClassLoader cl = Thread.currentThread().getContextClassLoader();
/*  66 */       this.root = cl.getResource(dirName);
/*  67 */       if (this.root == null) {
/*  68 */         cl = getClass().getClassLoader();
/*  69 */         this.root = cl.getResource(dirName);
/*     */       }
/*  71 */       if (verbose) System.out.println("STGroupDir(" + dirName + ") found via CLASSPATH at " + this.root);
/*  72 */       if (this.root == null)
/*  73 */         throw new IllegalArgumentException("No such directory: " + dirName);
/*     */     }
/*     */   }
/*     */ 
/*     */   public STGroupDir(String dirName, String encoding)
/*     */   {
/*  80 */     this(dirName, encoding, '<', '>');
/*     */   }
/*     */ 
/*     */   public STGroupDir(String dirName, String encoding, char delimiterStartChar, char delimiterStopChar)
/*     */   {
/*  86 */     this(dirName, delimiterStartChar, delimiterStopChar);
/*  87 */     this.encoding = encoding;
/*     */   }
/*     */ 
/*     */   public STGroupDir(URL root, String encoding, char delimiterStartChar, char delimiterStopChar)
/*     */   {
/*  93 */     super(delimiterStartChar, delimiterStopChar);
/*  94 */     this.groupDirName = new File(root.getFile()).getName();
/*  95 */     this.root = root;
/*  96 */     this.encoding = encoding;
/*     */   }
/*     */ 
/*     */   public void importTemplates(Token fileNameToken)
/*     */   {
/* 101 */     String msg = "import illegal in group files embedded in STGroupDirs; import " + fileNameToken.getText() + " in STGroupDir " + getName();
/*     */ 
/* 104 */     throw new UnsupportedOperationException(msg);
/*     */   }
/*     */ 
/*     */   protected CompiledST load(String name)
/*     */   {
/* 112 */     if (verbose) System.out.println("STGroupDir.load(" + name + ")");
/* 113 */     String parent = Misc.getParent(name);
/* 114 */     String prefix = Misc.getPrefix(name);
/*     */ 
/* 120 */     URL groupFileURL = null;
/*     */     try {
/* 122 */       groupFileURL = new URL(this.root + parent + ".stg");
/*     */     }
/*     */     catch (MalformedURLException e) {
/* 125 */       this.errMgr.internalError(null, "bad URL: " + this.root + parent + ".stg", e);
/* 126 */       return null;
/*     */     }
/* 128 */     InputStream is = null;
/*     */     try {
/* 130 */       is = groupFileURL.openStream();
/*     */     }
/*     */     catch (FileNotFoundException fnfe)
/*     */     {
/* 134 */       String unqualifiedName = Misc.getFileName(name);
/* 135 */       return loadTemplateFile(prefix, unqualifiedName + ".st");
/*     */     }
/*     */     catch (IOException ioe) {
/* 138 */       this.errMgr.internalError(null, "can't load template file " + name, ioe);
/*     */     }
/*     */     try {
/* 141 */       if (is != null) is.close(); 
/*     */     }
/*     */     catch (IOException ioe)
/*     */     {
/* 144 */       this.errMgr.internalError(null, "can't close template file stream " + name, ioe);
/*     */     }
/* 146 */     loadGroupFile(prefix, this.root + parent + ".stg");
/* 147 */     return rawGetTemplate(name);
/*     */   }
/*     */ 
/*     */   public CompiledST loadTemplateFile(String prefix, String unqualifiedFileName)
/*     */   {
/* 152 */     if (verbose) System.out.println("loadTemplateFile(" + unqualifiedFileName + ") in groupdir " + "from " + this.root + " prefix=" + prefix);
/*     */ 
/* 154 */     URL f = null;
/*     */     try {
/* 156 */       f = new URL(this.root + prefix + unqualifiedFileName);
/*     */     }
/*     */     catch (MalformedURLException me) {
/* 159 */       this.errMgr.runTimeError(null, null, 0, ErrorType.INVALID_TEMPLATE_NAME, me, this.root + unqualifiedFileName);
/*     */ 
/* 161 */       return null;
/*     */     }
/*     */     ANTLRInputStream fs;
/*     */     try {
/* 165 */       fs = new ANTLRInputStream(f.openStream(), this.encoding);
/* 166 */       fs.name = unqualifiedFileName;
/*     */     }
/*     */     catch (IOException ioe) {
/* 169 */       if (verbose) System.out.println(this.root + "/" + unqualifiedFileName + " doesn't exist");
/*     */ 
/* 171 */       return null;
/*     */     }
/* 173 */     return loadTemplateFile(prefix, unqualifiedFileName, fs);
/*     */   }
/*     */   public String getName() {
/* 176 */     return this.groupDirName; } 
/* 177 */   public String getFileName() { return this.root.getFile(); } 
/*     */   public URL getRootDirURL() {
/* 179 */     return this.root;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.STGroupDir
 * JD-Core Version:    0.6.2
 */