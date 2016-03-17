/*    */ package antlr;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.PrintWriter;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class DefaultJavaCodeGeneratorPrintWriterManager
/*    */   implements JavaCodeGeneratorPrintWriterManager
/*    */ {
/*    */   private Grammar grammar;
/*    */   private PrintWriterWithSMAP smapOutput;
/*    */   private PrintWriter currentOutput;
/*    */   private Tool tool;
/* 13 */   private Map sourceMaps = new HashMap();
/*    */   private String currentFileName;
/*    */ 
/*    */   public PrintWriter setupOutput(Tool paramTool, Grammar paramGrammar)
/*    */     throws IOException
/*    */   {
/* 17 */     return setupOutput(paramTool, paramGrammar, null);
/*    */   }
/*    */ 
/*    */   public PrintWriter setupOutput(Tool paramTool, String paramString) throws IOException {
/* 21 */     return setupOutput(paramTool, null, paramString);
/*    */   }
/*    */ 
/*    */   public PrintWriter setupOutput(Tool paramTool, Grammar paramGrammar, String paramString) throws IOException {
/* 25 */     this.tool = paramTool;
/* 26 */     this.grammar = paramGrammar;
/*    */ 
/* 28 */     if (paramString == null) {
/* 29 */       paramString = paramGrammar.getClassName();
/*    */     }
/* 31 */     this.smapOutput = new PrintWriterWithSMAP(paramTool.openOutputFile(paramString + ".java"));
/* 32 */     this.currentFileName = (paramString + ".java");
/* 33 */     this.currentOutput = this.smapOutput;
/* 34 */     return this.currentOutput;
/*    */   }
/*    */ 
/*    */   public void startMapping(int paramInt) {
/* 38 */     this.smapOutput.startMapping(paramInt);
/*    */   }
/*    */ 
/*    */   public void startSingleSourceLineMapping(int paramInt) {
/* 42 */     this.smapOutput.startSingleSourceLineMapping(paramInt);
/*    */   }
/*    */ 
/*    */   public void endMapping() {
/* 46 */     this.smapOutput.endMapping();
/*    */   }
/*    */ 
/*    */   public void finishOutput() throws IOException {
/* 50 */     this.currentOutput.close();
/* 51 */     if (this.grammar != null)
/*    */     {
/* 53 */       PrintWriter localPrintWriter = this.tool.openOutputFile(this.grammar.getClassName() + ".smap");
/* 54 */       String str = this.grammar.getFilename();
/* 55 */       str = str.replace('\\', '/');
/* 56 */       int i = str.lastIndexOf('/');
/* 57 */       if (i != -1)
/* 58 */         str = str.substring(i + 1);
/* 59 */       this.smapOutput.dump(localPrintWriter, this.grammar.getClassName(), str);
/* 60 */       this.sourceMaps.put(this.currentFileName, this.smapOutput.getSourceMap());
/*    */     }
/* 62 */     this.currentOutput = null;
/*    */   }
/*    */ 
/*    */   public Map getSourceMaps() {
/* 66 */     return this.sourceMaps;
/*    */   }
/*    */ 
/*    */   public int getCurrentOutputLine()
/*    */   {
/* 71 */     return this.smapOutput.getCurrentOutputLine();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.DefaultJavaCodeGeneratorPrintWriterManager
 * JD-Core Version:    0.6.2
 */