/*     */ package org.apache.pdfbox.pdmodel.interactive.form;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSInteger;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSString;
/*     */ 
/*     */ public class PDChoiceField extends PDVariableText
/*     */ {
/*     */   public static final int FLAG_COMBO = 131072;
/*     */   public static final int FLAG_EDIT = 262144;
/*     */   private PDAppearance appearance;
/*     */ 
/*     */   public PDChoiceField(PDAcroForm theAcroForm, COSDictionary field)
/*     */   {
/*  58 */     super(theAcroForm, field);
/*     */   }
/*     */ 
/*     */   private void setListboxValue(String value) throws IOException
/*     */   {
/*  63 */     COSString fieldValue = new COSString(value);
/*  64 */     getDictionary().setItem(COSName.V, fieldValue);
/*     */ 
/*  70 */     if (this.appearance == null)
/*     */     {
/*  72 */       this.appearance = new PDAppearance(getAcroForm(), this);
/*     */     }
/*  74 */     this.appearance.setAppearanceValue(value);
/*     */   }
/*     */ 
/*     */   public void setValue(String optionValue)
/*     */     throws IOException
/*     */   {
/*  89 */     int indexSelected = -1;
/*  90 */     COSArray options = (COSArray)getDictionary().getDictionaryObject(COSName.OPT);
/*  91 */     int fieldFlags = getFieldFlags();
/*  92 */     boolean isEditable = ((0x20000 & fieldFlags) != 0) && ((0x40000 & fieldFlags) != 0);
/*     */ 
/*  94 */     if ((options.size() == 0) && (!isEditable))
/*     */     {
/*  96 */       throw new IOException("Error: You cannot set a value for a choice field if there are no options.");
/*     */     }
/*     */ 
/* 103 */     for (int i = 0; (i < options.size()) && (indexSelected == -1); i++)
/*     */     {
/* 105 */       COSBase option = options.getObject(i);
/* 106 */       if ((option instanceof COSArray))
/*     */       {
/* 108 */         COSArray keyValuePair = (COSArray)option;
/* 109 */         COSString key = (COSString)keyValuePair.getObject(0);
/* 110 */         COSString value = (COSString)keyValuePair.getObject(1);
/* 111 */         if ((optionValue.equals(key.getString())) || (optionValue.equals(value.getString())))
/*     */         {
/* 114 */           if ((0x20000 & fieldFlags) != 0)
/*     */           {
/* 116 */             super.setValue(value.getString());
/*     */           }
/*     */           else
/*     */           {
/* 120 */             COSArray indexEntries = new COSArray();
/* 121 */             indexEntries.add(COSInteger.get(i));
/* 122 */             getDictionary().setItem(COSName.I, indexEntries);
/* 123 */             setListboxValue(value.getString());
/*     */           }
/*     */ 
/* 126 */           getDictionary().setItem(COSName.V, key);
/* 127 */           indexSelected = i;
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 133 */         COSString value = (COSString)option;
/* 134 */         if (optionValue.equals(value.getString()))
/*     */         {
/* 136 */           super.setValue(optionValue);
/* 137 */           indexSelected = i;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 142 */     if ((indexSelected == -1) && (isEditable))
/*     */     {
/* 144 */       super.setValue(optionValue);
/*     */     } else {
/* 146 */       if (indexSelected == -1)
/*     */       {
/* 148 */         throw new IOException("Error: '" + optionValue + "' was not an available option.");
/*     */       }
/*     */ 
/* 152 */       COSArray indexArray = (COSArray)getDictionary().getDictionaryObject(COSName.I);
/* 153 */       if (indexArray != null)
/*     */       {
/* 155 */         indexArray.clear();
/* 156 */         indexArray.add(COSInteger.get(indexSelected));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public COSArray getSelectedOptions()
/*     */   {
/* 168 */     return (COSArray)getDictionary().getDictionaryObject(COSName.I);
/*     */   }
/*     */ 
/*     */   public int getTopIndex()
/*     */   {
/* 180 */     return getDictionary().getInt(COSName.getPDFName("TI"), 0);
/*     */   }
/*     */ 
/*     */   public COSArray getOptions()
/*     */   {
/* 190 */     return (COSArray)getDictionary().getDictionaryObject(COSName.OPT);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.form.PDChoiceField
 * JD-Core Version:    0.6.2
 */