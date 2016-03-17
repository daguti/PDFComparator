/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import com.itextpdf.text.api.Indentable;
/*     */ import com.itextpdf.text.factories.RomanAlphabetFactory;
/*     */ import com.itextpdf.text.pdf.PdfName;
/*     */ import com.itextpdf.text.pdf.PdfObject;
/*     */ import com.itextpdf.text.pdf.interfaces.IAccessibleElement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class List
/*     */   implements TextElementArray, Indentable, IAccessibleElement
/*     */ {
/*     */   public static final boolean ORDERED = true;
/*     */   public static final boolean UNORDERED = false;
/*     */   public static final boolean NUMERICAL = false;
/*     */   public static final boolean ALPHABETICAL = true;
/*     */   public static final boolean UPPERCASE = false;
/*     */   public static final boolean LOWERCASE = true;
/* 121 */   protected ArrayList<Element> list = new ArrayList();
/*     */ 
/* 124 */   protected boolean numbered = false;
/*     */ 
/* 126 */   protected boolean lettered = false;
/*     */ 
/* 128 */   protected boolean lowercase = false;
/*     */ 
/* 130 */   protected boolean autoindent = false;
/*     */ 
/* 132 */   protected boolean alignindent = false;
/*     */ 
/* 135 */   protected int first = 1;
/*     */ 
/* 137 */   protected Chunk symbol = new Chunk("- ");
/*     */ 
/* 142 */   protected String preSymbol = "";
/*     */ 
/* 147 */   protected String postSymbol = ". ";
/*     */ 
/* 150 */   protected float indentationLeft = 0.0F;
/*     */ 
/* 152 */   protected float indentationRight = 0.0F;
/*     */ 
/* 154 */   protected float symbolIndent = 0.0F;
/*     */ 
/* 156 */   protected PdfName role = PdfName.L;
/* 157 */   protected HashMap<PdfName, PdfObject> accessibleAttributes = null;
/* 158 */   private AccessibleElementId id = null;
/*     */ 
/*     */   public List()
/*     */   {
/* 164 */     this(false, false);
/*     */   }
/*     */ 
/*     */   public List(float symbolIndent)
/*     */   {
/* 173 */     this.symbolIndent = symbolIndent;
/*     */   }
/*     */ 
/*     */   public List(boolean numbered)
/*     */   {
/* 181 */     this(numbered, false);
/*     */   }
/*     */ 
/*     */   public List(boolean numbered, boolean lettered)
/*     */   {
/* 190 */     this.numbered = numbered;
/* 191 */     this.lettered = lettered;
/* 192 */     this.autoindent = true;
/* 193 */     this.alignindent = true;
/*     */   }
/*     */ 
/*     */   public List(boolean numbered, float symbolIndent)
/*     */   {
/* 207 */     this(numbered, false, symbolIndent);
/*     */   }
/*     */ 
/*     */   public List(boolean numbered, boolean lettered, float symbolIndent)
/*     */   {
/* 217 */     this.numbered = numbered;
/* 218 */     this.lettered = lettered;
/* 219 */     this.symbolIndent = symbolIndent;
/*     */   }
/*     */ 
/*     */   public boolean process(ElementListener listener)
/*     */   {
/*     */     try
/*     */     {
/* 233 */       for (Element element : this.list) {
/* 234 */         listener.add(element);
/*     */       }
/* 236 */       return true;
/*     */     } catch (DocumentException de) {
/*     */     }
/* 239 */     return false;
/*     */   }
/*     */ 
/*     */   public int type()
/*     */   {
/* 249 */     return 14;
/*     */   }
/*     */ 
/*     */   public java.util.List<Chunk> getChunks()
/*     */   {
/* 258 */     java.util.List tmp = new ArrayList();
/* 259 */     for (Element element : this.list) {
/* 260 */       tmp.addAll(element.getChunks());
/*     */     }
/* 262 */     return tmp;
/*     */   }
/*     */ 
/*     */   public boolean add(String s)
/*     */   {
/* 275 */     if (s != null) {
/* 276 */       return add(new ListItem(s));
/*     */     }
/* 278 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean add(Element o)
/*     */   {
/* 289 */     if ((o instanceof ListItem)) {
/* 290 */       ListItem item = (ListItem)o;
/* 291 */       if ((this.numbered) || (this.lettered)) {
/* 292 */         Chunk chunk = new Chunk(this.preSymbol, this.symbol.getFont());
/* 293 */         chunk.setAttributes(this.symbol.getAttributes());
/* 294 */         int index = this.first + this.list.size();
/* 295 */         if (this.lettered)
/* 296 */           chunk.append(RomanAlphabetFactory.getString(index, this.lowercase));
/*     */         else
/* 298 */           chunk.append(String.valueOf(index));
/* 299 */         chunk.append(this.postSymbol);
/* 300 */         item.setListSymbol(chunk);
/*     */       }
/*     */       else {
/* 303 */         item.setListSymbol(this.symbol);
/*     */       }
/* 305 */       item.setIndentationLeft(this.symbolIndent, this.autoindent);
/* 306 */       item.setIndentationRight(0.0F);
/* 307 */       return this.list.add(item);
/*     */     }
/* 309 */     if ((o instanceof List)) {
/* 310 */       List nested = (List)o;
/* 311 */       nested.setIndentationLeft(nested.getIndentationLeft() + this.symbolIndent);
/* 312 */       this.first -= 1;
/* 313 */       return this.list.add(nested);
/*     */     }
/* 315 */     return false;
/*     */   }
/*     */ 
/*     */   public void normalizeIndentation()
/*     */   {
/* 322 */     float max = 0.0F;
/* 323 */     for (Element o : this.list) {
/* 324 */       if ((o instanceof ListItem)) {
/* 325 */         max = Math.max(max, ((ListItem)o).getIndentationLeft());
/*     */       }
/*     */     }
/* 328 */     for (Element o : this.list)
/* 329 */       if ((o instanceof ListItem))
/* 330 */         ((ListItem)o).setIndentationLeft(max);
/*     */   }
/*     */ 
/*     */   public void setNumbered(boolean numbered)
/*     */   {
/* 341 */     this.numbered = numbered;
/*     */   }
/*     */ 
/*     */   public void setLettered(boolean lettered)
/*     */   {
/* 348 */     this.lettered = lettered;
/*     */   }
/*     */ 
/*     */   public void setLowercase(boolean uppercase)
/*     */   {
/* 355 */     this.lowercase = uppercase;
/*     */   }
/*     */ 
/*     */   public void setAutoindent(boolean autoindent)
/*     */   {
/* 362 */     this.autoindent = autoindent;
/*     */   }
/*     */ 
/*     */   public void setAlignindent(boolean alignindent)
/*     */   {
/* 368 */     this.alignindent = alignindent;
/*     */   }
/*     */ 
/*     */   public void setFirst(int first)
/*     */   {
/* 377 */     this.first = first;
/*     */   }
/*     */ 
/*     */   public void setListSymbol(Chunk symbol)
/*     */   {
/* 386 */     this.symbol = symbol;
/*     */   }
/*     */ 
/*     */   public void setListSymbol(String symbol)
/*     */   {
/* 397 */     this.symbol = new Chunk(symbol);
/*     */   }
/*     */ 
/*     */   public void setIndentationLeft(float indentation)
/*     */   {
/* 406 */     this.indentationLeft = indentation;
/*     */   }
/*     */ 
/*     */   public void setIndentationRight(float indentation)
/*     */   {
/* 415 */     this.indentationRight = indentation;
/*     */   }
/*     */ 
/*     */   public void setSymbolIndent(float symbolIndent)
/*     */   {
/* 422 */     this.symbolIndent = symbolIndent;
/*     */   }
/*     */ 
/*     */   public ArrayList<Element> getItems()
/*     */   {
/* 433 */     return this.list;
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/* 442 */     return this.list.size();
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 451 */     return this.list.isEmpty();
/*     */   }
/*     */ 
/*     */   public float getTotalLeading()
/*     */   {
/* 460 */     if (this.list.size() < 1) {
/* 461 */       return -1.0F;
/*     */     }
/* 463 */     ListItem item = (ListItem)this.list.get(0);
/* 464 */     return item.getTotalLeading();
/*     */   }
/*     */ 
/*     */   public boolean isNumbered()
/*     */   {
/* 475 */     return this.numbered;
/*     */   }
/*     */ 
/*     */   public boolean isLettered()
/*     */   {
/* 483 */     return this.lettered;
/*     */   }
/*     */ 
/*     */   public boolean isLowercase()
/*     */   {
/* 491 */     return this.lowercase;
/*     */   }
/*     */ 
/*     */   public boolean isAutoindent()
/*     */   {
/* 499 */     return this.autoindent;
/*     */   }
/*     */ 
/*     */   public boolean isAlignindent()
/*     */   {
/* 507 */     return this.alignindent;
/*     */   }
/*     */ 
/*     */   public int getFirst()
/*     */   {
/* 515 */     return this.first;
/*     */   }
/*     */ 
/*     */   public Chunk getSymbol()
/*     */   {
/* 523 */     return this.symbol;
/*     */   }
/*     */ 
/*     */   public float getIndentationLeft()
/*     */   {
/* 531 */     return this.indentationLeft;
/*     */   }
/*     */ 
/*     */   public float getIndentationRight()
/*     */   {
/* 539 */     return this.indentationRight;
/*     */   }
/*     */ 
/*     */   public float getSymbolIndent()
/*     */   {
/* 547 */     return this.symbolIndent;
/*     */   }
/*     */ 
/*     */   public boolean isContent()
/*     */   {
/* 554 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isNestable()
/*     */   {
/* 562 */     return true;
/*     */   }
/*     */ 
/*     */   public String getPostSymbol()
/*     */   {
/* 571 */     return this.postSymbol;
/*     */   }
/*     */ 
/*     */   public void setPostSymbol(String postSymbol)
/*     */   {
/* 580 */     this.postSymbol = postSymbol;
/*     */   }
/*     */ 
/*     */   public String getPreSymbol()
/*     */   {
/* 589 */     return this.preSymbol;
/*     */   }
/*     */ 
/*     */   public void setPreSymbol(String preSymbol)
/*     */   {
/* 598 */     this.preSymbol = preSymbol;
/*     */   }
/*     */ 
/*     */   public ListItem getFirstItem() {
/* 602 */     Element lastElement = this.list.size() > 0 ? (Element)this.list.get(0) : null;
/* 603 */     if (lastElement != null) {
/* 604 */       if ((lastElement instanceof ListItem))
/* 605 */         return (ListItem)lastElement;
/* 606 */       if ((lastElement instanceof List)) {
/* 607 */         return ((List)lastElement).getFirstItem();
/*     */       }
/*     */     }
/* 610 */     return null;
/*     */   }
/*     */ 
/*     */   public ListItem getLastItem() {
/* 614 */     Element lastElement = this.list.size() > 0 ? (Element)this.list.get(this.list.size() - 1) : null;
/* 615 */     if (lastElement != null) {
/* 616 */       if ((lastElement instanceof ListItem))
/* 617 */         return (ListItem)lastElement;
/* 618 */       if ((lastElement instanceof List)) {
/* 619 */         return ((List)lastElement).getLastItem();
/*     */       }
/*     */     }
/* 622 */     return null;
/*     */   }
/*     */ 
/*     */   public PdfObject getAccessibleAttribute(PdfName key) {
/* 626 */     if (this.accessibleAttributes != null) {
/* 627 */       return (PdfObject)this.accessibleAttributes.get(key);
/*     */     }
/* 629 */     return null;
/*     */   }
/*     */ 
/*     */   public void setAccessibleAttribute(PdfName key, PdfObject value) {
/* 633 */     if (this.accessibleAttributes == null)
/* 634 */       this.accessibleAttributes = new HashMap();
/* 635 */     this.accessibleAttributes.put(key, value);
/*     */   }
/*     */ 
/*     */   public HashMap<PdfName, PdfObject> getAccessibleAttributes() {
/* 639 */     return this.accessibleAttributes;
/*     */   }
/*     */ 
/*     */   public PdfName getRole() {
/* 643 */     return this.role;
/*     */   }
/*     */ 
/*     */   public void setRole(PdfName role) {
/* 647 */     this.role = role;
/*     */   }
/*     */ 
/*     */   public AccessibleElementId getId() {
/* 651 */     if (this.id == null)
/* 652 */       this.id = new AccessibleElementId();
/* 653 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(AccessibleElementId id) {
/* 657 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public boolean isInline() {
/* 661 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.List
 * JD-Core Version:    0.6.2
 */