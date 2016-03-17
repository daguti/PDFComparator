/*     */ package com.itextpdf.text.xml;
/*     */ 
/*     */ import com.itextpdf.text.xml.simpleparser.SimpleXMLDocHandler;
/*     */ import com.itextpdf.text.xml.simpleparser.SimpleXMLParser;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class XmlToTxt
/*     */   implements SimpleXMLDocHandler
/*     */ {
/*     */   protected StringBuffer buf;
/*     */ 
/*     */   public static String parse(InputStream is)
/*     */     throws IOException
/*     */   {
/*  71 */     XmlToTxt handler = new XmlToTxt();
/*  72 */     SimpleXMLParser.parse(handler, null, new InputStreamReader(is), true);
/*  73 */     return handler.toString();
/*     */   }
/*     */ 
/*     */   protected XmlToTxt()
/*     */   {
/*  80 */     this.buf = new StringBuffer();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  87 */     return this.buf.toString();
/*     */   }
/*     */ 
/*     */   public void startElement(String tag, Map<String, String> h)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void endElement(String tag)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void startDocument()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void endDocument()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void text(String str)
/*     */   {
/* 118 */     this.buf.append(str);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.xml.XmlToTxt
 * JD-Core Version:    0.6.2
 */