/*     */ package org.apache.fontbox.cff;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.DecimalFormatSymbols;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class Type1FontFormatter
/*     */ {
/*     */   public static byte[] format(CFFFont font)
/*     */     throws IOException
/*     */   {
/*  46 */     DataOutput output = new DataOutput();
/*  47 */     printFont(font, output);
/*  48 */     return output.getBytes();
/*     */   }
/*     */ 
/*     */   private static void printFont(CFFFont font, DataOutput output)
/*     */     throws IOException
/*     */   {
/*  54 */     output.println("%!FontType1-1.0 " + font.getName() + " " + font.getProperty("version"));
/*     */ 
/*  57 */     printFontDictionary(font, output);
/*     */ 
/*  59 */     for (int i = 0; i < 8; i++)
/*     */     {
/*  61 */       StringBuilder sb = new StringBuilder();
/*     */ 
/*  63 */       for (int j = 0; j < 64; j++)
/*     */       {
/*  65 */         sb.append("0");
/*     */       }
/*     */ 
/*  68 */       output.println(sb.toString());
/*     */     }
/*     */ 
/*  71 */     output.println("cleartomark");
/*     */   }
/*     */ 
/*     */   private static void printFontDictionary(CFFFont font, DataOutput output)
/*     */     throws IOException
/*     */   {
/*  77 */     output.println("10 dict begin");
/*  78 */     output.println("/FontInfo 10 dict dup begin");
/*  79 */     output.println("/version (" + font.getProperty("version") + ") readonly def");
/*     */ 
/*  81 */     output.println("/Notice (" + font.getProperty("Notice") + ") readonly def");
/*     */ 
/*  83 */     output.println("/FullName (" + font.getProperty("FullName") + ") readonly def");
/*     */ 
/*  85 */     output.println("/FamilyName (" + font.getProperty("FamilyName") + ") readonly def");
/*     */ 
/*  87 */     output.println("/Weight (" + font.getProperty("Weight") + ") readonly def");
/*     */ 
/*  89 */     output.println("/ItalicAngle " + font.getProperty("ItalicAngle") + " def");
/*     */ 
/*  91 */     output.println("/isFixedPitch " + font.getProperty("isFixedPitch") + " def");
/*     */ 
/*  93 */     output.println("/UnderlinePosition " + font.getProperty("UnderlinePosition") + " def");
/*     */ 
/*  95 */     output.println("/UnderlineThickness " + font.getProperty("UnderlineThickness") + " def");
/*     */ 
/*  97 */     output.println("end readonly def");
/*  98 */     output.println("/FontName /" + font.getName() + " def");
/*  99 */     output.println("/PaintType " + font.getProperty("PaintType") + " def");
/* 100 */     output.println("/FontType 1 def");
/* 101 */     NumberFormat matrixFormat = new DecimalFormat("0.########", new DecimalFormatSymbols(Locale.US));
/* 102 */     output.println("/FontMatrix " + formatArray(font.getProperty("FontMatrix"), matrixFormat, false) + " readonly def");
/*     */ 
/* 105 */     output.println("/FontBBox " + formatArray(font.getProperty("FontBBox"), false) + " readonly def");
/*     */ 
/* 108 */     output.println("/StrokeWidth " + font.getProperty("StrokeWidth") + " def");
/*     */ 
/* 111 */     Collection mappings = font.getMappings();
/*     */ 
/* 113 */     output.println("/Encoding 256 array");
/* 114 */     output.println("0 1 255 {1 index exch /.notdef put} for");
/*     */ 
/* 116 */     for (CFFFont.Mapping mapping : mappings)
/*     */     {
/* 118 */       output.println("dup " + mapping.getCode() + " /" + mapping.getName() + " put");
/*     */     }
/*     */ 
/* 122 */     output.println("readonly def");
/* 123 */     output.println("currentdict end");
/*     */ 
/* 125 */     DataOutput eexecOutput = new DataOutput();
/*     */ 
/* 127 */     printEexecFontDictionary(font, eexecOutput);
/*     */ 
/* 129 */     output.println("currentfile eexec");
/*     */ 
/* 131 */     byte[] eexecBytes = Type1FontUtil.eexecEncrypt(eexecOutput.getBytes());
/*     */ 
/* 133 */     String hexString = Type1FontUtil.hexEncode(eexecBytes);
/* 134 */     for (int i = 0; i < hexString.length(); )
/*     */     {
/* 136 */       String hexLine = hexString.substring(i, Math.min(i + 72, hexString.length()));
/*     */ 
/* 139 */       output.println(hexLine);
/*     */ 
/* 141 */       i += hexLine.length();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void printEexecFontDictionary(CFFFont font, DataOutput output)
/*     */     throws IOException
/*     */   {
/* 148 */     output.println("dup /Private 15 dict dup begin");
/* 149 */     output.println("/RD {string currentfile exch readstring pop} executeonly def");
/*     */ 
/* 151 */     output.println("/ND {noaccess def} executeonly def");
/* 152 */     output.println("/NP {noaccess put} executeonly def");
/* 153 */     output.println("/BlueValues " + formatArray(font.getProperty("BlueValues"), true) + " ND");
/*     */ 
/* 155 */     output.println("/OtherBlues " + formatArray(font.getProperty("OtherBlues"), true) + " ND");
/*     */ 
/* 157 */     output.println("/BlueScale " + font.getProperty("BlueScale") + " def");
/* 158 */     output.println("/BlueShift " + font.getProperty("BlueShift") + " def");
/* 159 */     output.println("/BlueFuzz " + font.getProperty("BlueFuzz") + " def");
/* 160 */     output.println("/StdHW " + formatArray(font.getProperty("StdHW"), true) + " ND");
/*     */ 
/* 162 */     output.println("/StdVW " + formatArray(font.getProperty("StdVW"), true) + " ND");
/*     */ 
/* 164 */     output.println("/ForceBold " + font.getProperty("ForceBold") + " def");
/* 165 */     output.println("/MinFeature {16 16} def");
/* 166 */     output.println("/password 5839 def");
/*     */ 
/* 168 */     Collection mappings = font.getMappings();
/*     */ 
/* 170 */     output.println("2 index /CharStrings " + mappings.size() + " dict dup begin");
/*     */ 
/* 173 */     Type1CharStringFormatter formatter = new Type1CharStringFormatter();
/*     */ 
/* 175 */     for (CFFFont.Mapping mapping : mappings)
/*     */     {
/* 177 */       byte[] type1Bytes = formatter.format(mapping.toType1Sequence());
/*     */ 
/* 179 */       byte[] charstringBytes = Type1FontUtil.charstringEncrypt(type1Bytes, 4);
/*     */ 
/* 182 */       output.print("/" + mapping.getName() + " " + charstringBytes.length + " RD ");
/*     */ 
/* 184 */       output.write(charstringBytes);
/* 185 */       output.print(" ND");
/*     */ 
/* 187 */       output.println();
/*     */     }
/*     */ 
/* 190 */     output.println("end");
/* 191 */     output.println("end");
/*     */ 
/* 193 */     output.println("readonly put");
/* 194 */     output.println("noaccess put");
/* 195 */     output.println("dup /FontName get exch definefont pop");
/* 196 */     output.println("mark currentfile closefile");
/*     */   }
/*     */ 
/*     */   private static String formatArray(Object object, boolean executable)
/*     */   {
/* 201 */     return formatArray(object, null, executable);
/*     */   }
/*     */ 
/*     */   private static String formatArray(Object object, NumberFormat format, boolean executable)
/*     */   {
/* 206 */     StringBuffer sb = new StringBuffer();
/*     */ 
/* 208 */     sb.append(executable ? "{" : "[");
/*     */     String sep;
/*     */     Iterator i$;
/* 210 */     if ((object instanceof Collection))
/*     */     {
/* 212 */       sep = "";
/*     */ 
/* 214 */       Collection elements = (Collection)object;
/* 215 */       for (i$ = elements.iterator(); i$.hasNext(); ) { Object element = i$.next();
/*     */ 
/* 217 */         sb.append(sep).append(formatElement(element, format));
/*     */ 
/* 219 */         sep = " ";
/*     */       }
/*     */     }
/* 222 */     else if ((object instanceof Number))
/*     */     {
/* 224 */       sb.append(formatElement(object, format));
/*     */     }
/*     */ 
/* 227 */     sb.append(executable ? "}" : "]");
/*     */ 
/* 229 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   private static String formatElement(Object object, NumberFormat format)
/*     */   {
/* 234 */     if (format != null)
/*     */     {
/* 236 */       if (((object instanceof Double)) || ((object instanceof Float)))
/*     */       {
/* 238 */         Number number = (Number)object;
/* 239 */         return format.format(number.doubleValue());
/*     */       }
/* 241 */       if (((object instanceof Long)) || ((object instanceof Integer)))
/*     */       {
/* 243 */         Number number = (Number)object;
/* 244 */         return format.format(number.longValue());
/*     */       }
/*     */     }
/* 247 */     return String.valueOf(object);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.cff.Type1FontFormatter
 * JD-Core Version:    0.6.2
 */