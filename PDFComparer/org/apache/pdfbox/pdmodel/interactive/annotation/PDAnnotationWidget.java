/*     */ package org.apache.pdfbox.pdmodel.interactive.annotation;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.interactive.action.PDActionFactory;
/*     */ import org.apache.pdfbox.pdmodel.interactive.action.PDAnnotationAdditionalActions;
/*     */ import org.apache.pdfbox.pdmodel.interactive.action.type.PDAction;
/*     */ 
/*     */ public class PDAnnotationWidget extends PDAnnotation
/*     */ {
/*     */   public static final String SUB_TYPE = "Widget";
/*     */ 
/*     */   public PDAnnotationWidget()
/*     */   {
/*  46 */     getDictionary().setName(COSName.SUBTYPE, "Widget");
/*     */   }
/*     */ 
/*     */   public PDAnnotationWidget(COSDictionary field)
/*     */   {
/*  58 */     super(field);
/*     */   }
/*     */ 
/*     */   public String getHighlightingMode()
/*     */   {
/*  83 */     return getDictionary().getNameAsString(COSName.H, "I");
/*     */   }
/*     */ 
/*     */   public void setHighlightingMode(String highlightingMode)
/*     */   {
/* 109 */     if ((highlightingMode == null) || ("N".equals(highlightingMode)) || ("I".equals(highlightingMode)) || ("O".equals(highlightingMode)) || ("P".equals(highlightingMode)) || ("T".equals(highlightingMode)))
/*     */     {
/* 114 */       getDictionary().setName(COSName.H, highlightingMode);
/*     */     }
/*     */     else
/*     */     {
/* 118 */       throw new IllegalArgumentException("Valid values for highlighting mode are 'N', 'N', 'O', 'P' or 'T'");
/*     */     }
/*     */   }
/*     */ 
/*     */   public PDAppearanceCharacteristicsDictionary getAppearanceCharacteristics()
/*     */   {
/* 130 */     COSBase mk = getDictionary().getDictionaryObject(COSName.getPDFName("MK"));
/* 131 */     if ((mk instanceof COSDictionary))
/*     */     {
/* 133 */       return new PDAppearanceCharacteristicsDictionary((COSDictionary)mk);
/*     */     }
/* 135 */     return null;
/*     */   }
/*     */ 
/*     */   public void setAppearanceCharacteristics(PDAppearanceCharacteristicsDictionary appearanceCharacteristics)
/*     */   {
/* 145 */     getDictionary().setItem("MK", appearanceCharacteristics);
/*     */   }
/*     */ 
/*     */   public PDAction getAction()
/*     */   {
/* 155 */     COSDictionary action = (COSDictionary)getDictionary().getDictionaryObject(COSName.A);
/*     */ 
/* 157 */     return PDActionFactory.createAction(action);
/*     */   }
/*     */ 
/*     */   public void setAction(PDAction action)
/*     */   {
/* 167 */     getDictionary().setItem(COSName.A, action);
/*     */   }
/*     */ 
/*     */   public PDAnnotationAdditionalActions getActions()
/*     */   {
/* 179 */     COSDictionary aa = (COSDictionary)getDictionary().getDictionaryObject("AA");
/* 180 */     PDAnnotationAdditionalActions retval = null;
/* 181 */     if (aa != null)
/*     */     {
/* 183 */       retval = new PDAnnotationAdditionalActions(aa);
/*     */     }
/* 185 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setActions(PDAnnotationAdditionalActions actions)
/*     */   {
/* 195 */     getDictionary().setItem("AA", actions);
/*     */   }
/*     */ 
/*     */   public void setBorderStyle(PDBorderStyleDictionary bs)
/*     */   {
/* 207 */     getDictionary().setItem("BS", bs);
/*     */   }
/*     */ 
/*     */   public PDBorderStyleDictionary getBorderStyle()
/*     */   {
/* 218 */     COSDictionary bs = (COSDictionary)getDictionary().getItem(COSName.getPDFName("BS"));
/*     */ 
/* 220 */     if (bs != null)
/*     */     {
/* 222 */       return new PDBorderStyleDictionary(bs);
/*     */     }
/*     */ 
/* 226 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget
 * JD-Core Version:    0.6.2
 */