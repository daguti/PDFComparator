/*     */ package org.apache.pdfbox.pdmodel.fdf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSInteger;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.cos.COSString;
/*     */ import org.apache.pdfbox.pdmodel.common.COSArrayList;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.pdmodel.common.PDTextStream;
/*     */ import org.apache.pdfbox.pdmodel.interactive.action.PDActionFactory;
/*     */ import org.apache.pdfbox.pdmodel.interactive.action.PDAdditionalActions;
/*     */ import org.apache.pdfbox.pdmodel.interactive.action.type.PDAction;
/*     */ import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceDictionary;
/*     */ import org.apache.pdfbox.util.XMLUtil;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class FDFField
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSDictionary field;
/*     */ 
/*     */   public FDFField()
/*     */   {
/*  65 */     this.field = new COSDictionary();
/*     */   }
/*     */ 
/*     */   public FDFField(COSDictionary f)
/*     */   {
/*  75 */     this.field = f;
/*     */   }
/*     */ 
/*     */   public FDFField(Element fieldXML)
/*     */     throws IOException
/*     */   {
/*  86 */     this();
/*  87 */     setPartialFieldName(fieldXML.getAttribute("name"));
/*  88 */     NodeList nodeList = fieldXML.getChildNodes();
/*  89 */     List kids = new ArrayList();
/*  90 */     for (int i = 0; i < nodeList.getLength(); i++)
/*     */     {
/*  92 */       Node node = nodeList.item(i);
/*  93 */       if ((node instanceof Element))
/*     */       {
/*  95 */         Element child = (Element)node;
/*  96 */         if (child.getTagName().equals("value"))
/*     */         {
/*  98 */           setValue(XMLUtil.getNodeValue(child));
/*     */         }
/* 100 */         else if (child.getTagName().equals("value-richtext"))
/*     */         {
/* 102 */           setRichText(new PDTextStream(XMLUtil.getNodeValue(child)));
/*     */         }
/* 104 */         else if (child.getTagName().equals("field"))
/*     */         {
/* 106 */           kids.add(new FDFField(child));
/*     */         }
/*     */       }
/*     */     }
/* 110 */     if (kids.size() > 0)
/*     */     {
/* 112 */       setKids(kids);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void writeXML(Writer output)
/*     */     throws IOException
/*     */   {
/* 126 */     output.write("<field name=\"" + getPartialFieldName() + "\">\n");
/* 127 */     Object value = getValue();
/* 128 */     if (value != null)
/*     */     {
/* 130 */       if ((value instanceof String))
/*     */       {
/* 132 */         output.write("<value>" + escapeXML((String)value) + "</value>\n");
/*     */       }
/* 134 */       else if ((value instanceof PDTextStream))
/*     */       {
/* 136 */         output.write("<value>" + escapeXML(((PDTextStream)value).getAsString()) + "</value>\n");
/*     */       }
/*     */     }
/* 139 */     PDTextStream rt = getRichText();
/* 140 */     if (rt != null)
/*     */     {
/* 142 */       output.write("<value-richtext>" + escapeXML(rt.getAsString()) + "</value-richtext>\n");
/*     */     }
/* 144 */     List kids = getKids();
/* 145 */     if (kids != null)
/*     */     {
/* 147 */       for (int i = 0; i < kids.size(); i++)
/*     */       {
/* 149 */         ((FDFField)kids.get(i)).writeXML(output);
/*     */       }
/*     */     }
/* 152 */     output.write("</field>\n");
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/* 162 */     return this.field;
/*     */   }
/*     */ 
/*     */   public COSDictionary getCOSDictionary()
/*     */   {
/* 172 */     return this.field;
/*     */   }
/*     */ 
/*     */   public List<FDFField> getKids()
/*     */   {
/* 183 */     COSArray kids = (COSArray)this.field.getDictionaryObject(COSName.KIDS);
/* 184 */     List retval = null;
/* 185 */     if (kids != null)
/*     */     {
/* 187 */       List actuals = new ArrayList();
/* 188 */       for (int i = 0; i < kids.size(); i++)
/*     */       {
/* 190 */         actuals.add(new FDFField((COSDictionary)kids.getObject(i)));
/*     */       }
/* 192 */       retval = new COSArrayList(actuals, kids);
/*     */     }
/* 194 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setKids(List<FDFField> kids)
/*     */   {
/* 204 */     this.field.setItem(COSName.KIDS, COSArrayList.converterToCOSArray(kids));
/*     */   }
/*     */ 
/*     */   public String getPartialFieldName()
/*     */   {
/* 218 */     return this.field.getString(COSName.T);
/*     */   }
/*     */ 
/*     */   public void setPartialFieldName(String partial)
/*     */   {
/* 228 */     this.field.setString(COSName.T, partial);
/*     */   }
/*     */ 
/*     */   public Object getValue()
/*     */     throws IOException
/*     */   {
/* 243 */     Object retval = null;
/* 244 */     COSBase value = this.field.getDictionaryObject(COSName.V);
/* 245 */     if ((value instanceof COSName))
/*     */     {
/* 247 */       retval = ((COSName)value).getName();
/*     */     }
/* 249 */     else if ((value instanceof COSArray))
/*     */     {
/* 251 */       retval = COSArrayList.convertCOSStringCOSArrayToList((COSArray)value);
/*     */     }
/* 253 */     else if (((value instanceof COSString)) || ((value instanceof COSStream)))
/*     */     {
/* 255 */       retval = PDTextStream.createTextStream(value);
/*     */     }
/* 257 */     else if (value != null)
/*     */     {
/* 263 */       throw new IOException("Error:Unknown type for field import" + value);
/*     */     }
/* 265 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setValue(Object value)
/*     */     throws IOException
/*     */   {
/* 278 */     COSBase cos = null;
/* 279 */     if ((value instanceof List))
/*     */     {
/* 281 */       cos = COSArrayList.convertStringListToCOSStringCOSArray((List)value);
/*     */     }
/* 283 */     else if ((value instanceof String))
/*     */     {
/* 285 */       cos = COSName.getPDFName((String)value);
/*     */     }
/* 287 */     else if ((value instanceof COSObjectable))
/*     */     {
/* 289 */       cos = ((COSObjectable)value).getCOSObject();
/*     */     }
/* 291 */     else if (value != null)
/*     */     {
/* 297 */       throw new IOException("Error:Unknown type for field import" + value);
/*     */     }
/* 299 */     this.field.setItem(COSName.V, cos);
/*     */   }
/*     */ 
/*     */   public Integer getFieldFlags()
/*     */   {
/* 310 */     Integer retval = null;
/* 311 */     COSNumber ff = (COSNumber)this.field.getDictionaryObject(COSName.FF);
/* 312 */     if (ff != null)
/*     */     {
/* 314 */       retval = new Integer(ff.intValue());
/*     */     }
/* 316 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setFieldFlags(Integer ff)
/*     */   {
/* 327 */     COSInteger value = null;
/* 328 */     if (ff != null)
/*     */     {
/* 330 */       value = COSInteger.get(ff.intValue());
/*     */     }
/* 332 */     this.field.setItem(COSName.FF, value);
/*     */   }
/*     */ 
/*     */   public void setFieldFlags(int ff)
/*     */   {
/* 343 */     this.field.setInt(COSName.FF, ff);
/*     */   }
/*     */ 
/*     */   public Integer getSetFieldFlags()
/*     */   {
/* 354 */     Integer retval = null;
/* 355 */     COSNumber ff = (COSNumber)this.field.getDictionaryObject(COSName.SET_FF);
/* 356 */     if (ff != null)
/*     */     {
/* 358 */       retval = new Integer(ff.intValue());
/*     */     }
/* 360 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setSetFieldFlags(Integer ff)
/*     */   {
/* 371 */     COSInteger value = null;
/* 372 */     if (ff != null)
/*     */     {
/* 374 */       value = COSInteger.get(ff.intValue());
/*     */     }
/* 376 */     this.field.setItem(COSName.SET_FF, value);
/*     */   }
/*     */ 
/*     */   public void setSetFieldFlags(int ff)
/*     */   {
/* 387 */     this.field.setInt(COSName.SET_FF, ff);
/*     */   }
/*     */ 
/*     */   public Integer getClearFieldFlags()
/*     */   {
/* 398 */     Integer retval = null;
/* 399 */     COSNumber ff = (COSNumber)this.field.getDictionaryObject(COSName.CLR_FF);
/* 400 */     if (ff != null)
/*     */     {
/* 402 */       retval = new Integer(ff.intValue());
/*     */     }
/* 404 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setClearFieldFlags(Integer ff)
/*     */   {
/* 415 */     COSInteger value = null;
/* 416 */     if (ff != null)
/*     */     {
/* 418 */       value = COSInteger.get(ff.intValue());
/*     */     }
/* 420 */     this.field.setItem(COSName.CLR_FF, value);
/*     */   }
/*     */ 
/*     */   public void setClearFieldFlags(int ff)
/*     */   {
/* 431 */     this.field.setInt(COSName.CLR_FF, ff);
/*     */   }
/*     */ 
/*     */   public Integer getWidgetFieldFlags()
/*     */   {
/* 442 */     Integer retval = null;
/* 443 */     COSNumber f = (COSNumber)this.field.getDictionaryObject("F");
/* 444 */     if (f != null)
/*     */     {
/* 446 */       retval = new Integer(f.intValue());
/*     */     }
/* 448 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setWidgetFieldFlags(Integer f)
/*     */   {
/* 459 */     COSInteger value = null;
/* 460 */     if (f != null)
/*     */     {
/* 462 */       value = COSInteger.get(f.intValue());
/*     */     }
/* 464 */     this.field.setItem(COSName.F, value);
/*     */   }
/*     */ 
/*     */   public void setWidgetFieldFlags(int f)
/*     */   {
/* 475 */     this.field.setInt(COSName.F, f);
/*     */   }
/*     */ 
/*     */   public Integer getSetWidgetFieldFlags()
/*     */   {
/* 486 */     Integer retval = null;
/* 487 */     COSNumber ff = (COSNumber)this.field.getDictionaryObject(COSName.SET_F);
/* 488 */     if (ff != null)
/*     */     {
/* 490 */       retval = new Integer(ff.intValue());
/*     */     }
/* 492 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setSetWidgetFieldFlags(Integer ff)
/*     */   {
/* 503 */     COSInteger value = null;
/* 504 */     if (ff != null)
/*     */     {
/* 506 */       value = COSInteger.get(ff.intValue());
/*     */     }
/* 508 */     this.field.setItem(COSName.SET_F, value);
/*     */   }
/*     */ 
/*     */   public void setSetWidgetFieldFlags(int ff)
/*     */   {
/* 519 */     this.field.setInt(COSName.SET_F, ff);
/*     */   }
/*     */ 
/*     */   public Integer getClearWidgetFieldFlags()
/*     */   {
/* 530 */     Integer retval = null;
/* 531 */     COSNumber ff = (COSNumber)this.field.getDictionaryObject(COSName.CLR_F);
/* 532 */     if (ff != null)
/*     */     {
/* 534 */       retval = new Integer(ff.intValue());
/*     */     }
/* 536 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setClearWidgetFieldFlags(Integer ff)
/*     */   {
/* 547 */     COSInteger value = null;
/* 548 */     if (ff != null)
/*     */     {
/* 550 */       value = COSInteger.get(ff.intValue());
/*     */     }
/* 552 */     this.field.setItem(COSName.CLR_F, value);
/*     */   }
/*     */ 
/*     */   public void setClearWidgetFieldFlags(int ff)
/*     */   {
/* 563 */     this.field.setInt(COSName.CLR_F, ff);
/*     */   }
/*     */ 
/*     */   public PDAppearanceDictionary getAppearanceDictionary()
/*     */   {
/* 574 */     PDAppearanceDictionary retval = null;
/* 575 */     COSDictionary dict = (COSDictionary)this.field.getDictionaryObject(COSName.AP);
/* 576 */     if (dict != null)
/*     */     {
/* 578 */       retval = new PDAppearanceDictionary(dict);
/*     */     }
/* 580 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setAppearanceDictionary(PDAppearanceDictionary ap)
/*     */   {
/* 590 */     this.field.setItem(COSName.AP, ap);
/*     */   }
/*     */ 
/*     */   public FDFNamedPageReference getAppearanceStreamReference()
/*     */   {
/* 600 */     FDFNamedPageReference retval = null;
/* 601 */     COSDictionary ref = (COSDictionary)this.field.getDictionaryObject(COSName.AP_REF);
/* 602 */     if (ref != null)
/*     */     {
/* 604 */       retval = new FDFNamedPageReference(ref);
/*     */     }
/* 606 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setAppearanceStreamReference(FDFNamedPageReference ref)
/*     */   {
/* 616 */     this.field.setItem(COSName.AP_REF, ref);
/*     */   }
/*     */ 
/*     */   public FDFIconFit getIconFit()
/*     */   {
/* 626 */     FDFIconFit retval = null;
/* 627 */     COSDictionary dic = (COSDictionary)this.field.getDictionaryObject("IF");
/* 628 */     if (dic != null)
/*     */     {
/* 630 */       retval = new FDFIconFit(dic);
/*     */     }
/* 632 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setIconFit(FDFIconFit fit)
/*     */   {
/* 642 */     this.field.setItem("IF", fit);
/*     */   }
/*     */ 
/*     */   public List getOptions()
/*     */   {
/* 653 */     List retval = null;
/* 654 */     COSArray array = (COSArray)this.field.getDictionaryObject(COSName.OPT);
/* 655 */     if (array != null)
/*     */     {
/* 657 */       List objects = new ArrayList();
/* 658 */       for (int i = 0; i < array.size(); i++)
/*     */       {
/* 660 */         COSBase next = array.getObject(i);
/* 661 */         if ((next instanceof COSString))
/*     */         {
/* 663 */           objects.add(((COSString)next).getString());
/*     */         }
/*     */         else
/*     */         {
/* 667 */           COSArray value = (COSArray)next;
/* 668 */           objects.add(new FDFOptionElement(value));
/*     */         }
/*     */       }
/* 671 */       retval = new COSArrayList(objects, array);
/*     */     }
/* 673 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setOptions(List options)
/*     */   {
/* 684 */     COSArray value = COSArrayList.converterToCOSArray(options);
/* 685 */     this.field.setItem(COSName.OPT, value);
/*     */   }
/*     */ 
/*     */   public PDAction getAction()
/*     */   {
/* 695 */     return PDActionFactory.createAction((COSDictionary)this.field.getDictionaryObject(COSName.A));
/*     */   }
/*     */ 
/*     */   public void setAction(PDAction a)
/*     */   {
/* 705 */     this.field.setItem(COSName.A, a);
/*     */   }
/*     */ 
/*     */   public PDAdditionalActions getAdditionalActions()
/*     */   {
/* 716 */     PDAdditionalActions retval = null;
/* 717 */     COSDictionary dict = (COSDictionary)this.field.getDictionaryObject(COSName.AA);
/* 718 */     if (dict != null)
/*     */     {
/* 720 */       retval = new PDAdditionalActions(dict);
/*     */     }
/*     */ 
/* 723 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setAdditionalActions(PDAdditionalActions aa)
/*     */   {
/* 733 */     this.field.setItem(COSName.AA, aa);
/*     */   }
/*     */ 
/*     */   public PDTextStream getRichText()
/*     */   {
/* 743 */     COSBase rv = this.field.getDictionaryObject(COSName.RV);
/* 744 */     return PDTextStream.createTextStream(rv);
/*     */   }
/*     */ 
/*     */   public void setRichText(PDTextStream rv)
/*     */   {
/* 754 */     this.field.setItem(COSName.RV, rv);
/*     */   }
/*     */ 
/*     */   private String escapeXML(String input)
/*     */   {
/* 766 */     StringBuilder escapedXML = new StringBuilder();
/* 767 */     for (int i = 0; i < input.length(); i++)
/*     */     {
/* 769 */       char c = input.charAt(i);
/* 770 */       switch (c)
/*     */       {
/*     */       case '<':
/* 773 */         escapedXML.append("&lt;");
/* 774 */         break;
/*     */       case '>':
/* 776 */         escapedXML.append("&gt;");
/* 777 */         break;
/*     */       case '"':
/* 779 */         escapedXML.append("&quot;");
/* 780 */         break;
/*     */       case '&':
/* 782 */         escapedXML.append("&amp;");
/* 783 */         break;
/*     */       case '\'':
/* 785 */         escapedXML.append("&apos;");
/* 786 */         break;
/*     */       default:
/* 788 */         if (c > '~')
/*     */         {
/* 790 */           escapedXML.append("&#" + c + ";");
/*     */         }
/*     */         else
/*     */         {
/* 794 */           escapedXML.append(c);
/*     */         }break;
/*     */       }
/*     */     }
/* 798 */     return escapedXML.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.fdf.FDFField
 * JD-Core Version:    0.6.2
 */