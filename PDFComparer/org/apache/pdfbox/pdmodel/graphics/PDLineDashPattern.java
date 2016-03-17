/*     */ package org.apache.pdfbox.pdmodel.graphics;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSInteger;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ import org.apache.pdfbox.pdmodel.common.COSArrayList;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ 
/*     */ public class PDLineDashPattern
/*     */   implements COSObjectable, Cloneable
/*     */ {
/*  44 */   private static final Log LOG = LogFactory.getLog(PDLineDashPattern.class);
/*     */ 
/*  46 */   private COSArray lineDashPattern = null;
/*     */ 
/*     */   public PDLineDashPattern()
/*     */   {
/*  53 */     this.lineDashPattern = new COSArray();
/*  54 */     this.lineDashPattern.add(new COSArray());
/*  55 */     this.lineDashPattern.add(COSInteger.ZERO);
/*     */   }
/*     */ 
/*     */   public PDLineDashPattern(COSArray ldp)
/*     */   {
/*  65 */     this.lineDashPattern = ldp;
/*     */   }
/*     */ 
/*     */   public PDLineDashPattern(COSArray ldp, int phase)
/*     */   {
/*  76 */     this.lineDashPattern = new COSArray();
/*  77 */     this.lineDashPattern.add(ldp);
/*  78 */     this.lineDashPattern.add(COSInteger.get(phase));
/*     */   }
/*     */ 
/*     */   public Object clone()
/*     */   {
/*  86 */     PDLineDashPattern pattern = null;
/*     */     try
/*     */     {
/*  89 */       pattern = (PDLineDashPattern)super.clone();
/*  90 */       pattern.setDashPattern(getDashPattern());
/*  91 */       pattern.setPhaseStart(getPhaseStart());
/*     */     }
/*     */     catch (CloneNotSupportedException e)
/*     */     {
/*  95 */       LOG.error(e, e);
/*     */     }
/*  97 */     return pattern;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/* 105 */     return this.lineDashPattern;
/*     */   }
/*     */ 
/*     */   public int getPhaseStart()
/*     */   {
/* 116 */     COSNumber phase = (COSNumber)this.lineDashPattern.get(1);
/* 117 */     return phase.intValue();
/*     */   }
/*     */ 
/*     */   public void setPhaseStart(int phase)
/*     */   {
/* 127 */     this.lineDashPattern.set(1, phase);
/*     */   }
/*     */ 
/*     */   public List getDashPattern()
/*     */   {
/* 138 */     COSArray dashPatterns = (COSArray)this.lineDashPattern.get(0);
/* 139 */     return COSArrayList.convertIntegerCOSArrayToList(dashPatterns);
/*     */   }
/*     */ 
/*     */   public COSArray getCOSDashPattern()
/*     */   {
/* 149 */     return (COSArray)this.lineDashPattern.get(0);
/*     */   }
/*     */ 
/*     */   public void setDashPattern(List dashPattern)
/*     */   {
/* 159 */     this.lineDashPattern.set(0, COSArrayList.converterToCOSArray(dashPattern));
/*     */   }
/*     */ 
/*     */   public boolean isDashPatternEmpty()
/*     */   {
/* 169 */     float[] dashPattern = getCOSDashPattern().toFloatArray();
/* 170 */     boolean dashPatternEmpty = true;
/* 171 */     if (dashPattern != null)
/*     */     {
/* 173 */       int arraySize = dashPattern.length;
/* 174 */       for (int i = 0; i < arraySize; i++)
/*     */       {
/* 176 */         if (dashPattern[i] > 0.0F)
/*     */         {
/* 178 */           dashPatternEmpty = false;
/* 179 */           break;
/*     */         }
/*     */       }
/*     */     }
/* 183 */     return dashPatternEmpty;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.PDLineDashPattern
 * JD-Core Version:    0.6.2
 */