/*     */ package org.antlr.runtime.misc;
/*     */ 
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Stats
/*     */ {
/*     */   public static final String ANTLRWORKS_DIR = "antlrworks";
/*     */ 
/*     */   public static double stddev(int[] X)
/*     */   {
/*  56 */     int m = X.length;
/*  57 */     if (m <= 1) {
/*  58 */       return 0.0D;
/*     */     }
/*  60 */     double xbar = avg(X);
/*  61 */     double s2 = 0.0D;
/*  62 */     for (int i = 0; i < m; i++) {
/*  63 */       s2 += (X[i] - xbar) * (X[i] - xbar);
/*     */     }
/*  65 */     s2 /= (m - 1);
/*  66 */     return Math.sqrt(s2);
/*     */   }
/*     */ 
/*     */   public static double avg(int[] X)
/*     */   {
/*  71 */     double xbar = 0.0D;
/*  72 */     int m = X.length;
/*  73 */     if (m == 0) {
/*  74 */       return 0.0D;
/*     */     }
/*  76 */     for (int i = 0; i < m; i++) {
/*  77 */       xbar += X[i];
/*     */     }
/*  79 */     if (xbar >= 0.0D) {
/*  80 */       return xbar / m;
/*     */     }
/*  82 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   public static int min(int[] X) {
/*  86 */     int min = 2147483647;
/*  87 */     int m = X.length;
/*  88 */     if (m == 0) {
/*  89 */       return 0;
/*     */     }
/*  91 */     for (int i = 0; i < m; i++) {
/*  92 */       if (X[i] < min) {
/*  93 */         min = X[i];
/*     */       }
/*     */     }
/*  96 */     return min;
/*     */   }
/*     */ 
/*     */   public static int max(int[] X) {
/* 100 */     int max = -2147483648;
/* 101 */     int m = X.length;
/* 102 */     if (m == 0) {
/* 103 */       return 0;
/*     */     }
/* 105 */     for (int i = 0; i < m; i++) {
/* 106 */       if (X[i] > max) {
/* 107 */         max = X[i];
/*     */       }
/*     */     }
/* 110 */     return max;
/*     */   }
/*     */ 
/*     */   public static double avg(List<Integer> X)
/*     */   {
/* 115 */     double xbar = 0.0D;
/* 116 */     int m = X.size();
/* 117 */     if (m == 0) {
/* 118 */       return 0.0D;
/*     */     }
/* 120 */     for (int i = 0; i < m; i++) {
/* 121 */       xbar += ((Integer)X.get(i)).intValue();
/*     */     }
/* 123 */     if (xbar >= 0.0D) {
/* 124 */       return xbar / m;
/*     */     }
/* 126 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   public static int min(List<Integer> X) {
/* 130 */     int min = 2147483647;
/* 131 */     int m = X.size();
/* 132 */     if (m == 0) {
/* 133 */       return 0;
/*     */     }
/* 135 */     for (int i = 0; i < m; i++) {
/* 136 */       if (((Integer)X.get(i)).intValue() < min) {
/* 137 */         min = ((Integer)X.get(i)).intValue();
/*     */       }
/*     */     }
/* 140 */     return min;
/*     */   }
/*     */ 
/*     */   public static int max(List<Integer> X) {
/* 144 */     int max = -2147483648;
/* 145 */     int m = X.size();
/* 146 */     if (m == 0) {
/* 147 */       return 0;
/*     */     }
/* 149 */     for (int i = 0; i < m; i++) {
/* 150 */       if (((Integer)X.get(i)).intValue() > max) {
/* 151 */         max = ((Integer)X.get(i)).intValue();
/*     */       }
/*     */     }
/* 154 */     return max;
/*     */   }
/*     */ 
/*     */   public static int sum(int[] X) {
/* 158 */     int s = 0;
/* 159 */     int m = X.length;
/* 160 */     if (m == 0) {
/* 161 */       return 0;
/*     */     }
/* 163 */     for (int i = 0; i < m; i++) {
/* 164 */       s += X[i];
/*     */     }
/* 166 */     return s;
/*     */   }
/*     */ 
/*     */   public static void writeReport(String filename, String data) throws IOException {
/* 170 */     String absoluteFilename = getAbsoluteFileName(filename);
/* 171 */     File f = new File(absoluteFilename);
/* 172 */     File parent = f.getParentFile();
/* 173 */     parent.mkdirs();
/*     */ 
/* 175 */     FileOutputStream fos = new FileOutputStream(f, true);
/* 176 */     BufferedOutputStream bos = new BufferedOutputStream(fos);
/* 177 */     PrintStream ps = new PrintStream(bos);
/* 178 */     ps.println(data);
/* 179 */     ps.close();
/* 180 */     bos.close();
/* 181 */     fos.close();
/*     */   }
/*     */ 
/*     */   public static String getAbsoluteFileName(String filename) {
/* 185 */     return System.getProperty("user.home") + File.separator + "antlrworks" + File.separator + filename;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.misc.Stats
 * JD-Core Version:    0.6.2
 */