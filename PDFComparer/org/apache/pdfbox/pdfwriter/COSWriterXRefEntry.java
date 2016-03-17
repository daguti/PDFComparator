/*     */ package org.apache.pdfbox.pdfwriter;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.persistence.util.COSObjectKey;
/*     */ 
/*     */ public class COSWriterXRefEntry
/*     */   implements Comparable<COSWriterXRefEntry>
/*     */ {
/*     */   private long offset;
/*     */   private COSBase object;
/*     */   private COSObjectKey key;
/*  35 */   private boolean free = false;
/*     */   private static COSWriterXRefEntry nullEntry;
/*     */ 
/*     */   public int compareTo(COSWriterXRefEntry obj)
/*     */   {
/*  44 */     if ((obj instanceof COSWriterXRefEntry))
/*     */     {
/*  46 */       return (int)(getKey().getNumber() - obj.getKey().getNumber());
/*     */     }
/*     */ 
/*  50 */     return -1;
/*     */   }
/*     */ 
/*     */   public static COSWriterXRefEntry getNullEntry()
/*     */   {
/*  61 */     if (nullEntry == null)
/*     */     {
/*  63 */       nullEntry = new COSWriterXRefEntry(0L, null, new COSObjectKey(0L, 65535L));
/*  64 */       nullEntry.setFree(true);
/*     */     }
/*  66 */     return nullEntry;
/*     */   }
/*     */ 
/*     */   public COSObjectKey getKey()
/*     */   {
/*  76 */     return this.key;
/*     */   }
/*     */ 
/*     */   public long getOffset()
/*     */   {
/*  86 */     return this.offset;
/*     */   }
/*     */ 
/*     */   public boolean isFree()
/*     */   {
/*  96 */     return this.free;
/*     */   }
/*     */ 
/*     */   public void setFree(boolean newFree)
/*     */   {
/* 106 */     this.free = newFree;
/*     */   }
/*     */ 
/*     */   private void setKey(COSObjectKey newKey)
/*     */   {
/* 116 */     this.key = newKey;
/*     */   }
/*     */ 
/*     */   public void setOffset(long newOffset)
/*     */   {
/* 126 */     this.offset = newOffset;
/*     */   }
/*     */ 
/*     */   public COSWriterXRefEntry(long start, COSBase obj, COSObjectKey keyValue)
/*     */   {
/* 139 */     setOffset(start);
/* 140 */     setObject(obj);
/* 141 */     setKey(keyValue);
/*     */   }
/*     */ 
/*     */   public COSBase getObject()
/*     */   {
/* 151 */     return this.object;
/*     */   }
/*     */ 
/*     */   private void setObject(COSBase newObject)
/*     */   {
/* 161 */     this.object = newObject;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdfwriter.COSWriterXRefEntry
 * JD-Core Version:    0.6.2
 */