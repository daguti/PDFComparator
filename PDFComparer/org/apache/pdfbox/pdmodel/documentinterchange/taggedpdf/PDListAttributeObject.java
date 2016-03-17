/*     */ package org.apache.pdfbox.pdmodel.documentinterchange.taggedpdf;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ 
/*     */ public class PDListAttributeObject extends PDStandardAttributeObject
/*     */ {
/*     */   public static final String OWNER_LIST = "List";
/*     */   protected static final String LIST_NUMBERING = "ListNumbering";
/*     */   public static final String LIST_NUMBERING_CIRCLE = "Circle";
/*     */   public static final String LIST_NUMBERING_DECIMAL = "Decimal";
/*     */   public static final String LIST_NUMBERING_DISC = "Disc";
/*     */   public static final String LIST_NUMBERING_LOWER_ALPHA = "LowerAlpha";
/*     */   public static final String LIST_NUMBERING_LOWER_ROMAN = "LowerRoman";
/*     */   public static final String LIST_NUMBERING_NONE = "None";
/*     */   public static final String LIST_NUMBERING_SQUARE = "Square";
/*     */   public static final String LIST_NUMBERING_UPPER_ALPHA = "UpperAlpha";
/*     */   public static final String LIST_NUMBERING_UPPER_ROMAN = "UpperRoman";
/*     */ 
/*     */   public PDListAttributeObject()
/*     */   {
/*  81 */     setOwner("List");
/*     */   }
/*     */ 
/*     */   public PDListAttributeObject(COSDictionary dictionary)
/*     */   {
/*  91 */     super(dictionary);
/*     */   }
/*     */ 
/*     */   public String getListNumbering()
/*     */   {
/* 103 */     return getName("ListNumbering", "None");
/*     */   }
/*     */ 
/*     */   public void setListNumbering(String listNumbering)
/*     */   {
/* 125 */     setName("ListNumbering", listNumbering);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 131 */     StringBuilder sb = new StringBuilder().append(super.toString());
/* 132 */     if (isSpecified("ListNumbering"))
/*     */     {
/* 134 */       sb.append(", ListNumbering=").append(getListNumbering());
/*     */     }
/* 136 */     return sb.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.documentinterchange.taggedpdf.PDListAttributeObject
 * JD-Core Version:    0.6.2
 */