/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.xml.simpleparser.SimpleXMLDocHandler;
/*     */ import com.itextpdf.text.xml.simpleparser.SimpleXMLParser;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Stack;
/*     */ 
/*     */ public class XfdfReader
/*     */   implements SimpleXMLDocHandler
/*     */ {
/*  67 */   private boolean foundRoot = false;
/*  68 */   private final Stack<String> fieldNames = new Stack();
/*  69 */   private final Stack<String> fieldValues = new Stack();
/*     */   HashMap<String, String> fields;
/*     */   protected HashMap<String, List<String>> listFields;
/*     */   String fileSpec;
/*     */ 
/*     */   public XfdfReader(String filename)
/*     */     throws IOException
/*     */   {
/*  88 */     FileInputStream fin = null;
/*     */     try {
/*  90 */       fin = new FileInputStream(filename);
/*  91 */       SimpleXMLParser.parse(this, fin);
/*     */     } finally {
/*     */       try {
/*  94 */         if (fin != null) fin.close(); 
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public XfdfReader(byte[] xfdfIn)
/*     */     throws IOException
/*     */   {
/* 104 */     this(new ByteArrayInputStream(xfdfIn));
/*     */   }
/*     */ 
/*     */   public XfdfReader(InputStream is)
/*     */     throws IOException
/*     */   {
/* 114 */     SimpleXMLParser.parse(this, is);
/*     */   }
/*     */ 
/*     */   public HashMap<String, String> getFields()
/*     */   {
/* 123 */     return this.fields;
/*     */   }
/*     */ 
/*     */   public String getField(String name)
/*     */   {
/* 131 */     return (String)this.fields.get(name);
/*     */   }
/*     */ 
/*     */   public String getFieldValue(String name)
/*     */   {
/* 140 */     String field = (String)this.fields.get(name);
/* 141 */     if (field == null) {
/* 142 */       return null;
/*     */     }
/* 144 */     return field;
/*     */   }
/*     */ 
/*     */   public List<String> getListValues(String name)
/*     */   {
/* 155 */     return (List)this.listFields.get(name);
/*     */   }
/*     */ 
/*     */   public String getFileSpec()
/*     */   {
/* 162 */     return this.fileSpec;
/*     */   }
/*     */ 
/*     */   public void startElement(String tag, Map<String, String> h)
/*     */   {
/* 172 */     if (!this.foundRoot) {
/* 173 */       if (!tag.equals("xfdf")) {
/* 174 */         throw new RuntimeException(MessageLocalization.getComposedMessage("root.element.is.not.xfdf.1", new Object[] { tag }));
/*     */       }
/* 176 */       this.foundRoot = true;
/*     */     }
/*     */ 
/* 179 */     if (!tag.equals("xfdf"))
/*     */     {
/* 181 */       if (tag.equals("f")) {
/* 182 */         this.fileSpec = ((String)h.get("href"));
/* 183 */       } else if (tag.equals("fields")) {
/* 184 */         this.fields = new HashMap();
/* 185 */         this.listFields = new HashMap();
/* 186 */       } else if (tag.equals("field")) {
/* 187 */         String fName = (String)h.get("name");
/* 188 */         this.fieldNames.push(fName);
/* 189 */       } else if (tag.equals("value")) {
/* 190 */         this.fieldValues.push("");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void endElement(String tag)
/*     */   {
/* 198 */     if (tag.equals("value")) {
/* 199 */       String fName = "";
/* 200 */       for (int k = 0; k < this.fieldNames.size(); k++) {
/* 201 */         fName = fName + "." + (String)this.fieldNames.elementAt(k);
/*     */       }
/* 203 */       if (fName.startsWith("."))
/* 204 */         fName = fName.substring(1);
/* 205 */       String fVal = (String)this.fieldValues.pop();
/* 206 */       String old = (String)this.fields.put(fName, fVal);
/* 207 */       if (old != null) {
/* 208 */         List l = (List)this.listFields.get(fName);
/* 209 */         if (l == null) {
/* 210 */           l = new ArrayList();
/* 211 */           l.add(old);
/*     */         }
/* 213 */         l.add(fVal);
/* 214 */         this.listFields.put(fName, l);
/*     */       }
/*     */     }
/* 217 */     else if ((tag.equals("field")) && 
/* 218 */       (!this.fieldNames.isEmpty())) {
/* 219 */       this.fieldNames.pop();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void startDocument()
/*     */   {
/* 228 */     this.fileSpec = "";
/*     */   }
/*     */ 
/*     */   public void endDocument()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void text(String str)
/*     */   {
/* 243 */     if ((this.fieldNames.isEmpty()) || (this.fieldValues.isEmpty())) {
/* 244 */       return;
/*     */     }
/* 246 */     String val = (String)this.fieldValues.pop();
/* 247 */     val = val + str;
/* 248 */     this.fieldValues.push(val);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.XfdfReader
 * JD-Core Version:    0.6.2
 */