/*    */ package antlr;
/*    */ 
/*    */ import java.io.PrintWriter;
/*    */ import java.util.Enumeration;
/*    */ import java.util.StringTokenizer;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class NameSpace
/*    */ {
/* 22 */   private Vector names = new Vector();
/*    */   private String _name;
/*    */ 
/*    */   public NameSpace(String paramString)
/*    */   {
/* 26 */     this._name = new String(paramString);
/* 27 */     parse(paramString);
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 32 */     return this._name;
/*    */   }
/*    */ 
/*    */   protected void parse(String paramString)
/*    */   {
/* 42 */     StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "::");
/* 43 */     while (localStringTokenizer.hasMoreTokens())
/* 44 */       this.names.addElement(localStringTokenizer.nextToken());
/*    */   }
/*    */ 
/*    */   void emitDeclarations(PrintWriter paramPrintWriter)
/*    */   {
/* 51 */     for (Enumeration localEnumeration = this.names.elements(); localEnumeration.hasMoreElements(); ) {
/* 52 */       String str = (String)localEnumeration.nextElement();
/* 53 */       paramPrintWriter.println("ANTLR_BEGIN_NAMESPACE(" + str + ")");
/*    */     }
/*    */   }
/*    */ 
/*    */   void emitClosures(PrintWriter paramPrintWriter)
/*    */   {
/* 61 */     for (int i = 0; i < this.names.size(); i++)
/* 62 */       paramPrintWriter.println("ANTLR_END_NAMESPACE");
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.NameSpace
 * JD-Core Version:    0.6.2
 */