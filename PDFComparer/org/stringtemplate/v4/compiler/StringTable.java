/*    */ package org.stringtemplate.v4.compiler;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class StringTable
/*    */ {
/* 36 */   protected LinkedHashMap<String, Integer> table = new LinkedHashMap();
/* 37 */   protected int i = -1;
/*    */ 
/*    */   public int add(String s) {
/* 40 */     Integer I = (Integer)this.table.get(s);
/* 41 */     if (I != null) return I.intValue();
/* 42 */     this.i += 1;
/* 43 */     this.table.put(s, Integer.valueOf(this.i));
/* 44 */     return this.i;
/*    */   }
/*    */ 
/*    */   public String[] toArray() {
/* 48 */     String[] a = new String[this.table.size()];
/* 49 */     int i = 0;
/*    */     String s;
/* 50 */     for (Iterator i$ = this.table.keySet().iterator(); i$.hasNext(); a[(i++)] = s) s = (String)i$.next();
/* 51 */     return a;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.compiler.StringTable
 * JD-Core Version:    0.6.2
 */