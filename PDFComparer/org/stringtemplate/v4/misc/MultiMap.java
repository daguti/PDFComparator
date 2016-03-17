/*    */ package org.stringtemplate.v4.misc;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.List;
/*    */ 
/*    */ public class MultiMap<K, V> extends LinkedHashMap<K, List<V>>
/*    */ {
/*    */   public void map(K key, V value)
/*    */   {
/* 35 */     List elementsForKey = (List)get(key);
/* 36 */     if (elementsForKey == null) {
/* 37 */       elementsForKey = new ArrayList();
/* 38 */       super.put(key, elementsForKey);
/*    */     }
/* 40 */     elementsForKey.add(value);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.misc.MultiMap
 * JD-Core Version:    0.6.2
 */