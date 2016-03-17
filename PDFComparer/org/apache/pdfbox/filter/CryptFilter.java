/*    */ package org.apache.pdfbox.filter;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ 
/*    */ public class CryptFilter
/*    */   implements Filter
/*    */ {
/*    */   public void decode(InputStream compressedData, OutputStream result, COSDictionary options, int filterIndex)
/*    */     throws IOException
/*    */   {
/* 37 */     COSName encryptionName = (COSName)options.getDictionaryObject(COSName.NAME);
/* 38 */     if ((encryptionName == null) || (encryptionName.equals(COSName.IDENTITY)))
/*    */     {
/* 41 */       Filter identityFilter = new IdentityFilter();
/* 42 */       identityFilter.decode(compressedData, result, options, filterIndex);
/*    */     }
/*    */     else
/*    */     {
/* 46 */       throw new IOException("Unsupported crypt filter " + encryptionName.getName());
/*    */     }
/*    */   }
/*    */ 
/*    */   public void encode(InputStream rawData, OutputStream result, COSDictionary options, int filterIndex)
/*    */     throws IOException
/*    */   {
/* 56 */     COSName encryptionName = (COSName)options.getDictionaryObject(COSName.NAME);
/* 57 */     if ((encryptionName == null) || (encryptionName.equals(COSName.IDENTITY)))
/*    */     {
/* 60 */       Filter identityFilter = new IdentityFilter();
/* 61 */       identityFilter.encode(rawData, result, options, filterIndex);
/*    */     }
/*    */     else
/*    */     {
/* 65 */       throw new IOException("Unsupported crypt filter " + encryptionName.getName());
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.filter.CryptFilter
 * JD-Core Version:    0.6.2
 */