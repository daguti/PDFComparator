/*     */ package org.apache.pdfbox.filter;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import javax.imageio.stream.MemoryCacheImageInputStream;
/*     */ import javax.imageio.stream.MemoryCacheImageOutputStream;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ 
/*     */ public class LZWFilter
/*     */   implements Filter
/*     */ {
/*  47 */   private static final Log LOG = LogFactory.getLog(LZWFilter.class);
/*     */   public static final long CLEAR_TABLE = 256L;
/*     */   public static final long EOD = 257L;
/*     */ 
/*     */   public void decode(InputStream compressedData, OutputStream result, COSDictionary options, int filterIndex)
/*     */     throws IOException
/*     */   {
/*  68 */     COSBase baseObj = options.getDictionaryObject(COSName.DECODE_PARMS, COSName.DP);
/*  69 */     COSDictionary decodeParams = null;
/*  70 */     if ((baseObj instanceof COSDictionary))
/*     */     {
/*  72 */       decodeParams = (COSDictionary)baseObj;
/*     */     }
/*  74 */     else if ((baseObj instanceof COSArray))
/*     */     {
/*  76 */       COSArray paramArray = (COSArray)baseObj;
/*  77 */       if (filterIndex < paramArray.size())
/*     */       {
/*  79 */         decodeParams = (COSDictionary)paramArray.getObject(filterIndex);
/*     */       }
/*     */     }
/*  82 */     else if (baseObj != null)
/*     */     {
/*  84 */       throw new IOException("Error: Expected COSArray or COSDictionary and not " + baseObj.getClass().getName());
/*     */     }
/*     */ 
/*  88 */     int predictor = -1;
/*  89 */     int earlyChange = 1;
/*  90 */     if (decodeParams != null)
/*     */     {
/*  92 */       predictor = decodeParams.getInt(COSName.PREDICTOR);
/*  93 */       earlyChange = decodeParams.getInt(COSName.EARLY_CHANGE, 1);
/*  94 */       if ((earlyChange != 0) && (earlyChange != 1))
/*     */       {
/*  96 */         earlyChange = 1;
/*     */       }
/*     */     }
/*  99 */     if (predictor > 1)
/*     */     {
/* 101 */       int colors = Math.min(decodeParams.getInt(COSName.COLORS, 1), 32);
/* 102 */       int bitsPerPixel = decodeParams.getInt(COSName.BITS_PER_COMPONENT, 8);
/* 103 */       int columns = decodeParams.getInt(COSName.COLUMNS, 1);
/* 104 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 105 */       doLZWDecode(compressedData, baos, earlyChange);
/* 106 */       ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
/* 107 */       Predictor.decodePredictor(predictor, colors, bitsPerPixel, columns, bais, result);
/* 108 */       result.flush();
/* 109 */       baos.reset();
/* 110 */       bais.reset();
/*     */     }
/*     */     else
/*     */     {
/* 114 */       doLZWDecode(compressedData, result, earlyChange);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void doLZWDecode(InputStream compressedData, OutputStream result, int earlyChange) throws IOException
/*     */   {
/* 120 */     ArrayList codeTable = null;
/* 121 */     int chunk = 9;
/* 122 */     MemoryCacheImageInputStream in = new MemoryCacheImageInputStream(compressedData);
/* 123 */     long nextCommand = 0L;
/* 124 */     long prevCommand = -1L;
/*     */     try
/*     */     {
/* 128 */       while ((nextCommand = in.readBits(chunk)) != 257L)
/*     */       {
/* 130 */         if (nextCommand == 256L)
/*     */         {
/* 132 */           chunk = 9;
/* 133 */           codeTable = createCodeTable();
/* 134 */           prevCommand = -1L;
/*     */         }
/*     */         else
/*     */         {
/* 138 */           if (nextCommand < codeTable.size())
/*     */           {
/* 140 */             byte[] data = (byte[])codeTable.get((int)nextCommand);
/* 141 */             byte firstByte = data[0];
/* 142 */             result.write(data);
/* 143 */             if (prevCommand != -1L)
/*     */             {
/* 145 */               data = (byte[])codeTable.get((int)prevCommand);
/* 146 */               byte[] newData = new byte[data.length + 1];
/* 147 */               for (int i = 0; i < data.length; i++)
/*     */               {
/* 149 */                 newData[i] = data[i];
/*     */               }
/* 151 */               newData[data.length] = firstByte;
/* 152 */               codeTable.add(newData);
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 157 */             byte[] data = (byte[])codeTable.get((int)prevCommand);
/* 158 */             byte[] newData = new byte[data.length + 1];
/* 159 */             for (int i = 0; i < data.length; i++)
/*     */             {
/* 161 */               newData[i] = data[i];
/*     */             }
/* 163 */             newData[data.length] = data[0];
/* 164 */             result.write(newData);
/* 165 */             codeTable.add(newData);
/*     */           }
/*     */ 
/* 168 */           chunk = calculateChunk(codeTable.size(), earlyChange);
/* 169 */           prevCommand = nextCommand;
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (EOFException ex)
/*     */     {
/* 175 */       LOG.warn("Premature EOF in LZW stream, EOD code missing");
/*     */     }
/* 177 */     result.flush();
/*     */   }
/*     */ 
/*     */   public void encode(InputStream rawData, OutputStream result, COSDictionary options, int filterIndex)
/*     */     throws IOException
/*     */   {
/* 186 */     ArrayList codeTable = createCodeTable();
/* 187 */     int chunk = 9;
/*     */ 
/* 189 */     byte[] inputPattern = null;
/* 190 */     MemoryCacheImageOutputStream out = new MemoryCacheImageOutputStream(result);
/* 191 */     out.writeBits(256L, chunk);
/* 192 */     int foundCode = -1;
/*     */     int r;
/* 194 */     while ((r = rawData.read()) != -1)
/*     */     {
/* 196 */       byte by = (byte)r;
/* 197 */       if (inputPattern == null)
/*     */       {
/* 199 */         inputPattern = new byte[] { by };
/*     */ 
/* 203 */         foundCode = by & 0xFF;
/*     */       }
/*     */       else
/*     */       {
/* 207 */         byte[] inputPatternCopy = new byte[inputPattern.length + 1];
/* 208 */         for (int i = 0; i < inputPattern.length; i++)
/*     */         {
/* 210 */           inputPatternCopy[i] = inputPattern[i];
/*     */         }
/* 212 */         inputPattern = inputPatternCopy;
/* 213 */         inputPattern[(inputPattern.length - 1)] = by;
/* 214 */         int newFoundCode = findPatternCode(codeTable, inputPattern);
/* 215 */         if (newFoundCode == -1)
/*     */         {
/* 218 */           chunk = calculateChunk(codeTable.size() - 1, 1);
/* 219 */           out.writeBits(foundCode, chunk);
/*     */ 
/* 221 */           codeTable.add(inputPattern);
/*     */ 
/* 223 */           if (codeTable.size() == 4096)
/*     */           {
/* 226 */             out.writeBits(256L, chunk);
/* 227 */             chunk = 9;
/* 228 */             codeTable = createCodeTable();
/*     */           }
/*     */ 
/* 231 */           inputPattern = new byte[] { by };
/*     */ 
/* 235 */           foundCode = by & 0xFF;
/*     */         }
/*     */         else
/*     */         {
/* 239 */           foundCode = newFoundCode;
/*     */         }
/*     */       }
/*     */     }
/* 243 */     if (foundCode != -1)
/*     */     {
/* 245 */       chunk = calculateChunk(codeTable.size() - 1, 1);
/* 246 */       out.writeBits(foundCode, chunk);
/*     */     }
/*     */ 
/* 254 */     chunk = calculateChunk(codeTable.size(), 1);
/*     */ 
/* 256 */     out.writeBits(257L, chunk);
/* 257 */     out.writeBits(0L, 7);
/* 258 */     out.flush();
/*     */   }
/*     */ 
/*     */   private int findPatternCode(ArrayList<byte[]> codeTable, byte[] pattern)
/*     */   {
/* 271 */     int foundCode = -1;
/* 272 */     int foundLen = 0;
/* 273 */     for (int i = codeTable.size() - 1; i >= 0; i--)
/*     */     {
/* 275 */       if (i <= 257L)
/*     */       {
/* 278 */         if (foundCode != -1)
/*     */         {
/* 280 */           return foundCode;
/*     */         }
/* 282 */         if (pattern.length > 1)
/*     */         {
/* 284 */           return -1;
/*     */         }
/*     */       }
/* 287 */       byte[] tryPattern = (byte[])codeTable.get(i);
/* 288 */       if ((foundCode != -1) || (tryPattern.length > foundLen))
/*     */       {
/* 290 */         if (Arrays.equals(tryPattern, pattern))
/*     */         {
/* 292 */           foundCode = i;
/* 293 */           foundLen = tryPattern.length;
/*     */         }
/*     */       }
/*     */     }
/* 297 */     return foundCode;
/*     */   }
/*     */ 
/*     */   private ArrayList<byte[]> createCodeTable()
/*     */   {
/* 306 */     ArrayList codeTable = new ArrayList(4096);
/* 307 */     for (int i = 0; i < 256; i++)
/*     */     {
/* 309 */       codeTable.add(new byte[] { (byte)(i & 0xFF) });
/*     */     }
/*     */ 
/* 314 */     codeTable.add(null);
/* 315 */     codeTable.add(null);
/* 316 */     return codeTable;
/*     */   }
/*     */ 
/*     */   private int calculateChunk(int tabSize, int earlyChange)
/*     */   {
/* 329 */     if (tabSize >= 2048 - earlyChange)
/*     */     {
/* 331 */       return 12;
/*     */     }
/* 333 */     if (tabSize >= 1024 - earlyChange)
/*     */     {
/* 335 */       return 11;
/*     */     }
/* 337 */     if (tabSize >= 512 - earlyChange)
/*     */     {
/* 339 */       return 10;
/*     */     }
/* 341 */     return 9;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.filter.LZWFilter
 * JD-Core Version:    0.6.2
 */