/*     */ package org.apache.pdfbox.pdmodel;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Calendar;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ 
/*     */ public class PDDocumentInformation
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSDictionary info;
/*     */ 
/*     */   public PDDocumentInformation()
/*     */   {
/*  50 */     this.info = new COSDictionary();
/*     */   }
/*     */ 
/*     */   public PDDocumentInformation(COSDictionary dic)
/*     */   {
/*  60 */     this.info = dic;
/*     */   }
/*     */ 
/*     */   public COSDictionary getDictionary()
/*     */   {
/*  70 */     return this.info;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  80 */     return this.info;
/*     */   }
/*     */ 
/*     */   public String getTitle()
/*     */   {
/*  90 */     return this.info.getString(COSName.TITLE);
/*     */   }
/*     */ 
/*     */   public void setTitle(String title)
/*     */   {
/* 100 */     this.info.setString(COSName.TITLE, title);
/*     */   }
/*     */ 
/*     */   public String getAuthor()
/*     */   {
/* 110 */     return this.info.getString(COSName.AUTHOR);
/*     */   }
/*     */ 
/*     */   public void setAuthor(String author)
/*     */   {
/* 120 */     this.info.setString(COSName.AUTHOR, author);
/*     */   }
/*     */ 
/*     */   public String getSubject()
/*     */   {
/* 130 */     return this.info.getString(COSName.SUBJECT);
/*     */   }
/*     */ 
/*     */   public void setSubject(String subject)
/*     */   {
/* 140 */     this.info.setString(COSName.SUBJECT, subject);
/*     */   }
/*     */ 
/*     */   public String getKeywords()
/*     */   {
/* 150 */     return this.info.getString(COSName.KEYWORDS);
/*     */   }
/*     */ 
/*     */   public void setKeywords(String keywords)
/*     */   {
/* 160 */     this.info.setString(COSName.KEYWORDS, keywords);
/*     */   }
/*     */ 
/*     */   public String getCreator()
/*     */   {
/* 170 */     return this.info.getString(COSName.CREATOR);
/*     */   }
/*     */ 
/*     */   public void setCreator(String creator)
/*     */   {
/* 180 */     this.info.setString(COSName.CREATOR, creator);
/*     */   }
/*     */ 
/*     */   public String getProducer()
/*     */   {
/* 190 */     return this.info.getString(COSName.PRODUCER);
/*     */   }
/*     */ 
/*     */   public void setProducer(String producer)
/*     */   {
/* 200 */     this.info.setString(COSName.PRODUCER, producer);
/*     */   }
/*     */ 
/*     */   public Calendar getCreationDate()
/*     */     throws IOException
/*     */   {
/* 212 */     return this.info.getDate(COSName.CREATION_DATE);
/*     */   }
/*     */ 
/*     */   public void setCreationDate(Calendar date)
/*     */   {
/* 222 */     this.info.setDate(COSName.CREATION_DATE, date);
/*     */   }
/*     */ 
/*     */   public Calendar getModificationDate()
/*     */     throws IOException
/*     */   {
/* 234 */     return this.info.getDate(COSName.MOD_DATE);
/*     */   }
/*     */ 
/*     */   public void setModificationDate(Calendar date)
/*     */   {
/* 244 */     this.info.setDate(COSName.MOD_DATE, date);
/*     */   }
/*     */ 
/*     */   public String getTrapped()
/*     */   {
/* 255 */     return this.info.getNameAsString(COSName.TRAPPED);
/*     */   }
/*     */ 
/*     */   public Set<String> getMetadataKeys()
/*     */   {
/* 266 */     Set keys = new TreeSet();
/* 267 */     for (COSName key : this.info.keySet()) {
/* 268 */       keys.add(key.getName());
/*     */     }
/* 270 */     return keys;
/*     */   }
/*     */ 
/*     */   public String getCustomMetadataValue(String fieldName)
/*     */   {
/* 284 */     return this.info.getString(fieldName);
/*     */   }
/*     */ 
/*     */   public void setCustomMetadataValue(String fieldName, String fieldValue)
/*     */   {
/* 295 */     this.info.setString(fieldName, fieldValue);
/*     */   }
/*     */ 
/*     */   public void setTrapped(String value)
/*     */   {
/* 306 */     if ((value != null) && (!value.equals("True")) && (!value.equals("False")) && (!value.equals("Unknown")))
/*     */     {
/* 311 */       throw new RuntimeException("Valid values for trapped are 'True', 'False', or 'Unknown'");
/*     */     }
/*     */ 
/* 315 */     this.info.setName(COSName.TRAPPED, value);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.PDDocumentInformation
 * JD-Core Version:    0.6.2
 */