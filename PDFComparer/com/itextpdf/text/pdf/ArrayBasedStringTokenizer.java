/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class ArrayBasedStringTokenizer
/*     */ {
/*     */   private final Pattern regex;
/*     */ 
/*     */   public ArrayBasedStringTokenizer(String[] tokens)
/*     */   {
/*  64 */     this.regex = Pattern.compile(getRegexFromTokens(tokens));
/*     */   }
/*     */ 
/*     */   public String[] tokenize(String text)
/*     */   {
/*  69 */     List tokens = new ArrayList();
/*     */ 
/*  71 */     Matcher matcher = this.regex.matcher(text);
/*     */ 
/*  73 */     int endIndexOfpreviousMatch = 0;
/*     */ 
/*  75 */     while (matcher.find())
/*     */     {
/*  77 */       int startIndexOfMatch = matcher.start();
/*     */ 
/*  79 */       String previousToken = text.substring(endIndexOfpreviousMatch, startIndexOfMatch);
/*     */ 
/*  81 */       if (previousToken.length() > 0) {
/*  82 */         tokens.add(previousToken);
/*     */       }
/*     */ 
/*  85 */       String currentMatch = matcher.group();
/*     */ 
/*  89 */       tokens.add(currentMatch);
/*     */ 
/*  91 */       endIndexOfpreviousMatch = matcher.end();
/*     */     }
/*     */ 
/*  95 */     String tail = text.substring(endIndexOfpreviousMatch, text.length());
/*     */ 
/*  97 */     if (tail.length() > 0) {
/*  98 */       tokens.add(tail);
/*     */     }
/*     */ 
/* 101 */     return (String[])tokens.toArray(new String[0]);
/*     */   }
/*     */ 
/*     */   private String getRegexFromTokens(String[] tokens)
/*     */   {
/* 106 */     StringBuilder regexBuilder = new StringBuilder(100);
/*     */ 
/* 108 */     for (String token : tokens) {
/* 109 */       regexBuilder.append("(").append(token).append(")|");
/*     */     }
/*     */ 
/* 112 */     regexBuilder.setLength(regexBuilder.length() - 1);
/*     */ 
/* 114 */     String regex = regexBuilder.toString();
/*     */ 
/* 116 */     return regex;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.ArrayBasedStringTokenizer
 * JD-Core Version:    0.6.2
 */