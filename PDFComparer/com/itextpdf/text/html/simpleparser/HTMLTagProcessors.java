/*     */ package com.itextpdf.text.html.simpleparser;
/*     */ 
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ @Deprecated
/*     */ public class HTMLTagProcessors extends HashMap<String, HTMLTagProcessor>
/*     */ {
/* 105 */   public static final HTMLTagProcessor EM_STRONG_STRIKE_SUP_SUP = new HTMLTagProcessor()
/*     */   {
/*     */     public void startElement(HTMLWorker worker, String tag, Map<String, String> attrs)
/*     */     {
/* 110 */       tag = mapTag(tag);
/* 111 */       attrs.put(tag, null);
/* 112 */       worker.updateChain(tag, attrs);
/*     */     }
/*     */ 
/*     */     public void endElement(HTMLWorker worker, String tag)
/*     */     {
/* 118 */       tag = mapTag(tag);
/* 119 */       worker.updateChain(tag);
/*     */     }
/*     */ 
/*     */     private String mapTag(String tag)
/*     */     {
/* 128 */       if ("em".equalsIgnoreCase(tag))
/* 129 */         return "i";
/* 130 */       if ("strong".equalsIgnoreCase(tag))
/* 131 */         return "b";
/* 132 */       if ("strike".equalsIgnoreCase(tag))
/* 133 */         return "s";
/* 134 */       return tag;
/*     */     }
/* 105 */   };
/*     */ 
/* 142 */   public static final HTMLTagProcessor A = new HTMLTagProcessor()
/*     */   {
/*     */     public void startElement(HTMLWorker worker, String tag, Map<String, String> attrs)
/*     */     {
/* 147 */       worker.updateChain(tag, attrs);
/* 148 */       worker.flushContent();
/*     */     }
/*     */ 
/*     */     public void endElement(HTMLWorker worker, String tag)
/*     */     {
/* 154 */       worker.processLink();
/* 155 */       worker.updateChain(tag);
/*     */     }
/* 142 */   };
/*     */ 
/* 162 */   public static final HTMLTagProcessor BR = new HTMLTagProcessor()
/*     */   {
/*     */     public void startElement(HTMLWorker worker, String tag, Map<String, String> attrs)
/*     */     {
/* 167 */       worker.newLine();
/*     */     }
/*     */ 
/*     */     public void endElement(HTMLWorker worker, String tag)
/*     */     {
/*     */     }
/* 162 */   };
/*     */ 
/* 177 */   public static final HTMLTagProcessor UL_OL = new HTMLTagProcessor()
/*     */   {
/*     */     public void startElement(HTMLWorker worker, String tag, Map<String, String> attrs)
/*     */       throws DocumentException
/*     */     {
/* 183 */       worker.carriageReturn();
/* 184 */       if (worker.isPendingLI())
/* 185 */         worker.endElement("li");
/* 186 */       worker.setSkipText(true);
/* 187 */       worker.updateChain(tag, attrs);
/* 188 */       worker.pushToStack(worker.createList(tag));
/*     */     }
/*     */ 
/*     */     public void endElement(HTMLWorker worker, String tag)
/*     */       throws DocumentException
/*     */     {
/* 195 */       worker.carriageReturn();
/* 196 */       if (worker.isPendingLI())
/* 197 */         worker.endElement("li");
/* 198 */       worker.setSkipText(false);
/* 199 */       worker.updateChain(tag);
/* 200 */       worker.processList();
/*     */     }
/* 177 */   };
/*     */ 
/* 205 */   public static final HTMLTagProcessor HR = new HTMLTagProcessor()
/*     */   {
/*     */     public void startElement(HTMLWorker worker, String tag, Map<String, String> attrs) throws DocumentException {
/* 208 */       worker.carriageReturn();
/* 209 */       worker.pushToStack(worker.createLineSeparator(attrs));
/*     */     }
/*     */ 
/*     */     public void endElement(HTMLWorker worker, String tag)
/*     */     {
/*     */     }
/* 205 */   };
/*     */ 
/* 217 */   public static final HTMLTagProcessor SPAN = new HTMLTagProcessor()
/*     */   {
/*     */     public void startElement(HTMLWorker worker, String tag, Map<String, String> attrs)
/*     */     {
/* 223 */       worker.updateChain(tag, attrs);
/*     */     }
/*     */ 
/*     */     public void endElement(HTMLWorker worker, String tag)
/*     */     {
/* 230 */       worker.updateChain(tag);
/*     */     }
/* 217 */   };
/*     */ 
/* 235 */   public static final HTMLTagProcessor H = new HTMLTagProcessor()
/*     */   {
/*     */     public void startElement(HTMLWorker worker, String tag, Map<String, String> attrs)
/*     */       throws DocumentException
/*     */     {
/* 241 */       worker.carriageReturn();
/* 242 */       if (!attrs.containsKey("size")) {
/* 243 */         int v = 7 - Integer.parseInt(tag.substring(1));
/* 244 */         attrs.put("size", Integer.toString(v));
/*     */       }
/* 246 */       worker.updateChain(tag, attrs);
/*     */     }
/*     */ 
/*     */     public void endElement(HTMLWorker worker, String tag)
/*     */       throws DocumentException
/*     */     {
/* 253 */       worker.carriageReturn();
/* 254 */       worker.updateChain(tag);
/*     */     }
/* 235 */   };
/*     */ 
/* 259 */   public static final HTMLTagProcessor LI = new HTMLTagProcessor()
/*     */   {
/*     */     public void startElement(HTMLWorker worker, String tag, Map<String, String> attrs)
/*     */       throws DocumentException
/*     */     {
/* 265 */       worker.carriageReturn();
/* 266 */       if (worker.isPendingLI())
/* 267 */         worker.endElement(tag);
/* 268 */       worker.setSkipText(false);
/* 269 */       worker.setPendingLI(true);
/* 270 */       worker.updateChain(tag, attrs);
/* 271 */       worker.pushToStack(worker.createListItem());
/*     */     }
/*     */ 
/*     */     public void endElement(HTMLWorker worker, String tag)
/*     */       throws DocumentException
/*     */     {
/* 278 */       worker.carriageReturn();
/* 279 */       worker.setPendingLI(false);
/* 280 */       worker.setSkipText(true);
/* 281 */       worker.updateChain(tag);
/* 282 */       worker.processListItem();
/*     */     }
/* 259 */   };
/*     */ 
/* 287 */   public static final HTMLTagProcessor PRE = new HTMLTagProcessor()
/*     */   {
/*     */     public void startElement(HTMLWorker worker, String tag, Map<String, String> attrs)
/*     */       throws DocumentException
/*     */     {
/* 293 */       worker.carriageReturn();
/* 294 */       if (!attrs.containsKey("face")) {
/* 295 */         attrs.put("face", "Courier");
/*     */       }
/* 297 */       worker.updateChain(tag, attrs);
/* 298 */       worker.setInsidePRE(true);
/*     */     }
/*     */ 
/*     */     public void endElement(HTMLWorker worker, String tag)
/*     */       throws DocumentException
/*     */     {
/* 305 */       worker.carriageReturn();
/* 306 */       worker.updateChain(tag);
/* 307 */       worker.setInsidePRE(false);
/*     */     }
/* 287 */   };
/*     */ 
/* 312 */   public static final HTMLTagProcessor DIV = new HTMLTagProcessor()
/*     */   {
/*     */     public void startElement(HTMLWorker worker, String tag, Map<String, String> attrs)
/*     */       throws DocumentException
/*     */     {
/* 318 */       worker.carriageReturn();
/* 319 */       worker.updateChain(tag, attrs);
/*     */     }
/*     */ 
/*     */     public void endElement(HTMLWorker worker, String tag)
/*     */       throws DocumentException
/*     */     {
/* 326 */       worker.carriageReturn();
/* 327 */       worker.updateChain(tag);
/*     */     }
/* 312 */   };
/*     */ 
/* 333 */   public static final HTMLTagProcessor TABLE = new HTMLTagProcessor()
/*     */   {
/*     */     public void startElement(HTMLWorker worker, String tag, Map<String, String> attrs)
/*     */       throws DocumentException
/*     */     {
/* 340 */       worker.carriageReturn();
/* 341 */       TableWrapper table = new TableWrapper(attrs);
/* 342 */       worker.pushToStack(table);
/* 343 */       worker.pushTableState();
/* 344 */       worker.setPendingTD(false);
/* 345 */       worker.setPendingTR(false);
/* 346 */       worker.setSkipText(true);
/*     */ 
/* 348 */       attrs.remove("align");
/*     */ 
/* 350 */       attrs.put("colspan", "1");
/* 351 */       attrs.put("rowspan", "1");
/* 352 */       worker.updateChain(tag, attrs);
/*     */     }
/*     */ 
/*     */     public void endElement(HTMLWorker worker, String tag)
/*     */       throws DocumentException
/*     */     {
/* 359 */       worker.carriageReturn();
/* 360 */       if (worker.isPendingTR())
/* 361 */         worker.endElement("tr");
/* 362 */       worker.updateChain(tag);
/* 363 */       worker.processTable();
/* 364 */       worker.popTableState();
/* 365 */       worker.setSkipText(false);
/*     */     }
/* 333 */   };
/*     */ 
/* 369 */   public static final HTMLTagProcessor TR = new HTMLTagProcessor()
/*     */   {
/*     */     public void startElement(HTMLWorker worker, String tag, Map<String, String> attrs)
/*     */       throws DocumentException
/*     */     {
/* 376 */       worker.carriageReturn();
/* 377 */       if (worker.isPendingTR())
/* 378 */         worker.endElement(tag);
/* 379 */       worker.setSkipText(true);
/* 380 */       worker.setPendingTR(true);
/* 381 */       worker.updateChain(tag, attrs);
/*     */     }
/*     */ 
/*     */     public void endElement(HTMLWorker worker, String tag)
/*     */       throws DocumentException
/*     */     {
/* 388 */       worker.carriageReturn();
/* 389 */       if (worker.isPendingTD())
/* 390 */         worker.endElement("td");
/* 391 */       worker.setPendingTR(false);
/* 392 */       worker.updateChain(tag);
/* 393 */       worker.processRow();
/* 394 */       worker.setSkipText(true);
/*     */     }
/* 369 */   };
/*     */ 
/* 398 */   public static final HTMLTagProcessor TD = new HTMLTagProcessor()
/*     */   {
/*     */     public void startElement(HTMLWorker worker, String tag, Map<String, String> attrs)
/*     */       throws DocumentException
/*     */     {
/* 405 */       worker.carriageReturn();
/* 406 */       if (worker.isPendingTD())
/* 407 */         worker.endElement(tag);
/* 408 */       worker.setSkipText(false);
/* 409 */       worker.setPendingTD(true);
/* 410 */       worker.updateChain("td", attrs);
/* 411 */       worker.pushToStack(worker.createCell(tag));
/*     */     }
/*     */ 
/*     */     public void endElement(HTMLWorker worker, String tag)
/*     */       throws DocumentException
/*     */     {
/* 418 */       worker.carriageReturn();
/* 419 */       worker.setPendingTD(false);
/* 420 */       worker.updateChain("td");
/* 421 */       worker.setSkipText(true);
/*     */     }
/* 398 */   };
/*     */ 
/* 426 */   public static final HTMLTagProcessor IMG = new HTMLTagProcessor()
/*     */   {
/*     */     public void startElement(HTMLWorker worker, String tag, Map<String, String> attrs)
/*     */       throws DocumentException, IOException
/*     */     {
/* 432 */       worker.updateChain(tag, attrs);
/* 433 */       worker.processImage(worker.createImage(attrs), attrs);
/* 434 */       worker.updateChain(tag);
/*     */     }
/*     */ 
/*     */     public void endElement(HTMLWorker worker, String tag)
/*     */     {
/*     */     }
/* 426 */   };
/*     */   private static final long serialVersionUID = -959260811961222824L;
/*     */ 
/*     */   public HTMLTagProcessors()
/*     */   {
/*  67 */     put("a", A);
/*  68 */     put("b", EM_STRONG_STRIKE_SUP_SUP);
/*  69 */     put("body", DIV);
/*  70 */     put("br", BR);
/*  71 */     put("div", DIV);
/*  72 */     put("em", EM_STRONG_STRIKE_SUP_SUP);
/*  73 */     put("font", SPAN);
/*  74 */     put("h1", H);
/*  75 */     put("h2", H);
/*  76 */     put("h3", H);
/*  77 */     put("h4", H);
/*  78 */     put("h5", H);
/*  79 */     put("h6", H);
/*  80 */     put("hr", HR);
/*  81 */     put("i", EM_STRONG_STRIKE_SUP_SUP);
/*  82 */     put("img", IMG);
/*  83 */     put("li", LI);
/*  84 */     put("ol", UL_OL);
/*  85 */     put("p", DIV);
/*  86 */     put("pre", PRE);
/*  87 */     put("s", EM_STRONG_STRIKE_SUP_SUP);
/*  88 */     put("span", SPAN);
/*  89 */     put("strike", EM_STRONG_STRIKE_SUP_SUP);
/*  90 */     put("strong", EM_STRONG_STRIKE_SUP_SUP);
/*  91 */     put("sub", EM_STRONG_STRIKE_SUP_SUP);
/*  92 */     put("sup", EM_STRONG_STRIKE_SUP_SUP);
/*  93 */     put("table", TABLE);
/*  94 */     put("td", TD);
/*  95 */     put("th", TD);
/*  96 */     put("tr", TR);
/*  97 */     put("u", EM_STRONG_STRIKE_SUP_SUP);
/*  98 */     put("ul", UL_OL);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.html.simpleparser.HTMLTagProcessors
 * JD-Core Version:    0.6.2
 */