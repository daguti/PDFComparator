/*    */ package org.apache.pdfbox.encoding;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ 
/*    */ public class MacOSRomanEncoding extends MacRomanEncoding
/*    */ {
/* 33 */   public static final MacOSRomanEncoding INSTANCE = new MacOSRomanEncoding();
/*    */ 
/*    */   public MacOSRomanEncoding()
/*    */   {
/* 41 */     addCharacterEncoding(255, "notequal");
/* 42 */     addCharacterEncoding(260, "infinity");
/* 43 */     addCharacterEncoding(262, "lessequal");
/* 44 */     addCharacterEncoding(263, "greaterequal");
/* 45 */     addCharacterEncoding(266, "partialdiff");
/* 46 */     addCharacterEncoding(267, "summation");
/* 47 */     addCharacterEncoding(270, "product");
/* 48 */     addCharacterEncoding(271, "pi");
/* 49 */     addCharacterEncoding(272, "integral");
/* 50 */     addCharacterEncoding(275, "Omega");
/* 51 */     addCharacterEncoding(303, "radical");
/* 52 */     addCharacterEncoding(305, "approxequal");
/* 53 */     addCharacterEncoding(306, "Delta");
/* 54 */     addCharacterEncoding(327, "lozenge");
/* 55 */     addCharacterEncoding(333, "Euro");
/* 56 */     addCharacterEncoding(360, "apple");
/*    */   }
/*    */ 
/*    */   public COSBase getCOSObject()
/*    */   {
/* 66 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.encoding.MacOSRomanEncoding
 * JD-Core Version:    0.6.2
 */