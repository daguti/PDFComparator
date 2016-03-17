/*     */ package org.apache.pdfbox.pdmodel.font;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.fontbox.afm.AFMParser;
/*     */ import org.apache.fontbox.afm.CharMetric;
/*     */ import org.apache.fontbox.afm.FontMetric;
/*     */ import org.apache.fontbox.pfb.PfbParser;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSInteger;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.encoding.AFMEncoding;
/*     */ import org.apache.pdfbox.encoding.DictionaryEncoding;
/*     */ import org.apache.pdfbox.encoding.Encoding;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ 
/*     */ public class PDType1AfmPfbFont extends PDType1Font
/*     */ {
/*     */   private static final int BUFFERSIZE = 65535;
/*     */   private FontMetric metric;
/*     */ 
/*     */   public PDType1AfmPfbFont(PDDocument doc, String afmname)
/*     */     throws IOException
/*     */   {
/*  71 */     InputStream afmin = new BufferedInputStream(new FileInputStream(afmname), 65535);
/*  72 */     String pfbname = afmname.replaceAll(".AFM", "").replaceAll(".afm", "") + ".pfb";
/*  73 */     InputStream pfbin = new BufferedInputStream(new FileInputStream(pfbname), 65535);
/*  74 */     load(doc, afmin, pfbin);
/*     */   }
/*     */ 
/*     */   public PDType1AfmPfbFont(PDDocument doc, InputStream afm, InputStream pfb)
/*     */     throws IOException
/*     */   {
/*  88 */     load(doc, afm, pfb);
/*     */   }
/*     */ 
/*     */   private void load(PDDocument doc, InputStream afm, InputStream pfb)
/*     */     throws IOException
/*     */   {
/* 102 */     PDFontDescriptorDictionary fd = new PDFontDescriptorDictionary();
/* 103 */     setFontDescriptor(fd);
/*     */ 
/* 106 */     PfbParser pfbparser = new PfbParser(pfb);
/* 107 */     pfb.close();
/*     */ 
/* 109 */     PDStream fontStream = new PDStream(doc, pfbparser.getInputStream(), false);
/* 110 */     fontStream.getStream().setInt("Length", pfbparser.size());
/* 111 */     for (int i = 0; i < pfbparser.getLengths().length; i++)
/*     */     {
/* 113 */       fontStream.getStream().setInt("Length" + (i + 1), pfbparser.getLengths()[i]);
/*     */     }
/* 115 */     fontStream.addCompression();
/* 116 */     fd.setFontFile(fontStream);
/*     */ 
/* 119 */     AFMParser parser = new AFMParser(afm);
/* 120 */     parser.parse();
/* 121 */     this.metric = parser.getResult();
/* 122 */     setFontEncoding(afmToDictionary(new AFMEncoding(this.metric)));
/*     */ 
/* 125 */     setBaseFont(this.metric.getFontName());
/* 126 */     fd.setFontName(this.metric.getFontName());
/* 127 */     fd.setFontFamily(this.metric.getFamilyName());
/* 128 */     fd.setNonSymbolic(true);
/* 129 */     fd.setFontBoundingBox(new PDRectangle(this.metric.getFontBBox()));
/* 130 */     fd.setItalicAngle(this.metric.getItalicAngle());
/* 131 */     fd.setAscent(this.metric.getAscender());
/* 132 */     fd.setDescent(this.metric.getDescender());
/* 133 */     fd.setCapHeight(this.metric.getCapHeight());
/* 134 */     fd.setXHeight(this.metric.getXHeight());
/* 135 */     fd.setAverageWidth(this.metric.getAverageCharacterWidth());
/* 136 */     fd.setCharacterSet(this.metric.getCharacterSet());
/*     */ 
/* 139 */     int firstchar = 255;
/* 140 */     int lastchar = 0;
/*     */ 
/* 143 */     List listmetric = this.metric.getCharMetrics();
/* 144 */     Encoding encoding = getFontEncoding();
/* 145 */     int maxWidths = 256;
/* 146 */     List widths = new ArrayList(maxWidths);
/* 147 */     int zero = 250;
/* 148 */     Iterator iter = listmetric.iterator();
/* 149 */     for (int i = 0; i < maxWidths; i++)
/*     */     {
/* 151 */       widths.add(Float.valueOf(zero));
/*     */     }
/* 153 */     while (iter.hasNext())
/*     */     {
/* 155 */       CharMetric m = (CharMetric)iter.next();
/* 156 */       int n = m.getCharacterCode();
/* 157 */       if (n > 0)
/*     */       {
/* 159 */         firstchar = Math.min(firstchar, n);
/* 160 */         lastchar = Math.max(lastchar, n);
/* 161 */         if (m.getWx() > 0.0F)
/*     */         {
/* 163 */           int width = Math.round(m.getWx());
/* 164 */           widths.set(n, Float.valueOf(width));
/*     */ 
/* 168 */           if ((m.getName().equals("germandbls")) && (n != 223))
/*     */           {
/* 170 */             widths.set(223, Float.valueOf(width));
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/* 178 */       else if (m.getName().equals("adieresis"))
/*     */       {
/* 180 */         widths.set(228, widths.get(encoding.getCode("a")));
/*     */       }
/* 182 */       else if (m.getName().equals("odieresis"))
/*     */       {
/* 184 */         widths.set(246, widths.get(encoding.getCode("o")));
/*     */       }
/* 186 */       else if (m.getName().equals("udieresis"))
/*     */       {
/* 188 */         widths.set(252, widths.get(encoding.getCode("u")));
/*     */       }
/* 190 */       else if (m.getName().equals("Adieresis"))
/*     */       {
/* 192 */         widths.set(196, widths.get(encoding.getCode("A")));
/*     */       }
/* 194 */       else if (m.getName().equals("Odieresis"))
/*     */       {
/* 196 */         widths.set(214, widths.get(encoding.getCode("O")));
/*     */       }
/* 198 */       else if (m.getName().equals("Udieresis"))
/*     */       {
/* 200 */         widths.set(220, widths.get(encoding.getCode("U")));
/*     */       }
/*     */     }
/*     */ 
/* 204 */     setFirstChar(0);
/* 205 */     setLastChar(255);
/* 206 */     setWidths(widths);
/*     */   }
/*     */ 
/*     */   private DictionaryEncoding afmToDictionary(AFMEncoding encoding)
/*     */     throws IOException
/*     */   {
/* 217 */     COSArray array = new COSArray();
/* 218 */     array.add(COSInteger.ZERO);
/* 219 */     for (int i = 0; i < 256; i++)
/*     */     {
/* 221 */       array.add(COSName.getPDFName(encoding.getName(i)));
/*     */     }
/*     */ 
/* 225 */     array.set(224, COSName.getPDFName("germandbls"));
/* 226 */     array.set(229, COSName.getPDFName("adieresis"));
/* 227 */     array.set(247, COSName.getPDFName("odieresis"));
/* 228 */     array.set(253, COSName.getPDFName("udieresis"));
/* 229 */     array.set(197, COSName.getPDFName("Adieresis"));
/* 230 */     array.set(215, COSName.getPDFName("Odieresis"));
/* 231 */     array.set(221, COSName.getPDFName("Udieresis"));
/*     */ 
/* 233 */     COSDictionary dictionary = new COSDictionary();
/* 234 */     dictionary.setItem(COSName.NAME, COSName.ENCODING);
/* 235 */     dictionary.setItem(COSName.DIFFERENCES, array);
/* 236 */     dictionary.setItem(COSName.BASE_ENCODING, COSName.STANDARD_ENCODING);
/* 237 */     return new DictionaryEncoding(dictionary);
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 243 */     super.clear();
/* 244 */     this.metric = null;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.font.PDType1AfmPfbFont
 * JD-Core Version:    0.6.2
 */