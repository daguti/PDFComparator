/*     */ package org.apache.pdfbox.pdmodel.font;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ 
/*     */ public class PDFontFactory
/*     */ {
/*  45 */   private static final Log LOG = LogFactory.getLog(PDFontFactory.class);
/*     */ 
/*     */   /** @deprecated */
/*     */   public static PDFont createFont(COSDictionary dic, Map fontCache)
/*     */     throws IOException
/*     */   {
/*  63 */     return createFont(dic);
/*     */   }
/*     */ 
/*     */   public static PDFont createFont(COSDictionary dic)
/*     */     throws IOException
/*     */   {
/*  77 */     PDFont retval = null;
/*     */ 
/*  79 */     COSName type = (COSName)dic.getDictionaryObject(COSName.TYPE);
/*  80 */     if ((type != null) && (!COSName.FONT.equals(type)))
/*     */     {
/*  82 */       throw new IOException("Cannot create font if /Type is not /Font.  Actual=" + type);
/*     */     }
/*     */ 
/*  85 */     COSName subType = (COSName)dic.getDictionaryObject(COSName.SUBTYPE);
/*  86 */     if (subType == null)
/*     */     {
/*  88 */       throw new IOException("Cannot create font as /SubType is not set.");
/*     */     }
/*  90 */     if (subType.equals(COSName.TYPE1))
/*     */     {
/*  92 */       retval = new PDType1Font(dic);
/*     */     }
/*  94 */     else if (subType.equals(COSName.MM_TYPE1))
/*     */     {
/*  96 */       retval = new PDMMType1Font(dic);
/*     */     }
/*  98 */     else if (subType.equals(COSName.TRUE_TYPE))
/*     */     {
/* 100 */       retval = new PDTrueTypeFont(dic);
/*     */     }
/* 102 */     else if (subType.equals(COSName.TYPE3))
/*     */     {
/* 104 */       retval = new PDType3Font(dic);
/*     */     }
/* 106 */     else if (subType.equals(COSName.TYPE0))
/*     */     {
/* 108 */       retval = new PDType0Font(dic);
/*     */     }
/* 110 */     else if (subType.equals(COSName.CID_FONT_TYPE0))
/*     */     {
/* 112 */       retval = new PDCIDFontType0Font(dic);
/*     */     }
/* 114 */     else if (subType.equals(COSName.CID_FONT_TYPE2))
/*     */     {
/* 116 */       retval = new PDCIDFontType2Font(dic);
/*     */     }
/*     */     else
/*     */     {
/* 122 */       LOG.warn("Invalid font subtype '" + subType.getName() + "'");
/* 123 */       return new PDType1Font(dic);
/*     */     }
/* 125 */     return retval;
/*     */   }
/*     */ 
/*     */   public static PDFont createDefaultFont()
/*     */     throws IOException
/*     */   {
/* 136 */     COSDictionary dict = new COSDictionary();
/* 137 */     dict.setItem(COSName.TYPE, COSName.FONT);
/* 138 */     dict.setItem(COSName.SUBTYPE, COSName.TYPE1);
/* 139 */     dict.setString(COSName.BASE_FONT, "Arial");
/* 140 */     return createFont(dict);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.font.PDFontFactory
 * JD-Core Version:    0.6.2
 */