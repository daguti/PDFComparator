/*     */ package org.apache.pdfbox.pdfparser;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.cos.COSInteger;
/*     */ import org.apache.pdfbox.cos.COSObject;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.io.PushBackInputStream;
/*     */ 
/*     */ public class PDFObjectStreamParser extends BaseParser
/*     */ {
/*  43 */   private static final Log LOG = LogFactory.getLog(PDFObjectStreamParser.class);
/*     */ 
/*  46 */   private List<COSObject> streamObjects = null;
/*  47 */   private List<Long> objectNumbers = null;
/*     */   private COSStream stream;
/*     */ 
/*     */   public PDFObjectStreamParser(COSStream strm, COSDocument doc, boolean forceParsing)
/*     */     throws IOException
/*     */   {
/*  64 */     super(strm.getUnfilteredStream(), forceParsing);
/*  65 */     setDocument(doc);
/*  66 */     this.stream = strm;
/*     */   }
/*     */ 
/*     */   public PDFObjectStreamParser(COSStream strm, COSDocument doc)
/*     */     throws IOException
/*     */   {
/*  80 */     this(strm, doc, FORCE_PARSING);
/*     */   }
/*     */ 
/*     */   public void parse()
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/*  94 */       int numberOfObjects = this.stream.getInt("N");
/*  95 */       this.objectNumbers = new ArrayList(numberOfObjects);
/*  96 */       this.streamObjects = new ArrayList(numberOfObjects);
/*  97 */       for (int i = 0; i < numberOfObjects; i++)
/*     */       {
/*  99 */         long objectNumber = readObjectNumber();
/* 100 */         long offset = readLong();
/* 101 */         this.objectNumbers.add(new Long(objectNumber));
/*     */       }
/* 103 */       COSObject object = null;
/* 104 */       COSBase cosObject = null;
/* 105 */       int objectCounter = 0;
/* 106 */       while ((cosObject = parseDirObject()) != null)
/*     */       {
/* 108 */         object = new COSObject(cosObject);
/* 109 */         object.setGenerationNumber(COSInteger.ZERO);
/* 110 */         if (objectCounter >= this.objectNumbers.size())
/*     */         {
/* 112 */           LOG.error("/ObjStm (object stream) has more objects than /N " + numberOfObjects);
/* 113 */           break;
/*     */         }
/* 115 */         COSInteger objNum = COSInteger.get(((Long)this.objectNumbers.get(objectCounter)).intValue());
/*     */ 
/* 117 */         object.setObjectNumber(objNum);
/* 118 */         this.streamObjects.add(object);
/* 119 */         if (LOG.isDebugEnabled())
/*     */         {
/* 121 */           LOG.debug("parsed=" + object);
/*     */         }
/* 123 */         objectCounter++;
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/* 128 */       this.pdfSource.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public List<COSObject> getObjects()
/*     */   {
/* 139 */     return this.streamObjects;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdfparser.PDFObjectStreamParser
 * JD-Core Version:    0.6.2
 */