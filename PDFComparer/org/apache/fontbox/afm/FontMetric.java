/*     */ package org.apache.fontbox.afm;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.fontbox.util.BoundingBox;
/*     */ 
/*     */ public class FontMetric
/*     */ {
/*     */   private float afmVersion;
/*  42 */   private int metricSets = 0;
/*     */   private String fontName;
/*     */   private String fullName;
/*     */   private String familyName;
/*     */   private String weight;
/*     */   private BoundingBox fontBBox;
/*     */   private String fontVersion;
/*     */   private String notice;
/*     */   private String encodingScheme;
/*     */   private int mappingScheme;
/*     */   private int escChar;
/*     */   private String characterSet;
/*     */   private int characters;
/*     */   private boolean isBaseFont;
/*     */   private float[] vVector;
/*     */   private boolean isFixedV;
/*     */   private float capHeight;
/*     */   private float xHeight;
/*     */   private float ascender;
/*     */   private float descender;
/*  62 */   private List<String> comments = new ArrayList();
/*     */   private float underlinePosition;
/*     */   private float underlineThickness;
/*     */   private float italicAngle;
/*     */   private float[] charWidth;
/*     */   private boolean isFixedPitch;
/*     */   private float standardHorizontalWidth;
/*     */   private float standardVerticalWidth;
/*  72 */   private List<CharMetric> charMetrics = new ArrayList();
/*  73 */   private Map<String, CharMetric> charMetricsMap = new HashMap();
/*  74 */   private List<TrackKern> trackKern = new ArrayList();
/*  75 */   private List<Composite> composites = new ArrayList();
/*  76 */   private List<KernPair> kernPairs = new ArrayList();
/*  77 */   private List<KernPair> kernPairs0 = new ArrayList();
/*  78 */   private List<KernPair> kernPairs1 = new ArrayList();
/*     */ 
/*     */   public float getCharacterWidth(String name)
/*     */     throws IOException
/*     */   {
/*  98 */     float result = 0.0F;
/*  99 */     CharMetric metric = (CharMetric)this.charMetricsMap.get(name);
/* 100 */     if (metric == null)
/*     */     {
/* 102 */       result = 0.0F;
/*     */     }
/*     */     else
/*     */     {
/* 108 */       result = metric.getWx();
/*     */     }
/* 110 */     return result;
/*     */   }
/*     */ 
/*     */   public float getCharacterHeight(String name)
/*     */     throws IOException
/*     */   {
/* 124 */     float result = 0.0F;
/* 125 */     CharMetric metric = (CharMetric)this.charMetricsMap.get(name);
/* 126 */     if (metric == null)
/*     */     {
/* 128 */       result = 0.0F;
/*     */     }
/* 134 */     else if (metric.getWy() == 0.0F)
/*     */     {
/* 136 */       result = metric.getBoundingBox().getHeight();
/*     */     }
/*     */     else
/*     */     {
/* 140 */       result = metric.getWy();
/*     */     }
/*     */ 
/* 143 */     return result;
/*     */   }
/*     */ 
/*     */   public float getAverageCharacterWidth()
/*     */     throws IOException
/*     */   {
/* 156 */     float average = 0.0F;
/* 157 */     float totalWidths = 0.0F;
/* 158 */     float characterCount = 0.0F;
/* 159 */     Iterator iter = this.charMetricsMap.values().iterator();
/* 160 */     while (iter.hasNext())
/*     */     {
/* 162 */       CharMetric metric = (CharMetric)iter.next();
/* 163 */       if (metric.getWx() > 0.0F)
/*     */       {
/* 165 */         totalWidths += metric.getWx();
/* 166 */         characterCount += 1.0F;
/*     */       }
/*     */     }
/* 169 */     if (totalWidths > 0.0F)
/*     */     {
/* 171 */       average = totalWidths / characterCount;
/*     */     }
/*     */ 
/* 174 */     return average;
/*     */   }
/*     */ 
/*     */   public void addComment(String comment)
/*     */   {
/* 184 */     this.comments.add(comment);
/*     */   }
/*     */ 
/*     */   public List<String> getComments()
/*     */   {
/* 194 */     return this.comments;
/*     */   }
/*     */ 
/*     */   public float getAFMVersion()
/*     */   {
/* 204 */     return this.afmVersion;
/*     */   }
/*     */ 
/*     */   public int getMetricSets()
/*     */   {
/* 214 */     return this.metricSets;
/*     */   }
/*     */ 
/*     */   public void setAFMVersion(float afmVersionValue)
/*     */   {
/* 224 */     this.afmVersion = afmVersionValue;
/*     */   }
/*     */ 
/*     */   public void setMetricSets(int metricSetsValue)
/*     */   {
/* 234 */     if ((metricSetsValue < 0) || (metricSetsValue > 2))
/*     */     {
/* 236 */       throw new RuntimeException("The metricSets attribute must be in the set {0,1,2} and not '" + metricSetsValue + "'");
/*     */     }
/*     */ 
/* 239 */     this.metricSets = metricSetsValue;
/*     */   }
/*     */ 
/*     */   public String getFontName()
/*     */   {
/* 249 */     return this.fontName;
/*     */   }
/*     */ 
/*     */   public void setFontName(String name)
/*     */   {
/* 259 */     this.fontName = name;
/*     */   }
/*     */ 
/*     */   public String getFullName()
/*     */   {
/* 269 */     return this.fullName;
/*     */   }
/*     */ 
/*     */   public void setFullName(String fullNameValue)
/*     */   {
/* 279 */     this.fullName = fullNameValue;
/*     */   }
/*     */ 
/*     */   public String getFamilyName()
/*     */   {
/* 289 */     return this.familyName;
/*     */   }
/*     */ 
/*     */   public void setFamilyName(String familyNameValue)
/*     */   {
/* 299 */     this.familyName = familyNameValue;
/*     */   }
/*     */ 
/*     */   public String getWeight()
/*     */   {
/* 309 */     return this.weight;
/*     */   }
/*     */ 
/*     */   public void setWeight(String weightValue)
/*     */   {
/* 319 */     this.weight = weightValue;
/*     */   }
/*     */ 
/*     */   public BoundingBox getFontBBox()
/*     */   {
/* 329 */     return this.fontBBox;
/*     */   }
/*     */ 
/*     */   public void setFontBBox(BoundingBox bBox)
/*     */   {
/* 339 */     this.fontBBox = bBox;
/*     */   }
/*     */ 
/*     */   public String getNotice()
/*     */   {
/* 349 */     return this.notice;
/*     */   }
/*     */ 
/*     */   public void setNotice(String noticeValue)
/*     */   {
/* 359 */     this.notice = noticeValue;
/*     */   }
/*     */ 
/*     */   public String getEncodingScheme()
/*     */   {
/* 369 */     return this.encodingScheme;
/*     */   }
/*     */ 
/*     */   public void setEncodingScheme(String encodingSchemeValue)
/*     */   {
/* 379 */     this.encodingScheme = encodingSchemeValue;
/*     */   }
/*     */ 
/*     */   public int getMappingScheme()
/*     */   {
/* 389 */     return this.mappingScheme;
/*     */   }
/*     */ 
/*     */   public void setMappingScheme(int mappingSchemeValue)
/*     */   {
/* 399 */     this.mappingScheme = mappingSchemeValue;
/*     */   }
/*     */ 
/*     */   public int getEscChar()
/*     */   {
/* 409 */     return this.escChar;
/*     */   }
/*     */ 
/*     */   public void setEscChar(int escCharValue)
/*     */   {
/* 419 */     this.escChar = escCharValue;
/*     */   }
/*     */ 
/*     */   public String getCharacterSet()
/*     */   {
/* 429 */     return this.characterSet;
/*     */   }
/*     */ 
/*     */   public void setCharacterSet(String characterSetValue)
/*     */   {
/* 439 */     this.characterSet = characterSetValue;
/*     */   }
/*     */ 
/*     */   public int getCharacters()
/*     */   {
/* 449 */     return this.characters;
/*     */   }
/*     */ 
/*     */   public void setCharacters(int charactersValue)
/*     */   {
/* 459 */     this.characters = charactersValue;
/*     */   }
/*     */ 
/*     */   public boolean isBaseFont()
/*     */   {
/* 469 */     return this.isBaseFont;
/*     */   }
/*     */ 
/*     */   public void setIsBaseFont(boolean isBaseFontValue)
/*     */   {
/* 479 */     this.isBaseFont = isBaseFontValue;
/*     */   }
/*     */ 
/*     */   public float[] getVVector()
/*     */   {
/* 489 */     return this.vVector;
/*     */   }
/*     */ 
/*     */   public void setVVector(float[] vVectorValue)
/*     */   {
/* 499 */     this.vVector = vVectorValue;
/*     */   }
/*     */ 
/*     */   public boolean isFixedV()
/*     */   {
/* 509 */     return this.isFixedV;
/*     */   }
/*     */ 
/*     */   public void setIsFixedV(boolean isFixedVValue)
/*     */   {
/* 519 */     this.isFixedV = isFixedVValue;
/*     */   }
/*     */ 
/*     */   public float getCapHeight()
/*     */   {
/* 529 */     return this.capHeight;
/*     */   }
/*     */ 
/*     */   public void setCapHeight(float capHeightValue)
/*     */   {
/* 539 */     this.capHeight = capHeightValue;
/*     */   }
/*     */ 
/*     */   public float getXHeight()
/*     */   {
/* 549 */     return this.xHeight;
/*     */   }
/*     */ 
/*     */   public void setXHeight(float xHeightValue)
/*     */   {
/* 559 */     this.xHeight = xHeightValue;
/*     */   }
/*     */ 
/*     */   public float getAscender()
/*     */   {
/* 569 */     return this.ascender;
/*     */   }
/*     */ 
/*     */   public void setAscender(float ascenderValue)
/*     */   {
/* 579 */     this.ascender = ascenderValue;
/*     */   }
/*     */ 
/*     */   public float getDescender()
/*     */   {
/* 589 */     return this.descender;
/*     */   }
/*     */ 
/*     */   public void setDescender(float descenderValue)
/*     */   {
/* 599 */     this.descender = descenderValue;
/*     */   }
/*     */ 
/*     */   public String getFontVersion()
/*     */   {
/* 609 */     return this.fontVersion;
/*     */   }
/*     */ 
/*     */   public void setFontVersion(String fontVersionValue)
/*     */   {
/* 619 */     this.fontVersion = fontVersionValue;
/*     */   }
/*     */ 
/*     */   public float getUnderlinePosition()
/*     */   {
/* 629 */     return this.underlinePosition;
/*     */   }
/*     */ 
/*     */   public void setUnderlinePosition(float underlinePositionValue)
/*     */   {
/* 639 */     this.underlinePosition = underlinePositionValue;
/*     */   }
/*     */ 
/*     */   public float getUnderlineThickness()
/*     */   {
/* 649 */     return this.underlineThickness;
/*     */   }
/*     */ 
/*     */   public void setUnderlineThickness(float underlineThicknessValue)
/*     */   {
/* 659 */     this.underlineThickness = underlineThicknessValue;
/*     */   }
/*     */ 
/*     */   public float getItalicAngle()
/*     */   {
/* 669 */     return this.italicAngle;
/*     */   }
/*     */ 
/*     */   public void setItalicAngle(float italicAngleValue)
/*     */   {
/* 679 */     this.italicAngle = italicAngleValue;
/*     */   }
/*     */ 
/*     */   public float[] getCharWidth()
/*     */   {
/* 689 */     return this.charWidth;
/*     */   }
/*     */ 
/*     */   public void setCharWidth(float[] charWidthValue)
/*     */   {
/* 699 */     this.charWidth = charWidthValue;
/*     */   }
/*     */ 
/*     */   public boolean isFixedPitch()
/*     */   {
/* 709 */     return this.isFixedPitch;
/*     */   }
/*     */ 
/*     */   public void setFixedPitch(boolean isFixedPitchValue)
/*     */   {
/* 719 */     this.isFixedPitch = isFixedPitchValue;
/*     */   }
/*     */ 
/*     */   public List<CharMetric> getCharMetrics()
/*     */   {
/* 727 */     return this.charMetrics;
/*     */   }
/*     */ 
/*     */   public void setCharMetrics(List<CharMetric> charMetricsValue)
/*     */   {
/* 735 */     this.charMetrics = charMetricsValue;
/*     */   }
/*     */ 
/*     */   public void addCharMetric(CharMetric metric)
/*     */   {
/* 745 */     this.charMetrics.add(metric);
/* 746 */     this.charMetricsMap.put(metric.getName(), metric);
/*     */   }
/*     */ 
/*     */   public List<TrackKern> getTrackKern()
/*     */   {
/* 754 */     return this.trackKern;
/*     */   }
/*     */ 
/*     */   public void setTrackKern(List<TrackKern> trackKernValue)
/*     */   {
/* 762 */     this.trackKern = trackKernValue;
/*     */   }
/*     */ 
/*     */   public void addTrackKern(TrackKern kern)
/*     */   {
/* 772 */     this.trackKern.add(kern);
/*     */   }
/*     */ 
/*     */   public List<Composite> getComposites()
/*     */   {
/* 780 */     return this.composites;
/*     */   }
/*     */ 
/*     */   public void setComposites(List<Composite> compositesList)
/*     */   {
/* 788 */     this.composites = compositesList;
/*     */   }
/*     */ 
/*     */   public void addComposite(Composite composite)
/*     */   {
/* 798 */     this.composites.add(composite);
/*     */   }
/*     */ 
/*     */   public List<KernPair> getKernPairs()
/*     */   {
/* 806 */     return this.kernPairs;
/*     */   }
/*     */ 
/*     */   public void addKernPair(KernPair kernPair)
/*     */   {
/* 816 */     this.kernPairs.add(kernPair);
/*     */   }
/*     */ 
/*     */   public void setKernPairs(List<KernPair> kernPairsList)
/*     */   {
/* 824 */     this.kernPairs = kernPairsList;
/*     */   }
/*     */ 
/*     */   public List<KernPair> getKernPairs0()
/*     */   {
/* 832 */     return this.kernPairs0;
/*     */   }
/*     */ 
/*     */   public void addKernPair0(KernPair kernPair)
/*     */   {
/* 842 */     this.kernPairs0.add(kernPair);
/*     */   }
/*     */ 
/*     */   public void setKernPairs0(List<KernPair> kernPairs0List)
/*     */   {
/* 850 */     this.kernPairs0 = kernPairs0List;
/*     */   }
/*     */ 
/*     */   public List<KernPair> getKernPairs1()
/*     */   {
/* 858 */     return this.kernPairs1;
/*     */   }
/*     */ 
/*     */   public void addKernPair1(KernPair kernPair)
/*     */   {
/* 868 */     this.kernPairs1.add(kernPair);
/*     */   }
/*     */ 
/*     */   public void setKernPairs1(List<KernPair> kernPairs1List)
/*     */   {
/* 876 */     this.kernPairs1 = kernPairs1List;
/*     */   }
/*     */ 
/*     */   public float getStandardHorizontalWidth()
/*     */   {
/* 884 */     return this.standardHorizontalWidth;
/*     */   }
/*     */ 
/*     */   public void setStandardHorizontalWidth(float standardHorizontalWidthValue)
/*     */   {
/* 892 */     this.standardHorizontalWidth = standardHorizontalWidthValue;
/*     */   }
/*     */ 
/*     */   public float getStandardVerticalWidth()
/*     */   {
/* 900 */     return this.standardVerticalWidth;
/*     */   }
/*     */ 
/*     */   public void setStandardVerticalWidth(float standardVerticalWidthValue)
/*     */   {
/* 908 */     this.standardVerticalWidth = standardVerticalWidthValue;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.afm.FontMetric
 * JD-Core Version:    0.6.2
 */