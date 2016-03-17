/*    */ package org.apache.fontbox.util.autodetect;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public abstract class NativeFontDirFinder
/*    */   implements FontDirFinder
/*    */ {
/*    */   public List<File> find()
/*    */   {
/* 37 */     List fontDirList = new ArrayList();
/* 38 */     String[] searchableDirectories = getSearchableDirectories();
/* 39 */     if (searchableDirectories != null)
/*    */     {
/* 41 */       for (int i = 0; i < searchableDirectories.length; i++)
/*    */       {
/* 43 */         File fontDir = new File(searchableDirectories[i]);
/* 44 */         if ((fontDir.exists()) && (fontDir.canRead()))
/*    */         {
/* 46 */           fontDirList.add(fontDir);
/*    */         }
/*    */       }
/*    */     }
/* 50 */     return fontDirList;
/*    */   }
/*    */ 
/*    */   protected abstract String[] getSearchableDirectories();
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.util.autodetect.NativeFontDirFinder
 * JD-Core Version:    0.6.2
 */