/*     */ package org.apache.fontbox.encoding;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public abstract class Encoding
/*     */ {
/*     */   public static final int NUMBER_OF_MAC_GLYPHS = 258;
/*  42 */   public static final String[] MAC_GLYPH_NAMES = { ".notdef", ".null", "nonmarkingreturn", "space", "exclam", "quotedbl", "numbersign", "dollar", "percent", "ampersand", "quotesingle", "parenleft", "parenright", "asterisk", "plus", "comma", "hyphen", "period", "slash", "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "colon", "semicolon", "less", "equal", "greater", "question", "at", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "bracketleft", "backslash", "bracketright", "asciicircum", "underscore", "grave", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "braceleft", "bar", "braceright", "asciitilde", "Adieresis", "Aring", "Ccedilla", "Eacute", "Ntilde", "Odieresis", "Udieresis", "aacute", "agrave", "acircumflex", "adieresis", "atilde", "aring", "ccedilla", "eacute", "egrave", "ecircumflex", "edieresis", "iacute", "igrave", "icircumflex", "idieresis", "ntilde", "oacute", "ograve", "ocircumflex", "odieresis", "otilde", "uacute", "ugrave", "ucircumflex", "udieresis", "dagger", "degree", "cent", "sterling", "section", "bullet", "paragraph", "germandbls", "registered", "copyright", "trademark", "acute", "dieresis", "notequal", "AE", "Oslash", "infinity", "plusminus", "lessequal", "greaterequal", "yen", "mu", "partialdiff", "summation", "product", "pi", "integral", "ordfeminine", "ordmasculine", "Omega", "ae", "oslash", "questiondown", "exclamdown", "logicalnot", "radical", "florin", "approxequal", "Delta", "guillemotleft", "guillemotright", "ellipsis", "nonbreakingspace", "Agrave", "Atilde", "Otilde", "OE", "oe", "endash", "emdash", "quotedblleft", "quotedblright", "quoteleft", "quoteright", "divide", "lozenge", "ydieresis", "Ydieresis", "fraction", "currency", "guilsinglleft", "guilsinglright", "fi", "fl", "daggerdbl", "periodcentered", "quotesinglbase", "quotedblbase", "perthousand", "Acircumflex", "Ecircumflex", "Aacute", "Edieresis", "Egrave", "Iacute", "Icircumflex", "Idieresis", "Igrave", "Oacute", "Ocircumflex", "apple", "Ograve", "Uacute", "Ucircumflex", "Ugrave", "dotlessi", "circumflex", "tilde", "macron", "breve", "dotaccent", "ring", "cedilla", "hungarumlaut", "ogonek", "caron", "Lslash", "lslash", "Scaron", "scaron", "Zcaron", "zcaron", "brokenbar", "Eth", "eth", "Yacute", "yacute", "Thorn", "thorn", "minus", "multiply", "onesuperior", "twosuperior", "threesuperior", "onehalf", "onequarter", "threequarters", "franc", "Gbreve", "gbreve", "Idotaccent", "Scedilla", "scedilla", "Cacute", "cacute", "Ccaron", "ccaron", "dcroat" };
/*     */ 
/*  95 */   public static Map<String, Integer> MAC_GLYPH_NAMES_INDICES = new HashMap();
/*     */   private static final String NOTDEF = ".notdef";
/* 110 */   protected Map<Integer, String> codeToName = new HashMap();
/*     */ 
/* 114 */   protected Map<String, Integer> nameToCode = new HashMap();
/*     */ 
/* 116 */   private static final Map<String, String> NAME_TO_CHARACTER = new HashMap();
/* 117 */   private static final Map<String, String> CHARACTER_TO_NAME = new HashMap();
/*     */ 
/*     */   protected void addCharacterEncoding(int code, String name)
/*     */   {
/* 127 */     this.codeToName.put(Integer.valueOf(code), name);
/* 128 */     this.nameToCode.put(name, Integer.valueOf(code));
/*     */   }
/*     */ 
/*     */   public int getCode(String name)
/*     */     throws IOException
/*     */   {
/* 142 */     Integer code = (Integer)this.nameToCode.get(name);
/* 143 */     if (code == null)
/*     */     {
/* 145 */       throw new IOException("No character code for character name '" + name + "'");
/*     */     }
/* 147 */     return code.intValue();
/*     */   }
/*     */ 
/*     */   public String getName(int code)
/*     */     throws IOException
/*     */   {
/* 161 */     String name = (String)this.codeToName.get(Integer.valueOf(code));
/* 162 */     if (name == null)
/*     */     {
/* 164 */       name = ".notdef";
/*     */     }
/* 166 */     return name;
/*     */   }
/*     */ 
/*     */   public String getNameFromCharacter(char c)
/*     */     throws IOException
/*     */   {
/* 180 */     String name = (String)CHARACTER_TO_NAME.get(Character.valueOf(c));
/* 181 */     if (name == null)
/*     */     {
/* 183 */       throw new IOException("No name for character '" + c + "'");
/*     */     }
/* 185 */     return name;
/*     */   }
/*     */ 
/*     */   public String getCharacter(int code)
/*     */     throws IOException
/*     */   {
/* 199 */     return getCharacter(getName(code));
/*     */   }
/*     */ 
/*     */   public static String getCharacter(String name)
/*     */   {
/* 211 */     String character = (String)NAME_TO_CHARACTER.get(name);
/* 212 */     if (character == null)
/*     */     {
/* 214 */       character = name;
/*     */     }
/* 216 */     return character;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  96 */     for (int i = 0; i < 258; i++)
/*     */     {
/*  98 */       MAC_GLYPH_NAMES_INDICES.put(MAC_GLYPH_NAMES[i], Integer.valueOf(i));
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.encoding.Encoding
 * JD-Core Version:    0.6.2
 */