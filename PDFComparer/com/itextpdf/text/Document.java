/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.pdf.PdfName;
/*     */ import com.itextpdf.text.pdf.PdfObject;
/*     */ import com.itextpdf.text.pdf.interfaces.IAccessibleElement;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class Document
/*     */   implements DocListener, IAccessibleElement
/*     */ {
/* 105 */   public static boolean compress = true;
/*     */ 
/* 111 */   public static boolean plainRandomAccess = false;
/*     */ 
/* 114 */   public static float wmfFontCorrection = 0.86F;
/*     */ 
/* 120 */   protected ArrayList<DocListener> listeners = new ArrayList();
/*     */   protected boolean open;
/*     */   protected boolean close;
/*     */   protected Rectangle pageSize;
/* 134 */   protected float marginLeft = 0.0F;
/*     */ 
/* 137 */   protected float marginRight = 0.0F;
/*     */ 
/* 140 */   protected float marginTop = 0.0F;
/*     */ 
/* 143 */   protected float marginBottom = 0.0F;
/*     */ 
/* 146 */   protected boolean marginMirroring = false;
/*     */ 
/* 152 */   protected boolean marginMirroringTopBottom = false;
/*     */ 
/* 155 */   protected String javaScript_onLoad = null;
/*     */ 
/* 158 */   protected String javaScript_onUnLoad = null;
/*     */ 
/* 161 */   protected String htmlStyleClass = null;
/*     */ 
/* 166 */   protected int pageN = 0;
/*     */ 
/* 169 */   protected int chapternumber = 0;
/*     */ 
/* 171 */   protected PdfName role = PdfName.DOCUMENT;
/* 172 */   protected HashMap<PdfName, PdfObject> accessibleAttributes = null;
/* 173 */   protected AccessibleElementId id = new AccessibleElementId();
/*     */ 
/*     */   public Document()
/*     */   {
/* 182 */     this(PageSize.A4);
/*     */   }
/*     */ 
/*     */   public Document(Rectangle pageSize)
/*     */   {
/* 193 */     this(pageSize, 36.0F, 36.0F, 36.0F, 36.0F);
/*     */   }
/*     */ 
/*     */   public Document(Rectangle pageSize, float marginLeft, float marginRight, float marginTop, float marginBottom)
/*     */   {
/* 213 */     this.pageSize = pageSize;
/* 214 */     this.marginLeft = marginLeft;
/* 215 */     this.marginRight = marginRight;
/* 216 */     this.marginTop = marginTop;
/* 217 */     this.marginBottom = marginBottom;
/*     */   }
/*     */ 
/*     */   public void addDocListener(DocListener listener)
/*     */   {
/* 230 */     this.listeners.add(listener);
/*     */     IAccessibleElement ae;
/* 231 */     if ((listener instanceof IAccessibleElement)) {
/* 232 */       ae = (IAccessibleElement)listener;
/* 233 */       ae.setRole(this.role);
/* 234 */       ae.setId(this.id);
/* 235 */       if (this.accessibleAttributes != null)
/* 236 */         for (PdfName key : this.accessibleAttributes.keySet())
/* 237 */           ae.setAccessibleAttribute(key, (PdfObject)this.accessibleAttributes.get(key));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void removeDocListener(DocListener listener)
/*     */   {
/* 250 */     this.listeners.remove(listener);
/*     */   }
/*     */ 
/*     */   public boolean add(Element element)
/*     */     throws DocumentException
/*     */   {
/* 267 */     if (this.close) {
/* 268 */       throw new DocumentException(MessageLocalization.getComposedMessage("the.document.has.been.closed.you.can.t.add.any.elements", new Object[0]));
/*     */     }
/* 270 */     if ((!this.open) && (element.isContent())) {
/* 271 */       throw new DocumentException(MessageLocalization.getComposedMessage("the.document.is.not.open.yet.you.can.only.add.meta.information", new Object[0]));
/*     */     }
/* 273 */     boolean success = false;
/* 274 */     if ((element instanceof ChapterAutoNumber)) {
/* 275 */       this.chapternumber = ((ChapterAutoNumber)element).setAutomaticNumber(this.chapternumber);
/*     */     }
/* 277 */     for (DocListener listener : this.listeners) {
/* 278 */       success |= listener.add(element);
/*     */     }
/* 280 */     if ((element instanceof LargeElement)) {
/* 281 */       LargeElement e = (LargeElement)element;
/* 282 */       if (!e.isComplete())
/* 283 */         e.flushContent();
/*     */     }
/* 285 */     return success;
/*     */   }
/*     */ 
/*     */   public void open()
/*     */   {
/* 297 */     if (!this.close) {
/* 298 */       this.open = true;
/*     */     }
/* 300 */     for (DocListener listener : this.listeners) {
/* 301 */       listener.setPageSize(this.pageSize);
/* 302 */       listener.setMargins(this.marginLeft, this.marginRight, this.marginTop, this.marginBottom);
/*     */ 
/* 304 */       listener.open();
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean setPageSize(Rectangle pageSize)
/*     */   {
/* 317 */     this.pageSize = pageSize;
/* 318 */     for (DocListener listener : this.listeners) {
/* 319 */       listener.setPageSize(pageSize);
/*     */     }
/* 321 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean setMargins(float marginLeft, float marginRight, float marginTop, float marginBottom)
/*     */   {
/* 340 */     this.marginLeft = marginLeft;
/* 341 */     this.marginRight = marginRight;
/* 342 */     this.marginTop = marginTop;
/* 343 */     this.marginBottom = marginBottom;
/* 344 */     for (DocListener listener : this.listeners) {
/* 345 */       listener.setMargins(marginLeft, marginRight, marginTop, marginBottom);
/*     */     }
/*     */ 
/* 348 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean newPage()
/*     */   {
/* 359 */     if ((!this.open) || (this.close)) {
/* 360 */       return false;
/*     */     }
/* 362 */     for (DocListener listener : this.listeners) {
/* 363 */       listener.newPage();
/*     */     }
/* 365 */     return true;
/*     */   }
/*     */ 
/*     */   public void resetPageCount()
/*     */   {
/* 373 */     this.pageN = 0;
/* 374 */     for (DocListener listener : this.listeners)
/* 375 */       listener.resetPageCount();
/*     */   }
/*     */ 
/*     */   public void setPageCount(int pageN)
/*     */   {
/* 387 */     this.pageN = pageN;
/* 388 */     for (DocListener listener : this.listeners)
/* 389 */       listener.setPageCount(pageN);
/*     */   }
/*     */ 
/*     */   public int getPageNumber()
/*     */   {
/* 400 */     return this.pageN;
/*     */   }
/*     */ 
/*     */   public void close()
/*     */   {
/* 411 */     if (!this.close) {
/* 412 */       this.open = false;
/* 413 */       this.close = true;
/*     */     }
/* 415 */     for (DocListener listener : this.listeners)
/* 416 */       listener.close();
/*     */   }
/*     */ 
/*     */   public boolean addHeader(String name, String content)
/*     */   {
/*     */     try
/*     */     {
/* 434 */       return add(new Header(name, content));
/*     */     } catch (DocumentException de) {
/* 436 */       throw new ExceptionConverter(de);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean addTitle(String title)
/*     */   {
/*     */     try
/*     */     {
/* 450 */       return add(new Meta(1, title));
/*     */     } catch (DocumentException de) {
/* 452 */       throw new ExceptionConverter(de);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean addSubject(String subject)
/*     */   {
/*     */     try
/*     */     {
/* 466 */       return add(new Meta(2, subject));
/*     */     } catch (DocumentException de) {
/* 468 */       throw new ExceptionConverter(de);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean addKeywords(String keywords)
/*     */   {
/*     */     try
/*     */     {
/* 482 */       return add(new Meta(3, keywords));
/*     */     } catch (DocumentException de) {
/* 484 */       throw new ExceptionConverter(de);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean addAuthor(String author)
/*     */   {
/*     */     try
/*     */     {
/* 498 */       return add(new Meta(4, author));
/*     */     } catch (DocumentException de) {
/* 500 */       throw new ExceptionConverter(de);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean addCreator(String creator)
/*     */   {
/*     */     try
/*     */     {
/* 514 */       return add(new Meta(7, creator));
/*     */     } catch (DocumentException de) {
/* 516 */       throw new ExceptionConverter(de);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean addProducer()
/*     */   {
/*     */     try
/*     */     {
/* 528 */       return add(new Meta(5, Version.getInstance().getVersion()));
/*     */     } catch (DocumentException de) {
/* 530 */       throw new ExceptionConverter(de);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean addLanguage(String language)
/*     */   {
/*     */     try
/*     */     {
/* 541 */       return add(new Meta(8, language));
/*     */     } catch (DocumentException de) {
/* 543 */       throw new ExceptionConverter(de);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean addCreationDate()
/*     */   {
/*     */     try
/*     */     {
/* 556 */       SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
/*     */ 
/* 558 */       return add(new Meta(6, sdf.format(new Date())));
/*     */     } catch (DocumentException de) {
/* 560 */       throw new ExceptionConverter(de);
/*     */     }
/*     */   }
/*     */ 
/*     */   public float leftMargin()
/*     */   {
/* 573 */     return this.marginLeft;
/*     */   }
/*     */ 
/*     */   public float rightMargin()
/*     */   {
/* 583 */     return this.marginRight;
/*     */   }
/*     */ 
/*     */   public float topMargin()
/*     */   {
/* 593 */     return this.marginTop;
/*     */   }
/*     */ 
/*     */   public float bottomMargin()
/*     */   {
/* 603 */     return this.marginBottom;
/*     */   }
/*     */ 
/*     */   public float left()
/*     */   {
/* 613 */     return this.pageSize.getLeft(this.marginLeft);
/*     */   }
/*     */ 
/*     */   public float right()
/*     */   {
/* 623 */     return this.pageSize.getRight(this.marginRight);
/*     */   }
/*     */ 
/*     */   public float top()
/*     */   {
/* 633 */     return this.pageSize.getTop(this.marginTop);
/*     */   }
/*     */ 
/*     */   public float bottom()
/*     */   {
/* 643 */     return this.pageSize.getBottom(this.marginBottom);
/*     */   }
/*     */ 
/*     */   public float left(float margin)
/*     */   {
/* 655 */     return this.pageSize.getLeft(this.marginLeft + margin);
/*     */   }
/*     */ 
/*     */   public float right(float margin)
/*     */   {
/* 667 */     return this.pageSize.getRight(this.marginRight + margin);
/*     */   }
/*     */ 
/*     */   public float top(float margin)
/*     */   {
/* 679 */     return this.pageSize.getTop(this.marginTop + margin);
/*     */   }
/*     */ 
/*     */   public float bottom(float margin)
/*     */   {
/* 691 */     return this.pageSize.getBottom(this.marginBottom + margin);
/*     */   }
/*     */ 
/*     */   public Rectangle getPageSize()
/*     */   {
/* 701 */     return this.pageSize;
/*     */   }
/*     */ 
/*     */   public boolean isOpen()
/*     */   {
/* 710 */     return this.open;
/*     */   }
/*     */ 
/*     */   public void setJavaScript_onLoad(String code)
/*     */   {
/* 721 */     this.javaScript_onLoad = code;
/*     */   }
/*     */ 
/*     */   public String getJavaScript_onLoad()
/*     */   {
/* 731 */     return this.javaScript_onLoad;
/*     */   }
/*     */ 
/*     */   public void setJavaScript_onUnLoad(String code)
/*     */   {
/* 742 */     this.javaScript_onUnLoad = code;
/*     */   }
/*     */ 
/*     */   public String getJavaScript_onUnLoad()
/*     */   {
/* 752 */     return this.javaScript_onUnLoad;
/*     */   }
/*     */ 
/*     */   public void setHtmlStyleClass(String htmlStyleClass)
/*     */   {
/* 763 */     this.htmlStyleClass = htmlStyleClass;
/*     */   }
/*     */ 
/*     */   public String getHtmlStyleClass()
/*     */   {
/* 773 */     return this.htmlStyleClass;
/*     */   }
/*     */ 
/*     */   public boolean setMarginMirroring(boolean marginMirroring)
/*     */   {
/* 784 */     this.marginMirroring = marginMirroring;
/*     */ 
/* 786 */     for (Object element : this.listeners) {
/* 787 */       DocListener listener = (IAccessibleElement)element;
/* 788 */       listener.setMarginMirroring(marginMirroring);
/*     */     }
/* 790 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean setMarginMirroringTopBottom(boolean marginMirroringTopBottom)
/*     */   {
/* 802 */     this.marginMirroringTopBottom = marginMirroringTopBottom;
/*     */ 
/* 804 */     for (Object element : this.listeners) {
/* 805 */       DocListener listener = (IAccessibleElement)element;
/* 806 */       listener.setMarginMirroringTopBottom(marginMirroringTopBottom);
/*     */     }
/* 808 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isMarginMirroring()
/*     */   {
/* 817 */     return this.marginMirroring;
/*     */   }
/*     */ 
/*     */   public PdfObject getAccessibleAttribute(PdfName key) {
/* 821 */     if (this.accessibleAttributes != null) {
/* 822 */       return (PdfObject)this.accessibleAttributes.get(key);
/*     */     }
/* 824 */     return null;
/*     */   }
/*     */ 
/*     */   public void setAccessibleAttribute(PdfName key, PdfObject value) {
/* 828 */     if (this.accessibleAttributes == null)
/* 829 */       this.accessibleAttributes = new HashMap();
/* 830 */     this.accessibleAttributes.put(key, value);
/*     */   }
/*     */ 
/*     */   public HashMap<PdfName, PdfObject> getAccessibleAttributes() {
/* 834 */     return this.accessibleAttributes;
/*     */   }
/*     */ 
/*     */   public PdfName getRole() {
/* 838 */     return this.role;
/*     */   }
/*     */ 
/*     */   public void setRole(PdfName role) {
/* 842 */     this.role = role;
/*     */   }
/*     */ 
/*     */   public AccessibleElementId getId() {
/* 846 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(AccessibleElementId id) {
/* 850 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public boolean isInline() {
/* 854 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.Document
 * JD-Core Version:    0.6.2
 */