/*    */ package org.apache.pdfbox.preflight.font.util;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import org.apache.commons.io.IOUtils;
/*    */ import org.apache.pdfbox.cos.COSStream;
/*    */ 
/*    */ public class CIDToGIDMap
/*    */ {
/* 34 */   public final int NOTDEF_GLYPH_INDEX = 0;
/* 35 */   private byte[] map = null;
/*    */ 
/*    */   public void parseStream(COSStream stream)
/*    */     throws IOException
/*    */   {
/* 46 */     InputStream is = stream.getUnfilteredStream();
/* 47 */     ByteArrayOutputStream os = new ByteArrayOutputStream();
/*    */     try
/*    */     {
/* 51 */       IOUtils.copy(stream.getUnfilteredStream(), os);
/* 52 */       this.map = os.toByteArray();
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 56 */       IOUtils.closeQuietly(is);
/* 57 */       IOUtils.closeQuietly(os);
/*    */     }
/*    */   }
/*    */ 
/*    */   public int getGID(int cid)
/*    */   {
/* 69 */     if ((this.map == null) || (cid * 2 + 1 >= this.map.length))
/*    */     {
/* 71 */       return 0;
/*    */     }
/* 73 */     int index = cid * 2;
/* 74 */     return (this.map[index] & 0xFF) << 8 ^ this.map[(index + 1)] & 0xFF;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.font.util.CIDToGIDMap
 * JD-Core Version:    0.6.2
 */