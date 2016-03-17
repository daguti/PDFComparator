/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import java.net.URL;
/*     */ import java.security.MessageDigest;
/*     */ 
/*     */ public class ImgJBIG2 extends Image
/*     */ {
/*     */   private byte[] global;
/*     */   private byte[] globalHash;
/*     */ 
/*     */   ImgJBIG2(Image image)
/*     */   {
/*  66 */     super(image);
/*     */   }
/*     */ 
/*     */   public ImgJBIG2()
/*     */   {
/*  73 */     super((Image)null);
/*     */   }
/*     */ 
/*     */   public ImgJBIG2(int width, int height, byte[] data, byte[] globals)
/*     */   {
/*  84 */     super((URL)null);
/*  85 */     this.type = 36;
/*  86 */     this.originalType = 9;
/*  87 */     this.scaledHeight = height;
/*  88 */     setTop(this.scaledHeight);
/*  89 */     this.scaledWidth = width;
/*  90 */     setRight(this.scaledWidth);
/*  91 */     this.bpc = 1;
/*  92 */     this.colorspace = 1;
/*  93 */     this.rawData = data;
/*  94 */     this.plainWidth = getWidth();
/*  95 */     this.plainHeight = getHeight();
/*  96 */     if (globals != null) {
/*  97 */       this.global = globals;
/*     */       try
/*     */       {
/* 100 */         MessageDigest md = MessageDigest.getInstance("MD5");
/* 101 */         md.update(this.global);
/* 102 */         this.globalHash = md.digest();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public byte[] getGlobalBytes()
/*     */   {
/* 115 */     return this.global;
/*     */   }
/*     */ 
/*     */   public byte[] getGlobalHash()
/*     */   {
/* 123 */     return this.globalHash;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.ImgJBIG2
 * JD-Core Version:    0.6.2
 */