/*     */ package org.apache.pdfbox.preflight.parser;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import javax.activation.DataSource;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.apache.pdfbox.preflight.Format;
/*     */ import org.apache.pdfbox.preflight.PreflightDocument;
/*     */ import org.apache.pdfbox.preflight.ValidationResult;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.exception.SyntaxValidationException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ public class XmlResultParser
/*     */ {
/*     */   public Element validate(DataSource source)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/*  48 */       Document rdocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
/*  49 */       return validate(rdocument, source);
/*     */     } catch (ParserConfigurationException e) {
/*  51 */       IOException ioe = new IOException("Failed to init document builder");
/*  52 */       ioe.initCause(e);
/*  53 */       throw ioe;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Element validate(Document rdocument, DataSource source) throws IOException
/*     */   {
/*  59 */     String pdfType = null;
/*  60 */     ValidationResult result = null;
/*  61 */     long before = System.currentTimeMillis();
/*     */     try {
/*  63 */       PreflightParser parser = new PreflightParser(source);
/*     */       try
/*     */       {
/*  66 */         parser.parse();
/*  67 */         PreflightDocument document = parser.getPreflightDocument();
/*  68 */         document.validate();
/*  69 */         pdfType = document.getSpecification().getFname();
/*  70 */         result = document.getResult();
/*  71 */         document.close();
/*     */       }
/*     */       catch (SyntaxValidationException e)
/*     */       {
/*  75 */         result = e.getResult();
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  80 */       long after = System.currentTimeMillis();
/*  81 */       return generateFailureResponse(rdocument, source.getName(), after - before, pdfType, e);
/*     */     }
/*     */ 
/*  84 */     long after = System.currentTimeMillis();
/*  85 */     if (result.isValid()) {
/*  86 */       Element preflight = generateResponseSkeleton(rdocument, source.getName(), after - before);
/*     */ 
/*  88 */       Element valid = rdocument.createElement("isValid");
/*  89 */       valid.setAttribute("type", pdfType);
/*  90 */       valid.setTextContent("true");
/*  91 */       preflight.appendChild(valid);
/*  92 */       return preflight;
/*     */     }
/*  94 */     Element preflight = generateResponseSkeleton(rdocument, source.getName(), after - before);
/*     */ 
/*  96 */     createResponseWithError(rdocument, pdfType, result, preflight);
/*  97 */     return preflight;
/*     */   }
/*     */ 
/*     */   protected void createResponseWithError(Document rdocument, String pdfType, ValidationResult result, Element preflight)
/*     */   {
/* 103 */     Element valid = rdocument.createElement("isValid");
/* 104 */     valid.setAttribute("type", pdfType);
/* 105 */     valid.setTextContent("false");
/* 106 */     preflight.appendChild(valid);
/*     */ 
/* 108 */     Element errors = rdocument.createElement("errors");
/* 109 */     Map cleaned = cleanErrorList(result.getErrorsList());
/* 110 */     preflight.appendChild(errors);
/* 111 */     int totalCount = 0;
/* 112 */     for (Map.Entry entry : cleaned.entrySet())
/*     */     {
/* 114 */       Element error = rdocument.createElement("error");
/* 115 */       int count = ((Integer)entry.getValue()).intValue();
/* 116 */       error.setAttribute("count", String.format("%d", new Object[] { Integer.valueOf(count) }));
/* 117 */       totalCount += count;
/* 118 */       Element code = rdocument.createElement("code");
/* 119 */       code.setTextContent(((ValidationResult.ValidationError)entry.getKey()).getErrorCode());
/* 120 */       error.appendChild(code);
/* 121 */       Element detail = rdocument.createElement("details");
/* 122 */       detail.setTextContent(((ValidationResult.ValidationError)entry.getKey()).getDetails());
/* 123 */       error.appendChild(detail);
/* 124 */       errors.appendChild(error);
/*     */     }
/* 126 */     errors.setAttribute("count", String.format("%d", new Object[] { Integer.valueOf(totalCount) }));
/*     */   }
/*     */ 
/*     */   private Map<ValidationResult.ValidationError, Integer> cleanErrorList(List<ValidationResult.ValidationError> errors) {
/* 130 */     Map cleaned = new HashMap(errors.size());
/* 131 */     for (ValidationResult.ValidationError ve : errors) {
/* 132 */       Integer found = (Integer)cleaned.get(ve);
/* 133 */       if (found != null)
/* 134 */         cleaned.put(ve, Integer.valueOf(found.intValue() + 1));
/*     */       else {
/* 136 */         cleaned.put(ve, Integer.valueOf(1));
/*     */       }
/*     */     }
/*     */ 
/* 140 */     return cleaned;
/*     */   }
/*     */ 
/*     */   protected Element generateFailureResponse(Document rdocument, String name, long duration, String pdfType, Exception e) {
/* 144 */     Element preflight = generateResponseSkeleton(rdocument, name, duration);
/*     */ 
/* 146 */     Element valid = rdocument.createElement("isValid");
/* 147 */     valid.setAttribute("type", pdfType);
/* 148 */     valid.setTextContent("false");
/* 149 */     preflight.appendChild(valid);
/*     */ 
/* 151 */     Element exception = rdocument.createElement("exceptionThrown");
/* 152 */     Element message = rdocument.createElement("message");
/* 153 */     message.setTextContent(e.getMessage());
/* 154 */     StringWriter sw = new StringWriter();
/* 155 */     PrintWriter pw = new PrintWriter(sw);
/* 156 */     e.printStackTrace(pw);
/* 157 */     pw.close();
/* 158 */     Element stack = rdocument.createElement("stackTrace");
/* 159 */     stack.setTextContent(sw.toString());
/* 160 */     exception.appendChild(message);
/* 161 */     exception.appendChild(stack);
/* 162 */     preflight.appendChild(exception);
/* 163 */     return preflight;
/*     */   }
/*     */ 
/*     */   protected Element generateResponseSkeleton(Document rdocument, String name, long duration) {
/* 167 */     Element preflight = rdocument.createElement("preflight");
/* 168 */     preflight.setAttribute("name", name);
/*     */ 
/* 170 */     Element eduration = rdocument.createElement("executionTimeMS");
/* 171 */     eduration.setTextContent(String.format("%d", new Object[] { Long.valueOf(duration) }));
/* 172 */     preflight.appendChild(eduration);
/*     */ 
/* 174 */     return preflight;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.parser.XmlResultParser
 * JD-Core Version:    0.6.2
 */