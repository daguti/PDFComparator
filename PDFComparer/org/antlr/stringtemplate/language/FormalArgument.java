/*    */ package org.antlr.stringtemplate.language;
/*    */ 
/*    */ import java.util.LinkedHashMap;
/*    */ import org.antlr.stringtemplate.StringTemplate;
/*    */ 
/*    */ public class FormalArgument
/*    */ {
/*    */   public static final int OPTIONAL = 1;
/*    */   public static final int REQUIRED = 2;
/*    */   public static final int ZERO_OR_MORE = 4;
/*    */   public static final int ONE_OR_MORE = 8;
/* 32 */   public static final String[] suffixes = { null, "?", "", null, "*", null, null, null, "+" };
/*    */ 
/* 49 */   public static final LinkedHashMap UNKNOWN = new LinkedHashMap();
/*    */   public String name;
/*    */   public StringTemplate defaultValueST;
/*    */ 
/*    */   public FormalArgument(String name)
/*    */   {
/* 58 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public FormalArgument(String name, StringTemplate defaultValueST) {
/* 62 */     this.name = name;
/* 63 */     this.defaultValueST = defaultValueST;
/*    */   }
/*    */ 
/*    */   public static String getCardinalityName(int cardinality) {
/* 67 */     switch (cardinality) { case 1:
/* 68 */       return "optional";
/*    */     case 2:
/* 69 */       return "exactly one";
/*    */     case 4:
/* 70 */       return "zero-or-more";
/*    */     case 8:
/* 71 */       return "one-or-more";
/*    */     case 3:
/*    */     case 5:
/*    */     case 6:
/* 72 */     case 7: } return "unknown";
/*    */   }
/*    */ 
/*    */   public boolean equals(Object o)
/*    */   {
/* 77 */     if ((o == null) || (!(o instanceof FormalArgument))) {
/* 78 */       return false;
/*    */     }
/* 80 */     FormalArgument other = (FormalArgument)o;
/* 81 */     if (!this.name.equals(other.name)) {
/* 82 */       return false;
/*    */     }
/*    */ 
/* 85 */     if (((this.defaultValueST != null) && (other.defaultValueST == null)) || ((this.defaultValueST == null) && (other.defaultValueST != null)))
/*    */     {
/* 87 */       return false;
/*    */     }
/* 89 */     return true;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 93 */     if (this.defaultValueST != null) {
/* 94 */       return this.name + "=" + this.defaultValueST;
/*    */     }
/* 96 */     return this.name;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.stringtemplate.language.FormalArgument
 * JD-Core Version:    0.6.2
 */