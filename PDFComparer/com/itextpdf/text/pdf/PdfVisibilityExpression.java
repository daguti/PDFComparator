/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ 
/*     */ public class PdfVisibilityExpression extends PdfArray
/*     */ {
/*     */   public static final int OR = 0;
/*     */   public static final int AND = 1;
/*     */   public static final int NOT = -1;
/*     */ 
/*     */   public PdfVisibilityExpression(int type)
/*     */   {
/*  69 */     switch (type) {
/*     */     case 0:
/*  71 */       super.add(PdfName.OR);
/*  72 */       break;
/*     */     case 1:
/*  74 */       super.add(PdfName.AND);
/*  75 */       break;
/*     */     case -1:
/*  77 */       super.add(PdfName.NOT);
/*  78 */       break;
/*     */     default:
/*  80 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("illegal.ve.value", new Object[0]));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void add(int index, PdfObject element)
/*     */   {
/*  89 */     throw new IllegalArgumentException(MessageLocalization.getComposedMessage("illegal.ve.value", new Object[0]));
/*     */   }
/*     */ 
/*     */   public boolean add(PdfObject object)
/*     */   {
/*  97 */     if ((object instanceof PdfLayer))
/*  98 */       return super.add(((PdfLayer)object).getRef());
/*  99 */     if ((object instanceof PdfVisibilityExpression))
/* 100 */       return super.add(object);
/* 101 */     throw new IllegalArgumentException(MessageLocalization.getComposedMessage("illegal.ve.value", new Object[0]));
/*     */   }
/*     */ 
/*     */   public void addFirst(PdfObject object)
/*     */   {
/* 109 */     throw new IllegalArgumentException(MessageLocalization.getComposedMessage("illegal.ve.value", new Object[0]));
/*     */   }
/*     */ 
/*     */   public boolean add(float[] values)
/*     */   {
/* 117 */     throw new IllegalArgumentException(MessageLocalization.getComposedMessage("illegal.ve.value", new Object[0]));
/*     */   }
/*     */ 
/*     */   public boolean add(int[] values)
/*     */   {
/* 125 */     throw new IllegalArgumentException(MessageLocalization.getComposedMessage("illegal.ve.value", new Object[0]));
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfVisibilityExpression
 * JD-Core Version:    0.6.2
 */