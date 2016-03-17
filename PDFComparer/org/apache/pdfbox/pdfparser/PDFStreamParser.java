/*     */ package org.apache.pdfbox.pdfparser;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PushbackInputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSBoolean;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSNull;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ import org.apache.pdfbox.cos.COSObject;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.io.PushBackInputStream;
/*     */ import org.apache.pdfbox.io.RandomAccess;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ import org.apache.pdfbox.util.ImageParameters;
/*     */ import org.apache.pdfbox.util.PDFOperator;
/*     */ 
/*     */ public class PDFStreamParser extends BaseParser
/*     */ {
/*  54 */   private static final Log LOG = LogFactory.getLog(PDFStreamParser.class);
/*     */ 
/*  56 */   private List<Object> streamObjects = new ArrayList(100);
/*     */   private final RandomAccess file;
/*  58 */   private final int maxBinCharTestLength = 10;
/*  59 */   private final byte[] binCharTestArr = new byte[10];
/*     */ 
/*     */   public PDFStreamParser(InputStream stream, RandomAccess raf, boolean forceParsing)
/*     */     throws IOException
/*     */   {
/*  75 */     super(stream, forceParsing);
/*  76 */     this.file = raf;
/*     */   }
/*     */ 
/*     */   public PDFStreamParser(InputStream stream, RandomAccess raf)
/*     */     throws IOException
/*     */   {
/*  90 */     this(stream, raf, FORCE_PARSING);
/*     */   }
/*     */ 
/*     */   public PDFStreamParser(PDStream stream)
/*     */     throws IOException
/*     */   {
/* 102 */     this(stream.createInputStream(), stream.getStream().getScratchFile());
/*     */   }
/*     */ 
/*     */   public PDFStreamParser(COSStream stream, boolean forceParsing)
/*     */     throws IOException
/*     */   {
/* 117 */     this(stream.getUnfilteredStream(), stream.getScratchFile(), forceParsing);
/*     */   }
/*     */ 
/*     */   public PDFStreamParser(COSStream stream)
/*     */     throws IOException
/*     */   {
/* 129 */     this(stream.getUnfilteredStream(), stream.getScratchFile());
/*     */   }
/*     */ 
/*     */   public void parse()
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/*     */       Object token;
/* 143 */       while ((token = parseNextToken()) != null)
/*     */       {
/* 145 */         this.streamObjects.add(token);
/*     */       }
/*     */ 
/*     */     }
/*     */     finally
/*     */     {
/* 151 */       this.pdfSource.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public List<Object> getTokens()
/*     */   {
/* 162 */     return this.streamObjects;
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 172 */     this.pdfSource.close();
/*     */   }
/*     */ 
/*     */   public Iterator<Object> getTokenIterator()
/*     */   {
/* 183 */     return new Iterator()
/*     */     {
/*     */       private Object token;
/*     */ 
/*     */       private void tryNext()
/*     */       {
/*     */         try
/*     */         {
/* 191 */           if (this.token == null)
/*     */           {
/* 193 */             this.token = PDFStreamParser.this.parseNextToken();
/*     */           }
/*     */         }
/*     */         catch (IOException e)
/*     */         {
/* 198 */           throw new RuntimeException(e);
/*     */         }
/*     */       }
/*     */ 
/*     */       public boolean hasNext()
/*     */       {
/* 205 */         tryNext();
/* 206 */         return this.token != null;
/*     */       }
/*     */ 
/*     */       public Object next()
/*     */       {
/* 212 */         tryNext();
/* 213 */         Object tmp = this.token;
/* 214 */         if (tmp == null)
/*     */         {
/* 216 */           throw new NoSuchElementException();
/*     */         }
/* 218 */         this.token = null;
/* 219 */         return tmp;
/*     */       }
/*     */ 
/*     */       public void remove()
/*     */       {
/* 225 */         throw new UnsupportedOperationException();
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   private Object parseNextToken()
/*     */     throws IOException
/*     */   {
/* 241 */     skipSpaces();
/* 242 */     int nextByte = this.pdfSource.peek();
/* 243 */     if ((byte)nextByte == -1)
/*     */     {
/* 245 */       return null;
/*     */     }
/* 247 */     char c = (char)nextByte;
/*     */     Object retval;
/*     */     Object retval;
/*     */     Object retval;
/*     */     Object retval;
/*     */     Object retval;
/* 248 */     switch (c)
/*     */     {
/*     */     case '<':
/* 252 */       int leftBracket = this.pdfSource.read();
/* 253 */       c = (char)this.pdfSource.peek();
/* 254 */       this.pdfSource.unread(leftBracket);
/*     */       Object retval;
/* 255 */       if (c == '<')
/*     */       {
/* 257 */         COSDictionary pod = parseCOSDictionary();
/* 258 */         skipSpaces();
/*     */         Object retval;
/* 259 */         if ((char)this.pdfSource.peek() == 's')
/*     */         {
/* 261 */           retval = parseCOSStream(pod, this.file);
/*     */         }
/*     */         else
/*     */         {
/* 265 */           retval = pod;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 270 */         retval = parseCOSString();
/*     */       }
/* 272 */       break;
/*     */     case '[':
/* 276 */       retval = parseCOSArray();
/* 277 */       break;
/*     */     case '(':
/* 280 */       retval = parseCOSString();
/* 281 */       break;
/*     */     case '/':
/* 283 */       retval = parseCOSName();
/* 284 */       break;
/*     */     case 'n':
/* 287 */       String nullString = readString();
/* 288 */       if (nullString.equals("null"))
/*     */       {
/* 290 */         retval = COSNull.NULL;
/*     */       }
/*     */       else
/*     */       {
/* 294 */         retval = PDFOperator.getOperator(nullString);
/*     */       }
/* 296 */       break;
/*     */     case 'f':
/*     */     case 't':
/* 301 */       String next = readString();
/* 302 */       if (next.equals("true"))
/*     */       {
/* 304 */         retval = COSBoolean.TRUE;
/*     */       }
/*     */       else
/*     */       {
/*     */         Object retval;
/* 307 */         if (next.equals("false"))
/*     */         {
/* 309 */           retval = COSBoolean.FALSE;
/*     */         }
/*     */         else
/*     */         {
/* 313 */           retval = PDFOperator.getOperator(next);
/*     */         }
/*     */       }
/* 315 */       break;
/*     */     case 'R':
/* 319 */       String line = readString();
/* 320 */       if (line.equals("R"))
/*     */       {
/* 322 */         retval = new COSObject(null);
/*     */       }
/*     */       else
/*     */       {
/* 326 */         retval = PDFOperator.getOperator(line);
/*     */       }
/* 328 */       break;
/*     */     case '+':
/*     */     case '-':
/*     */     case '.':
/*     */     case '0':
/*     */     case '1':
/*     */     case '2':
/*     */     case '3':
/*     */     case '4':
/*     */     case '5':
/*     */     case '6':
/*     */     case '7':
/*     */     case '8':
/*     */     case '9':
/* 346 */       StringBuffer buf = new StringBuffer();
/* 347 */       buf.append(c);
/* 348 */       this.pdfSource.read();
/*     */ 
/* 350 */       boolean dotNotRead = c != '.';
/* 351 */       while ((Character.isDigit(c = (char)this.pdfSource.peek())) || ((dotNotRead) && (c == '.')))
/*     */       {
/* 353 */         buf.append(c);
/* 354 */         this.pdfSource.read();
/*     */ 
/* 356 */         if ((dotNotRead) && (c == '.'))
/*     */         {
/* 358 */           dotNotRead = false;
/*     */         }
/*     */       }
/* 361 */       retval = COSNumber.get(buf.toString());
/* 362 */       break;
/*     */     case 'B':
/* 366 */       String next = readString();
/* 367 */       retval = PDFOperator.getOperator(next);
/* 368 */       if (next.equals("BI"))
/*     */       {
/* 370 */         PDFOperator beginImageOP = (PDFOperator)retval;
/* 371 */         COSDictionary imageParams = new COSDictionary();
/* 372 */         beginImageOP.setImageParameters(new ImageParameters(imageParams));
/* 373 */         Object nextToken = null;
/* 374 */         while (((nextToken = parseNextToken()) instanceof COSName))
/*     */         {
/* 376 */           Object value = parseNextToken();
/* 377 */           imageParams.setItem((COSName)nextToken, (COSBase)value);
/*     */         }
/*     */ 
/* 380 */         PDFOperator imageData = (PDFOperator)nextToken;
/* 381 */         beginImageOP.setImageData(imageData.getImageData());
/* 382 */       }break;
/*     */     case 'I':
/* 388 */       String id = "" + (char)this.pdfSource.read() + (char)this.pdfSource.read();
/* 389 */       if (!id.equals("ID"))
/*     */       {
/* 391 */         throw new IOException("Error: Expected operator 'ID' actual='" + id + "'");
/*     */       }
/* 393 */       ByteArrayOutputStream imageData = new ByteArrayOutputStream();
/* 394 */       if (isWhitespace())
/*     */       {
/* 397 */         this.pdfSource.read();
/*     */       }
/* 399 */       int lastByte = this.pdfSource.read();
/* 400 */       int currentByte = this.pdfSource.read();
/*     */ 
/* 408 */       while (((lastByte != 69) || (currentByte != 73) || (!hasNextSpaceOrReturn()) || (!hasNoFollowingBinData(this.pdfSource))) && (!this.pdfSource.isEOF()))
/*     */       {
/* 411 */         imageData.write(lastByte);
/* 412 */         lastByte = currentByte;
/* 413 */         currentByte = this.pdfSource.read();
/*     */       }
/*     */ 
/* 416 */       retval = PDFOperator.getOperator("ID");
/*     */ 
/* 418 */       ((PDFOperator)retval).setImageData(imageData.toByteArray());
/* 419 */       break;
/*     */     case ']':
/* 425 */       this.pdfSource.read();
/* 426 */       retval = COSNull.NULL;
/* 427 */       break;
/*     */     case ')':
/*     */     case '*':
/*     */     case ',':
/*     */     case ':':
/*     */     case ';':
/*     */     case '=':
/*     */     case '>':
/*     */     case '?':
/*     */     case '@':
/*     */     case 'A':
/*     */     case 'C':
/*     */     case 'D':
/*     */     case 'E':
/*     */     case 'F':
/*     */     case 'G':
/*     */     case 'H':
/*     */     case 'J':
/*     */     case 'K':
/*     */     case 'L':
/*     */     case 'M':
/*     */     case 'N':
/*     */     case 'O':
/*     */     case 'P':
/*     */     case 'Q':
/*     */     case 'S':
/*     */     case 'T':
/*     */     case 'U':
/*     */     case 'V':
/*     */     case 'W':
/*     */     case 'X':
/*     */     case 'Y':
/*     */     case 'Z':
/*     */     case '\\':
/*     */     case '^':
/*     */     case '_':
/*     */     case '`':
/*     */     case 'a':
/*     */     case 'b':
/*     */     case 'c':
/*     */     case 'd':
/*     */     case 'e':
/*     */     case 'g':
/*     */     case 'h':
/*     */     case 'i':
/*     */     case 'j':
/*     */     case 'k':
/*     */     case 'l':
/*     */     case 'm':
/*     */     case 'o':
/*     */     case 'p':
/*     */     case 'q':
/*     */     case 'r':
/*     */     case 's':
/*     */     default:
/* 432 */       String operator = readOperator();
/* 433 */       if (operator.trim().length() == 0)
/*     */       {
/* 436 */         retval = null;
/*     */       }
/*     */       else
/*     */       {
/* 440 */         retval = PDFOperator.getOperator(operator);
/*     */       }
/*     */       break;
/*     */     }
/* 444 */     return retval;
/*     */   }
/*     */ 
/*     */   private boolean hasNoFollowingBinData(PushbackInputStream pdfSource)
/*     */     throws IOException
/*     */   {
/* 459 */     int readBytes = pdfSource.read(this.binCharTestArr, 0, 10);
/* 460 */     boolean noBinData = true;
/* 461 */     int startOpIdx = -1;
/* 462 */     int endOpIdx = -1;
/*     */ 
/* 464 */     if (readBytes > 0)
/*     */     {
/* 466 */       for (int bIdx = 0; bIdx < readBytes; bIdx++)
/*     */       {
/* 468 */         byte b = this.binCharTestArr[bIdx];
/* 469 */         if ((b < 9) || ((b > 10) && (b < 32) && (b != 13)))
/*     */         {
/* 472 */           noBinData = false;
/* 473 */           break;
/*     */         }
/*     */ 
/* 476 */         if ((startOpIdx == -1) && (b != 9) && (b != 32) && (b != 10) && (b != 13))
/*     */         {
/* 478 */           startOpIdx = bIdx;
/*     */         }
/* 480 */         else if ((startOpIdx != -1) && (endOpIdx == -1) && ((b == 9) || (b == 32) || (b == 10) || (b == 13)))
/*     */         {
/* 483 */           endOpIdx = bIdx;
/*     */         }
/*     */       }
/* 486 */       if (readBytes == 10)
/*     */       {
/* 489 */         if ((startOpIdx != -1) && (endOpIdx == -1))
/*     */         {
/* 491 */           endOpIdx = 10;
/*     */         }
/* 493 */         if ((endOpIdx != -1) && (startOpIdx != -1) && (endOpIdx - startOpIdx > 3))
/*     */         {
/* 495 */           noBinData = false;
/*     */         }
/*     */       }
/* 498 */       pdfSource.unread(this.binCharTestArr, 0, readBytes);
/*     */     }
/* 500 */     if (!noBinData)
/*     */     {
/* 502 */       LOG.warn("ignoring 'EI' assumed to be in the middle of inline image");
/*     */     }
/* 504 */     return noBinData;
/*     */   }
/*     */ 
/*     */   protected String readOperator()
/*     */     throws IOException
/*     */   {
/* 516 */     skipSpaces();
/*     */ 
/* 520 */     StringBuffer buffer = new StringBuffer(4);
/* 521 */     int nextChar = this.pdfSource.peek();
/*     */ 
/* 529 */     while ((nextChar != -1) && (!isWhitespace(nextChar)) && (!isClosing(nextChar)) && (nextChar != 91) && (nextChar != 60) && (nextChar != 40) && (nextChar != 47) && ((nextChar < 48) || (nextChar > 57)))
/*     */     {
/* 533 */       char currentChar = (char)this.pdfSource.read();
/* 534 */       nextChar = this.pdfSource.peek();
/* 535 */       buffer.append(currentChar);
/*     */ 
/* 537 */       if ((currentChar == 'd') && ((nextChar == 48) || (nextChar == 49)))
/*     */       {
/* 539 */         buffer.append((char)this.pdfSource.read());
/* 540 */         nextChar = this.pdfSource.peek();
/*     */       }
/*     */     }
/* 543 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */   private boolean isSpaceOrReturn(int c)
/*     */   {
/* 549 */     return (c == 10) || (c == 13) || (c == 32);
/*     */   }
/*     */ 
/*     */   private boolean hasNextSpaceOrReturn()
/*     */     throws IOException
/*     */   {
/* 560 */     return isSpaceOrReturn(this.pdfSource.peek());
/*     */   }
/*     */ 
/*     */   public void clearResources()
/*     */   {
/* 569 */     super.clearResources();
/* 570 */     if (this.streamObjects != null)
/*     */     {
/* 572 */       this.streamObjects.clear();
/* 573 */       this.streamObjects = null;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdfparser.PDFStreamParser
 * JD-Core Version:    0.6.2
 */