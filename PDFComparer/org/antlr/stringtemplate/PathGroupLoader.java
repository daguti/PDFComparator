/*     */ package org.antlr.stringtemplate;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.StringTokenizer;
/*     */ import org.antlr.stringtemplate.language.AngleBracketTemplateLexer;
/*     */ 
/*     */ public class PathGroupLoader
/*     */   implements StringTemplateGroupLoader
/*     */ {
/*  18 */   protected List dirs = null;
/*     */ 
/*  20 */   protected StringTemplateErrorListener errors = null;
/*     */ 
/*  25 */   String fileCharEncoding = System.getProperty("file.encoding");
/*     */ 
/*     */   public PathGroupLoader(StringTemplateErrorListener errors) {
/*  28 */     this.errors = errors;
/*     */   }
/*     */ 
/*     */   public PathGroupLoader(String dirStr, StringTemplateErrorListener errors)
/*     */   {
/*  35 */     this.errors = errors;
/*  36 */     StringTokenizer tokenizer = new StringTokenizer(dirStr, ":", false);
/*  37 */     while (tokenizer.hasMoreElements()) {
/*  38 */       String dir = (String)tokenizer.nextElement();
/*  39 */       if (this.dirs == null) {
/*  40 */         this.dirs = new ArrayList();
/*     */       }
/*  42 */       this.dirs.add(dir);
/*     */     }
/*     */   }
/*     */ 
/*     */   public StringTemplateGroup loadGroup(String groupName, Class templateLexer, StringTemplateGroup superGroup)
/*     */   {
/*  54 */     StringTemplateGroup group = null;
/*  55 */     BufferedReader br = null;
/*     */ 
/*  57 */     Class lexer = AngleBracketTemplateLexer.class;
/*  58 */     if (templateLexer != null)
/*  59 */       lexer = templateLexer;
/*     */     try
/*     */     {
/*  62 */       br = locate(groupName + ".stg");
/*  63 */       if (br == null) {
/*  64 */         error("no such group file " + groupName + ".stg");
/*  65 */         return null;
/*     */       }
/*  67 */       group = new StringTemplateGroup(br, lexer, this.errors, superGroup);
/*  68 */       br.close();
/*  69 */       br = null;
/*     */     }
/*     */     catch (IOException ioe) {
/*  72 */       error("can't load group " + groupName, ioe);
/*     */     }
/*     */     finally {
/*  75 */       if (br != null) {
/*     */         try {
/*  77 */           br.close();
/*     */         }
/*     */         catch (IOException ioe2) {
/*  80 */           error("Cannot close template group file: " + groupName + ".stg", ioe2);
/*     */         }
/*     */       }
/*     */     }
/*  84 */     return group;
/*     */   }
/*     */ 
/*     */   public StringTemplateGroup loadGroup(String groupName, StringTemplateGroup superGroup)
/*     */   {
/*  90 */     return loadGroup(groupName, null, superGroup);
/*     */   }
/*     */ 
/*     */   public StringTemplateGroup loadGroup(String groupName) {
/*  94 */     return loadGroup(groupName, null);
/*     */   }
/*     */ 
/*     */   public StringTemplateGroupInterface loadInterface(String interfaceName) {
/*  98 */     StringTemplateGroupInterface I = null;
/*     */     try {
/* 100 */       BufferedReader br = locate(interfaceName + ".sti");
/* 101 */       if (br == null) {
/* 102 */         error("no such interface file " + interfaceName + ".sti");
/* 103 */         return null;
/*     */       }
/* 105 */       I = new StringTemplateGroupInterface(br, this.errors);
/*     */     }
/*     */     catch (IOException ioe) {
/* 108 */       error("can't load interface " + interfaceName, ioe);
/*     */     }
/* 110 */     return I;
/*     */   }
/*     */ 
/*     */   protected BufferedReader locate(String name) throws IOException
/*     */   {
/* 115 */     for (int i = 0; i < this.dirs.size(); i++) {
/* 116 */       String dir = (String)this.dirs.get(i);
/* 117 */       String fileName = dir + "/" + name;
/* 118 */       if (new File(fileName).exists()) {
/* 119 */         FileInputStream fis = new FileInputStream(fileName);
/* 120 */         InputStreamReader isr = getInputStreamReader(fis);
/* 121 */         return new BufferedReader(isr);
/*     */       }
/*     */     }
/* 124 */     return null;
/*     */   }
/*     */ 
/*     */   protected InputStreamReader getInputStreamReader(InputStream in) {
/* 128 */     InputStreamReader isr = null;
/*     */     try {
/* 130 */       isr = new InputStreamReader(in, this.fileCharEncoding);
/*     */     }
/*     */     catch (UnsupportedEncodingException uee) {
/* 133 */       error("Invalid file character encoding: " + this.fileCharEncoding);
/*     */     }
/* 135 */     return isr;
/*     */   }
/*     */ 
/*     */   public String getFileCharEncoding() {
/* 139 */     return this.fileCharEncoding;
/*     */   }
/*     */ 
/*     */   public void setFileCharEncoding(String fileCharEncoding) {
/* 143 */     this.fileCharEncoding = fileCharEncoding;
/*     */   }
/*     */ 
/*     */   public void error(String msg) {
/* 147 */     error(msg, null);
/*     */   }
/*     */ 
/*     */   public void error(String msg, Exception e) {
/* 151 */     if (this.errors != null) {
/* 152 */       this.errors.error(msg, e);
/*     */     }
/*     */     else {
/* 155 */       System.err.println("StringTemplate: " + msg);
/* 156 */       if (e != null)
/* 157 */         e.printStackTrace();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.stringtemplate.PathGroupLoader
 * JD-Core Version:    0.6.2
 */