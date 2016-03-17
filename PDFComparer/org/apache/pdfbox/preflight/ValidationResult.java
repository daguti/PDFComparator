/*     */ package org.apache.pdfbox.preflight;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.xmpbox.XMPMetadata;
/*     */ 
/*     */ public class ValidationResult
/*     */ {
/*  38 */   private boolean isValid = false;
/*     */ 
/*  43 */   private List<ValidationError> lErrors = new ArrayList();
/*     */ 
/*  49 */   private XMPMetadata xmpMetaData = null;
/*     */ 
/*     */   public ValidationResult(boolean isValid)
/*     */   {
/*  58 */     this.isValid = isValid;
/*     */   }
/*     */ 
/*     */   public ValidationResult(ValidationError error)
/*     */   {
/*  70 */     this.isValid = false;
/*  71 */     if (error != null)
/*     */     {
/*  73 */       this.lErrors.add(error);
/*     */     }
/*     */   }
/*     */ 
/*     */   public ValidationResult(List<ValidationError> errors)
/*     */   {
/*  86 */     this.isValid = false;
/*  87 */     this.lErrors = errors;
/*     */   }
/*     */ 
/*     */   public void mergeResult(ValidationResult otherResult)
/*     */   {
/*  98 */     if (otherResult != null)
/*     */     {
/* 100 */       this.lErrors.addAll(otherResult.getErrorsList());
/* 101 */       this.isValid &= otherResult.isValid();
/*     */     }
/*     */   }
/*     */ 
/*     */   public XMPMetadata getXmpMetaData()
/*     */   {
/* 110 */     return this.xmpMetaData;
/*     */   }
/*     */ 
/*     */   void setXmpMetaData(XMPMetadata xmpMetaData)
/*     */   {
/* 119 */     this.xmpMetaData = xmpMetaData;
/*     */   }
/*     */ 
/*     */   public boolean isValid()
/*     */   {
/* 127 */     return this.isValid;
/*     */   }
/*     */ 
/*     */   public void addError(ValidationError error)
/*     */   {
/* 137 */     if (error != null)
/*     */     {
/* 139 */       this.isValid &= error.isWarning();
/* 140 */       this.lErrors.add(error);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addErrors(List<ValidationError> errors)
/*     */   {
/* 151 */     if (errors != null)
/*     */     {
/* 153 */       for (ValidationError validationError : errors)
/*     */       {
/* 155 */         addError(validationError);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public List<ValidationError> getErrorsList()
/*     */   {
/* 165 */     return this.lErrors;
/*     */   }
/*     */ 
/*     */   public static class ValidationError
/*     */   {
/*     */     private String errorCode;
/*     */     private String details;
/* 187 */     private boolean isWarning = false;
/*     */ 
/*     */     public ValidationError(String errorCode)
/*     */     {
/* 198 */       this.errorCode = errorCode;
/* 199 */       if (errorCode.startsWith("1.0"))
/*     */       {
/* 201 */         this.details = "Syntax error";
/*     */       }
/* 203 */       else if (errorCode.startsWith("1.1"))
/*     */       {
/* 205 */         this.details = "Body Syntax error";
/*     */       }
/* 207 */       else if (errorCode.startsWith("1.2"))
/*     */       {
/* 209 */         this.details = "Body Syntax error";
/*     */       }
/* 211 */       else if (errorCode.startsWith("1.3"))
/*     */       {
/* 213 */         this.details = "CrossRef Syntax error";
/*     */       }
/* 215 */       else if (errorCode.startsWith("1.4"))
/*     */       {
/* 217 */         this.details = "Trailer Syntax error";
/*     */       }
/* 219 */       else if (errorCode.startsWith("2.1"))
/*     */       {
/* 221 */         this.details = "Invalid Graphis object";
/*     */       }
/* 223 */       else if (errorCode.startsWith("2.2"))
/*     */       {
/* 225 */         this.details = "Invalid Graphis transparency";
/*     */       }
/* 227 */       else if (errorCode.startsWith("2.3"))
/*     */       {
/* 229 */         this.details = "Unexpected key in Graphic object definition";
/*     */       }
/* 231 */       else if (errorCode.startsWith("2.4"))
/*     */       {
/* 233 */         this.details = "Invalid Color space";
/*     */       }
/* 235 */       else if (errorCode.startsWith("3.1"))
/*     */       {
/* 237 */         this.details = "Invalid Font definition";
/*     */       }
/* 239 */       else if (errorCode.startsWith("3.2"))
/*     */       {
/* 241 */         this.details = "Font damaged";
/*     */       }
/* 243 */       else if (errorCode.startsWith("3.3"))
/*     */       {
/* 245 */         this.details = "Glyph error";
/*     */       }
/* 247 */       else if (errorCode.startsWith("4.1"))
/*     */       {
/* 249 */         this.details = "Transparency error";
/*     */       }
/* 251 */       else if (errorCode.startsWith("5.1"))
/*     */       {
/* 253 */         this.details = "Missing field in an annotation definition";
/*     */       }
/* 255 */       else if (errorCode.startsWith("5.2"))
/*     */       {
/* 257 */         this.details = "Forbidden field in an annotation definition";
/*     */       }
/* 259 */       else if (errorCode.startsWith("5.3"))
/*     */       {
/* 261 */         this.details = "Invalid field value in an annotation definition";
/*     */       }
/* 263 */       else if (errorCode.startsWith("6.1"))
/*     */       {
/* 265 */         this.details = "Invalid action definition";
/*     */       }
/* 267 */       else if (errorCode.startsWith("6.2"))
/*     */       {
/* 269 */         this.details = "Action is forbidden";
/*     */       }
/* 271 */       else if (errorCode.startsWith("7"))
/*     */       {
/* 273 */         this.details = "Error on MetaData";
/*     */       }
/*     */       else
/*     */       {
/* 278 */         this.details = "Unknown error";
/*     */       }
/*     */     }
/*     */ 
/*     */     public ValidationError(String errorCode, String details)
/*     */     {
/* 292 */       this(errorCode);
/* 293 */       if (details != null)
/*     */       {
/* 295 */         StringBuilder sb = new StringBuilder(this.details.length() + details.length() + 2);
/* 296 */         sb.append(this.details).append(", ").append(details);
/* 297 */         this.details = sb.toString();
/*     */       }
/*     */     }
/*     */ 
/*     */     public String getErrorCode()
/*     */     {
/* 306 */       return this.errorCode;
/*     */     }
/*     */ 
/*     */     public String getDetails()
/*     */     {
/* 314 */       return this.details;
/*     */     }
/*     */ 
/*     */     public void setDetails(String details)
/*     */     {
/* 324 */       this.details = details;
/*     */     }
/*     */ 
/*     */     public boolean isWarning()
/*     */     {
/* 329 */       return this.isWarning;
/*     */     }
/*     */ 
/*     */     public void setWarning(boolean isWarning)
/*     */     {
/* 334 */       this.isWarning = isWarning;
/*     */     }
/*     */ 
/*     */     public int hashCode()
/*     */     {
/* 339 */       return this.errorCode.hashCode();
/*     */     }
/*     */ 
/*     */     public boolean equals(Object o) {
/* 343 */       if ((o instanceof ValidationError)) {
/* 344 */         ValidationError ve = (ValidationError)o;
/*     */ 
/* 346 */         if ((this.errorCode == null) && (ve.errorCode != null))
/* 347 */           return false;
/* 348 */         if (!this.errorCode.equals(ve.errorCode))
/* 349 */           return false;
/* 350 */         if (!this.details.equals(ve.details)) {
/* 351 */           return false;
/*     */         }
/*     */ 
/* 354 */         return this.isWarning == ve.isWarning;
/*     */       }
/*     */ 
/* 357 */       return false;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.ValidationResult
 * JD-Core Version:    0.6.2
 */