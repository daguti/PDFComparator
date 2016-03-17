/*     */ package org.apache.pdfbox.pdmodel;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.pdfparser.ConformingPDFParser;
/*     */ import org.apache.pdfbox.persistence.util.COSObjectKey;
/*     */ 
/*     */ public class ConformingPDDocument extends PDDocument
/*     */ {
/*  40 */   private final Map<COSObjectKey, COSBase> objectPool = new HashMap();
/*     */ 
/*  42 */   private ConformingPDFParser parser = null;
/*     */ 
/*     */   public ConformingPDDocument() throws IOException
/*     */   {
/*     */   }
/*     */ 
/*     */   public ConformingPDDocument(COSDocument doc) throws IOException {
/*  49 */     super(doc);
/*     */   }
/*     */ 
/*     */   public static PDDocument load(File input)
/*     */     throws IOException
/*     */   {
/*  59 */     ConformingPDFParser parser = new ConformingPDFParser(input);
/*  60 */     parser.parse();
/*  61 */     return parser.getPDDocument();
/*     */   }
/*     */ 
/*     */   public COSBase getObjectFromPool(COSObjectKey key)
/*     */     throws IOException
/*     */   {
/*  71 */     return (COSBase)this.objectPool.get(key);
/*     */   }
/*     */ 
/*     */   public List<COSObjectKey> getObjectKeysFromPool()
/*     */     throws IOException
/*     */   {
/*  80 */     List keys = new ArrayList();
/*  81 */     for (COSObjectKey key : this.objectPool.keySet())
/*  82 */       keys.add(key);
/*  83 */     return keys;
/*     */   }
/*     */ 
/*     */   public COSBase getObjectFromPool(long number, long generation)
/*     */     throws IOException
/*     */   {
/*  94 */     return (COSBase)this.objectPool.get(new COSObjectKey(number, generation));
/*     */   }
/*     */ 
/*     */   public void putObjectInPool(COSBase object, long number, long generation) {
/*  98 */     this.objectPool.put(new COSObjectKey(number, generation), object);
/*     */   }
/*     */ 
/*     */   public ConformingPDFParser getParser()
/*     */   {
/* 105 */     return this.parser;
/*     */   }
/*     */ 
/*     */   public void setParser(ConformingPDFParser parser)
/*     */   {
/* 112 */     this.parser = parser;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.ConformingPDDocument
 * JD-Core Version:    0.6.2
 */