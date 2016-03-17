/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.Document;
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import com.itextpdf.text.log.Counter;
/*     */ import com.itextpdf.text.log.CounterFactory;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.security.MessageDigest;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class PdfSmartCopy extends PdfCopy
/*     */ {
/*  71 */   private HashMap<ByteStore, PdfIndirectReference> streamMap = null;
/*  72 */   private final HashMap<RefKey, Integer> serialized = new HashMap();
/*     */ 
/*  74 */   protected Counter COUNTER = CounterFactory.getCounter(PdfSmartCopy.class);
/*     */ 
/*  76 */   protected Counter getCounter() { return this.COUNTER; }
/*     */ 
/*     */   public PdfSmartCopy(Document document, OutputStream os)
/*     */     throws DocumentException
/*     */   {
/*  81 */     super(document, os);
/*  82 */     this.streamMap = new HashMap();
/*     */   }
/*     */ 
/*     */   protected PdfIndirectReference copyIndirect(PRIndirectReference in)
/*     */     throws IOException, BadPdfFormatException
/*     */   {
/*  98 */     PdfObject srcObj = PdfReader.getPdfObjectRelease(in);
/*  99 */     ByteStore streamKey = null;
/* 100 */     boolean validStream = false;
/* 101 */     if (srcObj.isStream()) {
/* 102 */       streamKey = new ByteStore((PRStream)srcObj, this.serialized);
/* 103 */       validStream = true;
/* 104 */       PdfIndirectReference streamRef = (PdfIndirectReference)this.streamMap.get(streamKey);
/* 105 */       if (streamRef != null) {
/* 106 */         return streamRef;
/*     */       }
/*     */     }
/* 109 */     else if (srcObj.isDictionary()) {
/* 110 */       streamKey = new ByteStore((PdfDictionary)srcObj, this.serialized);
/* 111 */       validStream = true;
/* 112 */       PdfIndirectReference streamRef = (PdfIndirectReference)this.streamMap.get(streamKey);
/* 113 */       if (streamRef != null) {
/* 114 */         return streamRef;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 119 */     RefKey key = new RefKey(in);
/* 120 */     PdfCopy.IndirectReferences iRef = (PdfCopy.IndirectReferences)this.indirects.get(key);
/*     */     PdfIndirectReference theRef;
/* 121 */     if (iRef != null) {
/* 122 */       PdfIndirectReference theRef = iRef.getRef();
/* 123 */       if (iRef.getCopied())
/* 124 */         return theRef;
/*     */     }
/*     */     else {
/* 127 */       theRef = this.body.getPdfIndirectReference();
/* 128 */       iRef = new PdfCopy.IndirectReferences(theRef);
/* 129 */       this.indirects.put(key, iRef);
/*     */     }
/* 131 */     if (srcObj.isDictionary()) {
/* 132 */       PdfObject type = PdfReader.getPdfObjectRelease(((PdfDictionary)srcObj).get(PdfName.TYPE));
/* 133 */       if ((type != null) && (PdfName.PAGE.equals(type))) {
/* 134 */         return theRef;
/*     */       }
/*     */     }
/* 137 */     iRef.setCopied();
/*     */ 
/* 139 */     if (validStream) {
/* 140 */       this.streamMap.put(streamKey, theRef);
/*     */     }
/*     */ 
/* 143 */     PdfObject obj = copyObject(srcObj);
/* 144 */     addToBody(obj, theRef);
/* 145 */     return theRef;
/*     */   }
/*     */ 
/*     */   public void freeReader(PdfReader reader) throws IOException
/*     */   {
/* 150 */     this.serialized.clear();
/* 151 */     super.freeReader(reader);
/*     */   }
/*     */ 
/*     */   public void addPage(PdfImportedPage iPage) throws IOException, BadPdfFormatException
/*     */   {
/* 156 */     if (this.currentPdfReaderInstance.getReader() != this.reader)
/* 157 */       this.serialized.clear();
/* 158 */     super.addPage(iPage);
/*     */   }
/*     */   static class ByteStore {
/*     */     private final byte[] b;
/*     */     private final int hash;
/*     */     private MessageDigest md5;
/*     */ 
/* 167 */     private void serObject(PdfObject obj, int level, ByteBuffer bb, HashMap<RefKey, Integer> serialized) throws IOException { if (level <= 0)
/* 168 */         return;
/* 169 */       if (obj == null) {
/* 170 */         bb.append("$Lnull");
/* 171 */         return;
/*     */       }
/* 173 */       PdfIndirectReference ref = null;
/* 174 */       ByteBuffer savedBb = null;
/*     */ 
/* 176 */       if (obj.isIndirect()) {
/* 177 */         ref = (PdfIndirectReference)obj;
/* 178 */         RefKey key = new RefKey(ref);
/* 179 */         if (serialized.containsKey(key)) {
/* 180 */           bb.append(((Integer)serialized.get(key)).intValue());
/* 181 */           return;
/*     */         }
/*     */ 
/* 184 */         savedBb = bb;
/* 185 */         bb = new ByteBuffer();
/*     */       }
/*     */ 
/* 188 */       obj = PdfReader.getPdfObject(obj);
/* 189 */       if (obj.isStream()) {
/* 190 */         bb.append("$B");
/* 191 */         serDic((PdfDictionary)obj, level - 1, bb, serialized);
/* 192 */         if (level > 0) {
/* 193 */           this.md5.reset();
/* 194 */           bb.append(this.md5.digest(PdfReader.getStreamBytesRaw((PRStream)obj)));
/*     */         }
/*     */       }
/* 197 */       else if (obj.isDictionary()) {
/* 198 */         serDic((PdfDictionary)obj, level - 1, bb, serialized);
/*     */       }
/* 200 */       else if (obj.isArray()) {
/* 201 */         serArray((PdfArray)obj, level - 1, bb, serialized);
/*     */       }
/* 203 */       else if (obj.isString()) {
/* 204 */         bb.append("$S").append(obj.toString());
/*     */       }
/* 206 */       else if (obj.isName()) {
/* 207 */         bb.append("$N").append(obj.toString());
/*     */       }
/*     */       else {
/* 210 */         bb.append("$L").append(obj.toString());
/*     */       }
/* 212 */       if (savedBb != null) {
/* 213 */         RefKey key = new RefKey(ref);
/* 214 */         if (!serialized.containsKey(key))
/* 215 */           serialized.put(key, Integer.valueOf(calculateHash(bb.getBuffer())));
/* 216 */         savedBb.append(bb);
/*     */       } }
/*     */ 
/*     */     private void serDic(PdfDictionary dic, int level, ByteBuffer bb, HashMap<RefKey, Integer> serialized) throws IOException
/*     */     {
/* 221 */       bb.append("$D");
/* 222 */       if (level <= 0)
/* 223 */         return;
/* 224 */       Object[] keys = dic.getKeys().toArray();
/* 225 */       Arrays.sort(keys);
/* 226 */       for (int k = 0; k < keys.length; k++) {
/* 227 */         serObject((PdfObject)keys[k], level, bb, serialized);
/* 228 */         serObject(dic.get((PdfName)keys[k]), level, bb, serialized);
/*     */       }
/*     */     }
/*     */ 
/*     */     private void serArray(PdfArray array, int level, ByteBuffer bb, HashMap<RefKey, Integer> serialized) throws IOException {
/* 233 */       bb.append("$A");
/* 234 */       if (level <= 0)
/* 235 */         return;
/* 236 */       for (int k = 0; k < array.size(); k++)
/* 237 */         serObject(array.getPdfObject(k), level, bb, serialized);
/*     */     }
/*     */ 
/*     */     ByteStore(PRStream str, HashMap<RefKey, Integer> serialized) throws IOException
/*     */     {
/*     */       try {
/* 243 */         this.md5 = MessageDigest.getInstance("MD5");
/*     */       }
/*     */       catch (Exception e) {
/* 246 */         throw new ExceptionConverter(e);
/*     */       }
/* 248 */       ByteBuffer bb = new ByteBuffer();
/* 249 */       int level = 100;
/* 250 */       serObject(str, level, bb, serialized);
/* 251 */       this.b = bb.toByteArray();
/* 252 */       this.hash = calculateHash(this.b);
/* 253 */       this.md5 = null;
/*     */     }
/*     */ 
/*     */     ByteStore(PdfDictionary dict, HashMap<RefKey, Integer> serialized) throws IOException {
/*     */       try {
/* 258 */         this.md5 = MessageDigest.getInstance("MD5");
/*     */       }
/*     */       catch (Exception e) {
/* 261 */         throw new ExceptionConverter(e);
/*     */       }
/* 263 */       ByteBuffer bb = new ByteBuffer();
/* 264 */       int level = 100;
/* 265 */       serObject(dict, level, bb, serialized);
/* 266 */       this.b = bb.toByteArray();
/* 267 */       this.hash = calculateHash(this.b);
/* 268 */       this.md5 = null;
/*     */     }
/*     */ 
/*     */     private static int calculateHash(byte[] b) {
/* 272 */       int hash = 0;
/* 273 */       int len = b.length;
/* 274 */       for (int k = 0; k < len; k++)
/* 275 */         hash = hash * 31 + (b[k] & 0xFF);
/* 276 */       return hash;
/*     */     }
/*     */ 
/*     */     public boolean equals(Object obj)
/*     */     {
/* 281 */       if (!(obj instanceof ByteStore))
/* 282 */         return false;
/* 283 */       if (hashCode() != obj.hashCode())
/* 284 */         return false;
/* 285 */       return Arrays.equals(this.b, ((ByteStore)obj).b);
/*     */     }
/*     */ 
/*     */     public int hashCode()
/*     */     {
/* 290 */       return this.hash;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfSmartCopy
 * JD-Core Version:    0.6.2
 */