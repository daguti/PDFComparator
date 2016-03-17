/*     */ package org.apache.pdfbox.preflight.font.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.pdfbox.pdmodel.font.PDFontDescriptor;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*     */ import org.apache.pdfbox.preflight.font.FontValidator;
/*     */ import org.apache.xmpbox.XMPMetadata;
/*     */ import org.apache.xmpbox.schema.DublinCoreSchema;
/*     */ import org.apache.xmpbox.schema.XMPRightsManagementSchema;
/*     */ import org.apache.xmpbox.type.AbstractField;
/*     */ import org.apache.xmpbox.type.ArrayProperty;
/*     */ import org.apache.xmpbox.type.BooleanType;
/*     */ import org.apache.xmpbox.type.ComplexPropertyContainer;
/*     */ import org.apache.xmpbox.type.TextType;
/*     */ 
/*     */ public class FontMetaDataValidation
/*     */ {
/*     */   public List<ValidationResult.ValidationError> validatePDFAIdentifer(XMPMetadata metadata, PDFontDescriptor fontDesc)
/*     */     throws ValidationException
/*     */   {
/*  50 */     List ve = new ArrayList();
/*     */ 
/*  52 */     analyseFontName(metadata, fontDesc, ve);
/*  53 */     analyseRights(metadata, fontDesc, ve);
/*     */ 
/*  55 */     return ve;
/*     */   }
/*     */ 
/*     */   public boolean analyseFontName(XMPMetadata metadata, PDFontDescriptor fontDesc, List<ValidationResult.ValidationError> ve)
/*     */   {
/*  70 */     String fontName = fontDesc.getFontName();
/*  71 */     String noSubSetName = fontName;
/*  72 */     if (FontValidator.isSubSet(fontName))
/*     */     {
/*  74 */       noSubSetName = fontName.split(FontValidator.getSubSetPatternDelimiter())[1];
/*     */     }
/*     */ 
/*  77 */     DublinCoreSchema dc = metadata.getDublinCoreSchema();
/*  78 */     if (dc != null)
/*     */     {
/*  80 */       if (dc.getTitleProperty() != null)
/*     */       {
/*  82 */         String defaultTitle = dc.getTitle("x-default");
/*  83 */         if (defaultTitle != null)
/*     */         {
/*  86 */           if ((!defaultTitle.equals(fontName)) && (noSubSetName != null) && (!defaultTitle.equals(noSubSetName)))
/*     */           {
/*  88 */             StringBuilder sb = new StringBuilder(80);
/*  89 */             sb.append("FontName").append(" present in the FontDescriptor dictionary doesn't match with XMP information dc:title of the Font File Stream.");
/*     */ 
/*  91 */             ve.add(new ValidationResult.ValidationError("7.2", sb.toString()));
/*  92 */             return false;
/*     */           }
/*     */ 
/*  96 */           return true;
/*     */         }
/*     */ 
/* 100 */         Iterator it = dc.getTitleProperty().getContainer().getAllProperties().iterator();
/* 101 */         boolean empty = true;
/* 102 */         while (it.hasNext())
/*     */         {
/* 104 */           empty = false;
/* 105 */           AbstractField tmp = (AbstractField)it.next();
/* 106 */           if ((tmp != null) && ((tmp instanceof TextType)))
/*     */           {
/* 108 */             if ((((TextType)tmp).getStringValue().equals(fontName)) || ((noSubSetName != null) && (((TextType)tmp).getStringValue().equals(noSubSetName))))
/*     */             {
/* 112 */               return true;
/*     */             }
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 118 */         StringBuilder sb = new StringBuilder(80);
/* 119 */         sb.append("FontName");
/* 120 */         if (empty)
/*     */         {
/* 122 */           sb.append(" present in the FontDescriptor dictionary can't be found in XMP information the Font File Stream.");
/* 123 */           ve.add(new ValidationResult.ValidationError("7.5", sb.toString()));
/*     */         }
/*     */         else
/*     */         {
/* 127 */           sb.append(" present in the FontDescriptor dictionary doesn't match with XMP information dc:title of the Font File Stream.");
/* 128 */           ve.add(new ValidationResult.ValidationError("7.2", sb.toString()));
/*     */         }
/* 130 */         return false;
/*     */       }
/*     */     }
/*     */ 
/* 134 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean analyseRights(XMPMetadata metadata, PDFontDescriptor fontDesc, List<ValidationResult.ValidationError> ve)
/*     */   {
/* 156 */     DublinCoreSchema dc = metadata.getDublinCoreSchema();
/* 157 */     if (dc != null)
/*     */     {
/* 159 */       ArrayProperty copyrights = dc.getRightsProperty();
/* 160 */       if ((copyrights == null) || (copyrights.getContainer() == null) || (copyrights.getContainer().getAllProperties().isEmpty()))
/*     */       {
/* 163 */         ve.add(new ValidationResult.ValidationError("7.5", "CopyRights is missing from the XMP information (dc:rights) of the Font File Stream."));
/*     */ 
/* 165 */         return false;
/*     */       }
/*     */     }
/*     */ 
/* 169 */     XMPRightsManagementSchema rights = metadata.getXMPRightsManagementSchema();
/* 170 */     if (rights != null)
/*     */     {
/* 172 */       BooleanType marked = rights.getMarkedProperty();
/* 173 */       if ((marked != null) && (!marked.getValue().booleanValue()))
/*     */       {
/* 175 */         ve.add(new ValidationResult.ValidationError("7.5", "the XMP information (xmpRights:Marked) is invalid for the Font File Stream."));
/*     */ 
/* 177 */         return false;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 185 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.font.util.FontMetaDataValidation
 * JD-Core Version:    0.6.2
 */