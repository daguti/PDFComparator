/*     */ package org.apache.fontbox.ttf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.apache.fontbox.util.BoundingBox;
/*     */ 
/*     */ public class GlyphData
/*     */ {
/*     */   private short xMin;
/*     */   private short yMin;
/*     */   private short xMax;
/*     */   private short yMax;
/*  34 */   private BoundingBox boundingBox = null;
/*     */   private short numberOfContours;
/*  36 */   private GlyfDescript glyphDescription = null;
/*     */ 
/*     */   public void initData(GlyphTable glyphTable, TTFDataStream data)
/*     */     throws IOException
/*     */   {
/*  47 */     this.numberOfContours = data.readSignedShort();
/*  48 */     this.xMin = data.readSignedShort();
/*  49 */     this.yMin = data.readSignedShort();
/*  50 */     this.xMax = data.readSignedShort();
/*  51 */     this.yMax = data.readSignedShort();
/*  52 */     this.boundingBox = new BoundingBox(this.xMin, this.yMin, this.xMax, this.yMax);
/*     */ 
/*  54 */     if (this.numberOfContours >= 0)
/*     */     {
/*  57 */       this.glyphDescription = new GlyfSimpleDescript(this.numberOfContours, data);
/*     */     }
/*     */     else
/*     */     {
/*  62 */       this.glyphDescription = new GlyfCompositeDescript(data, glyphTable);
/*     */     }
/*     */   }
/*     */ 
/*     */   public BoundingBox getBoundingBox()
/*     */   {
/*  71 */     return this.boundingBox;
/*     */   }
/*     */ 
/*     */   public void setBoundingBox(BoundingBox boundingBoxValue)
/*     */   {
/*  78 */     this.boundingBox = boundingBoxValue;
/*     */   }
/*     */ 
/*     */   public short getNumberOfContours()
/*     */   {
/*  85 */     return this.numberOfContours;
/*     */   }
/*     */ 
/*     */   public void setNumberOfContours(short numberOfContoursValue)
/*     */   {
/*  92 */     this.numberOfContours = numberOfContoursValue;
/*     */   }
/*     */ 
/*     */   public GlyphDescription getDescription()
/*     */   {
/* 101 */     return this.glyphDescription;
/*     */   }
/*     */ 
/*     */   public short getXMaximum()
/*     */   {
/* 110 */     return this.xMax;
/*     */   }
/*     */ 
/*     */   public short getXMinimum()
/*     */   {
/* 119 */     return this.xMin;
/*     */   }
/*     */ 
/*     */   public short getYMaximum()
/*     */   {
/* 128 */     return this.yMax;
/*     */   }
/*     */ 
/*     */   public short getYMinimum()
/*     */   {
/* 137 */     return this.yMin;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.ttf.GlyphData
 * JD-Core Version:    0.6.2
 */