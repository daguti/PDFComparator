/*    */ package org.antlr.stringtemplate.language;
/*    */ 
/*    */ import java.util.AbstractList;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ public class Cat extends AbstractList
/*    */ {
/*    */   protected List elements;
/*    */ 
/*    */   public Iterator iterator()
/*    */   {
/* 13 */     return super.iterator();
/*    */   }
/*    */ 
/*    */   public Object get(int index) {
/* 17 */     return this.elements.get(index);
/*    */   }
/*    */ 
/*    */   public int size() {
/* 21 */     return this.elements.size();
/*    */   }
/*    */ 
/*    */   public Cat(List attributes) {
/* 25 */     this.elements = new ArrayList();
/* 26 */     for (int i = 0; i < attributes.size(); i++) {
/* 27 */       Object attribute = attributes.get(i);
/* 28 */       attribute = ASTExpr.convertAnythingIteratableToIterator(attribute);
/* 29 */       if ((attribute instanceof Iterator)) {
/* 30 */         Iterator it = (Iterator)attribute;
/* 31 */         while (it.hasNext()) {
/* 32 */           Object o = it.next();
/* 33 */           this.elements.add(o);
/*    */         }
/*    */       }
/*    */       else {
/* 37 */         this.elements.add(attribute);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 43 */     StringBuffer buf = new StringBuffer();
/* 44 */     for (int i = 0; i < this.elements.size(); i++) {
/* 45 */       Object o = this.elements.get(i);
/* 46 */       buf.append(o);
/*    */     }
/* 48 */     return buf.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.stringtemplate.language.Cat
 * JD-Core Version:    0.6.2
 */