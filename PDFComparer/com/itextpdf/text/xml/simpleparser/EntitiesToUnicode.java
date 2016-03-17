/*     */ package com.itextpdf.text.xml.simpleparser;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class EntitiesToUnicode
/*     */ {
/*  59 */   private static final Map<String, Character> MAP = new HashMap();
/*     */ 
/*     */   public static char decodeEntity(String name)
/*     */   {
/* 366 */     if (name.startsWith("#x")) {
/*     */       try {
/* 368 */         return (char)Integer.parseInt(name.substring(2), 16);
/*     */       }
/*     */       catch (NumberFormatException nfe) {
/* 371 */         return '\000';
/*     */       }
/*     */     }
/* 374 */     if (name.startsWith("#")) {
/*     */       try {
/* 376 */         return (char)Integer.parseInt(name.substring(1));
/*     */       }
/*     */       catch (NumberFormatException nfe) {
/* 379 */         return '\000';
/*     */       }
/*     */     }
/* 382 */     Character c = (Character)MAP.get(name);
/* 383 */     if (c == null) {
/* 384 */       return '\000';
/*     */     }
/* 386 */     return c.charValue();
/*     */   }
/*     */ 
/*     */   public static String decodeString(String s)
/*     */   {
/* 394 */     int pos_amp = s.indexOf('&');
/* 395 */     if (pos_amp == -1) return s;
/*     */ 
/* 399 */     StringBuffer buf = new StringBuffer(s.substring(0, pos_amp));
/*     */     while (true)
/*     */     {
/* 402 */       int pos_sc = s.indexOf(';', pos_amp);
/* 403 */       if (pos_sc == -1) {
/* 404 */         buf.append(s.substring(pos_amp));
/* 405 */         return buf.toString();
/*     */       }
/* 407 */       int pos_a = s.indexOf('&', pos_amp + 1);
/* 408 */       while ((pos_a != -1) && (pos_a < pos_sc)) {
/* 409 */         buf.append(s.substring(pos_amp, pos_a));
/* 410 */         pos_amp = pos_a;
/* 411 */         pos_a = s.indexOf('&', pos_amp + 1);
/*     */       }
/* 413 */       char replace = decodeEntity(s.substring(pos_amp + 1, pos_sc));
/* 414 */       if (s.length() < pos_sc + 1) {
/* 415 */         return buf.toString();
/*     */       }
/* 417 */       if (replace == 0) {
/* 418 */         buf.append(s.substring(pos_amp, pos_sc + 1));
/*     */       }
/*     */       else {
/* 421 */         buf.append(replace);
/*     */       }
/* 423 */       pos_amp = s.indexOf('&', pos_sc);
/* 424 */       if (pos_amp == -1) {
/* 425 */         buf.append(s.substring(pos_sc + 1));
/* 426 */         return buf.toString();
/*     */       }
/*     */ 
/* 429 */       buf.append(s.substring(pos_sc + 1, pos_amp));
/*     */     }
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  61 */     MAP.put("nbsp", Character.valueOf(' '));
/*  62 */     MAP.put("iexcl", Character.valueOf('¡'));
/*  63 */     MAP.put("cent", Character.valueOf('¢'));
/*  64 */     MAP.put("pound", Character.valueOf('£'));
/*  65 */     MAP.put("curren", Character.valueOf('¤'));
/*  66 */     MAP.put("yen", Character.valueOf('¥'));
/*  67 */     MAP.put("brvbar", Character.valueOf('¦'));
/*  68 */     MAP.put("sect", Character.valueOf('§'));
/*  69 */     MAP.put("uml", Character.valueOf('¨'));
/*  70 */     MAP.put("copy", Character.valueOf('©'));
/*  71 */     MAP.put("ordf", Character.valueOf('ª'));
/*  72 */     MAP.put("laquo", Character.valueOf('«'));
/*  73 */     MAP.put("not", Character.valueOf('¬'));
/*  74 */     MAP.put("shy", Character.valueOf('­'));
/*  75 */     MAP.put("reg", Character.valueOf('®'));
/*  76 */     MAP.put("macr", Character.valueOf('¯'));
/*  77 */     MAP.put("deg", Character.valueOf('°'));
/*  78 */     MAP.put("plusmn", Character.valueOf('±'));
/*  79 */     MAP.put("sup2", Character.valueOf('²'));
/*  80 */     MAP.put("sup3", Character.valueOf('³'));
/*  81 */     MAP.put("acute", Character.valueOf('´'));
/*  82 */     MAP.put("micro", Character.valueOf('µ'));
/*  83 */     MAP.put("para", Character.valueOf('¶'));
/*  84 */     MAP.put("middot", Character.valueOf('·'));
/*  85 */     MAP.put("cedil", Character.valueOf('¸'));
/*  86 */     MAP.put("sup1", Character.valueOf('¹'));
/*  87 */     MAP.put("ordm", Character.valueOf('º'));
/*  88 */     MAP.put("raquo", Character.valueOf('»'));
/*  89 */     MAP.put("frac14", Character.valueOf('¼'));
/*  90 */     MAP.put("frac12", Character.valueOf('½'));
/*  91 */     MAP.put("frac34", Character.valueOf('¾'));
/*  92 */     MAP.put("iquest", Character.valueOf('¿'));
/*  93 */     MAP.put("Agrave", Character.valueOf('À'));
/*  94 */     MAP.put("Aacute", Character.valueOf('Á'));
/*  95 */     MAP.put("Acirc", Character.valueOf('Â'));
/*  96 */     MAP.put("Atilde", Character.valueOf('Ã'));
/*  97 */     MAP.put("Auml", Character.valueOf('Ä'));
/*  98 */     MAP.put("Aring", Character.valueOf('Å'));
/*  99 */     MAP.put("AElig", Character.valueOf('Æ'));
/* 100 */     MAP.put("Ccedil", Character.valueOf('Ç'));
/* 101 */     MAP.put("Egrave", Character.valueOf('È'));
/* 102 */     MAP.put("Eacute", Character.valueOf('É'));
/* 103 */     MAP.put("Ecirc", Character.valueOf('Ê'));
/* 104 */     MAP.put("Euml", Character.valueOf('Ë'));
/* 105 */     MAP.put("Igrave", Character.valueOf('Ì'));
/* 106 */     MAP.put("Iacute", Character.valueOf('Í'));
/* 107 */     MAP.put("Icirc", Character.valueOf('Î'));
/* 108 */     MAP.put("Iuml", Character.valueOf('Ï'));
/* 109 */     MAP.put("ETH", Character.valueOf('Ð'));
/* 110 */     MAP.put("Ntilde", Character.valueOf('Ñ'));
/* 111 */     MAP.put("Ograve", Character.valueOf('Ò'));
/* 112 */     MAP.put("Oacute", Character.valueOf('Ó'));
/* 113 */     MAP.put("Ocirc", Character.valueOf('Ô'));
/* 114 */     MAP.put("Otilde", Character.valueOf('Õ'));
/* 115 */     MAP.put("Ouml", Character.valueOf('Ö'));
/* 116 */     MAP.put("times", Character.valueOf('×'));
/* 117 */     MAP.put("Oslash", Character.valueOf('Ø'));
/* 118 */     MAP.put("Ugrave", Character.valueOf('Ù'));
/* 119 */     MAP.put("Uacute", Character.valueOf('Ú'));
/* 120 */     MAP.put("Ucirc", Character.valueOf('Û'));
/* 121 */     MAP.put("Uuml", Character.valueOf('Ü'));
/* 122 */     MAP.put("Yacute", Character.valueOf('Ý'));
/* 123 */     MAP.put("THORN", Character.valueOf('Þ'));
/* 124 */     MAP.put("szlig", Character.valueOf('ß'));
/* 125 */     MAP.put("agrave", Character.valueOf('à'));
/* 126 */     MAP.put("aacute", Character.valueOf('á'));
/* 127 */     MAP.put("acirc", Character.valueOf('â'));
/* 128 */     MAP.put("atilde", Character.valueOf('ã'));
/* 129 */     MAP.put("auml", Character.valueOf('ä'));
/* 130 */     MAP.put("aring", Character.valueOf('å'));
/* 131 */     MAP.put("aelig", Character.valueOf('æ'));
/* 132 */     MAP.put("ccedil", Character.valueOf('ç'));
/* 133 */     MAP.put("egrave", Character.valueOf('è'));
/* 134 */     MAP.put("eacute", Character.valueOf('é'));
/* 135 */     MAP.put("ecirc", Character.valueOf('ê'));
/* 136 */     MAP.put("euml", Character.valueOf('ë'));
/* 137 */     MAP.put("igrave", Character.valueOf('ì'));
/* 138 */     MAP.put("iacute", Character.valueOf('í'));
/* 139 */     MAP.put("icirc", Character.valueOf('î'));
/* 140 */     MAP.put("iuml", Character.valueOf('ï'));
/* 141 */     MAP.put("eth", Character.valueOf('ð'));
/* 142 */     MAP.put("ntilde", Character.valueOf('ñ'));
/* 143 */     MAP.put("ograve", Character.valueOf('ò'));
/* 144 */     MAP.put("oacute", Character.valueOf('ó'));
/* 145 */     MAP.put("ocirc", Character.valueOf('ô'));
/* 146 */     MAP.put("otilde", Character.valueOf('õ'));
/* 147 */     MAP.put("ouml", Character.valueOf('ö'));
/* 148 */     MAP.put("divide", Character.valueOf('÷'));
/* 149 */     MAP.put("oslash", Character.valueOf('ø'));
/* 150 */     MAP.put("ugrave", Character.valueOf('ù'));
/* 151 */     MAP.put("uacute", Character.valueOf('ú'));
/* 152 */     MAP.put("ucirc", Character.valueOf('û'));
/* 153 */     MAP.put("uuml", Character.valueOf('ü'));
/* 154 */     MAP.put("yacute", Character.valueOf('ý'));
/* 155 */     MAP.put("thorn", Character.valueOf('þ'));
/* 156 */     MAP.put("yuml", Character.valueOf('ÿ'));
/*     */ 
/* 158 */     MAP.put("fnof", Character.valueOf('ƒ'));
/*     */ 
/* 160 */     MAP.put("Alpha", Character.valueOf('Α'));
/* 161 */     MAP.put("Beta", Character.valueOf('Β'));
/* 162 */     MAP.put("Gamma", Character.valueOf('Γ'));
/* 163 */     MAP.put("Delta", Character.valueOf('Δ'));
/* 164 */     MAP.put("Epsilon", Character.valueOf('Ε'));
/* 165 */     MAP.put("Zeta", Character.valueOf('Ζ'));
/* 166 */     MAP.put("Eta", Character.valueOf('Η'));
/* 167 */     MAP.put("Theta", Character.valueOf('Θ'));
/* 168 */     MAP.put("Iota", Character.valueOf('Ι'));
/* 169 */     MAP.put("Kappa", Character.valueOf('Κ'));
/* 170 */     MAP.put("Lambda", Character.valueOf('Λ'));
/* 171 */     MAP.put("Mu", Character.valueOf('Μ'));
/* 172 */     MAP.put("Nu", Character.valueOf('Ν'));
/* 173 */     MAP.put("Xi", Character.valueOf('Ξ'));
/* 174 */     MAP.put("Omicron", Character.valueOf('Ο'));
/* 175 */     MAP.put("Pi", Character.valueOf('Π'));
/* 176 */     MAP.put("Rho", Character.valueOf('Ρ'));
/*     */ 
/* 178 */     MAP.put("Sigma", Character.valueOf('Σ'));
/* 179 */     MAP.put("Tau", Character.valueOf('Τ'));
/* 180 */     MAP.put("Upsilon", Character.valueOf('Υ'));
/* 181 */     MAP.put("Phi", Character.valueOf('Φ'));
/* 182 */     MAP.put("Chi", Character.valueOf('Χ'));
/* 183 */     MAP.put("Psi", Character.valueOf('Ψ'));
/* 184 */     MAP.put("Omega", Character.valueOf('Ω'));
/* 185 */     MAP.put("alpha", Character.valueOf('α'));
/* 186 */     MAP.put("beta", Character.valueOf('β'));
/* 187 */     MAP.put("gamma", Character.valueOf('γ'));
/* 188 */     MAP.put("delta", Character.valueOf('δ'));
/* 189 */     MAP.put("epsilon", Character.valueOf('ε'));
/* 190 */     MAP.put("zeta", Character.valueOf('ζ'));
/* 191 */     MAP.put("eta", Character.valueOf('η'));
/* 192 */     MAP.put("theta", Character.valueOf('θ'));
/* 193 */     MAP.put("iota", Character.valueOf('ι'));
/* 194 */     MAP.put("kappa", Character.valueOf('κ'));
/* 195 */     MAP.put("lambda", Character.valueOf('λ'));
/* 196 */     MAP.put("mu", Character.valueOf('μ'));
/* 197 */     MAP.put("nu", Character.valueOf('ν'));
/* 198 */     MAP.put("xi", Character.valueOf('ξ'));
/* 199 */     MAP.put("omicron", Character.valueOf('ο'));
/* 200 */     MAP.put("pi", Character.valueOf('π'));
/* 201 */     MAP.put("rho", Character.valueOf('ρ'));
/* 202 */     MAP.put("sigmaf", Character.valueOf('ς'));
/* 203 */     MAP.put("sigma", Character.valueOf('σ'));
/* 204 */     MAP.put("tau", Character.valueOf('τ'));
/* 205 */     MAP.put("upsilon", Character.valueOf('υ'));
/* 206 */     MAP.put("phi", Character.valueOf('φ'));
/* 207 */     MAP.put("chi", Character.valueOf('χ'));
/* 208 */     MAP.put("psi", Character.valueOf('ψ'));
/* 209 */     MAP.put("omega", Character.valueOf('ω'));
/* 210 */     MAP.put("thetasym", Character.valueOf('ϑ'));
/* 211 */     MAP.put("upsih", Character.valueOf('ϒ'));
/* 212 */     MAP.put("piv", Character.valueOf('ϖ'));
/*     */ 
/* 214 */     MAP.put("bull", Character.valueOf('•'));
/*     */ 
/* 216 */     MAP.put("hellip", Character.valueOf('…'));
/* 217 */     MAP.put("prime", Character.valueOf('′'));
/* 218 */     MAP.put("Prime", Character.valueOf('″'));
/* 219 */     MAP.put("oline", Character.valueOf('‾'));
/* 220 */     MAP.put("frasl", Character.valueOf('⁄'));
/*     */ 
/* 222 */     MAP.put("weierp", Character.valueOf('℘'));
/* 223 */     MAP.put("image", Character.valueOf('ℑ'));
/* 224 */     MAP.put("real", Character.valueOf('ℜ'));
/* 225 */     MAP.put("trade", Character.valueOf('™'));
/* 226 */     MAP.put("alefsym", Character.valueOf('ℵ'));
/*     */ 
/* 230 */     MAP.put("larr", Character.valueOf('←'));
/* 231 */     MAP.put("uarr", Character.valueOf('↑'));
/* 232 */     MAP.put("rarr", Character.valueOf('→'));
/* 233 */     MAP.put("darr", Character.valueOf('↓'));
/* 234 */     MAP.put("harr", Character.valueOf('↔'));
/* 235 */     MAP.put("crarr", Character.valueOf('↵'));
/* 236 */     MAP.put("lArr", Character.valueOf('⇐'));
/*     */ 
/* 240 */     MAP.put("uArr", Character.valueOf('⇑'));
/* 241 */     MAP.put("rArr", Character.valueOf('⇒'));
/*     */ 
/* 245 */     MAP.put("dArr", Character.valueOf('⇓'));
/* 246 */     MAP.put("hArr", Character.valueOf('⇔'));
/*     */ 
/* 248 */     MAP.put("forall", Character.valueOf('∀'));
/* 249 */     MAP.put("part", Character.valueOf('∂'));
/* 250 */     MAP.put("exist", Character.valueOf('∃'));
/* 251 */     MAP.put("empty", Character.valueOf('∅'));
/* 252 */     MAP.put("nabla", Character.valueOf('∇'));
/* 253 */     MAP.put("isin", Character.valueOf('∈'));
/* 254 */     MAP.put("notin", Character.valueOf('∉'));
/* 255 */     MAP.put("ni", Character.valueOf('∋'));
/*     */ 
/* 257 */     MAP.put("prod", Character.valueOf('∏'));
/*     */ 
/* 260 */     MAP.put("sum", Character.valueOf('∑'));
/*     */ 
/* 263 */     MAP.put("minus", Character.valueOf('−'));
/* 264 */     MAP.put("lowast", Character.valueOf('∗'));
/* 265 */     MAP.put("radic", Character.valueOf('√'));
/* 266 */     MAP.put("prop", Character.valueOf('∝'));
/* 267 */     MAP.put("infin", Character.valueOf('∞'));
/* 268 */     MAP.put("ang", Character.valueOf('∠'));
/* 269 */     MAP.put("and", Character.valueOf('∧'));
/* 270 */     MAP.put("or", Character.valueOf('∨'));
/* 271 */     MAP.put("cap", Character.valueOf('∩'));
/* 272 */     MAP.put("cup", Character.valueOf('∪'));
/* 273 */     MAP.put("int", Character.valueOf('∫'));
/* 274 */     MAP.put("there4", Character.valueOf('∴'));
/* 275 */     MAP.put("sim", Character.valueOf('∼'));
/*     */ 
/* 278 */     MAP.put("cong", Character.valueOf('≅'));
/* 279 */     MAP.put("asymp", Character.valueOf('≈'));
/* 280 */     MAP.put("ne", Character.valueOf('≠'));
/* 281 */     MAP.put("equiv", Character.valueOf('≡'));
/* 282 */     MAP.put("le", Character.valueOf('≤'));
/* 283 */     MAP.put("ge", Character.valueOf('≥'));
/* 284 */     MAP.put("sub", Character.valueOf('⊂'));
/* 285 */     MAP.put("sup", Character.valueOf('⊃'));
/*     */ 
/* 289 */     MAP.put("nsub", Character.valueOf('⊄'));
/* 290 */     MAP.put("sube", Character.valueOf('⊆'));
/* 291 */     MAP.put("supe", Character.valueOf('⊇'));
/* 292 */     MAP.put("oplus", Character.valueOf('⊕'));
/* 293 */     MAP.put("otimes", Character.valueOf('⊗'));
/* 294 */     MAP.put("perp", Character.valueOf('⊥'));
/* 295 */     MAP.put("sdot", Character.valueOf('⋅'));
/*     */ 
/* 298 */     MAP.put("lceil", Character.valueOf('⌈'));
/* 299 */     MAP.put("rceil", Character.valueOf('⌉'));
/* 300 */     MAP.put("lfloor", Character.valueOf('⌊'));
/* 301 */     MAP.put("rfloor", Character.valueOf('⌋'));
/* 302 */     MAP.put("lang", Character.valueOf('〈'));
/*     */ 
/* 305 */     MAP.put("rang", Character.valueOf('〉'));
/*     */ 
/* 309 */     MAP.put("loz", Character.valueOf('◊'));
/*     */ 
/* 311 */     MAP.put("spades", Character.valueOf('♠'));
/*     */ 
/* 313 */     MAP.put("clubs", Character.valueOf('♣'));
/* 314 */     MAP.put("hearts", Character.valueOf('♥'));
/* 315 */     MAP.put("diams", Character.valueOf('♦'));
/*     */ 
/* 317 */     MAP.put("quot", Character.valueOf('"'));
/* 318 */     MAP.put("amp", Character.valueOf('&'));
/* 319 */     MAP.put("apos", Character.valueOf('\''));
/* 320 */     MAP.put("lt", Character.valueOf('<'));
/* 321 */     MAP.put("gt", Character.valueOf('>'));
/*     */ 
/* 323 */     MAP.put("OElig", Character.valueOf('Œ'));
/* 324 */     MAP.put("oelig", Character.valueOf('œ'));
/*     */ 
/* 326 */     MAP.put("Scaron", Character.valueOf('Š'));
/* 327 */     MAP.put("scaron", Character.valueOf('š'));
/* 328 */     MAP.put("Yuml", Character.valueOf('Ÿ'));
/*     */ 
/* 330 */     MAP.put("circ", Character.valueOf('ˆ'));
/* 331 */     MAP.put("tilde", Character.valueOf('˜'));
/*     */ 
/* 333 */     MAP.put("ensp", Character.valueOf(' '));
/* 334 */     MAP.put("emsp", Character.valueOf(' '));
/* 335 */     MAP.put("thinsp", Character.valueOf(' '));
/* 336 */     MAP.put("zwnj", Character.valueOf('‌'));
/* 337 */     MAP.put("zwj", Character.valueOf('‍'));
/* 338 */     MAP.put("lrm", Character.valueOf('‎'));
/* 339 */     MAP.put("rlm", Character.valueOf('‏'));
/* 340 */     MAP.put("ndash", Character.valueOf('–'));
/* 341 */     MAP.put("mdash", Character.valueOf('—'));
/* 342 */     MAP.put("lsquo", Character.valueOf('‘'));
/* 343 */     MAP.put("rsquo", Character.valueOf('’'));
/* 344 */     MAP.put("sbquo", Character.valueOf('‚'));
/* 345 */     MAP.put("ldquo", Character.valueOf('“'));
/* 346 */     MAP.put("rdquo", Character.valueOf('”'));
/* 347 */     MAP.put("bdquo", Character.valueOf('„'));
/* 348 */     MAP.put("dagger", Character.valueOf('†'));
/* 349 */     MAP.put("Dagger", Character.valueOf('‡'));
/* 350 */     MAP.put("permil", Character.valueOf('‰'));
/* 351 */     MAP.put("lsaquo", Character.valueOf('‹'));
/*     */ 
/* 353 */     MAP.put("rsaquo", Character.valueOf('›'));
/*     */ 
/* 355 */     MAP.put("euro", Character.valueOf('€'));
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.xml.simpleparser.EntitiesToUnicode
 * JD-Core Version:    0.6.2
 */