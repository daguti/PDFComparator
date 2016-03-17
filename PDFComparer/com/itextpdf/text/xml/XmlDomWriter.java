/*     */ package com.itextpdf.text.xml;
/*     */ 
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.io.Writer;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.DocumentType;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ public class XmlDomWriter
/*     */ {
/*     */   protected PrintWriter fOut;
/*     */   protected boolean fCanonical;
/*     */   protected boolean fXML11;
/*     */ 
/*     */   public XmlDomWriter()
/*     */   {
/*     */   }
/*     */ 
/*     */   public XmlDomWriter(boolean canonical)
/*     */   {
/*  54 */     this.fCanonical = canonical;
/*     */   }
/*     */ 
/*     */   public void setCanonical(boolean canonical)
/*     */   {
/*  63 */     this.fCanonical = canonical;
/*     */   }
/*     */ 
/*     */   public void setOutput(OutputStream stream, String encoding)
/*     */     throws UnsupportedEncodingException
/*     */   {
/*  70 */     if (encoding == null) {
/*  71 */       encoding = "UTF8";
/*     */     }
/*     */ 
/*  74 */     Writer writer = new OutputStreamWriter(stream, encoding);
/*  75 */     this.fOut = new PrintWriter(writer);
/*     */   }
/*     */ 
/*     */   public void setOutput(Writer writer)
/*     */   {
/*  82 */     this.fOut = ((writer instanceof PrintWriter) ? (PrintWriter)writer : new PrintWriter(writer));
/*     */   }
/*     */ 
/*     */   public void write(Node node)
/*     */   {
/*  91 */     if (node == null) {
/*  92 */       return;
/*     */     }
/*     */ 
/*  95 */     short type = node.getNodeType();
/*     */     Node child;
/*  96 */     switch (type) {
/*     */     case 9:
/*  98 */       Document document = (Document)node;
/*  99 */       this.fXML11 = false;
/* 100 */       if (!this.fCanonical) {
/* 101 */         if (this.fXML11)
/* 102 */           this.fOut.println("<?xml version=\"1.1\" encoding=\"UTF-8\"?>");
/*     */         else {
/* 104 */           this.fOut.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
/*     */         }
/* 106 */         this.fOut.flush();
/* 107 */         write(document.getDoctype());
/*     */       }
/* 109 */       write(document.getDocumentElement());
/* 110 */       break;
/*     */     case 10:
/* 114 */       DocumentType doctype = (DocumentType)node;
/* 115 */       this.fOut.print("<!DOCTYPE ");
/* 116 */       this.fOut.print(doctype.getName());
/* 117 */       String publicId = doctype.getPublicId();
/* 118 */       String systemId = doctype.getSystemId();
/* 119 */       if (publicId != null) {
/* 120 */         this.fOut.print(" PUBLIC '");
/* 121 */         this.fOut.print(publicId);
/* 122 */         this.fOut.print("' '");
/* 123 */         this.fOut.print(systemId);
/* 124 */         this.fOut.print('\'');
/* 125 */       } else if (systemId != null) {
/* 126 */         this.fOut.print(" SYSTEM '");
/* 127 */         this.fOut.print(systemId);
/* 128 */         this.fOut.print('\'');
/*     */       }
/* 130 */       String internalSubset = doctype.getInternalSubset();
/* 131 */       if (internalSubset != null) {
/* 132 */         this.fOut.println(" [");
/* 133 */         this.fOut.print(internalSubset);
/* 134 */         this.fOut.print(']');
/*     */       }
/* 136 */       this.fOut.println('>');
/* 137 */       break;
/*     */     case 1:
/* 141 */       this.fOut.print('<');
/* 142 */       this.fOut.print(node.getNodeName());
/* 143 */       Attr[] attrs = sortAttributes(node.getAttributes());
/* 144 */       for (int i = 0; i < attrs.length; i++) {
/* 145 */         Attr attr = attrs[i];
/* 146 */         this.fOut.print(' ');
/* 147 */         this.fOut.print(attr.getNodeName());
/* 148 */         this.fOut.print("=\"");
/* 149 */         normalizeAndPrint(attr.getNodeValue(), true);
/* 150 */         this.fOut.print('"');
/*     */       }
/* 152 */       this.fOut.print('>');
/* 153 */       this.fOut.flush();
/*     */ 
/* 155 */       child = node.getFirstChild();
/*     */     case 5:
/*     */     case 4:
/*     */     case 3:
/*     */     case 7:
/*     */     case 8:
/* 156 */       while (child != null) {
/* 157 */         write(child);
/* 158 */         child = child.getNextSibling(); continue;
/*     */ 
/* 164 */         if (this.fCanonical) {
/* 165 */           Node child = node.getFirstChild();
/* 166 */           while (child != null) {
/* 167 */             write(child);
/* 168 */             child = child.getNextSibling();
/*     */           }
/*     */         } else {
/* 171 */           this.fOut.print('&');
/* 172 */           this.fOut.print(node.getNodeName());
/* 173 */           this.fOut.print(';');
/* 174 */           this.fOut.flush();
/*     */ 
/* 176 */           break;
/*     */ 
/* 180 */           if (this.fCanonical) {
/* 181 */             normalizeAndPrint(node.getNodeValue(), false);
/*     */           } else {
/* 183 */             this.fOut.print("<![CDATA[");
/* 184 */             this.fOut.print(node.getNodeValue());
/* 185 */             this.fOut.print("]]>");
/*     */           }
/* 187 */           this.fOut.flush();
/* 188 */           break;
/*     */ 
/* 192 */           normalizeAndPrint(node.getNodeValue(), false);
/* 193 */           this.fOut.flush();
/* 194 */           break;
/*     */ 
/* 198 */           this.fOut.print("<?");
/* 199 */           this.fOut.print(node.getNodeName());
/* 200 */           String data = node.getNodeValue();
/* 201 */           if ((data != null) && (data.length() > 0)) {
/* 202 */             this.fOut.print(' ');
/* 203 */             this.fOut.print(data);
/*     */           }
/* 205 */           this.fOut.print("?>");
/* 206 */           this.fOut.flush();
/* 207 */           break;
/*     */ 
/* 211 */           if (!this.fCanonical) {
/* 212 */             this.fOut.print("<!--");
/* 213 */             String comment = node.getNodeValue();
/* 214 */             if ((comment != null) && (comment.length() > 0)) {
/* 215 */               this.fOut.print(comment);
/*     */             }
/* 217 */             this.fOut.print("-->");
/* 218 */             this.fOut.flush();
/*     */           }
/*     */         }
/*     */       }
/*     */     case 2:
/*     */     case 6:
/*     */     }
/* 223 */     if (type == 1) {
/* 224 */       this.fOut.print("</");
/* 225 */       this.fOut.print(node.getNodeName());
/* 226 */       this.fOut.print('>');
/* 227 */       this.fOut.flush();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected Attr[] sortAttributes(NamedNodeMap attrs)
/*     */   {
/* 235 */     int len = attrs != null ? attrs.getLength() : 0;
/* 236 */     Attr[] array = new Attr[len];
/* 237 */     for (int i = 0; i < len; i++) {
/* 238 */       array[i] = ((Attr)attrs.item(i));
/*     */     }
/* 240 */     for (int i = 0; i < len - 1; i++) {
/* 241 */       String name = array[i].getNodeName();
/* 242 */       int index = i;
/* 243 */       for (int j = i + 1; j < len; j++) {
/* 244 */         String curName = array[j].getNodeName();
/* 245 */         if (curName.compareTo(name) < 0) {
/* 246 */           name = curName;
/* 247 */           index = j;
/*     */         }
/*     */       }
/* 250 */       if (index != i) {
/* 251 */         Attr temp = array[i];
/* 252 */         array[i] = array[index];
/* 253 */         array[index] = temp;
/*     */       }
/*     */     }
/*     */ 
/* 257 */     return array;
/*     */   }
/*     */ 
/*     */   protected void normalizeAndPrint(String s, boolean isAttValue)
/*     */   {
/* 268 */     int len = s != null ? s.length() : 0;
/* 269 */     for (int i = 0; i < len; i++) {
/* 270 */       char c = s.charAt(i);
/* 271 */       normalizeAndPrint(c, isAttValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void normalizeAndPrint(char c, boolean isAttValue)
/*     */   {
/* 279 */     switch (c) {
/*     */     case '<':
/* 281 */       this.fOut.print("&lt;");
/* 282 */       break;
/*     */     case '>':
/* 285 */       this.fOut.print("&gt;");
/* 286 */       break;
/*     */     case '&':
/* 289 */       this.fOut.print("&amp;");
/* 290 */       break;
/*     */     case '"':
/* 295 */       if (isAttValue)
/* 296 */         this.fOut.print("&quot;");
/*     */       else {
/* 298 */         this.fOut.print("\"");
/*     */       }
/* 300 */       break;
/*     */     case '\r':
/* 307 */       this.fOut.print("&#xD;");
/* 308 */       break;
/*     */     case '\n':
/* 311 */       if (this.fCanonical)
/* 312 */         this.fOut.print("&#xA;");
/* 313 */       break;
/*     */     }
/*     */ 
/* 326 */     if (((this.fXML11) && (((c >= '\001') && (c <= '\037') && (c != '\t') && (c != '\n')) || ((c >= '') && (c <= '')) || (c == ' '))) || ((isAttValue) && ((c == '\t') || (c == '\n'))))
/*     */     {
/* 329 */       this.fOut.print("&#x");
/* 330 */       this.fOut.print(Integer.toHexString(c).toUpperCase());
/* 331 */       this.fOut.print(";");
/*     */     } else {
/* 333 */       this.fOut.print(c);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.xml.XmlDomWriter
 * JD-Core Version:    0.6.2
 */