/*     */ package org.apache.pdfbox;
/*     */ 
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.security.KeyStore;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
/*     */ import org.apache.pdfbox.pdmodel.encryption.DecryptionMaterial;
/*     */ import org.apache.pdfbox.pdmodel.encryption.PublicKeyDecryptionMaterial;
/*     */ import org.apache.pdfbox.pdmodel.encryption.StandardDecryptionMaterial;
/*     */ 
/*     */ public class Decrypt
/*     */ {
/*     */   private static final String ALIAS = "-alias";
/*     */   private static final String PASSWORD = "-password";
/*     */   private static final String KEYSTORE = "-keyStore";
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws Exception
/*     */   {
/*  56 */     Decrypt decrypt = new Decrypt();
/*  57 */     decrypt.decrypt(args);
/*     */   }
/*     */ 
/*     */   private void decrypt(String[] args) throws Exception
/*     */   {
/*  62 */     if ((args.length < 2) || (args.length > 5))
/*     */     {
/*  64 */       usage();
/*     */     }
/*     */     else
/*     */     {
/*  68 */       String password = null;
/*  69 */       String infile = null;
/*  70 */       String outfile = null;
/*  71 */       String alias = null;
/*  72 */       String keyStore = null;
/*  73 */       for (int i = 0; i < args.length; i++)
/*     */       {
/*  75 */         if (args[i].equals("-alias"))
/*     */         {
/*  77 */           i++;
/*  78 */           if (i >= args.length)
/*     */           {
/*  80 */             usage();
/*     */           }
/*  82 */           alias = args[i];
/*     */         }
/*  84 */         else if (args[i].equals("-keyStore"))
/*     */         {
/*  86 */           i++;
/*  87 */           if (i >= args.length)
/*     */           {
/*  89 */             usage();
/*     */           }
/*  91 */           keyStore = args[i];
/*     */         }
/*  93 */         else if (args[i].equals("-password"))
/*     */         {
/*  95 */           i++;
/*  96 */           if (i >= args.length)
/*     */           {
/*  98 */             usage();
/*     */           }
/* 100 */           password = args[i];
/*     */         }
/* 102 */         else if (infile == null)
/*     */         {
/* 104 */           infile = args[i];
/*     */         }
/* 106 */         else if (outfile == null)
/*     */         {
/* 108 */           outfile = args[i];
/*     */         }
/*     */         else
/*     */         {
/* 112 */           usage();
/*     */         }
/*     */       }
/* 115 */       if (infile == null)
/*     */       {
/* 117 */         usage();
/*     */       }
/* 119 */       if (outfile == null)
/*     */       {
/* 121 */         outfile = infile;
/*     */       }
/* 123 */       if (password == null)
/*     */       {
/* 125 */         password = "";
/*     */       }
/*     */ 
/* 129 */       PDDocument document = null;
/*     */       try
/*     */       {
/* 133 */         document = PDDocument.load(infile);
/*     */ 
/* 135 */         if (document.isEncrypted())
/*     */         {
/* 137 */           DecryptionMaterial decryptionMaterial = null;
/* 138 */           if (keyStore != null)
/*     */           {
/* 140 */             KeyStore ks = KeyStore.getInstance("PKCS12");
/* 141 */             ks.load(new FileInputStream(keyStore), password.toCharArray());
/*     */ 
/* 143 */             decryptionMaterial = new PublicKeyDecryptionMaterial(ks, alias, password);
/*     */           }
/*     */           else
/*     */           {
/* 147 */             decryptionMaterial = new StandardDecryptionMaterial(password);
/*     */           }
/* 149 */           document.openProtection(decryptionMaterial);
/* 150 */           AccessPermission ap = document.getCurrentAccessPermission();
/* 151 */           if (ap.isOwnerPermission())
/*     */           {
/* 153 */             document.setAllSecurityToBeRemoved(true);
/* 154 */             document.save(outfile);
/*     */           }
/*     */           else
/*     */           {
/* 158 */             throw new IOException("Error: You are only allowed to decrypt a document with the owner password.");
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 164 */           System.err.println("Error: Document is not encrypted.");
/*     */         }
/*     */       }
/*     */       finally
/*     */       {
/* 169 */         if (document != null)
/*     */         {
/* 171 */           document.close();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void usage()
/*     */   {
/* 182 */     System.err.println("usage: java -jar pdfbox-app-x.y.z.jar Decrypt [options] <inputfile> [outputfile]");
/*     */ 
/* 184 */     System.err.println("-alias      The alias of the key in the certificate file (mandatory if several keys are available)");
/*     */ 
/* 186 */     System.err.println("-password   The password to open the certificate and extract the private key from it.");
/* 187 */     System.err.println("-keyStore   The KeyStore that holds the certificate.");
/* 188 */     System.exit(-1);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.Decrypt
 * JD-Core Version:    0.6.2
 */