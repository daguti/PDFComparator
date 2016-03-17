/*    */ package org.apache.pdfbox.preflight.font.container;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.apache.pdfbox.pdmodel.font.PDFont;
/*    */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*    */ 
/*    */ public class Type0Container extends FontContainer
/*    */ {
/*    */   protected FontContainer delegateFontContainer;
/*    */ 
/*    */   public Type0Container(PDFont font)
/*    */   {
/* 36 */     super(font);
/*    */   }
/*    */ 
/*    */   protected float getFontProgramWidth(int cid)
/*    */   {
/* 42 */     float width = 0.0F;
/* 43 */     if (this.delegateFontContainer != null)
/*    */     {
/* 45 */       width = this.delegateFontContainer.getFontProgramWidth(cid);
/*    */     }
/* 47 */     return width;
/*    */   }
/*    */ 
/*    */   public void setDelegateFontContainer(FontContainer delegateFontContainer)
/*    */   {
/* 52 */     this.delegateFontContainer = delegateFontContainer;
/*    */   }
/*    */ 
/*    */   public List<ValidationResult.ValidationError> getAllErrors()
/*    */   {
/* 57 */     if (this.delegateFontContainer != null)
/*    */     {
/* 59 */       this.errorBuffer.addAll(this.delegateFontContainer.getAllErrors());
/*    */     }
/* 61 */     return this.errorBuffer;
/*    */   }
/*    */ 
/*    */   public boolean isValid()
/*    */   {
/* 66 */     boolean result = (this.errorBuffer.isEmpty()) && (isEmbeddedFont());
/* 67 */     if (this.delegateFontContainer != null)
/*    */     {
/* 69 */       result &= this.delegateFontContainer.isValid();
/*    */     }
/* 71 */     return result;
/*    */   }
/*    */ 
/*    */   public boolean isEmbeddedFont()
/*    */   {
/* 76 */     boolean result = this.embeddedFont;
/* 77 */     if (this.delegateFontContainer != null)
/*    */     {
/* 79 */       result &= this.delegateFontContainer.isEmbeddedFont();
/*    */     }
/* 81 */     return result;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.font.container.Type0Container
 * JD-Core Version:    0.6.2
 */