/*     */ package com.itextpdf.text.xml.simpleparser;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.xml.XMLUtil;
/*     */ import com.itextpdf.text.xml.simpleparser.handler.HTMLNewLineHandler;
/*     */ import com.itextpdf.text.xml.simpleparser.handler.NeverNewLineHandler;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.util.HashMap;
/*     */ import java.util.Stack;
/*     */ 
/*     */ public final class SimpleXMLParser
/*     */ {
/*     */   private static final int UNKNOWN = 0;
/*     */   private static final int TEXT = 1;
/*     */   private static final int TAG_ENCOUNTERED = 2;
/*     */   private static final int EXAMIN_TAG = 3;
/*     */   private static final int TAG_EXAMINED = 4;
/*     */   private static final int IN_CLOSETAG = 5;
/*     */   private static final int SINGLE_TAG = 6;
/*     */   private static final int CDATA = 7;
/*     */   private static final int COMMENT = 8;
/*     */   private static final int PI = 9;
/*     */   private static final int ENTITY = 10;
/*     */   private static final int QUOTE = 11;
/*     */   private static final int ATTRIBUTE_KEY = 12;
/*     */   private static final int ATTRIBUTE_EQUAL = 13;
/*     */   private static final int ATTRIBUTE_VALUE = 14;
/*     */   private final Stack<Integer> stack;
/* 128 */   private int character = 0;
/*     */ 
/* 130 */   private int previousCharacter = -1;
/*     */ 
/* 132 */   private int lines = 1;
/*     */ 
/* 134 */   private int columns = 0;
/*     */ 
/* 136 */   private boolean eol = false;
/*     */ 
/* 143 */   private boolean nowhite = false;
/*     */   private int state;
/*     */   private final boolean html;
/* 149 */   private final StringBuffer text = new StringBuffer();
/*     */ 
/* 151 */   private final StringBuffer entity = new StringBuffer();
/*     */ 
/* 153 */   private String tag = null;
/*     */ 
/* 155 */   private HashMap<String, String> attributes = null;
/*     */   private final SimpleXMLDocHandler doc;
/*     */   private final SimpleXMLDocHandlerComment comment;
/* 161 */   private int nested = 0;
/*     */ 
/* 163 */   private int quoteCharacter = 34;
/*     */ 
/* 165 */   private String attributekey = null;
/*     */ 
/* 167 */   private String attributevalue = null;
/*     */   private NewLineHandler newLineHandler;
/*     */ 
/*     */   private SimpleXMLParser(SimpleXMLDocHandler doc, SimpleXMLDocHandlerComment comment, boolean html)
/*     */   {
/* 174 */     this.doc = doc;
/* 175 */     this.comment = comment;
/* 176 */     this.html = html;
/* 177 */     if (html)
/* 178 */       this.newLineHandler = new HTMLNewLineHandler();
/*     */     else {
/* 180 */       this.newLineHandler = new NeverNewLineHandler();
/*     */     }
/* 182 */     this.stack = new Stack();
/* 183 */     this.state = (html ? 1 : 0);
/*     */   }
/*     */ 
/*     */   private void go(Reader r)
/*     */     throws IOException
/*     */   {
/*     */     BufferedReader reader;
/*     */     BufferedReader reader;
/* 192 */     if ((r instanceof BufferedReader))
/* 193 */       reader = (BufferedReader)r;
/*     */     else
/* 195 */       reader = new BufferedReader(r);
/* 196 */     this.doc.startDocument();
/*     */     while (true)
/*     */     {
/* 199 */       if (this.previousCharacter == -1) {
/* 200 */         this.character = reader.read();
/*     */       }
/*     */       else
/*     */       {
/* 204 */         this.character = this.previousCharacter;
/* 205 */         this.previousCharacter = -1;
/*     */       }
/*     */ 
/* 209 */       if (this.character == -1) {
/* 210 */         if (this.html) {
/* 211 */           if ((this.html) && (this.state == 1))
/* 212 */             flush();
/* 213 */           this.doc.endDocument();
/*     */         } else {
/* 215 */           throwException(MessageLocalization.getComposedMessage("missing.end.tag", new Object[0]));
/*     */         }
/* 217 */         return;
/*     */       }
/*     */ 
/* 221 */       if ((this.character == 10) && (this.eol)) {
/* 222 */         this.eol = false;
/*     */       } else {
/* 224 */         if (this.eol) {
/* 225 */           this.eol = false;
/* 226 */         } else if (this.character == 10) {
/* 227 */           this.lines += 1;
/* 228 */           this.columns = 0;
/* 229 */         } else if (this.character == 13) {
/* 230 */           this.eol = true;
/* 231 */           this.character = 10;
/* 232 */           this.lines += 1;
/* 233 */           this.columns = 0;
/*     */         } else {
/* 235 */           this.columns += 1;
/*     */         }
/*     */ 
/* 238 */         switch (this.state)
/*     */         {
/*     */         case 0:
/* 241 */           if (this.character == 60) {
/* 242 */             saveState(1);
/* 243 */             this.state = 2; } break;
/*     */         case 1:
/* 248 */           if (this.character == 60) {
/* 249 */             flush();
/* 250 */             saveState(this.state);
/* 251 */             this.state = 2;
/* 252 */           } else if (this.character == 38) {
/* 253 */             saveState(this.state);
/* 254 */             this.entity.setLength(0);
/* 255 */             this.state = 10;
/* 256 */             this.nowhite = true;
/* 257 */           } else if (this.character == 32) {
/* 258 */             if ((this.html) && (this.nowhite)) {
/* 259 */               this.text.append(' ');
/* 260 */               this.nowhite = false;
/*     */             } else {
/* 262 */               if (this.nowhite) {
/* 263 */                 this.text.append((char)this.character);
/*     */               }
/* 265 */               this.nowhite = false;
/*     */             }
/* 267 */           } else if (Character.isWhitespace((char)this.character)) {
/* 268 */             if (!this.html)
/*     */             {
/* 271 */               if (this.nowhite) {
/* 272 */                 this.text.append((char)this.character);
/*     */               }
/* 274 */               this.nowhite = false;
/*     */             }
/*     */           } else {
/* 277 */             this.text.append((char)this.character);
/* 278 */             this.nowhite = true;
/*     */           }
/* 280 */           break;
/*     */         case 2:
/* 284 */           initTag();
/* 285 */           if (this.character == 47) {
/* 286 */             this.state = 5;
/* 287 */           } else if (this.character == 63) {
/* 288 */             restoreState();
/* 289 */             this.state = 9;
/*     */           } else {
/* 291 */             this.text.append((char)this.character);
/* 292 */             this.state = 3;
/*     */           }
/* 294 */           break;
/*     */         case 3:
/* 298 */           if (this.character == 62) {
/* 299 */             doTag();
/* 300 */             processTag(true);
/* 301 */             initTag();
/* 302 */             this.state = restoreState();
/* 303 */           } else if (this.character == 47) {
/* 304 */             this.state = 6;
/* 305 */           } else if ((this.character == 45) && (this.text.toString().equals("!-"))) {
/* 306 */             flush();
/* 307 */             this.state = 8;
/* 308 */           } else if ((this.character == 91) && (this.text.toString().equals("![CDATA"))) {
/* 309 */             flush();
/* 310 */             this.state = 7;
/* 311 */           } else if ((this.character == 69) && (this.text.toString().equals("!DOCTYP"))) {
/* 312 */             flush();
/* 313 */             this.state = 9;
/* 314 */           } else if (Character.isWhitespace((char)this.character)) {
/* 315 */             doTag();
/* 316 */             this.state = 4;
/*     */           } else {
/* 318 */             this.text.append((char)this.character);
/*     */           }
/* 320 */           break;
/*     */         case 4:
/* 323 */           if (this.character == 62) {
/* 324 */             processTag(true);
/* 325 */             initTag();
/* 326 */             this.state = restoreState();
/* 327 */           } else if (this.character == 47) {
/* 328 */             this.state = 6;
/* 329 */           } else if (!Character.isWhitespace((char)this.character))
/*     */           {
/* 332 */             this.text.append((char)this.character);
/* 333 */             this.state = 12;
/*     */           }
/* 335 */           break;
/*     */         case 5:
/* 339 */           if (this.character == 62) {
/* 340 */             doTag();
/* 341 */             processTag(false);
/* 342 */             if ((!this.html) && (this.nested == 0)) return;
/* 343 */             this.state = restoreState();
/*     */           }
/* 345 */           else if (!Character.isWhitespace((char)this.character)) {
/* 346 */             this.text.append((char)this.character); } break;
/*     */         case 6:
/* 353 */           if (this.character != 62)
/* 354 */             throwException(MessageLocalization.getComposedMessage("expected.gt.for.tag.lt.1.gt", new Object[] { this.tag }));
/* 355 */           doTag();
/* 356 */           processTag(true);
/* 357 */           processTag(false);
/* 358 */           initTag();
/* 359 */           if ((!this.html) && (this.nested == 0)) {
/* 360 */             this.doc.endDocument();
/* 361 */             return;
/*     */           }
/* 363 */           this.state = restoreState();
/* 364 */           break;
/*     */         case 7:
/* 368 */           if ((this.character == 62) && (this.text.toString().endsWith("]]")))
/*     */           {
/* 370 */             this.text.setLength(this.text.length() - 2);
/* 371 */             flush();
/* 372 */             this.state = restoreState();
/*     */           } else {
/* 374 */             this.text.append((char)this.character);
/* 375 */           }break;
/*     */         case 8:
/* 380 */           if ((this.character == 62) && (this.text.toString().endsWith("--")))
/*     */           {
/* 382 */             this.text.setLength(this.text.length() - 2);
/* 383 */             flush();
/* 384 */             this.state = restoreState();
/*     */           } else {
/* 386 */             this.text.append((char)this.character);
/* 387 */           }break;
/*     */         case 9:
/* 391 */           if (this.character == 62) {
/* 392 */             this.state = restoreState();
/* 393 */             if (this.state == 1) this.state = 0;  } break;
/*     */         case 10:
/* 399 */           if (this.character == 59) {
/* 400 */             this.state = restoreState();
/* 401 */             String cent = this.entity.toString();
/* 402 */             this.entity.setLength(0);
/* 403 */             char ce = EntitiesToUnicode.decodeEntity(cent);
/* 404 */             if (ce == 0)
/* 405 */               this.text.append('&').append(cent).append(';');
/*     */             else
/* 407 */               this.text.append(ce);
/* 408 */           } else if (((this.character != 35) && ((this.character < 48) || (this.character > 57)) && ((this.character < 97) || (this.character > 122)) && ((this.character < 65) || (this.character > 90))) || (this.entity.length() >= 7))
/*     */           {
/* 410 */             this.state = restoreState();
/* 411 */             this.previousCharacter = this.character;
/* 412 */             this.text.append('&').append(this.entity.toString());
/* 413 */             this.entity.setLength(0);
/*     */           }
/*     */           else {
/* 416 */             this.entity.append((char)this.character);
/*     */           }
/* 418 */           break;
/*     */         case 11:
/* 421 */           if ((this.html) && (this.quoteCharacter == 32) && (this.character == 62)) {
/* 422 */             flush();
/* 423 */             processTag(true);
/* 424 */             initTag();
/* 425 */             this.state = restoreState();
/*     */           }
/* 427 */           else if ((this.html) && (this.quoteCharacter == 32) && (Character.isWhitespace((char)this.character))) {
/* 428 */             flush();
/* 429 */             this.state = 4;
/*     */           }
/* 431 */           else if ((this.html) && (this.quoteCharacter == 32)) {
/* 432 */             this.text.append((char)this.character);
/*     */           }
/* 434 */           else if (this.character == this.quoteCharacter) {
/* 435 */             flush();
/* 436 */             this.state = 4;
/* 437 */           } else if (" \r\n\t".indexOf(this.character) >= 0) {
/* 438 */             this.text.append(' ');
/* 439 */           } else if (this.character == 38) {
/* 440 */             saveState(this.state);
/* 441 */             this.state = 10;
/* 442 */             this.entity.setLength(0);
/*     */           } else {
/* 444 */             this.text.append((char)this.character);
/*     */           }
/* 446 */           break;
/*     */         case 12:
/* 449 */           if (Character.isWhitespace((char)this.character)) {
/* 450 */             flush();
/* 451 */             this.state = 13;
/* 452 */           } else if (this.character == 61) {
/* 453 */             flush();
/* 454 */             this.state = 14;
/* 455 */           } else if ((this.html) && (this.character == 62)) {
/* 456 */             this.text.setLength(0);
/* 457 */             processTag(true);
/* 458 */             initTag();
/* 459 */             this.state = restoreState();
/*     */           } else {
/* 461 */             this.text.append((char)this.character);
/*     */           }
/* 463 */           break;
/*     */         case 13:
/* 466 */           if (this.character == 61)
/* 467 */             this.state = 14;
/* 468 */           else if (!Character.isWhitespace((char)this.character))
/*     */           {
/* 470 */             if ((this.html) && (this.character == 62)) {
/* 471 */               this.text.setLength(0);
/* 472 */               processTag(true);
/* 473 */               initTag();
/* 474 */               this.state = restoreState();
/* 475 */             } else if ((this.html) && (this.character == 47)) {
/* 476 */               flush();
/* 477 */               this.state = 6;
/* 478 */             } else if (this.html) {
/* 479 */               flush();
/* 480 */               this.text.append((char)this.character);
/* 481 */               this.state = 12;
/*     */             } else {
/* 483 */               throwException(MessageLocalization.getComposedMessage("error.in.attribute.processing", new Object[0]));
/*     */             }
/*     */           }
/* 485 */           break;
/*     */         case 14:
/* 488 */           if ((this.character == 34) || (this.character == 39)) {
/* 489 */             this.quoteCharacter = this.character;
/* 490 */             this.state = 11;
/* 491 */           } else if (!Character.isWhitespace((char)this.character))
/*     */           {
/* 493 */             if ((this.html) && (this.character == 62)) {
/* 494 */               flush();
/* 495 */               processTag(true);
/* 496 */               initTag();
/* 497 */               this.state = restoreState();
/* 498 */             } else if (this.html) {
/* 499 */               this.text.append((char)this.character);
/* 500 */               this.quoteCharacter = 32;
/* 501 */               this.state = 11;
/*     */             } else {
/* 503 */               throwException(MessageLocalization.getComposedMessage("error.in.attribute.processing", new Object[0]));
/*     */             }
/*     */           }
/*     */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private int restoreState()
/*     */   {
/* 515 */     if (!this.stack.empty()) {
/* 516 */       return ((Integer)this.stack.pop()).intValue();
/*     */     }
/* 518 */     return 0;
/*     */   }
/*     */ 
/*     */   private void saveState(int s)
/*     */   {
/* 525 */     this.stack.push(Integer.valueOf(s));
/*     */   }
/*     */ 
/*     */   private void flush()
/*     */   {
/* 533 */     switch (this.state) {
/*     */     case 1:
/*     */     case 7:
/* 536 */       if (this.text.length() > 0)
/* 537 */         this.doc.text(this.text.toString()); break;
/*     */     case 8:
/* 541 */       if (this.comment != null)
/* 542 */         this.comment.comment(this.text.toString()); break;
/*     */     case 12:
/* 546 */       this.attributekey = this.text.toString();
/* 547 */       if (this.html)
/* 548 */         this.attributekey = this.attributekey.toLowerCase(); break;
/*     */     case 11:
/*     */     case 14:
/* 552 */       this.attributevalue = this.text.toString();
/* 553 */       this.attributes.put(this.attributekey, this.attributevalue);
/* 554 */       break;
/*     */     case 2:
/*     */     case 3:
/*     */     case 4:
/*     */     case 5:
/*     */     case 6:
/*     */     case 9:
/*     */     case 10:
/* 558 */     case 13: } this.text.setLength(0);
/*     */   }
/*     */ 
/*     */   private void initTag()
/*     */   {
/* 564 */     this.tag = null;
/* 565 */     this.attributes = new HashMap();
/*     */   }
/*     */ 
/*     */   private void doTag() {
/* 569 */     if (this.tag == null)
/* 570 */       this.tag = this.text.toString();
/* 571 */     if (this.html)
/* 572 */       this.tag = this.tag.toLowerCase();
/* 573 */     this.text.setLength(0);
/*     */   }
/*     */ 
/*     */   private void processTag(boolean start)
/*     */   {
/* 580 */     if (start) {
/* 581 */       this.nested += 1;
/* 582 */       this.doc.startElement(this.tag, this.attributes);
/*     */     }
/*     */     else
/*     */     {
/* 586 */       if (this.newLineHandler.isNewLineTag(this.tag)) {
/* 587 */         this.nowhite = false;
/*     */       }
/* 589 */       this.nested -= 1;
/* 590 */       this.doc.endElement(this.tag);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void throwException(String s) throws IOException {
/* 595 */     throw new IOException(MessageLocalization.getComposedMessage("1.near.line.2.column.3", new Object[] { s, String.valueOf(this.lines), String.valueOf(this.columns) }));
/*     */   }
/*     */ 
/*     */   public static void parse(SimpleXMLDocHandler doc, SimpleXMLDocHandlerComment comment, Reader r, boolean html)
/*     */     throws IOException
/*     */   {
/* 607 */     SimpleXMLParser parser = new SimpleXMLParser(doc, comment, html);
/* 608 */     parser.go(r);
/*     */   }
/*     */ 
/*     */   public static void parse(SimpleXMLDocHandler doc, InputStream in)
/*     */     throws IOException
/*     */   {
/* 618 */     byte[] b4 = new byte[4];
/* 619 */     int count = in.read(b4);
/* 620 */     if (count != 4)
/* 621 */       throw new IOException(MessageLocalization.getComposedMessage("insufficient.length", new Object[0]));
/* 622 */     String encoding = XMLUtil.getEncodingName(b4);
/* 623 */     String decl = null;
/* 624 */     if (encoding.equals("UTF-8")) {
/* 625 */       StringBuffer sb = new StringBuffer();
/*     */       int c;
/* 627 */       while (((c = in.read()) != -1) && 
/* 628 */         (c != 62))
/*     */       {
/* 630 */         sb.append((char)c);
/*     */       }
/* 632 */       decl = sb.toString();
/*     */     }
/* 634 */     else if (encoding.equals("CP037")) {
/* 635 */       ByteArrayOutputStream bi = new ByteArrayOutputStream();
/*     */       int c;
/* 637 */       while (((c = in.read()) != -1) && 
/* 638 */         (c != 110))
/*     */       {
/* 640 */         bi.write(c);
/*     */       }
/* 642 */       decl = new String(bi.toByteArray(), "CP037");
/*     */     }
/* 644 */     if (decl != null) {
/* 645 */       decl = getDeclaredEncoding(decl);
/* 646 */       if (decl != null)
/* 647 */         encoding = decl;
/*     */     }
/* 649 */     parse(doc, new InputStreamReader(in, IanaEncodings.getJavaEncoding(encoding)));
/*     */   }
/*     */ 
/*     */   private static String getDeclaredEncoding(String decl) {
/* 653 */     if (decl == null)
/* 654 */       return null;
/* 655 */     int idx = decl.indexOf("encoding");
/* 656 */     if (idx < 0)
/* 657 */       return null;
/* 658 */     int idx1 = decl.indexOf('"', idx);
/* 659 */     int idx2 = decl.indexOf('\'', idx);
/* 660 */     if (idx1 == idx2)
/* 661 */       return null;
/* 662 */     if (((idx1 < 0) && (idx2 > 0)) || ((idx2 > 0) && (idx2 < idx1))) {
/* 663 */       int idx3 = decl.indexOf('\'', idx2 + 1);
/* 664 */       if (idx3 < 0)
/* 665 */         return null;
/* 666 */       return decl.substring(idx2 + 1, idx3);
/*     */     }
/* 668 */     if (((idx2 < 0) && (idx1 > 0)) || ((idx1 > 0) && (idx1 < idx2))) {
/* 669 */       int idx3 = decl.indexOf('"', idx1 + 1);
/* 670 */       if (idx3 < 0)
/* 671 */         return null;
/* 672 */       return decl.substring(idx1 + 1, idx3);
/*     */     }
/* 674 */     return null;
/*     */   }
/*     */ 
/*     */   public static void parse(SimpleXMLDocHandler doc, Reader r)
/*     */     throws IOException
/*     */   {
/* 683 */     parse(doc, null, r, false);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public static String escapeXML(String s, boolean onlyASCII)
/*     */   {
/* 700 */     return XMLUtil.escapeXML(s, onlyASCII);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.xml.simpleparser.SimpleXMLParser
 * JD-Core Version:    0.6.2
 */