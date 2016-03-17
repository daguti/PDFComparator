/*    */ package org.apache.pdfbox.preflight.annotation.pdfa;
/*    */ 
/*    */ import java.util.Map;
/*    */ import org.apache.pdfbox.preflight.annotation.AnnotationValidatorFactory;
/*    */ import org.apache.pdfbox.preflight.annotation.FreeTextAnnotationValidator;
/*    */ import org.apache.pdfbox.preflight.annotation.InkAnnotationValdiator;
/*    */ import org.apache.pdfbox.preflight.annotation.LineAnnotationValidator;
/*    */ import org.apache.pdfbox.preflight.annotation.LinkAnnotationValidator;
/*    */ import org.apache.pdfbox.preflight.annotation.MarkupAnnotationValidator;
/*    */ import org.apache.pdfbox.preflight.annotation.PopupAnnotationValidator;
/*    */ import org.apache.pdfbox.preflight.annotation.PrintMarkAnnotationValidator;
/*    */ import org.apache.pdfbox.preflight.annotation.RubberStampAnnotationValidator;
/*    */ import org.apache.pdfbox.preflight.annotation.SquareCircleAnnotationValidator;
/*    */ import org.apache.pdfbox.preflight.annotation.TextAnnotationValidator;
/*    */ import org.apache.pdfbox.preflight.annotation.TrapNetAnnotationValidator;
/*    */ import org.apache.pdfbox.preflight.annotation.WidgetAnnotationValidator;
/*    */ 
/*    */ public class PDFAbAnnotationFactory extends AnnotationValidatorFactory
/*    */ {
/*    */   protected void initializeClasses()
/*    */   {
/* 63 */     this.validatorClasses.put("Text", TextAnnotationValidator.class);
/* 64 */     this.validatorClasses.put("Link", LinkAnnotationValidator.class);
/* 65 */     this.validatorClasses.put("FreeText", FreeTextAnnotationValidator.class);
/* 66 */     this.validatorClasses.put("Line", LineAnnotationValidator.class);
/*    */ 
/* 68 */     this.validatorClasses.put("Square", SquareCircleAnnotationValidator.class);
/* 69 */     this.validatorClasses.put("Circle", SquareCircleAnnotationValidator.class);
/*    */ 
/* 71 */     this.validatorClasses.put("Highlight", MarkupAnnotationValidator.class);
/* 72 */     this.validatorClasses.put("Underline", MarkupAnnotationValidator.class);
/* 73 */     this.validatorClasses.put("StrikeOut", MarkupAnnotationValidator.class);
/* 74 */     this.validatorClasses.put("Squiggly", MarkupAnnotationValidator.class);
/*    */ 
/* 76 */     this.validatorClasses.put("Stamp", RubberStampAnnotationValidator.class);
/* 77 */     this.validatorClasses.put("Ink", InkAnnotationValdiator.class);
/* 78 */     this.validatorClasses.put("Popup", PopupAnnotationValidator.class);
/* 79 */     this.validatorClasses.put("Widget", WidgetAnnotationValidator.class);
/* 80 */     this.validatorClasses.put("PrinterMark", PrintMarkAnnotationValidator.class);
/* 81 */     this.validatorClasses.put("TrapNet", TrapNetAnnotationValidator.class);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.annotation.pdfa.PDFAbAnnotationFactory
 * JD-Core Version:    0.6.2
 */