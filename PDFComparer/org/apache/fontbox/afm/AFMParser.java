/*      */ package org.apache.fontbox.afm;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.util.StringTokenizer;
/*      */ import org.apache.fontbox.util.BoundingBox;
/*      */ 
/*      */ public class AFMParser
/*      */ {
/*      */   public static final String COMMENT = "Comment";
/*      */   public static final String START_FONT_METRICS = "StartFontMetrics";
/*      */   public static final String END_FONT_METRICS = "EndFontMetrics";
/*      */   public static final String FONT_NAME = "FontName";
/*      */   public static final String FULL_NAME = "FullName";
/*      */   public static final String FAMILY_NAME = "FamilyName";
/*      */   public static final String WEIGHT = "Weight";
/*      */   public static final String FONT_BBOX = "FontBBox";
/*      */   public static final String VERSION = "Version";
/*      */   public static final String NOTICE = "Notice";
/*      */   public static final String ENCODING_SCHEME = "EncodingScheme";
/*      */   public static final String MAPPING_SCHEME = "MappingScheme";
/*      */   public static final String ESC_CHAR = "EscChar";
/*      */   public static final String CHARACTER_SET = "CharacterSet";
/*      */   public static final String CHARACTERS = "Characters";
/*      */   public static final String IS_BASE_FONT = "IsBaseFont";
/*      */   public static final String V_VECTOR = "VVector";
/*      */   public static final String IS_FIXED_V = "IsFixedV";
/*      */   public static final String CAP_HEIGHT = "CapHeight";
/*      */   public static final String X_HEIGHT = "XHeight";
/*      */   public static final String ASCENDER = "Ascender";
/*      */   public static final String DESCENDER = "Descender";
/*      */   public static final String UNDERLINE_POSITION = "UnderlinePosition";
/*      */   public static final String UNDERLINE_THICKNESS = "UnderlineThickness";
/*      */   public static final String ITALIC_ANGLE = "ItalicAngle";
/*      */   public static final String CHAR_WIDTH = "CharWidth";
/*      */   public static final String IS_FIXED_PITCH = "IsFixedPitch";
/*      */   public static final String START_CHAR_METRICS = "StartCharMetrics";
/*      */   public static final String END_CHAR_METRICS = "EndCharMetrics";
/*      */   public static final String CHARMETRICS_C = "C";
/*      */   public static final String CHARMETRICS_CH = "CH";
/*      */   public static final String CHARMETRICS_WX = "WX";
/*      */   public static final String CHARMETRICS_W0X = "W0X";
/*      */   public static final String CHARMETRICS_W1X = "W1X";
/*      */   public static final String CHARMETRICS_WY = "WY";
/*      */   public static final String CHARMETRICS_W0Y = "W0Y";
/*      */   public static final String CHARMETRICS_W1Y = "W1Y";
/*      */   public static final String CHARMETRICS_W = "W";
/*      */   public static final String CHARMETRICS_W0 = "W0";
/*      */   public static final String CHARMETRICS_W1 = "W1";
/*      */   public static final String CHARMETRICS_VV = "VV";
/*      */   public static final String CHARMETRICS_N = "N";
/*      */   public static final String CHARMETRICS_B = "B";
/*      */   public static final String CHARMETRICS_L = "L";
/*      */   public static final String STD_HW = "StdHW";
/*      */   public static final String STD_VW = "StdVW";
/*      */   public static final String START_TRACK_KERN = "StartTrackKern";
/*      */   public static final String END_TRACK_KERN = "EndTrackKern";
/*      */   public static final String START_KERN_DATA = "StartKernData";
/*      */   public static final String END_KERN_DATA = "EndKernData";
/*      */   public static final String START_KERN_PAIRS = "StartKernPairs";
/*      */   public static final String END_KERN_PAIRS = "EndKernPairs";
/*      */   public static final String START_KERN_PAIRS0 = "StartKernPairs0";
/*      */   public static final String START_KERN_PAIRS1 = "StartKernPairs1";
/*      */   public static final String START_COMPOSITES = "StartComposites";
/*      */   public static final String END_COMPOSITES = "EndComposites";
/*      */   public static final String CC = "CC";
/*      */   public static final String PCC = "PCC";
/*      */   public static final String KERN_PAIR_KP = "KP";
/*      */   public static final String KERN_PAIR_KPH = "KPH";
/*      */   public static final String KERN_PAIR_KPX = "KPX";
/*      */   public static final String KERN_PAIR_KPY = "KPY";
/*      */   private static final int BITS_IN_HEX = 16;
/*      */   private InputStream input;
/*      */   private FontMetric result;
/*      */ 
/*      */   public static void main(String[] args)
/*      */     throws IOException
/*      */   {
/*  302 */     File afmDir = new File("Resources/afm");
/*  303 */     File[] files = afmDir.listFiles();
/*  304 */     for (int i = 0; i < files.length; i++)
/*      */     {
/*  306 */       if (files[i].getPath().toUpperCase().endsWith(".AFM"))
/*      */       {
/*  308 */         long start = System.currentTimeMillis();
/*  309 */         FileInputStream input = new FileInputStream(files[i]);
/*  310 */         AFMParser parser = new AFMParser(input);
/*  311 */         parser.parse();
/*  312 */         long stop = System.currentTimeMillis();
/*  313 */         System.out.println("Parsing:" + files[i].getPath() + " " + (stop - start));
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public AFMParser(InputStream in)
/*      */   {
/*  325 */     this.input = in;
/*      */   }
/*      */ 
/*      */   public void parse()
/*      */     throws IOException
/*      */   {
/*  336 */     this.result = parseFontMetric();
/*      */   }
/*      */ 
/*      */   public FontMetric getResult()
/*      */   {
/*  346 */     return this.result;
/*      */   }
/*      */ 
/*      */   private FontMetric parseFontMetric()
/*      */     throws IOException
/*      */   {
/*  358 */     FontMetric fontMetrics = new FontMetric();
/*  359 */     String startFontMetrics = readString();
/*  360 */     if (!"StartFontMetrics".equals(startFontMetrics))
/*      */     {
/*  362 */       throw new IOException("Error: The AFM file should start with StartFontMetrics and not '" + startFontMetrics + "'");
/*      */     }
/*      */ 
/*  365 */     fontMetrics.setAFMVersion(readFloat());
/*  366 */     String nextCommand = null;
/*  367 */     while (!"EndFontMetrics".equals(nextCommand = readString()))
/*      */     {
/*  369 */       if ("FontName".equals(nextCommand))
/*      */       {
/*  371 */         fontMetrics.setFontName(readLine());
/*      */       }
/*  373 */       else if ("FullName".equals(nextCommand))
/*      */       {
/*  375 */         fontMetrics.setFullName(readLine());
/*      */       }
/*  377 */       else if ("FamilyName".equals(nextCommand))
/*      */       {
/*  379 */         fontMetrics.setFamilyName(readLine());
/*      */       }
/*  381 */       else if ("Weight".equals(nextCommand))
/*      */       {
/*  383 */         fontMetrics.setWeight(readLine());
/*      */       }
/*  385 */       else if ("FontBBox".equals(nextCommand))
/*      */       {
/*  387 */         BoundingBox bBox = new BoundingBox();
/*  388 */         bBox.setLowerLeftX(readFloat());
/*  389 */         bBox.setLowerLeftY(readFloat());
/*  390 */         bBox.setUpperRightX(readFloat());
/*  391 */         bBox.setUpperRightY(readFloat());
/*  392 */         fontMetrics.setFontBBox(bBox);
/*      */       }
/*  394 */       else if ("Version".equals(nextCommand))
/*      */       {
/*  396 */         fontMetrics.setFontVersion(readLine());
/*      */       }
/*  398 */       else if ("Notice".equals(nextCommand))
/*      */       {
/*  400 */         fontMetrics.setNotice(readLine());
/*      */       }
/*  402 */       else if ("EncodingScheme".equals(nextCommand))
/*      */       {
/*  404 */         fontMetrics.setEncodingScheme(readLine());
/*      */       }
/*  406 */       else if ("MappingScheme".equals(nextCommand))
/*      */       {
/*  408 */         fontMetrics.setMappingScheme(readInt());
/*      */       }
/*  410 */       else if ("EscChar".equals(nextCommand))
/*      */       {
/*  412 */         fontMetrics.setEscChar(readInt());
/*      */       }
/*  414 */       else if ("CharacterSet".equals(nextCommand))
/*      */       {
/*  416 */         fontMetrics.setCharacterSet(readLine());
/*      */       }
/*  418 */       else if ("Characters".equals(nextCommand))
/*      */       {
/*  420 */         fontMetrics.setCharacters(readInt());
/*      */       }
/*  422 */       else if ("IsBaseFont".equals(nextCommand))
/*      */       {
/*  424 */         fontMetrics.setIsBaseFont(readBoolean());
/*      */       }
/*  426 */       else if ("VVector".equals(nextCommand))
/*      */       {
/*  428 */         float[] vector = new float[2];
/*  429 */         vector[0] = readFloat();
/*  430 */         vector[1] = readFloat();
/*  431 */         fontMetrics.setVVector(vector);
/*      */       }
/*  433 */       else if ("IsFixedV".equals(nextCommand))
/*      */       {
/*  435 */         fontMetrics.setIsFixedV(readBoolean());
/*      */       }
/*  437 */       else if ("CapHeight".equals(nextCommand))
/*      */       {
/*  439 */         fontMetrics.setCapHeight(readFloat());
/*      */       }
/*  441 */       else if ("XHeight".equals(nextCommand))
/*      */       {
/*  443 */         fontMetrics.setXHeight(readFloat());
/*      */       }
/*  445 */       else if ("Ascender".equals(nextCommand))
/*      */       {
/*  447 */         fontMetrics.setAscender(readFloat());
/*      */       }
/*  449 */       else if ("Descender".equals(nextCommand))
/*      */       {
/*  451 */         fontMetrics.setDescender(readFloat());
/*      */       }
/*  453 */       else if ("StdHW".equals(nextCommand))
/*      */       {
/*  455 */         fontMetrics.setStandardHorizontalWidth(readFloat());
/*      */       }
/*  457 */       else if ("StdVW".equals(nextCommand))
/*      */       {
/*  459 */         fontMetrics.setStandardVerticalWidth(readFloat());
/*      */       }
/*  461 */       else if ("Comment".equals(nextCommand))
/*      */       {
/*  463 */         fontMetrics.addComment(readLine());
/*      */       }
/*  465 */       else if ("UnderlinePosition".equals(nextCommand))
/*      */       {
/*  467 */         fontMetrics.setUnderlinePosition(readFloat());
/*      */       }
/*  469 */       else if ("UnderlineThickness".equals(nextCommand))
/*      */       {
/*  471 */         fontMetrics.setUnderlineThickness(readFloat());
/*      */       }
/*  473 */       else if ("ItalicAngle".equals(nextCommand))
/*      */       {
/*  475 */         fontMetrics.setItalicAngle(readFloat());
/*      */       }
/*  477 */       else if ("CharWidth".equals(nextCommand))
/*      */       {
/*  479 */         float[] widths = new float[2];
/*  480 */         widths[0] = readFloat();
/*  481 */         widths[1] = readFloat();
/*  482 */         fontMetrics.setCharWidth(widths);
/*      */       }
/*  484 */       else if ("IsFixedPitch".equals(nextCommand))
/*      */       {
/*  486 */         fontMetrics.setFixedPitch(readBoolean());
/*      */       }
/*  488 */       else if ("StartCharMetrics".equals(nextCommand))
/*      */       {
/*  490 */         int count = readInt();
/*  491 */         for (int i = 0; i < count; i++)
/*      */         {
/*  493 */           CharMetric charMetric = parseCharMetric();
/*  494 */           fontMetrics.addCharMetric(charMetric);
/*      */         }
/*  496 */         String end = readString();
/*  497 */         if (!end.equals("EndCharMetrics"))
/*      */         {
/*  499 */           throw new IOException("Error: Expected 'EndCharMetrics' actual '" + end + "'");
/*      */         }
/*      */ 
/*      */       }
/*  503 */       else if ("StartComposites".equals(nextCommand))
/*      */       {
/*  505 */         int count = readInt();
/*  506 */         for (int i = 0; i < count; i++)
/*      */         {
/*  508 */           Composite part = parseComposite();
/*  509 */           fontMetrics.addComposite(part);
/*      */         }
/*  511 */         String end = readString();
/*  512 */         if (!end.equals("EndComposites"))
/*      */         {
/*  514 */           throw new IOException("Error: Expected 'EndComposites' actual '" + end + "'");
/*      */         }
/*      */ 
/*      */       }
/*  518 */       else if ("StartKernData".equals(nextCommand))
/*      */       {
/*  520 */         parseKernData(fontMetrics);
/*      */       }
/*      */       else
/*      */       {
/*  524 */         throw new IOException("Unknown AFM key '" + nextCommand + "'");
/*      */       }
/*      */     }
/*  527 */     return fontMetrics;
/*      */   }
/*      */ 
/*      */   private void parseKernData(FontMetric fontMetrics)
/*      */     throws IOException
/*      */   {
/*  539 */     String nextCommand = null;
/*  540 */     while (!(nextCommand = readString()).equals("EndKernData"))
/*      */     {
/*  542 */       if ("StartTrackKern".equals(nextCommand))
/*      */       {
/*  544 */         int count = readInt();
/*  545 */         for (int i = 0; i < count; i++)
/*      */         {
/*  547 */           TrackKern kern = new TrackKern();
/*  548 */           kern.setDegree(readInt());
/*  549 */           kern.setMinPointSize(readFloat());
/*  550 */           kern.setMinKern(readFloat());
/*  551 */           kern.setMaxPointSize(readFloat());
/*  552 */           kern.setMaxKern(readFloat());
/*  553 */           fontMetrics.addTrackKern(kern);
/*      */         }
/*  555 */         String end = readString();
/*  556 */         if (!end.equals("EndTrackKern"))
/*      */         {
/*  558 */           throw new IOException("Error: Expected 'EndTrackKern' actual '" + end + "'");
/*      */         }
/*      */ 
/*      */       }
/*  562 */       else if ("StartKernPairs".equals(nextCommand))
/*      */       {
/*  564 */         int count = readInt();
/*  565 */         for (int i = 0; i < count; i++)
/*      */         {
/*  567 */           KernPair pair = parseKernPair();
/*  568 */           fontMetrics.addKernPair(pair);
/*      */         }
/*  570 */         String end = readString();
/*  571 */         if (!end.equals("EndKernPairs"))
/*      */         {
/*  573 */           throw new IOException("Error: Expected 'EndKernPairs' actual '" + end + "'");
/*      */         }
/*      */ 
/*      */       }
/*  577 */       else if ("StartKernPairs0".equals(nextCommand))
/*      */       {
/*  579 */         int count = readInt();
/*  580 */         for (int i = 0; i < count; i++)
/*      */         {
/*  582 */           KernPair pair = parseKernPair();
/*  583 */           fontMetrics.addKernPair0(pair);
/*      */         }
/*  585 */         String end = readString();
/*  586 */         if (!end.equals("EndKernPairs"))
/*      */         {
/*  588 */           throw new IOException("Error: Expected 'EndKernPairs' actual '" + end + "'");
/*      */         }
/*      */ 
/*      */       }
/*  592 */       else if ("StartKernPairs1".equals(nextCommand))
/*      */       {
/*  594 */         int count = readInt();
/*  595 */         for (int i = 0; i < count; i++)
/*      */         {
/*  597 */           KernPair pair = parseKernPair();
/*  598 */           fontMetrics.addKernPair1(pair);
/*      */         }
/*  600 */         String end = readString();
/*  601 */         if (!end.equals("EndKernPairs"))
/*      */         {
/*  603 */           throw new IOException("Error: Expected 'EndKernPairs' actual '" + end + "'");
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  609 */         throw new IOException("Unknown kerning data type '" + nextCommand + "'");
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private KernPair parseKernPair()
/*      */     throws IOException
/*      */   {
/*  623 */     KernPair kernPair = new KernPair();
/*  624 */     String cmd = readString();
/*  625 */     if ("KP".equals(cmd))
/*      */     {
/*  627 */       String first = readString();
/*  628 */       String second = readString();
/*  629 */       float x = readFloat();
/*  630 */       float y = readFloat();
/*  631 */       kernPair.setFirstKernCharacter(first);
/*  632 */       kernPair.setSecondKernCharacter(second);
/*  633 */       kernPair.setX(x);
/*  634 */       kernPair.setY(y);
/*      */     }
/*  636 */     else if ("KPH".equals(cmd))
/*      */     {
/*  638 */       String first = hexToString(readString());
/*  639 */       String second = hexToString(readString());
/*  640 */       float x = readFloat();
/*  641 */       float y = readFloat();
/*  642 */       kernPair.setFirstKernCharacter(first);
/*  643 */       kernPair.setSecondKernCharacter(second);
/*  644 */       kernPair.setX(x);
/*  645 */       kernPair.setY(y);
/*      */     }
/*  647 */     else if ("KPX".equals(cmd))
/*      */     {
/*  649 */       String first = readString();
/*  650 */       String second = readString();
/*  651 */       float x = readFloat();
/*  652 */       kernPair.setFirstKernCharacter(first);
/*  653 */       kernPair.setSecondKernCharacter(second);
/*  654 */       kernPair.setX(x);
/*  655 */       kernPair.setY(0.0F);
/*      */     }
/*  657 */     else if ("KPY".equals(cmd))
/*      */     {
/*  659 */       String first = readString();
/*  660 */       String second = readString();
/*  661 */       float y = readFloat();
/*  662 */       kernPair.setFirstKernCharacter(first);
/*  663 */       kernPair.setSecondKernCharacter(second);
/*  664 */       kernPair.setX(0.0F);
/*  665 */       kernPair.setY(y);
/*      */     }
/*      */     else
/*      */     {
/*  669 */       throw new IOException("Error expected kern pair command actual='" + cmd + "'");
/*      */     }
/*  671 */     return kernPair;
/*      */   }
/*      */ 
/*      */   private String hexToString(String hexString)
/*      */     throws IOException
/*      */   {
/*  685 */     if (hexString.length() < 2)
/*      */     {
/*  687 */       throw new IOException("Error: Expected hex string of length >= 2 not='" + hexString);
/*      */     }
/*  689 */     if ((hexString.charAt(0) != '<') || (hexString.charAt(hexString.length() - 1) != '>'))
/*      */     {
/*  692 */       throw new IOException("String should be enclosed by angle brackets '" + hexString + "'");
/*      */     }
/*  694 */     hexString = hexString.substring(1, hexString.length() - 1);
/*  695 */     byte[] data = new byte[hexString.length() / 2];
/*  696 */     for (int i = 0; i < hexString.length(); i += 2)
/*      */     {
/*  698 */       String hex = "" + hexString.charAt(i) + hexString.charAt(i + 1);
/*      */       try
/*      */       {
/*  701 */         data[(i / 2)] = ((byte)Integer.parseInt(hex, 16));
/*      */       }
/*      */       catch (NumberFormatException e)
/*      */       {
/*  705 */         throw new IOException("Error parsing AFM file:" + e);
/*      */       }
/*      */     }
/*  708 */     return new String(data, "ISO-8859-1");
/*      */   }
/*      */ 
/*      */   private Composite parseComposite()
/*      */     throws IOException
/*      */   {
/*  720 */     Composite composite = new Composite();
/*  721 */     String partData = readLine();
/*  722 */     StringTokenizer tokenizer = new StringTokenizer(partData, " ;");
/*      */ 
/*  725 */     String cc = tokenizer.nextToken();
/*  726 */     if (!cc.equals("CC"))
/*      */     {
/*  728 */       throw new IOException("Expected 'CC' actual='" + cc + "'");
/*      */     }
/*  730 */     String name = tokenizer.nextToken();
/*  731 */     composite.setName(name);
/*      */     int partCount;
/*      */     try {
/*  736 */       partCount = Integer.parseInt(tokenizer.nextToken());
/*      */     }
/*      */     catch (NumberFormatException e)
/*      */     {
/*  740 */       throw new IOException("Error parsing AFM document:" + e);
/*      */     }
/*  742 */     for (int i = 0; i < partCount; i++)
/*      */     {
/*  744 */       CompositePart part = new CompositePart();
/*  745 */       String pcc = tokenizer.nextToken();
/*  746 */       if (!pcc.equals("PCC"))
/*      */       {
/*  748 */         throw new IOException("Expected 'PCC' actual='" + pcc + "'");
/*      */       }
/*  750 */       String partName = tokenizer.nextToken();
/*      */       try
/*      */       {
/*  753 */         int x = Integer.parseInt(tokenizer.nextToken());
/*  754 */         int y = Integer.parseInt(tokenizer.nextToken());
/*      */ 
/*  756 */         part.setName(partName);
/*  757 */         part.setXDisplacement(x);
/*  758 */         part.setYDisplacement(y);
/*  759 */         composite.addPart(part);
/*      */       }
/*      */       catch (NumberFormatException e)
/*      */       {
/*  763 */         throw new IOException("Error parsing AFM document:" + e);
/*      */       }
/*      */     }
/*  766 */     return composite;
/*      */   }
/*      */ 
/*      */   private CharMetric parseCharMetric()
/*      */     throws IOException
/*      */   {
/*  778 */     CharMetric charMetric = new CharMetric();
/*  779 */     String metrics = readLine();
/*  780 */     StringTokenizer metricsTokenizer = new StringTokenizer(metrics);
/*      */     try
/*      */     {
/*  783 */       while (metricsTokenizer.hasMoreTokens())
/*      */       {
/*  785 */         String nextCommand = metricsTokenizer.nextToken();
/*  786 */         if (nextCommand.equals("C"))
/*      */         {
/*  788 */           String charCode = metricsTokenizer.nextToken();
/*  789 */           charMetric.setCharacterCode(Integer.parseInt(charCode));
/*  790 */           verifySemicolon(metricsTokenizer);
/*      */         }
/*  792 */         else if (nextCommand.equals("CH"))
/*      */         {
/*  796 */           String charCode = metricsTokenizer.nextToken();
/*  797 */           charMetric.setCharacterCode(Integer.parseInt(charCode, 16));
/*  798 */           verifySemicolon(metricsTokenizer);
/*      */         }
/*  800 */         else if (nextCommand.equals("WX"))
/*      */         {
/*  802 */           String wx = metricsTokenizer.nextToken();
/*  803 */           charMetric.setWx(Float.parseFloat(wx));
/*  804 */           verifySemicolon(metricsTokenizer);
/*      */         }
/*  806 */         else if (nextCommand.equals("W0X"))
/*      */         {
/*  808 */           String w0x = metricsTokenizer.nextToken();
/*  809 */           charMetric.setW0x(Float.parseFloat(w0x));
/*  810 */           verifySemicolon(metricsTokenizer);
/*      */         }
/*  812 */         else if (nextCommand.equals("W1X"))
/*      */         {
/*  814 */           String w1x = metricsTokenizer.nextToken();
/*  815 */           charMetric.setW0x(Float.parseFloat(w1x));
/*  816 */           verifySemicolon(metricsTokenizer);
/*      */         }
/*  818 */         else if (nextCommand.equals("WY"))
/*      */         {
/*  820 */           String wy = metricsTokenizer.nextToken();
/*  821 */           charMetric.setWy(Float.parseFloat(wy));
/*  822 */           verifySemicolon(metricsTokenizer);
/*      */         }
/*  824 */         else if (nextCommand.equals("W0Y"))
/*      */         {
/*  826 */           String w0y = metricsTokenizer.nextToken();
/*  827 */           charMetric.setW0y(Float.parseFloat(w0y));
/*  828 */           verifySemicolon(metricsTokenizer);
/*      */         }
/*  830 */         else if (nextCommand.equals("W1Y"))
/*      */         {
/*  832 */           String w1y = metricsTokenizer.nextToken();
/*  833 */           charMetric.setW0y(Float.parseFloat(w1y));
/*  834 */           verifySemicolon(metricsTokenizer);
/*      */         }
/*  836 */         else if (nextCommand.equals("W"))
/*      */         {
/*  838 */           String w0 = metricsTokenizer.nextToken();
/*  839 */           String w1 = metricsTokenizer.nextToken();
/*  840 */           float[] w = new float[2];
/*  841 */           w[0] = Float.parseFloat(w0);
/*  842 */           w[1] = Float.parseFloat(w1);
/*  843 */           charMetric.setW(w);
/*  844 */           verifySemicolon(metricsTokenizer);
/*      */         }
/*  846 */         else if (nextCommand.equals("W0"))
/*      */         {
/*  848 */           String w00 = metricsTokenizer.nextToken();
/*  849 */           String w01 = metricsTokenizer.nextToken();
/*  850 */           float[] w0 = new float[2];
/*  851 */           w0[0] = Float.parseFloat(w00);
/*  852 */           w0[1] = Float.parseFloat(w01);
/*  853 */           charMetric.setW0(w0);
/*  854 */           verifySemicolon(metricsTokenizer);
/*      */         }
/*  856 */         else if (nextCommand.equals("W1"))
/*      */         {
/*  858 */           String w10 = metricsTokenizer.nextToken();
/*  859 */           String w11 = metricsTokenizer.nextToken();
/*  860 */           float[] w1 = new float[2];
/*  861 */           w1[0] = Float.parseFloat(w10);
/*  862 */           w1[1] = Float.parseFloat(w11);
/*  863 */           charMetric.setW1(w1);
/*  864 */           verifySemicolon(metricsTokenizer);
/*      */         }
/*  866 */         else if (nextCommand.equals("VV"))
/*      */         {
/*  868 */           String vv0 = metricsTokenizer.nextToken();
/*  869 */           String vv1 = metricsTokenizer.nextToken();
/*  870 */           float[] vv = new float[2];
/*  871 */           vv[0] = Float.parseFloat(vv0);
/*  872 */           vv[1] = Float.parseFloat(vv1);
/*  873 */           charMetric.setVv(vv);
/*  874 */           verifySemicolon(metricsTokenizer);
/*      */         }
/*  876 */         else if (nextCommand.equals("N"))
/*      */         {
/*  878 */           String name = metricsTokenizer.nextToken();
/*  879 */           charMetric.setName(name);
/*  880 */           verifySemicolon(metricsTokenizer);
/*      */         }
/*  882 */         else if (nextCommand.equals("B"))
/*      */         {
/*  884 */           String llx = metricsTokenizer.nextToken();
/*  885 */           String lly = metricsTokenizer.nextToken();
/*  886 */           String urx = metricsTokenizer.nextToken();
/*  887 */           String ury = metricsTokenizer.nextToken();
/*  888 */           BoundingBox box = new BoundingBox();
/*  889 */           box.setLowerLeftX(Float.parseFloat(llx));
/*  890 */           box.setLowerLeftY(Float.parseFloat(lly));
/*  891 */           box.setUpperRightX(Float.parseFloat(urx));
/*  892 */           box.setUpperRightY(Float.parseFloat(ury));
/*  893 */           charMetric.setBoundingBox(box);
/*  894 */           verifySemicolon(metricsTokenizer);
/*      */         }
/*  896 */         else if (nextCommand.equals("L"))
/*      */         {
/*  898 */           String successor = metricsTokenizer.nextToken();
/*  899 */           String ligature = metricsTokenizer.nextToken();
/*  900 */           Ligature lig = new Ligature();
/*  901 */           lig.setSuccessor(successor);
/*  902 */           lig.setLigature(ligature);
/*  903 */           charMetric.addLigature(lig);
/*  904 */           verifySemicolon(metricsTokenizer);
/*      */         }
/*      */         else
/*      */         {
/*  908 */           throw new IOException("Unknown CharMetrics command '" + nextCommand + "'");
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (NumberFormatException e)
/*      */     {
/*  914 */       throw new IOException("Error: Corrupt AFM document:" + e);
/*      */     }
/*  916 */     return charMetric;
/*      */   }
/*      */ 
/*      */   private void verifySemicolon(StringTokenizer tokenizer)
/*      */     throws IOException
/*      */   {
/*  928 */     if (tokenizer.hasMoreTokens())
/*      */     {
/*  930 */       String semicolon = tokenizer.nextToken();
/*  931 */       if (!semicolon.equals(";"))
/*      */       {
/*  933 */         throw new IOException("Error: Expected semicolon in stream actual='" + semicolon + "'");
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  939 */       throw new IOException("CharMetrics is missing a semicolon after a command");
/*      */     }
/*      */   }
/*      */ 
/*      */   private boolean readBoolean()
/*      */     throws IOException
/*      */   {
/*  950 */     String theBoolean = readString();
/*  951 */     return Boolean.valueOf(theBoolean).booleanValue();
/*      */   }
/*      */ 
/*      */   private int readInt()
/*      */     throws IOException
/*      */   {
/*  961 */     String theInt = readString();
/*      */     try
/*      */     {
/*  964 */       return Integer.parseInt(theInt);
/*      */     }
/*      */     catch (NumberFormatException e)
/*      */     {
/*  968 */       throw new IOException("Error parsing AFM document:" + e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private float readFloat()
/*      */     throws IOException
/*      */   {
/*  979 */     String theFloat = readString();
/*  980 */     return Float.parseFloat(theFloat);
/*      */   }
/*      */ 
/*      */   private String readLine()
/*      */     throws IOException
/*      */   {
/*  991 */     StringBuffer buf = new StringBuffer();
/*  992 */     int nextByte = this.input.read();
/*  993 */     while (isWhitespace(nextByte))
/*      */     {
/*  995 */       nextByte = this.input.read();
/*      */     }
/*      */ 
/*  998 */     buf.append((char)nextByte);
/*      */ 
/* 1001 */     while (!isEOL(nextByte = this.input.read()))
/*      */     {
/* 1003 */       buf.append((char)nextByte);
/*      */     }
/* 1005 */     return buf.toString();
/*      */   }
/*      */ 
/*      */   private String readString()
/*      */     throws IOException
/*      */   {
/* 1018 */     StringBuffer buf = new StringBuffer();
/* 1019 */     int nextByte = this.input.read();
/* 1020 */     while (isWhitespace(nextByte))
/*      */     {
/* 1022 */       nextByte = this.input.read();
/*      */     }
/*      */ 
/* 1025 */     buf.append((char)nextByte);
/*      */ 
/* 1028 */     while (!isWhitespace(nextByte = this.input.read()))
/*      */     {
/* 1030 */       buf.append((char)nextByte);
/*      */     }
/* 1032 */     return buf.toString();
/*      */   }
/*      */ 
/*      */   private boolean isEOL(int character)
/*      */   {
/* 1044 */     return (character == 13) || (character == 10);
/*      */   }
/*      */ 
/*      */   private boolean isWhitespace(int character)
/*      */   {
/* 1057 */     return (character == 32) || (character == 9) || (character == 13) || (character == 10);
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.afm.AFMParser
 * JD-Core Version:    0.6.2
 */