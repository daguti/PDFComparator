/*    */ package org.apache.fontbox.afm;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class Composite
/*    */ {
/*    */   private String name;
/* 31 */   private List<CompositePart> parts = new ArrayList();
/*    */ 
/*    */   public String getName()
/*    */   {
/* 38 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String nameValue)
/*    */   {
/* 46 */     this.name = nameValue;
/*    */   }
/*    */ 
/*    */   public void addPart(CompositePart part)
/*    */   {
/* 56 */     this.parts.add(part);
/*    */   }
/*    */ 
/*    */   public List<CompositePart> getParts()
/*    */   {
/* 64 */     return this.parts;
/*    */   }
/*    */ 
/*    */   public void setParts(List<CompositePart> partsList)
/*    */   {
/* 72 */     this.parts = partsList;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.afm.Composite
 * JD-Core Version:    0.6.2
 */