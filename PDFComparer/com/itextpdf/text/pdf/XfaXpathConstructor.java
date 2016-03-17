/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.pdf.security.XpathConstructor;
/*     */ 
/*     */ public class XfaXpathConstructor
/*     */   implements XpathConstructor
/*     */ {
/*  71 */   private final String CONFIG = "config";
/*  72 */   private final String CONNECTIONSET = "connectionSet";
/*  73 */   private final String DATASETS = "datasets";
/*  74 */   private final String LOCALESET = "localeSet";
/*  75 */   private final String PDF = "pdf";
/*  76 */   private final String SOURCESET = "sourceSet";
/*  77 */   private final String STYLESHEET = "stylesheet";
/*  78 */   private final String TEMPLATE = "template";
/*  79 */   private final String XDC = "xdc";
/*  80 */   private final String XFDF = "xfdf";
/*  81 */   private final String XMPMETA = "xmpmeta";
/*     */   private String xpathExpression;
/*     */ 
/*     */   public XfaXpathConstructor()
/*     */   {
/*  87 */     this.xpathExpression = "";
/*     */   }
/*     */ 
/*     */   public XfaXpathConstructor(XdpPackage xdpPackage)
/*     */   {
/*     */     String strPackage;
/*  96 */     switch (1.$SwitchMap$com$itextpdf$text$pdf$XfaXpathConstructor$XdpPackage[xdpPackage.ordinal()]) {
/*     */     case 1:
/*  98 */       strPackage = "config";
/*  99 */       break;
/*     */     case 2:
/* 101 */       strPackage = "connectionSet";
/* 102 */       break;
/*     */     case 3:
/* 104 */       strPackage = "datasets";
/* 105 */       break;
/*     */     case 4:
/* 107 */       strPackage = "localeSet";
/* 108 */       break;
/*     */     case 5:
/* 110 */       strPackage = "pdf";
/* 111 */       break;
/*     */     case 6:
/* 113 */       strPackage = "sourceSet";
/* 114 */       break;
/*     */     case 7:
/* 116 */       strPackage = "stylesheet";
/* 117 */       break;
/*     */     case 8:
/* 119 */       strPackage = "template";
/* 120 */       break;
/*     */     case 9:
/* 122 */       strPackage = "xdc";
/* 123 */       break;
/*     */     case 10:
/* 125 */       strPackage = "xfdf";
/* 126 */       break;
/*     */     case 11:
/* 128 */       strPackage = "xmpmeta";
/* 129 */       break;
/*     */     default:
/* 131 */       this.xpathExpression = "";
/* 132 */       return;
/*     */     }
/*     */ 
/* 135 */     StringBuilder builder = new StringBuilder("/xdp:xdp/*[local-name()='");
/* 136 */     builder.append(strPackage);
/* 137 */     builder.append("']");
/* 138 */     this.xpathExpression = builder.toString();
/*     */   }
/*     */ 
/*     */   public String getXpathExpression()
/*     */   {
/* 147 */     return this.xpathExpression;
/*     */   }
/*     */ 
/*     */   public static enum XdpPackage
/*     */   {
/*  58 */     Config, 
/*  59 */     ConnectionSet, 
/*  60 */     Datasets, 
/*  61 */     LocaleSet, 
/*  62 */     Pdf, 
/*  63 */     SourceSet, 
/*  64 */     Stylesheet, 
/*  65 */     Template, 
/*  66 */     Xdc, 
/*  67 */     Xfdf, 
/*  68 */     Xmpmeta;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.XfaXpathConstructor
 * JD-Core Version:    0.6.2
 */