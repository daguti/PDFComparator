/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ /** @deprecated */
/*     */ class PdfCopyFormsImp extends PdfCopyFieldsImp
/*     */ {
/*     */   PdfCopyFormsImp(OutputStream os)
/*     */     throws DocumentException
/*     */   {
/*  68 */     super(os);
/*     */   }
/*     */ 
/*     */   public void copyDocumentFields(PdfReader reader)
/*     */     throws DocumentException
/*     */   {
/*  77 */     if (!reader.isOpenedWithFullPermissions())
/*  78 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("pdfreader.not.opened.with.owner.password", new Object[0]));
/*  79 */     if (this.readers2intrefs.containsKey(reader)) {
/*  80 */       reader = new PdfReader(reader);
/*     */     }
/*     */     else {
/*  83 */       if (reader.isTampered())
/*  84 */         throw new DocumentException(MessageLocalization.getComposedMessage("the.document.was.reused", new Object[0]));
/*  85 */       reader.consolidateNamedDestinations();
/*  86 */       reader.setTampered(true);
/*     */     }
/*  88 */     reader.shuffleSubsetNames();
/*  89 */     this.readers2intrefs.put(reader, new IntHashtable());
/*     */ 
/*  91 */     this.visited.put(reader, new IntHashtable());
/*     */ 
/*  93 */     this.fields.add(reader.getAcroFields());
/*  94 */     updateCalculationOrder(reader);
/*     */   }
/*     */ 
/*     */   void mergeFields()
/*     */   {
/* 103 */     for (int k = 0; k < this.fields.size(); k++) {
/* 104 */       Map fd = ((AcroFields)this.fields.get(k)).getFields();
/* 105 */       mergeWithMaster(fd);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfCopyFormsImp
 * JD-Core Version:    0.6.2
 */