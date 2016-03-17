/*     */ package org.apache.xmpbox.type;
/*     */ 
/*     */ import java.util.Calendar;
/*     */ import org.apache.xmpbox.XMPMetadata;
/*     */ 
/*     */ @StructuredType(preferedPrefix="stEvt", namespace="http://ns.adobe.com/xap/1.0/sType/ResourceEvent#")
/*     */ public class ResourceEventType extends AbstractStructuredType
/*     */ {
/*     */ 
/*     */   @PropertyType(type=Types.Choice, card=Cardinality.Simple)
/*     */   public static final String ACTION = "action";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*     */   public static final String CHANGED = "changed";
/*     */ 
/*     */   @PropertyType(type=Types.GUID, card=Cardinality.Simple)
/*     */   public static final String INSTANCE_ID = "instanceID";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*     */   public static final String PARAMETERS = "parameters";
/*     */ 
/*     */   @PropertyType(type=Types.AgentName, card=Cardinality.Simple)
/*     */   public static final String SOFTWARE_AGENT = "softwareAgent";
/*     */ 
/*     */   @PropertyType(type=Types.Date, card=Cardinality.Simple)
/*     */   public static final String WHEN = "when";
/*     */ 
/*     */   public ResourceEventType(XMPMetadata metadata)
/*     */   {
/*  57 */     super(metadata);
/*  58 */     addNamespace(getNamespace(), getPreferedPrefix());
/*     */   }
/*     */ 
/*     */   public String getInstanceID()
/*     */   {
/*  63 */     return getPropertyValueAsString("instanceID");
/*     */   }
/*     */ 
/*     */   public void setInstanceID(String value)
/*     */   {
/*  68 */     addSimpleProperty("instanceID", value);
/*     */   }
/*     */ 
/*     */   public String getSoftwareAgent()
/*     */   {
/*  73 */     return getPropertyValueAsString("softwareAgent");
/*     */   }
/*     */ 
/*     */   public void setSoftwareAgent(String value)
/*     */   {
/*  78 */     addSimpleProperty("softwareAgent", value);
/*     */   }
/*     */ 
/*     */   public Calendar getWhen()
/*     */   {
/*  83 */     return getDatePropertyAsCalendar("when");
/*     */   }
/*     */ 
/*     */   public void setWhen(Calendar value)
/*     */   {
/*  88 */     addSimpleProperty("when", value);
/*     */   }
/*     */ 
/*     */   public String getAction()
/*     */   {
/*  93 */     return getPropertyValueAsString("action");
/*     */   }
/*     */ 
/*     */   public void setAction(String value)
/*     */   {
/*  98 */     addSimpleProperty("action", value);
/*     */   }
/*     */ 
/*     */   public String getChanged()
/*     */   {
/* 103 */     return getPropertyValueAsString("changed");
/*     */   }
/*     */ 
/*     */   public void setChanged(String value)
/*     */   {
/* 108 */     addSimpleProperty("changed", value);
/*     */   }
/*     */ 
/*     */   public String getParameters()
/*     */   {
/* 113 */     return getPropertyValueAsString("parameters");
/*     */   }
/*     */ 
/*     */   public void setParameters(String value)
/*     */   {
/* 118 */     addSimpleProperty("parameters", value);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.type.ResourceEventType
 * JD-Core Version:    0.6.2
 */