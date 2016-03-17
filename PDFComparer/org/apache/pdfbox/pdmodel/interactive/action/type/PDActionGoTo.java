/*    */ package org.apache.pdfbox.pdmodel.interactive.action.type;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDDestination;
/*    */ 
/*    */ public class PDActionGoTo extends PDAction
/*    */ {
/*    */   public static final String SUB_TYPE = "GoTo";
/*    */ 
/*    */   public PDActionGoTo()
/*    */   {
/* 44 */     setSubType("GoTo");
/*    */   }
/*    */ 
/*    */   public PDActionGoTo(COSDictionary a)
/*    */   {
/* 54 */     super(a);
/*    */   }
/*    */ 
/*    */   public PDDestination getDestination()
/*    */     throws IOException
/*    */   {
/* 66 */     return PDDestination.create(getCOSDictionary().getDictionaryObject("D"));
/*    */   }
/*    */ 
/*    */   public void setDestination(PDDestination d)
/*    */   {
/* 76 */     getCOSDictionary().setItem("D", d);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.action.type.PDActionGoTo
 * JD-Core Version:    0.6.2
 */