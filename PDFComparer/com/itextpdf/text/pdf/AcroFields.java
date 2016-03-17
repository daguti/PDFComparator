/*      */ package com.itextpdf.text.pdf;
/*      */ 
/*      */ import com.itextpdf.text.BaseColor;
/*      */ import com.itextpdf.text.DocumentException;
/*      */ import com.itextpdf.text.ExceptionConverter;
/*      */ import com.itextpdf.text.Image;
/*      */ import com.itextpdf.text.Rectangle;
/*      */ import com.itextpdf.text.error_messages.MessageLocalization;
/*      */ import com.itextpdf.text.io.RASInputStream;
/*      */ import com.itextpdf.text.io.RandomAccessSourceFactory;
/*      */ import com.itextpdf.text.io.WindowRandomAccessSource;
/*      */ import com.itextpdf.text.pdf.codec.Base64;
/*      */ import com.itextpdf.text.pdf.security.PdfPKCS7;
/*      */ import com.itextpdf.text.xml.XmlToTxt;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Set;
/*      */ import org.w3c.dom.Node;
/*      */ 
/*      */ public class AcroFields
/*      */ {
/*      */   PdfReader reader;
/*      */   PdfWriter writer;
/*      */   Map<String, Item> fields;
/*      */   private int topFirst;
/*      */   private HashMap<String, int[]> sigNames;
/*      */   private boolean append;
/*      */   public static final int DA_FONT = 0;
/*      */   public static final int DA_SIZE = 1;
/*      */   public static final int DA_COLOR = 2;
/*   81 */   private HashMap<Integer, BaseFont> extensionFonts = new HashMap();
/*      */   private XfaForm xfa;
/*      */   public static final int FIELD_TYPE_NONE = 0;
/*      */   public static final int FIELD_TYPE_PUSHBUTTON = 1;
/*      */   public static final int FIELD_TYPE_CHECKBOX = 2;
/*      */   public static final int FIELD_TYPE_RADIOBUTTON = 3;
/*      */   public static final int FIELD_TYPE_TEXT = 4;
/*      */   public static final int FIELD_TYPE_LIST = 5;
/*      */   public static final int FIELD_TYPE_COMBO = 6;
/*      */   public static final int FIELD_TYPE_SIGNATURE = 7;
/*      */   private boolean lastWasString;
/*  127 */   private boolean generateAppearances = true;
/*      */ 
/*  129 */   private HashMap<String, BaseFont> localFonts = new HashMap();
/*      */   private float extraMarginLeft;
/*      */   private float extraMarginTop;
/*      */   private ArrayList<BaseFont> substitutionFonts;
/*      */   private ArrayList<String> orderedSignatureNames;
/* 2509 */   private static final HashMap<String, String[]> stdFieldFontNames = new HashMap();
/*      */   private int totalRevisions;
/*      */   private Map<String, TextField> fieldCache;
/* 2599 */   private static final PdfName[] buttonRemove = { PdfName.MK, PdfName.F, PdfName.FF, PdfName.Q, PdfName.BS, PdfName.BORDER };
/*      */ 
/*      */   AcroFields(PdfReader reader, PdfWriter writer)
/*      */   {
/*  136 */     this.reader = reader;
/*  137 */     this.writer = writer;
/*      */     try {
/*  139 */       this.xfa = new XfaForm(reader);
/*      */     }
/*      */     catch (Exception e) {
/*  142 */       throw new ExceptionConverter(e);
/*      */     }
/*  144 */     if ((writer instanceof PdfStamperImp)) {
/*  145 */       this.append = ((PdfStamperImp)writer).isAppend();
/*      */     }
/*  147 */     fill();
/*      */   }
/*      */ 
/*      */   void fill() {
/*  151 */     this.fields = new HashMap();
/*  152 */     PdfDictionary top = (PdfDictionary)PdfReader.getPdfObjectRelease(this.reader.getCatalog().get(PdfName.ACROFORM));
/*  153 */     if (top == null)
/*  154 */       return;
/*  155 */     PdfBoolean needappearances = top.getAsBoolean(PdfName.NEEDAPPEARANCES);
/*  156 */     if ((needappearances == null) || (!needappearances.booleanValue()))
/*  157 */       setGenerateAppearances(true);
/*      */     else
/*  159 */       setGenerateAppearances(false);
/*  160 */     PdfArray arrfds = (PdfArray)PdfReader.getPdfObjectRelease(top.get(PdfName.FIELDS));
/*  161 */     if ((arrfds == null) || (arrfds.size() == 0))
/*  162 */       return;
/*  163 */     for (int k = 1; k <= this.reader.getNumberOfPages(); k++) {
/*  164 */       PdfDictionary page = this.reader.getPageNRelease(k);
/*  165 */       PdfArray annots = (PdfArray)PdfReader.getPdfObjectRelease(page.get(PdfName.ANNOTS), page);
/*  166 */       if (annots != null)
/*      */       {
/*  168 */         for (int j = 0; j < annots.size(); j++) {
/*  169 */           PdfDictionary annot = annots.getAsDict(j);
/*  170 */           if (annot == null) {
/*  171 */             PdfReader.releaseLastXrefPartial(annots.getAsIndirectObject(j));
/*      */           }
/*  174 */           else if (!PdfName.WIDGET.equals(annot.getAsName(PdfName.SUBTYPE))) {
/*  175 */             PdfReader.releaseLastXrefPartial(annots.getAsIndirectObject(j));
/*      */           }
/*      */           else {
/*  178 */             PdfDictionary widget = annot;
/*  179 */             PdfDictionary dic = new PdfDictionary();
/*  180 */             dic.putAll(annot);
/*  181 */             String name = "";
/*  182 */             PdfDictionary value = null;
/*  183 */             PdfObject lastV = null;
/*  184 */             while (annot != null) {
/*  185 */               dic.mergeDifferent(annot);
/*  186 */               PdfString t = annot.getAsString(PdfName.T);
/*  187 */               if (t != null)
/*  188 */                 name = t.toUnicodeString() + "." + name;
/*  189 */               if ((lastV == null) && (annot.get(PdfName.V) != null))
/*  190 */                 lastV = PdfReader.getPdfObjectRelease(annot.get(PdfName.V));
/*  191 */               if ((value == null) && (t != null)) {
/*  192 */                 value = annot;
/*  193 */                 if ((annot.get(PdfName.V) == null) && (lastV != null))
/*  194 */                   value.put(PdfName.V, lastV);
/*      */               }
/*  196 */               annot = annot.getAsDict(PdfName.PARENT);
/*      */             }
/*  198 */             if (name.length() > 0)
/*  199 */               name = name.substring(0, name.length() - 1);
/*  200 */             Item item = (Item)this.fields.get(name);
/*  201 */             if (item == null) {
/*  202 */               item = new Item();
/*  203 */               this.fields.put(name, item);
/*      */             }
/*  205 */             if (value == null)
/*  206 */               item.addValue(widget);
/*      */             else
/*  208 */               item.addValue(value);
/*  209 */             item.addWidget(widget);
/*  210 */             item.addWidgetRef(annots.getAsIndirectObject(j));
/*  211 */             if (top != null)
/*  212 */               dic.mergeDifferent(top);
/*  213 */             item.addMerged(dic);
/*  214 */             item.addPage(k);
/*  215 */             item.addTabOrder(j);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  220 */     PdfNumber sigFlags = top.getAsNumber(PdfName.SIGFLAGS);
/*  221 */     if ((sigFlags == null) || ((sigFlags.intValue() & 0x1) != 1))
/*  222 */       return;
/*  223 */     for (int j = 0; j < arrfds.size(); j++) {
/*  224 */       PdfDictionary annot = arrfds.getAsDict(j);
/*  225 */       if (annot == null) {
/*  226 */         PdfReader.releaseLastXrefPartial(arrfds.getAsIndirectObject(j));
/*      */       }
/*  229 */       else if (!PdfName.WIDGET.equals(annot.getAsName(PdfName.SUBTYPE))) {
/*  230 */         PdfReader.releaseLastXrefPartial(arrfds.getAsIndirectObject(j));
/*      */       }
/*      */       else {
/*  233 */         PdfArray kids = (PdfArray)PdfReader.getPdfObjectRelease(annot.get(PdfName.KIDS));
/*  234 */         if (kids == null)
/*      */         {
/*  236 */           PdfDictionary dic = new PdfDictionary();
/*  237 */           dic.putAll(annot);
/*  238 */           PdfString t = annot.getAsString(PdfName.T);
/*  239 */           if (t != null)
/*      */           {
/*  241 */             String name = t.toUnicodeString();
/*  242 */             if (!this.fields.containsKey(name))
/*      */             {
/*  244 */               Item item = new Item();
/*  245 */               this.fields.put(name, item);
/*  246 */               item.addValue(dic);
/*  247 */               item.addWidget(dic);
/*  248 */               item.addWidgetRef(arrfds.getAsIndirectObject(j));
/*  249 */               item.addMerged(dic);
/*  250 */               item.addPage(-1);
/*  251 */               item.addTabOrder(-1);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public String[] getAppearanceStates(String fieldName)
/*      */   {
/*  268 */     Item fd = (Item)this.fields.get(fieldName);
/*  269 */     if (fd == null)
/*  270 */       return null;
/*  271 */     HashSet names = new LinkedHashSet();
/*  272 */     PdfDictionary vals = fd.getValue(0);
/*  273 */     PdfString stringOpt = vals.getAsString(PdfName.OPT);
/*      */ 
/*  276 */     if (stringOpt != null) {
/*  277 */       names.add(stringOpt.toUnicodeString());
/*      */     }
/*      */     else {
/*  280 */       PdfArray arrayOpt = vals.getAsArray(PdfName.OPT);
/*  281 */       if (arrayOpt != null) {
/*  282 */         for (int k = 0; k < arrayOpt.size(); k++) {
/*  283 */           PdfObject pdfObject = arrayOpt.getDirectObject(k);
/*  284 */           PdfString valStr = null;
/*      */ 
/*  286 */           switch (pdfObject.type()) {
/*      */           case 5:
/*  288 */             PdfArray pdfArray = (PdfArray)pdfObject;
/*  289 */             valStr = pdfArray.getAsString(1);
/*  290 */             break;
/*      */           case 3:
/*  292 */             valStr = (PdfString)pdfObject;
/*      */           }
/*      */ 
/*  296 */           if (valStr != null)
/*  297 */             names.add(valStr.toUnicodeString());
/*      */         }
/*      */       }
/*      */     }
/*  301 */     for (int k = 0; k < fd.size(); k++) {
/*  302 */       PdfDictionary dic = fd.getWidget(k);
/*  303 */       dic = dic.getAsDict(PdfName.AP);
/*  304 */       if (dic != null)
/*      */       {
/*  306 */         dic = dic.getAsDict(PdfName.N);
/*  307 */         if (dic != null)
/*      */         {
/*  309 */           for (Object element : dic.getKeys()) {
/*  310 */             String name = PdfName.decodeName(((PdfName)element).toString());
/*  311 */             names.add(name);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  314 */     String[] out = new String[names.size()];
/*  315 */     return (String[])names.toArray(out);
/*      */   }
/*      */ 
/*      */   private String[] getListOption(String fieldName, int idx) {
/*  319 */     Item fd = getFieldItem(fieldName);
/*  320 */     if (fd == null)
/*  321 */       return null;
/*  322 */     PdfArray ar = fd.getMerged(0).getAsArray(PdfName.OPT);
/*  323 */     if (ar == null)
/*  324 */       return null;
/*  325 */     String[] ret = new String[ar.size()];
/*  326 */     for (int k = 0; k < ar.size(); k++) {
/*  327 */       PdfObject obj = ar.getDirectObject(k);
/*      */       try {
/*  329 */         if (obj.isArray()) {
/*  330 */           obj = ((PdfArray)obj).getDirectObject(idx);
/*      */         }
/*  332 */         if (obj.isString())
/*  333 */           ret[k] = ((PdfString)obj).toUnicodeString();
/*      */         else
/*  335 */           ret[k] = obj.toString();
/*      */       }
/*      */       catch (Exception e) {
/*  338 */         ret[k] = "";
/*      */       }
/*      */     }
/*  341 */     return ret;
/*      */   }
/*      */ 
/*      */   public String[] getListOptionExport(String fieldName)
/*      */   {
/*  353 */     return getListOption(fieldName, 0);
/*      */   }
/*      */ 
/*      */   public String[] getListOptionDisplay(String fieldName)
/*      */   {
/*  365 */     return getListOption(fieldName, 1);
/*      */   }
/*      */ 
/*      */   public boolean setListOption(String fieldName, String[] exportValues, String[] displayValues)
/*      */   {
/*  391 */     if ((exportValues == null) && (displayValues == null))
/*  392 */       return false;
/*  393 */     if ((exportValues != null) && (displayValues != null) && (exportValues.length != displayValues.length))
/*  394 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.export.and.the.display.array.must.have.the.same.size", new Object[0]));
/*  395 */     int ftype = getFieldType(fieldName);
/*  396 */     if ((ftype != 6) && (ftype != 5))
/*  397 */       return false;
/*  398 */     Item fd = (Item)this.fields.get(fieldName);
/*  399 */     String[] sing = null;
/*  400 */     if ((exportValues == null) && (displayValues != null))
/*  401 */       sing = displayValues;
/*  402 */     else if ((exportValues != null) && (displayValues == null))
/*  403 */       sing = exportValues;
/*  404 */     PdfArray opt = new PdfArray();
/*  405 */     if (sing != null) {
/*  406 */       for (int k = 0; k < sing.length; k++)
/*  407 */         opt.add(new PdfString(sing[k], "UnicodeBig"));
/*      */     }
/*      */     else {
/*  410 */       for (int k = 0; k < exportValues.length; k++) {
/*  411 */         PdfArray a = new PdfArray();
/*  412 */         a.add(new PdfString(exportValues[k], "UnicodeBig"));
/*  413 */         a.add(new PdfString(displayValues[k], "UnicodeBig"));
/*  414 */         opt.add(a);
/*      */       }
/*      */     }
/*  417 */     fd.writeToAll(PdfName.OPT, opt, 5);
/*  418 */     return true;
/*      */   }
/*      */ 
/*      */   public int getFieldType(String fieldName)
/*      */   {
/*  434 */     Item fd = getFieldItem(fieldName);
/*  435 */     if (fd == null)
/*  436 */       return 0;
/*  437 */     PdfDictionary merged = fd.getMerged(0);
/*  438 */     PdfName type = merged.getAsName(PdfName.FT);
/*  439 */     if (type == null)
/*  440 */       return 0;
/*  441 */     int ff = 0;
/*  442 */     PdfNumber ffo = merged.getAsNumber(PdfName.FF);
/*  443 */     if (ffo != null) {
/*  444 */       ff = ffo.intValue();
/*      */     }
/*  446 */     if (PdfName.BTN.equals(type)) {
/*  447 */       if ((ff & 0x10000) != 0)
/*  448 */         return 1;
/*  449 */       if ((ff & 0x8000) != 0) {
/*  450 */         return 3;
/*      */       }
/*  452 */       return 2;
/*      */     }
/*  454 */     if (PdfName.TX.equals(type)) {
/*  455 */       return 4;
/*      */     }
/*  457 */     if (PdfName.CH.equals(type)) {
/*  458 */       if ((ff & 0x20000) != 0) {
/*  459 */         return 6;
/*      */       }
/*  461 */       return 5;
/*      */     }
/*  463 */     if (PdfName.SIG.equals(type)) {
/*  464 */       return 7;
/*      */     }
/*  466 */     return 0;
/*      */   }
/*      */ 
/*      */   public void exportAsFdf(FdfWriter writer)
/*      */   {
/*  475 */     for (Map.Entry entry : this.fields.entrySet()) {
/*  476 */       Item item = (Item)entry.getValue();
/*  477 */       String name = (String)entry.getKey();
/*  478 */       PdfObject v = item.getMerged(0).get(PdfName.V);
/*  479 */       if (v != null)
/*      */       {
/*  481 */         String value = getField(name);
/*  482 */         if (this.lastWasString)
/*  483 */           writer.setFieldAsString(name, value);
/*      */         else
/*  485 */           writer.setFieldAsName(name, value);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean renameField(String oldName, String newName)
/*      */   {
/*  499 */     int idx1 = oldName.lastIndexOf('.') + 1;
/*  500 */     int idx2 = newName.lastIndexOf('.') + 1;
/*  501 */     if (idx1 != idx2)
/*  502 */       return false;
/*  503 */     if (!oldName.substring(0, idx1).equals(newName.substring(0, idx2)))
/*  504 */       return false;
/*  505 */     if (this.fields.containsKey(newName))
/*  506 */       return false;
/*  507 */     Item item = (Item)this.fields.get(oldName);
/*  508 */     if (item == null)
/*  509 */       return false;
/*  510 */     newName = newName.substring(idx2);
/*  511 */     PdfString ss = new PdfString(newName, "UnicodeBig");
/*      */ 
/*  513 */     item.writeToAll(PdfName.T, ss, 5);
/*  514 */     item.markUsed(this, 4);
/*      */ 
/*  516 */     this.fields.remove(oldName);
/*  517 */     this.fields.put(newName, item);
/*      */ 
/*  519 */     return true;
/*      */   }
/*      */ 
/*      */   public static Object[] splitDAelements(String da) {
/*      */     try {
/*  524 */       PRTokeniser tk = new PRTokeniser(new RandomAccessFileOrArray(new RandomAccessSourceFactory().createSource(PdfEncodings.convertToBytes(da, null))));
/*  525 */       ArrayList stack = new ArrayList();
/*  526 */       Object[] ret = new Object[3];
/*  527 */       while (tk.nextToken())
/*  528 */         if (tk.getTokenType() != PRTokeniser.TokenType.COMMENT)
/*      */         {
/*  530 */           if (tk.getTokenType() == PRTokeniser.TokenType.OTHER) {
/*  531 */             String operator = tk.getStringValue();
/*  532 */             if (operator.equals("Tf")) {
/*  533 */               if (stack.size() >= 2) {
/*  534 */                 ret[0] = stack.get(stack.size() - 2);
/*  535 */                 ret[1] = new Float((String)stack.get(stack.size() - 1));
/*      */               }
/*      */             }
/*  538 */             else if (operator.equals("g")) {
/*  539 */               if (stack.size() >= 1) {
/*  540 */                 float gray = new Float((String)stack.get(stack.size() - 1)).floatValue();
/*  541 */                 if (gray != 0.0F)
/*  542 */                   ret[2] = new GrayColor(gray);
/*      */               }
/*      */             }
/*  545 */             else if (operator.equals("rg")) {
/*  546 */               if (stack.size() >= 3) {
/*  547 */                 float red = new Float((String)stack.get(stack.size() - 3)).floatValue();
/*  548 */                 float green = new Float((String)stack.get(stack.size() - 2)).floatValue();
/*  549 */                 float blue = new Float((String)stack.get(stack.size() - 1)).floatValue();
/*  550 */                 ret[2] = new BaseColor(red, green, blue);
/*      */               }
/*      */             }
/*  553 */             else if ((operator.equals("k")) && 
/*  554 */               (stack.size() >= 4)) {
/*  555 */               float cyan = new Float((String)stack.get(stack.size() - 4)).floatValue();
/*  556 */               float magenta = new Float((String)stack.get(stack.size() - 3)).floatValue();
/*  557 */               float yellow = new Float((String)stack.get(stack.size() - 2)).floatValue();
/*  558 */               float black = new Float((String)stack.get(stack.size() - 1)).floatValue();
/*  559 */               ret[2] = new CMYKColor(cyan, magenta, yellow, black);
/*      */             }
/*      */ 
/*  562 */             stack.clear();
/*      */           }
/*      */           else {
/*  565 */             stack.add(tk.getStringValue());
/*      */           }
/*      */         }
/*  567 */       return ret;
/*      */     }
/*      */     catch (IOException ioe) {
/*  570 */       throw new ExceptionConverter(ioe);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void decodeGenericDictionary(PdfDictionary merged, BaseField tx) throws IOException, DocumentException {
/*  575 */     int flags = 0;
/*      */ 
/*  577 */     PdfString da = merged.getAsString(PdfName.DA);
/*  578 */     if (da != null) {
/*  579 */       Object[] dab = splitDAelements(da.toUnicodeString());
/*  580 */       if (dab[1] != null)
/*  581 */         tx.setFontSize(((Float)dab[1]).floatValue());
/*  582 */       if (dab[2] != null)
/*  583 */         tx.setTextColor((BaseColor)dab[2]);
/*  584 */       if (dab[0] != null) {
/*  585 */         PdfDictionary font = merged.getAsDict(PdfName.DR);
/*  586 */         if (font != null) {
/*  587 */           font = font.getAsDict(PdfName.FONT);
/*  588 */           if (font != null) {
/*  589 */             PdfObject po = font.get(new PdfName((String)dab[0]));
/*  590 */             if ((po != null) && (po.type() == 10)) {
/*  591 */               PRIndirectReference por = (PRIndirectReference)po;
/*  592 */               BaseFont bp = new DocumentFont((PRIndirectReference)po);
/*  593 */               tx.setFont(bp);
/*  594 */               Integer porkey = Integer.valueOf(por.getNumber());
/*  595 */               BaseFont porf = (BaseFont)this.extensionFonts.get(porkey);
/*  596 */               if ((porf == null) && 
/*  597 */                 (!this.extensionFonts.containsKey(porkey))) {
/*  598 */                 PdfDictionary fo = (PdfDictionary)PdfReader.getPdfObject(po);
/*  599 */                 PdfDictionary fd = fo.getAsDict(PdfName.FONTDESCRIPTOR);
/*  600 */                 if (fd != null) {
/*  601 */                   PRStream prs = (PRStream)PdfReader.getPdfObject(fd.get(PdfName.FONTFILE2));
/*  602 */                   if (prs == null)
/*  603 */                     prs = (PRStream)PdfReader.getPdfObject(fd.get(PdfName.FONTFILE3));
/*  604 */                   if (prs == null) {
/*  605 */                     this.extensionFonts.put(porkey, null);
/*      */                   }
/*      */                   else {
/*      */                     try {
/*  609 */                       porf = BaseFont.createFont("font.ttf", "Identity-H", true, false, PdfReader.getStreamBytes(prs), null);
/*      */                     }
/*      */                     catch (Exception e) {
/*      */                     }
/*  613 */                     this.extensionFonts.put(porkey, porf);
/*      */                   }
/*      */                 }
/*      */               }
/*      */ 
/*  618 */               if ((tx instanceof TextField))
/*  619 */                 ((TextField)tx).setExtensionFont(porf);
/*      */             }
/*      */             else {
/*  622 */               BaseFont bf = (BaseFont)this.localFonts.get(dab[0]);
/*  623 */               if (bf == null) {
/*  624 */                 String[] fn = (String[])stdFieldFontNames.get(dab[0]);
/*  625 */                 if (fn != null)
/*      */                   try {
/*  627 */                     String enc = "winansi";
/*  628 */                     if (fn.length > 1)
/*  629 */                       enc = fn[1];
/*  630 */                     bf = BaseFont.createFont(fn[0], enc, false);
/*  631 */                     tx.setFont(bf);
/*      */                   }
/*      */                   catch (Exception e)
/*      */                   {
/*      */                   }
/*      */               }
/*      */               else
/*      */               {
/*  639 */                 tx.setFont(bf);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  646 */     PdfDictionary mk = merged.getAsDict(PdfName.MK);
/*  647 */     if (mk != null) {
/*  648 */       PdfArray ar = mk.getAsArray(PdfName.BC);
/*  649 */       BaseColor border = getMKColor(ar);
/*  650 */       tx.setBorderColor(border);
/*  651 */       if (border != null)
/*  652 */         tx.setBorderWidth(1.0F);
/*  653 */       ar = mk.getAsArray(PdfName.BG);
/*  654 */       tx.setBackgroundColor(getMKColor(ar));
/*  655 */       PdfNumber rotation = mk.getAsNumber(PdfName.R);
/*  656 */       if (rotation != null) {
/*  657 */         tx.setRotation(rotation.intValue());
/*      */       }
/*      */     }
/*  660 */     PdfNumber nfl = merged.getAsNumber(PdfName.F);
/*  661 */     flags = 0;
/*  662 */     tx.setVisibility(2);
/*  663 */     if (nfl != null) {
/*  664 */       flags = nfl.intValue();
/*  665 */       if (((flags & 0x4) != 0) && ((flags & 0x2) != 0))
/*  666 */         tx.setVisibility(1);
/*  667 */       else if (((flags & 0x4) != 0) && ((flags & 0x20) != 0))
/*  668 */         tx.setVisibility(3);
/*  669 */       else if ((flags & 0x4) != 0) {
/*  670 */         tx.setVisibility(0);
/*      */       }
/*      */     }
/*  673 */     nfl = merged.getAsNumber(PdfName.FF);
/*  674 */     flags = 0;
/*  675 */     if (nfl != null)
/*  676 */       flags = nfl.intValue();
/*  677 */     tx.setOptions(flags);
/*  678 */     if ((flags & 0x1000000) != 0) {
/*  679 */       PdfNumber maxLen = merged.getAsNumber(PdfName.MAXLEN);
/*  680 */       int len = 0;
/*  681 */       if (maxLen != null)
/*  682 */         len = maxLen.intValue();
/*  683 */       tx.setMaxCharacterLength(len);
/*      */     }
/*      */ 
/*  686 */     nfl = merged.getAsNumber(PdfName.Q);
/*  687 */     if (nfl != null) {
/*  688 */       if (nfl.intValue() == 1)
/*  689 */         tx.setAlignment(1);
/*  690 */       else if (nfl.intValue() == 2) {
/*  691 */         tx.setAlignment(2);
/*      */       }
/*      */     }
/*  694 */     PdfDictionary bs = merged.getAsDict(PdfName.BS);
/*  695 */     if (bs != null) {
/*  696 */       PdfNumber w = bs.getAsNumber(PdfName.W);
/*  697 */       if (w != null)
/*  698 */         tx.setBorderWidth(w.floatValue());
/*  699 */       PdfName s = bs.getAsName(PdfName.S);
/*  700 */       if (PdfName.D.equals(s))
/*  701 */         tx.setBorderStyle(1);
/*  702 */       else if (PdfName.B.equals(s))
/*  703 */         tx.setBorderStyle(2);
/*  704 */       else if (PdfName.I.equals(s))
/*  705 */         tx.setBorderStyle(3);
/*  706 */       else if (PdfName.U.equals(s))
/*  707 */         tx.setBorderStyle(4);
/*      */     }
/*      */     else {
/*  710 */       PdfArray bd = merged.getAsArray(PdfName.BORDER);
/*  711 */       if (bd != null) {
/*  712 */         if (bd.size() >= 3)
/*  713 */           tx.setBorderWidth(bd.getAsNumber(2).floatValue());
/*  714 */         if (bd.size() >= 4)
/*  715 */           tx.setBorderStyle(1);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   PdfAppearance getAppearance(PdfDictionary merged, String[] values, String fieldName) throws IOException, DocumentException {
/*  721 */     this.topFirst = 0;
/*  722 */     String text = values.length > 0 ? values[0] : null;
/*      */ 
/*  724 */     TextField tx = null;
/*  725 */     if ((this.fieldCache == null) || (!this.fieldCache.containsKey(fieldName))) {
/*  726 */       tx = new TextField(this.writer, null, null);
/*  727 */       tx.setExtraMargin(this.extraMarginLeft, this.extraMarginTop);
/*  728 */       tx.setBorderWidth(0.0F);
/*  729 */       tx.setSubstitutionFonts(this.substitutionFonts);
/*  730 */       decodeGenericDictionary(merged, tx);
/*      */ 
/*  732 */       PdfArray rect = merged.getAsArray(PdfName.RECT);
/*  733 */       Rectangle box = PdfReader.getNormalizedRectangle(rect);
/*  734 */       if ((tx.getRotation() == 90) || (tx.getRotation() == 270))
/*  735 */         box = box.rotate();
/*  736 */       tx.setBox(box);
/*  737 */       if (this.fieldCache != null)
/*  738 */         this.fieldCache.put(fieldName, tx);
/*      */     }
/*      */     else {
/*  741 */       tx = (TextField)this.fieldCache.get(fieldName);
/*  742 */       tx.setWriter(this.writer);
/*      */     }
/*  744 */     PdfName fieldType = merged.getAsName(PdfName.FT);
/*  745 */     if (PdfName.TX.equals(fieldType)) {
/*  746 */       if ((values.length > 0) && (values[0] != null)) {
/*  747 */         tx.setText(values[0]);
/*      */       }
/*  749 */       return tx.getAppearance();
/*      */     }
/*  751 */     if (!PdfName.CH.equals(fieldType))
/*  752 */       throw new DocumentException(MessageLocalization.getComposedMessage("an.appearance.was.requested.without.a.variable.text.field", new Object[0]));
/*  753 */     PdfArray opt = merged.getAsArray(PdfName.OPT);
/*  754 */     int flags = 0;
/*  755 */     PdfNumber nfl = merged.getAsNumber(PdfName.FF);
/*  756 */     if (nfl != null)
/*  757 */       flags = nfl.intValue();
/*  758 */     if (((flags & 0x20000) != 0) && (opt == null)) {
/*  759 */       tx.setText(text);
/*  760 */       return tx.getAppearance();
/*      */     }
/*  762 */     if (opt != null) {
/*  763 */       String[] choices = new String[opt.size()];
/*  764 */       String[] choicesExp = new String[opt.size()];
/*  765 */       for (int k = 0; k < opt.size(); k++) {
/*  766 */         PdfObject obj = opt.getPdfObject(k);
/*  767 */         if (obj.isString())
/*      */         {
/*      */           String tmp393_390 = ((PdfString)obj).toUnicodeString(); choicesExp[k] = tmp393_390; choices[k] = tmp393_390;
/*      */         }
/*      */         else {
/*  771 */           PdfArray a = (PdfArray)obj;
/*  772 */           choicesExp[k] = a.getAsString(0).toUnicodeString();
/*  773 */           choices[k] = a.getAsString(1).toUnicodeString();
/*      */         }
/*      */       }
/*  776 */       if ((flags & 0x20000) != 0) {
/*  777 */         for (int k = 0; k < choices.length; k++) {
/*  778 */           if (text.equals(choicesExp[k])) {
/*  779 */             text = choices[k];
/*  780 */             break;
/*      */           }
/*      */         }
/*  783 */         tx.setText(text);
/*  784 */         return tx.getAppearance();
/*      */       }
/*  786 */       ArrayList indexes = new ArrayList();
/*  787 */       for (int k = 0; k < choicesExp.length; k++) {
/*  788 */         for (int j = 0; j < values.length; j++) {
/*  789 */           String val = values[j];
/*  790 */           if ((val != null) && (val.equals(choicesExp[k]))) {
/*  791 */             indexes.add(Integer.valueOf(k));
/*  792 */             break;
/*      */           }
/*      */         }
/*      */       }
/*  796 */       tx.setChoices(choices);
/*  797 */       tx.setChoiceExports(choicesExp);
/*  798 */       tx.setChoiceSelections(indexes);
/*      */     }
/*  800 */     PdfAppearance app = tx.getListAppearance();
/*  801 */     this.topFirst = tx.getTopFirst();
/*  802 */     return app;
/*      */   }
/*      */ 
/*      */   PdfAppearance getAppearance(PdfDictionary merged, String text, String fieldName) throws IOException, DocumentException {
/*  806 */     String[] valueArr = new String[1];
/*  807 */     valueArr[0] = text;
/*  808 */     return getAppearance(merged, valueArr, fieldName);
/*      */   }
/*      */ 
/*      */   BaseColor getMKColor(PdfArray ar) {
/*  812 */     if (ar == null)
/*  813 */       return null;
/*  814 */     switch (ar.size()) {
/*      */     case 1:
/*  816 */       return new GrayColor(ar.getAsNumber(0).floatValue());
/*      */     case 3:
/*  818 */       return new BaseColor(ExtendedColor.normalize(ar.getAsNumber(0).floatValue()), ExtendedColor.normalize(ar.getAsNumber(1).floatValue()), ExtendedColor.normalize(ar.getAsNumber(2).floatValue()));
/*      */     case 4:
/*  820 */       return new CMYKColor(ar.getAsNumber(0).floatValue(), ar.getAsNumber(1).floatValue(), ar.getAsNumber(2).floatValue(), ar.getAsNumber(3).floatValue());
/*      */     case 2:
/*  822 */     }return null;
/*      */   }
/*      */ 
/*      */   public String getFieldRichValue(String name)
/*      */   {
/*  833 */     if (this.xfa.isXfaPresent()) {
/*  834 */       return null;
/*      */     }
/*      */ 
/*  837 */     Item item = (Item)this.fields.get(name);
/*  838 */     if (item == null) {
/*  839 */       return null;
/*      */     }
/*      */ 
/*  842 */     PdfDictionary merged = item.getMerged(0);
/*  843 */     PdfString rich = merged.getAsString(PdfName.RV);
/*      */ 
/*  845 */     String markup = null;
/*  846 */     if (rich != null) {
/*  847 */       markup = rich.toString();
/*      */     }
/*      */ 
/*  850 */     return markup;
/*      */   }
/*      */ 
/*      */   public String getField(String name)
/*      */   {
/*  859 */     if (this.xfa.isXfaPresent()) {
/*  860 */       name = this.xfa.findFieldName(name, this);
/*  861 */       if (name == null)
/*  862 */         return null;
/*  863 */       name = XfaForm.Xml2Som.getShortName(name);
/*  864 */       return XfaForm.getNodeText(this.xfa.findDatasetsNode(name));
/*      */     }
/*  866 */     Item item = (Item)this.fields.get(name);
/*  867 */     if (item == null)
/*  868 */       return null;
/*  869 */     this.lastWasString = false;
/*  870 */     PdfDictionary mergedDict = item.getMerged(0);
/*      */ 
/*  875 */     PdfObject v = PdfReader.getPdfObject(mergedDict.get(PdfName.V));
/*  876 */     if (v == null)
/*  877 */       return "";
/*  878 */     if ((v instanceof PRStream)) {
/*      */       try
/*      */       {
/*  881 */         byte[] valBytes = PdfReader.getStreamBytes((PRStream)v);
/*  882 */         return new String(valBytes);
/*      */       } catch (IOException e) {
/*  884 */         throw new ExceptionConverter(e);
/*      */       }
/*      */     }
/*      */ 
/*  888 */     PdfName type = mergedDict.getAsName(PdfName.FT);
/*  889 */     if (PdfName.BTN.equals(type)) {
/*  890 */       PdfNumber ff = mergedDict.getAsNumber(PdfName.FF);
/*  891 */       int flags = 0;
/*  892 */       if (ff != null)
/*  893 */         flags = ff.intValue();
/*  894 */       if ((flags & 0x10000) != 0)
/*  895 */         return "";
/*  896 */       String value = "";
/*  897 */       if ((v instanceof PdfName))
/*  898 */         value = PdfName.decodeName(v.toString());
/*  899 */       else if ((v instanceof PdfString))
/*  900 */         value = ((PdfString)v).toUnicodeString();
/*  901 */       PdfArray opts = item.getValue(0).getAsArray(PdfName.OPT);
/*  902 */       if (opts != null) {
/*  903 */         int idx = 0;
/*      */         try {
/*  905 */           idx = Integer.parseInt(value);
/*  906 */           PdfString ps = opts.getAsString(idx);
/*  907 */           value = ps.toUnicodeString();
/*  908 */           this.lastWasString = true;
/*      */         }
/*      */         catch (Exception e) {
/*      */         }
/*      */       }
/*  913 */       return value;
/*      */     }
/*  915 */     if ((v instanceof PdfString)) {
/*  916 */       this.lastWasString = true;
/*  917 */       return ((PdfString)v).toUnicodeString();
/*  918 */     }if ((v instanceof PdfName)) {
/*  919 */       return PdfName.decodeName(v.toString());
/*      */     }
/*  921 */     return "";
/*      */   }
/*      */ 
/*      */   public String[] getListSelection(String name)
/*      */   {
/*  933 */     String s = getField(name);
/*      */     String[] ret;
/*  934 */     if (s == null) {
/*  935 */       ret = new String[0];
/*      */     }
/*      */     else {
/*  938 */       ret = new String[] { s };
/*      */     }
/*  940 */     Item item = (Item)this.fields.get(name);
/*  941 */     if (item == null) {
/*  942 */       return ret;
/*      */     }
/*      */ 
/*  947 */     PdfArray values = item.getMerged(0).getAsArray(PdfName.I);
/*  948 */     if (values == null)
/*  949 */       return ret;
/*  950 */     String[] ret = new String[values.size()];
/*  951 */     String[] options = getListOptionExport(name);
/*      */ 
/*  953 */     int idx = 0;
/*  954 */     for (Iterator i = values.listIterator(); i.hasNext(); ) {
/*  955 */       PdfNumber n = (PdfNumber)i.next();
/*  956 */       ret[(idx++)] = options[n.intValue()];
/*      */     }
/*  958 */     return ret;
/*      */   }
/*      */ 
/*      */   public boolean setFieldProperty(String field, String name, Object value, int[] inst)
/*      */   {
/*  983 */     if (this.writer == null)
/*  984 */       throw new RuntimeException(MessageLocalization.getComposedMessage("this.acrofields.instance.is.read.only", new Object[0]));
/*      */     try {
/*  986 */       Item item = (Item)this.fields.get(field);
/*  987 */       if (item == null)
/*  988 */         return false;
/*  989 */       InstHit hit = new InstHit(inst);
/*      */ 
/*  992 */       if (name.equalsIgnoreCase("textfont")) {
/*  993 */         for (int k = 0; k < item.size(); k++) {
/*  994 */           if (hit.isHit(k)) {
/*  995 */             PdfDictionary merged = item.getMerged(k);
/*  996 */             PdfString da = merged.getAsString(PdfName.DA);
/*  997 */             PdfDictionary dr = merged.getAsDict(PdfName.DR);
/*  998 */             if (da != null) {
/*  999 */               if (dr == null) {
/* 1000 */                 dr = new PdfDictionary();
/* 1001 */                 merged.put(PdfName.DR, dr);
/*      */               }
/* 1003 */               Object[] dao = splitDAelements(da.toUnicodeString());
/* 1004 */               PdfAppearance cb = new PdfAppearance();
/* 1005 */               if (dao[0] != null) {
/* 1006 */                 BaseFont bf = (BaseFont)value;
/* 1007 */                 PdfName psn = (PdfName)PdfAppearance.stdFieldFontNames.get(bf.getPostscriptFontName());
/* 1008 */                 if (psn == null) {
/* 1009 */                   psn = new PdfName(bf.getPostscriptFontName());
/*      */                 }
/* 1011 */                 PdfDictionary fonts = dr.getAsDict(PdfName.FONT);
/* 1012 */                 if (fonts == null) {
/* 1013 */                   fonts = new PdfDictionary();
/* 1014 */                   dr.put(PdfName.FONT, fonts);
/*      */                 }
/* 1016 */                 PdfIndirectReference fref = (PdfIndirectReference)fonts.get(psn);
/* 1017 */                 PdfDictionary top = this.reader.getCatalog().getAsDict(PdfName.ACROFORM);
/* 1018 */                 markUsed(top);
/* 1019 */                 dr = top.getAsDict(PdfName.DR);
/* 1020 */                 if (dr == null) {
/* 1021 */                   dr = new PdfDictionary();
/* 1022 */                   top.put(PdfName.DR, dr);
/*      */                 }
/* 1024 */                 markUsed(dr);
/* 1025 */                 PdfDictionary fontsTop = dr.getAsDict(PdfName.FONT);
/* 1026 */                 if (fontsTop == null) {
/* 1027 */                   fontsTop = new PdfDictionary();
/* 1028 */                   dr.put(PdfName.FONT, fontsTop);
/*      */                 }
/* 1030 */                 markUsed(fontsTop);
/* 1031 */                 PdfIndirectReference frefTop = (PdfIndirectReference)fontsTop.get(psn);
/* 1032 */                 if (frefTop != null) {
/* 1033 */                   if (fref == null)
/* 1034 */                     fonts.put(psn, frefTop);
/*      */                 }
/* 1036 */                 else if (fref == null)
/*      */                 {
/*      */                   FontDetails fd;
/*      */                   FontDetails fd;
/* 1038 */                   if (bf.getFontType() == 4) {
/* 1039 */                     fd = new FontDetails(null, ((DocumentFont)bf).getIndirectReference(), bf);
/*      */                   }
/*      */                   else {
/* 1042 */                     bf.setSubset(false);
/* 1043 */                     fd = this.writer.addSimple(bf);
/* 1044 */                     this.localFonts.put(psn.toString().substring(1), bf);
/*      */                   }
/* 1046 */                   fontsTop.put(psn, fd.getIndirectReference());
/* 1047 */                   fonts.put(psn, fd.getIndirectReference());
/*      */                 }
/* 1049 */                 ByteBuffer buf = cb.getInternalBuffer();
/* 1050 */                 buf.append(psn.getBytes()).append(' ').append(((Float)dao[1]).floatValue()).append(" Tf ");
/* 1051 */                 if (dao[2] != null)
/* 1052 */                   cb.setColorFill((BaseColor)dao[2]);
/* 1053 */                 PdfString s = new PdfString(cb.toString());
/* 1054 */                 item.getMerged(k).put(PdfName.DA, s);
/* 1055 */                 item.getWidget(k).put(PdfName.DA, s);
/* 1056 */                 markUsed(item.getWidget(k));
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 1062 */       else if (name.equalsIgnoreCase("textcolor")) {
/* 1063 */         for (int k = 0; k < item.size(); k++) {
/* 1064 */           if (hit.isHit(k)) {
/* 1065 */             PdfDictionary merged = item.getMerged(k);
/* 1066 */             PdfString da = merged.getAsString(PdfName.DA);
/* 1067 */             if (da != null) {
/* 1068 */               Object[] dao = splitDAelements(da.toUnicodeString());
/* 1069 */               PdfAppearance cb = new PdfAppearance();
/* 1070 */               if (dao[0] != null) {
/* 1071 */                 ByteBuffer buf = cb.getInternalBuffer();
/* 1072 */                 buf.append(new PdfName((String)dao[0]).getBytes()).append(' ').append(((Float)dao[1]).floatValue()).append(" Tf ");
/* 1073 */                 cb.setColorFill((BaseColor)value);
/* 1074 */                 PdfString s = new PdfString(cb.toString());
/* 1075 */                 item.getMerged(k).put(PdfName.DA, s);
/* 1076 */                 item.getWidget(k).put(PdfName.DA, s);
/* 1077 */                 markUsed(item.getWidget(k));
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 1083 */       else if (name.equalsIgnoreCase("textsize")) {
/* 1084 */         for (int k = 0; k < item.size(); k++) {
/* 1085 */           if (hit.isHit(k)) {
/* 1086 */             PdfDictionary merged = item.getMerged(k);
/* 1087 */             PdfString da = merged.getAsString(PdfName.DA);
/* 1088 */             if (da != null) {
/* 1089 */               Object[] dao = splitDAelements(da.toUnicodeString());
/* 1090 */               PdfAppearance cb = new PdfAppearance();
/* 1091 */               if (dao[0] != null) {
/* 1092 */                 ByteBuffer buf = cb.getInternalBuffer();
/* 1093 */                 buf.append(new PdfName((String)dao[0]).getBytes()).append(' ').append(((Float)value).floatValue()).append(" Tf ");
/* 1094 */                 if (dao[2] != null)
/* 1095 */                   cb.setColorFill((BaseColor)dao[2]);
/* 1096 */                 PdfString s = new PdfString(cb.toString());
/* 1097 */                 item.getMerged(k).put(PdfName.DA, s);
/* 1098 */                 item.getWidget(k).put(PdfName.DA, s);
/* 1099 */                 markUsed(item.getWidget(k));
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 1105 */       else if ((name.equalsIgnoreCase("bgcolor")) || (name.equalsIgnoreCase("bordercolor"))) {
/* 1106 */         PdfName dname = name.equalsIgnoreCase("bgcolor") ? PdfName.BG : PdfName.BC;
/* 1107 */         for (int k = 0; k < item.size(); k++)
/* 1108 */           if (hit.isHit(k)) {
/* 1109 */             PdfDictionary merged = item.getMerged(k);
/* 1110 */             PdfDictionary mk = merged.getAsDict(PdfName.MK);
/* 1111 */             if (mk == null) {
/* 1112 */               if (value == null)
/* 1113 */                 return true;
/* 1114 */               mk = new PdfDictionary();
/* 1115 */               item.getMerged(k).put(PdfName.MK, mk);
/* 1116 */               item.getWidget(k).put(PdfName.MK, mk);
/* 1117 */               markUsed(item.getWidget(k));
/*      */             } else {
/* 1119 */               markUsed(mk);
/*      */             }
/* 1121 */             if (value == null)
/* 1122 */               mk.remove(dname);
/*      */             else
/* 1124 */               mk.put(dname, PdfFormField.getMKColor((BaseColor)value));
/*      */           }
/*      */       }
/*      */       else
/*      */       {
/* 1129 */         return false;
/* 1130 */       }return true;
/*      */     }
/*      */     catch (Exception e) {
/* 1133 */       throw new ExceptionConverter(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean setFieldProperty(String field, String name, int value, int[] inst)
/*      */   {
/* 1165 */     if (this.writer == null)
/* 1166 */       throw new RuntimeException(MessageLocalization.getComposedMessage("this.acrofields.instance.is.read.only", new Object[0]));
/* 1167 */     Item item = (Item)this.fields.get(field);
/* 1168 */     if (item == null)
/* 1169 */       return false;
/* 1170 */     InstHit hit = new InstHit(inst);
/* 1171 */     if (name.equalsIgnoreCase("flags")) {
/* 1172 */       PdfNumber num = new PdfNumber(value);
/* 1173 */       for (int k = 0; k < item.size(); k++) {
/* 1174 */         if (hit.isHit(k)) {
/* 1175 */           item.getMerged(k).put(PdfName.F, num);
/* 1176 */           item.getWidget(k).put(PdfName.F, num);
/* 1177 */           markUsed(item.getWidget(k));
/*      */         }
/*      */       }
/*      */     }
/* 1181 */     else if (name.equalsIgnoreCase("setflags")) {
/* 1182 */       for (int k = 0; k < item.size(); k++) {
/* 1183 */         if (hit.isHit(k)) {
/* 1184 */           PdfNumber num = item.getWidget(k).getAsNumber(PdfName.F);
/* 1185 */           int val = 0;
/* 1186 */           if (num != null)
/* 1187 */             val = num.intValue();
/* 1188 */           num = new PdfNumber(val | value);
/* 1189 */           item.getMerged(k).put(PdfName.F, num);
/* 1190 */           item.getWidget(k).put(PdfName.F, num);
/* 1191 */           markUsed(item.getWidget(k));
/*      */         }
/*      */       }
/*      */     }
/* 1195 */     else if (name.equalsIgnoreCase("clrflags")) {
/* 1196 */       for (int k = 0; k < item.size(); k++) {
/* 1197 */         if (hit.isHit(k)) {
/* 1198 */           PdfDictionary widget = item.getWidget(k);
/* 1199 */           PdfNumber num = widget.getAsNumber(PdfName.F);
/* 1200 */           int val = 0;
/* 1201 */           if (num != null)
/* 1202 */             val = num.intValue();
/* 1203 */           num = new PdfNumber(val & (value ^ 0xFFFFFFFF));
/* 1204 */           item.getMerged(k).put(PdfName.F, num);
/* 1205 */           widget.put(PdfName.F, num);
/* 1206 */           markUsed(widget);
/*      */         }
/*      */       }
/*      */     }
/* 1210 */     else if (name.equalsIgnoreCase("fflags")) {
/* 1211 */       PdfNumber num = new PdfNumber(value);
/* 1212 */       for (int k = 0; k < item.size(); k++) {
/* 1213 */         if (hit.isHit(k)) {
/* 1214 */           item.getMerged(k).put(PdfName.FF, num);
/* 1215 */           item.getValue(k).put(PdfName.FF, num);
/* 1216 */           markUsed(item.getValue(k));
/*      */         }
/*      */       }
/*      */     }
/* 1220 */     else if (name.equalsIgnoreCase("setfflags")) {
/* 1221 */       for (int k = 0; k < item.size(); k++) {
/* 1222 */         if (hit.isHit(k)) {
/* 1223 */           PdfDictionary valDict = item.getValue(k);
/* 1224 */           PdfNumber num = valDict.getAsNumber(PdfName.FF);
/* 1225 */           int val = 0;
/* 1226 */           if (num != null)
/* 1227 */             val = num.intValue();
/* 1228 */           num = new PdfNumber(val | value);
/* 1229 */           item.getMerged(k).put(PdfName.FF, num);
/* 1230 */           valDict.put(PdfName.FF, num);
/* 1231 */           markUsed(valDict);
/*      */         }
/*      */       }
/*      */     }
/* 1235 */     else if (name.equalsIgnoreCase("clrfflags")) {
/* 1236 */       for (int k = 0; k < item.size(); k++)
/* 1237 */         if (hit.isHit(k)) {
/* 1238 */           PdfDictionary valDict = item.getValue(k);
/* 1239 */           PdfNumber num = valDict.getAsNumber(PdfName.FF);
/* 1240 */           int val = 0;
/* 1241 */           if (num != null)
/* 1242 */             val = num.intValue();
/* 1243 */           num = new PdfNumber(val & (value ^ 0xFFFFFFFF));
/* 1244 */           item.getMerged(k).put(PdfName.FF, num);
/* 1245 */           valDict.put(PdfName.FF, num);
/* 1246 */           markUsed(valDict);
/*      */         }
/*      */     }
/*      */     else
/*      */     {
/* 1251 */       return false;
/* 1252 */     }return true;
/*      */   }
/*      */ 
/*      */   public void mergeXfaData(Node n)
/*      */     throws IOException, DocumentException
/*      */   {
/* 1263 */     XfaForm.Xml2SomDatasets data = new XfaForm.Xml2SomDatasets(n);
/* 1264 */     for (String string : data.getOrder()) {
/* 1265 */       String name = string;
/* 1266 */       String text = XfaForm.getNodeText((Node)data.getName2Node().get(name));
/* 1267 */       setField(name, text);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setFields(FdfReader fdf)
/*      */     throws IOException, DocumentException
/*      */   {
/* 1279 */     HashMap fd = fdf.getFields();
/* 1280 */     for (String f : fd.keySet()) {
/* 1281 */       String v = fdf.getFieldValue(f);
/* 1282 */       if (v != null)
/* 1283 */         setField(f, v);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setFields(XfdfReader xfdf)
/*      */     throws IOException, DocumentException
/*      */   {
/* 1295 */     HashMap fd = xfdf.getFields();
/* 1296 */     for (String f : fd.keySet()) {
/* 1297 */       String v = xfdf.getFieldValue(f);
/* 1298 */       if (v != null)
/* 1299 */         setField(f, v);
/* 1300 */       List l = xfdf.getListValues(f);
/* 1301 */       if (l != null)
/* 1302 */         setListSelection(v, (String[])l.toArray(new String[l.size()]));
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean regenerateField(String name)
/*      */     throws IOException, DocumentException
/*      */   {
/* 1320 */     String value = getField(name);
/* 1321 */     return setField(name, value, value);
/*      */   }
/*      */ 
/*      */   public boolean setField(String name, String value)
/*      */     throws IOException, DocumentException
/*      */   {
/* 1335 */     return setField(name, value, null);
/*      */   }
/*      */ 
/*      */   public boolean setFieldRichValue(String name, String richValue)
/*      */     throws DocumentException, IOException
/*      */   {
/* 1351 */     if (this.writer == null)
/*      */     {
/* 1353 */       throw new DocumentException(MessageLocalization.getComposedMessage("this.acrofields.instance.is.read.only", new Object[0]));
/*      */     }
/*      */ 
/* 1356 */     Item item = getFieldItem(name);
/* 1357 */     if (item == null)
/*      */     {
/* 1359 */       return false;
/*      */     }
/*      */ 
/* 1362 */     if (getFieldType(name) != 4)
/*      */     {
/* 1364 */       return false;
/*      */     }
/*      */ 
/* 1367 */     PdfDictionary merged = item.getMerged(0);
/* 1368 */     PdfNumber ffNum = merged.getAsNumber(PdfName.FF);
/* 1369 */     int flagVal = 0;
/* 1370 */     if (ffNum != null) {
/* 1371 */       flagVal = ffNum.intValue();
/*      */     }
/* 1373 */     if ((flagVal & 0x2000000) == 0)
/*      */     {
/* 1375 */       return false;
/*      */     }
/*      */ 
/* 1378 */     PdfString richString = new PdfString(richValue);
/* 1379 */     item.writeToAll(PdfName.RV, richString, 5);
/*      */ 
/* 1381 */     InputStream is = new ByteArrayInputStream(richValue.getBytes());
/* 1382 */     PdfString valueString = new PdfString(XmlToTxt.parse(is));
/* 1383 */     item.writeToAll(PdfName.V, valueString, 5);
/* 1384 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean setField(String name, String value, String display)
/*      */     throws IOException, DocumentException
/*      */   {
/* 1403 */     if (this.writer == null)
/* 1404 */       throw new DocumentException(MessageLocalization.getComposedMessage("this.acrofields.instance.is.read.only", new Object[0]));
/* 1405 */     if (this.xfa.isXfaPresent()) {
/* 1406 */       name = this.xfa.findFieldName(name, this);
/* 1407 */       if (name == null)
/* 1408 */         return false;
/* 1409 */       String shortName = XfaForm.Xml2Som.getShortName(name);
/* 1410 */       Node xn = this.xfa.findDatasetsNode(shortName);
/* 1411 */       if (xn == null) {
/* 1412 */         xn = this.xfa.getDatasetsSom().insertNode(this.xfa.getDatasetsNode(), shortName);
/*      */       }
/* 1414 */       this.xfa.setNodeText(xn, value);
/*      */     }
/* 1416 */     Item item = (Item)this.fields.get(name);
/* 1417 */     if (item == null)
/* 1418 */       return false;
/* 1419 */     PdfDictionary merged = item.getMerged(0);
/* 1420 */     PdfName type = merged.getAsName(PdfName.FT);
/* 1421 */     if (PdfName.TX.equals(type)) {
/* 1422 */       PdfNumber maxLen = merged.getAsNumber(PdfName.MAXLEN);
/* 1423 */       int len = 0;
/* 1424 */       if (maxLen != null)
/* 1425 */         len = maxLen.intValue();
/* 1426 */       if (len > 0)
/* 1427 */         value = value.substring(0, Math.min(len, value.length()));
/*      */     }
/* 1429 */     if (display == null)
/* 1430 */       display = value;
/* 1431 */     if ((PdfName.TX.equals(type)) || (PdfName.CH.equals(type))) {
/* 1432 */       PdfString v = new PdfString(value, "UnicodeBig");
/* 1433 */       for (int idx = 0; idx < item.size(); idx++) {
/* 1434 */         PdfDictionary valueDic = item.getValue(idx);
/* 1435 */         valueDic.put(PdfName.V, v);
/* 1436 */         valueDic.remove(PdfName.I);
/* 1437 */         markUsed(valueDic);
/* 1438 */         merged = item.getMerged(idx);
/* 1439 */         merged.remove(PdfName.I);
/* 1440 */         merged.put(PdfName.V, v);
/* 1441 */         PdfDictionary widget = item.getWidget(idx);
/* 1442 */         if (this.generateAppearances) {
/* 1443 */           PdfAppearance app = getAppearance(merged, display, name);
/* 1444 */           if (PdfName.CH.equals(type)) {
/* 1445 */             PdfNumber n = new PdfNumber(this.topFirst);
/* 1446 */             widget.put(PdfName.TI, n);
/* 1447 */             merged.put(PdfName.TI, n);
/*      */           }
/* 1449 */           PdfDictionary appDic = widget.getAsDict(PdfName.AP);
/* 1450 */           if (appDic == null) {
/* 1451 */             appDic = new PdfDictionary();
/* 1452 */             widget.put(PdfName.AP, appDic);
/* 1453 */             merged.put(PdfName.AP, appDic);
/*      */           }
/* 1455 */           appDic.put(PdfName.N, app.getIndirectReference());
/* 1456 */           this.writer.releaseTemplate(app);
/*      */         }
/*      */         else {
/* 1459 */           widget.remove(PdfName.AP);
/* 1460 */           merged.remove(PdfName.AP);
/*      */         }
/* 1462 */         markUsed(widget);
/*      */       }
/* 1464 */       return true;
/*      */     }
/* 1466 */     if (PdfName.BTN.equals(type)) {
/* 1467 */       PdfNumber ff = item.getMerged(0).getAsNumber(PdfName.FF);
/* 1468 */       int flags = 0;
/* 1469 */       if (ff != null)
/* 1470 */         flags = ff.intValue();
/* 1471 */       if ((flags & 0x10000) != 0)
/*      */       {
/*      */         Image img;
/*      */         try {
/* 1475 */           img = Image.getInstance(Base64.decode(value));
/*      */         }
/*      */         catch (Exception e) {
/* 1478 */           return false;
/*      */         }
/* 1480 */         PushbuttonField pb = getNewPushbuttonFromField(name);
/* 1481 */         pb.setImage(img);
/* 1482 */         replacePushbuttonField(name, pb.getField());
/* 1483 */         return true;
/*      */       }
/* 1485 */       PdfName v = new PdfName(value);
/* 1486 */       ArrayList lopt = new ArrayList();
/* 1487 */       PdfArray opts = item.getValue(0).getAsArray(PdfName.OPT);
/* 1488 */       if (opts != null) {
/* 1489 */         for (int k = 0; k < opts.size(); k++) {
/* 1490 */           PdfString valStr = opts.getAsString(k);
/* 1491 */           if (valStr != null)
/* 1492 */             lopt.add(valStr.toUnicodeString());
/*      */           else
/* 1494 */             lopt.add(null);
/*      */         }
/*      */       }
/* 1497 */       int vidx = lopt.indexOf(value);
/*      */       PdfName vt;
/*      */       PdfName vt;
/* 1499 */       if (vidx >= 0)
/* 1500 */         vt = new PdfName(String.valueOf(vidx));
/*      */       else
/* 1502 */         vt = v;
/* 1503 */       for (int idx = 0; idx < item.size(); idx++) {
/* 1504 */         merged = item.getMerged(idx);
/* 1505 */         PdfDictionary widget = item.getWidget(idx);
/* 1506 */         PdfDictionary valDict = item.getValue(idx);
/* 1507 */         markUsed(item.getValue(idx));
/* 1508 */         valDict.put(PdfName.V, vt);
/* 1509 */         merged.put(PdfName.V, vt);
/* 1510 */         markUsed(widget);
/* 1511 */         PdfDictionary appDic = widget.getAsDict(PdfName.AP);
/* 1512 */         if (appDic == null)
/* 1513 */           return false;
/* 1514 */         PdfDictionary normal = appDic.getAsDict(PdfName.N);
/* 1515 */         if ((isInAP(normal, vt)) || (normal == null)) {
/* 1516 */           merged.put(PdfName.AS, vt);
/* 1517 */           widget.put(PdfName.AS, vt);
/*      */         }
/*      */         else {
/* 1520 */           merged.put(PdfName.AS, PdfName.Off);
/* 1521 */           widget.put(PdfName.AS, PdfName.Off);
/*      */         }
/*      */       }
/* 1524 */       return true;
/*      */     }
/* 1526 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean setListSelection(String name, String[] value)
/*      */     throws IOException, DocumentException
/*      */   {
/* 1539 */     Item item = getFieldItem(name);
/* 1540 */     if (item == null)
/* 1541 */       return false;
/* 1542 */     PdfDictionary merged = item.getMerged(0);
/* 1543 */     PdfName type = merged.getAsName(PdfName.FT);
/* 1544 */     if (!PdfName.CH.equals(type)) {
/* 1545 */       return false;
/*      */     }
/* 1547 */     String[] options = getListOptionExport(name);
/* 1548 */     PdfArray array = new PdfArray();
/* 1549 */     for (String element : value) {
/* 1550 */       for (int j = 0; j < options.length; j++) {
/* 1551 */         if (options[j].equals(element)) {
/* 1552 */           array.add(new PdfNumber(j));
/* 1553 */           break;
/*      */         }
/*      */       }
/*      */     }
/* 1557 */     item.writeToAll(PdfName.I, array, 5);
/*      */ 
/* 1559 */     PdfArray vals = new PdfArray();
/* 1560 */     for (int i = 0; i < value.length; i++) {
/* 1561 */       vals.add(new PdfString(value[i]));
/*      */     }
/* 1563 */     item.writeToAll(PdfName.V, vals, 5);
/*      */ 
/* 1565 */     PdfAppearance app = getAppearance(merged, value, name);
/*      */ 
/* 1567 */     PdfDictionary apDic = new PdfDictionary();
/* 1568 */     apDic.put(PdfName.N, app.getIndirectReference());
/* 1569 */     item.writeToAll(PdfName.AP, apDic, 3);
/*      */ 
/* 1571 */     this.writer.releaseTemplate(app);
/*      */ 
/* 1573 */     item.markUsed(this, 6);
/* 1574 */     return true;
/*      */   }
/*      */ 
/*      */   boolean isInAP(PdfDictionary nDic, PdfName check) {
/* 1578 */     return (nDic != null) && (nDic.get(check) != null);
/*      */   }
/*      */ 
/*      */   public Map<String, Item> getFields()
/*      */   {
/* 1588 */     return this.fields;
/*      */   }
/*      */ 
/*      */   public Item getFieldItem(String name)
/*      */   {
/* 1599 */     if (this.xfa.isXfaPresent()) {
/* 1600 */       name = this.xfa.findFieldName(name, this);
/* 1601 */       if (name == null)
/* 1602 */         return null;
/*      */     }
/* 1604 */     return (Item)this.fields.get(name);
/*      */   }
/*      */ 
/*      */   public String getTranslatedFieldName(String name)
/*      */   {
/* 1614 */     if (this.xfa.isXfaPresent()) {
/* 1615 */       String namex = this.xfa.findFieldName(name, this);
/* 1616 */       if (namex != null)
/* 1617 */         name = namex;
/*      */     }
/* 1619 */     return name;
/*      */   }
/*      */ 
/*      */   public List<FieldPosition> getFieldPositions(String name)
/*      */   {
/* 1631 */     Item item = getFieldItem(name);
/* 1632 */     if (item == null)
/* 1633 */       return null;
/* 1634 */     ArrayList ret = new ArrayList();
/* 1635 */     for (int k = 0; k < item.size(); k++)
/*      */       try {
/* 1637 */         PdfDictionary wd = item.getWidget(k);
/* 1638 */         PdfArray rect = wd.getAsArray(PdfName.RECT);
/* 1639 */         if (rect != null)
/*      */         {
/* 1641 */           Rectangle r = PdfReader.getNormalizedRectangle(rect);
/* 1642 */           int page = item.getPage(k).intValue();
/* 1643 */           int rotation = this.reader.getPageRotation(page);
/* 1644 */           FieldPosition fp = new FieldPosition();
/* 1645 */           fp.page = page;
/* 1646 */           if (rotation != 0) {
/* 1647 */             Rectangle pageSize = this.reader.getPageSize(page);
/* 1648 */             switch (rotation) {
/*      */             case 270:
/* 1650 */               r = new Rectangle(pageSize.getTop() - r.getBottom(), r.getLeft(), pageSize.getTop() - r.getTop(), r.getRight());
/*      */ 
/* 1655 */               break;
/*      */             case 180:
/* 1657 */               r = new Rectangle(pageSize.getRight() - r.getLeft(), pageSize.getTop() - r.getBottom(), pageSize.getRight() - r.getRight(), pageSize.getTop() - r.getTop());
/*      */ 
/* 1662 */               break;
/*      */             case 90:
/* 1664 */               r = new Rectangle(r.getBottom(), pageSize.getRight() - r.getLeft(), r.getTop(), pageSize.getRight() - r.getRight());
/*      */             }
/*      */ 
/* 1671 */             r.normalize();
/*      */           }
/* 1673 */           fp.position = r;
/* 1674 */           ret.add(fp);
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*      */       }
/* 1680 */     return ret;
/*      */   }
/*      */ 
/*      */   private int removeRefFromArray(PdfArray array, PdfObject refo) {
/* 1684 */     if ((refo == null) || (!refo.isIndirect()))
/* 1685 */       return array.size();
/* 1686 */     PdfIndirectReference ref = (PdfIndirectReference)refo;
/* 1687 */     for (int j = 0; j < array.size(); j++) {
/* 1688 */       PdfObject obj = array.getPdfObject(j);
/* 1689 */       if (obj.isIndirect())
/*      */       {
/* 1691 */         if (((PdfIndirectReference)obj).getNumber() == ref.getNumber())
/* 1692 */           array.remove(j--); 
/*      */       }
/*      */     }
/* 1694 */     return array.size();
/*      */   }
/*      */ 
/*      */   public boolean removeFieldsFromPage(int page)
/*      */   {
/* 1704 */     if (page < 1)
/* 1705 */       return false;
/* 1706 */     String[] names = new String[this.fields.size()];
/* 1707 */     this.fields.keySet().toArray(names);
/* 1708 */     boolean found = false;
/* 1709 */     for (int k = 0; k < names.length; k++) {
/* 1710 */       boolean fr = removeField(names[k], page);
/* 1711 */       found = (found) || (fr);
/*      */     }
/* 1713 */     return found;
/*      */   }
/*      */ 
/*      */   public boolean removeField(String name, int page)
/*      */   {
/* 1726 */     Item item = getFieldItem(name);
/* 1727 */     if (item == null)
/* 1728 */       return false;
/* 1729 */     PdfDictionary acroForm = (PdfDictionary)PdfReader.getPdfObject(this.reader.getCatalog().get(PdfName.ACROFORM), this.reader.getCatalog());
/*      */ 
/* 1731 */     if (acroForm == null)
/* 1732 */       return false;
/* 1733 */     PdfArray arrayf = acroForm.getAsArray(PdfName.FIELDS);
/* 1734 */     if (arrayf == null)
/* 1735 */       return false;
/* 1736 */     for (int k = 0; k < item.size(); k++) {
/* 1737 */       int pageV = item.getPage(k).intValue();
/* 1738 */       if ((page == -1) || (page == pageV))
/*      */       {
/* 1740 */         PdfIndirectReference ref = item.getWidgetRef(k);
/* 1741 */         PdfDictionary wd = item.getWidget(k);
/* 1742 */         PdfDictionary pageDic = this.reader.getPageN(pageV);
/* 1743 */         PdfArray annots = pageDic.getAsArray(PdfName.ANNOTS);
/* 1744 */         if (annots != null)
/* 1745 */           if (removeRefFromArray(annots, ref) == 0) {
/* 1746 */             pageDic.remove(PdfName.ANNOTS);
/* 1747 */             markUsed(pageDic);
/*      */           }
/*      */           else {
/* 1750 */             markUsed(annots);
/*      */           }
/* 1752 */         PdfReader.killIndirect(ref);
/* 1753 */         PdfIndirectReference kid = ref;
/* 1754 */         while ((ref = wd.getAsIndirectObject(PdfName.PARENT)) != null) {
/* 1755 */           wd = wd.getAsDict(PdfName.PARENT);
/* 1756 */           PdfArray kids = wd.getAsArray(PdfName.KIDS);
/* 1757 */           if (removeRefFromArray(kids, kid) != 0)
/*      */             break;
/* 1759 */           kid = ref;
/* 1760 */           PdfReader.killIndirect(ref);
/*      */         }
/* 1762 */         if (ref == null) {
/* 1763 */           removeRefFromArray(arrayf, kid);
/* 1764 */           markUsed(arrayf);
/*      */         }
/* 1766 */         if (page != -1) {
/* 1767 */           item.remove(k);
/* 1768 */           k--;
/*      */         }
/*      */       }
/*      */     }
/* 1771 */     if ((page == -1) || (item.size() == 0))
/* 1772 */       this.fields.remove(name);
/* 1773 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean removeField(String name)
/*      */   {
/* 1783 */     return removeField(name, -1);
/*      */   }
/*      */ 
/*      */   public boolean isGenerateAppearances()
/*      */   {
/* 1792 */     return this.generateAppearances;
/*      */   }
/*      */ 
/*      */   public void setGenerateAppearances(boolean generateAppearances)
/*      */   {
/* 1804 */     this.generateAppearances = generateAppearances;
/* 1805 */     PdfDictionary top = this.reader.getCatalog().getAsDict(PdfName.ACROFORM);
/* 1806 */     if (generateAppearances)
/* 1807 */       top.remove(PdfName.NEEDAPPEARANCES);
/*      */     else
/* 1809 */       top.put(PdfName.NEEDAPPEARANCES, PdfBoolean.PDFTRUE);
/*      */   }
/*      */ 
/*      */   public boolean clearSignatureField(String name)
/*      */   {
/* 2123 */     this.sigNames = null;
/* 2124 */     getSignatureNames();
/* 2125 */     if (!this.sigNames.containsKey(name))
/* 2126 */       return false;
/* 2127 */     Item sig = (Item)this.fields.get(name);
/* 2128 */     sig.markUsed(this, 6);
/* 2129 */     int n = sig.size();
/* 2130 */     for (int k = 0; k < n; k++) {
/* 2131 */       clearSigDic(sig.getMerged(k));
/* 2132 */       clearSigDic(sig.getWidget(k));
/* 2133 */       clearSigDic(sig.getValue(k));
/*      */     }
/* 2135 */     return true;
/*      */   }
/*      */ 
/*      */   private static void clearSigDic(PdfDictionary dic) {
/* 2139 */     dic.remove(PdfName.AP);
/* 2140 */     dic.remove(PdfName.AS);
/* 2141 */     dic.remove(PdfName.V);
/* 2142 */     dic.remove(PdfName.DV);
/* 2143 */     dic.remove(PdfName.SV);
/* 2144 */     dic.remove(PdfName.FF);
/* 2145 */     dic.put(PdfName.F, new PdfNumber(4));
/*      */   }
/*      */ 
/*      */   public ArrayList<String> getSignatureNames()
/*      */   {
/* 2156 */     if (this.sigNames != null)
/* 2157 */       return new ArrayList(this.orderedSignatureNames);
/* 2158 */     this.sigNames = new HashMap();
/* 2159 */     this.orderedSignatureNames = new ArrayList();
/* 2160 */     ArrayList sorter = new ArrayList();
/* 2161 */     for (Map.Entry entry : this.fields.entrySet()) {
/* 2162 */       Item item = (Item)entry.getValue();
/* 2163 */       PdfDictionary merged = item.getMerged(0);
/* 2164 */       if (PdfName.SIG.equals(merged.get(PdfName.FT)))
/*      */       {
/* 2166 */         PdfDictionary v = merged.getAsDict(PdfName.V);
/* 2167 */         if (v != null)
/*      */         {
/* 2169 */           PdfString contents = v.getAsString(PdfName.CONTENTS);
/* 2170 */           if (contents != null)
/*      */           {
/* 2172 */             PdfArray ro = v.getAsArray(PdfName.BYTERANGE);
/* 2173 */             if (ro != null)
/*      */             {
/* 2175 */               int rangeSize = ro.size();
/* 2176 */               if (rangeSize >= 2)
/*      */               {
/* 2178 */                 int length = ro.getAsNumber(rangeSize - 1).intValue() + ro.getAsNumber(rangeSize - 2).intValue();
/* 2179 */                 sorter.add(new Object[] { entry.getKey(), { length, 0 } });
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 2181 */     Collections.sort(sorter, new SorterComparator(null));
/* 2182 */     if (!sorter.isEmpty()) {
/* 2183 */       if (((int[])(int[])((Object[])sorter.get(sorter.size() - 1))[1])[0] == this.reader.getFileLength())
/* 2184 */         this.totalRevisions = sorter.size();
/*      */       else
/* 2186 */         this.totalRevisions = (sorter.size() + 1);
/* 2187 */       for (int k = 0; k < sorter.size(); k++) {
/* 2188 */         Object[] objs = (Object[])sorter.get(k);
/* 2189 */         String name = (String)objs[0];
/* 2190 */         int[] p = (int[])objs[1];
/* 2191 */         p[1] = (k + 1);
/* 2192 */         this.sigNames.put(name, p);
/* 2193 */         this.orderedSignatureNames.add(name);
/*      */       }
/*      */     }
/* 2196 */     return new ArrayList(this.orderedSignatureNames);
/*      */   }
/*      */ 
/*      */   public ArrayList<String> getBlankSignatureNames()
/*      */   {
/* 2205 */     getSignatureNames();
/* 2206 */     ArrayList sigs = new ArrayList();
/* 2207 */     for (Map.Entry entry : this.fields.entrySet()) {
/* 2208 */       Item item = (Item)entry.getValue();
/* 2209 */       PdfDictionary merged = item.getMerged(0);
/* 2210 */       if ((PdfName.SIG.equals(merged.getAsName(PdfName.FT))) && 
/* 2212 */         (!this.sigNames.containsKey(entry.getKey())))
/*      */       {
/* 2214 */         sigs.add(entry.getKey());
/*      */       }
/*      */     }
/* 2216 */     return sigs;
/*      */   }
/*      */ 
/*      */   public PdfDictionary getSignatureDictionary(String name)
/*      */   {
/* 2227 */     getSignatureNames();
/* 2228 */     name = getTranslatedFieldName(name);
/* 2229 */     if (!this.sigNames.containsKey(name))
/* 2230 */       return null;
/* 2231 */     Item item = (Item)this.fields.get(name);
/* 2232 */     PdfDictionary merged = item.getMerged(0);
/* 2233 */     return merged.getAsDict(PdfName.V);
/*      */   }
/*      */ 
/*      */   public PdfIndirectReference getNormalAppearance(String name)
/*      */   {
/* 2243 */     getSignatureNames();
/* 2244 */     name = getTranslatedFieldName(name);
/* 2245 */     Item item = (Item)this.fields.get(name);
/* 2246 */     if (item == null)
/* 2247 */       return null;
/* 2248 */     PdfDictionary merged = item.getMerged(0);
/* 2249 */     PdfDictionary ap = merged.getAsDict(PdfName.AP);
/* 2250 */     if (ap == null)
/* 2251 */       return null;
/* 2252 */     PdfIndirectReference ref = ap.getAsIndirectObject(PdfName.N);
/* 2253 */     if (ref == null)
/* 2254 */       return null;
/* 2255 */     return ref;
/*      */   }
/*      */ 
/*      */   public boolean signatureCoversWholeDocument(String name)
/*      */   {
/* 2266 */     getSignatureNames();
/* 2267 */     name = getTranslatedFieldName(name);
/* 2268 */     if (!this.sigNames.containsKey(name))
/* 2269 */       return false;
/* 2270 */     return ((int[])this.sigNames.get(name))[0] == this.reader.getFileLength();
/*      */   }
/*      */ 
/*      */   public PdfPKCS7 verifySignature(String name)
/*      */   {
/* 2302 */     return verifySignature(name, null);
/*      */   }
/*      */ 
/*      */   public PdfPKCS7 verifySignature(String name, String provider)
/*      */   {
/* 2335 */     PdfDictionary v = getSignatureDictionary(name);
/* 2336 */     if (v == null)
/* 2337 */       return null;
/*      */     try {
/* 2339 */       PdfName sub = v.getAsName(PdfName.SUBFILTER);
/* 2340 */       PdfString contents = v.getAsString(PdfName.CONTENTS);
/* 2341 */       PdfPKCS7 pk = null;
/* 2342 */       if (sub.equals(PdfName.ADBE_X509_RSA_SHA1)) {
/* 2343 */         PdfString cert = v.getAsString(PdfName.CERT);
/* 2344 */         if (cert == null)
/* 2345 */           cert = v.getAsArray(PdfName.CERT).getAsString(0);
/* 2346 */         pk = new PdfPKCS7(contents.getOriginalBytes(), cert.getBytes(), provider);
/*      */       }
/*      */       else {
/* 2349 */         pk = new PdfPKCS7(contents.getOriginalBytes(), sub, provider);
/* 2350 */       }updateByteRange(pk, v);
/* 2351 */       PdfString str = v.getAsString(PdfName.M);
/* 2352 */       if (str != null)
/* 2353 */         pk.setSignDate(PdfDate.decode(str.toString()));
/* 2354 */       PdfObject obj = PdfReader.getPdfObject(v.get(PdfName.NAME));
/* 2355 */       if (obj != null) {
/* 2356 */         if (obj.isString())
/* 2357 */           pk.setSignName(((PdfString)obj).toUnicodeString());
/* 2358 */         else if (obj.isName())
/* 2359 */           pk.setSignName(PdfName.decodeName(obj.toString()));
/*      */       }
/* 2361 */       str = v.getAsString(PdfName.REASON);
/* 2362 */       if (str != null)
/* 2363 */         pk.setReason(str.toUnicodeString());
/* 2364 */       str = v.getAsString(PdfName.LOCATION);
/* 2365 */       if (str != null)
/* 2366 */         pk.setLocation(str.toUnicodeString());
/* 2367 */       return pk;
/*      */     }
/*      */     catch (Exception e) {
/* 2370 */       throw new ExceptionConverter(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void updateByteRange(PdfPKCS7 pkcs7, PdfDictionary v) {
/* 2375 */     PdfArray b = v.getAsArray(PdfName.BYTERANGE);
/* 2376 */     RandomAccessFileOrArray rf = this.reader.getSafeFile();
/* 2377 */     InputStream rg = null;
/*      */     try {
/* 2379 */       rg = new RASInputStream(new RandomAccessSourceFactory().createRanged(rf.createSourceView(), b.asLongArray()));
/* 2380 */       byte[] buf = new byte[8192];
/*      */       int rd;
/* 2382 */       while ((rd = rg.read(buf, 0, buf.length)) > 0)
/* 2383 */         pkcs7.update(buf, 0, rd);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2387 */       throw new ExceptionConverter(e);
/*      */     } finally {
/*      */       try {
/* 2390 */         if (rg != null) rg.close(); 
/*      */       }
/*      */       catch (IOException e)
/*      */       {
/* 2393 */         throw new ExceptionConverter(e);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void markUsed(PdfObject obj) {
/* 2399 */     if (!this.append)
/* 2400 */       return;
/* 2401 */     ((PdfStamperImp)this.writer).markUsed(obj);
/*      */   }
/*      */ 
/*      */   public int getTotalRevisions()
/*      */   {
/* 2410 */     getSignatureNames();
/* 2411 */     return this.totalRevisions;
/*      */   }
/*      */ 
/*      */   public int getRevision(String field)
/*      */   {
/* 2421 */     getSignatureNames();
/* 2422 */     field = getTranslatedFieldName(field);
/* 2423 */     if (!this.sigNames.containsKey(field))
/* 2424 */       return 0;
/* 2425 */     return ((int[])this.sigNames.get(field))[1];
/*      */   }
/*      */ 
/*      */   public InputStream extractRevision(String field)
/*      */     throws IOException
/*      */   {
/* 2437 */     getSignatureNames();
/* 2438 */     field = getTranslatedFieldName(field);
/* 2439 */     if (!this.sigNames.containsKey(field))
/* 2440 */       return null;
/* 2441 */     int length = ((int[])this.sigNames.get(field))[0];
/* 2442 */     RandomAccessFileOrArray raf = this.reader.getSafeFile();
/* 2443 */     return new RASInputStream(new WindowRandomAccessSource(raf.createSourceView(), 0L, length));
/*      */   }
/*      */ 
/*      */   public Map<String, TextField> getFieldCache()
/*      */   {
/* 2453 */     return this.fieldCache;
/*      */   }
/*      */ 
/*      */   public void setFieldCache(Map<String, TextField> fieldCache)
/*      */   {
/* 2483 */     this.fieldCache = fieldCache;
/*      */   }
/*      */ 
/*      */   public void setExtraMargin(float extraMarginLeft, float extraMarginTop)
/*      */   {
/* 2493 */     this.extraMarginLeft = extraMarginLeft;
/* 2494 */     this.extraMarginTop = extraMarginTop;
/*      */   }
/*      */ 
/*      */   public void addSubstitutionFont(BaseFont font)
/*      */   {
/* 2504 */     if (this.substitutionFonts == null)
/* 2505 */       this.substitutionFonts = new ArrayList();
/* 2506 */     this.substitutionFonts.add(font);
/*      */   }
/*      */ 
/*      */   public ArrayList<BaseFont> getSubstitutionFonts()
/*      */   {
/* 2562 */     return this.substitutionFonts;
/*      */   }
/*      */ 
/*      */   public void setSubstitutionFonts(ArrayList<BaseFont> substitutionFonts)
/*      */   {
/* 2572 */     this.substitutionFonts = substitutionFonts;
/*      */   }
/*      */ 
/*      */   public XfaForm getXfa()
/*      */   {
/* 2581 */     return this.xfa;
/*      */   }
/*      */ 
/*      */   public void removeXfa()
/*      */   {
/* 2588 */     PdfDictionary root = this.reader.getCatalog();
/* 2589 */     PdfDictionary acroform = root.getAsDict(PdfName.ACROFORM);
/* 2590 */     acroform.remove(PdfName.XFA);
/*      */     try {
/* 2592 */       this.xfa = new XfaForm(this.reader);
/*      */     }
/*      */     catch (Exception e) {
/* 2595 */       throw new ExceptionConverter(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public PushbuttonField getNewPushbuttonFromField(String field)
/*      */   {
/* 2611 */     return getNewPushbuttonFromField(field, 0);
/*      */   }
/*      */ 
/*      */   public PushbuttonField getNewPushbuttonFromField(String field, int order)
/*      */   {
/*      */     try
/*      */     {
/* 2627 */       if (getFieldType(field) != 1)
/* 2628 */         return null;
/* 2629 */       Item item = getFieldItem(field);
/* 2630 */       if (order >= item.size())
/* 2631 */         return null;
/* 2632 */       List pos = getFieldPositions(field);
/* 2633 */       Rectangle box = ((FieldPosition)pos.get(order)).position;
/* 2634 */       PushbuttonField newButton = new PushbuttonField(this.writer, box, null);
/* 2635 */       PdfDictionary dic = item.getMerged(order);
/* 2636 */       decodeGenericDictionary(dic, newButton);
/* 2637 */       PdfDictionary mk = dic.getAsDict(PdfName.MK);
/* 2638 */       if (mk != null) {
/* 2639 */         PdfString text = mk.getAsString(PdfName.CA);
/* 2640 */         if (text != null)
/* 2641 */           newButton.setText(text.toUnicodeString());
/* 2642 */         PdfNumber tp = mk.getAsNumber(PdfName.TP);
/* 2643 */         if (tp != null)
/* 2644 */           newButton.setLayout(tp.intValue() + 1);
/* 2645 */         PdfDictionary ifit = mk.getAsDict(PdfName.IF);
/* 2646 */         if (ifit != null) {
/* 2647 */           PdfName sw = ifit.getAsName(PdfName.SW);
/* 2648 */           if (sw != null) {
/* 2649 */             int scale = 1;
/* 2650 */             if (sw.equals(PdfName.B))
/* 2651 */               scale = 3;
/* 2652 */             else if (sw.equals(PdfName.S))
/* 2653 */               scale = 4;
/* 2654 */             else if (sw.equals(PdfName.N))
/* 2655 */               scale = 2;
/* 2656 */             newButton.setScaleIcon(scale);
/*      */           }
/* 2658 */           sw = ifit.getAsName(PdfName.S);
/* 2659 */           if ((sw != null) && 
/* 2660 */             (sw.equals(PdfName.A))) {
/* 2661 */             newButton.setProportionalIcon(false);
/*      */           }
/* 2663 */           PdfArray aj = ifit.getAsArray(PdfName.A);
/* 2664 */           if ((aj != null) && (aj.size() == 2)) {
/* 2665 */             float left = aj.getAsNumber(0).floatValue();
/* 2666 */             float bottom = aj.getAsNumber(1).floatValue();
/* 2667 */             newButton.setIconHorizontalAdjustment(left);
/* 2668 */             newButton.setIconVerticalAdjustment(bottom);
/*      */           }
/* 2670 */           PdfBoolean fb = ifit.getAsBoolean(PdfName.FB);
/* 2671 */           if ((fb != null) && (fb.booleanValue()))
/* 2672 */             newButton.setIconFitToBounds(true);
/*      */         }
/* 2674 */         PdfObject i = mk.get(PdfName.I);
/* 2675 */         if ((i != null) && (i.isIndirect()))
/* 2676 */           newButton.setIconReference((PRIndirectReference)i);
/*      */       }
/* 2678 */       return newButton;
/*      */     }
/*      */     catch (Exception e) {
/* 2681 */       throw new ExceptionConverter(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean replacePushbuttonField(String field, PdfFormField button)
/*      */   {
/* 2696 */     return replacePushbuttonField(field, button, 0);
/*      */   }
/*      */ 
/*      */   public boolean replacePushbuttonField(String field, PdfFormField button, int order)
/*      */   {
/* 2713 */     if (getFieldType(field) != 1)
/* 2714 */       return false;
/* 2715 */     Item item = getFieldItem(field);
/* 2716 */     if (order >= item.size())
/* 2717 */       return false;
/* 2718 */     PdfDictionary merged = item.getMerged(order);
/* 2719 */     PdfDictionary values = item.getValue(order);
/* 2720 */     PdfDictionary widgets = item.getWidget(order);
/* 2721 */     for (int k = 0; k < buttonRemove.length; k++) {
/* 2722 */       merged.remove(buttonRemove[k]);
/* 2723 */       values.remove(buttonRemove[k]);
/* 2724 */       widgets.remove(buttonRemove[k]);
/*      */     }
/* 2726 */     for (Object element : button.getKeys()) {
/* 2727 */       PdfName key = (PdfName)element;
/* 2728 */       if ((!key.equals(PdfName.T)) && (!key.equals(PdfName.RECT)))
/*      */       {
/* 2730 */         if (key.equals(PdfName.FF))
/* 2731 */           values.put(key, button.get(key));
/*      */         else
/* 2733 */           widgets.put(key, button.get(key));
/* 2734 */         merged.put(key, button.get(key));
/* 2735 */         markUsed(values);
/* 2736 */         markUsed(widgets);
/*      */       }
/*      */     }
/* 2738 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean doesSignatureFieldExist(String name)
/*      */   {
/* 2748 */     return (getBlankSignatureNames().contains(name)) || (getSignatureNames().contains(name));
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/* 2524 */     stdFieldFontNames.put("CoBO", new String[] { "Courier-BoldOblique" });
/* 2525 */     stdFieldFontNames.put("CoBo", new String[] { "Courier-Bold" });
/* 2526 */     stdFieldFontNames.put("CoOb", new String[] { "Courier-Oblique" });
/* 2527 */     stdFieldFontNames.put("Cour", new String[] { "Courier" });
/* 2528 */     stdFieldFontNames.put("HeBO", new String[] { "Helvetica-BoldOblique" });
/* 2529 */     stdFieldFontNames.put("HeBo", new String[] { "Helvetica-Bold" });
/* 2530 */     stdFieldFontNames.put("HeOb", new String[] { "Helvetica-Oblique" });
/* 2531 */     stdFieldFontNames.put("Helv", new String[] { "Helvetica" });
/* 2532 */     stdFieldFontNames.put("Symb", new String[] { "Symbol" });
/* 2533 */     stdFieldFontNames.put("TiBI", new String[] { "Times-BoldItalic" });
/* 2534 */     stdFieldFontNames.put("TiBo", new String[] { "Times-Bold" });
/* 2535 */     stdFieldFontNames.put("TiIt", new String[] { "Times-Italic" });
/* 2536 */     stdFieldFontNames.put("TiRo", new String[] { "Times-Roman" });
/* 2537 */     stdFieldFontNames.put("ZaDb", new String[] { "ZapfDingbats" });
/* 2538 */     stdFieldFontNames.put("HySm", new String[] { "HYSMyeongJo-Medium", "UniKS-UCS2-H" });
/* 2539 */     stdFieldFontNames.put("HyGo", new String[] { "HYGoThic-Medium", "UniKS-UCS2-H" });
/* 2540 */     stdFieldFontNames.put("KaGo", new String[] { "HeiseiKakuGo-W5", "UniKS-UCS2-H" });
/* 2541 */     stdFieldFontNames.put("KaMi", new String[] { "HeiseiMin-W3", "UniJIS-UCS2-H" });
/* 2542 */     stdFieldFontNames.put("MHei", new String[] { "MHei-Medium", "UniCNS-UCS2-H" });
/* 2543 */     stdFieldFontNames.put("MSun", new String[] { "MSung-Light", "UniCNS-UCS2-H" });
/* 2544 */     stdFieldFontNames.put("STSo", new String[] { "STSong-Light", "UniGB-UCS2-H" }); } 
/*      */   public static class FieldPosition { public int page;
/*      */     public Rectangle position; }
/*      */ 
/* 2549 */   private static class SorterComparator implements Comparator<Object[]> { public int compare(Object[] o1, Object[] o2) { int n1 = ((int[])(int[])o1[1])[0];
/* 2550 */       int n2 = ((int[])(int[])o2[1])[0];
/* 2551 */       return n1 - n2;
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class InstHit
/*      */   {
/*      */     IntHashtable hits;
/*      */ 
/*      */     public InstHit(int[] inst)
/*      */     {
/* 2102 */       if (inst == null)
/* 2103 */         return;
/* 2104 */       this.hits = new IntHashtable();
/* 2105 */       for (int k = 0; k < inst.length; k++)
/* 2106 */         this.hits.put(inst[k], 1);
/*      */     }
/*      */ 
/*      */     public boolean isHit(int n) {
/* 2110 */       if (this.hits == null)
/* 2111 */         return true;
/* 2112 */       return this.hits.containsKey(n);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class Item
/*      */   {
/*      */     public static final int WRITE_MERGED = 1;
/*      */     public static final int WRITE_WIDGET = 2;
/*      */     public static final int WRITE_VALUE = 4;
/* 1894 */     protected ArrayList<PdfDictionary> values = new ArrayList();
/*      */ 
/* 1901 */     protected ArrayList<PdfDictionary> widgets = new ArrayList();
/*      */ 
/* 1908 */     protected ArrayList<PdfIndirectReference> widget_refs = new ArrayList();
/*      */ 
/* 1916 */     protected ArrayList<PdfDictionary> merged = new ArrayList();
/*      */ 
/* 1924 */     protected ArrayList<Integer> page = new ArrayList();
/*      */ 
/* 1930 */     protected ArrayList<Integer> tabOrder = new ArrayList();
/*      */ 
/*      */     public void writeToAll(PdfName key, PdfObject value, int writeFlags)
/*      */     {
/* 1848 */       PdfDictionary curDict = null;
/* 1849 */       if ((writeFlags & 0x1) != 0) {
/* 1850 */         for (int i = 0; i < this.merged.size(); i++) {
/* 1851 */           curDict = getMerged(i);
/* 1852 */           curDict.put(key, value);
/*      */         }
/*      */       }
/* 1855 */       if ((writeFlags & 0x2) != 0) {
/* 1856 */         for (int i = 0; i < this.widgets.size(); i++) {
/* 1857 */           curDict = getWidget(i);
/* 1858 */           curDict.put(key, value);
/*      */         }
/*      */       }
/* 1861 */       if ((writeFlags & 0x4) != 0)
/* 1862 */         for (int i = 0; i < this.values.size(); i++) {
/* 1863 */           curDict = getValue(i);
/* 1864 */           curDict.put(key, value);
/*      */         }
/*      */     }
/*      */ 
/*      */     public void markUsed(AcroFields parentFields, int writeFlags)
/*      */     {
/* 1876 */       if ((writeFlags & 0x4) != 0) {
/* 1877 */         for (int i = 0; i < size(); i++) {
/* 1878 */           parentFields.markUsed(getValue(i));
/*      */         }
/*      */       }
/* 1881 */       if ((writeFlags & 0x2) != 0)
/* 1882 */         for (int i = 0; i < size(); i++)
/* 1883 */           parentFields.markUsed(getWidget(i));
/*      */     }
/*      */ 
/*      */     public int size()
/*      */     {
/* 1940 */       return this.values.size();
/*      */     }
/*      */ 
/*      */     void remove(int killIdx)
/*      */     {
/* 1951 */       this.values.remove(killIdx);
/* 1952 */       this.widgets.remove(killIdx);
/* 1953 */       this.widget_refs.remove(killIdx);
/* 1954 */       this.merged.remove(killIdx);
/* 1955 */       this.page.remove(killIdx);
/* 1956 */       this.tabOrder.remove(killIdx);
/*      */     }
/*      */ 
/*      */     public PdfDictionary getValue(int idx)
/*      */     {
/* 1967 */       return (PdfDictionary)this.values.get(idx);
/*      */     }
/*      */ 
/*      */     void addValue(PdfDictionary value)
/*      */     {
/* 1977 */       this.values.add(value);
/*      */     }
/*      */ 
/*      */     public PdfDictionary getWidget(int idx)
/*      */     {
/* 1988 */       return (PdfDictionary)this.widgets.get(idx);
/*      */     }
/*      */ 
/*      */     void addWidget(PdfDictionary widget)
/*      */     {
/* 1998 */       this.widgets.add(widget);
/*      */     }
/*      */ 
/*      */     public PdfIndirectReference getWidgetRef(int idx)
/*      */     {
/* 2009 */       return (PdfIndirectReference)this.widget_refs.get(idx);
/*      */     }
/*      */ 
/*      */     void addWidgetRef(PdfIndirectReference widgRef)
/*      */     {
/* 2019 */       this.widget_refs.add(widgRef);
/*      */     }
/*      */ 
/*      */     public PdfDictionary getMerged(int idx)
/*      */     {
/* 2033 */       return (PdfDictionary)this.merged.get(idx);
/*      */     }
/*      */ 
/*      */     void addMerged(PdfDictionary mergeDict)
/*      */     {
/* 2043 */       this.merged.add(mergeDict);
/*      */     }
/*      */ 
/*      */     public Integer getPage(int idx)
/*      */     {
/* 2054 */       return (Integer)this.page.get(idx);
/*      */     }
/*      */ 
/*      */     void addPage(int pg)
/*      */     {
/* 2064 */       this.page.add(Integer.valueOf(pg));
/*      */     }
/*      */ 
/*      */     void forcePage(int idx, int pg)
/*      */     {
/* 2074 */       this.page.set(idx, Integer.valueOf(pg));
/*      */     }
/*      */ 
/*      */     public Integer getTabOrder(int idx)
/*      */     {
/* 2085 */       return (Integer)this.tabOrder.get(idx);
/*      */     }
/*      */ 
/*      */     void addTabOrder(int order)
/*      */     {
/* 2095 */       this.tabOrder.add(Integer.valueOf(order));
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.AcroFields
 * JD-Core Version:    0.6.2
 */