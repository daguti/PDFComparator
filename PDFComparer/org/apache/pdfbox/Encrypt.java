/*     */ package org.apache.pdfbox;
/*     */ 
/*     */ import java.io.FileInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.security.cert.X509Certificate;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
/*     */ import org.apache.pdfbox.pdmodel.encryption.PublicKeyProtectionPolicy;
/*     */ import org.apache.pdfbox.pdmodel.encryption.PublicKeyRecipient;
/*     */ import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
/*     */ 
/*     */ public class Encrypt
/*     */ {
/*     */   public static void main(String[] args)
/*     */     throws Exception
/*     */   {
/*  52 */     Encrypt encrypt = new Encrypt();
/*  53 */     encrypt.encrypt(args);
/*     */   }
/*     */ 
/*     */   private void encrypt(String[] args) throws Exception
/*     */   {
/*  58 */     if (args.length < 1)
/*     */     {
/*  60 */       usage();
/*     */     }
/*     */     else
/*     */     {
/*  64 */       AccessPermission ap = new AccessPermission();
/*     */ 
/*  66 */       String infile = null;
/*  67 */       String outfile = null;
/*  68 */       String certFile = null;
/*  69 */       String userPassword = "";
/*  70 */       String ownerPassword = "";
/*     */ 
/*  72 */       int keyLength = 40;
/*     */ 
/*  74 */       PDDocument document = null;
/*     */       try
/*     */       {
/*  78 */         for (int i = 0; i < args.length; i++)
/*     */         {
/*  80 */           String key = args[i];
/*  81 */           if (key.equals("-O"))
/*     */           {
/*  83 */             ownerPassword = args[(++i)];
/*     */           }
/*  85 */           else if (key.equals("-U"))
/*     */           {
/*  87 */             userPassword = args[(++i)];
/*     */           }
/*  89 */           else if (key.equals("-canAssemble"))
/*     */           {
/*  91 */             ap.setCanAssembleDocument(args[(++i)].equalsIgnoreCase("true"));
/*     */           }
/*  93 */           else if (key.equals("-canExtractContent"))
/*     */           {
/*  95 */             ap.setCanExtractContent(args[(++i)].equalsIgnoreCase("true"));
/*     */           }
/*  97 */           else if (key.equals("-canExtractForAccessibility"))
/*     */           {
/*  99 */             ap.setCanExtractForAccessibility(args[(++i)].equalsIgnoreCase("true"));
/*     */           }
/* 101 */           else if (key.equals("-canFillInForm"))
/*     */           {
/* 103 */             ap.setCanFillInForm(args[(++i)].equalsIgnoreCase("true"));
/*     */           }
/* 105 */           else if (key.equals("-canModify"))
/*     */           {
/* 107 */             ap.setCanModify(args[(++i)].equalsIgnoreCase("true"));
/*     */           }
/* 109 */           else if (key.equals("-canModifyAnnotations"))
/*     */           {
/* 111 */             ap.setCanModifyAnnotations(args[(++i)].equalsIgnoreCase("true"));
/*     */           }
/* 113 */           else if (key.equals("-canPrint"))
/*     */           {
/* 115 */             ap.setCanPrint(args[(++i)].equalsIgnoreCase("true"));
/*     */           }
/* 117 */           else if (key.equals("-canPrintDegraded"))
/*     */           {
/* 119 */             ap.setCanPrintDegraded(args[(++i)].equalsIgnoreCase("true"));
/*     */           }
/* 121 */           else if (key.equals("-certFile"))
/*     */           {
/* 123 */             certFile = args[(++i)];
/*     */           }
/* 125 */           else if (key.equals("-keyLength"))
/*     */           {
/*     */             try
/*     */             {
/* 129 */               keyLength = Integer.parseInt(args[(++i)]);
/*     */             }
/*     */             catch (NumberFormatException e)
/*     */             {
/* 133 */               throw new NumberFormatException("Error: -keyLength is not an integer '" + args[i] + "'");
/*     */             }
/*     */ 
/*     */           }
/* 137 */           else if (infile == null)
/*     */           {
/* 139 */             infile = key;
/*     */           }
/* 141 */           else if (outfile == null)
/*     */           {
/* 143 */             outfile = key;
/*     */           }
/*     */           else
/*     */           {
/* 147 */             usage();
/*     */           }
/*     */         }
/* 150 */         if (infile == null)
/*     */         {
/* 152 */           usage();
/*     */         }
/* 154 */         if (outfile == null)
/*     */         {
/* 156 */           outfile = infile;
/*     */         }
/* 158 */         document = PDDocument.load(infile);
/*     */ 
/* 160 */         if (!document.isEncrypted())
/*     */         {
/* 162 */           if (certFile != null)
/*     */           {
/* 164 */             PublicKeyProtectionPolicy ppp = new PublicKeyProtectionPolicy();
/* 165 */             PublicKeyRecipient recip = new PublicKeyRecipient();
/* 166 */             recip.setPermission(ap);
/*     */ 
/* 169 */             CertificateFactory cf = CertificateFactory.getInstance("X.509");
/* 170 */             InputStream inStream = new FileInputStream(certFile);
/* 171 */             X509Certificate certificate = (X509Certificate)cf.generateCertificate(inStream);
/* 172 */             inStream.close();
/*     */ 
/* 174 */             recip.setX509(certificate);
/*     */ 
/* 176 */             ppp.addRecipient(recip);
/*     */ 
/* 178 */             ppp.setEncryptionKeyLength(keyLength);
/*     */ 
/* 180 */             document.protect(ppp);
/*     */           }
/*     */           else
/*     */           {
/* 184 */             StandardProtectionPolicy spp = new StandardProtectionPolicy(ownerPassword, userPassword, ap);
/*     */ 
/* 186 */             spp.setEncryptionKeyLength(keyLength);
/* 187 */             document.protect(spp);
/*     */           }
/* 189 */           document.save(outfile);
/*     */         }
/*     */         else
/*     */         {
/* 193 */           System.err.println("Error: Document is already encrypted.");
/*     */         }
/*     */       }
/*     */       finally
/*     */       {
/* 198 */         if (document != null)
/*     */         {
/* 200 */           document.close();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void usage()
/*     */   {
/* 211 */     System.err.println("usage: java -jar pdfbox-app-x.y.z.jar Encrypt [options] <inputfile> [outputfile]");
/* 212 */     System.err.println("   -O <password>                            Set the owner password(ignored if cert is set)");
/*     */ 
/* 214 */     System.err.println("   -U <password>                            Set the user password(ignored if cert is set)");
/*     */ 
/* 216 */     System.err.println("   -certFile <path to cert>                 Path to X.509 certificate");
/* 217 */     System.err.println("   -canAssemble <true|false>                Set the assemble permission");
/* 218 */     System.err.println("   -canExtractContent <true|false>          Set the extraction permission");
/* 219 */     System.err.println("   -canExtractForAccessibility <true|false> Set the extraction permission");
/* 220 */     System.err.println("   -canFillInForm <true|false>              Set the fill in form permission");
/* 221 */     System.err.println("   -canModify <true|false>                  Set the modify permission");
/* 222 */     System.err.println("   -canModifyAnnotations <true|false>       Set the modify annots permission");
/* 223 */     System.err.println("   -canPrint <true|false>                   Set the print permission");
/* 224 */     System.err.println("   -canPrintDegraded <true|false>           Set the print degraded permission");
/* 225 */     System.err.println("   -keyLength <length>                      The length of the key in bits(40)");
/* 226 */     System.err.println("\nNote: By default all permissions are set to true!");
/* 227 */     System.exit(1);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.Encrypt
 * JD-Core Version:    0.6.2
 */