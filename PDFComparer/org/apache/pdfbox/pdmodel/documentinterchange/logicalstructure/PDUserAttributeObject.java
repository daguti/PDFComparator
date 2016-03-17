/*     */ package org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ 
/*     */ public class PDUserAttributeObject extends PDAttributeObject
/*     */ {
/*     */   public static final String OWNER_USER_PROPERTIES = "UserProperties";
/*     */ 
/*     */   public PDUserAttributeObject()
/*     */   {
/*  46 */     setOwner("UserProperties");
/*     */   }
/*     */ 
/*     */   public PDUserAttributeObject(COSDictionary dictionary)
/*     */   {
/*  55 */     super(dictionary);
/*     */   }
/*     */ 
/*     */   public List<PDUserProperty> getOwnerUserProperties()
/*     */   {
/*  66 */     COSArray p = (COSArray)getCOSDictionary().getDictionaryObject(COSName.P);
/*     */ 
/*  68 */     List properties = new ArrayList(p.size());
/*  69 */     for (int i = 0; i < p.size(); i++)
/*     */     {
/*  71 */       properties.add(new PDUserProperty((COSDictionary)p.getObject(i), this));
/*     */     }
/*     */ 
/*  74 */     return properties;
/*     */   }
/*     */ 
/*     */   public void setUserProperties(List<PDUserProperty> userProperties)
/*     */   {
/*  84 */     COSArray p = new COSArray();
/*  85 */     for (PDUserProperty userProperty : userProperties)
/*     */     {
/*  87 */       p.add(userProperty);
/*     */     }
/*  89 */     getCOSDictionary().setItem(COSName.P, p);
/*     */   }
/*     */ 
/*     */   public void addUserProperty(PDUserProperty userProperty)
/*     */   {
/*  99 */     COSArray p = (COSArray)getCOSDictionary().getDictionaryObject(COSName.P);
/*     */ 
/* 101 */     p.add(userProperty);
/* 102 */     notifyChanged();
/*     */   }
/*     */ 
/*     */   public void removeUserProperty(PDUserProperty userProperty)
/*     */   {
/* 112 */     if (userProperty == null)
/*     */     {
/* 114 */       return;
/*     */     }
/* 116 */     COSArray p = (COSArray)getCOSDictionary().getDictionaryObject(COSName.P);
/*     */ 
/* 118 */     p.remove(userProperty.getCOSObject());
/* 119 */     notifyChanged();
/*     */   }
/*     */ 
/*     */   public void userPropertyChanged(PDUserProperty userProperty)
/*     */   {
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 130 */     return super.toString() + ", userProperties=" + getOwnerUserProperties();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDUserAttributeObject
 * JD-Core Version:    0.6.2
 */