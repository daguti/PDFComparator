/*    */ package org.apache.pdfbox.encoding;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import org.apache.fontbox.afm.CharMetric;
/*    */ import org.apache.fontbox.afm.FontMetric;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ 
/*    */ public class AFMEncoding extends Encoding
/*    */ {
/* 34 */   private FontMetric metric = null;
/*    */ 
/*    */   public AFMEncoding(FontMetric fontInfo)
/*    */   {
/* 43 */     this.metric = fontInfo;
/* 44 */     Iterator characters = this.metric.getCharMetrics().iterator();
/* 45 */     while (characters.hasNext())
/*    */     {
/* 47 */       CharMetric nextMetric = (CharMetric)characters.next();
/* 48 */       addCharacterEncoding(nextMetric.getCharacterCode(), nextMetric.getName());
/*    */     }
/*    */   }
/*    */ 
/*    */   public COSBase getCOSObject()
/*    */   {
/* 59 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.encoding.AFMEncoding
 * JD-Core Version:    0.6.2
 */