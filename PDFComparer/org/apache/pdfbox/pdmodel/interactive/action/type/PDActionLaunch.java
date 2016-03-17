/*     */ package org.apache.pdfbox.pdmodel.interactive.action.type;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.pdmodel.common.filespecification.PDFileSpecification;
/*     */ 
/*     */ public class PDActionLaunch extends PDAction
/*     */ {
/*     */   public static final String SUB_TYPE = "Launch";
/*     */ 
/*     */   public PDActionLaunch()
/*     */   {
/*  46 */     setSubType("Launch");
/*     */   }
/*     */ 
/*     */   public PDActionLaunch(COSDictionary a)
/*     */   {
/*  56 */     super(a);
/*     */   }
/*     */ 
/*     */   public PDFileSpecification getFile()
/*     */     throws IOException
/*     */   {
/*  72 */     return PDFileSpecification.createFS(getCOSDictionary().getDictionaryObject("F"));
/*     */   }
/*     */ 
/*     */   public void setFile(PDFileSpecification fs)
/*     */   {
/*  86 */     getCOSDictionary().setItem("F", fs);
/*     */   }
/*     */ 
/*     */   public PDWindowsLaunchParams getWinLaunchParams()
/*     */   {
/*  96 */     COSDictionary win = (COSDictionary)this.action.getDictionaryObject("Win");
/*  97 */     PDWindowsLaunchParams retval = null;
/*  98 */     if (win != null)
/*     */     {
/* 100 */       retval = new PDWindowsLaunchParams(win);
/*     */     }
/* 102 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setWinLaunchParams(PDWindowsLaunchParams win)
/*     */   {
/* 112 */     this.action.setItem("Win", win);
/*     */   }
/*     */ 
/*     */   public String getF()
/*     */   {
/* 125 */     return this.action.getString("F");
/*     */   }
/*     */ 
/*     */   public void setF(String f)
/*     */   {
/* 138 */     this.action.setString("F", f);
/*     */   }
/*     */ 
/*     */   public String getD()
/*     */   {
/* 148 */     return this.action.getString("D");
/*     */   }
/*     */ 
/*     */   public void setD(String d)
/*     */   {
/* 158 */     this.action.setString("D", d);
/*     */   }
/*     */ 
/*     */   public String getO()
/*     */   {
/* 172 */     return this.action.getString("O");
/*     */   }
/*     */ 
/*     */   public void setO(String o)
/*     */   {
/* 186 */     this.action.setString("O", o);
/*     */   }
/*     */ 
/*     */   public String getP()
/*     */   {
/* 197 */     return this.action.getString("P");
/*     */   }
/*     */ 
/*     */   public void setP(String p)
/*     */   {
/* 208 */     this.action.setString("P", p);
/*     */   }
/*     */ 
/*     */   public boolean shouldOpenInNewWindow()
/*     */   {
/* 222 */     return this.action.getBoolean("NewWindow", true);
/*     */   }
/*     */ 
/*     */   public void setOpenInNewWindow(boolean value)
/*     */   {
/* 232 */     this.action.setBoolean("NewWindow", value);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.action.type.PDActionLaunch
 * JD-Core Version:    0.6.2
 */