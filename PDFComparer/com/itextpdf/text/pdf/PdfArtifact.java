/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.AccessibleElementId;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.pdf.interfaces.IAccessibleElement;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ 
/*     */ public class PdfArtifact
/*     */   implements IAccessibleElement
/*     */ {
/*  55 */   private static final HashSet<String> allowedArtifactTypes = new HashSet(Arrays.asList(new String[] { "Pagination", "Layout", "Page", "Background" }));
/*     */   protected PdfName role;
/*     */   protected HashMap<PdfName, PdfObject> accessibleAttributes;
/*     */   protected AccessibleElementId id;
/*     */ 
/*     */   public PdfArtifact()
/*     */   {
/*  57 */     this.role = PdfName.ARTIFACT;
/*  58 */     this.accessibleAttributes = null;
/*  59 */     this.id = new AccessibleElementId();
/*     */   }
/*     */   public PdfObject getAccessibleAttribute(PdfName key) {
/*  62 */     if (this.accessibleAttributes != null) {
/*  63 */       return (PdfObject)this.accessibleAttributes.get(key);
/*     */     }
/*  65 */     return null;
/*     */   }
/*     */ 
/*     */   public void setAccessibleAttribute(PdfName key, PdfObject value) {
/*  69 */     if (this.accessibleAttributes == null)
/*  70 */       this.accessibleAttributes = new HashMap();
/*  71 */     this.accessibleAttributes.put(key, value);
/*     */   }
/*     */ 
/*     */   public HashMap<PdfName, PdfObject> getAccessibleAttributes() {
/*  75 */     return this.accessibleAttributes;
/*     */   }
/*     */ 
/*     */   public PdfName getRole() {
/*  79 */     return this.role;
/*     */   }
/*     */ 
/*     */   public void setRole(PdfName role) {
/*     */   }
/*     */ 
/*     */   public AccessibleElementId getId() {
/*  86 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(AccessibleElementId id) {
/*  90 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public boolean isInline() {
/*  94 */     return true;
/*     */   }
/*     */ 
/*     */   public PdfString getType() {
/*  98 */     return this.accessibleAttributes == null ? null : (PdfString)this.accessibleAttributes.get(PdfName.TYPE);
/*     */   }
/*     */ 
/*     */   public void setType(PdfString type) {
/* 102 */     if (!allowedArtifactTypes.contains(type.toString()))
/* 103 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.artifact.type.1.is.invalid", new Object[] { type }));
/* 104 */     setAccessibleAttribute(PdfName.TYPE, type);
/*     */   }
/*     */ 
/*     */   public void setType(ArtifactType type) {
/* 108 */     PdfString artifactType = null;
/* 109 */     switch (1.$SwitchMap$com$itextpdf$text$pdf$PdfArtifact$ArtifactType[type.ordinal()]) {
/*     */     case 1:
/* 111 */       artifactType = new PdfString("Background");
/* 112 */       break;
/*     */     case 2:
/* 114 */       artifactType = new PdfString("Layout");
/* 115 */       break;
/*     */     case 3:
/* 117 */       artifactType = new PdfString("Page");
/* 118 */       break;
/*     */     case 4:
/* 120 */       artifactType = new PdfString("Pagination");
/*     */     }
/*     */ 
/* 123 */     setAccessibleAttribute(PdfName.TYPE, artifactType);
/*     */   }
/*     */ 
/*     */   public PdfArray getBBox() {
/* 127 */     return this.accessibleAttributes == null ? null : (PdfArray)this.accessibleAttributes.get(PdfName.BBOX);
/*     */   }
/*     */ 
/*     */   public void setBBox(PdfArray bbox) {
/* 131 */     setAccessibleAttribute(PdfName.BBOX, bbox);
/*     */   }
/*     */ 
/*     */   public PdfArray getAttached() {
/* 135 */     return this.accessibleAttributes == null ? null : (PdfArray)this.accessibleAttributes.get(PdfName.ATTACHED);
/*     */   }
/*     */ 
/*     */   public void setAttached(PdfArray attached) {
/* 139 */     setAccessibleAttribute(PdfName.ATTACHED, attached);
/*     */   }
/*     */ 
/*     */   public static enum ArtifactType {
/* 143 */     PAGINATION, 
/* 144 */     LAYOUT, 
/* 145 */     PAGE, 
/* 146 */     BACKGROUND;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfArtifact
 * JD-Core Version:    0.6.2
 */