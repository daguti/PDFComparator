/*    */ package com.itextpdf.text.pdf.fonts.cmaps;
/*    */ 
/*    */ import com.itextpdf.text.error_messages.MessageLocalization;
/*    */ import com.itextpdf.text.io.RandomAccessSourceFactory;
/*    */ import com.itextpdf.text.io.StreamUtil;
/*    */ import com.itextpdf.text.pdf.PRTokeniser;
/*    */ import com.itextpdf.text.pdf.RandomAccessFileOrArray;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ public class CidResource
/*    */   implements CidLocation
/*    */ {
/*    */   public PRTokeniser getLocation(String location)
/*    */     throws IOException
/*    */   {
/* 64 */     String fullName = "com/itextpdf/text/pdf/fonts/cmaps/" + location;
/* 65 */     InputStream inp = StreamUtil.getResourceStream(fullName);
/* 66 */     if (inp == null)
/* 67 */       throw new IOException(MessageLocalization.getComposedMessage("the.cmap.1.was.not.found", new Object[] { fullName }));
/* 68 */     return new PRTokeniser(new RandomAccessFileOrArray(new RandomAccessSourceFactory().createSource(inp)));
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.fonts.cmaps.CidResource
 * JD-Core Version:    0.6.2
 */