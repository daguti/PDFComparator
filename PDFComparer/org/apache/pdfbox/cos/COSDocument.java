/*     */ package org.apache.pdfbox.cos;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.exceptions.COSVisitorException;
/*     */ import org.apache.pdfbox.io.RandomAccess;
/*     */ import org.apache.pdfbox.io.RandomAccessBuffer;
/*     */ import org.apache.pdfbox.io.RandomAccessFile;
/*     */ import org.apache.pdfbox.pdfparser.PDFObjectStreamParser;
/*     */ import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureInterface;
/*     */ import org.apache.pdfbox.persistence.util.COSObjectKey;
/*     */ 
/*     */ public class COSDocument extends COSBase
/*     */   implements Closeable
/*     */ {
/*  52 */   private static final Log LOG = LogFactory.getLog(COSDocument.class);
/*     */ 
/*  54 */   private float version = 1.4F;
/*     */ 
/*  60 */   private final Map<COSObjectKey, COSObject> objectPool = new HashMap();
/*     */ 
/*  66 */   private final Map<COSObjectKey, Long> xrefTable = new HashMap();
/*     */   private COSDictionary trailer;
/*     */   private SignatureInterface signatureInterface;
/*     */   private final RandomAccess scratchFile;
/*     */   private final File tmpFile;
/*  86 */   private String headerString = "%PDF-" + this.version;
/*     */ 
/*  88 */   private boolean warnMissingClose = true;
/*     */ 
/*  91 */   private boolean isDecrypted = false;
/*     */   private long startXref;
/*  95 */   private boolean closed = false;
/*     */   private final boolean forceParsing;
/*     */ 
/*     */   public COSDocument(RandomAccess scratchFileValue, boolean forceParsingValue)
/*     */   {
/* 114 */     this.scratchFile = scratchFileValue;
/* 115 */     this.tmpFile = null;
/* 116 */     this.forceParsing = forceParsingValue;
/*     */   }
/*     */ 
/*     */   public COSDocument(File scratchDir, boolean forceParsingValue)
/*     */     throws IOException
/*     */   {
/* 132 */     this.tmpFile = File.createTempFile("pdfbox-", ".tmp", scratchDir);
/* 133 */     this.scratchFile = new RandomAccessFile(this.tmpFile, "rw");
/* 134 */     this.forceParsing = forceParsingValue;
/*     */   }
/*     */ 
/*     */   public COSDocument()
/*     */   {
/* 142 */     this(new RandomAccessBuffer(), false);
/*     */   }
/*     */ 
/*     */   public COSDocument(File scratchDir)
/*     */     throws IOException
/*     */   {
/* 155 */     this(scratchDir, false);
/*     */   }
/*     */ 
/*     */   public COSDocument(RandomAccess file)
/*     */   {
/* 168 */     this(file, false);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public RandomAccess getScratchFile()
/*     */   {
/* 181 */     if (!this.closed)
/*     */     {
/* 183 */       return this.scratchFile;
/*     */     }
/*     */ 
/* 187 */     LOG.error("Can't access the scratch file as it is already closed!");
/* 188 */     return null;
/*     */   }
/*     */ 
/*     */   public COSStream createCOSStream()
/*     */   {
/* 199 */     return new COSStream(getScratchFile());
/*     */   }
/*     */ 
/*     */   public COSStream createCOSStream(COSDictionary dictionary)
/*     */   {
/* 211 */     return new COSStream(dictionary, getScratchFile());
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public COSObject getObjectByType(String type)
/*     */     throws IOException
/*     */   {
/* 226 */     return getObjectByType(COSName.getPDFName(type));
/*     */   }
/*     */ 
/*     */   public COSObject getObjectByType(COSName type)
/*     */     throws IOException
/*     */   {
/* 239 */     for (COSObject object : this.objectPool.values())
/*     */     {
/* 241 */       COSBase realObject = object.getObject();
/* 242 */       if ((realObject instanceof COSDictionary))
/*     */       {
/*     */         try
/*     */         {
/* 246 */           COSDictionary dic = (COSDictionary)realObject;
/* 247 */           COSBase typeItem = dic.getItem(COSName.TYPE);
/* 248 */           if ((typeItem != null) && ((typeItem instanceof COSName)))
/*     */           {
/* 250 */             COSName objectType = (COSName)typeItem;
/* 251 */             if (objectType.equals(type))
/*     */             {
/* 253 */               return object;
/*     */             }
/*     */           }
/* 256 */           else if (typeItem != null)
/*     */           {
/* 258 */             LOG.debug("Expected a /Name object after /Type, got '" + typeItem + "' instead");
/*     */           }
/*     */         }
/*     */         catch (ClassCastException e)
/*     */         {
/* 263 */           LOG.warn(e, e);
/*     */         }
/*     */       }
/*     */     }
/* 267 */     return null;
/*     */   }
/*     */ 
/*     */   public List<COSObject> getObjectsByType(String type)
/*     */     throws IOException
/*     */   {
/* 280 */     return getObjectsByType(COSName.getPDFName(type));
/*     */   }
/*     */ 
/*     */   public List<COSObject> getObjectsByType(COSName type)
/*     */     throws IOException
/*     */   {
/* 293 */     List retval = new ArrayList();
/* 294 */     for (COSObject object : this.objectPool.values())
/*     */     {
/* 296 */       COSBase realObject = object.getObject();
/* 297 */       if ((realObject instanceof COSDictionary))
/*     */       {
/*     */         try
/*     */         {
/* 301 */           COSDictionary dic = (COSDictionary)realObject;
/* 302 */           COSBase typeItem = dic.getItem(COSName.TYPE);
/* 303 */           if ((typeItem != null) && ((typeItem instanceof COSName)))
/*     */           {
/* 305 */             COSName objectType = (COSName)typeItem;
/* 306 */             if (objectType.equals(type))
/*     */             {
/* 308 */               retval.add(object);
/*     */             }
/*     */           }
/* 311 */           else if (typeItem != null)
/*     */           {
/* 313 */             LOG.debug("Expected a /Name object after /Type, got '" + typeItem + "' instead");
/*     */           }
/*     */         }
/*     */         catch (ClassCastException e)
/*     */         {
/* 318 */           LOG.warn(e, e);
/*     */         }
/*     */       }
/*     */     }
/* 322 */     return retval;
/*     */   }
/*     */ 
/*     */   public void print()
/*     */   {
/* 330 */     for (COSObject object : this.objectPool.values())
/*     */     {
/* 332 */       System.out.println(object);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setVersion(float versionValue)
/*     */   {
/* 344 */     if (versionValue != this.version)
/*     */     {
/* 346 */       this.headerString = this.headerString.replaceFirst(String.valueOf(this.version), String.valueOf(versionValue));
/*     */     }
/* 348 */     this.version = versionValue;
/*     */   }
/*     */ 
/*     */   public float getVersion()
/*     */   {
/* 358 */     return this.version;
/*     */   }
/*     */ 
/*     */   public void setDecrypted()
/*     */   {
/* 368 */     this.isDecrypted = true;
/*     */   }
/*     */ 
/*     */   public boolean isDecrypted()
/*     */   {
/* 379 */     return this.isDecrypted;
/*     */   }
/*     */ 
/*     */   public boolean isEncrypted()
/*     */   {
/* 389 */     boolean encrypted = false;
/* 390 */     if (this.trailer != null)
/*     */     {
/* 392 */       encrypted = this.trailer.getDictionaryObject(COSName.ENCRYPT) != null;
/*     */     }
/* 394 */     return encrypted;
/*     */   }
/*     */ 
/*     */   public COSDictionary getEncryptionDictionary()
/*     */   {
/* 405 */     return (COSDictionary)this.trailer.getDictionaryObject(COSName.ENCRYPT);
/*     */   }
/*     */ 
/*     */   public SignatureInterface getSignatureInterface()
/*     */   {
/* 414 */     return this.signatureInterface;
/*     */   }
/*     */ 
/*     */   public void setEncryptionDictionary(COSDictionary encDictionary)
/*     */   {
/* 425 */     this.trailer.setItem(COSName.ENCRYPT, encDictionary);
/*     */   }
/*     */ 
/*     */   public List<COSDictionary> getSignatureDictionaries()
/*     */     throws IOException
/*     */   {
/* 436 */     List signatureFields = getSignatureFields(false);
/* 437 */     List signatures = new LinkedList();
/* 438 */     for (COSDictionary dict : signatureFields)
/*     */     {
/* 440 */       COSBase dictionaryObject = dict.getDictionaryObject(COSName.V);
/* 441 */       if (dictionaryObject != null)
/*     */       {
/* 443 */         signatures.add((COSDictionary)dictionaryObject);
/*     */       }
/*     */     }
/* 446 */     return signatures;
/*     */   }
/*     */ 
/*     */   public List<COSDictionary> getSignatureFields(boolean onlyEmptyFields)
/*     */     throws IOException
/*     */   {
/* 458 */     COSObject documentCatalog = getCatalog();
/* 459 */     if (documentCatalog != null)
/*     */     {
/* 461 */       COSDictionary acroForm = (COSDictionary)documentCatalog.getDictionaryObject(COSName.ACRO_FORM);
/* 462 */       if (acroForm != null)
/*     */       {
/* 464 */         COSArray fields = (COSArray)acroForm.getDictionaryObject(COSName.FIELDS);
/* 465 */         if (fields != null)
/*     */         {
/* 469 */           HashMap signatures = new HashMap();
/* 470 */           for (Object object : fields)
/*     */           {
/* 472 */             COSObject dict = (COSObject)object;
/* 473 */             if (COSName.SIG.equals(dict.getItem(COSName.FT)))
/*     */             {
/* 475 */               COSBase dictionaryObject = dict.getDictionaryObject(COSName.V);
/* 476 */               if ((dictionaryObject == null) || ((dictionaryObject != null) && (!onlyEmptyFields)))
/*     */               {
/* 478 */                 signatures.put(new COSObjectKey(dict), (COSDictionary)dict.getObject());
/*     */               }
/*     */             }
/*     */           }
/* 482 */           return new LinkedList(signatures.values());
/*     */         }
/*     */       }
/*     */     }
/* 486 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */   public COSArray getDocumentID()
/*     */   {
/* 496 */     return (COSArray)getTrailer().getDictionaryObject(COSName.ID);
/*     */   }
/*     */ 
/*     */   public void setDocumentID(COSArray id)
/*     */   {
/* 506 */     getTrailer().setItem(COSName.ID, id);
/*     */   }
/*     */ 
/*     */   public void setSignatureInterface(SignatureInterface sigInterface)
/*     */   {
/* 515 */     this.signatureInterface = sigInterface;
/*     */   }
/*     */ 
/*     */   public COSObject getCatalog()
/*     */     throws IOException
/*     */   {
/* 529 */     COSObject catalog = getObjectByType(COSName.CATALOG);
/* 530 */     if (catalog == null)
/*     */     {
/* 532 */       throw new IOException("Catalog cannot be found");
/*     */     }
/* 534 */     return catalog;
/*     */   }
/*     */ 
/*     */   public List<COSObject> getObjects()
/*     */   {
/* 544 */     return new ArrayList(this.objectPool.values());
/*     */   }
/*     */ 
/*     */   public COSDictionary getTrailer()
/*     */   {
/* 554 */     return this.trailer;
/*     */   }
/*     */ 
/*     */   public void setTrailer(COSDictionary newTrailer)
/*     */   {
/* 565 */     this.trailer = newTrailer;
/*     */   }
/*     */ 
/*     */   public Object accept(ICOSVisitor visitor)
/*     */     throws COSVisitorException
/*     */   {
/* 578 */     return visitor.visitFromDocument(this);
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 588 */     if (!this.closed)
/*     */     {
/* 590 */       this.scratchFile.close();
/* 591 */       if (this.tmpFile != null)
/*     */       {
/* 593 */         this.tmpFile.delete();
/*     */       }
/* 595 */       if (this.trailer != null)
/*     */       {
/* 597 */         this.trailer.clear();
/* 598 */         this.trailer = null;
/*     */       }
/*     */ 
/* 601 */       for (COSObject object : this.objectPool.values())
/*     */       {
/* 603 */         COSBase cosObject = object.getObject();
/*     */ 
/* 605 */         if ((cosObject instanceof COSStream))
/*     */         {
/* 607 */           ((COSStream)cosObject).close();
/*     */         }
/* 609 */         else if ((cosObject instanceof COSDictionary))
/*     */         {
/* 611 */           ((COSDictionary)cosObject).clear();
/*     */         }
/* 613 */         else if ((cosObject instanceof COSArray))
/*     */         {
/* 615 */           ((COSArray)cosObject).clear();
/*     */         }
/*     */       }
/*     */ 
/* 619 */       this.objectPool.clear();
/* 620 */       this.closed = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void finalize()
/*     */     throws IOException
/*     */   {
/* 633 */     if (!this.closed)
/*     */     {
/* 635 */       if (this.warnMissingClose)
/*     */       {
/* 637 */         LOG.warn("Warning: You did not close a PDF Document");
/*     */       }
/* 639 */       close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setWarnMissingClose(boolean warn)
/*     */   {
/* 652 */     this.warnMissingClose = warn;
/*     */   }
/*     */ 
/*     */   public String getHeaderString()
/*     */   {
/* 660 */     return this.headerString;
/*     */   }
/*     */ 
/*     */   public void setHeaderString(String header)
/*     */   {
/* 667 */     this.headerString = header;
/*     */   }
/*     */ 
/*     */   public void dereferenceObjectStreams()
/*     */     throws IOException
/*     */   {
/* 678 */     for (Iterator i$ = getObjectsByType(COSName.OBJ_STM).iterator(); i$.hasNext(); ) { objStream = (COSObject)i$.next();
/*     */ 
/* 680 */       COSStream stream = (COSStream)objStream.getObject();
/* 681 */       PDFObjectStreamParser parser = new PDFObjectStreamParser(stream, this, this.forceParsing);
/*     */ 
/* 683 */       parser.parse();
/* 684 */       for (COSObject next : parser.getObjects())
/*     */       {
/* 686 */         COSObjectKey key = new COSObjectKey(next);
/* 687 */         if ((this.objectPool.get(key) == null) || (((COSObject)this.objectPool.get(key)).getObject() == null) || ((this.xrefTable.containsKey(key)) && (((Long)this.xrefTable.get(key)).longValue() == -objStream.getObjectNumber().longValue())))
/*     */         {
/* 691 */           COSObject obj = getObjectFromPool(key);
/* 692 */           obj.setObject(next.getObject());
/*     */         }
/*     */       }
/*     */     }
/*     */     COSObject objStream;
/*     */   }
/*     */ 
/*     */   public COSObject getObjectFromPool(COSObjectKey key)
/*     */     throws IOException
/*     */   {
/* 709 */     COSObject obj = null;
/* 710 */     if (key != null)
/*     */     {
/* 712 */       obj = (COSObject)this.objectPool.get(key);
/*     */     }
/* 714 */     if (obj == null)
/*     */     {
/* 717 */       obj = new COSObject(null);
/* 718 */       if (key != null)
/*     */       {
/* 720 */         obj.setObjectNumber(COSInteger.get(key.getNumber()));
/* 721 */         obj.setGenerationNumber(COSInteger.get(key.getGeneration()));
/* 722 */         this.objectPool.put(key, obj);
/*     */       }
/*     */     }
/* 725 */     return obj;
/*     */   }
/*     */ 
/*     */   public COSObject removeObject(COSObjectKey key)
/*     */   {
/* 735 */     return (COSObject)this.objectPool.remove(key);
/*     */   }
/*     */ 
/*     */   public void addXRefTable(Map<COSObjectKey, Long> xrefTableValues)
/*     */   {
/* 745 */     this.xrefTable.putAll(xrefTableValues);
/*     */   }
/*     */ 
/*     */   public Map<COSObjectKey, Long> getXrefTable()
/*     */   {
/* 755 */     return this.xrefTable;
/*     */   }
/*     */ 
/*     */   public void setStartXref(long startXrefValue)
/*     */   {
/* 766 */     this.startXref = startXrefValue;
/*     */   }
/*     */ 
/*     */   public long getStartXref()
/*     */   {
/* 776 */     return this.startXref;
/*     */   }
/*     */ 
/*     */   public boolean isXRefStream()
/*     */   {
/* 786 */     if (this.trailer != null)
/*     */     {
/* 788 */       return COSName.XREF.equals(this.trailer.getItem(COSName.TYPE));
/*     */     }
/* 790 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.cos.COSDocument
 * JD-Core Version:    0.6.2
 */