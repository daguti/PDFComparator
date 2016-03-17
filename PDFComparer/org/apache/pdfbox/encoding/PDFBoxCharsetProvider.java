/*    */ package org.apache.pdfbox.encoding;
/*    */ 
/*    */ import java.nio.charset.Charset;
/*    */ import java.nio.charset.spi.CharsetProvider;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.HashSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class PDFBoxCharsetProvider extends CharsetProvider
/*    */ {
/* 33 */   private final Set<Charset> available = new HashSet();
/* 34 */   private final Map<String, Charset> map = new HashMap();
/*    */ 
/*    */   public PDFBoxCharsetProvider()
/*    */   {
/* 41 */     this.available.add(PDFDocEncodingCharset.INSTANCE);
/* 42 */     for (Iterator i$ = this.available.iterator(); i$.hasNext(); ) { cs = (Charset)i$.next();
/*    */ 
/* 44 */       this.map.put(cs.name(), cs);
/* 45 */       for (String alias : cs.aliases())
/*    */       {
/* 47 */         this.map.put(alias, cs);
/*    */       }
/*    */     }
/*    */     Charset cs;
/*    */   }
/*    */ 
/*    */   public Iterator<Charset> charsets()
/*    */   {
/* 56 */     return Collections.unmodifiableSet(this.available).iterator();
/*    */   }
/*    */ 
/*    */   public Charset charsetForName(String charsetName)
/*    */   {
/* 63 */     return (Charset)this.map.get(charsetName);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.encoding.PDFBoxCharsetProvider
 * JD-Core Version:    0.6.2
 */