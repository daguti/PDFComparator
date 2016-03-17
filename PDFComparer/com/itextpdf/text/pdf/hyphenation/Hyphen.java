/*    */ package com.itextpdf.text.pdf.hyphenation;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Hyphen
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -7666138517324763063L;
/*    */   public String preBreak;
/*    */   public String noBreak;
/*    */   public String postBreak;
/*    */ 
/*    */   Hyphen(String pre, String no, String post)
/*    */   {
/* 41 */     this.preBreak = pre;
/* 42 */     this.noBreak = no;
/* 43 */     this.postBreak = post;
/*    */   }
/*    */ 
/*    */   Hyphen(String pre) {
/* 47 */     this.preBreak = pre;
/* 48 */     this.noBreak = null;
/* 49 */     this.postBreak = null;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 53 */     if ((this.noBreak == null) && (this.postBreak == null) && (this.preBreak != null) && (this.preBreak.equals("-")))
/*    */     {
/* 57 */       return "-";
/*    */     }
/* 59 */     StringBuffer res = new StringBuffer("{");
/* 60 */     res.append(this.preBreak);
/* 61 */     res.append("}{");
/* 62 */     res.append(this.postBreak);
/* 63 */     res.append("}{");
/* 64 */     res.append(this.noBreak);
/* 65 */     res.append('}');
/* 66 */     return res.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.hyphenation.Hyphen
 * JD-Core Version:    0.6.2
 */