/*     */ package org.apache.fontbox.ttf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.apache.fontbox.encoding.Encoding;
/*     */ 
/*     */ public class PostScriptTable extends TTFTable
/*     */ {
/*     */   private float formatType;
/*     */   private float italicAngle;
/*     */   private short underlinePosition;
/*     */   private short underlineThickness;
/*     */   private long isFixedPitch;
/*     */   private long minMemType42;
/*     */   private long maxMemType42;
/*     */   private long mimMemType1;
/*     */   private long maxMemType1;
/*  40 */   private String[] glyphNames = null;
/*     */   public static final String TAG = "post";
/*     */ 
/*     */   public void initData(TrueTypeFont ttf, TTFDataStream data)
/*     */     throws IOException
/*     */   {
/*  56 */     this.formatType = data.read32Fixed();
/*  57 */     this.italicAngle = data.read32Fixed();
/*  58 */     this.underlinePosition = data.readSignedShort();
/*  59 */     this.underlineThickness = data.readSignedShort();
/*  60 */     this.isFixedPitch = data.readUnsignedInt();
/*  61 */     this.minMemType42 = data.readUnsignedInt();
/*  62 */     this.maxMemType42 = data.readUnsignedInt();
/*  63 */     this.mimMemType1 = data.readUnsignedInt();
/*  64 */     this.maxMemType1 = data.readUnsignedInt();
/*     */ 
/*  66 */     if (this.formatType == 1.0F)
/*     */     {
/*  71 */       this.glyphNames = new String[258];
/*  72 */       System.arraycopy(Encoding.MAC_GLYPH_NAMES, 0, this.glyphNames, 0, 258);
/*     */     }
/*  74 */     else if (this.formatType == 2.0F)
/*     */     {
/*  76 */       int numGlyphs = data.readUnsignedShort();
/*  77 */       int[] glyphNameIndex = new int[numGlyphs];
/*  78 */       this.glyphNames = new String[numGlyphs];
/*  79 */       int maxIndex = -2147483648;
/*  80 */       for (int i = 0; i < numGlyphs; i++)
/*     */       {
/*  82 */         int index = data.readUnsignedShort();
/*  83 */         glyphNameIndex[i] = index;
/*     */ 
/*  86 */         if (index <= 32767)
/*     */         {
/*  88 */           maxIndex = Math.max(maxIndex, index);
/*     */         }
/*     */       }
/*  91 */       String[] nameArray = null;
/*  92 */       if (maxIndex >= 258)
/*     */       {
/*  94 */         nameArray = new String[maxIndex - 258 + 1];
/*  95 */         for (int i = 0; i < maxIndex - 258 + 1; i++)
/*     */         {
/*  97 */           int numberOfChars = data.readUnsignedByte();
/*  98 */           nameArray[i] = data.readString(numberOfChars);
/*     */         }
/*     */       }
/* 101 */       for (int i = 0; i < numGlyphs; i++)
/*     */       {
/* 103 */         int index = glyphNameIndex[i];
/* 104 */         if (index < 258)
/*     */         {
/* 106 */           this.glyphNames[i] = Encoding.MAC_GLYPH_NAMES[index];
/*     */         }
/* 108 */         else if ((index >= 258) && (index <= 32767))
/*     */         {
/* 110 */           this.glyphNames[i] = nameArray[(index - 258)];
/*     */         }
/*     */         else
/*     */         {
/* 116 */           this.glyphNames[i] = ".undefined";
/*     */         }
/*     */       }
/*     */     }
/* 120 */     else if (this.formatType == 2.5F)
/*     */     {
/* 122 */       int[] glyphNameIndex = new int[ttf.getNumberOfGlyphs()];
/* 123 */       for (int i = 0; i < glyphNameIndex.length; i++)
/*     */       {
/* 125 */         int offset = data.readSignedByte();
/* 126 */         glyphNameIndex[i] = (i + 1 + offset);
/*     */       }
/* 128 */       this.glyphNames = new String[glyphNameIndex.length];
/* 129 */       for (int i = 0; i < this.glyphNames.length; i++)
/*     */       {
/* 131 */         String name = Encoding.MAC_GLYPH_NAMES[glyphNameIndex[i]];
/* 132 */         if (name != null)
/*     */         {
/* 134 */           this.glyphNames[i] = name;
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/* 139 */     else if (this.formatType != 3.0F);
/* 143 */     this.initialized = true;
/*     */   }
/*     */ 
/*     */   public float getFormatType()
/*     */   {
/* 151 */     return this.formatType;
/*     */   }
/*     */ 
/*     */   public void setFormatType(float formatTypeValue)
/*     */   {
/* 159 */     this.formatType = formatTypeValue;
/*     */   }
/*     */ 
/*     */   public long getIsFixedPitch()
/*     */   {
/* 167 */     return this.isFixedPitch;
/*     */   }
/*     */ 
/*     */   public void setIsFixedPitch(long isFixedPitchValue)
/*     */   {
/* 175 */     this.isFixedPitch = isFixedPitchValue;
/*     */   }
/*     */ 
/*     */   public float getItalicAngle()
/*     */   {
/* 183 */     return this.italicAngle;
/*     */   }
/*     */ 
/*     */   public void setItalicAngle(float italicAngleValue)
/*     */   {
/* 191 */     this.italicAngle = italicAngleValue;
/*     */   }
/*     */ 
/*     */   public long getMaxMemType1()
/*     */   {
/* 199 */     return this.maxMemType1;
/*     */   }
/*     */ 
/*     */   public void setMaxMemType1(long maxMemType1Value)
/*     */   {
/* 207 */     this.maxMemType1 = maxMemType1Value;
/*     */   }
/*     */ 
/*     */   public long getMaxMemType42()
/*     */   {
/* 215 */     return this.maxMemType42;
/*     */   }
/*     */ 
/*     */   public void setMaxMemType42(long maxMemType42Value)
/*     */   {
/* 223 */     this.maxMemType42 = maxMemType42Value;
/*     */   }
/*     */ 
/*     */   public long getMimMemType1()
/*     */   {
/* 231 */     return this.mimMemType1;
/*     */   }
/*     */ 
/*     */   public void setMimMemType1(long mimMemType1Value)
/*     */   {
/* 239 */     this.mimMemType1 = mimMemType1Value;
/*     */   }
/*     */ 
/*     */   public long getMinMemType42()
/*     */   {
/* 247 */     return this.minMemType42;
/*     */   }
/*     */ 
/*     */   public void setMinMemType42(long minMemType42Value)
/*     */   {
/* 255 */     this.minMemType42 = minMemType42Value;
/*     */   }
/*     */ 
/*     */   public short getUnderlinePosition()
/*     */   {
/* 263 */     return this.underlinePosition;
/*     */   }
/*     */ 
/*     */   public void setUnderlinePosition(short underlinePositionValue)
/*     */   {
/* 271 */     this.underlinePosition = underlinePositionValue;
/*     */   }
/*     */ 
/*     */   public short getUnderlineThickness()
/*     */   {
/* 279 */     return this.underlineThickness;
/*     */   }
/*     */ 
/*     */   public void setUnderlineThickness(short underlineThicknessValue)
/*     */   {
/* 287 */     this.underlineThickness = underlineThicknessValue;
/*     */   }
/*     */ 
/*     */   public String[] getGlyphNames()
/*     */   {
/* 295 */     return this.glyphNames;
/*     */   }
/*     */ 
/*     */   public void setGlyphNames(String[] glyphNamesValue)
/*     */   {
/* 303 */     this.glyphNames = glyphNamesValue;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.ttf.PostScriptTable
 * JD-Core Version:    0.6.2
 */