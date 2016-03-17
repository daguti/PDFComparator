/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import com.itextpdf.text.api.Indentable;
/*     */ import com.itextpdf.text.api.Spaceable;
/*     */ import com.itextpdf.text.pdf.PdfName;
/*     */ import com.itextpdf.text.pdf.PdfObject;
/*     */ import com.itextpdf.text.pdf.PdfPTable;
/*     */ import com.itextpdf.text.pdf.interfaces.IAccessibleElement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class Paragraph extends Phrase
/*     */   implements Indentable, Spaceable, IAccessibleElement
/*     */ {
/*     */   private static final long serialVersionUID = 7852314969733375514L;
/*  84 */   protected int alignment = -1;
/*     */   protected float indentationLeft;
/*     */   protected float indentationRight;
/*  93 */   private float firstLineIndent = 0.0F;
/*     */   protected float spacingBefore;
/*     */   protected float spacingAfter;
/* 102 */   private float extraParagraphSpace = 0.0F;
/*     */ 
/* 105 */   protected boolean keeptogether = false;
/*     */ 
/* 107 */   protected PdfName role = PdfName.P;
/* 108 */   protected HashMap<PdfName, PdfObject> accessibleAttributes = null;
/* 109 */   private AccessibleElementId id = null;
/*     */ 
/*     */   public Paragraph()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Paragraph(float leading)
/*     */   {
/* 126 */     super(leading);
/*     */   }
/*     */ 
/*     */   public Paragraph(Chunk chunk)
/*     */   {
/* 135 */     super(chunk);
/*     */   }
/*     */ 
/*     */   public Paragraph(float leading, Chunk chunk)
/*     */   {
/* 146 */     super(leading, chunk);
/*     */   }
/*     */ 
/*     */   public Paragraph(String string)
/*     */   {
/* 155 */     super(string);
/*     */   }
/*     */ 
/*     */   public Paragraph(String string, Font font)
/*     */   {
/* 166 */     super(string, font);
/*     */   }
/*     */ 
/*     */   public Paragraph(float leading, String string)
/*     */   {
/* 177 */     super(leading, string);
/*     */   }
/*     */ 
/*     */   public Paragraph(float leading, String string, Font font)
/*     */   {
/* 189 */     super(leading, string, font);
/*     */   }
/*     */ 
/*     */   public Paragraph(Phrase phrase)
/*     */   {
/* 198 */     super(phrase);
/* 199 */     if ((phrase instanceof Paragraph)) {
/* 200 */       Paragraph p = (Paragraph)phrase;
/* 201 */       setAlignment(p.alignment);
/* 202 */       setIndentationLeft(p.getIndentationLeft());
/* 203 */       setIndentationRight(p.getIndentationRight());
/* 204 */       setFirstLineIndent(p.getFirstLineIndent());
/* 205 */       setSpacingAfter(p.getSpacingAfter());
/* 206 */       setSpacingBefore(p.getSpacingBefore());
/* 207 */       setExtraParagraphSpace(p.getExtraParagraphSpace());
/* 208 */       setRole(p.role);
/* 209 */       this.id = p.getId();
/* 210 */       if (p.accessibleAttributes != null)
/* 211 */         this.accessibleAttributes = new HashMap(p.accessibleAttributes);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Paragraph cloneShallow(boolean spacingBefore)
/*     */   {
/* 220 */     Paragraph copy = new Paragraph();
/* 221 */     copy.setFont(getFont());
/* 222 */     copy.setAlignment(getAlignment());
/* 223 */     copy.setLeading(getLeading(), this.multipliedLeading);
/* 224 */     copy.setIndentationLeft(getIndentationLeft());
/* 225 */     copy.setIndentationRight(getIndentationRight());
/* 226 */     copy.setFirstLineIndent(getFirstLineIndent());
/* 227 */     copy.setSpacingAfter(getSpacingAfter());
/* 228 */     if (spacingBefore)
/* 229 */       copy.setSpacingBefore(getSpacingBefore());
/* 230 */     copy.setExtraParagraphSpace(getExtraParagraphSpace());
/* 231 */     copy.setRole(this.role);
/* 232 */     copy.id = getId();
/* 233 */     if (this.accessibleAttributes != null)
/* 234 */       copy.accessibleAttributes = new HashMap(this.accessibleAttributes);
/* 235 */     copy.setTabSettings(getTabSettings());
/* 236 */     copy.setKeepTogether(getKeepTogether());
/* 237 */     return copy;
/*     */   }
/*     */ 
/*     */   public java.util.List<Element> breakUp()
/*     */   {
/* 245 */     java.util.List list = new ArrayList();
/* 246 */     Paragraph tmp = null;
/* 247 */     for (Element e : this) {
/* 248 */       if ((e.type() == 14) || (e.type() == 23) || (e.type() == 12)) {
/* 249 */         if ((tmp != null) && (tmp.size() > 0)) {
/* 250 */           tmp.setSpacingAfter(0.0F);
/* 251 */           list.add(tmp);
/* 252 */           tmp = cloneShallow(false);
/*     */         }
/* 254 */         if (list.size() == 0) {
/* 255 */           switch (e.type()) {
/*     */           case 23:
/* 257 */             ((PdfPTable)e).setSpacingBefore(getSpacingBefore());
/* 258 */             break;
/*     */           case 12:
/* 260 */             ((Paragraph)e).setSpacingBefore(getSpacingBefore());
/* 261 */             break;
/*     */           case 14:
/* 263 */             ListItem firstItem = ((List)e).getFirstItem();
/* 264 */             if (firstItem != null)
/* 265 */               firstItem.setSpacingBefore(getSpacingBefore()); break;
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 272 */         list.add(e);
/*     */       }
/*     */       else {
/* 275 */         if (tmp == null) {
/* 276 */           tmp = cloneShallow(list.size() == 0);
/*     */         }
/* 278 */         tmp.add(e);
/*     */       }
/*     */     }
/* 281 */     if ((tmp != null) && (tmp.size() > 0)) {
/* 282 */       list.add(tmp);
/*     */     }
/* 284 */     if (list.size() != 0) {
/* 285 */       Element lastElement = (Element)list.get(list.size() - 1);
/* 286 */       switch (lastElement.type()) {
/*     */       case 23:
/* 288 */         ((PdfPTable)lastElement).setSpacingAfter(getSpacingAfter());
/* 289 */         break;
/*     */       case 12:
/* 291 */         ((Paragraph)lastElement).setSpacingAfter(getSpacingAfter());
/* 292 */         break;
/*     */       case 14:
/* 294 */         ListItem lastItem = ((List)lastElement).getLastItem();
/* 295 */         if (lastItem != null)
/* 296 */           lastItem.setSpacingAfter(getSpacingAfter()); break;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 303 */     return list;
/*     */   }
/*     */ 
/*     */   public int type()
/*     */   {
/* 315 */     return 12;
/*     */   }
/*     */ 
/*     */   public boolean add(Element o)
/*     */   {
/* 328 */     if ((o instanceof List)) {
/* 329 */       List list = (List)o;
/* 330 */       list.setIndentationLeft(list.getIndentationLeft() + this.indentationLeft);
/* 331 */       list.setIndentationRight(this.indentationRight);
/* 332 */       return super.add(list);
/*     */     }
/* 334 */     if ((o instanceof Image)) {
/* 335 */       super.addSpecial(o);
/* 336 */       return true;
/*     */     }
/* 338 */     if ((o instanceof Paragraph)) {
/* 339 */       super.addSpecial(o);
/* 340 */       return true;
/*     */     }
/* 342 */     return super.add(o);
/*     */   }
/*     */ 
/*     */   public void setAlignment(int alignment)
/*     */   {
/* 353 */     this.alignment = alignment;
/*     */   }
/*     */ 
/*     */   public void setIndentationLeft(float indentation)
/*     */   {
/* 360 */     this.indentationLeft = indentation;
/*     */   }
/*     */ 
/*     */   public void setIndentationRight(float indentation)
/*     */   {
/* 367 */     this.indentationRight = indentation;
/*     */   }
/*     */ 
/*     */   public void setFirstLineIndent(float firstLineIndent)
/*     */   {
/* 375 */     this.firstLineIndent = firstLineIndent;
/*     */   }
/*     */ 
/*     */   public void setSpacingBefore(float spacing)
/*     */   {
/* 382 */     this.spacingBefore = spacing;
/*     */   }
/*     */ 
/*     */   public void setSpacingAfter(float spacing)
/*     */   {
/* 389 */     this.spacingAfter = spacing;
/*     */   }
/*     */ 
/*     */   public void setKeepTogether(boolean keeptogether)
/*     */   {
/* 398 */     this.keeptogether = keeptogether;
/*     */   }
/*     */ 
/*     */   public boolean getKeepTogether()
/*     */   {
/* 407 */     return this.keeptogether;
/*     */   }
/*     */ 
/*     */   public int getAlignment()
/*     */   {
/* 418 */     return this.alignment;
/*     */   }
/*     */ 
/*     */   public float getIndentationLeft()
/*     */   {
/* 425 */     return this.indentationLeft;
/*     */   }
/*     */ 
/*     */   public float getIndentationRight()
/*     */   {
/* 432 */     return this.indentationRight;
/*     */   }
/*     */ 
/*     */   public float getFirstLineIndent()
/*     */   {
/* 440 */     return this.firstLineIndent;
/*     */   }
/*     */ 
/*     */   public float getSpacingBefore()
/*     */   {
/* 447 */     return this.spacingBefore;
/*     */   }
/*     */ 
/*     */   public float getSpacingAfter()
/*     */   {
/* 454 */     return this.spacingAfter;
/*     */   }
/*     */ 
/*     */   public float getExtraParagraphSpace()
/*     */   {
/* 462 */     return this.extraParagraphSpace;
/*     */   }
/*     */ 
/*     */   public void setExtraParagraphSpace(float extraParagraphSpace)
/*     */   {
/* 470 */     this.extraParagraphSpace = extraParagraphSpace;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public float spacingBefore()
/*     */   {
/* 484 */     return getSpacingBefore();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public float spacingAfter()
/*     */   {
/* 496 */     return this.spacingAfter;
/*     */   }
/*     */ 
/*     */   public PdfObject getAccessibleAttribute(PdfName key) {
/* 500 */     if (this.accessibleAttributes != null) {
/* 501 */       return (PdfObject)this.accessibleAttributes.get(key);
/*     */     }
/* 503 */     return null;
/*     */   }
/*     */ 
/*     */   public void setAccessibleAttribute(PdfName key, PdfObject value) {
/* 507 */     if (this.accessibleAttributes == null)
/* 508 */       this.accessibleAttributes = new HashMap();
/* 509 */     this.accessibleAttributes.put(key, value);
/*     */   }
/*     */ 
/*     */   public HashMap<PdfName, PdfObject> getAccessibleAttributes() {
/* 513 */     return this.accessibleAttributes;
/*     */   }
/*     */ 
/*     */   public PdfName getRole() {
/* 517 */     return this.role;
/*     */   }
/*     */ 
/*     */   public void setRole(PdfName role) {
/* 521 */     this.role = role;
/*     */   }
/*     */ 
/*     */   public AccessibleElementId getId() {
/* 525 */     if (this.id == null)
/* 526 */       this.id = new AccessibleElementId();
/* 527 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(AccessibleElementId id) {
/* 531 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public boolean isInline() {
/* 535 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.Paragraph
 * JD-Core Version:    0.6.2
 */