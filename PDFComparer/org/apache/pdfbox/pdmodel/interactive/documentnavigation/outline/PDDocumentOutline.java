/*    */ package org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ 
/*    */ public class PDDocumentOutline extends PDOutlineNode
/*    */ {
/*    */   public PDDocumentOutline()
/*    */   {
/* 36 */     this.node.setName("Type", "Outlines");
/*    */   }
/*    */ 
/*    */   public PDDocumentOutline(COSDictionary dic)
/*    */   {
/* 46 */     super(dic);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline
 * JD-Core Version:    0.6.2
 */