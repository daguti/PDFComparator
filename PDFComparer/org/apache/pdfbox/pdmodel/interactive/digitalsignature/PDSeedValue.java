/*     */ package org.apache.pdfbox.pdmodel.interactive.digitalsignature;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.common.COSArrayList;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.util.BitFlagHelper;
/*     */ 
/*     */ public class PDSeedValue
/*     */   implements COSObjectable
/*     */ {
/*     */   public static final int FLAG_FILTER = 1;
/*     */   public static final int FLAG_SUBFILTER = 2;
/*     */   public static final int FLAG_V = 4;
/*     */   public static final int FLAG_REASON = 8;
/*     */   public static final int FLAG_LEGAL_ATTESTATION = 16;
/*     */   public static final int FLAG_ADD_REV_INFO = 32;
/*     */   public static final int FLAG_DIGEST_METHOD = 64;
/*     */   private COSDictionary dictionary;
/*     */ 
/*     */   public PDSeedValue()
/*     */   {
/*  81 */     this.dictionary = new COSDictionary();
/*  82 */     this.dictionary.setItem(COSName.TYPE, COSName.SV);
/*  83 */     this.dictionary.setDirect(true);
/*     */   }
/*     */ 
/*     */   public PDSeedValue(COSDictionary dict)
/*     */   {
/*  93 */     this.dictionary = dict;
/*  94 */     this.dictionary.setDirect(true);
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/* 105 */     return getDictionary();
/*     */   }
/*     */ 
/*     */   public COSDictionary getDictionary()
/*     */   {
/* 115 */     return this.dictionary;
/*     */   }
/*     */ 
/*     */   public boolean isFilterRequired()
/*     */   {
/* 124 */     return BitFlagHelper.getFlag(getDictionary(), COSName.FF, 1);
/*     */   }
/*     */ 
/*     */   public void setFilterRequired(boolean flag)
/*     */   {
/* 134 */     BitFlagHelper.setFlag(getDictionary(), COSName.FF, 1, flag);
/*     */   }
/*     */ 
/*     */   public boolean isSubFilterRequired()
/*     */   {
/* 143 */     return BitFlagHelper.getFlag(getDictionary(), COSName.FF, 2);
/*     */   }
/*     */ 
/*     */   public void setSubFilterRequired(boolean flag)
/*     */   {
/* 153 */     BitFlagHelper.setFlag(getDictionary(), COSName.FF, 2, flag);
/*     */   }
/*     */ 
/*     */   public boolean isDigestMethodRequired()
/*     */   {
/* 162 */     return BitFlagHelper.getFlag(getDictionary(), COSName.FF, 64);
/*     */   }
/*     */ 
/*     */   public void setDigestMethodRequired(boolean flag)
/*     */   {
/* 172 */     BitFlagHelper.setFlag(getDictionary(), COSName.FF, 64, flag);
/*     */   }
/*     */ 
/*     */   public boolean isVRequired()
/*     */   {
/* 181 */     return BitFlagHelper.getFlag(getDictionary(), COSName.FF, 4);
/*     */   }
/*     */ 
/*     */   public void setVRequired(boolean flag)
/*     */   {
/* 191 */     BitFlagHelper.setFlag(getDictionary(), COSName.FF, 4, flag);
/*     */   }
/*     */ 
/*     */   public boolean isReasonRequired()
/*     */   {
/* 200 */     return BitFlagHelper.getFlag(getDictionary(), COSName.FF, 8);
/*     */   }
/*     */ 
/*     */   public void setReasonRequired(boolean flag)
/*     */   {
/* 210 */     BitFlagHelper.setFlag(getDictionary(), COSName.FF, 8, flag);
/*     */   }
/*     */ 
/*     */   public boolean isLegalAttestationRequired()
/*     */   {
/* 219 */     return BitFlagHelper.getFlag(getDictionary(), COSName.FF, 16);
/*     */   }
/*     */ 
/*     */   public void setLegalAttestationRequired(boolean flag)
/*     */   {
/* 229 */     BitFlagHelper.setFlag(getDictionary(), COSName.FF, 16, flag);
/*     */   }
/*     */ 
/*     */   public boolean isAddRevInfoRequired()
/*     */   {
/* 238 */     return BitFlagHelper.getFlag(getDictionary(), COSName.FF, 32);
/*     */   }
/*     */ 
/*     */   public void setAddRevInfoRequired(boolean flag)
/*     */   {
/* 248 */     BitFlagHelper.setFlag(getDictionary(), COSName.FF, 32, flag);
/*     */   }
/*     */ 
/*     */   public String getFilter()
/*     */   {
/* 262 */     return this.dictionary.getNameAsString(COSName.FILTER);
/*     */   }
/*     */ 
/*     */   public void setFilter(COSName filter)
/*     */   {
/* 272 */     this.dictionary.setItem(COSName.FILTER, filter);
/*     */   }
/*     */ 
/*     */   public List<String> getSubFilter()
/*     */   {
/* 287 */     List retval = null;
/* 288 */     COSArray fields = (COSArray)this.dictionary.getDictionaryObject(COSName.SUBFILTER);
/*     */ 
/* 290 */     if (fields != null)
/*     */     {
/* 292 */       List actuals = new ArrayList();
/* 293 */       for (int i = 0; i < fields.size(); i++)
/*     */       {
/* 295 */         String element = fields.getName(i);
/* 296 */         if (element != null)
/*     */         {
/* 298 */           actuals.add(element);
/*     */         }
/*     */       }
/* 301 */       retval = new COSArrayList(actuals, fields);
/*     */     }
/* 303 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setSubFilter(List<COSName> subfilter)
/*     */   {
/* 315 */     this.dictionary.setItem(COSName.SUBFILTER, COSArrayList.converterToCOSArray(subfilter));
/*     */   }
/*     */ 
/*     */   public List<String> getDigestMethod()
/*     */   {
/* 327 */     List retval = null;
/* 328 */     COSArray fields = (COSArray)this.dictionary.getDictionaryObject(COSName.DIGEST_METHOD);
/*     */ 
/* 330 */     if (fields != null)
/*     */     {
/* 332 */       List actuals = new ArrayList();
/* 333 */       for (int i = 0; i < fields.size(); i++)
/*     */       {
/* 335 */         String element = fields.getName(i);
/* 336 */         if (element != null)
/*     */         {
/* 338 */           actuals.add(element);
/*     */         }
/*     */       }
/* 341 */       retval = new COSArrayList(actuals, fields);
/*     */     }
/* 343 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setDigestMethod(List<COSName> digestMethod)
/*     */   {
/* 361 */     for (COSName cosName : digestMethod)
/*     */     {
/* 363 */       if ((!cosName.equals(COSName.DIGEST_SHA1)) && (!cosName.equals(COSName.DIGEST_SHA256)) && (!cosName.equals(COSName.DIGEST_SHA384)) && (!cosName.equals(COSName.DIGEST_SHA512)) && (!cosName.equals(COSName.DIGEST_RIPEMD160)))
/*     */       {
/* 369 */         throw new IllegalArgumentException("Specified digest " + cosName.getName() + " isn't allowed.");
/*     */       }
/*     */     }
/* 372 */     this.dictionary.setItem(COSName.DIGEST_METHOD, COSArrayList.converterToCOSArray(digestMethod));
/*     */   }
/*     */ 
/*     */   public float getV()
/*     */   {
/* 387 */     return this.dictionary.getFloat(COSName.V);
/*     */   }
/*     */ 
/*     */   public void setV(float minimumRequiredCapability)
/*     */   {
/* 402 */     this.dictionary.setFloat(COSName.V, minimumRequiredCapability);
/*     */   }
/*     */ 
/*     */   public List<String> getReasons()
/*     */   {
/* 416 */     List retval = null;
/* 417 */     COSArray fields = (COSArray)this.dictionary.getDictionaryObject(COSName.REASONS);
/*     */ 
/* 419 */     if (fields != null)
/*     */     {
/* 421 */       List actuals = new ArrayList();
/* 422 */       for (int i = 0; i < fields.size(); i++)
/*     */       {
/* 424 */         String element = fields.getString(i);
/* 425 */         if (element != null)
/*     */         {
/* 427 */           actuals.add(element);
/*     */         }
/*     */       }
/* 430 */       retval = new COSArrayList(actuals, fields);
/*     */     }
/* 432 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setReasonsd(List<String> reasons)
/*     */   {
/* 444 */     this.dictionary.setItem(COSName.REASONS, COSArrayList.converterToCOSArray(reasons));
/*     */   }
/*     */ 
/*     */   public PDSeedValueMDP getMDP()
/*     */   {
/* 462 */     COSDictionary dict = (COSDictionary)this.dictionary.getDictionaryObject(COSName.MDP);
/* 463 */     PDSeedValueMDP mdp = null;
/* 464 */     if (dict != null)
/*     */     {
/* 466 */       mdp = new PDSeedValueMDP(dict);
/*     */     }
/* 468 */     return mdp;
/*     */   }
/*     */ 
/*     */   public void setMPD(PDSeedValueMDP mdp)
/*     */   {
/* 486 */     if (mdp != null)
/*     */     {
/* 488 */       this.dictionary.setItem(COSName.MDP, mdp.getCOSObject());
/*     */     }
/*     */   }
/*     */ 
/*     */   public PDSeedValueTimeStamp getTimeStamp()
/*     */   {
/* 501 */     COSDictionary dict = (COSDictionary)this.dictionary.getDictionaryObject(COSName.TIME_STAMP);
/* 502 */     PDSeedValueTimeStamp timestamp = null;
/* 503 */     if (dict != null)
/*     */     {
/* 505 */       timestamp = new PDSeedValueTimeStamp(dict);
/*     */     }
/* 507 */     return timestamp;
/*     */   }
/*     */ 
/*     */   public void setTimeStamp(PDSeedValueTimeStamp timestamp)
/*     */   {
/* 519 */     if (timestamp != null)
/*     */     {
/* 521 */       this.dictionary.setItem(COSName.TIME_STAMP, timestamp.getCOSObject());
/*     */     }
/*     */   }
/*     */ 
/*     */   public List<String> getLegalAttestation()
/*     */   {
/* 533 */     List retval = null;
/* 534 */     COSArray fields = (COSArray)this.dictionary.getDictionaryObject(COSName.LEGAL_ATTESTATION);
/*     */ 
/* 536 */     if (fields != null)
/*     */     {
/* 538 */       List actuals = new ArrayList();
/* 539 */       for (int i = 0; i < fields.size(); i++)
/*     */       {
/* 541 */         String element = fields.getString(i);
/* 542 */         if (element != null)
/*     */         {
/* 544 */           actuals.add(element);
/*     */         }
/*     */       }
/* 547 */       retval = new COSArrayList(actuals, fields);
/*     */     }
/* 549 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setLegalAttestation(List<String> legalAttestation)
/*     */   {
/* 561 */     this.dictionary.setItem(COSName.LEGAL_ATTESTATION, COSArrayList.converterToCOSArray(legalAttestation));
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSeedValue
 * JD-Core Version:    0.6.2
 */