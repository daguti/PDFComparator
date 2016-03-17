/*     */ package com.itextpdf.text.pdf.codec.wmf;
/*     */ 
/*     */ import com.itextpdf.text.BaseColor;
/*     */ import com.itextpdf.text.pdf.PdfContentByte;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Stack;
/*     */ 
/*     */ public class MetaState
/*     */ {
/*     */   public static final int TA_NOUPDATECP = 0;
/*     */   public static final int TA_UPDATECP = 1;
/*     */   public static final int TA_LEFT = 0;
/*     */   public static final int TA_RIGHT = 2;
/*     */   public static final int TA_CENTER = 6;
/*     */   public static final int TA_TOP = 0;
/*     */   public static final int TA_BOTTOM = 8;
/*     */   public static final int TA_BASELINE = 24;
/*     */   public static final int TRANSPARENT = 1;
/*     */   public static final int OPAQUE = 2;
/*     */   public static final int ALTERNATE = 1;
/*     */   public static final int WINDING = 2;
/*     */   public Stack<MetaState> savedStates;
/*     */   public ArrayList<MetaObject> MetaObjects;
/*     */   public Point currentPoint;
/*     */   public MetaPen currentPen;
/*     */   public MetaBrush currentBrush;
/*     */   public MetaFont currentFont;
/*  76 */   public BaseColor currentBackgroundColor = BaseColor.WHITE;
/*  77 */   public BaseColor currentTextColor = BaseColor.BLACK;
/*  78 */   public int backgroundMode = 2;
/*  79 */   public int polyFillMode = 1;
/*  80 */   public int lineJoin = 1;
/*     */   public int textAlign;
/*     */   public int offsetWx;
/*     */   public int offsetWy;
/*     */   public int extentWx;
/*     */   public int extentWy;
/*     */   public float scalingX;
/*     */   public float scalingY;
/*     */ 
/*     */   public MetaState()
/*     */   {
/*  92 */     this.savedStates = new Stack();
/*  93 */     this.MetaObjects = new ArrayList();
/*  94 */     this.currentPoint = new Point(0, 0);
/*  95 */     this.currentPen = new MetaPen();
/*  96 */     this.currentBrush = new MetaBrush();
/*  97 */     this.currentFont = new MetaFont();
/*     */   }
/*     */ 
/*     */   public MetaState(MetaState state) {
/* 101 */     setMetaState(state);
/*     */   }
/*     */ 
/*     */   public void setMetaState(MetaState state) {
/* 105 */     this.savedStates = state.savedStates;
/* 106 */     this.MetaObjects = state.MetaObjects;
/* 107 */     this.currentPoint = state.currentPoint;
/* 108 */     this.currentPen = state.currentPen;
/* 109 */     this.currentBrush = state.currentBrush;
/* 110 */     this.currentFont = state.currentFont;
/* 111 */     this.currentBackgroundColor = state.currentBackgroundColor;
/* 112 */     this.currentTextColor = state.currentTextColor;
/* 113 */     this.backgroundMode = state.backgroundMode;
/* 114 */     this.polyFillMode = state.polyFillMode;
/* 115 */     this.textAlign = state.textAlign;
/* 116 */     this.lineJoin = state.lineJoin;
/* 117 */     this.offsetWx = state.offsetWx;
/* 118 */     this.offsetWy = state.offsetWy;
/* 119 */     this.extentWx = state.extentWx;
/* 120 */     this.extentWy = state.extentWy;
/* 121 */     this.scalingX = state.scalingX;
/* 122 */     this.scalingY = state.scalingY;
/*     */   }
/*     */ 
/*     */   public void addMetaObject(MetaObject object) {
/* 126 */     for (int k = 0; k < this.MetaObjects.size(); k++) {
/* 127 */       if (this.MetaObjects.get(k) == null) {
/* 128 */         this.MetaObjects.set(k, object);
/* 129 */         return;
/*     */       }
/*     */     }
/* 132 */     this.MetaObjects.add(object);
/*     */   }
/*     */ 
/*     */   public void selectMetaObject(int index, PdfContentByte cb) {
/* 136 */     MetaObject obj = (MetaObject)this.MetaObjects.get(index);
/* 137 */     if (obj == null)
/*     */       return;
/*     */     int style;
/* 140 */     switch (obj.getType()) {
/*     */     case 2:
/* 142 */       this.currentBrush = ((MetaBrush)obj);
/* 143 */       style = this.currentBrush.getStyle();
/* 144 */       if (style == 0) {
/* 145 */         BaseColor color = this.currentBrush.getColor();
/* 146 */         cb.setColorFill(color);
/*     */       }
/* 148 */       else if (style == 2) {
/* 149 */         BaseColor color = this.currentBackgroundColor;
/* 150 */         cb.setColorFill(color);
/* 151 */       }break;
/*     */     case 1:
/* 155 */       this.currentPen = ((MetaPen)obj);
/* 156 */       style = this.currentPen.getStyle();
/* 157 */       if (style != 5) {
/* 158 */         BaseColor color = this.currentPen.getColor();
/* 159 */         cb.setColorStroke(color);
/* 160 */         cb.setLineWidth(Math.abs(this.currentPen.getPenWidth() * this.scalingX / this.extentWx));
/* 161 */         switch (style) {
/*     */         case 1:
/* 163 */           cb.setLineDash(18.0F, 6.0F, 0.0F);
/* 164 */           break;
/*     */         case 3:
/* 166 */           cb.setLiteral("[9 6 3 6]0 d\n");
/* 167 */           break;
/*     */         case 4:
/* 169 */           cb.setLiteral("[9 3 3 3 3 3]0 d\n");
/* 170 */           break;
/*     */         case 2:
/* 172 */           cb.setLineDash(3.0F, 0.0F);
/* 173 */           break;
/*     */         default:
/* 175 */           cb.setLineDash(0.0F);
/*     */         }
/*     */       }
/* 178 */       break;
/*     */     case 3:
/* 183 */       this.currentFont = ((MetaFont)obj);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void deleteMetaObject(int index)
/*     */   {
/* 190 */     this.MetaObjects.set(index, null);
/*     */   }
/*     */ 
/*     */   public void saveState(PdfContentByte cb) {
/* 194 */     cb.saveState();
/* 195 */     MetaState state = new MetaState(this);
/* 196 */     this.savedStates.push(state);
/*     */   }
/*     */ 
/*     */   public void restoreState(int index, PdfContentByte cb)
/*     */   {
/*     */     int pops;
/*     */     int pops;
/* 201 */     if (index < 0)
/* 202 */       pops = Math.min(-index, this.savedStates.size());
/*     */     else
/* 204 */       pops = Math.max(this.savedStates.size() - index, 0);
/* 205 */     if (pops == 0)
/* 206 */       return;
/* 207 */     MetaState state = null;
/* 208 */     while (pops-- != 0) {
/* 209 */       cb.restoreState();
/* 210 */       state = (MetaState)this.savedStates.pop();
/*     */     }
/* 212 */     setMetaState(state);
/*     */   }
/*     */ 
/*     */   public void cleanup(PdfContentByte cb) {
/* 216 */     int k = this.savedStates.size();
/* 217 */     while (k-- > 0)
/* 218 */       cb.restoreState();
/*     */   }
/*     */ 
/*     */   public float transformX(int x) {
/* 222 */     return (x - this.offsetWx) * this.scalingX / this.extentWx;
/*     */   }
/*     */ 
/*     */   public float transformY(int y) {
/* 226 */     return (1.0F - (y - this.offsetWy) / this.extentWy) * this.scalingY;
/*     */   }
/*     */ 
/*     */   public void setScalingX(float scalingX) {
/* 230 */     this.scalingX = scalingX;
/*     */   }
/*     */ 
/*     */   public void setScalingY(float scalingY) {
/* 234 */     this.scalingY = scalingY;
/*     */   }
/*     */ 
/*     */   public void setOffsetWx(int offsetWx) {
/* 238 */     this.offsetWx = offsetWx;
/*     */   }
/*     */ 
/*     */   public void setOffsetWy(int offsetWy) {
/* 242 */     this.offsetWy = offsetWy;
/*     */   }
/*     */ 
/*     */   public void setExtentWx(int extentWx) {
/* 246 */     this.extentWx = extentWx;
/*     */   }
/*     */ 
/*     */   public void setExtentWy(int extentWy) {
/* 250 */     this.extentWy = extentWy;
/*     */   }
/*     */ 
/*     */   public float transformAngle(float angle) {
/* 254 */     float ta = this.scalingY < 0.0F ? -angle : angle;
/* 255 */     return (float)(this.scalingX < 0.0F ? 3.141592653589793D - ta : ta);
/*     */   }
/*     */ 
/*     */   public void setCurrentPoint(Point p) {
/* 259 */     this.currentPoint = p;
/*     */   }
/*     */ 
/*     */   public Point getCurrentPoint() {
/* 263 */     return this.currentPoint;
/*     */   }
/*     */ 
/*     */   public MetaBrush getCurrentBrush() {
/* 267 */     return this.currentBrush;
/*     */   }
/*     */ 
/*     */   public MetaPen getCurrentPen() {
/* 271 */     return this.currentPen;
/*     */   }
/*     */ 
/*     */   public MetaFont getCurrentFont() {
/* 275 */     return this.currentFont;
/*     */   }
/*     */ 
/*     */   public BaseColor getCurrentBackgroundColor()
/*     */   {
/* 282 */     return this.currentBackgroundColor;
/*     */   }
/*     */ 
/*     */   public void setCurrentBackgroundColor(BaseColor currentBackgroundColor)
/*     */   {
/* 289 */     this.currentBackgroundColor = currentBackgroundColor;
/*     */   }
/*     */ 
/*     */   public BaseColor getCurrentTextColor()
/*     */   {
/* 296 */     return this.currentTextColor;
/*     */   }
/*     */ 
/*     */   public void setCurrentTextColor(BaseColor currentTextColor)
/*     */   {
/* 303 */     this.currentTextColor = currentTextColor;
/*     */   }
/*     */ 
/*     */   public int getBackgroundMode()
/*     */   {
/* 310 */     return this.backgroundMode;
/*     */   }
/*     */ 
/*     */   public void setBackgroundMode(int backgroundMode)
/*     */   {
/* 317 */     this.backgroundMode = backgroundMode;
/*     */   }
/*     */ 
/*     */   public int getTextAlign()
/*     */   {
/* 324 */     return this.textAlign;
/*     */   }
/*     */ 
/*     */   public void setTextAlign(int textAlign)
/*     */   {
/* 331 */     this.textAlign = textAlign;
/*     */   }
/*     */ 
/*     */   public int getPolyFillMode()
/*     */   {
/* 338 */     return this.polyFillMode;
/*     */   }
/*     */ 
/*     */   public void setPolyFillMode(int polyFillMode)
/*     */   {
/* 345 */     this.polyFillMode = polyFillMode;
/*     */   }
/*     */ 
/*     */   public void setLineJoinRectangle(PdfContentByte cb) {
/* 349 */     if (this.lineJoin != 0) {
/* 350 */       this.lineJoin = 0;
/* 351 */       cb.setLineJoin(0);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setLineJoinPolygon(PdfContentByte cb) {
/* 356 */     if (this.lineJoin == 0) {
/* 357 */       this.lineJoin = 1;
/* 358 */       cb.setLineJoin(1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean getLineNeutral() {
/* 363 */     return this.lineJoin == 0;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.codec.wmf.MetaState
 * JD-Core Version:    0.6.2
 */