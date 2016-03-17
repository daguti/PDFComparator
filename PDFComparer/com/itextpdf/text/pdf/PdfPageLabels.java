/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.factories.RomanAlphabetFactory;
/*     */ import com.itextpdf.text.factories.RomanNumberFactory;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class PdfPageLabels
/*     */ {
/*     */   public static final int DECIMAL_ARABIC_NUMERALS = 0;
/*     */   public static final int UPPERCASE_ROMAN_NUMERALS = 1;
/*     */   public static final int LOWERCASE_ROMAN_NUMERALS = 2;
/*     */   public static final int UPPERCASE_LETTERS = 3;
/*     */   public static final int LOWERCASE_LETTERS = 4;
/*     */   public static final int EMPTY = 5;
/*  85 */   static PdfName[] numberingStyle = { PdfName.D, PdfName.R, new PdfName("r"), PdfName.A, new PdfName("a") };
/*     */   private HashMap<Integer, PdfDictionary> map;
/*     */ 
/*     */   public PdfPageLabels()
/*     */   {
/*  94 */     this.map = new HashMap();
/*  95 */     addPageLabel(1, 0, null, 1);
/*     */   }
/*     */ 
/*     */   public void addPageLabel(int page, int numberStyle, String text, int firstPage)
/*     */   {
/* 105 */     if ((page < 1) || (firstPage < 1))
/* 106 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("in.a.page.label.the.page.numbers.must.be.greater.or.equal.to.1", new Object[0]));
/* 107 */     PdfDictionary dic = new PdfDictionary();
/* 108 */     if ((numberStyle >= 0) && (numberStyle < numberingStyle.length))
/* 109 */       dic.put(PdfName.S, numberingStyle[numberStyle]);
/* 110 */     if (text != null)
/* 111 */       dic.put(PdfName.P, new PdfString(text, "UnicodeBig"));
/* 112 */     if (firstPage != 1)
/* 113 */       dic.put(PdfName.ST, new PdfNumber(firstPage));
/* 114 */     this.map.put(Integer.valueOf(page - 1), dic);
/*     */   }
/*     */ 
/*     */   public void addPageLabel(int page, int numberStyle, String text)
/*     */   {
/* 124 */     addPageLabel(page, numberStyle, text, 1);
/*     */   }
/*     */ 
/*     */   public void addPageLabel(int page, int numberStyle)
/*     */   {
/* 133 */     addPageLabel(page, numberStyle, null, 1);
/*     */   }
/*     */ 
/*     */   public void addPageLabel(PdfPageLabelFormat format)
/*     */   {
/* 139 */     addPageLabel(format.physicalPage, format.numberStyle, format.prefix, format.logicalPage);
/*     */   }
/*     */ 
/*     */   public void removePageLabel(int page)
/*     */   {
/* 146 */     if (page <= 1)
/* 147 */       return;
/* 148 */     this.map.remove(Integer.valueOf(page - 1));
/*     */   }
/*     */ 
/*     */   public PdfDictionary getDictionary(PdfWriter writer)
/*     */   {
/*     */     try
/*     */     {
/* 156 */       return PdfNumberTree.writeTree(this.map, writer);
/*     */     }
/*     */     catch (IOException e) {
/* 159 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String[] getPageLabels(PdfReader reader)
/*     */   {
/* 170 */     int n = reader.getNumberOfPages();
/*     */ 
/* 172 */     PdfDictionary dict = reader.getCatalog();
/* 173 */     PdfDictionary labels = (PdfDictionary)PdfReader.getPdfObjectRelease(dict.get(PdfName.PAGELABELS));
/* 174 */     if (labels == null) {
/* 175 */       return null;
/*     */     }
/* 177 */     String[] labelstrings = new String[n];
/*     */ 
/* 179 */     HashMap numberTree = PdfNumberTree.readTree(labels);
/*     */ 
/* 181 */     int pagecount = 1;
/*     */ 
/* 183 */     String prefix = "";
/* 184 */     char type = 'D';
/* 185 */     for (int i = 0; i < n; i++) {
/* 186 */       Integer current = Integer.valueOf(i);
/* 187 */       if (numberTree.containsKey(current)) {
/* 188 */         PdfDictionary d = (PdfDictionary)PdfReader.getPdfObjectRelease((PdfObject)numberTree.get(current));
/* 189 */         if (d.contains(PdfName.ST)) {
/* 190 */           pagecount = ((PdfNumber)d.get(PdfName.ST)).intValue();
/*     */         }
/*     */         else {
/* 193 */           pagecount = 1;
/*     */         }
/* 195 */         if (d.contains(PdfName.P)) {
/* 196 */           prefix = ((PdfString)d.get(PdfName.P)).toUnicodeString();
/*     */         }
/* 198 */         if (d.contains(PdfName.S)) {
/* 199 */           type = ((PdfName)d.get(PdfName.S)).toString().charAt(1);
/*     */         }
/*     */         else {
/* 202 */           type = 'e';
/*     */         }
/*     */       }
/* 205 */       switch (type) {
/*     */       default:
/* 207 */         labelstrings[i] = (prefix + pagecount);
/* 208 */         break;
/*     */       case 'R':
/* 210 */         labelstrings[i] = (prefix + RomanNumberFactory.getUpperCaseString(pagecount));
/* 211 */         break;
/*     */       case 'r':
/* 213 */         labelstrings[i] = (prefix + RomanNumberFactory.getLowerCaseString(pagecount));
/* 214 */         break;
/*     */       case 'A':
/* 216 */         labelstrings[i] = (prefix + RomanAlphabetFactory.getUpperCaseString(pagecount));
/* 217 */         break;
/*     */       case 'a':
/* 219 */         labelstrings[i] = (prefix + RomanAlphabetFactory.getLowerCaseString(pagecount));
/* 220 */         break;
/*     */       case 'e':
/* 222 */         labelstrings[i] = prefix;
/*     */       }
/*     */ 
/* 225 */       pagecount++;
/*     */     }
/* 227 */     return labelstrings;
/*     */   }
/*     */ 
/*     */   public static PdfPageLabelFormat[] getPageLabelFormats(PdfReader reader)
/*     */   {
/* 237 */     PdfDictionary dict = reader.getCatalog();
/* 238 */     PdfDictionary labels = (PdfDictionary)PdfReader.getPdfObjectRelease(dict.get(PdfName.PAGELABELS));
/* 239 */     if (labels == null)
/* 240 */       return null;
/* 241 */     HashMap numberTree = PdfNumberTree.readTree(labels);
/* 242 */     Integer[] numbers = new Integer[numberTree.size()];
/* 243 */     numbers = (Integer[])numberTree.keySet().toArray(numbers);
/* 244 */     Arrays.sort(numbers);
/* 245 */     PdfPageLabelFormat[] formats = new PdfPageLabelFormat[numberTree.size()];
/*     */ 
/* 249 */     for (int k = 0; k < numbers.length; k++) {
/* 250 */       Integer key = numbers[k];
/* 251 */       PdfDictionary d = (PdfDictionary)PdfReader.getPdfObjectRelease((PdfObject)numberTree.get(key));
/*     */       int pagecount;
/*     */       int pagecount;
/* 252 */       if (d.contains(PdfName.ST))
/* 253 */         pagecount = ((PdfNumber)d.get(PdfName.ST)).intValue();
/*     */       else
/* 255 */         pagecount = 1;
/*     */       String prefix;
/*     */       String prefix;
/* 257 */       if (d.contains(PdfName.P))
/* 258 */         prefix = ((PdfString)d.get(PdfName.P)).toUnicodeString();
/*     */       else
/* 260 */         prefix = "";
/*     */       int numberStyle;
/* 262 */       if (d.contains(PdfName.S)) {
/* 263 */         char type = ((PdfName)d.get(PdfName.S)).toString().charAt(1);
/*     */         int numberStyle;
/* 264 */         switch (type) { case 'R':
/* 265 */           numberStyle = 1; break;
/*     */         case 'r':
/* 266 */           numberStyle = 2; break;
/*     */         case 'A':
/* 267 */           numberStyle = 3; break;
/*     */         case 'a':
/* 268 */           numberStyle = 4; break;
/*     */         default:
/* 269 */           numberStyle = 0; }
/*     */       }
/*     */       else {
/* 272 */         numberStyle = 5;
/*     */       }
/* 274 */       formats[k] = new PdfPageLabelFormat(key.intValue() + 1, numberStyle, prefix, pagecount);
/*     */     }
/* 276 */     return formats;
/*     */   }
/*     */ 
/*     */   public static class PdfPageLabelFormat
/*     */   {
/*     */     public int physicalPage;
/*     */     public int numberStyle;
/*     */     public String prefix;
/*     */     public int logicalPage;
/*     */ 
/*     */     public PdfPageLabelFormat(int physicalPage, int numberStyle, String prefix, int logicalPage)
/*     */     {
/* 293 */       this.physicalPage = physicalPage;
/* 294 */       this.numberStyle = numberStyle;
/* 295 */       this.prefix = prefix;
/* 296 */       this.logicalPage = logicalPage;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfPageLabels
 * JD-Core Version:    0.6.2
 */