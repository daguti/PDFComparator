/*     */ package antlr.collections.impl;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.Enumeration;
/*     */ 
/*     */ public class Vector
/*     */   implements Cloneable
/*     */ {
/*     */   protected Object[] data;
/*  17 */   protected int lastElement = -1;
/*     */ 
/*     */   public Vector() {
/*  20 */     this(10);
/*     */   }
/*     */ 
/*     */   public Vector(int paramInt) {
/*  24 */     this.data = new Object[paramInt];
/*     */   }
/*     */ 
/*     */   public synchronized void appendElement(Object paramObject) {
/*  28 */     ensureCapacity(this.lastElement + 2);
/*  29 */     this.data[(++this.lastElement)] = paramObject;
/*     */   }
/*     */ 
/*     */   public int capacity()
/*     */   {
/*  36 */     return this.data.length;
/*     */   }
/*     */ 
/*     */   public Object clone() {
/*  40 */     Vector localVector = null;
/*     */     try {
/*  42 */       localVector = (Vector)super.clone();
/*     */     }
/*     */     catch (CloneNotSupportedException localCloneNotSupportedException) {
/*  45 */       System.err.println("cannot clone Vector.super");
/*  46 */       return null;
/*     */     }
/*  48 */     localVector.data = new Object[size()];
/*  49 */     System.arraycopy(this.data, 0, localVector.data, 0, size());
/*  50 */     return localVector;
/*     */   }
/*     */ 
/*     */   public synchronized Object elementAt(int paramInt)
/*     */   {
/*  60 */     if (paramInt >= this.data.length) {
/*  61 */       throw new ArrayIndexOutOfBoundsException(paramInt + " >= " + this.data.length);
/*     */     }
/*  63 */     if (paramInt < 0) {
/*  64 */       throw new ArrayIndexOutOfBoundsException(paramInt + " < 0 ");
/*     */     }
/*  66 */     return this.data[paramInt];
/*     */   }
/*     */ 
/*     */   public synchronized Enumeration elements() {
/*  70 */     return new VectorEnumerator(this);
/*     */   }
/*     */ 
/*     */   public synchronized void ensureCapacity(int paramInt) {
/*  74 */     if (paramInt + 1 > this.data.length) {
/*  75 */       Object[] arrayOfObject = this.data;
/*  76 */       int i = this.data.length * 2;
/*  77 */       if (paramInt + 1 > i) {
/*  78 */         i = paramInt + 1;
/*     */       }
/*  80 */       this.data = new Object[i];
/*  81 */       System.arraycopy(arrayOfObject, 0, this.data, 0, arrayOfObject.length);
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized boolean removeElement(Object paramObject)
/*     */   {
/*  88 */     for (int i = 0; (i <= this.lastElement) && (this.data[i] != paramObject); i++);
/*  91 */     if (i <= this.lastElement) {
/*  92 */       this.data[i] = null;
/*  93 */       int j = this.lastElement - i;
/*  94 */       if (j > 0) {
/*  95 */         System.arraycopy(this.data, i + 1, this.data, i, j);
/*     */       }
/*  97 */       this.lastElement -= 1;
/*  98 */       return true;
/*     */     }
/*     */ 
/* 101 */     return false;
/*     */   }
/*     */ 
/*     */   public synchronized void setElementAt(Object paramObject, int paramInt)
/*     */   {
/* 106 */     if (paramInt >= this.data.length) {
/* 107 */       throw new ArrayIndexOutOfBoundsException(paramInt + " >= " + this.data.length);
/*     */     }
/* 109 */     this.data[paramInt] = paramObject;
/*     */ 
/* 111 */     if (paramInt > this.lastElement)
/* 112 */       this.lastElement = paramInt;
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/* 119 */     return this.lastElement + 1;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.collections.impl.Vector
 * JD-Core Version:    0.6.2
 */