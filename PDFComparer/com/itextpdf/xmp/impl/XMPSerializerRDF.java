/*      */ package com.itextpdf.xmp.impl;
/*      */ 
/*      */ import com.itextpdf.xmp.XMPException;
/*      */ import com.itextpdf.xmp.XMPMeta;
/*      */ import com.itextpdf.xmp.XMPMetaFactory;
/*      */ import com.itextpdf.xmp.XMPSchemaRegistry;
/*      */ import com.itextpdf.xmp.XMPVersionInfo;
/*      */ import com.itextpdf.xmp.options.PropertyOptions;
/*      */ import com.itextpdf.xmp.options.SerializeOptions;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.Set;
/*      */ 
/*      */ public class XMPSerializerRDF
/*      */ {
/*      */   private static final int DEFAULT_PAD = 2048;
/*      */   private static final String PACKET_HEADER = "<?xpacket begin=\"﻿\" id=\"W5M0MpCehiHzreSzNTczkc9d\"?>";
/*      */   private static final String PACKET_TRAILER = "<?xpacket end=\"";
/*      */   private static final String PACKET_TRAILER2 = "\"?>";
/*      */   private static final String RDF_XMPMETA_START = "<x:xmpmeta xmlns:x=\"adobe:ns:meta/\" x:xmptk=\"";
/*      */   private static final String RDF_XMPMETA_END = "</x:xmpmeta>";
/*      */   private static final String RDF_RDF_START = "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">";
/*      */   private static final String RDF_RDF_END = "</rdf:RDF>";
/*      */   private static final String RDF_SCHEMA_START = "<rdf:Description rdf:about=";
/*      */   private static final String RDF_SCHEMA_END = "</rdf:Description>";
/*      */   private static final String RDF_STRUCT_START = "<rdf:Description";
/*      */   private static final String RDF_STRUCT_END = "</rdf:Description>";
/*      */   private static final String RDF_EMPTY_STRUCT = "<rdf:Description/>";
/*   90 */   static final Set RDF_ATTR_QUALIFIER = new HashSet(Arrays.asList(new String[] { "xml:lang", "rdf:resource", "rdf:ID", "rdf:bagID", "rdf:nodeID" }));
/*      */   private XMPMetaImpl xmp;
/*      */   private CountOutputStream outputStream;
/*      */   private OutputStreamWriter writer;
/*      */   private SerializeOptions options;
/*  104 */   private int unicodeSize = 1;
/*      */   private int padding;
/*      */ 
/*      */   public void serialize(XMPMeta xmp, OutputStream out, SerializeOptions options)
/*      */     throws XMPException
/*      */   {
/*      */     try
/*      */     {
/*  124 */       this.outputStream = new CountOutputStream(out);
/*  125 */       this.writer = new OutputStreamWriter(this.outputStream, options.getEncoding());
/*      */ 
/*  127 */       this.xmp = ((XMPMetaImpl)xmp);
/*  128 */       this.options = options;
/*  129 */       this.padding = options.getPadding();
/*      */ 
/*  131 */       this.writer = new OutputStreamWriter(this.outputStream, options.getEncoding());
/*      */ 
/*  133 */       checkOptionsConsistence();
/*      */ 
/*  137 */       String tailStr = serializeAsRDF();
/*  138 */       this.writer.flush();
/*      */ 
/*  141 */       addPadding(tailStr.length());
/*      */ 
/*  144 */       write(tailStr);
/*  145 */       this.writer.flush();
/*      */ 
/*  147 */       this.outputStream.close();
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/*  151 */       throw new XMPException("Error writing to the OutputStream", 0);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void addPadding(int tailLength)
/*      */     throws XMPException, IOException
/*      */   {
/*  164 */     if (this.options.getExactPacketLength())
/*      */     {
/*  167 */       int minSize = this.outputStream.getBytesWritten() + tailLength * this.unicodeSize;
/*  168 */       if (minSize > this.padding)
/*      */       {
/*  170 */         throw new XMPException("Can't fit into specified packet size", 107);
/*      */       }
/*      */ 
/*  173 */       this.padding -= minSize;
/*      */     }
/*      */ 
/*  177 */     this.padding /= this.unicodeSize;
/*      */ 
/*  179 */     int newlineLen = this.options.getNewline().length();
/*  180 */     if (this.padding >= newlineLen)
/*      */     {
/*  182 */       this.padding -= newlineLen;
/*  183 */       while (this.padding >= 100 + newlineLen)
/*      */       {
/*  185 */         writeChars(100, ' ');
/*  186 */         writeNewline();
/*  187 */         this.padding -= 100 + newlineLen;
/*      */       }
/*  189 */       writeChars(this.padding, ' ');
/*  190 */       writeNewline();
/*      */     }
/*      */     else
/*      */     {
/*  194 */       writeChars(this.padding, ' ');
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void checkOptionsConsistence()
/*      */     throws XMPException
/*      */   {
/*  205 */     if ((this.options.getEncodeUTF16BE() | this.options.getEncodeUTF16LE()))
/*      */     {
/*  207 */       this.unicodeSize = 2;
/*      */     }
/*      */ 
/*  210 */     if (this.options.getExactPacketLength())
/*      */     {
/*  212 */       if ((this.options.getOmitPacketWrapper() | this.options.getIncludeThumbnailPad()))
/*      */       {
/*  214 */         throw new XMPException("Inconsistent options for exact size serialize", 103);
/*      */       }
/*      */ 
/*  217 */       if ((this.options.getPadding() & this.unicodeSize - 1) != 0)
/*      */       {
/*  219 */         throw new XMPException("Exact size must be a multiple of the Unicode element", 103);
/*      */       }
/*      */ 
/*      */     }
/*  223 */     else if (this.options.getReadOnlyPacket())
/*      */     {
/*  225 */       if ((this.options.getOmitPacketWrapper() | this.options.getIncludeThumbnailPad()))
/*      */       {
/*  227 */         throw new XMPException("Inconsistent options for read-only packet", 103);
/*      */       }
/*      */ 
/*  230 */       this.padding = 0;
/*      */     }
/*  232 */     else if (this.options.getOmitPacketWrapper())
/*      */     {
/*  234 */       if (this.options.getIncludeThumbnailPad())
/*      */       {
/*  236 */         throw new XMPException("Inconsistent options for non-packet serialize", 103);
/*      */       }
/*      */ 
/*  239 */       this.padding = 0;
/*      */     }
/*      */     else
/*      */     {
/*  243 */       if (this.padding == 0)
/*      */       {
/*  245 */         this.padding = (2048 * this.unicodeSize);
/*      */       }
/*      */ 
/*  248 */       if (this.options.getIncludeThumbnailPad())
/*      */       {
/*  250 */         if (!this.xmp.doesPropertyExist("http://ns.adobe.com/xap/1.0/", "Thumbnails"))
/*      */         {
/*  252 */           this.padding += 10000 * this.unicodeSize;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private String serializeAsRDF()
/*      */     throws IOException, XMPException
/*      */   {
/*  267 */     int level = 0;
/*      */ 
/*  270 */     if (!this.options.getOmitPacketWrapper())
/*      */     {
/*  272 */       writeIndent(level);
/*  273 */       write("<?xpacket begin=\"﻿\" id=\"W5M0MpCehiHzreSzNTczkc9d\"?>");
/*  274 */       writeNewline();
/*      */     }
/*      */ 
/*  278 */     if (!this.options.getOmitXmpMetaElement())
/*      */     {
/*  280 */       writeIndent(level);
/*  281 */       write("<x:xmpmeta xmlns:x=\"adobe:ns:meta/\" x:xmptk=\"");
/*      */ 
/*  283 */       if (!this.options.getOmitVersionAttribute())
/*      */       {
/*  285 */         write(XMPMetaFactory.getVersionInfo().getMessage());
/*      */       }
/*  287 */       write("\">");
/*  288 */       writeNewline();
/*  289 */       level++;
/*      */     }
/*      */ 
/*  293 */     writeIndent(level);
/*  294 */     write("<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">");
/*  295 */     writeNewline();
/*      */ 
/*  298 */     if (this.options.getUseCanonicalFormat())
/*      */     {
/*  300 */       serializeCanonicalRDFSchemas(level);
/*      */     }
/*      */     else
/*      */     {
/*  304 */       serializeCompactRDFSchemas(level);
/*      */     }
/*      */ 
/*  308 */     writeIndent(level);
/*  309 */     write("</rdf:RDF>");
/*  310 */     writeNewline();
/*      */ 
/*  313 */     if (!this.options.getOmitXmpMetaElement())
/*      */     {
/*  315 */       level--;
/*  316 */       writeIndent(level);
/*  317 */       write("</x:xmpmeta>");
/*  318 */       writeNewline();
/*      */     }
/*      */ 
/*  321 */     String tailStr = "";
/*  322 */     if (!this.options.getOmitPacketWrapper())
/*      */     {
/*  324 */       for (level = this.options.getBaseIndent(); level > 0; level--)
/*      */       {
/*  326 */         tailStr = tailStr + this.options.getIndent();
/*      */       }
/*      */ 
/*  329 */       tailStr = tailStr + "<?xpacket end=\"";
/*  330 */       tailStr = tailStr + (this.options.getReadOnlyPacket() ? 'r' : 'w');
/*  331 */       tailStr = tailStr + "\"?>";
/*      */     }
/*      */ 
/*  334 */     return tailStr;
/*      */   }
/*      */ 
/*      */   private void serializeCanonicalRDFSchemas(int level)
/*      */     throws IOException, XMPException
/*      */   {
/*  346 */     if (this.xmp.getRoot().getChildrenLength() > 0)
/*      */     {
/*  348 */       startOuterRDFDescription(this.xmp.getRoot(), level);
/*      */ 
/*  350 */       for (Iterator it = this.xmp.getRoot().iterateChildren(); it.hasNext(); )
/*      */       {
/*  352 */         XMPNode currSchema = (XMPNode)it.next();
/*  353 */         serializeCanonicalRDFSchema(currSchema, level);
/*      */       }
/*      */ 
/*  356 */       endOuterRDFDescription(level);
/*      */     }
/*      */     else
/*      */     {
/*  360 */       writeIndent(level + 1);
/*  361 */       write("<rdf:Description rdf:about=");
/*  362 */       writeTreeName();
/*  363 */       write("/>");
/*  364 */       writeNewline();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void writeTreeName()
/*      */     throws IOException
/*      */   {
/*  374 */     write(34);
/*  375 */     String name = this.xmp.getRoot().getName();
/*  376 */     if (name != null)
/*      */     {
/*  378 */       appendNodeValue(name, true);
/*      */     }
/*  380 */     write(34);
/*      */   }
/*      */ 
/*      */   private void serializeCompactRDFSchemas(int level)
/*      */     throws IOException, XMPException
/*      */   {
/*  393 */     writeIndent(level + 1);
/*  394 */     write("<rdf:Description rdf:about=");
/*  395 */     writeTreeName();
/*      */ 
/*  398 */     Set usedPrefixes = new HashSet();
/*  399 */     usedPrefixes.add("xml");
/*  400 */     usedPrefixes.add("rdf");
/*      */ 
/*  402 */     for (Iterator it = this.xmp.getRoot().iterateChildren(); it.hasNext(); )
/*      */     {
/*  404 */       XMPNode schema = (XMPNode)it.next();
/*  405 */       declareUsedNamespaces(schema, usedPrefixes, level + 3);
/*      */     }
/*      */ 
/*  409 */     boolean allAreAttrs = true;
/*  410 */     for (Iterator it = this.xmp.getRoot().iterateChildren(); it.hasNext(); )
/*      */     {
/*  412 */       XMPNode schema = (XMPNode)it.next();
/*  413 */       allAreAttrs &= serializeCompactRDFAttrProps(schema, level + 2);
/*      */     }
/*      */ 
/*  416 */     if (!allAreAttrs)
/*      */     {
/*  418 */       write(62);
/*  419 */       writeNewline();
/*      */     }
/*      */     else
/*      */     {
/*  423 */       write("/>");
/*  424 */       writeNewline();
/*  425 */       return;
/*      */     }
/*      */ 
/*  429 */     for (Iterator it = this.xmp.getRoot().iterateChildren(); it.hasNext(); )
/*      */     {
/*  431 */       XMPNode schema = (XMPNode)it.next();
/*  432 */       serializeCompactRDFElementProps(schema, level + 2);
/*      */     }
/*      */ 
/*  436 */     writeIndent(level + 1);
/*  437 */     write("</rdf:Description>");
/*  438 */     writeNewline();
/*      */   }
/*      */ 
/*      */   private boolean serializeCompactRDFAttrProps(XMPNode parentNode, int indent)
/*      */     throws IOException
/*      */   {
/*  454 */     boolean allAreAttrs = true;
/*      */ 
/*  456 */     for (Iterator it = parentNode.iterateChildren(); it.hasNext(); )
/*      */     {
/*  458 */       XMPNode prop = (XMPNode)it.next();
/*      */ 
/*  460 */       if (canBeRDFAttrProp(prop))
/*      */       {
/*  462 */         writeNewline();
/*  463 */         writeIndent(indent);
/*  464 */         write(prop.getName());
/*  465 */         write("=\"");
/*  466 */         appendNodeValue(prop.getValue(), true);
/*  467 */         write(34);
/*      */       }
/*      */       else
/*      */       {
/*  471 */         allAreAttrs = false;
/*      */       }
/*      */     }
/*  474 */     return allAreAttrs;
/*      */   }
/*      */ 
/*      */   private void serializeCompactRDFElementProps(XMPNode parentNode, int indent)
/*      */     throws IOException, XMPException
/*      */   {
/*  530 */     for (Iterator it = parentNode.iterateChildren(); it.hasNext(); )
/*      */     {
/*  532 */       XMPNode node = (XMPNode)it.next();
/*  533 */       if (!canBeRDFAttrProp(node))
/*      */       {
/*  538 */         boolean emitEndTag = true;
/*  539 */         boolean indentEndTag = true;
/*      */ 
/*  544 */         String elemName = node.getName();
/*  545 */         if ("[]".equals(elemName))
/*      */         {
/*  547 */           elemName = "rdf:li";
/*      */         }
/*      */ 
/*  550 */         writeIndent(indent);
/*  551 */         write(60);
/*  552 */         write(elemName);
/*      */ 
/*  554 */         boolean hasGeneralQualifiers = false;
/*  555 */         boolean hasRDFResourceQual = false;
/*      */ 
/*  557 */         for (Iterator iq = node.iterateQualifier(); iq.hasNext(); )
/*      */         {
/*  559 */           XMPNode qualifier = (XMPNode)iq.next();
/*  560 */           if (!RDF_ATTR_QUALIFIER.contains(qualifier.getName()))
/*      */           {
/*  562 */             hasGeneralQualifiers = true;
/*      */           }
/*      */           else
/*      */           {
/*  566 */             hasRDFResourceQual = "rdf:resource".equals(qualifier.getName());
/*  567 */             write(32);
/*  568 */             write(qualifier.getName());
/*  569 */             write("=\"");
/*  570 */             appendNodeValue(qualifier.getValue(), true);
/*  571 */             write(34);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  577 */         if (hasGeneralQualifiers)
/*      */         {
/*  579 */           serializeCompactRDFGeneralQualifier(indent, node);
/*      */         }
/*  584 */         else if (!node.getOptions().isCompositeProperty())
/*      */         {
/*  586 */           Object[] result = serializeCompactRDFSimpleProp(node);
/*  587 */           emitEndTag = ((Boolean)result[0]).booleanValue();
/*  588 */           indentEndTag = ((Boolean)result[1]).booleanValue();
/*      */         }
/*  590 */         else if (node.getOptions().isArray())
/*      */         {
/*  592 */           serializeCompactRDFArrayProp(node, indent);
/*      */         }
/*      */         else
/*      */         {
/*  596 */           emitEndTag = serializeCompactRDFStructProp(node, indent, hasRDFResourceQual);
/*      */         }
/*      */ 
/*  603 */         if (emitEndTag)
/*      */         {
/*  605 */           if (indentEndTag)
/*      */           {
/*  607 */             writeIndent(indent);
/*      */           }
/*  609 */           write("</");
/*  610 */           write(elemName);
/*  611 */           write(62);
/*  612 */           writeNewline();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private Object[] serializeCompactRDFSimpleProp(XMPNode node)
/*      */     throws IOException
/*      */   {
/*  629 */     Boolean emitEndTag = Boolean.TRUE;
/*  630 */     Boolean indentEndTag = Boolean.TRUE;
/*      */ 
/*  632 */     if (node.getOptions().isURI())
/*      */     {
/*  634 */       write(" rdf:resource=\"");
/*  635 */       appendNodeValue(node.getValue(), true);
/*  636 */       write("\"/>");
/*  637 */       writeNewline();
/*  638 */       emitEndTag = Boolean.FALSE;
/*      */     }
/*  640 */     else if ((node.getValue() == null) || (node.getValue().length() == 0))
/*      */     {
/*  642 */       write("/>");
/*  643 */       writeNewline();
/*  644 */       emitEndTag = Boolean.FALSE;
/*      */     }
/*      */     else
/*      */     {
/*  648 */       write(62);
/*  649 */       appendNodeValue(node.getValue(), false);
/*  650 */       indentEndTag = Boolean.FALSE;
/*      */     }
/*      */ 
/*  653 */     return new Object[] { emitEndTag, indentEndTag };
/*      */   }
/*      */ 
/*      */   private void serializeCompactRDFArrayProp(XMPNode node, int indent)
/*      */     throws IOException, XMPException
/*      */   {
/*  669 */     write(62);
/*  670 */     writeNewline();
/*  671 */     emitRDFArrayTag(node, true, indent + 1);
/*      */ 
/*  673 */     if (node.getOptions().isArrayAltText())
/*      */     {
/*  675 */       XMPNodeUtils.normalizeLangArray(node);
/*      */     }
/*      */ 
/*  678 */     serializeCompactRDFElementProps(node, indent + 2);
/*      */ 
/*  680 */     emitRDFArrayTag(node, false, indent + 1);
/*      */   }
/*      */ 
/*      */   private boolean serializeCompactRDFStructProp(XMPNode node, int indent, boolean hasRDFResourceQual)
/*      */     throws XMPException, IOException
/*      */   {
/*  698 */     boolean hasAttrFields = false;
/*  699 */     boolean hasElemFields = false;
/*  700 */     boolean emitEndTag = true;
/*      */ 
/*  702 */     for (Iterator ic = node.iterateChildren(); ic.hasNext(); )
/*      */     {
/*  704 */       XMPNode field = (XMPNode)ic.next();
/*  705 */       if (canBeRDFAttrProp(field))
/*      */       {
/*  707 */         hasAttrFields = true;
/*      */       }
/*      */       else
/*      */       {
/*  711 */         hasElemFields = true;
/*      */       }
/*      */ 
/*  714 */       if ((hasAttrFields) && (hasElemFields))
/*      */       {
/*      */         break;
/*      */       }
/*      */     }
/*      */ 
/*  720 */     if ((hasRDFResourceQual) && (hasElemFields))
/*      */     {
/*  722 */       throw new XMPException("Can't mix rdf:resource qualifier and element fields", 202);
/*      */     }
/*      */ 
/*  727 */     if (!node.hasChildren())
/*      */     {
/*  733 */       write(" rdf:parseType=\"Resource\"/>");
/*  734 */       writeNewline();
/*  735 */       emitEndTag = false;
/*      */     }
/*  738 */     else if (!hasElemFields)
/*      */     {
/*  742 */       serializeCompactRDFAttrProps(node, indent + 1);
/*  743 */       write("/>");
/*  744 */       writeNewline();
/*  745 */       emitEndTag = false;
/*      */     }
/*  748 */     else if (!hasAttrFields)
/*      */     {
/*  752 */       write(" rdf:parseType=\"Resource\">");
/*  753 */       writeNewline();
/*  754 */       serializeCompactRDFElementProps(node, indent + 1);
/*      */     }
/*      */     else
/*      */     {
/*  760 */       write(62);
/*  761 */       writeNewline();
/*  762 */       writeIndent(indent + 1);
/*  763 */       write("<rdf:Description");
/*  764 */       serializeCompactRDFAttrProps(node, indent + 2);
/*  765 */       write(">");
/*  766 */       writeNewline();
/*  767 */       serializeCompactRDFElementProps(node, indent + 1);
/*  768 */       writeIndent(indent + 1);
/*  769 */       write("</rdf:Description>");
/*  770 */       writeNewline();
/*      */     }
/*  772 */     return emitEndTag;
/*      */   }
/*      */ 
/*      */   private void serializeCompactRDFGeneralQualifier(int indent, XMPNode node)
/*      */     throws IOException, XMPException
/*      */   {
/*  791 */     write(" rdf:parseType=\"Resource\">");
/*  792 */     writeNewline();
/*      */ 
/*  794 */     serializeCanonicalRDFProperty(node, false, true, indent + 1);
/*      */ 
/*  796 */     for (Iterator iq = node.iterateQualifier(); iq.hasNext(); )
/*      */     {
/*  798 */       XMPNode qualifier = (XMPNode)iq.next();
/*  799 */       serializeCanonicalRDFProperty(qualifier, false, false, indent + 1);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void serializeCanonicalRDFSchema(XMPNode schemaNode, int level)
/*      */     throws IOException, XMPException
/*      */   {
/*  836 */     for (Iterator it = schemaNode.iterateChildren(); it.hasNext(); )
/*      */     {
/*  838 */       XMPNode propNode = (XMPNode)it.next();
/*  839 */       serializeCanonicalRDFProperty(propNode, this.options.getUseCanonicalFormat(), false, level + 2);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void declareUsedNamespaces(XMPNode node, Set usedPrefixes, int indent)
/*      */     throws IOException
/*      */   {
/*      */     Iterator it;
/*  855 */     if (node.getOptions().isSchemaNode())
/*      */     {
/*  858 */       String prefix = node.getValue().substring(0, node.getValue().length() - 1);
/*  859 */       declareNamespace(prefix, node.getName(), usedPrefixes, indent);
/*      */     }
/*  861 */     else if (node.getOptions().isStruct())
/*      */     {
/*  863 */       for (it = node.iterateChildren(); it.hasNext(); )
/*      */       {
/*  865 */         XMPNode field = (XMPNode)it.next();
/*  866 */         declareNamespace(field.getName(), null, usedPrefixes, indent);
/*      */       }
/*      */     }
/*      */ 
/*  870 */     for (Iterator it = node.iterateChildren(); it.hasNext(); )
/*      */     {
/*  872 */       XMPNode child = (XMPNode)it.next();
/*  873 */       declareUsedNamespaces(child, usedPrefixes, indent);
/*      */     }
/*      */ 
/*  876 */     for (Iterator it = node.iterateQualifier(); it.hasNext(); )
/*      */     {
/*  878 */       XMPNode qualifier = (XMPNode)it.next();
/*  879 */       declareNamespace(qualifier.getName(), null, usedPrefixes, indent);
/*  880 */       declareUsedNamespaces(qualifier, usedPrefixes, indent);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void declareNamespace(String prefix, String namespace, Set usedPrefixes, int indent)
/*      */     throws IOException
/*      */   {
/*  896 */     if (namespace == null)
/*      */     {
/*  899 */       QName qname = new QName(prefix);
/*  900 */       if (qname.hasPrefix())
/*      */       {
/*  902 */         prefix = qname.getPrefix();
/*      */ 
/*  904 */         namespace = XMPMetaFactory.getSchemaRegistry().getNamespaceURI(prefix + ":");
/*      */ 
/*  906 */         declareNamespace(prefix, namespace, usedPrefixes, indent);
/*      */       }
/*      */       else
/*      */       {
/*  910 */         return;
/*      */       }
/*      */     }
/*      */ 
/*  914 */     if (!usedPrefixes.contains(prefix))
/*      */     {
/*  916 */       writeNewline();
/*  917 */       writeIndent(indent);
/*  918 */       write("xmlns:");
/*  919 */       write(prefix);
/*  920 */       write("=\"");
/*  921 */       write(namespace);
/*  922 */       write(34);
/*  923 */       usedPrefixes.add(prefix);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void startOuterRDFDescription(XMPNode schemaNode, int level)
/*      */     throws IOException
/*      */   {
/*  936 */     writeIndent(level + 1);
/*  937 */     write("<rdf:Description rdf:about=");
/*  938 */     writeTreeName();
/*      */ 
/*  940 */     Set usedPrefixes = new HashSet();
/*  941 */     usedPrefixes.add("xml");
/*  942 */     usedPrefixes.add("rdf");
/*      */ 
/*  944 */     declareUsedNamespaces(schemaNode, usedPrefixes, level + 3);
/*      */ 
/*  946 */     write(62);
/*  947 */     writeNewline();
/*      */   }
/*      */ 
/*      */   private void endOuterRDFDescription(int level)
/*      */     throws IOException
/*      */   {
/*  956 */     writeIndent(level + 1);
/*  957 */     write("</rdf:Description>");
/*  958 */     writeNewline();
/*      */   }
/*      */ 
/*      */   private void serializeCanonicalRDFProperty(XMPNode node, boolean useCanonicalRDF, boolean emitAsRDFValue, int indent)
/*      */     throws IOException, XMPException
/*      */   {
/* 1015 */     boolean emitEndTag = true;
/* 1016 */     boolean indentEndTag = true;
/*      */ 
/* 1021 */     String elemName = node.getName();
/* 1022 */     if (emitAsRDFValue)
/*      */     {
/* 1024 */       elemName = "rdf:value";
/*      */     }
/* 1026 */     else if ("[]".equals(elemName))
/*      */     {
/* 1028 */       elemName = "rdf:li";
/*      */     }
/*      */ 
/* 1031 */     writeIndent(indent);
/* 1032 */     write(60);
/* 1033 */     write(elemName);
/*      */ 
/* 1035 */     boolean hasGeneralQualifiers = false;
/* 1036 */     boolean hasRDFResourceQual = false;
/*      */ 
/* 1038 */     for (Iterator it = node.iterateQualifier(); it.hasNext(); )
/*      */     {
/* 1040 */       XMPNode qualifier = (XMPNode)it.next();
/* 1041 */       if (!RDF_ATTR_QUALIFIER.contains(qualifier.getName()))
/*      */       {
/* 1043 */         hasGeneralQualifiers = true;
/*      */       }
/*      */       else
/*      */       {
/* 1047 */         hasRDFResourceQual = "rdf:resource".equals(qualifier.getName());
/* 1048 */         if (!emitAsRDFValue)
/*      */         {
/* 1050 */           write(32);
/* 1051 */           write(qualifier.getName());
/* 1052 */           write("=\"");
/* 1053 */           appendNodeValue(qualifier.getValue(), true);
/* 1054 */           write(34);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1061 */     if ((hasGeneralQualifiers) && (!emitAsRDFValue))
/*      */     {
/* 1068 */       if (hasRDFResourceQual)
/*      */       {
/* 1070 */         throw new XMPException("Can't mix rdf:resource and general qualifiers", 202);
/*      */       }
/*      */ 
/* 1076 */       if (useCanonicalRDF)
/*      */       {
/* 1078 */         write(">");
/* 1079 */         writeNewline();
/*      */ 
/* 1081 */         indent++;
/* 1082 */         writeIndent(indent);
/* 1083 */         write("<rdf:Description");
/* 1084 */         write(">");
/*      */       }
/*      */       else
/*      */       {
/* 1088 */         write(" rdf:parseType=\"Resource\">");
/*      */       }
/* 1090 */       writeNewline();
/*      */ 
/* 1092 */       serializeCanonicalRDFProperty(node, useCanonicalRDF, true, indent + 1);
/*      */ 
/* 1094 */       for (Iterator it = node.iterateQualifier(); it.hasNext(); )
/*      */       {
/* 1096 */         XMPNode qualifier = (XMPNode)it.next();
/* 1097 */         if (!RDF_ATTR_QUALIFIER.contains(qualifier.getName()))
/*      */         {
/* 1099 */           serializeCanonicalRDFProperty(qualifier, useCanonicalRDF, false, indent + 1);
/*      */         }
/*      */       }
/*      */ 
/* 1103 */       if (useCanonicalRDF)
/*      */       {
/* 1105 */         writeIndent(indent);
/* 1106 */         write("</rdf:Description>");
/* 1107 */         writeNewline();
/* 1108 */         indent--;
/*      */       }
/*      */ 
/*      */     }
/* 1115 */     else if (!node.getOptions().isCompositeProperty())
/*      */     {
/* 1119 */       if (node.getOptions().isURI())
/*      */       {
/* 1121 */         write(" rdf:resource=\"");
/* 1122 */         appendNodeValue(node.getValue(), true);
/* 1123 */         write("\"/>");
/* 1124 */         writeNewline();
/* 1125 */         emitEndTag = false;
/*      */       }
/* 1127 */       else if ((node.getValue() == null) || ("".equals(node.getValue())))
/*      */       {
/* 1129 */         write("/>");
/* 1130 */         writeNewline();
/* 1131 */         emitEndTag = false;
/*      */       }
/*      */       else
/*      */       {
/* 1135 */         write(62);
/* 1136 */         appendNodeValue(node.getValue(), false);
/* 1137 */         indentEndTag = false;
/*      */       }
/*      */     }
/* 1140 */     else if (node.getOptions().isArray())
/*      */     {
/* 1143 */       write(62);
/* 1144 */       writeNewline();
/* 1145 */       emitRDFArrayTag(node, true, indent + 1);
/* 1146 */       if (node.getOptions().isArrayAltText())
/*      */       {
/* 1148 */         XMPNodeUtils.normalizeLangArray(node);
/*      */       }
/* 1150 */       for (Iterator it = node.iterateChildren(); it.hasNext(); )
/*      */       {
/* 1152 */         XMPNode child = (XMPNode)it.next();
/* 1153 */         serializeCanonicalRDFProperty(child, useCanonicalRDF, false, indent + 2);
/*      */       }
/* 1155 */       emitRDFArrayTag(node, false, indent + 1);
/*      */     }
/* 1159 */     else if (!hasRDFResourceQual)
/*      */     {
/* 1162 */       if (!node.hasChildren())
/*      */       {
/* 1166 */         if (useCanonicalRDF)
/*      */         {
/* 1168 */           write(">");
/* 1169 */           writeNewline();
/* 1170 */           writeIndent(indent + 1);
/* 1171 */           write("<rdf:Description/>");
/*      */         }
/*      */         else
/*      */         {
/* 1175 */           write(" rdf:parseType=\"Resource\"/>");
/* 1176 */           emitEndTag = false;
/*      */         }
/* 1178 */         writeNewline();
/*      */       }
/*      */       else
/*      */       {
/* 1184 */         if (useCanonicalRDF)
/*      */         {
/* 1186 */           write(">");
/* 1187 */           writeNewline();
/* 1188 */           indent++;
/* 1189 */           writeIndent(indent);
/* 1190 */           write("<rdf:Description");
/* 1191 */           write(">");
/*      */         }
/*      */         else
/*      */         {
/* 1195 */           write(" rdf:parseType=\"Resource\">");
/*      */         }
/* 1197 */         writeNewline();
/*      */ 
/* 1199 */         for (Iterator it = node.iterateChildren(); it.hasNext(); )
/*      */         {
/* 1201 */           XMPNode child = (XMPNode)it.next();
/* 1202 */           serializeCanonicalRDFProperty(child, useCanonicalRDF, false, indent + 1);
/*      */         }
/*      */ 
/* 1205 */         if (useCanonicalRDF)
/*      */         {
/* 1207 */           writeIndent(indent);
/* 1208 */           write("</rdf:Description>");
/* 1209 */           writeNewline();
/* 1210 */           indent--;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1218 */       for (Iterator it = node.iterateChildren(); it.hasNext(); )
/*      */       {
/* 1220 */         XMPNode child = (XMPNode)it.next();
/* 1221 */         if (!canBeRDFAttrProp(child))
/*      */         {
/* 1223 */           throw new XMPException("Can't mix rdf:resource and complex fields", 202);
/*      */         }
/*      */ 
/* 1226 */         writeNewline();
/* 1227 */         writeIndent(indent + 1);
/* 1228 */         write(32);
/* 1229 */         write(child.getName());
/* 1230 */         write("=\"");
/* 1231 */         appendNodeValue(child.getValue(), true);
/* 1232 */         write(34);
/*      */       }
/* 1234 */       write("/>");
/* 1235 */       writeNewline();
/* 1236 */       emitEndTag = false;
/*      */     }
/*      */ 
/* 1241 */     if (emitEndTag)
/*      */     {
/* 1243 */       if (indentEndTag)
/*      */       {
/* 1245 */         writeIndent(indent);
/*      */       }
/* 1247 */       write("</");
/* 1248 */       write(elemName);
/* 1249 */       write(62);
/* 1250 */       writeNewline();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void emitRDFArrayTag(XMPNode arrayNode, boolean isStartTag, int indent)
/*      */     throws IOException
/*      */   {
/* 1266 */     if ((isStartTag) || (arrayNode.hasChildren()))
/*      */     {
/* 1268 */       writeIndent(indent);
/* 1269 */       write(isStartTag ? "<rdf:" : "</rdf:");
/*      */ 
/* 1271 */       if (arrayNode.getOptions().isArrayAlternate())
/*      */       {
/* 1273 */         write("Alt");
/*      */       }
/* 1275 */       else if (arrayNode.getOptions().isArrayOrdered())
/*      */       {
/* 1277 */         write("Seq");
/*      */       }
/*      */       else
/*      */       {
/* 1281 */         write("Bag");
/*      */       }
/*      */ 
/* 1284 */       if ((isStartTag) && (!arrayNode.hasChildren()))
/*      */       {
/* 1286 */         write("/>");
/*      */       }
/*      */       else
/*      */       {
/* 1290 */         write(">");
/*      */       }
/*      */ 
/* 1293 */       writeNewline();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void appendNodeValue(String value, boolean forAttribute)
/*      */     throws IOException
/*      */   {
/* 1311 */     if (value == null)
/*      */     {
/* 1313 */       value = "";
/*      */     }
/* 1315 */     write(Utils.escapeXML(value, forAttribute, true));
/*      */   }
/*      */ 
/*      */   private boolean canBeRDFAttrProp(XMPNode node)
/*      */   {
/* 1333 */     return (!node.hasQualifier()) && (!node.getOptions().isURI()) && (!node.getOptions().isCompositeProperty()) && (!node.getOptions().containsOneOf(1073741824)) && (!"[]".equals(node.getName()));
/*      */   }
/*      */ 
/*      */   private void writeIndent(int times)
/*      */     throws IOException
/*      */   {
/* 1349 */     for (int i = this.options.getBaseIndent() + times; i > 0; i--)
/*      */     {
/* 1351 */       this.writer.write(this.options.getIndent());
/*      */     }
/*      */   }
/*      */ 
/*      */   private void write(int c)
/*      */     throws IOException
/*      */   {
/* 1363 */     this.writer.write(c);
/*      */   }
/*      */ 
/*      */   private void write(String str)
/*      */     throws IOException
/*      */   {
/* 1374 */     this.writer.write(str);
/*      */   }
/*      */ 
/*      */   private void writeChars(int number, char c)
/*      */     throws IOException
/*      */   {
/* 1386 */     for (; number > 0; number--)
/*      */     {
/* 1388 */       this.writer.write(c);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void writeNewline()
/*      */     throws IOException
/*      */   {
/* 1399 */     this.writer.write(this.options.getNewline());
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.xmp.impl.XMPSerializerRDF
 * JD-Core Version:    0.6.2
 */