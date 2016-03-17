/*     */ package org.apache.pdfbox.encoding;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ 
/*     */ public class PdfDocEncoding extends Encoding
/*     */ {
/*  36 */   public static final PdfDocEncoding INSTANCE = new PdfDocEncoding();
/*     */ 
/*     */   public PdfDocEncoding()
/*     */   {
/*  43 */     addCharacterEncoding(65, "A");
/*  44 */     addCharacterEncoding(198, "AE");
/*  45 */     addCharacterEncoding(193, "Aacute");
/*  46 */     addCharacterEncoding(194, "Acircumflex");
/*  47 */     addCharacterEncoding(196, "Adieresis");
/*  48 */     addCharacterEncoding(192, "Agrave");
/*  49 */     addCharacterEncoding(197, "Aring");
/*  50 */     addCharacterEncoding(195, "Atilde");
/*  51 */     addCharacterEncoding(66, "B");
/*  52 */     addCharacterEncoding(67, "C");
/*  53 */     addCharacterEncoding(199, "Ccedilla");
/*  54 */     addCharacterEncoding(68, "D");
/*  55 */     addCharacterEncoding(69, "E");
/*  56 */     addCharacterEncoding(201, "Eacute");
/*  57 */     addCharacterEncoding(202, "Ecircumflex");
/*  58 */     addCharacterEncoding(203, "Edieresis");
/*  59 */     addCharacterEncoding(200, "Egrave");
/*  60 */     addCharacterEncoding(208, "Eth");
/*  61 */     addCharacterEncoding(160, "Euro");
/*  62 */     addCharacterEncoding(70, "F");
/*  63 */     addCharacterEncoding(71, "G");
/*  64 */     addCharacterEncoding(72, "H");
/*  65 */     addCharacterEncoding(73, "I");
/*  66 */     addCharacterEncoding(205, "Iacute");
/*  67 */     addCharacterEncoding(206, "Icircumflex");
/*  68 */     addCharacterEncoding(207, "Idieresis");
/*  69 */     addCharacterEncoding(204, "Igrave");
/*  70 */     addCharacterEncoding(74, "J");
/*  71 */     addCharacterEncoding(75, "K");
/*  72 */     addCharacterEncoding(76, "L");
/*  73 */     addCharacterEncoding(149, "Lslash");
/*  74 */     addCharacterEncoding(77, "M");
/*  75 */     addCharacterEncoding(78, "N");
/*  76 */     addCharacterEncoding(209, "Ntilde");
/*  77 */     addCharacterEncoding(79, "O");
/*  78 */     addCharacterEncoding(150, "OE");
/*  79 */     addCharacterEncoding(211, "Oacute");
/*  80 */     addCharacterEncoding(212, "Ocircumflex");
/*  81 */     addCharacterEncoding(214, "Odieresis");
/*  82 */     addCharacterEncoding(210, "Ograve");
/*  83 */     addCharacterEncoding(216, "Oslash");
/*  84 */     addCharacterEncoding(213, "Otilde");
/*  85 */     addCharacterEncoding(80, "P");
/*  86 */     addCharacterEncoding(81, "Q");
/*  87 */     addCharacterEncoding(82, "R");
/*  88 */     addCharacterEncoding(83, "S");
/*  89 */     addCharacterEncoding(151, "Scaron");
/*  90 */     addCharacterEncoding(84, "T");
/*  91 */     addCharacterEncoding(222, "Thorn");
/*  92 */     addCharacterEncoding(85, "U");
/*  93 */     addCharacterEncoding(218, "Uacute");
/*  94 */     addCharacterEncoding(219, "Ucircumflex");
/*  95 */     addCharacterEncoding(220, "Udieresis");
/*  96 */     addCharacterEncoding(217, "Ugrave");
/*  97 */     addCharacterEncoding(86, "V");
/*  98 */     addCharacterEncoding(87, "W");
/*  99 */     addCharacterEncoding(88, "X");
/* 100 */     addCharacterEncoding(89, "Y");
/* 101 */     addCharacterEncoding(221, "Yacute");
/* 102 */     addCharacterEncoding(152, "Ydieresis");
/* 103 */     addCharacterEncoding(90, "Z");
/* 104 */     addCharacterEncoding(153, "Zcaron");
/* 105 */     addCharacterEncoding(97, "a");
/* 106 */     addCharacterEncoding(225, "aacute");
/* 107 */     addCharacterEncoding(226, "acircumflex");
/* 108 */     addCharacterEncoding(180, "acute");
/* 109 */     addCharacterEncoding(228, "adieresis");
/* 110 */     addCharacterEncoding(230, "ae");
/* 111 */     addCharacterEncoding(224, "agrave");
/* 112 */     addCharacterEncoding(38, "ampersand");
/* 113 */     addCharacterEncoding(229, "aring");
/* 114 */     addCharacterEncoding(94, "asciicircum");
/* 115 */     addCharacterEncoding(126, "asciitilde");
/* 116 */     addCharacterEncoding(42, "asterisk");
/* 117 */     addCharacterEncoding(64, "at");
/* 118 */     addCharacterEncoding(227, "atilde");
/* 119 */     addCharacterEncoding(98, "b");
/* 120 */     addCharacterEncoding(92, "backslash");
/* 121 */     addCharacterEncoding(124, "bar");
/* 122 */     addCharacterEncoding(123, "braceleft");
/* 123 */     addCharacterEncoding(125, "braceright");
/* 124 */     addCharacterEncoding(91, "bracketleft");
/* 125 */     addCharacterEncoding(93, "bracketright");
/* 126 */     addCharacterEncoding(24, "breve");
/* 127 */     addCharacterEncoding(166, "brokenbar");
/* 128 */     addCharacterEncoding(128, "bullet");
/* 129 */     addCharacterEncoding(99, "c");
/* 130 */     addCharacterEncoding(25, "caron");
/* 131 */     addCharacterEncoding(231, "ccedilla");
/* 132 */     addCharacterEncoding(184, "cedilla");
/* 133 */     addCharacterEncoding(162, "cent");
/* 134 */     addCharacterEncoding(26, "circumflex");
/* 135 */     addCharacterEncoding(58, "colon");
/* 136 */     addCharacterEncoding(44, "comma");
/* 137 */     addCharacterEncoding(169, "copyright");
/* 138 */     addCharacterEncoding(164, "currency");
/* 139 */     addCharacterEncoding(100, "d");
/* 140 */     addCharacterEncoding(129, "dagger");
/* 141 */     addCharacterEncoding(130, "daggerdbl");
/* 142 */     addCharacterEncoding(176, "degree");
/* 143 */     addCharacterEncoding(168, "dieresis");
/* 144 */     addCharacterEncoding(247, "divide");
/* 145 */     addCharacterEncoding(36, "dollar");
/* 146 */     addCharacterEncoding(27, "dotaccent");
/* 147 */     addCharacterEncoding(154, "dotlessi");
/* 148 */     addCharacterEncoding(101, "e");
/* 149 */     addCharacterEncoding(233, "eacute");
/* 150 */     addCharacterEncoding(234, "ecircumflex");
/* 151 */     addCharacterEncoding(235, "edieresis");
/* 152 */     addCharacterEncoding(232, "egrave");
/* 153 */     addCharacterEncoding(56, "eight");
/* 154 */     addCharacterEncoding(131, "ellipsis");
/* 155 */     addCharacterEncoding(132, "emdash");
/* 156 */     addCharacterEncoding(133, "endash");
/* 157 */     addCharacterEncoding(61, "equal");
/* 158 */     addCharacterEncoding(240, "eth");
/* 159 */     addCharacterEncoding(33, "exclam");
/* 160 */     addCharacterEncoding(161, "exclamdown");
/* 161 */     addCharacterEncoding(102, "f");
/* 162 */     addCharacterEncoding(147, "fi");
/* 163 */     addCharacterEncoding(53, "five");
/* 164 */     addCharacterEncoding(148, "fl");
/* 165 */     addCharacterEncoding(134, "florin");
/* 166 */     addCharacterEncoding(52, "four");
/* 167 */     addCharacterEncoding(135, "fraction");
/* 168 */     addCharacterEncoding(103, "g");
/* 169 */     addCharacterEncoding(223, "germandbls");
/* 170 */     addCharacterEncoding(96, "grave");
/* 171 */     addCharacterEncoding(62, "greater");
/* 172 */     addCharacterEncoding(171, "guillemotleft");
/* 173 */     addCharacterEncoding(187, "guillemotright");
/* 174 */     addCharacterEncoding(136, "guilsinglleft");
/* 175 */     addCharacterEncoding(137, "guilsinglright");
/* 176 */     addCharacterEncoding(104, "h");
/* 177 */     addCharacterEncoding(28, "hungarumlaut");
/* 178 */     addCharacterEncoding(45, "hyphen");
/* 179 */     addCharacterEncoding(105, "i");
/* 180 */     addCharacterEncoding(237, "iacute");
/* 181 */     addCharacterEncoding(238, "icircumflex");
/* 182 */     addCharacterEncoding(239, "idieresis");
/* 183 */     addCharacterEncoding(236, "igrave");
/* 184 */     addCharacterEncoding(106, "j");
/* 185 */     addCharacterEncoding(107, "k");
/* 186 */     addCharacterEncoding(108, "l");
/* 187 */     addCharacterEncoding(60, "less");
/* 188 */     addCharacterEncoding(172, "logicalnot");
/* 189 */     addCharacterEncoding(155, "lslash");
/* 190 */     addCharacterEncoding(109, "m");
/* 191 */     addCharacterEncoding(175, "macron");
/* 192 */     addCharacterEncoding(138, "minus");
/* 193 */     addCharacterEncoding(181, "mu");
/* 194 */     addCharacterEncoding(215, "multiply");
/* 195 */     addCharacterEncoding(110, "n");
/* 196 */     addCharacterEncoding(57, "nine");
/* 197 */     addCharacterEncoding(241, "ntilde");
/* 198 */     addCharacterEncoding(35, "numbersign");
/* 199 */     addCharacterEncoding(111, "o");
/* 200 */     addCharacterEncoding(243, "oacute");
/* 201 */     addCharacterEncoding(244, "ocircumflex");
/* 202 */     addCharacterEncoding(246, "odieresis");
/* 203 */     addCharacterEncoding(156, "oe");
/* 204 */     addCharacterEncoding(29, "ogonek");
/* 205 */     addCharacterEncoding(242, "ograve");
/* 206 */     addCharacterEncoding(49, "one");
/* 207 */     addCharacterEncoding(189, "onehalf");
/* 208 */     addCharacterEncoding(188, "onequarter");
/* 209 */     addCharacterEncoding(185, "onesuperior");
/* 210 */     addCharacterEncoding(170, "ordfeminine");
/* 211 */     addCharacterEncoding(186, "ordmasculine");
/* 212 */     addCharacterEncoding(248, "oslash");
/* 213 */     addCharacterEncoding(245, "otilde");
/* 214 */     addCharacterEncoding(112, "p");
/* 215 */     addCharacterEncoding(182, "paragraph");
/* 216 */     addCharacterEncoding(40, "parenleft");
/* 217 */     addCharacterEncoding(41, "parenright");
/* 218 */     addCharacterEncoding(37, "percent");
/* 219 */     addCharacterEncoding(46, "period");
/* 220 */     addCharacterEncoding(183, "periodcentered");
/* 221 */     addCharacterEncoding(139, "perthousand");
/* 222 */     addCharacterEncoding(43, "plus");
/* 223 */     addCharacterEncoding(177, "plusminus");
/* 224 */     addCharacterEncoding(113, "q");
/* 225 */     addCharacterEncoding(63, "question");
/* 226 */     addCharacterEncoding(191, "questiondown");
/* 227 */     addCharacterEncoding(34, "quotedbl");
/* 228 */     addCharacterEncoding(140, "quotedblbase");
/* 229 */     addCharacterEncoding(141, "quotedblleft");
/* 230 */     addCharacterEncoding(142, "quotedblright");
/* 231 */     addCharacterEncoding(143, "quoteleft");
/* 232 */     addCharacterEncoding(144, "quoteright");
/* 233 */     addCharacterEncoding(145, "quotesinglbase");
/* 234 */     addCharacterEncoding(39, "quotesingle");
/* 235 */     addCharacterEncoding(114, "r");
/* 236 */     addCharacterEncoding(174, "registered");
/* 237 */     addCharacterEncoding(30, "ring");
/* 238 */     addCharacterEncoding(115, "s");
/* 239 */     addCharacterEncoding(157, "scaron");
/* 240 */     addCharacterEncoding(167, "section");
/* 241 */     addCharacterEncoding(59, "semicolon");
/* 242 */     addCharacterEncoding(55, "seven");
/* 243 */     addCharacterEncoding(54, "six");
/* 244 */     addCharacterEncoding(47, "slash");
/* 245 */     addCharacterEncoding(32, "space");
/* 246 */     addCharacterEncoding(163, "sterling");
/* 247 */     addCharacterEncoding(116, "t");
/* 248 */     addCharacterEncoding(254, "thorn");
/* 249 */     addCharacterEncoding(51, "three");
/* 250 */     addCharacterEncoding(190, "threequarters");
/* 251 */     addCharacterEncoding(179, "threesuperior");
/* 252 */     addCharacterEncoding(31, "tilde");
/* 253 */     addCharacterEncoding(146, "trademark");
/* 254 */     addCharacterEncoding(50, "two");
/* 255 */     addCharacterEncoding(178, "twosuperior");
/* 256 */     addCharacterEncoding(117, "u");
/* 257 */     addCharacterEncoding(250, "uacute");
/* 258 */     addCharacterEncoding(251, "ucircumflex");
/* 259 */     addCharacterEncoding(252, "udieresis");
/* 260 */     addCharacterEncoding(249, "ugrave");
/* 261 */     addCharacterEncoding(95, "underscore");
/* 262 */     addCharacterEncoding(118, "v");
/* 263 */     addCharacterEncoding(119, "w");
/* 264 */     addCharacterEncoding(120, "x");
/* 265 */     addCharacterEncoding(121, "y");
/* 266 */     addCharacterEncoding(253, "yacute");
/* 267 */     addCharacterEncoding(255, "ydieresis");
/* 268 */     addCharacterEncoding(165, "yen");
/* 269 */     addCharacterEncoding(122, "z");
/* 270 */     addCharacterEncoding(158, "zcaron");
/* 271 */     addCharacterEncoding(48, "zero");
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/* 281 */     return COSName.PDF_DOC_ENCODING;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.encoding.PdfDocEncoding
 * JD-Core Version:    0.6.2
 */