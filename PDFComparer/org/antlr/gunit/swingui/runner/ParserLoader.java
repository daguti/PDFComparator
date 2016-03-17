/*     */ package org.antlr.gunit.swingui.runner;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FilenameFilter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class ParserLoader extends ClassLoader
/*     */ {
/*     */   private HashMap<String, Class> classList;
/*     */   private String grammar;
/*     */ 
/*     */   public ParserLoader(String grammarName, String classDir)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/*  49 */     String lexerName = grammarName + "Lexer";
/*     */ 
/*  52 */     File dir = new File(classDir);
/*  53 */     if (dir.isDirectory()) {
/*  54 */       this.classList = new HashMap();
/*  55 */       this.grammar = grammarName;
/*  56 */       File[] files = dir.listFiles(new ClassFilenameFilter(grammarName));
/*  57 */       File[] arr$ = files; int len$ = arr$.length; for (int i$ = 0; i$ < len$; i$++) { File f = arr$[i$];
/*     */ 
/*  60 */         InputStream in = new BufferedInputStream(new FileInputStream(f));
/*  61 */         byte[] classData = new byte[in.available()];
/*  62 */         in.read(classData);
/*  63 */         in.close();
/*     */ 
/*  66 */         Class newClass = defineClass(null, classData, 0, classData.length);
/*  67 */         assert (newClass != null);
/*  68 */         resolveClass(newClass);
/*     */ 
/*  71 */         String fileName = f.getName();
/*  72 */         String className = fileName.substring(0, fileName.lastIndexOf("."));
/*  73 */         this.classList.put(className, newClass); }
/*     */     }
/*     */     else
/*     */     {
/*  77 */       throw new IOException(classDir + " is not a directory.");
/*     */     }
/*     */ 
/*  80 */     if ((this.classList.isEmpty()) || (!this.classList.containsKey(lexerName)))
/*  81 */       throw new ClassNotFoundException(lexerName + " not found.");
/*     */   }
/*     */ 
/*     */   public synchronized Class loadClass(String name, boolean resolve)
/*     */     throws ClassNotFoundException
/*     */   {
/*  91 */     if (name.startsWith(this.grammar)) {
/*  92 */       if (this.classList.containsKey(name))
/*     */       {
/*  94 */         return (Class)this.classList.get(name);
/*     */       }
/*     */ 
/*  97 */       throw new ClassNotFoundException(name);
/*     */     }
/*     */ 
/* 101 */     Class c = findSystemClass(name);
/*     */ 
/* 103 */     return c;
/*     */   }
/*     */ 
/*     */   protected static class ClassFilenameFilter
/*     */     implements FilenameFilter
/*     */   {
/*     */     private String grammarName;
/*     */ 
/*     */     protected ClassFilenameFilter(String name)
/*     */     {
/* 115 */       this.grammarName = name;
/*     */     }
/*     */ 
/*     */     public boolean accept(File dir, String name) {
/* 119 */       return (name.startsWith(this.grammarName)) && (name.endsWith(".class"));
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.swingui.runner.ParserLoader
 * JD-Core Version:    0.6.2
 */