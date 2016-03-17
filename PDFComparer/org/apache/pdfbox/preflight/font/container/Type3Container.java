/*    */ package org.apache.pdfbox.preflight.font.container;
/*    */ 
/*    */ import org.apache.pdfbox.pdmodel.font.PDFont;
/*    */ 
/*    */ public class Type3Container extends FontContainer
/*    */ {
/*    */   public Type3Container(PDFont font)
/*    */   {
/* 35 */     super(font);
/*    */   }
/*    */ 
/*    */   protected float getFontProgramWidth(int cid)
/*    */   {
/* 41 */     return 0.0F;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.font.container.Type3Container
 * JD-Core Version:    0.6.2
 */