/*     */ package org.antlr.tool;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Reader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class GrammarSpelunker
/*     */ {
/*     */   protected String grammarFileName;
/*     */   protected String token;
/*     */   protected Scanner scanner;
/*     */   protected String grammarModifier;
/*     */   protected String grammarName;
/*     */   protected String tokenVocab;
/*  56 */   protected String language = "Java";
/*     */   protected String inputDirectory;
/*     */   protected List<String> importedGrammars;
/*     */ 
/*     */   public GrammarSpelunker(String inputDirectory, String grammarFileName)
/*     */   {
/*  61 */     this.inputDirectory = inputDirectory;
/*  62 */     this.grammarFileName = grammarFileName;
/*     */   }
/*     */   void consume() throws IOException {
/*  65 */     this.token = this.scanner.nextToken();
/*     */   }
/*     */ 
/*     */   protected void match(String expecting) throws IOException {
/*  69 */     if (this.token.equals(expecting)) consume(); else
/*  70 */       throw new Error("Error parsing " + this.grammarFileName + ": '" + this.token + "' not expected '" + expecting + "'");
/*     */   }
/*     */ 
/*     */   public void parse() throws IOException
/*     */   {
/*  75 */     Reader r = new FileReader((this.inputDirectory != null ? this.inputDirectory + File.separator : "") + this.grammarFileName);
/*  76 */     BufferedReader br = new BufferedReader(r);
/*     */     try {
/*  78 */       this.scanner = new Scanner(br);
/*  79 */       consume();
/*  80 */       grammarHeader();
/*     */ 
/*  83 */       while ((this.token != null) && (!this.token.equals("@")) && (!this.token.equals(":")) && (!this.token.equals("import")) && (!this.token.equals("options")))
/*     */       {
/*  85 */         consume();
/*     */       }
/*  87 */       if (this.token.equals("options")) options();
/*     */ 
/*  89 */       while ((this.token != null) && (!this.token.equals("@")) && (!this.token.equals(":")) && (!this.token.equals("import")))
/*     */       {
/*  92 */         consume();
/*     */       }
/*  94 */       if (this.token.equals("import")) imports();
/*     */     }
/*     */     finally
/*     */     {
/*  98 */       if (br != null) br.close(); 
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void grammarHeader() throws IOException {
/* 103 */     if (this.token == null) return;
/* 104 */     if ((this.token.equals("tree")) || (this.token.equals("parser")) || (this.token.equals("lexer"))) {
/* 105 */       this.grammarModifier = this.token;
/* 106 */       consume();
/*     */     }
/* 108 */     match("grammar");
/* 109 */     this.grammarName = this.token;
/* 110 */     consume();
/*     */   }
/*     */ 
/*     */   protected void options() throws IOException
/*     */   {
/* 115 */     match("options");
/* 116 */     match("{");
/* 117 */     while ((this.token != null) && (!this.token.equals("}"))) {
/* 118 */       String name = this.token;
/* 119 */       consume();
/* 120 */       String value = this.token;
/* 121 */       consume();
/* 122 */       match(";");
/* 123 */       if (name.equals("tokenVocab")) this.tokenVocab = value;
/* 124 */       if (name.equals("language")) this.language = value;
/*     */     }
/* 126 */     match("}");
/*     */   }
/*     */ 
/*     */   protected void imports() throws IOException
/*     */   {
/* 131 */     match("import");
/* 132 */     this.importedGrammars = new ArrayList();
/* 133 */     while ((this.token != null) && (!this.token.equals(";"))) {
/* 134 */       this.importedGrammars.add(this.token);
/* 135 */       consume();
/*     */     }
/* 137 */     match(";");
/* 138 */     if (this.importedGrammars.size() == 0) this.importedGrammars = null; 
/*     */   }
/*     */ 
/* 141 */   public String getGrammarModifier() { return this.grammarModifier; } 
/*     */   public String getGrammarName() {
/* 143 */     return this.grammarName;
/*     */   }
/* 145 */   public String getTokenVocab() { return this.tokenVocab; } 
/*     */   public String getLanguage() {
/* 147 */     return this.language;
/*     */   }
/* 149 */   public List<String> getImportedGrammars() { return this.importedGrammars; }
/*     */ 
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws IOException
/*     */   {
/* 243 */     GrammarSpelunker g = new GrammarSpelunker(".", args[0]);
/* 244 */     g.parse();
/* 245 */     System.out.println(g.grammarModifier + " grammar " + g.grammarName);
/* 246 */     System.out.println("language=" + g.language);
/* 247 */     System.out.println("tokenVocab=" + g.tokenVocab);
/* 248 */     System.out.println("imports=" + g.importedGrammars);
/*     */   }
/*     */ 
/*     */   public static class Scanner
/*     */   {
/*     */     public static final int EOF = -1;
/*     */     Reader input;
/*     */     int c;
/*     */ 
/*     */     public Scanner(Reader input)
/*     */       throws IOException
/*     */     {
/* 160 */       this.input = input;
/* 161 */       consume();
/*     */     }
/*     */     boolean isDIGIT() {
/* 164 */       return (this.c >= 48) && (this.c <= 57); } 
/* 165 */     boolean isID_START() { return ((this.c >= 97) && (this.c <= 122)) || ((this.c >= 65) && (this.c <= 90)); } 
/* 166 */     boolean isID_LETTER() { return (isID_START()) || ((this.c >= 48) && (this.c <= 57)) || (this.c == 95); } 
/*     */     void consume() throws IOException {
/* 168 */       this.c = this.input.read();
/*     */     }
/*     */     public String nextToken() throws IOException {
/* 171 */       while (this.c != -1)
/*     */       {
/* 173 */         switch (this.c) { case 59:
/* 174 */           consume(); return ";";
/*     */         case 123:
/* 175 */           consume(); return "{";
/*     */         case 125:
/* 176 */           consume(); return "}";
/*     */         case 58:
/* 177 */           consume(); return ":";
/*     */         case 64:
/* 178 */           consume(); return "@";
/*     */         case 47:
/* 179 */           COMMENT(); break;
/*     */         case 39:
/* 180 */           return STRING();
/*     */         }
/* 182 */         if (isID_START()) return ID();
/* 183 */         if (isDIGIT()) return INT();
/* 184 */         consume();
/*     */       }
/*     */ 
/* 187 */       return null;
/*     */     }
/*     */ 
/*     */     String ID() throws IOException
/*     */     {
/* 192 */       StringBuffer buf = new StringBuffer();
/* 193 */       for (; (this.c != -1) && (isID_LETTER()); consume()) buf.append((char)this.c);
/* 194 */       return buf.toString();
/*     */     }
/*     */ 
/*     */     String INT() throws IOException {
/* 198 */       StringBuffer buf = new StringBuffer();
/* 199 */       for (; (this.c != -1) && (isDIGIT()); consume()) buf.append((char)this.c);
/* 200 */       return buf.toString();
/*     */     }
/*     */ 
/*     */     String STRING() throws IOException {
/* 204 */       StringBuffer buf = new StringBuffer();
/* 205 */       consume();
/* 206 */       while ((this.c != -1) && (this.c != 39)) {
/* 207 */         if (this.c == 92) {
/* 208 */           buf.append((char)this.c);
/* 209 */           consume();
/*     */         }
/* 211 */         buf.append((char)this.c);
/* 212 */         consume();
/*     */       }
/* 214 */       consume();
/* 215 */       return buf.toString();
/*     */     }
/*     */ 
/*     */     void COMMENT() throws IOException {
/* 219 */       if (this.c == 47) {
/* 220 */         consume();
/* 221 */         if (this.c == 42) {
/* 222 */           consume();
/*     */           while (true)
/*     */           {
/* 225 */             if (this.c == 42) {
/* 226 */               consume();
/* 227 */               if (this.c == 47) { consume(); break; }
/*     */             }
/*     */             else {
/* 230 */               while ((this.c != -1) && (this.c != 42)) consume();
/*     */             }
/*     */           }
/*     */         }
/* 234 */         if (this.c == 47)
/* 235 */           while ((this.c != -1) && (this.c != 10)) consume();
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.tool.GrammarSpelunker
 * JD-Core Version:    0.6.2
 */