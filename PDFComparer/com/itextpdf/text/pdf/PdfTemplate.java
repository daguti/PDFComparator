/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.AccessibleElementId;
/*     */ import com.itextpdf.text.Rectangle;
/*     */ import com.itextpdf.text.pdf.interfaces.IAccessibleElement;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class PdfTemplate extends PdfContentByte
/*     */   implements IAccessibleElement
/*     */ {
/*     */   public static final int TYPE_TEMPLATE = 1;
/*     */   public static final int TYPE_IMPORTED = 2;
/*     */   public static final int TYPE_PATTERN = 3;
/*     */   protected int type;
/*     */   protected PdfIndirectReference thisReference;
/*     */   protected PageResources pageResources;
/*  70 */   protected Rectangle bBox = new Rectangle(0.0F, 0.0F);
/*     */   protected PdfArray matrix;
/*     */   protected PdfTransparencyGroup group;
/*     */   protected PdfOCG layer;
/*     */   protected PdfIndirectReference pageReference;
/*  80 */   protected boolean contentTagged = false;
/*     */ 
/*  86 */   private PdfDictionary additional = null;
/*     */ 
/*  88 */   protected PdfName role = PdfName.FIGURE;
/*  89 */   protected HashMap<PdfName, PdfObject> accessibleAttributes = null;
/*  90 */   private AccessibleElementId id = null;
/*     */ 
/*     */   protected PdfTemplate()
/*     */   {
/*  97 */     super(null);
/*  98 */     this.type = 1;
/*     */   }
/*     */ 
/*     */   PdfTemplate(PdfWriter wr)
/*     */   {
/* 108 */     super(wr);
/* 109 */     this.type = 1;
/* 110 */     this.pageResources = new PageResources();
/* 111 */     this.pageResources.addDefaultColor(wr.getDefaultColorspace());
/* 112 */     this.thisReference = this.writer.getPdfIndirectReference();
/*     */   }
/*     */ 
/*     */   public static PdfTemplate createTemplate(PdfWriter writer, float width, float height)
/*     */   {
/* 129 */     return createTemplate(writer, width, height, null);
/*     */   }
/*     */ 
/*     */   static PdfTemplate createTemplate(PdfWriter writer, float width, float height, PdfName forcedName) {
/* 133 */     PdfTemplate template = new PdfTemplate(writer);
/* 134 */     template.setWidth(width);
/* 135 */     template.setHeight(height);
/* 136 */     writer.addDirectTemplateSimple(template, forcedName);
/* 137 */     return template;
/*     */   }
/*     */ 
/*     */   public boolean isTagged() {
/* 141 */     return (super.isTagged()) && (this.contentTagged);
/*     */   }
/*     */ 
/*     */   public void setWidth(float width)
/*     */   {
/* 151 */     this.bBox.setLeft(0.0F);
/* 152 */     this.bBox.setRight(width);
/*     */   }
/*     */ 
/*     */   public void setHeight(float height)
/*     */   {
/* 162 */     this.bBox.setBottom(0.0F);
/* 163 */     this.bBox.setTop(height);
/*     */   }
/*     */ 
/*     */   public float getWidth()
/*     */   {
/* 172 */     return this.bBox.getWidth();
/*     */   }
/*     */ 
/*     */   public float getHeight()
/*     */   {
/* 182 */     return this.bBox.getHeight();
/*     */   }
/*     */ 
/*     */   public Rectangle getBoundingBox() {
/* 186 */     return this.bBox;
/*     */   }
/*     */ 
/*     */   public void setBoundingBox(Rectangle bBox) {
/* 190 */     this.bBox = bBox;
/*     */   }
/*     */ 
/*     */   public void setLayer(PdfOCG layer)
/*     */   {
/* 198 */     this.layer = layer;
/*     */   }
/*     */ 
/*     */   public PdfOCG getLayer()
/*     */   {
/* 206 */     return this.layer;
/*     */   }
/*     */ 
/*     */   public void setMatrix(float a, float b, float c, float d, float e, float f) {
/* 210 */     this.matrix = new PdfArray();
/* 211 */     this.matrix.add(new PdfNumber(a));
/* 212 */     this.matrix.add(new PdfNumber(b));
/* 213 */     this.matrix.add(new PdfNumber(c));
/* 214 */     this.matrix.add(new PdfNumber(d));
/* 215 */     this.matrix.add(new PdfNumber(e));
/* 216 */     this.matrix.add(new PdfNumber(f));
/*     */   }
/*     */ 
/*     */   PdfArray getMatrix() {
/* 220 */     return this.matrix;
/*     */   }
/*     */ 
/*     */   public PdfIndirectReference getIndirectReference()
/*     */   {
/* 231 */     if (this.thisReference == null) {
/* 232 */       this.thisReference = this.writer.getPdfIndirectReference();
/*     */     }
/* 234 */     return this.thisReference;
/*     */   }
/*     */ 
/*     */   public void beginVariableText() {
/* 238 */     this.content.append("/Tx BMC ");
/*     */   }
/*     */ 
/*     */   public void endVariableText() {
/* 242 */     this.content.append("EMC ");
/*     */   }
/*     */ 
/*     */   PdfObject getResources()
/*     */   {
/* 252 */     return getPageResources().getResources();
/*     */   }
/*     */ 
/*     */   public PdfStream getFormXObject(int compressionLevel)
/*     */     throws IOException
/*     */   {
/* 263 */     return new PdfFormXObject(this, compressionLevel);
/*     */   }
/*     */ 
/*     */   public PdfContentByte getDuplicate()
/*     */   {
/* 273 */     PdfTemplate tpl = new PdfTemplate();
/* 274 */     tpl.writer = this.writer;
/* 275 */     tpl.pdf = this.pdf;
/* 276 */     tpl.thisReference = this.thisReference;
/* 277 */     tpl.pageResources = this.pageResources;
/* 278 */     tpl.bBox = new Rectangle(this.bBox);
/* 279 */     tpl.group = this.group;
/* 280 */     tpl.layer = this.layer;
/* 281 */     if (this.matrix != null) {
/* 282 */       tpl.matrix = new PdfArray(this.matrix);
/*     */     }
/* 284 */     tpl.separator = this.separator;
/* 285 */     tpl.additional = this.additional;
/* 286 */     return tpl;
/*     */   }
/*     */ 
/*     */   public int getType() {
/* 290 */     return this.type;
/*     */   }
/*     */ 
/*     */   PageResources getPageResources() {
/* 294 */     return this.pageResources;
/*     */   }
/*     */ 
/*     */   public PdfTransparencyGroup getGroup()
/*     */   {
/* 302 */     return this.group;
/*     */   }
/*     */ 
/*     */   public void setGroup(PdfTransparencyGroup group)
/*     */   {
/* 310 */     this.group = group;
/*     */   }
/*     */ 
/*     */   public PdfDictionary getAdditional()
/*     */   {
/* 321 */     return this.additional;
/*     */   }
/*     */ 
/*     */   public void setAdditional(PdfDictionary additional)
/*     */   {
/* 332 */     this.additional = additional;
/*     */   }
/*     */ 
/*     */   public PdfIndirectReference getCurrentPage() {
/* 336 */     return this.pageReference == null ? this.writer.getCurrentPage() : this.pageReference;
/*     */   }
/*     */ 
/*     */   public PdfIndirectReference getPageReference() {
/* 340 */     return this.pageReference;
/*     */   }
/*     */ 
/*     */   public void setPageReference(PdfIndirectReference pageReference) {
/* 344 */     this.pageReference = pageReference;
/*     */   }
/*     */ 
/*     */   public boolean isContentTagged() {
/* 348 */     return this.contentTagged;
/*     */   }
/*     */ 
/*     */   public void setContentTagged(boolean contentTagged) {
/* 352 */     this.contentTagged = contentTagged;
/*     */   }
/*     */ 
/*     */   public PdfObject getAccessibleAttribute(PdfName key) {
/* 356 */     if (this.accessibleAttributes != null) {
/* 357 */       return (PdfObject)this.accessibleAttributes.get(key);
/*     */     }
/* 359 */     return null;
/*     */   }
/*     */ 
/*     */   public void setAccessibleAttribute(PdfName key, PdfObject value) {
/* 363 */     if (this.accessibleAttributes == null)
/* 364 */       this.accessibleAttributes = new HashMap();
/* 365 */     this.accessibleAttributes.put(key, value);
/*     */   }
/*     */ 
/*     */   public HashMap<PdfName, PdfObject> getAccessibleAttributes() {
/* 369 */     return this.accessibleAttributes;
/*     */   }
/*     */ 
/*     */   public PdfName getRole() {
/* 373 */     return this.role;
/*     */   }
/*     */ 
/*     */   public void setRole(PdfName role) {
/* 377 */     this.role = role;
/*     */   }
/*     */ 
/*     */   public AccessibleElementId getId() {
/* 381 */     if (this.id == null)
/* 382 */       this.id = new AccessibleElementId();
/* 383 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(AccessibleElementId id) {
/* 387 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public boolean isInline() {
/* 391 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfTemplate
 * JD-Core Version:    0.6.2
 */