/*     */ package org.apache.pdfbox.pdmodel.common.filespecification;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ 
/*     */ public class PDComplexFileSpecification extends PDFileSpecification
/*     */ {
/*     */   private COSDictionary fs;
/*     */   private COSDictionary efDictionary;
/*     */ 
/*     */   public PDComplexFileSpecification()
/*     */   {
/*  40 */     this.fs = new COSDictionary();
/*  41 */     this.fs.setItem(COSName.TYPE, COSName.FILESPEC);
/*     */   }
/*     */ 
/*     */   public PDComplexFileSpecification(COSDictionary dict)
/*     */   {
/*  51 */     if (dict == null)
/*     */     {
/*  53 */       this.fs = new COSDictionary();
/*  54 */       this.fs.setItem(COSName.TYPE, COSName.FILESPEC);
/*     */     }
/*     */     else
/*     */     {
/*  58 */       this.fs = dict;
/*     */     }
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  69 */     return this.fs;
/*     */   }
/*     */ 
/*     */   private COSDictionary getEFDictionary()
/*     */   {
/*  74 */     if ((this.efDictionary == null) && (this.fs != null))
/*     */     {
/*  76 */       this.efDictionary = ((COSDictionary)this.fs.getDictionaryObject(COSName.EF));
/*     */     }
/*  78 */     return this.efDictionary;
/*     */   }
/*     */ 
/*     */   private COSBase getObjectFromEFDictionary(COSName key)
/*     */   {
/*  83 */     COSDictionary ef = getEFDictionary();
/*  84 */     if (ef != null)
/*     */     {
/*  86 */       return ef.getDictionaryObject(key);
/*     */     }
/*  88 */     return null;
/*     */   }
/*     */ 
/*     */   public COSDictionary getCOSDictionary()
/*     */   {
/*  98 */     return this.fs;
/*     */   }
/*     */ 
/*     */   public String getFilename()
/*     */   {
/* 112 */     String filename = getFileUnicode();
/* 113 */     if (filename == null)
/*     */     {
/* 115 */       filename = getFileDos();
/*     */     }
/* 117 */     if (filename == null)
/*     */     {
/* 119 */       filename = getFileMac();
/*     */     }
/* 121 */     if (filename == null)
/*     */     {
/* 123 */       filename = getFileUnix();
/*     */     }
/* 125 */     if (filename == null)
/*     */     {
/* 127 */       filename = getFile();
/*     */     }
/* 129 */     return filename;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public String getUnicodeFile()
/*     */   {
/* 141 */     return getFileUnicode();
/*     */   }
/*     */ 
/*     */   public String getFileUnicode()
/*     */   {
/* 151 */     return this.fs.getString(COSName.UF);
/*     */   }
/*     */ 
/*     */   public void setFileUnicode(String file)
/*     */   {
/* 161 */     this.fs.setString(COSName.UF, file);
/*     */   }
/*     */ 
/*     */   public String getFile()
/*     */   {
/* 171 */     return this.fs.getString(COSName.F);
/*     */   }
/*     */ 
/*     */   public void setFile(String file)
/*     */   {
/* 181 */     this.fs.setString(COSName.F, file);
/*     */   }
/*     */ 
/*     */   public String getFileDos()
/*     */   {
/* 191 */     return this.fs.getString(COSName.DOS);
/*     */   }
/*     */ 
/*     */   public void setFileDos(String file)
/*     */   {
/* 201 */     this.fs.setString(COSName.DOS, file);
/*     */   }
/*     */ 
/*     */   public String getFileMac()
/*     */   {
/* 211 */     return this.fs.getString(COSName.MAC);
/*     */   }
/*     */ 
/*     */   public void setFileMac(String file)
/*     */   {
/* 221 */     this.fs.setString(COSName.MAC, file);
/*     */   }
/*     */ 
/*     */   public String getFileUnix()
/*     */   {
/* 231 */     return this.fs.getString(COSName.UNIX);
/*     */   }
/*     */ 
/*     */   public void setFileUnix(String file)
/*     */   {
/* 241 */     this.fs.setString(COSName.UNIX, file);
/*     */   }
/*     */ 
/*     */   public void setVolatile(boolean fileIsVolatile)
/*     */   {
/* 252 */     this.fs.setBoolean(COSName.V, fileIsVolatile);
/*     */   }
/*     */ 
/*     */   public boolean isVolatile()
/*     */   {
/* 262 */     return this.fs.getBoolean(COSName.V, false);
/*     */   }
/*     */ 
/*     */   public PDEmbeddedFile getEmbeddedFile()
/*     */   {
/* 272 */     PDEmbeddedFile file = null;
/* 273 */     COSStream stream = (COSStream)getObjectFromEFDictionary(COSName.F);
/* 274 */     if (stream != null)
/*     */     {
/* 276 */       file = new PDEmbeddedFile(stream);
/*     */     }
/* 278 */     return file;
/*     */   }
/*     */ 
/*     */   public void setEmbeddedFile(PDEmbeddedFile file)
/*     */   {
/* 288 */     COSDictionary ef = getEFDictionary();
/* 289 */     if ((ef == null) && (file != null))
/*     */     {
/* 291 */       ef = new COSDictionary();
/* 292 */       this.fs.setItem(COSName.EF, ef);
/*     */     }
/* 294 */     if (ef != null)
/*     */     {
/* 296 */       ef.setItem(COSName.F, file);
/*     */     }
/*     */   }
/*     */ 
/*     */   public PDEmbeddedFile getEmbeddedFileDos()
/*     */   {
/* 307 */     PDEmbeddedFile file = null;
/* 308 */     COSStream stream = (COSStream)getObjectFromEFDictionary(COSName.DOS);
/* 309 */     if (stream != null)
/*     */     {
/* 311 */       file = new PDEmbeddedFile(stream);
/*     */     }
/* 313 */     return file;
/*     */   }
/*     */ 
/*     */   public void setEmbeddedFileDos(PDEmbeddedFile file)
/*     */   {
/* 323 */     COSDictionary ef = getEFDictionary();
/* 324 */     if ((ef == null) && (file != null))
/*     */     {
/* 326 */       ef = new COSDictionary();
/* 327 */       this.fs.setItem(COSName.EF, ef);
/*     */     }
/* 329 */     if (ef != null)
/*     */     {
/* 331 */       ef.setItem(COSName.DOS, file);
/*     */     }
/*     */   }
/*     */ 
/*     */   public PDEmbeddedFile getEmbeddedFileMac()
/*     */   {
/* 342 */     PDEmbeddedFile file = null;
/* 343 */     COSStream stream = (COSStream)getObjectFromEFDictionary(COSName.MAC);
/* 344 */     if (stream != null)
/*     */     {
/* 346 */       file = new PDEmbeddedFile(stream);
/*     */     }
/* 348 */     return file;
/*     */   }
/*     */ 
/*     */   public void setEmbeddedFileMac(PDEmbeddedFile file)
/*     */   {
/* 358 */     COSDictionary ef = getEFDictionary();
/* 359 */     if ((ef == null) && (file != null))
/*     */     {
/* 361 */       ef = new COSDictionary();
/* 362 */       this.fs.setItem(COSName.EF, ef);
/*     */     }
/* 364 */     if (ef != null)
/*     */     {
/* 366 */       ef.setItem(COSName.MAC, file);
/*     */     }
/*     */   }
/*     */ 
/*     */   public PDEmbeddedFile getEmbeddedFileUnix()
/*     */   {
/* 377 */     PDEmbeddedFile file = null;
/* 378 */     COSStream stream = (COSStream)getObjectFromEFDictionary(COSName.UNIX);
/* 379 */     if (stream != null)
/*     */     {
/* 381 */       file = new PDEmbeddedFile(stream);
/*     */     }
/* 383 */     return file;
/*     */   }
/*     */ 
/*     */   public void setEmbeddedFileUnix(PDEmbeddedFile file)
/*     */   {
/* 393 */     COSDictionary ef = getEFDictionary();
/* 394 */     if ((ef == null) && (file != null))
/*     */     {
/* 396 */       ef = new COSDictionary();
/* 397 */       this.fs.setItem(COSName.EF, ef);
/*     */     }
/* 399 */     if (ef != null)
/*     */     {
/* 401 */       ef.setItem(COSName.UNIX, file);
/*     */     }
/*     */   }
/*     */ 
/*     */   public PDEmbeddedFile getEmbeddedFileUnicode()
/*     */   {
/* 412 */     PDEmbeddedFile file = null;
/* 413 */     COSStream stream = (COSStream)getObjectFromEFDictionary(COSName.UF);
/* 414 */     if (stream != null)
/*     */     {
/* 416 */       file = new PDEmbeddedFile(stream);
/*     */     }
/* 418 */     return file;
/*     */   }
/*     */ 
/*     */   public void setEmbeddedFileUnicode(PDEmbeddedFile file)
/*     */   {
/* 429 */     COSDictionary ef = getEFDictionary();
/* 430 */     if ((ef == null) && (file != null))
/*     */     {
/* 432 */       ef = new COSDictionary();
/* 433 */       this.fs.setItem(COSName.EF, ef);
/*     */     }
/* 435 */     if (ef != null)
/*     */     {
/* 437 */       ef.setItem(COSName.UF, file);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setFileDescription(String description)
/*     */   {
/* 448 */     this.fs.setString(COSName.DESC, description);
/*     */   }
/*     */ 
/*     */   public String getFileDescription()
/*     */   {
/* 458 */     return this.fs.getString(COSName.DESC);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification
 * JD-Core Version:    0.6.2
 */