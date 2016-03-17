/*    */ package antlr.build;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FilenameFilter;
/*    */ 
/*    */ public class ANTLR
/*    */ {
/*  7 */   public static String compiler = "javac";
/*  8 */   public static String jarName = "antlr.jar";
/*  9 */   public static String root = ".";
/*    */ 
/* 11 */   public static String[] srcdir = { "antlr", "antlr/actions/cpp", "antlr/actions/java", "antlr/actions/csharp", "antlr/collections", "antlr/collections/impl", "antlr/debug", "antlr/ASdebug", "antlr/debug/misc", "antlr/preprocessor" };
/*    */ 
/*    */   public ANTLR()
/*    */   {
/* 25 */     compiler = System.getProperty("antlr.build.compiler", compiler);
/* 26 */     root = System.getProperty("antlr.build.root", root);
/*    */   }
/*    */   public String getName() {
/* 29 */     return "ANTLR";
/*    */   }
/*    */ 
/*    */   public void build(Tool paramTool) {
/* 33 */     if (!rootIsValidANTLRDir(paramTool)) {
/* 34 */       return;
/*    */     }
/*    */ 
/* 37 */     paramTool.antlr(root + "/antlr/antlr.g");
/* 38 */     paramTool.antlr(root + "/antlr/tokdef.g");
/* 39 */     paramTool.antlr(root + "/antlr/preprocessor/preproc.g");
/* 40 */     paramTool.antlr(root + "/antlr/actions/java/action.g");
/* 41 */     paramTool.antlr(root + "/antlr/actions/cpp/action.g");
/* 42 */     paramTool.antlr(root + "/antlr/actions/csharp/action.g");
/* 43 */     for (int i = 0; i < srcdir.length; i++) {
/* 44 */       String str = compiler + " -d " + root + " " + root + "/" + srcdir[i] + "/*.java";
/* 45 */       paramTool.system(str);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void jar(Tool paramTool)
/*    */   {
/* 51 */     if (!rootIsValidANTLRDir(paramTool)) {
/* 52 */       return;
/*    */     }
/* 54 */     StringBuffer localStringBuffer = new StringBuffer(2000);
/* 55 */     localStringBuffer.append("jar cvf " + root + "/" + jarName);
/* 56 */     for (int i = 0; i < srcdir.length; i++) {
/* 57 */       localStringBuffer.append(" " + root + "/" + srcdir[i] + "/*.class");
/*    */     }
/* 59 */     paramTool.system(localStringBuffer.toString());
/*    */   }
/*    */ 
/*    */   protected boolean rootIsValidANTLRDir(Tool paramTool)
/*    */   {
/* 66 */     if (root == null) {
/* 67 */       return false;
/*    */     }
/* 69 */     File localFile1 = new File(root);
/* 70 */     if (!localFile1.exists()) {
/* 71 */       paramTool.error("Property antlr.build.root==" + root + " does not exist");
/* 72 */       return false;
/*    */     }
/* 74 */     if (!localFile1.isDirectory()) {
/* 75 */       paramTool.error("Property antlr.build.root==" + root + " is not a directory");
/* 76 */       return false;
/*    */     }
/* 78 */     String[] arrayOfString1 = localFile1.list(new FilenameFilter() {
/*    */       public boolean accept(File paramAnonymousFile, String paramAnonymousString) {
/* 80 */         return (paramAnonymousFile.isDirectory()) && (paramAnonymousString.equals("antlr"));
/*    */       }
/*    */     });
/* 83 */     if ((arrayOfString1 == null) || (arrayOfString1.length == 0)) {
/* 84 */       paramTool.error("Property antlr.build.root==" + root + " does not appear to be a valid ANTLR project root (no antlr subdir)");
/* 85 */       return false;
/*    */     }
/* 87 */     File localFile2 = new File(root + "/antlr");
/* 88 */     String[] arrayOfString2 = localFile2.list();
/* 89 */     if ((arrayOfString2 == null) || (arrayOfString2.length == 0)) {
/* 90 */       paramTool.error("Property antlr.build.root==" + root + " does not appear to be a valid ANTLR project root (no .java files in antlr subdir");
/* 91 */       return false;
/*    */     }
/* 93 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.build.ANTLR
 * JD-Core Version:    0.6.2
 */