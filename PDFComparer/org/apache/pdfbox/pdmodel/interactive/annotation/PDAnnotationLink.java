/*     */ package org.apache.pdfbox.pdmodel.interactive.annotation;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.interactive.action.PDActionFactory;
/*     */ import org.apache.pdfbox.pdmodel.interactive.action.type.PDAction;
/*     */ import org.apache.pdfbox.pdmodel.interactive.action.type.PDActionURI;
/*     */ import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDDestination;
/*     */ 
/*     */ public class PDAnnotationLink extends PDAnnotation
/*     */ {
/*     */   public static final String HIGHLIGHT_MODE_NONE = "N";
/*     */   public static final String HIGHLIGHT_MODE_INVERT = "I";
/*     */   public static final String HIGHLIGHT_MODE_OUTLINE = "O";
/*     */   public static final String HIGHLIGHT_MODE_PUSH = "P";
/*     */   public static final String SUB_TYPE = "Link";
/*     */ 
/*     */   public PDAnnotationLink()
/*     */   {
/*  70 */     getDictionary().setItem(COSName.SUBTYPE, COSName.getPDFName("Link"));
/*     */   }
/*     */ 
/*     */   public PDAnnotationLink(COSDictionary field)
/*     */   {
/*  81 */     super(field);
/*     */   }
/*     */ 
/*     */   public PDAction getAction()
/*     */   {
/*  93 */     COSDictionary action = (COSDictionary)getDictionary().getDictionaryObject(COSName.A);
/*     */ 
/*  95 */     return PDActionFactory.createAction(action);
/*     */   }
/*     */ 
/*     */   public void setAction(PDAction action)
/*     */   {
/* 106 */     getDictionary().setItem(COSName.A, action);
/*     */   }
/*     */ 
/*     */   public void setBorderStyle(PDBorderStyleDictionary bs)
/*     */   {
/* 119 */     getDictionary().setItem("BS", bs);
/*     */   }
/*     */ 
/*     */   public PDBorderStyleDictionary getBorderStyle()
/*     */   {
/* 131 */     COSDictionary bs = (COSDictionary)getDictionary().getItem(COSName.getPDFName("BS"));
/*     */ 
/* 133 */     if (bs != null)
/*     */     {
/* 135 */       return new PDBorderStyleDictionary(bs);
/*     */     }
/*     */ 
/* 139 */     return null;
/*     */   }
/*     */ 
/*     */   public PDDestination getDestination()
/*     */     throws IOException
/*     */   {
/* 153 */     COSBase base = getDictionary().getDictionaryObject(COSName.DEST);
/* 154 */     PDDestination retval = PDDestination.create(base);
/*     */ 
/* 156 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setDestination(PDDestination dest)
/*     */   {
/* 166 */     getDictionary().setItem(COSName.DEST, dest);
/*     */   }
/*     */ 
/*     */   public String getHighlightMode()
/*     */   {
/* 177 */     return getDictionary().getNameAsString(COSName.H, "I");
/*     */   }
/*     */ 
/*     */   public void setHighlightMode(String mode)
/*     */   {
/* 187 */     getDictionary().setName(COSName.H, mode);
/*     */   }
/*     */ 
/*     */   public void setPreviousURI(PDActionURI pa)
/*     */   {
/* 198 */     getDictionary().setItem("PA", pa);
/*     */   }
/*     */ 
/*     */   public PDActionURI getPreviousURI()
/*     */   {
/* 209 */     COSDictionary pa = (COSDictionary)getDictionary().getDictionaryObject("PA");
/* 210 */     if (pa != null)
/*     */     {
/* 212 */       return new PDActionURI(pa);
/*     */     }
/*     */ 
/* 216 */     return null;
/*     */   }
/*     */ 
/*     */   public void setQuadPoints(float[] quadPoints)
/*     */   {
/* 229 */     COSArray newQuadPoints = new COSArray();
/* 230 */     newQuadPoints.setFloatArray(quadPoints);
/* 231 */     getDictionary().setItem("QuadPoints", newQuadPoints);
/*     */   }
/*     */ 
/*     */   public float[] getQuadPoints()
/*     */   {
/* 242 */     COSArray quadPoints = (COSArray)getDictionary().getDictionaryObject("QuadPoints");
/* 243 */     if (quadPoints != null)
/*     */     {
/* 245 */       return quadPoints.toFloatArray();
/*     */     }
/*     */ 
/* 249 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink
 * JD-Core Version:    0.6.2
 */