/*    */ package antlr;
/*    */ 
/*    */ import java.io.PrintWriter;
/*    */ 
/*    */ public class CSharpNameSpace extends NameSpace
/*    */ {
/*    */   public CSharpNameSpace(String paramString)
/*    */   {
/* 39 */     super(paramString);
/*    */   }
/*    */ 
/*    */   void emitDeclarations(PrintWriter paramPrintWriter)
/*    */   {
/* 46 */     paramPrintWriter.println("namespace " + getName());
/* 47 */     paramPrintWriter.println("{");
/*    */   }
/*    */ 
/*    */   void emitClosures(PrintWriter paramPrintWriter)
/*    */   {
/* 54 */     paramPrintWriter.println("}");
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.CSharpNameSpace
 * JD-Core Version:    0.6.2
 */