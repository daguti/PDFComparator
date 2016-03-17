/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import com.itextpdf.text.pdf.PdfName;
/*     */ import com.itextpdf.text.pdf.PdfObject;
/*     */ import com.itextpdf.text.pdf.interfaces.IAccessibleElement;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class ListBody
/*     */   implements IAccessibleElement
/*     */ {
/*  55 */   protected PdfName role = PdfName.LBODY;
/*  56 */   private AccessibleElementId id = null;
/*  57 */   protected HashMap<PdfName, PdfObject> accessibleAttributes = null;
/*  58 */   protected ListItem parentItem = null;
/*     */ 
/*     */   protected ListBody(ListItem parentItem) {
/*  61 */     this.parentItem = parentItem;
/*     */   }
/*     */ 
/*     */   public PdfObject getAccessibleAttribute(PdfName key) {
/*  65 */     if (this.accessibleAttributes != null) {
/*  66 */       return (PdfObject)this.accessibleAttributes.get(key);
/*     */     }
/*  68 */     return null;
/*     */   }
/*     */ 
/*     */   public void setAccessibleAttribute(PdfName key, PdfObject value) {
/*  72 */     if (this.accessibleAttributes == null)
/*  73 */       this.accessibleAttributes = new HashMap();
/*  74 */     this.accessibleAttributes.put(key, value);
/*     */   }
/*     */ 
/*     */   public HashMap<PdfName, PdfObject> getAccessibleAttributes() {
/*  78 */     return this.accessibleAttributes;
/*     */   }
/*     */ 
/*     */   public PdfName getRole() {
/*  82 */     return this.role;
/*     */   }
/*     */ 
/*     */   public void setRole(PdfName role) {
/*  86 */     this.role = role;
/*     */   }
/*     */ 
/*     */   public AccessibleElementId getId() {
/*  90 */     if (this.id == null)
/*  91 */       this.id = new AccessibleElementId();
/*  92 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(AccessibleElementId id) {
/*  96 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public boolean isInline() {
/* 100 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.ListBody
 * JD-Core Version:    0.6.2
 */