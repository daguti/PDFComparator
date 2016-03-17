/*    */ package antlr.preprocessor;
/*    */ 
/*    */ import antlr.Tool;
/*    */ import antlr.collections.impl.IndexedVector;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintWriter;
/*    */ import java.util.Enumeration;
/*    */ 
/*    */ public class GrammarFile
/*    */ {
/*    */   protected String fileName;
/* 20 */   protected String headerAction = "";
/*    */   protected IndexedVector options;
/*    */   protected IndexedVector grammars;
/* 23 */   protected boolean expanded = false;
/*    */   protected Tool tool;
/*    */ 
/*    */   public GrammarFile(Tool paramTool, String paramString)
/*    */   {
/* 27 */     this.fileName = paramString;
/* 28 */     this.grammars = new IndexedVector();
/* 29 */     this.tool = paramTool;
/*    */   }
/*    */ 
/*    */   public void addGrammar(Grammar paramGrammar) {
/* 33 */     this.grammars.appendElement(paramGrammar.getName(), paramGrammar);
/*    */   }
/*    */ 
/*    */   public void generateExpandedFile() throws IOException {
/* 37 */     if (!this.expanded) {
/* 38 */       return;
/*    */     }
/* 40 */     String str = nameForExpandedGrammarFile(getName());
/*    */ 
/* 43 */     PrintWriter localPrintWriter = this.tool.openOutputFile(str);
/* 44 */     localPrintWriter.println(toString());
/* 45 */     localPrintWriter.close();
/*    */   }
/*    */ 
/*    */   public IndexedVector getGrammars() {
/* 49 */     return this.grammars;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 53 */     return this.fileName;
/*    */   }
/*    */ 
/*    */   public String nameForExpandedGrammarFile(String paramString) {
/* 57 */     if (this.expanded)
/*    */     {
/* 59 */       return "expanded" + this.tool.fileMinusPath(paramString);
/*    */     }
/*    */ 
/* 62 */     return paramString;
/*    */   }
/*    */ 
/*    */   public void setExpanded(boolean paramBoolean)
/*    */   {
/* 67 */     this.expanded = paramBoolean;
/*    */   }
/*    */ 
/*    */   public void addHeaderAction(String paramString) {
/* 71 */     this.headerAction = (this.headerAction + paramString + System.getProperty("line.separator"));
/*    */   }
/*    */ 
/*    */   public void setOptions(IndexedVector paramIndexedVector) {
/* 75 */     this.options = paramIndexedVector;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 79 */     String str1 = this.headerAction == null ? "" : this.headerAction;
/* 80 */     String str2 = this.options == null ? "" : Hierarchy.optionsToString(this.options);
/*    */ 
/* 82 */     StringBuffer localStringBuffer = new StringBuffer(10000); localStringBuffer.append(str1); localStringBuffer.append(str2);
/* 83 */     for (Enumeration localEnumeration = this.grammars.elements(); localEnumeration.hasMoreElements(); ) {
/* 84 */       Grammar localGrammar = (Grammar)localEnumeration.nextElement();
/* 85 */       localStringBuffer.append(localGrammar.toString());
/*    */     }
/* 87 */     return localStringBuffer.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.preprocessor.GrammarFile
 * JD-Core Version:    0.6.2
 */