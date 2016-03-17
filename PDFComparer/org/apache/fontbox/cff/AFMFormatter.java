/*     */ package org.apache.fontbox.cff;
/*     */ 
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.awt.geom.Rectangle2D.Double;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.apache.fontbox.cff.encoding.CFFEncoding;
/*     */ 
/*     */ /** @deprecated */
/*     */ public class AFMFormatter
/*     */ {
/*     */   public static byte[] format(CFFFont font)
/*     */     throws IOException
/*     */   {
/*  51 */     DataOutput output = new DataOutput();
/*  52 */     printFont(font, output);
/*  53 */     return output.getBytes();
/*     */   }
/*     */ 
/*     */   private static void printFont(CFFFont font, DataOutput output)
/*     */     throws IOException
/*     */   {
/*  59 */     printFontMetrics(font, output);
/*     */   }
/*     */ 
/*     */   private static void printFontMetrics(CFFFont font, DataOutput output)
/*     */     throws IOException
/*     */   {
/*  66 */     List metrics = renderFont(font);
/*  67 */     output.println("StartFontMetrics 2.0");
/*  68 */     output.println("FontName " + font.getName());
/*  69 */     output.println("FullName " + font.getProperty("FullName"));
/*  70 */     output.println("FamilyName " + font.getProperty("FamilyName"));
/*  71 */     output.println("Weight " + font.getProperty("Weight"));
/*  72 */     CFFEncoding encoding = font.getEncoding();
/*  73 */     if (encoding.isFontSpecific())
/*     */     {
/*  75 */       output.println("EncodingScheme FontSpecific");
/*     */     }
/*  77 */     Rectangle2D bounds = getBounds(metrics);
/*  78 */     output.println("FontBBox " + (int)bounds.getX() + " " + (int)bounds.getY() + " " + (int)bounds.getMaxX() + " " + (int)bounds.getMaxY());
/*     */ 
/*  80 */     printDirectionMetrics(font, output);
/*  81 */     printCharMetrics(font, metrics, output);
/*  82 */     output.println("EndFontMetrics");
/*     */   }
/*     */ 
/*     */   private static void printDirectionMetrics(CFFFont font, DataOutput output)
/*     */     throws IOException
/*     */   {
/*  88 */     output.println("UnderlinePosition " + font.getProperty("UnderlinePosition"));
/*     */ 
/*  90 */     output.println("UnderlineThickness " + font.getProperty("UnderlineThickness"));
/*     */ 
/*  92 */     output.println("ItalicAngle " + font.getProperty("ItalicAngle"));
/*  93 */     output.println("IsFixedPitch " + font.getProperty("isFixedPitch"));
/*     */   }
/*     */ 
/*     */   private static void printCharMetrics(CFFFont font, List<CharMetric> metrics, DataOutput output)
/*     */     throws IOException
/*     */   {
/*  99 */     output.println("StartCharMetrics " + metrics.size());
/* 100 */     Collections.sort(metrics);
/* 101 */     for (CharMetric metric : metrics)
/*     */     {
/* 103 */       output.print("C " + metric.code + " ;");
/* 104 */       output.print(" ");
/* 105 */       output.print("WX " + metric.width + " ;");
/* 106 */       output.print(" ");
/* 107 */       output.print("N " + metric.name + " ;");
/* 108 */       output.print(" ");
/* 109 */       output.print("B " + (int)metric.bounds.getX() + " " + (int)metric.bounds.getY() + " " + (int)metric.bounds.getMaxX() + " " + (int)metric.bounds.getMaxY() + " ;");
/*     */ 
/* 113 */       output.println();
/*     */     }
/* 115 */     output.println("EndCharMetrics");
/*     */   }
/*     */ 
/*     */   private static List<CharMetric> renderFont(CFFFont font) throws IOException
/*     */   {
/* 120 */     List metrics = new ArrayList();
/* 121 */     CharStringRenderer renderer = font.createRenderer();
/* 122 */     Collection mappings = font.getMappings();
/* 123 */     for (CFFFont.Mapping mapping : mappings)
/*     */     {
/* 125 */       CharMetric metric = new CharMetric(null);
/* 126 */       metric.code = mapping.getCode();
/* 127 */       metric.name = mapping.getName();
/* 128 */       renderer.render(mapping.toType1Sequence());
/* 129 */       metric.width = renderer.getWidth();
/* 130 */       metric.bounds = renderer.getBounds();
/* 131 */       metrics.add(metric);
/*     */     }
/* 133 */     return metrics;
/*     */   }
/*     */ 
/*     */   private static Rectangle2D getBounds(List<CharMetric> metrics)
/*     */   {
/* 138 */     Rectangle2D bounds = null;
/* 139 */     for (CharMetric metric : metrics)
/*     */     {
/* 141 */       if (bounds == null)
/*     */       {
/* 143 */         bounds = new Rectangle2D.Double();
/* 144 */         bounds.setFrame(metric.bounds);
/*     */       }
/*     */       else
/*     */       {
/* 148 */         Rectangle2D.union(bounds, metric.bounds, bounds);
/*     */       }
/*     */     }
/* 151 */     return bounds;
/*     */   }
/*     */ 
/*     */   private static class CharMetric
/*     */     implements Comparable<CharMetric>
/*     */   {
/*     */     private int code;
/*     */     private String name;
/*     */     private int width;
/*     */     private Rectangle2D bounds;
/*     */ 
/*     */     public int compareTo(CharMetric that)
/*     */     {
/* 167 */       return this.code - that.code;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.cff.AFMFormatter
 * JD-Core Version:    0.6.2
 */