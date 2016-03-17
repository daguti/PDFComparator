/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.pdf.HyphenationEvent;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Phrase extends ArrayList<Element>
/*     */   implements TextElementArray
/*     */ {
/*     */   private static final long serialVersionUID = 2643594602455068231L;
/*  88 */   protected float leading = (0.0F / 0.0F);
/*     */ 
/*  91 */   protected float multipliedLeading = 0.0F;
/*     */   protected Font font;
/*  99 */   protected HyphenationEvent hyphenation = null;
/*     */ 
/* 105 */   protected TabSettings tabSettings = null;
/*     */ 
/*     */   public Phrase()
/*     */   {
/* 113 */     this(16.0F);
/*     */   }
/*     */ 
/*     */   public Phrase(Phrase phrase)
/*     */   {
/* 122 */     addAll(phrase);
/* 123 */     setLeading(phrase.getLeading(), phrase.getMultipliedLeading());
/* 124 */     this.font = phrase.getFont();
/* 125 */     this.tabSettings = phrase.getTabSettings();
/* 126 */     setHyphenation(phrase.getHyphenation());
/*     */   }
/*     */ 
/*     */   public Phrase(float leading)
/*     */   {
/* 135 */     this.leading = leading;
/* 136 */     this.font = new Font();
/*     */   }
/*     */ 
/*     */   public Phrase(Chunk chunk)
/*     */   {
/* 145 */     super.add(chunk);
/* 146 */     this.font = chunk.getFont();
/* 147 */     setHyphenation(chunk.getHyphenation());
/*     */   }
/*     */ 
/*     */   public Phrase(float leading, Chunk chunk)
/*     */   {
/* 158 */     this.leading = leading;
/* 159 */     super.add(chunk);
/* 160 */     this.font = chunk.getFont();
/* 161 */     setHyphenation(chunk.getHyphenation());
/*     */   }
/*     */ 
/*     */   public Phrase(String string)
/*     */   {
/* 170 */     this((0.0F / 0.0F), string, new Font());
/*     */   }
/*     */ 
/*     */   public Phrase(String string, Font font)
/*     */   {
/* 180 */     this((0.0F / 0.0F), string, font);
/*     */   }
/*     */ 
/*     */   public Phrase(float leading, String string)
/*     */   {
/* 190 */     this(leading, string, new Font());
/*     */   }
/*     */ 
/*     */   public Phrase(float leading, String string, Font font)
/*     */   {
/* 202 */     this.leading = leading;
/* 203 */     this.font = font;
/*     */ 
/* 205 */     if ((string != null) && (string.length() != 0))
/* 206 */       super.add(new Chunk(string, font));
/*     */   }
/*     */ 
/*     */   public boolean process(ElementListener listener)
/*     */   {
/*     */     try
/*     */     {
/* 221 */       for (Object element : this) {
/* 222 */         listener.add((Element)element);
/*     */       }
/* 224 */       return true;
/*     */     } catch (DocumentException de) {
/*     */     }
/* 227 */     return false;
/*     */   }
/*     */ 
/*     */   public int type()
/*     */   {
/* 237 */     return 11;
/*     */   }
/*     */ 
/*     */   public List<Chunk> getChunks()
/*     */   {
/* 246 */     List tmp = new ArrayList();
/* 247 */     for (Element element : this) {
/* 248 */       tmp.addAll(element.getChunks());
/*     */     }
/* 250 */     return tmp;
/*     */   }
/*     */ 
/*     */   public boolean isContent()
/*     */   {
/* 258 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isNestable()
/*     */   {
/* 266 */     return true;
/*     */   }
/*     */ 
/*     */   public void add(int index, Element element)
/*     */   {
/* 282 */     if (element == null) return;
/* 283 */     switch (element.type()) {
/*     */     case 10:
/* 285 */       Chunk chunk = (Chunk)element;
/* 286 */       if (!this.font.isStandardFont()) {
/* 287 */         chunk.setFont(this.font.difference(chunk.getFont()));
/*     */       }
/* 289 */       if ((this.hyphenation != null) && (chunk.getHyphenation() == null) && (!chunk.isEmpty())) {
/* 290 */         chunk.setHyphenation(this.hyphenation);
/*     */       }
/* 292 */       super.add(index, chunk);
/* 293 */       return;
/*     */     case 11:
/*     */     case 12:
/*     */     case 14:
/*     */     case 17:
/*     */     case 23:
/*     */     case 29:
/*     */     case 37:
/*     */     case 50:
/*     */     case 55:
/*     */     case 666:
/* 304 */       super.add(index, element);
/* 305 */       return;
/*     */     }
/* 307 */     throw new ClassCastException(MessageLocalization.getComposedMessage("insertion.of.illegal.element.1", new Object[] { element.getClass().getName() }));
/*     */   }
/*     */ 
/*     */   public boolean add(String s)
/*     */   {
/* 319 */     if (s == null) {
/* 320 */       return false;
/*     */     }
/* 322 */     return super.add(new Chunk(s, this.font)); } 
/*     */   public boolean add(Element element) { // Byte code:
/*     */     //   0: aload_1
/*     */     //   1: ifnonnull +5 -> 6
/*     */     //   4: iconst_0
/*     */     //   5: ireturn
/*     */     //   6: aload_1
/*     */     //   7: invokeinterface 36 1 0
/*     */     //   12: lookupswitch	default:+194->206, 10:+100->112, 11:+109->121, 12:+109->121, 14:+188->200, 17:+188->200, 23:+188->200, 29:+188->200, 37:+188->200, 50:+188->200, 55:+188->200, 666:+188->200
/*     */     //   113: aload_1
/*     */     //   114: checkcast 25	com/itextpdf/text/Chunk
/*     */     //   117: invokevirtual 50	com/itextpdf/text/Phrase:addChunk	(Lcom/itextpdf/text/Chunk;)Z
/*     */     //   120: ireturn
/*     */     //   121: aload_1
/*     */     //   122: checkcast 51	com/itextpdf/text/Phrase
/*     */     //   125: astore_2
/*     */     //   126: iconst_1
/*     */     //   127: istore_3
/*     */     //   128: aload_2
/*     */     //   129: invokevirtual 27	com/itextpdf/text/Phrase:iterator	()Ljava/util/Iterator;
/*     */     //   132: astore 5
/*     */     //   134: aload 5
/*     */     //   136: invokeinterface 28 1 0
/*     */     //   141: ifeq +57 -> 198
/*     */     //   144: aload 5
/*     */     //   146: invokeinterface 29 1 0
/*     */     //   151: checkcast 30	com/itextpdf/text/Element
/*     */     //   154: astore 6
/*     */     //   156: aload 6
/*     */     //   158: checkcast 30	com/itextpdf/text/Element
/*     */     //   161: astore 4
/*     */     //   163: aload 4
/*     */     //   165: instanceof 25
/*     */     //   168: ifeq +18 -> 186
/*     */     //   171: iload_3
/*     */     //   172: aload_0
/*     */     //   173: aload 4
/*     */     //   175: checkcast 25	com/itextpdf/text/Chunk
/*     */     //   178: invokevirtual 50	com/itextpdf/text/Phrase:addChunk	(Lcom/itextpdf/text/Chunk;)Z
/*     */     //   181: iand
/*     */     //   182: istore_3
/*     */     //   183: goto +12 -> 195
/*     */     //   186: iload_3
/*     */     //   187: aload_0
/*     */     //   188: aload 4
/*     */     //   190: invokevirtual 52	com/itextpdf/text/Phrase:add	(Lcom/itextpdf/text/Element;)Z
/*     */     //   193: iand
/*     */     //   194: istore_3
/*     */     //   195: goto -61 -> 134
/*     */     //   198: iload_3
/*     */     //   199: ireturn
/*     */     //   200: aload_0
/*     */     //   201: aload_1
/*     */     //   202: invokespecial 20	java/util/ArrayList:add	(Ljava/lang/Object;)Z
/*     */     //   205: ireturn
/*     */     //   206: new 43	java/lang/ClassCastException
/*     */     //   209: dup
/*     */     //   210: aload_1
/*     */     //   211: invokeinterface 36 1 0
/*     */     //   216: invokestatic 53	java/lang/String:valueOf	(I)Ljava/lang/String;
/*     */     //   219: invokespecial 49	java/lang/ClassCastException:<init>	(Ljava/lang/String;)V
/*     */     //   222: athrow
/*     */     //   223: astore_2
/*     */     //   224: new 43	java/lang/ClassCastException
/*     */     //   227: dup
/*     */     //   228: ldc 44
/*     */     //   230: iconst_1
/*     */     //   231: anewarray 45	java/lang/Object
/*     */     //   234: dup
/*     */     //   235: iconst_0
/*     */     //   236: aload_2
/*     */     //   237: invokevirtual 54	java/lang/ClassCastException:getMessage	()Ljava/lang/String;
/*     */     //   240: aastore
/*     */     //   241: invokestatic 48	com/itextpdf/text/error_messages/MessageLocalization:getComposedMessage	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
/*     */     //   244: invokespecial 49	java/lang/ClassCastException:<init>	(Ljava/lang/String;)V
/*     */     //   247: athrow
/*     */     //
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   6	120	223	java/lang/ClassCastException
/*     */     //   121	199	223	java/lang/ClassCastException
/*     */     //   200	205	223	java/lang/ClassCastException
/*     */     //   206	223	223	java/lang/ClassCastException } 
/* 385 */   public boolean addAll(Collection<? extends Element> collection) { for (Element e : collection) {
/* 386 */       add(e);
/*     */     }
/* 388 */     return true;
/*     */   }
/*     */ 
/*     */   protected boolean addChunk(Chunk chunk)
/*     */   {
/* 400 */     Font f = chunk.getFont();
/* 401 */     String c = chunk.getContent();
/* 402 */     if ((this.font != null) && (!this.font.isStandardFont())) {
/* 403 */       f = this.font.difference(chunk.getFont());
/*     */     }
/* 405 */     if ((size() > 0) && (!chunk.hasAttributes()))
/*     */       try {
/* 407 */         Chunk previous = (Chunk)get(size() - 1);
/* 408 */         if ((!previous.hasAttributes()) && ((f == null) || (f.compareTo(previous.getFont()) == 0)) && (!"".equals(previous.getContent().trim())) && (!"".equals(c.trim())))
/*     */         {
/* 413 */           previous.append(c);
/* 414 */           return true;
/*     */         }
/*     */       }
/*     */       catch (ClassCastException cce)
/*     */       {
/*     */       }
/* 420 */     Chunk newChunk = new Chunk(c, f);
/* 421 */     newChunk.setAttributes(chunk.getAttributes());
/* 422 */     newChunk.role = chunk.getRole();
/* 423 */     newChunk.accessibleAttributes = chunk.getAccessibleAttributes();
/* 424 */     if ((this.hyphenation != null) && (newChunk.getHyphenation() == null) && (!newChunk.isEmpty())) {
/* 425 */       newChunk.setHyphenation(this.hyphenation);
/*     */     }
/* 427 */     return super.add(newChunk);
/*     */   }
/*     */ 
/*     */   protected void addSpecial(Element object)
/*     */   {
/* 436 */     super.add(object);
/*     */   }
/*     */ 
/*     */   public void setLeading(float fixedLeading, float multipliedLeading)
/*     */   {
/* 449 */     this.leading = fixedLeading;
/* 450 */     this.multipliedLeading = multipliedLeading;
/*     */   }
/*     */ 
/*     */   public void setLeading(float fixedLeading)
/*     */   {
/* 457 */     this.leading = fixedLeading;
/* 458 */     this.multipliedLeading = 0.0F;
/*     */   }
/*     */ 
/*     */   public void setMultipliedLeading(float multipliedLeading)
/*     */   {
/* 468 */     this.leading = 0.0F;
/* 469 */     this.multipliedLeading = multipliedLeading;
/*     */   }
/*     */ 
/*     */   public void setFont(Font font)
/*     */   {
/* 477 */     this.font = font;
/*     */   }
/*     */ 
/*     */   public float getLeading()
/*     */   {
/* 488 */     if ((Float.isNaN(this.leading)) && (this.font != null)) {
/* 489 */       return this.font.getCalculatedLeading(1.5F);
/*     */     }
/* 491 */     return this.leading;
/*     */   }
/*     */ 
/*     */   public float getMultipliedLeading()
/*     */   {
/* 499 */     return this.multipliedLeading;
/*     */   }
/*     */ 
/*     */   public float getTotalLeading()
/*     */   {
/* 511 */     float m = this.font == null ? 12.0F * this.multipliedLeading : this.font.getCalculatedLeading(this.multipliedLeading);
/*     */ 
/* 513 */     if ((m > 0.0F) && (!hasLeading())) {
/* 514 */       return m;
/*     */     }
/* 516 */     return getLeading() + m;
/*     */   }
/*     */ 
/*     */   public boolean hasLeading()
/*     */   {
/* 525 */     if (Float.isNaN(this.leading)) {
/* 526 */       return false;
/*     */     }
/* 528 */     return true;
/*     */   }
/*     */ 
/*     */   public Font getFont()
/*     */   {
/* 537 */     return this.font;
/*     */   }
/*     */ 
/*     */   public String getContent()
/*     */   {
/* 546 */     StringBuffer buf = new StringBuffer();
/* 547 */     for (Chunk c : getChunks()) {
/* 548 */       buf.append(c.toString());
/*     */     }
/* 550 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 561 */     switch (size()) {
/*     */     case 0:
/* 563 */       return true;
/*     */     case 1:
/* 565 */       Element element = (Element)get(0);
/* 566 */       if ((element.type() == 10) && (((Chunk)element).isEmpty())) {
/* 567 */         return true;
/*     */       }
/* 569 */       return false;
/*     */     }
/* 571 */     return false;
/*     */   }
/*     */ 
/*     */   public HyphenationEvent getHyphenation()
/*     */   {
/* 581 */     return this.hyphenation;
/*     */   }
/*     */ 
/*     */   public void setHyphenation(HyphenationEvent hyphenation)
/*     */   {
/* 590 */     this.hyphenation = hyphenation;
/*     */   }
/*     */ 
/*     */   public TabSettings getTabSettings()
/*     */   {
/* 599 */     return this.tabSettings;
/*     */   }
/*     */ 
/*     */   public void setTabSettings(TabSettings tabSettings)
/*     */   {
/* 608 */     this.tabSettings = tabSettings;
/*     */   }
/*     */ 
/*     */   private Phrase(boolean dummy)
/*     */   {
/*     */   }
/*     */ 
/*     */   public static final Phrase getInstance(String string)
/*     */   {
/* 627 */     return getInstance(16, string, new Font());
/*     */   }
/*     */ 
/*     */   public static final Phrase getInstance(int leading, String string)
/*     */   {
/* 637 */     return getInstance(leading, string, new Font());
/*     */   }
/*     */ 
/*     */   public static final Phrase getInstance(int leading, String string, Font font)
/*     */   {
/* 648 */     Phrase p = new Phrase(true);
/* 649 */     p.setLeading(leading);
/* 650 */     p.font = font;
/* 651 */     if ((font.getFamily() != Font.FontFamily.SYMBOL) && (font.getFamily() != Font.FontFamily.ZAPFDINGBATS) && (font.getBaseFont() == null))
/*     */     {
/*     */       int index;
/* 653 */       while ((index = SpecialSymbol.index(string)) > -1) {
/* 654 */         if (index > 0) {
/* 655 */           String firstPart = string.substring(0, index);
/* 656 */           p.add(new Chunk(firstPart, font));
/* 657 */           string = string.substring(index);
/*     */         }
/* 659 */         Font symbol = new Font(Font.FontFamily.SYMBOL, font.getSize(), font.getStyle(), font.getColor());
/* 660 */         StringBuffer buf = new StringBuffer();
/* 661 */         buf.append(SpecialSymbol.getCorrespondingSymbol(string.charAt(0)));
/* 662 */         string = string.substring(1);
/* 663 */         while (SpecialSymbol.index(string) == 0) {
/* 664 */           buf.append(SpecialSymbol.getCorrespondingSymbol(string.charAt(0)));
/* 665 */           string = string.substring(1);
/*     */         }
/* 667 */         p.add(new Chunk(buf.toString(), symbol));
/*     */       }
/*     */     }
/* 670 */     if ((string != null) && (string.length() != 0)) {
/* 671 */       p.add(new Chunk(string, font));
/*     */     }
/* 673 */     return p;
/*     */   }
/*     */ 
/*     */   public boolean trim() {
/* 677 */     while (size() > 0) {
/* 678 */       Element firstChunk = (Element)get(0);
/* 679 */       if ((!(firstChunk instanceof Chunk)) || (!((Chunk)firstChunk).isWhitespace())) break;
/* 680 */       remove(firstChunk);
/*     */     }
/*     */ 
/* 685 */     while (size() > 0) {
/* 686 */       Element lastChunk = (Element)get(size() - 1);
/* 687 */       if ((!(lastChunk instanceof Chunk)) || (!((Chunk)lastChunk).isWhitespace())) break;
/* 688 */       remove(lastChunk);
/*     */     }
/*     */ 
/* 693 */     return size() > 0;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.Phrase
 * JD-Core Version:    0.6.2
 */