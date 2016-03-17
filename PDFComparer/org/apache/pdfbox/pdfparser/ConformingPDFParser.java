/*     */ package org.apache.pdfbox.pdfparser;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.cos.COSFloat;
/*     */ import org.apache.pdfbox.cos.COSInteger;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ import org.apache.pdfbox.cos.COSObject;
/*     */ import org.apache.pdfbox.cos.COSString;
/*     */ import org.apache.pdfbox.cos.COSUnread;
/*     */ import org.apache.pdfbox.io.PushBackInputStream;
/*     */ import org.apache.pdfbox.io.RandomAccess;
/*     */ import org.apache.pdfbox.io.RandomAccessFile;
/*     */ import org.apache.pdfbox.pdmodel.ConformingPDDocument;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.common.XrefEntry;
/*     */ import org.apache.pdfbox.persistence.util.COSObjectKey;
/*     */ 
/*     */ public class ConformingPDFParser extends BaseParser
/*     */ {
/*     */   protected RandomAccess inputFile;
/*     */   List<XrefEntry> xrefEntries;
/*     */   private long currentOffset;
/*  51 */   private ConformingPDDocument doc = null;
/*  52 */   private boolean throwNonConformingException = true;
/*  53 */   private boolean recursivlyRead = true;
/*     */ 
/*     */   public ConformingPDFParser(File inputFile)
/*     */     throws IOException
/*     */   {
/*  63 */     this.inputFile = new RandomAccessFile(inputFile, "r");
/*     */   }
/*     */ 
/*     */   public void parse()
/*     */     throws IOException
/*     */   {
/*  74 */     this.document = new COSDocument();
/*  75 */     this.doc = new ConformingPDDocument(this.document);
/*  76 */     this.currentOffset = (this.inputFile.length() - 1L);
/*  77 */     long xRefTableLocation = parseTrailerInformation();
/*  78 */     this.currentOffset = xRefTableLocation;
/*  79 */     parseXrefTable();
/*     */ 
/*  82 */     boolean oldValue = this.recursivlyRead;
/*  83 */     this.recursivlyRead = false;
/*  84 */     List keys = this.doc.getObjectKeysFromPool();
/*  85 */     for (COSObjectKey key : keys)
/*     */     {
/*  87 */       getObject(key.getNumber(), key.getGeneration());
/*     */     }
/*  89 */     this.recursivlyRead = oldValue;
/*     */   }
/*     */ 
/*     */   public COSDocument getDocument()
/*     */     throws IOException
/*     */   {
/* 102 */     if (this.document == null) {
/* 103 */       throw new IOException("You must call parse() before calling getDocument()");
/*     */     }
/* 105 */     return this.document;
/*     */   }
/*     */ 
/*     */   public PDDocument getPDDocument()
/*     */     throws IOException
/*     */   {
/* 117 */     return this.doc;
/*     */   }
/*     */ 
/*     */   private boolean parseXrefTable() throws IOException {
/* 121 */     String currentLine = readLine();
/* 122 */     if ((this.throwNonConformingException) && 
/* 123 */       (!"xref".equals(currentLine))) {
/* 124 */       throw new AssertionError("xref table not found.\nExpected: xref\nFound: " + currentLine);
/*     */     }
/*     */ 
/* 127 */     int objectNumber = readInt();
/* 128 */     int entries = readInt();
/* 129 */     this.xrefEntries = new ArrayList(entries);
/* 130 */     for (int i = 0; i < entries; i++) {
/* 131 */       this.xrefEntries.add(new XrefEntry(objectNumber++, readInt(), readInt(), readLine()));
/*     */     }
/* 133 */     return true;
/*     */   }
/*     */ 
/*     */   protected long parseTrailerInformation() throws IOException, NumberFormatException {
/* 137 */     long xrefLocation = -1L;
/* 138 */     consumeWhitespaceBackwards();
/* 139 */     String currentLine = readLineBackwards();
/* 140 */     if ((this.throwNonConformingException) && 
/* 141 */       (!"%%EOF".equals(currentLine))) {
/* 142 */       throw new AssertionError("Invalid EOF marker.\nExpected: %%EOF\nFound: " + currentLine);
/*     */     }
/*     */ 
/* 145 */     xrefLocation = readLongBackwards();
/* 146 */     currentLine = readLineBackwards();
/* 147 */     if ((this.throwNonConformingException) && 
/* 148 */       (!"startxref".equals(currentLine))) {
/* 149 */       throw new AssertionError("Invalid trailer.\nExpected: startxref\nFound: " + currentLine);
/*     */     }
/*     */ 
/* 152 */     this.document.setTrailer(readDictionaryBackwards());
/* 153 */     consumeWhitespaceBackwards();
/* 154 */     currentLine = readLineBackwards();
/* 155 */     if ((this.throwNonConformingException) && 
/* 156 */       (!"trailer".equals(currentLine))) {
/* 157 */       throw new AssertionError("Invalid trailer.\nExpected: trailer\nFound: " + currentLine);
/*     */     }
/*     */ 
/* 160 */     return xrefLocation;
/*     */   }
/*     */ 
/*     */   protected byte readByteBackwards() throws IOException {
/* 164 */     this.inputFile.seek(this.currentOffset);
/* 165 */     byte singleByte = (byte)this.inputFile.read();
/* 166 */     this.currentOffset -= 1L;
/* 167 */     return singleByte;
/*     */   }
/*     */ 
/*     */   protected byte readByte() throws IOException {
/* 171 */     this.inputFile.seek(this.currentOffset);
/* 172 */     byte singleByte = (byte)this.inputFile.read();
/* 173 */     this.currentOffset += 1L;
/* 174 */     return singleByte;
/*     */   }
/*     */ 
/*     */   protected String readBackwardUntilWhitespace() throws IOException {
/* 178 */     StringBuilder sb = new StringBuilder();
/* 179 */     byte singleByte = readByteBackwards();
/* 180 */     while (!isWhitespace(singleByte)) {
/* 181 */       sb.insert(0, (char)singleByte);
/* 182 */       singleByte = readByteBackwards();
/*     */     }
/* 184 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   protected byte consumeWhitespaceBackwards()
/*     */     throws IOException
/*     */   {
/* 196 */     this.inputFile.seek(this.currentOffset);
/* 197 */     byte singleByte = (byte)this.inputFile.read();
/* 198 */     if (!isWhitespace(singleByte)) {
/* 199 */       return singleByte;
/*     */     }
/*     */ 
/* 202 */     while (isWhitespace(singleByte)) {
/* 203 */       singleByte = readByteBackwards();
/*     */     }
/*     */ 
/* 207 */     this.currentOffset += 1L;
/* 208 */     return singleByte;
/*     */   }
/*     */ 
/*     */   protected byte consumeWhitespace()
/*     */     throws IOException
/*     */   {
/* 220 */     this.inputFile.seek(this.currentOffset);
/* 221 */     byte singleByte = (byte)this.inputFile.read();
/* 222 */     if (!isWhitespace(singleByte)) {
/* 223 */       return singleByte;
/*     */     }
/*     */ 
/* 226 */     while (isWhitespace(singleByte)) {
/* 227 */       singleByte = readByte();
/*     */     }
/*     */ 
/* 231 */     this.currentOffset -= 1L;
/* 232 */     return singleByte;
/*     */   }
/*     */ 
/*     */   protected long readLongBackwards()
/*     */     throws IOException, NumberFormatException
/*     */   {
/* 245 */     StringBuilder sb = new StringBuilder();
/* 246 */     consumeWhitespaceBackwards();
/* 247 */     byte singleByte = readByteBackwards();
/* 248 */     while (!isWhitespace(singleByte)) {
/* 249 */       sb.insert(0, (char)singleByte);
/* 250 */       singleByte = readByteBackwards();
/*     */     }
/* 252 */     if (sb.length() == 0)
/* 253 */       throw new AssertionError("Number not found.  Expected number at offset: " + this.currentOffset);
/* 254 */     return Long.parseLong(sb.toString());
/*     */   }
/*     */ 
/*     */   protected int readInt() throws IOException
/*     */   {
/* 259 */     StringBuilder sb = new StringBuilder();
/* 260 */     consumeWhitespace();
/* 261 */     byte singleByte = readByte();
/* 262 */     while (!isWhitespace(singleByte)) {
/* 263 */       sb.append((char)singleByte);
/* 264 */       singleByte = readByte();
/*     */     }
/* 266 */     if (sb.length() == 0)
/* 267 */       throw new AssertionError("Number not found.  Expected number at offset: " + this.currentOffset);
/* 268 */     return Integer.parseInt(sb.toString());
/*     */   }
/*     */ 
/*     */   protected COSNumber readNumber()
/*     */     throws IOException
/*     */   {
/* 278 */     StringBuilder sb = new StringBuilder();
/* 279 */     consumeWhitespace();
/* 280 */     byte singleByte = readByte();
/* 281 */     while (!isWhitespace(singleByte)) {
/* 282 */       sb.append((char)singleByte);
/* 283 */       singleByte = readByte();
/*     */     }
/* 285 */     if (sb.length() == 0)
/* 286 */       throw new AssertionError("Number not found.  Expected number at offset: " + this.currentOffset);
/* 287 */     return parseNumber(sb.toString());
/*     */   }
/*     */ 
/*     */   protected COSNumber parseNumber(String number) throws IOException {
/* 291 */     if (number.matches("^[0-9]+$"))
/* 292 */       return COSInteger.get(number);
/* 293 */     return new COSFloat(Float.parseFloat(number));
/*     */   }
/*     */ 
/*     */   protected COSBase processCosObject(String string) throws IOException {
/* 297 */     if ((string != null) && (string.endsWith(">")))
/*     */     {
/* 299 */       return COSString.createFromHexString(string.replaceAll("^<", "").replaceAll(">$", ""));
/*     */     }
/* 301 */     return null;
/*     */   }
/*     */ 
/*     */   protected COSBase readObjectBackwards() throws IOException {
/* 305 */     COSBase obj = null;
/* 306 */     consumeWhitespaceBackwards();
/* 307 */     String lastSection = readBackwardUntilWhitespace();
/* 308 */     if ("R".equals(lastSection))
/*     */     {
/* 310 */       long gen = readLongBackwards();
/* 311 */       long number = readLongBackwards();
/*     */ 
/* 313 */       this.doc.putObjectInPool(new COSUnread(), number, gen);
/* 314 */       obj = new COSUnread(number, gen, this); } else {
/* 315 */       if (">>".equals(lastSection))
/*     */       {
/* 317 */         throw new RuntimeException("Not yet implemented");
/* 318 */       }if ((lastSection != null) && (lastSection.endsWith("]")))
/*     */       {
/* 320 */         COSArray array = new COSArray();
/* 321 */         lastSection = lastSection.replaceAll("]$", "");
/* 322 */         while (!lastSection.startsWith("[")) {
/* 323 */           if (lastSection.matches("^\\s*<.*>\\s*$"))
/* 324 */             array.add(COSString.createFromHexString(lastSection.replaceAll("^\\s*<", "").replaceAll(">\\s*$", "")));
/* 325 */           lastSection = readBackwardUntilWhitespace();
/*     */         }
/* 327 */         lastSection = lastSection.replaceAll("^\\[", "");
/* 328 */         if (lastSection.matches("^\\s*<.*>\\s*$"))
/* 329 */           array.add(COSString.createFromHexString(lastSection.replaceAll("^\\s*<", "").replaceAll(">\\s*$", "")));
/* 330 */         obj = array;
/* 331 */       } else if ((lastSection != null) && (lastSection.endsWith(">")))
/*     */       {
/* 333 */         obj = processCosObject(lastSection);
/*     */       }
/*     */       else {
/*     */         try {
/* 337 */           Long.parseLong(lastSection);
/* 338 */           obj = COSNumber.get(lastSection);
/*     */         } catch (NumberFormatException e) {
/* 340 */           throw new RuntimeException("Not yet implemented");
/*     */         }
/*     */       }
/*     */     }
/* 344 */     return obj;
/*     */   }
/*     */ 
/*     */   protected COSName readNameBackwards() throws IOException {
/* 348 */     String name = readBackwardUntilWhitespace();
/* 349 */     name = name.replaceAll("^/", "");
/* 350 */     return COSName.getPDFName(name);
/*     */   }
/*     */ 
/*     */   public COSBase getObject(long objectNumber, long generation)
/*     */     throws IOException
/*     */   {
/* 356 */     XrefEntry entry = (XrefEntry)this.xrefEntries.get((int)objectNumber);
/* 357 */     this.currentOffset = entry.getByteOffset();
/* 358 */     return readObject(objectNumber, generation);
/*     */   }
/*     */ 
/*     */   public COSBase readObject(long objectNumber, long generation)
/*     */     throws IOException
/*     */   {
/* 372 */     if ((this.document != null) && (this.recursivlyRead))
/*     */     {
/* 374 */       COSBase obj = this.doc.getObjectFromPool(objectNumber, generation);
/* 375 */       if (obj != null) {
/* 376 */         return obj;
/*     */       }
/*     */     }
/* 379 */     int actualObjectNumber = readInt();
/* 380 */     if ((objectNumber != actualObjectNumber) && 
/* 381 */       (this.throwNonConformingException)) {
/* 382 */       throw new AssertionError("Object numer expected was " + objectNumber + " but actual was " + actualObjectNumber);
/*     */     }
/* 384 */     consumeWhitespace();
/*     */ 
/* 386 */     int actualGeneration = readInt();
/* 387 */     if ((generation != actualGeneration) && 
/* 388 */       (this.throwNonConformingException)) {
/* 389 */       throw new AssertionError("Generation expected was " + generation + " but actual was " + actualGeneration);
/*     */     }
/* 391 */     consumeWhitespace();
/*     */ 
/* 393 */     String obj = readWord();
/* 394 */     if ((!"obj".equals(obj)) && 
/* 395 */       (this.throwNonConformingException)) {
/* 396 */       throw new AssertionError("Expected keyword 'obj' but found " + obj);
/*     */     }
/*     */ 
/* 400 */     this.doc.putObjectInPool(new COSObject(null), objectNumber, generation);
/* 401 */     COSBase object = readObject();
/* 402 */     this.doc.putObjectInPool(object, objectNumber, generation);
/* 403 */     return object;
/*     */   }
/*     */ 
/*     */   protected COSBase readObject()
/*     */     throws IOException
/*     */   {
/* 412 */     consumeWhitespace();
/* 413 */     String string = readWord();
/* 414 */     if (string.startsWith("<<"))
/*     */     {
/* 416 */       COSDictionary dictionary = new COSDictionary();
/* 417 */       boolean atEndOfDictionary = false;
/*     */ 
/* 419 */       string = string.replaceAll("^<<", "");
/*     */ 
/* 421 */       if (("".equals(string)) || (string.matches("^\\w$")))
/* 422 */         string = readWord().trim();
/* 423 */       while (!atEndOfDictionary) {
/* 424 */         COSName name = COSName.getPDFName(string);
/* 425 */         COSBase object = readObject();
/* 426 */         dictionary.setItem(name, object);
/*     */ 
/* 428 */         byte singleByte = consumeWhitespace();
/* 429 */         if (singleByte == 62) {
/* 430 */           readByte();
/* 431 */           atEndOfDictionary = true;
/*     */         }
/* 433 */         if (!atEndOfDictionary)
/* 434 */           string = readWord().trim();
/*     */       }
/* 436 */       return dictionary;
/* 437 */     }if (string.startsWith("/"))
/*     */     {
/* 439 */       COSBase name = COSName.getPDFName(string);
/* 440 */       return name;
/* 441 */     }if (string.startsWith("-"))
/*     */     {
/* 443 */       return parseNumber(string);
/* 444 */     }if ((string.charAt(0) >= '0') && (string.charAt(0) <= '9'))
/*     */     {
/* 447 */       long tempOffset = this.currentOffset;
/* 448 */       consumeWhitespace();
/* 449 */       String tempString = readWord();
/* 450 */       if (tempString.matches("^[0-9]+$"))
/*     */       {
/* 452 */         tempString = readWord();
/* 453 */         if (!"R".equals(tempString))
/*     */         {
/* 455 */           this.currentOffset = tempOffset;
/* 456 */           return parseNumber(string);
/*     */         }
/*     */       }
/*     */       else {
/* 460 */         this.currentOffset = tempOffset;
/* 461 */         return parseNumber(string);
/*     */       }
/*     */ 
/* 465 */       this.currentOffset = tempOffset;
/* 466 */       int number = Integer.parseInt(string);
/* 467 */       int gen = readInt();
/* 468 */       String r = readWord();
/*     */ 
/* 470 */       if ((!"R".equals(r)) && 
/* 471 */         (this.throwNonConformingException)) {
/* 472 */         throw new AssertionError("Expected keyword 'R' but found " + r);
/*     */       }
/* 474 */       if (this.recursivlyRead)
/*     */       {
/* 476 */         long tempLocation = this.currentOffset;
/* 477 */         this.currentOffset = ((XrefEntry)this.xrefEntries.get(number)).getByteOffset();
/* 478 */         COSBase returnValue = readObject(number, gen);
/* 479 */         this.currentOffset = tempLocation;
/* 480 */         return returnValue;
/*     */       }
/*     */ 
/* 483 */       COSObject obj = new COSObject(new COSUnread());
/* 484 */       obj.setObjectNumber(COSInteger.get(number));
/* 485 */       obj.setGenerationNumber(COSInteger.get(gen));
/* 486 */       return obj;
/*     */     }
/* 488 */     if (string.startsWith("]"))
/*     */     {
/* 490 */       if ("]".equals(string))
/* 491 */         return null;
/* 492 */       int oldLength = string.length();
/* 493 */       this.currentOffset -= oldLength;
/* 494 */       return null;
/* 495 */     }if (string.startsWith("["))
/*     */     {
/* 499 */       int oldLength = string.length();
/* 500 */       string = "[";
/* 501 */       this.currentOffset -= oldLength - string.length() + 1;
/*     */ 
/* 503 */       COSArray array = new COSArray();
/* 504 */       COSBase object = readObject();
/* 505 */       while (object != null) {
/* 506 */         array.add(object);
/* 507 */         object = readObject();
/*     */       }
/* 509 */       return array;
/* 510 */     }if (string.startsWith("("))
/*     */     {
/* 512 */       StringBuilder sb = new StringBuilder(string.substring(1));
/* 513 */       byte singleByte = readByte();
/* 514 */       while (singleByte != 41) {
/* 515 */         sb.append((char)singleByte);
/* 516 */         singleByte = readByte();
/*     */       }
/* 518 */       return new COSString(sb.toString());
/*     */     }
/* 520 */     throw new RuntimeException("Not yet implemented: " + string + " loation=" + this.currentOffset);
/*     */   }
/*     */ 
/*     */   protected String readString()
/*     */     throws IOException
/*     */   {
/* 532 */     consumeWhitespace();
/* 533 */     StringBuilder buffer = new StringBuilder();
/* 534 */     int c = this.pdfSource.read();
/* 535 */     while ((!isEndOfName((char)c)) && (!isClosing(c)) && (c != -1)) {
/* 536 */       buffer.append((char)c);
/* 537 */       c = this.pdfSource.read();
/*     */     }
/* 539 */     if (c != -1) {
/* 540 */       this.pdfSource.unread(c);
/*     */     }
/* 542 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */   protected COSDictionary readDictionaryBackwards() throws IOException {
/* 546 */     COSDictionary dict = new COSDictionary();
/*     */ 
/* 549 */     consumeWhitespaceBackwards();
/* 550 */     byte singleByte = readByteBackwards();
/* 551 */     if ((this.throwNonConformingException) && 
/* 552 */       (singleByte != 62)) {
/* 553 */       throw new AssertionError("");
/*     */     }
/* 555 */     singleByte = readByteBackwards();
/* 556 */     if ((this.throwNonConformingException) && 
/* 557 */       (singleByte != 62)) {
/* 558 */       throw new AssertionError("");
/*     */     }
/*     */ 
/* 562 */     boolean atEndOfDictionary = false;
/* 563 */     singleByte = consumeWhitespaceBackwards();
/* 564 */     if (singleByte == 60) {
/* 565 */       this.inputFile.seek(this.currentOffset - 1L);
/* 566 */       atEndOfDictionary = (byte)this.inputFile.read() == 60;
/*     */     }
/*     */ 
/* 569 */     COSDictionary backwardsDictionary = new COSDictionary();
/*     */ 
/* 571 */     while (!atEndOfDictionary) {
/* 572 */       COSBase object = readObjectBackwards();
/* 573 */       COSName name = readNameBackwards();
/* 574 */       backwardsDictionary.setItem(name, object);
/*     */ 
/* 576 */       singleByte = consumeWhitespaceBackwards();
/* 577 */       if (singleByte == 60) {
/* 578 */         this.inputFile.seek(this.currentOffset - 1L);
/* 579 */         atEndOfDictionary = (byte)this.inputFile.read() == 60;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 585 */     Set backwardsKeys = backwardsDictionary.keySet();
/* 586 */     for (int i = backwardsKeys.size() - 1; i >= 0; i--) {
/* 587 */       dict.setItem((COSName)backwardsKeys.toArray()[i], backwardsDictionary.getItem((COSName)backwardsKeys.toArray()[i]));
/*     */     }
/*     */ 
/* 590 */     readByteBackwards();
/* 591 */     readByteBackwards();
/*     */ 
/* 593 */     return dict;
/*     */   }
/*     */ 
/*     */   protected String readLineBackwards()
/*     */     throws IOException
/*     */   {
/* 605 */     StringBuilder sb = new StringBuilder();
/* 606 */     boolean endOfObject = false;
/*     */     do
/*     */     {
/* 610 */       byte singleByte = readByteBackwards();
/* 611 */       if (singleByte == 10)
/*     */       {
/* 613 */         this.inputFile.seek(this.currentOffset);
/* 614 */         if ((byte)this.inputFile.read() == 13)
/* 615 */           this.currentOffset -= 1L;
/* 616 */         endOfObject = true;
/* 617 */       } else if (singleByte == 13) {
/* 618 */         endOfObject = true;
/*     */       } else {
/* 620 */         sb.insert(0, (char)singleByte);
/*     */       }
/*     */     }
/* 622 */     while (!endOfObject);
/*     */ 
/* 624 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   protected String readLine()
/*     */     throws IOException
/*     */   {
/* 636 */     StringBuilder sb = new StringBuilder();
/* 637 */     boolean endOfLine = false;
/*     */     do
/*     */     {
/* 641 */       byte singleByte = readByte();
/* 642 */       if (singleByte == 10)
/*     */       {
/* 644 */         this.inputFile.seek(this.currentOffset);
/* 645 */         if ((byte)this.inputFile.read() == 13)
/* 646 */           this.currentOffset += 1L;
/* 647 */         endOfLine = true;
/* 648 */       } else if (singleByte == 13) {
/* 649 */         endOfLine = true;
/*     */       } else {
/* 651 */         sb.append((char)singleByte);
/*     */       }
/*     */     }
/* 653 */     while (!endOfLine);
/*     */ 
/* 655 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   protected String readWord() throws IOException {
/* 659 */     StringBuilder sb = new StringBuilder();
/* 660 */     boolean stop = true;
/*     */     do {
/* 662 */       byte singleByte = readByte();
/* 663 */       stop = isWhitespace(singleByte);
/*     */ 
/* 667 */       if ((!stop) && (sb.length() > 0)) {
/* 668 */         stop = (singleByte == 47) || (singleByte == 91) || (singleByte == 93) || ((singleByte == 62) && (!">".equals(sb.toString())));
/*     */ 
/* 671 */         if (stop)
/* 672 */           this.currentOffset -= 1L;
/*     */       }
/* 674 */       if (!stop)
/* 675 */         sb.append((char)singleByte); 
/*     */     }
/* 676 */     while (!stop);
/*     */ 
/* 678 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public boolean isRecursivlyRead()
/*     */   {
/* 685 */     return this.recursivlyRead;
/*     */   }
/*     */ 
/*     */   public void setRecursivlyRead(boolean recursivlyRead)
/*     */   {
/* 692 */     this.recursivlyRead = recursivlyRead;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdfparser.ConformingPDFParser
 * JD-Core Version:    0.6.2
 */