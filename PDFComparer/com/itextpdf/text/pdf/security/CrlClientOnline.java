/*     */ package com.itextpdf.text.pdf.security;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.log.Logger;
/*     */ import com.itextpdf.text.log.LoggerFactory;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateParsingException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ 
/*     */ public class CrlClientOnline
/*     */   implements CrlClient
/*     */ {
/*  72 */   private static final Logger LOGGER = LoggerFactory.getLogger(CrlClientOnline.class);
/*     */ 
/*  75 */   protected List<URL> urls = new ArrayList();
/*     */ 
/*     */   public CrlClientOnline()
/*     */   {
/*     */   }
/*     */ 
/*     */   public CrlClientOnline(String[] crls)
/*     */   {
/*  88 */     for (String url : crls)
/*  89 */       addUrl(url);
/*     */   }
/*     */ 
/*     */   public CrlClientOnline(URL[] crls)
/*     */   {
/*  97 */     for (URL url : this.urls)
/*  98 */       addUrl(url);
/*     */   }
/*     */ 
/*     */   public CrlClientOnline(Certificate[] chain)
/*     */   {
/* 106 */     for (int i = 0; i < chain.length; i++) {
/* 107 */       X509Certificate cert = (X509Certificate)chain[i];
/* 108 */       LOGGER.info("Checking certificate: " + cert.getSubjectDN());
/*     */       try {
/* 110 */         addUrl(CertificateUtil.getCRLURL(cert));
/*     */       } catch (CertificateParsingException e) {
/* 112 */         LOGGER.info("Skipped CRL url (certificate could not be parsed)");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void addUrl(String url)
/*     */   {
/*     */     try
/*     */     {
/* 123 */       addUrl(new URL(url));
/*     */     } catch (MalformedURLException e) {
/* 125 */       LOGGER.info("Skipped CRL url (malformed): " + url);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void addUrl(URL url)
/*     */   {
/* 134 */     if (this.urls.contains(url)) {
/* 135 */       LOGGER.info("Skipped CRL url (duplicate): " + url);
/* 136 */       return;
/*     */     }
/* 138 */     this.urls.add(url);
/* 139 */     LOGGER.info("Added CRL url: " + url);
/*     */   }
/*     */ 
/*     */   public Collection<byte[]> getEncoded(X509Certificate checkCert, String url)
/*     */   {
/* 151 */     if (checkCert == null)
/* 152 */       return null;
/* 153 */     List urllist = new ArrayList(this.urls);
/* 154 */     if (urllist.size() == 0) {
/* 155 */       LOGGER.info("Looking for CRL for certificate " + checkCert.getSubjectDN());
/*     */       try {
/* 157 */         if (url == null)
/* 158 */           url = CertificateUtil.getCRLURL(checkCert);
/* 159 */         if (url == null)
/* 160 */           throw new NullPointerException();
/* 161 */         urllist.add(new URL(url));
/* 162 */         LOGGER.info("Found CRL url: " + url);
/*     */       }
/*     */       catch (Exception e) {
/* 165 */         LOGGER.info("Skipped CRL url: " + e.getMessage());
/*     */       }
/*     */     }
/* 168 */     ArrayList ar = new ArrayList();
/* 169 */     for (URL urlt : urllist) {
/*     */       try {
/* 171 */         LOGGER.info("Checking CRL: " + urlt);
/* 172 */         HttpURLConnection con = (HttpURLConnection)urlt.openConnection();
/* 173 */         if (con.getResponseCode() / 100 != 2) {
/* 174 */           throw new IOException(MessageLocalization.getComposedMessage("invalid.http.response.1", con.getResponseCode()));
/*     */         }
/*     */ 
/* 177 */         InputStream inp = (InputStream)con.getContent();
/* 178 */         byte[] buf = new byte[1024];
/* 179 */         ByteArrayOutputStream bout = new ByteArrayOutputStream();
/*     */         while (true) {
/* 181 */           int n = inp.read(buf, 0, buf.length);
/* 182 */           if (n <= 0)
/*     */             break;
/* 184 */           bout.write(buf, 0, n);
/*     */         }
/* 186 */         inp.close();
/* 187 */         ar.add(bout.toByteArray());
/* 188 */         LOGGER.info("Added CRL found at: " + urlt);
/*     */       }
/*     */       catch (Exception e) {
/* 191 */         LOGGER.info("Skipped CRL: " + e.getMessage() + " for " + urlt);
/*     */       }
/*     */     }
/* 194 */     return ar;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.security.CrlClientOnline
 * JD-Core Version:    0.6.2
 */