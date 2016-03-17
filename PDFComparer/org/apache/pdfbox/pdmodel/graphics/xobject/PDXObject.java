/*     */ package org.apache.pdfbox.pdmodel.graphics.xobject;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.pdmodel.common.PDMetadata;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ 
/*     */ public abstract class PDXObject
/*     */   implements COSObjectable
/*     */ {
/*  45 */   private static final Log LOG = LogFactory.getLog(PDXObject.class);
/*     */   private PDStream xobject;
/*     */ 
/*     */   public PDXObject(COSStream xobj)
/*     */   {
/*  56 */     this.xobject = new PDStream(xobj);
/*  57 */     getCOSStream().setItem(COSName.TYPE, COSName.XOBJECT);
/*     */   }
/*     */ 
/*     */   public PDXObject(PDStream xobj)
/*     */   {
/*  67 */     this.xobject = xobj;
/*  68 */     getCOSStream().setItem(COSName.TYPE, COSName.XOBJECT);
/*     */   }
/*     */ 
/*     */   public PDXObject(PDDocument doc)
/*     */   {
/*  78 */     this.xobject = new PDStream(doc);
/*  79 */     getCOSStream().setItem(COSName.TYPE, COSName.XOBJECT);
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  89 */     return this.xobject.getCOSObject();
/*     */   }
/*     */ 
/*     */   public COSStream getCOSStream()
/*     */   {
/*  99 */     return this.xobject.getStream();
/*     */   }
/*     */ 
/*     */   public PDStream getPDStream()
/*     */   {
/* 109 */     return this.xobject;
/*     */   }
/*     */ 
/*     */   public static PDXObject createXObject(COSBase xobject)
/*     */     throws IOException
/*     */   {
/* 122 */     return commonXObjectCreation(xobject, false);
/*     */   }
/*     */ 
/*     */   protected static PDXObject commonXObjectCreation(COSBase xobject, boolean isThumb)
/*     */   {
/* 136 */     PDXObject retval = null;
/* 137 */     if (xobject == null)
/*     */     {
/* 139 */       retval = null;
/*     */     }
/* 141 */     else if ((xobject instanceof COSStream))
/*     */     {
/* 143 */       COSStream xstream = (COSStream)xobject;
/* 144 */       String subtype = xstream.getNameAsString(COSName.SUBTYPE);
/*     */ 
/* 146 */       if (("Image".equals(subtype)) || ((subtype == null) && (isThumb)))
/*     */       {
/* 148 */         PDStream image = new PDStream(xstream);
/*     */ 
/* 152 */         List filters = image.getFilters();
/* 153 */         if ((filters != null) && (filters.contains(COSName.DCT_DECODE)))
/*     */         {
/* 155 */           return new PDJpeg(image);
/*     */         }
/* 157 */         if ((filters != null) && (filters.contains(COSName.CCITTFAX_DECODE)))
/*     */         {
/* 159 */           return new PDCcitt(image);
/*     */         }
/* 161 */         if ((filters != null) && (filters.contains(COSName.JPX_DECODE)))
/*     */         {
/* 168 */           return new PDPixelMap(image);
/*     */         }
/*     */ 
/* 172 */         retval = new PDPixelMap(image);
/*     */       }
/* 175 */       else if ("Form".equals(subtype))
/*     */       {
/* 177 */         retval = new PDXObjectForm(xstream);
/*     */       }
/*     */       else
/*     */       {
/* 181 */         LOG.warn("Skipping unknown XObject subtype '" + subtype + "'");
/*     */       }
/*     */     }
/* 184 */     return retval;
/*     */   }
/*     */ 
/*     */   public PDMetadata getMetadata()
/*     */   {
/* 195 */     PDMetadata retval = null;
/* 196 */     COSStream mdStream = (COSStream)getCOSStream().getDictionaryObject(COSName.METADATA);
/* 197 */     if (mdStream != null)
/*     */     {
/* 199 */       retval = new PDMetadata(mdStream);
/*     */     }
/* 201 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setMetadata(PDMetadata meta)
/*     */   {
/* 211 */     getCOSStream().setItem(COSName.METADATA, meta);
/*     */   }
/*     */ 
/*     */   public int getStructParent()
/*     */   {
/* 222 */     return getCOSStream().getInt(COSName.STRUCT_PARENT, 0);
/*     */   }
/*     */ 
/*     */   public void setStructParent(int structParent)
/*     */   {
/* 232 */     getCOSStream().setInt(COSName.STRUCT_PARENT, structParent);
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.xobject.PDXObject
 * JD-Core Version:    0.6.2
 */