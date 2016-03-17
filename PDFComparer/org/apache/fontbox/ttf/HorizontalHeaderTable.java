/*     */ package org.apache.fontbox.ttf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ 
/*     */ public class HorizontalHeaderTable extends TTFTable
/*     */ {
/*     */   public static final String TAG = "hhea";
/*     */   private float version;
/*     */   private short ascender;
/*     */   private short descender;
/*     */   private short lineGap;
/*     */   private int advanceWidthMax;
/*     */   private short minLeftSideBearing;
/*     */   private short minRightSideBearing;
/*     */   private short xMaxExtent;
/*     */   private short caretSlopeRise;
/*     */   private short caretSlopeRun;
/*     */   private short reserved1;
/*     */   private short reserved2;
/*     */   private short reserved3;
/*     */   private short reserved4;
/*     */   private short reserved5;
/*     */   private short metricDataFormat;
/*     */   private int numberOfHMetrics;
/*     */ 
/*     */   public void initData(TrueTypeFont ttf, TTFDataStream data)
/*     */     throws IOException
/*     */   {
/*  60 */     this.version = data.read32Fixed();
/*  61 */     this.ascender = data.readSignedShort();
/*  62 */     this.descender = data.readSignedShort();
/*  63 */     this.lineGap = data.readSignedShort();
/*  64 */     this.advanceWidthMax = data.readUnsignedShort();
/*  65 */     this.minLeftSideBearing = data.readSignedShort();
/*  66 */     this.minRightSideBearing = data.readSignedShort();
/*  67 */     this.xMaxExtent = data.readSignedShort();
/*  68 */     this.caretSlopeRise = data.readSignedShort();
/*  69 */     this.caretSlopeRun = data.readSignedShort();
/*  70 */     this.reserved1 = data.readSignedShort();
/*  71 */     this.reserved2 = data.readSignedShort();
/*  72 */     this.reserved3 = data.readSignedShort();
/*  73 */     this.reserved4 = data.readSignedShort();
/*  74 */     this.reserved5 = data.readSignedShort();
/*  75 */     this.metricDataFormat = data.readSignedShort();
/*  76 */     this.numberOfHMetrics = data.readUnsignedShort();
/*  77 */     this.initialized = true;
/*     */   }
/*     */ 
/*     */   public int getAdvanceWidthMax()
/*     */   {
/*  85 */     return this.advanceWidthMax;
/*     */   }
/*     */ 
/*     */   public void setAdvanceWidthMax(int advanceWidthMaxValue)
/*     */   {
/*  92 */     this.advanceWidthMax = advanceWidthMaxValue;
/*     */   }
/*     */ 
/*     */   public short getAscender()
/*     */   {
/*  99 */     return this.ascender;
/*     */   }
/*     */ 
/*     */   public void setAscender(short ascenderValue)
/*     */   {
/* 106 */     this.ascender = ascenderValue;
/*     */   }
/*     */ 
/*     */   public short getCaretSlopeRise()
/*     */   {
/* 113 */     return this.caretSlopeRise;
/*     */   }
/*     */ 
/*     */   public void setCaretSlopeRise(short caretSlopeRiseValue)
/*     */   {
/* 120 */     this.caretSlopeRise = caretSlopeRiseValue;
/*     */   }
/*     */ 
/*     */   public short getCaretSlopeRun()
/*     */   {
/* 127 */     return this.caretSlopeRun;
/*     */   }
/*     */ 
/*     */   public void setCaretSlopeRun(short caretSlopeRunValue)
/*     */   {
/* 134 */     this.caretSlopeRun = caretSlopeRunValue;
/*     */   }
/*     */ 
/*     */   public short getDescender()
/*     */   {
/* 141 */     return this.descender;
/*     */   }
/*     */ 
/*     */   public void setDescender(short descenderValue)
/*     */   {
/* 148 */     this.descender = descenderValue;
/*     */   }
/*     */ 
/*     */   public short getLineGap()
/*     */   {
/* 155 */     return this.lineGap;
/*     */   }
/*     */ 
/*     */   public void setLineGap(short lineGapValue)
/*     */   {
/* 162 */     this.lineGap = lineGapValue;
/*     */   }
/*     */ 
/*     */   public short getMetricDataFormat()
/*     */   {
/* 169 */     return this.metricDataFormat;
/*     */   }
/*     */ 
/*     */   public void setMetricDataFormat(short metricDataFormatValue)
/*     */   {
/* 176 */     this.metricDataFormat = metricDataFormatValue;
/*     */   }
/*     */ 
/*     */   public short getMinLeftSideBearing()
/*     */   {
/* 183 */     return this.minLeftSideBearing;
/*     */   }
/*     */ 
/*     */   public void setMinLeftSideBearing(short minLeftSideBearingValue)
/*     */   {
/* 190 */     this.minLeftSideBearing = minLeftSideBearingValue;
/*     */   }
/*     */ 
/*     */   public short getMinRightSideBearing()
/*     */   {
/* 197 */     return this.minRightSideBearing;
/*     */   }
/*     */ 
/*     */   public void setMinRightSideBearing(short minRightSideBearingValue)
/*     */   {
/* 204 */     this.minRightSideBearing = minRightSideBearingValue;
/*     */   }
/*     */ 
/*     */   public int getNumberOfHMetrics()
/*     */   {
/* 211 */     return this.numberOfHMetrics;
/*     */   }
/*     */ 
/*     */   public void setNumberOfHMetrics(int numberOfHMetricsValue)
/*     */   {
/* 218 */     this.numberOfHMetrics = numberOfHMetricsValue;
/*     */   }
/*     */ 
/*     */   public short getReserved1()
/*     */   {
/* 225 */     return this.reserved1;
/*     */   }
/*     */ 
/*     */   public void setReserved1(short reserved1Value)
/*     */   {
/* 232 */     this.reserved1 = reserved1Value;
/*     */   }
/*     */ 
/*     */   public short getReserved2()
/*     */   {
/* 239 */     return this.reserved2;
/*     */   }
/*     */ 
/*     */   public void setReserved2(short reserved2Value)
/*     */   {
/* 246 */     this.reserved2 = reserved2Value;
/*     */   }
/*     */ 
/*     */   public short getReserved3()
/*     */   {
/* 253 */     return this.reserved3;
/*     */   }
/*     */ 
/*     */   public void setReserved3(short reserved3Value)
/*     */   {
/* 260 */     this.reserved3 = reserved3Value;
/*     */   }
/*     */ 
/*     */   public short getReserved4()
/*     */   {
/* 267 */     return this.reserved4;
/*     */   }
/*     */ 
/*     */   public void setReserved4(short reserved4Value)
/*     */   {
/* 274 */     this.reserved4 = reserved4Value;
/*     */   }
/*     */ 
/*     */   public short getReserved5()
/*     */   {
/* 281 */     return this.reserved5;
/*     */   }
/*     */ 
/*     */   public void setReserved5(short reserved5Value)
/*     */   {
/* 288 */     this.reserved5 = reserved5Value;
/*     */   }
/*     */ 
/*     */   public float getVersion()
/*     */   {
/* 295 */     return this.version;
/*     */   }
/*     */ 
/*     */   public void setVersion(float versionValue)
/*     */   {
/* 302 */     this.version = versionValue;
/*     */   }
/*     */ 
/*     */   public short getXMaxExtent()
/*     */   {
/* 309 */     return this.xMaxExtent;
/*     */   }
/*     */ 
/*     */   public void setXMaxExtent(short maxExtentValue)
/*     */   {
/* 316 */     this.xMaxExtent = maxExtentValue;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.ttf.HorizontalHeaderTable
 * JD-Core Version:    0.6.2
 */