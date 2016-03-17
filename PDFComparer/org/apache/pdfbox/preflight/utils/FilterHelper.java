/*    */ package org.apache.pdfbox.preflight.utils;
/*    */ 
/*    */ import org.apache.pdfbox.preflight.PreflightContext;
/*    */ import org.apache.pdfbox.preflight.PreflightDocument;
/*    */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*    */ 
/*    */ public class FilterHelper
/*    */ {
/*    */   public static void isAuthorizedFilter(PreflightContext context, String filter)
/*    */   {
/* 62 */     PreflightDocument preflightDocument = context.getDocument();
/* 63 */     switch (1.$SwitchMap$org$apache$pdfbox$preflight$Format[preflightDocument.getSpecification().ordinal()])
/*    */     {
/*    */     case 1:
/* 66 */       isAuthorizedFilterInPDFA(context, filter);
/* 67 */       break;
/*    */     default:
/* 71 */       isAuthorizedFilterInPDFA(context, filter);
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void isAuthorizedFilterInPDFA(PreflightContext context, String filter)
/*    */   {
/* 86 */     if (filter != null)
/*    */     {
/* 89 */       if (("LZWDecode".equals(filter)) || ("LZW".equals(filter)))
/*    */       {
/* 91 */         context.addValidationError(new ValidationResult.ValidationError("1.2.7", "LZWDecode is forbidden"));
/*    */       }
/*    */ 
/* 97 */       boolean definedFilter = "FlateDecode".equals(filter);
/* 98 */       definedFilter = (definedFilter) || ("ASCIIHexDecode".equals(filter));
/* 99 */       definedFilter = (definedFilter) || ("ASCII85Decode".equals(filter));
/* 100 */       definedFilter = (definedFilter) || ("CCITTFaxDecode".equals(filter));
/* 101 */       definedFilter = (definedFilter) || ("DCTDecode".equals(filter));
/* 102 */       definedFilter = (definedFilter) || ("JBIG2Decode".equals(filter));
/* 103 */       definedFilter = (definedFilter) || ("RunLengthDecode".equals(filter));
/*    */ 
/* 105 */       definedFilter = (definedFilter) || ("Fl".equals(filter));
/* 106 */       definedFilter = (definedFilter) || ("AHx".equals(filter));
/* 107 */       definedFilter = (definedFilter) || ("A85".equals(filter));
/* 108 */       definedFilter = (definedFilter) || ("CCF".equals(filter));
/* 109 */       definedFilter = (definedFilter) || ("DCT".equals(filter));
/* 110 */       definedFilter = (definedFilter) || ("RL".equals(filter));
/*    */ 
/* 112 */       if (!definedFilter)
/*    */       {
/* 114 */         context.addValidationError(new ValidationResult.ValidationError("1.2.12", "This filter isn't defined in the PDF Reference Third Edition : " + filter));
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.utils.FilterHelper
 * JD-Core Version:    0.6.2
 */