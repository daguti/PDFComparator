/*     */ package com.itextpdf.text.pdf.languages;
/*     */ 
/*     */ import com.itextpdf.text.pdf.BidiLine;
/*     */ import com.itextpdf.text.pdf.BidiOrder;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class ArabicLigaturizer
/*     */   implements LanguageProcessor
/*     */ {
/*  61 */   private static HashMap<Character, char[]> maptable = new HashMap();
/*     */   private static final char ALEF = 'ا';
/*     */   private static final char ALEFHAMZA = 'أ';
/*     */   private static final char ALEFHAMZABELOW = 'إ';
/*     */   private static final char ALEFMADDA = 'آ';
/*     */   private static final char LAM = 'ل';
/*     */   private static final char HAMZA = 'ء';
/*     */   private static final char TATWEEL = 'ـ';
/*     */   private static final char ZWJ = '‍';
/*     */   private static final char HAMZAABOVE = 'ٔ';
/*     */   private static final char HAMZABELOW = 'ٕ';
/*     */   private static final char WAWHAMZA = 'ؤ';
/*     */   private static final char YEHHAMZA = 'ئ';
/*     */   private static final char WAW = 'و';
/*     */   private static final char ALEFMAKSURA = 'ى';
/*     */   private static final char YEH = 'ي';
/*     */   private static final char FARSIYEH = 'ی';
/*     */   private static final char SHADDA = 'ّ';
/*     */   private static final char KASRA = 'ِ';
/*     */   private static final char FATHA = 'َ';
/*     */   private static final char DAMMA = 'ُ';
/*     */   private static final char MADDA = 'ٓ';
/*     */   private static final char LAM_ALEF = 'ﻻ';
/*     */   private static final char LAM_ALEFHAMZA = 'ﻷ';
/*     */   private static final char LAM_ALEFHAMZABELOW = 'ﻹ';
/*     */   private static final char LAM_ALEFMADDA = 'ﻵ';
/* 607 */   private static final char[][] chartable = { { 'ء', 65152 }, { 'آ', 65153, 65154 }, { 'أ', 65155, 65156 }, { 'ؤ', 65157, 65158 }, { 'إ', 65159, 65160 }, { 'ئ', 65161, 65162, 65163, 65164 }, { 'ا', 65165, 65166 }, { 'ب', 65167, 65168, 65169, 65170 }, { 'ة', 65171, 65172 }, { 'ت', 65173, 65174, 65175, 65176 }, { 'ث', 65177, 65178, 65179, 65180 }, { 'ج', 65181, 65182, 65183, 65184 }, { 'ح', 65185, 65186, 65187, 65188 }, { 'خ', 65189, 65190, 65191, 65192 }, { 'د', 65193, 65194 }, { 'ذ', 65195, 65196 }, { 'ر', 65197, 65198 }, { 'ز', 65199, 65200 }, { 'س', 65201, 65202, 65203, 65204 }, { 'ش', 65205, 65206, 65207, 65208 }, { 'ص', 65209, 65210, 65211, 65212 }, { 'ض', 65213, 65214, 65215, 65216 }, { 'ط', 65217, 65218, 65219, 65220 }, { 'ظ', 65221, 65222, 65223, 65224 }, { 'ع', 65225, 65226, 65227, 65228 }, { 'غ', 65229, 65230, 65231, 65232 }, { 'ـ', 'ـ', 'ـ', 'ـ', 'ـ' }, { 'ف', 65233, 65234, 65235, 65236 }, { 'ق', 65237, 65238, 65239, 65240 }, { 'ك', 65241, 65242, 65243, 65244 }, { 'ل', 65245, 65246, 65247, 65248 }, { 'م', 65249, 65250, 65251, 65252 }, { 'ن', 65253, 65254, 65255, 65256 }, { 'ه', 65257, 65258, 65259, 65260 }, { 'و', 65261, 65262 }, { 'ى', 65263, 65264, 64488, 64489 }, { 'ي', 65265, 65266, 65267, 65268 }, { 'ٱ', 64336, 64337 }, { 'ٹ', 64358, 64359, 64360, 64361 }, { 'ٺ', 64350, 64351, 64352, 64353 }, { 'ٻ', 64338, 64339, 64340, 64341 }, { 'پ', 64342, 64343, 64344, 64345 }, { 'ٿ', 64354, 64355, 64356, 64357 }, { 'ڀ', 64346, 64347, 64348, 64349 }, { 'ڃ', 64374, 64375, 64376, 64377 }, { 'ڄ', 64370, 64371, 64372, 64373 }, { 'چ', 64378, 64379, 64380, 64381 }, { 'ڇ', 64382, 64383, 64384, 64385 }, { 'ڈ', 64392, 64393 }, { 'ڌ', 64388, 64389 }, { 'ڍ', 64386, 64387 }, { 'ڎ', 64390, 64391 }, { 'ڑ', 64396, 64397 }, { 'ژ', 64394, 64395 }, { 'ڤ', 64362, 64363, 64364, 64365 }, { 'ڦ', 64366, 64367, 64368, 64369 }, { 'ک', 64398, 64399, 64400, 64401 }, { 'ڭ', 64467, 64468, 64469, 64470 }, { 'گ', 64402, 64403, 64404, 64405 }, { 'ڱ', 64410, 64411, 64412, 64413 }, { 'ڳ', 64406, 64407, 64408, 64409 }, { 'ں', 64414, 64415 }, { 'ڻ', 64416, 64417, 64418, 64419 }, { 'ھ', 64426, 64427, 64428, 64429 }, { 'ۀ', 64420, 64421 }, { 'ہ', 64422, 64423, 64424, 64425 }, { 'ۅ', 64480, 64481 }, { 'ۆ', 64473, 64474 }, { 'ۇ', 64471, 64472 }, { 'ۈ', 64475, 64476 }, { 'ۉ', 64482, 64483 }, { 'ۋ', 64478, 64479 }, { 'ی', 64508, 64509, 64510, 64511 }, { 'ې', 64484, 64485, 64486, 64487 }, { 'ے', 64430, 64431 }, { 'ۓ', 64432, 64433 } };
/*     */   public static final int ar_nothing = 0;
/*     */   public static final int ar_novowel = 1;
/*     */   public static final int ar_composedtashkeel = 4;
/*     */   public static final int ar_lig = 8;
/*     */   public static final int DIGITS_EN2AN = 32;
/*     */   public static final int DIGITS_AN2EN = 64;
/*     */   public static final int DIGITS_EN2AN_INIT_LR = 96;
/*     */   public static final int DIGITS_EN2AN_INIT_AL = 128;
/*     */   private static final int DIGITS_RESERVED = 160;
/*     */   public static final int DIGITS_MASK = 224;
/*     */   public static final int DIGIT_TYPE_AN = 0;
/*     */   public static final int DIGIT_TYPE_AN_EXTENDED = 256;
/*     */   public static final int DIGIT_TYPE_MASK = 256;
/* 754 */   protected int options = 0;
/* 755 */   protected int runDirection = 3;
/*     */ 
/*     */   static boolean isVowel(char s)
/*     */   {
/*  64 */     return ((s >= 'ً') && (s <= 'ٕ')) || (s == 'ٰ');
/*     */   }
/*     */ 
/*     */   static char charshape(char s, int which)
/*     */   {
/*  70 */     if ((s >= 'ء') && (s <= 'ۓ')) {
/*  71 */       char[] c = (char[])maptable.get(Character.valueOf(s));
/*  72 */       if (c != null)
/*  73 */         return c[(which + 1)];
/*     */     }
/*  75 */     else if ((s >= 65269) && (s <= 65275)) {
/*  76 */       return (char)(s + which);
/*  77 */     }return s;
/*     */   }
/*     */ 
/*     */   static int shapecount(char s) {
/*  81 */     if ((s >= 'ء') && (s <= 'ۓ') && (!isVowel(s))) {
/*  82 */       char[] c = (char[])maptable.get(Character.valueOf(s));
/*  83 */       if (c != null)
/*  84 */         return c.length - 1;
/*     */     }
/*  86 */     else if (s == '‍') {
/*  87 */       return 4;
/*     */     }
/*  89 */     return 1;
/*     */   }
/*     */ 
/*     */   static int ligature(char newchar, charstruct oldchar)
/*     */   {
/*  94 */     int retval = 0;
/*     */ 
/*  96 */     if (oldchar.basechar == 0)
/*  97 */       return 0;
/*  98 */     if (isVowel(newchar)) {
/*  99 */       retval = 1;
/* 100 */       if ((oldchar.vowel != 0) && (newchar != 'ّ')) {
/* 101 */         retval = 2;
/*     */       }
/* 103 */       switch (newchar) {
/*     */       case 'ّ':
/* 105 */         if (oldchar.mark1 == 0) {
/* 106 */           oldchar.mark1 = 'ّ';
/*     */         }
/*     */         else {
/* 109 */           return 0;
/*     */         }
/*     */         break;
/*     */       case 'ٕ':
/* 113 */         switch (oldchar.basechar) {
/*     */         case 'ا':
/* 115 */           oldchar.basechar = 'إ';
/* 116 */           retval = 2;
/* 117 */           break;
/*     */         case 'ﻻ':
/* 119 */           oldchar.basechar = 65273;
/* 120 */           retval = 2;
/* 121 */           break;
/*     */         default:
/* 123 */           oldchar.mark1 = 'ٕ';
/* 124 */         }break;
/*     */       case 'ٔ':
/* 128 */         switch (oldchar.basechar) {
/*     */         case 'ا':
/* 130 */           oldchar.basechar = 'أ';
/* 131 */           retval = 2;
/* 132 */           break;
/*     */         case 'ﻻ':
/* 134 */           oldchar.basechar = 65271;
/* 135 */           retval = 2;
/* 136 */           break;
/*     */         case 'و':
/* 138 */           oldchar.basechar = 'ؤ';
/* 139 */           retval = 2;
/* 140 */           break;
/*     */         case 'ى':
/*     */         case 'ي':
/*     */         case 'ی':
/* 144 */           oldchar.basechar = 'ئ';
/* 145 */           retval = 2;
/* 146 */           break;
/*     */         default:
/* 148 */           oldchar.mark1 = 'ٔ';
/* 149 */         }break;
/*     */       case 'ٓ':
/* 153 */         switch (oldchar.basechar) {
/*     */         case 'ا':
/* 155 */           oldchar.basechar = 'آ';
/* 156 */           retval = 2;
/*     */         }
/*     */ 
/* 159 */         break;
/*     */       case 'ْ':
/*     */       default:
/* 161 */         oldchar.vowel = newchar;
/*     */       }
/*     */ 
/* 164 */       if (retval == 1) {
/* 165 */         oldchar.lignum += 1;
/*     */       }
/* 167 */       return retval;
/*     */     }
/* 169 */     if (oldchar.vowel != 0) {
/* 170 */       return 0;
/*     */     }
/*     */ 
/* 173 */     switch (oldchar.basechar) {
/*     */     case 'ل':
/* 175 */       switch (newchar) {
/*     */       case 'ا':
/* 177 */         oldchar.basechar = 65275;
/* 178 */         oldchar.numshapes = 2;
/* 179 */         retval = 3;
/* 180 */         break;
/*     */       case 'أ':
/* 182 */         oldchar.basechar = 65271;
/* 183 */         oldchar.numshapes = 2;
/* 184 */         retval = 3;
/* 185 */         break;
/*     */       case 'إ':
/* 187 */         oldchar.basechar = 65273;
/* 188 */         oldchar.numshapes = 2;
/* 189 */         retval = 3;
/* 190 */         break;
/*     */       case 'آ':
/* 192 */         oldchar.basechar = 65269;
/* 193 */         oldchar.numshapes = 2;
/* 194 */         retval = 3;
/*     */       case 'ؤ':
/*     */       case 'ئ':
/* 197 */       }break;
/*     */     case '\000':
/* 199 */       oldchar.basechar = newchar;
/* 200 */       oldchar.numshapes = shapecount(newchar);
/* 201 */       retval = 1;
/*     */     }
/*     */ 
/* 204 */     return retval;
/*     */   }
/*     */ 
/*     */   static void copycstostring(StringBuffer string, charstruct s, int level)
/*     */   {
/* 209 */     if (s.basechar == 0) {
/* 210 */       return;
/*     */     }
/* 212 */     string.append(s.basechar);
/* 213 */     s.lignum -= 1;
/* 214 */     if (s.mark1 != 0) {
/* 215 */       if ((level & 0x1) == 0) {
/* 216 */         string.append(s.mark1);
/* 217 */         s.lignum -= 1;
/*     */       }
/*     */       else {
/* 220 */         s.lignum -= 1;
/*     */       }
/*     */     }
/* 223 */     if (s.vowel != 0)
/* 224 */       if ((level & 0x1) == 0) {
/* 225 */         string.append(s.vowel);
/* 226 */         s.lignum -= 1;
/*     */       }
/*     */       else {
/* 229 */         s.lignum -= 1;
/*     */       }
/*     */   }
/*     */ 
/*     */   static void doublelig(StringBuffer string, int level)
/*     */   {
/*     */     int len;
/* 245 */     int olen = len = string.length();
/* 246 */     int j = 0; int si = 1;
/*     */ 
/* 249 */     while (si < olen) {
/* 250 */       char lapresult = '\000';
/* 251 */       if ((level & 0x4) != 0) {
/* 252 */         switch (string.charAt(j)) {
/*     */         case 'ّ':
/* 254 */           switch (string.charAt(si)) {
/*     */           case 'ِ':
/* 256 */             lapresult = 64610;
/* 257 */             break;
/*     */           case 'َ':
/* 259 */             lapresult = 64608;
/* 260 */             break;
/*     */           case 'ُ':
/* 262 */             lapresult = 64609;
/* 263 */             break;
/*     */           case 'ٌ':
/* 265 */             lapresult = 64606;
/* 266 */             break;
/*     */           case 'ٍ':
/* 268 */             lapresult = 64607;
/*     */           }
/*     */ 
/* 271 */           break;
/*     */         case 'ِ':
/* 273 */           if (string.charAt(si) == 'ّ')
/* 274 */             lapresult = 64610; break;
/*     */         case 'َ':
/* 277 */           if (string.charAt(si) == 'ّ')
/* 278 */             lapresult = 64608; break;
/*     */         case 'ُ':
/* 281 */           if (string.charAt(si) == 'ّ') {
/* 282 */             lapresult = 64609;
/*     */           }
/*     */           break;
/*     */         }
/*     */       }
/* 287 */       if ((level & 0x8) != 0) {
/* 288 */         switch (string.charAt(j)) {
/*     */         case 'ﻟ':
/* 290 */           switch (string.charAt(si)) {
/*     */           case 'ﺞ':
/* 292 */             lapresult = 64575;
/* 293 */             break;
/*     */           case 'ﺠ':
/* 295 */             lapresult = 64713;
/* 296 */             break;
/*     */           case 'ﺢ':
/* 298 */             lapresult = 64576;
/* 299 */             break;
/*     */           case 'ﺤ':
/* 301 */             lapresult = 64714;
/* 302 */             break;
/*     */           case 'ﺦ':
/* 304 */             lapresult = 64577;
/* 305 */             break;
/*     */           case 'ﺨ':
/* 307 */             lapresult = 64715;
/* 308 */             break;
/*     */           case 'ﻢ':
/* 310 */             lapresult = 64578;
/* 311 */             break;
/*     */           case 'ﻤ':
/* 313 */             lapresult = 64716;
/*     */           }
/*     */ 
/* 316 */           break;
/*     */         case 'ﺗ':
/* 318 */           switch (string.charAt(si)) {
/*     */           case 'ﺠ':
/* 320 */             lapresult = 64673;
/* 321 */             break;
/*     */           case 'ﺤ':
/* 323 */             lapresult = 64674;
/* 324 */             break;
/*     */           case 'ﺨ':
/* 326 */             lapresult = 64675;
/*     */           }
/*     */ 
/* 329 */           break;
/*     */         case 'ﺑ':
/* 331 */           switch (string.charAt(si)) {
/*     */           case 'ﺠ':
/* 333 */             lapresult = 64668;
/* 334 */             break;
/*     */           case 'ﺤ':
/* 336 */             lapresult = 64669;
/* 337 */             break;
/*     */           case 'ﺨ':
/* 339 */             lapresult = 64670;
/*     */           }
/*     */ 
/* 342 */           break;
/*     */         case 'ﻧ':
/* 344 */           switch (string.charAt(si)) {
/*     */           case 'ﺠ':
/* 346 */             lapresult = 64722;
/* 347 */             break;
/*     */           case 'ﺤ':
/* 349 */             lapresult = 64723;
/* 350 */             break;
/*     */           case 'ﺨ':
/* 352 */             lapresult = 64724;
/*     */           }
/*     */ 
/* 355 */           break;
/*     */         case 'ﻨ':
/* 358 */           switch (string.charAt(si)) {
/*     */           case 'ﺮ':
/* 360 */             lapresult = 64650;
/* 361 */             break;
/*     */           case 'ﺰ':
/* 363 */             lapresult = 64651;
/*     */           }
/*     */ 
/* 366 */           break;
/*     */         case 'ﻣ':
/* 368 */           switch (string.charAt(si)) {
/*     */           case 'ﺠ':
/* 370 */             lapresult = 64718;
/* 371 */             break;
/*     */           case 'ﺤ':
/* 373 */             lapresult = 64719;
/* 374 */             break;
/*     */           case 'ﺨ':
/* 376 */             lapresult = 64720;
/* 377 */             break;
/*     */           case 'ﻤ':
/* 379 */             lapresult = 64721;
/*     */           }
/*     */ 
/* 382 */           break;
/*     */         case 'ﻓ':
/* 385 */           switch (string.charAt(si)) {
/*     */           case 'ﻲ':
/* 387 */             lapresult = 64562;
/*     */           }
/*     */ 
/* 390 */           break;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 396 */       if (lapresult != 0) {
/* 397 */         string.setCharAt(j, lapresult);
/* 398 */         len--;
/* 399 */         si++;
/*     */       }
/*     */       else
/*     */       {
/* 403 */         j++;
/* 404 */         string.setCharAt(j, string.charAt(si));
/* 405 */         si++;
/*     */       }
/*     */     }
/* 408 */     string.setLength(len);
/*     */   }
/*     */ 
/*     */   static boolean connects_to_left(charstruct a) {
/* 412 */     return a.numshapes > 2;
/*     */   }
/*     */ 
/*     */   static void shape(char[] text, StringBuffer string, int level)
/*     */   {
/* 427 */     int p = 0;
/* 428 */     charstruct oldchar = new charstruct();
/* 429 */     charstruct curchar = new charstruct();
/* 430 */     while (p < text.length) {
/* 431 */       char nextletter = text[(p++)];
/*     */ 
/* 434 */       int join = ligature(nextletter, curchar);
/* 435 */       if (join == 0) {
/* 436 */         int nc = shapecount(nextletter);
/*     */         int which;
/*     */         int which;
/* 438 */         if (nc == 1) {
/* 439 */           which = 0;
/*     */         }
/*     */         else {
/* 442 */           which = 2;
/*     */         }
/* 444 */         if (connects_to_left(oldchar)) {
/* 445 */           which++;
/*     */         }
/*     */ 
/* 448 */         which %= curchar.numshapes;
/* 449 */         curchar.basechar = charshape(curchar.basechar, which);
/*     */ 
/* 452 */         copycstostring(string, oldchar, level);
/* 453 */         oldchar = curchar;
/*     */ 
/* 456 */         curchar = new charstruct();
/* 457 */         curchar.basechar = nextletter;
/* 458 */         curchar.numshapes = nc;
/* 459 */         curchar.lignum += 1;
/*     */       }
/* 462 */       else if (join != 1);
/*     */     }
/*     */     int which;
/*     */     int which;
/* 472 */     if (connects_to_left(oldchar))
/* 473 */       which = 1;
/*     */     else
/* 475 */       which = 0;
/* 476 */     which %= curchar.numshapes;
/* 477 */     curchar.basechar = charshape(curchar.basechar, which);
/*     */ 
/* 480 */     copycstostring(string, oldchar, level);
/* 481 */     copycstostring(string, curchar, level);
/*     */   }
/*     */ 
/*     */   public static int arabic_shape(char[] src, int srcoffset, int srclength, char[] dest, int destoffset, int destlength, int level) {
/* 485 */     char[] str = new char[srclength];
/* 486 */     for (int k = srclength + srcoffset - 1; k >= srcoffset; k--)
/* 487 */       str[(k - srcoffset)] = src[k];
/* 488 */     StringBuffer string = new StringBuffer(srclength);
/* 489 */     shape(str, string, level);
/* 490 */     if ((level & 0xC) != 0) {
/* 491 */       doublelig(string, level);
/*     */     }
/* 493 */     System.arraycopy(string.toString().toCharArray(), 0, dest, destoffset, string.length());
/* 494 */     return string.length();
/*     */   }
/*     */ 
/*     */   public static void processNumbers(char[] text, int offset, int length, int options) {
/* 498 */     int limit = offset + length;
/* 499 */     if ((options & 0xE0) != 0) {
/* 500 */       char digitBase = '0';
/* 501 */       switch (options & 0x100) {
/*     */       case 0:
/* 503 */         digitBase = '٠';
/* 504 */         break;
/*     */       case 256:
/* 507 */         digitBase = '۰';
/* 508 */         break;
/*     */       }
/*     */ 
/* 514 */       switch (options & 0xE0) {
/*     */       case 32:
/* 516 */         int digitDelta = digitBase - '0';
/* 517 */         for (int i = offset; i < limit; i++) {
/* 518 */           char ch = text[i];
/* 519 */           if ((ch <= '9') && (ch >= '0'))
/*     */           {
/*     */             int tmp152_150 = i; text[tmp152_150] = ((char)(text[tmp152_150] + digitDelta));
/*     */           }
/*     */         }
/*     */ 
/* 524 */         break;
/*     */       case 64:
/* 527 */         char digitTop = (char)(digitBase + '\t');
/* 528 */         int digitDelta = '0' - digitBase;
/* 529 */         for (int i = offset; i < limit; i++) {
/* 530 */           char ch = text[i];
/* 531 */           if ((ch <= digitTop) && (ch >= digitBase))
/*     */           {
/*     */             int tmp216_214 = i; text[tmp216_214] = ((char)(text[tmp216_214] + digitDelta));
/*     */           }
/*     */         }
/*     */ 
/* 536 */         break;
/*     */       case 96:
/* 539 */         shapeToArabicDigitsWithContext(text, 0, length, digitBase, false);
/* 540 */         break;
/*     */       case 128:
/* 543 */         shapeToArabicDigitsWithContext(text, 0, length, digitBase, true);
/* 544 */         break;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static void shapeToArabicDigitsWithContext(char[] dest, int start, int length, char digitBase, boolean lastStrongWasAL)
/*     */   {
/* 553 */     digitBase = (char)(digitBase - '0');
/*     */ 
/* 555 */     int limit = start + length;
/* 556 */     for (int i = start; i < limit; i++) {
/* 557 */       char ch = dest[i];
/* 558 */       switch (BidiOrder.getDirection(ch)) {
/*     */       case 0:
/*     */       case 3:
/* 561 */         lastStrongWasAL = false;
/* 562 */         break;
/*     */       case 4:
/* 564 */         lastStrongWasAL = true;
/* 565 */         break;
/*     */       case 8:
/* 567 */         if ((lastStrongWasAL) && (ch <= '9'))
/* 568 */           dest[i] = ((char)(ch + digitBase));
/*     */         break;
/*     */       case 1:
/*     */       case 2:
/*     */       case 5:
/*     */       case 6:
/*     */       case 7:
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public ArabicLigaturizer()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ArabicLigaturizer(int runDirection, int options)
/*     */   {
/* 761 */     this.runDirection = runDirection;
/* 762 */     this.options = options;
/*     */   }
/*     */ 
/*     */   public String process(String s) {
/* 766 */     return BidiLine.processLTR(s, this.runDirection, this.options);
/*     */   }
/*     */ 
/*     */   public boolean isRTL()
/*     */   {
/* 775 */     return true;
/*     */   }
/*     */ 
/*     */   static {
/* 779 */     for (char[] c : chartable)
/* 780 */       maptable.put(Character.valueOf(c[0]), c);
/*     */   }
/*     */ 
/*     */   static class charstruct
/*     */   {
/*     */     char basechar;
/*     */     char mark1;
/*     */     char vowel;
/*     */     int lignum;
/* 750 */     int numshapes = 1;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.languages.ArabicLigaturizer
 * JD-Core Version:    0.6.2
 */