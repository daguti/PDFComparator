/*    */ package com.itextpdf.text.pdf.fonts.otf;
/*    */ 
/*    */ public class TableHeader
/*    */ {
/*    */   public int version;
/*    */   public int scriptListOffset;
/*    */   public int featureListOffset;
/*    */   public int lookupListOffset;
/*    */ 
/*    */   public TableHeader(int version, int scriptListOffset, int featureListOffset, int lookupListOffset)
/*    */   {
/* 59 */     this.version = version;
/* 60 */     this.scriptListOffset = scriptListOffset;
/* 61 */     this.featureListOffset = featureListOffset;
/* 62 */     this.lookupListOffset = lookupListOffset;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.fonts.otf.TableHeader
 * JD-Core Version:    0.6.2
 */