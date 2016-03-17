/*     */ package org.apache.xmpbox.type;
/*     */ 
/*     */ import java.util.Calendar;
/*     */ import org.apache.xmpbox.XMPMetadata;
/*     */ 
/*     */ @StructuredType(preferedPrefix="stVer", namespace="http://ns.adobe.com/xap/1.0/sType/Version#")
/*     */ public class VersionType extends AbstractStructuredType
/*     */ {
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*     */   public static final String COMMENTS = "comments";
/*     */ 
/*     */   @PropertyType(type=Types.ResourceEvent, card=Cardinality.Simple)
/*     */   public static final String EVENT = "event";
/*     */ 
/*     */   @PropertyType(type=Types.ProperName, card=Cardinality.Simple)
/*     */   public static final String MODIFIER = "modifier";
/*     */ 
/*     */   @PropertyType(type=Types.Date, card=Cardinality.Simple)
/*     */   public static final String MODIFY_DATE = "modifyDate";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*     */   public static final String VERSION = "version";
/*     */ 
/*     */   public VersionType(XMPMetadata metadata)
/*     */   {
/*  54 */     super(metadata);
/*  55 */     addNamespace(getNamespace(), getPreferedPrefix());
/*     */   }
/*     */ 
/*     */   public String getComments()
/*     */   {
/*  60 */     return getPropertyValueAsString("comments");
/*     */   }
/*     */ 
/*     */   public void setComments(String value)
/*     */   {
/*  65 */     addSimpleProperty("comments", value);
/*     */   }
/*     */ 
/*     */   public ResourceEventType getEvent()
/*     */   {
/*  70 */     return (ResourceEventType)getFirstEquivalentProperty("event", ResourceEventType.class);
/*     */   }
/*     */ 
/*     */   public void setEvent(ResourceEventType value)
/*     */   {
/*  75 */     addProperty(value);
/*     */   }
/*     */ 
/*     */   public Calendar getModifyDate()
/*     */   {
/*  80 */     return getDatePropertyAsCalendar("modifyDate");
/*     */   }
/*     */ 
/*     */   public void setModifyDate(Calendar value)
/*     */   {
/*  85 */     addSimpleProperty("modifyDate", value);
/*     */   }
/*     */ 
/*     */   public String getVersion()
/*     */   {
/*  90 */     return getPropertyValueAsString("version");
/*     */   }
/*     */ 
/*     */   public void setVersion(String value)
/*     */   {
/*  95 */     addSimpleProperty("version", value);
/*     */   }
/*     */ 
/*     */   public String getModifier()
/*     */   {
/* 100 */     return getPropertyValueAsString("modifier");
/*     */   }
/*     */ 
/*     */   public void setModifier(String value)
/*     */   {
/* 105 */     addSimpleProperty("modifier", value);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.type.VersionType
 * JD-Core Version:    0.6.2
 */