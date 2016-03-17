/*     */ package org.stringtemplate.v4.misc;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Member;
/*     */ import java.lang.reflect.Method;
/*     */ import org.antlr.runtime.misc.DoubleKeyMap;
/*     */ import org.stringtemplate.v4.Interpreter;
/*     */ import org.stringtemplate.v4.ModelAdaptor;
/*     */ import org.stringtemplate.v4.ST;
/*     */ 
/*     */ public class ObjectModelAdaptor
/*     */   implements ModelAdaptor
/*     */ {
/*  41 */   protected DoubleKeyMap<Class, String, Member> classAndPropertyToMemberCache = new DoubleKeyMap();
/*     */   static STNoSuchPropertyException cachedException;
/*     */ 
/*     */   public synchronized Object getProperty(Interpreter interp, ST self, Object o, Object property, String propertyName)
/*     */     throws STNoSuchPropertyException
/*     */   {
/*  52 */     Object value = null;
/*  53 */     Class c = o.getClass();
/*     */ 
/*  55 */     if (property == null) {
/*  56 */       return throwNoSuchProperty(c.getName() + "." + propertyName);
/*     */     }
/*     */ 
/*  60 */     Member member = (Member)this.classAndPropertyToMemberCache.get(c, propertyName);
/*  61 */     if (member != null) {
/*     */       try {
/*  63 */         Class memberClass = member.getClass();
/*  64 */         if (memberClass == Method.class) return ((Method)member).invoke(o, new Object[0]);
/*  65 */         if (memberClass == Field.class) return ((Field)member).get(o); 
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*  68 */         throwNoSuchProperty(c.getName() + "." + propertyName);
/*     */       }
/*     */     }
/*  71 */     return lookupMethod(o, propertyName, value, c);
/*     */   }
/*     */ 
/*     */   public synchronized Object lookupMethod(Object o, String propertyName, Object value, Class c)
/*     */   {
/*  76 */     String methodSuffix = Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1, propertyName.length());
/*     */ 
/*  78 */     Method m = Misc.getMethod(c, "get" + methodSuffix);
/*  79 */     if (m == null) {
/*  80 */       m = Misc.getMethod(c, "is" + methodSuffix);
/*  81 */       if (m == null)
/*  82 */         m = Misc.getMethod(c, "has" + methodSuffix);
/*     */     }
/*     */     try
/*     */     {
/*  86 */       if (m != null) {
/*  87 */         this.classAndPropertyToMemberCache.put(c, propertyName, m);
/*  88 */         value = Misc.invokeMethod(m, o, value);
/*     */       }
/*     */       else
/*     */       {
/*  92 */         Field f = c.getField(propertyName);
/*  93 */         this.classAndPropertyToMemberCache.put(c, propertyName, f);
/*     */         try {
/*  95 */           value = Misc.accessField(f, o, value);
/*     */         }
/*     */         catch (IllegalAccessException iae) {
/*  98 */           throwNoSuchProperty(c.getName() + "." + propertyName);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 103 */       throwNoSuchProperty(c.getName() + "." + propertyName);
/*     */     }
/*     */ 
/* 106 */     return value;
/*     */   }
/*     */ 
/*     */   protected Object throwNoSuchProperty(String propertyName) {
/* 110 */     if (cachedException == null) cachedException = new STNoSuchPropertyException();
/* 111 */     cachedException.propertyName = propertyName;
/* 112 */     throw cachedException;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.misc.ObjectModelAdaptor
 * JD-Core Version:    0.6.2
 */