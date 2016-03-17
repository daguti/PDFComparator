/*     */ package org.apache.pdfbox.encoding;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ 
/*     */ public class StandardEncoding extends Encoding
/*     */ {
/*  36 */   public static final StandardEncoding INSTANCE = new StandardEncoding();
/*     */ 
/*     */   public StandardEncoding()
/*     */   {
/*  43 */     addCharacterEncoding(65, "A");
/*  44 */     addCharacterEncoding(225, "AE");
/*  45 */     addCharacterEncoding(66, "B");
/*  46 */     addCharacterEncoding(67, "C");
/*  47 */     addCharacterEncoding(68, "D");
/*  48 */     addCharacterEncoding(69, "E");
/*  49 */     addCharacterEncoding(70, "F");
/*  50 */     addCharacterEncoding(71, "G");
/*  51 */     addCharacterEncoding(72, "H");
/*  52 */     addCharacterEncoding(73, "I");
/*  53 */     addCharacterEncoding(74, "J");
/*  54 */     addCharacterEncoding(75, "K");
/*  55 */     addCharacterEncoding(76, "L");
/*  56 */     addCharacterEncoding(232, "Lslash");
/*  57 */     addCharacterEncoding(77, "M");
/*  58 */     addCharacterEncoding(78, "N");
/*  59 */     addCharacterEncoding(79, "O");
/*  60 */     addCharacterEncoding(234, "OE");
/*  61 */     addCharacterEncoding(233, "Oslash");
/*  62 */     addCharacterEncoding(80, "P");
/*  63 */     addCharacterEncoding(81, "Q");
/*  64 */     addCharacterEncoding(82, "R");
/*  65 */     addCharacterEncoding(83, "S");
/*  66 */     addCharacterEncoding(84, "T");
/*  67 */     addCharacterEncoding(85, "U");
/*  68 */     addCharacterEncoding(86, "V");
/*  69 */     addCharacterEncoding(87, "W");
/*  70 */     addCharacterEncoding(88, "X");
/*  71 */     addCharacterEncoding(89, "Y");
/*  72 */     addCharacterEncoding(90, "Z");
/*  73 */     addCharacterEncoding(97, "a");
/*  74 */     addCharacterEncoding(194, "acute");
/*  75 */     addCharacterEncoding(241, "ae");
/*  76 */     addCharacterEncoding(38, "ampersand");
/*  77 */     addCharacterEncoding(94, "asciicircum");
/*  78 */     addCharacterEncoding(126, "asciitilde");
/*  79 */     addCharacterEncoding(42, "asterisk");
/*  80 */     addCharacterEncoding(64, "at");
/*  81 */     addCharacterEncoding(98, "b");
/*  82 */     addCharacterEncoding(92, "backslash");
/*  83 */     addCharacterEncoding(124, "bar");
/*  84 */     addCharacterEncoding(123, "braceleft");
/*  85 */     addCharacterEncoding(125, "braceright");
/*  86 */     addCharacterEncoding(91, "bracketleft");
/*  87 */     addCharacterEncoding(93, "bracketright");
/*  88 */     addCharacterEncoding(198, "breve");
/*  89 */     addCharacterEncoding(183, "bullet");
/*  90 */     addCharacterEncoding(99, "c");
/*  91 */     addCharacterEncoding(207, "caron");
/*  92 */     addCharacterEncoding(203, "cedilla");
/*  93 */     addCharacterEncoding(162, "cent");
/*  94 */     addCharacterEncoding(195, "circumflex");
/*  95 */     addCharacterEncoding(58, "colon");
/*  96 */     addCharacterEncoding(44, "comma");
/*  97 */     addCharacterEncoding(168, "currency");
/*  98 */     addCharacterEncoding(100, "d");
/*  99 */     addCharacterEncoding(178, "dagger");
/* 100 */     addCharacterEncoding(179, "daggerdbl");
/* 101 */     addCharacterEncoding(200, "dieresis");
/* 102 */     addCharacterEncoding(36, "dollar");
/* 103 */     addCharacterEncoding(199, "dotaccent");
/* 104 */     addCharacterEncoding(245, "dotlessi");
/* 105 */     addCharacterEncoding(101, "e");
/* 106 */     addCharacterEncoding(56, "eight");
/* 107 */     addCharacterEncoding(188, "ellipsis");
/* 108 */     addCharacterEncoding(208, "emdash");
/* 109 */     addCharacterEncoding(177, "endash");
/* 110 */     addCharacterEncoding(61, "equal");
/* 111 */     addCharacterEncoding(33, "exclam");
/* 112 */     addCharacterEncoding(161, "exclamdown");
/* 113 */     addCharacterEncoding(102, "f");
/* 114 */     addCharacterEncoding(174, "fi");
/* 115 */     addCharacterEncoding(53, "five");
/* 116 */     addCharacterEncoding(175, "fl");
/* 117 */     addCharacterEncoding(166, "florin");
/* 118 */     addCharacterEncoding(52, "four");
/* 119 */     addCharacterEncoding(164, "fraction");
/* 120 */     addCharacterEncoding(103, "g");
/* 121 */     addCharacterEncoding(251, "germandbls");
/* 122 */     addCharacterEncoding(193, "grave");
/* 123 */     addCharacterEncoding(62, "greater");
/* 124 */     addCharacterEncoding(171, "guillemotleft");
/* 125 */     addCharacterEncoding(187, "guillemotright");
/* 126 */     addCharacterEncoding(172, "guilsinglleft");
/* 127 */     addCharacterEncoding(173, "guilsinglright");
/* 128 */     addCharacterEncoding(104, "h");
/* 129 */     addCharacterEncoding(205, "hungarumlaut");
/* 130 */     addCharacterEncoding(45, "hyphen");
/* 131 */     addCharacterEncoding(105, "i");
/* 132 */     addCharacterEncoding(106, "j");
/* 133 */     addCharacterEncoding(107, "k");
/* 134 */     addCharacterEncoding(108, "l");
/* 135 */     addCharacterEncoding(60, "less");
/* 136 */     addCharacterEncoding(248, "lslash");
/* 137 */     addCharacterEncoding(109, "m");
/* 138 */     addCharacterEncoding(197, "macron");
/* 139 */     addCharacterEncoding(110, "n");
/* 140 */     addCharacterEncoding(57, "nine");
/* 141 */     addCharacterEncoding(35, "numbersign");
/* 142 */     addCharacterEncoding(111, "o");
/* 143 */     addCharacterEncoding(250, "oe");
/* 144 */     addCharacterEncoding(206, "ogonek");
/* 145 */     addCharacterEncoding(49, "one");
/* 146 */     addCharacterEncoding(227, "ordfeminine");
/* 147 */     addCharacterEncoding(235, "ordmasculine");
/* 148 */     addCharacterEncoding(249, "oslash");
/* 149 */     addCharacterEncoding(112, "p");
/* 150 */     addCharacterEncoding(182, "paragraph");
/* 151 */     addCharacterEncoding(40, "parenleft");
/* 152 */     addCharacterEncoding(41, "parenright");
/* 153 */     addCharacterEncoding(37, "percent");
/* 154 */     addCharacterEncoding(46, "period");
/* 155 */     addCharacterEncoding(180, "periodcentered");
/* 156 */     addCharacterEncoding(189, "perthousand");
/* 157 */     addCharacterEncoding(43, "plus");
/* 158 */     addCharacterEncoding(113, "q");
/* 159 */     addCharacterEncoding(63, "question");
/* 160 */     addCharacterEncoding(191, "questiondown");
/* 161 */     addCharacterEncoding(34, "quotedbl");
/* 162 */     addCharacterEncoding(185, "quotedblbase");
/* 163 */     addCharacterEncoding(170, "quotedblleft");
/* 164 */     addCharacterEncoding(186, "quotedblright");
/* 165 */     addCharacterEncoding(96, "quoteleft");
/* 166 */     addCharacterEncoding(39, "quoteright");
/* 167 */     addCharacterEncoding(184, "quotesinglbase");
/* 168 */     addCharacterEncoding(169, "quotesingle");
/* 169 */     addCharacterEncoding(114, "r");
/* 170 */     addCharacterEncoding(202, "ring");
/* 171 */     addCharacterEncoding(115, "s");
/* 172 */     addCharacterEncoding(167, "section");
/* 173 */     addCharacterEncoding(59, "semicolon");
/* 174 */     addCharacterEncoding(55, "seven");
/* 175 */     addCharacterEncoding(54, "six");
/* 176 */     addCharacterEncoding(47, "slash");
/* 177 */     addCharacterEncoding(32, "space");
/* 178 */     addCharacterEncoding(163, "sterling");
/* 179 */     addCharacterEncoding(116, "t");
/* 180 */     addCharacterEncoding(51, "three");
/* 181 */     addCharacterEncoding(196, "tilde");
/* 182 */     addCharacterEncoding(50, "two");
/* 183 */     addCharacterEncoding(117, "u");
/* 184 */     addCharacterEncoding(95, "underscore");
/* 185 */     addCharacterEncoding(118, "v");
/* 186 */     addCharacterEncoding(119, "w");
/* 187 */     addCharacterEncoding(120, "x");
/* 188 */     addCharacterEncoding(121, "y");
/* 189 */     addCharacterEncoding(165, "yen");
/* 190 */     addCharacterEncoding(122, "z");
/* 191 */     addCharacterEncoding(48, "zero");
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/* 201 */     return COSName.STANDARD_ENCODING;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.encoding.StandardEncoding
 * JD-Core Version:    0.6.2
 */