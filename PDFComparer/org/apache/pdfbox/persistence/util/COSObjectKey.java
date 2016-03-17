/*     */ package org.apache.pdfbox.persistence.util;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSInteger;
/*     */ import org.apache.pdfbox.cos.COSObject;
/*     */ 
/*     */ public class COSObjectKey
/*     */   implements Comparable<COSObjectKey>
/*     */ {
/*     */   private long number;
/*     */   private long generation;
/*     */ 
/*     */   public COSObjectKey(COSObject object)
/*     */   {
/*  39 */     this(object.getObjectNumber().longValue(), object.getGenerationNumber().longValue());
/*     */   }
/*     */ 
/*     */   public COSObjectKey(long num, long gen)
/*     */   {
/*  50 */     setNumber(num);
/*  51 */     setGeneration(gen);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     return ((obj instanceof COSObjectKey)) && (((COSObjectKey)obj).getNumber() == getNumber()) && (((COSObjectKey)obj).getGeneration() == getGeneration());
/*     */   }
/*     */ 
/*     */   public long getGeneration()
/*     */   {
/*  71 */     return this.generation;
/*     */   }
/*     */ 
/*     */   public long getNumber()
/*     */   {
/*  80 */     return this.number;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  88 */     return (int)(this.number + this.generation);
/*     */   }
/*     */ 
/*     */   public void setGeneration(long newGeneration)
/*     */   {
/*  97 */     this.generation = newGeneration;
/*     */   }
/*     */ 
/*     */   public void setNumber(long newNumber)
/*     */   {
/* 106 */     this.number = newNumber;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 114 */     return "" + getNumber() + " " + getGeneration() + " R";
/*     */   }
/*     */ 
/*     */   public int compareTo(COSObjectKey other)
/*     */   {
/* 120 */     if (getNumber() < other.getNumber())
/*     */     {
/* 122 */       return -1;
/*     */     }
/* 124 */     if (getNumber() > other.getNumber())
/*     */     {
/* 126 */       return 1;
/*     */     }
/*     */ 
/* 130 */     if (getGeneration() < other.getGeneration())
/*     */     {
/* 132 */       return -1;
/*     */     }
/* 134 */     if (getGeneration() > other.getGeneration())
/*     */     {
/* 136 */       return 1;
/*     */     }
/*     */ 
/* 140 */     return 0;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.persistence.util.COSObjectKey
 * JD-Core Version:    0.6.2
 */