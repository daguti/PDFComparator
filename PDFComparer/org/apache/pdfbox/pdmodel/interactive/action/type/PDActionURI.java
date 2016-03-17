/*     */ package org.apache.pdfbox.pdmodel.interactive.action.type;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ 
/*     */ public class PDActionURI extends PDAction
/*     */ {
/*     */   public static final String SUB_TYPE = "URI";
/*     */ 
/*     */   public PDActionURI()
/*     */   {
/*  41 */     this.action = new COSDictionary();
/*  42 */     setSubType("URI");
/*     */   }
/*     */ 
/*     */   public PDActionURI(COSDictionary a)
/*     */   {
/*  52 */     super(a);
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  62 */     return this.action;
/*     */   }
/*     */ 
/*     */   public COSDictionary getCOSDictionary()
/*     */   {
/*  72 */     return this.action;
/*     */   }
/*     */ 
/*     */   public String getS()
/*     */   {
/*  83 */     return this.action.getNameAsString("S");
/*     */   }
/*     */ 
/*     */   public void setS(String s)
/*     */   {
/*  94 */     this.action.setName("S", s);
/*     */   }
/*     */ 
/*     */   public String getURI()
/*     */   {
/* 104 */     return this.action.getString("URI");
/*     */   }
/*     */ 
/*     */   public void setURI(String uri)
/*     */   {
/* 114 */     this.action.setString("URI", uri);
/*     */   }
/*     */ 
/*     */   public boolean shouldTrackMousePosition()
/*     */   {
/* 127 */     return this.action.getBoolean("IsMap", false);
/*     */   }
/*     */ 
/*     */   public void setTrackMousePosition(boolean value)
/*     */   {
/* 137 */     this.action.setBoolean("IsMap", value);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public String getBase()
/*     */   {
/* 154 */     return this.action.getString("Base");
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void setBase(String base)
/*     */   {
/* 171 */     this.action.setString("Base", base);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.action.type.PDActionURI
 * JD-Core Version:    0.6.2
 */