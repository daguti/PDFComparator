/*     */ package org.antlr.tool;
/*     */ 
/*     */ import antlr.BaseAST;
/*     */ import antlr.Token;
/*     */ import antlr.TokenWithIndex;
/*     */ import antlr.collections.AST;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.antlr.analysis.DFA;
/*     */ import org.antlr.analysis.NFAState;
/*     */ import org.antlr.misc.IntSet;
/*     */ import org.antlr.stringtemplate.StringTemplate;
/*     */ 
/*     */ public class GrammarAST extends BaseAST
/*     */ {
/*  60 */   static int count = 0;
/*     */ 
/*  62 */   public int ID = ++count;
/*     */ 
/*  65 */   public Token token = null;
/*     */   public String enclosingRuleName;
/*     */   public int ruleStartTokenIndex;
/*     */   public int ruleStopTokenIndex;
/*  74 */   public DFA lookaheadDFA = null;
/*     */ 
/*  77 */   public NFAState NFAStartState = null;
/*     */ 
/*  83 */   public NFAState NFATreeDownState = null;
/*     */ 
/*  89 */   public NFAState followingNFAState = null;
/*     */ 
/*  92 */   protected IntSet setValue = null;
/*     */   protected Map<String, Object> blockOptions;
/*     */   public Set<GrammarAST> rewriteRefsShallow;
/*     */   public Set<GrammarAST> rewriteRefsDeep;
/*     */   public Map<String, Object> terminalOptions;
/*     */   public int outerAltNum;
/*     */   public StringTemplate code;
/*     */ 
/*     */   public Map<String, Object> getBlockOptions()
/*     */   {
/* 138 */     return this.blockOptions;
/*     */   }
/*     */ 
/*     */   public void setBlockOptions(Map<String, Object> blockOptions)
/*     */   {
/* 146 */     this.blockOptions = blockOptions;
/*     */   }
/*     */   public GrammarAST() {
/*     */   }
/*     */ 
/*     */   public GrammarAST(int t, String txt) {
/* 152 */     initialize(t, txt);
/*     */   }
/*     */ 
/*     */   public void initialize(int i, String s) {
/* 156 */     this.token = new TokenWithIndex(i, s);
/*     */   }
/*     */ 
/*     */   public void initialize(AST ast) {
/* 160 */     GrammarAST t = (GrammarAST)ast;
/* 161 */     this.token = t.token;
/* 162 */     this.enclosingRuleName = t.enclosingRuleName;
/* 163 */     this.ruleStartTokenIndex = t.ruleStartTokenIndex;
/* 164 */     this.ruleStopTokenIndex = t.ruleStopTokenIndex;
/* 165 */     this.setValue = t.setValue;
/* 166 */     this.blockOptions = t.blockOptions;
/* 167 */     this.outerAltNum = t.outerAltNum;
/*     */   }
/*     */ 
/*     */   public void initialize(Token token) {
/* 171 */     this.token = token;
/*     */   }
/*     */ 
/*     */   public DFA getLookaheadDFA() {
/* 175 */     return this.lookaheadDFA;
/*     */   }
/*     */ 
/*     */   public void setLookaheadDFA(DFA lookaheadDFA) {
/* 179 */     this.lookaheadDFA = lookaheadDFA;
/*     */   }
/*     */ 
/*     */   public Token getToken() {
/* 183 */     return this.token;
/*     */   }
/*     */ 
/*     */   public NFAState getNFAStartState() {
/* 187 */     return this.NFAStartState;
/*     */   }
/*     */ 
/*     */   public void setNFAStartState(NFAState nfaStartState) {
/* 191 */     this.NFAStartState = nfaStartState;
/*     */   }
/*     */ 
/*     */   public String setBlockOption(Grammar grammar, String key, Object value)
/*     */   {
/* 198 */     if (this.blockOptions == null) {
/* 199 */       this.blockOptions = new HashMap();
/*     */     }
/* 201 */     return setOption(this.blockOptions, Grammar.legalBlockOptions, grammar, key, value);
/*     */   }
/*     */ 
/*     */   public String setTerminalOption(Grammar grammar, String key, Object value) {
/* 205 */     if (this.terminalOptions == null) {
/* 206 */       this.terminalOptions = new HashMap();
/*     */     }
/* 208 */     return setOption(this.terminalOptions, Grammar.legalTokenOptions, grammar, key, value);
/*     */   }
/*     */ 
/*     */   public String setOption(Map options, Set legalOptions, Grammar grammar, String key, Object value) {
/* 212 */     if (!legalOptions.contains(key)) {
/* 213 */       ErrorManager.grammarError(133, grammar, this.token, key);
/*     */ 
/* 217 */       return null;
/*     */     }
/* 219 */     if ((value instanceof String)) {
/* 220 */       String vs = (String)value;
/* 221 */       if (vs.charAt(0) == '"') {
/* 222 */         value = vs.substring(1, vs.length() - 1);
/*     */       }
/*     */     }
/* 225 */     if (key.equals("k")) {
/* 226 */       grammar.numberOfManualLookaheadOptions += 1;
/*     */     }
/* 228 */     if ((key.equals("backtrack")) && (value.toString().equals("true"))) {
/* 229 */       grammar.composite.getRootGrammar().atLeastOneBacktrackOption = true;
/*     */     }
/* 231 */     options.put(key, value);
/* 232 */     return key;
/*     */   }
/*     */ 
/*     */   public Object getBlockOption(String key) {
/* 236 */     Object value = null;
/* 237 */     if (this.blockOptions != null) {
/* 238 */       value = this.blockOptions.get(key);
/*     */     }
/* 240 */     return value;
/*     */   }
/*     */ 
/*     */   public void setOptions(Grammar grammar, Map options) {
/* 244 */     if (options == null) {
/* 245 */       this.blockOptions = null;
/* 246 */       return;
/*     */     }
/* 248 */     Set keys = options.keySet();
/* 249 */     for (Iterator it = keys.iterator(); it.hasNext(); ) {
/* 250 */       String optionName = (String)it.next();
/* 251 */       String stored = setBlockOption(grammar, optionName, options.get(optionName));
/* 252 */       if (stored == null)
/* 253 */         it.remove();
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getText()
/*     */   {
/* 259 */     if (this.token != null) {
/* 260 */       return this.token.getText();
/*     */     }
/* 262 */     return "";
/*     */   }
/*     */ 
/*     */   public void setType(int type) {
/* 266 */     this.token.setType(type);
/*     */   }
/*     */ 
/*     */   public void setText(String text) {
/* 270 */     this.token.setText(text);
/*     */   }
/*     */ 
/*     */   public int getType() {
/* 274 */     if (this.token != null) {
/* 275 */       return this.token.getType();
/*     */     }
/* 277 */     return -1;
/*     */   }
/*     */ 
/*     */   public int getLine() {
/* 281 */     int line = 0;
/* 282 */     if (this.token != null) {
/* 283 */       line = this.token.getLine();
/*     */     }
/* 285 */     if (line == 0) {
/* 286 */       AST child = getFirstChild();
/* 287 */       if (child != null) {
/* 288 */         line = child.getLine();
/*     */       }
/*     */     }
/* 291 */     return line;
/*     */   }
/*     */ 
/*     */   public int getColumn() {
/* 295 */     int col = 0;
/* 296 */     if (this.token != null) {
/* 297 */       col = this.token.getColumn();
/*     */     }
/* 299 */     if (col == 0) {
/* 300 */       AST child = getFirstChild();
/* 301 */       if (child != null) {
/* 302 */         col = child.getColumn();
/*     */       }
/*     */     }
/* 305 */     return col;
/*     */   }
/*     */   public int getCharPositionInLine() {
/* 308 */     return getColumn() - 1;
/*     */   }
/*     */   public void setLine(int line) {
/* 311 */     this.token.setLine(line);
/*     */   }
/*     */ 
/*     */   public void setColumn(int col) {
/* 315 */     this.token.setColumn(col);
/*     */   }
/*     */ 
/*     */   public IntSet getSetValue() {
/* 319 */     return this.setValue;
/*     */   }
/*     */ 
/*     */   public void setSetValue(IntSet setValue) {
/* 323 */     this.setValue = setValue;
/*     */   }
/*     */ 
/*     */   public GrammarAST getLastChild() {
/* 327 */     return ((GrammarAST)getFirstChild()).getLastSibling();
/*     */   }
/*     */ 
/*     */   public GrammarAST getLastSibling() {
/* 331 */     GrammarAST t = this;
/* 332 */     GrammarAST last = null;
/* 333 */     while (t != null) {
/* 334 */       last = t;
/* 335 */       t = (GrammarAST)t.getNextSibling();
/*     */     }
/* 337 */     return last;
/*     */   }
/*     */ 
/*     */   public GrammarAST getChild(int i)
/*     */   {
/* 342 */     int n = 0;
/* 343 */     AST t = getFirstChild();
/* 344 */     while (t != null) {
/* 345 */       if (n == i) {
/* 346 */         return (GrammarAST)t;
/*     */       }
/* 348 */       n++;
/* 349 */       t = (GrammarAST)t.getNextSibling();
/*     */     }
/* 351 */     return null;
/*     */   }
/*     */ 
/*     */   public GrammarAST getFirstChildWithType(int ttype) {
/* 355 */     AST t = getFirstChild();
/* 356 */     while (t != null) {
/* 357 */       if (t.getType() == ttype) {
/* 358 */         return (GrammarAST)t;
/*     */       }
/* 360 */       t = (GrammarAST)t.getNextSibling();
/*     */     }
/* 362 */     return null;
/*     */   }
/*     */ 
/*     */   public GrammarAST[] getChildrenAsArray() {
/* 366 */     AST t = getFirstChild();
/* 367 */     GrammarAST[] array = new GrammarAST[getNumberOfChildren()];
/* 368 */     int i = 0;
/* 369 */     while (t != null) {
/* 370 */       array[i] = ((GrammarAST)t);
/* 371 */       t = t.getNextSibling();
/* 372 */       i++;
/*     */     }
/* 374 */     return array;
/*     */   }
/*     */ 
/*     */   public GrammarAST findFirstType(int ttype)
/*     */   {
/* 383 */     if (getType() == ttype) {
/* 384 */       return this;
/*     */     }
/*     */ 
/* 387 */     GrammarAST child = (GrammarAST)getFirstChild();
/* 388 */     while (child != null) {
/* 389 */       GrammarAST result = child.findFirstType(ttype);
/* 390 */       if (result != null) {
/* 391 */         return result;
/*     */       }
/* 393 */       child = (GrammarAST)child.getNextSibling();
/*     */     }
/* 395 */     return null;
/*     */   }
/*     */ 
/*     */   public int getNumberOfChildrenWithType(int ttype) {
/* 399 */     AST p = getFirstChild();
/* 400 */     int n = 0;
/* 401 */     while (p != null) {
/* 402 */       if (p.getType() == ttype) n++;
/* 403 */       p = p.getNextSibling();
/*     */     }
/* 405 */     return n;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object ast)
/*     */   {
/* 412 */     if (this == ast) {
/* 413 */       return true;
/*     */     }
/* 415 */     if (!(ast instanceof GrammarAST)) {
/* 416 */       return getType() == ((AST)ast).getType();
/*     */     }
/* 418 */     GrammarAST t = (GrammarAST)ast;
/* 419 */     return (this.token.getLine() == t.getLine()) && (this.token.getColumn() == t.getColumn());
/*     */   }
/*     */ 
/*     */   public boolean hasSameTreeStructure(AST t)
/*     */   {
/* 426 */     if (getType() != t.getType()) return false;
/*     */ 
/* 428 */     if (getFirstChild() != null) {
/* 429 */       if (!((GrammarAST)getFirstChild()).hasSameListStructure(t.getFirstChild())) return false;
/*     */ 
/*     */     }
/* 432 */     else if (t.getFirstChild() != null) {
/* 433 */       return false;
/*     */     }
/* 435 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean hasSameListStructure(AST t)
/*     */   {
/* 442 */     if (t == null) {
/* 443 */       return false;
/*     */     }
/*     */ 
/* 447 */     AST sibling = this;
/*     */ 
/* 449 */     for (; (sibling != null) && (t != null); 
/* 449 */       t = t.getNextSibling())
/*     */     {
/* 452 */       if (sibling.getType() != t.getType()) {
/* 453 */         return false;
/*     */       }
/*     */ 
/* 456 */       if (sibling.getFirstChild() != null) {
/* 457 */         if (!((GrammarAST)sibling.getFirstChild()).hasSameListStructure(t.getFirstChild())) {
/* 458 */           return false;
/*     */         }
/*     */ 
/*     */       }
/* 462 */       else if (t.getFirstChild() != null)
/* 463 */         return false;
/* 449 */       sibling = sibling.getNextSibling();
/*     */     }
/*     */ 
/* 466 */     if ((sibling == null) && (t == null)) {
/* 467 */       return true;
/*     */     }
/*     */ 
/* 470 */     return false;
/*     */   }
/*     */ 
/*     */   public static GrammarAST dup(AST t) {
/* 474 */     if (t == null) {
/* 475 */       return null;
/*     */     }
/* 477 */     GrammarAST dup_t = new GrammarAST();
/* 478 */     dup_t.initialize(t);
/* 479 */     return dup_t;
/*     */   }
/*     */ 
/*     */   public static GrammarAST dupListNoActions(GrammarAST t, GrammarAST parent)
/*     */   {
/* 484 */     GrammarAST result = dupTreeNoActions(t, parent);
/* 485 */     GrammarAST nt = result;
/* 486 */     while (t != null) {
/* 487 */       t = (GrammarAST)t.getNextSibling();
/* 488 */       if ((t == null) || (t.getType() != 40))
/*     */       {
/* 491 */         GrammarAST d = dupTreeNoActions(t, parent);
/* 492 */         if (d != null) {
/* 493 */           if (nt != null) {
/* 494 */             nt.setNextSibling(d);
/*     */           }
/* 496 */           nt = d;
/*     */         }
/*     */       }
/*     */     }
/* 499 */     return result;
/*     */   }
/*     */ 
/*     */   public static GrammarAST dupTreeNoActions(GrammarAST t, GrammarAST parent)
/*     */   {
/* 506 */     if (t == null) {
/* 507 */       return null;
/*     */     }
/* 509 */     int ttype = t.getType();
/* 510 */     if (ttype == 80) {
/* 511 */       return null;
/*     */     }
/* 513 */     if ((ttype == 59) || (ttype == 71))
/*     */     {
/* 515 */       return dupListNoActions((GrammarAST)t.getFirstChild(), t);
/*     */     }
/*     */ 
/* 524 */     GrammarAST result = dup(t);
/*     */ 
/* 526 */     GrammarAST kids = dupListNoActions((GrammarAST)t.getFirstChild(), t);
/* 527 */     result.setFirstChild(kids);
/* 528 */     return result;
/*     */   }
/*     */ 
/*     */   public void setTreeEnclosingRuleNameDeeply(String rname) {
/* 532 */     GrammarAST t = this;
/* 533 */     t.enclosingRuleName = rname;
/* 534 */     t = t.getChild(0);
/* 535 */     while (t != null) {
/* 536 */       t.setTreeEnclosingRuleNameDeeply(rname);
/* 537 */       t = (GrammarAST)t.getNextSibling();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.tool.GrammarAST
 * JD-Core Version:    0.6.2
 */