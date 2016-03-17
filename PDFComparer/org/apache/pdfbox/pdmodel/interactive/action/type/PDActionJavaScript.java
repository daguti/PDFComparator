/*    */ package org.apache.pdfbox.pdmodel.interactive.action.type;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.pdmodel.common.PDTextStream;
/*    */ 
/*    */ public class PDActionJavaScript extends PDAction
/*    */ {
/*    */   public static final String SUB_TYPE = "JavaScript";
/*    */ 
/*    */   public PDActionJavaScript()
/*    */   {
/* 41 */     setSubType("JavaScript");
/*    */   }
/*    */ 
/*    */   public PDActionJavaScript(String js)
/*    */   {
/* 51 */     this();
/* 52 */     setAction(js);
/*    */   }
/*    */ 
/*    */   public PDActionJavaScript(COSDictionary a)
/*    */   {
/* 62 */     super(a);
/*    */   }
/*    */ 
/*    */   public void setAction(PDTextStream sAction)
/*    */   {
/* 70 */     this.action.setItem("JS", sAction);
/*    */   }
/*    */ 
/*    */   public void setAction(String sAction)
/*    */   {
/* 78 */     this.action.setString("JS", sAction);
/*    */   }
/*    */ 
/*    */   public PDTextStream getAction()
/*    */   {
/* 86 */     return PDTextStream.createTextStream(this.action.getDictionaryObject("JS"));
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.action.type.PDActionJavaScript
 * JD-Core Version:    0.6.2
 */