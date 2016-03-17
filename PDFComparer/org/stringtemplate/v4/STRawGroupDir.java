/*    */ package org.stringtemplate.v4;
/*    */ 
/*    */ import java.net.URL;
/*    */ import org.antlr.runtime.CharStream;
/*    */ import org.stringtemplate.v4.compiler.CompiledST;
/*    */ import org.stringtemplate.v4.compiler.Compiler;
/*    */ import org.stringtemplate.v4.misc.Misc;
/*    */ 
/*    */ public class STRawGroupDir extends STGroupDir
/*    */ {
/*    */   public STRawGroupDir(String dirName)
/*    */   {
/* 15 */     super(dirName);
/*    */   }
/*    */ 
/*    */   public STRawGroupDir(String dirName, char delimiterStartChar, char delimiterStopChar) {
/* 19 */     super(dirName, delimiterStartChar, delimiterStopChar);
/*    */   }
/*    */ 
/*    */   public STRawGroupDir(String dirName, String encoding) {
/* 23 */     super(dirName, encoding);
/*    */   }
/*    */ 
/*    */   public STRawGroupDir(String dirName, String encoding, char delimiterStartChar, char delimiterStopChar) {
/* 27 */     super(dirName, encoding, delimiterStartChar, delimiterStopChar);
/*    */   }
/*    */ 
/*    */   public STRawGroupDir(URL root, String encoding, char delimiterStartChar, char delimiterStopChar) {
/* 31 */     super(root, encoding, delimiterStartChar, delimiterStopChar);
/*    */   }
/*    */ 
/*    */   public CompiledST loadTemplateFile(String prefix, String unqualifiedFileName, CharStream templateStream)
/*    */   {
/* 38 */     String template = templateStream.substring(0, templateStream.size() - 1);
/* 39 */     String templateName = Misc.getFileNameNoSuffix(unqualifiedFileName);
/* 40 */     CompiledST impl = new Compiler(this).compile(templateName, template);
/* 41 */     impl.prefix = prefix;
/* 42 */     return impl;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.STRawGroupDir
 * JD-Core Version:    0.6.2
 */