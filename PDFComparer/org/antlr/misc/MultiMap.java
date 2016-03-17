/*    */ package org.antlr.misc;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.List;
/*    */ 
/*    */ public class MultiMap<K, V> extends LinkedHashMap<K, List<V>>
/*    */ {
/*    */   public void map(K key, V value)
/*    */   {
/* 37 */     List elementsForKey = (List)get(key);
/* 38 */     if (elementsForKey == null) {
/* 39 */       elementsForKey = new ArrayList();
/* 40 */       super.put(key, elementsForKey);
/*    */     }
/* 42 */     elementsForKey.add(value);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.misc.MultiMap
 * JD-Core Version:    0.6.2
 */