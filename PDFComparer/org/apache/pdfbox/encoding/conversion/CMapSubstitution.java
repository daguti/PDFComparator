/*    */ package org.apache.pdfbox.encoding.conversion;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class CMapSubstitution
/*    */ {
/* 31 */   private static HashMap<String, String> cmapSubstitutions = new HashMap();
/*    */ 
/*    */   public static String substituteCMap(String cmapName)
/*    */   {
/* 78 */     if (cmapSubstitutions.containsKey(cmapName))
/*    */     {
/* 80 */       return (String)cmapSubstitutions.get(cmapName);
/*    */     }
/* 82 */     return cmapName;
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 43 */     cmapSubstitutions.put("Adobe-GB1-4", "Adobe-GB1-UCS2");
/* 44 */     cmapSubstitutions.put("GBK-EUC-H", "GBK-EUC-UCS2");
/* 45 */     cmapSubstitutions.put("GBK-EUC-V", "GBK-EUC-UCS2");
/* 46 */     cmapSubstitutions.put("GBpc-EUC-H", "GBpc-EUC-UCS2C");
/* 47 */     cmapSubstitutions.put("GBpc-EUC-V", "GBpc-EUC-UCS2C");
/*    */ 
/* 50 */     cmapSubstitutions.put("Adobe-CNS1-4", "Adobe-CNS1-UCS2");
/* 51 */     cmapSubstitutions.put("B5pc-H", "B5pc-UCS2");
/* 52 */     cmapSubstitutions.put("B5pc-V", "B5pc-UCS2");
/* 53 */     cmapSubstitutions.put("ETen-B5-H", "ETen-B5-UCS2");
/* 54 */     cmapSubstitutions.put("ETen-B5-V", "ETen-B5-UCS2");
/* 55 */     cmapSubstitutions.put("ETenms-B5-H", "ETen-B5-UCS2");
/* 56 */     cmapSubstitutions.put("ETenms-B5-V", "ETen-B5-UCS2");
/*    */ 
/* 59 */     cmapSubstitutions.put("90ms-RKSJ-H", "90ms-RKSJ-UCS2");
/* 60 */     cmapSubstitutions.put("90ms-RKSJ-V", "90ms-RKSJ-UCS2");
/* 61 */     cmapSubstitutions.put("90msp-RKSJ-H", "90ms-RKSJ-UCS2");
/* 62 */     cmapSubstitutions.put("90msp-RKSJ-V", "90ms-RKSJ-UCS2");
/* 63 */     cmapSubstitutions.put("90pv-RKSJ-H", "90pv-RKSJ-UCS2");
/* 64 */     cmapSubstitutions.put("UniJIS-UCS2-HW-H", "UniJIS-UCS2-H");
/* 65 */     cmapSubstitutions.put("Adobe-Japan1-4", "Adobe-Japan1-UCS2");
/*    */ 
/* 67 */     cmapSubstitutions.put("Adobe-Identity-0", "Identity-H");
/* 68 */     cmapSubstitutions.put("Adobe-Identity-1", "Identity-H");
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.encoding.conversion.CMapSubstitution
 * JD-Core Version:    0.6.2
 */