/*    */ package org.apache.pdfbox.util;
/*    */ 
/*    */ import java.io.File;
/*    */ import javax.swing.filechooser.FileFilter;
/*    */ 
/*    */ public class ExtensionFileFilter extends FileFilter
/*    */ {
/* 32 */   private String[] extensions = null;
/*    */   private String desc;
/*    */ 
/*    */   public ExtensionFileFilter(String[] ext, String description)
/*    */   {
/* 43 */     this.extensions = ext;
/* 44 */     this.desc = description;
/*    */   }
/*    */ 
/*    */   public boolean accept(File pathname)
/*    */   {
/* 52 */     if (pathname.isDirectory())
/*    */     {
/* 54 */       return true;
/*    */     }
/* 56 */     boolean acceptable = false;
/* 57 */     String name = pathname.getName().toUpperCase();
/* 58 */     for (int i = 0; (!acceptable) && (i < this.extensions.length); i++)
/*    */     {
/* 60 */       if (name.endsWith(this.extensions[i].toUpperCase()))
/*    */       {
/* 62 */         acceptable = true;
/*    */       }
/*    */     }
/* 65 */     return acceptable;
/*    */   }
/*    */ 
/*    */   public String getDescription()
/*    */   {
/* 73 */     return this.desc;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.ExtensionFileFilter
 * JD-Core Version:    0.6.2
 */