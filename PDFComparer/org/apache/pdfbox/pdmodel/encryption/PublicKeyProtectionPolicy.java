/*     */ package org.apache.pdfbox.pdmodel.encryption;
/*     */ 
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public class PublicKeyProtectionPolicy extends ProtectionPolicy
/*     */ {
/*  71 */   private ArrayList recipients = null;
/*     */   private X509Certificate decryptionCertificate;
/*     */ 
/*     */   public PublicKeyProtectionPolicy()
/*     */   {
/*  83 */     this.recipients = new ArrayList();
/*     */   }
/*     */ 
/*     */   public void addRecipient(PublicKeyRecipient r)
/*     */   {
/*  93 */     this.recipients.add(r);
/*     */   }
/*     */ 
/*     */   public boolean removeRecipient(PublicKeyRecipient r)
/*     */   {
/* 105 */     return this.recipients.remove(r);
/*     */   }
/*     */ 
/*     */   public Iterator getRecipientsIterator()
/*     */   {
/* 116 */     return this.recipients.iterator();
/*     */   }
/*     */ 
/*     */   public X509Certificate getDecryptionCertificate()
/*     */   {
/* 126 */     return this.decryptionCertificate;
/*     */   }
/*     */ 
/*     */   public void setDecryptionCertificate(X509Certificate aDecryptionCertificate)
/*     */   {
/* 136 */     this.decryptionCertificate = aDecryptionCertificate;
/*     */   }
/*     */ 
/*     */   public int getRecipientsNumber()
/*     */   {
/* 146 */     return this.recipients.size();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.encryption.PublicKeyProtectionPolicy
 * JD-Core Version:    0.6.2
 */