/*     */ package org.antlr.stringtemplate.language;
/*     */ 
/*     */ import antlr.LLkParser;
/*     */ import antlr.NoViableAltException;
/*     */ import antlr.ParserSharedInputState;
/*     */ import antlr.RecognitionException;
/*     */ import antlr.Token;
/*     */ import antlr.TokenBuffer;
/*     */ import antlr.TokenStream;
/*     */ import antlr.TokenStreamException;
/*     */ import antlr.collections.impl.BitSet;
/*     */ import org.antlr.stringtemplate.StringTemplate;
/*     */ import org.antlr.stringtemplate.StringTemplateGroup;
/*     */ 
/*     */ public class TemplateParser extends LLkParser
/*     */   implements TemplateParserTokenTypes
/*     */ {
/*     */   protected StringTemplate self;
/* 329 */   public static final String[] _tokenNames = { "<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "LITERAL", "NEWLINE", "ACTION", "IF", "ELSEIF", "ELSE", "ENDIF", "REGION_REF", "REGION_DEF", "EXPR", "TEMPLATE", "IF_EXPR", "ESC_CHAR", "ESC", "HEX", "SUBTEMPLATE", "NESTED_PARENS", "INDENT", "COMMENT", "LINE_BREAK" };
/*     */ 
/* 360 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/*     */ 
/* 365 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/*     */ 
/*     */   public void reportError(RecognitionException e)
/*     */   {
/*  57 */     StringTemplateGroup group = this.self.getGroup();
/*  58 */     if (group == StringTemplate.defaultGroup) {
/*  59 */       this.self.error("template parse error; template context is " + this.self.getEnclosingInstanceStackString(), e);
/*     */     }
/*     */     else
/*  62 */       this.self.error("template parse error in group " + this.self.getGroup().getName() + " line " + this.self.getGroupFileLine() + "; template context is " + this.self.getEnclosingInstanceStackString(), e);
/*     */   }
/*     */ 
/*     */   protected TemplateParser(TokenBuffer tokenBuf, int k)
/*     */   {
/*  67 */     super(tokenBuf, k);
/*  68 */     this.tokenNames = _tokenNames;
/*     */   }
/*     */ 
/*     */   public TemplateParser(TokenBuffer tokenBuf) {
/*  72 */     this(tokenBuf, 1);
/*     */   }
/*     */ 
/*     */   protected TemplateParser(TokenStream lexer, int k) {
/*  76 */     super(lexer, k);
/*  77 */     this.tokenNames = _tokenNames;
/*     */   }
/*     */ 
/*     */   public TemplateParser(TokenStream lexer) {
/*  81 */     this(lexer, 1);
/*     */   }
/*     */ 
/*     */   public TemplateParser(ParserSharedInputState state) {
/*  85 */     super(state, 1);
/*  86 */     this.tokenNames = _tokenNames;
/*     */   }
/*     */ 
/*     */   public final void template(StringTemplate self)
/*     */     throws RecognitionException, TokenStreamException
/*     */   {
/*  93 */     Token s = null;
/*  94 */     Token nl = null;
/*     */ 
/*  96 */     this.self = self;
/*     */     try
/*     */     {
/*     */       while (true)
/*     */       {
/* 103 */         switch (LA(1))
/*     */         {
/*     */         case 4:
/* 106 */           s = LT(1);
/* 107 */           match(4);
/* 108 */           self.addChunk(new StringRef(self, s.getText()));
/* 109 */           break;
/*     */         case 5:
/* 113 */           nl = LT(1);
/* 114 */           match(5);
/*     */ 
/* 116 */           if ((LA(1) != 9) && (LA(1) != 10))
/* 117 */             self.addChunk(new NewlineRef(self, nl.getText())); break;
/*     */         case 6:
/*     */         case 7:
/*     */         case 11:
/*     */         case 12:
/* 127 */           action(self);
/*     */         case 8:
/*     */         case 9:
/*     */         case 10:
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (RecognitionException ex)
/*     */     {
/* 139 */       reportError(ex);
/* 140 */       recover(ex, _tokenSet_0);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void action(StringTemplate self)
/*     */     throws RecognitionException, TokenStreamException
/*     */   {
/* 148 */     Token a = null;
/* 149 */     Token i = null;
/* 150 */     Token ei = null;
/* 151 */     Token rr = null;
/* 152 */     Token rd = null;
/*     */     try
/*     */     {
/* 155 */       switch (LA(1))
/*     */       {
/*     */       case 6:
/* 158 */         a = LT(1);
/* 159 */         match(6);
/*     */ 
/* 161 */         String indent = ((ChunkToken)a).getIndentation();
/* 162 */         ASTExpr c = self.parseAction(a.getText());
/* 163 */         c.setIndentation(indent);
/* 164 */         self.addChunk(c);
/*     */ 
/* 166 */         break;
/*     */       case 7:
/* 170 */         i = LT(1);
/* 171 */         match(7);
/*     */ 
/* 173 */         ConditionalExpr c = (ConditionalExpr)self.parseAction(i.getText());
/*     */ 
/* 175 */         StringTemplate subtemplate = new StringTemplate(self.getGroup(), null);
/*     */ 
/* 177 */         subtemplate.setEnclosingInstance(self);
/* 178 */         subtemplate.setName(i.getText() + "_subtemplate");
/* 179 */         self.addChunk(c);
/*     */ 
/* 181 */         template(subtemplate);
/* 182 */         if (c != null) c.setSubtemplate(subtemplate);
/*     */ 
/* 186 */         while (LA(1) == 8) {
/* 187 */           ei = LT(1);
/* 188 */           match(8);
/*     */ 
/* 190 */           ASTExpr ec = self.parseAction(ei.getText());
/*     */ 
/* 192 */           StringTemplate elseIfSubtemplate = new StringTemplate(self.getGroup(), null);
/*     */ 
/* 194 */           elseIfSubtemplate.setEnclosingInstance(self);
/* 195 */           elseIfSubtemplate.setName(ei.getText() + "_subtemplate");
/*     */ 
/* 197 */           template(elseIfSubtemplate);
/* 198 */           if (c != null) c.addElseIfSubtemplate(ec, elseIfSubtemplate);
/*     */ 
/*     */         }
/*     */ 
/* 207 */         switch (LA(1))
/*     */         {
/*     */         case 9:
/* 210 */           match(9);
/*     */ 
/* 213 */           StringTemplate elseSubtemplate = new StringTemplate(self.getGroup(), null);
/*     */ 
/* 215 */           elseSubtemplate.setEnclosingInstance(self);
/* 216 */           elseSubtemplate.setName("else_subtemplate");
/*     */ 
/* 218 */           template(elseSubtemplate);
/* 219 */           if (c != null) c.setElseSubtemplate(elseSubtemplate); break;
/*     */         case 10:
/* 224 */           break;
/*     */         default:
/* 228 */           throw new NoViableAltException(LT(1), getFilename());
/*     */         }
/*     */ 
/* 232 */         match(10);
/* 233 */         break;
/*     */       case 11:
/* 237 */         rr = LT(1);
/* 238 */         match(11);
/*     */ 
/* 242 */         String regionName = rr.getText();
/* 243 */         String mangledRef = null;
/* 244 */         boolean err = false;
/*     */ 
/* 247 */         if (regionName.startsWith("super."))
/*     */         {
/* 249 */           String regionRef = regionName.substring("super.".length(), regionName.length());
/*     */ 
/* 251 */           String templateScope = self.getGroup().getUnMangledTemplateName(self.getName());
/*     */ 
/* 253 */           StringTemplate scopeST = self.getGroup().lookupTemplate(templateScope);
/* 254 */           if (scopeST == null) {
/* 255 */             self.getGroup().error("reference to region within undefined template: " + templateScope);
/*     */ 
/* 257 */             err = true;
/*     */           }
/* 259 */           if (!scopeST.containsRegionName(regionRef)) {
/* 260 */             self.getGroup().error("template " + templateScope + " has no region called " + regionRef);
/*     */ 
/* 262 */             err = true;
/*     */           }
/*     */           else {
/* 265 */             mangledRef = self.getGroup().getMangledRegionName(templateScope, regionRef);
/*     */ 
/* 267 */             mangledRef = "super." + mangledRef;
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 272 */           StringTemplate regionST = self.getGroup().defineImplicitRegionTemplate(self, regionName);
/*     */ 
/* 274 */           mangledRef = regionST.getName();
/*     */         }
/*     */ 
/* 277 */         if (!err)
/*     */         {
/* 279 */           String indent = ((ChunkToken)rr).getIndentation();
/* 280 */           ASTExpr c = self.parseAction(mangledRef + "()");
/* 281 */           c.setIndentation(indent);
/* 282 */           self.addChunk(c);
/* 283 */         }break;
/*     */       case 12:
/* 289 */         rd = LT(1);
/* 290 */         match(12);
/*     */ 
/* 292 */         String combinedNameTemplateStr = rd.getText();
/* 293 */         int indexOfDefSymbol = combinedNameTemplateStr.indexOf("::=");
/* 294 */         if (indexOfDefSymbol >= 1) {
/* 295 */           String regionName = combinedNameTemplateStr.substring(0, indexOfDefSymbol);
/* 296 */           String template = combinedNameTemplateStr.substring(indexOfDefSymbol + 3, combinedNameTemplateStr.length());
/*     */ 
/* 299 */           StringTemplate regionST = self.getGroup().defineRegionTemplate(self, regionName, template, 2);
/*     */ 
/* 305 */           String indent = ((ChunkToken)rd).getIndentation();
/* 306 */           ASTExpr c = self.parseAction(regionST.getName() + "()");
/* 307 */           c.setIndentation(indent);
/* 308 */           self.addChunk(c);
/*     */         }
/*     */         else {
/* 311 */           self.error("embedded region definition screwed up");
/*     */         }
/*     */ 
/* 314 */         break;
/*     */       case 8:
/*     */       case 9:
/*     */       case 10:
/*     */       default:
/* 318 */         throw new NoViableAltException(LT(1), getFilename());
/*     */       }
/*     */     }
/*     */     catch (RecognitionException ex)
/*     */     {
/* 323 */       reportError(ex);
/* 324 */       recover(ex, _tokenSet_1);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static final long[] mk_tokenSet_0()
/*     */   {
/* 357 */     long[] data = { 1792L, 0L };
/* 358 */     return data;
/*     */   }
/*     */ 
/*     */   private static final long[] mk_tokenSet_1() {
/* 362 */     long[] data = { 8176L, 0L };
/* 363 */     return data;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.stringtemplate.language.TemplateParser
 * JD-Core Version:    0.6.2
 */