/*     */ package org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.common.PDDictionaryWrapper;
/*     */ 
/*     */ public class PDUserProperty extends PDDictionaryWrapper
/*     */ {
/*     */   private final PDUserAttributeObject userAttributeObject;
/*     */ 
/*     */   public PDUserProperty(PDUserAttributeObject userAttributeObject)
/*     */   {
/*  42 */     this.userAttributeObject = userAttributeObject;
/*     */   }
/*     */ 
/*     */   public PDUserProperty(COSDictionary dictionary, PDUserAttributeObject userAttributeObject)
/*     */   {
/*  54 */     super(dictionary);
/*  55 */     this.userAttributeObject = userAttributeObject;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  66 */     return getCOSDictionary().getNameAsString(COSName.N);
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/*  76 */     potentiallyNotifyChanged(getName(), name);
/*  77 */     getCOSDictionary().setName(COSName.N, name);
/*     */   }
/*     */ 
/*     */   public COSBase getValue()
/*     */   {
/*  87 */     return getCOSDictionary().getDictionaryObject(COSName.V);
/*     */   }
/*     */ 
/*     */   public void setValue(COSBase value)
/*     */   {
/*  97 */     potentiallyNotifyChanged(getValue(), value);
/*  98 */     getCOSDictionary().setItem(COSName.V, value);
/*     */   }
/*     */ 
/*     */   public String getFormattedValue()
/*     */   {
/* 108 */     return getCOSDictionary().getString(COSName.F);
/*     */   }
/*     */ 
/*     */   public void setFormattedValue(String formattedValue)
/*     */   {
/* 118 */     potentiallyNotifyChanged(getFormattedValue(), formattedValue);
/* 119 */     getCOSDictionary().setString(COSName.F, formattedValue);
/*     */   }
/*     */ 
/*     */   public boolean isHidden()
/*     */   {
/* 130 */     return getCOSDictionary().getBoolean(COSName.H, false);
/*     */   }
/*     */ 
/*     */   public void setHidden(boolean hidden)
/*     */   {
/* 141 */     potentiallyNotifyChanged(Boolean.valueOf(isHidden()), Boolean.valueOf(hidden));
/* 142 */     getCOSDictionary().setBoolean(COSName.H, hidden);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 149 */     return "Name=" + getName() + ", Value=" + getValue() + ", FormattedValue=" + getFormattedValue() + ", Hidden=" + isHidden();
/*     */   }
/*     */ 
/*     */   private void potentiallyNotifyChanged(Object oldEntry, Object newEntry)
/*     */   {
/* 164 */     if (isEntryChanged(oldEntry, newEntry))
/*     */     {
/* 166 */       this.userAttributeObject.userPropertyChanged(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean isEntryChanged(Object oldEntry, Object newEntry)
/*     */   {
/* 180 */     if (oldEntry == null)
/*     */     {
/* 182 */       if (newEntry == null)
/*     */       {
/* 184 */         return false;
/*     */       }
/* 186 */       return true;
/*     */     }
/* 188 */     return !oldEntry.equals(newEntry);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDUserProperty
 * JD-Core Version:    0.6.2
 */