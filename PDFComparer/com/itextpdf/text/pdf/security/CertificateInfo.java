/*     */ package com.itextpdf.text.pdf.security;
/*     */ 
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.bouncycastle.asn1.ASN1InputStream;
/*     */ import org.bouncycastle.asn1.ASN1ObjectIdentifier;
/*     */ import org.bouncycastle.asn1.ASN1Primitive;
/*     */ import org.bouncycastle.asn1.ASN1Sequence;
/*     */ import org.bouncycastle.asn1.ASN1Set;
/*     */ import org.bouncycastle.asn1.ASN1String;
/*     */ import org.bouncycastle.asn1.ASN1TaggedObject;
/*     */ 
/*     */ public class CertificateInfo
/*     */ {
/*     */   public static X500Name getIssuerFields(X509Certificate cert)
/*     */   {
/*     */     try
/*     */     {
/* 323 */       return new X500Name((ASN1Sequence)getIssuer(cert.getTBSCertificate()));
/*     */     }
/*     */     catch (Exception e) {
/* 326 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static ASN1Primitive getIssuer(byte[] enc)
/*     */   {
/*     */     try
/*     */     {
/* 337 */       ASN1InputStream in = new ASN1InputStream(new ByteArrayInputStream(enc));
/* 338 */       ASN1Sequence seq = (ASN1Sequence)in.readObject();
/* 339 */       return (ASN1Primitive)seq.getObjectAt((seq.getObjectAt(0) instanceof ASN1TaggedObject) ? 3 : 2);
/*     */     }
/*     */     catch (IOException e) {
/* 342 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static X500Name getSubjectFields(X509Certificate cert)
/*     */   {
/*     */     try
/*     */     {
/* 355 */       if (cert != null)
/* 356 */         return new X500Name((ASN1Sequence)getSubject(cert.getTBSCertificate()));
/*     */     }
/*     */     catch (Exception e) {
/* 359 */       throw new ExceptionConverter(e);
/*     */     }
/* 361 */     return null;
/*     */   }
/*     */ 
/*     */   public static ASN1Primitive getSubject(byte[] enc)
/*     */   {
/*     */     try
/*     */     {
/* 371 */       ASN1InputStream in = new ASN1InputStream(new ByteArrayInputStream(enc));
/* 372 */       ASN1Sequence seq = (ASN1Sequence)in.readObject();
/* 373 */       return (ASN1Primitive)seq.getObjectAt((seq.getObjectAt(0) instanceof ASN1TaggedObject) ? 5 : 4);
/*     */     }
/*     */     catch (IOException e) {
/* 376 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class X509NameTokenizer
/*     */   {
/*     */     private String oid;
/*     */     private int index;
/* 257 */     private StringBuffer buf = new StringBuffer();
/*     */ 
/*     */     public X509NameTokenizer(String oid) {
/* 260 */       this.oid = oid;
/* 261 */       this.index = -1;
/*     */     }
/*     */ 
/*     */     public boolean hasMoreTokens() {
/* 265 */       return this.index != this.oid.length();
/*     */     }
/*     */ 
/*     */     public String nextToken() {
/* 269 */       if (this.index == this.oid.length()) {
/* 270 */         return null;
/*     */       }
/*     */ 
/* 273 */       int end = this.index + 1;
/* 274 */       boolean quoted = false;
/* 275 */       boolean escaped = false;
/*     */ 
/* 277 */       this.buf.setLength(0);
/*     */ 
/* 279 */       while (end != this.oid.length()) {
/* 280 */         char c = this.oid.charAt(end);
/*     */ 
/* 282 */         if (c == '"') {
/* 283 */           if (!escaped) {
/* 284 */             quoted = !quoted;
/*     */           }
/*     */           else {
/* 287 */             this.buf.append(c);
/*     */           }
/* 289 */           escaped = false;
/*     */         }
/* 292 */         else if ((escaped) || (quoted)) {
/* 293 */           this.buf.append(c);
/* 294 */           escaped = false;
/*     */         }
/* 296 */         else if (c == '\\') {
/* 297 */           escaped = true;
/*     */         } else {
/* 299 */           if (c == ',')
/*     */           {
/*     */             break;
/*     */           }
/* 303 */           this.buf.append(c);
/*     */         }
/*     */ 
/* 306 */         end++;
/*     */       }
/*     */ 
/* 309 */       this.index = end;
/* 310 */       return this.buf.toString().trim();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class X500Name
/*     */   {
/*  80 */     public static final ASN1ObjectIdentifier C = new ASN1ObjectIdentifier("2.5.4.6");
/*     */ 
/*  83 */     public static final ASN1ObjectIdentifier O = new ASN1ObjectIdentifier("2.5.4.10");
/*     */ 
/*  86 */     public static final ASN1ObjectIdentifier OU = new ASN1ObjectIdentifier("2.5.4.11");
/*     */ 
/*  89 */     public static final ASN1ObjectIdentifier T = new ASN1ObjectIdentifier("2.5.4.12");
/*     */ 
/*  92 */     public static final ASN1ObjectIdentifier CN = new ASN1ObjectIdentifier("2.5.4.3");
/*     */ 
/*  95 */     public static final ASN1ObjectIdentifier SN = new ASN1ObjectIdentifier("2.5.4.5");
/*     */ 
/*  98 */     public static final ASN1ObjectIdentifier L = new ASN1ObjectIdentifier("2.5.4.7");
/*     */ 
/* 101 */     public static final ASN1ObjectIdentifier ST = new ASN1ObjectIdentifier("2.5.4.8");
/*     */ 
/* 104 */     public static final ASN1ObjectIdentifier SURNAME = new ASN1ObjectIdentifier("2.5.4.4");
/*     */ 
/* 107 */     public static final ASN1ObjectIdentifier GIVENNAME = new ASN1ObjectIdentifier("2.5.4.42");
/*     */ 
/* 110 */     public static final ASN1ObjectIdentifier INITIALS = new ASN1ObjectIdentifier("2.5.4.43");
/*     */ 
/* 113 */     public static final ASN1ObjectIdentifier GENERATION = new ASN1ObjectIdentifier("2.5.4.44");
/*     */ 
/* 116 */     public static final ASN1ObjectIdentifier UNIQUE_IDENTIFIER = new ASN1ObjectIdentifier("2.5.4.45");
/*     */ 
/* 122 */     public static final ASN1ObjectIdentifier EmailAddress = new ASN1ObjectIdentifier("1.2.840.113549.1.9.1");
/*     */ 
/* 127 */     public static final ASN1ObjectIdentifier E = EmailAddress;
/*     */ 
/* 130 */     public static final ASN1ObjectIdentifier DC = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.25");
/*     */ 
/* 133 */     public static final ASN1ObjectIdentifier UID = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.1");
/*     */ 
/* 136 */     public static final Map<ASN1ObjectIdentifier, String> DefaultSymbols = new HashMap();
/*     */ 
/* 157 */     public Map<String, ArrayList<String>> values = new HashMap();
/*     */ 
/*     */     public X500Name(ASN1Sequence seq)
/*     */     {
/* 165 */       Enumeration e = seq.getObjects();
/*     */ 
/* 167 */       while (e.hasMoreElements()) {
/* 168 */         ASN1Set set = (ASN1Set)e.nextElement();
/*     */ 
/* 170 */         for (int i = 0; i < set.size(); i++) {
/* 171 */           ASN1Sequence s = (ASN1Sequence)set.getObjectAt(i);
/* 172 */           String id = (String)DefaultSymbols.get(s.getObjectAt(0));
/* 173 */           if (id != null)
/*     */           {
/* 175 */             ArrayList vs = (ArrayList)this.values.get(id);
/* 176 */             if (vs == null) {
/* 177 */               vs = new ArrayList();
/* 178 */               this.values.put(id, vs);
/*     */             }
/* 180 */             vs.add(((ASN1String)s.getObjectAt(1)).getString());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     public X500Name(String dirName)
/*     */     {
/* 190 */       CertificateInfo.X509NameTokenizer nTok = new CertificateInfo.X509NameTokenizer(dirName);
/*     */ 
/* 192 */       while (nTok.hasMoreTokens()) {
/* 193 */         String token = nTok.nextToken();
/* 194 */         int index = token.indexOf('=');
/*     */ 
/* 196 */         if (index == -1) {
/* 197 */           throw new IllegalArgumentException(MessageLocalization.getComposedMessage("badly.formated.directory.string", new Object[0]));
/*     */         }
/*     */ 
/* 200 */         String id = token.substring(0, index).toUpperCase();
/* 201 */         String value = token.substring(index + 1);
/* 202 */         ArrayList vs = (ArrayList)this.values.get(id);
/* 203 */         if (vs == null) {
/* 204 */           vs = new ArrayList();
/* 205 */           this.values.put(id, vs);
/*     */         }
/* 207 */         vs.add(value);
/*     */       }
/*     */     }
/*     */ 
/*     */     public String getField(String name)
/*     */     {
/* 218 */       List vs = (List)this.values.get(name);
/* 219 */       return vs == null ? null : (String)vs.get(0);
/*     */     }
/*     */ 
/*     */     public List<String> getFieldArray(String name)
/*     */     {
/* 228 */       return (List)this.values.get(name);
/*     */     }
/*     */ 
/*     */     public Map<String, ArrayList<String>> getFields()
/*     */     {
/* 236 */       return this.values;
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 244 */       return this.values.toString();
/*     */     }
/*     */ 
/*     */     static
/*     */     {
/* 139 */       DefaultSymbols.put(C, "C");
/* 140 */       DefaultSymbols.put(O, "O");
/* 141 */       DefaultSymbols.put(T, "T");
/* 142 */       DefaultSymbols.put(OU, "OU");
/* 143 */       DefaultSymbols.put(CN, "CN");
/* 144 */       DefaultSymbols.put(L, "L");
/* 145 */       DefaultSymbols.put(ST, "ST");
/* 146 */       DefaultSymbols.put(SN, "SN");
/* 147 */       DefaultSymbols.put(EmailAddress, "E");
/* 148 */       DefaultSymbols.put(DC, "DC");
/* 149 */       DefaultSymbols.put(UID, "UID");
/* 150 */       DefaultSymbols.put(SURNAME, "SURNAME");
/* 151 */       DefaultSymbols.put(GIVENNAME, "GIVENNAME");
/* 152 */       DefaultSymbols.put(INITIALS, "INITIALS");
/* 153 */       DefaultSymbols.put(GENERATION, "GENERATION");
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.security.CertificateInfo
 * JD-Core Version:    0.6.2
 */