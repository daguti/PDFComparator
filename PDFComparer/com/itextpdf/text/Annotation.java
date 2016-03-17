/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Annotation
/*     */   implements Element
/*     */ {
/*     */   public static final int TEXT = 0;
/*     */   public static final int URL_NET = 1;
/*     */   public static final int URL_AS_STRING = 2;
/*     */   public static final int FILE_DEST = 3;
/*     */   public static final int FILE_PAGE = 4;
/*     */   public static final int NAMED_DEST = 5;
/*     */   public static final int LAUNCH = 6;
/*     */   public static final int SCREEN = 7;
/*     */   public static final String TITLE = "title";
/*     */   public static final String CONTENT = "content";
/*     */   public static final String URL = "url";
/*     */   public static final String FILE = "file";
/*     */   public static final String DESTINATION = "destination";
/*     */   public static final String PAGE = "page";
/*     */   public static final String NAMED = "named";
/*     */   public static final String APPLICATION = "application";
/*     */   public static final String PARAMETERS = "parameters";
/*     */   public static final String OPERATION = "operation";
/*     */   public static final String DEFAULTDIR = "defaultdir";
/*     */   public static final String LLX = "llx";
/*     */   public static final String LLY = "lly";
/*     */   public static final String URX = "urx";
/*     */   public static final String URY = "ury";
/*     */   public static final String MIMETYPE = "mime";
/*     */   protected int annotationtype;
/* 140 */   protected HashMap<String, Object> annotationAttributes = new HashMap();
/*     */ 
/* 143 */   protected float llx = (0.0F / 0.0F);
/*     */ 
/* 146 */   protected float lly = (0.0F / 0.0F);
/*     */ 
/* 149 */   protected float urx = (0.0F / 0.0F);
/*     */ 
/* 152 */   protected float ury = (0.0F / 0.0F);
/*     */ 
/*     */   private Annotation(float llx, float lly, float urx, float ury)
/*     */   {
/* 170 */     this.llx = llx;
/* 171 */     this.lly = lly;
/* 172 */     this.urx = urx;
/* 173 */     this.ury = ury;
/*     */   }
/*     */ 
/*     */   public Annotation(Annotation an)
/*     */   {
/* 181 */     this.annotationtype = an.annotationtype;
/* 182 */     this.annotationAttributes = an.annotationAttributes;
/* 183 */     this.llx = an.llx;
/* 184 */     this.lly = an.lly;
/* 185 */     this.urx = an.urx;
/* 186 */     this.ury = an.ury;
/*     */   }
/*     */ 
/*     */   public Annotation(String title, String text)
/*     */   {
/* 199 */     this.annotationtype = 0;
/* 200 */     this.annotationAttributes.put("title", title);
/* 201 */     this.annotationAttributes.put("content", text);
/*     */   }
/*     */ 
/*     */   public Annotation(String title, String text, float llx, float lly, float urx, float ury)
/*     */   {
/* 223 */     this(llx, lly, urx, ury);
/* 224 */     this.annotationtype = 0;
/* 225 */     this.annotationAttributes.put("title", title);
/* 226 */     this.annotationAttributes.put("content", text);
/*     */   }
/*     */ 
/*     */   public Annotation(float llx, float lly, float urx, float ury, URL url)
/*     */   {
/* 244 */     this(llx, lly, urx, ury);
/* 245 */     this.annotationtype = 1;
/* 246 */     this.annotationAttributes.put("url", url);
/*     */   }
/*     */ 
/*     */   public Annotation(float llx, float lly, float urx, float ury, String url)
/*     */   {
/* 264 */     this(llx, lly, urx, ury);
/* 265 */     this.annotationtype = 2;
/* 266 */     this.annotationAttributes.put("file", url);
/*     */   }
/*     */ 
/*     */   public Annotation(float llx, float lly, float urx, float ury, String file, String dest)
/*     */   {
/* 287 */     this(llx, lly, urx, ury);
/* 288 */     this.annotationtype = 3;
/* 289 */     this.annotationAttributes.put("file", file);
/* 290 */     this.annotationAttributes.put("destination", dest);
/*     */   }
/*     */ 
/*     */   public Annotation(float llx, float lly, float urx, float ury, String moviePath, String mimeType, boolean showOnDisplay)
/*     */   {
/* 309 */     this(llx, lly, urx, ury);
/* 310 */     this.annotationtype = 7;
/* 311 */     this.annotationAttributes.put("file", moviePath);
/* 312 */     this.annotationAttributes.put("mime", mimeType);
/* 313 */     this.annotationAttributes.put("parameters", new boolean[] { false, showOnDisplay });
/*     */   }
/*     */ 
/*     */   public Annotation(float llx, float lly, float urx, float ury, String file, int page)
/*     */   {
/* 335 */     this(llx, lly, urx, ury);
/* 336 */     this.annotationtype = 4;
/* 337 */     this.annotationAttributes.put("file", file);
/* 338 */     this.annotationAttributes.put("page", Integer.valueOf(page));
/*     */   }
/*     */ 
/*     */   public Annotation(float llx, float lly, float urx, float ury, int named)
/*     */   {
/* 356 */     this(llx, lly, urx, ury);
/* 357 */     this.annotationtype = 5;
/* 358 */     this.annotationAttributes.put("named", Integer.valueOf(named));
/*     */   }
/*     */ 
/*     */   public Annotation(float llx, float lly, float urx, float ury, String application, String parameters, String operation, String defaultdir)
/*     */   {
/* 384 */     this(llx, lly, urx, ury);
/* 385 */     this.annotationtype = 6;
/* 386 */     this.annotationAttributes.put("application", application);
/* 387 */     this.annotationAttributes.put("parameters", parameters);
/* 388 */     this.annotationAttributes.put("operation", operation);
/* 389 */     this.annotationAttributes.put("defaultdir", defaultdir);
/*     */   }
/*     */ 
/*     */   public int type()
/*     */   {
/* 400 */     return 29;
/*     */   }
/*     */ 
/*     */   public boolean process(ElementListener listener)
/*     */   {
/*     */     try
/*     */     {
/* 413 */       return listener.add(this); } catch (DocumentException de) {
/*     */     }
/* 415 */     return false;
/*     */   }
/*     */ 
/*     */   public List<Chunk> getChunks()
/*     */   {
/* 426 */     return new ArrayList();
/*     */   }
/*     */ 
/*     */   public void setDimensions(float llx, float lly, float urx, float ury)
/*     */   {
/* 444 */     this.llx = llx;
/* 445 */     this.lly = lly;
/* 446 */     this.urx = urx;
/* 447 */     this.ury = ury;
/*     */   }
/*     */ 
/*     */   public float llx()
/*     */   {
/* 458 */     return this.llx;
/*     */   }
/*     */ 
/*     */   public float lly()
/*     */   {
/* 467 */     return this.lly;
/*     */   }
/*     */ 
/*     */   public float urx()
/*     */   {
/* 476 */     return this.urx;
/*     */   }
/*     */ 
/*     */   public float ury()
/*     */   {
/* 485 */     return this.ury;
/*     */   }
/*     */ 
/*     */   public float llx(float def)
/*     */   {
/* 496 */     if (Float.isNaN(this.llx))
/* 497 */       return def;
/* 498 */     return this.llx;
/*     */   }
/*     */ 
/*     */   public float lly(float def)
/*     */   {
/* 509 */     if (Float.isNaN(this.lly))
/* 510 */       return def;
/* 511 */     return this.lly;
/*     */   }
/*     */ 
/*     */   public float urx(float def)
/*     */   {
/* 522 */     if (Float.isNaN(this.urx))
/* 523 */       return def;
/* 524 */     return this.urx;
/*     */   }
/*     */ 
/*     */   public float ury(float def)
/*     */   {
/* 535 */     if (Float.isNaN(this.ury))
/* 536 */       return def;
/* 537 */     return this.ury;
/*     */   }
/*     */ 
/*     */   public int annotationType()
/*     */   {
/* 546 */     return this.annotationtype;
/*     */   }
/*     */ 
/*     */   public String title()
/*     */   {
/* 555 */     String s = (String)this.annotationAttributes.get("title");
/* 556 */     if (s == null)
/* 557 */       s = "";
/* 558 */     return s;
/*     */   }
/*     */ 
/*     */   public String content()
/*     */   {
/* 567 */     String s = (String)this.annotationAttributes.get("content");
/* 568 */     if (s == null)
/* 569 */       s = "";
/* 570 */     return s;
/*     */   }
/*     */ 
/*     */   public HashMap<String, Object> attributes()
/*     */   {
/* 579 */     return this.annotationAttributes;
/*     */   }
/*     */ 
/*     */   public boolean isContent()
/*     */   {
/* 587 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isNestable()
/*     */   {
/* 595 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.Annotation
 * JD-Core Version:    0.6.2
 */