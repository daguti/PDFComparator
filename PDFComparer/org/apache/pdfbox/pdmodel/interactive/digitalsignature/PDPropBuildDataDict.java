/*     */ package org.apache.pdfbox.pdmodel.interactive.digitalsignature;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ 
/*     */ public class PDPropBuildDataDict
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSDictionary dictionary;
/*     */ 
/*     */   public PDPropBuildDataDict()
/*     */   {
/*  41 */     this.dictionary = new COSDictionary();
/*  42 */     this.dictionary.setDirect(true);
/*     */   }
/*     */ 
/*     */   public PDPropBuildDataDict(COSDictionary dict)
/*     */   {
/*  52 */     this.dictionary = dict;
/*  53 */     this.dictionary.setDirect(true);
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  64 */     return getDictionary();
/*     */   }
/*     */ 
/*     */   public COSDictionary getDictionary()
/*     */   {
/*  74 */     return this.dictionary;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  83 */     return this.dictionary.getString(COSName.NAME);
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/*  93 */     this.dictionary.setName(COSName.NAME, name);
/*     */   }
/*     */ 
/*     */   public String getDate()
/*     */   {
/* 103 */     return this.dictionary.getString(COSName.DATE);
/*     */   }
/*     */ 
/*     */   public void setDate(String date)
/*     */   {
/* 114 */     this.dictionary.setString(COSName.DATE, date);
/*     */   }
/*     */ 
/*     */   public long getRevision()
/*     */   {
/* 124 */     return this.dictionary.getLong(COSName.R);
/*     */   }
/*     */ 
/*     */   public void setRevision(long revision)
/*     */   {
/* 134 */     this.dictionary.setLong(COSName.R, revision);
/*     */   }
/*     */ 
/*     */   public long getMinimumRevision()
/*     */   {
/* 145 */     return this.dictionary.getLong(COSName.V);
/*     */   }
/*     */ 
/*     */   public void setMinimumRevision(long revision)
/*     */   {
/* 156 */     this.dictionary.setLong(COSName.V, revision);
/*     */   }
/*     */ 
/*     */   public boolean getPreRelease()
/*     */   {
/* 167 */     return this.dictionary.getBoolean(COSName.PRE_RELEASE, false);
/*     */   }
/*     */ 
/*     */   public void setPreRelease(boolean preRelease)
/*     */   {
/* 179 */     this.dictionary.setBoolean(COSName.PRE_RELEASE, preRelease);
/*     */   }
/*     */ 
/*     */   public String getOS()
/*     */   {
/* 189 */     return this.dictionary.getString(COSName.OS);
/*     */   }
/*     */ 
/*     */   public void setOS(String os)
/*     */   {
/* 199 */     this.dictionary.setString(COSName.OS, os);
/*     */   }
/*     */ 
/*     */   public boolean getNonEFontNoWarn()
/*     */   {
/* 213 */     return this.dictionary.getBoolean(COSName.NON_EFONT_NO_WARN, true);
/*     */   }
/*     */ 
/*     */   public boolean getTrustedMode()
/*     */   {
/* 236 */     return this.dictionary.getBoolean(COSName.TRUSTED_MODE, false);
/*     */   }
/*     */ 
/*     */   public void setTrustedMode(boolean trustedMode)
/*     */   {
/* 246 */     this.dictionary.setBoolean(COSName.TRUSTED_MODE, trustedMode);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDPropBuildDataDict
 * JD-Core Version:    0.6.2
 */