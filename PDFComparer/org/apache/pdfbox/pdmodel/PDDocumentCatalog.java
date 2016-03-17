/*     */ package org.apache.pdfbox.pdmodel;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.pdmodel.common.COSArrayList;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.pdmodel.common.PDDestinationOrAction;
/*     */ import org.apache.pdfbox.pdmodel.common.PDMetadata;
/*     */ import org.apache.pdfbox.pdmodel.common.PDPageLabels;
/*     */ import org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDMarkInfo;
/*     */ import org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDStructureTreeRoot;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDOutputIntent;
/*     */ import org.apache.pdfbox.pdmodel.graphics.optionalcontent.PDOptionalContentProperties;
/*     */ import org.apache.pdfbox.pdmodel.interactive.action.PDActionFactory;
/*     */ import org.apache.pdfbox.pdmodel.interactive.action.PDDocumentCatalogAdditionalActions;
/*     */ import org.apache.pdfbox.pdmodel.interactive.action.type.PDURIDictionary;
/*     */ import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDDestination;
/*     */ import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
/*     */ import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
/*     */ import org.apache.pdfbox.pdmodel.interactive.pagenavigation.PDThread;
/*     */ import org.apache.pdfbox.pdmodel.interactive.viewerpreferences.PDViewerPreferences;
/*     */ 
/*     */ public class PDDocumentCatalog
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSDictionary root;
/*     */   private PDDocument document;
/*  57 */   private PDAcroForm acroForm = null;
/*     */   public static final String PAGE_MODE_USE_NONE = "UseNone";
/*     */   public static final String PAGE_MODE_USE_OUTLINES = "UseOutlines";
/*     */   public static final String PAGE_MODE_USE_THUMBS = "UseThumbs";
/*     */   public static final String PAGE_MODE_FULL_SCREEN = "FullScreen";
/*     */   public static final String PAGE_MODE_USE_OPTIONAL_CONTENT = "UseOC";
/*     */   public static final String PAGE_MODE_USE_ATTACHMENTS = "UseAttachments";
/*     */   public static final String PAGE_LAYOUT_SINGLE_PAGE = "SinglePage";
/*     */   public static final String PAGE_LAYOUT_ONE_COLUMN = "OneColumn";
/*     */   public static final String PAGE_LAYOUT_TWO_COLUMN_LEFT = "TwoColumnLeft";
/*     */   public static final String PAGE_LAYOUT_TWO_COLUMN_RIGHT = "TwoColumnRight";
/*     */   public static final String PAGE_LAYOUT_TWO_PAGE_LEFT = "TwoPageLeft";
/*     */   public static final String PAGE_LAYOUT_TWO_PAGE_RIGHT = "TwoPageRight";
/*     */ 
/*     */   public PDDocumentCatalog(PDDocument doc)
/*     */   {
/* 121 */     this.document = doc;
/* 122 */     this.root = new COSDictionary();
/* 123 */     this.root.setItem(COSName.TYPE, COSName.CATALOG);
/* 124 */     this.document.getDocument().getTrailer().setItem(COSName.ROOT, this.root);
/*     */   }
/*     */ 
/*     */   public PDDocumentCatalog(PDDocument doc, COSDictionary rootDictionary)
/*     */   {
/* 135 */     this.document = doc;
/* 136 */     this.root = rootDictionary;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/* 146 */     return this.root;
/*     */   }
/*     */ 
/*     */   public COSDictionary getCOSDictionary()
/*     */   {
/* 156 */     return this.root;
/*     */   }
/*     */ 
/*     */   public PDAcroForm getAcroForm()
/*     */   {
/* 167 */     if (this.acroForm == null)
/*     */     {
/* 169 */       COSDictionary acroFormDic = (COSDictionary)this.root.getDictionaryObject(COSName.ACRO_FORM);
/*     */ 
/* 171 */       if (acroFormDic != null)
/*     */       {
/* 173 */         this.acroForm = new PDAcroForm(this.document, acroFormDic);
/*     */       }
/*     */     }
/* 176 */     return this.acroForm;
/*     */   }
/*     */ 
/*     */   public void setAcroForm(PDAcroForm acro)
/*     */   {
/* 186 */     this.root.setItem(COSName.ACRO_FORM, acro);
/*     */   }
/*     */ 
/*     */   public PDPageNode getPages()
/*     */   {
/* 196 */     return new PDPageNode((COSDictionary)this.root.getDictionaryObject(COSName.PAGES));
/*     */   }
/*     */ 
/*     */   public List getAllPages()
/*     */   {
/* 208 */     List retval = new ArrayList();
/* 209 */     PDPageNode rootNode = getPages();
/*     */ 
/* 212 */     rootNode.getAllKids(retval);
/* 213 */     return retval;
/*     */   }
/*     */ 
/*     */   public PDViewerPreferences getViewerPreferences()
/*     */   {
/* 224 */     PDViewerPreferences retval = null;
/* 225 */     COSDictionary dict = (COSDictionary)this.root.getDictionaryObject(COSName.VIEWER_PREFERENCES);
/* 226 */     if (dict != null)
/*     */     {
/* 228 */       retval = new PDViewerPreferences(dict);
/*     */     }
/*     */ 
/* 231 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setViewerPreferences(PDViewerPreferences prefs)
/*     */   {
/* 241 */     this.root.setItem(COSName.VIEWER_PREFERENCES, prefs);
/*     */   }
/*     */ 
/*     */   public PDDocumentOutline getDocumentOutline()
/*     */   {
/* 252 */     PDDocumentOutline retval = null;
/* 253 */     COSDictionary dict = (COSDictionary)this.root.getDictionaryObject(COSName.OUTLINES);
/* 254 */     if (dict != null)
/*     */     {
/* 256 */       retval = new PDDocumentOutline(dict);
/*     */     }
/*     */ 
/* 259 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setDocumentOutline(PDDocumentOutline outlines)
/*     */   {
/* 269 */     this.root.setItem(COSName.OUTLINES, outlines);
/*     */   }
/*     */ 
/*     */   public List getThreads()
/*     */   {
/* 279 */     COSArray array = (COSArray)this.root.getDictionaryObject(COSName.THREADS);
/* 280 */     if (array == null)
/*     */     {
/* 282 */       array = new COSArray();
/* 283 */       this.root.setItem(COSName.THREADS, array);
/*     */     }
/* 285 */     List pdObjects = new ArrayList();
/* 286 */     for (int i = 0; i < array.size(); i++)
/*     */     {
/* 288 */       pdObjects.add(new PDThread((COSDictionary)array.getObject(i)));
/*     */     }
/* 290 */     return new COSArrayList(pdObjects, array);
/*     */   }
/*     */ 
/*     */   public void setThreads(List threads)
/*     */   {
/* 300 */     this.root.setItem(COSName.THREADS, COSArrayList.converterToCOSArray(threads));
/*     */   }
/*     */ 
/*     */   public PDMetadata getMetadata()
/*     */   {
/* 311 */     PDMetadata retval = null;
/* 312 */     COSBase metaObj = this.root.getDictionaryObject(COSName.METADATA);
/* 313 */     if ((metaObj instanceof COSStream))
/*     */     {
/* 315 */       retval = new PDMetadata((COSStream)metaObj);
/*     */     }
/* 317 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setMetadata(PDMetadata meta)
/*     */   {
/* 327 */     this.root.setItem(COSName.METADATA, meta);
/*     */   }
/*     */ 
/*     */   public void setOpenAction(PDDestinationOrAction action)
/*     */   {
/* 337 */     this.root.setItem(COSName.OPEN_ACTION, action);
/*     */   }
/*     */ 
/*     */   public PDDestinationOrAction getOpenAction()
/*     */     throws IOException
/*     */   {
/* 350 */     PDDestinationOrAction action = null;
/* 351 */     COSBase actionObj = this.root.getDictionaryObject(COSName.OPEN_ACTION);
/*     */ 
/* 353 */     if (actionObj != null)
/*     */     {
/* 357 */       if ((actionObj instanceof COSDictionary))
/*     */       {
/* 359 */         action = PDActionFactory.createAction((COSDictionary)actionObj);
/*     */       }
/* 361 */       else if ((actionObj instanceof COSArray))
/*     */       {
/* 363 */         action = PDDestination.create(actionObj);
/*     */       }
/*     */       else
/*     */       {
/* 367 */         throw new IOException("Unknown OpenAction " + actionObj);
/*     */       }
/*     */     }
/* 370 */     return action;
/*     */   }
/*     */ 
/*     */   public PDDocumentCatalogAdditionalActions getActions()
/*     */   {
/* 377 */     COSDictionary addAct = (COSDictionary)this.root.getDictionaryObject(COSName.AA);
/* 378 */     if (addAct == null)
/*     */     {
/* 380 */       addAct = new COSDictionary();
/* 381 */       this.root.setItem(COSName.AA, addAct);
/*     */     }
/* 383 */     return new PDDocumentCatalogAdditionalActions(addAct);
/*     */   }
/*     */ 
/*     */   public void setActions(PDDocumentCatalogAdditionalActions actions)
/*     */   {
/* 393 */     this.root.setItem(COSName.AA, actions);
/*     */   }
/*     */ 
/*     */   public PDDocumentNameDictionary getNames()
/*     */   {
/* 401 */     PDDocumentNameDictionary nameDic = null;
/* 402 */     COSDictionary names = (COSDictionary)this.root.getDictionaryObject(COSName.NAMES);
/* 403 */     if (names != null)
/*     */     {
/* 405 */       nameDic = new PDDocumentNameDictionary(this, names);
/*     */     }
/* 407 */     return nameDic;
/*     */   }
/*     */ 
/*     */   public void setNames(PDDocumentNameDictionary names)
/*     */   {
/* 417 */     this.root.setItem(COSName.NAMES, names);
/*     */   }
/*     */ 
/*     */   public PDMarkInfo getMarkInfo()
/*     */   {
/* 428 */     PDMarkInfo retval = null;
/* 429 */     COSDictionary dic = (COSDictionary)this.root.getDictionaryObject(COSName.MARK_INFO);
/* 430 */     if (dic != null)
/*     */     {
/* 432 */       retval = new PDMarkInfo(dic);
/*     */     }
/* 434 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setMarkInfo(PDMarkInfo markInfo)
/*     */   {
/* 444 */     this.root.setItem(COSName.MARK_INFO, markInfo);
/*     */   }
/*     */ 
/*     */   public List<PDOutputIntent> getOutputIntent()
/*     */   {
/* 453 */     List retval = new ArrayList();
/* 454 */     COSArray array = (COSArray)this.root.getItem(COSName.OUTPUT_INTENTS);
/* 455 */     if (array != null) {
/* 456 */       for (COSBase cosBase : array)
/*     */       {
/* 458 */         PDOutputIntent oi = new PDOutputIntent((COSStream)cosBase);
/* 459 */         retval.add(oi);
/*     */       }
/*     */     }
/* 462 */     return retval;
/*     */   }
/*     */ 
/*     */   public void addOutputIntent(PDOutputIntent outputIntent)
/*     */   {
/* 474 */     COSArray array = (COSArray)this.root.getItem(COSName.OUTPUT_INTENTS);
/* 475 */     if (array == null) {
/* 476 */       array = new COSArray();
/* 477 */       this.root.setItem(COSName.OUTPUT_INTENTS, array);
/*     */     }
/* 479 */     array.add(outputIntent.getCOSObject());
/*     */   }
/*     */ 
/*     */   public void setOutputIntents(List<PDOutputIntent> outputIntents)
/*     */   {
/* 489 */     COSArray array = new COSArray();
/* 490 */     for (PDOutputIntent intent : outputIntents)
/*     */     {
/* 492 */       array.add(intent.getCOSObject());
/*     */     }
/* 494 */     this.root.setItem(COSName.OUTPUT_INTENTS, array);
/*     */   }
/*     */ 
/*     */   public String getPageMode()
/*     */   {
/* 503 */     return this.root.getNameAsString(COSName.PAGE_MODE, "UseNone");
/*     */   }
/*     */ 
/*     */   public void setPageMode(String mode)
/*     */   {
/* 512 */     this.root.setName(COSName.PAGE_MODE, mode);
/*     */   }
/*     */ 
/*     */   public String getPageLayout()
/*     */   {
/* 521 */     return this.root.getNameAsString(COSName.PAGE_LAYOUT, "SinglePage");
/*     */   }
/*     */ 
/*     */   public void setPageLayout(String layout)
/*     */   {
/* 530 */     this.root.setName(COSName.PAGE_LAYOUT, layout);
/*     */   }
/*     */ 
/*     */   public PDURIDictionary getURI()
/*     */   {
/* 539 */     PDURIDictionary retval = null;
/* 540 */     COSDictionary uri = (COSDictionary)this.root.getDictionaryObject(COSName.URI);
/* 541 */     if (uri != null)
/*     */     {
/* 543 */       retval = new PDURIDictionary(uri);
/*     */     }
/* 545 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setURI(PDURIDictionary uri)
/*     */   {
/* 554 */     this.root.setItem(COSName.URI, uri);
/*     */   }
/*     */ 
/*     */   public PDStructureTreeRoot getStructureTreeRoot()
/*     */   {
/* 564 */     PDStructureTreeRoot treeRoot = null;
/* 565 */     COSDictionary dic = (COSDictionary)this.root.getDictionaryObject(COSName.STRUCT_TREE_ROOT);
/* 566 */     if (dic != null)
/*     */     {
/* 568 */       treeRoot = new PDStructureTreeRoot(dic);
/*     */     }
/* 570 */     return treeRoot;
/*     */   }
/*     */ 
/*     */   public void setStructureTreeRoot(PDStructureTreeRoot treeRoot)
/*     */   {
/* 580 */     this.root.setItem(COSName.STRUCT_TREE_ROOT, treeRoot);
/*     */   }
/*     */ 
/*     */   public String getLanguage()
/*     */   {
/* 590 */     return this.root.getString(COSName.LANG);
/*     */   }
/*     */ 
/*     */   public void setLanguage(String language)
/*     */   {
/* 600 */     this.root.setString(COSName.LANG, language);
/*     */   }
/*     */ 
/*     */   public String getVersion()
/*     */   {
/* 610 */     return this.root.getNameAsString(COSName.VERSION);
/*     */   }
/*     */ 
/*     */   public void setVersion(String version)
/*     */   {
/* 620 */     this.root.setName(COSName.VERSION, version);
/*     */   }
/*     */ 
/*     */   public PDPageLabels getPageLabels()
/*     */     throws IOException
/*     */   {
/* 632 */     PDPageLabels labels = null;
/* 633 */     COSDictionary dict = (COSDictionary)this.root.getDictionaryObject(COSName.PAGE_LABELS);
/* 634 */     if (dict != null)
/*     */     {
/* 636 */       labels = new PDPageLabels(this.document, dict);
/*     */     }
/* 638 */     return labels;
/*     */   }
/*     */ 
/*     */   public void setPageLabels(PDPageLabels labels)
/*     */   {
/* 648 */     this.root.setItem(COSName.PAGE_LABELS, labels);
/*     */   }
/*     */ 
/*     */   public PDOptionalContentProperties getOCProperties()
/*     */   {
/* 659 */     PDOptionalContentProperties retval = null;
/* 660 */     COSDictionary dict = (COSDictionary)this.root.getDictionaryObject(COSName.OCPROPERTIES);
/* 661 */     if (dict != null)
/*     */     {
/* 663 */       retval = new PDOptionalContentProperties(dict);
/*     */     }
/*     */ 
/* 666 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setOCProperties(PDOptionalContentProperties ocProperties)
/*     */   {
/* 678 */     this.root.setItem(COSName.OCPROPERTIES, ocProperties);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.PDDocumentCatalog
 * JD-Core Version:    0.6.2
 */