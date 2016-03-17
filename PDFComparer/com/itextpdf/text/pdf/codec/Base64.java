/*      */ package com.itextpdf.text.pdf.codec;
/*      */ 
/*      */ import com.itextpdf.text.error_messages.MessageLocalization;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.BufferedOutputStream;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.FilterInputStream;
/*      */ import java.io.FilterOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.io.Serializable;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.util.zip.GZIPInputStream;
/*      */ import java.util.zip.GZIPOutputStream;
/*      */ 
/*      */ public class Base64
/*      */ {
/*      */   public static final int NO_OPTIONS = 0;
/*      */   public static final int ENCODE = 1;
/*      */   public static final int DECODE = 0;
/*      */   public static final int GZIP = 2;
/*      */   public static final int DONT_BREAK_LINES = 8;
/*      */   public static final int URL_SAFE = 16;
/*      */   public static final int ORDERED = 32;
/*      */   private static final int MAX_LINE_LENGTH = 76;
/*      */   private static final byte EQUALS_SIGN = 61;
/*      */   private static final byte NEW_LINE = 10;
/*      */   private static final String PREFERRED_ENCODING = "UTF-8";
/*      */   private static final byte WHITE_SPACE_ENC = -5;
/*      */   private static final byte EQUALS_SIGN_ENC = -1;
/*  192 */   private static final byte[] _STANDARD_ALPHABET = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
/*      */ 
/*  211 */   private static final byte[] _STANDARD_DECODABET = { -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, -9, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, -9, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9 };
/*      */ 
/*  254 */   private static final byte[] _URL_SAFE_ALPHABET = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95 };
/*      */ 
/*  271 */   private static final byte[] _URL_SAFE_DECODABET = { -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, 63, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9 };
/*      */ 
/*  318 */   private static final byte[] _ORDERED_ALPHABET = { 45, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 95, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122 };
/*      */ 
/*  337 */   private static final byte[] _ORDERED_DECODABET = { -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 0, -9, -9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, -9, -9, -9, -1, -9, -9, -9, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, -9, -9, -9, -9, 37, -9, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, -9, -9, -9, -9 };
/*      */ 
/*      */   private static final byte[] getAlphabet(int options)
/*      */   {
/*  388 */     if ((options & 0x10) == 16) return _URL_SAFE_ALPHABET;
/*  389 */     if ((options & 0x20) == 32) return _ORDERED_ALPHABET;
/*  390 */     return _STANDARD_ALPHABET;
/*      */   }
/*      */ 
/*      */   private static final byte[] getDecodabet(int options)
/*      */   {
/*  403 */     if ((options & 0x10) == 16) return _URL_SAFE_DECODABET;
/*  404 */     if ((options & 0x20) == 32) return _ORDERED_DECODABET;
/*  405 */     return _STANDARD_DECODABET;
/*      */   }
/*      */ 
/*      */   private static final void usage(String msg)
/*      */   {
/*  446 */     System.err.println(msg);
/*  447 */     System.err.println("Usage: java Base64 -e|-d inputfile outputfile");
/*      */   }
/*      */ 
/*      */   private static byte[] encode3to4(byte[] b4, byte[] threeBytes, int numSigBytes, int options)
/*      */   {
/*  470 */     encode3to4(threeBytes, 0, numSigBytes, b4, 0, options);
/*  471 */     return b4;
/*      */   }
/*      */ 
/*      */   private static byte[] encode3to4(byte[] source, int srcOffset, int numSigBytes, byte[] destination, int destOffset, int options)
/*      */   {
/*  501 */     byte[] ALPHABET = getAlphabet(options);
/*      */ 
/*  514 */     int inBuff = (numSigBytes > 0 ? source[srcOffset] << 24 >>> 8 : 0) | (numSigBytes > 1 ? source[(srcOffset + 1)] << 24 >>> 16 : 0) | (numSigBytes > 2 ? source[(srcOffset + 2)] << 24 >>> 24 : 0);
/*      */ 
/*  518 */     switch (numSigBytes) {
/*      */     case 3:
/*  520 */       destination[destOffset] = ALPHABET[(inBuff >>> 18)];
/*  521 */       destination[(destOffset + 1)] = ALPHABET[(inBuff >>> 12 & 0x3F)];
/*  522 */       destination[(destOffset + 2)] = ALPHABET[(inBuff >>> 6 & 0x3F)];
/*  523 */       destination[(destOffset + 3)] = ALPHABET[(inBuff & 0x3F)];
/*  524 */       return destination;
/*      */     case 2:
/*  527 */       destination[destOffset] = ALPHABET[(inBuff >>> 18)];
/*  528 */       destination[(destOffset + 1)] = ALPHABET[(inBuff >>> 12 & 0x3F)];
/*  529 */       destination[(destOffset + 2)] = ALPHABET[(inBuff >>> 6 & 0x3F)];
/*  530 */       destination[(destOffset + 3)] = 61;
/*  531 */       return destination;
/*      */     case 1:
/*  534 */       destination[destOffset] = ALPHABET[(inBuff >>> 18)];
/*  535 */       destination[(destOffset + 1)] = ALPHABET[(inBuff >>> 12 & 0x3F)];
/*  536 */       destination[(destOffset + 2)] = 61;
/*  537 */       destination[(destOffset + 3)] = 61;
/*  538 */       return destination;
/*      */     }
/*      */ 
/*  541 */     return destination;
/*      */   }
/*      */ 
/*      */   public static String encodeObject(Serializable serializableObject)
/*      */   {
/*  559 */     return encodeObject(serializableObject, 0);
/*      */   }
/*      */ 
/*      */   public static String encodeObject(Serializable serializableObject, int options)
/*      */   {
/*  589 */     ByteArrayOutputStream baos = null;
/*  590 */     OutputStream b64os = null;
/*  591 */     ObjectOutputStream oos = null;
/*  592 */     GZIPOutputStream gzos = null;
/*      */ 
/*  595 */     int gzip = options & 0x2;
/*  596 */     int dontBreakLines = options & 0x8;
/*      */     try
/*      */     {
/*  600 */       baos = new ByteArrayOutputStream();
/*  601 */       b64os = new OutputStream(baos, 0x1 | options);
/*      */ 
/*  604 */       if (gzip == 2) {
/*  605 */         gzos = new GZIPOutputStream(b64os);
/*  606 */         oos = new ObjectOutputStream(gzos);
/*      */       }
/*      */       else {
/*  609 */         oos = new ObjectOutputStream(b64os);
/*      */       }
/*  611 */       oos.writeObject(serializableObject);
/*      */     }
/*      */     catch (IOException e) {
/*  614 */       e.printStackTrace();
/*  615 */       return null;
/*      */     } finally {
/*      */       try {
/*  618 */         oos.close(); } catch (Exception e) {
/*      */       }try { gzos.close(); } catch (Exception e) {
/*      */       }try { b64os.close(); } catch (Exception e) {
/*      */       }try { baos.close();
/*      */       } catch (Exception e) {
/*      */       }
/*      */     }
/*      */     try {
/*  626 */       return new String(baos.toByteArray(), "UTF-8");
/*      */     } catch (UnsupportedEncodingException uue) {
/*      */     }
/*  629 */     return new String(baos.toByteArray());
/*      */   }
/*      */ 
/*      */   public static String encodeBytes(byte[] source)
/*      */   {
/*  644 */     return encodeBytes(source, 0, source.length, 0);
/*      */   }
/*      */ 
/*      */   public static String encodeBytes(byte[] source, int options)
/*      */   {
/*  670 */     return encodeBytes(source, 0, source.length, options);
/*      */   }
/*      */ 
/*      */   public static String encodeBytes(byte[] source, int off, int len)
/*      */   {
/*  684 */     return encodeBytes(source, off, len, 0);
/*      */   }
/*      */ 
/*      */   public static String encodeBytes(byte[] source, int off, int len, int options)
/*      */   {
/*  714 */     int dontBreakLines = options & 0x8;
/*  715 */     int gzip = options & 0x2;
/*      */ 
/*  718 */     if (gzip == 2) {
/*  719 */       ByteArrayOutputStream baos = null;
/*  720 */       GZIPOutputStream gzos = null;
/*  721 */       OutputStream b64os = null;
/*      */       try
/*      */       {
/*  726 */         baos = new ByteArrayOutputStream();
/*  727 */         b64os = new OutputStream(baos, 0x1 | options);
/*  728 */         gzos = new GZIPOutputStream(b64os);
/*      */ 
/*  730 */         gzos.write(source, off, len);
/*  731 */         gzos.close();
/*      */       }
/*      */       catch (IOException e) {
/*  734 */         e.printStackTrace();
/*  735 */         return null;
/*      */       } finally {
/*      */         try {
/*  738 */           gzos.close(); } catch (Exception e) {
/*      */         }try { b64os.close(); } catch (Exception e) {
/*      */         }try { baos.close();
/*      */         } catch (Exception e) {
/*      */         }
/*      */       }
/*      */       try {
/*  745 */         return new String(baos.toByteArray(), "UTF-8");
/*      */       }
/*      */       catch (UnsupportedEncodingException uue) {
/*  748 */         return new String(baos.toByteArray());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  755 */     boolean breakLines = dontBreakLines == 0;
/*      */ 
/*  757 */     int len43 = len * 4 / 3;
/*  758 */     byte[] outBuff = new byte[len43 + (len % 3 > 0 ? 4 : 0) + (breakLines ? len43 / 76 : 0)];
/*      */ 
/*  761 */     int d = 0;
/*  762 */     int e = 0;
/*  763 */     int len2 = len - 2;
/*  764 */     int lineLength = 0;
/*  765 */     for (; d < len2; e += 4) {
/*  766 */       encode3to4(source, d + off, 3, outBuff, e, options);
/*      */ 
/*  768 */       lineLength += 4;
/*  769 */       if ((breakLines) && (lineLength == 76)) {
/*  770 */         outBuff[(e + 4)] = 10;
/*  771 */         e++;
/*  772 */         lineLength = 0;
/*      */       }
/*  765 */       d += 3;
/*      */     }
/*      */ 
/*  776 */     if (d < len) {
/*  777 */       encode3to4(source, d + off, len - d, outBuff, e, options);
/*  778 */       e += 4;
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  784 */       return new String(outBuff, 0, e, "UTF-8");
/*      */     } catch (UnsupportedEncodingException uue) {
/*      */     }
/*  787 */     return new String(outBuff, 0, e);
/*      */   }
/*      */ 
/*      */   private static int decode4to3(byte[] source, int srcOffset, byte[] destination, int destOffset, int options)
/*      */   {
/*  827 */     byte[] DECODABET = getDecodabet(options);
/*      */ 
/*  830 */     if (source[(srcOffset + 2)] == 61)
/*      */     {
/*  834 */       int outBuff = (DECODABET[source[srcOffset]] & 0xFF) << 18 | (DECODABET[source[(srcOffset + 1)]] & 0xFF) << 12;
/*      */ 
/*  837 */       destination[destOffset] = ((byte)(outBuff >>> 16));
/*  838 */       return 1;
/*      */     }
/*      */ 
/*  842 */     if (source[(srcOffset + 3)] == 61)
/*      */     {
/*  847 */       int outBuff = (DECODABET[source[srcOffset]] & 0xFF) << 18 | (DECODABET[source[(srcOffset + 1)]] & 0xFF) << 12 | (DECODABET[source[(srcOffset + 2)]] & 0xFF) << 6;
/*      */ 
/*  851 */       destination[destOffset] = ((byte)(outBuff >>> 16));
/*  852 */       destination[(destOffset + 1)] = ((byte)(outBuff >>> 8));
/*  853 */       return 2;
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  864 */       int outBuff = (DECODABET[source[srcOffset]] & 0xFF) << 18 | (DECODABET[source[(srcOffset + 1)]] & 0xFF) << 12 | (DECODABET[source[(srcOffset + 2)]] & 0xFF) << 6 | DECODABET[source[(srcOffset + 3)]] & 0xFF;
/*      */ 
/*  870 */       destination[destOffset] = ((byte)(outBuff >> 16));
/*  871 */       destination[(destOffset + 1)] = ((byte)(outBuff >> 8));
/*  872 */       destination[(destOffset + 2)] = ((byte)outBuff);
/*      */ 
/*  874 */       return 3;
/*      */     } catch (Exception e) {
/*  876 */       System.out.println("" + source[srcOffset] + ": " + DECODABET[source[srcOffset]]);
/*  877 */       System.out.println("" + source[(srcOffset + 1)] + ": " + DECODABET[source[(srcOffset + 1)]]);
/*  878 */       System.out.println("" + source[(srcOffset + 2)] + ": " + DECODABET[source[(srcOffset + 2)]]);
/*  879 */       System.out.println("" + source[(srcOffset + 3)] + ": " + DECODABET[source[(srcOffset + 3)]]);
/*  880 */     }return -1;
/*      */   }
/*      */ 
/*      */   public static byte[] decode(byte[] source, int off, int len, int options)
/*      */   {
/*  900 */     byte[] DECODABET = getDecodabet(options);
/*      */ 
/*  902 */     int len34 = len * 3 / 4;
/*  903 */     byte[] outBuff = new byte[len34];
/*  904 */     int outBuffPosn = 0;
/*      */ 
/*  906 */     byte[] b4 = new byte[4];
/*  907 */     int b4Posn = 0;
/*  908 */     int i = 0;
/*  909 */     byte sbiCrop = 0;
/*  910 */     byte sbiDecode = 0;
/*  911 */     for (i = off; i < off + len; i++) {
/*  912 */       sbiCrop = (byte)(source[i] & 0x7F);
/*  913 */       sbiDecode = DECODABET[sbiCrop];
/*      */ 
/*  915 */       if (sbiDecode >= -5)
/*      */       {
/*      */         byte[] out;
/*  917 */         if (sbiDecode >= -1) {
/*  918 */           b4[(b4Posn++)] = sbiCrop;
/*  919 */           if (b4Posn > 3) {
/*  920 */             outBuffPosn += decode4to3(b4, 0, outBuff, outBuffPosn, options);
/*  921 */             b4Posn = 0;
/*      */ 
/*  924 */             if (sbiCrop == 61) {
/*  925 */               break;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  932 */         System.err.println("Bad Base64 input character at " + i + ": " + source[i] + "(decimal)");
/*  933 */         return null;
/*      */       }
/*      */     }
/*      */ 
/*  937 */     out = new byte[outBuffPosn];
/*  938 */     System.arraycopy(outBuff, 0, out, 0, outBuffPosn);
/*  939 */     return out;
/*      */   }
/*      */ 
/*      */   public static byte[] decode(String s)
/*      */   {
/*  954 */     return decode(s, 0);
/*      */   }
/*      */ 
/*      */   public static byte[] decode(String s, int options)
/*      */   {
/*      */     try
/*      */     {
/*  970 */       bytes = s.getBytes("UTF-8");
/*      */     }
/*      */     catch (UnsupportedEncodingException uee) {
/*  973 */       bytes = s.getBytes();
/*      */     }
/*      */ 
/*  978 */     byte[] bytes = decode(bytes, 0, bytes.length, options);
/*      */ 
/*  983 */     if ((bytes != null) && (bytes.length >= 4))
/*      */     {
/*  985 */       int head = bytes[0] & 0xFF | bytes[1] << 8 & 0xFF00;
/*  986 */       if (35615 == head) {
/*  987 */         ByteArrayInputStream bais = null;
/*  988 */         GZIPInputStream gzis = null;
/*  989 */         ByteArrayOutputStream baos = null;
/*  990 */         byte[] buffer = new byte[2048];
/*  991 */         int length = 0;
/*      */         try
/*      */         {
/*  994 */           baos = new ByteArrayOutputStream();
/*  995 */           bais = new ByteArrayInputStream(bytes);
/*  996 */           gzis = new GZIPInputStream(bais);
/*      */ 
/*  998 */           while ((length = gzis.read(buffer)) >= 0) {
/*  999 */             baos.write(buffer, 0, length);
/*      */           }
/*      */ 
/* 1003 */           bytes = baos.toByteArray();
/*      */         }
/*      */         catch (IOException e)
/*      */         {
/*      */         }
/*      */         finally {
/*      */           try {
/* 1010 */             baos.close(); } catch (Exception e) {
/*      */           }try { gzis.close(); } catch (Exception e) {
/*      */           }try { bais.close();
/*      */           } catch (Exception e) {
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 1018 */     return bytes;
/*      */   }
/*      */ 
/*      */   public static Object decodeToObject(String encodedObject)
/*      */   {
/* 1034 */     byte[] objBytes = decode(encodedObject);
/*      */ 
/* 1036 */     ByteArrayInputStream bais = null;
/* 1037 */     ObjectInputStream ois = null;
/* 1038 */     Object obj = null;
/*      */     try
/*      */     {
/* 1041 */       bais = new ByteArrayInputStream(objBytes);
/* 1042 */       ois = new ObjectInputStream(bais);
/*      */ 
/* 1044 */       obj = ois.readObject();
/*      */     }
/*      */     catch (IOException e) {
/* 1047 */       e.printStackTrace();
/*      */     }
/*      */     catch (ClassNotFoundException e) {
/* 1050 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1053 */         bais.close(); } catch (Exception e) {
/*      */       }try { ois.close(); } catch (Exception e) {
/*      */       }
/*      */     }
/* 1057 */     return obj;
/*      */   }
/*      */ 
/*      */   public static boolean encodeToFile(byte[] dataToEncode, String filename)
/*      */   {
/* 1072 */     boolean success = false;
/* 1073 */     OutputStream bos = null;
/*      */     try {
/* 1075 */       bos = new OutputStream(new FileOutputStream(filename), 1);
/*      */ 
/* 1077 */       bos.write(dataToEncode);
/* 1078 */       success = true;
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/* 1082 */       success = false;
/*      */     } finally {
/*      */       try {
/* 1085 */         bos.close(); } catch (Exception e) {
/*      */       }
/*      */     }
/* 1088 */     return success;
/*      */   }
/*      */ 
/*      */   public static boolean decodeToFile(String dataToDecode, String filename)
/*      */   {
/* 1102 */     boolean success = false;
/* 1103 */     OutputStream bos = null;
/*      */     try {
/* 1105 */       bos = new OutputStream(new FileOutputStream(filename), 0);
/*      */ 
/* 1107 */       bos.write(dataToDecode.getBytes("UTF-8"));
/* 1108 */       success = true;
/*      */     }
/*      */     catch (IOException e) {
/* 1111 */       success = false;
/*      */     } finally {
/*      */       try {
/* 1114 */         bos.close(); } catch (Exception e) {
/*      */       }
/*      */     }
/* 1117 */     return success;
/*      */   }
/*      */ 
/*      */   public static byte[] decodeFromFile(String filename)
/*      */   {
/* 1133 */     byte[] decodedData = null;
/* 1134 */     InputStream bis = null;
/*      */     try
/*      */     {
/* 1137 */       File file = new File(filename);
/* 1138 */       byte[] buffer = null;
/* 1139 */       int length = 0;
/* 1140 */       int numBytes = 0;
/*      */ 
/* 1143 */       if (file.length() > 2147483647L) {
/* 1144 */         System.err.println("File is too big for this convenience method (" + file.length() + " bytes).");
/* 1145 */         return null;
/*      */       }
/* 1147 */       buffer = new byte[(int)file.length()];
/*      */ 
/* 1150 */       bis = new InputStream(new BufferedInputStream(new FileInputStream(file)), 0);
/*      */ 
/* 1155 */       while ((numBytes = bis.read(buffer, length, 4096)) >= 0) {
/* 1156 */         length += numBytes;
/*      */       }
/*      */ 
/* 1159 */       decodedData = new byte[length];
/* 1160 */       System.arraycopy(buffer, 0, decodedData, 0, length);
/*      */ 
/* 1167 */       if (null != bis) try {
/* 1168 */           bis.close();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*      */         }
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/* 1164 */       System.err.println("Error decoding from file " + filename);
/*      */ 
/* 1167 */       if (null != bis) try {
/* 1168 */           bis.close();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*      */         }
/*      */     }
/*      */     finally
/*      */     {
/* 1167 */       if (null != bis) try {
/* 1168 */           bis.close();
/*      */         }
/*      */         catch (Exception e) {  }
/*      */  
/*      */     }
/* 1172 */     return decodedData;
/*      */   }
/*      */ 
/*      */   public static String encodeFromFile(String filename)
/*      */   {
/* 1187 */     String encodedData = null;
/* 1188 */     InputStream bis = null;
/*      */     try
/*      */     {
/* 1191 */       File file = new File(filename);
/* 1192 */       byte[] buffer = new byte[Math.max((int)(file.length() * 1.4D), 40)];
/* 1193 */       int length = 0;
/* 1194 */       int numBytes = 0;
/*      */ 
/* 1197 */       bis = new InputStream(new BufferedInputStream(new FileInputStream(file)), 1);
/*      */ 
/* 1202 */       while ((numBytes = bis.read(buffer, length, 4096)) >= 0) {
/* 1203 */         length += numBytes;
/*      */       }
/*      */ 
/* 1206 */       encodedData = new String(buffer, 0, length, "UTF-8");
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/* 1210 */       System.err.println("Error encoding from file " + filename);
/*      */     } finally {
/*      */       try {
/* 1213 */         bis.close(); } catch (Exception e) {
/*      */       }
/*      */     }
/* 1216 */     return encodedData;
/*      */   }
/*      */ 
/*      */   public static void encodeFileToFile(String infile, String outfile)
/*      */   {
/* 1227 */     String encoded = encodeFromFile(infile);
/* 1228 */     OutputStream out = null;
/*      */     try {
/* 1230 */       out = new BufferedOutputStream(new FileOutputStream(outfile));
/*      */ 
/* 1232 */       out.write(encoded.getBytes("US-ASCII"));
/*      */     }
/*      */     catch (IOException ex) {
/* 1235 */       ex.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1238 */         out.close();
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void decodeFileToFile(String infile, String outfile)
/*      */   {
/* 1251 */     byte[] decoded = decodeFromFile(infile);
/* 1252 */     OutputStream out = null;
/*      */     try {
/* 1254 */       out = new BufferedOutputStream(new FileOutputStream(outfile));
/*      */ 
/* 1256 */       out.write(decoded);
/*      */     }
/*      */     catch (IOException ex) {
/* 1259 */       ex.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1262 */         out.close();
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class OutputStream extends FilterOutputStream
/*      */   {
/*      */     private boolean encode;
/*      */     private int position;
/*      */     private byte[] buffer;
/*      */     private int bufferLength;
/*      */     private int lineLength;
/*      */     private boolean breakLines;
/*      */     private byte[] b4;
/*      */     private boolean suspendEncoding;
/*      */     private int options;
/*      */     private byte[] alphabet;
/*      */     private byte[] decodabet;
/*      */ 
/*      */     public OutputStream(OutputStream out)
/*      */     {
/* 1513 */       this(out, 1);
/*      */     }
/*      */ 
/*      */     public OutputStream(OutputStream out, int options)
/*      */     {
/* 1538 */       super();
/* 1539 */       this.breakLines = ((options & 0x8) != 8);
/* 1540 */       this.encode = ((options & 0x1) == 1);
/* 1541 */       this.bufferLength = (this.encode ? 3 : 4);
/* 1542 */       this.buffer = new byte[this.bufferLength];
/* 1543 */       this.position = 0;
/* 1544 */       this.lineLength = 0;
/* 1545 */       this.suspendEncoding = false;
/* 1546 */       this.b4 = new byte[4];
/* 1547 */       this.options = options;
/* 1548 */       this.alphabet = Base64.getAlphabet(options);
/* 1549 */       this.decodabet = Base64.getDecodabet(options);
/*      */     }
/*      */ 
/*      */     public void write(int theByte)
/*      */       throws IOException
/*      */     {
/* 1567 */       if (this.suspendEncoding) {
/* 1568 */         this.out.write(theByte);
/* 1569 */         return;
/*      */       }
/*      */ 
/* 1573 */       if (this.encode) {
/* 1574 */         this.buffer[(this.position++)] = ((byte)theByte);
/* 1575 */         if (this.position >= this.bufferLength)
/*      */         {
/* 1577 */           this.out.write(Base64.encode3to4(this.b4, this.buffer, this.bufferLength, this.options));
/*      */ 
/* 1579 */           this.lineLength += 4;
/* 1580 */           if ((this.breakLines) && (this.lineLength >= 76)) {
/* 1581 */             this.out.write(10);
/* 1582 */             this.lineLength = 0;
/*      */           }
/*      */ 
/* 1585 */           this.position = 0;
/*      */         }
/*      */ 
/*      */       }
/* 1592 */       else if (this.decodabet[(theByte & 0x7F)] > -5) {
/* 1593 */         this.buffer[(this.position++)] = ((byte)theByte);
/* 1594 */         if (this.position >= this.bufferLength)
/*      */         {
/* 1596 */           int len = Base64.decode4to3(this.buffer, 0, this.b4, 0, this.options);
/* 1597 */           this.out.write(this.b4, 0, len);
/*      */ 
/* 1599 */           this.position = 0;
/*      */         }
/*      */       }
/* 1602 */       else if (this.decodabet[(theByte & 0x7F)] != -5) {
/* 1603 */         throw new IOException(MessageLocalization.getComposedMessage("invalid.character.in.base64.data", new Object[0]));
/*      */       }
/*      */     }
/*      */ 
/*      */     public void write(byte[] theBytes, int off, int len)
/*      */       throws IOException
/*      */     {
/* 1621 */       if (this.suspendEncoding) {
/* 1622 */         this.out.write(theBytes, off, len);
/* 1623 */         return;
/*      */       }
/*      */ 
/* 1626 */       for (int i = 0; i < len; i++)
/* 1627 */         write(theBytes[(off + i)]);
/*      */     }
/*      */ 
/*      */     public void flushBase64()
/*      */       throws IOException
/*      */     {
/* 1639 */       if (this.position > 0)
/* 1640 */         if (this.encode) {
/* 1641 */           this.out.write(Base64.encode3to4(this.b4, this.buffer, this.position, this.options));
/* 1642 */           this.position = 0;
/*      */         }
/*      */         else {
/* 1645 */           throw new IOException(MessageLocalization.getComposedMessage("base64.input.not.properly.padded", new Object[0]));
/*      */         }
/*      */     }
/*      */ 
/*      */     public void close()
/*      */       throws IOException
/*      */     {
/* 1659 */       flushBase64();
/*      */ 
/* 1663 */       super.close();
/*      */ 
/* 1665 */       this.buffer = null;
/* 1666 */       this.out = null;
/*      */     }
/*      */ 
/*      */     public void suspendEncoding()
/*      */       throws IOException
/*      */     {
/* 1679 */       flushBase64();
/* 1680 */       this.suspendEncoding = true;
/*      */     }
/*      */ 
/*      */     public void resumeEncoding()
/*      */     {
/* 1692 */       this.suspendEncoding = false;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class InputStream extends FilterInputStream
/*      */   {
/*      */     private boolean encode;
/*      */     private int position;
/*      */     private byte[] buffer;
/*      */     private int bufferLength;
/*      */     private int numSigBytes;
/*      */     private int lineLength;
/*      */     private boolean breakLines;
/*      */     private int options;
/*      */     private byte[] alphabet;
/*      */     private byte[] decodabet;
/*      */ 
/*      */     public InputStream(InputStream in)
/*      */     {
/* 1299 */       this(in, 0);
/*      */     }
/*      */ 
/*      */     public InputStream(InputStream in, int options)
/*      */     {
/* 1325 */       super();
/* 1326 */       this.breakLines = ((options & 0x8) != 8);
/* 1327 */       this.encode = ((options & 0x1) == 1);
/* 1328 */       this.bufferLength = (this.encode ? 4 : 3);
/* 1329 */       this.buffer = new byte[this.bufferLength];
/* 1330 */       this.position = -1;
/* 1331 */       this.lineLength = 0;
/* 1332 */       this.options = options;
/* 1333 */       this.alphabet = Base64.getAlphabet(options);
/* 1334 */       this.decodabet = Base64.getDecodabet(options);
/*      */     }
/*      */ 
/*      */     public int read()
/*      */       throws IOException
/*      */     {
/* 1346 */       if (this.position < 0) {
/* 1347 */         if (this.encode) {
/* 1348 */           byte[] b3 = new byte[3];
/* 1349 */           int numBinaryBytes = 0;
/* 1350 */           for (int i = 0; i < 3; i++) {
/*      */             try {
/* 1352 */               int b = this.in.read();
/*      */ 
/* 1355 */               if (b >= 0) {
/* 1356 */                 b3[i] = ((byte)b);
/* 1357 */                 numBinaryBytes++;
/*      */               }
/*      */ 
/*      */             }
/*      */             catch (IOException e)
/*      */             {
/* 1363 */               if (i == 0) {
/* 1364 */                 throw e;
/*      */               }
/*      */             }
/*      */           }
/*      */ 
/* 1369 */           if (numBinaryBytes > 0) {
/* 1370 */             Base64.encode3to4(b3, 0, numBinaryBytes, this.buffer, 0, this.options);
/* 1371 */             this.position = 0;
/* 1372 */             this.numSigBytes = 4;
/*      */           }
/*      */           else {
/* 1375 */             return -1;
/*      */           }
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1381 */           byte[] b4 = new byte[4];
/* 1382 */           int i = 0;
/* 1383 */           for (i = 0; i < 4; i++)
/*      */           {
/* 1385 */             int b = 0;
/*      */             do b = this.in.read();
/* 1387 */             while ((b >= 0) && (this.decodabet[(b & 0x7F)] <= -5));
/*      */ 
/* 1389 */             if (b < 0) {
/*      */               break;
/*      */             }
/* 1392 */             b4[i] = ((byte)b);
/*      */           }
/*      */ 
/* 1395 */           if (i == 4) {
/* 1396 */             this.numSigBytes = Base64.decode4to3(b4, 0, this.buffer, 0, this.options);
/* 1397 */             this.position = 0;
/*      */           } else {
/* 1399 */             if (i == 0) {
/* 1400 */               return -1;
/*      */             }
/*      */ 
/* 1404 */             throw new IOException(MessageLocalization.getComposedMessage("improperly.padded.base64.input", new Object[0]));
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1411 */       if (this.position >= 0)
/*      */       {
/* 1413 */         if (this.position >= this.numSigBytes) {
/* 1414 */           return -1;
/*      */         }
/* 1416 */         if ((this.encode) && (this.breakLines) && (this.lineLength >= 76)) {
/* 1417 */           this.lineLength = 0;
/* 1418 */           return 10;
/*      */         }
/*      */ 
/* 1421 */         this.lineLength += 1;
/*      */ 
/* 1425 */         int b = this.buffer[(this.position++)];
/*      */ 
/* 1427 */         if (this.position >= this.bufferLength) {
/* 1428 */           this.position = -1;
/*      */         }
/* 1430 */         return b & 0xFF;
/*      */       }
/*      */ 
/* 1438 */       throw new IOException(MessageLocalization.getComposedMessage("error.in.base64.code.reading.stream", new Object[0]));
/*      */     }
/*      */ 
/*      */     public int read(byte[] dest, int off, int len)
/*      */       throws IOException
/*      */     {
/* 1458 */       for (int i = 0; i < len; i++) {
/* 1459 */         int b = read();
/*      */ 
/* 1464 */         if (b >= 0) {
/* 1465 */           dest[(off + i)] = ((byte)b); } else {
/* 1466 */           if (i != 0) break;
/* 1467 */           return -1;
/*      */         }
/*      */       }
/*      */ 
/* 1471 */       return i;
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.codec.Base64
 * JD-Core Version:    0.6.2
 */