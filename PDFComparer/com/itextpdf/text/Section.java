/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import com.itextpdf.text.api.Indentable;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.pdf.PdfName;
/*     */ import com.itextpdf.text.pdf.PdfObject;
/*     */ import com.itextpdf.text.pdf.interfaces.IAccessibleElement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Section extends ArrayList<Element>
/*     */   implements TextElementArray, LargeElement, Indentable, IAccessibleElement
/*     */ {
/*     */   public static final int NUMBERSTYLE_DOTTED = 0;
/*     */   public static final int NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT = 1;
/*     */   private static final long serialVersionUID = 3324172577544748043L;
/*     */   protected Paragraph title;
/*     */   protected String bookmarkTitle;
/*     */   protected int numberDepth;
/* 116 */   protected int numberStyle = 0;
/*     */   protected float indentationLeft;
/*     */   protected float indentationRight;
/*     */   protected float indentation;
/* 128 */   protected boolean bookmarkOpen = true;
/*     */ 
/* 131 */   protected boolean triggerNewPage = false;
/*     */ 
/* 134 */   protected int subsections = 0;
/*     */ 
/* 137 */   protected ArrayList<Integer> numbers = null;
/*     */ 
/* 143 */   protected boolean complete = true;
/*     */ 
/* 149 */   protected boolean addedCompletely = false;
/*     */ 
/* 155 */   protected boolean notAddedYet = true;
/*     */ 
/*     */   protected Section()
/*     */   {
/* 163 */     this.title = new Paragraph();
/* 164 */     this.numberDepth = 1;
/* 165 */     this.title.setRole(new PdfName("H" + this.numberDepth));
/*     */   }
/*     */ 
/*     */   protected Section(Paragraph title, int numberDepth)
/*     */   {
/* 175 */     this.numberDepth = numberDepth;
/* 176 */     this.title = title;
/* 177 */     if (title != null)
/* 178 */       title.setRole(new PdfName("H" + numberDepth));
/*     */   }
/*     */ 
/*     */   public boolean process(ElementListener listener)
/*     */   {
/*     */     try
/*     */     {
/* 193 */       for (Object element2 : this) {
/* 194 */         Element element = (Element)element2;
/* 195 */         listener.add(element);
/*     */       }
/* 197 */       return true;
/*     */     } catch (DocumentException de) {
/*     */     }
/* 200 */     return false;
/*     */   }
/*     */ 
/*     */   public int type()
/*     */   {
/* 210 */     return 13;
/*     */   }
/*     */ 
/*     */   public boolean isChapter()
/*     */   {
/* 220 */     return type() == 16;
/*     */   }
/*     */ 
/*     */   public boolean isSection()
/*     */   {
/* 230 */     return type() == 13;
/*     */   }
/*     */ 
/*     */   public List<Chunk> getChunks()
/*     */   {
/* 239 */     List tmp = new ArrayList();
/* 240 */     for (Object element : this) {
/* 241 */       tmp.addAll(((Element)element).getChunks());
/*     */     }
/* 243 */     return tmp;
/*     */   }
/*     */ 
/*     */   public boolean isContent()
/*     */   {
/* 251 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isNestable()
/*     */   {
/* 259 */     return false;
/*     */   }
/*     */ 
/*     */   public void add(int index, Element element)
/*     */   {
/* 275 */     if (isAddedCompletely())
/* 276 */       throw new IllegalStateException(MessageLocalization.getComposedMessage("this.largeelement.has.already.been.added.to.the.document", new Object[0]));
/*     */     try
/*     */     {
/* 279 */       if (element.isNestable()) {
/* 280 */         super.add(index, element);
/*     */       }
/*     */       else
/* 283 */         throw new ClassCastException(MessageLocalization.getComposedMessage("you.can.t.add.a.1.to.a.section", new Object[] { element.getClass().getName() }));
/*     */     }
/*     */     catch (ClassCastException cce)
/*     */     {
/* 287 */       throw new ClassCastException(MessageLocalization.getComposedMessage("insertion.of.illegal.element.1", new Object[] { cce.getMessage() })); }  } 
/*     */   public boolean add(Element element) { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: invokevirtual 33	com/itextpdf/text/Section:isAddedCompletely	()Z
/*     */     //   4: ifeq +20 -> 24
/*     */     //   7: new 34	java/lang/IllegalStateException
/*     */     //   10: dup
/*     */     //   11: ldc 35
/*     */     //   13: iconst_0
/*     */     //   14: anewarray 36	java/lang/Object
/*     */     //   17: invokestatic 37	com/itextpdf/text/error_messages/MessageLocalization:getComposedMessage	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
/*     */     //   20: invokespecial 38	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
/*     */     //   23: athrow
/*     */     //   24: aload_1
/*     */     //   25: invokeinterface 48 1 0
/*     */     //   30: bipush 13
/*     */     //   32: if_icmpne +33 -> 65
/*     */     //   35: aload_1
/*     */     //   36: checkcast 49	com/itextpdf/text/Section
/*     */     //   39: astore_2
/*     */     //   40: aload_2
/*     */     //   41: aload_0
/*     */     //   42: dup
/*     */     //   43: getfield 5	com/itextpdf/text/Section:subsections	I
/*     */     //   46: iconst_1
/*     */     //   47: iadd
/*     */     //   48: dup_x1
/*     */     //   49: putfield 5	com/itextpdf/text/Section:subsections	I
/*     */     //   52: aload_0
/*     */     //   53: getfield 6	com/itextpdf/text/Section:numbers	Ljava/util/ArrayList;
/*     */     //   56: invokespecial 50	com/itextpdf/text/Section:setNumbers	(ILjava/util/ArrayList;)V
/*     */     //   59: aload_0
/*     */     //   60: aload_2
/*     */     //   61: invokespecial 51	java/util/ArrayList:add	(Ljava/lang/Object;)Z
/*     */     //   64: ireturn
/*     */     //   65: aload_1
/*     */     //   66: instanceof 52
/*     */     //   69: ifeq +58 -> 127
/*     */     //   72: aload_1
/*     */     //   73: checkcast 53	com/itextpdf/text/MarkedObject
/*     */     //   76: getfield 54	com/itextpdf/text/MarkedObject:element	Lcom/itextpdf/text/Element;
/*     */     //   79: invokeinterface 48 1 0
/*     */     //   84: bipush 13
/*     */     //   86: if_icmpne +41 -> 127
/*     */     //   89: aload_1
/*     */     //   90: checkcast 52	com/itextpdf/text/MarkedSection
/*     */     //   93: astore_2
/*     */     //   94: aload_2
/*     */     //   95: getfield 55	com/itextpdf/text/MarkedSection:element	Lcom/itextpdf/text/Element;
/*     */     //   98: checkcast 49	com/itextpdf/text/Section
/*     */     //   101: astore_3
/*     */     //   102: aload_3
/*     */     //   103: aload_0
/*     */     //   104: dup
/*     */     //   105: getfield 5	com/itextpdf/text/Section:subsections	I
/*     */     //   108: iconst_1
/*     */     //   109: iadd
/*     */     //   110: dup_x1
/*     */     //   111: putfield 5	com/itextpdf/text/Section:subsections	I
/*     */     //   114: aload_0
/*     */     //   115: getfield 6	com/itextpdf/text/Section:numbers	Ljava/util/ArrayList;
/*     */     //   118: invokespecial 50	com/itextpdf/text/Section:setNumbers	(ILjava/util/ArrayList;)V
/*     */     //   121: aload_0
/*     */     //   122: aload_2
/*     */     //   123: invokespecial 51	java/util/ArrayList:add	(Ljava/lang/Object;)Z
/*     */     //   126: ireturn
/*     */     //   127: aload_1
/*     */     //   128: invokeinterface 39 1 0
/*     */     //   133: ifeq +9 -> 142
/*     */     //   136: aload_0
/*     */     //   137: aload_1
/*     */     //   138: invokespecial 51	java/util/ArrayList:add	(Ljava/lang/Object;)Z
/*     */     //   141: ireturn
/*     */     //   142: new 41	java/lang/ClassCastException
/*     */     //   145: dup
/*     */     //   146: ldc 42
/*     */     //   148: iconst_1
/*     */     //   149: anewarray 36	java/lang/Object
/*     */     //   152: dup
/*     */     //   153: iconst_0
/*     */     //   154: aload_1
/*     */     //   155: invokevirtual 43	java/lang/Object:getClass	()Ljava/lang/Class;
/*     */     //   158: invokevirtual 44	java/lang/Class:getName	()Ljava/lang/String;
/*     */     //   161: aastore
/*     */     //   162: invokestatic 37	com/itextpdf/text/error_messages/MessageLocalization:getComposedMessage	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
/*     */     //   165: invokespecial 45	java/lang/ClassCastException:<init>	(Ljava/lang/String;)V
/*     */     //   168: athrow
/*     */     //   169: astore_2
/*     */     //   170: new 41	java/lang/ClassCastException
/*     */     //   173: dup
/*     */     //   174: ldc 46
/*     */     //   176: iconst_1
/*     */     //   177: anewarray 36	java/lang/Object
/*     */     //   180: dup
/*     */     //   181: iconst_0
/*     */     //   182: aload_2
/*     */     //   183: invokevirtual 47	java/lang/ClassCastException:getMessage	()Ljava/lang/String;
/*     */     //   186: aastore
/*     */     //   187: invokestatic 37	com/itextpdf/text/error_messages/MessageLocalization:getComposedMessage	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
/*     */     //   190: invokespecial 45	java/lang/ClassCastException:<init>	(Ljava/lang/String;)V
/*     */     //   193: athrow
/*     */     //
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   24	64	169	java/lang/ClassCastException
/*     */     //   65	126	169	java/lang/ClassCastException
/*     */     //   127	141	169	java/lang/ClassCastException
/*     */     //   142	169	169	java/lang/ClassCastException } 
/* 339 */   public boolean addAll(Collection<? extends Element> collection) { if (collection.size() == 0)
/* 340 */       return false;
/* 341 */     for (Element element : collection) {
/* 342 */       add(element);
/*     */     }
/* 344 */     return true;
/*     */   }
/*     */ 
/*     */   public Section addSection(float indentation, Paragraph title, int numberDepth)
/*     */   {
/* 358 */     if (isAddedCompletely()) {
/* 359 */       throw new IllegalStateException(MessageLocalization.getComposedMessage("this.largeelement.has.already.been.added.to.the.document", new Object[0]));
/*     */     }
/* 361 */     Section section = new Section(title, numberDepth);
/* 362 */     section.setIndentation(indentation);
/* 363 */     add(section);
/* 364 */     return section;
/*     */   }
/*     */ 
/*     */   public Section addSection(float indentation, Paragraph title)
/*     */   {
/* 375 */     return addSection(indentation, title, this.numberDepth + 1);
/*     */   }
/*     */ 
/*     */   public Section addSection(Paragraph title, int numberDepth)
/*     */   {
/* 386 */     return addSection(0.0F, title, numberDepth);
/*     */   }
/*     */ 
/*     */   protected MarkedSection addMarkedSection()
/*     */   {
/* 394 */     MarkedSection section = new MarkedSection(new Section(null, this.numberDepth + 1));
/* 395 */     add(section);
/* 396 */     return section;
/*     */   }
/*     */ 
/*     */   public Section addSection(Paragraph title)
/*     */   {
/* 406 */     return addSection(0.0F, title, this.numberDepth + 1);
/*     */   }
/*     */ 
/*     */   public Section addSection(float indentation, String title, int numberDepth)
/*     */   {
/* 418 */     return addSection(indentation, new Paragraph(title), numberDepth);
/*     */   }
/*     */ 
/*     */   public Section addSection(String title, int numberDepth)
/*     */   {
/* 429 */     return addSection(new Paragraph(title), numberDepth);
/*     */   }
/*     */ 
/*     */   public Section addSection(float indentation, String title)
/*     */   {
/* 440 */     return addSection(indentation, new Paragraph(title));
/*     */   }
/*     */ 
/*     */   public Section addSection(String title)
/*     */   {
/* 450 */     return addSection(new Paragraph(title));
/*     */   }
/*     */ 
/*     */   public void setTitle(Paragraph title)
/*     */   {
/* 461 */     this.title = title;
/*     */   }
/*     */ 
/*     */   public Paragraph getTitle()
/*     */   {
/* 470 */     return constructTitle(this.title, this.numbers, this.numberDepth, this.numberStyle);
/*     */   }
/*     */ 
/*     */   public static Paragraph constructTitle(Paragraph title, ArrayList<Integer> numbers, int numberDepth, int numberStyle)
/*     */   {
/* 483 */     if (title == null) {
/* 484 */       return null;
/*     */     }
/*     */ 
/* 487 */     int depth = Math.min(numbers.size(), numberDepth);
/* 488 */     if (depth < 1) {
/* 489 */       return title;
/*     */     }
/* 491 */     StringBuffer buf = new StringBuffer(" ");
/* 492 */     for (int i = 0; i < depth; i++) {
/* 493 */       buf.insert(0, ".");
/* 494 */       buf.insert(0, ((Integer)numbers.get(i)).intValue());
/*     */     }
/* 496 */     if (numberStyle == 1) {
/* 497 */       buf.deleteCharAt(buf.length() - 2);
/*     */     }
/* 499 */     Paragraph result = new Paragraph(title);
/*     */ 
/* 501 */     result.add(0, new Chunk(buf.toString(), title.getFont()));
/* 502 */     return result;
/*     */   }
/*     */ 
/*     */   public void setNumberDepth(int numberDepth)
/*     */   {
/* 515 */     this.numberDepth = numberDepth;
/*     */   }
/*     */ 
/*     */   public int getNumberDepth()
/*     */   {
/* 524 */     return this.numberDepth;
/*     */   }
/*     */ 
/*     */   public void setNumberStyle(int numberStyle)
/*     */   {
/* 535 */     this.numberStyle = numberStyle;
/*     */   }
/*     */ 
/*     */   public int getNumberStyle()
/*     */   {
/* 544 */     return this.numberStyle;
/*     */   }
/*     */ 
/*     */   public void setIndentationLeft(float indentation)
/*     */   {
/* 553 */     this.indentationLeft = indentation;
/*     */   }
/*     */ 
/*     */   public float getIndentationLeft()
/*     */   {
/* 562 */     return this.indentationLeft;
/*     */   }
/*     */ 
/*     */   public void setIndentationRight(float indentation)
/*     */   {
/* 571 */     this.indentationRight = indentation;
/*     */   }
/*     */ 
/*     */   public float getIndentationRight()
/*     */   {
/* 580 */     return this.indentationRight;
/*     */   }
/*     */ 
/*     */   public void setIndentation(float indentation)
/*     */   {
/* 589 */     this.indentation = indentation;
/*     */   }
/*     */ 
/*     */   public float getIndentation()
/*     */   {
/* 598 */     return this.indentation;
/*     */   }
/*     */ 
/*     */   public void setBookmarkOpen(boolean bookmarkOpen)
/*     */   {
/* 606 */     this.bookmarkOpen = bookmarkOpen;
/*     */   }
/*     */ 
/*     */   public boolean isBookmarkOpen()
/*     */   {
/* 614 */     return this.bookmarkOpen;
/*     */   }
/*     */ 
/*     */   public void setTriggerNewPage(boolean triggerNewPage)
/*     */   {
/* 622 */     this.triggerNewPage = triggerNewPage;
/*     */   }
/*     */ 
/*     */   public boolean isTriggerNewPage()
/*     */   {
/* 630 */     return (this.triggerNewPage) && (this.notAddedYet);
/*     */   }
/*     */ 
/*     */   public void setBookmarkTitle(String bookmarkTitle)
/*     */   {
/* 639 */     this.bookmarkTitle = bookmarkTitle;
/*     */   }
/*     */ 
/*     */   public Paragraph getBookmarkTitle()
/*     */   {
/* 647 */     if (this.bookmarkTitle == null) {
/* 648 */       return getTitle();
/*     */     }
/* 650 */     return new Paragraph(this.bookmarkTitle);
/*     */   }
/*     */ 
/*     */   public void setChapterNumber(int number)
/*     */   {
/* 658 */     this.numbers.set(this.numbers.size() - 1, Integer.valueOf(number));
/*     */ 
/* 660 */     for (Iterator i = iterator(); i.hasNext(); ) {
/* 661 */       Object s = i.next();
/* 662 */       if ((s instanceof Section))
/* 663 */         ((Section)s).setChapterNumber(number);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getDepth()
/*     */   {
/* 674 */     return this.numbers.size();
/*     */   }
/*     */ 
/*     */   private void setNumbers(int number, ArrayList<Integer> numbers)
/*     */   {
/* 686 */     this.numbers = new ArrayList();
/* 687 */     this.numbers.add(Integer.valueOf(number));
/* 688 */     this.numbers.addAll(numbers);
/*     */   }
/*     */ 
/*     */   public boolean isNotAddedYet()
/*     */   {
/* 697 */     return this.notAddedYet;
/*     */   }
/*     */ 
/*     */   public void setNotAddedYet(boolean notAddedYet)
/*     */   {
/* 707 */     this.notAddedYet = notAddedYet;
/*     */   }
/*     */ 
/*     */   protected boolean isAddedCompletely()
/*     */   {
/* 715 */     return this.addedCompletely;
/*     */   }
/*     */ 
/*     */   protected void setAddedCompletely(boolean addedCompletely)
/*     */   {
/* 723 */     this.addedCompletely = addedCompletely;
/*     */   }
/*     */ 
/*     */   public void flushContent()
/*     */   {
/* 731 */     setNotAddedYet(false);
/* 732 */     this.title = null;
/*     */ 
/* 734 */     for (Iterator i = iterator(); i.hasNext(); ) {
/* 735 */       Element element = (Element)i.next();
/* 736 */       if ((element instanceof Section)) {
/* 737 */         Section s = (Section)element;
/* 738 */         if ((!s.isComplete()) && (size() == 1)) {
/* 739 */           s.flushContent();
/* 740 */           return;
/*     */         }
/*     */ 
/* 743 */         s.setAddedCompletely(true);
/*     */       }
/*     */ 
/* 746 */       i.remove();
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isComplete()
/*     */   {
/* 755 */     return this.complete;
/*     */   }
/*     */ 
/*     */   public void setComplete(boolean complete)
/*     */   {
/* 763 */     this.complete = complete;
/*     */   }
/*     */ 
/*     */   public void newPage()
/*     */   {
/* 771 */     add(Chunk.NEXTPAGE);
/*     */   }
/*     */ 
/*     */   public PdfObject getAccessibleAttribute(PdfName key) {
/* 775 */     return this.title.getAccessibleAttribute(key);
/*     */   }
/*     */ 
/*     */   public void setAccessibleAttribute(PdfName key, PdfObject value) {
/* 779 */     this.title.setAccessibleAttribute(key, value);
/*     */   }
/*     */ 
/*     */   public HashMap<PdfName, PdfObject> getAccessibleAttributes() {
/* 783 */     return this.title.getAccessibleAttributes();
/*     */   }
/*     */ 
/*     */   public PdfName getRole() {
/* 787 */     return this.title.getRole();
/*     */   }
/*     */ 
/*     */   public void setRole(PdfName role) {
/* 791 */     this.title.setRole(role);
/*     */   }
/*     */ 
/*     */   public AccessibleElementId getId() {
/* 795 */     return this.title.getId();
/*     */   }
/*     */ 
/*     */   public void setId(AccessibleElementId id) {
/* 799 */     this.title.setId(id);
/*     */   }
/*     */ 
/*     */   public boolean isInline() {
/* 803 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.Section
 * JD-Core Version:    0.6.2
 */