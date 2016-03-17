/*     */ package antlr;
/*     */ 
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.Writer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PrintWriterWithSMAP extends PrintWriter
/*     */ {
/*  18 */   private int currentOutputLine = 1;
/*  19 */   private int currentSourceLine = 0;
/*  20 */   private Map sourceMap = new HashMap();
/*     */ 
/*  22 */   private boolean lastPrintCharacterWasCR = false;
/*  23 */   private boolean mapLines = false;
/*  24 */   private boolean mapSingleSourceLine = false;
/*  25 */   private boolean anythingWrittenSinceMapping = false;
/*     */ 
/*     */   public PrintWriterWithSMAP(OutputStream paramOutputStream) {
/*  28 */     super(paramOutputStream);
/*     */   }
/*     */   public PrintWriterWithSMAP(OutputStream paramOutputStream, boolean paramBoolean) {
/*  31 */     super(paramOutputStream, paramBoolean);
/*     */   }
/*     */   public PrintWriterWithSMAP(Writer paramWriter) {
/*  34 */     super(paramWriter);
/*     */   }
/*     */   public PrintWriterWithSMAP(Writer paramWriter, boolean paramBoolean) {
/*  37 */     super(paramWriter, paramBoolean);
/*     */   }
/*     */ 
/*     */   public void startMapping(int paramInt) {
/*  41 */     this.mapLines = true;
/*  42 */     if (paramInt != -888)
/*  43 */       this.currentSourceLine = paramInt;
/*     */   }
/*     */ 
/*     */   public void startSingleSourceLineMapping(int paramInt) {
/*  47 */     this.mapSingleSourceLine = true;
/*  48 */     this.mapLines = true;
/*  49 */     if (paramInt != -888)
/*  50 */       this.currentSourceLine = paramInt;
/*     */   }
/*     */ 
/*     */   public void endMapping() {
/*  54 */     mapLine(false);
/*  55 */     this.mapLines = false;
/*  56 */     this.mapSingleSourceLine = false;
/*     */   }
/*     */ 
/*     */   protected void mapLine(boolean paramBoolean) {
/*  60 */     if ((this.mapLines) && (this.anythingWrittenSinceMapping)) {
/*  61 */       Integer localInteger1 = new Integer(this.currentSourceLine);
/*  62 */       Integer localInteger2 = new Integer(this.currentOutputLine);
/*  63 */       Object localObject = (List)this.sourceMap.get(localInteger1);
/*  64 */       if (localObject == null) {
/*  65 */         localObject = new ArrayList();
/*  66 */         this.sourceMap.put(localInteger1, localObject);
/*     */       }
/*  68 */       if (!((List)localObject).contains(localInteger2))
/*  69 */         ((List)localObject).add(localInteger2);
/*     */     }
/*  71 */     if (paramBoolean)
/*  72 */       this.currentOutputLine += 1;
/*  73 */     if (!this.mapSingleSourceLine)
/*  74 */       this.currentSourceLine += 1;
/*  75 */     this.anythingWrittenSinceMapping = false;
/*     */   }
/*     */ 
/*     */   public void dump(PrintWriter paramPrintWriter, String paramString1, String paramString2) {
/*  79 */     paramPrintWriter.println("SMAP");
/*  80 */     paramPrintWriter.println(paramString1 + ".java");
/*  81 */     paramPrintWriter.println("G");
/*  82 */     paramPrintWriter.println("*S G");
/*  83 */     paramPrintWriter.println("*F");
/*  84 */     paramPrintWriter.println("+ 0 " + paramString2);
/*  85 */     paramPrintWriter.println(paramString2);
/*  86 */     paramPrintWriter.println("*L");
/*  87 */     ArrayList localArrayList = new ArrayList(this.sourceMap.keySet());
/*  88 */     Collections.sort(localArrayList);
/*  89 */     for (Iterator localIterator1 = localArrayList.iterator(); localIterator1.hasNext(); ) {
/*  90 */       localInteger1 = (Integer)localIterator1.next();
/*  91 */       List localList = (List)this.sourceMap.get(localInteger1);
/*  92 */       for (localIterator2 = localList.iterator(); localIterator2.hasNext(); ) {
/*  93 */         Integer localInteger2 = (Integer)localIterator2.next();
/*  94 */         paramPrintWriter.println(localInteger1 + ":" + localInteger2);
/*     */       }
/*     */     }
/*     */     Integer localInteger1;
/*     */     Iterator localIterator2;
/*  97 */     paramPrintWriter.println("*E");
/*  98 */     paramPrintWriter.close();
/*     */   }
/*     */ 
/*     */   public void write(char[] paramArrayOfChar, int paramInt1, int paramInt2) {
/* 102 */     int i = paramInt1 + paramInt2;
/* 103 */     for (int j = paramInt1; j < i; j++) {
/* 104 */       checkChar(paramArrayOfChar[j]);
/*     */     }
/* 106 */     super.write(paramArrayOfChar, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public void checkChar(int paramInt)
/*     */   {
/* 111 */     if ((this.lastPrintCharacterWasCR) && (paramInt != 10)) {
/* 112 */       mapLine(true);
/*     */     }
/* 114 */     else if (paramInt == 10) {
/* 115 */       mapLine(true);
/*     */     }
/* 117 */     else if (!Character.isWhitespace((char)paramInt)) {
/* 118 */       this.anythingWrittenSinceMapping = true;
/*     */     }
/* 120 */     this.lastPrintCharacterWasCR = (paramInt == 13);
/*     */   }
/*     */   public void write(int paramInt) {
/* 123 */     checkChar(paramInt);
/* 124 */     super.write(paramInt);
/*     */   }
/*     */   public void write(String paramString, int paramInt1, int paramInt2) {
/* 127 */     int i = paramInt1 + paramInt2;
/* 128 */     for (int j = paramInt1; j < i; j++) {
/* 129 */       checkChar(paramString.charAt(j));
/*     */     }
/* 131 */     super.write(paramString, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public void println()
/*     */   {
/* 141 */     mapLine(true);
/* 142 */     super.println();
/* 143 */     this.lastPrintCharacterWasCR = false;
/*     */   }
/*     */   public Map getSourceMap() {
/* 146 */     return this.sourceMap;
/*     */   }
/*     */ 
/*     */   public int getCurrentOutputLine() {
/* 150 */     return this.currentOutputLine;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.PrintWriterWithSMAP
 * JD-Core Version:    0.6.2
 */