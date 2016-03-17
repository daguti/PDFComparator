/*     */ package com.itextpdf.text.pdf.fonts.otf;
/*     */ 
/*     */ import com.itextpdf.text.io.RandomAccessSourceFactory;
/*     */ import com.itextpdf.text.log.Logger;
/*     */ import com.itextpdf.text.log.LoggerFactory;
/*     */ import com.itextpdf.text.pdf.RandomAccessFileOrArray;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public abstract class OpenTypeFontTableReader
/*     */ {
/*  66 */   protected static final Logger LOG = LoggerFactory.getLogger(OpenTypeFontTableReader.class);
/*     */   protected final RandomAccessFileOrArray rf;
/*     */   protected final int tableLocation;
/*     */   private List<String> supportedLanguages;
/*     */ 
/*     */   public OpenTypeFontTableReader(String fontFilePath, int tableLocation)
/*     */     throws IOException
/*     */   {
/*  76 */     this.rf = new RandomAccessFileOrArray(new RandomAccessSourceFactory().createBestSource(fontFilePath));
/*     */ 
/*  78 */     this.tableLocation = tableLocation;
/*     */   }
/*     */ 
/*     */   public Language getSupportedLanguage() throws FontReadingException
/*     */   {
/*  83 */     Language[] allLangs = Language.values();
/*     */ 
/*  85 */     for (String supportedLang : this.supportedLanguages) {
/*  86 */       for (Language lang : allLangs) {
/*  87 */         if (lang.isSupported(supportedLang)) {
/*  88 */           return lang;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  93 */     throw new FontReadingException("Unsupported languages " + this.supportedLanguages);
/*     */   }
/*     */ 
/*     */   protected final void startReadingTable()
/*     */     throws FontReadingException
/*     */   {
/*     */     try
/*     */     {
/* 104 */       TableHeader header = readHeader();
/*     */ 
/* 106 */       readScriptListTable(this.tableLocation + header.scriptListOffset);
/*     */ 
/* 109 */       readFeatureListTable(this.tableLocation + header.featureListOffset);
/*     */ 
/* 112 */       readLookupListTable(this.tableLocation + header.lookupListOffset);
/*     */     } catch (IOException e) {
/* 114 */       throw new FontReadingException("Error reading font file", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected abstract void readSubTable(int paramInt1, int paramInt2)
/*     */     throws IOException;
/*     */ 
/*     */   private void readLookupListTable(int lookupListTableLocation) throws IOException
/*     */   {
/* 123 */     this.rf.seek(lookupListTableLocation);
/* 124 */     int lookupCount = this.rf.readShort();
/*     */ 
/* 126 */     List lookupTableOffsets = new ArrayList();
/*     */ 
/* 128 */     for (int i = 0; i < lookupCount; i++) {
/* 129 */       int lookupTableOffset = this.rf.readShort();
/* 130 */       lookupTableOffsets.add(Integer.valueOf(lookupTableOffset));
/*     */     }
/*     */ 
/* 134 */     for (int i = 0; i < lookupCount; i++)
/*     */     {
/* 136 */       int lookupTableOffset = ((Integer)lookupTableOffsets.get(i)).intValue();
/* 137 */       readLookupTable(lookupListTableLocation + lookupTableOffset);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void readLookupTable(int lookupTableLocation) throws IOException
/*     */   {
/* 143 */     this.rf.seek(lookupTableLocation);
/* 144 */     int lookupType = this.rf.readShort();
/*     */ 
/* 148 */     this.rf.skipBytes(2);
/*     */ 
/* 150 */     int subTableCount = this.rf.readShort();
/*     */ 
/* 153 */     List subTableOffsets = new ArrayList();
/*     */ 
/* 155 */     for (int i = 0; i < subTableCount; i++) {
/* 156 */       int subTableOffset = this.rf.readShort();
/* 157 */       subTableOffsets.add(Integer.valueOf(subTableOffset));
/*     */     }
/*     */ 
/* 160 */     for (Iterator i$ = subTableOffsets.iterator(); i$.hasNext(); ) { int subTableOffset = ((Integer)i$.next()).intValue();
/*     */ 
/* 162 */       readSubTable(lookupType, lookupTableLocation + subTableOffset); }
/*     */   }
/*     */ 
/*     */   protected final List<Integer> readCoverageFormat(int coverageLocation)
/*     */     throws IOException
/*     */   {
/* 168 */     this.rf.seek(coverageLocation);
/* 169 */     int coverageFormat = this.rf.readShort();
/*     */ 
/* 173 */     if (coverageFormat == 1) {
/* 174 */       int glyphCount = this.rf.readShort();
/*     */ 
/* 176 */       List glyphIds = new ArrayList(glyphCount);
/*     */ 
/* 178 */       for (int i = 0; i < glyphCount; i++) {
/* 179 */         int coverageGlyphId = this.rf.readShort();
/* 180 */         glyphIds.add(Integer.valueOf(coverageGlyphId));
/*     */       }
/*     */     }
/* 183 */     else if (coverageFormat == 2)
/*     */     {
/* 185 */       int rangeCount = this.rf.readShort();
/*     */ 
/* 187 */       List glyphIds = new ArrayList();
/*     */ 
/* 189 */       for (int i = 0; i < rangeCount; i++)
/* 190 */         readRangeRecord(glyphIds);
/*     */     }
/*     */     else
/*     */     {
/* 194 */       throw new UnsupportedOperationException("Invalid coverage format: " + coverageFormat);
/*     */     }
/*     */     List glyphIds;
/* 198 */     return Collections.unmodifiableList(glyphIds);
/*     */   }
/*     */ 
/*     */   private void readRangeRecord(List<Integer> glyphIds) throws IOException {
/* 202 */     int startGlyphId = this.rf.readShort();
/* 203 */     int endGlyphId = this.rf.readShort();
/* 204 */     int startCoverageIndex = this.rf.readShort();
/*     */ 
/* 206 */     for (int glyphId = startGlyphId; glyphId <= endGlyphId; glyphId++)
/* 207 */       glyphIds.add(Integer.valueOf(glyphId));
/*     */   }
/*     */ 
/*     */   private void readScriptListTable(int scriptListTableLocationOffset)
/*     */     throws IOException
/*     */   {
/* 220 */     this.rf.seek(scriptListTableLocationOffset);
/*     */ 
/* 222 */     int scriptCount = this.rf.readShort();
/*     */ 
/* 224 */     Map scriptRecords = new HashMap(scriptCount);
/*     */ 
/* 227 */     for (int i = 0; i < scriptCount; i++) {
/* 228 */       readScriptRecord(scriptListTableLocationOffset, scriptRecords);
/*     */     }
/*     */ 
/* 231 */     List supportedLanguages = new ArrayList(scriptCount);
/*     */ 
/* 233 */     for (String scriptName : scriptRecords.keySet()) {
/* 234 */       readScriptTable(((Integer)scriptRecords.get(scriptName)).intValue());
/* 235 */       supportedLanguages.add(scriptName);
/*     */     }
/*     */ 
/* 238 */     this.supportedLanguages = Collections.unmodifiableList(supportedLanguages);
/*     */   }
/*     */ 
/*     */   private void readScriptRecord(int scriptListTableLocationOffset, Map<String, Integer> scriptRecords) throws IOException
/*     */   {
/* 243 */     String scriptTag = this.rf.readString(4, "utf-8");
/*     */ 
/* 245 */     int scriptOffset = this.rf.readShort();
/*     */ 
/* 247 */     scriptRecords.put(scriptTag, Integer.valueOf(scriptListTableLocationOffset + scriptOffset));
/*     */   }
/*     */ 
/*     */   private void readScriptTable(int scriptTableLocationOffset)
/*     */     throws IOException
/*     */   {
/* 253 */     this.rf.seek(scriptTableLocationOffset);
/* 254 */     int defaultLangSys = this.rf.readShort();
/* 255 */     int langSysCount = this.rf.readShort();
/*     */     Map langSysRecords;
/* 257 */     if (langSysCount > 0) {
/* 258 */       langSysRecords = new LinkedHashMap(langSysCount);
/*     */ 
/* 261 */       for (int i = 0; i < langSysCount; i++) {
/* 262 */         readLangSysRecord(langSysRecords);
/*     */       }
/*     */ 
/* 266 */       for (String langSysTag : langSysRecords.keySet()) {
/* 267 */         readLangSysTable(scriptTableLocationOffset + ((Integer)langSysRecords.get(langSysTag)).intValue());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 273 */     readLangSysTable(scriptTableLocationOffset + defaultLangSys);
/*     */   }
/*     */ 
/*     */   private void readLangSysRecord(Map<String, Integer> langSysRecords) throws IOException
/*     */   {
/* 278 */     String langSysTag = this.rf.readString(4, "utf-8");
/* 279 */     int langSys = this.rf.readShort();
/* 280 */     langSysRecords.put(langSysTag, Integer.valueOf(langSys));
/*     */   }
/*     */ 
/*     */   private void readLangSysTable(int langSysTableLocationOffset) throws IOException
/*     */   {
/* 285 */     this.rf.seek(langSysTableLocationOffset);
/* 286 */     int lookupOrderOffset = this.rf.readShort();
/* 287 */     LOG.debug("lookupOrderOffset=" + lookupOrderOffset);
/* 288 */     int reqFeatureIndex = this.rf.readShort();
/* 289 */     LOG.debug("reqFeatureIndex=" + reqFeatureIndex);
/* 290 */     int featureCount = this.rf.readShort();
/*     */ 
/* 292 */     List featureListIndices = new ArrayList(featureCount);
/* 293 */     for (int i = 0; i < featureCount; i++) {
/* 294 */       featureListIndices.add(Short.valueOf(this.rf.readShort()));
/*     */     }
/*     */ 
/* 297 */     LOG.debug("featureListIndices=" + featureListIndices);
/*     */   }
/*     */ 
/*     */   private void readFeatureListTable(int featureListTableLocationOffset)
/*     */     throws IOException
/*     */   {
/* 303 */     this.rf.seek(featureListTableLocationOffset);
/* 304 */     int featureCount = this.rf.readShort();
/* 305 */     LOG.debug("featureCount=" + featureCount);
/*     */ 
/* 307 */     Map featureRecords = new LinkedHashMap(featureCount);
/*     */ 
/* 309 */     for (int i = 0; i < featureCount; i++) {
/* 310 */       featureRecords.put(this.rf.readString(4, "utf-8"), Short.valueOf(this.rf.readShort()));
/*     */     }
/*     */ 
/* 313 */     for (String featureName : featureRecords.keySet()) {
/* 314 */       LOG.debug("*************featureName=" + featureName);
/* 315 */       readFeatureTable(featureListTableLocationOffset + ((Short)featureRecords.get(featureName)).shortValue());
/*     */     }
/*     */   }
/*     */ 
/*     */   private void readFeatureTable(int featureTableLocationOffset)
/*     */     throws IOException
/*     */   {
/* 323 */     this.rf.seek(featureTableLocationOffset);
/* 324 */     int featureParamsOffset = this.rf.readShort();
/* 325 */     LOG.debug("featureParamsOffset=" + featureParamsOffset);
/*     */ 
/* 327 */     int lookupCount = this.rf.readShort();
/* 328 */     LOG.debug("lookupCount=" + lookupCount);
/*     */ 
/* 330 */     List lookupListIndices = new ArrayList(lookupCount);
/* 331 */     for (int i = 0; i < lookupCount; i++)
/* 332 */       lookupListIndices.add(Short.valueOf(this.rf.readShort()));
/*     */   }
/*     */ 
/*     */   private TableHeader readHeader()
/*     */     throws IOException
/*     */   {
/* 340 */     this.rf.seek(this.tableLocation);
/*     */ 
/* 342 */     int version = this.rf.readInt();
/*     */ 
/* 344 */     int scriptListOffset = this.rf.readUnsignedShort();
/* 345 */     int featureListOffset = this.rf.readUnsignedShort();
/* 346 */     int lookupListOffset = this.rf.readUnsignedShort();
/*     */ 
/* 353 */     TableHeader header = new TableHeader(version, scriptListOffset, featureListOffset, lookupListOffset);
/*     */ 
/* 356 */     return header;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.fonts.otf.OpenTypeFontTableReader
 * JD-Core Version:    0.6.2
 */