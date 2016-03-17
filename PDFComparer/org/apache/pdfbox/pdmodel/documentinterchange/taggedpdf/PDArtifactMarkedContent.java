/*     */ package org.apache.pdfbox.pdmodel.documentinterchange.taggedpdf;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*     */ import org.apache.pdfbox.pdmodel.documentinterchange.markedcontent.PDMarkedContent;
/*     */ 
/*     */ public class PDArtifactMarkedContent extends PDMarkedContent
/*     */ {
/*     */   public PDArtifactMarkedContent(COSDictionary properties)
/*     */   {
/*  37 */     super(COSName.ARTIFACT, properties);
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/*  48 */     return getProperties().getNameAsString(COSName.TYPE);
/*     */   }
/*     */ 
/*     */   public PDRectangle getBBox()
/*     */   {
/*  58 */     PDRectangle retval = null;
/*  59 */     COSArray a = (COSArray)getProperties().getDictionaryObject(COSName.BBOX);
/*     */ 
/*  61 */     if (a != null)
/*     */     {
/*  63 */       retval = new PDRectangle(a);
/*     */     }
/*  65 */     return retval;
/*     */   }
/*     */ 
/*     */   public boolean isTopAttached()
/*     */   {
/*  76 */     return isAttached("Top");
/*     */   }
/*     */ 
/*     */   public boolean isBottomAttached()
/*     */   {
/*  87 */     return isAttached("Bottom");
/*     */   }
/*     */ 
/*     */   public boolean isLeftAttached()
/*     */   {
/*  98 */     return isAttached("Left");
/*     */   }
/*     */ 
/*     */   public boolean isRightAttached()
/*     */   {
/* 109 */     return isAttached("Right");
/*     */   }
/*     */ 
/*     */   public String getSubtype()
/*     */   {
/* 119 */     return getProperties().getNameAsString(COSName.SUBTYPE);
/*     */   }
/*     */ 
/*     */   private boolean isAttached(String edge)
/*     */   {
/* 132 */     COSArray a = (COSArray)getProperties().getDictionaryObject(COSName.ATTACHED);
/*     */ 
/* 134 */     if (a != null)
/*     */     {
/* 136 */       for (int i = 0; i < a.size(); i++)
/*     */       {
/* 138 */         if (edge.equals(a.getName(i)))
/*     */         {
/* 140 */           return true;
/*     */         }
/*     */       }
/*     */     }
/* 144 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.documentinterchange.taggedpdf.PDArtifactMarkedContent
 * JD-Core Version:    0.6.2
 */