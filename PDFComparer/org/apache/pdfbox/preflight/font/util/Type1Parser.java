/*     */ package org.apache.pdfbox.preflight.font.util;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.fontbox.cff.IndexData;
/*     */ import org.apache.fontbox.cff.Type1CharStringParser;
/*     */ import org.apache.fontbox.cff.Type1FontUtil;
/*     */ import org.apache.pdfbox.encoding.Encoding;
/*     */ import org.apache.pdfbox.encoding.MacRomanEncoding;
/*     */ import org.apache.pdfbox.encoding.PdfDocEncoding;
/*     */ import org.apache.pdfbox.encoding.StandardEncoding;
/*     */ import org.apache.pdfbox.encoding.WinAnsiEncoding;
/*     */ 
/*     */ public final class Type1Parser
/*     */ {
/*  51 */   public static final Log LOGGER = LogFactory.getLog(Type1Parser.class);
/*     */   protected static final char NAME_START = '/';
/*     */   protected static final String NOTDEF = "/.notdef";
/*     */   protected static final int DEFAULT_LEN_IV = 4;
/*     */   private static final String PS_STANDARD_ENCODING = "StandardEncoding";
/*     */   private static final String PS_ISOLATIN_ENCODING = "ISOLatin1Encoding";
/*     */   private static final String TOKEN_ENCODING = "US-ASCII";
/*  65 */   private PeekInputStream fontProgram = null;
/*     */ 
/*  69 */   private int clearTextSize = 0;
/*     */ 
/*  73 */   private int eexecSize = 0;
/*     */ 
/*  79 */   private int numberOfReadBytes = 0;
/*     */ 
/*  84 */   private Type1 type1Font = null;
/*     */ 
/*     */   private Type1Parser(InputStream type1, int length1, int length2, Encoding enc)
/*     */     throws IOException
/*     */   {
/*  89 */     this.fontProgram = new PeekInputStream(type1);
/*  90 */     this.clearTextSize = length1;
/*  91 */     this.eexecSize = length2;
/*     */ 
/*  93 */     if (enc != null)
/*     */     {
/*  95 */       this.type1Font = new Type1(enc);
/*     */     }
/*     */     else
/*     */     {
/*  99 */       this.type1Font = new Type1(new StandardEncoding());
/*     */     }
/* 101 */     this.type1Font.addCidWithLabel(Integer.valueOf(-1), "/.notdef");
/*     */   }
/*     */ 
/*     */   public static Type1Parser createParser(InputStream fontProgram, int clearTextLength, int eexecLength)
/*     */     throws IOException
/*     */   {
/* 119 */     Encoding encoding = getEncodingObject("");
/* 120 */     return createParserWithEncodingObject(fontProgram, clearTextLength, eexecLength, encoding);
/*     */   }
/*     */ 
/*     */   public static Type1Parser createParserWithEncodingName(InputStream fontProgram, int clearTextLength, int eexecLength, String encodingName)
/*     */     throws IOException
/*     */   {
/* 140 */     Encoding encoding = getEncodingObject(encodingName);
/* 141 */     return createParserWithEncodingObject(fontProgram, clearTextLength, eexecLength, encoding);
/*     */   }
/*     */ 
/*     */   private static Encoding getEncodingObject(String encodingName)
/*     */   {
/* 146 */     Encoding encoding = new StandardEncoding();
/* 147 */     if ("MacRomanEncoding".equals(encodingName))
/*     */     {
/* 149 */       encoding = new MacRomanEncoding();
/*     */     }
/* 151 */     else if ("MacExpertEncoding".equals(encodingName))
/*     */     {
/* 153 */       encoding = new MacRomanEncoding();
/*     */     }
/* 155 */     else if ("WinAnsiEncoding".equals(encodingName))
/*     */     {
/* 157 */       encoding = new WinAnsiEncoding();
/*     */     }
/* 159 */     else if ("PDFDocEncoding".equals(encodingName))
/*     */     {
/* 161 */       encoding = new PdfDocEncoding();
/*     */     }
/* 163 */     return encoding;
/*     */   }
/*     */ 
/*     */   public static Type1Parser createParserWithEncodingObject(InputStream fontProgram, int clearTextLength, int eexecLength, Encoding encoding)
/*     */     throws IOException
/*     */   {
/* 183 */     return new Type1Parser(fontProgram, clearTextLength, eexecLength, encoding);
/*     */   }
/*     */ 
/*     */   public Type1 parse() throws IOException
/*     */   {
/* 188 */     parseClearPartOfFontProgram(this.fontProgram);
/* 189 */     decodeAndParseEExecPart(this.fontProgram);
/* 190 */     return this.type1Font;
/*     */   }
/*     */ 
/*     */   private void parseClearPartOfFontProgram(PeekInputStream stream) throws IOException
/*     */   {
/* 195 */     skipComments(stream);
/* 196 */     parseFontInformationUntilEncodingPart(stream);
/*     */   }
/*     */ 
/*     */   private void decodeAndParseEExecPart(PeekInputStream stream) throws IOException
/*     */   {
/* 201 */     byte[] eexecPart = readEexec(stream);
/* 202 */     byte[] decodedEExecPart = decodeEexec(eexecPart);
/* 203 */     PeekInputStream eexecStream = new PeekInputStream(new ByteArrayInputStream(decodedEExecPart));
/* 204 */     parseEExecPart(eexecStream);
/*     */   }
/*     */ 
/*     */   private void skipComments(PeekInputStream stream) throws IOException
/*     */   {
/* 209 */     int nextChar = stream.peek();
/* 210 */     while (nextChar == 37)
/*     */     {
/* 212 */       if (nextChar == -1)
/*     */       {
/* 214 */         throw new IOException("Unexpected End Of File during a comment parsing");
/*     */       }
/* 216 */       readLine(stream);
/* 217 */       nextChar = stream.peek();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void parseFontInformationUntilEncodingPart(PeekInputStream stream) throws IOException
/*     */   {
/* 223 */     byte[] token = readToken(stream);
/* 224 */     while (!isEExecKeyWord(token))
/*     */     {
/* 227 */       if (isEncodingKeyWord(token))
/*     */       {
/* 229 */         parseEncodingDefinition(stream);
/*     */       }
/* 231 */       token = readToken(stream);
/*     */     }
/*     */ 
/* 234 */     while (!isStartOfEExecReached())
/*     */     {
/* 236 */       readNextCharacter(stream);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void parseEncodingDefinition(PeekInputStream stream) throws IOException
/*     */   {
/* 242 */     byte[] token = readToken(stream);
/* 243 */     String readableToken = new String(token, "US-ASCII");
/* 244 */     if ("ISOLatin1Encoding".equals(readableToken))
/*     */     {
/* 246 */       this.type1Font.initEncodingWithISOLatin1Encoding();
/*     */     }
/* 248 */     else if ("StandardEncoding".equals(readableToken))
/*     */     {
/* 250 */       this.type1Font.initEncodingWithStandardEncoding();
/*     */     }
/*     */     else
/*     */     {
/*     */       try
/*     */       {
/* 256 */         Integer.parseInt(readableToken);
/* 257 */         throwExceptionIfUnexpectedToken("array", readToken(stream));
/* 258 */         readEndSetEncodingValues(stream);
/*     */       }
/*     */       catch (NumberFormatException e)
/*     */       {
/* 262 */         throw new IOException("Invalid encoding : Expected int value before \"array\" key word if the Encoding isn't Standard or ISOLatin");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void parseEExecPart(PeekInputStream stream)
/*     */     throws IOException
/*     */   {
/* 270 */     int lenIV = 4;
/* 271 */     byte[] previousToken = new byte[0];
/* 272 */     while (!isEndOfStream(stream))
/*     */     {
/* 274 */       byte[] token = readToken(stream);
/* 275 */       if (isLenIVKeyWord(token))
/*     */       {
/* 279 */         byte[] l = readToken(stream);
/* 280 */         lenIV = Integer.parseInt(new String(l, "US-ASCII"));
/*     */       }
/* 282 */       else if (isBeginOfBinaryPart(token))
/*     */       {
/*     */         try
/*     */         {
/* 286 */           int lengthOfBinaryPart = Integer.parseInt(new String(previousToken, "US-ASCII"));
/* 287 */           skipSingleBlankSeparator(stream);
/* 288 */           stream.read(new byte[lengthOfBinaryPart], 0, lengthOfBinaryPart);
/* 289 */           token = readToken(stream);
/*     */         }
/*     */         catch (NumberFormatException e)
/*     */         {
/* 293 */           throw new IOException("Binary part found but previous token wasn't an integer");
/*     */         }
/*     */       }
/* 296 */       else if (isCharStringKeyWord(token))
/*     */       {
/* 298 */         parseCharStringArray(stream, lenIV);
/*     */       }
/* 300 */       previousToken = token;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void parseCharStringArray(PeekInputStream stream, int lenIV) throws IOException
/*     */   {
/* 306 */     int numberOfElements = readNumberOfCharStrings(stream);
/* 307 */     goToBeginOfCharStringElements(stream);
/*     */ 
/* 309 */     while (numberOfElements > 0)
/*     */     {
/* 311 */       byte[] labelToken = readToken(stream);
/* 312 */       String label = new String(labelToken, "US-ASCII");
/*     */ 
/* 314 */       if (label.equals("end"))
/*     */       {
/* 317 */         LOGGER.warn("[Type 1] Invalid number of elements in the CharString");
/* 318 */         break;
/*     */       }
/*     */ 
/* 321 */       byte[] sizeOfCharStringToken = readToken(stream);
/* 322 */       int sizeOfCharString = Integer.parseInt(new String(sizeOfCharStringToken, "US-ASCII"));
/*     */ 
/* 324 */       readToken(stream);
/* 325 */       skipSingleBlankSeparator(stream);
/*     */ 
/* 327 */       byte[] descBinary = new byte[sizeOfCharString];
/* 328 */       stream.read(descBinary, 0, sizeOfCharString);
/* 329 */       byte[] description = Type1FontUtil.charstringDecrypt(descBinary, lenIV);
/* 330 */       Type1CharStringParser t1p = new Type1CharStringParser();
/*     */ 
/* 332 */       List operations = t1p.parse(description, new IndexData(0));
/* 333 */       this.type1Font.addGlyphDescription(label, new GlyphDescription(operations));
/*     */ 
/* 335 */       readToken(stream);
/* 336 */       numberOfElements--;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void goToBeginOfCharStringElements(PeekInputStream stream) throws IOException
/*     */   {
/* 342 */     byte[] token = new byte[0];
/*     */     do
/*     */     {
/* 345 */       token = readToken(stream);
/* 346 */     }while (isNotBeginKeyWord(token));
/*     */   }
/*     */ 
/*     */   private boolean isNotBeginKeyWord(byte[] token) throws IOException
/*     */   {
/* 351 */     String word = new String(token, "US-ASCII");
/* 352 */     return !"begin".equals(word);
/*     */   }
/*     */ 
/*     */   private boolean isBeginOfBinaryPart(byte[] token) throws IOException
/*     */   {
/* 357 */     String word = new String(token, "US-ASCII");
/* 358 */     return ("RD".equals(word)) || ("-|".equals(word));
/*     */   }
/*     */ 
/*     */   private boolean isLenIVKeyWord(byte[] token) throws IOException
/*     */   {
/* 363 */     String word = new String(token, "US-ASCII");
/* 364 */     return "/lenIV".equals(word);
/*     */   }
/*     */ 
/*     */   private boolean isCharStringKeyWord(byte[] token) throws IOException
/*     */   {
/* 369 */     String word = new String(token, "US-ASCII");
/* 370 */     return "/CharStrings".equals(word);
/*     */   }
/*     */ 
/*     */   private int readNumberOfCharStrings(PeekInputStream stream) throws IOException
/*     */   {
/* 375 */     byte[] token = readToken(stream);
/* 376 */     String word = new String(token, "US-ASCII");
/*     */     try
/*     */     {
/* 379 */       return Integer.parseInt(word);
/*     */     }
/*     */     catch (NumberFormatException e) {
/*     */     }
/* 383 */     throw new IOException("Number of CharStrings elements is expected.");
/*     */   }
/*     */ 
/*     */   private void throwExceptionIfUnexpectedToken(String expectedValue, byte[] token)
/*     */     throws IOException
/*     */   {
/* 389 */     String valueToCheck = new String(token, "US-ASCII");
/* 390 */     if (!expectedValue.equals(valueToCheck))
/*     */     {
/* 392 */       throw new IOException(expectedValue + " was expected but we received " + valueToCheck);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void readEndSetEncodingValues(PeekInputStream stream) throws IOException
/*     */   {
/* 398 */     byte[] token = readToken(stream);
/* 399 */     boolean lastTokenWasReadOnly = false;
/* 400 */     while ((!lastTokenWasReadOnly) || (!isDefKeyWord(token)))
/*     */     {
/* 402 */       if (isDupKeyWord(token))
/*     */       {
/* 404 */         byte[] cidToken = readToken(stream);
/* 405 */         byte[] labelToken = readToken(stream);
/* 406 */         String cid = new String(cidToken, "US-ASCII");
/* 407 */         String label = new String(labelToken, "US-ASCII");
/*     */         try
/*     */         {
/* 410 */           this.type1Font.addCidWithLabel(Integer.valueOf(Integer.parseInt(cid)), label);
/*     */         }
/*     */         catch (NumberFormatException e)
/*     */         {
/* 414 */           throw new IOException("Invalid encoding : Expected CID value before \"" + label + "\" label");
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 419 */         lastTokenWasReadOnly = isReadOnlyKeyWord(token);
/*     */       }
/* 421 */       token = readToken(stream);
/*     */     }
/*     */   }
/*     */ 
/*     */   private byte[] readEexec(PeekInputStream stream) throws IOException
/*     */   {
/* 427 */     int BUFFER_SIZE = 1024;
/* 428 */     byte[] buffer = new byte[BUFFER_SIZE];
/* 429 */     ByteArrayOutputStream eexecPart = new ByteArrayOutputStream();
/* 430 */     int lr = 0;
/* 431 */     int total = 0;
/*     */     do
/*     */     {
/* 434 */       lr = stream.read(buffer, 0, BUFFER_SIZE);
/* 435 */       if ((lr == BUFFER_SIZE) && (total + BUFFER_SIZE < this.eexecSize))
/*     */       {
/* 437 */         eexecPart.write(buffer, 0, BUFFER_SIZE);
/* 438 */         total += BUFFER_SIZE;
/*     */       }
/* 440 */       else if ((lr > 0) && (total + lr < this.eexecSize))
/*     */       {
/* 442 */         eexecPart.write(buffer, 0, lr);
/* 443 */         total += lr;
/*     */       }
/* 445 */       else if ((lr > 0) && (total + lr >= this.eexecSize))
/*     */       {
/* 447 */         eexecPart.write(buffer, 0, this.eexecSize - total);
/* 448 */         total += this.eexecSize - total;
/*     */       }
/*     */     }
/* 450 */     while ((this.eexecSize > total) && (lr > 0));
/* 451 */     IOUtils.closeQuietly(eexecPart);
/* 452 */     return eexecPart.toByteArray();
/*     */   }
/*     */ 
/*     */   private byte[] decodeEexec(byte[] eexec)
/*     */   {
/* 457 */     return Type1FontUtil.eexecDecrypt(eexec);
/*     */   }
/*     */ 
/*     */   private byte[] readLine(PeekInputStream stream) throws IOException
/*     */   {
/* 462 */     ArrayList bytes = new ArrayList();
/* 463 */     int currentCharacter = 0;
/*     */     do
/*     */     {
/* 467 */       currentCharacter = readNextCharacter(stream);
/* 468 */       bytes.add(Byte.valueOf((byte)(currentCharacter & 0xFF)));
/* 469 */     }while ((10 != currentCharacter) && (13 != currentCharacter));
/*     */ 
/* 471 */     if ((13 == currentCharacter) && (10 == stream.peek()))
/*     */     {
/* 473 */       currentCharacter = readNextCharacter(stream);
/* 474 */       bytes.add(Byte.valueOf((byte)(currentCharacter & 0xFF)));
/*     */     }
/*     */ 
/* 477 */     byte[] result = new byte[bytes.size()];
/* 478 */     for (int i = 0; i < bytes.size(); i++)
/*     */     {
/* 480 */       result[i] = ((Byte)bytes.get(i)).byteValue();
/*     */     }
/* 482 */     return result;
/*     */   }
/*     */ 
/*     */   private byte[] readToken(PeekInputStream stream) throws IOException
/*     */   {
/* 487 */     byte[] token = new byte[0];
/* 488 */     skipBlankSeparators(stream);
/*     */ 
/* 490 */     int nextByte = stream.peek();
/* 491 */     if (nextByte < 0)
/*     */     {
/* 493 */       throw new IOException("Unexpected End Of File");
/*     */     }
/*     */ 
/* 496 */     if (nextByte == 40)
/*     */     {
/* 498 */       token = readStringLiteral(stream);
/*     */     }
/* 500 */     else if (nextByte == 91)
/*     */     {
/* 502 */       token = readArray(stream);
/*     */     }
/* 504 */     else if (nextByte == 123)
/*     */     {
/* 506 */       token = readProcedure(stream);
/*     */     }
/*     */     else
/*     */     {
/* 510 */       token = readNameOrArgument(stream);
/*     */     }
/*     */ 
/* 513 */     return token;
/*     */   }
/*     */ 
/*     */   private byte[] readStringLiteral(PeekInputStream stream) throws IOException
/*     */   {
/* 518 */     int opened = 0;
/* 519 */     List buffer = new ArrayList();
/*     */ 
/* 521 */     int currentByte = 0;
/*     */     do
/*     */     {
/* 524 */       currentByte = readNextCharacter(stream);
/* 525 */       if (currentByte < 0)
/*     */       {
/* 527 */         throw new IOException("Unexpected End Of File");
/*     */       }
/*     */ 
/* 530 */       if (currentByte == 40)
/*     */       {
/* 532 */         opened++;
/*     */       }
/* 534 */       else if (currentByte == 41)
/*     */       {
/* 536 */         opened--;
/*     */       }
/*     */ 
/* 539 */       buffer.add(Integer.valueOf(currentByte));
/* 540 */     }while (opened != 0);
/*     */ 
/* 542 */     return convertListOfIntToByteArray(buffer);
/*     */   }
/*     */ 
/*     */   private byte[] readArray(PeekInputStream stream) throws IOException
/*     */   {
/* 547 */     int opened = 0;
/* 548 */     List buffer = new ArrayList();
/*     */ 
/* 550 */     int currentByte = 0;
/*     */     do
/*     */     {
/* 553 */       currentByte = readNextCharacter(stream);
/* 554 */       if (currentByte < 0)
/*     */       {
/* 556 */         throw new IOException("Unexpected End Of File");
/*     */       }
/*     */ 
/* 559 */       if (currentByte == 91)
/*     */       {
/* 561 */         opened++;
/*     */       }
/* 563 */       else if (currentByte == 93)
/*     */       {
/* 565 */         opened--;
/*     */       }
/*     */ 
/* 568 */       buffer.add(Integer.valueOf(currentByte));
/* 569 */     }while (opened != 0);
/*     */ 
/* 571 */     return convertListOfIntToByteArray(buffer);
/*     */   }
/*     */ 
/*     */   private byte[] readProcedure(PeekInputStream stream) throws IOException
/*     */   {
/* 576 */     int opened = 0;
/* 577 */     List buffer = new ArrayList();
/*     */ 
/* 579 */     int currentByte = 0;
/*     */     do
/*     */     {
/* 582 */       currentByte = readNextCharacter(stream);
/* 583 */       if (currentByte < 0)
/*     */       {
/* 585 */         throw new IOException("Unexpected End Of File");
/*     */       }
/*     */ 
/* 588 */       if (currentByte == 123)
/*     */       {
/* 590 */         opened++;
/*     */       }
/* 592 */       else if (currentByte == 125)
/*     */       {
/* 594 */         opened--;
/*     */       }
/*     */ 
/* 597 */       buffer.add(Integer.valueOf(currentByte));
/* 598 */     }while (opened != 0);
/*     */ 
/* 600 */     return convertListOfIntToByteArray(buffer);
/*     */   }
/*     */ 
/*     */   private byte[] readNameOrArgument(PeekInputStream stream) throws IOException
/*     */   {
/* 605 */     List buffer = new ArrayList();
/* 606 */     int nextByte = 0;
/*     */     do
/*     */     {
/* 609 */       int currentByte = readNextCharacter(stream);
/* 610 */       if (currentByte < 0)
/*     */       {
/* 612 */         throw new IOException("Unexpected End Of File");
/*     */       }
/* 614 */       buffer.add(Integer.valueOf(currentByte));
/* 615 */       nextByte = stream.peek();
/* 616 */     }while ((isNotBlankSperator(nextByte)) && (isNotBeginOfName(nextByte)) && (isNotSeparator(nextByte)));
/*     */ 
/* 618 */     return convertListOfIntToByteArray(buffer);
/*     */   }
/*     */ 
/*     */   private boolean isNotBeginOfName(int character)
/*     */   {
/* 623 */     return 47 != character;
/*     */   }
/*     */ 
/*     */   private boolean isNotSeparator(int character)
/*     */   {
/* 628 */     return (123 != character) && (125 != character) && (91 != character) && (93 != character);
/*     */   }
/*     */ 
/*     */   private byte[] convertListOfIntToByteArray(List<Integer> input)
/*     */   {
/* 633 */     byte[] res = new byte[input.size()];
/* 634 */     for (int i = 0; i < res.length; i++)
/*     */     {
/* 636 */       res[i] = ((Integer)input.get(i)).byteValue();
/*     */     }
/* 638 */     return res;
/*     */   }
/*     */ 
/*     */   private int readNextCharacter(PeekInputStream stream) throws IOException
/*     */   {
/* 643 */     int currentByte = stream.read();
/* 644 */     this.numberOfReadBytes += 1;
/* 645 */     return currentByte;
/*     */   }
/*     */ 
/*     */   private void skipBlankSeparators(PeekInputStream stream) throws IOException
/*     */   {
/* 650 */     int nextByte = stream.peek();
/* 651 */     while (isBlankSperator(nextByte))
/*     */     {
/* 653 */       readNextCharacter(stream);
/* 654 */       nextByte = stream.peek();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void skipSingleBlankSeparator(PeekInputStream stream) throws IOException
/*     */   {
/* 660 */     int nextByte = stream.peek();
/* 661 */     if (isBlankSperator(nextByte))
/*     */     {
/* 663 */       readNextCharacter(stream);
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean isBlankSperator(int character)
/*     */   {
/* 669 */     return (character == 32) || (character == 10) || (character == 13);
/*     */   }
/*     */ 
/*     */   private boolean isNotBlankSperator(int character)
/*     */   {
/* 674 */     return !isBlankSperator(character);
/*     */   }
/*     */ 
/*     */   private boolean isEExecKeyWord(byte[] token) throws IOException
/*     */   {
/* 679 */     String word = new String(token, "US-ASCII");
/* 680 */     return "eexec".equals(word);
/*     */   }
/*     */ 
/*     */   private boolean isDefKeyWord(byte[] token) throws IOException
/*     */   {
/* 685 */     String word = new String(token, "US-ASCII");
/* 686 */     return "def".equals(word);
/*     */   }
/*     */ 
/*     */   private boolean isReadOnlyKeyWord(byte[] token) throws IOException
/*     */   {
/* 691 */     String word = new String(token, "US-ASCII");
/* 692 */     return "readonly".equals(word);
/*     */   }
/*     */ 
/*     */   private boolean isEncodingKeyWord(byte[] token) throws IOException
/*     */   {
/* 697 */     String word = new String(token, "US-ASCII");
/* 698 */     return "/Encoding".equals(word);
/*     */   }
/*     */ 
/*     */   private boolean isDupKeyWord(byte[] token) throws IOException
/*     */   {
/* 703 */     String word = new String(token, "US-ASCII");
/* 704 */     return "dup".equals(word);
/*     */   }
/*     */ 
/*     */   private boolean isStartOfEExecReached()
/*     */   {
/* 709 */     return this.numberOfReadBytes == this.clearTextSize;
/*     */   }
/*     */ 
/*     */   private boolean isEndOfStream(PeekInputStream stream)
/*     */   {
/*     */     try
/*     */     {
/* 716 */       skipBlankSeparators(stream);
/* 717 */       return false;
/*     */     }
/*     */     catch (IOException e) {
/*     */     }
/* 721 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.font.util.Type1Parser
 * JD-Core Version:    0.6.2
 */