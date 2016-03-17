/*     */ package org.apache.pdfbox.pdmodel.interactive.viewerpreferences;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ 
/*     */ public class PDViewerPreferences
/*     */   implements COSObjectable
/*     */ {
/*     */ 
/*     */   /** @deprecated */
/*     */   public static final String NON_FULL_SCREEN_PAGE_MODE_USE_NONE = "UseNone";
/*     */ 
/*     */   /** @deprecated */
/*     */   public static final String NON_FULL_SCREEN_PAGE_MODE_USE_OUTLINES = "UseOutlines";
/*     */ 
/*     */   /** @deprecated */
/*     */   public static final String NON_FULL_SCREEN_PAGE_MODE_USE_THUMBS = "UseThumbs";
/*     */ 
/*     */   /** @deprecated */
/*     */   public static final String NON_FULL_SCREEN_PAGE_MODE_USE_OPTIONAL_CONTENT = "UseOC";
/*     */ 
/*     */   /** @deprecated */
/*     */   public static final String READING_DIRECTION_L2R = "L2R";
/*     */ 
/*     */   /** @deprecated */
/*     */   public static final String READING_DIRECTION_R2L = "R2L";
/*     */ 
/*     */   /** @deprecated */
/*     */   public static final String BOUNDARY_MEDIA_BOX = "MediaBox";
/*     */ 
/*     */   /** @deprecated */
/*     */   public static final String BOUNDARY_CROP_BOX = "CropBox";
/*     */ 
/*     */   /** @deprecated */
/*     */   public static final String BOUNDARY_BLEED_BOX = "BleedBox";
/*     */ 
/*     */   /** @deprecated */
/*     */   public static final String BOUNDARY_TRIM_BOX = "TrimBox";
/*     */ 
/*     */   /** @deprecated */
/*     */   public static final String BOUNDARY_ART_BOX = "ArtBox";
/*     */   private COSDictionary prefs;
/*     */ 
/*     */   public PDViewerPreferences(COSDictionary dic)
/*     */   {
/* 209 */     this.prefs = dic;
/*     */   }
/*     */ 
/*     */   public COSDictionary getDictionary()
/*     */   {
/* 219 */     return this.prefs;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/* 229 */     return this.prefs;
/*     */   }
/*     */ 
/*     */   public boolean hideToolbar()
/*     */   {
/* 239 */     return this.prefs.getBoolean(COSName.HIDE_TOOLBAR, false);
/*     */   }
/*     */ 
/*     */   public void setHideToolbar(boolean value)
/*     */   {
/* 249 */     this.prefs.setBoolean(COSName.HIDE_TOOLBAR, value);
/*     */   }
/*     */ 
/*     */   public boolean hideMenubar()
/*     */   {
/* 259 */     return this.prefs.getBoolean(COSName.HIDE_MENUBAR, false);
/*     */   }
/*     */ 
/*     */   public void setHideMenubar(boolean value)
/*     */   {
/* 269 */     this.prefs.setBoolean(COSName.HIDE_MENUBAR, value);
/*     */   }
/*     */ 
/*     */   public boolean hideWindowUI()
/*     */   {
/* 279 */     return this.prefs.getBoolean(COSName.HIDE_WINDOWUI, false);
/*     */   }
/*     */ 
/*     */   public void setHideWindowUI(boolean value)
/*     */   {
/* 289 */     this.prefs.setBoolean(COSName.HIDE_WINDOWUI, value);
/*     */   }
/*     */ 
/*     */   public boolean fitWindow()
/*     */   {
/* 299 */     return this.prefs.getBoolean(COSName.FIT_WINDOW, false);
/*     */   }
/*     */ 
/*     */   public void setFitWindow(boolean value)
/*     */   {
/* 309 */     this.prefs.setBoolean(COSName.FIT_WINDOW, value);
/*     */   }
/*     */ 
/*     */   public boolean centerWindow()
/*     */   {
/* 319 */     return this.prefs.getBoolean(COSName.CENTER_WINDOW, false);
/*     */   }
/*     */ 
/*     */   public void setCenterWindow(boolean value)
/*     */   {
/* 329 */     this.prefs.setBoolean(COSName.CENTER_WINDOW, value);
/*     */   }
/*     */ 
/*     */   public boolean displayDocTitle()
/*     */   {
/* 339 */     return this.prefs.getBoolean(COSName.DISPLAY_DOC_TITLE, false);
/*     */   }
/*     */ 
/*     */   public void setDisplayDocTitle(boolean value)
/*     */   {
/* 349 */     this.prefs.setBoolean(COSName.DISPLAY_DOC_TITLE, value);
/*     */   }
/*     */ 
/*     */   public String getNonFullScreenPageMode()
/*     */   {
/* 359 */     return this.prefs.getNameAsString(COSName.NON_FULL_SCREEN_PAGE_MODE, NON_FULL_SCREEN_PAGE_MODE.UseNone.toString());
/*     */   }
/*     */ 
/*     */   public void setNonFullScreenPageMode(NON_FULL_SCREEN_PAGE_MODE value)
/*     */   {
/* 370 */     this.prefs.setName(COSName.NON_FULL_SCREEN_PAGE_MODE, value.toString());
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void setNonFullScreenPageMode(String value)
/*     */   {
/* 382 */     this.prefs.setName(COSName.NON_FULL_SCREEN_PAGE_MODE, value);
/*     */   }
/*     */ 
/*     */   public String getReadingDirection()
/*     */   {
/* 392 */     return this.prefs.getNameAsString(COSName.DIRECTION, READING_DIRECTION.L2R.toString());
/*     */   }
/*     */ 
/*     */   public void setReadingDirection(READING_DIRECTION value)
/*     */   {
/* 402 */     this.prefs.setName(COSName.DIRECTION, value.toString());
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void setReadingDirection(String value)
/*     */   {
/* 414 */     this.prefs.setName(COSName.DIRECTION, value.toString());
/*     */   }
/*     */ 
/*     */   public String getViewArea()
/*     */   {
/* 424 */     return this.prefs.getNameAsString(COSName.VIEW_AREA, BOUNDARY.CropBox.toString());
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void setViewArea(String value)
/*     */   {
/* 436 */     this.prefs.setName(COSName.VIEW_AREA, value);
/*     */   }
/*     */ 
/*     */   public void setViewArea(BOUNDARY value)
/*     */   {
/* 446 */     this.prefs.setName(COSName.VIEW_AREA, value.toString());
/*     */   }
/*     */ 
/*     */   public String getViewClip()
/*     */   {
/* 456 */     return this.prefs.getNameAsString(COSName.VIEW_CLIP, BOUNDARY.CropBox.toString());
/*     */   }
/*     */ 
/*     */   public void setViewClip(BOUNDARY value)
/*     */   {
/* 466 */     this.prefs.setName(COSName.VIEW_CLIP, value.toString());
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void setViewClip(String value)
/*     */   {
/* 478 */     this.prefs.setName(COSName.VIEW_CLIP, value);
/*     */   }
/*     */ 
/*     */   public String getPrintArea()
/*     */   {
/* 488 */     return this.prefs.getNameAsString(COSName.PRINT_AREA, BOUNDARY.CropBox.toString());
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void setPrintArea(String value)
/*     */   {
/* 500 */     this.prefs.setName(COSName.PRINT_AREA, value);
/*     */   }
/*     */ 
/*     */   public void setPrintArea(BOUNDARY value)
/*     */   {
/* 510 */     this.prefs.setName(COSName.PRINT_AREA, value.toString());
/*     */   }
/*     */ 
/*     */   public String getPrintClip()
/*     */   {
/* 520 */     return this.prefs.getNameAsString(COSName.PRINT_CLIP, BOUNDARY.CropBox.toString());
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void setPrintClip(String value)
/*     */   {
/* 532 */     this.prefs.setName(COSName.PRINT_CLIP, value);
/*     */   }
/*     */ 
/*     */   public void setPrintClip(BOUNDARY value)
/*     */   {
/* 542 */     this.prefs.setName(COSName.PRINT_CLIP, value.toString());
/*     */   }
/*     */ 
/*     */   public String getDuplex()
/*     */   {
/* 552 */     return this.prefs.getNameAsString(COSName.DUPLEX);
/*     */   }
/*     */ 
/*     */   public void setDuplex(DUPLEX value)
/*     */   {
/* 562 */     this.prefs.setName(COSName.DUPLEX, value.toString());
/*     */   }
/*     */ 
/*     */   public String getPrintScaling()
/*     */   {
/* 572 */     return this.prefs.getNameAsString(COSName.PRINT_SCALING, PRINT_SCALING.AppDefault.toString());
/*     */   }
/*     */ 
/*     */   public void setPrintScaling(PRINT_SCALING value)
/*     */   {
/* 582 */     this.prefs.setName(COSName.PRINT_SCALING, value.toString());
/*     */   }
/*     */ 
/*     */   public static enum PRINT_SCALING
/*     */   {
/* 193 */     None, 
/*     */ 
/* 197 */     AppDefault;
/*     */   }
/*     */ 
/*     */   public static enum DUPLEX
/*     */   {
/* 174 */     Simplex, 
/*     */ 
/* 178 */     DuplexFlipShortEdge, 
/*     */ 
/* 182 */     DuplexFlipLongEdge;
/*     */   }
/*     */ 
/*     */   public static enum BOUNDARY
/*     */   {
/* 147 */     MediaBox, 
/*     */ 
/* 151 */     CropBox, 
/*     */ 
/* 155 */     BleedBox, 
/*     */ 
/* 159 */     TrimBox, 
/*     */ 
/* 163 */     ArtBox;
/*     */   }
/*     */ 
/*     */   public static enum READING_DIRECTION
/*     */   {
/* 102 */     L2R, 
/*     */ 
/* 106 */     R2L;
/*     */   }
/*     */ 
/*     */   public static enum NON_FULL_SCREEN_PAGE_MODE
/*     */   {
/*  67 */     UseNone, 
/*     */ 
/*  71 */     UseOutlines, 
/*     */ 
/*  75 */     UseThumbs, 
/*     */ 
/*  79 */     UseOC;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.viewerpreferences.PDViewerPreferences
 * JD-Core Version:    0.6.2
 */