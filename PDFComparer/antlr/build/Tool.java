/*     */ package antlr.build;
/*     */ 
/*     */ import antlr.Utils;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Method;
/*     */ 
/*     */ public class Tool
/*     */ {
/*  50 */   public String os = null;
/*     */ 
/*     */   public Tool() {
/*  53 */     this.os = System.getProperty("os.name");
/*     */   }
/*     */ 
/*     */   public static void main(String[] paramArrayOfString) {
/*  57 */     if (paramArrayOfString.length != 1) {
/*  58 */       System.err.println("usage: java antlr.build.Tool action");
/*  59 */       return;
/*     */     }
/*  61 */     String str1 = "antlr.build.ANTLR";
/*  62 */     String str2 = paramArrayOfString[0];
/*  63 */     Tool localTool = new Tool();
/*  64 */     localTool.perform(str1, str2);
/*     */   }
/*     */ 
/*     */   public void perform(String paramString1, String paramString2)
/*     */   {
/*  71 */     if ((paramString1 == null) || (paramString2 == null)) {
/*  72 */       error("missing app or action");
/*  73 */       return;
/*     */     }
/*  75 */     Class localClass = null;
/*  76 */     Method localMethod = null;
/*  77 */     Object localObject = null;
/*     */     try {
/*  79 */       localObject = Utils.createInstanceOf(paramString1);
/*     */     }
/*     */     catch (Exception localException1)
/*     */     {
/*     */       try {
/*  84 */         if (!paramString1.startsWith("antlr.build.")) {
/*  85 */           localClass = Utils.loadClass("antlr.build." + paramString1);
/*     */         }
/*  87 */         error("no such application " + paramString1, localException1);
/*     */       }
/*     */       catch (Exception localException3) {
/*  90 */         error("no such application " + paramString1, localException3);
/*     */       }
/*     */     }
/*  93 */     if ((localClass == null) || (localObject == null))
/*  94 */       return;
/*     */     try
/*     */     {
/*  97 */       localMethod = localClass.getMethod(paramString2, new Class[] { Tool.class });
/*     */ 
/*  99 */       localMethod.invoke(localObject, new Object[] { this });
/*     */     }
/*     */     catch (Exception localException2)
/*     */     {
/* 103 */       error("no such action for application " + paramString1, localException2);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void system(String paramString)
/*     */   {
/* 111 */     Runtime localRuntime = Runtime.getRuntime();
/*     */     try {
/* 113 */       log(paramString);
/* 114 */       Process localProcess = null;
/* 115 */       if (!this.os.startsWith("Windows"))
/*     */       {
/* 117 */         localProcess = localRuntime.exec(new String[] { "sh", "-c", paramString });
/*     */       }
/*     */       else {
/* 120 */         localProcess = localRuntime.exec(paramString);
/*     */       }
/* 122 */       StreamScarfer localStreamScarfer1 = new StreamScarfer(localProcess.getErrorStream(), "stderr", this);
/*     */ 
/* 124 */       StreamScarfer localStreamScarfer2 = new StreamScarfer(localProcess.getInputStream(), "stdout", this);
/*     */ 
/* 126 */       localStreamScarfer1.start();
/* 127 */       localStreamScarfer2.start();
/* 128 */       int i = localProcess.waitFor();
/*     */     }
/*     */     catch (Exception localException) {
/* 131 */       error("cannot exec " + paramString, localException);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void antlr(String paramString)
/*     */   {
/* 139 */     String str = null;
/*     */     try {
/* 141 */       str = new File(paramString).getParent();
/* 142 */       if (str != null)
/* 143 */         str = new File(str).getCanonicalPath();
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/* 147 */       error("Invalid grammar file: " + paramString);
/*     */     }
/* 149 */     if (str != null) {
/* 150 */       log("java antlr.Tool -o " + str + " " + paramString);
/* 151 */       antlr.Tool localTool = new antlr.Tool();
/* 152 */       localTool.doEverything(new String[] { "-o", str, paramString });
/*     */     }
/*     */   }
/*     */ 
/*     */   public void stdout(String paramString)
/*     */   {
/* 158 */     System.out.println(paramString);
/*     */   }
/*     */ 
/*     */   public void stderr(String paramString)
/*     */   {
/* 163 */     System.err.println(paramString);
/*     */   }
/*     */ 
/*     */   public void error(String paramString) {
/* 167 */     System.err.println("antlr.build.Tool: " + paramString);
/*     */   }
/*     */ 
/*     */   public void log(String paramString) {
/* 171 */     System.out.println("executing: " + paramString);
/*     */   }
/*     */ 
/*     */   public void error(String paramString, Exception paramException) {
/* 175 */     System.err.println("antlr.build.Tool: " + paramString);
/* 176 */     paramException.printStackTrace(System.err);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.build.Tool
 * JD-Core Version:    0.6.2
 */