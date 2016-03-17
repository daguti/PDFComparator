/*     */ package org.apache.jempbox.xmp;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.apache.jempbox.impl.XMLUtil;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class XMPSchemaMediaManagement extends XMPSchema
/*     */ {
/*     */   public static final String NAMESPACE = "http://ns.adobe.com/xap/1.0/mm/";
/*     */ 
/*     */   public XMPSchemaMediaManagement(XMPMetadata parent)
/*     */   {
/*  45 */     super(parent, "xmpMM", "http://ns.adobe.com/xap/1.0/mm/");
/*     */   }
/*     */ 
/*     */   public XMPSchemaMediaManagement(Element element, String prefix)
/*     */   {
/*  56 */     super(element, prefix);
/*     */   }
/*     */ 
/*     */   public ResourceRef getDerivedFrom()
/*     */   {
/*  67 */     ResourceRef retval = null;
/*  68 */     NodeList nodes = this.schema.getElementsByTagName(this.prefix + ":DerivedFrom");
/*  69 */     if (nodes.getLength() > 0)
/*     */     {
/*  71 */       Element derived = (Element)nodes.item(0);
/*  72 */       retval = new ResourceRef(derived);
/*     */     }
/*     */     else
/*     */     {
/*  78 */       NodeList deprecatedNodes = this.schema.getElementsByTagName("xmpMM:RenditionOf");
/*  79 */       if (deprecatedNodes.getLength() > 0)
/*     */       {
/*  81 */         Element derived = (Element)deprecatedNodes.item(0);
/*  82 */         retval = new ResourceRef(derived);
/*     */       }
/*     */     }
/*  85 */     return retval;
/*     */   }
/*     */ 
/*     */   public ResourceRef createDerivedFrom()
/*     */   {
/*  96 */     Element node = this.schema.getOwnerDocument().createElement(this.prefix + ":DerivedFrom");
/*  97 */     ResourceRef ref = new ResourceRef(node);
/*  98 */     return ref;
/*     */   }
/*     */ 
/*     */   public void setDerivedFrom(ResourceRef resource)
/*     */   {
/* 110 */     XMLUtil.setElementableValue(this.schema, this.prefix + ":DerivedFrom", resource);
/*     */   }
/*     */ 
/*     */   public void setDocumentID(String id)
/*     */   {
/* 121 */     setTextProperty(this.prefix + ":DocumentID", id);
/*     */   }
/*     */ 
/*     */   public String getDocumentID()
/*     */   {
/* 131 */     return getTextProperty(this.prefix + ":DocumentID");
/*     */   }
/*     */ 
/*     */   public void setVersionID(String id)
/*     */   {
/* 140 */     setTextProperty(this.prefix + ":VersionID", id);
/*     */   }
/*     */ 
/*     */   public String getVersionID()
/*     */   {
/* 149 */     return getTextProperty(this.prefix + ":VersionID");
/*     */   }
/*     */ 
/*     */   public List<ResourceEvent> getHistory()
/*     */   {
/* 159 */     return getEventSequenceList(this.prefix + ":History");
/*     */   }
/*     */ 
/*     */   public void removeHistory(ResourceEvent event)
/*     */   {
/* 169 */     removeSequenceValue(this.prefix + ":History", event);
/*     */   }
/*     */ 
/*     */   public void addHistory(ResourceEvent event)
/*     */   {
/* 179 */     addSequenceValue(this.prefix + ":History", event);
/*     */   }
/*     */ 
/*     */   public ResourceRef getManagedFrom()
/*     */   {
/* 189 */     ResourceRef retval = null;
/* 190 */     NodeList nodes = this.schema.getElementsByTagName(this.prefix + ":ManagedFrom");
/* 191 */     if (nodes.getLength() > 0)
/*     */     {
/* 193 */       Element derived = (Element)nodes.item(0);
/* 194 */       retval = new ResourceRef(derived);
/*     */     }
/* 196 */     return retval;
/*     */   }
/*     */ 
/*     */   public ResourceRef createManagedFrom()
/*     */   {
/* 207 */     Element node = this.schema.getOwnerDocument().createElement(this.prefix + ":ManagedFrom");
/* 208 */     ResourceRef ref = new ResourceRef(node);
/* 209 */     return ref;
/*     */   }
/*     */ 
/*     */   public void setManagedFrom(ResourceRef resource)
/*     */   {
/* 221 */     XMLUtil.setElementableValue(this.schema, this.prefix + ":ManagedFrom", resource);
/*     */   }
/*     */ 
/*     */   public void setManager(String manager)
/*     */   {
/* 231 */     setTextProperty(this.prefix + ":Manager", manager);
/*     */   }
/*     */ 
/*     */   public String getManager()
/*     */   {
/* 241 */     return getTextProperty(this.prefix + ":Manager");
/*     */   }
/*     */ 
/*     */   public void setManageTo(String uri)
/*     */   {
/* 251 */     setTextProperty(this.prefix + ":ManageTo", uri);
/*     */   }
/*     */ 
/*     */   public String getManageTo()
/*     */   {
/* 261 */     return getTextProperty(this.prefix + ":ManageTo");
/*     */   }
/*     */ 
/*     */   public void setManageUI(String uri)
/*     */   {
/* 271 */     setTextProperty(this.prefix + ":ManageUI", uri);
/*     */   }
/*     */ 
/*     */   public String getManageUI()
/*     */   {
/* 281 */     return getTextProperty(this.prefix + ":ManageUI");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.jempbox.xmp.XMPSchemaMediaManagement
 * JD-Core Version:    0.6.2
 */