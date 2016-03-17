/*     */ package org.apache.pdfbox.pdmodel.interactive.form;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSInteger;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.common.COSArrayList;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.pdmodel.common.PDTextStream;
/*     */ import org.apache.pdfbox.pdmodel.fdf.FDFField;
/*     */ import org.apache.pdfbox.pdmodel.interactive.action.PDFormFieldAdditionalActions;
/*     */ import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
/*     */ import org.apache.pdfbox.util.BitFlagHelper;
/*     */ 
/*     */ public abstract class PDField
/*     */   implements COSObjectable
/*     */ {
/*     */   public static final int FLAG_READ_ONLY = 1;
/*     */   public static final int FLAG_REQUIRED = 2;
/*     */   public static final int FLAG_NO_EXPORT = 4;
/*     */   private PDAcroForm acroForm;
/*     */   private COSDictionary dictionary;
/*     */ 
/*     */   public PDField(PDAcroForm theAcroForm)
/*     */   {
/*  68 */     this.acroForm = theAcroForm;
/*  69 */     this.dictionary = new COSDictionary();
/*     */   }
/*     */ 
/*     */   public PDField(PDAcroForm theAcroForm, COSDictionary field)
/*     */   {
/*  81 */     this.acroForm = theAcroForm;
/*  82 */     this.dictionary = field;
/*     */   }
/*     */ 
/*     */   public String getPartialName()
/*     */   {
/*  92 */     return getDictionary().getString(COSName.T);
/*     */   }
/*     */ 
/*     */   public void setPartialName(String name)
/*     */   {
/* 102 */     getDictionary().setString(COSName.T, name);
/*     */   }
/*     */ 
/*     */   public String getFullyQualifiedName()
/*     */     throws IOException
/*     */   {
/* 114 */     PDField parent = getParent();
/* 115 */     String parentName = null;
/* 116 */     if (parent != null)
/*     */     {
/* 118 */       parentName = parent.getFullyQualifiedName();
/*     */     }
/* 120 */     String finalName = getPartialName();
/* 121 */     if (parentName != null)
/*     */     {
/* 123 */       if (finalName != null)
/*     */       {
/* 125 */         finalName = parentName + "." + finalName;
/*     */       }
/*     */       else
/*     */       {
/* 129 */         finalName = parentName;
/*     */       }
/*     */     }
/* 132 */     return finalName;
/*     */   }
/*     */ 
/*     */   public String getAlternateFieldName()
/*     */   {
/* 142 */     return getDictionary().getString(COSName.TU);
/*     */   }
/*     */ 
/*     */   public void setAlternateFieldName(String alternateFieldName)
/*     */   {
/* 152 */     getDictionary().setString(COSName.TU, alternateFieldName);
/*     */   }
/*     */ 
/*     */   public String getFieldType()
/*     */   {
/* 166 */     return getDictionary().getNameAsString(COSName.FT);
/*     */   }
/*     */ 
/*     */   public String findFieldType()
/*     */   {
/* 179 */     return findFieldType(getDictionary());
/*     */   }
/*     */ 
/*     */   private String findFieldType(COSDictionary dic)
/*     */   {
/* 184 */     String retval = dic.getNameAsString(COSName.FT);
/* 185 */     if (retval == null)
/*     */     {
/* 187 */       COSDictionary parent = (COSDictionary)dic.getDictionaryObject(COSName.PARENT, COSName.P);
/* 188 */       if (parent != null)
/*     */       {
/* 190 */         retval = findFieldType(parent);
/*     */       }
/*     */     }
/* 193 */     return retval;
/*     */   }
/*     */ 
/*     */   public abstract void setValue(String paramString)
/*     */     throws IOException;
/*     */ 
/*     */   public abstract String getValue()
/*     */     throws IOException;
/*     */ 
/*     */   public void setReadonly(boolean readonly)
/*     */   {
/* 222 */     BitFlagHelper.setFlag(getDictionary(), COSName.FF, 1, readonly);
/*     */   }
/*     */ 
/*     */   public boolean isReadonly()
/*     */   {
/* 231 */     return BitFlagHelper.getFlag(getDictionary(), COSName.FF, 1);
/*     */   }
/*     */ 
/*     */   public void setRequired(boolean required)
/*     */   {
/* 241 */     BitFlagHelper.setFlag(getDictionary(), COSName.FF, 2, required);
/*     */   }
/*     */ 
/*     */   public boolean isRequired()
/*     */   {
/* 250 */     return BitFlagHelper.getFlag(getDictionary(), COSName.FF, 2);
/*     */   }
/*     */ 
/*     */   public void setNoExport(boolean noExport)
/*     */   {
/* 260 */     BitFlagHelper.setFlag(getDictionary(), COSName.FF, 4, noExport);
/*     */   }
/*     */ 
/*     */   public boolean isNoExport()
/*     */   {
/* 269 */     return BitFlagHelper.getFlag(getDictionary(), COSName.FF, 4);
/*     */   }
/*     */ 
/*     */   public int getFieldFlags()
/*     */   {
/* 279 */     int retval = 0;
/* 280 */     COSInteger ff = (COSInteger)getDictionary().getDictionaryObject(COSName.FF);
/* 281 */     if (ff != null)
/*     */     {
/* 283 */       retval = ff.intValue();
/*     */     }
/* 285 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setFieldFlags(int flags)
/*     */   {
/* 295 */     getDictionary().setInt(COSName.FF, flags);
/*     */   }
/*     */ 
/*     */   public void importFDF(FDFField fdfField)
/*     */     throws IOException
/*     */   {
/* 307 */     Object fieldValue = fdfField.getValue();
/* 308 */     int fieldFlags = getFieldFlags();
/*     */ 
/* 310 */     if (fieldValue != null)
/*     */     {
/* 312 */       if ((fieldValue instanceof String))
/*     */       {
/* 314 */         setValue((String)fieldValue);
/*     */       }
/* 316 */       else if ((fieldValue instanceof PDTextStream))
/*     */       {
/* 318 */         setValue(((PDTextStream)fieldValue).getAsString());
/*     */       }
/*     */       else
/*     */       {
/* 322 */         throw new IOException("Unknown field type:" + fieldValue.getClass().getName());
/*     */       }
/*     */     }
/* 325 */     Integer ff = fdfField.getFieldFlags();
/* 326 */     if (ff != null)
/*     */     {
/* 328 */       setFieldFlags(ff.intValue());
/*     */     }
/*     */     else
/*     */     {
/* 333 */       Integer setFf = fdfField.getSetFieldFlags();
/*     */ 
/* 335 */       if (setFf != null)
/*     */       {
/* 337 */         int setFfInt = setFf.intValue();
/* 338 */         fieldFlags |= setFfInt;
/* 339 */         setFieldFlags(fieldFlags);
/*     */       }
/*     */ 
/* 342 */       Integer clrFf = fdfField.getClearFieldFlags();
/* 343 */       if (clrFf != null)
/*     */       {
/* 353 */         int clrFfValue = clrFf.intValue();
/* 354 */         clrFfValue ^= -1;
/* 355 */         fieldFlags &= clrFfValue;
/* 356 */         setFieldFlags(fieldFlags);
/*     */       }
/*     */     }
/*     */ 
/* 360 */     PDAnnotationWidget widget = getWidget();
/* 361 */     if (widget != null)
/*     */     {
/* 363 */       int annotFlags = widget.getAnnotationFlags();
/* 364 */       Integer f = fdfField.getWidgetFieldFlags();
/* 365 */       if ((f != null) && (widget != null))
/*     */       {
/* 367 */         widget.setAnnotationFlags(f.intValue());
/*     */       }
/*     */       else
/*     */       {
/* 372 */         Integer setF = fdfField.getSetWidgetFieldFlags();
/* 373 */         if (setF != null)
/*     */         {
/* 375 */           annotFlags |= setF.intValue();
/* 376 */           widget.setAnnotationFlags(annotFlags);
/*     */         }
/*     */ 
/* 379 */         Integer clrF = fdfField.getClearWidgetFieldFlags();
/* 380 */         if (clrF != null)
/*     */         {
/* 390 */           int clrFValue = clrF.intValue();
/* 391 */           clrFValue = (int)(clrFValue ^ 0xFFFFFFFF);
/* 392 */           annotFlags &= clrFValue;
/* 393 */           widget.setAnnotationFlags(annotFlags);
/*     */         }
/*     */       }
/*     */     }
/* 397 */     List fdfKids = fdfField.getKids();
/* 398 */     List pdKids = getKids();
/* 399 */     for (int i = 0; (fdfKids != null) && (i < fdfKids.size()); i++)
/*     */     {
/* 401 */       FDFField fdfChild = (FDFField)fdfKids.get(i);
/* 402 */       String fdfName = fdfChild.getPartialFieldName();
/* 403 */       for (int j = 0; j < pdKids.size(); j++)
/*     */       {
/* 405 */         Object pdChildObj = pdKids.get(j);
/* 406 */         if ((pdChildObj instanceof PDField))
/*     */         {
/* 408 */           PDField pdChild = (PDField)pdChildObj;
/* 409 */           if ((fdfName != null) && (fdfName.equals(pdChild.getPartialName())))
/*     */           {
/* 411 */             pdChild.importFDF(fdfChild);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public PDAnnotationWidget getWidget()
/*     */     throws IOException
/*     */   {
/* 428 */     PDAnnotationWidget retval = null;
/* 429 */     List kids = getKids();
/* 430 */     if (kids == null)
/*     */     {
/* 432 */       retval = new PDAnnotationWidget(getDictionary());
/*     */     }
/* 434 */     else if (kids.size() > 0)
/*     */     {
/* 436 */       Object firstKid = kids.get(0);
/* 437 */       if ((firstKid instanceof PDAnnotationWidget))
/*     */       {
/* 439 */         retval = (PDAnnotationWidget)firstKid;
/*     */       }
/*     */       else
/*     */       {
/* 443 */         retval = ((PDField)firstKid).getWidget();
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 448 */       retval = null;
/*     */     }
/* 450 */     return retval;
/*     */   }
/*     */ 
/*     */   public PDField getParent()
/*     */     throws IOException
/*     */   {
/* 462 */     PDField parent = null;
/* 463 */     COSDictionary parentDic = (COSDictionary)getDictionary().getDictionaryObject(COSName.PARENT, COSName.P);
/* 464 */     if (parentDic != null)
/*     */     {
/* 466 */       parent = PDFieldFactory.createField(getAcroForm(), parentDic);
/*     */     }
/* 468 */     return parent;
/*     */   }
/*     */ 
/*     */   public void setParent(PDField parent)
/*     */   {
/* 478 */     getDictionary().setItem("Parent", parent);
/*     */   }
/*     */ 
/*     */   public PDField findKid(String[] name, int nameIndex)
/*     */     throws IOException
/*     */   {
/* 493 */     PDField retval = null;
/* 494 */     COSArray kids = (COSArray)getDictionary().getDictionaryObject(COSName.KIDS);
/* 495 */     if (kids != null)
/*     */     {
/* 497 */       for (int i = 0; (retval == null) && (i < kids.size()); i++)
/*     */       {
/* 499 */         COSDictionary kidDictionary = (COSDictionary)kids.getObject(i);
/* 500 */         if (name[nameIndex].equals(kidDictionary.getString("T")))
/*     */         {
/* 502 */           retval = PDFieldFactory.createField(this.acroForm, kidDictionary);
/* 503 */           if (name.length > nameIndex + 1)
/*     */           {
/* 505 */             retval = retval.findKid(name, nameIndex + 1);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 510 */     return retval;
/*     */   }
/*     */ 
/*     */   public List<COSObjectable> getKids()
/*     */     throws IOException
/*     */   {
/* 522 */     List retval = null;
/* 523 */     COSArray kids = (COSArray)getDictionary().getDictionaryObject(COSName.KIDS);
/* 524 */     if (kids != null)
/*     */     {
/* 526 */       List kidsList = new ArrayList();
/* 527 */       for (int i = 0; i < kids.size(); i++)
/*     */       {
/* 529 */         COSDictionary kidDictionary = (COSDictionary)kids.getObject(i);
/* 530 */         if (kidDictionary != null)
/*     */         {
/* 534 */           COSDictionary parent = (COSDictionary)kidDictionary.getDictionaryObject(COSName.PARENT, COSName.P);
/* 535 */           if ((kidDictionary.getDictionaryObject(COSName.FT) != null) || ((parent != null) && (parent.getDictionaryObject(COSName.FT) != null)))
/*     */           {
/* 538 */             PDField field = PDFieldFactory.createField(this.acroForm, kidDictionary);
/* 539 */             if (field != null)
/*     */             {
/* 541 */               kidsList.add(field);
/*     */             }
/*     */           }
/* 544 */           else if ("Widget".equals(kidDictionary.getNameAsString(COSName.SUBTYPE)))
/*     */           {
/* 546 */             kidsList.add(new PDAnnotationWidget(kidDictionary));
/*     */           }
/*     */           else
/*     */           {
/* 550 */             PDField field = PDFieldFactory.createField(this.acroForm, kidDictionary);
/* 551 */             if (field != null)
/*     */             {
/* 553 */               kidsList.add(field);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 557 */       retval = new COSArrayList(kidsList, kids);
/*     */     }
/* 559 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setKids(List<COSObjectable> kids)
/*     */   {
/* 569 */     COSArray kidsArray = COSArrayList.converterToCOSArray(kids);
/* 570 */     getDictionary().setItem(COSName.KIDS, kidsArray);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 581 */     return "" + getDictionary().getDictionaryObject(COSName.V);
/*     */   }
/*     */ 
/*     */   public PDAcroForm getAcroForm()
/*     */   {
/* 591 */     return this.acroForm;
/*     */   }
/*     */ 
/*     */   public void setAcroForm(PDAcroForm value)
/*     */   {
/* 601 */     this.acroForm = value;
/*     */   }
/*     */ 
/*     */   public COSDictionary getDictionary()
/*     */   {
/* 611 */     return this.dictionary;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/* 621 */     return this.dictionary;
/*     */   }
/*     */ 
/*     */   public PDFormFieldAdditionalActions getActions()
/*     */   {
/* 632 */     COSDictionary aa = (COSDictionary)this.dictionary.getDictionaryObject(COSName.AA);
/* 633 */     PDFormFieldAdditionalActions retval = null;
/* 634 */     if (aa != null)
/*     */     {
/* 636 */       retval = new PDFormFieldAdditionalActions(aa);
/*     */     }
/* 638 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setActions(PDFormFieldAdditionalActions actions)
/*     */   {
/* 648 */     this.dictionary.setItem(COSName.AA, actions);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.form.PDField
 * JD-Core Version:    0.6.2
 */