/*     */ package org.apache.pdfbox.pdmodel.common;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ 
/*     */ public class PDPageLabelRange
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSDictionary root;
/*  39 */   private static final COSName KEY_START = COSName.getPDFName("St");
/*  40 */   private static final COSName KEY_PREFIX = COSName.P;
/*  41 */   private static final COSName KEY_STYLE = COSName.getPDFName("S");
/*     */   public static final String STYLE_DECIMAL = "D";
/*     */   public static final String STYLE_ROMAN_UPPER = "R";
/*     */   public static final String STYLE_ROMAN_LOWER = "r";
/*     */   public static final String STYLE_LETTERS_UPPER = "A";
/*     */   public static final String STYLE_LETTERS_LOWER = "a";
/*     */ 
/*     */   public PDPageLabelRange()
/*     */   {
/*  77 */     this(new COSDictionary());
/*     */   }
/*     */ 
/*     */   public PDPageLabelRange(COSDictionary dict)
/*     */   {
/*  88 */     this.root = dict;
/*     */   }
/*     */ 
/*     */   public COSDictionary getCOSDictionary()
/*     */   {
/*  98 */     return this.root;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/* 103 */     return this.root;
/*     */   }
/*     */ 
/*     */   public String getStyle()
/*     */   {
/* 113 */     return this.root.getNameAsString(KEY_STYLE);
/*     */   }
/*     */ 
/*     */   public void setStyle(String style)
/*     */   {
/* 125 */     if (style != null)
/*     */     {
/* 127 */       this.root.setName(KEY_STYLE, style);
/*     */     }
/*     */     else
/*     */     {
/* 131 */       this.root.removeItem(KEY_STYLE);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getStart()
/*     */   {
/* 142 */     return this.root.getInt(KEY_START, 1);
/*     */   }
/*     */ 
/*     */   public void setStart(int start)
/*     */   {
/* 155 */     if (start <= 0)
/*     */     {
/* 157 */       throw new IllegalArgumentException("The page numbering start value must be a positive integer");
/*     */     }
/*     */ 
/* 160 */     this.root.setInt(KEY_START, start);
/*     */   }
/*     */ 
/*     */   public String getPrefix()
/*     */   {
/* 171 */     return this.root.getString(KEY_PREFIX);
/*     */   }
/*     */ 
/*     */   public void setPrefix(String prefix)
/*     */   {
/* 183 */     if (prefix != null)
/*     */     {
/* 185 */       this.root.setString(KEY_PREFIX, prefix);
/*     */     }
/*     */     else
/*     */     {
/* 189 */       this.root.removeItem(KEY_PREFIX);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.PDPageLabelRange
 * JD-Core Version:    0.6.2
 */