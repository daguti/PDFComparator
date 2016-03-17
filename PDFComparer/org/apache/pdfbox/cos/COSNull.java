/*    */ package org.apache.pdfbox.cos;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import org.apache.pdfbox.exceptions.COSVisitorException;
/*    */ 
/*    */ public class COSNull extends COSBase
/*    */ {
/* 37 */   public static final byte[] NULL_BYTES = { 110, 117, 108, 108 };
/*    */ 
/* 42 */   public static final COSNull NULL = new COSNull();
/*    */ 
/*    */   public Object accept(ICOSVisitor visitor)
/*    */     throws COSVisitorException
/*    */   {
/* 61 */     return visitor.visitFromNull(this);
/*    */   }
/*    */ 
/*    */   public void writePDF(OutputStream output)
/*    */     throws IOException
/*    */   {
/* 72 */     output.write(NULL_BYTES);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.cos.COSNull
 * JD-Core Version:    0.6.2
 */