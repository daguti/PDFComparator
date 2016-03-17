/*     */ package org.apache.jempbox.xmp;
/*     */ 
/*     */ import org.apache.jempbox.impl.XMLUtil;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ public class ResourceRef
/*     */   implements Elementable
/*     */ {
/*  33 */   protected Element parent = null;
/*     */ 
/*     */   public ResourceRef(Element parentElement)
/*     */   {
/*  42 */     this.parent = parentElement;
/*  43 */     if (!this.parent.hasAttribute("xmlns:stRef"))
/*     */     {
/*  45 */       this.parent.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:stRef", "http://ns.adobe.com/xap/1.0/sType/ResourceRef#");
/*     */     }
/*     */   }
/*     */ 
/*     */   public Element getElement()
/*     */   {
/*  59 */     return this.parent;
/*     */   }
/*     */ 
/*     */   public String getInstanceID()
/*     */   {
/*  69 */     return XMLUtil.getStringValue(this.parent, "stRef:instanceID");
/*     */   }
/*     */ 
/*     */   public void setInstanceID(String id)
/*     */   {
/*  79 */     XMLUtil.setStringValue(this.parent, "stRef:instanceID", id);
/*     */   }
/*     */ 
/*     */   public String getDocumentID()
/*     */   {
/*  89 */     return XMLUtil.getStringValue(this.parent, "stRef:documentID");
/*     */   }
/*     */ 
/*     */   public void setDocumentID(String id)
/*     */   {
/*  99 */     XMLUtil.setStringValue(this.parent, "stRef:documentID", id);
/*     */   }
/*     */ 
/*     */   public String getVersionID()
/*     */   {
/* 109 */     return XMLUtil.getStringValue(this.parent, "stRef:versionID");
/*     */   }
/*     */ 
/*     */   public void setVersionID(String id)
/*     */   {
/* 119 */     XMLUtil.setStringValue(this.parent, "stRef:veresionID", id);
/*     */   }
/*     */ 
/*     */   public String getRenditionClass()
/*     */   {
/* 131 */     return XMLUtil.getStringValue(this.parent, "stRef:renditionClass");
/*     */   }
/*     */ 
/*     */   public void setRenditionClass(String renditionClass)
/*     */   {
/* 154 */     XMLUtil.setStringValue(this.parent, "stRef:renditionClass", renditionClass);
/*     */   }
/*     */ 
/*     */   public String getRenditionParams()
/*     */   {
/* 164 */     return XMLUtil.getStringValue(this.parent, "stRef:renditionParams");
/*     */   }
/*     */ 
/*     */   public void setRenditionParams(String params)
/*     */   {
/* 174 */     XMLUtil.setStringValue(this.parent, "stRef:renditionParams", params);
/*     */   }
/*     */ 
/*     */   public String getManager()
/*     */   {
/* 184 */     return XMLUtil.getStringValue(this.parent, "stRef:manager");
/*     */   }
/*     */ 
/*     */   public void setMangager(String manager)
/*     */   {
/* 194 */     XMLUtil.setStringValue(this.parent, "stRef:manager", manager);
/*     */   }
/*     */ 
/*     */   public String getManagerVariant()
/*     */   {
/* 204 */     return XMLUtil.getStringValue(this.parent, "stRef:managerVariant");
/*     */   }
/*     */ 
/*     */   public void setMangagerVariant(String managerVariant)
/*     */   {
/* 214 */     XMLUtil.setStringValue(this.parent, "stRef:managerVariant", managerVariant);
/*     */   }
/*     */ 
/*     */   public String getManagerTo()
/*     */   {
/* 224 */     return XMLUtil.getStringValue(this.parent, "stRef:managerTo");
/*     */   }
/*     */ 
/*     */   public void setMangagerTo(String managerTo)
/*     */   {
/* 234 */     XMLUtil.setStringValue(this.parent, "stRef:managerTo", managerTo);
/*     */   }
/*     */ 
/*     */   public String getManagerUI()
/*     */   {
/* 244 */     return XMLUtil.getStringValue(this.parent, "stRef:managerUI");
/*     */   }
/*     */ 
/*     */   public void setMangagerUI(String managerUI)
/*     */   {
/* 254 */     XMLUtil.setStringValue(this.parent, "stRef:managerUI", managerUI);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.jempbox.xmp.ResourceRef
 * JD-Core Version:    0.6.2
 */