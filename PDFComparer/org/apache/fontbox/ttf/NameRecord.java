/*     */ package org.apache.fontbox.ttf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ 
/*     */ public class NameRecord
/*     */ {
/*     */   public static final int PLATFORM_APPLE_UNICODE = 0;
/*     */   public static final int PLATFORM_MACINTOSH = 1;
/*     */   public static final int PLATFORM_ISO = 2;
/*     */   public static final int PLATFORM_WINDOWS = 3;
/*     */   public static final int PLATFORM_ENCODING_WINDOWS_UNDEFINED = 0;
/*     */   public static final int PLATFORM_ENCODING_WINDOWS_UNICODE = 1;
/*     */   public static final int NAME_COPYRIGHT = 0;
/*     */   public static final int NAME_FONT_FAMILY_NAME = 1;
/*     */   public static final int NAME_FONT_SUB_FAMILY_NAME = 2;
/*     */   public static final int NAME_UNIQUE_FONT_ID = 3;
/*     */   public static final int NAME_FULL_FONT_NAME = 4;
/*     */   public static final int NAME_VERSION = 5;
/*     */   public static final int NAME_POSTSCRIPT_NAME = 6;
/*     */   public static final int NAME_TRADEMARK = 7;
/*     */   private int platformId;
/*     */   private int platformEncodingId;
/*     */   private int languageId;
/*     */   private int nameId;
/*     */   private int stringLength;
/*     */   private int stringOffset;
/*     */   private String string;
/*     */ 
/*     */   public int getStringLength()
/*     */   {
/* 103 */     return this.stringLength;
/*     */   }
/*     */ 
/*     */   public void setStringLength(int stringLengthValue)
/*     */   {
/* 110 */     this.stringLength = stringLengthValue;
/*     */   }
/*     */ 
/*     */   public int getStringOffset()
/*     */   {
/* 117 */     return this.stringOffset;
/*     */   }
/*     */ 
/*     */   public void setStringOffset(int stringOffsetValue)
/*     */   {
/* 124 */     this.stringOffset = stringOffsetValue;
/*     */   }
/*     */ 
/*     */   public int getLanguageId()
/*     */   {
/* 132 */     return this.languageId;
/*     */   }
/*     */ 
/*     */   public void setLanguageId(int languageIdValue)
/*     */   {
/* 139 */     this.languageId = languageIdValue;
/*     */   }
/*     */ 
/*     */   public int getNameId()
/*     */   {
/* 146 */     return this.nameId;
/*     */   }
/*     */ 
/*     */   public void setNameId(int nameIdValue)
/*     */   {
/* 153 */     this.nameId = nameIdValue;
/*     */   }
/*     */ 
/*     */   public int getPlatformEncodingId()
/*     */   {
/* 160 */     return this.platformEncodingId;
/*     */   }
/*     */ 
/*     */   public void setPlatformEncodingId(int platformEncodingIdValue)
/*     */   {
/* 167 */     this.platformEncodingId = platformEncodingIdValue;
/*     */   }
/*     */ 
/*     */   public int getPlatformId()
/*     */   {
/* 174 */     return this.platformId;
/*     */   }
/*     */ 
/*     */   public void setPlatformId(int platformIdValue)
/*     */   {
/* 181 */     this.platformId = platformIdValue;
/*     */   }
/*     */ 
/*     */   public void initData(TrueTypeFont ttf, TTFDataStream data)
/*     */     throws IOException
/*     */   {
/* 193 */     this.platformId = data.readUnsignedShort();
/* 194 */     this.platformEncodingId = data.readUnsignedShort();
/* 195 */     this.languageId = data.readUnsignedShort();
/* 196 */     this.nameId = data.readUnsignedShort();
/* 197 */     this.stringLength = data.readUnsignedShort();
/* 198 */     this.stringOffset = data.readUnsignedShort();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 208 */     return "platform=" + this.platformId + " pEncoding=" + this.platformEncodingId + " language=" + this.languageId + " name=" + this.nameId;
/*     */   }
/*     */ 
/*     */   public String getString()
/*     */   {
/* 219 */     return this.string;
/*     */   }
/*     */ 
/*     */   public void setString(String stringValue)
/*     */   {
/* 226 */     this.string = stringValue;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.ttf.NameRecord
 * JD-Core Version:    0.6.2
 */