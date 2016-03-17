/*    */ package org.apache.pdfbox.encoding;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.Map;
/*    */ import org.apache.pdfbox.cos.COSArray;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.cos.COSNumber;
/*    */ 
/*    */ public class DictionaryEncoding extends Encoding
/*    */ {
/* 35 */   private COSDictionary encoding = null;
/*    */ 
/*    */   public DictionaryEncoding(COSDictionary fontEncoding)
/*    */     throws IOException
/*    */   {
/* 46 */     this.encoding = fontEncoding;
/*    */ 
/* 59 */     Encoding baseEncoding = StandardEncoding.INSTANCE;
/* 60 */     COSName baseEncodingName = (COSName)this.encoding.getDictionaryObject(COSName.BASE_ENCODING);
/*    */ 
/* 62 */     if (baseEncodingName != null) {
/* 63 */       baseEncoding = EncodingManager.INSTANCE.getEncoding(baseEncodingName);
/*    */     }
/*    */ 
/* 67 */     this.nameToCode.putAll(baseEncoding.nameToCode);
/* 68 */     this.codeToName.putAll(baseEncoding.codeToName);
/*    */ 
/* 72 */     COSArray differences = (COSArray)this.encoding.getDictionaryObject(COSName.DIFFERENCES);
/* 73 */     int currentIndex = -1;
/* 74 */     for (int i = 0; (differences != null) && (i < differences.size()); i++)
/*    */     {
/* 76 */       COSBase next = differences.getObject(i);
/* 77 */       if ((next instanceof COSNumber))
/*    */       {
/* 79 */         currentIndex = ((COSNumber)next).intValue();
/*    */       }
/* 81 */       else if ((next instanceof COSName))
/*    */       {
/* 83 */         COSName name = (COSName)next;
/* 84 */         addCharacterEncoding(currentIndex++, name.getName());
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public COSBase getCOSObject()
/*    */   {
/* 96 */     return this.encoding;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.encoding.DictionaryEncoding
 * JD-Core Version:    0.6.2
 */