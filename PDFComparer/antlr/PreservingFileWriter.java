/*    */ package antlr;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.BufferedWriter;
/*    */ import java.io.File;
/*    */ import java.io.FileReader;
/*    */ import java.io.FileWriter;
/*    */ import java.io.IOException;
/*    */ import java.io.Reader;
/*    */ import java.io.Writer;
/*    */ 
/*    */ public class PreservingFileWriter extends FileWriter
/*    */ {
/*    */   protected File target_file;
/*    */   protected File tmp_file;
/*    */ 
/*    */   public PreservingFileWriter(String paramString)
/*    */     throws IOException
/*    */   {
/* 24 */     super(paramString + ".antlr.tmp");
/*    */ 
/* 27 */     this.target_file = new File(paramString);
/*    */ 
/* 29 */     String str = this.target_file.getParent();
/* 30 */     if (str != null)
/*    */     {
/* 32 */       File localFile = new File(str);
/*    */ 
/* 34 */       if (!localFile.exists())
/* 35 */         throw new IOException("destination directory of '" + paramString + "' doesn't exist");
/* 36 */       if (!localFile.canWrite())
/* 37 */         throw new IOException("destination directory of '" + paramString + "' isn't writeable");
/*    */     }
/* 39 */     if ((this.target_file.exists()) && (!this.target_file.canWrite())) {
/* 40 */       throw new IOException("cannot write to '" + paramString + "'");
/*    */     }
/*    */ 
/* 43 */     this.tmp_file = new File(paramString + ".antlr.tmp");
/*    */   }
/*    */ 
/*    */   public void close()
/*    */     throws IOException
/*    */   {
/* 56 */     BufferedReader localBufferedReader1 = null;
/* 57 */     BufferedWriter localBufferedWriter = null;
/*    */     try
/*    */     {
/* 61 */       super.close();
/*    */ 
/* 63 */       char[] arrayOfChar1 = new char[1024];
/*    */ 
/* 67 */       if (this.target_file.length() == this.tmp_file.length())
/*    */       {
/* 71 */         char[] arrayOfChar2 = new char[1024];
/*    */ 
/* 73 */         localBufferedReader1 = new BufferedReader(new FileReader(this.tmp_file));
/* 74 */         BufferedReader localBufferedReader2 = new BufferedReader(new FileReader(this.target_file));
/*    */ 
/* 76 */         int m = 1;
/*    */ 
/* 78 */         label164: while (m != 0)
/*    */         {
/* 80 */           int j = localBufferedReader1.read(arrayOfChar1, 0, 1024);
/* 81 */           int k = localBufferedReader2.read(arrayOfChar2, 0, 1024);
/* 82 */           if (j != k)
/*    */           {
/* 84 */             m = 0;
/*    */           }
/* 87 */           else if (j != -1)
/*    */           {
/* 89 */             for (int n = 0; ; n++) { if (n >= j)
/*    */                 break label164;
/* 91 */               if (arrayOfChar1[n] != arrayOfChar2[n])
/*    */               {
/* 93 */                 m = 0;
/* 94 */                 break;
/*    */               }
/*    */             }
/*    */           }
/*    */         }
/* 99 */         localBufferedReader1.close();
/* 100 */         localBufferedReader2.close();
/*    */ 
/* 102 */         localBufferedReader1 = localBufferedReader2 = null;
/*    */ 
/* 104 */         if (m != 0) {
/* 105 */           return;
/*    */         }
/*    */       }
/* 108 */       localBufferedReader1 = new BufferedReader(new FileReader(this.tmp_file));
/* 109 */       localBufferedWriter = new BufferedWriter(new FileWriter(this.target_file));
/*    */       while (true)
/*    */       {
/* 113 */         int i = localBufferedReader1.read(arrayOfChar1, 0, 1024);
/* 114 */         if (i == -1)
/*    */           break;
/* 116 */         localBufferedWriter.write(arrayOfChar1, 0, i);
/*    */       }
/*    */     }
/*    */     finally {
/* 120 */       if (localBufferedReader1 != null)
/*    */         try {
/* 122 */           localBufferedReader1.close();
/*    */         } catch (IOException localIOException1) {
/*    */         }
/* 125 */       if (localBufferedWriter != null)
/*    */         try {
/* 127 */           localBufferedWriter.close();
/*    */         }
/*    */         catch (IOException localIOException2) {
/*    */         }
/* 131 */       if ((this.tmp_file != null) && (this.tmp_file.exists()))
/*    */       {
/* 133 */         this.tmp_file.delete();
/* 134 */         this.tmp_file = null;
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.PreservingFileWriter
 * JD-Core Version:    0.6.2
 */