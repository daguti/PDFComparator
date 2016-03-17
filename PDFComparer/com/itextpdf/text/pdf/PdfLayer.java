/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class PdfLayer extends PdfDictionary
/*     */   implements PdfOCG
/*     */ {
/*     */   protected PdfIndirectReference ref;
/*     */   protected ArrayList<PdfLayer> children;
/*     */   protected PdfLayer parent;
/*     */   protected String title;
/*  67 */   private boolean on = true;
/*     */ 
/*  72 */   private boolean onPanel = true;
/*     */ 
/*     */   PdfLayer(String title) {
/*  75 */     this.title = title;
/*     */   }
/*     */ 
/*     */   public static PdfLayer createTitle(String title, PdfWriter writer)
/*     */   {
/*  86 */     if (title == null)
/*  87 */       throw new NullPointerException(MessageLocalization.getComposedMessage("title.cannot.be.null", new Object[0]));
/*  88 */     PdfLayer layer = new PdfLayer(title);
/*  89 */     writer.registerLayer(layer);
/*  90 */     return layer;
/*     */   }
/*     */ 
/*     */   public PdfLayer(String name, PdfWriter writer)
/*     */     throws IOException
/*     */   {
/*  99 */     super(PdfName.OCG);
/* 100 */     setName(name);
/* 101 */     if ((writer instanceof PdfStamperImp))
/* 102 */       this.ref = writer.addToBody(this).getIndirectReference();
/*     */     else
/* 104 */       this.ref = writer.getPdfIndirectReference();
/* 105 */     writer.registerLayer(this);
/*     */   }
/*     */ 
/*     */   String getTitle() {
/* 109 */     return this.title;
/*     */   }
/*     */ 
/*     */   public void addChild(PdfLayer child)
/*     */   {
/* 117 */     if (child.parent != null)
/* 118 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.layer.1.already.has.a.parent", new Object[] { child.getAsString(PdfName.NAME).toUnicodeString() }));
/* 119 */     child.parent = this;
/* 120 */     if (this.children == null)
/* 121 */       this.children = new ArrayList();
/* 122 */     this.children.add(child);
/*     */   }
/*     */ 
/*     */   public PdfLayer getParent()
/*     */   {
/* 131 */     return this.parent;
/*     */   }
/*     */ 
/*     */   public ArrayList<PdfLayer> getChildren()
/*     */   {
/* 139 */     return this.children;
/*     */   }
/*     */ 
/*     */   public PdfIndirectReference getRef()
/*     */   {
/* 147 */     return this.ref;
/*     */   }
/*     */ 
/*     */   void setRef(PdfIndirectReference ref)
/*     */   {
/* 157 */     this.ref = ref;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 165 */     put(PdfName.NAME, new PdfString(name, "UnicodeBig"));
/*     */   }
/*     */ 
/*     */   public PdfObject getPdfObject()
/*     */   {
/* 173 */     return this;
/*     */   }
/*     */ 
/*     */   public boolean isOn()
/*     */   {
/* 181 */     return this.on;
/*     */   }
/*     */ 
/*     */   public void setOn(boolean on)
/*     */   {
/* 189 */     this.on = on;
/*     */   }
/*     */ 
/*     */   private PdfDictionary getUsage() {
/* 193 */     PdfDictionary usage = getAsDict(PdfName.USAGE);
/* 194 */     if (usage == null) {
/* 195 */       usage = new PdfDictionary();
/* 196 */       put(PdfName.USAGE, usage);
/*     */     }
/* 198 */     return usage;
/*     */   }
/*     */ 
/*     */   public void setCreatorInfo(String creator, String subtype)
/*     */   {
/* 211 */     PdfDictionary usage = getUsage();
/* 212 */     PdfDictionary dic = new PdfDictionary();
/* 213 */     dic.put(PdfName.CREATOR, new PdfString(creator, "UnicodeBig"));
/* 214 */     dic.put(PdfName.SUBTYPE, new PdfName(subtype));
/* 215 */     usage.put(PdfName.CREATORINFO, dic);
/*     */   }
/*     */ 
/*     */   public void setLanguage(String lang, boolean preferred)
/*     */   {
/* 227 */     PdfDictionary usage = getUsage();
/* 228 */     PdfDictionary dic = new PdfDictionary();
/* 229 */     dic.put(PdfName.LANG, new PdfString(lang, "UnicodeBig"));
/* 230 */     if (preferred)
/* 231 */       dic.put(PdfName.PREFERRED, PdfName.ON);
/* 232 */     usage.put(PdfName.LANGUAGE, dic);
/*     */   }
/*     */ 
/*     */   public void setExport(boolean export)
/*     */   {
/* 243 */     PdfDictionary usage = getUsage();
/* 244 */     PdfDictionary dic = new PdfDictionary();
/* 245 */     dic.put(PdfName.EXPORTSTATE, export ? PdfName.ON : PdfName.OFF);
/* 246 */     usage.put(PdfName.EXPORT, dic);
/*     */   }
/*     */ 
/*     */   public void setZoom(float min, float max)
/*     */   {
/* 259 */     if ((min <= 0.0F) && (max < 0.0F))
/* 260 */       return;
/* 261 */     PdfDictionary usage = getUsage();
/* 262 */     PdfDictionary dic = new PdfDictionary();
/* 263 */     if (min > 0.0F)
/* 264 */       dic.put(PdfName.MIN_LOWER_CASE, new PdfNumber(min));
/* 265 */     if (max >= 0.0F)
/* 266 */       dic.put(PdfName.MAX_LOWER_CASE, new PdfNumber(max));
/* 267 */     usage.put(PdfName.ZOOM, dic);
/*     */   }
/*     */ 
/*     */   public void setPrint(String subtype, boolean printstate)
/*     */   {
/* 279 */     PdfDictionary usage = getUsage();
/* 280 */     PdfDictionary dic = new PdfDictionary();
/* 281 */     dic.put(PdfName.SUBTYPE, new PdfName(subtype));
/* 282 */     dic.put(PdfName.PRINTSTATE, printstate ? PdfName.ON : PdfName.OFF);
/* 283 */     usage.put(PdfName.PRINT, dic);
/*     */   }
/*     */ 
/*     */   public void setView(boolean view)
/*     */   {
/* 292 */     PdfDictionary usage = getUsage();
/* 293 */     PdfDictionary dic = new PdfDictionary();
/* 294 */     dic.put(PdfName.VIEWSTATE, view ? PdfName.ON : PdfName.OFF);
/* 295 */     usage.put(PdfName.VIEW, dic);
/*     */   }
/*     */ 
/*     */   public void setPageElement(String pe)
/*     */   {
/* 305 */     PdfDictionary usage = getUsage();
/* 306 */     PdfDictionary dic = new PdfDictionary();
/* 307 */     dic.put(PdfName.SUBTYPE, new PdfName(pe));
/* 308 */     usage.put(PdfName.PAGEELEMENT, dic);
/*     */   }
/*     */ 
/*     */   public void setUser(String type, String[] names)
/*     */   {
/* 318 */     PdfDictionary usage = getUsage();
/* 319 */     PdfDictionary dic = new PdfDictionary();
/* 320 */     dic.put(PdfName.TYPE, new PdfName(type));
/* 321 */     PdfArray arr = new PdfArray();
/* 322 */     for (String s : names)
/* 323 */       arr.add(new PdfString(s, "UnicodeBig"));
/* 324 */     usage.put(PdfName.NAME, arr);
/* 325 */     usage.put(PdfName.USER, dic);
/*     */   }
/*     */ 
/*     */   public boolean isOnPanel()
/*     */   {
/* 333 */     return this.onPanel;
/*     */   }
/*     */ 
/*     */   public void setOnPanel(boolean onPanel)
/*     */   {
/* 343 */     this.onPanel = onPanel;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfLayer
 * JD-Core Version:    0.6.2
 */