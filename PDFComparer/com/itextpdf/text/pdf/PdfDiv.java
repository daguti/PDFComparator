/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.awt.geom.AffineTransform;
/*     */ import com.itextpdf.text.AccessibleElementId;
/*     */ import com.itextpdf.text.BaseColor;
/*     */ import com.itextpdf.text.Chunk;
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import com.itextpdf.text.Element;
/*     */ import com.itextpdf.text.ElementListener;
/*     */ import com.itextpdf.text.Rectangle;
/*     */ import com.itextpdf.text.api.Spaceable;
/*     */ import com.itextpdf.text.pdf.interfaces.IAccessibleElement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ 
/*     */ public class PdfDiv
/*     */   implements Element, Spaceable, IAccessibleElement
/*     */ {
/*     */   private ArrayList<Element> content;
/*  66 */   private Float left = null;
/*     */ 
/*  68 */   private Float top = null;
/*     */ 
/*  70 */   private Float right = null;
/*     */ 
/*  72 */   private Float bottom = null;
/*     */ 
/*  74 */   private Float width = null;
/*     */ 
/*  76 */   private Float height = null;
/*     */ 
/*  78 */   private Float percentageHeight = null;
/*     */ 
/*  80 */   private Float percentageWidth = null;
/*     */ 
/*  82 */   private float contentWidth = 0.0F;
/*     */ 
/*  84 */   private float contentHeight = 0.0F;
/*     */ 
/*  86 */   private int textAlignment = -1;
/*     */ 
/*  88 */   private float paddingLeft = 0.0F;
/*     */ 
/*  90 */   private float paddingRight = 0.0F;
/*     */ 
/*  92 */   private float paddingTop = 0.0F;
/*     */ 
/*  94 */   private float paddingBottom = 0.0F;
/*     */ 
/*  96 */   private FloatType floatType = FloatType.NONE;
/*     */ 
/*  98 */   private PositionType position = PositionType.STATIC;
/*     */ 
/* 100 */   private FloatLayout floatLayout = null;
/*     */   private float yLine;
/* 104 */   protected PdfName role = PdfName.DIV;
/* 105 */   protected HashMap<PdfName, PdfObject> accessibleAttributes = null;
/* 106 */   protected AccessibleElementId id = new AccessibleElementId();
/*     */ 
/* 160 */   private BaseColor backgroundColor = null;
/*     */   protected float spacingBefore;
/*     */   protected float spacingAfter;
/*     */ 
/*     */   public float getContentWidth()
/*     */   {
/* 109 */     return this.contentWidth;
/*     */   }
/*     */ 
/*     */   public void setContentWidth(float contentWidth) {
/* 113 */     this.contentWidth = contentWidth;
/*     */   }
/*     */ 
/*     */   public float getContentHeight() {
/* 117 */     return this.contentHeight;
/*     */   }
/*     */ 
/*     */   public void setContentHeight(float contentHeight) {
/* 121 */     this.contentHeight = contentHeight;
/*     */   }
/*     */ 
/*     */   public float getActualHeight() {
/* 125 */     return (this.height != null) && (this.height.floatValue() >= this.contentHeight) ? this.height.floatValue() : this.contentHeight;
/*     */   }
/*     */ 
/*     */   public float getActualWidth() {
/* 129 */     return (this.width != null) && (this.width.floatValue() >= this.contentWidth) ? this.width.floatValue() : this.contentWidth;
/*     */   }
/*     */ 
/*     */   public Float getPercentageHeight() {
/* 133 */     return this.percentageHeight;
/*     */   }
/*     */ 
/*     */   public void setPercentageHeight(Float percentageHeight) {
/* 137 */     this.percentageHeight = percentageHeight;
/*     */   }
/*     */ 
/*     */   public Float getPercentageWidth() {
/* 141 */     return this.percentageWidth;
/*     */   }
/*     */ 
/*     */   public void setPercentageWidth(Float percentageWidth) {
/* 145 */     this.percentageWidth = percentageWidth;
/*     */   }
/*     */ 
/*     */   public BaseColor getBackgroundColor() {
/* 149 */     return this.backgroundColor;
/*     */   }
/*     */ 
/*     */   public void setBackgroundColor(BaseColor backgroundColor) {
/* 153 */     this.backgroundColor = backgroundColor;
/*     */   }
/*     */ 
/*     */   public float getYLine() {
/* 157 */     return this.yLine;
/*     */   }
/*     */ 
/*     */   public PdfDiv()
/*     */   {
/* 173 */     this.content = new ArrayList();
/*     */   }
/*     */ 
/*     */   public List<Chunk> getChunks()
/*     */   {
/* 182 */     return new ArrayList();
/*     */   }
/*     */ 
/*     */   public int type()
/*     */   {
/* 191 */     return 37;
/*     */   }
/*     */ 
/*     */   public boolean isContent()
/*     */   {
/* 199 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isNestable()
/*     */   {
/* 207 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean process(ElementListener listener)
/*     */   {
/*     */     try
/*     */     {
/* 219 */       return listener.add(this);
/*     */     } catch (DocumentException de) {
/*     */     }
/* 222 */     return false;
/*     */   }
/*     */ 
/*     */   public void setSpacingBefore(float spacing)
/*     */   {
/* 232 */     this.spacingBefore = spacing;
/*     */   }
/*     */ 
/*     */   public void setSpacingAfter(float spacing)
/*     */   {
/* 241 */     this.spacingAfter = spacing;
/*     */   }
/*     */ 
/*     */   public float getSpacingBefore()
/*     */   {
/* 250 */     return this.spacingBefore;
/*     */   }
/*     */ 
/*     */   public float getSpacingAfter()
/*     */   {
/* 259 */     return this.spacingAfter;
/*     */   }
/*     */ 
/*     */   public int getTextAlignment()
/*     */   {
/* 268 */     return this.textAlignment;
/*     */   }
/*     */ 
/*     */   public void setTextAlignment(int textAlignment)
/*     */   {
/* 278 */     this.textAlignment = textAlignment;
/*     */   }
/*     */ 
/*     */   public void addElement(Element element) {
/* 282 */     this.content.add(element);
/*     */   }
/*     */ 
/*     */   public Float getLeft() {
/* 286 */     return this.left;
/*     */   }
/*     */ 
/*     */   public void setLeft(Float left) {
/* 290 */     this.left = left;
/*     */   }
/*     */ 
/*     */   public Float getRight() {
/* 294 */     return this.right;
/*     */   }
/*     */ 
/*     */   public void setRight(Float right) {
/* 298 */     this.right = right;
/*     */   }
/*     */ 
/*     */   public Float getTop() {
/* 302 */     return this.top;
/*     */   }
/*     */ 
/*     */   public void setTop(Float top) {
/* 306 */     this.top = top;
/*     */   }
/*     */ 
/*     */   public Float getBottom() {
/* 310 */     return this.bottom;
/*     */   }
/*     */ 
/*     */   public void setBottom(Float bottom) {
/* 314 */     this.bottom = bottom;
/*     */   }
/*     */ 
/*     */   public Float getWidth() {
/* 318 */     return this.width;
/*     */   }
/*     */ 
/*     */   public void setWidth(Float width) {
/* 322 */     this.width = width;
/*     */   }
/*     */ 
/*     */   public Float getHeight() {
/* 326 */     return this.height;
/*     */   }
/*     */ 
/*     */   public void setHeight(Float height) {
/* 330 */     this.height = height;
/*     */   }
/*     */ 
/*     */   public float getPaddingLeft() {
/* 334 */     return this.paddingLeft;
/*     */   }
/*     */ 
/*     */   public void setPaddingLeft(float paddingLeft) {
/* 338 */     this.paddingLeft = paddingLeft;
/*     */   }
/*     */ 
/*     */   public float getPaddingRight() {
/* 342 */     return this.paddingRight;
/*     */   }
/*     */ 
/*     */   public void setPaddingRight(float paddingRight) {
/* 346 */     this.paddingRight = paddingRight;
/*     */   }
/*     */ 
/*     */   public float getPaddingTop() {
/* 350 */     return this.paddingTop;
/*     */   }
/*     */ 
/*     */   public void setPaddingTop(float paddingTop) {
/* 354 */     this.paddingTop = paddingTop;
/*     */   }
/*     */ 
/*     */   public float getPaddingBottom() {
/* 358 */     return this.paddingBottom;
/*     */   }
/*     */ 
/*     */   public void setPaddingBottom(float paddingBottom) {
/* 362 */     this.paddingBottom = paddingBottom;
/*     */   }
/*     */ 
/*     */   public FloatType getFloatType() {
/* 366 */     return this.floatType;
/*     */   }
/*     */ 
/*     */   public void setFloatType(FloatType floatType) {
/* 370 */     this.floatType = floatType;
/*     */   }
/*     */ 
/*     */   public PositionType getPosition() {
/* 374 */     return this.position;
/*     */   }
/*     */ 
/*     */   public void setPosition(PositionType position) {
/* 378 */     this.position = position;
/*     */   }
/*     */ 
/*     */   public ArrayList<Element> getContent() {
/* 382 */     return this.content;
/*     */   }
/*     */ 
/*     */   public void setContent(ArrayList<Element> content) {
/* 386 */     this.content = content;
/*     */   }
/*     */ 
/*     */   public int layout(PdfContentByte canvas, boolean useAscender, boolean simulate, float llx, float lly, float urx, float ury) throws DocumentException
/*     */   {
/* 391 */     float leftX = Math.min(llx, urx);
/* 392 */     float maxY = Math.max(lly, ury);
/* 393 */     float minY = Math.min(lly, ury);
/* 394 */     float rightX = Math.max(llx, urx);
/* 395 */     this.yLine = maxY;
/* 396 */     boolean contentCutByFixedHeight = false;
/*     */ 
/* 398 */     if ((this.width != null) && (this.width.floatValue() > 0.0F)) {
/* 399 */       if (this.width.floatValue() < rightX - leftX)
/* 400 */         rightX = leftX + this.width.floatValue();
/* 401 */       else if (this.width.floatValue() > rightX - leftX)
/* 402 */         return 2;
/*     */     }
/* 404 */     else if (this.percentageWidth != null) {
/* 405 */       this.contentWidth = ((rightX - leftX) * this.percentageWidth.floatValue());
/* 406 */       rightX = leftX + this.contentWidth;
/*     */     }
/*     */ 
/* 409 */     if ((this.height != null) && (this.height.floatValue() > 0.0F)) {
/* 410 */       if (this.height.floatValue() < maxY - minY) {
/* 411 */         minY = maxY - this.height.floatValue();
/* 412 */         contentCutByFixedHeight = true;
/* 413 */       } else if (this.height.floatValue() > maxY - minY) {
/* 414 */         return 2;
/*     */       }
/* 416 */     } else if (this.percentageHeight != null) {
/* 417 */       if (this.percentageHeight.floatValue() < 1.0D) {
/* 418 */         contentCutByFixedHeight = true;
/*     */       }
/* 420 */       this.contentHeight = ((maxY - minY) * this.percentageHeight.floatValue());
/* 421 */       minY = maxY - this.contentHeight;
/*     */     }
/*     */ 
/* 424 */     if ((!simulate) && (this.position == PositionType.RELATIVE)) {
/* 425 */       Float translationX = null;
/* 426 */       if (this.left != null)
/* 427 */         translationX = this.left;
/* 428 */       else if (this.right != null)
/* 429 */         translationX = Float.valueOf(-this.right.floatValue());
/*     */       else {
/* 431 */         translationX = Float.valueOf(0.0F);
/*     */       }
/*     */ 
/* 434 */       Float translationY = null;
/* 435 */       if (this.top != null)
/* 436 */         translationY = Float.valueOf(-this.top.floatValue());
/* 437 */       else if (this.bottom != null)
/* 438 */         translationY = this.bottom;
/*     */       else {
/* 440 */         translationY = Float.valueOf(0.0F);
/*     */       }
/* 442 */       canvas.saveState();
/* 443 */       canvas.transform(new AffineTransform(1.0F, 0.0F, 0.0F, 1.0F, translationX.floatValue(), translationY.floatValue()));
/*     */     }
/*     */ 
/* 446 */     if ((!simulate) && 
/* 447 */       (this.backgroundColor != null) && (getActualWidth() > 0.0F) && (getActualHeight() > 0.0F)) {
/* 448 */       float backgroundWidth = getActualWidth();
/* 449 */       float backgroundHeight = getActualHeight();
/* 450 */       if (this.width != null) {
/* 451 */         backgroundWidth = this.width.floatValue() > 0.0F ? this.width.floatValue() : 0.0F;
/*     */       }
/*     */ 
/* 454 */       if (this.height != null) {
/* 455 */         backgroundHeight = this.height.floatValue() > 0.0F ? this.height.floatValue() : 0.0F;
/*     */       }
/* 457 */       if ((backgroundWidth > 0.0F) && (backgroundHeight > 0.0F)) {
/* 458 */         Rectangle background = new Rectangle(leftX, maxY - backgroundHeight, leftX + backgroundWidth, maxY);
/* 459 */         background.setBackgroundColor(this.backgroundColor);
/* 460 */         PdfArtifact artifact = new PdfArtifact();
/* 461 */         canvas.openMCBlock(artifact);
/* 462 */         canvas.rectangle(background);
/* 463 */         canvas.closeMCBlock(artifact);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 468 */     if (this.percentageWidth == null) {
/* 469 */       this.contentWidth = 0.0F;
/*     */     }
/* 471 */     if (this.percentageHeight == null) {
/* 472 */       this.contentHeight = 0.0F;
/*     */     }
/*     */ 
/* 475 */     minY += this.paddingBottom;
/* 476 */     leftX += this.paddingLeft;
/* 477 */     rightX -= this.paddingRight;
/*     */ 
/* 479 */     this.yLine -= this.paddingTop;
/*     */ 
/* 481 */     int status = 1;
/*     */ 
/* 483 */     if (!this.content.isEmpty()) {
/* 484 */       if (this.floatLayout == null) {
/* 485 */         ArrayList floatingElements = new ArrayList(this.content);
/* 486 */         this.floatLayout = new FloatLayout(floatingElements, useAscender);
/*     */       }
/*     */ 
/* 489 */       this.floatLayout.setSimpleColumn(leftX, minY, rightX, this.yLine);
/* 490 */       status = this.floatLayout.layout(canvas, simulate);
/* 491 */       this.yLine = this.floatLayout.getYLine();
/* 492 */       if ((this.percentageWidth == null) && (this.contentWidth < this.floatLayout.getFilledWidth())) {
/* 493 */         this.contentWidth = this.floatLayout.getFilledWidth();
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 498 */     if ((!simulate) && (this.position == PositionType.RELATIVE)) {
/* 499 */       canvas.restoreState();
/*     */     }
/*     */ 
/* 502 */     this.yLine -= this.paddingBottom;
/* 503 */     if (this.percentageHeight == null) {
/* 504 */       this.contentHeight = (maxY - this.yLine);
/*     */     }
/*     */ 
/* 507 */     if (this.percentageWidth == null) {
/* 508 */       this.contentWidth += this.paddingLeft + this.paddingRight;
/*     */     }
/*     */ 
/* 511 */     return contentCutByFixedHeight ? 1 : status;
/*     */   }
/*     */ 
/*     */   public PdfObject getAccessibleAttribute(PdfName key) {
/* 515 */     if (this.accessibleAttributes != null) {
/* 516 */       return (PdfObject)this.accessibleAttributes.get(key);
/*     */     }
/* 518 */     return null;
/*     */   }
/*     */ 
/*     */   public void setAccessibleAttribute(PdfName key, PdfObject value) {
/* 522 */     if (this.accessibleAttributes == null)
/* 523 */       this.accessibleAttributes = new HashMap();
/* 524 */     this.accessibleAttributes.put(key, value);
/*     */   }
/*     */ 
/*     */   public HashMap<PdfName, PdfObject> getAccessibleAttributes() {
/* 528 */     return this.accessibleAttributes;
/*     */   }
/*     */ 
/*     */   public PdfName getRole() {
/* 532 */     return this.role;
/*     */   }
/*     */ 
/*     */   public void setRole(PdfName role) {
/* 536 */     this.role = role;
/*     */   }
/*     */ 
/*     */   public AccessibleElementId getId() {
/* 540 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(AccessibleElementId id) {
/* 544 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public boolean isInline() {
/* 548 */     return false;
/*     */   }
/*     */ 
/*     */   public static enum PositionType
/*     */   {
/*  62 */     STATIC, ABSOLUTE, FIXED, RELATIVE;
/*     */   }
/*     */ 
/*     */   public static enum FloatType
/*     */   {
/*  60 */     NONE, LEFT, RIGHT;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfDiv
 * JD-Core Version:    0.6.2
 */