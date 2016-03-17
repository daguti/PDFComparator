/*     */ package com.itextpdf.text.pdf.fonts.cmaps;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class CMapCache
/*     */ {
/*  55 */   private static final HashMap<String, CMapUniCid> cacheUniCid = new HashMap();
/*  56 */   private static final HashMap<String, CMapCidUni> cacheCidUni = new HashMap();
/*  57 */   private static final HashMap<String, CMapCidByte> cacheCidByte = new HashMap();
/*  58 */   private static final HashMap<String, CMapByteCid> cacheByteCid = new HashMap();
/*     */ 
/*     */   public static CMapUniCid getCachedCMapUniCid(String name) throws IOException {
/*  61 */     CMapUniCid cmap = null;
/*  62 */     synchronized (cacheUniCid) {
/*  63 */       cmap = (CMapUniCid)cacheUniCid.get(name);
/*     */     }
/*  65 */     if (cmap == null) {
/*  66 */       cmap = new CMapUniCid();
/*  67 */       CMapParserEx.parseCid(name, cmap, new CidResource());
/*  68 */       synchronized (cacheUniCid) {
/*  69 */         cacheUniCid.put(name, cmap);
/*     */       }
/*     */     }
/*  72 */     return cmap;
/*     */   }
/*     */ 
/*     */   public static CMapCidUni getCachedCMapCidUni(String name) throws IOException {
/*  76 */     CMapCidUni cmap = null;
/*  77 */     synchronized (cacheCidUni) {
/*  78 */       cmap = (CMapCidUni)cacheCidUni.get(name);
/*     */     }
/*  80 */     if (cmap == null) {
/*  81 */       cmap = new CMapCidUni();
/*  82 */       CMapParserEx.parseCid(name, cmap, new CidResource());
/*  83 */       synchronized (cacheCidUni) {
/*  84 */         cacheCidUni.put(name, cmap);
/*     */       }
/*     */     }
/*  87 */     return cmap;
/*     */   }
/*     */ 
/*     */   public static CMapCidByte getCachedCMapCidByte(String name) throws IOException {
/*  91 */     CMapCidByte cmap = null;
/*  92 */     synchronized (cacheCidByte) {
/*  93 */       cmap = (CMapCidByte)cacheCidByte.get(name);
/*     */     }
/*  95 */     if (cmap == null) {
/*  96 */       cmap = new CMapCidByte();
/*  97 */       CMapParserEx.parseCid(name, cmap, new CidResource());
/*  98 */       synchronized (cacheCidByte) {
/*  99 */         cacheCidByte.put(name, cmap);
/*     */       }
/*     */     }
/* 102 */     return cmap;
/*     */   }
/*     */ 
/*     */   public static CMapByteCid getCachedCMapByteCid(String name) throws IOException {
/* 106 */     CMapByteCid cmap = null;
/* 107 */     synchronized (cacheByteCid) {
/* 108 */       cmap = (CMapByteCid)cacheByteCid.get(name);
/*     */     }
/* 110 */     if (cmap == null) {
/* 111 */       cmap = new CMapByteCid();
/* 112 */       CMapParserEx.parseCid(name, cmap, new CidResource());
/* 113 */       synchronized (cacheByteCid) {
/* 114 */         cacheByteCid.put(name, cmap);
/*     */       }
/*     */     }
/* 117 */     return cmap;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.fonts.cmaps.CMapCache
 * JD-Core Version:    0.6.2
 */