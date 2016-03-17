/*    */ package org.apache.fontbox.util.autodetect;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class MacFontDirFinder extends NativeFontDirFinder
/*    */ {
/*    */   protected String[] getSearchableDirectories()
/*    */   {
/* 38 */     return new String[] { System.getProperty("user.home") + "/Library/Fonts/", "/Library/Fonts/", "/System/Library/Fonts/", "/Network/Library/Fonts/" };
/*    */   }
/*    */ 
/*    */   public Map<String, String> getCommonTTFMapping()
/*    */   {
/* 50 */     HashMap map = new HashMap();
/* 51 */     map.put("Arial", "Arial");
/* 52 */     map.put("Arial,Bold", "ArialBold");
/* 53 */     map.put("Arial,Italic", "ArialItalic");
/* 54 */     map.put("Arial,BoldItalic", "ArialBoldItalic");
/*    */ 
/* 56 */     map.put("TimesNewRoman", "timesnewromanpsmt");
/* 57 */     map.put("TimesNewRoman,Bold", "timesnewromanpsmtbold");
/* 58 */     map.put("TimesNewRoman,BoldItalic", "timesnewromanpsmtbolditalic");
/* 59 */     map.put("TimesNewRoman,Italic", "timesnewromanpsmtitalic");
/*    */ 
/* 61 */     map.put("Courier", "couriernewpsmt");
/* 62 */     map.put("Courier,Bold", "couriernewpsmtbold");
/* 63 */     map.put("Courier,Italic", "couriernewpsmtitalic");
/* 64 */     map.put("Courier,BoldItalic", "couriernewpsmtbolditalic");
/*    */ 
/* 66 */     map.put("Symbol", "Symbol");
/* 67 */     map.put("ZapfDingbats", "ZapfDingbats");
/*    */ 
/* 69 */     return Collections.unmodifiableMap(map);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.util.autodetect.MacFontDirFinder
 * JD-Core Version:    0.6.2
 */