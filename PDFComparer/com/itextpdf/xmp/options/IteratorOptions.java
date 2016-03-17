/*     */ package com.itextpdf.xmp.options;
/*     */ 
/*     */ public final class IteratorOptions extends Options
/*     */ {
/*     */   public static final int JUST_CHILDREN = 256;
/*     */   public static final int JUST_LEAFNODES = 512;
/*     */   public static final int JUST_LEAFNAME = 1024;
/*     */   public static final int OMIT_QUALIFIERS = 4096;
/*     */ 
/*     */   public boolean isJustChildren()
/*     */   {
/*  61 */     return getOption(256);
/*     */   }
/*     */ 
/*     */   public boolean isJustLeafname()
/*     */   {
/*  70 */     return getOption(1024);
/*     */   }
/*     */ 
/*     */   public boolean isJustLeafnodes()
/*     */   {
/*  79 */     return getOption(512);
/*     */   }
/*     */ 
/*     */   public boolean isOmitQualifiers()
/*     */   {
/*  88 */     return getOption(4096);
/*     */   }
/*     */ 
/*     */   public IteratorOptions setJustChildren(boolean value)
/*     */   {
/* 100 */     setOption(256, value);
/* 101 */     return this;
/*     */   }
/*     */ 
/*     */   public IteratorOptions setJustLeafname(boolean value)
/*     */   {
/* 113 */     setOption(1024, value);
/* 114 */     return this;
/*     */   }
/*     */ 
/*     */   public IteratorOptions setJustLeafnodes(boolean value)
/*     */   {
/* 126 */     setOption(512, value);
/* 127 */     return this;
/*     */   }
/*     */ 
/*     */   public IteratorOptions setOmitQualifiers(boolean value)
/*     */   {
/* 139 */     setOption(4096, value);
/* 140 */     return this;
/*     */   }
/*     */ 
/*     */   protected String defineOptionName(int option)
/*     */   {
/* 149 */     switch (option) {
/*     */     case 256:
/* 151 */       return "JUST_CHILDREN";
/*     */     case 512:
/* 152 */       return "JUST_LEAFNODES";
/*     */     case 1024:
/* 153 */       return "JUST_LEAFNAME";
/*     */     case 4096:
/* 154 */       return "OMIT_QUALIFIERS";
/* 155 */     }return null;
/*     */   }
/*     */ 
/*     */   protected int getValidOptions()
/*     */   {
/* 165 */     return 5888;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.xmp.options.IteratorOptions
 * JD-Core Version:    0.6.2
 */