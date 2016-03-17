/*     */ package org.antlr.codegen;
/*     */ 
/*     */ import antlr.Token;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.antlr.tool.Grammar;
/*     */ 
/*     */ public class PythonTarget extends Target
/*     */ {
/*     */   public String getTokenTypeAsTargetLabel(CodeGenerator generator, int ttype)
/*     */   {
/*  49 */     if ((ttype >= 0) && (ttype <= 3)) {
/*  50 */       return String.valueOf(ttype);
/*     */     }
/*     */ 
/*  53 */     String name = generator.grammar.getTokenDisplayName(ttype);
/*     */ 
/*  56 */     if (name.charAt(0) == '\'') {
/*  57 */       return String.valueOf(ttype);
/*     */     }
/*     */ 
/*  60 */     return name;
/*     */   }
/*     */ 
/*     */   public String getTargetCharLiteralFromANTLRCharLiteral(CodeGenerator generator, String literal)
/*     */   {
/*  66 */     int c = Grammar.getCharValueFromGrammarCharLiteral(literal);
/*  67 */     return String.valueOf(c);
/*     */   }
/*     */ 
/*     */   private List splitLines(String text) {
/*  71 */     ArrayList l = new ArrayList();
/*  72 */     int idx = 0;
/*     */     while (true)
/*     */     {
/*  75 */       int eol = text.indexOf("\n", idx);
/*  76 */       if (eol == -1) {
/*  77 */         l.add(text.substring(idx));
/*  78 */         break;
/*     */       }
/*     */ 
/*  81 */       l.add(text.substring(idx, eol + 1));
/*  82 */       idx = eol + 1;
/*     */     }
/*     */ 
/*  86 */     return l;
/*     */   }
/*     */ 
/*     */   public List postProcessAction(List chunks, Token actionToken)
/*     */   {
/* 104 */     List nChunks = new ArrayList();
/* 105 */     for (int i = 0; i < chunks.size(); i++) {
/* 106 */       Object chunk = chunks.get(i);
/*     */ 
/* 108 */       if ((chunk instanceof String)) {
/* 109 */         String text = (String)chunks.get(i);
/* 110 */         if ((nChunks.size() == 0) && (actionToken.getColumn() > 0))
/*     */         {
/* 114 */           String ws = "";
/* 115 */           for (int j = 0; j < actionToken.getColumn(); j++) {
/* 116 */             ws = ws + " ";
/*     */           }
/* 118 */           text = ws + text;
/*     */         }
/*     */ 
/* 121 */         List parts = splitLines(text);
/* 122 */         for (int j = 0; j < parts.size(); j++) {
/* 123 */           chunk = parts.get(j);
/* 124 */           nChunks.add(chunk);
/*     */         }
/*     */       }
/*     */       else {
/* 128 */         if ((nChunks.size() == 0) && (actionToken.getColumn() > 0))
/*     */         {
/* 132 */           String ws = "";
/* 133 */           for (int j = 0; j < actionToken.getColumn(); j++) {
/* 134 */             ws = ws + " ";
/*     */           }
/* 136 */           nChunks.add(ws);
/*     */         }
/*     */ 
/* 139 */         nChunks.add(chunk);
/*     */       }
/*     */     }
/*     */ 
/* 143 */     int lineNo = actionToken.getLine();
/* 144 */     int col = 0;
/*     */ 
/* 147 */     int lastChunk = nChunks.size() - 1;
/*     */ 
/* 150 */     while ((lastChunk > 0) && ((nChunks.get(lastChunk) instanceof String)) && (((String)nChunks.get(lastChunk)).trim().length() == 0)) {
/* 151 */       lastChunk--;
/*     */     }
/*     */ 
/* 154 */     int firstChunk = 0;
/*     */ 
/* 158 */     while ((firstChunk <= lastChunk) && ((nChunks.get(firstChunk) instanceof String)) && (((String)nChunks.get(firstChunk)).trim().length() == 0) && (((String)nChunks.get(firstChunk)).endsWith("\n"))) {
/* 159 */       lineNo++;
/* 160 */       firstChunk++;
/*     */     }
/*     */ 
/* 163 */     int indent = -1;
/* 164 */     for (int i = firstChunk; i <= lastChunk; i++) {
/* 165 */       Object chunk = nChunks.get(i);
/*     */ 
/* 169 */       if ((chunk instanceof String)) {
/* 170 */         String text = (String)chunk;
/*     */ 
/* 172 */         if (col == 0) {
/* 173 */           if (indent == -1)
/*     */           {
/* 177 */             indent = 0;
/* 178 */             for (int j = 0; (j < text.length()) && 
/* 179 */               (Character.isWhitespace(text.charAt(j))); j++)
/*     */             {
/* 182 */               indent++;
/*     */             }
/*     */           }
/*     */ 
/* 186 */           if (text.length() >= indent)
/*     */           {
/* 188 */             for (int j = 0; j < indent; j++) {
/* 189 */               if (!Character.isWhitespace(text.charAt(j)))
/*     */               {
/* 191 */                 System.err.println("Warning: badly indented line " + lineNo + " in action:");
/* 192 */                 System.err.println(text);
/* 193 */                 break;
/*     */               }
/*     */             }
/*     */ 
/* 197 */             nChunks.set(i, text.substring(j));
/*     */           }
/* 199 */           else if (text.trim().length() > 0)
/*     */           {
/* 201 */             System.err.println("Warning: badly indented line " + lineNo + " in action:");
/* 202 */             System.err.println(text);
/*     */           }
/*     */         }
/*     */ 
/* 206 */         if (text.endsWith("\n")) {
/* 207 */           lineNo++;
/* 208 */           col = 0;
/*     */         }
/*     */         else {
/* 211 */           col += text.length();
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 216 */         col++;
/*     */       }
/*     */     }
/*     */ 
/* 220 */     return nChunks;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.codegen.PythonTarget
 * JD-Core Version:    0.6.2
 */