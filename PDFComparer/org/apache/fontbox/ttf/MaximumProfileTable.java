/*     */ package org.apache.fontbox.ttf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ 
/*     */ public class MaximumProfileTable extends TTFTable
/*     */ {
/*     */   public static final String TAG = "maxp";
/*     */   private float version;
/*     */   private int numGlyphs;
/*     */   private int maxPoints;
/*     */   private int maxContours;
/*     */   private int maxCompositePoints;
/*     */   private int maxCompositeContours;
/*     */   private int maxZones;
/*     */   private int maxTwilightPoints;
/*     */   private int maxStorage;
/*     */   private int maxFunctionDefs;
/*     */   private int maxInstructionDefs;
/*     */   private int maxStackElements;
/*     */   private int maxSizeOfInstructions;
/*     */   private int maxComponentElements;
/*     */   private int maxComponentDepth;
/*     */ 
/*     */   public int getMaxComponentDepth()
/*     */   {
/*  54 */     return this.maxComponentDepth;
/*     */   }
/*     */ 
/*     */   public void setMaxComponentDepth(int maxComponentDepthValue)
/*     */   {
/*  61 */     this.maxComponentDepth = maxComponentDepthValue;
/*     */   }
/*     */ 
/*     */   public int getMaxComponentElements()
/*     */   {
/*  68 */     return this.maxComponentElements;
/*     */   }
/*     */ 
/*     */   public void setMaxComponentElements(int maxComponentElementsValue)
/*     */   {
/*  75 */     this.maxComponentElements = maxComponentElementsValue;
/*     */   }
/*     */ 
/*     */   public int getMaxCompositeContours()
/*     */   {
/*  82 */     return this.maxCompositeContours;
/*     */   }
/*     */ 
/*     */   public void setMaxCompositeContours(int maxCompositeContoursValue)
/*     */   {
/*  89 */     this.maxCompositeContours = maxCompositeContoursValue;
/*     */   }
/*     */ 
/*     */   public int getMaxCompositePoints()
/*     */   {
/*  96 */     return this.maxCompositePoints;
/*     */   }
/*     */ 
/*     */   public void setMaxCompositePoints(int maxCompositePointsValue)
/*     */   {
/* 103 */     this.maxCompositePoints = maxCompositePointsValue;
/*     */   }
/*     */ 
/*     */   public int getMaxContours()
/*     */   {
/* 110 */     return this.maxContours;
/*     */   }
/*     */ 
/*     */   public void setMaxContours(int maxContoursValue)
/*     */   {
/* 117 */     this.maxContours = maxContoursValue;
/*     */   }
/*     */ 
/*     */   public int getMaxFunctionDefs()
/*     */   {
/* 124 */     return this.maxFunctionDefs;
/*     */   }
/*     */ 
/*     */   public void setMaxFunctionDefs(int maxFunctionDefsValue)
/*     */   {
/* 131 */     this.maxFunctionDefs = maxFunctionDefsValue;
/*     */   }
/*     */ 
/*     */   public int getMaxInstructionDefs()
/*     */   {
/* 138 */     return this.maxInstructionDefs;
/*     */   }
/*     */ 
/*     */   public void setMaxInstructionDefs(int maxInstructionDefsValue)
/*     */   {
/* 145 */     this.maxInstructionDefs = maxInstructionDefsValue;
/*     */   }
/*     */ 
/*     */   public int getMaxPoints()
/*     */   {
/* 152 */     return this.maxPoints;
/*     */   }
/*     */ 
/*     */   public void setMaxPoints(int maxPointsValue)
/*     */   {
/* 159 */     this.maxPoints = maxPointsValue;
/*     */   }
/*     */ 
/*     */   public int getMaxSizeOfInstructions()
/*     */   {
/* 166 */     return this.maxSizeOfInstructions;
/*     */   }
/*     */ 
/*     */   public void setMaxSizeOfInstructions(int maxSizeOfInstructionsValue)
/*     */   {
/* 173 */     this.maxSizeOfInstructions = maxSizeOfInstructionsValue;
/*     */   }
/*     */ 
/*     */   public int getMaxStackElements()
/*     */   {
/* 180 */     return this.maxStackElements;
/*     */   }
/*     */ 
/*     */   public void setMaxStackElements(int maxStackElementsValue)
/*     */   {
/* 187 */     this.maxStackElements = maxStackElementsValue;
/*     */   }
/*     */ 
/*     */   public int getMaxStorage()
/*     */   {
/* 194 */     return this.maxStorage;
/*     */   }
/*     */ 
/*     */   public void setMaxStorage(int maxStorageValue)
/*     */   {
/* 201 */     this.maxStorage = maxStorageValue;
/*     */   }
/*     */ 
/*     */   public int getMaxTwilightPoints()
/*     */   {
/* 208 */     return this.maxTwilightPoints;
/*     */   }
/*     */ 
/*     */   public void setMaxTwilightPoints(int maxTwilightPointsValue)
/*     */   {
/* 215 */     this.maxTwilightPoints = maxTwilightPointsValue;
/*     */   }
/*     */ 
/*     */   public int getMaxZones()
/*     */   {
/* 222 */     return this.maxZones;
/*     */   }
/*     */ 
/*     */   public void setMaxZones(int maxZonesValue)
/*     */   {
/* 229 */     this.maxZones = maxZonesValue;
/*     */   }
/*     */ 
/*     */   public int getNumGlyphs()
/*     */   {
/* 236 */     return this.numGlyphs;
/*     */   }
/*     */ 
/*     */   public void setNumGlyphs(int numGlyphsValue)
/*     */   {
/* 243 */     this.numGlyphs = numGlyphsValue;
/*     */   }
/*     */ 
/*     */   public float getVersion()
/*     */   {
/* 250 */     return this.version;
/*     */   }
/*     */ 
/*     */   public void setVersion(float versionValue)
/*     */   {
/* 257 */     this.version = versionValue;
/*     */   }
/*     */ 
/*     */   public void initData(TrueTypeFont ttf, TTFDataStream data)
/*     */     throws IOException
/*     */   {
/* 269 */     this.version = data.read32Fixed();
/* 270 */     this.numGlyphs = data.readUnsignedShort();
/* 271 */     this.maxPoints = data.readUnsignedShort();
/* 272 */     this.maxContours = data.readUnsignedShort();
/* 273 */     this.maxCompositePoints = data.readUnsignedShort();
/* 274 */     this.maxCompositeContours = data.readUnsignedShort();
/* 275 */     this.maxZones = data.readUnsignedShort();
/* 276 */     this.maxTwilightPoints = data.readUnsignedShort();
/* 277 */     this.maxStorage = data.readUnsignedShort();
/* 278 */     this.maxFunctionDefs = data.readUnsignedShort();
/* 279 */     this.maxInstructionDefs = data.readUnsignedShort();
/* 280 */     this.maxStackElements = data.readUnsignedShort();
/* 281 */     this.maxSizeOfInstructions = data.readUnsignedShort();
/* 282 */     this.maxComponentElements = data.readUnsignedShort();
/* 283 */     this.maxComponentDepth = data.readUnsignedShort();
/* 284 */     this.initialized = true;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.ttf.MaximumProfileTable
 * JD-Core Version:    0.6.2
 */