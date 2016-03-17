/*     */ package org.apache.pdfbox.preflight.font.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import org.apache.pdfbox.encoding.Encoding;
/*     */ 
/*     */ public class Type1
/*     */ {
/*  38 */   private Map<Integer, String> cidToLabel = new HashMap(0);
/*     */ 
/*  42 */   private Map<String, Integer> labelToCid = new HashMap(0);
/*     */ 
/*  46 */   private Map<String, GlyphDescription> labelToMetric = new HashMap(0);
/*     */ 
/*  51 */   private Encoding encoding = null;
/*     */ 
/*     */   public Type1(Encoding encoding)
/*     */   {
/*  55 */     this.encoding = encoding;
/*     */   }
/*     */ 
/*     */   void addCidWithLabel(Integer cid, String label)
/*     */   {
/*  60 */     this.labelToCid.put(label, cid);
/*  61 */     this.cidToLabel.put(cid, label);
/*     */   }
/*     */ 
/*     */   void addGlyphDescription(String glyphLabel, GlyphDescription description)
/*     */   {
/*  66 */     this.labelToMetric.put(glyphLabel, description);
/*     */   }
/*     */ 
/*     */   void initEncodingWithStandardEncoding()
/*     */   {
/*  71 */     this.labelToCid.put("/A", Integer.valueOf(65));
/*  72 */     this.labelToCid.put("/AE", Integer.valueOf(225));
/*  73 */     this.labelToCid.put("/B", Integer.valueOf(66));
/*  74 */     this.labelToCid.put("/C", Integer.valueOf(67));
/*  75 */     this.labelToCid.put("/D", Integer.valueOf(68));
/*  76 */     this.labelToCid.put("/E", Integer.valueOf(69));
/*  77 */     this.labelToCid.put("/F", Integer.valueOf(70));
/*  78 */     this.labelToCid.put("/G", Integer.valueOf(71));
/*  79 */     this.labelToCid.put("/H", Integer.valueOf(72));
/*  80 */     this.labelToCid.put("/I", Integer.valueOf(73));
/*  81 */     this.labelToCid.put("/J", Integer.valueOf(74));
/*  82 */     this.labelToCid.put("/K", Integer.valueOf(75));
/*  83 */     this.labelToCid.put("/L", Integer.valueOf(76));
/*  84 */     this.labelToCid.put("/Lslash", Integer.valueOf(232));
/*  85 */     this.labelToCid.put("/M", Integer.valueOf(77));
/*  86 */     this.labelToCid.put("/N", Integer.valueOf(78));
/*  87 */     this.labelToCid.put("/O", Integer.valueOf(79));
/*  88 */     this.labelToCid.put("/OE", Integer.valueOf(234));
/*  89 */     this.labelToCid.put("/Oslash", Integer.valueOf(233));
/*  90 */     this.labelToCid.put("/P", Integer.valueOf(80));
/*  91 */     this.labelToCid.put("/Q", Integer.valueOf(81));
/*  92 */     this.labelToCid.put("/R", Integer.valueOf(82));
/*  93 */     this.labelToCid.put("/S", Integer.valueOf(83));
/*  94 */     this.labelToCid.put("/T", Integer.valueOf(84));
/*  95 */     this.labelToCid.put("/U", Integer.valueOf(85));
/*  96 */     this.labelToCid.put("/V", Integer.valueOf(86));
/*  97 */     this.labelToCid.put("/W", Integer.valueOf(87));
/*  98 */     this.labelToCid.put("/X", Integer.valueOf(88));
/*  99 */     this.labelToCid.put("/Y", Integer.valueOf(89));
/* 100 */     this.labelToCid.put("/Z", Integer.valueOf(90));
/* 101 */     this.labelToCid.put("/a", Integer.valueOf(97));
/* 102 */     this.labelToCid.put("/acute", Integer.valueOf(194));
/* 103 */     this.labelToCid.put("/acute", Integer.valueOf(194));
/* 104 */     this.labelToCid.put("/ae", Integer.valueOf(241));
/* 105 */     this.labelToCid.put("/ampersand", Integer.valueOf(38));
/* 106 */     this.labelToCid.put("/asciicircum", Integer.valueOf(94));
/* 107 */     this.labelToCid.put("/asciitilde", Integer.valueOf(126));
/* 108 */     this.labelToCid.put("/asterisk", Integer.valueOf(42));
/* 109 */     this.labelToCid.put("/at", Integer.valueOf(64));
/* 110 */     this.labelToCid.put("/b", Integer.valueOf(98));
/* 111 */     this.labelToCid.put("/backslash", Integer.valueOf(92));
/* 112 */     this.labelToCid.put("/bar", Integer.valueOf(124));
/* 113 */     this.labelToCid.put("/braceleft", Integer.valueOf(123));
/* 114 */     this.labelToCid.put("/braceright", Integer.valueOf(125));
/* 115 */     this.labelToCid.put("/bracketleft", Integer.valueOf(91));
/* 116 */     this.labelToCid.put("/bracketright", Integer.valueOf(93));
/* 117 */     this.labelToCid.put("/breve", Integer.valueOf(198));
/* 118 */     this.labelToCid.put("/bullet", Integer.valueOf(183));
/* 119 */     this.labelToCid.put("/c", Integer.valueOf(99));
/* 120 */     this.labelToCid.put("/caron", Integer.valueOf(207));
/* 121 */     this.labelToCid.put("/cedilla", Integer.valueOf(203));
/* 122 */     this.labelToCid.put("/cent", Integer.valueOf(162));
/* 123 */     this.labelToCid.put("/circumflex", Integer.valueOf(195));
/* 124 */     this.labelToCid.put("/colon", Integer.valueOf(58));
/* 125 */     this.labelToCid.put("/comma", Integer.valueOf(44));
/* 126 */     this.labelToCid.put("/currency", Integer.valueOf(168));
/* 127 */     this.labelToCid.put("/d", Integer.valueOf(100));
/* 128 */     this.labelToCid.put("/dagger", Integer.valueOf(178));
/* 129 */     this.labelToCid.put("/daggerdbl", Integer.valueOf(179));
/* 130 */     this.labelToCid.put("/dieresis", Integer.valueOf(200));
/* 131 */     this.labelToCid.put("/dollar", Integer.valueOf(36));
/* 132 */     this.labelToCid.put("/dotaccent", Integer.valueOf(199));
/* 133 */     this.labelToCid.put("/dotlessi", Integer.valueOf(245));
/* 134 */     this.labelToCid.put("/e", Integer.valueOf(101));
/* 135 */     this.labelToCid.put("/eight", Integer.valueOf(56));
/* 136 */     this.labelToCid.put("/ellipsis", Integer.valueOf(274));
/* 137 */     this.labelToCid.put("/emdash", Integer.valueOf(208));
/* 138 */     this.labelToCid.put("/endash", Integer.valueOf(177));
/* 139 */     this.labelToCid.put("/equal", Integer.valueOf(61));
/* 140 */     this.labelToCid.put("/exclam", Integer.valueOf(33));
/* 141 */     this.labelToCid.put("/exclamdown", Integer.valueOf(161));
/* 142 */     this.labelToCid.put("/f", Integer.valueOf(102));
/* 143 */     this.labelToCid.put("/fi", Integer.valueOf(174));
/* 144 */     this.labelToCid.put("/five", Integer.valueOf(53));
/* 145 */     this.labelToCid.put("/fl", Integer.valueOf(175));
/* 146 */     this.labelToCid.put("/florin", Integer.valueOf(166));
/* 147 */     this.labelToCid.put("/four", Integer.valueOf(52));
/* 148 */     this.labelToCid.put("/fraction", Integer.valueOf(164));
/* 149 */     this.labelToCid.put("/g", Integer.valueOf(103));
/* 150 */     this.labelToCid.put("/germandbls", Integer.valueOf(251));
/* 151 */     this.labelToCid.put("/grave", Integer.valueOf(193));
/* 152 */     this.labelToCid.put("/greater", Integer.valueOf(62));
/* 153 */     this.labelToCid.put("/guillemotleft", Integer.valueOf(171));
/* 154 */     this.labelToCid.put("/guillemotright", Integer.valueOf(187));
/* 155 */     this.labelToCid.put("/guilsinglleft", Integer.valueOf(172));
/* 156 */     this.labelToCid.put("/guilsinglright", Integer.valueOf(173));
/* 157 */     this.labelToCid.put("/h", Integer.valueOf(104));
/* 158 */     this.labelToCid.put("/hungarumlaut", Integer.valueOf(205));
/* 159 */     this.labelToCid.put("/hyphen", Integer.valueOf(45));
/* 160 */     this.labelToCid.put("/i", Integer.valueOf(105));
/* 161 */     this.labelToCid.put("/j", Integer.valueOf(106));
/* 162 */     this.labelToCid.put("/k", Integer.valueOf(107));
/* 163 */     this.labelToCid.put("/l", Integer.valueOf(108));
/* 164 */     this.labelToCid.put("/less", Integer.valueOf(60));
/* 165 */     this.labelToCid.put("/lslash", Integer.valueOf(248));
/* 166 */     this.labelToCid.put("/m", Integer.valueOf(109));
/* 167 */     this.labelToCid.put("/macron", Integer.valueOf(197));
/* 168 */     this.labelToCid.put("/n", Integer.valueOf(110));
/* 169 */     this.labelToCid.put("/nine", Integer.valueOf(57));
/* 170 */     this.labelToCid.put("/numbersign", Integer.valueOf(35));
/* 171 */     this.labelToCid.put("/o", Integer.valueOf(111));
/* 172 */     this.labelToCid.put("/oe", Integer.valueOf(250));
/* 173 */     this.labelToCid.put("/ogonek", Integer.valueOf(206));
/* 174 */     this.labelToCid.put("/one", Integer.valueOf(49));
/* 175 */     this.labelToCid.put("/ordfeminine", Integer.valueOf(227));
/* 176 */     this.labelToCid.put("/ordmasculine", Integer.valueOf(235));
/* 177 */     this.labelToCid.put("/oslash", Integer.valueOf(249));
/* 178 */     this.labelToCid.put("/p", Integer.valueOf(112));
/* 179 */     this.labelToCid.put("/paragraph", Integer.valueOf(182));
/* 180 */     this.labelToCid.put("/parenleft", Integer.valueOf(40));
/* 181 */     this.labelToCid.put("/parenright", Integer.valueOf(41));
/* 182 */     this.labelToCid.put("/percent", Integer.valueOf(37));
/* 183 */     this.labelToCid.put("/period", Integer.valueOf(46));
/* 184 */     this.labelToCid.put("/periodcentered", Integer.valueOf(180));
/* 185 */     this.labelToCid.put("/perthousand", Integer.valueOf(189));
/* 186 */     this.labelToCid.put("/plus", Integer.valueOf(43));
/* 187 */     this.labelToCid.put("/q", Integer.valueOf(113));
/* 188 */     this.labelToCid.put("/question", Integer.valueOf(63));
/* 189 */     this.labelToCid.put("/questiondown", Integer.valueOf(191));
/* 190 */     this.labelToCid.put("/quotedbl", Integer.valueOf(34));
/* 191 */     this.labelToCid.put("/quotedblbase", Integer.valueOf(185));
/* 192 */     this.labelToCid.put("/quotedblleft", Integer.valueOf(170));
/* 193 */     this.labelToCid.put("/quotedblright", Integer.valueOf(186));
/* 194 */     this.labelToCid.put("/quoteleft", Integer.valueOf(96));
/* 195 */     this.labelToCid.put("/quoteright", Integer.valueOf(39));
/* 196 */     this.labelToCid.put("/quotesinglbase", Integer.valueOf(184));
/* 197 */     this.labelToCid.put("/quotesingle", Integer.valueOf(169));
/* 198 */     this.labelToCid.put("/r", Integer.valueOf(114));
/* 199 */     this.labelToCid.put("/ring", Integer.valueOf(202));
/* 200 */     this.labelToCid.put("/s", Integer.valueOf(115));
/* 201 */     this.labelToCid.put("/section", Integer.valueOf(167));
/* 202 */     this.labelToCid.put("/semicolon", Integer.valueOf(59));
/* 203 */     this.labelToCid.put("/seven", Integer.valueOf(55));
/* 204 */     this.labelToCid.put("/six", Integer.valueOf(54));
/* 205 */     this.labelToCid.put("/slash", Integer.valueOf(47));
/* 206 */     this.labelToCid.put("/space", Integer.valueOf(32));
/* 207 */     this.labelToCid.put("/sterling", Integer.valueOf(163));
/* 208 */     this.labelToCid.put("/t", Integer.valueOf(116));
/* 209 */     this.labelToCid.put("/three", Integer.valueOf(51));
/* 210 */     this.labelToCid.put("/tilde", Integer.valueOf(196));
/* 211 */     this.labelToCid.put("/two", Integer.valueOf(50));
/* 212 */     this.labelToCid.put("/u", Integer.valueOf(117));
/* 213 */     this.labelToCid.put("/underscore", Integer.valueOf(95));
/* 214 */     this.labelToCid.put("/v", Integer.valueOf(118));
/* 215 */     this.labelToCid.put("/w", Integer.valueOf(119));
/* 216 */     this.labelToCid.put("/x", Integer.valueOf(120));
/* 217 */     this.labelToCid.put("/y", Integer.valueOf(121));
/* 218 */     this.labelToCid.put("/yen", Integer.valueOf(165));
/* 219 */     this.labelToCid.put("/z", Integer.valueOf(122));
/* 220 */     this.labelToCid.put("/zero", Integer.valueOf(48));
/* 221 */     transafertLTOCinCTIL();
/*     */   }
/*     */ 
/*     */   private void transafertLTOCinCTIL()
/*     */   {
/* 226 */     for (Map.Entry entry : this.labelToCid.entrySet())
/*     */     {
/* 228 */       this.cidToLabel.put(entry.getValue(), entry.getKey());
/*     */     }
/*     */   }
/*     */ 
/*     */   void initEncodingWithISOLatin1Encoding()
/*     */   {
/* 234 */     this.labelToCid.put("/A", Integer.valueOf(65));
/* 235 */     this.labelToCid.put("/AE", Integer.valueOf(198));
/* 236 */     this.labelToCid.put("/Aacute", Integer.valueOf(193));
/* 237 */     this.labelToCid.put("/Acircumflex", Integer.valueOf(194));
/* 238 */     this.labelToCid.put("/Adieresis", Integer.valueOf(196));
/* 239 */     this.labelToCid.put("/Agrave", Integer.valueOf(192));
/* 240 */     this.labelToCid.put("/Aring", Integer.valueOf(197));
/* 241 */     this.labelToCid.put("/Atilde", Integer.valueOf(195));
/* 242 */     this.labelToCid.put("/B", Integer.valueOf(66));
/* 243 */     this.labelToCid.put("/C", Integer.valueOf(67));
/* 244 */     this.labelToCid.put("/Ccedilla", Integer.valueOf(199));
/* 245 */     this.labelToCid.put("/D", Integer.valueOf(68));
/* 246 */     this.labelToCid.put("/E", Integer.valueOf(69));
/* 247 */     this.labelToCid.put("/Eacute", Integer.valueOf(201));
/* 248 */     this.labelToCid.put("/Ecircumflex", Integer.valueOf(202));
/* 249 */     this.labelToCid.put("/Edieresis", Integer.valueOf(203));
/* 250 */     this.labelToCid.put("/Egrave", Integer.valueOf(200));
/* 251 */     this.labelToCid.put("/Eth", Integer.valueOf(208));
/* 252 */     this.labelToCid.put("/F", Integer.valueOf(70));
/* 253 */     this.labelToCid.put("/G", Integer.valueOf(71));
/* 254 */     this.labelToCid.put("/H", Integer.valueOf(72));
/* 255 */     this.labelToCid.put("/I", Integer.valueOf(73));
/* 256 */     this.labelToCid.put("/Iacute", Integer.valueOf(205));
/* 257 */     this.labelToCid.put("/Icircumflex", Integer.valueOf(206));
/* 258 */     this.labelToCid.put("/Idieresis", Integer.valueOf(207));
/* 259 */     this.labelToCid.put("/Igrave", Integer.valueOf(204));
/* 260 */     this.labelToCid.put("/J", Integer.valueOf(74));
/* 261 */     this.labelToCid.put("/K", Integer.valueOf(75));
/* 262 */     this.labelToCid.put("/L", Integer.valueOf(76));
/* 263 */     this.labelToCid.put("/M", Integer.valueOf(77));
/* 264 */     this.labelToCid.put("/N", Integer.valueOf(78));
/* 265 */     this.labelToCid.put("/Ntilde", Integer.valueOf(209));
/* 266 */     this.labelToCid.put("/O", Integer.valueOf(79));
/* 267 */     this.labelToCid.put("/Oacute", Integer.valueOf(211));
/* 268 */     this.labelToCid.put("/Ocircumflex", Integer.valueOf(212));
/* 269 */     this.labelToCid.put("/Odieresis", Integer.valueOf(214));
/* 270 */     this.labelToCid.put("/Ograve", Integer.valueOf(210));
/* 271 */     this.labelToCid.put("/Oslash", Integer.valueOf(216));
/* 272 */     this.labelToCid.put("/Otilde", Integer.valueOf(213));
/* 273 */     this.labelToCid.put("/P", Integer.valueOf(80));
/* 274 */     this.labelToCid.put("/Q", Integer.valueOf(81));
/* 275 */     this.labelToCid.put("/R", Integer.valueOf(82));
/* 276 */     this.labelToCid.put("/S", Integer.valueOf(83));
/* 277 */     this.labelToCid.put("/T", Integer.valueOf(84));
/* 278 */     this.labelToCid.put("/Thorn", Integer.valueOf(222));
/* 279 */     this.labelToCid.put("/U", Integer.valueOf(85));
/* 280 */     this.labelToCid.put("/Uacute", Integer.valueOf(218));
/* 281 */     this.labelToCid.put("/Ucircumflex", Integer.valueOf(219));
/* 282 */     this.labelToCid.put("/Udieresis", Integer.valueOf(220));
/* 283 */     this.labelToCid.put("/Ugrave", Integer.valueOf(217));
/* 284 */     this.labelToCid.put("/V", Integer.valueOf(86));
/* 285 */     this.labelToCid.put("/W", Integer.valueOf(87));
/* 286 */     this.labelToCid.put("/X", Integer.valueOf(88));
/* 287 */     this.labelToCid.put("/Y", Integer.valueOf(89));
/* 288 */     this.labelToCid.put("/Yacute", Integer.valueOf(221));
/* 289 */     this.labelToCid.put("/Z", Integer.valueOf(90));
/* 290 */     this.labelToCid.put("/a", Integer.valueOf(97));
/* 291 */     this.labelToCid.put("/aacute", Integer.valueOf(225));
/* 292 */     this.labelToCid.put("/acircumflex", Integer.valueOf(226));
/* 293 */     this.labelToCid.put("/acute", Integer.valueOf(146));
/* 294 */     this.labelToCid.put("/acute", Integer.valueOf(180));
/* 295 */     this.labelToCid.put("/adieresis", Integer.valueOf(228));
/* 296 */     this.labelToCid.put("/ae", Integer.valueOf(230));
/* 297 */     this.labelToCid.put("/agrave", Integer.valueOf(224));
/* 298 */     this.labelToCid.put("/ampersand", Integer.valueOf(38));
/* 299 */     this.labelToCid.put("/aring", Integer.valueOf(229));
/* 300 */     this.labelToCid.put("/asciicircum", Integer.valueOf(94));
/* 301 */     this.labelToCid.put("/asciitilde", Integer.valueOf(126));
/* 302 */     this.labelToCid.put("/asterisk", Integer.valueOf(42));
/* 303 */     this.labelToCid.put("/at", Integer.valueOf(64));
/* 304 */     this.labelToCid.put("/atilde", Integer.valueOf(227));
/* 305 */     this.labelToCid.put("/b", Integer.valueOf(98));
/* 306 */     this.labelToCid.put("/backslash", Integer.valueOf(92));
/* 307 */     this.labelToCid.put("/bar", Integer.valueOf(124));
/* 308 */     this.labelToCid.put("/braceleft", Integer.valueOf(123));
/* 309 */     this.labelToCid.put("/braceright", Integer.valueOf(125));
/* 310 */     this.labelToCid.put("/bracketleft", Integer.valueOf(91));
/* 311 */     this.labelToCid.put("/bracketright", Integer.valueOf(93));
/* 312 */     this.labelToCid.put("/breve", Integer.valueOf(150));
/* 313 */     this.labelToCid.put("/brokenbar", Integer.valueOf(166));
/* 314 */     this.labelToCid.put("/c", Integer.valueOf(99));
/* 315 */     this.labelToCid.put("/caron", Integer.valueOf(159));
/* 316 */     this.labelToCid.put("/ccedilla", Integer.valueOf(231));
/* 317 */     this.labelToCid.put("/cedilla", Integer.valueOf(184));
/* 318 */     this.labelToCid.put("/cent", Integer.valueOf(162));
/* 319 */     this.labelToCid.put("/circumflex", Integer.valueOf(147));
/* 320 */     this.labelToCid.put("/colon", Integer.valueOf(58));
/* 321 */     this.labelToCid.put("/comma", Integer.valueOf(44));
/* 322 */     this.labelToCid.put("/copyright", Integer.valueOf(169));
/* 323 */     this.labelToCid.put("/currency", Integer.valueOf(164));
/* 324 */     this.labelToCid.put("/d", Integer.valueOf(100));
/* 325 */     this.labelToCid.put("/degree", Integer.valueOf(176));
/* 326 */     this.labelToCid.put("/dieresis", Integer.valueOf(168));
/* 327 */     this.labelToCid.put("/divide", Integer.valueOf(247));
/* 328 */     this.labelToCid.put("/dollar", Integer.valueOf(36));
/* 329 */     this.labelToCid.put("/dotaccent", Integer.valueOf(151));
/* 330 */     this.labelToCid.put("/dotlessi", Integer.valueOf(144));
/* 331 */     this.labelToCid.put("/e", Integer.valueOf(101));
/* 332 */     this.labelToCid.put("/eacute", Integer.valueOf(233));
/* 333 */     this.labelToCid.put("/ecircumflex", Integer.valueOf(234));
/* 334 */     this.labelToCid.put("/edieresis", Integer.valueOf(235));
/* 335 */     this.labelToCid.put("/egrave", Integer.valueOf(232));
/* 336 */     this.labelToCid.put("/eight", Integer.valueOf(56));
/* 337 */     this.labelToCid.put("/equal", Integer.valueOf(61));
/* 338 */     this.labelToCid.put("/eth", Integer.valueOf(240));
/* 339 */     this.labelToCid.put("/exclam", Integer.valueOf(33));
/* 340 */     this.labelToCid.put("/exclamdown", Integer.valueOf(161));
/* 341 */     this.labelToCid.put("/f", Integer.valueOf(102));
/* 342 */     this.labelToCid.put("/five", Integer.valueOf(53));
/* 343 */     this.labelToCid.put("/four", Integer.valueOf(52));
/* 344 */     this.labelToCid.put("/g", Integer.valueOf(103));
/* 345 */     this.labelToCid.put("/germandbls", Integer.valueOf(223));
/* 346 */     this.labelToCid.put("/grave", Integer.valueOf(145));
/* 347 */     this.labelToCid.put("/greater", Integer.valueOf(62));
/* 348 */     this.labelToCid.put("/guillemotleft", Integer.valueOf(171));
/* 349 */     this.labelToCid.put("/guillemotright", Integer.valueOf(187));
/* 350 */     this.labelToCid.put("/h", Integer.valueOf(104));
/* 351 */     this.labelToCid.put("/hungarumlaut", Integer.valueOf(157));
/* 352 */     this.labelToCid.put("/hyphen", Integer.valueOf(173));
/* 353 */     this.labelToCid.put("/i", Integer.valueOf(105));
/* 354 */     this.labelToCid.put("/iacute", Integer.valueOf(237));
/* 355 */     this.labelToCid.put("/icircumflex", Integer.valueOf(238));
/* 356 */     this.labelToCid.put("/idieresis", Integer.valueOf(239));
/* 357 */     this.labelToCid.put("/igrave", Integer.valueOf(236));
/* 358 */     this.labelToCid.put("/j", Integer.valueOf(106));
/* 359 */     this.labelToCid.put("/k", Integer.valueOf(107));
/* 360 */     this.labelToCid.put("/l", Integer.valueOf(108));
/* 361 */     this.labelToCid.put("/less", Integer.valueOf(60));
/* 362 */     this.labelToCid.put("/logicalnot", Integer.valueOf(172));
/* 363 */     this.labelToCid.put("/m", Integer.valueOf(109));
/* 364 */     this.labelToCid.put("/macron", Integer.valueOf(175));
/* 365 */     this.labelToCid.put("/minus", Integer.valueOf(45));
/* 366 */     this.labelToCid.put("/mu", Integer.valueOf(181));
/* 367 */     this.labelToCid.put("/multiply", Integer.valueOf(215));
/* 368 */     this.labelToCid.put("/n", Integer.valueOf(110));
/* 369 */     this.labelToCid.put("/nine", Integer.valueOf(57));
/* 370 */     this.labelToCid.put("/ntilde", Integer.valueOf(241));
/* 371 */     this.labelToCid.put("/numbersign", Integer.valueOf(35));
/* 372 */     this.labelToCid.put("/o", Integer.valueOf(111));
/* 373 */     this.labelToCid.put("/oacute", Integer.valueOf(243));
/* 374 */     this.labelToCid.put("/ocircumflex", Integer.valueOf(244));
/* 375 */     this.labelToCid.put("/odieresis", Integer.valueOf(246));
/* 376 */     this.labelToCid.put("/ogonek", Integer.valueOf(158));
/* 377 */     this.labelToCid.put("/ograve", Integer.valueOf(242));
/* 378 */     this.labelToCid.put("/one", Integer.valueOf(49));
/* 379 */     this.labelToCid.put("/onehalf", Integer.valueOf(189));
/* 380 */     this.labelToCid.put("/onequarter", Integer.valueOf(188));
/* 381 */     this.labelToCid.put("/onesuperior", Integer.valueOf(185));
/* 382 */     this.labelToCid.put("/ordfeminine", Integer.valueOf(170));
/* 383 */     this.labelToCid.put("/ordmasculine", Integer.valueOf(186));
/* 384 */     this.labelToCid.put("/oslash", Integer.valueOf(248));
/* 385 */     this.labelToCid.put("/otilde", Integer.valueOf(245));
/* 386 */     this.labelToCid.put("/p", Integer.valueOf(112));
/* 387 */     this.labelToCid.put("/paragraph", Integer.valueOf(182));
/* 388 */     this.labelToCid.put("/parenleft", Integer.valueOf(40));
/* 389 */     this.labelToCid.put("/parenright", Integer.valueOf(41));
/* 390 */     this.labelToCid.put("/percent", Integer.valueOf(37));
/* 391 */     this.labelToCid.put("/period", Integer.valueOf(46));
/* 392 */     this.labelToCid.put("/periodcentered", Integer.valueOf(183));
/* 393 */     this.labelToCid.put("/plus", Integer.valueOf(43));
/* 394 */     this.labelToCid.put("/plusminus", Integer.valueOf(177));
/* 395 */     this.labelToCid.put("/q", Integer.valueOf(113));
/* 396 */     this.labelToCid.put("/question", Integer.valueOf(63));
/* 397 */     this.labelToCid.put("/questiondown", Integer.valueOf(191));
/* 398 */     this.labelToCid.put("/quotedbl", Integer.valueOf(34));
/* 399 */     this.labelToCid.put("/quoteleft", Integer.valueOf(96));
/* 400 */     this.labelToCid.put("/quoteright", Integer.valueOf(39));
/* 401 */     this.labelToCid.put("/r", Integer.valueOf(114));
/* 402 */     this.labelToCid.put("/registered", Integer.valueOf(174));
/* 403 */     this.labelToCid.put("/ring", Integer.valueOf(154));
/* 404 */     this.labelToCid.put("/s", Integer.valueOf(115));
/* 405 */     this.labelToCid.put("/section", Integer.valueOf(167));
/* 406 */     this.labelToCid.put("/semicolon", Integer.valueOf(59));
/* 407 */     this.labelToCid.put("/seven", Integer.valueOf(55));
/* 408 */     this.labelToCid.put("/six", Integer.valueOf(54));
/* 409 */     this.labelToCid.put("/slash", Integer.valueOf(47));
/* 410 */     this.labelToCid.put("/space", Integer.valueOf(32));
/* 411 */     this.labelToCid.put("/sterling", Integer.valueOf(163));
/* 412 */     this.labelToCid.put("/t", Integer.valueOf(116));
/* 413 */     this.labelToCid.put("/thorn", Integer.valueOf(254));
/* 414 */     this.labelToCid.put("/three", Integer.valueOf(51));
/* 415 */     this.labelToCid.put("/threequarters", Integer.valueOf(190));
/* 416 */     this.labelToCid.put("/threesuperior", Integer.valueOf(179));
/* 417 */     this.labelToCid.put("/tilde", Integer.valueOf(148));
/* 418 */     this.labelToCid.put("/two", Integer.valueOf(50));
/* 419 */     this.labelToCid.put("/twosuperior", Integer.valueOf(178));
/* 420 */     this.labelToCid.put("/u", Integer.valueOf(117));
/* 421 */     this.labelToCid.put("/uacute", Integer.valueOf(250));
/* 422 */     this.labelToCid.put("/ucircumflex", Integer.valueOf(251));
/* 423 */     this.labelToCid.put("/udieresis", Integer.valueOf(252));
/* 424 */     this.labelToCid.put("/ugrave", Integer.valueOf(249));
/* 425 */     this.labelToCid.put("/underscore", Integer.valueOf(95));
/* 426 */     this.labelToCid.put("/v", Integer.valueOf(118));
/* 427 */     this.labelToCid.put("/w", Integer.valueOf(119));
/* 428 */     this.labelToCid.put("/x", Integer.valueOf(120));
/* 429 */     this.labelToCid.put("/y", Integer.valueOf(121));
/* 430 */     this.labelToCid.put("/yacute", Integer.valueOf(253));
/* 431 */     this.labelToCid.put("/ydieresis", Integer.valueOf(255));
/* 432 */     this.labelToCid.put("/yen", Integer.valueOf(165));
/* 433 */     this.labelToCid.put("/z", Integer.valueOf(122));
/* 434 */     this.labelToCid.put("/zero", Integer.valueOf(48));
/* 435 */     transafertLTOCinCTIL();
/*     */   }
/*     */ 
/*     */   public int getWidthOfCID(int cid) throws GlyphException
/*     */   {
/* 440 */     String label = getLabelAsName(cid);
/*     */ 
/* 442 */     GlyphDescription glyph = (GlyphDescription)this.labelToMetric.get(label);
/* 443 */     if (glyph != null)
/*     */     {
/* 445 */       return glyph.getGlyphWidth();
/*     */     }
/*     */ 
/* 448 */     throw new GlyphException("3.3.1", cid, "Missing glyph for the CID " + cid);
/*     */   }
/*     */ 
/*     */   private String getLabelAsName(int cid)
/*     */   {
/* 453 */     String label = null;
/*     */     try
/*     */     {
/* 457 */       label = this.encoding.getName(cid);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 461 */       label = (String)this.cidToLabel.get(Integer.valueOf(cid));
/* 462 */       if (label == null)
/*     */       {
/* 464 */         label = "/.notdef";
/*     */       }
/*     */     }
/*     */ 
/* 468 */     return '/' + label;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.font.util.Type1
 * JD-Core Version:    0.6.2
 */