/*      */ package org.apache.pdfbox.preflight.javacc;
/*      */ 
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.io.Reader;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import org.apache.pdfbox.preflight.exception.BodyParseException;
/*      */ import org.apache.pdfbox.preflight.exception.CrossRefParseException;
/*      */ import org.apache.pdfbox.preflight.exception.HeaderParseException;
/*      */ import org.apache.pdfbox.preflight.exception.PdfParseException;
/*      */ import org.apache.pdfbox.preflight.exception.TrailerParseException;
/*      */ 
/*      */ public class PDFParser
/*      */   implements PDFParserConstants
/*      */ {
/*   16 */   public String pdfHeader = "";
/*      */   public PDFParserTokenManager token_source;
/*      */   SimpleCharStream jj_input_stream;
/*      */   public Token token;
/*      */   public Token jj_nt;
/*      */   private int jj_ntk;
/*      */   private int jj_gen;
/*  849 */   private final int[] jj_la1 = new int[37];
/*      */   private static int[] jj_la1_0;
/*      */   private static int[] jj_la1_1;
/*  970 */   private List<int[]> jj_expentries = new ArrayList();
/*      */   private int[] jj_expentry;
/*  972 */   private int jj_kind = -1;
/*      */ 
/*      */   public static boolean parse(InputStream is)
/*      */     throws IOException, ParseException
/*      */   {
/*   19 */     PDFParser parser = new PDFParser(is);
/*   20 */     parser.PDF();
/*   21 */     return true;
/*      */   }
/*      */ 
/*      */   public static void main(String[] args)
/*      */   {
/*   26 */     String filename = null;
/*   27 */     long initTime = 0L;
/*   28 */     long parseTime = 0L;
/*   29 */     long startTime = 0L;
/*   30 */     long stopTime = 0L;
/*      */     PDFParser parser;
/*      */     PDFParser parser;
/*   31 */     if (args.length == 0)
/*      */     {
/*   33 */       System.out.println("PDF Parser  . . .");
/*   34 */       parser = new PDFParser(System.in);
/*   35 */     } else if (args.length == 1)
/*      */     {
/*   37 */       filename = args[0];
/*   38 */       System.out.println("PDF Parser :  Reading from file " + filename + " . . .");
/*      */       try
/*      */       {
/*   41 */         startTime = System.currentTimeMillis();
/*   42 */         parser = new PDFParser(new FileInputStream(filename));
/*   43 */         stopTime = System.currentTimeMillis();
/*   44 */         initTime = stopTime - startTime;
/*      */       }
/*      */       catch (FileNotFoundException e) {
/*   47 */         System.out.println("PDF Parser :  File " + filename + " not found.");
/*   48 */         return;
/*      */       }
/*      */     }
/*      */     else {
/*   52 */       System.out.println("PDF Parser :  Usage is one of:");
/*   53 */       System.out.println("         java PDFParser < inputfile");
/*   54 */       System.out.println("OR");
/*   55 */       System.out.println("         java PDFParser inputfile");
/*   56 */       return;
/*      */     }
/*      */     try
/*      */     {
/*   60 */       startTime = System.currentTimeMillis();
/*      */ 
/*   62 */       parser.PDF();
/*      */ 
/*   64 */       stopTime = System.currentTimeMillis();
/*   65 */       parseTime = stopTime - startTime;
/*   66 */       System.out.println("PDF Parser ");
/*   67 */       System.out.print("   PDF Parser parsed " + filename + " successfully in " + (initTime + parseTime) + " ms.");
/*   68 */       System.out.println(" Init. : " + initTime + " ms / parse time : " + parseTime + " ms");
/*      */     }
/*      */     catch (ParseException e) {
/*   71 */       e.printStackTrace(System.out);
/*   72 */       System.out.println("PDF Parser :  Encountered errors during parse.");
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void indirect_object() throws ParseException {
/*   77 */     jj_consume_token(20);
/*   78 */     object_content();
/*   79 */     jj_consume_token(9);
/*      */   }
/*      */ 
/*      */   public final void object_content() throws ParseException
/*      */   {
/*      */     while (true) {
/*   85 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*      */       {
/*      */       case 1:
/*      */       case 2:
/*      */       case 3:
/*   90 */         break;
/*      */       default:
/*   92 */         this.jj_la1[0] = this.jj_gen;
/*   93 */         break;
/*      */       }
/*   95 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
/*      */       case 1:
/*   97 */         jj_consume_token(1);
/*   98 */         break;
/*      */       case 2:
/*  100 */         jj_consume_token(2);
/*  101 */         break;
/*      */       case 3:
/*  103 */         jj_consume_token(3);
/*      */       }
/*      */     }
/*  106 */     this.jj_la1[1] = this.jj_gen;
/*  107 */     jj_consume_token(-1);
/*  108 */     throw new ParseException();
/*      */ 
/*  111 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
/*      */     case 11:
/*      */     case 12:
/*      */     case 13:
/*      */     case 14:
/*      */     case 15:
/*      */     case 17:
/*      */     case 18:
/*  119 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
/*      */       case 11:
/*  121 */         jj_consume_token(11);
/*  122 */         break;
/*      */       case 12:
/*  124 */         jj_consume_token(12);
/*  125 */         checkNumericLength();
/*  126 */         break;
/*      */       case 13:
/*  128 */         jj_consume_token(13);
/*  129 */         checkStringHexLength();
/*  130 */         break;
/*      */       case 14:
/*  132 */         start_literal();
/*  133 */         break;
/*      */       case 15:
/*  135 */         array_of_object();
/*  136 */         break;
/*      */       case 17:
/*  138 */         jj_consume_token(17);
/*  139 */         checkNameLength();
/*  140 */         break;
/*      */       case 18:
/*  142 */         jj_consume_token(18);
/*  143 */         break;
/*      */       case 16:
/*      */       default:
/*  145 */         this.jj_la1[2] = this.jj_gen;
/*  146 */         jj_consume_token(-1);
/*  147 */         throw new ParseException();
/*      */       }
/*      */       while (true)
/*      */       {
/*  151 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*      */         {
/*      */         case 1:
/*      */         case 2:
/*  155 */           break;
/*      */         default:
/*  157 */           this.jj_la1[3] = this.jj_gen;
/*  158 */           break;
/*      */         }
/*  160 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
/*      */         case 1:
/*  162 */           jj_consume_token(1);
/*  163 */           break;
/*      */         case 2:
/*  165 */           jj_consume_token(2);
/*      */         }
/*      */       }
/*  168 */       this.jj_la1[4] = this.jj_gen;
/*  169 */       jj_consume_token(-1);
/*  170 */       throw new ParseException();
/*      */     case 38:
/*  175 */       dictionary_object();
/*      */       while (true)
/*      */       {
/*  178 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*      */         {
/*      */         case 1:
/*      */         case 2:
/*  182 */           break;
/*      */         default:
/*  184 */           this.jj_la1[5] = this.jj_gen;
/*  185 */           break;
/*      */         }
/*  187 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
/*      */         case 1:
/*  189 */           jj_consume_token(1);
/*  190 */           break;
/*      */         case 2:
/*  192 */           jj_consume_token(2);
/*      */         }
/*      */       }
/*  195 */       this.jj_la1[6] = this.jj_gen;
/*  196 */       jj_consume_token(-1);
/*  197 */       throw new ParseException();
/*      */ 
/*  200 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*      */       {
/*      */       case 3:
/*      */       case 10:
/*      */         while (true) {
/*  205 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*      */           {
/*      */           case 3:
/*  208 */             break;
/*      */           default:
/*  210 */             this.jj_la1[7] = this.jj_gen;
/*  211 */             break;
/*      */           }
/*  213 */           jj_consume_token(3);
/*      */         }
/*  215 */         jj_consume_token(10);
/*  216 */         jj_consume_token(31);
/*  217 */         int i = this.token.image.indexOf(tokenImage[31].substring(1, tokenImage[31].length() - 1));
/*  218 */         if ((this.token.image.charAt(i - 1) != '\n') && (this.token.image.charAt(i - 1) != '\r')) {
/*  219 */           throw new PdfParseException("Expected EOL before \"endstream\"", "1.2.2");
/*      */         }
/*      */         while (true)
/*      */         {
/*  223 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*      */           {
/*      */           case 1:
/*      */           case 2:
/*  227 */             break;
/*      */           default:
/*  229 */             this.jj_la1[8] = this.jj_gen;
/*  230 */             break;
/*      */           }
/*  232 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
/*      */           case 1:
/*  234 */             jj_consume_token(1);
/*  235 */             break;
/*      */           case 2:
/*  237 */             jj_consume_token(2);
/*      */           }
/*      */         }
/*  240 */         this.jj_la1[9] = this.jj_gen;
/*  241 */         jj_consume_token(-1);
/*  242 */         throw new ParseException();
/*      */       }
/*      */ 
/*  247 */       this.jj_la1[10] = this.jj_gen;
/*      */ 
/*  250 */       break;
/*      */     case 16:
/*      */     case 19:
/*      */     case 20:
/*      */     case 21:
/*      */     case 22:
/*      */     case 23:
/*      */     case 24:
/*      */     case 25:
/*      */     case 26:
/*      */     case 27:
/*      */     case 28:
/*      */     case 29:
/*      */     case 30:
/*      */     case 31:
/*      */     case 32:
/*      */     case 33:
/*      */     case 34:
/*      */     case 35:
/*      */     case 36:
/*      */     case 37:
/*      */     default:
/*  252 */       this.jj_la1[11] = this.jj_gen;
/*  253 */       jj_consume_token(-1);
/*  254 */       throw new ParseException();
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void array_of_object() throws ParseException {
/*  259 */     int counter = 0;
/*  260 */     jj_consume_token(15);
/*      */     while (true)
/*      */     {
/*  263 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*      */       {
/*      */       case 1:
/*      */       case 2:
/*      */       case 3:
/*      */       case 11:
/*      */       case 12:
/*      */       case 13:
/*      */       case 14:
/*      */       case 15:
/*      */       case 17:
/*      */       case 18:
/*      */       case 19:
/*      */       case 38:
/*  277 */         break;
/*      */       case 4:
/*      */       case 5:
/*      */       case 6:
/*      */       case 7:
/*      */       case 8:
/*      */       case 9:
/*      */       case 10:
/*      */       case 16:
/*      */       case 20:
/*      */       case 21:
/*      */       case 22:
/*      */       case 23:
/*      */       case 24:
/*      */       case 25:
/*      */       case 26:
/*      */       case 27:
/*      */       case 28:
/*      */       case 29:
/*      */       case 30:
/*      */       case 31:
/*      */       case 32:
/*      */       case 33:
/*      */       case 34:
/*      */       case 35:
/*      */       case 36:
/*      */       case 37:
/*      */       default:
/*  279 */         this.jj_la1[12] = this.jj_gen;
/*  280 */         break;
/*      */       }
/*  282 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
/*      */       case 11:
/*  284 */         jj_consume_token(11);
/*  285 */         counter++;
/*  286 */         break;
/*      */       case 12:
/*  288 */         jj_consume_token(12);
/*  289 */         counter++; checkNumericLength();
/*  290 */         break;
/*      */       case 13:
/*  292 */         jj_consume_token(13);
/*  293 */         counter++; checkStringHexLength();
/*  294 */         break;
/*      */       case 15:
/*  296 */         array_of_object();
/*  297 */         counter++;
/*  298 */         break;
/*      */       case 38:
/*  300 */         dictionary_object();
/*  301 */         counter++;
/*  302 */         break;
/*      */       case 17:
/*  304 */         jj_consume_token(17);
/*  305 */         counter++; checkNameLength();
/*  306 */         break;
/*      */       case 18:
/*  308 */         jj_consume_token(18);
/*  309 */         counter++;
/*  310 */         break;
/*      */       case 19:
/*  312 */         jj_consume_token(19);
/*  313 */         counter++;
/*  314 */         break;
/*      */       case 14:
/*  316 */         start_literal();
/*  317 */         counter++;
/*  318 */         break;
/*      */       case 1:
/*  320 */         jj_consume_token(1);
/*  321 */         break;
/*      */       case 2:
/*  323 */         jj_consume_token(2);
/*  324 */         break;
/*      */       case 3:
/*  326 */         jj_consume_token(3);
/*      */       case 4:
/*      */       case 5:
/*      */       case 6:
/*      */       case 7:
/*      */       case 8:
/*      */       case 9:
/*      */       case 10:
/*      */       case 16:
/*      */       case 20:
/*      */       case 21:
/*      */       case 22:
/*      */       case 23:
/*      */       case 24:
/*      */       case 25:
/*      */       case 26:
/*      */       case 27:
/*      */       case 28:
/*      */       case 29:
/*      */       case 30:
/*      */       case 31:
/*      */       case 32:
/*      */       case 33:
/*      */       case 34:
/*      */       case 35:
/*      */       case 36:
/*  330 */       case 37: }  } this.jj_la1[13] = this.jj_gen;
/*  331 */     jj_consume_token(-1);
/*  332 */     throw new ParseException();
/*      */ 
/*  335 */     jj_consume_token(16);
/*  336 */     if (counter > 8191) throw new PdfParseException("Array too long : " + counter, "1.0.2"); 
/*      */   }
/*      */ 
/*      */   public final void start_literal() throws ParseException {
/*  340 */     jj_consume_token(14);
/*  341 */     literal();
/*      */   }
/*      */ 
/*      */   void literal() throws ParseException {
/*  345 */     Token currentToken = null;
/*  346 */     int nesting = 1;
/*  347 */     int literalLength = 0;
/*      */     while (true)
/*      */     {
/*  350 */       Token previous = getToken(0);
/*  351 */       currentToken = getToken(1);
/*  352 */       if (currentToken.kind == 0) {
/*  353 */         throw new ParseException("EOF reach before the end of the literal string.");
/*      */       }
/*  355 */       literalLength += currentToken.image.getBytes().length;
/*  356 */       if (currentToken.kind == 14) {
/*  357 */         jj_consume_token(14);
/*  358 */         if ((previous != null) && (previous.image.getBytes()[(previous.image.getBytes().length - 1)] != 92))
/*  359 */           nesting++;
/*      */       }
/*  361 */       else if (currentToken.kind == 29) {
/*  362 */         jj_consume_token(29);
/*  363 */         if ((previous != null) && (previous.image.getBytes()[(previous.image.getBytes().length - 1)] != 92))
/*  364 */           nesting++;
/*      */       }
/*  366 */       else if (currentToken.kind == 28) {
/*  367 */         if ((previous != null) && (previous.image.getBytes()[(previous.image.getBytes().length - 1)] != 92)) {
/*  368 */           nesting--;
/*      */         }
/*  370 */         jj_consume_token(28);
/*  371 */         if (nesting == 0) {
/*  372 */           this.token_source.curLexState = 0;
/*  373 */           break;
/*      */         }
/*      */       } else {
/*  376 */         currentToken = getNextToken();
/*      */       }
/*      */     }
/*  379 */     if (literalLength > 65535)
/*  380 */       throw new PdfParseException("Literal String too long", "1.0.4");
/*      */   }
/*      */ 
/*      */   void checkNameLength() throws ParseException, ParseException
/*      */   {
/*  385 */     if ((this.token != null) && (this.token.image.getBytes().length > 127))
/*  386 */       throw new PdfParseException("Object Name is too long : " + this.token.image.getBytes().length, "1.0.3");
/*      */   }
/*      */ 
/*      */   void checkMagicNumberLength()
/*      */     throws ParseException, ParseException
/*      */   {
/*  393 */     if ((this.token != null) && (this.token.image.getBytes().length < 4))
/*  394 */       throw new PdfParseException("Not enough bytes after the Header (at least 4 bytes should be present with a value bigger than 127) : " + this.token.image, "1.1");
/*      */   }
/*      */ 
/*      */   void checkStringHexLength()
/*      */     throws ParseException, ParseException
/*      */   {
/*  401 */     if ((this.token != null) && ((this.token.image.length() - 2) / 2 > 65535))
/*  402 */       throw new PdfParseException("Object String Hexa is toot long", "1.0.5");
/*      */   }
/*      */ 
/*      */   void checkNumericLength()
/*      */     throws ParseException, ParseException
/*      */   {
/*  409 */     if (this.token != null) {
/*  410 */       String num = this.token.image;
/*      */       try {
/*  412 */         long numAsLong = Long.parseLong(num);
/*  413 */         if ((numAsLong > 2147483647L) || (numAsLong < -2147483648L))
/*  414 */           throw new PdfParseException("Numeric is too long or too small: " + num, "1.0.6");
/*      */       }
/*      */       catch (NumberFormatException e)
/*      */       {
/*      */         try {
/*  419 */           Double real = Double.valueOf(Double.parseDouble(num));
/*  420 */           if ((real.doubleValue() > 32767.0D) || (real.doubleValue() < -32767.0D))
/*  421 */             throw new PdfParseException("Float is too long or too small: " + num, "1.0.6");
/*      */         }
/*      */         catch (NumberFormatException e2)
/*      */         {
/*  425 */           throw new PdfParseException("Numeric has invalid format " + num, "1.0.6");
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void dictionary_object()
/*      */     throws ParseException
/*      */   {
/*  434 */     int tokenNumber = 0;
/*  435 */     jj_consume_token(38);
/*      */     while (true)
/*      */     {
/*  438 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*      */       {
/*      */       case 1:
/*      */       case 2:
/*      */       case 3:
/*  443 */         break;
/*      */       default:
/*  445 */         this.jj_la1[14] = this.jj_gen;
/*  446 */         break;
/*      */       }
/*  448 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
/*      */       case 1:
/*  450 */         jj_consume_token(1);
/*  451 */         break;
/*      */       case 2:
/*  453 */         jj_consume_token(2);
/*  454 */         break;
/*      */       case 3:
/*  456 */         jj_consume_token(3);
/*      */       }
/*      */     }
/*  459 */     this.jj_la1[15] = this.jj_gen;
/*  460 */     jj_consume_token(-1);
/*  461 */     throw new ParseException();
/*      */ 
/*  466 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*      */     {
/*      */     case 17:
/*  469 */       break;
/*      */     default:
/*  471 */       this.jj_la1[16] = this.jj_gen;
/*  472 */       break;
/*      */     }
/*  474 */     jj_consume_token(17);
/*  475 */     tokenNumber++; checkNameLength();
/*      */     while (true)
/*      */     {
/*  478 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*      */       {
/*      */       case 1:
/*      */       case 2:
/*      */       case 3:
/*  483 */         break;
/*      */       default:
/*  485 */         this.jj_la1[17] = this.jj_gen;
/*  486 */         break;
/*      */       }
/*  488 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
/*      */       case 1:
/*  490 */         jj_consume_token(1);
/*  491 */         break;
/*      */       case 2:
/*  493 */         jj_consume_token(2);
/*  494 */         break;
/*      */       case 3:
/*  496 */         jj_consume_token(3);
/*      */       }
/*      */     }
/*  499 */     this.jj_la1[18] = this.jj_gen;
/*  500 */     jj_consume_token(-1);
/*  501 */     throw new ParseException();
/*      */ 
/*  504 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
/*      */     case 11:
/*  506 */       jj_consume_token(11);
/*  507 */       break;
/*      */     case 17:
/*  509 */       jj_consume_token(17);
/*  510 */       checkNameLength();
/*  511 */       break;
/*      */     case 12:
/*  513 */       jj_consume_token(12);
/*  514 */       checkNumericLength();
/*  515 */       break;
/*      */     case 13:
/*  517 */       jj_consume_token(13);
/*  518 */       checkStringHexLength();
/*  519 */       break;
/*      */     case 14:
/*  521 */       start_literal();
/*  522 */       break;
/*      */     case 15:
/*  524 */       array_of_object();
/*  525 */       break;
/*      */     case 38:
/*  527 */       dictionary_object();
/*  528 */       break;
/*      */     case 18:
/*  530 */       jj_consume_token(18);
/*  531 */       break;
/*      */     case 19:
/*  533 */       jj_consume_token(19);
/*  534 */       break;
/*      */     case 16:
/*      */     case 20:
/*      */     case 21:
/*      */     case 22:
/*      */     case 23:
/*      */     case 24:
/*      */     case 25:
/*      */     case 26:
/*      */     case 27:
/*      */     case 28:
/*      */     case 29:
/*      */     case 30:
/*      */     case 31:
/*      */     case 32:
/*      */     case 33:
/*      */     case 34:
/*      */     case 35:
/*      */     case 36:
/*      */     case 37:
/*      */     default:
/*  536 */       this.jj_la1[19] = this.jj_gen;
/*  537 */       jj_consume_token(-1);
/*  538 */       throw new ParseException();
/*      */     }
/*  540 */     tokenNumber++;
/*      */     while (true)
/*      */     {
/*  543 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*      */       {
/*      */       case 1:
/*      */       case 2:
/*      */       case 3:
/*  548 */         break;
/*      */       default:
/*  550 */         this.jj_la1[20] = this.jj_gen;
/*  551 */         break;
/*      */       }
/*  553 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
/*      */       case 1:
/*  555 */         jj_consume_token(1);
/*  556 */         break;
/*      */       case 2:
/*  558 */         jj_consume_token(2);
/*  559 */         break;
/*      */       case 3:
/*  561 */         jj_consume_token(3);
/*      */       }
/*      */     }
/*  564 */     this.jj_la1[21] = this.jj_gen;
/*  565 */     jj_consume_token(-1);
/*  566 */     throw new ParseException();
/*      */ 
/*  570 */     jj_consume_token(39);
/*  571 */     int entries = tokenNumber / 2;
/*  572 */     if (entries > 4095)
/*  573 */       throw new PdfParseException("Too Many Entries In Dictionary : " + entries, "1.0.1");
/*      */   }
/*      */ 
/*      */   public final void PDF_header() throws ParseException, HeaderParseException
/*      */   {
/*      */     try {
/*  579 */       jj_consume_token(4);
/*  580 */       jj_consume_token(5);
/*  581 */       this.pdfHeader = this.token.image;
/*  582 */       jj_consume_token(3);
/*  583 */       jj_consume_token(4);
/*  584 */       jj_consume_token(6);
/*  585 */       checkMagicNumberLength();
/*  586 */       jj_consume_token(3);
/*      */     } catch (ParseException e) {
/*  588 */       throw new HeaderParseException(e);
/*      */     } catch (TokenMgrError e) {
/*  590 */       throw new HeaderParseException(e.getMessage());
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void PDF_body() throws ParseException, BodyParseException {
/*      */     try {
/*  596 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*      */       {
/*      */       case 1:
/*      */       case 2:
/*      */         while (true) {
/*  601 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
/*      */           case 1:
/*  603 */             jj_consume_token(1);
/*  604 */             break;
/*      */           case 2:
/*  606 */             jj_consume_token(2);
/*  607 */             break;
/*      */           default:
/*  609 */             this.jj_la1[22] = this.jj_gen;
/*  610 */             jj_consume_token(-1);
/*  611 */             throw new ParseException();
/*      */           }
/*  613 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*      */           {
/*      */           case 1:
/*      */           case 2:
/*      */           }
/*      */         }
/*  619 */         this.jj_la1[23] = this.jj_gen;
/*      */ 
/*  623 */         jj_consume_token(3);
/*  624 */         break;
/*      */       default:
/*  626 */         this.jj_la1[24] = this.jj_gen;
/*      */       }
/*      */ 
/*      */       while (true)
/*      */       {
/*  631 */         indirect_object();
/*      */         while (true)
/*      */         {
/*  634 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*      */           {
/*      */           case 1:
/*      */           case 2:
/*  638 */             break;
/*      */           default:
/*  640 */             this.jj_la1[25] = this.jj_gen;
/*  641 */             break;
/*      */           }
/*  643 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
/*      */           case 1:
/*  645 */             jj_consume_token(1);
/*  646 */             break;
/*      */           case 2:
/*  648 */             jj_consume_token(2);
/*      */           }
/*      */         }
/*  651 */         this.jj_la1[26] = this.jj_gen;
/*  652 */         jj_consume_token(-1);
/*  653 */         throw new ParseException();
/*      */ 
/*  656 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
/*      */         case 3:
/*  658 */           jj_consume_token(3);
/*  659 */           break;
/*      */         default:
/*  661 */           this.jj_la1[27] = this.jj_gen;
/*      */         }
/*      */ 
/*  664 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*      */         {
/*      */         case 20:
/*      */         }
/*      */       }
/*  669 */       this.jj_la1[28] = this.jj_gen;
/*      */     }
/*      */     catch (ParseException e)
/*      */     {
/*  674 */       throw new BodyParseException(e);
/*      */     } catch (TokenMgrError e) {
/*  676 */       throw new BodyParseException(e.getMessage());
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void PDF_cross_ref_table() throws ParseException, CrossRefParseException {
/*      */     try {
/*  682 */       jj_consume_token(32);
/*  683 */       jj_consume_token(3);
/*      */       while (true)
/*      */       {
/*  686 */         jj_consume_token(34);
/*      */         while (true)
/*      */         {
/*  689 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*      */           {
/*      */           case 1:
/*  692 */             break;
/*      */           default:
/*  694 */             this.jj_la1[29] = this.jj_gen;
/*  695 */             break;
/*      */           }
/*  697 */           jj_consume_token(1);
/*      */         }
/*  699 */         jj_consume_token(3);
/*      */         while (true)
/*      */         {
/*  702 */           jj_consume_token(33);
/*      */           while (true)
/*      */           {
/*  705 */             switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*      */             {
/*      */             case 1:
/*  708 */               break;
/*      */             default:
/*  710 */               this.jj_la1[30] = this.jj_gen;
/*  711 */               break;
/*      */             }
/*  713 */             jj_consume_token(1);
/*      */           }
/*  715 */           jj_consume_token(3);
/*  716 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*      */           {
/*      */           case 33:
/*      */           }
/*      */         }
/*  721 */         this.jj_la1[31] = this.jj_gen;
/*      */ 
/*  725 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*      */         {
/*      */         case 34:
/*      */         }
/*      */       }
/*  730 */       this.jj_la1[32] = this.jj_gen;
/*      */     }
/*      */     catch (ParseException e)
/*      */     {
/*  735 */       throw new CrossRefParseException(e);
/*      */     } catch (TokenMgrError e) {
/*  737 */       throw new CrossRefParseException(e.getMessage());
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void PDF_trailer_dictionnary() throws ParseException, TrailerParseException {
/*      */     try {
/*  743 */       jj_consume_token(37);
/*  744 */       jj_consume_token(3);
/*  745 */       dictionary_object();
/*      */       while (true)
/*      */       {
/*  748 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*      */         {
/*      */         case 1:
/*  751 */           break;
/*      */         default:
/*  753 */           this.jj_la1[33] = this.jj_gen;
/*  754 */           break;
/*      */         }
/*  756 */         jj_consume_token(1);
/*      */       }
/*  758 */       jj_consume_token(3);
/*      */     } catch (ParseException e) {
/*  760 */       throw new TrailerParseException(e);
/*      */     } catch (TokenMgrError e) {
/*  762 */       throw new TrailerParseException(e.getMessage());
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void PDF_Trailer_XRefOffset() throws ParseException, TrailerParseException {
/*      */     try {
/*  768 */       jj_consume_token(40);
/*  769 */       jj_consume_token(3);
/*  770 */       jj_consume_token(41);
/*  771 */       jj_consume_token(3);
/*  772 */       jj_consume_token(42);
/*  773 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
/*      */       case 3:
/*  775 */         jj_consume_token(3);
/*  776 */         break;
/*      */       default:
/*  778 */         this.jj_la1[34] = this.jj_gen;
/*      */       }
/*      */     }
/*      */     catch (ParseException e) {
/*  782 */       throw new TrailerParseException(e);
/*      */     } catch (TokenMgrError e) {
/*  784 */       throw new TrailerParseException(e.getMessage());
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void PDF_linearized_modified() throws ParseException, PdfParseException {
/*  789 */     int foundXref = 0;
/*  790 */     int foundTrailer = 0;
/*      */     try
/*      */     {
/*      */       while (true) {
/*  794 */         PDF_body();
/*  795 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
/*      */         case 32:
/*  797 */           PDF_cross_ref_table();
/*  798 */           foundXref++;
/*  799 */           PDF_trailer_dictionnary();
/*  800 */           foundTrailer++;
/*  801 */           break;
/*      */         default:
/*  803 */           this.jj_la1[35] = this.jj_gen;
/*      */         }
/*      */ 
/*  806 */         PDF_Trailer_XRefOffset();
/*  807 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*      */         {
/*      */         case 1:
/*      */         case 2:
/*      */         case 20:
/*      */         }
/*      */       }
/*  814 */       this.jj_la1[36] = this.jj_gen;
/*      */ 
/*  818 */       jj_consume_token(0);
/*  819 */       boolean expectedXRefAndTrailer = this.pdfHeader.matches("PDF-1\\.[1-4]");
/*  820 */       if ((expectedXRefAndTrailer) && ((foundXref <= 0) || (foundTrailer <= 0)))
/*  821 */         throw new TrailerParseException("Missing Xref table or Trailer keyword in the given PDF.");
/*      */     }
/*      */     catch (PdfParseException e) {
/*  824 */       throw e;
/*      */     } catch (ParseException e) {
/*  826 */       throw new TrailerParseException(e);
/*      */     } catch (TokenMgrError e) {
/*  828 */       throw new TrailerParseException(e.getMessage());
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void PDF()
/*      */     throws ParseException, PdfParseException
/*      */   {
/*  836 */     PDF_header();
/*  837 */     PDF_linearized_modified();
/*      */   }
/*      */ 
/*      */   private static void jj_la1_init_0()
/*      */   {
/*  857 */     jj_la1_0 = new int[] { 14, 14, 456704, 6, 6, 6, 6, 8, 6, 6, 1032, 456704, 981006, 981006, 14, 14, 131072, 14, 14, 980992, 14, 14, 6, 6, 6, 6, 6, 8, 1048576, 2, 2, 0, 0, 2, 8, 0, 1048582 };
/*      */   }
/*      */   private static void jj_la1_init_1() {
/*  860 */     jj_la1_1 = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 64, 64, 0, 0, 0, 0, 0, 64, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 4, 0, 0, 1, 0 };
/*      */   }
/*      */ 
/*      */   public PDFParser(InputStream stream)
/*      */   {
/*  865 */     this(stream, null);
/*      */   }
/*      */   public PDFParser(InputStream stream, String encoding) {
/*      */     try {
/*  869 */       this.jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch (UnsupportedEncodingException e) { throw new RuntimeException(e); }
/*  870 */     this.token_source = new PDFParserTokenManager(this.jj_input_stream);
/*  871 */     this.token = new Token();
/*  872 */     this.jj_ntk = -1;
/*  873 */     this.jj_gen = 0;
/*  874 */     for (int i = 0; i < 37; i++) this.jj_la1[i] = -1;
/*      */   }
/*      */ 
/*      */   public void ReInit(InputStream stream)
/*      */   {
/*  879 */     ReInit(stream, null);
/*      */   }
/*      */   public void ReInit(InputStream stream, String encoding) {
/*      */     try {
/*  883 */       this.jj_input_stream.ReInit(stream, encoding, 1, 1); } catch (UnsupportedEncodingException e) { throw new RuntimeException(e); }
/*  884 */     this.token_source.ReInit(this.jj_input_stream);
/*  885 */     this.token = new Token();
/*  886 */     this.jj_ntk = -1;
/*  887 */     this.jj_gen = 0;
/*  888 */     for (int i = 0; i < 37; i++) this.jj_la1[i] = -1;
/*      */   }
/*      */ 
/*      */   public PDFParser(Reader stream)
/*      */   {
/*  893 */     this.jj_input_stream = new SimpleCharStream(stream, 1, 1);
/*  894 */     this.token_source = new PDFParserTokenManager(this.jj_input_stream);
/*  895 */     this.token = new Token();
/*  896 */     this.jj_ntk = -1;
/*  897 */     this.jj_gen = 0;
/*  898 */     for (int i = 0; i < 37; i++) this.jj_la1[i] = -1;
/*      */   }
/*      */ 
/*      */   public void ReInit(Reader stream)
/*      */   {
/*  903 */     this.jj_input_stream.ReInit(stream, 1, 1);
/*  904 */     this.token_source.ReInit(this.jj_input_stream);
/*  905 */     this.token = new Token();
/*  906 */     this.jj_ntk = -1;
/*  907 */     this.jj_gen = 0;
/*  908 */     for (int i = 0; i < 37; i++) this.jj_la1[i] = -1;
/*      */   }
/*      */ 
/*      */   public PDFParser(PDFParserTokenManager tm)
/*      */   {
/*  913 */     this.token_source = tm;
/*  914 */     this.token = new Token();
/*  915 */     this.jj_ntk = -1;
/*  916 */     this.jj_gen = 0;
/*  917 */     for (int i = 0; i < 37; i++) this.jj_la1[i] = -1;
/*      */   }
/*      */ 
/*      */   public void ReInit(PDFParserTokenManager tm)
/*      */   {
/*  922 */     this.token_source = tm;
/*  923 */     this.token = new Token();
/*  924 */     this.jj_ntk = -1;
/*  925 */     this.jj_gen = 0;
/*  926 */     for (int i = 0; i < 37; i++) this.jj_la1[i] = -1;
/*      */   }
/*      */ 
/*      */   private Token jj_consume_token(int kind)
/*      */     throws ParseException
/*      */   {
/*  931 */     Token oldToken;
/*  931 */     if ((oldToken = this.token).next != null) this.token = this.token.next; else
/*  932 */       this.token = (this.token.next = this.token_source.getNextToken());
/*  933 */     this.jj_ntk = -1;
/*  934 */     if (this.token.kind == kind) {
/*  935 */       this.jj_gen += 1;
/*  936 */       return this.token;
/*      */     }
/*  938 */     this.token = oldToken;
/*  939 */     this.jj_kind = kind;
/*  940 */     throw generateParseException();
/*      */   }
/*      */ 
/*      */   public final Token getNextToken()
/*      */   {
/*  946 */     if (this.token.next != null) this.token = this.token.next; else
/*  947 */       this.token = (this.token.next = this.token_source.getNextToken());
/*  948 */     this.jj_ntk = -1;
/*  949 */     this.jj_gen += 1;
/*  950 */     return this.token;
/*      */   }
/*      */ 
/*      */   public final Token getToken(int index)
/*      */   {
/*  955 */     Token t = this.token;
/*  956 */     for (int i = 0; i < index; i++) {
/*  957 */       if (t.next != null) t = t.next; else
/*  958 */         t = t.next = this.token_source.getNextToken();
/*      */     }
/*  960 */     return t;
/*      */   }
/*      */ 
/*      */   private int jj_ntk() {
/*  964 */     if ((this.jj_nt = this.token.next) == null) {
/*  965 */       return this.jj_ntk = (this.token.next = this.token_source.getNextToken()).kind;
/*      */     }
/*  967 */     return this.jj_ntk = this.jj_nt.kind;
/*      */   }
/*      */ 
/*      */   public ParseException generateParseException()
/*      */   {
/*  976 */     this.jj_expentries.clear();
/*  977 */     boolean[] la1tokens = new boolean[43];
/*  978 */     if (this.jj_kind >= 0) {
/*  979 */       la1tokens[this.jj_kind] = true;
/*  980 */       this.jj_kind = -1;
/*      */     }
/*  982 */     for (int i = 0; i < 37; i++) {
/*  983 */       if (this.jj_la1[i] == this.jj_gen) {
/*  984 */         for (int j = 0; j < 32; j++) {
/*  985 */           if ((jj_la1_0[i] & 1 << j) != 0) {
/*  986 */             la1tokens[j] = true;
/*      */           }
/*  988 */           if ((jj_la1_1[i] & 1 << j) != 0) {
/*  989 */             la1tokens[(32 + j)] = true;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  994 */     for (int i = 0; i < 43; i++) {
/*  995 */       if (la1tokens[i] != 0) {
/*  996 */         this.jj_expentry = new int[1];
/*  997 */         this.jj_expentry[0] = i;
/*  998 */         this.jj_expentries.add(this.jj_expentry);
/*      */       }
/*      */     }
/* 1001 */     int[][] exptokseq = new int[this.jj_expentries.size()][];
/* 1002 */     for (int i = 0; i < this.jj_expentries.size(); i++) {
/* 1003 */       exptokseq[i] = ((int[])this.jj_expentries.get(i));
/*      */     }
/* 1005 */     return new ParseException(this.token, exptokseq, tokenImage);
/*      */   }
/*      */ 
/*      */   public final void enable_tracing()
/*      */   {
/*      */   }
/*      */ 
/*      */   public final void disable_tracing()
/*      */   {
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/*  853 */     jj_la1_init_0();
/*  854 */     jj_la1_init_1();
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.javacc.PDFParser
 * JD-Core Version:    0.6.2
 */