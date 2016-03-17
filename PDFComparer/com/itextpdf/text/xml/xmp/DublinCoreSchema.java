/*     */ package com.itextpdf.text.xml.xmp;
/*     */ 
/*     */ @Deprecated
/*     */ public class DublinCoreSchema extends XmpSchema
/*     */ {
/*     */   private static final long serialVersionUID = -4551741356374797330L;
/*     */   public static final String DEFAULT_XPATH_ID = "dc";
/*     */   public static final String DEFAULT_XPATH_URI = "http://purl.org/dc/elements/1.1/";
/*     */   public static final String CONTRIBUTOR = "dc:contributor";
/*     */   public static final String COVERAGE = "dc:coverage";
/*     */   public static final String CREATOR = "dc:creator";
/*     */   public static final String DATE = "dc:date";
/*     */   public static final String DESCRIPTION = "dc:description";
/*     */   public static final String FORMAT = "dc:format";
/*     */   public static final String IDENTIFIER = "dc:identifier";
/*     */   public static final String LANGUAGE = "dc:language";
/*     */   public static final String PUBLISHER = "dc:publisher";
/*     */   public static final String RELATION = "dc:relation";
/*     */   public static final String RIGHTS = "dc:rights";
/*     */   public static final String SOURCE = "dc:source";
/*     */   public static final String SUBJECT = "dc:subject";
/*     */   public static final String TITLE = "dc:title";
/*     */   public static final String TYPE = "dc:type";
/*     */ 
/*     */   public DublinCoreSchema()
/*     */   {
/*  99 */     super("xmlns:dc=\"http://purl.org/dc/elements/1.1/\"");
/* 100 */     setProperty("dc:format", "application/pdf");
/*     */   }
/*     */ 
/*     */   public void addTitle(String title)
/*     */   {
/* 108 */     XmpArray array = new XmpArray("rdf:Alt");
/* 109 */     array.add(title);
/* 110 */     setProperty("dc:title", array);
/*     */   }
/*     */ 
/*     */   public void addTitle(LangAlt title)
/*     */   {
/* 118 */     setProperty("dc:title", title);
/*     */   }
/*     */ 
/*     */   public void addDescription(String desc)
/*     */   {
/* 126 */     XmpArray array = new XmpArray("rdf:Alt");
/* 127 */     array.add(desc);
/* 128 */     setProperty("dc:description", array);
/*     */   }
/*     */ 
/*     */   public void addDescription(LangAlt desc)
/*     */   {
/* 136 */     setProperty("dc:description", desc);
/*     */   }
/*     */ 
/*     */   public void addSubject(String subject)
/*     */   {
/* 144 */     XmpArray array = new XmpArray("rdf:Bag");
/* 145 */     array.add(subject);
/* 146 */     setProperty("dc:subject", array);
/*     */   }
/*     */ 
/*     */   public void addSubject(String[] subject)
/*     */   {
/* 155 */     XmpArray array = new XmpArray("rdf:Bag");
/* 156 */     for (int i = 0; i < subject.length; i++) {
/* 157 */       array.add(subject[i]);
/*     */     }
/* 159 */     setProperty("dc:subject", array);
/*     */   }
/*     */ 
/*     */   public void addAuthor(String author)
/*     */   {
/* 167 */     XmpArray array = new XmpArray("rdf:Seq");
/* 168 */     array.add(author);
/* 169 */     setProperty("dc:creator", array);
/*     */   }
/*     */ 
/*     */   public void addAuthor(String[] author)
/*     */   {
/* 177 */     XmpArray array = new XmpArray("rdf:Seq");
/* 178 */     for (int i = 0; i < author.length; i++) {
/* 179 */       array.add(author[i]);
/*     */     }
/* 181 */     setProperty("dc:creator", array);
/*     */   }
/*     */ 
/*     */   public void addPublisher(String publisher)
/*     */   {
/* 189 */     XmpArray array = new XmpArray("rdf:Seq");
/* 190 */     array.add(publisher);
/* 191 */     setProperty("dc:publisher", array);
/*     */   }
/*     */ 
/*     */   public void addPublisher(String[] publisher)
/*     */   {
/* 199 */     XmpArray array = new XmpArray("rdf:Seq");
/* 200 */     for (int i = 0; i < publisher.length; i++) {
/* 201 */       array.add(publisher[i]);
/*     */     }
/* 203 */     setProperty("dc:publisher", array);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.xml.xmp.DublinCoreSchema
 * JD-Core Version:    0.6.2
 */