/*     */ package org.stringtemplate.v4.compiler;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.antlr.runtime.CharStream;
/*     */ import org.antlr.runtime.CommonToken;
/*     */ import org.antlr.runtime.MismatchedTokenException;
/*     */ import org.antlr.runtime.NoViableAltException;
/*     */ import org.antlr.runtime.RecognitionException;
/*     */ import org.antlr.runtime.Token;
/*     */ import org.antlr.runtime.TokenSource;
/*     */ import org.stringtemplate.v4.STGroup;
/*     */ import org.stringtemplate.v4.misc.ErrorManager;
/*     */ import org.stringtemplate.v4.misc.Misc;
/*     */ 
/*     */ public class STLexer
/*     */   implements TokenSource
/*     */ {
/*     */   public static final char EOF = 'èøø';
/*     */   public static final int EOF_TYPE = -1;
/*  76 */   public static final Token SKIP = new STToken(-1, "<skip>");
/*     */   public static final int RBRACK = 17;
/*     */   public static final int LBRACK = 16;
/*     */   public static final int ELSE = 5;
/*     */   public static final int ELLIPSIS = 11;
/*     */   public static final int LCURLY = 20;
/*     */   public static final int BANG = 10;
/*     */   public static final int EQUALS = 12;
/*     */   public static final int TEXT = 22;
/*     */   public static final int ID = 25;
/*     */   public static final int SEMI = 9;
/*     */   public static final int LPAREN = 14;
/*     */   public static final int IF = 4;
/*     */   public static final int ELSEIF = 6;
/*     */   public static final int COLON = 13;
/*     */   public static final int RPAREN = 15;
/*     */   public static final int COMMA = 18;
/*     */   public static final int RCURLY = 21;
/*     */   public static final int ENDIF = 7;
/*     */   public static final int RDELIM = 24;
/*     */   public static final int SUPER = 8;
/*     */   public static final int DOT = 19;
/*     */   public static final int LDELIM = 23;
/*     */   public static final int STRING = 26;
/*     */   public static final int PIPE = 28;
/*     */   public static final int OR = 29;
/*     */   public static final int AND = 30;
/*     */   public static final int INDENT = 31;
/*     */   public static final int NEWLINE = 32;
/*     */   public static final int AT = 33;
/*     */   public static final int REGION_END = 34;
/*     */   public static final int TRUE = 35;
/*     */   public static final int FALSE = 36;
/*     */   public static final int COMMENT = 37;
/* 115 */   char delimiterStartChar = '<';
/* 116 */   char delimiterStopChar = '>';
/*     */ 
/* 121 */   boolean scanningInsideExpr = false;
/*     */ 
/* 128 */   public int subtemplateDepth = 0;
/*     */   ErrorManager errMgr;
/*     */   Token templateToken;
/*     */   CharStream input;
/*     */   char c;
/*     */   int startCharIndex;
/*     */   int startLine;
/*     */   int startCharPositionInLine;
/* 147 */   List<Token> tokens = new ArrayList();
/*     */ 
/* 149 */   public STLexer(CharStream input) { this(STGroup.DEFAULT_ERR_MGR, input, null, '<', '>'); }
/*     */ 
/*     */   public STLexer(ErrorManager errMgr, CharStream input, Token templateToken) {
/* 152 */     this(errMgr, input, templateToken, '<', '>');
/*     */   }
/*     */ 
/*     */   public STLexer(ErrorManager errMgr, CharStream input, Token templateToken, char delimiterStartChar, char delimiterStopChar)
/*     */   {
/* 161 */     this.errMgr = errMgr;
/* 162 */     this.input = input;
/* 163 */     this.c = ((char)input.LA(1));
/* 164 */     this.templateToken = templateToken;
/* 165 */     this.delimiterStartChar = delimiterStartChar;
/* 166 */     this.delimiterStopChar = delimiterStopChar;
/*     */   }
/*     */ 
/*     */   public Token nextToken() {
/* 170 */     Token t = null;
/* 171 */     if (this.tokens.size() > 0) t = (Token)this.tokens.remove(0); else {
/* 172 */       t = _nextToken();
/*     */     }
/* 174 */     return t;
/*     */   }
/*     */ 
/*     */   public void match(char x)
/*     */   {
/* 179 */     if (this.c != x) {
/* 180 */       NoViableAltException e = new NoViableAltException("", 0, 0, this.input);
/* 181 */       this.errMgr.lexerError(this.input.getSourceName(), "expecting '" + x + "', found '" + str(this.c) + "'", this.templateToken, e);
/*     */     }
/* 183 */     consume();
/*     */   }
/*     */ 
/*     */   protected void consume() {
/* 187 */     this.input.consume();
/* 188 */     this.c = ((char)this.input.LA(1));
/*     */   }
/*     */   public void emit(Token token) {
/* 191 */     this.tokens.add(token);
/*     */   }
/*     */ 
/*     */   public Token _nextToken() {
/*     */     while (true) {
/* 196 */       this.startCharIndex = this.input.index();
/* 197 */       this.startLine = this.input.getLine();
/* 198 */       this.startCharPositionInLine = this.input.getCharPositionInLine();
/*     */ 
/* 200 */       if (this.c == 65535) return newToken(-1);
/* 202 */       Token t;
/*     */       Token t;
/* 202 */       if (this.scanningInsideExpr) t = inside(); else
/* 203 */         t = outside();
/* 204 */       if (t != SKIP) return t; 
/*     */     }
/*     */   }
/*     */ 
/*     */   protected Token outside()
/*     */   {
/* 209 */     if ((this.input.getCharPositionInLine() == 0) && ((this.c == ' ') || (this.c == '\t'))) {
/* 210 */       while ((this.c == ' ') || (this.c == '\t')) consume();
/* 211 */       if (this.c != 65535) return newToken(31);
/* 212 */       return newToken(22);
/*     */     }
/* 214 */     if (this.c == this.delimiterStartChar) {
/* 215 */       consume();
/* 216 */       if (this.c == '!') return COMMENT();
/* 217 */       if (this.c == '\\') return ESCAPE();
/* 218 */       this.scanningInsideExpr = true;
/* 219 */       return newToken(23);
/*     */     }
/* 221 */     if (this.c == '\r') { consume(); consume(); return newToken(32); }
/* 222 */     if (this.c == '\n') { consume(); return newToken(32); }
/* 223 */     if ((this.c == '}') && (this.subtemplateDepth > 0)) {
/* 224 */       this.scanningInsideExpr = true;
/* 225 */       this.subtemplateDepth -= 1;
/* 226 */       consume();
/* 227 */       return newTokenFromPreviousChar(21);
/*     */     }
/* 229 */     return mTEXT();
/*     */   }
/*     */ 
/*     */   protected Token inside() {
/*     */     while (true) {
/* 234 */       switch (this.c) { case '\t':
/*     */       case '\n':
/*     */       case '\r':
/*     */       case ' ':
/* 236 */         consume();
/* 237 */         return SKIP;
/*     */       case '.':
/* 239 */         consume();
/* 240 */         if ((this.input.LA(1) == 46) && (this.input.LA(2) == 46)) {
/* 241 */           consume();
/* 242 */           match('.');
/* 243 */           return newToken(11);
/*     */         }
/* 245 */         return newToken(19);
/*     */       case ',':
/* 246 */         consume(); return newToken(18);
/*     */       case ':':
/* 247 */         consume(); return newToken(13);
/*     */       case ';':
/* 248 */         consume(); return newToken(9);
/*     */       case '(':
/* 249 */         consume(); return newToken(14);
/*     */       case ')':
/* 250 */         consume(); return newToken(15);
/*     */       case '[':
/* 251 */         consume(); return newToken(16);
/*     */       case ']':
/* 252 */         consume(); return newToken(17);
/*     */       case '=':
/* 253 */         consume(); return newToken(12);
/*     */       case '!':
/* 254 */         consume(); return newToken(10);
/*     */       case '@':
/* 256 */         consume();
/* 257 */         if ((this.c == 'e') && (this.input.LA(2) == 110) && (this.input.LA(3) == 100)) {
/* 258 */           consume(); consume(); consume();
/* 259 */           return newToken(34);
/*     */         }
/* 261 */         return newToken(33);
/*     */       case '"':
/* 262 */         return mSTRING();
/*     */       case '&':
/* 263 */         consume(); match('&'); return newToken(30);
/*     */       case '|':
/* 264 */         consume(); match('|'); return newToken(29);
/*     */       case '{':
/* 265 */         return subTemplate();
/*     */       }
/* 267 */       if (this.c == this.delimiterStopChar) {
/* 268 */         consume();
/* 269 */         this.scanningInsideExpr = false;
/* 270 */         return newToken(24);
/*     */       }
/* 272 */       if (isIDStartLetter(this.c)) {
/* 273 */         Token id = mID();
/* 274 */         String name = id.getText();
/* 275 */         if (name.equals("if")) return newToken(4);
/* 276 */         if (name.equals("endif")) return newToken(7);
/* 277 */         if (name.equals("else")) return newToken(5);
/* 278 */         if (name.equals("elseif")) return newToken(6);
/* 279 */         if (name.equals("super")) return newToken(8);
/* 280 */         if (name.equals("true")) return newToken(35);
/* 281 */         if (name.equals("false")) return newToken(36);
/* 282 */         return id;
/*     */       }
/* 284 */       RecognitionException re = new NoViableAltException("", 0, 0, this.input);
/*     */ 
/* 286 */       re.line = this.startLine;
/* 287 */       re.charPositionInLine = this.startCharPositionInLine;
/* 288 */       this.errMgr.lexerError(this.input.getSourceName(), "invalid character '" + str(this.c) + "'", this.templateToken, re);
/* 289 */       if (this.c == 65535) {
/* 290 */         return newToken(-1);
/*     */       }
/* 292 */       consume();
/*     */     }
/*     */   }
/*     */ 
/*     */   Token subTemplate()
/*     */   {
/* 299 */     this.subtemplateDepth += 1;
/* 300 */     int m = this.input.mark();
/* 301 */     int curlyStartChar = this.startCharIndex;
/* 302 */     int curlyLine = this.startLine;
/* 303 */     int curlyPos = this.startCharPositionInLine;
/* 304 */     List argTokens = new ArrayList();
/* 305 */     consume();
/* 306 */     Token curly = newTokenFromPreviousChar(20);
/* 307 */     WS();
/* 308 */     argTokens.add(mID());
/* 309 */     WS();
/* 310 */     while (this.c == ',') {
/* 311 */       consume();
/* 312 */       argTokens.add(newTokenFromPreviousChar(18));
/* 313 */       WS();
/* 314 */       argTokens.add(mID());
/* 315 */       WS();
/*     */     }
/* 317 */     WS();
/* 318 */     if (this.c == '|') {
/* 319 */       consume();
/* 320 */       argTokens.add(newTokenFromPreviousChar(28));
/* 321 */       if (isWS(this.c)) consume();
/* 323 */       Token t;
/* 323 */       for (Iterator i$ = argTokens.iterator(); i$.hasNext(); emit(t)) t = (Token)i$.next();
/* 324 */       this.input.release(m);
/* 325 */       this.scanningInsideExpr = false;
/* 326 */       this.startCharIndex = curlyStartChar;
/* 327 */       this.startLine = curlyLine;
/* 328 */       this.startCharPositionInLine = curlyPos;
/* 329 */       return curly;
/*     */     }
/* 331 */     this.input.rewind(m);
/* 332 */     this.startCharIndex = curlyStartChar;
/* 333 */     this.startLine = curlyLine;
/* 334 */     this.startCharPositionInLine = curlyPos;
/* 335 */     consume();
/* 336 */     this.scanningInsideExpr = false;
/* 337 */     return curly;
/*     */   }
/*     */ 
/*     */   Token ESCAPE() {
/* 341 */     this.startCharIndex = this.input.index();
/* 342 */     this.startCharPositionInLine = this.input.getCharPositionInLine();
/* 343 */     consume();
/* 344 */     if (this.c == 'u') return UNICODE();
/* 345 */     String text = null;
/* 346 */     switch (this.c) { case '\\':
/* 347 */       LINEBREAK(); return SKIP;
/*     */     case 'n':
/* 348 */       text = "\n"; break;
/*     */     case 't':
/* 349 */       text = "\t"; break;
/*     */     case ' ':
/* 350 */       text = " "; break;
/*     */     default:
/* 352 */       NoViableAltException e = new NoViableAltException("", 0, 0, this.input);
/* 353 */       this.errMgr.lexerError(this.input.getSourceName(), "invalid escaped char: '" + str(this.c) + "'", this.templateToken, e);
/* 354 */       consume();
/* 355 */       match(this.delimiterStopChar);
/* 356 */       return SKIP;
/*     */     }
/* 358 */     consume();
/* 359 */     Token t = newToken(22, text, this.input.getCharPositionInLine() - 2);
/* 360 */     match(this.delimiterStopChar);
/* 361 */     return t;
/*     */   }
/*     */ 
/*     */   Token UNICODE() {
/* 365 */     consume();
/* 366 */     char[] chars = new char[4];
/* 367 */     if (!isUnicodeLetter(this.c)) {
/* 368 */       NoViableAltException e = new NoViableAltException("", 0, 0, this.input);
/* 369 */       this.errMgr.lexerError(this.input.getSourceName(), "invalid unicode char: '" + str(this.c) + "'", this.templateToken, e);
/*     */     }
/* 371 */     chars[0] = this.c;
/* 372 */     consume();
/* 373 */     if (!isUnicodeLetter(this.c)) {
/* 374 */       NoViableAltException e = new NoViableAltException("", 0, 0, this.input);
/* 375 */       this.errMgr.lexerError(this.input.getSourceName(), "invalid unicode char: '" + str(this.c) + "'", this.templateToken, e);
/*     */     }
/* 377 */     chars[1] = this.c;
/* 378 */     consume();
/* 379 */     if (!isUnicodeLetter(this.c)) {
/* 380 */       NoViableAltException e = new NoViableAltException("", 0, 0, this.input);
/* 381 */       this.errMgr.lexerError(this.input.getSourceName(), "invalid unicode char: '" + str(this.c) + "'", this.templateToken, e);
/*     */     }
/* 383 */     chars[2] = this.c;
/* 384 */     consume();
/* 385 */     if (!isUnicodeLetter(this.c)) {
/* 386 */       NoViableAltException e = new NoViableAltException("", 0, 0, this.input);
/* 387 */       this.errMgr.lexerError(this.input.getSourceName(), "invalid unicode char: '" + str(this.c) + "'", this.templateToken, e);
/*     */     }
/* 389 */     chars[3] = this.c;
/*     */ 
/* 391 */     char uc = (char)Integer.parseInt(new String(chars), 16);
/* 392 */     Token t = newToken(22, String.valueOf(uc), this.input.getCharPositionInLine() - 6);
/* 393 */     consume();
/* 394 */     match(this.delimiterStopChar);
/* 395 */     return t;
/*     */   }
/*     */ 
/*     */   Token mTEXT() {
/* 399 */     boolean modifiedText = false;
/* 400 */     StringBuilder buf = new StringBuilder();
/* 401 */     while ((this.c != 65535) && (this.c != this.delimiterStartChar) && 
/* 402 */       (this.c != '\r') && (this.c != '\n') && (
/* 403 */       (this.c != '}') || (this.subtemplateDepth <= 0)))
/* 404 */       if (this.c == '\\') {
/* 405 */         if (this.input.LA(2) == 92) {
/* 406 */           consume(); consume(); buf.append('\\');
/* 407 */           modifiedText = true;
/*     */         }
/* 410 */         else if ((this.input.LA(2) == this.delimiterStartChar) || (this.input.LA(2) == 125))
/*     */         {
/* 413 */           modifiedText = true;
/* 414 */           consume();
/* 415 */           buf.append(this.c); consume();
/*     */         }
/*     */         else {
/* 418 */           buf.append(this.c);
/* 419 */           consume();
/*     */         }
/*     */       }
/*     */       else {
/* 423 */         buf.append(this.c);
/* 424 */         consume();
/*     */       }
/* 426 */     if (modifiedText) return newToken(22, buf.toString());
/* 427 */     return newToken(22);
/*     */   }
/*     */ 
/*     */   Token mID()
/*     */   {
/* 433 */     this.startCharIndex = this.input.index();
/* 434 */     this.startLine = this.input.getLine();
/* 435 */     this.startCharPositionInLine = this.input.getCharPositionInLine();
/* 436 */     consume();
/* 437 */     while (isIDLetter(this.c)) {
/* 438 */       consume();
/*     */     }
/* 440 */     return newToken(25);
/*     */   }
/*     */ 
/*     */   Token mSTRING()
/*     */   {
/* 446 */     boolean sawEscape = false;
/* 447 */     StringBuilder buf = new StringBuilder();
/* 448 */     buf.append(this.c); consume();
/* 449 */     while (this.c != '"') {
/* 450 */       if (this.c == '\\') {
/* 451 */         sawEscape = true;
/* 452 */         consume();
/* 453 */         switch (this.c) { case 'n':
/* 454 */           buf.append('\n'); break;
/*     */         case 'r':
/* 455 */           buf.append('\r'); break;
/*     */         case 't':
/* 456 */           buf.append('\t'); break;
/*     */         default:
/* 457 */           buf.append(this.c);
/*     */         }
/* 459 */         consume();
/*     */       }
/*     */       else {
/* 462 */         buf.append(this.c);
/* 463 */         consume();
/* 464 */         if (this.c == 65535) {
/* 465 */           RecognitionException re = new MismatchedTokenException(34, this.input);
/*     */ 
/* 467 */           re.line = this.input.getLine();
/* 468 */           re.charPositionInLine = this.input.getCharPositionInLine();
/* 469 */           this.errMgr.lexerError(this.input.getSourceName(), "EOF in string", this.templateToken, re);
/*     */         }
/*     */       }
/*     */     }
/* 473 */     buf.append(this.c);
/* 474 */     consume();
/* 475 */     if (sawEscape) return newToken(26, buf.toString());
/* 476 */     return newToken(26);
/*     */   }
/*     */ 
/*     */   void WS() {
/* 480 */     while ((this.c == ' ') || (this.c == '\t') || (this.c == '\n') || (this.c == '\r')) consume(); 
/*     */   }
/*     */ 
/*     */   Token COMMENT()
/*     */   {
/* 484 */     match('!');
/* 485 */     while ((this.c != '!') || (this.input.LA(2) != this.delimiterStopChar)) {
/* 486 */       if (this.c == 65535) {
/* 487 */         RecognitionException re = new MismatchedTokenException(33, this.input);
/*     */ 
/* 489 */         re.line = this.input.getLine();
/* 490 */         re.charPositionInLine = this.input.getCharPositionInLine();
/* 491 */         this.errMgr.lexerError(this.input.getSourceName(), "Nonterminated comment starting at " + this.startLine + ":" + this.startCharPositionInLine + ": '!" + this.delimiterStopChar + "' missing", this.templateToken, re);
/*     */ 
/* 494 */         break;
/*     */       }
/* 496 */       consume();
/*     */     }
/* 498 */     consume(); consume();
/* 499 */     return newToken(37);
/*     */   }
/*     */ 
/*     */   void LINEBREAK() {
/* 503 */     match('\\');
/* 504 */     match(this.delimiterStopChar);
/* 505 */     while ((this.c == ' ') || (this.c == '\t')) consume();
/* 506 */     if (this.c == '\r') consume();
/* 507 */     match('\n');
/* 508 */     while ((this.c == ' ') || (this.c == '\t')) consume(); 
/*     */   }
/*     */ 
/* 511 */   public static boolean isIDStartLetter(char c) { return isIDLetter(c); } 
/* 512 */   public static boolean isIDLetter(char c) { return ((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z')) || ((c >= '0') && (c <= '9')) || (c == '_') || (c == '/'); } 
/* 513 */   public static boolean isWS(char c) { return (c == ' ') || (c == '\t') || (c == '\n') || (c == '\r'); } 
/* 514 */   public static boolean isUnicodeLetter(char c) { return ((c >= 'a') && (c <= 'f')) || ((c >= 'A') && (c <= 'F')) || ((c >= '0') && (c <= '9')); }
/*     */ 
/*     */   public Token newToken(int ttype) {
/* 517 */     STToken t = new STToken(this.input, ttype, this.startCharIndex, this.input.index() - 1);
/* 518 */     t.setLine(this.startLine);
/* 519 */     t.setCharPositionInLine(this.startCharPositionInLine);
/* 520 */     return t;
/*     */   }
/*     */ 
/*     */   public Token newTokenFromPreviousChar(int ttype) {
/* 524 */     STToken t = new STToken(this.input, ttype, this.input.index() - 1, this.input.index() - 1);
/* 525 */     t.setLine(this.input.getLine());
/* 526 */     t.setCharPositionInLine(this.input.getCharPositionInLine() - 1);
/* 527 */     return t;
/*     */   }
/*     */ 
/*     */   public Token newToken(int ttype, String text, int pos) {
/* 531 */     STToken t = new STToken(ttype, text);
/* 532 */     t.setStartIndex(this.startCharIndex);
/* 533 */     t.setStopIndex(this.input.index() - 1);
/* 534 */     t.setLine(this.input.getLine());
/* 535 */     t.setCharPositionInLine(pos);
/* 536 */     return t;
/*     */   }
/*     */ 
/*     */   public Token newToken(int ttype, String text) {
/* 540 */     STToken t = new STToken(ttype, text);
/* 541 */     t.setStartIndex(this.startCharIndex);
/* 542 */     t.setStopIndex(this.input.index() - 1);
/* 543 */     t.setLine(this.startLine);
/* 544 */     t.setCharPositionInLine(this.startCharPositionInLine);
/* 545 */     return t;
/*     */   }
/*     */ 
/*     */   public String getSourceName()
/*     */   {
/* 553 */     return "no idea";
/*     */   }
/*     */ 
/*     */   public static String str(int c) {
/* 557 */     if (c == 65535) return "<EOF>";
/* 558 */     return String.valueOf((char)c);
/*     */   }
/*     */ 
/*     */   public static class STToken extends CommonToken
/*     */   {
/*     */     public STToken(CharStream input, int type, int start, int stop)
/*     */     {
/*  57 */       super(type, 0, start, stop);
/*     */     }
/*  59 */     public STToken(int type, String text) { super(text); }
/*     */ 
/*     */     public String toString() {
/*  62 */       String channelStr = "";
/*  63 */       if (this.channel > 0) {
/*  64 */         channelStr = ",channel=" + this.channel;
/*     */       }
/*  66 */       String txt = getText();
/*  67 */       if (txt != null) txt = Misc.replaceEscapes(txt); else
/*  68 */         txt = "<no text>";
/*  69 */       String tokenName = null;
/*  70 */       if (this.type == -1) tokenName = "EOF"; else
/*  71 */         tokenName = STParser.tokenNames[this.type];
/*  72 */       return "[@" + getTokenIndex() + "," + this.start + ":" + this.stop + "='" + txt + "',<" + tokenName + ">" + channelStr + "," + this.line + ":" + getCharPositionInLine() + "]";
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.compiler.STLexer
 * JD-Core Version:    0.6.2
 */