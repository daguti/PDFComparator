/*     */ package org.apache.pdfbox.encoding;
/*     */ 
/*     */ import java.util.Map;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ 
/*     */ public class WinAnsiEncoding extends Encoding
/*     */ {
/*  35 */   public static final WinAnsiEncoding INSTANCE = new WinAnsiEncoding();
/*     */ 
/*     */   public WinAnsiEncoding()
/*     */   {
/*  42 */     addCharacterEncoding(65, "A");
/*  43 */     addCharacterEncoding(198, "AE");
/*  44 */     addCharacterEncoding(193, "Aacute");
/*  45 */     addCharacterEncoding(194, "Acircumflex");
/*  46 */     addCharacterEncoding(196, "Adieresis");
/*  47 */     addCharacterEncoding(192, "Agrave");
/*  48 */     addCharacterEncoding(197, "Aring");
/*  49 */     addCharacterEncoding(195, "Atilde");
/*  50 */     addCharacterEncoding(66, "B");
/*  51 */     addCharacterEncoding(67, "C");
/*  52 */     addCharacterEncoding(199, "Ccedilla");
/*  53 */     addCharacterEncoding(68, "D");
/*  54 */     addCharacterEncoding(69, "E");
/*  55 */     addCharacterEncoding(201, "Eacute");
/*  56 */     addCharacterEncoding(202, "Ecircumflex");
/*  57 */     addCharacterEncoding(203, "Edieresis");
/*  58 */     addCharacterEncoding(200, "Egrave");
/*  59 */     addCharacterEncoding(208, "Eth");
/*  60 */     addCharacterEncoding(128, "Euro");
/*  61 */     addCharacterEncoding(70, "F");
/*  62 */     addCharacterEncoding(71, "G");
/*  63 */     addCharacterEncoding(72, "H");
/*  64 */     addCharacterEncoding(73, "I");
/*  65 */     addCharacterEncoding(205, "Iacute");
/*  66 */     addCharacterEncoding(206, "Icircumflex");
/*  67 */     addCharacterEncoding(207, "Idieresis");
/*  68 */     addCharacterEncoding(204, "Igrave");
/*  69 */     addCharacterEncoding(74, "J");
/*  70 */     addCharacterEncoding(75, "K");
/*  71 */     addCharacterEncoding(76, "L");
/*  72 */     addCharacterEncoding(77, "M");
/*  73 */     addCharacterEncoding(78, "N");
/*  74 */     addCharacterEncoding(209, "Ntilde");
/*  75 */     addCharacterEncoding(79, "O");
/*  76 */     addCharacterEncoding(140, "OE");
/*  77 */     addCharacterEncoding(211, "Oacute");
/*  78 */     addCharacterEncoding(212, "Ocircumflex");
/*  79 */     addCharacterEncoding(214, "Odieresis");
/*  80 */     addCharacterEncoding(210, "Ograve");
/*  81 */     addCharacterEncoding(216, "Oslash");
/*  82 */     addCharacterEncoding(213, "Otilde");
/*  83 */     addCharacterEncoding(80, "P");
/*  84 */     addCharacterEncoding(81, "Q");
/*  85 */     addCharacterEncoding(82, "R");
/*  86 */     addCharacterEncoding(83, "S");
/*  87 */     addCharacterEncoding(138, "Scaron");
/*  88 */     addCharacterEncoding(84, "T");
/*  89 */     addCharacterEncoding(222, "Thorn");
/*  90 */     addCharacterEncoding(85, "U");
/*  91 */     addCharacterEncoding(218, "Uacute");
/*  92 */     addCharacterEncoding(219, "Ucircumflex");
/*  93 */     addCharacterEncoding(220, "Udieresis");
/*  94 */     addCharacterEncoding(217, "Ugrave");
/*  95 */     addCharacterEncoding(86, "V");
/*  96 */     addCharacterEncoding(87, "W");
/*  97 */     addCharacterEncoding(88, "X");
/*  98 */     addCharacterEncoding(89, "Y");
/*  99 */     addCharacterEncoding(221, "Yacute");
/* 100 */     addCharacterEncoding(159, "Ydieresis");
/* 101 */     addCharacterEncoding(90, "Z");
/* 102 */     addCharacterEncoding(142, "Zcaron");
/* 103 */     addCharacterEncoding(97, "a");
/* 104 */     addCharacterEncoding(225, "aacute");
/* 105 */     addCharacterEncoding(226, "acircumflex");
/* 106 */     addCharacterEncoding(180, "acute");
/* 107 */     addCharacterEncoding(228, "adieresis");
/* 108 */     addCharacterEncoding(230, "ae");
/* 109 */     addCharacterEncoding(224, "agrave");
/* 110 */     addCharacterEncoding(38, "ampersand");
/* 111 */     addCharacterEncoding(229, "aring");
/* 112 */     addCharacterEncoding(94, "asciicircum");
/* 113 */     addCharacterEncoding(126, "asciitilde");
/* 114 */     addCharacterEncoding(42, "asterisk");
/* 115 */     addCharacterEncoding(64, "at");
/* 116 */     addCharacterEncoding(227, "atilde");
/* 117 */     addCharacterEncoding(98, "b");
/* 118 */     addCharacterEncoding(92, "backslash");
/* 119 */     addCharacterEncoding(124, "bar");
/* 120 */     addCharacterEncoding(123, "braceleft");
/* 121 */     addCharacterEncoding(125, "braceright");
/* 122 */     addCharacterEncoding(91, "bracketleft");
/* 123 */     addCharacterEncoding(93, "bracketright");
/* 124 */     addCharacterEncoding(166, "brokenbar");
/* 125 */     addCharacterEncoding(149, "bullet");
/* 126 */     addCharacterEncoding(99, "c");
/* 127 */     addCharacterEncoding(231, "ccedilla");
/* 128 */     addCharacterEncoding(184, "cedilla");
/* 129 */     addCharacterEncoding(162, "cent");
/* 130 */     addCharacterEncoding(136, "circumflex");
/* 131 */     addCharacterEncoding(58, "colon");
/* 132 */     addCharacterEncoding(44, "comma");
/* 133 */     addCharacterEncoding(169, "copyright");
/* 134 */     addCharacterEncoding(164, "currency");
/* 135 */     addCharacterEncoding(100, "d");
/* 136 */     addCharacterEncoding(134, "dagger");
/* 137 */     addCharacterEncoding(135, "daggerdbl");
/* 138 */     addCharacterEncoding(176, "degree");
/* 139 */     addCharacterEncoding(168, "dieresis");
/* 140 */     addCharacterEncoding(247, "divide");
/* 141 */     addCharacterEncoding(36, "dollar");
/* 142 */     addCharacterEncoding(101, "e");
/* 143 */     addCharacterEncoding(233, "eacute");
/* 144 */     addCharacterEncoding(234, "ecircumflex");
/* 145 */     addCharacterEncoding(235, "edieresis");
/* 146 */     addCharacterEncoding(232, "egrave");
/* 147 */     addCharacterEncoding(56, "eight");
/* 148 */     addCharacterEncoding(133, "ellipsis");
/* 149 */     addCharacterEncoding(151, "emdash");
/* 150 */     addCharacterEncoding(150, "endash");
/* 151 */     addCharacterEncoding(61, "equal");
/* 152 */     addCharacterEncoding(240, "eth");
/* 153 */     addCharacterEncoding(33, "exclam");
/* 154 */     addCharacterEncoding(161, "exclamdown");
/* 155 */     addCharacterEncoding(102, "f");
/* 156 */     addCharacterEncoding(53, "five");
/* 157 */     addCharacterEncoding(131, "florin");
/* 158 */     addCharacterEncoding(52, "four");
/* 159 */     addCharacterEncoding(103, "g");
/* 160 */     addCharacterEncoding(223, "germandbls");
/* 161 */     addCharacterEncoding(96, "grave");
/* 162 */     addCharacterEncoding(62, "greater");
/* 163 */     addCharacterEncoding(171, "guillemotleft");
/* 164 */     addCharacterEncoding(187, "guillemotright");
/* 165 */     addCharacterEncoding(139, "guilsinglleft");
/* 166 */     addCharacterEncoding(155, "guilsinglright");
/* 167 */     addCharacterEncoding(104, "h");
/* 168 */     addCharacterEncoding(45, "hyphen");
/* 169 */     addCharacterEncoding(105, "i");
/* 170 */     addCharacterEncoding(237, "iacute");
/* 171 */     addCharacterEncoding(238, "icircumflex");
/* 172 */     addCharacterEncoding(239, "idieresis");
/* 173 */     addCharacterEncoding(236, "igrave");
/* 174 */     addCharacterEncoding(106, "j");
/* 175 */     addCharacterEncoding(107, "k");
/* 176 */     addCharacterEncoding(108, "l");
/* 177 */     addCharacterEncoding(60, "less");
/* 178 */     addCharacterEncoding(172, "logicalnot");
/* 179 */     addCharacterEncoding(109, "m");
/* 180 */     addCharacterEncoding(175, "macron");
/* 181 */     addCharacterEncoding(181, "mu");
/* 182 */     addCharacterEncoding(215, "multiply");
/* 183 */     addCharacterEncoding(110, "n");
/* 184 */     addCharacterEncoding(57, "nine");
/* 185 */     addCharacterEncoding(241, "ntilde");
/* 186 */     addCharacterEncoding(35, "numbersign");
/* 187 */     addCharacterEncoding(111, "o");
/* 188 */     addCharacterEncoding(243, "oacute");
/* 189 */     addCharacterEncoding(244, "ocircumflex");
/* 190 */     addCharacterEncoding(246, "odieresis");
/* 191 */     addCharacterEncoding(156, "oe");
/* 192 */     addCharacterEncoding(242, "ograve");
/* 193 */     addCharacterEncoding(49, "one");
/* 194 */     addCharacterEncoding(189, "onehalf");
/* 195 */     addCharacterEncoding(188, "onequarter");
/* 196 */     addCharacterEncoding(185, "onesuperior");
/* 197 */     addCharacterEncoding(170, "ordfeminine");
/* 198 */     addCharacterEncoding(186, "ordmasculine");
/* 199 */     addCharacterEncoding(248, "oslash");
/* 200 */     addCharacterEncoding(245, "otilde");
/* 201 */     addCharacterEncoding(112, "p");
/* 202 */     addCharacterEncoding(182, "paragraph");
/* 203 */     addCharacterEncoding(40, "parenleft");
/* 204 */     addCharacterEncoding(41, "parenright");
/* 205 */     addCharacterEncoding(37, "percent");
/* 206 */     addCharacterEncoding(46, "period");
/* 207 */     addCharacterEncoding(183, "periodcentered");
/* 208 */     addCharacterEncoding(137, "perthousand");
/* 209 */     addCharacterEncoding(43, "plus");
/* 210 */     addCharacterEncoding(177, "plusminus");
/* 211 */     addCharacterEncoding(113, "q");
/* 212 */     addCharacterEncoding(63, "question");
/* 213 */     addCharacterEncoding(191, "questiondown");
/* 214 */     addCharacterEncoding(34, "quotedbl");
/* 215 */     addCharacterEncoding(132, "quotedblbase");
/* 216 */     addCharacterEncoding(147, "quotedblleft");
/* 217 */     addCharacterEncoding(148, "quotedblright");
/* 218 */     addCharacterEncoding(145, "quoteleft");
/* 219 */     addCharacterEncoding(146, "quoteright");
/* 220 */     addCharacterEncoding(130, "quotesinglbase");
/* 221 */     addCharacterEncoding(39, "quotesingle");
/* 222 */     addCharacterEncoding(114, "r");
/* 223 */     addCharacterEncoding(174, "registered");
/* 224 */     addCharacterEncoding(115, "s");
/* 225 */     addCharacterEncoding(154, "scaron");
/* 226 */     addCharacterEncoding(167, "section");
/* 227 */     addCharacterEncoding(59, "semicolon");
/* 228 */     addCharacterEncoding(55, "seven");
/* 229 */     addCharacterEncoding(54, "six");
/* 230 */     addCharacterEncoding(47, "slash");
/* 231 */     addCharacterEncoding(32, "space");
/* 232 */     addCharacterEncoding(163, "sterling");
/* 233 */     addCharacterEncoding(116, "t");
/* 234 */     addCharacterEncoding(254, "thorn");
/* 235 */     addCharacterEncoding(51, "three");
/* 236 */     addCharacterEncoding(190, "threequarters");
/* 237 */     addCharacterEncoding(179, "threesuperior");
/* 238 */     addCharacterEncoding(152, "tilde");
/* 239 */     addCharacterEncoding(153, "trademark");
/* 240 */     addCharacterEncoding(50, "two");
/* 241 */     addCharacterEncoding(178, "twosuperior");
/* 242 */     addCharacterEncoding(117, "u");
/* 243 */     addCharacterEncoding(250, "uacute");
/* 244 */     addCharacterEncoding(251, "ucircumflex");
/* 245 */     addCharacterEncoding(252, "udieresis");
/* 246 */     addCharacterEncoding(249, "ugrave");
/* 247 */     addCharacterEncoding(95, "underscore");
/* 248 */     addCharacterEncoding(118, "v");
/* 249 */     addCharacterEncoding(119, "w");
/* 250 */     addCharacterEncoding(120, "x");
/* 251 */     addCharacterEncoding(121, "y");
/* 252 */     addCharacterEncoding(253, "yacute");
/* 253 */     addCharacterEncoding(255, "ydieresis");
/* 254 */     addCharacterEncoding(165, "yen");
/* 255 */     addCharacterEncoding(122, "z");
/* 256 */     addCharacterEncoding(158, "zcaron");
/* 257 */     addCharacterEncoding(48, "zero");
/*     */ 
/* 261 */     this.codeToName.put(Integer.valueOf(160), "space");
/* 262 */     this.codeToName.put(Integer.valueOf(173), "hyphen");
/* 263 */     for (int i = 33; i <= 255; i++)
/*     */     {
/* 265 */       if (!this.codeToName.containsKey(Integer.valueOf(i)))
/*     */       {
/* 267 */         this.codeToName.put(Integer.valueOf(i), "bullet");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/* 279 */     return COSName.WIN_ANSI_ENCODING;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.encoding.WinAnsiEncoding
 * JD-Core Version:    0.6.2
 */