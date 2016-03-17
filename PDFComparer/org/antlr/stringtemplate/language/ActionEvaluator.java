/*      */ package org.antlr.stringtemplate.language;
/*      */ 
/*      */ import antlr.MismatchedTokenException;
/*      */ import antlr.NoViableAltException;
/*      */ import antlr.RecognitionException;
/*      */ import antlr.TreeParser;
/*      */ import antlr.collections.AST;
/*      */ import antlr.collections.impl.BitSet;
/*      */ import java.io.StringWriter;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import org.antlr.stringtemplate.StringTemplate;
/*      */ import org.antlr.stringtemplate.StringTemplateGroup;
/*      */ import org.antlr.stringtemplate.StringTemplateWriter;
/*      */ 
/*      */ public class ActionEvaluator extends TreeParser
/*      */   implements ActionEvaluatorTokenTypes
/*      */ {
/*   57 */   protected StringTemplate self = null;
/*   58 */   protected StringTemplateWriter out = null;
/*   59 */   protected ASTExpr chunk = null;
/*      */ 
/* 1018 */   public static final String[] _tokenNames = { "<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "APPLY", "MULTI_APPLY", "ARGS", "INCLUDE", "\"if\"", "VALUE", "TEMPLATE", "FUNCTION", "SINGLEVALUEARG", "LIST", "NOTHING", "SEMI", "LPAREN", "RPAREN", "\"elseif\"", "COMMA", "ID", "ASSIGN", "COLON", "NOT", "PLUS", "DOT", "\"first\"", "\"rest\"", "\"last\"", "\"length\"", "\"strip\"", "\"trunc\"", "\"super\"", "ANONYMOUS_TEMPLATE", "STRING", "INT", "LBRACK", "RBRACK", "DOTDOTDOT", "TEMPLATE_ARGS", "NESTED_ANONYMOUS_TEMPLATE", "ESC_CHAR", "WS", "WS_CHAR" };
/*      */ 
/* 1069 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/*      */ 
/*      */   public ActionEvaluator(StringTemplate self, ASTExpr chunk, StringTemplateWriter out)
/*      */   {
/*   63 */     this.self = self;
/*   64 */     this.chunk = chunk;
/*   65 */     this.out = out;
/*      */   }
/*      */ 
/*      */   public void reportError(RecognitionException e) {
/*   69 */     this.self.error("eval tree parse error", e);
/*      */   }
/*      */   public ActionEvaluator() {
/*   72 */     this.tokenNames = _tokenNames;
/*      */   }
/*      */ 
/*      */   public final int action(AST _t) throws RecognitionException {
/*   76 */     int numCharsWritten = 0;
/*      */ 
/*   78 */     StringTemplateAST action_AST_in = _t == ASTNULL ? null : (StringTemplateAST)_t;
/*      */ 
/*   80 */     Object e = null;
/*      */     try
/*      */     {
/*   84 */       e = expr(_t);
/*   85 */       _t = this._retTree;
/*   86 */       numCharsWritten = this.chunk.writeAttribute(this.self, e, this.out);
/*      */     }
/*      */     catch (RecognitionException ex) {
/*   89 */       reportError(ex);
/*   90 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*   92 */     this._retTree = _t;
/*   93 */     return numCharsWritten;
/*      */   }
/*      */ 
/*      */   public final Object expr(AST _t) throws RecognitionException {
/*   97 */     Object value = null;
/*      */ 
/*   99 */     StringTemplateAST expr_AST_in = _t == ASTNULL ? null : (StringTemplateAST)_t;
/*      */ 
/*  101 */     Object a = null; Object b = null; Object e = null;
/*  102 */     Map argumentContext = null;
/*      */     try
/*      */     {
/*  106 */       if (_t == null) _t = ASTNULL;
/*  107 */       switch (_t.getType())
/*      */       {
/*      */       case 24:
/*  110 */         AST __t3 = _t;
/*  111 */         StringTemplateAST tmp1_AST_in = (StringTemplateAST)_t;
/*  112 */         match(_t, 24);
/*  113 */         _t = _t.getFirstChild();
/*  114 */         a = expr(_t);
/*  115 */         _t = this._retTree;
/*  116 */         b = expr(_t);
/*  117 */         _t = this._retTree;
/*  118 */         value = this.chunk.add(a, b);
/*  119 */         _t = __t3;
/*  120 */         _t = _t.getNextSibling();
/*  121 */         break;
/*      */       case 4:
/*      */       case 5:
/*  126 */         value = templateApplication(_t);
/*  127 */         _t = this._retTree;
/*  128 */         break;
/*      */       case 20:
/*      */       case 25:
/*      */       case 33:
/*      */       case 34:
/*      */       case 35:
/*  136 */         value = attribute(_t);
/*  137 */         _t = this._retTree;
/*  138 */         break;
/*      */       case 7:
/*  142 */         value = templateInclude(_t);
/*  143 */         _t = this._retTree;
/*  144 */         break;
/*      */       case 11:
/*  148 */         value = function(_t);
/*  149 */         _t = this._retTree;
/*  150 */         break;
/*      */       case 13:
/*  154 */         value = list(_t);
/*  155 */         _t = this._retTree;
/*  156 */         break;
/*      */       case 9:
/*  160 */         AST __t4 = _t;
/*  161 */         StringTemplateAST tmp2_AST_in = (StringTemplateAST)_t;
/*  162 */         match(_t, 9);
/*  163 */         _t = _t.getFirstChild();
/*  164 */         e = expr(_t);
/*  165 */         _t = this._retTree;
/*  166 */         _t = __t4;
/*  167 */         _t = _t.getNextSibling();
/*      */ 
/*  169 */         StringWriter buf = new StringWriter();
/*  170 */         StringTemplateWriter sw = this.self.getGroup().getStringTemplateWriter(buf);
/*      */ 
/*  172 */         int n = this.chunk.writeAttribute(this.self, e, sw);
/*  173 */         if (n > 0)
/*  174 */           value = buf.toString(); break;
/*      */       case 6:
/*      */       case 8:
/*      */       case 10:
/*      */       case 12:
/*      */       case 14:
/*      */       case 15:
/*      */       case 16:
/*      */       case 17:
/*      */       case 18:
/*      */       case 19:
/*      */       case 21:
/*      */       case 22:
/*      */       case 23:
/*      */       case 26:
/*      */       case 27:
/*      */       case 28:
/*      */       case 29:
/*      */       case 30:
/*      */       case 31:
/*      */       case 32:
/*      */       default:
/*  181 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  186 */       reportError(ex);
/*  187 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  189 */     this._retTree = _t;
/*  190 */     return value;
/*      */   }
/*      */ 
/*      */   public final Object templateApplication(AST _t)
/*      */     throws RecognitionException
/*      */   {
/*  197 */     Object value = null;
/*      */ 
/*  199 */     StringTemplateAST templateApplication_AST_in = _t == ASTNULL ? null : (StringTemplateAST)_t;
/*  200 */     StringTemplateAST anon = null;
/*      */ 
/*  202 */     Object a = null;
/*  203 */     Vector templatesToApply = new Vector();
/*  204 */     List attributes = new ArrayList();
/*      */     try
/*      */     {
/*  208 */       if (_t == null) _t = ASTNULL;
/*  209 */       switch (_t.getType())
/*      */       {
/*      */       case 4:
/*  212 */         AST __t14 = _t;
/*  213 */         StringTemplateAST tmp3_AST_in = (StringTemplateAST)_t;
/*  214 */         match(_t, 4);
/*  215 */         _t = _t.getFirstChild();
/*  216 */         a = expr(_t);
/*  217 */         _t = this._retTree;
/*      */ 
/*  219 */         int _cnt16 = 0;
/*      */         while (true)
/*      */         {
/*  222 */           if (_t == null) _t = ASTNULL;
/*  223 */           if (_t.getType() == 10) {
/*  224 */             template(_t, templatesToApply);
/*  225 */             _t = this._retTree;
/*      */           }
/*      */           else {
/*  228 */             if (_cnt16 >= 1) break; throw new NoViableAltException(_t);
/*      */           }
/*      */ 
/*  231 */           _cnt16++;
/*      */         }
/*      */ 
/*  234 */         value = this.chunk.applyListOfAlternatingTemplates(this.self, a, templatesToApply);
/*  235 */         _t = __t14;
/*  236 */         _t = _t.getNextSibling();
/*  237 */         break;
/*      */       case 5:
/*  241 */         AST __t17 = _t;
/*  242 */         StringTemplateAST tmp4_AST_in = (StringTemplateAST)_t;
/*  243 */         match(_t, 5);
/*  244 */         _t = _t.getFirstChild();
/*      */ 
/*  246 */         int _cnt19 = 0;
/*      */         while (true)
/*      */         {
/*  249 */           if (_t == null) _t = ASTNULL;
/*  250 */           if (_tokenSet_0.member(_t.getType())) {
/*  251 */             a = expr(_t);
/*  252 */             _t = this._retTree;
/*  253 */             attributes.add(a);
/*      */           }
/*      */           else {
/*  256 */             if (_cnt19 >= 1) break; throw new NoViableAltException(_t);
/*      */           }
/*      */ 
/*  259 */           _cnt19++;
/*      */         }
/*      */ 
/*  262 */         StringTemplateAST tmp5_AST_in = (StringTemplateAST)_t;
/*  263 */         match(_t, 22);
/*  264 */         _t = _t.getNextSibling();
/*  265 */         anon = (StringTemplateAST)_t;
/*  266 */         match(_t, 33);
/*  267 */         _t = _t.getNextSibling();
/*      */ 
/*  269 */         StringTemplate anonymous = anon.getStringTemplate();
/*  270 */         templatesToApply.addElement(anonymous);
/*  271 */         value = this.chunk.applyTemplateToListOfAttributes(this.self, attributes, anon.getStringTemplate());
/*      */ 
/*  275 */         _t = __t17;
/*  276 */         _t = _t.getNextSibling();
/*  277 */         break;
/*      */       default:
/*  281 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  286 */       reportError(ex);
/*  287 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  289 */     this._retTree = _t;
/*  290 */     return value;
/*      */   }
/*      */ 
/*      */   public final Object attribute(AST _t) throws RecognitionException {
/*  294 */     Object value = null;
/*      */ 
/*  296 */     StringTemplateAST attribute_AST_in = _t == ASTNULL ? null : (StringTemplateAST)_t;
/*  297 */     StringTemplateAST prop = null;
/*  298 */     StringTemplateAST i3 = null;
/*  299 */     StringTemplateAST i = null;
/*  300 */     StringTemplateAST s = null;
/*  301 */     StringTemplateAST at = null;
/*      */ 
/*  303 */     Object obj = null;
/*  304 */     Object propName = null;
/*  305 */     Object e = null;
/*      */     try
/*      */     {
/*  309 */       if (_t == null) _t = ASTNULL;
/*  310 */       switch (_t.getType())
/*      */       {
/*      */       case 25:
/*  313 */         AST __t33 = _t;
/*  314 */         StringTemplateAST tmp6_AST_in = (StringTemplateAST)_t;
/*  315 */         match(_t, 25);
/*  316 */         _t = _t.getFirstChild();
/*  317 */         obj = expr(_t);
/*  318 */         _t = this._retTree;
/*      */ 
/*  320 */         if (_t == null) _t = ASTNULL;
/*  321 */         switch (_t.getType())
/*      */         {
/*      */         case 20:
/*  324 */           prop = (StringTemplateAST)_t;
/*  325 */           match(_t, 20);
/*  326 */           _t = _t.getNextSibling();
/*  327 */           propName = prop.getText();
/*  328 */           break;
/*      */         case 9:
/*  332 */           AST __t35 = _t;
/*  333 */           StringTemplateAST tmp7_AST_in = (StringTemplateAST)_t;
/*  334 */           match(_t, 9);
/*  335 */           _t = _t.getFirstChild();
/*  336 */           e = expr(_t);
/*  337 */           _t = this._retTree;
/*  338 */           _t = __t35;
/*  339 */           _t = _t.getNextSibling();
/*  340 */           if (e != null) propName = e; break;
/*      */         default:
/*  345 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/*  349 */         _t = __t33;
/*  350 */         _t = _t.getNextSibling();
/*  351 */         value = this.chunk.getObjectProperty(this.self, obj, propName);
/*  352 */         break;
/*      */       case 20:
/*  356 */         i3 = (StringTemplateAST)_t;
/*  357 */         match(_t, 20);
/*  358 */         _t = _t.getNextSibling();
/*      */ 
/*  360 */         value = this.self.getAttribute(i3.getText());
/*      */ 
/*  362 */         break;
/*      */       case 35:
/*  366 */         i = (StringTemplateAST)_t;
/*  367 */         match(_t, 35);
/*  368 */         _t = _t.getNextSibling();
/*  369 */         value = new Integer(i.getText());
/*  370 */         break;
/*      */       case 34:
/*  374 */         s = (StringTemplateAST)_t;
/*  375 */         match(_t, 34);
/*  376 */         _t = _t.getNextSibling();
/*      */ 
/*  378 */         value = s.getText();
/*      */ 
/*  380 */         break;
/*      */       case 33:
/*  384 */         at = (StringTemplateAST)_t;
/*  385 */         match(_t, 33);
/*  386 */         _t = _t.getNextSibling();
/*      */ 
/*  388 */         value = at.getText();
/*  389 */         if (at.getText() != null) {
/*  390 */           StringTemplate valueST = new StringTemplate(this.self.getGroup(), at.getText());
/*  391 */           valueST.setEnclosingInstance(this.self);
/*  392 */           valueST.setName("<anonymous template argument>");
/*  393 */           value = valueST;
/*  394 */         }break;
/*      */       default:
/*  400 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  405 */       reportError(ex);
/*  406 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  408 */     this._retTree = _t;
/*  409 */     return value;
/*      */   }
/*      */ 
/*      */   public final Object templateInclude(AST _t) throws RecognitionException {
/*  413 */     Object value = null;
/*      */ 
/*  415 */     StringTemplateAST templateInclude_AST_in = _t == ASTNULL ? null : (StringTemplateAST)_t;
/*  416 */     StringTemplateAST id = null;
/*  417 */     StringTemplateAST a1 = null;
/*  418 */     StringTemplateAST a2 = null;
/*      */ 
/*  420 */     StringTemplateAST args = null;
/*  421 */     String name = null;
/*  422 */     Object n = null;
/*      */     try
/*      */     {
/*  426 */       AST __t10 = _t;
/*  427 */       StringTemplateAST tmp8_AST_in = (StringTemplateAST)_t;
/*  428 */       match(_t, 7);
/*  429 */       _t = _t.getFirstChild();
/*      */ 
/*  431 */       if (_t == null) _t = ASTNULL;
/*  432 */       switch (_t.getType())
/*      */       {
/*      */       case 20:
/*  435 */         id = (StringTemplateAST)_t;
/*  436 */         match(_t, 20);
/*  437 */         _t = _t.getNextSibling();
/*  438 */         a1 = (StringTemplateAST)_t;
/*  439 */         if (_t == null) throw new MismatchedTokenException();
/*  440 */         _t = _t.getNextSibling();
/*  441 */         name = id.getText(); args = a1;
/*  442 */         break;
/*      */       case 9:
/*  446 */         AST __t12 = _t;
/*  447 */         StringTemplateAST tmp9_AST_in = (StringTemplateAST)_t;
/*  448 */         match(_t, 9);
/*  449 */         _t = _t.getFirstChild();
/*  450 */         n = expr(_t);
/*  451 */         _t = this._retTree;
/*  452 */         a2 = (StringTemplateAST)_t;
/*  453 */         if (_t == null) throw new MismatchedTokenException();
/*  454 */         _t = _t.getNextSibling();
/*  455 */         _t = __t12;
/*  456 */         _t = _t.getNextSibling();
/*  457 */         if (n != null) name = n.toString(); args = a2;
/*  458 */         break;
/*      */       default:
/*  462 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  466 */       _t = __t10;
/*  467 */       _t = _t.getNextSibling();
/*      */ 
/*  469 */       if (name != null) {
/*  470 */         value = this.chunk.getTemplateInclude(this.self, name, args);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  475 */       reportError(ex);
/*  476 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  478 */     this._retTree = _t;
/*  479 */     return value;
/*      */   }
/*      */ 
/*      */   public final Object function(AST _t) throws RecognitionException {
/*  483 */     Object value = null;
/*      */ 
/*  485 */     StringTemplateAST function_AST_in = _t == ASTNULL ? null : (StringTemplateAST)_t;
/*      */     try
/*      */     {
/*  491 */       AST __t21 = _t;
/*  492 */       StringTemplateAST tmp10_AST_in = (StringTemplateAST)_t;
/*  493 */       match(_t, 11);
/*  494 */       _t = _t.getFirstChild();
/*      */ 
/*  496 */       if (_t == null) _t = ASTNULL;
/*      */       Object a;
/*  497 */       switch (_t.getType())
/*      */       {
/*      */       case 26:
/*  500 */         StringTemplateAST tmp11_AST_in = (StringTemplateAST)_t;
/*  501 */         match(_t, 26);
/*  502 */         _t = _t.getNextSibling();
/*  503 */         a = singleFunctionArg(_t);
/*  504 */         _t = this._retTree;
/*  505 */         value = this.chunk.first(a);
/*  506 */         break;
/*      */       case 27:
/*  510 */         StringTemplateAST tmp12_AST_in = (StringTemplateAST)_t;
/*  511 */         match(_t, 27);
/*  512 */         _t = _t.getNextSibling();
/*  513 */         a = singleFunctionArg(_t);
/*  514 */         _t = this._retTree;
/*  515 */         value = this.chunk.rest(a);
/*  516 */         break;
/*      */       case 28:
/*  520 */         StringTemplateAST tmp13_AST_in = (StringTemplateAST)_t;
/*  521 */         match(_t, 28);
/*  522 */         _t = _t.getNextSibling();
/*  523 */         a = singleFunctionArg(_t);
/*  524 */         _t = this._retTree;
/*  525 */         value = this.chunk.last(a);
/*  526 */         break;
/*      */       case 29:
/*  530 */         StringTemplateAST tmp14_AST_in = (StringTemplateAST)_t;
/*  531 */         match(_t, 29);
/*  532 */         _t = _t.getNextSibling();
/*  533 */         a = singleFunctionArg(_t);
/*  534 */         _t = this._retTree;
/*  535 */         value = this.chunk.length(a);
/*  536 */         break;
/*      */       case 30:
/*  540 */         StringTemplateAST tmp15_AST_in = (StringTemplateAST)_t;
/*  541 */         match(_t, 30);
/*  542 */         _t = _t.getNextSibling();
/*  543 */         a = singleFunctionArg(_t);
/*  544 */         _t = this._retTree;
/*  545 */         value = this.chunk.strip(a);
/*  546 */         break;
/*      */       case 31:
/*  550 */         StringTemplateAST tmp16_AST_in = (StringTemplateAST)_t;
/*  551 */         match(_t, 31);
/*  552 */         _t = _t.getNextSibling();
/*  553 */         a = singleFunctionArg(_t);
/*  554 */         _t = this._retTree;
/*  555 */         value = this.chunk.trunc(a);
/*  556 */         break;
/*      */       default:
/*  560 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  564 */       _t = __t21;
/*  565 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  568 */       reportError(ex);
/*  569 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  571 */     this._retTree = _t;
/*  572 */     return value;
/*      */   }
/*      */ 
/*      */   public final Object list(AST _t) throws RecognitionException
/*      */   {
/*  577 */     Object value = null;
/*      */ 
/*  579 */     StringTemplateAST list_AST_in = _t == ASTNULL ? null : (StringTemplateAST)_t;
/*      */ 
/*  581 */     Object e = null;
/*  582 */     List elements = new ArrayList();
/*      */     try
/*      */     {
/*  586 */       AST __t6 = _t;
/*  587 */       StringTemplateAST tmp17_AST_in = (StringTemplateAST)_t;
/*  588 */       match(_t, 13);
/*  589 */       _t = _t.getFirstChild();
/*      */ 
/*  591 */       int _cnt8 = 0;
/*      */       while (true)
/*      */       {
/*  594 */         if (_t == null) _t = ASTNULL;
/*  595 */         switch (_t.getType())
/*      */         {
/*      */         case 4:
/*      */         case 5:
/*      */         case 7:
/*      */         case 9:
/*      */         case 11:
/*      */         case 13:
/*      */         case 20:
/*      */         case 24:
/*      */         case 25:
/*      */         case 33:
/*      */         case 34:
/*      */         case 35:
/*  609 */           e = expr(_t);
/*  610 */           _t = this._retTree;
/*      */ 
/*  612 */           if (e != null)
/*  613 */             elements.add(e); break;
/*      */         case 14:
/*  620 */           StringTemplateAST tmp18_AST_in = (StringTemplateAST)_t;
/*  621 */           match(_t, 14);
/*  622 */           _t = _t.getNextSibling();
/*      */ 
/*  624 */           List nullSingleton = new ArrayList()
/*      */           {
/*      */           };
/*  625 */           elements.add(nullSingleton.iterator());
/*      */ 
/*  627 */           break;
/*      */         case 6:
/*      */         case 8:
/*      */         case 10:
/*      */         case 12:
/*      */         case 15:
/*      */         case 16:
/*      */         case 17:
/*      */         case 18:
/*      */         case 19:
/*      */         case 21:
/*      */         case 22:
/*      */         case 23:
/*      */         case 26:
/*      */         case 27:
/*      */         case 28:
/*      */         case 29:
/*      */         case 30:
/*      */         case 31:
/*      */         case 32:
/*      */         default:
/*  631 */           if (_cnt8 >= 1) break label314; throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/*  634 */         _cnt8++;
/*      */       }
/*      */ 
/*  637 */       label314: _t = __t6;
/*  638 */       _t = _t.getNextSibling();
/*  639 */       value = new Cat(elements);
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  642 */       reportError(ex);
/*  643 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  645 */     this._retTree = _t;
/*  646 */     return value;
/*      */   }
/*      */ 
/*      */   public final void template(AST _t, Vector templatesToApply)
/*      */     throws RecognitionException
/*      */   {
/*  653 */     StringTemplateAST template_AST_in = _t == ASTNULL ? null : (StringTemplateAST)_t;
/*  654 */     StringTemplateAST t = null;
/*  655 */     StringTemplateAST args = null;
/*  656 */     StringTemplateAST anon = null;
/*  657 */     StringTemplateAST args2 = null;
/*      */ 
/*  659 */     Map argumentContext = null;
/*  660 */     Object n = null;
/*      */     try
/*      */     {
/*  664 */       AST __t26 = _t;
/*  665 */       StringTemplateAST tmp19_AST_in = (StringTemplateAST)_t;
/*  666 */       match(_t, 10);
/*  667 */       _t = _t.getFirstChild();
/*      */ 
/*  669 */       if (_t == null) _t = ASTNULL;
/*  670 */       switch (_t.getType())
/*      */       {
/*      */       case 20:
/*  673 */         t = (StringTemplateAST)_t;
/*  674 */         match(_t, 20);
/*  675 */         _t = _t.getNextSibling();
/*  676 */         args = (StringTemplateAST)_t;
/*  677 */         if (_t == null) throw new MismatchedTokenException();
/*  678 */         _t = _t.getNextSibling();
/*      */ 
/*  680 */         String templateName = t.getText();
/*  681 */         StringTemplateGroup group = this.self.getGroup();
/*  682 */         StringTemplate embedded = group.getEmbeddedInstanceOf(this.self, templateName);
/*  683 */         if (embedded != null) {
/*  684 */           embedded.setArgumentsAST(args);
/*  685 */           templatesToApply.addElement(embedded); } break;
/*      */       case 33:
/*  692 */         anon = (StringTemplateAST)_t;
/*  693 */         match(_t, 33);
/*  694 */         _t = _t.getNextSibling();
/*      */ 
/*  696 */         StringTemplate anonymous = anon.getStringTemplate();
/*      */ 
/*  699 */         anonymous.setGroup(this.self.getGroup());
/*  700 */         templatesToApply.addElement(anonymous);
/*      */ 
/*  702 */         break;
/*      */       case 9:
/*  706 */         AST __t28 = _t;
/*  707 */         StringTemplateAST tmp20_AST_in = (StringTemplateAST)_t;
/*  708 */         match(_t, 9);
/*  709 */         _t = _t.getFirstChild();
/*  710 */         n = expr(_t);
/*  711 */         _t = this._retTree;
/*  712 */         args2 = (StringTemplateAST)_t;
/*  713 */         if (_t == null) throw new MismatchedTokenException();
/*  714 */         _t = _t.getNextSibling();
/*      */ 
/*  716 */         StringTemplate embedded = null;
/*  717 */         if (n != null) {
/*  718 */           String templateName = n.toString();
/*  719 */           StringTemplateGroup group = this.self.getGroup();
/*  720 */           embedded = group.getEmbeddedInstanceOf(this.self, templateName);
/*  721 */           if (embedded != null) {
/*  722 */             embedded.setArgumentsAST(args2);
/*  723 */             templatesToApply.addElement(embedded);
/*      */           }
/*      */         }
/*      */ 
/*  727 */         _t = __t28;
/*  728 */         _t = _t.getNextSibling();
/*  729 */         break;
/*      */       default:
/*  733 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  737 */       _t = __t26;
/*  738 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  741 */       reportError(ex);
/*  742 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  744 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final Object singleFunctionArg(AST _t) throws RecognitionException {
/*  748 */     Object value = null;
/*      */ 
/*  750 */     StringTemplateAST singleFunctionArg_AST_in = _t == ASTNULL ? null : (StringTemplateAST)_t;
/*      */     try
/*      */     {
/*  753 */       AST __t24 = _t;
/*  754 */       StringTemplateAST tmp21_AST_in = (StringTemplateAST)_t;
/*  755 */       match(_t, 12);
/*  756 */       _t = _t.getFirstChild();
/*  757 */       value = expr(_t);
/*  758 */       _t = this._retTree;
/*  759 */       _t = __t24;
/*  760 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  763 */       reportError(ex);
/*  764 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  766 */     this._retTree = _t;
/*  767 */     return value;
/*      */   }
/*      */ 
/*      */   public final boolean ifCondition(AST _t) throws RecognitionException {
/*  771 */     boolean value = false;
/*      */ 
/*  773 */     StringTemplateAST ifCondition_AST_in = _t == ASTNULL ? null : (StringTemplateAST)_t;
/*      */ 
/*  775 */     Object a = null; Object b = null;
/*      */     try
/*      */     {
/*  779 */       if (_t == null) _t = ASTNULL;
/*  780 */       switch (_t.getType())
/*      */       {
/*      */       case 4:
/*      */       case 5:
/*      */       case 7:
/*      */       case 9:
/*      */       case 11:
/*      */       case 13:
/*      */       case 20:
/*      */       case 24:
/*      */       case 25:
/*      */       case 33:
/*      */       case 34:
/*      */       case 35:
/*  794 */         a = ifAtom(_t);
/*  795 */         _t = this._retTree;
/*  796 */         value = this.chunk.testAttributeTrue(a);
/*  797 */         break;
/*      */       case 23:
/*  801 */         AST __t30 = _t;
/*  802 */         StringTemplateAST tmp22_AST_in = (StringTemplateAST)_t;
/*  803 */         match(_t, 23);
/*  804 */         _t = _t.getFirstChild();
/*  805 */         a = ifAtom(_t);
/*  806 */         _t = this._retTree;
/*  807 */         _t = __t30;
/*  808 */         _t = _t.getNextSibling();
/*  809 */         value = !this.chunk.testAttributeTrue(a);
/*  810 */         break;
/*      */       case 6:
/*      */       case 8:
/*      */       case 10:
/*      */       case 12:
/*      */       case 14:
/*      */       case 15:
/*      */       case 16:
/*      */       case 17:
/*      */       case 18:
/*      */       case 19:
/*      */       case 21:
/*      */       case 22:
/*      */       case 26:
/*      */       case 27:
/*      */       case 28:
/*      */       case 29:
/*      */       case 30:
/*      */       case 31:
/*      */       case 32:
/*      */       default:
/*  814 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  819 */       reportError(ex);
/*  820 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  822 */     this._retTree = _t;
/*  823 */     return value;
/*      */   }
/*      */ 
/*      */   public final Object ifAtom(AST _t) throws RecognitionException {
/*  827 */     Object value = null;
/*      */ 
/*  829 */     StringTemplateAST ifAtom_AST_in = _t == ASTNULL ? null : (StringTemplateAST)_t;
/*      */     try
/*      */     {
/*  832 */       value = expr(_t);
/*  833 */       _t = this._retTree;
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  836 */       reportError(ex);
/*  837 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  839 */     this._retTree = _t;
/*  840 */     return value;
/*      */   }
/*      */ 
/*      */   public final Map argList(AST _t, StringTemplate embedded, Map initialContext)
/*      */     throws RecognitionException
/*      */   {
/*  851 */     Map argumentContext = null;
/*      */ 
/*  853 */     StringTemplateAST argList_AST_in = _t == ASTNULL ? null : (StringTemplateAST)_t;
/*      */ 
/*  855 */     argumentContext = initialContext;
/*  856 */     if (argumentContext == null) {
/*  857 */       argumentContext = new HashMap();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  862 */       if (_t == null) _t = ASTNULL;
/*  863 */       switch (_t.getType())
/*      */       {
/*      */       case 6:
/*  866 */         AST __t37 = _t;
/*  867 */         StringTemplateAST tmp23_AST_in = (StringTemplateAST)_t;
/*  868 */         match(_t, 6);
/*  869 */         _t = _t.getFirstChild();
/*      */         while (true)
/*      */         {
/*  873 */           if (_t == null) _t = ASTNULL;
/*  874 */           if ((_t.getType() != 21) && (_t.getType() != 38)) break;
/*  875 */           argumentAssignment(_t, embedded, argumentContext);
/*  876 */           _t = this._retTree;
/*      */         }
/*      */ 
/*  884 */         _t = __t37;
/*  885 */         _t = _t.getNextSibling();
/*  886 */         break;
/*      */       case 12:
/*  890 */         singleTemplateArg(_t, embedded, argumentContext);
/*  891 */         _t = this._retTree;
/*  892 */         break;
/*      */       default:
/*  896 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  901 */       reportError(ex);
/*  902 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  904 */     this._retTree = _t;
/*  905 */     return argumentContext;
/*      */   }
/*      */ 
/*      */   public final void argumentAssignment(AST _t, StringTemplate embedded, Map argumentContext)
/*      */     throws RecognitionException
/*      */   {
/*  912 */     StringTemplateAST argumentAssignment_AST_in = _t == ASTNULL ? null : (StringTemplateAST)_t;
/*  913 */     StringTemplateAST arg = null;
/*      */ 
/*  915 */     Object e = null;
/*      */     try
/*      */     {
/*  919 */       if (_t == null) _t = ASTNULL;
/*  920 */       switch (_t.getType())
/*      */       {
/*      */       case 21:
/*  923 */         AST __t43 = _t;
/*  924 */         StringTemplateAST tmp24_AST_in = (StringTemplateAST)_t;
/*  925 */         match(_t, 21);
/*  926 */         _t = _t.getFirstChild();
/*  927 */         arg = (StringTemplateAST)_t;
/*  928 */         match(_t, 20);
/*  929 */         _t = _t.getNextSibling();
/*  930 */         e = expr(_t);
/*  931 */         _t = this._retTree;
/*  932 */         _t = __t43;
/*  933 */         _t = _t.getNextSibling();
/*      */ 
/*  935 */         if (e != null)
/*  936 */           this.self.rawSetArgumentAttribute(embedded, argumentContext, arg.getText(), e); break;
/*      */       case 38:
/*  943 */         StringTemplateAST tmp25_AST_in = (StringTemplateAST)_t;
/*  944 */         match(_t, 38);
/*  945 */         _t = _t.getNextSibling();
/*  946 */         embedded.setPassThroughAttributes(true);
/*  947 */         break;
/*      */       default:
/*  951 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  956 */       reportError(ex);
/*  957 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  959 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void singleTemplateArg(AST _t, StringTemplate embedded, Map argumentContext)
/*      */     throws RecognitionException
/*      */   {
/*  966 */     StringTemplateAST singleTemplateArg_AST_in = _t == ASTNULL ? null : (StringTemplateAST)_t;
/*      */ 
/*  968 */     Object e = null;
/*      */     try
/*      */     {
/*  972 */       AST __t41 = _t;
/*  973 */       StringTemplateAST tmp26_AST_in = (StringTemplateAST)_t;
/*  974 */       match(_t, 12);
/*  975 */       _t = _t.getFirstChild();
/*  976 */       e = expr(_t);
/*  977 */       _t = this._retTree;
/*  978 */       _t = __t41;
/*  979 */       _t = _t.getNextSibling();
/*      */ 
/*  981 */       if (e != null) {
/*  982 */         String soleArgName = null;
/*      */ 
/*  984 */         boolean error = false;
/*  985 */         Map formalArgs = embedded.getFormalArguments();
/*  986 */         if (formalArgs != null) {
/*  987 */           Set argNames = formalArgs.keySet();
/*  988 */           if (argNames.size() == 1) {
/*  989 */             soleArgName = (String)argNames.toArray()[0];
/*      */           }
/*      */           else
/*      */           {
/*  993 */             error = true;
/*      */           }
/*      */         }
/*      */         else {
/*  997 */           error = true;
/*      */         }
/*  999 */         if (error) {
/* 1000 */           this.self.error("template " + embedded.getName() + " must have exactly one formal arg in template context " + this.self.getEnclosingInstanceStackString());
/*      */         }
/*      */         else
/*      */         {
/* 1005 */           this.self.rawSetArgumentAttribute(embedded, argumentContext, soleArgName, e);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1011 */       reportError(ex);
/* 1012 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1014 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_0()
/*      */   {
/* 1066 */     long[] data = { 60180933296L, 0L };
/* 1067 */     return data;
/*      */   }
/*      */ 
/*      */   public static class NameValuePair
/*      */   {
/*      */     public String name;
/*      */     public Object value;
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.stringtemplate.language.ActionEvaluator
 * JD-Core Version:    0.6.2
 */