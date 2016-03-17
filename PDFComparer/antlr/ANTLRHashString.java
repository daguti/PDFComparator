/*     */ package antlr;
/*     */ 
/*     */ public class ANTLRHashString
/*     */ {
/*     */   private String s;
/*     */   private char[] buf;
/*     */   private int len;
/*     */   private CharScanner lexer;
/*     */   private static final int prime = 151;
/*     */ 
/*     */   public ANTLRHashString(char[] paramArrayOfChar, int paramInt, CharScanner paramCharScanner)
/*     */   {
/*  24 */     this.lexer = paramCharScanner;
/*  25 */     setBuffer(paramArrayOfChar, paramInt);
/*     */   }
/*     */ 
/*     */   public ANTLRHashString(CharScanner paramCharScanner)
/*     */   {
/*  30 */     this.lexer = paramCharScanner;
/*     */   }
/*     */ 
/*     */   public ANTLRHashString(String paramString, CharScanner paramCharScanner) {
/*  34 */     this.lexer = paramCharScanner;
/*  35 */     setString(paramString);
/*     */   }
/*     */ 
/*     */   private final char charAt(int paramInt) {
/*  39 */     return this.s != null ? this.s.charAt(paramInt) : this.buf[paramInt];
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/*  44 */     if ((!(paramObject instanceof ANTLRHashString)) && (!(paramObject instanceof String)))
/*  45 */       return false;
/*     */     ANTLRHashString localANTLRHashString;
/*  49 */     if ((paramObject instanceof String)) {
/*  50 */       localANTLRHashString = new ANTLRHashString((String)paramObject, this.lexer);
/*     */     }
/*     */     else {
/*  53 */       localANTLRHashString = (ANTLRHashString)paramObject;
/*     */     }
/*  55 */     int i = length();
/*  56 */     if (localANTLRHashString.length() != i) {
/*  57 */       return false;
/*     */     }
/*  59 */     if (this.lexer.getCaseSensitiveLiterals()) {
/*  60 */       for (j = 0; j < i; j++) {
/*  61 */         if (charAt(j) != localANTLRHashString.charAt(j)) {
/*  62 */           return false;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  67 */     for (int j = 0; j < i; j++) {
/*  68 */       if (this.lexer.toLower(charAt(j)) != this.lexer.toLower(localANTLRHashString.charAt(j))) {
/*  69 */         return false;
/*     */       }
/*     */     }
/*     */ 
/*  73 */     return true;
/*     */   }
/*     */ 
/*     */   public int hashCode() {
/*  77 */     int i = 0;
/*  78 */     int j = length();
/*     */ 
/*  80 */     if (this.lexer.getCaseSensitiveLiterals()) {
/*  81 */       for (k = 0; k < j; k++) {
/*  82 */         i = i * 151 + charAt(k);
/*     */       }
/*     */     }
/*     */ 
/*  86 */     for (int k = 0; k < j; k++) {
/*  87 */       i = i * 151 + this.lexer.toLower(charAt(k));
/*     */     }
/*     */ 
/*  90 */     return i;
/*     */   }
/*     */ 
/*     */   private final int length() {
/*  94 */     return this.s != null ? this.s.length() : this.len;
/*     */   }
/*     */ 
/*     */   public void setBuffer(char[] paramArrayOfChar, int paramInt) {
/*  98 */     this.buf = paramArrayOfChar;
/*  99 */     this.len = paramInt;
/* 100 */     this.s = null;
/*     */   }
/*     */ 
/*     */   public void setString(String paramString) {
/* 104 */     this.s = paramString;
/* 105 */     this.buf = null;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.ANTLRHashString
 * JD-Core Version:    0.6.2
 */