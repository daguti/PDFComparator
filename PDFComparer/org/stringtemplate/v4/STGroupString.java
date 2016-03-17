/*    */ package org.stringtemplate.v4;
/*    */ 
/*    */ import org.antlr.runtime.ANTLRStringStream;
/*    */ import org.antlr.runtime.CommonTokenStream;
/*    */ import org.stringtemplate.v4.compiler.CompiledST;
/*    */ import org.stringtemplate.v4.compiler.GroupLexer;
/*    */ import org.stringtemplate.v4.compiler.GroupParser;
/*    */ import org.stringtemplate.v4.misc.ErrorManager;
/*    */ import org.stringtemplate.v4.misc.ErrorType;
/*    */ 
/*    */ public class STGroupString extends STGroup
/*    */ {
/*    */   public String sourceName;
/*    */   public String text;
/* 39 */   protected boolean alreadyLoaded = false;
/*    */ 
/* 41 */   public STGroupString(String text) { this("<string>", text, '<', '>'); } 
/*    */   public STGroupString(String sourceName, String text) {
/* 43 */     this(sourceName, text, '<', '>');
/*    */   }
/*    */   public STGroupString(String sourceName, String text, char delimiterStartChar, char delimiterStopChar) {
/* 46 */     super(delimiterStartChar, delimiterStopChar);
/* 47 */     this.sourceName = sourceName;
/* 48 */     this.text = text;
/*    */   }
/*    */ 
/*    */   public boolean isDictionary(String name)
/*    */   {
/* 53 */     if (!this.alreadyLoaded) load();
/* 54 */     return super.isDictionary(name);
/*    */   }
/*    */ 
/*    */   public boolean isDefined(String name) {
/* 58 */     if (!this.alreadyLoaded) load();
/* 59 */     return super.isDefined(name);
/*    */   }
/*    */ 
/*    */   protected CompiledST load(String name) {
/* 63 */     if (!this.alreadyLoaded) load();
/* 64 */     return rawGetTemplate(name);
/*    */   }
/*    */ 
/*    */   public void load() {
/* 68 */     if (this.alreadyLoaded) return;
/* 69 */     this.alreadyLoaded = true;
/* 70 */     GroupParser parser = null;
/*    */     try {
/* 72 */       ANTLRStringStream fs = new ANTLRStringStream(this.text);
/* 73 */       fs.name = this.sourceName;
/* 74 */       GroupLexer lexer = new GroupLexer(fs);
/* 75 */       CommonTokenStream tokens = new CommonTokenStream(lexer);
/* 76 */       parser = new GroupParser(tokens);
/*    */ 
/* 79 */       parser.group(this, "/");
/*    */     }
/*    */     catch (Exception e) {
/* 82 */       this.errMgr.IOError(null, ErrorType.CANT_LOAD_GROUP_FILE, e, "<string>");
/*    */     }
/*    */   }
/*    */ 
/* 86 */   public String getFileName() { return "<string>"; }
/*    */ 
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.STGroupString
 * JD-Core Version:    0.6.2
 */