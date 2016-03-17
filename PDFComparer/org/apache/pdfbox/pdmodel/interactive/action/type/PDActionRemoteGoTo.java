/*     */ package org.apache.pdfbox.pdmodel.interactive.action.type;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.pdmodel.common.filespecification.PDFileSpecification;
/*     */ 
/*     */ public class PDActionRemoteGoTo extends PDAction
/*     */ {
/*     */   public static final String SUB_TYPE = "GoToR";
/*     */ 
/*     */   public PDActionRemoteGoTo()
/*     */   {
/*  45 */     this.action = new COSDictionary();
/*  46 */     setSubType("GoToR");
/*     */   }
/*     */ 
/*     */   public PDActionRemoteGoTo(COSDictionary a)
/*     */   {
/*  56 */     super(a);
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  66 */     return this.action;
/*     */   }
/*     */ 
/*     */   public COSDictionary getCOSDictionary()
/*     */   {
/*  76 */     return this.action;
/*     */   }
/*     */ 
/*     */   public String getS()
/*     */   {
/*  87 */     return this.action.getNameAsString("S");
/*     */   }
/*     */ 
/*     */   public void setS(String s)
/*     */   {
/*  98 */     this.action.setName("S", s);
/*     */   }
/*     */ 
/*     */   public PDFileSpecification getFile()
/*     */     throws IOException
/*     */   {
/* 110 */     return PDFileSpecification.createFS(this.action.getDictionaryObject("F"));
/*     */   }
/*     */ 
/*     */   public void setFile(PDFileSpecification fs)
/*     */   {
/* 120 */     this.action.setItem("F", fs);
/*     */   }
/*     */ 
/*     */   public COSBase getD()
/*     */   {
/* 136 */     return this.action.getDictionaryObject("D");
/*     */   }
/*     */ 
/*     */   public void setD(COSBase d)
/*     */   {
/* 152 */     this.action.setItem("D", d);
/*     */   }
/*     */ 
/*     */   public boolean shouldOpenInNewWindow()
/*     */   {
/* 165 */     return this.action.getBoolean("NewWindow", true);
/*     */   }
/*     */ 
/*     */   public void setOpenInNewWindow(boolean value)
/*     */   {
/* 175 */     this.action.setBoolean("NewWindow", value);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.action.type.PDActionRemoteGoTo
 * JD-Core Version:    0.6.2
 */