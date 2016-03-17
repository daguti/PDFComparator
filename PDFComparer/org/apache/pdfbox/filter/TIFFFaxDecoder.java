/*      */ package org.apache.pdfbox.filter;
/*      */ 
/*      */ class TIFFFaxDecoder
/*      */ {
/*      */   private int bitPointer;
/*      */   private int bytePointer;
/*      */   private byte[] data;
/*      */   private int w;
/*      */   private int h;
/*      */   private int fillOrder;
/*   31 */   private int changingElemSize = 0;
/*      */   private int[] prevChangingElems;
/*      */   private int[] currChangingElems;
/*   36 */   private int lastChangingElement = 0;
/*      */ 
/*   38 */   private int compression = 2;
/*      */ 
/*   41 */   private int uncompressedMode = 0;
/*   42 */   private int fillBits = 0;
/*      */   private int oneD;
/*   45 */   static int[] table1 = { 0, 1, 3, 7, 15, 31, 63, 127, 255 };
/*      */ 
/*   57 */   static int[] table2 = { 0, 128, 192, 224, 240, 248, 252, 254, 255 };
/*      */ 
/*   70 */   static byte[] flipTable = { 0, -128, 64, -64, 32, -96, 96, -32, 16, -112, 80, -48, 48, -80, 112, -16, 8, -120, 72, -56, 40, -88, 104, -24, 24, -104, 88, -40, 56, -72, 120, -8, 4, -124, 68, -60, 36, -92, 100, -28, 20, -108, 84, -44, 52, -76, 116, -12, 12, -116, 76, -52, 44, -84, 108, -20, 28, -100, 92, -36, 60, -68, 124, -4, 2, -126, 66, -62, 34, -94, 98, -30, 18, -110, 82, -46, 50, -78, 114, -14, 10, -118, 74, -54, 42, -86, 106, -22, 26, -102, 90, -38, 58, -70, 122, -6, 6, -122, 70, -58, 38, -90, 102, -26, 22, -106, 86, -42, 54, -74, 118, -10, 14, -114, 78, -50, 46, -82, 110, -18, 30, -98, 94, -34, 62, -66, 126, -2, 1, -127, 65, -63, 33, -95, 97, -31, 17, -111, 81, -47, 49, -79, 113, -15, 9, -119, 73, -55, 41, -87, 105, -23, 25, -103, 89, -39, 57, -71, 121, -7, 5, -123, 69, -59, 37, -91, 101, -27, 21, -107, 85, -43, 53, -75, 117, -11, 13, -115, 77, -51, 45, -83, 109, -19, 29, -99, 93, -35, 61, -67, 125, -3, 3, -125, 67, -61, 35, -93, 99, -29, 19, -109, 83, -45, 51, -77, 115, -13, 11, -117, 75, -53, 43, -85, 107, -21, 27, -101, 91, -37, 59, -69, 123, -5, 7, -121, 71, -57, 39, -89, 103, -25, 23, -105, 87, -41, 55, -73, 119, -9, 15, -113, 79, -49, 47, -81, 111, -17, 31, -97, 95, -33, 63, -65, 127, -1 };
/*      */ 
/*  106 */   static short[] white = { 6430, 6400, 6400, 6400, 3225, 3225, 3225, 3225, 944, 944, 944, 944, 976, 976, 976, 976, 1456, 1456, 1456, 1456, 1488, 1488, 1488, 1488, 718, 718, 718, 718, 718, 718, 718, 718, 750, 750, 750, 750, 750, 750, 750, 750, 1520, 1520, 1520, 1520, 1552, 1552, 1552, 1552, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 654, 654, 654, 654, 654, 654, 654, 654, 1072, 1072, 1072, 1072, 1104, 1104, 1104, 1104, 1136, 1136, 1136, 1136, 1168, 1168, 1168, 1168, 1200, 1200, 1200, 1200, 1232, 1232, 1232, 1232, 622, 622, 622, 622, 622, 622, 622, 622, 1008, 1008, 1008, 1008, 1040, 1040, 1040, 1040, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 1712, 1712, 1712, 1712, 1744, 1744, 1744, 1744, 846, 846, 846, 846, 846, 846, 846, 846, 1264, 1264, 1264, 1264, 1296, 1296, 1296, 1296, 1328, 1328, 1328, 1328, 1360, 1360, 1360, 1360, 1392, 1392, 1392, 1392, 1424, 1424, 1424, 1424, 686, 686, 686, 686, 686, 686, 686, 686, 910, 910, 910, 910, 910, 910, 910, 910, 1968, 1968, 1968, 1968, 2000, 2000, 2000, 2000, 2032, 2032, 2032, 2032, 16, 16, 16, 16, 10257, 10257, 10257, 10257, 12305, 12305, 12305, 12305, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 878, 878, 878, 878, 878, 878, 878, 878, 1904, 1904, 1904, 1904, 1936, 1936, 1936, 1936, -18413, -18413, -16365, -16365, -14317, -14317, -10221, -10221, 590, 590, 590, 590, 590, 590, 590, 590, 782, 782, 782, 782, 782, 782, 782, 782, 1584, 1584, 1584, 1584, 1616, 1616, 1616, 1616, 1648, 1648, 1648, 1648, 1680, 1680, 1680, 1680, 814, 814, 814, 814, 814, 814, 814, 814, 1776, 1776, 1776, 1776, 1808, 1808, 1808, 1808, 1840, 1840, 1840, 1840, 1872, 1872, 1872, 1872, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, 14353, 14353, 14353, 14353, 16401, 16401, 16401, 16401, 22547, 22547, 24595, 24595, 20497, 20497, 20497, 20497, 18449, 18449, 18449, 18449, 26643, 26643, 28691, 28691, 30739, 30739, -32749, -32749, -30701, -30701, -28653, -28653, -26605, -26605, -24557, -24557, -22509, -22509, -20461, -20461, 8207, 8207, 8207, 8207, 8207, 8207, 8207, 8207, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232 };
/*      */ 
/*  366 */   static short[] additionalMakeup = { 28679, 28679, 31752, -32759, -31735, -30711, -29687, -28663, 29703, 29703, 30727, 30727, -27639, -26615, -25591, -24567 };
/*      */ 
/*  374 */   static short[] initBlack = { 3226, 6412, 200, 168, 38, 38, 134, 134, 100, 100, 100, 100, 68, 68, 68, 68 };
/*      */ 
/*  382 */   static short[] twoBitBlack = { 292, 260, 226, 226 };
/*      */ 
/*  385 */   static short[] black = { 62, 62, 30, 30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 588, 588, 588, 588, 588, 588, 588, 588, 1680, 1680, 20499, 22547, 24595, 26643, 1776, 1776, 1808, 1808, -24557, -22509, -20461, -18413, 1904, 1904, 1936, 1936, -16365, -14317, 782, 782, 782, 782, 814, 814, 814, 814, -12269, -10221, 10257, 10257, 12305, 12305, 14353, 14353, 16403, 18451, 1712, 1712, 1744, 1744, 28691, 30739, -32749, -30701, -28653, -26605, 2061, 2061, 2061, 2061, 2061, 2061, 2061, 2061, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 750, 750, 750, 750, 1616, 1616, 1648, 1648, 1424, 1424, 1456, 1456, 1488, 1488, 1520, 1520, 1840, 1840, 1872, 1872, 1968, 1968, 8209, 8209, 524, 524, 524, 524, 524, 524, 524, 524, 556, 556, 556, 556, 556, 556, 556, 556, 1552, 1552, 1584, 1584, 2000, 2000, 2032, 2032, 976, 976, 1008, 1008, 1040, 1040, 1072, 1072, 1296, 1296, 1328, 1328, 718, 718, 718, 718, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 4113, 4113, 6161, 6161, 848, 848, 880, 880, 912, 912, 944, 944, 622, 622, 622, 622, 654, 654, 654, 654, 1104, 1104, 1136, 1136, 1168, 1168, 1200, 1200, 1232, 1232, 1264, 1264, 686, 686, 686, 686, 1360, 1360, 1392, 1392, 12, 12, 12, 12, 12, 12, 12, 12, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390 };
/*      */ 
/*  516 */   static byte[] twoDCodes = { 80, 88, 23, 71, 30, 30, 62, 62, 4, 4, 4, 4, 4, 4, 4, 4, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41 };
/*      */ 
/*      */   public TIFFFaxDecoder(int fillOrder, int w, int h)
/*      */   {
/*  558 */     this.fillOrder = fillOrder;
/*  559 */     this.w = w;
/*  560 */     this.h = h;
/*      */ 
/*  562 */     this.bitPointer = 0;
/*  563 */     this.bytePointer = 0;
/*  564 */     this.prevChangingElems = new int[w + 1];
/*  565 */     this.currChangingElems = new int[w + 1];
/*      */   }
/*      */ 
/*      */   public void decode1D(byte[] buffer, byte[] compData, int startX, int height)
/*      */   {
/*  573 */     this.data = compData;
/*      */ 
/*  575 */     int lineOffset = 0;
/*  576 */     int scanlineStride = (this.w + 7) / 8;
/*      */ 
/*  578 */     this.bitPointer = 0;
/*  579 */     this.bytePointer = 0;
/*      */ 
/*  581 */     for (int i = 0; i < height; i++)
/*      */     {
/*  583 */       decodeNextScanline(buffer, lineOffset, startX);
/*  584 */       lineOffset += scanlineStride;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void decodeNextScanline(byte[] buffer, int lineOffset, int bitOffset)
/*      */   {
/*  590 */     int bits = 0; int code = 0; int isT = 0;
/*      */ 
/*  592 */     boolean isWhite = true;
/*      */ 
/*  595 */     this.changingElemSize = 0;
/*      */ 
/*  598 */     while (bitOffset < this.w) {
/*  599 */       while (isWhite)
/*      */       {
/*  601 */         int current = nextNBits(10);
/*  602 */         int entry = white[current];
/*      */ 
/*  605 */         isT = entry & 0x1;
/*  606 */         bits = entry >>> 1 & 0xF;
/*      */ 
/*  608 */         if (bits == 12)
/*      */         {
/*  610 */           int twoBits = nextLesserThan8Bits(2);
/*      */ 
/*  612 */           current = current << 2 & 0xC | twoBits;
/*  613 */           entry = additionalMakeup[current];
/*  614 */           bits = entry >>> 1 & 0x7;
/*  615 */           code = entry >>> 4 & 0xFFF;
/*  616 */           bitOffset += code;
/*      */ 
/*  618 */           updatePointer(4 - bits); } else {
/*  619 */           if (bits == 0)
/*  620 */             throw new RuntimeException("Invalid code encountered.");
/*  621 */           if (bits == 15) {
/*  622 */             throw new RuntimeException("EOL encountered in white run.");
/*      */           }
/*      */ 
/*  625 */           code = entry >>> 5 & 0x7FF;
/*  626 */           bitOffset += code;
/*      */ 
/*  628 */           updatePointer(10 - bits);
/*  629 */           if (isT == 0) {
/*  630 */             isWhite = false;
/*  631 */             this.currChangingElems[(this.changingElemSize++)] = bitOffset;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  638 */       if (bitOffset == this.w) {
/*  639 */         if (this.compression == 2) {
/*  640 */           advancePointer();
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  645 */         while (!isWhite)
/*      */         {
/*  647 */           int current = nextLesserThan8Bits(4);
/*  648 */           int entry = initBlack[current];
/*      */ 
/*  651 */           isT = entry & 0x1;
/*  652 */           bits = entry >>> 1 & 0xF;
/*  653 */           code = entry >>> 5 & 0x7FF;
/*      */ 
/*  655 */           if (code == 100) {
/*  656 */             current = nextNBits(9);
/*  657 */             entry = black[current];
/*      */ 
/*  660 */             isT = entry & 0x1;
/*  661 */             bits = entry >>> 1 & 0xF;
/*  662 */             code = entry >>> 5 & 0x7FF;
/*      */ 
/*  664 */             if (bits == 12)
/*      */             {
/*  666 */               updatePointer(5);
/*  667 */               current = nextLesserThan8Bits(4);
/*  668 */               entry = additionalMakeup[current];
/*  669 */               bits = entry >>> 1 & 0x7;
/*  670 */               code = entry >>> 4 & 0xFFF;
/*      */ 
/*  672 */               setToBlack(buffer, lineOffset, bitOffset, code);
/*  673 */               bitOffset += code;
/*      */ 
/*  675 */               updatePointer(4 - bits); } else {
/*  676 */               if (bits == 15)
/*      */               {
/*  678 */                 throw new RuntimeException("EOL encountered in black run.");
/*      */               }
/*  680 */               setToBlack(buffer, lineOffset, bitOffset, code);
/*  681 */               bitOffset += code;
/*      */ 
/*  683 */               updatePointer(9 - bits);
/*  684 */               if (isT == 0) {
/*  685 */                 isWhite = true;
/*  686 */                 this.currChangingElems[(this.changingElemSize++)] = bitOffset;
/*      */               }
/*      */             }
/*  689 */           } else if (code == 200)
/*      */           {
/*  691 */             current = nextLesserThan8Bits(2);
/*  692 */             entry = twoBitBlack[current];
/*  693 */             code = entry >>> 5 & 0x7FF;
/*  694 */             bits = entry >>> 1 & 0xF;
/*      */ 
/*  696 */             setToBlack(buffer, lineOffset, bitOffset, code);
/*  697 */             bitOffset += code;
/*      */ 
/*  699 */             updatePointer(2 - bits);
/*  700 */             isWhite = true;
/*  701 */             this.currChangingElems[(this.changingElemSize++)] = bitOffset;
/*      */           }
/*      */           else {
/*  704 */             setToBlack(buffer, lineOffset, bitOffset, code);
/*  705 */             bitOffset += code;
/*      */ 
/*  707 */             updatePointer(4 - bits);
/*  708 */             isWhite = true;
/*  709 */             this.currChangingElems[(this.changingElemSize++)] = bitOffset;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  714 */         if (bitOffset == this.w) {
/*  715 */           if (this.compression == 2) {
/*  716 */             advancePointer();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  722 */     this.currChangingElems[(this.changingElemSize++)] = bitOffset;
/*      */   }
/*      */ 
/*      */   public void decode2D(byte[] buffer, byte[] compData, int startX, int height, long tiffT4Options)
/*      */   {
/*  732 */     this.data = compData;
/*  733 */     this.compression = 3;
/*      */ 
/*  735 */     this.bitPointer = 0;
/*  736 */     this.bytePointer = 0;
/*      */ 
/*  738 */     int scanlineStride = (this.w + 7) / 8;
/*      */ 
/*  741 */     int[] b = new int[2];
/*      */ 
/*  744 */     int currIndex = 0;
/*      */ 
/*  753 */     this.oneD = ((int)(tiffT4Options & 1L));
/*  754 */     this.uncompressedMode = ((int)((tiffT4Options & 0x2) >> 1));
/*  755 */     this.fillBits = ((int)((tiffT4Options & 0x4) >> 2));
/*      */ 
/*  758 */     if (readEOL() != 1) {
/*  759 */       throw new RuntimeException("First scanline must be 1D encoded.");
/*      */     }
/*      */ 
/*  762 */     int lineOffset = 0;
/*      */ 
/*  767 */     decodeNextScanline(buffer, lineOffset, startX);
/*  768 */     lineOffset += scanlineStride;
/*      */ 
/*  770 */     for (int lines = 1; lines < height; lines++)
/*      */     {
/*  774 */       if (readEOL() == 0)
/*      */       {
/*  779 */         int[] temp = this.prevChangingElems;
/*  780 */         this.prevChangingElems = this.currChangingElems;
/*  781 */         this.currChangingElems = temp;
/*  782 */         currIndex = 0;
/*      */ 
/*  785 */         int a0 = -1;
/*  786 */         boolean isWhite = true;
/*  787 */         int bitOffset = startX;
/*      */ 
/*  789 */         this.lastChangingElement = 0;
/*      */ 
/*  791 */         while (bitOffset < this.w)
/*      */         {
/*  793 */           getNextChangingElement(a0, isWhite, b);
/*      */ 
/*  795 */           int b1 = b[0];
/*  796 */           int b2 = b[1];
/*      */ 
/*  799 */           int entry = nextLesserThan8Bits(7);
/*      */ 
/*  802 */           entry = twoDCodes[entry] & 0xFF;
/*      */ 
/*  805 */           int code = (entry & 0x78) >>> 3;
/*  806 */           int bits = entry & 0x7;
/*      */ 
/*  808 */           if (code == 0) {
/*  809 */             if (!isWhite) {
/*  810 */               setToBlack(buffer, lineOffset, bitOffset, b2 - bitOffset);
/*      */             }
/*      */ 
/*  813 */             bitOffset = a0 = b2;
/*      */ 
/*  816 */             updatePointer(7 - bits);
/*  817 */           } else if (code == 1)
/*      */           {
/*  819 */             updatePointer(7 - bits);
/*      */ 
/*  823 */             if (isWhite) {
/*  824 */               int number = decodeWhiteCodeWord();
/*  825 */               bitOffset += number;
/*  826 */               this.currChangingElems[(currIndex++)] = bitOffset;
/*      */ 
/*  828 */               number = decodeBlackCodeWord();
/*  829 */               setToBlack(buffer, lineOffset, bitOffset, number);
/*  830 */               bitOffset += number;
/*  831 */               this.currChangingElems[(currIndex++)] = bitOffset;
/*      */             } else {
/*  833 */               int number = decodeBlackCodeWord();
/*  834 */               setToBlack(buffer, lineOffset, bitOffset, number);
/*  835 */               bitOffset += number;
/*  836 */               this.currChangingElems[(currIndex++)] = bitOffset;
/*      */ 
/*  838 */               number = decodeWhiteCodeWord();
/*  839 */               bitOffset += number;
/*  840 */               this.currChangingElems[(currIndex++)] = bitOffset;
/*      */             }
/*      */ 
/*  843 */             a0 = bitOffset;
/*  844 */           } else if (code <= 8)
/*      */           {
/*  846 */             int a1 = b1 + (code - 5);
/*      */ 
/*  848 */             this.currChangingElems[(currIndex++)] = a1;
/*      */ 
/*  852 */             if (!isWhite) {
/*  853 */               setToBlack(buffer, lineOffset, bitOffset, a1 - bitOffset);
/*      */             }
/*      */ 
/*  856 */             bitOffset = a0 = a1;
/*  857 */             isWhite = !isWhite;
/*      */ 
/*  859 */             updatePointer(7 - bits);
/*      */           } else {
/*  861 */             throw new RuntimeException("Invalid code encountered while decoding 2D group 3 compressed data.");
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  867 */         this.currChangingElems[(currIndex++)] = bitOffset;
/*  868 */         this.changingElemSize = currIndex;
/*      */       }
/*      */       else {
/*  871 */         decodeNextScanline(buffer, lineOffset, startX);
/*      */       }
/*      */ 
/*  874 */       lineOffset += scanlineStride;
/*      */     }
/*      */   }
/*      */ 
/*      */   public synchronized void decodeT6(byte[] buffer, byte[] compData, int startX, int height, long tiffT6Options, boolean encodedByteAlign)
/*      */   {
/*  884 */     this.data = compData;
/*  885 */     this.compression = 4;
/*      */ 
/*  887 */     this.bitPointer = 0;
/*  888 */     this.bytePointer = 0;
/*      */ 
/*  890 */     int scanlineStride = (this.w + 7) / 8;
/*      */ 
/*  899 */     int[] b = new int[2];
/*      */ 
/*  904 */     this.uncompressedMode = ((int)((tiffT6Options & 0x2) >> 1));
/*      */ 
/*  907 */     int[] cce = this.currChangingElems;
/*      */ 
/*  912 */     this.changingElemSize = 0;
/*  913 */     cce[(this.changingElemSize++)] = this.w;
/*  914 */     cce[(this.changingElemSize++)] = this.w;
/*      */ 
/*  916 */     int lineOffset = 0;
/*      */ 
/*  919 */     for (int lines = 0; lines < height; lines++) {
/*  920 */       if ((encodedByteAlign) && (this.bitPointer != 0))
/*      */       {
/*  922 */         this.bitPointer = 0;
/*  923 */         this.bytePointer += 1;
/*      */       }
/*      */ 
/*  926 */       int a0 = -1;
/*  927 */       boolean isWhite = true;
/*      */ 
/*  932 */       int[] temp = this.prevChangingElems;
/*  933 */       this.prevChangingElems = this.currChangingElems;
/*  934 */       cce = this.currChangingElems = temp;
/*  935 */       int currIndex = 0;
/*      */ 
/*  938 */       int bitOffset = startX;
/*      */ 
/*  941 */       this.lastChangingElement = 0;
/*      */ 
/*  944 */       while (bitOffset < this.w)
/*      */       {
/*  946 */         getNextChangingElement(a0, isWhite, b);
/*  947 */         int b1 = b[0];
/*  948 */         int b2 = b[1];
/*      */ 
/*  951 */         int entry = nextLesserThan8Bits(7);
/*      */ 
/*  953 */         entry = twoDCodes[entry] & 0xFF;
/*      */ 
/*  956 */         int code = (entry & 0x78) >>> 3;
/*  957 */         int bits = entry & 0x7;
/*      */ 
/*  959 */         if (code == 0)
/*      */         {
/*  961 */           if (!isWhite) {
/*  962 */             setToBlack(buffer, lineOffset, bitOffset, b2 - bitOffset);
/*      */           }
/*      */ 
/*  965 */           bitOffset = a0 = b2;
/*      */ 
/*  968 */           updatePointer(7 - bits);
/*  969 */         } else if (code == 1)
/*      */         {
/*  971 */           updatePointer(7 - bits);
/*      */ 
/*  975 */           if (isWhite)
/*      */           {
/*  977 */             int number = decodeWhiteCodeWord();
/*  978 */             bitOffset += number;
/*  979 */             cce[(currIndex++)] = bitOffset;
/*      */ 
/*  981 */             number = decodeBlackCodeWord();
/*  982 */             setToBlack(buffer, lineOffset, bitOffset, number);
/*  983 */             bitOffset += number;
/*  984 */             cce[(currIndex++)] = bitOffset;
/*      */           }
/*      */           else {
/*  987 */             int number = decodeBlackCodeWord();
/*  988 */             setToBlack(buffer, lineOffset, bitOffset, number);
/*  989 */             bitOffset += number;
/*  990 */             cce[(currIndex++)] = bitOffset;
/*      */ 
/*  992 */             number = decodeWhiteCodeWord();
/*  993 */             bitOffset += number;
/*  994 */             cce[(currIndex++)] = bitOffset;
/*      */           }
/*      */ 
/*  997 */           a0 = bitOffset;
/*  998 */         } else if (code <= 8) {
/*  999 */           int a1 = b1 + (code - 5);
/* 1000 */           cce[(currIndex++)] = a1;
/*      */ 
/* 1004 */           if (!isWhite) {
/* 1005 */             setToBlack(buffer, lineOffset, bitOffset, a1 - bitOffset);
/*      */           }
/*      */ 
/* 1008 */           bitOffset = a0 = a1;
/* 1009 */           isWhite = !isWhite;
/*      */ 
/* 1011 */           updatePointer(7 - bits);
/* 1012 */         } else if (code == 11) {
/* 1013 */           if (nextLesserThan8Bits(3) != 7) {
/* 1014 */             throw new RuntimeException("Invalid code encountered while decoding 2D group 4 compressed data.");
/*      */           }
/*      */ 
/* 1017 */           int zeros = 0;
/* 1018 */           boolean exit = false;
/*      */ 
/* 1020 */           while (!exit) {
/* 1021 */             while (nextLesserThan8Bits(1) != 1) {
/* 1022 */               zeros++;
/*      */             }
/*      */ 
/* 1025 */             if (zeros > 5)
/*      */             {
/* 1029 */               zeros -= 6;
/*      */ 
/* 1031 */               if ((!isWhite) && (zeros > 0)) {
/* 1032 */                 cce[(currIndex++)] = bitOffset;
/*      */               }
/*      */ 
/* 1036 */               bitOffset += zeros;
/* 1037 */               if (zeros > 0)
/*      */               {
/* 1039 */                 isWhite = true;
/*      */               }
/*      */ 
/* 1044 */               if (nextLesserThan8Bits(1) == 0) {
/* 1045 */                 if (!isWhite) {
/* 1046 */                   cce[(currIndex++)] = bitOffset;
/*      */                 }
/* 1048 */                 isWhite = true;
/*      */               } else {
/* 1050 */                 if (isWhite) {
/* 1051 */                   cce[(currIndex++)] = bitOffset;
/*      */                 }
/* 1053 */                 isWhite = false;
/*      */               }
/*      */ 
/* 1056 */               exit = true;
/*      */             }
/*      */ 
/* 1059 */             if (zeros == 5) {
/* 1060 */               if (!isWhite) {
/* 1061 */                 cce[(currIndex++)] = bitOffset;
/*      */               }
/* 1063 */               bitOffset += zeros;
/*      */ 
/* 1066 */               isWhite = true;
/*      */             } else {
/* 1068 */               bitOffset += zeros;
/*      */ 
/* 1070 */               cce[(currIndex++)] = bitOffset;
/* 1071 */               setToBlack(buffer, lineOffset, bitOffset, 1);
/* 1072 */               bitOffset++;
/*      */ 
/* 1075 */               isWhite = false;
/*      */             }
/*      */           }
/*      */         }
/*      */         else {
/* 1080 */           throw new RuntimeException("Invalid code encountered while decoding 2D group 4 compressed data.");
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1086 */       cce[(currIndex++)] = bitOffset;
/*      */ 
/* 1089 */       this.changingElemSize = currIndex;
/*      */ 
/* 1091 */       lineOffset += scanlineStride;
/*      */     }
/*      */   }
/*      */ 
/*      */   private void setToBlack(byte[] buffer, int lineOffset, int bitOffset, int numBits)
/*      */   {
/* 1098 */     int bitNum = 8 * lineOffset + bitOffset;
/* 1099 */     int lastBit = bitNum + numBits;
/*      */ 
/* 1101 */     int byteNum = bitNum >> 3;
/*      */ 
/* 1104 */     int shift = bitNum & 0x7;
/* 1105 */     if (shift > 0) {
/* 1106 */       int maskVal = 1 << 7 - shift;
/* 1107 */       byte val = buffer[byteNum];
/* 1108 */       while ((maskVal > 0) && (bitNum < lastBit)) {
/* 1109 */         val = (byte)(val | maskVal);
/* 1110 */         maskVal >>= 1;
/* 1111 */         bitNum++;
/*      */       }
/* 1113 */       buffer[byteNum] = val;
/*      */     }
/*      */ 
/* 1117 */     byteNum = bitNum >> 3;
/* 1118 */     while (bitNum < lastBit - 7) {
/* 1119 */       buffer[(byteNum++)] = -1;
/* 1120 */       bitNum += 8;
/*      */     }
/*      */ 
/* 1124 */     while (bitNum < lastBit) {
/* 1125 */       byteNum = bitNum >> 3;
/*      */       int tmp132_130 = byteNum;
/*      */       byte[] tmp132_129 = buffer; tmp132_129[tmp132_130] = ((byte)(tmp132_129[tmp132_130] | 1 << 7 - (bitNum & 0x7)));
/* 1127 */       bitNum++;
/*      */     }
/*      */   }
/*      */ 
/*      */   private int decodeWhiteCodeWord()
/*      */   {
/* 1133 */     int code = -1;
/* 1134 */     int runLength = 0;
/* 1135 */     boolean isWhite = true;
/*      */ 
/* 1137 */     while (isWhite) {
/* 1138 */       int current = nextNBits(10);
/* 1139 */       int entry = white[current];
/*      */ 
/* 1142 */       int isT = entry & 0x1;
/* 1143 */       int bits = entry >>> 1 & 0xF;
/*      */ 
/* 1145 */       if (bits == 12)
/*      */       {
/* 1147 */         int twoBits = nextLesserThan8Bits(2);
/*      */ 
/* 1149 */         current = current << 2 & 0xC | twoBits;
/* 1150 */         entry = additionalMakeup[current];
/* 1151 */         bits = entry >>> 1 & 0x7;
/* 1152 */         code = entry >>> 4 & 0xFFF;
/* 1153 */         runLength += code;
/* 1154 */         updatePointer(4 - bits); } else {
/* 1155 */         if (bits == 0)
/* 1156 */           throw new RuntimeException("Invalid code encountered.");
/* 1157 */         if (bits == 15) {
/* 1158 */           throw new RuntimeException("EOL encountered in white run.");
/*      */         }
/*      */ 
/* 1161 */         code = entry >>> 5 & 0x7FF;
/* 1162 */         runLength += code;
/* 1163 */         updatePointer(10 - bits);
/* 1164 */         if (isT == 0) {
/* 1165 */           isWhite = false;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1170 */     return runLength;
/*      */   }
/*      */ 
/*      */   private int decodeBlackCodeWord()
/*      */   {
/* 1175 */     int code = -1;
/* 1176 */     int runLength = 0;
/* 1177 */     boolean isWhite = false;
/*      */ 
/* 1179 */     while (!isWhite) {
/* 1180 */       int current = nextLesserThan8Bits(4);
/* 1181 */       int entry = initBlack[current];
/*      */ 
/* 1184 */       int isT = entry & 0x1;
/* 1185 */       int bits = entry >>> 1 & 0xF;
/* 1186 */       code = entry >>> 5 & 0x7FF;
/*      */ 
/* 1188 */       if (code == 100) {
/* 1189 */         current = nextNBits(9);
/* 1190 */         entry = black[current];
/*      */ 
/* 1193 */         isT = entry & 0x1;
/* 1194 */         bits = entry >>> 1 & 0xF;
/* 1195 */         code = entry >>> 5 & 0x7FF;
/*      */ 
/* 1197 */         if (bits == 12)
/*      */         {
/* 1199 */           updatePointer(5);
/* 1200 */           current = nextLesserThan8Bits(4);
/* 1201 */           entry = additionalMakeup[current];
/* 1202 */           bits = entry >>> 1 & 0x7;
/* 1203 */           code = entry >>> 4 & 0xFFF;
/* 1204 */           runLength += code;
/*      */ 
/* 1206 */           updatePointer(4 - bits); } else {
/* 1207 */           if (bits == 15)
/*      */           {
/* 1209 */             throw new RuntimeException("EOL encountered in black run.");
/*      */           }
/* 1211 */           runLength += code;
/* 1212 */           updatePointer(9 - bits);
/* 1213 */           if (isT == 0)
/* 1214 */             isWhite = true;
/*      */         }
/*      */       }
/* 1217 */       else if (code == 200)
/*      */       {
/* 1219 */         current = nextLesserThan8Bits(2);
/* 1220 */         entry = twoBitBlack[current];
/* 1221 */         code = entry >>> 5 & 0x7FF;
/* 1222 */         runLength += code;
/* 1223 */         bits = entry >>> 1 & 0xF;
/* 1224 */         updatePointer(2 - bits);
/* 1225 */         isWhite = true;
/*      */       }
/*      */       else {
/* 1228 */         runLength += code;
/* 1229 */         updatePointer(4 - bits);
/* 1230 */         isWhite = true;
/*      */       }
/*      */     }
/*      */ 
/* 1234 */     return runLength;
/*      */   }
/*      */ 
/*      */   private int readEOL() {
/* 1238 */     if (this.fillBits == 0) {
/* 1239 */       if (nextNBits(12) != 1)
/* 1240 */         throw new RuntimeException("Scanline must begin with EOL.");
/*      */     }
/* 1242 */     else if (this.fillBits == 1)
/*      */     {
/* 1248 */       int bitsLeft = 8 - this.bitPointer;
/*      */ 
/* 1250 */       if (nextNBits(bitsLeft) != 0) {
/* 1251 */         throw new RuntimeException("All fill bits preceding EOL code must be 0.");
/*      */       }
/*      */ 
/* 1258 */       if ((bitsLeft < 4) && 
/* 1259 */         (nextNBits(8) != 0))
/* 1260 */         throw new RuntimeException("All fill bits preceding EOL code must be 0.");
/*      */       int n;
/* 1268 */       while ((n = nextNBits(8)) != 1)
/*      */       {
/* 1271 */         if (n != 0) {
/* 1272 */           throw new RuntimeException("All fill bits preceding EOL code must be 0.");
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1278 */     if (this.oneD == 0) {
/* 1279 */       return 1;
/*      */     }
/*      */ 
/* 1283 */     return nextLesserThan8Bits(1);
/*      */   }
/*      */ 
/*      */   private void getNextChangingElement(int a0, boolean isWhite, int[] ret)
/*      */   {
/* 1289 */     int[] pce = this.prevChangingElems;
/* 1290 */     int ces = this.changingElemSize;
/*      */ 
/* 1295 */     int start = this.lastChangingElement > 0 ? this.lastChangingElement - 1 : 0;
/* 1296 */     if (isWhite)
/* 1297 */       start &= -2;
/*      */     else {
/* 1299 */       start |= 1;
/*      */     }
/*      */ 
/* 1302 */     for (int i = start; 
/* 1303 */       i < ces; i += 2) {
/* 1304 */       int temp = pce[i];
/* 1305 */       if (temp > a0) {
/* 1306 */         this.lastChangingElement = i;
/* 1307 */         ret[0] = temp;
/* 1308 */         break;
/*      */       }
/*      */     }
/*      */ 
/* 1312 */     if (i + 1 < ces)
/* 1313 */       ret[1] = pce[(i + 1)];
/*      */   }
/*      */ 
/*      */   private int nextNBits(int bitsToGet)
/*      */   {
/* 1319 */     int l = this.data.length - 1;
/* 1320 */     int bp = this.bytePointer;
/*      */     byte next2next;
/* 1322 */     if (this.fillOrder == 1) {
/* 1323 */       byte b = this.data[bp];
/*      */       byte next2next;
/* 1325 */       if (bp == l) {
/* 1326 */         byte next = 0;
/* 1327 */         next2next = 0;
/*      */       }
/*      */       else
/*      */       {
/*      */         byte next2next;
/* 1328 */         if (bp + 1 == l) {
/* 1329 */           byte next = this.data[(bp + 1)];
/* 1330 */           next2next = 0;
/*      */         } else {
/* 1332 */           byte next = this.data[(bp + 1)];
/* 1333 */           next2next = this.data[(bp + 2)];
/*      */         }
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*      */       byte next2next;
/* 1335 */       if (this.fillOrder == 2) {
/* 1336 */         byte b = flipTable[(this.data[bp] & 0xFF)];
/*      */         byte next2next;
/* 1338 */         if (bp == l) {
/* 1339 */           byte next = 0;
/* 1340 */           next2next = 0;
/*      */         }
/*      */         else
/*      */         {
/*      */           byte next2next;
/* 1341 */           if (bp + 1 == l) {
/* 1342 */             byte next = flipTable[(this.data[(bp + 1)] & 0xFF)];
/* 1343 */             next2next = 0;
/*      */           } else {
/* 1345 */             byte next = flipTable[(this.data[(bp + 1)] & 0xFF)];
/* 1346 */             next2next = flipTable[(this.data[(bp + 2)] & 0xFF)];
/*      */           }
/*      */         }
/*      */       } else { throw new RuntimeException("TIFF_FILL_ORDER tag must be either 1 or 2."); }
/*      */ 
/*      */     }
/*      */     byte next2next;
/*      */     byte next;
/*      */     byte b;
/* 1352 */     int bitsLeft = 8 - this.bitPointer;
/* 1353 */     int bitsFromNextByte = bitsToGet - bitsLeft;
/* 1354 */     int bitsFromNext2NextByte = 0;
/* 1355 */     if (bitsFromNextByte > 8) {
/* 1356 */       bitsFromNext2NextByte = bitsFromNextByte - 8;
/* 1357 */       bitsFromNextByte = 8;
/*      */     }
/*      */ 
/* 1360 */     this.bytePointer += 1;
/*      */ 
/* 1362 */     int i1 = (b & table1[bitsLeft]) << bitsToGet - bitsLeft;
/* 1363 */     int i2 = (next & table2[bitsFromNextByte]) >>> 8 - bitsFromNextByte;
/*      */ 
/* 1365 */     int i3 = 0;
/* 1366 */     if (bitsFromNext2NextByte != 0) {
/* 1367 */       i2 <<= bitsFromNext2NextByte;
/* 1368 */       i3 = (next2next & table2[bitsFromNext2NextByte]) >>> 8 - bitsFromNext2NextByte;
/*      */ 
/* 1370 */       i2 |= i3;
/* 1371 */       this.bytePointer += 1;
/* 1372 */       this.bitPointer = bitsFromNext2NextByte;
/*      */     }
/* 1374 */     else if (bitsFromNextByte == 8) {
/* 1375 */       this.bitPointer = 0;
/* 1376 */       this.bytePointer += 1;
/*      */     } else {
/* 1378 */       this.bitPointer = bitsFromNextByte;
/*      */     }
/*      */ 
/* 1382 */     int i = i1 | i2;
/* 1383 */     return i;
/*      */   }
/*      */ 
/*      */   private int nextLesserThan8Bits(int bitsToGet)
/*      */   {
/* 1388 */     int l = this.data.length - 1;
/* 1389 */     int bp = this.bytePointer;
/*      */     byte next;
/* 1391 */     if (this.fillOrder == 1) {
/* 1392 */       byte b = this.data[bp];
/*      */       byte next;
/* 1393 */       if (bp == l)
/* 1394 */         next = 0;
/*      */       else
/* 1396 */         next = this.data[(bp + 1)];
/*      */     }
/*      */     else
/*      */     {
/*      */       byte next;
/* 1398 */       if (this.fillOrder == 2) {
/* 1399 */         byte b = flipTable[(this.data[bp] & 0xFF)];
/*      */         byte next;
/* 1400 */         if (bp == l)
/* 1401 */           next = 0;
/*      */         else
/* 1403 */           next = flipTable[(this.data[(bp + 1)] & 0xFF)];
/*      */       }
/*      */       else {
/* 1406 */         throw new RuntimeException("TIFF_FILL_ORDER tag must be either 1 or 2.");
/*      */       }
/*      */     }
/*      */     byte next;
/*      */     byte b;
/* 1409 */     int bitsLeft = 8 - this.bitPointer;
/* 1410 */     int bitsFromNextByte = bitsToGet - bitsLeft;
/*      */ 
/* 1412 */     int shift = bitsLeft - bitsToGet;
/*      */     int i1;
/* 1414 */     if (shift >= 0) {
/* 1415 */       int i1 = (b & table1[bitsLeft]) >>> shift;
/* 1416 */       this.bitPointer += bitsToGet;
/* 1417 */       if (this.bitPointer == 8) {
/* 1418 */         this.bitPointer = 0;
/* 1419 */         this.bytePointer += 1;
/*      */       }
/*      */     } else {
/* 1422 */       i1 = (b & table1[bitsLeft]) << -shift;
/* 1423 */       int i2 = (next & table2[bitsFromNextByte]) >>> 8 - bitsFromNextByte;
/*      */ 
/* 1425 */       i1 |= i2;
/* 1426 */       this.bytePointer += 1;
/* 1427 */       this.bitPointer = bitsFromNextByte;
/*      */     }
/*      */ 
/* 1430 */     return i1;
/*      */   }
/*      */ 
/*      */   private void updatePointer(int bitsToMoveBack)
/*      */   {
/* 1435 */     int i = this.bitPointer - bitsToMoveBack;
/*      */ 
/* 1437 */     if (i < 0) {
/* 1438 */       this.bytePointer -= 1;
/* 1439 */       this.bitPointer = (8 + i);
/*      */     } else {
/* 1441 */       this.bitPointer = i;
/*      */     }
/*      */   }
/*      */ 
/*      */   private boolean advancePointer()
/*      */   {
/* 1447 */     if (this.bitPointer != 0) {
/* 1448 */       this.bytePointer += 1;
/* 1449 */       this.bitPointer = 0;
/*      */     }
/*      */ 
/* 1452 */     return true;
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.filter.TIFFFaxDecoder
 * JD-Core Version:    0.6.2
 */