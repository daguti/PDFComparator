/*     */ package org.apache.pdfbox.pdmodel.encryption;
/*     */ 
/*     */ public class StandardProtectionPolicy extends ProtectionPolicy
/*     */ {
/*     */   private AccessPermission permissions;
/*  43 */   private String ownerPassword = "";
/*     */ 
/*  45 */   private String userPassword = "";
/*     */ 
/*     */   public StandardProtectionPolicy(String ownerPass, String userPass, AccessPermission perms)
/*     */   {
/*  58 */     this.permissions = perms;
/*  59 */     this.userPassword = userPass;
/*  60 */     this.ownerPassword = ownerPass;
/*     */   }
/*     */ 
/*     */   public AccessPermission getPermissions()
/*     */   {
/*  70 */     return this.permissions;
/*     */   }
/*     */ 
/*     */   public void setPermissions(AccessPermission perms)
/*     */   {
/*  80 */     this.permissions = perms;
/*     */   }
/*     */ 
/*     */   public String getOwnerPassword()
/*     */   {
/*  90 */     return this.ownerPassword;
/*     */   }
/*     */ 
/*     */   public void setOwnerPassword(String ownerPass)
/*     */   {
/* 100 */     this.ownerPassword = ownerPass;
/*     */   }
/*     */ 
/*     */   public String getUserPassword()
/*     */   {
/* 110 */     return this.userPassword;
/*     */   }
/*     */ 
/*     */   public void setUserPassword(String userPass)
/*     */   {
/* 120 */     this.userPassword = userPass;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy
 * JD-Core Version:    0.6.2
 */