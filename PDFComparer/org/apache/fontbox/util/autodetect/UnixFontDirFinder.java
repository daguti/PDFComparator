/*    */ package org.apache.fontbox.util.autodetect;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class UnixFontDirFinder extends NativeFontDirFinder
/*    */ {
/*    */   protected String[] getSearchableDirectories()
/*    */   {
/* 38 */     return new String[] { System.getProperty("user.home") + "/.fonts", "/usr/local/fonts", "/usr/local/share/fonts", "/usr/share/fonts", "/usr/X11R6/lib/X11/fonts" };
/*    */   }
/*    */ 
/*    */   public Map<String, String> getCommonTTFMapping()
/*    */   {
/* 51 */     HashMap map = new HashMap();
/* 52 */     map.put("TimesNewRoman,BoldItalic", "LiberationSerif-BoldItalic");
/* 53 */     map.put("TimesNewRoman,Bold", "LiberationSerif-Bold");
/* 54 */     map.put("TimesNewRoman,Italic", "LiberationSerif-Italic");
/* 55 */     map.put("TimesNewRoman", "LiberationSerif");
/*    */ 
/* 57 */     map.put("Arial,BoldItalic", "LiberationSans-BoldItalic");
/* 58 */     map.put("Arial,Italic", "LiberationSans-Italic");
/* 59 */     map.put("Arial,Bold", "LiberationSans-Bold");
/* 60 */     map.put("Arial", "LiberationSans");
/*    */ 
/* 62 */     map.put("Courier,BoldItalic", "LiberationMono-BoldItalic");
/* 63 */     map.put("Courier,Italic", "LiberationMono-Italic");
/* 64 */     map.put("Courier,Bold", "LiberationMono-Bold");
/* 65 */     map.put("Courier", "LiberationMono");
/*    */ 
/* 67 */     map.put("Symbol", "OpenSymbol");
/* 68 */     map.put("ZapfDingbats", "Dingbats");
/* 69 */     return Collections.unmodifiableMap(map);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.util.autodetect.UnixFontDirFinder
 * JD-Core Version:    0.6.2
 */