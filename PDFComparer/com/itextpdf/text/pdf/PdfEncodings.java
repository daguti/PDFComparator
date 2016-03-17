/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import java.nio.charset.CodingErrorAction;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class PdfEncodings
/*     */ {
/*  61 */   static final char[] winansiByteToChar = { '\000', '\001', '\002', '\003', '\004', '\005', '\006', '\007', '\b', '\t', '\n', '\013', '\f', '\r', '\016', '\017', '\020', '\021', '\022', '\023', '\024', '\025', '\026', '\027', '\030', '\031', '\032', '\033', '\034', '\035', '\036', '\037', ' ', '!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ':', ';', '<', '=', '>', '?', '@', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '[', '\\', ']', '^', '_', '`', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '{', '|', '}', '~', '', '€', 65533, '‚', 'ƒ', '„', '…', '†', '‡', 'ˆ', '‰', 'Š', '‹', 'Œ', 65533, 'Ž', 65533, 65533, '‘', '’', '“', '”', '•', '–', '—', '˜', '™', 'š', '›', 'œ', 65533, 'ž', 'Ÿ', ' ', '¡', '¢', '£', '¤', '¥', '¦', '§', '¨', '©', 'ª', '«', '¬', '­', '®', '¯', '°', '±', '²', '³', '´', 'µ', '¶', '·', '¸', '¹', 'º', '»', '¼', '½', '¾', '¿', 'À', 'Á', 'Â', 'Ã', 'Ä', 'Å', 'Æ', 'Ç', 'È', 'É', 'Ê', 'Ë', 'Ì', 'Í', 'Î', 'Ï', 'Ð', 'Ñ', 'Ò', 'Ó', 'Ô', 'Õ', 'Ö', '×', 'Ø', 'Ù', 'Ú', 'Û', 'Ü', 'Ý', 'Þ', 'ß', 'à', 'á', 'â', 'ã', 'ä', 'å', 'æ', 'ç', 'è', 'é', 'ê', 'ë', 'ì', 'í', 'î', 'ï', 'ð', 'ñ', 'ò', 'ó', 'ô', 'õ', 'ö', '÷', 'ø', 'ù', 'ú', 'û', 'ü', 'ý', 'þ', 'ÿ' };
/*     */ 
/*  79 */   static final char[] pdfEncodingByteToChar = { '\000', '\001', '\002', '\003', '\004', '\005', '\006', '\007', '\b', '\t', '\n', '\013', '\f', '\r', '\016', '\017', '\020', '\021', '\022', '\023', '\024', '\025', '\026', '\027', '\030', '\031', '\032', '\033', '\034', '\035', '\036', '\037', ' ', '!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ':', ';', '<', '=', '>', '?', '@', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '[', '\\', ']', '^', '_', '`', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '{', '|', '}', '~', '', '•', '†', '‡', '…', '—', '–', 'ƒ', '⁄', '‹', '›', '−', '‰', '„', '“', '”', '‘', '’', '‚', '™', 64257, 64258, 'Ł', 'Œ', 'Š', 'Ÿ', 'Ž', 'ı', 'ł', 'œ', 'š', 'ž', 65533, '€', '¡', '¢', '£', '¤', '¥', '¦', '§', '¨', '©', 'ª', '«', '¬', '­', '®', '¯', '°', '±', '²', '³', '´', 'µ', '¶', '·', '¸', '¹', 'º', '»', '¼', '½', '¾', '¿', 'À', 'Á', 'Â', 'Ã', 'Ä', 'Å', 'Æ', 'Ç', 'È', 'É', 'Ê', 'Ë', 'Ì', 'Í', 'Î', 'Ï', 'Ð', 'Ñ', 'Ò', 'Ó', 'Ô', 'Õ', 'Ö', '×', 'Ø', 'Ù', 'Ú', 'Û', 'Ü', 'Ý', 'Þ', 'ß', 'à', 'á', 'â', 'ã', 'ä', 'å', 'æ', 'ç', 'è', 'é', 'ê', 'ë', 'ì', 'í', 'î', 'ï', 'ð', 'ñ', 'ò', 'ó', 'ô', 'õ', 'ö', '÷', 'ø', 'ù', 'ú', 'û', 'ü', 'ý', 'þ', 'ÿ' };
/*     */ 
/*  97 */   static final IntHashtable winansi = new IntHashtable();
/*     */ 
/*  99 */   static final IntHashtable pdfEncoding = new IntHashtable();
/*     */ 
/* 101 */   static HashMap<String, ExtraEncoding> extraEncodings = new HashMap();
/*     */ 
/*     */   public static final byte[] convertToBytes(String text, String encoding)
/*     */   {
/* 130 */     if (text == null)
/* 131 */       return new byte[0];
/* 132 */     if ((encoding == null) || (encoding.length() == 0)) {
/* 133 */       int len = text.length();
/* 134 */       byte[] b = new byte[len];
/* 135 */       for (int k = 0; k < len; k++)
/* 136 */         b[k] = ((byte)text.charAt(k));
/* 137 */       return b;
/*     */     }
/* 139 */     ExtraEncoding extra = (ExtraEncoding)extraEncodings.get(encoding.toLowerCase());
/* 140 */     if (extra != null) {
/* 141 */       byte[] b = extra.charToByte(text, encoding);
/* 142 */       if (b != null)
/* 143 */         return b;
/*     */     }
/* 145 */     IntHashtable hash = null;
/* 146 */     if (encoding.equals("Cp1252"))
/* 147 */       hash = winansi;
/* 148 */     else if (encoding.equals("PDF"))
/* 149 */       hash = pdfEncoding;
/* 150 */     if (hash != null) {
/* 151 */       char[] cc = text.toCharArray();
/* 152 */       int len = cc.length;
/* 153 */       int ptr = 0;
/* 154 */       byte[] b = new byte[len];
/* 155 */       int c = 0;
/* 156 */       for (int k = 0; k < len; k++) {
/* 157 */         char char1 = cc[k];
/* 158 */         if ((char1 < '') || ((char1 > ' ') && (char1 <= 'ÿ')))
/* 159 */           c = char1;
/*     */         else
/* 161 */           c = hash.get(char1);
/* 162 */         if (c != 0)
/* 163 */           b[(ptr++)] = ((byte)c);
/*     */       }
/* 165 */       if (ptr == len)
/* 166 */         return b;
/* 167 */       byte[] b2 = new byte[ptr];
/* 168 */       System.arraycopy(b, 0, b2, 0, ptr);
/* 169 */       return b2;
/*     */     }
/* 171 */     if (encoding.equals("UnicodeBig"))
/*     */     {
/* 173 */       char[] cc = text.toCharArray();
/* 174 */       int len = cc.length;
/* 175 */       byte[] b = new byte[cc.length * 2 + 2];
/* 176 */       b[0] = -2;
/* 177 */       b[1] = -1;
/* 178 */       int bptr = 2;
/* 179 */       for (int k = 0; k < len; k++) {
/* 180 */         char c = cc[k];
/* 181 */         b[(bptr++)] = ((byte)(c >> '\b'));
/* 182 */         b[(bptr++)] = ((byte)(c & 0xFF));
/*     */       }
/* 184 */       return b;
/*     */     }
/*     */     try {
/* 187 */       Charset cc = Charset.forName(encoding);
/* 188 */       CharsetEncoder ce = cc.newEncoder();
/* 189 */       ce.onUnmappableCharacter(CodingErrorAction.IGNORE);
/* 190 */       CharBuffer cb = CharBuffer.wrap(text.toCharArray());
/* 191 */       ByteBuffer bb = ce.encode(cb);
/* 192 */       bb.rewind();
/* 193 */       int lim = bb.limit();
/* 194 */       byte[] br = new byte[lim];
/* 195 */       bb.get(br);
/* 196 */       return br;
/*     */     }
/*     */     catch (IOException e) {
/* 199 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static final byte[] convertToBytes(char char1, String encoding)
/*     */   {
/* 210 */     if ((encoding == null) || (encoding.length() == 0))
/* 211 */       return new byte[] { (byte)char1 };
/* 212 */     ExtraEncoding extra = (ExtraEncoding)extraEncodings.get(encoding.toLowerCase());
/* 213 */     if (extra != null) {
/* 214 */       byte[] b = extra.charToByte(char1, encoding);
/* 215 */       if (b != null)
/* 216 */         return b;
/*     */     }
/* 218 */     IntHashtable hash = null;
/* 219 */     if (encoding.equals("Cp1252"))
/* 220 */       hash = winansi;
/* 221 */     else if (encoding.equals("PDF"))
/* 222 */       hash = pdfEncoding;
/* 223 */     if (hash != null) {
/* 224 */       int c = 0;
/* 225 */       if ((char1 < '') || ((char1 > ' ') && (char1 <= 'ÿ')))
/* 226 */         c = char1;
/*     */       else
/* 228 */         c = hash.get(char1);
/* 229 */       if (c != 0) {
/* 230 */         return new byte[] { (byte)c };
/*     */       }
/* 232 */       return new byte[0];
/*     */     }
/* 234 */     if (encoding.equals("UnicodeBig"))
/*     */     {
/* 236 */       byte[] b = new byte[4];
/* 237 */       b[0] = -2;
/* 238 */       b[1] = -1;
/* 239 */       b[2] = ((byte)(char1 >> '\b'));
/* 240 */       b[3] = ((byte)(char1 & 0xFF));
/* 241 */       return b;
/*     */     }
/*     */     try {
/* 244 */       Charset cc = Charset.forName(encoding);
/* 245 */       CharsetEncoder ce = cc.newEncoder();
/* 246 */       ce.onUnmappableCharacter(CodingErrorAction.IGNORE);
/* 247 */       CharBuffer cb = CharBuffer.wrap(new char[] { char1 });
/* 248 */       ByteBuffer bb = ce.encode(cb);
/* 249 */       bb.rewind();
/* 250 */       int lim = bb.limit();
/* 251 */       byte[] br = new byte[lim];
/* 252 */       bb.get(br);
/* 253 */       return br;
/*     */     }
/*     */     catch (IOException e) {
/* 256 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static final String convertToString(byte[] bytes, String encoding)
/*     */   {
/* 267 */     if (bytes == null)
/* 268 */       return "";
/* 269 */     if ((encoding == null) || (encoding.length() == 0)) {
/* 270 */       char[] c = new char[bytes.length];
/* 271 */       for (int k = 0; k < bytes.length; k++)
/* 272 */         c[k] = ((char)(bytes[k] & 0xFF));
/* 273 */       return new String(c);
/*     */     }
/* 275 */     ExtraEncoding extra = (ExtraEncoding)extraEncodings.get(encoding.toLowerCase());
/* 276 */     if (extra != null) {
/* 277 */       String text = extra.byteToChar(bytes, encoding);
/* 278 */       if (text != null)
/* 279 */         return text;
/*     */     }
/* 281 */     char[] ch = null;
/* 282 */     if (encoding.equals("Cp1252"))
/* 283 */       ch = winansiByteToChar;
/* 284 */     else if (encoding.equals("PDF"))
/* 285 */       ch = pdfEncodingByteToChar;
/* 286 */     if (ch != null) {
/* 287 */       int len = bytes.length;
/* 288 */       char[] c = new char[len];
/* 289 */       for (int k = 0; k < len; k++) {
/* 290 */         c[k] = ch[(bytes[k] & 0xFF)];
/*     */       }
/* 292 */       return new String(c);
/*     */     }
/*     */     try {
/* 295 */       return new String(bytes, encoding);
/*     */     }
/*     */     catch (UnsupportedEncodingException e) {
/* 298 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static boolean isPdfDocEncoding(String text)
/*     */   {
/* 307 */     if (text == null)
/* 308 */       return true;
/* 309 */     int len = text.length();
/* 310 */     for (int k = 0; k < len; k++) {
/* 311 */       char char1 = text.charAt(k);
/* 312 */       if ((char1 >= '') && ((char1 <= ' ') || (char1 > 'ÿ')))
/*     */       {
/* 314 */         if (!pdfEncoding.containsKey(char1))
/* 315 */           return false; 
/*     */       }
/*     */     }
/* 317 */     return true;
/*     */   }
/*     */ 
/*     */   public static void addExtraEncoding(String name, ExtraEncoding enc)
/*     */   {
/* 326 */     synchronized (extraEncodings) {
/* 327 */       HashMap newEncodings = (HashMap)extraEncodings.clone();
/* 328 */       newEncodings.put(name.toLowerCase(), enc);
/* 329 */       extraEncodings = newEncodings;
/*     */     }
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 104 */     for (int k = 128; k < 161; k++) {
/* 105 */       char c = winansiByteToChar[k];
/* 106 */       if (c != 65533) {
/* 107 */         winansi.put(c, k);
/*     */       }
/*     */     }
/* 110 */     for (int k = 128; k < 161; k++) {
/* 111 */       char c = pdfEncodingByteToChar[k];
/* 112 */       if (c != 65533) {
/* 113 */         pdfEncoding.put(c, k);
/*     */       }
/*     */     }
/* 116 */     addExtraEncoding("Wingdings", new WingdingsConversion(null));
/* 117 */     addExtraEncoding("Symbol", new SymbolConversion(true));
/* 118 */     addExtraEncoding("ZapfDingbats", new SymbolConversion(false));
/* 119 */     addExtraEncoding("SymbolTT", new SymbolTTConversion(null));
/* 120 */     addExtraEncoding("Cp437", new Cp437Conversion(null));
/*     */   }
/*     */ 
/*     */   private static class SymbolTTConversion
/*     */     implements ExtraEncoding
/*     */   {
/*     */     public byte[] charToByte(char char1, String encoding)
/*     */     {
/* 578 */       if (((char1 & 0xFF00) == 0) || ((char1 & 0xFF00) == 61440)) {
/* 579 */         return new byte[] { (byte)char1 };
/*     */       }
/* 581 */       return new byte[0];
/*     */     }
/*     */ 
/*     */     public byte[] charToByte(String text, String encoding) {
/* 585 */       char[] ch = text.toCharArray();
/* 586 */       byte[] b = new byte[ch.length];
/* 587 */       int ptr = 0;
/* 588 */       int len = ch.length;
/* 589 */       for (int k = 0; k < len; k++) {
/* 590 */         char c = ch[k];
/* 591 */         if (((c & 0xFF00) == 0) || ((c & 0xFF00) == 61440))
/* 592 */           b[(ptr++)] = ((byte)c);
/*     */       }
/* 594 */       if (ptr == len)
/* 595 */         return b;
/* 596 */       byte[] b2 = new byte[ptr];
/* 597 */       System.arraycopy(b, 0, b2, 0, ptr);
/* 598 */       return b2;
/*     */     }
/*     */ 
/*     */     public String byteToChar(byte[] b, String encoding) {
/* 602 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class SymbolConversion
/*     */     implements ExtraEncoding
/*     */   {
/* 470 */     private static final IntHashtable t1 = new IntHashtable();
/* 471 */     private static final IntHashtable t2 = new IntHashtable();
/*     */     private IntHashtable translation;
/*     */     private final char[] byteToChar;
/* 523 */     private static final char[] table1 = { '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', ' ', '!', '∀', '#', '∃', '%', '&', '∋', '(', ')', '*', '+', ',', '-', '.', '/', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ':', ';', '<', '=', '>', '?', '≅', 'Α', 'Β', 'Χ', 'Δ', 'Ε', 'Φ', 'Γ', 'Η', 'Ι', 'ϑ', 'Κ', 'Λ', 'Μ', 'Ν', 'Ο', 'Π', 'Θ', 'Ρ', 'Σ', 'Τ', 'Υ', 'ς', 'Ω', 'Ξ', 'Ψ', 'Ζ', '[', '∴', ']', '⊥', '_', '̅', 'α', 'β', 'χ', 'δ', 'ε', 'ϕ', 'γ', 'η', 'ι', 'φ', 'κ', 'λ', 'μ', 'ν', 'ο', 'π', 'θ', 'ρ', 'σ', 'τ', 'υ', 'ϖ', 'ω', 'ξ', 'ψ', 'ζ', '{', '|', '}', '~', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '€', 'ϒ', '′', '≤', '⁄', '∞', 'ƒ', '♣', '♦', '♥', '♠', '↔', '←', '↑', '→', '↓', '°', '±', '″', '≥', '×', '∝', '∂', '•', '÷', '≠', '≡', '≈', '…', '│', '─', '↵', 'ℵ', 'ℑ', 'ℜ', '℘', '⊗', '⊕', '∅', '∩', '∪', '⊃', '⊇', '⊄', '⊂', '⊆', '∈', '∉', '∠', '∇', '®', '©', '™', '∏', '√', '⋅', '¬', '∧', '∨', '⇔', '⇐', '⇑', '⇒', '⇓', '◊', '〈', '\000', '\000', '\000', '∑', '⎛', '⎜', '⎝', '⎡', '⎢', '⎣', '⎧', '⎨', '⎩', '⎪', '\000', '〉', '∫', '⌠', '⎮', '⌡', '⎞', '⎟', '⎠', '⎤', '⎥', '⎦', '⎫', '⎬', '⎭', '\000' };
/*     */ 
/* 542 */     private static final char[] table2 = { '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', ' ', '✁', '✂', '✃', '✄', '☎', '✆', '✇', '✈', '✉', '☛', '☞', '✌', '✍', '✎', '✏', '✐', '✑', '✒', '✓', '✔', '✕', '✖', '✗', '✘', '✙', '✚', '✛', '✜', '✝', '✞', '✟', '✠', '✡', '✢', '✣', '✤', '✥', '✦', '✧', '★', '✩', '✪', '✫', '✬', '✭', '✮', '✯', '✰', '✱', '✲', '✳', '✴', '✵', '✶', '✷', '✸', '✹', '✺', '✻', '✼', '✽', '✾', '✿', '❀', '❁', '❂', '❃', '❄', '❅', '❆', '❇', '❈', '❉', '❊', '❋', '●', '❍', '■', '❏', '❐', '❑', '❒', '▲', '▼', '◆', '❖', '◗', '❘', '❙', '❚', '❛', '❜', '❝', '❞', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '❡', '❢', '❣', '❤', '❥', '❦', '❧', '♣', '♦', '♥', '♠', '①', '②', '③', '④', '⑤', '⑥', '⑦', '⑧', '⑨', '⑩', '❶', '❷', '❸', '❹', '❺', '❻', '❼', '❽', '❾', '❿', '➀', '➁', '➂', '➃', '➄', '➅', '➆', '➇', '➈', '➉', '➊', '➋', '➌', '➍', '➎', '➏', '➐', '➑', '➒', '➓', '➔', '→', '↔', '↕', '➘', '➙', '➚', '➛', '➜', '➝', '➞', '➟', '➠', '➡', '➢', '➣', '➤', '➥', '➦', '➧', '➨', '➩', '➪', '➫', '➬', '➭', '➮', '➯', '\000', '➱', '➲', '➳', '➴', '➵', '➶', '➷', '➸', '➹', '➺', '➻', '➼', '➽', '➾', '\000' };
/*     */ 
/*     */     SymbolConversion(boolean symbol)
/*     */     {
/* 476 */       if (symbol) {
/* 477 */         this.translation = t1;
/* 478 */         this.byteToChar = table1;
/*     */       } else {
/* 480 */         this.translation = t2;
/* 481 */         this.byteToChar = table2;
/*     */       }
/*     */     }
/*     */ 
/*     */     public byte[] charToByte(String text, String encoding) {
/* 486 */       char[] cc = text.toCharArray();
/* 487 */       byte[] b = new byte[cc.length];
/* 488 */       int ptr = 0;
/* 489 */       int len = cc.length;
/* 490 */       for (int k = 0; k < len; k++) {
/* 491 */         char c = cc[k];
/* 492 */         byte v = (byte)this.translation.get(c);
/* 493 */         if (v != 0)
/* 494 */           b[(ptr++)] = v;
/*     */       }
/* 496 */       if (ptr == len)
/* 497 */         return b;
/* 498 */       byte[] b2 = new byte[ptr];
/* 499 */       System.arraycopy(b, 0, b2, 0, ptr);
/* 500 */       return b2;
/*     */     }
/*     */ 
/*     */     public byte[] charToByte(char char1, String encoding) {
/* 504 */       byte v = (byte)this.translation.get(char1);
/* 505 */       if (v != 0) {
/* 506 */         return new byte[] { v };
/*     */       }
/* 508 */       return new byte[0];
/*     */     }
/*     */ 
/*     */     public String byteToChar(byte[] b, String encoding) {
/* 512 */       int len = b.length;
/* 513 */       char[] cc = new char[len];
/* 514 */       int ptr = 0;
/* 515 */       for (int k = 0; k < len; k++) {
/* 516 */         int c = b[k] & 0xFF;
/* 517 */         char v = this.byteToChar[c];
/* 518 */         cc[(ptr++)] = v;
/*     */       }
/* 520 */       return new String(cc, 0, ptr);
/*     */     }
/*     */ 
/*     */     static
/*     */     {
/* 562 */       for (int k = 0; k < 256; k++) {
/* 563 */         int v = table1[k];
/* 564 */         if (v != 0)
/* 565 */           t1.put(v, k);
/*     */       }
/* 567 */       for (int k = 0; k < 256; k++) {
/* 568 */         int v = table2[k];
/* 569 */         if (v != 0)
/* 570 */           t2.put(v, k);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class Cp437Conversion
/*     */     implements ExtraEncoding
/*     */   {
/* 397 */     private static IntHashtable c2b = new IntHashtable();
/*     */ 
/* 451 */     private static final char[] table = { 'Ç', 'ü', 'é', 'â', 'ä', 'à', 'å', 'ç', 'ê', 'ë', 'è', 'ï', 'î', 'ì', 'Ä', 'Å', 'É', 'æ', 'Æ', 'ô', 'ö', 'ò', 'û', 'ù', 'ÿ', 'Ö', 'Ü', '¢', '£', '¥', '₧', 'ƒ', 'á', 'í', 'ó', 'ú', 'ñ', 'Ñ', 'ª', 'º', '¿', '⌐', '¬', '½', '¼', '¡', '«', '»', '░', '▒', '▓', '│', '┤', '╡', '╢', '╖', '╕', '╣', '║', '╗', '╝', '╜', '╛', '┐', '└', '┴', '┬', '├', '─', '┼', '╞', '╟', '╚', '╔', '╩', '╦', '╠', '═', '╬', '╧', '╨', '╤', '╥', '╙', '╘', '╒', '╓', '╫', '╪', '┘', '┌', '█', '▄', '▌', '▐', '▀', 'α', 'ß', 'Γ', 'π', 'Σ', 'σ', 'µ', 'τ', 'Φ', 'Θ', 'Ω', 'δ', '∞', 'φ', 'ε', '∩', '≡', '±', '≥', '≤', '⌠', '⌡', '÷', '≈', '°', '∙', '·', '√', 'ⁿ', '²', '■', ' ' };
/*     */ 
/*     */     public byte[] charToByte(String text, String encoding)
/*     */     {
/* 400 */       char[] cc = text.toCharArray();
/* 401 */       byte[] b = new byte[cc.length];
/* 402 */       int ptr = 0;
/* 403 */       int len = cc.length;
/* 404 */       for (int k = 0; k < len; k++) {
/* 405 */         char c = cc[k];
/* 406 */         if (c < '') {
/* 407 */           b[(ptr++)] = ((byte)c);
/*     */         } else {
/* 409 */           byte v = (byte)c2b.get(c);
/* 410 */           if (v != 0)
/* 411 */             b[(ptr++)] = v;
/*     */         }
/*     */       }
/* 414 */       if (ptr == len)
/* 415 */         return b;
/* 416 */       byte[] b2 = new byte[ptr];
/* 417 */       System.arraycopy(b, 0, b2, 0, ptr);
/* 418 */       return b2;
/*     */     }
/*     */ 
/*     */     public byte[] charToByte(char char1, String encoding) {
/* 422 */       if (char1 < '') {
/* 423 */         return new byte[] { (byte)char1 };
/*     */       }
/* 425 */       byte v = (byte)c2b.get(char1);
/* 426 */       if (v != 0) {
/* 427 */         return new byte[] { v };
/*     */       }
/* 429 */       return new byte[0];
/*     */     }
/*     */ 
/*     */     public String byteToChar(byte[] b, String encoding)
/*     */     {
/* 434 */       int len = b.length;
/* 435 */       char[] cc = new char[len];
/* 436 */       int ptr = 0;
/* 437 */       for (int k = 0; k < len; k++) {
/* 438 */         int c = b[k] & 0xFF;
/* 439 */         if (c >= 32)
/*     */         {
/* 441 */           if (c < 128) {
/* 442 */             cc[(ptr++)] = ((char)c);
/*     */           } else {
/* 444 */             char v = table[(c - 128)];
/* 445 */             cc[(ptr++)] = v;
/*     */           }
/*     */         }
/*     */       }
/* 448 */       return new String(cc, 0, ptr);
/*     */     }
/*     */ 
/*     */     static
/*     */     {
/* 463 */       for (int k = 0; k < table.length; k++)
/* 464 */         c2b.put(table[k], k + 128);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class WingdingsConversion
/*     */     implements ExtraEncoding
/*     */   {
/* 372 */     private static final byte[] table = { 0, 35, 34, 0, 0, 0, 41, 62, 81, 42, 0, 0, 65, 63, 0, 0, 0, 0, 0, -4, 0, 0, 0, -5, 0, 0, 0, 0, 0, 0, 86, 0, 88, 89, 0, 0, 0, 0, 0, 0, 0, 0, -75, 0, 0, 0, 0, 0, -74, 0, 0, 0, -83, -81, -84, 0, 0, 0, 0, 0, 0, 0, 0, 124, 123, 0, 0, 0, 84, 0, 0, 0, 0, 0, 0, 0, 0, -90, 0, 0, 0, 113, 114, 0, 0, 0, 117, 0, 0, 0, 0, 0, 0, 125, 126, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -116, -115, -114, -113, -112, -111, -110, -109, -108, -107, -127, -126, -125, -124, -123, -122, -121, -120, -119, -118, -116, -115, -114, -113, -112, -111, -110, -109, -108, -107, -24, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -24, -40, 0, 0, -60, -58, 0, 0, -16, 0, 0, 0, 0, 0, 0, 0, 0, 0, -36, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
/*     */ 
/*     */     public byte[] charToByte(char char1, String encoding)
/*     */     {
/* 336 */       if (char1 == ' ')
/* 337 */         return new byte[] { (byte)char1 };
/* 338 */       if ((char1 >= '✁') && (char1 <= '➾')) {
/* 339 */         byte v = table[(char1 - '✀')];
/* 340 */         if (v != 0)
/* 341 */           return new byte[] { v };
/*     */       }
/* 343 */       return new byte[0];
/*     */     }
/*     */ 
/*     */     public byte[] charToByte(String text, String encoding) {
/* 347 */       char[] cc = text.toCharArray();
/* 348 */       byte[] b = new byte[cc.length];
/* 349 */       int ptr = 0;
/* 350 */       int len = cc.length;
/* 351 */       for (int k = 0; k < len; k++) {
/* 352 */         char c = cc[k];
/* 353 */         if (c == ' ') {
/* 354 */           b[(ptr++)] = ((byte)c);
/* 355 */         } else if ((c >= '✁') && (c <= '➾')) {
/* 356 */           byte v = table[(c - '✀')];
/* 357 */           if (v != 0)
/* 358 */             b[(ptr++)] = v;
/*     */         }
/*     */       }
/* 361 */       if (ptr == len)
/* 362 */         return b;
/* 363 */       byte[] b2 = new byte[ptr];
/* 364 */       System.arraycopy(b, 0, b2, 0, ptr);
/* 365 */       return b2;
/*     */     }
/*     */ 
/*     */     public String byteToChar(byte[] b, String encoding) {
/* 369 */       return null;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfEncodings
 * JD-Core Version:    0.6.2
 */