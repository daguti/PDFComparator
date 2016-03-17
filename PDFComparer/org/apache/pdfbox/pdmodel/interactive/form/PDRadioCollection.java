/*     */ package org.apache.pdfbox.pdmodel.interactive.form;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.common.COSArrayList;
/*     */ import org.apache.pdfbox.util.BitFlagHelper;
/*     */ 
/*     */ public class PDRadioCollection extends PDChoiceButton
/*     */ {
/*     */   public static final int FLAG_RADIOS_IN_UNISON = 33554432;
/*     */ 
/*     */   public PDRadioCollection(PDAcroForm theAcroForm, COSDictionary field)
/*     */   {
/*  54 */     super(theAcroForm, field);
/*     */   }
/*     */ 
/*     */   public void setRadiosInUnison(boolean radiosInUnison)
/*     */   {
/*  68 */     BitFlagHelper.setFlag(getDictionary(), COSName.FF, 33554432, radiosInUnison);
/*     */   }
/*     */ 
/*     */   public boolean isRadiosInUnison()
/*     */   {
/*  77 */     return BitFlagHelper.getFlag(getDictionary(), COSName.FF, 33554432);
/*     */   }
/*     */ 
/*     */   public void setValue(String value)
/*     */     throws IOException
/*     */   {
/*  91 */     getDictionary().setString(COSName.V, value);
/*  92 */     List kids = getKids();
/*  93 */     for (int i = 0; i < kids.size(); i++)
/*     */     {
/*  95 */       PDField field = (PDField)kids.get(i);
/*  96 */       if ((field instanceof PDCheckbox))
/*     */       {
/*  98 */         PDCheckbox btn = (PDCheckbox)field;
/*  99 */         if (btn.getOnValue().equals(value))
/*     */         {
/* 101 */           btn.check();
/*     */         }
/*     */         else
/*     */         {
/* 105 */           btn.unCheck();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getValue()
/*     */     throws IOException
/*     */   {
/* 120 */     String retval = null;
/* 121 */     List kids = getKids();
/* 122 */     for (int i = 0; i < kids.size(); i++)
/*     */     {
/* 124 */       PDField kid = (PDField)kids.get(i);
/* 125 */       if ((kid instanceof PDCheckbox))
/*     */       {
/* 127 */         PDCheckbox btn = (PDCheckbox)kid;
/* 128 */         if (btn.isChecked())
/*     */         {
/* 130 */           retval = btn.getOnValue();
/*     */         }
/*     */       }
/*     */     }
/* 134 */     if (retval == null)
/*     */     {
/* 136 */       retval = getDictionary().getNameAsString(COSName.V);
/*     */     }
/* 138 */     return retval;
/*     */   }
/*     */ 
/*     */   public List getKids()
/*     */     throws IOException
/*     */   {
/* 152 */     COSArray kids = (COSArray)getDictionary().getDictionaryObject(COSName.KIDS);
/* 153 */     if (kids != null)
/*     */     {
/* 155 */       List kidsList = new ArrayList();
/* 156 */       for (int i = 0; i < kids.size(); i++)
/*     */       {
/* 158 */         PDField field = PDFieldFactory.createField(getAcroForm(), (COSDictionary)kids.getObject(i));
/* 159 */         if (field != null)
/*     */         {
/* 161 */           kidsList.add(field);
/*     */         }
/*     */       }
/* 164 */       return new COSArrayList(kidsList, kids);
/*     */     }
/*     */ 
/* 168 */     return new ArrayList();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.form.PDRadioCollection
 * JD-Core Version:    0.6.2
 */