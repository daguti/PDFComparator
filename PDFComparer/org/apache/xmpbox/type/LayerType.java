/*    */ package org.apache.xmpbox.type;
/*    */ 
/*    */ import org.apache.xmpbox.XMPMetadata;
/*    */ 
/*    */ @StructuredType(preferedPrefix="photoshop", namespace="http://ns.adobe.com/photoshop/1.0/")
/*    */ public class LayerType extends AbstractStructuredType
/*    */ {
/*    */ 
/*    */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*    */   public static final String LAYER_NAME = "LayerName";
/*    */ 
/*    */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*    */   public static final String LAYER_TEXT = "LayerText";
/*    */ 
/*    */   public LayerType(XMPMetadata metadata)
/*    */   {
/* 39 */     super(metadata);
/* 40 */     setAttribute(new Attribute("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "parseType", "Resource"));
/*    */   }
/*    */ 
/*    */   public String getLayerName()
/*    */   {
/* 50 */     AbstractField absProp = getFirstEquivalentProperty("LayerName", TextType.class);
/* 51 */     if (absProp != null)
/*    */     {
/* 53 */       return ((TextType)absProp).getStringValue();
/*    */     }
/* 55 */     return null;
/*    */   }
/*    */ 
/*    */   public void setLayerName(String image)
/*    */   {
/* 66 */     addProperty(createTextType("LayerName", image));
/*    */   }
/*    */ 
/*    */   public String getLayerText()
/*    */   {
/* 76 */     AbstractField absProp = getFirstEquivalentProperty("LayerText", TextType.class);
/* 77 */     if (absProp != null)
/*    */     {
/* 79 */       return ((TextType)absProp).getStringValue();
/*    */     }
/* 81 */     return null;
/*    */   }
/*    */ 
/*    */   public void setLayerText(String image)
/*    */   {
/* 92 */     addProperty(createTextType("LayerText", image));
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.type.LayerType
 * JD-Core Version:    0.6.2
 */