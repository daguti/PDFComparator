/*      */ package com.itextpdf.text.pdf;
/*      */ 
/*      */ import com.itextpdf.text.ExceptionConverter;
/*      */ import com.itextpdf.text.xml.XmlDomWriter;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.EmptyStackException;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import javax.xml.parsers.DocumentBuilder;
/*      */ import javax.xml.parsers.DocumentBuilderFactory;
/*      */ import javax.xml.parsers.ParserConfigurationException;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.NamedNodeMap;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.NodeList;
/*      */ import org.xml.sax.InputSource;
/*      */ import org.xml.sax.SAXException;
/*      */ 
/*      */ public class XfaForm
/*      */ {
/*      */   private Xml2SomTemplate templateSom;
/*      */   private Node templateNode;
/*      */   private Xml2SomDatasets datasetsSom;
/*      */   private Node datasetsNode;
/*      */   private AcroFieldsSearch acroFieldsSom;
/*      */   private PdfReader reader;
/*      */   private boolean xfaPresent;
/*      */   private Document domDocument;
/*      */   private boolean changed;
/*      */   public static final String XFA_DATA_SCHEMA = "http://www.xfa.org/schema/xfa-data/1.0/";
/*      */ 
/*      */   public XfaForm()
/*      */   {
/*      */   }
/*      */ 
/*      */   public static PdfObject getXfaObject(PdfReader reader)
/*      */   {
/*   93 */     PdfDictionary af = (PdfDictionary)PdfReader.getPdfObjectRelease(reader.getCatalog().get(PdfName.ACROFORM));
/*   94 */     if (af == null) {
/*   95 */       return null;
/*      */     }
/*   97 */     return PdfReader.getPdfObjectRelease(af.get(PdfName.XFA));
/*      */   }
/*      */ 
/*      */   public XfaForm(PdfReader reader)
/*      */     throws IOException, ParserConfigurationException, SAXException
/*      */   {
/*  109 */     this.reader = reader;
/*  110 */     PdfObject xfa = getXfaObject(reader);
/*  111 */     if (xfa == null) {
/*  112 */       this.xfaPresent = false;
/*  113 */       return;
/*      */     }
/*  115 */     this.xfaPresent = true;
/*  116 */     ByteArrayOutputStream bout = new ByteArrayOutputStream();
/*  117 */     if (xfa.isArray()) {
/*  118 */       PdfArray ar = (PdfArray)xfa;
/*  119 */       for (int k = 1; k < ar.size(); k += 2) {
/*  120 */         PdfObject ob = ar.getDirectObject(k);
/*  121 */         if ((ob instanceof PRStream)) {
/*  122 */           byte[] b = PdfReader.getStreamBytes((PRStream)ob);
/*  123 */           bout.write(b);
/*      */         }
/*      */       }
/*      */     }
/*  127 */     else if ((xfa instanceof PRStream)) {
/*  128 */       byte[] b = PdfReader.getStreamBytes((PRStream)xfa);
/*  129 */       bout.write(b);
/*      */     }
/*  131 */     bout.close();
/*  132 */     DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
/*  133 */     fact.setNamespaceAware(true);
/*  134 */     DocumentBuilder db = fact.newDocumentBuilder();
/*  135 */     this.domDocument = db.parse(new ByteArrayInputStream(bout.toByteArray()));
/*  136 */     extractNodes();
/*      */   }
/*      */ 
/*      */   private void extractNodes()
/*      */   {
/*  144 */     Map xfaNodes = extractXFANodes(this.domDocument);
/*      */ 
/*  146 */     if (xfaNodes.containsKey("template")) {
/*  147 */       this.templateNode = ((Node)xfaNodes.get("template"));
/*  148 */       this.templateSom = new Xml2SomTemplate(this.templateNode);
/*      */     }
/*  150 */     if (xfaNodes.containsKey("datasets")) {
/*  151 */       this.datasetsNode = ((Node)xfaNodes.get("datasets"));
/*  152 */       this.datasetsSom = new Xml2SomDatasets(this.datasetsNode.getFirstChild());
/*      */     }
/*  154 */     if (this.datasetsNode == null)
/*  155 */       createDatasetsNode(this.domDocument.getFirstChild());
/*      */   }
/*      */ 
/*      */   public static Map<String, Node> extractXFANodes(Document domDocument) {
/*  159 */     Map xfaNodes = new HashMap();
/*  160 */     Node n = domDocument.getFirstChild();
/*  161 */     while (n.getChildNodes().getLength() == 0) {
/*  162 */       n = n.getNextSibling();
/*      */     }
/*  164 */     n = n.getFirstChild();
/*  165 */     while (n != null) {
/*  166 */       if (n.getNodeType() == 1) {
/*  167 */         String s = n.getLocalName();
/*  168 */         xfaNodes.put(s, n);
/*      */       }
/*  170 */       n = n.getNextSibling();
/*      */     }
/*      */ 
/*  173 */     return xfaNodes;
/*      */   }
/*      */ 
/*      */   private void createDatasetsNode(Node n)
/*      */   {
/*  180 */     while (n.getChildNodes().getLength() == 0) {
/*  181 */       n = n.getNextSibling();
/*      */     }
/*  183 */     if (n != null) {
/*  184 */       Element e = n.getOwnerDocument().createElement("xfa:datasets");
/*  185 */       e.setAttribute("xmlns:xfa", "http://www.xfa.org/schema/xfa-data/1.0/");
/*  186 */       this.datasetsNode = e;
/*  187 */       n.appendChild(this.datasetsNode);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void setXfa(XfaForm form, PdfReader reader, PdfWriter writer)
/*      */     throws IOException
/*      */   {
/*  199 */     PdfDictionary af = (PdfDictionary)PdfReader.getPdfObjectRelease(reader.getCatalog().get(PdfName.ACROFORM));
/*  200 */     if (af == null) {
/*  201 */       return;
/*      */     }
/*  203 */     PdfObject xfa = getXfaObject(reader);
/*  204 */     if (xfa.isArray()) {
/*  205 */       PdfArray ar = (PdfArray)xfa;
/*  206 */       int t = -1;
/*  207 */       int d = -1;
/*  208 */       for (int k = 0; k < ar.size(); k += 2) {
/*  209 */         PdfString s = ar.getAsString(k);
/*  210 */         if ("template".equals(s.toString())) {
/*  211 */           t = k + 1;
/*      */         }
/*  213 */         if ("datasets".equals(s.toString())) {
/*  214 */           d = k + 1;
/*      */         }
/*      */       }
/*  217 */       if ((t > -1) && (d > -1)) {
/*  218 */         reader.killXref(ar.getAsIndirectObject(t));
/*  219 */         reader.killXref(ar.getAsIndirectObject(d));
/*  220 */         PdfStream tStream = new PdfStream(serializeDoc(form.templateNode));
/*  221 */         tStream.flateCompress(writer.getCompressionLevel());
/*  222 */         ar.set(t, writer.addToBody(tStream).getIndirectReference());
/*  223 */         PdfStream dStream = new PdfStream(serializeDoc(form.datasetsNode));
/*  224 */         dStream.flateCompress(writer.getCompressionLevel());
/*  225 */         ar.set(d, writer.addToBody(dStream).getIndirectReference());
/*  226 */         af.put(PdfName.XFA, new PdfArray(ar));
/*  227 */         return;
/*      */       }
/*      */     }
/*  230 */     reader.killXref(af.get(PdfName.XFA));
/*  231 */     PdfStream str = new PdfStream(serializeDoc(form.domDocument));
/*  232 */     str.flateCompress(writer.getCompressionLevel());
/*  233 */     PdfIndirectReference ref = writer.addToBody(str).getIndirectReference();
/*  234 */     af.put(PdfName.XFA, ref);
/*      */   }
/*      */ 
/*      */   public void setXfa(PdfWriter writer)
/*      */     throws IOException
/*      */   {
/*  243 */     setXfa(this, this.reader, writer);
/*      */   }
/*      */ 
/*      */   public static byte[] serializeDoc(Node n)
/*      */     throws IOException
/*      */   {
/*  253 */     XmlDomWriter xw = new XmlDomWriter();
/*  254 */     ByteArrayOutputStream fout = new ByteArrayOutputStream();
/*  255 */     xw.setOutput(fout, null);
/*  256 */     xw.setCanonical(false);
/*  257 */     xw.write(n);
/*  258 */     fout.close();
/*  259 */     return fout.toByteArray();
/*      */   }
/*      */ 
/*      */   public boolean isXfaPresent()
/*      */   {
/*  267 */     return this.xfaPresent;
/*      */   }
/*      */ 
/*      */   public Document getDomDocument()
/*      */   {
/*  275 */     return this.domDocument;
/*      */   }
/*      */ 
/*      */   public String findFieldName(String name, AcroFields af)
/*      */   {
/*  286 */     Map items = af.getFields();
/*  287 */     if (items.containsKey(name))
/*  288 */       return name;
/*  289 */     if (this.acroFieldsSom == null) {
/*  290 */       if ((items.isEmpty()) && (this.xfaPresent))
/*  291 */         this.acroFieldsSom = new AcroFieldsSearch(this.datasetsSom.getName2Node().keySet());
/*      */       else
/*  293 */         this.acroFieldsSom = new AcroFieldsSearch(items.keySet());
/*      */     }
/*  295 */     if (this.acroFieldsSom.getAcroShort2LongName().containsKey(name))
/*  296 */       return (String)this.acroFieldsSom.getAcroShort2LongName().get(name);
/*  297 */     return this.acroFieldsSom.inverseSearchGlobal(Xml2Som.splitParts(name));
/*      */   }
/*      */ 
/*      */   public String findDatasetsName(String name)
/*      */   {
/*  307 */     if (this.datasetsSom.getName2Node().containsKey(name))
/*  308 */       return name;
/*  309 */     return this.datasetsSom.inverseSearchGlobal(Xml2Som.splitParts(name));
/*      */   }
/*      */ 
/*      */   public Node findDatasetsNode(String name)
/*      */   {
/*  319 */     if (name == null)
/*  320 */       return null;
/*  321 */     name = findDatasetsName(name);
/*  322 */     if (name == null)
/*  323 */       return null;
/*  324 */     return (Node)this.datasetsSom.getName2Node().get(name);
/*      */   }
/*      */ 
/*      */   public static String getNodeText(Node n)
/*      */   {
/*  333 */     if (n == null)
/*  334 */       return "";
/*  335 */     return getNodeText(n, "");
/*      */   }
/*      */ 
/*      */   private static String getNodeText(Node n, String name)
/*      */   {
/*  340 */     Node n2 = n.getFirstChild();
/*  341 */     while (n2 != null) {
/*  342 */       if (n2.getNodeType() == 1) {
/*  343 */         name = getNodeText(n2, name);
/*      */       }
/*  345 */       else if (n2.getNodeType() == 3) {
/*  346 */         name = name + n2.getNodeValue();
/*      */       }
/*  348 */       n2 = n2.getNextSibling();
/*      */     }
/*  350 */     return name;
/*      */   }
/*      */ 
/*      */   public void setNodeText(Node n, String text)
/*      */   {
/*  360 */     if (n == null)
/*  361 */       return;
/*  362 */     Node nc = null;
/*  363 */     while ((nc = n.getFirstChild()) != null) {
/*  364 */       n.removeChild(nc);
/*      */     }
/*  366 */     if (n.getAttributes().getNamedItemNS("http://www.xfa.org/schema/xfa-data/1.0/", "dataNode") != null)
/*  367 */       n.getAttributes().removeNamedItemNS("http://www.xfa.org/schema/xfa-data/1.0/", "dataNode");
/*  368 */     n.appendChild(this.domDocument.createTextNode(text));
/*  369 */     this.changed = true;
/*      */   }
/*      */ 
/*      */   public void setXfaPresent(boolean xfaPresent)
/*      */   {
/*  377 */     this.xfaPresent = xfaPresent;
/*      */   }
/*      */ 
/*      */   public void setDomDocument(Document domDocument)
/*      */   {
/*  385 */     this.domDocument = domDocument;
/*  386 */     extractNodes();
/*      */   }
/*      */ 
/*      */   public PdfReader getReader()
/*      */   {
/*  394 */     return this.reader;
/*      */   }
/*      */ 
/*      */   public void setReader(PdfReader reader)
/*      */   {
/*  402 */     this.reader = reader;
/*      */   }
/*      */ 
/*      */   public boolean isChanged()
/*      */   {
/*  410 */     return this.changed;
/*      */   }
/*      */ 
/*      */   public void setChanged(boolean changed)
/*      */   {
/*  418 */     this.changed = changed;
/*      */   }
/*      */ 
/*      */   public Xml2SomTemplate getTemplateSom()
/*      */   {
/* 1073 */     return this.templateSom;
/*      */   }
/*      */ 
/*      */   public void setTemplateSom(Xml2SomTemplate templateSom)
/*      */   {
/* 1081 */     this.templateSom = templateSom;
/*      */   }
/*      */ 
/*      */   public Xml2SomDatasets getDatasetsSom()
/*      */   {
/* 1089 */     return this.datasetsSom;
/*      */   }
/*      */ 
/*      */   public void setDatasetsSom(Xml2SomDatasets datasetsSom)
/*      */   {
/* 1097 */     this.datasetsSom = datasetsSom;
/*      */   }
/*      */ 
/*      */   public AcroFieldsSearch getAcroFieldsSom()
/*      */   {
/* 1105 */     return this.acroFieldsSom;
/*      */   }
/*      */ 
/*      */   public void setAcroFieldsSom(AcroFieldsSearch acroFieldsSom)
/*      */   {
/* 1113 */     this.acroFieldsSom = acroFieldsSom;
/*      */   }
/*      */ 
/*      */   public Node getDatasetsNode()
/*      */   {
/* 1121 */     return this.datasetsNode;
/*      */   }
/*      */ 
/*      */   public void fillXfaForm(File file) throws IOException {
/* 1125 */     fillXfaForm(file, false);
/*      */   }
/*      */   public void fillXfaForm(File file, boolean readOnly) throws IOException {
/* 1128 */     fillXfaForm(new FileInputStream(file), readOnly);
/*      */   }
/*      */ 
/*      */   public void fillXfaForm(InputStream is) throws IOException {
/* 1132 */     fillXfaForm(is, false);
/*      */   }
/*      */   public void fillXfaForm(InputStream is, boolean readOnly) throws IOException {
/* 1135 */     fillXfaForm(new InputSource(is), readOnly);
/*      */   }
/*      */ 
/*      */   public void fillXfaForm(InputSource is) throws IOException {
/* 1139 */     fillXfaForm(is, false);
/*      */   }
/*      */   public void fillXfaForm(InputSource is, boolean readOnly) throws IOException {
/* 1142 */     DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
/*      */     try
/*      */     {
/* 1145 */       DocumentBuilder db = dbf.newDocumentBuilder();
/* 1146 */       Document newdoc = db.parse(is);
/* 1147 */       fillXfaForm(newdoc.getDocumentElement(), readOnly);
/*      */     } catch (ParserConfigurationException e) {
/* 1149 */       throw new ExceptionConverter(e);
/*      */     } catch (SAXException e) {
/* 1151 */       throw new ExceptionConverter(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void fillXfaForm(Node node) {
/* 1156 */     fillXfaForm(node, false);
/*      */   }
/*      */ 
/*      */   public void fillXfaForm(Node node, boolean readOnly)
/*      */   {
/* 1163 */     if (readOnly) {
/* 1164 */       NodeList nodeList = this.domDocument.getElementsByTagName("field");
/* 1165 */       for (int i = 0; i < nodeList.getLength(); i++) {
/* 1166 */         ((Element)nodeList.item(i)).setAttribute("access", "readOnly");
/*      */       }
/*      */     }
/* 1169 */     NodeList allChilds = this.datasetsNode.getChildNodes();
/* 1170 */     int len = allChilds.getLength();
/* 1171 */     Node data = null;
/* 1172 */     for (int k = 0; k < len; k++) {
/* 1173 */       Node n = allChilds.item(k);
/* 1174 */       if ((n.getNodeType() == 1) && (n.getLocalName().equals("data")) && ("http://www.xfa.org/schema/xfa-data/1.0/".equals(n.getNamespaceURI()))) {
/* 1175 */         data = n;
/* 1176 */         break;
/*      */       }
/*      */     }
/* 1179 */     if (data == null) {
/* 1180 */       data = this.datasetsNode.getOwnerDocument().createElementNS("http://www.xfa.org/schema/xfa-data/1.0/", "xfa:data");
/* 1181 */       this.datasetsNode.appendChild(data);
/*      */     }
/* 1183 */     NodeList list = data.getChildNodes();
/* 1184 */     if (list.getLength() == 0) {
/* 1185 */       data.appendChild(this.domDocument.importNode(node, true));
/*      */     }
/*      */     else
/*      */     {
/* 1190 */       Node firstNode = getFirstElementNode(data);
/* 1191 */       if (firstNode != null)
/* 1192 */         data.replaceChild(this.domDocument.importNode(node, true), firstNode);
/*      */     }
/* 1194 */     extractNodes();
/* 1195 */     setChanged(true);
/*      */   }
/*      */ 
/*      */   private Node getFirstElementNode(Node src) {
/* 1199 */     Node result = null;
/* 1200 */     NodeList list = src.getChildNodes();
/* 1201 */     for (int i = 0; i < list.getLength(); i++) {
/* 1202 */       if (list.item(i).getNodeType() == 1) {
/* 1203 */         result = list.item(i);
/* 1204 */         break;
/*      */       }
/*      */     }
/* 1207 */     return result;
/*      */   }
/*      */ 
/*      */   public static class Xml2SomTemplate extends XfaForm.Xml2Som
/*      */   {
/*      */     private boolean dynamicForm;
/*      */     private int templateLevel;
/*      */ 
/*      */     public Xml2SomTemplate(Node n)
/*      */     {
/*  933 */       this.order = new ArrayList();
/*  934 */       this.name2Node = new HashMap();
/*  935 */       this.stack = new XfaForm.Stack2();
/*  936 */       this.anform = 0;
/*  937 */       this.templateLevel = 0;
/*  938 */       this.inverseSearch = new HashMap();
/*  939 */       processTemplate(n, null);
/*      */     }
/*      */ 
/*      */     public String getFieldType(String s)
/*      */     {
/*  948 */       Node n = (Node)this.name2Node.get(s);
/*  949 */       if (n == null)
/*  950 */         return null;
/*  951 */       if ("exclGroup".equals(n.getLocalName()))
/*  952 */         return "exclGroup";
/*  953 */       Node ui = n.getFirstChild();
/*  954 */       while ((ui != null) && (
/*  955 */         (ui.getNodeType() != 1) || (!"ui".equals(ui.getLocalName()))))
/*      */       {
/*  958 */         ui = ui.getNextSibling();
/*      */       }
/*  960 */       if (ui == null)
/*  961 */         return null;
/*  962 */       Node type = ui.getFirstChild();
/*  963 */       while (type != null) {
/*  964 */         if ((type.getNodeType() == 1) && ((!"extras".equals(type.getLocalName())) || (!"picture".equals(type.getLocalName())))) {
/*  965 */           return type.getLocalName();
/*      */         }
/*  967 */         type = type.getNextSibling();
/*      */       }
/*  969 */       return null;
/*      */     }
/*      */ 
/*      */     private void processTemplate(Node n, HashMap<String, Integer> ff) {
/*  973 */       if (ff == null)
/*  974 */         ff = new HashMap();
/*  975 */       HashMap ss = new HashMap();
/*  976 */       Node n2 = n.getFirstChild();
/*  977 */       while (n2 != null) {
/*  978 */         if (n2.getNodeType() == 1) {
/*  979 */           String s = n2.getLocalName();
/*  980 */           if ("subform".equals(s)) {
/*  981 */             Node name = n2.getAttributes().getNamedItem("name");
/*  982 */             String nn = "#subform";
/*  983 */             boolean annon = true;
/*  984 */             if (name != null) {
/*  985 */               nn = escapeSom(name.getNodeValue());
/*  986 */               annon = false;
/*      */             }
/*      */             Integer i;
/*  989 */             if (annon) {
/*  990 */               Integer i = Integer.valueOf(this.anform);
/*  991 */               this.anform += 1;
/*      */             }
/*      */             else {
/*  994 */               i = (Integer)ss.get(nn);
/*  995 */               if (i == null)
/*  996 */                 i = Integer.valueOf(0);
/*      */               else
/*  998 */                 i = Integer.valueOf(i.intValue() + 1);
/*  999 */               ss.put(nn, i);
/*      */             }
/* 1001 */             this.stack.push(nn + "[" + i.toString() + "]");
/* 1002 */             this.templateLevel += 1;
/* 1003 */             if (annon)
/* 1004 */               processTemplate(n2, ff);
/*      */             else
/* 1006 */               processTemplate(n2, null);
/* 1007 */             this.templateLevel -= 1;
/* 1008 */             this.stack.pop();
/*      */           }
/* 1010 */           else if (("field".equals(s)) || ("exclGroup".equals(s))) {
/* 1011 */             Node name = n2.getAttributes().getNamedItem("name");
/* 1012 */             if (name != null) {
/* 1013 */               String nn = escapeSom(name.getNodeValue());
/* 1014 */               Integer i = (Integer)ff.get(nn);
/* 1015 */               if (i == null)
/* 1016 */                 i = Integer.valueOf(0);
/*      */               else
/* 1018 */                 i = Integer.valueOf(i.intValue() + 1);
/* 1019 */               ff.put(nn, i);
/* 1020 */               this.stack.push(nn + "[" + i.toString() + "]");
/* 1021 */               String unstack = printStack();
/* 1022 */               this.order.add(unstack);
/* 1023 */               inverseSearchAdd(unstack);
/* 1024 */               this.name2Node.put(unstack, n2);
/* 1025 */               this.stack.pop();
/*      */             }
/*      */           }
/* 1028 */           else if ((!this.dynamicForm) && (this.templateLevel > 0) && ("occur".equals(s))) {
/* 1029 */             int initial = 1;
/* 1030 */             int min = 1;
/* 1031 */             int max = 1;
/* 1032 */             Node a = n2.getAttributes().getNamedItem("initial");
/* 1033 */             if (a != null) try {
/* 1034 */                 initial = Integer.parseInt(a.getNodeValue().trim()); } catch (Exception e) {
/*      */               } a = n2.getAttributes().getNamedItem("min");
/* 1036 */             if (a != null) try {
/* 1037 */                 min = Integer.parseInt(a.getNodeValue().trim()); } catch (Exception e) {
/*      */               } a = n2.getAttributes().getNamedItem("max");
/* 1039 */             if (a != null) try {
/* 1040 */                 max = Integer.parseInt(a.getNodeValue().trim()); } catch (Exception e) {
/*      */               } if ((initial != min) || (min != max))
/* 1042 */               this.dynamicForm = true;
/*      */           }
/*      */         }
/* 1045 */         n2 = n2.getNextSibling();
/*      */       }
/*      */     }
/*      */ 
/*      */     public boolean isDynamicForm()
/*      */     {
/* 1056 */       return this.dynamicForm;
/*      */     }
/*      */ 
/*      */     public void setDynamicForm(boolean dynamicForm)
/*      */     {
/* 1064 */       this.dynamicForm = dynamicForm;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class AcroFieldsSearch extends XfaForm.Xml2Som
/*      */   {
/*      */     private HashMap<String, String> acroShort2LongName;
/*      */ 
/*      */     public AcroFieldsSearch(Collection<String> items)
/*      */     {
/*  892 */       this.inverseSearch = new HashMap();
/*  893 */       this.acroShort2LongName = new HashMap();
/*  894 */       for (String string : items) {
/*  895 */         String itemName = string;
/*  896 */         String itemShort = getShortName(itemName);
/*  897 */         this.acroShort2LongName.put(itemShort, itemName);
/*  898 */         inverseSearchAdd(this.inverseSearch, splitParts(itemShort), itemName);
/*      */       }
/*      */     }
/*      */ 
/*      */     public HashMap<String, String> getAcroShort2LongName()
/*      */     {
/*  908 */       return this.acroShort2LongName;
/*      */     }
/*      */ 
/*      */     public void setAcroShort2LongName(HashMap<String, String> acroShort2LongName)
/*      */     {
/*  917 */       this.acroShort2LongName = acroShort2LongName;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class Xml2SomDatasets extends XfaForm.Xml2Som
/*      */   {
/*      */     public Xml2SomDatasets(Node n)
/*      */     {
/*  775 */       this.order = new ArrayList();
/*  776 */       this.name2Node = new HashMap();
/*  777 */       this.stack = new XfaForm.Stack2();
/*  778 */       this.anform = 0;
/*  779 */       this.inverseSearch = new HashMap();
/*  780 */       processDatasetsInternal(n);
/*      */     }
/*      */ 
/*      */     public Node insertNode(Node n, String shortName)
/*      */     {
/*  790 */       XfaForm.Stack2 stack = splitParts(shortName);
/*  791 */       Document doc = n.getOwnerDocument();
/*  792 */       Node n2 = null;
/*  793 */       n = n.getFirstChild();
/*  794 */       while (n.getNodeType() != 1)
/*  795 */         n = n.getNextSibling();
/*  796 */       for (int k = 0; k < stack.size(); k++) {
/*  797 */         String part = (String)stack.get(k);
/*  798 */         int idx = part.lastIndexOf('[');
/*  799 */         String name = part.substring(0, idx);
/*  800 */         idx = Integer.parseInt(part.substring(idx + 1, part.length() - 1));
/*  801 */         int found = -1;
/*  802 */         for (n2 = n.getFirstChild(); n2 != null; n2 = n2.getNextSibling()) {
/*  803 */           if (n2.getNodeType() == 1) {
/*  804 */             String s = escapeSom(n2.getLocalName());
/*  805 */             if (s.equals(name)) {
/*  806 */               found++;
/*  807 */               if (found == idx)
/*      */                 break;
/*      */             }
/*      */           }
/*      */         }
/*  812 */         for (; found < idx; found++) {
/*  813 */           n2 = doc.createElementNS(null, name);
/*  814 */           n2 = n.appendChild(n2);
/*  815 */           Node attr = doc.createAttributeNS("http://www.xfa.org/schema/xfa-data/1.0/", "dataNode");
/*  816 */           attr.setNodeValue("dataGroup");
/*  817 */           n2.getAttributes().setNamedItemNS(attr);
/*      */         }
/*  819 */         n = n2;
/*      */       }
/*  821 */       inverseSearchAdd(this.inverseSearch, stack, shortName);
/*  822 */       this.name2Node.put(shortName, n2);
/*  823 */       this.order.add(shortName);
/*  824 */       return n2;
/*      */     }
/*      */ 
/*      */     private static boolean hasChildren(Node n) {
/*  828 */       Node dataNodeN = n.getAttributes().getNamedItemNS("http://www.xfa.org/schema/xfa-data/1.0/", "dataNode");
/*  829 */       if (dataNodeN != null) {
/*  830 */         String dataNode = dataNodeN.getNodeValue();
/*  831 */         if ("dataGroup".equals(dataNode))
/*  832 */           return true;
/*  833 */         if ("dataValue".equals(dataNode))
/*  834 */           return false;
/*      */       }
/*  836 */       if (!n.hasChildNodes())
/*  837 */         return false;
/*  838 */       Node n2 = n.getFirstChild();
/*  839 */       while (n2 != null) {
/*  840 */         if (n2.getNodeType() == 1) {
/*  841 */           return true;
/*      */         }
/*  843 */         n2 = n2.getNextSibling();
/*      */       }
/*  845 */       return false;
/*      */     }
/*      */ 
/*      */     private void processDatasetsInternal(Node n) {
/*  849 */       if (n != null) {
/*  850 */         HashMap ss = new HashMap();
/*  851 */         Node n2 = n.getFirstChild();
/*  852 */         while (n2 != null) {
/*  853 */           if (n2.getNodeType() == 1) {
/*  854 */             String s = escapeSom(n2.getLocalName());
/*  855 */             Integer i = (Integer)ss.get(s);
/*  856 */             if (i == null)
/*  857 */               i = Integer.valueOf(0);
/*      */             else
/*  859 */               i = Integer.valueOf(i.intValue() + 1);
/*  860 */             ss.put(s, i);
/*  861 */             if (hasChildren(n2)) {
/*  862 */               this.stack.push(s + "[" + i.toString() + "]");
/*  863 */               processDatasetsInternal(n2);
/*  864 */               this.stack.pop();
/*      */             }
/*      */             else {
/*  867 */               this.stack.push(s + "[" + i.toString() + "]");
/*  868 */               String unstack = printStack();
/*  869 */               this.order.add(unstack);
/*  870 */               inverseSearchAdd(unstack);
/*  871 */               this.name2Node.put(unstack, n2);
/*  872 */               this.stack.pop();
/*      */             }
/*      */           }
/*  875 */           n2 = n2.getNextSibling();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class Xml2Som
/*      */   {
/*      */     protected ArrayList<String> order;
/*      */     protected HashMap<String, Node> name2Node;
/*      */     protected HashMap<String, XfaForm.InverseStore> inverseSearch;
/*      */     protected XfaForm.Stack2<String> stack;
/*      */     protected int anform;
/*      */ 
/*      */     public static String escapeSom(String s)
/*      */     {
/*  542 */       if (s == null)
/*  543 */         return "";
/*  544 */       int idx = s.indexOf('.');
/*  545 */       if (idx < 0)
/*  546 */         return s;
/*  547 */       StringBuffer sb = new StringBuffer();
/*  548 */       int last = 0;
/*  549 */       while (idx >= 0) {
/*  550 */         sb.append(s.substring(last, idx));
/*  551 */         sb.append('\\');
/*  552 */         last = idx;
/*  553 */         idx = s.indexOf('.', idx + 1);
/*      */       }
/*  555 */       sb.append(s.substring(last));
/*  556 */       return sb.toString();
/*      */     }
/*      */ 
/*      */     public static String unescapeSom(String s)
/*      */     {
/*  565 */       int idx = s.indexOf('\\');
/*  566 */       if (idx < 0)
/*  567 */         return s;
/*  568 */       StringBuffer sb = new StringBuffer();
/*  569 */       int last = 0;
/*  570 */       while (idx >= 0) {
/*  571 */         sb.append(s.substring(last, idx));
/*  572 */         last = idx + 1;
/*  573 */         idx = s.indexOf('\\', idx + 1);
/*      */       }
/*  575 */       sb.append(s.substring(last));
/*  576 */       return sb.toString();
/*      */     }
/*      */ 
/*      */     protected String printStack()
/*      */     {
/*  585 */       if (this.stack.empty())
/*  586 */         return "";
/*  587 */       StringBuffer s = new StringBuffer();
/*  588 */       for (int k = 0; k < this.stack.size(); k++)
/*  589 */         s.append('.').append((String)this.stack.get(k));
/*  590 */       return s.substring(1);
/*      */     }
/*      */ 
/*      */     public static String getShortName(String s)
/*      */     {
/*  599 */       int idx = s.indexOf(".#subform[");
/*  600 */       if (idx < 0)
/*  601 */         return s;
/*  602 */       int last = 0;
/*  603 */       StringBuffer sb = new StringBuffer();
/*  604 */       while (idx >= 0) {
/*  605 */         sb.append(s.substring(last, idx));
/*  606 */         idx = s.indexOf("]", idx + 10);
/*  607 */         if (idx < 0)
/*  608 */           return sb.toString();
/*  609 */         last = idx + 1;
/*  610 */         idx = s.indexOf(".#subform[", last);
/*      */       }
/*  612 */       sb.append(s.substring(last));
/*  613 */       return sb.toString();
/*      */     }
/*      */ 
/*      */     public void inverseSearchAdd(String unstack)
/*      */     {
/*  621 */       inverseSearchAdd(this.inverseSearch, this.stack, unstack);
/*      */     }
/*      */ 
/*      */     public static void inverseSearchAdd(HashMap<String, XfaForm.InverseStore> inverseSearch, XfaForm.Stack2<String> stack, String unstack)
/*      */     {
/*  631 */       String last = (String)stack.peek();
/*  632 */       XfaForm.InverseStore store = (XfaForm.InverseStore)inverseSearch.get(last);
/*  633 */       if (store == null) {
/*  634 */         store = new XfaForm.InverseStore();
/*  635 */         inverseSearch.put(last, store);
/*      */       }
/*  637 */       for (int k = stack.size() - 2; k >= 0; k--) {
/*  638 */         last = (String)stack.get(k);
/*      */ 
/*  640 */         int idx = store.part.indexOf(last);
/*      */         XfaForm.InverseStore store2;
/*  641 */         if (idx < 0) {
/*  642 */           store.part.add(last);
/*  643 */           XfaForm.InverseStore store2 = new XfaForm.InverseStore();
/*  644 */           store.follow.add(store2);
/*      */         }
/*      */         else {
/*  647 */           store2 = (XfaForm.InverseStore)store.follow.get(idx);
/*  648 */         }store = store2;
/*      */       }
/*  650 */       store.part.add("");
/*  651 */       store.follow.add(unstack);
/*      */     }
/*      */ 
/*      */     public String inverseSearchGlobal(ArrayList<String> parts)
/*      */     {
/*  660 */       if (parts.isEmpty())
/*  661 */         return null;
/*  662 */       XfaForm.InverseStore store = (XfaForm.InverseStore)this.inverseSearch.get(parts.get(parts.size() - 1));
/*  663 */       if (store == null)
/*  664 */         return null;
/*  665 */       for (int k = parts.size() - 2; k >= 0; k--) {
/*  666 */         String part = (String)parts.get(k);
/*  667 */         int idx = store.part.indexOf(part);
/*  668 */         if (idx < 0) {
/*  669 */           if (store.isSimilar(part))
/*  670 */             return null;
/*  671 */           return store.getDefaultName();
/*      */         }
/*  673 */         store = (XfaForm.InverseStore)store.follow.get(idx);
/*      */       }
/*  675 */       return store.getDefaultName();
/*      */     }
/*      */ 
/*      */     public static XfaForm.Stack2<String> splitParts(String name)
/*      */     {
/*  684 */       while (name.startsWith("."))
/*  685 */         name = name.substring(1);
/*  686 */       XfaForm.Stack2 parts = new XfaForm.Stack2();
/*  687 */       int last = 0;
/*  688 */       int pos = 0;
/*      */       while (true)
/*      */       {
/*  691 */         pos = last;
/*      */         while (true) {
/*  693 */           pos = name.indexOf('.', pos);
/*  694 */           if (pos < 0)
/*      */             break;
/*  696 */           if (name.charAt(pos - 1) != '\\') break;
/*  697 */           pos++;
/*      */         }
/*      */ 
/*  701 */         if (pos < 0)
/*      */           break;
/*  703 */         String part = name.substring(last, pos);
/*  704 */         if (!part.endsWith("]"))
/*  705 */           part = part + "[0]";
/*  706 */         parts.add(part);
/*  707 */         last = pos + 1;
/*      */       }
/*  709 */       String part = name.substring(last);
/*  710 */       if (!part.endsWith("]"))
/*  711 */         part = part + "[0]";
/*  712 */       parts.add(part);
/*  713 */       return parts;
/*      */     }
/*      */ 
/*      */     public ArrayList<String> getOrder()
/*      */     {
/*  721 */       return this.order;
/*      */     }
/*      */ 
/*      */     public void setOrder(ArrayList<String> order)
/*      */     {
/*  729 */       this.order = order;
/*      */     }
/*      */ 
/*      */     public HashMap<String, Node> getName2Node()
/*      */     {
/*  737 */       return this.name2Node;
/*      */     }
/*      */ 
/*      */     public void setName2Node(HashMap<String, Node> name2Node)
/*      */     {
/*  745 */       this.name2Node = name2Node;
/*      */     }
/*      */ 
/*      */     public HashMap<String, XfaForm.InverseStore> getInverseSearch()
/*      */     {
/*  753 */       return this.inverseSearch;
/*      */     }
/*      */ 
/*      */     public void setInverseSearch(HashMap<String, XfaForm.InverseStore> inverseSearch)
/*      */     {
/*  761 */       this.inverseSearch = inverseSearch;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class Stack2<T> extends ArrayList<T>
/*      */   {
/*      */     private static final long serialVersionUID = -7451476576174095212L;
/*      */ 
/*      */     public T peek()
/*      */     {
/*  475 */       if (size() == 0)
/*  476 */         throw new EmptyStackException();
/*  477 */       return get(size() - 1);
/*      */     }
/*      */ 
/*      */     public T pop()
/*      */     {
/*  485 */       if (size() == 0)
/*  486 */         throw new EmptyStackException();
/*  487 */       Object ret = get(size() - 1);
/*  488 */       remove(size() - 1);
/*  489 */       return ret;
/*      */     }
/*      */ 
/*      */     public T push(T item)
/*      */     {
/*  498 */       add(item);
/*  499 */       return item;
/*      */     }
/*      */ 
/*      */     public boolean empty()
/*      */     {
/*  507 */       return size() == 0;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class InverseStore
/*      */   {
/*  426 */     protected ArrayList<String> part = new ArrayList();
/*  427 */     protected ArrayList<Object> follow = new ArrayList();
/*      */ 
/*      */     public String getDefaultName()
/*      */     {
/*  435 */       InverseStore store = this;
/*      */       while (true) {
/*  437 */         Object obj = store.follow.get(0);
/*  438 */         if ((obj instanceof String))
/*  439 */           return (String)obj;
/*  440 */         store = (InverseStore)obj;
/*      */       }
/*      */     }
/*      */ 
/*      */     public boolean isSimilar(String name)
/*      */     {
/*  453 */       int idx = name.indexOf('[');
/*  454 */       name = name.substring(0, idx + 1);
/*  455 */       for (int k = 0; k < this.part.size(); k++) {
/*  456 */         if (((String)this.part.get(k)).startsWith(name))
/*  457 */           return true;
/*      */       }
/*  459 */       return false;
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.XfaForm
 * JD-Core Version:    0.6.2
 */