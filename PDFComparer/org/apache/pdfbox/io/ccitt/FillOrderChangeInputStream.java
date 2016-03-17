/*    */ package org.apache.pdfbox.io.ccitt;
/*    */ 
/*    */ import java.io.FilterInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ public class FillOrderChangeInputStream extends FilterInputStream
/*    */ {
/* 73 */   private static final byte[] FLIP_TABLE = { 0, -128, 64, -64, 32, -96, 96, -32, 16, -112, 80, -48, 48, -80, 112, -16, 8, -120, 72, -56, 40, -88, 104, -24, 24, -104, 88, -40, 56, -72, 120, -8, 4, -124, 68, -60, 36, -92, 100, -28, 20, -108, 84, -44, 52, -76, 116, -12, 12, -116, 76, -52, 44, -84, 108, -20, 28, -100, 92, -36, 60, -68, 124, -4, 2, -126, 66, -62, 34, -94, 98, -30, 18, -110, 82, -46, 50, -78, 114, -14, 10, -118, 74, -54, 42, -86, 106, -22, 26, -102, 90, -38, 58, -70, 122, -6, 6, -122, 70, -58, 38, -90, 102, -26, 22, -106, 86, -42, 54, -74, 118, -10, 14, -114, 78, -50, 46, -82, 110, -18, 30, -98, 94, -34, 62, -66, 126, -2, 1, -127, 65, -63, 33, -95, 97, -31, 17, -111, 81, -47, 49, -79, 113, -15, 9, -119, 73, -55, 41, -87, 105, -23, 25, -103, 89, -39, 57, -71, 121, -7, 5, -123, 69, -59, 37, -91, 101, -27, 21, -107, 85, -43, 53, -75, 117, -11, 13, -115, 77, -51, 45, -83, 109, -19, 29, -99, 93, -35, 61, -67, 125, -3, 3, -125, 67, -61, 35, -93, 99, -29, 19, -109, 83, -45, 51, -77, 115, -13, 11, -117, 75, -53, 43, -85, 107, -21, 27, -101, 91, -37, 59, -69, 123, -5, 7, -121, 71, -57, 39, -89, 103, -25, 23, -105, 87, -41, 55, -73, 119, -9, 15, -113, 79, -49, 47, -81, 111, -17, 31, -97, 95, -33, 63, -65, 127, -1 };
/*    */ 
/*    */   public FillOrderChangeInputStream(InputStream in)
/*    */   {
/* 39 */     super(in);
/*    */   }
/*    */ 
/*    */   public int read(byte[] b, int off, int len)
/*    */     throws IOException
/*    */   {
/* 45 */     int result = super.read(b, off, len);
/* 46 */     if (result > 0)
/*    */     {
/* 48 */       int endpos = off + result;
/* 49 */       for (int i = off; i < endpos; i++)
/*    */       {
/* 51 */         b[i] = FLIP_TABLE[(b[i] & 0xFF)];
/*    */       }
/*    */     }
/* 54 */     return result;
/*    */   }
/*    */ 
/*    */   public int read()
/*    */     throws IOException
/*    */   {
/* 60 */     int b = super.read();
/* 61 */     if (b < 0)
/*    */     {
/* 63 */       return b;
/*    */     }
/*    */ 
/* 67 */     return FLIP_TABLE[b] & 0xFF;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.io.ccitt.FillOrderChangeInputStream
 * JD-Core Version:    0.6.2
 */