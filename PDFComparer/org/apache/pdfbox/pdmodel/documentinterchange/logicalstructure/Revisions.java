/*     */ package org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Revisions<T>
/*     */ {
/*     */   private List<T> objects;
/*     */   private List<Integer> revisionNumbers;
/*     */ 
/*     */   private List<T> getObjects()
/*     */   {
/*  37 */     if (this.objects == null)
/*     */     {
/*  39 */       this.objects = new ArrayList();
/*     */     }
/*  41 */     return this.objects;
/*     */   }
/*     */ 
/*     */   private List<Integer> getRevisionNumbers()
/*     */   {
/*  46 */     if (this.revisionNumbers == null)
/*     */     {
/*  48 */       this.revisionNumbers = new ArrayList();
/*     */     }
/*  50 */     return this.revisionNumbers;
/*     */   }
/*     */ 
/*     */   public T getObject(int index)
/*     */     throws IndexOutOfBoundsException
/*     */   {
/*  71 */     return getObjects().get(index);
/*     */   }
/*     */ 
/*     */   public int getRevisionNumber(int index)
/*     */     throws IndexOutOfBoundsException
/*     */   {
/*  83 */     return ((Integer)getRevisionNumbers().get(index)).intValue();
/*     */   }
/*     */ 
/*     */   public void addObject(T object, int revisionNumber)
/*     */   {
/*  94 */     getObjects().add(object);
/*  95 */     getRevisionNumbers().add(Integer.valueOf(revisionNumber));
/*     */   }
/*     */ 
/*     */   protected void setRevisionNumber(T object, int revisionNumber)
/*     */   {
/* 106 */     int index = getObjects().indexOf(object);
/* 107 */     if (index > -1)
/*     */     {
/* 109 */       getRevisionNumbers().set(index, Integer.valueOf(revisionNumber));
/*     */     }
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/* 120 */     return getObjects().size();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 128 */     StringBuilder sb = new StringBuilder();
/* 129 */     for (int i = 0; i < getObjects().size(); i++)
/*     */     {
/* 131 */       if (i > 0)
/*     */       {
/* 133 */         sb.append("; ");
/*     */       }
/* 135 */       sb.append("object=").append(getObjects().get(i)).append(", revisionNumber=").append(getRevisionNumber(i));
/*     */     }
/*     */ 
/* 138 */     return sb.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.Revisions
 * JD-Core Version:    0.6.2
 */