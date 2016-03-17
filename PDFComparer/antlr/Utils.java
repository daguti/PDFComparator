/*    */ package antlr;
/*    */ 
/*    */ public class Utils
/*    */ {
/*  4 */   private static boolean useSystemExit = true;
/*  5 */   private static boolean useDirectClassLoading = false;
/*    */ 
/*    */   public static Class loadClass(String paramString)
/*    */     throws ClassNotFoundException
/*    */   {
/*    */     try
/*    */     {
/* 16 */       ClassLoader localClassLoader = Thread.currentThread().getContextClassLoader();
/* 17 */       if ((!useDirectClassLoading) && (localClassLoader != null)) {
/* 18 */         return localClassLoader.loadClass(paramString);
/*    */       }
/* 20 */       return Class.forName(paramString);
/*    */     } catch (Exception localException) {
/*    */     }
/* 23 */     return Class.forName(paramString);
/*    */   }
/*    */ 
/*    */   public static Object createInstanceOf(String paramString) throws ClassNotFoundException, InstantiationException, IllegalAccessException
/*    */   {
/* 28 */     return loadClass(paramString).newInstance();
/*    */   }
/*    */ 
/*    */   public static void error(String paramString) {
/* 32 */     if (useSystemExit)
/* 33 */       System.exit(1);
/* 34 */     throw new RuntimeException("ANTLR Panic: " + paramString);
/*    */   }
/*    */ 
/*    */   public static void error(String paramString, Throwable paramThrowable) {
/* 38 */     if (useSystemExit)
/* 39 */       System.exit(1);
/* 40 */     throw new RuntimeException("ANTLR Panic", paramThrowable);
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/*  7 */     if ("true".equalsIgnoreCase(System.getProperty("ANTLR_DO_NOT_EXIT", "false")))
/*  8 */       useSystemExit = false;
/*  9 */     if ("true".equalsIgnoreCase(System.getProperty("ANTLR_USE_DIRECT_CLASS_LOADING", "false")))
/* 10 */       useDirectClassLoading = true;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.Utils
 * JD-Core Version:    0.6.2
 */