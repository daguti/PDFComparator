/*     */ package org.apache.pdfbox.preflight;
/*     */ 
/*     */ import java.util.Stack;
/*     */ 
/*     */ public class PreflightPath
/*     */ {
/*  35 */   private Stack objectPath = new Stack();
/*     */ 
/*  38 */   private Stack<Class> classObjPath = new Stack();
/*     */ 
/*     */   public boolean pushObject(Object pathElement)
/*     */   {
/*  43 */     boolean pushed = false;
/*  44 */     if (pathElement != null)
/*     */     {
/*  46 */       this.objectPath.push(pathElement);
/*  47 */       this.classObjPath.push(pathElement.getClass());
/*  48 */       pushed = true;
/*     */     }
/*  50 */     return pushed;
/*     */   }
/*     */ 
/*     */   public <T> T getPathElement(int position, Class<T> expectedType)
/*     */   {
/*  63 */     if ((position < 0) || (position >= this.objectPath.size()))
/*     */     {
/*  65 */       return null;
/*     */     }
/*  67 */     return this.objectPath.get(position);
/*     */   }
/*     */ 
/*     */   public <T> int getClosestTypePosition(Class<T> type)
/*     */   {
/*  78 */     for (int i = this.objectPath.size(); i-- > 0; )
/*     */     {
/*  80 */       if (((Class)this.classObjPath.get(i)).equals(type))
/*     */       {
/*  82 */         return i;
/*     */       }
/*     */     }
/*  85 */     return -1;
/*     */   }
/*     */ 
/*     */   public <T> T getClosestPathElement(Class<T> type)
/*     */   {
/*  90 */     return getPathElement(getClosestTypePosition(type), type);
/*     */   }
/*     */ 
/*     */   public Object peek()
/*     */   {
/* 100 */     return this.objectPath.peek();
/*     */   }
/*     */ 
/*     */   public Object pop()
/*     */   {
/* 105 */     this.classObjPath.pop();
/* 106 */     return this.objectPath.pop();
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 111 */     this.classObjPath.clear();
/* 112 */     this.objectPath.clear();
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/* 117 */     return this.objectPath.size();
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 122 */     return this.objectPath.isEmpty();
/*     */   }
/*     */ 
/*     */   public boolean isExpectedType(Class<?> type)
/*     */   {
/* 128 */     Class knownType = (Class)this.classObjPath.peek();
/* 129 */     return (knownType != null) && ((type.equals(knownType)) || (type.isAssignableFrom(knownType)));
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.PreflightPath
 * JD-Core Version:    0.6.2
 */