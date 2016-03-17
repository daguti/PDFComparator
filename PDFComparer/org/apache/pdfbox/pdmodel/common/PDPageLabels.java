/*     */ package org.apache.pdfbox.pdmodel.common;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import java.util.SortedMap;
/*     */ import java.util.TreeMap;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSInteger;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ 
/*     */ public class PDPageLabels
/*     */   implements COSObjectable
/*     */ {
/*     */   private SortedMap<Integer, PDPageLabelRange> labels;
/*     */   private PDDocument doc;
/*     */ 
/*     */   public PDPageLabels(PDDocument document)
/*     */   {
/*  65 */     this.labels = new TreeMap();
/*  66 */     this.doc = document;
/*  67 */     PDPageLabelRange defaultRange = new PDPageLabelRange();
/*  68 */     defaultRange.setStyle("D");
/*  69 */     this.labels.put(Integer.valueOf(0), defaultRange);
/*     */   }
/*     */ 
/*     */   public PDPageLabels(PDDocument document, COSDictionary dict)
/*     */     throws IOException
/*     */   {
/*  92 */     this(document);
/*  93 */     if (dict == null)
/*     */     {
/*  95 */       return;
/*     */     }
/*  97 */     PDNumberTreeNode root = new PDNumberTreeNode(dict, COSDictionary.class);
/*  98 */     findLabels(root);
/*     */   }
/*     */ 
/*     */   private void findLabels(PDNumberTreeNode node) throws IOException
/*     */   {
/* 103 */     if (node.getKids() != null)
/*     */     {
/* 105 */       List kids = node.getKids();
/* 106 */       for (PDNumberTreeNode kid : kids)
/*     */       {
/* 108 */         findLabels(kid);
/*     */       }
/*     */     }
/* 111 */     else if (node.getNumbers() != null)
/*     */     {
/* 113 */       Map numbers = node.getNumbers();
/* 114 */       for (Map.Entry i : numbers.entrySet())
/*     */       {
/* 116 */         if (((Integer)i.getKey()).intValue() >= 0)
/*     */         {
/* 118 */           this.labels.put(i.getKey(), new PDPageLabelRange((COSDictionary)i.getValue()));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getPageRangeCount()
/*     */   {
/* 138 */     return this.labels.size();
/*     */   }
/*     */ 
/*     */   public PDPageLabelRange getPageLabelRange(int startPage)
/*     */   {
/* 153 */     return (PDPageLabelRange)this.labels.get(Integer.valueOf(startPage));
/*     */   }
/*     */ 
/*     */   public void setLabelItem(int startPage, PDPageLabelRange item)
/*     */   {
/* 167 */     this.labels.put(Integer.valueOf(startPage), item);
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/* 175 */     COSDictionary dict = new COSDictionary();
/* 176 */     COSArray arr = new COSArray();
/* 177 */     for (Map.Entry i : this.labels.entrySet())
/*     */     {
/* 179 */       arr.add(COSInteger.get(((Integer)i.getKey()).intValue()));
/* 180 */       arr.add((COSObjectable)i.getValue());
/*     */     }
/* 182 */     dict.setItem(COSName.NUMS, arr);
/* 183 */     return dict;
/*     */   }
/*     */ 
/*     */   public Map<String, Integer> getPageIndicesByLabels()
/*     */   {
/* 202 */     final Map labelMap = new HashMap(this.doc.getNumberOfPages());
/*     */ 
/* 204 */     computeLabels(new LabelHandler()
/*     */     {
/*     */       public void newLabel(int pageIndex, String label)
/*     */       {
/* 208 */         labelMap.put(label, Integer.valueOf(pageIndex));
/*     */       }
/*     */     });
/* 211 */     return labelMap;
/*     */   }
/*     */ 
/*     */   public String[] getLabelsByPageIndices()
/*     */   {
/* 223 */     final String[] map = new String[this.doc.getNumberOfPages()];
/* 224 */     computeLabels(new LabelHandler()
/*     */     {
/*     */       public void newLabel(int pageIndex, String label)
/*     */       {
/* 228 */         if (pageIndex < PDPageLabels.this.doc.getNumberOfPages())
/*     */         {
/* 230 */           map[pageIndex] = label;
/*     */         }
/*     */       }
/*     */     });
/* 234 */     return map;
/*     */   }
/*     */ 
/*     */   private void computeLabels(LabelHandler handler)
/*     */   {
/* 249 */     Iterator iterator = this.labels.entrySet().iterator();
/*     */ 
/* 251 */     if (!iterator.hasNext())
/*     */     {
/* 253 */       return;
/*     */     }
/* 255 */     int pageIndex = 0;
/* 256 */     Map.Entry lastEntry = (Map.Entry)iterator.next();
/* 257 */     while (iterator.hasNext())
/*     */     {
/* 259 */       Map.Entry entry = (Map.Entry)iterator.next();
/* 260 */       int numPages = ((Integer)entry.getKey()).intValue() - ((Integer)lastEntry.getKey()).intValue();
/* 261 */       LabelGenerator gen = new LabelGenerator((PDPageLabelRange)lastEntry.getValue(), numPages);
/*     */ 
/* 263 */       while (gen.hasNext())
/*     */       {
/* 265 */         handler.newLabel(pageIndex, gen.next());
/* 266 */         pageIndex++;
/*     */       }
/* 268 */       lastEntry = entry;
/*     */     }
/* 270 */     LabelGenerator gen = new LabelGenerator((PDPageLabelRange)lastEntry.getValue(), this.doc.getNumberOfPages() - ((Integer)lastEntry.getKey()).intValue());
/*     */ 
/* 272 */     while (gen.hasNext())
/*     */     {
/* 274 */       handler.newLabel(pageIndex, gen.next());
/* 275 */       pageIndex++;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class LabelGenerator
/*     */     implements Iterator<String>
/*     */   {
/*     */     private PDPageLabelRange labelInfo;
/*     */     private int numPages;
/*     */     private int currentPage;
/* 362 */     private static final String[][] ROMANS = { { "", "i", "ii", "iii", "iv", "v", "vi", "vii", "viii", "ix" }, { "", "x", "xx", "xxx", "xl", "l", "lx", "lxx", "lxxx", "xc" }, { "", "c", "cc", "ccc", "cd", "d", "dc", "dcc", "dccc", "cm" } };
/*     */ 
/*     */     public LabelGenerator(PDPageLabelRange label, int pages)
/*     */     {
/* 293 */       this.labelInfo = label;
/* 294 */       this.numPages = pages;
/* 295 */       this.currentPage = 0;
/*     */     }
/*     */ 
/*     */     public boolean hasNext()
/*     */     {
/* 300 */       return this.currentPage < this.numPages;
/*     */     }
/*     */ 
/*     */     public String next()
/*     */     {
/* 305 */       if (!hasNext())
/*     */       {
/* 307 */         throw new NoSuchElementException();
/*     */       }
/* 309 */       StringBuilder buf = new StringBuilder();
/* 310 */       if (this.labelInfo.getPrefix() != null)
/*     */       {
/* 312 */         String label = this.labelInfo.getPrefix();
/*     */ 
/* 315 */         while (label.lastIndexOf(0) != -1)
/*     */         {
/* 317 */           label = label.substring(0, label.length() - 1);
/*     */         }
/* 319 */         buf.append(label);
/*     */       }
/* 321 */       if (this.labelInfo.getStyle() != null)
/*     */       {
/* 323 */         buf.append(getNumber(this.labelInfo.getStart() + this.currentPage, this.labelInfo.getStyle()));
/*     */       }
/*     */ 
/* 326 */       this.currentPage += 1;
/* 327 */       return buf.toString();
/*     */     }
/*     */ 
/*     */     private String getNumber(int pageIndex, String style)
/*     */     {
/* 332 */       if ("D".equals(style))
/*     */       {
/* 334 */         return Integer.toString(pageIndex);
/*     */       }
/* 336 */       if ("a".equals(style))
/*     */       {
/* 338 */         return makeLetterLabel(pageIndex);
/*     */       }
/* 340 */       if ("A".equals(style))
/*     */       {
/* 342 */         return makeLetterLabel(pageIndex).toUpperCase();
/*     */       }
/* 344 */       if ("r".equals(style))
/*     */       {
/* 346 */         return makeRomanLabel(pageIndex);
/*     */       }
/* 348 */       if ("R".equals(style))
/*     */       {
/* 350 */         return makeRomanLabel(pageIndex).toUpperCase();
/*     */       }
/*     */ 
/* 355 */       return Integer.toString(pageIndex);
/*     */     }
/*     */ 
/*     */     private static String makeRomanLabel(int pageIndex)
/*     */     {
/* 370 */       StringBuilder buf = new StringBuilder();
/* 371 */       int power = 0;
/* 372 */       while ((power < 3) && (pageIndex > 0))
/*     */       {
/* 374 */         buf.insert(0, ROMANS[power][(pageIndex % 10)]);
/* 375 */         pageIndex /= 10;
/* 376 */         power++;
/*     */       }
/*     */ 
/* 385 */       for (int i = 0; i < pageIndex; i++)
/*     */       {
/* 387 */         buf.insert(0, 'm');
/*     */       }
/* 389 */       return buf.toString();
/*     */     }
/*     */ 
/*     */     private static String makeLetterLabel(int num)
/*     */     {
/* 398 */       StringBuilder buf = new StringBuilder();
/* 399 */       int numLetters = num / 26 + Integer.signum(num % 26);
/* 400 */       int letter = num % 26 + 26 * (1 - Integer.signum(num % 26)) + 64;
/* 401 */       for (int i = 0; i < numLetters; i++)
/*     */       {
/* 403 */         buf.appendCodePoint(letter);
/*     */       }
/* 405 */       return buf.toString();
/*     */     }
/*     */ 
/*     */     public void remove()
/*     */     {
/* 411 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static abstract interface LabelHandler
/*     */   {
/*     */     public abstract void newLabel(int paramInt, String paramString);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.PDPageLabels
 * JD-Core Version:    0.6.2
 */