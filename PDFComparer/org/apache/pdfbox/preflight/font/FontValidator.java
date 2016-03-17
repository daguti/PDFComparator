/*    */ package org.apache.pdfbox.preflight.font;
/*    */ 
/*    */ import org.apache.pdfbox.pdmodel.font.PDFont;
/*    */ import org.apache.pdfbox.preflight.PreflightContext;
/*    */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*    */ import org.apache.pdfbox.preflight.font.container.FontContainer;
/*    */ import org.apache.pdfbox.preflight.font.descriptor.FontDescriptorHelper;
/*    */ 
/*    */ public abstract class FontValidator<T extends FontContainer>
/*    */ {
/*    */   protected T fontContainer;
/*    */   protected PreflightContext context;
/*    */   protected PDFont font;
/*    */   protected FontDescriptorHelper<T> descriptorHelper;
/*    */   private static final String SUB_SET_PATTERN = "^[A-Z]{6}\\+.*";
/*    */ 
/*    */   public FontValidator(PreflightContext context, PDFont font, T fContainer)
/*    */   {
/* 43 */     this.context = context;
/* 44 */     this.font = font;
/* 45 */     this.fontContainer = fContainer;
/* 46 */     this.context.addFontContainer(font.getCOSObject(), fContainer);
/*    */   }
/*    */ 
/*    */   public static boolean isSubSet(String fontName)
/*    */   {
/* 51 */     return (fontName != null) && (fontName.matches("^[A-Z]{6}\\+.*"));
/*    */   }
/*    */ 
/*    */   public static String getSubSetPatternDelimiter()
/*    */   {
/* 56 */     return "\\+";
/*    */   }
/*    */ 
/*    */   public abstract void validate()
/*    */     throws ValidationException;
/*    */ 
/*    */   protected void checkEncoding()
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void checkToUnicode()
/*    */   {
/*    */   }
/*    */ 
/*    */   public T getFontContainer()
/*    */   {
/* 73 */     return this.fontContainer;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.font.FontValidator
 * JD-Core Version:    0.6.2
 */