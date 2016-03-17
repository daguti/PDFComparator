/*     */ package org.apache.pdfbox.pdmodel.interactive.annotation;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.common.filespecification.PDFileSpecification;
/*     */ 
/*     */ public class PDAnnotationFileAttachment extends PDAnnotationMarkup
/*     */ {
/*     */   public static final String ATTACHMENT_NAME_PUSH_PIN = "PushPin";
/*     */   public static final String ATTACHMENT_NAME_GRAPH = "Graph";
/*     */   public static final String ATTACHMENT_NAME_PAPERCLIP = "Paperclip";
/*     */   public static final String ATTACHMENT_NAME_TAG = "Tag";
/*     */   public static final String SUB_TYPE = "FileAttachment";
/*     */ 
/*     */   public PDAnnotationFileAttachment()
/*     */   {
/*  61 */     getDictionary().setItem(COSName.SUBTYPE, COSName.getPDFName("FileAttachment"));
/*     */   }
/*     */ 
/*     */   public PDAnnotationFileAttachment(COSDictionary field)
/*     */   {
/*  72 */     super(field);
/*     */   }
/*     */ 
/*     */   public PDFileSpecification getFile()
/*     */     throws IOException
/*     */   {
/*  84 */     return PDFileSpecification.createFS(getDictionary().getDictionaryObject("FS"));
/*     */   }
/*     */ 
/*     */   public void setFile(PDFileSpecification file)
/*     */   {
/*  94 */     getDictionary().setItem("FS", file);
/*     */   }
/*     */ 
/*     */   public String getAttachmentName()
/*     */   {
/* 105 */     return getDictionary().getNameAsString("Name", "PushPin");
/*     */   }
/*     */ 
/*     */   public void setAttachementName(String name)
/*     */   {
/* 116 */     getDictionary().setName("Name", name);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationFileAttachment
 * JD-Core Version:    0.6.2
 */