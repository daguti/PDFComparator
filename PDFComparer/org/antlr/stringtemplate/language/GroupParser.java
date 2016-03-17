/*     */ package org.antlr.stringtemplate.language;
/*     */ 
/*     */ import antlr.LLkParser;
/*     */ import antlr.NoViableAltException;
/*     */ import antlr.ParserSharedInputState;
/*     */ import antlr.RecognitionException;
/*     */ import antlr.SemanticException;
/*     */ import antlr.Token;
/*     */ import antlr.TokenBuffer;
/*     */ import antlr.TokenStream;
/*     */ import antlr.TokenStreamException;
/*     */ import antlr.collections.impl.BitSet;
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.antlr.stringtemplate.StringTemplate;
/*     */ import org.antlr.stringtemplate.StringTemplateGroup;
/*     */ 
/*     */ public class GroupParser extends LLkParser
/*     */   implements GroupParserTokenTypes
/*     */ {
/*     */   protected StringTemplateGroup group;
/* 635 */   public static final String[] _tokenNames = { "<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "\"group\"", "ID", "COLON", "\"implements\"", "COMMA", "SEMI", "AT", "DOT", "LPAREN", "RPAREN", "DEFINED_TO_BE", "STRING", "BIGSTRING", "ASSIGN", "ANONYMOUS_TEMPLATE", "LBRACK", "RBRACK", "\"default\"", "STAR", "PLUS", "OPTIONAL", "SL_COMMENT", "ML_COMMENT", "WS" };
/*     */ 
/* 670 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/*     */ 
/* 675 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/*     */ 
/* 680 */   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
/*     */ 
/* 685 */   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
/*     */ 
/* 690 */   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
/*     */ 
/* 695 */   public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
/*     */ 
/*     */   public void reportError(RecognitionException e)
/*     */   {
/*  75 */     if (this.group != null) {
/*  76 */       this.group.error("template group parse error", e);
/*     */     }
/*     */     else {
/*  79 */       System.err.println("template group parse error: " + e);
/*  80 */       e.printStackTrace(System.err);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected GroupParser(TokenBuffer tokenBuf, int k) {
/*  85 */     super(tokenBuf, k);
/*  86 */     this.tokenNames = _tokenNames;
/*     */   }
/*     */ 
/*     */   public GroupParser(TokenBuffer tokenBuf) {
/*  90 */     this(tokenBuf, 3);
/*     */   }
/*     */ 
/*     */   protected GroupParser(TokenStream lexer, int k) {
/*  94 */     super(lexer, k);
/*  95 */     this.tokenNames = _tokenNames;
/*     */   }
/*     */ 
/*     */   public GroupParser(TokenStream lexer) {
/*  99 */     this(lexer, 3);
/*     */   }
/*     */ 
/*     */   public GroupParser(ParserSharedInputState state) {
/* 103 */     super(state, 3);
/* 104 */     this.tokenNames = _tokenNames;
/*     */   }
/*     */ 
/*     */   public final void group(StringTemplateGroup g)
/*     */     throws RecognitionException, TokenStreamException
/*     */   {
/* 111 */     Token name = null;
/* 112 */     Token s = null;
/* 113 */     Token i = null;
/* 114 */     Token i2 = null;
/*     */ 
/* 116 */     this.group = g;
/*     */     try
/*     */     {
/* 120 */       match(4);
/* 121 */       name = LT(1);
/* 122 */       match(5);
/* 123 */       g.setName(name.getText());
/*     */ 
/* 125 */       switch (LA(1))
/*     */       {
/*     */       case 6:
/* 128 */         match(6);
/* 129 */         s = LT(1);
/* 130 */         match(5);
/* 131 */         g.setSuperGroup(s.getText());
/* 132 */         break;
/*     */       case 7:
/*     */       case 9:
/* 137 */         break;
/*     */       case 8:
/*     */       default:
/* 141 */         throw new NoViableAltException(LT(1), getFilename());
/*     */       }
/*     */ 
/* 146 */       switch (LA(1))
/*     */       {
/*     */       case 7:
/* 149 */         match(7);
/* 150 */         i = LT(1);
/* 151 */         match(5);
/* 152 */         g.implementInterface(i.getText());
/*     */       case 9:
/*     */       }
/*     */ 
/* 156 */       while (LA(1) == 8) {
/* 157 */         match(8);
/* 158 */         i2 = LT(1);
/* 159 */         match(5);
/* 160 */         g.implementInterface(i2.getText()); continue;
/*     */ 
/* 172 */         break;
/*     */ 
/* 176 */         throw new NoViableAltException(LT(1), getFilename());
/*     */       }
/*     */ 
/* 180 */       match(9);
/*     */ 
/* 182 */       int _cnt7 = 0;
/*     */       while (true)
/*     */       {
/* 185 */         if (((LA(1) == 5) || (LA(1) == 10)) && ((LA(2) == 5) || (LA(2) == 12) || (LA(2) == 14)) && ((LA(3) == 5) || (LA(3) == 11) || (LA(3) == 13))) {
/* 186 */           template(g);
/*     */         }
/* 188 */         else if ((LA(1) == 5) && (LA(2) == 14) && (LA(3) == 19)) {
/* 189 */           mapdef(g);
/*     */         }
/*     */         else {
/* 192 */           if (_cnt7 >= 1) break; throw new NoViableAltException(LT(1), getFilename());
/*     */         }
/*     */ 
/* 195 */         _cnt7++;
/*     */       }
/*     */     }
/*     */     catch (RecognitionException ex)
/*     */     {
/* 200 */       reportError(ex);
/* 201 */       recover(ex, _tokenSet_0);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void template(StringTemplateGroup g)
/*     */     throws RecognitionException, TokenStreamException
/*     */   {
/* 209 */     Token scope = null;
/* 210 */     Token region = null;
/* 211 */     Token name = null;
/* 212 */     Token t = null;
/* 213 */     Token bt = null;
/* 214 */     Token alias = null;
/* 215 */     Token target = null;
/*     */ 
/* 217 */     Map formalArgs = null;
/* 218 */     StringTemplate st = null;
/* 219 */     boolean ignore = false;
/* 220 */     String templateName = null;
/* 221 */     int line = LT(1).getLine();
/*     */     try
/*     */     {
/* 225 */       if (((LA(1) == 5) || (LA(1) == 10)) && ((LA(2) == 5) || (LA(2) == 12)))
/*     */       {
/* 227 */         switch (LA(1))
/*     */         {
/*     */         case 10:
/* 230 */           match(10);
/* 231 */           scope = LT(1);
/* 232 */           match(5);
/* 233 */           match(11);
/* 234 */           region = LT(1);
/* 235 */           match(5);
/*     */ 
/* 237 */           templateName = g.getMangledRegionName(scope.getText(), region.getText());
/* 238 */           if (g.isDefinedInThisGroup(templateName)) {
/* 239 */             g.error("group " + g.getName() + " line " + line + ": redefinition of template region: @" + scope.getText() + "." + region.getText());
/*     */ 
/* 241 */             st = new StringTemplate();
/*     */           }
/*     */           else {
/* 244 */             boolean err = false;
/*     */ 
/* 246 */             StringTemplate scopeST = g.lookupTemplate(scope.getText());
/* 247 */             if (scopeST == null) {
/* 248 */               g.error("group " + g.getName() + " line " + line + ": reference to region within undefined template: " + scope.getText());
/*     */ 
/* 250 */               err = true;
/*     */             }
/* 252 */             if (!scopeST.containsRegionName(region.getText())) {
/* 253 */               g.error("group " + g.getName() + " line " + line + ": template " + scope.getText() + " has no region called " + region.getText());
/*     */ 
/* 255 */               err = true;
/*     */             }
/* 257 */             if (err) {
/* 258 */               st = new StringTemplate();
/*     */             }
/*     */             else {
/* 261 */               st = g.defineRegionTemplate(scope.getText(), region.getText(), null, 3);
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 268 */           break;
/*     */         case 5:
/* 272 */           name = LT(1);
/* 273 */           match(5);
/* 274 */           templateName = name.getText();
/*     */ 
/* 276 */           if (g.isDefinedInThisGroup(templateName)) {
/* 277 */             g.error("redefinition of template: " + templateName);
/* 278 */             st = new StringTemplate();
/*     */           }
/*     */           else {
/* 281 */             st = g.defineTemplate(templateName, null);
/*     */           }
/*     */ 
/* 284 */           break;
/*     */         default:
/* 288 */           throw new NoViableAltException(LT(1), getFilename());
/*     */         }
/*     */ 
/* 292 */         if (st != null) st.setGroupFileLine(line);
/* 293 */         match(12);
/*     */ 
/* 295 */         switch (LA(1))
/*     */         {
/*     */         case 5:
/* 298 */           args(st);
/* 299 */           break;
/*     */         case 13:
/* 303 */           st.defineEmptyFormalArgumentList();
/* 304 */           break;
/*     */         default:
/* 308 */           throw new NoViableAltException(LT(1), getFilename());
/*     */         }
/*     */ 
/* 312 */         match(13);
/* 313 */         match(14);
/*     */       }
/* 315 */       switch (LA(1))
/*     */       {
/*     */       case 15:
/* 318 */         t = LT(1);
/* 319 */         match(15);
/* 320 */         st.setTemplate(t.getText());
/* 321 */         break;
/*     */       case 16:
/* 325 */         bt = LT(1);
/* 326 */         match(16);
/* 327 */         st.setTemplate(bt.getText());
/* 328 */         break;
/*     */       default:
/* 332 */         throw new NoViableAltException(LT(1), getFilename());
/*     */ 
/* 337 */         if ((LA(1) == 5) && (LA(2) == 14)) {
/* 338 */           alias = LT(1);
/* 339 */           match(5);
/* 340 */           match(14);
/* 341 */           target = LT(1);
/* 342 */           match(5);
/* 343 */           g.defineTemplateAlias(alias.getText(), target.getText());
/*     */         }
/*     */         else {
/* 346 */           throw new NoViableAltException(LT(1), getFilename());
/*     */         }
/*     */         break;
/*     */       }
/*     */     } catch (RecognitionException ex) {
/* 351 */       reportError(ex);
/* 352 */       recover(ex, _tokenSet_1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void mapdef(StringTemplateGroup g)
/*     */     throws RecognitionException, TokenStreamException
/*     */   {
/* 360 */     Token name = null;
/*     */ 
/* 362 */     Map m = null;
/*     */     try
/*     */     {
/* 366 */       name = LT(1);
/* 367 */       match(5);
/* 368 */       match(14);
/* 369 */       m = map();
/*     */ 
/* 371 */       if (g.getMap(name.getText()) != null) {
/* 372 */         g.error("redefinition of map: " + name.getText());
/*     */       }
/* 374 */       else if (g.isDefinedInThisGroup(name.getText())) {
/* 375 */         g.error("redefinition of template as map: " + name.getText());
/*     */       }
/*     */       else {
/* 378 */         g.defineMap(name.getText(), m);
/*     */       }
/*     */     }
/*     */     catch (RecognitionException ex)
/*     */     {
/* 383 */       reportError(ex);
/* 384 */       recover(ex, _tokenSet_1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void args(StringTemplate st)
/*     */     throws RecognitionException, TokenStreamException
/*     */   {
/*     */     try
/*     */     {
/* 394 */       arg(st);
/*     */ 
/* 398 */       while (LA(1) == 8) {
/* 399 */         match(8);
/* 400 */         arg(st);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (RecognitionException ex)
/*     */     {
/* 410 */       reportError(ex);
/* 411 */       recover(ex, _tokenSet_2);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void arg(StringTemplate st)
/*     */     throws RecognitionException, TokenStreamException
/*     */   {
/* 419 */     Token name = null;
/* 420 */     Token s = null;
/* 421 */     Token bs = null;
/*     */ 
/* 423 */     StringTemplate defaultValue = null;
/*     */     try
/*     */     {
/* 427 */       name = LT(1);
/* 428 */       match(5);
/*     */ 
/* 430 */       if ((LA(1) == 17) && (LA(2) == 15)) {
/* 431 */         match(17);
/* 432 */         s = LT(1);
/* 433 */         match(15);
/*     */ 
/* 435 */         defaultValue = new StringTemplate("$_val_$");
/* 436 */         defaultValue.setAttribute("_val_", s.getText());
/* 437 */         defaultValue.defineFormalArgument("_val_");
/* 438 */         defaultValue.setName("<" + st.getName() + "'s arg " + name.getText() + " default value subtemplate>");
/*     */       }
/* 441 */       else if ((LA(1) == 17) && (LA(2) == 18)) {
/* 442 */         match(17);
/* 443 */         bs = LT(1);
/* 444 */         match(18);
/*     */ 
/* 446 */         defaultValue = new StringTemplate(st.getGroup(), bs.getText());
/* 447 */         defaultValue.setName("<" + st.getName() + "'s arg " + name.getText() + " default value subtemplate>");
/*     */       }
/* 450 */       else if ((LA(1) != 8) && (LA(1) != 13))
/*     */       {
/* 453 */         throw new NoViableAltException(LT(1), getFilename());
/*     */       }
/*     */ 
/* 457 */       st.defineFormalArgument(name.getText(), defaultValue);
/*     */     }
/*     */     catch (RecognitionException ex) {
/* 460 */       reportError(ex);
/* 461 */       recover(ex, _tokenSet_3);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final Map map() throws RecognitionException, TokenStreamException {
/* 466 */     Map mapping = new HashMap();
/*     */     try
/*     */     {
/* 470 */       match(19);
/* 471 */       mapPairs(mapping);
/* 472 */       match(20);
/*     */     }
/*     */     catch (RecognitionException ex) {
/* 475 */       reportError(ex);
/* 476 */       recover(ex, _tokenSet_1);
/*     */     }
/* 478 */     return mapping;
/*     */   }
/*     */ 
/*     */   public final void mapPairs(Map mapping)
/*     */     throws RecognitionException, TokenStreamException
/*     */   {
/*     */     try
/*     */     {
/* 487 */       switch (LA(1))
/*     */       {
/*     */       case 15:
/* 490 */         keyValuePair(mapping);
/*     */ 
/* 494 */         while ((LA(1) == 8) && (LA(2) == 15)) {
/* 495 */           match(8);
/* 496 */           keyValuePair(mapping);
/*     */         }
/*     */ 
/* 505 */         switch (LA(1))
/*     */         {
/*     */         case 8:
/* 508 */           match(8);
/* 509 */           defaultValuePair(mapping);
/* 510 */           break;
/*     */         case 20:
/* 514 */           break;
/*     */         default:
/* 518 */           throw new NoViableAltException(LT(1), getFilename());
/*     */         }
/*     */ 
/*     */         break;
/*     */       case 21:
/* 526 */         defaultValuePair(mapping);
/* 527 */         break;
/*     */       default:
/* 531 */         throw new NoViableAltException(LT(1), getFilename());
/*     */       }
/*     */     }
/*     */     catch (RecognitionException ex)
/*     */     {
/* 536 */       reportError(ex);
/* 537 */       recover(ex, _tokenSet_4);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void keyValuePair(Map mapping)
/*     */     throws RecognitionException, TokenStreamException
/*     */   {
/* 545 */     Token key = null;
/*     */ 
/* 547 */     StringTemplate v = null;
/*     */     try
/*     */     {
/* 551 */       key = LT(1);
/* 552 */       match(15);
/* 553 */       match(6);
/* 554 */       v = keyValue();
/* 555 */       mapping.put(key.getText(), v);
/*     */     }
/*     */     catch (RecognitionException ex) {
/* 558 */       reportError(ex);
/* 559 */       recover(ex, _tokenSet_5);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void defaultValuePair(Map mapping)
/*     */     throws RecognitionException, TokenStreamException
/*     */   {
/* 568 */     StringTemplate v = null;
/*     */     try
/*     */     {
/* 572 */       match(21);
/* 573 */       match(6);
/* 574 */       v = keyValue();
/* 575 */       mapping.put("_default_", v);
/*     */     }
/*     */     catch (RecognitionException ex) {
/* 578 */       reportError(ex);
/* 579 */       recover(ex, _tokenSet_4);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final StringTemplate keyValue() throws RecognitionException, TokenStreamException {
/* 584 */     StringTemplate value = null;
/*     */ 
/* 586 */     Token s1 = null;
/* 587 */     Token s2 = null;
/* 588 */     Token k = null;
/*     */     try
/*     */     {
/* 591 */       switch (LA(1))
/*     */       {
/*     */       case 16:
/* 594 */         s1 = LT(1);
/* 595 */         match(16);
/* 596 */         value = new StringTemplate(this.group, s1.getText());
/* 597 */         break;
/*     */       case 15:
/* 601 */         s2 = LT(1);
/* 602 */         match(15);
/* 603 */         value = new StringTemplate(this.group, s2.getText());
/* 604 */         break;
/*     */       case 5:
/* 608 */         k = LT(1);
/* 609 */         match(5);
/* 610 */         if (!k.getText().equals("key"))
/* 611 */           throw new SemanticException("k.getText().equals(\"key\")");
/* 612 */         value = ASTExpr.MAP_KEY_VALUE;
/* 613 */         break;
/*     */       case 8:
/*     */       case 20:
/* 618 */         value = null;
/* 619 */         break;
/*     */       default:
/* 623 */         throw new NoViableAltException(LT(1), getFilename());
/*     */       }
/*     */     }
/*     */     catch (RecognitionException ex)
/*     */     {
/* 628 */       reportError(ex);
/* 629 */       recover(ex, _tokenSet_5);
/*     */     }
/* 631 */     return value;
/*     */   }
/*     */ 
/*     */   private static final long[] mk_tokenSet_0()
/*     */   {
/* 667 */     long[] data = { 2L, 0L };
/* 668 */     return data;
/*     */   }
/*     */ 
/*     */   private static final long[] mk_tokenSet_1() {
/* 672 */     long[] data = { 1058L, 0L };
/* 673 */     return data;
/*     */   }
/*     */ 
/*     */   private static final long[] mk_tokenSet_2() {
/* 677 */     long[] data = { 8192L, 0L };
/* 678 */     return data;
/*     */   }
/*     */ 
/*     */   private static final long[] mk_tokenSet_3() {
/* 682 */     long[] data = { 8448L, 0L };
/* 683 */     return data;
/*     */   }
/*     */ 
/*     */   private static final long[] mk_tokenSet_4() {
/* 687 */     long[] data = { 1048576L, 0L };
/* 688 */     return data;
/*     */   }
/*     */ 
/*     */   private static final long[] mk_tokenSet_5() {
/* 692 */     long[] data = { 1048832L, 0L };
/* 693 */     return data;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.stringtemplate.language.GroupParser
 * JD-Core Version:    0.6.2
 */