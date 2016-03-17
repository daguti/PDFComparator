/*     */ package org.apache.pdfbox.pdmodel.documentinterchange.taggedpdf;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSFloat;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ import org.apache.pdfbox.cos.COSString;
/*     */ import org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDAttributeObject;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDGamma;
/*     */ 
/*     */ public abstract class PDStandardAttributeObject extends PDAttributeObject
/*     */ {
/*     */   protected static final float UNSPECIFIED = -1.0F;
/*     */ 
/*     */   public PDStandardAttributeObject()
/*     */   {
/*     */   }
/*     */ 
/*     */   public PDStandardAttributeObject(COSDictionary dictionary)
/*     */   {
/*  52 */     super(dictionary);
/*     */   }
/*     */ 
/*     */   public boolean isSpecified(String name)
/*     */   {
/*  65 */     return getCOSDictionary().getDictionaryObject(name) != null;
/*     */   }
/*     */ 
/*     */   protected String getString(String name)
/*     */   {
/*  77 */     return getCOSDictionary().getString(name);
/*     */   }
/*     */ 
/*     */   protected void setString(String name, String value)
/*     */   {
/*  88 */     COSBase oldBase = getCOSDictionary().getDictionaryObject(name);
/*  89 */     getCOSDictionary().setString(name, value);
/*  90 */     COSBase newBase = getCOSDictionary().getDictionaryObject(name);
/*  91 */     potentiallyNotifyChanged(oldBase, newBase);
/*     */   }
/*     */ 
/*     */   protected String[] getArrayOfString(String name)
/*     */   {
/* 102 */     COSBase v = getCOSDictionary().getDictionaryObject(name);
/* 103 */     if ((v instanceof COSArray))
/*     */     {
/* 105 */       COSArray array = (COSArray)v;
/* 106 */       String[] strings = new String[array.size()];
/* 107 */       for (int i = 0; i < array.size(); i++)
/*     */       {
/* 109 */         strings[i] = ((COSName)array.getObject(i)).getName();
/*     */       }
/* 111 */       return strings;
/*     */     }
/* 113 */     return null;
/*     */   }
/*     */ 
/*     */   protected void setArrayOfString(String name, String[] values)
/*     */   {
/* 124 */     COSBase oldBase = getCOSDictionary().getDictionaryObject(name);
/* 125 */     COSArray array = new COSArray();
/* 126 */     for (int i = 0; i < values.length; i++)
/*     */     {
/* 128 */       array.add(new COSString(values[i]));
/*     */     }
/* 130 */     getCOSDictionary().setItem(name, array);
/* 131 */     COSBase newBase = getCOSDictionary().getDictionaryObject(name);
/* 132 */     potentiallyNotifyChanged(oldBase, newBase);
/*     */   }
/*     */ 
/*     */   protected String getName(String name)
/*     */   {
/* 143 */     return getCOSDictionary().getNameAsString(name);
/*     */   }
/*     */ 
/*     */   protected String getName(String name, String defaultValue)
/*     */   {
/* 155 */     return getCOSDictionary().getNameAsString(name, defaultValue);
/*     */   }
/*     */ 
/*     */   protected Object getNameOrArrayOfName(String name, String defaultValue)
/*     */   {
/* 167 */     COSBase v = getCOSDictionary().getDictionaryObject(name);
/* 168 */     if ((v instanceof COSArray))
/*     */     {
/* 170 */       COSArray array = (COSArray)v;
/* 171 */       String[] names = new String[array.size()];
/* 172 */       for (int i = 0; i < array.size(); i++)
/*     */       {
/* 174 */         COSBase item = array.getObject(i);
/* 175 */         if ((item instanceof COSName))
/*     */         {
/* 177 */           names[i] = ((COSName)item).getName();
/*     */         }
/*     */       }
/* 180 */       return names;
/*     */     }
/* 182 */     if ((v instanceof COSName))
/*     */     {
/* 184 */       return ((COSName)v).getName();
/*     */     }
/* 186 */     return defaultValue;
/*     */   }
/*     */ 
/*     */   protected void setName(String name, String value)
/*     */   {
/* 197 */     COSBase oldBase = getCOSDictionary().getDictionaryObject(name);
/* 198 */     getCOSDictionary().setName(name, value);
/* 199 */     COSBase newBase = getCOSDictionary().getDictionaryObject(name);
/* 200 */     potentiallyNotifyChanged(oldBase, newBase);
/*     */   }
/*     */ 
/*     */   protected void setArrayOfName(String name, String[] values)
/*     */   {
/* 211 */     COSBase oldBase = getCOSDictionary().getDictionaryObject(name);
/* 212 */     COSArray array = new COSArray();
/* 213 */     for (int i = 0; i < values.length; i++)
/*     */     {
/* 215 */       array.add(COSName.getPDFName(values[i]));
/*     */     }
/* 217 */     getCOSDictionary().setItem(name, array);
/* 218 */     COSBase newBase = getCOSDictionary().getDictionaryObject(name);
/* 219 */     potentiallyNotifyChanged(oldBase, newBase);
/*     */   }
/*     */ 
/*     */   protected Object getNumberOrName(String name, String defaultValue)
/*     */   {
/* 231 */     COSBase value = getCOSDictionary().getDictionaryObject(name);
/* 232 */     if ((value instanceof COSNumber))
/*     */     {
/* 234 */       return Float.valueOf(((COSNumber)value).floatValue());
/*     */     }
/* 236 */     if ((value instanceof COSName))
/*     */     {
/* 238 */       return ((COSName)value).getName();
/*     */     }
/* 240 */     return defaultValue;
/*     */   }
/*     */ 
/*     */   protected int getInteger(String name, int defaultValue)
/*     */   {
/* 252 */     return getCOSDictionary().getInt(name, defaultValue);
/*     */   }
/*     */ 
/*     */   protected void setInteger(String name, int value)
/*     */   {
/* 263 */     COSBase oldBase = getCOSDictionary().getDictionaryObject(name);
/* 264 */     getCOSDictionary().setInt(name, value);
/* 265 */     COSBase newBase = getCOSDictionary().getDictionaryObject(name);
/* 266 */     potentiallyNotifyChanged(oldBase, newBase);
/*     */   }
/*     */ 
/*     */   protected float getNumber(String name, float defaultValue)
/*     */   {
/* 278 */     return getCOSDictionary().getFloat(name, defaultValue);
/*     */   }
/*     */ 
/*     */   protected float getNumber(String name)
/*     */   {
/* 289 */     return getCOSDictionary().getFloat(name);
/*     */   }
/*     */ 
/*     */   protected Object getNumberOrArrayOfNumber(String name, float defaultValue)
/*     */   {
/* 306 */     COSBase v = getCOSDictionary().getDictionaryObject(name);
/* 307 */     if ((v instanceof COSArray))
/*     */     {
/* 309 */       COSArray array = (COSArray)v;
/* 310 */       float[] values = new float[array.size()];
/* 311 */       for (int i = 0; i < array.size(); i++)
/*     */       {
/* 313 */         COSBase item = array.getObject(i);
/* 314 */         if ((item instanceof COSNumber))
/*     */         {
/* 316 */           values[i] = ((COSNumber)item).floatValue();
/*     */         }
/*     */       }
/* 319 */       return values;
/*     */     }
/* 321 */     if ((v instanceof COSNumber))
/*     */     {
/* 323 */       return Float.valueOf(((COSNumber)v).floatValue());
/*     */     }
/* 325 */     if (defaultValue == -1.0F)
/*     */     {
/* 327 */       return null;
/*     */     }
/* 329 */     return Float.valueOf(defaultValue);
/*     */   }
/*     */ 
/*     */   protected void setNumber(String name, float value)
/*     */   {
/* 340 */     COSBase oldBase = getCOSDictionary().getDictionaryObject(name);
/* 341 */     getCOSDictionary().setFloat(name, value);
/* 342 */     COSBase newBase = getCOSDictionary().getDictionaryObject(name);
/* 343 */     potentiallyNotifyChanged(oldBase, newBase);
/*     */   }
/*     */ 
/*     */   protected void setNumber(String name, int value)
/*     */   {
/* 354 */     COSBase oldBase = getCOSDictionary().getDictionaryObject(name);
/* 355 */     getCOSDictionary().setInt(name, value);
/* 356 */     COSBase newBase = getCOSDictionary().getDictionaryObject(name);
/* 357 */     potentiallyNotifyChanged(oldBase, newBase);
/*     */   }
/*     */ 
/*     */   protected void setArrayOfNumber(String name, float[] values)
/*     */   {
/* 368 */     COSArray array = new COSArray();
/* 369 */     for (int i = 0; i < values.length; i++)
/*     */     {
/* 371 */       array.add(new COSFloat(values[i]));
/*     */     }
/* 373 */     COSBase oldBase = getCOSDictionary().getDictionaryObject(name);
/* 374 */     getCOSDictionary().setItem(name, array);
/* 375 */     COSBase newBase = getCOSDictionary().getDictionaryObject(name);
/* 376 */     potentiallyNotifyChanged(oldBase, newBase);
/*     */   }
/*     */ 
/*     */   protected PDGamma getColor(String name)
/*     */   {
/* 387 */     COSArray c = (COSArray)getCOSDictionary().getDictionaryObject(name);
/* 388 */     if (c != null)
/*     */     {
/* 390 */       return new PDGamma(c);
/*     */     }
/* 392 */     return null;
/*     */   }
/*     */ 
/*     */   protected Object getColorOrFourColors(String name)
/*     */   {
/* 403 */     COSArray array = (COSArray)getCOSDictionary().getDictionaryObject(name);
/*     */ 
/* 405 */     if (array == null)
/*     */     {
/* 407 */       return null;
/*     */     }
/* 409 */     if (array.size() == 3)
/*     */     {
/* 412 */       return new PDGamma(array);
/*     */     }
/* 414 */     if (array.size() == 4)
/*     */     {
/* 416 */       return new PDFourColours(array);
/*     */     }
/* 418 */     return null;
/*     */   }
/*     */ 
/*     */   protected void setColor(String name, PDGamma value)
/*     */   {
/* 429 */     COSBase oldValue = getCOSDictionary().getDictionaryObject(name);
/* 430 */     getCOSDictionary().setItem(name, value);
/* 431 */     COSBase newValue = value == null ? null : value.getCOSObject();
/* 432 */     potentiallyNotifyChanged(oldValue, newValue);
/*     */   }
/*     */ 
/*     */   protected void setFourColors(String name, PDFourColours value)
/*     */   {
/* 443 */     COSBase oldValue = getCOSDictionary().getDictionaryObject(name);
/* 444 */     getCOSDictionary().setItem(name, value);
/* 445 */     COSBase newValue = value == null ? null : value.getCOSObject();
/* 446 */     potentiallyNotifyChanged(oldValue, newValue);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.documentinterchange.taggedpdf.PDStandardAttributeObject
 * JD-Core Version:    0.6.2
 */