/*     */ package org.apache.pdfbox.pdmodel.interactive.annotation;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Calendar;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.pdmodel.common.PDTextStream;
/*     */ 
/*     */ public class PDAnnotationMarkup extends PDAnnotation
/*     */ {
/*     */   public static final String SUB_TYPE_FREETEXT = "FreeText";
/*     */   public static final String SUB_TYPE_POLYGON = "Polygon";
/*     */   public static final String SUB_TYPE_POLYLINE = "PolyLine";
/*     */   public static final String SUB_TYPE_CARET = "Caret";
/*     */   public static final String SUB_TYPE_INK = "Ink";
/*     */   public static final String SUB_TYPE_SOUND = "Sound";
/*     */   public static final String RT_REPLY = "R";
/*     */   public static final String RT_GROUP = "Group";
/*     */ 
/*     */   public PDAnnotationMarkup()
/*     */   {
/*     */   }
/*     */ 
/*     */   public PDAnnotationMarkup(COSDictionary dict)
/*     */   {
/*  94 */     super(dict);
/*     */   }
/*     */ 
/*     */   public String getTitlePopup()
/*     */   {
/* 105 */     return getDictionary().getString("T");
/*     */   }
/*     */ 
/*     */   public void setTitlePopup(String t)
/*     */   {
/* 117 */     getDictionary().setString("T", t);
/*     */   }
/*     */ 
/*     */   public PDAnnotationPopup getPopup()
/*     */   {
/* 128 */     COSDictionary popup = (COSDictionary)getDictionary().getDictionaryObject("Popup");
/* 129 */     if (popup != null)
/*     */     {
/* 131 */       return new PDAnnotationPopup(popup);
/*     */     }
/*     */ 
/* 135 */     return null;
/*     */   }
/*     */ 
/*     */   public void setPopup(PDAnnotationPopup popup)
/*     */   {
/* 148 */     getDictionary().setItem("Popup", popup);
/*     */   }
/*     */ 
/*     */   public float getConstantOpacity()
/*     */   {
/* 159 */     return getDictionary().getFloat("CA", 1.0F);
/*     */   }
/*     */ 
/*     */   public void setConstantOpacity(float ca)
/*     */   {
/* 171 */     getDictionary().setFloat("CA", ca);
/*     */   }
/*     */ 
/*     */   public PDTextStream getRichContents()
/*     */   {
/* 182 */     COSBase rc = getDictionary().getDictionaryObject("RC");
/* 183 */     if (rc != null)
/*     */     {
/* 185 */       return PDTextStream.createTextStream(rc);
/*     */     }
/*     */ 
/* 189 */     return null;
/*     */   }
/*     */ 
/*     */   public void setRichContents(PDTextStream rc)
/*     */   {
/* 201 */     getDictionary().setItem("RC", rc);
/*     */   }
/*     */ 
/*     */   public Calendar getCreationDate()
/*     */     throws IOException
/*     */   {
/* 213 */     return getDictionary().getDate("CreationDate");
/*     */   }
/*     */ 
/*     */   public void setCreationDate(Calendar creationDate)
/*     */   {
/* 224 */     getDictionary().setDate("CreationDate", creationDate);
/*     */   }
/*     */ 
/*     */   public PDAnnotation getInReplyTo()
/*     */     throws IOException
/*     */   {
/* 237 */     COSBase irt = getDictionary().getDictionaryObject("IRT");
/* 238 */     return PDAnnotation.createAnnotation(irt);
/*     */   }
/*     */ 
/*     */   public void setInReplyTo(PDAnnotation irt)
/*     */   {
/* 249 */     getDictionary().setItem("IRT", irt);
/*     */   }
/*     */ 
/*     */   public String getSubject()
/*     */   {
/* 259 */     return getDictionary().getString("Subj");
/*     */   }
/*     */ 
/*     */   public void setSubject(String subj)
/*     */   {
/* 269 */     getDictionary().setString("Subj", subj);
/*     */   }
/*     */ 
/*     */   public String getReplyType()
/*     */   {
/* 280 */     return getDictionary().getNameAsString("RT", "R");
/*     */   }
/*     */ 
/*     */   public void setReplyType(String rt)
/*     */   {
/* 291 */     getDictionary().setName("RT", rt);
/*     */   }
/*     */ 
/*     */   public String getIntent()
/*     */   {
/* 303 */     return getDictionary().getNameAsString("IT");
/*     */   }
/*     */ 
/*     */   public void setIntent(String it)
/*     */   {
/* 315 */     getDictionary().setName("IT", it);
/*     */   }
/*     */ 
/*     */   public PDExternalDataDictionary getExternalData()
/*     */   {
/* 325 */     COSBase exData = getDictionary().getDictionaryObject("ExData");
/* 326 */     if ((exData instanceof COSDictionary))
/*     */     {
/* 328 */       return new PDExternalDataDictionary((COSDictionary)exData);
/*     */     }
/* 330 */     return null;
/*     */   }
/*     */ 
/*     */   public void setExternalData(PDExternalDataDictionary externalData)
/*     */   {
/* 340 */     getDictionary().setItem("ExData", externalData);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationMarkup
 * JD-Core Version:    0.6.2
 */