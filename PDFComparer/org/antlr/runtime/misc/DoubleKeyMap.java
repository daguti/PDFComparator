/*    */ package org.antlr.runtime.misc;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.HashSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class DoubleKeyMap<Key1, Key2, Value>
/*    */ {
/* 10 */   Map<Key1, Map<Key2, Value>> data = new LinkedHashMap();
/*    */ 
/*    */   public Value put(Key1 k1, Key2 k2, Value v) {
/* 13 */     Map data2 = (Map)this.data.get(k1);
/* 14 */     Object prev = null;
/* 15 */     if (data2 == null) {
/* 16 */       data2 = new LinkedHashMap();
/* 17 */       this.data.put(k1, data2);
/*    */     }
/*    */     else {
/* 20 */       prev = data2.get(k2);
/*    */     }
/* 22 */     data2.put(k2, v);
/* 23 */     return prev;
/*    */   }
/*    */ 
/*    */   public Value get(Key1 k1, Key2 k2) {
/* 27 */     Map data2 = (Map)this.data.get(k1);
/* 28 */     if (data2 == null) return null;
/* 29 */     return data2.get(k2);
/*    */   }
/*    */   public Map<Key2, Value> get(Key1 k1) {
/* 32 */     return (Map)this.data.get(k1);
/*    */   }
/*    */ 
/*    */   public Collection<Value> values(Key1 k1) {
/* 36 */     Map data2 = (Map)this.data.get(k1);
/* 37 */     if (data2 == null) return null;
/* 38 */     return data2.values();
/*    */   }
/*    */ 
/*    */   public Set<Key1> keySet()
/*    */   {
/* 43 */     return this.data.keySet();
/*    */   }
/*    */ 
/*    */   public Set<Key2> keySet(Key1 k1)
/*    */   {
/* 48 */     Map data2 = (Map)this.data.get(k1);
/* 49 */     if (data2 == null) return null;
/* 50 */     return data2.keySet();
/*    */   }
/*    */ 
/*    */   public Collection<Value> values() {
/* 54 */     Set s = new HashSet();
/* 55 */     for (Iterator i$ = this.data.values().iterator(); i$.hasNext(); ) { Map k2 = (Map)i$.next();
/* 56 */       for (i$ = k2.values().iterator(); i$.hasNext(); ) { Object v = i$.next();
/* 57 */         s.add(v);
/*    */       }
/*    */     }
/*    */     Iterator i$;
/* 60 */     return s;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.misc.DoubleKeyMap
 * JD-Core Version:    0.6.2
 */