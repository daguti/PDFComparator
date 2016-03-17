/*     */ package com.itextpdf.text.pdf.internal;
/*     */ 
/*     */ import com.itextpdf.text.pdf.PdfArray;
/*     */ import com.itextpdf.text.pdf.PdfBoolean;
/*     */ import com.itextpdf.text.pdf.PdfDictionary;
/*     */ import com.itextpdf.text.pdf.PdfName;
/*     */ import com.itextpdf.text.pdf.PdfNumber;
/*     */ import com.itextpdf.text.pdf.PdfObject;
/*     */ import com.itextpdf.text.pdf.PdfReader;
/*     */ import com.itextpdf.text.pdf.interfaces.PdfViewerPreferences;
/*     */ 
/*     */ public class PdfViewerPreferencesImp
/*     */   implements PdfViewerPreferences
/*     */ {
/*  63 */   public static final PdfName[] VIEWER_PREFERENCES = { PdfName.HIDETOOLBAR, PdfName.HIDEMENUBAR, PdfName.HIDEWINDOWUI, PdfName.FITWINDOW, PdfName.CENTERWINDOW, PdfName.DISPLAYDOCTITLE, PdfName.NONFULLSCREENPAGEMODE, PdfName.DIRECTION, PdfName.VIEWAREA, PdfName.VIEWCLIP, PdfName.PRINTAREA, PdfName.PRINTCLIP, PdfName.PRINTSCALING, PdfName.DUPLEX, PdfName.PICKTRAYBYPDFSIZE, PdfName.PRINTPAGERANGE, PdfName.NUMCOPIES };
/*     */ 
/*  85 */   public static final PdfName[] NONFULLSCREENPAGEMODE_PREFERENCES = { PdfName.USENONE, PdfName.USEOUTLINES, PdfName.USETHUMBS, PdfName.USEOC };
/*     */ 
/*  89 */   public static final PdfName[] DIRECTION_PREFERENCES = { PdfName.L2R, PdfName.R2L };
/*     */ 
/*  93 */   public static final PdfName[] PAGE_BOUNDARIES = { PdfName.MEDIABOX, PdfName.CROPBOX, PdfName.BLEEDBOX, PdfName.TRIMBOX, PdfName.ARTBOX };
/*     */ 
/*  97 */   public static final PdfName[] PRINTSCALING_PREFERENCES = { PdfName.APPDEFAULT, PdfName.NONE };
/*     */ 
/* 101 */   public static final PdfName[] DUPLEX_PREFERENCES = { PdfName.SIMPLEX, PdfName.DUPLEXFLIPSHORTEDGE, PdfName.DUPLEXFLIPLONGEDGE };
/*     */ 
/* 106 */   private int pageLayoutAndMode = 0;
/*     */ 
/* 109 */   private PdfDictionary viewerPreferences = new PdfDictionary();
/*     */   private static final int viewerPreferencesMask = 16773120;
/*     */ 
/*     */   public int getPageLayoutAndMode()
/*     */   {
/* 118 */     return this.pageLayoutAndMode;
/*     */   }
/*     */ 
/*     */   public PdfDictionary getViewerPreferences()
/*     */   {
/* 125 */     return this.viewerPreferences;
/*     */   }
/*     */ 
/*     */   public void setViewerPreferences(int preferences)
/*     */   {
/* 136 */     this.pageLayoutAndMode |= preferences;
/*     */ 
/* 139 */     if ((preferences & 0xFFF000) != 0) {
/* 140 */       this.pageLayoutAndMode = (0xFF000FFF & this.pageLayoutAndMode);
/* 141 */       if ((preferences & 0x1000) != 0)
/* 142 */         this.viewerPreferences.put(PdfName.HIDETOOLBAR, PdfBoolean.PDFTRUE);
/* 143 */       if ((preferences & 0x2000) != 0)
/* 144 */         this.viewerPreferences.put(PdfName.HIDEMENUBAR, PdfBoolean.PDFTRUE);
/* 145 */       if ((preferences & 0x4000) != 0)
/* 146 */         this.viewerPreferences.put(PdfName.HIDEWINDOWUI, PdfBoolean.PDFTRUE);
/* 147 */       if ((preferences & 0x8000) != 0)
/* 148 */         this.viewerPreferences.put(PdfName.FITWINDOW, PdfBoolean.PDFTRUE);
/* 149 */       if ((preferences & 0x10000) != 0)
/* 150 */         this.viewerPreferences.put(PdfName.CENTERWINDOW, PdfBoolean.PDFTRUE);
/* 151 */       if ((preferences & 0x20000) != 0) {
/* 152 */         this.viewerPreferences.put(PdfName.DISPLAYDOCTITLE, PdfBoolean.PDFTRUE);
/*     */       }
/* 154 */       if ((preferences & 0x40000) != 0)
/* 155 */         this.viewerPreferences.put(PdfName.NONFULLSCREENPAGEMODE, PdfName.USENONE);
/* 156 */       else if ((preferences & 0x80000) != 0)
/* 157 */         this.viewerPreferences.put(PdfName.NONFULLSCREENPAGEMODE, PdfName.USEOUTLINES);
/* 158 */       else if ((preferences & 0x100000) != 0)
/* 159 */         this.viewerPreferences.put(PdfName.NONFULLSCREENPAGEMODE, PdfName.USETHUMBS);
/* 160 */       else if ((preferences & 0x200000) != 0) {
/* 161 */         this.viewerPreferences.put(PdfName.NONFULLSCREENPAGEMODE, PdfName.USEOC);
/*     */       }
/* 163 */       if ((preferences & 0x400000) != 0)
/* 164 */         this.viewerPreferences.put(PdfName.DIRECTION, PdfName.L2R);
/* 165 */       else if ((preferences & 0x800000) != 0) {
/* 166 */         this.viewerPreferences.put(PdfName.DIRECTION, PdfName.R2L);
/*     */       }
/* 168 */       if ((preferences & 0x1000000) != 0)
/* 169 */         this.viewerPreferences.put(PdfName.PRINTSCALING, PdfName.NONE);
/*     */     }
/*     */   }
/*     */ 
/*     */   private int getIndex(PdfName key)
/*     */   {
/* 180 */     for (int i = 0; i < VIEWER_PREFERENCES.length; i++) {
/* 181 */       if (VIEWER_PREFERENCES[i].equals(key))
/* 182 */         return i;
/*     */     }
/* 184 */     return -1;
/*     */   }
/*     */ 
/*     */   private boolean isPossibleValue(PdfName value, PdfName[] accepted)
/*     */   {
/* 191 */     for (int i = 0; i < accepted.length; i++) {
/* 192 */       if (accepted[i].equals(value)) {
/* 193 */         return true;
/*     */       }
/*     */     }
/* 196 */     return false;
/*     */   }
/*     */ 
/*     */   public void addViewerPreference(PdfName key, PdfObject value)
/*     */   {
/* 203 */     switch (getIndex(key)) {
/*     */     case 0:
/*     */     case 1:
/*     */     case 2:
/*     */     case 3:
/*     */     case 4:
/*     */     case 5:
/*     */     case 14:
/* 211 */       if ((value instanceof PdfBoolean))
/* 212 */         this.viewerPreferences.put(key, value); break;
/*     */     case 6:
/* 216 */       if (((value instanceof PdfName)) && (isPossibleValue((PdfName)value, NONFULLSCREENPAGEMODE_PREFERENCES)))
/*     */       {
/* 218 */         this.viewerPreferences.put(key, value); } break;
/*     */     case 7:
/* 222 */       if (((value instanceof PdfName)) && (isPossibleValue((PdfName)value, DIRECTION_PREFERENCES)))
/*     */       {
/* 224 */         this.viewerPreferences.put(key, value); } break;
/*     */     case 8:
/*     */     case 9:
/*     */     case 10:
/*     */     case 11:
/* 231 */       if (((value instanceof PdfName)) && (isPossibleValue((PdfName)value, PAGE_BOUNDARIES)))
/*     */       {
/* 233 */         this.viewerPreferences.put(key, value); } break;
/*     */     case 12:
/* 237 */       if (((value instanceof PdfName)) && (isPossibleValue((PdfName)value, PRINTSCALING_PREFERENCES)))
/*     */       {
/* 239 */         this.viewerPreferences.put(key, value); } break;
/*     */     case 13:
/* 243 */       if (((value instanceof PdfName)) && (isPossibleValue((PdfName)value, DUPLEX_PREFERENCES)))
/*     */       {
/* 245 */         this.viewerPreferences.put(key, value); } break;
/*     */     case 15:
/* 249 */       if ((value instanceof PdfArray))
/* 250 */         this.viewerPreferences.put(key, value); break;
/*     */     case 16:
/* 254 */       if ((value instanceof PdfNumber))
/* 255 */         this.viewerPreferences.put(key, value);
/*     */       break;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addToCatalog(PdfDictionary catalog)
/*     */   {
/* 269 */     catalog.remove(PdfName.PAGELAYOUT);
/* 270 */     if ((this.pageLayoutAndMode & 0x1) != 0)
/* 271 */       catalog.put(PdfName.PAGELAYOUT, PdfName.SINGLEPAGE);
/* 272 */     else if ((this.pageLayoutAndMode & 0x2) != 0)
/* 273 */       catalog.put(PdfName.PAGELAYOUT, PdfName.ONECOLUMN);
/* 274 */     else if ((this.pageLayoutAndMode & 0x4) != 0)
/* 275 */       catalog.put(PdfName.PAGELAYOUT, PdfName.TWOCOLUMNLEFT);
/* 276 */     else if ((this.pageLayoutAndMode & 0x8) != 0)
/* 277 */       catalog.put(PdfName.PAGELAYOUT, PdfName.TWOCOLUMNRIGHT);
/* 278 */     else if ((this.pageLayoutAndMode & 0x10) != 0)
/* 279 */       catalog.put(PdfName.PAGELAYOUT, PdfName.TWOPAGELEFT);
/* 280 */     else if ((this.pageLayoutAndMode & 0x20) != 0) {
/* 281 */       catalog.put(PdfName.PAGELAYOUT, PdfName.TWOPAGERIGHT);
/*     */     }
/*     */ 
/* 284 */     catalog.remove(PdfName.PAGEMODE);
/* 285 */     if ((this.pageLayoutAndMode & 0x40) != 0)
/* 286 */       catalog.put(PdfName.PAGEMODE, PdfName.USENONE);
/* 287 */     else if ((this.pageLayoutAndMode & 0x80) != 0)
/* 288 */       catalog.put(PdfName.PAGEMODE, PdfName.USEOUTLINES);
/* 289 */     else if ((this.pageLayoutAndMode & 0x100) != 0)
/* 290 */       catalog.put(PdfName.PAGEMODE, PdfName.USETHUMBS);
/* 291 */     else if ((this.pageLayoutAndMode & 0x200) != 0)
/* 292 */       catalog.put(PdfName.PAGEMODE, PdfName.FULLSCREEN);
/* 293 */     else if ((this.pageLayoutAndMode & 0x400) != 0)
/* 294 */       catalog.put(PdfName.PAGEMODE, PdfName.USEOC);
/* 295 */     else if ((this.pageLayoutAndMode & 0x800) != 0) {
/* 296 */       catalog.put(PdfName.PAGEMODE, PdfName.USEATTACHMENTS);
/*     */     }
/*     */ 
/* 299 */     catalog.remove(PdfName.VIEWERPREFERENCES);
/* 300 */     if (this.viewerPreferences.size() > 0)
/* 301 */       catalog.put(PdfName.VIEWERPREFERENCES, this.viewerPreferences);
/*     */   }
/*     */ 
/*     */   public static PdfViewerPreferencesImp getViewerPreferences(PdfDictionary catalog)
/*     */   {
/* 306 */     PdfViewerPreferencesImp preferences = new PdfViewerPreferencesImp();
/* 307 */     int prefs = 0;
/* 308 */     PdfName name = null;
/*     */ 
/* 310 */     PdfObject obj = PdfReader.getPdfObjectRelease(catalog.get(PdfName.PAGELAYOUT));
/* 311 */     if ((obj != null) && (obj.isName())) {
/* 312 */       name = (PdfName)obj;
/* 313 */       if (name.equals(PdfName.SINGLEPAGE))
/* 314 */         prefs |= 1;
/* 315 */       else if (name.equals(PdfName.ONECOLUMN))
/* 316 */         prefs |= 2;
/* 317 */       else if (name.equals(PdfName.TWOCOLUMNLEFT))
/* 318 */         prefs |= 4;
/* 319 */       else if (name.equals(PdfName.TWOCOLUMNRIGHT))
/* 320 */         prefs |= 8;
/* 321 */       else if (name.equals(PdfName.TWOPAGELEFT))
/* 322 */         prefs |= 16;
/* 323 */       else if (name.equals(PdfName.TWOPAGERIGHT)) {
/* 324 */         prefs |= 32;
/*     */       }
/*     */     }
/* 327 */     obj = PdfReader.getPdfObjectRelease(catalog.get(PdfName.PAGEMODE));
/* 328 */     if ((obj != null) && (obj.isName())) {
/* 329 */       name = (PdfName)obj;
/* 330 */       if (name.equals(PdfName.USENONE))
/* 331 */         prefs |= 64;
/* 332 */       else if (name.equals(PdfName.USEOUTLINES))
/* 333 */         prefs |= 128;
/* 334 */       else if (name.equals(PdfName.USETHUMBS))
/* 335 */         prefs |= 256;
/* 336 */       else if (name.equals(PdfName.FULLSCREEN))
/* 337 */         prefs |= 512;
/* 338 */       else if (name.equals(PdfName.USEOC))
/* 339 */         prefs |= 1024;
/* 340 */       else if (name.equals(PdfName.USEATTACHMENTS)) {
/* 341 */         prefs |= 2048;
/*     */       }
/*     */     }
/* 344 */     preferences.setViewerPreferences(prefs);
/*     */ 
/* 346 */     obj = PdfReader.getPdfObjectRelease(catalog.get(PdfName.VIEWERPREFERENCES));
/*     */ 
/* 348 */     if ((obj != null) && (obj.isDictionary())) {
/* 349 */       PdfDictionary vp = (PdfDictionary)obj;
/* 350 */       for (int i = 0; i < VIEWER_PREFERENCES.length; i++) {
/* 351 */         obj = PdfReader.getPdfObjectRelease(vp.get(VIEWER_PREFERENCES[i]));
/* 352 */         preferences.addViewerPreference(VIEWER_PREFERENCES[i], obj);
/*     */       }
/*     */     }
/* 355 */     return preferences;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.internal.PdfViewerPreferencesImp
 * JD-Core Version:    0.6.2
 */