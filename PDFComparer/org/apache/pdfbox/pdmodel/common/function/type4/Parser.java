/*     */ package org.apache.pdfbox.pdmodel.common.function.type4;
/*     */ 
/*     */ public class Parser
/*     */ {
/*     */   public static void parse(CharSequence input, SyntaxHandler handler)
/*     */   {
/*  47 */     Tokenizer tokenizer = new Tokenizer(input, handler, null);
/*  48 */     tokenizer.tokenize();
/*     */   }
/*     */ 
/*     */   private static class Tokenizer
/*     */   {
/*     */     private static final char NUL = '\000';
/*     */     private static final char EOT = '\004';
/*     */     private static final char TAB = '\t';
/*     */     private static final char FF = '\f';
/*     */     private static final char CR = '\r';
/*     */     private static final char LF = '\n';
/*     */     private static final char SPACE = ' ';
/*     */     private CharSequence input;
/*     */     private int index;
/*     */     private Parser.SyntaxHandler handler;
/* 127 */     private Parser.State state = Parser.State.WHITESPACE;
/* 128 */     private StringBuilder buffer = new StringBuilder();
/*     */ 
/*     */     private Tokenizer(CharSequence text, Parser.SyntaxHandler syntaxHandler)
/*     */     {
/* 132 */       this.input = text;
/* 133 */       this.handler = syntaxHandler;
/*     */     }
/*     */ 
/*     */     private boolean hasMore()
/*     */     {
/* 138 */       return this.index < this.input.length();
/*     */     }
/*     */ 
/*     */     private char currentChar()
/*     */     {
/* 143 */       return this.input.charAt(this.index);
/*     */     }
/*     */ 
/*     */     private char nextChar()
/*     */     {
/* 148 */       this.index += 1;
/* 149 */       if (!hasMore())
/*     */       {
/* 151 */         return '\004';
/*     */       }
/*     */ 
/* 155 */       return currentChar();
/*     */     }
/*     */ 
/*     */     private char peek()
/*     */     {
/* 161 */       if (this.index < this.input.length() - 1)
/*     */       {
/* 163 */         return this.input.charAt(this.index + 1);
/*     */       }
/*     */ 
/* 167 */       return '\004';
/*     */     }
/*     */ 
/*     */     private Parser.State nextState()
/*     */     {
/* 173 */       char ch = currentChar();
/* 174 */       switch (ch)
/*     */       {
/*     */       case '\n':
/*     */       case '\f':
/*     */       case '\r':
/* 179 */         this.state = Parser.State.NEWLINE;
/* 180 */         break;
/*     */       case '\000':
/*     */       case '\t':
/*     */       case ' ':
/* 184 */         this.state = Parser.State.WHITESPACE;
/* 185 */         break;
/*     */       case '%':
/* 187 */         this.state = Parser.State.COMMENT;
/* 188 */         break;
/*     */       default:
/* 190 */         this.state = Parser.State.TOKEN;
/*     */       }
/* 192 */       return this.state;
/*     */     }
/*     */ 
/*     */     private void tokenize()
/*     */     {
/* 197 */       while (hasMore())
/*     */       {
/* 199 */         this.buffer.setLength(0);
/* 200 */         nextState();
/* 201 */         switch (Parser.1.$SwitchMap$org$apache$pdfbox$pdmodel$common$function$type4$Parser$State[this.state.ordinal()])
/*     */         {
/*     */         case 1:
/* 204 */           scanNewLine();
/* 205 */           break;
/*     */         case 2:
/* 207 */           scanWhitespace();
/* 208 */           break;
/*     */         case 3:
/* 210 */           scanComment();
/* 211 */           break;
/*     */         }
/* 213 */         scanToken();
/*     */       }
/*     */     }
/*     */ 
/*     */     private void scanNewLine()
/*     */     {
/* 220 */       assert (this.state == Parser.State.NEWLINE);
/* 221 */       char ch = currentChar();
/* 222 */       this.buffer.append(ch);
/* 223 */       if (ch == '\r')
/*     */       {
/* 225 */         if (peek() == '\n')
/*     */         {
/* 228 */           this.buffer.append(nextChar());
/*     */         }
/*     */       }
/* 231 */       this.handler.newLine(this.buffer);
/* 232 */       nextChar();
/*     */     }
/*     */ 
/*     */     private void scanWhitespace()
/*     */     {
/* 237 */       assert (this.state == Parser.State.WHITESPACE);
/* 238 */       this.buffer.append(currentChar());
/*     */ 
/* 240 */       while (hasMore())
/*     */       {
/* 242 */         char ch = nextChar();
/* 243 */         switch (ch)
/*     */         {
/*     */         case '\000':
/*     */         case '\t':
/*     */         case ' ':
/* 248 */           this.buffer.append(ch);
/* 249 */           break;
/*     */         default:
/* 251 */           break label102;
/*     */         }
/*     */       }
/* 254 */       label102: this.handler.whitespace(this.buffer);
/*     */     }
/*     */ 
/*     */     private void scanComment()
/*     */     {
/* 259 */       assert (this.state == Parser.State.COMMENT);
/* 260 */       this.buffer.append(currentChar());
/*     */ 
/* 262 */       while (hasMore())
/*     */       {
/* 264 */         char ch = nextChar();
/* 265 */         switch (ch)
/*     */         {
/*     */         case '\n':
/*     */         case '\f':
/*     */         case '\r':
/* 270 */           break;
/*     */         case '\013':
/*     */         default:
/* 272 */           this.buffer.append(ch);
/*     */         }
/*     */       }
/*     */ 
/* 276 */       this.handler.comment(this.buffer);
/*     */     }
/*     */ 
/*     */     private void scanToken()
/*     */     {
/* 281 */       assert (this.state == Parser.State.TOKEN);
/* 282 */       char ch = currentChar();
/* 283 */       this.buffer.append(ch);
/* 284 */       switch (ch)
/*     */       {
/*     */       case '{':
/*     */       case '}':
/* 288 */         this.handler.token(this.buffer);
/* 289 */         nextChar();
/* 290 */         return;
/*     */       }
/*     */ 
/* 295 */       while (hasMore())
/*     */       {
/* 297 */         ch = nextChar();
/* 298 */         switch (ch)
/*     */         {
/*     */         case '\000':
/*     */         case '\004':
/*     */         case '\t':
/*     */         case '\n':
/*     */         case '\f':
/*     */         case '\r':
/*     */         case ' ':
/*     */         case '{':
/*     */         case '}':
/* 309 */           break;
/*     */         default:
/* 311 */           this.buffer.append(ch);
/*     */         }
/*     */       }
/*     */ 
/* 315 */       this.handler.token(this.buffer);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static abstract class AbstractSyntaxHandler
/*     */     implements Parser.SyntaxHandler
/*     */   {
/*     */     public void comment(CharSequence text)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void newLine(CharSequence text)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void whitespace(CharSequence text)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public static abstract interface SyntaxHandler
/*     */   {
/*     */     public abstract void newLine(CharSequence paramCharSequence);
/*     */ 
/*     */     public abstract void whitespace(CharSequence paramCharSequence);
/*     */ 
/*     */     public abstract void token(CharSequence paramCharSequence);
/*     */ 
/*     */     public abstract void comment(CharSequence paramCharSequence);
/*     */   }
/*     */ 
/*     */   private static enum State
/*     */   {
/*  31 */     NEWLINE, WHITESPACE, COMMENT, TOKEN;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.function.type4.Parser
 * JD-Core Version:    0.6.2
 */