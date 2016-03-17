/*     */ package org.apache.pdfbox.pdmodel.font;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.awt.FontFormatException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.io.IOUtils;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ 
/*     */ public class PDCIDFontType2Font extends PDCIDFont
/*     */ {
/*  45 */   private static final Log LOG = LogFactory.getLog(PDCIDFontType2Font.class);
/*     */ 
/*  47 */   private Boolean hasCIDToGIDMap = null;
/*  48 */   private int[] cid2gid = null;
/*     */ 
/*     */   public PDCIDFontType2Font()
/*     */   {
/*  56 */     this.font.setItem(COSName.SUBTYPE, COSName.CID_FONT_TYPE2);
/*     */   }
/*     */ 
/*     */   public PDCIDFontType2Font(COSDictionary fontDictionary)
/*     */   {
/*  66 */     super(fontDictionary);
/*     */   }
/*     */ 
/*     */   public Font getawtFont()
/*     */     throws IOException
/*     */   {
/*  74 */     Font awtFont = null;
/*  75 */     PDFontDescriptorDictionary fd = (PDFontDescriptorDictionary)getFontDescriptor();
/*  76 */     PDStream ff2Stream = fd.getFontFile2();
/*  77 */     if (ff2Stream != null)
/*     */     {
/*     */       try
/*     */       {
/*  82 */         awtFont = Font.createFont(0, ff2Stream.createInputStream());
/*     */       }
/*     */       catch (FontFormatException f)
/*     */       {
/*  86 */         LOG.info("Can't read the embedded font " + fd.getFontName());
/*     */       }
/*  88 */       if (awtFont == null)
/*     */       {
/*  90 */         if (fd.getFontName() != null)
/*     */         {
/*  92 */           awtFont = FontManager.getAwtFont(fd.getFontName());
/*     */         }
/*  94 */         if (awtFont != null)
/*     */         {
/*  96 */           LOG.info("Using font " + awtFont.getName() + " instead");
/*     */         }
/*  98 */         setIsFontSubstituted(true);
/*     */       }
/*     */     }
/* 101 */     return awtFont;
/*     */   }
/*     */ 
/*     */   private void readCIDToGIDMapping()
/*     */   {
/* 109 */     COSBase map = this.font.getDictionaryObject(COSName.CID_TO_GID_MAP);
/* 110 */     if ((map instanceof COSStream))
/*     */     {
/* 112 */       COSStream stream = (COSStream)map;
/*     */       try
/*     */       {
/* 115 */         InputStream is = stream.getUnfilteredStream();
/* 116 */         byte[] mapAsBytes = IOUtils.toByteArray(is);
/* 117 */         IOUtils.closeQuietly(is);
/* 118 */         int numberOfInts = mapAsBytes.length / 2;
/* 119 */         this.cid2gid = new int[numberOfInts];
/* 120 */         int offset = 0;
/* 121 */         for (int index = 0; index < numberOfInts; index++)
/*     */         {
/* 123 */           this.cid2gid[index] = getCodeFromArray(mapAsBytes, offset, 2);
/* 124 */           offset += 2;
/*     */         }
/*     */       }
/*     */       catch (IOException exception)
/*     */       {
/* 129 */         LOG.error("Can't read the CIDToGIDMap", exception);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean hasCIDToGIDMap()
/*     */   {
/* 141 */     if (this.hasCIDToGIDMap == null)
/*     */     {
/* 143 */       COSBase map = this.font.getDictionaryObject(COSName.CID_TO_GID_MAP);
/* 144 */       if ((map != null) && ((map instanceof COSStream)))
/*     */       {
/* 146 */         this.hasCIDToGIDMap = Boolean.TRUE;
/*     */       }
/*     */       else
/*     */       {
/* 150 */         this.hasCIDToGIDMap = Boolean.FALSE;
/*     */       }
/*     */     }
/* 153 */     return this.hasCIDToGIDMap.booleanValue();
/*     */   }
/*     */ 
/*     */   public int mapCIDToGID(int cid)
/*     */   {
/* 164 */     if (hasCIDToGIDMap())
/*     */     {
/* 166 */       if (this.cid2gid == null)
/*     */       {
/* 168 */         readCIDToGIDMapping();
/*     */       }
/* 170 */       if ((this.cid2gid != null) && (cid < this.cid2gid.length))
/*     */       {
/* 172 */         return this.cid2gid[cid];
/*     */       }
/* 174 */       return -1;
/*     */     }
/*     */ 
/* 179 */     return cid;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.font.PDCIDFontType2Font
 * JD-Core Version:    0.6.2
 */