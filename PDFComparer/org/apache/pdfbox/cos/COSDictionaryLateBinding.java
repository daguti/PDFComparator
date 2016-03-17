/*    */ package org.apache.pdfbox.cos;
/*    */ 
/*    */ import java.util.Map;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ import org.apache.pdfbox.pdfparser.ConformingPDFParser;
/*    */ 
/*    */ public class COSDictionaryLateBinding extends COSDictionary
/*    */ {
/* 29 */   public static final Log log = LogFactory.getLog(COSDictionaryLateBinding.class);
/*    */   ConformingPDFParser parser;
/*    */ 
/*    */   public COSDictionaryLateBinding(ConformingPDFParser parser)
/*    */   {
/* 34 */     this.parser = parser;
/*    */   }
/*    */ 
/*    */   public COSBase getDictionaryObject(COSName key)
/*    */   {
/* 46 */     COSBase retval = (COSBase)this.items.get(key);
/* 47 */     if ((retval instanceof COSObject)) {
/* 48 */       int objectNumber = ((COSObject)retval).getObjectNumber().intValue();
/* 49 */       int generation = ((COSObject)retval).getGenerationNumber().intValue();
/*    */       try {
/* 51 */         retval = this.parser.getObject(objectNumber, generation);
/*    */       } catch (Exception e) {
/* 53 */         log.warn("Unable to read information for object " + objectNumber);
/*    */       }
/*    */     }
/* 56 */     if ((retval instanceof COSNull)) {
/* 57 */       retval = null;
/*    */     }
/* 59 */     return retval;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.cos.COSDictionaryLateBinding
 * JD-Core Version:    0.6.2
 */