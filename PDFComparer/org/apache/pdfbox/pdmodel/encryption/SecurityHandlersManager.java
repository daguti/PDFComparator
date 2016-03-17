/*     */ package org.apache.pdfbox.pdmodel.encryption;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.security.Security;
/*     */ import java.util.Hashtable;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.bouncycastle.jce.provider.BouncyCastleProvider;
/*     */ 
/*     */ public class SecurityHandlersManager
/*     */ {
/*  44 */   private static final Log LOG = LogFactory.getLog(SecurityHandlersManager.class);
/*     */   private static SecurityHandlersManager instance;
/*  57 */   private Hashtable handlerNames = null;
/*     */ 
/*  64 */   private Hashtable handlerPolicyClasses = null;
/*     */ 
/*     */   private SecurityHandlersManager()
/*     */   {
/*  71 */     this.handlerNames = new Hashtable();
/*  72 */     this.handlerPolicyClasses = new Hashtable();
/*     */     try
/*     */     {
/*  75 */       registerHandler("Standard", StandardSecurityHandler.class, StandardProtectionPolicy.class);
/*     */ 
/*  79 */       registerHandler("Adobe.PubSec", PublicKeySecurityHandler.class, PublicKeyProtectionPolicy.class);
/*     */     }
/*     */     catch (BadSecurityHandlerException e)
/*     */     {
/*  87 */       throw new RuntimeException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void registerHandler(String filterName, Class securityHandlerClass, Class protectionPolicyClass)
/*     */     throws BadSecurityHandlerException
/*     */   {
/* 107 */     if ((this.handlerNames.contains(securityHandlerClass)) || (this.handlerPolicyClasses.contains(securityHandlerClass)))
/*     */     {
/* 109 */       throw new BadSecurityHandlerException("the following security handler was already registered: " + securityHandlerClass.getName());
/*     */     }
/*     */ 
/* 113 */     if (SecurityHandler.class.isAssignableFrom(securityHandlerClass))
/*     */     {
/*     */       try
/*     */       {
/* 117 */         if (this.handlerNames.containsKey(filterName))
/*     */         {
/* 119 */           throw new BadSecurityHandlerException("a security handler was already registered for the filter name " + filterName);
/*     */         }
/*     */ 
/* 122 */         if (this.handlerPolicyClasses.containsKey(protectionPolicyClass))
/*     */         {
/* 124 */           throw new BadSecurityHandlerException("a security handler was already registered for the policy class " + protectionPolicyClass.getName());
/*     */         }
/*     */ 
/* 128 */         this.handlerNames.put(filterName, securityHandlerClass);
/* 129 */         this.handlerPolicyClasses.put(protectionPolicyClass, securityHandlerClass);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 133 */         throw new BadSecurityHandlerException(e);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 138 */       throw new BadSecurityHandlerException("The class is not a super class of SecurityHandler");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static SecurityHandlersManager getInstance()
/*     */   {
/* 150 */     if (instance == null)
/*     */     {
/* 152 */       instance = new SecurityHandlersManager();
/* 153 */       Security.addProvider(new BouncyCastleProvider());
/*     */     }
/* 155 */     return instance;
/*     */   }
/*     */ 
/*     */   public SecurityHandler getSecurityHandler(ProtectionPolicy policy)
/*     */     throws BadSecurityHandlerException
/*     */   {
/* 170 */     Object found = this.handlerPolicyClasses.get(policy.getClass());
/* 171 */     if (found == null)
/*     */     {
/* 173 */       throw new BadSecurityHandlerException("Cannot find an appropriate security handler for " + policy.getClass().getName());
/*     */     }
/*     */ 
/* 176 */     Class handlerclass = (Class)found;
/* 177 */     Class[] argsClasses = { policy.getClass() };
/* 178 */     Object[] args = { policy };
/*     */     try
/*     */     {
/* 181 */       Constructor c = handlerclass.getDeclaredConstructor(argsClasses);
/* 182 */       return (SecurityHandler)c.newInstance(args);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 187 */       LOG.error(e, e);
/* 188 */       throw new BadSecurityHandlerException("problem while trying to instanciate the security handler " + handlerclass.getName() + ": " + e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   public SecurityHandler getSecurityHandler(String filterName)
/*     */     throws BadSecurityHandlerException
/*     */   {
/* 208 */     Object found = this.handlerNames.get(filterName);
/* 209 */     if (found == null)
/*     */     {
/* 211 */       throw new BadSecurityHandlerException("Cannot find an appropriate security handler for " + filterName);
/*     */     }
/* 213 */     Class handlerclass = (Class)found;
/* 214 */     Class[] argsClasses = new Class[0];
/* 215 */     Object[] args = new Object[0];
/*     */     try
/*     */     {
/* 218 */       Constructor c = handlerclass.getDeclaredConstructor(argsClasses);
/* 219 */       return (SecurityHandler)c.newInstance(args);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 224 */       LOG.error(e, e);
/* 225 */       throw new BadSecurityHandlerException("problem while trying to instanciate the security handler " + handlerclass.getName() + ": " + e.getMessage());
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.encryption.SecurityHandlersManager
 * JD-Core Version:    0.6.2
 */