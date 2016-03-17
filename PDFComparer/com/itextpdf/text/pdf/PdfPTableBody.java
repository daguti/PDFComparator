/*    */ package com.itextpdf.text.pdf;
/*    */ 
/*    */ import com.itextpdf.text.AccessibleElementId;
/*    */ import com.itextpdf.text.pdf.interfaces.IAccessibleElement;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class PdfPTableBody
/*    */   implements IAccessibleElement
/*    */ {
/* 55 */   protected AccessibleElementId id = new AccessibleElementId();
/* 56 */   protected ArrayList<PdfPRow> rows = null;
/* 57 */   protected PdfName role = PdfName.TBODY;
/* 58 */   protected HashMap<PdfName, PdfObject> accessibleAttributes = null;
/*    */ 
/*    */   public PdfObject getAccessibleAttribute(PdfName key)
/*    */   {
/* 65 */     if (this.accessibleAttributes != null) {
/* 66 */       return (PdfObject)this.accessibleAttributes.get(key);
/*    */     }
/* 68 */     return null;
/*    */   }
/*    */ 
/*    */   public void setAccessibleAttribute(PdfName key, PdfObject value) {
/* 72 */     if (this.accessibleAttributes == null)
/* 73 */       this.accessibleAttributes = new HashMap();
/* 74 */     this.accessibleAttributes.put(key, value);
/*    */   }
/*    */ 
/*    */   public HashMap<PdfName, PdfObject> getAccessibleAttributes() {
/* 78 */     return this.accessibleAttributes;
/*    */   }
/*    */ 
/*    */   public PdfName getRole() {
/* 82 */     return this.role;
/*    */   }
/*    */ 
/*    */   public void setRole(PdfName role) {
/* 86 */     this.role = role;
/*    */   }
/*    */ 
/*    */   public AccessibleElementId getId() {
/* 90 */     return this.id;
/*    */   }
/*    */ 
/*    */   public void setId(AccessibleElementId id) {
/* 94 */     this.id = id;
/*    */   }
/*    */ 
/*    */   public boolean isInline() {
/* 98 */     return false;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfPTableBody
 * JD-Core Version:    0.6.2
 */