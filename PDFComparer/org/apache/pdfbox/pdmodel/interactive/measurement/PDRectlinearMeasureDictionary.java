/*     */ package org.apache.pdfbox.pdmodel.interactive.measurement;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ 
/*     */ public class PDRectlinearMeasureDictionary extends PDMeasureDictionary
/*     */ {
/*     */   public static final String SUBTYPE = "RL";
/*     */ 
/*     */   public PDRectlinearMeasureDictionary()
/*     */   {
/*  42 */     setSubtype("RL");
/*     */   }
/*     */ 
/*     */   public PDRectlinearMeasureDictionary(COSDictionary dictionary)
/*     */   {
/*  52 */     super(dictionary);
/*     */   }
/*     */ 
/*     */   public String getScaleRatio()
/*     */   {
/*  62 */     return getDictionary().getString(COSName.R);
/*     */   }
/*     */ 
/*     */   public void setScaleRatio(String scaleRatio)
/*     */   {
/*  72 */     getDictionary().setString(COSName.R, scaleRatio);
/*     */   }
/*     */ 
/*     */   public PDNumberFormatDictionary[] getChangeXs()
/*     */   {
/*  82 */     COSArray x = (COSArray)getDictionary().getDictionaryObject("X");
/*  83 */     if (x != null)
/*     */     {
/*  85 */       PDNumberFormatDictionary[] retval = new PDNumberFormatDictionary[x.size()];
/*     */ 
/*  87 */       for (int i = 0; i < x.size(); i++)
/*     */       {
/*  89 */         COSDictionary dic = (COSDictionary)x.get(i);
/*  90 */         retval[i] = new PDNumberFormatDictionary(dic);
/*     */       }
/*  92 */       return retval;
/*     */     }
/*  94 */     return null;
/*     */   }
/*     */ 
/*     */   public void setChangeXs(PDNumberFormatDictionary[] changeXs)
/*     */   {
/* 104 */     COSArray array = new COSArray();
/* 105 */     for (int i = 0; i < changeXs.length; i++)
/*     */     {
/* 107 */       array.add(changeXs[i]);
/*     */     }
/* 109 */     getDictionary().setItem("X", array);
/*     */   }
/*     */ 
/*     */   public PDNumberFormatDictionary[] getChangeYs()
/*     */   {
/* 119 */     COSArray y = (COSArray)getDictionary().getDictionaryObject("Y");
/* 120 */     if (y != null)
/*     */     {
/* 122 */       PDNumberFormatDictionary[] retval = new PDNumberFormatDictionary[y.size()];
/*     */ 
/* 124 */       for (int i = 0; i < y.size(); i++)
/*     */       {
/* 126 */         COSDictionary dic = (COSDictionary)y.get(i);
/* 127 */         retval[i] = new PDNumberFormatDictionary(dic);
/*     */       }
/* 129 */       return retval;
/*     */     }
/* 131 */     return null;
/*     */   }
/*     */ 
/*     */   public void setChangeYs(PDNumberFormatDictionary[] changeYs)
/*     */   {
/* 141 */     COSArray array = new COSArray();
/* 142 */     for (int i = 0; i < changeYs.length; i++)
/*     */     {
/* 144 */       array.add(changeYs[i]);
/*     */     }
/* 146 */     getDictionary().setItem("Y", array);
/*     */   }
/*     */ 
/*     */   public PDNumberFormatDictionary[] getDistances()
/*     */   {
/* 156 */     COSArray d = (COSArray)getDictionary().getDictionaryObject("D");
/* 157 */     if (d != null)
/*     */     {
/* 159 */       PDNumberFormatDictionary[] retval = new PDNumberFormatDictionary[d.size()];
/*     */ 
/* 161 */       for (int i = 0; i < d.size(); i++)
/*     */       {
/* 163 */         COSDictionary dic = (COSDictionary)d.get(i);
/* 164 */         retval[i] = new PDNumberFormatDictionary(dic);
/*     */       }
/* 166 */       return retval;
/*     */     }
/* 168 */     return null;
/*     */   }
/*     */ 
/*     */   public void setDistances(PDNumberFormatDictionary[] distances)
/*     */   {
/* 178 */     COSArray array = new COSArray();
/* 179 */     for (int i = 0; i < distances.length; i++)
/*     */     {
/* 181 */       array.add(distances[i]);
/*     */     }
/* 183 */     getDictionary().setItem("D", array);
/*     */   }
/*     */ 
/*     */   public PDNumberFormatDictionary[] getAreas()
/*     */   {
/* 193 */     COSArray a = (COSArray)getDictionary().getDictionaryObject(COSName.A);
/* 194 */     if (a != null)
/*     */     {
/* 196 */       PDNumberFormatDictionary[] retval = new PDNumberFormatDictionary[a.size()];
/*     */ 
/* 198 */       for (int i = 0; i < a.size(); i++)
/*     */       {
/* 200 */         COSDictionary dic = (COSDictionary)a.get(i);
/* 201 */         retval[i] = new PDNumberFormatDictionary(dic);
/*     */       }
/* 203 */       return retval;
/*     */     }
/* 205 */     return null;
/*     */   }
/*     */ 
/*     */   public void setAreas(PDNumberFormatDictionary[] areas)
/*     */   {
/* 215 */     COSArray array = new COSArray();
/* 216 */     for (int i = 0; i < areas.length; i++)
/*     */     {
/* 218 */       array.add(areas[i]);
/*     */     }
/* 220 */     getDictionary().setItem(COSName.A, array);
/*     */   }
/*     */ 
/*     */   public PDNumberFormatDictionary[] getAngles()
/*     */   {
/* 230 */     COSArray t = (COSArray)getDictionary().getDictionaryObject("T");
/* 231 */     if (t != null)
/*     */     {
/* 233 */       PDNumberFormatDictionary[] retval = new PDNumberFormatDictionary[t.size()];
/*     */ 
/* 235 */       for (int i = 0; i < t.size(); i++)
/*     */       {
/* 237 */         COSDictionary dic = (COSDictionary)t.get(i);
/* 238 */         retval[i] = new PDNumberFormatDictionary(dic);
/*     */       }
/* 240 */       return retval;
/*     */     }
/* 242 */     return null;
/*     */   }
/*     */ 
/*     */   public void setAngles(PDNumberFormatDictionary[] angles)
/*     */   {
/* 252 */     COSArray array = new COSArray();
/* 253 */     for (int i = 0; i < angles.length; i++)
/*     */     {
/* 255 */       array.add(angles[i]);
/*     */     }
/* 257 */     getDictionary().setItem("T", array);
/*     */   }
/*     */ 
/*     */   public PDNumberFormatDictionary[] getLineSloaps()
/*     */   {
/* 267 */     COSArray s = (COSArray)getDictionary().getDictionaryObject("S");
/* 268 */     if (s != null)
/*     */     {
/* 270 */       PDNumberFormatDictionary[] retval = new PDNumberFormatDictionary[s.size()];
/*     */ 
/* 272 */       for (int i = 0; i < s.size(); i++)
/*     */       {
/* 274 */         COSDictionary dic = (COSDictionary)s.get(i);
/* 275 */         retval[i] = new PDNumberFormatDictionary(dic);
/*     */       }
/* 277 */       return retval;
/*     */     }
/* 279 */     return null;
/*     */   }
/*     */ 
/*     */   public void setLineSloaps(PDNumberFormatDictionary[] lineSloaps)
/*     */   {
/* 289 */     COSArray array = new COSArray();
/* 290 */     for (int i = 0; i < lineSloaps.length; i++)
/*     */     {
/* 292 */       array.add(lineSloaps[i]);
/*     */     }
/* 294 */     getDictionary().setItem("S", array);
/*     */   }
/*     */ 
/*     */   public float[] getCoordSystemOrigin()
/*     */   {
/* 304 */     COSArray o = (COSArray)getDictionary().getDictionaryObject("O");
/* 305 */     if (o != null)
/*     */     {
/* 307 */       return o.toFloatArray();
/*     */     }
/* 309 */     return null;
/*     */   }
/*     */ 
/*     */   public void setCoordSystemOrigin(float[] coordSystemOrigin)
/*     */   {
/* 319 */     COSArray array = new COSArray();
/* 320 */     array.setFloatArray(coordSystemOrigin);
/* 321 */     getDictionary().setItem("O", array);
/*     */   }
/*     */ 
/*     */   public float getCYX()
/*     */   {
/* 331 */     return getDictionary().getFloat("CYX");
/*     */   }
/*     */ 
/*     */   public void setCYX(float cyx)
/*     */   {
/* 341 */     getDictionary().setFloat("CYX", cyx);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.measurement.PDRectlinearMeasureDictionary
 * JD-Core Version:    0.6.2
 */