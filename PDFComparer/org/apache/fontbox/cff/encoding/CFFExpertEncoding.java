/*     */ package org.apache.fontbox.cff.encoding;
/*     */ 
/*     */ public class CFFExpertEncoding extends CFFEncoding
/*     */ {
/*  41 */   private static final CFFExpertEncoding INSTANCE = new CFFExpertEncoding();
/*     */ 
/*     */   public static CFFExpertEncoding getInstance()
/*     */   {
/*  38 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  45 */     INSTANCE.register(0, 0);
/*  46 */     INSTANCE.register(1, 0);
/*  47 */     INSTANCE.register(2, 0);
/*  48 */     INSTANCE.register(3, 0);
/*  49 */     INSTANCE.register(4, 0);
/*  50 */     INSTANCE.register(5, 0);
/*  51 */     INSTANCE.register(6, 0);
/*  52 */     INSTANCE.register(7, 0);
/*  53 */     INSTANCE.register(8, 0);
/*  54 */     INSTANCE.register(9, 0);
/*  55 */     INSTANCE.register(10, 0);
/*  56 */     INSTANCE.register(11, 0);
/*  57 */     INSTANCE.register(12, 0);
/*  58 */     INSTANCE.register(13, 0);
/*  59 */     INSTANCE.register(14, 0);
/*  60 */     INSTANCE.register(15, 0);
/*  61 */     INSTANCE.register(16, 0);
/*  62 */     INSTANCE.register(17, 0);
/*  63 */     INSTANCE.register(18, 0);
/*  64 */     INSTANCE.register(19, 0);
/*  65 */     INSTANCE.register(20, 0);
/*  66 */     INSTANCE.register(21, 0);
/*  67 */     INSTANCE.register(22, 0);
/*  68 */     INSTANCE.register(23, 0);
/*  69 */     INSTANCE.register(24, 0);
/*  70 */     INSTANCE.register(25, 0);
/*  71 */     INSTANCE.register(26, 0);
/*  72 */     INSTANCE.register(27, 0);
/*  73 */     INSTANCE.register(28, 0);
/*  74 */     INSTANCE.register(29, 0);
/*  75 */     INSTANCE.register(30, 0);
/*  76 */     INSTANCE.register(31, 0);
/*  77 */     INSTANCE.register(32, 1);
/*  78 */     INSTANCE.register(33, 229);
/*  79 */     INSTANCE.register(34, 230);
/*  80 */     INSTANCE.register(35, 0);
/*  81 */     INSTANCE.register(36, 231);
/*  82 */     INSTANCE.register(37, 232);
/*  83 */     INSTANCE.register(38, 233);
/*  84 */     INSTANCE.register(39, 234);
/*  85 */     INSTANCE.register(40, 235);
/*  86 */     INSTANCE.register(41, 236);
/*  87 */     INSTANCE.register(42, 237);
/*  88 */     INSTANCE.register(43, 238);
/*  89 */     INSTANCE.register(44, 13);
/*  90 */     INSTANCE.register(45, 14);
/*  91 */     INSTANCE.register(46, 15);
/*  92 */     INSTANCE.register(47, 99);
/*  93 */     INSTANCE.register(48, 239);
/*  94 */     INSTANCE.register(49, 240);
/*  95 */     INSTANCE.register(50, 241);
/*  96 */     INSTANCE.register(51, 242);
/*  97 */     INSTANCE.register(52, 243);
/*  98 */     INSTANCE.register(53, 244);
/*  99 */     INSTANCE.register(54, 245);
/* 100 */     INSTANCE.register(55, 246);
/* 101 */     INSTANCE.register(56, 247);
/* 102 */     INSTANCE.register(57, 248);
/* 103 */     INSTANCE.register(58, 27);
/* 104 */     INSTANCE.register(59, 28);
/* 105 */     INSTANCE.register(60, 249);
/* 106 */     INSTANCE.register(61, 250);
/* 107 */     INSTANCE.register(62, 251);
/* 108 */     INSTANCE.register(63, 252);
/* 109 */     INSTANCE.register(64, 0);
/* 110 */     INSTANCE.register(65, 253);
/* 111 */     INSTANCE.register(66, 254);
/* 112 */     INSTANCE.register(67, 255);
/* 113 */     INSTANCE.register(68, 256);
/* 114 */     INSTANCE.register(69, 257);
/* 115 */     INSTANCE.register(70, 0);
/* 116 */     INSTANCE.register(71, 0);
/* 117 */     INSTANCE.register(72, 0);
/* 118 */     INSTANCE.register(73, 258);
/* 119 */     INSTANCE.register(74, 0);
/* 120 */     INSTANCE.register(75, 0);
/* 121 */     INSTANCE.register(76, 259);
/* 122 */     INSTANCE.register(77, 260);
/* 123 */     INSTANCE.register(78, 261);
/* 124 */     INSTANCE.register(79, 262);
/* 125 */     INSTANCE.register(80, 0);
/* 126 */     INSTANCE.register(81, 0);
/* 127 */     INSTANCE.register(82, 263);
/* 128 */     INSTANCE.register(83, 264);
/* 129 */     INSTANCE.register(84, 265);
/* 130 */     INSTANCE.register(85, 0);
/* 131 */     INSTANCE.register(86, 266);
/* 132 */     INSTANCE.register(87, 109);
/* 133 */     INSTANCE.register(88, 110);
/* 134 */     INSTANCE.register(89, 267);
/* 135 */     INSTANCE.register(90, 268);
/* 136 */     INSTANCE.register(91, 269);
/* 137 */     INSTANCE.register(92, 0);
/* 138 */     INSTANCE.register(93, 270);
/* 139 */     INSTANCE.register(94, 271);
/* 140 */     INSTANCE.register(95, 272);
/* 141 */     INSTANCE.register(96, 273);
/* 142 */     INSTANCE.register(97, 274);
/* 143 */     INSTANCE.register(98, 275);
/* 144 */     INSTANCE.register(99, 276);
/* 145 */     INSTANCE.register(100, 277);
/* 146 */     INSTANCE.register(101, 278);
/* 147 */     INSTANCE.register(102, 279);
/* 148 */     INSTANCE.register(103, 280);
/* 149 */     INSTANCE.register(104, 281);
/* 150 */     INSTANCE.register(105, 282);
/* 151 */     INSTANCE.register(106, 283);
/* 152 */     INSTANCE.register(107, 284);
/* 153 */     INSTANCE.register(108, 285);
/* 154 */     INSTANCE.register(109, 286);
/* 155 */     INSTANCE.register(110, 287);
/* 156 */     INSTANCE.register(111, 288);
/* 157 */     INSTANCE.register(112, 289);
/* 158 */     INSTANCE.register(113, 290);
/* 159 */     INSTANCE.register(114, 291);
/* 160 */     INSTANCE.register(115, 292);
/* 161 */     INSTANCE.register(116, 293);
/* 162 */     INSTANCE.register(117, 294);
/* 163 */     INSTANCE.register(118, 295);
/* 164 */     INSTANCE.register(119, 296);
/* 165 */     INSTANCE.register(120, 297);
/* 166 */     INSTANCE.register(121, 298);
/* 167 */     INSTANCE.register(122, 299);
/* 168 */     INSTANCE.register(123, 300);
/* 169 */     INSTANCE.register(124, 301);
/* 170 */     INSTANCE.register(125, 302);
/* 171 */     INSTANCE.register(126, 303);
/* 172 */     INSTANCE.register(127, 0);
/* 173 */     INSTANCE.register(128, 0);
/* 174 */     INSTANCE.register(129, 0);
/* 175 */     INSTANCE.register(130, 0);
/* 176 */     INSTANCE.register(131, 0);
/* 177 */     INSTANCE.register(132, 0);
/* 178 */     INSTANCE.register(133, 0);
/* 179 */     INSTANCE.register(134, 0);
/* 180 */     INSTANCE.register(135, 0);
/* 181 */     INSTANCE.register(136, 0);
/* 182 */     INSTANCE.register(137, 0);
/* 183 */     INSTANCE.register(138, 0);
/* 184 */     INSTANCE.register(139, 0);
/* 185 */     INSTANCE.register(140, 0);
/* 186 */     INSTANCE.register(141, 0);
/* 187 */     INSTANCE.register(142, 0);
/* 188 */     INSTANCE.register(143, 0);
/* 189 */     INSTANCE.register(144, 0);
/* 190 */     INSTANCE.register(145, 0);
/* 191 */     INSTANCE.register(146, 0);
/* 192 */     INSTANCE.register(147, 0);
/* 193 */     INSTANCE.register(148, 0);
/* 194 */     INSTANCE.register(149, 0);
/* 195 */     INSTANCE.register(150, 0);
/* 196 */     INSTANCE.register(151, 0);
/* 197 */     INSTANCE.register(152, 0);
/* 198 */     INSTANCE.register(153, 0);
/* 199 */     INSTANCE.register(154, 0);
/* 200 */     INSTANCE.register(155, 0);
/* 201 */     INSTANCE.register(156, 0);
/* 202 */     INSTANCE.register(157, 0);
/* 203 */     INSTANCE.register(158, 0);
/* 204 */     INSTANCE.register(159, 0);
/* 205 */     INSTANCE.register(160, 0);
/* 206 */     INSTANCE.register(161, 304);
/* 207 */     INSTANCE.register(162, 305);
/* 208 */     INSTANCE.register(163, 306);
/* 209 */     INSTANCE.register(164, 0);
/* 210 */     INSTANCE.register(165, 0);
/* 211 */     INSTANCE.register(166, 307);
/* 212 */     INSTANCE.register(167, 308);
/* 213 */     INSTANCE.register(168, 309);
/* 214 */     INSTANCE.register(169, 310);
/* 215 */     INSTANCE.register(170, 311);
/* 216 */     INSTANCE.register(171, 0);
/* 217 */     INSTANCE.register(172, 312);
/* 218 */     INSTANCE.register(173, 0);
/* 219 */     INSTANCE.register(174, 0);
/* 220 */     INSTANCE.register(175, 313);
/* 221 */     INSTANCE.register(176, 0);
/* 222 */     INSTANCE.register(177, 0);
/* 223 */     INSTANCE.register(178, 314);
/* 224 */     INSTANCE.register(179, 315);
/* 225 */     INSTANCE.register(180, 0);
/* 226 */     INSTANCE.register(181, 0);
/* 227 */     INSTANCE.register(182, 316);
/* 228 */     INSTANCE.register(183, 317);
/* 229 */     INSTANCE.register(184, 318);
/* 230 */     INSTANCE.register(185, 0);
/* 231 */     INSTANCE.register(186, 0);
/* 232 */     INSTANCE.register(187, 0);
/* 233 */     INSTANCE.register(188, 158);
/* 234 */     INSTANCE.register(189, 155);
/* 235 */     INSTANCE.register(190, 163);
/* 236 */     INSTANCE.register(191, 319);
/* 237 */     INSTANCE.register(192, 320);
/* 238 */     INSTANCE.register(193, 321);
/* 239 */     INSTANCE.register(194, 322);
/* 240 */     INSTANCE.register(195, 323);
/* 241 */     INSTANCE.register(196, 324);
/* 242 */     INSTANCE.register(197, 325);
/* 243 */     INSTANCE.register(198, 0);
/* 244 */     INSTANCE.register(199, 0);
/* 245 */     INSTANCE.register(200, 326);
/* 246 */     INSTANCE.register(201, 150);
/* 247 */     INSTANCE.register(202, 164);
/* 248 */     INSTANCE.register(203, 169);
/* 249 */     INSTANCE.register(204, 327);
/* 250 */     INSTANCE.register(205, 328);
/* 251 */     INSTANCE.register(206, 329);
/* 252 */     INSTANCE.register(207, 330);
/* 253 */     INSTANCE.register(208, 331);
/* 254 */     INSTANCE.register(209, 332);
/* 255 */     INSTANCE.register(210, 333);
/* 256 */     INSTANCE.register(211, 334);
/* 257 */     INSTANCE.register(212, 335);
/* 258 */     INSTANCE.register(213, 336);
/* 259 */     INSTANCE.register(214, 337);
/* 260 */     INSTANCE.register(215, 338);
/* 261 */     INSTANCE.register(216, 339);
/* 262 */     INSTANCE.register(217, 340);
/* 263 */     INSTANCE.register(218, 341);
/* 264 */     INSTANCE.register(219, 342);
/* 265 */     INSTANCE.register(220, 343);
/* 266 */     INSTANCE.register(221, 344);
/* 267 */     INSTANCE.register(222, 345);
/* 268 */     INSTANCE.register(223, 346);
/* 269 */     INSTANCE.register(224, 347);
/* 270 */     INSTANCE.register(225, 348);
/* 271 */     INSTANCE.register(226, 349);
/* 272 */     INSTANCE.register(227, 350);
/* 273 */     INSTANCE.register(228, 351);
/* 274 */     INSTANCE.register(229, 352);
/* 275 */     INSTANCE.register(230, 353);
/* 276 */     INSTANCE.register(231, 354);
/* 277 */     INSTANCE.register(232, 355);
/* 278 */     INSTANCE.register(233, 356);
/* 279 */     INSTANCE.register(234, 357);
/* 280 */     INSTANCE.register(235, 358);
/* 281 */     INSTANCE.register(236, 359);
/* 282 */     INSTANCE.register(237, 360);
/* 283 */     INSTANCE.register(238, 361);
/* 284 */     INSTANCE.register(239, 362);
/* 285 */     INSTANCE.register(240, 363);
/* 286 */     INSTANCE.register(241, 364);
/* 287 */     INSTANCE.register(242, 365);
/* 288 */     INSTANCE.register(243, 366);
/* 289 */     INSTANCE.register(244, 367);
/* 290 */     INSTANCE.register(245, 368);
/* 291 */     INSTANCE.register(246, 369);
/* 292 */     INSTANCE.register(247, 370);
/* 293 */     INSTANCE.register(248, 371);
/* 294 */     INSTANCE.register(249, 372);
/* 295 */     INSTANCE.register(250, 373);
/* 296 */     INSTANCE.register(251, 374);
/* 297 */     INSTANCE.register(252, 375);
/* 298 */     INSTANCE.register(253, 376);
/* 299 */     INSTANCE.register(254, 377);
/* 300 */     INSTANCE.register(255, 378);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.cff.encoding.CFFExpertEncoding
 * JD-Core Version:    0.6.2
 */