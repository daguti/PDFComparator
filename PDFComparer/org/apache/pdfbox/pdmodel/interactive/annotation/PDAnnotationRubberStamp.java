/*     */ package org.apache.pdfbox.pdmodel.interactive.annotation;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ 
/*     */ public class PDAnnotationRubberStamp extends PDAnnotationMarkup
/*     */ {
/*     */   public static final String NAME_APPROVED = "Approved";
/*     */   public static final String NAME_EXPERIMENTAL = "Experimental";
/*     */   public static final String NAME_NOT_APPROVED = "NotApproved";
/*     */   public static final String NAME_AS_IS = "AsIs";
/*     */   public static final String NAME_EXPIRED = "Expired";
/*     */   public static final String NAME_NOT_FOR_PUBLIC_RELEASE = "NotForPublicRelease";
/*     */   public static final String NAME_FOR_PUBLIC_RELEASE = "ForPublicRelease";
/*     */   public static final String NAME_DRAFT = "Draft";
/*     */   public static final String NAME_FOR_COMMENT = "ForComment";
/*     */   public static final String NAME_TOP_SECRET = "TopSecret";
/*     */   public static final String NAME_DEPARTMENTAL = "Departmental";
/*     */   public static final String NAME_CONFIDENTIAL = "Confidential";
/*     */   public static final String NAME_FINAL = "Final";
/*     */   public static final String NAME_SOLD = "Sold";
/*     */   public static final String SUB_TYPE = "Stamp";
/*     */ 
/*     */   public PDAnnotationRubberStamp()
/*     */   {
/* 105 */     getDictionary().setItem(COSName.SUBTYPE, COSName.getPDFName("Stamp"));
/*     */   }
/*     */ 
/*     */   public PDAnnotationRubberStamp(COSDictionary field)
/*     */   {
/* 116 */     super(field);
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 127 */     getDictionary().setName(COSName.NAME, name);
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 138 */     return getDictionary().getNameAsString(COSName.NAME, "Draft");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationRubberStamp
 * JD-Core Version:    0.6.2
 */