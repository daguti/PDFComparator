/*     */ package org.antlr.runtime;
/*     */ 
/*     */ public class DFA
/*     */ {
/*     */   protected short[] eot;
/*     */   protected short[] eof;
/*     */   protected char[] min;
/*     */   protected char[] max;
/*     */   protected short[] accept;
/*     */   protected short[] special;
/*     */   protected short[][] transition;
/*     */   protected int decisionNumber;
/*     */   protected BaseRecognizer recognizer;
/*     */   public static final boolean debug = false;
/*     */ 
/*     */   public int predict(IntStream input)
/*     */     throws RecognitionException
/*     */   {
/*  68 */     int mark = input.mark();
/*  69 */     int s = 0;
/*     */     try {
/*     */       char c;
/*     */       int snext;
/*     */       while (true) { int specialState = this.special[s];
/*     */         int i;
/*  75 */         if (specialState >= 0)
/*     */         {
/*  80 */           s = specialStateTransition(specialState, input);
/*     */ 
/*  85 */           if (s == -1) {
/*  86 */             noViableAlt(s, input);
/*  87 */             return 0;
/*     */           }
/*  89 */           input.consume();
/*     */         }
/*     */         else {
/*  92 */           if (this.accept[s] >= 1)
/*     */           {
/*  94 */             return this.accept[s];
/*     */           }
/*     */ 
/*  97 */           c = (char)input.LA(1);
/*  98 */           if ((c >= this.min[s]) && (c <= this.max[s])) {
/*  99 */             snext = this.transition[s][(c - this.min[s])];
/* 100 */             if (snext < 0)
/*     */             {
/* 105 */               if (this.eot[s] >= 0)
/*     */               {
/* 107 */                 s = this.eot[s];
/* 108 */                 input.consume();
/*     */               }
/*     */               else
/*     */               {
/* 116 */                 noViableAlt(s, input);
/* 117 */                 return 0;
/*     */               }
/*     */             } else { s = snext;
/* 120 */               input.consume(); }
/*     */           }
/*     */           else {
/* 123 */             if (this.eot[s] < 0)
/*     */               break;
/* 125 */             s = this.eot[s];
/* 126 */             input.consume();
/*     */           }
/*     */         } }
/* 129 */       if ((c == 65535) && (this.eof[s] >= 0))
/*     */       {
/* 131 */         return this.accept[this.eof[s]];
/*     */       }
/*     */ 
/* 144 */       noViableAlt(s, input);
/* 145 */       return 0;
/*     */     }
/*     */     finally
/*     */     {
/* 149 */       input.rewind(mark);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void noViableAlt(int s, IntStream input) throws NoViableAltException {
/* 154 */     if (this.recognizer.state.backtracking > 0) {
/* 155 */       this.recognizer.state.failed = true;
/* 156 */       return;
/*     */     }
/* 158 */     NoViableAltException nvae = new NoViableAltException(getDescription(), this.decisionNumber, s, input);
/*     */ 
/* 163 */     error(nvae);
/* 164 */     throw nvae;
/*     */   }
/*     */ 
/*     */   protected void error(NoViableAltException nvae)
/*     */   {
/*     */   }
/*     */ 
/*     */   public int specialStateTransition(int s, IntStream input) throws NoViableAltException
/*     */   {
/* 173 */     return -1;
/*     */   }
/*     */ 
/*     */   public String getDescription() {
/* 177 */     return "n/a";
/*     */   }
/*     */ 
/*     */   public static short[] unpackEncodedString(String encodedString)
/*     */   {
/* 187 */     int size = 0;
/* 188 */     for (int i = 0; i < encodedString.length(); i += 2) {
/* 189 */       size += encodedString.charAt(i);
/*     */     }
/* 191 */     short[] data = new short[size];
/* 192 */     int di = 0;
/* 193 */     for (int i = 0; i < encodedString.length(); i += 2) {
/* 194 */       char n = encodedString.charAt(i);
/* 195 */       char v = encodedString.charAt(i + 1);
/*     */ 
/* 197 */       for (int j = 1; j <= n; j++) {
/* 198 */         data[(di++)] = ((short)v);
/*     */       }
/*     */     }
/* 201 */     return data;
/*     */   }
/*     */ 
/*     */   public static char[] unpackEncodedStringToUnsignedChars(String encodedString)
/*     */   {
/* 207 */     int size = 0;
/* 208 */     for (int i = 0; i < encodedString.length(); i += 2) {
/* 209 */       size += encodedString.charAt(i);
/*     */     }
/* 211 */     char[] data = new char[size];
/* 212 */     int di = 0;
/* 213 */     for (int i = 0; i < encodedString.length(); i += 2) {
/* 214 */       char n = encodedString.charAt(i);
/* 215 */       char v = encodedString.charAt(i + 1);
/*     */ 
/* 217 */       for (int j = 1; j <= n; j++) {
/* 218 */         data[(di++)] = v;
/*     */       }
/*     */     }
/* 221 */     return data;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.DFA
 * JD-Core Version:    0.6.2
 */