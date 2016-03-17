/*     */ package org.apache.pdfbox.pdmodel.documentinterchange.taggedpdf;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ 
/*     */ public class PDPrintFieldAttributeObject extends PDStandardAttributeObject
/*     */ {
/*     */   public static final String OWNER_PRINT_FIELD = "PrintField";
/*     */   private static final String ROLE = "Role";
/*     */   private static final String CHECKED = "checked";
/*     */   private static final String DESC = "Desc";
/*     */   public static final String ROLE_RB = "rb";
/*     */   public static final String ROLE_CB = "cb";
/*     */   public static final String ROLE_PB = "pb";
/*     */   public static final String ROLE_TV = "tv";
/*     */   public static final String CHECKED_STATE_ON = "on";
/*     */   public static final String CHECKED_STATE_OFF = "off";
/*     */   public static final String CHECKED_STATE_NEUTRAL = "neutral";
/*     */ 
/*     */   public PDPrintFieldAttributeObject()
/*     */   {
/*  74 */     setOwner("PrintField");
/*     */   }
/*     */ 
/*     */   public PDPrintFieldAttributeObject(COSDictionary dictionary)
/*     */   {
/*  84 */     super(dictionary);
/*     */   }
/*     */ 
/*     */   public String getRole()
/*     */   {
/*  95 */     return getName("Role");
/*     */   }
/*     */ 
/*     */   public void setRole(String role)
/*     */   {
/* 111 */     setName("Role", role);
/*     */   }
/*     */ 
/*     */   public String getCheckedState()
/*     */   {
/* 121 */     return getName("checked", "off");
/*     */   }
/*     */ 
/*     */   public void setCheckedState(String checkedState)
/*     */   {
/* 136 */     setName("checked", checkedState);
/*     */   }
/*     */ 
/*     */   public String getAlternateName()
/*     */   {
/* 146 */     return getString("Desc");
/*     */   }
/*     */ 
/*     */   public void setAlternateName(String alternateName)
/*     */   {
/* 156 */     setString("Desc", alternateName);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 162 */     StringBuilder sb = new StringBuilder().append(super.toString());
/* 163 */     if (isSpecified("Role"))
/*     */     {
/* 165 */       sb.append(", Role=").append(getRole());
/*     */     }
/* 167 */     if (isSpecified("checked"))
/*     */     {
/* 169 */       sb.append(", Checked=").append(getCheckedState());
/*     */     }
/* 171 */     if (isSpecified("Desc"))
/*     */     {
/* 173 */       sb.append(", Desc=").append(getAlternateName());
/*     */     }
/* 175 */     return sb.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.documentinterchange.taggedpdf.PDPrintFieldAttributeObject
 * JD-Core Version:    0.6.2
 */