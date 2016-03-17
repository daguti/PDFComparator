/*     */ package org.apache.fontbox.afm;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.fontbox.util.BoundingBox;
/*     */ 
/*     */ public class CharMetric
/*     */ {
/*     */   private int characterCode;
/*     */   private float wx;
/*     */   private float w0x;
/*     */   private float w1x;
/*     */   private float wy;
/*     */   private float w0y;
/*     */   private float w1y;
/*     */   private float[] w;
/*     */   private float[] w0;
/*     */   private float[] w1;
/*     */   private float[] vv;
/*     */   private String name;
/*     */   private BoundingBox boundingBox;
/*  49 */   private List<Ligature> ligatures = new ArrayList();
/*     */ 
/*     */   public BoundingBox getBoundingBox()
/*     */   {
/*  56 */     return this.boundingBox;
/*     */   }
/*     */ 
/*     */   public void setBoundingBox(BoundingBox bBox)
/*     */   {
/*  64 */     this.boundingBox = bBox;
/*     */   }
/*     */ 
/*     */   public int getCharacterCode()
/*     */   {
/*  72 */     return this.characterCode;
/*     */   }
/*     */ 
/*     */   public void setCharacterCode(int cCode)
/*     */   {
/*  80 */     this.characterCode = cCode;
/*     */   }
/*     */ 
/*     */   public void addLigature(Ligature ligature)
/*     */   {
/*  90 */     this.ligatures.add(ligature);
/*     */   }
/*     */ 
/*     */   public List<Ligature> getLigatures()
/*     */   {
/*  98 */     return this.ligatures;
/*     */   }
/*     */ 
/*     */   public void setLigatures(List<Ligature> lig)
/*     */   {
/* 106 */     this.ligatures = lig;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 114 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String n)
/*     */   {
/* 122 */     this.name = n;
/*     */   }
/*     */ 
/*     */   public float[] getVv()
/*     */   {
/* 130 */     return this.vv;
/*     */   }
/*     */ 
/*     */   public void setVv(float[] vvValue)
/*     */   {
/* 138 */     this.vv = vvValue;
/*     */   }
/*     */ 
/*     */   public float[] getW()
/*     */   {
/* 146 */     return this.w;
/*     */   }
/*     */ 
/*     */   public void setW(float[] wValue)
/*     */   {
/* 154 */     this.w = wValue;
/*     */   }
/*     */ 
/*     */   public float[] getW0()
/*     */   {
/* 162 */     return this.w0;
/*     */   }
/*     */ 
/*     */   public void setW0(float[] w0Value)
/*     */   {
/* 170 */     this.w0 = w0Value;
/*     */   }
/*     */ 
/*     */   public float getW0x()
/*     */   {
/* 178 */     return this.w0x;
/*     */   }
/*     */ 
/*     */   public void setW0x(float w0xValue)
/*     */   {
/* 186 */     this.w0x = w0xValue;
/*     */   }
/*     */ 
/*     */   public float getW0y()
/*     */   {
/* 194 */     return this.w0y;
/*     */   }
/*     */ 
/*     */   public void setW0y(float w0yValue)
/*     */   {
/* 202 */     this.w0y = w0yValue;
/*     */   }
/*     */ 
/*     */   public float[] getW1()
/*     */   {
/* 210 */     return this.w1;
/*     */   }
/*     */ 
/*     */   public void setW1(float[] w1Value)
/*     */   {
/* 218 */     this.w1 = w1Value;
/*     */   }
/*     */ 
/*     */   public float getW1x()
/*     */   {
/* 226 */     return this.w1x;
/*     */   }
/*     */ 
/*     */   public void setW1x(float w1xValue)
/*     */   {
/* 234 */     this.w1x = w1xValue;
/*     */   }
/*     */ 
/*     */   public float getW1y()
/*     */   {
/* 242 */     return this.w1y;
/*     */   }
/*     */ 
/*     */   public void setW1y(float w1yValue)
/*     */   {
/* 250 */     this.w1y = w1yValue;
/*     */   }
/*     */ 
/*     */   public float getWx()
/*     */   {
/* 258 */     return this.wx;
/*     */   }
/*     */ 
/*     */   public void setWx(float wxValue)
/*     */   {
/* 266 */     this.wx = wxValue;
/*     */   }
/*     */ 
/*     */   public float getWy()
/*     */   {
/* 274 */     return this.wy;
/*     */   }
/*     */ 
/*     */   public void setWy(float wyValue)
/*     */   {
/* 282 */     this.wy = wyValue;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.afm.CharMetric
 * JD-Core Version:    0.6.2
 */