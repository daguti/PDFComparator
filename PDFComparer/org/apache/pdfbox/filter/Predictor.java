/*    */ package org.apache.pdfbox.filter;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import org.apache.pdfbox.io.IOUtils;
/*    */ 
/*    */ public class Predictor
/*    */ {
/*    */   static void decodePredictor(int predictor, int colors, int bitsPerComponent, int columns, InputStream in, OutputStream out)
/*    */     throws IOException
/*    */   {
/* 32 */     if (predictor == 1)
/*    */     {
/* 35 */       IOUtils.copy(in, out);
/*    */     }
/*    */     else
/*    */     {
/* 40 */       int bitsPerPixel = colors * bitsPerComponent;
/* 41 */       int bytesPerPixel = (bitsPerPixel + 7) / 8;
/* 42 */       int rowlength = (columns * bitsPerPixel + 7) / 8;
/* 43 */       byte[] actline = new byte[rowlength];
/* 44 */       byte[] lastline = new byte[rowlength];
/*    */ 
/* 46 */       int linepredictor = predictor;
/*    */ 
/* 48 */       while (in.available() > 0)
/*    */       {
/* 51 */         if (predictor >= 10)
/*    */         {
/* 54 */           linepredictor = in.read();
/* 55 */           if (linepredictor == -1)
/*    */           {
/* 57 */             return;
/*    */           }
/*    */ 
/* 61 */           linepredictor += 10;
/*    */         }
/*    */ 
/* 66 */         int offset = 0;
/*    */         int i;
/* 67 */         while ((offset < rowlength) && ((i = in.read(actline, offset, rowlength - offset)) != -1))
/*    */         {
/* 69 */           offset += i;
/*    */         }
/*    */ 
/* 73 */         switch (linepredictor)
/*    */         {
/*    */         case 2:
/* 78 */           if (bitsPerComponent != 8)
/*    */           {
/* 80 */             throw new IOException("TIFF-Predictor with " + bitsPerComponent + " bits per component not supported");
/*    */           }
/*    */ 
/* 84 */           for (int p = 0; p < rowlength; p++)
/*    */           {
/* 86 */             int sub = actline[p] & 0xFF;
/* 87 */             int left = p - bytesPerPixel >= 0 ? actline[(p - bytesPerPixel)] & 0xFF : 0;
/* 88 */             actline[p] = ((byte)(sub + left));
/*    */           }
/* 90 */           break;
/*    */         case 10:
/* 93 */           break;
/*    */         case 11:
/* 95 */           for (int p = 0; p < rowlength; p++)
/*    */           {
/* 97 */             int sub = actline[p];
/* 98 */             int left = p - bytesPerPixel >= 0 ? actline[(p - bytesPerPixel)] : 0;
/* 99 */             actline[p] = ((byte)(sub + left));
/*    */           }
/* 101 */           break;
/*    */         case 12:
/* 103 */           for (int p = 0; p < rowlength; p++)
/*    */           {
/* 105 */             int up = actline[p] & 0xFF;
/* 106 */             int prior = lastline[p] & 0xFF;
/* 107 */             actline[p] = ((byte)(up + prior & 0xFF));
/*    */           }
/* 109 */           break;
/*    */         case 13:
/* 111 */           for (int p = 0; p < rowlength; p++)
/*    */           {
/* 113 */             int avg = actline[p] & 0xFF;
/* 114 */             int left = p - bytesPerPixel >= 0 ? actline[(p - bytesPerPixel)] & 0xFF : 0;
/* 115 */             int up = lastline[p] & 0xFF;
/* 116 */             actline[p] = ((byte)(avg + (int)Math.floor((left + up) / 2) & 0xFF));
/*    */           }
/* 118 */           break;
/*    */         case 14:
/* 120 */           for (int p = 0; p < rowlength; p++)
/*    */           {
/* 122 */             int paeth = actline[p] & 0xFF;
/* 123 */             int a = p - bytesPerPixel >= 0 ? actline[(p - bytesPerPixel)] & 0xFF : 0;
/* 124 */             int b = lastline[p] & 0xFF;
/* 125 */             int c = p - bytesPerPixel >= 0 ? lastline[(p - bytesPerPixel)] & 0xFF : 0;
/* 126 */             int value = a + b - c;
/* 127 */             int absa = Math.abs(value - a);
/* 128 */             int absb = Math.abs(value - b);
/* 129 */             int absc = Math.abs(value - c);
/*    */ 
/* 131 */             if ((absa <= absb) && (absa <= absc))
/*    */             {
/* 133 */               actline[p] = ((byte)(paeth + a & 0xFF));
/*    */             }
/* 135 */             else if (absb <= absc)
/*    */             {
/* 137 */               actline[p] = ((byte)(paeth + b & 0xFF));
/*    */             }
/*    */             else
/*    */             {
/* 141 */               actline[p] = ((byte)(paeth + c & 0xFF));
/*    */             }
/*    */           }
/* 144 */           break;
/*    */         case 3:
/*    */         case 4:
/*    */         case 5:
/*    */         case 6:
/*    */         case 7:
/*    */         case 8:
/* 148 */         case 9: } System.arraycopy(actline, 0, lastline, 0, rowlength);
/* 149 */         out.write(actline, 0, actline.length);
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.filter.Predictor
 * JD-Core Version:    0.6.2
 */