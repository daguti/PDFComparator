/*     */ package org.apache.pdfbox.pdmodel.interactive.annotation;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ 
/*     */ public class PDAnnotationText extends PDAnnotationMarkup
/*     */ {
/*     */   public static final String NAME_COMMENT = "Comment";
/*     */   public static final String NAME_KEY = "Key";
/*     */   public static final String NAME_NOTE = "Note";
/*     */   public static final String NAME_HELP = "Help";
/*     */   public static final String NAME_NEW_PARAGRAPH = "NewParagraph";
/*     */   public static final String NAME_PARAGRAPH = "Paragraph";
/*     */   public static final String NAME_INSERT = "Insert";
/*     */   public static final String SUB_TYPE = "Text";
/*     */ 
/*     */   public PDAnnotationText()
/*     */   {
/*  82 */     getDictionary().setItem(COSName.SUBTYPE, COSName.getPDFName("Text"));
/*     */   }
/*     */ 
/*     */   public PDAnnotationText(COSDictionary field)
/*     */   {
/*  95 */     super(field);
/*     */   }
/*     */ 
/*     */   public void setOpen(boolean open)
/*     */   {
/* 106 */     getDictionary().setBoolean(COSName.getPDFName("Open"), open);
/*     */   }
/*     */ 
/*     */   public boolean getOpen()
/*     */   {
/* 117 */     return getDictionary().getBoolean(COSName.getPDFName("Open"), false);
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 129 */     getDictionary().setName(COSName.NAME, name);
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 140 */     return getDictionary().getNameAsString(COSName.NAME, "Note");
/*     */   }
/*     */ 
/*     */   public String getState()
/*     */   {
/* 150 */     return getDictionary().getString("State");
/*     */   }
/*     */ 
/*     */   public void setState(String state)
/*     */   {
/* 160 */     getDictionary().setString("State", state);
/*     */   }
/*     */ 
/*     */   public String getStateModel()
/*     */   {
/* 170 */     return getDictionary().getString("StateModel");
/*     */   }
/*     */ 
/*     */   public void setStateModel(String stateModel)
/*     */   {
/* 181 */     getDictionary().setString("StateModel", stateModel);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationText
 * JD-Core Version:    0.6.2
 */