/*     */ package org.apache.pdfbox.pdmodel.encryption;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ 
/*     */ /** @deprecated */
/*     */ public class PDEncryptionManager
/*     */ {
/*  42 */   private static Map handlerMap = Collections.synchronizedMap(new HashMap());
/*     */ 
/*     */   public static void registerSecurityHandler(String filterName, Class handlerClass)
/*     */   {
/*  63 */     handlerMap.put(COSName.getPDFName(filterName), handlerClass);
/*     */   }
/*     */ 
/*     */   public static PDEncryptionDictionary getEncryptionDictionary(COSDictionary dictionary)
/*     */     throws IOException
/*     */   {
/*  78 */     Object retval = null;
/*  79 */     if (dictionary != null)
/*     */     {
/*  81 */       COSName filter = (COSName)dictionary.getDictionaryObject(COSName.FILTER);
/*  82 */       Class handlerClass = (Class)handlerMap.get(filter);
/*  83 */       if (handlerClass == null)
/*     */       {
/*  85 */         throw new IOException("No handler for security handler '" + filter.getName() + "'");
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/*  91 */         Constructor ctor = handlerClass.getConstructor(new Class[] { COSDictionary.class });
/*     */ 
/*  94 */         retval = ctor.newInstance(new Object[] { dictionary });
/*     */       }
/*     */       catch (NoSuchMethodException e)
/*     */       {
/* 100 */         throw new IOException(e.getMessage());
/*     */       }
/*     */       catch (InstantiationException e)
/*     */       {
/* 104 */         throw new IOException(e.getMessage());
/*     */       }
/*     */       catch (IllegalAccessException e)
/*     */       {
/* 108 */         throw new IOException(e.getMessage());
/*     */       }
/*     */       catch (InvocationTargetException e)
/*     */       {
/* 112 */         throw new IOException(e.getMessage());
/*     */       }
/*     */     }
/*     */ 
/* 116 */     return (PDEncryptionDictionary)retval;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  46 */     registerSecurityHandler("Standard", PDStandardEncryption.class);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.encryption.PDEncryptionManager
 * JD-Core Version:    0.6.2
 */