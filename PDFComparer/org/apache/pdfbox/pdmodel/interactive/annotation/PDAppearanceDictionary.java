/*     */ package org.apache.pdfbox.pdmodel.interactive.annotation;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.pdmodel.common.COSDictionaryMap;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ 
/*     */ public class PDAppearanceDictionary
/*     */   implements COSObjectable
/*     */ {
/*  44 */   private static final Log LOG = LogFactory.getLog(PDAppearanceDictionary.class);
/*     */   private COSDictionary dictionary;
/*     */ 
/*     */   public PDAppearanceDictionary()
/*     */   {
/*  53 */     this.dictionary = new COSDictionary();
/*     */ 
/*  55 */     this.dictionary.setItem(COSName.N, new COSDictionary());
/*     */   }
/*     */ 
/*     */   public PDAppearanceDictionary(COSDictionary dict)
/*     */   {
/*  65 */     this.dictionary = dict;
/*     */   }
/*     */ 
/*     */   public COSDictionary getDictionary()
/*     */   {
/*  74 */     return this.dictionary;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  83 */     return this.dictionary;
/*     */   }
/*     */ 
/*     */   public Map<String, PDAppearanceStream> getNormalAppearance()
/*     */   {
/*  95 */     COSBase ap = this.dictionary.getDictionaryObject(COSName.N);
/*  96 */     if (ap == null)
/*     */     {
/*  98 */       return null;
/*     */     }
/* 100 */     if ((ap instanceof COSStream))
/*     */     {
/* 102 */       COSStream aux = (COSStream)ap;
/* 103 */       ap = new COSDictionary();
/* 104 */       ((COSDictionary)ap).setItem(COSName.DEFAULT, aux);
/*     */     }
/* 106 */     COSDictionary map = (COSDictionary)ap;
/* 107 */     Map actuals = new HashMap();
/* 108 */     Map retval = new COSDictionaryMap(actuals, map);
/* 109 */     for (COSName asName : map.keySet())
/*     */     {
/* 111 */       COSBase stream = map.getDictionaryObject(asName);
/*     */ 
/* 114 */       if ((stream instanceof COSStream))
/*     */       {
/* 116 */         COSStream as = (COSStream)stream;
/* 117 */         actuals.put(asName.getName(), new PDAppearanceStream(as));
/*     */       }
/*     */       else
/*     */       {
/* 121 */         LOG.debug("non-conformance workaround: ignore null value for appearance stream.");
/*     */       }
/*     */     }
/* 124 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setNormalAppearance(Map<String, PDAppearanceStream> appearanceMap)
/*     */   {
/* 136 */     this.dictionary.setItem(COSName.N, COSDictionaryMap.convert(appearanceMap));
/*     */   }
/*     */ 
/*     */   public void setNormalAppearance(PDAppearanceStream ap)
/*     */   {
/* 147 */     this.dictionary.setItem(COSName.N, ap.getStream());
/*     */   }
/*     */ 
/*     */   public Map<String, PDAppearanceStream> getRolloverAppearance()
/*     */   {
/* 160 */     Map retval = null;
/* 161 */     COSBase ap = this.dictionary.getDictionaryObject(COSName.R);
/*     */     COSDictionary map;
/*     */     Map actuals;
/* 162 */     if (ap == null)
/*     */     {
/* 164 */       retval = getNormalAppearance();
/*     */     }
/*     */     else
/*     */     {
/* 168 */       if ((ap instanceof COSStream))
/*     */       {
/* 170 */         COSStream aux = (COSStream)ap;
/* 171 */         ap = new COSDictionary();
/* 172 */         ((COSDictionary)ap).setItem(COSName.DEFAULT, aux);
/*     */       }
/* 174 */       map = (COSDictionary)ap;
/* 175 */       actuals = new HashMap();
/* 176 */       retval = new COSDictionaryMap(actuals, map);
/* 177 */       for (COSName asName : map.keySet())
/*     */       {
/* 179 */         COSBase stream = map.getDictionaryObject(asName);
/*     */ 
/* 182 */         if ((stream instanceof COSStream))
/*     */         {
/* 184 */           COSStream as = (COSStream)stream;
/* 185 */           actuals.put(asName.getName(), new PDAppearanceStream(as));
/*     */         }
/*     */         else
/*     */         {
/* 189 */           LOG.debug("non-conformance workaround: ignore null value for appearance stream.");
/*     */         }
/*     */       }
/*     */     }
/* 193 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setRolloverAppearance(Map<String, PDAppearanceStream> appearanceMap)
/*     */   {
/* 205 */     this.dictionary.setItem(COSName.R, COSDictionaryMap.convert(appearanceMap));
/*     */   }
/*     */ 
/*     */   public void setRolloverAppearance(PDAppearanceStream ap)
/*     */   {
/* 216 */     this.dictionary.setItem(COSName.R, ap.getStream());
/*     */   }
/*     */ 
/*     */   public Map<String, PDAppearanceStream> getDownAppearance()
/*     */   {
/* 229 */     Map retval = null;
/* 230 */     COSBase ap = this.dictionary.getDictionaryObject(COSName.D);
/*     */     COSDictionary map;
/*     */     Map actuals;
/* 231 */     if (ap == null)
/*     */     {
/* 233 */       retval = getNormalAppearance();
/*     */     }
/*     */     else
/*     */     {
/* 237 */       if ((ap instanceof COSStream))
/*     */       {
/* 239 */         COSStream aux = (COSStream)ap;
/* 240 */         ap = new COSDictionary();
/* 241 */         ((COSDictionary)ap).setItem(COSName.DEFAULT, aux);
/*     */       }
/* 243 */       map = (COSDictionary)ap;
/* 244 */       actuals = new HashMap();
/*     */ 
/* 246 */       retval = new COSDictionaryMap(actuals, map);
/* 247 */       for (COSName asName : map.keySet())
/*     */       {
/* 249 */         COSBase stream = map.getDictionaryObject(asName);
/*     */ 
/* 252 */         if ((stream instanceof COSStream))
/*     */         {
/* 254 */           COSStream as = (COSStream)stream;
/* 255 */           actuals.put(asName.getName(), new PDAppearanceStream(as));
/*     */         }
/*     */         else
/*     */         {
/* 259 */           LOG.debug("non-conformance workaround: ignore null value for appearance stream.");
/*     */         }
/*     */       }
/*     */     }
/* 263 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setDownAppearance(Map<String, PDAppearanceStream> appearanceMap)
/*     */   {
/* 275 */     this.dictionary.setItem(COSName.D, COSDictionaryMap.convert(appearanceMap));
/*     */   }
/*     */ 
/*     */   public void setDownAppearance(PDAppearanceStream ap)
/*     */   {
/* 286 */     this.dictionary.setItem(COSName.D, ap.getStream());
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceDictionary
 * JD-Core Version:    0.6.2
 */