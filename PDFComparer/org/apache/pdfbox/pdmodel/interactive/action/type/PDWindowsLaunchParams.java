/*     */ package org.apache.pdfbox.pdmodel.interactive.action.type;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ 
/*     */ public class PDWindowsLaunchParams
/*     */   implements COSObjectable
/*     */ {
/*     */   public static final String OPERATION_OPEN = "open";
/*     */   public static final String OPERATION_PRINT = "print";
/*     */   protected COSDictionary params;
/*     */ 
/*     */   public PDWindowsLaunchParams()
/*     */   {
/*  51 */     this.params = new COSDictionary();
/*     */   }
/*     */ 
/*     */   public PDWindowsLaunchParams(COSDictionary p)
/*     */   {
/*  61 */     this.params = p;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  71 */     return this.params;
/*     */   }
/*     */ 
/*     */   public COSDictionary getCOSDictionary()
/*     */   {
/*  81 */     return this.params;
/*     */   }
/*     */ 
/*     */   public String getFilename()
/*     */   {
/*  91 */     return this.params.getString("F");
/*     */   }
/*     */ 
/*     */   public void setFilename(String file)
/*     */   {
/* 101 */     this.params.setString("F", file);
/*     */   }
/*     */ 
/*     */   public String getDirectory()
/*     */   {
/* 111 */     return this.params.getString("D");
/*     */   }
/*     */ 
/*     */   public void setDirectory(String dir)
/*     */   {
/* 121 */     this.params.setString("D", dir);
/*     */   }
/*     */ 
/*     */   public String getOperation()
/*     */   {
/* 134 */     return this.params.getString("O", "open");
/*     */   }
/*     */ 
/*     */   public void setOperation(String op)
/*     */   {
/* 144 */     this.params.setString("D", op);
/*     */   }
/*     */ 
/*     */   public String getExecuteParam()
/*     */   {
/* 154 */     return this.params.getString("P");
/*     */   }
/*     */ 
/*     */   public void setExecuteParam(String param)
/*     */   {
/* 164 */     this.params.setString("P", param);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.action.type.PDWindowsLaunchParams
 * JD-Core Version:    0.6.2
 */