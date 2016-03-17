/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.BaseColor;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import java.util.Arrays;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class PdfDeviceNColor
/*     */   implements ICachedColorSpace, IPdfSpecialColorSpace
/*     */ {
/*     */   PdfSpotColor[] spotColors;
/*     */   ColorDetails[] colorantsDetails;
/*     */ 
/*     */   public PdfDeviceNColor(PdfSpotColor[] spotColors)
/*     */   {
/*  14 */     this.spotColors = spotColors;
/*     */   }
/*     */ 
/*     */   public int getNumberOfColorants() {
/*  18 */     return this.spotColors.length;
/*     */   }
/*     */ 
/*     */   public PdfSpotColor[] getSpotColors() {
/*  22 */     return this.spotColors;
/*     */   }
/*     */ 
/*     */   public ColorDetails[] getColorantDetails(PdfWriter writer) {
/*  26 */     if (this.colorantsDetails == null) {
/*  27 */       this.colorantsDetails = new ColorDetails[this.spotColors.length];
/*  28 */       int i = 0;
/*  29 */       for (PdfSpotColor spotColorant : this.spotColors) {
/*  30 */         this.colorantsDetails[i] = writer.addSimple(spotColorant);
/*  31 */         i++;
/*     */       }
/*     */     }
/*  34 */     return this.colorantsDetails;
/*     */   }
/*     */ 
/*     */   public PdfObject getPdfObject(PdfWriter writer) {
/*  38 */     PdfArray array = new PdfArray(PdfName.DEVICEN);
/*     */ 
/*  40 */     PdfArray colorants = new PdfArray();
/*  41 */     float[] colorantsRanges = new float[this.spotColors.length * 2];
/*  42 */     PdfDictionary colorantsDict = new PdfDictionary();
/*  43 */     String psFunFooter = "";
/*     */ 
/*  45 */     int numberOfColorants = this.spotColors.length;
/*  46 */     float[][] CMYK = new float[4][numberOfColorants];
/*  47 */     for (int i = 0; 
/*  48 */       i < numberOfColorants; i++) {
/*  49 */       PdfSpotColor spotColorant = this.spotColors[i];
/*  50 */       colorantsRanges[(2 * i)] = 0.0F;
/*  51 */       colorantsRanges[(2 * i + 1)] = 1.0F;
/*  52 */       colorants.add(spotColorant.getName());
/*  53 */       if (colorantsDict.get(spotColorant.getName()) != null)
/*  54 */         throw new RuntimeException(MessageLocalization.getComposedMessage("devicen.component.names.shall.be.different", new Object[0]));
/*  55 */       if (this.colorantsDetails != null)
/*  56 */         colorantsDict.put(spotColorant.getName(), this.colorantsDetails[i].getIndirectReference());
/*     */       else
/*  58 */         colorantsDict.put(spotColorant.getName(), spotColorant.getPdfObject(writer));
/*  59 */       BaseColor color = spotColorant.getAlternativeCS();
/*  60 */       if ((color instanceof ExtendedColor)) {
/*  61 */         int type = ((ExtendedColor)color).type;
/*  62 */         switch (type) {
/*     */         case 1:
/*  64 */           CMYK[0][i] = 0.0F;
/*  65 */           CMYK[1][i] = 0.0F;
/*  66 */           CMYK[2][i] = 0.0F;
/*  67 */           CMYK[3][i] = (1.0F - ((GrayColor)color).getGray());
/*  68 */           break;
/*     */         case 2:
/*  70 */           CMYK[0][i] = ((CMYKColor)color).getCyan();
/*  71 */           CMYK[1][i] = ((CMYKColor)color).getMagenta();
/*  72 */           CMYK[2][i] = ((CMYKColor)color).getYellow();
/*  73 */           CMYK[3][i] = ((CMYKColor)color).getBlack();
/*  74 */           break;
/*     */         case 7:
/*  76 */           CMYKColor cmyk = ((LabColor)color).toCmyk();
/*  77 */           CMYK[0][i] = cmyk.getCyan();
/*  78 */           CMYK[1][i] = cmyk.getMagenta();
/*  79 */           CMYK[2][i] = cmyk.getYellow();
/*  80 */           CMYK[3][i] = cmyk.getBlack();
/*  81 */           break;
/*     */         default:
/*  83 */           throw new RuntimeException(MessageLocalization.getComposedMessage("only.rgb.gray.and.cmyk.are.supported.as.alternative.color.spaces", new Object[0]));
/*     */         }
/*     */       } else {
/*  86 */         float r = color.getRed();
/*  87 */         float g = color.getGreen();
/*  88 */         float b = color.getBlue();
/*  89 */         float computedC = 0.0F; float computedM = 0.0F; float computedY = 0.0F; float computedK = 0.0F;
/*     */ 
/*  92 */         if ((r == 0.0F) && (g == 0.0F) && (b == 0.0F)) {
/*  93 */           computedK = 1.0F;
/*     */         } else {
/*  95 */           computedC = 1.0F - r / 255.0F;
/*  96 */           computedM = 1.0F - g / 255.0F;
/*  97 */           computedY = 1.0F - b / 255.0F;
/*     */ 
/*  99 */           float minCMY = Math.min(computedC, Math.min(computedM, computedY));
/*     */ 
/* 101 */           computedC = (computedC - minCMY) / (1.0F - minCMY);
/* 102 */           computedM = (computedM - minCMY) / (1.0F - minCMY);
/* 103 */           computedY = (computedY - minCMY) / (1.0F - minCMY);
/* 104 */           computedK = minCMY;
/*     */         }
/* 106 */         CMYK[0][i] = computedC;
/* 107 */         CMYK[1][i] = computedM;
/* 108 */         CMYK[2][i] = computedY;
/* 109 */         CMYK[3][i] = computedK;
/*     */       }
/* 111 */       psFunFooter = psFunFooter + "pop ";
/*     */     }
/* 113 */     array.add(colorants);
/*     */ 
/* 115 */     String psFunHeader = String.format(Locale.US, "1.000000 %d 1 roll ", new Object[] { Integer.valueOf(numberOfColorants + 1) });
/* 116 */     array.add(PdfName.DEVICECMYK);
/* 117 */     psFunHeader = psFunHeader + psFunHeader + psFunHeader + psFunHeader;
/* 118 */     String psFun = "";
/* 119 */     for (i = numberOfColorants + 4; 
/* 120 */       i > numberOfColorants; i--) {
/* 121 */       psFun = psFun + String.format(Locale.US, "%d -1 roll ", new Object[] { Integer.valueOf(i) });
/* 122 */       for (int j = numberOfColorants; j > 0; j--) {
/* 123 */         psFun = psFun + String.format(Locale.US, "%d index %f mul 1.000000 cvr exch sub mul ", new Object[] { Integer.valueOf(j), Float.valueOf(CMYK[(numberOfColorants + 4 - i)][(numberOfColorants - j)]) });
/*     */       }
/* 125 */       psFun = psFun + String.format(Locale.US, "1.000000 cvr exch sub %d 1 roll ", new Object[] { Integer.valueOf(i) });
/*     */     }
/*     */ 
/* 128 */     PdfFunction func = PdfFunction.type4(writer, colorantsRanges, new float[] { 0.0F, 1.0F, 0.0F, 1.0F, 0.0F, 1.0F, 0.0F, 1.0F }, "{ " + psFunHeader + psFun + psFunFooter + "}");
/* 129 */     array.add(func.getReference());
/*     */ 
/* 131 */     PdfDictionary attr = new PdfDictionary();
/* 132 */     attr.put(PdfName.SUBTYPE, PdfName.NCHANNEL);
/* 133 */     attr.put(PdfName.COLORANTS, colorantsDict);
/* 134 */     array.add(attr);
/*     */ 
/* 136 */     return array;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object o)
/*     */   {
/* 141 */     if (this == o) return true;
/* 142 */     if (!(o instanceof PdfDeviceNColor)) return false;
/*     */ 
/* 144 */     PdfDeviceNColor that = (PdfDeviceNColor)o;
/*     */ 
/* 146 */     if (!Arrays.equals(this.spotColors, that.spotColors)) return false;
/*     */ 
/* 148 */     return true;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 153 */     return Arrays.hashCode(this.spotColors);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfDeviceNColor
 * JD-Core Version:    0.6.2
 */