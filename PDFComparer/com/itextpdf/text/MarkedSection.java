/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import com.itextpdf.text.api.Indentable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public class MarkedSection extends MarkedObject
/*     */   implements Indentable
/*     */ {
/*  63 */   protected MarkedObject title = null;
/*     */ 
/*     */   public MarkedSection(Section section)
/*     */   {
/*  71 */     if (section.title != null) {
/*  72 */       this.title = new MarkedObject(section.title);
/*  73 */       section.setTitle(null);
/*     */     }
/*  75 */     this.element = section;
/*     */   }
/*     */ 
/*     */   public void add(int index, Element o)
/*     */   {
/*  88 */     ((Section)this.element).add(index, o);
/*     */   }
/*     */ 
/*     */   public boolean add(Element o)
/*     */   {
/* 101 */     return ((Section)this.element).add(o);
/*     */   }
/*     */ 
/*     */   public boolean process(ElementListener listener)
/*     */   {
/*     */     try
/*     */     {
/* 115 */       for (Iterator i = ((Section)this.element).iterator(); i.hasNext(); ) {
/* 116 */         Element element = (Element)i.next();
/* 117 */         listener.add(element);
/*     */       }
/* 119 */       return true;
/*     */     } catch (DocumentException de) {
/*     */     }
/* 122 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean addAll(Collection<? extends Element> collection)
/*     */   {
/* 135 */     return ((Section)this.element).addAll(collection);
/*     */   }
/*     */ 
/*     */   public MarkedSection addSection(float indentation, int numberDepth)
/*     */   {
/* 146 */     MarkedSection section = ((Section)this.element).addMarkedSection();
/* 147 */     section.setIndentation(indentation);
/* 148 */     section.setNumberDepth(numberDepth);
/* 149 */     return section;
/*     */   }
/*     */ 
/*     */   public MarkedSection addSection(float indentation)
/*     */   {
/* 159 */     MarkedSection section = ((Section)this.element).addMarkedSection();
/* 160 */     section.setIndentation(indentation);
/* 161 */     return section;
/*     */   }
/*     */ 
/*     */   public MarkedSection addSection(int numberDepth)
/*     */   {
/* 171 */     MarkedSection section = ((Section)this.element).addMarkedSection();
/* 172 */     section.setNumberDepth(numberDepth);
/* 173 */     return section;
/*     */   }
/*     */ 
/*     */   public MarkedSection addSection()
/*     */   {
/* 182 */     return ((Section)this.element).addMarkedSection();
/*     */   }
/*     */ 
/*     */   public void setTitle(MarkedObject title)
/*     */   {
/* 193 */     if ((title.element instanceof Paragraph))
/* 194 */       this.title = title;
/*     */   }
/*     */ 
/*     */   public MarkedObject getTitle()
/*     */   {
/* 203 */     Paragraph result = Section.constructTitle((Paragraph)this.title.element, ((Section)this.element).numbers, ((Section)this.element).numberDepth, ((Section)this.element).numberStyle);
/* 204 */     MarkedObject mo = new MarkedObject(result);
/* 205 */     mo.markupAttributes = this.title.markupAttributes;
/* 206 */     return mo;
/*     */   }
/*     */ 
/*     */   public void setNumberDepth(int numberDepth)
/*     */   {
/* 219 */     ((Section)this.element).setNumberDepth(numberDepth);
/*     */   }
/*     */ 
/*     */   public void setIndentationLeft(float indentation)
/*     */   {
/* 228 */     ((Section)this.element).setIndentationLeft(indentation);
/*     */   }
/*     */ 
/*     */   public void setIndentationRight(float indentation)
/*     */   {
/* 237 */     ((Section)this.element).setIndentationRight(indentation);
/*     */   }
/*     */ 
/*     */   public void setIndentation(float indentation)
/*     */   {
/* 246 */     ((Section)this.element).setIndentation(indentation);
/*     */   }
/*     */ 
/*     */   public void setBookmarkOpen(boolean bookmarkOpen)
/*     */   {
/* 254 */     ((Section)this.element).setBookmarkOpen(bookmarkOpen);
/*     */   }
/*     */ 
/*     */   public void setTriggerNewPage(boolean triggerNewPage)
/*     */   {
/* 262 */     ((Section)this.element).setTriggerNewPage(triggerNewPage);
/*     */   }
/*     */ 
/*     */   public void setBookmarkTitle(String bookmarkTitle)
/*     */   {
/* 271 */     ((Section)this.element).setBookmarkTitle(bookmarkTitle);
/*     */   }
/*     */ 
/*     */   public void newPage()
/*     */   {
/* 279 */     ((Section)this.element).newPage();
/*     */   }
/*     */ 
/*     */   public float getIndentationLeft()
/*     */   {
/* 286 */     return ((Section)this.element).getIndentationLeft();
/*     */   }
/*     */ 
/*     */   public float getIndentationRight()
/*     */   {
/* 293 */     return ((Section)this.element).getIndentationRight();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.MarkedSection
 * JD-Core Version:    0.6.2
 */