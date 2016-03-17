/*    */ package org.apache.xmpbox.type;
/*    */ 
/*    */ import org.apache.xmpbox.XMPMetadata;
/*    */ 
/*    */ @StructuredType(preferedPrefix="stJob", namespace="http://ns.adobe.com/xap/1.0/sType/Job#")
/*    */ public class JobType extends AbstractStructuredType
/*    */ {
/*    */ 
/*    */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*    */   public static final String ID = "id";
/*    */ 
/*    */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*    */   public static final String NAME = "name";
/*    */ 
/*    */   @PropertyType(type=Types.URL, card=Cardinality.Simple)
/*    */   public static final String URL = "url";
/*    */ 
/*    */   public JobType(XMPMetadata metadata)
/*    */   {
/* 41 */     this(metadata, null);
/*    */   }
/*    */ 
/*    */   public JobType(XMPMetadata metadata, String fieldPrefix)
/*    */   {
/* 46 */     super(metadata, fieldPrefix);
/* 47 */     addNamespace(getNamespace(), getPrefix());
/*    */   }
/*    */ 
/*    */   public void setId(String id)
/*    */   {
/* 52 */     addSimpleProperty("id", id);
/*    */   }
/*    */ 
/*    */   public void setName(String name)
/*    */   {
/* 57 */     addSimpleProperty("name", name);
/*    */   }
/*    */ 
/*    */   public void setUrl(String name)
/*    */   {
/* 62 */     addSimpleProperty("url", name);
/*    */   }
/*    */ 
/*    */   public String getId()
/*    */   {
/* 67 */     return getPropertyValueAsString("id");
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 72 */     return getPropertyValueAsString("name");
/*    */   }
/*    */ 
/*    */   public String getUrl()
/*    */   {
/* 77 */     return getPropertyValueAsString("url");
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.type.JobType
 * JD-Core Version:    0.6.2
 */