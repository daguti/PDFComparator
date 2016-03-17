/*     */ package com.itextpdf.xmp.impl;
/*     */ 
/*     */ import com.itextpdf.xmp.XMPException;
/*     */ import com.itextpdf.xmp.options.SerializeOptions;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ 
/*     */ public class XMPSerializerHelper
/*     */ {
/*     */   public static void serialize(XMPMetaImpl xmp, OutputStream out, SerializeOptions options)
/*     */     throws XMPException
/*     */   {
/*  63 */     options = options != null ? options : new SerializeOptions();
/*     */ 
/*  66 */     if (options.getSort())
/*     */     {
/*  68 */       xmp.sort();
/*     */     }
/*  70 */     new XMPSerializerRDF().serialize(xmp, out, options);
/*     */   }
/*     */ 
/*     */   public static String serializeToString(XMPMetaImpl xmp, SerializeOptions options)
/*     */     throws XMPException
/*     */   {
/*  89 */     options = options != null ? options : new SerializeOptions();
/*  90 */     options.setEncodeUTF16BE(true);
/*     */ 
/*  92 */     ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
/*  93 */     serialize(xmp, out, options);
/*     */     try
/*     */     {
/*  97 */       return out.toString(options.getEncoding());
/*     */     }
/*     */     catch (UnsupportedEncodingException e)
/*     */     {
/*     */     }
/*     */ 
/* 103 */     return out.toString();
/*     */   }
/*     */ 
/*     */   public static byte[] serializeToBuffer(XMPMetaImpl xmp, SerializeOptions options)
/*     */     throws XMPException
/*     */   {
/* 119 */     ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
/* 120 */     serialize(xmp, out, options);
/* 121 */     return out.toByteArray();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.xmp.impl.XMPSerializerHelper
 * JD-Core Version:    0.6.2
 */