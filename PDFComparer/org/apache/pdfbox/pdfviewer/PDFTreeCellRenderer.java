/*     */ package org.apache.pdfbox.pdfviewer;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import javax.swing.JTree;
/*     */ import javax.swing.tree.DefaultTreeCellRenderer;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSFloat;
/*     */ import org.apache.pdfbox.cos.COSInteger;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSNull;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.cos.COSString;
/*     */ 
/*     */ public class PDFTreeCellRenderer extends DefaultTreeCellRenderer
/*     */ {
/*     */   public Component getTreeCellRendererComponent(JTree tree, Object nodeValue, boolean isSelected, boolean expanded, boolean leaf, int row, boolean componentHasFocus)
/*     */   {
/*  55 */     nodeValue = convertToTreeObject(nodeValue);
/*  56 */     return super.getTreeCellRendererComponent(tree, nodeValue, isSelected, expanded, leaf, row, componentHasFocus);
/*     */   }
/*     */ 
/*     */   private Object convertToTreeObject(Object nodeValue)
/*     */   {
/*  62 */     if ((nodeValue instanceof MapEntry))
/*     */     {
/*  64 */       MapEntry entry = (MapEntry)nodeValue;
/*  65 */       COSName key = (COSName)entry.getKey();
/*  66 */       COSBase value = (COSBase)entry.getValue();
/*  67 */       nodeValue = key.getName() + ":" + convertToTreeObject(value);
/*     */     }
/*  69 */     else if ((nodeValue instanceof COSFloat))
/*     */     {
/*  71 */       nodeValue = "" + ((COSFloat)nodeValue).floatValue();
/*     */     }
/*  73 */     else if ((nodeValue instanceof COSInteger))
/*     */     {
/*  75 */       nodeValue = "" + ((COSInteger)nodeValue).intValue();
/*     */     }
/*  77 */     else if ((nodeValue instanceof COSString))
/*     */     {
/*  79 */       nodeValue = ((COSString)nodeValue).getString();
/*     */     }
/*  81 */     else if ((nodeValue instanceof COSName))
/*     */     {
/*  83 */       nodeValue = ((COSName)nodeValue).getName();
/*     */     }
/*  85 */     else if ((nodeValue instanceof ArrayEntry))
/*     */     {
/*  87 */       ArrayEntry entry = (ArrayEntry)nodeValue;
/*  88 */       nodeValue = "[" + entry.getIndex() + "]" + convertToTreeObject(entry.getValue());
/*     */     }
/*  90 */     else if ((nodeValue instanceof COSNull))
/*     */     {
/*  92 */       nodeValue = "null";
/*     */     }
/*  94 */     else if ((nodeValue instanceof COSDictionary))
/*     */     {
/*  96 */       COSDictionary dict = (COSDictionary)nodeValue;
/*  97 */       if ((nodeValue instanceof COSStream))
/*     */       {
/*  99 */         nodeValue = "Stream";
/*     */       }
/*     */       else
/*     */       {
/* 103 */         nodeValue = "Dictionary";
/*     */       }
/*     */ 
/* 106 */       COSName type = (COSName)dict.getDictionaryObject(COSName.TYPE);
/* 107 */       if (type != null)
/*     */       {
/* 109 */         nodeValue = nodeValue + "(" + type.getName();
/* 110 */         COSName subType = (COSName)dict.getDictionaryObject(COSName.SUBTYPE);
/* 111 */         if (subType != null)
/*     */         {
/* 113 */           nodeValue = nodeValue + ":" + subType.getName();
/*     */         }
/*     */ 
/* 116 */         nodeValue = nodeValue + ")";
/*     */       }
/*     */     }
/* 119 */     else if ((nodeValue instanceof COSArray))
/*     */     {
/* 121 */       nodeValue = "Array";
/*     */     }
/* 123 */     else if ((nodeValue instanceof COSString))
/*     */     {
/* 125 */       nodeValue = ((COSString)nodeValue).getString();
/*     */     }
/* 127 */     return nodeValue;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdfviewer.PDFTreeCellRenderer
 * JD-Core Version:    0.6.2
 */