/*     */ package org.apache.pdfbox.encoding.conversion;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ 
/*     */ class CJKEncodings
/*     */ {
/*  31 */   private static HashMap charsetMapping = new HashMap();
/*     */ 
/*     */   public static final String getCharset(String encoding)
/*     */   {
/* 185 */     if (encoding.startsWith("COSName"))
/*     */     {
/* 187 */       encoding = encoding.substring(8, encoding.length() - 1);
/*     */     }
/* 189 */     return (String)charsetMapping.get(encoding);
/*     */   }
/*     */ 
/*     */   public static final Iterator getEncodingIterator()
/*     */   {
/* 197 */     return charsetMapping.keySet().iterator();
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  41 */     charsetMapping.put("GB-EUC-H", "GB2312");
/*     */ 
/*  43 */     charsetMapping.put("GB-EUC-V", "GB2312");
/*     */ 
/*  45 */     charsetMapping.put("GBpc-EUC-H", "GB2312");
/*     */ 
/*  47 */     charsetMapping.put("GBpc-EUC-V", "GB2312");
/*     */ 
/*  49 */     charsetMapping.put("GBK-EUC-H", "GBK");
/*     */ 
/*  51 */     charsetMapping.put("GBK-EUC-V", "GBK");
/*     */ 
/*  54 */     charsetMapping.put("GBKp-EUC-H", "GBK");
/*     */ 
/*  56 */     charsetMapping.put("GBKp-EUC-V", "GBK");
/*     */ 
/*  58 */     charsetMapping.put("GBK2K-H", "GB18030");
/*     */ 
/*  60 */     charsetMapping.put("GBK2K-V", "GB18030");
/*     */ 
/*  62 */     charsetMapping.put("UniGB-UCS2-H", "ISO-10646-UCS-2");
/*     */ 
/*  64 */     charsetMapping.put("UniGB-UCS2-V", "ISO-10646-UCS-2");
/*     */ 
/*  67 */     charsetMapping.put("UniGB-UTF16-H", "UTF-16BE");
/*     */ 
/*  69 */     charsetMapping.put("UniGB-UTF16-V", "UTF-16BE");
/*     */ 
/*  73 */     charsetMapping.put("B5pc-H", "BIG5");
/*     */ 
/*  75 */     charsetMapping.put("B5pc-V", "BIG5");
/*     */ 
/*  77 */     charsetMapping.put("HKscs-B5-H", "Big5-HKSCS");
/*     */ 
/*  79 */     charsetMapping.put("HKscs-B5-V", "Big5-HKSCS");
/*     */ 
/*  81 */     charsetMapping.put("ETen-B5-H", "BIG5");
/*     */ 
/*  83 */     charsetMapping.put("ETen-B5-V", "BIG5");
/*     */ 
/*  85 */     charsetMapping.put("ETenms-B5-H", "BIG5");
/*     */ 
/*  87 */     charsetMapping.put("ETenms-B5-V", "BIG5");
/*     */ 
/*  89 */     charsetMapping.put("CNS-EUC-H", "HZ");
/*     */ 
/*  91 */     charsetMapping.put("CNS-EUC-V", "HZ");
/*     */ 
/*  93 */     charsetMapping.put("UniCNS-UCS2-H", "ISO-10646-UCS-2");
/*     */ 
/*  95 */     charsetMapping.put("UniCNS-UCS2-V", "ISO-10646-UCS-2");
/*     */ 
/*  99 */     charsetMapping.put("UniCNS-UTF16-H", "UTF-16BE");
/*     */ 
/* 101 */     charsetMapping.put("UniCNS-UTF16-V", "UTF-16BE");
/*     */ 
/* 105 */     charsetMapping.put("83pv-RKSJ-H", "JIS");
/*     */ 
/* 107 */     charsetMapping.put("90ms-RKSJ-H", "JIS");
/*     */ 
/* 109 */     charsetMapping.put("90ms-RKSJ-V", "JIS");
/*     */ 
/* 111 */     charsetMapping.put("90msp-RKSJ-H", "JIS");
/*     */ 
/* 113 */     charsetMapping.put("90msp-RKSJ-V", "JIS");
/*     */ 
/* 115 */     charsetMapping.put("90pv-RKSJ-H", "JIS");
/*     */ 
/* 117 */     charsetMapping.put("Add-RKSJ-H", "JIS");
/*     */ 
/* 119 */     charsetMapping.put("Add-RKSJ-V", "JIS");
/*     */ 
/* 121 */     charsetMapping.put("EUC-H", "JIS");
/*     */ 
/* 123 */     charsetMapping.put("EUC-V", "JIS");
/*     */ 
/* 125 */     charsetMapping.put("Ext-RKSJ-H", "JIS");
/*     */ 
/* 127 */     charsetMapping.put("Ext-RKSJ-V", "JIS");
/*     */ 
/* 129 */     charsetMapping.put("H", "JIS");
/*     */ 
/* 131 */     charsetMapping.put("V", "JIS");
/*     */ 
/* 133 */     charsetMapping.put("UniJIS-UCS2-H", "ISO-10646-UCS-2");
/*     */ 
/* 135 */     charsetMapping.put("UniJIS-UCS2-V", "ISO-10646-UCS-2");
/*     */ 
/* 137 */     charsetMapping.put("UniJIS-UCS2-HW-H", "ISO-10646-UCS-2");
/*     */ 
/* 139 */     charsetMapping.put("UniJIS-UCS2-HW-V", "ISO-10646-UCS-2");
/*     */ 
/* 142 */     charsetMapping.put("UniJIS-UTF16-H", "UTF-16BE");
/*     */ 
/* 144 */     charsetMapping.put("UniJIS-UTF16-V", "UTF-16BE");
/*     */ 
/* 146 */     charsetMapping.put("Identity-H", "JIS");
/*     */ 
/* 148 */     charsetMapping.put("Identity-V", "JIS");
/*     */ 
/* 152 */     charsetMapping.put("KSC-EUC-H", "KSC");
/*     */ 
/* 154 */     charsetMapping.put("KSC-EUC-V", "KSC");
/*     */ 
/* 157 */     charsetMapping.put("KSCms-UHC-H", "KSC");
/*     */ 
/* 159 */     charsetMapping.put("KSCms-UHC-V", "KSC");
/*     */ 
/* 161 */     charsetMapping.put("KSCms-UHC-HW-H", "KSC");
/*     */ 
/* 163 */     charsetMapping.put("KSCms-UHC-HW-V", "KSC");
/*     */ 
/* 165 */     charsetMapping.put("KSCpc-EUC-H", "KSC");
/*     */ 
/* 167 */     charsetMapping.put("UniKS-UCS2-H", "ISO-10646-UCS-2");
/*     */ 
/* 169 */     charsetMapping.put("UniKS-UCS2-V", "ISO-10646-UCS-2");
/*     */ 
/* 171 */     charsetMapping.put("UniKS-UTF16-H", "UTF-16BE");
/*     */ 
/* 173 */     charsetMapping.put("UniKS-UTF16-V", "UTF-16BE");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.encoding.conversion.CJKEncodings
 * JD-Core Version:    0.6.2
 */