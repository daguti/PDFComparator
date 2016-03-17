/*     */ package org.apache.pdfbox.pdmodel.documentinterchange.markedcontent;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.documentinterchange.taggedpdf.PDArtifactMarkedContent;
/*     */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObject;
/*     */ import org.apache.pdfbox.util.TextPosition;
/*     */ 
/*     */ public class PDMarkedContent
/*     */ {
/*     */   private String tag;
/*     */   private COSDictionary properties;
/*     */   private List<Object> contents;
/*     */ 
/*     */   public static PDMarkedContent create(COSName tag, COSDictionary properties)
/*     */   {
/*  46 */     if (COSName.ARTIFACT.equals(tag))
/*     */     {
/*  48 */       new PDArtifactMarkedContent(properties);
/*     */     }
/*  50 */     return new PDMarkedContent(tag, properties);
/*     */   }
/*     */ 
/*     */   public PDMarkedContent(COSName tag, COSDictionary properties)
/*     */   {
/*  67 */     this.tag = (tag == null ? null : tag.getName());
/*  68 */     this.properties = properties;
/*  69 */     this.contents = new ArrayList();
/*     */   }
/*     */ 
/*     */   public String getTag()
/*     */   {
/*  80 */     return this.tag;
/*     */   }
/*     */ 
/*     */   public COSDictionary getProperties()
/*     */   {
/*  90 */     return this.properties;
/*     */   }
/*     */ 
/*     */   public int getMCID()
/*     */   {
/* 100 */     return (getProperties() == null ? null : Integer.valueOf(getProperties().getInt(COSName.MCID))).intValue();
/*     */   }
/*     */ 
/*     */   public String getLanguage()
/*     */   {
/* 111 */     return getProperties() == null ? null : getProperties().getNameAsString(COSName.LANG);
/*     */   }
/*     */ 
/*     */   public String getActualText()
/*     */   {
/* 122 */     return getProperties() == null ? null : getProperties().getString(COSName.ACTUAL_TEXT);
/*     */   }
/*     */ 
/*     */   public String getAlternateDescription()
/*     */   {
/* 133 */     return getProperties() == null ? null : getProperties().getString(COSName.ALT);
/*     */   }
/*     */ 
/*     */   public String getExpandedForm()
/*     */   {
/* 144 */     return getProperties() == null ? null : getProperties().getString(COSName.E);
/*     */   }
/*     */ 
/*     */   public List<Object> getContents()
/*     */   {
/* 160 */     return this.contents;
/*     */   }
/*     */ 
/*     */   public void addText(TextPosition text)
/*     */   {
/* 170 */     getContents().add(text);
/*     */   }
/*     */ 
/*     */   public void addMarkedContent(PDMarkedContent markedContent)
/*     */   {
/* 180 */     getContents().add(markedContent);
/*     */   }
/*     */ 
/*     */   public void addXObject(PDXObject xobject)
/*     */   {
/* 190 */     getContents().add(xobject);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 197 */     StringBuilder sb = new StringBuilder("tag=").append(this.tag).append(", properties=").append(this.properties);
/*     */ 
/* 199 */     sb.append(", contents=").append(this.contents);
/* 200 */     return sb.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.documentinterchange.markedcontent.PDMarkedContent
 * JD-Core Version:    0.6.2
 */