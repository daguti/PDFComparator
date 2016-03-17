/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.exceptions.InvalidPdfException;
/*     */ import com.itextpdf.text.io.RandomAccessSourceFactory;
/*     */ import java.io.IOException;
/*     */ 
/*     */ public class PRTokeniser
/*     */ {
/*  76 */   public static final boolean[] delims = { true, true, false, false, false, false, false, false, false, false, true, true, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, true, false, false, true, true, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false };
/*     */   static final String EMPTY = "";
/*     */   private final RandomAccessFileOrArray file;
/*     */   protected TokenType type;
/*     */   protected String stringValue;
/*     */   protected int reference;
/*     */   protected int generation;
/*     */   protected boolean hexString;
/*     */ 
/*     */   public PRTokeniser(RandomAccessFileOrArray file)
/*     */   {
/* 122 */     this.file = file;
/*     */   }
/*     */ 
/*     */   public void seek(long pos) throws IOException {
/* 126 */     this.file.seek(pos);
/*     */   }
/*     */ 
/*     */   public long getFilePointer() throws IOException {
/* 130 */     return this.file.getFilePointer();
/*     */   }
/*     */ 
/*     */   public void close() throws IOException {
/* 134 */     this.file.close();
/*     */   }
/*     */ 
/*     */   public long length() throws IOException {
/* 138 */     return this.file.length();
/*     */   }
/*     */ 
/*     */   public int read() throws IOException {
/* 142 */     return this.file.read();
/*     */   }
/*     */ 
/*     */   public RandomAccessFileOrArray getSafeFile() {
/* 146 */     return new RandomAccessFileOrArray(this.file);
/*     */   }
/*     */ 
/*     */   public RandomAccessFileOrArray getFile()
/*     */   {
/* 151 */     return this.file;
/*     */   }
/*     */ 
/*     */   public String readString(int size) throws IOException {
/* 155 */     StringBuilder buf = new StringBuilder();
/*     */ 
/* 157 */     while (size-- > 0) {
/* 158 */       int ch = read();
/* 159 */       if (ch == -1)
/*     */         break;
/* 161 */       buf.append((char)ch);
/*     */     }
/* 163 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public static final boolean isWhitespace(int ch)
/*     */   {
/* 174 */     return isWhitespace(ch, true);
/*     */   }
/*     */ 
/*     */   public static final boolean isWhitespace(int ch, boolean isWhitespace)
/*     */   {
/* 185 */     return ((isWhitespace) && (ch == 0)) || (ch == 9) || (ch == 10) || (ch == 12) || (ch == 13) || (ch == 32);
/*     */   }
/*     */ 
/*     */   public static final boolean isDelimiter(int ch) {
/* 189 */     return (ch == 40) || (ch == 41) || (ch == 60) || (ch == 62) || (ch == 91) || (ch == 93) || (ch == 47) || (ch == 37);
/*     */   }
/*     */ 
/*     */   public static final boolean isDelimiterWhitespace(int ch) {
/* 193 */     return delims[(ch + 1)];
/*     */   }
/*     */ 
/*     */   public TokenType getTokenType() {
/* 197 */     return this.type;
/*     */   }
/*     */ 
/*     */   public String getStringValue() {
/* 201 */     return this.stringValue;
/*     */   }
/*     */ 
/*     */   public int getReference() {
/* 205 */     return this.reference;
/*     */   }
/*     */ 
/*     */   public int getGeneration() {
/* 209 */     return this.generation;
/*     */   }
/*     */ 
/*     */   public void backOnePosition(int ch) {
/* 213 */     if (ch != -1)
/* 214 */       this.file.pushBack((byte)ch);
/*     */   }
/*     */ 
/*     */   public void throwError(String error) throws IOException {
/* 218 */     throw new InvalidPdfException(MessageLocalization.getComposedMessage("1.at.file.pointer.2", new Object[] { error, String.valueOf(this.file.getFilePointer()) }));
/*     */   }
/*     */ 
/*     */   public int getHeaderOffset() throws IOException {
/* 222 */     String str = readString(1024);
/* 223 */     int idx = str.indexOf("%PDF-");
/* 224 */     if (idx < 0) {
/* 225 */       idx = str.indexOf("%FDF-");
/* 226 */       if (idx < 0) {
/* 227 */         throw new InvalidPdfException(MessageLocalization.getComposedMessage("pdf.header.not.found", new Object[0]));
/*     */       }
/*     */     }
/* 230 */     return idx;
/*     */   }
/*     */ 
/*     */   public char checkPdfHeader() throws IOException {
/* 234 */     this.file.seek(0L);
/* 235 */     String str = readString(1024);
/* 236 */     int idx = str.indexOf("%PDF-");
/* 237 */     if (idx != 0)
/* 238 */       throw new InvalidPdfException(MessageLocalization.getComposedMessage("pdf.header.not.found", new Object[0]));
/* 239 */     return str.charAt(7);
/*     */   }
/*     */ 
/*     */   public void checkFdfHeader() throws IOException {
/* 243 */     this.file.seek(0L);
/* 244 */     String str = readString(1024);
/* 245 */     int idx = str.indexOf("%FDF-");
/* 246 */     if (idx != 0)
/* 247 */       throw new InvalidPdfException(MessageLocalization.getComposedMessage("fdf.header.not.found", new Object[0]));
/*     */   }
/*     */ 
/*     */   public long getStartxref() throws IOException {
/* 251 */     int arrLength = 1024;
/* 252 */     long fileLength = this.file.length();
/* 253 */     long pos = fileLength - arrLength;
/* 254 */     if (pos < 1L) pos = 1L;
/* 255 */     while (pos > 0L) {
/* 256 */       this.file.seek(pos);
/* 257 */       String str = readString(arrLength);
/* 258 */       int idx = str.lastIndexOf("startxref");
/* 259 */       if (idx >= 0) return pos + idx;
/* 260 */       pos = pos - arrLength + 9L;
/*     */     }
/* 262 */     throw new InvalidPdfException(MessageLocalization.getComposedMessage("pdf.startxref.not.found", new Object[0]));
/*     */   }
/*     */ 
/*     */   public static int getHex(int v) {
/* 266 */     if ((v >= 48) && (v <= 57))
/* 267 */       return v - 48;
/* 268 */     if ((v >= 65) && (v <= 70))
/* 269 */       return v - 65 + 10;
/* 270 */     if ((v >= 97) && (v <= 102))
/* 271 */       return v - 97 + 10;
/* 272 */     return -1;
/*     */   }
/*     */ 
/*     */   public void nextValidToken() throws IOException {
/* 276 */     int level = 0;
/* 277 */     String n1 = null;
/* 278 */     String n2 = null;
/* 279 */     long ptr = 0L;
/* 280 */     while (nextToken()) {
/* 281 */       if (this.type != TokenType.COMMENT)
/*     */       {
/* 283 */         switch (level)
/*     */         {
/*     */         case 0:
/* 286 */           if (this.type != TokenType.NUMBER)
/* 287 */             return;
/* 288 */           ptr = this.file.getFilePointer();
/* 289 */           n1 = this.stringValue;
/* 290 */           level++;
/* 291 */           break;
/*     */         case 1:
/* 295 */           if (this.type != TokenType.NUMBER) {
/* 296 */             this.file.seek(ptr);
/* 297 */             this.type = TokenType.NUMBER;
/* 298 */             this.stringValue = n1;
/* 299 */             return;
/*     */           }
/* 301 */           n2 = this.stringValue;
/* 302 */           level++;
/* 303 */           break;
/*     */         default:
/* 307 */           if ((this.type != TokenType.OTHER) || (!this.stringValue.equals("R"))) {
/* 308 */             this.file.seek(ptr);
/* 309 */             this.type = TokenType.NUMBER;
/* 310 */             this.stringValue = n1;
/* 311 */             return;
/*     */           }
/* 313 */           this.type = TokenType.REF;
/* 314 */           this.reference = Integer.parseInt(n1);
/* 315 */           this.generation = Integer.parseInt(n2);
/* 316 */           return;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 321 */     if (level == 1)
/* 322 */       this.type = TokenType.NUMBER;
/*     */   }
/*     */ 
/*     */   public boolean nextToken()
/*     */     throws IOException
/*     */   {
/* 330 */     int ch = 0;
/*     */     do
/* 332 */       ch = this.file.read();
/* 333 */     while ((ch != -1) && (isWhitespace(ch)));
/* 334 */     if (ch == -1) {
/* 335 */       this.type = TokenType.ENDOFFILE;
/* 336 */       return false;
/*     */     }
/*     */ 
/* 342 */     StringBuilder outBuf = new StringBuilder();
/* 343 */     this.stringValue = "";
/*     */ 
/* 345 */     switch (ch) {
/*     */     case 91:
/* 347 */       this.type = TokenType.START_ARRAY;
/* 348 */       break;
/*     */     case 93:
/* 350 */       this.type = TokenType.END_ARRAY;
/* 351 */       break;
/*     */     case 47:
/* 354 */       outBuf.setLength(0);
/* 355 */       this.type = TokenType.NAME;
/*     */       while (true) {
/* 357 */         ch = this.file.read();
/* 358 */         if (delims[(ch + 1)] != 0)
/*     */           break;
/* 360 */         if (ch == 35) {
/* 361 */           ch = (getHex(this.file.read()) << 4) + getHex(this.file.read());
/*     */         }
/* 363 */         outBuf.append((char)ch);
/*     */       }
/* 365 */       backOnePosition(ch);
/* 366 */       break;
/*     */     case 62:
/* 369 */       ch = this.file.read();
/* 370 */       if (ch != 62)
/* 371 */         throwError(MessageLocalization.getComposedMessage("greaterthan.not.expected", new Object[0]));
/* 372 */       this.type = TokenType.END_DIC;
/* 373 */       break;
/*     */     case 60:
/* 376 */       int v1 = this.file.read();
/* 377 */       if (v1 == 60) {
/* 378 */         this.type = TokenType.START_DIC;
/*     */       }
/*     */       else {
/* 381 */         outBuf.setLength(0);
/* 382 */         this.type = TokenType.STRING;
/* 383 */         this.hexString = true;
/* 384 */         int v2 = 0;
/*     */         while (true)
/* 386 */           if (isWhitespace(v1)) {
/* 387 */             v1 = this.file.read(); } else {
/* 388 */             if (v1 == 62)
/*     */               break;
/* 390 */             v1 = getHex(v1);
/* 391 */             if (v1 < 0)
/*     */               break;
/* 393 */             v2 = this.file.read();
/* 394 */             while (isWhitespace(v2))
/* 395 */               v2 = this.file.read();
/* 396 */             if (v2 == 62) {
/* 397 */               ch = v1 << 4;
/* 398 */               outBuf.append((char)ch);
/* 399 */               break;
/*     */             }
/* 401 */             v2 = getHex(v2);
/* 402 */             if (v2 < 0)
/*     */               break;
/* 404 */             ch = (v1 << 4) + v2;
/* 405 */             outBuf.append((char)ch);
/* 406 */             v1 = this.file.read();
/*     */           }
/* 408 */         if ((v1 < 0) || (v2 < 0))
/* 409 */           throwError(MessageLocalization.getComposedMessage("error.reading.string", new Object[0]));  } break;
/*     */     case 37:
/* 413 */       this.type = TokenType.COMMENT;
/*     */       do {
/* 415 */         ch = this.file.read();
/* 416 */         if ((ch == -1) || (ch == 13)) break;  } while (ch != 10);
/* 417 */       break;
/*     */     case 40:
/* 420 */       outBuf.setLength(0);
/* 421 */       this.type = TokenType.STRING;
/* 422 */       this.hexString = false;
/* 423 */       int nesting = 0;
/*     */       while (true) {
/* 425 */         ch = this.file.read();
/* 426 */         if (ch == -1)
/*     */           break;
/* 428 */         if (ch == 40) {
/* 429 */           nesting++;
/*     */         }
/* 431 */         else if (ch == 41) {
/* 432 */           nesting--;
/*     */         } else {
/* 434 */           if (ch == 92) {
/* 435 */             boolean lineBreak = false;
/* 436 */             ch = this.file.read();
/* 437 */             switch (ch) {
/*     */             case 110:
/* 439 */               ch = 10;
/* 440 */               break;
/*     */             case 114:
/* 442 */               ch = 13;
/* 443 */               break;
/*     */             case 116:
/* 445 */               ch = 9;
/* 446 */               break;
/*     */             case 98:
/* 448 */               ch = 8;
/* 449 */               break;
/*     */             case 102:
/* 451 */               ch = 12;
/* 452 */               break;
/*     */             case 40:
/*     */             case 41:
/*     */             case 92:
/* 456 */               break;
/*     */             case 13:
/* 458 */               lineBreak = true;
/* 459 */               ch = this.file.read();
/* 460 */               if (ch != 10)
/* 461 */                 backOnePosition(ch); break;
/*     */             case 10:
/* 464 */               lineBreak = true;
/* 465 */               break;
/*     */             default:
/* 468 */               if ((ch >= 48) && (ch <= 55))
/*     */               {
/* 471 */                 int octal = ch - 48;
/* 472 */                 ch = this.file.read();
/* 473 */                 if ((ch < 48) || (ch > 55)) {
/* 474 */                   backOnePosition(ch);
/* 475 */                   ch = octal;
/*     */                 }
/*     */                 else {
/* 478 */                   octal = (octal << 3) + ch - 48;
/* 479 */                   ch = this.file.read();
/* 480 */                   if ((ch < 48) || (ch > 55)) {
/* 481 */                     backOnePosition(ch);
/* 482 */                     ch = octal;
/*     */                   }
/*     */                   else {
/* 485 */                     octal = (octal << 3) + ch - 48;
/* 486 */                     ch = octal & 0xFF; }  } 
/* 487 */               }break;
/*     */             }
/*     */ 
/* 490 */             if (lineBreak)
/*     */               continue;
/* 492 */             if (ch < 0)
/*     */               break;
/* 494 */             break label888;
/* 495 */           }if (ch == 13) {
/* 496 */             ch = this.file.read();
/* 497 */             if (ch < 0)
/*     */               break;
/* 499 */             if (ch != 10) {
/* 500 */               backOnePosition(ch);
/* 501 */               ch = 10;
/*     */             }
/*     */           }
/*     */         }
/* 504 */         if (nesting == -1)
/*     */           break;
/* 506 */         outBuf.append((char)ch);
/*     */       }
/* 508 */       if (ch == -1)
/* 509 */         throwError(MessageLocalization.getComposedMessage("error.reading.string", new Object[0])); break;
/*     */     default:
/* 514 */       label888: outBuf.setLength(0);
/* 515 */       if ((ch == 45) || (ch == 43) || (ch == 46) || ((ch >= 48) && (ch <= 57))) {
/* 516 */         this.type = TokenType.NUMBER;
/* 517 */         if (ch == 45)
/*     */         {
/* 519 */           boolean minus = false;
/*     */           do {
/* 521 */             minus = !minus;
/* 522 */             ch = this.file.read();
/* 523 */           }while (ch == 45);
/* 524 */           if (minus)
/* 525 */             outBuf.append('-');
/*     */         }
/*     */         else {
/* 528 */           outBuf.append((char)ch);
/* 529 */           ch = this.file.read();
/*     */         }
/*     */       }
/* 531 */       while ((ch != -1) && (((ch >= 48) && (ch <= 57)) || (ch == 46))) {
/* 532 */         outBuf.append((char)ch);
/* 533 */         ch = this.file.read(); continue;
/*     */ 
/* 537 */         this.type = TokenType.OTHER;
/*     */         do {
/* 539 */           outBuf.append((char)ch);
/* 540 */           ch = this.file.read();
/* 541 */         }while (delims[(ch + 1)] == 0);
/*     */       }
/* 543 */       if (ch != -1) {
/* 544 */         backOnePosition(ch);
/*     */       }
/*     */       break;
/*     */     }
/* 548 */     if (outBuf != null)
/* 549 */       this.stringValue = outBuf.toString();
/* 550 */     return true;
/*     */   }
/*     */ 
/*     */   public long longValue() {
/* 554 */     return Long.parseLong(this.stringValue);
/*     */   }
/*     */ 
/*     */   public int intValue() {
/* 558 */     return Integer.parseInt(this.stringValue);
/*     */   }
/*     */ 
/*     */   public boolean readLineSegment(byte[] input)
/*     */     throws IOException
/*     */   {
/* 573 */     return readLineSegment(input, true);
/*     */   }
/*     */ 
/*     */   public boolean readLineSegment(byte[] input, boolean isNullWhitespace)
/*     */     throws IOException
/*     */   {
/* 589 */     int c = -1;
/* 590 */     boolean eol = false;
/* 591 */     int ptr = 0;
/* 592 */     int len = input.length;
/*     */ 
/* 596 */     while ((ptr < len) && 
/* 597 */       (isWhitespace(c = read(), isNullWhitespace)));
/* 599 */     while ((!eol) && (ptr < len)) {
/* 600 */       switch (c) {
/*     */       case -1:
/*     */       case 10:
/* 603 */         eol = true;
/* 604 */         break;
/*     */       case 13:
/* 606 */         eol = true;
/* 607 */         long cur = getFilePointer();
/* 608 */         if (read() != 10)
/* 609 */           seek(cur); break;
/*     */       default:
/* 613 */         input[(ptr++)] = ((byte)c);
/*     */       }
/*     */ 
/* 618 */       if ((eol) || (len <= ptr)) {
/*     */         break;
/*     */       }
/* 621 */       c = read();
/*     */     }
/*     */ 
/* 624 */     if (ptr >= len) {
/* 625 */       eol = false;
/* 626 */       while (!eol) {
/* 627 */         switch (c = read()) {
/*     */         case -1:
/*     */         case 10:
/* 630 */           eol = true;
/* 631 */           break;
/*     */         case 13:
/* 633 */           eol = true;
/* 634 */           long cur = getFilePointer();
/* 635 */           if (read() != 10) {
/* 636 */             seek(cur);
/*     */           }
/*     */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 643 */     if ((c == -1) && (ptr == 0)) {
/* 644 */       return false;
/*     */     }
/* 646 */     if (ptr + 2 <= len) {
/* 647 */       input[(ptr++)] = 32;
/* 648 */       input[ptr] = 88;
/*     */     }
/* 650 */     return true;
/*     */   }
/*     */ 
/*     */   public static long[] checkObjectStart(byte[] line) {
/*     */     try {
/* 655 */       PRTokeniser tk = new PRTokeniser(new RandomAccessFileOrArray(new RandomAccessSourceFactory().createSource(line)));
/* 656 */       int num = 0;
/* 657 */       int gen = 0;
/* 658 */       if ((!tk.nextToken()) || (tk.getTokenType() != TokenType.NUMBER))
/* 659 */         return null;
/* 660 */       num = tk.intValue();
/* 661 */       if ((!tk.nextToken()) || (tk.getTokenType() != TokenType.NUMBER))
/* 662 */         return null;
/* 663 */       gen = tk.intValue();
/* 664 */       if (!tk.nextToken())
/* 665 */         return null;
/* 666 */       if (!tk.getStringValue().equals("obj"))
/* 667 */         return null;
/* 668 */       return new long[] { num, gen };
/*     */     }
/*     */     catch (Exception ioe)
/*     */     {
/*     */     }
/* 673 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean isHexString() {
/* 677 */     return this.hexString;
/*     */   }
/*     */ 
/*     */   public static enum TokenType
/*     */   {
/*  63 */     NUMBER, 
/*  64 */     STRING, 
/*  65 */     NAME, 
/*  66 */     COMMENT, 
/*  67 */     START_ARRAY, 
/*  68 */     END_ARRAY, 
/*  69 */     START_DIC, 
/*  70 */     END_DIC, 
/*  71 */     REF, 
/*  72 */     OTHER, 
/*  73 */     ENDOFFILE;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PRTokeniser
 * JD-Core Version:    0.6.2
 */