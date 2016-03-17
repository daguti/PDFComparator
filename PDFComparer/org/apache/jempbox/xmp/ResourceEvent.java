/*     */ package org.apache.jempbox.xmp;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Calendar;
/*     */ import org.apache.jempbox.impl.DateConverter;
/*     */ import org.apache.jempbox.impl.XMLUtil;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ public class ResourceEvent
/*     */   implements Elementable
/*     */ {
/*     */   public static final String NAMESPACE = "http://ns.adobe.com/xap/1.0/sType/ResourceEvent#";
/*     */   public static final String ACTION_CONVERTED = "converted";
/*     */   public static final String ACTION_COPIED = "copied";
/*     */   public static final String ACTION_CREATED = "created";
/*     */   public static final String ACTION_CROPPED = "cropped";
/*     */   public static final String ACTION_EDITED = "edited";
/*     */   public static final String ACTION_FILTERED = "filtered";
/*     */   public static final String ACTION_FORMATTED = "formatted";
/*     */   public static final String ACTION_VERSION_UPDATED = "version_updated";
/*     */   public static final String ACTION_PRINTED = "printed";
/*     */   public static final String ACTION_PUBLISHED = "published";
/*     */   public static final String ACTION_MANAGED = "managed";
/*     */   public static final String ACTION_PRODUCED = "produced";
/*     */   public static final String ACTION_RESIZED = "resized";
/*  97 */   protected Element parent = null;
/*     */ 
/*     */   public ResourceEvent(Element parentElement)
/*     */   {
/* 106 */     this.parent = parentElement;
/* 107 */     if (!this.parent.hasAttribute("xmlns:stEvt"))
/*     */     {
/* 109 */       this.parent.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:stEvt", "http://ns.adobe.com/xap/1.0/sType/ResourceEvent#");
/*     */     }
/*     */   }
/*     */ 
/*     */   public ResourceEvent(XMPSchema schema)
/*     */   {
/* 123 */     this.parent = schema.getElement().getOwnerDocument().createElement("rdf:li");
/* 124 */     this.parent.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:stEvt", "http://ns.adobe.com/xap/1.0/sType/ResourceEvent#");
/*     */   }
/*     */ 
/*     */   public Element getElement()
/*     */   {
/* 137 */     return this.parent;
/*     */   }
/*     */ 
/*     */   public String getAction()
/*     */   {
/* 147 */     return XMLUtil.getStringValue(this.parent, "stEvt:action");
/*     */   }
/*     */ 
/*     */   public void setAction(String action)
/*     */   {
/* 157 */     XMLUtil.setStringValue(this.parent, "stEvt:action", action);
/*     */   }
/*     */ 
/*     */   public String getInstanceID()
/*     */   {
/* 167 */     return XMLUtil.getStringValue(this.parent, "stEvt:instanceID");
/*     */   }
/*     */ 
/*     */   public void setInstanceID(String id)
/*     */   {
/* 177 */     XMLUtil.setStringValue(this.parent, "stEvt:instanceID", id);
/*     */   }
/*     */ 
/*     */   public String getParameters()
/*     */   {
/* 187 */     return XMLUtil.getStringValue(this.parent, "stEvt:parameters");
/*     */   }
/*     */ 
/*     */   public void setParameters(String param)
/*     */   {
/* 197 */     XMLUtil.setStringValue(this.parent, "stEvt:parameters", param);
/*     */   }
/*     */ 
/*     */   public String getSoftwareAgent()
/*     */   {
/* 207 */     return XMLUtil.getStringValue(this.parent, "stEvt:softwareAgent");
/*     */   }
/*     */ 
/*     */   public void setSoftwareAgent(String software)
/*     */   {
/* 217 */     XMLUtil.setStringValue(this.parent, "stEvt:softwareAgent", software);
/*     */   }
/*     */ 
/*     */   public Calendar getWhen()
/*     */     throws IOException
/*     */   {
/* 229 */     return DateConverter.toCalendar(XMLUtil.getStringValue(this.parent, "stEvt:when"));
/*     */   }
/*     */ 
/*     */   public void setWhen(Calendar when)
/*     */   {
/* 239 */     XMLUtil.setStringValue(this.parent, "stEvt:when", DateConverter.toISO8601(when));
/*     */   }
/*     */ 
/*     */   public String getManager()
/*     */   {
/* 249 */     return XMLUtil.getStringValue(this.parent, "stRef:manager");
/*     */   }
/*     */ 
/*     */   public void setMangager(String manager)
/*     */   {
/* 259 */     XMLUtil.setStringValue(this.parent, "stRef:manager", manager);
/*     */   }
/*     */ 
/*     */   public String getManagerVariant()
/*     */   {
/* 269 */     return XMLUtil.getStringValue(this.parent, "stRef:managerVariant");
/*     */   }
/*     */ 
/*     */   public void setMangagerVariant(String managerVariant)
/*     */   {
/* 279 */     XMLUtil.setStringValue(this.parent, "stRef:managerVariant", managerVariant);
/*     */   }
/*     */ 
/*     */   public String getManagerTo()
/*     */   {
/* 289 */     return XMLUtil.getStringValue(this.parent, "stRef:managerTo");
/*     */   }
/*     */ 
/*     */   public void setMangagerTo(String managerTo)
/*     */   {
/* 299 */     XMLUtil.setStringValue(this.parent, "stRef:managerTo", managerTo);
/*     */   }
/*     */ 
/*     */   public String getManagerUI()
/*     */   {
/* 309 */     return XMLUtil.getStringValue(this.parent, "stRef:managerUI");
/*     */   }
/*     */ 
/*     */   public void setMangagerUI(String managerUI)
/*     */   {
/* 319 */     XMLUtil.setStringValue(this.parent, "stRef:managerUI", managerUI);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.jempbox.xmp.ResourceEvent
 * JD-Core Version:    0.6.2
 */