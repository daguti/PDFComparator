/*     */ package com.itextpdf.xmp.options;
/*     */ 
/*     */ public final class ParseOptions extends Options
/*     */ {
/*     */   public static final int REQUIRE_XMP_META = 1;
/*     */   public static final int STRICT_ALIASING = 4;
/*     */   public static final int FIX_CONTROL_CHARS = 8;
/*     */   public static final int ACCEPT_LATIN_1 = 16;
/*     */   public static final int OMIT_NORMALIZATION = 32;
/*     */ 
/*     */   public ParseOptions()
/*     */   {
/*  62 */     setOption(24, true);
/*     */   }
/*     */ 
/*     */   public boolean getRequireXMPMeta()
/*     */   {
/*  71 */     return getOption(1);
/*     */   }
/*     */ 
/*     */   public ParseOptions setRequireXMPMeta(boolean value)
/*     */   {
/*  81 */     setOption(1, value);
/*  82 */     return this;
/*     */   }
/*     */ 
/*     */   public boolean getStrictAliasing()
/*     */   {
/*  91 */     return getOption(4);
/*     */   }
/*     */ 
/*     */   public ParseOptions setStrictAliasing(boolean value)
/*     */   {
/* 101 */     setOption(4, value);
/* 102 */     return this;
/*     */   }
/*     */ 
/*     */   public boolean getFixControlChars()
/*     */   {
/* 111 */     return getOption(8);
/*     */   }
/*     */ 
/*     */   public ParseOptions setFixControlChars(boolean value)
/*     */   {
/* 121 */     setOption(8, value);
/* 122 */     return this;
/*     */   }
/*     */ 
/*     */   public boolean getAcceptLatin1()
/*     */   {
/* 131 */     return getOption(16);
/*     */   }
/*     */ 
/*     */   public ParseOptions setOmitNormalization(boolean value)
/*     */   {
/* 141 */     setOption(32, value);
/* 142 */     return this;
/*     */   }
/*     */ 
/*     */   public boolean getOmitNormalization()
/*     */   {
/* 151 */     return getOption(32);
/*     */   }
/*     */ 
/*     */   public ParseOptions setAcceptLatin1(boolean value)
/*     */   {
/* 161 */     setOption(16, value);
/* 162 */     return this;
/*     */   }
/*     */ 
/*     */   protected String defineOptionName(int option)
/*     */   {
/* 171 */     switch (option) {
/*     */     case 1:
/* 173 */       return "REQUIRE_XMP_META";
/*     */     case 4:
/* 174 */       return "STRICT_ALIASING";
/*     */     case 8:
/* 175 */       return "FIX_CONTROL_CHARS";
/*     */     case 16:
/* 176 */       return "ACCEPT_LATIN_1";
/*     */     case 32:
/* 177 */       return "OMIT_NORMALIZATION";
/* 178 */     }return null;
/*     */   }
/*     */ 
/*     */   protected int getValidOptions()
/*     */   {
/* 188 */     return 61;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.xmp.options.ParseOptions
 * JD-Core Version:    0.6.2
 */