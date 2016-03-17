/*     */ package org.antlr.codegen;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.antlr.Tool;
/*     */ import org.antlr.stringtemplate.AttributeRenderer;
/*     */ import org.antlr.stringtemplate.StringTemplate;
/*     */ import org.antlr.stringtemplate.StringTemplateGroup;
/*     */ import org.antlr.tool.Grammar;
/*     */ 
/*     */ public class RubyTarget extends Target
/*     */ {
/*  43 */   public static final Set rubyKeywords = new HashSet() { } ;
/*     */ 
/*  62 */   public static HashMap sharedActionBlocks = new HashMap();
/*     */ 
/*     */   protected void genRecognizerFile(Tool tool, CodeGenerator generator, Grammar grammar, StringTemplate outputFileST)
/*     */     throws IOException
/*     */   {
/* 339 */     if (grammar.type == 4) {
/* 340 */       Map actions = grammar.getActions();
/* 341 */       if (actions.containsKey("all"))
/* 342 */         sharedActionBlocks.put(grammar.name, actions.get("all"));
/*     */     }
/* 344 */     else if ((grammar.implicitLexer) && 
/* 345 */       (sharedActionBlocks.containsKey(grammar.name))) {
/* 346 */       Map actions = grammar.getActions();
/* 347 */       actions.put("all", sharedActionBlocks.get(grammar.name));
/*     */     }
/*     */ 
/* 351 */     StringTemplateGroup group = generator.getTemplates();
/* 352 */     RubyRenderer renderer = new RubyRenderer();
/*     */     try {
/* 354 */       group.registerRenderer(Class.forName("java.lang.String"), renderer);
/*     */     }
/*     */     catch (ClassNotFoundException e) {
/* 357 */       System.err.println("ClassNotFoundException: " + e.getMessage());
/* 358 */       e.printStackTrace(System.err);
/*     */     }
/* 360 */     String fileName = generator.getRecognizerFileName(grammar.name, grammar.type);
/*     */ 
/* 362 */     generator.write(outputFileST, fileName);
/*     */   }
/*     */ 
/*     */   public String getTargetCharLiteralFromANTLRCharLiteral(CodeGenerator generator, String literal)
/*     */   {
/* 370 */     int code_point = 0;
/* 371 */     literal = literal.substring(1, literal.length() - 1);
/*     */ 
/* 373 */     if (literal.charAt(0) == '\\')
/* 374 */       switch (literal.charAt(1)) {
/*     */       case '"':
/*     */       case '\'':
/*     */       case '\\':
/* 378 */         code_point = literal.codePointAt(1);
/* 379 */         break;
/*     */       case 'n':
/* 381 */         code_point = 10;
/* 382 */         break;
/*     */       case 'r':
/* 384 */         code_point = 13;
/* 385 */         break;
/*     */       case 't':
/* 387 */         code_point = 9;
/* 388 */         break;
/*     */       case 'b':
/* 390 */         code_point = 8;
/* 391 */         break;
/*     */       case 'f':
/* 393 */         code_point = 12;
/* 394 */         break;
/*     */       case 'u':
/* 396 */         code_point = Integer.parseInt(literal.substring(2), 16);
/* 397 */         break;
/*     */       default:
/* 399 */         System.out.println("1: hey you didn't account for this: \"" + literal + "\"");
/* 400 */         break;
/*     */       }
/* 402 */     else if (literal.length() == 1)
/* 403 */       code_point = literal.codePointAt(0);
/*     */     else {
/* 405 */       System.out.println("2: hey you didn't account for this: \"" + literal + "\"");
/*     */     }
/*     */ 
/* 408 */     return "0x" + Integer.toHexString(code_point);
/*     */   }
/*     */ 
/*     */   public int getMaxCharValue(CodeGenerator generator)
/*     */   {
/* 414 */     return 255;
/*     */   }
/*     */ 
/*     */   public String getTokenTypeAsTargetLabel(CodeGenerator generator, int ttype)
/*     */   {
/* 419 */     String name = generator.grammar.getTokenDisplayName(ttype);
/*     */ 
/* 421 */     if (name.charAt(0) == '\'') {
/* 422 */       return generator.grammar.computeTokenNameFromLiteral(ttype, name);
/*     */     }
/* 424 */     return name;
/*     */   }
/*     */ 
/*     */   public boolean isValidActionScope(int grammarType, String scope) {
/* 428 */     if (scope.equals("all")) {
/* 429 */       return true;
/*     */     }
/* 431 */     if (scope.equals("token")) {
/* 432 */       return true;
/*     */     }
/* 434 */     if (scope.equals("module")) {
/* 435 */       return true;
/*     */     }
/* 437 */     if (scope.equals("overrides")) {
/* 438 */       return true;
/*     */     }
/*     */ 
/* 441 */     switch (grammarType) {
/*     */     case 1:
/* 443 */       if (scope.equals("lexer")) {
/* 444 */         return true;
/*     */       }
/*     */       break;
/*     */     case 2:
/* 448 */       if (scope.equals("parser")) {
/* 449 */         return true;
/*     */       }
/*     */       break;
/*     */     case 4:
/* 453 */       if (scope.equals("parser")) {
/* 454 */         return true;
/*     */       }
/* 456 */       if (scope.equals("lexer")) {
/* 457 */         return true;
/*     */       }
/*     */       break;
/*     */     case 3:
/* 461 */       if (scope.equals("treeparser")) {
/* 462 */         return true;
/*     */       }
/*     */       break;
/*     */     }
/* 466 */     return false;
/*     */   }
/*     */ 
/*     */   public String encodeIntAsCharEscape(int v)
/*     */   {
/*     */     int intValue;
/*     */     int intValue;
/* 472 */     if (v == 65535)
/* 473 */       intValue = -1;
/*     */     else {
/* 475 */       intValue = v;
/*     */     }
/*     */ 
/* 478 */     return String.valueOf(intValue);
/*     */   }
/*     */ 
/*     */   public class RubyRenderer
/*     */     implements AttributeRenderer
/*     */   {
/*  66 */     protected String[] rubyCharValueEscape = new String[256];
/*     */ 
/*     */     public RubyRenderer() {
/*  69 */       for (int i = 0; i < 16; i++) {
/*  70 */         this.rubyCharValueEscape[i] = ("\\x0" + Integer.toHexString(i));
/*     */       }
/*  72 */       for (int i = 16; i < 32; i++) {
/*  73 */         this.rubyCharValueEscape[i] = ("\\x" + Integer.toHexString(i));
/*     */       }
/*  75 */       for (char i = ' '; i < ''; i = (char)(i + '\001')) {
/*  76 */         this.rubyCharValueEscape[i] = Character.toString(i);
/*     */       }
/*  78 */       for (int i = 127; i < 256; i++) {
/*  79 */         this.rubyCharValueEscape[i] = ("\\x" + Integer.toHexString(i));
/*     */       }
/*     */ 
/*  82 */       this.rubyCharValueEscape[10] = "\\n";
/*  83 */       this.rubyCharValueEscape[13] = "\\r";
/*  84 */       this.rubyCharValueEscape[9] = "\\t";
/*  85 */       this.rubyCharValueEscape[8] = "\\b";
/*  86 */       this.rubyCharValueEscape[12] = "\\f";
/*  87 */       this.rubyCharValueEscape[92] = "\\\\";
/*  88 */       this.rubyCharValueEscape[34] = "\\\"";
/*     */     }
/*     */ 
/*     */     public String toString(Object o) {
/*  92 */       return o.toString();
/*     */     }
/*     */ 
/*     */     public String toString(Object o, String formatName) {
/*  96 */       String idString = o.toString();
/*     */ 
/*  98 */       if (idString.isEmpty()) return idString;
/*     */ 
/* 100 */       if (formatName.equals("snakecase"))
/* 101 */         return snakecase(idString);
/* 102 */       if (formatName.equals("camelcase"))
/* 103 */         return camelcase(idString);
/* 104 */       if (formatName.equals("subcamelcase"))
/* 105 */         return subcamelcase(idString);
/* 106 */       if (formatName.equals("constant"))
/* 107 */         return constantcase(idString);
/* 108 */       if (formatName.equals("platform"))
/* 109 */         return platform(idString);
/* 110 */       if (formatName.equals("lexerRule"))
/* 111 */         return lexerRule(idString);
/* 112 */       if (formatName.equals("constantPath"))
/* 113 */         return constantPath(idString);
/* 114 */       if (formatName.equals("rubyString"))
/* 115 */         return rubyString(idString);
/* 116 */       if (formatName.equals("label"))
/* 117 */         return label(idString);
/* 118 */       if (formatName.equals("symbol")) {
/* 119 */         return symbol(idString);
/*     */       }
/* 121 */       throw new IllegalArgumentException("Unsupported format name");
/*     */     }
/*     */ 
/*     */     private String snakecase(String value)
/*     */     {
/* 142 */       StringBuilder output_buffer = new StringBuilder();
/* 143 */       int l = value.length();
/* 144 */       int cliff = l - 1;
/*     */ 
/* 149 */       if (value.isEmpty()) return value;
/* 150 */       if (l == 1) return value.toLowerCase();
/*     */ 
/* 152 */       for (int i = 0; i < cliff; i++) {
/* 153 */         char cur = value.charAt(i);
/* 154 */         char next = value.charAt(i + 1);
/*     */ 
/* 156 */         if (Character.isLetter(cur)) {
/* 157 */           output_buffer.append(Character.toLowerCase(cur));
/*     */ 
/* 159 */           if ((Character.isDigit(next)) || (Character.isWhitespace(next))) {
/* 160 */             output_buffer.append('_');
/* 161 */           } else if ((Character.isLowerCase(cur)) && (Character.isUpperCase(next)))
/*     */           {
/* 163 */             output_buffer.append('_');
/* 164 */           } else if ((i < cliff - 1) && (Character.isUpperCase(cur)) && (Character.isUpperCase(next)))
/*     */           {
/* 167 */             char peek = value.charAt(i + 2);
/* 168 */             if (Character.isLowerCase(peek))
/*     */             {
/* 171 */               output_buffer.append('_');
/*     */             }
/*     */           }
/* 174 */         } else if (Character.isDigit(cur)) {
/* 175 */           output_buffer.append(cur);
/* 176 */           if (Character.isLetter(next))
/* 177 */             output_buffer.append('_');
/*     */         }
/* 179 */         else if (!Character.isWhitespace(cur))
/*     */         {
/* 182 */           output_buffer.append(cur);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 187 */       char cur = value.charAt(cliff);
/* 188 */       if (!Character.isWhitespace(cur)) {
/* 189 */         output_buffer.append(Character.toLowerCase(cur));
/*     */       }
/*     */ 
/* 192 */       return output_buffer.toString();
/*     */     }
/*     */ 
/*     */     private String constantcase(String value) {
/* 196 */       return snakecase(value).toUpperCase();
/*     */     }
/*     */ 
/*     */     private String platform(String value) {
/* 200 */       return "__" + value + "__";
/*     */     }
/*     */ 
/*     */     private String symbol(String value) {
/* 204 */       if (value.matches("[a-zA-Z_]\\w*[\\?\\!\\=]?")) {
/* 205 */         return ":" + value;
/*     */       }
/* 207 */       return "%s(" + value + ")";
/*     */     }
/*     */ 
/*     */     private String lexerRule(String value)
/*     */     {
/* 213 */       if (value.equals("Tokens"))
/*     */       {
/* 215 */         return "token!";
/*     */       }
/*     */ 
/* 219 */       return snakecase(value) + "!";
/*     */     }
/*     */ 
/*     */     private String constantPath(String value)
/*     */     {
/* 224 */       return value.replaceAll("\\.", "::");
/*     */     }
/*     */ 
/*     */     private String rubyString(String value) {
/* 228 */       StringBuilder output_buffer = new StringBuilder();
/* 229 */       int len = value.length();
/*     */ 
/* 231 */       output_buffer.append('"');
/* 232 */       for (int i = 0; i < len; i++) {
/* 233 */         output_buffer.append(this.rubyCharValueEscape[value.charAt(i)]);
/*     */       }
/* 235 */       output_buffer.append('"');
/* 236 */       return output_buffer.toString();
/*     */     }
/*     */ 
/*     */     private String camelcase(String value) {
/* 240 */       StringBuilder output_buffer = new StringBuilder();
/* 241 */       int cliff = value.length();
/*     */ 
/* 244 */       boolean at_edge = true;
/*     */ 
/* 246 */       if (value.isEmpty()) return value;
/* 247 */       if (cliff == 1) return value.toUpperCase();
/*     */ 
/* 249 */       for (int i = 0; i < cliff; i++) {
/* 250 */         char cur = value.charAt(i);
/*     */ 
/* 252 */         if (Character.isWhitespace(cur)) {
/* 253 */           at_edge = true;
/*     */         }
/* 255 */         else if (cur == '_') {
/* 256 */           at_edge = true;
/*     */         }
/* 258 */         else if (Character.isDigit(cur)) {
/* 259 */           output_buffer.append(cur);
/* 260 */           at_edge = true;
/*     */         }
/* 264 */         else if (at_edge) {
/* 265 */           output_buffer.append(Character.toUpperCase(cur));
/* 266 */           if (Character.isLetter(cur)) at_edge = false; 
/*     */         }
/* 268 */         else { output_buffer.append(cur); }
/*     */ 
/*     */       }
/*     */ 
/* 272 */       return output_buffer.toString();
/*     */     }
/*     */ 
/*     */     private String label(String value) {
/* 276 */       if (RubyTarget.rubyKeywords.contains(value))
/* 277 */         return platform(value);
/* 278 */       if ((Character.isUpperCase(value.charAt(0))) && (!value.equals("FILE")) && (!value.equals("LINE")))
/*     */       {
/* 281 */         return platform(value);
/* 282 */       }if (value.equals("FILE"))
/* 283 */         return "_FILE_";
/* 284 */       if (value.equals("LINE")) {
/* 285 */         return "_LINE_";
/*     */       }
/* 287 */       return value;
/*     */     }
/*     */ 
/*     */     private String subcamelcase(String value)
/*     */     {
/* 292 */       value = camelcase(value);
/* 293 */       if (value.isEmpty())
/* 294 */         return value;
/* 295 */       Character head = new Character(Character.toLowerCase(value.charAt(0)));
/* 296 */       String tail = value.substring(1);
/* 297 */       return head.toString().concat(tail);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.codegen.RubyTarget
 * JD-Core Version:    0.6.2
 */