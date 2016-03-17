/*     */ package org.apache.pdfbox.util;
/*     */ 
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ 
/*     */ public class QuickSort
/*     */ {
/*  34 */   private static final Comparator<? extends Comparable> objComp = new Comparator()
/*     */   {
/*     */     public int compare(Comparable object1, Comparable object2)
/*     */     {
/*  38 */       return object1.compareTo(object2);
/*     */     }
/*  34 */   };
/*     */ 
/*     */   public static <T> void sort(List<T> list, Comparator<T> cmp)
/*     */   {
/*  50 */     int size = list.size();
/*  51 */     if (size < 2)
/*     */     {
/*  53 */       return;
/*     */     }
/*  55 */     quicksort(list, cmp, 0, size - 1);
/*     */   }
/*     */ 
/*     */   public static <T extends Comparable> void sort(List<T> list)
/*     */   {
/*  65 */     sort(list, objComp);
/*     */   }
/*     */ 
/*     */   private static <T> void quicksort(List<T> list, Comparator<T> cmp, int left, int right)
/*     */   {
/*  70 */     if (left < right)
/*     */     {
/*  72 */       int splitter = split(list, cmp, left, right);
/*  73 */       quicksort(list, cmp, left, splitter - 1);
/*  74 */       quicksort(list, cmp, splitter + 1, right);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static <T> void swap(List<T> list, int i, int j)
/*     */   {
/*  80 */     Object tmp = list.get(i);
/*  81 */     list.set(i, list.get(j));
/*  82 */     list.set(j, tmp);
/*     */   }
/*     */ 
/*     */   private static <T> int split(List<T> list, Comparator<T> cmp, int left, int right)
/*     */   {
/*  87 */     int i = left;
/*  88 */     int j = right - 1;
/*  89 */     Object pivot = list.get(right);
/*     */     do
/*     */     {
/*  92 */       while ((cmp.compare(list.get(i), pivot) <= 0) && (i < right))
/*     */       {
/*  94 */         i++;
/*     */       }
/*  96 */       while ((cmp.compare(pivot, list.get(j)) <= 0) && (j > left))
/*     */       {
/*  98 */         j--;
/*     */       }
/* 100 */       if (i < j)
/*     */       {
/* 102 */         swap(list, i, j);
/*     */       }
/*     */     }
/* 105 */     while (i < j);
/*     */ 
/* 107 */     if (cmp.compare(pivot, list.get(i)) < 0)
/*     */     {
/* 109 */       swap(list, i, right);
/*     */     }
/* 111 */     return i;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.QuickSort
 * JD-Core Version:    0.6.2
 */