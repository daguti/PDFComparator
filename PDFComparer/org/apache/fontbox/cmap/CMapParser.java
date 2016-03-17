/*     */ package org.apache.fontbox.cmap;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PushbackInputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.fontbox.util.ResourceLoader;
/*     */ 
/*     */ public class CMapParser
/*     */ {
/*     */   private static final String BEGIN_CODESPACE_RANGE = "begincodespacerange";
/*     */   private static final String BEGIN_BASE_FONT_CHAR = "beginbfchar";
/*     */   private static final String BEGIN_BASE_FONT_RANGE = "beginbfrange";
/*     */   private static final String BEGIN_CID_CHAR = "begincidchar";
/*     */   private static final String BEGIN_CID_RANGE = "begincidrange";
/*     */   private static final String USECMAP = "usecmap";
/*     */   private static final String END_CODESPACE_RANGE = "endcodespacerange";
/*     */   private static final String END_BASE_FONT_CHAR = "endbfchar";
/*     */   private static final String END_BASE_FONT_RANGE = "endbfrange";
/*     */   private static final String END_CID_CHAR = "endcidchar";
/*     */   private static final String END_CID_RANGE = "endcidrange";
/*     */   private static final String END_CMAP = "endcmap";
/*     */   private static final String WMODE = "WMode";
/*     */   private static final String CMAP_NAME = "CMapName";
/*     */   private static final String CMAP_VERSION = "CMapVersion";
/*     */   private static final String CMAP_TYPE = "CMapType";
/*     */   private static final String REGISTRY = "Registry";
/*     */   private static final String ORDERING = "Ordering";
/*     */   private static final String SUPPLEMENT = "Supplement";
/*     */   private static final String MARK_END_OF_DICTIONARY = ">>";
/*     */   private static final String MARK_END_OF_ARRAY = "]";
/*  65 */   private byte[] tokenParserByteBuffer = new byte[512];
/*     */ 
/*     */   public CMap parse(File file)
/*     */     throws IOException
/*     */   {
/*  85 */     String rootDir = file.getParent() + File.separator;
/*  86 */     FileInputStream input = null;
/*     */     try
/*     */     {
/*  89 */       input = new FileInputStream(file);
/*  90 */       return parse(rootDir, input);
/*     */     }
/*     */     finally
/*     */     {
/*  94 */       if (input != null)
/*     */       {
/*  96 */         input.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public CMap parse(String resourceRoot, InputStream input)
/*     */     throws IOException
/*     */   {
/* 115 */     PushbackInputStream cmapStream = new PushbackInputStream(input);
/* 116 */     CMap result = new CMap();
/* 117 */     Object previousToken = null;
/* 118 */     Object token = null;
/* 119 */     while ((token = parseNextToken(cmapStream)) != null)
/*     */     {
/* 121 */       if ((token instanceof Operator))
/*     */       {
/* 123 */         Operator op = (Operator)token;
/* 124 */         if (op.op.equals("usecmap"))
/*     */         {
/* 126 */           LiteralName useCmapName = (LiteralName)previousToken;
/* 127 */           InputStream useStream = ResourceLoader.loadResource(resourceRoot + useCmapName.name);
/* 128 */           if (useStream == null)
/*     */           {
/* 130 */             throw new IOException("Error: Could not find referenced cmap stream " + useCmapName.name);
/*     */           }
/* 132 */           CMap useCMap = parse(resourceRoot, useStream);
/* 133 */           result.useCmap(useCMap);
/*     */         } else {
/* 135 */           if (op.op.equals("endcmap"))
/*     */           {
/*     */             break;
/*     */           }
/*     */ 
/* 140 */           if (op.op.equals("begincodespacerange"))
/*     */           {
/* 142 */             Number cosCount = (Number)previousToken;
/* 143 */             for (int j = 0; j < cosCount.intValue(); j++)
/*     */             {
/* 145 */               Object nextToken = parseNextToken(cmapStream);
/* 146 */               if ((nextToken instanceof Operator))
/*     */               {
/* 148 */                 if (((Operator)nextToken).op.equals("endcodespacerange"))
/*     */                   break;
/* 150 */                 throw new IOException("Error : ~codespacerange contains an unexpected operator : " + ((Operator)nextToken).op);
/*     */               }
/*     */ 
/* 155 */               byte[] startRange = (byte[])nextToken;
/* 156 */               byte[] endRange = (byte[])parseNextToken(cmapStream);
/* 157 */               CodespaceRange range = new CodespaceRange();
/* 158 */               range.setStart(startRange);
/* 159 */               range.setEnd(endRange);
/* 160 */               result.addCodespaceRange(range);
/*     */             }
/*     */           }
/* 163 */           else if (op.op.equals("beginbfchar"))
/*     */           {
/* 165 */             Number cosCount = (Number)previousToken;
/* 166 */             for (int j = 0; j < cosCount.intValue(); j++)
/*     */             {
/* 168 */               Object nextToken = parseNextToken(cmapStream);
/* 169 */               if ((nextToken instanceof Operator))
/*     */               {
/* 171 */                 if (((Operator)nextToken).op.equals("endbfchar"))
/*     */                   break;
/* 173 */                 throw new IOException("Error : ~bfchar contains an unexpected operator : " + ((Operator)nextToken).op);
/*     */               }
/*     */ 
/* 178 */               byte[] inputCode = (byte[])nextToken;
/* 179 */               nextToken = parseNextToken(cmapStream);
/* 180 */               if ((nextToken instanceof byte[]))
/*     */               {
/* 182 */                 byte[] bytes = (byte[])nextToken;
/* 183 */                 String value = createStringFromBytes(bytes);
/* 184 */                 result.addMapping(inputCode, value);
/*     */               }
/* 186 */               else if ((nextToken instanceof LiteralName))
/*     */               {
/* 188 */                 result.addMapping(inputCode, ((LiteralName)nextToken).name);
/*     */               }
/*     */               else
/*     */               {
/* 192 */                 throw new IOException("Error parsing CMap beginbfchar, expected{COSString or COSName} and not " + nextToken);
/*     */               }
/*     */             }
/*     */ 
/*     */           }
/* 197 */           else if (op.op.equals("beginbfrange"))
/*     */           {
/* 199 */             Number cosCount = (Number)previousToken;
/*     */ 
/* 201 */             for (int j = 0; j < cosCount.intValue(); j++)
/*     */             {
/* 203 */               Object nextToken = parseNextToken(cmapStream);
/* 204 */               if ((nextToken instanceof Operator))
/*     */               {
/* 206 */                 if (((Operator)nextToken).op.equals("endbfrange"))
/*     */                   break;
/* 208 */                 throw new IOException("Error : ~bfrange contains an unexpected operator : " + ((Operator)nextToken).op);
/*     */               }
/*     */ 
/* 213 */               byte[] startCode = (byte[])nextToken;
/* 214 */               byte[] endCode = (byte[])parseNextToken(cmapStream);
/* 215 */               nextToken = parseNextToken(cmapStream);
/* 216 */               List array = null;
/* 217 */               byte[] tokenBytes = null;
/* 218 */               if ((nextToken instanceof List))
/*     */               {
/* 220 */                 array = (List)nextToken;
/* 221 */                 tokenBytes = (byte[])array.get(0);
/*     */               }
/*     */               else
/*     */               {
/* 225 */                 tokenBytes = (byte[])nextToken;
/*     */               }
/* 227 */               boolean done = false;
/*     */ 
/* 229 */               if (Arrays.equals(startCode, tokenBytes))
/*     */               {
/* 231 */                 done = true;
/*     */               }
/* 233 */               String value = null;
/*     */ 
/* 235 */               int arrayIndex = 0;
/* 236 */               while (!done)
/*     */               {
/* 238 */                 if (compare(startCode, endCode) >= 0)
/*     */                 {
/* 240 */                   done = true;
/*     */                 }
/* 242 */                 value = createStringFromBytes(tokenBytes);
/* 243 */                 result.addMapping(startCode, value);
/* 244 */                 increment(startCode);
/*     */ 
/* 246 */                 if (array == null)
/*     */                 {
/* 248 */                   increment(tokenBytes);
/*     */                 }
/*     */                 else
/*     */                 {
/* 252 */                   arrayIndex++;
/* 253 */                   if (arrayIndex < array.size())
/*     */                   {
/* 255 */                     tokenBytes = (byte[])array.get(arrayIndex);
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/* 261 */           else if (op.op.equals("begincidchar"))
/*     */           {
/* 263 */             Number cosCount = (Number)previousToken;
/* 264 */             for (int j = 0; j < cosCount.intValue(); j++)
/*     */             {
/* 266 */               Object nextToken = parseNextToken(cmapStream);
/* 267 */               if ((nextToken instanceof Operator))
/*     */               {
/* 269 */                 if (((Operator)nextToken).op.equals("endcidchar"))
/*     */                   break;
/* 271 */                 throw new IOException("Error : ~cidchar contains an unexpected operator : " + ((Operator)nextToken).op);
/*     */               }
/*     */ 
/* 276 */               byte[] inputCode = (byte[])nextToken;
/* 277 */               int mappedCode = ((Integer)parseNextToken(cmapStream)).intValue();
/* 278 */               String mappedStr = createStringFromBytes(inputCode);
/* 279 */               result.addCIDMapping(mappedCode, mappedStr);
/*     */             }
/*     */           }
/* 282 */           else if (op.op.equals("begincidrange"))
/*     */           {
/* 284 */             int numberOfLines = ((Integer)previousToken).intValue();
/* 285 */             for (int n = 0; n < numberOfLines; n++)
/*     */             {
/* 287 */               Object nextToken = parseNextToken(cmapStream);
/* 288 */               if ((nextToken instanceof Operator))
/*     */               {
/* 290 */                 if (((Operator)nextToken).op.equals("endcidrange"))
/*     */                   break;
/* 292 */                 throw new IOException("Error : ~cidrange contains an unexpected operator : " + ((Operator)nextToken).op);
/*     */               }
/*     */ 
/* 297 */               byte[] startCode = (byte[])nextToken;
/* 298 */               int start = createIntFromBytes(startCode);
/* 299 */               byte[] endCode = (byte[])parseNextToken(cmapStream);
/* 300 */               int end = createIntFromBytes(endCode);
/* 301 */               int mappedCode = ((Integer)parseNextToken(cmapStream)).intValue();
/* 302 */               if ((startCode.length <= 2) && (endCode.length <= 2))
/*     */               {
/* 304 */                 result.addCIDRange((char)start, (char)end, mappedCode);
/*     */               }
/*     */               else
/*     */               {
/* 309 */                 int endOfMappings = mappedCode + end - start;
/* 310 */                 while (mappedCode <= endOfMappings)
/*     */                 {
/* 312 */                   String mappedStr = createStringFromBytes(startCode);
/* 313 */                   result.addCIDMapping(mappedCode++, mappedStr);
/* 314 */                   increment(startCode);
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/* 320 */       } else if ((token instanceof LiteralName))
/*     */       {
/* 322 */         LiteralName literal = (LiteralName)token;
/* 323 */         if ("WMode".equals(literal.name))
/*     */         {
/* 325 */           Object next = parseNextToken(cmapStream);
/* 326 */           if ((next instanceof Integer))
/*     */           {
/* 328 */             result.setWMode(((Integer)next).intValue());
/*     */           }
/*     */         }
/* 331 */         else if ("CMapName".equals(literal.name))
/*     */         {
/* 333 */           Object next = parseNextToken(cmapStream);
/* 334 */           if ((next instanceof LiteralName))
/*     */           {
/* 336 */             result.setName(((LiteralName)next).name);
/*     */           }
/*     */         }
/* 339 */         else if ("CMapVersion".equals(literal.name))
/*     */         {
/* 341 */           Object next = parseNextToken(cmapStream);
/* 342 */           if ((next instanceof Number))
/*     */           {
/* 344 */             result.setVersion(((Number)next).toString());
/*     */           }
/* 346 */           else if ((next instanceof String))
/*     */           {
/* 348 */             result.setVersion((String)next);
/*     */           }
/*     */         }
/* 351 */         else if ("CMapType".equals(literal.name))
/*     */         {
/* 353 */           Object next = parseNextToken(cmapStream);
/* 354 */           if ((next instanceof Integer))
/*     */           {
/* 356 */             result.setType(((Integer)next).intValue());
/*     */           }
/*     */         }
/* 359 */         else if ("Registry".equals(literal.name))
/*     */         {
/* 361 */           Object next = parseNextToken(cmapStream);
/* 362 */           if ((next instanceof String))
/*     */           {
/* 364 */             result.setRegistry((String)next);
/*     */           }
/*     */         }
/* 367 */         else if ("Ordering".equals(literal.name))
/*     */         {
/* 369 */           Object next = parseNextToken(cmapStream);
/* 370 */           if ((next instanceof String))
/*     */           {
/* 372 */             result.setOrdering((String)next);
/*     */           }
/*     */         }
/* 375 */         else if ("Supplement".equals(literal.name))
/*     */         {
/* 377 */           Object next = parseNextToken(cmapStream);
/* 378 */           if ((next instanceof Integer))
/*     */           {
/* 380 */             result.setSupplement(((Integer)next).intValue());
/*     */           }
/*     */         }
/*     */       }
/* 384 */       previousToken = token;
/*     */     }
/* 386 */     return result;
/*     */   }
/*     */ 
/*     */   private Object parseNextToken(PushbackInputStream is) throws IOException
/*     */   {
/* 391 */     Object retval = null;
/* 392 */     int nextByte = is.read();
/*     */ 
/* 394 */     while ((nextByte == 9) || (nextByte == 32) || (nextByte == 13) || (nextByte == 10))
/*     */     {
/* 396 */       nextByte = is.read();
/*     */     }
/* 398 */     switch (nextByte)
/*     */     {
/*     */     case 37:
/* 404 */       StringBuffer buffer = new StringBuffer();
/* 405 */       buffer.append((char)nextByte);
/* 406 */       readUntilEndOfLine(is, buffer);
/* 407 */       retval = buffer.toString();
/* 408 */       break;
/*     */     case 40:
/* 412 */       StringBuffer buffer = new StringBuffer();
/* 413 */       int stringByte = is.read();
/*     */ 
/* 415 */       while ((stringByte != -1) && (stringByte != 41))
/*     */       {
/* 417 */         buffer.append((char)stringByte);
/* 418 */         stringByte = is.read();
/*     */       }
/* 420 */       retval = buffer.toString();
/* 421 */       break;
/*     */     case 62:
/* 425 */       int secondCloseBrace = is.read();
/* 426 */       if (secondCloseBrace == 62)
/*     */       {
/* 428 */         retval = ">>";
/*     */       }
/*     */       else
/*     */       {
/* 432 */         throw new IOException("Error: expected the end of a dictionary.");
/*     */       }
/*     */ 
/*     */       break;
/*     */     case 93:
/* 438 */       retval = "]";
/* 439 */       break;
/*     */     case 91:
/* 443 */       List list = new ArrayList();
/*     */ 
/* 445 */       Object nextToken = parseNextToken(is);
/* 446 */       while ((nextToken != null) && (nextToken != "]"))
/*     */       {
/* 448 */         list.add(nextToken);
/* 449 */         nextToken = parseNextToken(is);
/*     */       }
/* 451 */       retval = list;
/* 452 */       break;
/*     */     case 60:
/* 456 */       int theNextByte = is.read();
/* 457 */       if (theNextByte == 60)
/*     */       {
/* 459 */         Map result = new HashMap();
/*     */ 
/* 461 */         Object key = parseNextToken(is);
/* 462 */         while (((key instanceof LiteralName)) && (key != ">>"))
/*     */         {
/* 464 */           Object value = parseNextToken(is);
/* 465 */           result.put(((LiteralName)key).name, value);
/* 466 */           key = parseNextToken(is);
/*     */         }
/* 468 */         retval = result;
/*     */       }
/*     */       else
/*     */       {
/* 474 */         int multiplyer = 16;
/* 475 */         int bufferIndex = -1;
/* 476 */         while ((theNextByte != -1) && (theNextByte != 62))
/*     */         {
/* 478 */           int intValue = 0;
/* 479 */           if ((theNextByte >= 48) && (theNextByte <= 57))
/*     */           {
/* 481 */             intValue = theNextByte - 48;
/*     */           }
/* 483 */           else if ((theNextByte >= 65) && (theNextByte <= 70))
/*     */           {
/* 485 */             intValue = 10 + theNextByte - 65;
/*     */           }
/* 487 */           else if ((theNextByte >= 97) && (theNextByte <= 102))
/*     */           {
/* 489 */             intValue = 10 + theNextByte - 97;
/*     */           }
/*     */           else
/*     */           {
/* 493 */             if (isWhitespaceOrEOF(theNextByte))
/*     */             {
/* 496 */               theNextByte = is.read();
/* 497 */               continue;
/*     */             }
/*     */ 
/* 501 */             throw new IOException("Error: expected hex character and not " + (char)theNextByte + ":" + theNextByte);
/*     */           }
/*     */ 
/* 504 */           intValue *= multiplyer;
/* 505 */           if (multiplyer == 16)
/*     */           {
/* 507 */             bufferIndex++;
/* 508 */             this.tokenParserByteBuffer[bufferIndex] = 0;
/* 509 */             multiplyer = 1;
/*     */           }
/*     */           else
/*     */           {
/* 513 */             multiplyer = 16;
/*     */           }
/*     */           int tmp657_655 = bufferIndex;
/*     */           byte[] tmp657_652 = this.tokenParserByteBuffer; tmp657_652[tmp657_655] = ((byte)(tmp657_652[tmp657_655] + intValue));
/* 516 */           theNextByte = is.read();
/*     */         }
/* 518 */         byte[] finalResult = new byte[bufferIndex + 1];
/* 519 */         System.arraycopy(this.tokenParserByteBuffer, 0, finalResult, 0, bufferIndex + 1);
/* 520 */         retval = finalResult;
/*     */       }
/* 522 */       break;
/*     */     case 47:
/* 526 */       StringBuffer buffer = new StringBuffer();
/* 527 */       int stringByte = is.read();
/*     */ 
/* 529 */       while ((!isWhitespaceOrEOF(stringByte)) && (!isDelimiter(stringByte)))
/*     */       {
/* 531 */         buffer.append((char)stringByte);
/* 532 */         stringByte = is.read();
/*     */       }
/* 534 */       if (isDelimiter(stringByte))
/*     */       {
/* 536 */         is.unread(stringByte);
/*     */       }
/* 538 */       retval = new LiteralName(buffer.toString(), null);
/* 539 */       break;
/*     */     case -1:
/* 544 */       break;
/*     */     case 48:
/*     */     case 49:
/*     */     case 50:
/*     */     case 51:
/*     */     case 52:
/*     */     case 53:
/*     */     case 54:
/*     */     case 55:
/*     */     case 56:
/*     */     case 57:
/* 557 */       StringBuffer buffer = new StringBuffer();
/* 558 */       buffer.append((char)nextByte);
/* 559 */       nextByte = is.read();
/*     */ 
/* 561 */       while ((!isWhitespaceOrEOF(nextByte)) && ((Character.isDigit((char)nextByte)) || (nextByte == 46)))
/*     */       {
/* 563 */         buffer.append((char)nextByte);
/* 564 */         nextByte = is.read();
/*     */       }
/* 566 */       is.unread(nextByte);
/* 567 */       String value = buffer.toString();
/* 568 */       if (value.indexOf('.') >= 0)
/*     */       {
/* 570 */         retval = new Double(value);
/*     */       }
/*     */       else
/*     */       {
/* 574 */         retval = new Integer(value);
/*     */       }
/* 576 */       break;
/*     */     default:
/* 580 */       StringBuffer buffer = new StringBuffer();
/* 581 */       buffer.append((char)nextByte);
/* 582 */       nextByte = is.read();
/*     */ 
/* 586 */       while ((!isWhitespaceOrEOF(nextByte)) && (!isDelimiter(nextByte)) && (!Character.isDigit(nextByte)))
/*     */       {
/* 588 */         buffer.append((char)nextByte);
/* 589 */         nextByte = is.read();
/*     */       }
/* 591 */       if ((isDelimiter(nextByte)) || (Character.isDigit(nextByte)))
/*     */       {
/* 593 */         is.unread(nextByte);
/*     */       }
/* 595 */       retval = new Operator(buffer.toString(), null);
/*     */ 
/* 597 */       break;
/*     */     }
/*     */ 
/* 600 */     return retval;
/*     */   }
/*     */ 
/*     */   private void readUntilEndOfLine(InputStream is, StringBuffer buf) throws IOException
/*     */   {
/* 605 */     int nextByte = is.read();
/* 606 */     while ((nextByte != -1) && (nextByte != 13) && (nextByte != 10))
/*     */     {
/* 608 */       buf.append((char)nextByte);
/* 609 */       nextByte = is.read();
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean isWhitespaceOrEOF(int aByte)
/*     */   {
/* 615 */     return (aByte == -1) || (aByte == 32) || (aByte == 13) || (aByte == 10);
/*     */   }
/*     */ 
/*     */   private boolean isDelimiter(int aByte)
/*     */   {
/* 621 */     switch (aByte)
/*     */     {
/*     */     case 37:
/*     */     case 40:
/*     */     case 41:
/*     */     case 47:
/*     */     case 60:
/*     */     case 62:
/*     */     case 91:
/*     */     case 93:
/*     */     case 123:
/*     */     case 125:
/* 633 */       return true;
/*     */     }
/* 635 */     return false;
/*     */   }
/*     */ 
/*     */   private void increment(byte[] data)
/*     */   {
/* 641 */     increment(data, data.length - 1);
/*     */   }
/*     */ 
/*     */   private void increment(byte[] data, int position)
/*     */   {
/* 646 */     if ((position > 0) && ((data[position] + 256) % 256 == 255))
/*     */     {
/* 648 */       data[position] = 0;
/* 649 */       increment(data, position - 1);
/*     */     }
/*     */     else
/*     */     {
/* 653 */       data[position] = ((byte)(data[position] + 1));
/*     */     }
/*     */   }
/*     */ 
/*     */   private int createIntFromBytes(byte[] bytes)
/*     */   {
/* 659 */     int intValue = (bytes[0] + 256) % 256;
/* 660 */     if (bytes.length == 2)
/*     */     {
/* 662 */       intValue <<= 8;
/* 663 */       intValue += (bytes[1] + 256) % 256;
/*     */     }
/* 665 */     return intValue;
/*     */   }
/*     */ 
/*     */   private String createStringFromBytes(byte[] bytes) throws IOException
/*     */   {
/* 670 */     String retval = null;
/* 671 */     if (bytes.length == 1)
/*     */     {
/* 673 */       retval = new String(bytes, "ISO-8859-1");
/*     */     }
/*     */     else
/*     */     {
/* 677 */       retval = new String(bytes, "UTF-16BE");
/*     */     }
/* 679 */     return retval;
/*     */   }
/*     */ 
/*     */   private int compare(byte[] first, byte[] second)
/*     */   {
/* 684 */     int retval = 1;
/* 685 */     int firstLength = first.length;
/* 686 */     for (int i = 0; i < firstLength; i++)
/*     */     {
/* 688 */       if (first[i] != second[i])
/*     */       {
/* 692 */         if ((first[i] + 256) % 256 < (second[i] + 256) % 256)
/*     */         {
/* 694 */           retval = -1;
/* 695 */           break;
/*     */         }
/*     */ 
/* 699 */         retval = 1;
/* 700 */         break;
/*     */       }
/*     */     }
/* 703 */     return retval;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws Exception
/*     */   {
/* 741 */     if (args.length != 1)
/*     */     {
/* 743 */       System.err.println("usage: java org.apache.fontbox.cmap.CMapParser <CMAP File>");
/* 744 */       System.exit(-1);
/*     */     }
/* 746 */     CMapParser parser = new CMapParser();
/* 747 */     File cmapFile = new File(args[0]);
/* 748 */     CMap result = parser.parse(cmapFile);
/* 749 */     System.out.println("Result:" + result);
/*     */   }
/*     */ 
/*     */   private class Operator
/*     */   {
/*     */     private String op;
/*     */ 
/*     */     private Operator(String theOp)
/*     */     {
/* 728 */       this.op = theOp;
/*     */     }
/*     */   }
/*     */ 
/*     */   private class LiteralName
/*     */   {
/*     */     private String name;
/*     */ 
/*     */     private LiteralName(String theName)
/*     */     {
/* 715 */       this.name = theName;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.cmap.CMapParser
 * JD-Core Version:    0.6.2
 */