/*     */ package org.apache.pdfbox.util;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class TextNormalize
/*     */ {
/*  30 */   private ICU4JImpl icu4j = null;
/*  31 */   private static final HashMap<Integer, String> DIACHASH = new HashMap();
/*     */   private String outputEncoding;
/*     */ 
/*     */   public TextNormalize(String encoding)
/*     */   {
/*  45 */     findICU4J();
/*  46 */     this.outputEncoding = encoding;
/*     */   }
/*     */ 
/*     */   private void findICU4J()
/*     */   {
/*     */     try
/*     */     {
/*  54 */       getClass().getClassLoader().loadClass("com.ibm.icu.text.Bidi");
/*  55 */       getClass().getClassLoader().loadClass("com.ibm.icu.text.Normalizer");
/*  56 */       this.icu4j = new ICU4JImpl();
/*     */     }
/*     */     catch (ClassNotFoundException e)
/*     */     {
/*  60 */       this.icu4j = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void populateDiacHash()
/*     */   {
/*  72 */     DIACHASH.put(new Integer(96), "̀");
/*  73 */     DIACHASH.put(new Integer(715), "̀");
/*  74 */     DIACHASH.put(new Integer(39), "́");
/*  75 */     DIACHASH.put(new Integer(697), "́");
/*  76 */     DIACHASH.put(new Integer(714), "́");
/*  77 */     DIACHASH.put(new Integer(94), "̂");
/*  78 */     DIACHASH.put(new Integer(710), "̂");
/*  79 */     DIACHASH.put(new Integer(126), "̃");
/*  80 */     DIACHASH.put(new Integer(713), "̄");
/*  81 */     DIACHASH.put(new Integer(176), "̊");
/*  82 */     DIACHASH.put(new Integer(698), "̋");
/*  83 */     DIACHASH.put(new Integer(711), "̌");
/*  84 */     DIACHASH.put(new Integer(712), "̍");
/*  85 */     DIACHASH.put(new Integer(34), "̎");
/*  86 */     DIACHASH.put(new Integer(699), "̒");
/*  87 */     DIACHASH.put(new Integer(700), "̓");
/*  88 */     DIACHASH.put(new Integer(1158), "̓");
/*  89 */     DIACHASH.put(new Integer(1370), "̓");
/*  90 */     DIACHASH.put(new Integer(701), "̔");
/*  91 */     DIACHASH.put(new Integer(1157), "̔");
/*  92 */     DIACHASH.put(new Integer(1369), "̔");
/*  93 */     DIACHASH.put(new Integer(724), "̝");
/*  94 */     DIACHASH.put(new Integer(725), "̞");
/*  95 */     DIACHASH.put(new Integer(726), "̟");
/*  96 */     DIACHASH.put(new Integer(727), "̠");
/*  97 */     DIACHASH.put(new Integer(690), "̡");
/*  98 */     DIACHASH.put(new Integer(716), "̩");
/*  99 */     DIACHASH.put(new Integer(695), "̫");
/* 100 */     DIACHASH.put(new Integer(717), "̱");
/* 101 */     DIACHASH.put(new Integer(95), "̲");
/* 102 */     DIACHASH.put(new Integer(8270), "͙");
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public String makeLineLogicalOrder(String str, boolean isRtlDominant)
/*     */   {
/* 119 */     if (this.icu4j != null)
/*     */     {
/* 121 */       return this.icu4j.makeLineLogicalOrder(str, isRtlDominant);
/*     */     }
/*     */ 
/* 125 */     return str;
/*     */   }
/*     */ 
/*     */   public String normalizePres(String str)
/*     */   {
/* 138 */     if (this.icu4j != null)
/*     */     {
/* 140 */       return this.icu4j.normalizePres(str);
/*     */     }
/*     */ 
/* 144 */     return str;
/*     */   }
/*     */ 
/*     */   public String normalizeDiac(String str)
/*     */   {
/* 159 */     if ((this.outputEncoding != null) && (this.outputEncoding.toUpperCase().startsWith("UTF")))
/*     */     {
/* 161 */       Integer c = new Integer(str.charAt(0));
/*     */ 
/* 163 */       if (DIACHASH.containsKey(c))
/*     */       {
/* 165 */         return (String)DIACHASH.get(c);
/*     */       }
/* 167 */       if (this.icu4j != null)
/*     */       {
/* 169 */         return this.icu4j.normalizeDiac(str);
/*     */       }
/*     */ 
/* 173 */       return str;
/*     */     }
/*     */ 
/* 178 */     return str;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  36 */     populateDiacHash();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.TextNormalize
 * JD-Core Version:    0.6.2
 */