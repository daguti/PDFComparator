/*      */ package org.apache.pdfbox.pdfparser;
/*      */ 
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import org.apache.commons.logging.Log;
/*      */ import org.apache.commons.logging.LogFactory;
/*      */ import org.apache.pdfbox.cos.COSArray;
/*      */ import org.apache.pdfbox.cos.COSBase;
/*      */ import org.apache.pdfbox.cos.COSBoolean;
/*      */ import org.apache.pdfbox.cos.COSDictionary;
/*      */ import org.apache.pdfbox.cos.COSDocument;
/*      */ import org.apache.pdfbox.cos.COSInteger;
/*      */ import org.apache.pdfbox.cos.COSName;
/*      */ import org.apache.pdfbox.cos.COSNull;
/*      */ import org.apache.pdfbox.cos.COSNumber;
/*      */ import org.apache.pdfbox.cos.COSObject;
/*      */ import org.apache.pdfbox.cos.COSStream;
/*      */ import org.apache.pdfbox.cos.COSString;
/*      */ import org.apache.pdfbox.exceptions.WrappedIOException;
/*      */ import org.apache.pdfbox.io.IOUtils;
/*      */ import org.apache.pdfbox.io.PushBackInputStream;
/*      */ import org.apache.pdfbox.io.RandomAccess;
/*      */ import org.apache.pdfbox.persistence.util.COSObjectKey;
/*      */ 
/*      */ public abstract class BaseParser
/*      */ {
/*      */   private static final long OBJECT_NUMBER_THRESHOLD = 10000000000L;
/*      */   private static final long GENERATION_NUMBER_THRESHOLD = 65535L;
/*      */   public static final String PROP_PUSHBACK_SIZE = "org.apache.pdfbox.baseParser.pushBackSize";
/*   68 */   private static final Log LOG = LogFactory.getLog(BaseParser.class);
/*      */   private static final int E = 101;
/*      */   private static final int N = 110;
/*      */   private static final int D = 100;
/*      */   private static final int S = 115;
/*      */   private static final int T = 116;
/*      */   private static final int R = 114;
/*      */   private static final int A = 97;
/*      */   private static final int M = 109;
/*      */   private static final int O = 111;
/*      */   private static final int B = 98;
/*      */   private static final int J = 106;
/*   84 */   private final int strmBufLen = 2048;
/*   85 */   private final byte[] strmBuf = new byte[2048];
/*      */ 
/*   90 */   public static final byte[] ENDSTREAM = { 101, 110, 100, 115, 116, 114, 101, 97, 109 };
/*      */ 
/*   96 */   public static final byte[] ENDOBJ = { 101, 110, 100, 111, 98, 106 };
/*      */   public static final String DEF = "def";
/*      */   private static final String ENDOBJ_STRING = "endobj";
/*      */   private static final String ENDSTREAM_STRING = "endstream";
/*      */   private static final String STREAM_STRING = "stream";
/*      */   private static final String TRUE = "true";
/*      */   private static final String FALSE = "false";
/*      */   private static final String NULL = "null";
/*  131 */   static boolean FORCE_PARSING = true;
/*      */   protected PushBackInputStream pdfSource;
/*      */   protected COSDocument document;
/*      */   protected final boolean forceParsing;
/*      */ 
/*      */   public BaseParser()
/*      */   {
/*  167 */     this.forceParsing = FORCE_PARSING;
/*      */   }
/*      */ 
/*      */   public BaseParser(InputStream input, boolean forceParsingValue)
/*      */     throws IOException
/*      */   {
/*  182 */     int pushbacksize = 65536;
/*      */     try
/*      */     {
/*  185 */       pushbacksize = Integer.getInteger("org.apache.pdfbox.baseParser.pushBackSize", 65536).intValue();
/*      */     }
/*      */     catch (SecurityException e)
/*      */     {
/*      */     }
/*      */ 
/*  191 */     this.pdfSource = new PushBackInputStream(new BufferedInputStream(input, 16384), pushbacksize);
/*      */ 
/*  193 */     this.forceParsing = forceParsingValue;
/*      */   }
/*      */ 
/*      */   public BaseParser(InputStream input)
/*      */     throws IOException
/*      */   {
/*  204 */     this(input, FORCE_PARSING);
/*      */   }
/*      */ 
/*      */   protected BaseParser(byte[] input)
/*      */     throws IOException
/*      */   {
/*  215 */     this(new ByteArrayInputStream(input));
/*      */   }
/*      */ 
/*      */   public void setDocument(COSDocument doc)
/*      */   {
/*  225 */     this.document = doc;
/*      */   }
/*      */ 
/*      */   private static boolean isHexDigit(char ch)
/*      */   {
/*  230 */     return ((ch >= '0') && (ch <= '9')) || ((ch >= 'a') && (ch <= 'f')) || ((ch >= 'A') && (ch <= 'F'));
/*      */   }
/*      */ 
/*      */   private COSBase parseCOSDictionaryValue()
/*      */     throws IOException
/*      */   {
/*  247 */     COSBase retval = null;
/*  248 */     long numOffset = this.pdfSource.getOffset();
/*  249 */     COSBase number = parseDirObject();
/*  250 */     skipSpaces();
/*  251 */     char next = (char)this.pdfSource.peek();
/*  252 */     if ((next >= '0') && (next <= '9'))
/*      */     {
/*  254 */       long genOffset = this.pdfSource.getOffset();
/*  255 */       COSBase generationNumber = parseDirObject();
/*  256 */       skipSpaces();
/*  257 */       char r = (char)this.pdfSource.read();
/*  258 */       if (r != 'R')
/*      */       {
/*  260 */         throw new IOException("expected='R' actual='" + r + "' at offset " + this.pdfSource.getOffset());
/*      */       }
/*  262 */       if (!(number instanceof COSInteger))
/*      */       {
/*  264 */         throw new IOException("expected number, actual=" + number + " at offset " + numOffset);
/*      */       }
/*  266 */       if (!(generationNumber instanceof COSInteger))
/*      */       {
/*  268 */         throw new IOException("expected number, actual=" + number + " at offset " + genOffset);
/*      */       }
/*  270 */       COSObjectKey key = new COSObjectKey(((COSInteger)number).intValue(), ((COSInteger)generationNumber).intValue());
/*      */ 
/*  272 */       retval = this.document.getObjectFromPool(key);
/*      */     }
/*      */     else
/*      */     {
/*  276 */       retval = number;
/*      */     }
/*  278 */     return retval;
/*      */   }
/*      */ 
/*      */   protected COSDictionary parseCOSDictionary()
/*      */     throws IOException
/*      */   {
/*  290 */     char c = (char)this.pdfSource.read();
/*  291 */     if (c != '<')
/*      */     {
/*  293 */       throw new IOException("expected='<' actual='" + c + "' at offset " + this.pdfSource.getOffset());
/*      */     }
/*  295 */     c = (char)this.pdfSource.read();
/*  296 */     if (c != '<')
/*      */     {
/*  298 */       throw new IOException("expected='<' actual='" + c + "' at offset " + this.pdfSource.getOffset());
/*      */     }
/*  300 */     skipSpaces();
/*  301 */     COSDictionary obj = new COSDictionary();
/*  302 */     boolean done = false;
/*  303 */     while (!done)
/*      */     {
/*  305 */       skipSpaces();
/*  306 */       c = (char)this.pdfSource.peek();
/*  307 */       if (c == '>')
/*      */       {
/*  309 */         done = true;
/*      */       }
/*  312 */       else if (c != '/')
/*      */       {
/*  316 */         LOG.warn("Invalid dictionary, found: '" + c + "' but expected: '/'");
/*  317 */         int read = this.pdfSource.read();
/*  318 */         while ((read != -1) && (read != 47) && (read != 62))
/*      */         {
/*  322 */           if (read == 101)
/*      */           {
/*  324 */             read = this.pdfSource.read();
/*  325 */             if (read == 110)
/*      */             {
/*  327 */               read = this.pdfSource.read();
/*  328 */               if (read == 100)
/*      */               {
/*  330 */                 read = this.pdfSource.read();
/*  331 */                 boolean isStream = (read == 115) && (this.pdfSource.read() == 116) && (this.pdfSource.read() == 114) && (this.pdfSource.read() == 101) && (this.pdfSource.read() == 97) && (this.pdfSource.read() == 109);
/*      */ 
/*  334 */                 boolean isObj = (!isStream) && (read == 111) && (this.pdfSource.read() == 98) && (this.pdfSource.read() == 106);
/*  335 */                 if ((isStream) || (isObj))
/*      */                 {
/*  337 */                   return obj;
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*  342 */           read = this.pdfSource.read();
/*      */         }
/*  344 */         if (read != -1)
/*      */         {
/*  346 */           this.pdfSource.unread(read);
/*      */         }
/*      */         else
/*      */         {
/*  350 */           return obj;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  355 */         COSName key = parseCOSName();
/*  356 */         COSBase value = parseCOSDictionaryValue();
/*  357 */         skipSpaces();
/*  358 */         if ((char)this.pdfSource.peek() == 'd')
/*      */         {
/*  362 */           String potentialDEF = readString();
/*  363 */           if (!potentialDEF.equals("def"))
/*      */           {
/*  365 */             this.pdfSource.unread(potentialDEF.getBytes("ISO-8859-1"));
/*      */           }
/*      */           else
/*      */           {
/*  369 */             skipSpaces();
/*      */           }
/*      */         }
/*      */ 
/*  373 */         if (value == null)
/*      */         {
/*  375 */           LOG.warn("Bad Dictionary Declaration " + this.pdfSource);
/*      */         }
/*      */         else
/*      */         {
/*  379 */           value.setDirect(true);
/*  380 */           obj.setItem(key, value);
/*      */         }
/*      */       }
/*      */     }
/*  384 */     char ch = (char)this.pdfSource.read();
/*  385 */     if (ch != '>')
/*      */     {
/*  387 */       throw new IOException("expected='>' actual='" + ch + "' at offset " + this.pdfSource.getOffset());
/*      */     }
/*  389 */     ch = (char)this.pdfSource.read();
/*  390 */     if (ch != '>')
/*      */     {
/*  392 */       throw new IOException("expected='>' actual='" + ch + "' at offset " + this.pdfSource.getOffset());
/*      */     }
/*  394 */     return obj;
/*      */   }
/*      */ 
/*      */   protected COSStream parseCOSStream(COSDictionary dic, RandomAccess file)
/*      */     throws IOException
/*      */   {
/*  409 */     COSStream stream = new COSStream(dic, file);
/*  410 */     OutputStream out = null;
/*      */     try
/*      */     {
/*  413 */       String streamString = readString();
/*      */ 
/*  416 */       if (!streamString.equals("stream"))
/*      */       {
/*  418 */         throw new IOException("expected='stream' actual='" + streamString + "' at offset " + this.pdfSource.getOffset());
/*      */       }
/*      */ 
/*  424 */       int whitespace = this.pdfSource.read();
/*      */ 
/*  429 */       while (whitespace == 32)
/*      */       {
/*  431 */         whitespace = this.pdfSource.read();
/*      */       }
/*      */ 
/*  434 */       if (whitespace == 13)
/*      */       {
/*  436 */         whitespace = this.pdfSource.read();
/*  437 */         if (whitespace != 10)
/*      */         {
/*  439 */           this.pdfSource.unread(whitespace);
/*      */         }
/*      */ 
/*      */       }
/*  444 */       else if (whitespace != 10)
/*      */       {
/*  453 */         this.pdfSource.unread(whitespace);
/*      */       }
/*      */ 
/*  459 */       COSBase streamLength = dic.getItem(COSName.LENGTH);
/*      */ 
/*  462 */       out = stream.createFilteredStream(streamLength);
/*      */ 
/*  465 */       int length = -1;
/*  466 */       if ((streamLength instanceof COSNumber))
/*      */       {
/*  468 */         length = ((COSNumber)streamLength).intValue();
/*      */       }
/*      */ 
/*  484 */       if (length == -1)
/*      */       {
/*  488 */         readUntilEndStream(new EndstreamOutputStream(out));
/*      */       }
/*      */       else
/*      */       {
/*  493 */         int left = length;
/*  494 */         while (left > 0)
/*      */         {
/*  496 */           int chunk = Math.min(left, 2048);
/*  497 */           int readCount = this.pdfSource.read(this.strmBuf, 0, chunk);
/*  498 */           if (readCount == -1)
/*      */           {
/*      */             break;
/*      */           }
/*  502 */           out.write(this.strmBuf, 0, readCount);
/*  503 */           left -= readCount;
/*      */         }
/*      */ 
/*  510 */         int readCount = this.pdfSource.read(this.strmBuf, 0, 20);
/*  511 */         if (readCount > 0)
/*      */         {
/*  513 */           boolean foundEndstream = false;
/*  514 */           int nextEndstreamCIdx = 0;
/*  515 */           for (int cIdx = 0; cIdx < readCount; cIdx++)
/*      */           {
/*  517 */             int ch = this.strmBuf[cIdx] & 0xFF;
/*  518 */             if (ch == ENDSTREAM[nextEndstreamCIdx])
/*      */             {
/*  520 */               nextEndstreamCIdx++; if (nextEndstreamCIdx >= ENDSTREAM.length)
/*      */               {
/*  522 */                 foundEndstream = true;
/*  523 */                 break;
/*      */               }
/*      */             } else {
/*  526 */               if ((nextEndstreamCIdx > 0) || (!isWhitespace(ch)))
/*      */               {
/*      */                 break;
/*      */               }
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  534 */           this.pdfSource.unread(this.strmBuf, 0, readCount);
/*      */ 
/*  537 */           if (!foundEndstream)
/*      */           {
/*  539 */             LOG.warn("Specified stream length " + length + " is wrong. Fall back to reading stream until 'endstream'.");
/*      */ 
/*  544 */             out.flush();
/*  545 */             InputStream writtenStreamBytes = stream.getFilteredStream();
/*  546 */             ByteArrayOutputStream bout = new ByteArrayOutputStream(length);
/*      */ 
/*  548 */             IOUtils.copy(writtenStreamBytes, bout);
/*  549 */             IOUtils.closeQuietly(writtenStreamBytes);
/*      */             try
/*      */             {
/*  552 */               this.pdfSource.unread(bout.toByteArray());
/*      */             }
/*      */             catch (IOException ioe)
/*      */             {
/*  556 */               throw new WrappedIOException("Could not push back " + bout.size() + " bytes in order to reparse stream. " + "Try increasing push back buffer using system property " + "org.apache.pdfbox.baseParser.pushBackSize", ioe);
/*      */             }
/*      */ 
/*  562 */             IOUtils.closeQuietly(out);
/*  563 */             out = stream.createFilteredStream(streamLength);
/*      */ 
/*  565 */             readUntilEndStream(new EndstreamOutputStream(out));
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  570 */       skipSpaces();
/*  571 */       String endStream = readString();
/*      */ 
/*  573 */       if (!endStream.equals("endstream"))
/*      */       {
/*  580 */         if (endStream.startsWith("endobj"))
/*      */         {
/*  582 */           byte[] endobjarray = endStream.getBytes("ISO-8859-1");
/*  583 */           this.pdfSource.unread(endobjarray);
/*      */         }
/*  591 */         else if (endStream.startsWith("endstream"))
/*      */         {
/*  593 */           String extra = endStream.substring(9, endStream.length());
/*  594 */           byte[] array = extra.getBytes("ISO-8859-1");
/*  595 */           this.pdfSource.unread(array);
/*      */         }
/*      */         else
/*      */         {
/*  603 */           readUntilEndStream(new EndstreamOutputStream(out));
/*  604 */           endStream = readString();
/*  605 */           if (!endStream.equals("endstream"))
/*      */           {
/*  607 */             throw new IOException("expected='endstream' actual='" + endStream + "' at offset " + this.pdfSource.getOffset());
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*  614 */       if (out != null)
/*      */       {
/*  616 */         out.close();
/*      */       }
/*      */     }
/*  619 */     return stream;
/*      */   }
/*      */ 
/*      */   protected void readUntilEndStream(OutputStream out)
/*      */     throws IOException
/*      */   {
/*  640 */     int charMatchCount = 0;
/*  641 */     byte[] keyw = ENDSTREAM;
/*      */ 
/*  643 */     int quickTestOffset = 5;
/*      */     int bufSize;
/*  646 */     while ((bufSize = this.pdfSource.read(this.strmBuf, charMatchCount, 2048 - charMatchCount)) > 0)
/*      */     {
/*  648 */       bufSize += charMatchCount;
/*      */ 
/*  650 */       int bIdx = charMatchCount;
/*      */ 
/*  654 */       for (int maxQuicktestIdx = bufSize - 5; bIdx < bufSize; bIdx++)
/*      */       {
/*      */         int quickTestIdx;
/*  661 */         if ((charMatchCount == 0) && ((quickTestIdx = bIdx + 5) < maxQuicktestIdx))
/*      */         {
/*  665 */           byte ch = this.strmBuf[quickTestIdx];
/*  666 */           if ((ch > 116) || (ch < 97))
/*      */           {
/*  670 */             bIdx = quickTestIdx;
/*  671 */             continue;
/*      */           }
/*      */         }
/*      */ 
/*  675 */         byte ch = this.strmBuf[bIdx];
/*      */ 
/*  677 */         if (ch == keyw[charMatchCount])
/*      */         {
/*  679 */           charMatchCount++; if (charMatchCount == keyw.length)
/*      */           {
/*  682 */             bIdx++;
/*  683 */             break;
/*      */           }
/*      */ 
/*      */         }
/*  688 */         else if ((charMatchCount == 3) && (ch == ENDOBJ[charMatchCount]))
/*      */         {
/*  691 */           keyw = ENDOBJ;
/*  692 */           charMatchCount++;
/*      */         }
/*      */         else
/*      */         {
/*  702 */           charMatchCount = (ch == 110) && (charMatchCount == 7) ? 2 : ch == 101 ? 1 : 0;
/*      */ 
/*  704 */           keyw = ENDSTREAM;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  709 */       int contentBytes = Math.max(0, bIdx - charMatchCount);
/*      */ 
/*  712 */       if (contentBytes > 0)
/*      */       {
/*  714 */         out.write(this.strmBuf, 0, contentBytes);
/*      */       }
/*  716 */       if (charMatchCount == keyw.length)
/*      */       {
/*  719 */         this.pdfSource.unread(this.strmBuf, contentBytes, bufSize - contentBytes);
/*  720 */         break;
/*      */       }
/*      */ 
/*  725 */       System.arraycopy(keyw, 0, this.strmBuf, 0, charMatchCount);
/*      */     }
/*      */ 
/*  730 */     out.flush();
/*      */   }
/*      */ 
/*      */   private int checkForMissingCloseParen(int bracesParameter)
/*      */     throws IOException
/*      */   {
/*  754 */     int braces = bracesParameter;
/*  755 */     byte[] nextThreeBytes = new byte[3];
/*  756 */     int amountRead = this.pdfSource.read(nextThreeBytes);
/*      */ 
/*  775 */     if (amountRead == 3)
/*      */     {
/*  777 */       if (((nextThreeBytes[0] == 13) && (nextThreeBytes[1] == 10) && (nextThreeBytes[2] == 47)) || ((nextThreeBytes[0] == 13) && (nextThreeBytes[1] == 47)))
/*      */       {
/*  784 */         braces = 0;
/*      */       }
/*      */     }
/*  787 */     if (amountRead > 0)
/*      */     {
/*  789 */       this.pdfSource.unread(nextThreeBytes, 0, amountRead);
/*      */     }
/*  791 */     return braces;
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   protected COSString parseCOSString(boolean isDictionary)
/*      */     throws IOException
/*      */   {
/*  806 */     return parseCOSString();
/*      */   }
/*      */ 
/*      */   protected COSString parseCOSString()
/*      */     throws IOException
/*      */   {
/*  818 */     char nextChar = (char)this.pdfSource.read();
/*  819 */     COSString retval = new COSString();
/*      */     char closeBrace;
/*  822 */     if (nextChar == '(')
/*      */     {
/*  824 */       char openBrace = '(';
/*  825 */       closeBrace = ')';
/*      */     } else {
/*  827 */       if (nextChar == '<')
/*      */       {
/*  829 */         return parseCOSHexString();
/*      */       }
/*      */ 
/*  833 */       throw new IOException("parseCOSString string should start with '(' or '<' and not '" + nextChar + "' " + this.pdfSource);
/*      */     }
/*      */     char closeBrace;
/*      */     char openBrace;
/*  839 */     int braces = 1;
/*  840 */     int c = this.pdfSource.read();
/*  841 */     while ((braces > 0) && (c != -1))
/*      */     {
/*  843 */       char ch = (char)c;
/*  844 */       int nextc = -2;
/*      */ 
/*  846 */       if (ch == closeBrace)
/*      */       {
/*  849 */         braces--;
/*  850 */         braces = checkForMissingCloseParen(braces);
/*  851 */         if (braces != 0)
/*      */         {
/*  853 */           retval.append(ch);
/*      */         }
/*      */       }
/*  856 */       else if (ch == openBrace)
/*      */       {
/*  858 */         braces++;
/*  859 */         retval.append(ch);
/*      */       }
/*  861 */       else if (ch == '\\')
/*      */       {
/*  864 */         char next = (char)this.pdfSource.read();
/*  865 */         switch (next)
/*      */         {
/*      */         case 'n':
/*  868 */           retval.append(10);
/*  869 */           break;
/*      */         case 'r':
/*  871 */           retval.append(13);
/*  872 */           break;
/*      */         case 't':
/*  874 */           retval.append(9);
/*  875 */           break;
/*      */         case 'b':
/*  877 */           retval.append(8);
/*  878 */           break;
/*      */         case 'f':
/*  880 */           retval.append(12);
/*  881 */           break;
/*      */         case ')':
/*  884 */           braces = checkForMissingCloseParen(braces);
/*  885 */           if (braces != 0)
/*      */           {
/*  887 */             retval.append(next);
/*      */           }
/*      */           else
/*      */           {
/*  891 */             retval.append(92);
/*      */           }
/*  893 */           break;
/*      */         case '(':
/*      */         case '\\':
/*  896 */           retval.append(next);
/*  897 */           break;
/*      */         case '\n':
/*      */         case '\r':
/*  901 */           c = this.pdfSource.read();
/*  902 */           while ((isEOL(c)) && (c != -1))
/*      */           {
/*  904 */             c = this.pdfSource.read();
/*      */           }
/*  906 */           nextc = c;
/*  907 */           break;
/*      */         case '0':
/*      */         case '1':
/*      */         case '2':
/*      */         case '3':
/*      */         case '4':
/*      */         case '5':
/*      */         case '6':
/*      */         case '7':
/*  917 */           StringBuffer octal = new StringBuffer();
/*  918 */           octal.append(next);
/*  919 */           c = this.pdfSource.read();
/*  920 */           char digit = (char)c;
/*  921 */           if ((digit >= '0') && (digit <= '7'))
/*      */           {
/*  923 */             octal.append(digit);
/*  924 */             c = this.pdfSource.read();
/*  925 */             digit = (char)c;
/*  926 */             if ((digit >= '0') && (digit <= '7'))
/*      */             {
/*  928 */               octal.append(digit);
/*      */             }
/*      */             else
/*      */             {
/*  932 */               nextc = c;
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/*  937 */             nextc = c;
/*      */           }
/*      */ 
/*  940 */           int character = 0;
/*      */           try
/*      */           {
/*  943 */             character = Integer.parseInt(octal.toString(), 8);
/*      */           }
/*      */           catch (NumberFormatException e)
/*      */           {
/*  947 */             throw new IOException("Error: Expected octal character, actual='" + octal + "'");
/*      */           }
/*  949 */           retval.append(character);
/*  950 */           break;
/*      */         default:
/*  956 */           retval.append(next);
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  962 */         retval.append(ch);
/*      */       }
/*  964 */       if (nextc != -2)
/*      */       {
/*  966 */         c = nextc;
/*      */       }
/*      */       else
/*      */       {
/*  970 */         c = this.pdfSource.read();
/*      */       }
/*      */     }
/*  973 */     if (c != -1)
/*      */     {
/*  975 */       this.pdfSource.unread(c);
/*      */     }
/*  977 */     return retval;
/*      */   }
/*      */ 
/*      */   private final COSString parseCOSHexString()
/*      */     throws IOException
/*      */   {
/*  994 */     StringBuilder sBuf = new StringBuilder();
/*      */     while (true)
/*      */     {
/*  997 */       int c = this.pdfSource.read();
/*  998 */       if (isHexDigit((char)c))
/*      */       {
/* 1000 */         sBuf.append((char)c);
/*      */       } else {
/* 1002 */         if (c == 62)
/*      */         {
/*      */           break;
/*      */         }
/* 1006 */         if (c < 0)
/*      */         {
/* 1008 */           throw new IOException("Missing closing bracket for hex string. Reached EOS.");
/*      */         }
/* 1010 */         if ((c != 32) && (c != 10) && (c != 9) && (c != 13) && (c != 8) && (c != 12))
/*      */         {
/* 1020 */           if (sBuf.length() % 2 != 0)
/*      */           {
/* 1022 */             sBuf.deleteCharAt(sBuf.length() - 1);
/*      */           }
/*      */ 
/*      */           do
/*      */           {
/* 1028 */             c = this.pdfSource.read();
/* 1029 */           }while ((c != 62) && (c >= 0));
/*      */ 
/* 1034 */           if (c >= 0)
/*      */             break;
/* 1036 */           throw new IOException("Missing closing bracket for hex string. Reached EOS.");
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1043 */     return COSString.createFromHexString(sBuf.toString(), this.forceParsing);
/*      */   }
/*      */ 
/*      */   protected COSArray parseCOSArray()
/*      */     throws IOException
/*      */   {
/* 1055 */     char ch = (char)this.pdfSource.read();
/* 1056 */     if (ch != '[')
/*      */     {
/* 1058 */       throw new IOException("expected='[' actual='" + ch + "' at offset " + this.pdfSource.getOffset());
/*      */     }
/* 1060 */     COSArray po = new COSArray();
/*      */ 
/* 1062 */     skipSpaces();
/*      */     int i;
/* 1064 */     while (((i = this.pdfSource.peek()) > 0) && ((char)i != ']'))
/*      */     {
/* 1066 */       COSBase pbo = parseDirObject();
/* 1067 */       if ((pbo instanceof COSObject))
/*      */       {
/* 1070 */         if ((po.get(po.size() - 1) instanceof COSInteger))
/*      */         {
/* 1072 */           COSInteger genNumber = (COSInteger)po.remove(po.size() - 1);
/* 1073 */           if ((po.get(po.size() - 1) instanceof COSInteger))
/*      */           {
/* 1075 */             COSInteger number = (COSInteger)po.remove(po.size() - 1);
/* 1076 */             COSObjectKey key = new COSObjectKey(number.intValue(), genNumber.intValue());
/* 1077 */             pbo = this.document.getObjectFromPool(key);
/*      */           }
/*      */           else
/*      */           {
/* 1082 */             pbo = null;
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 1087 */           pbo = null;
/*      */         }
/*      */       }
/* 1090 */       if (pbo != null)
/*      */       {
/* 1092 */         po.add(pbo);
/*      */       }
/*      */       else
/*      */       {
/* 1097 */         LOG.warn("Corrupt object reference");
/*      */ 
/* 1101 */         String isThisTheEnd = readString();
/* 1102 */         this.pdfSource.unread(isThisTheEnd.getBytes("ISO-8859-1"));
/* 1103 */         if (("endobj".equals(isThisTheEnd)) || ("endstream".equals(isThisTheEnd)))
/*      */         {
/* 1105 */           return po;
/*      */         }
/*      */       }
/* 1108 */       skipSpaces();
/*      */     }
/* 1110 */     this.pdfSource.read();
/* 1111 */     skipSpaces();
/* 1112 */     return po;
/*      */   }
/*      */ 
/*      */   protected boolean isEndOfName(char ch)
/*      */   {
/* 1123 */     return (ch == ' ') || (ch == '\r') || (ch == '\n') || (ch == '\t') || (ch == '>') || (ch == '<') || (ch == '[') || (ch == '/') || (ch == ']') || (ch == ')') || (ch == '(') || (ch == 'ð¿¿');
/*      */   }
/*      */ 
/*      */   protected COSName parseCOSName()
/*      */     throws IOException
/*      */   {
/* 1138 */     int c = this.pdfSource.read();
/* 1139 */     if ((char)c != '/')
/*      */     {
/* 1141 */       throw new IOException("expected='/' actual='" + (char)c + "'-" + c + " at offset " + this.pdfSource.getOffset());
/*      */     }
/*      */ 
/* 1144 */     StringBuilder buffer = new StringBuilder();
/* 1145 */     c = this.pdfSource.read();
/* 1146 */     while (c != -1)
/*      */     {
/* 1148 */       char ch = (char)c;
/* 1149 */       if (ch == '#')
/*      */       {
/* 1151 */         char ch1 = (char)this.pdfSource.read();
/* 1152 */         char ch2 = (char)this.pdfSource.read();
/*      */ 
/* 1161 */         if ((isHexDigit(ch1)) && (isHexDigit(ch2)))
/*      */         {
/* 1163 */           String hex = "" + ch1 + ch2;
/*      */           try
/*      */           {
/* 1166 */             buffer.append((char)Integer.parseInt(hex, 16));
/*      */           }
/*      */           catch (NumberFormatException e)
/*      */           {
/* 1170 */             throw new IOException("Error: expected hex number, actual='" + hex + "'");
/*      */           }
/* 1172 */           c = this.pdfSource.read();
/*      */         }
/*      */         else
/*      */         {
/* 1176 */           this.pdfSource.unread(ch2);
/* 1177 */           c = ch1;
/* 1178 */           buffer.append(ch);
/*      */         }
/*      */       } else {
/* 1181 */         if (isEndOfName(ch))
/*      */         {
/*      */           break;
/*      */         }
/*      */ 
/* 1187 */         buffer.append(ch);
/* 1188 */         c = this.pdfSource.read();
/*      */       }
/*      */     }
/* 1191 */     if (c != -1)
/*      */     {
/* 1193 */       this.pdfSource.unread(c);
/*      */     }
/* 1195 */     return COSName.getPDFName(buffer.toString());
/*      */   }
/*      */ 
/*      */   protected COSBoolean parseBoolean()
/*      */     throws IOException
/*      */   {
/* 1207 */     COSBoolean retval = null;
/* 1208 */     char c = (char)this.pdfSource.peek();
/* 1209 */     if (c == 't')
/*      */     {
/* 1211 */       String trueString = new String(this.pdfSource.readFully(4), "ISO-8859-1");
/* 1212 */       if (!trueString.equals("true"))
/*      */       {
/* 1214 */         throw new IOException("Error parsing boolean: expected='true' actual='" + trueString + "' at offset " + this.pdfSource.getOffset());
/*      */       }
/*      */ 
/* 1218 */       retval = COSBoolean.TRUE;
/*      */     }
/* 1221 */     else if (c == 'f')
/*      */     {
/* 1223 */       String falseString = new String(this.pdfSource.readFully(5), "ISO-8859-1");
/* 1224 */       if (!falseString.equals("false"))
/*      */       {
/* 1226 */         throw new IOException("Error parsing boolean: expected='true' actual='" + falseString + "' at offset " + this.pdfSource.getOffset());
/*      */       }
/*      */ 
/* 1230 */       retval = COSBoolean.FALSE;
/*      */     }
/*      */     else
/*      */     {
/* 1235 */       throw new IOException("Error parsing boolean expected='t or f' actual='" + c + "' at offset " + this.pdfSource.getOffset());
/*      */     }
/* 1237 */     return retval;
/*      */   }
/*      */ 
/*      */   protected COSBase parseDirObject()
/*      */     throws IOException
/*      */   {
/* 1249 */     COSBase retval = null;
/*      */ 
/* 1251 */     skipSpaces();
/* 1252 */     int nextByte = this.pdfSource.peek();
/* 1253 */     char c = (char)nextByte;
/* 1254 */     switch (c)
/*      */     {
/*      */     case '<':
/* 1258 */       int leftBracket = this.pdfSource.read();
/* 1259 */       c = (char)this.pdfSource.peek();
/* 1260 */       this.pdfSource.unread(leftBracket);
/* 1261 */       if (c == '<')
/*      */       {
/* 1264 */         retval = parseCOSDictionary();
/* 1265 */         skipSpaces();
/*      */       }
/*      */       else
/*      */       {
/* 1269 */         retval = parseCOSString();
/*      */       }
/* 1271 */       break;
/*      */     case '[':
/* 1275 */       retval = parseCOSArray();
/* 1276 */       break;
/*      */     case '(':
/* 1279 */       retval = parseCOSString();
/* 1280 */       break;
/*      */     case '/':
/* 1282 */       retval = parseCOSName();
/* 1283 */       break;
/*      */     case 'n':
/* 1286 */       String nullString = readString();
/* 1287 */       if (!nullString.equals("null"))
/*      */       {
/* 1289 */         throw new IOException("Expected='null' actual='" + nullString + "' at offset " + this.pdfSource.getOffset());
/*      */       }
/* 1291 */       retval = COSNull.NULL;
/* 1292 */       break;
/*      */     case 't':
/* 1296 */       String trueString = new String(this.pdfSource.readFully(4), "ISO-8859-1");
/* 1297 */       if (trueString.equals("true"))
/*      */       {
/* 1299 */         retval = COSBoolean.TRUE;
/*      */       }
/*      */       else
/*      */       {
/* 1303 */         throw new IOException("expected true actual='" + trueString + "' " + this.pdfSource);
/*      */       }
/*      */ 
/*      */       break;
/*      */     case 'f':
/* 1309 */       String falseString = new String(this.pdfSource.readFully(5), "ISO-8859-1");
/* 1310 */       if (falseString.equals("false"))
/*      */       {
/* 1312 */         retval = COSBoolean.FALSE;
/*      */       }
/*      */       else
/*      */       {
/* 1316 */         throw new IOException("expected false actual='" + falseString + "' " + this.pdfSource);
/*      */       }
/*      */ 
/*      */       break;
/*      */     case 'R':
/* 1321 */       this.pdfSource.read();
/* 1322 */       retval = new COSObject(null);
/* 1323 */       break;
/*      */     case 'ð¿¿':
/* 1325 */       return null;
/*      */     default:
/* 1328 */       if ((Character.isDigit(c)) || (c == '-') || (c == '+') || (c == '.'))
/*      */       {
/* 1330 */         StringBuilder buf = new StringBuilder();
/* 1331 */         int ic = this.pdfSource.read();
/* 1332 */         c = (char)ic;
/*      */ 
/* 1338 */         while ((Character.isDigit(c)) || (c == '-') || (c == '+') || (c == '.') || (c == 'E') || (c == 'e'))
/*      */         {
/* 1340 */           buf.append(c);
/* 1341 */           ic = this.pdfSource.read();
/* 1342 */           c = (char)ic;
/*      */         }
/* 1344 */         if (ic != -1)
/*      */         {
/* 1346 */           this.pdfSource.unread(ic);
/*      */         }
/* 1348 */         retval = COSNumber.get(buf.toString());
/*      */       }
/*      */       else
/*      */       {
/* 1355 */         String badString = readString();
/*      */ 
/* 1358 */         if ((badString == null) || (badString.length() == 0))
/*      */         {
/* 1360 */           int peek = this.pdfSource.peek();
/*      */ 
/* 1362 */           throw new IOException("Unknown dir object c='" + c + "' cInt=" + c + " peek='" + (char)peek + "' peekInt=" + peek + " " + this.pdfSource.getOffset());
/*      */         }
/*      */ 
/* 1368 */         if (("endobj".equals(badString)) || ("endstream".equals(badString)))
/*      */         {
/* 1370 */           this.pdfSource.unread(badString.getBytes("ISO-8859-1"));
/*      */         }
/*      */       }
/*      */       break;
/*      */     }
/* 1375 */     return retval;
/*      */   }
/*      */ 
/*      */   protected String readString()
/*      */     throws IOException
/*      */   {
/* 1387 */     skipSpaces();
/* 1388 */     StringBuilder buffer = new StringBuilder();
/* 1389 */     int c = this.pdfSource.read();
/* 1390 */     while ((!isEndOfName((char)c)) && (!isClosing(c)) && (c != -1))
/*      */     {
/* 1392 */       buffer.append((char)c);
/* 1393 */       c = this.pdfSource.read();
/*      */     }
/* 1395 */     if (c != -1)
/*      */     {
/* 1397 */       this.pdfSource.unread(c);
/*      */     }
/* 1399 */     return buffer.toString();
/*      */   }
/*      */ 
/*      */   protected String readExpectedString(String theString)
/*      */     throws IOException
/*      */   {
/* 1413 */     int c = this.pdfSource.read();
/* 1414 */     while ((isWhitespace(c)) && (c != -1))
/*      */     {
/* 1416 */       c = this.pdfSource.read();
/*      */     }
/* 1418 */     StringBuilder buffer = new StringBuilder(theString.length());
/* 1419 */     int charsRead = 0;
/* 1420 */     while ((!isEOL(c)) && (c != -1) && (charsRead < theString.length()))
/*      */     {
/* 1422 */       char next = (char)c;
/* 1423 */       buffer.append(next);
/* 1424 */       if (theString.charAt(charsRead) == next)
/*      */       {
/* 1426 */         charsRead++;
/*      */       }
/*      */       else
/*      */       {
/* 1430 */         this.pdfSource.unread(buffer.toString().getBytes("ISO-8859-1"));
/* 1431 */         throw new IOException("Error: Expected to read '" + theString + "' instead started reading '" + buffer.toString() + "'");
/*      */       }
/*      */ 
/* 1434 */       c = this.pdfSource.read();
/*      */     }
/* 1436 */     while ((isEOL(c)) && (c != -1))
/*      */     {
/* 1438 */       c = this.pdfSource.read();
/*      */     }
/* 1440 */     if (c != -1)
/*      */     {
/* 1442 */       this.pdfSource.unread(c);
/*      */     }
/* 1444 */     return buffer.toString();
/*      */   }
/*      */ 
/*      */   protected String readString(int length)
/*      */     throws IOException
/*      */   {
/* 1458 */     skipSpaces();
/*      */ 
/* 1460 */     int c = this.pdfSource.read();
/*      */ 
/* 1464 */     StringBuilder buffer = new StringBuilder(length);
/*      */ 
/* 1469 */     while ((!isWhitespace(c)) && (!isClosing(c)) && (c != -1) && (buffer.length() < length) && (c != 91) && (c != 60) && (c != 40) && (c != 47))
/*      */     {
/* 1471 */       buffer.append((char)c);
/* 1472 */       c = this.pdfSource.read();
/*      */     }
/* 1474 */     if (c != -1)
/*      */     {
/* 1476 */       this.pdfSource.unread(c);
/*      */     }
/* 1478 */     return buffer.toString();
/*      */   }
/*      */ 
/*      */   protected boolean isClosing()
/*      */     throws IOException
/*      */   {
/* 1490 */     return isClosing(this.pdfSource.peek());
/*      */   }
/*      */ 
/*      */   protected boolean isClosing(int c)
/*      */   {
/* 1501 */     return c == 93;
/*      */   }
/*      */ 
/*      */   protected String readLine()
/*      */     throws IOException
/*      */   {
/* 1515 */     if (this.pdfSource.isEOF())
/*      */     {
/* 1517 */       throw new IOException("Error: End-of-File, expected line");
/*      */     }
/*      */ 
/* 1520 */     StringBuilder buffer = new StringBuilder(11);
/*      */     int c;
/* 1523 */     while ((c = this.pdfSource.read()) != -1)
/*      */     {
/* 1525 */       if (isEOL(c))
/*      */       {
/*      */         break;
/*      */       }
/* 1529 */       buffer.append((char)c);
/*      */     }
/* 1531 */     return buffer.toString();
/*      */   }
/*      */ 
/*      */   protected boolean isEOL()
/*      */     throws IOException
/*      */   {
/* 1543 */     return isEOL(this.pdfSource.peek());
/*      */   }
/*      */ 
/*      */   protected boolean isEOL(int c)
/*      */   {
/* 1554 */     return (c == 10) || (c == 13);
/*      */   }
/*      */ 
/*      */   protected boolean isWhitespace()
/*      */     throws IOException
/*      */   {
/* 1566 */     return isWhitespace(this.pdfSource.peek());
/*      */   }
/*      */ 
/*      */   protected boolean isWhitespace(int c)
/*      */   {
/* 1577 */     return (c == 0) || (c == 9) || (c == 12) || (c == 10) || (c == 13) || (c == 32);
/*      */   }
/*      */ 
/*      */   protected void skipSpaces()
/*      */     throws IOException
/*      */   {
/* 1589 */     int c = this.pdfSource.read();
/*      */ 
/* 1592 */     while ((c == 0) || (c == 9) || (c == 12) || (c == 10) || (c == 13) || (c == 32) || (c == 37))
/*      */     {
/* 1594 */       if (c == 37)
/*      */       {
/* 1597 */         c = this.pdfSource.read();
/* 1598 */         while ((!isEOL(c)) && (c != -1))
/*      */         {
/* 1600 */           c = this.pdfSource.read();
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1605 */         c = this.pdfSource.read();
/*      */       }
/*      */     }
/* 1608 */     if (c != -1)
/*      */     {
/* 1610 */       this.pdfSource.unread(c);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected long readObjectNumber()
/*      */     throws IOException
/*      */   {
/* 1623 */     long retval = readLong();
/* 1624 */     if ((retval < 0L) || (retval >= 10000000000L))
/*      */     {
/* 1626 */       throw new IOException("Object Number '" + retval + "' has more than 10 digits or is negative");
/*      */     }
/* 1628 */     return retval;
/*      */   }
/*      */ 
/*      */   protected int readGenerationNumber()
/*      */     throws IOException
/*      */   {
/* 1639 */     int retval = readInt();
/* 1640 */     if ((retval < 0) || (retval > 65535L))
/*      */     {
/* 1642 */       throw new IOException("Generation Number '" + retval + "' has more than 5 digits");
/*      */     }
/* 1644 */     return retval;
/*      */   }
/*      */ 
/*      */   protected int readInt()
/*      */     throws IOException
/*      */   {
/* 1656 */     skipSpaces();
/* 1657 */     int retval = 0;
/*      */ 
/* 1659 */     StringBuilder intBuffer = readStringNumber();
/*      */     try
/*      */     {
/* 1663 */       retval = Integer.parseInt(intBuffer.toString());
/*      */     }
/*      */     catch (NumberFormatException e)
/*      */     {
/* 1667 */       this.pdfSource.unread(intBuffer.toString().getBytes("ISO-8859-1"));
/* 1668 */       throw new IOException("Error: Expected an integer type at offset " + this.pdfSource.getOffset());
/*      */     }
/* 1670 */     return retval;
/*      */   }
/*      */ 
/*      */   protected long readLong()
/*      */     throws IOException
/*      */   {
/* 1683 */     skipSpaces();
/* 1684 */     long retval = 0L;
/*      */ 
/* 1686 */     StringBuilder longBuffer = readStringNumber();
/*      */     try
/*      */     {
/* 1690 */       retval = Long.parseLong(longBuffer.toString());
/*      */     }
/*      */     catch (NumberFormatException e)
/*      */     {
/* 1694 */       this.pdfSource.unread(longBuffer.toString().getBytes("ISO-8859-1"));
/* 1695 */       throw new IOException("Error: Expected a long type at offset " + this.pdfSource.getOffset() + ", instead got '" + longBuffer + "'");
/*      */     }
/*      */ 
/* 1698 */     return retval;
/*      */   }
/*      */ 
/*      */   protected final StringBuilder readStringNumber()
/*      */     throws IOException
/*      */   {
/* 1710 */     int lastByte = 0;
/* 1711 */     StringBuilder buffer = new StringBuilder();
/*      */ 
/* 1718 */     while (((lastByte = this.pdfSource.read()) != 32) && (lastByte != 10) && (lastByte != 13) && (lastByte != 60) && (lastByte != 91) && (lastByte != 0) && (lastByte != -1))
/*      */     {
/* 1720 */       buffer.append((char)lastByte);
/*      */     }
/* 1722 */     if (lastByte != -1)
/*      */     {
/* 1724 */       this.pdfSource.unread(lastByte);
/*      */     }
/* 1726 */     return buffer;
/*      */   }
/*      */ 
/*      */   public void clearResources()
/*      */   {
/* 1734 */     this.document = null;
/* 1735 */     if (this.pdfSource != null)
/*      */     {
/* 1737 */       IOUtils.closeQuietly(this.pdfSource);
/* 1738 */       this.pdfSource = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/*      */     try
/*      */     {
/*  138 */       FORCE_PARSING = Boolean.getBoolean("org.apache.pdfbox.forceParsing");
/*      */     }
/*      */     catch (SecurityException e)
/*      */     {
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdfparser.BaseParser
 * JD-Core Version:    0.6.2
 */