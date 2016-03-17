/*     */ package org.apache.pdfbox.pdfparser;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.SortedSet;
/*     */ import java.util.TreeSet;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.persistence.util.COSObjectKey;
/*     */ 
/*     */ public class XrefTrailerResolver
/*     */ {
/*  91 */   private final Map<Long, XrefTrailerObj> bytePosToXrefMap = new HashMap();
/*  92 */   private XrefTrailerObj curXrefTrailerObj = null;
/*  93 */   private XrefTrailerObj resolvedXrefTrailer = null;
/*     */ 
/*  96 */   private static final Log LOG = LogFactory.getLog(XrefTrailerResolver.class);
/*     */ 
/*     */   public final COSDictionary getFirstTrailer()
/*     */   {
/* 100 */     if (this.bytePosToXrefMap.isEmpty()) {
/* 101 */       return null;
/*     */     }
/* 103 */     Set offsets = this.bytePosToXrefMap.keySet();
/* 104 */     SortedSet sortedOffset = new TreeSet(offsets);
/* 105 */     return ((XrefTrailerObj)this.bytePosToXrefMap.get(sortedOffset.first())).trailer;
/*     */   }
/*     */ 
/*     */   public final COSDictionary getLastTrailer()
/*     */   {
/* 110 */     if (this.bytePosToXrefMap.isEmpty()) {
/* 111 */       return null;
/*     */     }
/* 113 */     Set offsets = this.bytePosToXrefMap.keySet();
/* 114 */     SortedSet sortedOffset = new TreeSet(offsets);
/* 115 */     return ((XrefTrailerObj)this.bytePosToXrefMap.get(sortedOffset.last())).trailer;
/*     */   }
/*     */ 
/*     */   public void nextXrefObj(long startBytePos)
/*     */   {
/* 125 */     this.bytePosToXrefMap.put(Long.valueOf(startBytePos), this.curXrefTrailerObj = new XrefTrailerObj(null));
/*     */   }
/*     */ 
/*     */   public void setXRef(COSObjectKey objKey, long offset)
/*     */   {
/* 136 */     if (this.curXrefTrailerObj == null)
/*     */     {
/* 139 */       LOG.warn("Cannot add XRef entry for '" + objKey.getNumber() + "' because XRef start was not signalled.");
/* 140 */       return;
/*     */     }
/* 142 */     this.curXrefTrailerObj.xrefTable.put(objKey, Long.valueOf(offset));
/*     */   }
/*     */ 
/*     */   public void setTrailer(COSDictionary trailer)
/*     */   {
/* 152 */     if (this.curXrefTrailerObj == null)
/*     */     {
/* 155 */       LOG.warn("Cannot add trailer because XRef start was not signalled.");
/* 156 */       return;
/*     */     }
/* 158 */     this.curXrefTrailerObj.trailer = trailer;
/*     */   }
/*     */ 
/*     */   public COSDictionary getCurrentTrailer()
/*     */   {
/* 169 */     return this.curXrefTrailerObj.trailer;
/*     */   }
/*     */ 
/*     */   public void setStartxref(long startxrefBytePosValue)
/*     */   {
/* 188 */     if (this.resolvedXrefTrailer != null)
/*     */     {
/* 190 */       LOG.warn("Method must be called only ones with last startxref value.");
/* 191 */       return;
/*     */     }
/*     */ 
/* 194 */     this.resolvedXrefTrailer = new XrefTrailerObj(null);
/* 195 */     this.resolvedXrefTrailer.trailer = new COSDictionary();
/*     */ 
/* 197 */     XrefTrailerObj curObj = (XrefTrailerObj)this.bytePosToXrefMap.get(Long.valueOf(startxrefBytePosValue));
/* 198 */     List xrefSeqBytePos = new ArrayList();
/*     */ 
/* 200 */     if (curObj == null)
/*     */     {
/* 203 */       LOG.warn("Did not found XRef object at specified startxref position " + startxrefBytePosValue);
/*     */ 
/* 206 */       xrefSeqBytePos.addAll(this.bytePosToXrefMap.keySet());
/* 207 */       Collections.sort(xrefSeqBytePos);
/*     */     }
/*     */     else
/*     */     {
/* 213 */       xrefSeqBytePos.add(Long.valueOf(startxrefBytePosValue));
/* 214 */       while (curObj.trailer != null)
/*     */       {
/* 216 */         long prevBytePos = curObj.trailer.getLong(COSName.PREV, -1L);
/* 217 */         if (prevBytePos == -1L)
/*     */         {
/*     */           break;
/*     */         }
/*     */ 
/* 222 */         curObj = (XrefTrailerObj)this.bytePosToXrefMap.get(Long.valueOf(prevBytePos));
/* 223 */         if (curObj == null)
/*     */         {
/* 225 */           LOG.warn("Did not found XRef object pointed to by 'Prev' key at position " + prevBytePos);
/*     */         }
/*     */         else {
/* 228 */           xrefSeqBytePos.add(Long.valueOf(prevBytePos));
/*     */ 
/* 231 */           if (xrefSeqBytePos.size() >= this.bytePosToXrefMap.size())
/*     */           {
/*     */             break;
/*     */           }
/*     */         }
/*     */       }
/* 237 */       Collections.reverse(xrefSeqBytePos);
/*     */     }
/*     */ 
/* 241 */     for (Long bPos : xrefSeqBytePos)
/*     */     {
/* 243 */       curObj = (XrefTrailerObj)this.bytePosToXrefMap.get(bPos);
/* 244 */       if (curObj.trailer != null)
/*     */       {
/* 246 */         this.resolvedXrefTrailer.trailer.addAll(curObj.trailer);
/*     */       }
/* 248 */       this.resolvedXrefTrailer.xrefTable.putAll(curObj.xrefTable);
/*     */     }
/*     */   }
/*     */ 
/*     */   public COSDictionary getTrailer()
/*     */   {
/* 261 */     return this.resolvedXrefTrailer == null ? null : this.resolvedXrefTrailer.trailer;
/*     */   }
/*     */ 
/*     */   public Map<COSObjectKey, Long> getXrefTable()
/*     */   {
/* 272 */     return this.resolvedXrefTrailer == null ? null : this.resolvedXrefTrailer.xrefTable;
/*     */   }
/*     */ 
/*     */   public Set<Long> getContainedObjectNumbers(int objstmObjNr)
/*     */   {
/* 290 */     if (this.resolvedXrefTrailer == null)
/*     */     {
/* 292 */       return null;
/*     */     }
/* 294 */     Set refObjNrs = new HashSet();
/* 295 */     int cmpVal = -objstmObjNr;
/*     */ 
/* 297 */     for (Map.Entry xrefEntry : this.resolvedXrefTrailer.xrefTable.entrySet())
/*     */     {
/* 299 */       if (((Long)xrefEntry.getValue()).longValue() == cmpVal)
/*     */       {
/* 301 */         refObjNrs.add(Long.valueOf(((COSObjectKey)xrefEntry.getKey()).getNumber()));
/*     */       }
/*     */     }
/* 304 */     return refObjNrs;
/*     */   }
/*     */ 
/*     */   public void clearResources()
/*     */   {
/* 312 */     if (this.curXrefTrailerObj != null)
/*     */     {
/* 314 */       this.curXrefTrailerObj.clearResources();
/* 315 */       this.curXrefTrailerObj = null;
/*     */     }
/* 317 */     if (this.resolvedXrefTrailer != null)
/*     */     {
/* 319 */       this.resolvedXrefTrailer.clearResources();
/* 320 */       this.resolvedXrefTrailer = null;
/*     */     }
/* 322 */     if (this.bytePosToXrefMap != null)
/*     */     {
/* 324 */       this.bytePosToXrefMap.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */   private class XrefTrailerObj
/*     */   {
/*  64 */     protected COSDictionary trailer = null;
/*  65 */     private final Map<COSObjectKey, Long> xrefTable = new HashMap();
/*     */ 
/*     */     private XrefTrailerObj()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void clearResources()
/*     */     {
/*  79 */       if (this.trailer != null)
/*     */       {
/*  81 */         this.trailer.clear();
/*  82 */         this.trailer = null;
/*     */       }
/*  84 */       if (this.xrefTable != null)
/*     */       {
/*  86 */         this.xrefTable.clear();
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdfparser.XrefTrailerResolver
 * JD-Core Version:    0.6.2
 */