/*     */ package org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.common.PDDictionaryWrapper;
/*     */ import org.apache.pdfbox.pdmodel.documentinterchange.taggedpdf.PDExportFormatAttributeObject;
/*     */ import org.apache.pdfbox.pdmodel.documentinterchange.taggedpdf.PDLayoutAttributeObject;
/*     */ import org.apache.pdfbox.pdmodel.documentinterchange.taggedpdf.PDListAttributeObject;
/*     */ import org.apache.pdfbox.pdmodel.documentinterchange.taggedpdf.PDPrintFieldAttributeObject;
/*     */ import org.apache.pdfbox.pdmodel.documentinterchange.taggedpdf.PDTableAttributeObject;
/*     */ 
/*     */ public abstract class PDAttributeObject extends PDDictionaryWrapper
/*     */ {
/*     */   private PDStructureElement structureElement;
/*     */ 
/*     */   public static PDAttributeObject create(COSDictionary dictionary)
/*     */   {
/*  47 */     String owner = dictionary.getNameAsString(COSName.O);
/*  48 */     if ("UserProperties".equals(owner))
/*     */     {
/*  50 */       return new PDUserAttributeObject(dictionary);
/*     */     }
/*  52 */     if ("List".equals(owner))
/*     */     {
/*  54 */       return new PDListAttributeObject(dictionary);
/*     */     }
/*  56 */     if ("PrintField".equals(owner))
/*     */     {
/*  58 */       return new PDPrintFieldAttributeObject(dictionary);
/*     */     }
/*  60 */     if ("Table".equals(owner))
/*     */     {
/*  62 */       return new PDTableAttributeObject(dictionary);
/*     */     }
/*  64 */     if ("Layout".equals(owner))
/*     */     {
/*  66 */       return new PDLayoutAttributeObject(dictionary);
/*     */     }
/*  68 */     if (("XML-1.00".equals(owner)) || ("HTML-3.2".equals(owner)) || ("HTML-4.01".equals(owner)) || ("OEB-1.00".equals(owner)) || ("RTF-1.05".equals(owner)) || ("CSS-1.00".equals(owner)) || ("CSS-2.00".equals(owner)))
/*     */     {
/*  76 */       return new PDExportFormatAttributeObject(dictionary);
/*     */     }
/*  78 */     return new PDDefaultAttributeObject(dictionary);
/*     */   }
/*     */ 
/*     */   private PDStructureElement getStructureElement()
/*     */   {
/*  90 */     return this.structureElement;
/*     */   }
/*     */ 
/*     */   protected void setStructureElement(PDStructureElement structureElement)
/*     */   {
/* 100 */     this.structureElement = structureElement;
/*     */   }
/*     */ 
/*     */   public PDAttributeObject()
/*     */   {
/*     */   }
/*     */ 
/*     */   public PDAttributeObject(COSDictionary dictionary)
/*     */   {
/* 118 */     super(dictionary);
/*     */   }
/*     */ 
/*     */   public String getOwner()
/*     */   {
/* 129 */     return getCOSDictionary().getNameAsString(COSName.O);
/*     */   }
/*     */ 
/*     */   protected void setOwner(String owner)
/*     */   {
/* 139 */     getCOSDictionary().setName(COSName.O, owner);
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 151 */     return (getCOSDictionary().size() == 1) && (getOwner() != null);
/*     */   }
/*     */ 
/*     */   protected void potentiallyNotifyChanged(COSBase oldBase, COSBase newBase)
/*     */   {
/* 163 */     if (isValueChanged(oldBase, newBase))
/*     */     {
/* 165 */       notifyChanged();
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean isValueChanged(COSBase oldValue, COSBase newValue)
/*     */   {
/* 179 */     if (oldValue == null)
/*     */     {
/* 181 */       if (newValue == null)
/*     */       {
/* 183 */         return false;
/*     */       }
/* 185 */       return true;
/*     */     }
/* 187 */     return !oldValue.equals(newValue);
/*     */   }
/*     */ 
/*     */   protected void notifyChanged()
/*     */   {
/* 196 */     if (getStructureElement() != null)
/*     */     {
/* 198 */       getStructureElement().attributeChanged(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 205 */     return "O=" + getOwner();
/*     */   }
/*     */ 
/*     */   protected static String arrayToString(Object[] array)
/*     */   {
/* 216 */     StringBuilder sb = new StringBuilder("[");
/* 217 */     for (int i = 0; i < array.length; i++)
/*     */     {
/* 219 */       if (i > 0)
/*     */       {
/* 221 */         sb.append(", ");
/*     */       }
/* 223 */       sb.append(array[i]);
/*     */     }
/* 225 */     return ']';
/*     */   }
/*     */ 
/*     */   protected static String arrayToString(float[] array)
/*     */   {
/* 236 */     StringBuilder sb = new StringBuilder("[");
/* 237 */     for (int i = 0; i < array.length; i++)
/*     */     {
/* 239 */       if (i > 0)
/*     */       {
/* 241 */         sb.append(", ");
/*     */       }
/* 243 */       sb.append(array[i]);
/*     */     }
/* 245 */     return ']';
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDAttributeObject
 * JD-Core Version:    0.6.2
 */