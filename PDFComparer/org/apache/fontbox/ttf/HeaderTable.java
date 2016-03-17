/*     */ package org.apache.fontbox.ttf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Calendar;
/*     */ 
/*     */ public class HeaderTable extends TTFTable
/*     */ {
/*     */   public static final String TAG = "head";
/*     */   private float version;
/*     */   private float fontRevision;
/*     */   private long checkSumAdjustment;
/*     */   private long magicNumber;
/*     */   private int flags;
/*     */   private int unitsPerEm;
/*     */   private Calendar created;
/*     */   private Calendar modified;
/*     */   private short xMin;
/*     */   private short yMin;
/*     */   private short xMax;
/*     */   private short yMax;
/*     */   private int macStyle;
/*     */   private int lowestRecPPEM;
/*     */   private short fontDirectionHint;
/*     */   private short indexToLocFormat;
/*     */   private short glyphDataFormat;
/*     */ 
/*     */   public void initData(TrueTypeFont ttf, TTFDataStream data)
/*     */     throws IOException
/*     */   {
/*  61 */     this.version = data.read32Fixed();
/*  62 */     this.fontRevision = data.read32Fixed();
/*  63 */     this.checkSumAdjustment = data.readUnsignedInt();
/*  64 */     this.magicNumber = data.readUnsignedInt();
/*  65 */     this.flags = data.readUnsignedShort();
/*  66 */     this.unitsPerEm = data.readUnsignedShort();
/*  67 */     this.created = data.readInternationalDate();
/*  68 */     this.modified = data.readInternationalDate();
/*  69 */     this.xMin = data.readSignedShort();
/*  70 */     this.yMin = data.readSignedShort();
/*  71 */     this.xMax = data.readSignedShort();
/*  72 */     this.yMax = data.readSignedShort();
/*  73 */     this.macStyle = data.readUnsignedShort();
/*  74 */     this.lowestRecPPEM = data.readUnsignedShort();
/*  75 */     this.fontDirectionHint = data.readSignedShort();
/*  76 */     this.indexToLocFormat = data.readSignedShort();
/*  77 */     this.glyphDataFormat = data.readSignedShort();
/*  78 */     this.initialized = true;
/*     */   }
/*     */ 
/*     */   public long getCheckSumAdjustment()
/*     */   {
/*  85 */     return this.checkSumAdjustment;
/*     */   }
/*     */ 
/*     */   public void setCheckSumAdjustment(long checkSumAdjustmentValue)
/*     */   {
/*  92 */     this.checkSumAdjustment = checkSumAdjustmentValue;
/*     */   }
/*     */ 
/*     */   public Calendar getCreated()
/*     */   {
/*  99 */     return this.created;
/*     */   }
/*     */ 
/*     */   public void setCreated(Calendar createdValue)
/*     */   {
/* 106 */     this.created = createdValue;
/*     */   }
/*     */ 
/*     */   public int getFlags()
/*     */   {
/* 113 */     return this.flags;
/*     */   }
/*     */ 
/*     */   public void setFlags(int flagsValue)
/*     */   {
/* 120 */     this.flags = flagsValue;
/*     */   }
/*     */ 
/*     */   public short getFontDirectionHint()
/*     */   {
/* 127 */     return this.fontDirectionHint;
/*     */   }
/*     */ 
/*     */   public void setFontDirectionHint(short fontDirectionHintValue)
/*     */   {
/* 134 */     this.fontDirectionHint = fontDirectionHintValue;
/*     */   }
/*     */ 
/*     */   public float getFontRevision()
/*     */   {
/* 141 */     return this.fontRevision;
/*     */   }
/*     */ 
/*     */   public void setFontRevision(float fontRevisionValue)
/*     */   {
/* 148 */     this.fontRevision = fontRevisionValue;
/*     */   }
/*     */ 
/*     */   public short getGlyphDataFormat()
/*     */   {
/* 155 */     return this.glyphDataFormat;
/*     */   }
/*     */ 
/*     */   public void setGlyphDataFormat(short glyphDataFormatValue)
/*     */   {
/* 162 */     this.glyphDataFormat = glyphDataFormatValue;
/*     */   }
/*     */ 
/*     */   public short getIndexToLocFormat()
/*     */   {
/* 169 */     return this.indexToLocFormat;
/*     */   }
/*     */ 
/*     */   public void setIndexToLocFormat(short indexToLocFormatValue)
/*     */   {
/* 176 */     this.indexToLocFormat = indexToLocFormatValue;
/*     */   }
/*     */ 
/*     */   public int getLowestRecPPEM()
/*     */   {
/* 183 */     return this.lowestRecPPEM;
/*     */   }
/*     */ 
/*     */   public void setLowestRecPPEM(int lowestRecPPEMValue)
/*     */   {
/* 190 */     this.lowestRecPPEM = lowestRecPPEMValue;
/*     */   }
/*     */ 
/*     */   public int getMacStyle()
/*     */   {
/* 197 */     return this.macStyle;
/*     */   }
/*     */ 
/*     */   public void setMacStyle(int macStyleValue)
/*     */   {
/* 204 */     this.macStyle = macStyleValue;
/*     */   }
/*     */ 
/*     */   public long getMagicNumber()
/*     */   {
/* 211 */     return this.magicNumber;
/*     */   }
/*     */ 
/*     */   public void setMagicNumber(long magicNumberValue)
/*     */   {
/* 218 */     this.magicNumber = magicNumberValue;
/*     */   }
/*     */ 
/*     */   public Calendar getModified()
/*     */   {
/* 225 */     return this.modified;
/*     */   }
/*     */ 
/*     */   public void setModified(Calendar modifiedValue)
/*     */   {
/* 232 */     this.modified = modifiedValue;
/*     */   }
/*     */ 
/*     */   public int getUnitsPerEm()
/*     */   {
/* 239 */     return this.unitsPerEm;
/*     */   }
/*     */ 
/*     */   public void setUnitsPerEm(int unitsPerEmValue)
/*     */   {
/* 246 */     this.unitsPerEm = unitsPerEmValue;
/*     */   }
/*     */ 
/*     */   public float getVersion()
/*     */   {
/* 253 */     return this.version;
/*     */   }
/*     */ 
/*     */   public void setVersion(float versionValue)
/*     */   {
/* 260 */     this.version = versionValue;
/*     */   }
/*     */ 
/*     */   public short getXMax()
/*     */   {
/* 267 */     return this.xMax;
/*     */   }
/*     */ 
/*     */   public void setXMax(short maxValue)
/*     */   {
/* 274 */     this.xMax = maxValue;
/*     */   }
/*     */ 
/*     */   public short getXMin()
/*     */   {
/* 281 */     return this.xMin;
/*     */   }
/*     */ 
/*     */   public void setXMin(short minValue)
/*     */   {
/* 288 */     this.xMin = minValue;
/*     */   }
/*     */ 
/*     */   public short getYMax()
/*     */   {
/* 295 */     return this.yMax;
/*     */   }
/*     */ 
/*     */   public void setYMax(short maxValue)
/*     */   {
/* 302 */     this.yMax = maxValue;
/*     */   }
/*     */ 
/*     */   public short getYMin()
/*     */   {
/* 309 */     return this.yMin;
/*     */   }
/*     */ 
/*     */   public void setYMin(short minValue)
/*     */   {
/* 316 */     this.yMin = minValue;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.ttf.HeaderTable
 * JD-Core Version:    0.6.2
 */